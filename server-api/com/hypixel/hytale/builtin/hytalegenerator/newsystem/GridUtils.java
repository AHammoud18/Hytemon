/*     */ package com.hypixel.hytale.builtin.hytalegenerator.newsystem;
/*     */ 
/*     */ import com.hypixel.hytale.builtin.hytalegenerator.VectorUtil;
/*     */ import com.hypixel.hytale.builtin.hytalegenerator.bounds.Bounds3i;
/*     */ import com.hypixel.hytale.builtin.hytalegenerator.framework.math.Calculator;
/*     */ import com.hypixel.hytale.builtin.hytalegenerator.newsystem.bufferbundle.buffers.NVoxelBuffer;
/*     */ import com.hypixel.hytale.math.vector.Vector3d;
/*     */ import com.hypixel.hytale.math.vector.Vector3i;
/*     */ import javax.annotation.Nonnull;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class GridUtils
/*     */ {
/*  18 */   public static final int BUFFER_COUNT_IN_CHUNK_Y = 320 / NVoxelBuffer.SIZE.y;
/*     */ 
/*     */   
/*     */   public static void toBufferGrid_fromVoxelGridOverlap(@Nonnull Bounds3i bounds_voxelGrid) {
/*  22 */     assert bounds_voxelGrid.isCorrect();
/*     */     
/*  24 */     VectorUtil.bitShiftRight(3, bounds_voxelGrid.min);
/*     */     
/*  26 */     if (bounds_voxelGrid.max.x % NVoxelBuffer.SIZE.x == 0) bounds_voxelGrid.max.x--; 
/*  27 */     if (bounds_voxelGrid.max.y % NVoxelBuffer.SIZE.y == 0) bounds_voxelGrid.max.y--; 
/*  28 */     if (bounds_voxelGrid.max.z % NVoxelBuffer.SIZE.z == 0) bounds_voxelGrid.max.z--; 
/*  29 */     bounds_voxelGrid.max.x >>= 3;
/*  30 */     bounds_voxelGrid.max.y >>= 3;
/*  31 */     bounds_voxelGrid.max.z >>= 3;
/*     */   }
/*     */   
/*     */   @Nonnull
/*     */   public static Bounds3i createColumnBounds_voxelGrid(@Nonnull Vector3i position_bufferGrid, int minY_voxelSpace, int maxY_voxelSpace) {
/*  36 */     assert minY_voxelSpace <= maxY_voxelSpace;
/*     */     
/*  38 */     Vector3i min = position_bufferGrid.clone();
/*  39 */     VectorUtil.bitShiftLeft(3, min);
/*     */     
/*  41 */     Vector3i max = min.clone().add(NVoxelBuffer.SIZE);
/*     */     
/*  43 */     min.y = minY_voxelSpace;
/*  44 */     max.y = maxY_voxelSpace;
/*     */     
/*  46 */     return new Bounds3i(min, max);
/*     */   }
/*     */ 
/*     */   
/*     */   @Nonnull
/*     */   public static Bounds3i createBufferBoundsInclusive_fromVoxelBounds(@Nonnull Bounds3i bounds_voxelGrid) {
/*  52 */     assert bounds_voxelGrid.isCorrect();
/*     */     
/*  54 */     Vector3i min = bounds_voxelGrid.min.clone();
/*  55 */     Vector3i max = bounds_voxelGrid.max.clone();
/*     */     
/*  57 */     min.x = Calculator.floor(min.x, NVoxelBuffer.SIZE.x);
/*  58 */     min.x >>= 3;
/*     */     
/*  60 */     min.y = Calculator.floor(min.y, NVoxelBuffer.SIZE.y);
/*  61 */     min.y >>= 3;
/*     */     
/*  63 */     min.z = Calculator.floor(min.z, NVoxelBuffer.SIZE.z);
/*  64 */     min.z >>= 3;
/*     */     
/*  66 */     int mod = Calculator.wrap(max.x, NVoxelBuffer.SIZE.x);
/*  67 */     max.x = Calculator.ceil(max.x, NVoxelBuffer.SIZE.x);
/*  68 */     max.x >>= 3;
/*  69 */     if (mod == 0) { max.x += 2; }
/*  70 */     else { max.x++; }
/*     */     
/*  72 */     mod = Calculator.wrap(max.y, NVoxelBuffer.SIZE.y);
/*  73 */     max.y = Calculator.ceil(max.y, NVoxelBuffer.SIZE.y);
/*  74 */     max.y >>= 3;
/*  75 */     if (mod == 0) { max.y += 2; }
/*  76 */     else { max.y++; }
/*     */     
/*  78 */     mod = Calculator.wrap(max.z, NVoxelBuffer.SIZE.z);
/*  79 */     max.z = Calculator.ceil(max.z, NVoxelBuffer.SIZE.z);
/*  80 */     max.z >>= 3;
/*  81 */     if (mod == 0) { max.z += 2; }
/*  82 */     else { max.z++; }
/*     */     
/*  84 */     min.dropHash();
/*  85 */     max.dropHash();
/*     */     
/*  87 */     return new Bounds3i(min, max);
/*     */   }
/*     */   
/*     */   @Nonnull
/*     */   public static Bounds3i createColumnBounds_bufferGrid(@Nonnull Vector3i position_bufferGrid, int minY_bufferGrid, int maxY_bufferGrid) {
/*  92 */     assert minY_bufferGrid <= maxY_bufferGrid;
/*     */     
/*  94 */     Vector3i min = position_bufferGrid.clone();
/*  95 */     Vector3i max = min.clone().add(Vector3i.ALL_ONES);
/*  96 */     min.y = minY_bufferGrid;
/*  97 */     max.y = maxY_bufferGrid;
/*     */     
/*  99 */     return new Bounds3i(min, max);
/*     */   }
/*     */   
/*     */   @Nonnull
/*     */   public static Bounds3i createChunkBounds_voxelGrid(int x_chunkGrid, int z_chunkGrid) {
/* 104 */     Vector3i min = new Vector3i(x_chunkGrid << 5, 0, z_chunkGrid << 5);
/* 105 */     Vector3i max = min.clone().add(32, 320, 32);
/*     */     
/* 107 */     return new Bounds3i(min, max);
/*     */   }
/*     */   
/*     */   @Nonnull
/*     */   public static Bounds3i createUnitBounds3i(@Nonnull Vector3i position) {
/* 112 */     return new Bounds3i(position, position.clone().add(Vector3i.ALL_ONES));
/*     */   }
/*     */   
/*     */   @Nonnull
/*     */   public static Bounds3i createBounds_fromRadius_originVoxelInclusive(int radius) {
/* 117 */     Vector3i min = new Vector3i(-radius, -radius, -radius);
/* 118 */     Vector3i max = new Vector3i(radius + 1, radius + 1, radius + 1);
/*     */     
/* 120 */     return new Bounds3i(min, max);
/*     */   }
/*     */   
/*     */   @Nonnull
/*     */   public static Bounds3i createBounds_fromVector_originVoxelInclusive(@Nonnull Vector3i range) {
/* 125 */     Vector3i min = (new Vector3i(range)).scale(-1);
/* 126 */     Vector3i max = (new Vector3i(range)).add(Vector3i.ALL_ONES);
/*     */     
/* 128 */     return new Bounds3i(min, max);
/*     */   }
/*     */   
/*     */   @Nonnull
/*     */   public static Bounds3i createChunkBounds_bufferGrid(int x_chunkGrid, int z_chunkGrid) {
/* 133 */     int bits = 2;
/*     */     
/* 135 */     Vector3i min = new Vector3i(x_chunkGrid << 2, 0, z_chunkGrid << 2);
/* 136 */     Vector3i max = new Vector3i(x_chunkGrid + 1 << 2, 40, z_chunkGrid + 1 << 2);
/*     */     
/* 138 */     return new Bounds3i(min, max);
/*     */   }
/*     */   
/*     */   public static void toVoxelGrid_fromBufferGrid(@Nonnull Bounds3i bounds_bufferGrid) {
/* 142 */     assert bounds_bufferGrid.isCorrect();
/*     */     
/* 144 */     VectorUtil.bitShiftLeft(3, bounds_bufferGrid.min);
/* 145 */     VectorUtil.bitShiftLeft(3, bounds_bufferGrid.max);
/*     */   }
/*     */   
/*     */   public static void toVoxelGrid_fromBufferGrid(@Nonnull Vector3i position_voxelGrid) {
/* 149 */     VectorUtil.bitShiftLeft(3, position_voxelGrid);
/*     */   }
/*     */   
/*     */   public static void toBufferGrid_fromVoxelGrid(@Nonnull Vector3i worldPosition_voxelGrid) {
/* 153 */     VectorUtil.bitShiftRight(3, worldPosition_voxelGrid);
/*     */   }
/*     */   
/*     */   public static int toBufferDistanceInclusive_fromVoxelDistance(int distance_voxelGrid) {
/* 157 */     int distance_bufferGrid = distance_voxelGrid >> 3;
/*     */     
/* 159 */     if (Calculator.wrap(distance_voxelGrid, NVoxelBuffer.SIZE.x) == 0) {
/* 160 */       return distance_bufferGrid;
/*     */     }
/*     */     
/* 163 */     return distance_bufferGrid + 1;
/*     */   }
/*     */   
/*     */   @Nonnull
/*     */   public static Vector3i toIntegerGrid_fromDecimalGrid(@Nonnull Vector3d worldPosition_decimalGrid) {
/* 168 */     Vector3i position = new Vector3i();
/* 169 */     position.x = Calculator.toIntFloored(worldPosition_decimalGrid.x);
/* 170 */     position.y = Calculator.toIntFloored(worldPosition_decimalGrid.y);
/* 171 */     position.z = Calculator.toIntFloored(worldPosition_decimalGrid.z);
/*     */     
/* 173 */     return position;
/*     */   }
/*     */   
/*     */   public static void toVoxelGridInsideBuffer_fromWorldGrid(@Nonnull Vector3i worldPosition_voxelGrid) {
/* 177 */     worldPosition_voxelGrid.x = Calculator.wrap(worldPosition_voxelGrid.x, NVoxelBuffer.SIZE.x);
/* 178 */     worldPosition_voxelGrid.y = Calculator.wrap(worldPosition_voxelGrid.y, NVoxelBuffer.SIZE.y);
/* 179 */     worldPosition_voxelGrid.z = Calculator.wrap(worldPosition_voxelGrid.z, NVoxelBuffer.SIZE.z);
/*     */   }
/*     */   
/*     */   public static int toIndexFromPositionYXZ(@Nonnull Vector3i position, @Nonnull Bounds3i bounds) {
/* 183 */     assert bounds.contains(position);
/*     */     
/* 185 */     int x = position.x - bounds.min.x;
/* 186 */     int y = position.y - bounds.min.y;
/* 187 */     int z = position.z - bounds.min.z;
/*     */     
/* 189 */     int sizeX = bounds.max.x - bounds.min.x;
/* 190 */     int sizeY = bounds.max.y - bounds.min.y;
/*     */     
/* 192 */     return y + x * sizeY + z * sizeY * sizeX;
/*     */   }
/*     */   
/*     */   public static void setBoundsYToWorldHeight_bufferGrid(@Nonnull Bounds3i bounds_bufferGrid) {
/* 196 */     assert bounds_bufferGrid.isCorrect();
/*     */     
/* 198 */     bounds_bufferGrid.min.setY(0);
/* 199 */     bounds_bufferGrid.max.setY(40);
/*     */   }
/*     */   
/*     */   public static void setBoundsYToWorldHeight_voxelGrid(@Nonnull Bounds3i bounds_voxelGrid) {
/* 203 */     assert bounds_voxelGrid.isCorrect();
/*     */     
/* 205 */     bounds_voxelGrid.min.setY(0);
/* 206 */     bounds_voxelGrid.max.setY(320);
/*     */   }
/*     */   
/*     */   public static void toVoxelPosition_fromChunkPosition(@Nonnull Vector3i chunkPosition_voxelGrid) {
/* 210 */     chunkPosition_voxelGrid.x <<= 5;
/* 211 */     chunkPosition_voxelGrid.z <<= 5;
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\builtin\hytalegenerator\newsystem\GridUtils.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */