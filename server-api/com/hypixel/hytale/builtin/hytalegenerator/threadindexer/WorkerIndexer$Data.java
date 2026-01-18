/*     */ package com.hypixel.hytale.builtin.hytalegenerator.threadindexer;
/*     */ 
/*     */ import java.util.function.Supplier;
/*     */ import javax.annotation.Nonnull;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class Data<T>
/*     */ {
/*     */   private T[] data;
/*     */   private Supplier<T> initialize;
/*     */   
/*     */   public Data(int size, @Nonnull Supplier<T> initialize) {
/*  83 */     this.data = (T[])new Object[size];
/*  84 */     this.initialize = initialize;
/*     */   }
/*     */   
/*     */   public boolean isValid(@Nonnull WorkerIndexer.Id id) {
/*  88 */     return (id != null && id.id < this.data.length && id.id >= 0);
/*     */   }
/*     */   
/*     */   @Nonnull
/*     */   public T get(@Nonnull WorkerIndexer.Id id) {
/*  93 */     if (!isValid(id)) {
/*  94 */       throw new IllegalArgumentException("Invalid thread id " + String.valueOf(id));
/*     */     }
/*     */     
/*  97 */     if (this.data[id.id] == null) {
/*  98 */       this.data[id.id] = this.initialize.get();
/*  99 */       assert this.data[id.id] != null;
/*     */     } 
/*     */     
/* 102 */     return this.data[id.id];
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\builtin\hytalegenerator\threadindexer\WorkerIndexer$Data.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */