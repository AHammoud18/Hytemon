/*     */ package com.hypixel.hytale.server.core.asset.type.blocksound.config;
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
/*     */ import com.hypixel.hytale.math.range.FloatRange;
/*     */ import com.hypixel.hytale.protocol.BlockSoundEvent;
/*     */ import com.hypixel.hytale.protocol.BlockSoundSet;
/*     */ import com.hypixel.hytale.protocol.FloatRange;
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
/*     */ public class BlockSoundSet implements JsonAssetWithMap<String, IndexedLookupTableAssetMap<String, BlockSoundSet>>, NetworkSerializable<BlockSoundSet> {
/*  34 */   private static final FloatRange DEFAULT_MOVE_IN_REPEAT_RANGE = new FloatRange(0.5F, 1.5F); public static final int EMPTY_ID = 0;
/*     */   public static final String EMPTY = "EMPTY";
/*  36 */   public static final BlockSoundSet EMPTY_BLOCK_SOUND_SET = new BlockSoundSet("EMPTY");
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
/*     */   public static final AssetBuilderCodec<String, BlockSoundSet> CODEC;
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
/*     */   static {
/*  67 */     CODEC = ((AssetBuilderCodec.Builder)((AssetBuilderCodec.Builder)((AssetBuilderCodec.Builder)AssetBuilderCodec.builder(BlockSoundSet.class, BlockSoundSet::new, (Codec)Codec.STRING, (blockSounds, s) -> blockSounds.id = s, blockSounds -> blockSounds.id, (asset, data) -> asset.data = data, asset -> asset.data).appendInherited(new KeyedCodec("SoundEvents", (Codec)new EnumMapCodec(BlockSoundEvent.class, (Codec)Codec.STRING)), (blockParticleSet, s) -> blockParticleSet.soundEventIds = s, blockParticleSet -> blockParticleSet.soundEventIds, (blockParticleSet, parent) -> blockParticleSet.soundEventIds = parent.soundEventIds).addValidator(Validators.nonNull()).addValidator((Validator)SoundEvent.VALIDATOR_CACHE.getMapValueValidator()).addValidator((Validator)SoundEventValidators.MONO_VALIDATOR_CACHE.getMapValueValidator()).addValidator((Validator)SoundEventValidators.ONESHOT_VALIDATOR_CACHE.getMapValueValidator()).metadata((Metadata)UIDefaultCollapsedState.UNCOLLAPSED).add()).appendInherited(new KeyedCodec("MoveInRepeatRange", (Codec)FloatRange.CODEC), (blockSounds, f) -> blockSounds.moveInRepeatRange = f, blockSounds -> blockSounds.moveInRepeatRange, (blockSounds, parent) -> blockSounds.moveInRepeatRange = parent.moveInRepeatRange).add()).afterDecode(BlockSoundSet::processConfig)).build();
/*     */   }
/*  69 */   public static final ValidatorCache<String> VALIDATOR_CACHE = new ValidatorCache((Validator)new AssetKeyValidator(BlockSoundSet::getAssetStore)); private static AssetStore<String, BlockSoundSet, IndexedLookupTableAssetMap<String, BlockSoundSet>> ASSET_STORE;
/*     */   protected AssetExtraInfo.Data data;
/*     */   protected String id;
/*     */   
/*     */   public static AssetStore<String, BlockSoundSet, IndexedLookupTableAssetMap<String, BlockSoundSet>> getAssetStore() {
/*  74 */     if (ASSET_STORE == null) ASSET_STORE = AssetRegistry.getAssetStore(BlockSoundSet.class); 
/*  75 */     return ASSET_STORE;
/*     */   }
/*     */   
/*     */   public static IndexedLookupTableAssetMap<String, BlockSoundSet> getAssetMap() {
/*  79 */     return (IndexedLookupTableAssetMap<String, BlockSoundSet>)getAssetStore().getAssetMap();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  85 */   protected Map<BlockSoundEvent, String> soundEventIds = Collections.emptyMap();
/*  86 */   protected transient Object2IntMap<BlockSoundEvent> soundEventIndices = Object2IntMaps.emptyMap();
/*     */   
/*  88 */   protected FloatRange moveInRepeatRange = DEFAULT_MOVE_IN_REPEAT_RANGE;
/*     */   
/*     */   private SoftReference<BlockSoundSet> cachedPacket;
/*     */   
/*     */   public BlockSoundSet(String id, Map<BlockSoundEvent, String> soundEventIds) {
/*  93 */     this.id = id;
/*  94 */     this.soundEventIds = soundEventIds;
/*     */   }
/*     */   
/*     */   public BlockSoundSet(String id) {
/*  98 */     this.id = id;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Nonnull
/*     */   public BlockSoundSet toPacket() {
/* 107 */     BlockSoundSet cached = (this.cachedPacket == null) ? null : this.cachedPacket.get();
/* 108 */     if (cached != null) return cached;
/*     */     
/* 110 */     BlockSoundSet packet = new BlockSoundSet();
/*     */     
/* 112 */     packet.id = this.id;
/* 113 */     packet.soundEventIndices = (Map)this.soundEventIndices;
/* 114 */     packet.moveInRepeatRange = new FloatRange(this.moveInRepeatRange.getInclusiveMin(), this.moveInRepeatRange.getInclusiveMax());
/*     */     
/* 116 */     this.cachedPacket = new SoftReference<>(packet);
/* 117 */     return packet;
/*     */   }
/*     */ 
/*     */   
/*     */   public String getId() {
/* 122 */     return this.id;
/*     */   }
/*     */   
/*     */   public Map<BlockSoundEvent, String> getSoundEventIds() {
/* 126 */     return this.soundEventIds;
/*     */   }
/*     */   
/*     */   public Object2IntMap<BlockSoundEvent> getSoundEventIndices() {
/* 130 */     return this.soundEventIndices;
/*     */   }
/*     */   
/*     */   public FloatRange getMoveInRepeatRange() {
/* 134 */     return this.moveInRepeatRange;
/*     */   }
/*     */   
/*     */   protected void processConfig() {
/* 138 */     if (!this.soundEventIds.isEmpty()) {
/* 139 */       this.soundEventIndices = (Object2IntMap<BlockSoundEvent>)new Object2IntOpenHashMap();
/* 140 */       for (Map.Entry<BlockSoundEvent, String> entry : this.soundEventIds.entrySet()) {
/* 141 */         this.soundEventIndices.put(entry.getKey(), SoundEvent.getAssetMap().getIndex(entry.getValue()));
/*     */       }
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   @Nonnull
/*     */   public String toString() {
/* 149 */     return "BlockSoundSet{id='" + this.id + "', soundEventIds=" + String.valueOf(this.soundEventIds) + ", soundEventIndices=" + String.valueOf(this.soundEventIndices) + ", moveInRepeatRange=" + String.valueOf(this.moveInRepeatRange) + "}";
/*     */   }
/*     */   
/*     */   protected BlockSoundSet() {}
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\server\core\asset\type\blocksound\config\BlockSoundSet.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */