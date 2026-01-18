/*    */ package com.hypixel.hytale.builtin.hytalegenerator.positionproviders;
/*    */ 
/*    */ import com.hypixel.hytale.builtin.hytalegenerator.VectorUtil;
/*    */ import com.hypixel.hytale.math.vector.Vector3d;
/*    */ import javax.annotation.Nonnull;
/*    */ 
/*    */ public class AnchorPositionProvider
/*    */   extends PositionProvider
/*    */ {
/*    */   @Nonnull
/*    */   private final PositionProvider positionProvider;
/*    */   private final boolean isReversed;
/*    */   
/*    */   public AnchorPositionProvider(@Nonnull PositionProvider positionProvider, boolean isReversed) {
/* 15 */     this.positionProvider = positionProvider;
/* 16 */     this.isReversed = isReversed;
/*    */   }
/*    */ 
/*    */   
/*    */   public void positionsIn(@Nonnull PositionProvider.Context context) {
/* 21 */     if (context == null)
/*    */       return; 
/* 23 */     Vector3d anchor = context.anchor;
/* 24 */     if (anchor == null) {
/*    */       return;
/*    */     }
/*    */ 
/*    */     
/* 29 */     Vector3d offsetMin = this.isReversed ? context.minInclusive.clone().add(anchor) : context.minInclusive.clone().addScaled(anchor, -1.0D);
/*    */ 
/*    */     
/* 32 */     Vector3d offsetMax = this.isReversed ? context.maxExclusive.clone().add(anchor) : context.maxExclusive.clone().addScaled(anchor, -1.0D);
/*    */     
/* 34 */     PositionProvider.Context childContext = new PositionProvider.Context(offsetMin, offsetMax, p -> { Vector3d newPoint = p.clone(); if (this.isReversed) { newPoint.addScaled(anchor, -1.0D); } else { newPoint.add(anchor); }  if (VectorUtil.isInside(newPoint, context.minInclusive, context.maxExclusive)) context.consumer.accept(newPoint);  }context.anchor, context.workerId);
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */     
/* 45 */     this.positionProvider.positionsIn(childContext);
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\builtin\hytalegenerator\positionproviders\AnchorPositionProvider.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */