/*      */ package it.unimi.dsi.fastutil.shorts;
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
/*      */ public final class ShortBigArrays
/*      */ {
/*   70 */   public static final short[][] EMPTY_BIG_ARRAY = new short[0][];
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*   78 */   public static final short[][] DEFAULT_EMPTY_BIG_ARRAY = new short[0][];
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
/*      */   public static short get(short[][] array, long index) {
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
/*      */   public static void set(short[][] array, long index, short value) {
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
/*      */   public static void swap(short[][] array, long first, long second) {
/*  116 */     short t = array[BigArrays.segment(first)][BigArrays.displacement(first)];
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
/*      */   public static void add(short[][] array, long index, short incr) {
/*  131 */     array[BigArrays.segment(index)][BigArrays.displacement(index)] = (short)(array[BigArrays.segment(index)][BigArrays.displacement(index)] + incr);
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
/*      */   public static void mul(short[][] array, long index, short factor) {
/*  144 */     array[BigArrays.segment(index)][BigArrays.displacement(index)] = (short)(array[BigArrays.segment(index)][BigArrays.displacement(index)] * factor);
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
/*      */   public static void incr(short[][] array, long index) {
/*  156 */     array[BigArrays.segment(index)][BigArrays.displacement(index)] = (short)(array[BigArrays.segment(index)][BigArrays.displacement(index)] + 1);
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
/*      */   public static void decr(short[][] array, long index) {
/*  168 */     array[BigArrays.segment(index)][BigArrays.displacement(index)] = (short)(array[BigArrays.segment(index)][BigArrays.displacement(index)] - 1);
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
/*      */   public static long length(short[][] array) {
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
/*      */   public static void copy(short[][] srcArray, long srcPos, short[][] destArray, long destPos, long length) {
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
/*      */   public static void copyFromBig(short[][] srcArray, long srcPos, short[] destArray, int destPos, int length) {
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
/*      */   public static void copyToBig(short[] srcArray, int srcPos, short[][] destArray, long destPos, long length) {
/*  230 */     BigArrays.copyToBig(srcArray, srcPos, destArray, destPos, length);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static short[][] newBigArray(long length) {
/*  240 */     if (length == 0L) return EMPTY_BIG_ARRAY; 
/*  241 */     BigArrays.ensureLength(length);
/*  242 */     int baseLength = (int)(length + 134217727L >>> 27L);
/*  243 */     short[][] base = new short[baseLength][];
/*  244 */     int residual = (int)(length & 0x7FFFFFFL);
/*  245 */     if (residual != 0)
/*  246 */     { for (int i = 0; i < baseLength - 1; ) { base[i] = new short[134217728]; i++; }
/*  247 */        base[baseLength - 1] = new short[residual]; }
/*  248 */     else { for (int i = 0; i < baseLength; ) { base[i] = new short[134217728]; i++; }  }
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
/*      */   public static short[][] wrap(short[] array) {
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
/*      */   public static short[][] ensureCapacity(short[][] array, long length) {
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
/*      */   public static short[][] forceCapacity(short[][] array, long length, long preserve) {
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
/*      */   public static short[][] ensureCapacity(short[][] array, long length, long preserve) {
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
/*      */   public static short[][] grow(short[][] array, long length) {
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
/*      */   public static short[][] grow(short[][] array, long length, long preserve) {
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
/*      */   public static short[][] trim(short[][] array, long length) {
/*  402 */     BigArrays.ensureLength(length);
/*  403 */     long oldLength = length(array);
/*  404 */     if (length >= oldLength) return array; 
/*  405 */     int baseLength = (int)(length + 134217727L >>> 27L);
/*  406 */     short[][] base = Arrays.<short[]>copyOf(array, baseLength);
/*  407 */     int residual = (int)(length & 0x7FFFFFFL);
/*  408 */     if (residual != 0) base[baseLength - 1] = ShortArrays.trim(base[baseLength - 1], residual); 
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
/*      */   public static short[][] setLength(short[][] array, long length) {
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
/*      */   public static short[][] copy(short[][] array, long offset, long length) {
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
/*      */   public static short[][] copy(short[][] array) {
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
/*      */   public static void fill(short[][] array, short value) {
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
/*      */   public static void fill(short[][] array, long from, long to, short value) {
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
/*      */   public static boolean equals(short[][] a1, short[][] a2) {
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
/*      */   public static String toString(short[][] a) {
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
/*      */   public static void ensureFromTo(short[][] a, long from, long to) {
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
/*      */   public static void ensureOffsetLength(short[][] a, long offset, long length) {
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
/*      */   public static void ensureSameLength(short[][] a, short[][] b) {
/*  572 */     if (length(a) != length(b)) throw new IllegalArgumentException("Array size mismatch: " + length(a) + " != " + length(b)); 
/*      */   }
/*      */   
/*      */   private static final class BigArrayHashStrategy implements Hash.Strategy<short[][]>, Serializable {
/*      */     private static final long serialVersionUID = -7046029254386353129L;
/*      */     
/*      */     private BigArrayHashStrategy() {}
/*      */     
/*      */     public int hashCode(short[][] o) {
/*  581 */       return Arrays.deepHashCode((Object[])o);
/*      */     }
/*      */ 
/*      */     
/*      */     public boolean equals(short[][] a, short[][] b) {
/*  586 */       return ShortBigArrays.equals(a, b);
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
/*      */   private static final int DIGITS_PER_ELEMENT = 2;
/*      */   private static final int RADIXSORT_NO_REC = 1024;
/*      */   
/*      */   private static ForkJoinPool getPool() {
/*  606 */     ForkJoinPool current = ForkJoinTask.getPool();
/*  607 */     return (current == null) ? ForkJoinPool.commonPool() : current;
/*      */   }
/*      */   
/*      */   private static void swap(short[][] x, long a, long b, long n) {
/*  611 */     for (int i = 0; i < n; ) { BigArrays.swap(x, a, b); i++; a++; b++; }
/*      */   
/*      */   }
/*      */   private static long med3(short[][] x, long a, long b, long c, ShortComparator comp) {
/*  615 */     int ab = comp.compare(BigArrays.get(x, a), BigArrays.get(x, b));
/*  616 */     int ac = comp.compare(BigArrays.get(x, a), BigArrays.get(x, c));
/*  617 */     int bc = comp.compare(BigArrays.get(x, b), BigArrays.get(x, c));
/*  618 */     return (ab < 0) ? ((bc < 0) ? b : ((ac < 0) ? c : a)) : ((bc > 0) ? b : ((ac > 0) ? c : a));
/*      */   }
/*      */   private static void selectionSort(short[][] a, long from, long to, ShortComparator comp) {
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
/*      */   public static void quickSort(short[][] x, long from, long to, ShortComparator comp) {
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
/*  663 */     short v = BigArrays.get(x, m);
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
/*      */     BigArrays.swap(x, b++, c--); } private static long med3(short[][] x, long a, long b, long c) {
/*  691 */     int ab = Short.compare(BigArrays.get(x, a), BigArrays.get(x, b));
/*  692 */     int ac = Short.compare(BigArrays.get(x, a), BigArrays.get(x, c));
/*  693 */     int bc = Short.compare(BigArrays.get(x, b), BigArrays.get(x, c));
/*  694 */     return (ab < 0) ? ((bc < 0) ? b : ((ac < 0) ? c : a)) : ((bc > 0) ? b : ((ac > 0) ? c : a));
/*      */   }
/*      */   private static void selectionSort(short[][] a, long from, long to) {
/*      */     long i;
/*  698 */     for (i = from; i < to - 1L; i++) {
/*  699 */       long m = i; long j;
/*  700 */       for (j = i + 1L; j < to; ) { if (BigArrays.get(a, j) < BigArrays.get(a, m)) m = j;  j++; }
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
/*      */   public static void quickSort(short[][] x, ShortComparator comp) {
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
/*      */   public static void quickSort(short[][] x, long from, long to) {
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
/*  755 */     short v = BigArrays.get(x, m);
/*      */     
/*  757 */     long a = from, b = a, c = to - 1L, d = c;
/*      */     
/*      */     int comparison;
/*  760 */     while (b <= c && (comparison = Short.compare(BigArrays.get(x, b), v)) <= 0) {
/*  761 */       if (comparison == 0) BigArrays.swap(x, a++, b); 
/*  762 */       b++;
/*      */     } 
/*  764 */     while (c >= b && (comparison = Short.compare(BigArrays.get(x, c), v)) >= 0) {
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
/*      */   public static void quickSort(short[][] x) {
/*  793 */     quickSort(x, 0L, BigArrays.length(x));
/*      */   }
/*      */   
/*      */   protected static class ForkJoinQuickSort extends RecursiveAction {
/*      */     private static final long serialVersionUID = 1L;
/*      */     private final long from;
/*      */     private final long to;
/*      */     private final short[][] x;
/*      */     
/*      */     public ForkJoinQuickSort(short[][] x, long from, long to) {
/*  803 */       this.from = from;
/*  804 */       this.to = to;
/*  805 */       this.x = x;
/*      */     }
/*      */ 
/*      */ 
/*      */     
/*      */     protected void compute() {
/*  811 */       short[][] x = this.x;
/*  812 */       long len = this.to - this.from;
/*  813 */       if (len < 8192L) {
/*  814 */         ShortBigArrays.quickSort(x, this.from, this.to);
/*      */         
/*      */         return;
/*      */       } 
/*  818 */       long m = this.from + len / 2L;
/*  819 */       long l = this.from;
/*  820 */       long n = this.to - 1L;
/*  821 */       long s = len / 8L;
/*  822 */       l = ShortBigArrays.med3(x, l, l + s, l + 2L * s);
/*  823 */       m = ShortBigArrays.med3(x, m - s, m, m + s);
/*  824 */       n = ShortBigArrays.med3(x, n - 2L * s, n - s, n);
/*  825 */       m = ShortBigArrays.med3(x, l, m, n);
/*  826 */       short v = BigArrays.get(x, m);
/*      */       
/*  828 */       long a = this.from, b = a, c = this.to - 1L, d = c;
/*      */       
/*      */       int comparison;
/*  831 */       while (b <= c && (comparison = Short.compare(BigArrays.get(x, b), v)) <= 0) {
/*  832 */         if (comparison == 0) BigArrays.swap(x, a++, b); 
/*  833 */         b++;
/*      */       } 
/*  835 */       while (c >= b && (comparison = Short.compare(BigArrays.get(x, c), v)) >= 0) {
/*  836 */         if (comparison == 0) BigArrays.swap(x, c, d--); 
/*  837 */         c--;
/*      */       } 
/*  839 */       if (b > c) {
/*      */ 
/*      */ 
/*      */ 
/*      */         
/*  844 */         s = Math.min(a - this.from, b - a);
/*  845 */         ShortBigArrays.swap(x, this.from, b - s, s);
/*  846 */         s = Math.min(d - c, this.to - d - 1L);
/*  847 */         ShortBigArrays.swap(x, b, this.to - s, s);
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
/*      */   public static void parallelQuickSort(short[][] x, long from, long to) {
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
/*      */   public static void parallelQuickSort(short[][] x) {
/*  889 */     parallelQuickSort(x, 0L, BigArrays.length(x));
/*      */   }
/*      */   
/*      */   protected static class ForkJoinQuickSortComp extends RecursiveAction {
/*      */     private static final long serialVersionUID = 1L;
/*      */     private final long from;
/*      */     private final long to;
/*      */     private final short[][] x;
/*      */     private final ShortComparator comp;
/*      */     
/*      */     public ForkJoinQuickSortComp(short[][] x, long from, long to, ShortComparator comp) {
/*  900 */       this.from = from;
/*  901 */       this.to = to;
/*  902 */       this.x = x;
/*  903 */       this.comp = comp;
/*      */     }
/*      */ 
/*      */     
/*      */     protected void compute() {
/*  908 */       short[][] x = this.x;
/*  909 */       long len = this.to - this.from;
/*  910 */       if (len < 8192L) {
/*  911 */         ShortBigArrays.quickSort(x, this.from, this.to, this.comp);
/*      */         
/*      */         return;
/*      */       } 
/*  915 */       long m = this.from + len / 2L;
/*  916 */       long l = this.from;
/*  917 */       long n = this.to - 1L;
/*  918 */       long s = len / 8L;
/*  919 */       l = ShortBigArrays.med3(x, l, l + s, l + 2L * s, this.comp);
/*  920 */       m = ShortBigArrays.med3(x, m - s, m, m + s, this.comp);
/*  921 */       n = ShortBigArrays.med3(x, n - 2L * s, n - s, n, this.comp);
/*  922 */       m = ShortBigArrays.med3(x, l, m, n, this.comp);
/*  923 */       short v = BigArrays.get(x, m);
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
/*  942 */         ShortBigArrays.swap(x, this.from, b - s, s);
/*  943 */         s = Math.min(d - c, this.to - d - 1L);
/*  944 */         ShortBigArrays.swap(x, b, this.to - s, s);
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
/*      */   public static void parallelQuickSort(short[][] x, long from, long to, ShortComparator comp) {
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
/*      */   public static void parallelQuickSort(short[][] x, ShortComparator comp) {
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
/*      */   public static long binarySearch(short[][] a, long from, long to, short key) {
/* 1013 */     to--;
/* 1014 */     while (from <= to) {
/* 1015 */       long mid = from + to >>> 1L;
/* 1016 */       short midVal = BigArrays.get(a, mid);
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
/*      */   public static long binarySearch(short[][] a, short key) {
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
/*      */   public static long binarySearch(short[][] a, long from, long to, short key, ShortComparator c) {
/* 1065 */     to--;
/* 1066 */     while (from <= to) {
/* 1067 */       long mid = from + to >>> 1L;
/* 1068 */       short midVal = BigArrays.get(a, mid);
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
/*      */   public static long binarySearch(short[][] a, short key, ShortComparator c) {
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
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static void radixSort(short[][] a) {
/* 1127 */     radixSort(a, 0L, BigArrays.length(a));
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
/*      */   public static void radixSort(short[][] a, long from, long to) {
/* 1150 */     int maxLevel = 1;
/* 1151 */     int stackSize = 256;
/* 1152 */     long[] offsetStack = new long[256];
/* 1153 */     int offsetPos = 0;
/* 1154 */     long[] lengthStack = new long[256];
/* 1155 */     int lengthPos = 0;
/* 1156 */     int[] levelStack = new int[256];
/* 1157 */     int levelPos = 0;
/* 1158 */     offsetStack[offsetPos++] = from;
/* 1159 */     lengthStack[lengthPos++] = to - from;
/* 1160 */     levelStack[levelPos++] = 0;
/* 1161 */     long[] count = new long[256];
/* 1162 */     long[] pos = new long[256];
/* 1163 */     byte[][] digit = ByteBigArrays.newBigArray(to - from);
/* 1164 */     while (offsetPos > 0) {
/* 1165 */       long first = offsetStack[--offsetPos];
/* 1166 */       long length = lengthStack[--lengthPos];
/* 1167 */       int level = levelStack[--levelPos];
/* 1168 */       int signMask = (level % 2 == 0) ? 128 : 0;
/* 1169 */       if (length < 40L) {
/* 1170 */         selectionSort(a, first, first + length);
/*      */         continue;
/*      */       } 
/* 1173 */       int shift = (1 - level % 2) * 8;
/*      */ 
/*      */       
/*      */       long i;
/*      */       
/* 1178 */       for (i = length; i-- != 0L; BigArrays.set(digit, i, (byte)(BigArrays.get(a, first + i) >>> shift & 0xFF ^ signMask)));
/* 1179 */       for (i = length; i-- != 0L; count[BigArrays.get(digit, i) & 0xFF] = count[BigArrays.get(digit, i) & 0xFF] + 1L);
/*      */       
/* 1181 */       int lastUsed = -1;
/* 1182 */       long p = 0L;
/* 1183 */       for (int j = 0; j < 256; j++) {
/* 1184 */         if (count[j] != 0L) {
/* 1185 */           lastUsed = j;
/* 1186 */           if (level < 1 && count[j] > 1L) {
/*      */ 
/*      */             
/* 1189 */             offsetStack[offsetPos++] = p + first;
/* 1190 */             lengthStack[lengthPos++] = count[j];
/* 1191 */             levelStack[levelPos++] = level + 1;
/*      */           } 
/*      */         } 
/* 1194 */         pos[j] = p += count[j];
/*      */       } 
/*      */       
/* 1197 */       long end = length - count[lastUsed];
/* 1198 */       count[lastUsed] = 0L;
/*      */       
/* 1200 */       int c = -1;
/* 1201 */       for (long l1 = 0L; l1 < end; l1 += count[c], count[c] = 0L) {
/* 1202 */         short t = BigArrays.get(a, l1 + first);
/* 1203 */         c = BigArrays.get(digit, l1) & 0xFF; long d;
/* 1204 */         for (pos[c] = pos[c] - 1L; (d = pos[c] - 1L) > l1; ) {
/* 1205 */           short z = t;
/* 1206 */           int zz = c;
/* 1207 */           t = BigArrays.get(a, d + first);
/* 1208 */           c = BigArrays.get(digit, d) & 0xFF;
/* 1209 */           BigArrays.set(a, d + first, z);
/* 1210 */           BigArrays.set(digit, d, (byte)zz);
/*      */         } 
/* 1212 */         BigArrays.set(a, l1 + first, t);
/*      */       } 
/*      */     } 
/*      */   }
/*      */   private static void selectionSort(short[][] a, short[][] b, long from, long to) {
/*      */     long i;
/* 1218 */     for (i = from; i < to - 1L; i++) {
/* 1219 */       long m = i; long j;
/* 1220 */       for (j = i + 1L; j < to; ) { if (BigArrays.get(a, j) < BigArrays.get(a, m) || (BigArrays.get(a, j) == BigArrays.get(a, m) && BigArrays.get(b, j) < BigArrays.get(b, m))) m = j;  j++; }
/* 1221 */        if (m != i) {
/* 1222 */         short t = BigArrays.get(a, i);
/* 1223 */         BigArrays.set(a, i, BigArrays.get(a, m));
/* 1224 */         BigArrays.set(a, m, t);
/* 1225 */         t = BigArrays.get(b, i);
/* 1226 */         BigArrays.set(b, i, BigArrays.get(b, m));
/* 1227 */         BigArrays.set(b, m, t);
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
/*      */   public static void radixSort(short[][] a, short[][] b) {
/* 1256 */     radixSort(a, b, 0L, BigArrays.length(a));
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
/*      */   public static void radixSort(short[][] a, short[][] b, long from, long to) {
/* 1286 */     int layers = 2;
/* 1287 */     if (BigArrays.length(a) != BigArrays.length(b)) throw new IllegalArgumentException("Array size mismatch."); 
/* 1288 */     int maxLevel = 3;
/* 1289 */     int stackSize = 766;
/* 1290 */     long[] offsetStack = new long[766];
/* 1291 */     int offsetPos = 0;
/* 1292 */     long[] lengthStack = new long[766];
/* 1293 */     int lengthPos = 0;
/* 1294 */     int[] levelStack = new int[766];
/* 1295 */     int levelPos = 0;
/* 1296 */     offsetStack[offsetPos++] = from;
/* 1297 */     lengthStack[lengthPos++] = to - from;
/* 1298 */     levelStack[levelPos++] = 0;
/* 1299 */     long[] count = new long[256];
/* 1300 */     long[] pos = new long[256];
/* 1301 */     byte[][] digit = ByteBigArrays.newBigArray(to - from);
/* 1302 */     while (offsetPos > 0) {
/* 1303 */       long first = offsetStack[--offsetPos];
/* 1304 */       long length = lengthStack[--lengthPos];
/* 1305 */       int level = levelStack[--levelPos];
/* 1306 */       int signMask = (level % 2 == 0) ? 128 : 0;
/* 1307 */       if (length < 40L) {
/* 1308 */         selectionSort(a, b, first, first + length);
/*      */         continue;
/*      */       } 
/* 1311 */       short[][] k = (level < 2) ? a : b;
/* 1312 */       int shift = (1 - level % 2) * 8;
/*      */ 
/*      */       
/*      */       long i;
/*      */       
/* 1317 */       for (i = length; i-- != 0L; BigArrays.set(digit, i, (byte)(BigArrays.get(k, first + i) >>> shift & 0xFF ^ signMask)));
/* 1318 */       for (i = length; i-- != 0L; count[BigArrays.get(digit, i) & 0xFF] = count[BigArrays.get(digit, i) & 0xFF] + 1L);
/*      */       
/* 1320 */       int lastUsed = -1;
/* 1321 */       long p = 0L;
/* 1322 */       for (int j = 0; j < 256; j++) {
/* 1323 */         if (count[j] != 0L) {
/* 1324 */           lastUsed = j;
/* 1325 */           if (level < 3 && count[j] > 1L) {
/* 1326 */             offsetStack[offsetPos++] = p + first;
/* 1327 */             lengthStack[lengthPos++] = count[j];
/* 1328 */             levelStack[levelPos++] = level + 1;
/*      */           } 
/*      */         } 
/* 1331 */         pos[j] = p += count[j];
/*      */       } 
/*      */       
/* 1334 */       long end = length - count[lastUsed];
/* 1335 */       count[lastUsed] = 0L;
/*      */       
/* 1337 */       int c = -1;
/* 1338 */       for (long l1 = 0L; l1 < end; l1 += count[c], count[c] = 0L) {
/* 1339 */         short t = BigArrays.get(a, l1 + first);
/* 1340 */         short u = BigArrays.get(b, l1 + first);
/* 1341 */         c = BigArrays.get(digit, l1) & 0xFF; long d;
/* 1342 */         for (pos[c] = pos[c] - 1L; (d = pos[c] - 1L) > l1; ) {
/* 1343 */           short z = t;
/* 1344 */           int zz = c;
/* 1345 */           t = BigArrays.get(a, d + first);
/* 1346 */           BigArrays.set(a, d + first, z);
/* 1347 */           z = u;
/* 1348 */           u = BigArrays.get(b, d + first);
/* 1349 */           BigArrays.set(b, d + first, z);
/* 1350 */           c = BigArrays.get(digit, d) & 0xFF;
/* 1351 */           BigArrays.set(digit, d, (byte)zz);
/*      */         } 
/* 1353 */         BigArrays.set(a, l1 + first, t);
/* 1354 */         BigArrays.set(b, l1 + first, u);
/*      */       } 
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   private static void insertionSortIndirect(long[][] perm, short[][] a, short[][] b, long from, long to) {
/* 1362 */     long i = from; while (true) { long t, j, u; if (++i < to)
/* 1363 */       { t = BigArrays.get(perm, i);
/* 1364 */         j = i;
/* 1365 */         u = BigArrays.get(perm, j - 1L); } else { break; }  if (BigArrays.get(a, t) < BigArrays.get(a, u) || (BigArrays.get(a, t) == BigArrays.get(a, u) && BigArrays.get(b, t) < BigArrays.get(b, u)));
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/* 1372 */       BigArrays.set(perm, j, t); }
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
/*      */   public static void radixSortIndirect(long[][] perm, short[][] a, short[][] b, boolean stable) {
/* 1399 */     ensureSameLength(a, b);
/* 1400 */     radixSortIndirect(perm, a, b, 0L, BigArrays.length(a), stable);
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
/*      */   public static void radixSortIndirect(long[][] perm, short[][] a, short[][] b, long from, long to, boolean stable) {
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
/*      */     //   17: invokestatic insertionSortIndirect : ([[J[[S[[SJJ)V
/*      */     //   20: return
/*      */     //   21: iconst_2
/*      */     //   22: istore #8
/*      */     //   24: iconst_3
/*      */     //   25: istore #9
/*      */     //   27: sipush #766
/*      */     //   30: istore #10
/*      */     //   32: iconst_0
/*      */     //   33: istore #11
/*      */     //   35: sipush #766
/*      */     //   38: newarray long
/*      */     //   40: astore #12
/*      */     //   42: sipush #766
/*      */     //   45: newarray long
/*      */     //   47: astore #13
/*      */     //   49: sipush #766
/*      */     //   52: newarray int
/*      */     //   54: astore #14
/*      */     //   56: aload #12
/*      */     //   58: iload #11
/*      */     //   60: lload_3
/*      */     //   61: lastore
/*      */     //   62: aload #13
/*      */     //   64: iload #11
/*      */     //   66: lload #5
/*      */     //   68: lload_3
/*      */     //   69: lsub
/*      */     //   70: lastore
/*      */     //   71: aload #14
/*      */     //   73: iload #11
/*      */     //   75: iinc #11, 1
/*      */     //   78: iconst_0
/*      */     //   79: iastore
/*      */     //   80: sipush #256
/*      */     //   83: newarray long
/*      */     //   85: astore #15
/*      */     //   87: sipush #256
/*      */     //   90: newarray long
/*      */     //   92: astore #16
/*      */     //   94: iload #7
/*      */     //   96: ifeq -> 109
/*      */     //   99: aload_0
/*      */     //   100: invokestatic length : ([[J)J
/*      */     //   103: invokestatic newBigArray : (J)[[J
/*      */     //   106: goto -> 110
/*      */     //   109: aconst_null
/*      */     //   110: astore #17
/*      */     //   112: iload #11
/*      */     //   114: ifle -> 720
/*      */     //   117: aload #12
/*      */     //   119: iinc #11, -1
/*      */     //   122: iload #11
/*      */     //   124: laload
/*      */     //   125: lstore #18
/*      */     //   127: aload #13
/*      */     //   129: iload #11
/*      */     //   131: laload
/*      */     //   132: lstore #20
/*      */     //   134: aload #14
/*      */     //   136: iload #11
/*      */     //   138: iaload
/*      */     //   139: istore #22
/*      */     //   141: iload #22
/*      */     //   143: iconst_2
/*      */     //   144: irem
/*      */     //   145: ifne -> 154
/*      */     //   148: sipush #128
/*      */     //   151: goto -> 155
/*      */     //   154: iconst_0
/*      */     //   155: istore #23
/*      */     //   157: iload #22
/*      */     //   159: iconst_2
/*      */     //   160: if_icmpge -> 167
/*      */     //   163: aload_1
/*      */     //   164: goto -> 168
/*      */     //   167: aload_2
/*      */     //   168: astore #24
/*      */     //   170: iconst_1
/*      */     //   171: iload #22
/*      */     //   173: iconst_2
/*      */     //   174: irem
/*      */     //   175: isub
/*      */     //   176: bipush #8
/*      */     //   178: imul
/*      */     //   179: istore #25
/*      */     //   181: lload #18
/*      */     //   183: lload #20
/*      */     //   185: ladd
/*      */     //   186: lstore #26
/*      */     //   188: lload #26
/*      */     //   190: dup2
/*      */     //   191: lconst_1
/*      */     //   192: lsub
/*      */     //   193: lstore #26
/*      */     //   195: lload #18
/*      */     //   197: lcmp
/*      */     //   198: ifeq -> 232
/*      */     //   201: aload #15
/*      */     //   203: aload #24
/*      */     //   205: aload_0
/*      */     //   206: lload #26
/*      */     //   208: invokestatic get : ([[JJ)J
/*      */     //   211: invokestatic get : ([[SJ)S
/*      */     //   214: iload #25
/*      */     //   216: iushr
/*      */     //   217: sipush #255
/*      */     //   220: iand
/*      */     //   221: iload #23
/*      */     //   223: ixor
/*      */     //   224: dup2
/*      */     //   225: laload
/*      */     //   226: lconst_1
/*      */     //   227: ladd
/*      */     //   228: lastore
/*      */     //   229: goto -> 188
/*      */     //   232: iconst_m1
/*      */     //   233: istore #26
/*      */     //   235: iload #7
/*      */     //   237: ifeq -> 244
/*      */     //   240: lconst_0
/*      */     //   241: goto -> 246
/*      */     //   244: lload #18
/*      */     //   246: lstore #27
/*      */     //   248: iconst_0
/*      */     //   249: istore #29
/*      */     //   251: iload #29
/*      */     //   253: sipush #256
/*      */     //   256: if_icmpge -> 295
/*      */     //   259: aload #15
/*      */     //   261: iload #29
/*      */     //   263: laload
/*      */     //   264: lconst_0
/*      */     //   265: lcmp
/*      */     //   266: ifeq -> 273
/*      */     //   269: iload #29
/*      */     //   271: istore #26
/*      */     //   273: aload #16
/*      */     //   275: iload #29
/*      */     //   277: lload #27
/*      */     //   279: aload #15
/*      */     //   281: iload #29
/*      */     //   283: laload
/*      */     //   284: ladd
/*      */     //   285: dup2
/*      */     //   286: lstore #27
/*      */     //   288: lastore
/*      */     //   289: iinc #29, 1
/*      */     //   292: goto -> 251
/*      */     //   295: iload #7
/*      */     //   297: ifeq -> 490
/*      */     //   300: lload #18
/*      */     //   302: lload #20
/*      */     //   304: ladd
/*      */     //   305: lstore #29
/*      */     //   307: lload #29
/*      */     //   309: dup2
/*      */     //   310: lconst_1
/*      */     //   311: lsub
/*      */     //   312: lstore #29
/*      */     //   314: lload #18
/*      */     //   316: lcmp
/*      */     //   317: ifeq -> 363
/*      */     //   320: aload #17
/*      */     //   322: aload #16
/*      */     //   324: aload #24
/*      */     //   326: aload_0
/*      */     //   327: lload #29
/*      */     //   329: invokestatic get : ([[JJ)J
/*      */     //   332: invokestatic get : ([[SJ)S
/*      */     //   335: iload #25
/*      */     //   337: iushr
/*      */     //   338: sipush #255
/*      */     //   341: iand
/*      */     //   342: iload #23
/*      */     //   344: ixor
/*      */     //   345: dup2
/*      */     //   346: laload
/*      */     //   347: lconst_1
/*      */     //   348: lsub
/*      */     //   349: dup2_x2
/*      */     //   350: lastore
/*      */     //   351: aload_0
/*      */     //   352: lload #29
/*      */     //   354: invokestatic get : ([[JJ)J
/*      */     //   357: invokestatic set : ([[JJJ)V
/*      */     //   360: goto -> 307
/*      */     //   363: aload #17
/*      */     //   365: lconst_0
/*      */     //   366: aload_0
/*      */     //   367: lload #18
/*      */     //   369: lload #20
/*      */     //   371: invokestatic copy : ([[JJ[[JJJ)V
/*      */     //   374: lload #18
/*      */     //   376: lstore #27
/*      */     //   378: iconst_0
/*      */     //   379: istore #29
/*      */     //   381: iload #29
/*      */     //   383: sipush #256
/*      */     //   386: if_icmpge -> 481
/*      */     //   389: iload #22
/*      */     //   391: iconst_3
/*      */     //   392: if_icmpge -> 465
/*      */     //   395: aload #15
/*      */     //   397: iload #29
/*      */     //   399: laload
/*      */     //   400: lconst_1
/*      */     //   401: lcmp
/*      */     //   402: ifle -> 465
/*      */     //   405: aload #15
/*      */     //   407: iload #29
/*      */     //   409: laload
/*      */     //   410: ldc2_w 1024
/*      */     //   413: lcmp
/*      */     //   414: ifge -> 436
/*      */     //   417: aload_0
/*      */     //   418: aload_1
/*      */     //   419: aload_2
/*      */     //   420: lload #27
/*      */     //   422: lload #27
/*      */     //   424: aload #15
/*      */     //   426: iload #29
/*      */     //   428: laload
/*      */     //   429: ladd
/*      */     //   430: invokestatic insertionSortIndirect : ([[J[[S[[SJJ)V
/*      */     //   433: goto -> 465
/*      */     //   436: aload #12
/*      */     //   438: iload #11
/*      */     //   440: lload #27
/*      */     //   442: lastore
/*      */     //   443: aload #13
/*      */     //   445: iload #11
/*      */     //   447: aload #15
/*      */     //   449: iload #29
/*      */     //   451: laload
/*      */     //   452: lastore
/*      */     //   453: aload #14
/*      */     //   455: iload #11
/*      */     //   457: iinc #11, 1
/*      */     //   460: iload #22
/*      */     //   462: iconst_1
/*      */     //   463: iadd
/*      */     //   464: iastore
/*      */     //   465: lload #27
/*      */     //   467: aload #15
/*      */     //   469: iload #29
/*      */     //   471: laload
/*      */     //   472: ladd
/*      */     //   473: lstore #27
/*      */     //   475: iinc #29, 1
/*      */     //   478: goto -> 381
/*      */     //   481: aload #15
/*      */     //   483: lconst_0
/*      */     //   484: invokestatic fill : ([JJ)V
/*      */     //   487: goto -> 717
/*      */     //   490: lload #18
/*      */     //   492: lload #20
/*      */     //   494: ladd
/*      */     //   495: aload #15
/*      */     //   497: iload #26
/*      */     //   499: laload
/*      */     //   500: lsub
/*      */     //   501: lstore #29
/*      */     //   503: iconst_m1
/*      */     //   504: istore #31
/*      */     //   506: lload #18
/*      */     //   508: lstore #32
/*      */     //   510: lload #32
/*      */     //   512: lload #29
/*      */     //   514: lcmp
/*      */     //   515: ifgt -> 717
/*      */     //   518: aload_0
/*      */     //   519: lload #32
/*      */     //   521: invokestatic get : ([[JJ)J
/*      */     //   524: lstore #36
/*      */     //   526: aload #24
/*      */     //   528: lload #36
/*      */     //   530: invokestatic get : ([[SJ)S
/*      */     //   533: iload #25
/*      */     //   535: iushr
/*      */     //   536: sipush #255
/*      */     //   539: iand
/*      */     //   540: iload #23
/*      */     //   542: ixor
/*      */     //   543: istore #31
/*      */     //   545: lload #32
/*      */     //   547: lload #29
/*      */     //   549: lcmp
/*      */     //   550: ifge -> 622
/*      */     //   553: aload #16
/*      */     //   555: iload #31
/*      */     //   557: dup2
/*      */     //   558: laload
/*      */     //   559: lconst_1
/*      */     //   560: lsub
/*      */     //   561: dup2_x2
/*      */     //   562: lastore
/*      */     //   563: dup2
/*      */     //   564: lstore #34
/*      */     //   566: lload #32
/*      */     //   568: lcmp
/*      */     //   569: ifle -> 614
/*      */     //   572: lload #36
/*      */     //   574: lstore #38
/*      */     //   576: aload_0
/*      */     //   577: lload #34
/*      */     //   579: invokestatic get : ([[JJ)J
/*      */     //   582: lstore #36
/*      */     //   584: aload_0
/*      */     //   585: lload #34
/*      */     //   587: lload #38
/*      */     //   589: invokestatic set : ([[JJJ)V
/*      */     //   592: aload #24
/*      */     //   594: lload #36
/*      */     //   596: invokestatic get : ([[SJ)S
/*      */     //   599: iload #25
/*      */     //   601: iushr
/*      */     //   602: sipush #255
/*      */     //   605: iand
/*      */     //   606: iload #23
/*      */     //   608: ixor
/*      */     //   609: istore #31
/*      */     //   611: goto -> 553
/*      */     //   614: aload_0
/*      */     //   615: lload #32
/*      */     //   617: lload #36
/*      */     //   619: invokestatic set : ([[JJJ)V
/*      */     //   622: iload #22
/*      */     //   624: iconst_3
/*      */     //   625: if_icmpge -> 698
/*      */     //   628: aload #15
/*      */     //   630: iload #31
/*      */     //   632: laload
/*      */     //   633: lconst_1
/*      */     //   634: lcmp
/*      */     //   635: ifle -> 698
/*      */     //   638: aload #15
/*      */     //   640: iload #31
/*      */     //   642: laload
/*      */     //   643: ldc2_w 1024
/*      */     //   646: lcmp
/*      */     //   647: ifge -> 669
/*      */     //   650: aload_0
/*      */     //   651: aload_1
/*      */     //   652: aload_2
/*      */     //   653: lload #32
/*      */     //   655: lload #32
/*      */     //   657: aload #15
/*      */     //   659: iload #31
/*      */     //   661: laload
/*      */     //   662: ladd
/*      */     //   663: invokestatic insertionSortIndirect : ([[J[[S[[SJJ)V
/*      */     //   666: goto -> 698
/*      */     //   669: aload #12
/*      */     //   671: iload #11
/*      */     //   673: lload #32
/*      */     //   675: lastore
/*      */     //   676: aload #13
/*      */     //   678: iload #11
/*      */     //   680: aload #15
/*      */     //   682: iload #31
/*      */     //   684: laload
/*      */     //   685: lastore
/*      */     //   686: aload #14
/*      */     //   688: iload #11
/*      */     //   690: iinc #11, 1
/*      */     //   693: iload #22
/*      */     //   695: iconst_1
/*      */     //   696: iadd
/*      */     //   697: iastore
/*      */     //   698: lload #32
/*      */     //   700: aload #15
/*      */     //   702: iload #31
/*      */     //   704: laload
/*      */     //   705: ladd
/*      */     //   706: lstore #32
/*      */     //   708: aload #15
/*      */     //   710: iload #31
/*      */     //   712: lconst_0
/*      */     //   713: lastore
/*      */     //   714: goto -> 510
/*      */     //   717: goto -> 112
/*      */     //   720: return
/*      */     // Line number table:
/*      */     //   Java source line number -> byte code offset
/*      */     //   #1428	-> 0
/*      */     //   #1429	-> 11
/*      */     //   #1430	-> 20
/*      */     //   #1432	-> 21
/*      */     //   #1433	-> 24
/*      */     //   #1434	-> 27
/*      */     //   #1435	-> 32
/*      */     //   #1436	-> 35
/*      */     //   #1437	-> 42
/*      */     //   #1438	-> 49
/*      */     //   #1439	-> 56
/*      */     //   #1440	-> 62
/*      */     //   #1441	-> 71
/*      */     //   #1442	-> 80
/*      */     //   #1443	-> 87
/*      */     //   #1444	-> 94
/*      */     //   #1445	-> 112
/*      */     //   #1446	-> 117
/*      */     //   #1447	-> 127
/*      */     //   #1448	-> 134
/*      */     //   #1449	-> 141
/*      */     //   #1450	-> 157
/*      */     //   #1451	-> 170
/*      */     //   #1456	-> 181
/*      */     //   #1458	-> 232
/*      */     //   #1459	-> 235
/*      */     //   #1460	-> 248
/*      */     //   #1461	-> 259
/*      */     //   #1462	-> 273
/*      */     //   #1460	-> 289
/*      */     //   #1464	-> 295
/*      */     //   #1465	-> 300
/*      */     //   #1466	-> 363
/*      */     //   #1467	-> 374
/*      */     //   #1468	-> 378
/*      */     //   #1469	-> 389
/*      */     //   #1470	-> 405
/*      */     //   #1472	-> 436
/*      */     //   #1473	-> 443
/*      */     //   #1474	-> 453
/*      */     //   #1477	-> 465
/*      */     //   #1468	-> 475
/*      */     //   #1479	-> 481
/*      */     //   #1481	-> 490
/*      */     //   #1483	-> 503
/*      */     //   #1484	-> 506
/*      */     //   #1485	-> 518
/*      */     //   #1486	-> 526
/*      */     //   #1487	-> 545
/*      */     //   #1488	-> 553
/*      */     //   #1489	-> 572
/*      */     //   #1490	-> 576
/*      */     //   #1491	-> 584
/*      */     //   #1492	-> 592
/*      */     //   #1493	-> 611
/*      */     //   #1494	-> 614
/*      */     //   #1496	-> 622
/*      */     //   #1497	-> 638
/*      */     //   #1499	-> 669
/*      */     //   #1500	-> 676
/*      */     //   #1501	-> 686
/*      */     //   #1484	-> 698
/*      */     //   #1506	-> 717
/*      */     //   #1507	-> 720
/*      */     // Local variable table:
/*      */     //   start	length	slot	name	descriptor
/*      */     //   188	44	26	i	J
/*      */     //   251	44	29	i	I
/*      */     //   307	56	29	i	J
/*      */     //   381	100	29	i	I
/*      */     //   576	35	38	z	J
/*      */     //   566	56	34	d	J
/*      */     //   526	172	36	t	J
/*      */     //   510	207	32	i	J
/*      */     //   503	214	29	end	J
/*      */     //   506	211	31	c	I
/*      */     //   127	590	18	first	J
/*      */     //   134	583	20	length	J
/*      */     //   141	576	22	level	I
/*      */     //   157	560	23	signMask	I
/*      */     //   170	547	24	k	[[S
/*      */     //   181	536	25	shift	I
/*      */     //   235	482	26	lastUsed	I
/*      */     //   248	469	27	p	J
/*      */     //   0	721	0	perm	[[J
/*      */     //   0	721	1	a	[[S
/*      */     //   0	721	2	b	[[S
/*      */     //   0	721	3	from	J
/*      */     //   0	721	5	to	J
/*      */     //   0	721	7	stable	Z
/*      */     //   24	697	8	layers	I
/*      */     //   27	694	9	maxLevel	I
/*      */     //   32	689	10	stackSize	I
/*      */     //   35	686	11	stackPos	I
/*      */     //   42	679	12	offsetStack	[J
/*      */     //   49	672	13	lengthStack	[J
/*      */     //   56	665	14	levelStack	[I
/*      */     //   87	634	15	count	[J
/*      */     //   94	627	16	pos	[J
/*      */     //   112	609	17	support	[[J
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
/*      */   public static short[][] shuffle(short[][] a, long from, long to, Random random) {
/* 1519 */     return BigArrays.shuffle(a, from, to, random);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static short[][] shuffle(short[][] a, Random random) {
/* 1530 */     return BigArrays.shuffle(a, random);
/*      */   }
/*      */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\i\\unimi\dsi\fastutil\shorts\ShortBigArrays.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */