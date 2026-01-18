/*     */ package com.hypixel.hytale.server.core.asset.type.soundevent.config;
/*     */ import com.hypixel.hytale.assetstore.AssetExtraInfo;
/*     */ import com.hypixel.hytale.assetstore.AssetKeyValidator;
/*     */ import com.hypixel.hytale.assetstore.AssetRegistry;
/*     */ import com.hypixel.hytale.assetstore.AssetStore;
/*     */ import com.hypixel.hytale.assetstore.codec.AssetBuilderCodec;
/*     */ import com.hypixel.hytale.assetstore.map.IndexedLookupTableAssetMap;
/*     */ import com.hypixel.hytale.assetstore.map.JsonAssetWithMap;
/*     */ import com.hypixel.hytale.codec.Codec;
/*     */ import com.hypixel.hytale.codec.KeyedCodec;
/*     */ import com.hypixel.hytale.codec.codecs.array.ArrayCodec;
/*     */ import com.hypixel.hytale.codec.schema.metadata.Metadata;
/*     */ import com.hypixel.hytale.codec.schema.metadata.ui.UIEditor;
/*     */ import com.hypixel.hytale.codec.validation.Validator;
/*     */ import com.hypixel.hytale.codec.validation.ValidatorCache;
/*     */ import com.hypixel.hytale.codec.validation.Validators;
/*     */ import com.hypixel.hytale.common.util.AudioUtil;
/*     */ import com.hypixel.hytale.protocol.SoundEvent;
/*     */ import com.hypixel.hytale.server.core.asset.type.audiocategory.config.AudioCategory;
/*     */ import com.hypixel.hytale.server.core.io.NetworkSerializable;
/*     */ import java.lang.ref.SoftReference;
/*     */ import java.util.Arrays;
/*     */ import java.util.function.Supplier;
/*     */ import javax.annotation.Nonnull;
/*     */ import javax.annotation.Nullable;
/*     */ 
/*     */ public class SoundEvent implements JsonAssetWithMap<String, IndexedLookupTableAssetMap<String, SoundEvent>>, NetworkSerializable<SoundEvent> {
/*  28 */   public static final SoundEvent EMPTY_SOUND_EVENT = new SoundEvent("EMPTY");
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
/*     */   public static final int EMPTY_ID = 0;
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
/*     */   public static final String EMPTY = "EMPTY";
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
/*     */   public static final AssetBuilderCodec<String, SoundEvent> CODEC;
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
/*     */   static {
/* 120 */     CODEC = ((AssetBuilderCodec.Builder)((AssetBuilderCodec.Builder)((AssetBuilderCodec.Builder)((AssetBuilderCodec.Builder)((AssetBuilderCodec.Builder)((AssetBuilderCodec.Builder)((AssetBuilderCodec.Builder)((AssetBuilderCodec.Builder)((AssetBuilderCodec.Builder)((AssetBuilderCodec.Builder)((AssetBuilderCodec.Builder)AssetBuilderCodec.builder(SoundEvent.class, SoundEvent::new, (Codec)Codec.STRING, (t, k) -> t.id = k, t -> t.id, (asset, data) -> asset.data = data, asset -> asset.data).appendInherited(new KeyedCodec("Volume", (Codec)Codec.FLOAT), (soundEvent, f) -> soundEvent.volume = AudioUtil.decibelsToLinearGain(f.floatValue()), soundEvent -> Float.valueOf(AudioUtil.linearGainToDecibels(soundEvent.volume)), (soundEvent, parent) -> soundEvent.volume = parent.volume).metadata((Metadata)new UIEditor((UIEditor.EditorComponent)new UIEditor.FormattedNumber(null, " dB", null))).addValidator(Validators.range(Float.valueOf(-100.0F), Float.valueOf(10.0F))).documentation("Volume adjustment of the sound event in decibels.").add()).appendInherited(new KeyedCodec("Pitch", (Codec)Codec.FLOAT), (soundEvent, f) -> soundEvent.pitch = AudioUtil.semitonesToLinearPitch(f.floatValue()), soundEvent -> Float.valueOf(AudioUtil.linearPitchToSemitones(soundEvent.pitch)), (soundEvent, parent) -> soundEvent.pitch = parent.pitch).addValidator(Validators.range(Float.valueOf(-12.0F), Float.valueOf(12.0F))).documentation("Pitch adjustment of the sound event in semitones.").add()).appendInherited(new KeyedCodec("MusicDuckingVolume", (Codec)Codec.FLOAT), (soundEvent, f) -> soundEvent.musicDuckingVolume = AudioUtil.decibelsToLinearGain(f.floatValue()), soundEvent -> Float.valueOf(AudioUtil.linearGainToDecibels(soundEvent.musicDuckingVolume)), (soundEvent, parent) -> soundEvent.musicDuckingVolume = parent.musicDuckingVolume).addValidator(Validators.range(Float.valueOf(-100.0F), Float.valueOf(0.0F))).documentation("Amount to duck music volume when playing in decibels.").add()).appendInherited(new KeyedCodec("AmbientDuckingVolume", (Codec)Codec.FLOAT), (soundEvent, f) -> soundEvent.ambientDuckingVolume = AudioUtil.decibelsToLinearGain(f.floatValue()), soundEvent -> Float.valueOf(AudioUtil.linearGainToDecibels(soundEvent.ambientDuckingVolume)), (soundEvent, parent) -> soundEvent.ambientDuckingVolume = parent.ambientDuckingVolume).addValidator(Validators.range(Float.valueOf(-100.0F), Float.valueOf(0.0F))).documentation("Amount to duck ambient sounds when playing in decibels.").add()).appendInherited(new KeyedCodec("StartAttenuationDistance", (Codec)Codec.FLOAT), (soundEvent, f) -> soundEvent.startAttenuationDistance = f.floatValue(), soundEvent -> Float.valueOf(soundEvent.startAttenuationDistance), (soundEvent, parent) -> soundEvent.startAttenuationDistance = parent.startAttenuationDistance).documentation("Distance at which to begin attenuation in blocks.").add()).appendInherited(new KeyedCodec("MaxDistance", (Codec)Codec.FLOAT), (soundEvent, f) -> soundEvent.maxDistance = f.floatValue(), soundEvent -> Float.valueOf(soundEvent.maxDistance), (soundEvent, parent) -> soundEvent.maxDistance = parent.maxDistance).documentation("Maximum distance at which this sound event can be heard in blocks (i.e. the distance at which it's attenuated to zero).").add()).appendInherited(new KeyedCodec("MaxInstance", (Codec)Codec.INTEGER), (soundEvent, i) -> soundEvent.maxInstance = i.intValue(), soundEvent -> Integer.valueOf(soundEvent.maxInstance), (soundEvent, parent) -> soundEvent.maxInstance = parent.maxInstance).addValidator(Validators.range(Integer.valueOf(1), Integer.valueOf(100))).documentation("Max concurrent number of instances of this sound event.").add()).appendInherited(new KeyedCodec("PreventSoundInterruption", (Codec)Codec.BOOLEAN), (soundEvent, b) -> soundEvent.preventSoundInterruption = b.booleanValue(), soundEvent -> Boolean.valueOf(soundEvent.preventSoundInterruption), (soundEvent, parent) -> soundEvent.preventSoundInterruption = parent.preventSoundInterruption).documentation("Whether to prevent interruption of this sound event.").add()).appendInherited(new KeyedCodec("Layers", (Codec)new ArrayCodec(SoundEventLayer.CODEC, x$0 -> new SoundEventLayer[x$0])), (soundEvent, objects) -> soundEvent.layers = objects, soundEvent -> soundEvent.layers, (soundEvent, parent) -> soundEvent.layers = parent.layers).addValidator(Validators.nonEmptyArray()).documentation("The layered sounds that make up this sound event.").add()).appendInherited(new KeyedCodec("AudioCategory", (Codec)Codec.STRING), (soundEvent, s) -> soundEvent.audioCategoryId = s, soundEvent -> soundEvent.audioCategoryId, (soundEvent, parent) -> soundEvent.audioCategoryId = parent.audioCategoryId).addValidator(AudioCategory.VALIDATOR_CACHE.getValidator()).documentation("Audio category to assign this sound event to for additional property routing.").add()).afterDecode(SoundEvent::processConfig)).build();
/*     */   }
/* 122 */   public static final ValidatorCache<String> VALIDATOR_CACHE = new ValidatorCache((Validator)new AssetKeyValidator(SoundEvent::getAssetStore)); private static AssetStore<String, SoundEvent, IndexedLookupTableAssetMap<String, SoundEvent>> ASSET_STORE;
/*     */   protected AssetExtraInfo.Data data;
/*     */   protected String id;
/*     */   
/*     */   public static AssetStore<String, SoundEvent, IndexedLookupTableAssetMap<String, SoundEvent>> getAssetStore() {
/* 127 */     if (ASSET_STORE == null) ASSET_STORE = AssetRegistry.getAssetStore(SoundEvent.class); 
/* 128 */     return ASSET_STORE;
/*     */   }
/*     */   
/*     */   public static IndexedLookupTableAssetMap<String, SoundEvent> getAssetMap() {
/* 132 */     return (IndexedLookupTableAssetMap<String, SoundEvent>)getAssetStore().getAssetMap();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 139 */   protected transient float volume = 1.0F;
/* 140 */   protected transient float pitch = 1.0F;
/* 141 */   protected transient float musicDuckingVolume = 1.0F;
/* 142 */   protected transient float ambientDuckingVolume = 1.0F;
/* 143 */   protected float startAttenuationDistance = 2.0F;
/* 144 */   protected float maxDistance = 16.0F;
/* 145 */   protected int maxInstance = 50;
/*     */   protected boolean preventSoundInterruption = false;
/*     */   protected SoundEventLayer[] layers;
/*     */   @Nullable
/* 149 */   protected String audioCategoryId = null;
/*     */   
/* 151 */   protected transient int audioCategoryIndex = 0;
/*     */   
/* 153 */   protected transient int highestNumberOfChannels = 0; private SoftReference<SoundEvent> cachedPacket;
/*     */   
/*     */   protected void processConfig() {
/* 156 */     if (this.audioCategoryId != null) {
/* 157 */       this.audioCategoryIndex = AudioCategory.getAssetMap().getIndex(this.audioCategoryId);
/*     */     }
/* 159 */     if (this.layers != null) {
/* 160 */       for (SoundEventLayer layer : this.layers) {
/* 161 */         if (layer.highestNumberOfChannels > this.highestNumberOfChannels) {
/* 162 */           this.highestNumberOfChannels = layer.highestNumberOfChannels;
/*     */         }
/*     */       } 
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public SoundEvent(String id, float volume, float pitch, float musicDuckingVolume, float ambientDuckingVolume, float startAttenuationDistance, float maxDistance, int maxInstance, boolean preventSoundInterruption, SoundEventLayer[] layers) {
/* 171 */     this.id = id;
/* 172 */     this.volume = volume;
/* 173 */     this.pitch = pitch;
/* 174 */     this.musicDuckingVolume = musicDuckingVolume;
/* 175 */     this.ambientDuckingVolume = ambientDuckingVolume;
/* 176 */     this.startAttenuationDistance = startAttenuationDistance;
/* 177 */     this.maxDistance = maxDistance;
/* 178 */     this.maxInstance = maxInstance;
/* 179 */     this.preventSoundInterruption = preventSoundInterruption;
/* 180 */     this.layers = layers;
/*     */   }
/*     */   
/*     */   public SoundEvent(String id) {
/* 184 */     this.id = id;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getId() {
/* 192 */     return this.id;
/*     */   }
/*     */   
/*     */   public float getVolume() {
/* 196 */     return this.volume;
/*     */   }
/*     */   
/*     */   public float getPitch() {
/* 200 */     return this.pitch;
/*     */   }
/*     */   
/*     */   public float getMusicDuckingVolume() {
/* 204 */     return this.musicDuckingVolume;
/*     */   }
/*     */   
/*     */   public float getAmbientDuckingVolume() {
/* 208 */     return this.ambientDuckingVolume;
/*     */   }
/*     */   
/*     */   public float getStartAttenuationDistance() {
/* 212 */     return this.startAttenuationDistance;
/*     */   }
/*     */   
/*     */   public float getMaxDistance() {
/* 216 */     return this.maxDistance;
/*     */   }
/*     */   
/*     */   public int getMaxInstance() {
/* 220 */     return this.maxInstance;
/*     */   }
/*     */   
/*     */   public boolean getPreventSoundInterruption() {
/* 224 */     return this.preventSoundInterruption;
/*     */   }
/*     */   
/*     */   public SoundEventLayer[] getLayers() {
/* 228 */     return this.layers;
/*     */   }
/*     */   
/*     */   @Nullable
/*     */   public String getAudioCategoryId() {
/* 233 */     return this.audioCategoryId;
/*     */   }
/*     */   
/*     */   public int getAudioCategoryIndex() {
/* 237 */     return this.audioCategoryIndex;
/*     */   }
/*     */   
/*     */   public int getHighestNumberOfChannels() {
/* 241 */     return this.highestNumberOfChannels;
/*     */   }
/*     */ 
/*     */   
/*     */   @Nonnull
/*     */   public String toString() {
/* 247 */     return "SoundEvent{id='" + this.id + "', volume=" + this.volume + ", pitch=" + this.pitch + ", musicDuckingVolume=" + this.musicDuckingVolume + ", ambientDuckingVolume=" + this.ambientDuckingVolume + ", startAttenuationDistance=" + this.startAttenuationDistance + ", maxDistance=" + this.maxDistance + ", maxInstance=" + this.maxInstance + ", preventSoundInterruption=" + this.preventSoundInterruption + ", layers=" + 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 257 */       Arrays.toString((Object[])this.layers) + ", audioCategoryId='" + this.audioCategoryId + "', audioCategoryIndex=" + this.audioCategoryIndex + ", highestNumberOfChannels=" + this.highestNumberOfChannels + "}";
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Nonnull
/*     */   public SoundEvent toPacket() {
/* 267 */     SoundEvent cached = (this.cachedPacket == null) ? null : this.cachedPacket.get();
/* 268 */     if (cached != null) return cached;
/*     */     
/* 270 */     SoundEvent packet = new SoundEvent();
/*     */     
/* 272 */     packet.id = this.id;
/* 273 */     packet.volume = this.volume;
/* 274 */     packet.pitch = this.pitch;
/* 275 */     packet.musicDuckingVolume = this.musicDuckingVolume;
/* 276 */     packet.ambientDuckingVolume = this.ambientDuckingVolume;
/* 277 */     packet.startAttenuationDistance = this.startAttenuationDistance;
/* 278 */     packet.maxDistance = this.maxDistance;
/* 279 */     packet.maxInstance = this.maxInstance;
/* 280 */     packet.preventSoundInterruption = this.preventSoundInterruption;
/* 281 */     packet.audioCategory = this.audioCategoryIndex;
/*     */     
/* 283 */     if (this.layers != null && this.layers.length > 0) {
/* 284 */       packet.layers = new com.hypixel.hytale.protocol.SoundEventLayer[this.layers.length];
/* 285 */       for (int i = 0; i < this.layers.length; i++) {
/* 286 */         packet.layers[i] = this.layers[i].toPacket();
/*     */       }
/*     */     } 
/*     */     
/* 290 */     this.cachedPacket = new SoftReference<>(packet);
/* 291 */     return packet;
/*     */   }
/*     */   
/*     */   protected SoundEvent() {}
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\server\core\asset\type\soundevent\config\SoundEvent.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */