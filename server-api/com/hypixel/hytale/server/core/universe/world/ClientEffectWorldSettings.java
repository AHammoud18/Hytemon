/*     */ package com.hypixel.hytale.server.core.universe.world;
/*     */ 
/*     */ import com.hypixel.hytale.codec.Codec;
/*     */ import com.hypixel.hytale.codec.KeyedCodec;
/*     */ import com.hypixel.hytale.codec.builder.BuilderCodec;
/*     */ import com.hypixel.hytale.protocol.packets.world.UpdatePostFxSettings;
/*     */ import com.hypixel.hytale.protocol.packets.world.UpdateSunSettings;
/*     */ import java.util.function.Supplier;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class ClientEffectWorldSettings
/*     */ {
/*     */   public static BuilderCodec<ClientEffectWorldSettings> CODEC;
/*     */   
/*     */   static {
/*  53 */     CODEC = ((BuilderCodec.Builder)((BuilderCodec.Builder)((BuilderCodec.Builder)((BuilderCodec.Builder)((BuilderCodec.Builder)((BuilderCodec.Builder)((BuilderCodec.Builder)BuilderCodec.builder(ClientEffectWorldSettings.class, ClientEffectWorldSettings::new).append(new KeyedCodec("SunHeightPercent", (Codec)Codec.FLOAT), (settings, o) -> settings.sunHeightPercent = o.floatValue(), settings -> Float.valueOf(settings.sunHeightPercent)).add()).append(new KeyedCodec("SunAngleDegrees", (Codec)Codec.FLOAT), (settings, o) -> settings.sunAngleRadians = (float)Math.toRadians(o.floatValue()), settings -> Float.valueOf((float)Math.toDegrees(settings.sunAngleRadians))).add()).append(new KeyedCodec("BloomIntensity", (Codec)Codec.FLOAT), (settings, o) -> settings.bloomIntensity = o.floatValue(), settings -> Float.valueOf(settings.bloomIntensity)).add()).append(new KeyedCodec("BloomPower", (Codec)Codec.FLOAT), (settings, o) -> settings.bloomPower = o.floatValue(), settings -> Float.valueOf(settings.bloomPower)).add()).append(new KeyedCodec("SunIntensity", (Codec)Codec.FLOAT), (settings, o) -> settings.sunIntensity = o.floatValue(), settings -> Float.valueOf(settings.sunIntensity)).add()).append(new KeyedCodec("SunshaftIntensity", (Codec)Codec.FLOAT), (settings, o) -> settings.sunshaftIntensity = o.floatValue(), settings -> Float.valueOf(settings.sunshaftIntensity)).add()).append(new KeyedCodec("SunshaftScaleFactor", (Codec)Codec.FLOAT), (settings, o) -> settings.sunshaftScaleFactor = o.floatValue(), settings -> Float.valueOf(settings.sunshaftScaleFactor)).add()).build();
/*     */   }
/*  55 */   private float sunHeightPercent = 100.0F;
/*  56 */   private float sunAngleRadians = 0.0F;
/*     */   
/*  58 */   private float bloomIntensity = 0.3F;
/*  59 */   private float bloomPower = 8.0F;
/*  60 */   private float sunIntensity = 0.25F;
/*  61 */   private float sunshaftIntensity = 0.3F;
/*  62 */   private float sunshaftScaleFactor = 4.0F;
/*     */   
/*     */   public float getSunHeightPercent() {
/*  65 */     return this.sunHeightPercent;
/*     */   }
/*     */   
/*     */   public void setSunHeightPercent(float sunHeightPercent) {
/*  69 */     this.sunHeightPercent = sunHeightPercent;
/*     */   }
/*     */   
/*     */   public float getSunAngleRadians() {
/*  73 */     return this.sunAngleRadians;
/*     */   }
/*     */   
/*     */   public void setSunAngleRadians(float sunAngleRadians) {
/*  77 */     this.sunAngleRadians = sunAngleRadians;
/*     */   }
/*     */   
/*     */   public float getBloomIntensity() {
/*  81 */     return this.bloomIntensity;
/*     */   }
/*     */   
/*     */   public void setBloomIntensity(float bloomIntensity) {
/*  85 */     this.bloomIntensity = bloomIntensity;
/*     */   }
/*     */   
/*     */   public float getBloomPower() {
/*  89 */     return this.bloomPower;
/*     */   }
/*     */   
/*     */   public void setBloomPower(float bloomPower) {
/*  93 */     this.bloomPower = bloomPower;
/*     */   }
/*     */   
/*     */   public float getSunIntensity() {
/*  97 */     return this.sunIntensity;
/*     */   }
/*     */   
/*     */   public void setSunIntensity(float sunIntensity) {
/* 101 */     this.sunIntensity = sunIntensity;
/*     */   }
/*     */   
/*     */   public float getSunshaftIntensity() {
/* 105 */     return this.sunshaftIntensity;
/*     */   }
/*     */   
/*     */   public void setSunshaftIntensity(float sunshaftIntensity) {
/* 109 */     this.sunshaftIntensity = sunshaftIntensity;
/*     */   }
/*     */   
/*     */   public float getSunshaftScaleFactor() {
/* 113 */     return this.sunshaftScaleFactor;
/*     */   }
/*     */   
/*     */   public void setSunshaftScaleFactor(float sunshaftScaleFactor) {
/* 117 */     this.sunshaftScaleFactor = sunshaftScaleFactor;
/*     */   }
/*     */   
/*     */   public UpdateSunSettings createSunSettingsPacket() {
/* 121 */     return new UpdateSunSettings(this.sunHeightPercent, this.sunAngleRadians);
/*     */   }
/*     */   
/*     */   public UpdatePostFxSettings createPostFxSettingsPacket() {
/* 125 */     return new UpdatePostFxSettings(this.bloomIntensity, this.bloomPower, this.sunshaftScaleFactor, this.sunIntensity, this.sunshaftIntensity);
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\server\cor\\universe\world\ClientEffectWorldSettings.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */