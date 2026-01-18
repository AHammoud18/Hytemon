/*    */ package com.hypixel.hytale.builtin.hytalegenerator.density.nodes;
/*    */ 
/*    */ import com.hypixel.hytale.builtin.hytalegenerator.density.Density;
/*    */ import it.unimi.dsi.fastutil.doubles.Double2DoubleFunction;
/*    */ import javax.annotation.Nonnull;
/*    */ 
/*    */ public class CubeDensity
/*    */   extends Density {
/*    */   @Nonnull
/*    */   private final Double2DoubleFunction falloffFunction;
/*    */   
/*    */   public CubeDensity(@Nonnull Double2DoubleFunction falloffFunction) {
/* 13 */     this.falloffFunction = falloffFunction;
/*    */   }
/*    */ 
/*    */   
/*    */   public double process(@Nonnull Density.Context context) {
/* 18 */     double distance = Math.max(Math.abs(context.position.x), Math.abs(context.position.y));
/* 19 */     distance = Math.max(distance, Math.abs(context.position.z));
/* 20 */     return this.falloffFunction.get(distance);
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\builtin\hytalegenerator\density\nodes\CubeDensity.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */