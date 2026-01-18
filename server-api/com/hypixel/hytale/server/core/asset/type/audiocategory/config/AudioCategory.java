/*     */ package com.hypixel.hytale.server.core.asset.type.audiocategory.config;
/*     */ import com.hypixel.hytale.assetstore.AssetExtraInfo;
/*     */ import com.hypixel.hytale.assetstore.AssetKeyValidator;
/*     */ import com.hypixel.hytale.assetstore.AssetRegistry;
/*     */ import com.hypixel.hytale.assetstore.AssetStore;
/*     */ import com.hypixel.hytale.assetstore.codec.AssetBuilderCodec;
/*     */ import com.hypixel.hytale.assetstore.map.IndexedLookupTableAssetMap;
/*     */ import com.hypixel.hytale.assetstore.map.JsonAssetWithMap;
/*     */ import com.hypixel.hytale.codec.Codec;
/*     */ import com.hypixel.hytale.codec.KeyedCodec;
/*     */ import com.hypixel.hytale.codec.schema.metadata.Metadata;
/*     */ import com.hypixel.hytale.codec.schema.metadata.ui.UIEditor;
/*     */ import com.hypixel.hytale.codec.validation.Validator;
/*     */ import com.hypixel.hytale.codec.validation.ValidatorCache;
/*     */ import com.hypixel.hytale.codec.validation.Validators;
/*     */ import com.hypixel.hytale.common.util.AudioUtil;
/*     */ import com.hypixel.hytale.protocol.AudioCategory;
/*     */ import com.hypixel.hytale.server.core.io.NetworkSerializable;
/*     */ import java.lang.ref.SoftReference;
/*     */ import java.util.function.Supplier;
/*     */ import javax.annotation.Nonnull;
/*     */ 
/*     */ public class AudioCategory implements JsonAssetWithMap<String, IndexedLookupTableAssetMap<String, AudioCategory>>, NetworkSerializable<AudioCategory> {
/*  24 */   public static final AudioCategory EMPTY_AUDIO_CATEGORY = new AudioCategory("EMPTY");
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static final int EMPTY_ID = 0;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static final String EMPTY = "EMPTY";
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static final AssetBuilderCodec<String, AudioCategory> CODEC;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   static {
/*  47 */     CODEC = ((AssetBuilderCodec.Builder)((AssetBuilderCodec.Builder)AssetBuilderCodec.builder(AudioCategory.class, AudioCategory::new, (Codec)Codec.STRING, (t, k) -> t.id = k, t -> t.id, (asset, data) -> asset.data = data, asset -> asset.data).documentation("An asset used to define an audio category. Can be used to adjust the volume of all sound events that reference a given category. Note: When using an inheritance structure, these categories act a bit like an audio bus where the category's volume is combined with the volumes further up in the hierarchy. e.g. if the category's volume is 4dB and the parent is -2dB, the final volume will be 2dB.")).appendInherited(new KeyedCodec("Volume", (Codec)Codec.FLOAT), (category, f) -> category.volume = AudioUtil.decibelsToLinearGain(f.floatValue()), category -> Float.valueOf(AudioUtil.linearGainToDecibels(category.volume)), (category, parent) -> category.volume = parent.volume).metadata((Metadata)new UIEditor((UIEditor.EditorComponent)new UIEditor.FormattedNumber(null, " dB", null))).addValidator(Validators.range(Float.valueOf(-100.0F), Float.valueOf(10.0F))).documentation("Volume adjustment for the audio category in decibels.").add()).build();
/*     */   }
/*  49 */   public static final ValidatorCache<String> VALIDATOR_CACHE = new ValidatorCache((Validator)new AssetKeyValidator(AudioCategory::getAssetStore)); private static AssetStore<String, AudioCategory, IndexedLookupTableAssetMap<String, AudioCategory>> ASSET_STORE;
/*     */   protected AssetExtraInfo.Data data;
/*     */   protected String id;
/*     */   
/*     */   public static AssetStore<String, AudioCategory, IndexedLookupTableAssetMap<String, AudioCategory>> getAssetStore() {
/*  54 */     if (ASSET_STORE == null) ASSET_STORE = AssetRegistry.getAssetStore(AudioCategory.class); 
/*  55 */     return ASSET_STORE;
/*     */   }
/*     */   
/*     */   public static IndexedLookupTableAssetMap<String, AudioCategory> getAssetMap() {
/*  59 */     return (IndexedLookupTableAssetMap<String, AudioCategory>)getAssetStore().getAssetMap();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  66 */   protected float volume = AudioUtil.decibelsToLinearGain(0.0F);
/*     */   
/*     */   private SoftReference<AudioCategory> cachedPacket;
/*     */   
/*     */   public AudioCategory(String id) {
/*  71 */     this.id = id;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getId() {
/*  79 */     return this.id;
/*     */   }
/*     */   
/*     */   public float getVolume() {
/*  83 */     return this.volume;
/*     */   }
/*     */ 
/*     */   
/*     */   @Nonnull
/*     */   public String toString() {
/*  89 */     return "AudioCategory{id='" + this.id + "', volume=" + this.volume + "}";
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Nonnull
/*     */   public AudioCategory toPacket() {
/*  98 */     AudioCategory cached = (this.cachedPacket == null) ? null : this.cachedPacket.get();
/*  99 */     if (cached != null) return cached;
/*     */     
/* 101 */     AudioCategory packet = new AudioCategory();
/*     */     
/* 103 */     packet.id = this.id;
/*     */     
/* 105 */     packet.volume = this.volume;
/*     */     
/* 107 */     AssetExtraInfo.Data parentData = this.data;
/* 108 */     while (parentData != null) {
/* 109 */       String parentKey = (String)ASSET_STORE.transformKey(parentData.getParentKey());
/* 110 */       if (parentKey == null)
/*     */         break; 
/* 112 */       AudioCategory parent = (AudioCategory)((IndexedLookupTableAssetMap)ASSET_STORE.getAssetMap()).getAsset(parentKey);
/* 113 */       if (parent == null)
/*     */         break; 
/* 115 */       packet.volume *= parent.volume;
/* 116 */       parentData = parent.data;
/*     */     } 
/*     */     
/* 119 */     this.cachedPacket = new SoftReference<>(packet);
/* 120 */     return packet;
/*     */   }
/*     */   
/*     */   protected AudioCategory() {}
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\server\core\asset\type\audiocategory\config\AudioCategory.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */