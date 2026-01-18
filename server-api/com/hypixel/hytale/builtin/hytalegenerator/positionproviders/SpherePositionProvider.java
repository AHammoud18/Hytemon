/*    */ package com.hypixel.hytale.builtin.hytalegenerator.positionproviders;
/*    */ 
/*    */ import com.hypixel.hytale.builtin.hytalegenerator.VectorUtil;
/*    */ import com.hypixel.hytale.math.vector.Vector3d;
/*    */ import javax.annotation.Nonnull;
/*    */ 
/*    */ public class SpherePositionProvider
/*    */   extends PositionProvider {
/*    */   @Nonnull
/*    */   private final PositionProvider positionProvider;
/*    */   private final double range;
/*    */   
/*    */   public SpherePositionProvider(@Nonnull PositionProvider positionProvider, double range) {
/* 14 */     this.positionProvider = positionProvider;
/* 15 */     this.range = range;
/*    */   }
/*    */ 
/*    */   
/*    */   public void positionsIn(@Nonnull PositionProvider.Context context) {
/* 20 */     PositionProvider.Context childContext = new PositionProvider.Context(context);
/* 21 */     childContext.consumer = (position -> {
/*    */         double distance = position.length();
/*    */         if (VectorUtil.isInside(position, context.minInclusive, context.maxExclusive) && distance <= this.range) {
/*    */           context.consumer.accept(position);
/*    */         }
/*    */       });
/* 27 */     this.positionProvider.positionsIn(childContext);
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\builtin\hytalegenerator\positionproviders\SpherePositionProvider.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */