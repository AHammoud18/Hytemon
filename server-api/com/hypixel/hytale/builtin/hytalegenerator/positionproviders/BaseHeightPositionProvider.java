/*    */ package com.hypixel.hytale.builtin.hytalegenerator.positionproviders;
/*    */ 
/*    */ import com.hypixel.hytale.builtin.hytalegenerator.VectorUtil;
/*    */ import com.hypixel.hytale.builtin.hytalegenerator.framework.interfaces.functions.BiDouble2DoubleFunction;
/*    */ import com.hypixel.hytale.math.vector.Vector3d;
/*    */ import javax.annotation.Nonnull;
/*    */ 
/*    */ 
/*    */ public class BaseHeightPositionProvider
/*    */   extends PositionProvider
/*    */ {
/*    */   @Nonnull
/*    */   private final BiDouble2DoubleFunction baseHeightFunction;
/*    */   private final double maxYInput;
/*    */   private final double minYInput;
/*    */   @Nonnull
/*    */   private final PositionProvider positionProvider;
/*    */   
/*    */   public BaseHeightPositionProvider(@Nonnull BiDouble2DoubleFunction baseHeightFunction, @Nonnull PositionProvider positionProvider, double minYInput, double maxYInput) {
/* 20 */     maxYInput = Math.max(minYInput, maxYInput);
/* 21 */     this.baseHeightFunction = baseHeightFunction;
/* 22 */     this.positionProvider = positionProvider;
/* 23 */     this.maxYInput = maxYInput;
/* 24 */     this.minYInput = minYInput;
/*    */   }
/*    */ 
/*    */   
/*    */   public void positionsIn(@Nonnull PositionProvider.Context context) {
/* 29 */     PositionProvider.Context childContext = new PositionProvider.Context(context);
/* 30 */     childContext.consumer = (position -> {
/*    */         Vector3d offsetP = position.clone();
/*    */         offsetP.y += this.baseHeightFunction.apply(position.x, position.z);
/*    */         if (VectorUtil.isInside(offsetP, context.minInclusive, context.maxExclusive)) {
/*    */           context.consumer.accept(offsetP);
/*    */         }
/*    */       });
/* 37 */     this.positionProvider.positionsIn(childContext);
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\builtin\hytalegenerator\positionproviders\BaseHeightPositionProvider.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */