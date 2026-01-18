/*    */ package com.hypixel.hytale.builtin.hytalegenerator.density.nodes;
/*    */ 
/*    */ import com.hypixel.hytale.builtin.hytalegenerator.density.Density;
/*    */ import com.hypixel.hytale.math.vector.Vector3d;
/*    */ import javax.annotation.Nonnull;
/*    */ 
/*    */ public class XOverrideDensity
/*    */   extends Density
/*    */ {
/*    */   @Nonnull
/*    */   private Density input;
/*    */   private final double value;
/*    */   
/*    */   public XOverrideDensity(@Nonnull Density input, double value) {
/* 15 */     this.input = input;
/* 16 */     this.value = value;
/*    */   }
/*    */ 
/*    */   
/*    */   public double process(@Nonnull Density.Context context) {
/* 21 */     Vector3d childPosition = new Vector3d(this.value, context.position.y, context.position.z);
/* 22 */     Density.Context childContext = new Density.Context(context);
/* 23 */     childContext.position = childPosition;
/*    */     
/* 25 */     return this.input.process(childContext);
/*    */   }
/*    */ 
/*    */   
/*    */   public void setInputs(@Nonnull Density[] inputs) {
/* 30 */     assert inputs.length == 1;
/* 31 */     assert inputs[0] != null;
/* 32 */     this.input = inputs[0];
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\builtin\hytalegenerator\density\nodes\XOverrideDensity.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */