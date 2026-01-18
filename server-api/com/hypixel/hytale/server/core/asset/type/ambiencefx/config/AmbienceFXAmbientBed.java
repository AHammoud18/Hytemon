/*    */ package com.hypixel.hytale.server.core.asset.type.ambiencefx.config;
/*    */ 
/*    */ import com.hypixel.hytale.codec.Codec;
/*    */ import com.hypixel.hytale.codec.KeyedCodec;
/*    */ import com.hypixel.hytale.codec.builder.BuilderCodec;
/*    */ import com.hypixel.hytale.codec.codecs.EnumCodec;
/*    */ import com.hypixel.hytale.codec.schema.metadata.Metadata;
/*    */ import com.hypixel.hytale.codec.schema.metadata.ui.UIEditor;
/*    */ import com.hypixel.hytale.codec.validation.Validator;
/*    */ import com.hypixel.hytale.codec.validation.Validators;
/*    */ import com.hypixel.hytale.common.util.AudioUtil;
/*    */ import com.hypixel.hytale.protocol.AmbienceFXAmbientBed;
/*    */ import com.hypixel.hytale.protocol.AmbienceTransitionSpeed;
/*    */ import com.hypixel.hytale.server.core.asset.common.CommonAssetValidator;
/*    */ import com.hypixel.hytale.server.core.asset.common.SoundFileValidators;
/*    */ import com.hypixel.hytale.server.core.io.NetworkSerializable;
/*    */ import java.util.function.Supplier;
/*    */ import javax.annotation.Nonnull;
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
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class AmbienceFXAmbientBed
/*    */   implements NetworkSerializable<AmbienceFXAmbientBed>
/*    */ {
/*    */   public static final BuilderCodec<AmbienceFXAmbientBed> CODEC;
/*    */   protected String track;
/*    */   
/*    */   static {
/* 44 */     CODEC = ((BuilderCodec.Builder)((BuilderCodec.Builder)((BuilderCodec.Builder)((BuilderCodec.Builder)BuilderCodec.builder(AmbienceFXAmbientBed.class, AmbienceFXAmbientBed::new).appendInherited(new KeyedCodec("Track", (Codec)Codec.STRING), (ambienceFXAmbientBed, s) -> ambienceFXAmbientBed.track = s, ambienceFXAmbientBed -> ambienceFXAmbientBed.track, (ambienceFXAmbientBed, parent) -> ambienceFXAmbientBed.track = parent.track).addValidator(Validators.nonNull()).addValidator((Validator)CommonAssetValidator.SOUNDS).addValidator((Validator)SoundFileValidators.STEREO).add()).appendInherited(new KeyedCodec("Volume", (Codec)Codec.FLOAT), (ambienceFXAmbientBed, f) -> ambienceFXAmbientBed.decibels = f.floatValue(), ambienceFXAmbientBed -> Float.valueOf(ambienceFXAmbientBed.decibels), (ambienceFXAmbientBed, parent) -> ambienceFXAmbientBed.decibels = parent.decibels).metadata((Metadata)new UIEditor((UIEditor.EditorComponent)new UIEditor.FormattedNumber(null, " dB", null))).addValidator(Validators.range(Float.valueOf(-100.0F), Float.valueOf(10.0F))).add()).appendInherited(new KeyedCodec("TransitionSpeed", (Codec)new EnumCodec(AmbienceTransitionSpeed.class)), (ambienceFXAmbientBed, e) -> ambienceFXAmbientBed.transitionSpeed = e, ambienceFXAmbientBed -> ambienceFXAmbientBed.transitionSpeed, (ambienceFXAmbientBed, parent) -> ambienceFXAmbientBed.transitionSpeed = parent.transitionSpeed).documentation("How quickly to transition to this ambient bed and fade out any ambient beds that are stopping. For fading out stopping ambient beds, faster transitions take priority. Fade-ins are already fast by default.").add()).afterDecode(AmbienceFXAmbientBed::processConfig)).build();
/*    */   }
/*    */   
/* 47 */   protected float decibels = 0.0F;
/* 48 */   protected transient float volume = 1.0F;
/* 49 */   protected AmbienceTransitionSpeed transitionSpeed = AmbienceTransitionSpeed.Default;
/*    */   
/*    */   public AmbienceFXAmbientBed(String track, float decibels, AmbienceTransitionSpeed transitionSpeed) {
/* 52 */     this.track = track;
/* 53 */     this.decibels = decibels;
/* 54 */     this.transitionSpeed = transitionSpeed;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   @Nonnull
/*    */   public AmbienceFXAmbientBed toPacket() {
/* 63 */     AmbienceFXAmbientBed packet = new AmbienceFXAmbientBed();
/* 64 */     packet.track = this.track;
/* 65 */     packet.volume = this.volume;
/* 66 */     packet.transitionSpeed = this.transitionSpeed;
/* 67 */     return packet;
/*    */   }
/*    */   
/*    */   public String getTrack() {
/* 71 */     return this.track;
/*    */   }
/*    */   
/*    */   public float getDecibels() {
/* 75 */     return this.decibels;
/*    */   }
/*    */   
/*    */   public float getVolume() {
/* 79 */     return this.volume;
/*    */   }
/*    */   
/*    */   public AmbienceTransitionSpeed getTransitionSpeed() {
/* 83 */     return this.transitionSpeed;
/*    */   }
/*    */   
/*    */   protected void processConfig() {
/* 87 */     this.volume = AudioUtil.decibelsToLinearGain(this.decibels);
/*    */   }
/*    */ 
/*    */   
/*    */   @Nonnull
/*    */   public String toString() {
/* 93 */     return "AmbienceFXAmbientBed{track='" + this.track + "', decibels=" + this.decibels + ", volume=" + this.volume + ", transitionSpeed=" + String.valueOf(this.transitionSpeed) + "}";
/*    */   }
/*    */   
/*    */   protected AmbienceFXAmbientBed() {}
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\server\core\asset\type\ambiencefx\config\AmbienceFXAmbientBed.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */