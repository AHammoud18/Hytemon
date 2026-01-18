/*    */ package io.netty.channel;
/*    */ 
/*    */ import io.netty.util.concurrent.PromiseAggregator;
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
/*    */ @Deprecated
/*    */ public final class ChannelPromiseAggregator
/*    */   extends PromiseAggregator<Void, ChannelFuture>
/*    */   implements ChannelFutureListener
/*    */ {
/*    */   public ChannelPromiseAggregator(ChannelPromise aggregatePromise) {
/* 35 */     super(aggregatePromise);
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\io\netty\channel\ChannelPromiseAggregator.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */