/*      */ package it.unimi.dsi.fastutil.ints;
/*      */ 
/*      */ import it.unimi.dsi.fastutil.Arrays;
/*      */ import it.unimi.dsi.fastutil.Hash;
/*      */ import java.io.Serializable;
/*      */ import java.util.Arrays;
/*      */ import java.util.Random;
/*      */ import java.util.concurrent.ExecutorCompletionService;
/*      */ import java.util.concurrent.ForkJoinPool;
/*      */ import java.util.concurrent.ForkJoinTask;
/*      */ import java.util.concurrent.LinkedBlockingQueue;
/*      */ import java.util.concurrent.RecursiveAction;
/*      */ import java.util.concurrent.atomic.AtomicInteger;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ public final class IntArrays
/*      */ {
/*  104 */   public static final int[] EMPTY_ARRAY = new int[0];
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*  112 */   public static final int[] DEFAULT_EMPTY_ARRAY = new int[0]; private static final int QUICKSORT_NO_REC = 16;
/*      */   private static final int PARALLEL_QUICKSORT_NO_FORK = 8192;
/*      */   private static final int QUICKSORT_MEDIAN_OF_9 = 128;
/*      */   private static final int MERGESORT_NO_REC = 16;
/*      */   private static final int DIGIT_BITS = 8;
/*      */   private static final int DIGIT_MASK = 255;
/*      */   private static final int DIGITS_PER_ELEMENT = 4;
/*      */   private static final int RADIXSORT_NO_REC = 1024;
/*      */   private static final int RADIXSORT_NO_REC_SMALL = 64;
/*      */   private static final int PARALLEL_RADIXSORT_NO_FORK = 1024;
/*      */   static final int RADIX_SORT_MIN_THRESHOLD = 2000;
/*      */   
/*      */   public static int[] forceCapacity(int[] array, int length, int preserve) {
/*  125 */     int[] t = new int[length];
/*  126 */     System.arraycopy(array, 0, t, 0, preserve);
/*  127 */     return t;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static int[] ensureCapacity(int[] array, int length) {
/*  144 */     return ensureCapacity(array, length, array.length);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static int[] ensureCapacity(int[] array, int length, int preserve) {
/*  160 */     return (length > array.length) ? forceCapacity(array, length, preserve) : array;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static int[] grow(int[] array, int length) {
/*  178 */     return grow(array, length, array.length);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static int[] grow(int[] array, int length, int preserve) {
/*  199 */     if (length > array.length) {
/*  200 */       int newLength = (int)Math.max(Math.min(array.length + (array.length >> 1), 2147483639L), length);
/*  201 */       int[] t = new int[newLength];
/*  202 */       System.arraycopy(array, 0, t, 0, preserve);
/*  203 */       return t;
/*      */     } 
/*  205 */     return array;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static int[] trim(int[] array, int length) {
/*  219 */     if (length >= array.length) return array; 
/*  220 */     int[] t = (length == 0) ? EMPTY_ARRAY : new int[length];
/*  221 */     System.arraycopy(array, 0, t, 0, length);
/*  222 */     return t;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static int[] setLength(int[] array, int length) {
/*  238 */     if (length == array.length) return array; 
/*  239 */     if (length < array.length) return trim(array, length); 
/*  240 */     return ensureCapacity(array, length);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static int[] copy(int[] array, int offset, int length) {
/*  253 */     ensureOffsetLength(array, offset, length);
/*  254 */     int[] a = (length == 0) ? EMPTY_ARRAY : new int[length];
/*  255 */     System.arraycopy(array, offset, a, 0, length);
/*  256 */     return a;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static int[] copy(int[] array) {
/*  266 */     return (int[])array.clone();
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
/*      */   public static void fill(int[] array, int value) {
/*  278 */     int i = array.length;
/*  279 */     for (; i-- != 0; array[i] = value);
/*      */   }
/*      */ 
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
/*      */   public static void fill(int[] array, int from, int to, int value) {
/*  293 */     ensureFromTo(array, from, to);
/*  294 */     if (from == 0) { for (; to-- != 0; array[to] = value); }
/*  295 */     else { for (int i = from; i < to; ) { array[i] = value; i++; }
/*      */        }
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
/*      */   @Deprecated
/*      */   public static boolean equals(int[] a1, int[] a2) {
/*  309 */     int i = a1.length;
/*  310 */     if (i != a2.length) return false; 
/*  311 */     while (i-- != 0) { if (a1[i] != a2[i]) return false;  }
/*  312 */      return true;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static void ensureFromTo(int[] a, int from, int to) {
/*  334 */     Arrays.ensureFromTo(a.length, from, to);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static void ensureOffsetLength(int[] a, int offset, int length) {
/*  356 */     Arrays.ensureOffsetLength(a.length, offset, length);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static void ensureSameLength(int[] a, int[] b) {
/*  367 */     if (a.length != b.length) throw new IllegalArgumentException("Array size mismatch: " + a.length + " != " + b.length);
/*      */   
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private static ForkJoinPool getPool() {
/*  377 */     ForkJoinPool current = ForkJoinTask.getPool();
/*  378 */     return (current == null) ? ForkJoinPool.commonPool() : current;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static void swap(int[] x, int a, int b) {
/*  389 */     int t = x[a];
/*  390 */     x[a] = x[b];
/*  391 */     x[b] = t;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static void swap(int[] x, int a, int b, int n) {
/*  403 */     for (int i = 0; i < n; ) { swap(x, a, b); i++; a++; b++; }
/*      */   
/*      */   }
/*      */   private static int med3(int[] x, int a, int b, int c, IntComparator comp) {
/*  407 */     int ab = comp.compare(x[a], x[b]);
/*  408 */     int ac = comp.compare(x[a], x[c]);
/*  409 */     int bc = comp.compare(x[b], x[c]);
/*  410 */     return (ab < 0) ? ((bc < 0) ? b : ((ac < 0) ? c : a)) : ((bc > 0) ? b : ((ac > 0) ? c : a));
/*      */   }
/*      */   
/*      */   private static void selectionSort(int[] a, int from, int to, IntComparator comp) {
/*  414 */     for (int i = from; i < to - 1; i++) {
/*  415 */       int m = i;
/*  416 */       for (int j = i + 1; j < to; ) { if (comp.compare(a[j], a[m]) < 0) m = j;  j++; }
/*  417 */        if (m != i) {
/*  418 */         int u = a[i];
/*  419 */         a[i] = a[m];
/*  420 */         a[m] = u;
/*      */       } 
/*      */     } 
/*      */   }
/*      */   
/*      */   private static void insertionSort(int[] a, int from, int to, IntComparator comp) {
/*  426 */     for (int i = from; ++i < to; ) {
/*  427 */       int t = a[i];
/*  428 */       int j = i; int u;
/*  429 */       for (u = a[j - 1]; comp.compare(t, u) < 0; u = a[--j - 1]) {
/*  430 */         a[j] = u;
/*  431 */         if (from == j - 1) {
/*  432 */           j--;
/*      */           break;
/*      */         } 
/*      */       } 
/*  436 */       a[j] = t;
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
/*      */   public static void quickSort(int[] x, int from, int to, IntComparator comp) {
/*  460 */     int len = to - from;
/*      */     
/*  462 */     if (len < 16) {
/*  463 */       selectionSort(x, from, to, comp);
/*      */       
/*      */       return;
/*      */     } 
/*  467 */     int m = from + len / 2;
/*  468 */     int l = from;
/*  469 */     int n = to - 1;
/*  470 */     if (len > 128) {
/*  471 */       int i = len / 8;
/*  472 */       l = med3(x, l, l + i, l + 2 * i, comp);
/*  473 */       m = med3(x, m - i, m, m + i, comp);
/*  474 */       n = med3(x, n - 2 * i, n - i, n, comp);
/*      */     } 
/*  476 */     m = med3(x, l, m, n, comp);
/*  477 */     int v = x[m];
/*      */     
/*  479 */     int a = from, b = a, c = to - 1, d = c;
/*      */     while (true) {
/*      */       int comparison;
/*  482 */       if (b <= c && (comparison = comp.compare(x[b], v)) <= 0) {
/*  483 */         if (comparison == 0) swap(x, a++, b); 
/*  484 */         b++; continue;
/*      */       } 
/*  486 */       while (c >= b && (comparison = comp.compare(x[c], v)) >= 0) {
/*  487 */         if (comparison == 0) swap(x, c, d--); 
/*  488 */         c--;
/*      */       } 
/*  490 */       if (b > c)
/*  491 */         break;  swap(x, b++, c--);
/*      */     } 
/*      */ 
/*      */     
/*  495 */     int s = Math.min(a - from, b - a);
/*  496 */     swap(x, from, b - s, s);
/*  497 */     s = Math.min(d - c, to - d - 1);
/*  498 */     swap(x, b, to - s, s);
/*      */     
/*  500 */     if ((s = b - a) > 1) quickSort(x, from, from + s, comp); 
/*  501 */     if ((s = d - c) > 1) quickSort(x, to - s, to, comp);
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
/*      */   public static void quickSort(int[] x, IntComparator comp) {
/*  521 */     quickSort(x, 0, x.length, comp);
/*      */   }
/*      */   
/*      */   protected static class ForkJoinQuickSortComp extends RecursiveAction {
/*      */     private static final long serialVersionUID = 1L;
/*      */     private final int from;
/*      */     private final int to;
/*      */     private final int[] x;
/*      */     private final IntComparator comp;
/*      */     
/*      */     public ForkJoinQuickSortComp(int[] x, int from, int to, IntComparator comp) {
/*  532 */       this.from = from;
/*  533 */       this.to = to;
/*  534 */       this.x = x;
/*  535 */       this.comp = comp;
/*      */     }
/*      */ 
/*      */     
/*      */     protected void compute() {
/*  540 */       int[] x = this.x;
/*  541 */       int len = this.to - this.from;
/*  542 */       if (len < 8192) {
/*  543 */         IntArrays.quickSort(x, this.from, this.to, this.comp);
/*      */         
/*      */         return;
/*      */       } 
/*  547 */       int m = this.from + len / 2;
/*  548 */       int l = this.from;
/*  549 */       int n = this.to - 1;
/*  550 */       int s = len / 8;
/*  551 */       l = IntArrays.med3(x, l, l + s, l + 2 * s, this.comp);
/*  552 */       m = IntArrays.med3(x, m - s, m, m + s, this.comp);
/*  553 */       n = IntArrays.med3(x, n - 2 * s, n - s, n, this.comp);
/*  554 */       m = IntArrays.med3(x, l, m, n, this.comp);
/*  555 */       int v = x[m];
/*      */       
/*  557 */       int a = this.from, b = a, c = this.to - 1, d = c;
/*      */       while (true) {
/*      */         int comparison;
/*  560 */         if (b <= c && (comparison = this.comp.compare(x[b], v)) <= 0) {
/*  561 */           if (comparison == 0) IntArrays.swap(x, a++, b); 
/*  562 */           b++; continue;
/*      */         } 
/*  564 */         while (c >= b && (comparison = this.comp.compare(x[c], v)) >= 0) {
/*  565 */           if (comparison == 0) IntArrays.swap(x, c, d--); 
/*  566 */           c--;
/*      */         } 
/*  568 */         if (b > c)
/*  569 */           break;  IntArrays.swap(x, b++, c--);
/*      */       } 
/*      */ 
/*      */       
/*  573 */       s = Math.min(a - this.from, b - a);
/*  574 */       IntArrays.swap(x, this.from, b - s, s);
/*  575 */       s = Math.min(d - c, this.to - d - 1);
/*  576 */       IntArrays.swap(x, b, this.to - s, s);
/*      */       
/*  578 */       s = b - a;
/*  579 */       int t = d - c;
/*  580 */       if (s > 1 && t > 1) { invokeAll(new ForkJoinQuickSortComp(x, this.from, this.from + s, this.comp), new ForkJoinQuickSortComp(x, this.to - t, this.to, this.comp)); }
/*  581 */       else if (s > 1) { invokeAll((ForkJoinTask<?>[])new ForkJoinTask[] { new ForkJoinQuickSortComp(x, this.from, this.from + s, this.comp) }); }
/*  582 */       else { invokeAll((ForkJoinTask<?>[])new ForkJoinTask[] { new ForkJoinQuickSortComp(x, this.to - t, this.to, this.comp) }); }
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
/*      */   public static void parallelQuickSort(int[] x, int from, int to, IntComparator comp) {
/*  601 */     ForkJoinPool pool = getPool();
/*  602 */     if (to - from < 8192 || pool.getParallelism() == 1) { quickSort(x, from, to, comp); }
/*      */     else
/*  604 */     { pool.invoke(new ForkJoinQuickSortComp(x, from, to, comp)); }
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
/*      */   public static void parallelQuickSort(int[] x, IntComparator comp) {
/*  621 */     parallelQuickSort(x, 0, x.length, comp);
/*      */   }
/*      */   
/*      */   private static int med3(int[] x, int a, int b, int c) {
/*  625 */     int ab = Integer.compare(x[a], x[b]);
/*  626 */     int ac = Integer.compare(x[a], x[c]);
/*  627 */     int bc = Integer.compare(x[b], x[c]);
/*  628 */     return (ab < 0) ? ((bc < 0) ? b : ((ac < 0) ? c : a)) : ((bc > 0) ? b : ((ac > 0) ? c : a));
/*      */   }
/*      */   
/*      */   private static void selectionSort(int[] a, int from, int to) {
/*  632 */     for (int i = from; i < to - 1; i++) {
/*  633 */       int m = i;
/*  634 */       for (int j = i + 1; j < to; ) { if (a[j] < a[m]) m = j;  j++; }
/*  635 */        if (m != i) {
/*  636 */         int u = a[i];
/*  637 */         a[i] = a[m];
/*  638 */         a[m] = u;
/*      */       } 
/*      */     } 
/*      */   }
/*      */   
/*      */   private static void insertionSort(int[] a, int from, int to) {
/*  644 */     for (int i = from; ++i < to; ) {
/*  645 */       int t = a[i];
/*  646 */       int j = i; int u;
/*  647 */       for (u = a[j - 1]; t < u; u = a[--j - 1]) {
/*  648 */         a[j] = u;
/*  649 */         if (from == j - 1) {
/*  650 */           j--;
/*      */           break;
/*      */         } 
/*      */       } 
/*  654 */       a[j] = t;
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
/*      */   public static void quickSort(int[] x, int from, int to) {
/*  676 */     int len = to - from;
/*      */     
/*  678 */     if (len < 16) {
/*  679 */       selectionSort(x, from, to);
/*      */       
/*      */       return;
/*      */     } 
/*  683 */     int m = from + len / 2;
/*  684 */     int l = from;
/*  685 */     int n = to - 1;
/*  686 */     if (len > 128) {
/*  687 */       int i = len / 8;
/*  688 */       l = med3(x, l, l + i, l + 2 * i);
/*  689 */       m = med3(x, m - i, m, m + i);
/*  690 */       n = med3(x, n - 2 * i, n - i, n);
/*      */     } 
/*  692 */     m = med3(x, l, m, n);
/*  693 */     int v = x[m];
/*      */     
/*  695 */     int a = from, b = a, c = to - 1, d = c;
/*      */     while (true) {
/*      */       int comparison;
/*  698 */       if (b <= c && (comparison = Integer.compare(x[b], v)) <= 0) {
/*  699 */         if (comparison == 0) swap(x, a++, b); 
/*  700 */         b++; continue;
/*      */       } 
/*  702 */       while (c >= b && (comparison = Integer.compare(x[c], v)) >= 0) {
/*  703 */         if (comparison == 0) swap(x, c, d--); 
/*  704 */         c--;
/*      */       } 
/*  706 */       if (b > c)
/*  707 */         break;  swap(x, b++, c--);
/*      */     } 
/*      */ 
/*      */     
/*  711 */     int s = Math.min(a - from, b - a);
/*  712 */     swap(x, from, b - s, s);
/*  713 */     s = Math.min(d - c, to - d - 1);
/*  714 */     swap(x, b, to - s, s);
/*      */     
/*  716 */     if ((s = b - a) > 1) quickSort(x, from, from + s); 
/*  717 */     if ((s = d - c) > 1) quickSort(x, to - s, to);
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
/*      */   public static void quickSort(int[] x) {
/*  736 */     quickSort(x, 0, x.length);
/*      */   }
/*      */   
/*      */   protected static class ForkJoinQuickSort extends RecursiveAction {
/*      */     private static final long serialVersionUID = 1L;
/*      */     private final int from;
/*      */     private final int to;
/*      */     private final int[] x;
/*      */     
/*      */     public ForkJoinQuickSort(int[] x, int from, int to) {
/*  746 */       this.from = from;
/*  747 */       this.to = to;
/*  748 */       this.x = x;
/*      */     }
/*      */ 
/*      */ 
/*      */     
/*      */     protected void compute() {
/*  754 */       int[] x = this.x;
/*  755 */       int len = this.to - this.from;
/*  756 */       if (len < 8192) {
/*  757 */         IntArrays.quickSort(x, this.from, this.to);
/*      */         
/*      */         return;
/*      */       } 
/*  761 */       int m = this.from + len / 2;
/*  762 */       int l = this.from;
/*  763 */       int n = this.to - 1;
/*  764 */       int s = len / 8;
/*  765 */       l = IntArrays.med3(x, l, l + s, l + 2 * s);
/*  766 */       m = IntArrays.med3(x, m - s, m, m + s);
/*  767 */       n = IntArrays.med3(x, n - 2 * s, n - s, n);
/*  768 */       m = IntArrays.med3(x, l, m, n);
/*  769 */       int v = x[m];
/*      */       
/*  771 */       int a = this.from, b = a, c = this.to - 1, d = c;
/*      */       while (true) {
/*      */         int comparison;
/*  774 */         if (b <= c && (comparison = Integer.compare(x[b], v)) <= 0) {
/*  775 */           if (comparison == 0) IntArrays.swap(x, a++, b); 
/*  776 */           b++; continue;
/*      */         } 
/*  778 */         while (c >= b && (comparison = Integer.compare(x[c], v)) >= 0) {
/*  779 */           if (comparison == 0) IntArrays.swap(x, c, d--); 
/*  780 */           c--;
/*      */         } 
/*  782 */         if (b > c)
/*  783 */           break;  IntArrays.swap(x, b++, c--);
/*      */       } 
/*      */ 
/*      */       
/*  787 */       s = Math.min(a - this.from, b - a);
/*  788 */       IntArrays.swap(x, this.from, b - s, s);
/*  789 */       s = Math.min(d - c, this.to - d - 1);
/*  790 */       IntArrays.swap(x, b, this.to - s, s);
/*      */       
/*  792 */       s = b - a;
/*  793 */       int t = d - c;
/*  794 */       if (s > 1 && t > 1) { invokeAll(new ForkJoinQuickSort(x, this.from, this.from + s), new ForkJoinQuickSort(x, this.to - t, this.to)); }
/*  795 */       else if (s > 1) { invokeAll((ForkJoinTask<?>[])new ForkJoinTask[] { new ForkJoinQuickSort(x, this.from, this.from + s) }); }
/*  796 */       else { invokeAll((ForkJoinTask<?>[])new ForkJoinTask[] { new ForkJoinQuickSort(x, this.to - t, this.to) }); }
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
/*      */   public static void parallelQuickSort(int[] x, int from, int to) {
/*  814 */     ForkJoinPool pool = getPool();
/*  815 */     if (to - from < 8192 || pool.getParallelism() == 1) { quickSort(x, from, to); }
/*      */     else
/*  817 */     { pool.invoke(new ForkJoinQuickSort(x, from, to)); }
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
/*      */   public static void parallelQuickSort(int[] x) {
/*  833 */     parallelQuickSort(x, 0, x.length);
/*      */   }
/*      */   
/*      */   private static int med3Indirect(int[] perm, int[] x, int a, int b, int c) {
/*  837 */     int aa = x[perm[a]];
/*  838 */     int bb = x[perm[b]];
/*  839 */     int cc = x[perm[c]];
/*  840 */     int ab = Integer.compare(aa, bb);
/*  841 */     int ac = Integer.compare(aa, cc);
/*  842 */     int bc = Integer.compare(bb, cc);
/*  843 */     return (ab < 0) ? ((bc < 0) ? b : ((ac < 0) ? c : a)) : ((bc > 0) ? b : ((ac > 0) ? c : a));
/*      */   }
/*      */   
/*      */   private static void insertionSortIndirect(int[] perm, int[] a, int from, int to) {
/*  847 */     for (int i = from; ++i < to; ) {
/*  848 */       int t = perm[i];
/*  849 */       int j = i; int u;
/*  850 */       for (u = perm[j - 1]; a[t] < a[u]; u = perm[--j - 1]) {
/*  851 */         perm[j] = u;
/*  852 */         if (from == j - 1) {
/*  853 */           j--;
/*      */           break;
/*      */         } 
/*      */       } 
/*  857 */       perm[j] = t;
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
/*      */   
/*      */   public static void quickSortIndirect(int[] perm, int[] x, int from, int to) {
/*  886 */     int len = to - from;
/*      */     
/*  888 */     if (len < 16) {
/*  889 */       insertionSortIndirect(perm, x, from, to);
/*      */       
/*      */       return;
/*      */     } 
/*  893 */     int m = from + len / 2;
/*  894 */     int l = from;
/*  895 */     int n = to - 1;
/*  896 */     if (len > 128) {
/*  897 */       int i = len / 8;
/*  898 */       l = med3Indirect(perm, x, l, l + i, l + 2 * i);
/*  899 */       m = med3Indirect(perm, x, m - i, m, m + i);
/*  900 */       n = med3Indirect(perm, x, n - 2 * i, n - i, n);
/*      */     } 
/*  902 */     m = med3Indirect(perm, x, l, m, n);
/*  903 */     int v = x[perm[m]];
/*      */     
/*  905 */     int a = from, b = a, c = to - 1, d = c;
/*      */     while (true) {
/*      */       int comparison;
/*  908 */       if (b <= c && (comparison = Integer.compare(x[perm[b]], v)) <= 0) {
/*  909 */         if (comparison == 0) swap(perm, a++, b); 
/*  910 */         b++; continue;
/*      */       } 
/*  912 */       while (c >= b && (comparison = Integer.compare(x[perm[c]], v)) >= 0) {
/*  913 */         if (comparison == 0) swap(perm, c, d--); 
/*  914 */         c--;
/*      */       } 
/*  916 */       if (b > c)
/*  917 */         break;  swap(perm, b++, c--);
/*      */     } 
/*      */ 
/*      */     
/*  921 */     int s = Math.min(a - from, b - a);
/*  922 */     swap(perm, from, b - s, s);
/*  923 */     s = Math.min(d - c, to - d - 1);
/*  924 */     swap(perm, b, to - s, s);
/*      */     
/*  926 */     if ((s = b - a) > 1) quickSortIndirect(perm, x, from, from + s); 
/*  927 */     if ((s = d - c) > 1) quickSortIndirect(perm, x, to - s, to);
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
/*      */   public static void quickSortIndirect(int[] perm, int[] x) {
/*  951 */     quickSortIndirect(perm, x, 0, x.length);
/*      */   }
/*      */   
/*      */   protected static class ForkJoinQuickSortIndirect extends RecursiveAction {
/*      */     private static final long serialVersionUID = 1L;
/*      */     private final int from;
/*      */     private final int to;
/*      */     private final int[] perm;
/*      */     private final int[] x;
/*      */     
/*      */     public ForkJoinQuickSortIndirect(int[] perm, int[] x, int from, int to) {
/*  962 */       this.from = from;
/*  963 */       this.to = to;
/*  964 */       this.x = x;
/*  965 */       this.perm = perm;
/*      */     }
/*      */ 
/*      */ 
/*      */     
/*      */     protected void compute() {
/*  971 */       int[] x = this.x;
/*  972 */       int len = this.to - this.from;
/*  973 */       if (len < 8192) {
/*  974 */         IntArrays.quickSortIndirect(this.perm, x, this.from, this.to);
/*      */         
/*      */         return;
/*      */       } 
/*  978 */       int m = this.from + len / 2;
/*  979 */       int l = this.from;
/*  980 */       int n = this.to - 1;
/*  981 */       int s = len / 8;
/*  982 */       l = IntArrays.med3Indirect(this.perm, x, l, l + s, l + 2 * s);
/*  983 */       m = IntArrays.med3Indirect(this.perm, x, m - s, m, m + s);
/*  984 */       n = IntArrays.med3Indirect(this.perm, x, n - 2 * s, n - s, n);
/*  985 */       m = IntArrays.med3Indirect(this.perm, x, l, m, n);
/*  986 */       int v = x[this.perm[m]];
/*      */       
/*  988 */       int a = this.from, b = a, c = this.to - 1, d = c;
/*      */       while (true) {
/*      */         int comparison;
/*  991 */         if (b <= c && (comparison = Integer.compare(x[this.perm[b]], v)) <= 0) {
/*  992 */           if (comparison == 0) IntArrays.swap(this.perm, a++, b); 
/*  993 */           b++; continue;
/*      */         } 
/*  995 */         while (c >= b && (comparison = Integer.compare(x[this.perm[c]], v)) >= 0) {
/*  996 */           if (comparison == 0) IntArrays.swap(this.perm, c, d--); 
/*  997 */           c--;
/*      */         } 
/*  999 */         if (b > c)
/* 1000 */           break;  IntArrays.swap(this.perm, b++, c--);
/*      */       } 
/*      */ 
/*      */       
/* 1004 */       s = Math.min(a - this.from, b - a);
/* 1005 */       IntArrays.swap(this.perm, this.from, b - s, s);
/* 1006 */       s = Math.min(d - c, this.to - d - 1);
/* 1007 */       IntArrays.swap(this.perm, b, this.to - s, s);
/*      */       
/* 1009 */       s = b - a;
/* 1010 */       int t = d - c;
/* 1011 */       if (s > 1 && t > 1) { invokeAll(new ForkJoinQuickSortIndirect(this.perm, x, this.from, this.from + s), new ForkJoinQuickSortIndirect(this.perm, x, this.to - t, this.to)); }
/* 1012 */       else if (s > 1) { invokeAll((ForkJoinTask<?>[])new ForkJoinTask[] { new ForkJoinQuickSortIndirect(this.perm, x, this.from, this.from + s) }); }
/* 1013 */       else { invokeAll((ForkJoinTask<?>[])new ForkJoinTask[] { new ForkJoinQuickSortIndirect(this.perm, x, this.to - t, this.to) }); }
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
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static void parallelQuickSortIndirect(int[] perm, int[] x, int from, int to) {
/* 1037 */     ForkJoinPool pool = getPool();
/* 1038 */     if (to - from < 8192 || pool.getParallelism() == 1) { quickSortIndirect(perm, x, from, to); }
/*      */     else
/* 1040 */     { pool.invoke(new ForkJoinQuickSortIndirect(perm, x, from, to)); }
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
/*      */   public static void parallelQuickSortIndirect(int[] perm, int[] x) {
/* 1062 */     parallelQuickSortIndirect(perm, x, 0, x.length);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static void stabilize(int[] perm, int[] x, int from, int to) {
/* 1089 */     int curr = from;
/* 1090 */     for (int i = from + 1; i < to; i++) {
/* 1091 */       if (x[perm[i]] != x[perm[curr]]) {
/* 1092 */         if (i - curr > 1) parallelQuickSort(perm, curr, i); 
/* 1093 */         curr = i;
/*      */       } 
/*      */     } 
/* 1096 */     if (to - curr > 1) parallelQuickSort(perm, curr, to);
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
/*      */   public static void stabilize(int[] perm, int[] x) {
/* 1121 */     stabilize(perm, x, 0, perm.length);
/*      */   }
/*      */ 
/*      */   
/*      */   private static int med3(int[] x, int[] y, int a, int b, int c) {
/* 1126 */     int t, ab = ((t = Integer.compare(x[a], x[b])) == 0) ? Integer.compare(y[a], y[b]) : t;
/* 1127 */     int ac = ((t = Integer.compare(x[a], x[c])) == 0) ? Integer.compare(y[a], y[c]) : t;
/* 1128 */     int bc = ((t = Integer.compare(x[b], x[c])) == 0) ? Integer.compare(y[b], y[c]) : t;
/* 1129 */     return (ab < 0) ? ((bc < 0) ? b : ((ac < 0) ? c : a)) : ((bc > 0) ? b : ((ac > 0) ? c : a));
/*      */   }
/*      */   
/*      */   private static void swap(int[] x, int[] y, int a, int b) {
/* 1133 */     int t = x[a];
/* 1134 */     int u = y[a];
/* 1135 */     x[a] = x[b];
/* 1136 */     y[a] = y[b];
/* 1137 */     x[b] = t;
/* 1138 */     y[b] = u;
/*      */   }
/*      */   
/*      */   private static void swap(int[] x, int[] y, int a, int b, int n) {
/* 1142 */     for (int i = 0; i < n; ) { swap(x, y, a, b); i++; a++; b++; }
/*      */   
/*      */   }
/*      */   private static void selectionSort(int[] a, int[] b, int from, int to) {
/* 1146 */     for (int i = from; i < to - 1; i++) {
/* 1147 */       int m = i;
/* 1148 */       for (int j = i + 1; j < to; ) { int u; if ((u = Integer.compare(a[j], a[m])) < 0 || (u == 0 && b[j] < b[m])) m = j;  j++; }
/* 1149 */        if (m != i) {
/* 1150 */         int t = a[i];
/* 1151 */         a[i] = a[m];
/* 1152 */         a[m] = t;
/* 1153 */         t = b[i];
/* 1154 */         b[i] = b[m];
/* 1155 */         b[m] = t;
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
/*      */   public static void quickSort(int[] x, int[] y, int from, int to) {
/* 1182 */     int len = to - from;
/* 1183 */     if (len < 16) {
/* 1184 */       selectionSort(x, y, from, to);
/*      */       
/*      */       return;
/*      */     } 
/* 1188 */     int m = from + len / 2;
/* 1189 */     int l = from;
/* 1190 */     int n = to - 1;
/* 1191 */     if (len > 128) {
/* 1192 */       int i = len / 8;
/* 1193 */       l = med3(x, y, l, l + i, l + 2 * i);
/* 1194 */       m = med3(x, y, m - i, m, m + i);
/* 1195 */       n = med3(x, y, n - 2 * i, n - i, n);
/*      */     } 
/* 1197 */     m = med3(x, y, l, m, n);
/* 1198 */     int v = x[m], w = y[m];
/*      */     
/* 1200 */     int a = from, b = a, c = to - 1, d = c;
/*      */     
/*      */     while (true) {
/* 1203 */       if (b <= c) { int comparison; int t; if ((comparison = ((t = Integer.compare(x[b], v)) == 0) ? Integer.compare(y[b], w) : t) <= 0) {
/* 1204 */           if (comparison == 0) swap(x, y, a++, b); 
/* 1205 */           b++; continue;
/*      */         }  }
/* 1207 */        while (c >= b) { int comparison; int t; if ((comparison = ((t = Integer.compare(x[c], v)) == 0) ? Integer.compare(y[c], w) : t) >= 0) {
/* 1208 */           if (comparison == 0) swap(x, y, c, d--); 
/* 1209 */           c--;
/*      */         }  }
/* 1211 */        if (b > c)
/* 1212 */         break;  swap(x, y, b++, c--);
/*      */     } 
/*      */ 
/*      */     
/* 1216 */     int s = Math.min(a - from, b - a);
/* 1217 */     swap(x, y, from, b - s, s);
/* 1218 */     s = Math.min(d - c, to - d - 1);
/* 1219 */     swap(x, y, b, to - s, s);
/*      */     
/* 1221 */     if ((s = b - a) > 1) quickSort(x, y, from, from + s); 
/* 1222 */     if ((s = d - c) > 1) quickSort(x, y, to - s, to);
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
/*      */   public static void quickSort(int[] x, int[] y) {
/* 1243 */     ensureSameLength(x, y);
/* 1244 */     quickSort(x, y, 0, x.length);
/*      */   }
/*      */   
/*      */   protected static class ForkJoinQuickSort2 extends RecursiveAction { private static final long serialVersionUID = 1L;
/*      */     private final int from;
/*      */     private final int to;
/*      */     private final int[] x;
/*      */     private final int[] y;
/*      */     
/*      */     public ForkJoinQuickSort2(int[] x, int[] y, int from, int to) {
/* 1254 */       this.from = from;
/* 1255 */       this.to = to;
/* 1256 */       this.x = x;
/* 1257 */       this.y = y;
/*      */     }
/*      */ 
/*      */ 
/*      */     
/*      */     protected void compute() {
/* 1263 */       int[] x = this.x;
/* 1264 */       int[] y = this.y;
/* 1265 */       int len = this.to - this.from;
/* 1266 */       if (len < 8192) {
/* 1267 */         IntArrays.quickSort(x, y, this.from, this.to);
/*      */         
/*      */         return;
/*      */       } 
/* 1271 */       int m = this.from + len / 2;
/* 1272 */       int l = this.from;
/* 1273 */       int n = this.to - 1;
/* 1274 */       int s = len / 8;
/* 1275 */       l = IntArrays.med3(x, y, l, l + s, l + 2 * s);
/* 1276 */       m = IntArrays.med3(x, y, m - s, m, m + s);
/* 1277 */       n = IntArrays.med3(x, y, n - 2 * s, n - s, n);
/* 1278 */       m = IntArrays.med3(x, y, l, m, n);
/* 1279 */       int v = x[m], w = y[m];
/*      */       
/* 1281 */       int a = this.from, b = a, c = this.to - 1, d = c;
/*      */       
/*      */       while (true) {
/* 1284 */         if (b <= c) { int comparison; int i; if ((comparison = ((i = Integer.compare(x[b], v)) == 0) ? Integer.compare(y[b], w) : i) <= 0) {
/* 1285 */             if (comparison == 0) IntArrays.swap(x, y, a++, b); 
/* 1286 */             b++; continue;
/*      */           }  }
/* 1288 */          while (c >= b) { int comparison; int i; if ((comparison = ((i = Integer.compare(x[c], v)) == 0) ? Integer.compare(y[c], w) : i) >= 0) {
/* 1289 */             if (comparison == 0) IntArrays.swap(x, y, c, d--); 
/* 1290 */             c--;
/*      */           }  }
/* 1292 */          if (b > c)
/* 1293 */           break;  IntArrays.swap(x, y, b++, c--);
/*      */       } 
/*      */ 
/*      */       
/* 1297 */       s = Math.min(a - this.from, b - a);
/* 1298 */       IntArrays.swap(x, y, this.from, b - s, s);
/* 1299 */       s = Math.min(d - c, this.to - d - 1);
/* 1300 */       IntArrays.swap(x, y, b, this.to - s, s);
/* 1301 */       s = b - a;
/* 1302 */       int t = d - c;
/*      */       
/* 1304 */       if (s > 1 && t > 1) { invokeAll(new ForkJoinQuickSort2(x, y, this.from, this.from + s), new ForkJoinQuickSort2(x, y, this.to - t, this.to)); }
/* 1305 */       else if (s > 1) { invokeAll((ForkJoinTask<?>[])new ForkJoinTask[] { new ForkJoinQuickSort2(x, y, this.from, this.from + s) }); }
/* 1306 */       else { invokeAll((ForkJoinTask<?>[])new ForkJoinTask[] { new ForkJoinQuickSort2(x, y, this.to - t, this.to) }); }
/*      */     
/*      */     } }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static void parallelQuickSort(int[] x, int[] y, int from, int to) {
/* 1331 */     ForkJoinPool pool = getPool();
/* 1332 */     if (to - from < 8192 || pool.getParallelism() == 1) { quickSort(x, y, from, to); }
/*      */     else
/* 1334 */     { pool.invoke(new ForkJoinQuickSort2(x, y, from, to)); }
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
/*      */   public static void parallelQuickSort(int[] x, int[] y) {
/* 1357 */     ensureSameLength(x, y);
/* 1358 */     parallelQuickSort(x, y, 0, x.length);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static void unstableSort(int[] a, int from, int to) {
/* 1374 */     if (to - from >= 2000) {
/* 1375 */       radixSort(a, from, to);
/*      */     } else {
/* 1377 */       quickSort(a, from, to);
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
/*      */   public static void unstableSort(int[] a) {
/* 1390 */     unstableSort(a, 0, a.length);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static void unstableSort(int[] a, int from, int to, IntComparator comp) {
/* 1405 */     quickSort(a, from, to, comp);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static void unstableSort(int[] a, IntComparator comp) {
/* 1418 */     unstableSort(a, 0, a.length, comp);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static void mergeSort(int[] a, int from, int to, int[] supp) {
/* 1438 */     int len = to - from;
/*      */     
/* 1440 */     if (len < 16) {
/* 1441 */       insertionSort(a, from, to);
/*      */       return;
/*      */     } 
/* 1444 */     if (supp == null) supp = Arrays.copyOf(a, to);
/*      */     
/* 1446 */     int mid = from + to >>> 1;
/* 1447 */     mergeSort(supp, from, mid, a);
/* 1448 */     mergeSort(supp, mid, to, a);
/*      */ 
/*      */     
/* 1451 */     if (supp[mid - 1] <= supp[mid]) {
/* 1452 */       System.arraycopy(supp, from, a, from, len);
/*      */       
/*      */       return;
/*      */     } 
/* 1456 */     for (int i = from, p = from, q = mid; i < to; i++) {
/* 1457 */       if (q >= to || (p < mid && supp[p] <= supp[q])) { a[i] = supp[p++]; }
/* 1458 */       else { a[i] = supp[q++]; }
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
/*      */   public static void mergeSort(int[] a, int from, int to) {
/* 1474 */     mergeSort(a, from, to, (int[])null);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static void mergeSort(int[] a) {
/* 1487 */     mergeSort(a, 0, a.length);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static void mergeSort(int[] a, int from, int to, IntComparator comp, int[] supp) {
/* 1507 */     int len = to - from;
/*      */     
/* 1509 */     if (len < 16) {
/* 1510 */       insertionSort(a, from, to, comp);
/*      */       return;
/*      */     } 
/* 1513 */     if (supp == null) supp = Arrays.copyOf(a, to);
/*      */     
/* 1515 */     int mid = from + to >>> 1;
/* 1516 */     mergeSort(supp, from, mid, comp, a);
/* 1517 */     mergeSort(supp, mid, to, comp, a);
/*      */ 
/*      */     
/* 1520 */     if (comp.compare(supp[mid - 1], supp[mid]) <= 0) {
/* 1521 */       System.arraycopy(supp, from, a, from, len);
/*      */       
/*      */       return;
/*      */     } 
/* 1525 */     for (int i = from, p = from, q = mid; i < to; i++) {
/* 1526 */       if (q >= to || (p < mid && comp.compare(supp[p], supp[q]) <= 0)) { a[i] = supp[p++]; }
/* 1527 */       else { a[i] = supp[q++]; }
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
/*      */   public static void mergeSort(int[] a, int from, int to, IntComparator comp) {
/* 1545 */     mergeSort(a, from, to, comp, (int[])null);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static void mergeSort(int[] a, IntComparator comp) {
/* 1559 */     mergeSort(a, 0, a.length, comp);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static void stableSort(int[] a, int from, int to) {
/* 1580 */     unstableSort(a, from, to);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static void stableSort(int[] a) {
/* 1597 */     stableSort(a, 0, a.length);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static void stableSort(int[] a, int from, int to, IntComparator comp) {
/* 1617 */     mergeSort(a, from, to, comp);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static void stableSort(int[] a, IntComparator comp) {
/* 1635 */     stableSort(a, 0, a.length, comp);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static int binarySearch(int[] a, int from, int to, int key) {
/* 1659 */     to--;
/* 1660 */     while (from <= to) {
/* 1661 */       int mid = from + to >>> 1;
/* 1662 */       int midVal = a[mid];
/* 1663 */       if (midVal < key) { from = mid + 1; continue; }
/* 1664 */        if (midVal > key) { to = mid - 1; continue; }
/* 1665 */        return mid;
/*      */     } 
/* 1667 */     return -(from + 1);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static int binarySearch(int[] a, int key) {
/* 1687 */     return binarySearch(a, 0, a.length, key);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static int binarySearch(int[] a, int from, int to, int key, IntComparator c) {
/* 1711 */     to--;
/* 1712 */     while (from <= to) {
/* 1713 */       int mid = from + to >>> 1;
/* 1714 */       int midVal = a[mid];
/* 1715 */       int cmp = c.compare(midVal, key);
/* 1716 */       if (cmp < 0) { from = mid + 1; continue; }
/* 1717 */        if (cmp > 0) { to = mid - 1; continue; }
/* 1718 */        return mid;
/*      */     } 
/* 1720 */     return -(from + 1);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static int binarySearch(int[] a, int key, IntComparator c) {
/* 1741 */     return binarySearch(a, 0, a.length, key, c);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static void radixSort(int[] a) {
/* 1775 */     radixSort(a, 0, a.length);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static void radixSort(int[] a, int from, int to) {
/* 1794 */     if (to - from < 1024) {
/* 1795 */       quickSort(a, from, to);
/*      */       return;
/*      */     } 
/* 1798 */     int maxLevel = 3;
/* 1799 */     int stackSize = 766;
/* 1800 */     int stackPos = 0;
/* 1801 */     int[] offsetStack = new int[766];
/* 1802 */     int[] lengthStack = new int[766];
/* 1803 */     int[] levelStack = new int[766];
/* 1804 */     offsetStack[stackPos] = from;
/* 1805 */     lengthStack[stackPos] = to - from;
/* 1806 */     levelStack[stackPos++] = 0;
/* 1807 */     int[] count = new int[256];
/* 1808 */     int[] pos = new int[256];
/* 1809 */     while (stackPos > 0) {
/* 1810 */       int first = offsetStack[--stackPos];
/* 1811 */       int length = lengthStack[stackPos];
/* 1812 */       int level = levelStack[stackPos];
/* 1813 */       int signMask = (level % 4 == 0) ? 128 : 0;
/* 1814 */       int shift = (3 - level % 4) * 8;
/*      */ 
/*      */ 
/*      */ 
/*      */       
/* 1819 */       for (int i = first + length; i-- != first; count[a[i] >>> shift & 0xFF ^ signMask] = count[a[i] >>> shift & 0xFF ^ signMask] + 1);
/*      */       
/* 1821 */       int lastUsed = -1;
/* 1822 */       for (int j = 0, p = first; j < 256; j++) {
/* 1823 */         if (count[j] != 0) lastUsed = j; 
/* 1824 */         pos[j] = p += count[j];
/*      */       } 
/* 1826 */       int end = first + length - count[lastUsed];
/*      */       
/* 1828 */       for (int k = first, c = -1; k <= end; k += count[c], count[c] = 0) {
/* 1829 */         int t = a[k];
/* 1830 */         c = t >>> shift & 0xFF ^ signMask;
/* 1831 */         if (k < end) {
/* 1832 */           int d; for (pos[c] = pos[c] - 1; (d = pos[c] - 1) > k; ) {
/* 1833 */             int z = t;
/* 1834 */             t = a[d];
/* 1835 */             a[d] = z;
/* 1836 */             c = t >>> shift & 0xFF ^ signMask;
/*      */           } 
/* 1838 */           a[k] = t;
/*      */         } 
/* 1840 */         if (level < 3 && count[c] > 1)
/* 1841 */           if (count[c] < 1024) { quickSort(a, k, k + count[c]); }
/*      */           else
/* 1843 */           { offsetStack[stackPos] = k;
/* 1844 */             lengthStack[stackPos] = count[c];
/* 1845 */             levelStack[stackPos++] = level + 1; }
/*      */            
/*      */       } 
/*      */     } 
/*      */   }
/*      */   
/*      */   protected static final class Segment { protected final int offset;
/*      */     protected final int length;
/*      */     protected final int level;
/*      */     
/*      */     protected Segment(int offset, int length, int level) {
/* 1856 */       this.offset = offset;
/* 1857 */       this.length = length;
/* 1858 */       this.level = level;
/*      */     }
/*      */ 
/*      */     
/*      */     public String toString() {
/* 1863 */       return "Segment [offset=" + this.offset + ", length=" + this.length + ", level=" + this.level + "]";
/*      */     } }
/*      */ 
/*      */   
/* 1867 */   protected static final Segment POISON_PILL = new Segment(-1, -1, -1);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static void parallelRadixSort(int[] a, int from, int to) {
/* 1882 */     ForkJoinPool pool = getPool();
/* 1883 */     if (to - from < 1024 || pool.getParallelism() == 1) {
/* 1884 */       quickSort(a, from, to);
/*      */       return;
/*      */     } 
/* 1887 */     int maxLevel = 3;
/* 1888 */     LinkedBlockingQueue<Segment> queue = new LinkedBlockingQueue<>();
/* 1889 */     queue.add(new Segment(from, to - from, 0));
/* 1890 */     AtomicInteger queueSize = new AtomicInteger(1);
/* 1891 */     int numberOfThreads = pool.getParallelism();
/* 1892 */     ExecutorCompletionService<Void> executorCompletionService = new ExecutorCompletionService<>(pool);
/* 1893 */     for (int j = numberOfThreads; j-- != 0; executorCompletionService.submit(() -> {
/*      */           int[] count = new int[256]; int[] pos = new int[256]; while (true) {
/*      */             if (queueSize.get() == 0) {
/*      */               int m = numberOfThreads; while (m-- != 0)
/*      */                 queue.add(POISON_PILL); 
/*      */             }  Segment segment = queue.take(); if (segment == POISON_PILL)
/*      */               return null;  int first = segment.offset; int length = segment.length; int level = segment.level; int signMask = (level % 4 == 0) ? 128 : 0; int shift = (3 - level % 4) * 8;
/*      */             int i = first + length;
/*      */             while (i-- != first)
/*      */               count[a[i] >>> shift & 0xFF ^ signMask] = count[a[i] >>> shift & 0xFF ^ signMask] + 1; 
/*      */             int lastUsed = -1;
/*      */             int j = 0;
/*      */             int p = first;
/*      */             while (j < 256) {
/*      */               if (count[j] != 0)
/*      */                 lastUsed = j; 
/*      */               pos[j] = p += count[j];
/*      */               j++;
/*      */             } 
/*      */             int end = first + length - count[lastUsed];
/*      */             int k = first;
/*      */             int c = -1;
/*      */             while (k <= end) {
/*      */               int t = a[k];
/*      */               c = t >>> shift & 0xFF ^ signMask;
/*      */               if (k < end) {
/*      */                 pos[c] = pos[c] - 1;
/*      */                 int d;
/*      */                 while ((d = pos[c] - 1) > k) {
/*      */                   int z = t;
/*      */                   t = a[d];
/*      */                   a[d] = z;
/*      */                   c = t >>> shift & 0xFF ^ signMask;
/*      */                 } 
/*      */                 a[k] = t;
/*      */               } 
/*      */               if (level < 3 && count[c] > 1)
/*      */                 if (count[c] < 1024) {
/*      */                   quickSort(a, k, k + count[c]);
/*      */                 } else {
/*      */                   queueSize.incrementAndGet();
/*      */                   queue.add(new Segment(k, count[c], level + 1));
/*      */                 }  
/*      */               k += count[c];
/*      */               count[c] = 0;
/*      */             } 
/*      */             queueSize.decrementAndGet();
/*      */           } 
/*      */         }));
/* 1942 */     Throwable problem = null;
/* 1943 */     for (int i = numberOfThreads; i-- != 0;) { try {
/* 1944 */         executorCompletionService.take().get();
/* 1945 */       } catch (Exception e) {
/* 1946 */         problem = e.getCause();
/*      */       }  }
/* 1948 */      if (problem != null) throw (problem instanceof RuntimeException) ? (RuntimeException)problem : new RuntimeException(problem);
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
/*      */   public static void parallelRadixSort(int[] a) {
/* 1962 */     parallelRadixSort(a, 0, a.length);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static void radixSortIndirect(int[] perm, int[] a, boolean stable) {
/* 1986 */     radixSortIndirect(perm, a, 0, perm.length, stable);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static void radixSortIndirect(int[] perm, int[] a, int from, int to, boolean stable) {
/* 2012 */     if (to - from < 1024) {
/* 2013 */       quickSortIndirect(perm, a, from, to);
/* 2014 */       if (stable) stabilize(perm, a, from, to); 
/*      */       return;
/*      */     } 
/* 2017 */     int maxLevel = 3;
/* 2018 */     int stackSize = 766;
/* 2019 */     int stackPos = 0;
/* 2020 */     int[] offsetStack = new int[766];
/* 2021 */     int[] lengthStack = new int[766];
/* 2022 */     int[] levelStack = new int[766];
/* 2023 */     offsetStack[stackPos] = from;
/* 2024 */     lengthStack[stackPos] = to - from;
/* 2025 */     levelStack[stackPos++] = 0;
/* 2026 */     int[] count = new int[256];
/* 2027 */     int[] pos = new int[256];
/* 2028 */     int[] support = stable ? new int[perm.length] : null;
/* 2029 */     while (stackPos > 0) {
/* 2030 */       int first = offsetStack[--stackPos];
/* 2031 */       int length = lengthStack[stackPos];
/* 2032 */       int level = levelStack[stackPos];
/* 2033 */       int signMask = (level % 4 == 0) ? 128 : 0;
/* 2034 */       int shift = (3 - level % 4) * 8;
/*      */ 
/*      */ 
/*      */ 
/*      */       
/* 2039 */       for (int i = first + length; i-- != first; count[a[perm[i]] >>> shift & 0xFF ^ signMask] = count[a[perm[i]] >>> shift & 0xFF ^ signMask] + 1);
/*      */       
/* 2041 */       int lastUsed = -1; int j, p;
/* 2042 */       for (j = 0, p = stable ? 0 : first; j < 256; j++) {
/* 2043 */         if (count[j] != 0) lastUsed = j; 
/* 2044 */         pos[j] = p += count[j];
/*      */       } 
/* 2046 */       if (stable) {
/* 2047 */         for (j = first + length; j-- != first; ) { pos[a[perm[j]] >>> shift & 0xFF ^ signMask] = pos[a[perm[j]] >>> shift & 0xFF ^ signMask] - 1; support[pos[a[perm[j]] >>> shift & 0xFF ^ signMask] - 1] = perm[j]; }
/* 2048 */          System.arraycopy(support, 0, perm, first, length);
/* 2049 */         for (j = 0, p = first; j <= lastUsed; j++) {
/* 2050 */           if (level < 3 && count[j] > 1) {
/* 2051 */             if (count[j] < 1024) {
/* 2052 */               quickSortIndirect(perm, a, p, p + count[j]);
/* 2053 */               if (stable) stabilize(perm, a, p, p + count[j]); 
/*      */             } else {
/* 2055 */               offsetStack[stackPos] = p;
/* 2056 */               lengthStack[stackPos] = count[j];
/* 2057 */               levelStack[stackPos++] = level + 1;
/*      */             } 
/*      */           }
/* 2060 */           p += count[j];
/*      */         } 
/* 2062 */         Arrays.fill(count, 0); continue;
/*      */       } 
/* 2064 */       int end = first + length - count[lastUsed];
/*      */       
/* 2066 */       for (int k = first, c = -1; k <= end; k += count[c], count[c] = 0) {
/* 2067 */         int t = perm[k];
/* 2068 */         c = a[t] >>> shift & 0xFF ^ signMask;
/* 2069 */         if (k < end) {
/* 2070 */           int d; for (pos[c] = pos[c] - 1; (d = pos[c] - 1) > k; ) {
/* 2071 */             int z = t;
/* 2072 */             t = perm[d];
/* 2073 */             perm[d] = z;
/* 2074 */             c = a[t] >>> shift & 0xFF ^ signMask;
/*      */           } 
/* 2076 */           perm[k] = t;
/*      */         } 
/* 2078 */         if (level < 3 && count[c] > 1) {
/* 2079 */           if (count[c] < 1024) {
/* 2080 */             quickSortIndirect(perm, a, k, k + count[c]);
/* 2081 */             if (stable) stabilize(perm, a, k, k + count[c]); 
/*      */           } else {
/* 2083 */             offsetStack[stackPos] = k;
/* 2084 */             lengthStack[stackPos] = count[c];
/* 2085 */             levelStack[stackPos++] = level + 1;
/*      */           } 
/*      */         }
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
/*      */   public static void parallelRadixSortIndirect(int[] perm, int[] a, int from, int to, boolean stable) {
/* 2113 */     ForkJoinPool pool = getPool();
/* 2114 */     if (to - from < 1024 || pool.getParallelism() == 1) {
/* 2115 */       radixSortIndirect(perm, a, from, to, stable);
/*      */       return;
/*      */     } 
/* 2118 */     int maxLevel = 3;
/* 2119 */     LinkedBlockingQueue<Segment> queue = new LinkedBlockingQueue<>();
/* 2120 */     queue.add(new Segment(from, to - from, 0));
/* 2121 */     AtomicInteger queueSize = new AtomicInteger(1);
/* 2122 */     int numberOfThreads = pool.getParallelism();
/* 2123 */     ExecutorCompletionService<Void> executorCompletionService = new ExecutorCompletionService<>(pool);
/* 2124 */     int[] support = stable ? new int[perm.length] : null;
/* 2125 */     for (int j = numberOfThreads; j-- != 0; executorCompletionService.submit(() -> {
/*      */           int[] count = new int[256]; int[] pos = new int[256]; while (true) {
/*      */             if (queueSize.get() == 0) {
/*      */               int k = numberOfThreads; while (k-- != 0)
/*      */                 queue.add(POISON_PILL); 
/*      */             }  Segment segment = queue.take(); if (segment == POISON_PILL)
/*      */               return null;  int first = segment.offset; int length = segment.length; int level = segment.level; int signMask = (level % 4 == 0) ? 128 : 0; int shift = (3 - level % 4) * 8; int i = first + length; while (i-- != first)
/*      */               count[a[perm[i]] >>> shift & 0xFF ^ signMask] = count[a[perm[i]] >>> shift & 0xFF ^ signMask] + 1;  int lastUsed = -1; int j = 0; int p = first; while (j < 256) {
/*      */               if (count[j] != 0)
/*      */                 lastUsed = j;  pos[j] = p += count[j];
/*      */               j++;
/*      */             } 
/*      */             if (stable) {
/*      */               j = first + length;
/*      */               while (j-- != first) {
/*      */                 pos[a[perm[j]] >>> shift & 0xFF ^ signMask] = pos[a[perm[j]] >>> shift & 0xFF ^ signMask] - 1;
/*      */                 support[pos[a[perm[j]] >>> shift & 0xFF ^ signMask] - 1] = perm[j];
/*      */               } 
/*      */               System.arraycopy(support, first, perm, first, length);
/*      */               j = 0;
/*      */               p = first;
/*      */               while (j <= lastUsed) {
/*      */                 if (level < 3 && count[j] > 1)
/*      */                   if (count[j] < 1024) {
/*      */                     radixSortIndirect(perm, a, p, p + count[j], stable);
/*      */                   } else {
/*      */                     queueSize.incrementAndGet();
/*      */                     queue.add(new Segment(p, count[j], level + 1));
/*      */                   }  
/*      */                 p += count[j];
/*      */                 j++;
/*      */               } 
/*      */               Arrays.fill(count, 0);
/*      */             } else {
/*      */               int end = first + length - count[lastUsed];
/*      */               int k = first;
/*      */               int c = -1;
/*      */               while (k <= end) {
/*      */                 int t = perm[k];
/*      */                 c = a[t] >>> shift & 0xFF ^ signMask;
/*      */                 if (k < end) {
/*      */                   pos[c] = pos[c] - 1;
/*      */                   int d;
/*      */                   while ((d = pos[c] - 1) > k) {
/*      */                     int z = t;
/*      */                     t = perm[d];
/*      */                     perm[d] = z;
/*      */                     c = a[t] >>> shift & 0xFF ^ signMask;
/*      */                   } 
/*      */                   perm[k] = t;
/*      */                 } 
/*      */                 if (level < 3 && count[c] > 1)
/*      */                   if (count[c] < 1024) {
/*      */                     radixSortIndirect(perm, a, k, k + count[c], stable);
/*      */                   } else {
/*      */                     queueSize.incrementAndGet();
/*      */                     queue.add(new Segment(k, count[c], level + 1));
/*      */                   }  
/*      */                 k += count[c];
/*      */                 count[c] = 0;
/*      */               } 
/*      */             } 
/*      */             queueSize.decrementAndGet();
/*      */           } 
/*      */         }));
/* 2190 */     Throwable problem = null;
/* 2191 */     for (int i = numberOfThreads; i-- != 0;) { try {
/* 2192 */         executorCompletionService.take().get();
/* 2193 */       } catch (Exception e) {
/* 2194 */         problem = e.getCause();
/*      */       }  }
/* 2196 */      if (problem != null) throw (problem instanceof RuntimeException) ? (RuntimeException)problem : new RuntimeException(problem);
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
/*      */   public static void parallelRadixSortIndirect(int[] perm, int[] a, boolean stable) {
/* 2217 */     parallelRadixSortIndirect(perm, a, 0, a.length, stable);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static void radixSort(int[] a, int[] b) {
/* 2237 */     ensureSameLength(a, b);
/* 2238 */     radixSort(a, b, 0, a.length);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static void radixSort(int[] a, int[] b, int from, int to) {
/* 2261 */     if (to - from < 1024) {
/* 2262 */       quickSort(a, b, from, to);
/*      */       return;
/*      */     } 
/* 2265 */     int layers = 2;
/* 2266 */     int maxLevel = 7;
/* 2267 */     int stackSize = 1786;
/* 2268 */     int stackPos = 0;
/* 2269 */     int[] offsetStack = new int[1786];
/* 2270 */     int[] lengthStack = new int[1786];
/* 2271 */     int[] levelStack = new int[1786];
/* 2272 */     offsetStack[stackPos] = from;
/* 2273 */     lengthStack[stackPos] = to - from;
/* 2274 */     levelStack[stackPos++] = 0;
/* 2275 */     int[] count = new int[256];
/* 2276 */     int[] pos = new int[256];
/* 2277 */     while (stackPos > 0) {
/* 2278 */       int first = offsetStack[--stackPos];
/* 2279 */       int length = lengthStack[stackPos];
/* 2280 */       int level = levelStack[stackPos];
/* 2281 */       int signMask = (level % 4 == 0) ? 128 : 0;
/* 2282 */       int[] k = (level < 4) ? a : b;
/* 2283 */       int shift = (3 - level % 4) * 8;
/*      */ 
/*      */ 
/*      */ 
/*      */       
/* 2288 */       for (int i = first + length; i-- != first; count[k[i] >>> shift & 0xFF ^ signMask] = count[k[i] >>> shift & 0xFF ^ signMask] + 1);
/*      */       
/* 2290 */       int lastUsed = -1;
/* 2291 */       for (int j = 0, p = first; j < 256; j++) {
/* 2292 */         if (count[j] != 0) lastUsed = j; 
/* 2293 */         pos[j] = p += count[j];
/*      */       } 
/* 2295 */       int end = first + length - count[lastUsed];
/*      */       
/* 2297 */       for (int m = first, c = -1; m <= end; m += count[c], count[c] = 0) {
/* 2298 */         int t = a[m];
/* 2299 */         int u = b[m];
/* 2300 */         c = k[m] >>> shift & 0xFF ^ signMask;
/* 2301 */         if (m < end) {
/* 2302 */           int d; for (pos[c] = pos[c] - 1; (d = pos[c] - 1) > m; ) {
/* 2303 */             c = k[d] >>> shift & 0xFF ^ signMask;
/* 2304 */             int z = t;
/* 2305 */             t = a[d];
/* 2306 */             a[d] = z;
/* 2307 */             z = u;
/* 2308 */             u = b[d];
/* 2309 */             b[d] = z;
/*      */           } 
/* 2311 */           a[m] = t;
/* 2312 */           b[m] = u;
/*      */         } 
/* 2314 */         if (level < 7 && count[c] > 1) {
/* 2315 */           if (count[c] < 1024) { quickSort(a, b, m, m + count[c]); }
/*      */           else
/* 2317 */           { offsetStack[stackPos] = m;
/* 2318 */             lengthStack[stackPos] = count[c];
/* 2319 */             levelStack[stackPos++] = level + 1; }
/*      */         
/*      */         }
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
/*      */   public static void parallelRadixSort(int[] a, int[] b, int from, int to) {
/* 2346 */     ForkJoinPool pool = getPool();
/* 2347 */     if (to - from < 1024 || pool.getParallelism() == 1) {
/* 2348 */       quickSort(a, b, from, to);
/*      */       return;
/*      */     } 
/* 2351 */     int layers = 2;
/* 2352 */     if (a.length != b.length) throw new IllegalArgumentException("Array size mismatch."); 
/* 2353 */     int maxLevel = 7;
/* 2354 */     LinkedBlockingQueue<Segment> queue = new LinkedBlockingQueue<>();
/* 2355 */     queue.add(new Segment(from, to - from, 0));
/* 2356 */     AtomicInteger queueSize = new AtomicInteger(1);
/* 2357 */     int numberOfThreads = pool.getParallelism();
/* 2358 */     ExecutorCompletionService<Void> executorCompletionService = new ExecutorCompletionService<>(pool);
/* 2359 */     for (int j = numberOfThreads; j-- != 0; executorCompletionService.submit(() -> {
/*      */           int[] count = new int[256]; int[] pos = new int[256]; while (true) {
/*      */             if (queueSize.get() == 0) {
/*      */               int n = numberOfThreads; while (n-- != 0)
/*      */                 queue.add(POISON_PILL); 
/*      */             }  Segment segment = queue.take(); if (segment == POISON_PILL)
/*      */               return null;  int first = segment.offset; int length = segment.length; int level = segment.level; int signMask = (level % 4 == 0) ? 128 : 0; int[] k = (level < 4) ? a : b; int shift = (3 - level % 4) * 8; int i = first + length; while (i-- != first)
/*      */               count[k[i] >>> shift & 0xFF ^ signMask] = count[k[i] >>> shift & 0xFF ^ signMask] + 1;  int lastUsed = -1; int j = 0;
/*      */             int p = first;
/*      */             while (j < 256) {
/*      */               if (count[j] != 0)
/*      */                 lastUsed = j; 
/*      */               pos[j] = p += count[j];
/*      */               j++;
/*      */             } 
/*      */             int end = first + length - count[lastUsed];
/*      */             int m = first;
/*      */             int c = -1;
/*      */             while (m <= end) {
/*      */               int t = a[m];
/*      */               int u = b[m];
/*      */               c = k[m] >>> shift & 0xFF ^ signMask;
/*      */               if (m < end) {
/*      */                 pos[c] = pos[c] - 1;
/*      */                 int d;
/*      */                 while ((d = pos[c] - 1) > m) {
/*      */                   c = k[d] >>> shift & 0xFF ^ signMask;
/*      */                   int z = t;
/*      */                   int w = u;
/*      */                   t = a[d];
/*      */                   u = b[d];
/*      */                   a[d] = z;
/*      */                   b[d] = w;
/*      */                 } 
/*      */                 a[m] = t;
/*      */                 b[m] = u;
/*      */               } 
/*      */               if (level < 7 && count[c] > 1)
/*      */                 if (count[c] < 1024) {
/*      */                   quickSort(a, b, m, m + count[c]);
/*      */                 } else {
/*      */                   queueSize.incrementAndGet();
/*      */                   queue.add(new Segment(m, count[c], level + 1));
/*      */                 }  
/*      */               m += count[c];
/*      */               count[c] = 0;
/*      */             } 
/*      */             queueSize.decrementAndGet();
/*      */           } 
/*      */         }));
/* 2409 */     Throwable problem = null;
/* 2410 */     for (int i = numberOfThreads; i-- != 0;) { try {
/* 2411 */         executorCompletionService.take().get();
/* 2412 */       } catch (Exception e) {
/* 2413 */         problem = e.getCause();
/*      */       }  }
/* 2415 */      if (problem != null) throw (problem instanceof RuntimeException) ? (RuntimeException)problem : new RuntimeException(problem);
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
/*      */   public static void parallelRadixSort(int[] a, int[] b) {
/* 2436 */     ensureSameLength(a, b);
/* 2437 */     parallelRadixSort(a, b, 0, a.length);
/*      */   }
/*      */   
/*      */   private static void insertionSortIndirect(int[] perm, int[] a, int[] b, int from, int to) {
/* 2441 */     for (int i = from; ++i < to; ) {
/* 2442 */       int t = perm[i];
/* 2443 */       int j = i; int u;
/* 2444 */       for (u = perm[j - 1]; a[t] < a[u] || (a[t] == a[u] && b[t] < b[u]); u = perm[--j - 1]) {
/* 2445 */         perm[j] = u;
/* 2446 */         if (from == j - 1) {
/* 2447 */           j--;
/*      */           break;
/*      */         } 
/*      */       } 
/* 2451 */       perm[j] = t;
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
/*      */   public static void radixSortIndirect(int[] perm, int[] a, int[] b, boolean stable) {
/* 2478 */     ensureSameLength(a, b);
/* 2479 */     radixSortIndirect(perm, a, b, 0, a.length, stable);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static void radixSortIndirect(int[] perm, int[] a, int[] b, int from, int to, boolean stable) {
/* 2507 */     if (to - from < 64) {
/* 2508 */       insertionSortIndirect(perm, a, b, from, to);
/*      */       return;
/*      */     } 
/* 2511 */     int layers = 2;
/* 2512 */     int maxLevel = 7;
/* 2513 */     int stackSize = 1786;
/* 2514 */     int stackPos = 0;
/* 2515 */     int[] offsetStack = new int[1786];
/* 2516 */     int[] lengthStack = new int[1786];
/* 2517 */     int[] levelStack = new int[1786];
/* 2518 */     offsetStack[stackPos] = from;
/* 2519 */     lengthStack[stackPos] = to - from;
/* 2520 */     levelStack[stackPos++] = 0;
/* 2521 */     int[] count = new int[256];
/* 2522 */     int[] pos = new int[256];
/* 2523 */     int[] support = stable ? new int[perm.length] : null;
/* 2524 */     while (stackPos > 0) {
/* 2525 */       int first = offsetStack[--stackPos];
/* 2526 */       int length = lengthStack[stackPos];
/* 2527 */       int level = levelStack[stackPos];
/* 2528 */       int signMask = (level % 4 == 0) ? 128 : 0;
/* 2529 */       int[] k = (level < 4) ? a : b;
/* 2530 */       int shift = (3 - level % 4) * 8;
/*      */ 
/*      */ 
/*      */ 
/*      */       
/* 2535 */       for (int i = first + length; i-- != first; count[k[perm[i]] >>> shift & 0xFF ^ signMask] = count[k[perm[i]] >>> shift & 0xFF ^ signMask] + 1);
/*      */       
/* 2537 */       int lastUsed = -1; int j, p;
/* 2538 */       for (j = 0, p = stable ? 0 : first; j < 256; j++) {
/* 2539 */         if (count[j] != 0) lastUsed = j; 
/* 2540 */         pos[j] = p += count[j];
/*      */       } 
/* 2542 */       if (stable) {
/* 2543 */         for (j = first + length; j-- != first; ) { pos[k[perm[j]] >>> shift & 0xFF ^ signMask] = pos[k[perm[j]] >>> shift & 0xFF ^ signMask] - 1; support[pos[k[perm[j]] >>> shift & 0xFF ^ signMask] - 1] = perm[j]; }
/* 2544 */          System.arraycopy(support, 0, perm, first, length);
/* 2545 */         for (j = 0, p = first; j < 256; j++) {
/* 2546 */           if (level < 7 && count[j] > 1) {
/* 2547 */             if (count[j] < 64) { insertionSortIndirect(perm, a, b, p, p + count[j]); }
/*      */             else
/* 2549 */             { offsetStack[stackPos] = p;
/* 2550 */               lengthStack[stackPos] = count[j];
/* 2551 */               levelStack[stackPos++] = level + 1; }
/*      */           
/*      */           }
/* 2554 */           p += count[j];
/*      */         } 
/* 2556 */         Arrays.fill(count, 0); continue;
/*      */       } 
/* 2558 */       int end = first + length - count[lastUsed];
/*      */       
/* 2560 */       for (int m = first, c = -1; m <= end; m += count[c], count[c] = 0) {
/* 2561 */         int t = perm[m];
/* 2562 */         c = k[t] >>> shift & 0xFF ^ signMask;
/* 2563 */         if (m < end) {
/* 2564 */           int d; for (pos[c] = pos[c] - 1; (d = pos[c] - 1) > m; ) {
/* 2565 */             int z = t;
/* 2566 */             t = perm[d];
/* 2567 */             perm[d] = z;
/* 2568 */             c = k[t] >>> shift & 0xFF ^ signMask;
/*      */           } 
/* 2570 */           perm[m] = t;
/*      */         } 
/* 2572 */         if (level < 7 && count[c] > 1) {
/* 2573 */           if (count[c] < 64) { insertionSortIndirect(perm, a, b, m, m + count[c]); }
/*      */           else
/* 2575 */           { offsetStack[stackPos] = m;
/* 2576 */             lengthStack[stackPos] = count[c];
/* 2577 */             levelStack[stackPos++] = level + 1; }
/*      */         
/*      */         }
/*      */       } 
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   private static void selectionSort(int[][] a, int from, int to, int level) {
/* 2586 */     int layers = a.length;
/* 2587 */     int firstLayer = level / 4;
/* 2588 */     for (int i = from; i < to - 1; i++) {
/* 2589 */       int m = i;
/* 2590 */       for (int j = i + 1; j < to; j++) {
/* 2591 */         for (int p = firstLayer; p < layers; p++) {
/* 2592 */           if (a[p][j] < a[p][m]) {
/* 2593 */             m = j; break;
/*      */           } 
/* 2595 */           if (a[p][j] > a[p][m])
/*      */             break; 
/*      */         } 
/* 2598 */       }  if (m != i) {
/* 2599 */         for (int p = layers; p-- != 0; ) {
/* 2600 */           int u = a[p][i];
/* 2601 */           a[p][i] = a[p][m];
/* 2602 */           a[p][m] = u;
/*      */         } 
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
/*      */   public static void radixSort(int[][] a) {
/* 2623 */     radixSort(a, 0, (a[0]).length);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static void radixSort(int[][] a, int from, int to) {
/* 2643 */     if (to - from < 64) {
/* 2644 */       selectionSort(a, from, to, 0);
/*      */       return;
/*      */     } 
/* 2647 */     int layers = a.length;
/* 2648 */     int maxLevel = 4 * layers - 1;
/* 2649 */     int p = layers;
/* 2650 */     for (int l = (a[0]).length; p-- != 0;) { if ((a[p]).length != l) throw new IllegalArgumentException("The array of index " + p + " has not the same length of the array of index 0.");  }
/* 2651 */      int stackSize = 255 * (layers * 4 - 1) + 1;
/* 2652 */     int stackPos = 0;
/* 2653 */     int[] offsetStack = new int[stackSize];
/* 2654 */     int[] lengthStack = new int[stackSize];
/* 2655 */     int[] levelStack = new int[stackSize];
/* 2656 */     offsetStack[stackPos] = from;
/* 2657 */     lengthStack[stackPos] = to - from;
/* 2658 */     levelStack[stackPos++] = 0;
/* 2659 */     int[] count = new int[256];
/* 2660 */     int[] pos = new int[256];
/* 2661 */     int[] t = new int[layers];
/* 2662 */     while (stackPos > 0) {
/* 2663 */       int first = offsetStack[--stackPos];
/* 2664 */       int length = lengthStack[stackPos];
/* 2665 */       int level = levelStack[stackPos];
/* 2666 */       int signMask = (level % 4 == 0) ? 128 : 0;
/* 2667 */       int[] k = a[level / 4];
/* 2668 */       int shift = (3 - level % 4) * 8;
/*      */ 
/*      */ 
/*      */ 
/*      */       
/* 2673 */       for (int i = first + length; i-- != first; count[k[i] >>> shift & 0xFF ^ signMask] = count[k[i] >>> shift & 0xFF ^ signMask] + 1);
/*      */       
/* 2675 */       int lastUsed = -1;
/* 2676 */       for (int j = 0, n = first; j < 256; j++) {
/* 2677 */         if (count[j] != 0) lastUsed = j; 
/* 2678 */         pos[j] = n += count[j];
/*      */       } 
/* 2680 */       int end = first + length - count[lastUsed];
/*      */       
/* 2682 */       for (int m = first, c = -1; m <= end; m += count[c], count[c] = 0) {
/* 2683 */         int i1; for (i1 = layers; i1-- != 0; t[i1] = a[i1][m]);
/* 2684 */         c = k[m] >>> shift & 0xFF ^ signMask;
/* 2685 */         if (m < end) {
/* 2686 */           int d; for (pos[c] = pos[c] - 1; (d = pos[c] - 1) > m; ) {
/* 2687 */             c = k[d] >>> shift & 0xFF ^ signMask;
/* 2688 */             for (i1 = layers; i1-- != 0; ) {
/* 2689 */               int u = t[i1];
/* 2690 */               t[i1] = a[i1][d];
/* 2691 */               a[i1][d] = u;
/*      */             } 
/*      */           } 
/* 2694 */           for (i1 = layers; i1-- != 0; a[i1][m] = t[i1]);
/*      */         } 
/* 2696 */         if (level < maxLevel && count[c] > 1) {
/* 2697 */           if (count[c] < 64) { selectionSort(a, m, m + count[c], level + 1); }
/*      */           else
/* 2699 */           { offsetStack[stackPos] = m;
/* 2700 */             lengthStack[stackPos] = count[c];
/* 2701 */             levelStack[stackPos++] = level + 1; }
/*      */         
/*      */         }
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
/*      */   public static int[] shuffle(int[] a, int from, int to, Random random) {
/* 2718 */     for (int i = to - from; i-- != 0; ) {
/* 2719 */       int p = random.nextInt(i + 1);
/* 2720 */       int t = a[from + i];
/* 2721 */       a[from + i] = a[from + p];
/* 2722 */       a[from + p] = t;
/*      */     } 
/* 2724 */     return a;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static int[] shuffle(int[] a, Random random) {
/* 2735 */     for (int i = a.length; i-- != 0; ) {
/* 2736 */       int p = random.nextInt(i + 1);
/* 2737 */       int t = a[i];
/* 2738 */       a[i] = a[p];
/* 2739 */       a[p] = t;
/*      */     } 
/* 2741 */     return a;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static int[] reverse(int[] a) {
/* 2751 */     int length = a.length;
/* 2752 */     for (int i = length / 2; i-- != 0; ) {
/* 2753 */       int t = a[length - i - 1];
/* 2754 */       a[length - i - 1] = a[i];
/* 2755 */       a[i] = t;
/*      */     } 
/* 2757 */     return a;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static int[] reverse(int[] a, int from, int to) {
/* 2769 */     int length = to - from;
/* 2770 */     for (int i = length / 2; i-- != 0; ) {
/* 2771 */       int t = a[from + length - i - 1];
/* 2772 */       a[from + length - i - 1] = a[from + i];
/* 2773 */       a[from + i] = t;
/*      */     } 
/* 2775 */     return a;
/*      */   }
/*      */   
/*      */   private static final class ArrayHashStrategy implements Hash.Strategy<int[]>, Serializable {
/*      */     private static final long serialVersionUID = -7046029254386353129L;
/*      */     
/*      */     private ArrayHashStrategy() {}
/*      */     
/*      */     public int hashCode(int[] o) {
/* 2784 */       return Arrays.hashCode(o);
/*      */     }
/*      */ 
/*      */     
/*      */     public boolean equals(int[] a, int[] b) {
/* 2789 */       return Arrays.equals(a, b);
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
/* 2801 */   public static final Hash.Strategy<int[]> HASH_STRATEGY = new ArrayHashStrategy();
/*      */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\i\\unimi\dsi\fastutil\ints\IntArrays.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */