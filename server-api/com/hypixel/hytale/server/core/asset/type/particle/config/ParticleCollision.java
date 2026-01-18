/*    */ package com.hypixel.hytale.server.core.asset.type.particle.config;
/*    */ 
/*    */ import com.hypixel.hytale.codec.Codec;
/*    */ import com.hypixel.hytale.codec.KeyedCodec;
/*    */ import com.hypixel.hytale.codec.builder.BuilderCodec;
/*    */ import com.hypixel.hytale.codec.codecs.EnumCodec;
/*    */ import com.hypixel.hytale.codec.validation.Validators;
/*    */ import com.hypixel.hytale.protocol.ParticleCollision;
/*    */ import com.hypixel.hytale.protocol.ParticleCollisionAction;
/*    */ import com.hypixel.hytale.protocol.ParticleCollisionBlockType;
/*    */ import com.hypixel.hytale.protocol.ParticleRotationInfluence;
/*    */ import com.hypixel.hytale.server.core.io.NetworkSerializable;
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
/*    */ public class ParticleCollision
/*    */   implements NetworkSerializable<ParticleCollision>
/*    */ {
/*    */   public static final BuilderCodec<ParticleCollision> CODEC;
/*    */   
/*    */   static {
/* 36 */     CODEC = ((BuilderCodec.Builder)((BuilderCodec.Builder)((BuilderCodec.Builder)BuilderCodec.builder(ParticleCollision.class, ParticleCollision::new).append(new KeyedCodec("BlockType", (Codec)new EnumCodec(ParticleCollisionBlockType.class)), (particleCollision, o) -> particleCollision.blockType = o, particleCollision -> particleCollision.blockType).addValidator(Validators.nonNull()).add()).append(new KeyedCodec("Action", (Codec)new EnumCodec(ParticleCollisionAction.class)), (particleCollision, o) -> particleCollision.action = o, particleCollision -> particleCollision.action).addValidator(Validators.nonNull()).add()).append(new KeyedCodec("ParticleRotationInfluence", (Codec)new EnumCodec(ParticleRotationInfluence.class)), (particleCollision, o) -> particleCollision.particleRotationInfluence = o, particleCollision -> particleCollision.particleRotationInfluence).add()).build();
/*    */   } @Nonnull
/* 38 */   private ParticleCollisionBlockType blockType = ParticleCollisionBlockType.None;
/*    */   @Nonnull
/* 40 */   private ParticleCollisionAction action = ParticleCollisionAction.Expire;
/*    */   
/*    */   private ParticleRotationInfluence particleRotationInfluence;
/*    */   
/*    */   public ParticleCollision(ParticleCollisionBlockType blockType, ParticleCollisionAction action, ParticleRotationInfluence particleRotationInfluence) {
/* 45 */     this.blockType = blockType;
/* 46 */     this.action = action;
/* 47 */     this.particleRotationInfluence = particleRotationInfluence;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public ParticleCollisionBlockType getParticleMapCollision() {
/* 54 */     return this.blockType;
/*    */   }
/*    */   
/*    */   public ParticleCollisionAction getType() {
/* 58 */     return this.action;
/*    */   }
/*    */   
/*    */   public ParticleRotationInfluence getParticleRotationInfluence() {
/* 62 */     return this.particleRotationInfluence;
/*    */   }
/*    */ 
/*    */   
/*    */   @Nonnull
/*    */   public ParticleCollision toPacket() {
/* 68 */     ParticleCollision packet = new ParticleCollision();
/* 69 */     packet.blockType = this.blockType;
/* 70 */     packet.action = this.action;
/* 71 */     packet.particleRotationInfluence = (this.particleRotationInfluence != null) ? this.particleRotationInfluence : ParticleRotationInfluence.None;
/* 72 */     return packet;
/*    */   }
/*    */ 
/*    */   
/*    */   @Nonnull
/*    */   public String toString() {
/* 78 */     return "ParticleCollision{blockType=" + String.valueOf(this.blockType) + ", action=" + String.valueOf(this.action) + ", particleRotationInfluence=" + String.valueOf(this.particleRotationInfluence) + "}";
/*    */   }
/*    */   
/*    */   protected ParticleCollision() {}
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\server\core\asset\type\particle\config\ParticleCollision.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */