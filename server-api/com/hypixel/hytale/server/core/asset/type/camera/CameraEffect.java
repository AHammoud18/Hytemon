/*     */ package com.hypixel.hytale.server.core.asset.type.camera;
/*     */ 
/*     */ import com.hypixel.hytale.assetstore.AssetExtraInfo;
/*     */ import com.hypixel.hytale.assetstore.AssetKeyValidator;
/*     */ import com.hypixel.hytale.assetstore.AssetRegistry;
/*     */ import com.hypixel.hytale.assetstore.AssetStore;
/*     */ import com.hypixel.hytale.assetstore.codec.AssetCodec;
/*     */ import com.hypixel.hytale.assetstore.codec.AssetCodecMapCodec;
/*     */ import com.hypixel.hytale.assetstore.codec.ContainedAssetCodec;
/*     */ import com.hypixel.hytale.assetstore.map.IndexedLookupTableAssetMap;
/*     */ import com.hypixel.hytale.assetstore.map.JsonAssetWithMap;
/*     */ import com.hypixel.hytale.codec.Codec;
/*     */ import com.hypixel.hytale.codec.validation.Validator;
/*     */ import com.hypixel.hytale.codec.validation.ValidatorCache;
/*     */ import com.hypixel.hytale.protocol.packets.camera.CameraShakeEffect;
/*     */ import javax.annotation.Nonnull;
/*     */ 
/*     */ 
/*     */ public abstract class CameraEffect
/*     */   implements JsonAssetWithMap<String, IndexedLookupTableAssetMap<String, CameraEffect>>
/*     */ {
/*     */   @Nonnull
/*     */   public static final AssetCodecMapCodec<String, CameraEffect> CODEC;
/*     */   
/*     */   static {
/*  26 */     CODEC = new AssetCodecMapCodec((Codec)Codec.STRING, (t, k) -> t.id = k, t -> t.id, (t, data) -> t.data = data, t -> t.data);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Nonnull
/*  38 */   public static final Codec<String> CHILD_ASSET_CODEC = (Codec<String>)new ContainedAssetCodec(CameraEffect.class, (AssetCodec)CODEC);
/*     */   
/*     */   @Nonnull
/*  41 */   public static final ValidatorCache<String> VALIDATOR_CACHE = new ValidatorCache((Validator)new AssetKeyValidator(CameraEffect::getAssetStore));
/*     */ 
/*     */   
/*     */   private static AssetStore<String, CameraEffect, IndexedLookupTableAssetMap<String, CameraEffect>> ASSET_STORE;
/*     */   
/*     */   protected String id;
/*     */   
/*     */   protected AssetExtraInfo.Data data;
/*     */ 
/*     */   
/*     */   @Nonnull
/*     */   public static AssetStore<String, CameraEffect, IndexedLookupTableAssetMap<String, CameraEffect>> getAssetStore() {
/*  53 */     if (ASSET_STORE == null) ASSET_STORE = AssetRegistry.getAssetStore(CameraEffect.class); 
/*  54 */     return ASSET_STORE;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Nonnull
/*     */   public static IndexedLookupTableAssetMap<String, CameraEffect> getAssetMap() {
/*  62 */     return (IndexedLookupTableAssetMap<String, CameraEffect>)getAssetStore().getAssetMap();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getId() {
/*  77 */     return this.id;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public abstract CameraShakeEffect createCameraShakePacket();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public abstract CameraShakeEffect createCameraShakePacket(float paramFloat);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static class MissingCameraEffect
/*     */     extends CameraEffect
/*     */   {
/*     */     public MissingCameraEffect(@Nonnull String id) {}
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     @Nonnull
/*     */     public CameraShakeEffect createCameraShakePacket() {
/* 106 */       return new CameraShakeEffect();
/*     */     }
/*     */ 
/*     */     
/*     */     @Nonnull
/*     */     public CameraShakeEffect createCameraShakePacket(float intensityContext) {
/* 112 */       return new CameraShakeEffect();
/*     */     }
/*     */ 
/*     */     
/*     */     @Nonnull
/*     */     public String toString() {
/* 118 */       return "MissingShakeEffect{}";
/*     */     }
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\server\core\asset\type\camera\CameraEffect.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */