/*     */ package com.hypixel.hytale.server.core.modules.interaction.interaction.config;
/*     */ 
/*     */ import com.hypixel.hytale.codec.Codec;
/*     */ import com.hypixel.hytale.codec.KeyedCodec;
/*     */ import com.hypixel.hytale.codec.builder.BuilderCodec;
/*     */ import com.hypixel.hytale.codec.validation.Validators;
/*     */ import com.hypixel.hytale.protocol.Direction;
/*     */ import com.hypixel.hytale.protocol.InteractionCamera;
/*     */ import com.hypixel.hytale.protocol.Vector3f;
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
/*     */ public class InteractionCamera
/*     */   implements NetworkSerializable<InteractionCamera>
/*     */ {
/*     */   public static final BuilderCodec<InteractionCamera> CODEC;
/*     */   
/*     */   static {
/* 120 */     CODEC = ((BuilderCodec.Builder)((BuilderCodec.Builder)((BuilderCodec.Builder)BuilderCodec.builder(InteractionCamera.class, InteractionCamera::new).appendInherited(new KeyedCodec("Time", (Codec)Codec.FLOAT), (o, i) -> o.time = i.floatValue(), o -> Float.valueOf(o.time), (o, p) -> o.time = p.time).addValidator(Validators.greaterThan(Float.valueOf(0.0F))).add()).appendInherited(new KeyedCodec("Position", (Codec)ProtocolCodecs.VECTOR3F), (o, i) -> o.position = i, o -> o.position, (o, p) -> o.position = p.position).addValidator(Validators.nonNull()).add()).appendInherited(new KeyedCodec("Rotation", (Codec)ProtocolCodecs.DIRECTION), (o, i) -> { o.rotation = i; o.rotation.yaw *= 0.017453292F; o.rotation.pitch *= 0.017453292F; o.rotation.roll *= 0.017453292F; }o -> new Direction(o.rotation.yaw * 57.295776F, o.rotation.pitch * 57.295776F, o.rotation.roll * 57.295776F), (o, p) -> o.rotation = p.rotation).addValidator(Validators.nonNull()).add()).build();
/*     */   }
/* 122 */   private float time = 0.1F;
/* 123 */   private Vector3f position = new Vector3f(0.0F, 0.0F, 0.0F);
/* 124 */   private Direction rotation = new Direction(0.0F, 0.0F, 0.0F);
/*     */ 
/*     */   
/*     */   @Nonnull
/*     */   public InteractionCamera toPacket() {
/* 129 */     InteractionCamera packet = new InteractionCamera();
/* 130 */     packet.time = this.time;
/* 131 */     packet.position = this.position;
/* 132 */     packet.rotation = this.rotation;
/* 133 */     return packet;
/*     */   }
/*     */ 
/*     */   
/*     */   @Nonnull
/*     */   public String toString() {
/* 139 */     return "InteractionCamera{time=" + this.time + ", position=" + String.valueOf(this.position) + ", rotation=" + String.valueOf(this.rotation) + "}";
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\server\core\modules\interaction\interaction\config\InteractionCameraSettings$InteractionCamera.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */