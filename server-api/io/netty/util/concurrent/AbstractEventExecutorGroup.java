/*     */ package io.netty.util.concurrent;
/*     */ 
/*     */ import java.util.Collection;
/*     */ import java.util.Collections;
/*     */ import java.util.List;
/*     */ import java.util.concurrent.Callable;
/*     */ import java.util.concurrent.ExecutionException;
/*     */ import java.util.concurrent.Future;
/*     */ import java.util.concurrent.ScheduledFuture;
/*     */ import java.util.concurrent.TimeUnit;
/*     */ import java.util.concurrent.TimeoutException;
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
/*     */ public abstract class AbstractEventExecutorGroup
/*     */   implements EventExecutorGroup
/*     */ {
/*     */   public Future<?> submit(Runnable task) {
/*  35 */     return next().submit(task);
/*     */   }
/*     */ 
/*     */   
/*     */   public <T> Future<T> submit(Runnable task, T result) {
/*  40 */     return next().submit(task, result);
/*     */   }
/*     */ 
/*     */   
/*     */   public <T> Future<T> submit(Callable<T> task) {
/*  45 */     return next().submit(task);
/*     */   }
/*     */ 
/*     */   
/*     */   public ScheduledFuture<?> schedule(Runnable command, long delay, TimeUnit unit) {
/*  50 */     return next().schedule(command, delay, unit);
/*     */   }
/*     */ 
/*     */   
/*     */   public <V> ScheduledFuture<V> schedule(Callable<V> callable, long delay, TimeUnit unit) {
/*  55 */     return next().schedule(callable, delay, unit);
/*     */   }
/*     */ 
/*     */   
/*     */   public ScheduledFuture<?> scheduleAtFixedRate(Runnable command, long initialDelay, long period, TimeUnit unit) {
/*  60 */     return next().scheduleAtFixedRate(command, initialDelay, period, unit);
/*     */   }
/*     */ 
/*     */   
/*     */   public ScheduledFuture<?> scheduleWithFixedDelay(Runnable command, long initialDelay, long delay, TimeUnit unit) {
/*  65 */     return next().scheduleWithFixedDelay(command, initialDelay, delay, unit);
/*     */   }
/*     */ 
/*     */   
/*     */   public Future<?> shutdownGracefully() {
/*  70 */     return shutdownGracefully(2L, 15L, TimeUnit.SECONDS);
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
/*     */ 
/*     */ 
/*     */   
/*     */   @Deprecated
/*     */   public List<Runnable> shutdownNow() {
/*  86 */     shutdown();
/*  87 */     return Collections.emptyList();
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public <T> List<Future<T>> invokeAll(Collection<? extends Callable<T>> tasks) throws InterruptedException {
/*  93 */     return next().invokeAll(tasks);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public <T> List<Future<T>> invokeAll(Collection<? extends Callable<T>> tasks, long timeout, TimeUnit unit) throws InterruptedException {
/*  99 */     return next().invokeAll(tasks, timeout, unit);
/*     */   }
/*     */ 
/*     */   
/*     */   public <T> T invokeAny(Collection<? extends Callable<T>> tasks) throws InterruptedException, ExecutionException {
/* 104 */     return next().invokeAny(tasks);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public <T> T invokeAny(Collection<? extends Callable<T>> tasks, long timeout, TimeUnit unit) throws InterruptedException, ExecutionException, TimeoutException {
/* 110 */     return next().invokeAny(tasks, timeout, unit);
/*     */   }
/*     */ 
/*     */   
/*     */   public void execute(Runnable command) {
/* 115 */     next().execute(command);
/*     */   }
/*     */   
/*     */   @Deprecated
/*     */   public abstract void shutdown();
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\io\nett\\util\concurrent\AbstractEventExecutorGroup.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */