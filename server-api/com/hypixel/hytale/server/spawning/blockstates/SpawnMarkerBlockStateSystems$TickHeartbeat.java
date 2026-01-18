/*    */ package com.hypixel.hytale.server.spawning.blockstates;
/*    */ 
/*    */ import com.hypixel.hytale.component.ArchetypeChunk;
/*    */ import com.hypixel.hytale.component.CommandBuffer;
/*    */ import com.hypixel.hytale.component.ComponentAccessor;
/*    */ import com.hypixel.hytale.component.ComponentType;
/*    */ import com.hypixel.hytale.component.Ref;
/*    */ import com.hypixel.hytale.component.Store;
/*    */ import com.hypixel.hytale.component.query.Query;
/*    */ import com.hypixel.hytale.component.system.tick.EntityTickingSystem;
/*    */ import com.hypixel.hytale.server.core.universe.world.storage.ChunkStore;
/*    */ import java.util.logging.Level;
/*    */ import javax.annotation.Nonnull;
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
/*    */ public class TickHeartbeat
/*    */   extends EntityTickingSystem<ChunkStore>
/*    */ {
/*    */   private final ComponentType<ChunkStore, SpawnMarkerBlockState> componentType;
/*    */   
/*    */   public TickHeartbeat(ComponentType<ChunkStore, SpawnMarkerBlockState> componentType) {
/* 70 */     this.componentType = componentType;
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean isParallel(int archetypeChunkSize, int taskCount) {
/* 75 */     return false;
/*    */   }
/*    */ 
/*    */   
/*    */   public Query<ChunkStore> getQuery() {
/* 80 */     return (Query)this.componentType;
/*    */   }
/*    */ 
/*    */   
/*    */   public void tick(float dt, int index, @Nonnull ArchetypeChunk<ChunkStore> archetypeChunk, @Nonnull Store<ChunkStore> store, @Nonnull CommandBuffer<ChunkStore> commandBuffer) {
/* 85 */     SpawnMarkerBlockState state = (SpawnMarkerBlockState)archetypeChunk.getComponent(index, this.componentType);
/* 86 */     if (state.getSpawnMarkerReference() == null) {
/*    */       
/* 88 */       Ref<ChunkStore> ref = archetypeChunk.getReferenceTo(index);
/* 89 */       SpawnMarkerBlockStateSystems.createMarker(ref, state, ((ChunkStore)store.getExternalData()).getWorld().getEntityStore().getStore(), commandBuffer);
/*    */     } 
/*    */     
/* 92 */     if (state.getSpawnMarkerReference().getEntity((ComponentAccessor)((ChunkStore)store.getExternalData()).getWorld().getEntityStore().getStore()) != null) {
/* 93 */       state.refreshMarkerLostTimeout();
/* 94 */     } else if (state.tickMarkerLostTimeout(dt)) {
/*    */ 
/*    */       
/* 97 */       Ref<ChunkStore> ref = archetypeChunk.getReferenceTo(index);
/* 98 */       SpawnMarkerBlockStateSystems.LOGGER.at(Level.SEVERE).log("Creating new spawn marker due to desync with entity: %s", ref);
/* 99 */       SpawnMarkerBlockStateSystems.createMarker(ref, state, ((ChunkStore)store.getExternalData()).getWorld().getEntityStore().getStore(), commandBuffer);
/*    */     } 
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\server\spawning\blockstates\SpawnMarkerBlockStateSystems$TickHeartbeat.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */