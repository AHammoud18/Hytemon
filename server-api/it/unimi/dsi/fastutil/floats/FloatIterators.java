/*      */ package it.unimi.dsi.fastutil.floats;
/*      */ 
/*      */ import it.unimi.dsi.fastutil.BigArrays;
/*      */ import it.unimi.dsi.fastutil.SafeMath;
/*      */ import it.unimi.dsi.fastutil.bytes.ByteIterator;
/*      */ import it.unimi.dsi.fastutil.chars.CharIterator;
/*      */ import it.unimi.dsi.fastutil.doubles.DoubleIterator;
/*      */ import it.unimi.dsi.fastutil.doubles.DoubleIterators;
/*      */ import it.unimi.dsi.fastutil.shorts.ShortIterator;
/*      */ import java.io.Serializable;
/*      */ import java.util.Iterator;
/*      */ import java.util.ListIterator;
/*      */ import java.util.NoSuchElementException;
/*      */ import java.util.Objects;
/*      */ import java.util.PrimitiveIterator;
/*      */ import java.util.function.Consumer;
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
/*      */ 
/*      */ 
/*      */ public final class FloatIterators
/*      */ {
/*      */   public static class EmptyIterator
/*      */     implements FloatListIterator, Serializable, Cloneable
/*      */   {
/*      */     private static final long serialVersionUID = -7046029254386353129L;
/*      */     
/*      */     public boolean hasNext() {
/*   54 */       return false;
/*      */     }
/*      */ 
/*      */     
/*      */     public boolean hasPrevious() {
/*   59 */       return false;
/*      */     }
/*      */ 
/*      */     
/*      */     public float nextFloat() {
/*   64 */       throw new NoSuchElementException();
/*      */     }
/*      */ 
/*      */     
/*      */     public float previousFloat() {
/*   69 */       throw new NoSuchElementException();
/*      */     }
/*      */ 
/*      */     
/*      */     public int nextIndex() {
/*   74 */       return 0;
/*      */     }
/*      */ 
/*      */     
/*      */     public int previousIndex() {
/*   79 */       return -1;
/*      */     }
/*      */ 
/*      */     
/*      */     public int skip(int n) {
/*   84 */       return 0;
/*      */     }
/*      */ 
/*      */     
/*      */     public int back(int n) {
/*   89 */       return 0;
/*      */     }
/*      */ 
/*      */ 
/*      */     
/*      */     public void forEachRemaining(FloatConsumer action) {}
/*      */ 
/*      */ 
/*      */     
/*      */     @Deprecated
/*      */     public void forEachRemaining(Consumer<? super Float> action) {}
/*      */ 
/*      */     
/*      */     public Object clone() {
/*  103 */       return FloatIterators.EMPTY_ITERATOR;
/*      */     }
/*      */     
/*      */     private Object readResolve() {
/*  107 */       return FloatIterators.EMPTY_ITERATOR;
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
/*  119 */   public static final EmptyIterator EMPTY_ITERATOR = new EmptyIterator();
/*      */   
/*      */   private static class SingletonIterator
/*      */     implements FloatListIterator {
/*      */     private final float element;
/*      */     private byte curr;
/*      */     
/*      */     public SingletonIterator(float element) {
/*  127 */       this.element = element;
/*      */     }
/*      */ 
/*      */     
/*      */     public boolean hasNext() {
/*  132 */       return (this.curr == 0);
/*      */     }
/*      */ 
/*      */     
/*      */     public boolean hasPrevious() {
/*  137 */       return (this.curr == 1);
/*      */     }
/*      */ 
/*      */     
/*      */     public float nextFloat() {
/*  142 */       if (!hasNext()) throw new NoSuchElementException(); 
/*  143 */       this.curr = 1;
/*  144 */       return this.element;
/*      */     }
/*      */ 
/*      */     
/*      */     public float previousFloat() {
/*  149 */       if (!hasPrevious()) throw new NoSuchElementException(); 
/*  150 */       this.curr = 0;
/*  151 */       return this.element;
/*      */     }
/*      */ 
/*      */     
/*      */     public void forEachRemaining(FloatConsumer action) {
/*  156 */       Objects.requireNonNull(action);
/*  157 */       if (this.curr == 0) {
/*  158 */         action.accept(this.element);
/*  159 */         this.curr = 1;
/*      */       } 
/*      */     }
/*      */ 
/*      */     
/*      */     public int nextIndex() {
/*  165 */       return this.curr;
/*      */     }
/*      */ 
/*      */     
/*      */     public int previousIndex() {
/*  170 */       return this.curr - 1;
/*      */     }
/*      */ 
/*      */     
/*      */     public int back(int n) {
/*  175 */       if (n < 0) throw new IllegalArgumentException("Argument must be nonnegative: " + n); 
/*  176 */       if (n == 0 || this.curr < 1) return 0; 
/*  177 */       this.curr = 1;
/*  178 */       return 1;
/*      */     }
/*      */ 
/*      */     
/*      */     public int skip(int n) {
/*  183 */       if (n < 0) throw new IllegalArgumentException("Argument must be nonnegative: " + n); 
/*  184 */       if (n == 0 || this.curr > 0) return 0; 
/*  185 */       this.curr = 0;
/*  186 */       return 1;
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static FloatListIterator singleton(float element) {
/*  197 */     return new SingletonIterator(element);
/*      */   }
/*      */   
/*      */   private static class ArrayIterator implements FloatListIterator {
/*      */     private final float[] array;
/*      */     private final int offset;
/*      */     private final int length;
/*      */     private int curr;
/*      */     
/*      */     public ArrayIterator(float[] array, int offset, int length) {
/*  207 */       this.array = array;
/*  208 */       this.offset = offset;
/*  209 */       this.length = length;
/*      */     }
/*      */ 
/*      */     
/*      */     public boolean hasNext() {
/*  214 */       return (this.curr < this.length);
/*      */     }
/*      */ 
/*      */     
/*      */     public boolean hasPrevious() {
/*  219 */       return (this.curr > 0);
/*      */     }
/*      */ 
/*      */     
/*      */     public float nextFloat() {
/*  224 */       if (!hasNext()) throw new NoSuchElementException(); 
/*  225 */       return this.array[this.offset + this.curr++];
/*      */     }
/*      */ 
/*      */     
/*      */     public float previousFloat() {
/*  230 */       if (!hasPrevious()) throw new NoSuchElementException(); 
/*  231 */       return this.array[this.offset + --this.curr];
/*      */     }
/*      */ 
/*      */     
/*      */     public void forEachRemaining(FloatConsumer action) {
/*  236 */       Objects.requireNonNull(action);
/*  237 */       for (; this.curr < this.length; this.curr++) {
/*  238 */         action.accept(this.array[this.offset + this.curr]);
/*      */       }
/*      */     }
/*      */ 
/*      */     
/*      */     public int skip(int n) {
/*  244 */       if (n < 0) throw new IllegalArgumentException("Argument must be nonnegative: " + n); 
/*  245 */       if (n <= this.length - this.curr) {
/*  246 */         this.curr += n;
/*  247 */         return n;
/*      */       } 
/*  249 */       n = this.length - this.curr;
/*  250 */       this.curr = this.length;
/*  251 */       return n;
/*      */     }
/*      */ 
/*      */     
/*      */     public int back(int n) {
/*  256 */       if (n < 0) throw new IllegalArgumentException("Argument must be nonnegative: " + n); 
/*  257 */       if (n <= this.curr) {
/*  258 */         this.curr -= n;
/*  259 */         return n;
/*      */       } 
/*  261 */       n = this.curr;
/*  262 */       this.curr = 0;
/*  263 */       return n;
/*      */     }
/*      */ 
/*      */     
/*      */     public int nextIndex() {
/*  268 */       return this.curr;
/*      */     }
/*      */ 
/*      */     
/*      */     public int previousIndex() {
/*  273 */       return this.curr - 1;
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
/*      */   public static FloatListIterator wrap(float[] array, int offset, int length) {
/*  292 */     FloatArrays.ensureOffsetLength(array, offset, length);
/*  293 */     return new ArrayIterator(array, offset, length);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static FloatListIterator wrap(float[] array) {
/*  307 */     return new ArrayIterator(array, 0, array.length);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static int unwrap(FloatIterator i, float[] array, int offset, int max) {
/*  326 */     if (max < 0) throw new IllegalArgumentException("The maximum number of elements (" + max + ") is negative"); 
/*  327 */     if (offset < 0 || offset + max > array.length) throw new IllegalArgumentException(); 
/*  328 */     int j = max;
/*  329 */     for (; j-- != 0 && i.hasNext(); array[offset++] = i.nextFloat());
/*  330 */     return max - j - 1;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static int unwrap(FloatIterator i, float[] array) {
/*  346 */     return unwrap(i, array, 0, array.length);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static float[] unwrap(FloatIterator i, int max) {
/*  362 */     if (max < 0) throw new IllegalArgumentException("The maximum number of elements (" + max + ") is negative"); 
/*  363 */     float[] array = new float[16];
/*  364 */     int j = 0;
/*  365 */     while (max-- != 0 && i.hasNext()) {
/*  366 */       if (j == array.length) array = FloatArrays.grow(array, j + 1); 
/*  367 */       array[j++] = i.nextFloat();
/*      */     } 
/*  369 */     return FloatArrays.trim(array, j);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static float[] unwrap(FloatIterator i) {
/*  383 */     return unwrap(i, 2147483647);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static long unwrap(FloatIterator i, float[][] array, long offset, long max) {
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
/*      */     //   51: invokestatic length : ([[F)J
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
/*      */     //   98: invokeinterface nextFloat : ()F
/*      */     //   103: invokestatic set : ([[FJF)V
/*      */     //   106: goto -> 70
/*      */     //   109: lload #4
/*      */     //   111: lload #6
/*      */     //   113: lsub
/*      */     //   114: lconst_1
/*      */     //   115: lsub
/*      */     //   116: lreturn
/*      */     // Line number table:
/*      */     //   Java source line number -> byte code offset
/*      */     //   #402	-> 0
/*      */     //   #403	-> 40
/*      */     //   #404	-> 66
/*      */     //   #405	-> 70
/*      */     //   #406	-> 109
/*      */     // Local variable table:
/*      */     //   start	length	slot	name	descriptor
/*      */     //   0	117	0	i	Lit/unimi/dsi/fastutil/floats/FloatIterator;
/*      */     //   0	117	1	array	[[F
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
/*      */   public static long unwrap(FloatIterator i, float[][] array) {
/*  422 */     return unwrap(i, array, 0L, BigArrays.length(array));
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static int unwrap(FloatIterator i, FloatCollection c, int max) {
/*  442 */     if (max < 0) throw new IllegalArgumentException("The maximum number of elements (" + max + ") is negative"); 
/*  443 */     int j = max;
/*  444 */     for (; j-- != 0 && i.hasNext(); c.add(i.nextFloat()));
/*  445 */     return max - j - 1;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static float[][] unwrapBig(FloatIterator i, long max) {
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
/*      */     //   41: invokestatic newBigArray : (J)[[F
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
/*      */     //   70: invokestatic length : ([[F)J
/*      */     //   73: lcmp
/*      */     //   74: ifne -> 86
/*      */     //   77: aload_3
/*      */     //   78: lload #4
/*      */     //   80: lconst_1
/*      */     //   81: ladd
/*      */     //   82: invokestatic grow : ([[FJ)[[F
/*      */     //   85: astore_3
/*      */     //   86: aload_3
/*      */     //   87: lload #4
/*      */     //   89: dup2
/*      */     //   90: lconst_1
/*      */     //   91: ladd
/*      */     //   92: lstore #4
/*      */     //   94: aload_0
/*      */     //   95: invokeinterface nextFloat : ()F
/*      */     //   100: invokestatic set : ([[FJF)V
/*      */     //   103: goto -> 48
/*      */     //   106: aload_3
/*      */     //   107: lload #4
/*      */     //   109: invokestatic trim : ([[FJ)[[F
/*      */     //   112: areturn
/*      */     // Line number table:
/*      */     //   Java source line number -> byte code offset
/*      */     //   #461	-> 0
/*      */     //   #462	-> 38
/*      */     //   #463	-> 45
/*      */     //   #464	-> 48
/*      */     //   #465	-> 67
/*      */     //   #466	-> 86
/*      */     //   #468	-> 106
/*      */     // Local variable table:
/*      */     //   start	length	slot	name	descriptor
/*      */     //   0	113	0	i	Lit/unimi/dsi/fastutil/floats/FloatIterator;
/*      */     //   0	113	1	max	J
/*      */     //   45	68	3	array	[[F
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
/*      */   public static float[][] unwrapBig(FloatIterator i) {
/*  482 */     return unwrapBig(i, Long.MAX_VALUE);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static long unwrap(FloatIterator i, FloatCollection c) {
/*  500 */     long n = 0L;
/*  501 */     while (i.hasNext()) {
/*  502 */       c.add(i.nextFloat());
/*  503 */       n++;
/*      */     } 
/*  505 */     return n;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static int pour(FloatIterator i, FloatCollection s, int max) {
/*  523 */     if (max < 0) throw new IllegalArgumentException("The maximum number of elements (" + max + ") is negative"); 
/*  524 */     int j = max;
/*  525 */     for (; j-- != 0 && i.hasNext(); s.add(i.nextFloat()));
/*  526 */     return max - j - 1;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static int pour(FloatIterator i, FloatCollection s) {
/*  543 */     return pour(i, s, 2147483647);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static FloatList pour(FloatIterator i, int max) {
/*  560 */     FloatArrayList l = new FloatArrayList();
/*  561 */     pour(i, l, max);
/*  562 */     l.trim();
/*  563 */     return l;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static FloatList pour(FloatIterator i) {
/*  578 */     return pour(i, 2147483647);
/*      */   }
/*      */   
/*      */   private static class IteratorWrapper implements FloatIterator {
/*      */     final Iterator<Float> i;
/*      */     
/*      */     public IteratorWrapper(Iterator<Float> i) {
/*  585 */       this.i = i;
/*      */     }
/*      */ 
/*      */     
/*      */     public boolean hasNext() {
/*  590 */       return this.i.hasNext();
/*      */     }
/*      */ 
/*      */     
/*      */     public void remove() {
/*  595 */       this.i.remove();
/*      */     }
/*      */ 
/*      */     
/*      */     public float nextFloat() {
/*  600 */       return ((Float)this.i.next()).floatValue();
/*      */     }
/*      */ 
/*      */     
/*      */     public void forEachRemaining(FloatConsumer action) {
/*  605 */       this.i.forEachRemaining(action);
/*      */     }
/*      */ 
/*      */     
/*      */     @Deprecated
/*      */     public void forEachRemaining(Consumer<? super Float> action) {
/*  611 */       this.i.forEachRemaining(action);
/*      */     }
/*      */   }
/*      */   
/*      */   private static class PrimitiveIteratorWrapper implements FloatIterator {
/*      */     final PrimitiveIterator.OfDouble i;
/*      */     
/*      */     public PrimitiveIteratorWrapper(PrimitiveIterator.OfDouble i) {
/*  619 */       this.i = i;
/*      */     }
/*      */ 
/*      */     
/*      */     public boolean hasNext() {
/*  624 */       return this.i.hasNext();
/*      */     }
/*      */ 
/*      */     
/*      */     public void remove() {
/*  629 */       this.i.remove();
/*      */     }
/*      */ 
/*      */     
/*      */     public float nextFloat() {
/*  634 */       return (float)this.i.nextDouble();
/*      */     }
/*      */ 
/*      */     
/*      */     public void forEachRemaining(FloatConsumer action) {
/*  639 */       this.i.forEachRemaining(action);
/*      */     }
/*      */   }
/*      */   
/*      */   private static class CheckedPrimitiveIteratorWrapper extends PrimitiveIteratorWrapper {
/*      */     public CheckedPrimitiveIteratorWrapper(PrimitiveIterator.OfDouble i) {
/*  645 */       super(i);
/*      */     }
/*      */ 
/*      */     
/*      */     public float nextFloat() {
/*  650 */       return SafeMath.safeDoubleToFloat(this.i.nextDouble());
/*      */     }
/*      */ 
/*      */     
/*      */     public void forEachRemaining(FloatConsumer action) {
/*  655 */       this.i.forEachRemaining(value -> action.accept(SafeMath.safeDoubleToFloat(value)));
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
/*      */   public static FloatIterator asFloatIterator(Iterator<Float> i) {
/*  678 */     if (i instanceof FloatIterator) return (FloatIterator)i; 
/*  679 */     return new IteratorWrapper(i);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static FloatIterator narrow(PrimitiveIterator.OfDouble i) {
/*  693 */     return new CheckedPrimitiveIteratorWrapper(i);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static FloatIterator uncheckedNarrow(PrimitiveIterator.OfDouble i) {
/*  708 */     return new PrimitiveIteratorWrapper(i);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static DoubleIterator widen(FloatIterator i) {
/*  719 */     return DoubleIterators.wrap(i);
/*      */   }
/*      */   
/*      */   private static class ListIteratorWrapper implements FloatListIterator {
/*      */     final ListIterator<Float> i;
/*      */     
/*      */     public ListIteratorWrapper(ListIterator<Float> i) {
/*  726 */       this.i = i;
/*      */     }
/*      */ 
/*      */     
/*      */     public boolean hasNext() {
/*  731 */       return this.i.hasNext();
/*      */     }
/*      */ 
/*      */     
/*      */     public boolean hasPrevious() {
/*  736 */       return this.i.hasPrevious();
/*      */     }
/*      */ 
/*      */     
/*      */     public int nextIndex() {
/*  741 */       return this.i.nextIndex();
/*      */     }
/*      */ 
/*      */     
/*      */     public int previousIndex() {
/*  746 */       return this.i.previousIndex();
/*      */     }
/*      */ 
/*      */     
/*      */     public void set(float k) {
/*  751 */       this.i.set(Float.valueOf(k));
/*      */     }
/*      */ 
/*      */     
/*      */     public void add(float k) {
/*  756 */       this.i.add(Float.valueOf(k));
/*      */     }
/*      */ 
/*      */     
/*      */     public void remove() {
/*  761 */       this.i.remove();
/*      */     }
/*      */ 
/*      */     
/*      */     public float nextFloat() {
/*  766 */       return ((Float)this.i.next()).floatValue();
/*      */     }
/*      */ 
/*      */     
/*      */     public float previousFloat() {
/*  771 */       return ((Float)this.i.previous()).floatValue();
/*      */     }
/*      */ 
/*      */     
/*      */     public void forEachRemaining(FloatConsumer action) {
/*  776 */       this.i.forEachRemaining(action);
/*      */     }
/*      */ 
/*      */     
/*      */     @Deprecated
/*      */     public void forEachRemaining(Consumer<? super Float> action) {
/*  782 */       this.i.forEachRemaining(action);
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
/*      */   public static FloatListIterator asFloatIterator(ListIterator<Float> i) {
/*  803 */     if (i instanceof FloatListIterator) return (FloatListIterator)i; 
/*  804 */     return new ListIteratorWrapper(i);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static boolean any(FloatIterator iterator, FloatPredicate predicate) {
/*  816 */     return (indexOf(iterator, predicate) != -1);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static boolean any(FloatIterator iterator, DoublePredicate predicate) {
/*  829 */     Objects.requireNonNull(predicate); return any(iterator, (predicate instanceof FloatPredicate) ? (FloatPredicate)predicate : predicate::test);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static boolean all(FloatIterator iterator, FloatPredicate predicate) {
/*  841 */     Objects.requireNonNull(predicate);
/*      */     while (true) {
/*  843 */       if (!iterator.hasNext()) return true; 
/*  844 */       if (!predicate.test(iterator.nextFloat())) {
/*  845 */         return false;
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
/*      */   public static boolean all(FloatIterator iterator, DoublePredicate predicate) {
/*  860 */     Objects.requireNonNull(predicate); return all(iterator, (predicate instanceof FloatPredicate) ? (FloatPredicate)predicate : predicate::test);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static int indexOf(FloatIterator iterator, FloatPredicate predicate) {
/*  875 */     Objects.requireNonNull(predicate);
/*  876 */     for (int i = 0; iterator.hasNext(); i++) {
/*  877 */       if (predicate.test(iterator.nextFloat())) return i; 
/*      */     } 
/*  879 */     return -1;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static int indexOf(FloatIterator iterator, DoublePredicate predicate) {
/*  897 */     Objects.requireNonNull(predicate); return indexOf(iterator, (predicate instanceof FloatPredicate) ? (FloatPredicate)predicate : predicate::test);
/*      */   }
/*      */ 
/*      */ 
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
/*      */     extends AbstractFloatIterator
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
/*  947 */       this.minPos = minPos;
/*  948 */       this.pos = initialPos;
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
/*  997 */       return (this.pos < getMaxPos());
/*      */     }
/*      */ 
/*      */     
/*      */     public float nextFloat() {
/* 1002 */       if (!hasNext()) throw new NoSuchElementException(); 
/* 1003 */       return get(this.lastReturned = this.pos++);
/*      */     }
/*      */ 
/*      */     
/*      */     public void remove() {
/* 1008 */       if (this.lastReturned == -1) throw new IllegalStateException(); 
/* 1009 */       remove(this.lastReturned);
/*      */       
/* 1011 */       if (this.lastReturned < this.pos) this.pos--; 
/* 1012 */       this.lastReturned = -1;
/*      */     }
/*      */ 
/*      */     
/*      */     public void forEachRemaining(FloatConsumer action) {
/* 1017 */       while (this.pos < getMaxPos()) {
/* 1018 */         action.accept(get(this.lastReturned = this.pos++));
/*      */       }
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public int skip(int n) {
/* 1026 */       if (n < 0) throw new IllegalArgumentException("Argument must be nonnegative: " + n); 
/* 1027 */       int max = getMaxPos();
/* 1028 */       int remaining = max - this.pos;
/* 1029 */       if (n < remaining) {
/* 1030 */         this.pos += n;
/*      */       } else {
/* 1032 */         n = remaining;
/* 1033 */         this.pos = max;
/*      */       } 
/* 1035 */       this.lastReturned = this.pos - 1;
/* 1036 */       return n;
/*      */     }
/*      */ 
/*      */ 
/*      */     
/*      */     protected abstract float get(int param1Int);
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
/*      */     implements FloatListIterator
/*      */   {
/*      */     protected AbstractIndexBasedListIterator(int minPos, int initialPos) {
/* 1058 */       super(minPos, initialPos);
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
/*      */     protected abstract void add(int param1Int, float param1Float);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     protected abstract void set(int param1Int, float param1Float);
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
/* 1091 */       return (this.pos > this.minPos);
/*      */     }
/*      */ 
/*      */     
/*      */     public float previousFloat() {
/* 1096 */       if (!hasPrevious()) throw new NoSuchElementException(); 
/* 1097 */       return get(this.lastReturned = --this.pos);
/*      */     }
/*      */ 
/*      */     
/*      */     public int nextIndex() {
/* 1102 */       return this.pos;
/*      */     }
/*      */ 
/*      */     
/*      */     public int previousIndex() {
/* 1107 */       return this.pos - 1;
/*      */     }
/*      */ 
/*      */     
/*      */     public void add(float k) {
/* 1112 */       add(this.pos++, k);
/* 1113 */       this.lastReturned = -1;
/*      */     }
/*      */ 
/*      */     
/*      */     public void set(float k) {
/* 1118 */       if (this.lastReturned == -1) throw new IllegalStateException(); 
/* 1119 */       set(this.lastReturned, k);
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public int back(int n) {
/* 1126 */       if (n < 0) throw new IllegalArgumentException("Argument must be nonnegative: " + n); 
/* 1127 */       int remaining = this.pos - this.minPos;
/* 1128 */       if (n < remaining) {
/* 1129 */         this.pos -= n;
/*      */       } else {
/* 1131 */         n = remaining;
/* 1132 */         this.pos = this.minPos;
/*      */       } 
/* 1134 */       this.lastReturned = this.pos;
/* 1135 */       return n;
/*      */     }
/*      */   }
/*      */   
/*      */   private static class IteratorConcatenator implements FloatIterator {
/*      */     final FloatIterator[] a;
/* 1141 */     int lastOffset = -1; int offset;
/*      */     
/*      */     public IteratorConcatenator(FloatIterator[] a, int offset, int length) {
/* 1144 */       this.a = a;
/* 1145 */       this.offset = offset;
/* 1146 */       this.length = length;
/* 1147 */       advance();
/*      */     }
/*      */     int length;
/*      */     private void advance() {
/* 1151 */       while (this.length != 0 && 
/* 1152 */         !this.a[this.offset].hasNext()) {
/* 1153 */         this.length--;
/* 1154 */         this.offset++;
/*      */       } 
/*      */     }
/*      */ 
/*      */ 
/*      */     
/*      */     public boolean hasNext() {
/* 1161 */       return (this.length > 0);
/*      */     }
/*      */ 
/*      */     
/*      */     public float nextFloat() {
/* 1166 */       if (!hasNext()) throw new NoSuchElementException(); 
/* 1167 */       float next = this.a[this.lastOffset = this.offset].nextFloat();
/* 1168 */       advance();
/* 1169 */       return next;
/*      */     }
/*      */ 
/*      */     
/*      */     public void forEachRemaining(FloatConsumer action) {
/* 1174 */       while (this.length > 0) {
/* 1175 */         this.a[this.lastOffset = this.offset].forEachRemaining(action);
/* 1176 */         advance();
/*      */       } 
/*      */     }
/*      */ 
/*      */     
/*      */     @Deprecated
/*      */     public void forEachRemaining(Consumer<? super Float> action) {
/* 1183 */       while (this.length > 0) {
/* 1184 */         this.a[this.lastOffset = this.offset].forEachRemaining(action);
/* 1185 */         advance();
/*      */       } 
/*      */     }
/*      */ 
/*      */     
/*      */     public void remove() {
/* 1191 */       if (this.lastOffset == -1) throw new IllegalStateException(); 
/* 1192 */       this.a[this.lastOffset].remove();
/*      */     }
/*      */ 
/*      */     
/*      */     public int skip(int n) {
/* 1197 */       if (n < 0) throw new IllegalArgumentException("Argument must be nonnegative: " + n); 
/* 1198 */       this.lastOffset = -1;
/* 1199 */       int skipped = 0;
/* 1200 */       while (skipped < n && this.length != 0) {
/* 1201 */         skipped += this.a[this.offset].skip(n - skipped);
/* 1202 */         if (this.a[this.offset].hasNext())
/* 1203 */           break;  this.length--;
/* 1204 */         this.offset++;
/*      */       } 
/* 1206 */       return skipped;
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
/*      */   public static FloatIterator concat(FloatIterator... a) {
/* 1221 */     return concat(a, 0, a.length);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static FloatIterator concat(FloatIterator[] a, int offset, int length) {
/* 1239 */     return new IteratorConcatenator(a, offset, length);
/*      */   }
/*      */   
/*      */   public static class UnmodifiableIterator
/*      */     implements FloatIterator {
/*      */     protected final FloatIterator i;
/*      */     
/*      */     public UnmodifiableIterator(FloatIterator i) {
/* 1247 */       this.i = i;
/*      */     }
/*      */ 
/*      */     
/*      */     public boolean hasNext() {
/* 1252 */       return this.i.hasNext();
/*      */     }
/*      */ 
/*      */     
/*      */     public float nextFloat() {
/* 1257 */       return this.i.nextFloat();
/*      */     }
/*      */ 
/*      */     
/*      */     public void forEachRemaining(FloatConsumer action) {
/* 1262 */       this.i.forEachRemaining(action);
/*      */     }
/*      */ 
/*      */     
/*      */     @Deprecated
/*      */     public void forEachRemaining(Consumer<? super Float> action) {
/* 1268 */       this.i.forEachRemaining(action);
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static FloatIterator unmodifiable(FloatIterator i) {
/* 1279 */     return new UnmodifiableIterator(i);
/*      */   }
/*      */   
/*      */   public static class UnmodifiableBidirectionalIterator
/*      */     implements FloatBidirectionalIterator {
/*      */     protected final FloatBidirectionalIterator i;
/*      */     
/*      */     public UnmodifiableBidirectionalIterator(FloatBidirectionalIterator i) {
/* 1287 */       this.i = i;
/*      */     }
/*      */ 
/*      */     
/*      */     public boolean hasNext() {
/* 1292 */       return this.i.hasNext();
/*      */     }
/*      */ 
/*      */     
/*      */     public boolean hasPrevious() {
/* 1297 */       return this.i.hasPrevious();
/*      */     }
/*      */ 
/*      */     
/*      */     public float nextFloat() {
/* 1302 */       return this.i.nextFloat();
/*      */     }
/*      */ 
/*      */     
/*      */     public float previousFloat() {
/* 1307 */       return this.i.previousFloat();
/*      */     }
/*      */ 
/*      */     
/*      */     public void forEachRemaining(FloatConsumer action) {
/* 1312 */       this.i.forEachRemaining(action);
/*      */     }
/*      */ 
/*      */     
/*      */     @Deprecated
/*      */     public void forEachRemaining(Consumer<? super Float> action) {
/* 1318 */       this.i.forEachRemaining(action);
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static FloatBidirectionalIterator unmodifiable(FloatBidirectionalIterator i) {
/* 1329 */     return new UnmodifiableBidirectionalIterator(i);
/*      */   }
/*      */   
/*      */   public static class UnmodifiableListIterator
/*      */     implements FloatListIterator {
/*      */     protected final FloatListIterator i;
/*      */     
/*      */     public UnmodifiableListIterator(FloatListIterator i) {
/* 1337 */       this.i = i;
/*      */     }
/*      */ 
/*      */     
/*      */     public boolean hasNext() {
/* 1342 */       return this.i.hasNext();
/*      */     }
/*      */ 
/*      */     
/*      */     public boolean hasPrevious() {
/* 1347 */       return this.i.hasPrevious();
/*      */     }
/*      */ 
/*      */     
/*      */     public float nextFloat() {
/* 1352 */       return this.i.nextFloat();
/*      */     }
/*      */ 
/*      */     
/*      */     public float previousFloat() {
/* 1357 */       return this.i.previousFloat();
/*      */     }
/*      */ 
/*      */     
/*      */     public int nextIndex() {
/* 1362 */       return this.i.nextIndex();
/*      */     }
/*      */ 
/*      */     
/*      */     public int previousIndex() {
/* 1367 */       return this.i.previousIndex();
/*      */     }
/*      */ 
/*      */     
/*      */     public void forEachRemaining(FloatConsumer action) {
/* 1372 */       this.i.forEachRemaining(action);
/*      */     }
/*      */ 
/*      */     
/*      */     @Deprecated
/*      */     public void forEachRemaining(Consumer<? super Float> action) {
/* 1378 */       this.i.forEachRemaining(action);
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static FloatListIterator unmodifiable(FloatListIterator i) {
/* 1389 */     return new UnmodifiableListIterator(i);
/*      */   }
/*      */   
/*      */   private static final class ByteIteratorWrapper
/*      */     implements FloatIterator {
/*      */     final ByteIterator iterator;
/*      */     
/*      */     public ByteIteratorWrapper(ByteIterator iterator) {
/* 1397 */       this.iterator = iterator;
/*      */     }
/*      */ 
/*      */     
/*      */     public boolean hasNext() {
/* 1402 */       return this.iterator.hasNext();
/*      */     }
/*      */ 
/*      */     
/*      */     @Deprecated
/*      */     public Float next() {
/* 1408 */       return Float.valueOf(this.iterator.nextByte());
/*      */     }
/*      */ 
/*      */     
/*      */     public float nextFloat() {
/* 1413 */       return this.iterator.nextByte();
/*      */     }
/*      */ 
/*      */     
/*      */     public void forEachRemaining(FloatConsumer action) {
/* 1418 */       Objects.requireNonNull(action);
/* 1419 */       Objects.requireNonNull(action); this.iterator.forEachRemaining(action::accept);
/*      */     }
/*      */ 
/*      */     
/*      */     public void remove() {
/* 1424 */       this.iterator.remove();
/*      */     }
/*      */ 
/*      */     
/*      */     public int skip(int n) {
/* 1429 */       return this.iterator.skip(n);
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static FloatIterator wrap(ByteIterator iterator) {
/* 1440 */     return new ByteIteratorWrapper(iterator);
/*      */   }
/*      */   
/*      */   private static final class ShortIteratorWrapper
/*      */     implements FloatIterator {
/*      */     final ShortIterator iterator;
/*      */     
/*      */     public ShortIteratorWrapper(ShortIterator iterator) {
/* 1448 */       this.iterator = iterator;
/*      */     }
/*      */ 
/*      */     
/*      */     public boolean hasNext() {
/* 1453 */       return this.iterator.hasNext();
/*      */     }
/*      */ 
/*      */     
/*      */     @Deprecated
/*      */     public Float next() {
/* 1459 */       return Float.valueOf(this.iterator.nextShort());
/*      */     }
/*      */ 
/*      */     
/*      */     public float nextFloat() {
/* 1464 */       return this.iterator.nextShort();
/*      */     }
/*      */ 
/*      */     
/*      */     public void forEachRemaining(FloatConsumer action) {
/* 1469 */       Objects.requireNonNull(action);
/* 1470 */       Objects.requireNonNull(action); this.iterator.forEachRemaining(action::accept);
/*      */     }
/*      */ 
/*      */     
/*      */     public void remove() {
/* 1475 */       this.iterator.remove();
/*      */     }
/*      */ 
/*      */     
/*      */     public int skip(int n) {
/* 1480 */       return this.iterator.skip(n);
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static FloatIterator wrap(ShortIterator iterator) {
/* 1491 */     return new ShortIteratorWrapper(iterator);
/*      */   }
/*      */   
/*      */   private static final class CharIteratorWrapper
/*      */     implements FloatIterator {
/*      */     final CharIterator iterator;
/*      */     
/*      */     public CharIteratorWrapper(CharIterator iterator) {
/* 1499 */       this.iterator = iterator;
/*      */     }
/*      */ 
/*      */     
/*      */     public boolean hasNext() {
/* 1504 */       return this.iterator.hasNext();
/*      */     }
/*      */ 
/*      */     
/*      */     @Deprecated
/*      */     public Float next() {
/* 1510 */       return Float.valueOf(this.iterator.nextChar());
/*      */     }
/*      */ 
/*      */     
/*      */     public float nextFloat() {
/* 1515 */       return this.iterator.nextChar();
/*      */     }
/*      */ 
/*      */     
/*      */     public void forEachRemaining(FloatConsumer action) {
/* 1520 */       Objects.requireNonNull(action);
/* 1521 */       Objects.requireNonNull(action); this.iterator.forEachRemaining(action::accept);
/*      */     }
/*      */ 
/*      */     
/*      */     public void remove() {
/* 1526 */       this.iterator.remove();
/*      */     }
/*      */ 
/*      */     
/*      */     public int skip(int n) {
/* 1531 */       return this.iterator.skip(n);
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
/*      */   public static FloatIterator wrap(CharIterator iterator) {
/* 1549 */     return new CharIteratorWrapper(iterator);
/*      */   }
/*      */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\i\\unimi\dsi\fastutil\floats\FloatIterators.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */