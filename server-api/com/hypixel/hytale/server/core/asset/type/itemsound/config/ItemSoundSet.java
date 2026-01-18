/*     */ package com.hypixel.hytale.server.core.asset.type.itemsound.config;
/*     */ 
/*     */ import com.hypixel.hytale.assetstore.AssetExtraInfo;
/*     */ import com.hypixel.hytale.assetstore.AssetKeyValidator;
/*     */ import com.hypixel.hytale.assetstore.AssetRegistry;
/*     */ import com.hypixel.hytale.assetstore.AssetStore;
/*     */ import com.hypixel.hytale.assetstore.codec.AssetBuilderCodec;
/*     */ import com.hypixel.hytale.assetstore.map.IndexedLookupTableAssetMap;
/*     */ import com.hypixel.hytale.assetstore.map.JsonAssetWithMap;
/*     */ import com.hypixel.hytale.codec.Codec;
/*     */ import com.hypixel.hytale.codec.KeyedCodec;
/*     */ import com.hypixel.hytale.codec.codecs.map.EnumMapCodec;
/*     */ import com.hypixel.hytale.codec.schema.metadata.Metadata;
/*     */ import com.hypixel.hytale.codec.schema.metadata.ui.UIDefaultCollapsedState;
/*     */ import com.hypixel.hytale.codec.validation.Validator;
/*     */ import com.hypixel.hytale.codec.validation.ValidatorCache;
/*     */ import com.hypixel.hytale.codec.validation.Validators;
/*     */ import com.hypixel.hytale.protocol.ItemSoundEvent;
/*     */ import com.hypixel.hytale.protocol.ItemSoundSet;
/*     */ import com.hypixel.hytale.server.core.asset.type.soundevent.config.SoundEvent;
/*     */ import com.hypixel.hytale.server.core.asset.type.soundevent.validator.SoundEventValidators;
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
/*     */ 
/*     */ 
/*     */ public class ItemSoundSet
/*     */   implements JsonAssetWithMap<String, IndexedLookupTableAssetMap<String, ItemSoundSet>>, NetworkSerializable<ItemSoundSet>
/*     */ {
/*     */   public static final AssetBuilderCodec<String, ItemSoundSet> CODEC;
/*     */   
/*     */   static {
/*  52 */     CODEC = ((AssetBuilderCodec.Builder)((AssetBuilderCodec.Builder)AssetBuilderCodec.builder(ItemSoundSet.class, ItemSoundSet::new, (Codec)Codec.STRING, (itemSounds, s) -> itemSounds.id = s, itemSounds -> itemSounds.id, (asset, data) -> asset.data = data, asset -> asset.data).appendInherited(new KeyedCodec("SoundEvents", (Codec)new EnumMapCodec(ItemSoundEvent.class, (Codec)Codec.STRING)), (itemParticleSet, s) -> itemParticleSet.soundEventIds = s, itemParticleSet -> itemParticleSet.soundEventIds, (itemParticleSet, parent) -> itemParticleSet.soundEventIds = parent.soundEventIds).addValidator(Validators.nonNull()).addValidator((Validator)SoundEvent.VALIDATOR_CACHE.getMapValueValidator()).addValidator((Validator)SoundEventValidators.STEREO_VALIDATOR_CACHE.getMapValueValidator()).addValidator((Validator)SoundEventValidators.ONESHOT_VALIDATOR_CACHE.getMapValueValidator()).metadata((Metadata)UIDefaultCollapsedState.UNCOLLAPSED).add()).afterDecode(ItemSoundSet::processConfig)).build();
/*     */   }
/*  54 */   public static final ValidatorCache<String> VALIDATOR_CACHE = new ValidatorCache((Validator)new AssetKeyValidator(ItemSoundSet::getAssetStore)); private static AssetStore<String, ItemSoundSet, IndexedLookupTableAssetMap<String, ItemSoundSet>> ASSET_STORE;
/*     */   protected AssetExtraInfo.Data data;
/*     */   protected String id;
/*     */   
/*     */   public static AssetStore<String, ItemSoundSet, IndexedLookupTableAssetMap<String, ItemSoundSet>> getAssetStore() {
/*  59 */     if (ASSET_STORE == null) ASSET_STORE = AssetRegistry.getAssetStore(ItemSoundSet.class); 
/*  60 */     return ASSET_STORE;
/*     */   }
/*     */   
/*     */   public static IndexedLookupTableAssetMap<String, ItemSoundSet> getAssetMap() {
/*  64 */     return (IndexedLookupTableAssetMap<String, ItemSoundSet>)getAssetStore().getAssetMap();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  70 */   protected Map<ItemSoundEvent, String> soundEventIds = Collections.emptyMap();
/*  71 */   protected transient Object2IntMap<ItemSoundEvent> soundEventIndices = Object2IntMaps.emptyMap();
/*     */   
/*     */   private SoftReference<ItemSoundSet> cachedPacket;
/*     */   
/*     */   public ItemSoundSet(String id, Map<ItemSoundEvent, String> soundEventIds) {
/*  76 */     this.id = id;
/*  77 */     this.soundEventIds = soundEventIds;
/*     */   }
/*     */   
/*     */   public ItemSoundSet(String id) {
/*  81 */     this.id = id;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Nonnull
/*     */   public ItemSoundSet toPacket() {
/*  90 */     ItemSoundSet cached = (this.cachedPacket == null) ? null : this.cachedPacket.get();
/*  91 */     if (cached != null) return cached;
/*     */     
/*  93 */     ItemSoundSet packet = new ItemSoundSet();
/*     */     
/*  95 */     packet.id = this.id;
/*  96 */     packet.soundEventIndices = (Map)this.soundEventIndices;
/*     */     
/*  98 */     this.cachedPacket = new SoftReference<>(packet);
/*  99 */     return packet;
/*     */   }
/*     */ 
/*     */   
/*     */   public String getId() {
/* 104 */     return this.id;
/*     */   }
/*     */   
/*     */   public Map<ItemSoundEvent, String> getSoundEventIds() {
/* 108 */     return this.soundEventIds;
/*     */   }
/*     */   
/*     */   public Object2IntMap<ItemSoundEvent> getSoundEventIndices() {
/* 112 */     return this.soundEventIndices;
/*     */   }
/*     */   
/*     */   protected void processConfig() {
/* 116 */     if (!this.soundEventIds.isEmpty()) {
/* 117 */       this.soundEventIndices = (Object2IntMap<ItemSoundEvent>)new Object2IntOpenHashMap();
/* 118 */       for (Map.Entry<ItemSoundEvent, String> entry : this.soundEventIds.entrySet()) {
/* 119 */         this.soundEventIndices.put(entry.getKey(), SoundEvent.getAssetMap().getIndex(entry.getValue()));
/*     */       }
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   @Nonnull
/*     */   public String toString() {
/* 127 */     return "ItemSoundSet{id='" + this.id + "', soundEventIds=" + String.valueOf(this.soundEventIds) + ", soundEventIndices=" + String.valueOf(this.soundEventIndices) + "}";
/*     */   }
/*     */   
/*     */   protected ItemSoundSet() {}
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\server\core\asset\type\itemsound\config\ItemSoundSet.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */