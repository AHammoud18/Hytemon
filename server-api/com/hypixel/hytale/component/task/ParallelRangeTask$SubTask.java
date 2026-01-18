/*     */ package com.hypixel.hytale.component.task;
/*     */ 
/*     */ import java.util.concurrent.CountedCompleter;
/*     */ import java.util.function.IntConsumer;
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
/*     */ class SubTask<D extends IntConsumer>
/*     */   extends CountedCompleter<Void>
/*     */ {
/*     */   private int from;
/*     */   private int to;
/*     */   private D data;
/*     */   
/*     */   SubTask(ParallelRangeTask<?> parent, D data) {
/*  81 */     super(parent);
/*  82 */     this.data = data;
/*     */   }
/*     */   
/*     */   void init(int from, int to) {
/*  86 */     reinitialize();
/*  87 */     this.from = from;
/*  88 */     this.to = to;
/*     */   }
/*     */   
/*     */   D getData() {
/*  92 */     return this.data;
/*     */   }
/*     */   
/*     */   void setData(D data) {
/*  96 */     this.data = data;
/*     */   }
/*     */ 
/*     */   
/*     */   public void compute() {
/* 101 */     for (int i = this.from; i < this.to; i++) {
/* 102 */       this.data.accept(i);
/*     */     }
/* 104 */     propagateCompletion();
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\component\task\ParallelRangeTask$SubTask.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */