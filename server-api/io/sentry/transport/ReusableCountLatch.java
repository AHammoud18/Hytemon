/*     */ package io.sentry.transport;
/*     */ 
/*     */ import java.util.concurrent.TimeUnit;
/*     */ import java.util.concurrent.locks.AbstractQueuedSynchronizer;
/*     */ import org.jetbrains.annotations.NotNull;
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
/*     */ public final class ReusableCountLatch
/*     */ {
/*     */   @NotNull
/*     */   private final Sync sync;
/*     */   
/*     */   public ReusableCountLatch(int initialCount) {
/*  62 */     if (initialCount < 0) {
/*  63 */       throw new IllegalArgumentException("negative initial count '" + initialCount + "' is not allowed");
/*     */     }
/*     */     
/*  66 */     this.sync = new Sync(initialCount);
/*     */   }
/*     */ 
/*     */   
/*     */   public ReusableCountLatch() {
/*  71 */     this(0);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getCount() {
/*  80 */     return this.sync.getCount();
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
/*     */   public void decrement() {
/*  92 */     this.sync.decrement();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void increment() {
/* 100 */     this.sync.increment();
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
/*     */   public void waitTillZero() throws InterruptedException {
/* 130 */     this.sync.acquireSharedInterruptibly(1);
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
/*     */   public boolean waitTillZero(long timeout, @NotNull TimeUnit unit) throws InterruptedException {
/* 172 */     return this.sync.tryAcquireSharedNanos(1, unit.toNanos(timeout));
/*     */   }
/*     */   
/*     */   private static final class Sync
/*     */     extends AbstractQueuedSynchronizer {
/*     */     private static final long serialVersionUID = 5970133580157457018L;
/*     */     
/*     */     Sync(int count) {
/* 180 */       setState(count);
/*     */     }
/*     */     
/*     */     private int getCount() {
/* 184 */       return getState();
/*     */     } private void increment() {
/*     */       int oldCount;
/*     */       int newCount;
/*     */       do {
/* 189 */         oldCount = getState();
/* 190 */         newCount = oldCount + 1;
/* 191 */       } while (!compareAndSetState(oldCount, newCount));
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     private void decrement() {
/* 198 */       releaseShared(1);
/*     */     }
/*     */ 
/*     */     
/*     */     public int tryAcquireShared(int acquires) {
/* 203 */       return (getState() == 0) ? 1 : -1;
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     public boolean tryReleaseShared(int releases) {
/*     */       while (true) {
/* 210 */         int oldCount = getState();
/* 211 */         if (oldCount == 0) {
/* 212 */           return false;
/*     */         }
/* 214 */         int newCount = oldCount - 1;
/* 215 */         if (compareAndSetState(oldCount, newCount))
/* 216 */           return (newCount == 0); 
/*     */       } 
/*     */     }
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\io\sentry\transport\ReusableCountLatch.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */