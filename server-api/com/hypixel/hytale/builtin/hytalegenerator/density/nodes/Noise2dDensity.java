/*    */ package com.hypixel.hytale.builtin.hytalegenerator.density.nodes;
/*    */ 
/*    */ import com.hypixel.hytale.builtin.hytalegenerator.density.Density;
/*    */ import com.hypixel.hytale.builtin.hytalegenerator.fields.noise.NoiseField;
/*    */ import javax.annotation.Nonnull;
/*    */ 
/*    */ public class Noise2dDensity
/*    */   extends Density {
/*    */   private NoiseField noise;
/*    */   
/*    */   public Noise2dDensity(@Nonnull NoiseField noise) {
/* 12 */     this.noise = noise;
/*    */   }
/*    */ 
/*    */   
/*    */   public double process(@Nonnull Density.Context context) {
/* 17 */     return this.noise.valueAt(context.position.x, context.position.z);
/*    */   }
/*    */   
/*    */   public void setInputs(@Nonnull Density[] inputs) {}
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\builtin\hytalegenerator\density\nodes\Noise2dDensity.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */