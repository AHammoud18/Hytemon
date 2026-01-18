/*    */ package com.hypixel.hytale.builtin.hytalegenerator.materialproviders;
/*    */ 
/*    */ import javax.annotation.Nonnull;
/*    */ import javax.annotation.Nullable;
/*    */ 
/*    */ public class UpwardSpaceMaterialProvider<V> extends MaterialProvider<V> {
/*    */   @Nonnull
/*    */   private final MaterialProvider<V> materialProvider;
/*    */   private final int space;
/*    */   
/*    */   public UpwardSpaceMaterialProvider(@Nonnull MaterialProvider<V> materialProvider, int space) {
/* 12 */     this.materialProvider = materialProvider;
/* 13 */     this.space = space;
/*    */   }
/*    */ 
/*    */   
/*    */   @Nullable
/*    */   public V getVoxelTypeAt(@Nonnull MaterialProvider.Context context) {
/* 19 */     if (this.space != context.spaceAboveFloor) return null; 
/* 20 */     return this.materialProvider.getVoxelTypeAt(context);
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\builtin\hytalegenerator\materialproviders\UpwardSpaceMaterialProvider.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */