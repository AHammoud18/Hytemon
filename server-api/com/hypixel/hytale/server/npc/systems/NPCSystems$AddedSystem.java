/*     */ package com.hypixel.hytale.server.npc.systems;
/*     */ 
/*     */ import com.hypixel.hytale.component.AddReason;
/*     */ import com.hypixel.hytale.component.CommandBuffer;
/*     */ import com.hypixel.hytale.component.Component;
/*     */ import com.hypixel.hytale.component.ComponentAccessor;
/*     */ import com.hypixel.hytale.component.ComponentType;
/*     */ import com.hypixel.hytale.component.Ref;
/*     */ import com.hypixel.hytale.component.RemoveReason;
/*     */ import com.hypixel.hytale.component.Store;
/*     */ import com.hypixel.hytale.component.query.Query;
/*     */ import com.hypixel.hytale.component.system.RefSystem;
/*     */ import com.hypixel.hytale.logger.HytaleLogger;
/*     */ import com.hypixel.hytale.server.core.modules.entity.component.MovementAudioComponent;
/*     */ import com.hypixel.hytale.server.core.modules.entity.component.NewSpawnComponent;
/*     */ import com.hypixel.hytale.server.core.modules.entity.component.PositionDataComponent;
/*     */ import com.hypixel.hytale.server.core.prefab.PrefabCopyableComponent;
/*     */ import com.hypixel.hytale.server.core.universe.world.storage.EntityStore;
/*     */ import com.hypixel.hytale.server.npc.NPCPlugin;
/*     */ import com.hypixel.hytale.server.npc.blackboard.view.blocktype.BlockTypeView;
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
/*     */ public class AddedSystem
/*     */   extends RefSystem<EntityStore>
/*     */ {
/*     */   @Nonnull
/*     */   private final ComponentType<EntityStore, NPCEntity> npcComponentType;
/*     */   
/*     */   public AddedSystem(@Nonnull ComponentType<EntityStore, NPCEntity> npcComponentType) {
/*  61 */     this.npcComponentType = npcComponentType;
/*     */   }
/*     */ 
/*     */   
/*     */   public void onEntityAdded(@Nonnull Ref<EntityStore> ref, @Nonnull AddReason reason, @Nonnull Store<EntityStore> store, @Nonnull CommandBuffer<EntityStore> commandBuffer) {
/*  66 */     NPCEntity npcComponent = (NPCEntity)store.getComponent(ref, this.npcComponentType);
/*  67 */     assert npcComponent != null;
/*     */     
/*  69 */     Role role = npcComponent.getRole();
/*     */ 
/*     */     
/*  72 */     if (role == null) {
/*  73 */       ((HytaleLogger.Api)((HytaleLogger.Api)NPCPlugin.get().getLogger().atSevere()).withCause(new IllegalStateException("NPC has no role or role index in onLoad!"))).log();
/*  74 */       commandBuffer.removeEntity(ref, RemoveReason.REMOVE);
/*     */       
/*     */       return;
/*     */     } 
/*  78 */     npcComponent.initBlockChangeBlackboardView(ref, (ComponentAccessor)commandBuffer);
/*  79 */     role.loaded();
/*     */     
/*  81 */     commandBuffer.ensureComponent(ref, PrefabCopyableComponent.getComponentType());
/*     */     
/*  83 */     commandBuffer.ensureComponent(ref, PositionDataComponent.getComponentType());
/*  84 */     commandBuffer.ensureComponent(ref, MovementAudioComponent.getComponentType());
/*     */     
/*  86 */     if (reason == AddReason.SPAWN) {
/*  87 */       NewSpawnComponent newSpawnComponent = new NewSpawnComponent(role.getSpawnLockTime());
/*  88 */       commandBuffer.addComponent(ref, NewSpawnComponent.getComponentType(), (Component)newSpawnComponent);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public void onEntityRemove(@Nonnull Ref<EntityStore> ref, @Nonnull RemoveReason reason, @Nonnull Store<EntityStore> store, @Nonnull CommandBuffer<EntityStore> commandBuffer) {
/*  94 */     NPCEntity npcComponent = (NPCEntity)store.getComponent(ref, this.npcComponentType);
/*  95 */     assert npcComponent != null;
/*     */     
/*  97 */     BlockTypeView blockTypeView = npcComponent.removeBlockTypeBlackboardView();
/*  98 */     if (blockTypeView != null) {
/*  99 */       blockTypeView.removeSearchedBlockSets(ref, npcComponent, npcComponent.getBlackboardBlockTypeSets());
/*     */     }
/*     */     
/* 102 */     switch (NPCSystems.null.$SwitchMap$com$hypixel$hytale$component$RemoveReason[reason.ordinal()]) { case 1:
/* 103 */         npcComponent.getRole().removed(); break;
/* 104 */       case 2: npcComponent.getRole().unloaded();
/*     */         break; }
/*     */   
/*     */   }
/*     */   
/*     */   @Nonnull
/*     */   public Query<EntityStore> getQuery() {
/* 111 */     return (Query)this.npcComponentType;
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\server\npc\systems\NPCSystems$AddedSystem.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */