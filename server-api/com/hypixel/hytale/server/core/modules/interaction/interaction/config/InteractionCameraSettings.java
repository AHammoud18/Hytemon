/*     */ package com.hypixel.hytale.server.core.modules.interaction.interaction.config;
/*     */ 
/*     */ import com.hypixel.hytale.codec.Codec;
/*     */ import com.hypixel.hytale.codec.KeyedCodec;
/*     */ import com.hypixel.hytale.codec.builder.BuilderCodec;
/*     */ import com.hypixel.hytale.codec.codecs.array.ArrayCodec;
/*     */ import com.hypixel.hytale.codec.validation.LegacyValidator;
/*     */ import com.hypixel.hytale.codec.validation.ValidationResults;
/*     */ import com.hypixel.hytale.codec.validation.Validators;
/*     */ import com.hypixel.hytale.protocol.Direction;
/*     */ import com.hypixel.hytale.protocol.InteractionCameraSettings;
/*     */ import com.hypixel.hytale.protocol.Vector3f;
/*     */ import com.hypixel.hytale.server.core.codec.ProtocolCodecs;
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
/*     */ public class InteractionCameraSettings
/*     */   implements NetworkSerializable<InteractionCameraSettings>
/*     */ {
/*     */   public static final BuilderCodec<InteractionCameraSettings> CODEC;
/*     */   private InteractionCamera[] firstPerson;
/*     */   private InteractionCamera[] thirdPerson;
/*     */   
/*     */   static {
/*  41 */     CODEC = ((BuilderCodec.Builder)((BuilderCodec.Builder)BuilderCodec.builder(InteractionCameraSettings.class, InteractionCameraSettings::new).appendInherited(new KeyedCodec("FirstPerson", (Codec)new ArrayCodec((Codec)InteractionCamera.CODEC, x$0 -> new InteractionCamera[x$0])), (o, i) -> o.firstPerson = i, o -> o.firstPerson, (o, p) -> o.firstPerson = p.firstPerson).addValidator(getInteractionCameraValidator()).add()).appendInherited(new KeyedCodec("ThirdPerson", (Codec)new ArrayCodec((Codec)InteractionCamera.CODEC, x$0 -> new InteractionCamera[x$0])), (o, i) -> o.thirdPerson = i, o -> o.thirdPerson, (o, p) -> o.thirdPerson = p.thirdPerson).addValidator(getInteractionCameraValidator()).add()).build();
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   @Nonnull
/*     */   private static LegacyValidator<InteractionCamera[]> getInteractionCameraValidator() {
/*  48 */     return (interactionCameras, results) -> {
/*     */         if (interactionCameras == null) {
/*     */           return;
/*     */         }
/*     */         float lastTime = -1.0F;
/*     */         for (InteractionCamera entry : interactionCameras) {
/*     */           if (entry.time <= lastTime) {
/*     */             results.fail("Camera entry with time: " + entry.time + " conflicts with another entry");
/*     */           }
/*     */           lastTime = entry.time;
/*     */         } 
/*     */       };
/*     */   }
/*     */   
/*     */   @Nonnull
/*     */   public InteractionCameraSettings toPacket() {
/*  64 */     InteractionCameraSettings packet = new InteractionCameraSettings();
/*  65 */     if (this.firstPerson != null) {
/*  66 */       packet.firstPerson = new com.hypixel.hytale.protocol.InteractionCamera[this.firstPerson.length];
/*  67 */       for (int i = 0; i < this.firstPerson.length; i++) {
/*  68 */         packet.firstPerson[i] = this.firstPerson[i].toPacket();
/*     */       }
/*     */     } 
/*  71 */     if (this.thirdPerson != null) {
/*  72 */       packet.thirdPerson = new com.hypixel.hytale.protocol.InteractionCamera[this.thirdPerson.length];
/*  73 */       for (int i = 0; i < this.thirdPerson.length; i++) {
/*  74 */         packet.thirdPerson[i] = this.thirdPerson[i].toPacket();
/*     */       }
/*     */     } 
/*  77 */     return packet;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   @Nonnull
/*     */   public String toString() {
/*  84 */     return "InteractionCameraSettings{firstPerson=" + Arrays.toString((Object[])this.firstPerson) + ", thirdPerson=" + 
/*  85 */       Arrays.toString((Object[])this.thirdPerson) + "}";
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static class InteractionCamera
/*     */     implements NetworkSerializable<com.hypixel.hytale.protocol.InteractionCamera>
/*     */   {
/*     */     public static final BuilderCodec<InteractionCamera> CODEC;
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
/*     */     static {
/* 120 */       CODEC = ((BuilderCodec.Builder)((BuilderCodec.Builder)((BuilderCodec.Builder)BuilderCodec.builder(InteractionCamera.class, InteractionCamera::new).appendInherited(new KeyedCodec("Time", (Codec)Codec.FLOAT), (o, i) -> o.time = i.floatValue(), o -> Float.valueOf(o.time), (o, p) -> o.time = p.time).addValidator(Validators.greaterThan(Float.valueOf(0.0F))).add()).appendInherited(new KeyedCodec("Position", (Codec)ProtocolCodecs.VECTOR3F), (o, i) -> o.position = i, o -> o.position, (o, p) -> o.position = p.position).addValidator(Validators.nonNull()).add()).appendInherited(new KeyedCodec("Rotation", (Codec)ProtocolCodecs.DIRECTION), (o, i) -> { o.rotation = i; o.rotation.yaw *= 0.017453292F; o.rotation.pitch *= 0.017453292F; o.rotation.roll *= 0.017453292F; }o -> new Direction(o.rotation.yaw * 57.295776F, o.rotation.pitch * 57.295776F, o.rotation.roll * 57.295776F), (o, p) -> o.rotation = p.rotation).addValidator(Validators.nonNull()).add()).build();
/*     */     }
/* 122 */     private float time = 0.1F;
/* 123 */     private Vector3f position = new Vector3f(0.0F, 0.0F, 0.0F);
/* 124 */     private Direction rotation = new Direction(0.0F, 0.0F, 0.0F);
/*     */ 
/*     */     
/*     */     @Nonnull
/*     */     public com.hypixel.hytale.protocol.InteractionCamera toPacket() {
/* 129 */       com.hypixel.hytale.protocol.InteractionCamera packet = new com.hypixel.hytale.protocol.InteractionCamera();
/* 130 */       packet.time = this.time;
/* 131 */       packet.position = this.position;
/* 132 */       packet.rotation = this.rotation;
/* 133 */       return packet;
/*     */     }
/*     */ 
/*     */     
/*     */     @Nonnull
/*     */     public String toString() {
/* 139 */       return "InteractionCamera{time=" + this.time + ", position=" + String.valueOf(this.position) + ", rotation=" + String.valueOf(this.rotation) + "}";
/*     */     }
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\server\core\modules\interaction\interaction\config\InteractionCameraSettings.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */