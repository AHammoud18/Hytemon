/*    */ package com.hypixel.hytale.metrics.metric;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class SynchronizedAverageCollector
/*    */   extends AverageCollector
/*    */ {
/*    */   public synchronized double get() {
/* 11 */     return super.get();
/*    */   }
/*    */ 
/*    */   
/*    */   public synchronized long size() {
/* 16 */     return super.size();
/*    */   }
/*    */ 
/*    */   
/*    */   public synchronized double addAndGet(double v) {
/* 21 */     return super.addAndGet(v);
/*    */   }
/*    */ 
/*    */   
/*    */   public synchronized void add(double v) {
/* 26 */     super.add(v);
/*    */   }
/*    */ 
/*    */   
/*    */   public synchronized void remove(double v) {
/* 31 */     super.remove(v);
/*    */   }
/*    */ 
/*    */   
/*    */   public synchronized void clear() {
/* 36 */     super.clear();
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\metrics\metric\SynchronizedAverageCollector.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */