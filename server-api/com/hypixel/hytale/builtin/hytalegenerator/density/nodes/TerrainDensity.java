/*    */ package com.hypixel.hytale.builtin.hytalegenerator.density.nodes;
/*    */ 
/*    */ import com.hypixel.hytale.builtin.hytalegenerator.density.Density;
/*    */ import javax.annotation.Nonnull;
/*    */ 
/*    */ 
/*    */ public class TerrainDensity
/*    */   extends Density
/*    */ {
/*    */   public double process(@Nonnull Density.Context context) {
/* 11 */     if (context.terrainDensityProvider == null) {
/* 12 */       return 0.0D;
/*    */     }
/* 14 */     return context.terrainDensityProvider.get(context.position.toVector3i(), context.workerId);
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\builtin\hytalegenerator\density\nodes\TerrainDensity.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */