/*    */ package com.hypixel.hytale.builtin.hytalegenerator.density.nodes;
/*    */ 
/*    */ import com.hypixel.hytale.builtin.hytalegenerator.VectorUtil;
/*    */ import com.hypixel.hytale.builtin.hytalegenerator.density.Density;
/*    */ import com.hypixel.hytale.builtin.hytalegenerator.vectorproviders.VectorProvider;
/*    */ import com.hypixel.hytale.math.vector.Vector3d;
/*    */ import javax.annotation.Nonnull;
/*    */ 
/*    */ public class AngleDensity
/*    */   extends Density
/*    */ {
/*    */   private static final double HALF_PI = 1.5707963267948966D;
/*    */   @Nonnull
/*    */   private VectorProvider vectorProvider;
/*    */   @Nonnull
/*    */   private final Vector3d vector;
/*    */   private final boolean toAxis;
/*    */   
/*    */   public AngleDensity(@Nonnull VectorProvider vectorProvider, @Nonnull Vector3d vector, boolean toAxis) {
/* 20 */     this.vector = vector.clone();
/* 21 */     this.vectorProvider = vectorProvider;
/* 22 */     this.toAxis = toAxis;
/*    */   }
/*    */ 
/*    */   
/*    */   public double process(@Nonnull Density.Context context) {
/* 27 */     Vector3d otherVector = this.vectorProvider.process(new VectorProvider.Context(context));
/*    */     
/* 29 */     double slopeAngle = VectorUtil.angle(this.vector, otherVector);
/*    */     
/* 31 */     if (this.toAxis && slopeAngle > 1.5707963267948966D) {
/* 32 */       slopeAngle = Math.PI - slopeAngle;
/*    */     }
/*    */ 
/*    */     
/* 36 */     slopeAngle /= Math.PI;
/* 37 */     slopeAngle *= 180.0D;
/*    */     
/* 39 */     return slopeAngle;
/*    */   }
/*    */   
/*    */   public void setInputs(@Nonnull Density[] inputs) {}
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\builtin\hytalegenerator\density\nodes\AngleDensity.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */