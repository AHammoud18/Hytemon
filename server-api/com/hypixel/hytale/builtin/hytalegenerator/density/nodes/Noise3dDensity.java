/*    */ package com.hypixel.hytale.builtin.hytalegenerator.density.nodes;
/*    */ 
/*    */ import com.hypixel.hytale.builtin.hytalegenerator.density.Density;
/*    */ import com.hypixel.hytale.builtin.hytalegenerator.fields.noise.NoiseField;
/*    */ import javax.annotation.Nonnull;
/*    */ 
/*    */ public class Noise3dDensity
/*    */   extends Density {
/*    */   @Nonnull
/*    */   private final NoiseField noise;
/*    */   
/*    */   public Noise3dDensity(@Nonnull NoiseField noise) {
/* 13 */     this.noise = noise;
/*    */   }
/*    */ 
/*    */   
/*    */   public double process(@Nonnull Density.Context context) {
/* 18 */     return this.noise.valueAt(context.position.x, context.position.y, context.position.z);
/*    */   }
/*    */   
/*    */   public void setInputs(@Nonnull Density[] inputs) {}
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\builtin\hytalegenerator\density\nodes\Noise3dDensity.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */