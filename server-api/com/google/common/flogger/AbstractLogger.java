/*     */ package com.google.common.flogger;
/*     */ 
/*     */ import com.google.common.flogger.backend.LogData;
/*     */ import com.google.common.flogger.backend.LoggerBackend;
/*     */ import com.google.common.flogger.backend.LoggingException;
/*     */ import com.google.common.flogger.util.Checks;
/*     */ import com.google.errorprone.annotations.CheckReturnValue;
/*     */ import java.util.logging.Level;
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
/*     */ @CheckReturnValue
/*     */ public abstract class AbstractLogger<API extends LoggingApi<API>>
/*     */ {
/*     */   private final LoggerBackend backend;
/*     */   
/*     */   protected AbstractLogger(LoggerBackend backend) {
/*  43 */     this.backend = (LoggerBackend)Checks.checkNotNull(backend, "backend");
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
/*     */   public abstract API at(Level paramLevel);
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
/*     */   public final API atSevere() {
/*  68 */     return at(Level.SEVERE);
/*     */   }
/*     */ 
/*     */   
/*     */   public final API atWarning() {
/*  73 */     return at(Level.WARNING);
/*     */   }
/*     */ 
/*     */   
/*     */   public final API atInfo() {
/*  78 */     return at(Level.INFO);
/*     */   }
/*     */ 
/*     */   
/*     */   public final API atConfig() {
/*  83 */     return at(Level.CONFIG);
/*     */   }
/*     */ 
/*     */   
/*     */   public final API atFine() {
/*  88 */     return at(Level.FINE);
/*     */   }
/*     */ 
/*     */   
/*     */   public final API atFiner() {
/*  93 */     return at(Level.FINER);
/*     */   }
/*     */ 
/*     */   
/*     */   public final API atFinest() {
/*  98 */     return at(Level.FINEST);
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
/*     */   protected String getName() {
/* 115 */     return this.backend.getLoggerName();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected final boolean isLoggable(Level level) {
/* 123 */     return this.backend.isLoggable(level);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   final LoggerBackend getBackend() {
/* 133 */     return this.backend;
/*     */   }
/*     */ 
/*     */   
/*     */   final void write(LogData data) {
/* 138 */     Checks.checkNotNull(data, "data");
/*     */     try {
/* 140 */       this.backend.log(data);
/* 141 */     } catch (RuntimeException error) {
/*     */       try {
/* 143 */         this.backend.handleError(error, data);
/* 144 */       } catch (LoggingException allowed) {
/*     */         
/* 146 */         throw allowed;
/* 147 */       } catch (RuntimeException runtimeException) {
/* 148 */         System.err.println("logging error: " + runtimeException.getMessage());
/* 149 */         runtimeException.printStackTrace(System.err);
/*     */       } 
/*     */     } 
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\google\common\flogger\AbstractLogger.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */