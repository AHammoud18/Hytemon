/*     */ package com.hypixel.hytale.server.core.modules.interaction.interaction.config.none;
/*     */ 
/*     */ import com.hypixel.hytale.assetstore.map.IndexedLookupTableAssetMap;
/*     */ import com.hypixel.hytale.codec.Codec;
/*     */ import com.hypixel.hytale.codec.KeyedCodec;
/*     */ import com.hypixel.hytale.codec.builder.BuilderCodec;
/*     */ import com.hypixel.hytale.codec.codecs.EnumCodec;
/*     */ import com.hypixel.hytale.codec.validation.Validator;
/*     */ import com.hypixel.hytale.codec.validation.Validators;
/*     */ import com.hypixel.hytale.common.util.ArrayUtil;
/*     */ import com.hypixel.hytale.component.CommandBuffer;
/*     */ import com.hypixel.hytale.component.Ref;
/*     */ import com.hypixel.hytale.protocol.Interaction;
/*     */ import com.hypixel.hytale.protocol.InteractionState;
/*     */ import com.hypixel.hytale.protocol.InteractionType;
/*     */ import com.hypixel.hytale.protocol.Match;
/*     */ import com.hypixel.hytale.server.core.asset.type.entityeffect.config.EntityEffect;
/*     */ import com.hypixel.hytale.server.core.entity.InteractionContext;
/*     */ import com.hypixel.hytale.server.core.entity.effect.ActiveEntityEffect;
/*     */ import com.hypixel.hytale.server.core.entity.effect.EffectControllerComponent;
/*     */ import com.hypixel.hytale.server.core.modules.interaction.interaction.CooldownHandler;
/*     */ import com.hypixel.hytale.server.core.modules.interaction.interaction.config.SimpleInstantInteraction;
/*     */ import com.hypixel.hytale.server.core.modules.interaction.interaction.util.InteractionTarget;
/*     */ import com.hypixel.hytale.server.core.universe.world.storage.EntityStore;
/*     */ import it.unimi.dsi.fastutil.ints.Int2ObjectMap;
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
/*     */ public class EffectConditionInteraction
/*     */   extends SimpleInstantInteraction
/*     */ {
/*     */   @Nonnull
/*     */   public static final BuilderCodec<EffectConditionInteraction> CODEC;
/*     */   protected String[] entityEffectIds;
/*     */   protected int[] entityEffectIndexes;
/*     */   
/*     */   static {
/*  62 */     CODEC = ((BuilderCodec.Builder)((BuilderCodec.Builder)((BuilderCodec.Builder)((BuilderCodec.Builder)((BuilderCodec.Builder)BuilderCodec.builder(EffectConditionInteraction.class, EffectConditionInteraction::new, SimpleInstantInteraction.CODEC).documentation("An interaction that is successful if the given effects exist (or don't) on the target entity.")).append(new KeyedCodec("EntityEffectIds", (Codec)Codec.STRING_ARRAY), (effectConditionInteraction, strings) -> effectConditionInteraction.entityEffectIds = strings, effectConditionInteraction -> effectConditionInteraction.entityEffectIds).addValidator(Validators.nonNull()).addValidator(Validators.nonEmptyArray()).addValidator((Validator)EntityEffect.VALIDATOR_CACHE.getArrayValidator()).add()).append(new KeyedCodec("Match", (Codec)new EnumCodec(Match.class)), (effectConditionInteraction, aBoolean) -> effectConditionInteraction.match = aBoolean, effectConditionInteraction -> effectConditionInteraction.match).documentation("Field to specify whether the entity must have the specified effects (All), or must not have the specified effects (None). Default value is: All.").add()).appendInherited(new KeyedCodec("Entity", (Codec)InteractionTarget.CODEC), (o, i) -> o.entityTarget = i, o -> o.entityTarget, (o, p) -> o.entityTarget = p.entityTarget).documentation("The entity to target for this interaction.").addValidator(Validators.nonNull()).add()).afterDecode(effectConditionInteraction -> effectConditionInteraction.entityEffectIndexes = resolveEntityEffects(effectConditionInteraction.entityEffectIds))).build();
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
/*     */   @Nonnull
/*  77 */   protected Match match = Match.All;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Nonnull
/*  83 */   private InteractionTarget entityTarget = InteractionTarget.USER;
/*     */ 
/*     */ 
/*     */   
/*     */   protected void firstRun(@Nonnull InteractionType type, @Nonnull InteractionContext context, @Nonnull CooldownHandler cooldownHandler) {
/*  88 */     CommandBuffer<EntityStore> commandBuffer = context.getCommandBuffer();
/*  89 */     assert commandBuffer != null;
/*     */     
/*  91 */     Ref<EntityStore> ref = context.getEntity();
/*  92 */     Ref<EntityStore> targetRef = this.entityTarget.getEntity(context, ref);
/*  93 */     if (targetRef == null || !targetRef.isValid()) {
/*     */       return;
/*     */     }
/*     */     
/*  97 */     EffectControllerComponent effectControllerComponent = (EffectControllerComponent)commandBuffer.getComponent(targetRef, EffectControllerComponent.getComponentType());
/*  98 */     if (effectControllerComponent == null) {
/*     */       return;
/*     */     }
/*     */     
/* 102 */     Int2ObjectMap<ActiveEntityEffect> activeEffects = effectControllerComponent.getActiveEffects();
/* 103 */     for (int i = 0; i < this.entityEffectIndexes.length; i++) {
/* 104 */       switch (this.match) {
/*     */         case All:
/* 106 */           if (!activeEffects.containsKey(this.entityEffectIndexes[i])) {
/* 107 */             (context.getState()).state = InteractionState.Failed;
/*     */             return;
/*     */           } 
/*     */           break;
/*     */         case None:
/* 112 */           if (activeEffects.containsKey(this.entityEffectIndexes[i])) {
/* 113 */             (context.getState()).state = InteractionState.Failed;
/*     */             return;
/*     */           } 
/*     */           break;
/*     */       } 
/*     */     } 
/*     */   }
/*     */   
/*     */   private static int[] resolveEntityEffects(@Nullable String[] entityEffectIds) {
/* 122 */     if (entityEffectIds == null) return ArrayUtil.EMPTY_INT_ARRAY;
/*     */     
/* 124 */     IndexedLookupTableAssetMap<String, EntityEffect> entityEffectAssetMap = EntityEffect.getAssetMap();
/*     */     
/* 126 */     int[] entityEffectIndexes = new int[entityEffectIds.length];
/* 127 */     for (int i = 0; i < entityEffectIds.length; i++) {
/* 128 */       entityEffectIndexes[i] = entityEffectAssetMap.getIndex(entityEffectIds[i]);
/*     */     }
/* 130 */     return entityEffectIndexes;
/*     */   }
/*     */ 
/*     */   
/*     */   @Nonnull
/*     */   protected Interaction generatePacket() {
/* 136 */     return (Interaction)new com.hypixel.hytale.protocol.EffectConditionInteraction();
/*     */   }
/*     */ 
/*     */   
/*     */   protected void configurePacket(Interaction packet) {
/* 141 */     super.configurePacket(packet);
/* 142 */     com.hypixel.hytale.protocol.EffectConditionInteraction p = (com.hypixel.hytale.protocol.EffectConditionInteraction)packet;
/* 143 */     p.entityEffects = this.entityEffectIndexes;
/* 144 */     p.match = this.match;
/* 145 */     p.entityTarget = this.entityTarget.toProtocol();
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   @Nonnull
/*     */   public String toString() {
/* 152 */     return "EffectConditionInteraction{entityEffectIds=" + Arrays.toString((Object[])this.entityEffectIds) + ", entityEffectIndexes=" + 
/* 153 */       Arrays.toString(this.entityEffectIndexes) + ", match=" + String.valueOf(this.match) + ", entityTarget=" + String.valueOf(this.entityTarget) + "} " + super
/*     */ 
/*     */       
/* 156 */       .toString();
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\server\core\modules\interaction\interaction\config\none\EffectConditionInteraction.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */