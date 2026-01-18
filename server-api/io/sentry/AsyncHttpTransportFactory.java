/*    */ package io.sentry;
/*    */ 
/*    */ import io.sentry.transport.AsyncHttpTransport;
/*    */ import io.sentry.transport.ITransport;
/*    */ import io.sentry.transport.RateLimiter;
/*    */ import io.sentry.util.Objects;
/*    */ import org.jetbrains.annotations.ApiStatus.Internal;
/*    */ import org.jetbrains.annotations.NotNull;
/*    */ 
/*    */ 
/*    */ @Internal
/*    */ public final class AsyncHttpTransportFactory
/*    */   implements ITransportFactory
/*    */ {
/*    */   @NotNull
/*    */   public ITransport create(@NotNull SentryOptions options, @NotNull RequestDetails requestDetails) {
/* 17 */     Objects.requireNonNull(options, "options is required");
/* 18 */     Objects.requireNonNull(requestDetails, "requestDetails is required");
/*    */     
/* 20 */     return (ITransport)new AsyncHttpTransport(options, new RateLimiter(options), options
/* 21 */         .getTransportGate(), requestDetails);
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\io\sentry\AsyncHttpTransportFactory.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */