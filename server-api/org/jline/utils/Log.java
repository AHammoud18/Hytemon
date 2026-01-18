/*     */ package org.jline.utils;
/*     */ 
/*     */ import java.io.ByteArrayOutputStream;
/*     */ import java.io.PrintStream;
/*     */ import java.util.function.Supplier;
/*     */ import java.util.logging.Level;
/*     */ import java.util.logging.LogRecord;
/*     */ import java.util.logging.Logger;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public final class Log
/*     */ {
/*  65 */   private static final Logger logger = Logger.getLogger("org.jline");
/*     */   
/*     */   public static void trace(Object... messages) {
/*  68 */     log(Level.FINEST, messages);
/*     */   }
/*     */   
/*     */   public static void trace(Supplier<String> supplier) {
/*  72 */     log(Level.FINEST, supplier);
/*     */   }
/*     */   
/*     */   public static void debug(Supplier<String> supplier) {
/*  76 */     log(Level.FINE, supplier);
/*     */   }
/*     */   
/*     */   public static void debug(Object... messages) {
/*  80 */     log(Level.FINE, messages);
/*     */   }
/*     */   
/*     */   public static void info(Object... messages) {
/*  84 */     log(Level.INFO, messages);
/*     */   }
/*     */   
/*     */   public static void warn(Object... messages) {
/*  88 */     log(Level.WARNING, messages);
/*     */   }
/*     */   
/*     */   public static void error(Object... messages) {
/*  92 */     log(Level.SEVERE, messages);
/*     */   }
/*     */   
/*     */   public static boolean isDebugEnabled() {
/*  96 */     return isEnabled(Level.FINE);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   static void render(PrintStream out, Object message) {
/* 103 */     if (message != null && message.getClass().isArray()) {
/* 104 */       Object[] array = (Object[])message;
/*     */       
/* 106 */       out.print("[");
/* 107 */       for (int i = 0; i < array.length; i++) {
/* 108 */         out.print(array[i]);
/* 109 */         if (i + 1 < array.length) {
/* 110 */           out.print(",");
/*     */         }
/*     */       } 
/* 113 */       out.print("]");
/*     */     } else {
/* 115 */       out.print(message);
/*     */     } 
/*     */   }
/*     */   
/*     */   static LogRecord createRecord(Level level, Object... messages) {
/* 120 */     Throwable cause = null;
/* 121 */     ByteArrayOutputStream baos = new ByteArrayOutputStream();
/* 122 */     PrintStream ps = new PrintStream(baos);
/* 123 */     for (int i = 0; i < messages.length; i++) {
/*     */       
/* 125 */       if (i + 1 == messages.length && messages[i] instanceof Throwable) {
/* 126 */         cause = (Throwable)messages[i];
/*     */       } else {
/* 128 */         render(ps, messages[i]);
/*     */       } 
/*     */     } 
/* 131 */     ps.close();
/* 132 */     LogRecord r = new LogRecord(level, baos.toString());
/* 133 */     r.setThrown(cause);
/* 134 */     return r;
/*     */   }
/*     */   
/*     */   static LogRecord createRecord(Level level, Supplier<String> message) {
/* 138 */     return new LogRecord(level, message.get());
/*     */   }
/*     */   
/*     */   static void log(Level level, Supplier<String> message) {
/* 142 */     logr(level, () -> createRecord(level, message));
/*     */   }
/*     */   
/*     */   static void log(Level level, Object... messages) {
/* 146 */     logr(level, () -> createRecord(level, messages));
/*     */   }
/*     */   
/*     */   static void logr(Level level, Supplier<LogRecord> record) {
/* 150 */     if (logger.isLoggable(level)) {
/*     */       
/* 152 */       LogRecord tmp = record.get();
/* 153 */       tmp.setLoggerName(logger.getName());
/* 154 */       logger.log(tmp);
/*     */     } 
/*     */   }
/*     */   
/*     */   static boolean isEnabled(Level level) {
/* 159 */     return logger.isLoggable(level);
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\org\jlin\\utils\Log.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */