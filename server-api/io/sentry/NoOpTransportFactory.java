/*    */ package io.sentry;
/*    */ 
/*    */ import io.sentry.transport.ITransport;
/*    */ import io.sentry.transport.NoOpTransport;
/*    */ import org.jetbrains.annotations.ApiStatus.Internal;
/*    */ import org.jetbrains.annotations.NotNull;
/*    */ 
/*    */ @Internal
/*    */ public final class NoOpTransportFactory
/*    */   implements ITransportFactory {
/* 11 */   private static final NoOpTransportFactory instance = new NoOpTransportFactory();
/*    */   
/*    */   public static NoOpTransportFactory getInstance() {
/* 14 */     return instance;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   @NotNull
/*    */   public ITransport create(@NotNull SentryOptions options, @NotNull RequestDetails requestDetails) {
/* 22 */     return (ITransport)NoOpTransport.getInstance();
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\io\sentry\NoOpTransportFactory.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */