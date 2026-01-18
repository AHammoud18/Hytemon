/*     */ package com.hypixel.hytale.server.core.asset.type.particle.config;
/*     */ 
/*     */ import com.hypixel.hytale.codec.Codec;
/*     */ import com.hypixel.hytale.codec.KeyedCodec;
/*     */ import com.hypixel.hytale.codec.builder.BuilderCodec;
/*     */ import com.hypixel.hytale.codec.codecs.array.ArrayCodec;
/*     */ import com.hypixel.hytale.codec.validation.Validators;
/*     */ import com.hypixel.hytale.protocol.Color;
/*     */ import com.hypixel.hytale.protocol.Direction;
/*     */ import com.hypixel.hytale.protocol.Vector3f;
/*     */ import com.hypixel.hytale.protocol.WorldParticle;
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
/*     */ public class WorldParticle
/*     */   implements NetworkSerializable<WorldParticle>
/*     */ {
/*     */   public static final String SYSTEM_ID_DOC = "The id of the particle system.";
/*     */   public static final String COLOR_DOC = "The colour used if none was specified in the particle settings.";
/*     */   public static final String SCALE_DOC = "The scale of the particle system.";
/*     */   public static final String POSITION_OFFSET_DOC = "The position offset from the spawn position.";
/*     */   public static final String ROTATION_OFFSET_DOC = "The rotation offset from the spawn rotation.";
/*     */   public static final BuilderCodec<WorldParticle> CODEC;
/*     */   public static final ArrayCodec<WorldParticle> ARRAY_CODEC;
/*     */   protected String systemId;
/*     */   protected Color color;
/*     */   
/*     */   static {
/*  63 */     CODEC = ((BuilderCodec.Builder)((BuilderCodec.Builder)((BuilderCodec.Builder)((BuilderCodec.Builder)((BuilderCodec.Builder)((BuilderCodec.Builder)BuilderCodec.builder(WorldParticle.class, WorldParticle::new).documentation("Particle System that can be spawned in the world.")).append(new KeyedCodec("SystemId", (Codec)Codec.STRING), (particle, s) -> particle.systemId = s, particle -> particle.systemId).documentation("The id of the particle system.").addValidator(Validators.nonNull()).addValidator(ParticleSystem.VALIDATOR_CACHE.getValidator()).add()).append(new KeyedCodec("Color", (Codec)ProtocolCodecs.COLOR), (particle, o) -> particle.color = o, particle -> particle.color).documentation("The colour used if none was specified in the particle settings.").add()).append(new KeyedCodec("Scale", (Codec)Codec.FLOAT), (particle, f) -> particle.scale = f.floatValue(), particle -> Float.valueOf(particle.scale)).documentation("The scale of the particle system.").add()).append(new KeyedCodec("PositionOffset", (Codec)ProtocolCodecs.VECTOR3F), (particle, s) -> particle.positionOffset = s, particle -> particle.positionOffset).documentation("The position offset from the spawn position.").add()).append(new KeyedCodec("RotationOffset", (Codec)ProtocolCodecs.DIRECTION), (particle, s) -> particle.rotationOffset = s, particle -> particle.rotationOffset).documentation("The rotation offset from the spawn rotation.").add()).build();
/*     */     
/*  65 */     ARRAY_CODEC = new ArrayCodec((Codec)CODEC, x$0 -> new WorldParticle[x$0]);
/*     */   }
/*     */ 
/*     */   
/*  69 */   protected float scale = 1.0F;
/*     */   protected Vector3f positionOffset;
/*     */   protected Direction rotationOffset;
/*     */   
/*     */   public WorldParticle(String systemId, Color color, float scale, Vector3f positionOffset, Direction rotationOffset) {
/*  74 */     this.systemId = systemId;
/*  75 */     this.color = color;
/*  76 */     this.scale = scale;
/*  77 */     this.positionOffset = positionOffset;
/*  78 */     this.rotationOffset = rotationOffset;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getSystemId() {
/*  85 */     return this.systemId;
/*     */   }
/*     */   
/*     */   public Color getColor() {
/*  89 */     return this.color;
/*     */   }
/*     */   
/*     */   public float getScale() {
/*  93 */     return this.scale;
/*     */   }
/*     */   
/*     */   public Vector3f getPositionOffset() {
/*  97 */     return this.positionOffset;
/*     */   }
/*     */   
/*     */   public Direction getRotationOffset() {
/* 101 */     return this.rotationOffset;
/*     */   }
/*     */ 
/*     */   
/*     */   @Nonnull
/*     */   public WorldParticle toPacket() {
/* 107 */     WorldParticle packet = new WorldParticle();
/* 108 */     packet.systemId = this.systemId;
/* 109 */     packet.color = this.color;
/* 110 */     packet.scale = this.scale;
/* 111 */     packet.positionOffset = this.positionOffset;
/* 112 */     packet.rotationOffset = this.rotationOffset;
/* 113 */     return packet;
/*     */   }
/*     */ 
/*     */   
/*     */   @Nonnull
/*     */   public String toString() {
/* 119 */     return "WorldParticle{systemId='" + this.systemId + "', color=" + String.valueOf(this.color) + ", scale=" + this.scale + ", positionOffset=" + String.valueOf(this.positionOffset) + ", rotationOffset=" + String.valueOf(this.rotationOffset) + "}";
/*     */   }
/*     */   
/*     */   protected WorldParticle() {}
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\server\core\asset\type\particle\config\WorldParticle.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */