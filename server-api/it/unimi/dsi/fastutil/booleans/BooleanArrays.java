/*      */ package it.unimi.dsi.fastutil.booleans;
/*      */ 
/*      */ import it.unimi.dsi.fastutil.Arrays;
/*      */ import it.unimi.dsi.fastutil.Hash;
/*      */ import it.unimi.dsi.fastutil.ints.IntArrays;
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
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ public final class BooleanArrays
/*      */ {
/*  102 */   public static final boolean[] EMPTY_ARRAY = new boolean[0];
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*  110 */   public static final boolean[] DEFAULT_EMPTY_ARRAY = new boolean[0];
/*      */ 
/*      */   
/*      */   private static final int QUICKSORT_NO_REC = 16;
/*      */   
/*      */   private static final int PARALLEL_QUICKSORT_NO_FORK = 8192;
/*      */   
/*      */   private static final int QUICKSORT_MEDIAN_OF_9 = 128;
/*      */   
/*      */   private static final int MERGESORT_NO_REC = 16;
/*      */ 
/*      */   
/*      */   public static boolean[] forceCapacity(boolean[] array, int length, int preserve) {
/*  123 */     boolean[] t = new boolean[length];
/*  124 */     System.arraycopy(array, 0, t, 0, preserve);
/*  125 */     return t;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static boolean[] ensureCapacity(boolean[] array, int length) {
/*  142 */     return ensureCapacity(array, length, array.length);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static boolean[] ensureCapacity(boolean[] array, int length, int preserve) {
/*  158 */     return (length > array.length) ? forceCapacity(array, length, preserve) : array;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static boolean[] grow(boolean[] array, int length) {
/*  176 */     return grow(array, length, array.length);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static boolean[] grow(boolean[] array, int length, int preserve) {
/*  197 */     if (length > array.length) {
/*  198 */       int newLength = (int)Math.max(Math.min(array.length + (array.length >> 1), 2147483639L), length);
/*  199 */       boolean[] t = new boolean[newLength];
/*  200 */       System.arraycopy(array, 0, t, 0, preserve);
/*  201 */       return t;
/*      */     } 
/*  203 */     return array;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static boolean[] trim(boolean[] array, int length) {
/*  217 */     if (length >= array.length) return array; 
/*  218 */     boolean[] t = (length == 0) ? EMPTY_ARRAY : new boolean[length];
/*  219 */     System.arraycopy(array, 0, t, 0, length);
/*  220 */     return t;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static boolean[] setLength(boolean[] array, int length) {
/*  236 */     if (length == array.length) return array; 
/*  237 */     if (length < array.length) return trim(array, length); 
/*  238 */     return ensureCapacity(array, length);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static boolean[] copy(boolean[] array, int offset, int length) {
/*  251 */     ensureOffsetLength(array, offset, length);
/*  252 */     boolean[] a = (length == 0) ? EMPTY_ARRAY : new boolean[length];
/*  253 */     System.arraycopy(array, offset, a, 0, length);
/*  254 */     return a;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static boolean[] copy(boolean[] array) {
/*  264 */     return (boolean[])array.clone();
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
/*      */   public static void fill(boolean[] array, boolean value) {
/*  276 */     int i = array.length;
/*  277 */     for (; i-- != 0; array[i] = value);
/*      */   }
/*      */ 
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
/*      */   public static void fill(boolean[] array, int from, int to, boolean value) {
/*  291 */     ensureFromTo(array, from, to);
/*  292 */     if (from == 0) { for (; to-- != 0; array[to] = value); }
/*  293 */     else { for (int i = from; i < to; ) { array[i] = value; i++; }
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
/*      */   public static boolean equals(boolean[] a1, boolean[] a2) {
/*  307 */     int i = a1.length;
/*  308 */     if (i != a2.length) return false; 
/*  309 */     while (i-- != 0) { if (a1[i] != a2[i]) return false;  }
/*  310 */      return true;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static void ensureFromTo(boolean[] a, int from, int to) {
/*  332 */     Arrays.ensureFromTo(a.length, from, to);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static void ensureOffsetLength(boolean[] a, int offset, int length) {
/*  354 */     Arrays.ensureOffsetLength(a.length, offset, length);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static void ensureSameLength(boolean[] a, boolean[] b) {
/*  365 */     if (a.length != b.length) throw new IllegalArgumentException("Array size mismatch: " + a.length + " != " + b.length);
/*      */   
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private static ForkJoinPool getPool() {
/*  375 */     ForkJoinPool current = ForkJoinTask.getPool();
/*  376 */     return (current == null) ? ForkJoinPool.commonPool() : current;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static void swap(boolean[] x, int a, int b) {
/*  387 */     boolean t = x[a];
/*  388 */     x[a] = x[b];
/*  389 */     x[b] = t;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static void swap(boolean[] x, int a, int b, int n) {
/*  401 */     for (int i = 0; i < n; ) { swap(x, a, b); i++; a++; b++; }
/*      */   
/*      */   }
/*      */   private static int med3(boolean[] x, int a, int b, int c, BooleanComparator comp) {
/*  405 */     int ab = comp.compare(x[a], x[b]);
/*  406 */     int ac = comp.compare(x[a], x[c]);
/*  407 */     int bc = comp.compare(x[b], x[c]);
/*  408 */     return (ab < 0) ? ((bc < 0) ? b : ((ac < 0) ? c : a)) : ((bc > 0) ? b : ((ac > 0) ? c : a));
/*      */   }
/*      */   
/*      */   private static void selectionSort(boolean[] a, int from, int to, BooleanComparator comp) {
/*  412 */     for (int i = from; i < to - 1; i++) {
/*  413 */       int m = i;
/*  414 */       for (int j = i + 1; j < to; ) { if (comp.compare(a[j], a[m]) < 0) m = j;  j++; }
/*  415 */        if (m != i) {
/*  416 */         boolean u = a[i];
/*  417 */         a[i] = a[m];
/*  418 */         a[m] = u;
/*      */       } 
/*      */     } 
/*      */   }
/*      */   
/*      */   private static void insertionSort(boolean[] a, int from, int to, BooleanComparator comp) {
/*  424 */     for (int i = from; ++i < to; ) {
/*  425 */       boolean t = a[i];
/*  426 */       int j = i; boolean u;
/*  427 */       for (u = a[j - 1]; comp.compare(t, u) < 0; u = a[--j - 1]) {
/*  428 */         a[j] = u;
/*  429 */         if (from == j - 1) {
/*  430 */           j--;
/*      */           break;
/*      */         } 
/*      */       } 
/*  434 */       a[j] = t;
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
/*      */   public static void quickSort(boolean[] x, int from, int to, BooleanComparator comp) {
/*  458 */     int len = to - from;
/*      */     
/*  460 */     if (len < 16) {
/*  461 */       selectionSort(x, from, to, comp);
/*      */       
/*      */       return;
/*      */     } 
/*  465 */     int m = from + len / 2;
/*  466 */     int l = from;
/*  467 */     int n = to - 1;
/*  468 */     if (len > 128) {
/*  469 */       int i = len / 8;
/*  470 */       l = med3(x, l, l + i, l + 2 * i, comp);
/*  471 */       m = med3(x, m - i, m, m + i, comp);
/*  472 */       n = med3(x, n - 2 * i, n - i, n, comp);
/*      */     } 
/*  474 */     m = med3(x, l, m, n, comp);
/*  475 */     boolean v = x[m];
/*      */     
/*  477 */     int a = from, b = a, c = to - 1, d = c;
/*      */     while (true) {
/*      */       int comparison;
/*  480 */       if (b <= c && (comparison = comp.compare(x[b], v)) <= 0) {
/*  481 */         if (comparison == 0) swap(x, a++, b); 
/*  482 */         b++; continue;
/*      */       } 
/*  484 */       while (c >= b && (comparison = comp.compare(x[c], v)) >= 0) {
/*  485 */         if (comparison == 0) swap(x, c, d--); 
/*  486 */         c--;
/*      */       } 
/*  488 */       if (b > c)
/*  489 */         break;  swap(x, b++, c--);
/*      */     } 
/*      */ 
/*      */     
/*  493 */     int s = Math.min(a - from, b - a);
/*  494 */     swap(x, from, b - s, s);
/*  495 */     s = Math.min(d - c, to - d - 1);
/*  496 */     swap(x, b, to - s, s);
/*      */     
/*  498 */     if ((s = b - a) > 1) quickSort(x, from, from + s, comp); 
/*  499 */     if ((s = d - c) > 1) quickSort(x, to - s, to, comp);
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
/*      */   public static void quickSort(boolean[] x, BooleanComparator comp) {
/*  519 */     quickSort(x, 0, x.length, comp);
/*      */   }
/*      */   
/*      */   protected static class ForkJoinQuickSortComp extends RecursiveAction {
/*      */     private static final long serialVersionUID = 1L;
/*      */     private final int from;
/*      */     private final int to;
/*      */     private final boolean[] x;
/*      */     private final BooleanComparator comp;
/*      */     
/*      */     public ForkJoinQuickSortComp(boolean[] x, int from, int to, BooleanComparator comp) {
/*  530 */       this.from = from;
/*  531 */       this.to = to;
/*  532 */       this.x = x;
/*  533 */       this.comp = comp;
/*      */     }
/*      */ 
/*      */     
/*      */     protected void compute() {
/*  538 */       boolean[] x = this.x;
/*  539 */       int len = this.to - this.from;
/*  540 */       if (len < 8192) {
/*  541 */         BooleanArrays.quickSort(x, this.from, this.to, this.comp);
/*      */         
/*      */         return;
/*      */       } 
/*  545 */       int m = this.from + len / 2;
/*  546 */       int l = this.from;
/*  547 */       int n = this.to - 1;
/*  548 */       int s = len / 8;
/*  549 */       l = BooleanArrays.med3(x, l, l + s, l + 2 * s, this.comp);
/*  550 */       m = BooleanArrays.med3(x, m - s, m, m + s, this.comp);
/*  551 */       n = BooleanArrays.med3(x, n - 2 * s, n - s, n, this.comp);
/*  552 */       m = BooleanArrays.med3(x, l, m, n, this.comp);
/*  553 */       boolean v = x[m];
/*      */       
/*  555 */       int a = this.from, b = a, c = this.to - 1, d = c;
/*      */       while (true) {
/*      */         int comparison;
/*  558 */         if (b <= c && (comparison = this.comp.compare(x[b], v)) <= 0) {
/*  559 */           if (comparison == 0) BooleanArrays.swap(x, a++, b); 
/*  560 */           b++; continue;
/*      */         } 
/*  562 */         while (c >= b && (comparison = this.comp.compare(x[c], v)) >= 0) {
/*  563 */           if (comparison == 0) BooleanArrays.swap(x, c, d--); 
/*  564 */           c--;
/*      */         } 
/*  566 */         if (b > c)
/*  567 */           break;  BooleanArrays.swap(x, b++, c--);
/*      */       } 
/*      */ 
/*      */       
/*  571 */       s = Math.min(a - this.from, b - a);
/*  572 */       BooleanArrays.swap(x, this.from, b - s, s);
/*  573 */       s = Math.min(d - c, this.to - d - 1);
/*  574 */       BooleanArrays.swap(x, b, this.to - s, s);
/*      */       
/*  576 */       s = b - a;
/*  577 */       int t = d - c;
/*  578 */       if (s > 1 && t > 1) { invokeAll(new ForkJoinQuickSortComp(x, this.from, this.from + s, this.comp), new ForkJoinQuickSortComp(x, this.to - t, this.to, this.comp)); }
/*  579 */       else if (s > 1) { invokeAll((ForkJoinTask<?>[])new ForkJoinTask[] { new ForkJoinQuickSortComp(x, this.from, this.from + s, this.comp) }); }
/*  580 */       else { invokeAll((ForkJoinTask<?>[])new ForkJoinTask[] { new ForkJoinQuickSortComp(x, this.to - t, this.to, this.comp) }); }
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
/*      */   public static void parallelQuickSort(boolean[] x, int from, int to, BooleanComparator comp) {
/*  599 */     ForkJoinPool pool = getPool();
/*  600 */     if (to - from < 8192 || pool.getParallelism() == 1) { quickSort(x, from, to, comp); }
/*      */     else
/*  602 */     { pool.invoke(new ForkJoinQuickSortComp(x, from, to, comp)); }
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
/*      */   public static void parallelQuickSort(boolean[] x, BooleanComparator comp) {
/*  619 */     parallelQuickSort(x, 0, x.length, comp);
/*      */   }
/*      */   
/*      */   private static int med3(boolean[] x, int a, int b, int c) {
/*  623 */     int ab = Boolean.compare(x[a], x[b]);
/*  624 */     int ac = Boolean.compare(x[a], x[c]);
/*  625 */     int bc = Boolean.compare(x[b], x[c]);
/*  626 */     return (ab < 0) ? ((bc < 0) ? b : ((ac < 0) ? c : a)) : ((bc > 0) ? b : ((ac > 0) ? c : a));
/*      */   }
/*      */   
/*      */   private static void selectionSort(boolean[] a, int from, int to) {
/*  630 */     for (int i = from; i < to - 1; i++) {
/*  631 */       int m = i;
/*  632 */       for (int j = i + 1; j < to; ) { if (!a[j] && a[m]) m = j;  j++; }
/*  633 */        if (m != i) {
/*  634 */         boolean u = a[i];
/*  635 */         a[i] = a[m];
/*  636 */         a[m] = u;
/*      */       } 
/*      */     } 
/*      */   }
/*      */   
/*      */   private static void insertionSort(boolean[] a, int from, int to) {
/*  642 */     for (int i = from; ++i < to; ) {
/*  643 */       boolean t = a[i];
/*  644 */       int j = i; boolean u;
/*  645 */       for (u = a[j - 1]; !t && u; u = a[--j - 1]) {
/*  646 */         a[j] = u;
/*  647 */         if (from == j - 1) {
/*  648 */           j--;
/*      */           break;
/*      */         } 
/*      */       } 
/*  652 */       a[j] = t;
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
/*      */   public static void quickSort(boolean[] x, int from, int to) {
/*  674 */     int len = to - from;
/*      */     
/*  676 */     if (len < 16) {
/*  677 */       selectionSort(x, from, to);
/*      */       
/*      */       return;
/*      */     } 
/*  681 */     int m = from + len / 2;
/*  682 */     int l = from;
/*  683 */     int n = to - 1;
/*  684 */     if (len > 128) {
/*  685 */       int i = len / 8;
/*  686 */       l = med3(x, l, l + i, l + 2 * i);
/*  687 */       m = med3(x, m - i, m, m + i);
/*  688 */       n = med3(x, n - 2 * i, n - i, n);
/*      */     } 
/*  690 */     m = med3(x, l, m, n);
/*  691 */     boolean v = x[m];
/*      */     
/*  693 */     int a = from, b = a, c = to - 1, d = c;
/*      */     while (true) {
/*      */       int comparison;
/*  696 */       if (b <= c && (comparison = Boolean.compare(x[b], v)) <= 0) {
/*  697 */         if (comparison == 0) swap(x, a++, b); 
/*  698 */         b++; continue;
/*      */       } 
/*  700 */       while (c >= b && (comparison = Boolean.compare(x[c], v)) >= 0) {
/*  701 */         if (comparison == 0) swap(x, c, d--); 
/*  702 */         c--;
/*      */       } 
/*  704 */       if (b > c)
/*  705 */         break;  swap(x, b++, c--);
/*      */     } 
/*      */ 
/*      */     
/*  709 */     int s = Math.min(a - from, b - a);
/*  710 */     swap(x, from, b - s, s);
/*  711 */     s = Math.min(d - c, to - d - 1);
/*  712 */     swap(x, b, to - s, s);
/*      */     
/*  714 */     if ((s = b - a) > 1) quickSort(x, from, from + s); 
/*  715 */     if ((s = d - c) > 1) quickSort(x, to - s, to);
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
/*      */   public static void quickSort(boolean[] x) {
/*  734 */     quickSort(x, 0, x.length);
/*      */   }
/*      */   
/*      */   protected static class ForkJoinQuickSort extends RecursiveAction {
/*      */     private static final long serialVersionUID = 1L;
/*      */     private final int from;
/*      */     private final int to;
/*      */     private final boolean[] x;
/*      */     
/*      */     public ForkJoinQuickSort(boolean[] x, int from, int to) {
/*  744 */       this.from = from;
/*  745 */       this.to = to;
/*  746 */       this.x = x;
/*      */     }
/*      */ 
/*      */ 
/*      */     
/*      */     protected void compute() {
/*  752 */       boolean[] x = this.x;
/*  753 */       int len = this.to - this.from;
/*  754 */       if (len < 8192) {
/*  755 */         BooleanArrays.quickSort(x, this.from, this.to);
/*      */         
/*      */         return;
/*      */       } 
/*  759 */       int m = this.from + len / 2;
/*  760 */       int l = this.from;
/*  761 */       int n = this.to - 1;
/*  762 */       int s = len / 8;
/*  763 */       l = BooleanArrays.med3(x, l, l + s, l + 2 * s);
/*  764 */       m = BooleanArrays.med3(x, m - s, m, m + s);
/*  765 */       n = BooleanArrays.med3(x, n - 2 * s, n - s, n);
/*  766 */       m = BooleanArrays.med3(x, l, m, n);
/*  767 */       boolean v = x[m];
/*      */       
/*  769 */       int a = this.from, b = a, c = this.to - 1, d = c;
/*      */       while (true) {
/*      */         int comparison;
/*  772 */         if (b <= c && (comparison = Boolean.compare(x[b], v)) <= 0) {
/*  773 */           if (comparison == 0) BooleanArrays.swap(x, a++, b); 
/*  774 */           b++; continue;
/*      */         } 
/*  776 */         while (c >= b && (comparison = Boolean.compare(x[c], v)) >= 0) {
/*  777 */           if (comparison == 0) BooleanArrays.swap(x, c, d--); 
/*  778 */           c--;
/*      */         } 
/*  780 */         if (b > c)
/*  781 */           break;  BooleanArrays.swap(x, b++, c--);
/*      */       } 
/*      */ 
/*      */       
/*  785 */       s = Math.min(a - this.from, b - a);
/*  786 */       BooleanArrays.swap(x, this.from, b - s, s);
/*  787 */       s = Math.min(d - c, this.to - d - 1);
/*  788 */       BooleanArrays.swap(x, b, this.to - s, s);
/*      */       
/*  790 */       s = b - a;
/*  791 */       int t = d - c;
/*  792 */       if (s > 1 && t > 1) { invokeAll(new ForkJoinQuickSort(x, this.from, this.from + s), new ForkJoinQuickSort(x, this.to - t, this.to)); }
/*  793 */       else if (s > 1) { invokeAll((ForkJoinTask<?>[])new ForkJoinTask[] { new ForkJoinQuickSort(x, this.from, this.from + s) }); }
/*  794 */       else { invokeAll((ForkJoinTask<?>[])new ForkJoinTask[] { new ForkJoinQuickSort(x, this.to - t, this.to) }); }
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
/*      */   public static void parallelQuickSort(boolean[] x, int from, int to) {
/*  812 */     ForkJoinPool pool = getPool();
/*  813 */     if (to - from < 8192 || pool.getParallelism() == 1) { quickSort(x, from, to); }
/*      */     else
/*  815 */     { pool.invoke(new ForkJoinQuickSort(x, from, to)); }
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
/*      */   public static void parallelQuickSort(boolean[] x) {
/*  831 */     parallelQuickSort(x, 0, x.length);
/*      */   }
/*      */   
/*      */   private static int med3Indirect(int[] perm, boolean[] x, int a, int b, int c) {
/*  835 */     boolean aa = x[perm[a]];
/*  836 */     boolean bb = x[perm[b]];
/*  837 */     boolean cc = x[perm[c]];
/*  838 */     int ab = Boolean.compare(aa, bb);
/*  839 */     int ac = Boolean.compare(aa, cc);
/*  840 */     int bc = Boolean.compare(bb, cc);
/*  841 */     return (ab < 0) ? ((bc < 0) ? b : ((ac < 0) ? c : a)) : ((bc > 0) ? b : ((ac > 0) ? c : a));
/*      */   }
/*      */   
/*      */   private static void insertionSortIndirect(int[] perm, boolean[] a, int from, int to) {
/*  845 */     for (int i = from; ++i < to; ) {
/*  846 */       int t = perm[i];
/*  847 */       int j = i; int u;
/*  848 */       for (u = perm[j - 1]; !a[t] && a[u]; u = perm[--j - 1]) {
/*  849 */         perm[j] = u;
/*  850 */         if (from == j - 1) {
/*  851 */           j--;
/*      */           break;
/*      */         } 
/*      */       } 
/*  855 */       perm[j] = t;
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
/*      */   public static void quickSortIndirect(int[] perm, boolean[] x, int from, int to) {
/*  884 */     int len = to - from;
/*      */     
/*  886 */     if (len < 16) {
/*  887 */       insertionSortIndirect(perm, x, from, to);
/*      */       
/*      */       return;
/*      */     } 
/*  891 */     int m = from + len / 2;
/*  892 */     int l = from;
/*  893 */     int n = to - 1;
/*  894 */     if (len > 128) {
/*  895 */       int i = len / 8;
/*  896 */       l = med3Indirect(perm, x, l, l + i, l + 2 * i);
/*  897 */       m = med3Indirect(perm, x, m - i, m, m + i);
/*  898 */       n = med3Indirect(perm, x, n - 2 * i, n - i, n);
/*      */     } 
/*  900 */     m = med3Indirect(perm, x, l, m, n);
/*  901 */     boolean v = x[perm[m]];
/*      */     
/*  903 */     int a = from, b = a, c = to - 1, d = c;
/*      */     while (true) {
/*      */       int comparison;
/*  906 */       if (b <= c && (comparison = Boolean.compare(x[perm[b]], v)) <= 0) {
/*  907 */         if (comparison == 0) IntArrays.swap(perm, a++, b); 
/*  908 */         b++; continue;
/*      */       } 
/*  910 */       while (c >= b && (comparison = Boolean.compare(x[perm[c]], v)) >= 0) {
/*  911 */         if (comparison == 0) IntArrays.swap(perm, c, d--); 
/*  912 */         c--;
/*      */       } 
/*  914 */       if (b > c)
/*  915 */         break;  IntArrays.swap(perm, b++, c--);
/*      */     } 
/*      */ 
/*      */     
/*  919 */     int s = Math.min(a - from, b - a);
/*  920 */     IntArrays.swap(perm, from, b - s, s);
/*  921 */     s = Math.min(d - c, to - d - 1);
/*  922 */     IntArrays.swap(perm, b, to - s, s);
/*      */     
/*  924 */     if ((s = b - a) > 1) quickSortIndirect(perm, x, from, from + s); 
/*  925 */     if ((s = d - c) > 1) quickSortIndirect(perm, x, to - s, to);
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
/*      */   public static void quickSortIndirect(int[] perm, boolean[] x) {
/*  949 */     quickSortIndirect(perm, x, 0, x.length);
/*      */   }
/*      */   
/*      */   protected static class ForkJoinQuickSortIndirect extends RecursiveAction {
/*      */     private static final long serialVersionUID = 1L;
/*      */     private final int from;
/*      */     private final int to;
/*      */     private final int[] perm;
/*      */     private final boolean[] x;
/*      */     
/*      */     public ForkJoinQuickSortIndirect(int[] perm, boolean[] x, int from, int to) {
/*  960 */       this.from = from;
/*  961 */       this.to = to;
/*  962 */       this.x = x;
/*  963 */       this.perm = perm;
/*      */     }
/*      */ 
/*      */ 
/*      */     
/*      */     protected void compute() {
/*  969 */       boolean[] x = this.x;
/*  970 */       int len = this.to - this.from;
/*  971 */       if (len < 8192) {
/*  972 */         BooleanArrays.quickSortIndirect(this.perm, x, this.from, this.to);
/*      */         
/*      */         return;
/*      */       } 
/*  976 */       int m = this.from + len / 2;
/*  977 */       int l = this.from;
/*  978 */       int n = this.to - 1;
/*  979 */       int s = len / 8;
/*  980 */       l = BooleanArrays.med3Indirect(this.perm, x, l, l + s, l + 2 * s);
/*  981 */       m = BooleanArrays.med3Indirect(this.perm, x, m - s, m, m + s);
/*  982 */       n = BooleanArrays.med3Indirect(this.perm, x, n - 2 * s, n - s, n);
/*  983 */       m = BooleanArrays.med3Indirect(this.perm, x, l, m, n);
/*  984 */       boolean v = x[this.perm[m]];
/*      */       
/*  986 */       int a = this.from, b = a, c = this.to - 1, d = c;
/*      */       while (true) {
/*      */         int comparison;
/*  989 */         if (b <= c && (comparison = Boolean.compare(x[this.perm[b]], v)) <= 0) {
/*  990 */           if (comparison == 0) IntArrays.swap(this.perm, a++, b); 
/*  991 */           b++; continue;
/*      */         } 
/*  993 */         while (c >= b && (comparison = Boolean.compare(x[this.perm[c]], v)) >= 0) {
/*  994 */           if (comparison == 0) IntArrays.swap(this.perm, c, d--); 
/*  995 */           c--;
/*      */         } 
/*  997 */         if (b > c)
/*  998 */           break;  IntArrays.swap(this.perm, b++, c--);
/*      */       } 
/*      */ 
/*      */       
/* 1002 */       s = Math.min(a - this.from, b - a);
/* 1003 */       IntArrays.swap(this.perm, this.from, b - s, s);
/* 1004 */       s = Math.min(d - c, this.to - d - 1);
/* 1005 */       IntArrays.swap(this.perm, b, this.to - s, s);
/*      */       
/* 1007 */       s = b - a;
/* 1008 */       int t = d - c;
/* 1009 */       if (s > 1 && t > 1) { invokeAll(new ForkJoinQuickSortIndirect(this.perm, x, this.from, this.from + s), new ForkJoinQuickSortIndirect(this.perm, x, this.to - t, this.to)); }
/* 1010 */       else if (s > 1) { invokeAll((ForkJoinTask<?>[])new ForkJoinTask[] { new ForkJoinQuickSortIndirect(this.perm, x, this.from, this.from + s) }); }
/* 1011 */       else { invokeAll((ForkJoinTask<?>[])new ForkJoinTask[] { new ForkJoinQuickSortIndirect(this.perm, x, this.to - t, this.to) }); }
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
/*      */   public static void parallelQuickSortIndirect(int[] perm, boolean[] x, int from, int to) {
/* 1035 */     ForkJoinPool pool = getPool();
/* 1036 */     if (to - from < 8192 || pool.getParallelism() == 1) { quickSortIndirect(perm, x, from, to); }
/*      */     else
/* 1038 */     { pool.invoke(new ForkJoinQuickSortIndirect(perm, x, from, to)); }
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
/*      */   public static void parallelQuickSortIndirect(int[] perm, boolean[] x) {
/* 1060 */     parallelQuickSortIndirect(perm, x, 0, x.length);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static void stabilize(int[] perm, boolean[] x, int from, int to) {
/* 1087 */     int curr = from;
/* 1088 */     for (int i = from + 1; i < to; i++) {
/* 1089 */       if (x[perm[i]] != x[perm[curr]]) {
/* 1090 */         if (i - curr > 1) IntArrays.parallelQuickSort(perm, curr, i); 
/* 1091 */         curr = i;
/*      */       } 
/*      */     } 
/* 1094 */     if (to - curr > 1) IntArrays.parallelQuickSort(perm, curr, to);
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
/*      */   public static void stabilize(int[] perm, boolean[] x) {
/* 1119 */     stabilize(perm, x, 0, perm.length);
/*      */   }
/*      */ 
/*      */   
/*      */   private static int med3(boolean[] x, boolean[] y, int a, int b, int c) {
/* 1124 */     int t, ab = ((t = Boolean.compare(x[a], x[b])) == 0) ? Boolean.compare(y[a], y[b]) : t;
/* 1125 */     int ac = ((t = Boolean.compare(x[a], x[c])) == 0) ? Boolean.compare(y[a], y[c]) : t;
/* 1126 */     int bc = ((t = Boolean.compare(x[b], x[c])) == 0) ? Boolean.compare(y[b], y[c]) : t;
/* 1127 */     return (ab < 0) ? ((bc < 0) ? b : ((ac < 0) ? c : a)) : ((bc > 0) ? b : ((ac > 0) ? c : a));
/*      */   }
/*      */   
/*      */   private static void swap(boolean[] x, boolean[] y, int a, int b) {
/* 1131 */     boolean t = x[a];
/* 1132 */     boolean u = y[a];
/* 1133 */     x[a] = x[b];
/* 1134 */     y[a] = y[b];
/* 1135 */     x[b] = t;
/* 1136 */     y[b] = u;
/*      */   }
/*      */   
/*      */   private static void swap(boolean[] x, boolean[] y, int a, int b, int n) {
/* 1140 */     for (int i = 0; i < n; ) { swap(x, y, a, b); i++; a++; b++; }
/*      */   
/*      */   }
/*      */   private static void selectionSort(boolean[] a, boolean[] b, int from, int to) {
/* 1144 */     for (int i = from; i < to - 1; i++) {
/* 1145 */       int m = i;
/* 1146 */       for (int j = i + 1; j < to; ) { int u; if ((u = Boolean.compare(a[j], a[m])) < 0 || (u == 0 && !b[j] && b[m])) m = j;  j++; }
/* 1147 */        if (m != i) {
/* 1148 */         boolean t = a[i];
/* 1149 */         a[i] = a[m];
/* 1150 */         a[m] = t;
/* 1151 */         t = b[i];
/* 1152 */         b[i] = b[m];
/* 1153 */         b[m] = t;
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
/*      */   public static void quickSort(boolean[] x, boolean[] y, int from, int to) {
/* 1180 */     int len = to - from;
/* 1181 */     if (len < 16) {
/* 1182 */       selectionSort(x, y, from, to);
/*      */       
/*      */       return;
/*      */     } 
/* 1186 */     int m = from + len / 2;
/* 1187 */     int l = from;
/* 1188 */     int n = to - 1;
/* 1189 */     if (len > 128) {
/* 1190 */       int i = len / 8;
/* 1191 */       l = med3(x, y, l, l + i, l + 2 * i);
/* 1192 */       m = med3(x, y, m - i, m, m + i);
/* 1193 */       n = med3(x, y, n - 2 * i, n - i, n);
/*      */     } 
/* 1195 */     m = med3(x, y, l, m, n);
/* 1196 */     boolean v = x[m], w = y[m];
/*      */     
/* 1198 */     int a = from, b = a, c = to - 1, d = c;
/*      */     
/*      */     while (true) {
/* 1201 */       if (b <= c) { int comparison; int t; if ((comparison = ((t = Boolean.compare(x[b], v)) == 0) ? Boolean.compare(y[b], w) : t) <= 0) {
/* 1202 */           if (comparison == 0) swap(x, y, a++, b); 
/* 1203 */           b++; continue;
/*      */         }  }
/* 1205 */        while (c >= b) { int comparison; int t; if ((comparison = ((t = Boolean.compare(x[c], v)) == 0) ? Boolean.compare(y[c], w) : t) >= 0) {
/* 1206 */           if (comparison == 0) swap(x, y, c, d--); 
/* 1207 */           c--;
/*      */         }  }
/* 1209 */        if (b > c)
/* 1210 */         break;  swap(x, y, b++, c--);
/*      */     } 
/*      */ 
/*      */     
/* 1214 */     int s = Math.min(a - from, b - a);
/* 1215 */     swap(x, y, from, b - s, s);
/* 1216 */     s = Math.min(d - c, to - d - 1);
/* 1217 */     swap(x, y, b, to - s, s);
/*      */     
/* 1219 */     if ((s = b - a) > 1) quickSort(x, y, from, from + s); 
/* 1220 */     if ((s = d - c) > 1) quickSort(x, y, to - s, to);
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
/*      */   public static void quickSort(boolean[] x, boolean[] y) {
/* 1241 */     ensureSameLength(x, y);
/* 1242 */     quickSort(x, y, 0, x.length);
/*      */   }
/*      */   
/*      */   protected static class ForkJoinQuickSort2 extends RecursiveAction { private static final long serialVersionUID = 1L;
/*      */     private final int from;
/*      */     private final int to;
/*      */     private final boolean[] x;
/*      */     private final boolean[] y;
/*      */     
/*      */     public ForkJoinQuickSort2(boolean[] x, boolean[] y, int from, int to) {
/* 1252 */       this.from = from;
/* 1253 */       this.to = to;
/* 1254 */       this.x = x;
/* 1255 */       this.y = y;
/*      */     }
/*      */ 
/*      */ 
/*      */     
/*      */     protected void compute() {
/* 1261 */       boolean[] x = this.x;
/* 1262 */       boolean[] y = this.y;
/* 1263 */       int len = this.to - this.from;
/* 1264 */       if (len < 8192) {
/* 1265 */         BooleanArrays.quickSort(x, y, this.from, this.to);
/*      */         
/*      */         return;
/*      */       } 
/* 1269 */       int m = this.from + len / 2;
/* 1270 */       int l = this.from;
/* 1271 */       int n = this.to - 1;
/* 1272 */       int s = len / 8;
/* 1273 */       l = BooleanArrays.med3(x, y, l, l + s, l + 2 * s);
/* 1274 */       m = BooleanArrays.med3(x, y, m - s, m, m + s);
/* 1275 */       n = BooleanArrays.med3(x, y, n - 2 * s, n - s, n);
/* 1276 */       m = BooleanArrays.med3(x, y, l, m, n);
/* 1277 */       boolean v = x[m], w = y[m];
/*      */       
/* 1279 */       int a = this.from, b = a, c = this.to - 1, d = c;
/*      */       
/*      */       while (true) {
/* 1282 */         if (b <= c) { int comparison; int i; if ((comparison = ((i = Boolean.compare(x[b], v)) == 0) ? Boolean.compare(y[b], w) : i) <= 0) {
/* 1283 */             if (comparison == 0) BooleanArrays.swap(x, y, a++, b); 
/* 1284 */             b++; continue;
/*      */           }  }
/* 1286 */          while (c >= b) { int comparison; int i; if ((comparison = ((i = Boolean.compare(x[c], v)) == 0) ? Boolean.compare(y[c], w) : i) >= 0) {
/* 1287 */             if (comparison == 0) BooleanArrays.swap(x, y, c, d--); 
/* 1288 */             c--;
/*      */           }  }
/* 1290 */          if (b > c)
/* 1291 */           break;  BooleanArrays.swap(x, y, b++, c--);
/*      */       } 
/*      */ 
/*      */       
/* 1295 */       s = Math.min(a - this.from, b - a);
/* 1296 */       BooleanArrays.swap(x, y, this.from, b - s, s);
/* 1297 */       s = Math.min(d - c, this.to - d - 1);
/* 1298 */       BooleanArrays.swap(x, y, b, this.to - s, s);
/* 1299 */       s = b - a;
/* 1300 */       int t = d - c;
/*      */       
/* 1302 */       if (s > 1 && t > 1) { invokeAll(new ForkJoinQuickSort2(x, y, this.from, this.from + s), new ForkJoinQuickSort2(x, y, this.to - t, this.to)); }
/* 1303 */       else if (s > 1) { invokeAll((ForkJoinTask<?>[])new ForkJoinTask[] { new ForkJoinQuickSort2(x, y, this.from, this.from + s) }); }
/* 1304 */       else { invokeAll((ForkJoinTask<?>[])new ForkJoinTask[] { new ForkJoinQuickSort2(x, y, this.to - t, this.to) }); }
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
/*      */   public static void parallelQuickSort(boolean[] x, boolean[] y, int from, int to) {
/* 1329 */     ForkJoinPool pool = getPool();
/* 1330 */     if (to - from < 8192 || pool.getParallelism() == 1) { quickSort(x, y, from, to); }
/*      */     else
/* 1332 */     { pool.invoke(new ForkJoinQuickSort2(x, y, from, to)); }
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
/*      */   public static void parallelQuickSort(boolean[] x, boolean[] y) {
/* 1355 */     ensureSameLength(x, y);
/* 1356 */     parallelQuickSort(x, y, 0, x.length);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static void unstableSort(boolean[] a, int from, int to) {
/* 1371 */     quickSort(a, from, to);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static void unstableSort(boolean[] a) {
/* 1383 */     unstableSort(a, 0, a.length);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static void unstableSort(boolean[] a, int from, int to, BooleanComparator comp) {
/* 1398 */     quickSort(a, from, to, comp);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static void unstableSort(boolean[] a, BooleanComparator comp) {
/* 1411 */     unstableSort(a, 0, a.length, comp);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static void mergeSort(boolean[] a, int from, int to, boolean[] supp) {
/* 1431 */     int len = to - from;
/*      */     
/* 1433 */     if (len < 16) {
/* 1434 */       insertionSort(a, from, to);
/*      */       return;
/*      */     } 
/* 1437 */     if (supp == null) supp = Arrays.copyOf(a, to);
/*      */     
/* 1439 */     int mid = from + to >>> 1;
/* 1440 */     mergeSort(supp, from, mid, a);
/* 1441 */     mergeSort(supp, mid, to, a);
/*      */ 
/*      */     
/* 1444 */     if (!supp[mid - 1] || supp[mid]) {
/* 1445 */       System.arraycopy(supp, from, a, from, len);
/*      */       
/*      */       return;
/*      */     } 
/* 1449 */     for (int i = from, p = from, q = mid; i < to; i++) {
/* 1450 */       if (q >= to || (p < mid && (!supp[p] || supp[q]))) { a[i] = supp[p++]; }
/* 1451 */       else { a[i] = supp[q++]; }
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
/*      */   public static void mergeSort(boolean[] a, int from, int to) {
/* 1467 */     mergeSort(a, from, to, (boolean[])null);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static void mergeSort(boolean[] a) {
/* 1480 */     mergeSort(a, 0, a.length);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static void mergeSort(boolean[] a, int from, int to, BooleanComparator comp, boolean[] supp) {
/* 1500 */     int len = to - from;
/*      */     
/* 1502 */     if (len < 16) {
/* 1503 */       insertionSort(a, from, to, comp);
/*      */       return;
/*      */     } 
/* 1506 */     if (supp == null) supp = Arrays.copyOf(a, to);
/*      */     
/* 1508 */     int mid = from + to >>> 1;
/* 1509 */     mergeSort(supp, from, mid, comp, a);
/* 1510 */     mergeSort(supp, mid, to, comp, a);
/*      */ 
/*      */     
/* 1513 */     if (comp.compare(supp[mid - 1], supp[mid]) <= 0) {
/* 1514 */       System.arraycopy(supp, from, a, from, len);
/*      */       
/*      */       return;
/*      */     } 
/* 1518 */     for (int i = from, p = from, q = mid; i < to; i++) {
/* 1519 */       if (q >= to || (p < mid && comp.compare(supp[p], supp[q]) <= 0)) { a[i] = supp[p++]; }
/* 1520 */       else { a[i] = supp[q++]; }
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
/*      */   public static void mergeSort(boolean[] a, int from, int to, BooleanComparator comp) {
/* 1538 */     mergeSort(a, from, to, comp, (boolean[])null);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static void mergeSort(boolean[] a, BooleanComparator comp) {
/* 1552 */     mergeSort(a, 0, a.length, comp);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static void stableSort(boolean[] a, int from, int to) {
/* 1573 */     unstableSort(a, from, to);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static void stableSort(boolean[] a) {
/* 1590 */     stableSort(a, 0, a.length);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static void stableSort(boolean[] a, int from, int to, BooleanComparator comp) {
/* 1610 */     mergeSort(a, from, to, comp);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static void stableSort(boolean[] a, BooleanComparator comp) {
/* 1628 */     stableSort(a, 0, a.length, comp);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static boolean[] shuffle(boolean[] a, int from, int to, Random random) {
/* 1641 */     for (int i = to - from; i-- != 0; ) {
/* 1642 */       int p = random.nextInt(i + 1);
/* 1643 */       boolean t = a[from + i];
/* 1644 */       a[from + i] = a[from + p];
/* 1645 */       a[from + p] = t;
/*      */     } 
/* 1647 */     return a;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static boolean[] shuffle(boolean[] a, Random random) {
/* 1658 */     for (int i = a.length; i-- != 0; ) {
/* 1659 */       int p = random.nextInt(i + 1);
/* 1660 */       boolean t = a[i];
/* 1661 */       a[i] = a[p];
/* 1662 */       a[p] = t;
/*      */     } 
/* 1664 */     return a;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static boolean[] reverse(boolean[] a) {
/* 1674 */     int length = a.length;
/* 1675 */     for (int i = length / 2; i-- != 0; ) {
/* 1676 */       boolean t = a[length - i - 1];
/* 1677 */       a[length - i - 1] = a[i];
/* 1678 */       a[i] = t;
/*      */     } 
/* 1680 */     return a;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static boolean[] reverse(boolean[] a, int from, int to) {
/* 1692 */     int length = to - from;
/* 1693 */     for (int i = length / 2; i-- != 0; ) {
/* 1694 */       boolean t = a[from + length - i - 1];
/* 1695 */       a[from + length - i - 1] = a[from + i];
/* 1696 */       a[from + i] = t;
/*      */     } 
/* 1698 */     return a;
/*      */   }
/*      */   
/*      */   private static final class ArrayHashStrategy implements Hash.Strategy<boolean[]>, Serializable {
/*      */     private static final long serialVersionUID = -7046029254386353129L;
/*      */     
/*      */     private ArrayHashStrategy() {}
/*      */     
/*      */     public int hashCode(boolean[] o) {
/* 1707 */       return Arrays.hashCode(o);
/*      */     }
/*      */ 
/*      */     
/*      */     public boolean equals(boolean[] a, boolean[] b) {
/* 1712 */       return Arrays.equals(a, b);
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
/* 1724 */   public static final Hash.Strategy<boolean[]> HASH_STRATEGY = new ArrayHashStrategy();
/*      */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\i\\unimi\dsi\fastutil\booleans\BooleanArrays.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */