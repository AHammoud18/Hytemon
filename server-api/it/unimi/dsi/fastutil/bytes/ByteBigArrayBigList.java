/*      */ package it.unimi.dsi.fastutil.bytes;
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
/*      */ public class ByteBigArrayBigList
/*      */   extends AbstractByteBigList
/*      */   implements RandomAccess, Cloneable, Serializable
/*      */ {
/*      */   private static final long serialVersionUID = -7046029254386353130L;
/*      */   public static final int DEFAULT_INITIAL_CAPACITY = 10;
/*      */   protected transient byte[][] a;
/*      */   protected long size;
/*      */   
/*      */   protected ByteBigArrayBigList(byte[][] a, boolean dummy) {
/*   67 */     this.a = a;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public ByteBigArrayBigList(long capacity) {
/*   77 */     if (capacity < 0L) throw new IllegalArgumentException("Initial capacity (" + capacity + ") is negative"); 
/*   78 */     if (capacity == 0L) { this.a = ByteBigArrays.EMPTY_BIG_ARRAY; }
/*   79 */     else { this.a = ByteBigArrays.newBigArray(capacity); }
/*      */   
/*      */   }
/*      */ 
/*      */   
/*      */   public ByteBigArrayBigList() {
/*   85 */     this.a = ByteBigArrays.DEFAULT_EMPTY_BIG_ARRAY;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public ByteBigArrayBigList(ByteCollection c) {
/*   94 */     this(Size64.sizeOf(c));
/*      */     
/*   96 */     ((ByteBigList)c).getElements(0L, this.a, 0L, this.size = Size64.sizeOf(c));
/*      */     
/*   98 */     for (ByteIterator i = c.iterator(); i.hasNext(); add(i.nextByte()));
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public ByteBigArrayBigList(ByteBigList l) {
/*  108 */     this(l.size64());
/*  109 */     l.getElements(0L, this.a, 0L, this.size = l.size64());
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public ByteBigArrayBigList(byte[][] a) {
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
/*      */   public ByteBigArrayBigList(byte[][] a, long offset, long length) {
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
/*      */   public ByteBigArrayBigList(Iterator<? extends Byte> i) {
/*  140 */     this();
/*  141 */     for (; i.hasNext(); add(((Byte)i.next()).byteValue()));
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public ByteBigArrayBigList(ByteIterator i) {
/*  151 */     this();
/*  152 */     for (; i.hasNext(); add(i.nextByte()));
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public byte[][] elements() {
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
/*      */   public static ByteBigArrayBigList wrap(byte[][] a, long length) {
/*  172 */     if (length > BigArrays.length(a)) throw new IllegalArgumentException("The specified length (" + length + ") is greater than the array size (" + BigArrays.length(a) + ")"); 
/*  173 */     ByteBigArrayBigList l = new ByteBigArrayBigList(a, false);
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
/*      */   public static ByteBigArrayBigList wrap(byte[][] a) {
/*  185 */     return wrap(a, BigArrays.length(a));
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static ByteBigArrayBigList of() {
/*  194 */     return new ByteBigArrayBigList();
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
/*      */   public static ByteBigArrayBigList of(byte... init) {
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
/*  218 */     if (capacity <= BigArrays.length(this.a) || this.a == ByteBigArrays.DEFAULT_EMPTY_BIG_ARRAY)
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
/*  233 */       return;  if (this.a != ByteBigArrays.DEFAULT_EMPTY_BIG_ARRAY) { capacity = Math.max(oldLength + (oldLength >> 1L), capacity); }
/*  234 */     else if (capacity < 10L) { capacity = 10L; }
/*  235 */      this.a = BigArrays.forceCapacity(this.a, capacity, this.size);
/*  236 */     assert this.size <= BigArrays.length(this.a);
/*      */   }
/*      */ 
/*      */   
/*      */   public void add(long index, byte k) {
/*  241 */     ensureIndex(index);
/*  242 */     grow(this.size + 1L);
/*  243 */     if (index != this.size) BigArrays.copy(this.a, index, this.a, index + 1L, this.size - index); 
/*  244 */     BigArrays.set(this.a, index, k);
/*  245 */     this.size++;
/*  246 */     assert this.size <= BigArrays.length(this.a);
/*      */   }
/*      */ 
/*      */   
/*      */   public boolean add(byte k) {
/*  251 */     grow(this.size + 1L);
/*  252 */     BigArrays.set(this.a, this.size++, k);
/*  253 */     if (!$assertionsDisabled && this.size > BigArrays.length(this.a)) throw new AssertionError(); 
/*  254 */     return true;
/*      */   }
/*      */ 
/*      */   
/*      */   public byte getByte(long index) {
/*  259 */     if (index >= this.size) throw new IndexOutOfBoundsException("Index (" + index + ") is greater than or equal to list size (" + this.size + ")"); 
/*  260 */     return BigArrays.get(this.a, index);
/*      */   }
/*      */ 
/*      */   
/*      */   public long indexOf(byte k) {
/*  265 */     for (long i = 0L; i < this.size; ) { if (k == BigArrays.get(this.a, i)) return i;  i++; }
/*  266 */      return -1L;
/*      */   }
/*      */ 
/*      */   
/*      */   public long lastIndexOf(byte k) {
/*  271 */     for (long i = this.size; i-- != 0L;) { if (k == BigArrays.get(this.a, i)) return i;  }
/*  272 */      return -1L;
/*      */   }
/*      */ 
/*      */   
/*      */   public byte removeByte(long index) {
/*  277 */     if (index >= this.size) throw new IndexOutOfBoundsException("Index (" + index + ") is greater than or equal to list size (" + this.size + ")"); 
/*  278 */     byte old = BigArrays.get(this.a, index);
/*  279 */     this.size--;
/*  280 */     if (index != this.size) BigArrays.copy(this.a, index + 1L, this.a, index, this.size - index); 
/*  281 */     assert this.size <= BigArrays.length(this.a);
/*  282 */     return old;
/*      */   }
/*      */ 
/*      */   
/*      */   public boolean rem(byte k) {
/*  287 */     long index = indexOf(k);
/*  288 */     if (index == -1L) return false; 
/*  289 */     removeByte(index);
/*  290 */     assert this.size <= BigArrays.length(this.a);
/*  291 */     return true;
/*      */   }
/*      */ 
/*      */   
/*      */   public byte set(long index, byte k) {
/*  296 */     if (index >= this.size) throw new IndexOutOfBoundsException("Index (" + index + ") is greater than or equal to list size (" + this.size + ")"); 
/*  297 */     byte old = BigArrays.get(this.a, index);
/*  298 */     BigArrays.set(this.a, index, k);
/*  299 */     return old;
/*      */   }
/*      */ 
/*      */   
/*      */   public boolean removeAll(ByteCollection c) {
/*  304 */     byte[] s = null, d = null;
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
/*  328 */     byte[] s = null, d = null;
/*  329 */     int ss = -1, sd = 134217728, ds = -1, dd = 134217728; long i;
/*  330 */     for (i = 0L; i < this.size; i++) {
/*  331 */       if (sd == 134217728) {
/*  332 */         sd = 0;
/*  333 */         s = this.a[++ss];
/*      */       } 
/*  335 */       if (!c.contains(Byte.valueOf(s[sd]))) {
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
/*      */   public boolean addAll(long index, ByteCollection c) {
/*      */     // Byte code:
/*      */     //   0: aload_3
/*      */     //   1: instanceof it/unimi/dsi/fastutil/bytes/ByteList
/*      */     //   4: ifeq -> 17
/*      */     //   7: aload_0
/*      */     //   8: lload_1
/*      */     //   9: aload_3
/*      */     //   10: checkcast it/unimi/dsi/fastutil/bytes/ByteList
/*      */     //   13: invokevirtual addAll : (JLit/unimi/dsi/fastutil/bytes/ByteList;)Z
/*      */     //   16: ireturn
/*      */     //   17: aload_3
/*      */     //   18: instanceof it/unimi/dsi/fastutil/bytes/ByteBigList
/*      */     //   21: ifeq -> 34
/*      */     //   24: aload_0
/*      */     //   25: lload_1
/*      */     //   26: aload_3
/*      */     //   27: checkcast it/unimi/dsi/fastutil/bytes/ByteBigList
/*      */     //   30: invokevirtual addAll : (JLit/unimi/dsi/fastutil/bytes/ByteBigList;)Z
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
/*      */     //   67: getfield a : [[B
/*      */     //   70: lload_1
/*      */     //   71: aload_0
/*      */     //   72: getfield a : [[B
/*      */     //   75: lload_1
/*      */     //   76: iload #4
/*      */     //   78: i2l
/*      */     //   79: ladd
/*      */     //   80: aload_0
/*      */     //   81: getfield size : J
/*      */     //   84: lload_1
/*      */     //   85: lsub
/*      */     //   86: invokestatic copy : ([[BJ[[BJJ)V
/*      */     //   89: aload_3
/*      */     //   90: invokeinterface iterator : ()Lit/unimi/dsi/fastutil/bytes/ByteIterator;
/*      */     //   95: astore #5
/*      */     //   97: aload_0
/*      */     //   98: dup
/*      */     //   99: getfield size : J
/*      */     //   102: iload #4
/*      */     //   104: i2l
/*      */     //   105: ladd
/*      */     //   106: putfield size : J
/*      */     //   109: getstatic it/unimi/dsi/fastutil/bytes/ByteBigArrayBigList.$assertionsDisabled : Z
/*      */     //   112: ifne -> 138
/*      */     //   115: aload_0
/*      */     //   116: getfield size : J
/*      */     //   119: aload_0
/*      */     //   120: getfield a : [[B
/*      */     //   123: invokestatic length : ([[B)J
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
/*      */     //   147: getfield a : [[B
/*      */     //   150: lload_1
/*      */     //   151: dup2
/*      */     //   152: lconst_1
/*      */     //   153: ladd
/*      */     //   154: lstore_1
/*      */     //   155: aload #5
/*      */     //   157: invokeinterface nextByte : ()B
/*      */     //   162: invokestatic set : ([[BJB)V
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
/*      */     //   0	170	0	this	Lit/unimi/dsi/fastutil/bytes/ByteBigArrayBigList;
/*      */     //   0	170	1	index	J
/*      */     //   0	170	3	c	Lit/unimi/dsi/fastutil/bytes/ByteCollection;
/*      */     //   47	123	4	n	I
/*      */     //   97	73	5	i	Lit/unimi/dsi/fastutil/bytes/ByteIterator;
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
/*      */   public boolean addAll(long index, ByteBigList list) {
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
/*      */   public boolean addAll(long index, ByteList list) {
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
/*  422 */     if (size > this.size) BigArrays.fill(this.a, this.size, size, (byte)0); 
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
/*      */   private class SubList extends AbstractByteBigList.ByteRandomAccessSubList {
/*      */     private static final long serialVersionUID = -3185226345314976296L;
/*      */     
/*      */     protected SubList(long from, long to) {
/*  465 */       super(from, to);
/*      */     }
/*      */ 
/*      */ 
/*      */     
/*      */     private byte[][] getParentArray() {
/*  471 */       return ByteBigArrayBigList.this.a;
/*      */     }
/*      */ 
/*      */ 
/*      */     
/*      */     public byte getByte(long i) {
/*  477 */       ensureRestrictedIndex(i);
/*  478 */       return BigArrays.get(ByteBigArrayBigList.this.a, i + this.from);
/*      */     }
/*      */     
/*      */     private final class SubListIterator
/*      */       extends ByteBigListIterators.AbstractIndexBasedBigListIterator
/*      */     {
/*      */       SubListIterator(long index) {
/*  485 */         super(0L, index);
/*      */       }
/*      */ 
/*      */       
/*      */       protected final byte get(long i) {
/*  490 */         return BigArrays.get(ByteBigArrayBigList.this.a, ByteBigArrayBigList.SubList.this.from + i);
/*      */       }
/*      */ 
/*      */       
/*      */       protected final void add(long i, byte k) {
/*  495 */         ByteBigArrayBigList.SubList.this.add(i, k);
/*      */       }
/*      */ 
/*      */       
/*      */       protected final void set(long i, byte k) {
/*  500 */         ByteBigArrayBigList.SubList.this.set(i, k);
/*      */       }
/*      */ 
/*      */       
/*      */       protected final void remove(long i) {
/*  505 */         ByteBigArrayBigList.SubList.this.removeByte(i);
/*      */       }
/*      */ 
/*      */       
/*      */       protected final long getMaxPos() {
/*  510 */         return ByteBigArrayBigList.SubList.this.to - ByteBigArrayBigList.SubList.this.from;
/*      */       }
/*      */ 
/*      */       
/*      */       public byte nextByte() {
/*  515 */         if (!hasNext()) throw new NoSuchElementException(); 
/*  516 */         return BigArrays.get(ByteBigArrayBigList.this.a, ByteBigArrayBigList.SubList.this.from + (this.lastReturned = this.pos++));
/*      */       }
/*      */ 
/*      */       
/*      */       public byte previousByte() {
/*  521 */         if (!hasPrevious()) throw new NoSuchElementException(); 
/*  522 */         return BigArrays.get(ByteBigArrayBigList.this.a, ByteBigArrayBigList.SubList.this.from + (this.lastReturned = --this.pos));
/*      */       }
/*      */ 
/*      */ 
/*      */ 
/*      */       
/*      */       public void forEachRemaining(ByteConsumer action) {
/*      */         // Byte code:
/*      */         //   0: aload_0
/*      */         //   1: getfield this$1 : Lit/unimi/dsi/fastutil/bytes/ByteBigArrayBigList$SubList;
/*      */         //   4: getfield to : J
/*      */         //   7: aload_0
/*      */         //   8: getfield this$1 : Lit/unimi/dsi/fastutil/bytes/ByteBigArrayBigList$SubList;
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
/*      */         //   27: getfield this$1 : Lit/unimi/dsi/fastutil/bytes/ByteBigArrayBigList$SubList;
/*      */         //   30: getfield this$0 : Lit/unimi/dsi/fastutil/bytes/ByteBigArrayBigList;
/*      */         //   33: getfield a : [[B
/*      */         //   36: aload_0
/*      */         //   37: getfield this$1 : Lit/unimi/dsi/fastutil/bytes/ByteBigArrayBigList$SubList;
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
/*      */         //   60: invokestatic get : ([[BJ)B
/*      */         //   63: invokeinterface accept : (B)V
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
/*      */         //   0	72	0	this	Lit/unimi/dsi/fastutil/bytes/ByteBigArrayBigList$SubList$SubListIterator;
/*      */         //   0	72	1	action	Lit/unimi/dsi/fastutil/bytes/ByteConsumer;
/*      */         //   16	56	2	max	J
/*      */       }
/*      */     }
/*      */ 
/*      */ 
/*      */     
/*      */     public ByteBigListIterator listIterator(long index) {
/*  536 */       return new SubListIterator(index);
/*      */     }
/*      */     
/*      */     private final class SubListSpliterator
/*      */       extends ByteBigSpliterators.LateBindingSizeIndexBasedSpliterator {
/*      */       SubListSpliterator() {
/*  542 */         super(ByteBigArrayBigList.SubList.this.from);
/*      */       }
/*      */       
/*      */       private SubListSpliterator(long pos, long maxPos) {
/*  546 */         super(pos, maxPos);
/*      */       }
/*      */ 
/*      */       
/*      */       protected final long getMaxPosFromBackingStore() {
/*  551 */         return ByteBigArrayBigList.SubList.this.to;
/*      */       }
/*      */ 
/*      */       
/*      */       protected final byte get(long i) {
/*  556 */         return BigArrays.get(ByteBigArrayBigList.this.a, i);
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
/*      */       public boolean tryAdvance(ByteConsumer action) {
/*  574 */         if (this.pos >= getMaxPos()) return false; 
/*  575 */         action.accept(BigArrays.get(ByteBigArrayBigList.this.a, this.pos++));
/*  576 */         return true;
/*      */       }
/*      */ 
/*      */ 
/*      */ 
/*      */       
/*      */       public void forEachRemaining(ByteConsumer action) {
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
/*      */         //   16: getfield this$1 : Lit/unimi/dsi/fastutil/bytes/ByteBigArrayBigList$SubList;
/*      */         //   19: getfield this$0 : Lit/unimi/dsi/fastutil/bytes/ByteBigArrayBigList;
/*      */         //   22: getfield a : [[B
/*      */         //   25: aload_0
/*      */         //   26: dup
/*      */         //   27: getfield pos : J
/*      */         //   30: dup2_x1
/*      */         //   31: lconst_1
/*      */         //   32: ladd
/*      */         //   33: putfield pos : J
/*      */         //   36: invokestatic get : ([[BJ)B
/*      */         //   39: invokeinterface accept : (B)V
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
/*      */         //   0	48	0	this	Lit/unimi/dsi/fastutil/bytes/ByteBigArrayBigList$SubList$SubListSpliterator;
/*      */         //   0	48	1	action	Lit/unimi/dsi/fastutil/bytes/ByteConsumer;
/*      */         //   5	43	2	max	J
/*      */       }
/*      */     }
/*      */ 
/*      */ 
/*      */     
/*      */     public ByteSpliterator spliterator() {
/*  590 */       return new SubListSpliterator();
/*      */     }
/*      */     
/*      */     boolean contentsEquals(byte[][] otherA, long otherAFrom, long otherATo) {
/*  594 */       if (ByteBigArrayBigList.this.a == otherA && this.from == otherAFrom && this.to == otherATo) return true; 
/*  595 */       if (otherATo - otherAFrom != size64()) {
/*  596 */         return false;
/*      */       }
/*  598 */       long pos = this.to, otherPos = otherATo;
/*      */ 
/*      */ 
/*      */ 
/*      */       
/*  603 */       while (--pos >= this.from) { if (BigArrays.get(ByteBigArrayBigList.this.a, pos) != BigArrays.get(otherA, --otherPos)) return false;  }
/*  604 */        return true;
/*      */     }
/*      */ 
/*      */     
/*      */     public boolean equals(Object o) {
/*  609 */       if (o == this) return true; 
/*  610 */       if (o == null) return false; 
/*  611 */       if (!(o instanceof BigList)) return false; 
/*  612 */       if (o instanceof ByteBigArrayBigList) {
/*      */         
/*  614 */         ByteBigArrayBigList other = (ByteBigArrayBigList)o;
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
/*      */     int contentsCompareTo(byte[][] otherA, long otherAFrom, long otherATo) {
/*  626 */       if (ByteBigArrayBigList.this.a == otherA && this.from == otherAFrom && this.to == otherATo) return 0;
/*      */ 
/*      */       
/*      */       long i;
/*      */       long j;
/*  631 */       for (i = this.from, j = otherAFrom; i < this.to && i < otherATo; i++, j++) {
/*  632 */         byte e1 = BigArrays.get(ByteBigArrayBigList.this.a, i);
/*  633 */         byte e2 = BigArrays.get(otherA, j); int r;
/*  634 */         if ((r = Byte.compare(e1, e2)) != 0) return r; 
/*      */       } 
/*  636 */       return (i < otherATo) ? -1 : ((i < this.to) ? 1 : 0);
/*      */     }
/*      */ 
/*      */     
/*      */     public int compareTo(BigList<? extends Byte> l) {
/*  641 */       if (l instanceof ByteBigArrayBigList) {
/*      */         
/*  643 */         ByteBigArrayBigList other = (ByteBigArrayBigList)l;
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
/*      */   public ByteBigList subList(long from, long to) {
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
/*      */   public void getElements(long from, byte[][] a, long offset, long length) {
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
/*      */   public void getElements(long from, byte[] a, int offset, int length) {
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
/*      */   public void addElements(long index, byte[][] a, long offset, long length) {
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
/*      */   public void setElements(long index, byte[][] a, long offset, long length) {
/*  735 */     BigArrays.copy(a, offset, this.a, index, length);
/*      */   }
/*      */ 
/*      */   
/*      */   public void forEach(ByteConsumer action) {
/*  740 */     for (long i = 0L; i < this.size; i++) {
/*  741 */       action.accept(BigArrays.get(this.a, i));
/*      */     }
/*      */   }
/*      */ 
/*      */   
/*      */   public ByteBigListIterator listIterator(final long index) {
/*  747 */     ensureIndex(index);
/*  748 */     return new ByteBigListIterator() {
/*  749 */         long pos = index; long last = -1L;
/*      */ 
/*      */         
/*      */         public boolean hasNext() {
/*  753 */           return (this.pos < ByteBigArrayBigList.this.size);
/*      */         }
/*      */ 
/*      */         
/*      */         public boolean hasPrevious() {
/*  758 */           return (this.pos > 0L);
/*      */         }
/*      */ 
/*      */         
/*      */         public byte nextByte() {
/*  763 */           if (!hasNext()) throw new NoSuchElementException(); 
/*  764 */           return BigArrays.get(ByteBigArrayBigList.this.a, this.last = this.pos++);
/*      */         }
/*      */ 
/*      */         
/*      */         public byte previousByte() {
/*  769 */           if (!hasPrevious()) throw new NoSuchElementException(); 
/*  770 */           return BigArrays.get(ByteBigArrayBigList.this.a, this.last = --this.pos);
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
/*      */         public void add(byte k) {
/*  785 */           ByteBigArrayBigList.this.add(this.pos++, k);
/*  786 */           this.last = -1L;
/*      */         }
/*      */ 
/*      */         
/*      */         public void set(byte k) {
/*  791 */           if (this.last == -1L) throw new IllegalStateException(); 
/*  792 */           ByteBigArrayBigList.this.set(this.last, k);
/*      */         }
/*      */ 
/*      */         
/*      */         public void remove() {
/*  797 */           if (this.last == -1L) throw new IllegalStateException(); 
/*  798 */           ByteBigArrayBigList.this.removeByte(this.last);
/*      */           
/*  800 */           if (this.last < this.pos) this.pos--; 
/*  801 */           this.last = -1L;
/*      */         }
/*      */ 
/*      */ 
/*      */         
/*      */         public void forEachRemaining(ByteConsumer action) {
/*      */           // Byte code:
/*      */           //   0: aload_0
/*      */           //   1: getfield pos : J
/*      */           //   4: aload_0
/*      */           //   5: getfield this$0 : Lit/unimi/dsi/fastutil/bytes/ByteBigArrayBigList;
/*      */           //   8: getfield size : J
/*      */           //   11: lcmp
/*      */           //   12: ifge -> 50
/*      */           //   15: aload_1
/*      */           //   16: aload_0
/*      */           //   17: getfield this$0 : Lit/unimi/dsi/fastutil/bytes/ByteBigArrayBigList;
/*      */           //   20: getfield a : [[B
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
/*      */           //   39: invokestatic get : ([[BJ)B
/*      */           //   42: invokeinterface accept : (B)V
/*      */           //   47: goto -> 0
/*      */           //   50: return
/*      */           // Line number table:
/*      */           //   Java source line number -> byte code offset
/*      */           //   #806	-> 0
/*      */           //   #807	-> 15
/*      */           //   #809	-> 50
/*      */           // Local variable table:
/*      */           //   start	length	slot	name	descriptor
/*      */           //   0	51	0	this	Lit/unimi/dsi/fastutil/bytes/ByteBigArrayBigList$1;
/*      */           //   0	51	1	action	Lit/unimi/dsi/fastutil/bytes/ByteConsumer;
/*      */         }
/*      */ 
/*      */ 
/*      */         
/*      */         public long back(long n) {
/*  813 */           if (n < 0L) throw new IllegalArgumentException("Argument must be nonnegative: " + n); 
/*  814 */           long remaining = ByteBigArrayBigList.this.size - this.pos;
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
/*  828 */           long remaining = ByteBigArrayBigList.this.size - this.pos;
/*  829 */           if (n < remaining) {
/*  830 */             this.pos += n;
/*      */           } else {
/*  832 */             n = remaining;
/*  833 */             this.pos = ByteBigArrayBigList.this.size;
/*      */           } 
/*  835 */           this.last = this.pos - 1L;
/*  836 */           return n;
/*      */         }
/*      */       };
/*      */   }
/*      */   
/*      */   private final class Spliterator
/*      */     implements ByteSpliterator
/*      */   {
/*      */     boolean hasSplit = false;
/*      */     long pos;
/*      */     long max;
/*      */     
/*      */     public Spliterator() {
/*  849 */       this(0L, ByteBigArrayBigList.this.size, false);
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
/*  860 */       return this.hasSplit ? this.max : ByteBigArrayBigList.this.size;
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
/*      */     public boolean tryAdvance(ByteConsumer action) {
/*  875 */       if (this.pos >= getWorkingMax()) return false; 
/*  876 */       action.accept(BigArrays.get(ByteBigArrayBigList.this.a, this.pos++));
/*  877 */       return true;
/*      */     }
/*      */ 
/*      */     
/*      */     public void forEachRemaining(ByteConsumer action) {
/*  882 */       for (long max = getWorkingMax(); this.pos < max; this.pos++) {
/*  883 */         action.accept(BigArrays.get(ByteBigArrayBigList.this.a, this.pos));
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
/*      */     public ByteSpliterator trySplit() {
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
/*      */   public ByteSpliterator spliterator() {
/*  923 */     return new Spliterator();
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public ByteBigArrayBigList clone() {
/*      */     ByteBigArrayBigList c;
/*  930 */     if (getClass() == ByteBigArrayBigList.class) {
/*  931 */       c = new ByteBigArrayBigList(this.size);
/*  932 */       c.size = this.size;
/*      */     } else {
/*      */       try {
/*  935 */         c = (ByteBigArrayBigList)super.clone();
/*  936 */       } catch (CloneNotSupportedException e) {
/*      */         
/*  938 */         throw new InternalError(e);
/*      */       } 
/*  940 */       c.a = ByteBigArrays.newBigArray(this.size);
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
/*      */   public boolean equals(ByteBigArrayBigList l) {
/*  957 */     if (l == this) return true; 
/*  958 */     long s = size64();
/*  959 */     if (s != l.size64()) return false; 
/*  960 */     byte[][] a1 = this.a;
/*  961 */     byte[][] a2 = l.a;
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
/*  975 */     if (o instanceof ByteBigArrayBigList)
/*      */     {
/*  977 */       return equals((ByteBigArrayBigList)o);
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
/*      */   public int compareTo(ByteBigArrayBigList l) {
/* 1000 */     long s1 = size64(), s2 = l.size64();
/* 1001 */     byte[][] a1 = this.a, a2 = l.a;
/* 1002 */     if (a1 == a2 && s1 == s2) return 0;
/*      */     
/*      */     int i;
/* 1005 */     for (i = 0; i < s1 && i < s2; i++) {
/* 1006 */       byte e1 = BigArrays.get(a1, i);
/* 1007 */       byte e2 = BigArrays.get(a2, i); int r;
/* 1008 */       if ((r = Byte.compare(e1, e2)) != 0) return r; 
/*      */     } 
/* 1010 */     return (i < s2) ? -1 : ((i < s1) ? 1 : 0);
/*      */   }
/*      */ 
/*      */   
/*      */   public int compareTo(BigList<? extends Byte> l) {
/* 1015 */     if (l instanceof ByteBigArrayBigList) {
/* 1016 */       return compareTo((ByteBigArrayBigList)l);
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
/* 1027 */     for (int i = 0; i < this.size; ) { s.writeByte(BigArrays.get(this.a, i)); i++; }
/*      */   
/*      */   }
/*      */   private void readObject(ObjectInputStream s) throws IOException, ClassNotFoundException {
/* 1031 */     s.defaultReadObject();
/* 1032 */     this.a = ByteBigArrays.newBigArray(this.size);
/* 1033 */     for (int i = 0; i < this.size; ) { BigArrays.set(this.a, i, s.readByte()); i++; }
/*      */   
/*      */   }
/*      */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\i\\unimi\dsi\fastutil\bytes\ByteBigArrayBigList.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */