/*     */ package com.hypixel.hytale.logger.backend;
/*     */ 
/*     */ import com.google.common.flogger.backend.LogData;
/*     */ import com.google.common.flogger.backend.LoggerBackend;
/*     */ import com.google.common.flogger.backend.system.SimpleLogRecord;
/*     */ import com.hypixel.hytale.logger.sentry.HytaleSentryHandler;
/*     */ import com.hypixel.hytale.logger.sentry.SkipSentryException;
/*     */ import io.sentry.IScopes;
/*     */ import java.io.PrintStream;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ import java.util.Objects;
/*     */ import java.util.concurrent.ConcurrentHashMap;
/*     */ import java.util.concurrent.CopyOnWriteArrayList;
/*     */ import java.util.function.BiConsumer;
/*     */ import java.util.function.Function;
/*     */ import java.util.logging.FileHandler;
/*     */ import java.util.logging.Level;
/*     */ import java.util.logging.LogRecord;
/*     */ import javax.annotation.Nonnull;
/*     */ import javax.annotation.Nullable;
/*     */ 
/*     */ public class HytaleLoggerBackend
/*     */   extends LoggerBackend {
/*     */   public static Function<String, Level> LOG_LEVEL_LOADER;
/*  26 */   public static final PrintStream REAL_SOUT = System.out;
/*  27 */   public static final PrintStream REAL_SERR = System.err;
/*     */   
/*  29 */   private static final Map<String, HytaleLoggerBackend> CACHE = new ConcurrentHashMap<>();
/*  30 */   private static final HytaleLoggerBackend ROOT_LOGGER = new HytaleLoggerBackend("Hytale", null);
/*     */   
/*  32 */   private static final int OFF_VALUE = Level.OFF.intValue();
/*     */   
/*     */   private final String name;
/*     */   private final HytaleLoggerBackend parent;
/*     */   @Nonnull
/*  37 */   private Level level = Level.INFO;
/*     */   
/*     */   private BiConsumer<Level, Level> onLevelChange;
/*     */   
/*     */   @Nullable
/*     */   private HytaleSentryHandler sentryHandler;
/*     */   
/*     */   private boolean propagateSentryToParent = true;
/*     */   @Nonnull
/*  46 */   private CopyOnWriteArrayList<CopyOnWriteArrayList<LogRecord>> subscribers = new CopyOnWriteArrayList<>();
/*     */ 
/*     */   
/*     */   protected HytaleLoggerBackend(String name) {
/*  50 */     this.name = name;
/*  51 */     this.parent = ROOT_LOGGER;
/*     */   }
/*     */   
/*     */   protected HytaleLoggerBackend(String name, HytaleLoggerBackend parent) {
/*  55 */     this.name = name;
/*  56 */     this.parent = parent;
/*     */   }
/*     */ 
/*     */   
/*     */   public String getLoggerName() {
/*  61 */     return this.name;
/*     */   }
/*     */   
/*     */   @Nonnull
/*     */   public Level getLevel() {
/*  66 */     return this.level;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isLoggable(@Nonnull Level lvl) {
/*  71 */     int levelValue = this.level.intValue();
/*  72 */     return (lvl.intValue() >= levelValue && levelValue != OFF_VALUE);
/*     */   }
/*     */ 
/*     */   
/*     */   public void log(@Nonnull LogData data) {
/*  77 */     log((LogRecord)SimpleLogRecord.create(data));
/*     */   }
/*     */ 
/*     */   
/*     */   public void handleError(@Nonnull RuntimeException error, @Nonnull LogData badData) {
/*  82 */     log((LogRecord)SimpleLogRecord.error(error, badData));
/*     */   }
/*     */   
/*     */   public void log(@Nonnull LogRecord logRecord) {
/*  86 */     log(logRecord, false);
/*     */   }
/*     */ 
/*     */   
/*     */   public void log(@Nonnull LogRecord logRecord, boolean sentryHandled) {
/*  91 */     if (this.sentryHandler != null && !sentryHandled && logRecord.getThrown() != null && !SkipSentryException.hasSkipSentry(logRecord.getThrown())) {
/*  92 */       this.sentryHandler.publish(logRecord);
/*  93 */       sentryHandled = true;
/*     */     } 
/*     */ 
/*     */     
/*  97 */     if (!this.propagateSentryToParent && !sentryHandled && logRecord.getThrown() != null) {
/*  98 */       sentryHandled = true;
/*     */     }
/*     */     
/* 101 */     if (this.parent != null) {
/* 102 */       this.parent.log(logRecord, sentryHandled);
/*     */     } else {
/* 104 */       HytaleFileHandler.INSTANCE.log(logRecord);
/* 105 */       HytaleConsole.INSTANCE.publish(logRecord);
/*     */       
/* 107 */       for (int i = 0; i < this.subscribers.size(); i++) {
/* 108 */         ((CopyOnWriteArrayList<LogRecord>)this.subscribers.get(i)).add(logRecord);
/*     */       }
/*     */     } 
/*     */   }
/*     */   
/*     */   public static void subscribe(CopyOnWriteArrayList<LogRecord> subscriber) {
/* 114 */     if (ROOT_LOGGER.subscribers.contains(subscriber)) {
/*     */       return;
/*     */     }
/* 117 */     ROOT_LOGGER.subscribers.add(subscriber);
/*     */   }
/*     */   
/*     */   public static void unsubscribe(CopyOnWriteArrayList<LogRecord> subscriber) {
/* 121 */     if (!ROOT_LOGGER.subscribers.contains(subscriber)) {
/*     */       return;
/*     */     }
/* 124 */     ROOT_LOGGER.subscribers.remove(subscriber);
/*     */   }
/*     */   
/*     */   @Nonnull
/*     */   public HytaleLoggerBackend getSubLogger(String name) {
/* 129 */     HytaleLoggerBackend hytaleLoggerBackend = new HytaleLoggerBackend(this.name + "][" + this.name, this);
/* 130 */     hytaleLoggerBackend.loadLogLevel();
/* 131 */     return hytaleLoggerBackend;
/*     */   }
/*     */   
/*     */   public void setSentryClient(@Nullable IScopes scope) {
/* 135 */     if (scope != null) {
/* 136 */       this.sentryHandler = new HytaleSentryHandler(scope);
/* 137 */       this.sentryHandler.setLevel(Level.ALL);
/*     */     } else {
/* 139 */       this.sentryHandler = null;
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setPropagatesSentryToParent(boolean propagate) {
/* 152 */     this.propagateSentryToParent = propagate;
/*     */   }
/*     */   
/*     */   public void setOnLevelChange(BiConsumer<Level, Level> onLevelChange) {
/* 156 */     this.onLevelChange = onLevelChange;
/*     */   }
/*     */   
/*     */   public void setLevel(@Nonnull Level newLevel) {
/* 160 */     Level old = this.level;
/* 161 */     this.level = newLevel;
/* 162 */     if (this.onLevelChange != null && !Objects.equals(old, newLevel)) {
/* 163 */       this.onLevelChange.accept(old, newLevel);
/*     */     }
/*     */   }
/*     */   
/*     */   public void loadLogLevel() {
/* 168 */     if (this.name == null || LOG_LEVEL_LOADER == null)
/* 169 */       return;  Level level = LOG_LEVEL_LOADER.apply(this.name);
/* 170 */     if (level != null) setLevel(level); 
/*     */   }
/*     */   
/*     */   public static void loadLevels(@Nonnull List<Map.Entry<String, Level>> list) {
/* 174 */     for (Map.Entry<String, Level> e : list) getLogger(e.getKey()).setLevel(e.getValue()); 
/*     */   }
/*     */   
/*     */   public static void reloadLogLevels() {
/* 178 */     CACHE.values().forEach(HytaleLoggerBackend::loadLogLevel);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static HytaleLoggerBackend getLogger() {
/* 188 */     return ROOT_LOGGER;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static HytaleLoggerBackend getLogger(@Nonnull String name) {
/* 199 */     if (name.isEmpty()) return getLogger(); 
/* 200 */     HytaleLoggerBackend logger = CACHE.computeIfAbsent(name, HytaleLoggerBackend::new);
/* 201 */     logger.loadLogLevel();
/* 202 */     return logger;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Nonnull
/*     */   public static HytaleLoggerBackend getLogger(String name, BiConsumer<Level, Level> onLevelChange) {
/* 216 */     HytaleLoggerBackend logger = CACHE.computeIfAbsent(name, HytaleLoggerBackend::new);
/* 217 */     logger.setOnLevelChange(onLevelChange);
/* 218 */     logger.loadLogLevel();
/* 219 */     return logger;
/*     */   }
/*     */   
/*     */   public static void setIndent(int indent) {
/* 223 */     (HytaleConsole.INSTANCE.getFormatter()).maxModuleName = indent;
/* 224 */     FileHandler fileHandler = HytaleFileHandler.INSTANCE.getFileHandler();
/* 225 */     if (fileHandler != null) ((HytaleLogFormatter)fileHandler.getFormatter()).maxModuleName = indent; 
/*     */   }
/*     */   
/*     */   public static boolean isJunitTest() {
/* 229 */     for (StackTraceElement element : Thread.currentThread().getStackTrace()) {
/* 230 */       if (element.getClassName().startsWith("org.junit.")) {
/* 231 */         return true;
/*     */       }
/*     */     } 
/* 234 */     return false;
/*     */   }
/*     */   
/*     */   public static void rawLog(String message) {
/* 238 */     ROOT_LOGGER.log(new RawLogRecord(Level.ALL, message));
/*     */   }
/*     */   
/*     */   public static class RawLogRecord extends LogRecord {
/*     */     public RawLogRecord(@Nonnull Level level, String msg) {
/* 243 */       super(level, msg);
/*     */     }
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\logger\backend\HytaleLoggerBackend.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */