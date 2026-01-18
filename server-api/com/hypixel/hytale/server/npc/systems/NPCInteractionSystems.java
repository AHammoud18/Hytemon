/*     */ package com.hypixel.hytale.server.npc.systems;
/*     */ 
/*     */ import com.hypixel.hytale.component.AddReason;
/*     */ import com.hypixel.hytale.component.ArchetypeChunk;
/*     */ import com.hypixel.hytale.component.CommandBuffer;
/*     */ import com.hypixel.hytale.component.Component;
/*     */ import com.hypixel.hytale.component.ComponentType;
/*     */ import com.hypixel.hytale.component.Holder;
/*     */ import com.hypixel.hytale.component.Ref;
/*     */ import com.hypixel.hytale.component.RemoveReason;
/*     */ import com.hypixel.hytale.component.Store;
/*     */ import com.hypixel.hytale.component.query.Query;
/*     */ import com.hypixel.hytale.component.system.HolderSystem;
/*     */ import com.hypixel.hytale.component.system.tick.EntityTickingSystem;
/*     */ import com.hypixel.hytale.protocol.InteractionType;
/*     */ import com.hypixel.hytale.server.core.entity.InteractionManager;
/*     */ import com.hypixel.hytale.server.core.entity.LivingEntity;
/*     */ import com.hypixel.hytale.server.core.inventory.Inventory;
/*     */ import com.hypixel.hytale.server.core.inventory.container.ItemContainer;
/*     */ import com.hypixel.hytale.server.core.modules.interaction.IInteractionSimulationHandler;
/*     */ import com.hypixel.hytale.server.core.modules.interaction.InteractionModule;
/*     */ import com.hypixel.hytale.server.core.universe.world.storage.EntityStore;
/*     */ import com.hypixel.hytale.server.npc.entities.NPCEntity;
/*     */ import com.hypixel.hytale.server.npc.interactions.NPCInteractionSimulationHandler;
/*     */ import javax.annotation.Nonnull;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class NPCInteractionSystems
/*     */ {
/*     */   public static class AddSimulationManagerSystem
/*     */     extends HolderSystem<EntityStore>
/*     */   {
/*     */     @Nonnull
/*     */     private final ComponentType<EntityStore, NPCEntity> npcEntityComponentType;
/*     */     @Nonnull
/*     */     private final Query<EntityStore> query;
/*     */     
/*     */     public AddSimulationManagerSystem(@Nonnull ComponentType<EntityStore, NPCEntity> npcEntityComponentType) {
/*  42 */       this.npcEntityComponentType = npcEntityComponentType;
/*  43 */       this.query = (Query<EntityStore>)Query.and(new Query[] { (Query)npcEntityComponentType, 
/*     */             
/*  45 */             (Query)Query.not((Query)InteractionModule.get().getInteractionManagerComponent()) });
/*     */     }
/*     */ 
/*     */     
/*     */     public void onEntityAdd(@Nonnull Holder<EntityStore> holder, @Nonnull AddReason reason, @Nonnull Store<EntityStore> store) {
/*  50 */       NPCEntity npcComponent = (NPCEntity)holder.getComponent(this.npcEntityComponentType);
/*  51 */       assert npcComponent != null;
/*     */       
/*  53 */       holder.addComponent(InteractionModule.get().getInteractionManagerComponent(), (Component)new InteractionManager((LivingEntity)npcComponent, null, (IInteractionSimulationHandler)new NPCInteractionSimulationHandler()));
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public void onEntityRemoved(@Nonnull Holder<EntityStore> holder, @Nonnull RemoveReason reason, @Nonnull Store<EntityStore> store) {}
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     @Nonnull
/*     */     public Query<EntityStore> getQuery() {
/*  67 */       return this.query;
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static class TickHeldInteractionsSystem
/*     */     extends EntityTickingSystem<EntityStore>
/*     */   {
/*     */     @Nonnull
/*     */     private final ComponentType<EntityStore, NPCEntity> npcComponentType;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     @Nonnull
/*     */     private final ComponentType<EntityStore, InteractionManager> interactionManagerComponentType;
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     @Nonnull
/*     */     private final Query<EntityStore> query;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public TickHeldInteractionsSystem(@Nonnull ComponentType<EntityStore, NPCEntity> npcEntityComponentType) {
/* 100 */       this.npcComponentType = npcEntityComponentType;
/* 101 */       this.interactionManagerComponentType = InteractionModule.get().getInteractionManagerComponent();
/* 102 */       this.query = (Query<EntityStore>)Query.and(new Query[] { (Query)npcEntityComponentType, (Query)this.interactionManagerComponentType });
/*     */     }
/*     */ 
/*     */     
/*     */     public void tick(float dt, int index, @Nonnull ArchetypeChunk<EntityStore> archetypeChunk, @Nonnull Store<EntityStore> store, @Nonnull CommandBuffer<EntityStore> commandBuffer) {
/* 107 */       NPCEntity npcComponent = (NPCEntity)archetypeChunk.getComponent(index, this.npcComponentType);
/* 108 */       assert npcComponent != null;
/*     */       
/* 110 */       InteractionManager interactionManager = (InteractionManager)archetypeChunk.getComponent(index, this.interactionManagerComponentType);
/* 111 */       assert interactionManager != null;
/*     */       
/* 113 */       Inventory inventory = npcComponent.getInventory();
/* 114 */       ItemContainer armorInventory = inventory.getArmor();
/*     */       
/* 116 */       Ref<EntityStore> ref = archetypeChunk.getReferenceTo(index);
/* 117 */       interactionManager.tryRunHeldInteraction(ref, commandBuffer, InteractionType.Held);
/* 118 */       interactionManager.tryRunHeldInteraction(ref, commandBuffer, InteractionType.HeldOffhand);
/*     */       short i;
/* 120 */       for (i = 0; i < armorInventory.getCapacity(); i = (short)(i + 1)) {
/* 121 */         interactionManager.tryRunHeldInteraction(ref, commandBuffer, InteractionType.Equipped, i);
/*     */       }
/*     */     }
/*     */ 
/*     */     
/*     */     @Nonnull
/*     */     public Query<EntityStore> getQuery() {
/* 128 */       return this.query;
/*     */     }
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\server\npc\systems\NPCInteractionSystems.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */