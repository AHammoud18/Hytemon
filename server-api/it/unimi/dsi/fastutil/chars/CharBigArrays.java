/*      */ package it.unimi.dsi.fastutil.chars;
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
/*      */ public final class CharBigArrays
/*      */ {
/*   70 */   public static final char[][] EMPTY_BIG_ARRAY = new char[0][];
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*   78 */   public static final char[][] DEFAULT_EMPTY_BIG_ARRAY = new char[0][];
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
/*      */   public static char get(char[][] array, long index) {
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
/*      */   public static void set(char[][] array, long index, char value) {
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
/*      */   public static void swap(char[][] array, long first, long second) {
/*  116 */     char t = array[BigArrays.segment(first)][BigArrays.displacement(first)];
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
/*      */   public static void add(char[][] array, long index, char incr) {
/*  131 */     array[BigArrays.segment(index)][BigArrays.displacement(index)] = (char)(array[BigArrays.segment(index)][BigArrays.displacement(index)] + incr);
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
/*      */   public static void mul(char[][] array, long index, char factor) {
/*  144 */     array[BigArrays.segment(index)][BigArrays.displacement(index)] = (char)(array[BigArrays.segment(index)][BigArrays.displacement(index)] * factor);
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
/*      */   public static void incr(char[][] array, long index) {
/*  156 */     array[BigArrays.segment(index)][BigArrays.displacement(index)] = (char)(array[BigArrays.segment(index)][BigArrays.displacement(index)] + 1);
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
/*      */   public static void decr(char[][] array, long index) {
/*  168 */     array[BigArrays.segment(index)][BigArrays.displacement(index)] = (char)(array[BigArrays.segment(index)][BigArrays.displacement(index)] - 1);
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
/*      */   public static long length(char[][] array) {
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
/*      */   public static void copy(char[][] srcArray, long srcPos, char[][] destArray, long destPos, long length) {
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
/*      */   public static void copyFromBig(char[][] srcArray, long srcPos, char[] destArray, int destPos, int length) {
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
/*      */   public static void copyToBig(char[] srcArray, int srcPos, char[][] destArray, long destPos, long length) {
/*  230 */     BigArrays.copyToBig(srcArray, srcPos, destArray, destPos, length);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static char[][] newBigArray(long length) {
/*  240 */     if (length == 0L) return EMPTY_BIG_ARRAY; 
/*  241 */     BigArrays.ensureLength(length);
/*  242 */     int baseLength = (int)(length + 134217727L >>> 27L);
/*  243 */     char[][] base = new char[baseLength][];
/*  244 */     int residual = (int)(length & 0x7FFFFFFL);
/*  245 */     if (residual != 0)
/*  246 */     { for (int i = 0; i < baseLength - 1; ) { base[i] = new char[134217728]; i++; }
/*  247 */        base[baseLength - 1] = new char[residual]; }
/*  248 */     else { for (int i = 0; i < baseLength; ) { base[i] = new char[134217728]; i++; }  }
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
/*      */   public static char[][] wrap(char[] array) {
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
/*      */   public static char[][] ensureCapacity(char[][] array, long length) {
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
/*      */   public static char[][] forceCapacity(char[][] array, long length, long preserve) {
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
/*      */   public static char[][] ensureCapacity(char[][] array, long length, long preserve) {
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
/*      */   public static char[][] grow(char[][] array, long length) {
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
/*      */   public static char[][] grow(char[][] array, long length, long preserve) {
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
/*      */   public static char[][] trim(char[][] array, long length) {
/*  402 */     BigArrays.ensureLength(length);
/*  403 */     long oldLength = length(array);
/*  404 */     if (length >= oldLength) return array; 
/*  405 */     int baseLength = (int)(length + 134217727L >>> 27L);
/*  406 */     char[][] base = Arrays.<char[]>copyOf(array, baseLength);
/*  407 */     int residual = (int)(length & 0x7FFFFFFL);
/*  408 */     if (residual != 0) base[baseLength - 1] = CharArrays.trim(base[baseLength - 1], residual); 
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
/*      */   public static char[][] setLength(char[][] array, long length) {
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
/*      */   public static char[][] copy(char[][] array, long offset, long length) {
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
/*      */   public static char[][] copy(char[][] array) {
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
/*      */   public static void fill(char[][] array, char value) {
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
/*      */   public static void fill(char[][] array, long from, long to, char value) {
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
/*      */   public static boolean equals(char[][] a1, char[][] a2) {
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
/*      */   public static String toString(char[][] a) {
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
/*      */   public static void ensureFromTo(char[][] a, long from, long to) {
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
/*      */   public static void ensureOffsetLength(char[][] a, long offset, long length) {
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
/*      */   public static void ensureSameLength(char[][] a, char[][] b) {
/*  572 */     if (length(a) != length(b)) throw new IllegalArgumentException("Array size mismatch: " + length(a) + " != " + length(b)); 
/*      */   }
/*      */   
/*      */   private static final class BigArrayHashStrategy implements Hash.Strategy<char[][]>, Serializable {
/*      */     private static final long serialVersionUID = -7046029254386353129L;
/*      */     
/*      */     private BigArrayHashStrategy() {}
/*      */     
/*      */     public int hashCode(char[][] o) {
/*  581 */       return Arrays.deepHashCode((Object[])o);
/*      */     }
/*      */ 
/*      */     
/*      */     public boolean equals(char[][] a, char[][] b) {
/*  586 */       return CharBigArrays.equals(a, b);
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
/*      */   private static void swap(char[][] x, long a, long b, long n) {
/*  611 */     for (int i = 0; i < n; ) { BigArrays.swap(x, a, b); i++; a++; b++; }
/*      */   
/*      */   }
/*      */   private static long med3(char[][] x, long a, long b, long c, CharComparator comp) {
/*  615 */     int ab = comp.compare(BigArrays.get(x, a), BigArrays.get(x, b));
/*  616 */     int ac = comp.compare(BigArrays.get(x, a), BigArrays.get(x, c));
/*  617 */     int bc = comp.compare(BigArrays.get(x, b), BigArrays.get(x, c));
/*  618 */     return (ab < 0) ? ((bc < 0) ? b : ((ac < 0) ? c : a)) : ((bc > 0) ? b : ((ac > 0) ? c : a));
/*      */   }
/*      */   private static void selectionSort(char[][] a, long from, long to, CharComparator comp) {
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
/*      */   public static void quickSort(char[][] x, long from, long to, CharComparator comp) {
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
/*  663 */     char v = BigArrays.get(x, m);
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
/*      */     BigArrays.swap(x, b++, c--); } private static long med3(char[][] x, long a, long b, long c) {
/*  691 */     int ab = Character.compare(BigArrays.get(x, a), BigArrays.get(x, b));
/*  692 */     int ac = Character.compare(BigArrays.get(x, a), BigArrays.get(x, c));
/*  693 */     int bc = Character.compare(BigArrays.get(x, b), BigArrays.get(x, c));
/*  694 */     return (ab < 0) ? ((bc < 0) ? b : ((ac < 0) ? c : a)) : ((bc > 0) ? b : ((ac > 0) ? c : a));
/*      */   }
/*      */   private static void selectionSort(char[][] a, long from, long to) {
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
/*      */   public static void quickSort(char[][] x, CharComparator comp) {
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
/*      */   public static void quickSort(char[][] x, long from, long to) {
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
/*  755 */     char v = BigArrays.get(x, m);
/*      */     
/*  757 */     long a = from, b = a, c = to - 1L, d = c;
/*      */     
/*      */     int comparison;
/*  760 */     while (b <= c && (comparison = Character.compare(BigArrays.get(x, b), v)) <= 0) {
/*  761 */       if (comparison == 0) BigArrays.swap(x, a++, b); 
/*  762 */       b++;
/*      */     } 
/*  764 */     while (c >= b && (comparison = Character.compare(BigArrays.get(x, c), v)) >= 0) {
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
/*      */   public static void quickSort(char[][] x) {
/*  793 */     quickSort(x, 0L, BigArrays.length(x));
/*      */   }
/*      */   
/*      */   protected static class ForkJoinQuickSort extends RecursiveAction {
/*      */     private static final long serialVersionUID = 1L;
/*      */     private final long from;
/*      */     private final long to;
/*      */     private final char[][] x;
/*      */     
/*      */     public ForkJoinQuickSort(char[][] x, long from, long to) {
/*  803 */       this.from = from;
/*  804 */       this.to = to;
/*  805 */       this.x = x;
/*      */     }
/*      */ 
/*      */ 
/*      */     
/*      */     protected void compute() {
/*  811 */       char[][] x = this.x;
/*  812 */       long len = this.to - this.from;
/*  813 */       if (len < 8192L) {
/*  814 */         CharBigArrays.quickSort(x, this.from, this.to);
/*      */         
/*      */         return;
/*      */       } 
/*  818 */       long m = this.from + len / 2L;
/*  819 */       long l = this.from;
/*  820 */       long n = this.to - 1L;
/*  821 */       long s = len / 8L;
/*  822 */       l = CharBigArrays.med3(x, l, l + s, l + 2L * s);
/*  823 */       m = CharBigArrays.med3(x, m - s, m, m + s);
/*  824 */       n = CharBigArrays.med3(x, n - 2L * s, n - s, n);
/*  825 */       m = CharBigArrays.med3(x, l, m, n);
/*  826 */       char v = BigArrays.get(x, m);
/*      */       
/*  828 */       long a = this.from, b = a, c = this.to - 1L, d = c;
/*      */       
/*      */       int comparison;
/*  831 */       while (b <= c && (comparison = Character.compare(BigArrays.get(x, b), v)) <= 0) {
/*  832 */         if (comparison == 0) BigArrays.swap(x, a++, b); 
/*  833 */         b++;
/*      */       } 
/*  835 */       while (c >= b && (comparison = Character.compare(BigArrays.get(x, c), v)) >= 0) {
/*  836 */         if (comparison == 0) BigArrays.swap(x, c, d--); 
/*  837 */         c--;
/*      */       } 
/*  839 */       if (b > c) {
/*      */ 
/*      */ 
/*      */ 
/*      */         
/*  844 */         s = Math.min(a - this.from, b - a);
/*  845 */         CharBigArrays.swap(x, this.from, b - s, s);
/*  846 */         s = Math.min(d - c, this.to - d - 1L);
/*  847 */         CharBigArrays.swap(x, b, this.to - s, s);
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
/*      */   public static void parallelQuickSort(char[][] x, long from, long to) {
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
/*      */   public static void parallelQuickSort(char[][] x) {
/*  889 */     parallelQuickSort(x, 0L, BigArrays.length(x));
/*      */   }
/*      */   
/*      */   protected static class ForkJoinQuickSortComp extends RecursiveAction {
/*      */     private static final long serialVersionUID = 1L;
/*      */     private final long from;
/*      */     private final long to;
/*      */     private final char[][] x;
/*      */     private final CharComparator comp;
/*      */     
/*      */     public ForkJoinQuickSortComp(char[][] x, long from, long to, CharComparator comp) {
/*  900 */       this.from = from;
/*  901 */       this.to = to;
/*  902 */       this.x = x;
/*  903 */       this.comp = comp;
/*      */     }
/*      */ 
/*      */     
/*      */     protected void compute() {
/*  908 */       char[][] x = this.x;
/*  909 */       long len = this.to - this.from;
/*  910 */       if (len < 8192L) {
/*  911 */         CharBigArrays.quickSort(x, this.from, this.to, this.comp);
/*      */         
/*      */         return;
/*      */       } 
/*  915 */       long m = this.from + len / 2L;
/*  916 */       long l = this.from;
/*  917 */       long n = this.to - 1L;
/*  918 */       long s = len / 8L;
/*  919 */       l = CharBigArrays.med3(x, l, l + s, l + 2L * s, this.comp);
/*  920 */       m = CharBigArrays.med3(x, m - s, m, m + s, this.comp);
/*  921 */       n = CharBigArrays.med3(x, n - 2L * s, n - s, n, this.comp);
/*  922 */       m = CharBigArrays.med3(x, l, m, n, this.comp);
/*  923 */       char v = BigArrays.get(x, m);
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
/*  942 */         CharBigArrays.swap(x, this.from, b - s, s);
/*  943 */         s = Math.min(d - c, this.to - d - 1L);
/*  944 */         CharBigArrays.swap(x, b, this.to - s, s);
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
/*      */   public static void parallelQuickSort(char[][] x, long from, long to, CharComparator comp) {
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
/*      */   public static void parallelQuickSort(char[][] x, CharComparator comp) {
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
/*      */   public static long binarySearch(char[][] a, long from, long to, char key) {
/* 1013 */     to--;
/* 1014 */     while (from <= to) {
/* 1015 */       long mid = from + to >>> 1L;
/* 1016 */       char midVal = BigArrays.get(a, mid);
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
/*      */   public static long binarySearch(char[][] a, char key) {
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
/*      */   public static long binarySearch(char[][] a, long from, long to, char key, CharComparator c) {
/* 1065 */     to--;
/* 1066 */     while (from <= to) {
/* 1067 */       long mid = from + to >>> 1L;
/* 1068 */       char midVal = BigArrays.get(a, mid);
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
/*      */   public static long binarySearch(char[][] a, char key, CharComparator c) {
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
/*      */   public static void radixSort(char[][] a) {
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
/*      */   public static void radixSort(char[][] a, long from, long to) {
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
/* 1168 */       int signMask = 0;
/* 1169 */       if (length < 40L) {
/* 1170 */         selectionSort(a, first, first + length);
/*      */         continue;
/*      */       } 
/* 1173 */       int shift = (1 - level % 2) * 8;
/*      */ 
/*      */       
/*      */       long i;
/*      */       
/* 1178 */       for (i = length; i-- != 0L; BigArrays.set(digit, i, (byte)(BigArrays.get(a, first + i) >>> shift & 0xFF ^ 0x0)));
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
/* 1202 */         char t = BigArrays.get(a, l1 + first);
/* 1203 */         c = BigArrays.get(digit, l1) & 0xFF; long d;
/* 1204 */         for (pos[c] = pos[c] - 1L; (d = pos[c] - 1L) > l1; ) {
/* 1205 */           char z = t;
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
/*      */   private static void selectionSort(char[][] a, char[][] b, long from, long to) {
/*      */     long i;
/* 1218 */     for (i = from; i < to - 1L; i++) {
/* 1219 */       long m = i; long j;
/* 1220 */       for (j = i + 1L; j < to; ) { if (BigArrays.get(a, j) < BigArrays.get(a, m) || (BigArrays.get(a, j) == BigArrays.get(a, m) && BigArrays.get(b, j) < BigArrays.get(b, m))) m = j;  j++; }
/* 1221 */        if (m != i) {
/* 1222 */         char t = BigArrays.get(a, i);
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
/*      */   public static void radixSort(char[][] a, char[][] b) {
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
/*      */   public static void radixSort(char[][] a, char[][] b, long from, long to) {
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
/* 1306 */       int signMask = 0;
/* 1307 */       if (length < 40L) {
/* 1308 */         selectionSort(a, b, first, first + length);
/*      */         continue;
/*      */       } 
/* 1311 */       char[][] k = (level < 2) ? a : b;
/* 1312 */       int shift = (1 - level % 2) * 8;
/*      */ 
/*      */       
/*      */       long i;
/*      */       
/* 1317 */       for (i = length; i-- != 0L; BigArrays.set(digit, i, (byte)(BigArrays.get(k, first + i) >>> shift & 0xFF ^ 0x0)));
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
/* 1339 */         char t = BigArrays.get(a, l1 + first);
/* 1340 */         char u = BigArrays.get(b, l1 + first);
/* 1341 */         c = BigArrays.get(digit, l1) & 0xFF; long d;
/* 1342 */         for (pos[c] = pos[c] - 1L; (d = pos[c] - 1L) > l1; ) {
/* 1343 */           char z = t;
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
/*      */   private static void insertionSortIndirect(long[][] perm, char[][] a, char[][] b, long from, long to) {
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
/*      */   public static void radixSortIndirect(long[][] perm, char[][] a, char[][] b, boolean stable) {
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
/*      */   public static void radixSortIndirect(long[][] perm, char[][] a, char[][] b, long from, long to, boolean stable) {
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
/*      */     //   17: invokestatic insertionSortIndirect : ([[J[[C[[CJJ)V
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
/*      */     //   114: ifle -> 703
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
/*      */     //   141: iconst_0
/*      */     //   142: istore #23
/*      */     //   144: iload #22
/*      */     //   146: iconst_2
/*      */     //   147: if_icmpge -> 154
/*      */     //   150: aload_1
/*      */     //   151: goto -> 155
/*      */     //   154: aload_2
/*      */     //   155: astore #24
/*      */     //   157: iconst_1
/*      */     //   158: iload #22
/*      */     //   160: iconst_2
/*      */     //   161: irem
/*      */     //   162: isub
/*      */     //   163: bipush #8
/*      */     //   165: imul
/*      */     //   166: istore #25
/*      */     //   168: lload #18
/*      */     //   170: lload #20
/*      */     //   172: ladd
/*      */     //   173: lstore #26
/*      */     //   175: lload #26
/*      */     //   177: dup2
/*      */     //   178: lconst_1
/*      */     //   179: lsub
/*      */     //   180: lstore #26
/*      */     //   182: lload #18
/*      */     //   184: lcmp
/*      */     //   185: ifeq -> 218
/*      */     //   188: aload #15
/*      */     //   190: aload #24
/*      */     //   192: aload_0
/*      */     //   193: lload #26
/*      */     //   195: invokestatic get : ([[JJ)J
/*      */     //   198: invokestatic get : ([[CJ)C
/*      */     //   201: iload #25
/*      */     //   203: iushr
/*      */     //   204: sipush #255
/*      */     //   207: iand
/*      */     //   208: iconst_0
/*      */     //   209: ixor
/*      */     //   210: dup2
/*      */     //   211: laload
/*      */     //   212: lconst_1
/*      */     //   213: ladd
/*      */     //   214: lastore
/*      */     //   215: goto -> 175
/*      */     //   218: iconst_m1
/*      */     //   219: istore #26
/*      */     //   221: iload #7
/*      */     //   223: ifeq -> 230
/*      */     //   226: lconst_0
/*      */     //   227: goto -> 232
/*      */     //   230: lload #18
/*      */     //   232: lstore #27
/*      */     //   234: iconst_0
/*      */     //   235: istore #29
/*      */     //   237: iload #29
/*      */     //   239: sipush #256
/*      */     //   242: if_icmpge -> 281
/*      */     //   245: aload #15
/*      */     //   247: iload #29
/*      */     //   249: laload
/*      */     //   250: lconst_0
/*      */     //   251: lcmp
/*      */     //   252: ifeq -> 259
/*      */     //   255: iload #29
/*      */     //   257: istore #26
/*      */     //   259: aload #16
/*      */     //   261: iload #29
/*      */     //   263: lload #27
/*      */     //   265: aload #15
/*      */     //   267: iload #29
/*      */     //   269: laload
/*      */     //   270: ladd
/*      */     //   271: dup2
/*      */     //   272: lstore #27
/*      */     //   274: lastore
/*      */     //   275: iinc #29, 1
/*      */     //   278: goto -> 237
/*      */     //   281: iload #7
/*      */     //   283: ifeq -> 475
/*      */     //   286: lload #18
/*      */     //   288: lload #20
/*      */     //   290: ladd
/*      */     //   291: lstore #29
/*      */     //   293: lload #29
/*      */     //   295: dup2
/*      */     //   296: lconst_1
/*      */     //   297: lsub
/*      */     //   298: lstore #29
/*      */     //   300: lload #18
/*      */     //   302: lcmp
/*      */     //   303: ifeq -> 348
/*      */     //   306: aload #17
/*      */     //   308: aload #16
/*      */     //   310: aload #24
/*      */     //   312: aload_0
/*      */     //   313: lload #29
/*      */     //   315: invokestatic get : ([[JJ)J
/*      */     //   318: invokestatic get : ([[CJ)C
/*      */     //   321: iload #25
/*      */     //   323: iushr
/*      */     //   324: sipush #255
/*      */     //   327: iand
/*      */     //   328: iconst_0
/*      */     //   329: ixor
/*      */     //   330: dup2
/*      */     //   331: laload
/*      */     //   332: lconst_1
/*      */     //   333: lsub
/*      */     //   334: dup2_x2
/*      */     //   335: lastore
/*      */     //   336: aload_0
/*      */     //   337: lload #29
/*      */     //   339: invokestatic get : ([[JJ)J
/*      */     //   342: invokestatic set : ([[JJJ)V
/*      */     //   345: goto -> 293
/*      */     //   348: aload #17
/*      */     //   350: lconst_0
/*      */     //   351: aload_0
/*      */     //   352: lload #18
/*      */     //   354: lload #20
/*      */     //   356: invokestatic copy : ([[JJ[[JJJ)V
/*      */     //   359: lload #18
/*      */     //   361: lstore #27
/*      */     //   363: iconst_0
/*      */     //   364: istore #29
/*      */     //   366: iload #29
/*      */     //   368: sipush #256
/*      */     //   371: if_icmpge -> 466
/*      */     //   374: iload #22
/*      */     //   376: iconst_3
/*      */     //   377: if_icmpge -> 450
/*      */     //   380: aload #15
/*      */     //   382: iload #29
/*      */     //   384: laload
/*      */     //   385: lconst_1
/*      */     //   386: lcmp
/*      */     //   387: ifle -> 450
/*      */     //   390: aload #15
/*      */     //   392: iload #29
/*      */     //   394: laload
/*      */     //   395: ldc2_w 1024
/*      */     //   398: lcmp
/*      */     //   399: ifge -> 421
/*      */     //   402: aload_0
/*      */     //   403: aload_1
/*      */     //   404: aload_2
/*      */     //   405: lload #27
/*      */     //   407: lload #27
/*      */     //   409: aload #15
/*      */     //   411: iload #29
/*      */     //   413: laload
/*      */     //   414: ladd
/*      */     //   415: invokestatic insertionSortIndirect : ([[J[[C[[CJJ)V
/*      */     //   418: goto -> 450
/*      */     //   421: aload #12
/*      */     //   423: iload #11
/*      */     //   425: lload #27
/*      */     //   427: lastore
/*      */     //   428: aload #13
/*      */     //   430: iload #11
/*      */     //   432: aload #15
/*      */     //   434: iload #29
/*      */     //   436: laload
/*      */     //   437: lastore
/*      */     //   438: aload #14
/*      */     //   440: iload #11
/*      */     //   442: iinc #11, 1
/*      */     //   445: iload #22
/*      */     //   447: iconst_1
/*      */     //   448: iadd
/*      */     //   449: iastore
/*      */     //   450: lload #27
/*      */     //   452: aload #15
/*      */     //   454: iload #29
/*      */     //   456: laload
/*      */     //   457: ladd
/*      */     //   458: lstore #27
/*      */     //   460: iinc #29, 1
/*      */     //   463: goto -> 366
/*      */     //   466: aload #15
/*      */     //   468: lconst_0
/*      */     //   469: invokestatic fill : ([JJ)V
/*      */     //   472: goto -> 700
/*      */     //   475: lload #18
/*      */     //   477: lload #20
/*      */     //   479: ladd
/*      */     //   480: aload #15
/*      */     //   482: iload #26
/*      */     //   484: laload
/*      */     //   485: lsub
/*      */     //   486: lstore #29
/*      */     //   488: iconst_m1
/*      */     //   489: istore #31
/*      */     //   491: lload #18
/*      */     //   493: lstore #32
/*      */     //   495: lload #32
/*      */     //   497: lload #29
/*      */     //   499: lcmp
/*      */     //   500: ifgt -> 700
/*      */     //   503: aload_0
/*      */     //   504: lload #32
/*      */     //   506: invokestatic get : ([[JJ)J
/*      */     //   509: lstore #36
/*      */     //   511: aload #24
/*      */     //   513: lload #36
/*      */     //   515: invokestatic get : ([[CJ)C
/*      */     //   518: iload #25
/*      */     //   520: iushr
/*      */     //   521: sipush #255
/*      */     //   524: iand
/*      */     //   525: iconst_0
/*      */     //   526: ixor
/*      */     //   527: istore #31
/*      */     //   529: lload #32
/*      */     //   531: lload #29
/*      */     //   533: lcmp
/*      */     //   534: ifge -> 605
/*      */     //   537: aload #16
/*      */     //   539: iload #31
/*      */     //   541: dup2
/*      */     //   542: laload
/*      */     //   543: lconst_1
/*      */     //   544: lsub
/*      */     //   545: dup2_x2
/*      */     //   546: lastore
/*      */     //   547: dup2
/*      */     //   548: lstore #34
/*      */     //   550: lload #32
/*      */     //   552: lcmp
/*      */     //   553: ifle -> 597
/*      */     //   556: lload #36
/*      */     //   558: lstore #38
/*      */     //   560: aload_0
/*      */     //   561: lload #34
/*      */     //   563: invokestatic get : ([[JJ)J
/*      */     //   566: lstore #36
/*      */     //   568: aload_0
/*      */     //   569: lload #34
/*      */     //   571: lload #38
/*      */     //   573: invokestatic set : ([[JJJ)V
/*      */     //   576: aload #24
/*      */     //   578: lload #36
/*      */     //   580: invokestatic get : ([[CJ)C
/*      */     //   583: iload #25
/*      */     //   585: iushr
/*      */     //   586: sipush #255
/*      */     //   589: iand
/*      */     //   590: iconst_0
/*      */     //   591: ixor
/*      */     //   592: istore #31
/*      */     //   594: goto -> 537
/*      */     //   597: aload_0
/*      */     //   598: lload #32
/*      */     //   600: lload #36
/*      */     //   602: invokestatic set : ([[JJJ)V
/*      */     //   605: iload #22
/*      */     //   607: iconst_3
/*      */     //   608: if_icmpge -> 681
/*      */     //   611: aload #15
/*      */     //   613: iload #31
/*      */     //   615: laload
/*      */     //   616: lconst_1
/*      */     //   617: lcmp
/*      */     //   618: ifle -> 681
/*      */     //   621: aload #15
/*      */     //   623: iload #31
/*      */     //   625: laload
/*      */     //   626: ldc2_w 1024
/*      */     //   629: lcmp
/*      */     //   630: ifge -> 652
/*      */     //   633: aload_0
/*      */     //   634: aload_1
/*      */     //   635: aload_2
/*      */     //   636: lload #32
/*      */     //   638: lload #32
/*      */     //   640: aload #15
/*      */     //   642: iload #31
/*      */     //   644: laload
/*      */     //   645: ladd
/*      */     //   646: invokestatic insertionSortIndirect : ([[J[[C[[CJJ)V
/*      */     //   649: goto -> 681
/*      */     //   652: aload #12
/*      */     //   654: iload #11
/*      */     //   656: lload #32
/*      */     //   658: lastore
/*      */     //   659: aload #13
/*      */     //   661: iload #11
/*      */     //   663: aload #15
/*      */     //   665: iload #31
/*      */     //   667: laload
/*      */     //   668: lastore
/*      */     //   669: aload #14
/*      */     //   671: iload #11
/*      */     //   673: iinc #11, 1
/*      */     //   676: iload #22
/*      */     //   678: iconst_1
/*      */     //   679: iadd
/*      */     //   680: iastore
/*      */     //   681: lload #32
/*      */     //   683: aload #15
/*      */     //   685: iload #31
/*      */     //   687: laload
/*      */     //   688: ladd
/*      */     //   689: lstore #32
/*      */     //   691: aload #15
/*      */     //   693: iload #31
/*      */     //   695: lconst_0
/*      */     //   696: lastore
/*      */     //   697: goto -> 495
/*      */     //   700: goto -> 112
/*      */     //   703: return
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
/*      */     //   #1450	-> 144
/*      */     //   #1451	-> 157
/*      */     //   #1456	-> 168
/*      */     //   #1458	-> 218
/*      */     //   #1459	-> 221
/*      */     //   #1460	-> 234
/*      */     //   #1461	-> 245
/*      */     //   #1462	-> 259
/*      */     //   #1460	-> 275
/*      */     //   #1464	-> 281
/*      */     //   #1465	-> 286
/*      */     //   #1466	-> 348
/*      */     //   #1467	-> 359
/*      */     //   #1468	-> 363
/*      */     //   #1469	-> 374
/*      */     //   #1470	-> 390
/*      */     //   #1472	-> 421
/*      */     //   #1473	-> 428
/*      */     //   #1474	-> 438
/*      */     //   #1477	-> 450
/*      */     //   #1468	-> 460
/*      */     //   #1479	-> 466
/*      */     //   #1481	-> 475
/*      */     //   #1483	-> 488
/*      */     //   #1484	-> 491
/*      */     //   #1485	-> 503
/*      */     //   #1486	-> 511
/*      */     //   #1487	-> 529
/*      */     //   #1488	-> 537
/*      */     //   #1489	-> 556
/*      */     //   #1490	-> 560
/*      */     //   #1491	-> 568
/*      */     //   #1492	-> 576
/*      */     //   #1493	-> 594
/*      */     //   #1494	-> 597
/*      */     //   #1496	-> 605
/*      */     //   #1497	-> 621
/*      */     //   #1499	-> 652
/*      */     //   #1500	-> 659
/*      */     //   #1501	-> 669
/*      */     //   #1484	-> 681
/*      */     //   #1506	-> 700
/*      */     //   #1507	-> 703
/*      */     // Local variable table:
/*      */     //   start	length	slot	name	descriptor
/*      */     //   175	43	26	i	J
/*      */     //   237	44	29	i	I
/*      */     //   293	55	29	i	J
/*      */     //   366	100	29	i	I
/*      */     //   560	34	38	z	J
/*      */     //   550	55	34	d	J
/*      */     //   511	170	36	t	J
/*      */     //   495	205	32	i	J
/*      */     //   488	212	29	end	J
/*      */     //   491	209	31	c	I
/*      */     //   127	573	18	first	J
/*      */     //   134	566	20	length	J
/*      */     //   141	559	22	level	I
/*      */     //   144	556	23	signMask	I
/*      */     //   157	543	24	k	[[C
/*      */     //   168	532	25	shift	I
/*      */     //   221	479	26	lastUsed	I
/*      */     //   234	466	27	p	J
/*      */     //   0	704	0	perm	[[J
/*      */     //   0	704	1	a	[[C
/*      */     //   0	704	2	b	[[C
/*      */     //   0	704	3	from	J
/*      */     //   0	704	5	to	J
/*      */     //   0	704	7	stable	Z
/*      */     //   24	680	8	layers	I
/*      */     //   27	677	9	maxLevel	I
/*      */     //   32	672	10	stackSize	I
/*      */     //   35	669	11	stackPos	I
/*      */     //   42	662	12	offsetStack	[J
/*      */     //   49	655	13	lengthStack	[J
/*      */     //   56	648	14	levelStack	[I
/*      */     //   87	617	15	count	[J
/*      */     //   94	610	16	pos	[J
/*      */     //   112	592	17	support	[[J
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
/*      */   public static char[][] shuffle(char[][] a, long from, long to, Random random) {
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
/*      */   public static char[][] shuffle(char[][] a, Random random) {
/* 1530 */     return BigArrays.shuffle(a, random);
/*      */   }
/*      */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\i\\unimi\dsi\fastutil\chars\CharBigArrays.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */