/*     */ package com.hypixel.hytale.server.core.universe.world.accessor;
/*     */ 
/*     */ import com.hypixel.hytale.component.Holder;
/*     */ import com.hypixel.hytale.math.util.ChunkUtil;
/*     */ import com.hypixel.hytale.math.vector.Vector3i;
/*     */ import com.hypixel.hytale.protocol.BlockPosition;
/*     */ import com.hypixel.hytale.server.core.asset.type.blocktype.config.BlockType;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
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
/*     */ public interface IChunkAccessorSync<WorldChunk extends BlockAccessor>
/*     */ {
/*     */   @Nullable
/*     */   WorldChunk getChunkIfInMemory(long paramLong);
/*     */   
/*     */   @Nullable
/*     */   WorldChunk loadChunkIfInMemory(long paramLong);
/*     */   
/*     */   @Nullable
/*     */   WorldChunk getChunkIfLoaded(long paramLong);
/*     */   
/*     */   @Nullable
/*     */   WorldChunk getChunkIfNonTicking(long paramLong);
/*     */   
/*     */   @Nullable
/*     */   WorldChunk getChunk(long paramLong);
/*     */   
/*     */   @Nullable
/*     */   WorldChunk getNonTickingChunk(long paramLong);
/*     */   
/*     */   default int getBlock(@Nonnull Vector3i pos) {
/*  74 */     return getChunk(ChunkUtil.indexChunkFromBlock(pos.getX(), pos.getZ())).getBlock(pos.getX(), pos.getY(), pos.getZ());
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
/*     */   default int getBlock(int x, int y, int z) {
/*  86 */     return getChunk(ChunkUtil.indexChunkFromBlock(x, z)).getBlock(x, y, z);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Nullable
/*     */   default BlockType getBlockType(@Nonnull Vector3i pos) {
/*  98 */     return getBlockType(pos.getX(), pos.getY(), pos.getZ());
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
/*     */   @Nullable
/*     */   default BlockType getBlockType(int x, int y, int z) {
/* 112 */     WorldChunk chunk = getChunk(ChunkUtil.indexChunkFromBlock(x, z));
/* 113 */     int blockId = chunk.getBlock(x, y, z);
/* 114 */     return (BlockType)BlockType.getAssetMap().getAsset(blockId);
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
/*     */   default void setBlock(int x, int y, int z, String blockTypeKey) {
/* 126 */     setBlock(x, y, z, blockTypeKey, 0);
/*     */   }
/*     */   
/*     */   default void setBlock(int x, int y, int z, String blockTypeKey, int settings) {
/* 130 */     getChunk(ChunkUtil.indexChunkFromBlock(x, z)).setBlock(x, y, z, blockTypeKey, settings);
/*     */   }
/*     */   
/*     */   default boolean breakBlock(int x, int y, int z, int settings) {
/* 134 */     return getChunk(ChunkUtil.indexChunkFromBlock(x, z)).breakBlock(x, y, z, settings);
/*     */   }
/*     */   
/*     */   default boolean testBlockTypes(int x, int y, int z, @Nonnull BlockType blockTypeToTest, int rotation, @Nonnull TestBlockFunction predicate) {
/* 138 */     return getChunk(ChunkUtil.indexChunkFromBlock(x, z)).testBlockTypes(x, y, z, blockTypeToTest, rotation, predicate);
/*     */   }
/*     */   
/*     */   default boolean testPlaceBlock(int x, int y, int z, @Nonnull BlockType blockTypeToTest, int rotation) {
/* 142 */     return getChunk(ChunkUtil.indexChunkFromBlock(x, z)).testPlaceBlock(x, y, z, blockTypeToTest, rotation);
/*     */   }
/*     */   
/*     */   default boolean testPlaceBlock(int x, int y, int z, @Nonnull BlockType blockTypeToTest, int rotation, @Nonnull TestBlockFunction predicate) {
/* 146 */     return getChunk(ChunkUtil.indexChunkFromBlock(x, z)).testPlaceBlock(x, y, z, blockTypeToTest, rotation, predicate);
/*     */   }
/*     */   
/*     */   @Nullable
/*     */   @Deprecated
/*     */   default BlockState getState(int x, int y, int z, boolean followFiller) {
/* 152 */     WorldChunk chunk = getChunk(ChunkUtil.indexChunkFromBlock(x, z));
/* 153 */     if (followFiller) {
/* 154 */       int filler = chunk.getFiller(x, y, z);
/*     */       
/* 156 */       if (filler != 0) {
/* 157 */         x -= FillerBlockUtil.unpackX(filler);
/* 158 */         y -= FillerBlockUtil.unpackY(filler);
/* 159 */         z -= FillerBlockUtil.unpackZ(filler);
/*     */       } 
/*     */     } 
/* 162 */     if (y < 0 || y >= 320) return null; 
/* 163 */     return chunk.getState(x, y, z);
/*     */   }
/*     */   
/*     */   @Nullable
/*     */   default Holder<ChunkStore> getBlockComponentHolder(int x, int y, int z) {
/* 168 */     if (y < 0 || y >= 320) return null; 
/* 169 */     return getChunk(ChunkUtil.indexChunkFromBlock(x, z)).getBlockComponentHolder(x, y, z);
/*     */   }
/*     */   
/*     */   default void setBlockInteractionState(@Nonnull Vector3i blockPosition, @Nonnull BlockType blockType, @Nonnull String state) {
/* 173 */     getChunk(ChunkUtil.indexChunkFromBlock(blockPosition.x, blockPosition.z)).setBlockInteractionState(blockPosition, blockType, state);
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
/*     */   @Nonnull
/*     */   @Deprecated(forRemoval = true)
/*     */   default BlockPosition getBaseBlock(@Nonnull BlockPosition position) {
/* 189 */     WorldChunk chunk = getNonTickingChunk(ChunkUtil.indexChunkFromBlock(position.x, position.z));
/* 190 */     int filler = chunk.getFiller(position.x, position.y, position.z);
/*     */     
/* 192 */     if (filler != 0) {
/* 193 */       return new BlockPosition(position.x - 
/* 194 */           FillerBlockUtil.unpackX(filler), position.y - 
/* 195 */           FillerBlockUtil.unpackY(filler), position.z - 
/* 196 */           FillerBlockUtil.unpackZ(filler));
/*     */     }
/*     */     
/* 199 */     return position;
/*     */   }
/*     */   
/*     */   @FunctionalInterface
/*     */   public static interface TestBlockFunction {
/*     */     boolean test(int param1Int1, int param1Int2, int param1Int3, BlockType param1BlockType, int param1Int4, int param1Int5);
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\server\cor\\universe\world\accessor\IChunkAccessorSync.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */