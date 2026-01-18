/*     */ package com.hypixel.hytale.builtin.buildertools;
/*     */ 
/*     */ import com.hypixel.hytale.builtin.buildertools.utils.Material;
/*     */ import com.hypixel.hytale.math.util.ChunkUtil;
/*     */ import com.hypixel.hytale.math.vector.Vector3i;
/*     */ import com.hypixel.hytale.server.core.prefab.selection.mask.BlockMask;
/*     */ import com.hypixel.hytale.server.core.prefab.selection.standard.BlockSelection;
/*     */ import com.hypixel.hytale.server.core.universe.world.World;
/*     */ import com.hypixel.hytale.server.core.universe.world.accessor.BlockAccessor;
/*     */ import com.hypixel.hytale.server.core.universe.world.accessor.ChunkAccessor;
/*     */ import com.hypixel.hytale.server.core.universe.world.accessor.LocalCachedChunkAccessor;
/*     */ import com.hypixel.hytale.server.core.universe.world.accessor.OverridableChunkAccessor;
/*     */ import javax.annotation.Nonnull;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class EditOperation
/*     */ {
/*     */   private final BlockMask blockMask;
/*     */   @Nonnull
/*     */   private final OverridableChunkAccessor accessor;
/*     */   @Nonnull
/*     */   private final BlockSelection before;
/*     */   @Nonnull
/*     */   private final BlockSelection after;
/*     */   private final Vector3i min;
/*     */   private final Vector3i max;
/*     */   
/*     */   public EditOperation(@Nonnull World world, int x, int y, int z, int editRange, Vector3i min, Vector3i max, BlockMask blockMask) {
/*  31 */     this.blockMask = blockMask;
/*  32 */     this.accessor = (OverridableChunkAccessor)LocalCachedChunkAccessor.atWorldCoords((ChunkAccessor)world, x, z, editRange);
/*  33 */     this.min = min;
/*  34 */     this.max = max;
/*     */     
/*  36 */     this.before = new BlockSelection();
/*  37 */     this.before.setPosition(x, y, z);
/*  38 */     if (min != null && max != null) {
/*  39 */       this.before.setSelectionArea(min, max);
/*     */     }
/*  41 */     this.after = new BlockSelection(this.before);
/*     */   }
/*     */   
/*     */   public BlockMask getBlockMask() {
/*  45 */     return this.blockMask;
/*     */   }
/*     */   
/*     */   @Nonnull
/*     */   public BlockSelection getBefore() {
/*  50 */     return this.before;
/*     */   }
/*     */   
/*     */   @Nonnull
/*     */   public BlockSelection getAfter() {
/*  55 */     return this.after;
/*     */   }
/*     */   
/*     */   @Nonnull
/*     */   public OverridableChunkAccessor getAccessor() {
/*  60 */     return this.accessor;
/*     */   }
/*     */   
/*     */   public int getBlock(int x, int y, int z) {
/*  64 */     return this.accessor.getBlock(x, y, z);
/*     */   }
/*     */   
/*     */   public boolean setBlock(int x, int y, int z, int blockId) {
/*  68 */     return setBlock(x, y, z, blockId, 0);
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
/*     */   public boolean setBlock(int x, int y, int z, int blockId, int rotation) {
/*  82 */     int currentBlock = getBlock(x, y, z);
/*  83 */     int currentFluid = getFluid(x, y, z);
/*     */     
/*  85 */     if (this.blockMask != null && this.blockMask.isExcluded((ChunkAccessor)this.accessor, x, y, z, this.min, this.max, currentBlock, currentFluid)) return false;
/*     */     
/*  87 */     BlockAccessor blocks = this.accessor.getChunkIfInMemory(ChunkUtil.indexChunkFromBlock(x, z));
/*  88 */     if (blocks == null) return false;
/*     */     
/*  90 */     if (!this.before.hasBlockAtWorldPos(x, y, z)) this.before.addBlockAtWorldPos(x, y, z, currentBlock, blocks.getRotationIndex(x, y, z), blocks.getFiller(x, y, z), blocks.getSupportValue(x, y, z), blocks.getBlockComponentHolder(x, y, z)); 
/*  91 */     this.after.addBlockAtWorldPos(x, y, z, blockId, rotation, 0, 0);
/*     */ 
/*     */     
/*  94 */     if (blockId == 0) {
/*  95 */       setFluid(x, y, z, 0, (byte)0);
/*     */     }
/*     */     
/*  98 */     return true;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private boolean setFluid(int x, int y, int z, int fluidId, byte fluidLevel) {
/* 106 */     BlockAccessor chunk = this.accessor.getChunkIfInMemory(ChunkUtil.indexChunkFromBlock(x, z));
/* 107 */     if (chunk == null) return false;
/*     */     
/* 109 */     int currentBlock = getBlock(x, y, z);
/* 110 */     int currentFluid = getFluid(x, y, z);
/*     */     
/* 112 */     if (this.blockMask != null && this.blockMask.isExcluded((ChunkAccessor)this.accessor, x, y, z, this.min, this.max, currentBlock, currentFluid)) return false;
/*     */ 
/*     */     
/* 115 */     int beforeFluid = this.before.getFluidAtWorldPos(x, y, z);
/* 116 */     if (beforeFluid < 0) {
/* 117 */       int originalFluidId = chunk.getFluidId(x, y, z);
/* 118 */       byte originalFluidLevel = chunk.getFluidLevel(x, y, z);
/* 119 */       this.before.addFluidAtWorldPos(x, y, z, originalFluidId, originalFluidLevel);
/*     */     } 
/*     */     
/* 122 */     this.after.addFluidAtWorldPos(x, y, z, fluidId, fluidLevel);
/* 123 */     return true;
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
/* 135 */     BlockAccessor chunk = this.accessor.getChunkIfInMemory(ChunkUtil.indexChunkFromBlock(x, z));
/* 136 */     if (chunk != null) {
/* 137 */       return chunk.getFluidId(x, y, z);
/*     */     }
/* 139 */     return 0;
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
/*     */   public boolean setMaterial(int x, int y, int z, @Nonnull Material material) {
/* 155 */     if (material.isFluid()) {
/* 156 */       return setFluid(x, y, z, material.getFluidId(), material.getFluidLevel());
/*     */     }
/* 158 */     return setBlock(x, y, z, material.getBlockId(), material.getRotation());
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\builtin\buildertools\EditOperation.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */