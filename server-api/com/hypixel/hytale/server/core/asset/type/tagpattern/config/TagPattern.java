/*    */ package com.hypixel.hytale.server.core.asset.type.tagpattern.config;
/*    */ 
/*    */ import com.hypixel.hytale.assetstore.AssetExtraInfo;
/*    */ import com.hypixel.hytale.assetstore.AssetKeyValidator;
/*    */ import com.hypixel.hytale.assetstore.AssetRegistry;
/*    */ import com.hypixel.hytale.assetstore.AssetStore;
/*    */ import com.hypixel.hytale.assetstore.codec.AssetCodec;
/*    */ import com.hypixel.hytale.assetstore.codec.AssetCodecMapCodec;
/*    */ import com.hypixel.hytale.assetstore.codec.ContainedAssetCodec;
/*    */ import com.hypixel.hytale.assetstore.map.IndexedLookupTableAssetMap;
/*    */ import com.hypixel.hytale.assetstore.map.JsonAssetWithMap;
/*    */ import com.hypixel.hytale.codec.Codec;
/*    */ import com.hypixel.hytale.codec.builder.BuilderCodec;
/*    */ import com.hypixel.hytale.codec.validation.Validator;
/*    */ import com.hypixel.hytale.codec.validation.ValidatorCache;
/*    */ import com.hypixel.hytale.protocol.TagPattern;
/*    */ import com.hypixel.hytale.server.core.io.NetworkSerializable;
/*    */ import it.unimi.dsi.fastutil.ints.Int2ObjectMap;
/*    */ import it.unimi.dsi.fastutil.ints.IntSet;
/*    */ import java.lang.ref.SoftReference;
/*    */ import javax.annotation.Nonnull;
/*    */ 
/*    */ public abstract class TagPattern implements JsonAssetWithMap<String, IndexedLookupTableAssetMap<String, TagPattern>>, NetworkSerializable<TagPattern> {
/*    */   static {
/* 25 */     CODEC = new AssetCodecMapCodec("Op", (Codec)Codec.STRING, (t, k) -> t.id = k, t -> t.id, (t, data) -> t.data = data, t -> t.data);
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public static final AssetCodecMapCodec<String, TagPattern> CODEC;
/*    */ 
/*    */   
/* 33 */   public static final BuilderCodec<TagPattern> BASE_CODEC = BuilderCodec.abstractBuilder(TagPattern.class)
/* 34 */     .build();
/*    */   
/* 36 */   public static final Codec<String> CHILD_ASSET_CODEC = (Codec<String>)new ContainedAssetCodec(TagPattern.class, (AssetCodec)CODEC);
/*    */   
/* 38 */   public static final ValidatorCache<String> VALIDATOR_CACHE = new ValidatorCache((Validator)new AssetKeyValidator(TagPattern::getAssetStore));
/*    */   private static AssetStore<String, TagPattern, IndexedLookupTableAssetMap<String, TagPattern>> ASSET_STORE;
/*    */   private AssetExtraInfo.Data data;
/*    */   
/*    */   public static AssetStore<String, TagPattern, IndexedLookupTableAssetMap<String, TagPattern>> getAssetStore() {
/* 43 */     if (ASSET_STORE == null) ASSET_STORE = AssetRegistry.getAssetStore(TagPattern.class); 
/* 44 */     return ASSET_STORE;
/*    */   }
/*    */   protected String id; protected SoftReference<TagPattern> cachedPacket;
/*    */   public static IndexedLookupTableAssetMap<String, TagPattern> getAssetMap() {
/* 48 */     return (IndexedLookupTableAssetMap<String, TagPattern>)getAssetStore().getAssetMap();
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public String getId() {
/* 59 */     return this.id;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   @Nonnull
/*    */   public String toString() {
/* 67 */     return "TagPattern{id='" + this.id + "'}";
/*    */   }
/*    */   
/*    */   public abstract boolean test(Int2ObjectMap<IntSet> paramInt2ObjectMap);
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\server\core\asset\type\tagpattern\config\TagPattern.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */