/*     */ package com.hypixel.hytale.server.core.asset.type.particle.config;
/*     */ 
/*     */ import com.hypixel.hytale.codec.Codec;
/*     */ import com.hypixel.hytale.codec.KeyedCodec;
/*     */ import com.hypixel.hytale.codec.builder.BuilderCodec;
/*     */ import com.hypixel.hytale.codec.validation.Validators;
/*     */ import com.hypixel.hytale.protocol.ParticleAttractor;
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
/*     */ public class ParticleAttractor
/*     */   implements NetworkSerializable<ParticleAttractor>
/*     */ {
/*     */   public static final BuilderCodec<ParticleAttractor> CODEC;
/*     */   protected Vector3f position;
/*     */   protected Vector3f radialAxis;
/*     */   protected float trailPositionMultiplier;
/*     */   protected float radius;
/*     */   protected float radialAcceleration;
/*     */   protected float radialTangentAcceleration;
/*     */   protected Vector3f linearAcceleration;
/*     */   protected float radialImpulse;
/*     */   protected float radialTangentImpulse;
/*     */   protected Vector3f linearImpulse;
/*     */   protected Vector3f dampingMultiplier;
/*     */   
/*     */   static {
/*  73 */     CODEC = ((BuilderCodec.Builder)((BuilderCodec.Builder)((BuilderCodec.Builder)((BuilderCodec.Builder)((BuilderCodec.Builder)((BuilderCodec.Builder)((BuilderCodec.Builder)((BuilderCodec.Builder)((BuilderCodec.Builder)((BuilderCodec.Builder)((BuilderCodec.Builder)BuilderCodec.builder(ParticleAttractor.class, ParticleAttractor::new).addField(new KeyedCodec("Position", (Codec)ProtocolCodecs.VECTOR3F), (particleAttractor, o) -> particleAttractor.position = o, particleAttractor -> particleAttractor.position)).addField(new KeyedCodec("RadialAxis", (Codec)ProtocolCodecs.VECTOR3F), (particleAttractor, o) -> particleAttractor.radialAxis = o, particleAttractor -> particleAttractor.radialAxis)).append(new KeyedCodec("TrailPositionMultiplier", (Codec)Codec.FLOAT), (particleAttractor, f) -> particleAttractor.trailPositionMultiplier = f.floatValue(), particleAttractor -> Float.valueOf(particleAttractor.trailPositionMultiplier)).addValidator(Validators.range(Float.valueOf(0.0F), Float.valueOf(1.0F))).add()).addField(new KeyedCodec("Radius", (Codec)Codec.FLOAT), (particleAttractor, f) -> particleAttractor.radius = f.floatValue(), particleAttractor -> Float.valueOf(particleAttractor.radius))).addField(new KeyedCodec("RadialAcceleration", (Codec)Codec.FLOAT), (particleAttractor, f) -> particleAttractor.radialAcceleration = f.floatValue(), particleAttractor -> Float.valueOf(particleAttractor.radialAcceleration))).addField(new KeyedCodec("RadialTangentAcceleration", (Codec)Codec.FLOAT), (particleAttractor, f) -> particleAttractor.radialTangentAcceleration = f.floatValue(), particleAttractor -> Float.valueOf(particleAttractor.radialTangentAcceleration))).addField(new KeyedCodec("LinearAcceleration", (Codec)ProtocolCodecs.VECTOR3F), (particleAttractor, o) -> particleAttractor.linearAcceleration = o, particleAttractor -> particleAttractor.linearAcceleration)).addField(new KeyedCodec("RadialImpulse", (Codec)Codec.FLOAT), (particleAttractor, f) -> particleAttractor.radialImpulse = f.floatValue(), particleAttractor -> Float.valueOf(particleAttractor.radialImpulse))).addField(new KeyedCodec("RadialTangentImpulse", (Codec)Codec.FLOAT), (particleAttractor, f) -> particleAttractor.radialTangentImpulse = f.floatValue(), particleAttractor -> Float.valueOf(particleAttractor.radialTangentImpulse))).addField(new KeyedCodec("LinearImpulse", (Codec)ProtocolCodecs.VECTOR3F), (particleAttractor, o) -> particleAttractor.linearImpulse = o, particleAttractor -> particleAttractor.linearImpulse)).addField(new KeyedCodec("DampingMultiplier", (Codec)ProtocolCodecs.VECTOR3F), (particleAttractor, o) -> particleAttractor.dampingMultiplier = o, particleAttractor -> particleAttractor.dampingMultiplier)).build();
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
/*     */   public ParticleAttractor(Vector3f position, Vector3f radialAxis, float trailPositionMultiplier, float radius, float radialAcceleration, float radialTangentAcceleration, Vector3f linearAcceleration, float radialImpulse, float radialTangentImpulse, Vector3f linearImpulse, Vector3f dampingMultiplier) {
/*  88 */     this.position = position;
/*  89 */     this.radialAxis = radialAxis;
/*  90 */     this.trailPositionMultiplier = trailPositionMultiplier;
/*  91 */     this.radius = radius;
/*  92 */     this.radialAcceleration = radialAcceleration;
/*  93 */     this.radialTangentAcceleration = radialTangentAcceleration;
/*  94 */     this.linearAcceleration = linearAcceleration;
/*  95 */     this.radialImpulse = radialImpulse;
/*  96 */     this.radialTangentImpulse = radialTangentImpulse;
/*  97 */     this.linearImpulse = linearImpulse;
/*  98 */     this.dampingMultiplier = dampingMultiplier;
/*     */   }
/*     */ 
/*     */   
/*     */   protected ParticleAttractor() {}
/*     */   
/*     */   public Vector3f getPosition() {
/* 105 */     return this.position;
/*     */   }
/*     */   
/*     */   public Vector3f getRadialAxis() {
/* 109 */     return this.radialAxis;
/*     */   }
/*     */   
/*     */   public float getTrailPositionMultiplier() {
/* 113 */     return this.trailPositionMultiplier;
/*     */   }
/*     */   
/*     */   public float getRadius() {
/* 117 */     return this.radius;
/*     */   }
/*     */   
/*     */   public float getRadialAcceleration() {
/* 121 */     return this.radialAcceleration;
/*     */   }
/*     */   
/*     */   public float getRadialTangentAcceleration() {
/* 125 */     return this.radialTangentAcceleration;
/*     */   }
/*     */   
/*     */   public Vector3f getLinearAcceleration() {
/* 129 */     return this.linearAcceleration;
/*     */   }
/*     */   
/*     */   public float getRadialImpulse() {
/* 133 */     return this.radialImpulse;
/*     */   }
/*     */   
/*     */   public float getRadialTangentImpulse() {
/* 137 */     return this.radialTangentImpulse;
/*     */   }
/*     */   
/*     */   public Vector3f getLinearImpulse() {
/* 141 */     return this.linearImpulse;
/*     */   }
/*     */   
/*     */   public Vector3f getDampingMultiplier() {
/* 145 */     return this.dampingMultiplier;
/*     */   }
/*     */ 
/*     */   
/*     */   @Nonnull
/*     */   public ParticleAttractor toPacket() {
/* 151 */     ParticleAttractor packet = new ParticleAttractor();
/* 152 */     if (this.position != null) packet.position = this.position; 
/* 153 */     if (this.radialAxis != null) packet.radialAxis = this.radialAxis; 
/* 154 */     packet.trailPositionMultiplier = this.trailPositionMultiplier;
/* 155 */     packet.radius = this.radius;
/* 156 */     packet.radialAcceleration = this.radialAcceleration;
/* 157 */     packet.radialTangentAcceleration = this.radialTangentAcceleration;
/* 158 */     if (this.linearAcceleration != null) packet.linearAcceleration = this.linearAcceleration; 
/* 159 */     packet.radialImpulse = this.radialImpulse;
/* 160 */     packet.radialTangentImpulse = this.radialTangentImpulse;
/* 161 */     if (this.linearImpulse != null) packet.linearImpulse = this.linearImpulse; 
/* 162 */     if (this.dampingMultiplier != null) packet.dampingMultiplier = this.dampingMultiplier; 
/* 163 */     return packet;
/*     */   }
/*     */ 
/*     */   
/*     */   @Nonnull
/*     */   public String toString() {
/* 169 */     return "ParticleAttractor{position=" + String.valueOf(this.position) + ", radialAxis=" + String.valueOf(this.radialAxis) + ", trailPositionMultiplier=" + this.trailPositionMultiplier + ", radius=" + this.radius + ", radialAcceleration=" + this.radialAcceleration + ", radialTangentAcceleration=" + this.radialTangentAcceleration + ", linearAcceleration=" + String.valueOf(this.linearAcceleration) + ", radialImpulse=" + this.radialImpulse + ", radialTangentImpulse=" + this.radialTangentImpulse + ", linearImpulse=" + String.valueOf(this.linearImpulse) + ", dampingMultiplier=" + String.valueOf(this.dampingMultiplier) + "}";
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\server\core\asset\type\particle\config\ParticleAttractor.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */