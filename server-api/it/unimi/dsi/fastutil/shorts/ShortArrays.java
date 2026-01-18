/*      */ package it.unimi.dsi.fastutil.shorts;
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
/*      */ public final class ShortArrays
/*      */ {
/*  105 */   public static final short[] EMPTY_ARRAY = new short[0];
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*  113 */   public static final short[] DEFAULT_EMPTY_ARRAY = new short[0]; private static final int QUICKSORT_NO_REC = 16;
/*      */   private static final int PARALLEL_QUICKSORT_NO_FORK = 8192;
/*      */   private static final int QUICKSORT_MEDIAN_OF_9 = 128;
/*      */   private static final int MERGESORT_NO_REC = 16;
/*      */   private static final int DIGIT_BITS = 8;
/*      */   private static final int DIGIT_MASK = 255;
/*      */   private static final int DIGITS_PER_ELEMENT = 2;
/*      */   private static final int RADIXSORT_NO_REC = 1024;
/*      */   private static final int RADIXSORT_NO_REC_SMALL = 64;
/*      */   private static final int PARALLEL_RADIXSORT_NO_FORK = 1024;
/*      */   static final int RADIX_SORT_MIN_THRESHOLD = 1000;
/*      */   
/*      */   public static short[] forceCapacity(short[] array, int length, int preserve) {
/*  126 */     short[] t = new short[length];
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
/*      */   public static short[] ensureCapacity(short[] array, int length) {
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
/*      */   public static short[] ensureCapacity(short[] array, int length, int preserve) {
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
/*      */   public static short[] grow(short[] array, int length) {
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
/*      */   public static short[] grow(short[] array, int length, int preserve) {
/*  200 */     if (length > array.length) {
/*  201 */       int newLength = (int)Math.max(Math.min(array.length + (array.length >> 1), 2147483639L), length);
/*  202 */       short[] t = new short[newLength];
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
/*      */   public static short[] trim(short[] array, int length) {
/*  220 */     if (length >= array.length) return array; 
/*  221 */     short[] t = (length == 0) ? EMPTY_ARRAY : new short[length];
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
/*      */   public static short[] setLength(short[] array, int length) {
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
/*      */   public static short[] copy(short[] array, int offset, int length) {
/*  254 */     ensureOffsetLength(array, offset, length);
/*  255 */     short[] a = (length == 0) ? EMPTY_ARRAY : new short[length];
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
/*      */   public static short[] copy(short[] array) {
/*  267 */     return (short[])array.clone();
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
/*      */   public static void fill(short[] array, short value) {
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
/*      */   public static void fill(short[] array, int from, int to, short value) {
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
/*      */   public static boolean equals(short[] a1, short[] a2) {
/*  310 */     int i = a1.length;
/*  311 */     if (i != a2.length) return false; 
/*  312 */     while (i-- != 0) { if (a1[i] != a2[i]) return false;  }
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
/*      */   public static void ensureFromTo(short[] a, int from, int to) {
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
/*      */   public static void ensureOffsetLength(short[] a, int offset, int length) {
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
/*      */   public static void ensureSameLength(short[] a, short[] b) {
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
/*      */   public static void swap(short[] x, int a, int b) {
/*  390 */     short t = x[a];
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
/*      */   public static void swap(short[] x, int a, int b, int n) {
/*  404 */     for (int i = 0; i < n; ) { swap(x, a, b); i++; a++; b++; }
/*      */   
/*      */   }
/*      */   private static int med3(short[] x, int a, int b, int c, ShortComparator comp) {
/*  408 */     int ab = comp.compare(x[a], x[b]);
/*  409 */     int ac = comp.compare(x[a], x[c]);
/*  410 */     int bc = comp.compare(x[b], x[c]);
/*  411 */     return (ab < 0) ? ((bc < 0) ? b : ((ac < 0) ? c : a)) : ((bc > 0) ? b : ((ac > 0) ? c : a));
/*      */   }
/*      */   
/*      */   private static void selectionSort(short[] a, int from, int to, ShortComparator comp) {
/*  415 */     for (int i = from; i < to - 1; i++) {
/*  416 */       int m = i;
/*  417 */       for (int j = i + 1; j < to; ) { if (comp.compare(a[j], a[m]) < 0) m = j;  j++; }
/*  418 */        if (m != i) {
/*  419 */         short u = a[i];
/*  420 */         a[i] = a[m];
/*  421 */         a[m] = u;
/*      */       } 
/*      */     } 
/*      */   }
/*      */   
/*      */   private static void insertionSort(short[] a, int from, int to, ShortComparator comp) {
/*  427 */     for (int i = from; ++i < to; ) {
/*  428 */       short t = a[i];
/*  429 */       int j = i; short u;
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
/*      */   public static void quickSort(short[] x, int from, int to, ShortComparator comp) {
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
/*  478 */     short v = x[m];
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
/*      */   public static void quickSort(short[] x, ShortComparator comp) {
/*  522 */     quickSort(x, 0, x.length, comp);
/*      */   }
/*      */   
/*      */   protected static class ForkJoinQuickSortComp extends RecursiveAction {
/*      */     private static final long serialVersionUID = 1L;
/*      */     private final int from;
/*      */     private final int to;
/*      */     private final short[] x;
/*      */     private final ShortComparator comp;
/*      */     
/*      */     public ForkJoinQuickSortComp(short[] x, int from, int to, ShortComparator comp) {
/*  533 */       this.from = from;
/*  534 */       this.to = to;
/*  535 */       this.x = x;
/*  536 */       this.comp = comp;
/*      */     }
/*      */ 
/*      */     
/*      */     protected void compute() {
/*  541 */       short[] x = this.x;
/*  542 */       int len = this.to - this.from;
/*  543 */       if (len < 8192) {
/*  544 */         ShortArrays.quickSort(x, this.from, this.to, this.comp);
/*      */         
/*      */         return;
/*      */       } 
/*  548 */       int m = this.from + len / 2;
/*  549 */       int l = this.from;
/*  550 */       int n = this.to - 1;
/*  551 */       int s = len / 8;
/*  552 */       l = ShortArrays.med3(x, l, l + s, l + 2 * s, this.comp);
/*  553 */       m = ShortArrays.med3(x, m - s, m, m + s, this.comp);
/*  554 */       n = ShortArrays.med3(x, n - 2 * s, n - s, n, this.comp);
/*  555 */       m = ShortArrays.med3(x, l, m, n, this.comp);
/*  556 */       short v = x[m];
/*      */       
/*  558 */       int a = this.from, b = a, c = this.to - 1, d = c;
/*      */       while (true) {
/*      */         int comparison;
/*  561 */         if (b <= c && (comparison = this.comp.compare(x[b], v)) <= 0) {
/*  562 */           if (comparison == 0) ShortArrays.swap(x, a++, b); 
/*  563 */           b++; continue;
/*      */         } 
/*  565 */         while (c >= b && (comparison = this.comp.compare(x[c], v)) >= 0) {
/*  566 */           if (comparison == 0) ShortArrays.swap(x, c, d--); 
/*  567 */           c--;
/*      */         } 
/*  569 */         if (b > c)
/*  570 */           break;  ShortArrays.swap(x, b++, c--);
/*      */       } 
/*      */ 
/*      */       
/*  574 */       s = Math.min(a - this.from, b - a);
/*  575 */       ShortArrays.swap(x, this.from, b - s, s);
/*  576 */       s = Math.min(d - c, this.to - d - 1);
/*  577 */       ShortArrays.swap(x, b, this.to - s, s);
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
/*      */   public static void parallelQuickSort(short[] x, int from, int to, ShortComparator comp) {
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
/*      */   public static void parallelQuickSort(short[] x, ShortComparator comp) {
/*  622 */     parallelQuickSort(x, 0, x.length, comp);
/*      */   }
/*      */   
/*      */   private static int med3(short[] x, int a, int b, int c) {
/*  626 */     int ab = Short.compare(x[a], x[b]);
/*  627 */     int ac = Short.compare(x[a], x[c]);
/*  628 */     int bc = Short.compare(x[b], x[c]);
/*  629 */     return (ab < 0) ? ((bc < 0) ? b : ((ac < 0) ? c : a)) : ((bc > 0) ? b : ((ac > 0) ? c : a));
/*      */   }
/*      */   
/*      */   private static void selectionSort(short[] a, int from, int to) {
/*  633 */     for (int i = from; i < to - 1; i++) {
/*  634 */       int m = i;
/*  635 */       for (int j = i + 1; j < to; ) { if (a[j] < a[m]) m = j;  j++; }
/*  636 */        if (m != i) {
/*  637 */         short u = a[i];
/*  638 */         a[i] = a[m];
/*  639 */         a[m] = u;
/*      */       } 
/*      */     } 
/*      */   }
/*      */   
/*      */   private static void insertionSort(short[] a, int from, int to) {
/*  645 */     for (int i = from; ++i < to; ) {
/*  646 */       short t = a[i];
/*  647 */       int j = i; short u;
/*  648 */       for (u = a[j - 1]; t < u; u = a[--j - 1]) {
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
/*      */   public static void quickSort(short[] x, int from, int to) {
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
/*  694 */     short v = x[m];
/*      */     
/*  696 */     int a = from, b = a, c = to - 1, d = c;
/*      */     while (true) {
/*      */       int comparison;
/*  699 */       if (b <= c && (comparison = Short.compare(x[b], v)) <= 0) {
/*  700 */         if (comparison == 0) swap(x, a++, b); 
/*  701 */         b++; continue;
/*      */       } 
/*  703 */       while (c >= b && (comparison = Short.compare(x[c], v)) >= 0) {
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
/*      */   public static void quickSort(short[] x) {
/*  737 */     quickSort(x, 0, x.length);
/*      */   }
/*      */   
/*      */   protected static class ForkJoinQuickSort extends RecursiveAction {
/*      */     private static final long serialVersionUID = 1L;
/*      */     private final int from;
/*      */     private final int to;
/*      */     private final short[] x;
/*      */     
/*      */     public ForkJoinQuickSort(short[] x, int from, int to) {
/*  747 */       this.from = from;
/*  748 */       this.to = to;
/*  749 */       this.x = x;
/*      */     }
/*      */ 
/*      */ 
/*      */     
/*      */     protected void compute() {
/*  755 */       short[] x = this.x;
/*  756 */       int len = this.to - this.from;
/*  757 */       if (len < 8192) {
/*  758 */         ShortArrays.quickSort(x, this.from, this.to);
/*      */         
/*      */         return;
/*      */       } 
/*  762 */       int m = this.from + len / 2;
/*  763 */       int l = this.from;
/*  764 */       int n = this.to - 1;
/*  765 */       int s = len / 8;
/*  766 */       l = ShortArrays.med3(x, l, l + s, l + 2 * s);
/*  767 */       m = ShortArrays.med3(x, m - s, m, m + s);
/*  768 */       n = ShortArrays.med3(x, n - 2 * s, n - s, n);
/*  769 */       m = ShortArrays.med3(x, l, m, n);
/*  770 */       short v = x[m];
/*      */       
/*  772 */       int a = this.from, b = a, c = this.to - 1, d = c;
/*      */       while (true) {
/*      */         int comparison;
/*  775 */         if (b <= c && (comparison = Short.compare(x[b], v)) <= 0) {
/*  776 */           if (comparison == 0) ShortArrays.swap(x, a++, b); 
/*  777 */           b++; continue;
/*      */         } 
/*  779 */         while (c >= b && (comparison = Short.compare(x[c], v)) >= 0) {
/*  780 */           if (comparison == 0) ShortArrays.swap(x, c, d--); 
/*  781 */           c--;
/*      */         } 
/*  783 */         if (b > c)
/*  784 */           break;  ShortArrays.swap(x, b++, c--);
/*      */       } 
/*      */ 
/*      */       
/*  788 */       s = Math.min(a - this.from, b - a);
/*  789 */       ShortArrays.swap(x, this.from, b - s, s);
/*  790 */       s = Math.min(d - c, this.to - d - 1);
/*  791 */       ShortArrays.swap(x, b, this.to - s, s);
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
/*      */   public static void parallelQuickSort(short[] x, int from, int to) {
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
/*      */   public static void parallelQuickSort(short[] x) {
/*  834 */     parallelQuickSort(x, 0, x.length);
/*      */   }
/*      */   
/*      */   private static int med3Indirect(int[] perm, short[] x, int a, int b, int c) {
/*  838 */     short aa = x[perm[a]];
/*  839 */     short bb = x[perm[b]];
/*  840 */     short cc = x[perm[c]];
/*  841 */     int ab = Short.compare(aa, bb);
/*  842 */     int ac = Short.compare(aa, cc);
/*  843 */     int bc = Short.compare(bb, cc);
/*  844 */     return (ab < 0) ? ((bc < 0) ? b : ((ac < 0) ? c : a)) : ((bc > 0) ? b : ((ac > 0) ? c : a));
/*      */   }
/*      */   
/*      */   private static void insertionSortIndirect(int[] perm, short[] a, int from, int to) {
/*  848 */     for (int i = from; ++i < to; ) {
/*  849 */       int t = perm[i];
/*  850 */       int j = i; int u;
/*  851 */       for (u = perm[j - 1]; a[t] < a[u]; u = perm[--j - 1]) {
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
/*      */   public static void quickSortIndirect(int[] perm, short[] x, int from, int to) {
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
/*  904 */     short v = x[perm[m]];
/*      */     
/*  906 */     int a = from, b = a, c = to - 1, d = c;
/*      */     while (true) {
/*      */       int comparison;
/*  909 */       if (b <= c && (comparison = Short.compare(x[perm[b]], v)) <= 0) {
/*  910 */         if (comparison == 0) IntArrays.swap(perm, a++, b); 
/*  911 */         b++; continue;
/*      */       } 
/*  913 */       while (c >= b && (comparison = Short.compare(x[perm[c]], v)) >= 0) {
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
/*      */   public static void quickSortIndirect(int[] perm, short[] x) {
/*  952 */     quickSortIndirect(perm, x, 0, x.length);
/*      */   }
/*      */   
/*      */   protected static class ForkJoinQuickSortIndirect extends RecursiveAction {
/*      */     private static final long serialVersionUID = 1L;
/*      */     private final int from;
/*      */     private final int to;
/*      */     private final int[] perm;
/*      */     private final short[] x;
/*      */     
/*      */     public ForkJoinQuickSortIndirect(int[] perm, short[] x, int from, int to) {
/*  963 */       this.from = from;
/*  964 */       this.to = to;
/*  965 */       this.x = x;
/*  966 */       this.perm = perm;
/*      */     }
/*      */ 
/*      */ 
/*      */     
/*      */     protected void compute() {
/*  972 */       short[] x = this.x;
/*  973 */       int len = this.to - this.from;
/*  974 */       if (len < 8192) {
/*  975 */         ShortArrays.quickSortIndirect(this.perm, x, this.from, this.to);
/*      */         
/*      */         return;
/*      */       } 
/*  979 */       int m = this.from + len / 2;
/*  980 */       int l = this.from;
/*  981 */       int n = this.to - 1;
/*  982 */       int s = len / 8;
/*  983 */       l = ShortArrays.med3Indirect(this.perm, x, l, l + s, l + 2 * s);
/*  984 */       m = ShortArrays.med3Indirect(this.perm, x, m - s, m, m + s);
/*  985 */       n = ShortArrays.med3Indirect(this.perm, x, n - 2 * s, n - s, n);
/*  986 */       m = ShortArrays.med3Indirect(this.perm, x, l, m, n);
/*  987 */       short v = x[this.perm[m]];
/*      */       
/*  989 */       int a = this.from, b = a, c = this.to - 1, d = c;
/*      */       while (true) {
/*      */         int comparison;
/*  992 */         if (b <= c && (comparison = Short.compare(x[this.perm[b]], v)) <= 0) {
/*  993 */           if (comparison == 0) IntArrays.swap(this.perm, a++, b); 
/*  994 */           b++; continue;
/*      */         } 
/*  996 */         while (c >= b && (comparison = Short.compare(x[this.perm[c]], v)) >= 0) {
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
/*      */   public static void parallelQuickSortIndirect(int[] perm, short[] x, int from, int to) {
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
/*      */   public static void parallelQuickSortIndirect(int[] perm, short[] x) {
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
/*      */   public static void stabilize(int[] perm, short[] x, int from, int to) {
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
/*      */   public static void stabilize(int[] perm, short[] x) {
/* 1122 */     stabilize(perm, x, 0, perm.length);
/*      */   }
/*      */ 
/*      */   
/*      */   private static int med3(short[] x, short[] y, int a, int b, int c) {
/* 1127 */     int t, ab = ((t = Short.compare(x[a], x[b])) == 0) ? Short.compare(y[a], y[b]) : t;
/* 1128 */     int ac = ((t = Short.compare(x[a], x[c])) == 0) ? Short.compare(y[a], y[c]) : t;
/* 1129 */     int bc = ((t = Short.compare(x[b], x[c])) == 0) ? Short.compare(y[b], y[c]) : t;
/* 1130 */     return (ab < 0) ? ((bc < 0) ? b : ((ac < 0) ? c : a)) : ((bc > 0) ? b : ((ac > 0) ? c : a));
/*      */   }
/*      */   
/*      */   private static void swap(short[] x, short[] y, int a, int b) {
/* 1134 */     short t = x[a];
/* 1135 */     short u = y[a];
/* 1136 */     x[a] = x[b];
/* 1137 */     y[a] = y[b];
/* 1138 */     x[b] = t;
/* 1139 */     y[b] = u;
/*      */   }
/*      */   
/*      */   private static void swap(short[] x, short[] y, int a, int b, int n) {
/* 1143 */     for (int i = 0; i < n; ) { swap(x, y, a, b); i++; a++; b++; }
/*      */   
/*      */   }
/*      */   private static void selectionSort(short[] a, short[] b, int from, int to) {
/* 1147 */     for (int i = from; i < to - 1; i++) {
/* 1148 */       int m = i;
/* 1149 */       for (int j = i + 1; j < to; ) { int u; if ((u = Short.compare(a[j], a[m])) < 0 || (u == 0 && b[j] < b[m])) m = j;  j++; }
/* 1150 */        if (m != i) {
/* 1151 */         short t = a[i];
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
/*      */   public static void quickSort(short[] x, short[] y, int from, int to) {
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
/* 1199 */     short v = x[m], w = y[m];
/*      */     
/* 1201 */     int a = from, b = a, c = to - 1, d = c;
/*      */     
/*      */     while (true) {
/* 1204 */       if (b <= c) { int comparison; int t; if ((comparison = ((t = Short.compare(x[b], v)) == 0) ? Short.compare(y[b], w) : t) <= 0) {
/* 1205 */           if (comparison == 0) swap(x, y, a++, b); 
/* 1206 */           b++; continue;
/*      */         }  }
/* 1208 */        while (c >= b) { int comparison; int t; if ((comparison = ((t = Short.compare(x[c], v)) == 0) ? Short.compare(y[c], w) : t) >= 0) {
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
/*      */   public static void quickSort(short[] x, short[] y) {
/* 1244 */     ensureSameLength(x, y);
/* 1245 */     quickSort(x, y, 0, x.length);
/*      */   }
/*      */   
/*      */   protected static class ForkJoinQuickSort2 extends RecursiveAction { private static final long serialVersionUID = 1L;
/*      */     private final int from;
/*      */     private final int to;
/*      */     private final short[] x;
/*      */     private final short[] y;
/*      */     
/*      */     public ForkJoinQuickSort2(short[] x, short[] y, int from, int to) {
/* 1255 */       this.from = from;
/* 1256 */       this.to = to;
/* 1257 */       this.x = x;
/* 1258 */       this.y = y;
/*      */     }
/*      */ 
/*      */ 
/*      */     
/*      */     protected void compute() {
/* 1264 */       short[] x = this.x;
/* 1265 */       short[] y = this.y;
/* 1266 */       int len = this.to - this.from;
/* 1267 */       if (len < 8192) {
/* 1268 */         ShortArrays.quickSort(x, y, this.from, this.to);
/*      */         
/*      */         return;
/*      */       } 
/* 1272 */       int m = this.from + len / 2;
/* 1273 */       int l = this.from;
/* 1274 */       int n = this.to - 1;
/* 1275 */       int s = len / 8;
/* 1276 */       l = ShortArrays.med3(x, y, l, l + s, l + 2 * s);
/* 1277 */       m = ShortArrays.med3(x, y, m - s, m, m + s);
/* 1278 */       n = ShortArrays.med3(x, y, n - 2 * s, n - s, n);
/* 1279 */       m = ShortArrays.med3(x, y, l, m, n);
/* 1280 */       short v = x[m], w = y[m];
/*      */       
/* 1282 */       int a = this.from, b = a, c = this.to - 1, d = c;
/*      */       
/*      */       while (true) {
/* 1285 */         if (b <= c) { int comparison; int i; if ((comparison = ((i = Short.compare(x[b], v)) == 0) ? Short.compare(y[b], w) : i) <= 0) {
/* 1286 */             if (comparison == 0) ShortArrays.swap(x, y, a++, b); 
/* 1287 */             b++; continue;
/*      */           }  }
/* 1289 */          while (c >= b) { int comparison; int i; if ((comparison = ((i = Short.compare(x[c], v)) == 0) ? Short.compare(y[c], w) : i) >= 0) {
/* 1290 */             if (comparison == 0) ShortArrays.swap(x, y, c, d--); 
/* 1291 */             c--;
/*      */           }  }
/* 1293 */          if (b > c)
/* 1294 */           break;  ShortArrays.swap(x, y, b++, c--);
/*      */       } 
/*      */ 
/*      */       
/* 1298 */       s = Math.min(a - this.from, b - a);
/* 1299 */       ShortArrays.swap(x, y, this.from, b - s, s);
/* 1300 */       s = Math.min(d - c, this.to - d - 1);
/* 1301 */       ShortArrays.swap(x, y, b, this.to - s, s);
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
/*      */   public static void parallelQuickSort(short[] x, short[] y, int from, int to) {
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
/*      */   public static void parallelQuickSort(short[] x, short[] y) {
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
/*      */   public static void unstableSort(short[] a, int from, int to) {
/* 1375 */     if (to - from >= 1000) {
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
/*      */   public static void unstableSort(short[] a) {
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
/*      */   public static void unstableSort(short[] a, int from, int to, ShortComparator comp) {
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
/*      */   public static void unstableSort(short[] a, ShortComparator comp) {
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
/*      */   public static void mergeSort(short[] a, int from, int to, short[] supp) {
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
/* 1452 */     if (supp[mid - 1] <= supp[mid]) {
/* 1453 */       System.arraycopy(supp, from, a, from, len);
/*      */       
/*      */       return;
/*      */     } 
/* 1457 */     for (int i = from, p = from, q = mid; i < to; i++) {
/* 1458 */       if (q >= to || (p < mid && supp[p] <= supp[q])) { a[i] = supp[p++]; }
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
/*      */   public static void mergeSort(short[] a, int from, int to) {
/* 1475 */     mergeSort(a, from, to, (short[])null);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static void mergeSort(short[] a) {
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
/*      */   public static void mergeSort(short[] a, int from, int to, ShortComparator comp, short[] supp) {
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
/*      */   public static void mergeSort(short[] a, int from, int to, ShortComparator comp) {
/* 1546 */     mergeSort(a, from, to, comp, (short[])null);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static void mergeSort(short[] a, ShortComparator comp) {
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
/*      */   
/*      */   public static void stableSort(short[] a, int from, int to) {
/* 1581 */     unstableSort(a, from, to);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static void stableSort(short[] a) {
/* 1598 */     stableSort(a, 0, a.length);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static void stableSort(short[] a, int from, int to, ShortComparator comp) {
/* 1618 */     mergeSort(a, from, to, comp);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static void stableSort(short[] a, ShortComparator comp) {
/* 1636 */     stableSort(a, 0, a.length, comp);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static int binarySearch(short[] a, int from, int to, short key) {
/* 1660 */     to--;
/* 1661 */     while (from <= to) {
/* 1662 */       int mid = from + to >>> 1;
/* 1663 */       short midVal = a[mid];
/* 1664 */       if (midVal < key) { from = mid + 1; continue; }
/* 1665 */        if (midVal > key) { to = mid - 1; continue; }
/* 1666 */        return mid;
/*      */     } 
/* 1668 */     return -(from + 1);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static int binarySearch(short[] a, short key) {
/* 1688 */     return binarySearch(a, 0, a.length, key);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static int binarySearch(short[] a, int from, int to, short key, ShortComparator c) {
/* 1712 */     to--;
/* 1713 */     while (from <= to) {
/* 1714 */       int mid = from + to >>> 1;
/* 1715 */       short midVal = a[mid];
/* 1716 */       int cmp = c.compare(midVal, key);
/* 1717 */       if (cmp < 0) { from = mid + 1; continue; }
/* 1718 */        if (cmp > 0) { to = mid - 1; continue; }
/* 1719 */        return mid;
/*      */     } 
/* 1721 */     return -(from + 1);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static int binarySearch(short[] a, short key, ShortComparator c) {
/* 1742 */     return binarySearch(a, 0, a.length, key, c);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static void radixSort(short[] a) {
/* 1776 */     radixSort(a, 0, a.length);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static void radixSort(short[] a, int from, int to) {
/* 1795 */     if (to - from < 1024) {
/* 1796 */       quickSort(a, from, to);
/*      */       return;
/*      */     } 
/* 1799 */     int maxLevel = 1;
/* 1800 */     int stackSize = 256;
/* 1801 */     int stackPos = 0;
/* 1802 */     int[] offsetStack = new int[256];
/* 1803 */     int[] lengthStack = new int[256];
/* 1804 */     int[] levelStack = new int[256];
/* 1805 */     offsetStack[stackPos] = from;
/* 1806 */     lengthStack[stackPos] = to - from;
/* 1807 */     levelStack[stackPos++] = 0;
/* 1808 */     int[] count = new int[256];
/* 1809 */     int[] pos = new int[256];
/* 1810 */     while (stackPos > 0) {
/* 1811 */       int first = offsetStack[--stackPos];
/* 1812 */       int length = lengthStack[stackPos];
/* 1813 */       int level = levelStack[stackPos];
/* 1814 */       int signMask = (level % 2 == 0) ? 128 : 0;
/* 1815 */       int shift = (1 - level % 2) * 8;
/*      */ 
/*      */ 
/*      */ 
/*      */       
/* 1820 */       for (int i = first + length; i-- != first; count[a[i] >>> shift & 0xFF ^ signMask] = count[a[i] >>> shift & 0xFF ^ signMask] + 1);
/*      */       
/* 1822 */       int lastUsed = -1;
/* 1823 */       for (int j = 0, p = first; j < 256; j++) {
/* 1824 */         if (count[j] != 0) lastUsed = j; 
/* 1825 */         pos[j] = p += count[j];
/*      */       } 
/* 1827 */       int end = first + length - count[lastUsed];
/*      */       
/* 1829 */       for (int k = first, c = -1; k <= end; k += count[c], count[c] = 0) {
/* 1830 */         short t = a[k];
/* 1831 */         c = t >>> shift & 0xFF ^ signMask;
/* 1832 */         if (k < end) {
/* 1833 */           int d; for (pos[c] = pos[c] - 1; (d = pos[c] - 1) > k; ) {
/* 1834 */             short z = t;
/* 1835 */             t = a[d];
/* 1836 */             a[d] = z;
/* 1837 */             c = t >>> shift & 0xFF ^ signMask;
/*      */           } 
/* 1839 */           a[k] = t;
/*      */         } 
/* 1841 */         if (level < 1 && count[c] > 1)
/* 1842 */           if (count[c] < 1024) { quickSort(a, k, k + count[c]); }
/*      */           else
/* 1844 */           { offsetStack[stackPos] = k;
/* 1845 */             lengthStack[stackPos] = count[c];
/* 1846 */             levelStack[stackPos++] = level + 1; }
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
/* 1857 */       this.offset = offset;
/* 1858 */       this.length = length;
/* 1859 */       this.level = level;
/*      */     }
/*      */ 
/*      */     
/*      */     public String toString() {
/* 1864 */       return "Segment [offset=" + this.offset + ", length=" + this.length + ", level=" + this.level + "]";
/*      */     } }
/*      */ 
/*      */   
/* 1868 */   protected static final Segment POISON_PILL = new Segment(-1, -1, -1);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static void parallelRadixSort(short[] a, int from, int to) {
/* 1883 */     ForkJoinPool pool = getPool();
/* 1884 */     if (to - from < 1024 || pool.getParallelism() == 1) {
/* 1885 */       quickSort(a, from, to);
/*      */       return;
/*      */     } 
/* 1888 */     int maxLevel = 1;
/* 1889 */     LinkedBlockingQueue<Segment> queue = new LinkedBlockingQueue<>();
/* 1890 */     queue.add(new Segment(from, to - from, 0));
/* 1891 */     AtomicInteger queueSize = new AtomicInteger(1);
/* 1892 */     int numberOfThreads = pool.getParallelism();
/* 1893 */     ExecutorCompletionService<Void> executorCompletionService = new ExecutorCompletionService<>(pool);
/* 1894 */     for (int j = numberOfThreads; j-- != 0; executorCompletionService.submit(() -> {
/*      */           int[] count = new int[256]; int[] pos = new int[256]; while (true) {
/*      */             if (queueSize.get() == 0) {
/*      */               int m = numberOfThreads; while (m-- != 0)
/*      */                 queue.add(POISON_PILL); 
/*      */             }  Segment segment = queue.take(); if (segment == POISON_PILL)
/*      */               return null;  int first = segment.offset; int length = segment.length; int level = segment.level; int signMask = (level % 2 == 0) ? 128 : 0; int shift = (1 - level % 2) * 8;
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
/*      */               short t = a[k];
/*      */               c = t >>> shift & 0xFF ^ signMask;
/*      */               if (k < end) {
/*      */                 pos[c] = pos[c] - 1;
/*      */                 int d;
/*      */                 while ((d = pos[c] - 1) > k) {
/*      */                   short z = t;
/*      */                   t = a[d];
/*      */                   a[d] = z;
/*      */                   c = t >>> shift & 0xFF ^ signMask;
/*      */                 } 
/*      */                 a[k] = t;
/*      */               } 
/*      */               if (level < 1 && count[c] > 1)
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
/* 1943 */     Throwable problem = null;
/* 1944 */     for (int i = numberOfThreads; i-- != 0;) { try {
/* 1945 */         executorCompletionService.take().get();
/* 1946 */       } catch (Exception e) {
/* 1947 */         problem = e.getCause();
/*      */       }  }
/* 1949 */      if (problem != null) throw (problem instanceof RuntimeException) ? (RuntimeException)problem : new RuntimeException(problem);
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
/*      */   public static void parallelRadixSort(short[] a) {
/* 1963 */     parallelRadixSort(a, 0, a.length);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static void radixSortIndirect(int[] perm, short[] a, boolean stable) {
/* 1987 */     radixSortIndirect(perm, a, 0, perm.length, stable);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static void radixSortIndirect(int[] perm, short[] a, int from, int to, boolean stable) {
/* 2013 */     if (to - from < 1024) {
/* 2014 */       quickSortIndirect(perm, a, from, to);
/* 2015 */       if (stable) stabilize(perm, a, from, to); 
/*      */       return;
/*      */     } 
/* 2018 */     int maxLevel = 1;
/* 2019 */     int stackSize = 256;
/* 2020 */     int stackPos = 0;
/* 2021 */     int[] offsetStack = new int[256];
/* 2022 */     int[] lengthStack = new int[256];
/* 2023 */     int[] levelStack = new int[256];
/* 2024 */     offsetStack[stackPos] = from;
/* 2025 */     lengthStack[stackPos] = to - from;
/* 2026 */     levelStack[stackPos++] = 0;
/* 2027 */     int[] count = new int[256];
/* 2028 */     int[] pos = new int[256];
/* 2029 */     int[] support = stable ? new int[perm.length] : null;
/* 2030 */     while (stackPos > 0) {
/* 2031 */       int first = offsetStack[--stackPos];
/* 2032 */       int length = lengthStack[stackPos];
/* 2033 */       int level = levelStack[stackPos];
/* 2034 */       int signMask = (level % 2 == 0) ? 128 : 0;
/* 2035 */       int shift = (1 - level % 2) * 8;
/*      */ 
/*      */ 
/*      */ 
/*      */       
/* 2040 */       for (int i = first + length; i-- != first; count[a[perm[i]] >>> shift & 0xFF ^ signMask] = count[a[perm[i]] >>> shift & 0xFF ^ signMask] + 1);
/*      */       
/* 2042 */       int lastUsed = -1; int j, p;
/* 2043 */       for (j = 0, p = stable ? 0 : first; j < 256; j++) {
/* 2044 */         if (count[j] != 0) lastUsed = j; 
/* 2045 */         pos[j] = p += count[j];
/*      */       } 
/* 2047 */       if (stable) {
/* 2048 */         for (j = first + length; j-- != first; ) { pos[a[perm[j]] >>> shift & 0xFF ^ signMask] = pos[a[perm[j]] >>> shift & 0xFF ^ signMask] - 1; support[pos[a[perm[j]] >>> shift & 0xFF ^ signMask] - 1] = perm[j]; }
/* 2049 */          System.arraycopy(support, 0, perm, first, length);
/* 2050 */         for (j = 0, p = first; j <= lastUsed; j++) {
/* 2051 */           if (level < 1 && count[j] > 1) {
/* 2052 */             if (count[j] < 1024) {
/* 2053 */               quickSortIndirect(perm, a, p, p + count[j]);
/* 2054 */               if (stable) stabilize(perm, a, p, p + count[j]); 
/*      */             } else {
/* 2056 */               offsetStack[stackPos] = p;
/* 2057 */               lengthStack[stackPos] = count[j];
/* 2058 */               levelStack[stackPos++] = level + 1;
/*      */             } 
/*      */           }
/* 2061 */           p += count[j];
/*      */         } 
/* 2063 */         Arrays.fill(count, 0); continue;
/*      */       } 
/* 2065 */       int end = first + length - count[lastUsed];
/*      */       
/* 2067 */       for (int k = first, c = -1; k <= end; k += count[c], count[c] = 0) {
/* 2068 */         int t = perm[k];
/* 2069 */         c = a[t] >>> shift & 0xFF ^ signMask;
/* 2070 */         if (k < end) {
/* 2071 */           int d; for (pos[c] = pos[c] - 1; (d = pos[c] - 1) > k; ) {
/* 2072 */             int z = t;
/* 2073 */             t = perm[d];
/* 2074 */             perm[d] = z;
/* 2075 */             c = a[t] >>> shift & 0xFF ^ signMask;
/*      */           } 
/* 2077 */           perm[k] = t;
/*      */         } 
/* 2079 */         if (level < 1 && count[c] > 1) {
/* 2080 */           if (count[c] < 1024) {
/* 2081 */             quickSortIndirect(perm, a, k, k + count[c]);
/* 2082 */             if (stable) stabilize(perm, a, k, k + count[c]); 
/*      */           } else {
/* 2084 */             offsetStack[stackPos] = k;
/* 2085 */             lengthStack[stackPos] = count[c];
/* 2086 */             levelStack[stackPos++] = level + 1;
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
/*      */   public static void parallelRadixSortIndirect(int[] perm, short[] a, int from, int to, boolean stable) {
/* 2114 */     ForkJoinPool pool = getPool();
/* 2115 */     if (to - from < 1024 || pool.getParallelism() == 1) {
/* 2116 */       radixSortIndirect(perm, a, from, to, stable);
/*      */       return;
/*      */     } 
/* 2119 */     int maxLevel = 1;
/* 2120 */     LinkedBlockingQueue<Segment> queue = new LinkedBlockingQueue<>();
/* 2121 */     queue.add(new Segment(from, to - from, 0));
/* 2122 */     AtomicInteger queueSize = new AtomicInteger(1);
/* 2123 */     int numberOfThreads = pool.getParallelism();
/* 2124 */     ExecutorCompletionService<Void> executorCompletionService = new ExecutorCompletionService<>(pool);
/* 2125 */     int[] support = stable ? new int[perm.length] : null;
/* 2126 */     for (int j = numberOfThreads; j-- != 0; executorCompletionService.submit(() -> {
/*      */           int[] count = new int[256]; int[] pos = new int[256]; while (true) {
/*      */             if (queueSize.get() == 0) {
/*      */               int k = numberOfThreads; while (k-- != 0)
/*      */                 queue.add(POISON_PILL); 
/*      */             }  Segment segment = queue.take(); if (segment == POISON_PILL)
/*      */               return null;  int first = segment.offset; int length = segment.length; int level = segment.level; int signMask = (level % 2 == 0) ? 128 : 0; int shift = (1 - level % 2) * 8; int i = first + length; while (i-- != first)
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
/*      */                 if (level < 1 && count[j] > 1)
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
/*      */                 if (level < 1 && count[c] > 1)
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
/* 2191 */     Throwable problem = null;
/* 2192 */     for (int i = numberOfThreads; i-- != 0;) { try {
/* 2193 */         executorCompletionService.take().get();
/* 2194 */       } catch (Exception e) {
/* 2195 */         problem = e.getCause();
/*      */       }  }
/* 2197 */      if (problem != null) throw (problem instanceof RuntimeException) ? (RuntimeException)problem : new RuntimeException(problem);
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
/*      */   public static void parallelRadixSortIndirect(int[] perm, short[] a, boolean stable) {
/* 2218 */     parallelRadixSortIndirect(perm, a, 0, a.length, stable);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static void radixSort(short[] a, short[] b) {
/* 2238 */     ensureSameLength(a, b);
/* 2239 */     radixSort(a, b, 0, a.length);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static void radixSort(short[] a, short[] b, int from, int to) {
/* 2262 */     if (to - from < 1024) {
/* 2263 */       quickSort(a, b, from, to);
/*      */       return;
/*      */     } 
/* 2266 */     int layers = 2;
/* 2267 */     int maxLevel = 3;
/* 2268 */     int stackSize = 766;
/* 2269 */     int stackPos = 0;
/* 2270 */     int[] offsetStack = new int[766];
/* 2271 */     int[] lengthStack = new int[766];
/* 2272 */     int[] levelStack = new int[766];
/* 2273 */     offsetStack[stackPos] = from;
/* 2274 */     lengthStack[stackPos] = to - from;
/* 2275 */     levelStack[stackPos++] = 0;
/* 2276 */     int[] count = new int[256];
/* 2277 */     int[] pos = new int[256];
/* 2278 */     while (stackPos > 0) {
/* 2279 */       int first = offsetStack[--stackPos];
/* 2280 */       int length = lengthStack[stackPos];
/* 2281 */       int level = levelStack[stackPos];
/* 2282 */       int signMask = (level % 2 == 0) ? 128 : 0;
/* 2283 */       short[] k = (level < 2) ? a : b;
/* 2284 */       int shift = (1 - level % 2) * 8;
/*      */ 
/*      */ 
/*      */ 
/*      */       
/* 2289 */       for (int i = first + length; i-- != first; count[k[i] >>> shift & 0xFF ^ signMask] = count[k[i] >>> shift & 0xFF ^ signMask] + 1);
/*      */       
/* 2291 */       int lastUsed = -1;
/* 2292 */       for (int j = 0, p = first; j < 256; j++) {
/* 2293 */         if (count[j] != 0) lastUsed = j; 
/* 2294 */         pos[j] = p += count[j];
/*      */       } 
/* 2296 */       int end = first + length - count[lastUsed];
/*      */       
/* 2298 */       for (int m = first, c = -1; m <= end; m += count[c], count[c] = 0) {
/* 2299 */         short t = a[m];
/* 2300 */         short u = b[m];
/* 2301 */         c = k[m] >>> shift & 0xFF ^ signMask;
/* 2302 */         if (m < end) {
/* 2303 */           int d; for (pos[c] = pos[c] - 1; (d = pos[c] - 1) > m; ) {
/* 2304 */             c = k[d] >>> shift & 0xFF ^ signMask;
/* 2305 */             short z = t;
/* 2306 */             t = a[d];
/* 2307 */             a[d] = z;
/* 2308 */             z = u;
/* 2309 */             u = b[d];
/* 2310 */             b[d] = z;
/*      */           } 
/* 2312 */           a[m] = t;
/* 2313 */           b[m] = u;
/*      */         } 
/* 2315 */         if (level < 3 && count[c] > 1) {
/* 2316 */           if (count[c] < 1024) { quickSort(a, b, m, m + count[c]); }
/*      */           else
/* 2318 */           { offsetStack[stackPos] = m;
/* 2319 */             lengthStack[stackPos] = count[c];
/* 2320 */             levelStack[stackPos++] = level + 1; }
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
/*      */   public static void parallelRadixSort(short[] a, short[] b, int from, int to) {
/* 2347 */     ForkJoinPool pool = getPool();
/* 2348 */     if (to - from < 1024 || pool.getParallelism() == 1) {
/* 2349 */       quickSort(a, b, from, to);
/*      */       return;
/*      */     } 
/* 2352 */     int layers = 2;
/* 2353 */     if (a.length != b.length) throw new IllegalArgumentException("Array size mismatch."); 
/* 2354 */     int maxLevel = 3;
/* 2355 */     LinkedBlockingQueue<Segment> queue = new LinkedBlockingQueue<>();
/* 2356 */     queue.add(new Segment(from, to - from, 0));
/* 2357 */     AtomicInteger queueSize = new AtomicInteger(1);
/* 2358 */     int numberOfThreads = pool.getParallelism();
/* 2359 */     ExecutorCompletionService<Void> executorCompletionService = new ExecutorCompletionService<>(pool);
/* 2360 */     for (int j = numberOfThreads; j-- != 0; executorCompletionService.submit(() -> {
/*      */           int[] count = new int[256]; int[] pos = new int[256]; while (true) {
/*      */             if (queueSize.get() == 0) {
/*      */               int n = numberOfThreads; while (n-- != 0)
/*      */                 queue.add(POISON_PILL); 
/*      */             }  Segment segment = queue.take(); if (segment == POISON_PILL)
/*      */               return null;  int first = segment.offset; int length = segment.length; int level = segment.level; int signMask = (level % 2 == 0) ? 128 : 0; short[] k = (level < 2) ? a : b; int shift = (1 - level % 2) * 8; int i = first + length; while (i-- != first)
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
/*      */               short t = a[m];
/*      */               short u = b[m];
/*      */               c = k[m] >>> shift & 0xFF ^ signMask;
/*      */               if (m < end) {
/*      */                 pos[c] = pos[c] - 1;
/*      */                 int d;
/*      */                 while ((d = pos[c] - 1) > m) {
/*      */                   c = k[d] >>> shift & 0xFF ^ signMask;
/*      */                   short z = t;
/*      */                   short w = u;
/*      */                   t = a[d];
/*      */                   u = b[d];
/*      */                   a[d] = z;
/*      */                   b[d] = w;
/*      */                 } 
/*      */                 a[m] = t;
/*      */                 b[m] = u;
/*      */               } 
/*      */               if (level < 3 && count[c] > 1)
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
/* 2410 */     Throwable problem = null;
/* 2411 */     for (int i = numberOfThreads; i-- != 0;) { try {
/* 2412 */         executorCompletionService.take().get();
/* 2413 */       } catch (Exception e) {
/* 2414 */         problem = e.getCause();
/*      */       }  }
/* 2416 */      if (problem != null) throw (problem instanceof RuntimeException) ? (RuntimeException)problem : new RuntimeException(problem);
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
/*      */   public static void parallelRadixSort(short[] a, short[] b) {
/* 2437 */     ensureSameLength(a, b);
/* 2438 */     parallelRadixSort(a, b, 0, a.length);
/*      */   }
/*      */   
/*      */   private static void insertionSortIndirect(int[] perm, short[] a, short[] b, int from, int to) {
/* 2442 */     for (int i = from; ++i < to; ) {
/* 2443 */       int t = perm[i];
/* 2444 */       int j = i; int u;
/* 2445 */       for (u = perm[j - 1]; a[t] < a[u] || (a[t] == a[u] && b[t] < b[u]); u = perm[--j - 1]) {
/* 2446 */         perm[j] = u;
/* 2447 */         if (from == j - 1) {
/* 2448 */           j--;
/*      */           break;
/*      */         } 
/*      */       } 
/* 2452 */       perm[j] = t;
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
/*      */   public static void radixSortIndirect(int[] perm, short[] a, short[] b, boolean stable) {
/* 2479 */     ensureSameLength(a, b);
/* 2480 */     radixSortIndirect(perm, a, b, 0, a.length, stable);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static void radixSortIndirect(int[] perm, short[] a, short[] b, int from, int to, boolean stable) {
/* 2508 */     if (to - from < 64) {
/* 2509 */       insertionSortIndirect(perm, a, b, from, to);
/*      */       return;
/*      */     } 
/* 2512 */     int layers = 2;
/* 2513 */     int maxLevel = 3;
/* 2514 */     int stackSize = 766;
/* 2515 */     int stackPos = 0;
/* 2516 */     int[] offsetStack = new int[766];
/* 2517 */     int[] lengthStack = new int[766];
/* 2518 */     int[] levelStack = new int[766];
/* 2519 */     offsetStack[stackPos] = from;
/* 2520 */     lengthStack[stackPos] = to - from;
/* 2521 */     levelStack[stackPos++] = 0;
/* 2522 */     int[] count = new int[256];
/* 2523 */     int[] pos = new int[256];
/* 2524 */     int[] support = stable ? new int[perm.length] : null;
/* 2525 */     while (stackPos > 0) {
/* 2526 */       int first = offsetStack[--stackPos];
/* 2527 */       int length = lengthStack[stackPos];
/* 2528 */       int level = levelStack[stackPos];
/* 2529 */       int signMask = (level % 2 == 0) ? 128 : 0;
/* 2530 */       short[] k = (level < 2) ? a : b;
/* 2531 */       int shift = (1 - level % 2) * 8;
/*      */ 
/*      */ 
/*      */ 
/*      */       
/* 2536 */       for (int i = first + length; i-- != first; count[k[perm[i]] >>> shift & 0xFF ^ signMask] = count[k[perm[i]] >>> shift & 0xFF ^ signMask] + 1);
/*      */       
/* 2538 */       int lastUsed = -1; int j, p;
/* 2539 */       for (j = 0, p = stable ? 0 : first; j < 256; j++) {
/* 2540 */         if (count[j] != 0) lastUsed = j; 
/* 2541 */         pos[j] = p += count[j];
/*      */       } 
/* 2543 */       if (stable) {
/* 2544 */         for (j = first + length; j-- != first; ) { pos[k[perm[j]] >>> shift & 0xFF ^ signMask] = pos[k[perm[j]] >>> shift & 0xFF ^ signMask] - 1; support[pos[k[perm[j]] >>> shift & 0xFF ^ signMask] - 1] = perm[j]; }
/* 2545 */          System.arraycopy(support, 0, perm, first, length);
/* 2546 */         for (j = 0, p = first; j < 256; j++) {
/* 2547 */           if (level < 3 && count[j] > 1) {
/* 2548 */             if (count[j] < 64) { insertionSortIndirect(perm, a, b, p, p + count[j]); }
/*      */             else
/* 2550 */             { offsetStack[stackPos] = p;
/* 2551 */               lengthStack[stackPos] = count[j];
/* 2552 */               levelStack[stackPos++] = level + 1; }
/*      */           
/*      */           }
/* 2555 */           p += count[j];
/*      */         } 
/* 2557 */         Arrays.fill(count, 0); continue;
/*      */       } 
/* 2559 */       int end = first + length - count[lastUsed];
/*      */       
/* 2561 */       for (int m = first, c = -1; m <= end; m += count[c], count[c] = 0) {
/* 2562 */         int t = perm[m];
/* 2563 */         c = k[t] >>> shift & 0xFF ^ signMask;
/* 2564 */         if (m < end) {
/* 2565 */           int d; for (pos[c] = pos[c] - 1; (d = pos[c] - 1) > m; ) {
/* 2566 */             int z = t;
/* 2567 */             t = perm[d];
/* 2568 */             perm[d] = z;
/* 2569 */             c = k[t] >>> shift & 0xFF ^ signMask;
/*      */           } 
/* 2571 */           perm[m] = t;
/*      */         } 
/* 2573 */         if (level < 3 && count[c] > 1) {
/* 2574 */           if (count[c] < 64) { insertionSortIndirect(perm, a, b, m, m + count[c]); }
/*      */           else
/* 2576 */           { offsetStack[stackPos] = m;
/* 2577 */             lengthStack[stackPos] = count[c];
/* 2578 */             levelStack[stackPos++] = level + 1; }
/*      */         
/*      */         }
/*      */       } 
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   private static void selectionSort(short[][] a, int from, int to, int level) {
/* 2587 */     int layers = a.length;
/* 2588 */     int firstLayer = level / 2;
/* 2589 */     for (int i = from; i < to - 1; i++) {
/* 2590 */       int m = i;
/* 2591 */       for (int j = i + 1; j < to; j++) {
/* 2592 */         for (int p = firstLayer; p < layers; p++) {
/* 2593 */           if (a[p][j] < a[p][m]) {
/* 2594 */             m = j; break;
/*      */           } 
/* 2596 */           if (a[p][j] > a[p][m])
/*      */             break; 
/*      */         } 
/* 2599 */       }  if (m != i) {
/* 2600 */         for (int p = layers; p-- != 0; ) {
/* 2601 */           short u = a[p][i];
/* 2602 */           a[p][i] = a[p][m];
/* 2603 */           a[p][m] = u;
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
/*      */   public static void radixSort(short[][] a) {
/* 2624 */     radixSort(a, 0, (a[0]).length);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static void radixSort(short[][] a, int from, int to) {
/* 2644 */     if (to - from < 64) {
/* 2645 */       selectionSort(a, from, to, 0);
/*      */       return;
/*      */     } 
/* 2648 */     int layers = a.length;
/* 2649 */     int maxLevel = 2 * layers - 1;
/* 2650 */     int p = layers;
/* 2651 */     for (int l = (a[0]).length; p-- != 0;) { if ((a[p]).length != l) throw new IllegalArgumentException("The array of index " + p + " has not the same length of the array of index 0.");  }
/* 2652 */      int stackSize = 255 * (layers * 2 - 1) + 1;
/* 2653 */     int stackPos = 0;
/* 2654 */     int[] offsetStack = new int[stackSize];
/* 2655 */     int[] lengthStack = new int[stackSize];
/* 2656 */     int[] levelStack = new int[stackSize];
/* 2657 */     offsetStack[stackPos] = from;
/* 2658 */     lengthStack[stackPos] = to - from;
/* 2659 */     levelStack[stackPos++] = 0;
/* 2660 */     int[] count = new int[256];
/* 2661 */     int[] pos = new int[256];
/* 2662 */     short[] t = new short[layers];
/* 2663 */     while (stackPos > 0) {
/* 2664 */       int first = offsetStack[--stackPos];
/* 2665 */       int length = lengthStack[stackPos];
/* 2666 */       int level = levelStack[stackPos];
/* 2667 */       int signMask = (level % 2 == 0) ? 128 : 0;
/* 2668 */       short[] k = a[level / 2];
/* 2669 */       int shift = (1 - level % 2) * 8;
/*      */ 
/*      */ 
/*      */ 
/*      */       
/* 2674 */       for (int i = first + length; i-- != first; count[k[i] >>> shift & 0xFF ^ signMask] = count[k[i] >>> shift & 0xFF ^ signMask] + 1);
/*      */       
/* 2676 */       int lastUsed = -1;
/* 2677 */       for (int j = 0, n = first; j < 256; j++) {
/* 2678 */         if (count[j] != 0) lastUsed = j; 
/* 2679 */         pos[j] = n += count[j];
/*      */       } 
/* 2681 */       int end = first + length - count[lastUsed];
/*      */       
/* 2683 */       for (int m = first, c = -1; m <= end; m += count[c], count[c] = 0) {
/* 2684 */         int i1; for (i1 = layers; i1-- != 0; t[i1] = a[i1][m]);
/* 2685 */         c = k[m] >>> shift & 0xFF ^ signMask;
/* 2686 */         if (m < end) {
/* 2687 */           int d; for (pos[c] = pos[c] - 1; (d = pos[c] - 1) > m; ) {
/* 2688 */             c = k[d] >>> shift & 0xFF ^ signMask;
/* 2689 */             for (i1 = layers; i1-- != 0; ) {
/* 2690 */               short u = t[i1];
/* 2691 */               t[i1] = a[i1][d];
/* 2692 */               a[i1][d] = u;
/*      */             } 
/*      */           } 
/* 2695 */           for (i1 = layers; i1-- != 0; a[i1][m] = t[i1]);
/*      */         } 
/* 2697 */         if (level < maxLevel && count[c] > 1) {
/* 2698 */           if (count[c] < 64) { selectionSort(a, m, m + count[c], level + 1); }
/*      */           else
/* 2700 */           { offsetStack[stackPos] = m;
/* 2701 */             lengthStack[stackPos] = count[c];
/* 2702 */             levelStack[stackPos++] = level + 1; }
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
/*      */   public static short[] shuffle(short[] a, int from, int to, Random random) {
/* 2719 */     for (int i = to - from; i-- != 0; ) {
/* 2720 */       int p = random.nextInt(i + 1);
/* 2721 */       short t = a[from + i];
/* 2722 */       a[from + i] = a[from + p];
/* 2723 */       a[from + p] = t;
/*      */     } 
/* 2725 */     return a;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static short[] shuffle(short[] a, Random random) {
/* 2736 */     for (int i = a.length; i-- != 0; ) {
/* 2737 */       int p = random.nextInt(i + 1);
/* 2738 */       short t = a[i];
/* 2739 */       a[i] = a[p];
/* 2740 */       a[p] = t;
/*      */     } 
/* 2742 */     return a;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static short[] reverse(short[] a) {
/* 2752 */     int length = a.length;
/* 2753 */     for (int i = length / 2; i-- != 0; ) {
/* 2754 */       short t = a[length - i - 1];
/* 2755 */       a[length - i - 1] = a[i];
/* 2756 */       a[i] = t;
/*      */     } 
/* 2758 */     return a;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static short[] reverse(short[] a, int from, int to) {
/* 2770 */     int length = to - from;
/* 2771 */     for (int i = length / 2; i-- != 0; ) {
/* 2772 */       short t = a[from + length - i - 1];
/* 2773 */       a[from + length - i - 1] = a[from + i];
/* 2774 */       a[from + i] = t;
/*      */     } 
/* 2776 */     return a;
/*      */   }
/*      */   
/*      */   private static final class ArrayHashStrategy implements Hash.Strategy<short[]>, Serializable {
/*      */     private static final long serialVersionUID = -7046029254386353129L;
/*      */     
/*      */     private ArrayHashStrategy() {}
/*      */     
/*      */     public int hashCode(short[] o) {
/* 2785 */       return Arrays.hashCode(o);
/*      */     }
/*      */ 
/*      */     
/*      */     public boolean equals(short[] a, short[] b) {
/* 2790 */       return Arrays.equals(a, b);
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
/* 2802 */   public static final Hash.Strategy<short[]> HASH_STRATEGY = new ArrayHashStrategy();
/*      */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\i\\unimi\dsi\fastutil\shorts\ShortArrays.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */