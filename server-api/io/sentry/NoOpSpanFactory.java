/*    */ package io.sentry;
/*    */ 
/*    */ import org.jetbrains.annotations.ApiStatus.Internal;
/*    */ import org.jetbrains.annotations.NotNull;
/*    */ import org.jetbrains.annotations.Nullable;
/*    */ 
/*    */ @Internal
/*    */ public final class NoOpSpanFactory
/*    */   implements ISpanFactory {
/* 10 */   private static final NoOpSpanFactory instance = new NoOpSpanFactory();
/*    */ 
/*    */ 
/*    */   
/*    */   public static NoOpSpanFactory getInstance() {
/* 15 */     return instance;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   @NotNull
/*    */   public ITransaction createTransaction(@NotNull TransactionContext context, @NotNull IScopes scopes, @NotNull TransactionOptions transactionOptions, @Nullable CompositePerformanceCollector compositePerformanceCollector) {
/* 24 */     return NoOpTransaction.getInstance();
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   @NotNull
/*    */   public ISpan createSpan(@NotNull IScopes scopes, @NotNull SpanOptions spanOptions, @NotNull SpanContext spanContext, @Nullable ISpan parentSpan) {
/* 33 */     return NoOpSpan.getInstance();
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\io\sentry\NoOpSpanFactory.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */