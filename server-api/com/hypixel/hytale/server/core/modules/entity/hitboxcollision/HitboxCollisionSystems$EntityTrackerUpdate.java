/*     */ package com.hypixel.hytale.server.core.modules.entity.hitboxcollision;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class EntityTrackerUpdate
/*     */   extends EntityTickingSystem<EntityStore>
/*     */ {
/*     */   private final ComponentType<EntityStore, EntityTrackerSystems.Visible> visibleComponentType;
/*     */   private final ComponentType<EntityStore, HitboxCollision> componentType;
/*     */   @Nonnull
/*     */   private final Query<EntityStore> query;
/*     */   
/*     */   public EntityTrackerUpdate(ComponentType<EntityStore, EntityTrackerSystems.Visible> visibleComponentType, ComponentType<EntityStore, HitboxCollision> componentType) {
/*  59 */     this.visibleComponentType = visibleComponentType;
/*  60 */     this.componentType = componentType;
/*  61 */     this.query = (Query<EntityStore>)Query.and(new Query[] { (Query)visibleComponentType, (Query)componentType });
/*     */   }
/*     */ 
/*     */   
/*     */   @Nullable
/*     */   public SystemGroup<EntityStore> getGroup() {
/*  67 */     return EntityTrackerSystems.QUEUE_UPDATE_GROUP;
/*     */   }
/*     */ 
/*     */   
/*     */   @Nonnull
/*     */   public Query<EntityStore> getQuery() {
/*  73 */     return this.query;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isParallel(int archetypeChunkSize, int taskCount) {
/*  78 */     return EntityTickingSystem.maybeUseParallel(archetypeChunkSize, taskCount);
/*     */   }
/*     */ 
/*     */   
/*     */   public void tick(float dt, int index, @Nonnull ArchetypeChunk<EntityStore> archetypeChunk, @Nonnull Store<EntityStore> store, @Nonnull CommandBuffer<EntityStore> commandBuffer) {
/*  83 */     EntityTrackerSystems.Visible visible = (EntityTrackerSystems.Visible)archetypeChunk.getComponent(index, this.visibleComponentType);
/*  84 */     HitboxCollision hitboxCollision = (HitboxCollision)archetypeChunk.getComponent(index, this.componentType);
/*     */ 
/*     */     
/*  87 */     if (hitboxCollision.consumeNetworkOutdated()) {
/*  88 */       queueUpdatesFor(archetypeChunk.getReferenceTo(index), hitboxCollision, visible.visibleTo);
/*  89 */     } else if (!visible.newlyVisibleTo.isEmpty()) {
/*  90 */       queueUpdatesFor(archetypeChunk.getReferenceTo(index), hitboxCollision, visible.newlyVisibleTo);
/*     */     } 
/*     */   }
/*     */   
/*     */   private static void queueUpdatesFor(Ref<EntityStore> ref, @Nonnull HitboxCollision hitboxCollision, @Nonnull Map<Ref<EntityStore>, EntityTrackerSystems.EntityViewer> visibleTo) {
/*  95 */     ComponentUpdate update = new ComponentUpdate();
/*  96 */     update.type = ComponentUpdateType.HitboxCollision;
/*  97 */     update.hitboxCollisionConfigIndex = hitboxCollision.getHitboxCollisionConfigIndex();
/*     */     
/*  99 */     for (EntityTrackerSystems.EntityViewer viewer : visibleTo.values())
/* 100 */       viewer.queueUpdate(ref, update); 
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\server\core\modules\entity\hitboxcollision\HitboxCollisionSystems$EntityTrackerUpdate.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */