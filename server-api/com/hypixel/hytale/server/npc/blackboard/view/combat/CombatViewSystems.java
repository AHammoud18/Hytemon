/*     */ package com.hypixel.hytale.server.npc.blackboard.view.combat;
/*     */ import com.hypixel.hytale.assetstore.map.IndexedLookupTableAssetMap;
/*     */ import com.hypixel.hytale.component.AddReason;
/*     */ import com.hypixel.hytale.component.ArchetypeChunk;
/*     */ import com.hypixel.hytale.component.Component;
/*     */ import com.hypixel.hytale.component.ComponentType;
/*     */ import com.hypixel.hytale.component.Holder;
/*     */ import com.hypixel.hytale.component.Ref;
/*     */ import com.hypixel.hytale.component.RemoveReason;
/*     */ import com.hypixel.hytale.component.Resource;
/*     */ import com.hypixel.hytale.component.ResourceType;
/*     */ import com.hypixel.hytale.component.Store;
/*     */ import com.hypixel.hytale.component.query.Query;
/*     */ import com.hypixel.hytale.server.core.entity.InteractionChain;
/*     */ import com.hypixel.hytale.server.core.entity.InteractionManager;
/*     */ import com.hypixel.hytale.server.core.entity.LivingEntity;
/*     */ import com.hypixel.hytale.server.core.modules.interaction.interaction.config.Interaction;
/*     */ import com.hypixel.hytale.server.core.modules.interaction.interaction.config.RootInteraction;
/*     */ import com.hypixel.hytale.server.core.universe.world.storage.EntityStore;
/*     */ import com.hypixel.hytale.server.npc.NPCPlugin;
/*     */ import com.hypixel.hytale.server.npc.role.support.CombatSupport;
/*     */ import java.util.ArrayDeque;
/*     */ import java.util.List;
/*     */ import java.util.Set;
/*     */ import javax.annotation.Nonnull;
/*     */ 
/*     */ public class CombatViewSystems {
/*     */   public static class Ensure extends HolderSystem<EntityStore> {
/*     */     private final ComponentType<EntityStore, CombatViewSystems.CombatData> combatDataComponentType;
/*     */     
/*     */     public Ensure(ComponentType<EntityStore, CombatViewSystems.CombatData> combatDataComponentType) {
/*  32 */       this.combatDataComponentType = combatDataComponentType;
/*     */     }
/*     */ 
/*     */     
/*     */     @Nonnull
/*     */     public Query<EntityStore> getQuery() {
/*  38 */       return (Query<EntityStore>)AllLegacyEntityTypesQuery.INSTANCE;
/*     */     }
/*     */ 
/*     */     
/*     */     public void onEntityAdd(@Nonnull Holder<EntityStore> holder, @Nonnull AddReason reason, @Nonnull Store<EntityStore> store) {
/*  43 */       holder.ensureComponent(this.combatDataComponentType);
/*     */     }
/*     */     
/*     */     public void onEntityRemoved(@Nonnull Holder<EntityStore> holder, @Nonnull RemoveReason reason, @Nonnull Store<EntityStore> store) {}
/*     */   }
/*     */   
/*     */   public static class EntityRemoved
/*     */     extends HolderSystem<EntityStore>
/*     */   {
/*     */     private final ComponentType<EntityStore, CombatViewSystems.CombatData> combatDataComponentType;
/*     */     private final ResourceType<EntityStore, CombatViewSystems.CombatDataPool> dataPoolResourceType;
/*     */     
/*     */     public EntityRemoved(ComponentType<EntityStore, CombatViewSystems.CombatData> combatDataComponentType, ResourceType<EntityStore, CombatViewSystems.CombatDataPool> dataPoolResourceType) {
/*  56 */       this.combatDataComponentType = combatDataComponentType;
/*  57 */       this.dataPoolResourceType = dataPoolResourceType;
/*     */     }
/*     */ 
/*     */     
/*     */     public Query<EntityStore> getQuery() {
/*  62 */       return (Query)this.combatDataComponentType;
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     public void onEntityAdd(@Nonnull Holder<EntityStore> holder, @Nonnull AddReason reason, @Nonnull Store<EntityStore> store) {}
/*     */ 
/*     */ 
/*     */     
/*     */     public void onEntityRemoved(@Nonnull Holder<EntityStore> holder, @Nonnull RemoveReason reason, @Nonnull Store<EntityStore> store) {
/*  72 */       CombatViewSystems.CombatData combatData = (CombatViewSystems.CombatData)holder.getComponent(this.combatDataComponentType);
/*  73 */       CombatViewSystems.CombatDataPool dataPool = (CombatViewSystems.CombatDataPool)store.getResource(this.dataPoolResourceType);
/*  74 */       CombatViewSystems.clearCombatData(combatData, dataPool);
/*     */     }
/*     */   }
/*     */   
/*     */   public static class Ticking extends EntityTickingSystem<EntityStore> {
/*     */     private final ComponentType<EntityStore, CombatViewSystems.CombatData> combatDataComponentType;
/*     */     private final ResourceType<EntityStore, CombatViewSystems.CombatDataPool> dataPoolResourceType;
/*     */     
/*     */     public Ticking(ComponentType<EntityStore, CombatViewSystems.CombatData> combatDataComponentType, ResourceType<EntityStore, CombatViewSystems.CombatDataPool> dataPoolResourceType) {
/*  83 */       this.combatDataComponentType = combatDataComponentType;
/*  84 */       this.dataPoolResourceType = dataPoolResourceType;
/*     */     }
/*     */ 
/*     */     
/*     */     public Query<EntityStore> getQuery() {
/*  89 */       return (Query)this.combatDataComponentType;
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     public boolean isParallel(int archetypeChunkSize, int taskCount) {
/*  95 */       return false;
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     public void tick(float dt, int index, @Nonnull ArchetypeChunk<EntityStore> archetypeChunk, @Nonnull Store<EntityStore> store, @Nonnull CommandBuffer<EntityStore> commandBuffer) {
/* 101 */       CombatViewSystems.CombatData combatData = (CombatViewSystems.CombatData)archetypeChunk.getComponent(index, this.combatDataComponentType);
/* 102 */       CombatViewSystems.CombatDataPool dataPool = (CombatViewSystems.CombatDataPool)store.getResource(this.dataPoolResourceType);
/* 103 */       CombatViewSystems.clearCombatData(combatData, dataPool);
/*     */     }
/*     */   }
/*     */   
/*     */   private static void clearCombatData(@Nonnull CombatData combatData, @Nonnull CombatDataPool dataPool) {
/* 108 */     if (!combatData.interpreted)
/*     */       return; 
/* 110 */     List<InterpretedCombatData> dataList = combatData.combatData;
/* 111 */     for (int i = 0; i < dataList.size(); i++) {
/* 112 */       dataPool.releaseCombatData(dataList.get(i));
/*     */     }
/* 114 */     dataList.clear();
/* 115 */     combatData.interpreted = false;
/*     */   }
/*     */   
/*     */   @Nonnull
/*     */   public static List<InterpretedCombatData> getCombatData(@Nonnull Ref<EntityStore> reference) {
/* 120 */     CombatData combatData = (CombatData)reference.getStore().getComponent(reference, CombatData.getComponentType());
/* 121 */     if (combatData.interpreted) {
/* 122 */       return combatData.unmodifiableCombatData;
/*     */     }
/* 124 */     InteractionManager interactionManager = (InteractionManager)reference.getStore().getComponent(reference, InteractionModule.get().getInteractionManagerComponent());
/*     */     
/* 126 */     CombatDataPool combatDataPool = (CombatDataPool)reference.getStore().getResource(CombatDataPool.getResourceType());
/* 127 */     List<InterpretedCombatData> dataList = combatData.combatData;
/*     */     
/* 129 */     IndexedLookupTableAssetMap<String, RootInteraction> interactionAssetMap = RootInteraction.getAssetMap();
/* 130 */     Set<String> attackInteractions = interactionAssetMap.getKeysForTag(CombatSupport.ATTACK_TAG_INDEX);
/* 131 */     Set<String> meleeInteractions = interactionAssetMap.getKeysForTag(CombatSupport.MELEE_TAG_INDEX);
/* 132 */     Set<String> rangedInteractions = interactionAssetMap.getKeysForTag(CombatSupport.RANGED_TAG_INDEX);
/* 133 */     Set<String> blockInteractions = interactionAssetMap.getKeysForTag(CombatSupport.BLOCK_TAG_INDEX);
/*     */     
/* 135 */     LivingEntity entity = (LivingEntity)EntityUtils.getEntity(reference, (ComponentAccessor)reference.getStore());
/*     */ 
/*     */     
/* 138 */     interactionManager.forEachInteraction((chain, interaction, list) -> { String rootId = chain.getRootInteraction().getId(); if (!attackInteractions.contains(rootId)) return list;  InterpretedCombatData entry = combatDataPool.getEmptyCombatData(); entry.setAttack(rootId); entry.setCurrentElapsedTime(chain.getTimeInSeconds()); entry.setCharging(interaction instanceof com.hypixel.hytale.server.core.modules.interaction.interaction.config.client.ChargingInteraction); entry.setPerformingMeleeAttack(meleeInteractions.contains(rootId)); entry.setPerformingRangedAttack(rangedInteractions.contains(rootId)); entry.setPerformingBlock(blockInteractions.contains(rootId)); list.add(entry); return list; }dataList);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 153 */     combatData.interpreted = true;
/*     */     
/* 155 */     return combatData.unmodifiableCombatData;
/*     */   }
/*     */   
/*     */   public static class CombatData implements Component<EntityStore> {
/*     */     public static ComponentType<EntityStore, CombatData> getComponentType() {
/* 160 */       return NPCPlugin.get().getCombatDataComponentType();
/*     */     }
/*     */     
/* 163 */     private final List<InterpretedCombatData> combatData = (List<InterpretedCombatData>)new ObjectArrayList();
/* 164 */     private final List<InterpretedCombatData> unmodifiableCombatData = Collections.unmodifiableList(this.combatData);
/*     */     
/*     */     private boolean interpreted;
/*     */ 
/*     */     
/*     */     @Nonnull
/*     */     public Component<EntityStore> clone() {
/* 171 */       CombatData data = new CombatData();
/* 172 */       data.interpreted = this.interpreted;
/* 173 */       for (int i = 0; i < this.combatData.size(); i++) {
/* 174 */         data.combatData.add(((InterpretedCombatData)this.combatData.get(i)).clone());
/*     */       }
/* 176 */       return data;
/*     */     }
/*     */   }
/*     */   
/*     */   public static class CombatDataPool
/*     */     implements Resource<EntityStore> {
/*     */     private final ArrayDeque<InterpretedCombatData> combatDataPool;
/*     */     
/*     */     public CombatDataPool() {
/* 185 */       this.combatDataPool = new ArrayDeque<>();
/*     */     } public static ResourceType<EntityStore, CombatDataPool> getResourceType() {
/*     */       return NPCPlugin.get().getCombatDataPoolResourceType();
/*     */     }
/*     */     @Nonnull
/*     */     public Resource<EntityStore> clone() {
/* 191 */       return new CombatDataPool();
/*     */     }
/*     */     
/*     */     public InterpretedCombatData getEmptyCombatData() {
/* 195 */       if (this.combatDataPool.isEmpty()) {
/* 196 */         return new InterpretedCombatData();
/*     */       }
/* 198 */       return this.combatDataPool.poll();
/*     */     }
/*     */     
/*     */     public void releaseCombatData(@Nonnull InterpretedCombatData combatData) {
/* 202 */       this.combatDataPool.push(combatData);
/*     */     }
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\server\npc\blackboard\view\combat\CombatViewSystems.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */