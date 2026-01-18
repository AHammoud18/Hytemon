/*    */ package com.hypixel.hytale.server.spawning.world.system;
/*    */ 
/*    */ import com.hypixel.hytale.component.AddReason;
/*    */ import com.hypixel.hytale.component.ComponentType;
/*    */ import com.hypixel.hytale.component.Holder;
/*    */ import com.hypixel.hytale.component.RemoveReason;
/*    */ import com.hypixel.hytale.component.ResourceType;
/*    */ import com.hypixel.hytale.component.Store;
/*    */ import com.hypixel.hytale.component.query.Query;
/*    */ import com.hypixel.hytale.component.system.HolderSystem;
/*    */ import com.hypixel.hytale.server.core.universe.world.chunk.WorldChunk;
/*    */ import com.hypixel.hytale.server.core.universe.world.storage.ChunkStore;
/*    */ import com.hypixel.hytale.server.core.universe.world.storage.EntityStore;
/*    */ import com.hypixel.hytale.server.spawning.world.component.SpawnJobData;
/*    */ import com.hypixel.hytale.server.spawning.world.component.WorldSpawnData;
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
/*    */ public class EntityRemoved
/*    */   extends HolderSystem<ChunkStore>
/*    */ {
/*    */   private final ResourceType<EntityStore, WorldSpawnData> worldSpawnDataResourceType;
/*    */   private final ComponentType<ChunkStore, SpawnJobData> spawnJobDataComponentType;
/*    */   private final ComponentType<ChunkStore, WorldChunk> worldChunkComponentType;
/*    */   
/*    */   public EntityRemoved(ResourceType<EntityStore, WorldSpawnData> worldSpawnDataResourceType, ComponentType<ChunkStore, SpawnJobData> spawnJobDataComponentType) {
/* 58 */     this.worldSpawnDataResourceType = worldSpawnDataResourceType;
/* 59 */     this.spawnJobDataComponentType = spawnJobDataComponentType;
/* 60 */     this.worldChunkComponentType = WorldChunk.getComponentType();
/*    */   }
/*    */ 
/*    */   
/*    */   @Nonnull
/*    */   public Query<ChunkStore> getQuery() {
/* 66 */     return WorldSpawnJobSystems.TICKING_QUERY;
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public void onEntityAdd(@Nonnull Holder<ChunkStore> holder, @Nonnull AddReason reason, @Nonnull Store<ChunkStore> store) {}
/*    */ 
/*    */   
/*    */   public void onEntityRemoved(@Nonnull Holder<ChunkStore> entityHolder, @Nonnull RemoveReason reason, @Nonnull Store<ChunkStore> store) {
/* 75 */     SpawnJobData spawnJobData = (SpawnJobData)entityHolder.getComponent(this.spawnJobDataComponentType);
/* 76 */     WorldChunk worldChunk = (WorldChunk)entityHolder.getComponent(this.worldChunkComponentType);
/* 77 */     WorldSpawnData worldSpawnData = (WorldSpawnData)((ChunkStore)store.getExternalData()).getWorld().getEntityStore().getStore().getResource(this.worldSpawnDataResourceType);
/* 78 */     WorldSpawnJobSystems.endProbing(WorldSpawnJobSystems.Result.FAILED, spawnJobData, worldChunk, worldSpawnData);
/* 79 */     entityHolder.removeComponent(this.spawnJobDataComponentType);
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\server\spawning\world\system\WorldSpawnJobSystems$EntityRemoved.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */