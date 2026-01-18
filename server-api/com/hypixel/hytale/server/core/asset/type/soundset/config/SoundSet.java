/*     */ package com.hypixel.hytale.server.core.asset.type.soundset.config;
/*     */ import com.hypixel.hytale.assetstore.AssetExtraInfo;
/*     */ import com.hypixel.hytale.assetstore.AssetKeyValidator;
/*     */ import com.hypixel.hytale.assetstore.AssetRegistry;
/*     */ import com.hypixel.hytale.assetstore.AssetStore;
/*     */ import com.hypixel.hytale.assetstore.codec.AssetBuilderCodec;
/*     */ import com.hypixel.hytale.assetstore.codec.AssetCodec;
/*     */ import com.hypixel.hytale.assetstore.codec.ContainedAssetCodec;
/*     */ import com.hypixel.hytale.assetstore.map.IndexedLookupTableAssetMap;
/*     */ import com.hypixel.hytale.assetstore.map.JsonAssetWithMap;
/*     */ import com.hypixel.hytale.codec.Codec;
/*     */ import com.hypixel.hytale.codec.KeyedCodec;
/*     */ import com.hypixel.hytale.codec.codecs.EnumCodec;
/*     */ import com.hypixel.hytale.codec.codecs.map.MapCodec;
/*     */ import com.hypixel.hytale.codec.schema.metadata.Metadata;
/*     */ import com.hypixel.hytale.codec.schema.metadata.ui.UIDefaultCollapsedState;
/*     */ import com.hypixel.hytale.codec.validation.Validator;
/*     */ import com.hypixel.hytale.codec.validation.ValidatorCache;
/*     */ import com.hypixel.hytale.codec.validation.Validators;
/*     */ import com.hypixel.hytale.protocol.SoundCategory;
/*     */ import com.hypixel.hytale.server.core.asset.type.soundevent.config.SoundEvent;
/*     */ import com.hypixel.hytale.server.core.io.NetworkSerializable;
/*     */ import it.unimi.dsi.fastutil.objects.Object2IntMap;
/*     */ import it.unimi.dsi.fastutil.objects.Object2IntMaps;
/*     */ import it.unimi.dsi.fastutil.objects.Object2IntOpenHashMap;
/*     */ import java.lang.ref.SoftReference;
/*     */ import java.util.Collections;
/*     */ import java.util.Map;
/*     */ import java.util.function.Supplier;
/*     */ import javax.annotation.Nonnull;
/*     */ 
/*     */ public class SoundSet implements JsonAssetWithMap<String, IndexedLookupTableAssetMap<String, SoundSet>>, NetworkSerializable<SoundSet> {
/*  33 */   public static final SoundSet EMPTY_SOUND_SET = new SoundSet("EMPTY");
/*     */ 
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
/*     */ 
/*     */   
/*     */   public static final String EMPTY = "EMPTY";
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static final AssetBuilderCodec<String, SoundSet> CODEC;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   static {
/*  62 */     CODEC = ((AssetBuilderCodec.Builder)((AssetBuilderCodec.Builder)((AssetBuilderCodec.Builder)AssetBuilderCodec.builder(SoundSet.class, SoundSet::new, (Codec)Codec.STRING, (t, k) -> t.id = k, t -> t.id, (asset, data) -> asset.data = data, asset -> asset.data).appendInherited(new KeyedCodec("SoundEvents", (Codec)MapCodec.STRING_HASH_MAP_CODEC), (soundSet, s) -> soundSet.soundEventIds = s, soundSet -> soundSet.soundEventIds, (soundSet, parent) -> soundSet.soundEventIds = parent.soundEventIds).addValidator(Validators.nonNull()).addValidator((Validator)SoundEvent.VALIDATOR_CACHE.getMapValueValidator()).metadata((Metadata)UIDefaultCollapsedState.UNCOLLAPSED).add()).appendInherited(new KeyedCodec("Category", (Codec)new EnumCodec(SoundCategory.class)), (soundSet, s) -> soundSet.category = s, soundSet -> soundSet.category, (soundSet, parent) -> soundSet.category = parent.category).addValidator(Validators.nonNull()).add()).afterDecode(SoundSet::processConfig)).build();
/*     */   }
/*  64 */   public static final Codec<String> CHILD_ASSET_CODEC = (Codec<String>)new ContainedAssetCodec(SoundSet.class, (AssetCodec)CODEC);
/*     */   
/*  66 */   public static final ValidatorCache<String> VALIDATOR_CACHE = new ValidatorCache((Validator)new AssetKeyValidator(SoundSet::getAssetStore)); private static AssetStore<String, SoundSet, IndexedLookupTableAssetMap<String, SoundSet>> ASSET_STORE;
/*     */   protected AssetExtraInfo.Data data;
/*     */   protected String id;
/*     */   
/*     */   public static AssetStore<String, SoundSet, IndexedLookupTableAssetMap<String, SoundSet>> getAssetStore() {
/*  71 */     if (ASSET_STORE == null) ASSET_STORE = AssetRegistry.getAssetStore(SoundSet.class); 
/*  72 */     return ASSET_STORE;
/*     */   }
/*     */   
/*     */   public static IndexedLookupTableAssetMap<String, SoundSet> getAssetMap() {
/*  76 */     return (IndexedLookupTableAssetMap<String, SoundSet>)getAssetStore().getAssetMap();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  83 */   protected Map<String, String> soundEventIds = Collections.emptyMap();
/*  84 */   protected transient Object2IntMap<String> soundEventIndices = Object2IntMaps.emptyMap();
/*     */   @Nonnull
/*  86 */   protected SoundCategory category = SoundCategory.SFX;
/*     */   
/*     */   private SoftReference<SoundSet> cachedPacket;
/*     */ 
/*     */   
/*     */   public SoundSet(String id, Map<String, String> soundEventIds, SoundCategory category) {
/*  92 */     this.id = id;
/*  93 */     this.soundEventIds = soundEventIds;
/*  94 */     this.category = category;
/*     */   }
/*     */   
/*     */   public SoundSet(String id) {
/*  98 */     this.id = id;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getId() {
/* 106 */     return this.id;
/*     */   }
/*     */   
/*     */   public Map<String, String> getSoundEventIds() {
/* 110 */     return this.soundEventIds;
/*     */   }
/*     */   
/*     */   public Object2IntMap<String> getSoundEventIndices() {
/* 114 */     return this.soundEventIndices;
/*     */   }
/*     */   
/*     */   protected void processConfig() {
/* 118 */     if (!this.soundEventIds.isEmpty()) {
/* 119 */       this.soundEventIndices = (Object2IntMap<String>)new Object2IntOpenHashMap();
/* 120 */       for (Map.Entry<String, String> entry : this.soundEventIds.entrySet()) {
/* 121 */         this.soundEventIndices.put(entry.getKey(), SoundEvent.getAssetMap().getIndex(entry.getValue()));
/*     */       }
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   @Nonnull
/*     */   public String toString() {
/* 129 */     return "SoundSet{id='" + this.id + "', soundEventIds=" + String.valueOf(this.soundEventIds) + ", soundEventIndices=" + String.valueOf(this.soundEventIndices) + ", category=" + String.valueOf(this.category) + "}";
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Nonnull
/*     */   public SoundSet toPacket() {
/* 140 */     SoundSet cached = (this.cachedPacket == null) ? null : this.cachedPacket.get();
/* 141 */     if (cached != null) return cached;
/*     */     
/* 143 */     SoundSet packet = new SoundSet();
/*     */     
/* 145 */     packet.id = this.id;
/* 146 */     packet.sounds = (Map)this.soundEventIndices;
/* 147 */     packet.category = this.category;
/*     */     
/* 149 */     this.cachedPacket = new SoftReference<>(packet);
/* 150 */     return packet;
/*     */   }
/*     */   
/*     */   protected SoundSet() {}
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\server\core\asset\type\soundset\config\SoundSet.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */