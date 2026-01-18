/*    */ package com.hypixel.hytale.builtin.hytalegenerator.density.nodes;
/*    */ 
/*    */ import com.hypixel.hytale.builtin.hytalegenerator.density.Density;
/*    */ import com.hypixel.hytale.math.vector.Vector3d;
/*    */ import javax.annotation.Nonnull;
/*    */ import javax.annotation.Nullable;
/*    */ 
/*    */ public class ScaleDensity
/*    */   extends Density {
/*    */   @Nonnull
/*    */   private final Vector3d scale;
/*    */   private final boolean isInvalid;
/*    */   @Nullable
/*    */   private Density input;
/*    */   
/*    */   public ScaleDensity(double scaleX, double scaleY, double scaleZ, Density input) {
/* 17 */     this.scale = new Vector3d(1.0D / scaleX, 1.0D / scaleY, 1.0D / scaleZ);
/* 18 */     this.isInvalid = (scaleX == 0.0D || scaleY == 0.0D || scaleZ == 0.0D);
/* 19 */     this.input = input;
/*    */   }
/*    */ 
/*    */   
/*    */   public double process(@Nonnull Density.Context context) {
/* 24 */     if (this.input == null) return 0.0D; 
/* 25 */     if (this.isInvalid) return 0.0D;
/*    */     
/* 27 */     Vector3d scaledPosition = context.position.clone();
/* 28 */     scaledPosition.scale(this.scale);
/*    */     
/* 30 */     Density.Context childContext = new Density.Context(context);
/* 31 */     childContext.position = scaledPosition;
/*    */     
/* 33 */     return this.input.process(childContext);
/*    */   }
/*    */ 
/*    */   
/*    */   public void setInputs(@Nonnull Density[] inputs) {
/* 38 */     if (inputs.length == 0) this.input = null; 
/* 39 */     this.input = inputs[0];
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\builtin\hytalegenerator\density\nodes\ScaleDensity.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */