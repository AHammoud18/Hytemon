/*    */ package com.hypixel.hytale.server.spawning.suppression.system;
/*    */ 
/*    */ import com.hypixel.hytale.component.AddReason;
/*    */ import com.hypixel.hytale.component.CommandBuffer;
/*    */ import com.hypixel.hytale.component.Component;
/*    */ import com.hypixel.hytale.component.ComponentType;
/*    */ import com.hypixel.hytale.component.Ref;
/*    */ import com.hypixel.hytale.component.RemoveReason;
/*    */ import com.hypixel.hytale.component.ResourceType;
/*    */ import com.hypixel.hytale.component.Store;
/*    */ import com.hypixel.hytale.component.query.Query;
/*    */ import com.hypixel.hytale.component.system.RefSystem;
/*    */ import com.hypixel.hytale.server.core.universe.world.World;
/*    */ import com.hypixel.hytale.server.core.universe.world.chunk.BlockChunk;
/*    */ import com.hypixel.hytale.server.core.universe.world.storage.ChunkStore;
/*    */ import com.hypixel.hytale.server.core.universe.world.storage.EntityStore;
/*    */ import com.hypixel.hytale.server.spawning.SpawningPlugin;
/*    */ import com.hypixel.hytale.server.spawning.suppression.component.ChunkSuppressionEntry;
/*    */ import com.hypixel.hytale.server.spawning.suppression.component.SpawnSuppressionController;
/*    */ import java.util.logging.Level;
/*    */ import javax.annotation.Nonnull;
/*    */ 
/*    */ public class ChunkAdded
/*    */   extends RefSystem<ChunkStore>
/*    */ {
/* 26 */   private static final ComponentType<ChunkStore, BlockChunk> COMPONENT_TYPE = BlockChunk.getComponentType();
/*    */   
/*    */   private final ComponentType<ChunkStore, ChunkSuppressionEntry> chunkSuppressionEntryComponentType;
/*    */   private final ResourceType<EntityStore, SpawnSuppressionController> spawnSuppressionControllerResourceType;
/*    */   
/*    */   public ChunkAdded(ComponentType<ChunkStore, ChunkSuppressionEntry> chunkSuppressionEntryComponentType, ResourceType<EntityStore, SpawnSuppressionController> spawnSuppressionControllerResourceType) {
/* 32 */     this.chunkSuppressionEntryComponentType = chunkSuppressionEntryComponentType;
/* 33 */     this.spawnSuppressionControllerResourceType = spawnSuppressionControllerResourceType;
/*    */   }
/*    */ 
/*    */   
/*    */   public Query<ChunkStore> getQuery() {
/* 38 */     return (Query)COMPONENT_TYPE;
/*    */   }
/*    */ 
/*    */   
/*    */   public void onEntityAdded(@Nonnull Ref<ChunkStore> reference, @Nonnull AddReason reason, @Nonnull Store<ChunkStore> store, @Nonnull CommandBuffer<ChunkStore> commandBuffer) {
/* 43 */     World world = ((ChunkStore)store.getExternalData()).getWorld();
/* 44 */     SpawnSuppressionController spawnSuppressionController = (SpawnSuppressionController)world.getEntityStore().getStore().getResource(this.spawnSuppressionControllerResourceType);
/*    */     
/* 46 */     BlockChunk blockChunk = (BlockChunk)commandBuffer.getComponent(reference, COMPONENT_TYPE);
/* 47 */     long index = blockChunk.getIndex();
/* 48 */     ChunkSuppressionEntry entry = (ChunkSuppressionEntry)spawnSuppressionController.getChunkSuppressionMap().get(index);
/* 49 */     if (entry != null) {
/* 50 */       commandBuffer.addComponent(reference, this.chunkSuppressionEntryComponentType, (Component)entry);
/* 51 */       SpawningPlugin.get().getLogger().at(Level.FINEST).log("Annotated chunk index %s on load", index);
/*    */     } 
/*    */   }
/*    */   
/*    */   public void onEntityRemove(@Nonnull Ref<ChunkStore> reference, @Nonnull RemoveReason reason, @Nonnull Store<ChunkStore> store, @Nonnull CommandBuffer<ChunkStore> commandBuffer) {}
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\server\spawning\suppression\system\ChunkSuppressionSystems$ChunkAdded.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */