/*    */ package com.hypixel.hytale.builtin.adventure.shop;
/*    */ import com.hypixel.hytale.assetstore.AssetMap;
/*    */ import com.hypixel.hytale.assetstore.AssetStore;
/*    */ import com.hypixel.hytale.assetstore.codec.AssetCodec;
/*    */ import com.hypixel.hytale.assetstore.map.DefaultAssetMap;
/*    */ import com.hypixel.hytale.builtin.adventure.shop.barter.BarterShopAsset;
/*    */ import com.hypixel.hytale.builtin.adventure.shop.barter.BarterShopState;
/*    */ import com.hypixel.hytale.codec.Codec;
/*    */ import com.hypixel.hytale.codec.lookup.StringCodecMapCodec;
/*    */ import com.hypixel.hytale.server.core.asset.HytaleAssetStore;
/*    */ import com.hypixel.hytale.server.core.asset.type.item.config.Item;
/*    */ import com.hypixel.hytale.server.core.entity.entities.player.pages.choices.ChoiceElement;
/*    */ import com.hypixel.hytale.server.core.entity.entities.player.pages.choices.ChoiceInteraction;
/*    */ import com.hypixel.hytale.server.core.plugin.JavaPlugin;
/*    */ import com.hypixel.hytale.server.core.plugin.JavaPluginInit;
/*    */ import java.util.logging.Level;
/*    */ import javax.annotation.Nonnull;
/*    */ 
/*    */ public class ShopPlugin extends JavaPlugin {
/*    */   public static ShopPlugin get() {
/* 21 */     return instance;
/*    */   }
/*    */   protected static ShopPlugin instance;
/*    */   public ShopPlugin(@Nonnull JavaPluginInit init) {
/* 25 */     super(init);
/*    */   }
/*    */ 
/*    */   
/*    */   protected void setup() {
/* 30 */     instance = this;
/*    */ 
/*    */     
/* 33 */     getAssetRegistry().register((AssetStore)((HytaleAssetStore.Builder)((HytaleAssetStore.Builder)((HytaleAssetStore.Builder)((HytaleAssetStore.Builder)HytaleAssetStore.builder(ShopAsset.class, (AssetMap)new DefaultAssetMap())
/* 34 */         .setPath("Shops"))
/* 35 */         .setCodec((AssetCodec)ShopAsset.CODEC))
/* 36 */         .setKeyFunction(ShopAsset::getId))
/* 37 */         .loadsAfter(new Class[] { Item.class
/* 38 */           })).build());
/*    */ 
/*    */     
/* 41 */     getAssetRegistry().register((AssetStore)((HytaleAssetStore.Builder)((HytaleAssetStore.Builder)((HytaleAssetStore.Builder)((HytaleAssetStore.Builder)HytaleAssetStore.builder(BarterShopAsset.class, (AssetMap)new DefaultAssetMap())
/* 42 */         .setPath("BarterShops"))
/* 43 */         .setCodec((AssetCodec)BarterShopAsset.CODEC))
/* 44 */         .setKeyFunction(BarterShopAsset::getId))
/* 45 */         .loadsAfter(new Class[] { Item.class
/* 46 */           })).build());
/*    */     
/* 48 */     getCodecRegistry((StringCodecMapCodec)ChoiceElement.CODEC).register("ShopElement", ShopElement.class, (Codec)ShopElement.CODEC);
/*    */     
/* 50 */     getCodecRegistry((StringCodecMapCodec)ChoiceInteraction.CODEC).register("GiveItem", GiveItemInteraction.class, (Codec)GiveItemInteraction.CODEC);
/*    */     
/* 52 */     getCodecRegistry((StringCodecMapCodec)OpenCustomUIInteraction.PAGE_CODEC).register("Shop", ShopPageSupplier.class, (Codec)ShopPageSupplier.CODEC);
/*    */   }
/*    */ 
/*    */   
/*    */   protected void start() {
/* 57 */     BarterShopState.initialize(getDataDirectory());
/* 58 */     getLogger().at(Level.INFO).log("Barter shop state initialized");
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   protected void shutdown() {
/* 64 */     BarterShopState.shutdown();
/* 65 */     getLogger().at(Level.INFO).log("Barter shop state saved");
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\builtin\adventure\shop\ShopPlugin.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */