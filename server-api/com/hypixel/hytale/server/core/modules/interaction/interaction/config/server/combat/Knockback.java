/*    */ package com.hypixel.hytale.server.core.modules.interaction.interaction.config.server.combat;
/*    */ 
/*    */ import com.hypixel.hytale.codec.Codec;
/*    */ import com.hypixel.hytale.codec.KeyedCodec;
/*    */ import com.hypixel.hytale.codec.builder.BuilderCodec;
/*    */ import com.hypixel.hytale.codec.lookup.CodecMapCodec;
/*    */ import com.hypixel.hytale.codec.validation.Validators;
/*    */ import com.hypixel.hytale.math.vector.Vector3d;
/*    */ import com.hypixel.hytale.protocol.ChangeVelocityType;
/*    */ import com.hypixel.hytale.server.core.codec.ProtocolCodecs;
/*    */ import com.hypixel.hytale.server.core.modules.splitvelocity.VelocityConfig;
/*    */ import javax.annotation.Nonnull;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public abstract class Knockback
/*    */ {
/* 21 */   public static final CodecMapCodec<Knockback> CODEC = new CodecMapCodec("Type", true);
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public static final BuilderCodec<Knockback> BASE_CODEC;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   protected float force;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   protected float duration;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   static {
/* 51 */     BASE_CODEC = ((BuilderCodec.Builder)((BuilderCodec.Builder)((BuilderCodec.Builder)((BuilderCodec.Builder)BuilderCodec.abstractBuilder(Knockback.class).append(new KeyedCodec("Force", (Codec)Codec.DOUBLE), (knockbackAttachment, d) -> knockbackAttachment.force = d.floatValue(), knockbackAttachment -> Double.valueOf(knockbackAttachment.force)).add()).append(new KeyedCodec("Duration", (Codec)Codec.FLOAT), (knockbackAttachment, f) -> knockbackAttachment.duration = f.floatValue(), knockbackAttachment -> Float.valueOf(knockbackAttachment.duration)).addValidator(Validators.greaterThanOrEqual(Float.valueOf(0.0F))).documentation("The duration for which the knockback force should be continuously applied. If 0, force is applied once.").add()).append(new KeyedCodec("VelocityType", (Codec)ProtocolCodecs.CHANGE_VELOCITY_TYPE_CODEC), (knockbackAttachment, d) -> knockbackAttachment.velocityType = d, knockbackAttachment -> knockbackAttachment.velocityType).add()).appendInherited(new KeyedCodec("VelocityConfig", (Codec)VelocityConfig.CODEC), (o, i) -> o.velocityConfig = i, o -> o.velocityConfig, (o, p) -> o.velocityConfig = p.velocityConfig).add()).build();
/*    */   }
/*    */ 
/*    */   
/* 55 */   protected ChangeVelocityType velocityType = ChangeVelocityType.Add;
/*    */ 
/*    */   
/*    */   private VelocityConfig velocityConfig;
/*    */ 
/*    */   
/*    */   public float getForce() {
/* 62 */     return this.force;
/*    */   }
/*    */   
/*    */   public float getDuration() {
/* 66 */     return this.duration;
/*    */   }
/*    */   
/*    */   public ChangeVelocityType getVelocityType() {
/* 70 */     return this.velocityType;
/*    */   }
/*    */   
/*    */   public VelocityConfig getVelocityConfig() {
/* 74 */     return this.velocityConfig;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   @Nonnull
/*    */   public String toString() {
/* 82 */     return "Knockback{, force=" + this.force + ", duration=" + this.duration + ", velocityType=" + String.valueOf(this.velocityType) + ", velocityConfig=" + String.valueOf(this.velocityConfig) + "}";
/*    */   }
/*    */   
/*    */   public abstract Vector3d calculateVector(Vector3d paramVector3d1, float paramFloat, Vector3d paramVector3d2);
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\server\core\modules\interaction\interaction\config\server\combat\Knockback.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */