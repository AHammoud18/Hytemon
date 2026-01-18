/*      */ package it.unimi.dsi.fastutil.bytes;
/*      */ 
/*      */ import it.unimi.dsi.fastutil.BigArrays;
/*      */ import it.unimi.dsi.fastutil.Hash;
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
/*      */ public final class ByteBigArrays
/*      */ {
/*   69 */   public static final byte[][] EMPTY_BIG_ARRAY = new byte[0][];
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*   77 */   public static final byte[][] DEFAULT_EMPTY_BIG_ARRAY = new byte[0][];
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
/*      */   public static byte get(byte[][] array, long index) {
/*   89 */     return array[BigArrays.segment(index)][BigArrays.displacement(index)];
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
/*      */   public static void set(byte[][] array, long index, byte value) {
/*  102 */     array[BigArrays.segment(index)][BigArrays.displacement(index)] = value;
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
/*      */   public static void swap(byte[][] array, long first, long second) {
/*  115 */     byte t = array[BigArrays.segment(first)][BigArrays.displacement(first)];
/*  116 */     array[BigArrays.segment(first)][BigArrays.displacement(first)] = array[BigArrays.segment(second)][BigArrays.displacement(second)];
/*  117 */     array[BigArrays.segment(second)][BigArrays.displacement(second)] = t;
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
/*      */   public static void add(byte[][] array, long index, byte incr) {
/*  130 */     array[BigArrays.segment(index)][BigArrays.displacement(index)] = (byte)(array[BigArrays.segment(index)][BigArrays.displacement(index)] + incr);
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
/*      */   public static void mul(byte[][] array, long index, byte factor) {
/*  143 */     array[BigArrays.segment(index)][BigArrays.displacement(index)] = (byte)(array[BigArrays.segment(index)][BigArrays.displacement(index)] * factor);
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
/*      */   public static void incr(byte[][] array, long index) {
/*  155 */     array[BigArrays.segment(index)][BigArrays.displacement(index)] = (byte)(array[BigArrays.segment(index)][BigArrays.displacement(index)] + 1);
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
/*      */   public static void decr(byte[][] array, long index) {
/*  167 */     array[BigArrays.segment(index)][BigArrays.displacement(index)] = (byte)(array[BigArrays.segment(index)][BigArrays.displacement(index)] - 1);
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
/*      */   public static long length(byte[][] array) {
/*  179 */     int length = array.length;
/*  180 */     return (length == 0) ? 0L : (BigArrays.start(length - 1) + (array[length - 1]).length);
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
/*      */   public static void copy(byte[][] srcArray, long srcPos, byte[][] destArray, long destPos, long length) {
/*  197 */     BigArrays.copy(srcArray, srcPos, destArray, destPos, length);
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
/*      */   public static void copyFromBig(byte[][] srcArray, long srcPos, byte[] destArray, int destPos, int length) {
/*  213 */     BigArrays.copyFromBig(srcArray, srcPos, destArray, destPos, length);
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
/*      */   public static void copyToBig(byte[] srcArray, int srcPos, byte[][] destArray, long destPos, long length) {
/*  229 */     BigArrays.copyToBig(srcArray, srcPos, destArray, destPos, length);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static byte[][] newBigArray(long length) {
/*  239 */     if (length == 0L) return EMPTY_BIG_ARRAY; 
/*  240 */     BigArrays.ensureLength(length);
/*  241 */     int baseLength = (int)(length + 134217727L >>> 27L);
/*  242 */     byte[][] base = new byte[baseLength][];
/*  243 */     int residual = (int)(length & 0x7FFFFFFL);
/*  244 */     if (residual != 0)
/*  245 */     { for (int i = 0; i < baseLength - 1; ) { base[i] = new byte[134217728]; i++; }
/*  246 */        base[baseLength - 1] = new byte[residual]; }
/*  247 */     else { for (int i = 0; i < baseLength; ) { base[i] = new byte[134217728]; i++; }  }
/*  248 */      return base;
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
/*      */   public static byte[][] wrap(byte[] array) {
/*  263 */     return BigArrays.wrap(array);
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
/*      */   public static byte[][] ensureCapacity(byte[][] array, long length) {
/*  286 */     return ensureCapacity(array, length, length(array));
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
/*      */   public static byte[][] forceCapacity(byte[][] array, long length, long preserve) {
/*  307 */     return BigArrays.forceCapacity(array, length, preserve);
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
/*      */   public static byte[][] ensureCapacity(byte[][] array, long length, long preserve) {
/*  329 */     return (length > length(array)) ? forceCapacity(array, length, preserve) : array;
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
/*      */   public static byte[][] grow(byte[][] array, long length) {
/*  353 */     long oldLength = length(array);
/*  354 */     return (length > oldLength) ? grow(array, length, oldLength) : array;
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
/*      */   public static byte[][] grow(byte[][] array, long length, long preserve) {
/*  381 */     long oldLength = length(array);
/*  382 */     return (length > oldLength) ? ensureCapacity(array, Math.max(oldLength + (oldLength >> 1L), length), preserve) : array;
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
/*      */   public static byte[][] trim(byte[][] array, long length) {
/*  401 */     BigArrays.ensureLength(length);
/*  402 */     long oldLength = length(array);
/*  403 */     if (length >= oldLength) return array; 
/*  404 */     int baseLength = (int)(length + 134217727L >>> 27L);
/*  405 */     byte[][] base = Arrays.<byte[]>copyOf(array, baseLength);
/*  406 */     int residual = (int)(length & 0x7FFFFFFL);
/*  407 */     if (residual != 0) base[baseLength - 1] = ByteArrays.trim(base[baseLength - 1], residual); 
/*  408 */     return base;
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
/*      */   public static byte[][] setLength(byte[][] array, long length) {
/*  429 */     return BigArrays.setLength(array, length);
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
/*      */   public static byte[][] copy(byte[][] array, long offset, long length) {
/*  444 */     return BigArrays.copy(array, offset, length);
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
/*      */   public static byte[][] copy(byte[][] array) {
/*  456 */     return BigArrays.copy(array);
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
/*      */   public static void fill(byte[][] array, byte value) {
/*  472 */     for (int i = array.length; i-- != 0; Arrays.fill(array[i], value));
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
/*      */   public static void fill(byte[][] array, long from, long to, byte value) {
/*  490 */     BigArrays.fill(array, from, to, value);
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
/*      */   public static boolean equals(byte[][] a1, byte[][] a2) {
/*  507 */     return BigArrays.equals(a1, a2);
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
/*      */   public static String toString(byte[][] a) {
/*  519 */     return BigArrays.toString(a);
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
/*      */   public static void ensureFromTo(byte[][] a, long from, long to) {
/*  539 */     BigArrays.ensureFromTo(length(a), from, to);
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
/*      */   public static void ensureOffsetLength(byte[][] a, long offset, long length) {
/*  558 */     BigArrays.ensureOffsetLength(length(a), offset, length);
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
/*      */   public static void ensureSameLength(byte[][] a, byte[][] b) {
/*  571 */     if (length(a) != length(b)) throw new IllegalArgumentException("Array size mismatch: " + length(a) + " != " + length(b)); 
/*      */   }
/*      */   
/*      */   private static final class BigArrayHashStrategy implements Hash.Strategy<byte[][]>, Serializable {
/*      */     private static final long serialVersionUID = -7046029254386353129L;
/*      */     
/*      */     private BigArrayHashStrategy() {}
/*      */     
/*      */     public int hashCode(byte[][] o) {
/*  580 */       return Arrays.deepHashCode((Object[])o);
/*      */     }
/*      */ 
/*      */     
/*      */     public boolean equals(byte[][] a, byte[][] b) {
/*  585 */       return ByteBigArrays.equals(a, b);
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
/*  598 */   public static final Hash.Strategy HASH_STRATEGY = new BigArrayHashStrategy(); private static final int QUICKSORT_NO_REC = 7; private static final int PARALLEL_QUICKSORT_NO_FORK = 8192; private static final int MEDIUM = 40;
/*      */   private static final int DIGIT_BITS = 8;
/*      */   private static final int DIGIT_MASK = 255;
/*      */   private static final int DIGITS_PER_ELEMENT = 1;
/*      */   private static final int RADIXSORT_NO_REC = 1024;
/*      */   
/*      */   private static ForkJoinPool getPool() {
/*  605 */     ForkJoinPool current = ForkJoinTask.getPool();
/*  606 */     return (current == null) ? ForkJoinPool.commonPool() : current;
/*      */   }
/*      */   
/*      */   private static void swap(byte[][] x, long a, long b, long n) {
/*  610 */     for (int i = 0; i < n; ) { BigArrays.swap(x, a, b); i++; a++; b++; }
/*      */   
/*      */   }
/*      */   private static long med3(byte[][] x, long a, long b, long c, ByteComparator comp) {
/*  614 */     int ab = comp.compare(BigArrays.get(x, a), BigArrays.get(x, b));
/*  615 */     int ac = comp.compare(BigArrays.get(x, a), BigArrays.get(x, c));
/*  616 */     int bc = comp.compare(BigArrays.get(x, b), BigArrays.get(x, c));
/*  617 */     return (ab < 0) ? ((bc < 0) ? b : ((ac < 0) ? c : a)) : ((bc > 0) ? b : ((ac > 0) ? c : a));
/*      */   }
/*      */   private static void selectionSort(byte[][] a, long from, long to, ByteComparator comp) {
/*      */     long i;
/*  621 */     for (i = from; i < to - 1L; i++) {
/*  622 */       long m = i; long j;
/*  623 */       for (j = i + 1L; j < to; ) { if (comp.compare(BigArrays.get(a, j), BigArrays.get(a, m)) < 0) m = j;  j++; }
/*  624 */        if (m != i) BigArrays.swap(a, i, m);
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
/*      */   public static void quickSort(byte[][] x, long from, long to, ByteComparator comp) {
/*  643 */     long len = to - from;
/*      */     
/*  645 */     if (len < 7L) {
/*  646 */       selectionSort(x, from, to, comp);
/*      */       
/*      */       return;
/*      */     } 
/*  650 */     long m = from + len / 2L;
/*  651 */     if (len > 7L) {
/*  652 */       long l = from;
/*  653 */       long n = to - 1L;
/*  654 */       if (len > 40L) {
/*  655 */         long s = len / 8L;
/*  656 */         l = med3(x, l, l + s, l + 2L * s, comp);
/*  657 */         m = med3(x, m - s, m, m + s, comp);
/*  658 */         n = med3(x, n - 2L * s, n - s, n, comp);
/*      */       } 
/*  660 */       m = med3(x, l, m, n, comp);
/*      */     } 
/*  662 */     byte v = BigArrays.get(x, m);
/*      */     
/*  664 */     long a = from, b = a, c = to - 1L, d = c;
/*      */     
/*      */     int comparison;
/*  667 */     while (b <= c && (comparison = comp.compare(BigArrays.get(x, b), v)) <= 0) {
/*  668 */       if (comparison == 0) BigArrays.swap(x, a++, b); 
/*  669 */       b++;
/*      */     } 
/*  671 */     while (c >= b && (comparison = comp.compare(BigArrays.get(x, c), v)) >= 0) {
/*  672 */       if (comparison == 0) BigArrays.swap(x, c, d--); 
/*  673 */       c--;
/*      */     } 
/*  675 */     if (b > c) {
/*      */ 
/*      */ 
/*      */       
/*  679 */       long n = to;
/*  680 */       long s = Math.min(a - from, b - a);
/*  681 */       swap(x, from, b - s, s);
/*  682 */       s = Math.min(d - c, n - d - 1L);
/*  683 */       swap(x, b, n - s, s);
/*      */       
/*  685 */       if ((s = b - a) > 1L) quickSort(x, from, from + s, comp); 
/*  686 */       if ((s = d - c) > 1L) quickSort(x, n - s, n, comp); 
/*      */       return;
/*      */     } 
/*      */     BigArrays.swap(x, b++, c--); } private static long med3(byte[][] x, long a, long b, long c) {
/*  690 */     int ab = Byte.compare(BigArrays.get(x, a), BigArrays.get(x, b));
/*  691 */     int ac = Byte.compare(BigArrays.get(x, a), BigArrays.get(x, c));
/*  692 */     int bc = Byte.compare(BigArrays.get(x, b), BigArrays.get(x, c));
/*  693 */     return (ab < 0) ? ((bc < 0) ? b : ((ac < 0) ? c : a)) : ((bc > 0) ? b : ((ac > 0) ? c : a));
/*      */   }
/*      */   private static void selectionSort(byte[][] a, long from, long to) {
/*      */     long i;
/*  697 */     for (i = from; i < to - 1L; i++) {
/*  698 */       long m = i; long j;
/*  699 */       for (j = i + 1L; j < to; ) { if (BigArrays.get(a, j) < BigArrays.get(a, m)) m = j;  j++; }
/*  700 */        if (m != i) BigArrays.swap(a, i, m);
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
/*      */   public static void quickSort(byte[][] x, ByteComparator comp) {
/*  718 */     quickSort(x, 0L, BigArrays.length(x), comp);
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
/*      */   public static void quickSort(byte[][] x, long from, long to) {
/*  735 */     long len = to - from;
/*      */     
/*  737 */     if (len < 7L) {
/*  738 */       selectionSort(x, from, to);
/*      */       
/*      */       return;
/*      */     } 
/*  742 */     long m = from + len / 2L;
/*  743 */     if (len > 7L) {
/*  744 */       long l = from;
/*  745 */       long n = to - 1L;
/*  746 */       if (len > 40L) {
/*  747 */         long s = len / 8L;
/*  748 */         l = med3(x, l, l + s, l + 2L * s);
/*  749 */         m = med3(x, m - s, m, m + s);
/*  750 */         n = med3(x, n - 2L * s, n - s, n);
/*      */       } 
/*  752 */       m = med3(x, l, m, n);
/*      */     } 
/*  754 */     byte v = BigArrays.get(x, m);
/*      */     
/*  756 */     long a = from, b = a, c = to - 1L, d = c;
/*      */     
/*      */     int comparison;
/*  759 */     while (b <= c && (comparison = Byte.compare(BigArrays.get(x, b), v)) <= 0) {
/*  760 */       if (comparison == 0) BigArrays.swap(x, a++, b); 
/*  761 */       b++;
/*      */     } 
/*  763 */     while (c >= b && (comparison = Byte.compare(BigArrays.get(x, c), v)) >= 0) {
/*  764 */       if (comparison == 0) BigArrays.swap(x, c, d--); 
/*  765 */       c--;
/*      */     } 
/*  767 */     if (b > c) {
/*      */ 
/*      */ 
/*      */       
/*  771 */       long n = to;
/*  772 */       long s = Math.min(a - from, b - a);
/*  773 */       swap(x, from, b - s, s);
/*  774 */       s = Math.min(d - c, n - d - 1L);
/*  775 */       swap(x, b, n - s, s);
/*      */       
/*  777 */       if ((s = b - a) > 1L) quickSort(x, from, from + s); 
/*  778 */       if ((s = d - c) > 1L) quickSort(x, n - s, n);
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
/*      */   public static void quickSort(byte[][] x) {
/*  792 */     quickSort(x, 0L, BigArrays.length(x));
/*      */   }
/*      */   
/*      */   protected static class ForkJoinQuickSort extends RecursiveAction {
/*      */     private static final long serialVersionUID = 1L;
/*      */     private final long from;
/*      */     private final long to;
/*      */     private final byte[][] x;
/*      */     
/*      */     public ForkJoinQuickSort(byte[][] x, long from, long to) {
/*  802 */       this.from = from;
/*  803 */       this.to = to;
/*  804 */       this.x = x;
/*      */     }
/*      */ 
/*      */ 
/*      */     
/*      */     protected void compute() {
/*  810 */       byte[][] x = this.x;
/*  811 */       long len = this.to - this.from;
/*  812 */       if (len < 8192L) {
/*  813 */         ByteBigArrays.quickSort(x, this.from, this.to);
/*      */         
/*      */         return;
/*      */       } 
/*  817 */       long m = this.from + len / 2L;
/*  818 */       long l = this.from;
/*  819 */       long n = this.to - 1L;
/*  820 */       long s = len / 8L;
/*  821 */       l = ByteBigArrays.med3(x, l, l + s, l + 2L * s);
/*  822 */       m = ByteBigArrays.med3(x, m - s, m, m + s);
/*  823 */       n = ByteBigArrays.med3(x, n - 2L * s, n - s, n);
/*  824 */       m = ByteBigArrays.med3(x, l, m, n);
/*  825 */       byte v = BigArrays.get(x, m);
/*      */       
/*  827 */       long a = this.from, b = a, c = this.to - 1L, d = c;
/*      */       
/*      */       int comparison;
/*  830 */       while (b <= c && (comparison = Byte.compare(BigArrays.get(x, b), v)) <= 0) {
/*  831 */         if (comparison == 0) BigArrays.swap(x, a++, b); 
/*  832 */         b++;
/*      */       } 
/*  834 */       while (c >= b && (comparison = Byte.compare(BigArrays.get(x, c), v)) >= 0) {
/*  835 */         if (comparison == 0) BigArrays.swap(x, c, d--); 
/*  836 */         c--;
/*      */       } 
/*  838 */       if (b > c) {
/*      */ 
/*      */ 
/*      */ 
/*      */         
/*  843 */         s = Math.min(a - this.from, b - a);
/*  844 */         ByteBigArrays.swap(x, this.from, b - s, s);
/*  845 */         s = Math.min(d - c, this.to - d - 1L);
/*  846 */         ByteBigArrays.swap(x, b, this.to - s, s);
/*      */         
/*  848 */         s = b - a;
/*  849 */         long t = d - c;
/*  850 */         if (s > 1L && t > 1L) { invokeAll(new ForkJoinQuickSort(x, this.from, this.from + s), new ForkJoinQuickSort(x, this.to - t, this.to)); }
/*  851 */         else if (s > 1L) { invokeAll((ForkJoinTask<?>[])new ForkJoinTask[] { new ForkJoinQuickSort(x, this.from, this.from + s) }); }
/*  852 */         else { invokeAll((ForkJoinTask<?>[])new ForkJoinTask[] { new ForkJoinQuickSort(x, this.to - t, this.to) }); }
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
/*      */   public static void parallelQuickSort(byte[][] x, long from, long to) {
/*  870 */     ForkJoinPool pool = getPool();
/*  871 */     if (to - from < 8192L || pool.getParallelism() == 1) { quickSort(x, from, to); }
/*      */     else
/*  873 */     { pool.invoke(new ForkJoinQuickSort(x, from, to)); }
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
/*      */   public static void parallelQuickSort(byte[][] x) {
/*  888 */     parallelQuickSort(x, 0L, BigArrays.length(x));
/*      */   }
/*      */   
/*      */   protected static class ForkJoinQuickSortComp extends RecursiveAction {
/*      */     private static final long serialVersionUID = 1L;
/*      */     private final long from;
/*      */     private final long to;
/*      */     private final byte[][] x;
/*      */     private final ByteComparator comp;
/*      */     
/*      */     public ForkJoinQuickSortComp(byte[][] x, long from, long to, ByteComparator comp) {
/*  899 */       this.from = from;
/*  900 */       this.to = to;
/*  901 */       this.x = x;
/*  902 */       this.comp = comp;
/*      */     }
/*      */ 
/*      */     
/*      */     protected void compute() {
/*  907 */       byte[][] x = this.x;
/*  908 */       long len = this.to - this.from;
/*  909 */       if (len < 8192L) {
/*  910 */         ByteBigArrays.quickSort(x, this.from, this.to, this.comp);
/*      */         
/*      */         return;
/*      */       } 
/*  914 */       long m = this.from + len / 2L;
/*  915 */       long l = this.from;
/*  916 */       long n = this.to - 1L;
/*  917 */       long s = len / 8L;
/*  918 */       l = ByteBigArrays.med3(x, l, l + s, l + 2L * s, this.comp);
/*  919 */       m = ByteBigArrays.med3(x, m - s, m, m + s, this.comp);
/*  920 */       n = ByteBigArrays.med3(x, n - 2L * s, n - s, n, this.comp);
/*  921 */       m = ByteBigArrays.med3(x, l, m, n, this.comp);
/*  922 */       byte v = BigArrays.get(x, m);
/*      */       
/*  924 */       long a = this.from, b = a, c = this.to - 1L, d = c;
/*      */       
/*      */       int comparison;
/*  927 */       while (b <= c && (comparison = this.comp.compare(BigArrays.get(x, b), v)) <= 0) {
/*  928 */         if (comparison == 0) BigArrays.swap(x, a++, b); 
/*  929 */         b++;
/*      */       } 
/*  931 */       while (c >= b && (comparison = this.comp.compare(BigArrays.get(x, c), v)) >= 0) {
/*  932 */         if (comparison == 0) BigArrays.swap(x, c, d--); 
/*  933 */         c--;
/*      */       } 
/*  935 */       if (b > c) {
/*      */ 
/*      */ 
/*      */ 
/*      */         
/*  940 */         s = Math.min(a - this.from, b - a);
/*  941 */         ByteBigArrays.swap(x, this.from, b - s, s);
/*  942 */         s = Math.min(d - c, this.to - d - 1L);
/*  943 */         ByteBigArrays.swap(x, b, this.to - s, s);
/*      */         
/*  945 */         s = b - a;
/*  946 */         long t = d - c;
/*  947 */         if (s > 1L && t > 1L) { invokeAll(new ForkJoinQuickSortComp(x, this.from, this.from + s, this.comp), new ForkJoinQuickSortComp(x, this.to - t, this.to, this.comp)); }
/*  948 */         else if (s > 1L) { invokeAll((ForkJoinTask<?>[])new ForkJoinTask[] { new ForkJoinQuickSortComp(x, this.from, this.from + s, this.comp) }); }
/*  949 */         else { invokeAll((ForkJoinTask<?>[])new ForkJoinTask[] { new ForkJoinQuickSortComp(x, this.to - t, this.to, this.comp) }); }
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
/*      */   public static void parallelQuickSort(byte[][] x, long from, long to, ByteComparator comp) {
/*  968 */     ForkJoinPool pool = getPool();
/*  969 */     if (to - from < 8192L || pool.getParallelism() == 1) { quickSort(x, from, to, comp); }
/*      */     else
/*  971 */     { pool.invoke(new ForkJoinQuickSortComp(x, from, to, comp)); }
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
/*      */   public static void parallelQuickSort(byte[][] x, ByteComparator comp) {
/*  988 */     parallelQuickSort(x, 0L, BigArrays.length(x), comp);
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
/*      */   public static long binarySearch(byte[][] a, long from, long to, byte key) {
/* 1012 */     to--;
/* 1013 */     while (from <= to) {
/* 1014 */       long mid = from + to >>> 1L;
/* 1015 */       byte midVal = BigArrays.get(a, mid);
/* 1016 */       if (midVal < key) { from = mid + 1L; continue; }
/* 1017 */        if (midVal > key) { to = mid - 1L; continue; }
/* 1018 */        return mid;
/*      */     } 
/* 1020 */     return -(from + 1L);
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
/*      */   public static long binarySearch(byte[][] a, byte key) {
/* 1040 */     return binarySearch(a, 0L, BigArrays.length(a), key);
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
/*      */   public static long binarySearch(byte[][] a, long from, long to, byte key, ByteComparator c) {
/* 1064 */     to--;
/* 1065 */     while (from <= to) {
/* 1066 */       long mid = from + to >>> 1L;
/* 1067 */       byte midVal = BigArrays.get(a, mid);
/* 1068 */       int cmp = c.compare(midVal, key);
/* 1069 */       if (cmp < 0) { from = mid + 1L; continue; }
/* 1070 */        if (cmp > 0) { to = mid - 1L; continue; }
/* 1071 */        return mid;
/*      */     } 
/* 1073 */     return -(from + 1L);
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
/*      */   public static long binarySearch(byte[][] a, byte key, ByteComparator c) {
/* 1094 */     return binarySearch(a, 0L, BigArrays.length(a), key, c);
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
/*      */   public static void radixSort(byte[][] a) {
/* 1126 */     radixSort(a, 0L, BigArrays.length(a));
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
/*      */   public static void radixSort(byte[][] a, long from, long to) {
/* 1149 */     int maxLevel = 0;
/* 1150 */     int stackSize = 1;
/* 1151 */     long[] offsetStack = new long[1];
/* 1152 */     int offsetPos = 0;
/* 1153 */     long[] lengthStack = new long[1];
/* 1154 */     int lengthPos = 0;
/* 1155 */     int[] levelStack = new int[1];
/* 1156 */     int levelPos = 0;
/* 1157 */     offsetStack[offsetPos++] = from;
/* 1158 */     lengthStack[lengthPos++] = to - from;
/* 1159 */     levelStack[levelPos++] = 0;
/* 1160 */     long[] count = new long[256];
/* 1161 */     long[] pos = new long[256];
/* 1162 */     byte[][] digit = newBigArray(to - from);
/* 1163 */     while (offsetPos > 0) {
/* 1164 */       long first = offsetStack[--offsetPos];
/* 1165 */       long length = lengthStack[--lengthPos];
/* 1166 */       int level = levelStack[--levelPos];
/* 1167 */       int signMask = (level % 1 == 0) ? 128 : 0;
/* 1168 */       if (length < 40L) {
/* 1169 */         selectionSort(a, first, first + length);
/*      */         continue;
/*      */       } 
/* 1172 */       int shift = (0 - level % 1) * 8;
/*      */ 
/*      */       
/*      */       long i;
/*      */       
/* 1177 */       for (i = length; i-- != 0L; BigArrays.set(digit, i, (byte)(BigArrays.get(a, first + i) >>> shift & 0xFF ^ signMask)));
/* 1178 */       for (i = length; i-- != 0L; count[BigArrays.get(digit, i) & 0xFF] = count[BigArrays.get(digit, i) & 0xFF] + 1L);
/*      */       
/* 1180 */       int lastUsed = -1;
/* 1181 */       long p = 0L;
/* 1182 */       for (int j = 0; j < 256; j++) {
/* 1183 */         if (count[j] != 0L) {
/* 1184 */           lastUsed = j;
/* 1185 */           if (level < 0 && count[j] > 1L) {
/*      */ 
/*      */             
/* 1188 */             offsetStack[offsetPos++] = p + first;
/* 1189 */             lengthStack[lengthPos++] = count[j];
/* 1190 */             levelStack[levelPos++] = level + 1;
/*      */           } 
/*      */         } 
/* 1193 */         pos[j] = p += count[j];
/*      */       } 
/*      */       
/* 1196 */       long end = length - count[lastUsed];
/* 1197 */       count[lastUsed] = 0L;
/*      */       
/* 1199 */       int c = -1;
/* 1200 */       for (long l1 = 0L; l1 < end; l1 += count[c], count[c] = 0L) {
/* 1201 */         byte t = BigArrays.get(a, l1 + first);
/* 1202 */         c = BigArrays.get(digit, l1) & 0xFF; long d;
/* 1203 */         for (pos[c] = pos[c] - 1L; (d = pos[c] - 1L) > l1; ) {
/* 1204 */           byte z = t;
/* 1205 */           int zz = c;
/* 1206 */           t = BigArrays.get(a, d + first);
/* 1207 */           c = BigArrays.get(digit, d) & 0xFF;
/* 1208 */           BigArrays.set(a, d + first, z);
/* 1209 */           BigArrays.set(digit, d, (byte)zz);
/*      */         } 
/* 1211 */         BigArrays.set(a, l1 + first, t);
/*      */       } 
/*      */     } 
/*      */   }
/*      */   private static void selectionSort(byte[][] a, byte[][] b, long from, long to) {
/*      */     long i;
/* 1217 */     for (i = from; i < to - 1L; i++) {
/* 1218 */       long m = i; long j;
/* 1219 */       for (j = i + 1L; j < to; ) { if (BigArrays.get(a, j) < BigArrays.get(a, m) || (BigArrays.get(a, j) == BigArrays.get(a, m) && BigArrays.get(b, j) < BigArrays.get(b, m))) m = j;  j++; }
/* 1220 */        if (m != i) {
/* 1221 */         byte t = BigArrays.get(a, i);
/* 1222 */         BigArrays.set(a, i, BigArrays.get(a, m));
/* 1223 */         BigArrays.set(a, m, t);
/* 1224 */         t = BigArrays.get(b, i);
/* 1225 */         BigArrays.set(b, i, BigArrays.get(b, m));
/* 1226 */         BigArrays.set(b, m, t);
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
/*      */   public static void radixSort(byte[][] a, byte[][] b) {
/* 1255 */     radixSort(a, b, 0L, BigArrays.length(a));
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
/*      */   public static void radixSort(byte[][] a, byte[][] b, long from, long to) {
/* 1285 */     int layers = 2;
/* 1286 */     if (BigArrays.length(a) != BigArrays.length(b)) throw new IllegalArgumentException("Array size mismatch."); 
/* 1287 */     int maxLevel = 1;
/* 1288 */     int stackSize = 256;
/* 1289 */     long[] offsetStack = new long[256];
/* 1290 */     int offsetPos = 0;
/* 1291 */     long[] lengthStack = new long[256];
/* 1292 */     int lengthPos = 0;
/* 1293 */     int[] levelStack = new int[256];
/* 1294 */     int levelPos = 0;
/* 1295 */     offsetStack[offsetPos++] = from;
/* 1296 */     lengthStack[lengthPos++] = to - from;
/* 1297 */     levelStack[levelPos++] = 0;
/* 1298 */     long[] count = new long[256];
/* 1299 */     long[] pos = new long[256];
/* 1300 */     byte[][] digit = newBigArray(to - from);
/* 1301 */     while (offsetPos > 0) {
/* 1302 */       long first = offsetStack[--offsetPos];
/* 1303 */       long length = lengthStack[--lengthPos];
/* 1304 */       int level = levelStack[--levelPos];
/* 1305 */       int signMask = (level % 1 == 0) ? 128 : 0;
/* 1306 */       if (length < 40L) {
/* 1307 */         selectionSort(a, b, first, first + length);
/*      */         continue;
/*      */       } 
/* 1310 */       byte[][] k = (level < 1) ? a : b;
/* 1311 */       int shift = (0 - level % 1) * 8;
/*      */ 
/*      */       
/*      */       long i;
/*      */       
/* 1316 */       for (i = length; i-- != 0L; BigArrays.set(digit, i, (byte)(BigArrays.get(k, first + i) >>> shift & 0xFF ^ signMask)));
/* 1317 */       for (i = length; i-- != 0L; count[BigArrays.get(digit, i) & 0xFF] = count[BigArrays.get(digit, i) & 0xFF] + 1L);
/*      */       
/* 1319 */       int lastUsed = -1;
/* 1320 */       long p = 0L;
/* 1321 */       for (int j = 0; j < 256; j++) {
/* 1322 */         if (count[j] != 0L) {
/* 1323 */           lastUsed = j;
/* 1324 */           if (level < 1 && count[j] > 1L) {
/* 1325 */             offsetStack[offsetPos++] = p + first;
/* 1326 */             lengthStack[lengthPos++] = count[j];
/* 1327 */             levelStack[levelPos++] = level + 1;
/*      */           } 
/*      */         } 
/* 1330 */         pos[j] = p += count[j];
/*      */       } 
/*      */       
/* 1333 */       long end = length - count[lastUsed];
/* 1334 */       count[lastUsed] = 0L;
/*      */       
/* 1336 */       int c = -1;
/* 1337 */       for (long l1 = 0L; l1 < end; l1 += count[c], count[c] = 0L) {
/* 1338 */         byte t = BigArrays.get(a, l1 + first);
/* 1339 */         byte u = BigArrays.get(b, l1 + first);
/* 1340 */         c = BigArrays.get(digit, l1) & 0xFF; long d;
/* 1341 */         for (pos[c] = pos[c] - 1L; (d = pos[c] - 1L) > l1; ) {
/* 1342 */           byte z = t;
/* 1343 */           int zz = c;
/* 1344 */           t = BigArrays.get(a, d + first);
/* 1345 */           BigArrays.set(a, d + first, z);
/* 1346 */           z = u;
/* 1347 */           u = BigArrays.get(b, d + first);
/* 1348 */           BigArrays.set(b, d + first, z);
/* 1349 */           c = BigArrays.get(digit, d) & 0xFF;
/* 1350 */           BigArrays.set(digit, d, (byte)zz);
/*      */         } 
/* 1352 */         BigArrays.set(a, l1 + first, t);
/* 1353 */         BigArrays.set(b, l1 + first, u);
/*      */       } 
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   private static void insertionSortIndirect(long[][] perm, byte[][] a, byte[][] b, long from, long to) {
/* 1361 */     long i = from; while (true) { long t, j, u; if (++i < to)
/* 1362 */       { t = BigArrays.get(perm, i);
/* 1363 */         j = i;
/* 1364 */         u = BigArrays.get(perm, j - 1L); } else { break; }  if (BigArrays.get(a, t) < BigArrays.get(a, u) || (BigArrays.get(a, t) == BigArrays.get(a, u) && BigArrays.get(b, t) < BigArrays.get(b, u)));
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/* 1371 */       BigArrays.set(perm, j, t); }
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
/*      */   public static void radixSortIndirect(long[][] perm, byte[][] a, byte[][] b, boolean stable) {
/* 1398 */     ensureSameLength(a, b);
/* 1399 */     radixSortIndirect(perm, a, b, 0L, BigArrays.length(a), stable);
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
/*      */   public static void radixSortIndirect(long[][] perm, byte[][] a, byte[][] b, long from, long to, boolean stable) {
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
/*      */     //   17: invokestatic insertionSortIndirect : ([[J[[B[[BJJ)V
/*      */     //   20: return
/*      */     //   21: iconst_2
/*      */     //   22: istore #8
/*      */     //   24: iconst_1
/*      */     //   25: istore #9
/*      */     //   27: sipush #256
/*      */     //   30: istore #10
/*      */     //   32: iconst_0
/*      */     //   33: istore #11
/*      */     //   35: sipush #256
/*      */     //   38: newarray long
/*      */     //   40: astore #12
/*      */     //   42: sipush #256
/*      */     //   45: newarray long
/*      */     //   47: astore #13
/*      */     //   49: sipush #256
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
/*      */     //   143: iconst_1
/*      */     //   144: irem
/*      */     //   145: ifne -> 154
/*      */     //   148: sipush #128
/*      */     //   151: goto -> 155
/*      */     //   154: iconst_0
/*      */     //   155: istore #23
/*      */     //   157: iload #22
/*      */     //   159: iconst_1
/*      */     //   160: if_icmpge -> 167
/*      */     //   163: aload_1
/*      */     //   164: goto -> 168
/*      */     //   167: aload_2
/*      */     //   168: astore #24
/*      */     //   170: iconst_0
/*      */     //   171: iload #22
/*      */     //   173: iconst_1
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
/*      */     //   211: invokestatic get : ([[BJ)B
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
/*      */     //   332: invokestatic get : ([[BJ)B
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
/*      */     //   391: iconst_1
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
/*      */     //   430: invokestatic insertionSortIndirect : ([[J[[B[[BJJ)V
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
/*      */     //   530: invokestatic get : ([[BJ)B
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
/*      */     //   596: invokestatic get : ([[BJ)B
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
/*      */     //   624: iconst_1
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
/*      */     //   663: invokestatic insertionSortIndirect : ([[J[[B[[BJJ)V
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
/*      */     //   #1427	-> 0
/*      */     //   #1428	-> 11
/*      */     //   #1429	-> 20
/*      */     //   #1431	-> 21
/*      */     //   #1432	-> 24
/*      */     //   #1433	-> 27
/*      */     //   #1434	-> 32
/*      */     //   #1435	-> 35
/*      */     //   #1436	-> 42
/*      */     //   #1437	-> 49
/*      */     //   #1438	-> 56
/*      */     //   #1439	-> 62
/*      */     //   #1440	-> 71
/*      */     //   #1441	-> 80
/*      */     //   #1442	-> 87
/*      */     //   #1443	-> 94
/*      */     //   #1444	-> 112
/*      */     //   #1445	-> 117
/*      */     //   #1446	-> 127
/*      */     //   #1447	-> 134
/*      */     //   #1448	-> 141
/*      */     //   #1449	-> 157
/*      */     //   #1450	-> 170
/*      */     //   #1455	-> 181
/*      */     //   #1457	-> 232
/*      */     //   #1458	-> 235
/*      */     //   #1459	-> 248
/*      */     //   #1460	-> 259
/*      */     //   #1461	-> 273
/*      */     //   #1459	-> 289
/*      */     //   #1463	-> 295
/*      */     //   #1464	-> 300
/*      */     //   #1465	-> 363
/*      */     //   #1466	-> 374
/*      */     //   #1467	-> 378
/*      */     //   #1468	-> 389
/*      */     //   #1469	-> 405
/*      */     //   #1471	-> 436
/*      */     //   #1472	-> 443
/*      */     //   #1473	-> 453
/*      */     //   #1476	-> 465
/*      */     //   #1467	-> 475
/*      */     //   #1478	-> 481
/*      */     //   #1480	-> 490
/*      */     //   #1482	-> 503
/*      */     //   #1483	-> 506
/*      */     //   #1484	-> 518
/*      */     //   #1485	-> 526
/*      */     //   #1486	-> 545
/*      */     //   #1487	-> 553
/*      */     //   #1488	-> 572
/*      */     //   #1489	-> 576
/*      */     //   #1490	-> 584
/*      */     //   #1491	-> 592
/*      */     //   #1492	-> 611
/*      */     //   #1493	-> 614
/*      */     //   #1495	-> 622
/*      */     //   #1496	-> 638
/*      */     //   #1498	-> 669
/*      */     //   #1499	-> 676
/*      */     //   #1500	-> 686
/*      */     //   #1483	-> 698
/*      */     //   #1505	-> 717
/*      */     //   #1506	-> 720
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
/*      */     //   170	547	24	k	[[B
/*      */     //   181	536	25	shift	I
/*      */     //   235	482	26	lastUsed	I
/*      */     //   248	469	27	p	J
/*      */     //   0	721	0	perm	[[J
/*      */     //   0	721	1	a	[[B
/*      */     //   0	721	2	b	[[B
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
/*      */   public static byte[][] shuffle(byte[][] a, long from, long to, Random random) {
/* 1518 */     return BigArrays.shuffle(a, from, to, random);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static byte[][] shuffle(byte[][] a, Random random) {
/* 1529 */     return BigArrays.shuffle(a, random);
/*      */   }
/*      */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\i\\unimi\dsi\fastutil\bytes\ByteBigArrays.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */