/*      */ package it.unimi.dsi.fastutil.objects;
/*      */ 
/*      */ import it.unimi.dsi.fastutil.Arrays;
/*      */ import it.unimi.dsi.fastutil.SafeMath;
/*      */ import java.io.IOException;
/*      */ import java.io.ObjectInputStream;
/*      */ import java.io.ObjectOutputStream;
/*      */ import java.io.Serializable;
/*      */ import java.lang.reflect.Array;
/*      */ import java.util.Arrays;
/*      */ import java.util.Collection;
/*      */ import java.util.Comparator;
/*      */ import java.util.Iterator;
/*      */ import java.util.List;
/*      */ import java.util.ListIterator;
/*      */ import java.util.NoSuchElementException;
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
/*      */ public class ReferenceArrayList<K>
/*      */   extends AbstractReferenceList<K>
/*      */   implements RandomAccess, Cloneable, Serializable
/*      */ {
/*      */   private static final long serialVersionUID = -7046029254386353131L;
/*      */   public static final int DEFAULT_INITIAL_CAPACITY = 10;
/*      */   protected final boolean wrapped;
/*      */   protected transient K[] a;
/*      */   protected int size;
/*      */   
/*      */   private static final <K> K[] copyArraySafe(K[] a, int length) {
/*   80 */     if (length == 0) return (K[])ObjectArrays.EMPTY_ARRAY; 
/*   81 */     return Arrays.copyOf(a, length, (Class)Object[].class);
/*      */   }
/*      */   
/*      */   private static final <K> K[] copyArrayFromSafe(ReferenceArrayList<K> l) {
/*   85 */     return copyArraySafe(l.a, l.size);
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
/*      */   protected ReferenceArrayList(K[] a, boolean wrapped) {
/*   97 */     this.a = a;
/*   98 */     this.wrapped = wrapped;
/*      */   }
/*      */ 
/*      */   
/*      */   private void initArrayFromCapacity(int capacity) {
/*  103 */     if (capacity < 0) throw new IllegalArgumentException("Initial capacity (" + capacity + ") is negative"); 
/*  104 */     if (capacity == 0) { this.a = (K[])ObjectArrays.EMPTY_ARRAY; }
/*  105 */     else { this.a = (K[])new Object[capacity]; }
/*      */   
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public ReferenceArrayList(int capacity) {
/*  114 */     initArrayFromCapacity(capacity);
/*  115 */     this.wrapped = false;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public ReferenceArrayList() {
/*  121 */     this.a = (K[])ObjectArrays.DEFAULT_EMPTY_ARRAY;
/*  122 */     this.wrapped = false;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public ReferenceArrayList(Collection<? extends K> c) {
/*  131 */     if (c instanceof ReferenceArrayList) {
/*  132 */       this.a = copyArrayFromSafe((ReferenceArrayList)c);
/*  133 */       this.size = this.a.length;
/*      */     } else {
/*  135 */       initArrayFromCapacity(c.size());
/*  136 */       if (c instanceof ReferenceList) {
/*  137 */         ((ReferenceList)c).getElements(0, (Object[])this.a, 0, this.size = c.size());
/*      */       } else {
/*  139 */         this.size = ObjectIterators.unwrap(c.iterator(), this.a);
/*      */       } 
/*      */     } 
/*  142 */     this.wrapped = false;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public ReferenceArrayList(ReferenceCollection<? extends K> c) {
/*  151 */     if (c instanceof ReferenceArrayList) {
/*  152 */       this.a = copyArrayFromSafe((ReferenceArrayList)c);
/*  153 */       this.size = this.a.length;
/*      */     } else {
/*  155 */       initArrayFromCapacity(c.size());
/*  156 */       if (c instanceof ReferenceList) {
/*  157 */         ((ReferenceList)c).getElements(0, (Object[])this.a, 0, this.size = c.size());
/*      */       } else {
/*  159 */         this.size = ObjectIterators.unwrap(c.iterator(), this.a);
/*      */       } 
/*      */     } 
/*  162 */     this.wrapped = false;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public ReferenceArrayList(ReferenceList<? extends K> l) {
/*  171 */     if (l instanceof ReferenceArrayList) {
/*  172 */       this.a = copyArrayFromSafe((ReferenceArrayList)l);
/*  173 */       this.size = this.a.length;
/*      */     } else {
/*  175 */       initArrayFromCapacity(l.size());
/*  176 */       l.getElements(0, (Object[])this.a, 0, this.size = l.size());
/*      */     } 
/*  178 */     this.wrapped = false;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public ReferenceArrayList(K[] a) {
/*  187 */     this(a, 0, a.length);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public ReferenceArrayList(K[] a, int offset, int length) {
/*  198 */     this(length);
/*  199 */     System.arraycopy(a, offset, this.a, 0, length);
/*  200 */     this.size = length;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public ReferenceArrayList(Iterator<? extends K> i) {
/*  209 */     this();
/*  210 */     for (; i.hasNext(); add(i.next()));
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public ReferenceArrayList(ObjectIterator<? extends K> i) {
/*  219 */     this();
/*  220 */     for (; i.hasNext(); add(i.next()));
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
/*      */   public K[] elements() {
/*  239 */     return this.a;
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
/*      */   public static <K> ReferenceArrayList<K> wrap(K[] a, int length) {
/*  254 */     if (length > a.length) throw new IllegalArgumentException("The specified length (" + length + ") is greater than the array size (" + a.length + ")"); 
/*  255 */     ReferenceArrayList<K> l = new ReferenceArrayList<>(a, true);
/*  256 */     l.size = length;
/*  257 */     return l;
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
/*      */   public static <K> ReferenceArrayList<K> wrap(K[] a) {
/*  271 */     return wrap(a, a.length);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static <K> ReferenceArrayList<K> of() {
/*  280 */     return new ReferenceArrayList<>();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   @SafeVarargs
/*      */   public static <K> ReferenceArrayList<K> of(K... init) {
/*  292 */     return wrap(init);
/*      */   }
/*      */ 
/*      */   
/*      */   ReferenceArrayList<K> combine(ReferenceArrayList<? extends K> toAddFrom) {
/*  297 */     addAll(toAddFrom);
/*  298 */     return this;
/*      */   }
/*      */   
/*  301 */   private static final Collector<Object, ?, ReferenceArrayList<Object>> TO_LIST_COLLECTOR = (Collector)Collector.of(ReferenceArrayList::new, ReferenceArrayList::add, ReferenceArrayList::combine, new Collector.Characteristics[0]);
/*      */ 
/*      */ 
/*      */   
/*      */   public static <K> Collector<K, ?, ReferenceArrayList<K>> toList() {
/*  306 */     return (Collector)TO_LIST_COLLECTOR;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static <K> Collector<K, ?, ReferenceArrayList<K>> toListWithExpectedSize(int expectedSize) {
/*  314 */     if (expectedSize <= 10)
/*      */     {
/*      */       
/*  317 */       return toList();
/*      */     }
/*  319 */     return (Collector)Collector.of(new ReferenceCollections.SizeDecreasingSupplier<>(expectedSize, size -> (size <= 10) ? new ReferenceArrayList() : new ReferenceArrayList(size)), ReferenceArrayList::add, ReferenceArrayList::combine, new Collector.Characteristics[0]);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void ensureCapacity(int capacity) {
/*  329 */     if (capacity <= this.a.length || (this.a == ObjectArrays.DEFAULT_EMPTY_ARRAY && capacity <= 10))
/*  330 */       return;  if (this.wrapped) { this.a = ObjectArrays.ensureCapacity(this.a, capacity, this.size); }
/*      */     
/*  332 */     else if (capacity > this.a.length)
/*  333 */     { Object[] t = new Object[capacity];
/*  334 */       System.arraycopy(this.a, 0, t, 0, this.size);
/*  335 */       this.a = (K[])t; }
/*      */ 
/*      */     
/*  338 */     assert this.size <= this.a.length;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private void grow(int capacity) {
/*  349 */     if (capacity <= this.a.length)
/*  350 */       return;  if (this.a != ObjectArrays.DEFAULT_EMPTY_ARRAY) { capacity = (int)Math.max(Math.min(this.a.length + (this.a.length >> 1), 2147483639L), capacity); }
/*  351 */     else if (capacity < 10) { capacity = 10; }
/*  352 */      if (this.wrapped) { this.a = ObjectArrays.forceCapacity(this.a, capacity, this.size); }
/*      */     else
/*  354 */     { Object[] t = new Object[capacity];
/*  355 */       System.arraycopy(this.a, 0, t, 0, this.size);
/*  356 */       this.a = (K[])t; }
/*      */     
/*  358 */     assert this.size <= this.a.length;
/*      */   }
/*      */ 
/*      */   
/*      */   public void add(int index, K k) {
/*  363 */     ensureIndex(index);
/*  364 */     grow(this.size + 1);
/*  365 */     if (index != this.size) System.arraycopy(this.a, index, this.a, index + 1, this.size - index); 
/*  366 */     this.a[index] = k;
/*  367 */     this.size++;
/*  368 */     assert this.size <= this.a.length;
/*      */   }
/*      */ 
/*      */   
/*      */   public boolean add(K k) {
/*  373 */     grow(this.size + 1);
/*  374 */     this.a[this.size++] = k;
/*  375 */     assert this.size <= this.a.length;
/*  376 */     return true;
/*      */   }
/*      */ 
/*      */   
/*      */   public K get(int index) {
/*  381 */     if (index >= this.size) throw new IndexOutOfBoundsException("Index (" + index + ") is greater than or equal to list size (" + this.size + ")"); 
/*  382 */     return this.a[index];
/*      */   }
/*      */ 
/*      */   
/*      */   public int indexOf(Object k) {
/*  387 */     for (int i = 0; i < this.size; ) { if (k == this.a[i]) return i;  i++; }
/*  388 */      return -1;
/*      */   }
/*      */ 
/*      */   
/*      */   public int lastIndexOf(Object k) {
/*  393 */     for (int i = this.size; i-- != 0;) { if (k == this.a[i]) return i;  }
/*  394 */      return -1;
/*      */   }
/*      */ 
/*      */   
/*      */   public K remove(int index) {
/*  399 */     if (index >= this.size) throw new IndexOutOfBoundsException("Index (" + index + ") is greater than or equal to list size (" + this.size + ")"); 
/*  400 */     K old = this.a[index];
/*  401 */     this.size--;
/*  402 */     if (index != this.size) System.arraycopy(this.a, index + 1, this.a, index, this.size - index); 
/*  403 */     this.a[this.size] = null;
/*  404 */     assert this.size <= this.a.length;
/*  405 */     return old;
/*      */   }
/*      */ 
/*      */   
/*      */   public boolean remove(Object k) {
/*  410 */     int index = indexOf(k);
/*  411 */     if (index == -1) return false; 
/*  412 */     remove(index);
/*  413 */     assert this.size <= this.a.length;
/*  414 */     return true;
/*      */   }
/*      */ 
/*      */   
/*      */   public K set(int index, K k) {
/*  419 */     if (index >= this.size) throw new IndexOutOfBoundsException("Index (" + index + ") is greater than or equal to list size (" + this.size + ")"); 
/*  420 */     K old = this.a[index];
/*  421 */     this.a[index] = k;
/*  422 */     return old;
/*      */   }
/*      */ 
/*      */   
/*      */   public void clear() {
/*  427 */     Arrays.fill((Object[])this.a, 0, this.size, (Object)null);
/*  428 */     this.size = 0;
/*  429 */     assert this.size <= this.a.length;
/*      */   }
/*      */ 
/*      */   
/*      */   public int size() {
/*  434 */     return this.size;
/*      */   }
/*      */ 
/*      */   
/*      */   public void size(int size) {
/*  439 */     if (size > this.a.length) this.a = ObjectArrays.forceCapacity(this.a, size, this.size); 
/*  440 */     if (size > this.size) { Arrays.fill((Object[])this.a, this.size, size, (Object)null); }
/*  441 */     else { Arrays.fill((Object[])this.a, size, this.size, (Object)null); }
/*  442 */      this.size = size;
/*      */   }
/*      */ 
/*      */   
/*      */   public boolean isEmpty() {
/*  447 */     return (this.size == 0);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void trim() {
/*  456 */     trim(0);
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
/*      */   public void trim(int n) {
/*  475 */     if (n >= this.a.length || this.size == this.a.length)
/*  476 */       return;  K[] t = (K[])new Object[Math.max(n, this.size)];
/*  477 */     System.arraycopy(this.a, 0, t, 0, this.size);
/*  478 */     this.a = t;
/*  479 */     assert this.size <= this.a.length;
/*      */   }
/*      */   
/*      */   private class SubList extends AbstractReferenceList.ReferenceRandomAccessSubList<K> {
/*      */     private static final long serialVersionUID = -3185226345314976296L;
/*      */     
/*      */     protected SubList(int from, int to) {
/*  486 */       super(from, to);
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     private K[] getParentArray() {
/*  493 */       return ReferenceArrayList.this.a;
/*      */     }
/*      */ 
/*      */     
/*      */     public K get(int i) {
/*  498 */       ensureRestrictedIndex(i);
/*  499 */       return ReferenceArrayList.this.a[i + this.from];
/*      */     }
/*      */     
/*      */     private final class SubListIterator
/*      */       extends ObjectIterators.AbstractIndexBasedListIterator<K>
/*      */     {
/*      */       SubListIterator(int index) {
/*  506 */         super(0, index);
/*      */       }
/*      */ 
/*      */       
/*      */       protected final K get(int i) {
/*  511 */         return ReferenceArrayList.this.a[ReferenceArrayList.SubList.this.from + i];
/*      */       }
/*      */ 
/*      */       
/*      */       protected final void add(int i, K k) {
/*  516 */         ReferenceArrayList.SubList.this.add(i, k);
/*      */       }
/*      */ 
/*      */       
/*      */       protected final void set(int i, K k) {
/*  521 */         ReferenceArrayList.SubList.this.set(i, k);
/*      */       }
/*      */ 
/*      */       
/*      */       protected final void remove(int i) {
/*  526 */         ReferenceArrayList.SubList.this.remove(i);
/*      */       }
/*      */ 
/*      */       
/*      */       protected final int getMaxPos() {
/*  531 */         return ReferenceArrayList.SubList.this.to - ReferenceArrayList.SubList.this.from;
/*      */       }
/*      */ 
/*      */       
/*      */       public K next() {
/*  536 */         if (!hasNext()) throw new NoSuchElementException(); 
/*  537 */         return ReferenceArrayList.this.a[ReferenceArrayList.SubList.this.from + (this.lastReturned = this.pos++)];
/*      */       }
/*      */ 
/*      */       
/*      */       public K previous() {
/*  542 */         if (!hasPrevious()) throw new NoSuchElementException(); 
/*  543 */         return ReferenceArrayList.this.a[ReferenceArrayList.SubList.this.from + (this.lastReturned = --this.pos)];
/*      */       }
/*      */ 
/*      */       
/*      */       public void forEachRemaining(Consumer<? super K> action) {
/*  548 */         int max = ReferenceArrayList.SubList.this.to - ReferenceArrayList.SubList.this.from;
/*  549 */         while (this.pos < max) {
/*  550 */           action.accept(ReferenceArrayList.this.a[ReferenceArrayList.SubList.this.from + (this.lastReturned = this.pos++)]);
/*      */         }
/*      */       }
/*      */     }
/*      */ 
/*      */     
/*      */     public ObjectListIterator<K> listIterator(int index) {
/*  557 */       return new SubListIterator(index);
/*      */     }
/*      */     
/*      */     private final class SubListSpliterator
/*      */       extends ObjectSpliterators.LateBindingSizeIndexBasedSpliterator<K> {
/*      */       SubListSpliterator() {
/*  563 */         super(ReferenceArrayList.SubList.this.from);
/*      */       }
/*      */       
/*      */       private SubListSpliterator(int pos, int maxPos) {
/*  567 */         super(pos, maxPos);
/*      */       }
/*      */ 
/*      */       
/*      */       protected final int getMaxPosFromBackingStore() {
/*  572 */         return ReferenceArrayList.SubList.this.to;
/*      */       }
/*      */ 
/*      */       
/*      */       protected final K get(int i) {
/*  577 */         return ReferenceArrayList.this.a[i];
/*      */       }
/*      */ 
/*      */       
/*      */       protected final SubListSpliterator makeForSplit(int pos, int maxPos) {
/*  582 */         return new SubListSpliterator(pos, maxPos);
/*      */       }
/*      */ 
/*      */       
/*      */       public boolean tryAdvance(Consumer<? super K> action) {
/*  587 */         if (this.pos >= getMaxPos()) return false; 
/*  588 */         action.accept(ReferenceArrayList.this.a[this.pos++]);
/*  589 */         return true;
/*      */       }
/*      */ 
/*      */       
/*      */       public void forEachRemaining(Consumer<? super K> action) {
/*  594 */         int max = getMaxPos();
/*  595 */         while (this.pos < max) {
/*  596 */           action.accept(ReferenceArrayList.this.a[this.pos++]);
/*      */         }
/*      */       }
/*      */     }
/*      */ 
/*      */     
/*      */     public ObjectSpliterator<K> spliterator() {
/*  603 */       return new SubListSpliterator();
/*      */     }
/*      */     
/*      */     boolean contentsEquals(K[] otherA, int otherAFrom, int otherATo) {
/*  607 */       if (ReferenceArrayList.this.a == otherA && this.from == otherAFrom && this.to == otherATo) return true; 
/*  608 */       if (otherATo - otherAFrom != size()) {
/*  609 */         return false;
/*      */       }
/*  611 */       int pos = this.from, otherPos = otherAFrom;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/*  617 */       while (pos < this.to) { if (ReferenceArrayList.this.a[pos++] != otherA[otherPos++]) return false;  }
/*  618 */        return true;
/*      */     }
/*      */ 
/*      */     
/*      */     public boolean equals(Object o) {
/*  623 */       if (o == this) return true; 
/*  624 */       if (o == null) return false; 
/*  625 */       if (!(o instanceof List)) return false; 
/*  626 */       if (o instanceof ReferenceArrayList) {
/*      */         
/*  628 */         ReferenceArrayList<K> other = (ReferenceArrayList<K>)o;
/*  629 */         return contentsEquals(other.a, 0, other.size());
/*      */       } 
/*  631 */       if (o instanceof SubList) {
/*      */         
/*  633 */         SubList other = (SubList)o;
/*  634 */         return contentsEquals(other.getParentArray(), other.from, other.to);
/*      */       } 
/*  636 */       return super.equals(o);
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public ReferenceList<K> subList(int from, int to) {
/*  646 */     if (from == 0 && to == size()) return this; 
/*  647 */     ensureIndex(from);
/*  648 */     ensureIndex(to);
/*  649 */     if (from > to) throw new IndexOutOfBoundsException("Start index (" + from + ") is greater than end index (" + to + ")"); 
/*  650 */     return new SubList(from, to);
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
/*      */   public void getElements(int from, Object[] a, int offset, int length) {
/*  663 */     ObjectArrays.ensureOffsetLength(a, offset, length);
/*  664 */     System.arraycopy(this.a, from, a, offset, length);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void removeElements(int from, int to) {
/*  675 */     Arrays.ensureFromTo(this.size, from, to);
/*  676 */     System.arraycopy(this.a, to, this.a, from, this.size - to);
/*  677 */     this.size -= to - from;
/*  678 */     int i = to - from;
/*  679 */     for (; i-- != 0; this.a[this.size + i] = null);
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
/*      */   public void addElements(int index, K[] a, int offset, int length) {
/*  692 */     ensureIndex(index);
/*  693 */     ObjectArrays.ensureOffsetLength(a, offset, length);
/*  694 */     grow(this.size + length);
/*  695 */     System.arraycopy(this.a, index, this.a, index + length, this.size - index);
/*  696 */     System.arraycopy(a, offset, this.a, index, length);
/*  697 */     this.size += length;
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
/*      */   public void setElements(int index, K[] a, int offset, int length) {
/*  710 */     ensureIndex(index);
/*  711 */     ObjectArrays.ensureOffsetLength(a, offset, length);
/*  712 */     if (index + length > this.size) throw new IndexOutOfBoundsException("End index (" + (index + length) + ") is greater than list size (" + this.size + ")"); 
/*  713 */     System.arraycopy(a, offset, this.a, index, length);
/*      */   }
/*      */ 
/*      */   
/*      */   public void forEach(Consumer<? super K> action) {
/*  718 */     for (int i = 0; i < this.size; i++) {
/*  719 */       action.accept(this.a[i]);
/*      */     }
/*      */   }
/*      */ 
/*      */   
/*      */   public boolean addAll(int index, Collection<? extends K> c) {
/*  725 */     if (c instanceof ReferenceList) {
/*  726 */       return addAll(index, (ReferenceList<? extends K>)c);
/*      */     }
/*  728 */     ensureIndex(index);
/*  729 */     int n = c.size();
/*  730 */     if (n == 0) return false; 
/*  731 */     grow(this.size + n);
/*  732 */     System.arraycopy(this.a, index, this.a, index + n, this.size - index);
/*  733 */     Iterator<? extends K> i = c.iterator();
/*  734 */     this.size += n;
/*  735 */     for (; n-- != 0; this.a[index++] = i.next());
/*  736 */     assert this.size <= this.a.length;
/*  737 */     return true;
/*      */   }
/*      */ 
/*      */   
/*      */   public boolean addAll(int index, ReferenceList<? extends K> l) {
/*  742 */     ensureIndex(index);
/*  743 */     int n = l.size();
/*  744 */     if (n == 0) return false; 
/*  745 */     grow(this.size + n);
/*  746 */     System.arraycopy(this.a, index, this.a, index + n, this.size - index);
/*  747 */     l.getElements(0, (Object[])this.a, index, n);
/*  748 */     this.size += n;
/*  749 */     assert this.size <= this.a.length;
/*  750 */     return true;
/*      */   }
/*      */ 
/*      */   
/*      */   public boolean removeAll(Collection<?> c) {
/*  755 */     K[] arrayOfK = this.a;
/*  756 */     int j = 0;
/*  757 */     for (int i = 0; i < this.size; ) { if (!c.contains(arrayOfK[i])) arrayOfK[j++] = arrayOfK[i];  i++; }
/*  758 */      Arrays.fill((Object[])arrayOfK, j, this.size, (Object)null);
/*  759 */     boolean modified = (this.size != j);
/*  760 */     this.size = j;
/*  761 */     return modified;
/*      */   }
/*      */ 
/*      */   
/*      */   public Object[] toArray() {
/*  766 */     int size = size();
/*      */     
/*  768 */     if (size == 0) return ObjectArrays.EMPTY_ARRAY; 
/*  769 */     return Arrays.copyOf(this.a, size, Object[].class);
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public <T> T[] toArray(T[] a) {
/*  775 */     if (a == null) {
/*  776 */       a = (T[])new Object[size()];
/*  777 */     } else if (a.length < size()) {
/*  778 */       a = (T[])Array.newInstance(a.getClass().getComponentType(), size());
/*      */     } 
/*  780 */     System.arraycopy(this.a, 0, a, 0, size());
/*  781 */     if (a.length > size()) {
/*  782 */       a[size()] = null;
/*      */     }
/*  784 */     return a;
/*      */   }
/*      */ 
/*      */   
/*      */   public ObjectListIterator<K> listIterator(final int index) {
/*  789 */     ensureIndex(index);
/*  790 */     return new ObjectListIterator<K>() {
/*  791 */         int pos = index; int last = -1;
/*      */ 
/*      */         
/*      */         public boolean hasNext() {
/*  795 */           return (this.pos < ReferenceArrayList.this.size);
/*      */         }
/*      */ 
/*      */         
/*      */         public boolean hasPrevious() {
/*  800 */           return (this.pos > 0);
/*      */         }
/*      */ 
/*      */         
/*      */         public K next() {
/*  805 */           if (!hasNext()) throw new NoSuchElementException(); 
/*  806 */           return ReferenceArrayList.this.a[this.last = this.pos++];
/*      */         }
/*      */ 
/*      */         
/*      */         public K previous() {
/*  811 */           if (!hasPrevious()) throw new NoSuchElementException(); 
/*  812 */           return ReferenceArrayList.this.a[this.last = --this.pos];
/*      */         }
/*      */ 
/*      */         
/*      */         public int nextIndex() {
/*  817 */           return this.pos;
/*      */         }
/*      */ 
/*      */         
/*      */         public int previousIndex() {
/*  822 */           return this.pos - 1;
/*      */         }
/*      */ 
/*      */         
/*      */         public void add(K k) {
/*  827 */           ReferenceArrayList.this.add(this.pos++, k);
/*  828 */           this.last = -1;
/*      */         }
/*      */ 
/*      */         
/*      */         public void set(K k) {
/*  833 */           if (this.last == -1) throw new IllegalStateException(); 
/*  834 */           ReferenceArrayList.this.set(this.last, k);
/*      */         }
/*      */ 
/*      */         
/*      */         public void remove() {
/*  839 */           if (this.last == -1) throw new IllegalStateException(); 
/*  840 */           ReferenceArrayList.this.remove(this.last);
/*      */           
/*  842 */           if (this.last < this.pos) this.pos--; 
/*  843 */           this.last = -1;
/*      */         }
/*      */ 
/*      */         
/*      */         public void forEachRemaining(Consumer<? super K> action) {
/*  848 */           while (this.pos < ReferenceArrayList.this.size) {
/*  849 */             action.accept(ReferenceArrayList.this.a[this.last = this.pos++]);
/*      */           }
/*      */         }
/*      */ 
/*      */         
/*      */         public int back(int n) {
/*  855 */           if (n < 0) throw new IllegalArgumentException("Argument must be nonnegative: " + n); 
/*  856 */           int remaining = ReferenceArrayList.this.size - this.pos;
/*  857 */           if (n < remaining) {
/*  858 */             this.pos -= n;
/*      */           } else {
/*  860 */             n = remaining;
/*  861 */             this.pos = 0;
/*      */           } 
/*  863 */           this.last = this.pos;
/*  864 */           return n;
/*      */         }
/*      */ 
/*      */         
/*      */         public int skip(int n) {
/*  869 */           if (n < 0) throw new IllegalArgumentException("Argument must be nonnegative: " + n); 
/*  870 */           int remaining = ReferenceArrayList.this.size - this.pos;
/*  871 */           if (n < remaining) {
/*  872 */             this.pos += n;
/*      */           } else {
/*  874 */             n = remaining;
/*  875 */             this.pos = ReferenceArrayList.this.size;
/*      */           } 
/*  877 */           this.last = this.pos - 1;
/*  878 */           return n;
/*      */         }
/*      */       };
/*      */   }
/*      */ 
/*      */   
/*      */   private final class Spliterator
/*      */     implements ObjectSpliterator<K>
/*      */   {
/*      */     boolean hasSplit = false;
/*      */     int pos;
/*      */     int max;
/*      */     
/*      */     public Spliterator() {
/*  892 */       this(0, ReferenceArrayList.this.size, false);
/*      */     }
/*      */     
/*      */     private Spliterator(int pos, int max, boolean hasSplit) {
/*  896 */       assert pos <= max : "pos " + pos + " must be <= max " + max;
/*  897 */       this.pos = pos;
/*  898 */       this.max = max;
/*  899 */       this.hasSplit = hasSplit;
/*      */     }
/*      */     
/*      */     private int getWorkingMax() {
/*  903 */       return this.hasSplit ? this.max : ReferenceArrayList.this.size;
/*      */     }
/*      */ 
/*      */     
/*      */     public int characteristics() {
/*  908 */       return 16464;
/*      */     }
/*      */ 
/*      */     
/*      */     public long estimateSize() {
/*  913 */       return (getWorkingMax() - this.pos);
/*      */     }
/*      */ 
/*      */     
/*      */     public boolean tryAdvance(Consumer<? super K> action) {
/*  918 */       if (this.pos >= getWorkingMax()) return false; 
/*  919 */       action.accept(ReferenceArrayList.this.a[this.pos++]);
/*  920 */       return true;
/*      */     }
/*      */ 
/*      */     
/*      */     public void forEachRemaining(Consumer<? super K> action) {
/*  925 */       for (int max = getWorkingMax(); this.pos < max; this.pos++) {
/*  926 */         action.accept(ReferenceArrayList.this.a[this.pos]);
/*      */       }
/*      */     }
/*      */ 
/*      */     
/*      */     public long skip(long n) {
/*  932 */       if (n < 0L) throw new IllegalArgumentException("Argument must be nonnegative: " + n); 
/*  933 */       int max = getWorkingMax();
/*  934 */       if (this.pos >= max) return 0L; 
/*  935 */       int remaining = max - this.pos;
/*  936 */       if (n < remaining) {
/*  937 */         this.pos = SafeMath.safeLongToInt(this.pos + n);
/*  938 */         return n;
/*      */       } 
/*  940 */       n = remaining;
/*  941 */       this.pos = max;
/*  942 */       return n;
/*      */     }
/*      */ 
/*      */     
/*      */     public ObjectSpliterator<K> trySplit() {
/*  947 */       int max = getWorkingMax();
/*  948 */       int retLen = max - this.pos >> 1;
/*  949 */       if (retLen <= 1) return null;
/*      */       
/*  951 */       this.max = max;
/*  952 */       int myNewPos = this.pos + retLen;
/*  953 */       int retMax = myNewPos;
/*  954 */       int oldPos = this.pos;
/*  955 */       this.pos = myNewPos;
/*  956 */       this.hasSplit = true;
/*  957 */       return new Spliterator(oldPos, retMax, true);
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
/*      */   public ObjectSpliterator<K> spliterator() {
/*  975 */     return new Spliterator();
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public void sort(Comparator<? super K> comp) {
/*  981 */     if (comp == null) {
/*  982 */       ObjectArrays.stableSort(this.a, 0, this.size);
/*      */     } else {
/*  984 */       ObjectArrays.stableSort(this.a, 0, this.size, (Comparator)comp);
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   public void unstableSort(Comparator<? super K> comp) {
/*  990 */     if (comp == null) {
/*  991 */       ObjectArrays.unstableSort(this.a, 0, this.size);
/*      */     } else {
/*  993 */       ObjectArrays.unstableSort(this.a, 0, this.size, (Comparator)comp);
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public ReferenceArrayList<K> clone() {
/* 1000 */     ReferenceArrayList<K> cloned = null;
/*      */     
/* 1002 */     if (getClass() == ReferenceArrayList.class) {
/*      */ 
/*      */       
/* 1005 */       cloned = new ReferenceArrayList(copyArraySafe(this.a, this.size), false);
/* 1006 */       cloned.size = this.size;
/*      */     } else {
/*      */       try {
/* 1009 */         cloned = (ReferenceArrayList<K>)super.clone();
/* 1010 */       } catch (CloneNotSupportedException err) {
/*      */         
/* 1012 */         throw new InternalError(err);
/*      */       } 
/*      */ 
/*      */       
/* 1016 */       cloned.a = copyArraySafe(this.a, this.size);
/*      */     } 
/*      */     
/* 1019 */     return cloned;
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
/*      */   public boolean equals(ReferenceArrayList<K> l) {
/* 1034 */     if (l == this) return true; 
/* 1035 */     int s = size();
/* 1036 */     if (s != l.size()) return false; 
/* 1037 */     K[] a1 = this.a;
/* 1038 */     K[] a2 = l.a;
/* 1039 */     if (a1 == a2 && s == l.size()) return true; 
/* 1040 */     while (s-- != 0) { if (a1[s] != a2[s]) return false;  }
/* 1041 */      return true;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean equals(Object o) {
/* 1047 */     if (o == this) return true; 
/* 1048 */     if (o == null) return false; 
/* 1049 */     if (!(o instanceof List)) return false; 
/* 1050 */     if (o instanceof ReferenceArrayList)
/*      */     {
/* 1052 */       return equals((ReferenceArrayList<K>)o);
/*      */     }
/* 1054 */     if (o instanceof SubList)
/*      */     {
/*      */       
/* 1057 */       return ((SubList)o).equals(this);
/*      */     }
/* 1059 */     return super.equals(o);
/*      */   }
/*      */   
/*      */   private void writeObject(ObjectOutputStream s) throws IOException {
/* 1063 */     s.defaultWriteObject();
/* 1064 */     for (int i = 0; i < this.size; ) { s.writeObject(this.a[i]); i++; }
/*      */   
/*      */   }
/*      */   
/*      */   private void readObject(ObjectInputStream s) throws IOException, ClassNotFoundException {
/* 1069 */     s.defaultReadObject();
/* 1070 */     this.a = (K[])new Object[this.size];
/* 1071 */     for (int i = 0; i < this.size; ) { this.a[i] = (K)s.readObject(); i++; }
/*      */   
/*      */   }
/*      */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\i\\unimi\dsi\fastutil\objects\ReferenceArrayList.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */