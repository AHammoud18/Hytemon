/*    */ package com.hypixel.hytale.procedurallib.property;
/*    */ 
/*    */ import javax.annotation.Nonnull;
/*    */ 
/*    */ public class OffsetNoiseProperty
/*    */   implements NoiseProperty
/*    */ {
/*    */   protected final NoiseProperty noiseProperty;
/*    */   protected final double offsetX;
/*    */   protected final double offsetY;
/*    */   protected final double offsetZ;
/*    */   
/*    */   public OffsetNoiseProperty(NoiseProperty noiseProperty, double offset) {
/* 14 */     this(noiseProperty, offset, offset, offset);
/*    */   }
/*    */   
/*    */   public OffsetNoiseProperty(NoiseProperty noiseProperty, double offsetX, double offsetY, double offsetZ) {
/* 18 */     this.noiseProperty = noiseProperty;
/* 19 */     this.offsetX = offsetX;
/* 20 */     this.offsetY = offsetY;
/* 21 */     this.offsetZ = offsetZ;
/*    */   }
/*    */   
/*    */   public NoiseProperty getNoiseProperty() {
/* 25 */     return this.noiseProperty;
/*    */   }
/*    */   
/*    */   public double getOffsetX() {
/* 29 */     return this.offsetX;
/*    */   }
/*    */   
/*    */   public double getOffsetY() {
/* 33 */     return this.offsetY;
/*    */   }
/*    */   
/*    */   public double getOffsetZ() {
/* 37 */     return this.offsetZ;
/*    */   }
/*    */ 
/*    */   
/*    */   public double get(int seed, double x, double y) {
/* 42 */     return this.noiseProperty.get(seed, x + this.offsetX, y + this.offsetY);
/*    */   }
/*    */ 
/*    */   
/*    */   public double get(int seed, double x, double y, double z) {
/* 47 */     return this.noiseProperty.get(seed, x + this.offsetX, y + this.offsetY, z + this.offsetZ);
/*    */   }
/*    */ 
/*    */   
/*    */   @Nonnull
/*    */   public String toString() {
/* 53 */     return "OffsetNoiseProperty{noiseProperty=" + String.valueOf(this.noiseProperty) + ", offsetX=" + this.offsetX + ", offsetY=" + this.offsetY + ", offsetZ=" + this.offsetZ + "}";
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\procedurallib\property\OffsetNoiseProperty.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */