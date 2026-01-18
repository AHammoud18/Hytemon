/*      */ package it.unimi.dsi.fastutil.objects;
/*      */ 
/*      */ import it.unimi.dsi.fastutil.Arrays;
/*      */ import it.unimi.dsi.fastutil.Hash;
/*      */ import it.unimi.dsi.fastutil.ints.IntArrays;
/*      */ import java.io.Serializable;
/*      */ import java.lang.reflect.Array;
/*      */ import java.util.Arrays;
/*      */ import java.util.Comparator;
/*      */ import java.util.Objects;
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
/*      */ public final class ObjectArrays
/*      */ {
/*   90 */   public static final Object[] EMPTY_ARRAY = new Object[0];
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*   98 */   public static final Object[] DEFAULT_EMPTY_ARRAY = new Object[0];
/*      */ 
/*      */   
/*      */   private static final int QUICKSORT_NO_REC = 16;
/*      */ 
/*      */   
/*      */   private static final int PARALLEL_QUICKSORT_NO_FORK = 8192;
/*      */ 
/*      */   
/*      */   private static final int QUICKSORT_MEDIAN_OF_9 = 128;
/*      */ 
/*      */   
/*      */   private static final int MERGESORT_NO_REC = 16;
/*      */ 
/*      */   
/*      */   private static <K> K[] newArray(K[] prototype, int length) {
/*  114 */     Class<?> klass = prototype.getClass();
/*  115 */     if (klass == Object[].class) return (length == 0) ? (K[])EMPTY_ARRAY : (K[])new Object[length]; 
/*  116 */     return (K[])Array.newInstance(klass.getComponentType(), length);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static <K> K[] forceCapacity(K[] array, int length, int preserve) {
/*  130 */     K[] t = newArray(array, length);
/*  131 */     System.arraycopy(array, 0, t, 0, preserve);
/*  132 */     return t;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static <K> K[] ensureCapacity(K[] array, int length) {
/*  149 */     return ensureCapacity(array, length, array.length);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static <K> K[] ensureCapacity(K[] array, int length, int preserve) {
/*  165 */     return (length > array.length) ? forceCapacity(array, length, preserve) : array;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static <K> K[] grow(K[] array, int length) {
/*  183 */     return grow(array, length, array.length);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static <K> K[] grow(K[] array, int length, int preserve) {
/*  204 */     if (length > array.length) {
/*  205 */       int newLength = (int)Math.max(Math.min(array.length + (array.length >> 1), 2147483639L), length);
/*  206 */       K[] t = newArray(array, newLength);
/*  207 */       System.arraycopy(array, 0, t, 0, preserve);
/*  208 */       return t;
/*      */     } 
/*  210 */     return array;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static <K> K[] trim(K[] array, int length) {
/*  224 */     if (length >= array.length) return array; 
/*  225 */     K[] t = newArray(array, length);
/*  226 */     System.arraycopy(array, 0, t, 0, length);
/*  227 */     return t;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static <K> K[] setLength(K[] array, int length) {
/*  243 */     if (length == array.length) return array; 
/*  244 */     if (length < array.length) return trim(array, length); 
/*  245 */     return ensureCapacity(array, length);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static <K> K[] copy(K[] array, int offset, int length) {
/*  258 */     ensureOffsetLength(array, offset, length);
/*  259 */     K[] a = newArray(array, length);
/*  260 */     System.arraycopy(array, offset, a, 0, length);
/*  261 */     return a;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static <K> K[] copy(K[] array) {
/*  271 */     return (K[])array.clone();
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
/*      */   public static <K> void fill(K[] array, K value) {
/*  283 */     int i = array.length;
/*  284 */     for (; i-- != 0; array[i] = value);
/*      */   }
/*      */ 
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
/*      */   public static <K> void fill(K[] array, int from, int to, K value) {
/*  298 */     ensureFromTo(array, from, to);
/*  299 */     if (from == 0) { for (; to-- != 0; array[to] = value); }
/*  300 */     else { for (int i = from; i < to; ) { array[i] = value; i++; }
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
/*      */   public static <K> boolean equals(K[] a1, K[] a2) {
/*  314 */     int i = a1.length;
/*  315 */     if (i != a2.length) return false; 
/*  316 */     while (i-- != 0) { if (!Objects.equals(a1[i], a2[i])) return false;  }
/*  317 */      return true;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static <K> void ensureFromTo(K[] a, int from, int to) {
/*  339 */     Arrays.ensureFromTo(a.length, from, to);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static <K> void ensureOffsetLength(K[] a, int offset, int length) {
/*  361 */     Arrays.ensureOffsetLength(a.length, offset, length);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static <K> void ensureSameLength(K[] a, K[] b) {
/*  372 */     if (a.length != b.length) throw new IllegalArgumentException("Array size mismatch: " + a.length + " != " + b.length);
/*      */   
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private static ForkJoinPool getPool() {
/*  382 */     ForkJoinPool current = ForkJoinTask.getPool();
/*  383 */     return (current == null) ? ForkJoinPool.commonPool() : current;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static <K> void swap(K[] x, int a, int b) {
/*  394 */     K t = x[a];
/*  395 */     x[a] = x[b];
/*  396 */     x[b] = t;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static <K> void swap(K[] x, int a, int b, int n) {
/*  408 */     for (int i = 0; i < n; ) { swap(x, a, b); i++; a++; b++; }
/*      */   
/*      */   }
/*      */   private static <K> int med3(K[] x, int a, int b, int c, Comparator<K> comp) {
/*  412 */     int ab = comp.compare(x[a], x[b]);
/*  413 */     int ac = comp.compare(x[a], x[c]);
/*  414 */     int bc = comp.compare(x[b], x[c]);
/*  415 */     return (ab < 0) ? ((bc < 0) ? b : ((ac < 0) ? c : a)) : ((bc > 0) ? b : ((ac > 0) ? c : a));
/*      */   }
/*      */   
/*      */   private static <K> void selectionSort(K[] a, int from, int to, Comparator<K> comp) {
/*  419 */     for (int i = from; i < to - 1; i++) {
/*  420 */       int m = i;
/*  421 */       for (int j = i + 1; j < to; ) { if (comp.compare(a[j], a[m]) < 0) m = j;  j++; }
/*  422 */        if (m != i) {
/*  423 */         K u = a[i];
/*  424 */         a[i] = a[m];
/*  425 */         a[m] = u;
/*      */       } 
/*      */     } 
/*      */   }
/*      */   
/*      */   private static <K> void insertionSort(K[] a, int from, int to, Comparator<K> comp) {
/*  431 */     for (int i = from; ++i < to; ) {
/*  432 */       K t = a[i];
/*  433 */       int j = i;
/*  434 */       for (K u = a[j - 1]; comp.compare(t, u) < 0; u = a[--j - 1]) {
/*  435 */         a[j] = u;
/*  436 */         if (from == j - 1) {
/*  437 */           j--;
/*      */           break;
/*      */         } 
/*      */       } 
/*  441 */       a[j] = t;
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
/*      */   public static <K> void quickSort(K[] x, int from, int to, Comparator<K> comp) {
/*  465 */     int len = to - from;
/*      */     
/*  467 */     if (len < 16) {
/*  468 */       selectionSort(x, from, to, comp);
/*      */       
/*      */       return;
/*      */     } 
/*  472 */     int m = from + len / 2;
/*  473 */     int l = from;
/*  474 */     int n = to - 1;
/*  475 */     if (len > 128) {
/*  476 */       int i = len / 8;
/*  477 */       l = med3(x, l, l + i, l + 2 * i, comp);
/*  478 */       m = med3(x, m - i, m, m + i, comp);
/*  479 */       n = med3(x, n - 2 * i, n - i, n, comp);
/*      */     } 
/*  481 */     m = med3(x, l, m, n, comp);
/*  482 */     K v = x[m];
/*      */     
/*  484 */     int a = from, b = a, c = to - 1, d = c;
/*      */     while (true) {
/*      */       int comparison;
/*  487 */       if (b <= c && (comparison = comp.compare(x[b], v)) <= 0) {
/*  488 */         if (comparison == 0) swap(x, a++, b); 
/*  489 */         b++; continue;
/*      */       } 
/*  491 */       while (c >= b && (comparison = comp.compare(x[c], v)) >= 0) {
/*  492 */         if (comparison == 0) swap(x, c, d--); 
/*  493 */         c--;
/*      */       } 
/*  495 */       if (b > c)
/*  496 */         break;  swap(x, b++, c--);
/*      */     } 
/*      */ 
/*      */     
/*  500 */     int s = Math.min(a - from, b - a);
/*  501 */     swap(x, from, b - s, s);
/*  502 */     s = Math.min(d - c, to - d - 1);
/*  503 */     swap(x, b, to - s, s);
/*      */     
/*  505 */     if ((s = b - a) > 1) quickSort(x, from, from + s, comp); 
/*  506 */     if ((s = d - c) > 1) quickSort(x, to - s, to, comp);
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
/*      */   public static <K> void quickSort(K[] x, Comparator<K> comp) {
/*  526 */     quickSort(x, 0, x.length, comp);
/*      */   }
/*      */   
/*      */   protected static class ForkJoinQuickSortComp<K> extends RecursiveAction {
/*      */     private static final long serialVersionUID = 1L;
/*      */     private final int from;
/*      */     private final int to;
/*      */     private final K[] x;
/*      */     private final Comparator<K> comp;
/*      */     
/*      */     public ForkJoinQuickSortComp(K[] x, int from, int to, Comparator<K> comp) {
/*  537 */       this.from = from;
/*  538 */       this.to = to;
/*  539 */       this.x = x;
/*  540 */       this.comp = comp;
/*      */     }
/*      */ 
/*      */     
/*      */     protected void compute() {
/*  545 */       K[] x = this.x;
/*  546 */       int len = this.to - this.from;
/*  547 */       if (len < 8192) {
/*  548 */         ObjectArrays.quickSort(x, this.from, this.to, this.comp);
/*      */         
/*      */         return;
/*      */       } 
/*  552 */       int m = this.from + len / 2;
/*  553 */       int l = this.from;
/*  554 */       int n = this.to - 1;
/*  555 */       int s = len / 8;
/*  556 */       l = ObjectArrays.med3(x, l, l + s, l + 2 * s, this.comp);
/*  557 */       m = ObjectArrays.med3(x, m - s, m, m + s, this.comp);
/*  558 */       n = ObjectArrays.med3(x, n - 2 * s, n - s, n, this.comp);
/*  559 */       m = ObjectArrays.med3(x, l, m, n, this.comp);
/*  560 */       K v = x[m];
/*      */       
/*  562 */       int a = this.from, b = a, c = this.to - 1, d = c;
/*      */       while (true) {
/*      */         int comparison;
/*  565 */         if (b <= c && (comparison = this.comp.compare(x[b], v)) <= 0) {
/*  566 */           if (comparison == 0) ObjectArrays.swap(x, a++, b); 
/*  567 */           b++; continue;
/*      */         } 
/*  569 */         while (c >= b && (comparison = this.comp.compare(x[c], v)) >= 0) {
/*  570 */           if (comparison == 0) ObjectArrays.swap(x, c, d--); 
/*  571 */           c--;
/*      */         } 
/*  573 */         if (b > c)
/*  574 */           break;  ObjectArrays.swap(x, b++, c--);
/*      */       } 
/*      */ 
/*      */       
/*  578 */       s = Math.min(a - this.from, b - a);
/*  579 */       ObjectArrays.swap(x, this.from, b - s, s);
/*  580 */       s = Math.min(d - c, this.to - d - 1);
/*  581 */       ObjectArrays.swap(x, b, this.to - s, s);
/*      */       
/*  583 */       s = b - a;
/*  584 */       int t = d - c;
/*  585 */       if (s > 1 && t > 1) { invokeAll(new ForkJoinQuickSortComp(x, this.from, this.from + s, this.comp), new ForkJoinQuickSortComp(x, this.to - t, this.to, this.comp)); }
/*  586 */       else if (s > 1) { invokeAll((ForkJoinTask<?>[])new ForkJoinTask[] { new ForkJoinQuickSortComp(x, this.from, this.from + s, this.comp) }); }
/*  587 */       else { invokeAll((ForkJoinTask<?>[])new ForkJoinTask[] { new ForkJoinQuickSortComp(x, this.to - t, this.to, this.comp) }); }
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
/*      */   public static <K> void parallelQuickSort(K[] x, int from, int to, Comparator<K> comp) {
/*  606 */     ForkJoinPool pool = getPool();
/*  607 */     if (to - from < 8192 || pool.getParallelism() == 1) { quickSort(x, from, to, comp); }
/*      */     else
/*  609 */     { pool.invoke(new ForkJoinQuickSortComp<>(x, from, to, comp)); }
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
/*      */   public static <K> void parallelQuickSort(K[] x, Comparator<K> comp) {
/*  626 */     parallelQuickSort(x, 0, x.length, comp);
/*      */   }
/*      */ 
/*      */   
/*      */   private static <K> int med3(K[] x, int a, int b, int c) {
/*  631 */     int ab = ((Comparable<K>)x[a]).compareTo(x[b]);
/*  632 */     int ac = ((Comparable<K>)x[a]).compareTo(x[c]);
/*  633 */     int bc = ((Comparable<K>)x[b]).compareTo(x[c]);
/*  634 */     return (ab < 0) ? ((bc < 0) ? b : ((ac < 0) ? c : a)) : ((bc > 0) ? b : ((ac > 0) ? c : a));
/*      */   }
/*      */ 
/*      */   
/*      */   private static <K> void selectionSort(K[] a, int from, int to) {
/*  639 */     for (int i = from; i < to - 1; i++) {
/*  640 */       int m = i;
/*  641 */       for (int j = i + 1; j < to; ) { if (((Comparable<K>)a[j]).compareTo(a[m]) < 0) m = j;  j++; }
/*  642 */        if (m != i) {
/*  643 */         K u = a[i];
/*  644 */         a[i] = a[m];
/*  645 */         a[m] = u;
/*      */       } 
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   private static <K> void insertionSort(K[] a, int from, int to) {
/*  652 */     for (int i = from; ++i < to; ) {
/*  653 */       K t = a[i];
/*  654 */       int j = i;
/*  655 */       for (K u = a[j - 1]; ((Comparable<K>)t).compareTo(u) < 0; u = a[--j - 1]) {
/*  656 */         a[j] = u;
/*  657 */         if (from == j - 1) {
/*  658 */           j--;
/*      */           break;
/*      */         } 
/*      */       } 
/*  662 */       a[j] = t;
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
/*      */   public static <K> void quickSort(K[] x, int from, int to) {
/*  684 */     int len = to - from;
/*      */     
/*  686 */     if (len < 16) {
/*  687 */       selectionSort(x, from, to);
/*      */       
/*      */       return;
/*      */     } 
/*  691 */     int m = from + len / 2;
/*  692 */     int l = from;
/*  693 */     int n = to - 1;
/*  694 */     if (len > 128) {
/*  695 */       int i = len / 8;
/*  696 */       l = med3(x, l, l + i, l + 2 * i);
/*  697 */       m = med3(x, m - i, m, m + i);
/*  698 */       n = med3(x, n - 2 * i, n - i, n);
/*      */     } 
/*  700 */     m = med3(x, l, m, n);
/*  701 */     K v = x[m];
/*      */     
/*  703 */     int a = from, b = a, c = to - 1, d = c;
/*      */     while (true) {
/*      */       int comparison;
/*  706 */       if (b <= c && (comparison = ((Comparable<K>)x[b]).compareTo(v)) <= 0) {
/*  707 */         if (comparison == 0) swap(x, a++, b); 
/*  708 */         b++; continue;
/*      */       } 
/*  710 */       while (c >= b && (comparison = ((Comparable<K>)x[c]).compareTo(v)) >= 0) {
/*  711 */         if (comparison == 0) swap(x, c, d--); 
/*  712 */         c--;
/*      */       } 
/*  714 */       if (b > c)
/*  715 */         break;  swap(x, b++, c--);
/*      */     } 
/*      */ 
/*      */     
/*  719 */     int s = Math.min(a - from, b - a);
/*  720 */     swap(x, from, b - s, s);
/*  721 */     s = Math.min(d - c, to - d - 1);
/*  722 */     swap(x, b, to - s, s);
/*      */     
/*  724 */     if ((s = b - a) > 1) quickSort(x, from, from + s); 
/*  725 */     if ((s = d - c) > 1) quickSort(x, to - s, to);
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
/*      */   public static <K> void quickSort(K[] x) {
/*  744 */     quickSort(x, 0, x.length);
/*      */   }
/*      */   
/*      */   protected static class ForkJoinQuickSort<K> extends RecursiveAction {
/*      */     private static final long serialVersionUID = 1L;
/*      */     private final int from;
/*      */     private final int to;
/*      */     private final K[] x;
/*      */     
/*      */     public ForkJoinQuickSort(K[] x, int from, int to) {
/*  754 */       this.from = from;
/*  755 */       this.to = to;
/*  756 */       this.x = x;
/*      */     }
/*      */ 
/*      */ 
/*      */     
/*      */     protected void compute() {
/*  762 */       K[] x = this.x;
/*  763 */       int len = this.to - this.from;
/*  764 */       if (len < 8192) {
/*  765 */         ObjectArrays.quickSort(x, this.from, this.to);
/*      */         
/*      */         return;
/*      */       } 
/*  769 */       int m = this.from + len / 2;
/*  770 */       int l = this.from;
/*  771 */       int n = this.to - 1;
/*  772 */       int s = len / 8;
/*  773 */       l = ObjectArrays.med3(x, l, l + s, l + 2 * s);
/*  774 */       m = ObjectArrays.med3(x, m - s, m, m + s);
/*  775 */       n = ObjectArrays.med3(x, n - 2 * s, n - s, n);
/*  776 */       m = ObjectArrays.med3(x, l, m, n);
/*  777 */       K v = x[m];
/*      */       
/*  779 */       int a = this.from, b = a, c = this.to - 1, d = c;
/*      */       while (true) {
/*      */         int comparison;
/*  782 */         if (b <= c && (comparison = ((Comparable<K>)x[b]).compareTo(v)) <= 0) {
/*  783 */           if (comparison == 0) ObjectArrays.swap(x, a++, b); 
/*  784 */           b++; continue;
/*      */         } 
/*  786 */         while (c >= b && (comparison = ((Comparable<K>)x[c]).compareTo(v)) >= 0) {
/*  787 */           if (comparison == 0) ObjectArrays.swap(x, c, d--); 
/*  788 */           c--;
/*      */         } 
/*  790 */         if (b > c)
/*  791 */           break;  ObjectArrays.swap(x, b++, c--);
/*      */       } 
/*      */ 
/*      */       
/*  795 */       s = Math.min(a - this.from, b - a);
/*  796 */       ObjectArrays.swap(x, this.from, b - s, s);
/*  797 */       s = Math.min(d - c, this.to - d - 1);
/*  798 */       ObjectArrays.swap(x, b, this.to - s, s);
/*      */       
/*  800 */       s = b - a;
/*  801 */       int t = d - c;
/*  802 */       if (s > 1 && t > 1) { invokeAll(new ForkJoinQuickSort(x, this.from, this.from + s), new ForkJoinQuickSort(x, this.to - t, this.to)); }
/*  803 */       else if (s > 1) { invokeAll((ForkJoinTask<?>[])new ForkJoinTask[] { new ForkJoinQuickSort(x, this.from, this.from + s) }); }
/*  804 */       else { invokeAll((ForkJoinTask<?>[])new ForkJoinTask[] { new ForkJoinQuickSort(x, this.to - t, this.to) }); }
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
/*      */   public static <K> void parallelQuickSort(K[] x, int from, int to) {
/*  822 */     ForkJoinPool pool = getPool();
/*  823 */     if (to - from < 8192 || pool.getParallelism() == 1) { quickSort(x, from, to); }
/*      */     else
/*  825 */     { pool.invoke(new ForkJoinQuickSort<>(x, from, to)); }
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
/*      */   public static <K> void parallelQuickSort(K[] x) {
/*  841 */     parallelQuickSort(x, 0, x.length);
/*      */   }
/*      */ 
/*      */   
/*      */   private static <K> int med3Indirect(int[] perm, K[] x, int a, int b, int c) {
/*  846 */     K aa = x[perm[a]];
/*  847 */     K bb = x[perm[b]];
/*  848 */     K cc = x[perm[c]];
/*  849 */     int ab = ((Comparable<K>)aa).compareTo(bb);
/*  850 */     int ac = ((Comparable<K>)aa).compareTo(cc);
/*  851 */     int bc = ((Comparable<K>)bb).compareTo(cc);
/*  852 */     return (ab < 0) ? ((bc < 0) ? b : ((ac < 0) ? c : a)) : ((bc > 0) ? b : ((ac > 0) ? c : a));
/*      */   }
/*      */ 
/*      */   
/*      */   private static <K> void insertionSortIndirect(int[] perm, K[] a, int from, int to) {
/*  857 */     for (int i = from; ++i < to; ) {
/*  858 */       int t = perm[i];
/*  859 */       int j = i; int u;
/*  860 */       for (u = perm[j - 1]; ((Comparable<K>)a[t]).compareTo(a[u]) < 0; u = perm[--j - 1]) {
/*  861 */         perm[j] = u;
/*  862 */         if (from == j - 1) {
/*  863 */           j--;
/*      */           break;
/*      */         } 
/*      */       } 
/*  867 */       perm[j] = t;
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
/*      */   public static <K> void quickSortIndirect(int[] perm, K[] x, int from, int to) {
/*  896 */     int len = to - from;
/*      */     
/*  898 */     if (len < 16) {
/*  899 */       insertionSortIndirect(perm, x, from, to);
/*      */       
/*      */       return;
/*      */     } 
/*  903 */     int m = from + len / 2;
/*  904 */     int l = from;
/*  905 */     int n = to - 1;
/*  906 */     if (len > 128) {
/*  907 */       int i = len / 8;
/*  908 */       l = med3Indirect(perm, x, l, l + i, l + 2 * i);
/*  909 */       m = med3Indirect(perm, x, m - i, m, m + i);
/*  910 */       n = med3Indirect(perm, x, n - 2 * i, n - i, n);
/*      */     } 
/*  912 */     m = med3Indirect(perm, x, l, m, n);
/*  913 */     K v = x[perm[m]];
/*      */     
/*  915 */     int a = from, b = a, c = to - 1, d = c;
/*      */     while (true) {
/*      */       int comparison;
/*  918 */       if (b <= c && (comparison = ((Comparable<K>)x[perm[b]]).compareTo(v)) <= 0) {
/*  919 */         if (comparison == 0) IntArrays.swap(perm, a++, b); 
/*  920 */         b++; continue;
/*      */       } 
/*  922 */       while (c >= b && (comparison = ((Comparable<K>)x[perm[c]]).compareTo(v)) >= 0) {
/*  923 */         if (comparison == 0) IntArrays.swap(perm, c, d--); 
/*  924 */         c--;
/*      */       } 
/*  926 */       if (b > c)
/*  927 */         break;  IntArrays.swap(perm, b++, c--);
/*      */     } 
/*      */ 
/*      */     
/*  931 */     int s = Math.min(a - from, b - a);
/*  932 */     IntArrays.swap(perm, from, b - s, s);
/*  933 */     s = Math.min(d - c, to - d - 1);
/*  934 */     IntArrays.swap(perm, b, to - s, s);
/*      */     
/*  936 */     if ((s = b - a) > 1) quickSortIndirect(perm, x, from, from + s); 
/*  937 */     if ((s = d - c) > 1) quickSortIndirect(perm, x, to - s, to);
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
/*      */   public static <K> void quickSortIndirect(int[] perm, K[] x) {
/*  961 */     quickSortIndirect(perm, x, 0, x.length);
/*      */   }
/*      */   
/*      */   protected static class ForkJoinQuickSortIndirect<K> extends RecursiveAction {
/*      */     private static final long serialVersionUID = 1L;
/*      */     private final int from;
/*      */     private final int to;
/*      */     private final int[] perm;
/*      */     private final K[] x;
/*      */     
/*      */     public ForkJoinQuickSortIndirect(int[] perm, K[] x, int from, int to) {
/*  972 */       this.from = from;
/*  973 */       this.to = to;
/*  974 */       this.x = x;
/*  975 */       this.perm = perm;
/*      */     }
/*      */ 
/*      */ 
/*      */     
/*      */     protected void compute() {
/*  981 */       K[] x = this.x;
/*  982 */       int len = this.to - this.from;
/*  983 */       if (len < 8192) {
/*  984 */         ObjectArrays.quickSortIndirect(this.perm, x, this.from, this.to);
/*      */         
/*      */         return;
/*      */       } 
/*  988 */       int m = this.from + len / 2;
/*  989 */       int l = this.from;
/*  990 */       int n = this.to - 1;
/*  991 */       int s = len / 8;
/*  992 */       l = ObjectArrays.med3Indirect(this.perm, x, l, l + s, l + 2 * s);
/*  993 */       m = ObjectArrays.med3Indirect(this.perm, x, m - s, m, m + s);
/*  994 */       n = ObjectArrays.med3Indirect(this.perm, x, n - 2 * s, n - s, n);
/*  995 */       m = ObjectArrays.med3Indirect(this.perm, x, l, m, n);
/*  996 */       K v = x[this.perm[m]];
/*      */       
/*  998 */       int a = this.from, b = a, c = this.to - 1, d = c;
/*      */       while (true) {
/*      */         int comparison;
/* 1001 */         if (b <= c && (comparison = ((Comparable<K>)x[this.perm[b]]).compareTo(v)) <= 0) {
/* 1002 */           if (comparison == 0) IntArrays.swap(this.perm, a++, b); 
/* 1003 */           b++; continue;
/*      */         } 
/* 1005 */         while (c >= b && (comparison = ((Comparable<K>)x[this.perm[c]]).compareTo(v)) >= 0) {
/* 1006 */           if (comparison == 0) IntArrays.swap(this.perm, c, d--); 
/* 1007 */           c--;
/*      */         } 
/* 1009 */         if (b > c)
/* 1010 */           break;  IntArrays.swap(this.perm, b++, c--);
/*      */       } 
/*      */ 
/*      */       
/* 1014 */       s = Math.min(a - this.from, b - a);
/* 1015 */       IntArrays.swap(this.perm, this.from, b - s, s);
/* 1016 */       s = Math.min(d - c, this.to - d - 1);
/* 1017 */       IntArrays.swap(this.perm, b, this.to - s, s);
/*      */       
/* 1019 */       s = b - a;
/* 1020 */       int t = d - c;
/* 1021 */       if (s > 1 && t > 1) { invokeAll(new ForkJoinQuickSortIndirect(this.perm, x, this.from, this.from + s), new ForkJoinQuickSortIndirect(this.perm, x, this.to - t, this.to)); }
/* 1022 */       else if (s > 1) { invokeAll((ForkJoinTask<?>[])new ForkJoinTask[] { new ForkJoinQuickSortIndirect(this.perm, x, this.from, this.from + s) }); }
/* 1023 */       else { invokeAll((ForkJoinTask<?>[])new ForkJoinTask[] { new ForkJoinQuickSortIndirect(this.perm, x, this.to - t, this.to) }); }
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
/*      */   public static <K> void parallelQuickSortIndirect(int[] perm, K[] x, int from, int to) {
/* 1047 */     ForkJoinPool pool = getPool();
/* 1048 */     if (to - from < 8192 || pool.getParallelism() == 1) { quickSortIndirect(perm, x, from, to); }
/*      */     else
/* 1050 */     { pool.invoke(new ForkJoinQuickSortIndirect<>(perm, x, from, to)); }
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
/*      */   public static <K> void parallelQuickSortIndirect(int[] perm, K[] x) {
/* 1072 */     parallelQuickSortIndirect(perm, x, 0, x.length);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static <K> void stabilize(int[] perm, K[] x, int from, int to) {
/* 1099 */     int curr = from;
/* 1100 */     for (int i = from + 1; i < to; i++) {
/* 1101 */       if (x[perm[i]] != x[perm[curr]]) {
/* 1102 */         if (i - curr > 1) IntArrays.parallelQuickSort(perm, curr, i); 
/* 1103 */         curr = i;
/*      */       } 
/*      */     } 
/* 1106 */     if (to - curr > 1) IntArrays.parallelQuickSort(perm, curr, to);
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
/*      */   public static <K> void stabilize(int[] perm, K[] x) {
/* 1131 */     stabilize(perm, x, 0, perm.length);
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   private static <K> int med3(K[] x, K[] y, int a, int b, int c) {
/* 1137 */     int t, ab = ((t = ((Comparable<K>)x[a]).compareTo(x[b])) == 0) ? ((Comparable<K>)y[a]).compareTo(y[b]) : t;
/* 1138 */     int ac = ((t = ((Comparable<K>)x[a]).compareTo(x[c])) == 0) ? ((Comparable<K>)y[a]).compareTo(y[c]) : t;
/* 1139 */     int bc = ((t = ((Comparable<K>)x[b]).compareTo(x[c])) == 0) ? ((Comparable<K>)y[b]).compareTo(y[c]) : t;
/* 1140 */     return (ab < 0) ? ((bc < 0) ? b : ((ac < 0) ? c : a)) : ((bc > 0) ? b : ((ac > 0) ? c : a));
/*      */   }
/*      */   
/*      */   private static <K> void swap(K[] x, K[] y, int a, int b) {
/* 1144 */     K t = x[a];
/* 1145 */     K u = y[a];
/* 1146 */     x[a] = x[b];
/* 1147 */     y[a] = y[b];
/* 1148 */     x[b] = t;
/* 1149 */     y[b] = u;
/*      */   }
/*      */   
/*      */   private static <K> void swap(K[] x, K[] y, int a, int b, int n) {
/* 1153 */     for (int i = 0; i < n; ) { swap(x, y, a, b); i++; a++; b++; }
/*      */   
/*      */   }
/*      */   
/*      */   private static <K> void selectionSort(K[] a, K[] b, int from, int to) {
/* 1158 */     for (int i = from; i < to - 1; i++) {
/* 1159 */       int m = i;
/* 1160 */       for (int j = i + 1; j < to; ) { int u; if ((u = ((Comparable<K>)a[j]).compareTo(a[m])) < 0 || (u == 0 && ((Comparable<K>)b[j]).compareTo(b[m]) < 0)) m = j;  j++; }
/* 1161 */        if (m != i) {
/* 1162 */         K t = a[i];
/* 1163 */         a[i] = a[m];
/* 1164 */         a[m] = t;
/* 1165 */         t = b[i];
/* 1166 */         b[i] = b[m];
/* 1167 */         b[m] = t;
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
/*      */   public static <K> void quickSort(K[] x, K[] y, int from, int to) {
/* 1194 */     int len = to - from;
/* 1195 */     if (len < 16) {
/* 1196 */       selectionSort(x, y, from, to);
/*      */       
/*      */       return;
/*      */     } 
/* 1200 */     int m = from + len / 2;
/* 1201 */     int l = from;
/* 1202 */     int n = to - 1;
/* 1203 */     if (len > 128) {
/* 1204 */       int i = len / 8;
/* 1205 */       l = med3(x, y, l, l + i, l + 2 * i);
/* 1206 */       m = med3(x, y, m - i, m, m + i);
/* 1207 */       n = med3(x, y, n - 2 * i, n - i, n);
/*      */     } 
/* 1209 */     m = med3(x, y, l, m, n);
/* 1210 */     K v = x[m], w = y[m];
/*      */     
/* 1212 */     int a = from, b = a, c = to - 1, d = c;
/*      */     
/*      */     while (true) {
/* 1215 */       if (b <= c) { int comparison; int t; if ((comparison = ((t = ((Comparable<K>)x[b]).compareTo(v)) == 0) ? ((Comparable<K>)y[b]).compareTo(w) : t) <= 0) {
/* 1216 */           if (comparison == 0) swap(x, y, a++, b); 
/* 1217 */           b++; continue;
/*      */         }  }
/* 1219 */        while (c >= b) { int comparison; int t; if ((comparison = ((t = ((Comparable<K>)x[c]).compareTo(v)) == 0) ? ((Comparable<K>)y[c]).compareTo(w) : t) >= 0) {
/* 1220 */           if (comparison == 0) swap(x, y, c, d--); 
/* 1221 */           c--;
/*      */         }  }
/* 1223 */        if (b > c)
/* 1224 */         break;  swap(x, y, b++, c--);
/*      */     } 
/*      */ 
/*      */     
/* 1228 */     int s = Math.min(a - from, b - a);
/* 1229 */     swap(x, y, from, b - s, s);
/* 1230 */     s = Math.min(d - c, to - d - 1);
/* 1231 */     swap(x, y, b, to - s, s);
/*      */     
/* 1233 */     if ((s = b - a) > 1) quickSort(x, y, from, from + s); 
/* 1234 */     if ((s = d - c) > 1) quickSort(x, y, to - s, to);
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
/*      */   public static <K> void quickSort(K[] x, K[] y) {
/* 1255 */     ensureSameLength(x, y);
/* 1256 */     quickSort(x, y, 0, x.length);
/*      */   }
/*      */   
/*      */   protected static class ForkJoinQuickSort2<K> extends RecursiveAction { private static final long serialVersionUID = 1L;
/*      */     private final int from;
/*      */     private final int to;
/*      */     private final K[] x;
/*      */     private final K[] y;
/*      */     
/*      */     public ForkJoinQuickSort2(K[] x, K[] y, int from, int to) {
/* 1266 */       this.from = from;
/* 1267 */       this.to = to;
/* 1268 */       this.x = x;
/* 1269 */       this.y = y;
/*      */     }
/*      */ 
/*      */ 
/*      */     
/*      */     protected void compute() {
/* 1275 */       K[] x = this.x;
/* 1276 */       K[] y = this.y;
/* 1277 */       int len = this.to - this.from;
/* 1278 */       if (len < 8192) {
/* 1279 */         ObjectArrays.quickSort(x, y, this.from, this.to);
/*      */         
/*      */         return;
/*      */       } 
/* 1283 */       int m = this.from + len / 2;
/* 1284 */       int l = this.from;
/* 1285 */       int n = this.to - 1;
/* 1286 */       int s = len / 8;
/* 1287 */       l = ObjectArrays.med3(x, y, l, l + s, l + 2 * s);
/* 1288 */       m = ObjectArrays.med3(x, y, m - s, m, m + s);
/* 1289 */       n = ObjectArrays.med3(x, y, n - 2 * s, n - s, n);
/* 1290 */       m = ObjectArrays.med3(x, y, l, m, n);
/* 1291 */       K v = x[m], w = y[m];
/*      */       
/* 1293 */       int a = this.from, b = a, c = this.to - 1, d = c;
/*      */       
/*      */       while (true) {
/* 1296 */         if (b <= c) { int comparison; int i; if ((comparison = ((i = ((Comparable<K>)x[b]).compareTo(v)) == 0) ? ((Comparable<K>)y[b]).compareTo(w) : i) <= 0) {
/* 1297 */             if (comparison == 0) ObjectArrays.swap(x, y, a++, b); 
/* 1298 */             b++; continue;
/*      */           }  }
/* 1300 */          while (c >= b) { int comparison; int i; if ((comparison = ((i = ((Comparable<K>)x[c]).compareTo(v)) == 0) ? ((Comparable<K>)y[c]).compareTo(w) : i) >= 0) {
/* 1301 */             if (comparison == 0) ObjectArrays.swap(x, y, c, d--); 
/* 1302 */             c--;
/*      */           }  }
/* 1304 */          if (b > c)
/* 1305 */           break;  ObjectArrays.swap(x, y, b++, c--);
/*      */       } 
/*      */ 
/*      */       
/* 1309 */       s = Math.min(a - this.from, b - a);
/* 1310 */       ObjectArrays.swap(x, y, this.from, b - s, s);
/* 1311 */       s = Math.min(d - c, this.to - d - 1);
/* 1312 */       ObjectArrays.swap(x, y, b, this.to - s, s);
/* 1313 */       s = b - a;
/* 1314 */       int t = d - c;
/*      */       
/* 1316 */       if (s > 1 && t > 1) { invokeAll(new ForkJoinQuickSort2(x, y, this.from, this.from + s), new ForkJoinQuickSort2(x, y, this.to - t, this.to)); }
/* 1317 */       else if (s > 1) { invokeAll((ForkJoinTask<?>[])new ForkJoinTask[] { new ForkJoinQuickSort2(x, y, this.from, this.from + s) }); }
/* 1318 */       else { invokeAll((ForkJoinTask<?>[])new ForkJoinTask[] { new ForkJoinQuickSort2(x, y, this.to - t, this.to) }); }
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
/*      */   public static <K> void parallelQuickSort(K[] x, K[] y, int from, int to) {
/* 1343 */     ForkJoinPool pool = getPool();
/* 1344 */     if (to - from < 8192 || pool.getParallelism() == 1) { quickSort(x, y, from, to); }
/*      */     else
/* 1346 */     { pool.invoke(new ForkJoinQuickSort2<>(x, y, from, to)); }
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
/*      */   public static <K> void parallelQuickSort(K[] x, K[] y) {
/* 1369 */     ensureSameLength(x, y);
/* 1370 */     parallelQuickSort(x, y, 0, x.length);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static <K> void unstableSort(K[] a, int from, int to) {
/* 1385 */     quickSort(a, from, to);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static <K> void unstableSort(K[] a) {
/* 1397 */     unstableSort(a, 0, a.length);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static <K> void unstableSort(K[] a, int from, int to, Comparator<K> comp) {
/* 1412 */     quickSort(a, from, to, comp);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static <K> void unstableSort(K[] a, Comparator<K> comp) {
/* 1425 */     unstableSort(a, 0, a.length, comp);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static <K> void mergeSort(K[] a, int from, int to, K[] supp) {
/* 1445 */     int len = to - from;
/*      */     
/* 1447 */     if (len < 16) {
/* 1448 */       insertionSort(a, from, to);
/*      */       return;
/*      */     } 
/* 1451 */     if (supp == null) supp = Arrays.copyOf(a, to);
/*      */     
/* 1453 */     int mid = from + to >>> 1;
/* 1454 */     mergeSort(supp, from, mid, a);
/* 1455 */     mergeSort(supp, mid, to, a);
/*      */ 
/*      */     
/* 1458 */     if (((Comparable<K>)supp[mid - 1]).compareTo(supp[mid]) <= 0) {
/* 1459 */       System.arraycopy(supp, from, a, from, len);
/*      */       
/*      */       return;
/*      */     } 
/* 1463 */     for (int i = from, p = from, q = mid; i < to; i++) {
/* 1464 */       if (q >= to || (p < mid && ((Comparable<K>)supp[p]).compareTo(supp[q]) <= 0)) { a[i] = supp[p++]; }
/* 1465 */       else { a[i] = supp[q++]; }
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
/*      */   public static <K> void mergeSort(K[] a, int from, int to) {
/* 1481 */     mergeSort(a, from, to, (K[])null);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static <K> void mergeSort(K[] a) {
/* 1494 */     mergeSort(a, 0, a.length);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static <K> void mergeSort(K[] a, int from, int to, Comparator<K> comp, K[] supp) {
/* 1514 */     int len = to - from;
/*      */     
/* 1516 */     if (len < 16) {
/* 1517 */       insertionSort(a, from, to, comp);
/*      */       return;
/*      */     } 
/* 1520 */     if (supp == null) supp = Arrays.copyOf(a, to);
/*      */     
/* 1522 */     int mid = from + to >>> 1;
/* 1523 */     mergeSort(supp, from, mid, comp, a);
/* 1524 */     mergeSort(supp, mid, to, comp, a);
/*      */ 
/*      */     
/* 1527 */     if (comp.compare(supp[mid - 1], supp[mid]) <= 0) {
/* 1528 */       System.arraycopy(supp, from, a, from, len);
/*      */       
/*      */       return;
/*      */     } 
/* 1532 */     for (int i = from, p = from, q = mid; i < to; i++) {
/* 1533 */       if (q >= to || (p < mid && comp.compare(supp[p], supp[q]) <= 0)) { a[i] = supp[p++]; }
/* 1534 */       else { a[i] = supp[q++]; }
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
/*      */   public static <K> void mergeSort(K[] a, int from, int to, Comparator<K> comp) {
/* 1552 */     mergeSort(a, from, to, comp, (K[])null);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static <K> void mergeSort(K[] a, Comparator<K> comp) {
/* 1566 */     mergeSort(a, 0, a.length, comp);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static <K> void stableSort(K[] a, int from, int to) {
/* 1585 */     Arrays.sort((Object[])a, from, to);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static <K> void stableSort(K[] a) {
/* 1602 */     stableSort(a, 0, a.length);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static <K> void stableSort(K[] a, int from, int to, Comparator<K> comp) {
/* 1623 */     Arrays.sort(a, from, to, comp);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static <K> void stableSort(K[] a, Comparator<K> comp) {
/* 1641 */     stableSort(a, 0, a.length, comp);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static <K> int binarySearch(K[] a, int from, int to, K key) {
/* 1665 */     to--;
/* 1666 */     while (from <= to) {
/* 1667 */       int mid = from + to >>> 1;
/* 1668 */       K midVal = a[mid];
/* 1669 */       int cmp = ((Comparable<K>)midVal).compareTo(key);
/* 1670 */       if (cmp < 0) { from = mid + 1; continue; }
/* 1671 */        if (cmp > 0) { to = mid - 1; continue; }
/* 1672 */        return mid;
/*      */     } 
/* 1674 */     return -(from + 1);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static <K> int binarySearch(K[] a, K key) {
/* 1694 */     return binarySearch(a, 0, a.length, key);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static <K> int binarySearch(K[] a, int from, int to, K key, Comparator<K> c) {
/* 1718 */     to--;
/* 1719 */     while (from <= to) {
/* 1720 */       int mid = from + to >>> 1;
/* 1721 */       K midVal = a[mid];
/* 1722 */       int cmp = c.compare(midVal, key);
/* 1723 */       if (cmp < 0) { from = mid + 1; continue; }
/* 1724 */        if (cmp > 0) { to = mid - 1; continue; }
/* 1725 */        return mid;
/*      */     } 
/* 1727 */     return -(from + 1);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static <K> int binarySearch(K[] a, K key, Comparator<K> c) {
/* 1748 */     return binarySearch(a, 0, a.length, key, c);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static <K> K[] shuffle(K[] a, int from, int to, Random random) {
/* 1761 */     for (int i = to - from; i-- != 0; ) {
/* 1762 */       int p = random.nextInt(i + 1);
/* 1763 */       K t = a[from + i];
/* 1764 */       a[from + i] = a[from + p];
/* 1765 */       a[from + p] = t;
/*      */     } 
/* 1767 */     return a;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static <K> K[] shuffle(K[] a, Random random) {
/* 1778 */     for (int i = a.length; i-- != 0; ) {
/* 1779 */       int p = random.nextInt(i + 1);
/* 1780 */       K t = a[i];
/* 1781 */       a[i] = a[p];
/* 1782 */       a[p] = t;
/*      */     } 
/* 1784 */     return a;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static <K> K[] reverse(K[] a) {
/* 1794 */     int length = a.length;
/* 1795 */     for (int i = length / 2; i-- != 0; ) {
/* 1796 */       K t = a[length - i - 1];
/* 1797 */       a[length - i - 1] = a[i];
/* 1798 */       a[i] = t;
/*      */     } 
/* 1800 */     return a;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static <K> K[] reverse(K[] a, int from, int to) {
/* 1812 */     int length = to - from;
/* 1813 */     for (int i = length / 2; i-- != 0; ) {
/* 1814 */       K t = a[from + length - i - 1];
/* 1815 */       a[from + length - i - 1] = a[from + i];
/* 1816 */       a[from + i] = t;
/*      */     } 
/* 1818 */     return a;
/*      */   }
/*      */   
/*      */   private static final class ArrayHashStrategy<K> implements Hash.Strategy<K[]>, Serializable {
/*      */     private static final long serialVersionUID = -7046029254386353129L;
/*      */     
/*      */     private ArrayHashStrategy() {}
/*      */     
/*      */     public int hashCode(K[] o) {
/* 1827 */       return Arrays.hashCode((Object[])o);
/*      */     }
/*      */ 
/*      */     
/*      */     public boolean equals(K[] a, K[] b) {
/* 1832 */       return Arrays.equals((Object[])a, (Object[])b);
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
/* 1845 */   public static final Hash.Strategy HASH_STRATEGY = new ArrayHashStrategy();
/*      */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\i\\unimi\dsi\fastutil\objects\ObjectArrays.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */