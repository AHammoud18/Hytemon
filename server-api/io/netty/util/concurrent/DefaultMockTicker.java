/*    */ package io.netty.util.concurrent;
/*    */ 
/*    */ import io.netty.util.internal.ObjectUtil;
/*    */ import java.util.Collections;
/*    */ import java.util.IdentityHashMap;
/*    */ import java.util.Objects;
/*    */ import java.util.Set;
/*    */ import java.util.concurrent.TimeUnit;
/*    */ import java.util.concurrent.atomic.AtomicLong;
/*    */ import java.util.concurrent.locks.Condition;
/*    */ import java.util.concurrent.locks.ReentrantLock;
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
/*    */ final class DefaultMockTicker
/*    */   implements MockTicker
/*    */ {
/* 35 */   private final ReentrantLock lock = new ReentrantLock(true);
/* 36 */   private final Condition tickCondition = this.lock.newCondition();
/* 37 */   private final Condition sleeperCondition = this.lock.newCondition();
/* 38 */   private final AtomicLong nanoTime = new AtomicLong();
/* 39 */   private final Set<Thread> sleepers = Collections.newSetFromMap(new IdentityHashMap<>());
/*    */ 
/*    */   
/*    */   public long nanoTime() {
/* 43 */     return this.nanoTime.get();
/*    */   }
/*    */ 
/*    */   
/*    */   public void sleep(long delay, TimeUnit unit) throws InterruptedException {
/* 48 */     ObjectUtil.checkPositiveOrZero(delay, "delay");
/* 49 */     Objects.requireNonNull(unit, "unit");
/*    */     
/* 51 */     if (delay == 0L) {
/*    */       return;
/*    */     }
/*    */     
/* 55 */     long delayNanos = unit.toNanos(delay);
/* 56 */     this.lock.lockInterruptibly();
/*    */     try {
/* 58 */       long startTimeNanos = nanoTime();
/* 59 */       this.sleepers.add(Thread.currentThread());
/* 60 */       this.sleeperCondition.signalAll();
/*    */       do {
/* 62 */         this.tickCondition.await();
/* 63 */       } while (nanoTime() - startTimeNanos < delayNanos);
/*    */     } finally {
/* 65 */       this.sleepers.remove(Thread.currentThread());
/* 66 */       this.lock.unlock();
/*    */     } 
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void awaitSleepingThread(Thread thread) throws InterruptedException {
/* 74 */     this.lock.lockInterruptibly();
/*    */     try {
/* 76 */       while (!this.sleepers.contains(thread)) {
/* 77 */         this.sleeperCondition.await();
/*    */       }
/*    */     } finally {
/* 80 */       this.lock.unlock();
/*    */     } 
/*    */   }
/*    */ 
/*    */   
/*    */   public void advance(long amount, TimeUnit unit) {
/* 86 */     ObjectUtil.checkPositiveOrZero(amount, "amount");
/* 87 */     Objects.requireNonNull(unit, "unit");
/*    */     
/* 89 */     if (amount == 0L) {
/*    */       return;
/*    */     }
/*    */     
/* 93 */     long amountNanos = unit.toNanos(amount);
/* 94 */     this.lock.lock();
/*    */     try {
/* 96 */       this.nanoTime.addAndGet(amountNanos);
/* 97 */       this.tickCondition.signalAll();
/*    */     } finally {
/* 99 */       this.lock.unlock();
/*    */     } 
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\io\nett\\util\concurrent\DefaultMockTicker.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */