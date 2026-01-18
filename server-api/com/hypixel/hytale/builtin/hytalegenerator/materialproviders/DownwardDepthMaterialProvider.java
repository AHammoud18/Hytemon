/*    */ package com.hypixel.hytale.builtin.hytalegenerator.materialproviders;
/*    */ 
/*    */ import javax.annotation.Nonnull;
/*    */ import javax.annotation.Nullable;
/*    */ 
/*    */ public class DownwardDepthMaterialProvider<V> extends MaterialProvider<V> {
/*    */   @Nonnull
/*    */   private final MaterialProvider<V> materialProvider;
/*    */   private final int depth;
/*    */   
/*    */   public DownwardDepthMaterialProvider(@Nonnull MaterialProvider<V> materialProvider, int depth) {
/* 12 */     this.materialProvider = materialProvider;
/* 13 */     this.depth = depth;
/*    */   }
/*    */ 
/*    */   
/*    */   @Nullable
/*    */   public V getVoxelTypeAt(@Nonnull MaterialProvider.Context context) {
/* 19 */     if (this.depth != context.depthIntoFloor) return null; 
/* 20 */     return this.materialProvider.getVoxelTypeAt(context);
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\builtin\hytalegenerator\materialproviders\DownwardDepthMaterialProvider.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */