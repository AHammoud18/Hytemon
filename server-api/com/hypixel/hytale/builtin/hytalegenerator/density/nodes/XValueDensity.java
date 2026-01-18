/*    */ package com.hypixel.hytale.builtin.hytalegenerator.density.nodes;
/*    */ 
/*    */ import com.hypixel.hytale.builtin.hytalegenerator.density.Density;
/*    */ import javax.annotation.Nonnull;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class XValueDensity
/*    */   extends Density
/*    */ {
/*    */   public double process(@Nonnull Density.Context context) {
/* 14 */     return context.position.x;
/*    */   }
/*    */   
/*    */   public void setInputs(@Nonnull Density[] inputs) {}
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\builtin\hytalegenerator\density\nodes\XValueDensity.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */