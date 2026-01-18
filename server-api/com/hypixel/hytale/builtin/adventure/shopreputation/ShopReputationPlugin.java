/*    */ package com.hypixel.hytale.builtin.adventure.shopreputation;
/*    */ 
/*    */ import com.hypixel.hytale.assetstore.AssetRegistry;
/*    */ import com.hypixel.hytale.builtin.adventure.reputation.assets.ReputationGroup;
/*    */ import com.hypixel.hytale.builtin.adventure.reputation.assets.ReputationRank;
/*    */ import com.hypixel.hytale.builtin.adventure.shop.ShopAsset;
/*    */ import com.hypixel.hytale.server.core.plugin.JavaPlugin;
/*    */ import com.hypixel.hytale.server.core.plugin.JavaPluginInit;
/*    */ import javax.annotation.Nonnull;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class ShopReputationPlugin
/*    */   extends JavaPlugin
/*    */ {
/*    */   public ShopReputationPlugin(@Nonnull JavaPluginInit init) {
/* 19 */     super(init);
/*    */   }
/*    */ 
/*    */   
/*    */   protected void setup() {
/* 24 */     AssetRegistry.getAssetStore(ShopAsset.class).injectLoadsAfter(ReputationGroup.class);
/* 25 */     AssetRegistry.getAssetStore(ShopAsset.class).injectLoadsAfter(ReputationRank.class);
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\builtin\adventure\shopreputation\ShopReputationPlugin.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */