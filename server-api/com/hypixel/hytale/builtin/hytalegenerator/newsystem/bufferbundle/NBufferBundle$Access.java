/*     */ package com.hypixel.hytale.builtin.hytalegenerator.newsystem.bufferbundle;
/*     */ 
/*     */ import com.hypixel.hytale.builtin.hytalegenerator.bounds.Bounds3i;
/*     */ import com.hypixel.hytale.builtin.hytalegenerator.newsystem.GridUtils;
/*     */ import com.hypixel.hytale.builtin.hytalegenerator.newsystem.performanceinstruments.MemInstrument;
/*     */ import com.hypixel.hytale.math.vector.Vector3i;
/*     */ import java.util.Arrays;
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
/*     */ public class Access
/*     */   implements MemInstrument
/*     */ {
/*     */   private final NBufferBundle.Grid grid;
/*     */   private final Bounds3i bounds_bufferGrid;
/*     */   private final NBufferBundle.Grid.TrackedBuffer[] buffers;
/*     */   private boolean isClosed;
/*     */   
/*     */   public static class View
/*     */   {
/*     */     private final NBufferBundle.Access access;
/*     */     private final Bounds3i bounds_bufferGrid;
/*     */     
/*     */     private View(@Nonnull NBufferBundle.Access access, @Nonnull Bounds3i bounds_bufferGrid) {
/* 195 */       assert access.bounds_bufferGrid.contains(bounds_bufferGrid);
/*     */       
/* 197 */       this.access = access;
/* 198 */       this.bounds_bufferGrid = bounds_bufferGrid;
/*     */     }
/*     */     
/*     */     @Nonnull
/*     */     public NBufferBundle.Grid.TrackedBuffer getBuffer(@Nonnull Vector3i position_bufferGrid) {
/* 203 */       assert !this.access.isClosed;
/* 204 */       assert this.bounds_bufferGrid.contains(position_bufferGrid);
/*     */       
/* 206 */       return this.access.getBuffer(position_bufferGrid);
/*     */     }
/*     */     
/*     */     @Nonnull
/*     */     public Bounds3i getBounds_bufferGrid() {
/* 211 */       return this.bounds_bufferGrid.clone();
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private Access(@Nonnull NBufferBundle.Grid grid, @Nonnull Bounds3i bounds_bufferGrid) {
/* 222 */     assert bounds_bufferGrid.isCorrect();
/*     */     
/* 224 */     this.grid = grid;
/* 225 */     this.bounds_bufferGrid = bounds_bufferGrid.clone();
/* 226 */     this.bounds_bufferGrid.min.y = 0;
/* 227 */     this.bounds_bufferGrid.max.y = 40;
/*     */     
/* 229 */     Vector3i boundsSize_bufferGrid = this.bounds_bufferGrid.getSize();
/* 230 */     int bufferCount = boundsSize_bufferGrid.x * boundsSize_bufferGrid.y * boundsSize_bufferGrid.z;
/* 231 */     this.buffers = new NBufferBundle.Grid.TrackedBuffer[bufferCount];
/*     */     
/* 233 */     this.isClosed = false;
/*     */   }
/*     */   
/*     */   @Nonnull
/*     */   public View createView(@Nonnull Bounds3i viewBounds_bufferGrid) {
/* 238 */     assert this.bounds_bufferGrid.contains(viewBounds_bufferGrid);
/*     */     
/* 240 */     return new View(this, viewBounds_bufferGrid);
/*     */   }
/*     */   
/*     */   @Nonnull
/*     */   public View createView() {
/* 245 */     return new View(this, this.bounds_bufferGrid);
/*     */   }
/*     */   
/*     */   @Nonnull
/*     */   public NBufferBundle.Grid.TrackedBuffer getBuffer(@Nonnull Vector3i position_bufferGrid) {
/* 250 */     assert !this.isClosed;
/* 251 */     assert this.bounds_bufferGrid.contains(position_bufferGrid);
/*     */     
/* 253 */     int index = GridUtils.toIndexFromPositionYXZ(position_bufferGrid, this.bounds_bufferGrid);
/* 254 */     return this.buffers[index];
/*     */   }
/*     */   
/*     */   @Nonnull
/*     */   public Bounds3i getBounds_bufferGrid() {
/* 259 */     return this.bounds_bufferGrid.clone();
/*     */   }
/*     */   
/*     */   public void close() {
/* 263 */     this.grid.accessors.remove(this);
/* 264 */     this.isClosed = true;
/* 265 */     Arrays.fill((Object[])this.buffers, (Object)null);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   @Nonnull
/*     */   public MemInstrument.Report getMemoryUsage() {
/* 272 */     long size_bytes = 8L + this.bounds_bufferGrid.getMemoryUsage().size_bytes();
/* 273 */     return new MemInstrument.Report(size_bytes);
/*     */   }
/*     */   
/*     */   private void loadGrid() {
/* 277 */     assert !this.isClosed;
/* 278 */     assert this.bounds_bufferGrid.min.y == 0 && this.bounds_bufferGrid.max.y == 40;
/*     */     
/* 280 */     Vector3i position_bufferGrid = this.bounds_bufferGrid.min.clone();
/* 281 */     position_bufferGrid.setY(0);
/*     */     
/* 283 */     NBufferBundle.Grid.TrackedBuffer[] trackedBuffersOutput = new NBufferBundle.Grid.TrackedBuffer[40];
/*     */ 
/*     */     
/* 286 */     for (position_bufferGrid.z = this.bounds_bufferGrid.min.z; position_bufferGrid.z < this.bounds_bufferGrid.max.z; position_bufferGrid.z++) {
/* 287 */       for (position_bufferGrid.x = this.bounds_bufferGrid.min.x; position_bufferGrid.x < this.bounds_bufferGrid.max.x; position_bufferGrid.x++) {
/* 288 */         position_bufferGrid.setY(0);
/*     */         
/* 290 */         this.grid.ensureBufferColumnExists(position_bufferGrid, trackedBuffersOutput);
/* 291 */         int i = 0;
/* 292 */         for (position_bufferGrid.y = 0; position_bufferGrid.y < 40; position_bufferGrid.y++) {
/* 293 */           position_bufferGrid.dropHash();
/*     */           
/* 295 */           int index = GridUtils.toIndexFromPositionYXZ(position_bufferGrid, this.bounds_bufferGrid);
/* 296 */           this.buffers[index] = trackedBuffersOutput[i];
/* 297 */           i++;
/*     */         } 
/*     */       } 
/*     */     } 
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\builtin\hytalegenerator\newsystem\bufferbundle\NBufferBundle$Access.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */