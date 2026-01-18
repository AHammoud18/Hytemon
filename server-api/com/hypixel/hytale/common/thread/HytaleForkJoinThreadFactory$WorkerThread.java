/*    */ package com.hypixel.hytale.common.thread;
/*    */ 
/*    */ import com.hypixel.hytale.metrics.InitStackThread;
/*    */ import java.util.concurrent.ForkJoinPool;
/*    */ import java.util.concurrent.ForkJoinWorkerThread;
/*    */ import javax.annotation.Nonnull;
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
/*    */ public class WorkerThread
/*    */   extends ForkJoinWorkerThread
/*    */   implements InitStackThread
/*    */ {
/*    */   @Nonnull
/*    */   private final StackTraceElement[] initStack;
/*    */   
/*    */   protected WorkerThread(@Nonnull ForkJoinPool pool) {
/* 26 */     super(pool);
/* 27 */     this.initStack = Thread.currentThread().getStackTrace();
/*    */   }
/*    */ 
/*    */   
/*    */   @Nonnull
/*    */   public StackTraceElement[] getInitStack() {
/* 33 */     return this.initStack;
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\common\thread\HytaleForkJoinThreadFactory$WorkerThread.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */