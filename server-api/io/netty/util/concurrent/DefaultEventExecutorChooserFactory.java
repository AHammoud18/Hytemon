/*    */ package io.netty.util.concurrent;
/*    */ 
/*    */ import java.util.concurrent.atomic.AtomicInteger;
/*    */ import java.util.concurrent.atomic.AtomicLong;
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
/*    */ public final class DefaultEventExecutorChooserFactory
/*    */   implements EventExecutorChooserFactory
/*    */ {
/* 26 */   public static final DefaultEventExecutorChooserFactory INSTANCE = new DefaultEventExecutorChooserFactory();
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public EventExecutorChooserFactory.EventExecutorChooser newChooser(EventExecutor[] executors) {
/* 32 */     if (isPowerOfTwo(executors.length)) {
/* 33 */       return new PowerOfTwoEventExecutorChooser(executors);
/*    */     }
/* 35 */     return new GenericEventExecutorChooser(executors);
/*    */   }
/*    */ 
/*    */   
/*    */   private static boolean isPowerOfTwo(int val) {
/* 40 */     return ((val & -val) == val);
/*    */   }
/*    */   
/*    */   private static final class PowerOfTwoEventExecutorChooser implements EventExecutorChooserFactory.EventExecutorChooser {
/* 44 */     private final AtomicInteger idx = new AtomicInteger();
/*    */     private final EventExecutor[] executors;
/*    */     
/*    */     PowerOfTwoEventExecutorChooser(EventExecutor[] executors) {
/* 48 */       this.executors = executors;
/*    */     }
/*    */ 
/*    */     
/*    */     public EventExecutor next() {
/* 53 */       return this.executors[this.idx.getAndIncrement() & this.executors.length - 1];
/*    */     }
/*    */   }
/*    */ 
/*    */   
/*    */   private static final class GenericEventExecutorChooser
/*    */     implements EventExecutorChooserFactory.EventExecutorChooser
/*    */   {
/* 61 */     private final AtomicLong idx = new AtomicLong();
/*    */     private final EventExecutor[] executors;
/*    */     
/*    */     GenericEventExecutorChooser(EventExecutor[] executors) {
/* 65 */       this.executors = executors;
/*    */     }
/*    */ 
/*    */     
/*    */     public EventExecutor next() {
/* 70 */       return this.executors[(int)Math.abs(this.idx.getAndIncrement() % this.executors.length)];
/*    */     }
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\io\nett\\util\concurrent\DefaultEventExecutorChooserFactory.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */