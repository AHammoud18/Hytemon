/*     */ package io.sentry;
/*     */ 
/*     */ import org.jetbrains.annotations.ApiStatus.Internal;
/*     */ import org.jetbrains.annotations.NotNull;
/*     */ import org.jetbrains.annotations.Nullable;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public final class TransactionOptions
/*     */   extends SpanOptions
/*     */ {
/*     */   @Internal
/*     */   public static final long DEFAULT_DEADLINE_TIMEOUT_AUTO_TRANSACTION = 30000L;
/*     */   @Nullable
/*  16 */   private CustomSamplingContext customSamplingContext = null;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private boolean isAppStartTransaction = false;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private boolean waitForChildren = false;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Nullable
/*  36 */   private Long idleTimeout = null;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Nullable
/*  46 */   private Long deadlineTimeout = null;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Nullable
/*  52 */   private TransactionFinishedCallback transactionFinishedCallback = null;
/*     */   @Internal
/*     */   @Nullable
/*  55 */   private ISpanFactory spanFactory = null;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Nullable
/*     */   public CustomSamplingContext getCustomSamplingContext() {
/*  63 */     return this.customSamplingContext;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setCustomSamplingContext(@Nullable CustomSamplingContext customSamplingContext) {
/*  72 */     this.customSamplingContext = customSamplingContext;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isBindToScope() {
/*  81 */     return (ScopeBindingMode.ON == getScopeBindingMode());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setBindToScope(boolean bindToScope) {
/*  90 */     setScopeBindingMode(bindToScope ? ScopeBindingMode.ON : ScopeBindingMode.OFF);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isWaitForChildren() {
/*  99 */     return this.waitForChildren;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setWaitForChildren(boolean waitForChildren) {
/* 108 */     this.waitForChildren = waitForChildren;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Nullable
/*     */   public Long getIdleTimeout() {
/* 117 */     return this.idleTimeout;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Internal
/*     */   public void setDeadlineTimeout(@Nullable Long deadlineTimeoutMs) {
/* 129 */     this.deadlineTimeout = deadlineTimeoutMs;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Internal
/*     */   @Nullable
/*     */   public Long getDeadlineTimeout() {
/* 139 */     return this.deadlineTimeout;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setIdleTimeout(@Nullable Long idleTimeout) {
/* 148 */     this.idleTimeout = idleTimeout;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Nullable
/*     */   public TransactionFinishedCallback getTransactionFinishedCallback() {
/* 157 */     return this.transactionFinishedCallback;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setTransactionFinishedCallback(@Nullable TransactionFinishedCallback transactionFinishedCallback) {
/* 167 */     this.transactionFinishedCallback = transactionFinishedCallback;
/*     */   }
/*     */   
/*     */   @Internal
/*     */   public void setAppStartTransaction(boolean appStartTransaction) {
/* 172 */     this.isAppStartTransaction = appStartTransaction;
/*     */   }
/*     */   
/*     */   @Internal
/*     */   public boolean isAppStartTransaction() {
/* 177 */     return this.isAppStartTransaction;
/*     */   }
/*     */   @Internal
/*     */   @Nullable
/*     */   public ISpanFactory getSpanFactory() {
/* 182 */     return this.spanFactory;
/*     */   }
/*     */   
/*     */   @Internal
/*     */   public void setSpanFactory(@NotNull ISpanFactory spanFactory) {
/* 187 */     this.spanFactory = spanFactory;
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\io\sentry\TransactionOptions.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */