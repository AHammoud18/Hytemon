/*     */ package com.hypixel.hytale.server.core.modules.entitystats;
/*     */ 
/*     */ import com.hypixel.hytale.component.ArchetypeChunk;
/*     */ import com.hypixel.hytale.component.CommandBuffer;
/*     */ import com.hypixel.hytale.component.ComponentType;
/*     */ import com.hypixel.hytale.component.Ref;
/*     */ import com.hypixel.hytale.component.Store;
/*     */ import com.hypixel.hytale.component.SystemGroup;
/*     */ import com.hypixel.hytale.component.dependency.Dependency;
/*     */ import com.hypixel.hytale.component.dependency.Order;
/*     */ import com.hypixel.hytale.component.dependency.SystemDependency;
/*     */ import com.hypixel.hytale.component.dependency.SystemTypeDependency;
/*     */ import com.hypixel.hytale.component.query.Query;
/*     */ import com.hypixel.hytale.component.system.tick.EntityTickingSystem;
/*     */ import com.hypixel.hytale.protocol.ComponentUpdate;
/*     */ import com.hypixel.hytale.protocol.ComponentUpdateType;
/*     */ import com.hypixel.hytale.server.core.modules.entity.tracker.EntityTrackerSystems;
/*     */ import com.hypixel.hytale.server.core.universe.world.storage.EntityStore;
/*     */ import java.util.Map;
/*     */ import java.util.Set;
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
/*     */ public class EntityTrackerUpdate
/*     */   extends EntityTickingSystem<EntityStore>
/*     */ {
/*     */   private final ComponentType<EntityStore, EntityStatMap> componentType;
/* 348 */   private final ComponentType<EntityStore, EntityTrackerSystems.Visible> visibleComponentType = EntityTrackerSystems.Visible.getComponentType();
/*     */   @Nonnull
/*     */   private final Query<EntityStore> query;
/* 351 */   private final Set<Dependency<EntityStore>> dependencies = (Set)Set.of(new SystemDependency(Order.BEFORE, EntityTrackerSystems.EffectControllerSystem.class), new SystemTypeDependency(Order.AFTER, 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */         
/* 357 */         EntityStatsModule.get().getStatModifyingSystemType()));
/*     */ 
/*     */   
/*     */   public EntityTrackerUpdate(ComponentType<EntityStore, EntityStatMap> componentType) {
/* 361 */     this.componentType = componentType;
/* 362 */     this.query = (Query<EntityStore>)Query.and(new Query[] { (Query)this.visibleComponentType, (Query)componentType });
/*     */   }
/*     */ 
/*     */   
/*     */   @Nullable
/*     */   public SystemGroup<EntityStore> getGroup() {
/* 368 */     return EntityTrackerSystems.QUEUE_UPDATE_GROUP;
/*     */   }
/*     */ 
/*     */   
/*     */   @Nonnull
/*     */   public Query<EntityStore> getQuery() {
/* 374 */     return this.query;
/*     */   }
/*     */ 
/*     */   
/*     */   @Nonnull
/*     */   public Set<Dependency<EntityStore>> getDependencies() {
/* 380 */     return this.dependencies;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isParallel(int archetypeChunkSize, int taskCount) {
/* 385 */     return EntityTickingSystem.maybeUseParallel(archetypeChunkSize, taskCount);
/*     */   }
/*     */ 
/*     */   
/*     */   public void tick(float dt, int index, @Nonnull ArchetypeChunk<EntityStore> archetypeChunk, @Nonnull Store<EntityStore> store, @Nonnull CommandBuffer<EntityStore> commandBuffer) {
/* 390 */     Ref<EntityStore> ref = archetypeChunk.getReferenceTo(index);
/* 391 */     EntityTrackerSystems.Visible visible = (EntityTrackerSystems.Visible)archetypeChunk.getComponent(index, this.visibleComponentType);
/* 392 */     EntityStatMap statMap = (EntityStatMap)archetypeChunk.getComponent(index, this.componentType);
/*     */ 
/*     */     
/* 395 */     if (!visible.newlyVisibleTo.isEmpty()) {
/* 396 */       queueUpdatesForNewlyVisible(ref, statMap, visible.newlyVisibleTo);
/*     */     }
/*     */     
/* 399 */     if (statMap.consumeSelfNetworkOutdated()) {
/*     */       
/* 401 */       EntityTrackerSystems.EntityViewer selfEntityViewer = (EntityTrackerSystems.EntityViewer)visible.visibleTo.get(ref);
/* 402 */       if (selfEntityViewer != null && !visible.newlyVisibleTo.containsKey(ref)) {
/* 403 */         ComponentUpdate update = new ComponentUpdate();
/* 404 */         update.type = ComponentUpdateType.EntityStats;
/* 405 */         update.entityStatUpdates = (Map)statMap.consumeSelfUpdates();
/* 406 */         selfEntityViewer.queueUpdate(ref, update);
/*     */       } 
/*     */     } 
/*     */ 
/*     */     
/* 411 */     if (statMap.consumeNetworkOutdated()) {
/* 412 */       ComponentUpdate update = new ComponentUpdate();
/* 413 */       update.type = ComponentUpdateType.EntityStats;
/* 414 */       update.entityStatUpdates = (Map)statMap.consumeOtherUpdates();
/*     */       
/* 416 */       for (Map.Entry<Ref<EntityStore>, EntityTrackerSystems.EntityViewer> entry : (Iterable<Map.Entry<Ref<EntityStore>, EntityTrackerSystems.EntityViewer>>)visible.visibleTo.entrySet()) {
/* 417 */         Ref<EntityStore> viewerRef = entry.getKey();
/*     */ 
/*     */         
/* 420 */         if (visible.newlyVisibleTo.containsKey(viewerRef)) {
/*     */           continue;
/*     */         }
/* 423 */         if (ref.equals(viewerRef))
/*     */           continue; 
/* 425 */         ((EntityTrackerSystems.EntityViewer)entry.getValue()).queueUpdate(ref, update);
/*     */       } 
/*     */     } 
/*     */   }
/*     */   
/*     */   private static void queueUpdatesForNewlyVisible(@Nonnull Ref<EntityStore> ref, @Nonnull EntityStatMap statMap, @Nonnull Map<Ref<EntityStore>, EntityTrackerSystems.EntityViewer> newlyVisibleTo) {
/* 431 */     ComponentUpdate update = new ComponentUpdate();
/* 432 */     update.type = ComponentUpdateType.EntityStats;
/* 433 */     update.entityStatUpdates = (Map)statMap.createInitUpdate(false);
/*     */     
/* 435 */     for (Map.Entry<Ref<EntityStore>, EntityTrackerSystems.EntityViewer> entry : newlyVisibleTo.entrySet()) {
/*     */       
/* 437 */       if (ref.equals(entry.getKey())) {
/* 438 */         queueUpdateForNewlyVisibleSelf(ref, statMap, entry.getValue());
/*     */         
/*     */         continue;
/*     */       } 
/* 442 */       ((EntityTrackerSystems.EntityViewer)entry.getValue()).queueUpdate(ref, update);
/*     */     } 
/*     */   }
/*     */   
/*     */   private static void queueUpdateForNewlyVisibleSelf(Ref<EntityStore> ref, @Nonnull EntityStatMap statMap, @Nonnull EntityTrackerSystems.EntityViewer viewer) {
/* 447 */     ComponentUpdate update = new ComponentUpdate();
/* 448 */     update.type = ComponentUpdateType.EntityStats;
/* 449 */     update.entityStatUpdates = (Map)statMap.createInitUpdate(true);
/*     */     
/* 451 */     viewer.queueUpdate(ref, update);
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\server\core\modules\entitystats\EntityStatsSystems$EntityTrackerUpdate.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */