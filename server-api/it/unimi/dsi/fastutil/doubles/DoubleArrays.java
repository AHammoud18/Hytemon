/*      */ package it.unimi.dsi.fastutil.doubles;
/*      */ 
/*      */ import it.unimi.dsi.fastutil.Arrays;
/*      */ import it.unimi.dsi.fastutil.Hash;
/*      */ import it.unimi.dsi.fastutil.ints.IntArrays;
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
/*      */ public final class DoubleArrays
/*      */ {
/*  105 */   public static final double[] EMPTY_ARRAY = new double[0];
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*  113 */   public static final double[] DEFAULT_EMPTY_ARRAY = new double[0]; private static final int QUICKSORT_NO_REC = 16;
/*      */   private static final int PARALLEL_QUICKSORT_NO_FORK = 8192;
/*      */   private static final int QUICKSORT_MEDIAN_OF_9 = 128;
/*      */   private static final int MERGESORT_NO_REC = 16;
/*      */   private static final int DIGIT_BITS = 8;
/*      */   private static final int DIGIT_MASK = 255;
/*      */   private static final int DIGITS_PER_ELEMENT = 8;
/*      */   private static final int RADIXSORT_NO_REC = 1024;
/*      */   private static final int RADIXSORT_NO_REC_SMALL = 64;
/*      */   private static final int PARALLEL_RADIXSORT_NO_FORK = 1024;
/*      */   static final int RADIX_SORT_MIN_THRESHOLD = 4000;
/*      */   
/*      */   public static double[] forceCapacity(double[] array, int length, int preserve) {
/*  126 */     double[] t = new double[length];
/*  127 */     System.arraycopy(array, 0, t, 0, preserve);
/*  128 */     return t;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static double[] ensureCapacity(double[] array, int length) {
/*  145 */     return ensureCapacity(array, length, array.length);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static double[] ensureCapacity(double[] array, int length, int preserve) {
/*  161 */     return (length > array.length) ? forceCapacity(array, length, preserve) : array;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static double[] grow(double[] array, int length) {
/*  179 */     return grow(array, length, array.length);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static double[] grow(double[] array, int length, int preserve) {
/*  200 */     if (length > array.length) {
/*  201 */       int newLength = (int)Math.max(Math.min(array.length + (array.length >> 1), 2147483639L), length);
/*  202 */       double[] t = new double[newLength];
/*  203 */       System.arraycopy(array, 0, t, 0, preserve);
/*  204 */       return t;
/*      */     } 
/*  206 */     return array;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static double[] trim(double[] array, int length) {
/*  220 */     if (length >= array.length) return array; 
/*  221 */     double[] t = (length == 0) ? EMPTY_ARRAY : new double[length];
/*  222 */     System.arraycopy(array, 0, t, 0, length);
/*  223 */     return t;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static double[] setLength(double[] array, int length) {
/*  239 */     if (length == array.length) return array; 
/*  240 */     if (length < array.length) return trim(array, length); 
/*  241 */     return ensureCapacity(array, length);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static double[] copy(double[] array, int offset, int length) {
/*  254 */     ensureOffsetLength(array, offset, length);
/*  255 */     double[] a = (length == 0) ? EMPTY_ARRAY : new double[length];
/*  256 */     System.arraycopy(array, offset, a, 0, length);
/*  257 */     return a;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static double[] copy(double[] array) {
/*  267 */     return (double[])array.clone();
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
/*      */   public static void fill(double[] array, double value) {
/*  279 */     int i = array.length;
/*  280 */     for (; i-- != 0; array[i] = value);
/*      */   }
/*      */ 
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
/*      */   public static void fill(double[] array, int from, int to, double value) {
/*  294 */     ensureFromTo(array, from, to);
/*  295 */     if (from == 0) { for (; to-- != 0; array[to] = value); }
/*  296 */     else { for (int i = from; i < to; ) { array[i] = value; i++; }
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
/*      */   public static boolean equals(double[] a1, double[] a2) {
/*  310 */     int i = a1.length;
/*  311 */     if (i != a2.length) return false; 
/*  312 */     while (i-- != 0) { if (Double.doubleToLongBits(a1[i]) != Double.doubleToLongBits(a2[i])) return false;  }
/*  313 */      return true;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static void ensureFromTo(double[] a, int from, int to) {
/*  335 */     Arrays.ensureFromTo(a.length, from, to);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static void ensureOffsetLength(double[] a, int offset, int length) {
/*  357 */     Arrays.ensureOffsetLength(a.length, offset, length);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static void ensureSameLength(double[] a, double[] b) {
/*  368 */     if (a.length != b.length) throw new IllegalArgumentException("Array size mismatch: " + a.length + " != " + b.length);
/*      */   
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private static ForkJoinPool getPool() {
/*  378 */     ForkJoinPool current = ForkJoinTask.getPool();
/*  379 */     return (current == null) ? ForkJoinPool.commonPool() : current;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static void swap(double[] x, int a, int b) {
/*  390 */     double t = x[a];
/*  391 */     x[a] = x[b];
/*  392 */     x[b] = t;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static void swap(double[] x, int a, int b, int n) {
/*  404 */     for (int i = 0; i < n; ) { swap(x, a, b); i++; a++; b++; }
/*      */   
/*      */   }
/*      */   private static int med3(double[] x, int a, int b, int c, DoubleComparator comp) {
/*  408 */     int ab = comp.compare(x[a], x[b]);
/*  409 */     int ac = comp.compare(x[a], x[c]);
/*  410 */     int bc = comp.compare(x[b], x[c]);
/*  411 */     return (ab < 0) ? ((bc < 0) ? b : ((ac < 0) ? c : a)) : ((bc > 0) ? b : ((ac > 0) ? c : a));
/*      */   }
/*      */   
/*      */   private static void selectionSort(double[] a, int from, int to, DoubleComparator comp) {
/*  415 */     for (int i = from; i < to - 1; i++) {
/*  416 */       int m = i;
/*  417 */       for (int j = i + 1; j < to; ) { if (comp.compare(a[j], a[m]) < 0) m = j;  j++; }
/*  418 */        if (m != i) {
/*  419 */         double u = a[i];
/*  420 */         a[i] = a[m];
/*  421 */         a[m] = u;
/*      */       } 
/*      */     } 
/*      */   }
/*      */   
/*      */   private static void insertionSort(double[] a, int from, int to, DoubleComparator comp) {
/*  427 */     for (int i = from; ++i < to; ) {
/*  428 */       double t = a[i];
/*  429 */       int j = i; double u;
/*  430 */       for (u = a[j - 1]; comp.compare(t, u) < 0; u = a[--j - 1]) {
/*  431 */         a[j] = u;
/*  432 */         if (from == j - 1) {
/*  433 */           j--;
/*      */           break;
/*      */         } 
/*      */       } 
/*  437 */       a[j] = t;
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
/*      */   public static void quickSort(double[] x, int from, int to, DoubleComparator comp) {
/*  461 */     int len = to - from;
/*      */     
/*  463 */     if (len < 16) {
/*  464 */       selectionSort(x, from, to, comp);
/*      */       
/*      */       return;
/*      */     } 
/*  468 */     int m = from + len / 2;
/*  469 */     int l = from;
/*  470 */     int n = to - 1;
/*  471 */     if (len > 128) {
/*  472 */       int i = len / 8;
/*  473 */       l = med3(x, l, l + i, l + 2 * i, comp);
/*  474 */       m = med3(x, m - i, m, m + i, comp);
/*  475 */       n = med3(x, n - 2 * i, n - i, n, comp);
/*      */     } 
/*  477 */     m = med3(x, l, m, n, comp);
/*  478 */     double v = x[m];
/*      */     
/*  480 */     int a = from, b = a, c = to - 1, d = c;
/*      */     while (true) {
/*      */       int comparison;
/*  483 */       if (b <= c && (comparison = comp.compare(x[b], v)) <= 0) {
/*  484 */         if (comparison == 0) swap(x, a++, b); 
/*  485 */         b++; continue;
/*      */       } 
/*  487 */       while (c >= b && (comparison = comp.compare(x[c], v)) >= 0) {
/*  488 */         if (comparison == 0) swap(x, c, d--); 
/*  489 */         c--;
/*      */       } 
/*  491 */       if (b > c)
/*  492 */         break;  swap(x, b++, c--);
/*      */     } 
/*      */ 
/*      */     
/*  496 */     int s = Math.min(a - from, b - a);
/*  497 */     swap(x, from, b - s, s);
/*  498 */     s = Math.min(d - c, to - d - 1);
/*  499 */     swap(x, b, to - s, s);
/*      */     
/*  501 */     if ((s = b - a) > 1) quickSort(x, from, from + s, comp); 
/*  502 */     if ((s = d - c) > 1) quickSort(x, to - s, to, comp);
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
/*      */   public static void quickSort(double[] x, DoubleComparator comp) {
/*  522 */     quickSort(x, 0, x.length, comp);
/*      */   }
/*      */   
/*      */   protected static class ForkJoinQuickSortComp extends RecursiveAction {
/*      */     private static final long serialVersionUID = 1L;
/*      */     private final int from;
/*      */     private final int to;
/*      */     private final double[] x;
/*      */     private final DoubleComparator comp;
/*      */     
/*      */     public ForkJoinQuickSortComp(double[] x, int from, int to, DoubleComparator comp) {
/*  533 */       this.from = from;
/*  534 */       this.to = to;
/*  535 */       this.x = x;
/*  536 */       this.comp = comp;
/*      */     }
/*      */ 
/*      */     
/*      */     protected void compute() {
/*  541 */       double[] x = this.x;
/*  542 */       int len = this.to - this.from;
/*  543 */       if (len < 8192) {
/*  544 */         DoubleArrays.quickSort(x, this.from, this.to, this.comp);
/*      */         
/*      */         return;
/*      */       } 
/*  548 */       int m = this.from + len / 2;
/*  549 */       int l = this.from;
/*  550 */       int n = this.to - 1;
/*  551 */       int s = len / 8;
/*  552 */       l = DoubleArrays.med3(x, l, l + s, l + 2 * s, this.comp);
/*  553 */       m = DoubleArrays.med3(x, m - s, m, m + s, this.comp);
/*  554 */       n = DoubleArrays.med3(x, n - 2 * s, n - s, n, this.comp);
/*  555 */       m = DoubleArrays.med3(x, l, m, n, this.comp);
/*  556 */       double v = x[m];
/*      */       
/*  558 */       int a = this.from, b = a, c = this.to - 1, d = c;
/*      */       while (true) {
/*      */         int comparison;
/*  561 */         if (b <= c && (comparison = this.comp.compare(x[b], v)) <= 0) {
/*  562 */           if (comparison == 0) DoubleArrays.swap(x, a++, b); 
/*  563 */           b++; continue;
/*      */         } 
/*  565 */         while (c >= b && (comparison = this.comp.compare(x[c], v)) >= 0) {
/*  566 */           if (comparison == 0) DoubleArrays.swap(x, c, d--); 
/*  567 */           c--;
/*      */         } 
/*  569 */         if (b > c)
/*  570 */           break;  DoubleArrays.swap(x, b++, c--);
/*      */       } 
/*      */ 
/*      */       
/*  574 */       s = Math.min(a - this.from, b - a);
/*  575 */       DoubleArrays.swap(x, this.from, b - s, s);
/*  576 */       s = Math.min(d - c, this.to - d - 1);
/*  577 */       DoubleArrays.swap(x, b, this.to - s, s);
/*      */       
/*  579 */       s = b - a;
/*  580 */       int t = d - c;
/*  581 */       if (s > 1 && t > 1) { invokeAll(new ForkJoinQuickSortComp(x, this.from, this.from + s, this.comp), new ForkJoinQuickSortComp(x, this.to - t, this.to, this.comp)); }
/*  582 */       else if (s > 1) { invokeAll((ForkJoinTask<?>[])new ForkJoinTask[] { new ForkJoinQuickSortComp(x, this.from, this.from + s, this.comp) }); }
/*  583 */       else { invokeAll((ForkJoinTask<?>[])new ForkJoinTask[] { new ForkJoinQuickSortComp(x, this.to - t, this.to, this.comp) }); }
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
/*      */   public static void parallelQuickSort(double[] x, int from, int to, DoubleComparator comp) {
/*  602 */     ForkJoinPool pool = getPool();
/*  603 */     if (to - from < 8192 || pool.getParallelism() == 1) { quickSort(x, from, to, comp); }
/*      */     else
/*  605 */     { pool.invoke(new ForkJoinQuickSortComp(x, from, to, comp)); }
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
/*      */   public static void parallelQuickSort(double[] x, DoubleComparator comp) {
/*  622 */     parallelQuickSort(x, 0, x.length, comp);
/*      */   }
/*      */   
/*      */   private static int med3(double[] x, int a, int b, int c) {
/*  626 */     int ab = Double.compare(x[a], x[b]);
/*  627 */     int ac = Double.compare(x[a], x[c]);
/*  628 */     int bc = Double.compare(x[b], x[c]);
/*  629 */     return (ab < 0) ? ((bc < 0) ? b : ((ac < 0) ? c : a)) : ((bc > 0) ? b : ((ac > 0) ? c : a));
/*      */   }
/*      */   
/*      */   private static void selectionSort(double[] a, int from, int to) {
/*  633 */     for (int i = from; i < to - 1; i++) {
/*  634 */       int m = i;
/*  635 */       for (int j = i + 1; j < to; ) { if (Double.compare(a[j], a[m]) < 0) m = j;  j++; }
/*  636 */        if (m != i) {
/*  637 */         double u = a[i];
/*  638 */         a[i] = a[m];
/*  639 */         a[m] = u;
/*      */       } 
/*      */     } 
/*      */   }
/*      */   
/*      */   private static void insertionSort(double[] a, int from, int to) {
/*  645 */     for (int i = from; ++i < to; ) {
/*  646 */       double t = a[i];
/*  647 */       int j = i; double u;
/*  648 */       for (u = a[j - 1]; Double.compare(t, u) < 0; u = a[--j - 1]) {
/*  649 */         a[j] = u;
/*  650 */         if (from == j - 1) {
/*  651 */           j--;
/*      */           break;
/*      */         } 
/*      */       } 
/*  655 */       a[j] = t;
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
/*      */   public static void quickSort(double[] x, int from, int to) {
/*  677 */     int len = to - from;
/*      */     
/*  679 */     if (len < 16) {
/*  680 */       selectionSort(x, from, to);
/*      */       
/*      */       return;
/*      */     } 
/*  684 */     int m = from + len / 2;
/*  685 */     int l = from;
/*  686 */     int n = to - 1;
/*  687 */     if (len > 128) {
/*  688 */       int i = len / 8;
/*  689 */       l = med3(x, l, l + i, l + 2 * i);
/*  690 */       m = med3(x, m - i, m, m + i);
/*  691 */       n = med3(x, n - 2 * i, n - i, n);
/*      */     } 
/*  693 */     m = med3(x, l, m, n);
/*  694 */     double v = x[m];
/*      */     
/*  696 */     int a = from, b = a, c = to - 1, d = c;
/*      */     while (true) {
/*      */       int comparison;
/*  699 */       if (b <= c && (comparison = Double.compare(x[b], v)) <= 0) {
/*  700 */         if (comparison == 0) swap(x, a++, b); 
/*  701 */         b++; continue;
/*      */       } 
/*  703 */       while (c >= b && (comparison = Double.compare(x[c], v)) >= 0) {
/*  704 */         if (comparison == 0) swap(x, c, d--); 
/*  705 */         c--;
/*      */       } 
/*  707 */       if (b > c)
/*  708 */         break;  swap(x, b++, c--);
/*      */     } 
/*      */ 
/*      */     
/*  712 */     int s = Math.min(a - from, b - a);
/*  713 */     swap(x, from, b - s, s);
/*  714 */     s = Math.min(d - c, to - d - 1);
/*  715 */     swap(x, b, to - s, s);
/*      */     
/*  717 */     if ((s = b - a) > 1) quickSort(x, from, from + s); 
/*  718 */     if ((s = d - c) > 1) quickSort(x, to - s, to);
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
/*      */   public static void quickSort(double[] x) {
/*  737 */     quickSort(x, 0, x.length);
/*      */   }
/*      */   
/*      */   protected static class ForkJoinQuickSort extends RecursiveAction {
/*      */     private static final long serialVersionUID = 1L;
/*      */     private final int from;
/*      */     private final int to;
/*      */     private final double[] x;
/*      */     
/*      */     public ForkJoinQuickSort(double[] x, int from, int to) {
/*  747 */       this.from = from;
/*  748 */       this.to = to;
/*  749 */       this.x = x;
/*      */     }
/*      */ 
/*      */ 
/*      */     
/*      */     protected void compute() {
/*  755 */       double[] x = this.x;
/*  756 */       int len = this.to - this.from;
/*  757 */       if (len < 8192) {
/*  758 */         DoubleArrays.quickSort(x, this.from, this.to);
/*      */         
/*      */         return;
/*      */       } 
/*  762 */       int m = this.from + len / 2;
/*  763 */       int l = this.from;
/*  764 */       int n = this.to - 1;
/*  765 */       int s = len / 8;
/*  766 */       l = DoubleArrays.med3(x, l, l + s, l + 2 * s);
/*  767 */       m = DoubleArrays.med3(x, m - s, m, m + s);
/*  768 */       n = DoubleArrays.med3(x, n - 2 * s, n - s, n);
/*  769 */       m = DoubleArrays.med3(x, l, m, n);
/*  770 */       double v = x[m];
/*      */       
/*  772 */       int a = this.from, b = a, c = this.to - 1, d = c;
/*      */       while (true) {
/*      */         int comparison;
/*  775 */         if (b <= c && (comparison = Double.compare(x[b], v)) <= 0) {
/*  776 */           if (comparison == 0) DoubleArrays.swap(x, a++, b); 
/*  777 */           b++; continue;
/*      */         } 
/*  779 */         while (c >= b && (comparison = Double.compare(x[c], v)) >= 0) {
/*  780 */           if (comparison == 0) DoubleArrays.swap(x, c, d--); 
/*  781 */           c--;
/*      */         } 
/*  783 */         if (b > c)
/*  784 */           break;  DoubleArrays.swap(x, b++, c--);
/*      */       } 
/*      */ 
/*      */       
/*  788 */       s = Math.min(a - this.from, b - a);
/*  789 */       DoubleArrays.swap(x, this.from, b - s, s);
/*  790 */       s = Math.min(d - c, this.to - d - 1);
/*  791 */       DoubleArrays.swap(x, b, this.to - s, s);
/*      */       
/*  793 */       s = b - a;
/*  794 */       int t = d - c;
/*  795 */       if (s > 1 && t > 1) { invokeAll(new ForkJoinQuickSort(x, this.from, this.from + s), new ForkJoinQuickSort(x, this.to - t, this.to)); }
/*  796 */       else if (s > 1) { invokeAll((ForkJoinTask<?>[])new ForkJoinTask[] { new ForkJoinQuickSort(x, this.from, this.from + s) }); }
/*  797 */       else { invokeAll((ForkJoinTask<?>[])new ForkJoinTask[] { new ForkJoinQuickSort(x, this.to - t, this.to) }); }
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
/*      */   public static void parallelQuickSort(double[] x, int from, int to) {
/*  815 */     ForkJoinPool pool = getPool();
/*  816 */     if (to - from < 8192 || pool.getParallelism() == 1) { quickSort(x, from, to); }
/*      */     else
/*  818 */     { pool.invoke(new ForkJoinQuickSort(x, from, to)); }
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
/*      */   public static void parallelQuickSort(double[] x) {
/*  834 */     parallelQuickSort(x, 0, x.length);
/*      */   }
/*      */   
/*      */   private static int med3Indirect(int[] perm, double[] x, int a, int b, int c) {
/*  838 */     double aa = x[perm[a]];
/*  839 */     double bb = x[perm[b]];
/*  840 */     double cc = x[perm[c]];
/*  841 */     int ab = Double.compare(aa, bb);
/*  842 */     int ac = Double.compare(aa, cc);
/*  843 */     int bc = Double.compare(bb, cc);
/*  844 */     return (ab < 0) ? ((bc < 0) ? b : ((ac < 0) ? c : a)) : ((bc > 0) ? b : ((ac > 0) ? c : a));
/*      */   }
/*      */   
/*      */   private static void insertionSortIndirect(int[] perm, double[] a, int from, int to) {
/*  848 */     for (int i = from; ++i < to; ) {
/*  849 */       int t = perm[i];
/*  850 */       int j = i; int u;
/*  851 */       for (u = perm[j - 1]; Double.compare(a[t], a[u]) < 0; u = perm[--j - 1]) {
/*  852 */         perm[j] = u;
/*  853 */         if (from == j - 1) {
/*  854 */           j--;
/*      */           break;
/*      */         } 
/*      */       } 
/*  858 */       perm[j] = t;
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
/*      */   public static void quickSortIndirect(int[] perm, double[] x, int from, int to) {
/*  887 */     int len = to - from;
/*      */     
/*  889 */     if (len < 16) {
/*  890 */       insertionSortIndirect(perm, x, from, to);
/*      */       
/*      */       return;
/*      */     } 
/*  894 */     int m = from + len / 2;
/*  895 */     int l = from;
/*  896 */     int n = to - 1;
/*  897 */     if (len > 128) {
/*  898 */       int i = len / 8;
/*  899 */       l = med3Indirect(perm, x, l, l + i, l + 2 * i);
/*  900 */       m = med3Indirect(perm, x, m - i, m, m + i);
/*  901 */       n = med3Indirect(perm, x, n - 2 * i, n - i, n);
/*      */     } 
/*  903 */     m = med3Indirect(perm, x, l, m, n);
/*  904 */     double v = x[perm[m]];
/*      */     
/*  906 */     int a = from, b = a, c = to - 1, d = c;
/*      */     while (true) {
/*      */       int comparison;
/*  909 */       if (b <= c && (comparison = Double.compare(x[perm[b]], v)) <= 0) {
/*  910 */         if (comparison == 0) IntArrays.swap(perm, a++, b); 
/*  911 */         b++; continue;
/*      */       } 
/*  913 */       while (c >= b && (comparison = Double.compare(x[perm[c]], v)) >= 0) {
/*  914 */         if (comparison == 0) IntArrays.swap(perm, c, d--); 
/*  915 */         c--;
/*      */       } 
/*  917 */       if (b > c)
/*  918 */         break;  IntArrays.swap(perm, b++, c--);
/*      */     } 
/*      */ 
/*      */     
/*  922 */     int s = Math.min(a - from, b - a);
/*  923 */     IntArrays.swap(perm, from, b - s, s);
/*  924 */     s = Math.min(d - c, to - d - 1);
/*  925 */     IntArrays.swap(perm, b, to - s, s);
/*      */     
/*  927 */     if ((s = b - a) > 1) quickSortIndirect(perm, x, from, from + s); 
/*  928 */     if ((s = d - c) > 1) quickSortIndirect(perm, x, to - s, to);
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
/*      */   public static void quickSortIndirect(int[] perm, double[] x) {
/*  952 */     quickSortIndirect(perm, x, 0, x.length);
/*      */   }
/*      */   
/*      */   protected static class ForkJoinQuickSortIndirect extends RecursiveAction {
/*      */     private static final long serialVersionUID = 1L;
/*      */     private final int from;
/*      */     private final int to;
/*      */     private final int[] perm;
/*      */     private final double[] x;
/*      */     
/*      */     public ForkJoinQuickSortIndirect(int[] perm, double[] x, int from, int to) {
/*  963 */       this.from = from;
/*  964 */       this.to = to;
/*  965 */       this.x = x;
/*  966 */       this.perm = perm;
/*      */     }
/*      */ 
/*      */ 
/*      */     
/*      */     protected void compute() {
/*  972 */       double[] x = this.x;
/*  973 */       int len = this.to - this.from;
/*  974 */       if (len < 8192) {
/*  975 */         DoubleArrays.quickSortIndirect(this.perm, x, this.from, this.to);
/*      */         
/*      */         return;
/*      */       } 
/*  979 */       int m = this.from + len / 2;
/*  980 */       int l = this.from;
/*  981 */       int n = this.to - 1;
/*  982 */       int s = len / 8;
/*  983 */       l = DoubleArrays.med3Indirect(this.perm, x, l, l + s, l + 2 * s);
/*  984 */       m = DoubleArrays.med3Indirect(this.perm, x, m - s, m, m + s);
/*  985 */       n = DoubleArrays.med3Indirect(this.perm, x, n - 2 * s, n - s, n);
/*  986 */       m = DoubleArrays.med3Indirect(this.perm, x, l, m, n);
/*  987 */       double v = x[this.perm[m]];
/*      */       
/*  989 */       int a = this.from, b = a, c = this.to - 1, d = c;
/*      */       while (true) {
/*      */         int comparison;
/*  992 */         if (b <= c && (comparison = Double.compare(x[this.perm[b]], v)) <= 0) {
/*  993 */           if (comparison == 0) IntArrays.swap(this.perm, a++, b); 
/*  994 */           b++; continue;
/*      */         } 
/*  996 */         while (c >= b && (comparison = Double.compare(x[this.perm[c]], v)) >= 0) {
/*  997 */           if (comparison == 0) IntArrays.swap(this.perm, c, d--); 
/*  998 */           c--;
/*      */         } 
/* 1000 */         if (b > c)
/* 1001 */           break;  IntArrays.swap(this.perm, b++, c--);
/*      */       } 
/*      */ 
/*      */       
/* 1005 */       s = Math.min(a - this.from, b - a);
/* 1006 */       IntArrays.swap(this.perm, this.from, b - s, s);
/* 1007 */       s = Math.min(d - c, this.to - d - 1);
/* 1008 */       IntArrays.swap(this.perm, b, this.to - s, s);
/*      */       
/* 1010 */       s = b - a;
/* 1011 */       int t = d - c;
/* 1012 */       if (s > 1 && t > 1) { invokeAll(new ForkJoinQuickSortIndirect(this.perm, x, this.from, this.from + s), new ForkJoinQuickSortIndirect(this.perm, x, this.to - t, this.to)); }
/* 1013 */       else if (s > 1) { invokeAll((ForkJoinTask<?>[])new ForkJoinTask[] { new ForkJoinQuickSortIndirect(this.perm, x, this.from, this.from + s) }); }
/* 1014 */       else { invokeAll((ForkJoinTask<?>[])new ForkJoinTask[] { new ForkJoinQuickSortIndirect(this.perm, x, this.to - t, this.to) }); }
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
/*      */   public static void parallelQuickSortIndirect(int[] perm, double[] x, int from, int to) {
/* 1038 */     ForkJoinPool pool = getPool();
/* 1039 */     if (to - from < 8192 || pool.getParallelism() == 1) { quickSortIndirect(perm, x, from, to); }
/*      */     else
/* 1041 */     { pool.invoke(new ForkJoinQuickSortIndirect(perm, x, from, to)); }
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
/*      */   public static void parallelQuickSortIndirect(int[] perm, double[] x) {
/* 1063 */     parallelQuickSortIndirect(perm, x, 0, x.length);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static void stabilize(int[] perm, double[] x, int from, int to) {
/* 1090 */     int curr = from;
/* 1091 */     for (int i = from + 1; i < to; i++) {
/* 1092 */       if (x[perm[i]] != x[perm[curr]]) {
/* 1093 */         if (i - curr > 1) IntArrays.parallelQuickSort(perm, curr, i); 
/* 1094 */         curr = i;
/*      */       } 
/*      */     } 
/* 1097 */     if (to - curr > 1) IntArrays.parallelQuickSort(perm, curr, to);
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
/*      */   public static void stabilize(int[] perm, double[] x) {
/* 1122 */     stabilize(perm, x, 0, perm.length);
/*      */   }
/*      */ 
/*      */   
/*      */   private static int med3(double[] x, double[] y, int a, int b, int c) {
/* 1127 */     int t, ab = ((t = Double.compare(x[a], x[b])) == 0) ? Double.compare(y[a], y[b]) : t;
/* 1128 */     int ac = ((t = Double.compare(x[a], x[c])) == 0) ? Double.compare(y[a], y[c]) : t;
/* 1129 */     int bc = ((t = Double.compare(x[b], x[c])) == 0) ? Double.compare(y[b], y[c]) : t;
/* 1130 */     return (ab < 0) ? ((bc < 0) ? b : ((ac < 0) ? c : a)) : ((bc > 0) ? b : ((ac > 0) ? c : a));
/*      */   }
/*      */   
/*      */   private static void swap(double[] x, double[] y, int a, int b) {
/* 1134 */     double t = x[a];
/* 1135 */     double u = y[a];
/* 1136 */     x[a] = x[b];
/* 1137 */     y[a] = y[b];
/* 1138 */     x[b] = t;
/* 1139 */     y[b] = u;
/*      */   }
/*      */   
/*      */   private static void swap(double[] x, double[] y, int a, int b, int n) {
/* 1143 */     for (int i = 0; i < n; ) { swap(x, y, a, b); i++; a++; b++; }
/*      */   
/*      */   }
/*      */   private static void selectionSort(double[] a, double[] b, int from, int to) {
/* 1147 */     for (int i = from; i < to - 1; i++) {
/* 1148 */       int m = i;
/* 1149 */       for (int j = i + 1; j < to; ) { int u; if ((u = Double.compare(a[j], a[m])) < 0 || (u == 0 && Double.compare(b[j], b[m]) < 0)) m = j;  j++; }
/* 1150 */        if (m != i) {
/* 1151 */         double t = a[i];
/* 1152 */         a[i] = a[m];
/* 1153 */         a[m] = t;
/* 1154 */         t = b[i];
/* 1155 */         b[i] = b[m];
/* 1156 */         b[m] = t;
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
/*      */   public static void quickSort(double[] x, double[] y, int from, int to) {
/* 1183 */     int len = to - from;
/* 1184 */     if (len < 16) {
/* 1185 */       selectionSort(x, y, from, to);
/*      */       
/*      */       return;
/*      */     } 
/* 1189 */     int m = from + len / 2;
/* 1190 */     int l = from;
/* 1191 */     int n = to - 1;
/* 1192 */     if (len > 128) {
/* 1193 */       int i = len / 8;
/* 1194 */       l = med3(x, y, l, l + i, l + 2 * i);
/* 1195 */       m = med3(x, y, m - i, m, m + i);
/* 1196 */       n = med3(x, y, n - 2 * i, n - i, n);
/*      */     } 
/* 1198 */     m = med3(x, y, l, m, n);
/* 1199 */     double v = x[m], w = y[m];
/*      */     
/* 1201 */     int a = from, b = a, c = to - 1, d = c;
/*      */     
/*      */     while (true) {
/* 1204 */       if (b <= c) { int comparison; int t; if ((comparison = ((t = Double.compare(x[b], v)) == 0) ? Double.compare(y[b], w) : t) <= 0) {
/* 1205 */           if (comparison == 0) swap(x, y, a++, b); 
/* 1206 */           b++; continue;
/*      */         }  }
/* 1208 */        while (c >= b) { int comparison; int t; if ((comparison = ((t = Double.compare(x[c], v)) == 0) ? Double.compare(y[c], w) : t) >= 0) {
/* 1209 */           if (comparison == 0) swap(x, y, c, d--); 
/* 1210 */           c--;
/*      */         }  }
/* 1212 */        if (b > c)
/* 1213 */         break;  swap(x, y, b++, c--);
/*      */     } 
/*      */ 
/*      */     
/* 1217 */     int s = Math.min(a - from, b - a);
/* 1218 */     swap(x, y, from, b - s, s);
/* 1219 */     s = Math.min(d - c, to - d - 1);
/* 1220 */     swap(x, y, b, to - s, s);
/*      */     
/* 1222 */     if ((s = b - a) > 1) quickSort(x, y, from, from + s); 
/* 1223 */     if ((s = d - c) > 1) quickSort(x, y, to - s, to);
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
/*      */   public static void quickSort(double[] x, double[] y) {
/* 1244 */     ensureSameLength(x, y);
/* 1245 */     quickSort(x, y, 0, x.length);
/*      */   }
/*      */   
/*      */   protected static class ForkJoinQuickSort2 extends RecursiveAction { private static final long serialVersionUID = 1L;
/*      */     private final int from;
/*      */     private final int to;
/*      */     private final double[] x;
/*      */     private final double[] y;
/*      */     
/*      */     public ForkJoinQuickSort2(double[] x, double[] y, int from, int to) {
/* 1255 */       this.from = from;
/* 1256 */       this.to = to;
/* 1257 */       this.x = x;
/* 1258 */       this.y = y;
/*      */     }
/*      */ 
/*      */ 
/*      */     
/*      */     protected void compute() {
/* 1264 */       double[] x = this.x;
/* 1265 */       double[] y = this.y;
/* 1266 */       int len = this.to - this.from;
/* 1267 */       if (len < 8192) {
/* 1268 */         DoubleArrays.quickSort(x, y, this.from, this.to);
/*      */         
/*      */         return;
/*      */       } 
/* 1272 */       int m = this.from + len / 2;
/* 1273 */       int l = this.from;
/* 1274 */       int n = this.to - 1;
/* 1275 */       int s = len / 8;
/* 1276 */       l = DoubleArrays.med3(x, y, l, l + s, l + 2 * s);
/* 1277 */       m = DoubleArrays.med3(x, y, m - s, m, m + s);
/* 1278 */       n = DoubleArrays.med3(x, y, n - 2 * s, n - s, n);
/* 1279 */       m = DoubleArrays.med3(x, y, l, m, n);
/* 1280 */       double v = x[m], w = y[m];
/*      */       
/* 1282 */       int a = this.from, b = a, c = this.to - 1, d = c;
/*      */       
/*      */       while (true) {
/* 1285 */         if (b <= c) { int comparison; int i; if ((comparison = ((i = Double.compare(x[b], v)) == 0) ? Double.compare(y[b], w) : i) <= 0) {
/* 1286 */             if (comparison == 0) DoubleArrays.swap(x, y, a++, b); 
/* 1287 */             b++; continue;
/*      */           }  }
/* 1289 */          while (c >= b) { int comparison; int i; if ((comparison = ((i = Double.compare(x[c], v)) == 0) ? Double.compare(y[c], w) : i) >= 0) {
/* 1290 */             if (comparison == 0) DoubleArrays.swap(x, y, c, d--); 
/* 1291 */             c--;
/*      */           }  }
/* 1293 */          if (b > c)
/* 1294 */           break;  DoubleArrays.swap(x, y, b++, c--);
/*      */       } 
/*      */ 
/*      */       
/* 1298 */       s = Math.min(a - this.from, b - a);
/* 1299 */       DoubleArrays.swap(x, y, this.from, b - s, s);
/* 1300 */       s = Math.min(d - c, this.to - d - 1);
/* 1301 */       DoubleArrays.swap(x, y, b, this.to - s, s);
/* 1302 */       s = b - a;
/* 1303 */       int t = d - c;
/*      */       
/* 1305 */       if (s > 1 && t > 1) { invokeAll(new ForkJoinQuickSort2(x, y, this.from, this.from + s), new ForkJoinQuickSort2(x, y, this.to - t, this.to)); }
/* 1306 */       else if (s > 1) { invokeAll((ForkJoinTask<?>[])new ForkJoinTask[] { new ForkJoinQuickSort2(x, y, this.from, this.from + s) }); }
/* 1307 */       else { invokeAll((ForkJoinTask<?>[])new ForkJoinTask[] { new ForkJoinQuickSort2(x, y, this.to - t, this.to) }); }
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
/*      */   public static void parallelQuickSort(double[] x, double[] y, int from, int to) {
/* 1332 */     ForkJoinPool pool = getPool();
/* 1333 */     if (to - from < 8192 || pool.getParallelism() == 1) { quickSort(x, y, from, to); }
/*      */     else
/* 1335 */     { pool.invoke(new ForkJoinQuickSort2(x, y, from, to)); }
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
/*      */   public static void parallelQuickSort(double[] x, double[] y) {
/* 1358 */     ensureSameLength(x, y);
/* 1359 */     parallelQuickSort(x, y, 0, x.length);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static void unstableSort(double[] a, int from, int to) {
/* 1375 */     if (to - from >= 4000) {
/* 1376 */       radixSort(a, from, to);
/*      */     } else {
/* 1378 */       quickSort(a, from, to);
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
/*      */   public static void unstableSort(double[] a) {
/* 1391 */     unstableSort(a, 0, a.length);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static void unstableSort(double[] a, int from, int to, DoubleComparator comp) {
/* 1406 */     quickSort(a, from, to, comp);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static void unstableSort(double[] a, DoubleComparator comp) {
/* 1419 */     unstableSort(a, 0, a.length, comp);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static void mergeSort(double[] a, int from, int to, double[] supp) {
/* 1439 */     int len = to - from;
/*      */     
/* 1441 */     if (len < 16) {
/* 1442 */       insertionSort(a, from, to);
/*      */       return;
/*      */     } 
/* 1445 */     if (supp == null) supp = Arrays.copyOf(a, to);
/*      */     
/* 1447 */     int mid = from + to >>> 1;
/* 1448 */     mergeSort(supp, from, mid, a);
/* 1449 */     mergeSort(supp, mid, to, a);
/*      */ 
/*      */     
/* 1452 */     if (Double.compare(supp[mid - 1], supp[mid]) <= 0) {
/* 1453 */       System.arraycopy(supp, from, a, from, len);
/*      */       
/*      */       return;
/*      */     } 
/* 1457 */     for (int i = from, p = from, q = mid; i < to; i++) {
/* 1458 */       if (q >= to || (p < mid && Double.compare(supp[p], supp[q]) <= 0)) { a[i] = supp[p++]; }
/* 1459 */       else { a[i] = supp[q++]; }
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
/*      */   public static void mergeSort(double[] a, int from, int to) {
/* 1475 */     mergeSort(a, from, to, (double[])null);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static void mergeSort(double[] a) {
/* 1488 */     mergeSort(a, 0, a.length);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static void mergeSort(double[] a, int from, int to, DoubleComparator comp, double[] supp) {
/* 1508 */     int len = to - from;
/*      */     
/* 1510 */     if (len < 16) {
/* 1511 */       insertionSort(a, from, to, comp);
/*      */       return;
/*      */     } 
/* 1514 */     if (supp == null) supp = Arrays.copyOf(a, to);
/*      */     
/* 1516 */     int mid = from + to >>> 1;
/* 1517 */     mergeSort(supp, from, mid, comp, a);
/* 1518 */     mergeSort(supp, mid, to, comp, a);
/*      */ 
/*      */     
/* 1521 */     if (comp.compare(supp[mid - 1], supp[mid]) <= 0) {
/* 1522 */       System.arraycopy(supp, from, a, from, len);
/*      */       
/*      */       return;
/*      */     } 
/* 1526 */     for (int i = from, p = from, q = mid; i < to; i++) {
/* 1527 */       if (q >= to || (p < mid && comp.compare(supp[p], supp[q]) <= 0)) { a[i] = supp[p++]; }
/* 1528 */       else { a[i] = supp[q++]; }
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
/*      */   public static void mergeSort(double[] a, int from, int to, DoubleComparator comp) {
/* 1546 */     mergeSort(a, from, to, comp, (double[])null);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static void mergeSort(double[] a, DoubleComparator comp) {
/* 1560 */     mergeSort(a, 0, a.length, comp);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static void stableSort(double[] a, int from, int to) {
/* 1580 */     mergeSort(a, from, to);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static void stableSort(double[] a) {
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
/*      */   public static void stableSort(double[] a, int from, int to, DoubleComparator comp) {
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
/*      */   public static void stableSort(double[] a, DoubleComparator comp) {
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
/*      */   public static int binarySearch(double[] a, int from, int to, double key) {
/* 1659 */     to--;
/* 1660 */     while (from <= to) {
/* 1661 */       int mid = from + to >>> 1;
/* 1662 */       double midVal = a[mid];
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
/*      */   public static int binarySearch(double[] a, double key) {
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
/*      */   public static int binarySearch(double[] a, int from, int to, double key, DoubleComparator c) {
/* 1711 */     to--;
/* 1712 */     while (from <= to) {
/* 1713 */       int mid = from + to >>> 1;
/* 1714 */       double midVal = a[mid];
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
/*      */   public static int binarySearch(double[] a, double key, DoubleComparator c) {
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
/*      */   private static final long fixDouble(double d) {
/* 1762 */     long l = Double.doubleToLongBits(d);
/* 1763 */     return (l >= 0L) ? l : (l ^ Long.MAX_VALUE);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static void radixSort(double[] a) {
/* 1780 */     radixSort(a, 0, a.length);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static void radixSort(double[] a, int from, int to) {
/* 1799 */     if (to - from < 1024) {
/* 1800 */       quickSort(a, from, to);
/*      */       return;
/*      */     } 
/* 1803 */     int maxLevel = 7;
/* 1804 */     int stackSize = 1786;
/* 1805 */     int stackPos = 0;
/* 1806 */     int[] offsetStack = new int[1786];
/* 1807 */     int[] lengthStack = new int[1786];
/* 1808 */     int[] levelStack = new int[1786];
/* 1809 */     offsetStack[stackPos] = from;
/* 1810 */     lengthStack[stackPos] = to - from;
/* 1811 */     levelStack[stackPos++] = 0;
/* 1812 */     int[] count = new int[256];
/* 1813 */     int[] pos = new int[256];
/* 1814 */     while (stackPos > 0) {
/* 1815 */       int first = offsetStack[--stackPos];
/* 1816 */       int length = lengthStack[stackPos];
/* 1817 */       int level = levelStack[stackPos];
/* 1818 */       int signMask = (level % 8 == 0) ? 128 : 0;
/* 1819 */       int shift = (7 - level % 8) * 8;
/*      */ 
/*      */ 
/*      */ 
/*      */       
/* 1824 */       for (int i = first + length; i-- != first; count[(int)(fixDouble(a[i]) >>> shift & 0xFFL ^ signMask)] = count[(int)(fixDouble(a[i]) >>> shift & 0xFFL ^ signMask)] + 1);
/*      */       
/* 1826 */       int lastUsed = -1;
/* 1827 */       for (int j = 0, p = first; j < 256; j++) {
/* 1828 */         if (count[j] != 0) lastUsed = j; 
/* 1829 */         pos[j] = p += count[j];
/*      */       } 
/* 1831 */       int end = first + length - count[lastUsed];
/*      */       
/* 1833 */       for (int k = first, c = -1; k <= end; k += count[c], count[c] = 0) {
/* 1834 */         double t = a[k];
/* 1835 */         c = (int)(fixDouble(t) >>> shift & 0xFFL ^ signMask);
/* 1836 */         if (k < end) {
/* 1837 */           int d; for (pos[c] = pos[c] - 1; (d = pos[c] - 1) > k; ) {
/* 1838 */             double z = t;
/* 1839 */             t = a[d];
/* 1840 */             a[d] = z;
/* 1841 */             c = (int)(fixDouble(t) >>> shift & 0xFFL ^ signMask);
/*      */           } 
/* 1843 */           a[k] = t;
/*      */         } 
/* 1845 */         if (level < 7 && count[c] > 1)
/* 1846 */           if (count[c] < 1024) { quickSort(a, k, k + count[c]); }
/*      */           else
/* 1848 */           { offsetStack[stackPos] = k;
/* 1849 */             lengthStack[stackPos] = count[c];
/* 1850 */             levelStack[stackPos++] = level + 1; }
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
/* 1861 */       this.offset = offset;
/* 1862 */       this.length = length;
/* 1863 */       this.level = level;
/*      */     }
/*      */ 
/*      */     
/*      */     public String toString() {
/* 1868 */       return "Segment [offset=" + this.offset + ", length=" + this.length + ", level=" + this.level + "]";
/*      */     } }
/*      */ 
/*      */   
/* 1872 */   protected static final Segment POISON_PILL = new Segment(-1, -1, -1);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static void parallelRadixSort(double[] a, int from, int to) {
/* 1887 */     ForkJoinPool pool = getPool();
/* 1888 */     if (to - from < 1024 || pool.getParallelism() == 1) {
/* 1889 */       quickSort(a, from, to);
/*      */       return;
/*      */     } 
/* 1892 */     int maxLevel = 7;
/* 1893 */     LinkedBlockingQueue<Segment> queue = new LinkedBlockingQueue<>();
/* 1894 */     queue.add(new Segment(from, to - from, 0));
/* 1895 */     AtomicInteger queueSize = new AtomicInteger(1);
/* 1896 */     int numberOfThreads = pool.getParallelism();
/* 1897 */     ExecutorCompletionService<Void> executorCompletionService = new ExecutorCompletionService<>(pool);
/* 1898 */     for (int j = numberOfThreads; j-- != 0; executorCompletionService.submit(() -> {
/*      */           int[] count = new int[256]; int[] pos = new int[256]; while (true) {
/*      */             if (queueSize.get() == 0) {
/*      */               int m = numberOfThreads; while (m-- != 0)
/*      */                 queue.add(POISON_PILL); 
/*      */             }  Segment segment = queue.take(); if (segment == POISON_PILL)
/*      */               return null;  int first = segment.offset; int length = segment.length; int level = segment.level; int signMask = (level % 8 == 0) ? 128 : 0; int shift = (7 - level % 8) * 8;
/*      */             int i = first + length;
/*      */             while (i-- != first)
/*      */               count[(int)(fixDouble(a[i]) >>> shift & 0xFFL ^ signMask)] = count[(int)(fixDouble(a[i]) >>> shift & 0xFFL ^ signMask)] + 1; 
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
/*      */               double t = a[k];
/*      */               c = (int)(fixDouble(t) >>> shift & 0xFFL ^ signMask);
/*      */               if (k < end) {
/*      */                 pos[c] = pos[c] - 1;
/*      */                 int d;
/*      */                 while ((d = pos[c] - 1) > k) {
/*      */                   double z = t;
/*      */                   t = a[d];
/*      */                   a[d] = z;
/*      */                   c = (int)(fixDouble(t) >>> shift & 0xFFL ^ signMask);
/*      */                 } 
/*      */                 a[k] = t;
/*      */               } 
/*      */               if (level < 7 && count[c] > 1)
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
/* 1947 */     Throwable problem = null;
/* 1948 */     for (int i = numberOfThreads; i-- != 0;) { try {
/* 1949 */         executorCompletionService.take().get();
/* 1950 */       } catch (Exception e) {
/* 1951 */         problem = e.getCause();
/*      */       }  }
/* 1953 */      if (problem != null) throw (problem instanceof RuntimeException) ? (RuntimeException)problem : new RuntimeException(problem);
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
/*      */   public static void parallelRadixSort(double[] a) {
/* 1967 */     parallelRadixSort(a, 0, a.length);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static void radixSortIndirect(int[] perm, double[] a, boolean stable) {
/* 1991 */     radixSortIndirect(perm, a, 0, perm.length, stable);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static void radixSortIndirect(int[] perm, double[] a, int from, int to, boolean stable) {
/* 2017 */     if (to - from < 1024) {
/* 2018 */       quickSortIndirect(perm, a, from, to);
/* 2019 */       if (stable) stabilize(perm, a, from, to); 
/*      */       return;
/*      */     } 
/* 2022 */     int maxLevel = 7;
/* 2023 */     int stackSize = 1786;
/* 2024 */     int stackPos = 0;
/* 2025 */     int[] offsetStack = new int[1786];
/* 2026 */     int[] lengthStack = new int[1786];
/* 2027 */     int[] levelStack = new int[1786];
/* 2028 */     offsetStack[stackPos] = from;
/* 2029 */     lengthStack[stackPos] = to - from;
/* 2030 */     levelStack[stackPos++] = 0;
/* 2031 */     int[] count = new int[256];
/* 2032 */     int[] pos = new int[256];
/* 2033 */     int[] support = stable ? new int[perm.length] : null;
/* 2034 */     while (stackPos > 0) {
/* 2035 */       int first = offsetStack[--stackPos];
/* 2036 */       int length = lengthStack[stackPos];
/* 2037 */       int level = levelStack[stackPos];
/* 2038 */       int signMask = (level % 8 == 0) ? 128 : 0;
/* 2039 */       int shift = (7 - level % 8) * 8;
/*      */ 
/*      */ 
/*      */ 
/*      */       
/* 2044 */       for (int i = first + length; i-- != first; count[(int)(fixDouble(a[perm[i]]) >>> shift & 0xFFL ^ signMask)] = count[(int)(fixDouble(a[perm[i]]) >>> shift & 0xFFL ^ signMask)] + 1);
/*      */       
/* 2046 */       int lastUsed = -1; int j, p;
/* 2047 */       for (j = 0, p = stable ? 0 : first; j < 256; j++) {
/* 2048 */         if (count[j] != 0) lastUsed = j; 
/* 2049 */         pos[j] = p += count[j];
/*      */       } 
/* 2051 */       if (stable) {
/* 2052 */         for (j = first + length; j-- != first; ) { pos[(int)(fixDouble(a[perm[j]]) >>> shift & 0xFFL ^ signMask)] = pos[(int)(fixDouble(a[perm[j]]) >>> shift & 0xFFL ^ signMask)] - 1; support[pos[(int)(fixDouble(a[perm[j]]) >>> shift & 0xFFL ^ signMask)] - 1] = perm[j]; }
/* 2053 */          System.arraycopy(support, 0, perm, first, length);
/* 2054 */         for (j = 0, p = first; j <= lastUsed; j++) {
/* 2055 */           if (level < 7 && count[j] > 1) {
/* 2056 */             if (count[j] < 1024) {
/* 2057 */               quickSortIndirect(perm, a, p, p + count[j]);
/* 2058 */               if (stable) stabilize(perm, a, p, p + count[j]); 
/*      */             } else {
/* 2060 */               offsetStack[stackPos] = p;
/* 2061 */               lengthStack[stackPos] = count[j];
/* 2062 */               levelStack[stackPos++] = level + 1;
/*      */             } 
/*      */           }
/* 2065 */           p += count[j];
/*      */         } 
/* 2067 */         Arrays.fill(count, 0); continue;
/*      */       } 
/* 2069 */       int end = first + length - count[lastUsed];
/*      */       
/* 2071 */       for (int k = first, c = -1; k <= end; k += count[c], count[c] = 0) {
/* 2072 */         int t = perm[k];
/* 2073 */         c = (int)(fixDouble(a[t]) >>> shift & 0xFFL ^ signMask);
/* 2074 */         if (k < end) {
/* 2075 */           int d; for (pos[c] = pos[c] - 1; (d = pos[c] - 1) > k; ) {
/* 2076 */             int z = t;
/* 2077 */             t = perm[d];
/* 2078 */             perm[d] = z;
/* 2079 */             c = (int)(fixDouble(a[t]) >>> shift & 0xFFL ^ signMask);
/*      */           } 
/* 2081 */           perm[k] = t;
/*      */         } 
/* 2083 */         if (level < 7 && count[c] > 1) {
/* 2084 */           if (count[c] < 1024) {
/* 2085 */             quickSortIndirect(perm, a, k, k + count[c]);
/* 2086 */             if (stable) stabilize(perm, a, k, k + count[c]); 
/*      */           } else {
/* 2088 */             offsetStack[stackPos] = k;
/* 2089 */             lengthStack[stackPos] = count[c];
/* 2090 */             levelStack[stackPos++] = level + 1;
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
/*      */   public static void parallelRadixSortIndirect(int[] perm, double[] a, int from, int to, boolean stable) {
/* 2118 */     ForkJoinPool pool = getPool();
/* 2119 */     if (to - from < 1024 || pool.getParallelism() == 1) {
/* 2120 */       radixSortIndirect(perm, a, from, to, stable);
/*      */       return;
/*      */     } 
/* 2123 */     int maxLevel = 7;
/* 2124 */     LinkedBlockingQueue<Segment> queue = new LinkedBlockingQueue<>();
/* 2125 */     queue.add(new Segment(from, to - from, 0));
/* 2126 */     AtomicInteger queueSize = new AtomicInteger(1);
/* 2127 */     int numberOfThreads = pool.getParallelism();
/* 2128 */     ExecutorCompletionService<Void> executorCompletionService = new ExecutorCompletionService<>(pool);
/* 2129 */     int[] support = stable ? new int[perm.length] : null;
/* 2130 */     for (int j = numberOfThreads; j-- != 0; executorCompletionService.submit(() -> {
/*      */           int[] count = new int[256]; int[] pos = new int[256]; while (true) {
/*      */             if (queueSize.get() == 0) {
/*      */               int k = numberOfThreads; while (k-- != 0)
/*      */                 queue.add(POISON_PILL); 
/*      */             }  Segment segment = queue.take(); if (segment == POISON_PILL)
/*      */               return null;  int first = segment.offset; int length = segment.length; int level = segment.level; int signMask = (level % 8 == 0) ? 128 : 0; int shift = (7 - level % 8) * 8; int i = first + length; while (i-- != first)
/*      */               count[(int)(fixDouble(a[perm[i]]) >>> shift & 0xFFL ^ signMask)] = count[(int)(fixDouble(a[perm[i]]) >>> shift & 0xFFL ^ signMask)] + 1;  int lastUsed = -1; int j = 0; int p = first; while (j < 256) {
/*      */               if (count[j] != 0)
/*      */                 lastUsed = j;  pos[j] = p += count[j];
/*      */               j++;
/*      */             } 
/*      */             if (stable) {
/*      */               j = first + length;
/*      */               while (j-- != first) {
/*      */                 pos[(int)(fixDouble(a[perm[j]]) >>> shift & 0xFFL ^ signMask)] = pos[(int)(fixDouble(a[perm[j]]) >>> shift & 0xFFL ^ signMask)] - 1;
/*      */                 support[pos[(int)(fixDouble(a[perm[j]]) >>> shift & 0xFFL ^ signMask)] - 1] = perm[j];
/*      */               } 
/*      */               System.arraycopy(support, first, perm, first, length);
/*      */               j = 0;
/*      */               p = first;
/*      */               while (j <= lastUsed) {
/*      */                 if (level < 7 && count[j] > 1)
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
/*      */                 c = (int)(fixDouble(a[t]) >>> shift & 0xFFL ^ signMask);
/*      */                 if (k < end) {
/*      */                   pos[c] = pos[c] - 1;
/*      */                   int d;
/*      */                   while ((d = pos[c] - 1) > k) {
/*      */                     int z = t;
/*      */                     t = perm[d];
/*      */                     perm[d] = z;
/*      */                     c = (int)(fixDouble(a[t]) >>> shift & 0xFFL ^ signMask);
/*      */                   } 
/*      */                   perm[k] = t;
/*      */                 } 
/*      */                 if (level < 7 && count[c] > 1)
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
/* 2195 */     Throwable problem = null;
/* 2196 */     for (int i = numberOfThreads; i-- != 0;) { try {
/* 2197 */         executorCompletionService.take().get();
/* 2198 */       } catch (Exception e) {
/* 2199 */         problem = e.getCause();
/*      */       }  }
/* 2201 */      if (problem != null) throw (problem instanceof RuntimeException) ? (RuntimeException)problem : new RuntimeException(problem);
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
/*      */   public static void parallelRadixSortIndirect(int[] perm, double[] a, boolean stable) {
/* 2222 */     parallelRadixSortIndirect(perm, a, 0, a.length, stable);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static void radixSort(double[] a, double[] b) {
/* 2242 */     ensureSameLength(a, b);
/* 2243 */     radixSort(a, b, 0, a.length);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static void radixSort(double[] a, double[] b, int from, int to) {
/* 2266 */     if (to - from < 1024) {
/* 2267 */       quickSort(a, b, from, to);
/*      */       return;
/*      */     } 
/* 2270 */     int layers = 2;
/* 2271 */     int maxLevel = 15;
/* 2272 */     int stackSize = 3826;
/* 2273 */     int stackPos = 0;
/* 2274 */     int[] offsetStack = new int[3826];
/* 2275 */     int[] lengthStack = new int[3826];
/* 2276 */     int[] levelStack = new int[3826];
/* 2277 */     offsetStack[stackPos] = from;
/* 2278 */     lengthStack[stackPos] = to - from;
/* 2279 */     levelStack[stackPos++] = 0;
/* 2280 */     int[] count = new int[256];
/* 2281 */     int[] pos = new int[256];
/* 2282 */     while (stackPos > 0) {
/* 2283 */       int first = offsetStack[--stackPos];
/* 2284 */       int length = lengthStack[stackPos];
/* 2285 */       int level = levelStack[stackPos];
/* 2286 */       int signMask = (level % 8 == 0) ? 128 : 0;
/* 2287 */       double[] k = (level < 8) ? a : b;
/* 2288 */       int shift = (7 - level % 8) * 8;
/*      */ 
/*      */ 
/*      */ 
/*      */       
/* 2293 */       for (int i = first + length; i-- != first; count[(int)(fixDouble(k[i]) >>> shift & 0xFFL ^ signMask)] = count[(int)(fixDouble(k[i]) >>> shift & 0xFFL ^ signMask)] + 1);
/*      */       
/* 2295 */       int lastUsed = -1;
/* 2296 */       for (int j = 0, p = first; j < 256; j++) {
/* 2297 */         if (count[j] != 0) lastUsed = j; 
/* 2298 */         pos[j] = p += count[j];
/*      */       } 
/* 2300 */       int end = first + length - count[lastUsed];
/*      */       
/* 2302 */       for (int m = first, c = -1; m <= end; m += count[c], count[c] = 0) {
/* 2303 */         double t = a[m];
/* 2304 */         double u = b[m];
/* 2305 */         c = (int)(fixDouble(k[m]) >>> shift & 0xFFL ^ signMask);
/* 2306 */         if (m < end) {
/* 2307 */           int d; for (pos[c] = pos[c] - 1; (d = pos[c] - 1) > m; ) {
/* 2308 */             c = (int)(fixDouble(k[d]) >>> shift & 0xFFL ^ signMask);
/* 2309 */             double z = t;
/* 2310 */             t = a[d];
/* 2311 */             a[d] = z;
/* 2312 */             z = u;
/* 2313 */             u = b[d];
/* 2314 */             b[d] = z;
/*      */           } 
/* 2316 */           a[m] = t;
/* 2317 */           b[m] = u;
/*      */         } 
/* 2319 */         if (level < 15 && count[c] > 1) {
/* 2320 */           if (count[c] < 1024) { quickSort(a, b, m, m + count[c]); }
/*      */           else
/* 2322 */           { offsetStack[stackPos] = m;
/* 2323 */             lengthStack[stackPos] = count[c];
/* 2324 */             levelStack[stackPos++] = level + 1; }
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
/*      */   public static void parallelRadixSort(double[] a, double[] b, int from, int to) {
/* 2351 */     ForkJoinPool pool = getPool();
/* 2352 */     if (to - from < 1024 || pool.getParallelism() == 1) {
/* 2353 */       quickSort(a, b, from, to);
/*      */       return;
/*      */     } 
/* 2356 */     int layers = 2;
/* 2357 */     if (a.length != b.length) throw new IllegalArgumentException("Array size mismatch."); 
/* 2358 */     int maxLevel = 15;
/* 2359 */     LinkedBlockingQueue<Segment> queue = new LinkedBlockingQueue<>();
/* 2360 */     queue.add(new Segment(from, to - from, 0));
/* 2361 */     AtomicInteger queueSize = new AtomicInteger(1);
/* 2362 */     int numberOfThreads = pool.getParallelism();
/* 2363 */     ExecutorCompletionService<Void> executorCompletionService = new ExecutorCompletionService<>(pool);
/* 2364 */     for (int j = numberOfThreads; j-- != 0; executorCompletionService.submit(() -> {
/*      */           int[] count = new int[256]; int[] pos = new int[256]; while (true) {
/*      */             if (queueSize.get() == 0) {
/*      */               int n = numberOfThreads; while (n-- != 0)
/*      */                 queue.add(POISON_PILL); 
/*      */             }  Segment segment = queue.take(); if (segment == POISON_PILL)
/*      */               return null;  int first = segment.offset; int length = segment.length; int level = segment.level; int signMask = (level % 8 == 0) ? 128 : 0; double[] k = (level < 8) ? a : b; int shift = (7 - level % 8) * 8; int i = first + length; while (i-- != first)
/*      */               count[(int)(fixDouble(k[i]) >>> shift & 0xFFL ^ signMask)] = count[(int)(fixDouble(k[i]) >>> shift & 0xFFL ^ signMask)] + 1;  int lastUsed = -1; int j = 0;
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
/*      */               double t = a[m];
/*      */               double u = b[m];
/*      */               c = (int)(fixDouble(k[m]) >>> shift & 0xFFL ^ signMask);
/*      */               if (m < end) {
/*      */                 pos[c] = pos[c] - 1;
/*      */                 int d;
/*      */                 while ((d = pos[c] - 1) > m) {
/*      */                   c = (int)(fixDouble(k[d]) >>> shift & 0xFFL ^ signMask);
/*      */                   double z = t;
/*      */                   double w = u;
/*      */                   t = a[d];
/*      */                   u = b[d];
/*      */                   a[d] = z;
/*      */                   b[d] = w;
/*      */                 } 
/*      */                 a[m] = t;
/*      */                 b[m] = u;
/*      */               } 
/*      */               if (level < 15 && count[c] > 1)
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
/* 2414 */     Throwable problem = null;
/* 2415 */     for (int i = numberOfThreads; i-- != 0;) { try {
/* 2416 */         executorCompletionService.take().get();
/* 2417 */       } catch (Exception e) {
/* 2418 */         problem = e.getCause();
/*      */       }  }
/* 2420 */      if (problem != null) throw (problem instanceof RuntimeException) ? (RuntimeException)problem : new RuntimeException(problem);
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
/*      */   public static void parallelRadixSort(double[] a, double[] b) {
/* 2441 */     ensureSameLength(a, b);
/* 2442 */     parallelRadixSort(a, b, 0, a.length);
/*      */   }
/*      */   
/*      */   private static void insertionSortIndirect(int[] perm, double[] a, double[] b, int from, int to) {
/* 2446 */     for (int i = from; ++i < to; ) {
/* 2447 */       int t = perm[i];
/* 2448 */       int j = i; int u;
/* 2449 */       for (u = perm[j - 1]; Double.compare(a[t], a[u]) < 0 || (Double.compare(a[t], a[u]) == 0 && Double.compare(b[t], b[u]) < 0); u = perm[--j - 1]) {
/* 2450 */         perm[j] = u;
/* 2451 */         if (from == j - 1) {
/* 2452 */           j--;
/*      */           break;
/*      */         } 
/*      */       } 
/* 2456 */       perm[j] = t;
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
/*      */   public static void radixSortIndirect(int[] perm, double[] a, double[] b, boolean stable) {
/* 2483 */     ensureSameLength(a, b);
/* 2484 */     radixSortIndirect(perm, a, b, 0, a.length, stable);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static void radixSortIndirect(int[] perm, double[] a, double[] b, int from, int to, boolean stable) {
/* 2512 */     if (to - from < 64) {
/* 2513 */       insertionSortIndirect(perm, a, b, from, to);
/*      */       return;
/*      */     } 
/* 2516 */     int layers = 2;
/* 2517 */     int maxLevel = 15;
/* 2518 */     int stackSize = 3826;
/* 2519 */     int stackPos = 0;
/* 2520 */     int[] offsetStack = new int[3826];
/* 2521 */     int[] lengthStack = new int[3826];
/* 2522 */     int[] levelStack = new int[3826];
/* 2523 */     offsetStack[stackPos] = from;
/* 2524 */     lengthStack[stackPos] = to - from;
/* 2525 */     levelStack[stackPos++] = 0;
/* 2526 */     int[] count = new int[256];
/* 2527 */     int[] pos = new int[256];
/* 2528 */     int[] support = stable ? new int[perm.length] : null;
/* 2529 */     while (stackPos > 0) {
/* 2530 */       int first = offsetStack[--stackPos];
/* 2531 */       int length = lengthStack[stackPos];
/* 2532 */       int level = levelStack[stackPos];
/* 2533 */       int signMask = (level % 8 == 0) ? 128 : 0;
/* 2534 */       double[] k = (level < 8) ? a : b;
/* 2535 */       int shift = (7 - level % 8) * 8;
/*      */ 
/*      */ 
/*      */ 
/*      */       
/* 2540 */       for (int i = first + length; i-- != first; count[(int)(fixDouble(k[perm[i]]) >>> shift & 0xFFL ^ signMask)] = count[(int)(fixDouble(k[perm[i]]) >>> shift & 0xFFL ^ signMask)] + 1);
/*      */       
/* 2542 */       int lastUsed = -1; int j, p;
/* 2543 */       for (j = 0, p = stable ? 0 : first; j < 256; j++) {
/* 2544 */         if (count[j] != 0) lastUsed = j; 
/* 2545 */         pos[j] = p += count[j];
/*      */       } 
/* 2547 */       if (stable) {
/* 2548 */         for (j = first + length; j-- != first; ) { pos[(int)(fixDouble(k[perm[j]]) >>> shift & 0xFFL ^ signMask)] = pos[(int)(fixDouble(k[perm[j]]) >>> shift & 0xFFL ^ signMask)] - 1; support[pos[(int)(fixDouble(k[perm[j]]) >>> shift & 0xFFL ^ signMask)] - 1] = perm[j]; }
/* 2549 */          System.arraycopy(support, 0, perm, first, length);
/* 2550 */         for (j = 0, p = first; j < 256; j++) {
/* 2551 */           if (level < 15 && count[j] > 1) {
/* 2552 */             if (count[j] < 64) { insertionSortIndirect(perm, a, b, p, p + count[j]); }
/*      */             else
/* 2554 */             { offsetStack[stackPos] = p;
/* 2555 */               lengthStack[stackPos] = count[j];
/* 2556 */               levelStack[stackPos++] = level + 1; }
/*      */           
/*      */           }
/* 2559 */           p += count[j];
/*      */         } 
/* 2561 */         Arrays.fill(count, 0); continue;
/*      */       } 
/* 2563 */       int end = first + length - count[lastUsed];
/*      */       
/* 2565 */       for (int m = first, c = -1; m <= end; m += count[c], count[c] = 0) {
/* 2566 */         int t = perm[m];
/* 2567 */         c = (int)(fixDouble(k[t]) >>> shift & 0xFFL ^ signMask);
/* 2568 */         if (m < end) {
/* 2569 */           int d; for (pos[c] = pos[c] - 1; (d = pos[c] - 1) > m; ) {
/* 2570 */             int z = t;
/* 2571 */             t = perm[d];
/* 2572 */             perm[d] = z;
/* 2573 */             c = (int)(fixDouble(k[t]) >>> shift & 0xFFL ^ signMask);
/*      */           } 
/* 2575 */           perm[m] = t;
/*      */         } 
/* 2577 */         if (level < 15 && count[c] > 1) {
/* 2578 */           if (count[c] < 64) { insertionSortIndirect(perm, a, b, m, m + count[c]); }
/*      */           else
/* 2580 */           { offsetStack[stackPos] = m;
/* 2581 */             lengthStack[stackPos] = count[c];
/* 2582 */             levelStack[stackPos++] = level + 1; }
/*      */         
/*      */         }
/*      */       } 
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   private static void selectionSort(double[][] a, int from, int to, int level) {
/* 2591 */     int layers = a.length;
/* 2592 */     int firstLayer = level / 8;
/* 2593 */     for (int i = from; i < to - 1; i++) {
/* 2594 */       int m = i;
/* 2595 */       for (int j = i + 1; j < to; j++) {
/* 2596 */         for (int p = firstLayer; p < layers; p++) {
/* 2597 */           if (a[p][j] < a[p][m]) {
/* 2598 */             m = j; break;
/*      */           } 
/* 2600 */           if (a[p][j] > a[p][m])
/*      */             break; 
/*      */         } 
/* 2603 */       }  if (m != i) {
/* 2604 */         for (int p = layers; p-- != 0; ) {
/* 2605 */           double u = a[p][i];
/* 2606 */           a[p][i] = a[p][m];
/* 2607 */           a[p][m] = u;
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
/*      */   public static void radixSort(double[][] a) {
/* 2628 */     radixSort(a, 0, (a[0]).length);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static void radixSort(double[][] a, int from, int to) {
/* 2648 */     if (to - from < 64) {
/* 2649 */       selectionSort(a, from, to, 0);
/*      */       return;
/*      */     } 
/* 2652 */     int layers = a.length;
/* 2653 */     int maxLevel = 8 * layers - 1;
/* 2654 */     int p = layers;
/* 2655 */     for (int l = (a[0]).length; p-- != 0;) { if ((a[p]).length != l) throw new IllegalArgumentException("The array of index " + p + " has not the same length of the array of index 0.");  }
/* 2656 */      int stackSize = 255 * (layers * 8 - 1) + 1;
/* 2657 */     int stackPos = 0;
/* 2658 */     int[] offsetStack = new int[stackSize];
/* 2659 */     int[] lengthStack = new int[stackSize];
/* 2660 */     int[] levelStack = new int[stackSize];
/* 2661 */     offsetStack[stackPos] = from;
/* 2662 */     lengthStack[stackPos] = to - from;
/* 2663 */     levelStack[stackPos++] = 0;
/* 2664 */     int[] count = new int[256];
/* 2665 */     int[] pos = new int[256];
/* 2666 */     double[] t = new double[layers];
/* 2667 */     while (stackPos > 0) {
/* 2668 */       int first = offsetStack[--stackPos];
/* 2669 */       int length = lengthStack[stackPos];
/* 2670 */       int level = levelStack[stackPos];
/* 2671 */       int signMask = (level % 8 == 0) ? 128 : 0;
/* 2672 */       double[] k = a[level / 8];
/* 2673 */       int shift = (7 - level % 8) * 8;
/*      */ 
/*      */ 
/*      */ 
/*      */       
/* 2678 */       for (int i = first + length; i-- != first; count[(int)(fixDouble(k[i]) >>> shift & 0xFFL ^ signMask)] = count[(int)(fixDouble(k[i]) >>> shift & 0xFFL ^ signMask)] + 1);
/*      */       
/* 2680 */       int lastUsed = -1;
/* 2681 */       for (int j = 0, n = first; j < 256; j++) {
/* 2682 */         if (count[j] != 0) lastUsed = j; 
/* 2683 */         pos[j] = n += count[j];
/*      */       } 
/* 2685 */       int end = first + length - count[lastUsed];
/*      */       
/* 2687 */       for (int m = first, c = -1; m <= end; m += count[c], count[c] = 0) {
/* 2688 */         int i1; for (i1 = layers; i1-- != 0; t[i1] = a[i1][m]);
/* 2689 */         c = (int)(fixDouble(k[m]) >>> shift & 0xFFL ^ signMask);
/* 2690 */         if (m < end) {
/* 2691 */           int d; for (pos[c] = pos[c] - 1; (d = pos[c] - 1) > m; ) {
/* 2692 */             c = (int)(fixDouble(k[d]) >>> shift & 0xFFL ^ signMask);
/* 2693 */             for (i1 = layers; i1-- != 0; ) {
/* 2694 */               double u = t[i1];
/* 2695 */               t[i1] = a[i1][d];
/* 2696 */               a[i1][d] = u;
/*      */             } 
/*      */           } 
/* 2699 */           for (i1 = layers; i1-- != 0; a[i1][m] = t[i1]);
/*      */         } 
/* 2701 */         if (level < maxLevel && count[c] > 1) {
/* 2702 */           if (count[c] < 64) { selectionSort(a, m, m + count[c], level + 1); }
/*      */           else
/* 2704 */           { offsetStack[stackPos] = m;
/* 2705 */             lengthStack[stackPos] = count[c];
/* 2706 */             levelStack[stackPos++] = level + 1; }
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
/*      */   public static double[] shuffle(double[] a, int from, int to, Random random) {
/* 2723 */     for (int i = to - from; i-- != 0; ) {
/* 2724 */       int p = random.nextInt(i + 1);
/* 2725 */       double t = a[from + i];
/* 2726 */       a[from + i] = a[from + p];
/* 2727 */       a[from + p] = t;
/*      */     } 
/* 2729 */     return a;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static double[] shuffle(double[] a, Random random) {
/* 2740 */     for (int i = a.length; i-- != 0; ) {
/* 2741 */       int p = random.nextInt(i + 1);
/* 2742 */       double t = a[i];
/* 2743 */       a[i] = a[p];
/* 2744 */       a[p] = t;
/*      */     } 
/* 2746 */     return a;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static double[] reverse(double[] a) {
/* 2756 */     int length = a.length;
/* 2757 */     for (int i = length / 2; i-- != 0; ) {
/* 2758 */       double t = a[length - i - 1];
/* 2759 */       a[length - i - 1] = a[i];
/* 2760 */       a[i] = t;
/*      */     } 
/* 2762 */     return a;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static double[] reverse(double[] a, int from, int to) {
/* 2774 */     int length = to - from;
/* 2775 */     for (int i = length / 2; i-- != 0; ) {
/* 2776 */       double t = a[from + length - i - 1];
/* 2777 */       a[from + length - i - 1] = a[from + i];
/* 2778 */       a[from + i] = t;
/*      */     } 
/* 2780 */     return a;
/*      */   }
/*      */   
/*      */   private static final class ArrayHashStrategy implements Hash.Strategy<double[]>, Serializable {
/*      */     private static final long serialVersionUID = -7046029254386353129L;
/*      */     
/*      */     private ArrayHashStrategy() {}
/*      */     
/*      */     public int hashCode(double[] o) {
/* 2789 */       return Arrays.hashCode(o);
/*      */     }
/*      */ 
/*      */     
/*      */     public boolean equals(double[] a, double[] b) {
/* 2794 */       return Arrays.equals(a, b);
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
/* 2806 */   public static final Hash.Strategy<double[]> HASH_STRATEGY = new ArrayHashStrategy();
/*      */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\i\\unimi\dsi\fastutil\doubles\DoubleArrays.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */