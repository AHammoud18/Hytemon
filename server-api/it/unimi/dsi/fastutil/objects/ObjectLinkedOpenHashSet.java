/*      */ package it.unimi.dsi.fastutil.objects;
/*      */ 
/*      */ import it.unimi.dsi.fastutil.Hash;
/*      */ import it.unimi.dsi.fastutil.HashCommon;
/*      */ import it.unimi.dsi.fastutil.Size64;
/*      */ import java.io.IOException;
/*      */ import java.io.ObjectInputStream;
/*      */ import java.io.ObjectOutputStream;
/*      */ import java.io.Serializable;
/*      */ import java.util.Arrays;
/*      */ import java.util.Collection;
/*      */ import java.util.Comparator;
/*      */ import java.util.Iterator;
/*      */ import java.util.NoSuchElementException;
/*      */ import java.util.Objects;
/*      */ import java.util.SortedSet;
/*      */ import java.util.Spliterator;
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
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ public class ObjectLinkedOpenHashSet<K>
/*      */   extends AbstractObjectSortedSet<K>
/*      */   implements Serializable, Cloneable, Hash
/*      */ {
/*      */   private static final long serialVersionUID = 0L;
/*      */   private static final boolean ASSERTS = false;
/*      */   protected transient K[] key;
/*      */   protected transient int mask;
/*      */   protected transient boolean containsNull;
/*   91 */   protected transient int first = -1;
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*   96 */   protected transient int last = -1;
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected transient long[] link;
/*      */ 
/*      */ 
/*      */   
/*      */   protected transient int n;
/*      */ 
/*      */ 
/*      */   
/*      */   protected transient int maxFill;
/*      */ 
/*      */ 
/*      */   
/*      */   protected final transient int minN;
/*      */ 
/*      */ 
/*      */   
/*      */   protected int size;
/*      */ 
/*      */ 
/*      */   
/*      */   protected final float f;
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public ObjectLinkedOpenHashSet(int expected, float f) {
/*  127 */     if (f <= 0.0F || f >= 1.0F) throw new IllegalArgumentException("Load factor must be greater than 0 and smaller than 1"); 
/*  128 */     if (expected < 0) throw new IllegalArgumentException("The expected number of elements must be nonnegative"); 
/*  129 */     this.f = f;
/*  130 */     this.minN = this.n = HashCommon.arraySize(expected, f);
/*  131 */     this.mask = this.n - 1;
/*  132 */     this.maxFill = HashCommon.maxFill(this.n, f);
/*  133 */     this.key = (K[])new Object[this.n + 1];
/*  134 */     this.link = new long[this.n + 1];
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public ObjectLinkedOpenHashSet(int expected) {
/*  143 */     this(expected, 0.75F);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public ObjectLinkedOpenHashSet() {
/*  151 */     this(16, 0.75F);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public ObjectLinkedOpenHashSet(Collection<? extends K> c, float f) {
/*  161 */     this(c.size(), f);
/*  162 */     addAll(c);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public ObjectLinkedOpenHashSet(Collection<? extends K> c) {
/*  172 */     this(c, 0.75F);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public ObjectLinkedOpenHashSet(ObjectCollection<? extends K> c, float f) {
/*  182 */     this(c.size(), f);
/*  183 */     addAll(c);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public ObjectLinkedOpenHashSet(ObjectCollection<? extends K> c) {
/*  193 */     this(c, 0.75F);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public ObjectLinkedOpenHashSet(Iterator<? extends K> i, float f) {
/*  203 */     this(16, f);
/*  204 */     for (; i.hasNext(); add(i.next()));
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public ObjectLinkedOpenHashSet(Iterator<? extends K> i) {
/*  214 */     this(i, 0.75F);
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
/*      */   public ObjectLinkedOpenHashSet(K[] a, int offset, int length, float f) {
/*  226 */     this((length < 0) ? 0 : length, f);
/*  227 */     ObjectArrays.ensureOffsetLength(a, offset, length);
/*  228 */     for (int i = 0; i < length; ) { add(a[offset + i]); i++; }
/*      */   
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public ObjectLinkedOpenHashSet(K[] a, int offset, int length) {
/*  240 */     this(a, offset, length, 0.75F);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public ObjectLinkedOpenHashSet(K[] a, float f) {
/*  250 */     this(a, 0, a.length, f);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public ObjectLinkedOpenHashSet(K[] a) {
/*  260 */     this(a, 0.75F);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static <K> ObjectLinkedOpenHashSet<K> of() {
/*  269 */     return new ObjectLinkedOpenHashSet<>();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static <K> ObjectLinkedOpenHashSet<K> of(K e) {
/*  280 */     ObjectLinkedOpenHashSet<K> result = new ObjectLinkedOpenHashSet<>(1, 0.75F);
/*  281 */     result.add(e);
/*  282 */     return result;
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
/*      */   public static <K> ObjectLinkedOpenHashSet<K> of(K e0, K e1) {
/*  296 */     ObjectLinkedOpenHashSet<K> result = new ObjectLinkedOpenHashSet<>(2, 0.75F);
/*  297 */     result.add(e0);
/*  298 */     if (!result.add(e1)) {
/*  299 */       throw new IllegalArgumentException("Duplicate element: " + e1);
/*      */     }
/*  301 */     return result;
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
/*      */   public static <K> ObjectLinkedOpenHashSet<K> of(K e0, K e1, K e2) {
/*  316 */     ObjectLinkedOpenHashSet<K> result = new ObjectLinkedOpenHashSet<>(3, 0.75F);
/*  317 */     result.add(e0);
/*  318 */     if (!result.add(e1)) {
/*  319 */       throw new IllegalArgumentException("Duplicate element: " + e1);
/*      */     }
/*  321 */     if (!result.add(e2)) {
/*  322 */       throw new IllegalArgumentException("Duplicate element: " + e2);
/*      */     }
/*  324 */     return result;
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
/*      */   public static <K> ObjectLinkedOpenHashSet<K> of(K... a) {
/*  338 */     ObjectLinkedOpenHashSet<K> result = new ObjectLinkedOpenHashSet<>(a.length, 0.75F);
/*  339 */     for (K element : a) {
/*  340 */       if (!result.add(element)) {
/*  341 */         throw new IllegalArgumentException("Duplicate element " + element);
/*      */       }
/*      */     } 
/*  344 */     return result;
/*      */   }
/*      */ 
/*      */   
/*      */   private ObjectLinkedOpenHashSet<K> combine(ObjectLinkedOpenHashSet<? extends K> toAddFrom) {
/*  349 */     addAll(toAddFrom);
/*  350 */     return this;
/*      */   }
/*      */   
/*  353 */   private static final Collector<Object, ?, ObjectLinkedOpenHashSet<Object>> TO_SET_COLLECTOR = (Collector)Collector.of(ObjectLinkedOpenHashSet::new, ObjectLinkedOpenHashSet::add, ObjectLinkedOpenHashSet::combine, new Collector.Characteristics[0]);
/*      */   
/*      */   private static final int SPLITERATOR_CHARACTERISTICS = 81;
/*      */   
/*      */   public static <K> Collector<K, ?, ObjectLinkedOpenHashSet<K>> toSet() {
/*  358 */     return (Collector)TO_SET_COLLECTOR;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static <K> Collector<K, ?, ObjectLinkedOpenHashSet<K>> toSetWithExpectedSize(int expectedSize) {
/*  366 */     if (expectedSize <= 16)
/*      */     {
/*      */       
/*  369 */       return toSet();
/*      */     }
/*  371 */     return (Collector)Collector.of(new ObjectCollections.SizeDecreasingSupplier<>(expectedSize, size -> (size <= 16) ? new ObjectLinkedOpenHashSet() : new ObjectLinkedOpenHashSet(size)), ObjectLinkedOpenHashSet::add, ObjectLinkedOpenHashSet::combine, new Collector.Characteristics[0]);
/*      */   }
/*      */   
/*      */   private int realSize() {
/*  375 */     return this.containsNull ? (this.size - 1) : this.size;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void ensureCapacity(int capacity) {
/*  385 */     int needed = HashCommon.arraySize(capacity, this.f);
/*  386 */     if (needed > this.n) rehash(needed); 
/*      */   }
/*      */   
/*      */   private void tryCapacity(long capacity) {
/*  390 */     int needed = (int)Math.min(1073741824L, Math.max(2L, HashCommon.nextPowerOfTwo((long)Math.ceil(((float)capacity / this.f)))));
/*  391 */     if (needed > this.n) rehash(needed);
/*      */   
/*      */   }
/*      */ 
/*      */   
/*      */   public boolean addAll(Collection<? extends K> c) {
/*  397 */     if (this.f <= 0.5D) { ensureCapacity(c.size()); }
/*  398 */     else { tryCapacity((size() + c.size())); }
/*      */     
/*  400 */     return super.addAll(c);
/*      */   }
/*      */ 
/*      */   
/*      */   public boolean add(K k) {
/*      */     int pos;
/*  406 */     if (k == null) {
/*  407 */       if (this.containsNull) return false; 
/*  408 */       pos = this.n;
/*  409 */       this.containsNull = true;
/*      */     } else {
/*      */       
/*  412 */       K[] key = this.key;
/*      */       K curr;
/*  414 */       if ((curr = key[pos = HashCommon.mix(k.hashCode()) & this.mask]) != null) {
/*  415 */         if (curr.equals(k)) return false; 
/*  416 */         while ((curr = key[pos = pos + 1 & this.mask]) != null) { if (curr.equals(k)) return false;  }
/*      */       
/*  418 */       }  key[pos] = k;
/*      */     } 
/*  420 */     if (this.size == 0) {
/*  421 */       this.first = this.last = pos;
/*      */       
/*  423 */       this.link[pos] = -1L;
/*      */     } else {
/*  425 */       this.link[this.last] = this.link[this.last] ^ (this.link[this.last] ^ pos & 0xFFFFFFFFL) & 0xFFFFFFFFL;
/*  426 */       this.link[pos] = (this.last & 0xFFFFFFFFL) << 32L | 0xFFFFFFFFL;
/*  427 */       this.last = pos;
/*      */     } 
/*  429 */     if (this.size++ >= this.maxFill) rehash(HashCommon.arraySize(this.size + 1, this.f));
/*      */     
/*  431 */     return true;
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
/*      */   public K addOrGet(K k) {
/*      */     int pos;
/*  449 */     if (k == null) {
/*  450 */       if (this.containsNull) return this.key[this.n]; 
/*  451 */       pos = this.n;
/*  452 */       this.containsNull = true;
/*      */     } else {
/*      */       
/*  455 */       K[] key = this.key;
/*      */       K curr;
/*  457 */       if ((curr = key[pos = HashCommon.mix(k.hashCode()) & this.mask]) != null) {
/*  458 */         if (curr.equals(k)) return curr; 
/*  459 */         while ((curr = key[pos = pos + 1 & this.mask]) != null) { if (curr.equals(k)) return curr;  }
/*      */       
/*  461 */       }  key[pos] = k;
/*      */     } 
/*  463 */     if (this.size == 0) {
/*  464 */       this.first = this.last = pos;
/*      */       
/*  466 */       this.link[pos] = -1L;
/*      */     } else {
/*  468 */       this.link[this.last] = this.link[this.last] ^ (this.link[this.last] ^ pos & 0xFFFFFFFFL) & 0xFFFFFFFFL;
/*  469 */       this.link[pos] = (this.last & 0xFFFFFFFFL) << 32L | 0xFFFFFFFFL;
/*  470 */       this.last = pos;
/*      */     } 
/*  472 */     if (this.size++ >= this.maxFill) rehash(HashCommon.arraySize(this.size + 1, this.f));
/*      */     
/*  474 */     return k;
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
/*      */   protected final void shiftKeys(int pos) {
/*  487 */     K[] key = this.key; while (true) {
/*      */       K curr; int last;
/*  489 */       pos = (last = pos) + 1 & this.mask;
/*      */       while (true) {
/*  491 */         if ((curr = key[pos]) == null) {
/*  492 */           key[last] = null;
/*      */           return;
/*      */         } 
/*  495 */         int slot = HashCommon.mix(curr.hashCode()) & this.mask;
/*  496 */         if ((last <= pos) ? (last >= slot || slot > pos) : (last >= slot && slot > pos))
/*  497 */           break;  pos = pos + 1 & this.mask;
/*      */       } 
/*  499 */       key[last] = curr;
/*  500 */       fixPointers(pos, last);
/*      */     } 
/*      */   }
/*      */   
/*      */   private boolean removeEntry(int pos) {
/*  505 */     this.size--;
/*  506 */     fixPointers(pos);
/*  507 */     shiftKeys(pos);
/*  508 */     if (this.n > this.minN && this.size < this.maxFill / 4 && this.n > 16) rehash(this.n / 2); 
/*  509 */     return true;
/*      */   }
/*      */   
/*      */   private boolean removeNullEntry() {
/*  513 */     this.containsNull = false;
/*  514 */     this.key[this.n] = null;
/*  515 */     this.size--;
/*  516 */     fixPointers(this.n);
/*  517 */     if (this.n > this.minN && this.size < this.maxFill / 4 && this.n > 16) rehash(this.n / 2); 
/*  518 */     return true;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean remove(Object k) {
/*  524 */     if (k == null) {
/*  525 */       if (this.containsNull) return removeNullEntry(); 
/*  526 */       return false;
/*      */     } 
/*      */     
/*  529 */     K[] key = this.key;
/*      */     K curr;
/*      */     int pos;
/*  532 */     if ((curr = key[pos = HashCommon.mix(k.hashCode()) & this.mask]) == null) return false; 
/*  533 */     if (k.equals(curr)) return removeEntry(pos); 
/*      */     while (true) {
/*  535 */       if ((curr = key[pos = pos + 1 & this.mask]) == null) return false; 
/*  536 */       if (k.equals(curr)) return removeEntry(pos);
/*      */     
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   public boolean contains(Object k) {
/*  543 */     if (k == null) return this.containsNull;
/*      */     
/*  545 */     K[] key = this.key;
/*      */     K curr;
/*      */     int pos;
/*  548 */     if ((curr = key[pos = HashCommon.mix(k.hashCode()) & this.mask]) == null) return false; 
/*  549 */     if (k.equals(curr)) return true; 
/*      */     while (true) {
/*  551 */       if ((curr = key[pos = pos + 1 & this.mask]) == null) return false; 
/*  552 */       if (k.equals(curr)) return true;
/*      */     
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public K get(Object k) {
/*  563 */     if (k == null) return this.key[this.n];
/*      */ 
/*      */     
/*  566 */     K[] key = this.key;
/*      */     K curr;
/*      */     int pos;
/*  569 */     if ((curr = key[pos = HashCommon.mix(k.hashCode()) & this.mask]) == null) return null; 
/*  570 */     if (k.equals(curr)) return curr;
/*      */     
/*      */     while (true) {
/*  573 */       if ((curr = key[pos = pos + 1 & this.mask]) == null) return null; 
/*  574 */       if (k.equals(curr)) return curr;
/*      */     
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public K removeFirst() {
/*  585 */     if (this.size == 0) throw new NoSuchElementException(); 
/*  586 */     int pos = this.first;
/*      */     
/*  588 */     if (this.size == 1) { this.first = this.last = -1; }
/*      */     else
/*  590 */     { this.first = (int)this.link[pos];
/*  591 */       if (0 <= this.first)
/*      */       {
/*  593 */         this.link[this.first] = this.link[this.first] | 0xFFFFFFFF00000000L;
/*      */       } }
/*      */     
/*  596 */     K k = this.key[pos];
/*  597 */     this.size--;
/*  598 */     if (k == null)
/*  599 */     { this.containsNull = false;
/*  600 */       this.key[this.n] = null; }
/*  601 */     else { shiftKeys(pos); }
/*  602 */      if (this.n > this.minN && this.size < this.maxFill / 4 && this.n > 16) rehash(this.n / 2); 
/*  603 */     return k;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public K removeLast() {
/*  613 */     if (this.size == 0) throw new NoSuchElementException(); 
/*  614 */     int pos = this.last;
/*      */     
/*  616 */     if (this.size == 1) { this.first = this.last = -1; }
/*      */     else
/*  618 */     { this.last = (int)(this.link[pos] >>> 32L);
/*  619 */       if (0 <= this.last)
/*      */       {
/*  621 */         this.link[this.last] = this.link[this.last] | 0xFFFFFFFFL;
/*      */       } }
/*      */     
/*  624 */     K k = this.key[pos];
/*  625 */     this.size--;
/*  626 */     if (k == null)
/*  627 */     { this.containsNull = false;
/*  628 */       this.key[this.n] = null; }
/*  629 */     else { shiftKeys(pos); }
/*  630 */      if (this.n > this.minN && this.size < this.maxFill / 4 && this.n > 16) rehash(this.n / 2); 
/*  631 */     return k;
/*      */   }
/*      */   
/*      */   private void moveIndexToFirst(int i) {
/*  635 */     if (this.size == 1 || this.first == i)
/*  636 */       return;  if (this.last == i) {
/*  637 */       this.last = (int)(this.link[i] >>> 32L);
/*      */       
/*  639 */       this.link[this.last] = this.link[this.last] | 0xFFFFFFFFL;
/*      */     } else {
/*  641 */       long linki = this.link[i];
/*  642 */       int prev = (int)(linki >>> 32L);
/*  643 */       int next = (int)linki;
/*  644 */       this.link[prev] = this.link[prev] ^ (this.link[prev] ^ linki & 0xFFFFFFFFL) & 0xFFFFFFFFL;
/*  645 */       this.link[next] = this.link[next] ^ (this.link[next] ^ linki & 0xFFFFFFFF00000000L) & 0xFFFFFFFF00000000L;
/*      */     } 
/*  647 */     this.link[this.first] = this.link[this.first] ^ (this.link[this.first] ^ (i & 0xFFFFFFFFL) << 32L) & 0xFFFFFFFF00000000L;
/*  648 */     this.link[i] = 0xFFFFFFFF00000000L | this.first & 0xFFFFFFFFL;
/*  649 */     this.first = i;
/*      */   }
/*      */   
/*      */   private void moveIndexToLast(int i) {
/*  653 */     if (this.size == 1 || this.last == i)
/*  654 */       return;  if (this.first == i) {
/*  655 */       this.first = (int)this.link[i];
/*      */       
/*  657 */       this.link[this.first] = this.link[this.first] | 0xFFFFFFFF00000000L;
/*      */     } else {
/*  659 */       long linki = this.link[i];
/*  660 */       int prev = (int)(linki >>> 32L);
/*  661 */       int next = (int)linki;
/*  662 */       this.link[prev] = this.link[prev] ^ (this.link[prev] ^ linki & 0xFFFFFFFFL) & 0xFFFFFFFFL;
/*  663 */       this.link[next] = this.link[next] ^ (this.link[next] ^ linki & 0xFFFFFFFF00000000L) & 0xFFFFFFFF00000000L;
/*      */     } 
/*  665 */     this.link[this.last] = this.link[this.last] ^ (this.link[this.last] ^ i & 0xFFFFFFFFL) & 0xFFFFFFFFL;
/*  666 */     this.link[i] = (this.last & 0xFFFFFFFFL) << 32L | 0xFFFFFFFFL;
/*  667 */     this.last = i;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean addAndMoveToFirst(K k) {
/*      */     int pos;
/*  679 */     if (k == null) {
/*  680 */       if (this.containsNull) {
/*  681 */         moveIndexToFirst(this.n);
/*  682 */         return false;
/*      */       } 
/*  684 */       this.containsNull = true;
/*  685 */       pos = this.n;
/*      */     } else {
/*      */       
/*  688 */       K[] key = this.key;
/*  689 */       pos = HashCommon.mix(k.hashCode()) & this.mask;
/*      */       
/*  691 */       while (key[pos] != null) {
/*  692 */         if (k.equals(key[pos])) {
/*  693 */           moveIndexToFirst(pos);
/*  694 */           return false;
/*      */         } 
/*  696 */         pos = pos + 1 & this.mask;
/*      */       } 
/*      */     } 
/*  699 */     this.key[pos] = k;
/*  700 */     if (this.size == 0) {
/*  701 */       this.first = this.last = pos;
/*      */       
/*  703 */       this.link[pos] = -1L;
/*      */     } else {
/*  705 */       this.link[this.first] = this.link[this.first] ^ (this.link[this.first] ^ (pos & 0xFFFFFFFFL) << 32L) & 0xFFFFFFFF00000000L;
/*  706 */       this.link[pos] = 0xFFFFFFFF00000000L | this.first & 0xFFFFFFFFL;
/*  707 */       this.first = pos;
/*      */     } 
/*  709 */     if (this.size++ >= this.maxFill) rehash(HashCommon.arraySize(this.size, this.f));
/*      */     
/*  711 */     return true;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean addAndMoveToLast(K k) {
/*      */     int pos;
/*  723 */     if (k == null) {
/*  724 */       if (this.containsNull) {
/*  725 */         moveIndexToLast(this.n);
/*  726 */         return false;
/*      */       } 
/*  728 */       this.containsNull = true;
/*  729 */       pos = this.n;
/*      */     } else {
/*      */       
/*  732 */       K[] key = this.key;
/*  733 */       pos = HashCommon.mix(k.hashCode()) & this.mask;
/*      */       
/*  735 */       while (key[pos] != null) {
/*  736 */         if (k.equals(key[pos])) {
/*  737 */           moveIndexToLast(pos);
/*  738 */           return false;
/*      */         } 
/*  740 */         pos = pos + 1 & this.mask;
/*      */       } 
/*      */     } 
/*  743 */     this.key[pos] = k;
/*  744 */     if (this.size == 0) {
/*  745 */       this.first = this.last = pos;
/*      */       
/*  747 */       this.link[pos] = -1L;
/*      */     } else {
/*  749 */       this.link[this.last] = this.link[this.last] ^ (this.link[this.last] ^ pos & 0xFFFFFFFFL) & 0xFFFFFFFFL;
/*  750 */       this.link[pos] = (this.last & 0xFFFFFFFFL) << 32L | 0xFFFFFFFFL;
/*  751 */       this.last = pos;
/*      */     } 
/*  753 */     if (this.size++ >= this.maxFill) rehash(HashCommon.arraySize(this.size, this.f));
/*      */     
/*  755 */     return true;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void clear() {
/*  766 */     if (this.size == 0)
/*  767 */       return;  this.size = 0;
/*  768 */     this.containsNull = false;
/*  769 */     Arrays.fill((Object[])this.key, (Object)null);
/*  770 */     this.first = this.last = -1;
/*      */   }
/*      */ 
/*      */   
/*      */   public int size() {
/*  775 */     return this.size;
/*      */   }
/*      */ 
/*      */   
/*      */   public boolean isEmpty() {
/*  780 */     return (this.size == 0);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected void fixPointers(int i) {
/*  790 */     if (this.size == 0) {
/*  791 */       this.first = this.last = -1;
/*      */       return;
/*      */     } 
/*  794 */     if (this.first == i) {
/*  795 */       this.first = (int)this.link[i];
/*  796 */       if (0 <= this.first)
/*      */       {
/*  798 */         this.link[this.first] = this.link[this.first] | 0xFFFFFFFF00000000L;
/*      */       }
/*      */       return;
/*      */     } 
/*  802 */     if (this.last == i) {
/*  803 */       this.last = (int)(this.link[i] >>> 32L);
/*  804 */       if (0 <= this.last)
/*      */       {
/*  806 */         this.link[this.last] = this.link[this.last] | 0xFFFFFFFFL;
/*      */       }
/*      */       return;
/*      */     } 
/*  810 */     long linki = this.link[i];
/*  811 */     int prev = (int)(linki >>> 32L);
/*  812 */     int next = (int)linki;
/*  813 */     this.link[prev] = this.link[prev] ^ (this.link[prev] ^ linki & 0xFFFFFFFFL) & 0xFFFFFFFFL;
/*  814 */     this.link[next] = this.link[next] ^ (this.link[next] ^ linki & 0xFFFFFFFF00000000L) & 0xFFFFFFFF00000000L;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected void fixPointers(int s, int d) {
/*  825 */     if (this.size == 1) {
/*  826 */       this.first = this.last = d;
/*      */       
/*  828 */       this.link[d] = -1L;
/*      */       return;
/*      */     } 
/*  831 */     if (this.first == s) {
/*  832 */       this.first = d;
/*  833 */       this.link[(int)this.link[s]] = this.link[(int)this.link[s]] ^ (this.link[(int)this.link[s]] ^ (d & 0xFFFFFFFFL) << 32L) & 0xFFFFFFFF00000000L;
/*  834 */       this.link[d] = this.link[s];
/*      */       return;
/*      */     } 
/*  837 */     if (this.last == s) {
/*  838 */       this.last = d;
/*  839 */       this.link[(int)(this.link[s] >>> 32L)] = this.link[(int)(this.link[s] >>> 32L)] ^ (this.link[(int)(this.link[s] >>> 32L)] ^ d & 0xFFFFFFFFL) & 0xFFFFFFFFL;
/*  840 */       this.link[d] = this.link[s];
/*      */       return;
/*      */     } 
/*  843 */     long links = this.link[s];
/*  844 */     int prev = (int)(links >>> 32L);
/*  845 */     int next = (int)links;
/*  846 */     this.link[prev] = this.link[prev] ^ (this.link[prev] ^ d & 0xFFFFFFFFL) & 0xFFFFFFFFL;
/*  847 */     this.link[next] = this.link[next] ^ (this.link[next] ^ (d & 0xFFFFFFFFL) << 32L) & 0xFFFFFFFF00000000L;
/*  848 */     this.link[d] = links;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public K first() {
/*  858 */     if (this.size == 0) throw new NoSuchElementException(); 
/*  859 */     return this.key[this.first];
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public K last() {
/*  869 */     if (this.size == 0) throw new NoSuchElementException(); 
/*  870 */     return this.key[this.last];
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public ObjectSortedSet<K> tailSet(K from) {
/*  880 */     throw new UnsupportedOperationException();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public ObjectSortedSet<K> headSet(K to) {
/*  890 */     throw new UnsupportedOperationException();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public ObjectSortedSet<K> subSet(K from, K to) {
/*  900 */     throw new UnsupportedOperationException();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Comparator<? super K> comparator() {
/*  910 */     return null;
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
/*      */   private final class SetIterator
/*      */     implements ObjectListIterator<K>
/*      */   {
/*  925 */     int prev = -1;
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  930 */     int next = -1;
/*      */     
/*  932 */     int curr = -1;
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  937 */     int index = -1;
/*      */     
/*      */     SetIterator() {
/*  940 */       this.next = ObjectLinkedOpenHashSet.this.first;
/*  941 */       this.index = 0;
/*      */     }
/*      */     
/*      */     SetIterator(K from) {
/*  945 */       if (from == null) {
/*  946 */         if (ObjectLinkedOpenHashSet.this.containsNull) {
/*  947 */           this.next = (int)ObjectLinkedOpenHashSet.this.link[ObjectLinkedOpenHashSet.this.n];
/*  948 */           this.prev = ObjectLinkedOpenHashSet.this.n; return;
/*      */         } 
/*  950 */         throw new NoSuchElementException("The key " + from + " does not belong to this set.");
/*      */       } 
/*  952 */       if (Objects.equals(ObjectLinkedOpenHashSet.this.key[ObjectLinkedOpenHashSet.this.last], from)) {
/*  953 */         this.prev = ObjectLinkedOpenHashSet.this.last;
/*  954 */         this.index = ObjectLinkedOpenHashSet.this.size;
/*      */         
/*      */         return;
/*      */       } 
/*  958 */       K[] key = ObjectLinkedOpenHashSet.this.key;
/*  959 */       int pos = HashCommon.mix(from.hashCode()) & ObjectLinkedOpenHashSet.this.mask;
/*      */       
/*  961 */       while (key[pos] != null) {
/*  962 */         if (key[pos].equals(from)) {
/*      */           
/*  964 */           this.next = (int)ObjectLinkedOpenHashSet.this.link[pos];
/*  965 */           this.prev = pos;
/*      */           return;
/*      */         } 
/*  968 */         pos = pos + 1 & ObjectLinkedOpenHashSet.this.mask;
/*      */       } 
/*  970 */       throw new NoSuchElementException("The key " + from + " does not belong to this set.");
/*      */     }
/*      */ 
/*      */     
/*      */     public boolean hasNext() {
/*  975 */       return (this.next != -1);
/*      */     }
/*      */ 
/*      */     
/*      */     public boolean hasPrevious() {
/*  980 */       return (this.prev != -1);
/*      */     }
/*      */ 
/*      */     
/*      */     public K next() {
/*  985 */       if (!hasNext()) throw new NoSuchElementException(); 
/*  986 */       this.curr = this.next;
/*  987 */       this.next = (int)ObjectLinkedOpenHashSet.this.link[this.curr];
/*  988 */       this.prev = this.curr;
/*  989 */       if (this.index >= 0) this.index++;
/*      */       
/*  991 */       return ObjectLinkedOpenHashSet.this.key[this.curr];
/*      */     }
/*      */ 
/*      */     
/*      */     public K previous() {
/*  996 */       if (!hasPrevious()) throw new NoSuchElementException(); 
/*  997 */       this.curr = this.prev;
/*  998 */       this.prev = (int)(ObjectLinkedOpenHashSet.this.link[this.curr] >>> 32L);
/*  999 */       this.next = this.curr;
/* 1000 */       if (this.index >= 0) this.index--; 
/* 1001 */       return ObjectLinkedOpenHashSet.this.key[this.curr];
/*      */     }
/*      */ 
/*      */     
/*      */     public void forEachRemaining(Consumer<? super K> action) {
/* 1006 */       K[] key = ObjectLinkedOpenHashSet.this.key;
/* 1007 */       long[] link = ObjectLinkedOpenHashSet.this.link;
/* 1008 */       while (this.next != -1) {
/* 1009 */         this.curr = this.next;
/* 1010 */         this.next = (int)link[this.curr];
/* 1011 */         this.prev = this.curr;
/* 1012 */         if (this.index >= 0) this.index++;
/*      */         
/* 1014 */         action.accept(key[this.curr]);
/*      */       } 
/*      */     }
/*      */     
/*      */     private final void ensureIndexKnown() {
/* 1019 */       if (this.index >= 0)
/* 1020 */         return;  if (this.prev == -1) {
/* 1021 */         this.index = 0;
/*      */         return;
/*      */       } 
/* 1024 */       if (this.next == -1) {
/* 1025 */         this.index = ObjectLinkedOpenHashSet.this.size;
/*      */         return;
/*      */       } 
/* 1028 */       int pos = ObjectLinkedOpenHashSet.this.first;
/* 1029 */       this.index = 1;
/* 1030 */       while (pos != this.prev) {
/* 1031 */         pos = (int)ObjectLinkedOpenHashSet.this.link[pos];
/* 1032 */         this.index++;
/*      */       } 
/*      */     }
/*      */ 
/*      */     
/*      */     public int nextIndex() {
/* 1038 */       ensureIndexKnown();
/* 1039 */       return this.index;
/*      */     }
/*      */ 
/*      */     
/*      */     public int previousIndex() {
/* 1044 */       ensureIndexKnown();
/* 1045 */       return this.index - 1;
/*      */     }
/*      */ 
/*      */     
/*      */     public void remove() {
/* 1050 */       ensureIndexKnown();
/* 1051 */       if (this.curr == -1) throw new IllegalStateException(); 
/* 1052 */       if (this.curr == this.prev)
/*      */       
/*      */       { 
/* 1055 */         this.index--;
/* 1056 */         this.prev = (int)(ObjectLinkedOpenHashSet.this.link[this.curr] >>> 32L); }
/* 1057 */       else { this.next = (int)ObjectLinkedOpenHashSet.this.link[this.curr]; }
/* 1058 */        ObjectLinkedOpenHashSet.this.size--;
/*      */ 
/*      */       
/* 1061 */       if (this.prev == -1) { ObjectLinkedOpenHashSet.this.first = this.next; }
/* 1062 */       else { ObjectLinkedOpenHashSet.this.link[this.prev] = ObjectLinkedOpenHashSet.this.link[this.prev] ^ (ObjectLinkedOpenHashSet.this.link[this.prev] ^ this.next & 0xFFFFFFFFL) & 0xFFFFFFFFL; }
/* 1063 */        if (this.next == -1) { ObjectLinkedOpenHashSet.this.last = this.prev; }
/* 1064 */       else { ObjectLinkedOpenHashSet.this.link[this.next] = ObjectLinkedOpenHashSet.this.link[this.next] ^ (ObjectLinkedOpenHashSet.this.link[this.next] ^ (this.prev & 0xFFFFFFFFL) << 32L) & 0xFFFFFFFF00000000L; }
/* 1065 */        int pos = this.curr;
/* 1066 */       this.curr = -1;
/* 1067 */       if (pos == ObjectLinkedOpenHashSet.this.n) {
/* 1068 */         ObjectLinkedOpenHashSet.this.containsNull = false;
/* 1069 */         ObjectLinkedOpenHashSet.this.key[ObjectLinkedOpenHashSet.this.n] = null;
/*      */       } else {
/*      */         
/* 1072 */         K[] key = ObjectLinkedOpenHashSet.this.key; while (true) {
/*      */           K curr;
/*      */           int last;
/* 1075 */           pos = (last = pos) + 1 & ObjectLinkedOpenHashSet.this.mask;
/*      */           while (true) {
/* 1077 */             if ((curr = key[pos]) == null) {
/* 1078 */               key[last] = null;
/*      */               return;
/*      */             } 
/* 1081 */             int slot = HashCommon.mix(curr.hashCode()) & ObjectLinkedOpenHashSet.this.mask;
/* 1082 */             if ((last <= pos) ? (last >= slot || slot > pos) : (last >= slot && slot > pos))
/* 1083 */               break;  pos = pos + 1 & ObjectLinkedOpenHashSet.this.mask;
/*      */           } 
/* 1085 */           key[last] = curr;
/* 1086 */           if (this.next == pos) this.next = last; 
/* 1087 */           if (this.prev == pos) this.prev = last; 
/* 1088 */           ObjectLinkedOpenHashSet.this.fixPointers(pos, last);
/*      */         } 
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
/*      */   public ObjectListIterator<K> iterator(K from) {
/* 1104 */     return new SetIterator(from);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public ObjectListIterator<K> iterator() {
/* 1115 */     return new SetIterator();
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
/*      */   public ObjectSpliterator<K> spliterator() {
/* 1135 */     return ObjectSpliterators.asSpliterator(iterator(), Size64.sizeOf(this), 81);
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public void forEach(Consumer<? super K> action) {
/* 1141 */     int next = this.first;
/* 1142 */     while (next != -1) {
/* 1143 */       int curr = next;
/* 1144 */       next = (int)this.link[curr];
/*      */       
/* 1146 */       action.accept(this.key[curr]);
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
/*      */   public boolean trim() {
/* 1164 */     return trim(this.size);
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
/*      */   public boolean trim(int n) {
/* 1186 */     int l = HashCommon.nextPowerOfTwo((int)Math.ceil((n / this.f)));
/* 1187 */     if (l >= this.n || this.size > HashCommon.maxFill(l, this.f)) return true; 
/*      */     try {
/* 1189 */       rehash(l);
/* 1190 */     } catch (OutOfMemoryError cantDoIt) {
/* 1191 */       return false;
/*      */     } 
/* 1193 */     return true;
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
/*      */   protected void rehash(int newN) {
/* 1208 */     K[] key = this.key;
/* 1209 */     int mask = newN - 1;
/* 1210 */     K[] newKey = (K[])new Object[newN + 1];
/* 1211 */     int i = this.first, prev = -1, newPrev = -1;
/* 1212 */     long[] link = this.link;
/* 1213 */     long[] newLink = new long[newN + 1];
/* 1214 */     this.first = -1;
/* 1215 */     for (int j = this.size; j-- != 0; ) {
/* 1216 */       int pos; if (key[i] == null) { pos = newN; }
/*      */       else
/* 1218 */       { pos = HashCommon.mix(key[i].hashCode()) & mask;
/* 1219 */         for (; newKey[pos] != null; pos = pos + 1 & mask); }
/*      */       
/* 1221 */       newKey[pos] = key[i];
/* 1222 */       if (prev != -1) {
/* 1223 */         newLink[newPrev] = newLink[newPrev] ^ (newLink[newPrev] ^ pos & 0xFFFFFFFFL) & 0xFFFFFFFFL;
/* 1224 */         newLink[pos] = newLink[pos] ^ (newLink[pos] ^ (newPrev & 0xFFFFFFFFL) << 32L) & 0xFFFFFFFF00000000L;
/* 1225 */         newPrev = pos;
/*      */       } else {
/* 1227 */         newPrev = this.first = pos;
/*      */         
/* 1229 */         newLink[pos] = -1L;
/*      */       } 
/* 1231 */       int t = i;
/* 1232 */       i = (int)link[i];
/* 1233 */       prev = t;
/*      */     } 
/* 1235 */     this.link = newLink;
/* 1236 */     this.last = newPrev;
/* 1237 */     if (newPrev != -1)
/*      */     {
/* 1239 */       newLink[newPrev] = newLink[newPrev] | 0xFFFFFFFFL; } 
/* 1240 */     this.n = newN;
/* 1241 */     this.mask = mask;
/* 1242 */     this.maxFill = HashCommon.maxFill(this.n, this.f);
/* 1243 */     this.key = newKey;
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
/*      */   public ObjectLinkedOpenHashSet<K> clone() {
/*      */     ObjectLinkedOpenHashSet<K> c;
/*      */     try {
/* 1260 */       c = (ObjectLinkedOpenHashSet<K>)super.clone();
/* 1261 */     } catch (CloneNotSupportedException cantHappen) {
/* 1262 */       throw new InternalError();
/*      */     } 
/* 1264 */     c.key = (K[])this.key.clone();
/* 1265 */     c.containsNull = this.containsNull;
/* 1266 */     c.link = (long[])this.link.clone();
/* 1267 */     return c;
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
/*      */   public int hashCode() {
/* 1281 */     int h = 0;
/* 1282 */     for (int j = realSize(), i = 0; j-- != 0; ) {
/* 1283 */       for (; this.key[i] == null; i++);
/* 1284 */       if (this != this.key[i]) h += this.key[i].hashCode(); 
/* 1285 */       i++;
/*      */     } 
/*      */     
/* 1288 */     return h;
/*      */   }
/*      */   
/*      */   private void writeObject(ObjectOutputStream s) throws IOException {
/* 1292 */     ObjectIterator<K> i = iterator();
/* 1293 */     s.defaultWriteObject();
/* 1294 */     for (int j = this.size; j-- != 0; s.writeObject(i.next()));
/*      */   }
/*      */ 
/*      */   
/*      */   private void readObject(ObjectInputStream s) throws IOException, ClassNotFoundException {
/* 1299 */     s.defaultReadObject();
/* 1300 */     this.n = HashCommon.arraySize(this.size, this.f);
/* 1301 */     this.maxFill = HashCommon.maxFill(this.n, this.f);
/* 1302 */     this.mask = this.n - 1;
/* 1303 */     K[] key = this.key = (K[])new Object[this.n + 1];
/* 1304 */     long[] link = this.link = new long[this.n + 1];
/* 1305 */     int prev = -1;
/* 1306 */     this.first = this.last = -1;
/*      */     
/* 1308 */     for (int i = this.size; i-- != 0; ) {
/* 1309 */       int pos; K k = (K)s.readObject();
/* 1310 */       if (k == null)
/* 1311 */       { pos = this.n;
/* 1312 */         this.containsNull = true; }
/*      */       
/* 1314 */       else if (key[pos = HashCommon.mix(k.hashCode()) & this.mask] != null) { while (key[pos = pos + 1 & this.mask] != null); }
/*      */       
/* 1316 */       key[pos] = k;
/* 1317 */       if (this.first != -1) {
/* 1318 */         link[prev] = link[prev] ^ (link[prev] ^ pos & 0xFFFFFFFFL) & 0xFFFFFFFFL;
/* 1319 */         link[pos] = link[pos] ^ (link[pos] ^ (prev & 0xFFFFFFFFL) << 32L) & 0xFFFFFFFF00000000L;
/* 1320 */         prev = pos; continue;
/*      */       } 
/* 1322 */       prev = this.first = pos;
/*      */       
/* 1324 */       link[pos] = link[pos] | 0xFFFFFFFF00000000L;
/*      */     } 
/*      */     
/* 1327 */     this.last = prev;
/* 1328 */     if (prev != -1)
/*      */     {
/* 1330 */       link[prev] = link[prev] | 0xFFFFFFFFL;
/*      */     }
/*      */   }
/*      */   
/*      */   private void checkTable() {}
/*      */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\i\\unimi\dsi\fastutil\objects\ObjectLinkedOpenHashSet.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */