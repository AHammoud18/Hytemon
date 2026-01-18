/*     */ package com.hypixel.hytale.server.core.asset.type.soundevent.config;
/*     */ 
/*     */ import com.hypixel.hytale.codec.Codec;
/*     */ import com.hypixel.hytale.codec.KeyedCodec;
/*     */ import com.hypixel.hytale.codec.builder.BuilderCodec;
/*     */ import com.hypixel.hytale.codec.schema.metadata.Metadata;
/*     */ import com.hypixel.hytale.codec.schema.metadata.ui.UIEditor;
/*     */ import com.hypixel.hytale.codec.validation.Validator;
/*     */ import com.hypixel.hytale.codec.validation.Validators;
/*     */ import com.hypixel.hytale.codec.validation.validator.ArrayValidator;
/*     */ import com.hypixel.hytale.common.util.AudioUtil;
/*     */ import com.hypixel.hytale.protocol.SoundEventLayer;
/*     */ import com.hypixel.hytale.protocol.SoundEventLayerRandomSettings;
/*     */ import com.hypixel.hytale.server.core.asset.common.CommonAssetValidator;
/*     */ import com.hypixel.hytale.server.core.asset.common.OggVorbisInfoCache;
/*     */ import com.hypixel.hytale.server.core.io.NetworkSerializable;
/*     */ import java.util.Arrays;
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
/*     */ public class SoundEventLayer
/*     */   implements NetworkSerializable<SoundEventLayer>
/*     */ {
/*     */   public static final Codec<SoundEventLayer> CODEC;
/*     */   
/*     */   static {
/*  89 */     CODEC = (Codec<SoundEventLayer>)((BuilderCodec.Builder)((BuilderCodec.Builder)((BuilderCodec.Builder)((BuilderCodec.Builder)((BuilderCodec.Builder)((BuilderCodec.Builder)((BuilderCodec.Builder)((BuilderCodec.Builder)((BuilderCodec.Builder)BuilderCodec.builder(SoundEventLayer.class, SoundEventLayer::new).append(new KeyedCodec("Volume", (Codec)Codec.FLOAT), (soundEventLayer, f) -> soundEventLayer.volume = AudioUtil.decibelsToLinearGain(f.floatValue()), soundEventLayer -> Float.valueOf(AudioUtil.linearGainToDecibels(soundEventLayer.volume))).metadata((Metadata)new UIEditor((UIEditor.EditorComponent)new UIEditor.FormattedNumber(null, " dB", null))).addValidator(Validators.range(Float.valueOf(-100.0F), Float.valueOf(10.0F))).documentation("Volume offset for this layer in decibels.").add()).append(new KeyedCodec("StartDelay", (Codec)Codec.FLOAT), (soundEventLayer, f) -> soundEventLayer.startDelay = f.floatValue(), soundEventLayer -> Float.valueOf(soundEventLayer.startDelay)).documentation("A delay in seconds from when the sound event starts after which this layer should begin.").add()).append(new KeyedCodec("Looping", (Codec)Codec.BOOLEAN), (soundEventLayer, b) -> soundEventLayer.looping = b.booleanValue(), soundEventLayer -> Boolean.valueOf(soundEventLayer.looping)).documentation("Whether this layer loops.").add()).append(new KeyedCodec("Probability", (Codec)Codec.INTEGER), (soundEventLayer, i) -> soundEventLayer.probability = i.intValue(), soundEventLayer -> Integer.valueOf(soundEventLayer.probability)).documentation("The probability of this layer being played when the sound event is triggered in percentage.").add()).append(new KeyedCodec("ProbabilityRerollDelay", (Codec)Codec.FLOAT), (soundEventLayer, f) -> soundEventLayer.probabilityRerollDelay = f.floatValue(), soundEventLayer -> Float.valueOf(soundEventLayer.probabilityRerollDelay)).documentation("A delay in seconds before the probability of this layer playing can be rerolled to see if it will now play (or not play) again.").add()).append(new KeyedCodec("RandomSettings", RandomSettings.CODEC), (soundEventLayer, o) -> soundEventLayer.randomSettings = o, soundEventLayer -> soundEventLayer.randomSettings).documentation("Randomization settings for parameters of this layer.").add()).append(new KeyedCodec("Files", (Codec)Codec.STRING_ARRAY), (soundEventLayer, s) -> soundEventLayer.files = s, soundEventLayer -> soundEventLayer.files).addValidator(Validators.nonEmptyArray()).addValidator((Validator)new ArrayValidator((Validator)CommonAssetValidator.SOUNDS)).documentation("The list of possible sound files for this layer. One will be chosen at random.").add()).append(new KeyedCodec("RoundRobinHistorySize", (Codec)Codec.INTEGER), (soundEventLayer, i) -> soundEventLayer.roundRobinHistorySize = i.intValue(), soundEventLayer -> Integer.valueOf(soundEventLayer.roundRobinHistorySize)).addValidator(Validators.range(Integer.valueOf(0), Integer.valueOf(32))).documentation("The same sound file will not repeat within this many plays. 0 disables round-robin behavior.").add()).afterDecode(layer -> { if (layer.files == null) return;  for (String file : layer.files) { OggVorbisInfoCache.OggVorbisInfo info = OggVorbisInfoCache.getNow(file); if (info != null && info.channels > layer.highestNumberOfChannels) layer.highestNumberOfChannels = info.channels;  }  })).build();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  96 */   protected transient float volume = 1.0F;
/*     */   
/*  98 */   protected float startDelay = 0.0F;
/*     */   protected boolean looping = false;
/* 100 */   protected int probability = 100;
/* 101 */   protected float probabilityRerollDelay = 1.0F;
/*     */   
/* 103 */   protected RandomSettings randomSettings = RandomSettings.DEFAULT;
/*     */   
/*     */   protected String[] files;
/*     */   
/* 107 */   protected int roundRobinHistorySize = 0;
/*     */   
/* 109 */   protected transient int highestNumberOfChannels = 0;
/*     */   
/*     */   public SoundEventLayer(float volume, float startDelay, boolean looping, int probability, float probabilityRerollDelay, RandomSettings randomSettings, String[] files, int roundRobinHistorySize) {
/* 112 */     this.volume = volume;
/* 113 */     this.startDelay = startDelay;
/* 114 */     this.looping = looping;
/* 115 */     this.probability = probability;
/* 116 */     this.probabilityRerollDelay = probabilityRerollDelay;
/* 117 */     this.randomSettings = randomSettings;
/* 118 */     this.files = files;
/* 119 */     this.roundRobinHistorySize = roundRobinHistorySize;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public float getVolume() {
/* 126 */     return this.volume;
/*     */   }
/*     */   
/*     */   public float getStartDelay() {
/* 130 */     return this.startDelay;
/*     */   }
/*     */   
/*     */   public boolean isLooping() {
/* 134 */     return this.looping;
/*     */   }
/*     */   
/*     */   public int getProbability() {
/* 138 */     return this.probability;
/*     */   }
/*     */   
/*     */   public float getProbabilityRerollDelay() {
/* 142 */     return this.probabilityRerollDelay;
/*     */   }
/*     */   
/*     */   public RandomSettings getRandomSettings() {
/* 146 */     return this.randomSettings;
/*     */   }
/*     */   
/*     */   public String[] getFiles() {
/* 150 */     return this.files;
/*     */   }
/*     */   
/*     */   public int getRoundRobinHistorySize() {
/* 154 */     return this.roundRobinHistorySize;
/*     */   }
/*     */   
/*     */   public int getHighestNumberOfChannels() {
/* 158 */     return this.highestNumberOfChannels;
/*     */   }
/*     */ 
/*     */   
/*     */   @Nonnull
/*     */   public SoundEventLayer toPacket() {
/* 164 */     SoundEventLayer packet = new SoundEventLayer();
/*     */     
/* 166 */     packet.volume = this.volume;
/* 167 */     packet.startDelay = this.startDelay;
/* 168 */     packet.looping = this.looping;
/* 169 */     packet.probability = this.probability;
/* 170 */     packet.probabilityRerollDelay = this.probabilityRerollDelay;
/*     */     
/* 172 */     packet.randomSettings = new SoundEventLayerRandomSettings();
/* 173 */     packet.randomSettings.minVolume = this.randomSettings.minVolume;
/* 174 */     packet.randomSettings.maxVolume = this.randomSettings.maxVolume;
/* 175 */     packet.randomSettings.minPitch = this.randomSettings.minPitch;
/* 176 */     packet.randomSettings.maxPitch = this.randomSettings.maxPitch;
/* 177 */     packet.randomSettings.maxStartOffset = this.randomSettings.maxStartOffset;
/*     */     
/* 179 */     packet.files = this.files;
/* 180 */     packet.roundRobinHistorySize = this.roundRobinHistorySize;
/*     */     
/* 182 */     return packet;
/*     */   }
/*     */ 
/*     */   
/*     */   @Nonnull
/*     */   public String toString() {
/* 188 */     return "SoundEventLayer{, volume=" + this.volume + ", startDelay=" + this.startDelay + ", looping=" + this.looping + ", probability=" + this.probability + ", probabilityRerollDelay=" + this.probabilityRerollDelay + ", randomSettings=" + String.valueOf(this.randomSettings) + ", files=" + 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 195 */       Arrays.toString((Object[])this.files) + ", roundRobinHistorySize=" + this.roundRobinHistorySize + ", highestNumberOfChannels=" + this.highestNumberOfChannels + "}";
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
/*     */   protected SoundEventLayer() {}
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static class RandomSettings
/*     */   {
/*     */     public static final Codec<RandomSettings> CODEC;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     static {
/* 240 */       CODEC = (Codec<RandomSettings>)((BuilderCodec.Builder)((BuilderCodec.Builder)((BuilderCodec.Builder)((BuilderCodec.Builder)((BuilderCodec.Builder)BuilderCodec.builder(RandomSettings.class, RandomSettings::new).append(new KeyedCodec("MinVolume", (Codec)Codec.FLOAT), (soundEventLayer, f) -> soundEventLayer.minVolume = AudioUtil.decibelsToLinearGain(f.floatValue()), soundEventLayer -> Float.valueOf(AudioUtil.linearGainToDecibels(soundEventLayer.minVolume))).addValidator(Validators.range(Float.valueOf(-100.0F), Float.valueOf(0.0F))).documentation("Minimum additional random volume offset in decibels.").add()).append(new KeyedCodec("MaxVolume", (Codec)Codec.FLOAT), (soundEventLayer, f) -> soundEventLayer.maxVolume = AudioUtil.decibelsToLinearGain(f.floatValue()), soundEventLayer -> Float.valueOf(AudioUtil.linearGainToDecibels(soundEventLayer.maxVolume))).addValidator(Validators.range(Float.valueOf(0.0F), Float.valueOf(10.0F))).documentation("Maximum additional random volume offset in decibels.").add()).append(new KeyedCodec("MinPitch", (Codec)Codec.FLOAT), (soundEventLayer, f) -> soundEventLayer.minPitch = AudioUtil.semitonesToLinearPitch(f.floatValue()), soundEventLayer -> Float.valueOf(AudioUtil.linearPitchToSemitones(soundEventLayer.minPitch))).addValidator(Validators.range(Float.valueOf(-12.0F), Float.valueOf(0.0F))).documentation("Minimum additional random pitch offset in semitones.").add()).append(new KeyedCodec("MaxPitch", (Codec)Codec.FLOAT), (soundEventLayer, f) -> soundEventLayer.maxPitch = AudioUtil.semitonesToLinearPitch(f.floatValue()), soundEventLayer -> Float.valueOf(AudioUtil.linearPitchToSemitones(soundEventLayer.maxPitch))).addValidator(Validators.range(Float.valueOf(0.0F), Float.valueOf(12.0F))).documentation("Maximum additional random pitch offset in semitones.").add()).append(new KeyedCodec("MaxStartOffset", (Codec)Codec.FLOAT), (soundEventLayer, f) -> soundEventLayer.maxStartOffset = f.floatValue(), soundEventLayer -> Float.valueOf(soundEventLayer.maxStartOffset)).addValidator(Validators.range(Float.valueOf(0.0F), Float.valueOf(Float.MAX_VALUE))).documentation("Maximum amount by which to offset the start of this sound event (e.g. start up to x seconds into the sound). This should only really be used for looping sounds to prevent phasing issues.").add()).build();
/*     */     }
/* 242 */     public static final RandomSettings DEFAULT = new RandomSettings();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 249 */     protected transient float minVolume = 1.0F;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 256 */     protected transient float maxVolume = 1.0F;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 263 */     protected transient float minPitch = 1.0F;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 270 */     protected transient float maxPitch = 1.0F;
/*     */     
/*     */     protected float maxStartOffset;
/*     */     
/*     */     public float getMinVolume() {
/* 275 */       return this.minVolume;
/*     */     }
/*     */     
/*     */     public float getMaxVolume() {
/* 279 */       return this.maxVolume;
/*     */     }
/*     */     
/*     */     public float getMinPitch() {
/* 283 */       return this.minPitch;
/*     */     }
/*     */     
/*     */     public float getMaxPitch() {
/* 287 */       return this.maxPitch;
/*     */     }
/*     */     
/*     */     public float getMaxStartOffset() {
/* 291 */       return this.maxStartOffset;
/*     */     }
/*     */ 
/*     */     
/*     */     @Nonnull
/*     */     public String toString() {
/* 297 */       return "RandomSettings{, minVolume=" + this.minVolume + ", maxVolume=" + this.maxVolume + ", minPitch=" + this.minPitch + ", maxPitch=" + this.maxPitch + ", maxStartOffset=" + this.maxStartOffset + "}";
/*     */     }
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\server\core\asset\type\soundevent\config\SoundEventLayer.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */