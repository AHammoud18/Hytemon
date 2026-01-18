/*    */ package com.hypixel.hytale.builtin.hytalegenerator.density.nodes;
/*    */ 
/*    */ import com.hypixel.hytale.builtin.hytalegenerator.density.Density;
/*    */ import com.hypixel.hytale.math.vector.Vector3d;
/*    */ import javax.annotation.Nonnull;
/*    */ import javax.annotation.Nullable;
/*    */ 
/*    */ public class AnchorDensity
/*    */   extends Density
/*    */ {
/*    */   @Nullable
/*    */   private Density input;
/*    */   private final boolean isReversed;
/*    */   
/*    */   public AnchorDensity(Density input, boolean isReversed) {
/* 16 */     this.input = input;
/* 17 */     this.isReversed = isReversed;
/*    */   }
/*    */ 
/*    */   
/*    */   public double process(@Nonnull Density.Context context) {
/* 22 */     Vector3d anchor = context.densityAnchor;
/*    */     
/* 24 */     if (anchor == null) {
/* 25 */       return this.input.process(context);
/*    */     }
/* 27 */     if (this.isReversed) {
/* 28 */       Vector3d vector3d = new Vector3d(context.position.x + anchor.x, context.position.y + anchor.y, context.position.z + anchor.z);
/* 29 */       Density.Context context1 = new Density.Context(context);
/* 30 */       context1.position = vector3d;
/* 31 */       return this.input.process(context1);
/*    */     } 
/*    */     
/* 34 */     Vector3d childPosition = new Vector3d(context.position.x - anchor.x, context.position.y - anchor.y, context.position.z - anchor.z);
/* 35 */     Density.Context childContext = new Density.Context(context);
/* 36 */     childContext.position = childPosition;
/* 37 */     return this.input.process(childContext);
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public void setInputs(@Nonnull Density[] inputs) {
/* 43 */     if (inputs.length == 0) this.input = null; 
/* 44 */     this.input = inputs[0];
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\builtin\hytalegenerator\density\nodes\AnchorDensity.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */