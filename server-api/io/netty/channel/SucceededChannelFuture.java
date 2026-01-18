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
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ final class SucceededChannelFuture
/*    */   extends CompleteChannelFuture
/*    */ {
/*    */   SucceededChannelFuture(Channel channel, EventExecutor executor) {
/* 33 */     super(channel, executor);
/*    */   }
/*    */ 
/*    */   
/*    */   public Throwable cause() {
/* 38 */     return null;
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean isSuccess() {
/* 43 */     return true;
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\io\netty\channel\SucceededChannelFuture.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */