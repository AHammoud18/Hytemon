/*    */ package io.netty.channel;
/*    */ 
/*    */ import io.netty.util.concurrent.EventExecutor;
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
/*    */ final class DefaultChannelHandlerContext
/*    */   extends AbstractChannelHandlerContext
/*    */ {
/*    */   private final ChannelHandler handler;
/*    */   
/*    */   DefaultChannelHandlerContext(DefaultChannelPipeline pipeline, EventExecutor executor, String name, ChannelHandler handler) {
/* 26 */     super(pipeline, executor, name, (Class)handler.getClass());
/* 27 */     this.handler = handler;
/*    */   }
/*    */ 
/*    */   
/*    */   public ChannelHandler handler() {
/* 32 */     return this.handler;
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\io\netty\channel\DefaultChannelHandlerContext.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */