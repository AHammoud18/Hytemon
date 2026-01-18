/*    */ package io.netty.channel;
/*    */ 
/*    */ import io.netty.util.concurrent.EventExecutor;
/*    */ import io.netty.util.concurrent.Future;
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
/*    */ 
/*    */ 
/*    */ public interface IoEventLoopGroup
/*    */   extends EventLoopGroup
/*    */ {
/*    */   @Deprecated
/*    */   default ChannelFuture register(Channel channel) {
/* 34 */     return next().register(channel);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   @Deprecated
/*    */   default ChannelFuture register(ChannelPromise promise) {
/* 43 */     return next().register(promise);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   default Future<IoRegistration> register(IoHandle handle) {
/* 53 */     return next().register(handle);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   default boolean isCompatible(Class<? extends IoHandle> handleType) {
/* 64 */     return next().isCompatible(handleType);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   default boolean isIoType(Class<? extends IoHandler> handlerType) {
/* 75 */     return next().isIoType(handlerType);
/*    */   }
/*    */   
/*    */   IoEventLoop next();
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\io\netty\channel\IoEventLoopGroup.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */