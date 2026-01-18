/*     */ package com.hypixel.hytale.builtin.hytalegenerator.datastructures.voxelspace;
/*     */ 
/*     */ import com.hypixel.hytale.builtin.hytalegenerator.bounds.Bounds3i;
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
/*     */ public interface VoxelSpace<T>
/*     */ {
/*     */   boolean set(T paramT, int paramInt1, int paramInt2, int paramInt3);
/*     */   
/*     */   boolean set(T paramT, @Nonnull Vector3i paramVector3i);
/*     */   
/*     */   void set(T paramT);
/*     */   
/*     */   void setOrigin(int paramInt1, int paramInt2, int paramInt3);
/*     */   
/*     */   @Nullable
/*     */   T getContent(int paramInt1, int paramInt2, int paramInt3);
/*     */   
/*     */   @Nullable
/*     */   T getContent(@Nonnull Vector3i paramVector3i);
/*     */   
/*     */   boolean replace(T paramT, int paramInt1, int paramInt2, int paramInt3, @Nonnull Predicate<T> paramPredicate);
/*     */   
/*     */   void pasteFrom(@Nonnull VoxelSpace<T> paramVoxelSpace);
/*     */   
/*     */   int getOriginX();
/*     */   
/*     */   int getOriginY();
/*     */   
/*     */   int getOriginZ();
/*     */   
/*     */   String getName();
/*     */   
/*     */   boolean isInsideSpace(int paramInt1, int paramInt2, int paramInt3);
/*     */   
/*     */   boolean isInsideSpace(@Nonnull Vector3i paramVector3i);
/*     */   
/*     */   void forEach(VoxelConsumer<? super T> paramVoxelConsumer);
/*     */   
/*     */   @Nonnull
/*     */   default Bounds3i getBounds() {
/* 132 */     return new Bounds3i(new Vector3i(minX(), minY(), minZ()), new Vector3i(maxX(), maxY(), maxZ()));
/*     */   }
/*     */   
/*     */   int minX();
/*     */   
/*     */   int maxX();
/*     */   
/*     */   int minY();
/*     */   
/*     */   int maxY();
/*     */   
/*     */   int minZ();
/*     */   
/*     */   int maxZ();
/*     */   
/*     */   int sizeX();
/*     */   
/*     */   int sizeY();
/*     */   
/*     */   int sizeZ();
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\builtin\hytalegenerator\datastructures\voxelspace\VoxelSpace.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */