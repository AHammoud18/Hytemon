/*      */ package it.unimi.dsi.fastutil.objects;
/*      */ 
/*      */ import it.unimi.dsi.fastutil.BigArrays;
/*      */ import it.unimi.dsi.fastutil.Hash;
/*      */ import java.io.Serializable;
/*      */ import java.lang.reflect.Array;
/*      */ import java.util.Arrays;
/*      */ import java.util.Comparator;
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
/*      */ public final class ObjectBigArrays
/*      */ {
/*   77 */   public static final Object[][] EMPTY_BIG_ARRAY = new Object[0][];
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*   85 */   public static final Object[][] DEFAULT_EMPTY_BIG_ARRAY = new Object[0][];
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
/*      */   public static <K> K get(K[][] array, long index) {
/*   97 */     return array[BigArrays.segment(index)][BigArrays.displacement(index)];
/*      */   }
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
/*      */   public static <K> void set(K[][] array, long index, K value) {
/*  110 */     array[BigArrays.segment(index)][BigArrays.displacement(index)] = value;
/*      */   }
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
/*      */   public static <K> void swap(K[][] array, long first, long second) {
/*  123 */     K t = array[BigArrays.segment(first)][BigArrays.displacement(first)];
/*  124 */     array[BigArrays.segment(first)][BigArrays.displacement(first)] = array[BigArrays.segment(second)][BigArrays.displacement(second)];
/*  125 */     array[BigArrays.segment(second)][BigArrays.displacement(second)] = t;
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
/*      */   public static <K> long length(K[][] array) {
/*  137 */     int length = array.length;
/*  138 */     return (length == 0) ? 0L : (BigArrays.start(length - 1) + (array[length - 1]).length);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
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
/*      */   public static <K> void copy(K[][] srcArray, long srcPos, K[][] destArray, long destPos, long length) {
/*  155 */     BigArrays.copy((Object[][])srcArray, srcPos, (Object[][])destArray, destPos, length);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
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
/*      */   public static <K> void copyFromBig(K[][] srcArray, long srcPos, K[] destArray, int destPos, int length) {
/*  171 */     BigArrays.copyFromBig((Object[][])srcArray, srcPos, (Object[])destArray, destPos, length);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
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
/*      */   public static <K> void copyToBig(K[] srcArray, int srcPos, K[][] destArray, long destPos, long length) {
/*  187 */     BigArrays.copyToBig((Object[])srcArray, srcPos, (Object[][])destArray, destPos, length);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static <K> K[][] newBigArray(K[][] prototype, long length) {
/*  204 */     return (K[][])newBigArray(prototype.getClass().getComponentType(), length);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static Object[][] newBigArray(Class<?> componentType, long length) {
/*  219 */     if (length == 0L && componentType == Object[].class) return EMPTY_BIG_ARRAY; 
/*  220 */     BigArrays.ensureLength(length);
/*  221 */     int baseLength = (int)(length + 134217727L >>> 27L);
/*  222 */     Object[][] base = (Object[][])Array.newInstance(componentType, baseLength);
/*  223 */     int residual = (int)(length & 0x7FFFFFFL);
/*  224 */     if (residual != 0)
/*  225 */     { for (int i = 0; i < baseLength - 1; ) { base[i] = (Object[])Array.newInstance(componentType.getComponentType(), 134217728); i++; }
/*  226 */        base[baseLength - 1] = (Object[])Array.newInstance(componentType.getComponentType(), residual); }
/*  227 */     else { for (int i = 0; i < baseLength; ) { base[i] = (Object[])Array.newInstance(componentType.getComponentType(), 134217728); i++; }  }
/*  228 */      return base;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static Object[][] newBigArray(long length) {
/*  238 */     if (length == 0L) return EMPTY_BIG_ARRAY; 
/*  239 */     BigArrays.ensureLength(length);
/*  240 */     int baseLength = (int)(length + 134217727L >>> 27L);
/*  241 */     Object[][] base = new Object[baseLength][];
/*  242 */     int residual = (int)(length & 0x7FFFFFFL);
/*  243 */     if (residual != 0)
/*  244 */     { for (int i = 0; i < baseLength - 1; ) { base[i] = new Object[134217728]; i++; }
/*  245 */        base[baseLength - 1] = new Object[residual]; }
/*  246 */     else { for (int i = 0; i < baseLength; ) { base[i] = new Object[134217728]; i++; }  }
/*  247 */      return base;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
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
/*      */   public static <K> K[][] wrap(K[] array) {
/*  263 */     return (K[][])BigArrays.wrap((Object[])array);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
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
/*      */   public static <K> K[][] ensureCapacity(K[][] array, long length) {
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
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   @Deprecated
/*      */   public static <K> K[][] forceCapacity(K[][] array, long length, long preserve) {
/*  312 */     return (K[][])BigArrays.forceCapacity((Object[][])array, length, preserve);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
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
/*      */   public static <K> K[][] ensureCapacity(K[][] array, long length, long preserve) {
/*  338 */     return (length > length(array)) ? forceCapacity(array, length, preserve) : array;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
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
/*      */   public static <K> K[][] grow(K[][] array, long length) {
/*  362 */     long oldLength = length(array);
/*  363 */     return (length > oldLength) ? grow(array, length, oldLength) : array;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
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
/*      */   public static <K> K[][] grow(K[][] array, long length, long preserve) {
/*  390 */     long oldLength = length(array);
/*  391 */     return (length > oldLength) ? ensureCapacity(array, Math.max(oldLength + (oldLength >> 1L), length), preserve) : array;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
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
/*      */   public static <K> K[][] trim(K[][] array, long length) {
/*  410 */     return (K[][])BigArrays.trim((Object[][])array, length);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
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
/*      */   public static <K> K[][] setLength(K[][] array, long length) {
/*  431 */     return (K[][])BigArrays.setLength((Object[][])array, length);
/*      */   }
/*      */ 
/*      */ 
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
/*      */   public static <K> K[][] copy(K[][] array, long offset, long length) {
/*  446 */     return (K[][])BigArrays.copy((Object[][])array, offset, length);
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
/*      */   public static <K> K[][] copy(K[][] array) {
/*  458 */     return (K[][])BigArrays.copy((Object[][])array);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
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
/*      */   public static <K> void fill(K[][] array, K value) {
/*  474 */     for (int i = array.length; i-- != 0; Arrays.fill((Object[])array[i], value));
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
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
/*      */   public static <K> void fill(K[][] array, long from, long to, K value) {
/*  492 */     BigArrays.fill((Object[][])array, from, to, value);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
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
/*      */   public static <K> boolean equals(K[][] a1, K[][] a2) {
/*  509 */     return BigArrays.equals((Object[][])a1, (Object[][])a2);
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
/*      */   public static <K> String toString(K[][] a) {
/*  521 */     return BigArrays.toString((Object[][])a);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
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
/*      */   public static <K> void ensureFromTo(K[][] a, long from, long to) {
/*  541 */     BigArrays.ensureFromTo(length(a), from, to);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
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
/*      */   public static <K> void ensureOffsetLength(K[][] a, long offset, long length) {
/*  560 */     BigArrays.ensureOffsetLength(length(a), offset, length);
/*      */   }
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
/*      */   public static <K> void ensureSameLength(K[][] a, K[][] b) {
/*  573 */     if (length(a) != length(b)) throw new IllegalArgumentException("Array size mismatch: " + length(a) + " != " + length(b)); 
/*      */   }
/*      */   
/*      */   private static final class BigArrayHashStrategy<K> implements Hash.Strategy<K[][]>, Serializable {
/*      */     private static final long serialVersionUID = -7046029254386353129L;
/*      */     
/*      */     private BigArrayHashStrategy() {}
/*      */     
/*      */     public int hashCode(K[][] o) {
/*  582 */       return Arrays.deepHashCode((Object[])o);
/*      */     }
/*      */ 
/*      */     
/*      */     public boolean equals(K[][] a, K[][] b) {
/*  587 */       return ObjectBigArrays.equals(a, b);
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
/*  600 */   public static final Hash.Strategy HASH_STRATEGY = new BigArrayHashStrategy();
/*      */   
/*      */   private static final int QUICKSORT_NO_REC = 7;
/*      */   private static final int PARALLEL_QUICKSORT_NO_FORK = 8192;
/*      */   private static final int MEDIUM = 40;
/*      */   
/*      */   private static ForkJoinPool getPool() {
/*  607 */     ForkJoinPool current = ForkJoinTask.getPool();
/*  608 */     return (current == null) ? ForkJoinPool.commonPool() : current;
/*      */   }
/*      */   
/*      */   private static <K> void swap(K[][] x, long a, long b, long n) {
/*  612 */     for (int i = 0; i < n; ) { BigArrays.swap((Object[][])x, a, b); i++; a++; b++; }
/*      */   
/*      */   }
/*      */   private static <K> long med3(K[][] x, long a, long b, long c, Comparator<K> comp) {
/*  616 */     int ab = comp.compare((K)BigArrays.get((Object[][])x, a), (K)BigArrays.get((Object[][])x, b));
/*  617 */     int ac = comp.compare((K)BigArrays.get((Object[][])x, a), (K)BigArrays.get((Object[][])x, c));
/*  618 */     int bc = comp.compare((K)BigArrays.get((Object[][])x, b), (K)BigArrays.get((Object[][])x, c));
/*  619 */     return (ab < 0) ? ((bc < 0) ? b : ((ac < 0) ? c : a)) : ((bc > 0) ? b : ((ac > 0) ? c : a));
/*      */   }
/*      */   private static <K> void selectionSort(K[][] a, long from, long to, Comparator<K> comp) {
/*      */     long i;
/*  623 */     for (i = from; i < to - 1L; i++) {
/*  624 */       long m = i; long j;
/*  625 */       for (j = i + 1L; j < to; ) { if (comp.compare((K)BigArrays.get((Object[][])a, j), (K)BigArrays.get((Object[][])a, m)) < 0) m = j;  j++; }
/*  626 */        if (m != i) BigArrays.swap((Object[][])a, i, m);
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
/*      */   public static <K> void quickSort(K[][] x, long from, long to, Comparator<K> comp) {
/*  645 */     long len = to - from;
/*      */     
/*  647 */     if (len < 7L) {
/*  648 */       selectionSort(x, from, to, comp);
/*      */       
/*      */       return;
/*      */     } 
/*  652 */     long m = from + len / 2L;
/*  653 */     if (len > 7L) {
/*  654 */       long l = from;
/*  655 */       long n = to - 1L;
/*  656 */       if (len > 40L) {
/*  657 */         long s = len / 8L;
/*  658 */         l = med3(x, l, l + s, l + 2L * s, comp);
/*  659 */         m = med3(x, m - s, m, m + s, comp);
/*  660 */         n = med3(x, n - 2L * s, n - s, n, comp);
/*      */       } 
/*  662 */       m = med3(x, l, m, n, comp);
/*      */     } 
/*  664 */     K v = (K)BigArrays.get((Object[][])x, m);
/*      */     
/*  666 */     long a = from, b = a, c = to - 1L, d = c;
/*      */     
/*      */     int comparison;
/*  669 */     while (b <= c && (comparison = comp.compare((K)BigArrays.get((Object[][])x, b), v)) <= 0) {
/*  670 */       if (comparison == 0) BigArrays.swap((Object[][])x, a++, b); 
/*  671 */       b++;
/*      */     } 
/*  673 */     while (c >= b && (comparison = comp.compare((K)BigArrays.get((Object[][])x, c), v)) >= 0) {
/*  674 */       if (comparison == 0) BigArrays.swap((Object[][])x, c, d--); 
/*  675 */       c--;
/*      */     } 
/*  677 */     if (b > c) {
/*      */ 
/*      */ 
/*      */       
/*  681 */       long n = to;
/*  682 */       long s = Math.min(a - from, b - a);
/*  683 */       swap(x, from, b - s, s);
/*  684 */       s = Math.min(d - c, n - d - 1L);
/*  685 */       swap(x, b, n - s, s);
/*      */       
/*  687 */       if ((s = b - a) > 1L) quickSort(x, from, from + s, comp); 
/*  688 */       if ((s = d - c) > 1L) quickSort(x, n - s, n, comp); 
/*      */       return;
/*      */     } 
/*      */     BigArrays.swap((Object[][])x, b++, c--);
/*      */   } private static <K> long med3(K[][] x, long a, long b, long c) {
/*  693 */     int ab = ((Comparable<Object>)BigArrays.get((Object[][])x, a)).compareTo(BigArrays.get((Object[][])x, b));
/*  694 */     int ac = ((Comparable<Object>)BigArrays.get((Object[][])x, a)).compareTo(BigArrays.get((Object[][])x, c));
/*  695 */     int bc = ((Comparable<Object>)BigArrays.get((Object[][])x, b)).compareTo(BigArrays.get((Object[][])x, c));
/*  696 */     return (ab < 0) ? ((bc < 0) ? b : ((ac < 0) ? c : a)) : ((bc > 0) ? b : ((ac > 0) ? c : a));
/*      */   }
/*      */   
/*      */   private static <K> void selectionSort(K[][] a, long from, long to) {
/*      */     long i;
/*  701 */     for (i = from; i < to - 1L; i++) {
/*  702 */       long m = i; long j;
/*  703 */       for (j = i + 1L; j < to; ) { if (((Comparable<Object>)BigArrays.get((Object[][])a, j)).compareTo(BigArrays.get((Object[][])a, m)) < 0) m = j;  j++; }
/*  704 */        if (m != i) BigArrays.swap((Object[][])a, i, m);
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
/*      */   public static <K> void quickSort(K[][] x, Comparator<K> comp) {
/*  722 */     quickSort(x, 0L, BigArrays.length((Object[][])x), comp);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static <K> void quickSort(K[][] x, long from, long to) {
/*  739 */     long len = to - from;
/*      */     
/*  741 */     if (len < 7L) {
/*  742 */       selectionSort(x, from, to);
/*      */       
/*      */       return;
/*      */     } 
/*  746 */     long m = from + len / 2L;
/*  747 */     if (len > 7L) {
/*  748 */       long l = from;
/*  749 */       long n = to - 1L;
/*  750 */       if (len > 40L) {
/*  751 */         long s = len / 8L;
/*  752 */         l = med3(x, l, l + s, l + 2L * s);
/*  753 */         m = med3(x, m - s, m, m + s);
/*  754 */         n = med3(x, n - 2L * s, n - s, n);
/*      */       } 
/*  756 */       m = med3(x, l, m, n);
/*      */     } 
/*  758 */     K v = (K)BigArrays.get((Object[][])x, m);
/*      */     
/*  760 */     long a = from, b = a, c = to - 1L, d = c;
/*      */     
/*      */     int comparison;
/*  763 */     while (b <= c && (comparison = ((Comparable<K>)BigArrays.get((Object[][])x, b)).compareTo(v)) <= 0) {
/*  764 */       if (comparison == 0) BigArrays.swap((Object[][])x, a++, b); 
/*  765 */       b++;
/*      */     } 
/*  767 */     while (c >= b && (comparison = ((Comparable<K>)BigArrays.get((Object[][])x, c)).compareTo(v)) >= 0) {
/*  768 */       if (comparison == 0) BigArrays.swap((Object[][])x, c, d--); 
/*  769 */       c--;
/*      */     } 
/*  771 */     if (b > c) {
/*      */ 
/*      */ 
/*      */       
/*  775 */       long n = to;
/*  776 */       long s = Math.min(a - from, b - a);
/*  777 */       swap(x, from, b - s, s);
/*  778 */       s = Math.min(d - c, n - d - 1L);
/*  779 */       swap(x, b, n - s, s);
/*      */       
/*  781 */       if ((s = b - a) > 1L) quickSort(x, from, from + s); 
/*  782 */       if ((s = d - c) > 1L) quickSort(x, n - s, n);
/*      */       
/*      */       return;
/*      */     } 
/*      */     BigArrays.swap((Object[][])x, b++, c--);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static <K> void quickSort(K[][] x) {
/*  796 */     quickSort(x, 0L, BigArrays.length((Object[][])x));
/*      */   }
/*      */   
/*      */   protected static class ForkJoinQuickSort<K> extends RecursiveAction {
/*      */     private static final long serialVersionUID = 1L;
/*      */     private final long from;
/*      */     private final long to;
/*      */     private final K[][] x;
/*      */     
/*      */     public ForkJoinQuickSort(K[][] x, long from, long to) {
/*  806 */       this.from = from;
/*  807 */       this.to = to;
/*  808 */       this.x = x;
/*      */     }
/*      */ 
/*      */ 
/*      */     
/*      */     protected void compute() {
/*  814 */       K[][] x = this.x;
/*  815 */       long len = this.to - this.from;
/*  816 */       if (len < 8192L) {
/*  817 */         ObjectBigArrays.quickSort(x, this.from, this.to);
/*      */         
/*      */         return;
/*      */       } 
/*  821 */       long m = this.from + len / 2L;
/*  822 */       long l = this.from;
/*  823 */       long n = this.to - 1L;
/*  824 */       long s = len / 8L;
/*  825 */       l = ObjectBigArrays.med3(x, l, l + s, l + 2L * s);
/*  826 */       m = ObjectBigArrays.med3(x, m - s, m, m + s);
/*  827 */       n = ObjectBigArrays.med3(x, n - 2L * s, n - s, n);
/*  828 */       m = ObjectBigArrays.med3(x, l, m, n);
/*  829 */       K v = (K)BigArrays.get((Object[][])x, m);
/*      */       
/*  831 */       long a = this.from, b = a, c = this.to - 1L, d = c;
/*      */       
/*      */       int comparison;
/*  834 */       while (b <= c && (comparison = ((Comparable<K>)BigArrays.get((Object[][])x, b)).compareTo(v)) <= 0) {
/*  835 */         if (comparison == 0) BigArrays.swap((Object[][])x, a++, b); 
/*  836 */         b++;
/*      */       } 
/*  838 */       while (c >= b && (comparison = ((Comparable<K>)BigArrays.get((Object[][])x, c)).compareTo(v)) >= 0) {
/*  839 */         if (comparison == 0) BigArrays.swap((Object[][])x, c, d--); 
/*  840 */         c--;
/*      */       } 
/*  842 */       if (b > c) {
/*      */ 
/*      */ 
/*      */ 
/*      */         
/*  847 */         s = Math.min(a - this.from, b - a);
/*  848 */         ObjectBigArrays.swap(x, this.from, b - s, s);
/*  849 */         s = Math.min(d - c, this.to - d - 1L);
/*  850 */         ObjectBigArrays.swap(x, b, this.to - s, s);
/*      */         
/*  852 */         s = b - a;
/*  853 */         long t = d - c;
/*  854 */         if (s > 1L && t > 1L) { invokeAll(new ForkJoinQuickSort(x, this.from, this.from + s), new ForkJoinQuickSort(x, this.to - t, this.to)); }
/*  855 */         else if (s > 1L) { invokeAll((ForkJoinTask<?>[])new ForkJoinTask[] { new ForkJoinQuickSort(x, this.from, this.from + s) }); }
/*  856 */         else { invokeAll((ForkJoinTask<?>[])new ForkJoinTask[] { new ForkJoinQuickSort(x, this.to - t, this.to) }); }
/*      */         
/*      */         return;
/*      */       } 
/*      */       BigArrays.swap((Object[][])x, b++, c--);
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
/*      */   public static <K> void parallelQuickSort(K[][] x, long from, long to) {
/*  874 */     ForkJoinPool pool = getPool();
/*  875 */     if (to - from < 8192L || pool.getParallelism() == 1) { quickSort(x, from, to); }
/*      */     else
/*  877 */     { pool.invoke(new ForkJoinQuickSort<>(x, from, to)); }
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
/*      */   public static <K> void parallelQuickSort(K[][] x) {
/*  892 */     parallelQuickSort(x, 0L, BigArrays.length((Object[][])x));
/*      */   }
/*      */   
/*      */   protected static class ForkJoinQuickSortComp<K> extends RecursiveAction {
/*      */     private static final long serialVersionUID = 1L;
/*      */     private final long from;
/*      */     private final long to;
/*      */     private final K[][] x;
/*      */     private final Comparator<K> comp;
/*      */     
/*      */     public ForkJoinQuickSortComp(K[][] x, long from, long to, Comparator<K> comp) {
/*  903 */       this.from = from;
/*  904 */       this.to = to;
/*  905 */       this.x = x;
/*  906 */       this.comp = comp;
/*      */     }
/*      */ 
/*      */     
/*      */     protected void compute() {
/*  911 */       K[][] x = this.x;
/*  912 */       long len = this.to - this.from;
/*  913 */       if (len < 8192L) {
/*  914 */         ObjectBigArrays.quickSort(x, this.from, this.to, this.comp);
/*      */         
/*      */         return;
/*      */       } 
/*  918 */       long m = this.from + len / 2L;
/*  919 */       long l = this.from;
/*  920 */       long n = this.to - 1L;
/*  921 */       long s = len / 8L;
/*  922 */       l = ObjectBigArrays.med3(x, l, l + s, l + 2L * s, this.comp);
/*  923 */       m = ObjectBigArrays.med3(x, m - s, m, m + s, this.comp);
/*  924 */       n = ObjectBigArrays.med3(x, n - 2L * s, n - s, n, this.comp);
/*  925 */       m = ObjectBigArrays.med3(x, l, m, n, this.comp);
/*  926 */       K v = (K)BigArrays.get((Object[][])x, m);
/*      */       
/*  928 */       long a = this.from, b = a, c = this.to - 1L, d = c;
/*      */       
/*      */       int comparison;
/*  931 */       while (b <= c && (comparison = this.comp.compare((K)BigArrays.get((Object[][])x, b), v)) <= 0) {
/*  932 */         if (comparison == 0) BigArrays.swap((Object[][])x, a++, b); 
/*  933 */         b++;
/*      */       } 
/*  935 */       while (c >= b && (comparison = this.comp.compare((K)BigArrays.get((Object[][])x, c), v)) >= 0) {
/*  936 */         if (comparison == 0) BigArrays.swap((Object[][])x, c, d--); 
/*  937 */         c--;
/*      */       } 
/*  939 */       if (b > c) {
/*      */ 
/*      */ 
/*      */ 
/*      */         
/*  944 */         s = Math.min(a - this.from, b - a);
/*  945 */         ObjectBigArrays.swap(x, this.from, b - s, s);
/*  946 */         s = Math.min(d - c, this.to - d - 1L);
/*  947 */         ObjectBigArrays.swap(x, b, this.to - s, s);
/*      */         
/*  949 */         s = b - a;
/*  950 */         long t = d - c;
/*  951 */         if (s > 1L && t > 1L) { invokeAll(new ForkJoinQuickSortComp(x, this.from, this.from + s, this.comp), new ForkJoinQuickSortComp(x, this.to - t, this.to, this.comp)); }
/*  952 */         else if (s > 1L) { invokeAll((ForkJoinTask<?>[])new ForkJoinTask[] { new ForkJoinQuickSortComp(x, this.from, this.from + s, this.comp) }); }
/*  953 */         else { invokeAll((ForkJoinTask<?>[])new ForkJoinTask[] { new ForkJoinQuickSortComp(x, this.to - t, this.to, this.comp) }); }
/*      */         
/*      */         return;
/*      */       } 
/*      */       BigArrays.swap((Object[][])x, b++, c--);
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
/*      */   public static <K> void parallelQuickSort(K[][] x, long from, long to, Comparator<K> comp) {
/*  972 */     ForkJoinPool pool = getPool();
/*  973 */     if (to - from < 8192L || pool.getParallelism() == 1) { quickSort(x, from, to, comp); }
/*      */     else
/*  975 */     { pool.invoke(new ForkJoinQuickSortComp<>(x, from, to, comp)); }
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
/*      */   public static <K> void parallelQuickSort(K[][] x, Comparator<K> comp) {
/*  992 */     parallelQuickSort(x, 0L, BigArrays.length((Object[][])x), comp);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static <K> long binarySearch(K[][] a, long from, long to, K key) {
/* 1016 */     to--;
/* 1017 */     while (from <= to) {
/* 1018 */       long mid = from + to >>> 1L;
/* 1019 */       K midVal = (K)BigArrays.get((Object[][])a, mid);
/* 1020 */       int cmp = ((Comparable<K>)midVal).compareTo(key);
/* 1021 */       if (cmp < 0) { from = mid + 1L; continue; }
/* 1022 */        if (cmp > 0) { to = mid - 1L; continue; }
/* 1023 */        return mid;
/*      */     } 
/* 1025 */     return -(from + 1L);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static <K> long binarySearch(K[][] a, Object key) {
/* 1045 */     return binarySearch(a, 0L, BigArrays.length((Object[][])a), (K)key);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static <K> long binarySearch(K[][] a, long from, long to, K key, Comparator<K> c) {
/* 1069 */     to--;
/* 1070 */     while (from <= to) {
/* 1071 */       long mid = from + to >>> 1L;
/* 1072 */       K midVal = (K)BigArrays.get((Object[][])a, mid);
/* 1073 */       int cmp = c.compare(midVal, key);
/* 1074 */       if (cmp < 0) { from = mid + 1L; continue; }
/* 1075 */        if (cmp > 0) { to = mid - 1L; continue; }
/* 1076 */        return mid;
/*      */     } 
/* 1078 */     return -(from + 1L);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static <K> long binarySearch(K[][] a, K key, Comparator<K> c) {
/* 1099 */     return binarySearch(a, 0L, BigArrays.length((Object[][])a), key, c);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static <K> K[][] shuffle(K[][] a, long from, long to, Random random) {
/* 1112 */     return (K[][])BigArrays.shuffle((Object[][])a, from, to, random);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static <K> K[][] shuffle(K[][] a, Random random) {
/* 1123 */     return (K[][])BigArrays.shuffle((Object[][])a, random);
/*      */   }
/*      */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\i\\unimi\dsi\fastutil\objects\ObjectBigArrays.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */