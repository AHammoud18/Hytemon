/*     */ package com.hypixel.hytale.server.core.asset.type.ambiencefx.config;
/*     */ 
/*     */ import com.hypixel.hytale.codec.Codec;
/*     */ import com.hypixel.hytale.codec.KeyedCodec;
/*     */ import com.hypixel.hytale.codec.builder.BuilderCodec;
/*     */ import com.hypixel.hytale.codec.codecs.EnumCodec;
/*     */ import com.hypixel.hytale.codec.validation.Validators;
/*     */ import com.hypixel.hytale.protocol.AmbienceFXAltitude;
/*     */ import com.hypixel.hytale.protocol.AmbienceFXSound;
/*     */ import com.hypixel.hytale.protocol.AmbienceFXSoundPlay3D;
/*     */ import com.hypixel.hytale.protocol.Range;
/*     */ import com.hypixel.hytale.protocol.Rangef;
/*     */ import com.hypixel.hytale.server.core.asset.type.blocksound.config.BlockSoundSet;
/*     */ import com.hypixel.hytale.server.core.asset.type.soundevent.config.SoundEvent;
/*     */ import com.hypixel.hytale.server.core.codec.ProtocolCodecs;
/*     */ import com.hypixel.hytale.server.core.io.NetworkSerializable;
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
/*     */ public class AmbienceFXSound
/*     */   implements NetworkSerializable<AmbienceFXSound>
/*     */ {
/*     */   public static final BuilderCodec<AmbienceFXSound> CODEC;
/*     */   
/*     */   static {
/*  52 */     CODEC = ((BuilderCodec.Builder)((BuilderCodec.Builder)((BuilderCodec.Builder)((BuilderCodec.Builder)((BuilderCodec.Builder)((BuilderCodec.Builder)((BuilderCodec.Builder)BuilderCodec.builder(AmbienceFXSound.class, AmbienceFXSound::new).append(new KeyedCodec("SoundEventId", (Codec)Codec.STRING), (ambienceFXSound, o) -> ambienceFXSound.soundEventId = o, ambienceFXSound -> ambienceFXSound.soundEventId).addValidator(Validators.nonNull()).addValidator(SoundEvent.VALIDATOR_CACHE.getValidator()).add()).addField(new KeyedCodec("Play3D", (Codec)new EnumCodec(AmbienceFXSoundPlay3D.class)), (ambienceFXSound, o) -> ambienceFXSound.play3D = o, ambienceFXSound -> ambienceFXSound.play3D)).append(new KeyedCodec("BlockSoundSetId", (Codec)Codec.STRING), (ambienceFXSound, o) -> ambienceFXSound.blockSoundSetId = o, ambienceFXSound -> ambienceFXSound.blockSoundSetId).addValidator(BlockSoundSet.VALIDATOR_CACHE.getValidator()).add()).addField(new KeyedCodec("Altitude", (Codec)new EnumCodec(AmbienceFXAltitude.class)), (ambienceFXSound, o) -> ambienceFXSound.altitude = o, ambienceFXSound -> ambienceFXSound.altitude)).addField(new KeyedCodec("Frequency", (Codec)ProtocolCodecs.RANGEF), (ambienceFXSound, o) -> ambienceFXSound.frequency = o, ambienceFXSound -> ambienceFXSound.frequency)).addField(new KeyedCodec("Radius", (Codec)ProtocolCodecs.RANGE), (ambienceFXSound, o) -> ambienceFXSound.radius = o, ambienceFXSound -> ambienceFXSound.radius)).afterDecode(AmbienceFXSound::processConfig)).build();
/*     */   }
/*  54 */   public static final Rangef DEFAULT_FREQUENCY = new Rangef(1.0F, 10.0F);
/*  55 */   public static final Range DEFAULT_RADIUS = new Range(0, 24);
/*     */   
/*     */   protected String soundEventId;
/*     */   protected transient int soundEventIndex;
/*  59 */   protected AmbienceFXSoundPlay3D play3D = AmbienceFXSoundPlay3D.Random;
/*     */   protected String blockSoundSetId;
/*     */   protected transient int blockSoundSetIndex;
/*  62 */   protected AmbienceFXAltitude altitude = AmbienceFXAltitude.Normal;
/*  63 */   protected Rangef frequency = DEFAULT_FREQUENCY;
/*  64 */   protected Range radius = DEFAULT_RADIUS;
/*     */   
/*     */   public AmbienceFXSound(String soundEventId, AmbienceFXSoundPlay3D play3D, String blockSoundSetId, AmbienceFXAltitude altitude, Rangef frequency, Range radius) {
/*  67 */     this.soundEventId = soundEventId;
/*  68 */     this.play3D = play3D;
/*  69 */     this.blockSoundSetId = blockSoundSetId;
/*  70 */     this.altitude = altitude;
/*  71 */     this.frequency = frequency;
/*  72 */     this.radius = radius;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Nonnull
/*     */   public AmbienceFXSound toPacket() {
/*  81 */     AmbienceFXSound packet = new AmbienceFXSound();
/*  82 */     packet.soundEventIndex = this.soundEventIndex;
/*  83 */     packet.play3D = this.play3D;
/*  84 */     packet.blockSoundSetIndex = this.blockSoundSetIndex;
/*  85 */     packet.altitude = this.altitude;
/*  86 */     packet.frequency = this.frequency;
/*  87 */     packet.radius = this.radius;
/*  88 */     return packet;
/*     */   }
/*     */   
/*     */   public String getSoundEventId() {
/*  92 */     return this.soundEventId;
/*     */   }
/*     */   
/*     */   public int getSoundEventIndex() {
/*  96 */     return this.soundEventIndex;
/*     */   }
/*     */   
/*     */   public AmbienceFXSoundPlay3D getPlay3D() {
/* 100 */     return this.play3D;
/*     */   }
/*     */   
/*     */   public String getBlockSoundSetId() {
/* 104 */     return this.blockSoundSetId;
/*     */   }
/*     */   
/*     */   public AmbienceFXAltitude getAltitude() {
/* 108 */     return this.altitude;
/*     */   }
/*     */   
/*     */   public Rangef getFrequency() {
/* 112 */     return this.frequency;
/*     */   }
/*     */   
/*     */   public Range getRadius() {
/* 116 */     return this.radius;
/*     */   }
/*     */   
/*     */   protected void processConfig() {
/* 120 */     if (this.soundEventId != null) {
/* 121 */       this.soundEventIndex = SoundEvent.getAssetMap().getIndex(this.soundEventId);
/*     */     }
/*     */     
/* 124 */     if (this.blockSoundSetId != null) {
/* 125 */       this.blockSoundSetIndex = BlockSoundSet.getAssetMap().getIndex(this.blockSoundSetId);
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   @Nonnull
/*     */   public String toString() {
/* 132 */     return "AmbienceFXSound{soundEventId='" + this.soundEventId + "', soundEventIndex=" + this.soundEventIndex + ", play3D=" + String.valueOf(this.play3D) + ", blockSoundSetId='" + this.blockSoundSetId + "', blockSoundSetIndex=" + this.blockSoundSetIndex + ", altitude=" + String.valueOf(this.altitude) + ", frequency=" + String.valueOf(this.frequency) + ", radius=" + String.valueOf(this.radius) + "}";
/*     */   }
/*     */   
/*     */   protected AmbienceFXSound() {}
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\server\core\asset\type\ambiencefx\config\AmbienceFXSound.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */