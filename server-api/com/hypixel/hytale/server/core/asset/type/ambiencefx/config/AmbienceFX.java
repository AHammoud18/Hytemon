/*     */ package com.hypixel.hytale.server.core.asset.type.ambiencefx.config;
/*     */ import com.hypixel.hytale.assetstore.AssetExtraInfo;
/*     */ import com.hypixel.hytale.assetstore.AssetRegistry;
/*     */ import com.hypixel.hytale.assetstore.AssetStore;
/*     */ import com.hypixel.hytale.assetstore.codec.AssetBuilderCodec;
/*     */ import com.hypixel.hytale.assetstore.map.IndexedAssetMap;
/*     */ import com.hypixel.hytale.assetstore.map.JsonAssetWithMap;
/*     */ import com.hypixel.hytale.codec.Codec;
/*     */ import com.hypixel.hytale.codec.KeyedCodec;
/*     */ import com.hypixel.hytale.codec.codecs.array.ArrayCodec;
/*     */ import com.hypixel.hytale.codec.schema.metadata.Metadata;
/*     */ import com.hypixel.hytale.codec.schema.metadata.ui.UIDefaultCollapsedState;
/*     */ import com.hypixel.hytale.codec.schema.metadata.ui.UIEditorSectionStart;
/*     */ import com.hypixel.hytale.codec.validation.LateValidator;
/*     */ import com.hypixel.hytale.codec.validation.ValidatorCache;
/*     */ import com.hypixel.hytale.common.util.ArrayUtil;
/*     */ import com.hypixel.hytale.protocol.AmbienceFX;
/*     */ import com.hypixel.hytale.protocol.AmbienceFXSound;
/*     */ import com.hypixel.hytale.server.core.asset.type.audiocategory.config.AudioCategory;
/*     */ import com.hypixel.hytale.server.core.io.NetworkSerializable;
/*     */ import java.lang.ref.SoftReference;
/*     */ import java.util.Arrays;
/*     */ import javax.annotation.Nonnull;
/*     */ import javax.annotation.Nullable;
/*     */ 
/*     */ public class AmbienceFX implements JsonAssetWithMap<String, IndexedAssetMap<String, AmbienceFX>>, NetworkSerializable<AmbienceFX> {
/*  27 */   public static final ValidatorCache<String> VALIDATOR_CACHE = new ValidatorCache((Validator)new AssetKeyValidator(AmbienceFX::getAssetStore));
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
/*     */   public static final AssetBuilderCodec<String, AmbienceFX> CODEC;
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
/*     */ 
/*     */   
/*     */   static {
/* 103 */     CODEC = ((AssetBuilderCodec.Builder)((AssetBuilderCodec.Builder)((AssetBuilderCodec.Builder)((AssetBuilderCodec.Builder)((AssetBuilderCodec.Builder)((AssetBuilderCodec.Builder)((AssetBuilderCodec.Builder)((AssetBuilderCodec.Builder)((AssetBuilderCodec.Builder)AssetBuilderCodec.builder(AmbienceFX.class, AmbienceFX::new, (Codec)Codec.STRING, (ambienceFX, k) -> ambienceFX.id = k, ambienceFX -> ambienceFX.id, (asset, data) -> asset.data = data, asset -> asset.data).appendInherited(new KeyedCodec("Conditions", (Codec)AmbienceFXConditions.CODEC), (ambienceFX, l) -> ambienceFX.conditions = l, ambienceFX -> ambienceFX.conditions, (ambienceFX, parent) -> ambienceFX.conditions = parent.conditions).metadata((Metadata)new UIEditorSectionStart("Conditions")).metadata((Metadata)UIDefaultCollapsedState.UNCOLLAPSED).add()).appendInherited(new KeyedCodec("Sounds", (Codec)new ArrayCodec((Codec)AmbienceFXSound.CODEC, x$0 -> new AmbienceFXSound[x$0])), (ambienceFX, l) -> ambienceFX.sounds = l, ambienceFX -> ambienceFX.sounds, (ambienceFX, parent) -> ambienceFX.sounds = parent.sounds).metadata((Metadata)new UIEditorSectionStart("Audio")).add()).appendInherited(new KeyedCodec("Music", (Codec)AmbienceFXMusic.CODEC), (ambienceFX, l) -> ambienceFX.music = l, ambienceFX -> ambienceFX.music, (ambienceFX, parent) -> ambienceFX.music = parent.music).add()).appendInherited(new KeyedCodec("AmbientBed", (Codec)AmbienceFXAmbientBed.CODEC), (ambienceFX, l) -> ambienceFX.ambientBed = l, ambienceFX -> ambienceFX.ambientBed, (ambienceFX, parent) -> ambienceFX.ambientBed = parent.ambientBed).add()).appendInherited(new KeyedCodec("SoundEffect", (Codec)AmbienceFXSoundEffect.CODEC), (ambienceFX, l) -> ambienceFX.soundEffect = l, ambienceFX -> ambienceFX.soundEffect, (ambienceFX, parent) -> ambienceFX.soundEffect = parent.soundEffect).add()).appendInherited(new KeyedCodec("Priority", (Codec)Codec.INTEGER), (ambienceFX, i) -> ambienceFX.priority = i.intValue(), ambienceFX -> Integer.valueOf(ambienceFX.priority), (ambienceFX, parent) -> ambienceFX.priority = parent.priority).addValidator(Validators.greaterThanOrEqual(Integer.valueOf(0))).documentation("Priority for this AmbienceFX. Only applies to music and sound effect. Higher number means higher priority.").add()).appendInherited(new KeyedCodec("BlockedAmbienceFxIds", (Codec)Codec.STRING_ARRAY), (ambienceFX, s) -> ambienceFX.blockedAmbienceFxIds = s, ambienceFX -> ambienceFX.blockedAmbienceFxIds, (ambienceFX, parent) -> ambienceFX.blockedAmbienceFxIds = parent.blockedAmbienceFxIds).addValidatorLate(() -> VALIDATOR_CACHE.getArrayValidator().late()).add()).appendInherited(new KeyedCodec("AudioCategory", (Codec)Codec.STRING), (ambienceFX, s) -> ambienceFX.audioCategoryId = s, ambienceFX -> ambienceFX.audioCategoryId, (ambienceFX, parent) -> ambienceFX.audioCategoryId = parent.audioCategoryId).addValidator(AudioCategory.VALIDATOR_CACHE.getValidator()).documentation("Audio category to assign this ambienceFX to for additional property routing. Only affects ambient bed and music, not emitters.").add()).afterDecode(ambienceFX -> { if (ambienceFX.audioCategoryId != null) ambienceFX.audioCategoryIndex = AudioCategory.getAssetMap().getIndex(ambienceFX.audioCategoryId);  })).build();
/*     */   }
/*     */   
/* 106 */   public static final AmbienceFX EMPTY = new AmbienceFX() {  }
/*     */   ; private static AssetStore<String, AmbienceFX, IndexedAssetMap<String, AmbienceFX>> ASSET_STORE;
/*     */   protected AssetExtraInfo.Data data;
/*     */   protected String id;
/*     */   protected AmbienceFXConditions conditions;
/*     */   
/*     */   public static AssetStore<String, AmbienceFX, IndexedAssetMap<String, AmbienceFX>> getAssetStore() {
/* 113 */     if (ASSET_STORE == null) ASSET_STORE = AssetRegistry.getAssetStore(AmbienceFX.class); 
/* 114 */     return ASSET_STORE;
/*     */   }
/*     */   protected AmbienceFXSound[] sounds; protected AmbienceFXMusic music; protected AmbienceFXAmbientBed ambientBed; protected AmbienceFXSoundEffect soundEffect;
/*     */   public static IndexedAssetMap<String, AmbienceFX> getAssetMap() {
/* 118 */     return (IndexedAssetMap<String, AmbienceFX>)getAssetStore().getAssetMap();
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
/* 129 */   protected int priority = 0;
/*     */   
/*     */   protected String[] blockedAmbienceFxIds;
/*     */   @Nullable
/* 133 */   protected String audioCategoryId = null;
/*     */   
/* 135 */   protected transient int audioCategoryIndex = 0;
/*     */   
/*     */   private SoftReference<AmbienceFX> cachedPacket;
/*     */   
/*     */   public AmbienceFX(String id) {
/* 140 */     this.id = id;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Nonnull
/*     */   public AmbienceFX toPacket() {
/* 149 */     AmbienceFX cached = (this.cachedPacket == null) ? null : this.cachedPacket.get();
/* 150 */     if (cached != null) return cached;
/*     */     
/* 152 */     AmbienceFX packet = new AmbienceFX();
/* 153 */     packet.id = this.id;
/* 154 */     if (this.conditions != null) {
/* 155 */       packet.conditions = this.conditions.toPacket();
/*     */     }
/* 157 */     if (this.sounds != null && this.sounds.length > 0) {
/* 158 */       packet.sounds = (AmbienceFXSound[])ArrayUtil.copyAndMutate((Object[])this.sounds, AmbienceFXSound::toPacket, x$0 -> new AmbienceFXSound[x$0]);
/*     */     }
/* 160 */     if (this.music != null) {
/* 161 */       packet.music = this.music.toPacket();
/*     */     }
/* 163 */     if (this.ambientBed != null) {
/* 164 */       packet.ambientBed = this.ambientBed.toPacket();
/*     */     }
/* 166 */     if (this.soundEffect != null) {
/* 167 */       packet.soundEffect = this.soundEffect.toPacket();
/*     */     }
/* 169 */     packet.priority = this.priority;
/*     */     
/* 171 */     if (this.blockedAmbienceFxIds != null) {
/*     */       
/* 173 */       packet.blockedAmbienceFxIndices = new int[this.blockedAmbienceFxIds.length];
/* 174 */       for (int i = 0; i < this.blockedAmbienceFxIds.length; i++) {
/* 175 */         packet.blockedAmbienceFxIndices[i] = getAssetMap().getIndex(this.blockedAmbienceFxIds[i]);
/*     */       }
/*     */     } 
/*     */     
/* 179 */     packet.audioCategoryIndex = this.audioCategoryIndex;
/*     */     
/* 181 */     this.cachedPacket = new SoftReference<>(packet);
/* 182 */     return packet;
/*     */   }
/*     */ 
/*     */   
/*     */   public String getId() {
/* 187 */     return this.id;
/*     */   }
/*     */   
/*     */   public AmbienceFXConditions getConditions() {
/* 191 */     return this.conditions;
/*     */   }
/*     */   
/*     */   public AmbienceFXSound[] getSounds() {
/* 195 */     return this.sounds;
/*     */   }
/*     */   
/*     */   public AmbienceFXMusic getMusic() {
/* 199 */     return this.music;
/*     */   }
/*     */   
/*     */   public AmbienceFXAmbientBed getAmbientBed() {
/* 203 */     return this.ambientBed;
/*     */   }
/*     */   
/*     */   public AmbienceFXSoundEffect getSoundEffect() {
/* 207 */     return this.soundEffect;
/*     */   }
/*     */   public int getPriority() {
/* 210 */     return this.priority;
/*     */   }
/*     */   public String[] getBlockedAmbienceFxIds() {
/* 213 */     return this.blockedAmbienceFxIds;
/*     */   }
/*     */ 
/*     */   
/*     */   @Nonnull
/*     */   public String toString() {
/* 219 */     return "AmbienceFX{id='" + this.id + "', conditions=" + String.valueOf(this.conditions) + ", sounds=" + 
/*     */ 
/*     */       
/* 222 */       Arrays.toString((Object[])this.sounds) + ", music=" + String.valueOf(this.music) + ", ambientBed=" + String.valueOf(this.ambientBed) + ", soundEffect='" + String.valueOf(this.soundEffect) + ", priority=" + this.priority + "', blockedAmbienceFxIds=" + 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 227 */       Arrays.toString((Object[])this.blockedAmbienceFxIds) + ", audioCategoryId=" + this.audioCategoryId + "}";
/*     */   }
/*     */   
/*     */   protected AmbienceFX() {}
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\server\core\asset\type\ambiencefx\config\AmbienceFX.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */