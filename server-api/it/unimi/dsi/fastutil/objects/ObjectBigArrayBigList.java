/*      */ package it.unimi.dsi.fastutil.objects;
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
/*      */ import java.util.Objects;
/*      */ import java.util.RandomAccess;
/*      */ import java.util.function.Consumer;
/*      */ import java.util.function.Supplier;
/*      */ import java.util.stream.Collector;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ public class ObjectBigArrayBigList<K>
/*      */   extends AbstractObjectBigList<K>
/*      */   implements RandomAccess, Cloneable, Serializable
/*      */ {
/*      */   private static final long serialVersionUID = -7046029254386353131L;
/*      */   public static final int DEFAULT_INITIAL_CAPACITY = 10;
/*      */   protected final boolean wrapped;
/*      */   protected transient K[][] a;
/*      */   protected long size;
/*      */   
/*      */   protected ObjectBigArrayBigList(K[][] a, boolean dummy) {
/*   80 */     this.a = a;
/*   81 */     this.wrapped = true;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public ObjectBigArrayBigList(long capacity) {
/*   91 */     if (capacity < 0L) throw new IllegalArgumentException("Initial capacity (" + capacity + ") is negative"); 
/*   92 */     if (capacity == 0L) { this.a = (K[][])ObjectBigArrays.EMPTY_BIG_ARRAY; }
/*   93 */     else { this.a = (K[][])ObjectBigArrays.newBigArray(capacity); }
/*   94 */      this.wrapped = false;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public ObjectBigArrayBigList() {
/*  100 */     this.a = (K[][])ObjectBigArrays.DEFAULT_EMPTY_BIG_ARRAY;
/*  101 */     this.wrapped = false;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public ObjectBigArrayBigList(ObjectCollection<? extends K> c) {
/*  110 */     this(Size64.sizeOf(c));
/*      */     
/*  112 */     ((ObjectBigList)c).getElements(0L, (Object[][])this.a, 0L, this.size = Size64.sizeOf(c));
/*      */     
/*  114 */     for (ObjectIterator<? extends K> i = c.iterator(); i.hasNext(); add(i.next()));
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public ObjectBigArrayBigList(Collection<? extends K> c) {
/*  124 */     this(Size64.sizeOf(c));
/*      */     
/*  126 */     ((ObjectBigList)c).getElements(0L, (Object[][])this.a, 0L, this.size = Size64.sizeOf(c));
/*      */     
/*  128 */     for (Iterator<? extends K> i = c.iterator(); i.hasNext(); add(i.next()));
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public ObjectBigArrayBigList(ObjectBigList<? extends K> l) {
/*  138 */     this(l.size64());
/*  139 */     l.getElements(0L, (Object[][])this.a, 0L, this.size = l.size64());
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public ObjectBigArrayBigList(K[][] a) {
/*  148 */     this(a, 0L, BigArrays.length((Object[][])a));
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public ObjectBigArrayBigList(K[][] a, long offset, long length) {
/*  159 */     this(length);
/*  160 */     BigArrays.copy((Object[][])a, offset, (Object[][])this.a, 0L, length);
/*  161 */     this.size = length;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public ObjectBigArrayBigList(Iterator<? extends K> i) {
/*  170 */     this();
/*  171 */     for (; i.hasNext(); add(i.next()));
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public ObjectBigArrayBigList(ObjectIterator<? extends K> i) {
/*  181 */     this();
/*  182 */     for (; i.hasNext(); add(i.next()));
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
/*      */   public K[][] elements() {
/*  196 */     return this.a;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static <K> ObjectBigArrayBigList<K> wrap(K[][] a, long length) {
/*  207 */     if (length > BigArrays.length((Object[][])a)) throw new IllegalArgumentException("The specified length (" + length + ") is greater than the array size (" + BigArrays.length(a) + ")"); 
/*  208 */     ObjectBigArrayBigList<K> l = new ObjectBigArrayBigList<>(a, false);
/*  209 */     l.size = length;
/*  210 */     return l;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static <K> ObjectBigArrayBigList<K> wrap(K[][] a) {
/*  220 */     return wrap(a, BigArrays.length((Object[][])a));
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static <K> ObjectBigArrayBigList<K> of() {
/*  229 */     return new ObjectBigArrayBigList<>();
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
/*      */   @SafeVarargs
/*      */   public static <K> ObjectBigArrayBigList<K> of(K... init) {
/*  243 */     return wrap((K[][])BigArrays.wrap((Object[])init));
/*      */   }
/*      */ 
/*      */   
/*      */   private ObjectBigArrayBigList<K> combine(ObjectBigArrayBigList<? extends K> toAddFrom) {
/*  248 */     addAll(toAddFrom);
/*  249 */     return this;
/*      */   }
/*      */   
/*  252 */   private static final Collector<Object, ?, ObjectBigArrayBigList<Object>> TO_LIST_COLLECTOR = (Collector)Collector.of(ObjectBigArrayBigList::new, ObjectBigArrayBigList::add, ObjectBigArrayBigList::combine, new Collector.Characteristics[0]);
/*      */ 
/*      */ 
/*      */   
/*      */   public static <K> Collector<K, ?, ObjectBigArrayBigList<K>> toBigList() {
/*  257 */     return (Collector)TO_LIST_COLLECTOR;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static <K> Collector<K, ?, ObjectBigArrayBigList<K>> toBigListWithExpectedSize(long expectedSize) {
/*  268 */     return (Collector)Collector.of(() -> new ObjectBigArrayBigList(expectedSize), ObjectBigArrayBigList::add, ObjectBigArrayBigList::combine, new Collector.Characteristics[0]);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void ensureCapacity(long capacity) {
/*  278 */     if (capacity <= BigArrays.length((Object[][])this.a) || this.a == ObjectBigArrays.DEFAULT_EMPTY_BIG_ARRAY)
/*  279 */       return;  if (this.wrapped) { this.a = (K[][])BigArrays.forceCapacity((Object[][])this.a, capacity, this.size); }
/*      */     
/*  281 */     else if (capacity > BigArrays.length((Object[][])this.a))
/*  282 */     { Object[][] t = ObjectBigArrays.newBigArray(capacity);
/*  283 */       BigArrays.copy((Object[][])this.a, 0L, t, 0L, this.size);
/*  284 */       this.a = (K[][])t; }
/*      */ 
/*      */     
/*  287 */     assert this.size <= BigArrays.length((Object[][])this.a);
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
/*  298 */     long oldLength = BigArrays.length((Object[][])this.a);
/*  299 */     if (capacity <= oldLength)
/*  300 */       return;  if (this.a != ObjectBigArrays.DEFAULT_EMPTY_BIG_ARRAY) { capacity = Math.max(oldLength + (oldLength >> 1L), capacity); }
/*  301 */     else if (capacity < 10L) { capacity = 10L; }
/*  302 */      if (this.wrapped) { this.a = (K[][])BigArrays.forceCapacity((Object[][])this.a, capacity, this.size); }
/*      */     else
/*  304 */     { Object[][] t = ObjectBigArrays.newBigArray(capacity);
/*  305 */       BigArrays.copy((Object[][])this.a, 0L, t, 0L, this.size);
/*  306 */       this.a = (K[][])t; }
/*      */     
/*  308 */     assert this.size <= BigArrays.length((Object[][])this.a);
/*      */   }
/*      */ 
/*      */   
/*      */   public void add(long index, K k) {
/*  313 */     ensureIndex(index);
/*  314 */     grow(this.size + 1L);
/*  315 */     if (index != this.size) BigArrays.copy((Object[][])this.a, index, (Object[][])this.a, index + 1L, this.size - index); 
/*  316 */     BigArrays.set((Object[][])this.a, index, k);
/*  317 */     this.size++;
/*  318 */     assert this.size <= BigArrays.length((Object[][])this.a);
/*      */   }
/*      */ 
/*      */   
/*      */   public boolean add(K k) {
/*  323 */     grow(this.size + 1L);
/*  324 */     BigArrays.set((Object[][])this.a, this.size++, k);
/*  325 */     if (!$assertionsDisabled && this.size > BigArrays.length((Object[][])this.a)) throw new AssertionError(); 
/*  326 */     return true;
/*      */   }
/*      */ 
/*      */   
/*      */   public K get(long index) {
/*  331 */     if (index >= this.size) throw new IndexOutOfBoundsException("Index (" + index + ") is greater than or equal to list size (" + this.size + ")"); 
/*  332 */     return (K)BigArrays.get((Object[][])this.a, index);
/*      */   }
/*      */ 
/*      */   
/*      */   public long indexOf(Object k) {
/*  337 */     for (long i = 0L; i < this.size; ) { if (Objects.equals(k, BigArrays.get((Object[][])this.a, i))) return i;  i++; }
/*  338 */      return -1L;
/*      */   }
/*      */ 
/*      */   
/*      */   public long lastIndexOf(Object k) {
/*  343 */     for (long i = this.size; i-- != 0L;) { if (Objects.equals(k, BigArrays.get((Object[][])this.a, i))) return i;  }
/*  344 */      return -1L;
/*      */   }
/*      */ 
/*      */   
/*      */   public K remove(long index) {
/*  349 */     if (index >= this.size) throw new IndexOutOfBoundsException("Index (" + index + ") is greater than or equal to list size (" + this.size + ")"); 
/*  350 */     K old = (K)BigArrays.get((Object[][])this.a, index);
/*  351 */     this.size--;
/*  352 */     if (index != this.size) BigArrays.copy((Object[][])this.a, index + 1L, (Object[][])this.a, index, this.size - index); 
/*  353 */     BigArrays.set((Object[][])this.a, this.size, null);
/*  354 */     assert this.size <= BigArrays.length((Object[][])this.a);
/*  355 */     return old;
/*      */   }
/*      */ 
/*      */   
/*      */   public boolean remove(Object k) {
/*  360 */     long index = indexOf(k);
/*  361 */     if (index == -1L) return false; 
/*  362 */     remove(index);
/*  363 */     assert this.size <= BigArrays.length((Object[][])this.a);
/*  364 */     return true;
/*      */   }
/*      */ 
/*      */   
/*      */   public K set(long index, K k) {
/*  369 */     if (index >= this.size) throw new IndexOutOfBoundsException("Index (" + index + ") is greater than or equal to list size (" + this.size + ")"); 
/*  370 */     K old = (K)BigArrays.get((Object[][])this.a, index);
/*  371 */     BigArrays.set((Object[][])this.a, index, k);
/*  372 */     return old;
/*      */   }
/*      */ 
/*      */   
/*      */   public boolean removeAll(Collection<?> c) {
/*  377 */     K[] s = null, d = null;
/*  378 */     int ss = -1, sd = 134217728, ds = -1, dd = 134217728; long i;
/*  379 */     for (i = 0L; i < this.size; i++) {
/*  380 */       if (sd == 134217728) {
/*  381 */         sd = 0;
/*  382 */         s = this.a[++ss];
/*      */       } 
/*  384 */       if (!c.contains(s[sd])) {
/*  385 */         if (dd == 134217728) {
/*  386 */           d = this.a[++ds];
/*  387 */           dd = 0;
/*      */         } 
/*  389 */         d[dd++] = s[sd];
/*      */       } 
/*  391 */       sd++;
/*      */     } 
/*  393 */     long j = BigArrays.index(ds, dd);
/*  394 */     BigArrays.fill((Object[][])this.a, j, this.size, null);
/*  395 */     boolean modified = (this.size != j);
/*  396 */     this.size = j;
/*  397 */     return modified;
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
/*      */   public boolean addAll(long index, Collection<? extends K> c) {
/*      */     // Byte code:
/*      */     //   0: aload_3
/*      */     //   1: instanceof it/unimi/dsi/fastutil/objects/ObjectList
/*      */     //   4: ifeq -> 17
/*      */     //   7: aload_0
/*      */     //   8: lload_1
/*      */     //   9: aload_3
/*      */     //   10: checkcast it/unimi/dsi/fastutil/objects/ObjectList
/*      */     //   13: invokevirtual addAll : (JLit/unimi/dsi/fastutil/objects/ObjectList;)Z
/*      */     //   16: ireturn
/*      */     //   17: aload_3
/*      */     //   18: instanceof it/unimi/dsi/fastutil/objects/ObjectBigList
/*      */     //   21: ifeq -> 34
/*      */     //   24: aload_0
/*      */     //   25: lload_1
/*      */     //   26: aload_3
/*      */     //   27: checkcast it/unimi/dsi/fastutil/objects/ObjectBigList
/*      */     //   30: invokevirtual addAll : (JLit/unimi/dsi/fastutil/objects/ObjectBigList;)Z
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
/*      */     //   67: getfield a : [[Ljava/lang/Object;
/*      */     //   70: lload_1
/*      */     //   71: aload_0
/*      */     //   72: getfield a : [[Ljava/lang/Object;
/*      */     //   75: lload_1
/*      */     //   76: iload #4
/*      */     //   78: i2l
/*      */     //   79: ladd
/*      */     //   80: aload_0
/*      */     //   81: getfield size : J
/*      */     //   84: lload_1
/*      */     //   85: lsub
/*      */     //   86: invokestatic copy : ([[Ljava/lang/Object;J[[Ljava/lang/Object;JJ)V
/*      */     //   89: aload_3
/*      */     //   90: invokeinterface iterator : ()Ljava/util/Iterator;
/*      */     //   95: astore #5
/*      */     //   97: aload_0
/*      */     //   98: dup
/*      */     //   99: getfield size : J
/*      */     //   102: iload #4
/*      */     //   104: i2l
/*      */     //   105: ladd
/*      */     //   106: putfield size : J
/*      */     //   109: getstatic it/unimi/dsi/fastutil/objects/ObjectBigArrayBigList.$assertionsDisabled : Z
/*      */     //   112: ifne -> 138
/*      */     //   115: aload_0
/*      */     //   116: getfield size : J
/*      */     //   119: aload_0
/*      */     //   120: getfield a : [[Ljava/lang/Object;
/*      */     //   123: invokestatic length : ([[Ljava/lang/Object;)J
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
/*      */     //   147: getfield a : [[Ljava/lang/Object;
/*      */     //   150: lload_1
/*      */     //   151: dup2
/*      */     //   152: lconst_1
/*      */     //   153: ladd
/*      */     //   154: lstore_1
/*      */     //   155: aload #5
/*      */     //   157: invokeinterface next : ()Ljava/lang/Object;
/*      */     //   162: invokestatic set : ([[Ljava/lang/Object;JLjava/lang/Object;)V
/*      */     //   165: goto -> 138
/*      */     //   168: iconst_1
/*      */     //   169: ireturn
/*      */     // Line number table:
/*      */     //   Java source line number -> byte code offset
/*      */     //   #402	-> 0
/*      */     //   #403	-> 7
/*      */     //   #405	-> 17
/*      */     //   #406	-> 24
/*      */     //   #408	-> 34
/*      */     //   #409	-> 39
/*      */     //   #410	-> 47
/*      */     //   #411	-> 54
/*      */     //   #412	-> 66
/*      */     //   #413	-> 89
/*      */     //   #414	-> 97
/*      */     //   #415	-> 109
/*      */     //   #416	-> 138
/*      */     //   #417	-> 168
/*      */     // Local variable table:
/*      */     //   start	length	slot	name	descriptor
/*      */     //   0	170	0	this	Lit/unimi/dsi/fastutil/objects/ObjectBigArrayBigList;
/*      */     //   0	170	1	index	J
/*      */     //   0	170	3	c	Ljava/util/Collection;
/*      */     //   47	123	4	n	I
/*      */     //   97	73	5	i	Ljava/util/Iterator;
/*      */     // Local variable type table:
/*      */     //   start	length	slot	name	signature
/*      */     //   0	170	0	this	Lit/unimi/dsi/fastutil/objects/ObjectBigArrayBigList<TK;>;
/*      */     //   0	170	3	c	Ljava/util/Collection<+TK;>;
/*      */     //   97	73	5	i	Ljava/util/Iterator<+TK;>;
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
/*      */   public boolean addAll(long index, ObjectBigList<? extends K> list) {
/*  422 */     ensureIndex(index);
/*  423 */     long n = list.size64();
/*  424 */     if (n == 0L) return false; 
/*  425 */     grow(this.size + n);
/*  426 */     BigArrays.copy((Object[][])this.a, index, (Object[][])this.a, index + n, this.size - index);
/*  427 */     list.getElements(0L, (Object[][])this.a, index, n);
/*  428 */     this.size += n;
/*  429 */     assert this.size <= BigArrays.length((Object[][])this.a);
/*  430 */     return true;
/*      */   }
/*      */ 
/*      */   
/*      */   public boolean addAll(long index, ObjectList<? extends K> list) {
/*  435 */     ensureIndex(index);
/*  436 */     int n = list.size();
/*  437 */     if (n == 0) return false; 
/*  438 */     grow(this.size + n);
/*  439 */     BigArrays.copy((Object[][])this.a, index, (Object[][])this.a, index + n, this.size - index);
/*  440 */     this.size += n;
/*  441 */     assert this.size <= BigArrays.length((Object[][])this.a);
/*  442 */     int segment = BigArrays.segment(index);
/*  443 */     int displ = BigArrays.displacement(index);
/*  444 */     int pos = 0;
/*  445 */     while (n > 0) {
/*  446 */       int l = Math.min((this.a[segment]).length - displ, n);
/*  447 */       list.getElements(pos, (Object[])this.a[segment], displ, l);
/*  448 */       if ((displ += l) == 134217728) {
/*  449 */         displ = 0;
/*  450 */         segment++;
/*      */       } 
/*  452 */       pos += l;
/*  453 */       n -= l;
/*      */     } 
/*  455 */     return true;
/*      */   }
/*      */ 
/*      */   
/*      */   public void clear() {
/*  460 */     BigArrays.fill((Object[][])this.a, 0L, this.size, null);
/*  461 */     this.size = 0L;
/*  462 */     assert this.size <= BigArrays.length((Object[][])this.a);
/*      */   }
/*      */ 
/*      */   
/*      */   public long size64() {
/*  467 */     return this.size;
/*      */   }
/*      */ 
/*      */   
/*      */   public void size(long size) {
/*  472 */     if (size > BigArrays.length((Object[][])this.a)) this.a = (K[][])BigArrays.forceCapacity((Object[][])this.a, size, this.size); 
/*  473 */     if (size > this.size) { BigArrays.fill((Object[][])this.a, this.size, size, null); }
/*  474 */     else { BigArrays.fill((Object[][])this.a, size, this.size, null); }
/*  475 */      this.size = size;
/*      */   }
/*      */ 
/*      */   
/*      */   public boolean isEmpty() {
/*  480 */     return (this.size == 0L);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void trim() {
/*  489 */     trim(0L);
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
/*  507 */     long arrayLength = BigArrays.length((Object[][])this.a);
/*  508 */     if (n >= arrayLength || this.size == arrayLength)
/*  509 */       return;  this.a = (K[][])BigArrays.trim((Object[][])this.a, Math.max(n, this.size));
/*  510 */     assert this.size <= BigArrays.length((Object[][])this.a);
/*      */   }
/*      */   
/*      */   private class SubList extends AbstractObjectBigList.ObjectRandomAccessSubList<K> {
/*      */     private static final long serialVersionUID = -3185226345314976296L;
/*      */     
/*      */     protected SubList(long from, long to) {
/*  517 */       super(from, to);
/*      */     }
/*      */ 
/*      */ 
/*      */     
/*      */     private K[][] getParentArray() {
/*  523 */       return ObjectBigArrayBigList.this.a;
/*      */     }
/*      */ 
/*      */ 
/*      */     
/*      */     public K get(long i) {
/*  529 */       ensureRestrictedIndex(i);
/*  530 */       return (K)BigArrays.get((Object[][])ObjectBigArrayBigList.this.a, i + this.from);
/*      */     }
/*      */     
/*      */     private final class SubListIterator
/*      */       extends ObjectBigListIterators.AbstractIndexBasedBigListIterator<K>
/*      */     {
/*      */       SubListIterator(long index) {
/*  537 */         super(0L, index);
/*      */       }
/*      */ 
/*      */       
/*      */       protected final K get(long i) {
/*  542 */         return (K)BigArrays.get((Object[][])ObjectBigArrayBigList.this.a, ObjectBigArrayBigList.SubList.this.from + i);
/*      */       }
/*      */ 
/*      */       
/*      */       protected final void add(long i, K k) {
/*  547 */         ObjectBigArrayBigList.SubList.this.add(i, k);
/*      */       }
/*      */ 
/*      */       
/*      */       protected final void set(long i, K k) {
/*  552 */         ObjectBigArrayBigList.SubList.this.set(i, k);
/*      */       }
/*      */ 
/*      */       
/*      */       protected final void remove(long i) {
/*  557 */         ObjectBigArrayBigList.SubList.this.remove(i);
/*      */       }
/*      */ 
/*      */       
/*      */       protected final long getMaxPos() {
/*  562 */         return ObjectBigArrayBigList.SubList.this.to - ObjectBigArrayBigList.SubList.this.from;
/*      */       }
/*      */ 
/*      */       
/*      */       public K next() {
/*  567 */         if (!hasNext()) throw new NoSuchElementException(); 
/*  568 */         return (K)BigArrays.get((Object[][])ObjectBigArrayBigList.this.a, ObjectBigArrayBigList.SubList.this.from + (this.lastReturned = this.pos++));
/*      */       }
/*      */ 
/*      */       
/*      */       public K previous() {
/*  573 */         if (!hasPrevious()) throw new NoSuchElementException(); 
/*  574 */         return (K)BigArrays.get((Object[][])ObjectBigArrayBigList.this.a, ObjectBigArrayBigList.SubList.this.from + (this.lastReturned = --this.pos));
/*      */       }
/*      */ 
/*      */ 
/*      */ 
/*      */       
/*      */       public void forEachRemaining(Consumer<? super K> action) {
/*      */         // Byte code:
/*      */         //   0: aload_0
/*      */         //   1: getfield this$1 : Lit/unimi/dsi/fastutil/objects/ObjectBigArrayBigList$SubList;
/*      */         //   4: getfield to : J
/*      */         //   7: aload_0
/*      */         //   8: getfield this$1 : Lit/unimi/dsi/fastutil/objects/ObjectBigArrayBigList$SubList;
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
/*      */         //   27: getfield this$1 : Lit/unimi/dsi/fastutil/objects/ObjectBigArrayBigList$SubList;
/*      */         //   30: getfield this$0 : Lit/unimi/dsi/fastutil/objects/ObjectBigArrayBigList;
/*      */         //   33: getfield a : [[Ljava/lang/Object;
/*      */         //   36: aload_0
/*      */         //   37: getfield this$1 : Lit/unimi/dsi/fastutil/objects/ObjectBigArrayBigList$SubList;
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
/*      */         //   60: invokestatic get : ([[Ljava/lang/Object;J)Ljava/lang/Object;
/*      */         //   63: invokeinterface accept : (Ljava/lang/Object;)V
/*      */         //   68: goto -> 16
/*      */         //   71: return
/*      */         // Line number table:
/*      */         //   Java source line number -> byte code offset
/*      */         //   #579	-> 0
/*      */         //   #580	-> 16
/*      */         //   #581	-> 25
/*      */         //   #583	-> 71
/*      */         // Local variable table:
/*      */         //   start	length	slot	name	descriptor
/*      */         //   0	72	0	this	Lit/unimi/dsi/fastutil/objects/ObjectBigArrayBigList$SubList$SubListIterator;
/*      */         //   0	72	1	action	Ljava/util/function/Consumer;
/*      */         //   16	56	2	max	J
/*      */         // Local variable type table:
/*      */         //   start	length	slot	name	signature
/*      */         //   0	72	0	this	Lit/unimi/dsi/fastutil/objects/ObjectBigArrayBigList<TK;>.SubList.SubListIterator;
/*      */         //   0	72	1	action	Ljava/util/function/Consumer<-TK;>;
/*      */       }
/*      */     }
/*      */ 
/*      */ 
/*      */     
/*      */     public ObjectBigListIterator<K> listIterator(long index) {
/*  588 */       return new SubListIterator(index);
/*      */     }
/*      */     
/*      */     private final class SubListSpliterator
/*      */       extends ObjectBigSpliterators.LateBindingSizeIndexBasedSpliterator<K> {
/*      */       SubListSpliterator() {
/*  594 */         super(ObjectBigArrayBigList.SubList.this.from);
/*      */       }
/*      */       
/*      */       private SubListSpliterator(long pos, long maxPos) {
/*  598 */         super(pos, maxPos);
/*      */       }
/*      */ 
/*      */       
/*      */       protected final long getMaxPosFromBackingStore() {
/*  603 */         return ObjectBigArrayBigList.SubList.this.to;
/*      */       }
/*      */ 
/*      */       
/*      */       protected final K get(long i) {
/*  608 */         return (K)BigArrays.get((Object[][])ObjectBigArrayBigList.this.a, i);
/*      */       }
/*      */ 
/*      */       
/*      */       protected final SubListSpliterator makeForSplit(long pos, long maxPos) {
/*  613 */         return new SubListSpliterator(pos, maxPos);
/*      */       }
/*      */ 
/*      */       
/*      */       protected final long computeSplitPoint() {
/*  618 */         long defaultSplit = super.computeSplitPoint();
/*      */ 
/*      */         
/*  621 */         return BigArrays.nearestSegmentStart(defaultSplit, this.pos + 1L, getMaxPos() - 1L);
/*      */       }
/*      */ 
/*      */       
/*      */       public boolean tryAdvance(Consumer<? super K> action) {
/*  626 */         if (this.pos >= getMaxPos()) return false; 
/*  627 */         action.accept((K)BigArrays.get((Object[][])ObjectBigArrayBigList.this.a, this.pos++));
/*  628 */         return true;
/*      */       }
/*      */ 
/*      */ 
/*      */ 
/*      */       
/*      */       public void forEachRemaining(Consumer<? super K> action) {
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
/*      */         //   16: getfield this$1 : Lit/unimi/dsi/fastutil/objects/ObjectBigArrayBigList$SubList;
/*      */         //   19: getfield this$0 : Lit/unimi/dsi/fastutil/objects/ObjectBigArrayBigList;
/*      */         //   22: getfield a : [[Ljava/lang/Object;
/*      */         //   25: aload_0
/*      */         //   26: dup
/*      */         //   27: getfield pos : J
/*      */         //   30: dup2_x1
/*      */         //   31: lconst_1
/*      */         //   32: ladd
/*      */         //   33: putfield pos : J
/*      */         //   36: invokestatic get : ([[Ljava/lang/Object;J)Ljava/lang/Object;
/*      */         //   39: invokeinterface accept : (Ljava/lang/Object;)V
/*      */         //   44: goto -> 5
/*      */         //   47: return
/*      */         // Line number table:
/*      */         //   Java source line number -> byte code offset
/*      */         //   #633	-> 0
/*      */         //   #634	-> 5
/*      */         //   #635	-> 14
/*      */         //   #637	-> 47
/*      */         // Local variable table:
/*      */         //   start	length	slot	name	descriptor
/*      */         //   0	48	0	this	Lit/unimi/dsi/fastutil/objects/ObjectBigArrayBigList$SubList$SubListSpliterator;
/*      */         //   0	48	1	action	Ljava/util/function/Consumer;
/*      */         //   5	43	2	max	J
/*      */         // Local variable type table:
/*      */         //   start	length	slot	name	signature
/*      */         //   0	48	0	this	Lit/unimi/dsi/fastutil/objects/ObjectBigArrayBigList<TK;>.SubList.SubListSpliterator;
/*      */         //   0	48	1	action	Ljava/util/function/Consumer<-TK;>;
/*      */       }
/*      */     }
/*      */ 
/*      */ 
/*      */     
/*      */     public ObjectSpliterator<K> spliterator() {
/*  642 */       return new SubListSpliterator();
/*      */     }
/*      */     
/*      */     boolean contentsEquals(K[][] otherA, long otherAFrom, long otherATo) {
/*  646 */       if (ObjectBigArrayBigList.this.a == otherA && this.from == otherAFrom && this.to == otherATo) return true; 
/*  647 */       if (otherATo - otherAFrom != size64()) {
/*  648 */         return false;
/*      */       }
/*  650 */       long pos = this.to, otherPos = otherATo;
/*      */ 
/*      */ 
/*      */ 
/*      */       
/*  655 */       while (--pos >= this.from) { if (!Objects.equals(BigArrays.get((Object[][])ObjectBigArrayBigList.this.a, pos), BigArrays.get((Object[][])otherA, --otherPos))) return false;  }
/*  656 */        return true;
/*      */     }
/*      */ 
/*      */     
/*      */     public boolean equals(Object o) {
/*  661 */       if (o == this) return true; 
/*  662 */       if (o == null) return false; 
/*  663 */       if (!(o instanceof BigList)) return false; 
/*  664 */       if (o instanceof ObjectBigArrayBigList) {
/*      */         
/*  666 */         ObjectBigArrayBigList<K> other = (ObjectBigArrayBigList<K>)o;
/*  667 */         return contentsEquals(other.a, 0L, other.size64());
/*      */       } 
/*  669 */       if (o instanceof SubList) {
/*      */         
/*  671 */         SubList other = (SubList)o;
/*  672 */         return contentsEquals(other.getParentArray(), other.from, other.to);
/*      */       } 
/*  674 */       return super.equals(o);
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     int contentsCompareTo(K[][] otherA, long otherAFrom, long otherATo) {
/*      */       long i;
/*      */       long j;
/*  683 */       for (i = this.from, j = otherAFrom; i < this.to && i < otherATo; i++, j++) {
/*  684 */         K e1 = (K)BigArrays.get((Object[][])ObjectBigArrayBigList.this.a, i);
/*  685 */         K e2 = (K)BigArrays.get((Object[][])otherA, j); int r;
/*  686 */         if ((r = ((Comparable<K>)e1).compareTo(e2)) != 0) return r; 
/*      */       } 
/*  688 */       return (i < otherATo) ? -1 : ((i < this.to) ? 1 : 0);
/*      */     }
/*      */ 
/*      */ 
/*      */     
/*      */     public int compareTo(BigList<? extends K> l) {
/*  694 */       if (l instanceof ObjectBigArrayBigList) {
/*      */         
/*  696 */         ObjectBigArrayBigList<K> other = (ObjectBigArrayBigList)l;
/*  697 */         return contentsCompareTo(other.a, 0L, other.size64());
/*      */       } 
/*  699 */       if (l instanceof SubList) {
/*      */         
/*  701 */         SubList other = (SubList)l;
/*  702 */         return contentsCompareTo(other.getParentArray(), other.from, other.to);
/*      */       } 
/*  704 */       return super.compareTo(l);
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public ObjectBigList<K> subList(long from, long to) {
/*  714 */     if (from == 0L && to == size64()) return this; 
/*  715 */     ensureIndex(from);
/*  716 */     ensureIndex(to);
/*  717 */     if (from > to) throw new IndexOutOfBoundsException("Start index (" + from + ") is greater than end index (" + to + ")"); 
/*  718 */     return new SubList(from, to);
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
/*      */   public void getElements(long from, Object[][] a, long offset, long length) {
/*  731 */     BigArrays.copy((Object[][])this.a, from, a, offset, length);
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
/*      */   public void getElements(long from, Object[] a, int offset, int length) {
/*  744 */     BigArrays.copyFromBig((Object[][])this.a, from, a, offset, length);
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
/*  755 */     BigArrays.ensureFromTo(this.size, from, to);
/*  756 */     BigArrays.copy((Object[][])this.a, to, (Object[][])this.a, from, this.size - to);
/*  757 */     this.size -= to - from;
/*  758 */     BigArrays.fill((Object[][])this.a, this.size, this.size + to - from, null);
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
/*      */   public void addElements(long index, K[][] a, long offset, long length) {
/*  771 */     ensureIndex(index);
/*  772 */     BigArrays.ensureOffsetLength((Object[][])a, offset, length);
/*  773 */     grow(this.size + length);
/*  774 */     BigArrays.copy((Object[][])this.a, index, (Object[][])this.a, index + length, this.size - index);
/*  775 */     BigArrays.copy((Object[][])a, offset, (Object[][])this.a, index, length);
/*  776 */     this.size += length;
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
/*      */   public void setElements(long index, Object[][] a, long offset, long length) {
/*  789 */     BigArrays.copy(a, offset, (Object[][])this.a, index, length);
/*      */   }
/*      */ 
/*      */   
/*      */   public void forEach(Consumer<? super K> action) {
/*  794 */     for (long i = 0L; i < this.size; i++) {
/*  795 */       action.accept((K)BigArrays.get((Object[][])this.a, i));
/*      */     }
/*      */   }
/*      */ 
/*      */   
/*      */   public ObjectBigListIterator<K> listIterator(final long index) {
/*  801 */     ensureIndex(index);
/*  802 */     return new ObjectBigListIterator<K>() {
/*  803 */         long pos = index; long last = -1L;
/*      */ 
/*      */         
/*      */         public boolean hasNext() {
/*  807 */           return (this.pos < ObjectBigArrayBigList.this.size);
/*      */         }
/*      */ 
/*      */         
/*      */         public boolean hasPrevious() {
/*  812 */           return (this.pos > 0L);
/*      */         }
/*      */ 
/*      */         
/*      */         public K next() {
/*  817 */           if (!hasNext()) throw new NoSuchElementException(); 
/*  818 */           return (K)BigArrays.get((Object[][])ObjectBigArrayBigList.this.a, this.last = this.pos++);
/*      */         }
/*      */ 
/*      */         
/*      */         public K previous() {
/*  823 */           if (!hasPrevious()) throw new NoSuchElementException(); 
/*  824 */           return (K)BigArrays.get((Object[][])ObjectBigArrayBigList.this.a, this.last = --this.pos);
/*      */         }
/*      */ 
/*      */         
/*      */         public long nextIndex() {
/*  829 */           return this.pos;
/*      */         }
/*      */ 
/*      */         
/*      */         public long previousIndex() {
/*  834 */           return this.pos - 1L;
/*      */         }
/*      */ 
/*      */         
/*      */         public void add(K k) {
/*  839 */           ObjectBigArrayBigList.this.add(this.pos++, k);
/*  840 */           this.last = -1L;
/*      */         }
/*      */ 
/*      */         
/*      */         public void set(K k) {
/*  845 */           if (this.last == -1L) throw new IllegalStateException(); 
/*  846 */           ObjectBigArrayBigList.this.set(this.last, k);
/*      */         }
/*      */ 
/*      */         
/*      */         public void remove() {
/*  851 */           if (this.last == -1L) throw new IllegalStateException(); 
/*  852 */           ObjectBigArrayBigList.this.remove(this.last);
/*      */           
/*  854 */           if (this.last < this.pos) this.pos--; 
/*  855 */           this.last = -1L;
/*      */         }
/*      */ 
/*      */ 
/*      */         
/*      */         public void forEachRemaining(Consumer<? super K> action) {
/*      */           // Byte code:
/*      */           //   0: aload_0
/*      */           //   1: getfield pos : J
/*      */           //   4: aload_0
/*      */           //   5: getfield this$0 : Lit/unimi/dsi/fastutil/objects/ObjectBigArrayBigList;
/*      */           //   8: getfield size : J
/*      */           //   11: lcmp
/*      */           //   12: ifge -> 50
/*      */           //   15: aload_1
/*      */           //   16: aload_0
/*      */           //   17: getfield this$0 : Lit/unimi/dsi/fastutil/objects/ObjectBigArrayBigList;
/*      */           //   20: getfield a : [[Ljava/lang/Object;
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
/*      */           //   39: invokestatic get : ([[Ljava/lang/Object;J)Ljava/lang/Object;
/*      */           //   42: invokeinterface accept : (Ljava/lang/Object;)V
/*      */           //   47: goto -> 0
/*      */           //   50: return
/*      */           // Line number table:
/*      */           //   Java source line number -> byte code offset
/*      */           //   #860	-> 0
/*      */           //   #861	-> 15
/*      */           //   #863	-> 50
/*      */           // Local variable table:
/*      */           //   start	length	slot	name	descriptor
/*      */           //   0	51	0	this	Lit/unimi/dsi/fastutil/objects/ObjectBigArrayBigList$1;
/*      */           //   0	51	1	action	Ljava/util/function/Consumer;
/*      */           // Local variable type table:
/*      */           //   start	length	slot	name	signature
/*      */           //   0	51	0	this	Lit/unimi/dsi/fastutil/objects/ObjectBigArrayBigList$1;
/*      */           //   0	51	1	action	Ljava/util/function/Consumer<-TK;>;
/*      */         }
/*      */ 
/*      */ 
/*      */         
/*      */         public long back(long n) {
/*  867 */           if (n < 0L) throw new IllegalArgumentException("Argument must be nonnegative: " + n); 
/*  868 */           long remaining = ObjectBigArrayBigList.this.size - this.pos;
/*  869 */           if (n < remaining) {
/*  870 */             this.pos -= n;
/*      */           } else {
/*  872 */             n = remaining;
/*  873 */             this.pos = 0L;
/*      */           } 
/*  875 */           this.last = this.pos;
/*  876 */           return n;
/*      */         }
/*      */ 
/*      */         
/*      */         public long skip(long n) {
/*  881 */           if (n < 0L) throw new IllegalArgumentException("Argument must be nonnegative: " + n); 
/*  882 */           long remaining = ObjectBigArrayBigList.this.size - this.pos;
/*  883 */           if (n < remaining) {
/*  884 */             this.pos += n;
/*      */           } else {
/*  886 */             n = remaining;
/*  887 */             this.pos = ObjectBigArrayBigList.this.size;
/*      */           } 
/*  889 */           this.last = this.pos - 1L;
/*  890 */           return n;
/*      */         }
/*      */       };
/*      */   }
/*      */   
/*      */   private final class Spliterator
/*      */     implements ObjectSpliterator<K>
/*      */   {
/*      */     boolean hasSplit = false;
/*      */     long pos;
/*      */     long max;
/*      */     
/*      */     public Spliterator() {
/*  903 */       this(0L, ObjectBigArrayBigList.this.size, false);
/*      */     }
/*      */     
/*      */     private Spliterator(long pos, long max, boolean hasSplit) {
/*  907 */       assert pos <= max : "pos " + pos + " must be <= max " + max;
/*  908 */       this.pos = pos;
/*  909 */       this.max = max;
/*  910 */       this.hasSplit = hasSplit;
/*      */     }
/*      */     
/*      */     private long getWorkingMax() {
/*  914 */       return this.hasSplit ? this.max : ObjectBigArrayBigList.this.size;
/*      */     }
/*      */ 
/*      */     
/*      */     public int characteristics() {
/*  919 */       return 16464;
/*      */     }
/*      */ 
/*      */     
/*      */     public long estimateSize() {
/*  924 */       return getWorkingMax() - this.pos;
/*      */     }
/*      */ 
/*      */     
/*      */     public boolean tryAdvance(Consumer<? super K> action) {
/*  929 */       if (this.pos >= getWorkingMax()) return false; 
/*  930 */       action.accept((K)BigArrays.get((Object[][])ObjectBigArrayBigList.this.a, this.pos++));
/*  931 */       return true;
/*      */     }
/*      */ 
/*      */     
/*      */     public void forEachRemaining(Consumer<? super K> action) {
/*  936 */       for (long max = getWorkingMax(); this.pos < max; this.pos++) {
/*  937 */         action.accept((K)BigArrays.get((Object[][])ObjectBigArrayBigList.this.a, this.pos));
/*      */       }
/*      */     }
/*      */ 
/*      */     
/*      */     public long skip(long n) {
/*  943 */       if (n < 0L) throw new IllegalArgumentException("Argument must be nonnegative: " + n); 
/*  944 */       long max = getWorkingMax();
/*  945 */       if (this.pos >= max) return 0L; 
/*  946 */       long remaining = max - this.pos;
/*  947 */       if (n < remaining) {
/*  948 */         this.pos += n;
/*  949 */         return n;
/*      */       } 
/*  951 */       n = remaining;
/*  952 */       this.pos = max;
/*  953 */       return n;
/*      */     }
/*      */ 
/*      */     
/*      */     public ObjectSpliterator<K> trySplit() {
/*  958 */       long max = getWorkingMax();
/*  959 */       long retLen = max - this.pos >> 1L;
/*  960 */       if (retLen <= 1L) return null;
/*      */       
/*  962 */       this.max = max;
/*  963 */       long myNewPos = this.pos + retLen;
/*      */ 
/*      */       
/*  966 */       myNewPos = BigArrays.nearestSegmentStart(myNewPos, this.pos + 1L, max - 1L);
/*  967 */       long retMax = myNewPos;
/*  968 */       long oldPos = this.pos;
/*  969 */       this.pos = myNewPos;
/*  970 */       this.hasSplit = true;
/*  971 */       return new Spliterator(oldPos, retMax, true);
/*      */     }
/*      */   }
/*      */ 
/*      */   
/*      */   public ObjectSpliterator<K> spliterator() {
/*  977 */     return new Spliterator();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public ObjectBigArrayBigList<K> clone() {
/*      */     ObjectBigArrayBigList<K> c;
/*  985 */     if (getClass() == ObjectBigArrayBigList.class) {
/*  986 */       c = new ObjectBigArrayBigList(this.size);
/*  987 */       c.size = this.size;
/*      */     } else {
/*      */       try {
/*  990 */         c = (ObjectBigArrayBigList<K>)super.clone();
/*  991 */       } catch (CloneNotSupportedException e) {
/*      */         
/*  993 */         throw new InternalError(e);
/*      */       } 
/*  995 */       c.a = (K[][])ObjectBigArrays.newBigArray(this.size);
/*      */     } 
/*  997 */     BigArrays.copy((Object[][])this.a, 0L, (Object[][])c.a, 0L, this.size);
/*  998 */     return c;
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
/*      */   public boolean equals(ObjectBigArrayBigList<K> l) {
/* 1012 */     if (l == this) return true; 
/* 1013 */     long s = size64();
/* 1014 */     if (s != l.size64()) return false; 
/* 1015 */     K[][] a1 = this.a;
/* 1016 */     K[][] a2 = l.a;
/*      */     
/* 1018 */     if (a1 == a2) return true;
/*      */     
/* 1020 */     while (s-- != 0L) { if (!Objects.equals(BigArrays.get((Object[][])a1, s), BigArrays.get((Object[][])a2, s))) return false;  }
/* 1021 */      return true;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean equals(Object o) {
/* 1027 */     if (o == this) return true; 
/* 1028 */     if (o == null) return false; 
/* 1029 */     if (!(o instanceof BigList)) return false; 
/* 1030 */     if (o instanceof ObjectBigArrayBigList)
/*      */     {
/* 1032 */       return equals((ObjectBigArrayBigList<K>)o);
/*      */     }
/* 1034 */     if (o instanceof SubList)
/*      */     {
/*      */       
/* 1037 */       return ((SubList)o).equals(this);
/*      */     }
/* 1039 */     return super.equals(o);
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
/*      */   public int compareTo(ObjectBigArrayBigList<? extends K> l) {
/* 1055 */     long s1 = size64(), s2 = l.size64();
/* 1056 */     K[][] a1 = this.a, a2 = l.a;
/*      */     
/*      */     int i;
/* 1059 */     for (i = 0; i < s1 && i < s2; i++) {
/* 1060 */       K e1 = (K)BigArrays.get((Object[][])a1, i);
/* 1061 */       K e2 = (K)BigArrays.get((Object[][])a2, i); int r;
/* 1062 */       if ((r = ((Comparable<K>)e1).compareTo(e2)) != 0) return r; 
/*      */     } 
/* 1064 */     return (i < s2) ? -1 : ((i < s1) ? 1 : 0);
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public int compareTo(BigList<? extends K> l) {
/* 1070 */     if (l instanceof ObjectBigArrayBigList) {
/* 1071 */       return compareTo((ObjectBigArrayBigList<? extends K>)l);
/*      */     }
/* 1073 */     if (l instanceof SubList)
/*      */     {
/* 1075 */       return -((SubList)l).compareTo(this);
/*      */     }
/* 1077 */     return super.compareTo(l);
/*      */   }
/*      */   
/*      */   private void writeObject(ObjectOutputStream s) throws IOException {
/* 1081 */     s.defaultWriteObject();
/* 1082 */     for (int i = 0; i < this.size; ) { s.writeObject(BigArrays.get((Object[][])this.a, i)); i++; }
/*      */   
/*      */   }
/*      */   
/*      */   private void readObject(ObjectInputStream s) throws IOException, ClassNotFoundException {
/* 1087 */     s.defaultReadObject();
/* 1088 */     this.a = (K[][])ObjectBigArrays.newBigArray(this.size);
/* 1089 */     for (int i = 0; i < this.size; ) { BigArrays.set((Object[][])this.a, i, s.readObject()); i++; }
/*      */   
/*      */   }
/*      */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\i\\unimi\dsi\fastutil\objects\ObjectBigArrayBigList.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */