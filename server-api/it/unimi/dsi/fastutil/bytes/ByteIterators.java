/*      */ package it.unimi.dsi.fastutil.bytes;
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
/*      */ public final class ByteIterators
/*      */ {
/*      */   public static class EmptyIterator
/*      */     implements ByteListIterator, Serializable, Cloneable
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
/*      */     public byte nextByte() {
/*   64 */       throw new NoSuchElementException();
/*      */     }
/*      */ 
/*      */     
/*      */     public byte previousByte() {
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
/*      */     public void forEachRemaining(ByteConsumer action) {}
/*      */ 
/*      */ 
/*      */     
/*      */     @Deprecated
/*      */     public void forEachRemaining(Consumer<? super Byte> action) {}
/*      */ 
/*      */     
/*      */     public Object clone() {
/*  103 */       return ByteIterators.EMPTY_ITERATOR;
/*      */     }
/*      */     
/*      */     private Object readResolve() {
/*  107 */       return ByteIterators.EMPTY_ITERATOR;
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
/*      */     implements ByteListIterator {
/*      */     private final byte element;
/*      */     private byte curr;
/*      */     
/*      */     public SingletonIterator(byte element) {
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
/*      */     public byte nextByte() {
/*  142 */       if (!hasNext()) throw new NoSuchElementException(); 
/*  143 */       this.curr = 1;
/*  144 */       return this.element;
/*      */     }
/*      */ 
/*      */     
/*      */     public byte previousByte() {
/*  149 */       if (!hasPrevious()) throw new NoSuchElementException(); 
/*  150 */       this.curr = 0;
/*  151 */       return this.element;
/*      */     }
/*      */ 
/*      */     
/*      */     public void forEachRemaining(ByteConsumer action) {
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
/*      */   public static ByteListIterator singleton(byte element) {
/*  197 */     return new SingletonIterator(element);
/*      */   }
/*      */   
/*      */   private static class ArrayIterator implements ByteListIterator {
/*      */     private final byte[] array;
/*      */     private final int offset;
/*      */     private final int length;
/*      */     private int curr;
/*      */     
/*      */     public ArrayIterator(byte[] array, int offset, int length) {
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
/*      */     public byte nextByte() {
/*  224 */       if (!hasNext()) throw new NoSuchElementException(); 
/*  225 */       return this.array[this.offset + this.curr++];
/*      */     }
/*      */ 
/*      */     
/*      */     public byte previousByte() {
/*  230 */       if (!hasPrevious()) throw new NoSuchElementException(); 
/*  231 */       return this.array[this.offset + --this.curr];
/*      */     }
/*      */ 
/*      */     
/*      */     public void forEachRemaining(ByteConsumer action) {
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
/*      */   public static ByteListIterator wrap(byte[] array, int offset, int length) {
/*  292 */     ByteArrays.ensureOffsetLength(array, offset, length);
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
/*      */   public static ByteListIterator wrap(byte[] array) {
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
/*      */   public static int unwrap(ByteIterator i, byte[] array, int offset, int max) {
/*  326 */     if (max < 0) throw new IllegalArgumentException("The maximum number of elements (" + max + ") is negative"); 
/*  327 */     if (offset < 0 || offset + max > array.length) throw new IllegalArgumentException(); 
/*  328 */     int j = max;
/*  329 */     for (; j-- != 0 && i.hasNext(); array[offset++] = i.nextByte());
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
/*      */   public static int unwrap(ByteIterator i, byte[] array) {
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
/*      */   public static byte[] unwrap(ByteIterator i, int max) {
/*  362 */     if (max < 0) throw new IllegalArgumentException("The maximum number of elements (" + max + ") is negative"); 
/*  363 */     byte[] array = new byte[16];
/*  364 */     int j = 0;
/*  365 */     while (max-- != 0 && i.hasNext()) {
/*  366 */       if (j == array.length) array = ByteArrays.grow(array, j + 1); 
/*  367 */       array[j++] = i.nextByte();
/*      */     } 
/*  369 */     return ByteArrays.trim(array, j);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static byte[] unwrap(ByteIterator i) {
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
/*      */   public static long unwrap(ByteIterator i, byte[][] array, long offset, long max) {
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
/*      */     //   51: invokestatic length : ([[B)J
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
/*      */     //   98: invokeinterface nextByte : ()B
/*      */     //   103: invokestatic set : ([[BJB)V
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
/*      */     //   0	117	0	i	Lit/unimi/dsi/fastutil/bytes/ByteIterator;
/*      */     //   0	117	1	array	[[B
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
/*      */   public static long unwrap(ByteIterator i, byte[][] array) {
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
/*      */   public static int unwrap(ByteIterator i, ByteCollection c, int max) {
/*  442 */     if (max < 0) throw new IllegalArgumentException("The maximum number of elements (" + max + ") is negative"); 
/*  443 */     int j = max;
/*  444 */     for (; j-- != 0 && i.hasNext(); c.add(i.nextByte()));
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
/*      */   public static byte[][] unwrapBig(ByteIterator i, long max) {
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
/*      */     //   41: invokestatic newBigArray : (J)[[B
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
/*      */     //   70: invokestatic length : ([[B)J
/*      */     //   73: lcmp
/*      */     //   74: ifne -> 86
/*      */     //   77: aload_3
/*      */     //   78: lload #4
/*      */     //   80: lconst_1
/*      */     //   81: ladd
/*      */     //   82: invokestatic grow : ([[BJ)[[B
/*      */     //   85: astore_3
/*      */     //   86: aload_3
/*      */     //   87: lload #4
/*      */     //   89: dup2
/*      */     //   90: lconst_1
/*      */     //   91: ladd
/*      */     //   92: lstore #4
/*      */     //   94: aload_0
/*      */     //   95: invokeinterface nextByte : ()B
/*      */     //   100: invokestatic set : ([[BJB)V
/*      */     //   103: goto -> 48
/*      */     //   106: aload_3
/*      */     //   107: lload #4
/*      */     //   109: invokestatic trim : ([[BJ)[[B
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
/*      */     //   0	113	0	i	Lit/unimi/dsi/fastutil/bytes/ByteIterator;
/*      */     //   0	113	1	max	J
/*      */     //   45	68	3	array	[[B
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
/*      */   public static byte[][] unwrapBig(ByteIterator i) {
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
/*      */   public static long unwrap(ByteIterator i, ByteCollection c) {
/*  500 */     long n = 0L;
/*  501 */     while (i.hasNext()) {
/*  502 */       c.add(i.nextByte());
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
/*      */   public static int pour(ByteIterator i, ByteCollection s, int max) {
/*  523 */     if (max < 0) throw new IllegalArgumentException("The maximum number of elements (" + max + ") is negative"); 
/*  524 */     int j = max;
/*  525 */     for (; j-- != 0 && i.hasNext(); s.add(i.nextByte()));
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
/*      */   public static int pour(ByteIterator i, ByteCollection s) {
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
/*      */   public static ByteList pour(ByteIterator i, int max) {
/*  560 */     ByteArrayList l = new ByteArrayList();
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
/*      */   public static ByteList pour(ByteIterator i) {
/*  578 */     return pour(i, 2147483647);
/*      */   }
/*      */   
/*      */   private static class IteratorWrapper implements ByteIterator {
/*      */     final Iterator<Byte> i;
/*      */     
/*      */     public IteratorWrapper(Iterator<Byte> i) {
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
/*      */     public byte nextByte() {
/*  600 */       return ((Byte)this.i.next()).byteValue();
/*      */     }
/*      */ 
/*      */     
/*      */     public void forEachRemaining(ByteConsumer action) {
/*  605 */       this.i.forEachRemaining(action);
/*      */     }
/*      */ 
/*      */     
/*      */     @Deprecated
/*      */     public void forEachRemaining(Consumer<? super Byte> action) {
/*  611 */       this.i.forEachRemaining(action);
/*      */     }
/*      */   }
/*      */   
/*      */   private static class PrimitiveIteratorWrapper implements ByteIterator {
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
/*      */     public byte nextByte() {
/*  634 */       return (byte)this.i.nextInt();
/*      */     }
/*      */ 
/*      */     
/*      */     public void forEachRemaining(ByteConsumer action) {
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
/*      */     public byte nextByte() {
/*  650 */       return SafeMath.safeIntToByte(this.i.nextInt());
/*      */     }
/*      */ 
/*      */     
/*      */     public void forEachRemaining(ByteConsumer action) {
/*  655 */       this.i.forEachRemaining(value -> action.accept(SafeMath.safeIntToByte(value)));
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
/*      */   public static ByteIterator asByteIterator(Iterator<Byte> i) {
/*  678 */     if (i instanceof ByteIterator) return (ByteIterator)i; 
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
/*      */   public static ByteIterator narrow(PrimitiveIterator.OfInt i) {
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
/*      */   public static ByteIterator uncheckedNarrow(PrimitiveIterator.OfInt i) {
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
/*      */   public static IntIterator widen(ByteIterator i) {
/*  719 */     return IntIterators.wrap(i);
/*      */   }
/*      */   
/*      */   private static class ListIteratorWrapper implements ByteListIterator {
/*      */     final ListIterator<Byte> i;
/*      */     
/*      */     public ListIteratorWrapper(ListIterator<Byte> i) {
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
/*      */     public void set(byte k) {
/*  751 */       this.i.set(Byte.valueOf(k));
/*      */     }
/*      */ 
/*      */     
/*      */     public void add(byte k) {
/*  756 */       this.i.add(Byte.valueOf(k));
/*      */     }
/*      */ 
/*      */     
/*      */     public void remove() {
/*  761 */       this.i.remove();
/*      */     }
/*      */ 
/*      */     
/*      */     public byte nextByte() {
/*  766 */       return ((Byte)this.i.next()).byteValue();
/*      */     }
/*      */ 
/*      */     
/*      */     public byte previousByte() {
/*  771 */       return ((Byte)this.i.previous()).byteValue();
/*      */     }
/*      */ 
/*      */     
/*      */     public void forEachRemaining(ByteConsumer action) {
/*  776 */       this.i.forEachRemaining(action);
/*      */     }
/*      */ 
/*      */     
/*      */     @Deprecated
/*      */     public void forEachRemaining(Consumer<? super Byte> action) {
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
/*      */   public static ByteListIterator asByteIterator(ListIterator<Byte> i) {
/*  803 */     if (i instanceof ByteListIterator) return (ByteListIterator)i; 
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
/*      */   public static boolean any(ByteIterator iterator, BytePredicate predicate) {
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
/*      */   public static boolean any(ByteIterator iterator, IntPredicate predicate) {
/*  829 */     Objects.requireNonNull(predicate); return any(iterator, (predicate instanceof BytePredicate) ? (BytePredicate)predicate : predicate::test);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static boolean all(ByteIterator iterator, BytePredicate predicate) {
/*  841 */     Objects.requireNonNull(predicate);
/*      */     while (true) {
/*  843 */       if (!iterator.hasNext()) return true; 
/*  844 */       if (!predicate.test(iterator.nextByte())) {
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
/*      */   public static boolean all(ByteIterator iterator, IntPredicate predicate) {
/*  860 */     Objects.requireNonNull(predicate); return all(iterator, (predicate instanceof BytePredicate) ? (BytePredicate)predicate : predicate::test);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static int indexOf(ByteIterator iterator, BytePredicate predicate) {
/*  875 */     Objects.requireNonNull(predicate);
/*  876 */     for (int i = 0; iterator.hasNext(); i++) {
/*  877 */       if (predicate.test(iterator.nextByte())) return i; 
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
/*      */   public static int indexOf(ByteIterator iterator, IntPredicate predicate) {
/*  897 */     Objects.requireNonNull(predicate); return indexOf(iterator, (predicate instanceof BytePredicate) ? (BytePredicate)predicate : predicate::test);
/*      */   }
/*      */ 
/*      */ 
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
/*      */     extends AbstractByteIterator
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
/*      */     public byte nextByte() {
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
/*      */     public void forEachRemaining(ByteConsumer action) {
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
/*      */     protected abstract byte get(int param1Int);
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
/*      */     implements ByteListIterator
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
/*      */     protected abstract void add(int param1Int, byte param1Byte);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     protected abstract void set(int param1Int, byte param1Byte);
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
/*      */     public byte previousByte() {
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
/*      */     public void add(byte k) {
/* 1112 */       add(this.pos++, k);
/* 1113 */       this.lastReturned = -1;
/*      */     }
/*      */ 
/*      */     
/*      */     public void set(byte k) {
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
/*      */     } }
/*      */   
/*      */   private static class IntervalIterator implements ByteListIterator {
/*      */     private final byte from;
/*      */     private final byte to;
/*      */     byte curr;
/*      */     
/*      */     public IntervalIterator(byte from, byte to) {
/* 1144 */       this.from = this.curr = from;
/* 1145 */       this.to = to;
/*      */     }
/*      */ 
/*      */     
/*      */     public boolean hasNext() {
/* 1150 */       return (this.curr < this.to);
/*      */     }
/*      */ 
/*      */     
/*      */     public boolean hasPrevious() {
/* 1155 */       return (this.curr > this.from);
/*      */     }
/*      */ 
/*      */     
/*      */     public byte nextByte() {
/* 1160 */       if (!hasNext()) throw new NoSuchElementException(); 
/* 1161 */       this.curr = (byte)(this.curr + 1); return this.curr;
/*      */     }
/*      */ 
/*      */     
/*      */     public byte previousByte() {
/* 1166 */       if (!hasPrevious()) throw new NoSuchElementException(); 
/* 1167 */       return this.curr = (byte)(this.curr - 1);
/*      */     }
/*      */ 
/*      */     
/*      */     public void forEachRemaining(ByteConsumer action) {
/* 1172 */       Objects.requireNonNull(action);
/* 1173 */       for (; this.curr < this.to; this.curr = (byte)(this.curr + 1)) {
/* 1174 */         action.accept(this.curr);
/*      */       }
/*      */     }
/*      */ 
/*      */     
/*      */     public int nextIndex() {
/* 1180 */       return this.curr - this.from;
/*      */     }
/*      */ 
/*      */     
/*      */     public int previousIndex() {
/* 1185 */       return this.curr - this.from - 1;
/*      */     }
/*      */ 
/*      */     
/*      */     public int skip(int n) {
/* 1190 */       if (n < 0) throw new IllegalArgumentException("Argument must be nonnegative: " + n); 
/* 1191 */       if (this.curr + n <= this.to) {
/* 1192 */         this.curr = (byte)(this.curr + n);
/* 1193 */         return n;
/*      */       } 
/* 1195 */       n = this.to - this.curr;
/* 1196 */       this.curr = this.to;
/* 1197 */       return n;
/*      */     }
/*      */ 
/*      */     
/*      */     public int back(int n) {
/* 1202 */       if (this.curr - n >= this.from) {
/* 1203 */         this.curr = (byte)(this.curr - n);
/* 1204 */         return n;
/*      */       } 
/* 1206 */       n = this.curr - this.from;
/* 1207 */       this.curr = this.from;
/* 1208 */       return n;
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
/*      */   public static ByteListIterator fromTo(byte from, byte to) {
/* 1224 */     return new IntervalIterator(from, to);
/*      */   }
/*      */   
/*      */   private static class IteratorConcatenator implements ByteIterator {
/*      */     final ByteIterator[] a;
/* 1229 */     int lastOffset = -1; int offset; int length;
/*      */     
/*      */     public IteratorConcatenator(ByteIterator[] a, int offset, int length) {
/* 1232 */       this.a = a;
/* 1233 */       this.offset = offset;
/* 1234 */       this.length = length;
/* 1235 */       advance();
/*      */     }
/*      */     
/*      */     private void advance() {
/* 1239 */       while (this.length != 0 && 
/* 1240 */         !this.a[this.offset].hasNext()) {
/* 1241 */         this.length--;
/* 1242 */         this.offset++;
/*      */       } 
/*      */     }
/*      */ 
/*      */ 
/*      */     
/*      */     public boolean hasNext() {
/* 1249 */       return (this.length > 0);
/*      */     }
/*      */ 
/*      */     
/*      */     public byte nextByte() {
/* 1254 */       if (!hasNext()) throw new NoSuchElementException(); 
/* 1255 */       byte next = this.a[this.lastOffset = this.offset].nextByte();
/* 1256 */       advance();
/* 1257 */       return next;
/*      */     }
/*      */ 
/*      */     
/*      */     public void forEachRemaining(ByteConsumer action) {
/* 1262 */       while (this.length > 0) {
/* 1263 */         this.a[this.lastOffset = this.offset].forEachRemaining(action);
/* 1264 */         advance();
/*      */       } 
/*      */     }
/*      */ 
/*      */     
/*      */     @Deprecated
/*      */     public void forEachRemaining(Consumer<? super Byte> action) {
/* 1271 */       while (this.length > 0) {
/* 1272 */         this.a[this.lastOffset = this.offset].forEachRemaining(action);
/* 1273 */         advance();
/*      */       } 
/*      */     }
/*      */ 
/*      */     
/*      */     public void remove() {
/* 1279 */       if (this.lastOffset == -1) throw new IllegalStateException(); 
/* 1280 */       this.a[this.lastOffset].remove();
/*      */     }
/*      */ 
/*      */     
/*      */     public int skip(int n) {
/* 1285 */       if (n < 0) throw new IllegalArgumentException("Argument must be nonnegative: " + n); 
/* 1286 */       this.lastOffset = -1;
/* 1287 */       int skipped = 0;
/* 1288 */       while (skipped < n && this.length != 0) {
/* 1289 */         skipped += this.a[this.offset].skip(n - skipped);
/* 1290 */         if (this.a[this.offset].hasNext())
/* 1291 */           break;  this.length--;
/* 1292 */         this.offset++;
/*      */       } 
/* 1294 */       return skipped;
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
/*      */   public static ByteIterator concat(ByteIterator... a) {
/* 1309 */     return concat(a, 0, a.length);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static ByteIterator concat(ByteIterator[] a, int offset, int length) {
/* 1327 */     return new IteratorConcatenator(a, offset, length);
/*      */   }
/*      */   
/*      */   public static class UnmodifiableIterator
/*      */     implements ByteIterator {
/*      */     protected final ByteIterator i;
/*      */     
/*      */     public UnmodifiableIterator(ByteIterator i) {
/* 1335 */       this.i = i;
/*      */     }
/*      */ 
/*      */     
/*      */     public boolean hasNext() {
/* 1340 */       return this.i.hasNext();
/*      */     }
/*      */ 
/*      */     
/*      */     public byte nextByte() {
/* 1345 */       return this.i.nextByte();
/*      */     }
/*      */ 
/*      */     
/*      */     public void forEachRemaining(ByteConsumer action) {
/* 1350 */       this.i.forEachRemaining(action);
/*      */     }
/*      */ 
/*      */     
/*      */     @Deprecated
/*      */     public void forEachRemaining(Consumer<? super Byte> action) {
/* 1356 */       this.i.forEachRemaining(action);
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static ByteIterator unmodifiable(ByteIterator i) {
/* 1367 */     return new UnmodifiableIterator(i);
/*      */   }
/*      */   
/*      */   public static class UnmodifiableBidirectionalIterator
/*      */     implements ByteBidirectionalIterator {
/*      */     protected final ByteBidirectionalIterator i;
/*      */     
/*      */     public UnmodifiableBidirectionalIterator(ByteBidirectionalIterator i) {
/* 1375 */       this.i = i;
/*      */     }
/*      */ 
/*      */     
/*      */     public boolean hasNext() {
/* 1380 */       return this.i.hasNext();
/*      */     }
/*      */ 
/*      */     
/*      */     public boolean hasPrevious() {
/* 1385 */       return this.i.hasPrevious();
/*      */     }
/*      */ 
/*      */     
/*      */     public byte nextByte() {
/* 1390 */       return this.i.nextByte();
/*      */     }
/*      */ 
/*      */     
/*      */     public byte previousByte() {
/* 1395 */       return this.i.previousByte();
/*      */     }
/*      */ 
/*      */     
/*      */     public void forEachRemaining(ByteConsumer action) {
/* 1400 */       this.i.forEachRemaining(action);
/*      */     }
/*      */ 
/*      */     
/*      */     @Deprecated
/*      */     public void forEachRemaining(Consumer<? super Byte> action) {
/* 1406 */       this.i.forEachRemaining(action);
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static ByteBidirectionalIterator unmodifiable(ByteBidirectionalIterator i) {
/* 1417 */     return new UnmodifiableBidirectionalIterator(i);
/*      */   }
/*      */   
/*      */   public static class UnmodifiableListIterator
/*      */     implements ByteListIterator {
/*      */     protected final ByteListIterator i;
/*      */     
/*      */     public UnmodifiableListIterator(ByteListIterator i) {
/* 1425 */       this.i = i;
/*      */     }
/*      */ 
/*      */     
/*      */     public boolean hasNext() {
/* 1430 */       return this.i.hasNext();
/*      */     }
/*      */ 
/*      */     
/*      */     public boolean hasPrevious() {
/* 1435 */       return this.i.hasPrevious();
/*      */     }
/*      */ 
/*      */     
/*      */     public byte nextByte() {
/* 1440 */       return this.i.nextByte();
/*      */     }
/*      */ 
/*      */     
/*      */     public byte previousByte() {
/* 1445 */       return this.i.previousByte();
/*      */     }
/*      */ 
/*      */     
/*      */     public int nextIndex() {
/* 1450 */       return this.i.nextIndex();
/*      */     }
/*      */ 
/*      */     
/*      */     public int previousIndex() {
/* 1455 */       return this.i.previousIndex();
/*      */     }
/*      */ 
/*      */     
/*      */     public void forEachRemaining(ByteConsumer action) {
/* 1460 */       this.i.forEachRemaining(action);
/*      */     }
/*      */ 
/*      */     
/*      */     @Deprecated
/*      */     public void forEachRemaining(Consumer<? super Byte> action) {
/* 1466 */       this.i.forEachRemaining(action);
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static ByteListIterator unmodifiable(ByteListIterator i) {
/* 1477 */     return new UnmodifiableListIterator(i);
/*      */   }
/*      */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\i\\unimi\dsi\fastutil\bytes\ByteIterators.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */