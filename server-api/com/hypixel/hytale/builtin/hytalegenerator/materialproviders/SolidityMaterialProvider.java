/*    */ package com.hypixel.hytale.builtin.hytalegenerator.materialproviders;
/*    */ 
/*    */ import javax.annotation.Nonnull;
/*    */ 
/*    */ public class SolidityMaterialProvider<V>
/*    */   extends MaterialProvider<V> {
/*    */   @Nonnull
/*    */   private final MaterialProvider<V> solidMaterialProvider;
/*    */   @Nonnull
/*    */   private final MaterialProvider<V> emptyMaterialProvider;
/*    */   
/*    */   public SolidityMaterialProvider(@Nonnull MaterialProvider<V> solidMaterialProvider, @Nonnull MaterialProvider<V> emptyMaterialProvider) {
/* 13 */     this.solidMaterialProvider = solidMaterialProvider;
/* 14 */     this.emptyMaterialProvider = emptyMaterialProvider;
/*    */   }
/*    */ 
/*    */   
/*    */   public V getVoxelTypeAt(@Nonnull MaterialProvider.Context context) {
/* 19 */     if (context.depthIntoFloor <= 0)
/*    */     {
/* 21 */       return this.emptyMaterialProvider.getVoxelTypeAt(context);
/*    */     }
/*    */     
/* 24 */     return this.solidMaterialProvider.getVoxelTypeAt(context);
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\builtin\hytalegenerator\materialproviders\SolidityMaterialProvider.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */