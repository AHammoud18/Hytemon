/*     */ package com.hypixel.hytale.server.core.asset.type.soundevent.config;
/*     */ 
/*     */ import com.hypixel.hytale.codec.Codec;
/*     */ import com.hypixel.hytale.codec.KeyedCodec;
/*     */ import com.hypixel.hytale.codec.builder.BuilderCodec;
/*     */ import com.hypixel.hytale.codec.validation.Validators;
/*     */ import com.hypixel.hytale.common.util.AudioUtil;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class RandomSettings
/*     */ {
/*     */   public static final Codec<RandomSettings> CODEC;
/*     */   
/*     */   static {
/* 240 */     CODEC = (Codec<RandomSettings>)((BuilderCodec.Builder)((BuilderCodec.Builder)((BuilderCodec.Builder)((BuilderCodec.Builder)((BuilderCodec.Builder)BuilderCodec.builder(RandomSettings.class, RandomSettings::new).append(new KeyedCodec("MinVolume", (Codec)Codec.FLOAT), (soundEventLayer, f) -> soundEventLayer.minVolume = AudioUtil.decibelsToLinearGain(f.floatValue()), soundEventLayer -> Float.valueOf(AudioUtil.linearGainToDecibels(soundEventLayer.minVolume))).addValidator(Validators.range(Float.valueOf(-100.0F), Float.valueOf(0.0F))).documentation("Minimum additional random volume offset in decibels.").add()).append(new KeyedCodec("MaxVolume", (Codec)Codec.FLOAT), (soundEventLayer, f) -> soundEventLayer.maxVolume = AudioUtil.decibelsToLinearGain(f.floatValue()), soundEventLayer -> Float.valueOf(AudioUtil.linearGainToDecibels(soundEventLayer.maxVolume))).addValidator(Validators.range(Float.valueOf(0.0F), Float.valueOf(10.0F))).documentation("Maximum additional random volume offset in decibels.").add()).append(new KeyedCodec("MinPitch", (Codec)Codec.FLOAT), (soundEventLayer, f) -> soundEventLayer.minPitch = AudioUtil.semitonesToLinearPitch(f.floatValue()), soundEventLayer -> Float.valueOf(AudioUtil.linearPitchToSemitones(soundEventLayer.minPitch))).addValidator(Validators.range(Float.valueOf(-12.0F), Float.valueOf(0.0F))).documentation("Minimum additional random pitch offset in semitones.").add()).append(new KeyedCodec("MaxPitch", (Codec)Codec.FLOAT), (soundEventLayer, f) -> soundEventLayer.maxPitch = AudioUtil.semitonesToLinearPitch(f.floatValue()), soundEventLayer -> Float.valueOf(AudioUtil.linearPitchToSemitones(soundEventLayer.maxPitch))).addValidator(Validators.range(Float.valueOf(0.0F), Float.valueOf(12.0F))).documentation("Maximum additional random pitch offset in semitones.").add()).append(new KeyedCodec("MaxStartOffset", (Codec)Codec.FLOAT), (soundEventLayer, f) -> soundEventLayer.maxStartOffset = f.floatValue(), soundEventLayer -> Float.valueOf(soundEventLayer.maxStartOffset)).addValidator(Validators.range(Float.valueOf(0.0F), Float.valueOf(Float.MAX_VALUE))).documentation("Maximum amount by which to offset the start of this sound event (e.g. start up to x seconds into the sound). This should only really be used for looping sounds to prevent phasing issues.").add()).build();
/*     */   }
/* 242 */   public static final RandomSettings DEFAULT = new RandomSettings();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 249 */   protected transient float minVolume = 1.0F;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 256 */   protected transient float maxVolume = 1.0F;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 263 */   protected transient float minPitch = 1.0F;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 270 */   protected transient float maxPitch = 1.0F;
/*     */   
/*     */   protected float maxStartOffset;
/*     */   
/*     */   public float getMinVolume() {
/* 275 */     return this.minVolume;
/*     */   }
/*     */   
/*     */   public float getMaxVolume() {
/* 279 */     return this.maxVolume;
/*     */   }
/*     */   
/*     */   public float getMinPitch() {
/* 283 */     return this.minPitch;
/*     */   }
/*     */   
/*     */   public float getMaxPitch() {
/* 287 */     return this.maxPitch;
/*     */   }
/*     */   
/*     */   public float getMaxStartOffset() {
/* 291 */     return this.maxStartOffset;
/*     */   }
/*     */ 
/*     */   
/*     */   @Nonnull
/*     */   public String toString() {
/* 297 */     return "RandomSettings{, minVolume=" + this.minVolume + ", maxVolume=" + this.maxVolume + ", minPitch=" + this.minPitch + ", maxPitch=" + this.maxPitch + ", maxStartOffset=" + this.maxStartOffset + "}";
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\server\core\asset\type\soundevent\config\SoundEventLayer$RandomSettings.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */