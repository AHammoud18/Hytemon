/*     */ package com.hypixel.hytale.sneakythrow;
/*     */ 
/*     */ import com.hypixel.hytale.function.consumer.TriConsumer;
/*     */ import com.hypixel.hytale.sneakythrow.consumer.ThrowableBiConsumer;
/*     */ import com.hypixel.hytale.sneakythrow.consumer.ThrowableConsumer;
/*     */ import com.hypixel.hytale.sneakythrow.consumer.ThrowableIntConsumer;
/*     */ import com.hypixel.hytale.sneakythrow.consumer.ThrowableTriConsumer;
/*     */ import com.hypixel.hytale.sneakythrow.function.ThrowableBiFunction;
/*     */ import com.hypixel.hytale.sneakythrow.function.ThrowableFunction;
/*     */ import com.hypixel.hytale.sneakythrow.supplier.ThrowableIntSupplier;
/*     */ import com.hypixel.hytale.sneakythrow.supplier.ThrowableSupplier;
/*     */ import java.util.function.BiConsumer;
/*     */ import java.util.function.BiFunction;
/*     */ import java.util.function.Consumer;
/*     */ import java.util.function.Function;
/*     */ import java.util.function.IntConsumer;
/*     */ import java.util.function.IntSupplier;
/*     */ import java.util.function.Supplier;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class SneakyThrow
/*     */ {
/*     */   @Nonnull
/*     */   public static RuntimeException sneakyThrow(@Nonnull Throwable t) {
/*  66 */     if (t == null) throw new NullPointerException("t"); 
/*  67 */     return sneakyThrow0(t);
/*     */   }
/*     */ 
/*     */   
/*     */   private static <T extends Throwable> T sneakyThrow0(Throwable t) throws T {
/*  72 */     throw (T)t;
/*     */   }
/*     */   
/*     */   public static <E extends Throwable> Runnable sneakyRunnable(ThrowableRunnable<E> runnable) {
/*  76 */     return runnable;
/*     */   }
/*     */   
/*     */   public static <T, E extends Throwable> Supplier<T> sneakySupplier(ThrowableSupplier<T, E> supplier) {
/*  80 */     return (Supplier)supplier;
/*     */   }
/*     */   
/*     */   public static <E extends Throwable> IntSupplier sneakySupplier(ThrowableIntSupplier<E> supplier) {
/*  84 */     return (IntSupplier)supplier;
/*     */   }
/*     */   
/*     */   public static <T, E extends Throwable> Consumer<T> sneakyConsumer(ThrowableConsumer<T, E> consumer) {
/*  88 */     return (Consumer)consumer;
/*     */   }
/*     */   
/*     */   public static <T, U, E extends Throwable> BiConsumer<T, U> sneakyConsumer(ThrowableBiConsumer<T, U, E> consumer) {
/*  92 */     return (BiConsumer)consumer;
/*     */   }
/*     */   
/*     */   public static <T, U, V, E extends Throwable> TriConsumer<T, U, V> sneakyConsumer(ThrowableTriConsumer<T, U, V, E> consumer) {
/*  96 */     return (TriConsumer)consumer;
/*     */   }
/*     */   
/*     */   public static <E extends Throwable> IntConsumer sneakyIntConsumer(ThrowableIntConsumer<E> consumer) {
/* 100 */     return (IntConsumer)consumer;
/*     */   }
/*     */   
/*     */   public static <T, R, E extends Throwable> Function<T, R> sneakyFunction(ThrowableFunction<T, R, E> function) {
/* 104 */     return (Function)function;
/*     */   }
/*     */   
/*     */   public static <T, U, R, E extends Throwable> BiFunction<T, U, R> sneakyFunction(ThrowableBiFunction<T, U, R, E> function) {
/* 108 */     return (BiFunction)function;
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\sneakythrow\SneakyThrow.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */