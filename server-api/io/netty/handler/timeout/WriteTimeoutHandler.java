/*     */ package io.netty.handler.timeout;
/*     */ 
/*     */ import io.netty.channel.ChannelFuture;
/*     */ import io.netty.channel.ChannelFutureListener;
/*     */ import io.netty.channel.ChannelHandlerContext;
/*     */ import io.netty.channel.ChannelOutboundHandlerAdapter;
/*     */ import io.netty.channel.ChannelPromise;
/*     */ import io.netty.util.concurrent.Future;
/*     */ import io.netty.util.concurrent.GenericFutureListener;
/*     */ import io.netty.util.internal.ObjectUtil;
/*     */ import java.util.concurrent.TimeUnit;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class WriteTimeoutHandler
/*     */   extends ChannelOutboundHandlerAdapter
/*     */ {
/*  67 */   private static final long MIN_TIMEOUT_NANOS = TimeUnit.MILLISECONDS.toNanos(1L);
/*     */ 
/*     */ 
/*     */   
/*     */   private final long timeoutNanos;
/*     */ 
/*     */ 
/*     */   
/*     */   private WriteTimeoutTask lastTask;
/*     */ 
/*     */ 
/*     */   
/*     */   private boolean closed;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public WriteTimeoutHandler(int timeoutSeconds) {
/*  85 */     this(timeoutSeconds, TimeUnit.SECONDS);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public WriteTimeoutHandler(long timeout, TimeUnit unit) {
/*  97 */     ObjectUtil.checkNotNull(unit, "unit");
/*     */     
/*  99 */     if (timeout <= 0L) {
/* 100 */       this.timeoutNanos = 0L;
/*     */     } else {
/* 102 */       this.timeoutNanos = Math.max(unit.toNanos(timeout), MIN_TIMEOUT_NANOS);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public void write(ChannelHandlerContext ctx, Object msg, ChannelPromise promise) throws Exception {
/* 108 */     if (this.timeoutNanos > 0L) {
/* 109 */       promise = promise.unvoid();
/* 110 */       scheduleTimeout(ctx, promise);
/*     */     } 
/* 112 */     ctx.write(msg, promise);
/*     */   }
/*     */ 
/*     */   
/*     */   public void handlerRemoved(ChannelHandlerContext ctx) throws Exception {
/* 117 */     assert ctx.executor().inEventLoop();
/* 118 */     WriteTimeoutTask task = this.lastTask;
/* 119 */     this.lastTask = null;
/* 120 */     while (task != null) {
/* 121 */       assert task.ctx.executor().inEventLoop();
/* 122 */       task.scheduledFuture.cancel(false);
/* 123 */       WriteTimeoutTask prev = task.prev;
/* 124 */       task.prev = null;
/* 125 */       task.next = null;
/* 126 */       task = prev;
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   private void scheduleTimeout(ChannelHandlerContext ctx, ChannelPromise promise) {
/* 132 */     WriteTimeoutTask task = new WriteTimeoutTask(ctx, promise);
/* 133 */     task.scheduledFuture = (Future<?>)ctx.executor().schedule(task, this.timeoutNanos, TimeUnit.NANOSECONDS);
/*     */     
/* 135 */     if (!task.scheduledFuture.isDone()) {
/* 136 */       addWriteTimeoutTask(task);
/*     */ 
/*     */       
/* 139 */       promise.addListener((GenericFutureListener)task);
/*     */     } 
/*     */   }
/*     */   
/*     */   private void addWriteTimeoutTask(WriteTimeoutTask task) {
/* 144 */     assert task.ctx.executor().inEventLoop();
/* 145 */     if (this.lastTask != null) {
/* 146 */       this.lastTask.next = task;
/* 147 */       task.prev = this.lastTask;
/*     */     } 
/* 149 */     this.lastTask = task;
/*     */   }
/*     */   
/*     */   private void removeWriteTimeoutTask(WriteTimeoutTask task) {
/* 153 */     assert task.ctx.executor().inEventLoop();
/* 154 */     if (task == this.lastTask) {
/*     */       
/* 156 */       assert task.next == null;
/* 157 */       this.lastTask = this.lastTask.prev;
/* 158 */       if (this.lastTask != null)
/* 159 */         this.lastTask.next = null; 
/*     */     } else {
/* 161 */       if (task.prev == null && task.next == null) {
/*     */         return;
/*     */       }
/* 164 */       if (task.prev == null) {
/*     */         
/* 166 */         task.next.prev = null;
/*     */       } else {
/* 168 */         task.prev.next = task.next;
/* 169 */         task.next.prev = task.prev;
/*     */       } 
/* 171 */     }  task.prev = null;
/* 172 */     task.next = null;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void writeTimedOut(ChannelHandlerContext ctx) throws Exception {
/* 179 */     if (!this.closed) {
/* 180 */       ctx.fireExceptionCaught((Throwable)WriteTimeoutException.INSTANCE);
/* 181 */       ctx.close();
/* 182 */       this.closed = true;
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   private final class WriteTimeoutTask
/*     */     implements Runnable, ChannelFutureListener
/*     */   {
/*     */     private final ChannelHandlerContext ctx;
/*     */     
/*     */     private final ChannelPromise promise;
/*     */     WriteTimeoutTask prev;
/*     */     WriteTimeoutTask next;
/*     */     Future<?> scheduledFuture;
/*     */     
/*     */     WriteTimeoutTask(ChannelHandlerContext ctx, ChannelPromise promise) {
/* 198 */       this.ctx = ctx;
/* 199 */       this.promise = promise;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public void run() {
/* 207 */       if (!this.promise.isDone()) {
/*     */         try {
/* 209 */           WriteTimeoutHandler.this.writeTimedOut(this.ctx);
/* 210 */         } catch (Throwable t) {
/* 211 */           this.ctx.fireExceptionCaught(t);
/*     */         } 
/*     */       }
/* 214 */       WriteTimeoutHandler.this.removeWriteTimeoutTask(this);
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     public void operationComplete(ChannelFuture future) throws Exception {
/* 220 */       this.scheduledFuture.cancel(false);
/*     */ 
/*     */ 
/*     */       
/* 224 */       if (this.ctx.executor().inEventLoop()) {
/* 225 */         WriteTimeoutHandler.this.removeWriteTimeoutTask(this);
/*     */       
/*     */       }
/*     */       else {
/*     */ 
/*     */         
/* 231 */         assert this.promise.isDone();
/* 232 */         this.ctx.executor().execute(this);
/*     */       } 
/*     */     }
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\io\netty\handler\timeout\WriteTimeoutHandler.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */