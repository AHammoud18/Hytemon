/*    */ package io.sentry.util;
/*    */ 
/*    */ import io.sentry.ISentryLifecycleToken;
/*    */ import java.util.concurrent.locks.ReentrantLock;
/*    */ import org.jetbrains.annotations.NotNull;
/*    */ 
/*    */ public final class AutoClosableReentrantLock
/*    */   extends ReentrantLock {
/*    */   private static final long serialVersionUID = -3283069816958445549L;
/*    */   
/*    */   public ISentryLifecycleToken acquire() {
/* 12 */     lock();
/* 13 */     return new AutoClosableReentrantLockLifecycleToken(this);
/*    */   }
/*    */   
/*    */   static final class AutoClosableReentrantLockLifecycleToken implements ISentryLifecycleToken {
/*    */     @NotNull
/*    */     private final ReentrantLock lock;
/*    */     
/*    */     AutoClosableReentrantLockLifecycleToken(@NotNull ReentrantLock lock) {
/* 21 */       this.lock = lock;
/*    */     }
/*    */ 
/*    */     
/*    */     public void close() {
/* 26 */       this.lock.unlock();
/*    */     }
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\io\sentr\\util\AutoClosableReentrantLock.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */