/*    */ package com.hypixel.hytale.component.task;
/*    */ 
/*    */ import com.hypixel.hytale.common.util.ArrayUtil;
/*    */ import java.util.Arrays;
/*    */ import java.util.concurrent.CountedCompleter;
/*    */ import java.util.function.IntConsumer;
/*    */ import java.util.function.Supplier;
/*    */ import javax.annotation.Nonnull;
/*    */ 
/*    */ public class ParallelTask<D extends IntConsumer>
/*    */   extends CountedCompleter<Void> {
/*    */   private final Supplier<D> supplier;
/*    */   @Nonnull
/* 14 */   private ParallelRangeTask<D>[] subTasks = (ParallelRangeTask<D>[])new ParallelRangeTask[0];
/*    */   
/*    */   private int size;
/*    */   
/*    */   private volatile boolean running;
/*    */   
/*    */   public ParallelTask(Supplier<D> supplier) {
/* 21 */     this((CountedCompleter<?>)null, supplier);
/*    */   }
/*    */   
/*    */   public ParallelTask(CountedCompleter<?> completer, Supplier<D> supplier) {
/* 25 */     super(completer);
/* 26 */     this.supplier = supplier;
/*    */   }
/*    */ 
/*    */   
/*    */   public void reinitialize() {
/* 31 */     if (this.running) throw new IllegalStateException("Parallel task has already been started"); 
/* 32 */     super.reinitialize();
/*    */   }
/*    */   
/*    */   public void init() {
/* 36 */     reinitialize();
/* 37 */     this.size = 0;
/*    */   }
/*    */   
/*    */   public ParallelRangeTask<D> appendTask() {
/* 41 */     if (this.running) throw new IllegalStateException("Parallel task has already been started"); 
/* 42 */     if (this.subTasks.length <= this.size) {
/* 43 */       this.subTasks = Arrays.<ParallelRangeTask<D>>copyOf(this.subTasks, ArrayUtil.grow(this.size));
/* 44 */       for (int i = this.size; i < this.subTasks.length; i++) {
/* 45 */         this.subTasks[i] = new ParallelRangeTask<>(this, this.supplier);
/*    */       }
/*    */     } 
/* 48 */     return this.subTasks[this.size++];
/*    */   }
/*    */   
/*    */   public int size() {
/* 52 */     return this.size;
/*    */   }
/*    */   
/*    */   public ParallelRangeTask<D> get(int i) {
/* 56 */     return this.subTasks[i];
/*    */   }
/*    */ 
/*    */   
/*    */   public void compute() {
/* 61 */     setPendingCount(this.size - 1);
/* 62 */     for (int i = 0; i < this.size - 1; i++) {
/* 63 */       this.subTasks[i].fork();
/*    */     }
/* 65 */     this.subTasks[this.size - 1].compute();
/*    */   }
/*    */   
/*    */   public void doInvoke() {
/* 69 */     this.running = true; int i;
/* 70 */     for (i = 0; i < this.size; i++) {
/* 71 */       (this.subTasks[i]).running = true;
/*    */     }
/* 73 */     invoke();
/* 74 */     for (i = 0; i < this.size; i++) {
/* 75 */       (this.subTasks[i]).running = false;
/*    */     }
/* 77 */     this.running = false;
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\component\task\ParallelTask.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */