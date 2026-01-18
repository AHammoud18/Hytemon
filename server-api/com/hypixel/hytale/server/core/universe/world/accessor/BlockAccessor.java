/*     */ package com.hypixel.hytale.server.core.universe.world.accessor;
/*     */ 
/*     */ import com.hypixel.hytale.assetstore.map.BlockTypeAssetMap;
/*     */ import com.hypixel.hytale.component.Holder;
/*     */ import com.hypixel.hytale.function.predicate.TriIntPredicate;
/*     */ import com.hypixel.hytale.math.util.ChunkUtil;
/*     */ import com.hypixel.hytale.math.vector.Vector3i;
/*     */ import com.hypixel.hytale.protocol.BlockMaterial;
/*     */ import com.hypixel.hytale.server.core.asset.type.blockhitbox.BlockBoundingBoxes;
/*     */ import com.hypixel.hytale.server.core.asset.type.blocktype.config.BlockType;
/*     */ import com.hypixel.hytale.server.core.asset.type.blocktype.config.Rotation;
/*     */ import com.hypixel.hytale.server.core.asset.type.blocktype.config.RotationTuple;
/*     */ import com.hypixel.hytale.server.core.universe.world.meta.BlockState;
/*     */ import com.hypixel.hytale.server.core.universe.world.storage.ChunkStore;
/*     */ import com.hypixel.hytale.server.core.util.FillerBlockUtil;
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
/*     */ public interface BlockAccessor
/*     */ {
/*     */   default int getBlock(@Nonnull Vector3i pos) {
/*  32 */     return getBlock(pos.getX(), pos.getY(), pos.getZ());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   default boolean setBlock(int x, int y, int z, int id, BlockType blockType) {
/*  40 */     return setBlock(x, y, z, id, blockType, 0, 0, 0);
/*     */   }
/*     */   
/*     */   default boolean setBlock(int x, int y, int z, String blockTypeKey) {
/*  44 */     return setBlock(x, y, z, blockTypeKey, 0);
/*     */   }
/*     */   
/*     */   default boolean setBlock(int x, int y, int z, String blockTypeKey, int settings) {
/*  48 */     int index = BlockType.getAssetMap().getIndex(blockTypeKey);
/*  49 */     if (index == Integer.MIN_VALUE) throw new IllegalArgumentException("Unknown key! " + blockTypeKey); 
/*  50 */     return setBlock(x, y, z, index, settings);
/*     */   }
/*     */   
/*     */   default boolean setBlock(int x, int y, int z, int id) {
/*  54 */     return setBlock(x, y, z, id, 0);
/*     */   }
/*     */   
/*     */   default boolean setBlock(int x, int y, int z, int id, int settings) {
/*  58 */     return setBlock(x, y, z, id, (BlockType)BlockType.getAssetMap().getAsset(id), 0, 0, settings);
/*     */   }
/*     */   
/*     */   default boolean setBlock(int x, int y, int z, @Nonnull BlockType blockType) {
/*  62 */     return setBlock(x, y, z, blockType, 0);
/*     */   }
/*     */   
/*     */   default boolean setBlock(int x, int y, int z, @Nonnull BlockType blockType, int settings) {
/*  66 */     String key = blockType.getId();
/*  67 */     int index = BlockType.getAssetMap().getIndex(key);
/*  68 */     if (index == Integer.MIN_VALUE) throw new IllegalArgumentException("Unknown key! " + key); 
/*  69 */     return setBlock(x, y, z, index, blockType, 0, 0, settings);
/*     */   }
/*     */   
/*     */   default boolean breakBlock(int x, int y, int z, int filler, int settings) {
/*  73 */     if ((settings & 0x10) == 0) {
/*  74 */       x -= FillerBlockUtil.unpackX(filler);
/*  75 */       y -= FillerBlockUtil.unpackY(filler);
/*  76 */       z -= FillerBlockUtil.unpackZ(filler);
/*     */     } 
/*     */     
/*  79 */     return setBlock(x, y, z, 0, BlockType.EMPTY, 0, 0, settings);
/*     */   }
/*     */   
/*     */   default boolean breakBlock(int x, int y, int z) {
/*  83 */     return breakBlock(x, y, z, 0);
/*     */   }
/*     */   
/*     */   default boolean breakBlock(int x, int y, int z, int settings) {
/*  87 */     return breakBlock(x, y, z, 0, settings);
/*     */   }
/*     */   
/*     */   default boolean testBlocks(int x, int y, int z, @Nonnull BlockType blockTypeToTest, int rotation, @Nonnull TriIntPredicate predicate) {
/*  91 */     int worldX = (getX() << 5) + (x & 0x1F);
/*  92 */     int worldZ = (getZ() << 5) + (z & 0x1F);
/*     */     
/*  94 */     return FillerBlockUtil.testFillerBlocks(((BlockBoundingBoxes)BlockBoundingBoxes.getAssetMap().getAsset(blockTypeToTest.getHitboxTypeIndex())).get(rotation), (x1, y1, z1) -> {
/*     */           int blockX = worldX + x1;
/*     */           int blockY = y + y1;
/*     */           int blockZ = worldZ + z1;
/*     */           return predicate.test(blockX, blockY, blockZ);
/*     */         });
/*     */   }
/*     */ 
/*     */   
/*     */   default boolean testBlockTypes(int x, int y, int z, @Nonnull BlockType blockTypeToTest, int rotation, @Nonnull IChunkAccessorSync.TestBlockFunction predicate) {
/* 104 */     int worldX = (getX() << 5) + (x & 0x1F);
/* 105 */     int worldZ = (getZ() << 5) + (z & 0x1F);
/*     */     
/* 107 */     return testBlocks(x, y, z, blockTypeToTest, rotation, (blockX, blockY, blockZ) -> {
/*     */           int block;
/*     */           int otherRotation;
/*     */           int filler;
/*     */           boolean sameChunk = ChunkUtil.isSameChunk(worldX, worldZ, blockX, blockZ);
/*     */           if (sameChunk) {
/*     */             block = getBlock(blockX, blockY, blockZ);
/*     */             otherRotation = getRotationIndex(blockX, blockY, blockZ);
/*     */             filler = getFiller(blockX, blockY, blockZ);
/*     */           } else {
/*     */             BlockAccessor chunk = getChunkAccessor().getNonTickingChunk(ChunkUtil.indexChunkFromBlock(blockX, blockZ));
/*     */             block = chunk.getBlock(blockX, blockY, blockZ);
/*     */             otherRotation = chunk.getRotationIndex(blockX, blockY, blockZ);
/*     */             filler = chunk.getFiller(blockX, blockY, blockZ);
/*     */           } 
/*     */           return predicate.test(blockX, blockY, blockZ, (BlockType)BlockType.getAssetMap().getAsset(block), otherRotation, filler);
/*     */         });
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   default boolean placeBlock(int x, int y, int z, String originalBlockTypeKey, @Nonnull Rotation yaw, @Nonnull Rotation pitch, @Nonnull Rotation roll, int settings) {
/* 129 */     return placeBlock(x, y, z, originalBlockTypeKey, RotationTuple.of(yaw, pitch, roll), settings, true);
/*     */   }
/*     */   
/*     */   default boolean placeBlock(int x, int y, int z, String originalBlockTypeKey, @Nonnull RotationTuple rotationTuple, int settings, boolean validatePlacement) {
/* 133 */     BlockTypeAssetMap<String, BlockType> assetMap = BlockType.getAssetMap();
/*     */     
/* 135 */     BlockType placedBlockType = (BlockType)assetMap.getAsset(originalBlockTypeKey);
/* 136 */     int rotationIndex = rotationTuple.index();
/*     */     
/* 138 */     if (validatePlacement && !testPlaceBlock(x, y, z, placedBlockType, rotationIndex)) return false;
/*     */     
/* 140 */     int setBlockSettings = 0;
/* 141 */     if ((settings & 0x2) != 0) {
/* 142 */       setBlockSettings |= 0x100;
/*     */     }
/* 144 */     setBlock(x, y, z, assetMap.getIndex(originalBlockTypeKey), placedBlockType, rotationIndex, 0, setBlockSettings);
/* 145 */     return true;
/*     */   }
/*     */   
/*     */   default boolean placeBlock(int x, int y, int z, String blockTypeKey, @Nonnull Rotation yaw, @Nonnull Rotation pitch, @Nonnull Rotation roll) {
/* 149 */     return placeBlock(x, y, z, blockTypeKey, yaw, pitch, roll, 0);
/*     */   }
/*     */   
/*     */   default boolean testPlaceBlock(int x, int y, int z, @Nonnull BlockType blockTypeToTest, int rotationIndex) {
/* 153 */     return testPlaceBlock(x, y, z, blockTypeToTest, rotationIndex, (x1, y1, z1, blockType, rotation, filler) -> false);
/*     */   }
/*     */   
/*     */   default boolean testPlaceBlock(int x, int y, int z, @Nonnull BlockType blockTypeToTest, int rotationIndex, @Nonnull IChunkAccessorSync.TestBlockFunction filter) {
/* 157 */     return testBlockTypes(x, y, z, blockTypeToTest, rotationIndex, (blockX, blockY, blockZ, blockType, rotation, filler) -> (blockType == BlockType.EMPTY) ? true : ((blockType.getMaterial() == BlockMaterial.Empty) ? true : (
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */         
/* 164 */         (filler != 0 && blockType.isUnknown()) ? true : filter.test(blockX, blockY, blockZ, blockType, rotation, filler))));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Nullable
/*     */   default BlockType getBlockType(int x, int y, int z) {
/* 172 */     return (BlockType)BlockType.getAssetMap().getAsset(getBlock(x, y, z));
/*     */   }
/*     */   
/*     */   @Nullable
/*     */   default BlockType getBlockType(@Nonnull Vector3i block) {
/* 177 */     return getBlockType(block.getX(), block.getY(), block.getZ());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Deprecated
/*     */   default void setState(int x, int y, int z, BlockState state) {
/* 197 */     setState(x, y, z, state, true);
/*     */   }
/*     */   
/*     */   default void setBlockInteractionState(@Nonnull Vector3i blockPosition, @Nonnull BlockType blockType, @Nonnull String state) {
/* 201 */     setBlockInteractionState(blockPosition.x, blockPosition.y, blockPosition.z, blockType, state, false);
/*     */   }
/*     */   
/*     */   default void setBlockInteractionState(int x, int y, int z, @Nonnull BlockType blockType, @Nonnull String state, boolean force) {
/* 205 */     String currentState = getCurrentInteractionState(blockType);
/* 206 */     if (!force && currentState != null && currentState.equals(state))
/*     */       return; 
/* 208 */     BlockType newState = blockType.getBlockForState(state);
/* 209 */     if (newState == null)
/* 210 */       return;  int settings = 198;
/*     */     
/* 212 */     int currentRotation = getRotationIndex(x, y, z);
/* 213 */     setBlock(x, y, z, BlockType.getAssetMap().getIndex(newState.getId()), newState, currentRotation, 0, 198);
/*     */   }
/*     */   
/*     */   @Nullable
/*     */   static String getCurrentInteractionState(@Nonnull BlockType blockType) {
/* 218 */     return (blockType.getState() != null) ? blockType.getStateForBlock(blockType) : null;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Deprecated(forRemoval = true)
/*     */   default RotationTuple getRotation(int x, int y, int z) {
/* 238 */     return RotationTuple.get(getRotationIndex(x, y, z));
/*     */   }
/*     */   
/*     */   int getX();
/*     */   
/*     */   int getZ();
/*     */   
/*     */   ChunkAccessor getChunkAccessor();
/*     */   
/*     */   int getBlock(int paramInt1, int paramInt2, int paramInt3);
/*     */   
/*     */   boolean setBlock(int paramInt1, int paramInt2, int paramInt3, int paramInt4, BlockType paramBlockType, int paramInt5, int paramInt6, int paramInt7);
/*     */   
/*     */   boolean setTicking(int paramInt1, int paramInt2, int paramInt3, boolean paramBoolean);
/*     */   
/*     */   boolean isTicking(int paramInt1, int paramInt2, int paramInt3);
/*     */   
/*     */   @Nullable
/*     */   @Deprecated
/*     */   BlockState getState(int paramInt1, int paramInt2, int paramInt3);
/*     */   
/*     */   @Nullable
/*     */   @Deprecated
/*     */   Holder<ChunkStore> getBlockComponentHolder(int paramInt1, int paramInt2, int paramInt3);
/*     */   
/*     */   @Deprecated
/*     */   void setState(int paramInt1, int paramInt2, int paramInt3, BlockState paramBlockState, boolean paramBoolean);
/*     */   
/*     */   @Deprecated(forRemoval = true)
/*     */   int getFluidId(int paramInt1, int paramInt2, int paramInt3);
/*     */   
/*     */   @Deprecated(forRemoval = true)
/*     */   byte getFluidLevel(int paramInt1, int paramInt2, int paramInt3);
/*     */   
/*     */   @Deprecated(forRemoval = true)
/*     */   int getSupportValue(int paramInt1, int paramInt2, int paramInt3);
/*     */   
/*     */   @Deprecated(forRemoval = true)
/*     */   int getFiller(int paramInt1, int paramInt2, int paramInt3);
/*     */   
/*     */   @Deprecated(forRemoval = true)
/*     */   int getRotationIndex(int paramInt1, int paramInt2, int paramInt3);
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\server\cor\\universe\world\accessor\BlockAccessor.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */