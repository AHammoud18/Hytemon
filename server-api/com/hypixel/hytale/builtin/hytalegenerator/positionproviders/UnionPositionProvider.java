/*    */ package com.hypixel.hytale.builtin.hytalegenerator.positionproviders;
/*    */ 
/*    */ import java.util.ArrayList;
/*    */ import java.util.List;
/*    */ import javax.annotation.Nonnull;
/*    */ 
/*    */ public class UnionPositionProvider extends PositionProvider {
/*    */   @Nonnull
/*    */   private final List<PositionProvider> positionProviders;
/*    */   
/*    */   public UnionPositionProvider(@Nonnull List<PositionProvider> positionProviders) {
/* 12 */     this.positionProviders = new ArrayList<>();
/* 13 */     this.positionProviders.addAll(positionProviders);
/*    */   }
/*    */ 
/*    */   
/*    */   public void positionsIn(@Nonnull PositionProvider.Context context) {
/* 18 */     for (PositionProvider position : this.positionProviders)
/* 19 */       position.positionsIn(context); 
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\builtin\hytalegenerator\positionproviders\UnionPositionProvider.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */