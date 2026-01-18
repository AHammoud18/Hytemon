/*     */ package com.hypixel.hytale.server.npc.systems;
/*     */ 
/*     */ import com.hypixel.hytale.component.AddReason;
/*     */ import com.hypixel.hytale.component.CommandBuffer;
/*     */ import com.hypixel.hytale.component.ComponentAccessor;
/*     */ import com.hypixel.hytale.component.ComponentType;
/*     */ import com.hypixel.hytale.component.Ref;
/*     */ import com.hypixel.hytale.component.RemoveReason;
/*     */ import com.hypixel.hytale.component.Store;
/*     */ import com.hypixel.hytale.component.query.Query;
/*     */ import com.hypixel.hytale.component.system.RefSystem;
/*     */ import com.hypixel.hytale.logger.HytaleLogger;
/*     */ import com.hypixel.hytale.server.core.asset.type.entityeffect.config.EntityEffect;
/*     */ import com.hypixel.hytale.server.core.entity.effect.EffectControllerComponent;
/*     */ import com.hypixel.hytale.server.core.universe.world.storage.EntityStore;
/*     */ import com.hypixel.hytale.server.npc.NPCPlugin;
/*     */ import com.hypixel.hytale.server.npc.config.balancing.BalanceAsset;
/*     */ import com.hypixel.hytale.server.npc.entities.NPCEntity;
/*     */ import com.hypixel.hytale.server.npc.role.Role;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class AddSpawnEntityEffectSystem
/*     */   extends RefSystem<EntityStore>
/*     */ {
/*     */   @Nonnull
/*     */   private final ComponentType<EntityStore, NPCEntity> npcComponentType;
/*     */   
/*     */   public AddSpawnEntityEffectSystem(@Nonnull ComponentType<EntityStore, NPCEntity> npcComponentType) {
/* 129 */     this.npcComponentType = npcComponentType;
/*     */   }
/*     */ 
/*     */   
/*     */   public void onEntityAdded(@Nonnull Ref<EntityStore> ref, @Nonnull AddReason reason, @Nonnull Store<EntityStore> store, @Nonnull CommandBuffer<EntityStore> commandBuffer) {
/* 134 */     NPCEntity npcComponent = (NPCEntity)store.getComponent(ref, this.npcComponentType);
/* 135 */     assert npcComponent != null;
/*     */     
/* 137 */     EffectControllerComponent effectController = (EffectControllerComponent)store.getComponent(ref, EffectControllerComponent.getComponentType());
/* 138 */     assert effectController != null;
/*     */     
/* 140 */     Role role = npcComponent.getRole();
/*     */ 
/*     */     
/* 143 */     if (role == null) {
/* 144 */       ((HytaleLogger.Api)((HytaleLogger.Api)NPCPlugin.get().getLogger().atSevere()).withCause(new IllegalStateException("NPC has no role or role index in onLoad!"))).log();
/* 145 */       commandBuffer.removeEntity(ref, RemoveReason.REMOVE);
/*     */       
/*     */       return;
/*     */     } 
/* 149 */     String balanceAssetId = role.getBalanceAsset();
/* 150 */     if (balanceAssetId == null)
/*     */       return; 
/* 152 */     BalanceAsset balanceAsset = (BalanceAsset)BalanceAsset.getAssetMap().getAsset(balanceAssetId);
/* 153 */     String entityEffectId = balanceAsset.getEntityEffect();
/* 154 */     if (entityEffectId == null)
/*     */       return; 
/* 156 */     EntityEffect entityEffect = (EntityEffect)EntityEffect.getAssetMap().getAsset(entityEffectId);
/* 157 */     effectController.addEffect(ref, entityEffect, (ComponentAccessor)commandBuffer);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void onEntityRemove(@Nonnull Ref<EntityStore> ref, @Nonnull RemoveReason reason, @Nonnull Store<EntityStore> store, @Nonnull CommandBuffer<EntityStore> commandBuffer) {}
/*     */ 
/*     */   
/*     */   @Nonnull
/*     */   public Query<EntityStore> getQuery() {
/* 167 */     return (Query<EntityStore>)Query.and(new Query[] { (Query)this.npcComponentType, (Query)EffectControllerComponent.getComponentType() });
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\server\npc\systems\NPCSystems$AddSpawnEntityEffectSystem.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */