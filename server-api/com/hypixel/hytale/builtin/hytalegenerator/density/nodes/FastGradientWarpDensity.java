/*    */ package com.hypixel.hytale.builtin.hytalegenerator.density.nodes;
/*    */ 
/*    */ import com.hypixel.hytale.builtin.hytalegenerator.density.Density;
/*    */ import com.hypixel.hytale.builtin.hytalegenerator.fields.FastNoiseLite;
/*    */ import com.hypixel.hytale.math.vector.Vector3d;
/*    */ import javax.annotation.Nonnull;
/*    */ import javax.annotation.Nullable;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class FastGradientWarpDensity
/*    */   extends Density
/*    */ {
/*    */   private static final double HALF_PI = 1.5707963267948966D;
/*    */   @Nullable
/*    */   private Density input;
/*    */   private final double warpScale;
/*    */   @Nonnull
/*    */   private final FastNoiseLite warper;
/*    */   
/*    */   public FastGradientWarpDensity(@Nonnull Density input, float warpLacunarity, float warpPersistence, int warpOctaves, float warpScale, float warpFactor, int seed) {
/* 26 */     if (warpOctaves < 0.0D) {
/* 27 */       throw new IllegalArgumentException();
/*    */     }
/* 29 */     this.warpScale = warpScale;
/* 30 */     this.input = input;
/*    */     
/* 32 */     this.warper = new FastNoiseLite();
/* 33 */     this.warper.setSeed(seed);
/* 34 */     this.warper.SetFractalGain(warpPersistence);
/* 35 */     this.warper.SetFractalLacunarity(warpLacunarity);
/* 36 */     this.warper.setDomainWarpType(FastNoiseLite.DomainWarpType.OpenSimplex2);
/* 37 */     this.warper.setFractalOctaves(warpOctaves);
/* 38 */     this.warper.setDomainWarpAmp(warpFactor);
/* 39 */     this.warper.setDomainWarpFreq(warpScale);
/*    */   }
/*    */ 
/*    */   
/*    */   public double process(@Nonnull Density.Context context) {
/* 44 */     if (this.input == null) return 0.0D; 
/* 45 */     FastNoiseLite.Vector3 warpedPosition = new FastNoiseLite.Vector3(context.position.x, context.position.y, context.position.z);
/* 46 */     this.warper.DomainWarpFractalProgressive(warpedPosition);
/*    */     
/* 48 */     Density.Context childContext = new Density.Context(context);
/* 49 */     childContext.position = new Vector3d(warpedPosition.x, warpedPosition.y, warpedPosition.z);
/* 50 */     return this.input.process(childContext);
/*    */   }
/*    */ 
/*    */   
/*    */   public void setInputs(@Nonnull Density[] inputs) {
/* 55 */     if (inputs.length == 1) {
/* 56 */       this.input = inputs[0];
/*    */     } else {
/* 58 */       this.input = null;
/*    */     } 
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\builtin\hytalegenerator\density\nodes\FastGradientWarpDensity.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */