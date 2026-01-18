/*     */ package it.unimi.dsi.fastutil;
/*     */ 
/*     */ import it.unimi.dsi.fastutil.ints.IntComparator;
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
/*     */ 
/*     */ public class Arrays
/*     */ {
/*     */   public static final int MAX_ARRAY_SIZE = 2147483639;
/*     */   private static final int MERGESORT_NO_REC = 16;
/*     */   private static final int QUICKSORT_NO_REC = 16;
/*     */   private static final int PARALLEL_QUICKSORT_NO_FORK = 8192;
/*     */   private static final int QUICKSORT_MEDIAN_OF_9 = 128;
/*     */   
/*     */   public static void ensureFromTo(int arrayLength, int from, int to) {
/*  73 */     assert arrayLength >= 0;
/*     */     
/*  75 */     if (from < 0) throw new ArrayIndexOutOfBoundsException("Start index (" + from + ") is negative"); 
/*  76 */     if (from > to) throw new IllegalArgumentException("Start index (" + from + ") is greater than end index (" + to + ")"); 
/*  77 */     if (to > arrayLength) throw new ArrayIndexOutOfBoundsException("End index (" + to + ") is greater than array length (" + arrayLength + ")");
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static void ensureOffsetLength(int arrayLength, int offset, int length) {
/* 101 */     assert arrayLength >= 0;
/*     */     
/* 103 */     if (offset < 0) throw new ArrayIndexOutOfBoundsException("Offset (" + offset + ") is negative"); 
/* 104 */     if (length < 0) throw new IllegalArgumentException("Length (" + length + ") is negative"); 
/* 105 */     if (length > arrayLength - offset) throw new ArrayIndexOutOfBoundsException("Last index (" + (offset + length) + ") is greater than array length (" + arrayLength + ")");
/*     */   
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static void inPlaceMerge(int from, int mid, int to, IntComparator comp, Swapper swapper) {
/*     */     int firstCut, secondCut;
/* 115 */     if (from >= mid || mid >= to)
/* 116 */       return;  if (to - from == 2) {
/* 117 */       if (comp.compare(mid, from) < 0) swapper.swap(from, mid);
/*     */ 
/*     */ 
/*     */       
/*     */       return;
/*     */     } 
/*     */     
/* 124 */     if (mid - from > to - mid) {
/* 125 */       firstCut = from + (mid - from) / 2;
/* 126 */       secondCut = lowerBound(mid, to, firstCut, comp);
/*     */     } else {
/*     */       
/* 129 */       secondCut = mid + (to - mid) / 2;
/* 130 */       firstCut = upperBound(from, mid, secondCut, comp);
/*     */     } 
/*     */     
/* 133 */     int first2 = firstCut;
/* 134 */     int middle2 = mid;
/* 135 */     int last2 = secondCut;
/* 136 */     if (middle2 != first2 && middle2 != last2) {
/* 137 */       int first1 = first2;
/* 138 */       int last1 = middle2;
/* 139 */       while (first1 < --last1)
/* 140 */         swapper.swap(first1++, last1); 
/* 141 */       first1 = middle2;
/* 142 */       last1 = last2;
/* 143 */       while (first1 < --last1)
/* 144 */         swapper.swap(first1++, last1); 
/* 145 */       first1 = first2;
/* 146 */       last1 = last2;
/* 147 */       while (first1 < --last1) {
/* 148 */         swapper.swap(first1++, last1);
/*     */       }
/*     */     } 
/* 151 */     mid = firstCut + secondCut - mid;
/* 152 */     inPlaceMerge(from, firstCut, mid, comp, swapper);
/* 153 */     inPlaceMerge(mid, secondCut, to, comp, swapper);
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
/*     */   private static int lowerBound(int from, int to, int pos, IntComparator comp) {
/* 170 */     int len = to - from;
/* 171 */     while (len > 0) {
/* 172 */       int half = len / 2;
/* 173 */       int middle = from + half;
/* 174 */       if (comp.compare(middle, pos) < 0) {
/* 175 */         from = middle + 1;
/* 176 */         len -= half + 1;
/*     */         continue;
/*     */       } 
/* 179 */       len = half;
/*     */     } 
/*     */     
/* 182 */     return from;
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
/*     */   private static int upperBound(int from, int mid, int pos, IntComparator comp) {
/* 200 */     int len = mid - from;
/* 201 */     while (len > 0) {
/* 202 */       int half = len / 2;
/* 203 */       int middle = from + half;
/* 204 */       if (comp.compare(pos, middle) < 0) {
/* 205 */         len = half;
/*     */         continue;
/*     */       } 
/* 208 */       from = middle + 1;
/* 209 */       len -= half + 1;
/*     */     } 
/*     */     
/* 212 */     return from;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static int med3(int a, int b, int c, IntComparator comp) {
/* 219 */     int ab = comp.compare(a, b);
/* 220 */     int ac = comp.compare(a, c);
/* 221 */     int bc = comp.compare(b, c);
/* 222 */     return (ab < 0) ? (
/* 223 */       (bc < 0) ? b : ((ac < 0) ? c : a)) : (
/* 224 */       (bc > 0) ? b : ((ac > 0) ? c : a));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static ForkJoinPool getPool() {
/* 231 */     ForkJoinPool current = ForkJoinTask.getPool();
/* 232 */     return (current == null) ? ForkJoinPool.commonPool() : current;
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
/*     */   public static void mergeSort(int from, int to, IntComparator c, Swapper swapper) {
/* 256 */     int length = to - from;
/*     */ 
/*     */     
/* 259 */     if (length < 16) {
/* 260 */       for (int i = from; i < to; i++) {
/* 261 */         for (int j = i; j > from && c.compare(j - 1, j) > 0; j--) {
/* 262 */           swapper.swap(j, j - 1);
/*     */         }
/*     */       } 
/*     */       
/*     */       return;
/*     */     } 
/*     */     
/* 269 */     int mid = from + to >>> 1;
/* 270 */     mergeSort(from, mid, c, swapper);
/* 271 */     mergeSort(mid, to, c, swapper);
/*     */ 
/*     */ 
/*     */     
/* 275 */     if (c.compare(mid - 1, mid) <= 0) {
/*     */       return;
/*     */     }
/* 278 */     inPlaceMerge(from, mid, to, c, swapper);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected static void swap(Swapper swapper, int a, int b, int n) {
/* 289 */     for (int i = 0; i < n; ) { swapper.swap(a, b); i++; a++; b++; }
/*     */   
/*     */   }
/*     */ 
/*     */   
/*     */   protected static class ForkJoinGenericQuickSort
/*     */     extends RecursiveAction
/*     */   {
/*     */     private static final long serialVersionUID = 1L;
/*     */     private final int from;
/*     */     private final int to;
/*     */     private final IntComparator comp;
/*     */     private final Swapper swapper;
/*     */     
/*     */     public ForkJoinGenericQuickSort(int from, int to, IntComparator comp, Swapper swapper) {
/* 304 */       this.from = from;
/* 305 */       this.to = to;
/* 306 */       this.comp = comp;
/* 307 */       this.swapper = swapper;
/*     */     }
/*     */ 
/*     */     
/*     */     protected void compute() {
/* 312 */       int len = this.to - this.from;
/* 313 */       if (len < 8192) {
/* 314 */         Arrays.quickSort(this.from, this.to, this.comp, this.swapper);
/*     */         
/*     */         return;
/*     */       } 
/* 318 */       int m = this.from + len / 2;
/* 319 */       int l = this.from;
/* 320 */       int n = this.to - 1;
/* 321 */       int s = len / 8;
/* 322 */       l = Arrays.med3(l, l + s, l + 2 * s, this.comp);
/* 323 */       m = Arrays.med3(m - s, m, m + s, this.comp);
/* 324 */       n = Arrays.med3(n - 2 * s, n - s, n, this.comp);
/* 325 */       m = Arrays.med3(l, m, n, this.comp);
/*     */       
/* 327 */       int a = this.from, b = a, c = this.to - 1, d = c;
/*     */       while (true) {
/*     */         int comparison;
/* 330 */         if (b <= c && (comparison = this.comp.compare(b, m)) <= 0) {
/* 331 */           if (comparison == 0) {
/*     */             
/* 333 */             if (a == m) { m = b; }
/* 334 */             else if (b == m) { m = a; }
/* 335 */              this.swapper.swap(a++, b);
/*     */           } 
/* 337 */           b++; continue;
/*     */         } 
/* 339 */         while (c >= b && (comparison = this.comp.compare(c, m)) >= 0) {
/* 340 */           if (comparison == 0) {
/*     */             
/* 342 */             if (c == m) { m = d; }
/* 343 */             else if (d == m) { m = c; }
/* 344 */              this.swapper.swap(c, d--);
/*     */           } 
/* 346 */           c--;
/*     */         } 
/* 348 */         if (b > c)
/*     */           break; 
/* 350 */         if (b == m) { m = d; }
/* 351 */         else if (c == m) { m = c; }
/* 352 */          this.swapper.swap(b++, c--);
/*     */       } 
/*     */ 
/*     */       
/* 356 */       s = Math.min(a - this.from, b - a);
/* 357 */       Arrays.swap(this.swapper, this.from, b - s, s);
/* 358 */       s = Math.min(d - c, this.to - d - 1);
/* 359 */       Arrays.swap(this.swapper, b, this.to - s, s);
/*     */ 
/*     */ 
/*     */       
/* 363 */       s = b - a;
/* 364 */       int t = d - c;
/* 365 */       if (s > 1 && t > 1) { invokeAll(new ForkJoinGenericQuickSort(this.from, this.from + s, this.comp, this.swapper), new ForkJoinGenericQuickSort(this.to - t, this.to, this.comp, this.swapper)); }
/* 366 */       else if (s > 1) { invokeAll((ForkJoinTask<?>[])new ForkJoinTask[] { new ForkJoinGenericQuickSort(this.from, this.from + s, this.comp, this.swapper) }); }
/* 367 */       else { invokeAll((ForkJoinTask<?>[])new ForkJoinTask[] { new ForkJoinGenericQuickSort(this.to - t, this.to, this.comp, this.swapper) }); }
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
/*     */   public static void parallelQuickSort(int from, int to, IntComparator comp, Swapper swapper) {
/* 385 */     ForkJoinPool pool = getPool();
/* 386 */     if (to - from < 8192 || pool.getParallelism() == 1) { quickSort(from, to, comp, swapper); }
/*     */     else
/* 388 */     { pool.invoke(new ForkJoinGenericQuickSort(from, to, comp, swapper)); }
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
/*     */ 
/*     */   
/*     */   public static void quickSort(int from, int to, IntComparator comp, Swapper swapper) {
/* 407 */     int len = to - from;
/*     */     
/* 409 */     if (len < 16) {
/* 410 */       for (int i = from; i < to; i++) {
/* 411 */         for (int j = i; j > from && comp.compare(j - 1, j) > 0; j--) {
/* 412 */           swapper.swap(j, j - 1);
/*     */         }
/*     */       } 
/*     */       
/*     */       return;
/*     */     } 
/* 418 */     int m = from + len / 2;
/* 419 */     int l = from;
/* 420 */     int n = to - 1;
/* 421 */     if (len > 128) {
/* 422 */       int i = len / 8;
/* 423 */       l = med3(l, l + i, l + 2 * i, comp);
/* 424 */       m = med3(m - i, m, m + i, comp);
/* 425 */       n = med3(n - 2 * i, n - i, n, comp);
/*     */     } 
/* 427 */     m = med3(l, m, n, comp);
/*     */ 
/*     */     
/* 430 */     int a = from;
/* 431 */     int b = a;
/* 432 */     int c = to - 1;
/*     */     
/* 434 */     int d = c;
/*     */     while (true) {
/*     */       int comparison;
/* 437 */       if (b <= c && (comparison = comp.compare(b, m)) <= 0) {
/* 438 */         if (comparison == 0) {
/*     */           
/* 440 */           if (a == m) { m = b; }
/* 441 */           else if (b == m) { m = a; }
/* 442 */            swapper.swap(a++, b);
/*     */         } 
/* 444 */         b++; continue;
/*     */       } 
/* 446 */       while (c >= b && (comparison = comp.compare(c, m)) >= 0) {
/* 447 */         if (comparison == 0) {
/*     */           
/* 449 */           if (c == m) { m = d; }
/* 450 */           else if (d == m) { m = c; }
/* 451 */            swapper.swap(c, d--);
/*     */         } 
/* 453 */         c--;
/*     */       } 
/* 455 */       if (b > c)
/*     */         break; 
/* 457 */       if (b == m) { m = d; }
/* 458 */       else if (c == m) { m = c; }
/* 459 */        swapper.swap(b++, c--);
/*     */     } 
/*     */ 
/*     */ 
/*     */     
/* 464 */     int s = Math.min(a - from, b - a);
/* 465 */     swap(swapper, from, b - s, s);
/* 466 */     s = Math.min(d - c, to - d - 1);
/* 467 */     swap(swapper, b, to - s, s);
/*     */ 
/*     */     
/* 470 */     if ((s = b - a) > 1) quickSort(from, from + s, comp, swapper); 
/* 471 */     if ((s = d - c) > 1) quickSort(to - s, to, comp, swapper); 
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\i\\unimi\dsi\fastutil\Arrays.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */