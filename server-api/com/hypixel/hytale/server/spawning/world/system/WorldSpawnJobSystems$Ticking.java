/*     */ package com.hypixel.hytale.server.spawning.world.system;
/*     */ 
/*     */ import com.hypixel.hytale.component.ArchetypeChunk;
/*     */ import com.hypixel.hytale.component.CommandBuffer;
/*     */ import com.hypixel.hytale.component.ComponentType;
/*     */ import com.hypixel.hytale.component.ResourceType;
/*     */ import com.hypixel.hytale.component.Store;
/*     */ import com.hypixel.hytale.component.query.Query;
/*     */ import com.hypixel.hytale.component.system.tick.EntityTickingSystem;
/*     */ import com.hypixel.hytale.server.core.universe.world.World;
/*     */ import com.hypixel.hytale.server.core.universe.world.chunk.WorldChunk;
/*     */ import com.hypixel.hytale.server.core.universe.world.storage.ChunkStore;
/*     */ import com.hypixel.hytale.server.core.universe.world.storage.EntityStore;
/*     */ import com.hypixel.hytale.server.spawning.suppression.component.SpawnSuppressionController;
/*     */ import com.hypixel.hytale.server.spawning.world.ChunkEnvironmentSpawnData;
/*     */ import com.hypixel.hytale.server.spawning.world.component.ChunkSpawnData;
/*     */ import com.hypixel.hytale.server.spawning.world.component.ChunkSpawnedNPCData;
/*     */ import com.hypixel.hytale.server.spawning.world.component.SpawnJobData;
/*     */ import com.hypixel.hytale.server.spawning.world.component.WorldSpawnData;
/*     */ import javax.annotation.Nonnull;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class Ticking
/*     */   extends EntityTickingSystem<ChunkStore>
/*     */ {
/*     */   private final ResourceType<EntityStore, WorldSpawnData> worldSpawnDataResourceType;
/*     */   private final ResourceType<EntityStore, SpawnSuppressionController> spawnSuppressionControllerResourceType;
/*     */   private final ComponentType<ChunkStore, SpawnJobData> spawnJobDataComponentType;
/*     */   private final ComponentType<ChunkStore, WorldChunk> worldChunkComponentType;
/*     */   private final ComponentType<ChunkStore, ChunkSpawnData> chunkSpawnDataComponentType;
/*     */   private final ComponentType<ChunkStore, ChunkSpawnedNPCData> chunkSpawnedNPCDataComponentType;
/*     */   
/*     */   public Ticking(ResourceType<EntityStore, WorldSpawnData> worldSpawnDataResourceType, ResourceType<EntityStore, SpawnSuppressionController> spawnSuppressionControllerResourceType, ComponentType<ChunkStore, SpawnJobData> spawnJobDataComponentType, ComponentType<ChunkStore, ChunkSpawnData> chunkSpawnDataComponentType, ComponentType<ChunkStore, ChunkSpawnedNPCData> chunkSpawnedNPCDataComponentType) {
/* 141 */     this.worldSpawnDataResourceType = worldSpawnDataResourceType;
/* 142 */     this.spawnSuppressionControllerResourceType = spawnSuppressionControllerResourceType;
/* 143 */     this.spawnJobDataComponentType = spawnJobDataComponentType;
/* 144 */     this.worldChunkComponentType = WorldChunk.getComponentType();
/* 145 */     this.chunkSpawnDataComponentType = chunkSpawnDataComponentType;
/* 146 */     this.chunkSpawnedNPCDataComponentType = chunkSpawnedNPCDataComponentType;
/*     */   }
/*     */ 
/*     */   
/*     */   public Query<ChunkStore> getQuery() {
/* 151 */     return WorldSpawnJobSystems.QUERY;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isParallel(int archetypeChunkSize, int taskCount) {
/* 157 */     return false;
/*     */   }
/*     */ 
/*     */   
/*     */   public void tick(float dt, int index, @Nonnull ArchetypeChunk<ChunkStore> archetypeChunk, @Nonnull Store<ChunkStore> store, @Nonnull CommandBuffer<ChunkStore> commandBuffer) {
/* 162 */     World world = ((ChunkStore)store.getExternalData()).getWorld();
/* 163 */     Store<EntityStore> entityStore = world.getEntityStore().getStore();
/*     */     
/* 165 */     SpawnJobData spawnJobData = (SpawnJobData)archetypeChunk.getComponent(index, this.spawnJobDataComponentType);
/* 166 */     WorldSpawnData worldSpawnData = (WorldSpawnData)entityStore.getResource(this.worldSpawnDataResourceType);
/* 167 */     WorldChunk worldChunk = (WorldChunk)archetypeChunk.getComponent(index, this.worldChunkComponentType);
/* 168 */     if (spawnJobData.isTerminated() || worldSpawnData.getActualNPCs() > worldSpawnData.getExpectedNPCs()) {
/* 169 */       WorldSpawnJobSystems.endProbing(WorldSpawnJobSystems.Result.FAILED, spawnJobData, worldChunk, worldSpawnData);
/* 170 */       commandBuffer.removeComponent(archetypeChunk.getReferenceTo(index), this.spawnJobDataComponentType);
/*     */       
/*     */       return;
/*     */     } 
/* 174 */     ChunkSpawnData chunkSpawnData = (ChunkSpawnData)archetypeChunk.getComponent(index, this.chunkSpawnDataComponentType);
/* 175 */     ChunkSpawnedNPCData chunkSpawnedNPCData = (ChunkSpawnedNPCData)archetypeChunk.getComponent(index, this.chunkSpawnedNPCDataComponentType);
/* 176 */     int environmentIndex = spawnJobData.getEnvironmentIndex();
/* 177 */     double spawnedNPCs = chunkSpawnedNPCData.getEnvironmentSpawnCount(environmentIndex);
/* 178 */     ChunkEnvironmentSpawnData chunkEnvironmentSpawnData = chunkSpawnData.getEnvironmentSpawnData(environmentIndex);
/* 179 */     if (chunkEnvironmentSpawnData.allRolesUnspawnable() || (!spawnJobData.isIgnoreFullyPopulated() && chunkEnvironmentSpawnData.isFullyPopulated(spawnedNPCs))) {
/* 180 */       WorldSpawnJobSystems.endProbing(WorldSpawnJobSystems.Result.FAILED, spawnJobData, worldChunk, worldSpawnData);
/* 181 */       commandBuffer.removeComponent(archetypeChunk.getReferenceTo(index), this.spawnJobDataComponentType);
/*     */       
/*     */       return;
/*     */     } 
/* 185 */     SpawnSuppressionController spawnSuppressionController = (SpawnSuppressionController)entityStore.getResource(this.spawnSuppressionControllerResourceType);
/* 186 */     WorldSpawnJobSystems.Result result = WorldSpawnJobSystems.run(spawnJobData, worldChunk, chunkEnvironmentSpawnData, worldSpawnData, spawnSuppressionController);
/* 187 */     if (result == WorldSpawnJobSystems.Result.SUCCESS) {
/* 188 */       chunkSpawnData.setLastSpawn(System.nanoTime());
/*     */     }
/* 190 */     if (result != WorldSpawnJobSystems.Result.TRY_AGAIN)
/* 191 */       commandBuffer.removeComponent(archetypeChunk.getReferenceTo(index), this.spawnJobDataComponentType); 
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\server\spawning\world\system\WorldSpawnJobSystems$Ticking.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */