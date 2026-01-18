/*    */ package com.hypixel.hytale.server.core.asset.type.blocktype.config.farming;
/*    */ import com.hypixel.hytale.assetstore.AssetExtraInfo;
/*    */ import com.hypixel.hytale.assetstore.AssetRegistry;
/*    */ import com.hypixel.hytale.assetstore.AssetStore;
/*    */ import com.hypixel.hytale.assetstore.codec.AssetCodecMapCodec;
/*    */ import com.hypixel.hytale.assetstore.map.DefaultAssetMap;
/*    */ import com.hypixel.hytale.assetstore.map.JsonAssetWithMap;
/*    */ import com.hypixel.hytale.codec.Codec;
/*    */ import com.hypixel.hytale.codec.KeyedCodec;
/*    */ import com.hypixel.hytale.codec.builder.BuilderCodec;
/*    */ import com.hypixel.hytale.codec.codecs.array.ArrayCodec;
/*    */ import com.hypixel.hytale.component.CommandBuffer;
/*    */ import com.hypixel.hytale.component.Ref;
/*    */ import com.hypixel.hytale.server.core.universe.world.storage.ChunkStore;
/*    */ import javax.annotation.Nonnull;
/*    */ 
/*    */ public abstract class GrowthModifierAsset implements JsonAssetWithMap<String, DefaultAssetMap<String, GrowthModifierAsset>> {
/*    */   public static final AssetCodecMapCodec<String, GrowthModifierAsset> CODEC;
/*    */   
/*    */   static {
/* 21 */     CODEC = new AssetCodecMapCodec((Codec)Codec.STRING, (t, k) -> t.id = k, t -> t.id, (t, data) -> t.data = data, t -> t.data);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/* 28 */   public static final Codec<String> CHILD_ASSET_CODEC = (Codec<String>)new ContainedAssetCodec(GrowthModifierAsset.class, (AssetCodec)CODEC); public static final Codec<String[]> CHILD_ASSET_CODEC_ARRAY; public static final BuilderCodec<GrowthModifierAsset> ABSTRACT_CODEC; private static AssetStore<String, GrowthModifierAsset, DefaultAssetMap<String, GrowthModifierAsset>> ASSET_STORE; static {
/* 29 */     CHILD_ASSET_CODEC_ARRAY = (Codec<String[]>)new ArrayCodec(CHILD_ASSET_CODEC, x$0 -> new String[x$0]);
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */     
/* 38 */     ABSTRACT_CODEC = ((BuilderCodec.Builder)BuilderCodec.abstractBuilder(GrowthModifierAsset.class).appendInherited(new KeyedCodec("Modifier", (Codec)Codec.DOUBLE), (asset, modifier) -> asset.modifier = modifier.doubleValue(), asset -> Double.valueOf(asset.modifier), (asset, parent) -> asset.modifier = parent.modifier).add()).build();
/*    */   }
/*    */   private AssetExtraInfo.Data data; protected String id; protected double modifier;
/*    */   
/*    */   public static AssetStore<String, GrowthModifierAsset, DefaultAssetMap<String, GrowthModifierAsset>> getAssetStore() {
/* 43 */     if (ASSET_STORE == null) ASSET_STORE = AssetRegistry.getAssetStore(GrowthModifierAsset.class); 
/* 44 */     return ASSET_STORE;
/*    */   }
/*    */   
/*    */   public static DefaultAssetMap<String, GrowthModifierAsset> getAssetMap() {
/* 48 */     return (DefaultAssetMap<String, GrowthModifierAsset>)getAssetStore().getAssetMap();
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public GrowthModifierAsset() {}
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public GrowthModifierAsset(String id) {
/* 60 */     this.id = id;
/*    */   }
/*    */ 
/*    */   
/*    */   public String getId() {
/* 65 */     return this.id;
/*    */   }
/*    */   
/*    */   public double getModifier() {
/* 69 */     return this.modifier;
/*    */   }
/*    */   
/*    */   public double getCurrentGrowthMultiplier(CommandBuffer<ChunkStore> commandBuffer, Ref<ChunkStore> sectionRef, Ref<ChunkStore> blockRef, int x, int y, int z, boolean initialTick) {
/* 73 */     return this.modifier;
/*    */   }
/*    */ 
/*    */   
/*    */   @Nonnull
/*    */   public String toString() {
/* 79 */     return "GrowthModifierAsset{id='" + this.id + "', modifier=" + this.modifier + "}";
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\server\core\asset\type\blocktype\config\farming\GrowthModifierAsset.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */