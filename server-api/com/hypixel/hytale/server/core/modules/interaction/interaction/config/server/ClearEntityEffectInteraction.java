/*    */ package com.hypixel.hytale.server.core.modules.interaction.interaction.config.server;
/*    */ 
/*    */ import com.hypixel.hytale.codec.Codec;
/*    */ import com.hypixel.hytale.codec.KeyedCodec;
/*    */ import com.hypixel.hytale.codec.builder.BuilderCodec;
/*    */ import com.hypixel.hytale.codec.validation.LateValidator;
/*    */ import com.hypixel.hytale.codec.validation.Validators;
/*    */ import com.hypixel.hytale.component.CommandBuffer;
/*    */ import com.hypixel.hytale.component.ComponentAccessor;
/*    */ import com.hypixel.hytale.component.Ref;
/*    */ import com.hypixel.hytale.protocol.Interaction;
/*    */ import com.hypixel.hytale.protocol.InteractionType;
/*    */ import com.hypixel.hytale.server.core.asset.type.entityeffect.config.EntityEffect;
/*    */ import com.hypixel.hytale.server.core.entity.InteractionContext;
/*    */ import com.hypixel.hytale.server.core.entity.effect.EffectControllerComponent;
/*    */ import com.hypixel.hytale.server.core.modules.interaction.interaction.CooldownHandler;
/*    */ import com.hypixel.hytale.server.core.modules.interaction.interaction.config.SimpleInstantInteraction;
/*    */ import com.hypixel.hytale.server.core.modules.interaction.interaction.util.InteractionTarget;
/*    */ import com.hypixel.hytale.server.core.universe.world.storage.EntityStore;
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
/*    */ public class ClearEntityEffectInteraction
/*    */   extends SimpleInstantInteraction
/*    */ {
/*    */   public static final BuilderCodec<ClearEntityEffectInteraction> CODEC;
/*    */   protected String entityEffectId;
/*    */   
/*    */   static {
/* 42 */     CODEC = ((BuilderCodec.Builder)((BuilderCodec.Builder)((BuilderCodec.Builder)BuilderCodec.builder(ClearEntityEffectInteraction.class, ClearEntityEffectInteraction::new, SimpleInstantInteraction.CODEC).documentation("Removes the given entity effect from the given entity.")).append(new KeyedCodec("EntityEffectId", (Codec)Codec.STRING), (clearEntityEffectInteraction, string) -> clearEntityEffectInteraction.entityEffectId = string, clearEntityEffectInteraction -> clearEntityEffectInteraction.entityEffectId).addValidator(Validators.nonNull()).addValidatorLate(() -> EntityEffect.VALIDATOR_CACHE.getValidator().late()).add()).appendInherited(new KeyedCodec("Entity", (Codec)InteractionTarget.CODEC), (o, i) -> o.entityTarget = i, o -> o.entityTarget, (o, p) -> o.entityTarget = p.entityTarget).documentation("The entity to target for this interaction.").addValidator(Validators.nonNull()).add()).build();
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   @Nonnull
/* 50 */   private InteractionTarget entityTarget = InteractionTarget.USER;
/*    */ 
/*    */ 
/*    */   
/*    */   protected void firstRun(@Nonnull InteractionType type, @Nonnull InteractionContext context, @Nonnull CooldownHandler cooldownHandler) {
/* 55 */     Ref<EntityStore> ref = context.getEntity();
/* 56 */     Ref<EntityStore> targetRef = this.entityTarget.getEntity(context, ref);
/* 57 */     if (targetRef == null || !targetRef.isValid()) {
/*    */       return;
/*    */     }
/*    */     
/* 61 */     EntityEffect entityEffect = (EntityEffect)EntityEffect.getAssetMap().getAsset(this.entityEffectId);
/* 62 */     if (entityEffect == null) {
/*    */       return;
/*    */     }
/*    */     
/* 66 */     CommandBuffer<EntityStore> commandBuffer = context.getCommandBuffer();
/* 67 */     EffectControllerComponent effectControllerComponent = (EffectControllerComponent)commandBuffer.getComponent(targetRef, EffectControllerComponent.getComponentType());
/* 68 */     if (effectControllerComponent != null) {
/* 69 */       effectControllerComponent.removeEffect(targetRef, EntityEffect.getAssetMap().getIndex(this.entityEffectId), (ComponentAccessor)commandBuffer);
/*    */     }
/*    */   }
/*    */ 
/*    */   
/*    */   @Nonnull
/*    */   protected Interaction generatePacket() {
/* 76 */     return (Interaction)new com.hypixel.hytale.protocol.ClearEntityEffectInteraction();
/*    */   }
/*    */ 
/*    */   
/*    */   protected void configurePacket(Interaction packet) {
/* 81 */     super.configurePacket(packet);
/* 82 */     com.hypixel.hytale.protocol.ClearEntityEffectInteraction p = (com.hypixel.hytale.protocol.ClearEntityEffectInteraction)packet;
/* 83 */     p.effectId = EntityEffect.getAssetMap().getIndex(this.entityEffectId);
/* 84 */     p.entityTarget = this.entityTarget.toProtocol();
/*    */   }
/*    */ 
/*    */   
/*    */   @Nonnull
/*    */   public String toString() {
/* 90 */     return "ClearEntityEffectInteraction{entityEffectId='" + this.entityEffectId + "', entityTarget=" + String.valueOf(this.entityTarget) + "} " + super
/*    */ 
/*    */       
/* 93 */       .toString();
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\server\core\modules\interaction\interaction\config\server\ClearEntityEffectInteraction.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */