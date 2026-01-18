/*      */ package it.unimi.dsi.fastutil.booleans;
/*      */ 
/*      */ import it.unimi.dsi.fastutil.BigArrays;
/*      */ import java.io.Serializable;
/*      */ import java.util.Iterator;
/*      */ import java.util.ListIterator;
/*      */ import java.util.NoSuchElementException;
/*      */ import java.util.Objects;
/*      */ import java.util.function.Consumer;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ public final class BooleanIterators
/*      */ {
/*      */   public static class EmptyIterator
/*      */     implements BooleanListIterator, Serializable, Cloneable
/*      */   {
/*      */     private static final long serialVersionUID = -7046029254386353129L;
/*      */     
/*      */     public boolean hasNext() {
/*   51 */       return false;
/*      */     }
/*      */ 
/*      */     
/*      */     public boolean hasPrevious() {
/*   56 */       return false;
/*      */     }
/*      */ 
/*      */     
/*      */     public boolean nextBoolean() {
/*   61 */       throw new NoSuchElementException();
/*      */     }
/*      */ 
/*      */     
/*      */     public boolean previousBoolean() {
/*   66 */       throw new NoSuchElementException();
/*      */     }
/*      */ 
/*      */     
/*      */     public int nextIndex() {
/*   71 */       return 0;
/*      */     }
/*      */ 
/*      */     
/*      */     public int previousIndex() {
/*   76 */       return -1;
/*      */     }
/*      */ 
/*      */     
/*      */     public int skip(int n) {
/*   81 */       return 0;
/*      */     }
/*      */ 
/*      */     
/*      */     public int back(int n) {
/*   86 */       return 0;
/*      */     }
/*      */ 
/*      */ 
/*      */     
/*      */     public void forEachRemaining(BooleanConsumer action) {}
/*      */ 
/*      */ 
/*      */     
/*      */     @Deprecated
/*      */     public void forEachRemaining(Consumer<? super Boolean> action) {}
/*      */ 
/*      */     
/*      */     public Object clone() {
/*  100 */       return BooleanIterators.EMPTY_ITERATOR;
/*      */     }
/*      */     
/*      */     private Object readResolve() {
/*  104 */       return BooleanIterators.EMPTY_ITERATOR;
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
/*  116 */   public static final EmptyIterator EMPTY_ITERATOR = new EmptyIterator();
/*      */   
/*      */   private static class SingletonIterator
/*      */     implements BooleanListIterator {
/*      */     private final boolean element;
/*      */     private byte curr;
/*      */     
/*      */     public SingletonIterator(boolean element) {
/*  124 */       this.element = element;
/*      */     }
/*      */ 
/*      */     
/*      */     public boolean hasNext() {
/*  129 */       return (this.curr == 0);
/*      */     }
/*      */ 
/*      */     
/*      */     public boolean hasPrevious() {
/*  134 */       return (this.curr == 1);
/*      */     }
/*      */ 
/*      */     
/*      */     public boolean nextBoolean() {
/*  139 */       if (!hasNext()) throw new NoSuchElementException(); 
/*  140 */       this.curr = 1;
/*  141 */       return this.element;
/*      */     }
/*      */ 
/*      */     
/*      */     public boolean previousBoolean() {
/*  146 */       if (!hasPrevious()) throw new NoSuchElementException(); 
/*  147 */       this.curr = 0;
/*  148 */       return this.element;
/*      */     }
/*      */ 
/*      */     
/*      */     public void forEachRemaining(BooleanConsumer action) {
/*  153 */       Objects.requireNonNull(action);
/*  154 */       if (this.curr == 0) {
/*  155 */         action.accept(this.element);
/*  156 */         this.curr = 1;
/*      */       } 
/*      */     }
/*      */ 
/*      */     
/*      */     public int nextIndex() {
/*  162 */       return this.curr;
/*      */     }
/*      */ 
/*      */     
/*      */     public int previousIndex() {
/*  167 */       return this.curr - 1;
/*      */     }
/*      */ 
/*      */     
/*      */     public int back(int n) {
/*  172 */       if (n < 0) throw new IllegalArgumentException("Argument must be nonnegative: " + n); 
/*  173 */       if (n == 0 || this.curr < 1) return 0; 
/*  174 */       this.curr = 1;
/*  175 */       return 1;
/*      */     }
/*      */ 
/*      */     
/*      */     public int skip(int n) {
/*  180 */       if (n < 0) throw new IllegalArgumentException("Argument must be nonnegative: " + n); 
/*  181 */       if (n == 0 || this.curr > 0) return 0; 
/*  182 */       this.curr = 0;
/*  183 */       return 1;
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static BooleanListIterator singleton(boolean element) {
/*  194 */     return new SingletonIterator(element);
/*      */   }
/*      */   
/*      */   private static class ArrayIterator implements BooleanListIterator {
/*      */     private final boolean[] array;
/*      */     private final int offset;
/*      */     private final int length;
/*      */     private int curr;
/*      */     
/*      */     public ArrayIterator(boolean[] array, int offset, int length) {
/*  204 */       this.array = array;
/*  205 */       this.offset = offset;
/*  206 */       this.length = length;
/*      */     }
/*      */ 
/*      */     
/*      */     public boolean hasNext() {
/*  211 */       return (this.curr < this.length);
/*      */     }
/*      */ 
/*      */     
/*      */     public boolean hasPrevious() {
/*  216 */       return (this.curr > 0);
/*      */     }
/*      */ 
/*      */     
/*      */     public boolean nextBoolean() {
/*  221 */       if (!hasNext()) throw new NoSuchElementException(); 
/*  222 */       return this.array[this.offset + this.curr++];
/*      */     }
/*      */ 
/*      */     
/*      */     public boolean previousBoolean() {
/*  227 */       if (!hasPrevious()) throw new NoSuchElementException(); 
/*  228 */       return this.array[this.offset + --this.curr];
/*      */     }
/*      */ 
/*      */     
/*      */     public void forEachRemaining(BooleanConsumer action) {
/*  233 */       Objects.requireNonNull(action);
/*  234 */       for (; this.curr < this.length; this.curr++) {
/*  235 */         action.accept(this.array[this.offset + this.curr]);
/*      */       }
/*      */     }
/*      */ 
/*      */     
/*      */     public int skip(int n) {
/*  241 */       if (n < 0) throw new IllegalArgumentException("Argument must be nonnegative: " + n); 
/*  242 */       if (n <= this.length - this.curr) {
/*  243 */         this.curr += n;
/*  244 */         return n;
/*      */       } 
/*  246 */       n = this.length - this.curr;
/*  247 */       this.curr = this.length;
/*  248 */       return n;
/*      */     }
/*      */ 
/*      */     
/*      */     public int back(int n) {
/*  253 */       if (n < 0) throw new IllegalArgumentException("Argument must be nonnegative: " + n); 
/*  254 */       if (n <= this.curr) {
/*  255 */         this.curr -= n;
/*  256 */         return n;
/*      */       } 
/*  258 */       n = this.curr;
/*  259 */       this.curr = 0;
/*  260 */       return n;
/*      */     }
/*      */ 
/*      */     
/*      */     public int nextIndex() {
/*  265 */       return this.curr;
/*      */     }
/*      */ 
/*      */     
/*      */     public int previousIndex() {
/*  270 */       return this.curr - 1;
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
/*      */   public static BooleanListIterator wrap(boolean[] array, int offset, int length) {
/*  289 */     BooleanArrays.ensureOffsetLength(array, offset, length);
/*  290 */     return new ArrayIterator(array, offset, length);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static BooleanListIterator wrap(boolean[] array) {
/*  304 */     return new ArrayIterator(array, 0, array.length);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static int unwrap(BooleanIterator i, boolean[] array, int offset, int max) {
/*  323 */     if (max < 0) throw new IllegalArgumentException("The maximum number of elements (" + max + ") is negative"); 
/*  324 */     if (offset < 0 || offset + max > array.length) throw new IllegalArgumentException(); 
/*  325 */     int j = max;
/*  326 */     for (; j-- != 0 && i.hasNext(); array[offset++] = i.nextBoolean());
/*  327 */     return max - j - 1;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static int unwrap(BooleanIterator i, boolean[] array) {
/*  343 */     return unwrap(i, array, 0, array.length);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static boolean[] unwrap(BooleanIterator i, int max) {
/*  359 */     if (max < 0) throw new IllegalArgumentException("The maximum number of elements (" + max + ") is negative"); 
/*  360 */     boolean[] array = new boolean[16];
/*  361 */     int j = 0;
/*  362 */     while (max-- != 0 && i.hasNext()) {
/*  363 */       if (j == array.length) array = BooleanArrays.grow(array, j + 1); 
/*  364 */       array[j++] = i.nextBoolean();
/*      */     } 
/*  366 */     return BooleanArrays.trim(array, j);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static boolean[] unwrap(BooleanIterator i) {
/*  380 */     return unwrap(i, 2147483647);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static long unwrap(BooleanIterator i, boolean[][] array, long offset, long max) {
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
/*      */     //   51: invokestatic length : ([[Z)J
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
/*      */     //   98: invokeinterface nextBoolean : ()Z
/*      */     //   103: invokestatic set : ([[ZJZ)V
/*      */     //   106: goto -> 70
/*      */     //   109: lload #4
/*      */     //   111: lload #6
/*      */     //   113: lsub
/*      */     //   114: lconst_1
/*      */     //   115: lsub
/*      */     //   116: lreturn
/*      */     // Line number table:
/*      */     //   Java source line number -> byte code offset
/*      */     //   #399	-> 0
/*      */     //   #400	-> 40
/*      */     //   #401	-> 66
/*      */     //   #402	-> 70
/*      */     //   #403	-> 109
/*      */     // Local variable table:
/*      */     //   start	length	slot	name	descriptor
/*      */     //   0	117	0	i	Lit/unimi/dsi/fastutil/booleans/BooleanIterator;
/*      */     //   0	117	1	array	[[Z
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
/*      */   public static long unwrap(BooleanIterator i, boolean[][] array) {
/*  419 */     return unwrap(i, array, 0L, BigArrays.length(array));
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static int unwrap(BooleanIterator i, BooleanCollection c, int max) {
/*  439 */     if (max < 0) throw new IllegalArgumentException("The maximum number of elements (" + max + ") is negative"); 
/*  440 */     int j = max;
/*  441 */     for (; j-- != 0 && i.hasNext(); c.add(i.nextBoolean()));
/*  442 */     return max - j - 1;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static boolean[][] unwrapBig(BooleanIterator i, long max) {
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
/*      */     //   41: invokestatic newBigArray : (J)[[Z
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
/*      */     //   70: invokestatic length : ([[Z)J
/*      */     //   73: lcmp
/*      */     //   74: ifne -> 86
/*      */     //   77: aload_3
/*      */     //   78: lload #4
/*      */     //   80: lconst_1
/*      */     //   81: ladd
/*      */     //   82: invokestatic grow : ([[ZJ)[[Z
/*      */     //   85: astore_3
/*      */     //   86: aload_3
/*      */     //   87: lload #4
/*      */     //   89: dup2
/*      */     //   90: lconst_1
/*      */     //   91: ladd
/*      */     //   92: lstore #4
/*      */     //   94: aload_0
/*      */     //   95: invokeinterface nextBoolean : ()Z
/*      */     //   100: invokestatic set : ([[ZJZ)V
/*      */     //   103: goto -> 48
/*      */     //   106: aload_3
/*      */     //   107: lload #4
/*      */     //   109: invokestatic trim : ([[ZJ)[[Z
/*      */     //   112: areturn
/*      */     // Line number table:
/*      */     //   Java source line number -> byte code offset
/*      */     //   #458	-> 0
/*      */     //   #459	-> 38
/*      */     //   #460	-> 45
/*      */     //   #461	-> 48
/*      */     //   #462	-> 67
/*      */     //   #463	-> 86
/*      */     //   #465	-> 106
/*      */     // Local variable table:
/*      */     //   start	length	slot	name	descriptor
/*      */     //   0	113	0	i	Lit/unimi/dsi/fastutil/booleans/BooleanIterator;
/*      */     //   0	113	1	max	J
/*      */     //   45	68	3	array	[[Z
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
/*      */   public static boolean[][] unwrapBig(BooleanIterator i) {
/*  479 */     return unwrapBig(i, Long.MAX_VALUE);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static long unwrap(BooleanIterator i, BooleanCollection c) {
/*  497 */     long n = 0L;
/*  498 */     while (i.hasNext()) {
/*  499 */       c.add(i.nextBoolean());
/*  500 */       n++;
/*      */     } 
/*  502 */     return n;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static int pour(BooleanIterator i, BooleanCollection s, int max) {
/*  520 */     if (max < 0) throw new IllegalArgumentException("The maximum number of elements (" + max + ") is negative"); 
/*  521 */     int j = max;
/*  522 */     for (; j-- != 0 && i.hasNext(); s.add(i.nextBoolean()));
/*  523 */     return max - j - 1;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static int pour(BooleanIterator i, BooleanCollection s) {
/*  540 */     return pour(i, s, 2147483647);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static BooleanList pour(BooleanIterator i, int max) {
/*  557 */     BooleanArrayList l = new BooleanArrayList();
/*  558 */     pour(i, l, max);
/*  559 */     l.trim();
/*  560 */     return l;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static BooleanList pour(BooleanIterator i) {
/*  575 */     return pour(i, 2147483647);
/*      */   }
/*      */   
/*      */   private static class IteratorWrapper implements BooleanIterator {
/*      */     final Iterator<Boolean> i;
/*      */     
/*      */     public IteratorWrapper(Iterator<Boolean> i) {
/*  582 */       this.i = i;
/*      */     }
/*      */ 
/*      */     
/*      */     public boolean hasNext() {
/*  587 */       return this.i.hasNext();
/*      */     }
/*      */ 
/*      */     
/*      */     public void remove() {
/*  592 */       this.i.remove();
/*      */     }
/*      */ 
/*      */     
/*      */     public boolean nextBoolean() {
/*  597 */       return ((Boolean)this.i.next()).booleanValue();
/*      */     }
/*      */ 
/*      */     
/*      */     public void forEachRemaining(BooleanConsumer action) {
/*  602 */       this.i.forEachRemaining(action);
/*      */     }
/*      */ 
/*      */     
/*      */     @Deprecated
/*      */     public void forEachRemaining(Consumer<? super Boolean> action) {
/*  608 */       this.i.forEachRemaining(action);
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
/*      */   public static BooleanIterator asBooleanIterator(Iterator<Boolean> i) {
/*  629 */     if (i instanceof BooleanIterator) return (BooleanIterator)i; 
/*  630 */     return new IteratorWrapper(i);
/*      */   }
/*      */   
/*      */   private static class ListIteratorWrapper implements BooleanListIterator {
/*      */     final ListIterator<Boolean> i;
/*      */     
/*      */     public ListIteratorWrapper(ListIterator<Boolean> i) {
/*  637 */       this.i = i;
/*      */     }
/*      */ 
/*      */     
/*      */     public boolean hasNext() {
/*  642 */       return this.i.hasNext();
/*      */     }
/*      */ 
/*      */     
/*      */     public boolean hasPrevious() {
/*  647 */       return this.i.hasPrevious();
/*      */     }
/*      */ 
/*      */     
/*      */     public int nextIndex() {
/*  652 */       return this.i.nextIndex();
/*      */     }
/*      */ 
/*      */     
/*      */     public int previousIndex() {
/*  657 */       return this.i.previousIndex();
/*      */     }
/*      */ 
/*      */     
/*      */     public void set(boolean k) {
/*  662 */       this.i.set(Boolean.valueOf(k));
/*      */     }
/*      */ 
/*      */     
/*      */     public void add(boolean k) {
/*  667 */       this.i.add(Boolean.valueOf(k));
/*      */     }
/*      */ 
/*      */     
/*      */     public void remove() {
/*  672 */       this.i.remove();
/*      */     }
/*      */ 
/*      */     
/*      */     public boolean nextBoolean() {
/*  677 */       return ((Boolean)this.i.next()).booleanValue();
/*      */     }
/*      */ 
/*      */     
/*      */     public boolean previousBoolean() {
/*  682 */       return ((Boolean)this.i.previous()).booleanValue();
/*      */     }
/*      */ 
/*      */     
/*      */     public void forEachRemaining(BooleanConsumer action) {
/*  687 */       this.i.forEachRemaining(action);
/*      */     }
/*      */ 
/*      */     
/*      */     @Deprecated
/*      */     public void forEachRemaining(Consumer<? super Boolean> action) {
/*  693 */       this.i.forEachRemaining(action);
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
/*      */   public static BooleanListIterator asBooleanIterator(ListIterator<Boolean> i) {
/*  714 */     if (i instanceof BooleanListIterator) return (BooleanListIterator)i; 
/*  715 */     return new ListIteratorWrapper(i);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static boolean any(BooleanIterator iterator, BooleanPredicate predicate) {
/*  727 */     return (indexOf(iterator, predicate) != -1);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static boolean all(BooleanIterator iterator, BooleanPredicate predicate) {
/*  739 */     Objects.requireNonNull(predicate);
/*      */     while (true) {
/*  741 */       if (!iterator.hasNext()) return true; 
/*  742 */       if (!predicate.test(iterator.nextBoolean())) {
/*  743 */         return false;
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
/*      */   public static int indexOf(BooleanIterator iterator, BooleanPredicate predicate) {
/*  758 */     Objects.requireNonNull(predicate);
/*  759 */     for (int i = 0; iterator.hasNext(); i++) {
/*  760 */       if (predicate.test(iterator.nextBoolean())) return i; 
/*      */     } 
/*  762 */     return -1;
/*      */   }
/*      */ 
/*      */ 
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
/*      */     extends AbstractBooleanIterator
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
/*  812 */       this.minPos = minPos;
/*  813 */       this.pos = initialPos;
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
/*  862 */       return (this.pos < getMaxPos());
/*      */     }
/*      */ 
/*      */     
/*      */     public boolean nextBoolean() {
/*  867 */       if (!hasNext()) throw new NoSuchElementException(); 
/*  868 */       return get(this.lastReturned = this.pos++);
/*      */     }
/*      */ 
/*      */     
/*      */     public void remove() {
/*  873 */       if (this.lastReturned == -1) throw new IllegalStateException(); 
/*  874 */       remove(this.lastReturned);
/*      */       
/*  876 */       if (this.lastReturned < this.pos) this.pos--; 
/*  877 */       this.lastReturned = -1;
/*      */     }
/*      */ 
/*      */     
/*      */     public void forEachRemaining(BooleanConsumer action) {
/*  882 */       while (this.pos < getMaxPos()) {
/*  883 */         action.accept(get(this.lastReturned = this.pos++));
/*      */       }
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public int skip(int n) {
/*  891 */       if (n < 0) throw new IllegalArgumentException("Argument must be nonnegative: " + n); 
/*  892 */       int max = getMaxPos();
/*  893 */       int remaining = max - this.pos;
/*  894 */       if (n < remaining) {
/*  895 */         this.pos += n;
/*      */       } else {
/*  897 */         n = remaining;
/*  898 */         this.pos = max;
/*      */       } 
/*  900 */       this.lastReturned = this.pos - 1;
/*  901 */       return n;
/*      */     }
/*      */ 
/*      */ 
/*      */     
/*      */     protected abstract boolean get(int param1Int);
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
/*      */     implements BooleanListIterator
/*      */   {
/*      */     protected AbstractIndexBasedListIterator(int minPos, int initialPos) {
/*  923 */       super(minPos, initialPos);
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
/*      */     protected abstract void add(int param1Int, boolean param1Boolean);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     protected abstract void set(int param1Int, boolean param1Boolean);
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
/*  956 */       return (this.pos > this.minPos);
/*      */     }
/*      */ 
/*      */     
/*      */     public boolean previousBoolean() {
/*  961 */       if (!hasPrevious()) throw new NoSuchElementException(); 
/*  962 */       return get(this.lastReturned = --this.pos);
/*      */     }
/*      */ 
/*      */     
/*      */     public int nextIndex() {
/*  967 */       return this.pos;
/*      */     }
/*      */ 
/*      */     
/*      */     public int previousIndex() {
/*  972 */       return this.pos - 1;
/*      */     }
/*      */ 
/*      */     
/*      */     public void add(boolean k) {
/*  977 */       add(this.pos++, k);
/*  978 */       this.lastReturned = -1;
/*      */     }
/*      */ 
/*      */     
/*      */     public void set(boolean k) {
/*  983 */       if (this.lastReturned == -1) throw new IllegalStateException(); 
/*  984 */       set(this.lastReturned, k);
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public int back(int n) {
/*  991 */       if (n < 0) throw new IllegalArgumentException("Argument must be nonnegative: " + n); 
/*  992 */       int remaining = this.pos - this.minPos;
/*  993 */       if (n < remaining) {
/*  994 */         this.pos -= n;
/*      */       } else {
/*  996 */         n = remaining;
/*  997 */         this.pos = this.minPos;
/*      */       } 
/*  999 */       this.lastReturned = this.pos;
/* 1000 */       return n;
/*      */     }
/*      */   }
/*      */   
/*      */   private static class IteratorConcatenator implements BooleanIterator {
/*      */     final BooleanIterator[] a;
/* 1006 */     int lastOffset = -1; int offset;
/*      */     
/*      */     public IteratorConcatenator(BooleanIterator[] a, int offset, int length) {
/* 1009 */       this.a = a;
/* 1010 */       this.offset = offset;
/* 1011 */       this.length = length;
/* 1012 */       advance();
/*      */     }
/*      */     int length;
/*      */     private void advance() {
/* 1016 */       while (this.length != 0 && 
/* 1017 */         !this.a[this.offset].hasNext()) {
/* 1018 */         this.length--;
/* 1019 */         this.offset++;
/*      */       } 
/*      */     }
/*      */ 
/*      */ 
/*      */     
/*      */     public boolean hasNext() {
/* 1026 */       return (this.length > 0);
/*      */     }
/*      */ 
/*      */     
/*      */     public boolean nextBoolean() {
/* 1031 */       if (!hasNext()) throw new NoSuchElementException(); 
/* 1032 */       boolean next = this.a[this.lastOffset = this.offset].nextBoolean();
/* 1033 */       advance();
/* 1034 */       return next;
/*      */     }
/*      */ 
/*      */     
/*      */     public void forEachRemaining(BooleanConsumer action) {
/* 1039 */       while (this.length > 0) {
/* 1040 */         this.a[this.lastOffset = this.offset].forEachRemaining(action);
/* 1041 */         advance();
/*      */       } 
/*      */     }
/*      */ 
/*      */     
/*      */     @Deprecated
/*      */     public void forEachRemaining(Consumer<? super Boolean> action) {
/* 1048 */       while (this.length > 0) {
/* 1049 */         this.a[this.lastOffset = this.offset].forEachRemaining(action);
/* 1050 */         advance();
/*      */       } 
/*      */     }
/*      */ 
/*      */     
/*      */     public void remove() {
/* 1056 */       if (this.lastOffset == -1) throw new IllegalStateException(); 
/* 1057 */       this.a[this.lastOffset].remove();
/*      */     }
/*      */ 
/*      */     
/*      */     public int skip(int n) {
/* 1062 */       if (n < 0) throw new IllegalArgumentException("Argument must be nonnegative: " + n); 
/* 1063 */       this.lastOffset = -1;
/* 1064 */       int skipped = 0;
/* 1065 */       while (skipped < n && this.length != 0) {
/* 1066 */         skipped += this.a[this.offset].skip(n - skipped);
/* 1067 */         if (this.a[this.offset].hasNext())
/* 1068 */           break;  this.length--;
/* 1069 */         this.offset++;
/*      */       } 
/* 1071 */       return skipped;
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
/*      */   public static BooleanIterator concat(BooleanIterator... a) {
/* 1086 */     return concat(a, 0, a.length);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static BooleanIterator concat(BooleanIterator[] a, int offset, int length) {
/* 1104 */     return new IteratorConcatenator(a, offset, length);
/*      */   }
/*      */   
/*      */   public static class UnmodifiableIterator
/*      */     implements BooleanIterator {
/*      */     protected final BooleanIterator i;
/*      */     
/*      */     public UnmodifiableIterator(BooleanIterator i) {
/* 1112 */       this.i = i;
/*      */     }
/*      */ 
/*      */     
/*      */     public boolean hasNext() {
/* 1117 */       return this.i.hasNext();
/*      */     }
/*      */ 
/*      */     
/*      */     public boolean nextBoolean() {
/* 1122 */       return this.i.nextBoolean();
/*      */     }
/*      */ 
/*      */     
/*      */     public void forEachRemaining(BooleanConsumer action) {
/* 1127 */       this.i.forEachRemaining(action);
/*      */     }
/*      */ 
/*      */     
/*      */     @Deprecated
/*      */     public void forEachRemaining(Consumer<? super Boolean> action) {
/* 1133 */       this.i.forEachRemaining(action);
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static BooleanIterator unmodifiable(BooleanIterator i) {
/* 1144 */     return new UnmodifiableIterator(i);
/*      */   }
/*      */   
/*      */   public static class UnmodifiableBidirectionalIterator
/*      */     implements BooleanBidirectionalIterator {
/*      */     protected final BooleanBidirectionalIterator i;
/*      */     
/*      */     public UnmodifiableBidirectionalIterator(BooleanBidirectionalIterator i) {
/* 1152 */       this.i = i;
/*      */     }
/*      */ 
/*      */     
/*      */     public boolean hasNext() {
/* 1157 */       return this.i.hasNext();
/*      */     }
/*      */ 
/*      */     
/*      */     public boolean hasPrevious() {
/* 1162 */       return this.i.hasPrevious();
/*      */     }
/*      */ 
/*      */     
/*      */     public boolean nextBoolean() {
/* 1167 */       return this.i.nextBoolean();
/*      */     }
/*      */ 
/*      */     
/*      */     public boolean previousBoolean() {
/* 1172 */       return this.i.previousBoolean();
/*      */     }
/*      */ 
/*      */     
/*      */     public void forEachRemaining(BooleanConsumer action) {
/* 1177 */       this.i.forEachRemaining(action);
/*      */     }
/*      */ 
/*      */     
/*      */     @Deprecated
/*      */     public void forEachRemaining(Consumer<? super Boolean> action) {
/* 1183 */       this.i.forEachRemaining(action);
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static BooleanBidirectionalIterator unmodifiable(BooleanBidirectionalIterator i) {
/* 1194 */     return new UnmodifiableBidirectionalIterator(i);
/*      */   }
/*      */   
/*      */   public static class UnmodifiableListIterator
/*      */     implements BooleanListIterator {
/*      */     protected final BooleanListIterator i;
/*      */     
/*      */     public UnmodifiableListIterator(BooleanListIterator i) {
/* 1202 */       this.i = i;
/*      */     }
/*      */ 
/*      */     
/*      */     public boolean hasNext() {
/* 1207 */       return this.i.hasNext();
/*      */     }
/*      */ 
/*      */     
/*      */     public boolean hasPrevious() {
/* 1212 */       return this.i.hasPrevious();
/*      */     }
/*      */ 
/*      */     
/*      */     public boolean nextBoolean() {
/* 1217 */       return this.i.nextBoolean();
/*      */     }
/*      */ 
/*      */     
/*      */     public boolean previousBoolean() {
/* 1222 */       return this.i.previousBoolean();
/*      */     }
/*      */ 
/*      */     
/*      */     public int nextIndex() {
/* 1227 */       return this.i.nextIndex();
/*      */     }
/*      */ 
/*      */     
/*      */     public int previousIndex() {
/* 1232 */       return this.i.previousIndex();
/*      */     }
/*      */ 
/*      */     
/*      */     public void forEachRemaining(BooleanConsumer action) {
/* 1237 */       this.i.forEachRemaining(action);
/*      */     }
/*      */ 
/*      */     
/*      */     @Deprecated
/*      */     public void forEachRemaining(Consumer<? super Boolean> action) {
/* 1243 */       this.i.forEachRemaining(action);
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static BooleanListIterator unmodifiable(BooleanListIterator i) {
/* 1254 */     return new UnmodifiableListIterator(i);
/*      */   }
/*      */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\i\\unimi\dsi\fastutil\booleans\BooleanIterators.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */