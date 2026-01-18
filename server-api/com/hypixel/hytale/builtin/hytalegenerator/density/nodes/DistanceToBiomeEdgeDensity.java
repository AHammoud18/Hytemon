/*    */ package com.hypixel.hytale.builtin.hytalegenerator.density.nodes;
/*    */ 
/*    */ import com.hypixel.hytale.builtin.hytalegenerator.density.Density;
/*    */ import javax.annotation.Nonnull;
/*    */ 
/*    */ public class DistanceToBiomeEdgeDensity
/*    */   extends Density
/*    */ {
/*    */   public double process(@Nonnull Density.Context context) {
/* 10 */     return context.distanceToBiomeEdge;
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\builtin\hytalegenerator\density\nodes\DistanceToBiomeEdgeDensity.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */