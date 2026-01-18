/*      */ package it.unimi.dsi.fastutil.booleans;
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
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ public class BooleanBigArrayBigList
/*      */   extends AbstractBooleanBigList
/*      */   implements RandomAccess, Cloneable, Serializable
/*      */ {
/*      */   private static final long serialVersionUID = -7046029254386353130L;
/*      */   public static final int DEFAULT_INITIAL_CAPACITY = 10;
/*      */   protected transient boolean[][] a;
/*      */   protected long size;
/*      */   
/*      */   protected BooleanBigArrayBigList(boolean[][] a, boolean dummy) {
/*   67 */     this.a = a;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public BooleanBigArrayBigList(long capacity) {
/*   77 */     if (capacity < 0L) throw new IllegalArgumentException("Initial capacity (" + capacity + ") is negative"); 
/*   78 */     if (capacity == 0L) { this.a = BooleanBigArrays.EMPTY_BIG_ARRAY; }
/*   79 */     else { this.a = BooleanBigArrays.newBigArray(capacity); }
/*      */   
/*      */   }
/*      */ 
/*      */   
/*      */   public BooleanBigArrayBigList() {
/*   85 */     this.a = BooleanBigArrays.DEFAULT_EMPTY_BIG_ARRAY;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public BooleanBigArrayBigList(BooleanCollection c) {
/*   94 */     this(Size64.sizeOf(c));
/*      */     
/*   96 */     ((BooleanBigList)c).getElements(0L, this.a, 0L, this.size = Size64.sizeOf(c));
/*      */     
/*   98 */     for (BooleanIterator i = c.iterator(); i.hasNext(); add(i.nextBoolean()));
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public BooleanBigArrayBigList(BooleanBigList l) {
/*  108 */     this(l.size64());
/*  109 */     l.getElements(0L, this.a, 0L, this.size = l.size64());
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public BooleanBigArrayBigList(boolean[][] a) {
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
/*      */   public BooleanBigArrayBigList(boolean[][] a, long offset, long length) {
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
/*      */   public BooleanBigArrayBigList(Iterator<? extends Boolean> i) {
/*  140 */     this();
/*  141 */     for (; i.hasNext(); add(((Boolean)i.next()).booleanValue()));
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public BooleanBigArrayBigList(BooleanIterator i) {
/*  151 */     this();
/*  152 */     for (; i.hasNext(); add(i.nextBoolean()));
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean[][] elements() {
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
/*      */   public static BooleanBigArrayBigList wrap(boolean[][] a, long length) {
/*  172 */     if (length > BigArrays.length(a)) throw new IllegalArgumentException("The specified length (" + length + ") is greater than the array size (" + BigArrays.length(a) + ")"); 
/*  173 */     BooleanBigArrayBigList l = new BooleanBigArrayBigList(a, false);
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
/*      */   public static BooleanBigArrayBigList wrap(boolean[][] a) {
/*  185 */     return wrap(a, BigArrays.length(a));
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static BooleanBigArrayBigList of() {
/*  194 */     return new BooleanBigArrayBigList();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static BooleanBigArrayBigList of(boolean... init) {
/*  208 */     return wrap(BigArrays.wrap(init));
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void ensureCapacity(long capacity) {
/*  218 */     if (capacity <= BigArrays.length(this.a) || this.a == BooleanBigArrays.DEFAULT_EMPTY_BIG_ARRAY)
/*  219 */       return;  this.a = BigArrays.forceCapacity(this.a, capacity, this.size);
/*  220 */     assert this.size <= BigArrays.length(this.a);
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
/*  231 */     long oldLength = BigArrays.length(this.a);
/*  232 */     if (capacity <= oldLength)
/*  233 */       return;  if (this.a != BooleanBigArrays.DEFAULT_EMPTY_BIG_ARRAY) { capacity = Math.max(oldLength + (oldLength >> 1L), capacity); }
/*  234 */     else if (capacity < 10L) { capacity = 10L; }
/*  235 */      this.a = BigArrays.forceCapacity(this.a, capacity, this.size);
/*  236 */     assert this.size <= BigArrays.length(this.a);
/*      */   }
/*      */ 
/*      */   
/*      */   public void add(long index, boolean k) {
/*  241 */     ensureIndex(index);
/*  242 */     grow(this.size + 1L);
/*  243 */     if (index != this.size) BigArrays.copy(this.a, index, this.a, index + 1L, this.size - index); 
/*  244 */     BigArrays.set(this.a, index, k);
/*  245 */     this.size++;
/*  246 */     assert this.size <= BigArrays.length(this.a);
/*      */   }
/*      */ 
/*      */   
/*      */   public boolean add(boolean k) {
/*  251 */     grow(this.size + 1L);
/*  252 */     BigArrays.set(this.a, this.size++, k);
/*  253 */     if (!$assertionsDisabled && this.size > BigArrays.length(this.a)) throw new AssertionError(); 
/*  254 */     return true;
/*      */   }
/*      */ 
/*      */   
/*      */   public boolean getBoolean(long index) {
/*  259 */     if (index >= this.size) throw new IndexOutOfBoundsException("Index (" + index + ") is greater than or equal to list size (" + this.size + ")"); 
/*  260 */     return BigArrays.get(this.a, index);
/*      */   }
/*      */ 
/*      */   
/*      */   public long indexOf(boolean k) {
/*  265 */     for (long i = 0L; i < this.size; ) { if (k == BigArrays.get(this.a, i)) return i;  i++; }
/*  266 */      return -1L;
/*      */   }
/*      */ 
/*      */   
/*      */   public long lastIndexOf(boolean k) {
/*  271 */     for (long i = this.size; i-- != 0L;) { if (k == BigArrays.get(this.a, i)) return i;  }
/*  272 */      return -1L;
/*      */   }
/*      */ 
/*      */   
/*      */   public boolean removeBoolean(long index) {
/*  277 */     if (index >= this.size) throw new IndexOutOfBoundsException("Index (" + index + ") is greater than or equal to list size (" + this.size + ")"); 
/*  278 */     boolean old = BigArrays.get(this.a, index);
/*  279 */     this.size--;
/*  280 */     if (index != this.size) BigArrays.copy(this.a, index + 1L, this.a, index, this.size - index); 
/*  281 */     assert this.size <= BigArrays.length(this.a);
/*  282 */     return old;
/*      */   }
/*      */ 
/*      */   
/*      */   public boolean rem(boolean k) {
/*  287 */     long index = indexOf(k);
/*  288 */     if (index == -1L) return false; 
/*  289 */     removeBoolean(index);
/*  290 */     assert this.size <= BigArrays.length(this.a);
/*  291 */     return true;
/*      */   }
/*      */ 
/*      */   
/*      */   public boolean set(long index, boolean k) {
/*  296 */     if (index >= this.size) throw new IndexOutOfBoundsException("Index (" + index + ") is greater than or equal to list size (" + this.size + ")"); 
/*  297 */     boolean old = BigArrays.get(this.a, index);
/*  298 */     BigArrays.set(this.a, index, k);
/*  299 */     return old;
/*      */   }
/*      */ 
/*      */   
/*      */   public boolean removeAll(BooleanCollection c) {
/*  304 */     boolean[] s = null, d = null;
/*  305 */     int ss = -1, sd = 134217728, ds = -1, dd = 134217728; long i;
/*  306 */     for (i = 0L; i < this.size; i++) {
/*  307 */       if (sd == 134217728) {
/*  308 */         sd = 0;
/*  309 */         s = this.a[++ss];
/*      */       } 
/*  311 */       if (!c.contains(s[sd])) {
/*  312 */         if (dd == 134217728) {
/*  313 */           d = this.a[++ds];
/*  314 */           dd = 0;
/*      */         } 
/*  316 */         d[dd++] = s[sd];
/*      */       } 
/*  318 */       sd++;
/*      */     } 
/*  320 */     long j = BigArrays.index(ds, dd);
/*  321 */     boolean modified = (this.size != j);
/*  322 */     this.size = j;
/*  323 */     return modified;
/*      */   }
/*      */ 
/*      */   
/*      */   public boolean removeAll(Collection<?> c) {
/*  328 */     boolean[] s = null, d = null;
/*  329 */     int ss = -1, sd = 134217728, ds = -1, dd = 134217728; long i;
/*  330 */     for (i = 0L; i < this.size; i++) {
/*  331 */       if (sd == 134217728) {
/*  332 */         sd = 0;
/*  333 */         s = this.a[++ss];
/*      */       } 
/*  335 */       if (!c.contains(Boolean.valueOf(s[sd]))) {
/*  336 */         if (dd == 134217728) {
/*  337 */           d = this.a[++ds];
/*  338 */           dd = 0;
/*      */         } 
/*  340 */         d[dd++] = s[sd];
/*      */       } 
/*  342 */       sd++;
/*      */     } 
/*  344 */     long j = BigArrays.index(ds, dd);
/*  345 */     boolean modified = (this.size != j);
/*  346 */     this.size = j;
/*  347 */     return modified;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean addAll(long index, BooleanCollection c) {
/*      */     // Byte code:
/*      */     //   0: aload_3
/*      */     //   1: instanceof it/unimi/dsi/fastutil/booleans/BooleanList
/*      */     //   4: ifeq -> 17
/*      */     //   7: aload_0
/*      */     //   8: lload_1
/*      */     //   9: aload_3
/*      */     //   10: checkcast it/unimi/dsi/fastutil/booleans/BooleanList
/*      */     //   13: invokevirtual addAll : (JLit/unimi/dsi/fastutil/booleans/BooleanList;)Z
/*      */     //   16: ireturn
/*      */     //   17: aload_3
/*      */     //   18: instanceof it/unimi/dsi/fastutil/booleans/BooleanBigList
/*      */     //   21: ifeq -> 34
/*      */     //   24: aload_0
/*      */     //   25: lload_1
/*      */     //   26: aload_3
/*      */     //   27: checkcast it/unimi/dsi/fastutil/booleans/BooleanBigList
/*      */     //   30: invokevirtual addAll : (JLit/unimi/dsi/fastutil/booleans/BooleanBigList;)Z
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
/*      */     //   67: getfield a : [[Z
/*      */     //   70: lload_1
/*      */     //   71: aload_0
/*      */     //   72: getfield a : [[Z
/*      */     //   75: lload_1
/*      */     //   76: iload #4
/*      */     //   78: i2l
/*      */     //   79: ladd
/*      */     //   80: aload_0
/*      */     //   81: getfield size : J
/*      */     //   84: lload_1
/*      */     //   85: lsub
/*      */     //   86: invokestatic copy : ([[ZJ[[ZJJ)V
/*      */     //   89: aload_3
/*      */     //   90: invokeinterface iterator : ()Lit/unimi/dsi/fastutil/booleans/BooleanIterator;
/*      */     //   95: astore #5
/*      */     //   97: aload_0
/*      */     //   98: dup
/*      */     //   99: getfield size : J
/*      */     //   102: iload #4
/*      */     //   104: i2l
/*      */     //   105: ladd
/*      */     //   106: putfield size : J
/*      */     //   109: getstatic it/unimi/dsi/fastutil/booleans/BooleanBigArrayBigList.$assertionsDisabled : Z
/*      */     //   112: ifne -> 138
/*      */     //   115: aload_0
/*      */     //   116: getfield size : J
/*      */     //   119: aload_0
/*      */     //   120: getfield a : [[Z
/*      */     //   123: invokestatic length : ([[Z)J
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
/*      */     //   147: getfield a : [[Z
/*      */     //   150: lload_1
/*      */     //   151: dup2
/*      */     //   152: lconst_1
/*      */     //   153: ladd
/*      */     //   154: lstore_1
/*      */     //   155: aload #5
/*      */     //   157: invokeinterface nextBoolean : ()Z
/*      */     //   162: invokestatic set : ([[ZJZ)V
/*      */     //   165: goto -> 138
/*      */     //   168: iconst_1
/*      */     //   169: ireturn
/*      */     // Line number table:
/*      */     //   Java source line number -> byte code offset
/*      */     //   #352	-> 0
/*      */     //   #353	-> 7
/*      */     //   #355	-> 17
/*      */     //   #356	-> 24
/*      */     //   #358	-> 34
/*      */     //   #359	-> 39
/*      */     //   #360	-> 47
/*      */     //   #361	-> 54
/*      */     //   #362	-> 66
/*      */     //   #363	-> 89
/*      */     //   #364	-> 97
/*      */     //   #365	-> 109
/*      */     //   #366	-> 138
/*      */     //   #367	-> 168
/*      */     // Local variable table:
/*      */     //   start	length	slot	name	descriptor
/*      */     //   0	170	0	this	Lit/unimi/dsi/fastutil/booleans/BooleanBigArrayBigList;
/*      */     //   0	170	1	index	J
/*      */     //   0	170	3	c	Lit/unimi/dsi/fastutil/booleans/BooleanCollection;
/*      */     //   47	123	4	n	I
/*      */     //   97	73	5	i	Lit/unimi/dsi/fastutil/booleans/BooleanIterator;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean addAll(long index, BooleanBigList list) {
/*  372 */     ensureIndex(index);
/*  373 */     long n = list.size64();
/*  374 */     if (n == 0L) return false; 
/*  375 */     grow(this.size + n);
/*  376 */     BigArrays.copy(this.a, index, this.a, index + n, this.size - index);
/*  377 */     list.getElements(0L, this.a, index, n);
/*  378 */     this.size += n;
/*  379 */     assert this.size <= BigArrays.length(this.a);
/*  380 */     return true;
/*      */   }
/*      */ 
/*      */   
/*      */   public boolean addAll(long index, BooleanList list) {
/*  385 */     ensureIndex(index);
/*  386 */     int n = list.size();
/*  387 */     if (n == 0) return false; 
/*  388 */     grow(this.size + n);
/*  389 */     BigArrays.copy(this.a, index, this.a, index + n, this.size - index);
/*  390 */     this.size += n;
/*  391 */     assert this.size <= BigArrays.length(this.a);
/*  392 */     int segment = BigArrays.segment(index);
/*  393 */     int displ = BigArrays.displacement(index);
/*  394 */     int pos = 0;
/*  395 */     while (n > 0) {
/*  396 */       int l = Math.min((this.a[segment]).length - displ, n);
/*  397 */       list.getElements(pos, this.a[segment], displ, l);
/*  398 */       if ((displ += l) == 134217728) {
/*  399 */         displ = 0;
/*  400 */         segment++;
/*      */       } 
/*  402 */       pos += l;
/*  403 */       n -= l;
/*      */     } 
/*  405 */     return true;
/*      */   }
/*      */ 
/*      */   
/*      */   public void clear() {
/*  410 */     this.size = 0L;
/*  411 */     assert this.size <= BigArrays.length(this.a);
/*      */   }
/*      */ 
/*      */   
/*      */   public long size64() {
/*  416 */     return this.size;
/*      */   }
/*      */ 
/*      */   
/*      */   public void size(long size) {
/*  421 */     if (size > BigArrays.length(this.a)) this.a = BigArrays.forceCapacity(this.a, size, this.size); 
/*  422 */     if (size > this.size) BigArrays.fill(this.a, this.size, size, false); 
/*  423 */     this.size = size;
/*      */   }
/*      */ 
/*      */   
/*      */   public boolean isEmpty() {
/*  428 */     return (this.size == 0L);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void trim() {
/*  437 */     trim(0L);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
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
/*  455 */     long arrayLength = BigArrays.length(this.a);
/*  456 */     if (n >= arrayLength || this.size == arrayLength)
/*  457 */       return;  this.a = BigArrays.trim(this.a, Math.max(n, this.size));
/*  458 */     assert this.size <= BigArrays.length(this.a);
/*      */   }
/*      */   
/*      */   private class SubList extends AbstractBooleanBigList.BooleanRandomAccessSubList {
/*      */     private static final long serialVersionUID = -3185226345314976296L;
/*      */     
/*      */     protected SubList(long from, long to) {
/*  465 */       super(from, to);
/*      */     }
/*      */ 
/*      */ 
/*      */     
/*      */     private boolean[][] getParentArray() {
/*  471 */       return BooleanBigArrayBigList.this.a;
/*      */     }
/*      */ 
/*      */ 
/*      */     
/*      */     public boolean getBoolean(long i) {
/*  477 */       ensureRestrictedIndex(i);
/*  478 */       return BigArrays.get(BooleanBigArrayBigList.this.a, i + this.from);
/*      */     }
/*      */     
/*      */     private final class SubListIterator
/*      */       extends BooleanBigListIterators.AbstractIndexBasedBigListIterator
/*      */     {
/*      */       SubListIterator(long index) {
/*  485 */         super(0L, index);
/*      */       }
/*      */ 
/*      */       
/*      */       protected final boolean get(long i) {
/*  490 */         return BigArrays.get(BooleanBigArrayBigList.this.a, BooleanBigArrayBigList.SubList.this.from + i);
/*      */       }
/*      */ 
/*      */       
/*      */       protected final void add(long i, boolean k) {
/*  495 */         BooleanBigArrayBigList.SubList.this.add(i, k);
/*      */       }
/*      */ 
/*      */       
/*      */       protected final void set(long i, boolean k) {
/*  500 */         BooleanBigArrayBigList.SubList.this.set(i, k);
/*      */       }
/*      */ 
/*      */       
/*      */       protected final void remove(long i) {
/*  505 */         BooleanBigArrayBigList.SubList.this.removeBoolean(i);
/*      */       }
/*      */ 
/*      */       
/*      */       protected final long getMaxPos() {
/*  510 */         return BooleanBigArrayBigList.SubList.this.to - BooleanBigArrayBigList.SubList.this.from;
/*      */       }
/*      */ 
/*      */       
/*      */       public boolean nextBoolean() {
/*  515 */         if (!hasNext()) throw new NoSuchElementException(); 
/*  516 */         return BigArrays.get(BooleanBigArrayBigList.this.a, BooleanBigArrayBigList.SubList.this.from + (this.lastReturned = this.pos++));
/*      */       }
/*      */ 
/*      */       
/*      */       public boolean previousBoolean() {
/*  521 */         if (!hasPrevious()) throw new NoSuchElementException(); 
/*  522 */         return BigArrays.get(BooleanBigArrayBigList.this.a, BooleanBigArrayBigList.SubList.this.from + (this.lastReturned = --this.pos));
/*      */       }
/*      */ 
/*      */ 
/*      */ 
/*      */       
/*      */       public void forEachRemaining(BooleanConsumer action) {
/*      */         // Byte code:
/*      */         //   0: aload_0
/*      */         //   1: getfield this$1 : Lit/unimi/dsi/fastutil/booleans/BooleanBigArrayBigList$SubList;
/*      */         //   4: getfield to : J
/*      */         //   7: aload_0
/*      */         //   8: getfield this$1 : Lit/unimi/dsi/fastutil/booleans/BooleanBigArrayBigList$SubList;
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
/*      */         //   27: getfield this$1 : Lit/unimi/dsi/fastutil/booleans/BooleanBigArrayBigList$SubList;
/*      */         //   30: getfield this$0 : Lit/unimi/dsi/fastutil/booleans/BooleanBigArrayBigList;
/*      */         //   33: getfield a : [[Z
/*      */         //   36: aload_0
/*      */         //   37: getfield this$1 : Lit/unimi/dsi/fastutil/booleans/BooleanBigArrayBigList$SubList;
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
/*      */         //   60: invokestatic get : ([[ZJ)Z
/*      */         //   63: invokeinterface accept : (Z)V
/*      */         //   68: goto -> 16
/*      */         //   71: return
/*      */         // Line number table:
/*      */         //   Java source line number -> byte code offset
/*      */         //   #527	-> 0
/*      */         //   #528	-> 16
/*      */         //   #529	-> 25
/*      */         //   #531	-> 71
/*      */         // Local variable table:
/*      */         //   start	length	slot	name	descriptor
/*      */         //   0	72	0	this	Lit/unimi/dsi/fastutil/booleans/BooleanBigArrayBigList$SubList$SubListIterator;
/*      */         //   0	72	1	action	Lit/unimi/dsi/fastutil/booleans/BooleanConsumer;
/*      */         //   16	56	2	max	J
/*      */       }
/*      */     }
/*      */ 
/*      */ 
/*      */     
/*      */     public BooleanBigListIterator listIterator(long index) {
/*  536 */       return new SubListIterator(index);
/*      */     }
/*      */     
/*      */     private final class SubListSpliterator
/*      */       extends BooleanBigSpliterators.LateBindingSizeIndexBasedSpliterator {
/*      */       SubListSpliterator() {
/*  542 */         super(BooleanBigArrayBigList.SubList.this.from);
/*      */       }
/*      */       
/*      */       private SubListSpliterator(long pos, long maxPos) {
/*  546 */         super(pos, maxPos);
/*      */       }
/*      */ 
/*      */       
/*      */       protected final long getMaxPosFromBackingStore() {
/*  551 */         return BooleanBigArrayBigList.SubList.this.to;
/*      */       }
/*      */ 
/*      */       
/*      */       protected final boolean get(long i) {
/*  556 */         return BigArrays.get(BooleanBigArrayBigList.this.a, i);
/*      */       }
/*      */ 
/*      */       
/*      */       protected final SubListSpliterator makeForSplit(long pos, long maxPos) {
/*  561 */         return new SubListSpliterator(pos, maxPos);
/*      */       }
/*      */ 
/*      */       
/*      */       protected final long computeSplitPoint() {
/*  566 */         long defaultSplit = super.computeSplitPoint();
/*      */ 
/*      */         
/*  569 */         return BigArrays.nearestSegmentStart(defaultSplit, this.pos + 1L, getMaxPos() - 1L);
/*      */       }
/*      */ 
/*      */       
/*      */       public boolean tryAdvance(BooleanConsumer action) {
/*  574 */         if (this.pos >= getMaxPos()) return false; 
/*  575 */         action.accept(BigArrays.get(BooleanBigArrayBigList.this.a, this.pos++));
/*  576 */         return true;
/*      */       }
/*      */ 
/*      */ 
/*      */ 
/*      */       
/*      */       public void forEachRemaining(BooleanConsumer action) {
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
/*      */         //   16: getfield this$1 : Lit/unimi/dsi/fastutil/booleans/BooleanBigArrayBigList$SubList;
/*      */         //   19: getfield this$0 : Lit/unimi/dsi/fastutil/booleans/BooleanBigArrayBigList;
/*      */         //   22: getfield a : [[Z
/*      */         //   25: aload_0
/*      */         //   26: dup
/*      */         //   27: getfield pos : J
/*      */         //   30: dup2_x1
/*      */         //   31: lconst_1
/*      */         //   32: ladd
/*      */         //   33: putfield pos : J
/*      */         //   36: invokestatic get : ([[ZJ)Z
/*      */         //   39: invokeinterface accept : (Z)V
/*      */         //   44: goto -> 5
/*      */         //   47: return
/*      */         // Line number table:
/*      */         //   Java source line number -> byte code offset
/*      */         //   #581	-> 0
/*      */         //   #582	-> 5
/*      */         //   #583	-> 14
/*      */         //   #585	-> 47
/*      */         // Local variable table:
/*      */         //   start	length	slot	name	descriptor
/*      */         //   0	48	0	this	Lit/unimi/dsi/fastutil/booleans/BooleanBigArrayBigList$SubList$SubListSpliterator;
/*      */         //   0	48	1	action	Lit/unimi/dsi/fastutil/booleans/BooleanConsumer;
/*      */         //   5	43	2	max	J
/*      */       }
/*      */     }
/*      */ 
/*      */ 
/*      */     
/*      */     public BooleanSpliterator spliterator() {
/*  590 */       return new SubListSpliterator();
/*      */     }
/*      */     
/*      */     boolean contentsEquals(boolean[][] otherA, long otherAFrom, long otherATo) {
/*  594 */       if (BooleanBigArrayBigList.this.a == otherA && this.from == otherAFrom && this.to == otherATo) return true; 
/*  595 */       if (otherATo - otherAFrom != size64()) {
/*  596 */         return false;
/*      */       }
/*  598 */       long pos = this.to, otherPos = otherATo;
/*      */ 
/*      */ 
/*      */ 
/*      */       
/*  603 */       while (--pos >= this.from) { if (BigArrays.get(BooleanBigArrayBigList.this.a, pos) != BigArrays.get(otherA, --otherPos)) return false;  }
/*  604 */        return true;
/*      */     }
/*      */ 
/*      */     
/*      */     public boolean equals(Object o) {
/*  609 */       if (o == this) return true; 
/*  610 */       if (o == null) return false; 
/*  611 */       if (!(o instanceof BigList)) return false; 
/*  612 */       if (o instanceof BooleanBigArrayBigList) {
/*      */         
/*  614 */         BooleanBigArrayBigList other = (BooleanBigArrayBigList)o;
/*  615 */         return contentsEquals(other.a, 0L, other.size64());
/*      */       } 
/*  617 */       if (o instanceof SubList) {
/*      */         
/*  619 */         SubList other = (SubList)o;
/*  620 */         return contentsEquals(other.getParentArray(), other.from, other.to);
/*      */       } 
/*  622 */       return super.equals(o);
/*      */     }
/*      */     
/*      */     int contentsCompareTo(boolean[][] otherA, long otherAFrom, long otherATo) {
/*  626 */       if (BooleanBigArrayBigList.this.a == otherA && this.from == otherAFrom && this.to == otherATo) return 0;
/*      */ 
/*      */       
/*      */       long i;
/*      */       long j;
/*  631 */       for (i = this.from, j = otherAFrom; i < this.to && i < otherATo; i++, j++) {
/*  632 */         boolean e1 = BigArrays.get(BooleanBigArrayBigList.this.a, i);
/*  633 */         boolean e2 = BigArrays.get(otherA, j); int r;
/*  634 */         if ((r = Boolean.compare(e1, e2)) != 0) return r; 
/*      */       } 
/*  636 */       return (i < otherATo) ? -1 : ((i < this.to) ? 1 : 0);
/*      */     }
/*      */ 
/*      */     
/*      */     public int compareTo(BigList<? extends Boolean> l) {
/*  641 */       if (l instanceof BooleanBigArrayBigList) {
/*      */         
/*  643 */         BooleanBigArrayBigList other = (BooleanBigArrayBigList)l;
/*  644 */         return contentsCompareTo(other.a, 0L, other.size64());
/*      */       } 
/*  646 */       if (l instanceof SubList) {
/*      */         
/*  648 */         SubList other = (SubList)l;
/*  649 */         return contentsCompareTo(other.getParentArray(), other.from, other.to);
/*      */       } 
/*  651 */       return super.compareTo(l);
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public BooleanBigList subList(long from, long to) {
/*  661 */     if (from == 0L && to == size64()) return this; 
/*  662 */     ensureIndex(from);
/*  663 */     ensureIndex(to);
/*  664 */     if (from > to) throw new IndexOutOfBoundsException("Start index (" + from + ") is greater than end index (" + to + ")"); 
/*  665 */     return new SubList(from, to);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void getElements(long from, boolean[][] a, long offset, long length) {
/*  678 */     BigArrays.copy(this.a, from, a, offset, length);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void getElements(long from, boolean[] a, int offset, int length) {
/*  691 */     BigArrays.copyFromBig(this.a, from, a, offset, length);
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
/*  702 */     BigArrays.ensureFromTo(this.size, from, to);
/*  703 */     BigArrays.copy(this.a, to, this.a, from, this.size - to);
/*  704 */     this.size -= to - from;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void addElements(long index, boolean[][] a, long offset, long length) {
/*  717 */     ensureIndex(index);
/*  718 */     BigArrays.ensureOffsetLength(a, offset, length);
/*  719 */     grow(this.size + length);
/*  720 */     BigArrays.copy(this.a, index, this.a, index + length, this.size - index);
/*  721 */     BigArrays.copy(a, offset, this.a, index, length);
/*  722 */     this.size += length;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setElements(long index, boolean[][] a, long offset, long length) {
/*  735 */     BigArrays.copy(a, offset, this.a, index, length);
/*      */   }
/*      */ 
/*      */   
/*      */   public void forEach(BooleanConsumer action) {
/*  740 */     for (long i = 0L; i < this.size; i++) {
/*  741 */       action.accept(BigArrays.get(this.a, i));
/*      */     }
/*      */   }
/*      */ 
/*      */   
/*      */   public BooleanBigListIterator listIterator(final long index) {
/*  747 */     ensureIndex(index);
/*  748 */     return new BooleanBigListIterator() {
/*  749 */         long pos = index; long last = -1L;
/*      */ 
/*      */         
/*      */         public boolean hasNext() {
/*  753 */           return (this.pos < BooleanBigArrayBigList.this.size);
/*      */         }
/*      */ 
/*      */         
/*      */         public boolean hasPrevious() {
/*  758 */           return (this.pos > 0L);
/*      */         }
/*      */ 
/*      */         
/*      */         public boolean nextBoolean() {
/*  763 */           if (!hasNext()) throw new NoSuchElementException(); 
/*  764 */           return BigArrays.get(BooleanBigArrayBigList.this.a, this.last = this.pos++);
/*      */         }
/*      */ 
/*      */         
/*      */         public boolean previousBoolean() {
/*  769 */           if (!hasPrevious()) throw new NoSuchElementException(); 
/*  770 */           return BigArrays.get(BooleanBigArrayBigList.this.a, this.last = --this.pos);
/*      */         }
/*      */ 
/*      */         
/*      */         public long nextIndex() {
/*  775 */           return this.pos;
/*      */         }
/*      */ 
/*      */         
/*      */         public long previousIndex() {
/*  780 */           return this.pos - 1L;
/*      */         }
/*      */ 
/*      */         
/*      */         public void add(boolean k) {
/*  785 */           BooleanBigArrayBigList.this.add(this.pos++, k);
/*  786 */           this.last = -1L;
/*      */         }
/*      */ 
/*      */         
/*      */         public void set(boolean k) {
/*  791 */           if (this.last == -1L) throw new IllegalStateException(); 
/*  792 */           BooleanBigArrayBigList.this.set(this.last, k);
/*      */         }
/*      */ 
/*      */         
/*      */         public void remove() {
/*  797 */           if (this.last == -1L) throw new IllegalStateException(); 
/*  798 */           BooleanBigArrayBigList.this.removeBoolean(this.last);
/*      */           
/*  800 */           if (this.last < this.pos) this.pos--; 
/*  801 */           this.last = -1L;
/*      */         }
/*      */ 
/*      */ 
/*      */         
/*      */         public void forEachRemaining(BooleanConsumer action) {
/*      */           // Byte code:
/*      */           //   0: aload_0
/*      */           //   1: getfield pos : J
/*      */           //   4: aload_0
/*      */           //   5: getfield this$0 : Lit/unimi/dsi/fastutil/booleans/BooleanBigArrayBigList;
/*      */           //   8: getfield size : J
/*      */           //   11: lcmp
/*      */           //   12: ifge -> 50
/*      */           //   15: aload_1
/*      */           //   16: aload_0
/*      */           //   17: getfield this$0 : Lit/unimi/dsi/fastutil/booleans/BooleanBigArrayBigList;
/*      */           //   20: getfield a : [[Z
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
/*      */           //   39: invokestatic get : ([[ZJ)Z
/*      */           //   42: invokeinterface accept : (Z)V
/*      */           //   47: goto -> 0
/*      */           //   50: return
/*      */           // Line number table:
/*      */           //   Java source line number -> byte code offset
/*      */           //   #806	-> 0
/*      */           //   #807	-> 15
/*      */           //   #809	-> 50
/*      */           // Local variable table:
/*      */           //   start	length	slot	name	descriptor
/*      */           //   0	51	0	this	Lit/unimi/dsi/fastutil/booleans/BooleanBigArrayBigList$1;
/*      */           //   0	51	1	action	Lit/unimi/dsi/fastutil/booleans/BooleanConsumer;
/*      */         }
/*      */ 
/*      */ 
/*      */         
/*      */         public long back(long n) {
/*  813 */           if (n < 0L) throw new IllegalArgumentException("Argument must be nonnegative: " + n); 
/*  814 */           long remaining = BooleanBigArrayBigList.this.size - this.pos;
/*  815 */           if (n < remaining) {
/*  816 */             this.pos -= n;
/*      */           } else {
/*  818 */             n = remaining;
/*  819 */             this.pos = 0L;
/*      */           } 
/*  821 */           this.last = this.pos;
/*  822 */           return n;
/*      */         }
/*      */ 
/*      */         
/*      */         public long skip(long n) {
/*  827 */           if (n < 0L) throw new IllegalArgumentException("Argument must be nonnegative: " + n); 
/*  828 */           long remaining = BooleanBigArrayBigList.this.size - this.pos;
/*  829 */           if (n < remaining) {
/*  830 */             this.pos += n;
/*      */           } else {
/*  832 */             n = remaining;
/*  833 */             this.pos = BooleanBigArrayBigList.this.size;
/*      */           } 
/*  835 */           this.last = this.pos - 1L;
/*  836 */           return n;
/*      */         }
/*      */       };
/*      */   }
/*      */   
/*      */   private final class Spliterator
/*      */     implements BooleanSpliterator
/*      */   {
/*      */     boolean hasSplit = false;
/*      */     long pos;
/*      */     long max;
/*      */     
/*      */     public Spliterator() {
/*  849 */       this(0L, BooleanBigArrayBigList.this.size, false);
/*      */     }
/*      */     
/*      */     private Spliterator(long pos, long max, boolean hasSplit) {
/*  853 */       assert pos <= max : "pos " + pos + " must be <= max " + max;
/*  854 */       this.pos = pos;
/*  855 */       this.max = max;
/*  856 */       this.hasSplit = hasSplit;
/*      */     }
/*      */     
/*      */     private long getWorkingMax() {
/*  860 */       return this.hasSplit ? this.max : BooleanBigArrayBigList.this.size;
/*      */     }
/*      */ 
/*      */     
/*      */     public int characteristics() {
/*  865 */       return 16720;
/*      */     }
/*      */ 
/*      */     
/*      */     public long estimateSize() {
/*  870 */       return getWorkingMax() - this.pos;
/*      */     }
/*      */ 
/*      */     
/*      */     public boolean tryAdvance(BooleanConsumer action) {
/*  875 */       if (this.pos >= getWorkingMax()) return false; 
/*  876 */       action.accept(BigArrays.get(BooleanBigArrayBigList.this.a, this.pos++));
/*  877 */       return true;
/*      */     }
/*      */ 
/*      */     
/*      */     public void forEachRemaining(BooleanConsumer action) {
/*  882 */       for (long max = getWorkingMax(); this.pos < max; this.pos++) {
/*  883 */         action.accept(BigArrays.get(BooleanBigArrayBigList.this.a, this.pos));
/*      */       }
/*      */     }
/*      */ 
/*      */     
/*      */     public long skip(long n) {
/*  889 */       if (n < 0L) throw new IllegalArgumentException("Argument must be nonnegative: " + n); 
/*  890 */       long max = getWorkingMax();
/*  891 */       if (this.pos >= max) return 0L; 
/*  892 */       long remaining = max - this.pos;
/*  893 */       if (n < remaining) {
/*  894 */         this.pos += n;
/*  895 */         return n;
/*      */       } 
/*  897 */       n = remaining;
/*  898 */       this.pos = max;
/*  899 */       return n;
/*      */     }
/*      */ 
/*      */     
/*      */     public BooleanSpliterator trySplit() {
/*  904 */       long max = getWorkingMax();
/*  905 */       long retLen = max - this.pos >> 1L;
/*  906 */       if (retLen <= 1L) return null;
/*      */       
/*  908 */       this.max = max;
/*  909 */       long myNewPos = this.pos + retLen;
/*      */ 
/*      */       
/*  912 */       myNewPos = BigArrays.nearestSegmentStart(myNewPos, this.pos + 1L, max - 1L);
/*  913 */       long retMax = myNewPos;
/*  914 */       long oldPos = this.pos;
/*  915 */       this.pos = myNewPos;
/*  916 */       this.hasSplit = true;
/*  917 */       return new Spliterator(oldPos, retMax, true);
/*      */     }
/*      */   }
/*      */ 
/*      */   
/*      */   public BooleanSpliterator spliterator() {
/*  923 */     return new Spliterator();
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public BooleanBigArrayBigList clone() {
/*      */     BooleanBigArrayBigList c;
/*  930 */     if (getClass() == BooleanBigArrayBigList.class) {
/*  931 */       c = new BooleanBigArrayBigList(this.size);
/*  932 */       c.size = this.size;
/*      */     } else {
/*      */       try {
/*  935 */         c = (BooleanBigArrayBigList)super.clone();
/*  936 */       } catch (CloneNotSupportedException e) {
/*      */         
/*  938 */         throw new InternalError(e);
/*      */       } 
/*  940 */       c.a = BooleanBigArrays.newBigArray(this.size);
/*      */     } 
/*  942 */     BigArrays.copy(this.a, 0L, c.a, 0L, this.size);
/*  943 */     return c;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean equals(BooleanBigArrayBigList l) {
/*  957 */     if (l == this) return true; 
/*  958 */     long s = size64();
/*  959 */     if (s != l.size64()) return false; 
/*  960 */     boolean[][] a1 = this.a;
/*  961 */     boolean[][] a2 = l.a;
/*      */     
/*  963 */     if (a1 == a2) return true;
/*      */     
/*  965 */     while (s-- != 0L) { if (BigArrays.get(a1, s) != BigArrays.get(a2, s)) return false;  }
/*  966 */      return true;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean equals(Object o) {
/*  972 */     if (o == this) return true; 
/*  973 */     if (o == null) return false; 
/*  974 */     if (!(o instanceof BigList)) return false; 
/*  975 */     if (o instanceof BooleanBigArrayBigList)
/*      */     {
/*  977 */       return equals((BooleanBigArrayBigList)o);
/*      */     }
/*  979 */     if (o instanceof SubList)
/*      */     {
/*      */       
/*  982 */       return ((SubList)o).equals(this);
/*      */     }
/*  984 */     return super.equals(o);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public int compareTo(BooleanBigArrayBigList l) {
/* 1000 */     long s1 = size64(), s2 = l.size64();
/* 1001 */     boolean[][] a1 = this.a, a2 = l.a;
/* 1002 */     if (a1 == a2 && s1 == s2) return 0;
/*      */     
/*      */     int i;
/* 1005 */     for (i = 0; i < s1 && i < s2; i++) {
/* 1006 */       boolean e1 = BigArrays.get(a1, i);
/* 1007 */       boolean e2 = BigArrays.get(a2, i); int r;
/* 1008 */       if ((r = Boolean.compare(e1, e2)) != 0) return r; 
/*      */     } 
/* 1010 */     return (i < s2) ? -1 : ((i < s1) ? 1 : 0);
/*      */   }
/*      */ 
/*      */   
/*      */   public int compareTo(BigList<? extends Boolean> l) {
/* 1015 */     if (l instanceof BooleanBigArrayBigList) {
/* 1016 */       return compareTo((BooleanBigArrayBigList)l);
/*      */     }
/* 1018 */     if (l instanceof SubList)
/*      */     {
/* 1020 */       return -((SubList)l).compareTo(this);
/*      */     }
/* 1022 */     return super.compareTo(l);
/*      */   }
/*      */   
/*      */   private void writeObject(ObjectOutputStream s) throws IOException {
/* 1026 */     s.defaultWriteObject();
/* 1027 */     for (int i = 0; i < this.size; ) { s.writeBoolean(BigArrays.get(this.a, i)); i++; }
/*      */   
/*      */   }
/*      */   private void readObject(ObjectInputStream s) throws IOException, ClassNotFoundException {
/* 1031 */     s.defaultReadObject();
/* 1032 */     this.a = BooleanBigArrays.newBigArray(this.size);
/* 1033 */     for (int i = 0; i < this.size; ) { BigArrays.set(this.a, i, s.readBoolean()); i++; }
/*      */   
/*      */   }
/*      */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\i\\unimi\dsi\fastutil\booleans\BooleanBigArrayBigList.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */