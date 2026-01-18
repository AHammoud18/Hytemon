/*      */ package it.unimi.dsi.fastutil.ints;
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
/*      */ import java.util.concurrent.atomic.AtomicIntegerArray;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ public final class IntBigArrays
/*      */ {
/*   71 */   public static final int[][] EMPTY_BIG_ARRAY = new int[0][];
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*   79 */   public static final int[][] DEFAULT_EMPTY_BIG_ARRAY = new int[0][];
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
/*      */   public static int get(int[][] array, long index) {
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
/*      */   public static void set(int[][] array, long index, int value) {
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
/*      */   public static void swap(int[][] array, long first, long second) {
/*  117 */     int t = array[BigArrays.segment(first)][BigArrays.displacement(first)];
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
/*      */   public static void add(int[][] array, long index, int incr) {
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
/*      */   public static void mul(int[][] array, long index, int factor) {
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
/*      */   public static void incr(int[][] array, long index) {
/*  157 */     array[BigArrays.segment(index)][BigArrays.displacement(index)] = array[BigArrays.segment(index)][BigArrays.displacement(index)] + 1;
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
/*      */   public static void decr(int[][] array, long index) {
/*  169 */     array[BigArrays.segment(index)][BigArrays.displacement(index)] = array[BigArrays.segment(index)][BigArrays.displacement(index)] - 1;
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
/*      */   public static long length(int[][] array) {
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
/*      */   public static void copy(int[][] srcArray, long srcPos, int[][] destArray, long destPos, long length) {
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
/*      */   public static void copyFromBig(int[][] srcArray, long srcPos, int[] destArray, int destPos, int length) {
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
/*      */   public static void copyToBig(int[] srcArray, int srcPos, int[][] destArray, long destPos, long length) {
/*  231 */     BigArrays.copyToBig(srcArray, srcPos, destArray, destPos, length);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static int[][] newBigArray(long length) {
/*  241 */     if (length == 0L) return EMPTY_BIG_ARRAY; 
/*  242 */     BigArrays.ensureLength(length);
/*  243 */     int baseLength = (int)(length + 134217727L >>> 27L);
/*  244 */     int[][] base = new int[baseLength][];
/*  245 */     int residual = (int)(length & 0x7FFFFFFL);
/*  246 */     if (residual != 0)
/*  247 */     { for (int i = 0; i < baseLength - 1; ) { base[i] = new int[134217728]; i++; }
/*  248 */        base[baseLength - 1] = new int[residual]; }
/*  249 */     else { for (int i = 0; i < baseLength; ) { base[i] = new int[134217728]; i++; }  }
/*  250 */      return base;
/*      */   }
/*      */ 
/*      */   
/*  254 */   public static final AtomicIntegerArray[] EMPTY_BIG_ATOMIC_ARRAY = new AtomicIntegerArray[0];
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static AtomicIntegerArray[] newBigAtomicArray(long length) {
/*  263 */     if (length == 0L) return EMPTY_BIG_ATOMIC_ARRAY; 
/*  264 */     BigArrays.ensureLength(length);
/*  265 */     int baseLength = (int)(length + 134217727L >>> 27L);
/*  266 */     AtomicIntegerArray[] base = new AtomicIntegerArray[baseLength];
/*  267 */     int residual = (int)(length & 0x7FFFFFFL);
/*  268 */     if (residual != 0)
/*  269 */     { for (int i = 0; i < baseLength - 1; ) { base[i] = new AtomicIntegerArray(134217728); i++; }
/*  270 */        base[baseLength - 1] = new AtomicIntegerArray(residual); }
/*  271 */     else { for (int i = 0; i < baseLength; ) { base[i] = new AtomicIntegerArray(134217728); i++; }  }
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
/*      */   public static int[][] wrap(int[] array) {
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
/*      */   public static int[][] ensureCapacity(int[][] array, long length) {
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
/*      */   public static int[][] forceCapacity(int[][] array, long length, long preserve) {
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
/*      */   public static int[][] ensureCapacity(int[][] array, long length, long preserve) {
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
/*      */   public static int[][] grow(int[][] array, long length) {
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
/*      */   public static int[][] grow(int[][] array, long length, long preserve) {
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
/*      */   public static int[][] trim(int[][] array, long length) {
/*  425 */     BigArrays.ensureLength(length);
/*  426 */     long oldLength = length(array);
/*  427 */     if (length >= oldLength) return array; 
/*  428 */     int baseLength = (int)(length + 134217727L >>> 27L);
/*  429 */     int[][] base = Arrays.<int[]>copyOf(array, baseLength);
/*  430 */     int residual = (int)(length & 0x7FFFFFFL);
/*  431 */     if (residual != 0) base[baseLength - 1] = IntArrays.trim(base[baseLength - 1], residual); 
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
/*      */   public static int[][] setLength(int[][] array, long length) {
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
/*      */   public static int[][] copy(int[][] array, long offset, long length) {
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
/*      */   public static int[][] copy(int[][] array) {
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
/*      */   public static void fill(int[][] array, int value) {
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
/*      */   public static void fill(int[][] array, long from, long to, int value) {
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
/*      */   public static boolean equals(int[][] a1, int[][] a2) {
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
/*      */   public static String toString(int[][] a) {
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
/*      */   public static void ensureFromTo(int[][] a, long from, long to) {
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
/*      */   public static void ensureOffsetLength(int[][] a, long offset, long length) {
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
/*      */   public static void ensureSameLength(int[][] a, int[][] b) {
/*  595 */     if (length(a) != length(b)) throw new IllegalArgumentException("Array size mismatch: " + length(a) + " != " + length(b)); 
/*      */   }
/*      */   
/*      */   private static final class BigArrayHashStrategy implements Hash.Strategy<int[][]>, Serializable {
/*      */     private static final long serialVersionUID = -7046029254386353129L;
/*      */     
/*      */     private BigArrayHashStrategy() {}
/*      */     
/*      */     public int hashCode(int[][] o) {
/*  604 */       return Arrays.deepHashCode((Object[])o);
/*      */     }
/*      */ 
/*      */     
/*      */     public boolean equals(int[][] a, int[][] b) {
/*  609 */       return IntBigArrays.equals(a, b);
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
/*      */   private static final int DIGITS_PER_ELEMENT = 4;
/*      */   private static final int RADIXSORT_NO_REC = 1024;
/*      */   
/*      */   private static ForkJoinPool getPool() {
/*  629 */     ForkJoinPool current = ForkJoinTask.getPool();
/*  630 */     return (current == null) ? ForkJoinPool.commonPool() : current;
/*      */   }
/*      */   
/*      */   private static void swap(int[][] x, long a, long b, long n) {
/*  634 */     for (int i = 0; i < n; ) { BigArrays.swap(x, a, b); i++; a++; b++; }
/*      */   
/*      */   }
/*      */   private static long med3(int[][] x, long a, long b, long c, IntComparator comp) {
/*  638 */     int ab = comp.compare(BigArrays.get(x, a), BigArrays.get(x, b));
/*  639 */     int ac = comp.compare(BigArrays.get(x, a), BigArrays.get(x, c));
/*  640 */     int bc = comp.compare(BigArrays.get(x, b), BigArrays.get(x, c));
/*  641 */     return (ab < 0) ? ((bc < 0) ? b : ((ac < 0) ? c : a)) : ((bc > 0) ? b : ((ac > 0) ? c : a));
/*      */   }
/*      */   private static void selectionSort(int[][] a, long from, long to, IntComparator comp) {
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
/*      */   public static void quickSort(int[][] x, long from, long to, IntComparator comp) {
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
/*  686 */     int v = BigArrays.get(x, m);
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
/*      */     BigArrays.swap(x, b++, c--); } private static long med3(int[][] x, long a, long b, long c) {
/*  714 */     int ab = Integer.compare(BigArrays.get(x, a), BigArrays.get(x, b));
/*  715 */     int ac = Integer.compare(BigArrays.get(x, a), BigArrays.get(x, c));
/*  716 */     int bc = Integer.compare(BigArrays.get(x, b), BigArrays.get(x, c));
/*  717 */     return (ab < 0) ? ((bc < 0) ? b : ((ac < 0) ? c : a)) : ((bc > 0) ? b : ((ac > 0) ? c : a));
/*      */   }
/*      */   private static void selectionSort(int[][] a, long from, long to) {
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
/*      */   public static void quickSort(int[][] x, IntComparator comp) {
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
/*      */   public static void quickSort(int[][] x, long from, long to) {
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
/*  778 */     int v = BigArrays.get(x, m);
/*      */     
/*  780 */     long a = from, b = a, c = to - 1L, d = c;
/*      */     
/*      */     int comparison;
/*  783 */     while (b <= c && (comparison = Integer.compare(BigArrays.get(x, b), v)) <= 0) {
/*  784 */       if (comparison == 0) BigArrays.swap(x, a++, b); 
/*  785 */       b++;
/*      */     } 
/*  787 */     while (c >= b && (comparison = Integer.compare(BigArrays.get(x, c), v)) >= 0) {
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
/*      */   public static void quickSort(int[][] x) {
/*  816 */     quickSort(x, 0L, BigArrays.length(x));
/*      */   }
/*      */   
/*      */   protected static class ForkJoinQuickSort extends RecursiveAction {
/*      */     private static final long serialVersionUID = 1L;
/*      */     private final long from;
/*      */     private final long to;
/*      */     private final int[][] x;
/*      */     
/*      */     public ForkJoinQuickSort(int[][] x, long from, long to) {
/*  826 */       this.from = from;
/*  827 */       this.to = to;
/*  828 */       this.x = x;
/*      */     }
/*      */ 
/*      */ 
/*      */     
/*      */     protected void compute() {
/*  834 */       int[][] x = this.x;
/*  835 */       long len = this.to - this.from;
/*  836 */       if (len < 8192L) {
/*  837 */         IntBigArrays.quickSort(x, this.from, this.to);
/*      */         
/*      */         return;
/*      */       } 
/*  841 */       long m = this.from + len / 2L;
/*  842 */       long l = this.from;
/*  843 */       long n = this.to - 1L;
/*  844 */       long s = len / 8L;
/*  845 */       l = IntBigArrays.med3(x, l, l + s, l + 2L * s);
/*  846 */       m = IntBigArrays.med3(x, m - s, m, m + s);
/*  847 */       n = IntBigArrays.med3(x, n - 2L * s, n - s, n);
/*  848 */       m = IntBigArrays.med3(x, l, m, n);
/*  849 */       int v = BigArrays.get(x, m);
/*      */       
/*  851 */       long a = this.from, b = a, c = this.to - 1L, d = c;
/*      */       
/*      */       int comparison;
/*  854 */       while (b <= c && (comparison = Integer.compare(BigArrays.get(x, b), v)) <= 0) {
/*  855 */         if (comparison == 0) BigArrays.swap(x, a++, b); 
/*  856 */         b++;
/*      */       } 
/*  858 */       while (c >= b && (comparison = Integer.compare(BigArrays.get(x, c), v)) >= 0) {
/*  859 */         if (comparison == 0) BigArrays.swap(x, c, d--); 
/*  860 */         c--;
/*      */       } 
/*  862 */       if (b > c) {
/*      */ 
/*      */ 
/*      */ 
/*      */         
/*  867 */         s = Math.min(a - this.from, b - a);
/*  868 */         IntBigArrays.swap(x, this.from, b - s, s);
/*  869 */         s = Math.min(d - c, this.to - d - 1L);
/*  870 */         IntBigArrays.swap(x, b, this.to - s, s);
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
/*      */   public static void parallelQuickSort(int[][] x, long from, long to) {
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
/*      */   public static void parallelQuickSort(int[][] x) {
/*  912 */     parallelQuickSort(x, 0L, BigArrays.length(x));
/*      */   }
/*      */   
/*      */   protected static class ForkJoinQuickSortComp extends RecursiveAction {
/*      */     private static final long serialVersionUID = 1L;
/*      */     private final long from;
/*      */     private final long to;
/*      */     private final int[][] x;
/*      */     private final IntComparator comp;
/*      */     
/*      */     public ForkJoinQuickSortComp(int[][] x, long from, long to, IntComparator comp) {
/*  923 */       this.from = from;
/*  924 */       this.to = to;
/*  925 */       this.x = x;
/*  926 */       this.comp = comp;
/*      */     }
/*      */ 
/*      */     
/*      */     protected void compute() {
/*  931 */       int[][] x = this.x;
/*  932 */       long len = this.to - this.from;
/*  933 */       if (len < 8192L) {
/*  934 */         IntBigArrays.quickSort(x, this.from, this.to, this.comp);
/*      */         
/*      */         return;
/*      */       } 
/*  938 */       long m = this.from + len / 2L;
/*  939 */       long l = this.from;
/*  940 */       long n = this.to - 1L;
/*  941 */       long s = len / 8L;
/*  942 */       l = IntBigArrays.med3(x, l, l + s, l + 2L * s, this.comp);
/*  943 */       m = IntBigArrays.med3(x, m - s, m, m + s, this.comp);
/*  944 */       n = IntBigArrays.med3(x, n - 2L * s, n - s, n, this.comp);
/*  945 */       m = IntBigArrays.med3(x, l, m, n, this.comp);
/*  946 */       int v = BigArrays.get(x, m);
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
/*  965 */         IntBigArrays.swap(x, this.from, b - s, s);
/*  966 */         s = Math.min(d - c, this.to - d - 1L);
/*  967 */         IntBigArrays.swap(x, b, this.to - s, s);
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
/*      */   public static void parallelQuickSort(int[][] x, long from, long to, IntComparator comp) {
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
/*      */   public static void parallelQuickSort(int[][] x, IntComparator comp) {
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
/*      */   public static long binarySearch(int[][] a, long from, long to, int key) {
/* 1036 */     to--;
/* 1037 */     while (from <= to) {
/* 1038 */       long mid = from + to >>> 1L;
/* 1039 */       int midVal = BigArrays.get(a, mid);
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
/*      */   public static long binarySearch(int[][] a, int key) {
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
/*      */   public static long binarySearch(int[][] a, long from, long to, int key, IntComparator c) {
/* 1088 */     to--;
/* 1089 */     while (from <= to) {
/* 1090 */       long mid = from + to >>> 1L;
/* 1091 */       int midVal = BigArrays.get(a, mid);
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
/*      */   public static long binarySearch(int[][] a, int key, IntComparator c) {
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
/*      */   public static void radixSort(int[][] a) {
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
/*      */   public static void radixSort(int[][] a, long from, long to) {
/* 1173 */     int maxLevel = 3;
/* 1174 */     int stackSize = 766;
/* 1175 */     long[] offsetStack = new long[766];
/* 1176 */     int offsetPos = 0;
/* 1177 */     long[] lengthStack = new long[766];
/* 1178 */     int lengthPos = 0;
/* 1179 */     int[] levelStack = new int[766];
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
/* 1191 */       int signMask = (level % 4 == 0) ? 128 : 0;
/* 1192 */       if (length < 40L) {
/* 1193 */         selectionSort(a, first, first + length);
/*      */         continue;
/*      */       } 
/* 1196 */       int shift = (3 - level % 4) * 8;
/*      */ 
/*      */       
/*      */       long i;
/*      */       
/* 1201 */       for (i = length; i-- != 0L; BigArrays.set(digit, i, (byte)(BigArrays.get(a, first + i) >>> shift & 0xFF ^ signMask)));
/* 1202 */       for (i = length; i-- != 0L; count[BigArrays.get(digit, i) & 0xFF] = count[BigArrays.get(digit, i) & 0xFF] + 1L);
/*      */       
/* 1204 */       int lastUsed = -1;
/* 1205 */       long p = 0L;
/* 1206 */       for (int j = 0; j < 256; j++) {
/* 1207 */         if (count[j] != 0L) {
/* 1208 */           lastUsed = j;
/* 1209 */           if (level < 3 && count[j] > 1L) {
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
/* 1225 */         int t = BigArrays.get(a, l1 + first);
/* 1226 */         c = BigArrays.get(digit, l1) & 0xFF; long d;
/* 1227 */         for (pos[c] = pos[c] - 1L; (d = pos[c] - 1L) > l1; ) {
/* 1228 */           int z = t;
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
/*      */   private static void selectionSort(int[][] a, int[][] b, long from, long to) {
/*      */     long i;
/* 1241 */     for (i = from; i < to - 1L; i++) {
/* 1242 */       long m = i; long j;
/* 1243 */       for (j = i + 1L; j < to; ) { if (BigArrays.get(a, j) < BigArrays.get(a, m) || (BigArrays.get(a, j) == BigArrays.get(a, m) && BigArrays.get(b, j) < BigArrays.get(b, m))) m = j;  j++; }
/* 1244 */        if (m != i) {
/* 1245 */         int t = BigArrays.get(a, i);
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
/*      */   public static void radixSort(int[][] a, int[][] b) {
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
/*      */   public static void radixSort(int[][] a, int[][] b, long from, long to) {
/* 1309 */     int layers = 2;
/* 1310 */     if (BigArrays.length(a) != BigArrays.length(b)) throw new IllegalArgumentException("Array size mismatch."); 
/* 1311 */     int maxLevel = 7;
/* 1312 */     int stackSize = 1786;
/* 1313 */     long[] offsetStack = new long[1786];
/* 1314 */     int offsetPos = 0;
/* 1315 */     long[] lengthStack = new long[1786];
/* 1316 */     int lengthPos = 0;
/* 1317 */     int[] levelStack = new int[1786];
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
/* 1329 */       int signMask = (level % 4 == 0) ? 128 : 0;
/* 1330 */       if (length < 40L) {
/* 1331 */         selectionSort(a, b, first, first + length);
/*      */         continue;
/*      */       } 
/* 1334 */       int[][] k = (level < 4) ? a : b;
/* 1335 */       int shift = (3 - level % 4) * 8;
/*      */ 
/*      */       
/*      */       long i;
/*      */       
/* 1340 */       for (i = length; i-- != 0L; BigArrays.set(digit, i, (byte)(BigArrays.get(k, first + i) >>> shift & 0xFF ^ signMask)));
/* 1341 */       for (i = length; i-- != 0L; count[BigArrays.get(digit, i) & 0xFF] = count[BigArrays.get(digit, i) & 0xFF] + 1L);
/*      */       
/* 1343 */       int lastUsed = -1;
/* 1344 */       long p = 0L;
/* 1345 */       for (int j = 0; j < 256; j++) {
/* 1346 */         if (count[j] != 0L) {
/* 1347 */           lastUsed = j;
/* 1348 */           if (level < 7 && count[j] > 1L) {
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
/* 1362 */         int t = BigArrays.get(a, l1 + first);
/* 1363 */         int u = BigArrays.get(b, l1 + first);
/* 1364 */         c = BigArrays.get(digit, l1) & 0xFF; long d;
/* 1365 */         for (pos[c] = pos[c] - 1L; (d = pos[c] - 1L) > l1; ) {
/* 1366 */           int z = t;
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
/*      */   private static void insertionSortIndirect(long[][] perm, int[][] a, int[][] b, long from, long to) {
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
/*      */   public static void radixSortIndirect(long[][] perm, int[][] a, int[][] b, boolean stable) {
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
/*      */   public static void radixSortIndirect(long[][] perm, int[][] a, int[][] b, long from, long to, boolean stable) {
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
/*      */     //   17: invokestatic insertionSortIndirect : ([[J[[I[[IJJ)V
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
/*      */     //   115: ifle -> 723
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
/*      */     //   199: ifeq -> 233
/*      */     //   202: aload #15
/*      */     //   204: aload #24
/*      */     //   206: aload_0
/*      */     //   207: lload #26
/*      */     //   209: invokestatic get : ([[JJ)J
/*      */     //   212: invokestatic get : ([[IJ)I
/*      */     //   215: iload #25
/*      */     //   217: iushr
/*      */     //   218: sipush #255
/*      */     //   221: iand
/*      */     //   222: iload #23
/*      */     //   224: ixor
/*      */     //   225: dup2
/*      */     //   226: laload
/*      */     //   227: lconst_1
/*      */     //   228: ladd
/*      */     //   229: lastore
/*      */     //   230: goto -> 189
/*      */     //   233: iconst_m1
/*      */     //   234: istore #26
/*      */     //   236: iload #7
/*      */     //   238: ifeq -> 245
/*      */     //   241: lconst_0
/*      */     //   242: goto -> 247
/*      */     //   245: lload #18
/*      */     //   247: lstore #27
/*      */     //   249: iconst_0
/*      */     //   250: istore #29
/*      */     //   252: iload #29
/*      */     //   254: sipush #256
/*      */     //   257: if_icmpge -> 296
/*      */     //   260: aload #15
/*      */     //   262: iload #29
/*      */     //   264: laload
/*      */     //   265: lconst_0
/*      */     //   266: lcmp
/*      */     //   267: ifeq -> 274
/*      */     //   270: iload #29
/*      */     //   272: istore #26
/*      */     //   274: aload #16
/*      */     //   276: iload #29
/*      */     //   278: lload #27
/*      */     //   280: aload #15
/*      */     //   282: iload #29
/*      */     //   284: laload
/*      */     //   285: ladd
/*      */     //   286: dup2
/*      */     //   287: lstore #27
/*      */     //   289: lastore
/*      */     //   290: iinc #29, 1
/*      */     //   293: goto -> 252
/*      */     //   296: iload #7
/*      */     //   298: ifeq -> 492
/*      */     //   301: lload #18
/*      */     //   303: lload #20
/*      */     //   305: ladd
/*      */     //   306: lstore #29
/*      */     //   308: lload #29
/*      */     //   310: dup2
/*      */     //   311: lconst_1
/*      */     //   312: lsub
/*      */     //   313: lstore #29
/*      */     //   315: lload #18
/*      */     //   317: lcmp
/*      */     //   318: ifeq -> 364
/*      */     //   321: aload #17
/*      */     //   323: aload #16
/*      */     //   325: aload #24
/*      */     //   327: aload_0
/*      */     //   328: lload #29
/*      */     //   330: invokestatic get : ([[JJ)J
/*      */     //   333: invokestatic get : ([[IJ)I
/*      */     //   336: iload #25
/*      */     //   338: iushr
/*      */     //   339: sipush #255
/*      */     //   342: iand
/*      */     //   343: iload #23
/*      */     //   345: ixor
/*      */     //   346: dup2
/*      */     //   347: laload
/*      */     //   348: lconst_1
/*      */     //   349: lsub
/*      */     //   350: dup2_x2
/*      */     //   351: lastore
/*      */     //   352: aload_0
/*      */     //   353: lload #29
/*      */     //   355: invokestatic get : ([[JJ)J
/*      */     //   358: invokestatic set : ([[JJJ)V
/*      */     //   361: goto -> 308
/*      */     //   364: aload #17
/*      */     //   366: lconst_0
/*      */     //   367: aload_0
/*      */     //   368: lload #18
/*      */     //   370: lload #20
/*      */     //   372: invokestatic copy : ([[JJ[[JJJ)V
/*      */     //   375: lload #18
/*      */     //   377: lstore #27
/*      */     //   379: iconst_0
/*      */     //   380: istore #29
/*      */     //   382: iload #29
/*      */     //   384: sipush #256
/*      */     //   387: if_icmpge -> 483
/*      */     //   390: iload #22
/*      */     //   392: bipush #7
/*      */     //   394: if_icmpge -> 467
/*      */     //   397: aload #15
/*      */     //   399: iload #29
/*      */     //   401: laload
/*      */     //   402: lconst_1
/*      */     //   403: lcmp
/*      */     //   404: ifle -> 467
/*      */     //   407: aload #15
/*      */     //   409: iload #29
/*      */     //   411: laload
/*      */     //   412: ldc2_w 1024
/*      */     //   415: lcmp
/*      */     //   416: ifge -> 438
/*      */     //   419: aload_0
/*      */     //   420: aload_1
/*      */     //   421: aload_2
/*      */     //   422: lload #27
/*      */     //   424: lload #27
/*      */     //   426: aload #15
/*      */     //   428: iload #29
/*      */     //   430: laload
/*      */     //   431: ladd
/*      */     //   432: invokestatic insertionSortIndirect : ([[J[[I[[IJJ)V
/*      */     //   435: goto -> 467
/*      */     //   438: aload #12
/*      */     //   440: iload #11
/*      */     //   442: lload #27
/*      */     //   444: lastore
/*      */     //   445: aload #13
/*      */     //   447: iload #11
/*      */     //   449: aload #15
/*      */     //   451: iload #29
/*      */     //   453: laload
/*      */     //   454: lastore
/*      */     //   455: aload #14
/*      */     //   457: iload #11
/*      */     //   459: iinc #11, 1
/*      */     //   462: iload #22
/*      */     //   464: iconst_1
/*      */     //   465: iadd
/*      */     //   466: iastore
/*      */     //   467: lload #27
/*      */     //   469: aload #15
/*      */     //   471: iload #29
/*      */     //   473: laload
/*      */     //   474: ladd
/*      */     //   475: lstore #27
/*      */     //   477: iinc #29, 1
/*      */     //   480: goto -> 382
/*      */     //   483: aload #15
/*      */     //   485: lconst_0
/*      */     //   486: invokestatic fill : ([JJ)V
/*      */     //   489: goto -> 720
/*      */     //   492: lload #18
/*      */     //   494: lload #20
/*      */     //   496: ladd
/*      */     //   497: aload #15
/*      */     //   499: iload #26
/*      */     //   501: laload
/*      */     //   502: lsub
/*      */     //   503: lstore #29
/*      */     //   505: iconst_m1
/*      */     //   506: istore #31
/*      */     //   508: lload #18
/*      */     //   510: lstore #32
/*      */     //   512: lload #32
/*      */     //   514: lload #29
/*      */     //   516: lcmp
/*      */     //   517: ifgt -> 720
/*      */     //   520: aload_0
/*      */     //   521: lload #32
/*      */     //   523: invokestatic get : ([[JJ)J
/*      */     //   526: lstore #36
/*      */     //   528: aload #24
/*      */     //   530: lload #36
/*      */     //   532: invokestatic get : ([[IJ)I
/*      */     //   535: iload #25
/*      */     //   537: iushr
/*      */     //   538: sipush #255
/*      */     //   541: iand
/*      */     //   542: iload #23
/*      */     //   544: ixor
/*      */     //   545: istore #31
/*      */     //   547: lload #32
/*      */     //   549: lload #29
/*      */     //   551: lcmp
/*      */     //   552: ifge -> 624
/*      */     //   555: aload #16
/*      */     //   557: iload #31
/*      */     //   559: dup2
/*      */     //   560: laload
/*      */     //   561: lconst_1
/*      */     //   562: lsub
/*      */     //   563: dup2_x2
/*      */     //   564: lastore
/*      */     //   565: dup2
/*      */     //   566: lstore #34
/*      */     //   568: lload #32
/*      */     //   570: lcmp
/*      */     //   571: ifle -> 616
/*      */     //   574: lload #36
/*      */     //   576: lstore #38
/*      */     //   578: aload_0
/*      */     //   579: lload #34
/*      */     //   581: invokestatic get : ([[JJ)J
/*      */     //   584: lstore #36
/*      */     //   586: aload_0
/*      */     //   587: lload #34
/*      */     //   589: lload #38
/*      */     //   591: invokestatic set : ([[JJJ)V
/*      */     //   594: aload #24
/*      */     //   596: lload #36
/*      */     //   598: invokestatic get : ([[IJ)I
/*      */     //   601: iload #25
/*      */     //   603: iushr
/*      */     //   604: sipush #255
/*      */     //   607: iand
/*      */     //   608: iload #23
/*      */     //   610: ixor
/*      */     //   611: istore #31
/*      */     //   613: goto -> 555
/*      */     //   616: aload_0
/*      */     //   617: lload #32
/*      */     //   619: lload #36
/*      */     //   621: invokestatic set : ([[JJJ)V
/*      */     //   624: iload #22
/*      */     //   626: bipush #7
/*      */     //   628: if_icmpge -> 701
/*      */     //   631: aload #15
/*      */     //   633: iload #31
/*      */     //   635: laload
/*      */     //   636: lconst_1
/*      */     //   637: lcmp
/*      */     //   638: ifle -> 701
/*      */     //   641: aload #15
/*      */     //   643: iload #31
/*      */     //   645: laload
/*      */     //   646: ldc2_w 1024
/*      */     //   649: lcmp
/*      */     //   650: ifge -> 672
/*      */     //   653: aload_0
/*      */     //   654: aload_1
/*      */     //   655: aload_2
/*      */     //   656: lload #32
/*      */     //   658: lload #32
/*      */     //   660: aload #15
/*      */     //   662: iload #31
/*      */     //   664: laload
/*      */     //   665: ladd
/*      */     //   666: invokestatic insertionSortIndirect : ([[J[[I[[IJJ)V
/*      */     //   669: goto -> 701
/*      */     //   672: aload #12
/*      */     //   674: iload #11
/*      */     //   676: lload #32
/*      */     //   678: lastore
/*      */     //   679: aload #13
/*      */     //   681: iload #11
/*      */     //   683: aload #15
/*      */     //   685: iload #31
/*      */     //   687: laload
/*      */     //   688: lastore
/*      */     //   689: aload #14
/*      */     //   691: iload #11
/*      */     //   693: iinc #11, 1
/*      */     //   696: iload #22
/*      */     //   698: iconst_1
/*      */     //   699: iadd
/*      */     //   700: iastore
/*      */     //   701: lload #32
/*      */     //   703: aload #15
/*      */     //   705: iload #31
/*      */     //   707: laload
/*      */     //   708: ladd
/*      */     //   709: lstore #32
/*      */     //   711: aload #15
/*      */     //   713: iload #31
/*      */     //   715: lconst_0
/*      */     //   716: lastore
/*      */     //   717: goto -> 512
/*      */     //   720: goto -> 113
/*      */     //   723: return
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
/*      */     //   #1473	-> 158
/*      */     //   #1474	-> 171
/*      */     //   #1479	-> 182
/*      */     //   #1481	-> 233
/*      */     //   #1482	-> 236
/*      */     //   #1483	-> 249
/*      */     //   #1484	-> 260
/*      */     //   #1485	-> 274
/*      */     //   #1483	-> 290
/*      */     //   #1487	-> 296
/*      */     //   #1488	-> 301
/*      */     //   #1489	-> 364
/*      */     //   #1490	-> 375
/*      */     //   #1491	-> 379
/*      */     //   #1492	-> 390
/*      */     //   #1493	-> 407
/*      */     //   #1495	-> 438
/*      */     //   #1496	-> 445
/*      */     //   #1497	-> 455
/*      */     //   #1500	-> 467
/*      */     //   #1491	-> 477
/*      */     //   #1502	-> 483
/*      */     //   #1504	-> 492
/*      */     //   #1506	-> 505
/*      */     //   #1507	-> 508
/*      */     //   #1508	-> 520
/*      */     //   #1509	-> 528
/*      */     //   #1510	-> 547
/*      */     //   #1511	-> 555
/*      */     //   #1512	-> 574
/*      */     //   #1513	-> 578
/*      */     //   #1514	-> 586
/*      */     //   #1515	-> 594
/*      */     //   #1516	-> 613
/*      */     //   #1517	-> 616
/*      */     //   #1519	-> 624
/*      */     //   #1520	-> 641
/*      */     //   #1522	-> 672
/*      */     //   #1523	-> 679
/*      */     //   #1524	-> 689
/*      */     //   #1507	-> 701
/*      */     //   #1529	-> 720
/*      */     //   #1530	-> 723
/*      */     // Local variable table:
/*      */     //   start	length	slot	name	descriptor
/*      */     //   189	44	26	i	J
/*      */     //   252	44	29	i	I
/*      */     //   308	56	29	i	J
/*      */     //   382	101	29	i	I
/*      */     //   578	35	38	z	J
/*      */     //   568	56	34	d	J
/*      */     //   528	173	36	t	J
/*      */     //   512	208	32	i	J
/*      */     //   505	215	29	end	J
/*      */     //   508	212	31	c	I
/*      */     //   128	592	18	first	J
/*      */     //   135	585	20	length	J
/*      */     //   142	578	22	level	I
/*      */     //   158	562	23	signMask	I
/*      */     //   171	549	24	k	[[I
/*      */     //   182	538	25	shift	I
/*      */     //   236	484	26	lastUsed	I
/*      */     //   249	471	27	p	J
/*      */     //   0	724	0	perm	[[J
/*      */     //   0	724	1	a	[[I
/*      */     //   0	724	2	b	[[I
/*      */     //   0	724	3	from	J
/*      */     //   0	724	5	to	J
/*      */     //   0	724	7	stable	Z
/*      */     //   24	700	8	layers	I
/*      */     //   28	696	9	maxLevel	I
/*      */     //   33	691	10	stackSize	I
/*      */     //   36	688	11	stackPos	I
/*      */     //   43	681	12	offsetStack	[J
/*      */     //   50	674	13	lengthStack	[J
/*      */     //   57	667	14	levelStack	[I
/*      */     //   88	636	15	count	[J
/*      */     //   95	629	16	pos	[J
/*      */     //   113	611	17	support	[[J
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
/*      */   public static int[][] shuffle(int[][] a, long from, long to, Random random) {
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
/*      */   public static int[][] shuffle(int[][] a, Random random) {
/* 1553 */     return BigArrays.shuffle(a, random);
/*      */   }
/*      */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\i\\unimi\dsi\fastutil\ints\IntBigArrays.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */