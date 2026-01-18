/*     */ package com.hypixel.hytale.builtin.hytalegenerator.datastructures.voxelspace;
/*     */ 
/*     */ import com.hypixel.hytale.math.vector.Vector3i;
/*     */ import java.util.function.Predicate;
/*     */ import javax.annotation.Nonnull;
/*     */ import javax.annotation.Nullable;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class WindowVoxelSpace<T>
/*     */   implements VoxelSpace<T>
/*     */ {
/*     */   @Nonnull
/*     */   private final VoxelSpace<T> wrappedVoxelSpace;
/*     */   @Nonnull
/*     */   private final VoxelCoordinate min;
/*     */   @Nonnull
/*     */   private final VoxelCoordinate max;
/*     */   
/*     */   public WindowVoxelSpace(@Nonnull VoxelSpace<T> voxelSpace) {
/*  25 */     this.wrappedVoxelSpace = voxelSpace;
/*  26 */     this
/*  27 */       .min = new VoxelCoordinate(voxelSpace.minX(), voxelSpace.minY(), voxelSpace.minZ());
/*  28 */     this
/*  29 */       .max = new VoxelCoordinate(voxelSpace.maxX(), voxelSpace.maxY(), voxelSpace.maxZ());
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
/*     */   public WindowVoxelSpace<T> setWindow(int minX, int minY, int minZ, int maxX, int maxY, int maxZ) {
/*  44 */     if (minX < this.wrappedVoxelSpace.minX() || minY < this.wrappedVoxelSpace
/*  45 */       .minY() || minZ < this.wrappedVoxelSpace
/*  46 */       .minZ() || maxX < minX || maxY < minY || maxZ < minZ || maxX > this.wrappedVoxelSpace
/*     */ 
/*     */ 
/*     */       
/*  50 */       .maxX() || maxY > this.wrappedVoxelSpace
/*  51 */       .maxY() || maxZ > this.wrappedVoxelSpace
/*  52 */       .maxZ())
/*  53 */       throw new IllegalArgumentException("invalid values"); 
/*  54 */     this.min.x = minX;
/*  55 */     this.min.y = minY;
/*  56 */     this.min.z = minZ;
/*  57 */     this.max.x = maxX;
/*  58 */     this.max.y = maxY;
/*  59 */     this.max.z = maxZ;
/*  60 */     return this;
/*     */   }
/*     */ 
/*     */   
/*     */   @Nonnull
/*     */   public VoxelSpace<T> getWrappedSchematic() {
/*  66 */     return this.wrappedVoxelSpace;
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
/*     */   public boolean set(T content, int x, int y, int z) {
/*  84 */     if (!isInsideSpace(x, y, z)) {
/*  85 */       return false;
/*     */     }
/*  87 */     if (!this.wrappedVoxelSpace.isInsideSpace(x, y, z))
/*  88 */       return false; 
/*  89 */     return this.wrappedVoxelSpace.set(content, x, y, z);
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean set(T content, @Nonnull Vector3i position) {
/*  94 */     return set(content, position.x, position.y, position.z);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void set(T content) {
/* 103 */     for (int x = minX(); x < maxX(); x++) {
/* 104 */       for (int y = minY(); y < maxY(); y++) {
/* 105 */         for (int z = minZ(); z < maxZ(); z++) {
/* 106 */           set(content, x, y, z);
/*     */         }
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public void setOrigin(int x, int y, int z) {
/* 114 */     throw new UnsupportedOperationException("can't set origin of window");
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
/*     */   public T getContent(int x, int y, int z) {
/* 127 */     if (!isInsideSpace(x, y, z))
/* 128 */       throw new IllegalArgumentException("outside schematic"); 
/* 129 */     return this.wrappedVoxelSpace.getContent(x, y, z);
/*     */   }
/*     */ 
/*     */   
/*     */   @Nullable
/*     */   public T getContent(@Nonnull Vector3i position) {
/* 135 */     return getContent(position.x, position.y, position.z);
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
/*     */   public boolean replace(T replacement, int x, int y, int z, @Nonnull Predicate<T> mask) {
/* 151 */     if (!isInsideSpace(x, y, z))
/* 152 */       throw new IllegalArgumentException("outside schematic"); 
/* 153 */     return this.wrappedVoxelSpace.replace(replacement, x, y, z, mask);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void pasteFrom(@Nonnull VoxelSpace<T> source) {
/* 163 */     for (int x = source.minX(); x < source.maxX(); x++) {
/* 164 */       for (int y = source.minY(); y < source.maxY(); y++) {
/* 165 */         for (int z = source.minZ(); z < source.maxZ(); z++) {
/* 166 */           set(source.getContent(x, y, z), x, y, z);
/*     */         }
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getOriginX() {
/* 178 */     int offset = this.min.x - this.wrappedVoxelSpace.minX();
/* 179 */     return this.wrappedVoxelSpace.getOriginX() - offset;
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
/*     */   public int getOriginY() {
/* 191 */     int offset = this.min.y - this.wrappedVoxelSpace.minY();
/* 192 */     return this.wrappedVoxelSpace.getOriginY() - offset;
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
/*     */   public int getOriginZ() {
/* 204 */     int offset = this.min.z - this.wrappedVoxelSpace.minZ();
/* 205 */     return this.wrappedVoxelSpace.getOriginZ() - offset;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Nonnull
/*     */   public String getName() {
/* 216 */     return "window_to_" + this.wrappedVoxelSpace.getName();
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
/*     */   public boolean isInsideSpace(int x, int y, int z) {
/* 229 */     return (x >= minX() && x < maxX() && y >= 
/* 230 */       minY() && y < maxY() && z >= 
/* 231 */       minZ() && z < maxZ());
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isInsideSpace(@Nonnull Vector3i position) {
/* 237 */     return isInsideSpace(position.x, position.y, position.z);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void forEach(@Nonnull VoxelConsumer<? super T> action) {
/* 248 */     for (int x = minX(); x < maxX(); x++) {
/* 249 */       for (int y = minY(); y < maxY(); y++) {
/* 250 */         for (int z = minZ(); z < maxZ(); z++) {
/* 251 */           action.accept(getContent(x, y, z), x, y, z);
/*     */         }
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int minX() {
/* 261 */     return this.min.x;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int maxX() {
/* 271 */     return this.max.x;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int minY() {
/* 281 */     return this.min.y;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int maxY() {
/* 291 */     return this.max.y;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int minZ() {
/* 301 */     return this.min.z;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int maxZ() {
/* 311 */     return this.max.z;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int sizeX() {
/* 321 */     return this.max.x - this.min.x;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int sizeY() {
/* 331 */     return this.max.y - this.min.y;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int sizeZ() {
/* 341 */     return this.max.z - this.min.z;
/*     */   }
/*     */ 
/*     */   
/*     */   @Nonnull
/*     */   public String toString() {
/* 347 */     return "WindowVoxelSpace{wrappedVoxelSpace=" + String.valueOf(this.wrappedVoxelSpace) + ", min=" + String.valueOf(this.min) + ", max=" + String.valueOf(this.max) + "}";
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\builtin\hytalegenerator\datastructures\voxelspace\WindowVoxelSpace.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */