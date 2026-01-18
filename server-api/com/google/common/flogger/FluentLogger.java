/*     */ package com.google.common.flogger;
/*     */ 
/*     */ import com.google.common.flogger.backend.LoggerBackend;
/*     */ import com.google.common.flogger.backend.Platform;
/*     */ import com.google.common.flogger.parser.DefaultPrintfMessageParser;
/*     */ import com.google.common.flogger.parser.MessageParser;
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
/*     */ public final class FluentLogger
/*     */   extends AbstractLogger<FluentLogger.Api>
/*     */ {
/*     */   public static interface Api
/*     */     extends LoggingApi<Api> {}
/*     */   
/*     */   private static final class NoOp
/*     */     extends LoggingApi.NoOp<Api>
/*     */     implements Api
/*     */   {
/*     */     private NoOp() {}
/*     */   }
/*     */   
/*  61 */   static final NoOp NO_OP = new NoOp();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static FluentLogger forEnclosingClass() {
/*  70 */     String loggingClass = Platform.getCallerFinder().findLoggingClass(FluentLogger.class);
/*  71 */     return new FluentLogger(Platform.getBackend(loggingClass));
/*     */   }
/*     */ 
/*     */   
/*     */   FluentLogger(LoggerBackend backend) {
/*  76 */     super(backend);
/*     */   }
/*     */ 
/*     */   
/*     */   public Api at(Level level) {
/*  81 */     boolean isLoggable = isLoggable(level);
/*  82 */     boolean isForced = Platform.shouldForceLogging(getName(), level, isLoggable);
/*  83 */     return (isLoggable || isForced) ? new Context(level, isForced) : NO_OP;
/*     */   }
/*     */   
/*     */   final class Context
/*     */     extends LogContext<FluentLogger, Api>
/*     */     implements Api {
/*     */     private Context(Level level, boolean isForced) {
/*  90 */       super(level, isForced);
/*     */     }
/*     */ 
/*     */     
/*     */     protected FluentLogger getLogger() {
/*  95 */       return FluentLogger.this;
/*     */     }
/*     */ 
/*     */     
/*     */     protected FluentLogger.Api api() {
/* 100 */       return this;
/*     */     }
/*     */ 
/*     */     
/*     */     protected FluentLogger.Api noOp() {
/* 105 */       return FluentLogger.NO_OP;
/*     */     }
/*     */ 
/*     */     
/*     */     protected MessageParser getMessageParser() {
/* 110 */       return (MessageParser)DefaultPrintfMessageParser.getInstance();
/*     */     }
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\google\common\flogger\FluentLogger.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */