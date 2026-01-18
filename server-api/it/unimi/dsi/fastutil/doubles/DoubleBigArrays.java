/*      */ package it.unimi.dsi.fastutil.doubles;
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
/*      */ public final class DoubleBigArrays
/*      */ {
/*   70 */   public static final double[][] EMPTY_BIG_ARRAY = new double[0][];
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*   78 */   public static final double[][] DEFAULT_EMPTY_BIG_ARRAY = new double[0][];
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
/*      */   public static double get(double[][] array, long index) {
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
/*      */   public static void set(double[][] array, long index, double value) {
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
/*      */   public static void swap(double[][] array, long first, long second) {
/*  116 */     double t = array[BigArrays.segment(first)][BigArrays.displacement(first)];
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
/*      */   public static void add(double[][] array, long index, double incr) {
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
/*      */   public static void mul(double[][] array, long index, double factor) {
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
/*      */   public static void incr(double[][] array, long index) {
/*  156 */     array[BigArrays.segment(index)][BigArrays.displacement(index)] = array[BigArrays.segment(index)][BigArrays.displacement(index)] + 1.0D;
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
/*      */   public static void decr(double[][] array, long index) {
/*  168 */     array[BigArrays.segment(index)][BigArrays.displacement(index)] = array[BigArrays.segment(index)][BigArrays.displacement(index)] - 1.0D;
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
/*      */   public static long length(double[][] array) {
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
/*      */   public static void copy(double[][] srcArray, long srcPos, double[][] destArray, long destPos, long length) {
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
/*      */   public static void copyFromBig(double[][] srcArray, long srcPos, double[] destArray, int destPos, int length) {
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
/*      */   public static void copyToBig(double[] srcArray, int srcPos, double[][] destArray, long destPos, long length) {
/*  230 */     BigArrays.copyToBig(srcArray, srcPos, destArray, destPos, length);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static double[][] newBigArray(long length) {
/*  240 */     if (length == 0L) return EMPTY_BIG_ARRAY; 
/*  241 */     BigArrays.ensureLength(length);
/*  242 */     int baseLength = (int)(length + 134217727L >>> 27L);
/*  243 */     double[][] base = new double[baseLength][];
/*  244 */     int residual = (int)(length & 0x7FFFFFFL);
/*  245 */     if (residual != 0)
/*  246 */     { for (int i = 0; i < baseLength - 1; ) { base[i] = new double[134217728]; i++; }
/*  247 */        base[baseLength - 1] = new double[residual]; }
/*  248 */     else { for (int i = 0; i < baseLength; ) { base[i] = new double[134217728]; i++; }  }
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
/*      */   public static double[][] wrap(double[] array) {
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
/*      */   public static double[][] ensureCapacity(double[][] array, long length) {
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
/*      */   public static double[][] forceCapacity(double[][] array, long length, long preserve) {
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
/*      */   public static double[][] ensureCapacity(double[][] array, long length, long preserve) {
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
/*      */   public static double[][] grow(double[][] array, long length) {
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
/*      */   public static double[][] grow(double[][] array, long length, long preserve) {
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
/*      */   public static double[][] trim(double[][] array, long length) {
/*  402 */     BigArrays.ensureLength(length);
/*  403 */     long oldLength = length(array);
/*  404 */     if (length >= oldLength) return array; 
/*  405 */     int baseLength = (int)(length + 134217727L >>> 27L);
/*  406 */     double[][] base = Arrays.<double[]>copyOf(array, baseLength);
/*  407 */     int residual = (int)(length & 0x7FFFFFFL);
/*  408 */     if (residual != 0) base[baseLength - 1] = DoubleArrays.trim(base[baseLength - 1], residual); 
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
/*      */   public static double[][] setLength(double[][] array, long length) {
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
/*      */   public static double[][] copy(double[][] array, long offset, long length) {
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
/*      */   public static double[][] copy(double[][] array) {
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
/*      */   public static void fill(double[][] array, double value) {
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
/*      */   public static void fill(double[][] array, long from, long to, double value) {
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
/*      */   public static boolean equals(double[][] a1, double[][] a2) {
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
/*      */   public static String toString(double[][] a) {
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
/*      */   public static void ensureFromTo(double[][] a, long from, long to) {
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
/*      */   public static void ensureOffsetLength(double[][] a, long offset, long length) {
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
/*      */   public static void ensureSameLength(double[][] a, double[][] b) {
/*  572 */     if (length(a) != length(b)) throw new IllegalArgumentException("Array size mismatch: " + length(a) + " != " + length(b)); 
/*      */   }
/*      */   
/*      */   private static final class BigArrayHashStrategy implements Hash.Strategy<double[][]>, Serializable {
/*      */     private static final long serialVersionUID = -7046029254386353129L;
/*      */     
/*      */     private BigArrayHashStrategy() {}
/*      */     
/*      */     public int hashCode(double[][] o) {
/*  581 */       return Arrays.deepHashCode((Object[])o);
/*      */     }
/*      */ 
/*      */     
/*      */     public boolean equals(double[][] a, double[][] b) {
/*  586 */       return DoubleBigArrays.equals(a, b);
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
/*      */   private static final int DIGITS_PER_ELEMENT = 8;
/*      */   private static final int RADIXSORT_NO_REC = 1024;
/*      */   
/*      */   private static ForkJoinPool getPool() {
/*  606 */     ForkJoinPool current = ForkJoinTask.getPool();
/*  607 */     return (current == null) ? ForkJoinPool.commonPool() : current;
/*      */   }
/*      */   
/*      */   private static void swap(double[][] x, long a, long b, long n) {
/*  611 */     for (int i = 0; i < n; ) { BigArrays.swap(x, a, b); i++; a++; b++; }
/*      */   
/*      */   }
/*      */   private static long med3(double[][] x, long a, long b, long c, DoubleComparator comp) {
/*  615 */     int ab = comp.compare(BigArrays.get(x, a), BigArrays.get(x, b));
/*  616 */     int ac = comp.compare(BigArrays.get(x, a), BigArrays.get(x, c));
/*  617 */     int bc = comp.compare(BigArrays.get(x, b), BigArrays.get(x, c));
/*  618 */     return (ab < 0) ? ((bc < 0) ? b : ((ac < 0) ? c : a)) : ((bc > 0) ? b : ((ac > 0) ? c : a));
/*      */   }
/*      */   private static void selectionSort(double[][] a, long from, long to, DoubleComparator comp) {
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
/*      */   public static void quickSort(double[][] x, long from, long to, DoubleComparator comp) {
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
/*  663 */     double v = BigArrays.get(x, m);
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
/*      */     BigArrays.swap(x, b++, c--); } private static long med3(double[][] x, long a, long b, long c) {
/*  691 */     int ab = Double.compare(BigArrays.get(x, a), BigArrays.get(x, b));
/*  692 */     int ac = Double.compare(BigArrays.get(x, a), BigArrays.get(x, c));
/*  693 */     int bc = Double.compare(BigArrays.get(x, b), BigArrays.get(x, c));
/*  694 */     return (ab < 0) ? ((bc < 0) ? b : ((ac < 0) ? c : a)) : ((bc > 0) ? b : ((ac > 0) ? c : a));
/*      */   }
/*      */   private static void selectionSort(double[][] a, long from, long to) {
/*      */     long i;
/*  698 */     for (i = from; i < to - 1L; i++) {
/*  699 */       long m = i; long j;
/*  700 */       for (j = i + 1L; j < to; ) { if (Double.compare(BigArrays.get(a, j), BigArrays.get(a, m)) < 0) m = j;  j++; }
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
/*      */   public static void quickSort(double[][] x, DoubleComparator comp) {
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
/*      */   public static void quickSort(double[][] x, long from, long to) {
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
/*  755 */     double v = BigArrays.get(x, m);
/*      */     
/*  757 */     long a = from, b = a, c = to - 1L, d = c;
/*      */     
/*      */     int comparison;
/*  760 */     while (b <= c && (comparison = Double.compare(BigArrays.get(x, b), v)) <= 0) {
/*  761 */       if (comparison == 0) BigArrays.swap(x, a++, b); 
/*  762 */       b++;
/*      */     } 
/*  764 */     while (c >= b && (comparison = Double.compare(BigArrays.get(x, c), v)) >= 0) {
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
/*      */   public static void quickSort(double[][] x) {
/*  793 */     quickSort(x, 0L, BigArrays.length(x));
/*      */   }
/*      */   
/*      */   protected static class ForkJoinQuickSort extends RecursiveAction {
/*      */     private static final long serialVersionUID = 1L;
/*      */     private final long from;
/*      */     private final long to;
/*      */     private final double[][] x;
/*      */     
/*      */     public ForkJoinQuickSort(double[][] x, long from, long to) {
/*  803 */       this.from = from;
/*  804 */       this.to = to;
/*  805 */       this.x = x;
/*      */     }
/*      */ 
/*      */ 
/*      */     
/*      */     protected void compute() {
/*  811 */       double[][] x = this.x;
/*  812 */       long len = this.to - this.from;
/*  813 */       if (len < 8192L) {
/*  814 */         DoubleBigArrays.quickSort(x, this.from, this.to);
/*      */         
/*      */         return;
/*      */       } 
/*  818 */       long m = this.from + len / 2L;
/*  819 */       long l = this.from;
/*  820 */       long n = this.to - 1L;
/*  821 */       long s = len / 8L;
/*  822 */       l = DoubleBigArrays.med3(x, l, l + s, l + 2L * s);
/*  823 */       m = DoubleBigArrays.med3(x, m - s, m, m + s);
/*  824 */       n = DoubleBigArrays.med3(x, n - 2L * s, n - s, n);
/*  825 */       m = DoubleBigArrays.med3(x, l, m, n);
/*  826 */       double v = BigArrays.get(x, m);
/*      */       
/*  828 */       long a = this.from, b = a, c = this.to - 1L, d = c;
/*      */       
/*      */       int comparison;
/*  831 */       while (b <= c && (comparison = Double.compare(BigArrays.get(x, b), v)) <= 0) {
/*  832 */         if (comparison == 0) BigArrays.swap(x, a++, b); 
/*  833 */         b++;
/*      */       } 
/*  835 */       while (c >= b && (comparison = Double.compare(BigArrays.get(x, c), v)) >= 0) {
/*  836 */         if (comparison == 0) BigArrays.swap(x, c, d--); 
/*  837 */         c--;
/*      */       } 
/*  839 */       if (b > c) {
/*      */ 
/*      */ 
/*      */ 
/*      */         
/*  844 */         s = Math.min(a - this.from, b - a);
/*  845 */         DoubleBigArrays.swap(x, this.from, b - s, s);
/*  846 */         s = Math.min(d - c, this.to - d - 1L);
/*  847 */         DoubleBigArrays.swap(x, b, this.to - s, s);
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
/*      */   public static void parallelQuickSort(double[][] x, long from, long to) {
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
/*      */   public static void parallelQuickSort(double[][] x) {
/*  889 */     parallelQuickSort(x, 0L, BigArrays.length(x));
/*      */   }
/*      */   
/*      */   protected static class ForkJoinQuickSortComp extends RecursiveAction {
/*      */     private static final long serialVersionUID = 1L;
/*      */     private final long from;
/*      */     private final long to;
/*      */     private final double[][] x;
/*      */     private final DoubleComparator comp;
/*      */     
/*      */     public ForkJoinQuickSortComp(double[][] x, long from, long to, DoubleComparator comp) {
/*  900 */       this.from = from;
/*  901 */       this.to = to;
/*  902 */       this.x = x;
/*  903 */       this.comp = comp;
/*      */     }
/*      */ 
/*      */     
/*      */     protected void compute() {
/*  908 */       double[][] x = this.x;
/*  909 */       long len = this.to - this.from;
/*  910 */       if (len < 8192L) {
/*  911 */         DoubleBigArrays.quickSort(x, this.from, this.to, this.comp);
/*      */         
/*      */         return;
/*      */       } 
/*  915 */       long m = this.from + len / 2L;
/*  916 */       long l = this.from;
/*  917 */       long n = this.to - 1L;
/*  918 */       long s = len / 8L;
/*  919 */       l = DoubleBigArrays.med3(x, l, l + s, l + 2L * s, this.comp);
/*  920 */       m = DoubleBigArrays.med3(x, m - s, m, m + s, this.comp);
/*  921 */       n = DoubleBigArrays.med3(x, n - 2L * s, n - s, n, this.comp);
/*  922 */       m = DoubleBigArrays.med3(x, l, m, n, this.comp);
/*  923 */       double v = BigArrays.get(x, m);
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
/*  942 */         DoubleBigArrays.swap(x, this.from, b - s, s);
/*  943 */         s = Math.min(d - c, this.to - d - 1L);
/*  944 */         DoubleBigArrays.swap(x, b, this.to - s, s);
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
/*      */   public static void parallelQuickSort(double[][] x, long from, long to, DoubleComparator comp) {
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
/*      */   public static void parallelQuickSort(double[][] x, DoubleComparator comp) {
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
/*      */   public static long binarySearch(double[][] a, long from, long to, double key) {
/* 1013 */     to--;
/* 1014 */     while (from <= to) {
/* 1015 */       long mid = from + to >>> 1L;
/* 1016 */       double midVal = BigArrays.get(a, mid);
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
/*      */   public static long binarySearch(double[][] a, double key) {
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
/*      */   public static long binarySearch(double[][] a, long from, long to, double key, DoubleComparator c) {
/* 1065 */     to--;
/* 1066 */     while (from <= to) {
/* 1067 */       long mid = from + to >>> 1L;
/* 1068 */       double midVal = BigArrays.get(a, mid);
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
/*      */   public static long binarySearch(double[][] a, double key, DoubleComparator c) {
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
/*      */   private static final long fixDouble(double d) {
/* 1110 */     long l = Double.doubleToRawLongBits(d);
/* 1111 */     return (l >= 0L) ? l : (l ^ Long.MAX_VALUE);
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
/*      */   public static void radixSort(double[][] a) {
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
/*      */   public static void radixSort(double[][] a, long from, long to) {
/* 1155 */     int maxLevel = 7;
/* 1156 */     int stackSize = 1786;
/* 1157 */     long[] offsetStack = new long[1786];
/* 1158 */     int offsetPos = 0;
/* 1159 */     long[] lengthStack = new long[1786];
/* 1160 */     int lengthPos = 0;
/* 1161 */     int[] levelStack = new int[1786];
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
/* 1173 */       int signMask = (level % 8 == 0) ? 128 : 0;
/* 1174 */       if (length < 40L) {
/* 1175 */         selectionSort(a, first, first + length);
/*      */         continue;
/*      */       } 
/* 1178 */       int shift = (7 - level % 8) * 8;
/*      */ 
/*      */       
/*      */       long i;
/*      */       
/* 1183 */       for (i = length; i-- != 0L; BigArrays.set(digit, i, (byte)(int)(fixDouble(BigArrays.get(a, first + i)) >>> shift & 0xFFL ^ signMask)));
/* 1184 */       for (i = length; i-- != 0L; count[BigArrays.get(digit, i) & 0xFF] = count[BigArrays.get(digit, i) & 0xFF] + 1L);
/*      */       
/* 1186 */       int lastUsed = -1;
/* 1187 */       long p = 0L;
/* 1188 */       for (int j = 0; j < 256; j++) {
/* 1189 */         if (count[j] != 0L) {
/* 1190 */           lastUsed = j;
/* 1191 */           if (level < 7 && count[j] > 1L) {
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
/* 1207 */         double t = BigArrays.get(a, l1 + first);
/* 1208 */         c = BigArrays.get(digit, l1) & 0xFF; long d;
/* 1209 */         for (pos[c] = pos[c] - 1L; (d = pos[c] - 1L) > l1; ) {
/* 1210 */           double z = t;
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
/*      */   private static void selectionSort(double[][] a, double[][] b, long from, long to) {
/*      */     long i;
/* 1223 */     for (i = from; i < to - 1L; i++) {
/* 1224 */       long m = i; long j;
/* 1225 */       for (j = i + 1L; j < to; ) { if (Double.compare(BigArrays.get(a, j), BigArrays.get(a, m)) < 0 || (Double.compare(BigArrays.get(a, j), BigArrays.get(a, m)) == 0 && Double.compare(BigArrays.get(b, j), BigArrays.get(b, m)) < 0)) m = j;  j++; }
/* 1226 */        if (m != i) {
/* 1227 */         double t = BigArrays.get(a, i);
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
/*      */   public static void radixSort(double[][] a, double[][] b) {
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
/*      */   public static void radixSort(double[][] a, double[][] b, long from, long to) {
/* 1291 */     int layers = 2;
/* 1292 */     if (BigArrays.length(a) != BigArrays.length(b)) throw new IllegalArgumentException("Array size mismatch."); 
/* 1293 */     int maxLevel = 15;
/* 1294 */     int stackSize = 3826;
/* 1295 */     long[] offsetStack = new long[3826];
/* 1296 */     int offsetPos = 0;
/* 1297 */     long[] lengthStack = new long[3826];
/* 1298 */     int lengthPos = 0;
/* 1299 */     int[] levelStack = new int[3826];
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
/* 1311 */       int signMask = (level % 8 == 0) ? 128 : 0;
/* 1312 */       if (length < 40L) {
/* 1313 */         selectionSort(a, b, first, first + length);
/*      */         continue;
/*      */       } 
/* 1316 */       double[][] k = (level < 8) ? a : b;
/* 1317 */       int shift = (7 - level % 8) * 8;
/*      */ 
/*      */       
/*      */       long i;
/*      */       
/* 1322 */       for (i = length; i-- != 0L; BigArrays.set(digit, i, (byte)(int)(fixDouble(BigArrays.get(k, first + i)) >>> shift & 0xFFL ^ signMask)));
/* 1323 */       for (i = length; i-- != 0L; count[BigArrays.get(digit, i) & 0xFF] = count[BigArrays.get(digit, i) & 0xFF] + 1L);
/*      */       
/* 1325 */       int lastUsed = -1;
/* 1326 */       long p = 0L;
/* 1327 */       for (int j = 0; j < 256; j++) {
/* 1328 */         if (count[j] != 0L) {
/* 1329 */           lastUsed = j;
/* 1330 */           if (level < 15 && count[j] > 1L) {
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
/* 1344 */         double t = BigArrays.get(a, l1 + first);
/* 1345 */         double u = BigArrays.get(b, l1 + first);
/* 1346 */         c = BigArrays.get(digit, l1) & 0xFF; long d;
/* 1347 */         for (pos[c] = pos[c] - 1L; (d = pos[c] - 1L) > l1; ) {
/* 1348 */           double z = t;
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
/*      */   private static void insertionSortIndirect(long[][] perm, double[][] a, double[][] b, long from, long to) {
/* 1367 */     long i = from; while (true) { long t, j, u; if (++i < to)
/* 1368 */       { t = BigArrays.get(perm, i);
/* 1369 */         j = i;
/* 1370 */         u = BigArrays.get(perm, j - 1L); } else { break; }  if (Double.compare(BigArrays.get(a, t), BigArrays.get(a, u)) < 0 || (Double.compare(BigArrays.get(a, t), BigArrays.get(a, u)) == 0 && Double.compare(BigArrays.get(b, t), BigArrays.get(b, u)) < 0));
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
/*      */   public static void radixSortIndirect(long[][] perm, double[][] a, double[][] b, boolean stable) {
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
/*      */   public static void radixSortIndirect(long[][] perm, double[][] a, double[][] b, long from, long to, boolean stable) {
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
/*      */     //   17: invokestatic insertionSortIndirect : ([[J[[D[[DJJ)V
/*      */     //   20: return
/*      */     //   21: iconst_2
/*      */     //   22: istore #8
/*      */     //   24: bipush #15
/*      */     //   26: istore #9
/*      */     //   28: sipush #3826
/*      */     //   31: istore #10
/*      */     //   33: iconst_0
/*      */     //   34: istore #11
/*      */     //   36: sipush #3826
/*      */     //   39: newarray long
/*      */     //   41: astore #12
/*      */     //   43: sipush #3826
/*      */     //   46: newarray long
/*      */     //   48: astore #13
/*      */     //   50: sipush #3826
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
/*      */     //   115: ifle -> 747
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
/*      */     //   144: bipush #8
/*      */     //   146: irem
/*      */     //   147: ifne -> 156
/*      */     //   150: sipush #128
/*      */     //   153: goto -> 157
/*      */     //   156: iconst_0
/*      */     //   157: istore #23
/*      */     //   159: iload #22
/*      */     //   161: bipush #8
/*      */     //   163: if_icmpge -> 170
/*      */     //   166: aload_1
/*      */     //   167: goto -> 171
/*      */     //   170: aload_2
/*      */     //   171: astore #24
/*      */     //   173: bipush #7
/*      */     //   175: iload #22
/*      */     //   177: bipush #8
/*      */     //   179: irem
/*      */     //   180: isub
/*      */     //   181: bipush #8
/*      */     //   183: imul
/*      */     //   184: istore #25
/*      */     //   186: lload #18
/*      */     //   188: lload #20
/*      */     //   190: ladd
/*      */     //   191: lstore #26
/*      */     //   193: lload #26
/*      */     //   195: dup2
/*      */     //   196: lconst_1
/*      */     //   197: lsub
/*      */     //   198: lstore #26
/*      */     //   200: lload #18
/*      */     //   202: lcmp
/*      */     //   203: ifeq -> 242
/*      */     //   206: aload #15
/*      */     //   208: aload #24
/*      */     //   210: aload_0
/*      */     //   211: lload #26
/*      */     //   213: invokestatic get : ([[JJ)J
/*      */     //   216: invokestatic get : ([[DJ)D
/*      */     //   219: invokestatic fixDouble : (D)J
/*      */     //   222: iload #25
/*      */     //   224: lushr
/*      */     //   225: ldc2_w 255
/*      */     //   228: land
/*      */     //   229: iload #23
/*      */     //   231: i2l
/*      */     //   232: lxor
/*      */     //   233: l2i
/*      */     //   234: dup2
/*      */     //   235: laload
/*      */     //   236: lconst_1
/*      */     //   237: ladd
/*      */     //   238: lastore
/*      */     //   239: goto -> 193
/*      */     //   242: iconst_m1
/*      */     //   243: istore #26
/*      */     //   245: iload #7
/*      */     //   247: ifeq -> 254
/*      */     //   250: lconst_0
/*      */     //   251: goto -> 256
/*      */     //   254: lload #18
/*      */     //   256: lstore #27
/*      */     //   258: iconst_0
/*      */     //   259: istore #29
/*      */     //   261: iload #29
/*      */     //   263: sipush #256
/*      */     //   266: if_icmpge -> 305
/*      */     //   269: aload #15
/*      */     //   271: iload #29
/*      */     //   273: laload
/*      */     //   274: lconst_0
/*      */     //   275: lcmp
/*      */     //   276: ifeq -> 283
/*      */     //   279: iload #29
/*      */     //   281: istore #26
/*      */     //   283: aload #16
/*      */     //   285: iload #29
/*      */     //   287: lload #27
/*      */     //   289: aload #15
/*      */     //   291: iload #29
/*      */     //   293: laload
/*      */     //   294: ladd
/*      */     //   295: dup2
/*      */     //   296: lstore #27
/*      */     //   298: lastore
/*      */     //   299: iinc #29, 1
/*      */     //   302: goto -> 261
/*      */     //   305: iload #7
/*      */     //   307: ifeq -> 506
/*      */     //   310: lload #18
/*      */     //   312: lload #20
/*      */     //   314: ladd
/*      */     //   315: lstore #29
/*      */     //   317: lload #29
/*      */     //   319: dup2
/*      */     //   320: lconst_1
/*      */     //   321: lsub
/*      */     //   322: lstore #29
/*      */     //   324: lload #18
/*      */     //   326: lcmp
/*      */     //   327: ifeq -> 378
/*      */     //   330: aload #17
/*      */     //   332: aload #16
/*      */     //   334: aload #24
/*      */     //   336: aload_0
/*      */     //   337: lload #29
/*      */     //   339: invokestatic get : ([[JJ)J
/*      */     //   342: invokestatic get : ([[DJ)D
/*      */     //   345: invokestatic fixDouble : (D)J
/*      */     //   348: iload #25
/*      */     //   350: lushr
/*      */     //   351: ldc2_w 255
/*      */     //   354: land
/*      */     //   355: iload #23
/*      */     //   357: i2l
/*      */     //   358: lxor
/*      */     //   359: l2i
/*      */     //   360: dup2
/*      */     //   361: laload
/*      */     //   362: lconst_1
/*      */     //   363: lsub
/*      */     //   364: dup2_x2
/*      */     //   365: lastore
/*      */     //   366: aload_0
/*      */     //   367: lload #29
/*      */     //   369: invokestatic get : ([[JJ)J
/*      */     //   372: invokestatic set : ([[JJJ)V
/*      */     //   375: goto -> 317
/*      */     //   378: aload #17
/*      */     //   380: lconst_0
/*      */     //   381: aload_0
/*      */     //   382: lload #18
/*      */     //   384: lload #20
/*      */     //   386: invokestatic copy : ([[JJ[[JJJ)V
/*      */     //   389: lload #18
/*      */     //   391: lstore #27
/*      */     //   393: iconst_0
/*      */     //   394: istore #29
/*      */     //   396: iload #29
/*      */     //   398: sipush #256
/*      */     //   401: if_icmpge -> 497
/*      */     //   404: iload #22
/*      */     //   406: bipush #15
/*      */     //   408: if_icmpge -> 481
/*      */     //   411: aload #15
/*      */     //   413: iload #29
/*      */     //   415: laload
/*      */     //   416: lconst_1
/*      */     //   417: lcmp
/*      */     //   418: ifle -> 481
/*      */     //   421: aload #15
/*      */     //   423: iload #29
/*      */     //   425: laload
/*      */     //   426: ldc2_w 1024
/*      */     //   429: lcmp
/*      */     //   430: ifge -> 452
/*      */     //   433: aload_0
/*      */     //   434: aload_1
/*      */     //   435: aload_2
/*      */     //   436: lload #27
/*      */     //   438: lload #27
/*      */     //   440: aload #15
/*      */     //   442: iload #29
/*      */     //   444: laload
/*      */     //   445: ladd
/*      */     //   446: invokestatic insertionSortIndirect : ([[J[[D[[DJJ)V
/*      */     //   449: goto -> 481
/*      */     //   452: aload #12
/*      */     //   454: iload #11
/*      */     //   456: lload #27
/*      */     //   458: lastore
/*      */     //   459: aload #13
/*      */     //   461: iload #11
/*      */     //   463: aload #15
/*      */     //   465: iload #29
/*      */     //   467: laload
/*      */     //   468: lastore
/*      */     //   469: aload #14
/*      */     //   471: iload #11
/*      */     //   473: iinc #11, 1
/*      */     //   476: iload #22
/*      */     //   478: iconst_1
/*      */     //   479: iadd
/*      */     //   480: iastore
/*      */     //   481: lload #27
/*      */     //   483: aload #15
/*      */     //   485: iload #29
/*      */     //   487: laload
/*      */     //   488: ladd
/*      */     //   489: lstore #27
/*      */     //   491: iinc #29, 1
/*      */     //   494: goto -> 396
/*      */     //   497: aload #15
/*      */     //   499: lconst_0
/*      */     //   500: invokestatic fill : ([JJ)V
/*      */     //   503: goto -> 744
/*      */     //   506: lload #18
/*      */     //   508: lload #20
/*      */     //   510: ladd
/*      */     //   511: aload #15
/*      */     //   513: iload #26
/*      */     //   515: laload
/*      */     //   516: lsub
/*      */     //   517: lstore #29
/*      */     //   519: iconst_m1
/*      */     //   520: istore #31
/*      */     //   522: lload #18
/*      */     //   524: lstore #32
/*      */     //   526: lload #32
/*      */     //   528: lload #29
/*      */     //   530: lcmp
/*      */     //   531: ifgt -> 744
/*      */     //   534: aload_0
/*      */     //   535: lload #32
/*      */     //   537: invokestatic get : ([[JJ)J
/*      */     //   540: lstore #36
/*      */     //   542: aload #24
/*      */     //   544: lload #36
/*      */     //   546: invokestatic get : ([[DJ)D
/*      */     //   549: invokestatic fixDouble : (D)J
/*      */     //   552: iload #25
/*      */     //   554: lushr
/*      */     //   555: ldc2_w 255
/*      */     //   558: land
/*      */     //   559: iload #23
/*      */     //   561: i2l
/*      */     //   562: lxor
/*      */     //   563: l2i
/*      */     //   564: istore #31
/*      */     //   566: lload #32
/*      */     //   568: lload #29
/*      */     //   570: lcmp
/*      */     //   571: ifge -> 648
/*      */     //   574: aload #16
/*      */     //   576: iload #31
/*      */     //   578: dup2
/*      */     //   579: laload
/*      */     //   580: lconst_1
/*      */     //   581: lsub
/*      */     //   582: dup2_x2
/*      */     //   583: lastore
/*      */     //   584: dup2
/*      */     //   585: lstore #34
/*      */     //   587: lload #32
/*      */     //   589: lcmp
/*      */     //   590: ifle -> 640
/*      */     //   593: lload #36
/*      */     //   595: lstore #38
/*      */     //   597: aload_0
/*      */     //   598: lload #34
/*      */     //   600: invokestatic get : ([[JJ)J
/*      */     //   603: lstore #36
/*      */     //   605: aload_0
/*      */     //   606: lload #34
/*      */     //   608: lload #38
/*      */     //   610: invokestatic set : ([[JJJ)V
/*      */     //   613: aload #24
/*      */     //   615: lload #36
/*      */     //   617: invokestatic get : ([[DJ)D
/*      */     //   620: invokestatic fixDouble : (D)J
/*      */     //   623: iload #25
/*      */     //   625: lushr
/*      */     //   626: ldc2_w 255
/*      */     //   629: land
/*      */     //   630: iload #23
/*      */     //   632: i2l
/*      */     //   633: lxor
/*      */     //   634: l2i
/*      */     //   635: istore #31
/*      */     //   637: goto -> 574
/*      */     //   640: aload_0
/*      */     //   641: lload #32
/*      */     //   643: lload #36
/*      */     //   645: invokestatic set : ([[JJJ)V
/*      */     //   648: iload #22
/*      */     //   650: bipush #15
/*      */     //   652: if_icmpge -> 725
/*      */     //   655: aload #15
/*      */     //   657: iload #31
/*      */     //   659: laload
/*      */     //   660: lconst_1
/*      */     //   661: lcmp
/*      */     //   662: ifle -> 725
/*      */     //   665: aload #15
/*      */     //   667: iload #31
/*      */     //   669: laload
/*      */     //   670: ldc2_w 1024
/*      */     //   673: lcmp
/*      */     //   674: ifge -> 696
/*      */     //   677: aload_0
/*      */     //   678: aload_1
/*      */     //   679: aload_2
/*      */     //   680: lload #32
/*      */     //   682: lload #32
/*      */     //   684: aload #15
/*      */     //   686: iload #31
/*      */     //   688: laload
/*      */     //   689: ladd
/*      */     //   690: invokestatic insertionSortIndirect : ([[J[[D[[DJJ)V
/*      */     //   693: goto -> 725
/*      */     //   696: aload #12
/*      */     //   698: iload #11
/*      */     //   700: lload #32
/*      */     //   702: lastore
/*      */     //   703: aload #13
/*      */     //   705: iload #11
/*      */     //   707: aload #15
/*      */     //   709: iload #31
/*      */     //   711: laload
/*      */     //   712: lastore
/*      */     //   713: aload #14
/*      */     //   715: iload #11
/*      */     //   717: iinc #11, 1
/*      */     //   720: iload #22
/*      */     //   722: iconst_1
/*      */     //   723: iadd
/*      */     //   724: iastore
/*      */     //   725: lload #32
/*      */     //   727: aload #15
/*      */     //   729: iload #31
/*      */     //   731: laload
/*      */     //   732: ladd
/*      */     //   733: lstore #32
/*      */     //   735: aload #15
/*      */     //   737: iload #31
/*      */     //   739: lconst_0
/*      */     //   740: lastore
/*      */     //   741: goto -> 526
/*      */     //   744: goto -> 113
/*      */     //   747: return
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
/*      */     //   #1455	-> 159
/*      */     //   #1456	-> 173
/*      */     //   #1461	-> 186
/*      */     //   #1463	-> 242
/*      */     //   #1464	-> 245
/*      */     //   #1465	-> 258
/*      */     //   #1466	-> 269
/*      */     //   #1467	-> 283
/*      */     //   #1465	-> 299
/*      */     //   #1469	-> 305
/*      */     //   #1470	-> 310
/*      */     //   #1471	-> 378
/*      */     //   #1472	-> 389
/*      */     //   #1473	-> 393
/*      */     //   #1474	-> 404
/*      */     //   #1475	-> 421
/*      */     //   #1477	-> 452
/*      */     //   #1478	-> 459
/*      */     //   #1479	-> 469
/*      */     //   #1482	-> 481
/*      */     //   #1473	-> 491
/*      */     //   #1484	-> 497
/*      */     //   #1486	-> 506
/*      */     //   #1488	-> 519
/*      */     //   #1489	-> 522
/*      */     //   #1490	-> 534
/*      */     //   #1491	-> 542
/*      */     //   #1492	-> 566
/*      */     //   #1493	-> 574
/*      */     //   #1494	-> 593
/*      */     //   #1495	-> 597
/*      */     //   #1496	-> 605
/*      */     //   #1497	-> 613
/*      */     //   #1498	-> 637
/*      */     //   #1499	-> 640
/*      */     //   #1501	-> 648
/*      */     //   #1502	-> 665
/*      */     //   #1504	-> 696
/*      */     //   #1505	-> 703
/*      */     //   #1506	-> 713
/*      */     //   #1489	-> 725
/*      */     //   #1511	-> 744
/*      */     //   #1512	-> 747
/*      */     // Local variable table:
/*      */     //   start	length	slot	name	descriptor
/*      */     //   193	49	26	i	J
/*      */     //   261	44	29	i	I
/*      */     //   317	61	29	i	J
/*      */     //   396	101	29	i	I
/*      */     //   597	40	38	z	J
/*      */     //   587	61	34	d	J
/*      */     //   542	183	36	t	J
/*      */     //   526	218	32	i	J
/*      */     //   519	225	29	end	J
/*      */     //   522	222	31	c	I
/*      */     //   128	616	18	first	J
/*      */     //   135	609	20	length	J
/*      */     //   142	602	22	level	I
/*      */     //   159	585	23	signMask	I
/*      */     //   173	571	24	k	[[D
/*      */     //   186	558	25	shift	I
/*      */     //   245	499	26	lastUsed	I
/*      */     //   258	486	27	p	J
/*      */     //   0	748	0	perm	[[J
/*      */     //   0	748	1	a	[[D
/*      */     //   0	748	2	b	[[D
/*      */     //   0	748	3	from	J
/*      */     //   0	748	5	to	J
/*      */     //   0	748	7	stable	Z
/*      */     //   24	724	8	layers	I
/*      */     //   28	720	9	maxLevel	I
/*      */     //   33	715	10	stackSize	I
/*      */     //   36	712	11	stackPos	I
/*      */     //   43	705	12	offsetStack	[J
/*      */     //   50	698	13	lengthStack	[J
/*      */     //   57	691	14	levelStack	[I
/*      */     //   88	660	15	count	[J
/*      */     //   95	653	16	pos	[J
/*      */     //   113	635	17	support	[[J
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
/*      */   public static double[][] shuffle(double[][] a, long from, long to, Random random) {
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
/*      */   public static double[][] shuffle(double[][] a, Random random) {
/* 1535 */     return BigArrays.shuffle(a, random);
/*      */   }
/*      */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\i\\unimi\dsi\fastutil\doubles\DoubleBigArrays.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */