/*    */ package com.hypixel.hytale.builtin.hytalegenerator.density.nodes;
/*    */ 
/*    */ import com.hypixel.hytale.math.vector.Vector3d;
/*    */ import javax.annotation.Nonnull;
/*    */ import javax.annotation.Nullable;
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
/*    */ 
/*    */ 
/*    */ 
/*    */ class Cache
/*    */ {
/*    */   MultiCacheDensity.Entry[] entries;
/*    */   int oldestIndex;
/*    */   
/*    */   Cache(int size) {
/* 51 */     this.entries = new MultiCacheDensity.Entry[size];
/* 52 */     for (int i = 0; i < size; i++) {
/* 53 */       this.entries[i] = new MultiCacheDensity.Entry();
/*    */     }
/*    */     
/* 56 */     this.oldestIndex = 0;
/*    */   }
/*    */   
/*    */   MultiCacheDensity.Entry getNext() {
/* 60 */     MultiCacheDensity.Entry entry = this.entries[this.oldestIndex];
/*    */     
/* 62 */     this.oldestIndex++;
/* 63 */     if (this.oldestIndex >= this.entries.length) this.oldestIndex = 0;
/*    */     
/* 65 */     return entry;
/*    */   }
/*    */ 
/*    */   
/*    */   @Nullable
/*    */   MultiCacheDensity.Entry find(@Nonnull Vector3d position) {
/* 71 */     int startIndex = this.oldestIndex - 1;
/* 72 */     if (startIndex < 0) startIndex += this.entries.length;
/*    */     
/* 74 */     int index = startIndex;
/*    */     
/*    */     do {
/* 77 */       if (position.equals((this.entries[index]).position)) {
/* 78 */         return this.entries[index];
/*    */       }
/*    */       
/* 81 */       index++;
/* 82 */       if (index < this.entries.length) continue;  index = 0;
/* 83 */     } while (index != startIndex);
/*    */     
/* 85 */     return null;
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\builtin\hytalegenerator\density\nodes\MultiCacheDensity$Cache.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */