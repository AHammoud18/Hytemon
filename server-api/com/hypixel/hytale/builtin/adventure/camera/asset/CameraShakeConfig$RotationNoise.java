/*     */ package com.hypixel.hytale.builtin.adventure.camera.asset;
/*     */ 
/*     */ import com.hypixel.hytale.codec.Codec;
/*     */ import com.hypixel.hytale.codec.KeyedCodec;
/*     */ import com.hypixel.hytale.codec.builder.BuilderCodec;
/*     */ import com.hypixel.hytale.protocol.RotationNoise;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class RotationNoise
/*     */   implements NetworkSerializable<RotationNoise>
/*     */ {
/*     */   public static final BuilderCodec<RotationNoise> CODEC;
/*     */   
/*     */   static {
/* 114 */     CODEC = ((BuilderCodec.Builder)((BuilderCodec.Builder)((BuilderCodec.Builder)((BuilderCodec.Builder)BuilderCodec.builder(RotationNoise.class, RotationNoise::new).documentation("The rotational noise sources. Each component's list of noise configurations are summed together to calculate the output value for that component")).appendInherited(new KeyedCodec("Pitch", (Codec)NoiseConfig.ARRAY_CODEC), (o, v) -> o.pitch = v, o -> o.pitch, (o, p) -> o.pitch = p.pitch).documentation("The noise used to vary the camera pitch").add()).appendInherited(new KeyedCodec("Yaw", (Codec)NoiseConfig.ARRAY_CODEC), (o, v) -> o.yaw = v, o -> o.yaw, (o, p) -> o.yaw = p.yaw).documentation("The noise used to vary the camera yaw").add()).appendInherited(new KeyedCodec("Roll", (Codec)NoiseConfig.ARRAY_CODEC), (o, v) -> o.roll = v, o -> o.roll, (o, p) -> o.roll = p.roll).documentation("The noise used to vary the camera roll").add()).build();
/*     */   }
/* 116 */   public static final RotationNoise NONE = new RotationNoise();
/*     */   
/*     */   protected NoiseConfig[] pitch;
/*     */   
/*     */   protected NoiseConfig[] yaw;
/*     */   protected NoiseConfig[] roll;
/*     */   
/*     */   @Nonnull
/*     */   public RotationNoise toPacket() {
/* 125 */     return new RotationNoise(NoiseConfig.toPacket(this.pitch), NoiseConfig.toPacket(this.yaw), NoiseConfig.toPacket(this.roll));
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   @Nonnull
/*     */   public String toString() {
/* 132 */     return "RotationNoise{pitch=" + Arrays.toString((Object[])this.pitch) + ", yaw=" + 
/* 133 */       Arrays.toString((Object[])this.yaw) + ", roll=" + 
/* 134 */       Arrays.toString((Object[])this.roll) + "}";
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\builtin\adventure\camera\asset\CameraShakeConfig$RotationNoise.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */