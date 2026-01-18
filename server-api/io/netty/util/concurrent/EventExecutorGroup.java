/*     */ package io.netty.util.concurrent;
/*     */ 
/*     */ import java.util.Iterator;
/*     */ import java.util.List;
/*     */ import java.util.concurrent.Callable;
/*     */ import java.util.concurrent.Future;
/*     */ import java.util.concurrent.ScheduledExecutorService;
/*     */ import java.util.concurrent.ScheduledFuture;
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
/*     */ public interface EventExecutorGroup
/*     */   extends ScheduledExecutorService, Iterable<EventExecutor>
/*     */ {
/*     */   ScheduledFuture<?> scheduleWithFixedDelay(Runnable paramRunnable, long paramLong1, long paramLong2, TimeUnit paramTimeUnit);
/*     */   
/*     */   ScheduledFuture<?> scheduleAtFixedRate(Runnable paramRunnable, long paramLong1, long paramLong2, TimeUnit paramTimeUnit);
/*     */   
/*     */   <V> ScheduledFuture<V> schedule(Callable<V> paramCallable, long paramLong, TimeUnit paramTimeUnit);
/*     */   
/*     */   ScheduledFuture<?> schedule(Runnable paramRunnable, long paramLong, TimeUnit paramTimeUnit);
/*     */   
/*     */   default Ticker ticker() {
/* 108 */     return Ticker.systemTicker();
/*     */   }
/*     */   
/*     */   <T> Future<T> submit(Callable<T> paramCallable);
/*     */   
/*     */   <T> Future<T> submit(Runnable paramRunnable, T paramT);
/*     */   
/*     */   Future<?> submit(Runnable paramRunnable);
/*     */   
/*     */   Iterator<EventExecutor> iterator();
/*     */   
/*     */   EventExecutor next();
/*     */   
/*     */   @Deprecated
/*     */   List<Runnable> shutdownNow();
/*     */   
/*     */   @Deprecated
/*     */   void shutdown();
/*     */   
/*     */   Future<?> terminationFuture();
/*     */   
/*     */   Future<?> shutdownGracefully(long paramLong1, long paramLong2, TimeUnit paramTimeUnit);
/*     */   
/*     */   Future<?> shutdownGracefully();
/*     */   
/*     */   boolean isShuttingDown();
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\io\nett\\util\concurrent\EventExecutorGroup.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */