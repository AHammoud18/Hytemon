/*     */ package com.hypixel.hytale.server.core.modules.interaction.interaction.config.none.simple;
/*     */ 
/*     */ import com.hypixel.hytale.assetstore.codec.AssetCodec;
/*     */ import com.hypixel.hytale.assetstore.codec.ContainedAssetCodec;
/*     */ import com.hypixel.hytale.codec.Codec;
/*     */ import com.hypixel.hytale.codec.KeyedCodec;
/*     */ import com.hypixel.hytale.codec.builder.BuilderCodec;
/*     */ import com.hypixel.hytale.codec.validation.LateValidator;
/*     */ import com.hypixel.hytale.codec.validation.Validators;
/*     */ import com.hypixel.hytale.component.CommandBuffer;
/*     */ import com.hypixel.hytale.component.ComponentAccessor;
/*     */ import com.hypixel.hytale.component.Ref;
/*     */ import com.hypixel.hytale.protocol.Interaction;
/*     */ import com.hypixel.hytale.protocol.InteractionType;
/*     */ import com.hypixel.hytale.server.core.asset.type.entityeffect.config.EntityEffect;
/*     */ import com.hypixel.hytale.server.core.entity.InteractionContext;
/*     */ import com.hypixel.hytale.server.core.entity.effect.EffectControllerComponent;
/*     */ import com.hypixel.hytale.server.core.modules.interaction.interaction.CooldownHandler;
/*     */ import com.hypixel.hytale.server.core.modules.interaction.interaction.config.SimpleInstantInteraction;
/*     */ import com.hypixel.hytale.server.core.modules.interaction.interaction.util.InteractionTarget;
/*     */ import com.hypixel.hytale.server.core.universe.world.storage.EntityStore;
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
/*     */ public class ApplyEffectInteraction
/*     */   extends SimpleInstantInteraction
/*     */ {
/*     */   @Nonnull
/*     */   public static final BuilderCodec<ApplyEffectInteraction> CODEC;
/*     */   private String effectId;
/*     */   
/*     */   static {
/*  49 */     CODEC = ((BuilderCodec.Builder)((BuilderCodec.Builder)((BuilderCodec.Builder)BuilderCodec.builder(ApplyEffectInteraction.class, ApplyEffectInteraction::new, SimpleInstantInteraction.CODEC).documentation("Applies the given entity effect to the entity.")).appendInherited(new KeyedCodec("EffectId", (Codec)new ContainedAssetCodec(EntityEffect.class, (AssetCodec)EntityEffect.CODEC)), (interaction, s) -> interaction.effectId = s, interaction -> interaction.effectId, (interaction, parent) -> interaction.effectId = parent.effectId).addValidator(Validators.nonNull()).addValidatorLate(() -> EntityEffect.VALIDATOR_CACHE.getValidator().late()).add()).appendInherited(new KeyedCodec("Entity", (Codec)InteractionTarget.CODEC), (o, i) -> o.entityTarget = i, o -> o.entityTarget, (o, p) -> o.entityTarget = p.entityTarget).documentation("The entity to target for this interaction.").addValidator(Validators.nonNull()).add()).build();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Nonnull
/*  60 */   private InteractionTarget entityTarget = InteractionTarget.USER;
/*     */ 
/*     */ 
/*     */   
/*     */   protected void firstRun(@Nonnull InteractionType type, @Nonnull InteractionContext context, @Nonnull CooldownHandler cooldownHandler) {
/*  65 */     if (this.effectId == null)
/*     */       return; 
/*  67 */     EntityEffect entityEffect = (EntityEffect)EntityEffect.getAssetMap().getAsset(this.effectId);
/*  68 */     if (entityEffect == null) {
/*     */       return;
/*     */     }
/*     */     
/*  72 */     Ref<EntityStore> ref = context.getEntity();
/*  73 */     Ref<EntityStore> targetRef = this.entityTarget.getEntity(context, ref);
/*  74 */     if (targetRef == null || !targetRef.isValid()) {
/*     */       return;
/*     */     }
/*     */     
/*  78 */     CommandBuffer<EntityStore> commandBuffer = context.getCommandBuffer();
/*  79 */     assert commandBuffer != null;
/*     */     
/*  81 */     EffectControllerComponent effectControllerComponent = (EffectControllerComponent)commandBuffer.getComponent(targetRef, EffectControllerComponent.getComponentType());
/*  82 */     if (effectControllerComponent != null) {
/*  83 */       effectControllerComponent.addEffect(targetRef, entityEffect, (ComponentAccessor)commandBuffer);
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   protected void simulateFirstRun(@Nonnull InteractionType type, @Nonnull InteractionContext context, @Nonnull CooldownHandler cooldownHandler) {}
/*     */ 
/*     */ 
/*     */   
/*     */   @Nonnull
/*     */   protected Interaction generatePacket() {
/*  95 */     return (Interaction)new com.hypixel.hytale.protocol.ApplyEffectInteraction();
/*     */   }
/*     */ 
/*     */   
/*     */   protected void configurePacket(Interaction packet) {
/* 100 */     super.configurePacket(packet);
/* 101 */     com.hypixel.hytale.protocol.ApplyEffectInteraction p = (com.hypixel.hytale.protocol.ApplyEffectInteraction)packet;
/* 102 */     p.effectId = EntityEffect.getAssetMap().getIndex(this.effectId);
/* 103 */     p.entityTarget = this.entityTarget.toProtocol();
/*     */   }
/*     */ 
/*     */   
/*     */   @Nonnull
/*     */   public String toString() {
/* 109 */     return "ApplyEffectInteraction{effectId='" + this.effectId + "', entityTarget=" + String.valueOf(this.entityTarget) + "} " + super
/*     */ 
/*     */       
/* 112 */       .toString();
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\server\core\modules\interaction\interaction\config\none\simple\ApplyEffectInteraction.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */