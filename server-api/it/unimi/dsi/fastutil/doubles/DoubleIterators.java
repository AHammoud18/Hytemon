/*      */ package it.unimi.dsi.fastutil.doubles;
/*      */ 
/*      */ import it.unimi.dsi.fastutil.BigArrays;
/*      */ import it.unimi.dsi.fastutil.bytes.ByteIterator;
/*      */ import it.unimi.dsi.fastutil.chars.CharIterator;
/*      */ import it.unimi.dsi.fastutil.floats.FloatIterator;
/*      */ import it.unimi.dsi.fastutil.ints.IntIterator;
/*      */ import it.unimi.dsi.fastutil.shorts.ShortIterator;
/*      */ import java.io.Serializable;
/*      */ import java.util.Iterator;
/*      */ import java.util.ListIterator;
/*      */ import java.util.NoSuchElementException;
/*      */ import java.util.Objects;
/*      */ import java.util.PrimitiveIterator;
/*      */ import java.util.function.Consumer;
/*      */ import java.util.function.DoubleConsumer;
/*      */ import java.util.function.DoublePredicate;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ public final class DoubleIterators
/*      */ {
/*      */   public static class EmptyIterator
/*      */     implements DoubleListIterator, Serializable, Cloneable
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
/*      */     public double nextDouble() {
/*   62 */       throw new NoSuchElementException();
/*      */     }
/*      */ 
/*      */     
/*      */     public double previousDouble() {
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
/*      */     public void forEachRemaining(DoubleConsumer action) {}
/*      */ 
/*      */ 
/*      */     
/*      */     @Deprecated
/*      */     public void forEachRemaining(Consumer<? super Double> action) {}
/*      */ 
/*      */     
/*      */     public Object clone() {
/*  101 */       return DoubleIterators.EMPTY_ITERATOR;
/*      */     }
/*      */     
/*      */     private Object readResolve() {
/*  105 */       return DoubleIterators.EMPTY_ITERATOR;
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
/*      */     implements DoubleListIterator {
/*      */     private final double element;
/*      */     private byte curr;
/*      */     
/*      */     public SingletonIterator(double element) {
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
/*      */     public double nextDouble() {
/*  140 */       if (!hasNext()) throw new NoSuchElementException(); 
/*  141 */       this.curr = 1;
/*  142 */       return this.element;
/*      */     }
/*      */ 
/*      */     
/*      */     public double previousDouble() {
/*  147 */       if (!hasPrevious()) throw new NoSuchElementException(); 
/*  148 */       this.curr = 0;
/*  149 */       return this.element;
/*      */     }
/*      */ 
/*      */     
/*      */     public void forEachRemaining(DoubleConsumer action) {
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
/*      */   public static DoubleListIterator singleton(double element) {
/*  195 */     return new SingletonIterator(element);
/*      */   }
/*      */   
/*      */   private static class ArrayIterator implements DoubleListIterator {
/*      */     private final double[] array;
/*      */     private final int offset;
/*      */     private final int length;
/*      */     private int curr;
/*      */     
/*      */     public ArrayIterator(double[] array, int offset, int length) {
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
/*      */     public double nextDouble() {
/*  222 */       if (!hasNext()) throw new NoSuchElementException(); 
/*  223 */       return this.array[this.offset + this.curr++];
/*      */     }
/*      */ 
/*      */     
/*      */     public double previousDouble() {
/*  228 */       if (!hasPrevious()) throw new NoSuchElementException(); 
/*  229 */       return this.array[this.offset + --this.curr];
/*      */     }
/*      */ 
/*      */     
/*      */     public void forEachRemaining(DoubleConsumer action) {
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
/*      */   public static DoubleListIterator wrap(double[] array, int offset, int length) {
/*  290 */     DoubleArrays.ensureOffsetLength(array, offset, length);
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
/*      */   public static DoubleListIterator wrap(double[] array) {
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
/*      */   public static int unwrap(DoubleIterator i, double[] array, int offset, int max) {
/*  324 */     if (max < 0) throw new IllegalArgumentException("The maximum number of elements (" + max + ") is negative"); 
/*  325 */     if (offset < 0 || offset + max > array.length) throw new IllegalArgumentException(); 
/*  326 */     int j = max;
/*  327 */     for (; j-- != 0 && i.hasNext(); array[offset++] = i.nextDouble());
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
/*      */   public static int unwrap(DoubleIterator i, double[] array) {
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
/*      */   public static double[] unwrap(DoubleIterator i, int max) {
/*  360 */     if (max < 0) throw new IllegalArgumentException("The maximum number of elements (" + max + ") is negative"); 
/*  361 */     double[] array = new double[16];
/*  362 */     int j = 0;
/*  363 */     while (max-- != 0 && i.hasNext()) {
/*  364 */       if (j == array.length) array = DoubleArrays.grow(array, j + 1); 
/*  365 */       array[j++] = i.nextDouble();
/*      */     } 
/*  367 */     return DoubleArrays.trim(array, j);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static double[] unwrap(DoubleIterator i) {
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
/*      */   public static long unwrap(DoubleIterator i, double[][] array, long offset, long max) {
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
/*      */     //   51: invokestatic length : ([[D)J
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
/*      */     //   98: invokeinterface nextDouble : ()D
/*      */     //   103: invokestatic set : ([[DJD)V
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
/*      */     //   0	117	0	i	Lit/unimi/dsi/fastutil/doubles/DoubleIterator;
/*      */     //   0	117	1	array	[[D
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
/*      */   public static long unwrap(DoubleIterator i, double[][] array) {
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
/*      */   public static int unwrap(DoubleIterator i, DoubleCollection c, int max) {
/*  440 */     if (max < 0) throw new IllegalArgumentException("The maximum number of elements (" + max + ") is negative"); 
/*  441 */     int j = max;
/*  442 */     for (; j-- != 0 && i.hasNext(); c.add(i.nextDouble()));
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
/*      */   public static double[][] unwrapBig(DoubleIterator i, long max) {
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
/*      */     //   41: invokestatic newBigArray : (J)[[D
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
/*      */     //   70: invokestatic length : ([[D)J
/*      */     //   73: lcmp
/*      */     //   74: ifne -> 86
/*      */     //   77: aload_3
/*      */     //   78: lload #4
/*      */     //   80: lconst_1
/*      */     //   81: ladd
/*      */     //   82: invokestatic grow : ([[DJ)[[D
/*      */     //   85: astore_3
/*      */     //   86: aload_3
/*      */     //   87: lload #4
/*      */     //   89: dup2
/*      */     //   90: lconst_1
/*      */     //   91: ladd
/*      */     //   92: lstore #4
/*      */     //   94: aload_0
/*      */     //   95: invokeinterface nextDouble : ()D
/*      */     //   100: invokestatic set : ([[DJD)V
/*      */     //   103: goto -> 48
/*      */     //   106: aload_3
/*      */     //   107: lload #4
/*      */     //   109: invokestatic trim : ([[DJ)[[D
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
/*      */     //   0	113	0	i	Lit/unimi/dsi/fastutil/doubles/DoubleIterator;
/*      */     //   0	113	1	max	J
/*      */     //   45	68	3	array	[[D
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
/*      */   public static double[][] unwrapBig(DoubleIterator i) {
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
/*      */   public static long unwrap(DoubleIterator i, DoubleCollection c) {
/*  498 */     long n = 0L;
/*  499 */     while (i.hasNext()) {
/*  500 */       c.add(i.nextDouble());
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
/*      */   public static int pour(DoubleIterator i, DoubleCollection s, int max) {
/*  521 */     if (max < 0) throw new IllegalArgumentException("The maximum number of elements (" + max + ") is negative"); 
/*  522 */     int j = max;
/*  523 */     for (; j-- != 0 && i.hasNext(); s.add(i.nextDouble()));
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
/*      */   public static int pour(DoubleIterator i, DoubleCollection s) {
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
/*      */   public static DoubleList pour(DoubleIterator i, int max) {
/*  558 */     DoubleArrayList l = new DoubleArrayList();
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
/*      */   public static DoubleList pour(DoubleIterator i) {
/*  576 */     return pour(i, 2147483647);
/*      */   }
/*      */   
/*      */   private static class IteratorWrapper implements DoubleIterator {
/*      */     final Iterator<Double> i;
/*      */     
/*      */     public IteratorWrapper(Iterator<Double> i) {
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
/*      */     public double nextDouble() {
/*  598 */       return ((Double)this.i.next()).doubleValue();
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public void forEachRemaining(DoubleConsumer action) {
/*  605 */       this.i.forEachRemaining(action);
/*      */     }
/*      */ 
/*      */ 
/*      */     
/*      */     public void forEachRemaining(DoubleConsumer action) {
/*  611 */       Objects.requireNonNull(action);
/*  612 */       Objects.requireNonNull(action); this.i.forEachRemaining((action instanceof Consumer) ? (Consumer<? super Double>)action : action::accept);
/*      */     }
/*      */ 
/*      */     
/*      */     @Deprecated
/*      */     public void forEachRemaining(Consumer<? super Double> action) {
/*  618 */       this.i.forEachRemaining(action);
/*      */     }
/*      */   }
/*      */   
/*      */   private static class PrimitiveIteratorWrapper implements DoubleIterator {
/*      */     final PrimitiveIterator.OfDouble i;
/*      */     
/*      */     public PrimitiveIteratorWrapper(PrimitiveIterator.OfDouble i) {
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
/*      */     public double nextDouble() {
/*  641 */       return this.i.nextDouble();
/*      */     }
/*      */ 
/*      */     
/*      */     public void forEachRemaining(DoubleConsumer action) {
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
/*      */   public static DoubleIterator asDoubleIterator(Iterator<Double> i) {
/*  667 */     if (i instanceof DoubleIterator) return (DoubleIterator)i; 
/*  668 */     if (i instanceof PrimitiveIterator.OfDouble) return new PrimitiveIteratorWrapper((PrimitiveIterator.OfDouble)i); 
/*  669 */     return new IteratorWrapper(i);
/*      */   }
/*      */   
/*      */   private static class ListIteratorWrapper implements DoubleListIterator {
/*      */     final ListIterator<Double> i;
/*      */     
/*      */     public ListIteratorWrapper(ListIterator<Double> i) {
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
/*      */     public void set(double k) {
/*  701 */       this.i.set(Double.valueOf(k));
/*      */     }
/*      */ 
/*      */     
/*      */     public void add(double k) {
/*  706 */       this.i.add(Double.valueOf(k));
/*      */     }
/*      */ 
/*      */     
/*      */     public void remove() {
/*  711 */       this.i.remove();
/*      */     }
/*      */ 
/*      */     
/*      */     public double nextDouble() {
/*  716 */       return ((Double)this.i.next()).doubleValue();
/*      */     }
/*      */ 
/*      */     
/*      */     public double previousDouble() {
/*  721 */       return ((Double)this.i.previous()).doubleValue();
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public void forEachRemaining(DoubleConsumer action) {
/*  728 */       this.i.forEachRemaining(action);
/*      */     }
/*      */ 
/*      */ 
/*      */     
/*      */     public void forEachRemaining(DoubleConsumer action) {
/*  734 */       Objects.requireNonNull(action);
/*  735 */       Objects.requireNonNull(action); this.i.forEachRemaining((action instanceof Consumer) ? (Consumer<? super Double>)action : action::accept);
/*      */     }
/*      */ 
/*      */     
/*      */     @Deprecated
/*      */     public void forEachRemaining(Consumer<? super Double> action) {
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
/*      */   public static DoubleListIterator asDoubleIterator(ListIterator<Double> i) {
/*  762 */     if (i instanceof DoubleListIterator) return (DoubleListIterator)i; 
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
/*      */   public static boolean any(DoubleIterator iterator, DoublePredicate predicate) {
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
/*      */   public static boolean all(DoubleIterator iterator, DoublePredicate predicate) {
/*  787 */     Objects.requireNonNull(predicate);
/*      */     while (true) {
/*  789 */       if (!iterator.hasNext()) return true; 
/*  790 */       if (!predicate.test(iterator.nextDouble())) {
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
/*      */   public static int indexOf(DoubleIterator iterator, DoublePredicate predicate) {
/*  806 */     Objects.requireNonNull(predicate);
/*  807 */     for (int i = 0; iterator.hasNext(); i++) {
/*  808 */       if (predicate.test(iterator.nextDouble())) return i; 
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
/*      */     extends AbstractDoubleIterator
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
/*      */     public double nextDouble() {
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
/*      */     public void forEachRemaining(DoubleConsumer action) {
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
/*      */     protected abstract double get(int param1Int);
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
/*      */     implements DoubleListIterator
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
/*      */     protected abstract void add(int param1Int, double param1Double);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     protected abstract void set(int param1Int, double param1Double);
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
/*      */     public double previousDouble() {
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
/*      */     public void add(double k) {
/* 1025 */       add(this.pos++, k);
/* 1026 */       this.lastReturned = -1;
/*      */     }
/*      */ 
/*      */     
/*      */     public void set(double k) {
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
/*      */     }
/*      */   }
/*      */   
/*      */   private static class IteratorConcatenator implements DoubleIterator {
/*      */     final DoubleIterator[] a;
/* 1054 */     int lastOffset = -1; int offset;
/*      */     
/*      */     public IteratorConcatenator(DoubleIterator[] a, int offset, int length) {
/* 1057 */       this.a = a;
/* 1058 */       this.offset = offset;
/* 1059 */       this.length = length;
/* 1060 */       advance();
/*      */     }
/*      */     int length;
/*      */     private void advance() {
/* 1064 */       while (this.length != 0 && 
/* 1065 */         !this.a[this.offset].hasNext()) {
/* 1066 */         this.length--;
/* 1067 */         this.offset++;
/*      */       } 
/*      */     }
/*      */ 
/*      */ 
/*      */     
/*      */     public boolean hasNext() {
/* 1074 */       return (this.length > 0);
/*      */     }
/*      */ 
/*      */     
/*      */     public double nextDouble() {
/* 1079 */       if (!hasNext()) throw new NoSuchElementException(); 
/* 1080 */       double next = this.a[this.lastOffset = this.offset].nextDouble();
/* 1081 */       advance();
/* 1082 */       return next;
/*      */     }
/*      */ 
/*      */     
/*      */     public void forEachRemaining(DoubleConsumer action) {
/* 1087 */       while (this.length > 0) {
/* 1088 */         this.a[this.lastOffset = this.offset].forEachRemaining(action);
/* 1089 */         advance();
/*      */       } 
/*      */     }
/*      */ 
/*      */     
/*      */     @Deprecated
/*      */     public void forEachRemaining(Consumer<? super Double> action) {
/* 1096 */       while (this.length > 0) {
/* 1097 */         this.a[this.lastOffset = this.offset].forEachRemaining(action);
/* 1098 */         advance();
/*      */       } 
/*      */     }
/*      */ 
/*      */     
/*      */     public void remove() {
/* 1104 */       if (this.lastOffset == -1) throw new IllegalStateException(); 
/* 1105 */       this.a[this.lastOffset].remove();
/*      */     }
/*      */ 
/*      */     
/*      */     public int skip(int n) {
/* 1110 */       if (n < 0) throw new IllegalArgumentException("Argument must be nonnegative: " + n); 
/* 1111 */       this.lastOffset = -1;
/* 1112 */       int skipped = 0;
/* 1113 */       while (skipped < n && this.length != 0) {
/* 1114 */         skipped += this.a[this.offset].skip(n - skipped);
/* 1115 */         if (this.a[this.offset].hasNext())
/* 1116 */           break;  this.length--;
/* 1117 */         this.offset++;
/*      */       } 
/* 1119 */       return skipped;
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
/*      */   public static DoubleIterator concat(DoubleIterator... a) {
/* 1134 */     return concat(a, 0, a.length);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static DoubleIterator concat(DoubleIterator[] a, int offset, int length) {
/* 1152 */     return new IteratorConcatenator(a, offset, length);
/*      */   }
/*      */   
/*      */   public static class UnmodifiableIterator
/*      */     implements DoubleIterator {
/*      */     protected final DoubleIterator i;
/*      */     
/*      */     public UnmodifiableIterator(DoubleIterator i) {
/* 1160 */       this.i = i;
/*      */     }
/*      */ 
/*      */     
/*      */     public boolean hasNext() {
/* 1165 */       return this.i.hasNext();
/*      */     }
/*      */ 
/*      */     
/*      */     public double nextDouble() {
/* 1170 */       return this.i.nextDouble();
/*      */     }
/*      */ 
/*      */     
/*      */     public void forEachRemaining(DoubleConsumer action) {
/* 1175 */       this.i.forEachRemaining(action);
/*      */     }
/*      */ 
/*      */     
/*      */     @Deprecated
/*      */     public void forEachRemaining(Consumer<? super Double> action) {
/* 1181 */       this.i.forEachRemaining(action);
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static DoubleIterator unmodifiable(DoubleIterator i) {
/* 1192 */     return new UnmodifiableIterator(i);
/*      */   }
/*      */   
/*      */   public static class UnmodifiableBidirectionalIterator
/*      */     implements DoubleBidirectionalIterator {
/*      */     protected final DoubleBidirectionalIterator i;
/*      */     
/*      */     public UnmodifiableBidirectionalIterator(DoubleBidirectionalIterator i) {
/* 1200 */       this.i = i;
/*      */     }
/*      */ 
/*      */     
/*      */     public boolean hasNext() {
/* 1205 */       return this.i.hasNext();
/*      */     }
/*      */ 
/*      */     
/*      */     public boolean hasPrevious() {
/* 1210 */       return this.i.hasPrevious();
/*      */     }
/*      */ 
/*      */     
/*      */     public double nextDouble() {
/* 1215 */       return this.i.nextDouble();
/*      */     }
/*      */ 
/*      */     
/*      */     public double previousDouble() {
/* 1220 */       return this.i.previousDouble();
/*      */     }
/*      */ 
/*      */     
/*      */     public void forEachRemaining(DoubleConsumer action) {
/* 1225 */       this.i.forEachRemaining(action);
/*      */     }
/*      */ 
/*      */     
/*      */     @Deprecated
/*      */     public void forEachRemaining(Consumer<? super Double> action) {
/* 1231 */       this.i.forEachRemaining(action);
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static DoubleBidirectionalIterator unmodifiable(DoubleBidirectionalIterator i) {
/* 1242 */     return new UnmodifiableBidirectionalIterator(i);
/*      */   }
/*      */   
/*      */   public static class UnmodifiableListIterator
/*      */     implements DoubleListIterator {
/*      */     protected final DoubleListIterator i;
/*      */     
/*      */     public UnmodifiableListIterator(DoubleListIterator i) {
/* 1250 */       this.i = i;
/*      */     }
/*      */ 
/*      */     
/*      */     public boolean hasNext() {
/* 1255 */       return this.i.hasNext();
/*      */     }
/*      */ 
/*      */     
/*      */     public boolean hasPrevious() {
/* 1260 */       return this.i.hasPrevious();
/*      */     }
/*      */ 
/*      */     
/*      */     public double nextDouble() {
/* 1265 */       return this.i.nextDouble();
/*      */     }
/*      */ 
/*      */     
/*      */     public double previousDouble() {
/* 1270 */       return this.i.previousDouble();
/*      */     }
/*      */ 
/*      */     
/*      */     public int nextIndex() {
/* 1275 */       return this.i.nextIndex();
/*      */     }
/*      */ 
/*      */     
/*      */     public int previousIndex() {
/* 1280 */       return this.i.previousIndex();
/*      */     }
/*      */ 
/*      */     
/*      */     public void forEachRemaining(DoubleConsumer action) {
/* 1285 */       this.i.forEachRemaining(action);
/*      */     }
/*      */ 
/*      */     
/*      */     @Deprecated
/*      */     public void forEachRemaining(Consumer<? super Double> action) {
/* 1291 */       this.i.forEachRemaining(action);
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static DoubleListIterator unmodifiable(DoubleListIterator i) {
/* 1302 */     return new UnmodifiableListIterator(i);
/*      */   }
/*      */   
/*      */   private static final class ByteIteratorWrapper
/*      */     implements DoubleIterator {
/*      */     final ByteIterator iterator;
/*      */     
/*      */     public ByteIteratorWrapper(ByteIterator iterator) {
/* 1310 */       this.iterator = iterator;
/*      */     }
/*      */ 
/*      */     
/*      */     public boolean hasNext() {
/* 1315 */       return this.iterator.hasNext();
/*      */     }
/*      */ 
/*      */     
/*      */     @Deprecated
/*      */     public Double next() {
/* 1321 */       return Double.valueOf(this.iterator.nextByte());
/*      */     }
/*      */ 
/*      */     
/*      */     public double nextDouble() {
/* 1326 */       return this.iterator.nextByte();
/*      */     }
/*      */ 
/*      */     
/*      */     public void forEachRemaining(DoubleConsumer action) {
/* 1331 */       Objects.requireNonNull(action);
/* 1332 */       Objects.requireNonNull(action); this.iterator.forEachRemaining(action::accept);
/*      */     }
/*      */ 
/*      */     
/*      */     public void remove() {
/* 1337 */       this.iterator.remove();
/*      */     }
/*      */ 
/*      */     
/*      */     public int skip(int n) {
/* 1342 */       return this.iterator.skip(n);
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static DoubleIterator wrap(ByteIterator iterator) {
/* 1353 */     return new ByteIteratorWrapper(iterator);
/*      */   }
/*      */   
/*      */   private static final class ShortIteratorWrapper
/*      */     implements DoubleIterator {
/*      */     final ShortIterator iterator;
/*      */     
/*      */     public ShortIteratorWrapper(ShortIterator iterator) {
/* 1361 */       this.iterator = iterator;
/*      */     }
/*      */ 
/*      */     
/*      */     public boolean hasNext() {
/* 1366 */       return this.iterator.hasNext();
/*      */     }
/*      */ 
/*      */     
/*      */     @Deprecated
/*      */     public Double next() {
/* 1372 */       return Double.valueOf(this.iterator.nextShort());
/*      */     }
/*      */ 
/*      */     
/*      */     public double nextDouble() {
/* 1377 */       return this.iterator.nextShort();
/*      */     }
/*      */ 
/*      */     
/*      */     public void forEachRemaining(DoubleConsumer action) {
/* 1382 */       Objects.requireNonNull(action);
/* 1383 */       Objects.requireNonNull(action); this.iterator.forEachRemaining(action::accept);
/*      */     }
/*      */ 
/*      */     
/*      */     public void remove() {
/* 1388 */       this.iterator.remove();
/*      */     }
/*      */ 
/*      */     
/*      */     public int skip(int n) {
/* 1393 */       return this.iterator.skip(n);
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static DoubleIterator wrap(ShortIterator iterator) {
/* 1404 */     return new ShortIteratorWrapper(iterator);
/*      */   }
/*      */   
/*      */   private static final class CharIteratorWrapper
/*      */     implements DoubleIterator {
/*      */     final CharIterator iterator;
/*      */     
/*      */     public CharIteratorWrapper(CharIterator iterator) {
/* 1412 */       this.iterator = iterator;
/*      */     }
/*      */ 
/*      */     
/*      */     public boolean hasNext() {
/* 1417 */       return this.iterator.hasNext();
/*      */     }
/*      */ 
/*      */     
/*      */     @Deprecated
/*      */     public Double next() {
/* 1423 */       return Double.valueOf(this.iterator.nextChar());
/*      */     }
/*      */ 
/*      */     
/*      */     public double nextDouble() {
/* 1428 */       return this.iterator.nextChar();
/*      */     }
/*      */ 
/*      */     
/*      */     public void forEachRemaining(DoubleConsumer action) {
/* 1433 */       Objects.requireNonNull(action);
/* 1434 */       Objects.requireNonNull(action); this.iterator.forEachRemaining(action::accept);
/*      */     }
/*      */ 
/*      */     
/*      */     public void remove() {
/* 1439 */       this.iterator.remove();
/*      */     }
/*      */ 
/*      */     
/*      */     public int skip(int n) {
/* 1444 */       return this.iterator.skip(n);
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
/*      */   public static DoubleIterator wrap(CharIterator iterator) {
/* 1462 */     return new CharIteratorWrapper(iterator);
/*      */   }
/*      */   
/*      */   private static final class IntIteratorWrapper
/*      */     implements DoubleIterator {
/*      */     final IntIterator iterator;
/*      */     
/*      */     public IntIteratorWrapper(IntIterator iterator) {
/* 1470 */       this.iterator = iterator;
/*      */     }
/*      */ 
/*      */     
/*      */     public boolean hasNext() {
/* 1475 */       return this.iterator.hasNext();
/*      */     }
/*      */ 
/*      */     
/*      */     @Deprecated
/*      */     public Double next() {
/* 1481 */       return Double.valueOf(this.iterator.nextInt());
/*      */     }
/*      */ 
/*      */     
/*      */     public double nextDouble() {
/* 1486 */       return this.iterator.nextInt();
/*      */     }
/*      */ 
/*      */     
/*      */     public void forEachRemaining(DoubleConsumer action) {
/* 1491 */       Objects.requireNonNull(action);
/* 1492 */       Objects.requireNonNull(action); this.iterator.forEachRemaining(action::accept);
/*      */     }
/*      */ 
/*      */     
/*      */     public void remove() {
/* 1497 */       this.iterator.remove();
/*      */     }
/*      */ 
/*      */     
/*      */     public int skip(int n) {
/* 1502 */       return this.iterator.skip(n);
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static DoubleIterator wrap(IntIterator iterator) {
/* 1513 */     return new IntIteratorWrapper(iterator);
/*      */   }
/*      */   
/*      */   private static final class FloatIteratorWrapper
/*      */     implements DoubleIterator {
/*      */     final FloatIterator iterator;
/*      */     
/*      */     public FloatIteratorWrapper(FloatIterator iterator) {
/* 1521 */       this.iterator = iterator;
/*      */     }
/*      */ 
/*      */     
/*      */     public boolean hasNext() {
/* 1526 */       return this.iterator.hasNext();
/*      */     }
/*      */ 
/*      */     
/*      */     @Deprecated
/*      */     public Double next() {
/* 1532 */       return Double.valueOf(this.iterator.nextFloat());
/*      */     }
/*      */ 
/*      */     
/*      */     public double nextDouble() {
/* 1537 */       return this.iterator.nextFloat();
/*      */     }
/*      */ 
/*      */     
/*      */     public void forEachRemaining(DoubleConsumer action) {
/* 1542 */       Objects.requireNonNull(action);
/* 1543 */       Objects.requireNonNull(action); this.iterator.forEachRemaining(action::accept);
/*      */     }
/*      */ 
/*      */     
/*      */     public void remove() {
/* 1548 */       this.iterator.remove();
/*      */     }
/*      */ 
/*      */     
/*      */     public int skip(int n) {
/* 1553 */       return this.iterator.skip(n);
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static DoubleIterator wrap(FloatIterator iterator) {
/* 1564 */     return new FloatIteratorWrapper(iterator);
/*      */   }
/*      */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\i\\unimi\dsi\fastutil\doubles\DoubleIterators.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */