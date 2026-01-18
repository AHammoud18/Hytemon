/*     */ package com.hypixel.hytale.server.core.asset.type.reverbeffect.config;
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
/*     */ import com.hypixel.hytale.protocol.ReverbEffect;
/*     */ import com.hypixel.hytale.server.core.io.NetworkSerializable;
/*     */ import java.lang.ref.SoftReference;
/*     */ import java.util.function.Supplier;
/*     */ import javax.annotation.Nonnull;
/*     */ 
/*     */ 
/*     */ public class ReverbEffect
/*     */   implements JsonAssetWithMap<String, IndexedLookupTableAssetMap<String, ReverbEffect>>, NetworkSerializable<ReverbEffect>
/*     */ {
/*     */   public static final int EMPTY_ID = 0;
/*     */   public static final String EMPTY = "EMPTY";
/*  31 */   public static final ReverbEffect EMPTY_REVERB_EFFECT = new ReverbEffect("EMPTY");
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
/*     */   public static final AssetBuilderCodec<String, ReverbEffect> CODEC;
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
/*     */   static {
/* 175 */     CODEC = ((AssetBuilderCodec.Builder)((AssetBuilderCodec.Builder)((AssetBuilderCodec.Builder)((AssetBuilderCodec.Builder)((AssetBuilderCodec.Builder)((AssetBuilderCodec.Builder)((AssetBuilderCodec.Builder)((AssetBuilderCodec.Builder)((AssetBuilderCodec.Builder)((AssetBuilderCodec.Builder)((AssetBuilderCodec.Builder)((AssetBuilderCodec.Builder)((AssetBuilderCodec.Builder)((AssetBuilderCodec.Builder)((AssetBuilderCodec.Builder)((AssetBuilderCodec.Builder)AssetBuilderCodec.builder(ReverbEffect.class, ReverbEffect::new, (Codec)Codec.STRING, (t, k) -> t.id = k, t -> t.id, (asset, data) -> asset.data = data, asset -> asset.data).documentation("An asset used to define a reverb audio effect.")).metadata((Metadata)new UIEditorPreview(UIEditorPreview.PreviewType.REVERB_EFFECT))).appendInherited(new KeyedCodec("DryGain", (Codec)Codec.FLOAT), (reverb, f) -> reverb.dryGain = AudioUtil.decibelsToLinearGain(f.floatValue()), reverb -> Float.valueOf(AudioUtil.linearGainToDecibels(reverb.dryGain)), (reverb, parent) -> reverb.dryGain = parent.dryGain).metadata((Metadata)new UIEditor((UIEditor.EditorComponent)new UIEditor.FormattedNumber(null, " dB", null))).addValidator(Validators.range(Float.valueOf(-100.0F), Float.valueOf(10.0F))).documentation("Dry signal gain adjustment in decibels.").add()).appendInherited(new KeyedCodec("ModalDensity", (Codec)Codec.FLOAT), (reverb, f) -> reverb.modalDensity = f.floatValue(), reverb -> Float.valueOf(reverb.modalDensity), (reverb, parent) -> reverb.modalDensity = parent.modalDensity).addValidator(Validators.range(Float.valueOf(0.0F), Float.valueOf(1.0F))).documentation("Modal density of the reverb.").add()).appendInherited(new KeyedCodec("Diffusion", (Codec)Codec.FLOAT), (reverb, f) -> reverb.diffusion = f.floatValue(), reverb -> Float.valueOf(reverb.diffusion), (reverb, parent) -> reverb.diffusion = parent.diffusion).addValidator(Validators.range(Float.valueOf(0.0F), Float.valueOf(1.0F))).documentation("Diffusion of the reverb reflections.").add()).appendInherited(new KeyedCodec("Gain", (Codec)Codec.FLOAT), (reverb, f) -> reverb.gain = AudioUtil.decibelsToLinearGain(f.floatValue()), reverb -> Float.valueOf(AudioUtil.linearGainToDecibels(reverb.gain)), (reverb, parent) -> reverb.gain = parent.gain).metadata((Metadata)new UIEditor((UIEditor.EditorComponent)new UIEditor.FormattedNumber(null, " dB", null))).addValidator(Validators.range(Float.valueOf(-100.0F), Float.valueOf(0.0F))).documentation("Overall reverb gain in decibels.").add()).appendInherited(new KeyedCodec("HighFrequencyGain", (Codec)Codec.FLOAT), (reverb, f) -> reverb.highFrequencyGain = AudioUtil.decibelsToLinearGain(f.floatValue()), reverb -> Float.valueOf(AudioUtil.linearGainToDecibels(reverb.highFrequencyGain)), (reverb, parent) -> reverb.highFrequencyGain = parent.highFrequencyGain).metadata((Metadata)new UIEditor((UIEditor.EditorComponent)new UIEditor.FormattedNumber(null, " dB", null))).addValidator(Validators.range(Float.valueOf(-100.0F), Float.valueOf(0.0F))).documentation("High frequency gain in decibels.").add()).appendInherited(new KeyedCodec("DecayTime", (Codec)Codec.FLOAT), (reverb, f) -> reverb.decayTime = f.floatValue(), reverb -> Float.valueOf(reverb.decayTime), (reverb, parent) -> reverb.decayTime = parent.decayTime).addValidator(Validators.range(Float.valueOf(0.1F), Float.valueOf(20.0F))).documentation("Decay time in seconds.").add()).appendInherited(new KeyedCodec("HighFrequencyDecayRatio", (Codec)Codec.FLOAT), (reverb, f) -> reverb.highFrequencyDecayRatio = f.floatValue(), reverb -> Float.valueOf(reverb.highFrequencyDecayRatio), (reverb, parent) -> reverb.highFrequencyDecayRatio = parent.highFrequencyDecayRatio).addValidator(Validators.range(Float.valueOf(0.1F), Float.valueOf(2.0F))).documentation("High frequency decay ratio.").add()).appendInherited(new KeyedCodec("ReflectionGain", (Codec)Codec.FLOAT), (reverb, f) -> reverb.reflectionGain = AudioUtil.decibelsToLinearGain(f.floatValue()), reverb -> Float.valueOf(AudioUtil.linearGainToDecibels(reverb.reflectionGain)), (reverb, parent) -> reverb.reflectionGain = parent.reflectionGain).metadata((Metadata)new UIEditor((UIEditor.EditorComponent)new UIEditor.FormattedNumber(null, " dB", null))).addValidator(Validators.range(Float.valueOf(-100.0F), Float.valueOf(10.0F))).documentation("Early reflections gain in decibels.").add()).appendInherited(new KeyedCodec("ReflectionDelay", (Codec)Codec.FLOAT), (reverb, f) -> reverb.reflectionDelay = f.floatValue(), reverb -> Float.valueOf(reverb.reflectionDelay), (reverb, parent) -> reverb.reflectionDelay = parent.reflectionDelay).addValidator(Validators.range(Float.valueOf(0.0F), Float.valueOf(0.3F))).documentation("Early reflections delay in seconds.").add()).appendInherited(new KeyedCodec("LateReverbGain", (Codec)Codec.FLOAT), (reverb, f) -> reverb.lateReverbGain = AudioUtil.decibelsToLinearGain(f.floatValue()), reverb -> Float.valueOf(AudioUtil.linearGainToDecibels(reverb.lateReverbGain)), (reverb, parent) -> reverb.lateReverbGain = parent.lateReverbGain).metadata((Metadata)new UIEditor((UIEditor.EditorComponent)new UIEditor.FormattedNumber(null, " dB", null))).addValidator(Validators.range(Float.valueOf(-100.0F), Float.valueOf(20.0F))).documentation("Late reverb gain in decibels.").add()).appendInherited(new KeyedCodec("LateReverbDelay", (Codec)Codec.FLOAT), (reverb, f) -> reverb.lateReverbDelay = f.floatValue(), reverb -> Float.valueOf(reverb.lateReverbDelay), (reverb, parent) -> reverb.lateReverbDelay = parent.lateReverbDelay).addValidator(Validators.range(Float.valueOf(0.0F), Float.valueOf(0.1F))).documentation("Late reverb delay in seconds.").add()).appendInherited(new KeyedCodec("RoomRolloffFactor", (Codec)Codec.FLOAT), (reverb, f) -> reverb.roomRolloffFactor = f.floatValue(), reverb -> Float.valueOf(reverb.roomRolloffFactor), (reverb, parent) -> reverb.roomRolloffFactor = parent.roomRolloffFactor).addValidator(Validators.range(Float.valueOf(0.0F), Float.valueOf(10.0F))).documentation("Room rolloff factor.").add()).appendInherited(new KeyedCodec("AirAbsorbptionHighFrequencyGain", (Codec)Codec.FLOAT), (reverb, f) -> reverb.airAbsorptionHighFrequencyGain = AudioUtil.decibelsToLinearGain(f.floatValue()), reverb -> Float.valueOf(AudioUtil.linearGainToDecibels(reverb.airAbsorptionHighFrequencyGain)), (reverb, parent) -> reverb.airAbsorptionHighFrequencyGain = parent.airAbsorptionHighFrequencyGain).metadata((Metadata)new UIEditor((UIEditor.EditorComponent)new UIEditor.FormattedNumber(null, " dB", null))).addValidator(Validators.range(Float.valueOf(-1.0F), Float.valueOf(0.0F))).documentation("Air absorption high frequency gain in decibels.").add()).appendInherited(new KeyedCodec("LimitDecayHighFrequency", (Codec)Codec.BOOLEAN), (reverb, b) -> reverb.limitDecayHighFrequency = b.booleanValue(), reverb -> Boolean.valueOf(reverb.limitDecayHighFrequency), (reverb, parent) -> reverb.limitDecayHighFrequency = parent.limitDecayHighFrequency).documentation("Whether to limit high frequency decay time.").add()).build();
/*     */   }
/* 177 */   public static final ValidatorCache<String> VALIDATOR_CACHE = new ValidatorCache((Validator)new AssetKeyValidator(ReverbEffect::getAssetStore)); private static AssetStore<String, ReverbEffect, IndexedLookupTableAssetMap<String, ReverbEffect>> ASSET_STORE;
/*     */   protected AssetExtraInfo.Data data;
/*     */   protected String id;
/*     */   
/*     */   public static AssetStore<String, ReverbEffect, IndexedLookupTableAssetMap<String, ReverbEffect>> getAssetStore() {
/* 182 */     if (ASSET_STORE == null) ASSET_STORE = AssetRegistry.getAssetStore(ReverbEffect.class); 
/* 183 */     return ASSET_STORE;
/*     */   }
/*     */   
/*     */   public static IndexedLookupTableAssetMap<String, ReverbEffect> getAssetMap() {
/* 187 */     return (IndexedLookupTableAssetMap<String, ReverbEffect>)getAssetStore().getAssetMap();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 195 */   protected float dryGain = AudioUtil.decibelsToLinearGain(0.0F);
/* 196 */   protected float modalDensity = 1.0F;
/* 197 */   protected float diffusion = 1.0F;
/* 198 */   protected float gain = AudioUtil.decibelsToLinearGain(-10.0F);
/* 199 */   protected float highFrequencyGain = AudioUtil.decibelsToLinearGain(-1.0F);
/* 200 */   protected float decayTime = 1.49F;
/* 201 */   protected float highFrequencyDecayRatio = 0.83F;
/* 202 */   protected float reflectionGain = AudioUtil.decibelsToLinearGain(-26.0F);
/* 203 */   protected float reflectionDelay = 0.007F;
/* 204 */   protected float lateReverbGain = AudioUtil.decibelsToLinearGain(2.0F);
/* 205 */   protected float lateReverbDelay = 0.011F;
/* 206 */   protected float roomRolloffFactor = 0.0F;
/* 207 */   protected float airAbsorptionHighFrequencyGain = AudioUtil.decibelsToLinearGain(-0.05F);
/*     */   
/*     */   protected boolean limitDecayHighFrequency = true;
/*     */   private SoftReference<ReverbEffect> cachedPacket;
/*     */   
/*     */   public ReverbEffect(String id) {
/* 213 */     this.id = id;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getId() {
/* 221 */     return this.id;
/*     */   }
/*     */   
/*     */   public float getDryGain() {
/* 225 */     return this.dryGain;
/*     */   }
/*     */   
/*     */   public float getModalDensity() {
/* 229 */     return this.modalDensity;
/*     */   }
/*     */   
/*     */   public float getDiffusion() {
/* 233 */     return this.diffusion;
/*     */   }
/*     */   
/*     */   public float getGain() {
/* 237 */     return this.gain;
/*     */   }
/*     */   
/*     */   public float getHighFrequencyGain() {
/* 241 */     return this.highFrequencyGain;
/*     */   }
/*     */   
/*     */   public float getDecayTime() {
/* 245 */     return this.decayTime;
/*     */   }
/*     */   
/*     */   public float getHighFrequencyDecayRatio() {
/* 249 */     return this.highFrequencyDecayRatio;
/*     */   }
/*     */   
/*     */   public float getReflectionGain() {
/* 253 */     return this.reflectionGain;
/*     */   }
/*     */   
/*     */   public float getReflectionDelay() {
/* 257 */     return this.reflectionDelay;
/*     */   }
/*     */   
/*     */   public float getLateReverbGain() {
/* 261 */     return this.lateReverbGain;
/*     */   }
/*     */   
/*     */   public float getLateReverbDelay() {
/* 265 */     return this.lateReverbDelay;
/*     */   }
/*     */   
/*     */   public float getRoomRolloffFactor() {
/* 269 */     return this.roomRolloffFactor;
/*     */   }
/*     */   
/*     */   public float getAirAbsorptionHighFrequencyGain() {
/* 273 */     return this.airAbsorptionHighFrequencyGain;
/*     */   }
/*     */   
/*     */   public boolean isLimitDecayHighFrequency() {
/* 277 */     return this.limitDecayHighFrequency;
/*     */   }
/*     */ 
/*     */   
/*     */   @Nonnull
/*     */   public String toString() {
/* 283 */     return "ReverbEffect{id='" + this.id + "', dryGain=" + this.dryGain + ", modalDensity=" + this.modalDensity + ", diffusion=" + this.diffusion + ", gain=" + this.gain + ", highFrequencyGain=" + this.highFrequencyGain + ", decayTime=" + this.decayTime + ", highFrequencyDecayRatio=" + this.highFrequencyDecayRatio + ", reflectionGain=" + this.reflectionGain + ", reflectionDelay=" + this.reflectionDelay + ", lateReverbGain=" + this.lateReverbGain + ", lateReverbDelay=" + this.lateReverbDelay + ", roomRolloffFactor=" + this.roomRolloffFactor + ", airAbsorptionHighFrequencyGain=" + this.airAbsorptionHighFrequencyGain + ", limitDecayHighFrequency=" + this.limitDecayHighFrequency + "}";
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
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Nonnull
/*     */   public ReverbEffect toPacket() {
/* 305 */     ReverbEffect cached = (this.cachedPacket == null) ? null : this.cachedPacket.get();
/* 306 */     if (cached != null) return cached;
/*     */     
/* 308 */     ReverbEffect packet = new ReverbEffect();
/*     */     
/* 310 */     packet.id = this.id;
/* 311 */     packet.dryGain = this.dryGain;
/* 312 */     packet.modalDensity = this.modalDensity;
/* 313 */     packet.diffusion = this.diffusion;
/* 314 */     packet.gain = this.gain;
/* 315 */     packet.highFrequencyGain = this.highFrequencyGain;
/* 316 */     packet.decayTime = this.decayTime;
/* 317 */     packet.highFrequencyDecayRatio = this.highFrequencyDecayRatio;
/* 318 */     packet.reflectionGain = this.reflectionGain;
/* 319 */     packet.reflectionDelay = this.reflectionDelay;
/* 320 */     packet.lateReverbGain = this.lateReverbGain;
/* 321 */     packet.lateReverbDelay = this.lateReverbDelay;
/* 322 */     packet.roomRolloffFactor = this.roomRolloffFactor;
/* 323 */     packet.airAbsorptionHighFrequencyGain = this.airAbsorptionHighFrequencyGain;
/* 324 */     packet.limitDecayHighFrequency = this.limitDecayHighFrequency;
/*     */     
/* 326 */     this.cachedPacket = new SoftReference<>(packet);
/* 327 */     return packet;
/*     */   }
/*     */   
/*     */   protected ReverbEffect() {}
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\server\core\asset\type\reverbeffect\config\ReverbEffect.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */