/*    */ package com.hypixel.hytale.builtin.adventure.shop;
/*    */ 
/*    */ import com.hypixel.hytale.server.core.entity.entities.player.pages.choices.ChoiceBasePage;
/*    */ import com.hypixel.hytale.server.core.entity.entities.player.pages.choices.ChoiceElement;
/*    */ import com.hypixel.hytale.server.core.universe.PlayerRef;
/*    */ import javax.annotation.Nonnull;
/*    */ import javax.annotation.Nullable;
/*    */ 
/*    */ public class ShopPage
/*    */   extends ChoiceBasePage {
/*    */   public ShopPage(@Nonnull PlayerRef playerRef, String shopId) {
/* 12 */     super(playerRef, getShopElements(shopId), "Pages/ShopPage.ui");
/*    */   }
/*    */   
/*    */   @Nullable
/*    */   protected static ChoiceElement[] getShopElements(String shopId) {
/* 17 */     ShopAsset shopAsset = (ShopAsset)ShopAsset.getAssetMap().getAsset(shopId);
/* 18 */     if (shopAsset == null) return null;
/*    */     
/* 20 */     return shopAsset.getElements();
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\builtin\adventure\shop\ShopPage.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */