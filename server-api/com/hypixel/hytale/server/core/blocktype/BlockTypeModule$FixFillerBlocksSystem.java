/*     */ package com.hypixel.hytale.server.core.blocktype;
/*     */ 
/*     */ import com.hypixel.hytale.assetstore.map.BlockTypeAssetMap;
/*     */ import com.hypixel.hytale.assetstore.map.IndexedLookupTableAssetMap;
/*     */ import com.hypixel.hytale.component.AddReason;
/*     */ import com.hypixel.hytale.component.CommandBuffer;
/*     */ import com.hypixel.hytale.component.ComponentType;
/*     */ import com.hypixel.hytale.component.DisableProcessingAssert;
/*     */ import com.hypixel.hytale.component.Ref;
/*     */ import com.hypixel.hytale.component.RemoveReason;
/*     */ import com.hypixel.hytale.component.Store;
/*     */ import com.hypixel.hytale.component.query.Query;
/*     */ import com.hypixel.hytale.component.system.RefSystem;
/*     */ import com.hypixel.hytale.math.util.ChunkUtil;
/*     */ import com.hypixel.hytale.server.core.asset.type.blockhitbox.BlockBoundingBoxes;
/*     */ import com.hypixel.hytale.server.core.asset.type.blocktype.config.BlockType;
/*     */ import com.hypixel.hytale.server.core.universe.world.World;
/*     */ import com.hypixel.hytale.server.core.universe.world.accessor.BlockAccessor;
/*     */ import com.hypixel.hytale.server.core.universe.world.accessor.ChunkAccessor;
/*     */ import com.hypixel.hytale.server.core.universe.world.accessor.LocalCachedChunkAccessor;
/*     */ import com.hypixel.hytale.server.core.universe.world.chunk.BlockChunk;
/*     */ import com.hypixel.hytale.server.core.universe.world.chunk.ChunkFlag;
/*     */ import com.hypixel.hytale.server.core.universe.world.chunk.WorldChunk;
/*     */ import com.hypixel.hytale.server.core.universe.world.chunk.section.BlockSection;
/*     */ import com.hypixel.hytale.server.core.universe.world.storage.ChunkStore;
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
/*     */ @Deprecated
/*     */ class FixFillerBlocksSystem
/*     */   extends RefSystem<ChunkStore>
/*     */   implements DisableProcessingAssert
/*     */ {
/* 376 */   private static final ComponentType<ChunkStore, WorldChunk> COMPONENT_TYPE = WorldChunk.getComponentType();
/*     */ 
/*     */   
/*     */   public Query<ChunkStore> getQuery() {
/* 380 */     return (Query)COMPONENT_TYPE;
/*     */   }
/*     */ 
/*     */   
/*     */   public void onEntityAdded(@Nonnull Ref<ChunkStore> ref, @Nonnull AddReason reason, @Nonnull Store<ChunkStore> store, @Nonnull CommandBuffer<ChunkStore> commandBuffer) {
/* 385 */     WorldChunk chunk = (WorldChunk)store.getComponent(ref, COMPONENT_TYPE);
/*     */ 
/*     */     
/* 388 */     if (!chunk.is(ChunkFlag.NEWLY_GENERATED))
/*     */       return; 
/* 390 */     World world = ((ChunkStore)store.getExternalData()).getWorld();
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 395 */     world.execute(() -> fixFillerFor(world, chunk));
/*     */   }
/*     */   
/*     */   public static void fixFillerFor(@Nonnull World world, @Nonnull WorldChunk chunk) {
/* 399 */     BlockChunk blockChunk = chunk.getBlockChunk();
/*     */     
/* 401 */     BlockTypeAssetMap<String, BlockType> blockTypeAssetMap = BlockType.getAssetMap();
/* 402 */     IndexedLookupTableAssetMap<String, BlockBoundingBoxes> hitboxAssetMap = BlockBoundingBoxes.getAssetMap();
/*     */     
/* 404 */     LocalCachedChunkAccessor accessor = LocalCachedChunkAccessor.atChunk((ChunkAccessor)world, chunk, 1);
/* 405 */     for (int x = -1; x < 2; x++) {
/* 406 */       for (int z = -1; z < 2; z++) {
/* 407 */         if (x != 0 || z != 0) {
/* 408 */           WorldChunk chunkIfInMemory = world.getChunkIfInMemory(ChunkUtil.indexChunk(x + chunk.getX(), z + chunk.getZ()));
/* 409 */           if (chunkIfInMemory != null) accessor.overwrite(chunkIfInMemory); 
/*     */         } 
/*     */       } 
/*     */     } 
/* 413 */     for (int sectionIndex = 0; sectionIndex < 10; sectionIndex++) {
/* 414 */       BlockSection section = blockChunk.getSectionAtIndex(sectionIndex);
/*     */       
/* 416 */       boolean skipInsideSection = (section.getMaximumHitboxExtent() <= 0.0D);
/*     */       
/* 418 */       int sectionYBlock = sectionIndex << 5;
/*     */       
/* 420 */       for (int yInSection = 0; yInSection < 32; yInSection++) {
/* 421 */         int y = sectionYBlock | yInSection;
/* 422 */         for (int i = -1; i < 33; i++) {
/* 423 */           for (int z = -1; z < 33; z++) {
/*     */ 
/*     */             
/* 426 */             if (i < 1 || i >= 31 || y < 1 || y >= 319 || z < 1 || z >= 31)
/*     */             {
/*     */ 
/*     */ 
/*     */ 
/*     */               
/* 432 */               if (i < 0 || i >= 32 || y < 0 || y >= 320 || z < 0 || z >= 32) {
/*     */ 
/*     */                 
/* 435 */                 int worldX = (chunk.getX() << 5) + i;
/* 436 */                 int worldZ = (chunk.getZ() << 5) + z;
/* 437 */                 WorldChunk neighbourChunk = accessor.getChunkIfInMemory(ChunkUtil.indexChunkFromBlock(worldX, worldZ));
/* 438 */                 if (neighbourChunk != null) {
/*     */                   
/* 440 */                   BlockSection neighbourSection = neighbourChunk.getBlockChunk().getSectionAtBlockY(y);
/*     */                   
/* 442 */                   if (neighbourSection.getMaximumHitboxExtent() > 0.0D) {
/*     */                     
/* 444 */                     int blockId = neighbourSection.get(i, y, z);
/* 445 */                     if (blockId != 0) {
/* 446 */                       BlockType blockType = (BlockType)blockTypeAssetMap.getAsset(blockId);
/* 447 */                       int rotation = neighbourSection.getRotationIndex(i, y, z);
/*     */                       
/* 449 */                       BlockTypeModule.breakOrSetFillerBlocks(blockTypeAssetMap, hitboxAssetMap, (ChunkAccessor<?>)accessor, (BlockAccessor)chunk, i, y, z, blockType, rotation);
/*     */                     } 
/*     */                   } 
/*     */                 } 
/* 453 */               } else if (!skipInsideSection) {
/*     */                 
/* 455 */                 int blockId = section.get(i, y, z);
/* 456 */                 if (blockId != 0) {
/* 457 */                   BlockType blockType = (BlockType)blockTypeAssetMap.getAsset(blockId);
/* 458 */                   int rotation = section.getRotationIndex(i, y, z);
/*     */                   
/* 460 */                   BlockTypeModule.breakOrSetFillerBlocks(blockTypeAssetMap, hitboxAssetMap, (ChunkAccessor<?>)accessor, (BlockAccessor)chunk, i, y, z, blockType, rotation);
/*     */                 } 
/*     */               } 
/*     */             }
/*     */           } 
/*     */         } 
/*     */       } 
/*     */     } 
/*     */   }
/*     */   
/*     */   public void onEntityRemove(@Nonnull Ref<ChunkStore> ref, @Nonnull RemoveReason reason, @Nonnull Store<ChunkStore> store, @Nonnull CommandBuffer<ChunkStore> commandBuffer) {}
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\server\core\blocktype\BlockTypeModule$FixFillerBlocksSystem.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */