/*    */ package com.hypixel.hytale.server.core.asset.type.item.config;
/*    */ 
/*    */ import com.hypixel.hytale.assetstore.AssetExtraInfo;
/*    */ import com.hypixel.hytale.assetstore.AssetRegistry;
/*    */ import com.hypixel.hytale.assetstore.AssetStore;
/*    */ import com.hypixel.hytale.assetstore.codec.AssetBuilderCodec;
/*    */ import com.hypixel.hytale.assetstore.codec.AssetCodec;
/*    */ import com.hypixel.hytale.assetstore.map.DefaultAssetMap;
/*    */ import com.hypixel.hytale.assetstore.map.JsonAssetWithMap;
/*    */ import com.hypixel.hytale.codec.Codec;
/*    */ import com.hypixel.hytale.codec.KeyedCodec;
/*    */ import com.hypixel.hytale.codec.codecs.array.ArrayCodec;
/*    */ import java.util.function.Supplier;
/*    */ import javax.annotation.Nonnull;
/*    */ 
/*    */ public class BuilderToolItemReferenceAsset implements JsonAssetWithMap<String, DefaultAssetMap<String, BuilderToolItemReferenceAsset>> {
/*    */   private static AssetStore<String, BuilderToolItemReferenceAsset, DefaultAssetMap<String, BuilderToolItemReferenceAsset>> ASSET_STORE;
/*    */   @Nonnull
/*    */   public static final AssetCodec<String, BuilderToolItemReferenceAsset> CODEC;
/*    */   private String id;
/*    */   protected String[] itemIds;
/*    */   private AssetExtraInfo.Data data;
/*    */   
/*    */   public static DefaultAssetMap<String, BuilderToolItemReferenceAsset> getAssetMap() {
/* 25 */     return (DefaultAssetMap<String, BuilderToolItemReferenceAsset>)getAssetStore().getAssetMap();
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public static AssetStore<String, BuilderToolItemReferenceAsset, DefaultAssetMap<String, BuilderToolItemReferenceAsset>> getAssetStore() {
/* 37 */     if (ASSET_STORE == null) ASSET_STORE = AssetRegistry.getAssetStore(BuilderToolItemReferenceAsset.class); 
/* 38 */     return ASSET_STORE;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   static {
/* 59 */     CODEC = (AssetCodec<String, BuilderToolItemReferenceAsset>)((AssetBuilderCodec.Builder)AssetBuilderCodec.builder(BuilderToolItemReferenceAsset.class, BuilderToolItemReferenceAsset::new, (Codec)Codec.STRING, (t, k) -> t.id = k, t -> t.id, (asset, data) -> asset.data = data, asset -> asset.data).append(new KeyedCodec("BuilderToolItems", (Codec)new ArrayCodec((Codec)Codec.STRING, x$0 -> new String[x$0])), (i, itemIds) -> i.itemIds = itemIds, i -> i.itemIds).add()).build();
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public String[] getItems() {
/* 80 */     return this.itemIds;
/*    */   }
/*    */ 
/*    */   
/*    */   public String getId() {
/* 85 */     return this.id;
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\server\core\asset\type\item\config\BuilderToolItemReferenceAsset.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */