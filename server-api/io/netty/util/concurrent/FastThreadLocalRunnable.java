/*    */ package io.netty.util.concurrent;
/*    */ 
/*    */ import io.netty.util.internal.ObjectUtil;
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
/*    */ final class FastThreadLocalRunnable
/*    */   implements Runnable
/*    */ {
/*    */   private final Runnable runnable;
/*    */   
/*    */   private FastThreadLocalRunnable(Runnable runnable) {
/* 24 */     this.runnable = (Runnable)ObjectUtil.checkNotNull(runnable, "runnable");
/*    */   }
/*    */ 
/*    */   
/*    */   public void run() {
/*    */     try {
/* 30 */       this.runnable.run();
/*    */     } finally {
/* 32 */       FastThreadLocal.removeAll();
/*    */     } 
/*    */   }
/*    */   
/*    */   static Runnable wrap(Runnable runnable) {
/* 37 */     return (runnable instanceof FastThreadLocalRunnable) ? runnable : new FastThreadLocalRunnable(runnable);
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\io\nett\\util\concurrent\FastThreadLocalRunnable.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */