/*     */ package com.hypixel.hytale.common.util;
/*     */ 
/*     */ import com.hypixel.hytale.function.predicate.UnaryBiPredicate;
/*     */ import com.hypixel.hytale.math.util.MathUtil;
/*     */ import java.lang.reflect.Array;
/*     */ import java.util.Arrays;
/*     */ import java.util.BitSet;
/*     */ import java.util.Map;
/*     */ import java.util.Objects;
/*     */ import java.util.Random;
/*     */ import java.util.function.Function;
/*     */ import java.util.function.IntFunction;
/*     */ import java.util.function.Supplier;
/*     */ import javax.annotation.Nonnull;
/*     */ import javax.annotation.Nullable;
/*     */ 
/*     */ 
/*     */ 
/*     */ public class ArrayUtil
/*     */ {
/*  21 */   public static final String[] EMPTY_STRING_ARRAY = new String[0];
/*  22 */   public static final double[] EMPTY_DOUBLE_ARRAY = new double[0];
/*  23 */   public static final int[] EMPTY_INT_ARRAY = new int[0];
/*  24 */   public static final long[] EMPTY_LONG_ARRAY = new long[0];
/*  25 */   public static final boolean[] EMPTY_BOOLEAN_ARRAY = new boolean[0];
/*  26 */   public static final Integer[] EMPTY_INTEGER_ARRAY = new Integer[0];
/*  27 */   public static final byte[] EMPTY_BYTE_ARRAY = new byte[0];
/*  28 */   public static final BitSet[] EMPTY_BITSET_ARRAY = new BitSet[0];
/*  29 */   public static final float[] EMPTY_FLOAT_ARRAY = new float[0];
/*     */   
/*  31 */   private static final Object[] EMPTY_OBJECT_ARRAY = new Object[0];
/*  32 */   private static final Supplier[] EMPTY_SUPPLIER_ARRAY = new Supplier[0];
/*  33 */   private static final Map.Entry[] EMPTY_ENTRY_ARRAY = new Map.Entry[0];
/*     */ 
/*     */   
/*     */   @Nonnull
/*     */   public static <T> T[] emptyArray() {
/*  38 */     return (T[])EMPTY_OBJECT_ARRAY;
/*     */   }
/*     */ 
/*     */   
/*     */   @Nonnull
/*     */   public static <T> Supplier<T>[] emptySupplierArray() {
/*  44 */     return (Supplier<T>[])EMPTY_SUPPLIER_ARRAY;
/*     */   }
/*     */ 
/*     */   
/*     */   @Nonnull
/*     */   public static <K, V> Map.Entry<K, V>[] emptyEntryArray() {
/*  50 */     return (Map.Entry<K, V>[])EMPTY_ENTRY_ARRAY;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static int grow(int oldSize) {
/*  60 */     return (int)MathUtil.clamp(oldSize + (oldSize >> 1), 2L, 2147483639L);
/*     */   }
/*     */   
/*     */   public static <StartType, EndType> EndType[] copyAndMutate(@Nullable StartType[] array, @Nonnull Function<StartType, EndType> adapter, @Nonnull IntFunction<EndType[]> arrayProvider) {
/*  64 */     if (array == null) return null; 
/*  65 */     EndType[] endArray = arrayProvider.apply(array.length);
/*  66 */     for (int i = 0; i < endArray.length; i++) {
/*  67 */       endArray[i] = adapter.apply(array[i]);
/*     */     }
/*  69 */     return endArray;
/*     */   }
/*     */   
/*     */   @Nullable
/*     */   public static <T> T[] combine(@Nullable T[] a1, @Nullable T[] a2) {
/*  74 */     if (a1 == null || a1.length == 0) return a2; 
/*  75 */     if (a2 == null || a2.length == 0) return a1; 
/*  76 */     T[] newArray = Arrays.copyOf(a1, a1.length + a2.length);
/*  77 */     System.arraycopy(a2, 0, newArray, a1.length, a2.length);
/*  78 */     return newArray;
/*     */   }
/*     */   
/*     */   @Nonnull
/*     */   public static <T> T[] append(@Nullable T[] arr, @Nonnull T t) {
/*  83 */     if (arr == null) {
/*     */       
/*  85 */       T[] arrayOfT = (T[])Array.newInstance(t.getClass(), 1);
/*  86 */       arrayOfT[0] = t;
/*  87 */       return arrayOfT;
/*     */     } 
/*  89 */     T[] newArray = Arrays.copyOf(arr, arr.length + 1);
/*  90 */     newArray[arr.length] = t;
/*  91 */     return newArray;
/*     */   }
/*     */   
/*     */   @Nonnull
/*     */   public static <T> T[] remove(@Nonnull T[] arr, int index) {
/*  96 */     int newLength = arr.length - 1;
/*     */     
/*  98 */     T[] newArray = (T[])Array.newInstance(arr.getClass().getComponentType(), newLength);
/*  99 */     System.arraycopy(arr, 0, newArray, 0, index);
/* 100 */     if (index < newLength) System.arraycopy(arr, index + 1, newArray, index, newLength - index); 
/* 101 */     return newArray;
/*     */   }
/*     */   
/*     */   public static boolean startsWith(@Nonnull byte[] array, @Nonnull byte[] start) {
/* 105 */     if (start.length > array.length) return false; 
/* 106 */     for (int i = 0; i < start.length; i++) {
/* 107 */       if (array[i] != start[i]) {
/* 108 */         return false;
/*     */       }
/*     */     } 
/* 111 */     return true;
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
/*     */   public static <T> boolean equals(@Nullable T[] a, @Nullable T[] a2, @Nonnull UnaryBiPredicate<T> predicate) {
/* 131 */     if (a == a2) {
/* 132 */       return true;
/*     */     }
/* 134 */     if (a == null || a2 == null) {
/* 135 */       return false;
/*     */     }
/*     */     
/* 138 */     int length = a.length;
/* 139 */     if (a2.length != length) {
/* 140 */       return false;
/*     */     }
/*     */     
/* 143 */     for (int i = 0; i < length; ) {
/* 144 */       T o1 = a[i];
/* 145 */       T o2 = a2[i];
/* 146 */       if ((o1 == null) ? (o2 == null) : predicate.test(o1, o2)) { i++; continue; }
/* 147 */        return false;
/*     */     } 
/*     */ 
/*     */     
/* 151 */     return true;
/*     */   }
/*     */ 
/*     */   
/*     */   @Nonnull
/*     */   public static <T> T[][] split(@Nonnull T[] data, int size) {
/* 157 */     Class<? extends T[]> aClass = (Class)data.getClass();
/*     */     
/* 159 */     T[][] ret = (T[][])Array.newInstance(aClass.getComponentType(), new int[] { MathUtil.ceil(data.length / size), 0 });
/* 160 */     int start = 0;
/* 161 */     for (int i = 0; i < ret.length; i++) {
/* 162 */       ret[i] = Arrays.copyOfRange(data, start, Math.min(start + size, data.length));
/* 163 */       start += size;
/*     */     } 
/* 165 */     return ret;
/*     */   }
/*     */   
/*     */   public static byte[][] split(@Nonnull byte[] data, int size) {
/* 169 */     byte[][] ret = new byte[MathUtil.ceil(data.length / size)][];
/* 170 */     int start = 0;
/* 171 */     for (int i = 0; i < ret.length; i++) {
/* 172 */       ret[i] = Arrays.copyOfRange(data, start, Math.min(start + size, data.length));
/* 173 */       start += size;
/*     */     } 
/* 175 */     return ret;
/*     */   }
/*     */ 
/*     */   
/*     */   public static void shuffleArray(@Nonnull int[] ar, int from, int to, @Nonnull Random rnd) {
/* 180 */     Objects.checkFromToIndex(from, to, ar.length);
/* 181 */     for (int i = to - 1; i > from; i--) {
/* 182 */       int index = rnd.nextInt(i + 1 - from) + from;
/*     */       
/* 184 */       int a = ar[index];
/* 185 */       ar[index] = ar[i];
/* 186 */       ar[i] = a;
/*     */     } 
/*     */   }
/*     */   
/*     */   public static void shuffleArray(@Nonnull byte[] ar, int from, int to, @Nonnull Random rnd) {
/* 191 */     Objects.checkFromToIndex(from, to, ar.length);
/* 192 */     for (int i = to - 1; i > from; i--) {
/* 193 */       int index = rnd.nextInt(i + 1 - from) + from;
/*     */       
/* 195 */       byte a = ar[index];
/* 196 */       ar[index] = ar[i];
/* 197 */       ar[i] = a;
/*     */     } 
/*     */   }
/*     */   
/*     */   public static <T> boolean contains(@Nonnull T[] array, @Nullable T obj) {
/* 202 */     return (indexOf(array, obj) >= 0);
/*     */   }
/*     */   
/*     */   public static <T> boolean contains(@Nonnull T[] array, @Nullable T obj, int start, int end) {
/* 206 */     return (indexOf(array, obj, start, end) >= 0);
/*     */   }
/*     */   
/*     */   public static <T> int indexOf(@Nonnull T[] array, @Nullable T obj) {
/* 210 */     return indexOf(array, obj, 0, array.length);
/*     */   }
/*     */   
/*     */   public static <T> int indexOf(@Nonnull T[] array, @Nullable T obj, int start, int end) {
/* 214 */     if (obj == null) {
/* 215 */       for (int i = start; i < end; i++) {
/* 216 */         if (array[i] == null) return i; 
/*     */       } 
/*     */     } else {
/* 219 */       for (int i = start; i < end; i++) {
/* 220 */         if (obj.equals(array[i])) return i; 
/*     */       } 
/*     */     } 
/* 223 */     return -1;
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\commo\\util\ArrayUtil.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */