/*    */ package com.hypixel.hytale.common.util;
/*    */ 
/*    */ import it.unimi.dsi.fastutil.objects.ObjectArrayList;
/*    */ import java.util.Comparator;
/*    */ import java.util.List;
/*    */ import java.util.function.BiPredicate;
/*    */ import java.util.function.Function;
/*    */ import java.util.function.Predicate;
/*    */ import javax.annotation.Nonnull;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class ListUtil
/*    */ {
/*    */   @Nonnull
/*    */   public static <T> List<List<T>> partition(@Nonnull List<T> list, int sectionSize) {
/* 21 */     ObjectArrayList objectArrayList = new ObjectArrayList();
/* 22 */     for (int i = 0; i < list.size(); i += sectionSize) {
/* 23 */       int endIndex = Math.min(list.size(), i + sectionSize);
/* 24 */       objectArrayList.add(list.subList(i, endIndex));
/*    */     } 
/* 26 */     return (List<List<T>>)objectArrayList;
/*    */   }
/*    */   
/*    */   public static <T> void removeIf(@Nonnull List<T> list, @Nonnull Predicate<T> predicate) {
/* 30 */     for (int i = list.size() - 1; i >= 0; i--) {
/* 31 */       if (predicate.test(list.get(i))) {
/* 32 */         list.remove(i);
/*    */       }
/*    */     } 
/*    */   }
/*    */   
/*    */   public static <T, U> void removeIf(@Nonnull List<T> list, @Nonnull BiPredicate<T, U> predicate, U obj) {
/* 38 */     for (int i = list.size() - 1; i >= 0; i--) {
/* 39 */       if (predicate.test(list.get(i), obj)) {
/* 40 */         list.remove(i);
/*    */       }
/*    */     } 
/*    */   }
/*    */   
/*    */   public static <T> boolean emptyOrAllNull(@Nonnull List<T> list) {
/* 46 */     for (int i = 0; i < list.size(); i++) {
/* 47 */       T e = list.get(i);
/* 48 */       if (e != null) return false; 
/*    */     } 
/* 50 */     return true;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public static <T, V> int binarySearch(@Nonnull List<? extends T> l, @Nonnull Function<T, V> func, V key, @Nonnull Comparator<? super V> c) {
/* 57 */     int low = 0;
/* 58 */     int high = l.size() - 1;
/*    */     
/* 60 */     while (low <= high) {
/* 61 */       int mid = low + high >>> 1;
/* 62 */       T midVal = l.get(mid);
/* 63 */       int cmp = c.compare(func.apply(midVal), key);
/*    */       
/* 65 */       if (cmp < 0) {
/* 66 */         low = mid + 1; continue;
/* 67 */       }  if (cmp > 0) {
/* 68 */         high = mid - 1; continue;
/*    */       } 
/* 70 */       return mid;
/*    */     } 
/*    */     
/* 73 */     return -(low + 1);
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\commo\\util\ListUtil.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */