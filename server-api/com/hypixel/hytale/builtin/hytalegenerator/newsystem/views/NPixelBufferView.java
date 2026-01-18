/*     */ package com.hypixel.hytale.builtin.hytalegenerator.newsystem.views;
/*     */ 
/*     */ import com.hypixel.hytale.builtin.hytalegenerator.bounds.Bounds3i;
/*     */ import com.hypixel.hytale.builtin.hytalegenerator.datastructures.voxelspace.VoxelConsumer;
/*     */ import com.hypixel.hytale.builtin.hytalegenerator.datastructures.voxelspace.VoxelSpace;
/*     */ import com.hypixel.hytale.builtin.hytalegenerator.newsystem.GridUtils;
/*     */ import com.hypixel.hytale.builtin.hytalegenerator.newsystem.bufferbundle.NBufferBundle;
/*     */ import com.hypixel.hytale.builtin.hytalegenerator.newsystem.bufferbundle.buffers.NPixelBuffer;
/*     */ import com.hypixel.hytale.math.vector.Vector3i;
/*     */ import java.util.function.Predicate;
/*     */ import javax.annotation.Nonnull;
/*     */ import javax.annotation.Nullable;
/*     */ 
/*     */ public class NPixelBufferView<T>
/*     */   implements VoxelSpace<T>
/*     */ {
/*     */   public static final int Y_LEVEL_BUFFER_GRID = 0;
/*     */   public static final int Y_LEVEL_VOXEL_GRID = 0;
/*     */   private final Class<T> voxelType;
/*     */   private final NBufferBundle.Access.View bufferAccess;
/*     */   private final Bounds3i bounds_voxelGrid;
/*     */   private final Vector3i size_voxelGrid;
/*     */   
/*     */   public NPixelBufferView(@Nonnull NBufferBundle.Access.View bufferAccess, @Nonnull Class<T> pixelType) {
/*  25 */     assert (bufferAccess.getBounds_bufferGrid()).min.y <= 0 && (bufferAccess.getBounds_bufferGrid()).max.y > 0;
/*     */     
/*  27 */     this.bufferAccess = bufferAccess;
/*  28 */     this.voxelType = pixelType;
/*     */     
/*  30 */     this.bounds_voxelGrid = bufferAccess.getBounds_bufferGrid();
/*  31 */     GridUtils.toVoxelGrid_fromBufferGrid(this.bounds_voxelGrid);
/*  32 */     this.bounds_voxelGrid.min.y = 0;
/*  33 */     this.bounds_voxelGrid.max.y = 1;
/*     */     
/*  35 */     this.size_voxelGrid = this.bounds_voxelGrid.getSize();
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean set(T content, int x, int y, int z) {
/*  40 */     return set(content, new Vector3i(x, y, z));
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean set(T value, @Nonnull Vector3i position_voxelGrid) {
/*  45 */     assert this.bounds_voxelGrid.contains(position_voxelGrid);
/*     */     
/*  47 */     NPixelBuffer<T> buffer = getBuffer(position_voxelGrid);
/*  48 */     Vector3i positionInBuffer_voxelGrid = position_voxelGrid.clone();
/*  49 */     GridUtils.toVoxelGridInsideBuffer_fromWorldGrid(positionInBuffer_voxelGrid);
/*     */     
/*  51 */     buffer.setPixelContent(positionInBuffer_voxelGrid, value);
/*  52 */     return true;
/*     */   }
/*     */ 
/*     */   
/*     */   public void set(T content) {
/*  57 */     throw new UnsupportedOperationException();
/*     */   }
/*     */ 
/*     */   
/*     */   public void setOrigin(int x, int y, int z) {
/*  62 */     throw new UnsupportedOperationException();
/*     */   }
/*     */ 
/*     */   
/*     */   @Nullable
/*     */   public T getContent(int x, int y, int z) {
/*  68 */     return getContent(new Vector3i(x, y, z));
/*     */   }
/*     */ 
/*     */   
/*     */   @Nullable
/*     */   public T getContent(@Nonnull Vector3i position_voxelGrid) {
/*  74 */     assert this.bounds_voxelGrid.contains(position_voxelGrid);
/*     */     
/*  76 */     NPixelBuffer<T> buffer = getBuffer(position_voxelGrid);
/*  77 */     Vector3i positionInBuffer_voxelGrid = position_voxelGrid.clone();
/*  78 */     GridUtils.toVoxelGridInsideBuffer_fromWorldGrid(positionInBuffer_voxelGrid);
/*     */     
/*  80 */     return (T)buffer.getPixelContent(positionInBuffer_voxelGrid);
/*     */   }
/*     */   
/*     */   private NPixelBuffer<T> getBuffer(@Nonnull Vector3i position_voxelGrid) {
/*  84 */     assert this.bounds_voxelGrid.contains(position_voxelGrid);
/*     */     
/*  86 */     Vector3i localBufferPosition_bufferGrid = position_voxelGrid.clone();
/*  87 */     GridUtils.toBufferGrid_fromVoxelGrid(localBufferPosition_bufferGrid);
/*  88 */     return (NPixelBuffer<T>)this.bufferAccess.getBuffer(localBufferPosition_bufferGrid).buffer();
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean replace(T replacement, int x, int y, int z, @Nonnull Predicate<T> mask) {
/*  93 */     return false;
/*     */   }
/*     */ 
/*     */   
/*     */   public void pasteFrom(@Nonnull VoxelSpace<T> source) {
/*  98 */     throw new UnsupportedOperationException();
/*     */   }
/*     */ 
/*     */   
/*     */   public int getOriginX() {
/* 103 */     return 0;
/*     */   }
/*     */ 
/*     */   
/*     */   public int getOriginY() {
/* 108 */     return 0;
/*     */   }
/*     */ 
/*     */   
/*     */   public int getOriginZ() {
/* 113 */     return 0;
/*     */   }
/*     */ 
/*     */   
/*     */   public String getName() {
/* 118 */     throw new UnsupportedOperationException();
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isInsideSpace(int x, int y, int z) {
/* 123 */     return isInsideSpace(new Vector3i(x, y, z));
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isInsideSpace(@Nonnull Vector3i position) {
/* 128 */     return this.bounds_voxelGrid.contains(position);
/*     */   }
/*     */ 
/*     */   
/*     */   public void forEach(VoxelConsumer<? super T> action) {
/* 133 */     throw new UnsupportedOperationException();
/*     */   }
/*     */ 
/*     */   
/*     */   public int minX() {
/* 138 */     return this.bounds_voxelGrid.min.x;
/*     */   }
/*     */ 
/*     */   
/*     */   public int maxX() {
/* 143 */     return this.bounds_voxelGrid.max.x;
/*     */   }
/*     */ 
/*     */   
/*     */   public int minY() {
/* 148 */     return this.bounds_voxelGrid.min.y;
/*     */   }
/*     */ 
/*     */   
/*     */   public int maxY() {
/* 153 */     return this.bounds_voxelGrid.max.y;
/*     */   }
/*     */ 
/*     */   
/*     */   public int minZ() {
/* 158 */     return this.bounds_voxelGrid.min.z;
/*     */   }
/*     */ 
/*     */   
/*     */   public int maxZ() {
/* 163 */     return this.bounds_voxelGrid.max.z;
/*     */   }
/*     */ 
/*     */   
/*     */   public int sizeX() {
/* 168 */     return this.size_voxelGrid.x;
/*     */   }
/*     */ 
/*     */   
/*     */   public int sizeY() {
/* 173 */     return this.size_voxelGrid.y;
/*     */   }
/*     */ 
/*     */   
/*     */   public int sizeZ() {
/* 178 */     return this.size_voxelGrid.z;
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\builtin\hytalegenerator\newsystem\views\NPixelBufferView.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */