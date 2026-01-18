/*    */ package com.github.luben.zstd;
/*    */ 
/*    */ import java.io.Closeable;
/*    */ import java.util.concurrent.atomic.AtomicIntegerFieldUpdater;
/*    */ 
/*    */ abstract class AutoCloseBase
/*    */   implements Closeable
/*    */ {
/*  9 */   private static final AtomicIntegerFieldUpdater<AutoCloseBase> SHARED_LOCK_UPDATER = AtomicIntegerFieldUpdater.newUpdater(AutoCloseBase.class, "sharedLock");
/*    */   
/*    */   private static final int SHARED_LOCK_CLOSED = -1;
/*    */   
/*    */   private volatile int sharedLock;
/*    */ 
/*    */   
/*    */   void storeFence() {
/* 17 */     this.sharedLock = 0;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   void acquireSharedLock() {
/*    */     int i;
/*    */     do {
/* 26 */       i = this.sharedLock;
/* 27 */       if (i < 0) {
/* 28 */         throw new IllegalStateException("Closed");
/*    */       }
/* 30 */       if (i == Integer.MAX_VALUE) {
/* 31 */         throw new IllegalStateException("Shared lock overflow");
/*    */       }
/* 33 */     } while (!SHARED_LOCK_UPDATER.compareAndSet(this, i, i + 1));
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   void releaseSharedLock() {
/*    */     int i;
/*    */     do {
/* 41 */       i = this.sharedLock;
/* 42 */       if (i < 0) {
/* 43 */         throw new IllegalStateException("Closed");
/*    */       }
/* 45 */       if (i == 0) {
/* 46 */         throw new IllegalStateException("Shared lock underflow");
/*    */       }
/* 48 */     } while (!SHARED_LOCK_UPDATER.compareAndSet(this, i, i - 1));
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   abstract void doClose();
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void close() {
/* 61 */     synchronized (this) {
/* 62 */       if (this.sharedLock == -1) {
/*    */         return;
/*    */       }
/* 65 */       if (!SHARED_LOCK_UPDATER.compareAndSet(this, 0, -1)) {
/* 66 */         throw new IllegalStateException("Attempt to close while in use");
/*    */       }
/* 68 */       doClose();
/*    */     } 
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\github\luben\zstd\AutoCloseBase.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */