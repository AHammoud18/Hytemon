/*    */ package com.hypixel.hytale.procedurallib.logic;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class ResultBuffer2d
/*    */ {
/*    */   public int hash;
/*    */   public int hash2;
/*    */   public int ix;
/*    */   public int iy;
/*    */   public int ix2;
/*    */   public int iy2;
/*    */   public double distance;
/*    */   public double distance2;
/*    */   public double x;
/*    */   public double y;
/*    */   public double x2;
/*    */   public double y2;
/*    */   
/*    */   public void register(int hash, int ix, int iy, double distance, double x, double y) {
/* 22 */     if (distance < this.distance) {
/* 23 */       this.ix = ix;
/* 24 */       this.iy = iy;
/* 25 */       this.distance = distance;
/* 26 */       this.x = x;
/* 27 */       this.y = y;
/* 28 */       this.hash = hash;
/*    */     } 
/*    */   }
/*    */   
/*    */   public void register2(int hash, int ix, int iy, double distance, double x, double y) {
/* 33 */     if (distance < this.distance) {
/* 34 */       this.distance2 = this.distance;
/* 35 */       this.x2 = this.x;
/* 36 */       this.y2 = this.y;
/* 37 */       this.ix2 = this.ix;
/* 38 */       this.iy2 = this.iy;
/* 39 */       this.distance = distance;
/* 40 */       this.x = x;
/* 41 */       this.y = y;
/* 42 */       this.ix = ix;
/* 43 */       this.iy = iy;
/* 44 */       this.hash2 = this.hash;
/* 45 */       this.hash = hash;
/* 46 */     } else if (distance < this.distance2) {
/* 47 */       this.distance2 = distance;
/* 48 */       this.x2 = x;
/* 49 */       this.y2 = y;
/* 50 */       this.ix2 = ix;
/* 51 */       this.iy2 = iy;
/* 52 */       this.hash2 = hash;
/*    */     } 
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\procedurallib\logic\ResultBuffer$ResultBuffer2d.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */