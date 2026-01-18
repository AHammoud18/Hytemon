/*     */ package com.hypixel.hytale.builtin.adventure.farming;
/*     */ 
/*     */ import com.hypixel.hytale.builtin.adventure.farming.states.CoopBlock;
/*     */ import com.hypixel.hytale.component.AddReason;
/*     */ import com.hypixel.hytale.component.CommandBuffer;
/*     */ import com.hypixel.hytale.component.Ref;
/*     */ import com.hypixel.hytale.component.RemoveReason;
/*     */ import com.hypixel.hytale.component.Store;
/*     */ import com.hypixel.hytale.component.query.Query;
/*     */ import com.hypixel.hytale.component.system.RefSystem;
/*     */ import com.hypixel.hytale.math.util.ChunkUtil;
/*     */ import com.hypixel.hytale.server.core.modules.block.BlockModule;
/*     */ import com.hypixel.hytale.server.core.modules.time.WorldTimeResource;
/*     */ import com.hypixel.hytale.server.core.universe.world.World;
/*     */ import com.hypixel.hytale.server.core.universe.world.chunk.BlockChunk;
/*     */ import com.hypixel.hytale.server.core.universe.world.chunk.ChunkColumn;
/*     */ import com.hypixel.hytale.server.core.universe.world.chunk.section.BlockSection;
/*     */ import com.hypixel.hytale.server.core.universe.world.chunk.section.ChunkSection;
/*     */ import com.hypixel.hytale.server.core.universe.world.storage.ChunkStore;
/*     */ import com.hypixel.hytale.server.core.universe.world.storage.EntityStore;
/*     */ import org.checkerframework.checker.nullness.compatqual.NonNullDecl;
/*     */ import org.checkerframework.checker.nullness.compatqual.NullableDecl;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class OnCoopAdded
/*     */   extends RefSystem<ChunkStore>
/*     */ {
/* 394 */   private static final Query<ChunkStore> QUERY = (Query<ChunkStore>)Query.and(new Query[] {
/* 395 */         (Query)BlockModule.BlockStateInfo.getComponentType(), 
/* 396 */         (Query)CoopBlock.getComponentType()
/*     */       });
/*     */ 
/*     */   
/*     */   public void onEntityAdded(@NonNullDecl Ref<ChunkStore> ref, @NonNullDecl AddReason reason, @NonNullDecl Store<ChunkStore> store, @NonNullDecl CommandBuffer<ChunkStore> commandBuffer) {
/* 401 */     CoopBlock coopBlock = (CoopBlock)commandBuffer.getComponent(ref, CoopBlock.getComponentType());
/* 402 */     if (coopBlock == null)
/*     */       return; 
/* 404 */     WorldTimeResource worldTimeResource = (WorldTimeResource)((ChunkStore)commandBuffer.getExternalData()).getWorld().getEntityStore().getStore().getResource(WorldTimeResource.getResourceType());
/*     */     
/* 406 */     BlockModule.BlockStateInfo info = (BlockModule.BlockStateInfo)commandBuffer.getComponent(ref, BlockModule.BlockStateInfo.getComponentType());
/* 407 */     assert info != null;
/*     */     
/* 409 */     int x = ChunkUtil.xFromBlockInColumn(info.getIndex());
/* 410 */     int y = ChunkUtil.yFromBlockInColumn(info.getIndex());
/* 411 */     int z = ChunkUtil.zFromBlockInColumn(info.getIndex());
/*     */     
/* 413 */     BlockChunk blockChunk = (BlockChunk)commandBuffer.getComponent(info.getChunkRef(), BlockChunk.getComponentType());
/* 414 */     assert blockChunk != null;
/* 415 */     BlockSection blockSection = blockChunk.getSectionAtBlockY(y);
/*     */     
/* 417 */     blockSection.scheduleTick(ChunkUtil.indexBlock(x, y, z), coopBlock.getNextScheduledTick(worldTimeResource));
/*     */   }
/*     */ 
/*     */   
/*     */   public void onEntityRemove(@NonNullDecl Ref<ChunkStore> ref, @NonNullDecl RemoveReason reason, @NonNullDecl Store<ChunkStore> store, @NonNullDecl CommandBuffer<ChunkStore> commandBuffer) {
/* 422 */     if (reason == RemoveReason.UNLOAD) {
/*     */       return;
/*     */     }
/*     */     
/* 426 */     CoopBlock coop = (CoopBlock)commandBuffer.getComponent(ref, CoopBlock.getComponentType());
/* 427 */     if (coop == null) {
/*     */       return;
/*     */     }
/*     */     
/* 431 */     BlockModule.BlockStateInfo info = (BlockModule.BlockStateInfo)commandBuffer.getComponent(ref, BlockModule.BlockStateInfo.getComponentType());
/* 432 */     assert info != null;
/*     */     
/* 434 */     Store<EntityStore> entityStore = ((ChunkStore)commandBuffer.getExternalData()).getWorld().getEntityStore().getStore();
/*     */     
/* 436 */     int x = ChunkUtil.xFromBlockInColumn(info.getIndex());
/* 437 */     int y = ChunkUtil.yFromBlockInColumn(info.getIndex());
/* 438 */     int z = ChunkUtil.zFromBlockInColumn(info.getIndex());
/*     */     
/* 440 */     BlockChunk blockChunk = (BlockChunk)commandBuffer.getComponent(info.getChunkRef(), BlockChunk.getComponentType());
/* 441 */     assert blockChunk != null;
/*     */     
/* 443 */     ChunkColumn column = (ChunkColumn)commandBuffer.getComponent(info.getChunkRef(), ChunkColumn.getComponentType());
/* 444 */     assert column != null;
/* 445 */     Ref<ChunkStore> sectionRef = column.getSection(ChunkUtil.chunkCoordinate(y));
/* 446 */     assert sectionRef != null;
/* 447 */     BlockSection blockSection = (BlockSection)commandBuffer.getComponent(sectionRef, BlockSection.getComponentType());
/* 448 */     assert blockSection != null;
/* 449 */     ChunkSection chunkSection = (ChunkSection)commandBuffer.getComponent(sectionRef, ChunkSection.getComponentType());
/* 450 */     assert chunkSection != null;
/*     */     
/* 452 */     int worldX = ChunkUtil.worldCoordFromLocalCoord(chunkSection.getX(), x);
/* 453 */     int worldY = ChunkUtil.worldCoordFromLocalCoord(chunkSection.getY(), y);
/* 454 */     int worldZ = ChunkUtil.worldCoordFromLocalCoord(chunkSection.getZ(), z);
/*     */     
/* 456 */     World world = ((ChunkStore)commandBuffer.getExternalData()).getWorld();
/* 457 */     WorldTimeResource worldTimeResource = (WorldTimeResource)world.getEntityStore().getStore().getResource(WorldTimeResource.getResourceType());
/* 458 */     coop.handleBlockBroken(world, worldTimeResource, entityStore, worldX, worldY, worldZ);
/*     */   }
/*     */ 
/*     */   
/*     */   @NullableDecl
/*     */   public Query<ChunkStore> getQuery() {
/* 464 */     return QUERY;
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\builtin\adventure\farming\FarmingSystems$OnCoopAdded.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */