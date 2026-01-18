/*    */ package com.hypixel.hytale.builtin.hytalegenerator.positionproviders;
/*    */ 
/*    */ import com.hypixel.hytale.builtin.hytalegenerator.fields.points.PointProvider;
/*    */ import javax.annotation.Nonnull;
/*    */ 
/*    */ public class Mesh3DPositionProvider
/*    */   extends PositionProvider {
/*    */   @Nonnull
/*    */   private final PointProvider pointGenerator;
/*    */   
/*    */   public Mesh3DPositionProvider(@Nonnull PointProvider positionProvider) {
/* 12 */     this.pointGenerator = positionProvider;
/*    */   }
/*    */ 
/*    */   
/*    */   public void positionsIn(@Nonnull PositionProvider.Context context) {
/* 17 */     this.pointGenerator.points3d(context.minInclusive, context.maxExclusive, context.consumer);
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\builtin\hytalegenerator\positionproviders\Mesh3DPositionProvider.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */