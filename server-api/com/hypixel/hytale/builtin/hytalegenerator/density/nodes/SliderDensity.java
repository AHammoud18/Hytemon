/*    */ package com.hypixel.hytale.builtin.hytalegenerator.density.nodes;
/*    */ 
/*    */ import com.hypixel.hytale.builtin.hytalegenerator.density.Density;
/*    */ import com.hypixel.hytale.math.vector.Vector3d;
/*    */ import javax.annotation.Nonnull;
/*    */ import javax.annotation.Nullable;
/*    */ 
/*    */ public class SliderDensity
/*    */   extends Density {
/*    */   private final double slideX;
/*    */   private final double slideY;
/*    */   private final double slideZ;
/*    */   @Nullable
/*    */   private Density input;
/*    */   
/*    */   public SliderDensity(double slideX, double slideY, double slideZ, Density input) {
/* 17 */     this.slideX = slideX;
/* 18 */     this.slideY = slideY;
/* 19 */     this.slideZ = slideZ;
/* 20 */     this.input = input;
/*    */   }
/*    */ 
/*    */   
/*    */   public double process(@Nonnull Density.Context context) {
/* 25 */     if (this.input == null) return 0.0D;
/*    */     
/* 27 */     Vector3d childPosition = new Vector3d(context.position.x - this.slideX, context.position.y - this.slideY, context.position.z - this.slideZ);
/* 28 */     Density.Context childContext = new Density.Context(context);
/* 29 */     childContext.position = childPosition;
/*    */     
/* 31 */     return this.input.process(childContext);
/*    */   }
/*    */ 
/*    */   
/*    */   public void setInputs(@Nonnull Density[] inputs) {
/* 36 */     if (inputs.length == 0) this.input = null; 
/* 37 */     this.input = inputs[0];
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\builtin\hytalegenerator\density\nodes\SliderDensity.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */