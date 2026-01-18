/*     */ package com.hypixel.hytale.server.core.asset.type.model.config;
/*     */ 
/*     */ import com.hypixel.hytale.codec.Codec;
/*     */ import com.hypixel.hytale.codec.KeyedCodec;
/*     */ import com.hypixel.hytale.codec.builder.BuilderCodec;
/*     */ import com.hypixel.hytale.codec.codecs.EnumCodec;
/*     */ import com.hypixel.hytale.codec.codecs.array.ArrayCodec;
/*     */ import com.hypixel.hytale.codec.validation.Validators;
/*     */ import com.hypixel.hytale.protocol.Color;
/*     */ import com.hypixel.hytale.protocol.Direction;
/*     */ import com.hypixel.hytale.protocol.EntityPart;
/*     */ import com.hypixel.hytale.protocol.ModelParticle;
/*     */ import com.hypixel.hytale.protocol.Vector3f;
/*     */ import com.hypixel.hytale.server.core.asset.type.particle.config.ParticleSystem;
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
/*     */ public class ModelParticle
/*     */   implements NetworkSerializable<ModelParticle>
/*     */ {
/*     */   public static final BuilderCodec<ModelParticle> CODEC;
/*     */   public static final ArrayCodec<ModelParticle> ARRAY_CODEC;
/*     */   protected String systemId;
/*     */   
/*     */   static {
/*  74 */     CODEC = ((BuilderCodec.Builder)((BuilderCodec.Builder)((BuilderCodec.Builder)((BuilderCodec.Builder)((BuilderCodec.Builder)((BuilderCodec.Builder)((BuilderCodec.Builder)((BuilderCodec.Builder)BuilderCodec.builder(ModelParticle.class, ModelParticle::new).append(new KeyedCodec("SystemId", (Codec)Codec.STRING), (particle, s) -> particle.systemId = s, particle -> particle.systemId).addValidator(Validators.nonNull()).addValidator(ParticleSystem.VALIDATOR_CACHE.getValidator()).add()).append(new KeyedCodec("TargetEntityPart", (Codec)new EnumCodec(EntityPart.class)), (particle, o) -> particle.targetEntityPart = o, particle -> particle.targetEntityPart).addValidator(Validators.nonNull()).add()).append(new KeyedCodec("TargetNodeName", (Codec)Codec.STRING), (particle, s) -> particle.targetNodeName = s, particle -> particle.targetNodeName).add()).append(new KeyedCodec("Color", (Codec)ProtocolCodecs.COLOR), (particle, o) -> particle.color = o, particle -> particle.color).add()).append(new KeyedCodec("Scale", (Codec)Codec.DOUBLE), (particle, o) -> particle.scale = o.floatValue(), particle -> Double.valueOf(particle.scale)).addValidator(Validators.greaterThan(Double.valueOf(0.0D))).add()).append(new KeyedCodec("PositionOffset", (Codec)ProtocolCodecs.VECTOR3F), (particle, s) -> particle.positionOffset = s, particle -> particle.positionOffset).add()).append(new KeyedCodec("RotationOffset", (Codec)ProtocolCodecs.DIRECTION), (particle, s) -> particle.rotationOffset = s, particle -> particle.rotationOffset).add()).append(new KeyedCodec("DetachedFromModel", (Codec)Codec.BOOLEAN), (modelParticle, aBoolean) -> modelParticle.detachedFromModel = aBoolean.booleanValue(), modelParticle -> Boolean.valueOf(modelParticle.detachedFromModel)).documentation("To indicate if the spawned particle should be attached to the model and follow it, or spawn in world space.").add()).build();
/*     */     
/*  76 */     ARRAY_CODEC = new ArrayCodec((Codec)CODEC, x$0 -> new ModelParticle[x$0]);
/*     */   }
/*     */   @Nonnull
/*  79 */   protected EntityPart targetEntityPart = EntityPart.Self;
/*     */   
/*     */   protected String targetNodeName;
/*     */   protected Color color;
/*  83 */   protected float scale = 1.0F;
/*     */   protected Vector3f positionOffset;
/*     */   protected Direction rotationOffset;
/*     */   protected boolean detachedFromModel;
/*     */   
/*     */   public ModelParticle(String systemId, EntityPart targetEntityPart, String targetNodeName, Color color, float scale, Vector3f positionOffset, Direction rotationOffset, boolean detachedFromModel) {
/*  89 */     this.systemId = systemId;
/*  90 */     this.targetEntityPart = targetEntityPart;
/*  91 */     this.targetNodeName = targetNodeName;
/*  92 */     this.color = color;
/*  93 */     this.scale = scale;
/*  94 */     this.positionOffset = positionOffset;
/*  95 */     this.rotationOffset = rotationOffset;
/*  96 */     this.detachedFromModel = detachedFromModel;
/*     */   }
/*     */   
/*     */   public ModelParticle(ModelParticle other) {
/* 100 */     this.systemId = other.systemId;
/* 101 */     this.targetEntityPart = other.targetEntityPart;
/* 102 */     this.targetNodeName = other.targetNodeName;
/* 103 */     this.color = other.color;
/* 104 */     this.scale = other.scale;
/* 105 */     this.positionOffset = other.positionOffset;
/* 106 */     this.rotationOffset = other.rotationOffset;
/* 107 */     this.detachedFromModel = other.detachedFromModel;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Nonnull
/*     */   public ModelParticle toPacket() {
/* 116 */     ModelParticle packet = new ModelParticle();
/* 117 */     packet.systemId = this.systemId;
/* 118 */     packet.targetEntityPart = this.targetEntityPart;
/* 119 */     packet.targetNodeName = this.targetNodeName;
/* 120 */     packet.color = this.color;
/* 121 */     packet.scale = this.scale;
/* 122 */     packet.positionOffset = this.positionOffset;
/* 123 */     packet.rotationOffset = this.rotationOffset;
/* 124 */     packet.detachedFromModel = this.detachedFromModel;
/* 125 */     return packet;
/*     */   }
/*     */   
/*     */   public String getSystemId() {
/* 129 */     return this.systemId;
/*     */   }
/*     */   
/*     */   public EntityPart getTargetEntityPart() {
/* 133 */     return this.targetEntityPart;
/*     */   }
/*     */   
/*     */   public String getTargetNodeName() {
/* 137 */     return this.targetNodeName;
/*     */   }
/*     */   
/*     */   public Color getColor() {
/* 141 */     return this.color;
/*     */   }
/*     */   
/*     */   public float getScale() {
/* 145 */     return this.scale;
/*     */   }
/*     */   
/*     */   public Vector3f getPositionOffset() {
/* 149 */     return this.positionOffset;
/*     */   }
/*     */   
/*     */   public Direction getRotationOffset() {
/* 153 */     return this.rotationOffset;
/*     */   }
/*     */   
/*     */   public boolean isDetachedFromModel() {
/* 157 */     return this.detachedFromModel;
/*     */   }
/*     */   
/*     */   public ModelParticle scale(float scale) {
/* 161 */     this.scale *= scale;
/* 162 */     if (this.positionOffset != null) {
/* 163 */       this.positionOffset.x *= scale;
/* 164 */       this.positionOffset.y *= scale;
/* 165 */       this.positionOffset.z *= scale;
/*     */     } 
/* 167 */     return this;
/*     */   }
/*     */ 
/*     */   
/*     */   @Nonnull
/*     */   public String toString() {
/* 173 */     return "ModelParticle{systemId='" + this.systemId + "', targetEntityPart=" + String.valueOf(this.targetEntityPart) + ", targetNodeName='" + this.targetNodeName + "', color=" + String.valueOf(this.color) + ", scale=" + this.scale + ", positionOffset=" + String.valueOf(this.positionOffset) + ", rotationOffset=" + String.valueOf(this.rotationOffset) + ", detachedFromModel=" + this.detachedFromModel + "}";
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
/*     */   public ModelParticle clone() {
/* 187 */     return new ModelParticle(this);
/*     */   }
/*     */   
/*     */   protected ModelParticle() {}
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\server\core\asset\type\model\config\ModelParticle.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */