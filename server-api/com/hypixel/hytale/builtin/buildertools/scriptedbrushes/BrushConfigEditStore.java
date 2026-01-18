/*     */ package com.hypixel.hytale.builtin.buildertools.scriptedbrushes;
/*     */ 
/*     */ import com.hypixel.hytale.assetstore.map.BlockTypeAssetMap;
/*     */ import com.hypixel.hytale.builtin.buildertools.BuilderToolsPlugin;
/*     */ import com.hypixel.hytale.builtin.buildertools.utils.Material;
/*     */ import com.hypixel.hytale.math.block.BlockUtil;
/*     */ import com.hypixel.hytale.math.util.ChunkUtil;
/*     */ import com.hypixel.hytale.math.vector.Vector3i;
/*     */ import com.hypixel.hytale.server.core.asset.type.blocktype.config.BlockType;
/*     */ import com.hypixel.hytale.server.core.prefab.selection.mask.BlockMask;
/*     */ import com.hypixel.hytale.server.core.prefab.selection.standard.BlockSelection;
/*     */ import com.hypixel.hytale.server.core.universe.world.World;
/*     */ import com.hypixel.hytale.server.core.universe.world.accessor.ChunkAccessor;
/*     */ import com.hypixel.hytale.server.core.universe.world.chunk.WorldChunk;
/*     */ import it.unimi.dsi.fastutil.ints.Int2IntMap;
/*     */ import it.unimi.dsi.fastutil.ints.Int2IntMaps;
/*     */ import it.unimi.dsi.fastutil.ints.Int2IntOpenHashMap;
/*     */ import it.unimi.dsi.fastutil.longs.LongOpenHashSet;
/*     */ import it.unimi.dsi.fastutil.objects.ObjectIterator;
/*     */ import javax.annotation.Nonnull;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class BrushConfigEditStore
/*     */ {
/*     */   @Nonnull
/*     */   private final BrushConfig brushConfig;
/*     */   @Nonnull
/*     */   private final BrushConfigChunkAccessor accessor;
/*     */   @Nonnull
/*     */   private final BlockSelection before;
/*     */   @Nonnull
/*     */   private final BlockSelection previous;
/*     */   private BlockSelection current;
/*     */   private final LongOpenHashSet packedPlacedBlockPositions;
/*     */   
/*     */   public BrushConfigEditStore(LongOpenHashSet packedPlacedBlockPositions, @Nonnull BrushConfig brushConfig, World world) {
/*  42 */     this.brushConfig = brushConfig;
/*  43 */     this.packedPlacedBlockPositions = packedPlacedBlockPositions;
/*     */     
/*  45 */     Vector3i origin = brushConfig.getOrigin();
/*  46 */     int shapeWidth = brushConfig.getShapeWidth();
/*  47 */     int shapeHeight = brushConfig.getShapeHeight();
/*  48 */     int halfWidth = shapeWidth / 2;
/*  49 */     int halfHeight = shapeHeight / 2;
/*     */     
/*  51 */     this.accessor = BrushConfigChunkAccessor.atWorldCoords(this, (ChunkAccessor<WorldChunk>)world, origin.x, origin.z, shapeWidth * 2);
/*     */     
/*  53 */     this.before = new BlockSelection();
/*  54 */     this.before.setPosition(origin.x, origin.y, origin.z);
/*     */     
/*  56 */     this.before.setSelectionArea(new Vector3i(origin.x - halfWidth, origin.y - halfHeight, origin.z - halfWidth), new Vector3i(origin.x + halfWidth, origin.y + halfHeight, origin.z + halfWidth));
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*  61 */     this.previous = new BlockSelection(this.before);
/*  62 */     this.current = new BlockSelection(this.before);
/*     */   }
/*     */   
/*     */   @Nonnull
/*     */   public BrushConfigChunkAccessor getAccessor() {
/*  67 */     return this.accessor;
/*     */   }
/*     */   
/*     */   public int getOriginalBlock(int x, int y, int z) {
/*  71 */     return this.accessor.getBlockIgnoringHistory(x, y, z);
/*     */   }
/*     */   
/*     */   public int getBlock(int x, int y, int z) {
/*  75 */     if (this.previous.hasBlockAtWorldPos(x, y, z)) {
/*  76 */       return this.previous.getBlockAtWorldPos(x, y, z);
/*     */     }
/*     */     
/*  79 */     return getOriginalBlock(x, y, z);
/*     */   }
/*     */   
/*     */   public int getBlockIncludingCurrent(int x, int y, int z) {
/*  83 */     if (this.current.hasBlockAtWorldPos(x, y, z)) {
/*  84 */       return this.current.getBlockAtWorldPos(x, y, z);
/*     */     }
/*     */     
/*  87 */     return getBlock(x, y, z);
/*     */   }
/*     */   
/*     */   public boolean setBlock(int x, int y, int z, int blockId) {
/*  91 */     boolean hasHistory = (this.previous.hasBlockAtWorldPos(x, y, z) || this.previous.getFluidAtWorldPos(x, y, z) >= 0);
/*  92 */     switch (this.brushConfig.getHistoryMask()) {
/*     */       
/*     */       case Only:
/*  95 */         if (!hasHistory) return false;
/*     */         
/*     */         break;
/*     */       case Not:
/*  99 */         if (hasHistory) return false;
/*     */         
/*     */         break;
/*     */     } 
/* 103 */     if (this.brushConfig.getRandom().nextInt(100) >= this.brushConfig.getDensity()) return false;
/*     */ 
/*     */     
/* 106 */     if (getOriginalBlock(x, y, z) == 0) {
/* 107 */       this.packedPlacedBlockPositions.add(BlockUtil.pack(x, y, z));
/*     */     }
/*     */     
/* 110 */     int currentBlock = getBlock(x, y, z);
/* 111 */     int currentFluid = getFluid(x, y, z);
/*     */     
/* 113 */     BlockMask blockMask = this.brushConfig.getBlockMask();
/* 114 */     if (blockMask != null && blockMask.isExcluded((ChunkAccessor)this.accessor, x, y, z, null, null, currentBlock, currentFluid)) return false;
/*     */     
/* 116 */     if (!this.before.hasBlockAtWorldPos(x, y, z)) {
/* 117 */       WorldChunk blocks = this.accessor.getChunkIfInMemory(ChunkUtil.indexChunkFromBlock(x, z));
/* 118 */       if (blocks != null) {
/* 119 */         this.before.addBlockAtWorldPos(x, y, z, currentBlock, blocks.getRotationIndex(x, y, z), blocks.getFiller(x, y, z), blocks.getSupportValue(x, y, z), blocks.getBlockComponentHolder(x, y, z));
/*     */       }
/*     */     } 
/*     */     
/* 123 */     this.current.addBlockAtWorldPos(x, y, z, blockId, 0, 0, 0);
/*     */     
/* 125 */     return true;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private boolean setFluid(int x, int y, int z, int fluidId, byte fluidLevel) {
/* 133 */     boolean hasHistory = (this.previous.hasBlockAtWorldPos(x, y, z) || this.previous.getFluidAtWorldPos(x, y, z) >= 0);
/* 134 */     switch (this.brushConfig.getHistoryMask()) {
/*     */       
/*     */       case Only:
/* 137 */         if (!hasHistory) return false;
/*     */         
/*     */         break;
/*     */       case Not:
/* 141 */         if (hasHistory) return false;
/*     */         
/*     */         break;
/*     */     } 
/* 145 */     if (this.brushConfig.getRandom().nextInt(100) >= this.brushConfig.getDensity()) return false;
/*     */     
/* 147 */     int currentBlock = getBlock(x, y, z);
/* 148 */     int currentFluid = getFluid(x, y, z);
/*     */     
/* 150 */     BlockMask blockMask = this.brushConfig.getBlockMask();
/* 151 */     if (blockMask != null && blockMask.isExcluded((ChunkAccessor)this.accessor, x, y, z, null, null, currentBlock, currentFluid)) return false;
/*     */ 
/*     */     
/* 154 */     int beforeFluid = this.before.getFluidAtWorldPos(x, y, z);
/* 155 */     if (beforeFluid < 0) {
/* 156 */       WorldChunk chunk = this.accessor.getChunkIfInMemory(ChunkUtil.indexChunkFromBlock(x, z));
/* 157 */       if (chunk != null) {
/* 158 */         int originalFluidId = chunk.getFluidId(x, y, z);
/* 159 */         byte originalFluidLevel = chunk.getFluidLevel(x, y, z);
/* 160 */         this.before.addFluidAtWorldPos(x, y, z, originalFluidId, originalFluidLevel);
/*     */       } 
/*     */     } 
/*     */     
/* 164 */     this.current.addFluidAtWorldPos(x, y, z, fluidId, fluidLevel);
/*     */     
/* 166 */     return true;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private int getOriginalFluid(int x, int y, int z) {
/* 173 */     WorldChunk chunk = this.accessor.getChunkIfInMemory(ChunkUtil.indexChunkFromBlock(x, z));
/* 174 */     if (chunk != null) {
/* 175 */       return chunk.getFluidId(x, y, z);
/*     */     }
/* 177 */     return 0;
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
/*     */   public int getFluid(int x, int y, int z) {
/* 189 */     int previousFluid = this.previous.getFluidAtWorldPos(x, y, z);
/* 190 */     if (previousFluid >= 0) {
/* 191 */       return previousFluid;
/*     */     }
/* 193 */     return getOriginalFluid(x, y, z);
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
/*     */   public boolean setMaterial(int x, int y, int z, @Nonnull Material material) {
/* 210 */     if (material.isFluid()) {
/* 211 */       return setFluid(x, y, z, material.getFluidId(), material.getFluidLevel());
/*     */     }
/* 213 */     boolean result = setBlock(x, y, z, material.getBlockId());
/*     */     
/* 215 */     if (result && material.isEmpty()) {
/* 216 */       setFluid(x, y, z, 0, (byte)0);
/*     */     }
/* 218 */     return result;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Nonnull
/*     */   public BuilderToolsPlugin.BuilderState.BlocksSampleData getBlockSampledataIncludingPreviousStages(int x, int y, int z, int radius) {
/* 227 */     BuilderToolsPlugin.BuilderState.BlocksSampleData data = new BuilderToolsPlugin.BuilderState.BlocksSampleData();
/* 228 */     Int2IntOpenHashMap int2IntOpenHashMap = new Int2IntOpenHashMap();
/* 229 */     for (int ix = x - radius; ix <= x + radius; ix++) {
/* 230 */       for (int iz = z - radius; iz <= z + radius; iz++) {
/* 231 */         for (int iy = y - radius; iy <= y + radius; iy++) {
/* 232 */           int currentBlock = getBlock(ix, iy, iz);
/* 233 */           int2IntOpenHashMap.put(currentBlock, int2IntOpenHashMap.getOrDefault(currentBlock, 0) + 1);
/*     */         } 
/*     */       } 
/*     */     } 
/* 237 */     BlockTypeAssetMap<String, BlockType> assetMap = BlockType.getAssetMap();
/* 238 */     for (ObjectIterator<Int2IntMap.Entry> objectIterator = Int2IntMaps.fastIterable((Int2IntMap)int2IntOpenHashMap).iterator(); objectIterator.hasNext(); ) { Int2IntMap.Entry pair = objectIterator.next();
/* 239 */       int block = pair.getIntKey();
/* 240 */       int count = pair.getIntValue();
/* 241 */       if (count > data.mainBlockCount) {
/* 242 */         data.mainBlock = block;
/* 243 */         data.mainBlockCount = count;
/*     */       } 
/* 245 */       BlockType blockType = (BlockType)assetMap.getAsset(block);
/* 246 */       if (count > data.mainBlockNotAirCount && block != 0) {
/* 247 */         data.mainBlockNotAir = block;
/* 248 */         data.mainBlockNotAirCount = count;
/*     */       }  }
/*     */     
/* 251 */     return data;
/*     */   }
/*     */   
/*     */   public void flushCurrentEditsToPrevious() {
/* 255 */     this.previous.add(this.current);
/* 256 */     this.current = new BlockSelection();
/* 257 */     this.current.setPosition((this.brushConfig.getOrigin()).x, (this.brushConfig.getOrigin()).y, (this.brushConfig.getOrigin()).z);
/*     */   }
/*     */   
/*     */   @Nonnull
/*     */   public BlockSelection getAfter() {
/* 262 */     return this.previous;
/*     */   }
/*     */   
/*     */   @Nonnull
/*     */   public BlockSelection getBefore() {
/* 267 */     return this.before;
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\builtin\buildertools\scriptedbrushes\BrushConfigEditStore.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */