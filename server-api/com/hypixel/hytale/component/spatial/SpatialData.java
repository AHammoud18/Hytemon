/*     */ package com.hypixel.hytale.component.spatial;
/*     */ 
/*     */ import com.hypixel.hytale.common.util.ArrayUtil;
/*     */ import com.hypixel.hytale.math.vector.Vector3d;
/*     */ import it.unimi.dsi.fastutil.ints.IntArrays;
/*     */ import java.util.Arrays;
/*     */ import java.util.Objects;
/*     */ import javax.annotation.Nonnull;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class SpatialData<T>
/*     */ {
/*  18 */   public static final Vector3d[] EMPTY_VECTOR_ARRAY = new Vector3d[0];
/*     */   @Nonnull
/*  20 */   private int[] indexes = ArrayUtil.EMPTY_INT_ARRAY;
/*     */   @Nonnull
/*  22 */   private long[] moroton = ArrayUtil.EMPTY_LONG_ARRAY;
/*     */   
/*  24 */   private Vector3d[] vectors = EMPTY_VECTOR_ARRAY;
/*     */   @Nonnull
/*  26 */   private T[] data = (T[])ArrayUtil.emptyArray();
/*     */   private int size;
/*     */   
/*     */   public int size() {
/*  30 */     return this.size;
/*     */   }
/*     */   
/*     */   public int getSortedIndex(int i) {
/*  34 */     return this.indexes[i];
/*     */   }
/*     */   
/*     */   @Nonnull
/*     */   public Vector3d getVector(int i) {
/*  39 */     return this.vectors[i];
/*     */   }
/*     */   
/*     */   @Nonnull
/*     */   public T getData(int i) {
/*  44 */     return this.data[i];
/*     */   }
/*     */   
/*     */   public void add(@Nonnull Vector3d vector, @Nonnull T value) {
/*  48 */     Objects.requireNonNull(value);
/*  49 */     if (this.vectors.length < this.size + 1) {
/*  50 */       int newLength = ArrayUtil.grow(this.size);
/*  51 */       this.indexes = Arrays.copyOf(this.indexes, newLength);
/*  52 */       this.vectors = Arrays.<Vector3d>copyOf(this.vectors, newLength);
/*  53 */       this.data = Arrays.copyOf(this.data, newLength);
/*  54 */       for (int i = this.size; i < newLength; i++) {
/*  55 */         this.vectors[i] = new Vector3d();
/*     */       }
/*     */     } 
/*     */     
/*  59 */     int index = this.size++;
/*  60 */     this.indexes[index] = index;
/*  61 */     this.vectors[index].assign(vector);
/*  62 */     this.data[index] = value;
/*     */   }
/*     */   
/*     */   public void addCapacity(int additionalSize) {
/*  66 */     int newSize = this.size + additionalSize;
/*  67 */     if (this.vectors.length < newSize) {
/*  68 */       int newLength = ArrayUtil.grow(newSize);
/*  69 */       this.indexes = Arrays.copyOf(this.indexes, newLength);
/*  70 */       this.vectors = Arrays.<Vector3d>copyOf(this.vectors, newLength);
/*  71 */       this.data = Arrays.copyOf(this.data, newLength);
/*  72 */       for (int i = this.size; i < newLength; i++) {
/*  73 */         this.vectors[i] = new Vector3d();
/*     */       }
/*     */     } 
/*     */   }
/*     */   
/*     */   public void append(@Nonnull Vector3d vector, @Nonnull T value) {
/*  79 */     Objects.requireNonNull(value);
/*  80 */     int index = this.size++;
/*  81 */     this.indexes[index] = index;
/*  82 */     this.vectors[index].assign(vector);
/*  83 */     this.data[index] = value;
/*     */   }
/*     */   
/*     */   public void sort() {
/*  87 */     IntArrays.quickSort(this.indexes, 0, this.size, (i1, i2) -> {
/*     */           Vector3d v1 = this.vectors[i1];
/*     */           Vector3d v2 = this.vectors[i2];
/*     */           int xComp = Double.compare(v1.x, v2.x);
/*     */           if (xComp != 0) {
/*     */             return xComp;
/*     */           }
/*     */           int zComp = Double.compare(v1.z, v2.z);
/*     */           return (zComp != 0) ? zComp : Double.compare(v1.y, v2.y);
/*     */         });
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void sortMorton() {
/* 103 */     double minX = Double.POSITIVE_INFINITY, minY = Double.POSITIVE_INFINITY, minZ = Double.POSITIVE_INFINITY;
/* 104 */     double maxX = Double.NEGATIVE_INFINITY, maxY = Double.NEGATIVE_INFINITY, maxZ = Double.NEGATIVE_INFINITY; int i;
/* 105 */     for (i = 0; i < this.size; i++) {
/* 106 */       Vector3d v = this.vectors[i];
/* 107 */       if (v.x < minX) minX = v.x; 
/* 108 */       if (v.y < minY) minY = v.y; 
/* 109 */       if (v.z < minZ) minZ = v.z; 
/* 110 */       if (v.x > maxX) maxX = v.x; 
/* 111 */       if (v.y > maxY) maxY = v.y; 
/* 112 */       if (v.z > maxZ) maxZ = v.z;
/*     */     
/*     */     } 
/*     */     
/* 116 */     this.moroton = (this.moroton.length < this.size) ? Arrays.copyOf(this.moroton, this.size) : this.moroton;
/* 117 */     for (i = 0; i < this.size; i++) {
/* 118 */       Vector3d v = this.vectors[i];
/* 119 */       this.moroton[i] = Long.reverse(MortonCode.encode(v.x, v.y, v.z, minX, minY, minZ, maxX, maxY, maxZ));
/*     */     } 
/*     */     
/* 122 */     IntArrays.quickSort(this.indexes, 0, this.size, (i1, i2) -> Long.compare(this.moroton[i1], this.moroton[i2]));
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void clear() {
/* 128 */     Arrays.fill((Object[])this.data, 0, this.size, (Object)null);
/* 129 */     this.size = 0;
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\component\spatial\SpatialData.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */