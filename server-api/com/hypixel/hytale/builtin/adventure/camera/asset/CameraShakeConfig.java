/*     */ package com.hypixel.hytale.builtin.adventure.camera.asset;
/*     */ 
/*     */ import com.hypixel.hytale.codec.Codec;
/*     */ import com.hypixel.hytale.codec.KeyedCodec;
/*     */ import com.hypixel.hytale.codec.builder.BuilderCodec;
/*     */ import com.hypixel.hytale.codec.validation.Validators;
/*     */ import com.hypixel.hytale.protocol.CameraShakeConfig;
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
/*     */ public class CameraShakeConfig
/*     */   implements NetworkSerializable<CameraShakeConfig>
/*     */ {
/*     */   public static final BuilderCodec<CameraShakeConfig> CODEC;
/*     */   protected float duration;
/*     */   protected Float startTime;
/*     */   
/*     */   static {
/*  35 */     CODEC = ((BuilderCodec.Builder)((BuilderCodec.Builder)((BuilderCodec.Builder)((BuilderCodec.Builder)((BuilderCodec.Builder)((BuilderCodec.Builder)BuilderCodec.builder(CameraShakeConfig.class, CameraShakeConfig::new).appendInherited(new KeyedCodec("Duration", (Codec)Codec.FLOAT), (o, v) -> o.duration = v.floatValue(), o -> Float.valueOf(o.duration), (o, p) -> o.duration = p.duration).documentation("The time period that the camera will shake at full intensity for").addValidator(Validators.min(Float.valueOf(0.0F))).add()).appendInherited(new KeyedCodec("StartTime", (Codec)Codec.FLOAT), (o, v) -> o.startTime = v, o -> o.startTime, (o, p) -> o.startTime = p.startTime).documentation("The initial time value that the Offset and Rotation noises are sampled from when the camera-shake starts. If absent, the camera-shake uses a continuously incremented time value.").add()).appendInherited(new KeyedCodec("EaseIn", (Codec)EasingConfig.CODEC), (o, v) -> o.easeIn = v, o -> o.easeIn, (o, p) -> o.easeIn = p.easeIn).documentation("The fade-in time and intensity curve for the camera shake").addValidator(Validators.nonNull()).add()).appendInherited(new KeyedCodec("EaseOut", (Codec)EasingConfig.CODEC), (o, v) -> o.easeOut = v, o -> o.easeOut, (o, p) -> o.easeOut = p.easeOut).documentation("The fade-out time and intensity curve for the camera shake").addValidator(Validators.nonNull()).add()).appendInherited(new KeyedCodec("Offset", (Codec)OffsetNoise.CODEC), (o, v) -> o.offset = v, o -> o.offset, (o, p) -> o.offset = p.offset).documentation("The translational offset motion").add()).appendInherited(new KeyedCodec("Rotation", (Codec)RotationNoise.CODEC), (o, v) -> o.rotation = v, o -> o.rotation, (o, p) -> o.rotation = p.rotation).documentation("The rotational motion").add()).build();
/*     */   }
/*     */ 
/*     */   
/*  39 */   protected EasingConfig easeIn = EasingConfig.NONE;
/*  40 */   protected EasingConfig easeOut = EasingConfig.NONE;
/*  41 */   protected OffsetNoise offset = OffsetNoise.NONE;
/*  42 */   protected RotationNoise rotation = RotationNoise.NONE;
/*     */ 
/*     */   
/*     */   @Nonnull
/*     */   public CameraShakeConfig toPacket() {
/*  47 */     boolean continuous = (this.startTime == null);
/*  48 */     float startTime = continuous ? 0.0F : this.startTime.floatValue();
/*  49 */     return new CameraShakeConfig(this.duration, startTime, continuous, this.easeIn.toPacket(), this.easeOut.toPacket(), this.offset.toPacket(), this.rotation.toPacket());
/*     */   }
/*     */ 
/*     */   
/*     */   @Nonnull
/*     */   public String toString() {
/*  55 */     return "CameraShakeConfig{duration=" + this.duration + ", startTime=" + this.startTime + ", easeIn=" + String.valueOf(this.easeIn) + ", easeOut=" + String.valueOf(this.easeOut) + ", offset=" + String.valueOf(this.offset) + ", rotation=" + String.valueOf(this.rotation) + "}";
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static class OffsetNoise
/*     */     implements NetworkSerializable<com.hypixel.hytale.protocol.OffsetNoise>
/*     */   {
/*     */     public static final BuilderCodec<OffsetNoise> CODEC;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     static {
/*  77 */       CODEC = ((BuilderCodec.Builder)((BuilderCodec.Builder)((BuilderCodec.Builder)((BuilderCodec.Builder)BuilderCodec.builder(OffsetNoise.class, OffsetNoise::new).documentation("The translational offset noise sources. Each component's list of noise configurations are summed together to calculate the output value for that component")).appendInherited(new KeyedCodec("X", (Codec)NoiseConfig.ARRAY_CODEC), (o, v) -> o.x = v, o -> o.x, (o, p) -> o.x = p.x).documentation("The noise used to vary the camera x-offset").add()).appendInherited(new KeyedCodec("Y", (Codec)NoiseConfig.ARRAY_CODEC), (o, v) -> o.y = v, o -> o.y, (o, p) -> o.y = p.y).documentation("The noise used to vary the camera y-offset").add()).appendInherited(new KeyedCodec("Z", (Codec)NoiseConfig.ARRAY_CODEC), (o, v) -> o.z = v, o -> o.z, (o, p) -> o.z = p.z).documentation("The noise used to vary the camera z-offset").add()).build();
/*     */     }
/*  79 */     public static final OffsetNoise NONE = new OffsetNoise();
/*     */     
/*     */     protected NoiseConfig[] x;
/*     */     
/*     */     protected NoiseConfig[] y;
/*     */     protected NoiseConfig[] z;
/*     */     
/*     */     @Nonnull
/*     */     public com.hypixel.hytale.protocol.OffsetNoise toPacket() {
/*  88 */       return new com.hypixel.hytale.protocol.OffsetNoise(NoiseConfig.toPacket(this.x), NoiseConfig.toPacket(this.y), NoiseConfig.toPacket(this.z));
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     @Nonnull
/*     */     public String toString() {
/*  95 */       return "OffsetNoise{x=" + Arrays.toString((Object[])this.x) + ", y=" + 
/*  96 */         Arrays.toString((Object[])this.y) + ", z=" + 
/*  97 */         Arrays.toString((Object[])this.z) + "}";
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static class RotationNoise
/*     */     implements NetworkSerializable<com.hypixel.hytale.protocol.RotationNoise>
/*     */   {
/*     */     public static final BuilderCodec<RotationNoise> CODEC;
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     static {
/* 114 */       CODEC = ((BuilderCodec.Builder)((BuilderCodec.Builder)((BuilderCodec.Builder)((BuilderCodec.Builder)BuilderCodec.builder(RotationNoise.class, RotationNoise::new).documentation("The rotational noise sources. Each component's list of noise configurations are summed together to calculate the output value for that component")).appendInherited(new KeyedCodec("Pitch", (Codec)NoiseConfig.ARRAY_CODEC), (o, v) -> o.pitch = v, o -> o.pitch, (o, p) -> o.pitch = p.pitch).documentation("The noise used to vary the camera pitch").add()).appendInherited(new KeyedCodec("Yaw", (Codec)NoiseConfig.ARRAY_CODEC), (o, v) -> o.yaw = v, o -> o.yaw, (o, p) -> o.yaw = p.yaw).documentation("The noise used to vary the camera yaw").add()).appendInherited(new KeyedCodec("Roll", (Codec)NoiseConfig.ARRAY_CODEC), (o, v) -> o.roll = v, o -> o.roll, (o, p) -> o.roll = p.roll).documentation("The noise used to vary the camera roll").add()).build();
/*     */     }
/* 116 */     public static final RotationNoise NONE = new RotationNoise();
/*     */     
/*     */     protected NoiseConfig[] pitch;
/*     */     
/*     */     protected NoiseConfig[] yaw;
/*     */     protected NoiseConfig[] roll;
/*     */     
/*     */     @Nonnull
/*     */     public com.hypixel.hytale.protocol.RotationNoise toPacket() {
/* 125 */       return new com.hypixel.hytale.protocol.RotationNoise(NoiseConfig.toPacket(this.pitch), NoiseConfig.toPacket(this.yaw), NoiseConfig.toPacket(this.roll));
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     @Nonnull
/*     */     public String toString() {
/* 132 */       return "RotationNoise{pitch=" + Arrays.toString((Object[])this.pitch) + ", yaw=" + 
/* 133 */         Arrays.toString((Object[])this.yaw) + ", roll=" + 
/* 134 */         Arrays.toString((Object[])this.roll) + "}";
/*     */     }
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\builtin\adventure\camera\asset\CameraShakeConfig.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */