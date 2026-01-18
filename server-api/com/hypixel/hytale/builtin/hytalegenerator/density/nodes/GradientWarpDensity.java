/*    */ package com.hypixel.hytale.builtin.hytalegenerator.density.nodes;
/*    */ 
/*    */ import com.hypixel.hytale.builtin.hytalegenerator.density.Density;
/*    */ import com.hypixel.hytale.math.vector.Vector3d;
/*    */ import javax.annotation.Nonnull;
/*    */ import javax.annotation.Nullable;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class GradientWarpDensity
/*    */   extends Density
/*    */ {
/*    */   private static final double HALF_PI = 1.5707963267948966D;
/*    */   @Nullable
/*    */   private Density input;
/*    */   @Nullable
/*    */   private Density warpInput;
/*    */   private final double slopeRange;
/*    */   private final double warpFactor;
/*    */   
/*    */   public GradientWarpDensity(@Nonnull Density input, @Nonnull Density warpInput, double slopeRange, double warpFactor) {
/* 23 */     if (slopeRange <= 0.0D) {
/* 24 */       throw new IllegalArgumentException();
/*    */     }
/* 26 */     this.slopeRange = slopeRange;
/* 27 */     this.warpFactor = warpFactor;
/* 28 */     this.input = input;
/* 29 */     this.warpInput = warpInput;
/*    */   }
/*    */ 
/*    */   
/*    */   public double process(@Nonnull Density.Context context) {
/* 34 */     if (this.input == null)
/* 35 */       return 0.0D; 
/* 36 */     if (this.warpInput == null) {
/* 37 */       return this.input.process(context);
/*    */     }
/* 39 */     double valueAtOrigin = this.warpInput.process(context);
/*    */     
/* 41 */     double maxX = context.position.x + this.slopeRange;
/* 42 */     double maxY = context.position.y + this.slopeRange;
/* 43 */     double maxZ = context.position.z + this.slopeRange;
/*    */     
/* 45 */     Density.Context childContext = new Density.Context(context);
/*    */     
/* 47 */     childContext.position = new Vector3d(maxX, context.position.y, context.position.z);
/* 48 */     double deltaX = this.warpInput.process(childContext) - valueAtOrigin;
/*    */     
/* 50 */     childContext.position = new Vector3d(context.position.x, maxY, context.position.z);
/* 51 */     double deltaY = this.warpInput.process(childContext) - valueAtOrigin;
/*    */     
/* 53 */     childContext.position = new Vector3d(context.position.x, context.position.z, maxZ);
/* 54 */     double deltaZ = this.warpInput.process(childContext) - valueAtOrigin;
/*    */     
/* 56 */     Vector3d gradient = new Vector3d(deltaX, deltaY, deltaZ);
/* 57 */     gradient.scale(1.0D / this.slopeRange);
/* 58 */     gradient.scale(this.warpFactor);
/* 59 */     gradient.add(context.position.x, context.position.y, context.position.z);
/*    */     
/* 61 */     childContext.position = gradient;
/* 62 */     return this.input.process(childContext);
/*    */   }
/*    */ 
/*    */   
/*    */   public void setInputs(@Nonnull Density[] inputs) {
/* 67 */     if (inputs.length == 0) this.input = null; 
/* 68 */     this.input = inputs[0];
/* 69 */     if (inputs.length < 2) this.warpInput = null; 
/* 70 */     this.warpInput = inputs[1];
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\builtin\hytalegenerator\density\nodes\GradientWarpDensity.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */