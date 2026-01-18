/*    */ package com.hypixel.hytale.builtin.hytalegenerator.density.nodes;
/*    */ 
/*    */ import com.hypixel.hytale.builtin.hytalegenerator.density.Density;
/*    */ import javax.annotation.Nonnull;
/*    */ 
/*    */ public class ConstantValueDensity
/*    */   extends Density {
/*    */   private final double value;
/*    */   
/*    */   public ConstantValueDensity(double value) {
/* 11 */     this.value = value;
/*    */   }
/*    */ 
/*    */   
/*    */   public double process(@Nonnull Density.Context context) {
/* 16 */     return this.value;
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\builtin\hytalegenerator\density\nodes\ConstantValueDensity.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */