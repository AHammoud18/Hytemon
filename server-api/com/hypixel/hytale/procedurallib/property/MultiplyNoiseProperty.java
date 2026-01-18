/*    */ package com.hypixel.hytale.procedurallib.property;
/*    */ 
/*    */ import java.util.Arrays;
/*    */ import javax.annotation.Nonnull;
/*    */ 
/*    */ 
/*    */ 
/*    */ public class MultiplyNoiseProperty
/*    */   implements NoiseProperty
/*    */ {
/*    */   protected final NoiseProperty[] noiseProperties;
/*    */   
/*    */   public MultiplyNoiseProperty(NoiseProperty[] noiseProperties) {
/* 14 */     this.noiseProperties = noiseProperties;
/*    */   }
/*    */   
/*    */   public NoiseProperty[] getNoiseProperties() {
/* 18 */     return this.noiseProperties;
/*    */   }
/*    */ 
/*    */   
/*    */   public double get(int seed, double x, double y) {
/* 23 */     double val = this.noiseProperties[0].get(seed, x, y);
/* 24 */     for (int i = 1; i < this.noiseProperties.length; i++) {
/* 25 */       val *= this.noiseProperties[i].get(seed, x, y);
/*    */     }
/* 27 */     return val;
/*    */   }
/*    */ 
/*    */   
/*    */   public double get(int seed, double x, double y, double z) {
/* 32 */     double val = this.noiseProperties[0].get(seed, x, y, z);
/* 33 */     for (int i = 1; i < this.noiseProperties.length; i++) {
/* 34 */       val *= this.noiseProperties[i].get(seed, x, y, z);
/*    */     }
/* 36 */     return val;
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   @Nonnull
/*    */   public String toString() {
/* 43 */     return "MultiplyNoiseProperty{noiseProperties=" + Arrays.toString((Object[])this.noiseProperties) + "}";
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\procedurallib\property\MultiplyNoiseProperty.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */