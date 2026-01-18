/*    */ package com.hypixel.hytale.server.core.asset.type.ambiencefx.config;
/*    */ 
/*    */ import com.hypixel.hytale.codec.Codec;
/*    */ import com.hypixel.hytale.codec.KeyedCodec;
/*    */ import com.hypixel.hytale.codec.builder.BuilderCodec;
/*    */ import com.hypixel.hytale.codec.schema.metadata.Metadata;
/*    */ import com.hypixel.hytale.codec.schema.metadata.ui.UIEditor;
/*    */ import com.hypixel.hytale.codec.validation.Validator;
/*    */ import com.hypixel.hytale.codec.validation.Validators;
/*    */ import com.hypixel.hytale.codec.validation.validator.ArrayValidator;
/*    */ import com.hypixel.hytale.common.util.AudioUtil;
/*    */ import com.hypixel.hytale.protocol.AmbienceFXMusic;
/*    */ import com.hypixel.hytale.server.core.asset.common.CommonAssetValidator;
/*    */ import com.hypixel.hytale.server.core.io.NetworkSerializable;
/*    */ import java.util.Arrays;
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
/*    */ public class AmbienceFXMusic
/*    */   implements NetworkSerializable<AmbienceFXMusic>
/*    */ {
/*    */   public static final BuilderCodec<AmbienceFXMusic> CODEC;
/*    */   protected String[] tracks;
/*    */   
/*    */   static {
/* 36 */     CODEC = ((BuilderCodec.Builder)((BuilderCodec.Builder)((BuilderCodec.Builder)BuilderCodec.builder(AmbienceFXMusic.class, AmbienceFXMusic::new).appendInherited(new KeyedCodec("Tracks", (Codec)Codec.STRING_ARRAY), (ambienceFXMusic, strings) -> ambienceFXMusic.tracks = strings, ambienceFXMusic -> ambienceFXMusic.tracks, (ambienceFXMusic, parent) -> ambienceFXMusic.tracks = parent.tracks).addValidator(Validators.nonEmptyArray()).addValidator((Validator)new ArrayValidator((Validator)CommonAssetValidator.MUSIC)).add()).appendInherited(new KeyedCodec("Volume", (Codec)Codec.FLOAT), (ambienceFXMusic, f) -> ambienceFXMusic.decibels = f.floatValue(), ambienceFXMusic -> Float.valueOf(ambienceFXMusic.decibels), (ambienceFXMusic, parent) -> ambienceFXMusic.decibels = parent.decibels).metadata((Metadata)new UIEditor((UIEditor.EditorComponent)new UIEditor.FormattedNumber(null, " dB", null))).addValidator(Validators.range(Float.valueOf(-100.0F), Float.valueOf(10.0F))).add()).afterDecode(AmbienceFXMusic::processConfig)).build();
/*    */   }
/*    */   
/* 39 */   protected float decibels = 0.0F;
/* 40 */   protected transient float volume = 1.0F;
/*    */   
/*    */   public AmbienceFXMusic(String[] tracks, float decibels) {
/* 43 */     this.tracks = tracks;
/* 44 */     this.decibels = decibels;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   @Nonnull
/*    */   public AmbienceFXMusic toPacket() {
/* 53 */     AmbienceFXMusic packet = new AmbienceFXMusic();
/* 54 */     if (this.tracks != null && this.tracks.length > 0) {
/* 55 */       packet.tracks = this.tracks;
/*    */     }
/* 57 */     packet.volume = this.volume;
/* 58 */     return packet;
/*    */   }
/*    */   
/*    */   public String[] getTracks() {
/* 62 */     return this.tracks;
/*    */   }
/*    */   
/*    */   public float getDecibels() {
/* 66 */     return this.decibels;
/*    */   }
/*    */   
/*    */   public float getVolume() {
/* 70 */     return this.volume;
/*    */   }
/*    */   
/*    */   protected void processConfig() {
/* 74 */     this.volume = AudioUtil.decibelsToLinearGain(this.decibels);
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   @Nonnull
/*    */   public String toString() {
/* 81 */     return "AmbienceFXMusic{tracks=" + Arrays.toString((Object[])this.tracks) + ", decibels=" + this.decibels + ", volume=" + this.volume + "}";
/*    */   }
/*    */   
/*    */   protected AmbienceFXMusic() {}
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\server\core\asset\type\ambiencefx\config\AmbienceFXMusic.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */