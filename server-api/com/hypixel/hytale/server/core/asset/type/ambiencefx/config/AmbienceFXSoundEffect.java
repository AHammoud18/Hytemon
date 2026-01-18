/*    */ package com.hypixel.hytale.server.core.asset.type.ambiencefx.config;
/*    */ 
/*    */ import com.hypixel.hytale.codec.Codec;
/*    */ import com.hypixel.hytale.codec.KeyedCodec;
/*    */ import com.hypixel.hytale.codec.builder.BuilderCodec;
/*    */ import com.hypixel.hytale.protocol.AmbienceFXSoundEffect;
/*    */ import com.hypixel.hytale.server.core.asset.type.equalizereffect.config.EqualizerEffect;
/*    */ import com.hypixel.hytale.server.core.asset.type.reverbeffect.config.ReverbEffect;
/*    */ import com.hypixel.hytale.server.core.io.NetworkSerializable;
/*    */ import java.util.function.Supplier;
/*    */ import javax.annotation.Nonnull;
/*    */ import javax.annotation.Nullable;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class AmbienceFXSoundEffect
/*    */   implements NetworkSerializable<AmbienceFXSoundEffect>
/*    */ {
/*    */   public static final BuilderCodec<AmbienceFXSoundEffect> CODEC;
/*    */   @Nullable
/*    */   protected String reverbEffectId;
/*    */   
/*    */   static {
/* 34 */     CODEC = ((BuilderCodec.Builder)((BuilderCodec.Builder)((BuilderCodec.Builder)((BuilderCodec.Builder)BuilderCodec.builder(AmbienceFXSoundEffect.class, AmbienceFXSoundEffect::new).appendInherited(new KeyedCodec("ReverbEffectId", (Codec)Codec.STRING), (ambienceFXSoundEffect, s) -> ambienceFXSoundEffect.reverbEffectId = s, ambienceFXSoundEffect -> ambienceFXSoundEffect.reverbEffectId, (ambienceFXSoundEffect, parent) -> ambienceFXSoundEffect.reverbEffectId = parent.reverbEffectId).addValidator(ReverbEffect.VALIDATOR_CACHE.getValidator()).add()).appendInherited(new KeyedCodec("EqualizerEffectId", (Codec)Codec.STRING), (ambienceFXSoundEffect, s) -> ambienceFXSoundEffect.equalizerEffectId = s, ambienceFXSoundEffect -> ambienceFXSoundEffect.equalizerEffectId, (ambienceFXSoundEffect, parent) -> ambienceFXSoundEffect.equalizerEffectId = parent.equalizerEffectId).addValidator(EqualizerEffect.VALIDATOR_CACHE.getValidator()).add()).appendInherited(new KeyedCodec("IsInstant", (Codec)Codec.BOOLEAN), (ambienceFXSoundEffect, b) -> ambienceFXSoundEffect.isInstant = b.booleanValue(), ambienceFXSoundEffect -> Boolean.valueOf(ambienceFXSoundEffect.isInstant), (ambienceFXSoundEffect, parent) -> ambienceFXSoundEffect.isInstant = parent.isInstant).add()).afterDecode(AmbienceFXSoundEffect::processConfig)).build();
/*    */   }
/*    */ 
/*    */   
/* 38 */   protected transient int reverbEffectIndex = 0;
/*    */   
/*    */   @Nullable
/*    */   protected String equalizerEffectId;
/* 42 */   protected transient int equalizerEffectIndex = 0;
/*    */   
/*    */   protected boolean isInstant = false;
/*    */   
/*    */   public AmbienceFXSoundEffect(@Nullable String reverbEffectId, @Nullable String equalizerEffectId, boolean isInstant) {
/* 47 */     this.reverbEffectId = reverbEffectId;
/* 48 */     this.equalizerEffectId = equalizerEffectId;
/* 49 */     this.isInstant = isInstant;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   protected void processConfig() {
/* 56 */     if (this.reverbEffectId != null) {
/* 57 */       this.reverbEffectIndex = ReverbEffect.getAssetMap().getIndex(this.reverbEffectId);
/*    */     }
/* 59 */     if (this.equalizerEffectId != null) {
/* 60 */       this.equalizerEffectIndex = EqualizerEffect.getAssetMap().getIndex(this.equalizerEffectId);
/*    */     }
/*    */   }
/*    */ 
/*    */   
/*    */   @Nonnull
/*    */   public AmbienceFXSoundEffect toPacket() {
/* 67 */     AmbienceFXSoundEffect packet = new AmbienceFXSoundEffect();
/* 68 */     packet.reverbEffectIndex = this.reverbEffectIndex;
/* 69 */     packet.equalizerEffectIndex = this.equalizerEffectIndex;
/* 70 */     packet.isInstant = this.isInstant;
/* 71 */     return packet;
/*    */   }
/*    */   
/*    */   @Nullable
/*    */   public String getReverbEffectId() {
/* 76 */     return this.reverbEffectId;
/*    */   }
/*    */   
/*    */   public int getReverbEffectIndex() {
/* 80 */     return this.reverbEffectIndex;
/*    */   }
/*    */   
/*    */   @Nullable
/*    */   public String getEqualizerEffectId() {
/* 85 */     return this.equalizerEffectId;
/*    */   }
/*    */   
/*    */   public int getEqualizerEffectIndex() {
/* 89 */     return this.equalizerEffectIndex;
/*    */   }
/*    */   
/*    */   public boolean isInstant() {
/* 93 */     return this.isInstant;
/*    */   }
/*    */ 
/*    */   
/*    */   @Nonnull
/*    */   public String toString() {
/* 99 */     return "AmbienceFXSoundEffect{reverbEffectId='" + this.reverbEffectId + "', equalizerEffectId='" + this.equalizerEffectId + "', isInstant=" + this.isInstant + "}";
/*    */   }
/*    */   
/*    */   protected AmbienceFXSoundEffect() {}
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\server\core\asset\type\ambiencefx\config\AmbienceFXSoundEffect.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */