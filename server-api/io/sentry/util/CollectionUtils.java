/*     */ package io.sentry.util;
/*     */ 
/*     */ import java.util.ArrayList;
/*     */ import java.util.Collection;
/*     */ import java.util.HashMap;
/*     */ import java.util.List;
/*     */ import java.util.ListIterator;
/*     */ import java.util.Map;
/*     */ import java.util.concurrent.ConcurrentHashMap;
/*     */ import java.util.concurrent.CopyOnWriteArrayList;
/*     */ import org.jetbrains.annotations.ApiStatus.Internal;
/*     */ import org.jetbrains.annotations.NotNull;
/*     */ import org.jetbrains.annotations.Nullable;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ @Internal
/*     */ public final class CollectionUtils
/*     */ {
/*     */   public static int size(@NotNull Iterable<?> data) {
/*  28 */     if (data instanceof Collection) {
/*  29 */       return ((Collection)data).size();
/*     */     }
/*  31 */     int counter = 0;
/*  32 */     for (Object ignored : data) {
/*  33 */       counter++;
/*     */     }
/*  35 */     return counter;
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
/*     */   @Nullable
/*     */   public static <K, V> Map<K, V> newConcurrentHashMap(@Nullable Map<K, V> map) {
/*  49 */     if (map != null) {
/*  50 */       Map<K, V> concurrentMap = new ConcurrentHashMap<>();
/*     */       
/*  52 */       for (Map.Entry<K, V> entry : map.entrySet()) {
/*  53 */         if (entry.getKey() != null && entry.getValue() != null) {
/*  54 */           concurrentMap.put(entry.getKey(), entry.getValue());
/*     */         }
/*     */       } 
/*  57 */       return concurrentMap;
/*     */     } 
/*  59 */     return null;
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
/*     */   @Nullable
/*     */   public static <K, V> Map<K, V> newHashMap(@Nullable Map<K, V> map) {
/*  72 */     if (map != null) {
/*  73 */       return new HashMap<>(map);
/*     */     }
/*  75 */     return null;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Nullable
/*     */   public static <T> List<T> newArrayList(@Nullable List<T> list) {
/*  87 */     if (list != null) {
/*  88 */       return new ArrayList<>(list);
/*     */     }
/*  90 */     return null;
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
/*     */   @NotNull
/*     */   public static <K, V> Map<K, V> filterMapEntries(@NotNull Map<K, V> map, @NotNull Predicate<Map.Entry<K, V>> predicate) {
/* 105 */     Map<K, V> filteredMap = new HashMap<>();
/* 106 */     for (Map.Entry<K, V> entry : map.entrySet()) {
/* 107 */       if (predicate.test(entry)) {
/* 108 */         filteredMap.put(entry.getKey(), entry.getValue());
/*     */       }
/*     */     } 
/* 111 */     return filteredMap;
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
/*     */   @NotNull
/*     */   public static <T, R> List<R> map(@NotNull List<T> list, @NotNull Mapper<T, R> f) {
/* 126 */     List<R> mappedList = new ArrayList<>(list.size());
/* 127 */     for (T t : list) {
/* 128 */       mappedList.add(f.map(t));
/*     */     }
/* 130 */     return mappedList;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @NotNull
/*     */   public static <T> List<T> filterListEntries(@NotNull List<T> list, @NotNull Predicate<T> predicate) {
/* 141 */     List<T> filteredList = new ArrayList<>(list.size());
/* 142 */     for (T entry : list) {
/* 143 */       if (predicate.test(entry)) {
/* 144 */         filteredList.add(entry);
/*     */       }
/*     */     } 
/* 147 */     return filteredList;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static <T> boolean contains(@NotNull T[] array, @NotNull T element) {
/* 158 */     for (T t : array) {
/* 159 */       if (element.equals(t)) {
/* 160 */         return true;
/*     */       }
/*     */     } 
/* 163 */     return false;
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
/*     */   @NotNull
/*     */   public static <T> ListIterator<T> reverseListIterator(@NotNull CopyOnWriteArrayList<T> list) {
/* 200 */     CopyOnWriteArrayList<T> copy = new CopyOnWriteArrayList<>(list);
/* 201 */     return copy.listIterator(copy.size());
/*     */   }
/*     */   
/*     */   public static interface Predicate<T> {
/*     */     boolean test(T param1T);
/*     */   }
/*     */   
/*     */   public static interface Mapper<T, R> {
/*     */     R map(T param1T);
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\io\sentr\\util\CollectionUtils.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */