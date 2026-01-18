/*      */ package it.unimi.dsi.fastutil.ints;
/*      */ 
/*      */ import it.unimi.dsi.fastutil.BigArrays;
/*      */ import it.unimi.dsi.fastutil.BigList;
/*      */ import it.unimi.dsi.fastutil.BigListIterator;
/*      */ import it.unimi.dsi.fastutil.Size64;
/*      */ import java.io.IOException;
/*      */ import java.io.ObjectInputStream;
/*      */ import java.io.ObjectOutputStream;
/*      */ import java.io.Serializable;
/*      */ import java.util.Collection;
/*      */ import java.util.Iterator;
/*      */ import java.util.NoSuchElementException;
/*      */ import java.util.RandomAccess;
/*      */ import java.util.function.IntConsumer;
/*      */ import java.util.function.Supplier;
/*      */ import java.util.stream.IntStream;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ public class IntBigArrayBigList
/*      */   extends AbstractIntBigList
/*      */   implements RandomAccess, Cloneable, Serializable
/*      */ {
/*      */   private static final long serialVersionUID = -7046029254386353130L;
/*      */   public static final int DEFAULT_INITIAL_CAPACITY = 10;
/*      */   protected transient int[][] a;
/*      */   protected long size;
/*      */   
/*      */   protected IntBigArrayBigList(int[][] a, boolean dummy) {
/*   67 */     this.a = a;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public IntBigArrayBigList(long capacity) {
/*   77 */     if (capacity < 0L) throw new IllegalArgumentException("Initial capacity (" + capacity + ") is negative"); 
/*   78 */     if (capacity == 0L) { this.a = IntBigArrays.EMPTY_BIG_ARRAY; }
/*   79 */     else { this.a = IntBigArrays.newBigArray(capacity); }
/*      */   
/*      */   }
/*      */ 
/*      */   
/*      */   public IntBigArrayBigList() {
/*   85 */     this.a = IntBigArrays.DEFAULT_EMPTY_BIG_ARRAY;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public IntBigArrayBigList(IntCollection c) {
/*   94 */     this(Size64.sizeOf(c));
/*      */     
/*   96 */     ((IntBigList)c).getElements(0L, this.a, 0L, this.size = Size64.sizeOf(c));
/*      */     
/*   98 */     for (IntIterator i = c.iterator(); i.hasNext(); add(i.nextInt()));
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public IntBigArrayBigList(IntBigList l) {
/*  108 */     this(l.size64());
/*  109 */     l.getElements(0L, this.a, 0L, this.size = l.size64());
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public IntBigArrayBigList(int[][] a) {
/*  118 */     this(a, 0L, BigArrays.length(a));
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public IntBigArrayBigList(int[][] a, long offset, long length) {
/*  129 */     this(length);
/*  130 */     BigArrays.copy(a, offset, this.a, 0L, length);
/*  131 */     this.size = length;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public IntBigArrayBigList(Iterator<? extends Integer> i) {
/*  140 */     this();
/*  141 */     for (; i.hasNext(); add(((Integer)i.next()).intValue()));
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public IntBigArrayBigList(IntIterator i) {
/*  151 */     this();
/*  152 */     for (; i.hasNext(); add(i.nextInt()));
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public int[][] elements() {
/*  161 */     return this.a;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static IntBigArrayBigList wrap(int[][] a, long length) {
/*  172 */     if (length > BigArrays.length(a)) throw new IllegalArgumentException("The specified length (" + length + ") is greater than the array size (" + BigArrays.length(a) + ")"); 
/*  173 */     IntBigArrayBigList l = new IntBigArrayBigList(a, false);
/*  174 */     l.size = length;
/*  175 */     return l;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static IntBigArrayBigList wrap(int[][] a) {
/*  185 */     return wrap(a, BigArrays.length(a));
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static IntBigArrayBigList of() {
/*  194 */     return new IntBigArrayBigList();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static IntBigArrayBigList of(int... init) {
/*  208 */     return wrap(BigArrays.wrap(init));
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static IntBigArrayBigList toBigList(IntStream stream) {
/*  222 */     return stream.<IntBigArrayBigList>collect(IntBigArrayBigList::new, IntBigArrayBigList::add, IntBigList::addAll);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static IntBigArrayBigList toBigListWithExpectedSize(IntStream stream, long expectedSize) {
/*  239 */     return stream.<IntBigArrayBigList>collect(() -> new IntBigArrayBigList(expectedSize), IntBigArrayBigList::add, IntBigList::addAll);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void ensureCapacity(long capacity) {
/*  249 */     if (capacity <= BigArrays.length(this.a) || this.a == IntBigArrays.DEFAULT_EMPTY_BIG_ARRAY)
/*  250 */       return;  this.a = BigArrays.forceCapacity(this.a, capacity, this.size);
/*  251 */     assert this.size <= BigArrays.length(this.a);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private void grow(long capacity) {
/*  262 */     long oldLength = BigArrays.length(this.a);
/*  263 */     if (capacity <= oldLength)
/*  264 */       return;  if (this.a != IntBigArrays.DEFAULT_EMPTY_BIG_ARRAY) { capacity = Math.max(oldLength + (oldLength >> 1L), capacity); }
/*  265 */     else if (capacity < 10L) { capacity = 10L; }
/*  266 */      this.a = BigArrays.forceCapacity(this.a, capacity, this.size);
/*  267 */     assert this.size <= BigArrays.length(this.a);
/*      */   }
/*      */ 
/*      */   
/*      */   public void add(long index, int k) {
/*  272 */     ensureIndex(index);
/*  273 */     grow(this.size + 1L);
/*  274 */     if (index != this.size) BigArrays.copy(this.a, index, this.a, index + 1L, this.size - index); 
/*  275 */     BigArrays.set(this.a, index, k);
/*  276 */     this.size++;
/*  277 */     assert this.size <= BigArrays.length(this.a);
/*      */   }
/*      */ 
/*      */   
/*      */   public boolean add(int k) {
/*  282 */     grow(this.size + 1L);
/*  283 */     BigArrays.set(this.a, this.size++, k);
/*  284 */     if (!$assertionsDisabled && this.size > BigArrays.length(this.a)) throw new AssertionError(); 
/*  285 */     return true;
/*      */   }
/*      */ 
/*      */   
/*      */   public int getInt(long index) {
/*  290 */     if (index >= this.size) throw new IndexOutOfBoundsException("Index (" + index + ") is greater than or equal to list size (" + this.size + ")"); 
/*  291 */     return BigArrays.get(this.a, index);
/*      */   }
/*      */ 
/*      */   
/*      */   public long indexOf(int k) {
/*  296 */     for (long i = 0L; i < this.size; ) { if (k == BigArrays.get(this.a, i)) return i;  i++; }
/*  297 */      return -1L;
/*      */   }
/*      */ 
/*      */   
/*      */   public long lastIndexOf(int k) {
/*  302 */     for (long i = this.size; i-- != 0L;) { if (k == BigArrays.get(this.a, i)) return i;  }
/*  303 */      return -1L;
/*      */   }
/*      */ 
/*      */   
/*      */   public int removeInt(long index) {
/*  308 */     if (index >= this.size) throw new IndexOutOfBoundsException("Index (" + index + ") is greater than or equal to list size (" + this.size + ")"); 
/*  309 */     int old = BigArrays.get(this.a, index);
/*  310 */     this.size--;
/*  311 */     if (index != this.size) BigArrays.copy(this.a, index + 1L, this.a, index, this.size - index); 
/*  312 */     assert this.size <= BigArrays.length(this.a);
/*  313 */     return old;
/*      */   }
/*      */ 
/*      */   
/*      */   public boolean rem(int k) {
/*  318 */     long index = indexOf(k);
/*  319 */     if (index == -1L) return false; 
/*  320 */     removeInt(index);
/*  321 */     assert this.size <= BigArrays.length(this.a);
/*  322 */     return true;
/*      */   }
/*      */ 
/*      */   
/*      */   public int set(long index, int k) {
/*  327 */     if (index >= this.size) throw new IndexOutOfBoundsException("Index (" + index + ") is greater than or equal to list size (" + this.size + ")"); 
/*  328 */     int old = BigArrays.get(this.a, index);
/*  329 */     BigArrays.set(this.a, index, k);
/*  330 */     return old;
/*      */   }
/*      */ 
/*      */   
/*      */   public boolean removeAll(IntCollection c) {
/*  335 */     int[] s = null, d = null;
/*  336 */     int ss = -1, sd = 134217728, ds = -1, dd = 134217728; long i;
/*  337 */     for (i = 0L; i < this.size; i++) {
/*  338 */       if (sd == 134217728) {
/*  339 */         sd = 0;
/*  340 */         s = this.a[++ss];
/*      */       } 
/*  342 */       if (!c.contains(s[sd])) {
/*  343 */         if (dd == 134217728) {
/*  344 */           d = this.a[++ds];
/*  345 */           dd = 0;
/*      */         } 
/*  347 */         d[dd++] = s[sd];
/*      */       } 
/*  349 */       sd++;
/*      */     } 
/*  351 */     long j = BigArrays.index(ds, dd);
/*  352 */     boolean modified = (this.size != j);
/*  353 */     this.size = j;
/*  354 */     return modified;
/*      */   }
/*      */ 
/*      */   
/*      */   public boolean removeAll(Collection<?> c) {
/*  359 */     int[] s = null, d = null;
/*  360 */     int ss = -1, sd = 134217728, ds = -1, dd = 134217728; long i;
/*  361 */     for (i = 0L; i < this.size; i++) {
/*  362 */       if (sd == 134217728) {
/*  363 */         sd = 0;
/*  364 */         s = this.a[++ss];
/*      */       } 
/*  366 */       if (!c.contains(Integer.valueOf(s[sd]))) {
/*  367 */         if (dd == 134217728) {
/*  368 */           d = this.a[++ds];
/*  369 */           dd = 0;
/*      */         } 
/*  371 */         d[dd++] = s[sd];
/*      */       } 
/*  373 */       sd++;
/*      */     } 
/*  375 */     long j = BigArrays.index(ds, dd);
/*  376 */     boolean modified = (this.size != j);
/*  377 */     this.size = j;
/*  378 */     return modified;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean addAll(long index, IntCollection c) {
/*      */     // Byte code:
/*      */     //   0: aload_3
/*      */     //   1: instanceof it/unimi/dsi/fastutil/ints/IntList
/*      */     //   4: ifeq -> 17
/*      */     //   7: aload_0
/*      */     //   8: lload_1
/*      */     //   9: aload_3
/*      */     //   10: checkcast it/unimi/dsi/fastutil/ints/IntList
/*      */     //   13: invokevirtual addAll : (JLit/unimi/dsi/fastutil/ints/IntList;)Z
/*      */     //   16: ireturn
/*      */     //   17: aload_3
/*      */     //   18: instanceof it/unimi/dsi/fastutil/ints/IntBigList
/*      */     //   21: ifeq -> 34
/*      */     //   24: aload_0
/*      */     //   25: lload_1
/*      */     //   26: aload_3
/*      */     //   27: checkcast it/unimi/dsi/fastutil/ints/IntBigList
/*      */     //   30: invokevirtual addAll : (JLit/unimi/dsi/fastutil/ints/IntBigList;)Z
/*      */     //   33: ireturn
/*      */     //   34: aload_0
/*      */     //   35: lload_1
/*      */     //   36: invokevirtual ensureIndex : (J)V
/*      */     //   39: aload_3
/*      */     //   40: invokeinterface size : ()I
/*      */     //   45: istore #4
/*      */     //   47: iload #4
/*      */     //   49: ifne -> 54
/*      */     //   52: iconst_0
/*      */     //   53: ireturn
/*      */     //   54: aload_0
/*      */     //   55: aload_0
/*      */     //   56: getfield size : J
/*      */     //   59: iload #4
/*      */     //   61: i2l
/*      */     //   62: ladd
/*      */     //   63: invokespecial grow : (J)V
/*      */     //   66: aload_0
/*      */     //   67: getfield a : [[I
/*      */     //   70: lload_1
/*      */     //   71: aload_0
/*      */     //   72: getfield a : [[I
/*      */     //   75: lload_1
/*      */     //   76: iload #4
/*      */     //   78: i2l
/*      */     //   79: ladd
/*      */     //   80: aload_0
/*      */     //   81: getfield size : J
/*      */     //   84: lload_1
/*      */     //   85: lsub
/*      */     //   86: invokestatic copy : ([[IJ[[IJJ)V
/*      */     //   89: aload_3
/*      */     //   90: invokeinterface iterator : ()Lit/unimi/dsi/fastutil/ints/IntIterator;
/*      */     //   95: astore #5
/*      */     //   97: aload_0
/*      */     //   98: dup
/*      */     //   99: getfield size : J
/*      */     //   102: iload #4
/*      */     //   104: i2l
/*      */     //   105: ladd
/*      */     //   106: putfield size : J
/*      */     //   109: getstatic it/unimi/dsi/fastutil/ints/IntBigArrayBigList.$assertionsDisabled : Z
/*      */     //   112: ifne -> 138
/*      */     //   115: aload_0
/*      */     //   116: getfield size : J
/*      */     //   119: aload_0
/*      */     //   120: getfield a : [[I
/*      */     //   123: invokestatic length : ([[I)J
/*      */     //   126: lcmp
/*      */     //   127: ifle -> 138
/*      */     //   130: new java/lang/AssertionError
/*      */     //   133: dup
/*      */     //   134: invokespecial <init> : ()V
/*      */     //   137: athrow
/*      */     //   138: iload #4
/*      */     //   140: iinc #4, -1
/*      */     //   143: ifeq -> 168
/*      */     //   146: aload_0
/*      */     //   147: getfield a : [[I
/*      */     //   150: lload_1
/*      */     //   151: dup2
/*      */     //   152: lconst_1
/*      */     //   153: ladd
/*      */     //   154: lstore_1
/*      */     //   155: aload #5
/*      */     //   157: invokeinterface nextInt : ()I
/*      */     //   162: invokestatic set : ([[IJI)V
/*      */     //   165: goto -> 138
/*      */     //   168: iconst_1
/*      */     //   169: ireturn
/*      */     // Line number table:
/*      */     //   Java source line number -> byte code offset
/*      */     //   #383	-> 0
/*      */     //   #384	-> 7
/*      */     //   #386	-> 17
/*      */     //   #387	-> 24
/*      */     //   #389	-> 34
/*      */     //   #390	-> 39
/*      */     //   #391	-> 47
/*      */     //   #392	-> 54
/*      */     //   #393	-> 66
/*      */     //   #394	-> 89
/*      */     //   #395	-> 97
/*      */     //   #396	-> 109
/*      */     //   #397	-> 138
/*      */     //   #398	-> 168
/*      */     // Local variable table:
/*      */     //   start	length	slot	name	descriptor
/*      */     //   0	170	0	this	Lit/unimi/dsi/fastutil/ints/IntBigArrayBigList;
/*      */     //   0	170	1	index	J
/*      */     //   0	170	3	c	Lit/unimi/dsi/fastutil/ints/IntCollection;
/*      */     //   47	123	4	n	I
/*      */     //   97	73	5	i	Lit/unimi/dsi/fastutil/ints/IntIterator;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean addAll(long index, IntBigList list) {
/*  403 */     ensureIndex(index);
/*  404 */     long n = list.size64();
/*  405 */     if (n == 0L) return false; 
/*  406 */     grow(this.size + n);
/*  407 */     BigArrays.copy(this.a, index, this.a, index + n, this.size - index);
/*  408 */     list.getElements(0L, this.a, index, n);
/*  409 */     this.size += n;
/*  410 */     assert this.size <= BigArrays.length(this.a);
/*  411 */     return true;
/*      */   }
/*      */ 
/*      */   
/*      */   public boolean addAll(long index, IntList list) {
/*  416 */     ensureIndex(index);
/*  417 */     int n = list.size();
/*  418 */     if (n == 0) return false; 
/*  419 */     grow(this.size + n);
/*  420 */     BigArrays.copy(this.a, index, this.a, index + n, this.size - index);
/*  421 */     this.size += n;
/*  422 */     assert this.size <= BigArrays.length(this.a);
/*  423 */     int segment = BigArrays.segment(index);
/*  424 */     int displ = BigArrays.displacement(index);
/*  425 */     int pos = 0;
/*  426 */     while (n > 0) {
/*  427 */       int l = Math.min((this.a[segment]).length - displ, n);
/*  428 */       list.getElements(pos, this.a[segment], displ, l);
/*  429 */       if ((displ += l) == 134217728) {
/*  430 */         displ = 0;
/*  431 */         segment++;
/*      */       } 
/*  433 */       pos += l;
/*  434 */       n -= l;
/*      */     } 
/*  436 */     return true;
/*      */   }
/*      */ 
/*      */   
/*      */   public void clear() {
/*  441 */     this.size = 0L;
/*  442 */     assert this.size <= BigArrays.length(this.a);
/*      */   }
/*      */ 
/*      */   
/*      */   public long size64() {
/*  447 */     return this.size;
/*      */   }
/*      */ 
/*      */   
/*      */   public void size(long size) {
/*  452 */     if (size > BigArrays.length(this.a)) this.a = BigArrays.forceCapacity(this.a, size, this.size); 
/*  453 */     if (size > this.size) BigArrays.fill(this.a, this.size, size, 0); 
/*  454 */     this.size = size;
/*      */   }
/*      */ 
/*      */   
/*      */   public boolean isEmpty() {
/*  459 */     return (this.size == 0L);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void trim() {
/*  468 */     trim(0L);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void trim(long n) {
/*  486 */     long arrayLength = BigArrays.length(this.a);
/*  487 */     if (n >= arrayLength || this.size == arrayLength)
/*  488 */       return;  this.a = BigArrays.trim(this.a, Math.max(n, this.size));
/*  489 */     assert this.size <= BigArrays.length(this.a);
/*      */   }
/*      */   
/*      */   private class SubList extends AbstractIntBigList.IntRandomAccessSubList {
/*      */     private static final long serialVersionUID = -3185226345314976296L;
/*      */     
/*      */     protected SubList(long from, long to) {
/*  496 */       super(from, to);
/*      */     }
/*      */ 
/*      */ 
/*      */     
/*      */     private int[][] getParentArray() {
/*  502 */       return IntBigArrayBigList.this.a;
/*      */     }
/*      */ 
/*      */ 
/*      */     
/*      */     public int getInt(long i) {
/*  508 */       ensureRestrictedIndex(i);
/*  509 */       return BigArrays.get(IntBigArrayBigList.this.a, i + this.from);
/*      */     }
/*      */     
/*      */     private final class SubListIterator
/*      */       extends IntBigListIterators.AbstractIndexBasedBigListIterator
/*      */     {
/*      */       SubListIterator(long index) {
/*  516 */         super(0L, index);
/*      */       }
/*      */ 
/*      */       
/*      */       protected final int get(long i) {
/*  521 */         return BigArrays.get(IntBigArrayBigList.this.a, IntBigArrayBigList.SubList.this.from + i);
/*      */       }
/*      */ 
/*      */       
/*      */       protected final void add(long i, int k) {
/*  526 */         IntBigArrayBigList.SubList.this.add(i, k);
/*      */       }
/*      */ 
/*      */       
/*      */       protected final void set(long i, int k) {
/*  531 */         IntBigArrayBigList.SubList.this.set(i, k);
/*      */       }
/*      */ 
/*      */       
/*      */       protected final void remove(long i) {
/*  536 */         IntBigArrayBigList.SubList.this.removeInt(i);
/*      */       }
/*      */ 
/*      */       
/*      */       protected final long getMaxPos() {
/*  541 */         return IntBigArrayBigList.SubList.this.to - IntBigArrayBigList.SubList.this.from;
/*      */       }
/*      */ 
/*      */       
/*      */       public int nextInt() {
/*  546 */         if (!hasNext()) throw new NoSuchElementException(); 
/*  547 */         return BigArrays.get(IntBigArrayBigList.this.a, IntBigArrayBigList.SubList.this.from + (this.lastReturned = this.pos++));
/*      */       }
/*      */ 
/*      */       
/*      */       public int previousInt() {
/*  552 */         if (!hasPrevious()) throw new NoSuchElementException(); 
/*  553 */         return BigArrays.get(IntBigArrayBigList.this.a, IntBigArrayBigList.SubList.this.from + (this.lastReturned = --this.pos));
/*      */       }
/*      */ 
/*      */ 
/*      */ 
/*      */       
/*      */       public void forEachRemaining(IntConsumer action) {
/*      */         // Byte code:
/*      */         //   0: aload_0
/*      */         //   1: getfield this$1 : Lit/unimi/dsi/fastutil/ints/IntBigArrayBigList$SubList;
/*      */         //   4: getfield to : J
/*      */         //   7: aload_0
/*      */         //   8: getfield this$1 : Lit/unimi/dsi/fastutil/ints/IntBigArrayBigList$SubList;
/*      */         //   11: getfield from : J
/*      */         //   14: lsub
/*      */         //   15: lstore_2
/*      */         //   16: aload_0
/*      */         //   17: getfield pos : J
/*      */         //   20: lload_2
/*      */         //   21: lcmp
/*      */         //   22: ifge -> 71
/*      */         //   25: aload_1
/*      */         //   26: aload_0
/*      */         //   27: getfield this$1 : Lit/unimi/dsi/fastutil/ints/IntBigArrayBigList$SubList;
/*      */         //   30: getfield this$0 : Lit/unimi/dsi/fastutil/ints/IntBigArrayBigList;
/*      */         //   33: getfield a : [[I
/*      */         //   36: aload_0
/*      */         //   37: getfield this$1 : Lit/unimi/dsi/fastutil/ints/IntBigArrayBigList$SubList;
/*      */         //   40: getfield from : J
/*      */         //   43: aload_0
/*      */         //   44: aload_0
/*      */         //   45: dup
/*      */         //   46: getfield pos : J
/*      */         //   49: dup2_x1
/*      */         //   50: lconst_1
/*      */         //   51: ladd
/*      */         //   52: putfield pos : J
/*      */         //   55: dup2_x1
/*      */         //   56: putfield lastReturned : J
/*      */         //   59: ladd
/*      */         //   60: invokestatic get : ([[IJ)I
/*      */         //   63: invokeinterface accept : (I)V
/*      */         //   68: goto -> 16
/*      */         //   71: return
/*      */         // Line number table:
/*      */         //   Java source line number -> byte code offset
/*      */         //   #558	-> 0
/*      */         //   #559	-> 16
/*      */         //   #560	-> 25
/*      */         //   #562	-> 71
/*      */         // Local variable table:
/*      */         //   start	length	slot	name	descriptor
/*      */         //   0	72	0	this	Lit/unimi/dsi/fastutil/ints/IntBigArrayBigList$SubList$SubListIterator;
/*      */         //   0	72	1	action	Ljava/util/function/IntConsumer;
/*      */         //   16	56	2	max	J
/*      */       }
/*      */     }
/*      */ 
/*      */ 
/*      */     
/*      */     public IntBigListIterator listIterator(long index) {
/*  567 */       return new SubListIterator(index);
/*      */     }
/*      */     
/*      */     private final class SubListSpliterator
/*      */       extends IntBigSpliterators.LateBindingSizeIndexBasedSpliterator {
/*      */       SubListSpliterator() {
/*  573 */         super(IntBigArrayBigList.SubList.this.from);
/*      */       }
/*      */       
/*      */       private SubListSpliterator(long pos, long maxPos) {
/*  577 */         super(pos, maxPos);
/*      */       }
/*      */ 
/*      */       
/*      */       protected final long getMaxPosFromBackingStore() {
/*  582 */         return IntBigArrayBigList.SubList.this.to;
/*      */       }
/*      */ 
/*      */       
/*      */       protected final int get(long i) {
/*  587 */         return BigArrays.get(IntBigArrayBigList.this.a, i);
/*      */       }
/*      */ 
/*      */       
/*      */       protected final SubListSpliterator makeForSplit(long pos, long maxPos) {
/*  592 */         return new SubListSpliterator(pos, maxPos);
/*      */       }
/*      */ 
/*      */       
/*      */       protected final long computeSplitPoint() {
/*  597 */         long defaultSplit = super.computeSplitPoint();
/*      */ 
/*      */         
/*  600 */         return BigArrays.nearestSegmentStart(defaultSplit, this.pos + 1L, getMaxPos() - 1L);
/*      */       }
/*      */ 
/*      */       
/*      */       public boolean tryAdvance(IntConsumer action) {
/*  605 */         if (this.pos >= getMaxPos()) return false; 
/*  606 */         action.accept(BigArrays.get(IntBigArrayBigList.this.a, this.pos++));
/*  607 */         return true;
/*      */       }
/*      */ 
/*      */ 
/*      */ 
/*      */       
/*      */       public void forEachRemaining(IntConsumer action) {
/*      */         // Byte code:
/*      */         //   0: aload_0
/*      */         //   1: invokevirtual getMaxPos : ()J
/*      */         //   4: lstore_2
/*      */         //   5: aload_0
/*      */         //   6: getfield pos : J
/*      */         //   9: lload_2
/*      */         //   10: lcmp
/*      */         //   11: ifge -> 47
/*      */         //   14: aload_1
/*      */         //   15: aload_0
/*      */         //   16: getfield this$1 : Lit/unimi/dsi/fastutil/ints/IntBigArrayBigList$SubList;
/*      */         //   19: getfield this$0 : Lit/unimi/dsi/fastutil/ints/IntBigArrayBigList;
/*      */         //   22: getfield a : [[I
/*      */         //   25: aload_0
/*      */         //   26: dup
/*      */         //   27: getfield pos : J
/*      */         //   30: dup2_x1
/*      */         //   31: lconst_1
/*      */         //   32: ladd
/*      */         //   33: putfield pos : J
/*      */         //   36: invokestatic get : ([[IJ)I
/*      */         //   39: invokeinterface accept : (I)V
/*      */         //   44: goto -> 5
/*      */         //   47: return
/*      */         // Line number table:
/*      */         //   Java source line number -> byte code offset
/*      */         //   #612	-> 0
/*      */         //   #613	-> 5
/*      */         //   #614	-> 14
/*      */         //   #616	-> 47
/*      */         // Local variable table:
/*      */         //   start	length	slot	name	descriptor
/*      */         //   0	48	0	this	Lit/unimi/dsi/fastutil/ints/IntBigArrayBigList$SubList$SubListSpliterator;
/*      */         //   0	48	1	action	Ljava/util/function/IntConsumer;
/*      */         //   5	43	2	max	J
/*      */       }
/*      */     }
/*      */ 
/*      */ 
/*      */     
/*      */     public IntSpliterator spliterator() {
/*  621 */       return new SubListSpliterator();
/*      */     }
/*      */     
/*      */     boolean contentsEquals(int[][] otherA, long otherAFrom, long otherATo) {
/*  625 */       if (IntBigArrayBigList.this.a == otherA && this.from == otherAFrom && this.to == otherATo) return true; 
/*  626 */       if (otherATo - otherAFrom != size64()) {
/*  627 */         return false;
/*      */       }
/*  629 */       long pos = this.to, otherPos = otherATo;
/*      */ 
/*      */ 
/*      */ 
/*      */       
/*  634 */       while (--pos >= this.from) { if (BigArrays.get(IntBigArrayBigList.this.a, pos) != BigArrays.get(otherA, --otherPos)) return false;  }
/*  635 */        return true;
/*      */     }
/*      */ 
/*      */     
/*      */     public boolean equals(Object o) {
/*  640 */       if (o == this) return true; 
/*  641 */       if (o == null) return false; 
/*  642 */       if (!(o instanceof BigList)) return false; 
/*  643 */       if (o instanceof IntBigArrayBigList) {
/*      */         
/*  645 */         IntBigArrayBigList other = (IntBigArrayBigList)o;
/*  646 */         return contentsEquals(other.a, 0L, other.size64());
/*      */       } 
/*  648 */       if (o instanceof SubList) {
/*      */         
/*  650 */         SubList other = (SubList)o;
/*  651 */         return contentsEquals(other.getParentArray(), other.from, other.to);
/*      */       } 
/*  653 */       return super.equals(o);
/*      */     }
/*      */     
/*      */     int contentsCompareTo(int[][] otherA, long otherAFrom, long otherATo) {
/*  657 */       if (IntBigArrayBigList.this.a == otherA && this.from == otherAFrom && this.to == otherATo) return 0;
/*      */ 
/*      */       
/*      */       long i;
/*      */       long j;
/*  662 */       for (i = this.from, j = otherAFrom; i < this.to && i < otherATo; i++, j++) {
/*  663 */         int e1 = BigArrays.get(IntBigArrayBigList.this.a, i);
/*  664 */         int e2 = BigArrays.get(otherA, j); int r;
/*  665 */         if ((r = Integer.compare(e1, e2)) != 0) return r; 
/*      */       } 
/*  667 */       return (i < otherATo) ? -1 : ((i < this.to) ? 1 : 0);
/*      */     }
/*      */ 
/*      */     
/*      */     public int compareTo(BigList<? extends Integer> l) {
/*  672 */       if (l instanceof IntBigArrayBigList) {
/*      */         
/*  674 */         IntBigArrayBigList other = (IntBigArrayBigList)l;
/*  675 */         return contentsCompareTo(other.a, 0L, other.size64());
/*      */       } 
/*  677 */       if (l instanceof SubList) {
/*      */         
/*  679 */         SubList other = (SubList)l;
/*  680 */         return contentsCompareTo(other.getParentArray(), other.from, other.to);
/*      */       } 
/*  682 */       return super.compareTo(l);
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public IntBigList subList(long from, long to) {
/*  692 */     if (from == 0L && to == size64()) return this; 
/*  693 */     ensureIndex(from);
/*  694 */     ensureIndex(to);
/*  695 */     if (from > to) throw new IndexOutOfBoundsException("Start index (" + from + ") is greater than end index (" + to + ")"); 
/*  696 */     return new SubList(from, to);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void getElements(long from, int[][] a, long offset, long length) {
/*  709 */     BigArrays.copy(this.a, from, a, offset, length);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void getElements(long from, int[] a, int offset, int length) {
/*  722 */     BigArrays.copyFromBig(this.a, from, a, offset, length);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void removeElements(long from, long to) {
/*  733 */     BigArrays.ensureFromTo(this.size, from, to);
/*  734 */     BigArrays.copy(this.a, to, this.a, from, this.size - to);
/*  735 */     this.size -= to - from;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void addElements(long index, int[][] a, long offset, long length) {
/*  748 */     ensureIndex(index);
/*  749 */     BigArrays.ensureOffsetLength(a, offset, length);
/*  750 */     grow(this.size + length);
/*  751 */     BigArrays.copy(this.a, index, this.a, index + length, this.size - index);
/*  752 */     BigArrays.copy(a, offset, this.a, index, length);
/*  753 */     this.size += length;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setElements(long index, int[][] a, long offset, long length) {
/*  766 */     BigArrays.copy(a, offset, this.a, index, length);
/*      */   }
/*      */ 
/*      */   
/*      */   public void forEach(IntConsumer action) {
/*  771 */     for (long i = 0L; i < this.size; i++) {
/*  772 */       action.accept(BigArrays.get(this.a, i));
/*      */     }
/*      */   }
/*      */ 
/*      */   
/*      */   public IntBigListIterator listIterator(final long index) {
/*  778 */     ensureIndex(index);
/*  779 */     return new IntBigListIterator() {
/*  780 */         long pos = index; long last = -1L;
/*      */ 
/*      */         
/*      */         public boolean hasNext() {
/*  784 */           return (this.pos < IntBigArrayBigList.this.size);
/*      */         }
/*      */ 
/*      */         
/*      */         public boolean hasPrevious() {
/*  789 */           return (this.pos > 0L);
/*      */         }
/*      */ 
/*      */         
/*      */         public int nextInt() {
/*  794 */           if (!hasNext()) throw new NoSuchElementException(); 
/*  795 */           return BigArrays.get(IntBigArrayBigList.this.a, this.last = this.pos++);
/*      */         }
/*      */ 
/*      */         
/*      */         public int previousInt() {
/*  800 */           if (!hasPrevious()) throw new NoSuchElementException(); 
/*  801 */           return BigArrays.get(IntBigArrayBigList.this.a, this.last = --this.pos);
/*      */         }
/*      */ 
/*      */         
/*      */         public long nextIndex() {
/*  806 */           return this.pos;
/*      */         }
/*      */ 
/*      */         
/*      */         public long previousIndex() {
/*  811 */           return this.pos - 1L;
/*      */         }
/*      */ 
/*      */         
/*      */         public void add(int k) {
/*  816 */           IntBigArrayBigList.this.add(this.pos++, k);
/*  817 */           this.last = -1L;
/*      */         }
/*      */ 
/*      */         
/*      */         public void set(int k) {
/*  822 */           if (this.last == -1L) throw new IllegalStateException(); 
/*  823 */           IntBigArrayBigList.this.set(this.last, k);
/*      */         }
/*      */ 
/*      */         
/*      */         public void remove() {
/*  828 */           if (this.last == -1L) throw new IllegalStateException(); 
/*  829 */           IntBigArrayBigList.this.removeInt(this.last);
/*      */           
/*  831 */           if (this.last < this.pos) this.pos--; 
/*  832 */           this.last = -1L;
/*      */         }
/*      */ 
/*      */ 
/*      */         
/*      */         public void forEachRemaining(IntConsumer action) {
/*      */           // Byte code:
/*      */           //   0: aload_0
/*      */           //   1: getfield pos : J
/*      */           //   4: aload_0
/*      */           //   5: getfield this$0 : Lit/unimi/dsi/fastutil/ints/IntBigArrayBigList;
/*      */           //   8: getfield size : J
/*      */           //   11: lcmp
/*      */           //   12: ifge -> 50
/*      */           //   15: aload_1
/*      */           //   16: aload_0
/*      */           //   17: getfield this$0 : Lit/unimi/dsi/fastutil/ints/IntBigArrayBigList;
/*      */           //   20: getfield a : [[I
/*      */           //   23: aload_0
/*      */           //   24: aload_0
/*      */           //   25: dup
/*      */           //   26: getfield pos : J
/*      */           //   29: dup2_x1
/*      */           //   30: lconst_1
/*      */           //   31: ladd
/*      */           //   32: putfield pos : J
/*      */           //   35: dup2_x1
/*      */           //   36: putfield last : J
/*      */           //   39: invokestatic get : ([[IJ)I
/*      */           //   42: invokeinterface accept : (I)V
/*      */           //   47: goto -> 0
/*      */           //   50: return
/*      */           // Line number table:
/*      */           //   Java source line number -> byte code offset
/*      */           //   #837	-> 0
/*      */           //   #838	-> 15
/*      */           //   #840	-> 50
/*      */           // Local variable table:
/*      */           //   start	length	slot	name	descriptor
/*      */           //   0	51	0	this	Lit/unimi/dsi/fastutil/ints/IntBigArrayBigList$1;
/*      */           //   0	51	1	action	Ljava/util/function/IntConsumer;
/*      */         }
/*      */ 
/*      */ 
/*      */         
/*      */         public long back(long n) {
/*  844 */           if (n < 0L) throw new IllegalArgumentException("Argument must be nonnegative: " + n); 
/*  845 */           long remaining = IntBigArrayBigList.this.size - this.pos;
/*  846 */           if (n < remaining) {
/*  847 */             this.pos -= n;
/*      */           } else {
/*  849 */             n = remaining;
/*  850 */             this.pos = 0L;
/*      */           } 
/*  852 */           this.last = this.pos;
/*  853 */           return n;
/*      */         }
/*      */ 
/*      */         
/*      */         public long skip(long n) {
/*  858 */           if (n < 0L) throw new IllegalArgumentException("Argument must be nonnegative: " + n); 
/*  859 */           long remaining = IntBigArrayBigList.this.size - this.pos;
/*  860 */           if (n < remaining) {
/*  861 */             this.pos += n;
/*      */           } else {
/*  863 */             n = remaining;
/*  864 */             this.pos = IntBigArrayBigList.this.size;
/*      */           } 
/*  866 */           this.last = this.pos - 1L;
/*  867 */           return n;
/*      */         }
/*      */       };
/*      */   }
/*      */   
/*      */   private final class Spliterator
/*      */     implements IntSpliterator
/*      */   {
/*      */     boolean hasSplit = false;
/*      */     long pos;
/*      */     long max;
/*      */     
/*      */     public Spliterator() {
/*  880 */       this(0L, IntBigArrayBigList.this.size, false);
/*      */     }
/*      */     
/*      */     private Spliterator(long pos, long max, boolean hasSplit) {
/*  884 */       assert pos <= max : "pos " + pos + " must be <= max " + max;
/*  885 */       this.pos = pos;
/*  886 */       this.max = max;
/*  887 */       this.hasSplit = hasSplit;
/*      */     }
/*      */     
/*      */     private long getWorkingMax() {
/*  891 */       return this.hasSplit ? this.max : IntBigArrayBigList.this.size;
/*      */     }
/*      */ 
/*      */     
/*      */     public int characteristics() {
/*  896 */       return 16720;
/*      */     }
/*      */ 
/*      */     
/*      */     public long estimateSize() {
/*  901 */       return getWorkingMax() - this.pos;
/*      */     }
/*      */ 
/*      */     
/*      */     public boolean tryAdvance(IntConsumer action) {
/*  906 */       if (this.pos >= getWorkingMax()) return false; 
/*  907 */       action.accept(BigArrays.get(IntBigArrayBigList.this.a, this.pos++));
/*  908 */       return true;
/*      */     }
/*      */ 
/*      */     
/*      */     public void forEachRemaining(IntConsumer action) {
/*  913 */       for (long max = getWorkingMax(); this.pos < max; this.pos++) {
/*  914 */         action.accept(BigArrays.get(IntBigArrayBigList.this.a, this.pos));
/*      */       }
/*      */     }
/*      */ 
/*      */     
/*      */     public long skip(long n) {
/*  920 */       if (n < 0L) throw new IllegalArgumentException("Argument must be nonnegative: " + n); 
/*  921 */       long max = getWorkingMax();
/*  922 */       if (this.pos >= max) return 0L; 
/*  923 */       long remaining = max - this.pos;
/*  924 */       if (n < remaining) {
/*  925 */         this.pos += n;
/*  926 */         return n;
/*      */       } 
/*  928 */       n = remaining;
/*  929 */       this.pos = max;
/*  930 */       return n;
/*      */     }
/*      */ 
/*      */     
/*      */     public IntSpliterator trySplit() {
/*  935 */       long max = getWorkingMax();
/*  936 */       long retLen = max - this.pos >> 1L;
/*  937 */       if (retLen <= 1L) return null;
/*      */       
/*  939 */       this.max = max;
/*  940 */       long myNewPos = this.pos + retLen;
/*      */ 
/*      */       
/*  943 */       myNewPos = BigArrays.nearestSegmentStart(myNewPos, this.pos + 1L, max - 1L);
/*  944 */       long retMax = myNewPos;
/*  945 */       long oldPos = this.pos;
/*  946 */       this.pos = myNewPos;
/*  947 */       this.hasSplit = true;
/*  948 */       return new Spliterator(oldPos, retMax, true);
/*      */     }
/*      */   }
/*      */ 
/*      */   
/*      */   public IntSpliterator spliterator() {
/*  954 */     return new Spliterator();
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public IntBigArrayBigList clone() {
/*      */     IntBigArrayBigList c;
/*  961 */     if (getClass() == IntBigArrayBigList.class) {
/*  962 */       c = new IntBigArrayBigList(this.size);
/*  963 */       c.size = this.size;
/*      */     } else {
/*      */       try {
/*  966 */         c = (IntBigArrayBigList)super.clone();
/*  967 */       } catch (CloneNotSupportedException e) {
/*      */         
/*  969 */         throw new InternalError(e);
/*      */       } 
/*  971 */       c.a = IntBigArrays.newBigArray(this.size);
/*      */     } 
/*  973 */     BigArrays.copy(this.a, 0L, c.a, 0L, this.size);
/*  974 */     return c;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean equals(IntBigArrayBigList l) {
/*  988 */     if (l == this) return true; 
/*  989 */     long s = size64();
/*  990 */     if (s != l.size64()) return false; 
/*  991 */     int[][] a1 = this.a;
/*  992 */     int[][] a2 = l.a;
/*      */     
/*  994 */     if (a1 == a2) return true;
/*      */     
/*  996 */     while (s-- != 0L) { if (BigArrays.get(a1, s) != BigArrays.get(a2, s)) return false;  }
/*  997 */      return true;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean equals(Object o) {
/* 1003 */     if (o == this) return true; 
/* 1004 */     if (o == null) return false; 
/* 1005 */     if (!(o instanceof BigList)) return false; 
/* 1006 */     if (o instanceof IntBigArrayBigList)
/*      */     {
/* 1008 */       return equals((IntBigArrayBigList)o);
/*      */     }
/* 1010 */     if (o instanceof SubList)
/*      */     {
/*      */       
/* 1013 */       return ((SubList)o).equals(this);
/*      */     }
/* 1015 */     return super.equals(o);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public int compareTo(IntBigArrayBigList l) {
/* 1031 */     long s1 = size64(), s2 = l.size64();
/* 1032 */     int[][] a1 = this.a, a2 = l.a;
/* 1033 */     if (a1 == a2 && s1 == s2) return 0;
/*      */     
/*      */     int i;
/* 1036 */     for (i = 0; i < s1 && i < s2; i++) {
/* 1037 */       int e1 = BigArrays.get(a1, i);
/* 1038 */       int e2 = BigArrays.get(a2, i); int r;
/* 1039 */       if ((r = Integer.compare(e1, e2)) != 0) return r; 
/*      */     } 
/* 1041 */     return (i < s2) ? -1 : ((i < s1) ? 1 : 0);
/*      */   }
/*      */ 
/*      */   
/*      */   public int compareTo(BigList<? extends Integer> l) {
/* 1046 */     if (l instanceof IntBigArrayBigList) {
/* 1047 */       return compareTo((IntBigArrayBigList)l);
/*      */     }
/* 1049 */     if (l instanceof SubList)
/*      */     {
/* 1051 */       return -((SubList)l).compareTo(this);
/*      */     }
/* 1053 */     return super.compareTo(l);
/*      */   }
/*      */   
/*      */   private void writeObject(ObjectOutputStream s) throws IOException {
/* 1057 */     s.defaultWriteObject();
/* 1058 */     for (int i = 0; i < this.size; ) { s.writeInt(BigArrays.get(this.a, i)); i++; }
/*      */   
/*      */   }
/*      */   private void readObject(ObjectInputStream s) throws IOException, ClassNotFoundException {
/* 1062 */     s.defaultReadObject();
/* 1063 */     this.a = IntBigArrays.newBigArray(this.size);
/* 1064 */     for (int i = 0; i < this.size; ) { BigArrays.set(this.a, i, s.readInt()); i++; }
/*      */   
/*      */   }
/*      */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\i\\unimi\dsi\fastutil\ints\IntBigArrayBigList.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */