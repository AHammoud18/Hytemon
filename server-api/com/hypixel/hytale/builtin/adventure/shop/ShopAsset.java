/*    */ package com.hypixel.hytale.builtin.adventure.shop;
/*    */ 
/*    */ import com.hypixel.hytale.assetstore.AssetExtraInfo;
/*    */ import com.hypixel.hytale.assetstore.AssetKeyValidator;
/*    */ import com.hypixel.hytale.assetstore.AssetRegistry;
/*    */ import com.hypixel.hytale.assetstore.AssetStore;
/*    */ import com.hypixel.hytale.assetstore.codec.AssetBuilderCodec;
/*    */ import com.hypixel.hytale.assetstore.map.DefaultAssetMap;
/*    */ import com.hypixel.hytale.assetstore.map.JsonAssetWithMap;
/*    */ import com.hypixel.hytale.codec.Codec;
/*    */ import com.hypixel.hytale.codec.KeyedCodec;
/*    */ import com.hypixel.hytale.codec.codecs.array.ArrayCodec;
/*    */ import com.hypixel.hytale.codec.validation.Validator;
/*    */ import com.hypixel.hytale.codec.validation.ValidatorCache;
/*    */ import com.hypixel.hytale.server.core.entity.entities.player.pages.choices.ChoiceElement;
/*    */ import java.util.Arrays;
/*    */ import java.util.function.Supplier;
/*    */ import javax.annotation.Nonnull;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class ShopAsset
/*    */   implements JsonAssetWithMap<String, DefaultAssetMap<String, ShopAsset>>
/*    */ {
/*    */   public static final AssetBuilderCodec<String, ShopAsset> CODEC;
/*    */   
/*    */   static {
/* 33 */     CODEC = ((AssetBuilderCodec.Builder)AssetBuilderCodec.builder(ShopAsset.class, ShopAsset::new, (Codec)Codec.STRING, (shopAsset, s) -> shopAsset.id = s, shopAsset -> shopAsset.id, (shopAsset, data) -> shopAsset.extraData = data, shopAsset -> shopAsset.extraData).addField(new KeyedCodec("Content", (Codec)new ArrayCodec((Codec)ChoiceElement.CODEC, x$0 -> new ChoiceElement[x$0])), (shopAsset, choiceElements) -> shopAsset.elements = choiceElements, shopAsset -> shopAsset.elements)).build();
/*    */   }
/* 35 */   public static final ValidatorCache<String> VALIDATOR_CACHE = new ValidatorCache((Validator)new AssetKeyValidator(ShopAsset::getAssetStore));
/*    */   private static AssetStore<String, ShopAsset, DefaultAssetMap<String, ShopAsset>> ASSET_STORE;
/*    */   protected AssetExtraInfo.Data extraData;
/*    */   
/*    */   public static AssetStore<String, ShopAsset, DefaultAssetMap<String, ShopAsset>> getAssetStore() {
/* 40 */     if (ASSET_STORE == null) ASSET_STORE = AssetRegistry.getAssetStore(ShopAsset.class); 
/* 41 */     return ASSET_STORE;
/*    */   }
/*    */   protected String id; protected ChoiceElement[] elements;
/*    */   public static DefaultAssetMap<String, ShopAsset> getAssetMap() {
/* 45 */     return (DefaultAssetMap<String, ShopAsset>)getAssetStore().getAssetMap();
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public ShopAsset(String id, ChoiceElement[] elements) {
/* 54 */     this.id = id;
/* 55 */     this.elements = elements;
/*    */   }
/*    */ 
/*    */   
/*    */   protected ShopAsset() {}
/*    */ 
/*    */   
/*    */   public String getId() {
/* 63 */     return this.id;
/*    */   }
/*    */   
/*    */   public ChoiceElement[] getElements() {
/* 67 */     return this.elements;
/*    */   }
/*    */ 
/*    */   
/*    */   @Nonnull
/*    */   public String toString() {
/* 73 */     return "ShopAsset{id='" + this.id + "', elements=" + 
/*    */       
/* 75 */       Arrays.toString((Object[])this.elements) + "}";
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\builtin\adventure\shop\ShopAsset.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */