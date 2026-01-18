/*     */ package com.hypixel.hytale.builtin.hytalegenerator.newsystem.views;
/*     */ 
/*     */ import com.hypixel.hytale.builtin.hytalegenerator.bounds.Bounds3i;
/*     */ import com.hypixel.hytale.builtin.hytalegenerator.datastructures.voxelspace.VoxelConsumer;
/*     */ import com.hypixel.hytale.builtin.hytalegenerator.datastructures.voxelspace.VoxelSpace;
/*     */ import com.hypixel.hytale.builtin.hytalegenerator.newsystem.GridUtils;
/*     */ import com.hypixel.hytale.builtin.hytalegenerator.newsystem.bufferbundle.NBufferBundle;
/*     */ import com.hypixel.hytale.builtin.hytalegenerator.newsystem.bufferbundle.buffers.NVoxelBuffer;
/*     */ import com.hypixel.hytale.math.vector.Vector3i;
/*     */ import java.util.function.Predicate;
/*     */ import javax.annotation.Nonnull;
/*     */ import javax.annotation.Nullable;
/*     */ 
/*     */ public class NVoxelBufferView<T>
/*     */   implements VoxelSpace<T> {
/*     */   private final Class<T> voxelType;
/*     */   private final NBufferBundle.Access.View bufferAccess;
/*     */   private final Bounds3i bounds_voxelGrid;
/*     */   private final Vector3i size_voxelGrid;
/*     */   
/*     */   public NVoxelBufferView(@Nonnull NBufferBundle.Access.View bufferAccess, @Nonnull Class<T> voxelType) {
/*  22 */     this.bufferAccess = bufferAccess;
/*  23 */     this.voxelType = voxelType;
/*  24 */     this.bounds_voxelGrid = bufferAccess.getBounds_bufferGrid();
/*  25 */     GridUtils.toVoxelGrid_fromBufferGrid(this.bounds_voxelGrid);
/*  26 */     this.size_voxelGrid = this.bounds_voxelGrid.getSize();
/*     */   }
/*     */   
/*     */   public void copyFrom(@Nonnull NVoxelBufferView<T> source) {
/*  30 */     assert source.bounds_voxelGrid.contains(this.bounds_voxelGrid);
/*     */     
/*  32 */     Bounds3i thisBounds_bufferGrid = this.bufferAccess.getBounds_bufferGrid();
/*  33 */     Vector3i pos_bufferGrid = new Vector3i();
/*     */     
/*  35 */     pos_bufferGrid.setX(thisBounds_bufferGrid.min.x); for (; pos_bufferGrid.x < thisBounds_bufferGrid.max.x; pos_bufferGrid.setX(pos_bufferGrid.x + 1)) {
/*  36 */       pos_bufferGrid.setY(thisBounds_bufferGrid.min.y); for (; pos_bufferGrid.y < thisBounds_bufferGrid.max.y; pos_bufferGrid.setY(pos_bufferGrid.y + 1)) {
/*  37 */         pos_bufferGrid.setZ(thisBounds_bufferGrid.min.z); for (; pos_bufferGrid.z < thisBounds_bufferGrid.max.z; pos_bufferGrid.setZ(pos_bufferGrid.z + 1)) {
/*     */           
/*  39 */           NVoxelBuffer<T> sourceBuffer = source.getBuffer_fromBufferGrid(pos_bufferGrid);
/*  40 */           NVoxelBuffer<T> destinationBuffer = getBuffer_fromBufferGrid(pos_bufferGrid);
/*  41 */           destinationBuffer.reference(sourceBuffer);
/*     */         } 
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean set(T content, int x, int y, int z) {
/*  49 */     return set(content, new Vector3i(x, y, z));
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean set(T content, @Nonnull Vector3i position_voxelGrid) {
/*  54 */     assert this.bounds_voxelGrid.contains(position_voxelGrid);
/*     */     
/*  56 */     NVoxelBuffer<T> buffer = getBuffer_fromVoxelGrid(position_voxelGrid);
/*  57 */     Vector3i positionInBuffer_voxelGrid = position_voxelGrid.clone();
/*  58 */     GridUtils.toVoxelGridInsideBuffer_fromWorldGrid(positionInBuffer_voxelGrid);
/*     */     
/*  60 */     buffer.setVoxelContent(positionInBuffer_voxelGrid, content);
/*  61 */     return true;
/*     */   }
/*     */ 
/*     */   
/*     */   public void set(T content) {
/*  66 */     throw new UnsupportedOperationException();
/*     */   }
/*     */ 
/*     */   
/*     */   public void setOrigin(int x, int y, int z) {
/*  71 */     throw new UnsupportedOperationException();
/*     */   }
/*     */ 
/*     */   
/*     */   @Nullable
/*     */   public T getContent(int x, int y, int z) {
/*  77 */     return getContent(new Vector3i(x, y, z));
/*     */   }
/*     */ 
/*     */   
/*     */   @Nullable
/*     */   public T getContent(@Nonnull Vector3i position_voxelGrid) {
/*  83 */     assert this.bounds_voxelGrid.contains(position_voxelGrid);
/*     */     
/*  85 */     NVoxelBuffer<T> buffer = getBuffer_fromVoxelGrid(position_voxelGrid);
/*  86 */     Vector3i positionInBuffer_voxelGrid = position_voxelGrid.clone();
/*  87 */     GridUtils.toVoxelGridInsideBuffer_fromWorldGrid(positionInBuffer_voxelGrid);
/*     */     
/*  89 */     return (T)buffer.getVoxelContent(positionInBuffer_voxelGrid);
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean replace(T replacement, int x, int y, int z, @Nonnull Predicate<T> mask) {
/*  94 */     return false;
/*     */   }
/*     */ 
/*     */   
/*     */   public void pasteFrom(@Nonnull VoxelSpace<T> source) {
/*  99 */     throw new UnsupportedOperationException();
/*     */   }
/*     */ 
/*     */   
/*     */   public int getOriginX() {
/* 104 */     return 0;
/*     */   }
/*     */ 
/*     */   
/*     */   public int getOriginY() {
/* 109 */     return 0;
/*     */   }
/*     */ 
/*     */   
/*     */   public int getOriginZ() {
/* 114 */     return 0;
/*     */   }
/*     */ 
/*     */   
/*     */   public String getName() {
/* 119 */     throw new UnsupportedOperationException();
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isInsideSpace(int x, int y, int z) {
/* 124 */     return isInsideSpace(new Vector3i(x, y, z));
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isInsideSpace(@Nonnull Vector3i position) {
/* 129 */     return this.bounds_voxelGrid.contains(position);
/*     */   }
/*     */ 
/*     */   
/*     */   public void forEach(VoxelConsumer<? super T> action) {
/* 134 */     throw new UnsupportedOperationException();
/*     */   }
/*     */ 
/*     */   
/*     */   public int minX() {
/* 139 */     return this.bounds_voxelGrid.min.x;
/*     */   }
/*     */ 
/*     */   
/*     */   public int maxX() {
/* 144 */     return this.bounds_voxelGrid.max.x;
/*     */   }
/*     */ 
/*     */   
/*     */   public int minY() {
/* 149 */     return this.bounds_voxelGrid.min.y;
/*     */   }
/*     */ 
/*     */   
/*     */   public int maxY() {
/* 154 */     return this.bounds_voxelGrid.max.y;
/*     */   }
/*     */ 
/*     */   
/*     */   public int minZ() {
/* 159 */     return this.bounds_voxelGrid.min.z;
/*     */   }
/*     */ 
/*     */   
/*     */   public int maxZ() {
/* 164 */     return this.bounds_voxelGrid.max.z;
/*     */   }
/*     */ 
/*     */   
/*     */   public int sizeX() {
/* 169 */     return this.size_voxelGrid.x;
/*     */   }
/*     */ 
/*     */   
/*     */   public int sizeY() {
/* 174 */     return this.size_voxelGrid.y;
/*     */   }
/*     */ 
/*     */   
/*     */   public int sizeZ() {
/* 179 */     return this.size_voxelGrid.z;
/*     */   }
/*     */   
/*     */   @Nonnull
/*     */   private NVoxelBuffer<T> getBuffer_fromVoxelGrid(@Nonnull Vector3i position_voxelGrid) {
/* 184 */     Vector3i localBufferPosition_bufferGrid = position_voxelGrid.clone();
/* 185 */     GridUtils.toBufferGrid_fromVoxelGrid(localBufferPosition_bufferGrid);
/*     */     
/* 187 */     return getBuffer_fromBufferGrid(localBufferPosition_bufferGrid);
/*     */   }
/*     */   
/*     */   @Nonnull
/*     */   private NVoxelBuffer<T> getBuffer_fromBufferGrid(@Nonnull Vector3i position_bufferGrid) {
/* 192 */     return (NVoxelBuffer<T>)this.bufferAccess.getBuffer(position_bufferGrid).buffer();
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\builtin\hytalegenerator\newsystem\views\NVoxelBufferView.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */