/*     */ package com.hypixel.hytale.builtin.adventure.farming;
/*     */ 
/*     */ import com.hypixel.hytale.builtin.adventure.farming.config.FarmingCoopAsset;
/*     */ import com.hypixel.hytale.builtin.adventure.farming.states.CoopBlock;
/*     */ import com.hypixel.hytale.builtin.adventure.farming.states.FarmingBlock;
/*     */ import com.hypixel.hytale.builtin.adventure.farming.states.TilledSoilBlock;
/*     */ import com.hypixel.hytale.component.ArchetypeChunk;
/*     */ import com.hypixel.hytale.component.CommandBuffer;
/*     */ import com.hypixel.hytale.component.ComponentAccessor;
/*     */ import com.hypixel.hytale.component.Ref;
/*     */ import com.hypixel.hytale.component.Store;
/*     */ import com.hypixel.hytale.component.query.Query;
/*     */ import com.hypixel.hytale.component.system.tick.EntityTickingSystem;
/*     */ import com.hypixel.hytale.math.util.ChunkUtil;
/*     */ import com.hypixel.hytale.math.vector.Vector3d;
/*     */ import com.hypixel.hytale.math.vector.Vector3i;
/*     */ import com.hypixel.hytale.server.core.asset.type.blocktick.BlockTickStrategy;
/*     */ import com.hypixel.hytale.server.core.asset.type.blocktype.config.BlockType;
/*     */ import com.hypixel.hytale.server.core.asset.type.blocktype.config.farming.FarmingData;
/*     */ import com.hypixel.hytale.server.core.modules.block.BlockModule;
/*     */ import com.hypixel.hytale.server.core.modules.time.WorldTimeResource;
/*     */ import com.hypixel.hytale.server.core.universe.world.World;
/*     */ import com.hypixel.hytale.server.core.universe.world.chunk.BlockChunk;
/*     */ import com.hypixel.hytale.server.core.universe.world.chunk.BlockComponentChunk;
/*     */ import com.hypixel.hytale.server.core.universe.world.chunk.ChunkColumn;
/*     */ import com.hypixel.hytale.server.core.universe.world.chunk.WorldChunk;
/*     */ import com.hypixel.hytale.server.core.universe.world.chunk.section.BlockSection;
/*     */ import com.hypixel.hytale.server.core.universe.world.chunk.section.ChunkSection;
/*     */ import com.hypixel.hytale.server.core.universe.world.storage.ChunkStore;
/*     */ import com.hypixel.hytale.server.core.universe.world.storage.EntityStore;
/*     */ import com.hypixel.hytale.server.core.util.TargetUtil;
/*     */ import java.time.Instant;
/*     */ import java.util.List;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
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
/* 193 */   private static final Query<ChunkStore> QUERY = (Query<ChunkStore>)Query.and(new Query[] { (Query)BlockSection.getComponentType(), (Query)ChunkSection.getComponentType() });
/*     */ 
/*     */   
/*     */   public void tick(float dt, int index, @Nonnull ArchetypeChunk<ChunkStore> archetypeChunk, @Nonnull Store<ChunkStore> store, @Nonnull CommandBuffer<ChunkStore> commandBuffer) {
/* 197 */     BlockSection blocks = (BlockSection)archetypeChunk.getComponent(index, BlockSection.getComponentType());
/* 198 */     assert blocks != null;
/*     */     
/* 200 */     if (blocks.getTickingBlocksCountCopy() == 0)
/*     */       return; 
/* 202 */     ChunkSection section = (ChunkSection)archetypeChunk.getComponent(index, ChunkSection.getComponentType());
/* 203 */     assert section != null;
/*     */     
/* 205 */     BlockComponentChunk blockComponentChunk = (BlockComponentChunk)commandBuffer.getComponent(section.getChunkColumnReference(), BlockComponentChunk.getComponentType());
/* 206 */     assert blockComponentChunk != null;
/*     */     
/* 208 */     Ref<ChunkStore> ref = archetypeChunk.getReferenceTo(index);
/*     */ 
/*     */     
/* 211 */     blocks.forEachTicking(blockComponentChunk, commandBuffer, section.getY(), (blockComponentChunk1, commandBuffer1, localX, localY, localZ, blockId) -> {
/*     */           Ref<ChunkStore> blockRef = blockComponentChunk1.getEntityReference(ChunkUtil.indexBlockInColumn(localX, localY, localZ));
/*     */           if (blockRef == null) {
/*     */             return BlockTickStrategy.IGNORED;
/*     */           }
/*     */           FarmingBlock farming = (FarmingBlock)commandBuffer1.getComponent(blockRef, FarmingBlock.getComponentType());
/*     */           if (farming != null) {
/*     */             FarmingUtil.tickFarming(commandBuffer1, blocks, ref, blockRef, farming, localX, localY, localZ, false);
/*     */             return BlockTickStrategy.SLEEP;
/*     */           } 
/*     */           TilledSoilBlock soil = (TilledSoilBlock)commandBuffer1.getComponent(blockRef, TilledSoilBlock.getComponentType());
/*     */           if (soil != null) {
/*     */             tickSoil(commandBuffer1, blockComponentChunk1, blockRef, soil);
/*     */             return BlockTickStrategy.SLEEP;
/*     */           } 
/*     */           CoopBlock coop = (CoopBlock)commandBuffer1.getComponent(blockRef, CoopBlock.getComponentType());
/*     */           if (coop != null) {
/*     */             tickCoop(commandBuffer1, blockComponentChunk1, blockRef, coop);
/*     */             return BlockTickStrategy.SLEEP;
/*     */           } 
/*     */           return BlockTickStrategy.IGNORED;
/*     */         });
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private static void tickSoil(CommandBuffer<ChunkStore> commandBuffer, BlockComponentChunk blockComponentChunk, Ref<ChunkStore> blockRef, TilledSoilBlock soilBlock) {
/* 238 */     BlockModule.BlockStateInfo info = (BlockModule.BlockStateInfo)commandBuffer.getComponent(blockRef, BlockModule.BlockStateInfo.getComponentType());
/* 239 */     assert info != null;
/*     */     
/* 241 */     int x = ChunkUtil.xFromBlockInColumn(info.getIndex());
/* 242 */     int y = ChunkUtil.yFromBlockInColumn(info.getIndex());
/* 243 */     int z = ChunkUtil.zFromBlockInColumn(info.getIndex());
/*     */     
/* 245 */     if (y >= 320)
/*     */       return; 
/* 247 */     int checkIndex = ChunkUtil.indexBlockInColumn(x, y + 1, z);
/* 248 */     Ref<ChunkStore> aboveBlockRef = blockComponentChunk.getEntityReference(checkIndex);
/*     */     
/* 250 */     boolean hasCrop = false;
/* 251 */     if (aboveBlockRef != null) {
/* 252 */       FarmingBlock farmingBlock = (FarmingBlock)commandBuffer.getComponent(aboveBlockRef, FarmingBlock.getComponentType());
/* 253 */       hasCrop = (farmingBlock != null);
/*     */     } 
/*     */     
/* 256 */     assert info.getChunkRef() != null;
/* 257 */     BlockChunk blockChunk = (BlockChunk)commandBuffer.getComponent(info.getChunkRef(), BlockChunk.getComponentType());
/* 258 */     assert blockChunk != null;
/* 259 */     BlockSection blockSection = blockChunk.getSectionAtBlockY(y);
/*     */     
/* 261 */     BlockType blockType = (BlockType)BlockType.getAssetMap().getAsset(blockSection.get(x, y, z));
/* 262 */     Instant currentTime = ((WorldTimeResource)((ChunkStore)commandBuffer.getExternalData()).getWorld().getEntityStore().getStore().getResource(WorldTimeResource.getResourceType())).getGameTime();
/*     */     
/* 264 */     Instant decayTime = soilBlock.getDecayTime();
/*     */ 
/*     */     
/* 267 */     if (soilBlock.isPlanted() && !hasCrop) {
/* 268 */       if (!FarmingSystems.updateSoilDecayTime(commandBuffer, soilBlock, blockType))
/* 269 */         return;  if (decayTime != null) {
/* 270 */         blockSection.scheduleTick(ChunkUtil.indexBlock(x, y, z), decayTime);
/*     */       }
/* 272 */     } else if (!soilBlock.isPlanted() && !hasCrop) {
/*     */       
/* 274 */       if (decayTime == null || !decayTime.isAfter(currentTime)) {
/* 275 */         assert info.getChunkRef() != null;
/*     */         
/* 277 */         if (blockType == null || blockType.getFarming() == null || blockType.getFarming().getSoilConfig() == null)
/* 278 */           return;  FarmingData.SoilConfig soilConfig = blockType.getFarming().getSoilConfig();
/* 279 */         String str = soilConfig.getTargetBlock();
/* 280 */         if (str == null)
/*     */           return; 
/* 282 */         int targetBlockId = BlockType.getAssetMap().getIndex(str);
/* 283 */         if (targetBlockId == Integer.MIN_VALUE)
/* 284 */           return;  BlockType targetBlockType = (BlockType)BlockType.getAssetMap().getAsset(targetBlockId);
/*     */         
/* 286 */         int rotation = blockSection.getRotationIndex(x, y, z);
/*     */         
/* 288 */         WorldChunk worldChunk = (WorldChunk)commandBuffer.getComponent(info.getChunkRef(), WorldChunk.getComponentType());
/* 289 */         commandBuffer.run(_store -> worldChunk.setBlock(x, y, z, targetBlockId, targetBlockType, rotation, 0, 0));
/*     */ 
/*     */         
/*     */         return;
/*     */       } 
/* 294 */     } else if (hasCrop) {
/* 295 */       soilBlock.setDecayTime(null);
/*     */     } 
/*     */     
/* 298 */     String targetBlock = soilBlock.computeBlockType(currentTime, blockType);
/* 299 */     if (targetBlock != null && !targetBlock.equals(blockType.getId())) {
/* 300 */       WorldChunk worldChunk = (WorldChunk)commandBuffer.getComponent(info.getChunkRef(), WorldChunk.getComponentType());
/* 301 */       int rotation = blockSection.getRotationIndex(x, y, z);
/* 302 */       int targetBlockId = BlockType.getAssetMap().getIndex(targetBlock);
/* 303 */       BlockType targetBlockType = (BlockType)BlockType.getAssetMap().getAsset(targetBlockId);
/* 304 */       commandBuffer.run(_store -> worldChunk.setBlock(x, y, z, targetBlockId, targetBlockType, rotation, 0, 2));
/*     */     } 
/*     */ 
/*     */ 
/*     */     
/* 309 */     soilBlock.setPlanted(hasCrop);
/*     */   }
/*     */   
/*     */   private static void tickCoop(CommandBuffer<ChunkStore> commandBuffer, BlockComponentChunk blockComponentChunk, Ref<ChunkStore> blockRef, CoopBlock coopBlock) {
/* 313 */     BlockModule.BlockStateInfo info = (BlockModule.BlockStateInfo)commandBuffer.getComponent(blockRef, BlockModule.BlockStateInfo.getComponentType());
/* 314 */     assert info != null;
/*     */     
/* 316 */     Store<EntityStore> store = ((ChunkStore)commandBuffer.getExternalData()).getWorld().getEntityStore().getStore();
/* 317 */     WorldTimeResource worldTimeResource = (WorldTimeResource)store.getResource(WorldTimeResource.getResourceType());
/* 318 */     FarmingCoopAsset coopAsset = coopBlock.getCoopAsset();
/* 319 */     if (coopAsset == null)
/*     */       return; 
/* 321 */     int x = ChunkUtil.xFromBlockInColumn(info.getIndex());
/* 322 */     int y = ChunkUtil.yFromBlockInColumn(info.getIndex());
/* 323 */     int z = ChunkUtil.zFromBlockInColumn(info.getIndex());
/*     */     
/* 325 */     BlockChunk blockChunk = (BlockChunk)commandBuffer.getComponent(info.getChunkRef(), BlockChunk.getComponentType());
/* 326 */     assert blockChunk != null;
/*     */     
/* 328 */     ChunkColumn column = (ChunkColumn)commandBuffer.getComponent(info.getChunkRef(), ChunkColumn.getComponentType());
/* 329 */     assert column != null;
/* 330 */     Ref<ChunkStore> sectionRef = column.getSection(ChunkUtil.chunkCoordinate(y));
/* 331 */     assert sectionRef != null;
/* 332 */     BlockSection blockSection = (BlockSection)commandBuffer.getComponent(sectionRef, BlockSection.getComponentType());
/* 333 */     assert blockSection != null;
/* 334 */     ChunkSection chunkSection = (ChunkSection)commandBuffer.getComponent(sectionRef, ChunkSection.getComponentType());
/* 335 */     assert chunkSection != null;
/*     */     
/* 337 */     int worldX = ChunkUtil.worldCoordFromLocalCoord(chunkSection.getX(), x);
/* 338 */     int worldY = ChunkUtil.worldCoordFromLocalCoord(chunkSection.getY(), y);
/* 339 */     int worldZ = ChunkUtil.worldCoordFromLocalCoord(chunkSection.getZ(), z);
/*     */     
/* 341 */     World world = ((ChunkStore)commandBuffer.getExternalData()).getWorld();
/* 342 */     WorldChunk chunk = world.getChunkIfInMemory(ChunkUtil.indexChunkFromBlock(worldX, worldZ));
/* 343 */     double blockRotation = chunk.getRotation(worldX, worldY, worldZ).yaw().getRadians();
/* 344 */     Vector3d spawnOffset = (new Vector3d()).assign(coopAsset.getResidentSpawnOffset()).rotateY((float)blockRotation);
/* 345 */     Vector3i coopLocation = new Vector3i(worldX, worldY, worldZ);
/*     */ 
/*     */     
/* 348 */     boolean tryCapture = coopAsset.getCaptureWildNPCsInRange();
/* 349 */     float captureRange = coopAsset.getWildCaptureRadius();
/* 350 */     if (tryCapture && captureRange >= 0.0F) {
/* 351 */       world.execute(() -> {
/*     */             List<Ref<EntityStore>> entities = TargetUtil.getAllEntitiesInSphere(coopLocation.toVector3d(), captureRange, (ComponentAccessor)store);
/*     */ 
/*     */ 
/*     */             
/*     */             for (Ref<EntityStore> entity : entities) {
/*     */               coopBlock.tryPutWildResidentFromWild(store, entity, worldTimeResource, coopLocation);
/*     */             }
/*     */           });
/*     */     }
/*     */ 
/*     */     
/* 363 */     if (coopBlock.shouldResidentsBeInCoop(worldTimeResource)) {
/* 364 */       world.execute(() -> coopBlock.ensureNoResidentsInWorld(store));
/*     */     } else {
/* 366 */       world.execute(() -> {
/*     */             coopBlock.ensureSpawnResidentsInWorld(world, store, coopLocation.toVector3d(), spawnOffset);
/*     */             
/*     */             coopBlock.generateProduceToInventory(worldTimeResource);
/*     */             
/*     */             Vector3i blockPos = new Vector3i(worldX, worldY, worldZ);
/*     */             
/*     */             BlockType currentBlockType = world.getBlockType(blockPos);
/*     */             
/*     */             assert currentBlockType != null;
/*     */             chunk.setBlockInteractionState(blockPos, currentBlockType, coopBlock.hasProduce() ? "Produce_Ready" : "default");
/*     */           });
/*     */     } 
/* 379 */     Instant nextTickInstant = coopBlock.getNextScheduledTick(worldTimeResource);
/* 380 */     if (nextTickInstant != null) {
/* 381 */       blockSection.scheduleTick(ChunkUtil.indexBlock(x, y, z), nextTickInstant);
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   @Nullable
/*     */   public Query<ChunkStore> getQuery() {
/* 388 */     return QUERY;
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\builtin\adventure\farming\FarmingSystems$Ticking.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */