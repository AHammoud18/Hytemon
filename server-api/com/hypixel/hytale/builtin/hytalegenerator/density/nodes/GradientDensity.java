/*    */ package com.hypixel.hytale.builtin.hytalegenerator.density.nodes;
/*    */ 
/*    */ import com.hypixel.hytale.builtin.hytalegenerator.VectorUtil;
/*    */ import com.hypixel.hytale.builtin.hytalegenerator.density.Density;
/*    */ import com.hypixel.hytale.math.vector.Vector3d;
/*    */ import javax.annotation.Nonnull;
/*    */ import javax.annotation.Nullable;
/*    */ 
/*    */ public class GradientDensity
/*    */   extends Density
/*    */ {
/*    */   private static final double HALF_PI = 1.5707963267948966D;
/*    */   @Nullable
/*    */   private Density input;
/*    */   private final double slopeRange;
/*    */   @Nonnull
/*    */   private final Vector3d axis;
/*    */   
/*    */   public GradientDensity(@Nonnull Density input, double slopeRange, @Nonnull Vector3d axis) {
/* 20 */     if (slopeRange <= 0.0D) {
/* 21 */       throw new IllegalArgumentException();
/*    */     }
/* 23 */     this.axis = axis.clone();
/* 24 */     this.slopeRange = slopeRange;
/* 25 */     this.input = input;
/*    */   }
/*    */ 
/*    */   
/*    */   public double process(@Nonnull Density.Context context) {
/* 30 */     if (this.input == null) return 0.0D;
/*    */     
/* 32 */     double valueAtOrigin = this.input.process(context);
/*    */     
/* 34 */     double maxX = context.position.x + this.slopeRange;
/* 35 */     double maxY = context.position.y + this.slopeRange;
/* 36 */     double maxZ = context.position.z + this.slopeRange;
/*    */     
/* 38 */     Density.Context childContext = new Density.Context(context);
/*    */     
/* 40 */     childContext.position = new Vector3d(maxX, context.position.y, context.position.z);
/* 41 */     double deltaX = Math.abs(this.input.process(childContext) - valueAtOrigin);
/*    */     
/* 43 */     childContext.position = new Vector3d(context.position.x, maxY, context.position.z);
/* 44 */     double deltaY = Math.abs(this.input.process(childContext) - valueAtOrigin);
/*    */     
/* 46 */     childContext.position = new Vector3d(context.position.x, context.position.y, maxZ);
/* 47 */     double deltaZ = Math.abs(this.input.process(childContext) - valueAtOrigin);
/*    */     
/* 49 */     Vector3d slopeDirection = new Vector3d(deltaX, deltaY, deltaZ);
/*    */     
/* 51 */     double slopeAngle = VectorUtil.angle(this.axis, slopeDirection);
/* 52 */     if (slopeAngle > 1.5707963267948966D) {
/* 53 */       slopeAngle = Math.PI - slopeAngle;
/*    */     }
/*    */     
/* 56 */     slopeAngle /= 1.5707963267948966D;
/* 57 */     slopeAngle *= 90.0D;
/*    */     
/* 59 */     return slopeAngle;
/*    */   }
/*    */ 
/*    */   
/*    */   public void setInputs(@Nonnull Density[] inputs) {
/* 64 */     if (inputs.length == 0) this.input = null; 
/* 65 */     this.input = inputs[0];
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\builtin\hytalegenerator\density\nodes\GradientDensity.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */