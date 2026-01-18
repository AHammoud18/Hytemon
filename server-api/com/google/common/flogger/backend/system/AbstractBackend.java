/*     */ package com.google.common.flogger.backend.system;
/*     */ 
/*     */ import com.google.common.flogger.backend.LoggerBackend;
/*     */ import java.util.logging.Filter;
/*     */ import java.util.logging.Handler;
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
/*     */ public abstract class AbstractBackend
/*     */   extends LoggerBackend
/*     */ {
/*     */   private static volatile boolean cannotUseForcingLogger = false;
/*     */   private final Logger logger;
/*     */   
/*     */   AbstractBackend(Logger logger) {
/*  42 */     this.logger = logger;
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
/*     */   
/*     */   protected AbstractBackend(String loggingClass) {
/*  56 */     this(Logger.getLogger(loggingClass.replace('$', '.')));
/*     */   }
/*     */ 
/*     */   
/*     */   public final String getLoggerName() {
/*  61 */     return this.logger.getName();
/*     */   }
/*     */ 
/*     */   
/*     */   public final boolean isLoggable(Level lvl) {
/*  66 */     return this.logger.isLoggable(lvl);
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
/*     */   public final void log(LogRecord record, boolean wasForced) {
/*  98 */     if (!wasForced || this.logger.isLoggable(record.getLevel())) {
/*     */ 
/*     */       
/* 101 */       this.logger.log(record);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     }
/*     */     else {
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 114 */       Filter filter = this.logger.getFilter();
/* 115 */       if (filter != null) {
/* 116 */         filter.isLoggable(record);
/*     */       }
/* 118 */       if (this.logger.getClass() == Logger.class || cannotUseForcingLogger) {
/*     */ 
/*     */ 
/*     */         
/* 122 */         publish(this.logger, record);
/*     */       }
/*     */       else {
/*     */         
/* 126 */         forceLoggingViaChildLogger(record);
/*     */       } 
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
/*     */   private static void publish(Logger logger, LogRecord record) {
/* 153 */     for (Handler handler : logger.getHandlers()) {
/* 154 */       handler.publish(record);
/*     */     }
/* 156 */     if (logger.getUseParentHandlers()) {
/* 157 */       logger = logger.getParent();
/* 158 */       if (logger != null) {
/* 159 */         publish(logger, record);
/*     */       }
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   void forceLoggingViaChildLogger(LogRecord record) {
/* 169 */     Logger forcingLogger = getForcingLogger(this.logger);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     try {
/* 176 */       forcingLogger.setLevel(Level.ALL);
/* 177 */     } catch (SecurityException e) {
/*     */ 
/*     */ 
/*     */       
/* 181 */       cannotUseForcingLogger = true;
/*     */       
/* 183 */       Logger.getLogger("")
/* 184 */         .log(Level.SEVERE, "Forcing log statements with Flogger has been partially disabled.\nThe Flogger library cannot modify logger log levels, which is necessary to force log statements. This is likely due to an installed SecurityManager.\nForced log statements will still be published directly to log handlers, but will not be visible to the 'log(LogRecord)' method of Logger subclasses.\n");
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 191 */       publish(this.logger, record);
/*     */       
/*     */       return;
/*     */     } 
/* 195 */     forcingLogger.log(record);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   Logger getForcingLogger(Logger parent) {
/* 202 */     return Logger.getLogger(parent.getName() + ".__forced__");
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\google\common\flogger\backend\system\AbstractBackend.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */