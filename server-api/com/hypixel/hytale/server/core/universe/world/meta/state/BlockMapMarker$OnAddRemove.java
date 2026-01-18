/*     */ package com.hypixel.hytale.server.core.universe.world.meta.state;
/*     */ 
/*     */ import com.hypixel.hytale.component.AddReason;
/*     */ import com.hypixel.hytale.component.CommandBuffer;
/*     */ import com.hypixel.hytale.component.ComponentType;
/*     */ import com.hypixel.hytale.component.Ref;
/*     */ import com.hypixel.hytale.component.RemoveReason;
/*     */ import com.hypixel.hytale.component.ResourceType;
/*     */ import com.hypixel.hytale.component.Store;
/*     */ import com.hypixel.hytale.component.query.Query;
/*     */ import com.hypixel.hytale.component.system.RefSystem;
/*     */ import com.hypixel.hytale.math.util.ChunkUtil;
/*     */ import com.hypixel.hytale.math.vector.Vector3i;
/*     */ import com.hypixel.hytale.server.core.modules.block.BlockModule;
/*     */ import com.hypixel.hytale.server.core.universe.world.chunk.BlockChunk;
/*     */ import com.hypixel.hytale.server.core.universe.world.chunk.WorldChunk;
/*     */ import com.hypixel.hytale.server.core.universe.world.storage.ChunkStore;
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
/*     */ public class OnAddRemove
/*     */   extends RefSystem<ChunkStore>
/*     */ {
/*  76 */   private static final ComponentType<ChunkStore, BlockMapMarker> COMPONENT_TYPE = BlockMapMarker.getComponentType();
/*  77 */   private static final ResourceType<ChunkStore, BlockMapMarkersResource> BLOCK_MAP_MARKERS_RESOURCE_TYPE = BlockMapMarkersResource.getResourceType();
/*     */ 
/*     */   
/*     */   public void onEntityAdded(@Nonnull Ref<ChunkStore> ref, @Nonnull AddReason reason, @Nonnull Store<ChunkStore> store, @Nonnull CommandBuffer<ChunkStore> commandBuffer) {
/*  81 */     BlockModule.BlockStateInfo blockInfo = (BlockModule.BlockStateInfo)commandBuffer.getComponent(ref, BlockModule.BlockStateInfo.getComponentType());
/*  82 */     assert blockInfo != null;
/*  83 */     Ref<ChunkStore> chunkRef = blockInfo.getChunkRef();
/*  84 */     if (!chunkRef.isValid()) {
/*     */       return;
/*     */     }
/*     */ 
/*     */     
/*  89 */     BlockChunk blockChunk = (BlockChunk)commandBuffer.getComponent(chunkRef, BlockChunk.getComponentType());
/*  90 */     assert blockChunk != null;
/*     */     
/*  92 */     BlockMapMarker blockMapMarker = (BlockMapMarker)commandBuffer.getComponent(ref, COMPONENT_TYPE);
/*  93 */     assert blockMapMarker != null;
/*     */     
/*  95 */     WorldChunk wc = (WorldChunk)commandBuffer.getComponent(chunkRef, WorldChunk.getComponentType());
/*     */ 
/*     */ 
/*     */     
/*  99 */     Vector3i blockPosition = new Vector3i(ChunkUtil.worldCoordFromLocalCoord(wc.getX(), ChunkUtil.xFromBlockInColumn(blockInfo.getIndex())), ChunkUtil.yFromBlockInColumn(blockInfo.getIndex()), ChunkUtil.worldCoordFromLocalCoord(wc.getZ(), ChunkUtil.zFromBlockInColumn(blockInfo.getIndex())));
/*     */ 
/*     */     
/* 102 */     BlockMapMarkersResource resource = (BlockMapMarkersResource)commandBuffer.getResource(BLOCK_MAP_MARKERS_RESOURCE_TYPE);
/* 103 */     resource.addMarker(blockPosition, blockMapMarker.getName(), blockMapMarker.getIcon());
/*     */   }
/*     */ 
/*     */   
/*     */   public void onEntityRemove(@Nonnull Ref<ChunkStore> ref, @Nonnull RemoveReason reason, @Nonnull Store<ChunkStore> store, @Nonnull CommandBuffer<ChunkStore> commandBuffer) {
/* 108 */     if (reason != RemoveReason.REMOVE)
/*     */       return; 
/* 110 */     BlockModule.BlockStateInfo blockInfo = (BlockModule.BlockStateInfo)commandBuffer.getComponent(ref, BlockModule.BlockStateInfo.getComponentType());
/* 111 */     assert blockInfo != null;
/* 112 */     Ref<ChunkStore> chunkRef = blockInfo.getChunkRef();
/* 113 */     if (!chunkRef.isValid()) {
/*     */       return;
/*     */     }
/*     */     
/* 117 */     WorldChunk wc = (WorldChunk)commandBuffer.getComponent(chunkRef, WorldChunk.getComponentType());
/*     */ 
/*     */ 
/*     */     
/* 121 */     Vector3i blockPosition = new Vector3i(ChunkUtil.worldCoordFromLocalCoord(wc.getX(), ChunkUtil.xFromBlockInColumn(blockInfo.getIndex())), ChunkUtil.yFromBlockInColumn(blockInfo.getIndex()), ChunkUtil.worldCoordFromLocalCoord(wc.getZ(), ChunkUtil.zFromBlockInColumn(blockInfo.getIndex())));
/*     */ 
/*     */     
/* 124 */     BlockMapMarkersResource resource = (BlockMapMarkersResource)commandBuffer.getResource(BLOCK_MAP_MARKERS_RESOURCE_TYPE);
/* 125 */     resource.removeMarker(blockPosition);
/*     */   }
/*     */ 
/*     */   
/*     */   @Nullable
/*     */   public Query<ChunkStore> getQuery() {
/* 131 */     return (Query)COMPONENT_TYPE;
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\server\cor\\universe\world\meta\state\BlockMapMarker$OnAddRemove.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */