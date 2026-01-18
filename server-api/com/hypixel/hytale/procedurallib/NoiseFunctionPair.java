/*    */ package com.hypixel.hytale.procedurallib;
/*    */ 
/*    */ import javax.annotation.Nonnull;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class NoiseFunctionPair
/*    */   implements NoiseFunction
/*    */ {
/*    */   protected NoiseFunction2d noiseFunction2d;
/*    */   protected NoiseFunction3d noiseFunction3d;
/*    */   
/*    */   public NoiseFunctionPair() {}
/*    */   
/*    */   public NoiseFunctionPair(NoiseFunction2d noiseFunction2d, NoiseFunction3d noiseFunction3d) {
/* 17 */     this.noiseFunction2d = noiseFunction2d;
/* 18 */     this.noiseFunction3d = noiseFunction3d;
/*    */   }
/*    */   
/*    */   public NoiseFunction2d getNoiseFunction2d() {
/* 22 */     return this.noiseFunction2d;
/*    */   }
/*    */   
/*    */   public void setNoiseFunction2d(NoiseFunction2d noiseFunction2d) {
/* 26 */     this.noiseFunction2d = noiseFunction2d;
/*    */   }
/*    */   
/*    */   public NoiseFunction3d getNoiseFunction3d() {
/* 30 */     return this.noiseFunction3d;
/*    */   }
/*    */   
/*    */   public void setNoiseFunction3d(NoiseFunction3d noiseFunction3d) {
/* 34 */     this.noiseFunction3d = noiseFunction3d;
/*    */   }
/*    */ 
/*    */   
/*    */   public double get(int seed, int offsetSeed, double x, double y) {
/* 39 */     return this.noiseFunction2d.get(seed, offsetSeed, x, y);
/*    */   }
/*    */ 
/*    */   
/*    */   public double get(int seed, int offsetSeed, double x, double y, double z) {
/* 44 */     return this.noiseFunction3d.get(seed, offsetSeed, x, y, z);
/*    */   }
/*    */ 
/*    */   
/*    */   @Nonnull
/*    */   public String toString() {
/* 50 */     return "NoiseFunctionPair{noiseFunction2d=" + String.valueOf(this.noiseFunction2d) + ", noiseFunction3d=" + String.valueOf(this.noiseFunction3d) + "}";
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\procedurallib\NoiseFunctionPair.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */