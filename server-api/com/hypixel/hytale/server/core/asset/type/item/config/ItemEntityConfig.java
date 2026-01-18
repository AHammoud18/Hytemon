/*     */ package com.hypixel.hytale.server.core.asset.type.item.config;
/*     */ 
/*     */ import com.hypixel.hytale.codec.Codec;
/*     */ import com.hypixel.hytale.codec.KeyedCodec;
/*     */ import com.hypixel.hytale.codec.builder.BuilderCodec;
/*     */ import com.hypixel.hytale.protocol.Color;
/*     */ import com.hypixel.hytale.protocol.ItemEntityConfig;
/*     */ import com.hypixel.hytale.server.core.asset.type.particle.config.ParticleSystem;
/*     */ import com.hypixel.hytale.server.core.codec.ProtocolCodecs;
/*     */ import com.hypixel.hytale.server.core.io.NetworkSerializable;
/*     */ import com.hypixel.hytale.server.core.modules.physics.component.PhysicsValues;
/*     */ import java.util.function.Supplier;
/*     */ import javax.annotation.Nonnull;
/*     */ 
/*     */ public class ItemEntityConfig
/*     */   implements NetworkSerializable<ItemEntityConfig> {
/*     */   public static final String DEFAULT_PARTICLE_SYSTEM_ID = "Item";
/*  18 */   public static final ItemEntityConfig DEFAULT = new ItemEntityConfig("Item", null, true);
/*  19 */   public static final ItemEntityConfig DEFAULT_BLOCK = new ItemEntityConfig(null, null, true);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static final BuilderCodec<ItemEntityConfig> CODEC;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   static {
/*  62 */     CODEC = ((BuilderCodec.Builder)((BuilderCodec.Builder)((BuilderCodec.Builder)((BuilderCodec.Builder)((BuilderCodec.Builder)((BuilderCodec.Builder)BuilderCodec.builder(ItemEntityConfig.class, ItemEntityConfig::new).appendInherited(new KeyedCodec("Physics", (Codec)PhysicsValues.CODEC), (itemEntityConfig, physicsValues) -> itemEntityConfig.physicsValues = physicsValues, itemEntityConfig -> itemEntityConfig.physicsValues, (o, p) -> o.physicsValues = p.physicsValues).add()).appendInherited(new KeyedCodec("PickupRadius", (Codec)Codec.FLOAT), (itemEntityConfig, box) -> itemEntityConfig.pickupRadius = box.floatValue(), itemEntityConfig -> Float.valueOf(itemEntityConfig.pickupRadius), (o, p) -> o.pickupRadius = p.pickupRadius).add()).appendInherited(new KeyedCodec("Lifetime", (Codec)Codec.FLOAT), (itemEntityConfig, v) -> itemEntityConfig.ttl = v, itemEntityConfig -> itemEntityConfig.ttl, (o, p) -> o.ttl = p.ttl).add()).appendInherited(new KeyedCodec("ParticleSystemId", (Codec)Codec.STRING), (o, i) -> o.particleSystemId = i, o -> o.particleSystemId, (o, p) -> o.particleSystemId = p.particleSystemId).addValidator(ParticleSystem.VALIDATOR_CACHE.getValidator()).add()).appendInherited(new KeyedCodec("ParticleColor", (Codec)ProtocolCodecs.COLOR), (o, i) -> o.particleColor = i, o -> o.particleColor, (o, p) -> o.particleColor = p.particleColor).add()).appendInherited(new KeyedCodec("ShowItemParticles", (Codec)Codec.BOOLEAN), (o, i) -> o.showItemParticles = i.booleanValue(), o -> Boolean.valueOf(o.showItemParticles), (o, p) -> o.showItemParticles = p.showItemParticles).add()).build();
/*     */   }
/*  64 */   protected PhysicsValues physicsValues = new PhysicsValues(5.0D, 0.5D, false);
/*     */   
/*  66 */   protected float pickupRadius = 1.75F;
/*     */   
/*     */   protected Float ttl;
/*     */   
/*  70 */   protected String particleSystemId = "Item";
/*     */   
/*     */   protected Color particleColor;
/*     */   
/*     */   protected boolean showItemParticles = true;
/*     */ 
/*     */   
/*     */   public ItemEntityConfig(String particleSystemId, Color particleColor, boolean showItemParticles) {
/*  78 */     this.particleSystemId = particleSystemId;
/*  79 */     this.particleColor = particleColor;
/*  80 */     this.showItemParticles = showItemParticles;
/*     */   }
/*     */   
/*     */   public PhysicsValues getPhysicsValues() {
/*  84 */     return this.physicsValues;
/*     */   }
/*     */   
/*     */   public float getPickupRadius() {
/*  88 */     return this.pickupRadius;
/*     */   }
/*     */   
/*     */   public Float getTtl() {
/*  92 */     return this.ttl;
/*     */   }
/*     */ 
/*     */   
/*     */   @Nonnull
/*     */   public ItemEntityConfig toPacket() {
/*  98 */     ItemEntityConfig packet = new ItemEntityConfig();
/*  99 */     packet.particleSystemId = this.particleSystemId;
/* 100 */     packet.particleColor = this.particleColor;
/* 101 */     packet.showItemParticles = this.showItemParticles;
/* 102 */     return packet;
/*     */   }
/*     */ 
/*     */   
/*     */   @Nonnull
/*     */   public String toString() {
/* 108 */     return "ItemEntityConfig{physicsValues=" + String.valueOf(this.physicsValues) + ", pickupRadius=" + this.pickupRadius + ", ttl=" + this.ttl + "}";
/*     */   }
/*     */   
/*     */   public ItemEntityConfig() {}
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\server\core\asset\type\item\config\ItemEntityConfig.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */