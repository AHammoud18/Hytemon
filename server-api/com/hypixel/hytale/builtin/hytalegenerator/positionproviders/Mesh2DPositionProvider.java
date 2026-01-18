/*    */ package com.hypixel.hytale.builtin.hytalegenerator.positionproviders;
/*    */ 
/*    */ import com.hypixel.hytale.builtin.hytalegenerator.fields.points.PointProvider;
/*    */ import com.hypixel.hytale.math.vector.Vector2d;
/*    */ import com.hypixel.hytale.math.vector.Vector3d;
/*    */ import javax.annotation.Nonnull;
/*    */ 
/*    */ public class Mesh2DPositionProvider
/*    */   extends PositionProvider {
/*    */   @Nonnull
/*    */   private final PointProvider pointGenerator;
/*    */   private final int y;
/*    */   
/*    */   public Mesh2DPositionProvider(@Nonnull PointProvider positionProvider, int y) {
/* 15 */     this.pointGenerator = positionProvider;
/* 16 */     this.y = y;
/*    */   }
/*    */ 
/*    */   
/*    */   public void positionsIn(@Nonnull PositionProvider.Context context) {
/* 21 */     if (context.minInclusive.y > this.y || context.maxExclusive.y <= this.y) {
/*    */       return;
/*    */     }
/* 24 */     Vector2d min2d = new Vector2d(context.minInclusive.x, context.minInclusive.z);
/* 25 */     Vector2d max2d = new Vector2d(context.maxExclusive.x, context.maxExclusive.z);
/* 26 */     this.pointGenerator.points2d(min2d, max2d, point -> context.consumer.accept(new Vector3d(point.x, this.y, point.y)));
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\builtin\hytalegenerator\positionproviders\Mesh2DPositionProvider.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */