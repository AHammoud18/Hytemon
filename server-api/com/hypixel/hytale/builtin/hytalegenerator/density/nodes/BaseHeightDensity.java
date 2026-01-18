/*    */ package com.hypixel.hytale.builtin.hytalegenerator.density.nodes;
/*    */ 
/*    */ import com.hypixel.hytale.builtin.hytalegenerator.density.Density;
/*    */ import com.hypixel.hytale.builtin.hytalegenerator.framework.interfaces.functions.BiDouble2DoubleFunction;
/*    */ import javax.annotation.Nonnull;
/*    */ 
/*    */ public class BaseHeightDensity
/*    */   extends Density {
/*    */   @Nonnull
/*    */   private final BiDouble2DoubleFunction heightFunction;
/*    */   private final boolean isDistance;
/*    */   
/*    */   public BaseHeightDensity(@Nonnull BiDouble2DoubleFunction heightFunction, boolean isDistance) {
/* 14 */     this.heightFunction = heightFunction;
/* 15 */     this.isDistance = isDistance;
/*    */   }
/*    */ 
/*    */   
/*    */   public double process(@Nonnull Density.Context context) {
/* 20 */     if (this.isDistance) {
/* 21 */       return context.position.y - this.heightFunction.apply(context.position.x, context.position.z);
/*    */     }
/* 23 */     return this.heightFunction.apply(context.position.x, context.position.z);
/*    */   }
/*    */   
/*    */   public boolean skipInputs(double y) {
/* 27 */     return true;
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\builtin\hytalegenerator\density\nodes\BaseHeightDensity.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */