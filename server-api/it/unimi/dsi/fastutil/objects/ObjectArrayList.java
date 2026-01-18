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
/*      */ public class ObjectArrayList<K>
/*      */   extends AbstractObjectList<K>
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
/*      */   private static final <K> K[] copyArrayFromSafe(ObjectArrayList<K> l) {
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
/*      */   protected ObjectArrayList(K[] a, boolean wrapped) {
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
/*      */   public ObjectArrayList(int capacity) {
/*  114 */     initArrayFromCapacity(capacity);
/*  115 */     this.wrapped = false;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public ObjectArrayList() {
/*  121 */     this.a = (K[])ObjectArrays.DEFAULT_EMPTY_ARRAY;
/*  122 */     this.wrapped = false;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public ObjectArrayList(Collection<? extends K> c) {
/*  131 */     if (c instanceof ObjectArrayList) {
/*  132 */       this.a = copyArrayFromSafe((ObjectArrayList)c);
/*  133 */       this.size = this.a.length;
/*      */     } else {
/*  135 */       initArrayFromCapacity(c.size());
/*  136 */       if (c instanceof ObjectList) {
/*  137 */         ((ObjectList)c).getElements(0, (Object[])this.a, 0, this.size = c.size());
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
/*      */   public ObjectArrayList(ObjectCollection<? extends K> c) {
/*  151 */     if (c instanceof ObjectArrayList) {
/*  152 */       this.a = copyArrayFromSafe((ObjectArrayList)c);
/*  153 */       this.size = this.a.length;
/*      */     } else {
/*  155 */       initArrayFromCapacity(c.size());
/*  156 */       if (c instanceof ObjectList) {
/*  157 */         ((ObjectList)c).getElements(0, (Object[])this.a, 0, this.size = c.size());
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
/*      */   public ObjectArrayList(ObjectList<? extends K> l) {
/*  171 */     if (l instanceof ObjectArrayList) {
/*  172 */       this.a = copyArrayFromSafe((ObjectArrayList)l);
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
/*      */   public ObjectArrayList(K[] a) {
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
/*      */   public ObjectArrayList(K[] a, int offset, int length) {
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
/*      */   public ObjectArrayList(Iterator<? extends K> i) {
/*  209 */     this();
/*  210 */     for (; i.hasNext(); add(i.next()));
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public ObjectArrayList(ObjectIterator<? extends K> i) {
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
/*      */   public static <K> ObjectArrayList<K> wrap(K[] a, int length) {
/*  254 */     if (length > a.length) throw new IllegalArgumentException("The specified length (" + length + ") is greater than the array size (" + a.length + ")"); 
/*  255 */     ObjectArrayList<K> l = new ObjectArrayList<>(a, true);
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
/*      */   public static <K> ObjectArrayList<K> wrap(K[] a) {
/*  271 */     return wrap(a, a.length);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static <K> ObjectArrayList<K> of() {
/*  280 */     return new ObjectArrayList<>();
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
/*      */   public static <K> ObjectArrayList<K> of(K... init) {
/*  292 */     return wrap(init);
/*      */   }
/*      */ 
/*      */   
/*      */   ObjectArrayList<K> combine(ObjectArrayList<? extends K> toAddFrom) {
/*  297 */     addAll(toAddFrom);
/*  298 */     return this;
/*      */   }
/*      */   
/*  301 */   private static final Collector<Object, ?, ObjectArrayList<Object>> TO_LIST_COLLECTOR = (Collector)Collector.of(ObjectArrayList::new, ObjectArrayList::add, ObjectArrayList::combine, new Collector.Characteristics[0]);
/*      */ 
/*      */ 
/*      */   
/*      */   public static <K> Collector<K, ?, ObjectArrayList<K>> toList() {
/*  306 */     return (Collector)TO_LIST_COLLECTOR;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static <K> Collector<K, ?, ObjectArrayList<K>> toListWithExpectedSize(int expectedSize) {
/*  314 */     if (expectedSize <= 10)
/*      */     {
/*      */       
/*  317 */       return toList();
/*      */     }
/*  319 */     return (Collector)Collector.of(new ObjectCollections.SizeDecreasingSupplier<>(expectedSize, size -> (size <= 10) ? new ObjectArrayList() : new ObjectArrayList(size)), ObjectArrayList::add, ObjectArrayList::combine, new Collector.Characteristics[0]);
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
/*  387 */     for (int i = 0; i < this.size; ) { if (Objects.equals(k, this.a[i])) return i;  i++; }
/*  388 */      return -1;
/*      */   }
/*      */ 
/*      */   
/*      */   public int lastIndexOf(Object k) {
/*  393 */     for (int i = this.size; i-- != 0;) { if (Objects.equals(k, this.a[i])) return i;  }
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
/*      */   private class SubList extends AbstractObjectList.ObjectRandomAccessSubList<K> {
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
/*  493 */       return ObjectArrayList.this.a;
/*      */     }
/*      */ 
/*      */     
/*      */     public K get(int i) {
/*  498 */       ensureRestrictedIndex(i);
/*  499 */       return ObjectArrayList.this.a[i + this.from];
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
/*  511 */         return ObjectArrayList.this.a[ObjectArrayList.SubList.this.from + i];
/*      */       }
/*      */ 
/*      */       
/*      */       protected final void add(int i, K k) {
/*  516 */         ObjectArrayList.SubList.this.add(i, k);
/*      */       }
/*      */ 
/*      */       
/*      */       protected final void set(int i, K k) {
/*  521 */         ObjectArrayList.SubList.this.set(i, k);
/*      */       }
/*      */ 
/*      */       
/*      */       protected final void remove(int i) {
/*  526 */         ObjectArrayList.SubList.this.remove(i);
/*      */       }
/*      */ 
/*      */       
/*      */       protected final int getMaxPos() {
/*  531 */         return ObjectArrayList.SubList.this.to - ObjectArrayList.SubList.this.from;
/*      */       }
/*      */ 
/*      */       
/*      */       public K next() {
/*  536 */         if (!hasNext()) throw new NoSuchElementException(); 
/*  537 */         return ObjectArrayList.this.a[ObjectArrayList.SubList.this.from + (this.lastReturned = this.pos++)];
/*      */       }
/*      */ 
/*      */       
/*      */       public K previous() {
/*  542 */         if (!hasPrevious()) throw new NoSuchElementException(); 
/*  543 */         return ObjectArrayList.this.a[ObjectArrayList.SubList.this.from + (this.lastReturned = --this.pos)];
/*      */       }
/*      */ 
/*      */       
/*      */       public void forEachRemaining(Consumer<? super K> action) {
/*  548 */         int max = ObjectArrayList.SubList.this.to - ObjectArrayList.SubList.this.from;
/*  549 */         while (this.pos < max) {
/*  550 */           action.accept(ObjectArrayList.this.a[ObjectArrayList.SubList.this.from + (this.lastReturned = this.pos++)]);
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
/*  563 */         super(ObjectArrayList.SubList.this.from);
/*      */       }
/*      */       
/*      */       private SubListSpliterator(int pos, int maxPos) {
/*  567 */         super(pos, maxPos);
/*      */       }
/*      */ 
/*      */       
/*      */       protected final int getMaxPosFromBackingStore() {
/*  572 */         return ObjectArrayList.SubList.this.to;
/*      */       }
/*      */ 
/*      */       
/*      */       protected final K get(int i) {
/*  577 */         return ObjectArrayList.this.a[i];
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
/*  588 */         action.accept(ObjectArrayList.this.a[this.pos++]);
/*  589 */         return true;
/*      */       }
/*      */ 
/*      */       
/*      */       public void forEachRemaining(Consumer<? super K> action) {
/*  594 */         int max = getMaxPos();
/*  595 */         while (this.pos < max) {
/*  596 */           action.accept(ObjectArrayList.this.a[this.pos++]);
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
/*  607 */       if (ObjectArrayList.this.a == otherA && this.from == otherAFrom && this.to == otherATo) return true; 
/*  608 */       if (otherATo - otherAFrom != size()) {
/*  609 */         return false;
/*      */       }
/*  611 */       int pos = this.from, otherPos = otherAFrom;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/*  617 */       while (pos < this.to) { if (!Objects.equals(ObjectArrayList.this.a[pos++], otherA[otherPos++])) return false;  }
/*  618 */        return true;
/*      */     }
/*      */ 
/*      */     
/*      */     public boolean equals(Object o) {
/*  623 */       if (o == this) return true; 
/*  624 */       if (o == null) return false; 
/*  625 */       if (!(o instanceof List)) return false; 
/*  626 */       if (o instanceof ObjectArrayList) {
/*      */         
/*  628 */         ObjectArrayList<K> other = (ObjectArrayList<K>)o;
/*  629 */         return contentsEquals(other.a, 0, other.size());
/*      */       } 
/*  631 */       if (o instanceof SubList) {
/*      */         
/*  633 */         SubList other = (SubList)o;
/*  634 */         return contentsEquals(other.getParentArray(), other.from, other.to);
/*      */       } 
/*  636 */       return super.equals(o);
/*      */     }
/*      */ 
/*      */ 
/*      */     
/*      */     int contentsCompareTo(K[] otherA, int otherAFrom, int otherATo) {
/*      */       int i;
/*      */       int j;
/*  644 */       for (i = this.from, j = otherAFrom; i < this.to && i < otherATo; i++, j++) {
/*  645 */         K e1 = ObjectArrayList.this.a[i];
/*  646 */         K e2 = otherA[j]; int r;
/*  647 */         if ((r = ((Comparable<K>)e1).compareTo(e2)) != 0) return r; 
/*      */       } 
/*  649 */       return (i < otherATo) ? -1 : ((i < this.to) ? 1 : 0);
/*      */     }
/*      */ 
/*      */ 
/*      */     
/*      */     public int compareTo(List<? extends K> l) {
/*  655 */       if (l instanceof ObjectArrayList) {
/*      */         
/*  657 */         ObjectArrayList<K> other = (ObjectArrayList)l;
/*  658 */         return contentsCompareTo(other.a, 0, other.size());
/*      */       } 
/*  660 */       if (l instanceof SubList) {
/*      */         
/*  662 */         SubList other = (SubList)l;
/*  663 */         return contentsCompareTo(other.getParentArray(), other.from, other.to);
/*      */       } 
/*  665 */       return super.compareTo(l);
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public ObjectList<K> subList(int from, int to) {
/*  675 */     if (from == 0 && to == size()) return this; 
/*  676 */     ensureIndex(from);
/*  677 */     ensureIndex(to);
/*  678 */     if (from > to) throw new IndexOutOfBoundsException("Start index (" + from + ") is greater than end index (" + to + ")"); 
/*  679 */     return new SubList(from, to);
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
/*  692 */     ObjectArrays.ensureOffsetLength(a, offset, length);
/*  693 */     System.arraycopy(this.a, from, a, offset, length);
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
/*  704 */     Arrays.ensureFromTo(this.size, from, to);
/*  705 */     System.arraycopy(this.a, to, this.a, from, this.size - to);
/*  706 */     this.size -= to - from;
/*  707 */     int i = to - from;
/*  708 */     for (; i-- != 0; this.a[this.size + i] = null);
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
/*  721 */     ensureIndex(index);
/*  722 */     ObjectArrays.ensureOffsetLength(a, offset, length);
/*  723 */     grow(this.size + length);
/*  724 */     System.arraycopy(this.a, index, this.a, index + length, this.size - index);
/*  725 */     System.arraycopy(a, offset, this.a, index, length);
/*  726 */     this.size += length;
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
/*  739 */     ensureIndex(index);
/*  740 */     ObjectArrays.ensureOffsetLength(a, offset, length);
/*  741 */     if (index + length > this.size) throw new IndexOutOfBoundsException("End index (" + (index + length) + ") is greater than list size (" + this.size + ")"); 
/*  742 */     System.arraycopy(a, offset, this.a, index, length);
/*      */   }
/*      */ 
/*      */   
/*      */   public void forEach(Consumer<? super K> action) {
/*  747 */     for (int i = 0; i < this.size; i++) {
/*  748 */       action.accept(this.a[i]);
/*      */     }
/*      */   }
/*      */ 
/*      */   
/*      */   public boolean addAll(int index, Collection<? extends K> c) {
/*  754 */     if (c instanceof ObjectList) {
/*  755 */       return addAll(index, (ObjectList<? extends K>)c);
/*      */     }
/*  757 */     ensureIndex(index);
/*  758 */     int n = c.size();
/*  759 */     if (n == 0) return false; 
/*  760 */     grow(this.size + n);
/*  761 */     System.arraycopy(this.a, index, this.a, index + n, this.size - index);
/*  762 */     Iterator<? extends K> i = c.iterator();
/*  763 */     this.size += n;
/*  764 */     for (; n-- != 0; this.a[index++] = i.next());
/*  765 */     assert this.size <= this.a.length;
/*  766 */     return true;
/*      */   }
/*      */ 
/*      */   
/*      */   public boolean addAll(int index, ObjectList<? extends K> l) {
/*  771 */     ensureIndex(index);
/*  772 */     int n = l.size();
/*  773 */     if (n == 0) return false; 
/*  774 */     grow(this.size + n);
/*  775 */     System.arraycopy(this.a, index, this.a, index + n, this.size - index);
/*  776 */     l.getElements(0, (Object[])this.a, index, n);
/*  777 */     this.size += n;
/*  778 */     assert this.size <= this.a.length;
/*  779 */     return true;
/*      */   }
/*      */ 
/*      */   
/*      */   public boolean removeAll(Collection<?> c) {
/*  784 */     K[] arrayOfK = this.a;
/*  785 */     int j = 0;
/*  786 */     for (int i = 0; i < this.size; ) { if (!c.contains(arrayOfK[i])) arrayOfK[j++] = arrayOfK[i];  i++; }
/*  787 */      Arrays.fill((Object[])arrayOfK, j, this.size, (Object)null);
/*  788 */     boolean modified = (this.size != j);
/*  789 */     this.size = j;
/*  790 */     return modified;
/*      */   }
/*      */ 
/*      */   
/*      */   public Object[] toArray() {
/*  795 */     int size = size();
/*      */     
/*  797 */     if (size == 0) return ObjectArrays.EMPTY_ARRAY; 
/*  798 */     return Arrays.copyOf(this.a, size, Object[].class);
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public <T> T[] toArray(T[] a) {
/*  804 */     if (a == null) {
/*  805 */       a = (T[])new Object[size()];
/*  806 */     } else if (a.length < size()) {
/*  807 */       a = (T[])Array.newInstance(a.getClass().getComponentType(), size());
/*      */     } 
/*  809 */     System.arraycopy(this.a, 0, a, 0, size());
/*  810 */     if (a.length > size()) {
/*  811 */       a[size()] = null;
/*      */     }
/*  813 */     return a;
/*      */   }
/*      */ 
/*      */   
/*      */   public ObjectListIterator<K> listIterator(final int index) {
/*  818 */     ensureIndex(index);
/*  819 */     return new ObjectListIterator<K>() {
/*  820 */         int pos = index; int last = -1;
/*      */ 
/*      */         
/*      */         public boolean hasNext() {
/*  824 */           return (this.pos < ObjectArrayList.this.size);
/*      */         }
/*      */ 
/*      */         
/*      */         public boolean hasPrevious() {
/*  829 */           return (this.pos > 0);
/*      */         }
/*      */ 
/*      */         
/*      */         public K next() {
/*  834 */           if (!hasNext()) throw new NoSuchElementException(); 
/*  835 */           return ObjectArrayList.this.a[this.last = this.pos++];
/*      */         }
/*      */ 
/*      */         
/*      */         public K previous() {
/*  840 */           if (!hasPrevious()) throw new NoSuchElementException(); 
/*  841 */           return ObjectArrayList.this.a[this.last = --this.pos];
/*      */         }
/*      */ 
/*      */         
/*      */         public int nextIndex() {
/*  846 */           return this.pos;
/*      */         }
/*      */ 
/*      */         
/*      */         public int previousIndex() {
/*  851 */           return this.pos - 1;
/*      */         }
/*      */ 
/*      */         
/*      */         public void add(K k) {
/*  856 */           ObjectArrayList.this.add(this.pos++, k);
/*  857 */           this.last = -1;
/*      */         }
/*      */ 
/*      */         
/*      */         public void set(K k) {
/*  862 */           if (this.last == -1) throw new IllegalStateException(); 
/*  863 */           ObjectArrayList.this.set(this.last, k);
/*      */         }
/*      */ 
/*      */         
/*      */         public void remove() {
/*  868 */           if (this.last == -1) throw new IllegalStateException(); 
/*  869 */           ObjectArrayList.this.remove(this.last);
/*      */           
/*  871 */           if (this.last < this.pos) this.pos--; 
/*  872 */           this.last = -1;
/*      */         }
/*      */ 
/*      */         
/*      */         public void forEachRemaining(Consumer<? super K> action) {
/*  877 */           while (this.pos < ObjectArrayList.this.size) {
/*  878 */             action.accept(ObjectArrayList.this.a[this.last = this.pos++]);
/*      */           }
/*      */         }
/*      */ 
/*      */         
/*      */         public int back(int n) {
/*  884 */           if (n < 0) throw new IllegalArgumentException("Argument must be nonnegative: " + n); 
/*  885 */           int remaining = ObjectArrayList.this.size - this.pos;
/*  886 */           if (n < remaining) {
/*  887 */             this.pos -= n;
/*      */           } else {
/*  889 */             n = remaining;
/*  890 */             this.pos = 0;
/*      */           } 
/*  892 */           this.last = this.pos;
/*  893 */           return n;
/*      */         }
/*      */ 
/*      */         
/*      */         public int skip(int n) {
/*  898 */           if (n < 0) throw new IllegalArgumentException("Argument must be nonnegative: " + n); 
/*  899 */           int remaining = ObjectArrayList.this.size - this.pos;
/*  900 */           if (n < remaining) {
/*  901 */             this.pos += n;
/*      */           } else {
/*  903 */             n = remaining;
/*  904 */             this.pos = ObjectArrayList.this.size;
/*      */           } 
/*  906 */           this.last = this.pos - 1;
/*  907 */           return n;
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
/*  921 */       this(0, ObjectArrayList.this.size, false);
/*      */     }
/*      */     
/*      */     private Spliterator(int pos, int max, boolean hasSplit) {
/*  925 */       assert pos <= max : "pos " + pos + " must be <= max " + max;
/*  926 */       this.pos = pos;
/*  927 */       this.max = max;
/*  928 */       this.hasSplit = hasSplit;
/*      */     }
/*      */     
/*      */     private int getWorkingMax() {
/*  932 */       return this.hasSplit ? this.max : ObjectArrayList.this.size;
/*      */     }
/*      */ 
/*      */     
/*      */     public int characteristics() {
/*  937 */       return 16464;
/*      */     }
/*      */ 
/*      */     
/*      */     public long estimateSize() {
/*  942 */       return (getWorkingMax() - this.pos);
/*      */     }
/*      */ 
/*      */     
/*      */     public boolean tryAdvance(Consumer<? super K> action) {
/*  947 */       if (this.pos >= getWorkingMax()) return false; 
/*  948 */       action.accept(ObjectArrayList.this.a[this.pos++]);
/*  949 */       return true;
/*      */     }
/*      */ 
/*      */     
/*      */     public void forEachRemaining(Consumer<? super K> action) {
/*  954 */       for (int max = getWorkingMax(); this.pos < max; this.pos++) {
/*  955 */         action.accept(ObjectArrayList.this.a[this.pos]);
/*      */       }
/*      */     }
/*      */ 
/*      */     
/*      */     public long skip(long n) {
/*  961 */       if (n < 0L) throw new IllegalArgumentException("Argument must be nonnegative: " + n); 
/*  962 */       int max = getWorkingMax();
/*  963 */       if (this.pos >= max) return 0L; 
/*  964 */       int remaining = max - this.pos;
/*  965 */       if (n < remaining) {
/*  966 */         this.pos = SafeMath.safeLongToInt(this.pos + n);
/*  967 */         return n;
/*      */       } 
/*  969 */       n = remaining;
/*  970 */       this.pos = max;
/*  971 */       return n;
/*      */     }
/*      */ 
/*      */     
/*      */     public ObjectSpliterator<K> trySplit() {
/*  976 */       int max = getWorkingMax();
/*  977 */       int retLen = max - this.pos >> 1;
/*  978 */       if (retLen <= 1) return null;
/*      */       
/*  980 */       this.max = max;
/*  981 */       int myNewPos = this.pos + retLen;
/*  982 */       int retMax = myNewPos;
/*  983 */       int oldPos = this.pos;
/*  984 */       this.pos = myNewPos;
/*  985 */       this.hasSplit = true;
/*  986 */       return new Spliterator(oldPos, retMax, true);
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
/* 1004 */     return new Spliterator();
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public void sort(Comparator<? super K> comp) {
/* 1010 */     if (comp == null) {
/* 1011 */       ObjectArrays.stableSort(this.a, 0, this.size);
/*      */     } else {
/* 1013 */       ObjectArrays.stableSort(this.a, 0, this.size, (Comparator)comp);
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   public void unstableSort(Comparator<? super K> comp) {
/* 1019 */     if (comp == null) {
/* 1020 */       ObjectArrays.unstableSort(this.a, 0, this.size);
/*      */     } else {
/* 1022 */       ObjectArrays.unstableSort(this.a, 0, this.size, (Comparator)comp);
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public ObjectArrayList<K> clone() {
/* 1029 */     ObjectArrayList<K> cloned = null;
/*      */     
/* 1031 */     if (getClass() == ObjectArrayList.class) {
/*      */ 
/*      */       
/* 1034 */       cloned = new ObjectArrayList(copyArraySafe(this.a, this.size), false);
/* 1035 */       cloned.size = this.size;
/*      */     } else {
/*      */       try {
/* 1038 */         cloned = (ObjectArrayList<K>)super.clone();
/* 1039 */       } catch (CloneNotSupportedException err) {
/*      */         
/* 1041 */         throw new InternalError(err);
/*      */       } 
/*      */ 
/*      */       
/* 1045 */       cloned.a = copyArraySafe(this.a, this.size);
/*      */     } 
/*      */     
/* 1048 */     return cloned;
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
/*      */   public boolean equals(ObjectArrayList<K> l) {
/* 1063 */     if (l == this) return true; 
/* 1064 */     int s = size();
/* 1065 */     if (s != l.size()) return false; 
/* 1066 */     K[] a1 = this.a;
/* 1067 */     K[] a2 = l.a;
/* 1068 */     if (a1 == a2 && s == l.size()) return true; 
/* 1069 */     while (s-- != 0) { if (!Objects.equals(a1[s], a2[s])) return false;  }
/* 1070 */      return true;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean equals(Object o) {
/* 1076 */     if (o == this) return true; 
/* 1077 */     if (o == null) return false; 
/* 1078 */     if (!(o instanceof List)) return false; 
/* 1079 */     if (o instanceof ObjectArrayList)
/*      */     {
/* 1081 */       return equals((ObjectArrayList<K>)o);
/*      */     }
/* 1083 */     if (o instanceof SubList)
/*      */     {
/*      */       
/* 1086 */       return ((SubList)o).equals(this);
/*      */     }
/* 1088 */     return super.equals(o);
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
/*      */   public int compareTo(ObjectArrayList<? extends K> l) {
/* 1103 */     int s1 = size(), s2 = l.size();
/* 1104 */     K[] a1 = this.a, a2 = l.a;
/*      */     
/*      */     int i;
/*      */     
/* 1108 */     for (i = 0; i < s1 && i < s2; i++) {
/* 1109 */       K e1 = a1[i];
/* 1110 */       K e2 = a2[i]; int r;
/* 1111 */       if ((r = ((Comparable<K>)e1).compareTo(e2)) != 0) return r; 
/*      */     } 
/* 1113 */     return (i < s2) ? -1 : ((i < s1) ? 1 : 0);
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public int compareTo(List<? extends K> l) {
/* 1119 */     if (l instanceof ObjectArrayList) {
/* 1120 */       return compareTo((ObjectArrayList<? extends K>)l);
/*      */     }
/* 1122 */     if (l instanceof SubList)
/*      */     {
/* 1124 */       return -((SubList)l).compareTo(this);
/*      */     }
/* 1126 */     return super.compareTo(l);
/*      */   }
/*      */   
/*      */   private void writeObject(ObjectOutputStream s) throws IOException {
/* 1130 */     s.defaultWriteObject();
/* 1131 */     for (int i = 0; i < this.size; ) { s.writeObject(this.a[i]); i++; }
/*      */   
/*      */   }
/*      */   
/*      */   private void readObject(ObjectInputStream s) throws IOException, ClassNotFoundException {
/* 1136 */     s.defaultReadObject();
/* 1137 */     this.a = (K[])new Object[this.size];
/* 1138 */     for (int i = 0; i < this.size; ) { this.a[i] = (K)s.readObject(); i++; }
/*      */   
/*      */   }
/*      */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\i\\unimi\dsi\fastutil\objects\ObjectArrayList.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */