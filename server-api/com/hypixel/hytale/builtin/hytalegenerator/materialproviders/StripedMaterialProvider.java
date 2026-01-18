/*    */ package com.hypixel.hytale.builtin.hytalegenerator.materialproviders;
/*    */ 
/*    */ import java.util.List;
/*    */ import javax.annotation.Nonnull;
/*    */ import javax.annotation.Nullable;
/*    */ 
/*    */ public class StripedMaterialProvider<V>
/*    */   extends MaterialProvider<V> {
/*    */   @Nonnull
/*    */   private final MaterialProvider<V> materialProvider;
/*    */   @Nonnull
/*    */   private final Stripe[] stripes;
/*    */   
/*    */   public StripedMaterialProvider(@Nonnull MaterialProvider<V> materialProvider, @Nonnull List<Stripe> stripes) {
/* 15 */     this.materialProvider = materialProvider;
/* 16 */     this.stripes = new Stripe[stripes.size()];
/* 17 */     for (int i = 0; i < stripes.size(); i++) {
/* 18 */       Stripe s = stripes.get(i);
/* 19 */       this.stripes[i] = s;
/*    */     } 
/*    */   }
/*    */ 
/*    */   
/*    */   @Nullable
/*    */   public V getVoxelTypeAt(@Nonnull MaterialProvider.Context context) {
/* 26 */     for (Stripe s : this.stripes) {
/* 27 */       if (s.contains(context.position.y)) {
/* 28 */         return this.materialProvider.getVoxelTypeAt(context);
/*    */       }
/*    */     } 
/* 31 */     return null;
/*    */   }
/*    */   
/*    */   public static class Stripe {
/*    */     private final int topY;
/*    */     private final int bottomY;
/*    */     
/*    */     public Stripe(int topY, int bottomY) {
/* 39 */       this.topY = topY;
/* 40 */       this.bottomY = bottomY;
/*    */     }
/*    */     
/*    */     public boolean contains(int y) {
/* 44 */       return (y >= this.bottomY && y <= this.topY);
/*    */     }
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\builtin\hytalegenerator\materialproviders\StripedMaterialProvider.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */