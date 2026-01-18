/*    */ package io.netty.handler.codec.compression;
/*    */ 
/*    */ import io.netty.channel.ChannelFuture;
/*    */ import io.netty.channel.ChannelFutureListener;
/*    */ import io.netty.channel.ChannelHandlerContext;
/*    */ import io.netty.channel.ChannelPromise;
/*    */ import io.netty.util.concurrent.Future;
/*    */ import io.netty.util.concurrent.GenericFutureListener;
/*    */ import io.netty.util.concurrent.ScheduledFuture;
/*    */ import java.util.concurrent.TimeUnit;
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
/*    */ final class EncoderUtil
/*    */ {
/*    */   private static final int THREAD_POOL_DELAY_SECONDS = 10;
/*    */   
/*    */   static void closeAfterFinishEncode(final ChannelHandlerContext ctx, ChannelFuture finishFuture, final ChannelPromise promise) {
/* 31 */     if (!finishFuture.isDone()) {
/*    */       
/* 33 */       final ScheduledFuture future = ctx.executor().schedule(new Runnable()
/*    */           {
/*    */             public void run() {
/* 36 */               ctx.close(promise);
/*    */             }
/*    */           },  10L, TimeUnit.SECONDS);
/*    */       
/* 40 */       finishFuture.addListener((GenericFutureListener)new ChannelFutureListener()
/*    */           {
/*    */             public void operationComplete(ChannelFuture f)
/*    */             {
/* 44 */               future.cancel(true);
/* 45 */               if (!promise.isDone()) {
/* 46 */                 ctx.close(promise);
/*    */               }
/*    */             }
/*    */           });
/*    */     } else {
/* 51 */       ctx.close(promise);
/*    */     } 
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\io\netty\handler\codec\compression\EncoderUtil.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */