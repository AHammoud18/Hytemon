/*    */ package com.hypixel.hytale.procedurallib.property;
/*    */ 
/*    */ import java.util.Arrays;
/*    */ import javax.annotation.Nonnull;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class MaxNoiseProperty
/*    */   implements NoiseProperty
/*    */ {
/*    */   public static final double MAX_EPSILON = 0.999999D;
/*    */   protected final NoiseProperty[] noiseProperties;
/*    */   
/*    */   public MaxNoiseProperty(NoiseProperty[] noiseProperties) {
/* 16 */     this.noiseProperties = noiseProperties;
/*    */   }
/*    */   
/*    */   public NoiseProperty[] getNoiseProperties() {
/* 20 */     return this.noiseProperties;
/*    */   }
/*    */ 
/*    */   
/*    */   public double get(int seed, double x, double y) {
/* 25 */     double val = this.noiseProperties[0].get(seed, x, y);
/*    */     
/* 27 */     for (int i = 1; i < this.noiseProperties.length; i++) {
/* 28 */       if (val > 0.999999D) return 1.0D;  double d;
/* 29 */       if (val < (d = this.noiseProperties[i].get(seed, x, y))) {
/* 30 */         val = d;
/*    */       }
/*    */     } 
/* 33 */     return val;
/*    */   }
/*    */ 
/*    */   
/*    */   public double get(int seed, double x, double y, double z) {
/* 38 */     double val = this.noiseProperties[0].get(seed, x, y, z);
/*    */     
/* 40 */     for (int i = 1; i < this.noiseProperties.length; i++) {
/* 41 */       if (val > 0.999999D) return 1.0D;  double d;
/* 42 */       if (val < (d = this.noiseProperties[i].get(seed, x, y, z))) {
/* 43 */         val = d;
/*    */       }
/*    */     } 
/* 46 */     return val;
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   @Nonnull
/*    */   public String toString() {
/* 53 */     return "MaxNoiseProperty{noiseProperties=" + Arrays.toString((Object[])this.noiseProperties) + "}";
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\procedurallib\property\MaxNoiseProperty.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */