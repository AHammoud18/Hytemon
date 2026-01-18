/*     */ package com.hypixel.hytale.builtin.hytalegenerator;
/*     */ 
/*     */ import java.util.ArrayList;
/*     */ import java.util.Collections;
/*     */ import java.util.List;
/*     */ import java.util.function.BiFunction;
/*     */ import javax.annotation.Nonnull;
/*     */ 
/*     */ public class ArrayUtil
/*     */ {
/*     */   @Nonnull
/*     */   public static <T> T[] brokenCopyOf(@Nonnull T[] a) {
/*  13 */     T[] copy = (T[])new Object[a.length];
/*  14 */     System.arraycopy(a, 0, copy, 0, a.length);
/*  15 */     return copy;
/*     */   }
/*     */   
/*     */   public static <T> void copy(@Nonnull T[] source, @Nonnull T[] destination) {
/*  19 */     if (source.length != destination.length)
/*  20 */       throw new IllegalArgumentException("arrays must have the same size"); 
/*  21 */     System.arraycopy(source, 0, destination, 0, source.length);
/*     */   }
/*     */   
/*     */   @Nonnull
/*     */   public static <T> T[] append(@Nonnull T[] a, T e) {
/*  26 */     T[] expanded = (T[])new Object[a.length + 1];
/*  27 */     System.arraycopy(a, 0, expanded, 0, a.length);
/*  28 */     expanded[a.length] = e;
/*  29 */     return expanded;
/*     */   }
/*     */   
/*     */   @Nonnull
/*     */   public static <T> List<List<T>> split(@Nonnull List<T> list, int partCount) {
/*  34 */     if (partCount < 1) {
/*  35 */       throw new IllegalArgumentException("parts must be greater than 0");
/*     */     }
/*     */     
/*  38 */     if (partCount == 1) {
/*  39 */       return Collections.singletonList(list);
/*     */     }
/*     */     
/*  42 */     List<List<T>> out = new ArrayList<>(partCount);
/*  43 */     int listSize = list.size();
/*     */     
/*  45 */     if (listSize <= partCount) {
/*  46 */       int i; for (i = 0; i < listSize; i++) {
/*  47 */         out.add(List.of(list.get(i)));
/*     */       }
/*  49 */       for (i = listSize; i < partCount; i++) {
/*  50 */         out.add(List.of());
/*     */       }
/*  52 */       return out;
/*     */     } 
/*     */     
/*  55 */     int[] partSizes = getPartSizes(listSize, partCount);
/*     */     
/*  57 */     int elementIndex = 0;
/*  58 */     for (int partIndex = 0; partIndex < partCount; partIndex++) {
/*     */       
/*  60 */       int partSize = partSizes[partIndex];
/*  61 */       List<T> partList = new ArrayList<>(partSize);
/*     */       
/*  63 */       for (int i = 0; i < partSize; i++) {
/*  64 */         partList.add(list.get(elementIndex++));
/*     */       }
/*     */       
/*  67 */       out.add(partList);
/*     */     } 
/*     */     
/*  70 */     return out;
/*     */   }
/*     */   
/*     */   public static int[] getPartSizes(int total, int partCount) {
/*  74 */     if (total < 0 || partCount < 1) {
/*  75 */       throw new IllegalArgumentException("total and/or parts must be greater than 0");
/*     */     }
/*     */     
/*  78 */     if (total == 0) {
/*  79 */       return new int[] { total };
/*     */     }
/*     */     
/*  82 */     int[] sizes = new int[partCount];
/*  83 */     int baseSize = total / partCount;
/*  84 */     int remainder = total % partCount;
/*     */     
/*  86 */     for (int i = 0; i < partCount; i++) {
/*  87 */       if (i < remainder) {
/*  88 */         sizes[i] = baseSize + 1;
/*     */       } else {
/*  90 */         sizes[i] = baseSize;
/*     */       } 
/*     */     } 
/*     */     
/*  94 */     return sizes;
/*     */   }
/*     */   
/*     */   public static <T, G> int sortedSearch(@Nonnull List<T> sortedList, @Nonnull G gauge, @Nonnull BiFunction<G, T, Integer> comparator) {
/*  98 */     int BINARY_SIZE_THRESHOLD = 250;
/*     */     
/* 100 */     if (sortedList.isEmpty()) return -1;
/*     */     
/* 102 */     if (sortedList.size() == 1) {
/* 103 */       if (((Integer)comparator.apply(gauge, sortedList.getFirst())).intValue() == 0)
/* 104 */         return 0; 
/* 105 */       return -1;
/*     */     } 
/*     */     
/* 108 */     if (sortedList.size() <= 250) {
/*     */       
/* 110 */       for (int i = 0; i < sortedList.size(); i++) {
/* 111 */         if (((Integer)comparator.apply(gauge, sortedList.get(i))).intValue() == 0)
/* 112 */           return i; 
/*     */       } 
/* 114 */       return -1;
/*     */     } 
/*     */     
/* 117 */     return binarySearch(sortedList, gauge, comparator);
/*     */   }
/*     */   
/*     */   public static <T, G> int binarySearch(@Nonnull List<T> sortedList, @Nonnull G gauge, @Nonnull BiFunction<G, T, Integer> comparator) {
/* 121 */     if (sortedList.isEmpty()) return -1;
/*     */     
/* 123 */     int min = 0;
/* 124 */     int max = sortedList.size();
/*     */ 
/*     */     
/*     */     while (true) {
/* 128 */       int index = (max + min) / 2;
/*     */       
/* 130 */       T item = sortedList.get(index);
/* 131 */       int comparison = ((Integer)comparator.apply(gauge, item)).intValue();
/*     */       
/* 133 */       if (comparison == 0) {
/* 134 */         return index;
/*     */       }
/*     */       
/* 137 */       if (min == max - 1) {
/* 138 */         return -1;
/*     */       }
/*     */       
/* 141 */       if (comparison == -1) {
/* 142 */         max = index;
/*     */       }
/*     */       
/* 145 */       if (comparison == 1)
/* 146 */         min = index; 
/*     */     } 
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\builtin\hytalegenerator\ArrayUtil.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */