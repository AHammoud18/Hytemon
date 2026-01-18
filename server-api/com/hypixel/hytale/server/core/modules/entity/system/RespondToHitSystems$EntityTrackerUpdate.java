/*     */ package com.hypixel.hytale.server.core.modules.entity.system;
/*     */ 
/*     */ import com.hypixel.hytale.component.ArchetypeChunk;
/*     */ import com.hypixel.hytale.component.CommandBuffer;
/*     */ import com.hypixel.hytale.component.ComponentType;
/*     */ import com.hypixel.hytale.component.Ref;
/*     */ import com.hypixel.hytale.component.Store;
/*     */ import com.hypixel.hytale.component.SystemGroup;
/*     */ import com.hypixel.hytale.component.query.Query;
/*     */ import com.hypixel.hytale.component.system.tick.EntityTickingSystem;
/*     */ import com.hypixel.hytale.protocol.ComponentUpdate;
/*     */ import com.hypixel.hytale.protocol.ComponentUpdateType;
/*     */ import com.hypixel.hytale.server.core.modules.entity.component.RespondToHit;
/*     */ import com.hypixel.hytale.server.core.modules.entity.tracker.EntityTrackerSystems;
/*     */ import com.hypixel.hytale.server.core.universe.world.storage.EntityStore;
/*     */ import java.util.Map;
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
/*     */ public class EntityTrackerUpdate
/*     */   extends EntityTickingSystem<EntityStore>
/*     */ {
/*     */   private final ComponentType<EntityStore, EntityTrackerSystems.Visible> componentType;
/*     */   @Nonnull
/*     */   private final Query<EntityStore> query;
/*     */   
/*     */   public EntityTrackerUpdate(ComponentType<EntityStore, EntityTrackerSystems.Visible> componentType) {
/*  54 */     this.componentType = componentType;
/*  55 */     this.query = (Query<EntityStore>)Query.and(new Query[] { (Query)componentType, (Query)RespondToHit.getComponentType() });
/*     */   }
/*     */ 
/*     */   
/*     */   @Nullable
/*     */   public SystemGroup<EntityStore> getGroup() {
/*  61 */     return EntityTrackerSystems.QUEUE_UPDATE_GROUP;
/*     */   }
/*     */ 
/*     */   
/*     */   @Nonnull
/*     */   public Query<EntityStore> getQuery() {
/*  67 */     return this.query;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isParallel(int archetypeChunkSize, int taskCount) {
/*  72 */     return EntityTickingSystem.maybeUseParallel(archetypeChunkSize, taskCount);
/*     */   }
/*     */ 
/*     */   
/*     */   public void tick(float dt, int systemIndex, @Nonnull Store<EntityStore> store) {
/*  77 */     super.tick(dt, systemIndex, store);
/*     */ 
/*     */     
/*  80 */     ((RespondToHitSystems.QueueResource)store.getResource(RespondToHitSystems.QueueResource.getResourceType())).queue.clear();
/*     */   }
/*     */ 
/*     */   
/*     */   public void tick(float dt, int index, @Nonnull ArchetypeChunk<EntityStore> archetypeChunk, @Nonnull Store<EntityStore> store, @Nonnull CommandBuffer<EntityStore> commandBuffer) {
/*  85 */     EntityTrackerSystems.Visible visible = (EntityTrackerSystems.Visible)archetypeChunk.getComponent(index, this.componentType);
/*     */     
/*  87 */     Ref<EntityStore> ref = archetypeChunk.getReferenceTo(index);
/*     */ 
/*     */     
/*  90 */     if (((RespondToHitSystems.QueueResource)commandBuffer.getResource(RespondToHitSystems.QueueResource.getResourceType())).queue.remove(ref)) {
/*  91 */       queueUpdatesFor(ref, visible.visibleTo);
/*  92 */     } else if (!visible.newlyVisibleTo.isEmpty()) {
/*  93 */       queueUpdatesFor(ref, visible.newlyVisibleTo);
/*     */     } 
/*     */   }
/*     */   
/*     */   private static void queueUpdatesFor(Ref<EntityStore> ref, @Nonnull Map<Ref<EntityStore>, EntityTrackerSystems.EntityViewer> visibleTo) {
/*  98 */     ComponentUpdate update = new ComponentUpdate();
/*  99 */     update.type = ComponentUpdateType.RespondToHit;
/* 100 */     for (EntityTrackerSystems.EntityViewer viewer : visibleTo.values())
/* 101 */       viewer.queueUpdate(ref, update); 
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\server\core\modules\entity\system\RespondToHitSystems$EntityTrackerUpdate.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */