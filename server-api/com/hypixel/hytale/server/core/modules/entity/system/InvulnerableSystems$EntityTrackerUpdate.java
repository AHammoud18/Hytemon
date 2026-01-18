/*    */ package com.hypixel.hytale.server.core.modules.entity.system;
/*    */ 
/*    */ import com.hypixel.hytale.component.ArchetypeChunk;
/*    */ import com.hypixel.hytale.component.CommandBuffer;
/*    */ import com.hypixel.hytale.component.ComponentType;
/*    */ import com.hypixel.hytale.component.Ref;
/*    */ import com.hypixel.hytale.component.Store;
/*    */ import com.hypixel.hytale.component.SystemGroup;
/*    */ import com.hypixel.hytale.component.query.Query;
/*    */ import com.hypixel.hytale.component.system.tick.EntityTickingSystem;
/*    */ import com.hypixel.hytale.protocol.ComponentUpdate;
/*    */ import com.hypixel.hytale.protocol.ComponentUpdateType;
/*    */ import com.hypixel.hytale.server.core.modules.entity.component.Invulnerable;
/*    */ import com.hypixel.hytale.server.core.modules.entity.tracker.EntityTrackerSystems;
/*    */ import com.hypixel.hytale.server.core.universe.world.storage.EntityStore;
/*    */ import java.util.Map;
/*    */ import javax.annotation.Nonnull;
/*    */ import javax.annotation.Nullable;
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
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class EntityTrackerUpdate
/*    */   extends EntityTickingSystem<EntityStore>
/*    */ {
/*    */   private final ComponentType<EntityStore, EntityTrackerSystems.Visible> componentType;
/*    */   @Nonnull
/*    */   private final Query<EntityStore> query;
/*    */   
/*    */   public EntityTrackerUpdate(ComponentType<EntityStore, EntityTrackerSystems.Visible> componentType) {
/* 46 */     this.componentType = componentType;
/* 47 */     this.query = (Query<EntityStore>)Query.and(new Query[] { (Query)componentType, (Query)Invulnerable.getComponentType() });
/*    */   }
/*    */ 
/*    */   
/*    */   @Nullable
/*    */   public SystemGroup<EntityStore> getGroup() {
/* 53 */     return EntityTrackerSystems.QUEUE_UPDATE_GROUP;
/*    */   }
/*    */ 
/*    */   
/*    */   @Nonnull
/*    */   public Query<EntityStore> getQuery() {
/* 59 */     return this.query;
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean isParallel(int archetypeChunkSize, int taskCount) {
/* 64 */     return EntityTickingSystem.maybeUseParallel(archetypeChunkSize, taskCount);
/*    */   }
/*    */ 
/*    */   
/*    */   public void tick(float dt, int systemIndex, @Nonnull Store<EntityStore> store) {
/* 69 */     super.tick(dt, systemIndex, store);
/*    */ 
/*    */     
/* 72 */     ((InvulnerableSystems.QueueResource)store.getResource(InvulnerableSystems.QueueResource.getResourceType())).queue.clear();
/*    */   }
/*    */ 
/*    */   
/*    */   public void tick(float dt, int index, @Nonnull ArchetypeChunk<EntityStore> archetypeChunk, @Nonnull Store<EntityStore> store, @Nonnull CommandBuffer<EntityStore> commandBuffer) {
/* 77 */     EntityTrackerSystems.Visible visible = (EntityTrackerSystems.Visible)archetypeChunk.getComponent(index, this.componentType);
/*    */     
/* 79 */     Ref<EntityStore> ref = archetypeChunk.getReferenceTo(index);
/*    */ 
/*    */     
/* 82 */     if (((InvulnerableSystems.QueueResource)commandBuffer.getResource(InvulnerableSystems.QueueResource.getResourceType())).queue.remove(ref)) {
/* 83 */       queueUpdatesFor(ref, visible.visibleTo);
/* 84 */     } else if (!visible.newlyVisibleTo.isEmpty()) {
/* 85 */       queueUpdatesFor(ref, visible.newlyVisibleTo);
/*    */     } 
/*    */   }
/*    */   
/*    */   private static void queueUpdatesFor(Ref<EntityStore> ref, @Nonnull Map<Ref<EntityStore>, EntityTrackerSystems.EntityViewer> visibleTo) {
/* 90 */     ComponentUpdate update = new ComponentUpdate();
/* 91 */     update.type = ComponentUpdateType.Invulnerable; for (EntityTrackerSystems.EntityViewer viewer : visibleTo.values())
/* 92 */       viewer.queueUpdate(ref, update); 
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\server\core\modules\entity\system\InvulnerableSystems$EntityTrackerUpdate.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */