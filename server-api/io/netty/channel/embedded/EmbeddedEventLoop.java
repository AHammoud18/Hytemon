/*     */ package io.netty.channel.embedded;
/*     */ 
/*     */ import io.netty.channel.Channel;
/*     */ import io.netty.channel.ChannelFuture;
/*     */ import io.netty.channel.ChannelPromise;
/*     */ import io.netty.channel.DefaultChannelPromise;
/*     */ import io.netty.channel.EventLoop;
/*     */ import io.netty.channel.EventLoopGroup;
/*     */ import io.netty.util.concurrent.AbstractScheduledEventExecutor;
/*     */ import io.netty.util.concurrent.EventExecutor;
/*     */ import io.netty.util.concurrent.EventExecutorGroup;
/*     */ import io.netty.util.concurrent.Future;
/*     */ import io.netty.util.concurrent.MockTicker;
/*     */ import io.netty.util.concurrent.Ticker;
/*     */ import io.netty.util.internal.ObjectUtil;
/*     */ import java.util.ArrayDeque;
/*     */ import java.util.Queue;
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
/*     */ final class EmbeddedEventLoop
/*     */   extends AbstractScheduledEventExecutor
/*     */   implements EventLoop
/*     */ {
/*     */   private final Ticker ticker;
/*  37 */   private final Queue<Runnable> tasks = new ArrayDeque<>(2);
/*     */   
/*     */   EmbeddedEventLoop(Ticker ticker) {
/*  40 */     this.ticker = ticker;
/*     */   }
/*     */ 
/*     */   
/*     */   public EventLoopGroup parent() {
/*  45 */     return (EventLoopGroup)super.parent();
/*     */   }
/*     */ 
/*     */   
/*     */   public EventLoop next() {
/*  50 */     return (EventLoop)super.next();
/*     */   }
/*     */ 
/*     */   
/*     */   public void execute(Runnable command) {
/*  55 */     this.tasks.add((Runnable)ObjectUtil.checkNotNull(command, "command"));
/*     */   }
/*     */   
/*     */   void runTasks() {
/*     */     while (true) {
/*  60 */       Runnable task = this.tasks.poll();
/*  61 */       if (task == null) {
/*     */         break;
/*     */       }
/*     */       
/*  65 */       task.run();
/*     */     } 
/*     */   }
/*     */   
/*     */   boolean hasPendingNormalTasks() {
/*  70 */     return !this.tasks.isEmpty();
/*     */   }
/*     */   
/*     */   long runScheduledTasks() {
/*  74 */     long time = getCurrentTimeNanos();
/*     */     while (true) {
/*  76 */       Runnable task = pollScheduledTask(time);
/*  77 */       if (task == null) {
/*  78 */         return nextScheduledTaskNano();
/*     */       }
/*     */       
/*  81 */       task.run();
/*     */     } 
/*     */   }
/*     */   
/*     */   long nextScheduledTask() {
/*  86 */     return nextScheduledTaskNano();
/*     */   }
/*     */ 
/*     */   
/*     */   public Ticker ticker() {
/*  91 */     return this.ticker;
/*     */   }
/*     */ 
/*     */   
/*     */   protected long getCurrentTimeNanos() {
/*  96 */     return this.ticker.nanoTime();
/*     */   }
/*     */ 
/*     */   
/*     */   protected void cancelScheduledTasks() {
/* 101 */     super.cancelScheduledTasks();
/*     */   }
/*     */ 
/*     */   
/*     */   public Future<?> shutdownGracefully(long quietPeriod, long timeout, TimeUnit unit) {
/* 106 */     throw new UnsupportedOperationException();
/*     */   }
/*     */ 
/*     */   
/*     */   public Future<?> terminationFuture() {
/* 111 */     throw new UnsupportedOperationException();
/*     */   }
/*     */ 
/*     */   
/*     */   @Deprecated
/*     */   public void shutdown() {
/* 117 */     throw new UnsupportedOperationException();
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isShuttingDown() {
/* 122 */     return false;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isShutdown() {
/* 127 */     return false;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isTerminated() {
/* 132 */     return false;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean awaitTermination(long timeout, TimeUnit unit) {
/* 137 */     return false;
/*     */   }
/*     */ 
/*     */   
/*     */   public ChannelFuture register(Channel channel) {
/* 142 */     return register((ChannelPromise)new DefaultChannelPromise(channel, (EventExecutor)this));
/*     */   }
/*     */ 
/*     */   
/*     */   public ChannelFuture register(ChannelPromise promise) {
/* 147 */     ObjectUtil.checkNotNull(promise, "promise");
/* 148 */     promise.channel().unsafe().register(this, promise);
/* 149 */     return (ChannelFuture)promise;
/*     */   }
/*     */ 
/*     */   
/*     */   @Deprecated
/*     */   public ChannelFuture register(Channel channel, ChannelPromise promise) {
/* 155 */     channel.unsafe().register(this, promise);
/* 156 */     return (ChannelFuture)promise;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean inEventLoop() {
/* 161 */     return true;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean inEventLoop(Thread thread) {
/* 166 */     return true;
/*     */   }
/*     */ 
/*     */   
/*     */   static final class FreezableTicker
/*     */     implements MockTicker
/*     */   {
/* 173 */     private final Ticker unfrozen = Ticker.systemTicker();
/*     */ 
/*     */ 
/*     */     
/*     */     private long startTime;
/*     */ 
/*     */ 
/*     */     
/*     */     private long frozenTimestamp;
/*     */ 
/*     */ 
/*     */     
/*     */     private boolean timeFrozen;
/*     */ 
/*     */ 
/*     */     
/*     */     public void advance(long amount, TimeUnit unit) {
/* 190 */       long nanos = unit.toNanos(amount);
/* 191 */       if (this.timeFrozen) {
/* 192 */         this.frozenTimestamp += nanos;
/*     */       }
/*     */       else {
/*     */         
/* 196 */         this.startTime -= nanos;
/*     */       } 
/*     */     }
/*     */ 
/*     */     
/*     */     public long nanoTime() {
/* 202 */       if (this.timeFrozen) {
/* 203 */         return this.frozenTimestamp;
/*     */       }
/* 205 */       return this.unfrozen.nanoTime() - this.startTime;
/*     */     }
/*     */ 
/*     */     
/*     */     public void sleep(long delay, TimeUnit unit) throws InterruptedException {
/* 210 */       throw new UnsupportedOperationException("Sleeping is not supported by the default ticker for EmbeddedEventLoop. Please use a different ticker implementation if you require sleep support.");
/*     */     }
/*     */ 
/*     */     
/*     */     public void freezeTime() {
/* 215 */       if (!this.timeFrozen) {
/* 216 */         this.frozenTimestamp = nanoTime();
/* 217 */         this.timeFrozen = true;
/*     */       } 
/*     */     }
/*     */     
/*     */     public void unfreezeTime() {
/* 222 */       if (this.timeFrozen) {
/*     */ 
/*     */ 
/*     */         
/* 226 */         this.startTime = this.unfrozen.nanoTime() - this.frozenTimestamp;
/* 227 */         this.timeFrozen = false;
/*     */       } 
/*     */     }
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\io\netty\channel\embedded\EmbeddedEventLoop.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */