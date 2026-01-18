/*    */ package com.hypixel.hytale.builtin.hytalegenerator.vectorproviders;
/*    */ 
/*    */ import com.hypixel.hytale.builtin.hytalegenerator.density.Density;
/*    */ import com.hypixel.hytale.math.vector.Vector3d;
/*    */ import javax.annotation.Nonnull;
/*    */ 
/*    */ public class DensityGradientVectorProvider
/*    */   extends VectorProvider {
/*    */   @Nonnull
/*    */   private final Density density;
/*    */   private final double sampleDistance;
/*    */   
/*    */   public DensityGradientVectorProvider(@Nonnull Density density, double sampleDistance) {
/* 14 */     assert sampleDistance >= 0.0D;
/*    */     
/* 16 */     this.density = density;
/* 17 */     this.sampleDistance = Math.max(0.0D, sampleDistance);
/*    */   }
/*    */   
/*    */   @Nonnull
/*    */   public Vector3d process(@Nonnull VectorProvider.Context context) {
/* 22 */     double valueAtOrigin = this.density.process(new Density.Context(context));
/*    */     
/* 24 */     double maxX = context.position.x + this.sampleDistance;
/* 25 */     double maxY = context.position.y + this.sampleDistance;
/* 26 */     double maxZ = context.position.z + this.sampleDistance;
/*    */     
/* 28 */     Density.Context childContext = new Density.Context(context);
/*    */     
/* 30 */     childContext.position = new Vector3d(maxX, context.position.y, context.position.z);
/* 31 */     double deltaX = this.density.process(childContext) - valueAtOrigin;
/*    */     
/* 33 */     childContext.position = new Vector3d(context.position.x, maxY, context.position.z);
/* 34 */     double deltaY = this.density.process(childContext) - valueAtOrigin;
/*    */     
/* 36 */     childContext.position = new Vector3d(context.position.x, context.position.y, maxZ);
/* 37 */     double deltaZ = this.density.process(childContext) - valueAtOrigin;
/*    */     
/* 39 */     return new Vector3d(deltaX, deltaY, deltaZ);
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\builtin\hytalegenerator\vectorproviders\DensityGradientVectorProvider.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */