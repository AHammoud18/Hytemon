/*    */ package com.hypixel.hytale.builtin.hytalegenerator.density.nodes;
/*    */ 
/*    */ import com.hypixel.hytale.builtin.hytalegenerator.density.Density;
/*    */ import com.hypixel.hytale.builtin.hytalegenerator.threadindexer.WorkerIndexer;
/*    */ import com.hypixel.hytale.math.vector.Vector3d;
/*    */ import javax.annotation.Nonnull;
/*    */ import javax.annotation.Nullable;
/*    */ 
/*    */ public class MultiCacheDensity
/*    */   extends Density {
/*    */   @Nonnull
/*    */   private final WorkerIndexer.Data<Cache> threadData;
/*    */   @Nonnull
/*    */   private Density input;
/*    */   
/*    */   public MultiCacheDensity(@Nonnull Density input, int threadCount, int capacity) {
/* 17 */     this.input = input;
/* 18 */     this.threadData = new WorkerIndexer.Data(threadCount, () -> new Cache(capacity));
/*    */   }
/*    */ 
/*    */   
/*    */   public double process(@Nonnull Density.Context context) {
/* 23 */     Cache cache = (Cache)this.threadData.get(context.workerId);
/*    */     
/* 25 */     Entry matchingEntry = cache.find(context.position);
/*    */     
/* 27 */     if (matchingEntry == null) {
/* 28 */       matchingEntry = cache.getNext();
/* 29 */       if (matchingEntry.position == null) {
/* 30 */         matchingEntry.position = new Vector3d();
/*    */       }
/* 32 */       matchingEntry.position.assign(context.position);
/* 33 */       matchingEntry.value = this.input.process(context);
/*    */     } 
/*    */     
/* 36 */     return matchingEntry.value;
/*    */   }
/*    */ 
/*    */   
/*    */   public void setInputs(@Nonnull Density[] inputs) {
/* 41 */     assert inputs.length != 0;
/* 42 */     assert inputs[0] != null;
/* 43 */     this.input = inputs[0];
/*    */   }
/*    */   
/*    */   private static class Cache {
/*    */     MultiCacheDensity.Entry[] entries;
/*    */     int oldestIndex;
/*    */     
/*    */     Cache(int size) {
/* 51 */       this.entries = new MultiCacheDensity.Entry[size];
/* 52 */       for (int i = 0; i < size; i++) {
/* 53 */         this.entries[i] = new MultiCacheDensity.Entry();
/*    */       }
/*    */       
/* 56 */       this.oldestIndex = 0;
/*    */     }
/*    */     
/*    */     MultiCacheDensity.Entry getNext() {
/* 60 */       MultiCacheDensity.Entry entry = this.entries[this.oldestIndex];
/*    */       
/* 62 */       this.oldestIndex++;
/* 63 */       if (this.oldestIndex >= this.entries.length) this.oldestIndex = 0;
/*    */       
/* 65 */       return entry;
/*    */     }
/*    */ 
/*    */     
/*    */     @Nullable
/*    */     MultiCacheDensity.Entry find(@Nonnull Vector3d position) {
/* 71 */       int startIndex = this.oldestIndex - 1;
/* 72 */       if (startIndex < 0) startIndex += this.entries.length;
/*    */       
/* 74 */       int index = startIndex;
/*    */       
/*    */       do {
/* 77 */         if (position.equals((this.entries[index]).position)) {
/* 78 */           return this.entries[index];
/*    */         }
/*    */         
/* 81 */         index++;
/* 82 */         if (index < this.entries.length) continue;  index = 0;
/* 83 */       } while (index != startIndex);
/*    */       
/* 85 */       return null;
/*    */     } }
/*    */   
/*    */   private static class Entry { Vector3d position;
/*    */     
/*    */     Entry() {
/* 91 */       this.position = null;
/* 92 */       this.value = 0.0D;
/*    */     }
/*    */     
/*    */     double value; }
/*    */ 
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\builtin\hytalegenerator\density\nodes\MultiCacheDensity.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */