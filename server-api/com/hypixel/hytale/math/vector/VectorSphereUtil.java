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
/*     */ public class VectorSphereUtil
/*     */ {
/*     */   public static void forEachVector(Iterable<Vector3d> vectors, double originX, double originY, double originZ, double radius, Consumer<Vector3d> consumer) {
/*  33 */     forEachVector(vectors, originX, originY, originZ, radius, radius, radius, consumer);
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
/*     */   public static void forEachVector(Iterable<Vector3d> vectors, double originX, double originY, double originZ, double radiusX, double radiusY, double radiusZ, Consumer<Vector3d> consumer) {
/*  52 */     forEachVector(vectors, Function.identity(), originX, originY, originZ, radiusX, radiusY, radiusZ, consumer);
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
/*     */   public static <T> void forEachVector(Iterable<T> input, @Nonnull Function<T, Vector3d> func, double originX, double originY, double originZ, double radius, Consumer<T> consumer) {
/*  70 */     forEachVector(input, func, originX, originY, originZ, radius, radius, radius, consumer);
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
/*     */   public static <T> void forEachVector(Iterable<T> input, @Nonnull Function<T, Vector3d> func, double originX, double originY, double originZ, double radiusX, double radiusY, double radiusZ, Consumer<T> consumer) {
/*  91 */     forEachVector(input, func, originX, originY, originZ, radiusX, radiusY, radiusZ, (t, c, n0) -> c.accept(t), consumer, (Object)null);
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
/*     */   public static <T, V> void forEachVector(Iterable<T> input, @Nonnull Function<T, Vector3d> func, double originX, double originY, double originZ, double radius, BiConsumer<T, V> consumer, V objV) {
/* 111 */     forEachVector(input, func, originX, originY, originZ, radius, radius, radius, consumer, objV);
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
/*     */   public static <T, V> void forEachVector(Iterable<T> input, @Nonnull Function<T, Vector3d> func, double originX, double originY, double originZ, double radiusX, double radiusY, double radiusZ, BiConsumer<T, V> consumer, V objV) {
/* 134 */     forEachVector(input, func, originX, originY, originZ, radiusX, radiusY, radiusZ, (t, c, objV2) -> c.accept(t, objV2), consumer, objV);
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
/*     */   public static <T, V1, V2> void forEachVector(Iterable<T> input, @Nonnull Function<T, Vector3d> func, double originX, double originY, double originZ, double radius, @Nonnull TriConsumer<T, V1, V2> consumer, V1 objV1, V2 objV2) {
/* 156 */     forEachVector(input, func, originX, originY, originZ, radius, radius, radius, consumer, objV1, objV2);
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
/*     */   public static <T, V1, V2> void forEachVector(Iterable<T> input, @Nonnull Function<T, Vector3d> func, double originX, double originY, double originZ, double radiusX, double radiusY, double radiusZ, @Nonnull TriConsumer<T, V1, V2> consumer, V1 objV1, V2 objV2) {
/* 181 */     if (input instanceof FastCollection) { FastCollection<T> fastCollection = (FastCollection<T>)input;
/* 182 */       fastCollection.forEach((obj, _func, _originX, _originY, _originZ, _radiusX, _radiusY, _radiusZ, _consumer, _objV1, _objV2) -> { Vector3d vector = _func.apply(obj); if (isInside(_originX, _originY, _originZ, _radiusX, _radiusY, _radiusZ, vector)) _consumer.accept(obj, _objV1, _objV2);  }func, originX, originY, originZ, radiusX, radiusY, radiusZ, consumer, objV1, objV2);
/*     */       
/*     */        }
/*     */     
/*     */     else
/*     */     
/*     */     { 
/* 189 */       for (T obj : input) {
/* 190 */         Vector3d vector = func.apply(obj);
/* 191 */         if (isInside(originX, originY, originZ, radiusX, radiusY, radiusZ, vector)) {
/* 192 */           consumer.accept(obj, objV1, objV2);
/*     */         }
/*     */       }  }
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
/*     */ 
/*     */ 
/*     */   
/*     */   public static <T> void forEachVector(@Nonnull Int2ObjectMap<T> input, @Nonnull Function<T, Vector3d> func, double originX, double originY, double originZ, double radius, IntObjectConsumer<T> consumer) {
/* 213 */     forEachVector(input, func, originX, originY, originZ, radius, radius, radius, consumer);
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
/*     */   public static <T> void forEachVector(@Nonnull Int2ObjectMap<T> input, @Nonnull Function<T, Vector3d> func, double originX, double originY, double originZ, double radiusX, double radiusY, double radiusZ, IntObjectConsumer<T> consumer) {
/* 234 */     forEachVector(input, func, originX, originY, originZ, radiusX, radiusY, radiusZ, (i, t, c, n0) -> c.accept(i, t), consumer, (Object)null);
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
/*     */   public static <T, V> void forEachVector(@Nonnull Int2ObjectMap<T> input, @Nonnull Function<T, Vector3d> func, double originX, double originY, double originZ, double radius, IntBiObjectConsumer<T, V> consumer, V objV) {
/* 254 */     forEachVector(input, func, originX, originY, originZ, radius, radius, radius, consumer, objV);
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
/*     */   public static <T, V> void forEachVector(@Nonnull Int2ObjectMap<T> input, @Nonnull Function<T, Vector3d> func, double originX, double originY, double originZ, double radiusX, double radiusY, double radiusZ, IntBiObjectConsumer<T, V> consumer, V objV) {
/* 277 */     forEachVector(input, func, originX, originY, originZ, radiusX, radiusY, radiusZ, (i, t, objV1, c) -> c.accept(i, t, objV1), objV, consumer);
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
/*     */   public static <T, V1, V2> void forEachVector(@Nonnull Int2ObjectMap<T> input, @Nonnull Function<T, Vector3d> func, double originX, double originY, double originZ, double radius, @Nonnull IntTriObjectConsumer<T, V1, V2> consumer, V1 objV1, V2 objV2) {
/* 299 */     forEachVector(input, func, originX, originY, originZ, radius, radius, radius, consumer, objV1, objV2);
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
/*     */   public static <T, V1, V2> void forEachVector(@Nonnull Int2ObjectMap<T> input, @Nonnull Function<T, Vector3d> func, double originX, double originY, double originZ, double radiusX, double radiusY, double radiusZ, @Nonnull IntTriObjectConsumer<T, V1, V2> consumer, V1 objV1, V2 objV2) {
/* 325 */     for (ObjectIterator<Int2ObjectMap.Entry<T>> objectIterator = input.int2ObjectEntrySet().iterator(); objectIterator.hasNext(); ) { Int2ObjectMap.Entry<T> next = objectIterator.next();
/* 326 */       int key = next.getIntKey();
/* 327 */       T value = (T)next.getValue();
/* 328 */       Vector3d vector = func.apply(value);
/* 329 */       if (isInside(originX, originY, originZ, radiusX, radiusY, radiusZ, vector)) {
/* 330 */         consumer.accept(key, value, objV1, objV2);
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
/*     */   public static boolean isInside(double originX, double originY, double originZ, double radius, @Nonnull Vector3d vector) {
/* 347 */     return isInside(originX, originY, originZ, radius, radius, radius, vector);
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
/*     */   public static boolean isInside(double originX, double originY, double originZ, double radiusX, double radiusY, double radiusZ, @Nonnull Vector3d vector) {
/* 365 */     double x = vector.getX() - originX;
/* 366 */     double y = vector.getY() - originY;
/* 367 */     double z = vector.getZ() - originZ;
/*     */     
/* 369 */     double xRatio = x / radiusX;
/* 370 */     double yRatio = y / radiusY;
/* 371 */     double zRatio = z / radiusZ;
/*     */     
/* 373 */     return (xRatio * xRatio + yRatio * yRatio + zRatio * zRatio <= 1.0D);
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\math\vector\VectorSphereUtil.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */