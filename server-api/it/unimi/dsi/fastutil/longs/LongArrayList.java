/*      */ package it.unimi.dsi.fastutil.longs;
/*      */ 
/*      */ import it.unimi.dsi.fastutil.Arrays;
/*      */ import it.unimi.dsi.fastutil.SafeMath;
/*      */ import java.io.IOException;
/*      */ import java.io.ObjectInputStream;
/*      */ import java.io.ObjectOutputStream;
/*      */ import java.io.Serializable;
/*      */ import java.util.Arrays;
/*      */ import java.util.Collection;
/*      */ import java.util.Iterator;
/*      */ import java.util.List;
/*      */ import java.util.ListIterator;
/*      */ import java.util.NoSuchElementException;
/*      */ import java.util.RandomAccess;
/*      */ import java.util.function.LongConsumer;
/*      */ import java.util.function.Supplier;
/*      */ import java.util.stream.LongStream;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ public class LongArrayList
/*      */   extends AbstractLongList
/*      */   implements RandomAccess, Cloneable, Serializable
/*      */ {
/*      */   private static final long serialVersionUID = -7046029254386353130L;
/*      */   public static final int DEFAULT_INITIAL_CAPACITY = 10;
/*      */   protected transient long[] a;
/*      */   protected int size;
/*      */   
/*      */   private static final long[] copyArraySafe(long[] a, int length) {
/*   62 */     if (length == 0) return LongArrays.EMPTY_ARRAY; 
/*   63 */     return Arrays.copyOf(a, length);
/*      */   }
/*      */   
/*      */   private static final long[] copyArrayFromSafe(LongArrayList l) {
/*   67 */     return copyArraySafe(l.a, l.size);
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
/*      */   protected LongArrayList(long[] a, boolean wrapped) {
/*   79 */     this.a = a;
/*      */   }
/*      */   
/*      */   private void initArrayFromCapacity(int capacity) {
/*   83 */     if (capacity < 0) throw new IllegalArgumentException("Initial capacity (" + capacity + ") is negative"); 
/*   84 */     if (capacity == 0) { this.a = LongArrays.EMPTY_ARRAY; }
/*   85 */     else { this.a = new long[capacity]; }
/*      */   
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public LongArrayList(int capacity) {
/*   94 */     initArrayFromCapacity(capacity);
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public LongArrayList() {
/*  100 */     this.a = LongArrays.DEFAULT_EMPTY_ARRAY;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public LongArrayList(Collection<? extends Long> c) {
/*  109 */     if (c instanceof LongArrayList) {
/*  110 */       this.a = copyArrayFromSafe((LongArrayList)c);
/*  111 */       this.size = this.a.length;
/*      */     } else {
/*  113 */       initArrayFromCapacity(c.size());
/*  114 */       if (c instanceof LongList) {
/*  115 */         ((LongList)c).getElements(0, this.a, 0, this.size = c.size());
/*      */       } else {
/*  117 */         this.size = LongIterators.unwrap(LongIterators.asLongIterator(c.iterator()), this.a);
/*      */       } 
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public LongArrayList(LongCollection c) {
/*  128 */     if (c instanceof LongArrayList) {
/*  129 */       this.a = copyArrayFromSafe((LongArrayList)c);
/*  130 */       this.size = this.a.length;
/*      */     } else {
/*  132 */       initArrayFromCapacity(c.size());
/*  133 */       if (c instanceof LongList) {
/*  134 */         ((LongList)c).getElements(0, this.a, 0, this.size = c.size());
/*      */       } else {
/*  136 */         this.size = LongIterators.unwrap(c.iterator(), this.a);
/*      */       } 
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public LongArrayList(LongList l) {
/*  147 */     if (l instanceof LongArrayList) {
/*  148 */       this.a = copyArrayFromSafe((LongArrayList)l);
/*  149 */       this.size = this.a.length;
/*      */     } else {
/*  151 */       initArrayFromCapacity(l.size());
/*  152 */       l.getElements(0, this.a, 0, this.size = l.size());
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public LongArrayList(long[] a) {
/*  162 */     this(a, 0, a.length);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public LongArrayList(long[] a, int offset, int length) {
/*  173 */     this(length);
/*  174 */     System.arraycopy(a, offset, this.a, 0, length);
/*  175 */     this.size = length;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public LongArrayList(Iterator<? extends Long> i) {
/*  184 */     this();
/*  185 */     for (; i.hasNext(); add(((Long)i.next()).longValue()));
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public LongArrayList(LongIterator i) {
/*  194 */     this();
/*  195 */     for (; i.hasNext(); add(i.nextLong()));
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public long[] elements() {
/*  204 */     return this.a;
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
/*      */   public static LongArrayList wrap(long[] a, int length) {
/*  219 */     if (length > a.length) throw new IllegalArgumentException("The specified length (" + length + ") is greater than the array size (" + a.length + ")"); 
/*  220 */     LongArrayList l = new LongArrayList(a, true);
/*  221 */     l.size = length;
/*  222 */     return l;
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
/*      */   public static LongArrayList wrap(long[] a) {
/*  236 */     return wrap(a, a.length);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static LongArrayList of() {
/*  245 */     return new LongArrayList();
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
/*      */   public static LongArrayList of(long... init) {
/*  257 */     return wrap(init);
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
/*      */   public static LongArrayList toList(LongStream stream) {
/*  271 */     return stream.<LongArrayList>collect(LongArrayList::new, LongArrayList::add, LongList::addAll);
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
/*      */   public static LongArrayList toListWithExpectedSize(LongStream stream, int expectedSize) {
/*  286 */     if (expectedSize <= 10)
/*      */     {
/*      */       
/*  289 */       return toList(stream);
/*      */     }
/*  291 */     return stream.<LongArrayList>collect(new LongCollections.SizeDecreasingSupplier<>(expectedSize, size -> (size <= 10) ? new LongArrayList() : new LongArrayList(size)), LongArrayList::add, LongList::addAll);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void ensureCapacity(int capacity) {
/*  301 */     if (capacity <= this.a.length || (this.a == LongArrays.DEFAULT_EMPTY_ARRAY && capacity <= 10))
/*  302 */       return;  this.a = LongArrays.ensureCapacity(this.a, capacity, this.size);
/*  303 */     assert this.size <= this.a.length;
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
/*  314 */     if (capacity <= this.a.length)
/*  315 */       return;  if (this.a != LongArrays.DEFAULT_EMPTY_ARRAY) { capacity = (int)Math.max(Math.min(this.a.length + (this.a.length >> 1), 2147483639L), capacity); }
/*  316 */     else if (capacity < 10) { capacity = 10; }
/*  317 */      this.a = LongArrays.forceCapacity(this.a, capacity, this.size);
/*  318 */     assert this.size <= this.a.length;
/*      */   }
/*      */ 
/*      */   
/*      */   public void add(int index, long k) {
/*  323 */     ensureIndex(index);
/*  324 */     grow(this.size + 1);
/*  325 */     if (index != this.size) System.arraycopy(this.a, index, this.a, index + 1, this.size - index); 
/*  326 */     this.a[index] = k;
/*  327 */     this.size++;
/*  328 */     assert this.size <= this.a.length;
/*      */   }
/*      */ 
/*      */   
/*      */   public boolean add(long k) {
/*  333 */     grow(this.size + 1);
/*  334 */     this.a[this.size++] = k;
/*  335 */     assert this.size <= this.a.length;
/*  336 */     return true;
/*      */   }
/*      */ 
/*      */   
/*      */   public long getLong(int index) {
/*  341 */     if (index >= this.size) throw new IndexOutOfBoundsException("Index (" + index + ") is greater than or equal to list size (" + this.size + ")"); 
/*  342 */     return this.a[index];
/*      */   }
/*      */ 
/*      */   
/*      */   public int indexOf(long k) {
/*  347 */     for (int i = 0; i < this.size; ) { if (k == this.a[i]) return i;  i++; }
/*  348 */      return -1;
/*      */   }
/*      */ 
/*      */   
/*      */   public int lastIndexOf(long k) {
/*  353 */     for (int i = this.size; i-- != 0;) { if (k == this.a[i]) return i;  }
/*  354 */      return -1;
/*      */   }
/*      */ 
/*      */   
/*      */   public long removeLong(int index) {
/*  359 */     if (index >= this.size) throw new IndexOutOfBoundsException("Index (" + index + ") is greater than or equal to list size (" + this.size + ")"); 
/*  360 */     long old = this.a[index];
/*  361 */     this.size--;
/*  362 */     if (index != this.size) System.arraycopy(this.a, index + 1, this.a, index, this.size - index); 
/*  363 */     assert this.size <= this.a.length;
/*  364 */     return old;
/*      */   }
/*      */ 
/*      */   
/*      */   public boolean rem(long k) {
/*  369 */     int index = indexOf(k);
/*  370 */     if (index == -1) return false; 
/*  371 */     removeLong(index);
/*  372 */     assert this.size <= this.a.length;
/*  373 */     return true;
/*      */   }
/*      */ 
/*      */   
/*      */   public long set(int index, long k) {
/*  378 */     if (index >= this.size) throw new IndexOutOfBoundsException("Index (" + index + ") is greater than or equal to list size (" + this.size + ")"); 
/*  379 */     long old = this.a[index];
/*  380 */     this.a[index] = k;
/*  381 */     return old;
/*      */   }
/*      */ 
/*      */   
/*      */   public void clear() {
/*  386 */     this.size = 0;
/*  387 */     assert this.size <= this.a.length;
/*      */   }
/*      */ 
/*      */   
/*      */   public int size() {
/*  392 */     return this.size;
/*      */   }
/*      */ 
/*      */   
/*      */   public void size(int size) {
/*  397 */     if (size > this.a.length) this.a = LongArrays.forceCapacity(this.a, size, this.size); 
/*  398 */     if (size > this.size) Arrays.fill(this.a, this.size, size, 0L); 
/*  399 */     this.size = size;
/*      */   }
/*      */ 
/*      */   
/*      */   public boolean isEmpty() {
/*  404 */     return (this.size == 0);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void trim() {
/*  413 */     trim(0);
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
/*  432 */     if (n >= this.a.length || this.size == this.a.length)
/*  433 */       return;  long[] t = new long[Math.max(n, this.size)];
/*  434 */     System.arraycopy(this.a, 0, t, 0, this.size);
/*  435 */     this.a = t;
/*  436 */     assert this.size <= this.a.length;
/*      */   }
/*      */   
/*      */   private class SubList extends AbstractLongList.LongRandomAccessSubList {
/*      */     private static final long serialVersionUID = -3185226345314976296L;
/*      */     
/*      */     protected SubList(int from, int to) {
/*  443 */       super(from, to);
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     private long[] getParentArray() {
/*  450 */       return LongArrayList.this.a;
/*      */     }
/*      */ 
/*      */     
/*      */     public long getLong(int i) {
/*  455 */       ensureRestrictedIndex(i);
/*  456 */       return LongArrayList.this.a[i + this.from];
/*      */     }
/*      */     
/*      */     private final class SubListIterator
/*      */       extends LongIterators.AbstractIndexBasedListIterator
/*      */     {
/*      */       SubListIterator(int index) {
/*  463 */         super(0, index);
/*      */       }
/*      */ 
/*      */       
/*      */       protected final long get(int i) {
/*  468 */         return LongArrayList.this.a[LongArrayList.SubList.this.from + i];
/*      */       }
/*      */ 
/*      */       
/*      */       protected final void add(int i, long k) {
/*  473 */         LongArrayList.SubList.this.add(i, k);
/*      */       }
/*      */ 
/*      */       
/*      */       protected final void set(int i, long k) {
/*  478 */         LongArrayList.SubList.this.set(i, k);
/*      */       }
/*      */ 
/*      */       
/*      */       protected final void remove(int i) {
/*  483 */         LongArrayList.SubList.this.removeLong(i);
/*      */       }
/*      */ 
/*      */       
/*      */       protected final int getMaxPos() {
/*  488 */         return LongArrayList.SubList.this.to - LongArrayList.SubList.this.from;
/*      */       }
/*      */ 
/*      */       
/*      */       public long nextLong() {
/*  493 */         if (!hasNext()) throw new NoSuchElementException(); 
/*  494 */         return LongArrayList.this.a[LongArrayList.SubList.this.from + (this.lastReturned = this.pos++)];
/*      */       }
/*      */ 
/*      */       
/*      */       public long previousLong() {
/*  499 */         if (!hasPrevious()) throw new NoSuchElementException(); 
/*  500 */         return LongArrayList.this.a[LongArrayList.SubList.this.from + (this.lastReturned = --this.pos)];
/*      */       }
/*      */ 
/*      */       
/*      */       public void forEachRemaining(LongConsumer action) {
/*  505 */         int max = LongArrayList.SubList.this.to - LongArrayList.SubList.this.from;
/*  506 */         while (this.pos < max) {
/*  507 */           action.accept(LongArrayList.this.a[LongArrayList.SubList.this.from + (this.lastReturned = this.pos++)]);
/*      */         }
/*      */       }
/*      */     }
/*      */ 
/*      */     
/*      */     public LongListIterator listIterator(int index) {
/*  514 */       return new SubListIterator(index);
/*      */     }
/*      */     
/*      */     private final class SubListSpliterator
/*      */       extends LongSpliterators.LateBindingSizeIndexBasedSpliterator {
/*      */       SubListSpliterator() {
/*  520 */         super(LongArrayList.SubList.this.from);
/*      */       }
/*      */       
/*      */       private SubListSpliterator(int pos, int maxPos) {
/*  524 */         super(pos, maxPos);
/*      */       }
/*      */ 
/*      */       
/*      */       protected final int getMaxPosFromBackingStore() {
/*  529 */         return LongArrayList.SubList.this.to;
/*      */       }
/*      */ 
/*      */       
/*      */       protected final long get(int i) {
/*  534 */         return LongArrayList.this.a[i];
/*      */       }
/*      */ 
/*      */       
/*      */       protected final SubListSpliterator makeForSplit(int pos, int maxPos) {
/*  539 */         return new SubListSpliterator(pos, maxPos);
/*      */       }
/*      */ 
/*      */       
/*      */       public boolean tryAdvance(LongConsumer action) {
/*  544 */         if (this.pos >= getMaxPos()) return false; 
/*  545 */         action.accept(LongArrayList.this.a[this.pos++]);
/*  546 */         return true;
/*      */       }
/*      */ 
/*      */       
/*      */       public void forEachRemaining(LongConsumer action) {
/*  551 */         int max = getMaxPos();
/*  552 */         while (this.pos < max) {
/*  553 */           action.accept(LongArrayList.this.a[this.pos++]);
/*      */         }
/*      */       }
/*      */     }
/*      */ 
/*      */     
/*      */     public LongSpliterator spliterator() {
/*  560 */       return new SubListSpliterator();
/*      */     }
/*      */     
/*      */     boolean contentsEquals(long[] otherA, int otherAFrom, int otherATo) {
/*  564 */       if (LongArrayList.this.a == otherA && this.from == otherAFrom && this.to == otherATo) return true; 
/*  565 */       if (otherATo - otherAFrom != size()) {
/*  566 */         return false;
/*      */       }
/*  568 */       int pos = this.from, otherPos = otherAFrom;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/*  574 */       while (pos < this.to) { if (LongArrayList.this.a[pos++] != otherA[otherPos++]) return false;  }
/*  575 */        return true;
/*      */     }
/*      */ 
/*      */     
/*      */     public boolean equals(Object o) {
/*  580 */       if (o == this) return true; 
/*  581 */       if (o == null) return false; 
/*  582 */       if (!(o instanceof List)) return false; 
/*  583 */       if (o instanceof LongArrayList) {
/*      */         
/*  585 */         LongArrayList other = (LongArrayList)o;
/*  586 */         return contentsEquals(other.a, 0, other.size());
/*      */       } 
/*  588 */       if (o instanceof SubList) {
/*      */         
/*  590 */         SubList other = (SubList)o;
/*  591 */         return contentsEquals(other.getParentArray(), other.from, other.to);
/*      */       } 
/*  593 */       return super.equals(o);
/*      */     }
/*      */     
/*      */     int contentsCompareTo(long[] otherA, int otherAFrom, int otherATo) {
/*  597 */       if (LongArrayList.this.a == otherA && this.from == otherAFrom && this.to == otherATo) return 0;
/*      */       
/*      */       int i;
/*      */       int j;
/*  601 */       for (i = this.from, j = otherAFrom; i < this.to && i < otherATo; i++, j++) {
/*  602 */         long e1 = LongArrayList.this.a[i];
/*  603 */         long e2 = otherA[j]; int r;
/*  604 */         if ((r = Long.compare(e1, e2)) != 0) return r; 
/*      */       } 
/*  606 */       return (i < otherATo) ? -1 : ((i < this.to) ? 1 : 0);
/*      */     }
/*      */ 
/*      */     
/*      */     public int compareTo(List<? extends Long> l) {
/*  611 */       if (l instanceof LongArrayList) {
/*      */         
/*  613 */         LongArrayList other = (LongArrayList)l;
/*  614 */         return contentsCompareTo(other.a, 0, other.size());
/*      */       } 
/*  616 */       if (l instanceof SubList) {
/*      */         
/*  618 */         SubList other = (SubList)l;
/*  619 */         return contentsCompareTo(other.getParentArray(), other.from, other.to);
/*      */       } 
/*  621 */       return super.compareTo(l);
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public LongList subList(int from, int to) {
/*  631 */     if (from == 0 && to == size()) return this; 
/*  632 */     ensureIndex(from);
/*  633 */     ensureIndex(to);
/*  634 */     if (from > to) throw new IndexOutOfBoundsException("Start index (" + from + ") is greater than end index (" + to + ")"); 
/*  635 */     return new SubList(from, to);
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
/*      */   public void getElements(int from, long[] a, int offset, int length) {
/*  648 */     LongArrays.ensureOffsetLength(a, offset, length);
/*  649 */     System.arraycopy(this.a, from, a, offset, length);
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
/*  660 */     Arrays.ensureFromTo(this.size, from, to);
/*  661 */     System.arraycopy(this.a, to, this.a, from, this.size - to);
/*  662 */     this.size -= to - from;
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
/*      */   public void addElements(int index, long[] a, int offset, int length) {
/*  675 */     ensureIndex(index);
/*  676 */     LongArrays.ensureOffsetLength(a, offset, length);
/*  677 */     grow(this.size + length);
/*  678 */     System.arraycopy(this.a, index, this.a, index + length, this.size - index);
/*  679 */     System.arraycopy(a, offset, this.a, index, length);
/*  680 */     this.size += length;
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
/*      */   public void setElements(int index, long[] a, int offset, int length) {
/*  693 */     ensureIndex(index);
/*  694 */     LongArrays.ensureOffsetLength(a, offset, length);
/*  695 */     if (index + length > this.size) throw new IndexOutOfBoundsException("End index (" + (index + length) + ") is greater than list size (" + this.size + ")"); 
/*  696 */     System.arraycopy(a, offset, this.a, index, length);
/*      */   }
/*      */ 
/*      */   
/*      */   public void forEach(LongConsumer action) {
/*  701 */     for (int i = 0; i < this.size; i++) {
/*  702 */       action.accept(this.a[i]);
/*      */     }
/*      */   }
/*      */ 
/*      */   
/*      */   public boolean addAll(int index, LongCollection c) {
/*  708 */     if (c instanceof LongList) {
/*  709 */       return addAll(index, (LongList)c);
/*      */     }
/*  711 */     ensureIndex(index);
/*  712 */     int n = c.size();
/*  713 */     if (n == 0) return false; 
/*  714 */     grow(this.size + n);
/*  715 */     System.arraycopy(this.a, index, this.a, index + n, this.size - index);
/*  716 */     LongIterator i = c.iterator();
/*  717 */     this.size += n;
/*  718 */     for (; n-- != 0; this.a[index++] = i.nextLong());
/*  719 */     assert this.size <= this.a.length;
/*  720 */     return true;
/*      */   }
/*      */ 
/*      */   
/*      */   public boolean addAll(int index, LongList l) {
/*  725 */     ensureIndex(index);
/*  726 */     int n = l.size();
/*  727 */     if (n == 0) return false; 
/*  728 */     grow(this.size + n);
/*  729 */     System.arraycopy(this.a, index, this.a, index + n, this.size - index);
/*  730 */     l.getElements(0, this.a, index, n);
/*  731 */     this.size += n;
/*  732 */     assert this.size <= this.a.length;
/*  733 */     return true;
/*      */   }
/*      */ 
/*      */   
/*      */   public boolean removeAll(LongCollection c) {
/*  738 */     long[] a = this.a;
/*  739 */     int j = 0;
/*  740 */     for (int i = 0; i < this.size; ) { if (!c.contains(a[i])) a[j++] = a[i];  i++; }
/*  741 */      boolean modified = (this.size != j);
/*  742 */     this.size = j;
/*  743 */     return modified;
/*      */   }
/*      */ 
/*      */   
/*      */   public long[] toArray(long[] a) {
/*  748 */     if (a == null || a.length < this.size) a = Arrays.copyOf(a, this.size); 
/*  749 */     System.arraycopy(this.a, 0, a, 0, this.size);
/*  750 */     return a;
/*      */   }
/*      */ 
/*      */   
/*      */   public LongListIterator listIterator(final int index) {
/*  755 */     ensureIndex(index);
/*  756 */     return new LongListIterator() {
/*  757 */         int pos = index; int last = -1;
/*      */ 
/*      */         
/*      */         public boolean hasNext() {
/*  761 */           return (this.pos < LongArrayList.this.size);
/*      */         }
/*      */ 
/*      */         
/*      */         public boolean hasPrevious() {
/*  766 */           return (this.pos > 0);
/*      */         }
/*      */ 
/*      */         
/*      */         public long nextLong() {
/*  771 */           if (!hasNext()) throw new NoSuchElementException(); 
/*  772 */           return LongArrayList.this.a[this.last = this.pos++];
/*      */         }
/*      */ 
/*      */         
/*      */         public long previousLong() {
/*  777 */           if (!hasPrevious()) throw new NoSuchElementException(); 
/*  778 */           return LongArrayList.this.a[this.last = --this.pos];
/*      */         }
/*      */ 
/*      */         
/*      */         public int nextIndex() {
/*  783 */           return this.pos;
/*      */         }
/*      */ 
/*      */         
/*      */         public int previousIndex() {
/*  788 */           return this.pos - 1;
/*      */         }
/*      */ 
/*      */         
/*      */         public void add(long k) {
/*  793 */           LongArrayList.this.add(this.pos++, k);
/*  794 */           this.last = -1;
/*      */         }
/*      */ 
/*      */         
/*      */         public void set(long k) {
/*  799 */           if (this.last == -1) throw new IllegalStateException(); 
/*  800 */           LongArrayList.this.set(this.last, k);
/*      */         }
/*      */ 
/*      */         
/*      */         public void remove() {
/*  805 */           if (this.last == -1) throw new IllegalStateException(); 
/*  806 */           LongArrayList.this.removeLong(this.last);
/*      */           
/*  808 */           if (this.last < this.pos) this.pos--; 
/*  809 */           this.last = -1;
/*      */         }
/*      */ 
/*      */         
/*      */         public void forEachRemaining(LongConsumer action) {
/*  814 */           while (this.pos < LongArrayList.this.size) {
/*  815 */             action.accept(LongArrayList.this.a[this.last = this.pos++]);
/*      */           }
/*      */         }
/*      */ 
/*      */         
/*      */         public int back(int n) {
/*  821 */           if (n < 0) throw new IllegalArgumentException("Argument must be nonnegative: " + n); 
/*  822 */           int remaining = LongArrayList.this.size - this.pos;
/*  823 */           if (n < remaining) {
/*  824 */             this.pos -= n;
/*      */           } else {
/*  826 */             n = remaining;
/*  827 */             this.pos = 0;
/*      */           } 
/*  829 */           this.last = this.pos;
/*  830 */           return n;
/*      */         }
/*      */ 
/*      */         
/*      */         public int skip(int n) {
/*  835 */           if (n < 0) throw new IllegalArgumentException("Argument must be nonnegative: " + n); 
/*  836 */           int remaining = LongArrayList.this.size - this.pos;
/*  837 */           if (n < remaining) {
/*  838 */             this.pos += n;
/*      */           } else {
/*  840 */             n = remaining;
/*  841 */             this.pos = LongArrayList.this.size;
/*      */           } 
/*  843 */           this.last = this.pos - 1;
/*  844 */           return n;
/*      */         }
/*      */       };
/*      */   }
/*      */ 
/*      */   
/*      */   private final class Spliterator
/*      */     implements LongSpliterator
/*      */   {
/*      */     boolean hasSplit = false;
/*      */     int pos;
/*      */     int max;
/*      */     
/*      */     public Spliterator() {
/*  858 */       this(0, LongArrayList.this.size, false);
/*      */     }
/*      */     
/*      */     private Spliterator(int pos, int max, boolean hasSplit) {
/*  862 */       assert pos <= max : "pos " + pos + " must be <= max " + max;
/*  863 */       this.pos = pos;
/*  864 */       this.max = max;
/*  865 */       this.hasSplit = hasSplit;
/*      */     }
/*      */     
/*      */     private int getWorkingMax() {
/*  869 */       return this.hasSplit ? this.max : LongArrayList.this.size;
/*      */     }
/*      */ 
/*      */     
/*      */     public int characteristics() {
/*  874 */       return 16720;
/*      */     }
/*      */ 
/*      */     
/*      */     public long estimateSize() {
/*  879 */       return (getWorkingMax() - this.pos);
/*      */     }
/*      */ 
/*      */     
/*      */     public boolean tryAdvance(LongConsumer action) {
/*  884 */       if (this.pos >= getWorkingMax()) return false; 
/*  885 */       action.accept(LongArrayList.this.a[this.pos++]);
/*  886 */       return true;
/*      */     }
/*      */ 
/*      */     
/*      */     public void forEachRemaining(LongConsumer action) {
/*  891 */       for (int max = getWorkingMax(); this.pos < max; this.pos++) {
/*  892 */         action.accept(LongArrayList.this.a[this.pos]);
/*      */       }
/*      */     }
/*      */ 
/*      */     
/*      */     public long skip(long n) {
/*  898 */       if (n < 0L) throw new IllegalArgumentException("Argument must be nonnegative: " + n); 
/*  899 */       int max = getWorkingMax();
/*  900 */       if (this.pos >= max) return 0L; 
/*  901 */       int remaining = max - this.pos;
/*  902 */       if (n < remaining) {
/*  903 */         this.pos = SafeMath.safeLongToInt(this.pos + n);
/*  904 */         return n;
/*      */       } 
/*  906 */       n = remaining;
/*  907 */       this.pos = max;
/*  908 */       return n;
/*      */     }
/*      */ 
/*      */     
/*      */     public LongSpliterator trySplit() {
/*  913 */       int max = getWorkingMax();
/*  914 */       int retLen = max - this.pos >> 1;
/*  915 */       if (retLen <= 1) return null;
/*      */       
/*  917 */       this.max = max;
/*  918 */       int myNewPos = this.pos + retLen;
/*  919 */       int retMax = myNewPos;
/*  920 */       int oldPos = this.pos;
/*  921 */       this.pos = myNewPos;
/*  922 */       this.hasSplit = true;
/*  923 */       return new Spliterator(oldPos, retMax, true);
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
/*      */   public LongSpliterator spliterator() {
/*  941 */     return new Spliterator();
/*      */   }
/*      */ 
/*      */   
/*      */   public void sort(LongComparator comp) {
/*  946 */     if (comp == null) {
/*  947 */       LongArrays.stableSort(this.a, 0, this.size);
/*      */     } else {
/*  949 */       LongArrays.stableSort(this.a, 0, this.size, comp);
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   public void unstableSort(LongComparator comp) {
/*  955 */     if (comp == null) {
/*  956 */       LongArrays.unstableSort(this.a, 0, this.size);
/*      */     } else {
/*  958 */       LongArrays.unstableSort(this.a, 0, this.size, comp);
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public LongArrayList clone() {
/*  965 */     LongArrayList cloned = null;
/*      */     
/*  967 */     if (getClass() == LongArrayList.class) {
/*      */ 
/*      */       
/*  970 */       cloned = new LongArrayList(copyArraySafe(this.a, this.size), false);
/*  971 */       cloned.size = this.size;
/*      */     } else {
/*      */       try {
/*  974 */         cloned = (LongArrayList)super.clone();
/*  975 */       } catch (CloneNotSupportedException err) {
/*      */         
/*  977 */         throw new InternalError(err);
/*      */       } 
/*      */ 
/*      */       
/*  981 */       cloned.a = copyArraySafe(this.a, this.size);
/*      */     } 
/*  983 */     return cloned;
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
/*      */   public boolean equals(LongArrayList l) {
/*  998 */     if (l == this) return true; 
/*  999 */     int s = size();
/* 1000 */     if (s != l.size()) return false; 
/* 1001 */     long[] a1 = this.a;
/* 1002 */     long[] a2 = l.a;
/* 1003 */     if (a1 == a2 && s == l.size()) return true; 
/* 1004 */     while (s-- != 0) { if (a1[s] != a2[s]) return false;  }
/* 1005 */      return true;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean equals(Object o) {
/* 1011 */     if (o == this) return true; 
/* 1012 */     if (o == null) return false; 
/* 1013 */     if (!(o instanceof List)) return false; 
/* 1014 */     if (o instanceof LongArrayList)
/*      */     {
/* 1016 */       return equals((LongArrayList)o);
/*      */     }
/* 1018 */     if (o instanceof SubList)
/*      */     {
/*      */       
/* 1021 */       return ((SubList)o).equals(this);
/*      */     }
/* 1023 */     return super.equals(o);
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
/*      */   public int compareTo(LongArrayList l) {
/* 1038 */     int s1 = size(), s2 = l.size();
/* 1039 */     long[] a1 = this.a, a2 = l.a;
/* 1040 */     if (a1 == a2 && s1 == s2) return 0;
/*      */ 
/*      */     
/*      */     int i;
/* 1044 */     for (i = 0; i < s1 && i < s2; i++) {
/* 1045 */       long e1 = a1[i];
/* 1046 */       long e2 = a2[i]; int r;
/* 1047 */       if ((r = Long.compare(e1, e2)) != 0) return r; 
/*      */     } 
/* 1049 */     return (i < s2) ? -1 : ((i < s1) ? 1 : 0);
/*      */   }
/*      */ 
/*      */   
/*      */   public int compareTo(List<? extends Long> l) {
/* 1054 */     if (l instanceof LongArrayList) {
/* 1055 */       return compareTo((LongArrayList)l);
/*      */     }
/* 1057 */     if (l instanceof SubList)
/*      */     {
/* 1059 */       return -((SubList)l).compareTo(this);
/*      */     }
/* 1061 */     return super.compareTo(l);
/*      */   }
/*      */   
/*      */   private void writeObject(ObjectOutputStream s) throws IOException {
/* 1065 */     s.defaultWriteObject();
/* 1066 */     for (int i = 0; i < this.size; ) { s.writeLong(this.a[i]); i++; }
/*      */   
/*      */   }
/*      */   private void readObject(ObjectInputStream s) throws IOException, ClassNotFoundException {
/* 1070 */     s.defaultReadObject();
/* 1071 */     this.a = new long[this.size];
/* 1072 */     for (int i = 0; i < this.size; ) { this.a[i] = s.readLong(); i++; }
/*      */   
/*      */   }
/*      */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\i\\unimi\dsi\fastutil\longs\LongArrayList.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */