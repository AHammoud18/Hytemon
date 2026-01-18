/*    */ package com.hypixel.hytale.builtin.hytalegenerator.materialproviders;
/*    */ 
/*    */ import com.hypixel.hytale.builtin.hytalegenerator.materialproviders.functions.DoubleFunctionXZ;
/*    */ import javax.annotation.Nonnull;
/*    */ import javax.annotation.Nullable;
/*    */ 
/*    */ public class HorizontalMaterialProvider<V>
/*    */   extends MaterialProvider<V> {
/*    */   @Nonnull
/*    */   private final MaterialProvider<V> materialProvider;
/*    */   @Nonnull
/*    */   private final DoubleFunctionXZ topY;
/*    */   @Nonnull
/*    */   private final DoubleFunctionXZ bottomY;
/*    */   
/*    */   public HorizontalMaterialProvider(@Nonnull MaterialProvider<V> materialProvider, @Nonnull DoubleFunctionXZ topY, @Nonnull DoubleFunctionXZ bottomY) {
/* 17 */     this.materialProvider = materialProvider;
/* 18 */     this.topY = topY;
/* 19 */     this.bottomY = bottomY;
/*    */   }
/*    */ 
/*    */   
/*    */   @Nullable
/*    */   public V getVoxelTypeAt(@Nonnull MaterialProvider.Context context) {
/* 25 */     double topY = this.topY.apply(context.position.x, context.position.z);
/* 26 */     double bottomY = this.bottomY.apply(context.position.x, context.position.z);
/*    */     
/* 28 */     if (context.position.y >= topY || context.position.y < bottomY) {
/* 29 */       return null;
/*    */     }
/* 31 */     return this.materialProvider.getVoxelTypeAt(context);
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\builtin\hytalegenerator\materialproviders\HorizontalMaterialProvider.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */