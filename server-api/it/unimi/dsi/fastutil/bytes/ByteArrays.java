/*      */ package it.unimi.dsi.fastutil.bytes;
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
/*      */ public final class ByteArrays
/*      */ {
/*  105 */   public static final byte[] EMPTY_ARRAY = new byte[0];
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*  113 */   public static final byte[] DEFAULT_EMPTY_ARRAY = new byte[0]; private static final int QUICKSORT_NO_REC = 16;
/*      */   private static final int PARALLEL_QUICKSORT_NO_FORK = 8192;
/*      */   private static final int QUICKSORT_MEDIAN_OF_9 = 128;
/*      */   private static final int MERGESORT_NO_REC = 16;
/*      */   private static final int DIGIT_BITS = 8;
/*      */   private static final int DIGIT_MASK = 255;
/*      */   private static final int DIGITS_PER_ELEMENT = 1;
/*      */   private static final int RADIXSORT_NO_REC = 1024;
/*      */   private static final int RADIXSORT_NO_REC_SMALL = 64;
/*      */   private static final int PARALLEL_RADIXSORT_NO_FORK = 1024;
/*      */   static final int RADIX_SORT_MIN_THRESHOLD = 1000;
/*      */   
/*      */   public static byte[] forceCapacity(byte[] array, int length, int preserve) {
/*  126 */     byte[] t = new byte[length];
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
/*      */   public static byte[] ensureCapacity(byte[] array, int length) {
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
/*      */   public static byte[] ensureCapacity(byte[] array, int length, int preserve) {
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
/*      */   public static byte[] grow(byte[] array, int length) {
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
/*      */   public static byte[] grow(byte[] array, int length, int preserve) {
/*  200 */     if (length > array.length) {
/*  201 */       int newLength = (int)Math.max(Math.min(array.length + (array.length >> 1), 2147483639L), length);
/*  202 */       byte[] t = new byte[newLength];
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
/*      */   public static byte[] trim(byte[] array, int length) {
/*  220 */     if (length >= array.length) return array; 
/*  221 */     byte[] t = (length == 0) ? EMPTY_ARRAY : new byte[length];
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
/*      */   public static byte[] setLength(byte[] array, int length) {
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
/*      */   public static byte[] copy(byte[] array, int offset, int length) {
/*  254 */     ensureOffsetLength(array, offset, length);
/*  255 */     byte[] a = (length == 0) ? EMPTY_ARRAY : new byte[length];
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
/*      */   public static byte[] copy(byte[] array) {
/*  267 */     return (byte[])array.clone();
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
/*      */   public static void fill(byte[] array, byte value) {
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
/*      */   public static void fill(byte[] array, int from, int to, byte value) {
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
/*      */   public static boolean equals(byte[] a1, byte[] a2) {
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
/*      */   public static void ensureFromTo(byte[] a, int from, int to) {
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
/*      */   public static void ensureOffsetLength(byte[] a, int offset, int length) {
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
/*      */   public static void ensureSameLength(byte[] a, byte[] b) {
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
/*      */   public static void swap(byte[] x, int a, int b) {
/*  390 */     byte t = x[a];
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
/*      */   public static void swap(byte[] x, int a, int b, int n) {
/*  404 */     for (int i = 0; i < n; ) { swap(x, a, b); i++; a++; b++; }
/*      */   
/*      */   }
/*      */   private static int med3(byte[] x, int a, int b, int c, ByteComparator comp) {
/*  408 */     int ab = comp.compare(x[a], x[b]);
/*  409 */     int ac = comp.compare(x[a], x[c]);
/*  410 */     int bc = comp.compare(x[b], x[c]);
/*  411 */     return (ab < 0) ? ((bc < 0) ? b : ((ac < 0) ? c : a)) : ((bc > 0) ? b : ((ac > 0) ? c : a));
/*      */   }
/*      */   
/*      */   private static void selectionSort(byte[] a, int from, int to, ByteComparator comp) {
/*  415 */     for (int i = from; i < to - 1; i++) {
/*  416 */       int m = i;
/*  417 */       for (int j = i + 1; j < to; ) { if (comp.compare(a[j], a[m]) < 0) m = j;  j++; }
/*  418 */        if (m != i) {
/*  419 */         byte u = a[i];
/*  420 */         a[i] = a[m];
/*  421 */         a[m] = u;
/*      */       } 
/*      */     } 
/*      */   }
/*      */   
/*      */   private static void insertionSort(byte[] a, int from, int to, ByteComparator comp) {
/*  427 */     for (int i = from; ++i < to; ) {
/*  428 */       byte t = a[i];
/*  429 */       int j = i; byte u;
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
/*      */   public static void quickSort(byte[] x, int from, int to, ByteComparator comp) {
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
/*  478 */     byte v = x[m];
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
/*      */   public static void quickSort(byte[] x, ByteComparator comp) {
/*  522 */     quickSort(x, 0, x.length, comp);
/*      */   }
/*      */   
/*      */   protected static class ForkJoinQuickSortComp extends RecursiveAction {
/*      */     private static final long serialVersionUID = 1L;
/*      */     private final int from;
/*      */     private final int to;
/*      */     private final byte[] x;
/*      */     private final ByteComparator comp;
/*      */     
/*      */     public ForkJoinQuickSortComp(byte[] x, int from, int to, ByteComparator comp) {
/*  533 */       this.from = from;
/*  534 */       this.to = to;
/*  535 */       this.x = x;
/*  536 */       this.comp = comp;
/*      */     }
/*      */ 
/*      */     
/*      */     protected void compute() {
/*  541 */       byte[] x = this.x;
/*  542 */       int len = this.to - this.from;
/*  543 */       if (len < 8192) {
/*  544 */         ByteArrays.quickSort(x, this.from, this.to, this.comp);
/*      */         
/*      */         return;
/*      */       } 
/*  548 */       int m = this.from + len / 2;
/*  549 */       int l = this.from;
/*  550 */       int n = this.to - 1;
/*  551 */       int s = len / 8;
/*  552 */       l = ByteArrays.med3(x, l, l + s, l + 2 * s, this.comp);
/*  553 */       m = ByteArrays.med3(x, m - s, m, m + s, this.comp);
/*  554 */       n = ByteArrays.med3(x, n - 2 * s, n - s, n, this.comp);
/*  555 */       m = ByteArrays.med3(x, l, m, n, this.comp);
/*  556 */       byte v = x[m];
/*      */       
/*  558 */       int a = this.from, b = a, c = this.to - 1, d = c;
/*      */       while (true) {
/*      */         int comparison;
/*  561 */         if (b <= c && (comparison = this.comp.compare(x[b], v)) <= 0) {
/*  562 */           if (comparison == 0) ByteArrays.swap(x, a++, b); 
/*  563 */           b++; continue;
/*      */         } 
/*  565 */         while (c >= b && (comparison = this.comp.compare(x[c], v)) >= 0) {
/*  566 */           if (comparison == 0) ByteArrays.swap(x, c, d--); 
/*  567 */           c--;
/*      */         } 
/*  569 */         if (b > c)
/*  570 */           break;  ByteArrays.swap(x, b++, c--);
/*      */       } 
/*      */ 
/*      */       
/*  574 */       s = Math.min(a - this.from, b - a);
/*  575 */       ByteArrays.swap(x, this.from, b - s, s);
/*  576 */       s = Math.min(d - c, this.to - d - 1);
/*  577 */       ByteArrays.swap(x, b, this.to - s, s);
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
/*      */   public static void parallelQuickSort(byte[] x, int from, int to, ByteComparator comp) {
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
/*      */   public static void parallelQuickSort(byte[] x, ByteComparator comp) {
/*  622 */     parallelQuickSort(x, 0, x.length, comp);
/*      */   }
/*      */   
/*      */   private static int med3(byte[] x, int a, int b, int c) {
/*  626 */     int ab = Byte.compare(x[a], x[b]);
/*  627 */     int ac = Byte.compare(x[a], x[c]);
/*  628 */     int bc = Byte.compare(x[b], x[c]);
/*  629 */     return (ab < 0) ? ((bc < 0) ? b : ((ac < 0) ? c : a)) : ((bc > 0) ? b : ((ac > 0) ? c : a));
/*      */   }
/*      */   
/*      */   private static void selectionSort(byte[] a, int from, int to) {
/*  633 */     for (int i = from; i < to - 1; i++) {
/*  634 */       int m = i;
/*  635 */       for (int j = i + 1; j < to; ) { if (a[j] < a[m]) m = j;  j++; }
/*  636 */        if (m != i) {
/*  637 */         byte u = a[i];
/*  638 */         a[i] = a[m];
/*  639 */         a[m] = u;
/*      */       } 
/*      */     } 
/*      */   }
/*      */   
/*      */   private static void insertionSort(byte[] a, int from, int to) {
/*  645 */     for (int i = from; ++i < to; ) {
/*  646 */       byte t = a[i];
/*  647 */       int j = i; byte u;
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
/*      */   public static void quickSort(byte[] x, int from, int to) {
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
/*  694 */     byte v = x[m];
/*      */     
/*  696 */     int a = from, b = a, c = to - 1, d = c;
/*      */     while (true) {
/*      */       int comparison;
/*  699 */       if (b <= c && (comparison = Byte.compare(x[b], v)) <= 0) {
/*  700 */         if (comparison == 0) swap(x, a++, b); 
/*  701 */         b++; continue;
/*      */       } 
/*  703 */       while (c >= b && (comparison = Byte.compare(x[c], v)) >= 0) {
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
/*      */   public static void quickSort(byte[] x) {
/*  737 */     quickSort(x, 0, x.length);
/*      */   }
/*      */   
/*      */   protected static class ForkJoinQuickSort extends RecursiveAction {
/*      */     private static final long serialVersionUID = 1L;
/*      */     private final int from;
/*      */     private final int to;
/*      */     private final byte[] x;
/*      */     
/*      */     public ForkJoinQuickSort(byte[] x, int from, int to) {
/*  747 */       this.from = from;
/*  748 */       this.to = to;
/*  749 */       this.x = x;
/*      */     }
/*      */ 
/*      */ 
/*      */     
/*      */     protected void compute() {
/*  755 */       byte[] x = this.x;
/*  756 */       int len = this.to - this.from;
/*  757 */       if (len < 8192) {
/*  758 */         ByteArrays.quickSort(x, this.from, this.to);
/*      */         
/*      */         return;
/*      */       } 
/*  762 */       int m = this.from + len / 2;
/*  763 */       int l = this.from;
/*  764 */       int n = this.to - 1;
/*  765 */       int s = len / 8;
/*  766 */       l = ByteArrays.med3(x, l, l + s, l + 2 * s);
/*  767 */       m = ByteArrays.med3(x, m - s, m, m + s);
/*  768 */       n = ByteArrays.med3(x, n - 2 * s, n - s, n);
/*  769 */       m = ByteArrays.med3(x, l, m, n);
/*  770 */       byte v = x[m];
/*      */       
/*  772 */       int a = this.from, b = a, c = this.to - 1, d = c;
/*      */       while (true) {
/*      */         int comparison;
/*  775 */         if (b <= c && (comparison = Byte.compare(x[b], v)) <= 0) {
/*  776 */           if (comparison == 0) ByteArrays.swap(x, a++, b); 
/*  777 */           b++; continue;
/*      */         } 
/*  779 */         while (c >= b && (comparison = Byte.compare(x[c], v)) >= 0) {
/*  780 */           if (comparison == 0) ByteArrays.swap(x, c, d--); 
/*  781 */           c--;
/*      */         } 
/*  783 */         if (b > c)
/*  784 */           break;  ByteArrays.swap(x, b++, c--);
/*      */       } 
/*      */ 
/*      */       
/*  788 */       s = Math.min(a - this.from, b - a);
/*  789 */       ByteArrays.swap(x, this.from, b - s, s);
/*  790 */       s = Math.min(d - c, this.to - d - 1);
/*  791 */       ByteArrays.swap(x, b, this.to - s, s);
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
/*      */   public static void parallelQuickSort(byte[] x, int from, int to) {
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
/*      */   public static void parallelQuickSort(byte[] x) {
/*  834 */     parallelQuickSort(x, 0, x.length);
/*      */   }
/*      */   
/*      */   private static int med3Indirect(int[] perm, byte[] x, int a, int b, int c) {
/*  838 */     byte aa = x[perm[a]];
/*  839 */     byte bb = x[perm[b]];
/*  840 */     byte cc = x[perm[c]];
/*  841 */     int ab = Byte.compare(aa, bb);
/*  842 */     int ac = Byte.compare(aa, cc);
/*  843 */     int bc = Byte.compare(bb, cc);
/*  844 */     return (ab < 0) ? ((bc < 0) ? b : ((ac < 0) ? c : a)) : ((bc > 0) ? b : ((ac > 0) ? c : a));
/*      */   }
/*      */   
/*      */   private static void insertionSortIndirect(int[] perm, byte[] a, int from, int to) {
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
/*      */   public static void quickSortIndirect(int[] perm, byte[] x, int from, int to) {
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
/*  904 */     byte v = x[perm[m]];
/*      */     
/*  906 */     int a = from, b = a, c = to - 1, d = c;
/*      */     while (true) {
/*      */       int comparison;
/*  909 */       if (b <= c && (comparison = Byte.compare(x[perm[b]], v)) <= 0) {
/*  910 */         if (comparison == 0) IntArrays.swap(perm, a++, b); 
/*  911 */         b++; continue;
/*      */       } 
/*  913 */       while (c >= b && (comparison = Byte.compare(x[perm[c]], v)) >= 0) {
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
/*      */   public static void quickSortIndirect(int[] perm, byte[] x) {
/*  952 */     quickSortIndirect(perm, x, 0, x.length);
/*      */   }
/*      */   
/*      */   protected static class ForkJoinQuickSortIndirect extends RecursiveAction {
/*      */     private static final long serialVersionUID = 1L;
/*      */     private final int from;
/*      */     private final int to;
/*      */     private final int[] perm;
/*      */     private final byte[] x;
/*      */     
/*      */     public ForkJoinQuickSortIndirect(int[] perm, byte[] x, int from, int to) {
/*  963 */       this.from = from;
/*  964 */       this.to = to;
/*  965 */       this.x = x;
/*  966 */       this.perm = perm;
/*      */     }
/*      */ 
/*      */ 
/*      */     
/*      */     protected void compute() {
/*  972 */       byte[] x = this.x;
/*  973 */       int len = this.to - this.from;
/*  974 */       if (len < 8192) {
/*  975 */         ByteArrays.quickSortIndirect(this.perm, x, this.from, this.to);
/*      */         
/*      */         return;
/*      */       } 
/*  979 */       int m = this.from + len / 2;
/*  980 */       int l = this.from;
/*  981 */       int n = this.to - 1;
/*  982 */       int s = len / 8;
/*  983 */       l = ByteArrays.med3Indirect(this.perm, x, l, l + s, l + 2 * s);
/*  984 */       m = ByteArrays.med3Indirect(this.perm, x, m - s, m, m + s);
/*  985 */       n = ByteArrays.med3Indirect(this.perm, x, n - 2 * s, n - s, n);
/*  986 */       m = ByteArrays.med3Indirect(this.perm, x, l, m, n);
/*  987 */       byte v = x[this.perm[m]];
/*      */       
/*  989 */       int a = this.from, b = a, c = this.to - 1, d = c;
/*      */       while (true) {
/*      */         int comparison;
/*  992 */         if (b <= c && (comparison = Byte.compare(x[this.perm[b]], v)) <= 0) {
/*  993 */           if (comparison == 0) IntArrays.swap(this.perm, a++, b); 
/*  994 */           b++; continue;
/*      */         } 
/*  996 */         while (c >= b && (comparison = Byte.compare(x[this.perm[c]], v)) >= 0) {
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
/*      */   public static void parallelQuickSortIndirect(int[] perm, byte[] x, int from, int to) {
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
/*      */   public static void parallelQuickSortIndirect(int[] perm, byte[] x) {
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
/*      */   public static void stabilize(int[] perm, byte[] x, int from, int to) {
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
/*      */   public static void stabilize(int[] perm, byte[] x) {
/* 1122 */     stabilize(perm, x, 0, perm.length);
/*      */   }
/*      */ 
/*      */   
/*      */   private static int med3(byte[] x, byte[] y, int a, int b, int c) {
/* 1127 */     int t, ab = ((t = Byte.compare(x[a], x[b])) == 0) ? Byte.compare(y[a], y[b]) : t;
/* 1128 */     int ac = ((t = Byte.compare(x[a], x[c])) == 0) ? Byte.compare(y[a], y[c]) : t;
/* 1129 */     int bc = ((t = Byte.compare(x[b], x[c])) == 0) ? Byte.compare(y[b], y[c]) : t;
/* 1130 */     return (ab < 0) ? ((bc < 0) ? b : ((ac < 0) ? c : a)) : ((bc > 0) ? b : ((ac > 0) ? c : a));
/*      */   }
/*      */   
/*      */   private static void swap(byte[] x, byte[] y, int a, int b) {
/* 1134 */     byte t = x[a];
/* 1135 */     byte u = y[a];
/* 1136 */     x[a] = x[b];
/* 1137 */     y[a] = y[b];
/* 1138 */     x[b] = t;
/* 1139 */     y[b] = u;
/*      */   }
/*      */   
/*      */   private static void swap(byte[] x, byte[] y, int a, int b, int n) {
/* 1143 */     for (int i = 0; i < n; ) { swap(x, y, a, b); i++; a++; b++; }
/*      */   
/*      */   }
/*      */   private static void selectionSort(byte[] a, byte[] b, int from, int to) {
/* 1147 */     for (int i = from; i < to - 1; i++) {
/* 1148 */       int m = i;
/* 1149 */       for (int j = i + 1; j < to; ) { int u; if ((u = Byte.compare(a[j], a[m])) < 0 || (u == 0 && b[j] < b[m])) m = j;  j++; }
/* 1150 */        if (m != i) {
/* 1151 */         byte t = a[i];
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
/*      */   public static void quickSort(byte[] x, byte[] y, int from, int to) {
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
/* 1199 */     byte v = x[m], w = y[m];
/*      */     
/* 1201 */     int a = from, b = a, c = to - 1, d = c;
/*      */     
/*      */     while (true) {
/* 1204 */       if (b <= c) { int comparison; int t; if ((comparison = ((t = Byte.compare(x[b], v)) == 0) ? Byte.compare(y[b], w) : t) <= 0) {
/* 1205 */           if (comparison == 0) swap(x, y, a++, b); 
/* 1206 */           b++; continue;
/*      */         }  }
/* 1208 */        while (c >= b) { int comparison; int t; if ((comparison = ((t = Byte.compare(x[c], v)) == 0) ? Byte.compare(y[c], w) : t) >= 0) {
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
/*      */   public static void quickSort(byte[] x, byte[] y) {
/* 1244 */     ensureSameLength(x, y);
/* 1245 */     quickSort(x, y, 0, x.length);
/*      */   }
/*      */   
/*      */   protected static class ForkJoinQuickSort2 extends RecursiveAction { private static final long serialVersionUID = 1L;
/*      */     private final int from;
/*      */     private final int to;
/*      */     private final byte[] x;
/*      */     private final byte[] y;
/*      */     
/*      */     public ForkJoinQuickSort2(byte[] x, byte[] y, int from, int to) {
/* 1255 */       this.from = from;
/* 1256 */       this.to = to;
/* 1257 */       this.x = x;
/* 1258 */       this.y = y;
/*      */     }
/*      */ 
/*      */ 
/*      */     
/*      */     protected void compute() {
/* 1264 */       byte[] x = this.x;
/* 1265 */       byte[] y = this.y;
/* 1266 */       int len = this.to - this.from;
/* 1267 */       if (len < 8192) {
/* 1268 */         ByteArrays.quickSort(x, y, this.from, this.to);
/*      */         
/*      */         return;
/*      */       } 
/* 1272 */       int m = this.from + len / 2;
/* 1273 */       int l = this.from;
/* 1274 */       int n = this.to - 1;
/* 1275 */       int s = len / 8;
/* 1276 */       l = ByteArrays.med3(x, y, l, l + s, l + 2 * s);
/* 1277 */       m = ByteArrays.med3(x, y, m - s, m, m + s);
/* 1278 */       n = ByteArrays.med3(x, y, n - 2 * s, n - s, n);
/* 1279 */       m = ByteArrays.med3(x, y, l, m, n);
/* 1280 */       byte v = x[m], w = y[m];
/*      */       
/* 1282 */       int a = this.from, b = a, c = this.to - 1, d = c;
/*      */       
/*      */       while (true) {
/* 1285 */         if (b <= c) { int comparison; int i; if ((comparison = ((i = Byte.compare(x[b], v)) == 0) ? Byte.compare(y[b], w) : i) <= 0) {
/* 1286 */             if (comparison == 0) ByteArrays.swap(x, y, a++, b); 
/* 1287 */             b++; continue;
/*      */           }  }
/* 1289 */          while (c >= b) { int comparison; int i; if ((comparison = ((i = Byte.compare(x[c], v)) == 0) ? Byte.compare(y[c], w) : i) >= 0) {
/* 1290 */             if (comparison == 0) ByteArrays.swap(x, y, c, d--); 
/* 1291 */             c--;
/*      */           }  }
/* 1293 */          if (b > c)
/* 1294 */           break;  ByteArrays.swap(x, y, b++, c--);
/*      */       } 
/*      */ 
/*      */       
/* 1298 */       s = Math.min(a - this.from, b - a);
/* 1299 */       ByteArrays.swap(x, y, this.from, b - s, s);
/* 1300 */       s = Math.min(d - c, this.to - d - 1);
/* 1301 */       ByteArrays.swap(x, y, b, this.to - s, s);
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
/*      */   public static void parallelQuickSort(byte[] x, byte[] y, int from, int to) {
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
/*      */   public static void parallelQuickSort(byte[] x, byte[] y) {
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
/*      */   public static void unstableSort(byte[] a, int from, int to) {
/* 1375 */     Arrays.sort(a, from, to);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static void unstableSort(byte[] a) {
/* 1387 */     unstableSort(a, 0, a.length);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static void unstableSort(byte[] a, int from, int to, ByteComparator comp) {
/* 1402 */     quickSort(a, from, to, comp);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static void unstableSort(byte[] a, ByteComparator comp) {
/* 1415 */     unstableSort(a, 0, a.length, comp);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static void mergeSort(byte[] a, int from, int to, byte[] supp) {
/* 1435 */     int len = to - from;
/*      */     
/* 1437 */     if (len < 16) {
/* 1438 */       insertionSort(a, from, to);
/*      */       return;
/*      */     } 
/* 1441 */     if (supp == null) supp = Arrays.copyOf(a, to);
/*      */     
/* 1443 */     int mid = from + to >>> 1;
/* 1444 */     mergeSort(supp, from, mid, a);
/* 1445 */     mergeSort(supp, mid, to, a);
/*      */ 
/*      */     
/* 1448 */     if (supp[mid - 1] <= supp[mid]) {
/* 1449 */       System.arraycopy(supp, from, a, from, len);
/*      */       
/*      */       return;
/*      */     } 
/* 1453 */     for (int i = from, p = from, q = mid; i < to; i++) {
/* 1454 */       if (q >= to || (p < mid && supp[p] <= supp[q])) { a[i] = supp[p++]; }
/* 1455 */       else { a[i] = supp[q++]; }
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
/*      */   public static void mergeSort(byte[] a, int from, int to) {
/* 1471 */     mergeSort(a, from, to, (byte[])null);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static void mergeSort(byte[] a) {
/* 1484 */     mergeSort(a, 0, a.length);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static void mergeSort(byte[] a, int from, int to, ByteComparator comp, byte[] supp) {
/* 1504 */     int len = to - from;
/*      */     
/* 1506 */     if (len < 16) {
/* 1507 */       insertionSort(a, from, to, comp);
/*      */       return;
/*      */     } 
/* 1510 */     if (supp == null) supp = Arrays.copyOf(a, to);
/*      */     
/* 1512 */     int mid = from + to >>> 1;
/* 1513 */     mergeSort(supp, from, mid, comp, a);
/* 1514 */     mergeSort(supp, mid, to, comp, a);
/*      */ 
/*      */     
/* 1517 */     if (comp.compare(supp[mid - 1], supp[mid]) <= 0) {
/* 1518 */       System.arraycopy(supp, from, a, from, len);
/*      */       
/*      */       return;
/*      */     } 
/* 1522 */     for (int i = from, p = from, q = mid; i < to; i++) {
/* 1523 */       if (q >= to || (p < mid && comp.compare(supp[p], supp[q]) <= 0)) { a[i] = supp[p++]; }
/* 1524 */       else { a[i] = supp[q++]; }
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
/*      */   public static void mergeSort(byte[] a, int from, int to, ByteComparator comp) {
/* 1542 */     mergeSort(a, from, to, comp, (byte[])null);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static void mergeSort(byte[] a, ByteComparator comp) {
/* 1556 */     mergeSort(a, 0, a.length, comp);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static void stableSort(byte[] a, int from, int to) {
/* 1577 */     unstableSort(a, from, to);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static void stableSort(byte[] a) {
/* 1594 */     stableSort(a, 0, a.length);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static void stableSort(byte[] a, int from, int to, ByteComparator comp) {
/* 1614 */     mergeSort(a, from, to, comp);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static void stableSort(byte[] a, ByteComparator comp) {
/* 1632 */     stableSort(a, 0, a.length, comp);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static int binarySearch(byte[] a, int from, int to, byte key) {
/* 1656 */     to--;
/* 1657 */     while (from <= to) {
/* 1658 */       int mid = from + to >>> 1;
/* 1659 */       byte midVal = a[mid];
/* 1660 */       if (midVal < key) { from = mid + 1; continue; }
/* 1661 */        if (midVal > key) { to = mid - 1; continue; }
/* 1662 */        return mid;
/*      */     } 
/* 1664 */     return -(from + 1);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static int binarySearch(byte[] a, byte key) {
/* 1684 */     return binarySearch(a, 0, a.length, key);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static int binarySearch(byte[] a, int from, int to, byte key, ByteComparator c) {
/* 1708 */     to--;
/* 1709 */     while (from <= to) {
/* 1710 */       int mid = from + to >>> 1;
/* 1711 */       byte midVal = a[mid];
/* 1712 */       int cmp = c.compare(midVal, key);
/* 1713 */       if (cmp < 0) { from = mid + 1; continue; }
/* 1714 */        if (cmp > 0) { to = mid - 1; continue; }
/* 1715 */        return mid;
/*      */     } 
/* 1717 */     return -(from + 1);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static int binarySearch(byte[] a, byte key, ByteComparator c) {
/* 1738 */     return binarySearch(a, 0, a.length, key, c);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static void radixSort(byte[] a) {
/* 1772 */     radixSort(a, 0, a.length);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static void radixSort(byte[] a, int from, int to) {
/* 1791 */     if (to - from < 1024) {
/* 1792 */       quickSort(a, from, to);
/*      */       return;
/*      */     } 
/* 1795 */     int maxLevel = 0;
/* 1796 */     int stackSize = 1;
/* 1797 */     int stackPos = 0;
/* 1798 */     int[] offsetStack = new int[1];
/* 1799 */     int[] lengthStack = new int[1];
/* 1800 */     int[] levelStack = new int[1];
/* 1801 */     offsetStack[stackPos] = from;
/* 1802 */     lengthStack[stackPos] = to - from;
/* 1803 */     levelStack[stackPos++] = 0;
/* 1804 */     int[] count = new int[256];
/* 1805 */     int[] pos = new int[256];
/* 1806 */     while (stackPos > 0) {
/* 1807 */       int first = offsetStack[--stackPos];
/* 1808 */       int length = lengthStack[stackPos];
/* 1809 */       int level = levelStack[stackPos];
/* 1810 */       int signMask = (level % 1 == 0) ? 128 : 0;
/* 1811 */       int shift = (0 - level % 1) * 8;
/*      */ 
/*      */ 
/*      */ 
/*      */       
/* 1816 */       for (int i = first + length; i-- != first; count[a[i] >>> shift & 0xFF ^ signMask] = count[a[i] >>> shift & 0xFF ^ signMask] + 1);
/*      */       
/* 1818 */       int lastUsed = -1;
/* 1819 */       for (int j = 0, p = first; j < 256; j++) {
/* 1820 */         if (count[j] != 0) lastUsed = j; 
/* 1821 */         pos[j] = p += count[j];
/*      */       } 
/* 1823 */       int end = first + length - count[lastUsed];
/*      */       
/* 1825 */       for (int k = first, c = -1; k <= end; k += count[c], count[c] = 0) {
/* 1826 */         byte t = a[k];
/* 1827 */         c = t >>> shift & 0xFF ^ signMask;
/* 1828 */         if (k < end) {
/* 1829 */           int d; for (pos[c] = pos[c] - 1; (d = pos[c] - 1) > k; ) {
/* 1830 */             byte z = t;
/* 1831 */             t = a[d];
/* 1832 */             a[d] = z;
/* 1833 */             c = t >>> shift & 0xFF ^ signMask;
/*      */           } 
/* 1835 */           a[k] = t;
/*      */         } 
/* 1837 */         if (level < 0 && count[c] > 1)
/* 1838 */           if (count[c] < 1024) { quickSort(a, k, k + count[c]); }
/*      */           else
/* 1840 */           { offsetStack[stackPos] = k;
/* 1841 */             lengthStack[stackPos] = count[c];
/* 1842 */             levelStack[stackPos++] = level + 1; }
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
/* 1853 */       this.offset = offset;
/* 1854 */       this.length = length;
/* 1855 */       this.level = level;
/*      */     }
/*      */ 
/*      */     
/*      */     public String toString() {
/* 1860 */       return "Segment [offset=" + this.offset + ", length=" + this.length + ", level=" + this.level + "]";
/*      */     } }
/*      */ 
/*      */   
/* 1864 */   protected static final Segment POISON_PILL = new Segment(-1, -1, -1);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static void parallelRadixSort(byte[] a, int from, int to) {
/* 1879 */     ForkJoinPool pool = getPool();
/* 1880 */     if (to - from < 1024 || pool.getParallelism() == 1) {
/* 1881 */       quickSort(a, from, to);
/*      */       return;
/*      */     } 
/* 1884 */     int maxLevel = 0;
/* 1885 */     LinkedBlockingQueue<Segment> queue = new LinkedBlockingQueue<>();
/* 1886 */     queue.add(new Segment(from, to - from, 0));
/* 1887 */     AtomicInteger queueSize = new AtomicInteger(1);
/* 1888 */     int numberOfThreads = pool.getParallelism();
/* 1889 */     ExecutorCompletionService<Void> executorCompletionService = new ExecutorCompletionService<>(pool);
/* 1890 */     for (int j = numberOfThreads; j-- != 0; executorCompletionService.submit(() -> {
/*      */           int[] count = new int[256]; int[] pos = new int[256]; while (true) {
/*      */             if (queueSize.get() == 0) {
/*      */               int m = numberOfThreads; while (m-- != 0)
/*      */                 queue.add(POISON_PILL); 
/*      */             }  Segment segment = queue.take(); if (segment == POISON_PILL)
/*      */               return null;  int first = segment.offset; int length = segment.length; int level = segment.level; int signMask = (level % 1 == 0) ? 128 : 0; int shift = (0 - level % 1) * 8;
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
/*      */               byte t = a[k];
/*      */               c = t >>> shift & 0xFF ^ signMask;
/*      */               if (k < end) {
/*      */                 pos[c] = pos[c] - 1;
/*      */                 int d;
/*      */                 while ((d = pos[c] - 1) > k) {
/*      */                   byte z = t;
/*      */                   t = a[d];
/*      */                   a[d] = z;
/*      */                   c = t >>> shift & 0xFF ^ signMask;
/*      */                 } 
/*      */                 a[k] = t;
/*      */               } 
/*      */               if (level < 0 && count[c] > 1)
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
/* 1939 */     Throwable problem = null;
/* 1940 */     for (int i = numberOfThreads; i-- != 0;) { try {
/* 1941 */         executorCompletionService.take().get();
/* 1942 */       } catch (Exception e) {
/* 1943 */         problem = e.getCause();
/*      */       }  }
/* 1945 */      if (problem != null) throw (problem instanceof RuntimeException) ? (RuntimeException)problem : new RuntimeException(problem);
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
/*      */   public static void parallelRadixSort(byte[] a) {
/* 1959 */     parallelRadixSort(a, 0, a.length);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static void radixSortIndirect(int[] perm, byte[] a, boolean stable) {
/* 1983 */     radixSortIndirect(perm, a, 0, perm.length, stable);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static void radixSortIndirect(int[] perm, byte[] a, int from, int to, boolean stable) {
/* 2009 */     if (to - from < 1024) {
/* 2010 */       quickSortIndirect(perm, a, from, to);
/* 2011 */       if (stable) stabilize(perm, a, from, to); 
/*      */       return;
/*      */     } 
/* 2014 */     int maxLevel = 0;
/* 2015 */     int stackSize = 1;
/* 2016 */     int stackPos = 0;
/* 2017 */     int[] offsetStack = new int[1];
/* 2018 */     int[] lengthStack = new int[1];
/* 2019 */     int[] levelStack = new int[1];
/* 2020 */     offsetStack[stackPos] = from;
/* 2021 */     lengthStack[stackPos] = to - from;
/* 2022 */     levelStack[stackPos++] = 0;
/* 2023 */     int[] count = new int[256];
/* 2024 */     int[] pos = new int[256];
/* 2025 */     int[] support = stable ? new int[perm.length] : null;
/* 2026 */     while (stackPos > 0) {
/* 2027 */       int first = offsetStack[--stackPos];
/* 2028 */       int length = lengthStack[stackPos];
/* 2029 */       int level = levelStack[stackPos];
/* 2030 */       int signMask = (level % 1 == 0) ? 128 : 0;
/* 2031 */       int shift = (0 - level % 1) * 8;
/*      */ 
/*      */ 
/*      */ 
/*      */       
/* 2036 */       for (int i = first + length; i-- != first; count[a[perm[i]] >>> shift & 0xFF ^ signMask] = count[a[perm[i]] >>> shift & 0xFF ^ signMask] + 1);
/*      */       
/* 2038 */       int lastUsed = -1; int j, p;
/* 2039 */       for (j = 0, p = stable ? 0 : first; j < 256; j++) {
/* 2040 */         if (count[j] != 0) lastUsed = j; 
/* 2041 */         pos[j] = p += count[j];
/*      */       } 
/* 2043 */       if (stable) {
/* 2044 */         for (j = first + length; j-- != first; ) { pos[a[perm[j]] >>> shift & 0xFF ^ signMask] = pos[a[perm[j]] >>> shift & 0xFF ^ signMask] - 1; support[pos[a[perm[j]] >>> shift & 0xFF ^ signMask] - 1] = perm[j]; }
/* 2045 */          System.arraycopy(support, 0, perm, first, length);
/* 2046 */         for (j = 0, p = first; j <= lastUsed; j++) {
/* 2047 */           if (level < 0 && count[j] > 1) {
/* 2048 */             if (count[j] < 1024) {
/* 2049 */               quickSortIndirect(perm, a, p, p + count[j]);
/* 2050 */               if (stable) stabilize(perm, a, p, p + count[j]); 
/*      */             } else {
/* 2052 */               offsetStack[stackPos] = p;
/* 2053 */               lengthStack[stackPos] = count[j];
/* 2054 */               levelStack[stackPos++] = level + 1;
/*      */             } 
/*      */           }
/* 2057 */           p += count[j];
/*      */         } 
/* 2059 */         Arrays.fill(count, 0); continue;
/*      */       } 
/* 2061 */       int end = first + length - count[lastUsed];
/*      */       
/* 2063 */       for (int k = first, c = -1; k <= end; k += count[c], count[c] = 0) {
/* 2064 */         int t = perm[k];
/* 2065 */         c = a[t] >>> shift & 0xFF ^ signMask;
/* 2066 */         if (k < end) {
/* 2067 */           int d; for (pos[c] = pos[c] - 1; (d = pos[c] - 1) > k; ) {
/* 2068 */             int z = t;
/* 2069 */             t = perm[d];
/* 2070 */             perm[d] = z;
/* 2071 */             c = a[t] >>> shift & 0xFF ^ signMask;
/*      */           } 
/* 2073 */           perm[k] = t;
/*      */         } 
/* 2075 */         if (level < 0 && count[c] > 1) {
/* 2076 */           if (count[c] < 1024) {
/* 2077 */             quickSortIndirect(perm, a, k, k + count[c]);
/* 2078 */             if (stable) stabilize(perm, a, k, k + count[c]); 
/*      */           } else {
/* 2080 */             offsetStack[stackPos] = k;
/* 2081 */             lengthStack[stackPos] = count[c];
/* 2082 */             levelStack[stackPos++] = level + 1;
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
/*      */   public static void parallelRadixSortIndirect(int[] perm, byte[] a, int from, int to, boolean stable) {
/* 2110 */     ForkJoinPool pool = getPool();
/* 2111 */     if (to - from < 1024 || pool.getParallelism() == 1) {
/* 2112 */       radixSortIndirect(perm, a, from, to, stable);
/*      */       return;
/*      */     } 
/* 2115 */     int maxLevel = 0;
/* 2116 */     LinkedBlockingQueue<Segment> queue = new LinkedBlockingQueue<>();
/* 2117 */     queue.add(new Segment(from, to - from, 0));
/* 2118 */     AtomicInteger queueSize = new AtomicInteger(1);
/* 2119 */     int numberOfThreads = pool.getParallelism();
/* 2120 */     ExecutorCompletionService<Void> executorCompletionService = new ExecutorCompletionService<>(pool);
/* 2121 */     int[] support = stable ? new int[perm.length] : null;
/* 2122 */     for (int j = numberOfThreads; j-- != 0; executorCompletionService.submit(() -> {
/*      */           int[] count = new int[256]; int[] pos = new int[256]; while (true) {
/*      */             if (queueSize.get() == 0) {
/*      */               int k = numberOfThreads; while (k-- != 0)
/*      */                 queue.add(POISON_PILL); 
/*      */             }  Segment segment = queue.take(); if (segment == POISON_PILL)
/*      */               return null;  int first = segment.offset; int length = segment.length; int level = segment.level; int signMask = (level % 1 == 0) ? 128 : 0; int shift = (0 - level % 1) * 8; int i = first + length; while (i-- != first)
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
/*      */                 if (level < 0 && count[j] > 1)
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
/*      */                 if (level < 0 && count[c] > 1)
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
/* 2187 */     Throwable problem = null;
/* 2188 */     for (int i = numberOfThreads; i-- != 0;) { try {
/* 2189 */         executorCompletionService.take().get();
/* 2190 */       } catch (Exception e) {
/* 2191 */         problem = e.getCause();
/*      */       }  }
/* 2193 */      if (problem != null) throw (problem instanceof RuntimeException) ? (RuntimeException)problem : new RuntimeException(problem);
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
/*      */   public static void parallelRadixSortIndirect(int[] perm, byte[] a, boolean stable) {
/* 2214 */     parallelRadixSortIndirect(perm, a, 0, a.length, stable);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static void radixSort(byte[] a, byte[] b) {
/* 2234 */     ensureSameLength(a, b);
/* 2235 */     radixSort(a, b, 0, a.length);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static void radixSort(byte[] a, byte[] b, int from, int to) {
/* 2258 */     if (to - from < 1024) {
/* 2259 */       quickSort(a, b, from, to);
/*      */       return;
/*      */     } 
/* 2262 */     int layers = 2;
/* 2263 */     int maxLevel = 1;
/* 2264 */     int stackSize = 256;
/* 2265 */     int stackPos = 0;
/* 2266 */     int[] offsetStack = new int[256];
/* 2267 */     int[] lengthStack = new int[256];
/* 2268 */     int[] levelStack = new int[256];
/* 2269 */     offsetStack[stackPos] = from;
/* 2270 */     lengthStack[stackPos] = to - from;
/* 2271 */     levelStack[stackPos++] = 0;
/* 2272 */     int[] count = new int[256];
/* 2273 */     int[] pos = new int[256];
/* 2274 */     while (stackPos > 0) {
/* 2275 */       int first = offsetStack[--stackPos];
/* 2276 */       int length = lengthStack[stackPos];
/* 2277 */       int level = levelStack[stackPos];
/* 2278 */       int signMask = (level % 1 == 0) ? 128 : 0;
/* 2279 */       byte[] k = (level < 1) ? a : b;
/* 2280 */       int shift = (0 - level % 1) * 8;
/*      */ 
/*      */ 
/*      */ 
/*      */       
/* 2285 */       for (int i = first + length; i-- != first; count[k[i] >>> shift & 0xFF ^ signMask] = count[k[i] >>> shift & 0xFF ^ signMask] + 1);
/*      */       
/* 2287 */       int lastUsed = -1;
/* 2288 */       for (int j = 0, p = first; j < 256; j++) {
/* 2289 */         if (count[j] != 0) lastUsed = j; 
/* 2290 */         pos[j] = p += count[j];
/*      */       } 
/* 2292 */       int end = first + length - count[lastUsed];
/*      */       
/* 2294 */       for (int m = first, c = -1; m <= end; m += count[c], count[c] = 0) {
/* 2295 */         byte t = a[m];
/* 2296 */         byte u = b[m];
/* 2297 */         c = k[m] >>> shift & 0xFF ^ signMask;
/* 2298 */         if (m < end) {
/* 2299 */           int d; for (pos[c] = pos[c] - 1; (d = pos[c] - 1) > m; ) {
/* 2300 */             c = k[d] >>> shift & 0xFF ^ signMask;
/* 2301 */             byte z = t;
/* 2302 */             t = a[d];
/* 2303 */             a[d] = z;
/* 2304 */             z = u;
/* 2305 */             u = b[d];
/* 2306 */             b[d] = z;
/*      */           } 
/* 2308 */           a[m] = t;
/* 2309 */           b[m] = u;
/*      */         } 
/* 2311 */         if (level < 1 && count[c] > 1) {
/* 2312 */           if (count[c] < 1024) { quickSort(a, b, m, m + count[c]); }
/*      */           else
/* 2314 */           { offsetStack[stackPos] = m;
/* 2315 */             lengthStack[stackPos] = count[c];
/* 2316 */             levelStack[stackPos++] = level + 1; }
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
/*      */   public static void parallelRadixSort(byte[] a, byte[] b, int from, int to) {
/* 2343 */     ForkJoinPool pool = getPool();
/* 2344 */     if (to - from < 1024 || pool.getParallelism() == 1) {
/* 2345 */       quickSort(a, b, from, to);
/*      */       return;
/*      */     } 
/* 2348 */     int layers = 2;
/* 2349 */     if (a.length != b.length) throw new IllegalArgumentException("Array size mismatch."); 
/* 2350 */     int maxLevel = 1;
/* 2351 */     LinkedBlockingQueue<Segment> queue = new LinkedBlockingQueue<>();
/* 2352 */     queue.add(new Segment(from, to - from, 0));
/* 2353 */     AtomicInteger queueSize = new AtomicInteger(1);
/* 2354 */     int numberOfThreads = pool.getParallelism();
/* 2355 */     ExecutorCompletionService<Void> executorCompletionService = new ExecutorCompletionService<>(pool);
/* 2356 */     for (int j = numberOfThreads; j-- != 0; executorCompletionService.submit(() -> {
/*      */           int[] count = new int[256]; int[] pos = new int[256]; while (true) {
/*      */             if (queueSize.get() == 0) {
/*      */               int n = numberOfThreads; while (n-- != 0)
/*      */                 queue.add(POISON_PILL); 
/*      */             }  Segment segment = queue.take(); if (segment == POISON_PILL)
/*      */               return null;  int first = segment.offset; int length = segment.length; int level = segment.level; int signMask = (level % 1 == 0) ? 128 : 0; byte[] k = (level < 1) ? a : b; int shift = (0 - level % 1) * 8; int i = first + length; while (i-- != first)
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
/*      */               byte t = a[m];
/*      */               byte u = b[m];
/*      */               c = k[m] >>> shift & 0xFF ^ signMask;
/*      */               if (m < end) {
/*      */                 pos[c] = pos[c] - 1;
/*      */                 int d;
/*      */                 while ((d = pos[c] - 1) > m) {
/*      */                   c = k[d] >>> shift & 0xFF ^ signMask;
/*      */                   byte z = t;
/*      */                   byte w = u;
/*      */                   t = a[d];
/*      */                   u = b[d];
/*      */                   a[d] = z;
/*      */                   b[d] = w;
/*      */                 } 
/*      */                 a[m] = t;
/*      */                 b[m] = u;
/*      */               } 
/*      */               if (level < 1 && count[c] > 1)
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
/* 2406 */     Throwable problem = null;
/* 2407 */     for (int i = numberOfThreads; i-- != 0;) { try {
/* 2408 */         executorCompletionService.take().get();
/* 2409 */       } catch (Exception e) {
/* 2410 */         problem = e.getCause();
/*      */       }  }
/* 2412 */      if (problem != null) throw (problem instanceof RuntimeException) ? (RuntimeException)problem : new RuntimeException(problem);
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
/*      */   public static void parallelRadixSort(byte[] a, byte[] b) {
/* 2433 */     ensureSameLength(a, b);
/* 2434 */     parallelRadixSort(a, b, 0, a.length);
/*      */   }
/*      */   
/*      */   private static void insertionSortIndirect(int[] perm, byte[] a, byte[] b, int from, int to) {
/* 2438 */     for (int i = from; ++i < to; ) {
/* 2439 */       int t = perm[i];
/* 2440 */       int j = i; int u;
/* 2441 */       for (u = perm[j - 1]; a[t] < a[u] || (a[t] == a[u] && b[t] < b[u]); u = perm[--j - 1]) {
/* 2442 */         perm[j] = u;
/* 2443 */         if (from == j - 1) {
/* 2444 */           j--;
/*      */           break;
/*      */         } 
/*      */       } 
/* 2448 */       perm[j] = t;
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
/*      */   public static void radixSortIndirect(int[] perm, byte[] a, byte[] b, boolean stable) {
/* 2475 */     ensureSameLength(a, b);
/* 2476 */     radixSortIndirect(perm, a, b, 0, a.length, stable);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static void radixSortIndirect(int[] perm, byte[] a, byte[] b, int from, int to, boolean stable) {
/* 2504 */     if (to - from < 64) {
/* 2505 */       insertionSortIndirect(perm, a, b, from, to);
/*      */       return;
/*      */     } 
/* 2508 */     int layers = 2;
/* 2509 */     int maxLevel = 1;
/* 2510 */     int stackSize = 256;
/* 2511 */     int stackPos = 0;
/* 2512 */     int[] offsetStack = new int[256];
/* 2513 */     int[] lengthStack = new int[256];
/* 2514 */     int[] levelStack = new int[256];
/* 2515 */     offsetStack[stackPos] = from;
/* 2516 */     lengthStack[stackPos] = to - from;
/* 2517 */     levelStack[stackPos++] = 0;
/* 2518 */     int[] count = new int[256];
/* 2519 */     int[] pos = new int[256];
/* 2520 */     int[] support = stable ? new int[perm.length] : null;
/* 2521 */     while (stackPos > 0) {
/* 2522 */       int first = offsetStack[--stackPos];
/* 2523 */       int length = lengthStack[stackPos];
/* 2524 */       int level = levelStack[stackPos];
/* 2525 */       int signMask = (level % 1 == 0) ? 128 : 0;
/* 2526 */       byte[] k = (level < 1) ? a : b;
/* 2527 */       int shift = (0 - level % 1) * 8;
/*      */ 
/*      */ 
/*      */ 
/*      */       
/* 2532 */       for (int i = first + length; i-- != first; count[k[perm[i]] >>> shift & 0xFF ^ signMask] = count[k[perm[i]] >>> shift & 0xFF ^ signMask] + 1);
/*      */       
/* 2534 */       int lastUsed = -1; int j, p;
/* 2535 */       for (j = 0, p = stable ? 0 : first; j < 256; j++) {
/* 2536 */         if (count[j] != 0) lastUsed = j; 
/* 2537 */         pos[j] = p += count[j];
/*      */       } 
/* 2539 */       if (stable) {
/* 2540 */         for (j = first + length; j-- != first; ) { pos[k[perm[j]] >>> shift & 0xFF ^ signMask] = pos[k[perm[j]] >>> shift & 0xFF ^ signMask] - 1; support[pos[k[perm[j]] >>> shift & 0xFF ^ signMask] - 1] = perm[j]; }
/* 2541 */          System.arraycopy(support, 0, perm, first, length);
/* 2542 */         for (j = 0, p = first; j < 256; j++) {
/* 2543 */           if (level < 1 && count[j] > 1) {
/* 2544 */             if (count[j] < 64) { insertionSortIndirect(perm, a, b, p, p + count[j]); }
/*      */             else
/* 2546 */             { offsetStack[stackPos] = p;
/* 2547 */               lengthStack[stackPos] = count[j];
/* 2548 */               levelStack[stackPos++] = level + 1; }
/*      */           
/*      */           }
/* 2551 */           p += count[j];
/*      */         } 
/* 2553 */         Arrays.fill(count, 0); continue;
/*      */       } 
/* 2555 */       int end = first + length - count[lastUsed];
/*      */       
/* 2557 */       for (int m = first, c = -1; m <= end; m += count[c], count[c] = 0) {
/* 2558 */         int t = perm[m];
/* 2559 */         c = k[t] >>> shift & 0xFF ^ signMask;
/* 2560 */         if (m < end) {
/* 2561 */           int d; for (pos[c] = pos[c] - 1; (d = pos[c] - 1) > m; ) {
/* 2562 */             int z = t;
/* 2563 */             t = perm[d];
/* 2564 */             perm[d] = z;
/* 2565 */             c = k[t] >>> shift & 0xFF ^ signMask;
/*      */           } 
/* 2567 */           perm[m] = t;
/*      */         } 
/* 2569 */         if (level < 1 && count[c] > 1) {
/* 2570 */           if (count[c] < 64) { insertionSortIndirect(perm, a, b, m, m + count[c]); }
/*      */           else
/* 2572 */           { offsetStack[stackPos] = m;
/* 2573 */             lengthStack[stackPos] = count[c];
/* 2574 */             levelStack[stackPos++] = level + 1; }
/*      */         
/*      */         }
/*      */       } 
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   private static void selectionSort(byte[][] a, int from, int to, int level) {
/* 2583 */     int layers = a.length;
/* 2584 */     int firstLayer = level / 1;
/* 2585 */     for (int i = from; i < to - 1; i++) {
/* 2586 */       int m = i;
/* 2587 */       for (int j = i + 1; j < to; j++) {
/* 2588 */         for (int p = firstLayer; p < layers; p++) {
/* 2589 */           if (a[p][j] < a[p][m]) {
/* 2590 */             m = j; break;
/*      */           } 
/* 2592 */           if (a[p][j] > a[p][m])
/*      */             break; 
/*      */         } 
/* 2595 */       }  if (m != i) {
/* 2596 */         for (int p = layers; p-- != 0; ) {
/* 2597 */           byte u = a[p][i];
/* 2598 */           a[p][i] = a[p][m];
/* 2599 */           a[p][m] = u;
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
/*      */   public static void radixSort(byte[][] a) {
/* 2620 */     radixSort(a, 0, (a[0]).length);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static void radixSort(byte[][] a, int from, int to) {
/* 2640 */     if (to - from < 64) {
/* 2641 */       selectionSort(a, from, to, 0);
/*      */       return;
/*      */     } 
/* 2644 */     int layers = a.length;
/* 2645 */     int maxLevel = 1 * layers - 1;
/* 2646 */     int p = layers;
/* 2647 */     for (int l = (a[0]).length; p-- != 0;) { if ((a[p]).length != l) throw new IllegalArgumentException("The array of index " + p + " has not the same length of the array of index 0.");  }
/* 2648 */      int stackSize = 255 * (layers * 1 - 1) + 1;
/* 2649 */     int stackPos = 0;
/* 2650 */     int[] offsetStack = new int[stackSize];
/* 2651 */     int[] lengthStack = new int[stackSize];
/* 2652 */     int[] levelStack = new int[stackSize];
/* 2653 */     offsetStack[stackPos] = from;
/* 2654 */     lengthStack[stackPos] = to - from;
/* 2655 */     levelStack[stackPos++] = 0;
/* 2656 */     int[] count = new int[256];
/* 2657 */     int[] pos = new int[256];
/* 2658 */     byte[] t = new byte[layers];
/* 2659 */     while (stackPos > 0) {
/* 2660 */       int first = offsetStack[--stackPos];
/* 2661 */       int length = lengthStack[stackPos];
/* 2662 */       int level = levelStack[stackPos];
/* 2663 */       int signMask = (level % 1 == 0) ? 128 : 0;
/* 2664 */       byte[] k = a[level / 1];
/* 2665 */       int shift = (0 - level % 1) * 8;
/*      */ 
/*      */ 
/*      */ 
/*      */       
/* 2670 */       for (int i = first + length; i-- != first; count[k[i] >>> shift & 0xFF ^ signMask] = count[k[i] >>> shift & 0xFF ^ signMask] + 1);
/*      */       
/* 2672 */       int lastUsed = -1;
/* 2673 */       for (int j = 0, n = first; j < 256; j++) {
/* 2674 */         if (count[j] != 0) lastUsed = j; 
/* 2675 */         pos[j] = n += count[j];
/*      */       } 
/* 2677 */       int end = first + length - count[lastUsed];
/*      */       
/* 2679 */       for (int m = first, c = -1; m <= end; m += count[c], count[c] = 0) {
/* 2680 */         int i1; for (i1 = layers; i1-- != 0; t[i1] = a[i1][m]);
/* 2681 */         c = k[m] >>> shift & 0xFF ^ signMask;
/* 2682 */         if (m < end) {
/* 2683 */           int d; for (pos[c] = pos[c] - 1; (d = pos[c] - 1) > m; ) {
/* 2684 */             c = k[d] >>> shift & 0xFF ^ signMask;
/* 2685 */             for (i1 = layers; i1-- != 0; ) {
/* 2686 */               byte u = t[i1];
/* 2687 */               t[i1] = a[i1][d];
/* 2688 */               a[i1][d] = u;
/*      */             } 
/*      */           } 
/* 2691 */           for (i1 = layers; i1-- != 0; a[i1][m] = t[i1]);
/*      */         } 
/* 2693 */         if (level < maxLevel && count[c] > 1) {
/* 2694 */           if (count[c] < 64) { selectionSort(a, m, m + count[c], level + 1); }
/*      */           else
/* 2696 */           { offsetStack[stackPos] = m;
/* 2697 */             lengthStack[stackPos] = count[c];
/* 2698 */             levelStack[stackPos++] = level + 1; }
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
/*      */   public static byte[] shuffle(byte[] a, int from, int to, Random random) {
/* 2715 */     for (int i = to - from; i-- != 0; ) {
/* 2716 */       int p = random.nextInt(i + 1);
/* 2717 */       byte t = a[from + i];
/* 2718 */       a[from + i] = a[from + p];
/* 2719 */       a[from + p] = t;
/*      */     } 
/* 2721 */     return a;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static byte[] shuffle(byte[] a, Random random) {
/* 2732 */     for (int i = a.length; i-- != 0; ) {
/* 2733 */       int p = random.nextInt(i + 1);
/* 2734 */       byte t = a[i];
/* 2735 */       a[i] = a[p];
/* 2736 */       a[p] = t;
/*      */     } 
/* 2738 */     return a;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static byte[] reverse(byte[] a) {
/* 2748 */     int length = a.length;
/* 2749 */     for (int i = length / 2; i-- != 0; ) {
/* 2750 */       byte t = a[length - i - 1];
/* 2751 */       a[length - i - 1] = a[i];
/* 2752 */       a[i] = t;
/*      */     } 
/* 2754 */     return a;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static byte[] reverse(byte[] a, int from, int to) {
/* 2766 */     int length = to - from;
/* 2767 */     for (int i = length / 2; i-- != 0; ) {
/* 2768 */       byte t = a[from + length - i - 1];
/* 2769 */       a[from + length - i - 1] = a[from + i];
/* 2770 */       a[from + i] = t;
/*      */     } 
/* 2772 */     return a;
/*      */   }
/*      */   
/*      */   private static final class ArrayHashStrategy implements Hash.Strategy<byte[]>, Serializable {
/*      */     private static final long serialVersionUID = -7046029254386353129L;
/*      */     
/*      */     private ArrayHashStrategy() {}
/*      */     
/*      */     public int hashCode(byte[] o) {
/* 2781 */       return Arrays.hashCode(o);
/*      */     }
/*      */ 
/*      */     
/*      */     public boolean equals(byte[] a, byte[] b) {
/* 2786 */       return Arrays.equals(a, b);
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
/* 2798 */   public static final Hash.Strategy<byte[]> HASH_STRATEGY = new ArrayHashStrategy();
/*      */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\i\\unimi\dsi\fastutil\bytes\ByteArrays.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */