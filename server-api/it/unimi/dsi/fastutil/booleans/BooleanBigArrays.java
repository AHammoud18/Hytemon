/*     */ package it.unimi.dsi.fastutil.booleans;
/*     */ 
/*     */ import it.unimi.dsi.fastutil.BigArrays;
/*     */ import it.unimi.dsi.fastutil.Hash;
/*     */ import java.io.Serializable;
/*     */ import java.util.Arrays;
/*     */ import java.util.Random;
/*     */ import java.util.concurrent.ForkJoinPool;
/*     */ import java.util.concurrent.ForkJoinTask;
/*     */ import java.util.concurrent.RecursiveAction;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public final class BooleanBigArrays
/*     */ {
/*  69 */   public static final boolean[][] EMPTY_BIG_ARRAY = new boolean[0][];
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  77 */   public static final boolean[][] DEFAULT_EMPTY_BIG_ARRAY = new boolean[0][];
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Deprecated
/*     */   public static boolean get(boolean[][] array, long index) {
/*  89 */     return array[BigArrays.segment(index)][BigArrays.displacement(index)];
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
/*     */   @Deprecated
/*     */   public static void set(boolean[][] array, long index, boolean value) {
/* 102 */     array[BigArrays.segment(index)][BigArrays.displacement(index)] = value;
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
/*     */   @Deprecated
/*     */   public static void swap(boolean[][] array, long first, long second) {
/* 115 */     boolean t = array[BigArrays.segment(first)][BigArrays.displacement(first)];
/* 116 */     array[BigArrays.segment(first)][BigArrays.displacement(first)] = array[BigArrays.segment(second)][BigArrays.displacement(second)];
/* 117 */     array[BigArrays.segment(second)][BigArrays.displacement(second)] = t;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Deprecated
/*     */   public static long length(boolean[][] array) {
/* 129 */     int length = array.length;
/* 130 */     return (length == 0) ? 0L : (BigArrays.start(length - 1) + (array[length - 1]).length);
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
/*     */   @Deprecated
/*     */   public static void copy(boolean[][] srcArray, long srcPos, boolean[][] destArray, long destPos, long length) {
/* 147 */     BigArrays.copy(srcArray, srcPos, destArray, destPos, length);
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
/*     */   @Deprecated
/*     */   public static void copyFromBig(boolean[][] srcArray, long srcPos, boolean[] destArray, int destPos, int length) {
/* 163 */     BigArrays.copyFromBig(srcArray, srcPos, destArray, destPos, length);
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
/*     */   @Deprecated
/*     */   public static void copyToBig(boolean[] srcArray, int srcPos, boolean[][] destArray, long destPos, long length) {
/* 179 */     BigArrays.copyToBig(srcArray, srcPos, destArray, destPos, length);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static boolean[][] newBigArray(long length) {
/* 189 */     if (length == 0L) return EMPTY_BIG_ARRAY; 
/* 190 */     BigArrays.ensureLength(length);
/* 191 */     int baseLength = (int)(length + 134217727L >>> 27L);
/* 192 */     boolean[][] base = new boolean[baseLength][];
/* 193 */     int residual = (int)(length & 0x7FFFFFFL);
/* 194 */     if (residual != 0)
/* 195 */     { for (int i = 0; i < baseLength - 1; ) { base[i] = new boolean[134217728]; i++; }
/* 196 */        base[baseLength - 1] = new boolean[residual]; }
/* 197 */     else { for (int i = 0; i < baseLength; ) { base[i] = new boolean[134217728]; i++; }  }
/* 198 */      return base;
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
/*     */   @Deprecated
/*     */   public static boolean[][] wrap(boolean[] array) {
/* 213 */     return BigArrays.wrap(array);
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
/*     */ 
/*     */   
/*     */   @Deprecated
/*     */   public static boolean[][] ensureCapacity(boolean[][] array, long length) {
/* 236 */     return ensureCapacity(array, length, length(array));
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
/*     */   @Deprecated
/*     */   public static boolean[][] forceCapacity(boolean[][] array, long length, long preserve) {
/* 257 */     return BigArrays.forceCapacity(array, length, preserve);
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
/*     */   
/*     */   @Deprecated
/*     */   public static boolean[][] ensureCapacity(boolean[][] array, long length, long preserve) {
/* 279 */     return (length > length(array)) ? forceCapacity(array, length, preserve) : array;
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
/*     */ 
/*     */ 
/*     */   
/*     */   @Deprecated
/*     */   public static boolean[][] grow(boolean[][] array, long length) {
/* 303 */     long oldLength = length(array);
/* 304 */     return (length > oldLength) ? grow(array, length, oldLength) : array;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Deprecated
/*     */   public static boolean[][] grow(boolean[][] array, long length, long preserve) {
/* 331 */     long oldLength = length(array);
/* 332 */     return (length > oldLength) ? ensureCapacity(array, Math.max(oldLength + (oldLength >> 1L), length), preserve) : array;
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
/*     */   @Deprecated
/*     */   public static boolean[][] trim(boolean[][] array, long length) {
/* 351 */     BigArrays.ensureLength(length);
/* 352 */     long oldLength = length(array);
/* 353 */     if (length >= oldLength) return array; 
/* 354 */     int baseLength = (int)(length + 134217727L >>> 27L);
/* 355 */     boolean[][] base = Arrays.<boolean[]>copyOf(array, baseLength);
/* 356 */     int residual = (int)(length & 0x7FFFFFFL);
/* 357 */     if (residual != 0) base[baseLength - 1] = BooleanArrays.trim(base[baseLength - 1], residual); 
/* 358 */     return base;
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
/*     */   @Deprecated
/*     */   public static boolean[][] setLength(boolean[][] array, long length) {
/* 379 */     return BigArrays.setLength(array, length);
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
/*     */   @Deprecated
/*     */   public static boolean[][] copy(boolean[][] array, long offset, long length) {
/* 394 */     return BigArrays.copy(array, offset, length);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Deprecated
/*     */   public static boolean[][] copy(boolean[][] array) {
/* 406 */     return BigArrays.copy(array);
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
/*     */   @Deprecated
/*     */   public static void fill(boolean[][] array, boolean value) {
/* 422 */     for (int i = array.length; i-- != 0; Arrays.fill(array[i], value));
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
/*     */   @Deprecated
/*     */   public static void fill(boolean[][] array, long from, long to, boolean value) {
/* 440 */     BigArrays.fill(array, from, to, value);
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
/*     */   @Deprecated
/*     */   public static boolean equals(boolean[][] a1, boolean[][] a2) {
/* 457 */     return BigArrays.equals(a1, a2);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Deprecated
/*     */   public static String toString(boolean[][] a) {
/* 469 */     return BigArrays.toString(a);
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
/*     */   @Deprecated
/*     */   public static void ensureFromTo(boolean[][] a, long from, long to) {
/* 489 */     BigArrays.ensureFromTo(length(a), from, to);
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
/*     */   @Deprecated
/*     */   public static void ensureOffsetLength(boolean[][] a, long offset, long length) {
/* 508 */     BigArrays.ensureOffsetLength(length(a), offset, length);
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
/*     */   @Deprecated
/*     */   public static void ensureSameLength(boolean[][] a, boolean[][] b) {
/* 521 */     if (length(a) != length(b)) throw new IllegalArgumentException("Array size mismatch: " + length(a) + " != " + length(b)); 
/*     */   }
/*     */   
/*     */   private static final class BigArrayHashStrategy implements Hash.Strategy<boolean[][]>, Serializable {
/*     */     private static final long serialVersionUID = -7046029254386353129L;
/*     */     
/*     */     private BigArrayHashStrategy() {}
/*     */     
/*     */     public int hashCode(boolean[][] o) {
/* 530 */       return Arrays.deepHashCode((Object[])o);
/*     */     }
/*     */ 
/*     */     
/*     */     public boolean equals(boolean[][] a, boolean[][] b) {
/* 535 */       return BooleanBigArrays.equals(a, b);
/*     */     }
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
/* 548 */   public static final Hash.Strategy HASH_STRATEGY = new BigArrayHashStrategy();
/*     */   
/*     */   private static final int QUICKSORT_NO_REC = 7;
/*     */   private static final int PARALLEL_QUICKSORT_NO_FORK = 8192;
/*     */   private static final int MEDIUM = 40;
/*     */   
/*     */   private static ForkJoinPool getPool() {
/* 555 */     ForkJoinPool current = ForkJoinTask.getPool();
/* 556 */     return (current == null) ? ForkJoinPool.commonPool() : current;
/*     */   }
/*     */   
/*     */   private static void swap(boolean[][] x, long a, long b, long n) {
/* 560 */     for (int i = 0; i < n; ) { BigArrays.swap(x, a, b); i++; a++; b++; }
/*     */   
/*     */   }
/*     */   private static long med3(boolean[][] x, long a, long b, long c, BooleanComparator comp) {
/* 564 */     int ab = comp.compare(BigArrays.get(x, a), BigArrays.get(x, b));
/* 565 */     int ac = comp.compare(BigArrays.get(x, a), BigArrays.get(x, c));
/* 566 */     int bc = comp.compare(BigArrays.get(x, b), BigArrays.get(x, c));
/* 567 */     return (ab < 0) ? ((bc < 0) ? b : ((ac < 0) ? c : a)) : ((bc > 0) ? b : ((ac > 0) ? c : a));
/*     */   }
/*     */   private static void selectionSort(boolean[][] a, long from, long to, BooleanComparator comp) {
/*     */     long i;
/* 571 */     for (i = from; i < to - 1L; i++) {
/* 572 */       long m = i; long j;
/* 573 */       for (j = i + 1L; j < to; ) { if (comp.compare(BigArrays.get(a, j), BigArrays.get(a, m)) < 0) m = j;  j++; }
/* 574 */        if (m != i) BigArrays.swap(a, i, m);
/*     */     
/*     */     } 
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
/*     */   public static void quickSort(boolean[][] x, long from, long to, BooleanComparator comp) {
/* 593 */     long len = to - from;
/*     */     
/* 595 */     if (len < 7L) {
/* 596 */       selectionSort(x, from, to, comp);
/*     */       
/*     */       return;
/*     */     } 
/* 600 */     long m = from + len / 2L;
/* 601 */     if (len > 7L) {
/* 602 */       long l = from;
/* 603 */       long n = to - 1L;
/* 604 */       if (len > 40L) {
/* 605 */         long s = len / 8L;
/* 606 */         l = med3(x, l, l + s, l + 2L * s, comp);
/* 607 */         m = med3(x, m - s, m, m + s, comp);
/* 608 */         n = med3(x, n - 2L * s, n - s, n, comp);
/*     */       } 
/* 610 */       m = med3(x, l, m, n, comp);
/*     */     } 
/* 612 */     boolean v = BigArrays.get(x, m);
/*     */     
/* 614 */     long a = from, b = a, c = to - 1L, d = c;
/*     */     
/*     */     int comparison;
/* 617 */     while (b <= c && (comparison = comp.compare(BigArrays.get(x, b), v)) <= 0) {
/* 618 */       if (comparison == 0) BigArrays.swap(x, a++, b); 
/* 619 */       b++;
/*     */     } 
/* 621 */     while (c >= b && (comparison = comp.compare(BigArrays.get(x, c), v)) >= 0) {
/* 622 */       if (comparison == 0) BigArrays.swap(x, c, d--); 
/* 623 */       c--;
/*     */     } 
/* 625 */     if (b > c) {
/*     */ 
/*     */ 
/*     */       
/* 629 */       long n = to;
/* 630 */       long s = Math.min(a - from, b - a);
/* 631 */       swap(x, from, b - s, s);
/* 632 */       s = Math.min(d - c, n - d - 1L);
/* 633 */       swap(x, b, n - s, s);
/*     */       
/* 635 */       if ((s = b - a) > 1L) quickSort(x, from, from + s, comp); 
/* 636 */       if ((s = d - c) > 1L) quickSort(x, n - s, n, comp); 
/*     */       return;
/*     */     } 
/*     */     BigArrays.swap(x, b++, c--); } private static long med3(boolean[][] x, long a, long b, long c) {
/* 640 */     int ab = Boolean.compare(BigArrays.get(x, a), BigArrays.get(x, b));
/* 641 */     int ac = Boolean.compare(BigArrays.get(x, a), BigArrays.get(x, c));
/* 642 */     int bc = Boolean.compare(BigArrays.get(x, b), BigArrays.get(x, c));
/* 643 */     return (ab < 0) ? ((bc < 0) ? b : ((ac < 0) ? c : a)) : ((bc > 0) ? b : ((ac > 0) ? c : a));
/*     */   }
/*     */   private static void selectionSort(boolean[][] a, long from, long to) {
/*     */     long i;
/* 647 */     for (i = from; i < to - 1L; i++) {
/* 648 */       long m = i; long j;
/* 649 */       for (j = i + 1L; j < to; ) { if (!BigArrays.get(a, j) && BigArrays.get(a, m)) m = j;  j++; }
/* 650 */        if (m != i) BigArrays.swap(a, i, m);
/*     */     
/*     */     } 
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
/*     */   public static void quickSort(boolean[][] x, BooleanComparator comp) {
/* 668 */     quickSort(x, 0L, BigArrays.length(x), comp);
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
/*     */   public static void quickSort(boolean[][] x, long from, long to) {
/* 685 */     long len = to - from;
/*     */     
/* 687 */     if (len < 7L) {
/* 688 */       selectionSort(x, from, to);
/*     */       
/*     */       return;
/*     */     } 
/* 692 */     long m = from + len / 2L;
/* 693 */     if (len > 7L) {
/* 694 */       long l = from;
/* 695 */       long n = to - 1L;
/* 696 */       if (len > 40L) {
/* 697 */         long s = len / 8L;
/* 698 */         l = med3(x, l, l + s, l + 2L * s);
/* 699 */         m = med3(x, m - s, m, m + s);
/* 700 */         n = med3(x, n - 2L * s, n - s, n);
/*     */       } 
/* 702 */       m = med3(x, l, m, n);
/*     */     } 
/* 704 */     boolean v = BigArrays.get(x, m);
/*     */     
/* 706 */     long a = from, b = a, c = to - 1L, d = c;
/*     */     
/*     */     int comparison;
/* 709 */     while (b <= c && (comparison = Boolean.compare(BigArrays.get(x, b), v)) <= 0) {
/* 710 */       if (comparison == 0) BigArrays.swap(x, a++, b); 
/* 711 */       b++;
/*     */     } 
/* 713 */     while (c >= b && (comparison = Boolean.compare(BigArrays.get(x, c), v)) >= 0) {
/* 714 */       if (comparison == 0) BigArrays.swap(x, c, d--); 
/* 715 */       c--;
/*     */     } 
/* 717 */     if (b > c) {
/*     */ 
/*     */ 
/*     */       
/* 721 */       long n = to;
/* 722 */       long s = Math.min(a - from, b - a);
/* 723 */       swap(x, from, b - s, s);
/* 724 */       s = Math.min(d - c, n - d - 1L);
/* 725 */       swap(x, b, n - s, s);
/*     */       
/* 727 */       if ((s = b - a) > 1L) quickSort(x, from, from + s); 
/* 728 */       if ((s = d - c) > 1L) quickSort(x, n - s, n);
/*     */       
/*     */       return;
/*     */     } 
/*     */     BigArrays.swap(x, b++, c--);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static void quickSort(boolean[][] x) {
/* 742 */     quickSort(x, 0L, BigArrays.length(x));
/*     */   }
/*     */   
/*     */   protected static class ForkJoinQuickSort extends RecursiveAction {
/*     */     private static final long serialVersionUID = 1L;
/*     */     private final long from;
/*     */     private final long to;
/*     */     private final boolean[][] x;
/*     */     
/*     */     public ForkJoinQuickSort(boolean[][] x, long from, long to) {
/* 752 */       this.from = from;
/* 753 */       this.to = to;
/* 754 */       this.x = x;
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     protected void compute() {
/* 760 */       boolean[][] x = this.x;
/* 761 */       long len = this.to - this.from;
/* 762 */       if (len < 8192L) {
/* 763 */         BooleanBigArrays.quickSort(x, this.from, this.to);
/*     */         
/*     */         return;
/*     */       } 
/* 767 */       long m = this.from + len / 2L;
/* 768 */       long l = this.from;
/* 769 */       long n = this.to - 1L;
/* 770 */       long s = len / 8L;
/* 771 */       l = BooleanBigArrays.med3(x, l, l + s, l + 2L * s);
/* 772 */       m = BooleanBigArrays.med3(x, m - s, m, m + s);
/* 773 */       n = BooleanBigArrays.med3(x, n - 2L * s, n - s, n);
/* 774 */       m = BooleanBigArrays.med3(x, l, m, n);
/* 775 */       boolean v = BigArrays.get(x, m);
/*     */       
/* 777 */       long a = this.from, b = a, c = this.to - 1L, d = c;
/*     */       
/*     */       int comparison;
/* 780 */       while (b <= c && (comparison = Boolean.compare(BigArrays.get(x, b), v)) <= 0) {
/* 781 */         if (comparison == 0) BigArrays.swap(x, a++, b); 
/* 782 */         b++;
/*     */       } 
/* 784 */       while (c >= b && (comparison = Boolean.compare(BigArrays.get(x, c), v)) >= 0) {
/* 785 */         if (comparison == 0) BigArrays.swap(x, c, d--); 
/* 786 */         c--;
/*     */       } 
/* 788 */       if (b > c) {
/*     */ 
/*     */ 
/*     */ 
/*     */         
/* 793 */         s = Math.min(a - this.from, b - a);
/* 794 */         BooleanBigArrays.swap(x, this.from, b - s, s);
/* 795 */         s = Math.min(d - c, this.to - d - 1L);
/* 796 */         BooleanBigArrays.swap(x, b, this.to - s, s);
/*     */         
/* 798 */         s = b - a;
/* 799 */         long t = d - c;
/* 800 */         if (s > 1L && t > 1L) { invokeAll(new ForkJoinQuickSort(x, this.from, this.from + s), new ForkJoinQuickSort(x, this.to - t, this.to)); }
/* 801 */         else if (s > 1L) { invokeAll((ForkJoinTask<?>[])new ForkJoinTask[] { new ForkJoinQuickSort(x, this.from, this.from + s) }); }
/* 802 */         else { invokeAll((ForkJoinTask<?>[])new ForkJoinTask[] { new ForkJoinQuickSort(x, this.to - t, this.to) }); }
/*     */         
/*     */         return;
/*     */       } 
/*     */       BigArrays.swap(x, b++, c--);
/*     */     }
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
/*     */   public static void parallelQuickSort(boolean[][] x, long from, long to) {
/* 820 */     ForkJoinPool pool = getPool();
/* 821 */     if (to - from < 8192L || pool.getParallelism() == 1) { quickSort(x, from, to); }
/*     */     else
/* 823 */     { pool.invoke(new ForkJoinQuickSort(x, from, to)); }
/*     */   
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
/*     */   public static void parallelQuickSort(boolean[][] x) {
/* 838 */     parallelQuickSort(x, 0L, BigArrays.length(x));
/*     */   }
/*     */   
/*     */   protected static class ForkJoinQuickSortComp extends RecursiveAction {
/*     */     private static final long serialVersionUID = 1L;
/*     */     private final long from;
/*     */     private final long to;
/*     */     private final boolean[][] x;
/*     */     private final BooleanComparator comp;
/*     */     
/*     */     public ForkJoinQuickSortComp(boolean[][] x, long from, long to, BooleanComparator comp) {
/* 849 */       this.from = from;
/* 850 */       this.to = to;
/* 851 */       this.x = x;
/* 852 */       this.comp = comp;
/*     */     }
/*     */ 
/*     */     
/*     */     protected void compute() {
/* 857 */       boolean[][] x = this.x;
/* 858 */       long len = this.to - this.from;
/* 859 */       if (len < 8192L) {
/* 860 */         BooleanBigArrays.quickSort(x, this.from, this.to, this.comp);
/*     */         
/*     */         return;
/*     */       } 
/* 864 */       long m = this.from + len / 2L;
/* 865 */       long l = this.from;
/* 866 */       long n = this.to - 1L;
/* 867 */       long s = len / 8L;
/* 868 */       l = BooleanBigArrays.med3(x, l, l + s, l + 2L * s, this.comp);
/* 869 */       m = BooleanBigArrays.med3(x, m - s, m, m + s, this.comp);
/* 870 */       n = BooleanBigArrays.med3(x, n - 2L * s, n - s, n, this.comp);
/* 871 */       m = BooleanBigArrays.med3(x, l, m, n, this.comp);
/* 872 */       boolean v = BigArrays.get(x, m);
/*     */       
/* 874 */       long a = this.from, b = a, c = this.to - 1L, d = c;
/*     */       
/*     */       int comparison;
/* 877 */       while (b <= c && (comparison = this.comp.compare(BigArrays.get(x, b), v)) <= 0) {
/* 878 */         if (comparison == 0) BigArrays.swap(x, a++, b); 
/* 879 */         b++;
/*     */       } 
/* 881 */       while (c >= b && (comparison = this.comp.compare(BigArrays.get(x, c), v)) >= 0) {
/* 882 */         if (comparison == 0) BigArrays.swap(x, c, d--); 
/* 883 */         c--;
/*     */       } 
/* 885 */       if (b > c) {
/*     */ 
/*     */ 
/*     */ 
/*     */         
/* 890 */         s = Math.min(a - this.from, b - a);
/* 891 */         BooleanBigArrays.swap(x, this.from, b - s, s);
/* 892 */         s = Math.min(d - c, this.to - d - 1L);
/* 893 */         BooleanBigArrays.swap(x, b, this.to - s, s);
/*     */         
/* 895 */         s = b - a;
/* 896 */         long t = d - c;
/* 897 */         if (s > 1L && t > 1L) { invokeAll(new ForkJoinQuickSortComp(x, this.from, this.from + s, this.comp), new ForkJoinQuickSortComp(x, this.to - t, this.to, this.comp)); }
/* 898 */         else if (s > 1L) { invokeAll((ForkJoinTask<?>[])new ForkJoinTask[] { new ForkJoinQuickSortComp(x, this.from, this.from + s, this.comp) }); }
/* 899 */         else { invokeAll((ForkJoinTask<?>[])new ForkJoinTask[] { new ForkJoinQuickSortComp(x, this.to - t, this.to, this.comp) }); }
/*     */         
/*     */         return;
/*     */       } 
/*     */       BigArrays.swap(x, b++, c--);
/*     */     }
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
/*     */   public static void parallelQuickSort(boolean[][] x, long from, long to, BooleanComparator comp) {
/* 918 */     ForkJoinPool pool = getPool();
/* 919 */     if (to - from < 8192L || pool.getParallelism() == 1) { quickSort(x, from, to, comp); }
/*     */     else
/* 921 */     { pool.invoke(new ForkJoinQuickSortComp(x, from, to, comp)); }
/*     */   
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
/*     */   public static void parallelQuickSort(boolean[][] x, BooleanComparator comp) {
/* 938 */     parallelQuickSort(x, 0L, BigArrays.length(x), comp);
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
/*     */   public static boolean[][] shuffle(boolean[][] a, long from, long to, Random random) {
/* 951 */     return BigArrays.shuffle(a, from, to, random);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static boolean[][] shuffle(boolean[][] a, Random random) {
/* 962 */     return BigArrays.shuffle(a, random);
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\i\\unimi\dsi\fastutil\booleans\BooleanBigArrays.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */