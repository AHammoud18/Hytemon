/*    */ package com.hypixel.hytale.common.util;
/*    */ 
/*    */ import it.unimi.dsi.fastutil.objects.Object2ObjectOpenHashMap;
/*    */ import java.util.Collections;
/*    */ import java.util.Map;
/*    */ import java.util.function.Supplier;
/*    */ import javax.annotation.Nonnull;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class MapUtil
/*    */ {
/*    */   @Nonnull
/*    */   public static <T, V> Map<T, V> combineUnmodifiable(@Nonnull Map<T, V> one, @Nonnull Map<T, V> two) {
/* 26 */     Object2ObjectOpenHashMap<T, V> object2ObjectOpenHashMap = new Object2ObjectOpenHashMap();
/* 27 */     object2ObjectOpenHashMap.putAll(one);
/* 28 */     object2ObjectOpenHashMap.putAll(two);
/* 29 */     return Collections.unmodifiableMap((Map<? extends T, ? extends V>)object2ObjectOpenHashMap);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   @Nonnull
/*    */   public static <T, V, M extends Map<T, V>> Map<T, V> combineUnmodifiable(@Nonnull Map<T, V> one, @Nonnull Map<T, V> two, @Nonnull Supplier<M> supplier) {
/* 45 */     Map<T, V> map = (Map<T, V>)supplier.get();
/* 46 */     map.putAll(one);
/* 47 */     map.putAll(two);
/* 48 */     return Collections.unmodifiableMap(map);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   @Nonnull
/*    */   public static <T, V, M extends Map<T, V>> M combine(@Nonnull Map<T, V> one, @Nonnull Map<T, V> two, @Nonnull Supplier<M> supplier) {
/* 64 */     Map<T, V> map = (Map)supplier.get();
/* 65 */     map.putAll(one);
/* 66 */     map.putAll(two);
/* 67 */     return (M)map;
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\commo\\util\MapUtil.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */