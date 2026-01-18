/*     */ package com.hypixel.hytale.server.core.universe.world.meta;
/*     */ 
/*     */ import com.hypixel.hytale.component.ArchetypeChunk;
/*     */ import com.hypixel.hytale.component.CommandBuffer;
/*     */ import com.hypixel.hytale.component.ComponentType;
/*     */ import com.hypixel.hytale.component.DisableProcessingAssert;
/*     */ import com.hypixel.hytale.component.RemoveReason;
/*     */ import com.hypixel.hytale.component.Store;
/*     */ import com.hypixel.hytale.component.dependency.Dependency;
/*     */ import com.hypixel.hytale.component.dependency.RootDependency;
/*     */ import com.hypixel.hytale.component.query.Query;
/*     */ import com.hypixel.hytale.component.system.tick.EntityTickingSystem;
/*     */ import com.hypixel.hytale.logger.HytaleLogger;
/*     */ import com.hypixel.hytale.math.util.ChunkUtil;
/*     */ import com.hypixel.hytale.math.vector.Vector3i;
/*     */ import com.hypixel.hytale.server.core.asset.type.blocktype.config.BlockType;
/*     */ import com.hypixel.hytale.server.core.modules.block.BlockModule;
/*     */ import com.hypixel.hytale.server.core.universe.world.World;
/*     */ import com.hypixel.hytale.server.core.universe.world.chunk.WorldChunk;
/*     */ import com.hypixel.hytale.server.core.universe.world.storage.ChunkStore;
/*     */ import java.util.Set;
/*     */ import java.util.logging.Level;
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
/*     */ public class LegacyLateInitBlockStateSystem<T extends BlockState>
/*     */   extends EntityTickingSystem<ChunkStore>
/*     */   implements DisableProcessingAssert
/*     */ {
/* 294 */   private static final HytaleLogger LOGGER = HytaleLogger.forEnclosingClass();
/*     */   
/*     */   private final ComponentType<ChunkStore, T> componentType;
/*     */   @Nonnull
/*     */   private final Query<ChunkStore> query;
/*     */   
/*     */   public LegacyLateInitBlockStateSystem(ComponentType<ChunkStore, T> componentType) {
/* 301 */     this.componentType = componentType;
/* 302 */     this.query = (Query<ChunkStore>)Query.and(new Query[] { (Query)componentType, (Query)BlockModule.BlockStateInfo.getComponentType() });
/*     */   }
/*     */ 
/*     */   
/*     */   @Nonnull
/*     */   public Query<ChunkStore> getQuery() {
/* 308 */     return this.query;
/*     */   }
/*     */ 
/*     */   
/*     */   @Nonnull
/*     */   public Set<Dependency<ChunkStore>> getDependencies() {
/* 314 */     return RootDependency.firstSet();
/*     */   }
/*     */ 
/*     */   
/*     */   public void tick(float dt, int index, @Nonnull ArchetypeChunk<ChunkStore> archetypeChunk, @Nonnull Store<ChunkStore> store, @Nonnull CommandBuffer<ChunkStore> commandBuffer) {
/* 319 */     BlockState blockState = (BlockState)archetypeChunk.getComponent(index, this.componentType);
/* 320 */     assert blockState != null;
/*     */     
/* 322 */     BlockModule.BlockStateInfo blockStateInfoComponent = (BlockModule.BlockStateInfo)archetypeChunk.getComponent(index, BlockModule.BlockStateInfo.getComponentType());
/* 323 */     assert blockStateInfoComponent != null;
/*     */     
/*     */     try {
/* 326 */       if (!blockState.initialized.get()) {
/* 327 */         blockState.initialized.set(true);
/*     */         
/* 329 */         if (blockState.getReference() == null || !blockState.getReference().isValid()) {
/* 330 */           blockState.setReference(archetypeChunk.getReferenceTo(index));
/*     */         }
/*     */         
/* 333 */         World world = ((ChunkStore)store.getExternalData()).getWorld();
/* 334 */         Store<ChunkStore> chunkStore = world.getChunkStore().getStore();
/*     */         
/* 336 */         WorldChunk worldChunkComponent = (WorldChunk)chunkStore.getComponent(blockStateInfoComponent.getChunkRef(), WorldChunk.getComponentType());
/* 337 */         assert worldChunkComponent != null;
/*     */         
/* 339 */         int x = ChunkUtil.xFromBlockInColumn(blockStateInfoComponent.getIndex());
/* 340 */         int y = ChunkUtil.yFromBlockInColumn(blockStateInfoComponent.getIndex());
/* 341 */         int z = ChunkUtil.zFromBlockInColumn(blockStateInfoComponent.getIndex());
/*     */         
/* 343 */         blockState.setPosition(worldChunkComponent, new Vector3i(x, y, z));
/*     */         
/* 345 */         int blockIndex = worldChunkComponent.getBlock(x, y, z);
/* 346 */         BlockType blockType = (BlockType)BlockType.getAssetMap().getAsset(blockIndex);
/* 347 */         if (!blockState.initialize(blockType)) {
/* 348 */           LOGGER.at(Level.SEVERE).log("Removing invalid block state %s", blockState);
/* 349 */           commandBuffer.removeEntity(archetypeChunk.getReferenceTo(index), RemoveReason.REMOVE);
/*     */         } 
/*     */       } 
/* 352 */     } catch (Exception throwable) {
/* 353 */       ((HytaleLogger.Api)LOGGER.at(Level.SEVERE).withCause(throwable)).log("Exception while re-init BlockState! Removing!! %s", blockState);
/* 354 */       commandBuffer.removeEntity(archetypeChunk.getReferenceTo(index), RemoveReason.REMOVE);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   @Nonnull
/*     */   public String toString() {
/* 361 */     return "LegacyLateInitBlockStateSystem{componentType=" + String.valueOf(this.componentType) + "}";
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\server\cor\\universe\world\meta\BlockStateModule$LegacyLateInitBlockStateSystem.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */