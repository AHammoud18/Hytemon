/*     */ package com.hypixel.hytale.builtin.hytalegenerator.datastructures.voxelspace;
/*     */ 
/*     */ import com.hypixel.hytale.math.vector.Vector3i;
/*     */ import java.util.function.Predicate;
/*     */ import javax.annotation.Nonnull;
/*     */ import javax.annotation.Nullable;
/*     */ 
/*     */ public class NullSpace<V>
/*     */   implements VoxelSpace<V> {
/*  10 */   private static final NullSpace INSTANCE = new NullSpace();
/*     */   
/*     */   public static <V> NullSpace<V> instance() {
/*  13 */     return INSTANCE;
/*     */   }
/*     */   
/*     */   public static <V> NullSpace<V> instance(@Nonnull Class<V> clazz) {
/*  17 */     return INSTANCE;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean set(V content, int x, int y, int z) {
/*  24 */     return false;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean set(V content, @Nonnull Vector3i position) {
/*  29 */     return set(content, position.x, position.y, position.z);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void set(V content) {}
/*     */ 
/*     */ 
/*     */   
/*     */   public void setOrigin(int x, int y, int z) {}
/*     */ 
/*     */ 
/*     */   
/*     */   @Nullable
/*     */   public V getContent(int x, int y, int z) {
/*  45 */     return null;
/*     */   }
/*     */ 
/*     */   
/*     */   @Nullable
/*     */   public V getContent(@Nonnull Vector3i position) {
/*  51 */     return getContent(position.x, position.y, position.z);
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean replace(V replacement, int x, int y, int z, @Nonnull Predicate<V> mask) {
/*  56 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void pasteFrom(@Nonnull VoxelSpace<V> source) {}
/*     */ 
/*     */ 
/*     */   
/*     */   public int getOriginX() {
/*  66 */     return 0;
/*     */   }
/*     */ 
/*     */   
/*     */   public int getOriginY() {
/*  71 */     return 0;
/*     */   }
/*     */ 
/*     */   
/*     */   public int getOriginZ() {
/*  76 */     return 0;
/*     */   }
/*     */ 
/*     */   
/*     */   @Nonnull
/*     */   public String getName() {
/*  82 */     return "null_space";
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isInsideSpace(int x, int y, int z) {
/*  87 */     return false;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isInsideSpace(@Nonnull Vector3i position) {
/*  92 */     return isInsideSpace(position.x, position.y, position.z);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void forEach(VoxelConsumer<? super V> action) {}
/*     */ 
/*     */ 
/*     */   
/*     */   public int minX() {
/* 102 */     return 0;
/*     */   }
/*     */ 
/*     */   
/*     */   public int maxX() {
/* 107 */     return 0;
/*     */   }
/*     */ 
/*     */   
/*     */   public int minY() {
/* 112 */     return 0;
/*     */   }
/*     */ 
/*     */   
/*     */   public int maxY() {
/* 117 */     return 0;
/*     */   }
/*     */ 
/*     */   
/*     */   public int minZ() {
/* 122 */     return 0;
/*     */   }
/*     */ 
/*     */   
/*     */   public int maxZ() {
/* 127 */     return 0;
/*     */   }
/*     */ 
/*     */   
/*     */   public int sizeX() {
/* 132 */     return 0;
/*     */   }
/*     */ 
/*     */   
/*     */   public int sizeY() {
/* 137 */     return 0;
/*     */   }
/*     */ 
/*     */   
/*     */   public int sizeZ() {
/* 142 */     return 0;
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\builtin\hytalegenerator\datastructures\voxelspace\NullSpace.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */