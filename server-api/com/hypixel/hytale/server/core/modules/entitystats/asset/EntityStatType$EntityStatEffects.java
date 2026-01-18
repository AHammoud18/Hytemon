/*     */ package com.hypixel.hytale.server.core.modules.entitystats.asset;
/*     */ 
/*     */ import com.hypixel.hytale.codec.Codec;
/*     */ import com.hypixel.hytale.codec.KeyedCodec;
/*     */ import com.hypixel.hytale.codec.builder.BuilderCodec;
/*     */ import com.hypixel.hytale.codec.validation.LateValidator;
/*     */ import com.hypixel.hytale.protocol.EntityStatEffects;
/*     */ import com.hypixel.hytale.server.core.asset.type.model.config.ModelParticle;
/*     */ import com.hypixel.hytale.server.core.asset.type.soundevent.config.SoundEvent;
/*     */ import com.hypixel.hytale.server.core.io.NetworkSerializable;
/*     */ import com.hypixel.hytale.server.core.modules.interaction.interaction.config.RootInteraction;
/*     */ import java.util.Arrays;
/*     */ import java.util.function.Supplier;
/*     */ import javax.annotation.Nonnull;
/*     */ import javax.annotation.Nullable;
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
/*     */ public class EntityStatEffects
/*     */   implements NetworkSerializable<EntityStatEffects>
/*     */ {
/*     */   public static final BuilderCodec<EntityStatEffects> CODEC;
/*     */   private boolean triggerAtZero;
/*     */   @Nullable
/*     */   private String soundEventId;
/*     */   private int soundEventIndex;
/*     */   private ModelParticle[] particles;
/*     */   private String interactions;
/*     */   
/*     */   static {
/* 392 */     CODEC = ((BuilderCodec.Builder)((BuilderCodec.Builder)((BuilderCodec.Builder)((BuilderCodec.Builder)((BuilderCodec.Builder)BuilderCodec.builder(EntityStatEffects.class, EntityStatEffects::new).append(new KeyedCodec("TriggerAtZero", (Codec)Codec.BOOLEAN), (entityStatEffects, f) -> entityStatEffects.triggerAtZero = f.booleanValue(), entityStatEffects -> Boolean.valueOf(entityStatEffects.triggerAtZero)).documentation("Indicates that the effects should trigger when the stat reaches zero").add()).append(new KeyedCodec("SoundEventId", (Codec)Codec.STRING), (entityStatEffects, s) -> entityStatEffects.soundEventId = s, entityStatEffects -> entityStatEffects.soundEventId).addValidator(SoundEvent.VALIDATOR_CACHE.getValidator()).add()).append(new KeyedCodec("Particles", (Codec)ModelParticle.ARRAY_CODEC), (entityStatEffects, modelParticles) -> entityStatEffects.particles = modelParticles, entityStatEffects -> entityStatEffects.particles).add()).append(new KeyedCodec("Interactions", (Codec)RootInteraction.CHILD_ASSET_CODEC), (entityStatEffects, interactions) -> entityStatEffects.interactions = interactions, entityStatEffects -> entityStatEffects.interactions).addValidatorLate(() -> RootInteraction.VALIDATOR_CACHE.getValidator().late()).add()).afterDecode(entityStatEffects -> { String soundEventId = entityStatEffects.soundEventId; if (soundEventId != null) entityStatEffects.soundEventIndex = SoundEvent.getAssetMap().getIndex(soundEventId);  })).build();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public EntityStatEffects(@Nullable String soundEventId, ModelParticle[] particles, String interactions) {
/* 402 */     this.soundEventId = soundEventId;
/* 403 */     if (soundEventId != null) this.soundEventIndex = SoundEvent.getAssetMap().getIndex(soundEventId); 
/* 404 */     this.particles = particles;
/* 405 */     this.interactions = interactions;
/*     */   }
/*     */ 
/*     */   
/*     */   protected EntityStatEffects() {}
/*     */   
/*     */   @Nullable
/*     */   public String getSoundEventId() {
/* 413 */     return this.soundEventId;
/*     */   }
/*     */   
/*     */   public int getSoundEventIndex() {
/* 417 */     return this.soundEventIndex;
/*     */   }
/*     */   
/*     */   public ModelParticle[] getParticles() {
/* 421 */     return this.particles;
/*     */   }
/*     */   
/*     */   public String getInteractions() {
/* 425 */     return this.interactions;
/*     */   }
/*     */   
/*     */   public boolean triggerAtZero() {
/* 429 */     return this.triggerAtZero;
/*     */   }
/*     */ 
/*     */   
/*     */   @Nonnull
/*     */   public EntityStatEffects toPacket() {
/* 435 */     EntityStatEffects packet = new EntityStatEffects();
/* 436 */     packet.soundEventIndex = this.soundEventIndex;
/* 437 */     packet.triggerAtZero = this.triggerAtZero;
/*     */     
/* 439 */     if (this.particles != null && this.particles.length > 0) {
/* 440 */       packet.particles = new com.hypixel.hytale.protocol.ModelParticle[this.particles.length];
/*     */       
/* 442 */       for (int i = 0; i < this.particles.length; i++) {
/* 443 */         packet.particles[i] = this.particles[i].toPacket();
/*     */       }
/*     */     } 
/*     */     
/* 447 */     return packet;
/*     */   }
/*     */ 
/*     */   
/*     */   @Nonnull
/*     */   public String toString() {
/* 453 */     return "EntityStatEffects{soundEventId='" + this.soundEventId + "', particles=" + 
/*     */       
/* 455 */       Arrays.toString((Object[])this.particles) + ", interactions=" + this.interactions + "}";
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\server\core\modules\entitystats\asset\EntityStatType$EntityStatEffects.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */