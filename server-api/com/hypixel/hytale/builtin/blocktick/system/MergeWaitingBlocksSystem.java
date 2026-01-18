/*    */ package com.hypixel.hytale.builtin.blocktick.system;
/*    */ import com.hypixel.hytale.component.CommandBuffer;
/*    */ import com.hypixel.hytale.component.ComponentType;
/*    */ import com.hypixel.hytale.component.Ref;
/*    */ import com.hypixel.hytale.component.Store;
/*    */ import com.hypixel.hytale.component.query.Query;
/*    */ import com.hypixel.hytale.component.system.RefSystem;
/*    */ import com.hypixel.hytale.server.core.universe.world.chunk.BlockChunk;
/*    */ import com.hypixel.hytale.server.core.universe.world.chunk.WorldChunk;
/*    */ import com.hypixel.hytale.server.core.universe.world.storage.ChunkStore;
/*    */ import javax.annotation.Nonnull;
/*    */ 
/*    */ public class MergeWaitingBlocksSystem extends RefSystem<ChunkStore> {
/* 14 */   private static final ComponentType<ChunkStore, WorldChunk> COMPONENT_TYPE = WorldChunk.getComponentType();
/*    */ 
/*    */   
/*    */   public Query<ChunkStore> getQuery() {
/* 18 */     return (Query)COMPONENT_TYPE;
/*    */   }
/*    */ 
/*    */   
/*    */   public void onEntityAdded(@Nonnull Ref<ChunkStore> ref, @Nonnull AddReason reason, @Nonnull Store<ChunkStore> store, @Nonnull CommandBuffer<ChunkStore> commandBuffer) {
/* 23 */     ChunkStore chunkStore = (ChunkStore)store.getExternalData();
/* 24 */     WorldChunk chunk = (WorldChunk)store.getComponent(ref, COMPONENT_TYPE);
/* 25 */     int x = chunk.getX();
/* 26 */     int z = chunk.getZ();
/*    */     
/* 28 */     mergeTickingBlocks(chunkStore, x - 1, z);
/* 29 */     mergeTickingBlocks(chunkStore, x + 1, z);
/* 30 */     mergeTickingBlocks(chunkStore, x, z - 1);
/* 31 */     mergeTickingBlocks(chunkStore, x, z + 1);
/*    */   }
/*    */ 
/*    */   
/*    */   public void onEntityRemove(@Nonnull Ref<ChunkStore> ref, @Nonnull RemoveReason reason, @Nonnull Store<ChunkStore> store, @Nonnull CommandBuffer<ChunkStore> commandBuffer) {}
/*    */ 
/*    */   
/*    */   public static void mergeTickingBlocks(@Nonnull ChunkStore store, int x, int z) {
/* 39 */     BlockChunk blockChunk = (BlockChunk)store.getChunkComponent(ChunkUtil.indexChunk(x, z), BlockChunk.getComponentType());
/* 40 */     if (blockChunk != null) blockChunk.mergeTickingBlocks(); 
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\builtin\blocktick\system\MergeWaitingBlocksSystem.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */