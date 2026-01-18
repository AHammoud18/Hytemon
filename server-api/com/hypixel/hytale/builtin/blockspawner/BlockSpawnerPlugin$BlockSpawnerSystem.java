/*     */ package com.hypixel.hytale.builtin.blockspawner;
/*     */ 
/*     */ import com.hypixel.hytale.builtin.blockspawner.state.BlockSpawner;
/*     */ import com.hypixel.hytale.component.AddReason;
/*     */ import com.hypixel.hytale.component.CommandBuffer;
/*     */ import com.hypixel.hytale.component.ComponentType;
/*     */ import com.hypixel.hytale.component.Holder;
/*     */ import com.hypixel.hytale.component.Ref;
/*     */ import com.hypixel.hytale.component.RemoveReason;
/*     */ import com.hypixel.hytale.component.Store;
/*     */ import com.hypixel.hytale.component.query.Query;
/*     */ import com.hypixel.hytale.component.system.RefSystem;
/*     */ import com.hypixel.hytale.math.Axis;
/*     */ import com.hypixel.hytale.math.util.ChunkUtil;
/*     */ import com.hypixel.hytale.math.util.HashUtil;
/*     */ import com.hypixel.hytale.protocol.GameMode;
/*     */ import com.hypixel.hytale.server.core.asset.type.blocktype.config.BlockType;
/*     */ import com.hypixel.hytale.server.core.asset.type.blocktype.config.Rotation;
/*     */ import com.hypixel.hytale.server.core.asset.type.blocktype.config.RotationTuple;
/*     */ import com.hypixel.hytale.server.core.asset.type.blocktype.config.VariantRotation;
/*     */ import com.hypixel.hytale.server.core.modules.block.BlockModule;
/*     */ import com.hypixel.hytale.server.core.universe.world.WorldConfig;
/*     */ import com.hypixel.hytale.server.core.universe.world.chunk.BlockRotationUtil;
/*     */ import com.hypixel.hytale.server.core.universe.world.chunk.WorldChunk;
/*     */ import com.hypixel.hytale.server.core.universe.world.storage.ChunkStore;
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
/*     */ class BlockSpawnerSystem
/*     */   extends RefSystem<ChunkStore>
/*     */ {
/*  75 */   private static final ComponentType<ChunkStore, BlockSpawner> COMPONENT_TYPE = BlockSpawner.getComponentType();
/*  76 */   private static final ComponentType<ChunkStore, BlockModule.BlockStateInfo> BLOCK_INFO_COMPONENT_TYPE = BlockModule.BlockStateInfo.getComponentType();
/*  77 */   private static final Query<ChunkStore> QUERY = (Query<ChunkStore>)Query.and(new Query[] { (Query)COMPONENT_TYPE, (Query)BLOCK_INFO_COMPONENT_TYPE });
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Query<ChunkStore> getQuery() {
/*  84 */     return QUERY; } public void onEntityAdded(@Nonnull Ref<ChunkStore> ref, @Nonnull AddReason reason, @Nonnull Store<ChunkStore> store, @Nonnull CommandBuffer<ChunkStore> commandBuffer) { String key;
/*     */     VariantRotation variantRotation;
/*     */     int randomHash;
/*     */     RotationTuple spawnerRotation;
/*     */     Rotation rotationYaw, spawnerYaw;
/*  89 */     WorldConfig worldConfig = ((ChunkStore)store.getExternalData()).getWorld().getWorldConfig();
/*  90 */     if (worldConfig.getGameMode() == GameMode.Creative)
/*     */       return; 
/*  92 */     BlockSpawner state = (BlockSpawner)commandBuffer.getComponent(ref, COMPONENT_TYPE);
/*  93 */     assert state != null;
/*     */     
/*  95 */     BlockModule.BlockStateInfo info = (BlockModule.BlockStateInfo)commandBuffer.getComponent(ref, BLOCK_INFO_COMPONENT_TYPE);
/*  96 */     assert info != null;
/*     */     
/*  98 */     String blockSpawnerId = state.getBlockSpawnerId();
/*  99 */     if (blockSpawnerId == null)
/*     */       return; 
/* 101 */     BlockSpawnerTable table = (BlockSpawnerTable)BlockSpawnerTable.getAssetMap().getAsset(blockSpawnerId);
/* 102 */     if (table == null) {
/* 103 */       BlockSpawnerPlugin.LOGGER.at(Level.WARNING).log("Failed to find BlockSpawner Asset by name: %s", blockSpawnerId);
/*     */       
/*     */       return;
/*     */     } 
/* 107 */     Ref<ChunkStore> chunk = info.getChunkRef();
/* 108 */     if (chunk == null)
/* 109 */       return;  WorldChunk wc = (WorldChunk)commandBuffer.getComponent(chunk, WorldChunk.getComponentType());
/*     */     
/* 111 */     int x = ChunkUtil.worldCoordFromLocalCoord(wc.getX(), ChunkUtil.xFromBlockInColumn(info.getIndex()));
/* 112 */     int y = ChunkUtil.yFromBlockInColumn(info.getIndex());
/* 113 */     int z = ChunkUtil.worldCoordFromLocalCoord(wc.getZ(), ChunkUtil.zFromBlockInColumn(info.getIndex()));
/*     */ 
/*     */     
/* 116 */     long seed = worldConfig.getSeed();
/* 117 */     double randomRnd = HashUtil.random(x, y, z, seed + -1699164769L);
/*     */     
/* 119 */     BlockSpawnerEntry entry = (BlockSpawnerEntry)table.getEntries().get(randomRnd);
/* 120 */     if (entry == null)
/*     */       return; 
/* 122 */     String blockKey = entry.getBlockName();
/* 123 */     switch (BlockSpawnerPlugin.null.$SwitchMap$com$hypixel$hytale$builtin$blockspawner$BlockSpawnerEntry$RotationMode[entry.getRotationMode().ordinal()]) { default: throw new MatchException(null, null);
/*     */       case 1: 
/*     */       case 2:
/* 126 */         key = entry.getBlockName();
/* 127 */         variantRotation = ((BlockType)BlockType.getAssetMap().getAsset(key)).getVariantRotation();
/* 128 */         if (variantRotation == VariantRotation.None);
/*     */ 
/*     */ 
/*     */         
/* 132 */         randomHash = (int)HashUtil.rehash(x, y, z, seed + -1699164769L);
/* 133 */         rotationYaw = Rotation.NORMAL[(randomHash & 0xFFFF) % Rotation.NORMAL.length];
/*     */ 
/*     */       
/*     */       case 3:
/* 137 */         key = entry.getBlockName();
/* 138 */         variantRotation = ((BlockType)BlockType.getAssetMap().getAsset(key)).getVariantRotation();
/* 139 */         if (variantRotation == VariantRotation.None);
/*     */ 
/*     */ 
/*     */         
/* 143 */         spawnerRotation = RotationTuple.get(wc.getRotationIndex(x, y, z));
/* 144 */         spawnerYaw = spawnerRotation.yaw(); }
/* 145 */      RotationTuple rotation = BlockRotationUtil.getRotated(RotationTuple.NONE, Axis.Y, spawnerYaw, variantRotation);
/*     */ 
/*     */ 
/*     */     
/* 149 */     Holder<ChunkStore> holder = entry.getBlockComponents();
/*     */ 
/*     */ 
/*     */     
/* 153 */     commandBuffer.removeEntity(ref, RemoveReason.REMOVE);
/* 154 */     commandBuffer.run(_store -> {
/*     */           int flags = 4;
/*     */           if (holder != null)
/*     */             flags |= 0x2; 
/*     */           int blockId = BlockType.getAssetMap().getIndex(blockKey);
/*     */           BlockType blockType = (BlockType)BlockType.getAssetMap().getAsset(blockId);
/*     */           wc.setBlock(x, y, z, blockId, blockType, rotation.index(), 0, flags);
/*     */           if (holder != null)
/*     */             wc.setState(x, y, z, holder.clone()); 
/*     */         }); }
/*     */ 
/*     */   
/*     */   public void onEntityRemove(@Nonnull Ref<ChunkStore> ref, @Nonnull RemoveReason reason, @Nonnull Store<ChunkStore> store, @Nonnull CommandBuffer<ChunkStore> commandBuffer) {}
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\builtin\blockspawner\BlockSpawnerPlugin$BlockSpawnerSystem.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */