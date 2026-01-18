/*      */ package it.unimi.dsi.fastutil.longs;
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
/*      */ import java.util.concurrent.atomic.AtomicLongArray;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ public final class LongBigArrays
/*      */ {
/*   71 */   public static final long[][] EMPTY_BIG_ARRAY = new long[0][];
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*   79 */   public static final long[][] DEFAULT_EMPTY_BIG_ARRAY = new long[0][];
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
/*      */   public static long get(long[][] array, long index) {
/*   91 */     return array[BigArrays.segment(index)][BigArrays.displacement(index)];
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
/*      */   public static void set(long[][] array, long index, long value) {
/*  104 */     array[BigArrays.segment(index)][BigArrays.displacement(index)] = value;
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
/*      */   public static void swap(long[][] array, long first, long second) {
/*  117 */     long t = array[BigArrays.segment(first)][BigArrays.displacement(first)];
/*  118 */     array[BigArrays.segment(first)][BigArrays.displacement(first)] = array[BigArrays.segment(second)][BigArrays.displacement(second)];
/*  119 */     array[BigArrays.segment(second)][BigArrays.displacement(second)] = t;
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
/*      */   public static void add(long[][] array, long index, long incr) {
/*  132 */     array[BigArrays.segment(index)][BigArrays.displacement(index)] = array[BigArrays.segment(index)][BigArrays.displacement(index)] + incr;
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
/*      */   public static void mul(long[][] array, long index, long factor) {
/*  145 */     array[BigArrays.segment(index)][BigArrays.displacement(index)] = array[BigArrays.segment(index)][BigArrays.displacement(index)] * factor;
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
/*      */   public static void incr(long[][] array, long index) {
/*  157 */     array[BigArrays.segment(index)][BigArrays.displacement(index)] = array[BigArrays.segment(index)][BigArrays.displacement(index)] + 1L;
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
/*      */   public static void decr(long[][] array, long index) {
/*  169 */     array[BigArrays.segment(index)][BigArrays.displacement(index)] = array[BigArrays.segment(index)][BigArrays.displacement(index)] - 1L;
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
/*      */   public static long length(long[][] array) {
/*  181 */     int length = array.length;
/*  182 */     return (length == 0) ? 0L : (BigArrays.start(length - 1) + (array[length - 1]).length);
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
/*      */   public static void copy(long[][] srcArray, long srcPos, long[][] destArray, long destPos, long length) {
/*  199 */     BigArrays.copy(srcArray, srcPos, destArray, destPos, length);
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
/*      */   public static void copyFromBig(long[][] srcArray, long srcPos, long[] destArray, int destPos, int length) {
/*  215 */     BigArrays.copyFromBig(srcArray, srcPos, destArray, destPos, length);
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
/*      */   public static void copyToBig(long[] srcArray, int srcPos, long[][] destArray, long destPos, long length) {
/*  231 */     BigArrays.copyToBig(srcArray, srcPos, destArray, destPos, length);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static long[][] newBigArray(long length) {
/*  241 */     if (length == 0L) return EMPTY_BIG_ARRAY; 
/*  242 */     BigArrays.ensureLength(length);
/*  243 */     int baseLength = (int)(length + 134217727L >>> 27L);
/*  244 */     long[][] base = new long[baseLength][];
/*  245 */     int residual = (int)(length & 0x7FFFFFFL);
/*  246 */     if (residual != 0)
/*  247 */     { for (int i = 0; i < baseLength - 1; ) { base[i] = new long[134217728]; i++; }
/*  248 */        base[baseLength - 1] = new long[residual]; }
/*  249 */     else { for (int i = 0; i < baseLength; ) { base[i] = new long[134217728]; i++; }  }
/*  250 */      return base;
/*      */   }
/*      */ 
/*      */   
/*  254 */   public static final AtomicLongArray[] EMPTY_BIG_ATOMIC_ARRAY = new AtomicLongArray[0];
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static AtomicLongArray[] newBigAtomicArray(long length) {
/*  263 */     if (length == 0L) return EMPTY_BIG_ATOMIC_ARRAY; 
/*  264 */     BigArrays.ensureLength(length);
/*  265 */     int baseLength = (int)(length + 134217727L >>> 27L);
/*  266 */     AtomicLongArray[] base = new AtomicLongArray[baseLength];
/*  267 */     int residual = (int)(length & 0x7FFFFFFL);
/*  268 */     if (residual != 0)
/*  269 */     { for (int i = 0; i < baseLength - 1; ) { base[i] = new AtomicLongArray(134217728); i++; }
/*  270 */        base[baseLength - 1] = new AtomicLongArray(residual); }
/*  271 */     else { for (int i = 0; i < baseLength; ) { base[i] = new AtomicLongArray(134217728); i++; }  }
/*  272 */      return base;
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
/*      */   public static long[][] wrap(long[] array) {
/*  287 */     return BigArrays.wrap(array);
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
/*      */   public static long[][] ensureCapacity(long[][] array, long length) {
/*  310 */     return ensureCapacity(array, length, length(array));
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
/*      */   public static long[][] forceCapacity(long[][] array, long length, long preserve) {
/*  331 */     return BigArrays.forceCapacity(array, length, preserve);
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
/*      */   public static long[][] ensureCapacity(long[][] array, long length, long preserve) {
/*  353 */     return (length > length(array)) ? forceCapacity(array, length, preserve) : array;
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
/*      */   public static long[][] grow(long[][] array, long length) {
/*  377 */     long oldLength = length(array);
/*  378 */     return (length > oldLength) ? grow(array, length, oldLength) : array;
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
/*      */   public static long[][] grow(long[][] array, long length, long preserve) {
/*  405 */     long oldLength = length(array);
/*  406 */     return (length > oldLength) ? ensureCapacity(array, Math.max(oldLength + (oldLength >> 1L), length), preserve) : array;
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
/*      */   public static long[][] trim(long[][] array, long length) {
/*  425 */     BigArrays.ensureLength(length);
/*  426 */     long oldLength = length(array);
/*  427 */     if (length >= oldLength) return array; 
/*  428 */     int baseLength = (int)(length + 134217727L >>> 27L);
/*  429 */     long[][] base = Arrays.<long[]>copyOf(array, baseLength);
/*  430 */     int residual = (int)(length & 0x7FFFFFFL);
/*  431 */     if (residual != 0) base[baseLength - 1] = LongArrays.trim(base[baseLength - 1], residual); 
/*  432 */     return base;
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
/*      */   public static long[][] setLength(long[][] array, long length) {
/*  453 */     return BigArrays.setLength(array, length);
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
/*      */   public static long[][] copy(long[][] array, long offset, long length) {
/*  468 */     return BigArrays.copy(array, offset, length);
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
/*      */   public static long[][] copy(long[][] array) {
/*  480 */     return BigArrays.copy(array);
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
/*      */   public static void fill(long[][] array, long value) {
/*  496 */     for (int i = array.length; i-- != 0; Arrays.fill(array[i], value));
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
/*      */   public static void fill(long[][] array, long from, long to, long value) {
/*  514 */     BigArrays.fill(array, from, to, value);
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
/*      */   public static boolean equals(long[][] a1, long[][] a2) {
/*  531 */     return BigArrays.equals(a1, a2);
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
/*      */   public static String toString(long[][] a) {
/*  543 */     return BigArrays.toString(a);
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
/*      */   public static void ensureFromTo(long[][] a, long from, long to) {
/*  563 */     BigArrays.ensureFromTo(length(a), from, to);
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
/*      */   public static void ensureOffsetLength(long[][] a, long offset, long length) {
/*  582 */     BigArrays.ensureOffsetLength(length(a), offset, length);
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
/*      */   public static void ensureSameLength(long[][] a, long[][] b) {
/*  595 */     if (length(a) != length(b)) throw new IllegalArgumentException("Array size mismatch: " + length(a) + " != " + length(b)); 
/*      */   }
/*      */   
/*      */   private static final class BigArrayHashStrategy implements Hash.Strategy<long[][]>, Serializable {
/*      */     private static final long serialVersionUID = -7046029254386353129L;
/*      */     
/*      */     private BigArrayHashStrategy() {}
/*      */     
/*      */     public int hashCode(long[][] o) {
/*  604 */       return Arrays.deepHashCode((Object[])o);
/*      */     }
/*      */ 
/*      */     
/*      */     public boolean equals(long[][] a, long[][] b) {
/*  609 */       return LongBigArrays.equals(a, b);
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
/*  622 */   public static final Hash.Strategy HASH_STRATEGY = new BigArrayHashStrategy(); private static final int QUICKSORT_NO_REC = 7; private static final int PARALLEL_QUICKSORT_NO_FORK = 8192; private static final int MEDIUM = 40;
/*      */   private static final int DIGIT_BITS = 8;
/*      */   private static final int DIGIT_MASK = 255;
/*      */   private static final int DIGITS_PER_ELEMENT = 8;
/*      */   private static final int RADIXSORT_NO_REC = 1024;
/*      */   
/*      */   private static ForkJoinPool getPool() {
/*  629 */     ForkJoinPool current = ForkJoinTask.getPool();
/*  630 */     return (current == null) ? ForkJoinPool.commonPool() : current;
/*      */   }
/*      */   
/*      */   private static void swap(long[][] x, long a, long b, long n) {
/*  634 */     for (int i = 0; i < n; ) { BigArrays.swap(x, a, b); i++; a++; b++; }
/*      */   
/*      */   }
/*      */   private static long med3(long[][] x, long a, long b, long c, LongComparator comp) {
/*  638 */     int ab = comp.compare(BigArrays.get(x, a), BigArrays.get(x, b));
/*  639 */     int ac = comp.compare(BigArrays.get(x, a), BigArrays.get(x, c));
/*  640 */     int bc = comp.compare(BigArrays.get(x, b), BigArrays.get(x, c));
/*  641 */     return (ab < 0) ? ((bc < 0) ? b : ((ac < 0) ? c : a)) : ((bc > 0) ? b : ((ac > 0) ? c : a));
/*      */   }
/*      */   private static void selectionSort(long[][] a, long from, long to, LongComparator comp) {
/*      */     long i;
/*  645 */     for (i = from; i < to - 1L; i++) {
/*  646 */       long m = i; long j;
/*  647 */       for (j = i + 1L; j < to; ) { if (comp.compare(BigArrays.get(a, j), BigArrays.get(a, m)) < 0) m = j;  j++; }
/*  648 */        if (m != i) BigArrays.swap(a, i, m);
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
/*      */   public static void quickSort(long[][] x, long from, long to, LongComparator comp) {
/*  667 */     long len = to - from;
/*      */     
/*  669 */     if (len < 7L) {
/*  670 */       selectionSort(x, from, to, comp);
/*      */       
/*      */       return;
/*      */     } 
/*  674 */     long m = from + len / 2L;
/*  675 */     if (len > 7L) {
/*  676 */       long l = from;
/*  677 */       long n = to - 1L;
/*  678 */       if (len > 40L) {
/*  679 */         long s = len / 8L;
/*  680 */         l = med3(x, l, l + s, l + 2L * s, comp);
/*  681 */         m = med3(x, m - s, m, m + s, comp);
/*  682 */         n = med3(x, n - 2L * s, n - s, n, comp);
/*      */       } 
/*  684 */       m = med3(x, l, m, n, comp);
/*      */     } 
/*  686 */     long v = BigArrays.get(x, m);
/*      */     
/*  688 */     long a = from, b = a, c = to - 1L, d = c;
/*      */     
/*      */     int comparison;
/*  691 */     while (b <= c && (comparison = comp.compare(BigArrays.get(x, b), v)) <= 0) {
/*  692 */       if (comparison == 0) BigArrays.swap(x, a++, b); 
/*  693 */       b++;
/*      */     } 
/*  695 */     while (c >= b && (comparison = comp.compare(BigArrays.get(x, c), v)) >= 0) {
/*  696 */       if (comparison == 0) BigArrays.swap(x, c, d--); 
/*  697 */       c--;
/*      */     } 
/*  699 */     if (b > c) {
/*      */ 
/*      */ 
/*      */       
/*  703 */       long n = to;
/*  704 */       long s = Math.min(a - from, b - a);
/*  705 */       swap(x, from, b - s, s);
/*  706 */       s = Math.min(d - c, n - d - 1L);
/*  707 */       swap(x, b, n - s, s);
/*      */       
/*  709 */       if ((s = b - a) > 1L) quickSort(x, from, from + s, comp); 
/*  710 */       if ((s = d - c) > 1L) quickSort(x, n - s, n, comp); 
/*      */       return;
/*      */     } 
/*      */     BigArrays.swap(x, b++, c--); } private static long med3(long[][] x, long a, long b, long c) {
/*  714 */     int ab = Long.compare(BigArrays.get(x, a), BigArrays.get(x, b));
/*  715 */     int ac = Long.compare(BigArrays.get(x, a), BigArrays.get(x, c));
/*  716 */     int bc = Long.compare(BigArrays.get(x, b), BigArrays.get(x, c));
/*  717 */     return (ab < 0) ? ((bc < 0) ? b : ((ac < 0) ? c : a)) : ((bc > 0) ? b : ((ac > 0) ? c : a));
/*      */   }
/*      */   private static void selectionSort(long[][] a, long from, long to) {
/*      */     long i;
/*  721 */     for (i = from; i < to - 1L; i++) {
/*  722 */       long m = i; long j;
/*  723 */       for (j = i + 1L; j < to; ) { if (BigArrays.get(a, j) < BigArrays.get(a, m)) m = j;  j++; }
/*  724 */        if (m != i) BigArrays.swap(a, i, m);
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
/*      */   public static void quickSort(long[][] x, LongComparator comp) {
/*  742 */     quickSort(x, 0L, BigArrays.length(x), comp);
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
/*      */   public static void quickSort(long[][] x, long from, long to) {
/*  759 */     long len = to - from;
/*      */     
/*  761 */     if (len < 7L) {
/*  762 */       selectionSort(x, from, to);
/*      */       
/*      */       return;
/*      */     } 
/*  766 */     long m = from + len / 2L;
/*  767 */     if (len > 7L) {
/*  768 */       long l = from;
/*  769 */       long n = to - 1L;
/*  770 */       if (len > 40L) {
/*  771 */         long s = len / 8L;
/*  772 */         l = med3(x, l, l + s, l + 2L * s);
/*  773 */         m = med3(x, m - s, m, m + s);
/*  774 */         n = med3(x, n - 2L * s, n - s, n);
/*      */       } 
/*  776 */       m = med3(x, l, m, n);
/*      */     } 
/*  778 */     long v = BigArrays.get(x, m);
/*      */     
/*  780 */     long a = from, b = a, c = to - 1L, d = c;
/*      */     
/*      */     int comparison;
/*  783 */     while (b <= c && (comparison = Long.compare(BigArrays.get(x, b), v)) <= 0) {
/*  784 */       if (comparison == 0) BigArrays.swap(x, a++, b); 
/*  785 */       b++;
/*      */     } 
/*  787 */     while (c >= b && (comparison = Long.compare(BigArrays.get(x, c), v)) >= 0) {
/*  788 */       if (comparison == 0) BigArrays.swap(x, c, d--); 
/*  789 */       c--;
/*      */     } 
/*  791 */     if (b > c) {
/*      */ 
/*      */ 
/*      */       
/*  795 */       long n = to;
/*  796 */       long s = Math.min(a - from, b - a);
/*  797 */       swap(x, from, b - s, s);
/*  798 */       s = Math.min(d - c, n - d - 1L);
/*  799 */       swap(x, b, n - s, s);
/*      */       
/*  801 */       if ((s = b - a) > 1L) quickSort(x, from, from + s); 
/*  802 */       if ((s = d - c) > 1L) quickSort(x, n - s, n);
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
/*      */   public static void quickSort(long[][] x) {
/*  816 */     quickSort(x, 0L, BigArrays.length(x));
/*      */   }
/*      */   
/*      */   protected static class ForkJoinQuickSort extends RecursiveAction {
/*      */     private static final long serialVersionUID = 1L;
/*      */     private final long from;
/*      */     private final long to;
/*      */     private final long[][] x;
/*      */     
/*      */     public ForkJoinQuickSort(long[][] x, long from, long to) {
/*  826 */       this.from = from;
/*  827 */       this.to = to;
/*  828 */       this.x = x;
/*      */     }
/*      */ 
/*      */ 
/*      */     
/*      */     protected void compute() {
/*  834 */       long[][] x = this.x;
/*  835 */       long len = this.to - this.from;
/*  836 */       if (len < 8192L) {
/*  837 */         LongBigArrays.quickSort(x, this.from, this.to);
/*      */         
/*      */         return;
/*      */       } 
/*  841 */       long m = this.from + len / 2L;
/*  842 */       long l = this.from;
/*  843 */       long n = this.to - 1L;
/*  844 */       long s = len / 8L;
/*  845 */       l = LongBigArrays.med3(x, l, l + s, l + 2L * s);
/*  846 */       m = LongBigArrays.med3(x, m - s, m, m + s);
/*  847 */       n = LongBigArrays.med3(x, n - 2L * s, n - s, n);
/*  848 */       m = LongBigArrays.med3(x, l, m, n);
/*  849 */       long v = BigArrays.get(x, m);
/*      */       
/*  851 */       long a = this.from, b = a, c = this.to - 1L, d = c;
/*      */       
/*      */       int comparison;
/*  854 */       while (b <= c && (comparison = Long.compare(BigArrays.get(x, b), v)) <= 0) {
/*  855 */         if (comparison == 0) BigArrays.swap(x, a++, b); 
/*  856 */         b++;
/*      */       } 
/*  858 */       while (c >= b && (comparison = Long.compare(BigArrays.get(x, c), v)) >= 0) {
/*  859 */         if (comparison == 0) BigArrays.swap(x, c, d--); 
/*  860 */         c--;
/*      */       } 
/*  862 */       if (b > c) {
/*      */ 
/*      */ 
/*      */ 
/*      */         
/*  867 */         s = Math.min(a - this.from, b - a);
/*  868 */         LongBigArrays.swap(x, this.from, b - s, s);
/*  869 */         s = Math.min(d - c, this.to - d - 1L);
/*  870 */         LongBigArrays.swap(x, b, this.to - s, s);
/*      */         
/*  872 */         s = b - a;
/*  873 */         long t = d - c;
/*  874 */         if (s > 1L && t > 1L) { invokeAll(new ForkJoinQuickSort(x, this.from, this.from + s), new ForkJoinQuickSort(x, this.to - t, this.to)); }
/*  875 */         else if (s > 1L) { invokeAll((ForkJoinTask<?>[])new ForkJoinTask[] { new ForkJoinQuickSort(x, this.from, this.from + s) }); }
/*  876 */         else { invokeAll((ForkJoinTask<?>[])new ForkJoinTask[] { new ForkJoinQuickSort(x, this.to - t, this.to) }); }
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
/*      */   public static void parallelQuickSort(long[][] x, long from, long to) {
/*  894 */     ForkJoinPool pool = getPool();
/*  895 */     if (to - from < 8192L || pool.getParallelism() == 1) { quickSort(x, from, to); }
/*      */     else
/*  897 */     { pool.invoke(new ForkJoinQuickSort(x, from, to)); }
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
/*      */   public static void parallelQuickSort(long[][] x) {
/*  912 */     parallelQuickSort(x, 0L, BigArrays.length(x));
/*      */   }
/*      */   
/*      */   protected static class ForkJoinQuickSortComp extends RecursiveAction {
/*      */     private static final long serialVersionUID = 1L;
/*      */     private final long from;
/*      */     private final long to;
/*      */     private final long[][] x;
/*      */     private final LongComparator comp;
/*      */     
/*      */     public ForkJoinQuickSortComp(long[][] x, long from, long to, LongComparator comp) {
/*  923 */       this.from = from;
/*  924 */       this.to = to;
/*  925 */       this.x = x;
/*  926 */       this.comp = comp;
/*      */     }
/*      */ 
/*      */     
/*      */     protected void compute() {
/*  931 */       long[][] x = this.x;
/*  932 */       long len = this.to - this.from;
/*  933 */       if (len < 8192L) {
/*  934 */         LongBigArrays.quickSort(x, this.from, this.to, this.comp);
/*      */         
/*      */         return;
/*      */       } 
/*  938 */       long m = this.from + len / 2L;
/*  939 */       long l = this.from;
/*  940 */       long n = this.to - 1L;
/*  941 */       long s = len / 8L;
/*  942 */       l = LongBigArrays.med3(x, l, l + s, l + 2L * s, this.comp);
/*  943 */       m = LongBigArrays.med3(x, m - s, m, m + s, this.comp);
/*  944 */       n = LongBigArrays.med3(x, n - 2L * s, n - s, n, this.comp);
/*  945 */       m = LongBigArrays.med3(x, l, m, n, this.comp);
/*  946 */       long v = BigArrays.get(x, m);
/*      */       
/*  948 */       long a = this.from, b = a, c = this.to - 1L, d = c;
/*      */       
/*      */       int comparison;
/*  951 */       while (b <= c && (comparison = this.comp.compare(BigArrays.get(x, b), v)) <= 0) {
/*  952 */         if (comparison == 0) BigArrays.swap(x, a++, b); 
/*  953 */         b++;
/*      */       } 
/*  955 */       while (c >= b && (comparison = this.comp.compare(BigArrays.get(x, c), v)) >= 0) {
/*  956 */         if (comparison == 0) BigArrays.swap(x, c, d--); 
/*  957 */         c--;
/*      */       } 
/*  959 */       if (b > c) {
/*      */ 
/*      */ 
/*      */ 
/*      */         
/*  964 */         s = Math.min(a - this.from, b - a);
/*  965 */         LongBigArrays.swap(x, this.from, b - s, s);
/*  966 */         s = Math.min(d - c, this.to - d - 1L);
/*  967 */         LongBigArrays.swap(x, b, this.to - s, s);
/*      */         
/*  969 */         s = b - a;
/*  970 */         long t = d - c;
/*  971 */         if (s > 1L && t > 1L) { invokeAll(new ForkJoinQuickSortComp(x, this.from, this.from + s, this.comp), new ForkJoinQuickSortComp(x, this.to - t, this.to, this.comp)); }
/*  972 */         else if (s > 1L) { invokeAll((ForkJoinTask<?>[])new ForkJoinTask[] { new ForkJoinQuickSortComp(x, this.from, this.from + s, this.comp) }); }
/*  973 */         else { invokeAll((ForkJoinTask<?>[])new ForkJoinTask[] { new ForkJoinQuickSortComp(x, this.to - t, this.to, this.comp) }); }
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
/*      */   public static void parallelQuickSort(long[][] x, long from, long to, LongComparator comp) {
/*  992 */     ForkJoinPool pool = getPool();
/*  993 */     if (to - from < 8192L || pool.getParallelism() == 1) { quickSort(x, from, to, comp); }
/*      */     else
/*  995 */     { pool.invoke(new ForkJoinQuickSortComp(x, from, to, comp)); }
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
/*      */   public static void parallelQuickSort(long[][] x, LongComparator comp) {
/* 1012 */     parallelQuickSort(x, 0L, BigArrays.length(x), comp);
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
/*      */   public static long binarySearch(long[][] a, long from, long to, long key) {
/* 1036 */     to--;
/* 1037 */     while (from <= to) {
/* 1038 */       long mid = from + to >>> 1L;
/* 1039 */       long midVal = BigArrays.get(a, mid);
/* 1040 */       if (midVal < key) { from = mid + 1L; continue; }
/* 1041 */        if (midVal > key) { to = mid - 1L; continue; }
/* 1042 */        return mid;
/*      */     } 
/* 1044 */     return -(from + 1L);
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
/*      */   public static long binarySearch(long[][] a, long key) {
/* 1064 */     return binarySearch(a, 0L, BigArrays.length(a), key);
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
/*      */   public static long binarySearch(long[][] a, long from, long to, long key, LongComparator c) {
/* 1088 */     to--;
/* 1089 */     while (from <= to) {
/* 1090 */       long mid = from + to >>> 1L;
/* 1091 */       long midVal = BigArrays.get(a, mid);
/* 1092 */       int cmp = c.compare(midVal, key);
/* 1093 */       if (cmp < 0) { from = mid + 1L; continue; }
/* 1094 */        if (cmp > 0) { to = mid - 1L; continue; }
/* 1095 */        return mid;
/*      */     } 
/* 1097 */     return -(from + 1L);
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
/*      */   public static long binarySearch(long[][] a, long key, LongComparator c) {
/* 1118 */     return binarySearch(a, 0L, BigArrays.length(a), key, c);
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
/*      */   public static void radixSort(long[][] a) {
/* 1150 */     radixSort(a, 0L, BigArrays.length(a));
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
/*      */   public static void radixSort(long[][] a, long from, long to) {
/* 1173 */     int maxLevel = 7;
/* 1174 */     int stackSize = 1786;
/* 1175 */     long[] offsetStack = new long[1786];
/* 1176 */     int offsetPos = 0;
/* 1177 */     long[] lengthStack = new long[1786];
/* 1178 */     int lengthPos = 0;
/* 1179 */     int[] levelStack = new int[1786];
/* 1180 */     int levelPos = 0;
/* 1181 */     offsetStack[offsetPos++] = from;
/* 1182 */     lengthStack[lengthPos++] = to - from;
/* 1183 */     levelStack[levelPos++] = 0;
/* 1184 */     long[] count = new long[256];
/* 1185 */     long[] pos = new long[256];
/* 1186 */     byte[][] digit = ByteBigArrays.newBigArray(to - from);
/* 1187 */     while (offsetPos > 0) {
/* 1188 */       long first = offsetStack[--offsetPos];
/* 1189 */       long length = lengthStack[--lengthPos];
/* 1190 */       int level = levelStack[--levelPos];
/* 1191 */       int signMask = (level % 8 == 0) ? 128 : 0;
/* 1192 */       if (length < 40L) {
/* 1193 */         selectionSort(a, first, first + length);
/*      */         continue;
/*      */       } 
/* 1196 */       int shift = (7 - level % 8) * 8;
/*      */ 
/*      */       
/*      */       long i;
/*      */       
/* 1201 */       for (i = length; i-- != 0L; BigArrays.set(digit, i, (byte)(int)(BigArrays.get(a, first + i) >>> shift & 0xFFL ^ signMask)));
/* 1202 */       for (i = length; i-- != 0L; count[BigArrays.get(digit, i) & 0xFF] = count[BigArrays.get(digit, i) & 0xFF] + 1L);
/*      */       
/* 1204 */       int lastUsed = -1;
/* 1205 */       long p = 0L;
/* 1206 */       for (int j = 0; j < 256; j++) {
/* 1207 */         if (count[j] != 0L) {
/* 1208 */           lastUsed = j;
/* 1209 */           if (level < 7 && count[j] > 1L) {
/*      */ 
/*      */             
/* 1212 */             offsetStack[offsetPos++] = p + first;
/* 1213 */             lengthStack[lengthPos++] = count[j];
/* 1214 */             levelStack[levelPos++] = level + 1;
/*      */           } 
/*      */         } 
/* 1217 */         pos[j] = p += count[j];
/*      */       } 
/*      */       
/* 1220 */       long end = length - count[lastUsed];
/* 1221 */       count[lastUsed] = 0L;
/*      */       
/* 1223 */       int c = -1;
/* 1224 */       for (long l1 = 0L; l1 < end; l1 += count[c], count[c] = 0L) {
/* 1225 */         long t = BigArrays.get(a, l1 + first);
/* 1226 */         c = BigArrays.get(digit, l1) & 0xFF; long d;
/* 1227 */         for (pos[c] = pos[c] - 1L; (d = pos[c] - 1L) > l1; ) {
/* 1228 */           long z = t;
/* 1229 */           int zz = c;
/* 1230 */           t = BigArrays.get(a, d + first);
/* 1231 */           c = BigArrays.get(digit, d) & 0xFF;
/* 1232 */           BigArrays.set(a, d + first, z);
/* 1233 */           BigArrays.set(digit, d, (byte)zz);
/*      */         } 
/* 1235 */         BigArrays.set(a, l1 + first, t);
/*      */       } 
/*      */     } 
/*      */   }
/*      */   private static void selectionSort(long[][] a, long[][] b, long from, long to) {
/*      */     long i;
/* 1241 */     for (i = from; i < to - 1L; i++) {
/* 1242 */       long m = i; long j;
/* 1243 */       for (j = i + 1L; j < to; ) { if (BigArrays.get(a, j) < BigArrays.get(a, m) || (BigArrays.get(a, j) == BigArrays.get(a, m) && BigArrays.get(b, j) < BigArrays.get(b, m))) m = j;  j++; }
/* 1244 */        if (m != i) {
/* 1245 */         long t = BigArrays.get(a, i);
/* 1246 */         BigArrays.set(a, i, BigArrays.get(a, m));
/* 1247 */         BigArrays.set(a, m, t);
/* 1248 */         t = BigArrays.get(b, i);
/* 1249 */         BigArrays.set(b, i, BigArrays.get(b, m));
/* 1250 */         BigArrays.set(b, m, t);
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
/*      */   public static void radixSort(long[][] a, long[][] b) {
/* 1279 */     radixSort(a, b, 0L, BigArrays.length(a));
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
/*      */   public static void radixSort(long[][] a, long[][] b, long from, long to) {
/* 1309 */     int layers = 2;
/* 1310 */     if (BigArrays.length(a) != BigArrays.length(b)) throw new IllegalArgumentException("Array size mismatch."); 
/* 1311 */     int maxLevel = 15;
/* 1312 */     int stackSize = 3826;
/* 1313 */     long[] offsetStack = new long[3826];
/* 1314 */     int offsetPos = 0;
/* 1315 */     long[] lengthStack = new long[3826];
/* 1316 */     int lengthPos = 0;
/* 1317 */     int[] levelStack = new int[3826];
/* 1318 */     int levelPos = 0;
/* 1319 */     offsetStack[offsetPos++] = from;
/* 1320 */     lengthStack[lengthPos++] = to - from;
/* 1321 */     levelStack[levelPos++] = 0;
/* 1322 */     long[] count = new long[256];
/* 1323 */     long[] pos = new long[256];
/* 1324 */     byte[][] digit = ByteBigArrays.newBigArray(to - from);
/* 1325 */     while (offsetPos > 0) {
/* 1326 */       long first = offsetStack[--offsetPos];
/* 1327 */       long length = lengthStack[--lengthPos];
/* 1328 */       int level = levelStack[--levelPos];
/* 1329 */       int signMask = (level % 8 == 0) ? 128 : 0;
/* 1330 */       if (length < 40L) {
/* 1331 */         selectionSort(a, b, first, first + length);
/*      */         continue;
/*      */       } 
/* 1334 */       long[][] k = (level < 8) ? a : b;
/* 1335 */       int shift = (7 - level % 8) * 8;
/*      */ 
/*      */       
/*      */       long i;
/*      */       
/* 1340 */       for (i = length; i-- != 0L; BigArrays.set(digit, i, (byte)(int)(BigArrays.get(k, first + i) >>> shift & 0xFFL ^ signMask)));
/* 1341 */       for (i = length; i-- != 0L; count[BigArrays.get(digit, i) & 0xFF] = count[BigArrays.get(digit, i) & 0xFF] + 1L);
/*      */       
/* 1343 */       int lastUsed = -1;
/* 1344 */       long p = 0L;
/* 1345 */       for (int j = 0; j < 256; j++) {
/* 1346 */         if (count[j] != 0L) {
/* 1347 */           lastUsed = j;
/* 1348 */           if (level < 15 && count[j] > 1L) {
/* 1349 */             offsetStack[offsetPos++] = p + first;
/* 1350 */             lengthStack[lengthPos++] = count[j];
/* 1351 */             levelStack[levelPos++] = level + 1;
/*      */           } 
/*      */         } 
/* 1354 */         pos[j] = p += count[j];
/*      */       } 
/*      */       
/* 1357 */       long end = length - count[lastUsed];
/* 1358 */       count[lastUsed] = 0L;
/*      */       
/* 1360 */       int c = -1;
/* 1361 */       for (long l1 = 0L; l1 < end; l1 += count[c], count[c] = 0L) {
/* 1362 */         long t = BigArrays.get(a, l1 + first);
/* 1363 */         long u = BigArrays.get(b, l1 + first);
/* 1364 */         c = BigArrays.get(digit, l1) & 0xFF; long d;
/* 1365 */         for (pos[c] = pos[c] - 1L; (d = pos[c] - 1L) > l1; ) {
/* 1366 */           long z = t;
/* 1367 */           int zz = c;
/* 1368 */           t = BigArrays.get(a, d + first);
/* 1369 */           BigArrays.set(a, d + first, z);
/* 1370 */           z = u;
/* 1371 */           u = BigArrays.get(b, d + first);
/* 1372 */           BigArrays.set(b, d + first, z);
/* 1373 */           c = BigArrays.get(digit, d) & 0xFF;
/* 1374 */           BigArrays.set(digit, d, (byte)zz);
/*      */         } 
/* 1376 */         BigArrays.set(a, l1 + first, t);
/* 1377 */         BigArrays.set(b, l1 + first, u);
/*      */       } 
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   private static void insertionSortIndirect(long[][] perm, long[][] a, long[][] b, long from, long to) {
/* 1385 */     long i = from; while (true) { long t, j, u; if (++i < to)
/* 1386 */       { t = BigArrays.get(perm, i);
/* 1387 */         j = i;
/* 1388 */         u = BigArrays.get(perm, j - 1L); } else { break; }  if (BigArrays.get(a, t) < BigArrays.get(a, u) || (BigArrays.get(a, t) == BigArrays.get(a, u) && BigArrays.get(b, t) < BigArrays.get(b, u)));
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/* 1395 */       BigArrays.set(perm, j, t); }
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
/*      */   public static void radixSortIndirect(long[][] perm, long[][] a, long[][] b, boolean stable) {
/* 1422 */     ensureSameLength(a, b);
/* 1423 */     radixSortIndirect(perm, a, b, 0L, BigArrays.length(a), stable);
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
/*      */   public static void radixSortIndirect(long[][] perm, long[][] a, long[][] b, long from, long to, boolean stable) {
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
/*      */     //   17: invokestatic insertionSortIndirect : ([[J[[J[[JJJ)V
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
/*      */     //   203: ifeq -> 239
/*      */     //   206: aload #15
/*      */     //   208: aload #24
/*      */     //   210: aload_0
/*      */     //   211: lload #26
/*      */     //   213: invokestatic get : ([[JJ)J
/*      */     //   216: invokestatic get : ([[JJ)J
/*      */     //   219: iload #25
/*      */     //   221: lushr
/*      */     //   222: ldc2_w 255
/*      */     //   225: land
/*      */     //   226: iload #23
/*      */     //   228: i2l
/*      */     //   229: lxor
/*      */     //   230: l2i
/*      */     //   231: dup2
/*      */     //   232: laload
/*      */     //   233: lconst_1
/*      */     //   234: ladd
/*      */     //   235: lastore
/*      */     //   236: goto -> 193
/*      */     //   239: iconst_m1
/*      */     //   240: istore #26
/*      */     //   242: iload #7
/*      */     //   244: ifeq -> 251
/*      */     //   247: lconst_0
/*      */     //   248: goto -> 253
/*      */     //   251: lload #18
/*      */     //   253: lstore #27
/*      */     //   255: iconst_0
/*      */     //   256: istore #29
/*      */     //   258: iload #29
/*      */     //   260: sipush #256
/*      */     //   263: if_icmpge -> 302
/*      */     //   266: aload #15
/*      */     //   268: iload #29
/*      */     //   270: laload
/*      */     //   271: lconst_0
/*      */     //   272: lcmp
/*      */     //   273: ifeq -> 280
/*      */     //   276: iload #29
/*      */     //   278: istore #26
/*      */     //   280: aload #16
/*      */     //   282: iload #29
/*      */     //   284: lload #27
/*      */     //   286: aload #15
/*      */     //   288: iload #29
/*      */     //   290: laload
/*      */     //   291: ladd
/*      */     //   292: dup2
/*      */     //   293: lstore #27
/*      */     //   295: lastore
/*      */     //   296: iinc #29, 1
/*      */     //   299: goto -> 258
/*      */     //   302: iload #7
/*      */     //   304: ifeq -> 500
/*      */     //   307: lload #18
/*      */     //   309: lload #20
/*      */     //   311: ladd
/*      */     //   312: lstore #29
/*      */     //   314: lload #29
/*      */     //   316: dup2
/*      */     //   317: lconst_1
/*      */     //   318: lsub
/*      */     //   319: lstore #29
/*      */     //   321: lload #18
/*      */     //   323: lcmp
/*      */     //   324: ifeq -> 372
/*      */     //   327: aload #17
/*      */     //   329: aload #16
/*      */     //   331: aload #24
/*      */     //   333: aload_0
/*      */     //   334: lload #29
/*      */     //   336: invokestatic get : ([[JJ)J
/*      */     //   339: invokestatic get : ([[JJ)J
/*      */     //   342: iload #25
/*      */     //   344: lushr
/*      */     //   345: ldc2_w 255
/*      */     //   348: land
/*      */     //   349: iload #23
/*      */     //   351: i2l
/*      */     //   352: lxor
/*      */     //   353: l2i
/*      */     //   354: dup2
/*      */     //   355: laload
/*      */     //   356: lconst_1
/*      */     //   357: lsub
/*      */     //   358: dup2_x2
/*      */     //   359: lastore
/*      */     //   360: aload_0
/*      */     //   361: lload #29
/*      */     //   363: invokestatic get : ([[JJ)J
/*      */     //   366: invokestatic set : ([[JJJ)V
/*      */     //   369: goto -> 314
/*      */     //   372: aload #17
/*      */     //   374: lconst_0
/*      */     //   375: aload_0
/*      */     //   376: lload #18
/*      */     //   378: lload #20
/*      */     //   380: invokestatic copy : ([[JJ[[JJJ)V
/*      */     //   383: lload #18
/*      */     //   385: lstore #27
/*      */     //   387: iconst_0
/*      */     //   388: istore #29
/*      */     //   390: iload #29
/*      */     //   392: sipush #256
/*      */     //   395: if_icmpge -> 491
/*      */     //   398: iload #22
/*      */     //   400: bipush #15
/*      */     //   402: if_icmpge -> 475
/*      */     //   405: aload #15
/*      */     //   407: iload #29
/*      */     //   409: laload
/*      */     //   410: lconst_1
/*      */     //   411: lcmp
/*      */     //   412: ifle -> 475
/*      */     //   415: aload #15
/*      */     //   417: iload #29
/*      */     //   419: laload
/*      */     //   420: ldc2_w 1024
/*      */     //   423: lcmp
/*      */     //   424: ifge -> 446
/*      */     //   427: aload_0
/*      */     //   428: aload_1
/*      */     //   429: aload_2
/*      */     //   430: lload #27
/*      */     //   432: lload #27
/*      */     //   434: aload #15
/*      */     //   436: iload #29
/*      */     //   438: laload
/*      */     //   439: ladd
/*      */     //   440: invokestatic insertionSortIndirect : ([[J[[J[[JJJ)V
/*      */     //   443: goto -> 475
/*      */     //   446: aload #12
/*      */     //   448: iload #11
/*      */     //   450: lload #27
/*      */     //   452: lastore
/*      */     //   453: aload #13
/*      */     //   455: iload #11
/*      */     //   457: aload #15
/*      */     //   459: iload #29
/*      */     //   461: laload
/*      */     //   462: lastore
/*      */     //   463: aload #14
/*      */     //   465: iload #11
/*      */     //   467: iinc #11, 1
/*      */     //   470: iload #22
/*      */     //   472: iconst_1
/*      */     //   473: iadd
/*      */     //   474: iastore
/*      */     //   475: lload #27
/*      */     //   477: aload #15
/*      */     //   479: iload #29
/*      */     //   481: laload
/*      */     //   482: ladd
/*      */     //   483: lstore #27
/*      */     //   485: iinc #29, 1
/*      */     //   488: goto -> 390
/*      */     //   491: aload #15
/*      */     //   493: lconst_0
/*      */     //   494: invokestatic fill : ([JJ)V
/*      */     //   497: goto -> 732
/*      */     //   500: lload #18
/*      */     //   502: lload #20
/*      */     //   504: ladd
/*      */     //   505: aload #15
/*      */     //   507: iload #26
/*      */     //   509: laload
/*      */     //   510: lsub
/*      */     //   511: lstore #29
/*      */     //   513: iconst_m1
/*      */     //   514: istore #31
/*      */     //   516: lload #18
/*      */     //   518: lstore #32
/*      */     //   520: lload #32
/*      */     //   522: lload #29
/*      */     //   524: lcmp
/*      */     //   525: ifgt -> 732
/*      */     //   528: aload_0
/*      */     //   529: lload #32
/*      */     //   531: invokestatic get : ([[JJ)J
/*      */     //   534: lstore #36
/*      */     //   536: aload #24
/*      */     //   538: lload #36
/*      */     //   540: invokestatic get : ([[JJ)J
/*      */     //   543: iload #25
/*      */     //   545: lushr
/*      */     //   546: ldc2_w 255
/*      */     //   549: land
/*      */     //   550: iload #23
/*      */     //   552: i2l
/*      */     //   553: lxor
/*      */     //   554: l2i
/*      */     //   555: istore #31
/*      */     //   557: lload #32
/*      */     //   559: lload #29
/*      */     //   561: lcmp
/*      */     //   562: ifge -> 636
/*      */     //   565: aload #16
/*      */     //   567: iload #31
/*      */     //   569: dup2
/*      */     //   570: laload
/*      */     //   571: lconst_1
/*      */     //   572: lsub
/*      */     //   573: dup2_x2
/*      */     //   574: lastore
/*      */     //   575: dup2
/*      */     //   576: lstore #34
/*      */     //   578: lload #32
/*      */     //   580: lcmp
/*      */     //   581: ifle -> 628
/*      */     //   584: lload #36
/*      */     //   586: lstore #38
/*      */     //   588: aload_0
/*      */     //   589: lload #34
/*      */     //   591: invokestatic get : ([[JJ)J
/*      */     //   594: lstore #36
/*      */     //   596: aload_0
/*      */     //   597: lload #34
/*      */     //   599: lload #38
/*      */     //   601: invokestatic set : ([[JJJ)V
/*      */     //   604: aload #24
/*      */     //   606: lload #36
/*      */     //   608: invokestatic get : ([[JJ)J
/*      */     //   611: iload #25
/*      */     //   613: lushr
/*      */     //   614: ldc2_w 255
/*      */     //   617: land
/*      */     //   618: iload #23
/*      */     //   620: i2l
/*      */     //   621: lxor
/*      */     //   622: l2i
/*      */     //   623: istore #31
/*      */     //   625: goto -> 565
/*      */     //   628: aload_0
/*      */     //   629: lload #32
/*      */     //   631: lload #36
/*      */     //   633: invokestatic set : ([[JJJ)V
/*      */     //   636: iload #22
/*      */     //   638: bipush #15
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
/*      */     //   678: invokestatic insertionSortIndirect : ([[J[[J[[JJJ)V
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
/*      */     //   729: goto -> 520
/*      */     //   732: goto -> 113
/*      */     //   735: return
/*      */     // Line number table:
/*      */     //   Java source line number -> byte code offset
/*      */     //   #1451	-> 0
/*      */     //   #1452	-> 11
/*      */     //   #1453	-> 20
/*      */     //   #1455	-> 21
/*      */     //   #1456	-> 24
/*      */     //   #1457	-> 28
/*      */     //   #1458	-> 33
/*      */     //   #1459	-> 36
/*      */     //   #1460	-> 43
/*      */     //   #1461	-> 50
/*      */     //   #1462	-> 57
/*      */     //   #1463	-> 63
/*      */     //   #1464	-> 72
/*      */     //   #1465	-> 81
/*      */     //   #1466	-> 88
/*      */     //   #1467	-> 95
/*      */     //   #1468	-> 113
/*      */     //   #1469	-> 118
/*      */     //   #1470	-> 128
/*      */     //   #1471	-> 135
/*      */     //   #1472	-> 142
/*      */     //   #1473	-> 159
/*      */     //   #1474	-> 173
/*      */     //   #1479	-> 186
/*      */     //   #1481	-> 239
/*      */     //   #1482	-> 242
/*      */     //   #1483	-> 255
/*      */     //   #1484	-> 266
/*      */     //   #1485	-> 280
/*      */     //   #1483	-> 296
/*      */     //   #1487	-> 302
/*      */     //   #1488	-> 307
/*      */     //   #1489	-> 372
/*      */     //   #1490	-> 383
/*      */     //   #1491	-> 387
/*      */     //   #1492	-> 398
/*      */     //   #1493	-> 415
/*      */     //   #1495	-> 446
/*      */     //   #1496	-> 453
/*      */     //   #1497	-> 463
/*      */     //   #1500	-> 475
/*      */     //   #1491	-> 485
/*      */     //   #1502	-> 491
/*      */     //   #1504	-> 500
/*      */     //   #1506	-> 513
/*      */     //   #1507	-> 516
/*      */     //   #1508	-> 528
/*      */     //   #1509	-> 536
/*      */     //   #1510	-> 557
/*      */     //   #1511	-> 565
/*      */     //   #1512	-> 584
/*      */     //   #1513	-> 588
/*      */     //   #1514	-> 596
/*      */     //   #1515	-> 604
/*      */     //   #1516	-> 625
/*      */     //   #1517	-> 628
/*      */     //   #1519	-> 636
/*      */     //   #1520	-> 653
/*      */     //   #1522	-> 684
/*      */     //   #1523	-> 691
/*      */     //   #1524	-> 701
/*      */     //   #1507	-> 713
/*      */     //   #1529	-> 732
/*      */     //   #1530	-> 735
/*      */     // Local variable table:
/*      */     //   start	length	slot	name	descriptor
/*      */     //   193	46	26	i	J
/*      */     //   258	44	29	i	I
/*      */     //   314	58	29	i	J
/*      */     //   390	101	29	i	I
/*      */     //   588	37	38	z	J
/*      */     //   578	58	34	d	J
/*      */     //   536	177	36	t	J
/*      */     //   520	212	32	i	J
/*      */     //   513	219	29	end	J
/*      */     //   516	216	31	c	I
/*      */     //   128	604	18	first	J
/*      */     //   135	597	20	length	J
/*      */     //   142	590	22	level	I
/*      */     //   159	573	23	signMask	I
/*      */     //   173	559	24	k	[[J
/*      */     //   186	546	25	shift	I
/*      */     //   242	490	26	lastUsed	I
/*      */     //   255	477	27	p	J
/*      */     //   0	736	0	perm	[[J
/*      */     //   0	736	1	a	[[J
/*      */     //   0	736	2	b	[[J
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
/*      */   public static long[][] shuffle(long[][] a, long from, long to, Random random) {
/* 1542 */     return BigArrays.shuffle(a, from, to, random);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static long[][] shuffle(long[][] a, Random random) {
/* 1553 */     return BigArrays.shuffle(a, random);
/*      */   }
/*      */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\i\\unimi\dsi\fastutil\longs\LongBigArrays.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */