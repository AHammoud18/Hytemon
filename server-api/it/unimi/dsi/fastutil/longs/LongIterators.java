/*      */ package it.unimi.dsi.fastutil.longs;
/*      */ 
/*      */ import it.unimi.dsi.fastutil.BigArrays;
/*      */ import it.unimi.dsi.fastutil.bytes.ByteIterator;
/*      */ import it.unimi.dsi.fastutil.chars.CharIterator;
/*      */ import it.unimi.dsi.fastutil.ints.IntIterator;
/*      */ import it.unimi.dsi.fastutil.shorts.ShortIterator;
/*      */ import java.io.Serializable;
/*      */ import java.util.Iterator;
/*      */ import java.util.ListIterator;
/*      */ import java.util.NoSuchElementException;
/*      */ import java.util.Objects;
/*      */ import java.util.PrimitiveIterator;
/*      */ import java.util.function.Consumer;
/*      */ import java.util.function.LongConsumer;
/*      */ import java.util.function.LongPredicate;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ public final class LongIterators
/*      */ {
/*      */   public static class EmptyIterator
/*      */     implements LongListIterator, Serializable, Cloneable
/*      */   {
/*      */     private static final long serialVersionUID = -7046029254386353129L;
/*      */     
/*      */     public boolean hasNext() {
/*   52 */       return false;
/*      */     }
/*      */ 
/*      */     
/*      */     public boolean hasPrevious() {
/*   57 */       return false;
/*      */     }
/*      */ 
/*      */     
/*      */     public long nextLong() {
/*   62 */       throw new NoSuchElementException();
/*      */     }
/*      */ 
/*      */     
/*      */     public long previousLong() {
/*   67 */       throw new NoSuchElementException();
/*      */     }
/*      */ 
/*      */     
/*      */     public int nextIndex() {
/*   72 */       return 0;
/*      */     }
/*      */ 
/*      */     
/*      */     public int previousIndex() {
/*   77 */       return -1;
/*      */     }
/*      */ 
/*      */     
/*      */     public int skip(int n) {
/*   82 */       return 0;
/*      */     }
/*      */ 
/*      */     
/*      */     public int back(int n) {
/*   87 */       return 0;
/*      */     }
/*      */ 
/*      */ 
/*      */     
/*      */     public void forEachRemaining(LongConsumer action) {}
/*      */ 
/*      */ 
/*      */     
/*      */     @Deprecated
/*      */     public void forEachRemaining(Consumer<? super Long> action) {}
/*      */ 
/*      */     
/*      */     public Object clone() {
/*  101 */       return LongIterators.EMPTY_ITERATOR;
/*      */     }
/*      */     
/*      */     private Object readResolve() {
/*  105 */       return LongIterators.EMPTY_ITERATOR;
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
/*  117 */   public static final EmptyIterator EMPTY_ITERATOR = new EmptyIterator();
/*      */   
/*      */   private static class SingletonIterator
/*      */     implements LongListIterator {
/*      */     private final long element;
/*      */     private byte curr;
/*      */     
/*      */     public SingletonIterator(long element) {
/*  125 */       this.element = element;
/*      */     }
/*      */ 
/*      */     
/*      */     public boolean hasNext() {
/*  130 */       return (this.curr == 0);
/*      */     }
/*      */ 
/*      */     
/*      */     public boolean hasPrevious() {
/*  135 */       return (this.curr == 1);
/*      */     }
/*      */ 
/*      */     
/*      */     public long nextLong() {
/*  140 */       if (!hasNext()) throw new NoSuchElementException(); 
/*  141 */       this.curr = 1;
/*  142 */       return this.element;
/*      */     }
/*      */ 
/*      */     
/*      */     public long previousLong() {
/*  147 */       if (!hasPrevious()) throw new NoSuchElementException(); 
/*  148 */       this.curr = 0;
/*  149 */       return this.element;
/*      */     }
/*      */ 
/*      */     
/*      */     public void forEachRemaining(LongConsumer action) {
/*  154 */       Objects.requireNonNull(action);
/*  155 */       if (this.curr == 0) {
/*  156 */         action.accept(this.element);
/*  157 */         this.curr = 1;
/*      */       } 
/*      */     }
/*      */ 
/*      */     
/*      */     public int nextIndex() {
/*  163 */       return this.curr;
/*      */     }
/*      */ 
/*      */     
/*      */     public int previousIndex() {
/*  168 */       return this.curr - 1;
/*      */     }
/*      */ 
/*      */     
/*      */     public int back(int n) {
/*  173 */       if (n < 0) throw new IllegalArgumentException("Argument must be nonnegative: " + n); 
/*  174 */       if (n == 0 || this.curr < 1) return 0; 
/*  175 */       this.curr = 1;
/*  176 */       return 1;
/*      */     }
/*      */ 
/*      */     
/*      */     public int skip(int n) {
/*  181 */       if (n < 0) throw new IllegalArgumentException("Argument must be nonnegative: " + n); 
/*  182 */       if (n == 0 || this.curr > 0) return 0; 
/*  183 */       this.curr = 0;
/*  184 */       return 1;
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static LongListIterator singleton(long element) {
/*  195 */     return new SingletonIterator(element);
/*      */   }
/*      */   
/*      */   private static class ArrayIterator implements LongListIterator {
/*      */     private final long[] array;
/*      */     private final int offset;
/*      */     private final int length;
/*      */     private int curr;
/*      */     
/*      */     public ArrayIterator(long[] array, int offset, int length) {
/*  205 */       this.array = array;
/*  206 */       this.offset = offset;
/*  207 */       this.length = length;
/*      */     }
/*      */ 
/*      */     
/*      */     public boolean hasNext() {
/*  212 */       return (this.curr < this.length);
/*      */     }
/*      */ 
/*      */     
/*      */     public boolean hasPrevious() {
/*  217 */       return (this.curr > 0);
/*      */     }
/*      */ 
/*      */     
/*      */     public long nextLong() {
/*  222 */       if (!hasNext()) throw new NoSuchElementException(); 
/*  223 */       return this.array[this.offset + this.curr++];
/*      */     }
/*      */ 
/*      */     
/*      */     public long previousLong() {
/*  228 */       if (!hasPrevious()) throw new NoSuchElementException(); 
/*  229 */       return this.array[this.offset + --this.curr];
/*      */     }
/*      */ 
/*      */     
/*      */     public void forEachRemaining(LongConsumer action) {
/*  234 */       Objects.requireNonNull(action);
/*  235 */       for (; this.curr < this.length; this.curr++) {
/*  236 */         action.accept(this.array[this.offset + this.curr]);
/*      */       }
/*      */     }
/*      */ 
/*      */     
/*      */     public int skip(int n) {
/*  242 */       if (n < 0) throw new IllegalArgumentException("Argument must be nonnegative: " + n); 
/*  243 */       if (n <= this.length - this.curr) {
/*  244 */         this.curr += n;
/*  245 */         return n;
/*      */       } 
/*  247 */       n = this.length - this.curr;
/*  248 */       this.curr = this.length;
/*  249 */       return n;
/*      */     }
/*      */ 
/*      */     
/*      */     public int back(int n) {
/*  254 */       if (n < 0) throw new IllegalArgumentException("Argument must be nonnegative: " + n); 
/*  255 */       if (n <= this.curr) {
/*  256 */         this.curr -= n;
/*  257 */         return n;
/*      */       } 
/*  259 */       n = this.curr;
/*  260 */       this.curr = 0;
/*  261 */       return n;
/*      */     }
/*      */ 
/*      */     
/*      */     public int nextIndex() {
/*  266 */       return this.curr;
/*      */     }
/*      */ 
/*      */     
/*      */     public int previousIndex() {
/*  271 */       return this.curr - 1;
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
/*      */   public static LongListIterator wrap(long[] array, int offset, int length) {
/*  290 */     LongArrays.ensureOffsetLength(array, offset, length);
/*  291 */     return new ArrayIterator(array, offset, length);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static LongListIterator wrap(long[] array) {
/*  305 */     return new ArrayIterator(array, 0, array.length);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static int unwrap(LongIterator i, long[] array, int offset, int max) {
/*  324 */     if (max < 0) throw new IllegalArgumentException("The maximum number of elements (" + max + ") is negative"); 
/*  325 */     if (offset < 0 || offset + max > array.length) throw new IllegalArgumentException(); 
/*  326 */     int j = max;
/*  327 */     for (; j-- != 0 && i.hasNext(); array[offset++] = i.nextLong());
/*  328 */     return max - j - 1;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static int unwrap(LongIterator i, long[] array) {
/*  344 */     return unwrap(i, array, 0, array.length);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static long[] unwrap(LongIterator i, int max) {
/*  360 */     if (max < 0) throw new IllegalArgumentException("The maximum number of elements (" + max + ") is negative"); 
/*  361 */     long[] array = new long[16];
/*  362 */     int j = 0;
/*  363 */     while (max-- != 0 && i.hasNext()) {
/*  364 */       if (j == array.length) array = LongArrays.grow(array, j + 1); 
/*  365 */       array[j++] = i.nextLong();
/*      */     } 
/*  367 */     return LongArrays.trim(array, j);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static long[] unwrap(LongIterator i) {
/*  381 */     return unwrap(i, 2147483647);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static long unwrap(LongIterator i, long[][] array, long offset, long max) {
/*      */     // Byte code:
/*      */     //   0: lload #4
/*      */     //   2: lconst_0
/*      */     //   3: lcmp
/*      */     //   4: ifge -> 40
/*      */     //   7: new java/lang/IllegalArgumentException
/*      */     //   10: dup
/*      */     //   11: new java/lang/StringBuilder
/*      */     //   14: dup
/*      */     //   15: invokespecial <init> : ()V
/*      */     //   18: ldc 'The maximum number of elements ('
/*      */     //   20: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
/*      */     //   23: lload #4
/*      */     //   25: invokevirtual append : (J)Ljava/lang/StringBuilder;
/*      */     //   28: ldc ') is negative'
/*      */     //   30: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
/*      */     //   33: invokevirtual toString : ()Ljava/lang/String;
/*      */     //   36: invokespecial <init> : (Ljava/lang/String;)V
/*      */     //   39: athrow
/*      */     //   40: lload_2
/*      */     //   41: lconst_0
/*      */     //   42: lcmp
/*      */     //   43: iflt -> 58
/*      */     //   46: lload_2
/*      */     //   47: lload #4
/*      */     //   49: ladd
/*      */     //   50: aload_1
/*      */     //   51: invokestatic length : ([[J)J
/*      */     //   54: lcmp
/*      */     //   55: ifle -> 66
/*      */     //   58: new java/lang/IllegalArgumentException
/*      */     //   61: dup
/*      */     //   62: invokespecial <init> : ()V
/*      */     //   65: athrow
/*      */     //   66: lload #4
/*      */     //   68: lstore #6
/*      */     //   70: lload #6
/*      */     //   72: dup2
/*      */     //   73: lconst_1
/*      */     //   74: lsub
/*      */     //   75: lstore #6
/*      */     //   77: lconst_0
/*      */     //   78: lcmp
/*      */     //   79: ifeq -> 109
/*      */     //   82: aload_0
/*      */     //   83: invokeinterface hasNext : ()Z
/*      */     //   88: ifeq -> 109
/*      */     //   91: aload_1
/*      */     //   92: lload_2
/*      */     //   93: dup2
/*      */     //   94: lconst_1
/*      */     //   95: ladd
/*      */     //   96: lstore_2
/*      */     //   97: aload_0
/*      */     //   98: invokeinterface nextLong : ()J
/*      */     //   103: invokestatic set : ([[JJJ)V
/*      */     //   106: goto -> 70
/*      */     //   109: lload #4
/*      */     //   111: lload #6
/*      */     //   113: lsub
/*      */     //   114: lconst_1
/*      */     //   115: lsub
/*      */     //   116: lreturn
/*      */     // Line number table:
/*      */     //   Java source line number -> byte code offset
/*      */     //   #400	-> 0
/*      */     //   #401	-> 40
/*      */     //   #402	-> 66
/*      */     //   #403	-> 70
/*      */     //   #404	-> 109
/*      */     // Local variable table:
/*      */     //   start	length	slot	name	descriptor
/*      */     //   0	117	0	i	Lit/unimi/dsi/fastutil/longs/LongIterator;
/*      */     //   0	117	1	array	[[J
/*      */     //   0	117	2	offset	J
/*      */     //   0	117	4	max	J
/*      */     //   70	47	6	j	J
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static long unwrap(LongIterator i, long[][] array) {
/*  420 */     return unwrap(i, array, 0L, BigArrays.length(array));
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static int unwrap(LongIterator i, LongCollection c, int max) {
/*  440 */     if (max < 0) throw new IllegalArgumentException("The maximum number of elements (" + max + ") is negative"); 
/*  441 */     int j = max;
/*  442 */     for (; j-- != 0 && i.hasNext(); c.add(i.nextLong()));
/*  443 */     return max - j - 1;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static long[][] unwrapBig(LongIterator i, long max) {
/*      */     // Byte code:
/*      */     //   0: lload_1
/*      */     //   1: lconst_0
/*      */     //   2: lcmp
/*      */     //   3: ifge -> 38
/*      */     //   6: new java/lang/IllegalArgumentException
/*      */     //   9: dup
/*      */     //   10: new java/lang/StringBuilder
/*      */     //   13: dup
/*      */     //   14: invokespecial <init> : ()V
/*      */     //   17: ldc 'The maximum number of elements ('
/*      */     //   19: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
/*      */     //   22: lload_1
/*      */     //   23: invokevirtual append : (J)Ljava/lang/StringBuilder;
/*      */     //   26: ldc ') is negative'
/*      */     //   28: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
/*      */     //   31: invokevirtual toString : ()Ljava/lang/String;
/*      */     //   34: invokespecial <init> : (Ljava/lang/String;)V
/*      */     //   37: athrow
/*      */     //   38: ldc2_w 16
/*      */     //   41: invokestatic newBigArray : (J)[[J
/*      */     //   44: astore_3
/*      */     //   45: lconst_0
/*      */     //   46: lstore #4
/*      */     //   48: lload_1
/*      */     //   49: dup2
/*      */     //   50: lconst_1
/*      */     //   51: lsub
/*      */     //   52: lstore_1
/*      */     //   53: lconst_0
/*      */     //   54: lcmp
/*      */     //   55: ifeq -> 106
/*      */     //   58: aload_0
/*      */     //   59: invokeinterface hasNext : ()Z
/*      */     //   64: ifeq -> 106
/*      */     //   67: lload #4
/*      */     //   69: aload_3
/*      */     //   70: invokestatic length : ([[J)J
/*      */     //   73: lcmp
/*      */     //   74: ifne -> 86
/*      */     //   77: aload_3
/*      */     //   78: lload #4
/*      */     //   80: lconst_1
/*      */     //   81: ladd
/*      */     //   82: invokestatic grow : ([[JJ)[[J
/*      */     //   85: astore_3
/*      */     //   86: aload_3
/*      */     //   87: lload #4
/*      */     //   89: dup2
/*      */     //   90: lconst_1
/*      */     //   91: ladd
/*      */     //   92: lstore #4
/*      */     //   94: aload_0
/*      */     //   95: invokeinterface nextLong : ()J
/*      */     //   100: invokestatic set : ([[JJJ)V
/*      */     //   103: goto -> 48
/*      */     //   106: aload_3
/*      */     //   107: lload #4
/*      */     //   109: invokestatic trim : ([[JJ)[[J
/*      */     //   112: areturn
/*      */     // Line number table:
/*      */     //   Java source line number -> byte code offset
/*      */     //   #459	-> 0
/*      */     //   #460	-> 38
/*      */     //   #461	-> 45
/*      */     //   #462	-> 48
/*      */     //   #463	-> 67
/*      */     //   #464	-> 86
/*      */     //   #466	-> 106
/*      */     // Local variable table:
/*      */     //   start	length	slot	name	descriptor
/*      */     //   0	113	0	i	Lit/unimi/dsi/fastutil/longs/LongIterator;
/*      */     //   0	113	1	max	J
/*      */     //   45	68	3	array	[[J
/*      */     //   48	65	4	j	J
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static long[][] unwrapBig(LongIterator i) {
/*  480 */     return unwrapBig(i, Long.MAX_VALUE);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static long unwrap(LongIterator i, LongCollection c) {
/*  498 */     long n = 0L;
/*  499 */     while (i.hasNext()) {
/*  500 */       c.add(i.nextLong());
/*  501 */       n++;
/*      */     } 
/*  503 */     return n;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static int pour(LongIterator i, LongCollection s, int max) {
/*  521 */     if (max < 0) throw new IllegalArgumentException("The maximum number of elements (" + max + ") is negative"); 
/*  522 */     int j = max;
/*  523 */     for (; j-- != 0 && i.hasNext(); s.add(i.nextLong()));
/*  524 */     return max - j - 1;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static int pour(LongIterator i, LongCollection s) {
/*  541 */     return pour(i, s, 2147483647);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static LongList pour(LongIterator i, int max) {
/*  558 */     LongArrayList l = new LongArrayList();
/*  559 */     pour(i, l, max);
/*  560 */     l.trim();
/*  561 */     return l;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static LongList pour(LongIterator i) {
/*  576 */     return pour(i, 2147483647);
/*      */   }
/*      */   
/*      */   private static class IteratorWrapper implements LongIterator {
/*      */     final Iterator<Long> i;
/*      */     
/*      */     public IteratorWrapper(Iterator<Long> i) {
/*  583 */       this.i = i;
/*      */     }
/*      */ 
/*      */     
/*      */     public boolean hasNext() {
/*  588 */       return this.i.hasNext();
/*      */     }
/*      */ 
/*      */     
/*      */     public void remove() {
/*  593 */       this.i.remove();
/*      */     }
/*      */ 
/*      */     
/*      */     public long nextLong() {
/*  598 */       return ((Long)this.i.next()).longValue();
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public void forEachRemaining(LongConsumer action) {
/*  605 */       this.i.forEachRemaining(action);
/*      */     }
/*      */ 
/*      */ 
/*      */     
/*      */     public void forEachRemaining(LongConsumer action) {
/*  611 */       Objects.requireNonNull(action);
/*  612 */       Objects.requireNonNull(action); this.i.forEachRemaining((action instanceof Consumer) ? (Consumer<? super Long>)action : action::accept);
/*      */     }
/*      */ 
/*      */     
/*      */     @Deprecated
/*      */     public void forEachRemaining(Consumer<? super Long> action) {
/*  618 */       this.i.forEachRemaining(action);
/*      */     }
/*      */   }
/*      */   
/*      */   private static class PrimitiveIteratorWrapper implements LongIterator {
/*      */     final PrimitiveIterator.OfLong i;
/*      */     
/*      */     public PrimitiveIteratorWrapper(PrimitiveIterator.OfLong i) {
/*  626 */       this.i = i;
/*      */     }
/*      */ 
/*      */     
/*      */     public boolean hasNext() {
/*  631 */       return this.i.hasNext();
/*      */     }
/*      */ 
/*      */     
/*      */     public void remove() {
/*  636 */       this.i.remove();
/*      */     }
/*      */ 
/*      */     
/*      */     public long nextLong() {
/*  641 */       return this.i.nextLong();
/*      */     }
/*      */ 
/*      */     
/*      */     public void forEachRemaining(LongConsumer action) {
/*  646 */       this.i.forEachRemaining(action);
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
/*      */   public static LongIterator asLongIterator(Iterator<Long> i) {
/*  667 */     if (i instanceof LongIterator) return (LongIterator)i; 
/*  668 */     if (i instanceof PrimitiveIterator.OfLong) return new PrimitiveIteratorWrapper((PrimitiveIterator.OfLong)i); 
/*  669 */     return new IteratorWrapper(i);
/*      */   }
/*      */   
/*      */   private static class ListIteratorWrapper implements LongListIterator {
/*      */     final ListIterator<Long> i;
/*      */     
/*      */     public ListIteratorWrapper(ListIterator<Long> i) {
/*  676 */       this.i = i;
/*      */     }
/*      */ 
/*      */     
/*      */     public boolean hasNext() {
/*  681 */       return this.i.hasNext();
/*      */     }
/*      */ 
/*      */     
/*      */     public boolean hasPrevious() {
/*  686 */       return this.i.hasPrevious();
/*      */     }
/*      */ 
/*      */     
/*      */     public int nextIndex() {
/*  691 */       return this.i.nextIndex();
/*      */     }
/*      */ 
/*      */     
/*      */     public int previousIndex() {
/*  696 */       return this.i.previousIndex();
/*      */     }
/*      */ 
/*      */     
/*      */     public void set(long k) {
/*  701 */       this.i.set(Long.valueOf(k));
/*      */     }
/*      */ 
/*      */     
/*      */     public void add(long k) {
/*  706 */       this.i.add(Long.valueOf(k));
/*      */     }
/*      */ 
/*      */     
/*      */     public void remove() {
/*  711 */       this.i.remove();
/*      */     }
/*      */ 
/*      */     
/*      */     public long nextLong() {
/*  716 */       return ((Long)this.i.next()).longValue();
/*      */     }
/*      */ 
/*      */     
/*      */     public long previousLong() {
/*  721 */       return ((Long)this.i.previous()).longValue();
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public void forEachRemaining(LongConsumer action) {
/*  728 */       this.i.forEachRemaining(action);
/*      */     }
/*      */ 
/*      */ 
/*      */     
/*      */     public void forEachRemaining(LongConsumer action) {
/*  734 */       Objects.requireNonNull(action);
/*  735 */       Objects.requireNonNull(action); this.i.forEachRemaining((action instanceof Consumer) ? (Consumer<? super Long>)action : action::accept);
/*      */     }
/*      */ 
/*      */     
/*      */     @Deprecated
/*      */     public void forEachRemaining(Consumer<? super Long> action) {
/*  741 */       this.i.forEachRemaining(action);
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
/*      */   public static LongListIterator asLongIterator(ListIterator<Long> i) {
/*  762 */     if (i instanceof LongListIterator) return (LongListIterator)i; 
/*  763 */     return new ListIteratorWrapper(i);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static boolean any(LongIterator iterator, LongPredicate predicate) {
/*  775 */     return (indexOf(iterator, predicate) != -1);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static boolean all(LongIterator iterator, LongPredicate predicate) {
/*  787 */     Objects.requireNonNull(predicate);
/*      */     while (true) {
/*  789 */       if (!iterator.hasNext()) return true; 
/*  790 */       if (!predicate.test(iterator.nextLong())) {
/*  791 */         return false;
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
/*      */   public static int indexOf(LongIterator iterator, LongPredicate predicate) {
/*  806 */     Objects.requireNonNull(predicate);
/*  807 */     for (int i = 0; iterator.hasNext(); i++) {
/*  808 */       if (predicate.test(iterator.nextLong())) return i; 
/*      */     } 
/*  810 */     return -1;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static abstract class AbstractIndexBasedIterator
/*      */     extends AbstractLongIterator
/*      */   {
/*      */     protected final int minPos;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     protected int pos;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     protected int lastReturned;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     protected AbstractIndexBasedIterator(int minPos, int initialPos) {
/*  860 */       this.minPos = minPos;
/*  861 */       this.pos = initialPos;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public boolean hasNext() {
/*  910 */       return (this.pos < getMaxPos());
/*      */     }
/*      */ 
/*      */     
/*      */     public long nextLong() {
/*  915 */       if (!hasNext()) throw new NoSuchElementException(); 
/*  916 */       return get(this.lastReturned = this.pos++);
/*      */     }
/*      */ 
/*      */     
/*      */     public void remove() {
/*  921 */       if (this.lastReturned == -1) throw new IllegalStateException(); 
/*  922 */       remove(this.lastReturned);
/*      */       
/*  924 */       if (this.lastReturned < this.pos) this.pos--; 
/*  925 */       this.lastReturned = -1;
/*      */     }
/*      */ 
/*      */     
/*      */     public void forEachRemaining(LongConsumer action) {
/*  930 */       while (this.pos < getMaxPos()) {
/*  931 */         action.accept(get(this.lastReturned = this.pos++));
/*      */       }
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public int skip(int n) {
/*  939 */       if (n < 0) throw new IllegalArgumentException("Argument must be nonnegative: " + n); 
/*  940 */       int max = getMaxPos();
/*  941 */       int remaining = max - this.pos;
/*  942 */       if (n < remaining) {
/*  943 */         this.pos += n;
/*      */       } else {
/*  945 */         n = remaining;
/*  946 */         this.pos = max;
/*      */       } 
/*  948 */       this.lastReturned = this.pos - 1;
/*  949 */       return n;
/*      */     }
/*      */ 
/*      */ 
/*      */     
/*      */     protected abstract long get(int param1Int);
/*      */ 
/*      */ 
/*      */     
/*      */     protected abstract void remove(int param1Int);
/*      */ 
/*      */ 
/*      */     
/*      */     protected abstract int getMaxPos();
/*      */   }
/*      */ 
/*      */   
/*      */   public static abstract class AbstractIndexBasedListIterator
/*      */     extends AbstractIndexBasedIterator
/*      */     implements LongListIterator
/*      */   {
/*      */     protected AbstractIndexBasedListIterator(int minPos, int initialPos) {
/*  971 */       super(minPos, initialPos);
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     protected abstract void add(int param1Int, long param1Long);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     protected abstract void set(int param1Int, long param1Long);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public boolean hasPrevious() {
/* 1004 */       return (this.pos > this.minPos);
/*      */     }
/*      */ 
/*      */     
/*      */     public long previousLong() {
/* 1009 */       if (!hasPrevious()) throw new NoSuchElementException(); 
/* 1010 */       return get(this.lastReturned = --this.pos);
/*      */     }
/*      */ 
/*      */     
/*      */     public int nextIndex() {
/* 1015 */       return this.pos;
/*      */     }
/*      */ 
/*      */     
/*      */     public int previousIndex() {
/* 1020 */       return this.pos - 1;
/*      */     }
/*      */ 
/*      */     
/*      */     public void add(long k) {
/* 1025 */       add(this.pos++, k);
/* 1026 */       this.lastReturned = -1;
/*      */     }
/*      */ 
/*      */     
/*      */     public void set(long k) {
/* 1031 */       if (this.lastReturned == -1) throw new IllegalStateException(); 
/* 1032 */       set(this.lastReturned, k);
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public int back(int n) {
/* 1039 */       if (n < 0) throw new IllegalArgumentException("Argument must be nonnegative: " + n); 
/* 1040 */       int remaining = this.pos - this.minPos;
/* 1041 */       if (n < remaining) {
/* 1042 */         this.pos -= n;
/*      */       } else {
/* 1044 */         n = remaining;
/* 1045 */         this.pos = this.minPos;
/*      */       } 
/* 1047 */       this.lastReturned = this.pos;
/* 1048 */       return n;
/*      */     } }
/*      */   
/*      */   private static class IntervalIterator implements LongBidirectionalIterator {
/*      */     private final long from;
/*      */     private final long to;
/*      */     long curr;
/*      */     
/*      */     public IntervalIterator(long from, long to) {
/* 1057 */       this.from = this.curr = from;
/* 1058 */       this.to = to;
/*      */     }
/*      */ 
/*      */     
/*      */     public boolean hasNext() {
/* 1063 */       return (this.curr < this.to);
/*      */     }
/*      */ 
/*      */     
/*      */     public boolean hasPrevious() {
/* 1068 */       return (this.curr > this.from);
/*      */     }
/*      */ 
/*      */     
/*      */     public long nextLong() {
/* 1073 */       if (!hasNext()) throw new NoSuchElementException(); 
/* 1074 */       return this.curr++;
/*      */     }
/*      */ 
/*      */     
/*      */     public long previousLong() {
/* 1079 */       if (!hasPrevious()) throw new NoSuchElementException(); 
/* 1080 */       return --this.curr;
/*      */     }
/*      */ 
/*      */     
/*      */     public void forEachRemaining(LongConsumer action) {
/* 1085 */       Objects.requireNonNull(action);
/* 1086 */       for (; this.curr < this.to; this.curr++) {
/* 1087 */         action.accept(this.curr);
/*      */       }
/*      */     }
/*      */ 
/*      */     
/*      */     public int skip(int n) {
/* 1093 */       if (n < 0) throw new IllegalArgumentException("Argument must be nonnegative: " + n); 
/* 1094 */       if (this.curr + n <= this.to) {
/* 1095 */         this.curr += n;
/* 1096 */         return n;
/*      */       } 
/* 1098 */       n = (int)(this.to - this.curr);
/* 1099 */       this.curr = this.to;
/* 1100 */       return n;
/*      */     }
/*      */ 
/*      */     
/*      */     public int back(int n) {
/* 1105 */       if (this.curr - n >= this.from) {
/* 1106 */         this.curr -= n;
/* 1107 */         return n;
/*      */       } 
/* 1109 */       n = (int)(this.curr - this.from);
/* 1110 */       this.curr = this.from;
/* 1111 */       return n;
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
/*      */   public static LongBidirectionalIterator fromTo(long from, long to) {
/* 1134 */     return new IntervalIterator(from, to);
/*      */   }
/*      */   
/*      */   private static class IteratorConcatenator implements LongIterator {
/*      */     final LongIterator[] a;
/* 1139 */     int lastOffset = -1; int offset; int length;
/*      */     
/*      */     public IteratorConcatenator(LongIterator[] a, int offset, int length) {
/* 1142 */       this.a = a;
/* 1143 */       this.offset = offset;
/* 1144 */       this.length = length;
/* 1145 */       advance();
/*      */     }
/*      */     
/*      */     private void advance() {
/* 1149 */       while (this.length != 0 && 
/* 1150 */         !this.a[this.offset].hasNext()) {
/* 1151 */         this.length--;
/* 1152 */         this.offset++;
/*      */       } 
/*      */     }
/*      */ 
/*      */ 
/*      */     
/*      */     public boolean hasNext() {
/* 1159 */       return (this.length > 0);
/*      */     }
/*      */ 
/*      */     
/*      */     public long nextLong() {
/* 1164 */       if (!hasNext()) throw new NoSuchElementException(); 
/* 1165 */       long next = this.a[this.lastOffset = this.offset].nextLong();
/* 1166 */       advance();
/* 1167 */       return next;
/*      */     }
/*      */ 
/*      */     
/*      */     public void forEachRemaining(LongConsumer action) {
/* 1172 */       while (this.length > 0) {
/* 1173 */         this.a[this.lastOffset = this.offset].forEachRemaining(action);
/* 1174 */         advance();
/*      */       } 
/*      */     }
/*      */ 
/*      */     
/*      */     @Deprecated
/*      */     public void forEachRemaining(Consumer<? super Long> action) {
/* 1181 */       while (this.length > 0) {
/* 1182 */         this.a[this.lastOffset = this.offset].forEachRemaining(action);
/* 1183 */         advance();
/*      */       } 
/*      */     }
/*      */ 
/*      */     
/*      */     public void remove() {
/* 1189 */       if (this.lastOffset == -1) throw new IllegalStateException(); 
/* 1190 */       this.a[this.lastOffset].remove();
/*      */     }
/*      */ 
/*      */     
/*      */     public int skip(int n) {
/* 1195 */       if (n < 0) throw new IllegalArgumentException("Argument must be nonnegative: " + n); 
/* 1196 */       this.lastOffset = -1;
/* 1197 */       int skipped = 0;
/* 1198 */       while (skipped < n && this.length != 0) {
/* 1199 */         skipped += this.a[this.offset].skip(n - skipped);
/* 1200 */         if (this.a[this.offset].hasNext())
/* 1201 */           break;  this.length--;
/* 1202 */         this.offset++;
/*      */       } 
/* 1204 */       return skipped;
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
/*      */   public static LongIterator concat(LongIterator... a) {
/* 1219 */     return concat(a, 0, a.length);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static LongIterator concat(LongIterator[] a, int offset, int length) {
/* 1237 */     return new IteratorConcatenator(a, offset, length);
/*      */   }
/*      */   
/*      */   public static class UnmodifiableIterator
/*      */     implements LongIterator {
/*      */     protected final LongIterator i;
/*      */     
/*      */     public UnmodifiableIterator(LongIterator i) {
/* 1245 */       this.i = i;
/*      */     }
/*      */ 
/*      */     
/*      */     public boolean hasNext() {
/* 1250 */       return this.i.hasNext();
/*      */     }
/*      */ 
/*      */     
/*      */     public long nextLong() {
/* 1255 */       return this.i.nextLong();
/*      */     }
/*      */ 
/*      */     
/*      */     public void forEachRemaining(LongConsumer action) {
/* 1260 */       this.i.forEachRemaining(action);
/*      */     }
/*      */ 
/*      */     
/*      */     @Deprecated
/*      */     public void forEachRemaining(Consumer<? super Long> action) {
/* 1266 */       this.i.forEachRemaining(action);
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static LongIterator unmodifiable(LongIterator i) {
/* 1277 */     return new UnmodifiableIterator(i);
/*      */   }
/*      */   
/*      */   public static class UnmodifiableBidirectionalIterator
/*      */     implements LongBidirectionalIterator {
/*      */     protected final LongBidirectionalIterator i;
/*      */     
/*      */     public UnmodifiableBidirectionalIterator(LongBidirectionalIterator i) {
/* 1285 */       this.i = i;
/*      */     }
/*      */ 
/*      */     
/*      */     public boolean hasNext() {
/* 1290 */       return this.i.hasNext();
/*      */     }
/*      */ 
/*      */     
/*      */     public boolean hasPrevious() {
/* 1295 */       return this.i.hasPrevious();
/*      */     }
/*      */ 
/*      */     
/*      */     public long nextLong() {
/* 1300 */       return this.i.nextLong();
/*      */     }
/*      */ 
/*      */     
/*      */     public long previousLong() {
/* 1305 */       return this.i.previousLong();
/*      */     }
/*      */ 
/*      */     
/*      */     public void forEachRemaining(LongConsumer action) {
/* 1310 */       this.i.forEachRemaining(action);
/*      */     }
/*      */ 
/*      */     
/*      */     @Deprecated
/*      */     public void forEachRemaining(Consumer<? super Long> action) {
/* 1316 */       this.i.forEachRemaining(action);
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static LongBidirectionalIterator unmodifiable(LongBidirectionalIterator i) {
/* 1327 */     return new UnmodifiableBidirectionalIterator(i);
/*      */   }
/*      */   
/*      */   public static class UnmodifiableListIterator
/*      */     implements LongListIterator {
/*      */     protected final LongListIterator i;
/*      */     
/*      */     public UnmodifiableListIterator(LongListIterator i) {
/* 1335 */       this.i = i;
/*      */     }
/*      */ 
/*      */     
/*      */     public boolean hasNext() {
/* 1340 */       return this.i.hasNext();
/*      */     }
/*      */ 
/*      */     
/*      */     public boolean hasPrevious() {
/* 1345 */       return this.i.hasPrevious();
/*      */     }
/*      */ 
/*      */     
/*      */     public long nextLong() {
/* 1350 */       return this.i.nextLong();
/*      */     }
/*      */ 
/*      */     
/*      */     public long previousLong() {
/* 1355 */       return this.i.previousLong();
/*      */     }
/*      */ 
/*      */     
/*      */     public int nextIndex() {
/* 1360 */       return this.i.nextIndex();
/*      */     }
/*      */ 
/*      */     
/*      */     public int previousIndex() {
/* 1365 */       return this.i.previousIndex();
/*      */     }
/*      */ 
/*      */     
/*      */     public void forEachRemaining(LongConsumer action) {
/* 1370 */       this.i.forEachRemaining(action);
/*      */     }
/*      */ 
/*      */     
/*      */     @Deprecated
/*      */     public void forEachRemaining(Consumer<? super Long> action) {
/* 1376 */       this.i.forEachRemaining(action);
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static LongListIterator unmodifiable(LongListIterator i) {
/* 1387 */     return new UnmodifiableListIterator(i);
/*      */   }
/*      */   
/*      */   private static final class ByteIteratorWrapper
/*      */     implements LongIterator {
/*      */     final ByteIterator iterator;
/*      */     
/*      */     public ByteIteratorWrapper(ByteIterator iterator) {
/* 1395 */       this.iterator = iterator;
/*      */     }
/*      */ 
/*      */     
/*      */     public boolean hasNext() {
/* 1400 */       return this.iterator.hasNext();
/*      */     }
/*      */ 
/*      */     
/*      */     @Deprecated
/*      */     public Long next() {
/* 1406 */       return Long.valueOf(this.iterator.nextByte());
/*      */     }
/*      */ 
/*      */     
/*      */     public long nextLong() {
/* 1411 */       return this.iterator.nextByte();
/*      */     }
/*      */ 
/*      */     
/*      */     public void forEachRemaining(LongConsumer action) {
/* 1416 */       Objects.requireNonNull(action);
/* 1417 */       Objects.requireNonNull(action); this.iterator.forEachRemaining(action::accept);
/*      */     }
/*      */ 
/*      */     
/*      */     public void remove() {
/* 1422 */       this.iterator.remove();
/*      */     }
/*      */ 
/*      */     
/*      */     public int skip(int n) {
/* 1427 */       return this.iterator.skip(n);
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static LongIterator wrap(ByteIterator iterator) {
/* 1438 */     return new ByteIteratorWrapper(iterator);
/*      */   }
/*      */   
/*      */   private static final class ShortIteratorWrapper
/*      */     implements LongIterator {
/*      */     final ShortIterator iterator;
/*      */     
/*      */     public ShortIteratorWrapper(ShortIterator iterator) {
/* 1446 */       this.iterator = iterator;
/*      */     }
/*      */ 
/*      */     
/*      */     public boolean hasNext() {
/* 1451 */       return this.iterator.hasNext();
/*      */     }
/*      */ 
/*      */     
/*      */     @Deprecated
/*      */     public Long next() {
/* 1457 */       return Long.valueOf(this.iterator.nextShort());
/*      */     }
/*      */ 
/*      */     
/*      */     public long nextLong() {
/* 1462 */       return this.iterator.nextShort();
/*      */     }
/*      */ 
/*      */     
/*      */     public void forEachRemaining(LongConsumer action) {
/* 1467 */       Objects.requireNonNull(action);
/* 1468 */       Objects.requireNonNull(action); this.iterator.forEachRemaining(action::accept);
/*      */     }
/*      */ 
/*      */     
/*      */     public void remove() {
/* 1473 */       this.iterator.remove();
/*      */     }
/*      */ 
/*      */     
/*      */     public int skip(int n) {
/* 1478 */       return this.iterator.skip(n);
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static LongIterator wrap(ShortIterator iterator) {
/* 1489 */     return new ShortIteratorWrapper(iterator);
/*      */   }
/*      */   
/*      */   private static final class CharIteratorWrapper
/*      */     implements LongIterator {
/*      */     final CharIterator iterator;
/*      */     
/*      */     public CharIteratorWrapper(CharIterator iterator) {
/* 1497 */       this.iterator = iterator;
/*      */     }
/*      */ 
/*      */     
/*      */     public boolean hasNext() {
/* 1502 */       return this.iterator.hasNext();
/*      */     }
/*      */ 
/*      */     
/*      */     @Deprecated
/*      */     public Long next() {
/* 1508 */       return Long.valueOf(this.iterator.nextChar());
/*      */     }
/*      */ 
/*      */     
/*      */     public long nextLong() {
/* 1513 */       return this.iterator.nextChar();
/*      */     }
/*      */ 
/*      */     
/*      */     public void forEachRemaining(LongConsumer action) {
/* 1518 */       Objects.requireNonNull(action);
/* 1519 */       Objects.requireNonNull(action); this.iterator.forEachRemaining(action::accept);
/*      */     }
/*      */ 
/*      */     
/*      */     public void remove() {
/* 1524 */       this.iterator.remove();
/*      */     }
/*      */ 
/*      */     
/*      */     public int skip(int n) {
/* 1529 */       return this.iterator.skip(n);
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
/*      */   public static LongIterator wrap(CharIterator iterator) {
/* 1547 */     return new CharIteratorWrapper(iterator);
/*      */   }
/*      */   
/*      */   private static final class IntIteratorWrapper
/*      */     implements LongIterator {
/*      */     final IntIterator iterator;
/*      */     
/*      */     public IntIteratorWrapper(IntIterator iterator) {
/* 1555 */       this.iterator = iterator;
/*      */     }
/*      */ 
/*      */     
/*      */     public boolean hasNext() {
/* 1560 */       return this.iterator.hasNext();
/*      */     }
/*      */ 
/*      */     
/*      */     @Deprecated
/*      */     public Long next() {
/* 1566 */       return Long.valueOf(this.iterator.nextInt());
/*      */     }
/*      */ 
/*      */     
/*      */     public long nextLong() {
/* 1571 */       return this.iterator.nextInt();
/*      */     }
/*      */ 
/*      */     
/*      */     public void forEachRemaining(LongConsumer action) {
/* 1576 */       Objects.requireNonNull(action);
/* 1577 */       Objects.requireNonNull(action); this.iterator.forEachRemaining(action::accept);
/*      */     }
/*      */ 
/*      */     
/*      */     public void remove() {
/* 1582 */       this.iterator.remove();
/*      */     }
/*      */ 
/*      */     
/*      */     public int skip(int n) {
/* 1587 */       return this.iterator.skip(n);
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static LongIterator wrap(IntIterator iterator) {
/* 1598 */     return new IntIteratorWrapper(iterator);
/*      */   }
/*      */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\i\\unimi\dsi\fastutil\longs\LongIterators.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */