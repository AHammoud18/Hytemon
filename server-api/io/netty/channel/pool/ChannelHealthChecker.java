/*    */ package io.netty.channel.pool;
/*    */ 
/*    */ import io.netty.channel.Channel;
/*    */ import io.netty.channel.EventLoop;
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
/*    */ public interface ChannelHealthChecker
/*    */ {
/* 32 */   public static final ChannelHealthChecker ACTIVE = new ChannelHealthChecker()
/*    */     {
/*    */       public Future<Boolean> isHealthy(Channel channel) {
/* 35 */         EventLoop loop = channel.eventLoop();
/* 36 */         return channel.isActive() ? loop.newSucceededFuture(Boolean.TRUE) : loop.newSucceededFuture(Boolean.FALSE);
/*    */       }
/*    */     };
/*    */   
/*    */   Future<Boolean> isHealthy(Channel paramChannel);
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\io\netty\channel\pool\ChannelHealthChecker.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */