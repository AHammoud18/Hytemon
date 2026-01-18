/*    */ package io.netty.channel;
/*    */ 
/*    */ import io.netty.util.concurrent.Promise;
/*    */ import io.netty.util.concurrent.PromiseNotifier;
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
/*    */ public final class ChannelPromiseNotifier
/*    */   extends PromiseNotifier<Void, ChannelFuture>
/*    */   implements ChannelFutureListener
/*    */ {
/*    */   public ChannelPromiseNotifier(ChannelPromise... promises) {
/* 36 */     super((Promise[])promises);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public ChannelPromiseNotifier(boolean logNotifyFailure, ChannelPromise... promises) {
/* 46 */     super(logNotifyFailure, (Promise[])promises);
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\io\netty\channel\ChannelPromiseNotifier.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */