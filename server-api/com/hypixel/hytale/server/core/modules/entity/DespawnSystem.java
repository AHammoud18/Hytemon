/*    */ package com.hypixel.hytale.server.core.modules.entity;
/*    */ 
/*    */ import com.hypixel.hytale.component.ArchetypeChunk;
/*    */ import com.hypixel.hytale.component.CommandBuffer;
/*    */ import com.hypixel.hytale.component.ComponentType;
/*    */ import com.hypixel.hytale.component.Ref;
/*    */ import com.hypixel.hytale.component.RemoveReason;
/*    */ import com.hypixel.hytale.component.Store;
/*    */ import com.hypixel.hytale.component.query.Query;
/*    */ import com.hypixel.hytale.component.system.tick.EntityTickingSystem;
/*    */ import com.hypixel.hytale.server.core.modules.entity.component.Interactable;
/*    */ import com.hypixel.hytale.server.core.modules.time.TimeResource;
/*    */ import com.hypixel.hytale.server.core.universe.world.storage.EntityStore;
/*    */ import java.time.Instant;
/*    */ import javax.annotation.Nonnull;
/*    */ 
/*    */ public class DespawnSystem extends EntityTickingSystem<EntityStore> {
/*    */   private final ComponentType<EntityStore, DespawnComponent> despawnComponentType;
/*    */   @Nonnull
/*    */   private final Query<EntityStore> query;
/*    */   
/*    */   public DespawnSystem(ComponentType<EntityStore, DespawnComponent> despawnComponentType) {
/* 23 */     this.despawnComponentType = despawnComponentType;
/*    */ 
/*    */ 
/*    */     
/* 27 */     this.query = (Query<EntityStore>)Query.and(new Query[] { (Query)despawnComponentType, (Query)Query.not((Query)Interactable.getComponentType()) });
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean isParallel(int archetypeChunkSize, int taskCount) {
/* 32 */     return EntityTickingSystem.maybeUseParallel(archetypeChunkSize, taskCount);
/*    */   }
/*    */ 
/*    */   
/*    */   @Nonnull
/*    */   public Query<EntityStore> getQuery() {
/* 38 */     return this.query;
/*    */   }
/*    */ 
/*    */   
/*    */   public void tick(float dt, int index, @Nonnull ArchetypeChunk<EntityStore> archetypeChunk, @Nonnull Store<EntityStore> store, @Nonnull CommandBuffer<EntityStore> commandBuffer) {
/* 43 */     DespawnComponent despawn = (DespawnComponent)archetypeChunk.getComponent(index, this.despawnComponentType);
/* 44 */     Instant despawnInstant = despawn.getDespawn();
/*    */     
/* 46 */     TimeResource timeResource = (TimeResource)store.getResource(TimeResource.getResourceType());
/* 47 */     if (!timeResource.getNow().isAfter(despawnInstant))
/*    */       return; 
/* 49 */     Ref<EntityStore> entityRef = archetypeChunk.getReferenceTo(index);
/*    */     
/* 51 */     commandBuffer.removeEntity(entityRef, RemoveReason.REMOVE);
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\server\core\modules\entity\DespawnSystem.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */