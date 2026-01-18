/*     */ package com.hypixel.hytale.math.vector;
/*     */ 
/*     */ import com.hypixel.fastutil.FastCollection;
/*     */ import com.hypixel.hytale.function.consumer.IntBiObjectConsumer;
/*     */ import com.hypixel.hytale.function.consumer.IntObjectConsumer;
/*     */ import com.hypixel.hytale.function.consumer.IntTriObjectConsumer;
/*     */ import com.hypixel.hytale.function.consumer.TriConsumer;
/*     */ import it.unimi.dsi.fastutil.ints.Int2ObjectMap;
/*     */ import it.unimi.dsi.fastutil.objects.ObjectIterator;
/*     */ import java.util.function.BiConsumer;
/*     */ import java.util.function.Consumer;
/*     */ import java.util.function.Function;
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
/*     */ public class VectorBoxUtil
/*     */ {
/*     */   public static void forEachVector(Iterable<Vector3d> vectors, double originX, double originY, double originZ, double apothem, Consumer<Vector3d> consumer) {
/*  33 */     forEachVector(vectors, originX, originY, originZ, apothem, apothem, apothem, consumer);
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
/*     */   public static void forEachVector(Iterable<Vector3d> vectors, double originX, double originY, double originZ, double apothemX, double apothemY, double apothemZ, Consumer<Vector3d> consumer) {
/*  52 */     forEachVector(vectors, Function.identity(), originX, originY, originZ, apothemX, apothemY, apothemZ, consumer);
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
/*     */   public static void forEachVector(Iterable<Vector3d> vectors, double originX, double originY, double originZ, double apothemXMin, double apothemYMin, double apothemZMin, double apothemXMax, double apothemYMax, double apothemZMax, Consumer<Vector3d> consumer) {
/*  75 */     forEachVector(vectors, Function.identity(), originX, originY, originZ, apothemXMin, apothemYMin, apothemZMin, apothemXMax, apothemYMax, apothemZMax, consumer);
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
/*     */   public static <T> void forEachVector(Iterable<T> input, @Nonnull Function<T, Vector3d> func, double originX, double originY, double originZ, double apothem, Consumer<T> consumer) {
/*  93 */     forEachVector(input, func, originX, originY, originZ, apothem, apothem, apothem, consumer);
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
/*     */   public static <T> void forEachVector(Iterable<T> input, @Nonnull Function<T, Vector3d> func, double originX, double originY, double originZ, double apothemX, double apothemY, double apothemZ, Consumer<T> consumer) {
/* 114 */     forEachVector(input, func, originX, originY, originZ, -apothemX, -apothemY, -apothemZ, apothemX, apothemY, apothemZ, consumer);
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
/*     */   public static <T> void forEachVector(Iterable<T> input, @Nonnull Function<T, Vector3d> func, double originX, double originY, double originZ, double apothemXMin, double apothemYMin, double apothemZMin, double apothemXMax, double apothemYMax, double apothemZMax, Consumer<T> consumer) {
/* 139 */     forEachVector(input, func, originX, originY, originZ, apothemXMin, apothemYMin, apothemZMin, apothemXMax, apothemYMax, apothemZMax, (t, c, n0) -> c.accept(t), consumer, (Object)null);
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
/*     */   public static <T, V> void forEachVector(Iterable<T> input, @Nonnull Function<T, Vector3d> func, double originX, double originY, double originZ, double apothem, BiConsumer<T, V> consumer, V objV) {
/* 159 */     forEachVector(input, func, originX, originY, originZ, apothem, apothem, apothem, consumer, objV);
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
/*     */   public static <T, V> void forEachVector(Iterable<T> input, @Nonnull Function<T, Vector3d> func, double originX, double originY, double originZ, double apothemX, double apothemY, double apothemZ, BiConsumer<T, V> consumer, V objV) {
/* 182 */     forEachVector(input, func, originX, originY, originZ, -apothemX, -apothemY, -apothemZ, apothemX, apothemY, apothemZ, consumer, objV);
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
/*     */   public static <T, V> void forEachVector(Iterable<T> input, @Nonnull Function<T, Vector3d> func, double originX, double originY, double originZ, double apothemXMin, double apothemYMin, double apothemZMin, double apothemXMax, double apothemYMax, double apothemZMax, BiConsumer<T, V> consumer, V objV) {
/* 209 */     forEachVector(input, func, originX, originY, originZ, apothemXMin, apothemYMin, apothemZMin, apothemXMax, apothemYMax, apothemZMax, (t, objV1, c) -> c.accept(t, objV1), objV, consumer);
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
/*     */   public static <T, V1, V2> void forEachVector(Iterable<T> input, @Nonnull Function<T, Vector3d> func, double originX, double originY, double originZ, double apothem, @Nonnull TriConsumer<T, V1, V2> consumer, V1 objV1, V2 objV2) {
/* 231 */     forEachVector(input, func, originX, originY, originZ, apothem, apothem, apothem, consumer, objV1, objV2);
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
/*     */   public static <T, V1, V2> void forEachVector(Iterable<T> input, @Nonnull Function<T, Vector3d> func, double originX, double originY, double originZ, double apothemX, double apothemY, double apothemZ, @Nonnull TriConsumer<T, V1, V2> consumer, V1 objV1, V2 objV2) {
/* 256 */     forEachVector(input, func, originX, originY, originZ, -apothemX, -apothemY, -apothemZ, apothemX, apothemY, apothemZ, consumer, objV1, objV2);
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
/*     */   public static <T, V1, V2> void forEachVector(Iterable<T> input, @Nonnull Function<T, Vector3d> func, double originX, double originY, double originZ, double apothemXMin, double apothemYMin, double apothemZMin, double apothemXMax, double apothemYMax, double apothemZMax, @Nonnull TriConsumer<T, V1, V2> consumer, V1 objV1, V2 objV2) {
/* 286 */     if (input instanceof FastCollection) {
/* 287 */       ((FastCollection)input).forEach((obj, _func, _originX, _originY, _originZ, _apothemXMin, _apothemYMin, _apothemZMin, _apothemXMax, _apothemYMax, _apothemZMax, _consumer, _objV1, _objV2) -> { Vector3d vector = _func.apply(obj); if (isInside(_originX, _originY, _originZ, _apothemXMin, _apothemYMin, _apothemZMin, _apothemXMax, _apothemYMax, _apothemZMax, vector)) _consumer.accept(obj, _objV1, _objV2);  }func, originX, originY, originZ, apothemXMin, apothemYMin, apothemZMin, apothemXMax, apothemYMax, apothemZMax, consumer, objV1, objV2);
/*     */ 
/*     */     
/*     */     }
/*     */     else {
/*     */ 
/*     */       
/* 294 */       for (T obj : input) {
/* 295 */         Vector3d vector = func.apply(obj);
/* 296 */         if (isInside(originX, originY, originZ, apothemXMin, apothemYMin, apothemZMin, apothemXMax, apothemYMax, apothemZMax, vector)) {
/* 297 */           consumer.accept(obj, objV1, objV2);
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static <T> void forEachVector(@Nonnull Int2ObjectMap<T> input, @Nonnull Function<T, Vector3d> func, double originX, double originY, double originZ, double apothem, IntObjectConsumer<T> consumer) {
/* 318 */     forEachVector(input, func, originX, originY, originZ, apothem, apothem, apothem, consumer);
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
/*     */   public static <T> void forEachVector(@Nonnull Int2ObjectMap<T> input, @Nonnull Function<T, Vector3d> func, double originX, double originY, double originZ, double apothemX, double apothemY, double apothemZ, IntObjectConsumer<T> consumer) {
/* 339 */     forEachVector(input, func, originX, originY, originZ, -apothemX, -apothemY, -apothemZ, apothemX, apothemY, apothemZ, consumer);
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
/*     */   public static <T> void forEachVector(@Nonnull Int2ObjectMap<T> input, @Nonnull Function<T, Vector3d> func, double originX, double originY, double originZ, double apothemXMin, double apothemYMin, double apothemZMin, double apothemXMax, double apothemYMax, double apothemZMax, IntObjectConsumer<T> consumer) {
/* 364 */     forEachVector(input, func, originX, originY, originZ, apothemXMin, apothemYMin, apothemZMin, apothemXMax, apothemYMax, apothemZMax, (i, t, c, n0) -> c.accept(i, t), consumer, (Object)null);
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
/*     */   public static <T, V> void forEachVector(@Nonnull Int2ObjectMap<T> input, @Nonnull Function<T, Vector3d> func, double originX, double originY, double originZ, double apothem, IntBiObjectConsumer<T, V> consumer, V objV) {
/* 384 */     forEachVector(input, func, originX, originY, originZ, apothem, apothem, apothem, consumer, objV);
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
/*     */   public static <T, V> void forEachVector(@Nonnull Int2ObjectMap<T> input, @Nonnull Function<T, Vector3d> func, double originX, double originY, double originZ, double apothemX, double apothemY, double apothemZ, IntBiObjectConsumer<T, V> consumer, V objV) {
/* 407 */     forEachVector(input, func, originX, originY, originZ, -apothemX, -apothemY, -apothemZ, apothemX, apothemY, apothemZ, consumer, objV);
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
/*     */   public static <T, V> void forEachVector(@Nonnull Int2ObjectMap<T> input, @Nonnull Function<T, Vector3d> func, double originX, double originY, double originZ, double apothemXMin, double apothemYMin, double apothemZMin, double apothemXMax, double apothemYMax, double apothemZMax, IntBiObjectConsumer<T, V> consumer, V objV) {
/* 434 */     forEachVector(input, func, originX, originY, originZ, apothemXMin, apothemYMin, apothemZMin, apothemXMax, apothemYMax, apothemZMax, (i, t, objV1, c) -> c.accept(i, t, objV1), objV, consumer);
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
/*     */   public static <T, V1, V2> void forEachVector(@Nonnull Int2ObjectMap<T> input, @Nonnull Function<T, Vector3d> func, double originX, double originY, double originZ, double apothem, @Nonnull IntTriObjectConsumer<T, V1, V2> consumer, V1 objV1, V2 objV2) {
/* 456 */     forEachVector(input, func, originX, originY, originZ, apothem, apothem, apothem, consumer, objV1, objV2);
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
/*     */   public static <T, V1, V2> void forEachVector(@Nonnull Int2ObjectMap<T> input, @Nonnull Function<T, Vector3d> func, double originX, double originY, double originZ, double apothemX, double apothemY, double apothemZ, @Nonnull IntTriObjectConsumer<T, V1, V2> consumer, V1 objV1, V2 objV2) {
/* 481 */     forEachVector(input, func, originX, originY, originZ, -apothemX, -apothemY, -apothemZ, apothemX, apothemY, apothemZ, consumer, objV1, objV2);
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
/*     */   public static <T, V1, V2> void forEachVector(@Nonnull Int2ObjectMap<T> input, @Nonnull Function<T, Vector3d> func, double originX, double originY, double originZ, double apothemXMin, double apothemYMin, double apothemZMin, double apothemXMax, double apothemYMax, double apothemZMax, @Nonnull IntTriObjectConsumer<T, V1, V2> consumer, V1 objV1, V2 objV2) {
/* 511 */     for (ObjectIterator<Int2ObjectMap.Entry<T>> objectIterator = input.int2ObjectEntrySet().iterator(); objectIterator.hasNext(); ) { Int2ObjectMap.Entry<T> next = objectIterator.next();
/* 512 */       int key = next.getIntKey();
/* 513 */       T value = (T)next.getValue();
/* 514 */       Vector3d vector = func.apply(value);
/* 515 */       if (isInside(originX, originY, originZ, apothemXMin, apothemYMin, apothemZMin, apothemXMax, apothemYMax, apothemZMax, vector)) {
/* 516 */         consumer.accept(key, value, objV1, objV2);
/*     */       } }
/*     */   
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
/*     */   public static boolean isInside(double originX, double originY, double originZ, double apothem, @Nonnull Vector3d vector) {
/* 533 */     return isInside(originX, originY, originZ, apothem, apothem, apothem, vector);
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
/*     */   public static boolean isInside(double originX, double originY, double originZ, double apothemX, double apothemY, double apothemZ, @Nonnull Vector3d vector) {
/* 551 */     return isInside(originX, originY, originZ, -apothemX, -apothemY, -apothemZ, apothemX, apothemY, apothemZ, vector);
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
/*     */   public static boolean isInside(double originX, double originY, double originZ, double xMin, double yMin, double zMin, double xMax, double yMax, double zMax, @Nonnull Vector3d vector) {
/* 573 */     double x = vector.getX() - originX;
/* 574 */     double y = vector.getY() - originY;
/* 575 */     double z = vector.getZ() - originZ;
/*     */     
/* 577 */     return (x >= xMin && x <= xMax && y >= yMin && y <= yMax && z >= zMin && z <= zMax);
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\math\vector\VectorBoxUtil.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */