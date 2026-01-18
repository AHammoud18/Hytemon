/*      */ package it.unimi.dsi.fastutil.objects;
/*      */ 
/*      */ import it.unimi.dsi.fastutil.BigArrays;
/*      */ import java.io.Serializable;
/*      */ import java.util.Iterator;
/*      */ import java.util.ListIterator;
/*      */ import java.util.NoSuchElementException;
/*      */ import java.util.Objects;
/*      */ import java.util.function.Consumer;
/*      */ import java.util.function.Predicate;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ public final class ObjectIterators
/*      */ {
/*      */   public static class EmptyIterator<K>
/*      */     implements ObjectListIterator<K>, Serializable, Cloneable
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
/*      */     public K next() {
/*   62 */       throw new NoSuchElementException();
/*      */     }
/*      */ 
/*      */     
/*      */     public K previous() {
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
/*      */     public void forEachRemaining(Consumer<? super K> action) {}
/*      */ 
/*      */     
/*      */     public Object clone() {
/*   96 */       return ObjectIterators.EMPTY_ITERATOR;
/*      */     }
/*      */     
/*      */     private Object readResolve() {
/*  100 */       return ObjectIterators.EMPTY_ITERATOR;
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
/*  112 */   public static final EmptyIterator EMPTY_ITERATOR = new EmptyIterator();
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static <K> ObjectIterator<K> emptyIterator() {
/*  128 */     return EMPTY_ITERATOR;
/*      */   }
/*      */   
/*      */   private static class SingletonIterator<K>
/*      */     implements ObjectListIterator<K> {
/*      */     private final K element;
/*      */     private byte curr;
/*      */     
/*      */     public SingletonIterator(K element) {
/*  137 */       this.element = element;
/*      */     }
/*      */ 
/*      */     
/*      */     public boolean hasNext() {
/*  142 */       return (this.curr == 0);
/*      */     }
/*      */ 
/*      */     
/*      */     public boolean hasPrevious() {
/*  147 */       return (this.curr == 1);
/*      */     }
/*      */ 
/*      */     
/*      */     public K next() {
/*  152 */       if (!hasNext()) throw new NoSuchElementException(); 
/*  153 */       this.curr = 1;
/*  154 */       return this.element;
/*      */     }
/*      */ 
/*      */     
/*      */     public K previous() {
/*  159 */       if (!hasPrevious()) throw new NoSuchElementException(); 
/*  160 */       this.curr = 0;
/*  161 */       return this.element;
/*      */     }
/*      */ 
/*      */     
/*      */     public void forEachRemaining(Consumer<? super K> action) {
/*  166 */       Objects.requireNonNull(action);
/*  167 */       if (this.curr == 0) {
/*  168 */         action.accept(this.element);
/*  169 */         this.curr = 1;
/*      */       } 
/*      */     }
/*      */ 
/*      */     
/*      */     public int nextIndex() {
/*  175 */       return this.curr;
/*      */     }
/*      */ 
/*      */     
/*      */     public int previousIndex() {
/*  180 */       return this.curr - 1;
/*      */     }
/*      */ 
/*      */     
/*      */     public int back(int n) {
/*  185 */       if (n < 0) throw new IllegalArgumentException("Argument must be nonnegative: " + n); 
/*  186 */       if (n == 0 || this.curr < 1) return 0; 
/*  187 */       this.curr = 1;
/*  188 */       return 1;
/*      */     }
/*      */ 
/*      */     
/*      */     public int skip(int n) {
/*  193 */       if (n < 0) throw new IllegalArgumentException("Argument must be nonnegative: " + n); 
/*  194 */       if (n == 0 || this.curr > 0) return 0; 
/*  195 */       this.curr = 0;
/*  196 */       return 1;
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static <K> ObjectListIterator<K> singleton(K element) {
/*  207 */     return new SingletonIterator<>(element);
/*      */   }
/*      */   
/*      */   private static class ArrayIterator<K> implements ObjectListIterator<K> {
/*      */     private final K[] array;
/*      */     private final int offset;
/*      */     private final int length;
/*      */     private int curr;
/*      */     
/*      */     public ArrayIterator(K[] array, int offset, int length) {
/*  217 */       this.array = array;
/*  218 */       this.offset = offset;
/*  219 */       this.length = length;
/*      */     }
/*      */ 
/*      */     
/*      */     public boolean hasNext() {
/*  224 */       return (this.curr < this.length);
/*      */     }
/*      */ 
/*      */     
/*      */     public boolean hasPrevious() {
/*  229 */       return (this.curr > 0);
/*      */     }
/*      */ 
/*      */     
/*      */     public K next() {
/*  234 */       if (!hasNext()) throw new NoSuchElementException(); 
/*  235 */       return this.array[this.offset + this.curr++];
/*      */     }
/*      */ 
/*      */     
/*      */     public K previous() {
/*  240 */       if (!hasPrevious()) throw new NoSuchElementException(); 
/*  241 */       return this.array[this.offset + --this.curr];
/*      */     }
/*      */ 
/*      */     
/*      */     public void forEachRemaining(Consumer<? super K> action) {
/*  246 */       Objects.requireNonNull(action);
/*  247 */       for (; this.curr < this.length; this.curr++) {
/*  248 */         action.accept(this.array[this.offset + this.curr]);
/*      */       }
/*      */     }
/*      */ 
/*      */     
/*      */     public int skip(int n) {
/*  254 */       if (n < 0) throw new IllegalArgumentException("Argument must be nonnegative: " + n); 
/*  255 */       if (n <= this.length - this.curr) {
/*  256 */         this.curr += n;
/*  257 */         return n;
/*      */       } 
/*  259 */       n = this.length - this.curr;
/*  260 */       this.curr = this.length;
/*  261 */       return n;
/*      */     }
/*      */ 
/*      */     
/*      */     public int back(int n) {
/*  266 */       if (n < 0) throw new IllegalArgumentException("Argument must be nonnegative: " + n); 
/*  267 */       if (n <= this.curr) {
/*  268 */         this.curr -= n;
/*  269 */         return n;
/*      */       } 
/*  271 */       n = this.curr;
/*  272 */       this.curr = 0;
/*  273 */       return n;
/*      */     }
/*      */ 
/*      */     
/*      */     public int nextIndex() {
/*  278 */       return this.curr;
/*      */     }
/*      */ 
/*      */     
/*      */     public int previousIndex() {
/*  283 */       return this.curr - 1;
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
/*      */   public static <K> ObjectListIterator<K> wrap(K[] array, int offset, int length) {
/*  302 */     ObjectArrays.ensureOffsetLength(array, offset, length);
/*  303 */     return new ArrayIterator<>(array, offset, length);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static <K> ObjectListIterator<K> wrap(K[] array) {
/*  317 */     return new ArrayIterator<>(array, 0, array.length);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static <K> int unwrap(Iterator<? extends K> i, K[] array, int offset, int max) {
/*  336 */     if (max < 0) throw new IllegalArgumentException("The maximum number of elements (" + max + ") is negative"); 
/*  337 */     if (offset < 0 || offset + max > array.length) throw new IllegalArgumentException(); 
/*  338 */     int j = max;
/*  339 */     for (; j-- != 0 && i.hasNext(); array[offset++] = i.next());
/*  340 */     return max - j - 1;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static <K> int unwrap(Iterator<? extends K> i, K[] array) {
/*  356 */     return unwrap(i, array, 0, array.length);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static <K> K[] unwrap(Iterator<? extends K> i, int max) {
/*  372 */     if (max < 0) throw new IllegalArgumentException("The maximum number of elements (" + max + ") is negative"); 
/*  373 */     K[] array = (K[])new Object[16];
/*  374 */     int j = 0;
/*  375 */     while (max-- != 0 && i.hasNext()) {
/*  376 */       if (j == array.length) array = ObjectArrays.grow(array, j + 1); 
/*  377 */       array[j++] = i.next();
/*      */     } 
/*  379 */     return ObjectArrays.trim(array, j);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static <K> K[] unwrap(Iterator<? extends K> i) {
/*  393 */     return unwrap(i, 2147483647);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static <K> long unwrap(Iterator<? extends K> i, K[][] array, long offset, long max) {
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
/*      */     //   51: invokestatic length : ([[Ljava/lang/Object;)J
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
/*      */     //   98: invokeinterface next : ()Ljava/lang/Object;
/*      */     //   103: invokestatic set : ([[Ljava/lang/Object;JLjava/lang/Object;)V
/*      */     //   106: goto -> 70
/*      */     //   109: lload #4
/*      */     //   111: lload #6
/*      */     //   113: lsub
/*      */     //   114: lconst_1
/*      */     //   115: lsub
/*      */     //   116: lreturn
/*      */     // Line number table:
/*      */     //   Java source line number -> byte code offset
/*      */     //   #412	-> 0
/*      */     //   #413	-> 40
/*      */     //   #414	-> 66
/*      */     //   #415	-> 70
/*      */     //   #416	-> 109
/*      */     // Local variable table:
/*      */     //   start	length	slot	name	descriptor
/*      */     //   0	117	0	i	Ljava/util/Iterator;
/*      */     //   0	117	1	array	[[Ljava/lang/Object;
/*      */     //   0	117	2	offset	J
/*      */     //   0	117	4	max	J
/*      */     //   70	47	6	j	J
/*      */     // Local variable type table:
/*      */     //   start	length	slot	name	signature
/*      */     //   0	117	0	i	Ljava/util/Iterator<+TK;>;
/*      */     //   0	117	1	array	[[TK;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static <K> long unwrap(Iterator<? extends K> i, K[][] array) {
/*  432 */     return unwrap(i, array, 0L, BigArrays.length((Object[][])array));
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static <K> int unwrap(Iterator<K> i, ObjectCollection<? super K> c, int max) {
/*  452 */     if (max < 0) throw new IllegalArgumentException("The maximum number of elements (" + max + ") is negative"); 
/*  453 */     int j = max;
/*  454 */     for (; j-- != 0 && i.hasNext(); c.add(i.next()));
/*  455 */     return max - j - 1;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static <K> K[][] unwrapBig(Iterator<? extends K> i, long max) {
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
/*      */     //   41: invokestatic newBigArray : (J)[[Ljava/lang/Object;
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
/*      */     //   70: invokestatic length : ([[Ljava/lang/Object;)J
/*      */     //   73: lcmp
/*      */     //   74: ifne -> 86
/*      */     //   77: aload_3
/*      */     //   78: lload #4
/*      */     //   80: lconst_1
/*      */     //   81: ladd
/*      */     //   82: invokestatic grow : ([[Ljava/lang/Object;J)[[Ljava/lang/Object;
/*      */     //   85: astore_3
/*      */     //   86: aload_3
/*      */     //   87: lload #4
/*      */     //   89: dup2
/*      */     //   90: lconst_1
/*      */     //   91: ladd
/*      */     //   92: lstore #4
/*      */     //   94: aload_0
/*      */     //   95: invokeinterface next : ()Ljava/lang/Object;
/*      */     //   100: invokestatic set : ([[Ljava/lang/Object;JLjava/lang/Object;)V
/*      */     //   103: goto -> 48
/*      */     //   106: aload_3
/*      */     //   107: lload #4
/*      */     //   109: invokestatic trim : ([[Ljava/lang/Object;J)[[Ljava/lang/Object;
/*      */     //   112: areturn
/*      */     // Line number table:
/*      */     //   Java source line number -> byte code offset
/*      */     //   #471	-> 0
/*      */     //   #472	-> 38
/*      */     //   #473	-> 45
/*      */     //   #474	-> 48
/*      */     //   #475	-> 67
/*      */     //   #476	-> 86
/*      */     //   #478	-> 106
/*      */     // Local variable table:
/*      */     //   start	length	slot	name	descriptor
/*      */     //   0	113	0	i	Ljava/util/Iterator;
/*      */     //   0	113	1	max	J
/*      */     //   45	68	3	array	[[Ljava/lang/Object;
/*      */     //   48	65	4	j	J
/*      */     // Local variable type table:
/*      */     //   start	length	slot	name	signature
/*      */     //   0	113	0	i	Ljava/util/Iterator<+TK;>;
/*      */     //   45	68	3	array	[[TK;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static <K> K[][] unwrapBig(Iterator<? extends K> i) {
/*  492 */     return unwrapBig(i, Long.MAX_VALUE);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static <K> long unwrap(Iterator<K> i, ObjectCollection<? super K> c) {
/*  510 */     long n = 0L;
/*  511 */     while (i.hasNext()) {
/*  512 */       c.add(i.next());
/*  513 */       n++;
/*      */     } 
/*  515 */     return n;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static <K> int pour(Iterator<K> i, ObjectCollection<? super K> s, int max) {
/*  533 */     if (max < 0) throw new IllegalArgumentException("The maximum number of elements (" + max + ") is negative"); 
/*  534 */     int j = max;
/*  535 */     for (; j-- != 0 && i.hasNext(); s.add(i.next()));
/*  536 */     return max - j - 1;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static <K> int pour(Iterator<K> i, ObjectCollection<? super K> s) {
/*  553 */     return pour(i, s, 2147483647);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static <K> ObjectList<K> pour(Iterator<K> i, int max) {
/*  570 */     ObjectArrayList<K> l = new ObjectArrayList<>();
/*  571 */     pour(i, l, max);
/*  572 */     l.trim();
/*  573 */     return l;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static <K> ObjectList<K> pour(Iterator<K> i) {
/*  588 */     return pour(i, 2147483647);
/*      */   }
/*      */   
/*      */   private static class IteratorWrapper<K> implements ObjectIterator<K> {
/*      */     final Iterator<K> i;
/*      */     
/*      */     public IteratorWrapper(Iterator<K> i) {
/*  595 */       this.i = i;
/*      */     }
/*      */ 
/*      */     
/*      */     public boolean hasNext() {
/*  600 */       return this.i.hasNext();
/*      */     }
/*      */ 
/*      */     
/*      */     public void remove() {
/*  605 */       this.i.remove();
/*      */     }
/*      */ 
/*      */     
/*      */     public K next() {
/*  610 */       return this.i.next();
/*      */     }
/*      */ 
/*      */     
/*      */     public void forEachRemaining(Consumer<? super K> action) {
/*  615 */       this.i.forEachRemaining(action);
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
/*      */   public static <K> ObjectIterator<K> asObjectIterator(Iterator<K> i) {
/*  635 */     if (i instanceof ObjectIterator) return (ObjectIterator<K>)i; 
/*  636 */     return new IteratorWrapper<>(i);
/*      */   }
/*      */   
/*      */   private static class ListIteratorWrapper<K> implements ObjectListIterator<K> {
/*      */     final ListIterator<K> i;
/*      */     
/*      */     public ListIteratorWrapper(ListIterator<K> i) {
/*  643 */       this.i = i;
/*      */     }
/*      */ 
/*      */     
/*      */     public boolean hasNext() {
/*  648 */       return this.i.hasNext();
/*      */     }
/*      */ 
/*      */     
/*      */     public boolean hasPrevious() {
/*  653 */       return this.i.hasPrevious();
/*      */     }
/*      */ 
/*      */     
/*      */     public int nextIndex() {
/*  658 */       return this.i.nextIndex();
/*      */     }
/*      */ 
/*      */     
/*      */     public int previousIndex() {
/*  663 */       return this.i.previousIndex();
/*      */     }
/*      */ 
/*      */     
/*      */     public void set(K k) {
/*  668 */       this.i.set(k);
/*      */     }
/*      */ 
/*      */     
/*      */     public void add(K k) {
/*  673 */       this.i.add(k);
/*      */     }
/*      */ 
/*      */     
/*      */     public void remove() {
/*  678 */       this.i.remove();
/*      */     }
/*      */ 
/*      */     
/*      */     public K next() {
/*  683 */       return this.i.next();
/*      */     }
/*      */ 
/*      */     
/*      */     public K previous() {
/*  688 */       return this.i.previous();
/*      */     }
/*      */ 
/*      */     
/*      */     public void forEachRemaining(Consumer<? super K> action) {
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
/*      */   public static <K> ObjectListIterator<K> asObjectIterator(ListIterator<K> i) {
/*  713 */     if (i instanceof ObjectListIterator) return (ObjectListIterator<K>)i; 
/*  714 */     return new ListIteratorWrapper<>(i);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static <K> boolean any(Iterator<K> iterator, Predicate<? super K> predicate) {
/*  726 */     return (indexOf(iterator, predicate) != -1);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static <K> boolean all(Iterator<K> iterator, Predicate<? super K> predicate) {
/*  738 */     Objects.requireNonNull(predicate);
/*      */     while (true) {
/*  740 */       if (!iterator.hasNext()) return true; 
/*  741 */       if (!predicate.test(iterator.next())) {
/*  742 */         return false;
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
/*      */   public static <K> int indexOf(Iterator<K> iterator, Predicate<? super K> predicate) {
/*  757 */     Objects.requireNonNull(predicate);
/*  758 */     for (int i = 0; iterator.hasNext(); i++) {
/*  759 */       if (predicate.test(iterator.next())) return i; 
/*      */     } 
/*  761 */     return -1;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static abstract class AbstractIndexBasedIterator<K>
/*      */     extends AbstractObjectIterator<K>
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
/*  811 */       this.minPos = minPos;
/*  812 */       this.pos = initialPos;
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
/*      */     protected abstract K get(int param1Int);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     protected abstract void remove(int param1Int);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     protected abstract int getMaxPos();
/*      */ 
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
/*  861 */       return (this.pos < getMaxPos());
/*      */     }
/*      */ 
/*      */     
/*      */     public K next() {
/*  866 */       if (!hasNext()) throw new NoSuchElementException(); 
/*  867 */       return get(this.lastReturned = this.pos++);
/*      */     }
/*      */ 
/*      */     
/*      */     public void remove() {
/*  872 */       if (this.lastReturned == -1) throw new IllegalStateException(); 
/*  873 */       remove(this.lastReturned);
/*      */       
/*  875 */       if (this.lastReturned < this.pos) this.pos--; 
/*  876 */       this.lastReturned = -1;
/*      */     }
/*      */ 
/*      */     
/*      */     public void forEachRemaining(Consumer<? super K> action) {
/*  881 */       while (this.pos < getMaxPos()) {
/*  882 */         action.accept(get(this.lastReturned = this.pos++));
/*      */       }
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public int skip(int n) {
/*  890 */       if (n < 0) throw new IllegalArgumentException("Argument must be nonnegative: " + n); 
/*  891 */       int max = getMaxPos();
/*  892 */       int remaining = max - this.pos;
/*  893 */       if (n < remaining) {
/*  894 */         this.pos += n;
/*      */       } else {
/*  896 */         n = remaining;
/*  897 */         this.pos = max;
/*      */       } 
/*  899 */       this.lastReturned = this.pos - 1;
/*  900 */       return n;
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
/*      */   public static abstract class AbstractIndexBasedListIterator<K>
/*      */     extends AbstractIndexBasedIterator<K>
/*      */     implements ObjectListIterator<K>
/*      */   {
/*      */     protected AbstractIndexBasedListIterator(int minPos, int initialPos) {
/*  922 */       super(minPos, initialPos);
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
/*      */     protected abstract void add(int param1Int, K param1K);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     protected abstract void set(int param1Int, K param1K);
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
/*  955 */       return (this.pos > this.minPos);
/*      */     }
/*      */ 
/*      */     
/*      */     public K previous() {
/*  960 */       if (!hasPrevious()) throw new NoSuchElementException(); 
/*  961 */       return get(this.lastReturned = --this.pos);
/*      */     }
/*      */ 
/*      */     
/*      */     public int nextIndex() {
/*  966 */       return this.pos;
/*      */     }
/*      */ 
/*      */     
/*      */     public int previousIndex() {
/*  971 */       return this.pos - 1;
/*      */     }
/*      */ 
/*      */     
/*      */     public void add(K k) {
/*  976 */       add(this.pos++, k);
/*  977 */       this.lastReturned = -1;
/*      */     }
/*      */ 
/*      */     
/*      */     public void set(K k) {
/*  982 */       if (this.lastReturned == -1) throw new IllegalStateException(); 
/*  983 */       set(this.lastReturned, k);
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public int back(int n) {
/*  990 */       if (n < 0) throw new IllegalArgumentException("Argument must be nonnegative: " + n); 
/*  991 */       int remaining = this.pos - this.minPos;
/*  992 */       if (n < remaining) {
/*  993 */         this.pos -= n;
/*      */       } else {
/*  995 */         n = remaining;
/*  996 */         this.pos = this.minPos;
/*      */       } 
/*  998 */       this.lastReturned = this.pos;
/*  999 */       return n;
/*      */     }
/*      */   }
/*      */   
/*      */   private static class IteratorConcatenator<K> implements ObjectIterator<K> {
/*      */     final ObjectIterator<? extends K>[] a;
/* 1005 */     int lastOffset = -1; int offset;
/*      */     
/*      */     public IteratorConcatenator(ObjectIterator<? extends K>[] a, int offset, int length) {
/* 1008 */       this.a = a;
/* 1009 */       this.offset = offset;
/* 1010 */       this.length = length;
/* 1011 */       advance();
/*      */     }
/*      */     int length;
/*      */     private void advance() {
/* 1015 */       while (this.length != 0 && 
/* 1016 */         !this.a[this.offset].hasNext()) {
/* 1017 */         this.length--;
/* 1018 */         this.offset++;
/*      */       } 
/*      */     }
/*      */ 
/*      */ 
/*      */     
/*      */     public boolean hasNext() {
/* 1025 */       return (this.length > 0);
/*      */     }
/*      */ 
/*      */     
/*      */     public K next() {
/* 1030 */       if (!hasNext()) throw new NoSuchElementException(); 
/* 1031 */       K next = this.a[this.lastOffset = this.offset].next();
/* 1032 */       advance();
/* 1033 */       return next;
/*      */     }
/*      */ 
/*      */     
/*      */     public void forEachRemaining(Consumer<? super K> action) {
/* 1038 */       while (this.length > 0) {
/* 1039 */         this.a[this.lastOffset = this.offset].forEachRemaining(action);
/* 1040 */         advance();
/*      */       } 
/*      */     }
/*      */ 
/*      */     
/*      */     public void remove() {
/* 1046 */       if (this.lastOffset == -1) throw new IllegalStateException(); 
/* 1047 */       this.a[this.lastOffset].remove();
/*      */     }
/*      */ 
/*      */     
/*      */     public int skip(int n) {
/* 1052 */       if (n < 0) throw new IllegalArgumentException("Argument must be nonnegative: " + n); 
/* 1053 */       this.lastOffset = -1;
/* 1054 */       int skipped = 0;
/* 1055 */       while (skipped < n && this.length != 0) {
/* 1056 */         skipped += this.a[this.offset].skip(n - skipped);
/* 1057 */         if (this.a[this.offset].hasNext())
/* 1058 */           break;  this.length--;
/* 1059 */         this.offset++;
/*      */       } 
/* 1061 */       return skipped;
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
/*      */   @SafeVarargs
/*      */   public static <K> ObjectIterator<K> concat(ObjectIterator<? extends K>... a) {
/* 1077 */     return concat(a, 0, a.length);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static <K> ObjectIterator<K> concat(ObjectIterator<? extends K>[] a, int offset, int length) {
/* 1095 */     return new IteratorConcatenator<>(a, offset, length);
/*      */   }
/*      */   
/*      */   public static class UnmodifiableIterator<K>
/*      */     implements ObjectIterator<K> {
/*      */     protected final ObjectIterator<? extends K> i;
/*      */     
/*      */     public UnmodifiableIterator(ObjectIterator<? extends K> i) {
/* 1103 */       this.i = i;
/*      */     }
/*      */ 
/*      */     
/*      */     public boolean hasNext() {
/* 1108 */       return this.i.hasNext();
/*      */     }
/*      */ 
/*      */     
/*      */     public K next() {
/* 1113 */       return this.i.next();
/*      */     }
/*      */ 
/*      */     
/*      */     public void forEachRemaining(Consumer<? super K> action) {
/* 1118 */       this.i.forEachRemaining(action);
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static <K> ObjectIterator<K> unmodifiable(ObjectIterator<? extends K> i) {
/* 1129 */     return new UnmodifiableIterator<>(i);
/*      */   }
/*      */   
/*      */   public static class UnmodifiableBidirectionalIterator<K>
/*      */     implements ObjectBidirectionalIterator<K> {
/*      */     protected final ObjectBidirectionalIterator<? extends K> i;
/*      */     
/*      */     public UnmodifiableBidirectionalIterator(ObjectBidirectionalIterator<? extends K> i) {
/* 1137 */       this.i = i;
/*      */     }
/*      */ 
/*      */     
/*      */     public boolean hasNext() {
/* 1142 */       return this.i.hasNext();
/*      */     }
/*      */ 
/*      */     
/*      */     public boolean hasPrevious() {
/* 1147 */       return this.i.hasPrevious();
/*      */     }
/*      */ 
/*      */     
/*      */     public K next() {
/* 1152 */       return this.i.next();
/*      */     }
/*      */ 
/*      */     
/*      */     public K previous() {
/* 1157 */       return (K)this.i.previous();
/*      */     }
/*      */ 
/*      */     
/*      */     public void forEachRemaining(Consumer<? super K> action) {
/* 1162 */       this.i.forEachRemaining(action);
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static <K> ObjectBidirectionalIterator<K> unmodifiable(ObjectBidirectionalIterator<? extends K> i) {
/* 1173 */     return new UnmodifiableBidirectionalIterator<>(i);
/*      */   }
/*      */   
/*      */   public static class UnmodifiableListIterator<K>
/*      */     implements ObjectListIterator<K> {
/*      */     protected final ObjectListIterator<? extends K> i;
/*      */     
/*      */     public UnmodifiableListIterator(ObjectListIterator<? extends K> i) {
/* 1181 */       this.i = i;
/*      */     }
/*      */ 
/*      */     
/*      */     public boolean hasNext() {
/* 1186 */       return this.i.hasNext();
/*      */     }
/*      */ 
/*      */     
/*      */     public boolean hasPrevious() {
/* 1191 */       return this.i.hasPrevious();
/*      */     }
/*      */ 
/*      */     
/*      */     public K next() {
/* 1196 */       return this.i.next();
/*      */     }
/*      */ 
/*      */     
/*      */     public K previous() {
/* 1201 */       return (K)this.i.previous();
/*      */     }
/*      */ 
/*      */     
/*      */     public int nextIndex() {
/* 1206 */       return this.i.nextIndex();
/*      */     }
/*      */ 
/*      */     
/*      */     public int previousIndex() {
/* 1211 */       return this.i.previousIndex();
/*      */     }
/*      */ 
/*      */     
/*      */     public void forEachRemaining(Consumer<? super K> action) {
/* 1216 */       this.i.forEachRemaining(action);
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static <K> ObjectListIterator<K> unmodifiable(ObjectListIterator<? extends K> i) {
/* 1227 */     return new UnmodifiableListIterator<>(i);
/*      */   }
/*      */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\i\\unimi\dsi\fastutil\objects\ObjectIterators.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */