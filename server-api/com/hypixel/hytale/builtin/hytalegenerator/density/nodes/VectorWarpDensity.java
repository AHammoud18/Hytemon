/*    */ package com.hypixel.hytale.builtin.hytalegenerator.density.nodes;
/*    */ 
/*    */ import com.hypixel.hytale.builtin.hytalegenerator.density.Density;
/*    */ import com.hypixel.hytale.math.vector.Vector3d;
/*    */ import javax.annotation.Nonnull;
/*    */ import javax.annotation.Nullable;
/*    */ 
/*    */ 
/*    */ 
/*    */ public class VectorWarpDensity
/*    */   extends Density
/*    */ {
/*    */   @Nullable
/*    */   private Density input;
/*    */   @Nullable
/*    */   private Density warpInput;
/*    */   private final double warpFactor;
/*    */   @Nonnull
/*    */   private final Vector3d warpVector;
/*    */   
/*    */   public VectorWarpDensity(@Nonnull Density input, @Nonnull Density warpInput, double warpFactor, @Nonnull Vector3d warpVector) {
/* 22 */     this.input = input;
/* 23 */     this.warpInput = warpInput;
/* 24 */     this.warpFactor = warpFactor;
/* 25 */     this.warpVector = warpVector;
/*    */   }
/*    */ 
/*    */   
/*    */   public double process(@Nonnull Density.Context context) {
/* 30 */     if (this.input == null) return 0.0D; 
/* 31 */     if (this.warpInput == null) {
/* 32 */       return this.input.process(context);
/*    */     }
/* 34 */     double warp = this.warpInput.process(context);
/* 35 */     warp *= this.warpFactor;
/*    */     
/* 37 */     Vector3d samplePoint = this.warpVector.clone();
/* 38 */     samplePoint.setLength(1.0D);
/* 39 */     samplePoint.scale(warp);
/* 40 */     samplePoint.add(context.position);
/*    */     
/* 42 */     Density.Context childContext = new Density.Context(context);
/* 43 */     childContext.position = samplePoint;
/*    */     
/* 45 */     return this.input.process(childContext);
/*    */   }
/*    */ 
/*    */   
/*    */   public void setInputs(@Nonnull Density[] inputs) {
/* 50 */     if (inputs.length == 0) this.input = null; 
/* 51 */     this.input = inputs[0];
/* 52 */     if (inputs.length < 2) this.warpInput = null; 
/* 53 */     this.warpInput = inputs[1];
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\builtin\hytalegenerator\density\nodes\VectorWarpDensity.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */