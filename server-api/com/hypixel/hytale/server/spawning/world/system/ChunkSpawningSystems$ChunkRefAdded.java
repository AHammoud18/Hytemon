/*    */ package com.hypixel.hytale.server.spawning.world.system;
/*    */ 
/*    */ import com.hypixel.hytale.component.AddReason;
/*    */ import com.hypixel.hytale.component.CommandBuffer;
/*    */ import com.hypixel.hytale.component.ComponentType;
/*    */ import com.hypixel.hytale.component.Ref;
/*    */ import com.hypixel.hytale.component.RemoveReason;
/*    */ import com.hypixel.hytale.component.ResourceType;
/*    */ import com.hypixel.hytale.component.Store;
/*    */ import com.hypixel.hytale.component.query.Query;
/*    */ import com.hypixel.hytale.component.system.RefSystem;
/*    */ import com.hypixel.hytale.server.core.universe.world.chunk.WorldChunk;
/*    */ import com.hypixel.hytale.server.core.universe.world.storage.ChunkStore;
/*    */ import com.hypixel.hytale.server.core.universe.world.storage.EntityStore;
/*    */ import com.hypixel.hytale.server.spawning.world.component.ChunkSpawnData;
/*    */ import com.hypixel.hytale.server.spawning.world.component.ChunkSpawnedNPCData;
/*    */ import com.hypixel.hytale.server.spawning.world.component.WorldSpawnData;
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
/*    */ public class ChunkRefAdded
/*    */   extends RefSystem<ChunkStore>
/*    */ {
/*    */   private final ResourceType<EntityStore, WorldSpawnData> worldSpawnDataResourceType;
/*    */   private final ComponentType<ChunkStore, ChunkSpawnData> chunkSpawnDataComponentType;
/*    */   private final ComponentType<ChunkStore, ChunkSpawnedNPCData> chunkSpawnedNPCDataComponentType;
/*    */   private final ComponentType<ChunkStore, WorldChunk> worldChunkComponentType;
/*    */   
/*    */   public ChunkRefAdded(ResourceType<EntityStore, WorldSpawnData> worldSpawnDataResourceType, ComponentType<ChunkStore, ChunkSpawnData> chunkSpawnDataComponentType, ComponentType<ChunkStore, ChunkSpawnedNPCData> chunkSpawnedNPCDataComponentType) {
/* 46 */     this.worldSpawnDataResourceType = worldSpawnDataResourceType;
/* 47 */     this.chunkSpawnDataComponentType = chunkSpawnDataComponentType;
/* 48 */     this.chunkSpawnedNPCDataComponentType = chunkSpawnedNPCDataComponentType;
/* 49 */     this.worldChunkComponentType = WorldChunk.getComponentType();
/*    */   }
/*    */ 
/*    */   
/*    */   @Nonnull
/*    */   public Query<ChunkStore> getQuery() {
/* 55 */     return ChunkSpawningSystems.TICKING_QUERY;
/*    */   }
/*    */ 
/*    */   
/*    */   public void onEntityAdded(@Nonnull Ref<ChunkStore> ref, @Nonnull AddReason reason, @Nonnull Store<ChunkStore> store, @Nonnull CommandBuffer<ChunkStore> commandBuffer) {
/* 60 */     WorldSpawnData worldSpawnData = (WorldSpawnData)((ChunkStore)store.getExternalData()).getWorld().getEntityStore().getStore().getResource(this.worldSpawnDataResourceType);
/* 61 */     WorldChunk worldChunk = (WorldChunk)store.getComponent(ref, this.worldChunkComponentType);
/* 62 */     if (ChunkSpawningSystems.processStartedChunk(ref, store, worldChunk, worldSpawnData, this.chunkSpawnDataComponentType, this.chunkSpawnedNPCDataComponentType, commandBuffer)) {
/* 63 */       ChunkSpawningSystems.LOGGER.at(Level.FINE).log("Adding chunk [%s/%s]", worldChunk.getX(), worldChunk.getZ());
/* 64 */       ChunkSpawningSystems.updateChunkCount(1, worldSpawnData);
/*    */     } 
/*    */   }
/*    */   
/*    */   public void onEntityRemove(@Nonnull Ref<ChunkStore> ref, @Nonnull RemoveReason reason, @Nonnull Store<ChunkStore> store, @Nonnull CommandBuffer<ChunkStore> commandBuffer) {}
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\server\spawning\world\system\ChunkSpawningSystems$ChunkRefAdded.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */