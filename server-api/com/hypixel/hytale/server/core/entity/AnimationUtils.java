/*     */ package com.hypixel.hytale.server.core.entity;
/*     */ 
/*     */ import com.hypixel.hytale.component.ComponentAccessor;
/*     */ import com.hypixel.hytale.component.Ref;
/*     */ import com.hypixel.hytale.logger.HytaleLogger;
/*     */ import com.hypixel.hytale.protocol.AnimationSlot;
/*     */ import com.hypixel.hytale.protocol.Packet;
/*     */ import com.hypixel.hytale.protocol.packets.entities.PlayAnimation;
/*     */ import com.hypixel.hytale.server.core.asset.type.itemanimation.config.ItemPlayerAnimations;
/*     */ import com.hypixel.hytale.server.core.asset.type.model.config.Model;
/*     */ import com.hypixel.hytale.server.core.modules.entity.component.ModelComponent;
/*     */ import com.hypixel.hytale.server.core.modules.entity.tracker.NetworkId;
/*     */ import com.hypixel.hytale.server.core.universe.PlayerRef;
/*     */ import com.hypixel.hytale.server.core.universe.world.PlayerUtil;
/*     */ import com.hypixel.hytale.server.core.universe.world.storage.EntityStore;
/*     */ import java.util.concurrent.TimeUnit;
/*     */ import java.util.logging.Level;
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
/*     */ public class AnimationUtils
/*     */ {
/*     */   public static void playAnimation(@Nonnull Ref<EntityStore> ref, @Nonnull AnimationSlot animationSlot, @Nullable String animationId, boolean sendToSelf, @Nonnull ComponentAccessor<EntityStore> componentAccessor) {
/*  39 */     playAnimation(ref, animationSlot, null, animationId, sendToSelf, componentAccessor);
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static void playAnimation(@Nonnull Ref<EntityStore> ref, @Nonnull AnimationSlot animationSlot, @Nullable String itemAnimationsId, @Nullable String animationId, boolean sendToSelf, @Nonnull ComponentAccessor<EntityStore> componentAccessor) {
/*  60 */     Model model = null;
/*  61 */     ModelComponent modelComponent = (ModelComponent)componentAccessor.getComponent(ref, ModelComponent.getComponentType());
/*  62 */     if (modelComponent != null) {
/*  63 */       model = modelComponent.getModel();
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*  68 */     if (animationSlot != AnimationSlot.Action && animationId != null && model != null && !model.getAnimationSetMap().containsKey(animationId)) {
/*  69 */       ((HytaleLogger.Api)Entity.LOGGER.at(Level.WARNING).atMostEvery(1, TimeUnit.MINUTES)).log("Missing animation '%s' for Model '%s'", animationId, model.getModelAssetId());
/*     */       
/*     */       return;
/*     */     } 
/*  73 */     NetworkId networkIdComponent = (NetworkId)componentAccessor.getComponent(ref, NetworkId.getComponentType());
/*  74 */     assert networkIdComponent != null;
/*     */ 
/*     */ 
/*     */     
/*  78 */     PlayAnimation animationPacket = new PlayAnimation(networkIdComponent.getId(), itemAnimationsId, animationId, animationSlot); if (sendToSelf) {
/*  79 */       PlayerUtil.forEachPlayerThatCanSeeEntity(ref, (playerRef, playerRefComponent, ca) -> playerRefComponent.getPacketHandler().writeNoCache((Packet)animationPacket), componentAccessor);
/*     */     } else {
/*     */       
/*  82 */       PlayerUtil.forEachPlayerThatCanSeeEntity(ref, (playerRef, playerRefComponent, ca) -> playerRefComponent.getPacketHandler().writeNoCache((Packet)animationPacket), ref, componentAccessor);
/*     */     } 
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
/*     */ 
/*     */ 
/*     */   
/*     */   public static void playAnimation(@Nonnull Ref<EntityStore> ref, @Nonnull AnimationSlot animationSlot, @Nonnull String itemAnimationsId, @Nonnull String animationId, @Nonnull ComponentAccessor<EntityStore> componentAccessor) {
/* 101 */     playAnimation(ref, animationSlot, itemAnimationsId, animationId, false, componentAccessor);
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
/*     */ 
/*     */   
/*     */   public static void playAnimation(@Nonnull Ref<EntityStore> ref, @Nonnull AnimationSlot animationSlot, @Nullable ItemPlayerAnimations itemAnimations, @Nonnull String animationId, @Nonnull ComponentAccessor<EntityStore> componentAccessor) {
/* 118 */     String itemAnimationsId = (itemAnimations != null) ? itemAnimations.getId() : null;
/* 119 */     playAnimation(ref, animationSlot, itemAnimationsId, animationId, false, componentAccessor);
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
/*     */   public static void stopAnimation(@Nonnull Ref<EntityStore> ref, @Nonnull AnimationSlot animationSlot, @Nonnull ComponentAccessor<EntityStore> componentAccessor) {
/* 132 */     stopAnimation(ref, animationSlot, false, componentAccessor);
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
/*     */   public static void stopAnimation(@Nonnull Ref<EntityStore> ref, @Nonnull AnimationSlot animationSlot, boolean sendToSelf, @Nonnull ComponentAccessor<EntityStore> componentAccessor) {
/* 147 */     NetworkId networkIdComponent = (NetworkId)componentAccessor.getComponent(ref, NetworkId.getComponentType());
/* 148 */     assert networkIdComponent != null;
/*     */ 
/*     */ 
/*     */     
/* 152 */     PlayAnimation animationPacket = new PlayAnimation(networkIdComponent.getId(), null, null, animationSlot); if (sendToSelf) {
/* 153 */       PlayerUtil.forEachPlayerThatCanSeeEntity(ref, (playerRef, playerRefComponent, ca) -> playerRefComponent.getPacketHandler().write((Packet)animationPacket), componentAccessor);
/*     */     } else {
/*     */       
/* 156 */       PlayerUtil.forEachPlayerThatCanSeeEntity(ref, (playerRef, playerRefComponent, ca) -> playerRefComponent.getPacketHandler().write((Packet)animationPacket), ref, componentAccessor);
/*     */     } 
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
/*     */   
/*     */   public static void playAnimation(@Nonnull Ref<EntityStore> ref, @Nonnull AnimationSlot animationSlot, @Nullable String animationId, @Nonnull ComponentAccessor<EntityStore> componentAccessor) {
/* 173 */     playAnimation(ref, animationSlot, animationId, false, componentAccessor);
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\server\core\entity\AnimationUtils.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */