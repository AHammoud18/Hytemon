/*    */ package com.hypixel.hytale.math.hitdetection;
/*    */ 
/*    */ import com.hypixel.hytale.math.vector.Vector4d;
/*    */ import javax.annotation.Nonnull;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class Vector4dBufferList
/*    */ {
/*    */   private Vector4d[] vectors;
/*    */   private int size;
/*    */   
/*    */   public Vector4dBufferList(int size) {
/* 16 */     this.vectors = new Vector4d[size];
/* 17 */     for (int i = 0; i < size; i++) {
/* 18 */       this.vectors[i] = new Vector4d();
/*    */     }
/* 20 */     this.size = 0;
/*    */   }
/*    */   
/*    */   public Vector4d next() {
/* 24 */     return this.vectors[this.size++];
/*    */   }
/*    */   
/*    */   public void clear() {
/* 28 */     this.size = 0;
/*    */   }
/*    */   
/*    */   public int size() {
/* 32 */     return this.size;
/*    */   }
/*    */   
/*    */   public Vector4d get(int i) {
/* 36 */     return this.vectors[i];
/*    */   }
/*    */   
/*    */   public boolean isEmpty() {
/* 40 */     return (this.size == 0);
/*    */   }
/*    */ 
/*    */   
/*    */   @Nonnull
/*    */   public String toString() {
/* 46 */     StringBuilder sb = new StringBuilder();
/* 47 */     sb.append("Vector4dBufferList{vectors=[\n");
/* 48 */     for (int i = 0; i < this.size; i++) {
/* 49 */       sb.append(this.vectors[i]).append(",\n");
/*    */     }
/* 51 */     sb.append("], size=").append(this.size).append('}');
/* 52 */     return sb.toString();
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\math\hitdetection\Vector4dBufferList.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */