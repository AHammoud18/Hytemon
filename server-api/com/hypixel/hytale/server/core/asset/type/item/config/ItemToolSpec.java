/*     */ package com.hypixel.hytale.server.core.asset.type.item.config;
/*     */ 
/*     */ import com.hypixel.hytale.assetstore.AssetExtraInfo;
/*     */ import com.hypixel.hytale.assetstore.AssetKeyValidator;
/*     */ import com.hypixel.hytale.assetstore.AssetRegistry;
/*     */ import com.hypixel.hytale.assetstore.AssetStore;
/*     */ import com.hypixel.hytale.assetstore.codec.AssetBuilderCodec;
/*     */ import com.hypixel.hytale.assetstore.codec.AssetCodec;
/*     */ import com.hypixel.hytale.assetstore.map.DefaultAssetMap;
/*     */ import com.hypixel.hytale.assetstore.map.JsonAssetWithMap;
/*     */ import com.hypixel.hytale.codec.Codec;
/*     */ import com.hypixel.hytale.codec.KeyedCodec;
/*     */ import com.hypixel.hytale.codec.validation.Validator;
/*     */ import com.hypixel.hytale.codec.validation.ValidatorCache;
/*     */ import com.hypixel.hytale.protocol.ItemToolSpec;
/*     */ import com.hypixel.hytale.server.core.asset.type.soundevent.config.SoundEvent;
/*     */ import com.hypixel.hytale.server.core.asset.type.soundevent.validator.SoundEventValidators;
/*     */ import com.hypixel.hytale.server.core.io.NetworkSerializable;
/*     */ import java.lang.ref.SoftReference;
/*     */ import java.util.function.Supplier;
/*     */ import javax.annotation.Nonnull;
/*     */ import javax.annotation.Nullable;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class ItemToolSpec
/*     */   implements JsonAssetWithMap<String, DefaultAssetMap<String, ItemToolSpec>>, NetworkSerializable<ItemToolSpec>
/*     */ {
/*     */   public static final AssetCodec<String, ItemToolSpec> CODEC;
/*     */   
/*     */   static {
/*  67 */     CODEC = (AssetCodec<String, ItemToolSpec>)((AssetBuilderCodec.Builder)((AssetBuilderCodec.Builder)((AssetBuilderCodec.Builder)((AssetBuilderCodec.Builder)((AssetBuilderCodec.Builder)((AssetBuilderCodec.Builder)AssetBuilderCodec.builder(ItemToolSpec.class, ItemToolSpec::new, (Codec)Codec.STRING, (itemToolSpec, s) -> itemToolSpec.gatherType = s, itemToolSpec -> itemToolSpec.gatherType, (asset, data) -> asset.data = data, asset -> asset.data).addField(new KeyedCodec("GatherType", (Codec)Codec.STRING), (itemToolSpec, s) -> itemToolSpec.gatherType = s, itemToolSpec -> itemToolSpec.gatherType)).addField(new KeyedCodec("Power", (Codec)Codec.DOUBLE), (itemToolSpec, d) -> itemToolSpec.power = d.floatValue(), itemToolSpec -> Double.valueOf(itemToolSpec.power))).addField(new KeyedCodec("Quality", (Codec)Codec.INTEGER), (itemToolSpec, i) -> itemToolSpec.quality = i.intValue(), itemToolSpec -> Integer.valueOf(itemToolSpec.quality))).addField(new KeyedCodec("IsIncorrect", (Codec)Codec.BOOLEAN), (itemToolSpec, i) -> itemToolSpec.incorrect = i.booleanValue(), itemToolSpec -> Boolean.valueOf(itemToolSpec.incorrect))).appendInherited(new KeyedCodec("HitSoundLayer", (Codec)Codec.STRING), (spec, s) -> spec.hitSoundLayerId = s, spec -> spec.hitSoundLayerId, (spec, parent) -> spec.hitSoundLayerId = parent.hitSoundLayerId).addValidator(SoundEvent.VALIDATOR_CACHE.getValidator()).addValidator((Validator)SoundEventValidators.MONO).documentation("Sound to play in addition to the block breaking sound when hitting this block type.").add()).afterDecode(ItemToolSpec::processConfig)).build();
/*     */   }
/*  69 */   public static final ValidatorCache<String> VALIDATOR_CACHE = new ValidatorCache((Validator)new AssetKeyValidator(ItemToolSpec::getAssetStore)); private static AssetStore<String, ItemToolSpec, DefaultAssetMap<String, ItemToolSpec>> ASSET_STORE; protected AssetExtraInfo.Data data;
/*     */   protected String gatherType;
/*     */   
/*     */   public static AssetStore<String, ItemToolSpec, DefaultAssetMap<String, ItemToolSpec>> getAssetStore() {
/*  73 */     if (ASSET_STORE == null) ASSET_STORE = AssetRegistry.getAssetStore(ItemToolSpec.class); 
/*  74 */     return ASSET_STORE;
/*     */   }
/*     */   protected float power; protected int quality; protected boolean incorrect;
/*     */   public static DefaultAssetMap<String, ItemToolSpec> getAssetMap() {
/*  78 */     return (DefaultAssetMap<String, ItemToolSpec>)getAssetStore().getAssetMap();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Nullable
/*  88 */   protected String hitSoundLayerId = null;
/*     */   
/*  90 */   protected transient int hitSoundLayerIndex = 0;
/*     */   
/*     */   private SoftReference<ItemToolSpec> cachedPacket;
/*     */   
/*     */   public ItemToolSpec(String gatherType, float power, int quality) {
/*  95 */     this.gatherType = gatherType;
/*  96 */     this.power = power;
/*  97 */     this.quality = quality;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void processConfig() {
/* 104 */     if (this.hitSoundLayerId != null) {
/* 105 */       this.hitSoundLayerIndex = SoundEvent.getAssetMap().getIndex(this.hitSoundLayerId);
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   @Nonnull
/*     */   public ItemToolSpec toPacket() {
/* 112 */     ItemToolSpec cached = (this.cachedPacket == null) ? null : this.cachedPacket.get();
/* 113 */     if (cached != null) return cached;
/*     */     
/* 115 */     ItemToolSpec packet = new ItemToolSpec();
/* 116 */     packet.gatherType = this.gatherType;
/* 117 */     packet.power = this.power;
/* 118 */     packet.quality = this.quality;
/*     */     
/* 120 */     this.cachedPacket = new SoftReference<>(packet);
/* 121 */     return packet;
/*     */   }
/*     */ 
/*     */   
/*     */   public String getId() {
/* 126 */     return this.gatherType;
/*     */   }
/*     */   
/*     */   public String getGatherType() {
/* 130 */     return this.gatherType;
/*     */   }
/*     */   
/*     */   public float getPower() {
/* 134 */     return this.power;
/*     */   }
/*     */   
/*     */   public int getQuality() {
/* 138 */     return this.quality;
/*     */   }
/*     */   
/*     */   public boolean isIncorrect() {
/* 142 */     return this.incorrect;
/*     */   }
/*     */   
/*     */   public int getHitSoundLayerIndex() {
/* 146 */     return this.hitSoundLayerIndex;
/*     */   }
/*     */ 
/*     */   
/*     */   @Nonnull
/*     */   public String toString() {
/* 152 */     return "ItemToolSpec{gatherType='" + this.gatherType + "', power=" + this.power + ", quality=" + this.quality + ", incorrect=" + this.incorrect + ", hitSoundLayerId='" + this.hitSoundLayerId + "', hitSoundLayerIndex=" + this.hitSoundLayerIndex + "}";
/*     */   }
/*     */   
/*     */   protected ItemToolSpec() {}
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\server\core\asset\type\item\config\ItemToolSpec.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */