/*    */ package com.hypixel.hytale.procedurallib.property;
/*    */ 
/*    */ 
/*    */ 
/*    */ public class InvertNoiseProperty
/*    */   implements NoiseProperty
/*    */ {
/*    */   protected final NoiseProperty noiseProperty;
/*    */   
/*    */   public InvertNoiseProperty(NoiseProperty noiseProperty) {
/* 11 */     this.noiseProperty = noiseProperty;
/*    */   }
/*    */ 
/*    */   
/*    */   public double get(int seed, double x, double y) {
/* 16 */     return 1.0D - this.noiseProperty.get(seed, x, y);
/*    */   }
/*    */ 
/*    */   
/*    */   public double get(int seed, double x, double y, double z) {
/* 21 */     return 1.0D - this.noiseProperty.get(seed, x, y, z);
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\procedurallib\property\InvertNoiseProperty.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */