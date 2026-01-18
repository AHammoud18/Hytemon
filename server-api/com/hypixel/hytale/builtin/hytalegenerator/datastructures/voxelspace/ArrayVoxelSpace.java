/*     */ package com.hypixel.hytale.builtin.hytalegenerator.datastructures.voxelspace;
/*     */ 
/*     */ import com.hypixel.hytale.builtin.hytalegenerator.bounds.Bounds3i;
/*     */ import com.hypixel.hytale.math.vector.Vector3i;
/*     */ import java.util.function.Predicate;
/*     */ import javax.annotation.Nonnull;
/*     */ import javax.annotation.Nullable;
/*     */ 
/*     */ public class ArrayVoxelSpace<T>
/*     */   implements VoxelSpace<T> {
/*     */   protected final int sizeX;
/*     */   protected final int sizeY;
/*     */   @Nonnull
/*  14 */   protected String name = "schematic"; protected final int sizeZ; @Nonnull
/*     */   protected final T[] contents; @Nullable
/*  16 */   protected T[] fastReset = null;
/*     */   
/*     */   protected VoxelCoordinate origin;
/*     */   
/*     */   public ArrayVoxelSpace(@Nonnull Bounds3i bounds) {
/*  21 */     this("", (bounds.getSize()).x, (bounds.getSize()).y, (bounds.getSize()).z, -bounds.min.x, bounds.min.y, -bounds.min.z);
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
/*     */   public ArrayVoxelSpace(@Nonnull String name, int sizeX, int sizeY, int sizeZ, int originX, int originY, int originZ) {
/*  39 */     if (name == null)
/*  40 */       throw new NullPointerException(); 
/*  41 */     if (sizeX < 1 || sizeY < 1 || sizeZ < 1) {
/*  42 */       throw new IllegalArgumentException("invalid size " + sizeX + " " + sizeY + " " + sizeZ);
/*     */     }
/*  44 */     this.name = name;
/*  45 */     this.sizeX = sizeX;
/*  46 */     this.sizeY = sizeY;
/*  47 */     this.sizeZ = sizeZ;
/*  48 */     this.contents = (T[])new Object[sizeX * sizeY * sizeZ];
/*  49 */     this.origin = new VoxelCoordinate(originX, originY, originZ);
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
/*     */   public ArrayVoxelSpace(int sizeX, int sizeY, int sizeZ) {
/*  62 */     if (sizeX < 1 || sizeY < 1 || sizeZ < 1) {
/*  63 */       throw new IllegalArgumentException("invalid size " + sizeX + " " + sizeY + " " + sizeZ);
/*     */     }
/*  65 */     this.name = getClass().getName();
/*  66 */     this.sizeX = sizeX;
/*  67 */     this.sizeY = sizeY;
/*  68 */     this.sizeZ = sizeZ;
/*  69 */     this.contents = (T[])new Object[sizeX * sizeY * sizeZ];
/*  70 */     this.origin = new VoxelCoordinate(0, 0, 0);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ArrayVoxelSpace(@Nonnull VoxelSpace<T> voxelSpace) {
/*  80 */     this(voxelSpace.getName(), voxelSpace
/*  81 */         .sizeX(), voxelSpace
/*  82 */         .sizeY(), voxelSpace
/*  83 */         .sizeZ(), voxelSpace
/*  84 */         .getOriginX(), voxelSpace
/*  85 */         .getOriginY(), voxelSpace
/*  86 */         .getOriginZ());
/*     */     
/*  88 */     voxelSpace.forEach((v, x, y, z) -> set((T)v, x, y, z));
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
/*     */   public void setFastResetTo(T e) {
/* 100 */     this.fastReset = (T[])new Object[this.contents.length];
/* 101 */     for (int i = 0; i < this.fastReset.length; ) { this.fastReset[i] = e; i++; }
/*     */   
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void disableFastReset() {
/* 109 */     this.fastReset = null;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean hasFastReset() {
/* 118 */     return (this.fastReset != null);
/*     */   }
/*     */ 
/*     */   
/*     */   public void fastReset() {
/* 123 */     if (this.fastReset == null)
/* 124 */       throw new IllegalStateException("no fast-reset"); 
/* 125 */     System.arraycopy(this.fastReset, 0, this.contents, 0, this.fastReset.length);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int sizeX() {
/* 134 */     return this.sizeX;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public int sizeY() {
/* 140 */     return this.sizeY;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public int sizeZ() {
/* 146 */     return this.sizeZ;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void pasteFrom(@Nonnull VoxelSpace<T> source) {
/* 156 */     if (source == null)
/* 157 */       throw new NullPointerException(); 
/* 158 */     for (int x = source.minX(); x < source.maxX(); x++) {
/* 159 */       for (int y = source.minY(); y < source.maxY(); y++) {
/* 160 */         for (int z = source.minZ(); z < source.maxZ(); z++) {
/* 161 */           set(source.getContent(x, y, z), x, y, z);
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
/*     */   
/*     */   public boolean set(T content, int x, int y, int z) {
/* 174 */     if (!isInsideSpace(x, y, z))
/* 175 */       return false; 
/* 176 */     this.contents[arrayIndex(x + this.origin.x, y + this.origin.y, z + this.origin.z)] = content;
/*     */     
/* 178 */     return true;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean set(T content, @Nonnull Vector3i position) {
/* 183 */     return set(content, position.x, position.y, position.z);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void set(T content) {
/* 192 */     for (int x = minX(); x < maxX(); x++) {
/* 193 */       for (int y = minY(); y < maxY(); y++) {
/* 194 */         for (int z = minZ(); z < maxZ(); z++) {
/* 195 */           set(content, x, y, z);
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
/*     */ 
/*     */   
/*     */   public void setOrigin(int x, int y, int z) {
/* 209 */     this.origin.x = x;
/* 210 */     this.origin.y = y;
/* 211 */     this.origin.z = z;
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
/* 224 */     if (!isInsideSpace(x, y, z))
/* 225 */       throw new IndexOutOfBoundsException("Coordinates outside VoxelSpace: " + x + " " + y + " " + z + " constraints " + 
/*     */           
/* 227 */           minX() + " -> " + maxX() + " " + minY() + " -> " + maxY() + " " + 
/* 228 */           minZ() + " -> " + maxZ() + "\n" + toString()); 
/* 229 */     return this.contents[
/* 230 */         arrayIndex(x + this.origin.x, y + this.origin.y, z + this.origin.z)];
/*     */   }
/*     */ 
/*     */   
/*     */   @Nullable
/*     */   public T getContent(@Nonnull Vector3i position) {
/* 236 */     return getContent(position.x, position.y, position.z);
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
/* 252 */     if (!isInsideSpace(x, y, z))
/* 253 */       throw new IllegalArgumentException("outside schematic"); 
/* 254 */     if (!mask.test(getContent(x, y, z)))
/* 255 */       return false; 
/* 256 */     set(replacement, x, y, z);
/* 257 */     return true;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public T[] toArray() {
/* 266 */     return (T[])this.contents.clone();
/*     */   }
/*     */ 
/*     */   
/*     */   @Nonnull
/*     */   VoxelCoordinate getOrigin() {
/* 272 */     return this.origin.clone();
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public int getOriginX() {
/* 278 */     return this.origin.x;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public int getOriginY() {
/* 284 */     return this.origin.y;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public int getOriginZ() {
/* 290 */     return this.origin.z;
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
/* 301 */     return this.name;
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
/* 314 */     return (x + this.origin.x >= 0 && x + this.origin.x < this.sizeX && y + this.origin.y >= 0 && y + this.origin.y < this.sizeY && z + this.origin.z >= 0 && z + this.origin.z < this.sizeZ);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isInsideSpace(@Nonnull Vector3i position) {
/* 321 */     return isInsideSpace(position.x, position.y, position.z);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void forEach(@Nonnull VoxelConsumer<? super T> action) {
/* 330 */     if (action == null)
/* 331 */       throw new NullPointerException(); 
/* 332 */     for (int x = minX(); x < maxX(); x++) {
/* 333 */       for (int y = minY(); y < maxY(); y++) {
/* 334 */         for (int z = minZ(); z < maxZ(); z++) {
/* 335 */           action.accept(getContent(x, y, z), x, y, z);
/*     */         }
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int minX() {
/* 346 */     return -this.origin.x;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int maxX() {
/* 356 */     return this.sizeX - this.origin.x;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int minY() {
/* 366 */     return -this.origin.y;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int maxY() {
/* 376 */     return this.sizeY - this.origin.y;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int minZ() {
/* 386 */     return -this.origin.z;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int maxZ() {
/* 396 */     return this.sizeZ - this.origin.z;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   @Nonnull
/*     */   public ArrayVoxelSpace<T> clone() {
/* 403 */     ArrayVoxelSpace<T> clone = new ArrayVoxelSpace(this.name, this.sizeX, this.sizeY, this.sizeZ, this.origin.x, this.origin.y, this.origin.z);
/*     */ 
/*     */     
/* 406 */     forEach((v, x, y, z) -> clone.set(v, x, y, z));
/* 407 */     return clone;
/*     */   }
/*     */   
/*     */   private int arrayIndex(int x, int y, int z) {
/* 411 */     return y + x * this.sizeY + z * this.sizeY * this.sizeX;
/*     */   }
/*     */ 
/*     */   
/*     */   @Nonnull
/*     */   public String toString() {
/* 417 */     return "ArrayVoxelSpace{sizeX=" + this.sizeX + ", sizeY=" + this.sizeY + ", sizeZ=" + this.sizeZ + ", minX=" + 
/*     */ 
/*     */ 
/*     */       
/* 421 */       minX() + ", minY=" + 
/* 422 */       minY() + ", minZ=" + 
/* 423 */       minZ() + ", maxX=" + 
/* 424 */       maxX() + ", maxY=" + 
/* 425 */       maxY() + ", maxZ=" + 
/* 426 */       maxZ() + ", name='" + this.name + "', origin=" + String.valueOf(this.origin) + "}";
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\builtin\hytalegenerator\datastructures\voxelspace\ArrayVoxelSpace.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */