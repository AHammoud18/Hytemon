/*    */ package com.hypixel.hytale.builtin.adventure.camera.asset;
/*    */ 
/*    */ import com.hypixel.hytale.codec.Codec;
/*    */ import com.hypixel.hytale.codec.KeyedCodec;
/*    */ import com.hypixel.hytale.codec.builder.BuilderCodec;
/*    */ import com.hypixel.hytale.protocol.OffsetNoise;
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
/*    */ public class OffsetNoise
/*    */   implements NetworkSerializable<OffsetNoise>
/*    */ {
/*    */   public static final BuilderCodec<OffsetNoise> CODEC;
/*    */   
/*    */   static {
/* 77 */     CODEC = ((BuilderCodec.Builder)((BuilderCodec.Builder)((BuilderCodec.Builder)((BuilderCodec.Builder)BuilderCodec.builder(OffsetNoise.class, OffsetNoise::new).documentation("The translational offset noise sources. Each component's list of noise configurations are summed together to calculate the output value for that component")).appendInherited(new KeyedCodec("X", (Codec)NoiseConfig.ARRAY_CODEC), (o, v) -> o.x = v, o -> o.x, (o, p) -> o.x = p.x).documentation("The noise used to vary the camera x-offset").add()).appendInherited(new KeyedCodec("Y", (Codec)NoiseConfig.ARRAY_CODEC), (o, v) -> o.y = v, o -> o.y, (o, p) -> o.y = p.y).documentation("The noise used to vary the camera y-offset").add()).appendInherited(new KeyedCodec("Z", (Codec)NoiseConfig.ARRAY_CODEC), (o, v) -> o.z = v, o -> o.z, (o, p) -> o.z = p.z).documentation("The noise used to vary the camera z-offset").add()).build();
/*    */   }
/* 79 */   public static final OffsetNoise NONE = new OffsetNoise();
/*    */   
/*    */   protected NoiseConfig[] x;
/*    */   
/*    */   protected NoiseConfig[] y;
/*    */   protected NoiseConfig[] z;
/*    */   
/*    */   @Nonnull
/*    */   public OffsetNoise toPacket() {
/* 88 */     return new OffsetNoise(NoiseConfig.toPacket(this.x), NoiseConfig.toPacket(this.y), NoiseConfig.toPacket(this.z));
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   @Nonnull
/*    */   public String toString() {
/* 95 */     return "OffsetNoise{x=" + Arrays.toString((Object[])this.x) + ", y=" + 
/* 96 */       Arrays.toString((Object[])this.y) + ", z=" + 
/* 97 */       Arrays.toString((Object[])this.z) + "}";
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\builtin\adventure\camera\asset\CameraShakeConfig$OffsetNoise.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */