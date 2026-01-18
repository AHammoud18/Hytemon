/*     */ package com.hypixel.hytale.server.core.asset.type.equalizereffect.config;
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
/*     */ import com.hypixel.hytale.codec.schema.metadata.Metadata;
/*     */ import com.hypixel.hytale.codec.schema.metadata.ui.UIEditor;
/*     */ import com.hypixel.hytale.codec.schema.metadata.ui.UIEditorPreview;
/*     */ import com.hypixel.hytale.codec.validation.Validator;
/*     */ import com.hypixel.hytale.codec.validation.ValidatorCache;
/*     */ import com.hypixel.hytale.codec.validation.Validators;
/*     */ import com.hypixel.hytale.common.util.AudioUtil;
/*     */ import com.hypixel.hytale.protocol.EqualizerEffect;
/*     */ import com.hypixel.hytale.server.core.io.NetworkSerializable;
/*     */ import java.lang.ref.SoftReference;
/*     */ import java.util.function.Supplier;
/*     */ import javax.annotation.Nonnull;
/*     */ 
/*     */ 
/*     */ public class EqualizerEffect
/*     */   implements JsonAssetWithMap<String, IndexedLookupTableAssetMap<String, EqualizerEffect>>, NetworkSerializable<EqualizerEffect>
/*     */ {
/*     */   public static final int EMPTY_ID = 0;
/*     */   public static final String EMPTY = "EMPTY";
/*  31 */   public static final EqualizerEffect EMPTY_EQUALIZER_EFFECT = new EqualizerEffect("EMPTY");
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static final float MIN_GAIN_DB = -18.0F;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static final float MAX_GAIN_DB = 18.0F;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static final float MIN_WIDTH = 0.01F;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static final float MAX_WIDTH = 1.0F;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static final float LOW_FREQ_MIN = 50.0F;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static final float LOW_FREQ_MAX = 800.0F;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static final float LOW_MID_FREQ_MIN = 200.0F;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static final float LOW_MID_FREQ_MAX = 3000.0F;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static final float HIGH_MID_FREQ_MIN = 1000.0F;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static final float HIGH_MID_FREQ_MAX = 8000.0F;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static final float HIGH_FREQ_MIN = 4000.0F;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static final float HIGH_FREQ_MAX = 16000.0F;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static final AssetBuilderCodec<String, EqualizerEffect> CODEC;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   static {
/* 152 */     CODEC = ((AssetBuilderCodec.Builder)((AssetBuilderCodec.Builder)((AssetBuilderCodec.Builder)((AssetBuilderCodec.Builder)((AssetBuilderCodec.Builder)((AssetBuilderCodec.Builder)((AssetBuilderCodec.Builder)((AssetBuilderCodec.Builder)((AssetBuilderCodec.Builder)((AssetBuilderCodec.Builder)((AssetBuilderCodec.Builder)((AssetBuilderCodec.Builder)AssetBuilderCodec.builder(EqualizerEffect.class, EqualizerEffect::new, (Codec)Codec.STRING, (t, k) -> t.id = k, t -> t.id, (asset, data) -> asset.data = data, asset -> asset.data).documentation("An asset used to define a 4-band equalizer audio effect.")).metadata((Metadata)new UIEditorPreview(UIEditorPreview.PreviewType.EQUALIZER_EFFECT))).appendInherited(new KeyedCodec("LowGain", (Codec)Codec.FLOAT), (eq, f) -> eq.lowGain = AudioUtil.decibelsToLinearGain(f.floatValue()), eq -> Float.valueOf(AudioUtil.linearGainToDecibels(eq.lowGain)), (eq, parent) -> eq.lowGain = parent.lowGain).metadata((Metadata)new UIEditor((UIEditor.EditorComponent)new UIEditor.FormattedNumber(null, " dB", null))).addValidator(Validators.range(Float.valueOf(-18.0F), Float.valueOf(18.0F))).documentation("Low band gain in decibels.").add()).appendInherited(new KeyedCodec("LowCutOff", (Codec)Codec.FLOAT), (eq, f) -> eq.lowCutOff = f.floatValue(), eq -> Float.valueOf(eq.lowCutOff), (eq, parent) -> eq.lowCutOff = parent.lowCutOff).addValidator(Validators.range(Float.valueOf(50.0F), Float.valueOf(800.0F))).documentation("Low band cutoff frequency in Hz.").add()).appendInherited(new KeyedCodec("LowMidGain", (Codec)Codec.FLOAT), (eq, f) -> eq.lowMidGain = AudioUtil.decibelsToLinearGain(f.floatValue()), eq -> Float.valueOf(AudioUtil.linearGainToDecibels(eq.lowMidGain)), (eq, parent) -> eq.lowMidGain = parent.lowMidGain).metadata((Metadata)new UIEditor((UIEditor.EditorComponent)new UIEditor.FormattedNumber(null, " dB", null))).addValidator(Validators.range(Float.valueOf(-18.0F), Float.valueOf(18.0F))).documentation("Low-mid band gain in decibels.").add()).appendInherited(new KeyedCodec("LowMidCenter", (Codec)Codec.FLOAT), (eq, f) -> eq.lowMidCenter = f.floatValue(), eq -> Float.valueOf(eq.lowMidCenter), (eq, parent) -> eq.lowMidCenter = parent.lowMidCenter).addValidator(Validators.range(Float.valueOf(200.0F), Float.valueOf(3000.0F))).documentation("Low-mid band center frequency in Hz.").add()).appendInherited(new KeyedCodec("LowMidWidth", (Codec)Codec.FLOAT), (eq, f) -> eq.lowMidWidth = f.floatValue(), eq -> Float.valueOf(eq.lowMidWidth), (eq, parent) -> eq.lowMidWidth = parent.lowMidWidth).addValidator(Validators.range(Float.valueOf(0.01F), Float.valueOf(1.0F))).documentation("Low-mid band width.").add()).appendInherited(new KeyedCodec("HighMidGain", (Codec)Codec.FLOAT), (eq, f) -> eq.highMidGain = AudioUtil.decibelsToLinearGain(f.floatValue()), eq -> Float.valueOf(AudioUtil.linearGainToDecibels(eq.highMidGain)), (eq, parent) -> eq.highMidGain = parent.highMidGain).metadata((Metadata)new UIEditor((UIEditor.EditorComponent)new UIEditor.FormattedNumber(null, " dB", null))).addValidator(Validators.range(Float.valueOf(-18.0F), Float.valueOf(18.0F))).documentation("High-mid band gain in decibels.").add()).appendInherited(new KeyedCodec("HighMidCenter", (Codec)Codec.FLOAT), (eq, f) -> eq.highMidCenter = f.floatValue(), eq -> Float.valueOf(eq.highMidCenter), (eq, parent) -> eq.highMidCenter = parent.highMidCenter).addValidator(Validators.range(Float.valueOf(1000.0F), Float.valueOf(8000.0F))).documentation("High-mid band center frequency in Hz.").add()).appendInherited(new KeyedCodec("HighMidWidth", (Codec)Codec.FLOAT), (eq, f) -> eq.highMidWidth = f.floatValue(), eq -> Float.valueOf(eq.highMidWidth), (eq, parent) -> eq.highMidWidth = parent.highMidWidth).addValidator(Validators.range(Float.valueOf(0.01F), Float.valueOf(1.0F))).documentation("High-mid band width.").add()).appendInherited(new KeyedCodec("HighGain", (Codec)Codec.FLOAT), (eq, f) -> eq.highGain = AudioUtil.decibelsToLinearGain(f.floatValue()), eq -> Float.valueOf(AudioUtil.linearGainToDecibels(eq.highGain)), (eq, parent) -> eq.highGain = parent.highGain).metadata((Metadata)new UIEditor((UIEditor.EditorComponent)new UIEditor.FormattedNumber(null, " dB", null))).addValidator(Validators.range(Float.valueOf(-18.0F), Float.valueOf(18.0F))).documentation("High band gain in decibels.").add()).appendInherited(new KeyedCodec("HighCutOff", (Codec)Codec.FLOAT), (eq, f) -> eq.highCutOff = f.floatValue(), eq -> Float.valueOf(eq.highCutOff), (eq, parent) -> eq.highCutOff = parent.highCutOff).addValidator(Validators.range(Float.valueOf(4000.0F), Float.valueOf(16000.0F))).documentation("High band cutoff frequency in Hz.").add()).build();
/*     */   }
/* 154 */   public static final ValidatorCache<String> VALIDATOR_CACHE = new ValidatorCache((Validator)new AssetKeyValidator(EqualizerEffect::getAssetStore)); private static AssetStore<String, EqualizerEffect, IndexedLookupTableAssetMap<String, EqualizerEffect>> ASSET_STORE;
/*     */   protected AssetExtraInfo.Data data;
/*     */   protected String id;
/*     */   
/*     */   public static AssetStore<String, EqualizerEffect, IndexedLookupTableAssetMap<String, EqualizerEffect>> getAssetStore() {
/* 159 */     if (ASSET_STORE == null) ASSET_STORE = AssetRegistry.getAssetStore(EqualizerEffect.class); 
/* 160 */     return ASSET_STORE;
/*     */   }
/*     */   
/*     */   public static IndexedLookupTableAssetMap<String, EqualizerEffect> getAssetMap() {
/* 164 */     return (IndexedLookupTableAssetMap<String, EqualizerEffect>)getAssetStore().getAssetMap();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 172 */   protected float lowGain = 1.0F;
/* 173 */   protected float lowCutOff = 200.0F;
/* 174 */   protected float lowMidGain = 1.0F;
/* 175 */   protected float lowMidCenter = 500.0F;
/* 176 */   protected float lowMidWidth = 1.0F;
/* 177 */   protected float highMidGain = 1.0F;
/* 178 */   protected float highMidCenter = 3000.0F;
/* 179 */   protected float highMidWidth = 1.0F;
/* 180 */   protected float highGain = 1.0F;
/* 181 */   protected float highCutOff = 6000.0F;
/*     */   
/*     */   private SoftReference<EqualizerEffect> cachedPacket;
/*     */   
/*     */   public EqualizerEffect(String id) {
/* 186 */     this.id = id;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getId() {
/* 194 */     return this.id;
/*     */   }
/*     */   
/*     */   public float getLowGain() {
/* 198 */     return this.lowGain;
/*     */   }
/*     */   
/*     */   public float getLowCutOff() {
/* 202 */     return this.lowCutOff;
/*     */   }
/*     */   
/*     */   public float getLowMidGain() {
/* 206 */     return this.lowMidGain;
/*     */   }
/*     */   
/*     */   public float getLowMidCenter() {
/* 210 */     return this.lowMidCenter;
/*     */   }
/*     */   
/*     */   public float getLowMidWidth() {
/* 214 */     return this.lowMidWidth;
/*     */   }
/*     */   
/*     */   public float getHighMidGain() {
/* 218 */     return this.highMidGain;
/*     */   }
/*     */   
/*     */   public float getHighMidCenter() {
/* 222 */     return this.highMidCenter;
/*     */   }
/*     */   
/*     */   public float getHighMidWidth() {
/* 226 */     return this.highMidWidth;
/*     */   }
/*     */   
/*     */   public float getHighGain() {
/* 230 */     return this.highGain;
/*     */   }
/*     */   
/*     */   public float getHighCutOff() {
/* 234 */     return this.highCutOff;
/*     */   }
/*     */ 
/*     */   
/*     */   @Nonnull
/*     */   public String toString() {
/* 240 */     return "EqualizerEffect{id='" + this.id + "', lowGain=" + this.lowGain + ", lowCutOff=" + this.lowCutOff + ", lowMidGain=" + this.lowMidGain + ", lowMidCenter=" + this.lowMidCenter + ", lowMidWidth=" + this.lowMidWidth + ", highMidGain=" + this.highMidGain + ", highMidCenter=" + this.highMidCenter + ", highMidWidth=" + this.highMidWidth + ", highGain=" + this.highGain + ", highCutOff=" + this.highCutOff + "}";
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
/*     */ 
/*     */   
/*     */   @Nonnull
/*     */   public EqualizerEffect toPacket() {
/* 258 */     EqualizerEffect cached = (this.cachedPacket == null) ? null : this.cachedPacket.get();
/* 259 */     if (cached != null) return cached;
/*     */     
/* 261 */     EqualizerEffect packet = new EqualizerEffect();
/*     */     
/* 263 */     packet.id = this.id;
/* 264 */     packet.lowGain = this.lowGain;
/* 265 */     packet.lowCutOff = this.lowCutOff;
/* 266 */     packet.lowMidGain = this.lowMidGain;
/* 267 */     packet.lowMidCenter = this.lowMidCenter;
/* 268 */     packet.lowMidWidth = this.lowMidWidth;
/* 269 */     packet.highMidGain = this.highMidGain;
/* 270 */     packet.highMidCenter = this.highMidCenter;
/* 271 */     packet.highMidWidth = this.highMidWidth;
/* 272 */     packet.highGain = this.highGain;
/* 273 */     packet.highCutOff = this.highCutOff;
/*     */     
/* 275 */     this.cachedPacket = new SoftReference<>(packet);
/* 276 */     return packet;
/*     */   }
/*     */   
/*     */   protected EqualizerEffect() {}
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\server\core\asset\type\equalizereffect\config\EqualizerEffect.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */