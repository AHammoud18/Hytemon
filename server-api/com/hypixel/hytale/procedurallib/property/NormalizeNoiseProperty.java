/*    */ package com.hypixel.hytale.procedurallib.property;
/*    */ 
/*    */ import com.hypixel.hytale.procedurallib.logic.GeneralNoise;
/*    */ import javax.annotation.Nonnull;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class NormalizeNoiseProperty
/*    */   implements NoiseProperty
/*    */ {
/*    */   protected final NoiseProperty noiseProperty;
/*    */   protected final double min;
/*    */   protected final double range;
/*    */   
/*    */   public NormalizeNoiseProperty(NoiseProperty noiseProperty, double min, double range) {
/* 17 */     this.noiseProperty = noiseProperty;
/* 18 */     this.min = min;
/* 19 */     this.range = range;
/*    */   }
/*    */   
/*    */   public NoiseProperty getNoiseProperty() {
/* 23 */     return this.noiseProperty;
/*    */   }
/*    */   
/*    */   public double getMin() {
/* 27 */     return this.min;
/*    */   }
/*    */   
/*    */   public double getRange() {
/* 31 */     return this.range;
/*    */   }
/*    */ 
/*    */   
/*    */   public double get(int seed, double x, double y) {
/* 36 */     return GeneralNoise.limit((this.noiseProperty.get(seed, x, y) - this.min) / this.range);
/*    */   }
/*    */ 
/*    */   
/*    */   public double get(int seed, double x, double y, double z) {
/* 41 */     return GeneralNoise.limit((this.noiseProperty.get(seed, x, y, z) - this.min) / this.range);
/*    */   }
/*    */ 
/*    */   
/*    */   @Nonnull
/*    */   public String toString() {
/* 47 */     return "NormalizeNoiseProperty{noiseProperty=" + String.valueOf(this.noiseProperty) + ", min=" + this.min + ", range=" + this.range + "}";
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\procedurallib\property\NormalizeNoiseProperty.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */