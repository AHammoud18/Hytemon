/*      */ package it.unimi.dsi.fastutil.chars;
/*      */ 
/*      */ import it.unimi.dsi.fastutil.BigArrays;
/*      */ import it.unimi.dsi.fastutil.SafeMath;
/*      */ import it.unimi.dsi.fastutil.ints.IntIterator;
/*      */ import it.unimi.dsi.fastutil.ints.IntIterators;
/*      */ import java.io.Serializable;
/*      */ import java.util.Iterator;
/*      */ import java.util.ListIterator;
/*      */ import java.util.NoSuchElementException;
/*      */ import java.util.Objects;
/*      */ import java.util.PrimitiveIterator;
/*      */ import java.util.function.Consumer;
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
/*      */ 
/*      */ 
/*      */ 
/*      */ public final class CharIterators
/*      */ {
/*      */   public static class EmptyIterator
/*      */     implements CharListIterator, Serializable, Cloneable
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
/*      */     public char nextChar() {
/*   64 */       throw new NoSuchElementException();
/*      */     }
/*      */ 
/*      */     
/*      */     public char previousChar() {
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
/*      */     public void forEachRemaining(CharConsumer action) {}
/*      */ 
/*      */ 
/*      */     
/*      */     @Deprecated
/*      */     public void forEachRemaining(Consumer<? super Character> action) {}
/*      */ 
/*      */     
/*      */     public Object clone() {
/*  103 */       return CharIterators.EMPTY_ITERATOR;
/*      */     }
/*      */     
/*      */     private Object readResolve() {
/*  107 */       return CharIterators.EMPTY_ITERATOR;
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
/*      */     implements CharListIterator {
/*      */     private final char element;
/*      */     private byte curr;
/*      */     
/*      */     public SingletonIterator(char element) {
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
/*      */     public char nextChar() {
/*  142 */       if (!hasNext()) throw new NoSuchElementException(); 
/*  143 */       this.curr = 1;
/*  144 */       return this.element;
/*      */     }
/*      */ 
/*      */     
/*      */     public char previousChar() {
/*  149 */       if (!hasPrevious()) throw new NoSuchElementException(); 
/*  150 */       this.curr = 0;
/*  151 */       return this.element;
/*      */     }
/*      */ 
/*      */     
/*      */     public void forEachRemaining(CharConsumer action) {
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
/*      */   public static CharListIterator singleton(char element) {
/*  197 */     return new SingletonIterator(element);
/*      */   }
/*      */   
/*      */   private static class ArrayIterator implements CharListIterator {
/*      */     private final char[] array;
/*      */     private final int offset;
/*      */     private final int length;
/*      */     private int curr;
/*      */     
/*      */     public ArrayIterator(char[] array, int offset, int length) {
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
/*      */     public char nextChar() {
/*  224 */       if (!hasNext()) throw new NoSuchElementException(); 
/*  225 */       return this.array[this.offset + this.curr++];
/*      */     }
/*      */ 
/*      */     
/*      */     public char previousChar() {
/*  230 */       if (!hasPrevious()) throw new NoSuchElementException(); 
/*  231 */       return this.array[this.offset + --this.curr];
/*      */     }
/*      */ 
/*      */     
/*      */     public void forEachRemaining(CharConsumer action) {
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
/*      */   public static CharListIterator wrap(char[] array, int offset, int length) {
/*  292 */     CharArrays.ensureOffsetLength(array, offset, length);
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
/*      */   public static CharListIterator wrap(char[] array) {
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
/*      */   public static int unwrap(CharIterator i, char[] array, int offset, int max) {
/*  326 */     if (max < 0) throw new IllegalArgumentException("The maximum number of elements (" + max + ") is negative"); 
/*  327 */     if (offset < 0 || offset + max > array.length) throw new IllegalArgumentException(); 
/*  328 */     int j = max;
/*  329 */     for (; j-- != 0 && i.hasNext(); array[offset++] = i.nextChar());
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
/*      */   public static int unwrap(CharIterator i, char[] array) {
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
/*      */   public static char[] unwrap(CharIterator i, int max) {
/*  362 */     if (max < 0) throw new IllegalArgumentException("The maximum number of elements (" + max + ") is negative"); 
/*  363 */     char[] array = new char[16];
/*  364 */     int j = 0;
/*  365 */     while (max-- != 0 && i.hasNext()) {
/*  366 */       if (j == array.length) array = CharArrays.grow(array, j + 1); 
/*  367 */       array[j++] = i.nextChar();
/*      */     } 
/*  369 */     return CharArrays.trim(array, j);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static char[] unwrap(CharIterator i) {
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
/*      */   public static long unwrap(CharIterator i, char[][] array, long offset, long max) {
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
/*      */     //   51: invokestatic length : ([[C)J
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
/*      */     //   98: invokeinterface nextChar : ()C
/*      */     //   103: invokestatic set : ([[CJC)V
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
/*      */     //   0	117	0	i	Lit/unimi/dsi/fastutil/chars/CharIterator;
/*      */     //   0	117	1	array	[[C
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
/*      */   public static long unwrap(CharIterator i, char[][] array) {
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
/*      */   public static int unwrap(CharIterator i, CharCollection c, int max) {
/*  442 */     if (max < 0) throw new IllegalArgumentException("The maximum number of elements (" + max + ") is negative"); 
/*  443 */     int j = max;
/*  444 */     for (; j-- != 0 && i.hasNext(); c.add(i.nextChar()));
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
/*      */   public static char[][] unwrapBig(CharIterator i, long max) {
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
/*      */     //   41: invokestatic newBigArray : (J)[[C
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
/*      */     //   70: invokestatic length : ([[C)J
/*      */     //   73: lcmp
/*      */     //   74: ifne -> 86
/*      */     //   77: aload_3
/*      */     //   78: lload #4
/*      */     //   80: lconst_1
/*      */     //   81: ladd
/*      */     //   82: invokestatic grow : ([[CJ)[[C
/*      */     //   85: astore_3
/*      */     //   86: aload_3
/*      */     //   87: lload #4
/*      */     //   89: dup2
/*      */     //   90: lconst_1
/*      */     //   91: ladd
/*      */     //   92: lstore #4
/*      */     //   94: aload_0
/*      */     //   95: invokeinterface nextChar : ()C
/*      */     //   100: invokestatic set : ([[CJC)V
/*      */     //   103: goto -> 48
/*      */     //   106: aload_3
/*      */     //   107: lload #4
/*      */     //   109: invokestatic trim : ([[CJ)[[C
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
/*      */     //   0	113	0	i	Lit/unimi/dsi/fastutil/chars/CharIterator;
/*      */     //   0	113	1	max	J
/*      */     //   45	68	3	array	[[C
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
/*      */   public static char[][] unwrapBig(CharIterator i) {
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
/*      */   public static long unwrap(CharIterator i, CharCollection c) {
/*  500 */     long n = 0L;
/*  501 */     while (i.hasNext()) {
/*  502 */       c.add(i.nextChar());
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
/*      */   public static int pour(CharIterator i, CharCollection s, int max) {
/*  523 */     if (max < 0) throw new IllegalArgumentException("The maximum number of elements (" + max + ") is negative"); 
/*  524 */     int j = max;
/*  525 */     for (; j-- != 0 && i.hasNext(); s.add(i.nextChar()));
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
/*      */   public static int pour(CharIterator i, CharCollection s) {
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
/*      */   public static CharList pour(CharIterator i, int max) {
/*  560 */     CharArrayList l = new CharArrayList();
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
/*      */   public static CharList pour(CharIterator i) {
/*  578 */     return pour(i, 2147483647);
/*      */   }
/*      */   
/*      */   private static class IteratorWrapper implements CharIterator {
/*      */     final Iterator<Character> i;
/*      */     
/*      */     public IteratorWrapper(Iterator<Character> i) {
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
/*      */     public char nextChar() {
/*  600 */       return ((Character)this.i.next()).charValue();
/*      */     }
/*      */ 
/*      */     
/*      */     public void forEachRemaining(CharConsumer action) {
/*  605 */       this.i.forEachRemaining(action);
/*      */     }
/*      */ 
/*      */     
/*      */     @Deprecated
/*      */     public void forEachRemaining(Consumer<? super Character> action) {
/*  611 */       this.i.forEachRemaining(action);
/*      */     }
/*      */   }
/*      */   
/*      */   private static class PrimitiveIteratorWrapper implements CharIterator {
/*      */     final PrimitiveIterator.OfInt i;
/*      */     
/*      */     public PrimitiveIteratorWrapper(PrimitiveIterator.OfInt i) {
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
/*      */     public char nextChar() {
/*  634 */       return (char)this.i.nextInt();
/*      */     }
/*      */ 
/*      */     
/*      */     public void forEachRemaining(CharConsumer action) {
/*  639 */       this.i.forEachRemaining(action);
/*      */     }
/*      */   }
/*      */   
/*      */   private static class CheckedPrimitiveIteratorWrapper extends PrimitiveIteratorWrapper {
/*      */     public CheckedPrimitiveIteratorWrapper(PrimitiveIterator.OfInt i) {
/*  645 */       super(i);
/*      */     }
/*      */ 
/*      */     
/*      */     public char nextChar() {
/*  650 */       return SafeMath.safeIntToChar(this.i.nextInt());
/*      */     }
/*      */ 
/*      */     
/*      */     public void forEachRemaining(CharConsumer action) {
/*  655 */       this.i.forEachRemaining(value -> action.accept(SafeMath.safeIntToChar(value)));
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
/*      */   public static CharIterator asCharIterator(Iterator<Character> i) {
/*  678 */     if (i instanceof CharIterator) return (CharIterator)i; 
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
/*      */   public static CharIterator narrow(PrimitiveIterator.OfInt i) {
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
/*      */   public static CharIterator uncheckedNarrow(PrimitiveIterator.OfInt i) {
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
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static IntIterator widen(CharIterator i) {
/*  726 */     return IntIterators.wrap(i);
/*      */   }
/*      */   
/*      */   private static class ListIteratorWrapper implements CharListIterator {
/*      */     final ListIterator<Character> i;
/*      */     
/*      */     public ListIteratorWrapper(ListIterator<Character> i) {
/*  733 */       this.i = i;
/*      */     }
/*      */ 
/*      */     
/*      */     public boolean hasNext() {
/*  738 */       return this.i.hasNext();
/*      */     }
/*      */ 
/*      */     
/*      */     public boolean hasPrevious() {
/*  743 */       return this.i.hasPrevious();
/*      */     }
/*      */ 
/*      */     
/*      */     public int nextIndex() {
/*  748 */       return this.i.nextIndex();
/*      */     }
/*      */ 
/*      */     
/*      */     public int previousIndex() {
/*  753 */       return this.i.previousIndex();
/*      */     }
/*      */ 
/*      */     
/*      */     public void set(char k) {
/*  758 */       this.i.set(Character.valueOf(k));
/*      */     }
/*      */ 
/*      */     
/*      */     public void add(char k) {
/*  763 */       this.i.add(Character.valueOf(k));
/*      */     }
/*      */ 
/*      */     
/*      */     public void remove() {
/*  768 */       this.i.remove();
/*      */     }
/*      */ 
/*      */     
/*      */     public char nextChar() {
/*  773 */       return ((Character)this.i.next()).charValue();
/*      */     }
/*      */ 
/*      */     
/*      */     public char previousChar() {
/*  778 */       return ((Character)this.i.previous()).charValue();
/*      */     }
/*      */ 
/*      */     
/*      */     public void forEachRemaining(CharConsumer action) {
/*  783 */       this.i.forEachRemaining(action);
/*      */     }
/*      */ 
/*      */     
/*      */     @Deprecated
/*      */     public void forEachRemaining(Consumer<? super Character> action) {
/*  789 */       this.i.forEachRemaining(action);
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
/*      */   public static CharListIterator asCharIterator(ListIterator<Character> i) {
/*  810 */     if (i instanceof CharListIterator) return (CharListIterator)i; 
/*  811 */     return new ListIteratorWrapper(i);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static boolean any(CharIterator iterator, CharPredicate predicate) {
/*  823 */     return (indexOf(iterator, predicate) != -1);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static boolean any(CharIterator iterator, IntPredicate predicate) {
/*  836 */     Objects.requireNonNull(predicate); return any(iterator, (predicate instanceof CharPredicate) ? (CharPredicate)predicate : predicate::test);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static boolean all(CharIterator iterator, CharPredicate predicate) {
/*  848 */     Objects.requireNonNull(predicate);
/*      */     while (true) {
/*  850 */       if (!iterator.hasNext()) return true; 
/*  851 */       if (!predicate.test(iterator.nextChar())) {
/*  852 */         return false;
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
/*      */   public static boolean all(CharIterator iterator, IntPredicate predicate) {
/*  867 */     Objects.requireNonNull(predicate); return all(iterator, (predicate instanceof CharPredicate) ? (CharPredicate)predicate : predicate::test);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static int indexOf(CharIterator iterator, CharPredicate predicate) {
/*  882 */     Objects.requireNonNull(predicate);
/*  883 */     for (int i = 0; iterator.hasNext(); i++) {
/*  884 */       if (predicate.test(iterator.nextChar())) return i; 
/*      */     } 
/*  886 */     return -1;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static int indexOf(CharIterator iterator, IntPredicate predicate) {
/*  904 */     Objects.requireNonNull(predicate); return indexOf(iterator, (predicate instanceof CharPredicate) ? (CharPredicate)predicate : predicate::test);
/*      */   }
/*      */ 
/*      */ 
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
/*      */     extends AbstractCharIterator
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
/*  954 */       this.minPos = minPos;
/*  955 */       this.pos = initialPos;
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
/* 1004 */       return (this.pos < getMaxPos());
/*      */     }
/*      */ 
/*      */     
/*      */     public char nextChar() {
/* 1009 */       if (!hasNext()) throw new NoSuchElementException(); 
/* 1010 */       return get(this.lastReturned = this.pos++);
/*      */     }
/*      */ 
/*      */     
/*      */     public void remove() {
/* 1015 */       if (this.lastReturned == -1) throw new IllegalStateException(); 
/* 1016 */       remove(this.lastReturned);
/*      */       
/* 1018 */       if (this.lastReturned < this.pos) this.pos--; 
/* 1019 */       this.lastReturned = -1;
/*      */     }
/*      */ 
/*      */     
/*      */     public void forEachRemaining(CharConsumer action) {
/* 1024 */       while (this.pos < getMaxPos()) {
/* 1025 */         action.accept(get(this.lastReturned = this.pos++));
/*      */       }
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public int skip(int n) {
/* 1033 */       if (n < 0) throw new IllegalArgumentException("Argument must be nonnegative: " + n); 
/* 1034 */       int max = getMaxPos();
/* 1035 */       int remaining = max - this.pos;
/* 1036 */       if (n < remaining) {
/* 1037 */         this.pos += n;
/*      */       } else {
/* 1039 */         n = remaining;
/* 1040 */         this.pos = max;
/*      */       } 
/* 1042 */       this.lastReturned = this.pos - 1;
/* 1043 */       return n;
/*      */     }
/*      */ 
/*      */ 
/*      */     
/*      */     protected abstract char get(int param1Int);
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
/*      */     implements CharListIterator
/*      */   {
/*      */     protected AbstractIndexBasedListIterator(int minPos, int initialPos) {
/* 1065 */       super(minPos, initialPos);
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
/*      */     protected abstract void add(int param1Int, char param1Char);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     protected abstract void set(int param1Int, char param1Char);
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
/* 1098 */       return (this.pos > this.minPos);
/*      */     }
/*      */ 
/*      */     
/*      */     public char previousChar() {
/* 1103 */       if (!hasPrevious()) throw new NoSuchElementException(); 
/* 1104 */       return get(this.lastReturned = --this.pos);
/*      */     }
/*      */ 
/*      */     
/*      */     public int nextIndex() {
/* 1109 */       return this.pos;
/*      */     }
/*      */ 
/*      */     
/*      */     public int previousIndex() {
/* 1114 */       return this.pos - 1;
/*      */     }
/*      */ 
/*      */     
/*      */     public void add(char k) {
/* 1119 */       add(this.pos++, k);
/* 1120 */       this.lastReturned = -1;
/*      */     }
/*      */ 
/*      */     
/*      */     public void set(char k) {
/* 1125 */       if (this.lastReturned == -1) throw new IllegalStateException(); 
/* 1126 */       set(this.lastReturned, k);
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public int back(int n) {
/* 1133 */       if (n < 0) throw new IllegalArgumentException("Argument must be nonnegative: " + n); 
/* 1134 */       int remaining = this.pos - this.minPos;
/* 1135 */       if (n < remaining) {
/* 1136 */         this.pos -= n;
/*      */       } else {
/* 1138 */         n = remaining;
/* 1139 */         this.pos = this.minPos;
/*      */       } 
/* 1141 */       this.lastReturned = this.pos;
/* 1142 */       return n;
/*      */     } }
/*      */   
/*      */   private static class IntervalIterator implements CharListIterator {
/*      */     private final char from;
/*      */     private final char to;
/*      */     char curr;
/*      */     
/*      */     public IntervalIterator(char from, char to) {
/* 1151 */       this.from = this.curr = from;
/* 1152 */       this.to = to;
/*      */     }
/*      */ 
/*      */     
/*      */     public boolean hasNext() {
/* 1157 */       return (this.curr < this.to);
/*      */     }
/*      */ 
/*      */     
/*      */     public boolean hasPrevious() {
/* 1162 */       return (this.curr > this.from);
/*      */     }
/*      */ 
/*      */     
/*      */     public char nextChar() {
/* 1167 */       if (!hasNext()) throw new NoSuchElementException(); 
/* 1168 */       this.curr = (char)(this.curr + 1); return this.curr;
/*      */     }
/*      */ 
/*      */     
/*      */     public char previousChar() {
/* 1173 */       if (!hasPrevious()) throw new NoSuchElementException(); 
/* 1174 */       return this.curr = (char)(this.curr - 1);
/*      */     }
/*      */ 
/*      */     
/*      */     public void forEachRemaining(CharConsumer action) {
/* 1179 */       Objects.requireNonNull(action);
/* 1180 */       for (; this.curr < this.to; this.curr = (char)(this.curr + 1)) {
/* 1181 */         action.accept(this.curr);
/*      */       }
/*      */     }
/*      */ 
/*      */     
/*      */     public int nextIndex() {
/* 1187 */       return this.curr - this.from;
/*      */     }
/*      */ 
/*      */     
/*      */     public int previousIndex() {
/* 1192 */       return this.curr - this.from - 1;
/*      */     }
/*      */ 
/*      */     
/*      */     public int skip(int n) {
/* 1197 */       if (n < 0) throw new IllegalArgumentException("Argument must be nonnegative: " + n); 
/* 1198 */       if (this.curr + n <= this.to) {
/* 1199 */         this.curr = (char)(this.curr + n);
/* 1200 */         return n;
/*      */       } 
/* 1202 */       n = this.to - this.curr;
/* 1203 */       this.curr = this.to;
/* 1204 */       return n;
/*      */     }
/*      */ 
/*      */     
/*      */     public int back(int n) {
/* 1209 */       if (this.curr - n >= this.from) {
/* 1210 */         this.curr = (char)(this.curr - n);
/* 1211 */         return n;
/*      */       } 
/* 1213 */       n = this.curr - this.from;
/* 1214 */       this.curr = this.from;
/* 1215 */       return n;
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
/*      */   public static CharListIterator fromTo(char from, char to) {
/* 1231 */     return new IntervalIterator(from, to);
/*      */   }
/*      */   
/*      */   private static class IteratorConcatenator implements CharIterator {
/*      */     final CharIterator[] a;
/* 1236 */     int lastOffset = -1; int offset; int length;
/*      */     
/*      */     public IteratorConcatenator(CharIterator[] a, int offset, int length) {
/* 1239 */       this.a = a;
/* 1240 */       this.offset = offset;
/* 1241 */       this.length = length;
/* 1242 */       advance();
/*      */     }
/*      */     
/*      */     private void advance() {
/* 1246 */       while (this.length != 0 && 
/* 1247 */         !this.a[this.offset].hasNext()) {
/* 1248 */         this.length--;
/* 1249 */         this.offset++;
/*      */       } 
/*      */     }
/*      */ 
/*      */ 
/*      */     
/*      */     public boolean hasNext() {
/* 1256 */       return (this.length > 0);
/*      */     }
/*      */ 
/*      */     
/*      */     public char nextChar() {
/* 1261 */       if (!hasNext()) throw new NoSuchElementException(); 
/* 1262 */       char next = this.a[this.lastOffset = this.offset].nextChar();
/* 1263 */       advance();
/* 1264 */       return next;
/*      */     }
/*      */ 
/*      */     
/*      */     public void forEachRemaining(CharConsumer action) {
/* 1269 */       while (this.length > 0) {
/* 1270 */         this.a[this.lastOffset = this.offset].forEachRemaining(action);
/* 1271 */         advance();
/*      */       } 
/*      */     }
/*      */ 
/*      */     
/*      */     @Deprecated
/*      */     public void forEachRemaining(Consumer<? super Character> action) {
/* 1278 */       while (this.length > 0) {
/* 1279 */         this.a[this.lastOffset = this.offset].forEachRemaining(action);
/* 1280 */         advance();
/*      */       } 
/*      */     }
/*      */ 
/*      */     
/*      */     public void remove() {
/* 1286 */       if (this.lastOffset == -1) throw new IllegalStateException(); 
/* 1287 */       this.a[this.lastOffset].remove();
/*      */     }
/*      */ 
/*      */     
/*      */     public int skip(int n) {
/* 1292 */       if (n < 0) throw new IllegalArgumentException("Argument must be nonnegative: " + n); 
/* 1293 */       this.lastOffset = -1;
/* 1294 */       int skipped = 0;
/* 1295 */       while (skipped < n && this.length != 0) {
/* 1296 */         skipped += this.a[this.offset].skip(n - skipped);
/* 1297 */         if (this.a[this.offset].hasNext())
/* 1298 */           break;  this.length--;
/* 1299 */         this.offset++;
/*      */       } 
/* 1301 */       return skipped;
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
/*      */   public static CharIterator concat(CharIterator... a) {
/* 1316 */     return concat(a, 0, a.length);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static CharIterator concat(CharIterator[] a, int offset, int length) {
/* 1334 */     return new IteratorConcatenator(a, offset, length);
/*      */   }
/*      */   
/*      */   public static class UnmodifiableIterator
/*      */     implements CharIterator {
/*      */     protected final CharIterator i;
/*      */     
/*      */     public UnmodifiableIterator(CharIterator i) {
/* 1342 */       this.i = i;
/*      */     }
/*      */ 
/*      */     
/*      */     public boolean hasNext() {
/* 1347 */       return this.i.hasNext();
/*      */     }
/*      */ 
/*      */     
/*      */     public char nextChar() {
/* 1352 */       return this.i.nextChar();
/*      */     }
/*      */ 
/*      */     
/*      */     public void forEachRemaining(CharConsumer action) {
/* 1357 */       this.i.forEachRemaining(action);
/*      */     }
/*      */ 
/*      */     
/*      */     @Deprecated
/*      */     public void forEachRemaining(Consumer<? super Character> action) {
/* 1363 */       this.i.forEachRemaining(action);
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static CharIterator unmodifiable(CharIterator i) {
/* 1374 */     return new UnmodifiableIterator(i);
/*      */   }
/*      */   
/*      */   public static class UnmodifiableBidirectionalIterator
/*      */     implements CharBidirectionalIterator {
/*      */     protected final CharBidirectionalIterator i;
/*      */     
/*      */     public UnmodifiableBidirectionalIterator(CharBidirectionalIterator i) {
/* 1382 */       this.i = i;
/*      */     }
/*      */ 
/*      */     
/*      */     public boolean hasNext() {
/* 1387 */       return this.i.hasNext();
/*      */     }
/*      */ 
/*      */     
/*      */     public boolean hasPrevious() {
/* 1392 */       return this.i.hasPrevious();
/*      */     }
/*      */ 
/*      */     
/*      */     public char nextChar() {
/* 1397 */       return this.i.nextChar();
/*      */     }
/*      */ 
/*      */     
/*      */     public char previousChar() {
/* 1402 */       return this.i.previousChar();
/*      */     }
/*      */ 
/*      */     
/*      */     public void forEachRemaining(CharConsumer action) {
/* 1407 */       this.i.forEachRemaining(action);
/*      */     }
/*      */ 
/*      */     
/*      */     @Deprecated
/*      */     public void forEachRemaining(Consumer<? super Character> action) {
/* 1413 */       this.i.forEachRemaining(action);
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static CharBidirectionalIterator unmodifiable(CharBidirectionalIterator i) {
/* 1424 */     return new UnmodifiableBidirectionalIterator(i);
/*      */   }
/*      */   
/*      */   public static class UnmodifiableListIterator
/*      */     implements CharListIterator {
/*      */     protected final CharListIterator i;
/*      */     
/*      */     public UnmodifiableListIterator(CharListIterator i) {
/* 1432 */       this.i = i;
/*      */     }
/*      */ 
/*      */     
/*      */     public boolean hasNext() {
/* 1437 */       return this.i.hasNext();
/*      */     }
/*      */ 
/*      */     
/*      */     public boolean hasPrevious() {
/* 1442 */       return this.i.hasPrevious();
/*      */     }
/*      */ 
/*      */     
/*      */     public char nextChar() {
/* 1447 */       return this.i.nextChar();
/*      */     }
/*      */ 
/*      */     
/*      */     public char previousChar() {
/* 1452 */       return this.i.previousChar();
/*      */     }
/*      */ 
/*      */     
/*      */     public int nextIndex() {
/* 1457 */       return this.i.nextIndex();
/*      */     }
/*      */ 
/*      */     
/*      */     public int previousIndex() {
/* 1462 */       return this.i.previousIndex();
/*      */     }
/*      */ 
/*      */     
/*      */     public void forEachRemaining(CharConsumer action) {
/* 1467 */       this.i.forEachRemaining(action);
/*      */     }
/*      */ 
/*      */     
/*      */     @Deprecated
/*      */     public void forEachRemaining(Consumer<? super Character> action) {
/* 1473 */       this.i.forEachRemaining(action);
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static CharListIterator unmodifiable(CharListIterator i) {
/* 1484 */     return new UnmodifiableListIterator(i);
/*      */   }
/*      */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\i\\unimi\dsi\fastutil\chars\CharIterators.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */