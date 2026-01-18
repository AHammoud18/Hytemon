/*      */ package it.unimi.dsi.fastutil.floats;
/*      */ 
/*      */ import it.unimi.dsi.fastutil.BigArrays;
/*      */ import it.unimi.dsi.fastutil.Hash;
/*      */ import it.unimi.dsi.fastutil.bytes.ByteBigArrays;
/*      */ import java.io.Serializable;
/*      */ import java.util.Arrays;
/*      */ import java.util.Random;
/*      */ import java.util.concurrent.ForkJoinPool;
/*      */ import java.util.concurrent.ForkJoinTask;
/*      */ import java.util.concurrent.RecursiveAction;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ public final class FloatBigArrays
/*      */ {
/*   70 */   public static final float[][] EMPTY_BIG_ARRAY = new float[0][];
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*   78 */   public static final float[][] DEFAULT_EMPTY_BIG_ARRAY = new float[0][];
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   @Deprecated
/*      */   public static float get(float[][] array, long index) {
/*   90 */     return array[BigArrays.segment(index)][BigArrays.displacement(index)];
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   @Deprecated
/*      */   public static void set(float[][] array, long index, float value) {
/*  103 */     array[BigArrays.segment(index)][BigArrays.displacement(index)] = value;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   @Deprecated
/*      */   public static void swap(float[][] array, long first, long second) {
/*  116 */     float t = array[BigArrays.segment(first)][BigArrays.displacement(first)];
/*  117 */     array[BigArrays.segment(first)][BigArrays.displacement(first)] = array[BigArrays.segment(second)][BigArrays.displacement(second)];
/*  118 */     array[BigArrays.segment(second)][BigArrays.displacement(second)] = t;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   @Deprecated
/*      */   public static void add(float[][] array, long index, float incr) {
/*  131 */     array[BigArrays.segment(index)][BigArrays.displacement(index)] = array[BigArrays.segment(index)][BigArrays.displacement(index)] + incr;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   @Deprecated
/*      */   public static void mul(float[][] array, long index, float factor) {
/*  144 */     array[BigArrays.segment(index)][BigArrays.displacement(index)] = array[BigArrays.segment(index)][BigArrays.displacement(index)] * factor;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   @Deprecated
/*      */   public static void incr(float[][] array, long index) {
/*  156 */     array[BigArrays.segment(index)][BigArrays.displacement(index)] = array[BigArrays.segment(index)][BigArrays.displacement(index)] + 1.0F;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   @Deprecated
/*      */   public static void decr(float[][] array, long index) {
/*  168 */     array[BigArrays.segment(index)][BigArrays.displacement(index)] = array[BigArrays.segment(index)][BigArrays.displacement(index)] - 1.0F;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   @Deprecated
/*      */   public static long length(float[][] array) {
/*  180 */     int length = array.length;
/*  181 */     return (length == 0) ? 0L : (BigArrays.start(length - 1) + (array[length - 1]).length);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   @Deprecated
/*      */   public static void copy(float[][] srcArray, long srcPos, float[][] destArray, long destPos, long length) {
/*  198 */     BigArrays.copy(srcArray, srcPos, destArray, destPos, length);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   @Deprecated
/*      */   public static void copyFromBig(float[][] srcArray, long srcPos, float[] destArray, int destPos, int length) {
/*  214 */     BigArrays.copyFromBig(srcArray, srcPos, destArray, destPos, length);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   @Deprecated
/*      */   public static void copyToBig(float[] srcArray, int srcPos, float[][] destArray, long destPos, long length) {
/*  230 */     BigArrays.copyToBig(srcArray, srcPos, destArray, destPos, length);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static float[][] newBigArray(long length) {
/*  240 */     if (length == 0L) return EMPTY_BIG_ARRAY; 
/*  241 */     BigArrays.ensureLength(length);
/*  242 */     int baseLength = (int)(length + 134217727L >>> 27L);
/*  243 */     float[][] base = new float[baseLength][];
/*  244 */     int residual = (int)(length & 0x7FFFFFFL);
/*  245 */     if (residual != 0)
/*  246 */     { for (int i = 0; i < baseLength - 1; ) { base[i] = new float[134217728]; i++; }
/*  247 */        base[baseLength - 1] = new float[residual]; }
/*  248 */     else { for (int i = 0; i < baseLength; ) { base[i] = new float[134217728]; i++; }  }
/*  249 */      return base;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   @Deprecated
/*      */   public static float[][] wrap(float[] array) {
/*  264 */     return BigArrays.wrap(array);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   @Deprecated
/*      */   public static float[][] ensureCapacity(float[][] array, long length) {
/*  287 */     return ensureCapacity(array, length, length(array));
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   @Deprecated
/*      */   public static float[][] forceCapacity(float[][] array, long length, long preserve) {
/*  308 */     return BigArrays.forceCapacity(array, length, preserve);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   @Deprecated
/*      */   public static float[][] ensureCapacity(float[][] array, long length, long preserve) {
/*  330 */     return (length > length(array)) ? forceCapacity(array, length, preserve) : array;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   @Deprecated
/*      */   public static float[][] grow(float[][] array, long length) {
/*  354 */     long oldLength = length(array);
/*  355 */     return (length > oldLength) ? grow(array, length, oldLength) : array;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   @Deprecated
/*      */   public static float[][] grow(float[][] array, long length, long preserve) {
/*  382 */     long oldLength = length(array);
/*  383 */     return (length > oldLength) ? ensureCapacity(array, Math.max(oldLength + (oldLength >> 1L), length), preserve) : array;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   @Deprecated
/*      */   public static float[][] trim(float[][] array, long length) {
/*  402 */     BigArrays.ensureLength(length);
/*  403 */     long oldLength = length(array);
/*  404 */     if (length >= oldLength) return array; 
/*  405 */     int baseLength = (int)(length + 134217727L >>> 27L);
/*  406 */     float[][] base = Arrays.<float[]>copyOf(array, baseLength);
/*  407 */     int residual = (int)(length & 0x7FFFFFFL);
/*  408 */     if (residual != 0) base[baseLength - 1] = FloatArrays.trim(base[baseLength - 1], residual); 
/*  409 */     return base;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   @Deprecated
/*      */   public static float[][] setLength(float[][] array, long length) {
/*  430 */     return BigArrays.setLength(array, length);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   @Deprecated
/*      */   public static float[][] copy(float[][] array, long offset, long length) {
/*  445 */     return BigArrays.copy(array, offset, length);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   @Deprecated
/*      */   public static float[][] copy(float[][] array) {
/*  457 */     return BigArrays.copy(array);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   @Deprecated
/*      */   public static void fill(float[][] array, float value) {
/*  473 */     for (int i = array.length; i-- != 0; Arrays.fill(array[i], value));
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   @Deprecated
/*      */   public static void fill(float[][] array, long from, long to, float value) {
/*  491 */     BigArrays.fill(array, from, to, value);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   @Deprecated
/*      */   public static boolean equals(float[][] a1, float[][] a2) {
/*  508 */     return BigArrays.equals(a1, a2);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   @Deprecated
/*      */   public static String toString(float[][] a) {
/*  520 */     return BigArrays.toString(a);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   @Deprecated
/*      */   public static void ensureFromTo(float[][] a, long from, long to) {
/*  540 */     BigArrays.ensureFromTo(length(a), from, to);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   @Deprecated
/*      */   public static void ensureOffsetLength(float[][] a, long offset, long length) {
/*  559 */     BigArrays.ensureOffsetLength(length(a), offset, length);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   @Deprecated
/*      */   public static void ensureSameLength(float[][] a, float[][] b) {
/*  572 */     if (length(a) != length(b)) throw new IllegalArgumentException("Array size mismatch: " + length(a) + " != " + length(b)); 
/*      */   }
/*      */   
/*      */   private static final class BigArrayHashStrategy implements Hash.Strategy<float[][]>, Serializable {
/*      */     private static final long serialVersionUID = -7046029254386353129L;
/*      */     
/*      */     private BigArrayHashStrategy() {}
/*      */     
/*      */     public int hashCode(float[][] o) {
/*  581 */       return Arrays.deepHashCode((Object[])o);
/*      */     }
/*      */ 
/*      */     
/*      */     public boolean equals(float[][] a, float[][] b) {
/*  586 */       return FloatBigArrays.equals(a, b);
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*  599 */   public static final Hash.Strategy HASH_STRATEGY = new BigArrayHashStrategy(); private static final int QUICKSORT_NO_REC = 7; private static final int PARALLEL_QUICKSORT_NO_FORK = 8192; private static final int MEDIUM = 40;
/*      */   private static final int DIGIT_BITS = 8;
/*      */   private static final int DIGIT_MASK = 255;
/*      */   private static final int DIGITS_PER_ELEMENT = 4;
/*      */   private static final int RADIXSORT_NO_REC = 1024;
/*      */   
/*      */   private static ForkJoinPool getPool() {
/*  606 */     ForkJoinPool current = ForkJoinTask.getPool();
/*  607 */     return (current == null) ? ForkJoinPool.commonPool() : current;
/*      */   }
/*      */   
/*      */   private static void swap(float[][] x, long a, long b, long n) {
/*  611 */     for (int i = 0; i < n; ) { BigArrays.swap(x, a, b); i++; a++; b++; }
/*      */   
/*      */   }
/*      */   private static long med3(float[][] x, long a, long b, long c, FloatComparator comp) {
/*  615 */     int ab = comp.compare(BigArrays.get(x, a), BigArrays.get(x, b));
/*  616 */     int ac = comp.compare(BigArrays.get(x, a), BigArrays.get(x, c));
/*  617 */     int bc = comp.compare(BigArrays.get(x, b), BigArrays.get(x, c));
/*  618 */     return (ab < 0) ? ((bc < 0) ? b : ((ac < 0) ? c : a)) : ((bc > 0) ? b : ((ac > 0) ? c : a));
/*      */   }
/*      */   private static void selectionSort(float[][] a, long from, long to, FloatComparator comp) {
/*      */     long i;
/*  622 */     for (i = from; i < to - 1L; i++) {
/*  623 */       long m = i; long j;
/*  624 */       for (j = i + 1L; j < to; ) { if (comp.compare(BigArrays.get(a, j), BigArrays.get(a, m)) < 0) m = j;  j++; }
/*  625 */        if (m != i) BigArrays.swap(a, i, m);
/*      */     
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static void quickSort(float[][] x, long from, long to, FloatComparator comp) {
/*  644 */     long len = to - from;
/*      */     
/*  646 */     if (len < 7L) {
/*  647 */       selectionSort(x, from, to, comp);
/*      */       
/*      */       return;
/*      */     } 
/*  651 */     long m = from + len / 2L;
/*  652 */     if (len > 7L) {
/*  653 */       long l = from;
/*  654 */       long n = to - 1L;
/*  655 */       if (len > 40L) {
/*  656 */         long s = len / 8L;
/*  657 */         l = med3(x, l, l + s, l + 2L * s, comp);
/*  658 */         m = med3(x, m - s, m, m + s, comp);
/*  659 */         n = med3(x, n - 2L * s, n - s, n, comp);
/*      */       } 
/*  661 */       m = med3(x, l, m, n, comp);
/*      */     } 
/*  663 */     float v = BigArrays.get(x, m);
/*      */     
/*  665 */     long a = from, b = a, c = to - 1L, d = c;
/*      */     
/*      */     int comparison;
/*  668 */     while (b <= c && (comparison = comp.compare(BigArrays.get(x, b), v)) <= 0) {
/*  669 */       if (comparison == 0) BigArrays.swap(x, a++, b); 
/*  670 */       b++;
/*      */     } 
/*  672 */     while (c >= b && (comparison = comp.compare(BigArrays.get(x, c), v)) >= 0) {
/*  673 */       if (comparison == 0) BigArrays.swap(x, c, d--); 
/*  674 */       c--;
/*      */     } 
/*  676 */     if (b > c) {
/*      */ 
/*      */ 
/*      */       
/*  680 */       long n = to;
/*  681 */       long s = Math.min(a - from, b - a);
/*  682 */       swap(x, from, b - s, s);
/*  683 */       s = Math.min(d - c, n - d - 1L);
/*  684 */       swap(x, b, n - s, s);
/*      */       
/*  686 */       if ((s = b - a) > 1L) quickSort(x, from, from + s, comp); 
/*  687 */       if ((s = d - c) > 1L) quickSort(x, n - s, n, comp); 
/*      */       return;
/*      */     } 
/*      */     BigArrays.swap(x, b++, c--); } private static long med3(float[][] x, long a, long b, long c) {
/*  691 */     int ab = Float.compare(BigArrays.get(x, a), BigArrays.get(x, b));
/*  692 */     int ac = Float.compare(BigArrays.get(x, a), BigArrays.get(x, c));
/*  693 */     int bc = Float.compare(BigArrays.get(x, b), BigArrays.get(x, c));
/*  694 */     return (ab < 0) ? ((bc < 0) ? b : ((ac < 0) ? c : a)) : ((bc > 0) ? b : ((ac > 0) ? c : a));
/*      */   }
/*      */   private static void selectionSort(float[][] a, long from, long to) {
/*      */     long i;
/*  698 */     for (i = from; i < to - 1L; i++) {
/*  699 */       long m = i; long j;
/*  700 */       for (j = i + 1L; j < to; ) { if (Float.compare(BigArrays.get(a, j), BigArrays.get(a, m)) < 0) m = j;  j++; }
/*  701 */        if (m != i) BigArrays.swap(a, i, m);
/*      */     
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static void quickSort(float[][] x, FloatComparator comp) {
/*  719 */     quickSort(x, 0L, BigArrays.length(x), comp);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static void quickSort(float[][] x, long from, long to) {
/*  736 */     long len = to - from;
/*      */     
/*  738 */     if (len < 7L) {
/*  739 */       selectionSort(x, from, to);
/*      */       
/*      */       return;
/*      */     } 
/*  743 */     long m = from + len / 2L;
/*  744 */     if (len > 7L) {
/*  745 */       long l = from;
/*  746 */       long n = to - 1L;
/*  747 */       if (len > 40L) {
/*  748 */         long s = len / 8L;
/*  749 */         l = med3(x, l, l + s, l + 2L * s);
/*  750 */         m = med3(x, m - s, m, m + s);
/*  751 */         n = med3(x, n - 2L * s, n - s, n);
/*      */       } 
/*  753 */       m = med3(x, l, m, n);
/*      */     } 
/*  755 */     float v = BigArrays.get(x, m);
/*      */     
/*  757 */     long a = from, b = a, c = to - 1L, d = c;
/*      */     
/*      */     int comparison;
/*  760 */     while (b <= c && (comparison = Float.compare(BigArrays.get(x, b), v)) <= 0) {
/*  761 */       if (comparison == 0) BigArrays.swap(x, a++, b); 
/*  762 */       b++;
/*      */     } 
/*  764 */     while (c >= b && (comparison = Float.compare(BigArrays.get(x, c), v)) >= 0) {
/*  765 */       if (comparison == 0) BigArrays.swap(x, c, d--); 
/*  766 */       c--;
/*      */     } 
/*  768 */     if (b > c) {
/*      */ 
/*      */ 
/*      */       
/*  772 */       long n = to;
/*  773 */       long s = Math.min(a - from, b - a);
/*  774 */       swap(x, from, b - s, s);
/*  775 */       s = Math.min(d - c, n - d - 1L);
/*  776 */       swap(x, b, n - s, s);
/*      */       
/*  778 */       if ((s = b - a) > 1L) quickSort(x, from, from + s); 
/*  779 */       if ((s = d - c) > 1L) quickSort(x, n - s, n);
/*      */       
/*      */       return;
/*      */     } 
/*      */     BigArrays.swap(x, b++, c--);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static void quickSort(float[][] x) {
/*  793 */     quickSort(x, 0L, BigArrays.length(x));
/*      */   }
/*      */   
/*      */   protected static class ForkJoinQuickSort extends RecursiveAction {
/*      */     private static final long serialVersionUID = 1L;
/*      */     private final long from;
/*      */     private final long to;
/*      */     private final float[][] x;
/*      */     
/*      */     public ForkJoinQuickSort(float[][] x, long from, long to) {
/*  803 */       this.from = from;
/*  804 */       this.to = to;
/*  805 */       this.x = x;
/*      */     }
/*      */ 
/*      */ 
/*      */     
/*      */     protected void compute() {
/*  811 */       float[][] x = this.x;
/*  812 */       long len = this.to - this.from;
/*  813 */       if (len < 8192L) {
/*  814 */         FloatBigArrays.quickSort(x, this.from, this.to);
/*      */         
/*      */         return;
/*      */       } 
/*  818 */       long m = this.from + len / 2L;
/*  819 */       long l = this.from;
/*  820 */       long n = this.to - 1L;
/*  821 */       long s = len / 8L;
/*  822 */       l = FloatBigArrays.med3(x, l, l + s, l + 2L * s);
/*  823 */       m = FloatBigArrays.med3(x, m - s, m, m + s);
/*  824 */       n = FloatBigArrays.med3(x, n - 2L * s, n - s, n);
/*  825 */       m = FloatBigArrays.med3(x, l, m, n);
/*  826 */       float v = BigArrays.get(x, m);
/*      */       
/*  828 */       long a = this.from, b = a, c = this.to - 1L, d = c;
/*      */       
/*      */       int comparison;
/*  831 */       while (b <= c && (comparison = Float.compare(BigArrays.get(x, b), v)) <= 0) {
/*  832 */         if (comparison == 0) BigArrays.swap(x, a++, b); 
/*  833 */         b++;
/*      */       } 
/*  835 */       while (c >= b && (comparison = Float.compare(BigArrays.get(x, c), v)) >= 0) {
/*  836 */         if (comparison == 0) BigArrays.swap(x, c, d--); 
/*  837 */         c--;
/*      */       } 
/*  839 */       if (b > c) {
/*      */ 
/*      */ 
/*      */ 
/*      */         
/*  844 */         s = Math.min(a - this.from, b - a);
/*  845 */         FloatBigArrays.swap(x, this.from, b - s, s);
/*  846 */         s = Math.min(d - c, this.to - d - 1L);
/*  847 */         FloatBigArrays.swap(x, b, this.to - s, s);
/*      */         
/*  849 */         s = b - a;
/*  850 */         long t = d - c;
/*  851 */         if (s > 1L && t > 1L) { invokeAll(new ForkJoinQuickSort(x, this.from, this.from + s), new ForkJoinQuickSort(x, this.to - t, this.to)); }
/*  852 */         else if (s > 1L) { invokeAll((ForkJoinTask<?>[])new ForkJoinTask[] { new ForkJoinQuickSort(x, this.from, this.from + s) }); }
/*  853 */         else { invokeAll((ForkJoinTask<?>[])new ForkJoinTask[] { new ForkJoinQuickSort(x, this.to - t, this.to) }); }
/*      */         
/*      */         return;
/*      */       } 
/*      */       BigArrays.swap(x, b++, c--);
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static void parallelQuickSort(float[][] x, long from, long to) {
/*  871 */     ForkJoinPool pool = getPool();
/*  872 */     if (to - from < 8192L || pool.getParallelism() == 1) { quickSort(x, from, to); }
/*      */     else
/*  874 */     { pool.invoke(new ForkJoinQuickSort(x, from, to)); }
/*      */   
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static void parallelQuickSort(float[][] x) {
/*  889 */     parallelQuickSort(x, 0L, BigArrays.length(x));
/*      */   }
/*      */   
/*      */   protected static class ForkJoinQuickSortComp extends RecursiveAction {
/*      */     private static final long serialVersionUID = 1L;
/*      */     private final long from;
/*      */     private final long to;
/*      */     private final float[][] x;
/*      */     private final FloatComparator comp;
/*      */     
/*      */     public ForkJoinQuickSortComp(float[][] x, long from, long to, FloatComparator comp) {
/*  900 */       this.from = from;
/*  901 */       this.to = to;
/*  902 */       this.x = x;
/*  903 */       this.comp = comp;
/*      */     }
/*      */ 
/*      */     
/*      */     protected void compute() {
/*  908 */       float[][] x = this.x;
/*  909 */       long len = this.to - this.from;
/*  910 */       if (len < 8192L) {
/*  911 */         FloatBigArrays.quickSort(x, this.from, this.to, this.comp);
/*      */         
/*      */         return;
/*      */       } 
/*  915 */       long m = this.from + len / 2L;
/*  916 */       long l = this.from;
/*  917 */       long n = this.to - 1L;
/*  918 */       long s = len / 8L;
/*  919 */       l = FloatBigArrays.med3(x, l, l + s, l + 2L * s, this.comp);
/*  920 */       m = FloatBigArrays.med3(x, m - s, m, m + s, this.comp);
/*  921 */       n = FloatBigArrays.med3(x, n - 2L * s, n - s, n, this.comp);
/*  922 */       m = FloatBigArrays.med3(x, l, m, n, this.comp);
/*  923 */       float v = BigArrays.get(x, m);
/*      */       
/*  925 */       long a = this.from, b = a, c = this.to - 1L, d = c;
/*      */       
/*      */       int comparison;
/*  928 */       while (b <= c && (comparison = this.comp.compare(BigArrays.get(x, b), v)) <= 0) {
/*  929 */         if (comparison == 0) BigArrays.swap(x, a++, b); 
/*  930 */         b++;
/*      */       } 
/*  932 */       while (c >= b && (comparison = this.comp.compare(BigArrays.get(x, c), v)) >= 0) {
/*  933 */         if (comparison == 0) BigArrays.swap(x, c, d--); 
/*  934 */         c--;
/*      */       } 
/*  936 */       if (b > c) {
/*      */ 
/*      */ 
/*      */ 
/*      */         
/*  941 */         s = Math.min(a - this.from, b - a);
/*  942 */         FloatBigArrays.swap(x, this.from, b - s, s);
/*  943 */         s = Math.min(d - c, this.to - d - 1L);
/*  944 */         FloatBigArrays.swap(x, b, this.to - s, s);
/*      */         
/*  946 */         s = b - a;
/*  947 */         long t = d - c;
/*  948 */         if (s > 1L && t > 1L) { invokeAll(new ForkJoinQuickSortComp(x, this.from, this.from + s, this.comp), new ForkJoinQuickSortComp(x, this.to - t, this.to, this.comp)); }
/*  949 */         else if (s > 1L) { invokeAll((ForkJoinTask<?>[])new ForkJoinTask[] { new ForkJoinQuickSortComp(x, this.from, this.from + s, this.comp) }); }
/*  950 */         else { invokeAll((ForkJoinTask<?>[])new ForkJoinTask[] { new ForkJoinQuickSortComp(x, this.to - t, this.to, this.comp) }); }
/*      */         
/*      */         return;
/*      */       } 
/*      */       BigArrays.swap(x, b++, c--);
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static void parallelQuickSort(float[][] x, long from, long to, FloatComparator comp) {
/*  969 */     ForkJoinPool pool = getPool();
/*  970 */     if (to - from < 8192L || pool.getParallelism() == 1) { quickSort(x, from, to, comp); }
/*      */     else
/*  972 */     { pool.invoke(new ForkJoinQuickSortComp(x, from, to, comp)); }
/*      */   
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static void parallelQuickSort(float[][] x, FloatComparator comp) {
/*  989 */     parallelQuickSort(x, 0L, BigArrays.length(x), comp);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static long binarySearch(float[][] a, long from, long to, float key) {
/* 1013 */     to--;
/* 1014 */     while (from <= to) {
/* 1015 */       long mid = from + to >>> 1L;
/* 1016 */       float midVal = BigArrays.get(a, mid);
/* 1017 */       if (midVal < key) { from = mid + 1L; continue; }
/* 1018 */        if (midVal > key) { to = mid - 1L; continue; }
/* 1019 */        return mid;
/*      */     } 
/* 1021 */     return -(from + 1L);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static long binarySearch(float[][] a, float key) {
/* 1041 */     return binarySearch(a, 0L, BigArrays.length(a), key);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static long binarySearch(float[][] a, long from, long to, float key, FloatComparator c) {
/* 1065 */     to--;
/* 1066 */     while (from <= to) {
/* 1067 */       long mid = from + to >>> 1L;
/* 1068 */       float midVal = BigArrays.get(a, mid);
/* 1069 */       int cmp = c.compare(midVal, key);
/* 1070 */       if (cmp < 0) { from = mid + 1L; continue; }
/* 1071 */        if (cmp > 0) { to = mid - 1L; continue; }
/* 1072 */        return mid;
/*      */     } 
/* 1074 */     return -(from + 1L);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static long binarySearch(float[][] a, float key, FloatComparator c) {
/* 1095 */     return binarySearch(a, 0L, BigArrays.length(a), key, c);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private static final int fixFloat(float f) {
/* 1110 */     int i = Float.floatToRawIntBits(f);
/* 1111 */     return (i >= 0) ? i : (i ^ Integer.MAX_VALUE);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static void radixSort(float[][] a) {
/* 1132 */     radixSort(a, 0L, BigArrays.length(a));
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static void radixSort(float[][] a, long from, long to) {
/* 1155 */     int maxLevel = 3;
/* 1156 */     int stackSize = 766;
/* 1157 */     long[] offsetStack = new long[766];
/* 1158 */     int offsetPos = 0;
/* 1159 */     long[] lengthStack = new long[766];
/* 1160 */     int lengthPos = 0;
/* 1161 */     int[] levelStack = new int[766];
/* 1162 */     int levelPos = 0;
/* 1163 */     offsetStack[offsetPos++] = from;
/* 1164 */     lengthStack[lengthPos++] = to - from;
/* 1165 */     levelStack[levelPos++] = 0;
/* 1166 */     long[] count = new long[256];
/* 1167 */     long[] pos = new long[256];
/* 1168 */     byte[][] digit = ByteBigArrays.newBigArray(to - from);
/* 1169 */     while (offsetPos > 0) {
/* 1170 */       long first = offsetStack[--offsetPos];
/* 1171 */       long length = lengthStack[--lengthPos];
/* 1172 */       int level = levelStack[--levelPos];
/* 1173 */       int signMask = (level % 4 == 0) ? 128 : 0;
/* 1174 */       if (length < 40L) {
/* 1175 */         selectionSort(a, first, first + length);
/*      */         continue;
/*      */       } 
/* 1178 */       int shift = (3 - level % 4) * 8;
/*      */ 
/*      */       
/*      */       long i;
/*      */       
/* 1183 */       for (i = length; i-- != 0L; BigArrays.set(digit, i, (byte)(fixFloat(BigArrays.get(a, first + i)) >>> shift & 0xFF ^ signMask)));
/* 1184 */       for (i = length; i-- != 0L; count[BigArrays.get(digit, i) & 0xFF] = count[BigArrays.get(digit, i) & 0xFF] + 1L);
/*      */       
/* 1186 */       int lastUsed = -1;
/* 1187 */       long p = 0L;
/* 1188 */       for (int j = 0; j < 256; j++) {
/* 1189 */         if (count[j] != 0L) {
/* 1190 */           lastUsed = j;
/* 1191 */           if (level < 3 && count[j] > 1L) {
/*      */ 
/*      */             
/* 1194 */             offsetStack[offsetPos++] = p + first;
/* 1195 */             lengthStack[lengthPos++] = count[j];
/* 1196 */             levelStack[levelPos++] = level + 1;
/*      */           } 
/*      */         } 
/* 1199 */         pos[j] = p += count[j];
/*      */       } 
/*      */       
/* 1202 */       long end = length - count[lastUsed];
/* 1203 */       count[lastUsed] = 0L;
/*      */       
/* 1205 */       int c = -1;
/* 1206 */       for (long l1 = 0L; l1 < end; l1 += count[c], count[c] = 0L) {
/* 1207 */         float t = BigArrays.get(a, l1 + first);
/* 1208 */         c = BigArrays.get(digit, l1) & 0xFF; long d;
/* 1209 */         for (pos[c] = pos[c] - 1L; (d = pos[c] - 1L) > l1; ) {
/* 1210 */           float z = t;
/* 1211 */           int zz = c;
/* 1212 */           t = BigArrays.get(a, d + first);
/* 1213 */           c = BigArrays.get(digit, d) & 0xFF;
/* 1214 */           BigArrays.set(a, d + first, z);
/* 1215 */           BigArrays.set(digit, d, (byte)zz);
/*      */         } 
/* 1217 */         BigArrays.set(a, l1 + first, t);
/*      */       } 
/*      */     } 
/*      */   }
/*      */   private static void selectionSort(float[][] a, float[][] b, long from, long to) {
/*      */     long i;
/* 1223 */     for (i = from; i < to - 1L; i++) {
/* 1224 */       long m = i; long j;
/* 1225 */       for (j = i + 1L; j < to; ) { if (Float.compare(BigArrays.get(a, j), BigArrays.get(a, m)) < 0 || (Float.compare(BigArrays.get(a, j), BigArrays.get(a, m)) == 0 && Float.compare(BigArrays.get(b, j), BigArrays.get(b, m)) < 0)) m = j;  j++; }
/* 1226 */        if (m != i) {
/* 1227 */         float t = BigArrays.get(a, i);
/* 1228 */         BigArrays.set(a, i, BigArrays.get(a, m));
/* 1229 */         BigArrays.set(a, m, t);
/* 1230 */         t = BigArrays.get(b, i);
/* 1231 */         BigArrays.set(b, i, BigArrays.get(b, m));
/* 1232 */         BigArrays.set(b, m, t);
/*      */       } 
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static void radixSort(float[][] a, float[][] b) {
/* 1261 */     radixSort(a, b, 0L, BigArrays.length(a));
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static void radixSort(float[][] a, float[][] b, long from, long to) {
/* 1291 */     int layers = 2;
/* 1292 */     if (BigArrays.length(a) != BigArrays.length(b)) throw new IllegalArgumentException("Array size mismatch."); 
/* 1293 */     int maxLevel = 7;
/* 1294 */     int stackSize = 1786;
/* 1295 */     long[] offsetStack = new long[1786];
/* 1296 */     int offsetPos = 0;
/* 1297 */     long[] lengthStack = new long[1786];
/* 1298 */     int lengthPos = 0;
/* 1299 */     int[] levelStack = new int[1786];
/* 1300 */     int levelPos = 0;
/* 1301 */     offsetStack[offsetPos++] = from;
/* 1302 */     lengthStack[lengthPos++] = to - from;
/* 1303 */     levelStack[levelPos++] = 0;
/* 1304 */     long[] count = new long[256];
/* 1305 */     long[] pos = new long[256];
/* 1306 */     byte[][] digit = ByteBigArrays.newBigArray(to - from);
/* 1307 */     while (offsetPos > 0) {
/* 1308 */       long first = offsetStack[--offsetPos];
/* 1309 */       long length = lengthStack[--lengthPos];
/* 1310 */       int level = levelStack[--levelPos];
/* 1311 */       int signMask = (level % 4 == 0) ? 128 : 0;
/* 1312 */       if (length < 40L) {
/* 1313 */         selectionSort(a, b, first, first + length);
/*      */         continue;
/*      */       } 
/* 1316 */       float[][] k = (level < 4) ? a : b;
/* 1317 */       int shift = (3 - level % 4) * 8;
/*      */ 
/*      */       
/*      */       long i;
/*      */       
/* 1322 */       for (i = length; i-- != 0L; BigArrays.set(digit, i, (byte)(fixFloat(BigArrays.get(k, first + i)) >>> shift & 0xFF ^ signMask)));
/* 1323 */       for (i = length; i-- != 0L; count[BigArrays.get(digit, i) & 0xFF] = count[BigArrays.get(digit, i) & 0xFF] + 1L);
/*      */       
/* 1325 */       int lastUsed = -1;
/* 1326 */       long p = 0L;
/* 1327 */       for (int j = 0; j < 256; j++) {
/* 1328 */         if (count[j] != 0L) {
/* 1329 */           lastUsed = j;
/* 1330 */           if (level < 7 && count[j] > 1L) {
/* 1331 */             offsetStack[offsetPos++] = p + first;
/* 1332 */             lengthStack[lengthPos++] = count[j];
/* 1333 */             levelStack[levelPos++] = level + 1;
/*      */           } 
/*      */         } 
/* 1336 */         pos[j] = p += count[j];
/*      */       } 
/*      */       
/* 1339 */       long end = length - count[lastUsed];
/* 1340 */       count[lastUsed] = 0L;
/*      */       
/* 1342 */       int c = -1;
/* 1343 */       for (long l1 = 0L; l1 < end; l1 += count[c], count[c] = 0L) {
/* 1344 */         float t = BigArrays.get(a, l1 + first);
/* 1345 */         float u = BigArrays.get(b, l1 + first);
/* 1346 */         c = BigArrays.get(digit, l1) & 0xFF; long d;
/* 1347 */         for (pos[c] = pos[c] - 1L; (d = pos[c] - 1L) > l1; ) {
/* 1348 */           float z = t;
/* 1349 */           int zz = c;
/* 1350 */           t = BigArrays.get(a, d + first);
/* 1351 */           BigArrays.set(a, d + first, z);
/* 1352 */           z = u;
/* 1353 */           u = BigArrays.get(b, d + first);
/* 1354 */           BigArrays.set(b, d + first, z);
/* 1355 */           c = BigArrays.get(digit, d) & 0xFF;
/* 1356 */           BigArrays.set(digit, d, (byte)zz);
/*      */         } 
/* 1358 */         BigArrays.set(a, l1 + first, t);
/* 1359 */         BigArrays.set(b, l1 + first, u);
/*      */       } 
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   private static void insertionSortIndirect(long[][] perm, float[][] a, float[][] b, long from, long to) {
/* 1367 */     long i = from; while (true) { long t, j, u; if (++i < to)
/* 1368 */       { t = BigArrays.get(perm, i);
/* 1369 */         j = i;
/* 1370 */         u = BigArrays.get(perm, j - 1L); } else { break; }  if (Float.compare(BigArrays.get(a, t), BigArrays.get(a, u)) < 0 || (Float.compare(BigArrays.get(a, t), BigArrays.get(a, u)) == 0 && Float.compare(BigArrays.get(b, t), BigArrays.get(b, u)) < 0));
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/* 1377 */       BigArrays.set(perm, j, t); }
/*      */   
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static void radixSortIndirect(long[][] perm, float[][] a, float[][] b, boolean stable) {
/* 1404 */     ensureSameLength(a, b);
/* 1405 */     radixSortIndirect(perm, a, b, 0L, BigArrays.length(a), stable);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static void radixSortIndirect(long[][] perm, float[][] a, float[][] b, long from, long to, boolean stable) {
/*      */     // Byte code:
/*      */     //   0: lload #5
/*      */     //   2: lload_3
/*      */     //   3: lsub
/*      */     //   4: ldc2_w 1024
/*      */     //   7: lcmp
/*      */     //   8: ifge -> 21
/*      */     //   11: aload_0
/*      */     //   12: aload_1
/*      */     //   13: aload_2
/*      */     //   14: lload_3
/*      */     //   15: lload #5
/*      */     //   17: invokestatic insertionSortIndirect : ([[J[[F[[FJJ)V
/*      */     //   20: return
/*      */     //   21: iconst_2
/*      */     //   22: istore #8
/*      */     //   24: bipush #7
/*      */     //   26: istore #9
/*      */     //   28: sipush #1786
/*      */     //   31: istore #10
/*      */     //   33: iconst_0
/*      */     //   34: istore #11
/*      */     //   36: sipush #1786
/*      */     //   39: newarray long
/*      */     //   41: astore #12
/*      */     //   43: sipush #1786
/*      */     //   46: newarray long
/*      */     //   48: astore #13
/*      */     //   50: sipush #1786
/*      */     //   53: newarray int
/*      */     //   55: astore #14
/*      */     //   57: aload #12
/*      */     //   59: iload #11
/*      */     //   61: lload_3
/*      */     //   62: lastore
/*      */     //   63: aload #13
/*      */     //   65: iload #11
/*      */     //   67: lload #5
/*      */     //   69: lload_3
/*      */     //   70: lsub
/*      */     //   71: lastore
/*      */     //   72: aload #14
/*      */     //   74: iload #11
/*      */     //   76: iinc #11, 1
/*      */     //   79: iconst_0
/*      */     //   80: iastore
/*      */     //   81: sipush #256
/*      */     //   84: newarray long
/*      */     //   86: astore #15
/*      */     //   88: sipush #256
/*      */     //   91: newarray long
/*      */     //   93: astore #16
/*      */     //   95: iload #7
/*      */     //   97: ifeq -> 110
/*      */     //   100: aload_0
/*      */     //   101: invokestatic length : ([[J)J
/*      */     //   104: invokestatic newBigArray : (J)[[J
/*      */     //   107: goto -> 111
/*      */     //   110: aconst_null
/*      */     //   111: astore #17
/*      */     //   113: iload #11
/*      */     //   115: ifle -> 735
/*      */     //   118: aload #12
/*      */     //   120: iinc #11, -1
/*      */     //   123: iload #11
/*      */     //   125: laload
/*      */     //   126: lstore #18
/*      */     //   128: aload #13
/*      */     //   130: iload #11
/*      */     //   132: laload
/*      */     //   133: lstore #20
/*      */     //   135: aload #14
/*      */     //   137: iload #11
/*      */     //   139: iaload
/*      */     //   140: istore #22
/*      */     //   142: iload #22
/*      */     //   144: iconst_4
/*      */     //   145: irem
/*      */     //   146: ifne -> 155
/*      */     //   149: sipush #128
/*      */     //   152: goto -> 156
/*      */     //   155: iconst_0
/*      */     //   156: istore #23
/*      */     //   158: iload #22
/*      */     //   160: iconst_4
/*      */     //   161: if_icmpge -> 168
/*      */     //   164: aload_1
/*      */     //   165: goto -> 169
/*      */     //   168: aload_2
/*      */     //   169: astore #24
/*      */     //   171: iconst_3
/*      */     //   172: iload #22
/*      */     //   174: iconst_4
/*      */     //   175: irem
/*      */     //   176: isub
/*      */     //   177: bipush #8
/*      */     //   179: imul
/*      */     //   180: istore #25
/*      */     //   182: lload #18
/*      */     //   184: lload #20
/*      */     //   186: ladd
/*      */     //   187: lstore #26
/*      */     //   189: lload #26
/*      */     //   191: dup2
/*      */     //   192: lconst_1
/*      */     //   193: lsub
/*      */     //   194: lstore #26
/*      */     //   196: lload #18
/*      */     //   198: lcmp
/*      */     //   199: ifeq -> 236
/*      */     //   202: aload #15
/*      */     //   204: aload #24
/*      */     //   206: aload_0
/*      */     //   207: lload #26
/*      */     //   209: invokestatic get : ([[JJ)J
/*      */     //   212: invokestatic get : ([[FJ)F
/*      */     //   215: invokestatic fixFloat : (F)I
/*      */     //   218: iload #25
/*      */     //   220: iushr
/*      */     //   221: sipush #255
/*      */     //   224: iand
/*      */     //   225: iload #23
/*      */     //   227: ixor
/*      */     //   228: dup2
/*      */     //   229: laload
/*      */     //   230: lconst_1
/*      */     //   231: ladd
/*      */     //   232: lastore
/*      */     //   233: goto -> 189
/*      */     //   236: iconst_m1
/*      */     //   237: istore #26
/*      */     //   239: iload #7
/*      */     //   241: ifeq -> 248
/*      */     //   244: lconst_0
/*      */     //   245: goto -> 250
/*      */     //   248: lload #18
/*      */     //   250: lstore #27
/*      */     //   252: iconst_0
/*      */     //   253: istore #29
/*      */     //   255: iload #29
/*      */     //   257: sipush #256
/*      */     //   260: if_icmpge -> 299
/*      */     //   263: aload #15
/*      */     //   265: iload #29
/*      */     //   267: laload
/*      */     //   268: lconst_0
/*      */     //   269: lcmp
/*      */     //   270: ifeq -> 277
/*      */     //   273: iload #29
/*      */     //   275: istore #26
/*      */     //   277: aload #16
/*      */     //   279: iload #29
/*      */     //   281: lload #27
/*      */     //   283: aload #15
/*      */     //   285: iload #29
/*      */     //   287: laload
/*      */     //   288: ladd
/*      */     //   289: dup2
/*      */     //   290: lstore #27
/*      */     //   292: lastore
/*      */     //   293: iinc #29, 1
/*      */     //   296: goto -> 255
/*      */     //   299: iload #7
/*      */     //   301: ifeq -> 498
/*      */     //   304: lload #18
/*      */     //   306: lload #20
/*      */     //   308: ladd
/*      */     //   309: lstore #29
/*      */     //   311: lload #29
/*      */     //   313: dup2
/*      */     //   314: lconst_1
/*      */     //   315: lsub
/*      */     //   316: lstore #29
/*      */     //   318: lload #18
/*      */     //   320: lcmp
/*      */     //   321: ifeq -> 370
/*      */     //   324: aload #17
/*      */     //   326: aload #16
/*      */     //   328: aload #24
/*      */     //   330: aload_0
/*      */     //   331: lload #29
/*      */     //   333: invokestatic get : ([[JJ)J
/*      */     //   336: invokestatic get : ([[FJ)F
/*      */     //   339: invokestatic fixFloat : (F)I
/*      */     //   342: iload #25
/*      */     //   344: iushr
/*      */     //   345: sipush #255
/*      */     //   348: iand
/*      */     //   349: iload #23
/*      */     //   351: ixor
/*      */     //   352: dup2
/*      */     //   353: laload
/*      */     //   354: lconst_1
/*      */     //   355: lsub
/*      */     //   356: dup2_x2
/*      */     //   357: lastore
/*      */     //   358: aload_0
/*      */     //   359: lload #29
/*      */     //   361: invokestatic get : ([[JJ)J
/*      */     //   364: invokestatic set : ([[JJJ)V
/*      */     //   367: goto -> 311
/*      */     //   370: aload #17
/*      */     //   372: lconst_0
/*      */     //   373: aload_0
/*      */     //   374: lload #18
/*      */     //   376: lload #20
/*      */     //   378: invokestatic copy : ([[JJ[[JJJ)V
/*      */     //   381: lload #18
/*      */     //   383: lstore #27
/*      */     //   385: iconst_0
/*      */     //   386: istore #29
/*      */     //   388: iload #29
/*      */     //   390: sipush #256
/*      */     //   393: if_icmpge -> 489
/*      */     //   396: iload #22
/*      */     //   398: bipush #7
/*      */     //   400: if_icmpge -> 473
/*      */     //   403: aload #15
/*      */     //   405: iload #29
/*      */     //   407: laload
/*      */     //   408: lconst_1
/*      */     //   409: lcmp
/*      */     //   410: ifle -> 473
/*      */     //   413: aload #15
/*      */     //   415: iload #29
/*      */     //   417: laload
/*      */     //   418: ldc2_w 1024
/*      */     //   421: lcmp
/*      */     //   422: ifge -> 444
/*      */     //   425: aload_0
/*      */     //   426: aload_1
/*      */     //   427: aload_2
/*      */     //   428: lload #27
/*      */     //   430: lload #27
/*      */     //   432: aload #15
/*      */     //   434: iload #29
/*      */     //   436: laload
/*      */     //   437: ladd
/*      */     //   438: invokestatic insertionSortIndirect : ([[J[[F[[FJJ)V
/*      */     //   441: goto -> 473
/*      */     //   444: aload #12
/*      */     //   446: iload #11
/*      */     //   448: lload #27
/*      */     //   450: lastore
/*      */     //   451: aload #13
/*      */     //   453: iload #11
/*      */     //   455: aload #15
/*      */     //   457: iload #29
/*      */     //   459: laload
/*      */     //   460: lastore
/*      */     //   461: aload #14
/*      */     //   463: iload #11
/*      */     //   465: iinc #11, 1
/*      */     //   468: iload #22
/*      */     //   470: iconst_1
/*      */     //   471: iadd
/*      */     //   472: iastore
/*      */     //   473: lload #27
/*      */     //   475: aload #15
/*      */     //   477: iload #29
/*      */     //   479: laload
/*      */     //   480: ladd
/*      */     //   481: lstore #27
/*      */     //   483: iinc #29, 1
/*      */     //   486: goto -> 388
/*      */     //   489: aload #15
/*      */     //   491: lconst_0
/*      */     //   492: invokestatic fill : ([JJ)V
/*      */     //   495: goto -> 732
/*      */     //   498: lload #18
/*      */     //   500: lload #20
/*      */     //   502: ladd
/*      */     //   503: aload #15
/*      */     //   505: iload #26
/*      */     //   507: laload
/*      */     //   508: lsub
/*      */     //   509: lstore #29
/*      */     //   511: iconst_m1
/*      */     //   512: istore #31
/*      */     //   514: lload #18
/*      */     //   516: lstore #32
/*      */     //   518: lload #32
/*      */     //   520: lload #29
/*      */     //   522: lcmp
/*      */     //   523: ifgt -> 732
/*      */     //   526: aload_0
/*      */     //   527: lload #32
/*      */     //   529: invokestatic get : ([[JJ)J
/*      */     //   532: lstore #36
/*      */     //   534: aload #24
/*      */     //   536: lload #36
/*      */     //   538: invokestatic get : ([[FJ)F
/*      */     //   541: invokestatic fixFloat : (F)I
/*      */     //   544: iload #25
/*      */     //   546: iushr
/*      */     //   547: sipush #255
/*      */     //   550: iand
/*      */     //   551: iload #23
/*      */     //   553: ixor
/*      */     //   554: istore #31
/*      */     //   556: lload #32
/*      */     //   558: lload #29
/*      */     //   560: lcmp
/*      */     //   561: ifge -> 636
/*      */     //   564: aload #16
/*      */     //   566: iload #31
/*      */     //   568: dup2
/*      */     //   569: laload
/*      */     //   570: lconst_1
/*      */     //   571: lsub
/*      */     //   572: dup2_x2
/*      */     //   573: lastore
/*      */     //   574: dup2
/*      */     //   575: lstore #34
/*      */     //   577: lload #32
/*      */     //   579: lcmp
/*      */     //   580: ifle -> 628
/*      */     //   583: lload #36
/*      */     //   585: lstore #38
/*      */     //   587: aload_0
/*      */     //   588: lload #34
/*      */     //   590: invokestatic get : ([[JJ)J
/*      */     //   593: lstore #36
/*      */     //   595: aload_0
/*      */     //   596: lload #34
/*      */     //   598: lload #38
/*      */     //   600: invokestatic set : ([[JJJ)V
/*      */     //   603: aload #24
/*      */     //   605: lload #36
/*      */     //   607: invokestatic get : ([[FJ)F
/*      */     //   610: invokestatic fixFloat : (F)I
/*      */     //   613: iload #25
/*      */     //   615: iushr
/*      */     //   616: sipush #255
/*      */     //   619: iand
/*      */     //   620: iload #23
/*      */     //   622: ixor
/*      */     //   623: istore #31
/*      */     //   625: goto -> 564
/*      */     //   628: aload_0
/*      */     //   629: lload #32
/*      */     //   631: lload #36
/*      */     //   633: invokestatic set : ([[JJJ)V
/*      */     //   636: iload #22
/*      */     //   638: bipush #7
/*      */     //   640: if_icmpge -> 713
/*      */     //   643: aload #15
/*      */     //   645: iload #31
/*      */     //   647: laload
/*      */     //   648: lconst_1
/*      */     //   649: lcmp
/*      */     //   650: ifle -> 713
/*      */     //   653: aload #15
/*      */     //   655: iload #31
/*      */     //   657: laload
/*      */     //   658: ldc2_w 1024
/*      */     //   661: lcmp
/*      */     //   662: ifge -> 684
/*      */     //   665: aload_0
/*      */     //   666: aload_1
/*      */     //   667: aload_2
/*      */     //   668: lload #32
/*      */     //   670: lload #32
/*      */     //   672: aload #15
/*      */     //   674: iload #31
/*      */     //   676: laload
/*      */     //   677: ladd
/*      */     //   678: invokestatic insertionSortIndirect : ([[J[[F[[FJJ)V
/*      */     //   681: goto -> 713
/*      */     //   684: aload #12
/*      */     //   686: iload #11
/*      */     //   688: lload #32
/*      */     //   690: lastore
/*      */     //   691: aload #13
/*      */     //   693: iload #11
/*      */     //   695: aload #15
/*      */     //   697: iload #31
/*      */     //   699: laload
/*      */     //   700: lastore
/*      */     //   701: aload #14
/*      */     //   703: iload #11
/*      */     //   705: iinc #11, 1
/*      */     //   708: iload #22
/*      */     //   710: iconst_1
/*      */     //   711: iadd
/*      */     //   712: iastore
/*      */     //   713: lload #32
/*      */     //   715: aload #15
/*      */     //   717: iload #31
/*      */     //   719: laload
/*      */     //   720: ladd
/*      */     //   721: lstore #32
/*      */     //   723: aload #15
/*      */     //   725: iload #31
/*      */     //   727: lconst_0
/*      */     //   728: lastore
/*      */     //   729: goto -> 518
/*      */     //   732: goto -> 113
/*      */     //   735: return
/*      */     // Line number table:
/*      */     //   Java source line number -> byte code offset
/*      */     //   #1433	-> 0
/*      */     //   #1434	-> 11
/*      */     //   #1435	-> 20
/*      */     //   #1437	-> 21
/*      */     //   #1438	-> 24
/*      */     //   #1439	-> 28
/*      */     //   #1440	-> 33
/*      */     //   #1441	-> 36
/*      */     //   #1442	-> 43
/*      */     //   #1443	-> 50
/*      */     //   #1444	-> 57
/*      */     //   #1445	-> 63
/*      */     //   #1446	-> 72
/*      */     //   #1447	-> 81
/*      */     //   #1448	-> 88
/*      */     //   #1449	-> 95
/*      */     //   #1450	-> 113
/*      */     //   #1451	-> 118
/*      */     //   #1452	-> 128
/*      */     //   #1453	-> 135
/*      */     //   #1454	-> 142
/*      */     //   #1455	-> 158
/*      */     //   #1456	-> 171
/*      */     //   #1461	-> 182
/*      */     //   #1463	-> 236
/*      */     //   #1464	-> 239
/*      */     //   #1465	-> 252
/*      */     //   #1466	-> 263
/*      */     //   #1467	-> 277
/*      */     //   #1465	-> 293
/*      */     //   #1469	-> 299
/*      */     //   #1470	-> 304
/*      */     //   #1471	-> 370
/*      */     //   #1472	-> 381
/*      */     //   #1473	-> 385
/*      */     //   #1474	-> 396
/*      */     //   #1475	-> 413
/*      */     //   #1477	-> 444
/*      */     //   #1478	-> 451
/*      */     //   #1479	-> 461
/*      */     //   #1482	-> 473
/*      */     //   #1473	-> 483
/*      */     //   #1484	-> 489
/*      */     //   #1486	-> 498
/*      */     //   #1488	-> 511
/*      */     //   #1489	-> 514
/*      */     //   #1490	-> 526
/*      */     //   #1491	-> 534
/*      */     //   #1492	-> 556
/*      */     //   #1493	-> 564
/*      */     //   #1494	-> 583
/*      */     //   #1495	-> 587
/*      */     //   #1496	-> 595
/*      */     //   #1497	-> 603
/*      */     //   #1498	-> 625
/*      */     //   #1499	-> 628
/*      */     //   #1501	-> 636
/*      */     //   #1502	-> 653
/*      */     //   #1504	-> 684
/*      */     //   #1505	-> 691
/*      */     //   #1506	-> 701
/*      */     //   #1489	-> 713
/*      */     //   #1511	-> 732
/*      */     //   #1512	-> 735
/*      */     // Local variable table:
/*      */     //   start	length	slot	name	descriptor
/*      */     //   189	47	26	i	J
/*      */     //   255	44	29	i	I
/*      */     //   311	59	29	i	J
/*      */     //   388	101	29	i	I
/*      */     //   587	38	38	z	J
/*      */     //   577	59	34	d	J
/*      */     //   534	179	36	t	J
/*      */     //   518	214	32	i	J
/*      */     //   511	221	29	end	J
/*      */     //   514	218	31	c	I
/*      */     //   128	604	18	first	J
/*      */     //   135	597	20	length	J
/*      */     //   142	590	22	level	I
/*      */     //   158	574	23	signMask	I
/*      */     //   171	561	24	k	[[F
/*      */     //   182	550	25	shift	I
/*      */     //   239	493	26	lastUsed	I
/*      */     //   252	480	27	p	J
/*      */     //   0	736	0	perm	[[J
/*      */     //   0	736	1	a	[[F
/*      */     //   0	736	2	b	[[F
/*      */     //   0	736	3	from	J
/*      */     //   0	736	5	to	J
/*      */     //   0	736	7	stable	Z
/*      */     //   24	712	8	layers	I
/*      */     //   28	708	9	maxLevel	I
/*      */     //   33	703	10	stackSize	I
/*      */     //   36	700	11	stackPos	I
/*      */     //   43	693	12	offsetStack	[J
/*      */     //   50	686	13	lengthStack	[J
/*      */     //   57	679	14	levelStack	[I
/*      */     //   88	648	15	count	[J
/*      */     //   95	641	16	pos	[J
/*      */     //   113	623	17	support	[[J
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static float[][] shuffle(float[][] a, long from, long to, Random random) {
/* 1524 */     return BigArrays.shuffle(a, from, to, random);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static float[][] shuffle(float[][] a, Random random) {
/* 1535 */     return BigArrays.shuffle(a, random);
/*      */   }
/*      */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\i\\unimi\dsi\fastutil\floats\FloatBigArrays.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */