/*    */ package com.hypixel.hytale.procedurallib.condition;
/*    */ 
/*    */ import java.util.Arrays;
/*    */ import javax.annotation.Nonnull;
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
/*    */ public class Multiple
/*    */   implements IDoubleThreshold
/*    */ {
/*    */   protected final DoubleThreshold.Single[] singles;
/*    */   
/*    */   public Multiple(DoubleThreshold.Single[] singles) {
/* 47 */     this.singles = singles;
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean eval(double d) {
/* 52 */     for (DoubleThreshold.Single single : this.singles) {
/* 53 */       if (single.eval(d)) return true; 
/*    */     } 
/* 55 */     return false;
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean eval(double d, double factor) {
/* 60 */     for (DoubleThreshold.Single single : this.singles) {
/* 61 */       if (single.eval(d, factor)) return true; 
/*    */     } 
/* 63 */     return false;
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   @Nonnull
/*    */   public String toString() {
/* 70 */     return "DoubleThreshold.Multiple{singles=" + Arrays.toString((Object[])this.singles) + "}";
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\procedurallib\condition\DoubleThreshold$Multiple.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */