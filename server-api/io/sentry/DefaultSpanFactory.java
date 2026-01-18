/*    */ package io.sentry;
/*    */ 
/*    */ import org.jetbrains.annotations.ApiStatus.Internal;
/*    */ import org.jetbrains.annotations.NotNull;
/*    */ import org.jetbrains.annotations.Nullable;
/*    */ 
/*    */ 
/*    */ 
/*    */ @Internal
/*    */ public final class DefaultSpanFactory
/*    */   implements ISpanFactory
/*    */ {
/*    */   @NotNull
/*    */   public ITransaction createTransaction(@NotNull TransactionContext context, @NotNull IScopes scopes, @NotNull TransactionOptions transactionOptions, @Nullable CompositePerformanceCollector compositePerformanceCollector) {
/* 15 */     return new SentryTracer(context, scopes, transactionOptions, compositePerformanceCollector);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   @NotNull
/*    */   public ISpan createSpan(@NotNull IScopes scopes, @NotNull SpanOptions spanOptions, @NotNull SpanContext spanContext, @Nullable ISpan parentSpan) {
/* 30 */     return NoOpSpan.getInstance();
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\io\sentry\DefaultSpanFactory.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */