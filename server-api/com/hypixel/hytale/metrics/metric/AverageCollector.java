/*    */ package com.hypixel.hytale.metrics.metric;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class AverageCollector
/*    */ {
/* 12 */   private double val = 0.0D;
/* 13 */   private long n = 0L;
/*    */ 
/*    */   
/*    */   public double get() {
/* 17 */     return this.val;
/*    */   }
/*    */   
/*    */   public long size() {
/* 21 */     return this.n;
/*    */   }
/*    */   
/*    */   public double addAndGet(double v) {
/* 25 */     add(v);
/* 26 */     return get();
/*    */   }
/*    */   
/*    */   public void add(double v) {
/* 30 */     this.n++;
/* 31 */     this.val = this.val - this.val / this.n + v / this.n;
/*    */   }
/*    */   
/*    */   public void remove(double v) {
/* 35 */     if (this.n == 1L) {
/* 36 */       this.n = 0L;
/* 37 */       this.val = 0.0D;
/* 38 */     } else if (this.n > 1L) {
/* 39 */       this.val = (this.val - v / this.n) / (1.0D - 1.0D / this.n);
/* 40 */       this.n--;
/*    */     } 
/*    */   }
/*    */   
/*    */   public void clear() {
/* 45 */     this.val = 0.0D;
/* 46 */     this.n = 0L;
/*    */   }
/*    */   
/*    */   public static double add(double val, double v, int n) {
/* 50 */     return val - val / n + v / n;
/*    */   }
/*    */   
/*    */   public void set(double v) {
/* 54 */     this.n = 1L;
/* 55 */     this.val = v;
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\metrics\metric\AverageCollector.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */