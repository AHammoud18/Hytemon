/*    */ package com.hypixel.hytale.procedurallib.property;
/*    */ 
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
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class Entry
/*    */ {
/*    */   private NoiseProperty noiseProperty;
/*    */   private double factor;
/*    */   
/*    */   public Entry(NoiseProperty noiseProperty, double factor) {
/* 54 */     this.noiseProperty = noiseProperty;
/* 55 */     this.factor = factor;
/*    */   }
/*    */   
/*    */   public NoiseProperty getNoiseProperty() {
/* 59 */     return this.noiseProperty;
/*    */   }
/*    */   
/*    */   public void setNoiseProperty(NoiseProperty noiseProperty) {
/* 63 */     this.noiseProperty = noiseProperty;
/*    */   }
/*    */   
/*    */   public double getFactor() {
/* 67 */     return this.factor;
/*    */   }
/*    */   
/*    */   public void setFactor(double factor) {
/* 71 */     this.factor = factor;
/*    */   }
/*    */ 
/*    */   
/*    */   @Nonnull
/*    */   public String toString() {
/* 77 */     return "Entry{noiseProperty=" + String.valueOf(this.noiseProperty) + ", factor=" + this.factor + "}";
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\procedurallib\property\SumNoiseProperty$Entry.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */