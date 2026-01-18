/*     */ package com.google.common.flogger.backend;
/*     */ 
/*     */ import com.google.common.flogger.FluentLogger;
/*     */ import com.google.common.flogger.StackSize;
/*     */ import com.google.common.flogger.context.ContextDataProvider;
/*     */ import com.google.common.flogger.context.LogLevelMap;
/*     */ import com.google.common.flogger.context.ScopedLoggingContext;
/*     */ import com.google.common.flogger.context.Tags;
/*     */ import java.util.concurrent.atomic.AtomicBoolean;
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
/*     */ public final class NoOpContextDataProvider
/*     */   extends ContextDataProvider
/*     */ {
/*  34 */   private static final ContextDataProvider NO_OP_INSTANCE = new NoOpContextDataProvider();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static final ContextDataProvider getInstance() {
/*  42 */     return NO_OP_INSTANCE;
/*     */   }
/*     */   
/*     */   private static final class NoOpScopedLoggingContext
/*     */     extends ScopedLoggingContext
/*     */     implements ScopedLoggingContext.LoggingContextCloseable
/*     */   {
/*     */     private static final class LazyLogger
/*     */     {
/*  51 */       private static final FluentLogger logger = FluentLogger.forEnclosingClass();
/*     */     }
/*  53 */     private final AtomicBoolean haveWarned = new AtomicBoolean();
/*     */     
/*     */     private void logWarningOnceOnly() {
/*  56 */       if (this.haveWarned.compareAndSet(false, true)) {
/*  57 */         ((FluentLogger.Api)((FluentLogger.Api)LazyLogger.logger
/*  58 */           .atWarning())
/*  59 */           .withStackTrace(StackSize.SMALL))
/*  60 */           .log("Scoped logging contexts are disabled; no context data provider was installed.\nTo enable scoped logging contexts in your application, see the site-specific Platform class used to configure logging behaviour.\nDefault Platform: com.google.common.flogger.backend.system.DefaultPlatform");
/*     */       }
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public ScopedLoggingContext.Builder newContext() {
/*  70 */       return new ScopedLoggingContext.Builder()
/*     */         {
/*     */           public ScopedLoggingContext.LoggingContextCloseable install() {
/*  73 */             NoOpContextDataProvider.NoOpScopedLoggingContext.this.logWarningOnceOnly();
/*  74 */             return NoOpContextDataProvider.NoOpScopedLoggingContext.this;
/*     */           }
/*     */         };
/*     */     }
/*     */ 
/*     */     
/*     */     public boolean addTags(Tags tags) {
/*  81 */       logWarningOnceOnly();
/*  82 */       return false;
/*     */     }
/*     */ 
/*     */     
/*     */     public boolean applyLogLevelMap(LogLevelMap m) {
/*  87 */       logWarningOnceOnly();
/*  88 */       return false;
/*     */     }
/*     */     
/*     */     public void close() {}
/*     */     
/*     */     private NoOpScopedLoggingContext() {} }
/*     */   
/*  95 */   private final ScopedLoggingContext noOpContext = new NoOpScopedLoggingContext();
/*     */ 
/*     */   
/*     */   public ScopedLoggingContext getContextApiSingleton() {
/*  99 */     return this.noOpContext;
/*     */   }
/*     */ 
/*     */   
/*     */   public String toString() {
/* 104 */     return "No-op Provider";
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\google\common\flogger\backend\NoOpContextDataProvider.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */