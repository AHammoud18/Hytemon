/*     */ package com.hypixel.hytale.builtin.hytalegenerator.datastructures.bicoordinatecache;
/*     */ 
/*     */ import java.util.Iterator;
/*     */ import java.util.concurrent.ConcurrentHashMap;
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
/*     */ public class HashedBiCoordinateCache<T>
/*     */   implements BiCoordinateCache<T>
/*     */ {
/*     */   @Nonnull
/*  22 */   private final ConcurrentHashMap<Long, T> values = new ConcurrentHashMap<>();
/*     */ 
/*     */ 
/*     */   
/*     */   public static long hash(int x, int z) {
/*  27 */     long hash = x;
/*  28 */     hash <<= 32L;
/*  29 */     hash += z;
/*  30 */     return hash;
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
/*  43 */     long key = hash(x, z);
/*  44 */     if (!this.values.containsKey(Long.valueOf(key)))
/*  45 */       throw new IllegalStateException("doesn't contain coordinates"); 
/*  46 */     return this.values.get(Long.valueOf(key));
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
/*  59 */     return this.values.containsKey(Long.valueOf(hash(x, z)));
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
/*     */   @Nonnull
/*     */   public T save(int x, int z, @Nonnull T value) {
/*  73 */     long key = hash(x, z);
/*  74 */     this.values.put(Long.valueOf(key), value);
/*  75 */     return value;
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
/*  87 */     long key = hash(x, z);
/*     */     
/*  89 */     if (!this.values.containsKey(Long.valueOf(key)))
/*     */       return; 
/*  91 */     this.values.remove(Long.valueOf(key));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void flush() {
/* 100 */     for (Iterator<Long> iterator = this.values.keySet().iterator(); iterator.hasNext(); ) { long key = ((Long)iterator.next()).longValue();
/* 101 */       this.values.remove(Long.valueOf(key)); }
/*     */   
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int size() {
/* 111 */     return this.values.size();
/*     */   }
/*     */ 
/*     */   
/*     */   @Nonnull
/*     */   public String toString() {
/* 117 */     return "HashedBiCoordinateCache{values=" + String.valueOf(this.values) + "}";
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\builtin\hytalegenerator\datastructures\bicoordinatecache\HashedBiCoordinateCache.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */