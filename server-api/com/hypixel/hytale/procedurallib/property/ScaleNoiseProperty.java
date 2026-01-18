/*    */ package com.hypixel.hytale.procedurallib.property;
/*    */ 
/*    */ import javax.annotation.Nonnull;
/*    */ 
/*    */ public class ScaleNoiseProperty
/*    */   implements NoiseProperty
/*    */ {
/*    */   protected final NoiseProperty noiseProperty;
/*    */   protected final double scaleX;
/*    */   protected final double scaleY;
/*    */   protected final double scaleZ;
/*    */   
/*    */   public ScaleNoiseProperty(NoiseProperty noiseProperty, double scale) {
/* 14 */     this(noiseProperty, scale, scale, scale);
/*    */   }
/*    */   
/*    */   public ScaleNoiseProperty(NoiseProperty noiseProperty, double scaleX, double scaleY, double scaleZ) {
/* 18 */     this.noiseProperty = noiseProperty;
/* 19 */     this.scaleX = scaleX;
/* 20 */     this.scaleY = scaleY;
/* 21 */     this.scaleZ = scaleZ;
/*    */   }
/*    */   
/*    */   public NoiseProperty getNoiseProperty() {
/* 25 */     return this.noiseProperty;
/*    */   }
/*    */   
/*    */   public double getScaleX() {
/* 29 */     return this.scaleX;
/*    */   }
/*    */   
/*    */   public double getScaleY() {
/* 33 */     return this.scaleY;
/*    */   }
/*    */   
/*    */   public double getScaleZ() {
/* 37 */     return this.scaleZ;
/*    */   }
/*    */ 
/*    */   
/*    */   public double get(int seed, double x, double y) {
/* 42 */     return this.noiseProperty.get(seed, x * this.scaleX, y * this.scaleY);
/*    */   }
/*    */ 
/*    */   
/*    */   public double get(int seed, double x, double y, double z) {
/* 47 */     return this.noiseProperty.get(seed, x * this.scaleX, y * this.scaleY, z * this.scaleZ);
/*    */   }
/*    */ 
/*    */   
/*    */   @Nonnull
/*    */   public String toString() {
/* 53 */     return "ScaleNoiseProperty{noiseProperty=" + String.valueOf(this.noiseProperty) + ", scaleX=" + this.scaleX + ", scaleY=" + this.scaleY + ", scaleZ=" + this.scaleZ + "}";
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\procedurallib\property\ScaleNoiseProperty.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */