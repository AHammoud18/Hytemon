/*    */ package com.hypixel.hytale.server.core.modules.entity.player;
/*    */ 
/*    */ import com.hypixel.hytale.component.AddReason;
/*    */ import com.hypixel.hytale.component.ArchetypeChunk;
/*    */ import com.hypixel.hytale.component.CommandBuffer;
/*    */ import com.hypixel.hytale.component.ComponentType;
/*    */ import com.hypixel.hytale.component.Holder;
/*    */ import com.hypixel.hytale.component.Ref;
/*    */ import com.hypixel.hytale.component.RemoveReason;
/*    */ import com.hypixel.hytale.component.Store;
/*    */ import com.hypixel.hytale.component.query.Query;
/*    */ import com.hypixel.hytale.component.system.HolderSystem;
/*    */ import com.hypixel.hytale.component.system.tick.EntityTickingSystem;
/*    */ import com.hypixel.hytale.server.core.universe.world.storage.EntityStore;
/*    */ import javax.annotation.Nonnull;
/*    */ 
/*    */ 
/*    */ 
/*    */ public class PlayerChunkTrackerSystems
/*    */ {
/*    */   public static class AddSystem
/*    */     extends HolderSystem<EntityStore>
/*    */   {
/*    */     @Nonnull
/* 25 */     private static final ComponentType<EntityStore, ChunkTracker> CHUNK_TRACKER_COMPONENT_TYPE = ChunkTracker.getComponentType();
/*    */ 
/*    */     
/*    */     public Query<EntityStore> getQuery() {
/* 29 */       return (Query)CHUNK_TRACKER_COMPONENT_TYPE;
/*    */     }
/*    */ 
/*    */     
/*    */     public void onEntityAdd(@Nonnull Holder<EntityStore> holder, @Nonnull AddReason reason, @Nonnull Store<EntityStore> store) {
/* 34 */       ChunkTracker chunkTrackerComponent = (ChunkTracker)holder.getComponent(CHUNK_TRACKER_COMPONENT_TYPE);
/* 35 */       assert chunkTrackerComponent != null;
/*    */       
/* 37 */       chunkTrackerComponent.setReadyForChunks(true);
/*    */     }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */     
/*    */     public void onEntityRemoved(@Nonnull Holder<EntityStore> holder, @Nonnull RemoveReason reason, @Nonnull Store<EntityStore> store) {}
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public static class UpdateSystem
/*    */     extends EntityTickingSystem<EntityStore>
/*    */   {
/*    */     @Nonnull
/* 54 */     private static final ComponentType<EntityStore, ChunkTracker> CHUNK_TRACKER_COMPONENT_TYPE = ChunkTracker.getComponentType();
/*    */ 
/*    */     
/*    */     public Query<EntityStore> getQuery() {
/* 58 */       return (Query)CHUNK_TRACKER_COMPONENT_TYPE;
/*    */     }
/*    */ 
/*    */     
/*    */     public boolean isParallel(int archetypeChunkSize, int taskCount) {
/* 63 */       return false;
/*    */     }
/*    */ 
/*    */     
/*    */     public void tick(float dt, int index, @Nonnull ArchetypeChunk<EntityStore> archetypeChunk, @Nonnull Store<EntityStore> store, @Nonnull CommandBuffer<EntityStore> commandBuffer) {
/* 68 */       Ref<EntityStore> ref = archetypeChunk.getReferenceTo(index);
/*    */       
/* 70 */       ChunkTracker chunkTrackerComponent = (ChunkTracker)archetypeChunk.getComponent(index, CHUNK_TRACKER_COMPONENT_TYPE);
/* 71 */       assert chunkTrackerComponent != null;
/*    */       
/* 73 */       chunkTrackerComponent.tick(ref, dt, commandBuffer);
/*    */     }
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\server\core\modules\entity\player\PlayerChunkTrackerSystems.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */