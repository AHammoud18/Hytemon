/*    */ package com.hypixel.hytale.builtin.hytalegenerator.positionproviders;
/*    */ 
/*    */ import com.hypixel.hytale.math.vector.Vector3d;
/*    */ import javax.annotation.Nonnull;
/*    */ 
/*    */ public class VerticalEliminatorPositionProvider
/*    */   extends PositionProvider
/*    */ {
/*    */   private final int maxY;
/*    */   
/*    */   public VerticalEliminatorPositionProvider(int minY, int maxY, @Nonnull PositionProvider positionProvider) {
/* 12 */     this.minY = minY;
/* 13 */     this.maxY = maxY;
/* 14 */     this.positionProvider = positionProvider;
/*    */   }
/*    */   private final int minY; @Nonnull
/*    */   private final PositionProvider positionProvider;
/*    */   public void positionsIn(@Nonnull PositionProvider.Context context) {
/* 19 */     PositionProvider.Context childContext = new PositionProvider.Context(context);
/* 20 */     childContext.consumer = (positions -> {
/*    */         if (positions.y < this.minY || positions.y >= this.maxY)
/*    */           return; 
/*    */         context.consumer.accept(positions);
/*    */       });
/* 25 */     this.positionProvider.positionsIn(childContext);
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\builtin\hytalegenerator\positionproviders\VerticalEliminatorPositionProvider.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */