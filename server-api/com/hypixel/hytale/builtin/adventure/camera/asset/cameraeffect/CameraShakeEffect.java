/*     */ package com.hypixel.hytale.builtin.adventure.camera.asset.cameraeffect;
/*     */ 
/*     */ import com.hypixel.hytale.builtin.adventure.camera.asset.camerashake.CameraShake;
/*     */ import com.hypixel.hytale.codec.Codec;
/*     */ import com.hypixel.hytale.codec.KeyedCodec;
/*     */ import com.hypixel.hytale.codec.builder.BuilderCodec;
/*     */ import com.hypixel.hytale.protocol.AccumulationMode;
/*     */ import com.hypixel.hytale.server.core.asset.type.camera.CameraEffect;
/*     */ import java.util.function.Supplier;
/*     */ import javax.annotation.Nonnull;
/*     */ import javax.annotation.Nullable;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class CameraShakeEffect
/*     */   extends CameraEffect
/*     */ {
/*     */   @Nonnull
/*     */   public static final BuilderCodec<CameraShakeEffect> CODEC;
/*     */   @Nullable
/*     */   protected String cameraShakeId;
/*     */   
/*     */   static {
/*  43 */     CODEC = ((BuilderCodec.Builder)((BuilderCodec.Builder)((BuilderCodec.Builder)BuilderCodec.builder(CameraShakeEffect.class, CameraShakeEffect::new).appendInherited(new KeyedCodec("CameraShake", CameraShake.CHILD_ASSET_CODEC), (cameraShakeEffect, s) -> cameraShakeEffect.cameraShakeId = s, cameraShakeEffect -> cameraShakeEffect.cameraShakeId, (cameraShakeEffect, parent) -> cameraShakeEffect.cameraShakeId = parent.cameraShakeId).documentation("The type of camera shake to apply for this effect.").addValidator(CameraShake.VALIDATOR_CACHE.getValidator()).add()).appendInherited(new KeyedCodec("Intensity", (Codec)ShakeIntensity.CODEC), (cameraShakeEffect, s) -> cameraShakeEffect.intensity = s, cameraShakeEffect -> cameraShakeEffect.intensity, (cameraShakeEffect, parent) -> cameraShakeEffect.intensity = parent.intensity).documentation("Controls how intensity-context (such as damage) is interpreted as shake intensity.").add()).afterDecode(cameraShakeEffect -> { if (cameraShakeEffect.cameraShakeId != null) cameraShakeEffect.cameraShakeIndex = CameraShake.getAssetMap().getIndex(cameraShakeEffect.cameraShakeId);  })).build();
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
/*  54 */   protected int cameraShakeIndex = Integer.MIN_VALUE;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Nullable
/*     */   protected ShakeIntensity intensity;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Nonnull
/*     */   public AccumulationMode getAccumulationMode() {
/*  67 */     if (this.intensity == null) return ShakeIntensity.DEFAULT_ACCUMULATION_MODE;
/*     */     
/*  69 */     return this.intensity.getAccumulationMode();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public float getDefaultIntensityContext() {
/*  76 */     if (this.intensity == null) return 0.0F;
/*     */     
/*  78 */     return this.intensity.getValue();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public float calculateIntensity(float intensityContext) {
/*  88 */     if (this.intensity == null) return intensityContext;
/*     */     
/*  90 */     ShakeIntensity.Modifier modifier = this.intensity.getModifier();
/*  91 */     if (modifier == null) return intensityContext;
/*     */     
/*  93 */     return modifier.apply(intensityContext);
/*     */   }
/*     */ 
/*     */   
/*     */   @Nonnull
/*     */   public com.hypixel.hytale.protocol.packets.camera.CameraShakeEffect createCameraShakePacket() {
/*  99 */     float intensity = getDefaultIntensityContext();
/* 100 */     return createCameraShakePacket(intensity);
/*     */   }
/*     */ 
/*     */   
/*     */   @Nonnull
/*     */   public com.hypixel.hytale.protocol.packets.camera.CameraShakeEffect createCameraShakePacket(float intensityContext) {
/* 106 */     float intensity = calculateIntensity(intensityContext);
/* 107 */     AccumulationMode accumulationMode = getAccumulationMode();
/* 108 */     return new com.hypixel.hytale.protocol.packets.camera.CameraShakeEffect(this.cameraShakeIndex, intensity, accumulationMode);
/*     */   }
/*     */ 
/*     */   
/*     */   @Nonnull
/*     */   public String toString() {
/* 114 */     return "CameraShakeEffect{id='" + this.id + "', data=" + String.valueOf(this.data) + ", cameraShakeId='" + this.cameraShakeId + "', cameraShakeIndex=" + this.cameraShakeIndex + ", intensity=" + String.valueOf(this.intensity) + "}";
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\builtin\adventure\camera\asset\cameraeffect\CameraShakeEffect.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */