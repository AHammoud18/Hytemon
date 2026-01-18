/*    */ package com.hypixel.hytale.procedurallib.condition;
/*    */ 
/*    */ import java.util.Arrays;
/*    */ import javax.annotation.Nonnull;
/*    */ 
/*    */ public class DoubleThreshold
/*    */ {
/*    */   public static class Single
/*    */     implements IDoubleThreshold {
/*    */     protected final double min;
/*    */     protected final double max;
/*    */     protected final double halfRange;
/*    */     
/*    */     public Single(double min, double max) {
/* 15 */       this.min = min;
/* 16 */       this.max = max;
/* 17 */       this.halfRange = (max - min) * 0.5D;
/*    */     }
/*    */ 
/*    */     
/*    */     public boolean eval(double d) {
/* 22 */       return (this.min <= d && d <= this.max);
/*    */     }
/*    */ 
/*    */     
/*    */     public boolean eval(double d, double factor) {
/* 27 */       double t0 = this.min + this.halfRange * factor;
/* 28 */       double t1 = this.max - this.halfRange * factor;
/* 29 */       return (t0 <= d && d <= t1);
/*    */     }
/*    */ 
/*    */     
/*    */     @Nonnull
/*    */     public String toString() {
/* 35 */       return "DoubleThreshold.Single{min=" + this.min + ", max=" + this.max + ", halfRange=" + this.halfRange + "}";
/*    */     }
/*    */   }
/*    */ 
/*    */   
/*    */   public static class Multiple
/*    */     implements IDoubleThreshold
/*    */   {
/*    */     protected final DoubleThreshold.Single[] singles;
/*    */ 
/*    */     
/*    */     public Multiple(DoubleThreshold.Single[] singles) {
/* 47 */       this.singles = singles;
/*    */     }
/*    */ 
/*    */     
/*    */     public boolean eval(double d) {
/* 52 */       for (DoubleThreshold.Single single : this.singles) {
/* 53 */         if (single.eval(d)) return true; 
/*    */       } 
/* 55 */       return false;
/*    */     }
/*    */ 
/*    */     
/*    */     public boolean eval(double d, double factor) {
/* 60 */       for (DoubleThreshold.Single single : this.singles) {
/* 61 */         if (single.eval(d, factor)) return true; 
/*    */       } 
/* 63 */       return false;
/*    */     }
/*    */ 
/*    */ 
/*    */     
/*    */     @Nonnull
/*    */     public String toString() {
/* 70 */       return "DoubleThreshold.Multiple{singles=" + Arrays.toString((Object[])this.singles) + "}";
/*    */     }
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\procedurallib\condition\DoubleThreshold.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */