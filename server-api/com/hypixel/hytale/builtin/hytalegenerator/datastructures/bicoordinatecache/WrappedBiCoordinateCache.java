/*     */ package com.hypixel.hytale.builtin.hytalegenerator.datastructures.bicoordinatecache;
/*     */ 
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
/*     */ public class WrappedBiCoordinateCache<T>
/*     */   implements BiCoordinateCache<T>
/*     */ {
/*     */   private final int sizeX;
/*     */   private final int sizeZ;
/*     */   @Nonnull
/*     */   private final T[][] values;
/*     */   @Nonnull
/*     */   private final boolean[][] populated;
/*     */   private int size;
/*     */   
/*     */   public WrappedBiCoordinateCache(int sizeX, int sizeZ) {
/*  38 */     if (sizeX < 0 || sizeZ < 0)
/*  39 */       throw new IllegalArgumentException("negative size"); 
/*  40 */     this.sizeX = sizeX;
/*  41 */     this.sizeZ = sizeZ;
/*  42 */     this.values = (T[][])new Object[sizeX][sizeZ];
/*  43 */     this.populated = new boolean[sizeX][sizeZ];
/*  44 */     this.size = 0;
/*     */   }
/*     */ 
/*     */   
/*     */   public int localXFrom(int x) {
/*  49 */     if (x < 0) {
/*  50 */       return (x % this.sizeX + this.sizeX - 1) % this.sizeX;
/*     */     }
/*  52 */     return x % this.sizeX;
/*     */   }
/*     */ 
/*     */   
/*     */   public int localZFrom(int z) {
/*  57 */     if (z < 0) {
/*  58 */       return (z % this.sizeZ + this.sizeZ - 1) % this.sizeZ;
/*     */     }
/*  60 */     return z % this.sizeZ;
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
/*     */   public T get(int x, int z) {
/*  73 */     x = localXFrom(x);
/*  74 */     z = localZFrom(z);
/*  75 */     if (!isCached(x, z)) {
/*  76 */       throw new IllegalStateException("accessing coordinates that are not cached: " + x + " " + z);
/*     */     }
/*  78 */     return this.values[x][z];
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
/*     */   public boolean isCached(int x, int z) {
/*  91 */     return this.populated[localXFrom(x)][localZFrom(z)];
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
/*     */   public T save(int x, int z, T value) {
/* 104 */     x = localXFrom(x);
/* 105 */     z = localZFrom(z);
/* 106 */     this.values[x][z] = value;
/* 107 */     this.populated[x][z] = true;
/* 108 */     this.size++;
/* 109 */     return value;
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
/*     */   public void flush(int x, int z) {
/* 121 */     x = localXFrom(x);
/* 122 */     z = localZFrom(z);
/*     */     
/* 124 */     if (!this.populated[x][z])
/*     */       return; 
/* 126 */     this.values[x][z] = null;
/* 127 */     this.populated[x][z] = false;
/* 128 */     this.size--;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void flush() {
/* 137 */     for (int x = 0; x < this.sizeX; x++) {
/* 138 */       for (int z = 0; z < this.sizeZ; z++) {
/* 139 */         this.values[x][z] = null;
/* 140 */         this.populated[x][z] = false;
/*     */       } 
/* 142 */     }  this.size = 0;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int size() {
/* 152 */     return this.size;
/*     */   }
/*     */ 
/*     */   
/*     */   @Nonnull
/*     */   public String toString() {
/* 158 */     return "WrappedBiCoordinateCache{sizeX=" + this.sizeX + ", sizeZ=" + this.sizeZ + ", values=" + 
/*     */ 
/*     */       
/* 161 */       Arrays.toString((Object[])this.values) + ", populated=" + 
/* 162 */       Arrays.toString((Object[])this.populated) + ", size=" + this.size + "}";
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\builtin\hytalegenerator\datastructures\bicoordinatecache\WrappedBiCoordinateCache.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */