/*     */ package com.hypixel.hytale.server.core.prefab.selection.buffer.impl;
/*     */ 
/*     */ import com.hypixel.hytale.component.Holder;
/*     */ import com.hypixel.hytale.server.core.prefab.PrefabRotation;
/*     */ import com.hypixel.hytale.server.core.prefab.PrefabWeights;
/*     */ import com.hypixel.hytale.server.core.prefab.selection.buffer.PrefabBufferCall;
/*     */ import com.hypixel.hytale.server.core.universe.world.storage.ChunkStore;
/*     */ import com.hypixel.hytale.server.core.universe.world.storage.EntityStore;
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
/*     */ public interface IPrefabBuffer
/*     */ {
/*     */   public static final ColumnPredicate<?> ALL_COLUMNS = (x, z, blocks, o) -> true;
/*     */   
/*     */   default int getMinX() {
/*  35 */     return getMinX(PrefabRotation.ROTATION_0);
/*     */   }
/*     */   
/*     */   default int getMinZ() {
/*  39 */     return getMinZ(PrefabRotation.ROTATION_0);
/*     */   }
/*     */   
/*     */   default int getMaxX() {
/*  43 */     return getMaxX(PrefabRotation.ROTATION_0);
/*     */   }
/*     */   
/*     */   default int getMaxZ() {
/*  47 */     return getMaxZ(PrefabRotation.ROTATION_0);
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
/*     */   default int getMaximumExtend() {
/*  60 */     int max = 0;
/*  61 */     for (PrefabRotation rotation : PrefabRotation.VALUES) {
/*  62 */       int x = getMaxX(rotation) - getMinX(rotation);
/*  63 */       if (x > max) max = x; 
/*  64 */       int z = getMaxZ(rotation) - getMinZ(rotation);
/*  65 */       if (z > max) max = z; 
/*     */     } 
/*  67 */     return max;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   default <T extends PrefabBufferCall> boolean compare(@Nonnull BlockComparingPredicate<T> blockComparingPredicate, @Nonnull T t) {
/* 120 */     return forEachRaw(iterateAllColumns(), (x, y, z, blockId, chance, holder, support, rotation, filler, o) -> blockComparingPredicate.test(x, y, z, blockId, rotation, holder, o), (x, y, z, fluidId, level, o) -> true, (x, z, entityWrappers, o) -> true, t);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   default <T extends PrefabBufferCall> boolean compare(@Nonnull BlockComparingPrefabPredicate<T> blockComparingIterator, @Nonnull T t, @Nonnull IPrefabBuffer secondPrefab) {
/* 131 */     throw new UnsupportedOperationException("Not implemented! Please implement some inefficient default here!");
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Nonnull
/*     */   static <T> ColumnPredicate<T> iterateAllColumns() {
/* 141 */     return (ColumnPredicate)ALL_COLUMNS;
/*     */   }
/*     */   
/*     */   int getAnchorX();
/*     */   
/*     */   int getAnchorY();
/*     */   
/*     */   int getAnchorZ();
/*     */   
/*     */   int getMinX(@Nonnull PrefabRotation paramPrefabRotation);
/*     */   
/*     */   int getMinY();
/*     */   
/*     */   int getMinZ(@Nonnull PrefabRotation paramPrefabRotation);
/*     */   
/*     */   int getMaxX(@Nonnull PrefabRotation paramPrefabRotation);
/*     */   
/*     */   int getMaxY();
/*     */   
/*     */   int getMaxZ(@Nonnull PrefabRotation paramPrefabRotation);
/*     */   
/*     */   int getMinYAt(@Nonnull PrefabRotation paramPrefabRotation, int paramInt1, int paramInt2);
/*     */   
/*     */   int getMaxYAt(@Nonnull PrefabRotation paramPrefabRotation, int paramInt1, int paramInt2);
/*     */   
/*     */   int getColumnCount();
/*     */   
/*     */   @Nonnull
/*     */   PrefabBuffer.ChildPrefab[] getChildPrefabs();
/*     */   
/*     */   <T extends PrefabBufferCall> void forEach(@Nonnull ColumnPredicate<T> paramColumnPredicate, @Nonnull BlockConsumer<T> paramBlockConsumer, @Nullable EntityConsumer<T> paramEntityConsumer, @Nullable ChildConsumer<T> paramChildConsumer, @Nonnull T paramT);
/*     */   
/*     */   <T> void forEachRaw(@Nonnull ColumnPredicate<T> paramColumnPredicate, @Nonnull RawBlockConsumer<T> paramRawBlockConsumer, @Nonnull FluidConsumer<T> paramFluidConsumer, @Nullable EntityConsumer<T> paramEntityConsumer, @Nullable T paramT);
/*     */   
/*     */   <T> boolean forEachRaw(@Nonnull ColumnPredicate<T> paramColumnPredicate, @Nonnull RawBlockPredicate<T> paramRawBlockPredicate, @Nonnull FluidPredicate<T> paramFluidPredicate, @Nullable EntityPredicate<T> paramEntityPredicate, @Nullable T paramT);
/*     */   
/*     */   void release();
/*     */   
/*     */   int getBlockId(int paramInt1, int paramInt2, int paramInt3);
/*     */   
/*     */   int getFiller(int paramInt1, int paramInt2, int paramInt3);
/*     */   
/*     */   int getRotationIndex(int paramInt1, int paramInt2, int paramInt3);
/*     */   
/*     */   @FunctionalInterface
/*     */   public static interface ColumnPredicate<T> {
/*     */     boolean test(int param1Int1, int param1Int2, int param1Int3, T param1T);
/*     */   }
/*     */   
/*     */   @FunctionalInterface
/*     */   public static interface BlockComparingPredicate<T> {
/*     */     boolean test(int param1Int1, int param1Int2, int param1Int3, int param1Int4, int param1Int5, Holder<ChunkStore> param1Holder, T param1T);
/*     */   }
/*     */   
/*     */   @FunctionalInterface
/*     */   public static interface RawBlockPredicate<T> {
/*     */     boolean test(int param1Int1, int param1Int2, int param1Int3, int param1Int4, float param1Float, Holder<ChunkStore> param1Holder, int param1Int5, int param1Int6, int param1Int7, T param1T);
/*     */   }
/*     */   
/*     */   @FunctionalInterface
/*     */   public static interface FluidPredicate<T> {
/*     */     boolean test(int param1Int1, int param1Int2, int param1Int3, int param1Int4, byte param1Byte, T param1T);
/*     */   }
/*     */   
/*     */   @FunctionalInterface
/*     */   public static interface EntityPredicate<T> {
/*     */     boolean test(int param1Int1, int param1Int2, @Nonnull Holder<EntityStore>[] param1ArrayOfHolder, T param1T);
/*     */   }
/*     */   
/*     */   @FunctionalInterface
/*     */   public static interface BlockComparingPrefabPredicate<T> {
/*     */     boolean test(int param1Int1, int param1Int2, int param1Int3, int param1Int4, Holder<ChunkStore> param1Holder1, float param1Float1, int param1Int5, int param1Int6, int param1Int7, Holder<ChunkStore> param1Holder2, float param1Float2, int param1Int8, int param1Int9, T param1T);
/*     */   }
/*     */   
/*     */   @FunctionalInterface
/*     */   public static interface RawBlockConsumer<T> {
/*     */     void accept(int param1Int1, int param1Int2, int param1Int3, int param1Int4, int param1Int5, float param1Float, Holder<ChunkStore> param1Holder, int param1Int6, int param1Int7, int param1Int8, T param1T);
/*     */   }
/*     */   
/*     */   @FunctionalInterface
/*     */   public static interface EntityConsumer<T> {
/*     */     void accept(int param1Int1, int param1Int2, @Nullable Holder<EntityStore>[] param1ArrayOfHolder, T param1T);
/*     */   }
/*     */   
/*     */   @FunctionalInterface
/*     */   public static interface ChildConsumer<T> {
/*     */     void accept(int param1Int1, int param1Int2, int param1Int3, String param1String, boolean param1Boolean1, boolean param1Boolean2, boolean param1Boolean3, PrefabWeights param1PrefabWeights, PrefabRotation param1PrefabRotation, T param1T);
/*     */   }
/*     */   
/*     */   @FunctionalInterface
/*     */   public static interface FluidConsumer<T> {
/*     */     void accept(int param1Int1, int param1Int2, int param1Int3, int param1Int4, byte param1Byte, T param1T);
/*     */   }
/*     */   
/*     */   @FunctionalInterface
/*     */   public static interface BlockConsumer<T> {
/*     */     void accept(int param1Int1, int param1Int2, int param1Int3, int param1Int4, @Nullable Holder<ChunkStore> param1Holder, int param1Int5, int param1Int6, int param1Int7, T param1T, int param1Int8, int param1Int9);
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\server\core\prefab\selection\buffer\impl\IPrefabBuffer.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */