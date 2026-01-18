/*      */ package it.unimi.dsi.fastutil;
/*      */ 
/*      */ import it.unimi.dsi.fastutil.booleans.BooleanArrays;
/*      */ import it.unimi.dsi.fastutil.booleans.BooleanBigArrays;
/*      */ import it.unimi.dsi.fastutil.bytes.ByteArrays;
/*      */ import it.unimi.dsi.fastutil.bytes.ByteBigArrays;
/*      */ import it.unimi.dsi.fastutil.chars.CharArrays;
/*      */ import it.unimi.dsi.fastutil.chars.CharBigArrays;
/*      */ import it.unimi.dsi.fastutil.doubles.DoubleArrays;
/*      */ import it.unimi.dsi.fastutil.doubles.DoubleBigArrays;
/*      */ import it.unimi.dsi.fastutil.floats.FloatArrays;
/*      */ import it.unimi.dsi.fastutil.floats.FloatBigArrays;
/*      */ import it.unimi.dsi.fastutil.ints.IntArrays;
/*      */ import it.unimi.dsi.fastutil.ints.IntBigArrays;
/*      */ import it.unimi.dsi.fastutil.longs.LongArrays;
/*      */ import it.unimi.dsi.fastutil.longs.LongBigArrays;
/*      */ import it.unimi.dsi.fastutil.longs.LongComparator;
/*      */ import it.unimi.dsi.fastutil.objects.ObjectArrays;
/*      */ import it.unimi.dsi.fastutil.objects.ObjectBigArrays;
/*      */ import it.unimi.dsi.fastutil.shorts.ShortArrays;
/*      */ import it.unimi.dsi.fastutil.shorts.ShortBigArrays;
/*      */ import java.lang.reflect.Array;
/*      */ import java.util.Arrays;
/*      */ import java.util.Objects;
/*      */ import java.util.Random;
/*      */ import java.util.concurrent.atomic.AtomicIntegerArray;
/*      */ import java.util.concurrent.atomic.AtomicLongArray;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ public class BigArrays
/*      */ {
/*      */   public static final int SEGMENT_SHIFT = 27;
/*      */   public static final int SEGMENT_SIZE = 134217728;
/*      */   public static final int SEGMENT_MASK = 134217727;
/*      */   private static final int SMALL = 7;
/*      */   private static final int MEDIUM = 40;
/*      */   
/*      */   public static int segment(long index) {
/*  217 */     return (int)(index >>> 27L);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static int displacement(long index) {
/*  227 */     return (int)(index & 0x7FFFFFFL);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static long start(int segment) {
/*  237 */     return segment << 27L;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static long nearestSegmentStart(long index, long min, long max) {
/*  265 */     long lower = start(segment(index));
/*  266 */     long upper = start(segment(index) + 1);
/*  267 */     if (upper >= max) {
/*  268 */       if (lower < min) {
/*  269 */         return index;
/*      */       }
/*  271 */       return lower;
/*      */     } 
/*  273 */     if (lower < min) return upper;
/*      */     
/*  275 */     long mid = lower + (upper - lower >> 1L);
/*  276 */     return (index <= mid) ? lower : upper;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static long index(int segment, int displacement) {
/*  289 */     return start(segment) + displacement;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static void ensureFromTo(long bigArrayLength, long from, long to) {
/*  309 */     assert bigArrayLength >= 0L;
/*  310 */     if (from < 0L) throw new ArrayIndexOutOfBoundsException("Start index (" + from + ") is negative"); 
/*  311 */     if (from > to) throw new IllegalArgumentException("Start index (" + from + ") is greater than end index (" + to + ")"); 
/*  312 */     if (to > bigArrayLength) throw new ArrayIndexOutOfBoundsException("End index (" + to + ") is greater than big-array length (" + bigArrayLength + ")");
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
/*      */   public static void ensureOffsetLength(long bigArrayLength, long offset, long length) {
/*  331 */     assert bigArrayLength >= 0L;
/*  332 */     if (offset < 0L) throw new ArrayIndexOutOfBoundsException("Offset (" + offset + ") is negative"); 
/*  333 */     if (length < 0L) throw new IllegalArgumentException("Length (" + length + ") is negative"); 
/*  334 */     if (length > bigArrayLength - offset) throw new ArrayIndexOutOfBoundsException("Last index (" + Long.toUnsignedString(offset + length) + ") is greater than big-array length (" + bigArrayLength + ")");
/*      */   
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static void ensureLength(long bigArrayLength) {
/*  345 */     if (bigArrayLength < 0L) throw new IllegalArgumentException("Negative big-array size: " + bigArrayLength); 
/*  346 */     if (bigArrayLength >= 288230376017494016L) throw new IllegalArgumentException("Big-array size too big: " + bigArrayLength);
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
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private static void inPlaceMerge(long from, long mid, long to, LongComparator comp, BigSwapper swapper) {
/*      */     // Byte code:
/*      */     //   0: lload_0
/*      */     //   1: lload_2
/*      */     //   2: lcmp
/*      */     //   3: ifge -> 13
/*      */     //   6: lload_2
/*      */     //   7: lload #4
/*      */     //   9: lcmp
/*      */     //   10: iflt -> 14
/*      */     //   13: return
/*      */     //   14: lload #4
/*      */     //   16: lload_0
/*      */     //   17: lsub
/*      */     //   18: ldc2_w 2
/*      */     //   21: lcmp
/*      */     //   22: ifne -> 47
/*      */     //   25: aload #6
/*      */     //   27: lload_2
/*      */     //   28: lload_0
/*      */     //   29: invokeinterface compare : (JJ)I
/*      */     //   34: ifge -> 46
/*      */     //   37: aload #7
/*      */     //   39: lload_0
/*      */     //   40: lload_2
/*      */     //   41: invokeinterface swap : (JJ)V
/*      */     //   46: return
/*      */     //   47: lload_2
/*      */     //   48: lload_0
/*      */     //   49: lsub
/*      */     //   50: lload #4
/*      */     //   52: lload_2
/*      */     //   53: lsub
/*      */     //   54: lcmp
/*      */     //   55: ifle -> 84
/*      */     //   58: lload_0
/*      */     //   59: lload_2
/*      */     //   60: lload_0
/*      */     //   61: lsub
/*      */     //   62: ldc2_w 2
/*      */     //   65: ldiv
/*      */     //   66: ladd
/*      */     //   67: lstore #8
/*      */     //   69: lload_2
/*      */     //   70: lload #4
/*      */     //   72: lload #8
/*      */     //   74: aload #6
/*      */     //   76: invokestatic lowerBound : (JJJLit/unimi/dsi/fastutil/longs/LongComparator;)J
/*      */     //   79: lstore #10
/*      */     //   81: goto -> 107
/*      */     //   84: lload_2
/*      */     //   85: lload #4
/*      */     //   87: lload_2
/*      */     //   88: lsub
/*      */     //   89: ldc2_w 2
/*      */     //   92: ldiv
/*      */     //   93: ladd
/*      */     //   94: lstore #10
/*      */     //   96: lload_0
/*      */     //   97: lload_2
/*      */     //   98: lload #10
/*      */     //   100: aload #6
/*      */     //   102: invokestatic upperBound : (JJJLit/unimi/dsi/fastutil/longs/LongComparator;)J
/*      */     //   105: lstore #8
/*      */     //   107: lload #8
/*      */     //   109: lstore #12
/*      */     //   111: lload_2
/*      */     //   112: lstore #14
/*      */     //   114: lload #10
/*      */     //   116: lstore #16
/*      */     //   118: lload #14
/*      */     //   120: lload #12
/*      */     //   122: lcmp
/*      */     //   123: ifeq -> 254
/*      */     //   126: lload #14
/*      */     //   128: lload #16
/*      */     //   130: lcmp
/*      */     //   131: ifeq -> 254
/*      */     //   134: lload #12
/*      */     //   136: lstore #18
/*      */     //   138: lload #14
/*      */     //   140: lstore #20
/*      */     //   142: lload #18
/*      */     //   144: lload #20
/*      */     //   146: lconst_1
/*      */     //   147: lsub
/*      */     //   148: dup2
/*      */     //   149: lstore #20
/*      */     //   151: lcmp
/*      */     //   152: ifge -> 174
/*      */     //   155: aload #7
/*      */     //   157: lload #18
/*      */     //   159: dup2
/*      */     //   160: lconst_1
/*      */     //   161: ladd
/*      */     //   162: lstore #18
/*      */     //   164: lload #20
/*      */     //   166: invokeinterface swap : (JJ)V
/*      */     //   171: goto -> 142
/*      */     //   174: lload #14
/*      */     //   176: lstore #18
/*      */     //   178: lload #16
/*      */     //   180: lstore #20
/*      */     //   182: lload #18
/*      */     //   184: lload #20
/*      */     //   186: lconst_1
/*      */     //   187: lsub
/*      */     //   188: dup2
/*      */     //   189: lstore #20
/*      */     //   191: lcmp
/*      */     //   192: ifge -> 214
/*      */     //   195: aload #7
/*      */     //   197: lload #18
/*      */     //   199: dup2
/*      */     //   200: lconst_1
/*      */     //   201: ladd
/*      */     //   202: lstore #18
/*      */     //   204: lload #20
/*      */     //   206: invokeinterface swap : (JJ)V
/*      */     //   211: goto -> 182
/*      */     //   214: lload #12
/*      */     //   216: lstore #18
/*      */     //   218: lload #16
/*      */     //   220: lstore #20
/*      */     //   222: lload #18
/*      */     //   224: lload #20
/*      */     //   226: lconst_1
/*      */     //   227: lsub
/*      */     //   228: dup2
/*      */     //   229: lstore #20
/*      */     //   231: lcmp
/*      */     //   232: ifge -> 254
/*      */     //   235: aload #7
/*      */     //   237: lload #18
/*      */     //   239: dup2
/*      */     //   240: lconst_1
/*      */     //   241: ladd
/*      */     //   242: lstore #18
/*      */     //   244: lload #20
/*      */     //   246: invokeinterface swap : (JJ)V
/*      */     //   251: goto -> 222
/*      */     //   254: lload #8
/*      */     //   256: lload #10
/*      */     //   258: lload_2
/*      */     //   259: lsub
/*      */     //   260: ladd
/*      */     //   261: lstore_2
/*      */     //   262: lload_0
/*      */     //   263: lload #8
/*      */     //   265: lload_2
/*      */     //   266: aload #6
/*      */     //   268: aload #7
/*      */     //   270: invokestatic inPlaceMerge : (JJJLit/unimi/dsi/fastutil/longs/LongComparator;Lit/unimi/dsi/fastutil/BigSwapper;)V
/*      */     //   273: lload_2
/*      */     //   274: lload #10
/*      */     //   276: lload #4
/*      */     //   278: aload #6
/*      */     //   280: aload #7
/*      */     //   282: invokestatic inPlaceMerge : (JJJLit/unimi/dsi/fastutil/longs/LongComparator;Lit/unimi/dsi/fastutil/BigSwapper;)V
/*      */     //   285: return
/*      */     // Line number table:
/*      */     //   Java source line number -> byte code offset
/*      */     //   #359	-> 0
/*      */     //   #360	-> 14
/*      */     //   #361	-> 25
/*      */     //   #362	-> 37
/*      */     //   #364	-> 46
/*      */     //   #368	-> 47
/*      */     //   #369	-> 58
/*      */     //   #370	-> 69
/*      */     //   #372	-> 84
/*      */     //   #373	-> 96
/*      */     //   #375	-> 107
/*      */     //   #376	-> 111
/*      */     //   #377	-> 114
/*      */     //   #378	-> 118
/*      */     //   #379	-> 134
/*      */     //   #380	-> 138
/*      */     //   #381	-> 142
/*      */     //   #382	-> 174
/*      */     //   #383	-> 178
/*      */     //   #384	-> 182
/*      */     //   #385	-> 214
/*      */     //   #386	-> 218
/*      */     //   #387	-> 222
/*      */     //   #389	-> 254
/*      */     //   #390	-> 262
/*      */     //   #391	-> 273
/*      */     //   #392	-> 285
/*      */     // Local variable table:
/*      */     //   start	length	slot	name	descriptor
/*      */     //   69	15	8	firstCut	J
/*      */     //   81	3	10	secondCut	J
/*      */     //   138	116	18	first1	J
/*      */     //   142	112	20	last1	J
/*      */     //   0	286	0	from	J
/*      */     //   0	286	2	mid	J
/*      */     //   0	286	4	to	J
/*      */     //   0	286	6	comp	Lit/unimi/dsi/fastutil/longs/LongComparator;
/*      */     //   0	286	7	swapper	Lit/unimi/dsi/fastutil/BigSwapper;
/*      */     //   107	179	8	firstCut	J
/*      */     //   96	190	10	secondCut	J
/*      */     //   111	175	12	first2	J
/*      */     //   114	172	14	middle2	J
/*      */     //   118	168	16	last2	J
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private static long lowerBound(long mid, long to, long firstCut, LongComparator comp) {
/*  407 */     long len = to - mid;
/*  408 */     while (len > 0L) {
/*  409 */       long half = len / 2L;
/*  410 */       long middle = mid + half;
/*  411 */       if (comp.compare(middle, firstCut) < 0) {
/*  412 */         mid = middle + 1L;
/*  413 */         len -= half + 1L; continue;
/*      */       } 
/*  415 */       len = half;
/*      */     } 
/*      */     
/*  418 */     return mid;
/*      */   }
/*      */ 
/*      */   
/*      */   private static long med3(long a, long b, long c, LongComparator comp) {
/*  423 */     int ab = comp.compare(a, b);
/*  424 */     int ac = comp.compare(a, c);
/*  425 */     int bc = comp.compare(b, c);
/*  426 */     return (ab < 0) ? ((bc < 0) ? b : ((ac < 0) ? c : a)) : ((bc > 0) ? b : ((ac > 0) ? c : a));
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static void mergeSort(long from, long to, LongComparator comp, BigSwapper swapper) {
/*  446 */     long length = to - from;
/*      */     
/*  448 */     if (length < 7L) {
/*  449 */       long i; for (i = from; i < to; i++) {
/*  450 */         long j; for (j = i; j > from && comp.compare(j - 1L, j) > 0; j--) {
/*  451 */           swapper.swap(j, j - 1L);
/*      */         }
/*      */       } 
/*      */       
/*      */       return;
/*      */     } 
/*  457 */     long mid = from + to >>> 1L;
/*  458 */     mergeSort(from, mid, comp, swapper);
/*  459 */     mergeSort(mid, to, comp, swapper);
/*      */ 
/*      */     
/*  462 */     if (comp.compare(mid - 1L, mid) <= 0)
/*      */       return; 
/*  464 */     inPlaceMerge(from, mid, to, comp, swapper);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static void quickSort(long from, long to, LongComparator comp, BigSwapper swapper) {
/*  482 */     long len = to - from;
/*      */     
/*  484 */     if (len < 7L) {
/*  485 */       long i; for (i = from; i < to; ) { long j; for (j = i; j > from && comp.compare(j - 1L, j) > 0; j--)
/*  486 */           swapper.swap(j, j - 1L); 
/*      */         i++; }
/*      */       
/*      */       return;
/*      */     } 
/*  491 */     long m = from + len / 2L;
/*  492 */     if (len > 7L) {
/*  493 */       long l = from, n = to - 1L;
/*  494 */       if (len > 40L) {
/*  495 */         long s = len / 8L;
/*  496 */         l = med3(l, l + s, l + 2L * s, comp);
/*  497 */         m = med3(m - s, m, m + s, comp);
/*  498 */         n = med3(n - 2L * s, n - s, n, comp);
/*      */       } 
/*  500 */       m = med3(l, m, n, comp);
/*      */     } 
/*      */     
/*  503 */     long a = from, b = a, c = to - 1L, d = c;
/*      */     
/*      */     int comparison;
/*      */     
/*  507 */     while (b <= c && (comparison = comp.compare(b, m)) <= 0) {
/*  508 */       if (comparison == 0) {
/*  509 */         if (a == m) { m = b; }
/*  510 */         else if (b == m) { m = a; }
/*  511 */          swapper.swap(a++, b);
/*      */       } 
/*  513 */       b++;
/*      */     } 
/*  515 */     while (c >= b && (comparison = comp.compare(c, m)) >= 0) {
/*  516 */       if (comparison == 0) {
/*  517 */         if (c == m) { m = d; }
/*  518 */         else if (d == m) { m = c; }
/*  519 */          swapper.swap(c, d--);
/*      */       } 
/*  521 */       c--;
/*      */     } 
/*  523 */     if (b > c) {
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/*  530 */       long n = from + len;
/*  531 */       long s = Math.min(a - from, b - a);
/*  532 */       vecSwap(swapper, from, b - s, s);
/*  533 */       s = Math.min(d - c, n - d - 1L);
/*  534 */       vecSwap(swapper, b, n - s, s);
/*      */       
/*  536 */       if ((s = b - a) > 1L) quickSort(from, from + s, comp, swapper); 
/*  537 */       if ((s = d - c) > 1L) quickSort(n - s, n, comp, swapper);
/*      */       
/*      */       return;
/*      */     } 
/*      */     if (b == m) {
/*      */       m = d;
/*      */     } else if (c == m) {
/*      */       m = c;
/*      */     } 
/*      */     swapper.swap(b++, c--);
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   private static long upperBound(long from, long mid, long secondCut, LongComparator comp) {
/*  552 */     long len = mid - from;
/*  553 */     while (len > 0L) {
/*  554 */       long half = len / 2L;
/*  555 */       long middle = from + half;
/*  556 */       if (comp.compare(secondCut, middle) < 0) {
/*  557 */         len = half; continue;
/*      */       } 
/*  559 */       from = middle + 1L;
/*  560 */       len -= half + 1L;
/*      */     } 
/*      */     
/*  563 */     return from;
/*      */   }
/*      */ 
/*      */   
/*      */   private static void vecSwap(BigSwapper swapper, long from, long l, long s) {
/*  568 */     for (int i = 0; i < s; ) { swapper.swap(from, l); i++; from++; l++; }
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
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static byte get(byte[][] array, long index) {
/*  619 */     return array[segment(index)][displacement(index)];
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static void set(byte[][] array, long index, byte value) {
/*  630 */     array[segment(index)][displacement(index)] = value;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static void swap(byte[][] array, long first, long second) {
/*  641 */     byte t = array[segment(first)][displacement(first)];
/*  642 */     array[segment(first)][displacement(first)] = array[segment(second)][displacement(second)];
/*  643 */     array[segment(second)][displacement(second)] = t;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static byte[][] reverse(byte[][] a) {
/*  653 */     long length = length(a);
/*  654 */     for (long i = length / 2L; i-- != 0L; swap(a, i, length - i - 1L));
/*  655 */     return a;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static void add(byte[][] array, long index, byte incr) {
/*  666 */     array[segment(index)][displacement(index)] = (byte)(array[segment(index)][displacement(index)] + incr);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static void mul(byte[][] array, long index, byte factor) {
/*  677 */     array[segment(index)][displacement(index)] = (byte)(array[segment(index)][displacement(index)] * factor);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static void incr(byte[][] array, long index) {
/*  687 */     array[segment(index)][displacement(index)] = (byte)(array[segment(index)][displacement(index)] + 1);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static void decr(byte[][] array, long index) {
/*  697 */     array[segment(index)][displacement(index)] = (byte)(array[segment(index)][displacement(index)] - 1);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static void assertBigArray(byte[][] array) {
/*  709 */     int l = array.length;
/*  710 */     if (l == 0)
/*  711 */       return;  for (int i = 0; i < l - 1; ) { if ((array[i]).length != 134217728) throw new IllegalStateException("All segments except for the last one must be of length 2^" + Integer.toString(27));  i++; }
/*  712 */      if ((array[l - 1]).length > 134217728) throw new IllegalStateException("The last segment must be of length at most 2^" + Integer.toString(27)); 
/*  713 */     if ((array[l - 1]).length == 0 && l == 1) throw new IllegalStateException("The last segment must be of nonzero length");
/*      */   
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static long length(byte[][] array) {
/*  723 */     int length = array.length;
/*  724 */     return (length == 0) ? 0L : (start(length - 1) + (array[length - 1]).length);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static void copy(byte[][] srcArray, long srcPos, byte[][] destArray, long destPos, long length) {
/*  739 */     if (destPos <= srcPos) {
/*  740 */       int srcSegment = segment(srcPos);
/*  741 */       int destSegment = segment(destPos);
/*  742 */       int srcDispl = displacement(srcPos);
/*  743 */       int destDispl = displacement(destPos);
/*  744 */       while (length > 0L) {
/*  745 */         int l = (int)Math.min(length, Math.min((srcArray[srcSegment]).length - srcDispl, (destArray[destSegment]).length - destDispl));
/*  746 */         if (l == 0) throw new ArrayIndexOutOfBoundsException(); 
/*  747 */         System.arraycopy(srcArray[srcSegment], srcDispl, destArray[destSegment], destDispl, l);
/*  748 */         if ((srcDispl += l) == 134217728) {
/*  749 */           srcDispl = 0;
/*  750 */           srcSegment++;
/*      */         } 
/*  752 */         if ((destDispl += l) == 134217728) {
/*  753 */           destDispl = 0;
/*  754 */           destSegment++;
/*      */         } 
/*  756 */         length -= l;
/*      */       } 
/*      */     } else {
/*  759 */       int srcSegment = segment(srcPos + length);
/*  760 */       int destSegment = segment(destPos + length);
/*  761 */       int srcDispl = displacement(srcPos + length);
/*  762 */       int destDispl = displacement(destPos + length);
/*  763 */       while (length > 0L) {
/*  764 */         if (srcDispl == 0) {
/*  765 */           srcDispl = 134217728;
/*  766 */           srcSegment--;
/*      */         } 
/*  768 */         if (destDispl == 0) {
/*  769 */           destDispl = 134217728;
/*  770 */           destSegment--;
/*      */         } 
/*  772 */         int l = (int)Math.min(length, Math.min(srcDispl, destDispl));
/*  773 */         if (l == 0) throw new ArrayIndexOutOfBoundsException(); 
/*  774 */         System.arraycopy(srcArray[srcSegment], srcDispl - l, destArray[destSegment], destDispl - l, l);
/*  775 */         srcDispl -= l;
/*  776 */         destDispl -= l;
/*  777 */         length -= l;
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
/*      */   public static void copyFromBig(byte[][] srcArray, long srcPos, byte[] destArray, int destPos, int length) {
/*  793 */     int srcSegment = segment(srcPos);
/*  794 */     int srcDispl = displacement(srcPos);
/*  795 */     while (length > 0) {
/*  796 */       int l = Math.min((srcArray[srcSegment]).length - srcDispl, length);
/*  797 */       if (l == 0) throw new ArrayIndexOutOfBoundsException(); 
/*  798 */       System.arraycopy(srcArray[srcSegment], srcDispl, destArray, destPos, l);
/*  799 */       if ((srcDispl += l) == 134217728) {
/*  800 */         srcDispl = 0;
/*  801 */         srcSegment++;
/*      */       } 
/*  803 */       destPos += l;
/*  804 */       length -= l;
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
/*      */   public static void copyToBig(byte[] srcArray, int srcPos, byte[][] destArray, long destPos, long length) {
/*  819 */     int destSegment = segment(destPos);
/*  820 */     int destDispl = displacement(destPos);
/*  821 */     while (length > 0L) {
/*  822 */       int l = (int)Math.min(((destArray[destSegment]).length - destDispl), length);
/*  823 */       if (l == 0) throw new ArrayIndexOutOfBoundsException(); 
/*  824 */       System.arraycopy(srcArray, srcPos, destArray[destSegment], destDispl, l);
/*  825 */       if ((destDispl += l) == 134217728) {
/*  826 */         destDispl = 0;
/*  827 */         destSegment++;
/*      */       } 
/*  829 */       srcPos += l;
/*  830 */       length -= l;
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
/*      */   public static byte[][] wrap(byte[] array) {
/*  844 */     if (array.length == 0) return ByteBigArrays.EMPTY_BIG_ARRAY; 
/*  845 */     if (array.length <= 134217728) return new byte[][] { array }; 
/*  846 */     byte[][] bigArray = ByteBigArrays.newBigArray(array.length);
/*  847 */     for (int i = 0; i < bigArray.length; ) { System.arraycopy(array, (int)start(i), bigArray[i], 0, (bigArray[i]).length); i++; }
/*  848 */      return bigArray;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static byte[][] ensureCapacity(byte[][] array, long length) {
/*  869 */     return ensureCapacity(array, length, length(array));
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static byte[][] forceCapacity(byte[][] array, long length, long preserve) {
/*  888 */     ensureLength(length);
/*  889 */     int valid = array.length - ((array.length == 0 || (array.length > 0 && (array[array.length - 1]).length == 134217728)) ? 0 : 1);
/*  890 */     int baseLength = (int)(length + 134217727L >>> 27L);
/*  891 */     byte[][] base = Arrays.<byte[]>copyOf(array, baseLength);
/*  892 */     int residual = (int)(length & 0x7FFFFFFL);
/*  893 */     if (residual != 0)
/*  894 */     { for (int i = valid; i < baseLength - 1; ) { base[i] = new byte[134217728]; i++; }
/*  895 */        base[baseLength - 1] = new byte[residual]; }
/*  896 */     else { for (int i = valid; i < baseLength; ) { base[i] = new byte[134217728]; i++; }  }
/*  897 */      if (preserve - valid * 134217728L > 0L) copy(array, valid * 134217728L, base, valid * 134217728L, preserve - valid * 134217728L); 
/*  898 */     return base;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static byte[][] ensureCapacity(byte[][] array, long length, long preserve) {
/*  918 */     return (length > length(array)) ? forceCapacity(array, length, preserve) : array;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static byte[][] grow(byte[][] array, long length) {
/*  940 */     long oldLength = length(array);
/*  941 */     return (length > oldLength) ? grow(array, length, oldLength) : array;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static byte[][] grow(byte[][] array, long length, long preserve) {
/*  966 */     long oldLength = length(array);
/*  967 */     return (length > oldLength) ? ensureCapacity(array, Math.max(oldLength + (oldLength >> 1L), length), preserve) : array;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static byte[][] trim(byte[][] array, long length) {
/*  985 */     ensureLength(length);
/*  986 */     long oldLength = length(array);
/*  987 */     if (length >= oldLength) return array; 
/*  988 */     int baseLength = (int)(length + 134217727L >>> 27L);
/*  989 */     byte[][] base = Arrays.<byte[]>copyOf(array, baseLength);
/*  990 */     int residual = (int)(length & 0x7FFFFFFL);
/*  991 */     if (residual != 0) base[baseLength - 1] = ByteArrays.trim(base[baseLength - 1], residual); 
/*  992 */     return base;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static byte[][] setLength(byte[][] array, long length) {
/* 1012 */     long oldLength = length(array);
/* 1013 */     if (length == oldLength) return array; 
/* 1014 */     if (length < oldLength) return trim(array, length); 
/* 1015 */     return ensureCapacity(array, length);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static byte[][] copy(byte[][] array, long offset, long length) {
/* 1028 */     ensureOffsetLength(array, offset, length);
/* 1029 */     byte[][] a = ByteBigArrays.newBigArray(length);
/* 1030 */     copy(array, offset, a, 0L, length);
/* 1031 */     return a;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static byte[][] copy(byte[][] array) {
/* 1041 */     byte[][] base = (byte[][])array.clone();
/* 1042 */     for (int i = base.length; i-- != 0; base[i] = (byte[])array[i].clone());
/* 1043 */     return base;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static void fill(byte[][] array, byte value) {
/* 1057 */     for (int i = array.length; i-- != 0; Arrays.fill(array[i], value));
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static void fill(byte[][] array, long from, long to, byte value) {
/* 1073 */     long length = length(array);
/* 1074 */     ensureFromTo(length, from, to);
/* 1075 */     if (length == 0L)
/* 1076 */       return;  int fromSegment = segment(from);
/* 1077 */     int toSegment = segment(to);
/* 1078 */     int fromDispl = displacement(from);
/* 1079 */     int toDispl = displacement(to);
/* 1080 */     if (fromSegment == toSegment) {
/* 1081 */       Arrays.fill(array[fromSegment], fromDispl, toDispl, value);
/*      */       return;
/*      */     } 
/* 1084 */     if (toDispl != 0) Arrays.fill(array[toSegment], 0, toDispl, value); 
/* 1085 */     for (; --toSegment > fromSegment; Arrays.fill(array[toSegment], value));
/* 1086 */     Arrays.fill(array[fromSegment], fromDispl, 134217728, value);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static boolean equals(byte[][] a1, byte[][] a2) {
/* 1101 */     if (length(a1) != length(a2)) return false; 
/* 1102 */     int i = a1.length;
/*      */     
/* 1104 */     while (i-- != 0) {
/* 1105 */       byte[] t = a1[i];
/* 1106 */       byte[] u = a2[i];
/* 1107 */       int j = t.length;
/* 1108 */       while (j-- != 0) { if (t[j] != u[j]) return false;  }
/*      */     
/* 1110 */     }  return true;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static String toString(byte[][] a) {
/* 1120 */     if (a == null) return "null"; 
/* 1121 */     long last = length(a) - 1L;
/* 1122 */     if (last == -1L) return "[]"; 
/* 1123 */     StringBuilder b = new StringBuilder();
/* 1124 */     b.append('['); long i;
/* 1125 */     for (i = 0L;; i++) {
/* 1126 */       b.append(String.valueOf(get(a, i)));
/* 1127 */       if (i == last) return b.append(']').toString(); 
/* 1128 */       b.append(", ");
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
/*      */   public static void ensureFromTo(byte[][] a, long from, long to) {
/* 1147 */     ensureFromTo(length(a), from, to);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static void ensureOffsetLength(byte[][] a, long offset, long length) {
/* 1164 */     ensureOffsetLength(length(a), offset, length);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static void ensureSameLength(byte[][] a, byte[][] b) {
/* 1175 */     if (length(a) != length(b)) throw new IllegalArgumentException("Array size mismatch: " + length(a) + " != " + length(b));
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
/*      */   public static byte[][] shuffle(byte[][] a, long from, long to, Random random) {
/* 1188 */     for (long i = to - from; i-- != 0L; ) {
/* 1189 */       long p = (random.nextLong() & Long.MAX_VALUE) % (i + 1L);
/* 1190 */       byte t = get(a, from + i);
/* 1191 */       set(a, from + i, get(a, from + p));
/* 1192 */       set(a, from + p, t);
/*      */     } 
/* 1194 */     return a;
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
/* 1205 */     for (long i = length(a); i-- != 0L; ) {
/* 1206 */       long p = (random.nextLong() & Long.MAX_VALUE) % (i + 1L);
/* 1207 */       byte t = get(a, i);
/* 1208 */       set(a, i, get(a, p));
/* 1209 */       set(a, p, t);
/*      */     } 
/* 1211 */     return a;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static int get(int[][] array, long index) {
/* 1262 */     return array[segment(index)][displacement(index)];
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static void set(int[][] array, long index, int value) {
/* 1273 */     array[segment(index)][displacement(index)] = value;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static long length(AtomicIntegerArray[] array) {
/* 1283 */     int length = array.length;
/* 1284 */     return (length == 0) ? 0L : (start(length - 1) + array[length - 1].length());
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static int get(AtomicIntegerArray[] array, long index) {
/* 1295 */     return array[segment(index)].get(displacement(index));
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static void set(AtomicIntegerArray[] array, long index, int value) {
/* 1306 */     array[segment(index)].set(displacement(index), value);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static int getAndSet(AtomicIntegerArray[] array, long index, int value) {
/* 1319 */     return array[segment(index)].getAndSet(displacement(index), value);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static int getAndAdd(AtomicIntegerArray[] array, long index, int value) {
/* 1331 */     return array[segment(index)].getAndAdd(displacement(index), value);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static int addAndGet(AtomicIntegerArray[] array, long index, int value) {
/* 1343 */     return array[segment(index)].addAndGet(displacement(index), value);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static int getAndIncrement(AtomicIntegerArray[] array, long index) {
/* 1354 */     return array[segment(index)].getAndDecrement(displacement(index));
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static int incrementAndGet(AtomicIntegerArray[] array, long index) {
/* 1365 */     return array[segment(index)].incrementAndGet(displacement(index));
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static int getAndDecrement(AtomicIntegerArray[] array, long index) {
/* 1376 */     return array[segment(index)].getAndDecrement(displacement(index));
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static int decrementAndGet(AtomicIntegerArray[] array, long index) {
/* 1387 */     return array[segment(index)].decrementAndGet(displacement(index));
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static boolean compareAndSet(AtomicIntegerArray[] array, long index, int expected, int value) {
/* 1402 */     return array[segment(index)].compareAndSet(displacement(index), expected, value);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static void swap(int[][] array, long first, long second) {
/* 1413 */     int t = array[segment(first)][displacement(first)];
/* 1414 */     array[segment(first)][displacement(first)] = array[segment(second)][displacement(second)];
/* 1415 */     array[segment(second)][displacement(second)] = t;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static int[][] reverse(int[][] a) {
/* 1425 */     long length = length(a);
/* 1426 */     for (long i = length / 2L; i-- != 0L; swap(a, i, length - i - 1L));
/* 1427 */     return a;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static void add(int[][] array, long index, int incr) {
/* 1438 */     array[segment(index)][displacement(index)] = array[segment(index)][displacement(index)] + incr;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static void mul(int[][] array, long index, int factor) {
/* 1449 */     array[segment(index)][displacement(index)] = array[segment(index)][displacement(index)] * factor;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static void incr(int[][] array, long index) {
/* 1459 */     array[segment(index)][displacement(index)] = array[segment(index)][displacement(index)] + 1;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static void decr(int[][] array, long index) {
/* 1469 */     array[segment(index)][displacement(index)] = array[segment(index)][displacement(index)] - 1;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static void assertBigArray(int[][] array) {
/* 1481 */     int l = array.length;
/* 1482 */     if (l == 0)
/* 1483 */       return;  for (int i = 0; i < l - 1; ) { if ((array[i]).length != 134217728) throw new IllegalStateException("All segments except for the last one must be of length 2^" + Integer.toString(27));  i++; }
/* 1484 */      if ((array[l - 1]).length > 134217728) throw new IllegalStateException("The last segment must be of length at most 2^" + Integer.toString(27)); 
/* 1485 */     if ((array[l - 1]).length == 0 && l == 1) throw new IllegalStateException("The last segment must be of nonzero length");
/*      */   
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static long length(int[][] array) {
/* 1495 */     int length = array.length;
/* 1496 */     return (length == 0) ? 0L : (start(length - 1) + (array[length - 1]).length);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static void copy(int[][] srcArray, long srcPos, int[][] destArray, long destPos, long length) {
/* 1511 */     if (destPos <= srcPos) {
/* 1512 */       int srcSegment = segment(srcPos);
/* 1513 */       int destSegment = segment(destPos);
/* 1514 */       int srcDispl = displacement(srcPos);
/* 1515 */       int destDispl = displacement(destPos);
/* 1516 */       while (length > 0L) {
/* 1517 */         int l = (int)Math.min(length, Math.min((srcArray[srcSegment]).length - srcDispl, (destArray[destSegment]).length - destDispl));
/* 1518 */         if (l == 0) throw new ArrayIndexOutOfBoundsException(); 
/* 1519 */         System.arraycopy(srcArray[srcSegment], srcDispl, destArray[destSegment], destDispl, l);
/* 1520 */         if ((srcDispl += l) == 134217728) {
/* 1521 */           srcDispl = 0;
/* 1522 */           srcSegment++;
/*      */         } 
/* 1524 */         if ((destDispl += l) == 134217728) {
/* 1525 */           destDispl = 0;
/* 1526 */           destSegment++;
/*      */         } 
/* 1528 */         length -= l;
/*      */       } 
/*      */     } else {
/* 1531 */       int srcSegment = segment(srcPos + length);
/* 1532 */       int destSegment = segment(destPos + length);
/* 1533 */       int srcDispl = displacement(srcPos + length);
/* 1534 */       int destDispl = displacement(destPos + length);
/* 1535 */       while (length > 0L) {
/* 1536 */         if (srcDispl == 0) {
/* 1537 */           srcDispl = 134217728;
/* 1538 */           srcSegment--;
/*      */         } 
/* 1540 */         if (destDispl == 0) {
/* 1541 */           destDispl = 134217728;
/* 1542 */           destSegment--;
/*      */         } 
/* 1544 */         int l = (int)Math.min(length, Math.min(srcDispl, destDispl));
/* 1545 */         if (l == 0) throw new ArrayIndexOutOfBoundsException(); 
/* 1546 */         System.arraycopy(srcArray[srcSegment], srcDispl - l, destArray[destSegment], destDispl - l, l);
/* 1547 */         srcDispl -= l;
/* 1548 */         destDispl -= l;
/* 1549 */         length -= l;
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
/*      */   public static void copyFromBig(int[][] srcArray, long srcPos, int[] destArray, int destPos, int length) {
/* 1565 */     int srcSegment = segment(srcPos);
/* 1566 */     int srcDispl = displacement(srcPos);
/* 1567 */     while (length > 0) {
/* 1568 */       int l = Math.min((srcArray[srcSegment]).length - srcDispl, length);
/* 1569 */       if (l == 0) throw new ArrayIndexOutOfBoundsException(); 
/* 1570 */       System.arraycopy(srcArray[srcSegment], srcDispl, destArray, destPos, l);
/* 1571 */       if ((srcDispl += l) == 134217728) {
/* 1572 */         srcDispl = 0;
/* 1573 */         srcSegment++;
/*      */       } 
/* 1575 */       destPos += l;
/* 1576 */       length -= l;
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
/*      */   public static void copyToBig(int[] srcArray, int srcPos, int[][] destArray, long destPos, long length) {
/* 1591 */     int destSegment = segment(destPos);
/* 1592 */     int destDispl = displacement(destPos);
/* 1593 */     while (length > 0L) {
/* 1594 */       int l = (int)Math.min(((destArray[destSegment]).length - destDispl), length);
/* 1595 */       if (l == 0) throw new ArrayIndexOutOfBoundsException(); 
/* 1596 */       System.arraycopy(srcArray, srcPos, destArray[destSegment], destDispl, l);
/* 1597 */       if ((destDispl += l) == 134217728) {
/* 1598 */         destDispl = 0;
/* 1599 */         destSegment++;
/*      */       } 
/* 1601 */       srcPos += l;
/* 1602 */       length -= l;
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
/*      */   public static int[][] wrap(int[] array) {
/* 1616 */     if (array.length == 0) return IntBigArrays.EMPTY_BIG_ARRAY; 
/* 1617 */     if (array.length <= 134217728) return new int[][] { array }; 
/* 1618 */     int[][] bigArray = IntBigArrays.newBigArray(array.length);
/* 1619 */     for (int i = 0; i < bigArray.length; ) { System.arraycopy(array, (int)start(i), bigArray[i], 0, (bigArray[i]).length); i++; }
/* 1620 */      return bigArray;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static int[][] ensureCapacity(int[][] array, long length) {
/* 1641 */     return ensureCapacity(array, length, length(array));
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static int[][] forceCapacity(int[][] array, long length, long preserve) {
/* 1660 */     ensureLength(length);
/* 1661 */     int valid = array.length - ((array.length == 0 || (array.length > 0 && (array[array.length - 1]).length == 134217728)) ? 0 : 1);
/* 1662 */     int baseLength = (int)(length + 134217727L >>> 27L);
/* 1663 */     int[][] base = Arrays.<int[]>copyOf(array, baseLength);
/* 1664 */     int residual = (int)(length & 0x7FFFFFFL);
/* 1665 */     if (residual != 0)
/* 1666 */     { for (int i = valid; i < baseLength - 1; ) { base[i] = new int[134217728]; i++; }
/* 1667 */        base[baseLength - 1] = new int[residual]; }
/* 1668 */     else { for (int i = valid; i < baseLength; ) { base[i] = new int[134217728]; i++; }  }
/* 1669 */      if (preserve - valid * 134217728L > 0L) copy(array, valid * 134217728L, base, valid * 134217728L, preserve - valid * 134217728L); 
/* 1670 */     return base;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static int[][] ensureCapacity(int[][] array, long length, long preserve) {
/* 1690 */     return (length > length(array)) ? forceCapacity(array, length, preserve) : array;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static int[][] grow(int[][] array, long length) {
/* 1712 */     long oldLength = length(array);
/* 1713 */     return (length > oldLength) ? grow(array, length, oldLength) : array;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static int[][] grow(int[][] array, long length, long preserve) {
/* 1738 */     long oldLength = length(array);
/* 1739 */     return (length > oldLength) ? ensureCapacity(array, Math.max(oldLength + (oldLength >> 1L), length), preserve) : array;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static int[][] trim(int[][] array, long length) {
/* 1757 */     ensureLength(length);
/* 1758 */     long oldLength = length(array);
/* 1759 */     if (length >= oldLength) return array; 
/* 1760 */     int baseLength = (int)(length + 134217727L >>> 27L);
/* 1761 */     int[][] base = Arrays.<int[]>copyOf(array, baseLength);
/* 1762 */     int residual = (int)(length & 0x7FFFFFFL);
/* 1763 */     if (residual != 0) base[baseLength - 1] = IntArrays.trim(base[baseLength - 1], residual); 
/* 1764 */     return base;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static int[][] setLength(int[][] array, long length) {
/* 1784 */     long oldLength = length(array);
/* 1785 */     if (length == oldLength) return array; 
/* 1786 */     if (length < oldLength) return trim(array, length); 
/* 1787 */     return ensureCapacity(array, length);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static int[][] copy(int[][] array, long offset, long length) {
/* 1800 */     ensureOffsetLength(array, offset, length);
/* 1801 */     int[][] a = IntBigArrays.newBigArray(length);
/* 1802 */     copy(array, offset, a, 0L, length);
/* 1803 */     return a;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static int[][] copy(int[][] array) {
/* 1813 */     int[][] base = (int[][])array.clone();
/* 1814 */     for (int i = base.length; i-- != 0; base[i] = (int[])array[i].clone());
/* 1815 */     return base;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static void fill(int[][] array, int value) {
/* 1829 */     for (int i = array.length; i-- != 0; Arrays.fill(array[i], value));
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static void fill(int[][] array, long from, long to, int value) {
/* 1845 */     long length = length(array);
/* 1846 */     ensureFromTo(length, from, to);
/* 1847 */     if (length == 0L)
/* 1848 */       return;  int fromSegment = segment(from);
/* 1849 */     int toSegment = segment(to);
/* 1850 */     int fromDispl = displacement(from);
/* 1851 */     int toDispl = displacement(to);
/* 1852 */     if (fromSegment == toSegment) {
/* 1853 */       Arrays.fill(array[fromSegment], fromDispl, toDispl, value);
/*      */       return;
/*      */     } 
/* 1856 */     if (toDispl != 0) Arrays.fill(array[toSegment], 0, toDispl, value); 
/* 1857 */     for (; --toSegment > fromSegment; Arrays.fill(array[toSegment], value));
/* 1858 */     Arrays.fill(array[fromSegment], fromDispl, 134217728, value);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static boolean equals(int[][] a1, int[][] a2) {
/* 1873 */     if (length(a1) != length(a2)) return false; 
/* 1874 */     int i = a1.length;
/*      */     
/* 1876 */     while (i-- != 0) {
/* 1877 */       int[] t = a1[i];
/* 1878 */       int[] u = a2[i];
/* 1879 */       int j = t.length;
/* 1880 */       while (j-- != 0) { if (t[j] != u[j]) return false;  }
/*      */     
/* 1882 */     }  return true;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static String toString(int[][] a) {
/* 1892 */     if (a == null) return "null"; 
/* 1893 */     long last = length(a) - 1L;
/* 1894 */     if (last == -1L) return "[]"; 
/* 1895 */     StringBuilder b = new StringBuilder();
/* 1896 */     b.append('['); long i;
/* 1897 */     for (i = 0L;; i++) {
/* 1898 */       b.append(String.valueOf(get(a, i)));
/* 1899 */       if (i == last) return b.append(']').toString(); 
/* 1900 */       b.append(", ");
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
/*      */   public static void ensureFromTo(int[][] a, long from, long to) {
/* 1919 */     ensureFromTo(length(a), from, to);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static void ensureOffsetLength(int[][] a, long offset, long length) {
/* 1936 */     ensureOffsetLength(length(a), offset, length);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static void ensureSameLength(int[][] a, int[][] b) {
/* 1947 */     if (length(a) != length(b)) throw new IllegalArgumentException("Array size mismatch: " + length(a) + " != " + length(b));
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
/*      */   public static int[][] shuffle(int[][] a, long from, long to, Random random) {
/* 1960 */     for (long i = to - from; i-- != 0L; ) {
/* 1961 */       long p = (random.nextLong() & Long.MAX_VALUE) % (i + 1L);
/* 1962 */       int t = get(a, from + i);
/* 1963 */       set(a, from + i, get(a, from + p));
/* 1964 */       set(a, from + p, t);
/*      */     } 
/* 1966 */     return a;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static int[][] shuffle(int[][] a, Random random) {
/* 1977 */     for (long i = length(a); i-- != 0L; ) {
/* 1978 */       long p = (random.nextLong() & Long.MAX_VALUE) % (i + 1L);
/* 1979 */       int t = get(a, i);
/* 1980 */       set(a, i, get(a, p));
/* 1981 */       set(a, p, t);
/*      */     } 
/* 1983 */     return a;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static long get(long[][] array, long index) {
/* 2034 */     return array[segment(index)][displacement(index)];
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static void set(long[][] array, long index, long value) {
/* 2045 */     array[segment(index)][displacement(index)] = value;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static long length(AtomicLongArray[] array) {
/* 2055 */     int length = array.length;
/* 2056 */     return (length == 0) ? 0L : (start(length - 1) + array[length - 1].length());
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static long get(AtomicLongArray[] array, long index) {
/* 2067 */     return array[segment(index)].get(displacement(index));
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static void set(AtomicLongArray[] array, long index, long value) {
/* 2078 */     array[segment(index)].set(displacement(index), value);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static long getAndSet(AtomicLongArray[] array, long index, long value) {
/* 2091 */     return array[segment(index)].getAndSet(displacement(index), value);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static long getAndAdd(AtomicLongArray[] array, long index, long value) {
/* 2103 */     return array[segment(index)].getAndAdd(displacement(index), value);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static long addAndGet(AtomicLongArray[] array, long index, long value) {
/* 2115 */     return array[segment(index)].addAndGet(displacement(index), value);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static long getAndIncrement(AtomicLongArray[] array, long index) {
/* 2126 */     return array[segment(index)].getAndDecrement(displacement(index));
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static long incrementAndGet(AtomicLongArray[] array, long index) {
/* 2137 */     return array[segment(index)].incrementAndGet(displacement(index));
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static long getAndDecrement(AtomicLongArray[] array, long index) {
/* 2148 */     return array[segment(index)].getAndDecrement(displacement(index));
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static long decrementAndGet(AtomicLongArray[] array, long index) {
/* 2159 */     return array[segment(index)].decrementAndGet(displacement(index));
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static boolean compareAndSet(AtomicLongArray[] array, long index, long expected, long value) {
/* 2174 */     return array[segment(index)].compareAndSet(displacement(index), expected, value);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static void swap(long[][] array, long first, long second) {
/* 2185 */     long t = array[segment(first)][displacement(first)];
/* 2186 */     array[segment(first)][displacement(first)] = array[segment(second)][displacement(second)];
/* 2187 */     array[segment(second)][displacement(second)] = t;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static long[][] reverse(long[][] a) {
/* 2197 */     long length = length(a);
/* 2198 */     for (long i = length / 2L; i-- != 0L; swap(a, i, length - i - 1L));
/* 2199 */     return a;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static void add(long[][] array, long index, long incr) {
/* 2210 */     array[segment(index)][displacement(index)] = array[segment(index)][displacement(index)] + incr;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static void mul(long[][] array, long index, long factor) {
/* 2221 */     array[segment(index)][displacement(index)] = array[segment(index)][displacement(index)] * factor;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static void incr(long[][] array, long index) {
/* 2231 */     array[segment(index)][displacement(index)] = array[segment(index)][displacement(index)] + 1L;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static void decr(long[][] array, long index) {
/* 2241 */     array[segment(index)][displacement(index)] = array[segment(index)][displacement(index)] - 1L;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static void assertBigArray(long[][] array) {
/* 2253 */     int l = array.length;
/* 2254 */     if (l == 0)
/* 2255 */       return;  for (int i = 0; i < l - 1; ) { if ((array[i]).length != 134217728) throw new IllegalStateException("All segments except for the last one must be of length 2^" + Integer.toString(27));  i++; }
/* 2256 */      if ((array[l - 1]).length > 134217728) throw new IllegalStateException("The last segment must be of length at most 2^" + Integer.toString(27)); 
/* 2257 */     if ((array[l - 1]).length == 0 && l == 1) throw new IllegalStateException("The last segment must be of nonzero length");
/*      */   
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static long length(long[][] array) {
/* 2267 */     int length = array.length;
/* 2268 */     return (length == 0) ? 0L : (start(length - 1) + (array[length - 1]).length);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static void copy(long[][] srcArray, long srcPos, long[][] destArray, long destPos, long length) {
/* 2283 */     if (destPos <= srcPos) {
/* 2284 */       int srcSegment = segment(srcPos);
/* 2285 */       int destSegment = segment(destPos);
/* 2286 */       int srcDispl = displacement(srcPos);
/* 2287 */       int destDispl = displacement(destPos);
/* 2288 */       while (length > 0L) {
/* 2289 */         int l = (int)Math.min(length, Math.min((srcArray[srcSegment]).length - srcDispl, (destArray[destSegment]).length - destDispl));
/* 2290 */         if (l == 0) throw new ArrayIndexOutOfBoundsException(); 
/* 2291 */         System.arraycopy(srcArray[srcSegment], srcDispl, destArray[destSegment], destDispl, l);
/* 2292 */         if ((srcDispl += l) == 134217728) {
/* 2293 */           srcDispl = 0;
/* 2294 */           srcSegment++;
/*      */         } 
/* 2296 */         if ((destDispl += l) == 134217728) {
/* 2297 */           destDispl = 0;
/* 2298 */           destSegment++;
/*      */         } 
/* 2300 */         length -= l;
/*      */       } 
/*      */     } else {
/* 2303 */       int srcSegment = segment(srcPos + length);
/* 2304 */       int destSegment = segment(destPos + length);
/* 2305 */       int srcDispl = displacement(srcPos + length);
/* 2306 */       int destDispl = displacement(destPos + length);
/* 2307 */       while (length > 0L) {
/* 2308 */         if (srcDispl == 0) {
/* 2309 */           srcDispl = 134217728;
/* 2310 */           srcSegment--;
/*      */         } 
/* 2312 */         if (destDispl == 0) {
/* 2313 */           destDispl = 134217728;
/* 2314 */           destSegment--;
/*      */         } 
/* 2316 */         int l = (int)Math.min(length, Math.min(srcDispl, destDispl));
/* 2317 */         if (l == 0) throw new ArrayIndexOutOfBoundsException(); 
/* 2318 */         System.arraycopy(srcArray[srcSegment], srcDispl - l, destArray[destSegment], destDispl - l, l);
/* 2319 */         srcDispl -= l;
/* 2320 */         destDispl -= l;
/* 2321 */         length -= l;
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
/*      */   public static void copyFromBig(long[][] srcArray, long srcPos, long[] destArray, int destPos, int length) {
/* 2337 */     int srcSegment = segment(srcPos);
/* 2338 */     int srcDispl = displacement(srcPos);
/* 2339 */     while (length > 0) {
/* 2340 */       int l = Math.min((srcArray[srcSegment]).length - srcDispl, length);
/* 2341 */       if (l == 0) throw new ArrayIndexOutOfBoundsException(); 
/* 2342 */       System.arraycopy(srcArray[srcSegment], srcDispl, destArray, destPos, l);
/* 2343 */       if ((srcDispl += l) == 134217728) {
/* 2344 */         srcDispl = 0;
/* 2345 */         srcSegment++;
/*      */       } 
/* 2347 */       destPos += l;
/* 2348 */       length -= l;
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
/*      */   public static void copyToBig(long[] srcArray, int srcPos, long[][] destArray, long destPos, long length) {
/* 2363 */     int destSegment = segment(destPos);
/* 2364 */     int destDispl = displacement(destPos);
/* 2365 */     while (length > 0L) {
/* 2366 */       int l = (int)Math.min(((destArray[destSegment]).length - destDispl), length);
/* 2367 */       if (l == 0) throw new ArrayIndexOutOfBoundsException(); 
/* 2368 */       System.arraycopy(srcArray, srcPos, destArray[destSegment], destDispl, l);
/* 2369 */       if ((destDispl += l) == 134217728) {
/* 2370 */         destDispl = 0;
/* 2371 */         destSegment++;
/*      */       } 
/* 2373 */       srcPos += l;
/* 2374 */       length -= l;
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
/*      */   public static long[][] wrap(long[] array) {
/* 2388 */     if (array.length == 0) return LongBigArrays.EMPTY_BIG_ARRAY; 
/* 2389 */     if (array.length <= 134217728) return new long[][] { array }; 
/* 2390 */     long[][] bigArray = LongBigArrays.newBigArray(array.length);
/* 2391 */     for (int i = 0; i < bigArray.length; ) { System.arraycopy(array, (int)start(i), bigArray[i], 0, (bigArray[i]).length); i++; }
/* 2392 */      return bigArray;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static long[][] ensureCapacity(long[][] array, long length) {
/* 2413 */     return ensureCapacity(array, length, length(array));
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static long[][] forceCapacity(long[][] array, long length, long preserve) {
/* 2432 */     ensureLength(length);
/* 2433 */     int valid = array.length - ((array.length == 0 || (array.length > 0 && (array[array.length - 1]).length == 134217728)) ? 0 : 1);
/* 2434 */     int baseLength = (int)(length + 134217727L >>> 27L);
/* 2435 */     long[][] base = Arrays.<long[]>copyOf(array, baseLength);
/* 2436 */     int residual = (int)(length & 0x7FFFFFFL);
/* 2437 */     if (residual != 0)
/* 2438 */     { for (int i = valid; i < baseLength - 1; ) { base[i] = new long[134217728]; i++; }
/* 2439 */        base[baseLength - 1] = new long[residual]; }
/* 2440 */     else { for (int i = valid; i < baseLength; ) { base[i] = new long[134217728]; i++; }  }
/* 2441 */      if (preserve - valid * 134217728L > 0L) copy(array, valid * 134217728L, base, valid * 134217728L, preserve - valid * 134217728L); 
/* 2442 */     return base;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static long[][] ensureCapacity(long[][] array, long length, long preserve) {
/* 2462 */     return (length > length(array)) ? forceCapacity(array, length, preserve) : array;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static long[][] grow(long[][] array, long length) {
/* 2484 */     long oldLength = length(array);
/* 2485 */     return (length > oldLength) ? grow(array, length, oldLength) : array;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static long[][] grow(long[][] array, long length, long preserve) {
/* 2510 */     long oldLength = length(array);
/* 2511 */     return (length > oldLength) ? ensureCapacity(array, Math.max(oldLength + (oldLength >> 1L), length), preserve) : array;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static long[][] trim(long[][] array, long length) {
/* 2529 */     ensureLength(length);
/* 2530 */     long oldLength = length(array);
/* 2531 */     if (length >= oldLength) return array; 
/* 2532 */     int baseLength = (int)(length + 134217727L >>> 27L);
/* 2533 */     long[][] base = Arrays.<long[]>copyOf(array, baseLength);
/* 2534 */     int residual = (int)(length & 0x7FFFFFFL);
/* 2535 */     if (residual != 0) base[baseLength - 1] = LongArrays.trim(base[baseLength - 1], residual); 
/* 2536 */     return base;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static long[][] setLength(long[][] array, long length) {
/* 2556 */     long oldLength = length(array);
/* 2557 */     if (length == oldLength) return array; 
/* 2558 */     if (length < oldLength) return trim(array, length); 
/* 2559 */     return ensureCapacity(array, length);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static long[][] copy(long[][] array, long offset, long length) {
/* 2572 */     ensureOffsetLength(array, offset, length);
/* 2573 */     long[][] a = LongBigArrays.newBigArray(length);
/* 2574 */     copy(array, offset, a, 0L, length);
/* 2575 */     return a;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static long[][] copy(long[][] array) {
/* 2585 */     long[][] base = (long[][])array.clone();
/* 2586 */     for (int i = base.length; i-- != 0; base[i] = (long[])array[i].clone());
/* 2587 */     return base;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static void fill(long[][] array, long value) {
/* 2601 */     for (int i = array.length; i-- != 0; Arrays.fill(array[i], value));
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static void fill(long[][] array, long from, long to, long value) {
/* 2617 */     long length = length(array);
/* 2618 */     ensureFromTo(length, from, to);
/* 2619 */     if (length == 0L)
/* 2620 */       return;  int fromSegment = segment(from);
/* 2621 */     int toSegment = segment(to);
/* 2622 */     int fromDispl = displacement(from);
/* 2623 */     int toDispl = displacement(to);
/* 2624 */     if (fromSegment == toSegment) {
/* 2625 */       Arrays.fill(array[fromSegment], fromDispl, toDispl, value);
/*      */       return;
/*      */     } 
/* 2628 */     if (toDispl != 0) Arrays.fill(array[toSegment], 0, toDispl, value); 
/* 2629 */     for (; --toSegment > fromSegment; Arrays.fill(array[toSegment], value));
/* 2630 */     Arrays.fill(array[fromSegment], fromDispl, 134217728, value);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static boolean equals(long[][] a1, long[][] a2) {
/* 2645 */     if (length(a1) != length(a2)) return false; 
/* 2646 */     int i = a1.length;
/*      */     
/* 2648 */     while (i-- != 0) {
/* 2649 */       long[] t = a1[i];
/* 2650 */       long[] u = a2[i];
/* 2651 */       int j = t.length;
/* 2652 */       while (j-- != 0) { if (t[j] != u[j]) return false;  }
/*      */     
/* 2654 */     }  return true;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static String toString(long[][] a) {
/* 2664 */     if (a == null) return "null"; 
/* 2665 */     long last = length(a) - 1L;
/* 2666 */     if (last == -1L) return "[]"; 
/* 2667 */     StringBuilder b = new StringBuilder();
/* 2668 */     b.append('['); long i;
/* 2669 */     for (i = 0L;; i++) {
/* 2670 */       b.append(String.valueOf(get(a, i)));
/* 2671 */       if (i == last) return b.append(']').toString(); 
/* 2672 */       b.append(", ");
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
/*      */   public static void ensureFromTo(long[][] a, long from, long to) {
/* 2691 */     ensureFromTo(length(a), from, to);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static void ensureOffsetLength(long[][] a, long offset, long length) {
/* 2708 */     ensureOffsetLength(length(a), offset, length);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static void ensureSameLength(long[][] a, long[][] b) {
/* 2719 */     if (length(a) != length(b)) throw new IllegalArgumentException("Array size mismatch: " + length(a) + " != " + length(b));
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
/*      */   public static long[][] shuffle(long[][] a, long from, long to, Random random) {
/* 2732 */     for (long i = to - from; i-- != 0L; ) {
/* 2733 */       long p = (random.nextLong() & Long.MAX_VALUE) % (i + 1L);
/* 2734 */       long t = get(a, from + i);
/* 2735 */       set(a, from + i, get(a, from + p));
/* 2736 */       set(a, from + p, t);
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
/*      */   
/*      */   public static long[][] shuffle(long[][] a, Random random) {
/* 2749 */     for (long i = length(a); i-- != 0L; ) {
/* 2750 */       long p = (random.nextLong() & Long.MAX_VALUE) % (i + 1L);
/* 2751 */       long t = get(a, i);
/* 2752 */       set(a, i, get(a, p));
/* 2753 */       set(a, p, t);
/*      */     } 
/* 2755 */     return a;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static double get(double[][] array, long index) {
/* 2806 */     return array[segment(index)][displacement(index)];
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static void set(double[][] array, long index, double value) {
/* 2817 */     array[segment(index)][displacement(index)] = value;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static void swap(double[][] array, long first, long second) {
/* 2828 */     double t = array[segment(first)][displacement(first)];
/* 2829 */     array[segment(first)][displacement(first)] = array[segment(second)][displacement(second)];
/* 2830 */     array[segment(second)][displacement(second)] = t;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static double[][] reverse(double[][] a) {
/* 2840 */     long length = length(a);
/* 2841 */     for (long i = length / 2L; i-- != 0L; swap(a, i, length - i - 1L));
/* 2842 */     return a;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static void add(double[][] array, long index, double incr) {
/* 2853 */     array[segment(index)][displacement(index)] = array[segment(index)][displacement(index)] + incr;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static void mul(double[][] array, long index, double factor) {
/* 2864 */     array[segment(index)][displacement(index)] = array[segment(index)][displacement(index)] * factor;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static void incr(double[][] array, long index) {
/* 2874 */     array[segment(index)][displacement(index)] = array[segment(index)][displacement(index)] + 1.0D;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static void decr(double[][] array, long index) {
/* 2884 */     array[segment(index)][displacement(index)] = array[segment(index)][displacement(index)] - 1.0D;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static void assertBigArray(double[][] array) {
/* 2896 */     int l = array.length;
/* 2897 */     if (l == 0)
/* 2898 */       return;  for (int i = 0; i < l - 1; ) { if ((array[i]).length != 134217728) throw new IllegalStateException("All segments except for the last one must be of length 2^" + Integer.toString(27));  i++; }
/* 2899 */      if ((array[l - 1]).length > 134217728) throw new IllegalStateException("The last segment must be of length at most 2^" + Integer.toString(27)); 
/* 2900 */     if ((array[l - 1]).length == 0 && l == 1) throw new IllegalStateException("The last segment must be of nonzero length");
/*      */   
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static long length(double[][] array) {
/* 2910 */     int length = array.length;
/* 2911 */     return (length == 0) ? 0L : (start(length - 1) + (array[length - 1]).length);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static void copy(double[][] srcArray, long srcPos, double[][] destArray, long destPos, long length) {
/* 2926 */     if (destPos <= srcPos) {
/* 2927 */       int srcSegment = segment(srcPos);
/* 2928 */       int destSegment = segment(destPos);
/* 2929 */       int srcDispl = displacement(srcPos);
/* 2930 */       int destDispl = displacement(destPos);
/* 2931 */       while (length > 0L) {
/* 2932 */         int l = (int)Math.min(length, Math.min((srcArray[srcSegment]).length - srcDispl, (destArray[destSegment]).length - destDispl));
/* 2933 */         if (l == 0) throw new ArrayIndexOutOfBoundsException(); 
/* 2934 */         System.arraycopy(srcArray[srcSegment], srcDispl, destArray[destSegment], destDispl, l);
/* 2935 */         if ((srcDispl += l) == 134217728) {
/* 2936 */           srcDispl = 0;
/* 2937 */           srcSegment++;
/*      */         } 
/* 2939 */         if ((destDispl += l) == 134217728) {
/* 2940 */           destDispl = 0;
/* 2941 */           destSegment++;
/*      */         } 
/* 2943 */         length -= l;
/*      */       } 
/*      */     } else {
/* 2946 */       int srcSegment = segment(srcPos + length);
/* 2947 */       int destSegment = segment(destPos + length);
/* 2948 */       int srcDispl = displacement(srcPos + length);
/* 2949 */       int destDispl = displacement(destPos + length);
/* 2950 */       while (length > 0L) {
/* 2951 */         if (srcDispl == 0) {
/* 2952 */           srcDispl = 134217728;
/* 2953 */           srcSegment--;
/*      */         } 
/* 2955 */         if (destDispl == 0) {
/* 2956 */           destDispl = 134217728;
/* 2957 */           destSegment--;
/*      */         } 
/* 2959 */         int l = (int)Math.min(length, Math.min(srcDispl, destDispl));
/* 2960 */         if (l == 0) throw new ArrayIndexOutOfBoundsException(); 
/* 2961 */         System.arraycopy(srcArray[srcSegment], srcDispl - l, destArray[destSegment], destDispl - l, l);
/* 2962 */         srcDispl -= l;
/* 2963 */         destDispl -= l;
/* 2964 */         length -= l;
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
/*      */   public static void copyFromBig(double[][] srcArray, long srcPos, double[] destArray, int destPos, int length) {
/* 2980 */     int srcSegment = segment(srcPos);
/* 2981 */     int srcDispl = displacement(srcPos);
/* 2982 */     while (length > 0) {
/* 2983 */       int l = Math.min((srcArray[srcSegment]).length - srcDispl, length);
/* 2984 */       if (l == 0) throw new ArrayIndexOutOfBoundsException(); 
/* 2985 */       System.arraycopy(srcArray[srcSegment], srcDispl, destArray, destPos, l);
/* 2986 */       if ((srcDispl += l) == 134217728) {
/* 2987 */         srcDispl = 0;
/* 2988 */         srcSegment++;
/*      */       } 
/* 2990 */       destPos += l;
/* 2991 */       length -= l;
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
/*      */   public static void copyToBig(double[] srcArray, int srcPos, double[][] destArray, long destPos, long length) {
/* 3006 */     int destSegment = segment(destPos);
/* 3007 */     int destDispl = displacement(destPos);
/* 3008 */     while (length > 0L) {
/* 3009 */       int l = (int)Math.min(((destArray[destSegment]).length - destDispl), length);
/* 3010 */       if (l == 0) throw new ArrayIndexOutOfBoundsException(); 
/* 3011 */       System.arraycopy(srcArray, srcPos, destArray[destSegment], destDispl, l);
/* 3012 */       if ((destDispl += l) == 134217728) {
/* 3013 */         destDispl = 0;
/* 3014 */         destSegment++;
/*      */       } 
/* 3016 */       srcPos += l;
/* 3017 */       length -= l;
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
/*      */   public static double[][] wrap(double[] array) {
/* 3031 */     if (array.length == 0) return DoubleBigArrays.EMPTY_BIG_ARRAY; 
/* 3032 */     if (array.length <= 134217728) return new double[][] { array }; 
/* 3033 */     double[][] bigArray = DoubleBigArrays.newBigArray(array.length);
/* 3034 */     for (int i = 0; i < bigArray.length; ) { System.arraycopy(array, (int)start(i), bigArray[i], 0, (bigArray[i]).length); i++; }
/* 3035 */      return bigArray;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static double[][] ensureCapacity(double[][] array, long length) {
/* 3056 */     return ensureCapacity(array, length, length(array));
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static double[][] forceCapacity(double[][] array, long length, long preserve) {
/* 3075 */     ensureLength(length);
/* 3076 */     int valid = array.length - ((array.length == 0 || (array.length > 0 && (array[array.length - 1]).length == 134217728)) ? 0 : 1);
/* 3077 */     int baseLength = (int)(length + 134217727L >>> 27L);
/* 3078 */     double[][] base = Arrays.<double[]>copyOf(array, baseLength);
/* 3079 */     int residual = (int)(length & 0x7FFFFFFL);
/* 3080 */     if (residual != 0)
/* 3081 */     { for (int i = valid; i < baseLength - 1; ) { base[i] = new double[134217728]; i++; }
/* 3082 */        base[baseLength - 1] = new double[residual]; }
/* 3083 */     else { for (int i = valid; i < baseLength; ) { base[i] = new double[134217728]; i++; }  }
/* 3084 */      if (preserve - valid * 134217728L > 0L) copy(array, valid * 134217728L, base, valid * 134217728L, preserve - valid * 134217728L); 
/* 3085 */     return base;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static double[][] ensureCapacity(double[][] array, long length, long preserve) {
/* 3105 */     return (length > length(array)) ? forceCapacity(array, length, preserve) : array;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static double[][] grow(double[][] array, long length) {
/* 3127 */     long oldLength = length(array);
/* 3128 */     return (length > oldLength) ? grow(array, length, oldLength) : array;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static double[][] grow(double[][] array, long length, long preserve) {
/* 3153 */     long oldLength = length(array);
/* 3154 */     return (length > oldLength) ? ensureCapacity(array, Math.max(oldLength + (oldLength >> 1L), length), preserve) : array;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static double[][] trim(double[][] array, long length) {
/* 3172 */     ensureLength(length);
/* 3173 */     long oldLength = length(array);
/* 3174 */     if (length >= oldLength) return array; 
/* 3175 */     int baseLength = (int)(length + 134217727L >>> 27L);
/* 3176 */     double[][] base = Arrays.<double[]>copyOf(array, baseLength);
/* 3177 */     int residual = (int)(length & 0x7FFFFFFL);
/* 3178 */     if (residual != 0) base[baseLength - 1] = DoubleArrays.trim(base[baseLength - 1], residual); 
/* 3179 */     return base;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static double[][] setLength(double[][] array, long length) {
/* 3199 */     long oldLength = length(array);
/* 3200 */     if (length == oldLength) return array; 
/* 3201 */     if (length < oldLength) return trim(array, length); 
/* 3202 */     return ensureCapacity(array, length);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static double[][] copy(double[][] array, long offset, long length) {
/* 3215 */     ensureOffsetLength(array, offset, length);
/* 3216 */     double[][] a = DoubleBigArrays.newBigArray(length);
/* 3217 */     copy(array, offset, a, 0L, length);
/* 3218 */     return a;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static double[][] copy(double[][] array) {
/* 3228 */     double[][] base = (double[][])array.clone();
/* 3229 */     for (int i = base.length; i-- != 0; base[i] = (double[])array[i].clone());
/* 3230 */     return base;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static void fill(double[][] array, double value) {
/* 3244 */     for (int i = array.length; i-- != 0; Arrays.fill(array[i], value));
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static void fill(double[][] array, long from, long to, double value) {
/* 3260 */     long length = length(array);
/* 3261 */     ensureFromTo(length, from, to);
/* 3262 */     if (length == 0L)
/* 3263 */       return;  int fromSegment = segment(from);
/* 3264 */     int toSegment = segment(to);
/* 3265 */     int fromDispl = displacement(from);
/* 3266 */     int toDispl = displacement(to);
/* 3267 */     if (fromSegment == toSegment) {
/* 3268 */       Arrays.fill(array[fromSegment], fromDispl, toDispl, value);
/*      */       return;
/*      */     } 
/* 3271 */     if (toDispl != 0) Arrays.fill(array[toSegment], 0, toDispl, value); 
/* 3272 */     for (; --toSegment > fromSegment; Arrays.fill(array[toSegment], value));
/* 3273 */     Arrays.fill(array[fromSegment], fromDispl, 134217728, value);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static boolean equals(double[][] a1, double[][] a2) {
/* 3288 */     if (length(a1) != length(a2)) return false; 
/* 3289 */     int i = a1.length;
/*      */     
/* 3291 */     while (i-- != 0) {
/* 3292 */       double[] t = a1[i];
/* 3293 */       double[] u = a2[i];
/* 3294 */       int j = t.length;
/* 3295 */       while (j-- != 0) { if (Double.doubleToLongBits(t[j]) != Double.doubleToLongBits(u[j])) return false;  }
/*      */     
/* 3297 */     }  return true;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static String toString(double[][] a) {
/* 3307 */     if (a == null) return "null"; 
/* 3308 */     long last = length(a) - 1L;
/* 3309 */     if (last == -1L) return "[]"; 
/* 3310 */     StringBuilder b = new StringBuilder();
/* 3311 */     b.append('['); long i;
/* 3312 */     for (i = 0L;; i++) {
/* 3313 */       b.append(String.valueOf(get(a, i)));
/* 3314 */       if (i == last) return b.append(']').toString(); 
/* 3315 */       b.append(", ");
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
/*      */   public static void ensureFromTo(double[][] a, long from, long to) {
/* 3334 */     ensureFromTo(length(a), from, to);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static void ensureOffsetLength(double[][] a, long offset, long length) {
/* 3351 */     ensureOffsetLength(length(a), offset, length);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static void ensureSameLength(double[][] a, double[][] b) {
/* 3362 */     if (length(a) != length(b)) throw new IllegalArgumentException("Array size mismatch: " + length(a) + " != " + length(b));
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
/*      */   public static double[][] shuffle(double[][] a, long from, long to, Random random) {
/* 3375 */     for (long i = to - from; i-- != 0L; ) {
/* 3376 */       long p = (random.nextLong() & Long.MAX_VALUE) % (i + 1L);
/* 3377 */       double t = get(a, from + i);
/* 3378 */       set(a, from + i, get(a, from + p));
/* 3379 */       set(a, from + p, t);
/*      */     } 
/* 3381 */     return a;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static double[][] shuffle(double[][] a, Random random) {
/* 3392 */     for (long i = length(a); i-- != 0L; ) {
/* 3393 */       long p = (random.nextLong() & Long.MAX_VALUE) % (i + 1L);
/* 3394 */       double t = get(a, i);
/* 3395 */       set(a, i, get(a, p));
/* 3396 */       set(a, p, t);
/*      */     } 
/* 3398 */     return a;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static boolean get(boolean[][] array, long index) {
/* 3449 */     return array[segment(index)][displacement(index)];
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static void set(boolean[][] array, long index, boolean value) {
/* 3460 */     array[segment(index)][displacement(index)] = value;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static void swap(boolean[][] array, long first, long second) {
/* 3471 */     boolean t = array[segment(first)][displacement(first)];
/* 3472 */     array[segment(first)][displacement(first)] = array[segment(second)][displacement(second)];
/* 3473 */     array[segment(second)][displacement(second)] = t;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static boolean[][] reverse(boolean[][] a) {
/* 3483 */     long length = length(a);
/* 3484 */     for (long i = length / 2L; i-- != 0L; swap(a, i, length - i - 1L));
/* 3485 */     return a;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static void assertBigArray(boolean[][] array) {
/* 3497 */     int l = array.length;
/* 3498 */     if (l == 0)
/* 3499 */       return;  for (int i = 0; i < l - 1; ) { if ((array[i]).length != 134217728) throw new IllegalStateException("All segments except for the last one must be of length 2^" + Integer.toString(27));  i++; }
/* 3500 */      if ((array[l - 1]).length > 134217728) throw new IllegalStateException("The last segment must be of length at most 2^" + Integer.toString(27)); 
/* 3501 */     if ((array[l - 1]).length == 0 && l == 1) throw new IllegalStateException("The last segment must be of nonzero length");
/*      */   
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static long length(boolean[][] array) {
/* 3511 */     int length = array.length;
/* 3512 */     return (length == 0) ? 0L : (start(length - 1) + (array[length - 1]).length);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static void copy(boolean[][] srcArray, long srcPos, boolean[][] destArray, long destPos, long length) {
/* 3527 */     if (destPos <= srcPos) {
/* 3528 */       int srcSegment = segment(srcPos);
/* 3529 */       int destSegment = segment(destPos);
/* 3530 */       int srcDispl = displacement(srcPos);
/* 3531 */       int destDispl = displacement(destPos);
/* 3532 */       while (length > 0L) {
/* 3533 */         int l = (int)Math.min(length, Math.min((srcArray[srcSegment]).length - srcDispl, (destArray[destSegment]).length - destDispl));
/* 3534 */         if (l == 0) throw new ArrayIndexOutOfBoundsException(); 
/* 3535 */         System.arraycopy(srcArray[srcSegment], srcDispl, destArray[destSegment], destDispl, l);
/* 3536 */         if ((srcDispl += l) == 134217728) {
/* 3537 */           srcDispl = 0;
/* 3538 */           srcSegment++;
/*      */         } 
/* 3540 */         if ((destDispl += l) == 134217728) {
/* 3541 */           destDispl = 0;
/* 3542 */           destSegment++;
/*      */         } 
/* 3544 */         length -= l;
/*      */       } 
/*      */     } else {
/* 3547 */       int srcSegment = segment(srcPos + length);
/* 3548 */       int destSegment = segment(destPos + length);
/* 3549 */       int srcDispl = displacement(srcPos + length);
/* 3550 */       int destDispl = displacement(destPos + length);
/* 3551 */       while (length > 0L) {
/* 3552 */         if (srcDispl == 0) {
/* 3553 */           srcDispl = 134217728;
/* 3554 */           srcSegment--;
/*      */         } 
/* 3556 */         if (destDispl == 0) {
/* 3557 */           destDispl = 134217728;
/* 3558 */           destSegment--;
/*      */         } 
/* 3560 */         int l = (int)Math.min(length, Math.min(srcDispl, destDispl));
/* 3561 */         if (l == 0) throw new ArrayIndexOutOfBoundsException(); 
/* 3562 */         System.arraycopy(srcArray[srcSegment], srcDispl - l, destArray[destSegment], destDispl - l, l);
/* 3563 */         srcDispl -= l;
/* 3564 */         destDispl -= l;
/* 3565 */         length -= l;
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
/*      */   public static void copyFromBig(boolean[][] srcArray, long srcPos, boolean[] destArray, int destPos, int length) {
/* 3581 */     int srcSegment = segment(srcPos);
/* 3582 */     int srcDispl = displacement(srcPos);
/* 3583 */     while (length > 0) {
/* 3584 */       int l = Math.min((srcArray[srcSegment]).length - srcDispl, length);
/* 3585 */       if (l == 0) throw new ArrayIndexOutOfBoundsException(); 
/* 3586 */       System.arraycopy(srcArray[srcSegment], srcDispl, destArray, destPos, l);
/* 3587 */       if ((srcDispl += l) == 134217728) {
/* 3588 */         srcDispl = 0;
/* 3589 */         srcSegment++;
/*      */       } 
/* 3591 */       destPos += l;
/* 3592 */       length -= l;
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
/*      */   public static void copyToBig(boolean[] srcArray, int srcPos, boolean[][] destArray, long destPos, long length) {
/* 3607 */     int destSegment = segment(destPos);
/* 3608 */     int destDispl = displacement(destPos);
/* 3609 */     while (length > 0L) {
/* 3610 */       int l = (int)Math.min(((destArray[destSegment]).length - destDispl), length);
/* 3611 */       if (l == 0) throw new ArrayIndexOutOfBoundsException(); 
/* 3612 */       System.arraycopy(srcArray, srcPos, destArray[destSegment], destDispl, l);
/* 3613 */       if ((destDispl += l) == 134217728) {
/* 3614 */         destDispl = 0;
/* 3615 */         destSegment++;
/*      */       } 
/* 3617 */       srcPos += l;
/* 3618 */       length -= l;
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
/*      */   public static boolean[][] wrap(boolean[] array) {
/* 3632 */     if (array.length == 0) return BooleanBigArrays.EMPTY_BIG_ARRAY; 
/* 3633 */     if (array.length <= 134217728) return new boolean[][] { array }; 
/* 3634 */     boolean[][] bigArray = BooleanBigArrays.newBigArray(array.length);
/* 3635 */     for (int i = 0; i < bigArray.length; ) { System.arraycopy(array, (int)start(i), bigArray[i], 0, (bigArray[i]).length); i++; }
/* 3636 */      return bigArray;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static boolean[][] ensureCapacity(boolean[][] array, long length) {
/* 3657 */     return ensureCapacity(array, length, length(array));
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static boolean[][] forceCapacity(boolean[][] array, long length, long preserve) {
/* 3676 */     ensureLength(length);
/* 3677 */     int valid = array.length - ((array.length == 0 || (array.length > 0 && (array[array.length - 1]).length == 134217728)) ? 0 : 1);
/* 3678 */     int baseLength = (int)(length + 134217727L >>> 27L);
/* 3679 */     boolean[][] base = Arrays.<boolean[]>copyOf(array, baseLength);
/* 3680 */     int residual = (int)(length & 0x7FFFFFFL);
/* 3681 */     if (residual != 0)
/* 3682 */     { for (int i = valid; i < baseLength - 1; ) { base[i] = new boolean[134217728]; i++; }
/* 3683 */        base[baseLength - 1] = new boolean[residual]; }
/* 3684 */     else { for (int i = valid; i < baseLength; ) { base[i] = new boolean[134217728]; i++; }  }
/* 3685 */      if (preserve - valid * 134217728L > 0L) copy(array, valid * 134217728L, base, valid * 134217728L, preserve - valid * 134217728L); 
/* 3686 */     return base;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static boolean[][] ensureCapacity(boolean[][] array, long length, long preserve) {
/* 3706 */     return (length > length(array)) ? forceCapacity(array, length, preserve) : array;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static boolean[][] grow(boolean[][] array, long length) {
/* 3728 */     long oldLength = length(array);
/* 3729 */     return (length > oldLength) ? grow(array, length, oldLength) : array;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static boolean[][] grow(boolean[][] array, long length, long preserve) {
/* 3754 */     long oldLength = length(array);
/* 3755 */     return (length > oldLength) ? ensureCapacity(array, Math.max(oldLength + (oldLength >> 1L), length), preserve) : array;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static boolean[][] trim(boolean[][] array, long length) {
/* 3773 */     ensureLength(length);
/* 3774 */     long oldLength = length(array);
/* 3775 */     if (length >= oldLength) return array; 
/* 3776 */     int baseLength = (int)(length + 134217727L >>> 27L);
/* 3777 */     boolean[][] base = Arrays.<boolean[]>copyOf(array, baseLength);
/* 3778 */     int residual = (int)(length & 0x7FFFFFFL);
/* 3779 */     if (residual != 0) base[baseLength - 1] = BooleanArrays.trim(base[baseLength - 1], residual); 
/* 3780 */     return base;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static boolean[][] setLength(boolean[][] array, long length) {
/* 3800 */     long oldLength = length(array);
/* 3801 */     if (length == oldLength) return array; 
/* 3802 */     if (length < oldLength) return trim(array, length); 
/* 3803 */     return ensureCapacity(array, length);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static boolean[][] copy(boolean[][] array, long offset, long length) {
/* 3816 */     ensureOffsetLength(array, offset, length);
/* 3817 */     boolean[][] a = BooleanBigArrays.newBigArray(length);
/* 3818 */     copy(array, offset, a, 0L, length);
/* 3819 */     return a;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static boolean[][] copy(boolean[][] array) {
/* 3829 */     boolean[][] base = (boolean[][])array.clone();
/* 3830 */     for (int i = base.length; i-- != 0; base[i] = (boolean[])array[i].clone());
/* 3831 */     return base;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static void fill(boolean[][] array, boolean value) {
/* 3845 */     for (int i = array.length; i-- != 0; Arrays.fill(array[i], value));
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static void fill(boolean[][] array, long from, long to, boolean value) {
/* 3861 */     long length = length(array);
/* 3862 */     ensureFromTo(length, from, to);
/* 3863 */     if (length == 0L)
/* 3864 */       return;  int fromSegment = segment(from);
/* 3865 */     int toSegment = segment(to);
/* 3866 */     int fromDispl = displacement(from);
/* 3867 */     int toDispl = displacement(to);
/* 3868 */     if (fromSegment == toSegment) {
/* 3869 */       Arrays.fill(array[fromSegment], fromDispl, toDispl, value);
/*      */       return;
/*      */     } 
/* 3872 */     if (toDispl != 0) Arrays.fill(array[toSegment], 0, toDispl, value); 
/* 3873 */     for (; --toSegment > fromSegment; Arrays.fill(array[toSegment], value));
/* 3874 */     Arrays.fill(array[fromSegment], fromDispl, 134217728, value);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static boolean equals(boolean[][] a1, boolean[][] a2) {
/* 3889 */     if (length(a1) != length(a2)) return false; 
/* 3890 */     int i = a1.length;
/*      */     
/* 3892 */     while (i-- != 0) {
/* 3893 */       boolean[] t = a1[i];
/* 3894 */       boolean[] u = a2[i];
/* 3895 */       int j = t.length;
/* 3896 */       while (j-- != 0) { if (t[j] != u[j]) return false;  }
/*      */     
/* 3898 */     }  return true;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static String toString(boolean[][] a) {
/* 3908 */     if (a == null) return "null"; 
/* 3909 */     long last = length(a) - 1L;
/* 3910 */     if (last == -1L) return "[]"; 
/* 3911 */     StringBuilder b = new StringBuilder();
/* 3912 */     b.append('['); long i;
/* 3913 */     for (i = 0L;; i++) {
/* 3914 */       b.append(String.valueOf(get(a, i)));
/* 3915 */       if (i == last) return b.append(']').toString(); 
/* 3916 */       b.append(", ");
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
/*      */   public static void ensureFromTo(boolean[][] a, long from, long to) {
/* 3935 */     ensureFromTo(length(a), from, to);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static void ensureOffsetLength(boolean[][] a, long offset, long length) {
/* 3952 */     ensureOffsetLength(length(a), offset, length);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static void ensureSameLength(boolean[][] a, boolean[][] b) {
/* 3963 */     if (length(a) != length(b)) throw new IllegalArgumentException("Array size mismatch: " + length(a) + " != " + length(b));
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
/*      */   public static boolean[][] shuffle(boolean[][] a, long from, long to, Random random) {
/* 3976 */     for (long i = to - from; i-- != 0L; ) {
/* 3977 */       long p = (random.nextLong() & Long.MAX_VALUE) % (i + 1L);
/* 3978 */       boolean t = get(a, from + i);
/* 3979 */       set(a, from + i, get(a, from + p));
/* 3980 */       set(a, from + p, t);
/*      */     } 
/* 3982 */     return a;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static boolean[][] shuffle(boolean[][] a, Random random) {
/* 3993 */     for (long i = length(a); i-- != 0L; ) {
/* 3994 */       long p = (random.nextLong() & Long.MAX_VALUE) % (i + 1L);
/* 3995 */       boolean t = get(a, i);
/* 3996 */       set(a, i, get(a, p));
/* 3997 */       set(a, p, t);
/*      */     } 
/* 3999 */     return a;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static short get(short[][] array, long index) {
/* 4050 */     return array[segment(index)][displacement(index)];
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static void set(short[][] array, long index, short value) {
/* 4061 */     array[segment(index)][displacement(index)] = value;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static void swap(short[][] array, long first, long second) {
/* 4072 */     short t = array[segment(first)][displacement(first)];
/* 4073 */     array[segment(first)][displacement(first)] = array[segment(second)][displacement(second)];
/* 4074 */     array[segment(second)][displacement(second)] = t;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static short[][] reverse(short[][] a) {
/* 4084 */     long length = length(a);
/* 4085 */     for (long i = length / 2L; i-- != 0L; swap(a, i, length - i - 1L));
/* 4086 */     return a;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static void add(short[][] array, long index, short incr) {
/* 4097 */     array[segment(index)][displacement(index)] = (short)(array[segment(index)][displacement(index)] + incr);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static void mul(short[][] array, long index, short factor) {
/* 4108 */     array[segment(index)][displacement(index)] = (short)(array[segment(index)][displacement(index)] * factor);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static void incr(short[][] array, long index) {
/* 4118 */     array[segment(index)][displacement(index)] = (short)(array[segment(index)][displacement(index)] + 1);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static void decr(short[][] array, long index) {
/* 4128 */     array[segment(index)][displacement(index)] = (short)(array[segment(index)][displacement(index)] - 1);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static void assertBigArray(short[][] array) {
/* 4140 */     int l = array.length;
/* 4141 */     if (l == 0)
/* 4142 */       return;  for (int i = 0; i < l - 1; ) { if ((array[i]).length != 134217728) throw new IllegalStateException("All segments except for the last one must be of length 2^" + Integer.toString(27));  i++; }
/* 4143 */      if ((array[l - 1]).length > 134217728) throw new IllegalStateException("The last segment must be of length at most 2^" + Integer.toString(27)); 
/* 4144 */     if ((array[l - 1]).length == 0 && l == 1) throw new IllegalStateException("The last segment must be of nonzero length");
/*      */   
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static long length(short[][] array) {
/* 4154 */     int length = array.length;
/* 4155 */     return (length == 0) ? 0L : (start(length - 1) + (array[length - 1]).length);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static void copy(short[][] srcArray, long srcPos, short[][] destArray, long destPos, long length) {
/* 4170 */     if (destPos <= srcPos) {
/* 4171 */       int srcSegment = segment(srcPos);
/* 4172 */       int destSegment = segment(destPos);
/* 4173 */       int srcDispl = displacement(srcPos);
/* 4174 */       int destDispl = displacement(destPos);
/* 4175 */       while (length > 0L) {
/* 4176 */         int l = (int)Math.min(length, Math.min((srcArray[srcSegment]).length - srcDispl, (destArray[destSegment]).length - destDispl));
/* 4177 */         if (l == 0) throw new ArrayIndexOutOfBoundsException(); 
/* 4178 */         System.arraycopy(srcArray[srcSegment], srcDispl, destArray[destSegment], destDispl, l);
/* 4179 */         if ((srcDispl += l) == 134217728) {
/* 4180 */           srcDispl = 0;
/* 4181 */           srcSegment++;
/*      */         } 
/* 4183 */         if ((destDispl += l) == 134217728) {
/* 4184 */           destDispl = 0;
/* 4185 */           destSegment++;
/*      */         } 
/* 4187 */         length -= l;
/*      */       } 
/*      */     } else {
/* 4190 */       int srcSegment = segment(srcPos + length);
/* 4191 */       int destSegment = segment(destPos + length);
/* 4192 */       int srcDispl = displacement(srcPos + length);
/* 4193 */       int destDispl = displacement(destPos + length);
/* 4194 */       while (length > 0L) {
/* 4195 */         if (srcDispl == 0) {
/* 4196 */           srcDispl = 134217728;
/* 4197 */           srcSegment--;
/*      */         } 
/* 4199 */         if (destDispl == 0) {
/* 4200 */           destDispl = 134217728;
/* 4201 */           destSegment--;
/*      */         } 
/* 4203 */         int l = (int)Math.min(length, Math.min(srcDispl, destDispl));
/* 4204 */         if (l == 0) throw new ArrayIndexOutOfBoundsException(); 
/* 4205 */         System.arraycopy(srcArray[srcSegment], srcDispl - l, destArray[destSegment], destDispl - l, l);
/* 4206 */         srcDispl -= l;
/* 4207 */         destDispl -= l;
/* 4208 */         length -= l;
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
/*      */   public static void copyFromBig(short[][] srcArray, long srcPos, short[] destArray, int destPos, int length) {
/* 4224 */     int srcSegment = segment(srcPos);
/* 4225 */     int srcDispl = displacement(srcPos);
/* 4226 */     while (length > 0) {
/* 4227 */       int l = Math.min((srcArray[srcSegment]).length - srcDispl, length);
/* 4228 */       if (l == 0) throw new ArrayIndexOutOfBoundsException(); 
/* 4229 */       System.arraycopy(srcArray[srcSegment], srcDispl, destArray, destPos, l);
/* 4230 */       if ((srcDispl += l) == 134217728) {
/* 4231 */         srcDispl = 0;
/* 4232 */         srcSegment++;
/*      */       } 
/* 4234 */       destPos += l;
/* 4235 */       length -= l;
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
/*      */   public static void copyToBig(short[] srcArray, int srcPos, short[][] destArray, long destPos, long length) {
/* 4250 */     int destSegment = segment(destPos);
/* 4251 */     int destDispl = displacement(destPos);
/* 4252 */     while (length > 0L) {
/* 4253 */       int l = (int)Math.min(((destArray[destSegment]).length - destDispl), length);
/* 4254 */       if (l == 0) throw new ArrayIndexOutOfBoundsException(); 
/* 4255 */       System.arraycopy(srcArray, srcPos, destArray[destSegment], destDispl, l);
/* 4256 */       if ((destDispl += l) == 134217728) {
/* 4257 */         destDispl = 0;
/* 4258 */         destSegment++;
/*      */       } 
/* 4260 */       srcPos += l;
/* 4261 */       length -= l;
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
/*      */   public static short[][] wrap(short[] array) {
/* 4275 */     if (array.length == 0) return ShortBigArrays.EMPTY_BIG_ARRAY; 
/* 4276 */     if (array.length <= 134217728) return new short[][] { array }; 
/* 4277 */     short[][] bigArray = ShortBigArrays.newBigArray(array.length);
/* 4278 */     for (int i = 0; i < bigArray.length; ) { System.arraycopy(array, (int)start(i), bigArray[i], 0, (bigArray[i]).length); i++; }
/* 4279 */      return bigArray;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static short[][] ensureCapacity(short[][] array, long length) {
/* 4300 */     return ensureCapacity(array, length, length(array));
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static short[][] forceCapacity(short[][] array, long length, long preserve) {
/* 4319 */     ensureLength(length);
/* 4320 */     int valid = array.length - ((array.length == 0 || (array.length > 0 && (array[array.length - 1]).length == 134217728)) ? 0 : 1);
/* 4321 */     int baseLength = (int)(length + 134217727L >>> 27L);
/* 4322 */     short[][] base = Arrays.<short[]>copyOf(array, baseLength);
/* 4323 */     int residual = (int)(length & 0x7FFFFFFL);
/* 4324 */     if (residual != 0)
/* 4325 */     { for (int i = valid; i < baseLength - 1; ) { base[i] = new short[134217728]; i++; }
/* 4326 */        base[baseLength - 1] = new short[residual]; }
/* 4327 */     else { for (int i = valid; i < baseLength; ) { base[i] = new short[134217728]; i++; }  }
/* 4328 */      if (preserve - valid * 134217728L > 0L) copy(array, valid * 134217728L, base, valid * 134217728L, preserve - valid * 134217728L); 
/* 4329 */     return base;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static short[][] ensureCapacity(short[][] array, long length, long preserve) {
/* 4349 */     return (length > length(array)) ? forceCapacity(array, length, preserve) : array;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static short[][] grow(short[][] array, long length) {
/* 4371 */     long oldLength = length(array);
/* 4372 */     return (length > oldLength) ? grow(array, length, oldLength) : array;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static short[][] grow(short[][] array, long length, long preserve) {
/* 4397 */     long oldLength = length(array);
/* 4398 */     return (length > oldLength) ? ensureCapacity(array, Math.max(oldLength + (oldLength >> 1L), length), preserve) : array;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static short[][] trim(short[][] array, long length) {
/* 4416 */     ensureLength(length);
/* 4417 */     long oldLength = length(array);
/* 4418 */     if (length >= oldLength) return array; 
/* 4419 */     int baseLength = (int)(length + 134217727L >>> 27L);
/* 4420 */     short[][] base = Arrays.<short[]>copyOf(array, baseLength);
/* 4421 */     int residual = (int)(length & 0x7FFFFFFL);
/* 4422 */     if (residual != 0) base[baseLength - 1] = ShortArrays.trim(base[baseLength - 1], residual); 
/* 4423 */     return base;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static short[][] setLength(short[][] array, long length) {
/* 4443 */     long oldLength = length(array);
/* 4444 */     if (length == oldLength) return array; 
/* 4445 */     if (length < oldLength) return trim(array, length); 
/* 4446 */     return ensureCapacity(array, length);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static short[][] copy(short[][] array, long offset, long length) {
/* 4459 */     ensureOffsetLength(array, offset, length);
/* 4460 */     short[][] a = ShortBigArrays.newBigArray(length);
/* 4461 */     copy(array, offset, a, 0L, length);
/* 4462 */     return a;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static short[][] copy(short[][] array) {
/* 4472 */     short[][] base = (short[][])array.clone();
/* 4473 */     for (int i = base.length; i-- != 0; base[i] = (short[])array[i].clone());
/* 4474 */     return base;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static void fill(short[][] array, short value) {
/* 4488 */     for (int i = array.length; i-- != 0; Arrays.fill(array[i], value));
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static void fill(short[][] array, long from, long to, short value) {
/* 4504 */     long length = length(array);
/* 4505 */     ensureFromTo(length, from, to);
/* 4506 */     if (length == 0L)
/* 4507 */       return;  int fromSegment = segment(from);
/* 4508 */     int toSegment = segment(to);
/* 4509 */     int fromDispl = displacement(from);
/* 4510 */     int toDispl = displacement(to);
/* 4511 */     if (fromSegment == toSegment) {
/* 4512 */       Arrays.fill(array[fromSegment], fromDispl, toDispl, value);
/*      */       return;
/*      */     } 
/* 4515 */     if (toDispl != 0) Arrays.fill(array[toSegment], 0, toDispl, value); 
/* 4516 */     for (; --toSegment > fromSegment; Arrays.fill(array[toSegment], value));
/* 4517 */     Arrays.fill(array[fromSegment], fromDispl, 134217728, value);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static boolean equals(short[][] a1, short[][] a2) {
/* 4532 */     if (length(a1) != length(a2)) return false; 
/* 4533 */     int i = a1.length;
/*      */     
/* 4535 */     while (i-- != 0) {
/* 4536 */       short[] t = a1[i];
/* 4537 */       short[] u = a2[i];
/* 4538 */       int j = t.length;
/* 4539 */       while (j-- != 0) { if (t[j] != u[j]) return false;  }
/*      */     
/* 4541 */     }  return true;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static String toString(short[][] a) {
/* 4551 */     if (a == null) return "null"; 
/* 4552 */     long last = length(a) - 1L;
/* 4553 */     if (last == -1L) return "[]"; 
/* 4554 */     StringBuilder b = new StringBuilder();
/* 4555 */     b.append('['); long i;
/* 4556 */     for (i = 0L;; i++) {
/* 4557 */       b.append(String.valueOf(get(a, i)));
/* 4558 */       if (i == last) return b.append(']').toString(); 
/* 4559 */       b.append(", ");
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
/*      */   public static void ensureFromTo(short[][] a, long from, long to) {
/* 4578 */     ensureFromTo(length(a), from, to);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static void ensureOffsetLength(short[][] a, long offset, long length) {
/* 4595 */     ensureOffsetLength(length(a), offset, length);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static void ensureSameLength(short[][] a, short[][] b) {
/* 4606 */     if (length(a) != length(b)) throw new IllegalArgumentException("Array size mismatch: " + length(a) + " != " + length(b));
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
/*      */   public static short[][] shuffle(short[][] a, long from, long to, Random random) {
/* 4619 */     for (long i = to - from; i-- != 0L; ) {
/* 4620 */       long p = (random.nextLong() & Long.MAX_VALUE) % (i + 1L);
/* 4621 */       short t = get(a, from + i);
/* 4622 */       set(a, from + i, get(a, from + p));
/* 4623 */       set(a, from + p, t);
/*      */     } 
/* 4625 */     return a;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static short[][] shuffle(short[][] a, Random random) {
/* 4636 */     for (long i = length(a); i-- != 0L; ) {
/* 4637 */       long p = (random.nextLong() & Long.MAX_VALUE) % (i + 1L);
/* 4638 */       short t = get(a, i);
/* 4639 */       set(a, i, get(a, p));
/* 4640 */       set(a, p, t);
/*      */     } 
/* 4642 */     return a;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static char get(char[][] array, long index) {
/* 4693 */     return array[segment(index)][displacement(index)];
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static void set(char[][] array, long index, char value) {
/* 4704 */     array[segment(index)][displacement(index)] = value;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static void swap(char[][] array, long first, long second) {
/* 4715 */     char t = array[segment(first)][displacement(first)];
/* 4716 */     array[segment(first)][displacement(first)] = array[segment(second)][displacement(second)];
/* 4717 */     array[segment(second)][displacement(second)] = t;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static char[][] reverse(char[][] a) {
/* 4727 */     long length = length(a);
/* 4728 */     for (long i = length / 2L; i-- != 0L; swap(a, i, length - i - 1L));
/* 4729 */     return a;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static void add(char[][] array, long index, char incr) {
/* 4740 */     array[segment(index)][displacement(index)] = (char)(array[segment(index)][displacement(index)] + incr);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static void mul(char[][] array, long index, char factor) {
/* 4751 */     array[segment(index)][displacement(index)] = (char)(array[segment(index)][displacement(index)] * factor);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static void incr(char[][] array, long index) {
/* 4761 */     array[segment(index)][displacement(index)] = (char)(array[segment(index)][displacement(index)] + 1);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static void decr(char[][] array, long index) {
/* 4771 */     array[segment(index)][displacement(index)] = (char)(array[segment(index)][displacement(index)] - 1);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static void assertBigArray(char[][] array) {
/* 4783 */     int l = array.length;
/* 4784 */     if (l == 0)
/* 4785 */       return;  for (int i = 0; i < l - 1; ) { if ((array[i]).length != 134217728) throw new IllegalStateException("All segments except for the last one must be of length 2^" + Integer.toString(27));  i++; }
/* 4786 */      if ((array[l - 1]).length > 134217728) throw new IllegalStateException("The last segment must be of length at most 2^" + Integer.toString(27)); 
/* 4787 */     if ((array[l - 1]).length == 0 && l == 1) throw new IllegalStateException("The last segment must be of nonzero length");
/*      */   
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static long length(char[][] array) {
/* 4797 */     int length = array.length;
/* 4798 */     return (length == 0) ? 0L : (start(length - 1) + (array[length - 1]).length);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static void copy(char[][] srcArray, long srcPos, char[][] destArray, long destPos, long length) {
/* 4813 */     if (destPos <= srcPos) {
/* 4814 */       int srcSegment = segment(srcPos);
/* 4815 */       int destSegment = segment(destPos);
/* 4816 */       int srcDispl = displacement(srcPos);
/* 4817 */       int destDispl = displacement(destPos);
/* 4818 */       while (length > 0L) {
/* 4819 */         int l = (int)Math.min(length, Math.min((srcArray[srcSegment]).length - srcDispl, (destArray[destSegment]).length - destDispl));
/* 4820 */         if (l == 0) throw new ArrayIndexOutOfBoundsException(); 
/* 4821 */         System.arraycopy(srcArray[srcSegment], srcDispl, destArray[destSegment], destDispl, l);
/* 4822 */         if ((srcDispl += l) == 134217728) {
/* 4823 */           srcDispl = 0;
/* 4824 */           srcSegment++;
/*      */         } 
/* 4826 */         if ((destDispl += l) == 134217728) {
/* 4827 */           destDispl = 0;
/* 4828 */           destSegment++;
/*      */         } 
/* 4830 */         length -= l;
/*      */       } 
/*      */     } else {
/* 4833 */       int srcSegment = segment(srcPos + length);
/* 4834 */       int destSegment = segment(destPos + length);
/* 4835 */       int srcDispl = displacement(srcPos + length);
/* 4836 */       int destDispl = displacement(destPos + length);
/* 4837 */       while (length > 0L) {
/* 4838 */         if (srcDispl == 0) {
/* 4839 */           srcDispl = 134217728;
/* 4840 */           srcSegment--;
/*      */         } 
/* 4842 */         if (destDispl == 0) {
/* 4843 */           destDispl = 134217728;
/* 4844 */           destSegment--;
/*      */         } 
/* 4846 */         int l = (int)Math.min(length, Math.min(srcDispl, destDispl));
/* 4847 */         if (l == 0) throw new ArrayIndexOutOfBoundsException(); 
/* 4848 */         System.arraycopy(srcArray[srcSegment], srcDispl - l, destArray[destSegment], destDispl - l, l);
/* 4849 */         srcDispl -= l;
/* 4850 */         destDispl -= l;
/* 4851 */         length -= l;
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
/*      */   public static void copyFromBig(char[][] srcArray, long srcPos, char[] destArray, int destPos, int length) {
/* 4867 */     int srcSegment = segment(srcPos);
/* 4868 */     int srcDispl = displacement(srcPos);
/* 4869 */     while (length > 0) {
/* 4870 */       int l = Math.min((srcArray[srcSegment]).length - srcDispl, length);
/* 4871 */       if (l == 0) throw new ArrayIndexOutOfBoundsException(); 
/* 4872 */       System.arraycopy(srcArray[srcSegment], srcDispl, destArray, destPos, l);
/* 4873 */       if ((srcDispl += l) == 134217728) {
/* 4874 */         srcDispl = 0;
/* 4875 */         srcSegment++;
/*      */       } 
/* 4877 */       destPos += l;
/* 4878 */       length -= l;
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
/*      */   public static void copyToBig(char[] srcArray, int srcPos, char[][] destArray, long destPos, long length) {
/* 4893 */     int destSegment = segment(destPos);
/* 4894 */     int destDispl = displacement(destPos);
/* 4895 */     while (length > 0L) {
/* 4896 */       int l = (int)Math.min(((destArray[destSegment]).length - destDispl), length);
/* 4897 */       if (l == 0) throw new ArrayIndexOutOfBoundsException(); 
/* 4898 */       System.arraycopy(srcArray, srcPos, destArray[destSegment], destDispl, l);
/* 4899 */       if ((destDispl += l) == 134217728) {
/* 4900 */         destDispl = 0;
/* 4901 */         destSegment++;
/*      */       } 
/* 4903 */       srcPos += l;
/* 4904 */       length -= l;
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
/*      */   public static char[][] wrap(char[] array) {
/* 4918 */     if (array.length == 0) return CharBigArrays.EMPTY_BIG_ARRAY; 
/* 4919 */     if (array.length <= 134217728) return new char[][] { array }; 
/* 4920 */     char[][] bigArray = CharBigArrays.newBigArray(array.length);
/* 4921 */     for (int i = 0; i < bigArray.length; ) { System.arraycopy(array, (int)start(i), bigArray[i], 0, (bigArray[i]).length); i++; }
/* 4922 */      return bigArray;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static char[][] ensureCapacity(char[][] array, long length) {
/* 4943 */     return ensureCapacity(array, length, length(array));
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static char[][] forceCapacity(char[][] array, long length, long preserve) {
/* 4962 */     ensureLength(length);
/* 4963 */     int valid = array.length - ((array.length == 0 || (array.length > 0 && (array[array.length - 1]).length == 134217728)) ? 0 : 1);
/* 4964 */     int baseLength = (int)(length + 134217727L >>> 27L);
/* 4965 */     char[][] base = Arrays.<char[]>copyOf(array, baseLength);
/* 4966 */     int residual = (int)(length & 0x7FFFFFFL);
/* 4967 */     if (residual != 0)
/* 4968 */     { for (int i = valid; i < baseLength - 1; ) { base[i] = new char[134217728]; i++; }
/* 4969 */        base[baseLength - 1] = new char[residual]; }
/* 4970 */     else { for (int i = valid; i < baseLength; ) { base[i] = new char[134217728]; i++; }  }
/* 4971 */      if (preserve - valid * 134217728L > 0L) copy(array, valid * 134217728L, base, valid * 134217728L, preserve - valid * 134217728L); 
/* 4972 */     return base;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static char[][] ensureCapacity(char[][] array, long length, long preserve) {
/* 4992 */     return (length > length(array)) ? forceCapacity(array, length, preserve) : array;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static char[][] grow(char[][] array, long length) {
/* 5014 */     long oldLength = length(array);
/* 5015 */     return (length > oldLength) ? grow(array, length, oldLength) : array;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static char[][] grow(char[][] array, long length, long preserve) {
/* 5040 */     long oldLength = length(array);
/* 5041 */     return (length > oldLength) ? ensureCapacity(array, Math.max(oldLength + (oldLength >> 1L), length), preserve) : array;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static char[][] trim(char[][] array, long length) {
/* 5059 */     ensureLength(length);
/* 5060 */     long oldLength = length(array);
/* 5061 */     if (length >= oldLength) return array; 
/* 5062 */     int baseLength = (int)(length + 134217727L >>> 27L);
/* 5063 */     char[][] base = Arrays.<char[]>copyOf(array, baseLength);
/* 5064 */     int residual = (int)(length & 0x7FFFFFFL);
/* 5065 */     if (residual != 0) base[baseLength - 1] = CharArrays.trim(base[baseLength - 1], residual); 
/* 5066 */     return base;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static char[][] setLength(char[][] array, long length) {
/* 5086 */     long oldLength = length(array);
/* 5087 */     if (length == oldLength) return array; 
/* 5088 */     if (length < oldLength) return trim(array, length); 
/* 5089 */     return ensureCapacity(array, length);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static char[][] copy(char[][] array, long offset, long length) {
/* 5102 */     ensureOffsetLength(array, offset, length);
/* 5103 */     char[][] a = CharBigArrays.newBigArray(length);
/* 5104 */     copy(array, offset, a, 0L, length);
/* 5105 */     return a;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static char[][] copy(char[][] array) {
/* 5115 */     char[][] base = (char[][])array.clone();
/* 5116 */     for (int i = base.length; i-- != 0; base[i] = (char[])array[i].clone());
/* 5117 */     return base;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static void fill(char[][] array, char value) {
/* 5131 */     for (int i = array.length; i-- != 0; Arrays.fill(array[i], value));
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static void fill(char[][] array, long from, long to, char value) {
/* 5147 */     long length = length(array);
/* 5148 */     ensureFromTo(length, from, to);
/* 5149 */     if (length == 0L)
/* 5150 */       return;  int fromSegment = segment(from);
/* 5151 */     int toSegment = segment(to);
/* 5152 */     int fromDispl = displacement(from);
/* 5153 */     int toDispl = displacement(to);
/* 5154 */     if (fromSegment == toSegment) {
/* 5155 */       Arrays.fill(array[fromSegment], fromDispl, toDispl, value);
/*      */       return;
/*      */     } 
/* 5158 */     if (toDispl != 0) Arrays.fill(array[toSegment], 0, toDispl, value); 
/* 5159 */     for (; --toSegment > fromSegment; Arrays.fill(array[toSegment], value));
/* 5160 */     Arrays.fill(array[fromSegment], fromDispl, 134217728, value);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static boolean equals(char[][] a1, char[][] a2) {
/* 5175 */     if (length(a1) != length(a2)) return false; 
/* 5176 */     int i = a1.length;
/*      */     
/* 5178 */     while (i-- != 0) {
/* 5179 */       char[] t = a1[i];
/* 5180 */       char[] u = a2[i];
/* 5181 */       int j = t.length;
/* 5182 */       while (j-- != 0) { if (t[j] != u[j]) return false;  }
/*      */     
/* 5184 */     }  return true;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static String toString(char[][] a) {
/* 5194 */     if (a == null) return "null"; 
/* 5195 */     long last = length(a) - 1L;
/* 5196 */     if (last == -1L) return "[]"; 
/* 5197 */     StringBuilder b = new StringBuilder();
/* 5198 */     b.append('['); long i;
/* 5199 */     for (i = 0L;; i++) {
/* 5200 */       b.append(String.valueOf(get(a, i)));
/* 5201 */       if (i == last) return b.append(']').toString(); 
/* 5202 */       b.append(", ");
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
/*      */   public static void ensureFromTo(char[][] a, long from, long to) {
/* 5221 */     ensureFromTo(length(a), from, to);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static void ensureOffsetLength(char[][] a, long offset, long length) {
/* 5238 */     ensureOffsetLength(length(a), offset, length);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static void ensureSameLength(char[][] a, char[][] b) {
/* 5249 */     if (length(a) != length(b)) throw new IllegalArgumentException("Array size mismatch: " + length(a) + " != " + length(b));
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
/*      */   public static char[][] shuffle(char[][] a, long from, long to, Random random) {
/* 5262 */     for (long i = to - from; i-- != 0L; ) {
/* 5263 */       long p = (random.nextLong() & Long.MAX_VALUE) % (i + 1L);
/* 5264 */       char t = get(a, from + i);
/* 5265 */       set(a, from + i, get(a, from + p));
/* 5266 */       set(a, from + p, t);
/*      */     } 
/* 5268 */     return a;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static char[][] shuffle(char[][] a, Random random) {
/* 5279 */     for (long i = length(a); i-- != 0L; ) {
/* 5280 */       long p = (random.nextLong() & Long.MAX_VALUE) % (i + 1L);
/* 5281 */       char t = get(a, i);
/* 5282 */       set(a, i, get(a, p));
/* 5283 */       set(a, p, t);
/*      */     } 
/* 5285 */     return a;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static float get(float[][] array, long index) {
/* 5336 */     return array[segment(index)][displacement(index)];
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static void set(float[][] array, long index, float value) {
/* 5347 */     array[segment(index)][displacement(index)] = value;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static void swap(float[][] array, long first, long second) {
/* 5358 */     float t = array[segment(first)][displacement(first)];
/* 5359 */     array[segment(first)][displacement(first)] = array[segment(second)][displacement(second)];
/* 5360 */     array[segment(second)][displacement(second)] = t;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static float[][] reverse(float[][] a) {
/* 5370 */     long length = length(a);
/* 5371 */     for (long i = length / 2L; i-- != 0L; swap(a, i, length - i - 1L));
/* 5372 */     return a;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static void add(float[][] array, long index, float incr) {
/* 5383 */     array[segment(index)][displacement(index)] = array[segment(index)][displacement(index)] + incr;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static void mul(float[][] array, long index, float factor) {
/* 5394 */     array[segment(index)][displacement(index)] = array[segment(index)][displacement(index)] * factor;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static void incr(float[][] array, long index) {
/* 5404 */     array[segment(index)][displacement(index)] = array[segment(index)][displacement(index)] + 1.0F;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static void decr(float[][] array, long index) {
/* 5414 */     array[segment(index)][displacement(index)] = array[segment(index)][displacement(index)] - 1.0F;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static void assertBigArray(float[][] array) {
/* 5426 */     int l = array.length;
/* 5427 */     if (l == 0)
/* 5428 */       return;  for (int i = 0; i < l - 1; ) { if ((array[i]).length != 134217728) throw new IllegalStateException("All segments except for the last one must be of length 2^" + Integer.toString(27));  i++; }
/* 5429 */      if ((array[l - 1]).length > 134217728) throw new IllegalStateException("The last segment must be of length at most 2^" + Integer.toString(27)); 
/* 5430 */     if ((array[l - 1]).length == 0 && l == 1) throw new IllegalStateException("The last segment must be of nonzero length");
/*      */   
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static long length(float[][] array) {
/* 5440 */     int length = array.length;
/* 5441 */     return (length == 0) ? 0L : (start(length - 1) + (array[length - 1]).length);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static void copy(float[][] srcArray, long srcPos, float[][] destArray, long destPos, long length) {
/* 5456 */     if (destPos <= srcPos) {
/* 5457 */       int srcSegment = segment(srcPos);
/* 5458 */       int destSegment = segment(destPos);
/* 5459 */       int srcDispl = displacement(srcPos);
/* 5460 */       int destDispl = displacement(destPos);
/* 5461 */       while (length > 0L) {
/* 5462 */         int l = (int)Math.min(length, Math.min((srcArray[srcSegment]).length - srcDispl, (destArray[destSegment]).length - destDispl));
/* 5463 */         if (l == 0) throw new ArrayIndexOutOfBoundsException(); 
/* 5464 */         System.arraycopy(srcArray[srcSegment], srcDispl, destArray[destSegment], destDispl, l);
/* 5465 */         if ((srcDispl += l) == 134217728) {
/* 5466 */           srcDispl = 0;
/* 5467 */           srcSegment++;
/*      */         } 
/* 5469 */         if ((destDispl += l) == 134217728) {
/* 5470 */           destDispl = 0;
/* 5471 */           destSegment++;
/*      */         } 
/* 5473 */         length -= l;
/*      */       } 
/*      */     } else {
/* 5476 */       int srcSegment = segment(srcPos + length);
/* 5477 */       int destSegment = segment(destPos + length);
/* 5478 */       int srcDispl = displacement(srcPos + length);
/* 5479 */       int destDispl = displacement(destPos + length);
/* 5480 */       while (length > 0L) {
/* 5481 */         if (srcDispl == 0) {
/* 5482 */           srcDispl = 134217728;
/* 5483 */           srcSegment--;
/*      */         } 
/* 5485 */         if (destDispl == 0) {
/* 5486 */           destDispl = 134217728;
/* 5487 */           destSegment--;
/*      */         } 
/* 5489 */         int l = (int)Math.min(length, Math.min(srcDispl, destDispl));
/* 5490 */         if (l == 0) throw new ArrayIndexOutOfBoundsException(); 
/* 5491 */         System.arraycopy(srcArray[srcSegment], srcDispl - l, destArray[destSegment], destDispl - l, l);
/* 5492 */         srcDispl -= l;
/* 5493 */         destDispl -= l;
/* 5494 */         length -= l;
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
/*      */   public static void copyFromBig(float[][] srcArray, long srcPos, float[] destArray, int destPos, int length) {
/* 5510 */     int srcSegment = segment(srcPos);
/* 5511 */     int srcDispl = displacement(srcPos);
/* 5512 */     while (length > 0) {
/* 5513 */       int l = Math.min((srcArray[srcSegment]).length - srcDispl, length);
/* 5514 */       if (l == 0) throw new ArrayIndexOutOfBoundsException(); 
/* 5515 */       System.arraycopy(srcArray[srcSegment], srcDispl, destArray, destPos, l);
/* 5516 */       if ((srcDispl += l) == 134217728) {
/* 5517 */         srcDispl = 0;
/* 5518 */         srcSegment++;
/*      */       } 
/* 5520 */       destPos += l;
/* 5521 */       length -= l;
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
/*      */   public static void copyToBig(float[] srcArray, int srcPos, float[][] destArray, long destPos, long length) {
/* 5536 */     int destSegment = segment(destPos);
/* 5537 */     int destDispl = displacement(destPos);
/* 5538 */     while (length > 0L) {
/* 5539 */       int l = (int)Math.min(((destArray[destSegment]).length - destDispl), length);
/* 5540 */       if (l == 0) throw new ArrayIndexOutOfBoundsException(); 
/* 5541 */       System.arraycopy(srcArray, srcPos, destArray[destSegment], destDispl, l);
/* 5542 */       if ((destDispl += l) == 134217728) {
/* 5543 */         destDispl = 0;
/* 5544 */         destSegment++;
/*      */       } 
/* 5546 */       srcPos += l;
/* 5547 */       length -= l;
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
/*      */   public static float[][] wrap(float[] array) {
/* 5561 */     if (array.length == 0) return FloatBigArrays.EMPTY_BIG_ARRAY; 
/* 5562 */     if (array.length <= 134217728) return new float[][] { array }; 
/* 5563 */     float[][] bigArray = FloatBigArrays.newBigArray(array.length);
/* 5564 */     for (int i = 0; i < bigArray.length; ) { System.arraycopy(array, (int)start(i), bigArray[i], 0, (bigArray[i]).length); i++; }
/* 5565 */      return bigArray;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static float[][] ensureCapacity(float[][] array, long length) {
/* 5586 */     return ensureCapacity(array, length, length(array));
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static float[][] forceCapacity(float[][] array, long length, long preserve) {
/* 5605 */     ensureLength(length);
/* 5606 */     int valid = array.length - ((array.length == 0 || (array.length > 0 && (array[array.length - 1]).length == 134217728)) ? 0 : 1);
/* 5607 */     int baseLength = (int)(length + 134217727L >>> 27L);
/* 5608 */     float[][] base = Arrays.<float[]>copyOf(array, baseLength);
/* 5609 */     int residual = (int)(length & 0x7FFFFFFL);
/* 5610 */     if (residual != 0)
/* 5611 */     { for (int i = valid; i < baseLength - 1; ) { base[i] = new float[134217728]; i++; }
/* 5612 */        base[baseLength - 1] = new float[residual]; }
/* 5613 */     else { for (int i = valid; i < baseLength; ) { base[i] = new float[134217728]; i++; }  }
/* 5614 */      if (preserve - valid * 134217728L > 0L) copy(array, valid * 134217728L, base, valid * 134217728L, preserve - valid * 134217728L); 
/* 5615 */     return base;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static float[][] ensureCapacity(float[][] array, long length, long preserve) {
/* 5635 */     return (length > length(array)) ? forceCapacity(array, length, preserve) : array;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static float[][] grow(float[][] array, long length) {
/* 5657 */     long oldLength = length(array);
/* 5658 */     return (length > oldLength) ? grow(array, length, oldLength) : array;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static float[][] grow(float[][] array, long length, long preserve) {
/* 5683 */     long oldLength = length(array);
/* 5684 */     return (length > oldLength) ? ensureCapacity(array, Math.max(oldLength + (oldLength >> 1L), length), preserve) : array;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static float[][] trim(float[][] array, long length) {
/* 5702 */     ensureLength(length);
/* 5703 */     long oldLength = length(array);
/* 5704 */     if (length >= oldLength) return array; 
/* 5705 */     int baseLength = (int)(length + 134217727L >>> 27L);
/* 5706 */     float[][] base = Arrays.<float[]>copyOf(array, baseLength);
/* 5707 */     int residual = (int)(length & 0x7FFFFFFL);
/* 5708 */     if (residual != 0) base[baseLength - 1] = FloatArrays.trim(base[baseLength - 1], residual); 
/* 5709 */     return base;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static float[][] setLength(float[][] array, long length) {
/* 5729 */     long oldLength = length(array);
/* 5730 */     if (length == oldLength) return array; 
/* 5731 */     if (length < oldLength) return trim(array, length); 
/* 5732 */     return ensureCapacity(array, length);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static float[][] copy(float[][] array, long offset, long length) {
/* 5745 */     ensureOffsetLength(array, offset, length);
/* 5746 */     float[][] a = FloatBigArrays.newBigArray(length);
/* 5747 */     copy(array, offset, a, 0L, length);
/* 5748 */     return a;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static float[][] copy(float[][] array) {
/* 5758 */     float[][] base = (float[][])array.clone();
/* 5759 */     for (int i = base.length; i-- != 0; base[i] = (float[])array[i].clone());
/* 5760 */     return base;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static void fill(float[][] array, float value) {
/* 5774 */     for (int i = array.length; i-- != 0; Arrays.fill(array[i], value));
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static void fill(float[][] array, long from, long to, float value) {
/* 5790 */     long length = length(array);
/* 5791 */     ensureFromTo(length, from, to);
/* 5792 */     if (length == 0L)
/* 5793 */       return;  int fromSegment = segment(from);
/* 5794 */     int toSegment = segment(to);
/* 5795 */     int fromDispl = displacement(from);
/* 5796 */     int toDispl = displacement(to);
/* 5797 */     if (fromSegment == toSegment) {
/* 5798 */       Arrays.fill(array[fromSegment], fromDispl, toDispl, value);
/*      */       return;
/*      */     } 
/* 5801 */     if (toDispl != 0) Arrays.fill(array[toSegment], 0, toDispl, value); 
/* 5802 */     for (; --toSegment > fromSegment; Arrays.fill(array[toSegment], value));
/* 5803 */     Arrays.fill(array[fromSegment], fromDispl, 134217728, value);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static boolean equals(float[][] a1, float[][] a2) {
/* 5818 */     if (length(a1) != length(a2)) return false; 
/* 5819 */     int i = a1.length;
/*      */     
/* 5821 */     while (i-- != 0) {
/* 5822 */       float[] t = a1[i];
/* 5823 */       float[] u = a2[i];
/* 5824 */       int j = t.length;
/* 5825 */       while (j-- != 0) { if (Float.floatToIntBits(t[j]) != Float.floatToIntBits(u[j])) return false;  }
/*      */     
/* 5827 */     }  return true;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static String toString(float[][] a) {
/* 5837 */     if (a == null) return "null"; 
/* 5838 */     long last = length(a) - 1L;
/* 5839 */     if (last == -1L) return "[]"; 
/* 5840 */     StringBuilder b = new StringBuilder();
/* 5841 */     b.append('['); long i;
/* 5842 */     for (i = 0L;; i++) {
/* 5843 */       b.append(String.valueOf(get(a, i)));
/* 5844 */       if (i == last) return b.append(']').toString(); 
/* 5845 */       b.append(", ");
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
/*      */   public static void ensureFromTo(float[][] a, long from, long to) {
/* 5864 */     ensureFromTo(length(a), from, to);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static void ensureOffsetLength(float[][] a, long offset, long length) {
/* 5881 */     ensureOffsetLength(length(a), offset, length);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static void ensureSameLength(float[][] a, float[][] b) {
/* 5892 */     if (length(a) != length(b)) throw new IllegalArgumentException("Array size mismatch: " + length(a) + " != " + length(b));
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
/*      */   public static float[][] shuffle(float[][] a, long from, long to, Random random) {
/* 5905 */     for (long i = to - from; i-- != 0L; ) {
/* 5906 */       long p = (random.nextLong() & Long.MAX_VALUE) % (i + 1L);
/* 5907 */       float t = get(a, from + i);
/* 5908 */       set(a, from + i, get(a, from + p));
/* 5909 */       set(a, from + p, t);
/*      */     } 
/* 5911 */     return a;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static float[][] shuffle(float[][] a, Random random) {
/* 5922 */     for (long i = length(a); i-- != 0L; ) {
/* 5923 */       long p = (random.nextLong() & Long.MAX_VALUE) % (i + 1L);
/* 5924 */       float t = get(a, i);
/* 5925 */       set(a, i, get(a, p));
/* 5926 */       set(a, p, t);
/*      */     } 
/* 5928 */     return a;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static <K> K get(K[][] array, long index) {
/* 5978 */     return array[segment(index)][displacement(index)];
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static <K> void set(K[][] array, long index, K value) {
/* 5989 */     array[segment(index)][displacement(index)] = value;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static <K> void swap(K[][] array, long first, long second) {
/* 6000 */     K t = array[segment(first)][displacement(first)];
/* 6001 */     array[segment(first)][displacement(first)] = array[segment(second)][displacement(second)];
/* 6002 */     array[segment(second)][displacement(second)] = t;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static <K> K[][] reverse(K[][] a) {
/* 6012 */     long length = length(a);
/* 6013 */     for (long i = length / 2L; i-- != 0L; swap(a, i, length - i - 1L));
/* 6014 */     return a;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static <K> void assertBigArray(K[][] array) {
/* 6026 */     int l = array.length;
/* 6027 */     if (l == 0)
/* 6028 */       return;  for (int i = 0; i < l - 1; ) { if ((array[i]).length != 134217728) throw new IllegalStateException("All segments except for the last one must be of length 2^" + Integer.toString(27));  i++; }
/* 6029 */      if ((array[l - 1]).length > 134217728) throw new IllegalStateException("The last segment must be of length at most 2^" + Integer.toString(27)); 
/* 6030 */     if ((array[l - 1]).length == 0 && l == 1) throw new IllegalStateException("The last segment must be of nonzero length");
/*      */   
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static <K> long length(K[][] array) {
/* 6040 */     int length = array.length;
/* 6041 */     return (length == 0) ? 0L : (start(length - 1) + (array[length - 1]).length);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static <K> void copy(K[][] srcArray, long srcPos, K[][] destArray, long destPos, long length) {
/* 6056 */     if (destPos <= srcPos) {
/* 6057 */       int srcSegment = segment(srcPos);
/* 6058 */       int destSegment = segment(destPos);
/* 6059 */       int srcDispl = displacement(srcPos);
/* 6060 */       int destDispl = displacement(destPos);
/* 6061 */       while (length > 0L) {
/* 6062 */         int l = (int)Math.min(length, Math.min((srcArray[srcSegment]).length - srcDispl, (destArray[destSegment]).length - destDispl));
/* 6063 */         if (l == 0) throw new ArrayIndexOutOfBoundsException(); 
/* 6064 */         System.arraycopy(srcArray[srcSegment], srcDispl, destArray[destSegment], destDispl, l);
/* 6065 */         if ((srcDispl += l) == 134217728) {
/* 6066 */           srcDispl = 0;
/* 6067 */           srcSegment++;
/*      */         } 
/* 6069 */         if ((destDispl += l) == 134217728) {
/* 6070 */           destDispl = 0;
/* 6071 */           destSegment++;
/*      */         } 
/* 6073 */         length -= l;
/*      */       } 
/*      */     } else {
/* 6076 */       int srcSegment = segment(srcPos + length);
/* 6077 */       int destSegment = segment(destPos + length);
/* 6078 */       int srcDispl = displacement(srcPos + length);
/* 6079 */       int destDispl = displacement(destPos + length);
/* 6080 */       while (length > 0L) {
/* 6081 */         if (srcDispl == 0) {
/* 6082 */           srcDispl = 134217728;
/* 6083 */           srcSegment--;
/*      */         } 
/* 6085 */         if (destDispl == 0) {
/* 6086 */           destDispl = 134217728;
/* 6087 */           destSegment--;
/*      */         } 
/* 6089 */         int l = (int)Math.min(length, Math.min(srcDispl, destDispl));
/* 6090 */         if (l == 0) throw new ArrayIndexOutOfBoundsException(); 
/* 6091 */         System.arraycopy(srcArray[srcSegment], srcDispl - l, destArray[destSegment], destDispl - l, l);
/* 6092 */         srcDispl -= l;
/* 6093 */         destDispl -= l;
/* 6094 */         length -= l;
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
/*      */   public static <K> void copyFromBig(K[][] srcArray, long srcPos, K[] destArray, int destPos, int length) {
/* 6110 */     int srcSegment = segment(srcPos);
/* 6111 */     int srcDispl = displacement(srcPos);
/* 6112 */     while (length > 0) {
/* 6113 */       int l = Math.min((srcArray[srcSegment]).length - srcDispl, length);
/* 6114 */       if (l == 0) throw new ArrayIndexOutOfBoundsException(); 
/* 6115 */       System.arraycopy(srcArray[srcSegment], srcDispl, destArray, destPos, l);
/* 6116 */       if ((srcDispl += l) == 134217728) {
/* 6117 */         srcDispl = 0;
/* 6118 */         srcSegment++;
/*      */       } 
/* 6120 */       destPos += l;
/* 6121 */       length -= l;
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
/*      */   public static <K> void copyToBig(K[] srcArray, int srcPos, K[][] destArray, long destPos, long length) {
/* 6136 */     int destSegment = segment(destPos);
/* 6137 */     int destDispl = displacement(destPos);
/* 6138 */     while (length > 0L) {
/* 6139 */       int l = (int)Math.min(((destArray[destSegment]).length - destDispl), length);
/* 6140 */       if (l == 0) throw new ArrayIndexOutOfBoundsException(); 
/* 6141 */       System.arraycopy(srcArray, srcPos, destArray[destSegment], destDispl, l);
/* 6142 */       if ((destDispl += l) == 134217728) {
/* 6143 */         destDispl = 0;
/* 6144 */         destSegment++;
/*      */       } 
/* 6146 */       srcPos += l;
/* 6147 */       length -= l;
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
/*      */   public static <K> K[][] wrap(K[] array) {
/* 6162 */     if (array.length == 0 && array.getClass() == Object[].class) return (K[][])ObjectBigArrays.EMPTY_BIG_ARRAY; 
/* 6163 */     if (array.length <= 134217728) {
/* 6164 */       K[][] arrayOfK = (K[][])Array.newInstance(array.getClass(), 1);
/* 6165 */       arrayOfK[0] = array;
/* 6166 */       return arrayOfK;
/*      */     } 
/* 6168 */     K[][] bigArray = (K[][])ObjectBigArrays.newBigArray(array.getClass(), array.length);
/* 6169 */     for (int i = 0; i < bigArray.length; ) { System.arraycopy(array, (int)start(i), bigArray[i], 0, (bigArray[i]).length); i++; }
/* 6170 */      return bigArray;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static <K> K[][] ensureCapacity(K[][] array, long length) {
/* 6191 */     return ensureCapacity(array, length, length(array));
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static <K> K[][] forceCapacity(K[][] array, long length, long preserve) {
/* 6215 */     ensureLength(length);
/* 6216 */     int valid = array.length - ((array.length == 0 || (array.length > 0 && (array[array.length - 1]).length == 134217728)) ? 0 : 1);
/* 6217 */     int baseLength = (int)(length + 134217727L >>> 27L);
/* 6218 */     K[][] base = (K[][])Arrays.<Object[]>copyOf((Object[][])array, baseLength);
/* 6219 */     Class<?> componentType = array.getClass().getComponentType();
/* 6220 */     int residual = (int)(length & 0x7FFFFFFL);
/* 6221 */     if (residual != 0)
/* 6222 */     { for (int i = valid; i < baseLength - 1; ) { base[i] = (K[])Array.newInstance(componentType.getComponentType(), 134217728); i++; }
/* 6223 */        base[baseLength - 1] = (K[])Array.newInstance(componentType.getComponentType(), residual); }
/* 6224 */     else { for (int i = valid; i < baseLength; ) { base[i] = (K[])Array.newInstance(componentType.getComponentType(), 134217728); i++; }  }
/* 6225 */      if (preserve - valid * 134217728L > 0L) copy(array, valid * 134217728L, base, valid * 134217728L, preserve - valid * 134217728L); 
/* 6226 */     return base;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static <K> K[][] ensureCapacity(K[][] array, long length, long preserve) {
/* 6250 */     return (length > length(array)) ? forceCapacity(array, length, preserve) : array;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static <K> K[][] grow(K[][] array, long length) {
/* 6272 */     long oldLength = length(array);
/* 6273 */     return (length > oldLength) ? grow(array, length, oldLength) : array;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static <K> K[][] grow(K[][] array, long length, long preserve) {
/* 6298 */     long oldLength = length(array);
/* 6299 */     return (length > oldLength) ? ensureCapacity(array, Math.max(oldLength + (oldLength >> 1L), length), preserve) : array;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static <K> K[][] trim(K[][] array, long length) {
/* 6317 */     ensureLength(length);
/* 6318 */     long oldLength = length(array);
/* 6319 */     if (length >= oldLength) return array; 
/* 6320 */     int baseLength = (int)(length + 134217727L >>> 27L);
/* 6321 */     K[][] base = (K[][])Arrays.<Object[]>copyOf((Object[][])array, baseLength);
/* 6322 */     int residual = (int)(length & 0x7FFFFFFL);
/* 6323 */     if (residual != 0) base[baseLength - 1] = (K[])ObjectArrays.trim((Object[])base[baseLength - 1], residual); 
/* 6324 */     return base;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static <K> K[][] setLength(K[][] array, long length) {
/* 6344 */     long oldLength = length(array);
/* 6345 */     if (length == oldLength) return array; 
/* 6346 */     if (length < oldLength) return trim(array, length); 
/* 6347 */     return ensureCapacity(array, length);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static <K> K[][] copy(K[][] array, long offset, long length) {
/* 6360 */     ensureOffsetLength(array, offset, length);
/* 6361 */     K[][] a = (K[][])ObjectBigArrays.newBigArray((Object[][])array, length);
/* 6362 */     copy(array, offset, a, 0L, length);
/* 6363 */     return a;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static <K> K[][] copy(K[][] array) {
/* 6373 */     K[][] base = (K[][])array.clone();
/* 6374 */     for (int i = base.length; i-- != 0; base[i] = (K[])array[i].clone());
/* 6375 */     return base;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static <K> void fill(K[][] array, K value) {
/* 6389 */     for (int i = array.length; i-- != 0; Arrays.fill((Object[])array[i], value));
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static <K> void fill(K[][] array, long from, long to, K value) {
/* 6405 */     long length = length(array);
/* 6406 */     ensureFromTo(length, from, to);
/* 6407 */     if (length == 0L)
/* 6408 */       return;  int fromSegment = segment(from);
/* 6409 */     int toSegment = segment(to);
/* 6410 */     int fromDispl = displacement(from);
/* 6411 */     int toDispl = displacement(to);
/* 6412 */     if (fromSegment == toSegment) {
/* 6413 */       Arrays.fill((Object[])array[fromSegment], fromDispl, toDispl, value);
/*      */       return;
/*      */     } 
/* 6416 */     if (toDispl != 0) Arrays.fill((Object[])array[toSegment], 0, toDispl, value); 
/* 6417 */     for (; --toSegment > fromSegment; Arrays.fill((Object[])array[toSegment], value));
/* 6418 */     Arrays.fill((Object[])array[fromSegment], fromDispl, 134217728, value);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static <K> boolean equals(K[][] a1, K[][] a2) {
/* 6433 */     if (length(a1) != length(a2)) return false; 
/* 6434 */     int i = a1.length;
/*      */     
/* 6436 */     while (i-- != 0) {
/* 6437 */       K[] t = a1[i];
/* 6438 */       K[] u = a2[i];
/* 6439 */       int j = t.length;
/* 6440 */       while (j-- != 0) { if (!Objects.equals(t[j], u[j])) return false;  }
/*      */     
/* 6442 */     }  return true;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static <K> String toString(K[][] a) {
/* 6452 */     if (a == null) return "null"; 
/* 6453 */     long last = length(a) - 1L;
/* 6454 */     if (last == -1L) return "[]"; 
/* 6455 */     StringBuilder b = new StringBuilder();
/* 6456 */     b.append('['); long i;
/* 6457 */     for (i = 0L;; i++) {
/* 6458 */       b.append(String.valueOf(get(a, i)));
/* 6459 */       if (i == last) return b.append(']').toString(); 
/* 6460 */       b.append(", ");
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
/*      */   public static <K> void ensureFromTo(K[][] a, long from, long to) {
/* 6479 */     ensureFromTo(length(a), from, to);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static <K> void ensureOffsetLength(K[][] a, long offset, long length) {
/* 6496 */     ensureOffsetLength(length(a), offset, length);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static <K> void ensureSameLength(K[][] a, K[][] b) {
/* 6507 */     if (length(a) != length(b)) throw new IllegalArgumentException("Array size mismatch: " + length(a) + " != " + length(b));
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
/*      */   public static <K> K[][] shuffle(K[][] a, long from, long to, Random random) {
/* 6520 */     for (long i = to - from; i-- != 0L; ) {
/* 6521 */       long p = (random.nextLong() & Long.MAX_VALUE) % (i + 1L);
/* 6522 */       K t = get(a, from + i);
/* 6523 */       set(a, from + i, get(a, from + p));
/* 6524 */       set(a, from + p, t);
/*      */     } 
/* 6526 */     return a;
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
/* 6537 */     for (long i = length(a); i-- != 0L; ) {
/* 6538 */       long p = (random.nextLong() & Long.MAX_VALUE) % (i + 1L);
/* 6539 */       K t = get(a, i);
/* 6540 */       set(a, i, get(a, p));
/* 6541 */       set(a, p, t);
/*      */     } 
/* 6543 */     return a;
/*      */   }
/*      */   
/*      */   public static void main(String[] arg) {
/*      */     // Byte code:
/*      */     //   0: lconst_1
/*      */     //   1: aload_0
/*      */     //   2: iconst_0
/*      */     //   3: aaload
/*      */     //   4: invokestatic parseInt : (Ljava/lang/String;)I
/*      */     //   7: lshl
/*      */     //   8: invokestatic newBigArray : (J)[[I
/*      */     //   11: astore_1
/*      */     //   12: bipush #10
/*      */     //   14: istore #10
/*      */     //   16: iload #10
/*      */     //   18: iinc #10, -1
/*      */     //   21: ifeq -> 378
/*      */     //   24: invokestatic currentTimeMillis : ()J
/*      */     //   27: lneg
/*      */     //   28: lstore #8
/*      */     //   30: lconst_0
/*      */     //   31: lstore_2
/*      */     //   32: aload_1
/*      */     //   33: invokestatic length : ([[I)J
/*      */     //   36: lstore #11
/*      */     //   38: lload #11
/*      */     //   40: dup2
/*      */     //   41: lconst_1
/*      */     //   42: lsub
/*      */     //   43: lstore #11
/*      */     //   45: lconst_0
/*      */     //   46: lcmp
/*      */     //   47: ifeq -> 66
/*      */     //   50: lload_2
/*      */     //   51: lload #11
/*      */     //   53: aload_1
/*      */     //   54: lload #11
/*      */     //   56: invokestatic get : ([[IJ)I
/*      */     //   59: i2l
/*      */     //   60: lxor
/*      */     //   61: lxor
/*      */     //   62: lstore_2
/*      */     //   63: goto -> 38
/*      */     //   66: lload_2
/*      */     //   67: lconst_0
/*      */     //   68: lcmp
/*      */     //   69: ifne -> 78
/*      */     //   72: getstatic java/lang/System.err : Ljava/io/PrintStream;
/*      */     //   75: invokevirtual println : ()V
/*      */     //   78: getstatic java/lang/System.out : Ljava/io/PrintStream;
/*      */     //   81: new java/lang/StringBuilder
/*      */     //   84: dup
/*      */     //   85: invokespecial <init> : ()V
/*      */     //   88: ldc_w 'Single loop: '
/*      */     //   91: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
/*      */     //   94: lload #8
/*      */     //   96: invokestatic currentTimeMillis : ()J
/*      */     //   99: ladd
/*      */     //   100: invokevirtual append : (J)Ljava/lang/StringBuilder;
/*      */     //   103: ldc_w 'ms'
/*      */     //   106: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
/*      */     //   109: invokevirtual toString : ()Ljava/lang/String;
/*      */     //   112: invokevirtual println : (Ljava/lang/String;)V
/*      */     //   115: invokestatic currentTimeMillis : ()J
/*      */     //   118: lneg
/*      */     //   119: lstore #8
/*      */     //   121: lconst_0
/*      */     //   122: lstore #4
/*      */     //   124: aload_1
/*      */     //   125: arraylength
/*      */     //   126: istore #11
/*      */     //   128: iload #11
/*      */     //   130: iinc #11, -1
/*      */     //   133: ifeq -> 180
/*      */     //   136: aload_1
/*      */     //   137: iload #11
/*      */     //   139: aaload
/*      */     //   140: astore #12
/*      */     //   142: aload #12
/*      */     //   144: arraylength
/*      */     //   145: istore #13
/*      */     //   147: iload #13
/*      */     //   149: iinc #13, -1
/*      */     //   152: ifeq -> 177
/*      */     //   155: lload #4
/*      */     //   157: aload #12
/*      */     //   159: iload #13
/*      */     //   161: iaload
/*      */     //   162: i2l
/*      */     //   163: iload #11
/*      */     //   165: iload #13
/*      */     //   167: invokestatic index : (II)J
/*      */     //   170: lxor
/*      */     //   171: lxor
/*      */     //   172: lstore #4
/*      */     //   174: goto -> 147
/*      */     //   177: goto -> 128
/*      */     //   180: lload #4
/*      */     //   182: lconst_0
/*      */     //   183: lcmp
/*      */     //   184: ifne -> 193
/*      */     //   187: getstatic java/lang/System.err : Ljava/io/PrintStream;
/*      */     //   190: invokevirtual println : ()V
/*      */     //   193: lload_2
/*      */     //   194: lload #4
/*      */     //   196: lcmp
/*      */     //   197: ifeq -> 208
/*      */     //   200: new java/lang/AssertionError
/*      */     //   203: dup
/*      */     //   204: invokespecial <init> : ()V
/*      */     //   207: athrow
/*      */     //   208: getstatic java/lang/System.out : Ljava/io/PrintStream;
/*      */     //   211: new java/lang/StringBuilder
/*      */     //   214: dup
/*      */     //   215: invokespecial <init> : ()V
/*      */     //   218: ldc_w 'Double loop: '
/*      */     //   221: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
/*      */     //   224: lload #8
/*      */     //   226: invokestatic currentTimeMillis : ()J
/*      */     //   229: ladd
/*      */     //   230: invokevirtual append : (J)Ljava/lang/StringBuilder;
/*      */     //   233: ldc_w 'ms'
/*      */     //   236: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
/*      */     //   239: invokevirtual toString : ()Ljava/lang/String;
/*      */     //   242: invokevirtual println : (Ljava/lang/String;)V
/*      */     //   245: lconst_0
/*      */     //   246: lstore #6
/*      */     //   248: aload_1
/*      */     //   249: invokestatic length : ([[I)J
/*      */     //   252: lstore #11
/*      */     //   254: aload_1
/*      */     //   255: arraylength
/*      */     //   256: istore #13
/*      */     //   258: iload #13
/*      */     //   260: iinc #13, -1
/*      */     //   263: ifeq -> 310
/*      */     //   266: aload_1
/*      */     //   267: iload #13
/*      */     //   269: aaload
/*      */     //   270: astore #14
/*      */     //   272: aload #14
/*      */     //   274: arraylength
/*      */     //   275: istore #15
/*      */     //   277: iload #15
/*      */     //   279: iinc #15, -1
/*      */     //   282: ifeq -> 307
/*      */     //   285: lload #4
/*      */     //   287: aload #14
/*      */     //   289: iload #15
/*      */     //   291: iaload
/*      */     //   292: i2l
/*      */     //   293: lload #11
/*      */     //   295: lconst_1
/*      */     //   296: lsub
/*      */     //   297: dup2
/*      */     //   298: lstore #11
/*      */     //   300: lxor
/*      */     //   301: lxor
/*      */     //   302: lstore #4
/*      */     //   304: goto -> 277
/*      */     //   307: goto -> 258
/*      */     //   310: lload #6
/*      */     //   312: lconst_0
/*      */     //   313: lcmp
/*      */     //   314: ifne -> 323
/*      */     //   317: getstatic java/lang/System.err : Ljava/io/PrintStream;
/*      */     //   320: invokevirtual println : ()V
/*      */     //   323: lload_2
/*      */     //   324: lload #6
/*      */     //   326: lcmp
/*      */     //   327: ifeq -> 338
/*      */     //   330: new java/lang/AssertionError
/*      */     //   333: dup
/*      */     //   334: invokespecial <init> : ()V
/*      */     //   337: athrow
/*      */     //   338: getstatic java/lang/System.out : Ljava/io/PrintStream;
/*      */     //   341: new java/lang/StringBuilder
/*      */     //   344: dup
/*      */     //   345: invokespecial <init> : ()V
/*      */     //   348: ldc_w 'Double loop (with additional index): '
/*      */     //   351: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
/*      */     //   354: lload #8
/*      */     //   356: invokestatic currentTimeMillis : ()J
/*      */     //   359: ladd
/*      */     //   360: invokevirtual append : (J)Ljava/lang/StringBuilder;
/*      */     //   363: ldc_w 'ms'
/*      */     //   366: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
/*      */     //   369: invokevirtual toString : ()Ljava/lang/String;
/*      */     //   372: invokevirtual println : (Ljava/lang/String;)V
/*      */     //   375: goto -> 16
/*      */     //   378: return
/*      */     // Line number table:
/*      */     //   Java source line number -> byte code offset
/*      */     //   #6547	-> 0
/*      */     //   #6549	-> 12
/*      */     //   #6550	-> 24
/*      */     //   #6551	-> 30
/*      */     //   #6552	-> 32
/*      */     //   #6553	-> 66
/*      */     //   #6554	-> 78
/*      */     //   #6555	-> 115
/*      */     //   #6556	-> 121
/*      */     //   #6557	-> 124
/*      */     //   #6558	-> 136
/*      */     //   #6559	-> 142
/*      */     //   #6560	-> 177
/*      */     //   #6561	-> 180
/*      */     //   #6562	-> 193
/*      */     //   #6563	-> 208
/*      */     //   #6564	-> 245
/*      */     //   #6565	-> 248
/*      */     //   #6566	-> 254
/*      */     //   #6567	-> 266
/*      */     //   #6568	-> 272
/*      */     //   #6569	-> 307
/*      */     //   #6570	-> 310
/*      */     //   #6571	-> 323
/*      */     //   #6572	-> 338
/*      */     //   #6573	-> 375
/*      */     //   #6574	-> 378
/*      */     // Local variable table:
/*      */     //   start	length	slot	name	descriptor
/*      */     //   38	28	11	i	J
/*      */     //   147	30	13	d	I
/*      */     //   142	35	12	t	[I
/*      */     //   128	52	11	i	I
/*      */     //   277	30	15	d	I
/*      */     //   272	35	14	t	[I
/*      */     //   258	52	13	i	I
/*      */     //   254	121	11	j	J
/*      */     //   32	346	2	x	J
/*      */     //   124	254	4	y	J
/*      */     //   248	130	6	z	J
/*      */     //   30	348	8	start	J
/*      */     //   16	362	10	k	I
/*      */     //   0	379	0	arg	[Ljava/lang/String;
/*      */     //   12	367	1	a	[[I
/*      */   }
/*      */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\i\\unimi\dsi\fastutil\BigArrays.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */