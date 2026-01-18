/*     */ package com.hypixel.hytale.component.task;
/*     */ 
/*     */ import java.util.concurrent.CountedCompleter;
/*     */ import java.util.concurrent.ForkJoinPool;
/*     */ import java.util.function.IntConsumer;
/*     */ import java.util.function.Supplier;
/*     */ import javax.annotation.Nonnull;
/*     */ 
/*     */ public class ParallelRangeTask<D extends IntConsumer> extends CountedCompleter<Void> {
/*  10 */   public static final int PARALLELISM = Math.max(ForkJoinPool.getCommonPoolParallelism(), 1);
/*  11 */   public static final int TASK_COUNT = Math.max(ForkJoinPool.getCommonPoolParallelism() << 2, 1);
/*     */   
/*     */   @Nonnull
/*     */   private final SubTask<D>[] subTasks;
/*     */   private int size;
/*     */   public volatile boolean running;
/*     */   
/*     */   public ParallelRangeTask(@Nonnull Supplier<D> supplier) {
/*  19 */     this(null, supplier);
/*     */   }
/*     */ 
/*     */   
/*     */   public ParallelRangeTask(CountedCompleter<?> completer, @Nonnull Supplier<D> supplier) {
/*  24 */     super(completer);
/*  25 */     this.subTasks = (SubTask<D>[])new SubTask[TASK_COUNT];
/*  26 */     for (int i = 0; i < this.subTasks.length; i++) {
/*  27 */       this.subTasks[i] = new SubTask<>(this, supplier.get());
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   public void reinitialize() {
/*  33 */     if (this.running) throw new IllegalStateException("ParallelRangeTask has already been started"); 
/*  34 */     super.reinitialize();
/*     */   }
/*     */   
/*     */   @Nonnull
/*     */   public ParallelRangeTask<D> init(int from, int to) {
/*  39 */     reinitialize();
/*     */     
/*  41 */     int perTask = Math.max((to - from + this.subTasks.length - 1) / this.subTasks.length, 1);
/*  42 */     this.size = 0;
/*  43 */     while (this.size < this.subTasks.length && from < to) {
/*  44 */       int next = Math.min(from + perTask, to);
/*  45 */       this.subTasks[this.size].init(from, next);
/*  46 */       from = next;
/*  47 */       this.size++;
/*     */     } 
/*  49 */     if (from < to) throw new IllegalStateException("Failed to distribute the whole range to tasks!"); 
/*  50 */     return this;
/*     */   }
/*     */   
/*     */   public int size() {
/*  54 */     return this.size;
/*     */   }
/*     */   
/*     */   public D get(int i) {
/*  58 */     return this.subTasks[i].getData();
/*     */   }
/*     */   
/*     */   public void set(int i, D data) {
/*  62 */     if (this.running) throw new IllegalStateException("ParallelRangeTask has already been started"); 
/*  63 */     this.subTasks[i].setData(data);
/*     */   }
/*     */ 
/*     */   
/*     */   public void compute() {
/*  68 */     setPendingCount(this.size - 1);
/*  69 */     for (int i = 0; i < this.size - 1; i++) {
/*  70 */       this.subTasks[i].fork();
/*     */     }
/*  72 */     this.subTasks[this.size - 1].compute();
/*     */   }
/*     */   
/*     */   static class SubTask<D extends IntConsumer> extends CountedCompleter<Void> {
/*     */     private int from;
/*     */     private int to;
/*     */     private D data;
/*     */     
/*     */     SubTask(ParallelRangeTask<?> parent, D data) {
/*  81 */       super(parent);
/*  82 */       this.data = data;
/*     */     }
/*     */     
/*     */     void init(int from, int to) {
/*  86 */       reinitialize();
/*  87 */       this.from = from;
/*  88 */       this.to = to;
/*     */     }
/*     */     
/*     */     D getData() {
/*  92 */       return this.data;
/*     */     }
/*     */     
/*     */     void setData(D data) {
/*  96 */       this.data = data;
/*     */     }
/*     */ 
/*     */     
/*     */     public void compute() {
/* 101 */       for (int i = this.from; i < this.to; i++) {
/* 102 */         this.data.accept(i);
/*     */       }
/* 104 */       propagateCompletion();
/*     */     }
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\component\task\ParallelRangeTask.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */