/*    */ package com.hypixel.hytale.server.core.asset.type.fluidfx.config;
/*    */ 
/*    */ import com.hypixel.hytale.codec.Codec;
/*    */ import com.hypixel.hytale.codec.KeyedCodec;
/*    */ import com.hypixel.hytale.codec.builder.BuilderCodec;
/*    */ import com.hypixel.hytale.codec.validation.Validators;
/*    */ import com.hypixel.hytale.protocol.Color;
/*    */ import com.hypixel.hytale.protocol.FluidParticle;
/*    */ import com.hypixel.hytale.server.core.asset.type.particle.config.ParticleSystem;
/*    */ import com.hypixel.hytale.server.core.codec.ProtocolCodecs;
/*    */ import com.hypixel.hytale.server.core.io.NetworkSerializable;
/*    */ import java.lang.ref.SoftReference;
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
/*    */ public class FluidParticle
/*    */   implements NetworkSerializable<FluidParticle>
/*    */ {
/*    */   public static final BuilderCodec<FluidParticle> CODEC;
/*    */   protected String systemId;
/*    */   protected Color color;
/*    */   
/*    */   static {
/* 42 */     CODEC = ((BuilderCodec.Builder)((BuilderCodec.Builder)((BuilderCodec.Builder)((BuilderCodec.Builder)BuilderCodec.builder(FluidParticle.class, FluidParticle::new).documentation("Particle System that can be spawned in relation to a fluid.")).append(new KeyedCodec("SystemId", (Codec)Codec.STRING), (particle, s) -> particle.systemId = s, particle -> particle.systemId).documentation("The id of the particle system.").addValidator(Validators.nonNull()).addValidator(ParticleSystem.VALIDATOR_CACHE.getValidator()).add()).append(new KeyedCodec("Color", (Codec)ProtocolCodecs.COLOR), (particle, o) -> particle.color = o, particle -> particle.color).documentation("The colour used if none was specified in the particle settings.").add()).append(new KeyedCodec("Scale", (Codec)Codec.FLOAT), (particle, f) -> particle.scale = f.floatValue(), particle -> Float.valueOf(particle.scale)).documentation("The scale of the particle system.").add()).build();
/*    */   }
/*    */ 
/*    */   
/* 46 */   protected float scale = 1.0F;
/*    */   
/*    */   private SoftReference<FluidParticle> cachedPacket;
/*    */   
/*    */   public FluidParticle(String systemId, Color color, float scale) {
/* 51 */     this.systemId = systemId;
/* 52 */     this.color = color;
/* 53 */     this.scale = scale;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public String getSystemId() {
/* 60 */     return this.systemId;
/*    */   }
/*    */   
/*    */   public Color getColor() {
/* 64 */     return this.color;
/*    */   }
/*    */   
/*    */   public float getScale() {
/* 68 */     return this.scale;
/*    */   }
/*    */ 
/*    */   
/*    */   @Nonnull
/*    */   public FluidParticle toPacket() {
/* 74 */     FluidParticle cached = (this.cachedPacket == null) ? null : this.cachedPacket.get();
/* 75 */     if (cached != null) return cached;
/*    */     
/* 77 */     FluidParticle packet = new FluidParticle();
/* 78 */     packet.systemId = this.systemId;
/* 79 */     packet.color = this.color;
/* 80 */     packet.scale = this.scale;
/*    */     
/* 82 */     this.cachedPacket = new SoftReference<>(packet);
/* 83 */     return packet;
/*    */   }
/*    */ 
/*    */   
/*    */   @Nonnull
/*    */   public String toString() {
/* 89 */     return "FluidParticle{systemId='" + this.systemId + "', color=" + String.valueOf(this.color) + ", scale=" + this.scale + "}";
/*    */   }
/*    */   
/*    */   protected FluidParticle() {}
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\server\core\asset\type\fluidfx\config\FluidParticle.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */