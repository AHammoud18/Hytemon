/*      */ package it.unimi.dsi.fastutil.shorts;
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
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ public class ShortArrayList
/*      */   extends AbstractShortList
/*      */   implements RandomAccess, Cloneable, Serializable
/*      */ {
/*      */   private static final long serialVersionUID = -7046029254386353130L;
/*      */   public static final int DEFAULT_INITIAL_CAPACITY = 10;
/*      */   protected transient short[] a;
/*      */   protected int size;
/*      */   
/*      */   private static final short[] copyArraySafe(short[] a, int length) {
/*   62 */     if (length == 0) return ShortArrays.EMPTY_ARRAY; 
/*   63 */     return Arrays.copyOf(a, length);
/*      */   }
/*      */   
/*      */   private static final short[] copyArrayFromSafe(ShortArrayList l) {
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
/*      */   protected ShortArrayList(short[] a, boolean wrapped) {
/*   79 */     this.a = a;
/*      */   }
/*      */   
/*      */   private void initArrayFromCapacity(int capacity) {
/*   83 */     if (capacity < 0) throw new IllegalArgumentException("Initial capacity (" + capacity + ") is negative"); 
/*   84 */     if (capacity == 0) { this.a = ShortArrays.EMPTY_ARRAY; }
/*   85 */     else { this.a = new short[capacity]; }
/*      */   
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public ShortArrayList(int capacity) {
/*   94 */     initArrayFromCapacity(capacity);
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public ShortArrayList() {
/*  100 */     this.a = ShortArrays.DEFAULT_EMPTY_ARRAY;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public ShortArrayList(Collection<? extends Short> c) {
/*  109 */     if (c instanceof ShortArrayList) {
/*  110 */       this.a = copyArrayFromSafe((ShortArrayList)c);
/*  111 */       this.size = this.a.length;
/*      */     } else {
/*  113 */       initArrayFromCapacity(c.size());
/*  114 */       if (c instanceof ShortList) {
/*  115 */         ((ShortList)c).getElements(0, this.a, 0, this.size = c.size());
/*      */       } else {
/*  117 */         this.size = ShortIterators.unwrap(ShortIterators.asShortIterator(c.iterator()), this.a);
/*      */       } 
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public ShortArrayList(ShortCollection c) {
/*  128 */     if (c instanceof ShortArrayList) {
/*  129 */       this.a = copyArrayFromSafe((ShortArrayList)c);
/*  130 */       this.size = this.a.length;
/*      */     } else {
/*  132 */       initArrayFromCapacity(c.size());
/*  133 */       if (c instanceof ShortList) {
/*  134 */         ((ShortList)c).getElements(0, this.a, 0, this.size = c.size());
/*      */       } else {
/*  136 */         this.size = ShortIterators.unwrap(c.iterator(), this.a);
/*      */       } 
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public ShortArrayList(ShortList l) {
/*  147 */     if (l instanceof ShortArrayList) {
/*  148 */       this.a = copyArrayFromSafe((ShortArrayList)l);
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
/*      */   public ShortArrayList(short[] a) {
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
/*      */   public ShortArrayList(short[] a, int offset, int length) {
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
/*      */   public ShortArrayList(Iterator<? extends Short> i) {
/*  184 */     this();
/*  185 */     for (; i.hasNext(); add(((Short)i.next()).shortValue()));
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public ShortArrayList(ShortIterator i) {
/*  194 */     this();
/*  195 */     for (; i.hasNext(); add(i.nextShort()));
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public short[] elements() {
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
/*      */   public static ShortArrayList wrap(short[] a, int length) {
/*  219 */     if (length > a.length) throw new IllegalArgumentException("The specified length (" + length + ") is greater than the array size (" + a.length + ")"); 
/*  220 */     ShortArrayList l = new ShortArrayList(a, true);
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
/*      */   public static ShortArrayList wrap(short[] a) {
/*  236 */     return wrap(a, a.length);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static ShortArrayList of() {
/*  245 */     return new ShortArrayList();
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
/*      */   public static ShortArrayList of(short... init) {
/*  257 */     return wrap(init);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void ensureCapacity(int capacity) {
/*  267 */     if (capacity <= this.a.length || (this.a == ShortArrays.DEFAULT_EMPTY_ARRAY && capacity <= 10))
/*  268 */       return;  this.a = ShortArrays.ensureCapacity(this.a, capacity, this.size);
/*  269 */     assert this.size <= this.a.length;
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
/*  280 */     if (capacity <= this.a.length)
/*  281 */       return;  if (this.a != ShortArrays.DEFAULT_EMPTY_ARRAY) { capacity = (int)Math.max(Math.min(this.a.length + (this.a.length >> 1), 2147483639L), capacity); }
/*  282 */     else if (capacity < 10) { capacity = 10; }
/*  283 */      this.a = ShortArrays.forceCapacity(this.a, capacity, this.size);
/*  284 */     assert this.size <= this.a.length;
/*      */   }
/*      */ 
/*      */   
/*      */   public void add(int index, short k) {
/*  289 */     ensureIndex(index);
/*  290 */     grow(this.size + 1);
/*  291 */     if (index != this.size) System.arraycopy(this.a, index, this.a, index + 1, this.size - index); 
/*  292 */     this.a[index] = k;
/*  293 */     this.size++;
/*  294 */     assert this.size <= this.a.length;
/*      */   }
/*      */ 
/*      */   
/*      */   public boolean add(short k) {
/*  299 */     grow(this.size + 1);
/*  300 */     this.a[this.size++] = k;
/*  301 */     assert this.size <= this.a.length;
/*  302 */     return true;
/*      */   }
/*      */ 
/*      */   
/*      */   public short getShort(int index) {
/*  307 */     if (index >= this.size) throw new IndexOutOfBoundsException("Index (" + index + ") is greater than or equal to list size (" + this.size + ")"); 
/*  308 */     return this.a[index];
/*      */   }
/*      */ 
/*      */   
/*      */   public int indexOf(short k) {
/*  313 */     for (int i = 0; i < this.size; ) { if (k == this.a[i]) return i;  i++; }
/*  314 */      return -1;
/*      */   }
/*      */ 
/*      */   
/*      */   public int lastIndexOf(short k) {
/*  319 */     for (int i = this.size; i-- != 0;) { if (k == this.a[i]) return i;  }
/*  320 */      return -1;
/*      */   }
/*      */ 
/*      */   
/*      */   public short removeShort(int index) {
/*  325 */     if (index >= this.size) throw new IndexOutOfBoundsException("Index (" + index + ") is greater than or equal to list size (" + this.size + ")"); 
/*  326 */     short old = this.a[index];
/*  327 */     this.size--;
/*  328 */     if (index != this.size) System.arraycopy(this.a, index + 1, this.a, index, this.size - index); 
/*  329 */     assert this.size <= this.a.length;
/*  330 */     return old;
/*      */   }
/*      */ 
/*      */   
/*      */   public boolean rem(short k) {
/*  335 */     int index = indexOf(k);
/*  336 */     if (index == -1) return false; 
/*  337 */     removeShort(index);
/*  338 */     assert this.size <= this.a.length;
/*  339 */     return true;
/*      */   }
/*      */ 
/*      */   
/*      */   public short set(int index, short k) {
/*  344 */     if (index >= this.size) throw new IndexOutOfBoundsException("Index (" + index + ") is greater than or equal to list size (" + this.size + ")"); 
/*  345 */     short old = this.a[index];
/*  346 */     this.a[index] = k;
/*  347 */     return old;
/*      */   }
/*      */ 
/*      */   
/*      */   public void clear() {
/*  352 */     this.size = 0;
/*  353 */     assert this.size <= this.a.length;
/*      */   }
/*      */ 
/*      */   
/*      */   public int size() {
/*  358 */     return this.size;
/*      */   }
/*      */ 
/*      */   
/*      */   public void size(int size) {
/*  363 */     if (size > this.a.length) this.a = ShortArrays.forceCapacity(this.a, size, this.size); 
/*  364 */     if (size > this.size) Arrays.fill(this.a, this.size, size, (short)0); 
/*  365 */     this.size = size;
/*      */   }
/*      */ 
/*      */   
/*      */   public boolean isEmpty() {
/*  370 */     return (this.size == 0);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void trim() {
/*  379 */     trim(0);
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
/*  398 */     if (n >= this.a.length || this.size == this.a.length)
/*  399 */       return;  short[] t = new short[Math.max(n, this.size)];
/*  400 */     System.arraycopy(this.a, 0, t, 0, this.size);
/*  401 */     this.a = t;
/*  402 */     assert this.size <= this.a.length;
/*      */   }
/*      */   
/*      */   private class SubList extends AbstractShortList.ShortRandomAccessSubList {
/*      */     private static final long serialVersionUID = -3185226345314976296L;
/*      */     
/*      */     protected SubList(int from, int to) {
/*  409 */       super(from, to);
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     private short[] getParentArray() {
/*  416 */       return ShortArrayList.this.a;
/*      */     }
/*      */ 
/*      */     
/*      */     public short getShort(int i) {
/*  421 */       ensureRestrictedIndex(i);
/*  422 */       return ShortArrayList.this.a[i + this.from];
/*      */     }
/*      */     
/*      */     private final class SubListIterator
/*      */       extends ShortIterators.AbstractIndexBasedListIterator
/*      */     {
/*      */       SubListIterator(int index) {
/*  429 */         super(0, index);
/*      */       }
/*      */ 
/*      */       
/*      */       protected final short get(int i) {
/*  434 */         return ShortArrayList.this.a[ShortArrayList.SubList.this.from + i];
/*      */       }
/*      */ 
/*      */       
/*      */       protected final void add(int i, short k) {
/*  439 */         ShortArrayList.SubList.this.add(i, k);
/*      */       }
/*      */ 
/*      */       
/*      */       protected final void set(int i, short k) {
/*  444 */         ShortArrayList.SubList.this.set(i, k);
/*      */       }
/*      */ 
/*      */       
/*      */       protected final void remove(int i) {
/*  449 */         ShortArrayList.SubList.this.removeShort(i);
/*      */       }
/*      */ 
/*      */       
/*      */       protected final int getMaxPos() {
/*  454 */         return ShortArrayList.SubList.this.to - ShortArrayList.SubList.this.from;
/*      */       }
/*      */ 
/*      */       
/*      */       public short nextShort() {
/*  459 */         if (!hasNext()) throw new NoSuchElementException(); 
/*  460 */         return ShortArrayList.this.a[ShortArrayList.SubList.this.from + (this.lastReturned = this.pos++)];
/*      */       }
/*      */ 
/*      */       
/*      */       public short previousShort() {
/*  465 */         if (!hasPrevious()) throw new NoSuchElementException(); 
/*  466 */         return ShortArrayList.this.a[ShortArrayList.SubList.this.from + (this.lastReturned = --this.pos)];
/*      */       }
/*      */ 
/*      */       
/*      */       public void forEachRemaining(ShortConsumer action) {
/*  471 */         int max = ShortArrayList.SubList.this.to - ShortArrayList.SubList.this.from;
/*  472 */         while (this.pos < max) {
/*  473 */           action.accept(ShortArrayList.this.a[ShortArrayList.SubList.this.from + (this.lastReturned = this.pos++)]);
/*      */         }
/*      */       }
/*      */     }
/*      */ 
/*      */     
/*      */     public ShortListIterator listIterator(int index) {
/*  480 */       return new SubListIterator(index);
/*      */     }
/*      */     
/*      */     private final class SubListSpliterator
/*      */       extends ShortSpliterators.LateBindingSizeIndexBasedSpliterator {
/*      */       SubListSpliterator() {
/*  486 */         super(ShortArrayList.SubList.this.from);
/*      */       }
/*      */       
/*      */       private SubListSpliterator(int pos, int maxPos) {
/*  490 */         super(pos, maxPos);
/*      */       }
/*      */ 
/*      */       
/*      */       protected final int getMaxPosFromBackingStore() {
/*  495 */         return ShortArrayList.SubList.this.to;
/*      */       }
/*      */ 
/*      */       
/*      */       protected final short get(int i) {
/*  500 */         return ShortArrayList.this.a[i];
/*      */       }
/*      */ 
/*      */       
/*      */       protected final SubListSpliterator makeForSplit(int pos, int maxPos) {
/*  505 */         return new SubListSpliterator(pos, maxPos);
/*      */       }
/*      */ 
/*      */       
/*      */       public boolean tryAdvance(ShortConsumer action) {
/*  510 */         if (this.pos >= getMaxPos()) return false; 
/*  511 */         action.accept(ShortArrayList.this.a[this.pos++]);
/*  512 */         return true;
/*      */       }
/*      */ 
/*      */       
/*      */       public void forEachRemaining(ShortConsumer action) {
/*  517 */         int max = getMaxPos();
/*  518 */         while (this.pos < max) {
/*  519 */           action.accept(ShortArrayList.this.a[this.pos++]);
/*      */         }
/*      */       }
/*      */     }
/*      */ 
/*      */     
/*      */     public ShortSpliterator spliterator() {
/*  526 */       return new SubListSpliterator();
/*      */     }
/*      */     
/*      */     boolean contentsEquals(short[] otherA, int otherAFrom, int otherATo) {
/*  530 */       if (ShortArrayList.this.a == otherA && this.from == otherAFrom && this.to == otherATo) return true; 
/*  531 */       if (otherATo - otherAFrom != size()) {
/*  532 */         return false;
/*      */       }
/*  534 */       int pos = this.from, otherPos = otherAFrom;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/*  540 */       while (pos < this.to) { if (ShortArrayList.this.a[pos++] != otherA[otherPos++]) return false;  }
/*  541 */        return true;
/*      */     }
/*      */ 
/*      */     
/*      */     public boolean equals(Object o) {
/*  546 */       if (o == this) return true; 
/*  547 */       if (o == null) return false; 
/*  548 */       if (!(o instanceof List)) return false; 
/*  549 */       if (o instanceof ShortArrayList) {
/*      */         
/*  551 */         ShortArrayList other = (ShortArrayList)o;
/*  552 */         return contentsEquals(other.a, 0, other.size());
/*      */       } 
/*  554 */       if (o instanceof SubList) {
/*      */         
/*  556 */         SubList other = (SubList)o;
/*  557 */         return contentsEquals(other.getParentArray(), other.from, other.to);
/*      */       } 
/*  559 */       return super.equals(o);
/*      */     }
/*      */     
/*      */     int contentsCompareTo(short[] otherA, int otherAFrom, int otherATo) {
/*  563 */       if (ShortArrayList.this.a == otherA && this.from == otherAFrom && this.to == otherATo) return 0;
/*      */       
/*      */       int i;
/*      */       int j;
/*  567 */       for (i = this.from, j = otherAFrom; i < this.to && i < otherATo; i++, j++) {
/*  568 */         short e1 = ShortArrayList.this.a[i];
/*  569 */         short e2 = otherA[j]; int r;
/*  570 */         if ((r = Short.compare(e1, e2)) != 0) return r; 
/*      */       } 
/*  572 */       return (i < otherATo) ? -1 : ((i < this.to) ? 1 : 0);
/*      */     }
/*      */ 
/*      */     
/*      */     public int compareTo(List<? extends Short> l) {
/*  577 */       if (l instanceof ShortArrayList) {
/*      */         
/*  579 */         ShortArrayList other = (ShortArrayList)l;
/*  580 */         return contentsCompareTo(other.a, 0, other.size());
/*      */       } 
/*  582 */       if (l instanceof SubList) {
/*      */         
/*  584 */         SubList other = (SubList)l;
/*  585 */         return contentsCompareTo(other.getParentArray(), other.from, other.to);
/*      */       } 
/*  587 */       return super.compareTo(l);
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public ShortList subList(int from, int to) {
/*  597 */     if (from == 0 && to == size()) return this; 
/*  598 */     ensureIndex(from);
/*  599 */     ensureIndex(to);
/*  600 */     if (from > to) throw new IndexOutOfBoundsException("Start index (" + from + ") is greater than end index (" + to + ")"); 
/*  601 */     return new SubList(from, to);
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
/*      */   public void getElements(int from, short[] a, int offset, int length) {
/*  614 */     ShortArrays.ensureOffsetLength(a, offset, length);
/*  615 */     System.arraycopy(this.a, from, a, offset, length);
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
/*  626 */     Arrays.ensureFromTo(this.size, from, to);
/*  627 */     System.arraycopy(this.a, to, this.a, from, this.size - to);
/*  628 */     this.size -= to - from;
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
/*      */   public void addElements(int index, short[] a, int offset, int length) {
/*  641 */     ensureIndex(index);
/*  642 */     ShortArrays.ensureOffsetLength(a, offset, length);
/*  643 */     grow(this.size + length);
/*  644 */     System.arraycopy(this.a, index, this.a, index + length, this.size - index);
/*  645 */     System.arraycopy(a, offset, this.a, index, length);
/*  646 */     this.size += length;
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
/*      */   public void setElements(int index, short[] a, int offset, int length) {
/*  659 */     ensureIndex(index);
/*  660 */     ShortArrays.ensureOffsetLength(a, offset, length);
/*  661 */     if (index + length > this.size) throw new IndexOutOfBoundsException("End index (" + (index + length) + ") is greater than list size (" + this.size + ")"); 
/*  662 */     System.arraycopy(a, offset, this.a, index, length);
/*      */   }
/*      */ 
/*      */   
/*      */   public void forEach(ShortConsumer action) {
/*  667 */     for (int i = 0; i < this.size; i++) {
/*  668 */       action.accept(this.a[i]);
/*      */     }
/*      */   }
/*      */ 
/*      */   
/*      */   public boolean addAll(int index, ShortCollection c) {
/*  674 */     if (c instanceof ShortList) {
/*  675 */       return addAll(index, (ShortList)c);
/*      */     }
/*  677 */     ensureIndex(index);
/*  678 */     int n = c.size();
/*  679 */     if (n == 0) return false; 
/*  680 */     grow(this.size + n);
/*  681 */     System.arraycopy(this.a, index, this.a, index + n, this.size - index);
/*  682 */     ShortIterator i = c.iterator();
/*  683 */     this.size += n;
/*  684 */     for (; n-- != 0; this.a[index++] = i.nextShort());
/*  685 */     assert this.size <= this.a.length;
/*  686 */     return true;
/*      */   }
/*      */ 
/*      */   
/*      */   public boolean addAll(int index, ShortList l) {
/*  691 */     ensureIndex(index);
/*  692 */     int n = l.size();
/*  693 */     if (n == 0) return false; 
/*  694 */     grow(this.size + n);
/*  695 */     System.arraycopy(this.a, index, this.a, index + n, this.size - index);
/*  696 */     l.getElements(0, this.a, index, n);
/*  697 */     this.size += n;
/*  698 */     assert this.size <= this.a.length;
/*  699 */     return true;
/*      */   }
/*      */ 
/*      */   
/*      */   public boolean removeAll(ShortCollection c) {
/*  704 */     short[] a = this.a;
/*  705 */     int j = 0;
/*  706 */     for (int i = 0; i < this.size; ) { if (!c.contains(a[i])) a[j++] = a[i];  i++; }
/*  707 */      boolean modified = (this.size != j);
/*  708 */     this.size = j;
/*  709 */     return modified;
/*      */   }
/*      */ 
/*      */   
/*      */   public short[] toArray(short[] a) {
/*  714 */     if (a == null || a.length < this.size) a = Arrays.copyOf(a, this.size); 
/*  715 */     System.arraycopy(this.a, 0, a, 0, this.size);
/*  716 */     return a;
/*      */   }
/*      */ 
/*      */   
/*      */   public ShortListIterator listIterator(final int index) {
/*  721 */     ensureIndex(index);
/*  722 */     return new ShortListIterator() {
/*  723 */         int pos = index; int last = -1;
/*      */ 
/*      */         
/*      */         public boolean hasNext() {
/*  727 */           return (this.pos < ShortArrayList.this.size);
/*      */         }
/*      */ 
/*      */         
/*      */         public boolean hasPrevious() {
/*  732 */           return (this.pos > 0);
/*      */         }
/*      */ 
/*      */         
/*      */         public short nextShort() {
/*  737 */           if (!hasNext()) throw new NoSuchElementException(); 
/*  738 */           return ShortArrayList.this.a[this.last = this.pos++];
/*      */         }
/*      */ 
/*      */         
/*      */         public short previousShort() {
/*  743 */           if (!hasPrevious()) throw new NoSuchElementException(); 
/*  744 */           return ShortArrayList.this.a[this.last = --this.pos];
/*      */         }
/*      */ 
/*      */         
/*      */         public int nextIndex() {
/*  749 */           return this.pos;
/*      */         }
/*      */ 
/*      */         
/*      */         public int previousIndex() {
/*  754 */           return this.pos - 1;
/*      */         }
/*      */ 
/*      */         
/*      */         public void add(short k) {
/*  759 */           ShortArrayList.this.add(this.pos++, k);
/*  760 */           this.last = -1;
/*      */         }
/*      */ 
/*      */         
/*      */         public void set(short k) {
/*  765 */           if (this.last == -1) throw new IllegalStateException(); 
/*  766 */           ShortArrayList.this.set(this.last, k);
/*      */         }
/*      */ 
/*      */         
/*      */         public void remove() {
/*  771 */           if (this.last == -1) throw new IllegalStateException(); 
/*  772 */           ShortArrayList.this.removeShort(this.last);
/*      */           
/*  774 */           if (this.last < this.pos) this.pos--; 
/*  775 */           this.last = -1;
/*      */         }
/*      */ 
/*      */         
/*      */         public void forEachRemaining(ShortConsumer action) {
/*  780 */           while (this.pos < ShortArrayList.this.size) {
/*  781 */             action.accept(ShortArrayList.this.a[this.last = this.pos++]);
/*      */           }
/*      */         }
/*      */ 
/*      */         
/*      */         public int back(int n) {
/*  787 */           if (n < 0) throw new IllegalArgumentException("Argument must be nonnegative: " + n); 
/*  788 */           int remaining = ShortArrayList.this.size - this.pos;
/*  789 */           if (n < remaining) {
/*  790 */             this.pos -= n;
/*      */           } else {
/*  792 */             n = remaining;
/*  793 */             this.pos = 0;
/*      */           } 
/*  795 */           this.last = this.pos;
/*  796 */           return n;
/*      */         }
/*      */ 
/*      */         
/*      */         public int skip(int n) {
/*  801 */           if (n < 0) throw new IllegalArgumentException("Argument must be nonnegative: " + n); 
/*  802 */           int remaining = ShortArrayList.this.size - this.pos;
/*  803 */           if (n < remaining) {
/*  804 */             this.pos += n;
/*      */           } else {
/*  806 */             n = remaining;
/*  807 */             this.pos = ShortArrayList.this.size;
/*      */           } 
/*  809 */           this.last = this.pos - 1;
/*  810 */           return n;
/*      */         }
/*      */       };
/*      */   }
/*      */ 
/*      */   
/*      */   private final class Spliterator
/*      */     implements ShortSpliterator
/*      */   {
/*      */     boolean hasSplit = false;
/*      */     int pos;
/*      */     int max;
/*      */     
/*      */     public Spliterator() {
/*  824 */       this(0, ShortArrayList.this.size, false);
/*      */     }
/*      */     
/*      */     private Spliterator(int pos, int max, boolean hasSplit) {
/*  828 */       assert pos <= max : "pos " + pos + " must be <= max " + max;
/*  829 */       this.pos = pos;
/*  830 */       this.max = max;
/*  831 */       this.hasSplit = hasSplit;
/*      */     }
/*      */     
/*      */     private int getWorkingMax() {
/*  835 */       return this.hasSplit ? this.max : ShortArrayList.this.size;
/*      */     }
/*      */ 
/*      */     
/*      */     public int characteristics() {
/*  840 */       return 16720;
/*      */     }
/*      */ 
/*      */     
/*      */     public long estimateSize() {
/*  845 */       return (getWorkingMax() - this.pos);
/*      */     }
/*      */ 
/*      */     
/*      */     public boolean tryAdvance(ShortConsumer action) {
/*  850 */       if (this.pos >= getWorkingMax()) return false; 
/*  851 */       action.accept(ShortArrayList.this.a[this.pos++]);
/*  852 */       return true;
/*      */     }
/*      */ 
/*      */     
/*      */     public void forEachRemaining(ShortConsumer action) {
/*  857 */       for (int max = getWorkingMax(); this.pos < max; this.pos++) {
/*  858 */         action.accept(ShortArrayList.this.a[this.pos]);
/*      */       }
/*      */     }
/*      */ 
/*      */     
/*      */     public long skip(long n) {
/*  864 */       if (n < 0L) throw new IllegalArgumentException("Argument must be nonnegative: " + n); 
/*  865 */       int max = getWorkingMax();
/*  866 */       if (this.pos >= max) return 0L; 
/*  867 */       int remaining = max - this.pos;
/*  868 */       if (n < remaining) {
/*  869 */         this.pos = SafeMath.safeLongToInt(this.pos + n);
/*  870 */         return n;
/*      */       } 
/*  872 */       n = remaining;
/*  873 */       this.pos = max;
/*  874 */       return n;
/*      */     }
/*      */ 
/*      */     
/*      */     public ShortSpliterator trySplit() {
/*  879 */       int max = getWorkingMax();
/*  880 */       int retLen = max - this.pos >> 1;
/*  881 */       if (retLen <= 1) return null;
/*      */       
/*  883 */       this.max = max;
/*  884 */       int myNewPos = this.pos + retLen;
/*  885 */       int retMax = myNewPos;
/*  886 */       int oldPos = this.pos;
/*  887 */       this.pos = myNewPos;
/*  888 */       this.hasSplit = true;
/*  889 */       return new Spliterator(oldPos, retMax, true);
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
/*      */   public ShortSpliterator spliterator() {
/*  907 */     return new Spliterator();
/*      */   }
/*      */ 
/*      */   
/*      */   public void sort(ShortComparator comp) {
/*  912 */     if (comp == null) {
/*  913 */       ShortArrays.stableSort(this.a, 0, this.size);
/*      */     } else {
/*  915 */       ShortArrays.stableSort(this.a, 0, this.size, comp);
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   public void unstableSort(ShortComparator comp) {
/*  921 */     if (comp == null) {
/*  922 */       ShortArrays.unstableSort(this.a, 0, this.size);
/*      */     } else {
/*  924 */       ShortArrays.unstableSort(this.a, 0, this.size, comp);
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public ShortArrayList clone() {
/*  931 */     ShortArrayList cloned = null;
/*      */     
/*  933 */     if (getClass() == ShortArrayList.class) {
/*      */ 
/*      */       
/*  936 */       cloned = new ShortArrayList(copyArraySafe(this.a, this.size), false);
/*  937 */       cloned.size = this.size;
/*      */     } else {
/*      */       try {
/*  940 */         cloned = (ShortArrayList)super.clone();
/*  941 */       } catch (CloneNotSupportedException err) {
/*      */         
/*  943 */         throw new InternalError(err);
/*      */       } 
/*      */ 
/*      */       
/*  947 */       cloned.a = copyArraySafe(this.a, this.size);
/*      */     } 
/*  949 */     return cloned;
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
/*      */   public boolean equals(ShortArrayList l) {
/*  964 */     if (l == this) return true; 
/*  965 */     int s = size();
/*  966 */     if (s != l.size()) return false; 
/*  967 */     short[] a1 = this.a;
/*  968 */     short[] a2 = l.a;
/*  969 */     if (a1 == a2 && s == l.size()) return true; 
/*  970 */     while (s-- != 0) { if (a1[s] != a2[s]) return false;  }
/*  971 */      return true;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean equals(Object o) {
/*  977 */     if (o == this) return true; 
/*  978 */     if (o == null) return false; 
/*  979 */     if (!(o instanceof List)) return false; 
/*  980 */     if (o instanceof ShortArrayList)
/*      */     {
/*  982 */       return equals((ShortArrayList)o);
/*      */     }
/*  984 */     if (o instanceof SubList)
/*      */     {
/*      */       
/*  987 */       return ((SubList)o).equals(this);
/*      */     }
/*  989 */     return super.equals(o);
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
/*      */   public int compareTo(ShortArrayList l) {
/* 1004 */     int s1 = size(), s2 = l.size();
/* 1005 */     short[] a1 = this.a, a2 = l.a;
/* 1006 */     if (a1 == a2 && s1 == s2) return 0;
/*      */ 
/*      */     
/*      */     int i;
/* 1010 */     for (i = 0; i < s1 && i < s2; i++) {
/* 1011 */       short e1 = a1[i];
/* 1012 */       short e2 = a2[i]; int r;
/* 1013 */       if ((r = Short.compare(e1, e2)) != 0) return r; 
/*      */     } 
/* 1015 */     return (i < s2) ? -1 : ((i < s1) ? 1 : 0);
/*      */   }
/*      */ 
/*      */   
/*      */   public int compareTo(List<? extends Short> l) {
/* 1020 */     if (l instanceof ShortArrayList) {
/* 1021 */       return compareTo((ShortArrayList)l);
/*      */     }
/* 1023 */     if (l instanceof SubList)
/*      */     {
/* 1025 */       return -((SubList)l).compareTo(this);
/*      */     }
/* 1027 */     return super.compareTo(l);
/*      */   }
/*      */   
/*      */   private void writeObject(ObjectOutputStream s) throws IOException {
/* 1031 */     s.defaultWriteObject();
/* 1032 */     for (int i = 0; i < this.size; ) { s.writeShort(this.a[i]); i++; }
/*      */   
/*      */   }
/*      */   private void readObject(ObjectInputStream s) throws IOException, ClassNotFoundException {
/* 1036 */     s.defaultReadObject();
/* 1037 */     this.a = new short[this.size];
/* 1038 */     for (int i = 0; i < this.size; ) { this.a[i] = s.readShort(); i++; }
/*      */   
/*      */   }
/*      */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\i\\unimi\dsi\fastutil\shorts\ShortArrayList.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */