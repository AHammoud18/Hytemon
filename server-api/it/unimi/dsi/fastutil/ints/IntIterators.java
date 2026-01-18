/*      */ package it.unimi.dsi.fastutil.ints;
/*      */ 
/*      */ import it.unimi.dsi.fastutil.BigArrays;
/*      */ import it.unimi.dsi.fastutil.bytes.ByteIterator;
/*      */ import it.unimi.dsi.fastutil.chars.CharIterator;
/*      */ import it.unimi.dsi.fastutil.shorts.ShortIterator;
/*      */ import java.io.Serializable;
/*      */ import java.util.Iterator;
/*      */ import java.util.ListIterator;
/*      */ import java.util.NoSuchElementException;
/*      */ import java.util.Objects;
/*      */ import java.util.PrimitiveIterator;
/*      */ import java.util.function.Consumer;
/*      */ import java.util.function.IntConsumer;
/*      */ import java.util.function.IntPredicate;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ public final class IntIterators
/*      */ {
/*      */   public static class EmptyIterator
/*      */     implements IntListIterator, Serializable, Cloneable
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
/*      */     public int nextInt() {
/*   62 */       throw new NoSuchElementException();
/*      */     }
/*      */ 
/*      */     
/*      */     public int previousInt() {
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
/*      */     public void forEachRemaining(IntConsumer action) {}
/*      */ 
/*      */ 
/*      */     
/*      */     @Deprecated
/*      */     public void forEachRemaining(Consumer<? super Integer> action) {}
/*      */ 
/*      */     
/*      */     public Object clone() {
/*  101 */       return IntIterators.EMPTY_ITERATOR;
/*      */     }
/*      */     
/*      */     private Object readResolve() {
/*  105 */       return IntIterators.EMPTY_ITERATOR;
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
/*      */     implements IntListIterator {
/*      */     private final int element;
/*      */     private byte curr;
/*      */     
/*      */     public SingletonIterator(int element) {
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
/*      */     public int nextInt() {
/*  140 */       if (!hasNext()) throw new NoSuchElementException(); 
/*  141 */       this.curr = 1;
/*  142 */       return this.element;
/*      */     }
/*      */ 
/*      */     
/*      */     public int previousInt() {
/*  147 */       if (!hasPrevious()) throw new NoSuchElementException(); 
/*  148 */       this.curr = 0;
/*  149 */       return this.element;
/*      */     }
/*      */ 
/*      */     
/*      */     public void forEachRemaining(IntConsumer action) {
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
/*      */   public static IntListIterator singleton(int element) {
/*  195 */     return new SingletonIterator(element);
/*      */   }
/*      */   
/*      */   private static class ArrayIterator implements IntListIterator {
/*      */     private final int[] array;
/*      */     private final int offset;
/*      */     private final int length;
/*      */     private int curr;
/*      */     
/*      */     public ArrayIterator(int[] array, int offset, int length) {
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
/*      */     public int nextInt() {
/*  222 */       if (!hasNext()) throw new NoSuchElementException(); 
/*  223 */       return this.array[this.offset + this.curr++];
/*      */     }
/*      */ 
/*      */     
/*      */     public int previousInt() {
/*  228 */       if (!hasPrevious()) throw new NoSuchElementException(); 
/*  229 */       return this.array[this.offset + --this.curr];
/*      */     }
/*      */ 
/*      */     
/*      */     public void forEachRemaining(IntConsumer action) {
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
/*      */   public static IntListIterator wrap(int[] array, int offset, int length) {
/*  290 */     IntArrays.ensureOffsetLength(array, offset, length);
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
/*      */   public static IntListIterator wrap(int[] array) {
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
/*      */   public static int unwrap(IntIterator i, int[] array, int offset, int max) {
/*  324 */     if (max < 0) throw new IllegalArgumentException("The maximum number of elements (" + max + ") is negative"); 
/*  325 */     if (offset < 0 || offset + max > array.length) throw new IllegalArgumentException(); 
/*  326 */     int j = max;
/*  327 */     for (; j-- != 0 && i.hasNext(); array[offset++] = i.nextInt());
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
/*      */   public static int unwrap(IntIterator i, int[] array) {
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
/*      */   public static int[] unwrap(IntIterator i, int max) {
/*  360 */     if (max < 0) throw new IllegalArgumentException("The maximum number of elements (" + max + ") is negative"); 
/*  361 */     int[] array = new int[16];
/*  362 */     int j = 0;
/*  363 */     while (max-- != 0 && i.hasNext()) {
/*  364 */       if (j == array.length) array = IntArrays.grow(array, j + 1); 
/*  365 */       array[j++] = i.nextInt();
/*      */     } 
/*  367 */     return IntArrays.trim(array, j);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static int[] unwrap(IntIterator i) {
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
/*      */   public static long unwrap(IntIterator i, int[][] array, long offset, long max) {
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
/*      */     //   51: invokestatic length : ([[I)J
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
/*      */     //   98: invokeinterface nextInt : ()I
/*      */     //   103: invokestatic set : ([[IJI)V
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
/*      */     //   0	117	0	i	Lit/unimi/dsi/fastutil/ints/IntIterator;
/*      */     //   0	117	1	array	[[I
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
/*      */   public static long unwrap(IntIterator i, int[][] array) {
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
/*      */   public static int unwrap(IntIterator i, IntCollection c, int max) {
/*  440 */     if (max < 0) throw new IllegalArgumentException("The maximum number of elements (" + max + ") is negative"); 
/*  441 */     int j = max;
/*  442 */     for (; j-- != 0 && i.hasNext(); c.add(i.nextInt()));
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
/*      */   public static int[][] unwrapBig(IntIterator i, long max) {
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
/*      */     //   41: invokestatic newBigArray : (J)[[I
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
/*      */     //   70: invokestatic length : ([[I)J
/*      */     //   73: lcmp
/*      */     //   74: ifne -> 86
/*      */     //   77: aload_3
/*      */     //   78: lload #4
/*      */     //   80: lconst_1
/*      */     //   81: ladd
/*      */     //   82: invokestatic grow : ([[IJ)[[I
/*      */     //   85: astore_3
/*      */     //   86: aload_3
/*      */     //   87: lload #4
/*      */     //   89: dup2
/*      */     //   90: lconst_1
/*      */     //   91: ladd
/*      */     //   92: lstore #4
/*      */     //   94: aload_0
/*      */     //   95: invokeinterface nextInt : ()I
/*      */     //   100: invokestatic set : ([[IJI)V
/*      */     //   103: goto -> 48
/*      */     //   106: aload_3
/*      */     //   107: lload #4
/*      */     //   109: invokestatic trim : ([[IJ)[[I
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
/*      */     //   0	113	0	i	Lit/unimi/dsi/fastutil/ints/IntIterator;
/*      */     //   0	113	1	max	J
/*      */     //   45	68	3	array	[[I
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
/*      */   public static int[][] unwrapBig(IntIterator i) {
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
/*      */   public static long unwrap(IntIterator i, IntCollection c) {
/*  498 */     long n = 0L;
/*  499 */     while (i.hasNext()) {
/*  500 */       c.add(i.nextInt());
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
/*      */   public static int pour(IntIterator i, IntCollection s, int max) {
/*  521 */     if (max < 0) throw new IllegalArgumentException("The maximum number of elements (" + max + ") is negative"); 
/*  522 */     int j = max;
/*  523 */     for (; j-- != 0 && i.hasNext(); s.add(i.nextInt()));
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
/*      */   public static int pour(IntIterator i, IntCollection s) {
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
/*      */   public static IntList pour(IntIterator i, int max) {
/*  558 */     IntArrayList l = new IntArrayList();
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
/*      */   public static IntList pour(IntIterator i) {
/*  576 */     return pour(i, 2147483647);
/*      */   }
/*      */   
/*      */   private static class IteratorWrapper implements IntIterator {
/*      */     final Iterator<Integer> i;
/*      */     
/*      */     public IteratorWrapper(Iterator<Integer> i) {
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
/*      */     public int nextInt() {
/*  598 */       return ((Integer)this.i.next()).intValue();
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public void forEachRemaining(IntConsumer action) {
/*  605 */       this.i.forEachRemaining(action);
/*      */     }
/*      */ 
/*      */ 
/*      */     
/*      */     public void forEachRemaining(IntConsumer action) {
/*  611 */       Objects.requireNonNull(action);
/*  612 */       Objects.requireNonNull(action); this.i.forEachRemaining((action instanceof Consumer) ? (Consumer<? super Integer>)action : action::accept);
/*      */     }
/*      */ 
/*      */     
/*      */     @Deprecated
/*      */     public void forEachRemaining(Consumer<? super Integer> action) {
/*  618 */       this.i.forEachRemaining(action);
/*      */     }
/*      */   }
/*      */   
/*      */   private static class PrimitiveIteratorWrapper implements IntIterator {
/*      */     final PrimitiveIterator.OfInt i;
/*      */     
/*      */     public PrimitiveIteratorWrapper(PrimitiveIterator.OfInt i) {
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
/*      */     public int nextInt() {
/*  641 */       return this.i.nextInt();
/*      */     }
/*      */ 
/*      */     
/*      */     public void forEachRemaining(IntConsumer action) {
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
/*      */   public static IntIterator asIntIterator(Iterator<Integer> i) {
/*  667 */     if (i instanceof IntIterator) return (IntIterator)i; 
/*  668 */     if (i instanceof PrimitiveIterator.OfInt) return new PrimitiveIteratorWrapper((PrimitiveIterator.OfInt)i); 
/*  669 */     return new IteratorWrapper(i);
/*      */   }
/*      */   
/*      */   private static class ListIteratorWrapper implements IntListIterator {
/*      */     final ListIterator<Integer> i;
/*      */     
/*      */     public ListIteratorWrapper(ListIterator<Integer> i) {
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
/*      */     public void set(int k) {
/*  701 */       this.i.set(Integer.valueOf(k));
/*      */     }
/*      */ 
/*      */     
/*      */     public void add(int k) {
/*  706 */       this.i.add(Integer.valueOf(k));
/*      */     }
/*      */ 
/*      */     
/*      */     public void remove() {
/*  711 */       this.i.remove();
/*      */     }
/*      */ 
/*      */     
/*      */     public int nextInt() {
/*  716 */       return ((Integer)this.i.next()).intValue();
/*      */     }
/*      */ 
/*      */     
/*      */     public int previousInt() {
/*  721 */       return ((Integer)this.i.previous()).intValue();
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public void forEachRemaining(IntConsumer action) {
/*  728 */       this.i.forEachRemaining(action);
/*      */     }
/*      */ 
/*      */ 
/*      */     
/*      */     public void forEachRemaining(IntConsumer action) {
/*  734 */       Objects.requireNonNull(action);
/*  735 */       Objects.requireNonNull(action); this.i.forEachRemaining((action instanceof Consumer) ? (Consumer<? super Integer>)action : action::accept);
/*      */     }
/*      */ 
/*      */     
/*      */     @Deprecated
/*      */     public void forEachRemaining(Consumer<? super Integer> action) {
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
/*      */   public static IntListIterator asIntIterator(ListIterator<Integer> i) {
/*  762 */     if (i instanceof IntListIterator) return (IntListIterator)i; 
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
/*      */   public static boolean any(IntIterator iterator, IntPredicate predicate) {
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
/*      */   public static boolean all(IntIterator iterator, IntPredicate predicate) {
/*  787 */     Objects.requireNonNull(predicate);
/*      */     while (true) {
/*  789 */       if (!iterator.hasNext()) return true; 
/*  790 */       if (!predicate.test(iterator.nextInt())) {
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
/*      */   public static int indexOf(IntIterator iterator, IntPredicate predicate) {
/*  806 */     Objects.requireNonNull(predicate);
/*  807 */     for (int i = 0; iterator.hasNext(); i++) {
/*  808 */       if (predicate.test(iterator.nextInt())) return i; 
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
/*      */     extends AbstractIntIterator
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
/*      */     public int nextInt() {
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
/*      */     public void forEachRemaining(IntConsumer action) {
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
/*      */     protected abstract int get(int param1Int);
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
/*      */     implements IntListIterator
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
/*      */     protected abstract void add(int param1Int1, int param1Int2);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     protected abstract void set(int param1Int1, int param1Int2);
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
/*      */     public int previousInt() {
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
/*      */     public void add(int k) {
/* 1025 */       add(this.pos++, k);
/* 1026 */       this.lastReturned = -1;
/*      */     }
/*      */ 
/*      */     
/*      */     public void set(int k) {
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
/*      */   private static class IntervalIterator implements IntListIterator {
/*      */     private final int from;
/*      */     private final int to;
/*      */     int curr;
/*      */     
/*      */     public IntervalIterator(int from, int to) {
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
/*      */     public int nextInt() {
/* 1073 */       if (!hasNext()) throw new NoSuchElementException(); 
/* 1074 */       return this.curr++;
/*      */     }
/*      */ 
/*      */     
/*      */     public int previousInt() {
/* 1079 */       if (!hasPrevious()) throw new NoSuchElementException(); 
/* 1080 */       return --this.curr;
/*      */     }
/*      */ 
/*      */     
/*      */     public void forEachRemaining(IntConsumer action) {
/* 1085 */       Objects.requireNonNull(action);
/* 1086 */       for (; this.curr < this.to; this.curr++) {
/* 1087 */         action.accept(this.curr);
/*      */       }
/*      */     }
/*      */ 
/*      */     
/*      */     public int nextIndex() {
/* 1093 */       return this.curr - this.from;
/*      */     }
/*      */ 
/*      */     
/*      */     public int previousIndex() {
/* 1098 */       return this.curr - this.from - 1;
/*      */     }
/*      */ 
/*      */     
/*      */     public int skip(int n) {
/* 1103 */       if (n < 0) throw new IllegalArgumentException("Argument must be nonnegative: " + n); 
/* 1104 */       if (this.curr + n <= this.to) {
/* 1105 */         this.curr += n;
/* 1106 */         return n;
/*      */       } 
/* 1108 */       n = this.to - this.curr;
/* 1109 */       this.curr = this.to;
/* 1110 */       return n;
/*      */     }
/*      */ 
/*      */     
/*      */     public int back(int n) {
/* 1115 */       if (this.curr - n >= this.from) {
/* 1116 */         this.curr -= n;
/* 1117 */         return n;
/*      */       } 
/* 1119 */       n = this.curr - this.from;
/* 1120 */       this.curr = this.from;
/* 1121 */       return n;
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
/*      */   public static IntListIterator fromTo(int from, int to) {
/* 1137 */     return new IntervalIterator(from, to);
/*      */   }
/*      */   
/*      */   private static class IteratorConcatenator implements IntIterator {
/*      */     final IntIterator[] a;
/* 1142 */     int lastOffset = -1; int offset; int length;
/*      */     
/*      */     public IteratorConcatenator(IntIterator[] a, int offset, int length) {
/* 1145 */       this.a = a;
/* 1146 */       this.offset = offset;
/* 1147 */       this.length = length;
/* 1148 */       advance();
/*      */     }
/*      */     
/*      */     private void advance() {
/* 1152 */       while (this.length != 0 && 
/* 1153 */         !this.a[this.offset].hasNext()) {
/* 1154 */         this.length--;
/* 1155 */         this.offset++;
/*      */       } 
/*      */     }
/*      */ 
/*      */ 
/*      */     
/*      */     public boolean hasNext() {
/* 1162 */       return (this.length > 0);
/*      */     }
/*      */ 
/*      */     
/*      */     public int nextInt() {
/* 1167 */       if (!hasNext()) throw new NoSuchElementException(); 
/* 1168 */       int next = this.a[this.lastOffset = this.offset].nextInt();
/* 1169 */       advance();
/* 1170 */       return next;
/*      */     }
/*      */ 
/*      */     
/*      */     public void forEachRemaining(IntConsumer action) {
/* 1175 */       while (this.length > 0) {
/* 1176 */         this.a[this.lastOffset = this.offset].forEachRemaining(action);
/* 1177 */         advance();
/*      */       } 
/*      */     }
/*      */ 
/*      */     
/*      */     @Deprecated
/*      */     public void forEachRemaining(Consumer<? super Integer> action) {
/* 1184 */       while (this.length > 0) {
/* 1185 */         this.a[this.lastOffset = this.offset].forEachRemaining(action);
/* 1186 */         advance();
/*      */       } 
/*      */     }
/*      */ 
/*      */     
/*      */     public void remove() {
/* 1192 */       if (this.lastOffset == -1) throw new IllegalStateException(); 
/* 1193 */       this.a[this.lastOffset].remove();
/*      */     }
/*      */ 
/*      */     
/*      */     public int skip(int n) {
/* 1198 */       if (n < 0) throw new IllegalArgumentException("Argument must be nonnegative: " + n); 
/* 1199 */       this.lastOffset = -1;
/* 1200 */       int skipped = 0;
/* 1201 */       while (skipped < n && this.length != 0) {
/* 1202 */         skipped += this.a[this.offset].skip(n - skipped);
/* 1203 */         if (this.a[this.offset].hasNext())
/* 1204 */           break;  this.length--;
/* 1205 */         this.offset++;
/*      */       } 
/* 1207 */       return skipped;
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
/*      */   public static IntIterator concat(IntIterator... a) {
/* 1222 */     return concat(a, 0, a.length);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static IntIterator concat(IntIterator[] a, int offset, int length) {
/* 1240 */     return new IteratorConcatenator(a, offset, length);
/*      */   }
/*      */   
/*      */   public static class UnmodifiableIterator
/*      */     implements IntIterator {
/*      */     protected final IntIterator i;
/*      */     
/*      */     public UnmodifiableIterator(IntIterator i) {
/* 1248 */       this.i = i;
/*      */     }
/*      */ 
/*      */     
/*      */     public boolean hasNext() {
/* 1253 */       return this.i.hasNext();
/*      */     }
/*      */ 
/*      */     
/*      */     public int nextInt() {
/* 1258 */       return this.i.nextInt();
/*      */     }
/*      */ 
/*      */     
/*      */     public void forEachRemaining(IntConsumer action) {
/* 1263 */       this.i.forEachRemaining(action);
/*      */     }
/*      */ 
/*      */     
/*      */     @Deprecated
/*      */     public void forEachRemaining(Consumer<? super Integer> action) {
/* 1269 */       this.i.forEachRemaining(action);
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static IntIterator unmodifiable(IntIterator i) {
/* 1280 */     return new UnmodifiableIterator(i);
/*      */   }
/*      */   
/*      */   public static class UnmodifiableBidirectionalIterator
/*      */     implements IntBidirectionalIterator {
/*      */     protected final IntBidirectionalIterator i;
/*      */     
/*      */     public UnmodifiableBidirectionalIterator(IntBidirectionalIterator i) {
/* 1288 */       this.i = i;
/*      */     }
/*      */ 
/*      */     
/*      */     public boolean hasNext() {
/* 1293 */       return this.i.hasNext();
/*      */     }
/*      */ 
/*      */     
/*      */     public boolean hasPrevious() {
/* 1298 */       return this.i.hasPrevious();
/*      */     }
/*      */ 
/*      */     
/*      */     public int nextInt() {
/* 1303 */       return this.i.nextInt();
/*      */     }
/*      */ 
/*      */     
/*      */     public int previousInt() {
/* 1308 */       return this.i.previousInt();
/*      */     }
/*      */ 
/*      */     
/*      */     public void forEachRemaining(IntConsumer action) {
/* 1313 */       this.i.forEachRemaining(action);
/*      */     }
/*      */ 
/*      */     
/*      */     @Deprecated
/*      */     public void forEachRemaining(Consumer<? super Integer> action) {
/* 1319 */       this.i.forEachRemaining(action);
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static IntBidirectionalIterator unmodifiable(IntBidirectionalIterator i) {
/* 1330 */     return new UnmodifiableBidirectionalIterator(i);
/*      */   }
/*      */   
/*      */   public static class UnmodifiableListIterator
/*      */     implements IntListIterator {
/*      */     protected final IntListIterator i;
/*      */     
/*      */     public UnmodifiableListIterator(IntListIterator i) {
/* 1338 */       this.i = i;
/*      */     }
/*      */ 
/*      */     
/*      */     public boolean hasNext() {
/* 1343 */       return this.i.hasNext();
/*      */     }
/*      */ 
/*      */     
/*      */     public boolean hasPrevious() {
/* 1348 */       return this.i.hasPrevious();
/*      */     }
/*      */ 
/*      */     
/*      */     public int nextInt() {
/* 1353 */       return this.i.nextInt();
/*      */     }
/*      */ 
/*      */     
/*      */     public int previousInt() {
/* 1358 */       return this.i.previousInt();
/*      */     }
/*      */ 
/*      */     
/*      */     public int nextIndex() {
/* 1363 */       return this.i.nextIndex();
/*      */     }
/*      */ 
/*      */     
/*      */     public int previousIndex() {
/* 1368 */       return this.i.previousIndex();
/*      */     }
/*      */ 
/*      */     
/*      */     public void forEachRemaining(IntConsumer action) {
/* 1373 */       this.i.forEachRemaining(action);
/*      */     }
/*      */ 
/*      */     
/*      */     @Deprecated
/*      */     public void forEachRemaining(Consumer<? super Integer> action) {
/* 1379 */       this.i.forEachRemaining(action);
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static IntListIterator unmodifiable(IntListIterator i) {
/* 1390 */     return new UnmodifiableListIterator(i);
/*      */   }
/*      */   
/*      */   private static final class ByteIteratorWrapper
/*      */     implements IntIterator {
/*      */     final ByteIterator iterator;
/*      */     
/*      */     public ByteIteratorWrapper(ByteIterator iterator) {
/* 1398 */       this.iterator = iterator;
/*      */     }
/*      */ 
/*      */     
/*      */     public boolean hasNext() {
/* 1403 */       return this.iterator.hasNext();
/*      */     }
/*      */ 
/*      */     
/*      */     @Deprecated
/*      */     public Integer next() {
/* 1409 */       return Integer.valueOf(this.iterator.nextByte());
/*      */     }
/*      */ 
/*      */     
/*      */     public int nextInt() {
/* 1414 */       return this.iterator.nextByte();
/*      */     }
/*      */ 
/*      */     
/*      */     public void forEachRemaining(IntConsumer action) {
/* 1419 */       Objects.requireNonNull(action);
/* 1420 */       Objects.requireNonNull(action); this.iterator.forEachRemaining(action::accept);
/*      */     }
/*      */ 
/*      */     
/*      */     public void remove() {
/* 1425 */       this.iterator.remove();
/*      */     }
/*      */ 
/*      */     
/*      */     public int skip(int n) {
/* 1430 */       return this.iterator.skip(n);
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static IntIterator wrap(ByteIterator iterator) {
/* 1441 */     return new ByteIteratorWrapper(iterator);
/*      */   }
/*      */   
/*      */   private static final class ShortIteratorWrapper
/*      */     implements IntIterator {
/*      */     final ShortIterator iterator;
/*      */     
/*      */     public ShortIteratorWrapper(ShortIterator iterator) {
/* 1449 */       this.iterator = iterator;
/*      */     }
/*      */ 
/*      */     
/*      */     public boolean hasNext() {
/* 1454 */       return this.iterator.hasNext();
/*      */     }
/*      */ 
/*      */     
/*      */     @Deprecated
/*      */     public Integer next() {
/* 1460 */       return Integer.valueOf(this.iterator.nextShort());
/*      */     }
/*      */ 
/*      */     
/*      */     public int nextInt() {
/* 1465 */       return this.iterator.nextShort();
/*      */     }
/*      */ 
/*      */     
/*      */     public void forEachRemaining(IntConsumer action) {
/* 1470 */       Objects.requireNonNull(action);
/* 1471 */       Objects.requireNonNull(action); this.iterator.forEachRemaining(action::accept);
/*      */     }
/*      */ 
/*      */     
/*      */     public void remove() {
/* 1476 */       this.iterator.remove();
/*      */     }
/*      */ 
/*      */     
/*      */     public int skip(int n) {
/* 1481 */       return this.iterator.skip(n);
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static IntIterator wrap(ShortIterator iterator) {
/* 1492 */     return new ShortIteratorWrapper(iterator);
/*      */   }
/*      */   
/*      */   private static final class CharIteratorWrapper
/*      */     implements IntIterator {
/*      */     final CharIterator iterator;
/*      */     
/*      */     public CharIteratorWrapper(CharIterator iterator) {
/* 1500 */       this.iterator = iterator;
/*      */     }
/*      */ 
/*      */     
/*      */     public boolean hasNext() {
/* 1505 */       return this.iterator.hasNext();
/*      */     }
/*      */ 
/*      */     
/*      */     @Deprecated
/*      */     public Integer next() {
/* 1511 */       return Integer.valueOf(this.iterator.nextChar());
/*      */     }
/*      */ 
/*      */     
/*      */     public int nextInt() {
/* 1516 */       return this.iterator.nextChar();
/*      */     }
/*      */ 
/*      */     
/*      */     public void forEachRemaining(IntConsumer action) {
/* 1521 */       Objects.requireNonNull(action);
/* 1522 */       Objects.requireNonNull(action); this.iterator.forEachRemaining(action::accept);
/*      */     }
/*      */ 
/*      */     
/*      */     public void remove() {
/* 1527 */       this.iterator.remove();
/*      */     }
/*      */ 
/*      */     
/*      */     public int skip(int n) {
/* 1532 */       return this.iterator.skip(n);
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
/*      */   public static IntIterator wrap(CharIterator iterator) {
/* 1550 */     return new CharIteratorWrapper(iterator);
/*      */   }
/*      */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\i\\unimi\dsi\fastutil\ints\IntIterators.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */