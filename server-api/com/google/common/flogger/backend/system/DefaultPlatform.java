/*     */ package com.google.common.flogger.backend.system;
/*     */ 
/*     */ import com.google.common.flogger.backend.LoggerBackend;
/*     */ import com.google.common.flogger.backend.NoOpContextDataProvider;
/*     */ import com.google.common.flogger.backend.Platform;
/*     */ import com.google.common.flogger.context.ContextDataProvider;
/*     */ import com.google.common.flogger.util.StaticMethodCaller;
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
/*     */ public class DefaultPlatform
/*     */   extends Platform
/*     */ {
/*     */   private static final String BACKEND_FACTORY = "flogger.backend_factory";
/*     */   private static final String LOGGING_CONTEXT = "flogger.logging_context";
/*     */   private static final String CLOCK = "flogger.clock";
/*     */   private final BackendFactory backendFactory;
/*     */   private final ContextDataProvider context;
/*     */   private final Clock clock;
/*     */   private final Platform.LogCallerFinder callerFinder;
/*     */   
/*     */   public DefaultPlatform() {
/*  68 */     BackendFactory factory = (BackendFactory)StaticMethodCaller.callGetterFromSystemProperty("flogger.backend_factory", BackendFactory.class);
/*  69 */     this.backendFactory = (factory != null) ? factory : SimpleBackendFactory.getInstance();
/*     */     
/*  71 */     ContextDataProvider context = (ContextDataProvider)StaticMethodCaller.callGetterFromSystemProperty("flogger.logging_context", ContextDataProvider.class);
/*  72 */     this.context = (context != null) ? context : NoOpContextDataProvider.getInstance();
/*  73 */     Clock clock = (Clock)StaticMethodCaller.callGetterFromSystemProperty("flogger.clock", Clock.class);
/*  74 */     this.clock = (clock != null) ? clock : SystemClock.getInstance();
/*     */     
/*  76 */     this.callerFinder = StackBasedCallerFinder.getInstance();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   DefaultPlatform(BackendFactory factory, ContextDataProvider context, Clock clock, Platform.LogCallerFinder callerFinder) {
/*  85 */     this.backendFactory = factory;
/*  86 */     this.context = context;
/*  87 */     this.clock = clock;
/*  88 */     this.callerFinder = callerFinder;
/*     */   }
/*     */ 
/*     */   
/*     */   protected Platform.LogCallerFinder getCallerFinderImpl() {
/*  93 */     return this.callerFinder;
/*     */   }
/*     */ 
/*     */   
/*     */   protected LoggerBackend getBackendImpl(String className) {
/*  98 */     return this.backendFactory.create(className);
/*     */   }
/*     */ 
/*     */   
/*     */   protected ContextDataProvider getContextDataProviderImpl() {
/* 103 */     return this.context;
/*     */   }
/*     */ 
/*     */   
/*     */   protected long getCurrentTimeNanosImpl() {
/* 108 */     return this.clock.getCurrentTimeNanos();
/*     */   }
/*     */ 
/*     */   
/*     */   protected String getConfigInfoImpl() {
/* 113 */     return "Platform: " + getClass().getName() + "\nBackendFactory: " + this.backendFactory + "\nClock: " + this.clock + "\nLoggingContext: " + this.context + "\nLogCallerFinder: " + this.callerFinder + "\n";
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\google\common\flogger\backend\system\DefaultPlatform.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */