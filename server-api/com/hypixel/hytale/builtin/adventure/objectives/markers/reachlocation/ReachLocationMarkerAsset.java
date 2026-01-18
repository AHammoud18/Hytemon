/*    */ package com.hypixel.hytale.builtin.adventure.objectives.markers.reachlocation;
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
/*    */ import com.hypixel.hytale.codec.validation.Validator;
/*    */ import com.hypixel.hytale.codec.validation.ValidatorCache;
/*    */ import com.hypixel.hytale.codec.validation.Validators;
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
/*    */ public class ReachLocationMarkerAsset
/*    */   implements JsonAssetWithMap<String, DefaultAssetMap<String, ReachLocationMarkerAsset>>
/*    */ {
/*    */   public static final AssetBuilderCodec<String, ReachLocationMarkerAsset> CODEC;
/*    */   
/*    */   static {
/* 41 */     CODEC = ((AssetBuilderCodec.Builder)((AssetBuilderCodec.Builder)AssetBuilderCodec.builder(ReachLocationMarkerAsset.class, ReachLocationMarkerAsset::new, (Codec)Codec.STRING, (t, k) -> t.id = k, t -> t.id, (asset, data) -> asset.data = data, asset -> asset.data).append(new KeyedCodec("Radius", (Codec)Codec.FLOAT), (reachLocationMarkerAsset, aFloat) -> reachLocationMarkerAsset.radius = aFloat.floatValue(), reachLocationMarkerAsset -> Float.valueOf(reachLocationMarkerAsset.radius)).addValidator(Validators.greaterThan(Float.valueOf(0.0F))).add()).append(new KeyedCodec("Name", (Codec)Codec.STRING), (reachLocationMarkerAsset, s) -> reachLocationMarkerAsset.name = s, reachLocationMarkerAsset -> reachLocationMarkerAsset.name).addValidator(Validators.nonNull()).addValidator(Validators.nonEmptyString()).add()).build();
/*    */   }
/* 43 */   public static final ValidatorCache<String> VALIDATOR_CACHE = new ValidatorCache((Validator)new AssetKeyValidator(ReachLocationMarkerAsset::getAssetStore));
/*    */   private static AssetStore<String, ReachLocationMarkerAsset, DefaultAssetMap<String, ReachLocationMarkerAsset>> ASSET_STORE;
/*    */   protected AssetExtraInfo.Data data;
/*    */   
/*    */   public static AssetStore<String, ReachLocationMarkerAsset, DefaultAssetMap<String, ReachLocationMarkerAsset>> getAssetStore() {
/* 48 */     if (ASSET_STORE == null) ASSET_STORE = AssetRegistry.getAssetStore(ReachLocationMarkerAsset.class); 
/* 49 */     return ASSET_STORE;
/*    */   }
/*    */   protected String id; protected String name;
/*    */   public static DefaultAssetMap<String, ReachLocationMarkerAsset> getAssetMap() {
/* 53 */     return (DefaultAssetMap<String, ReachLocationMarkerAsset>)getAssetStore().getAssetMap();
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/* 60 */   protected float radius = 1.0F;
/*    */ 
/*    */   
/*    */   public String getId() {
/* 64 */     return this.id;
/*    */   }
/*    */   
/*    */   public float getRadius() {
/* 68 */     return this.radius;
/*    */   }
/*    */   
/*    */   public String getName() {
/* 72 */     return this.name;
/*    */   }
/*    */ 
/*    */   
/*    */   @Nonnull
/*    */   public String toString() {
/* 78 */     return "ReachLocationMarkerAsset{id='" + this.id + "', name='" + this.name + "', radius=" + this.radius + "}";
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\builtin\adventure\objectives\markers\reachlocation\ReachLocationMarkerAsset.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */