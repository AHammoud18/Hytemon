/*    */ package io.netty.channel;
/*    */ 
/*    */ import io.netty.util.concurrent.EventExecutor;
/*    */ import io.netty.util.concurrent.Future;
/*    */ import io.netty.util.internal.ObjectUtil;
/*    */ import io.netty.util.internal.PlatformDependent;
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
/*    */ final class FailedChannelFuture
/*    */   extends CompleteChannelFuture
/*    */ {
/*    */   private final Throwable cause;
/*    */   
/*    */   FailedChannelFuture(Channel channel, EventExecutor executor, Throwable cause) {
/* 38 */     super(channel, executor);
/* 39 */     this.cause = (Throwable)ObjectUtil.checkNotNull(cause, "cause");
/*    */   }
/*    */ 
/*    */   
/*    */   public Throwable cause() {
/* 44 */     return this.cause;
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean isSuccess() {
/* 49 */     return false;
/*    */   }
/*    */ 
/*    */   
/*    */   public ChannelFuture sync() {
/* 54 */     PlatformDependent.throwException(this.cause);
/* 55 */     return this;
/*    */   }
/*    */ 
/*    */   
/*    */   public ChannelFuture syncUninterruptibly() {
/* 60 */     PlatformDependent.throwException(this.cause);
/* 61 */     return this;
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\io\netty\channel\FailedChannelFuture.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */