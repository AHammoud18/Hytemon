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
/*      */ 
/*      */ public class ReferenceLinkedOpenHashSet<K>
/*      */   extends AbstractReferenceSortedSet<K>
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
/*      */   public ReferenceLinkedOpenHashSet(int expected, float f) {
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
/*      */   public ReferenceLinkedOpenHashSet(int expected) {
/*  143 */     this(expected, 0.75F);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public ReferenceLinkedOpenHashSet() {
/*  151 */     this(16, 0.75F);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public ReferenceLinkedOpenHashSet(Collection<? extends K> c, float f) {
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
/*      */   public ReferenceLinkedOpenHashSet(Collection<? extends K> c) {
/*  172 */     this(c, 0.75F);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public ReferenceLinkedOpenHashSet(ReferenceCollection<? extends K> c, float f) {
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
/*      */   public ReferenceLinkedOpenHashSet(ReferenceCollection<? extends K> c) {
/*  193 */     this(c, 0.75F);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public ReferenceLinkedOpenHashSet(Iterator<? extends K> i, float f) {
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
/*      */   public ReferenceLinkedOpenHashSet(Iterator<? extends K> i) {
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
/*      */   public ReferenceLinkedOpenHashSet(K[] a, int offset, int length, float f) {
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
/*      */   public ReferenceLinkedOpenHashSet(K[] a, int offset, int length) {
/*  240 */     this(a, offset, length, 0.75F);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public ReferenceLinkedOpenHashSet(K[] a, float f) {
/*  250 */     this(a, 0, a.length, f);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public ReferenceLinkedOpenHashSet(K[] a) {
/*  260 */     this(a, 0.75F);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static <K> ReferenceLinkedOpenHashSet<K> of() {
/*  269 */     return new ReferenceLinkedOpenHashSet<>();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static <K> ReferenceLinkedOpenHashSet<K> of(K e) {
/*  280 */     ReferenceLinkedOpenHashSet<K> result = new ReferenceLinkedOpenHashSet<>(1, 0.75F);
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
/*      */   public static <K> ReferenceLinkedOpenHashSet<K> of(K e0, K e1) {
/*  296 */     ReferenceLinkedOpenHashSet<K> result = new ReferenceLinkedOpenHashSet<>(2, 0.75F);
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
/*      */   public static <K> ReferenceLinkedOpenHashSet<K> of(K e0, K e1, K e2) {
/*  316 */     ReferenceLinkedOpenHashSet<K> result = new ReferenceLinkedOpenHashSet<>(3, 0.75F);
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
/*      */   public static <K> ReferenceLinkedOpenHashSet<K> of(K... a) {
/*  338 */     ReferenceLinkedOpenHashSet<K> result = new ReferenceLinkedOpenHashSet<>(a.length, 0.75F);
/*  339 */     for (K element : a) {
/*  340 */       if (!result.add(element)) {
/*  341 */         throw new IllegalArgumentException("Duplicate element " + element);
/*      */       }
/*      */     } 
/*  344 */     return result;
/*      */   }
/*      */ 
/*      */   
/*      */   private ReferenceLinkedOpenHashSet<K> combine(ReferenceLinkedOpenHashSet<? extends K> toAddFrom) {
/*  349 */     addAll(toAddFrom);
/*  350 */     return this;
/*      */   }
/*      */   
/*  353 */   private static final Collector<Object, ?, ReferenceLinkedOpenHashSet<Object>> TO_SET_COLLECTOR = (Collector)Collector.of(ReferenceLinkedOpenHashSet::new, ReferenceLinkedOpenHashSet::add, ReferenceLinkedOpenHashSet::combine, new Collector.Characteristics[0]);
/*      */   
/*      */   private static final int SPLITERATOR_CHARACTERISTICS = 81;
/*      */   
/*      */   public static <K> Collector<K, ?, ReferenceLinkedOpenHashSet<K>> toSet() {
/*  358 */     return (Collector)TO_SET_COLLECTOR;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static <K> Collector<K, ?, ReferenceLinkedOpenHashSet<K>> toSetWithExpectedSize(int expectedSize) {
/*  366 */     if (expectedSize <= 16)
/*      */     {
/*      */       
/*  369 */       return toSet();
/*      */     }
/*  371 */     return (Collector)Collector.of(new ReferenceCollections.SizeDecreasingSupplier<>(expectedSize, size -> (size <= 16) ? new ReferenceLinkedOpenHashSet() : new ReferenceLinkedOpenHashSet(size)), ReferenceLinkedOpenHashSet::add, ReferenceLinkedOpenHashSet::combine, new Collector.Characteristics[0]);
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
/*  414 */       if ((curr = key[pos = HashCommon.mix(System.identityHashCode(k)) & this.mask]) != null) {
/*  415 */         if (curr == k) return false; 
/*  416 */         while ((curr = key[pos = pos + 1 & this.mask]) != null) { if (curr == k) return false;  }
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
/*      */   protected final void shiftKeys(int pos) {
/*  444 */     K[] key = this.key; while (true) {
/*      */       K curr; int last;
/*  446 */       pos = (last = pos) + 1 & this.mask;
/*      */       while (true) {
/*  448 */         if ((curr = key[pos]) == null) {
/*  449 */           key[last] = null;
/*      */           return;
/*      */         } 
/*  452 */         int slot = HashCommon.mix(System.identityHashCode(curr)) & this.mask;
/*  453 */         if ((last <= pos) ? (last >= slot || slot > pos) : (last >= slot && slot > pos))
/*  454 */           break;  pos = pos + 1 & this.mask;
/*      */       } 
/*  456 */       key[last] = curr;
/*  457 */       fixPointers(pos, last);
/*      */     } 
/*      */   }
/*      */   
/*      */   private boolean removeEntry(int pos) {
/*  462 */     this.size--;
/*  463 */     fixPointers(pos);
/*  464 */     shiftKeys(pos);
/*  465 */     if (this.n > this.minN && this.size < this.maxFill / 4 && this.n > 16) rehash(this.n / 2); 
/*  466 */     return true;
/*      */   }
/*      */   
/*      */   private boolean removeNullEntry() {
/*  470 */     this.containsNull = false;
/*  471 */     this.key[this.n] = null;
/*  472 */     this.size--;
/*  473 */     fixPointers(this.n);
/*  474 */     if (this.n > this.minN && this.size < this.maxFill / 4 && this.n > 16) rehash(this.n / 2); 
/*  475 */     return true;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean remove(Object k) {
/*  481 */     if (k == null) {
/*  482 */       if (this.containsNull) return removeNullEntry(); 
/*  483 */       return false;
/*      */     } 
/*      */     
/*  486 */     K[] key = this.key;
/*      */     K curr;
/*      */     int pos;
/*  489 */     if ((curr = key[pos = HashCommon.mix(System.identityHashCode(k)) & this.mask]) == null) return false; 
/*  490 */     if (k == curr) return removeEntry(pos); 
/*      */     while (true) {
/*  492 */       if ((curr = key[pos = pos + 1 & this.mask]) == null) return false; 
/*  493 */       if (k == curr) return removeEntry(pos);
/*      */     
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   public boolean contains(Object k) {
/*  500 */     if (k == null) return this.containsNull;
/*      */     
/*  502 */     K[] key = this.key;
/*      */     K curr;
/*      */     int pos;
/*  505 */     if ((curr = key[pos = HashCommon.mix(System.identityHashCode(k)) & this.mask]) == null) return false; 
/*  506 */     if (k == curr) return true; 
/*      */     while (true) {
/*  508 */       if ((curr = key[pos = pos + 1 & this.mask]) == null) return false; 
/*  509 */       if (k == curr) return true;
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
/*  520 */     if (this.size == 0) throw new NoSuchElementException(); 
/*  521 */     int pos = this.first;
/*      */     
/*  523 */     if (this.size == 1) { this.first = this.last = -1; }
/*      */     else
/*  525 */     { this.first = (int)this.link[pos];
/*  526 */       if (0 <= this.first)
/*      */       {
/*  528 */         this.link[this.first] = this.link[this.first] | 0xFFFFFFFF00000000L;
/*      */       } }
/*      */     
/*  531 */     K k = this.key[pos];
/*  532 */     this.size--;
/*  533 */     if (k == null)
/*  534 */     { this.containsNull = false;
/*  535 */       this.key[this.n] = null; }
/*  536 */     else { shiftKeys(pos); }
/*  537 */      if (this.n > this.minN && this.size < this.maxFill / 4 && this.n > 16) rehash(this.n / 2); 
/*  538 */     return k;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public K removeLast() {
/*  548 */     if (this.size == 0) throw new NoSuchElementException(); 
/*  549 */     int pos = this.last;
/*      */     
/*  551 */     if (this.size == 1) { this.first = this.last = -1; }
/*      */     else
/*  553 */     { this.last = (int)(this.link[pos] >>> 32L);
/*  554 */       if (0 <= this.last)
/*      */       {
/*  556 */         this.link[this.last] = this.link[this.last] | 0xFFFFFFFFL;
/*      */       } }
/*      */     
/*  559 */     K k = this.key[pos];
/*  560 */     this.size--;
/*  561 */     if (k == null)
/*  562 */     { this.containsNull = false;
/*  563 */       this.key[this.n] = null; }
/*  564 */     else { shiftKeys(pos); }
/*  565 */      if (this.n > this.minN && this.size < this.maxFill / 4 && this.n > 16) rehash(this.n / 2); 
/*  566 */     return k;
/*      */   }
/*      */   
/*      */   private void moveIndexToFirst(int i) {
/*  570 */     if (this.size == 1 || this.first == i)
/*  571 */       return;  if (this.last == i) {
/*  572 */       this.last = (int)(this.link[i] >>> 32L);
/*      */       
/*  574 */       this.link[this.last] = this.link[this.last] | 0xFFFFFFFFL;
/*      */     } else {
/*  576 */       long linki = this.link[i];
/*  577 */       int prev = (int)(linki >>> 32L);
/*  578 */       int next = (int)linki;
/*  579 */       this.link[prev] = this.link[prev] ^ (this.link[prev] ^ linki & 0xFFFFFFFFL) & 0xFFFFFFFFL;
/*  580 */       this.link[next] = this.link[next] ^ (this.link[next] ^ linki & 0xFFFFFFFF00000000L) & 0xFFFFFFFF00000000L;
/*      */     } 
/*  582 */     this.link[this.first] = this.link[this.first] ^ (this.link[this.first] ^ (i & 0xFFFFFFFFL) << 32L) & 0xFFFFFFFF00000000L;
/*  583 */     this.link[i] = 0xFFFFFFFF00000000L | this.first & 0xFFFFFFFFL;
/*  584 */     this.first = i;
/*      */   }
/*      */   
/*      */   private void moveIndexToLast(int i) {
/*  588 */     if (this.size == 1 || this.last == i)
/*  589 */       return;  if (this.first == i) {
/*  590 */       this.first = (int)this.link[i];
/*      */       
/*  592 */       this.link[this.first] = this.link[this.first] | 0xFFFFFFFF00000000L;
/*      */     } else {
/*  594 */       long linki = this.link[i];
/*  595 */       int prev = (int)(linki >>> 32L);
/*  596 */       int next = (int)linki;
/*  597 */       this.link[prev] = this.link[prev] ^ (this.link[prev] ^ linki & 0xFFFFFFFFL) & 0xFFFFFFFFL;
/*  598 */       this.link[next] = this.link[next] ^ (this.link[next] ^ linki & 0xFFFFFFFF00000000L) & 0xFFFFFFFF00000000L;
/*      */     } 
/*  600 */     this.link[this.last] = this.link[this.last] ^ (this.link[this.last] ^ i & 0xFFFFFFFFL) & 0xFFFFFFFFL;
/*  601 */     this.link[i] = (this.last & 0xFFFFFFFFL) << 32L | 0xFFFFFFFFL;
/*  602 */     this.last = i;
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
/*  614 */     if (k == null) {
/*  615 */       if (this.containsNull) {
/*  616 */         moveIndexToFirst(this.n);
/*  617 */         return false;
/*      */       } 
/*  619 */       this.containsNull = true;
/*  620 */       pos = this.n;
/*      */     } else {
/*      */       
/*  623 */       K[] key = this.key;
/*  624 */       pos = HashCommon.mix(System.identityHashCode(k)) & this.mask;
/*      */       
/*  626 */       while (key[pos] != null) {
/*  627 */         if (k == key[pos]) {
/*  628 */           moveIndexToFirst(pos);
/*  629 */           return false;
/*      */         } 
/*  631 */         pos = pos + 1 & this.mask;
/*      */       } 
/*      */     } 
/*  634 */     this.key[pos] = k;
/*  635 */     if (this.size == 0) {
/*  636 */       this.first = this.last = pos;
/*      */       
/*  638 */       this.link[pos] = -1L;
/*      */     } else {
/*  640 */       this.link[this.first] = this.link[this.first] ^ (this.link[this.first] ^ (pos & 0xFFFFFFFFL) << 32L) & 0xFFFFFFFF00000000L;
/*  641 */       this.link[pos] = 0xFFFFFFFF00000000L | this.first & 0xFFFFFFFFL;
/*  642 */       this.first = pos;
/*      */     } 
/*  644 */     if (this.size++ >= this.maxFill) rehash(HashCommon.arraySize(this.size, this.f));
/*      */     
/*  646 */     return true;
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
/*  658 */     if (k == null) {
/*  659 */       if (this.containsNull) {
/*  660 */         moveIndexToLast(this.n);
/*  661 */         return false;
/*      */       } 
/*  663 */       this.containsNull = true;
/*  664 */       pos = this.n;
/*      */     } else {
/*      */       
/*  667 */       K[] key = this.key;
/*  668 */       pos = HashCommon.mix(System.identityHashCode(k)) & this.mask;
/*      */       
/*  670 */       while (key[pos] != null) {
/*  671 */         if (k == key[pos]) {
/*  672 */           moveIndexToLast(pos);
/*  673 */           return false;
/*      */         } 
/*  675 */         pos = pos + 1 & this.mask;
/*      */       } 
/*      */     } 
/*  678 */     this.key[pos] = k;
/*  679 */     if (this.size == 0) {
/*  680 */       this.first = this.last = pos;
/*      */       
/*  682 */       this.link[pos] = -1L;
/*      */     } else {
/*  684 */       this.link[this.last] = this.link[this.last] ^ (this.link[this.last] ^ pos & 0xFFFFFFFFL) & 0xFFFFFFFFL;
/*  685 */       this.link[pos] = (this.last & 0xFFFFFFFFL) << 32L | 0xFFFFFFFFL;
/*  686 */       this.last = pos;
/*      */     } 
/*  688 */     if (this.size++ >= this.maxFill) rehash(HashCommon.arraySize(this.size, this.f));
/*      */     
/*  690 */     return true;
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
/*  701 */     if (this.size == 0)
/*  702 */       return;  this.size = 0;
/*  703 */     this.containsNull = false;
/*  704 */     Arrays.fill((Object[])this.key, (Object)null);
/*  705 */     this.first = this.last = -1;
/*      */   }
/*      */ 
/*      */   
/*      */   public int size() {
/*  710 */     return this.size;
/*      */   }
/*      */ 
/*      */   
/*      */   public boolean isEmpty() {
/*  715 */     return (this.size == 0);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected void fixPointers(int i) {
/*  725 */     if (this.size == 0) {
/*  726 */       this.first = this.last = -1;
/*      */       return;
/*      */     } 
/*  729 */     if (this.first == i) {
/*  730 */       this.first = (int)this.link[i];
/*  731 */       if (0 <= this.first)
/*      */       {
/*  733 */         this.link[this.first] = this.link[this.first] | 0xFFFFFFFF00000000L;
/*      */       }
/*      */       return;
/*      */     } 
/*  737 */     if (this.last == i) {
/*  738 */       this.last = (int)(this.link[i] >>> 32L);
/*  739 */       if (0 <= this.last)
/*      */       {
/*  741 */         this.link[this.last] = this.link[this.last] | 0xFFFFFFFFL;
/*      */       }
/*      */       return;
/*      */     } 
/*  745 */     long linki = this.link[i];
/*  746 */     int prev = (int)(linki >>> 32L);
/*  747 */     int next = (int)linki;
/*  748 */     this.link[prev] = this.link[prev] ^ (this.link[prev] ^ linki & 0xFFFFFFFFL) & 0xFFFFFFFFL;
/*  749 */     this.link[next] = this.link[next] ^ (this.link[next] ^ linki & 0xFFFFFFFF00000000L) & 0xFFFFFFFF00000000L;
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
/*  760 */     if (this.size == 1) {
/*  761 */       this.first = this.last = d;
/*      */       
/*  763 */       this.link[d] = -1L;
/*      */       return;
/*      */     } 
/*  766 */     if (this.first == s) {
/*  767 */       this.first = d;
/*  768 */       this.link[(int)this.link[s]] = this.link[(int)this.link[s]] ^ (this.link[(int)this.link[s]] ^ (d & 0xFFFFFFFFL) << 32L) & 0xFFFFFFFF00000000L;
/*  769 */       this.link[d] = this.link[s];
/*      */       return;
/*      */     } 
/*  772 */     if (this.last == s) {
/*  773 */       this.last = d;
/*  774 */       this.link[(int)(this.link[s] >>> 32L)] = this.link[(int)(this.link[s] >>> 32L)] ^ (this.link[(int)(this.link[s] >>> 32L)] ^ d & 0xFFFFFFFFL) & 0xFFFFFFFFL;
/*  775 */       this.link[d] = this.link[s];
/*      */       return;
/*      */     } 
/*  778 */     long links = this.link[s];
/*  779 */     int prev = (int)(links >>> 32L);
/*  780 */     int next = (int)links;
/*  781 */     this.link[prev] = this.link[prev] ^ (this.link[prev] ^ d & 0xFFFFFFFFL) & 0xFFFFFFFFL;
/*  782 */     this.link[next] = this.link[next] ^ (this.link[next] ^ (d & 0xFFFFFFFFL) << 32L) & 0xFFFFFFFF00000000L;
/*  783 */     this.link[d] = links;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public K first() {
/*  793 */     if (this.size == 0) throw new NoSuchElementException(); 
/*  794 */     return this.key[this.first];
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public K last() {
/*  804 */     if (this.size == 0) throw new NoSuchElementException(); 
/*  805 */     return this.key[this.last];
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public ReferenceSortedSet<K> tailSet(K from) {
/*  815 */     throw new UnsupportedOperationException();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public ReferenceSortedSet<K> headSet(K to) {
/*  825 */     throw new UnsupportedOperationException();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public ReferenceSortedSet<K> subSet(K from, K to) {
/*  835 */     throw new UnsupportedOperationException();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Comparator<? super K> comparator() {
/*  845 */     return null;
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
/*  860 */     int prev = -1;
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  865 */     int next = -1;
/*      */     
/*  867 */     int curr = -1;
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  872 */     int index = -1;
/*      */     
/*      */     SetIterator() {
/*  875 */       this.next = ReferenceLinkedOpenHashSet.this.first;
/*  876 */       this.index = 0;
/*      */     }
/*      */     
/*      */     SetIterator(K from) {
/*  880 */       if (from == null) {
/*  881 */         if (ReferenceLinkedOpenHashSet.this.containsNull) {
/*  882 */           this.next = (int)ReferenceLinkedOpenHashSet.this.link[ReferenceLinkedOpenHashSet.this.n];
/*  883 */           this.prev = ReferenceLinkedOpenHashSet.this.n; return;
/*      */         } 
/*  885 */         throw new NoSuchElementException("The key " + from + " does not belong to this set.");
/*      */       } 
/*  887 */       if (ReferenceLinkedOpenHashSet.this.key[ReferenceLinkedOpenHashSet.this.last] == from) {
/*  888 */         this.prev = ReferenceLinkedOpenHashSet.this.last;
/*  889 */         this.index = ReferenceLinkedOpenHashSet.this.size;
/*      */         
/*      */         return;
/*      */       } 
/*  893 */       K[] key = ReferenceLinkedOpenHashSet.this.key;
/*  894 */       int pos = HashCommon.mix(System.identityHashCode(from)) & ReferenceLinkedOpenHashSet.this.mask;
/*      */       
/*  896 */       while (key[pos] != null) {
/*  897 */         if (key[pos] == from) {
/*      */           
/*  899 */           this.next = (int)ReferenceLinkedOpenHashSet.this.link[pos];
/*  900 */           this.prev = pos;
/*      */           return;
/*      */         } 
/*  903 */         pos = pos + 1 & ReferenceLinkedOpenHashSet.this.mask;
/*      */       } 
/*  905 */       throw new NoSuchElementException("The key " + from + " does not belong to this set.");
/*      */     }
/*      */ 
/*      */     
/*      */     public boolean hasNext() {
/*  910 */       return (this.next != -1);
/*      */     }
/*      */ 
/*      */     
/*      */     public boolean hasPrevious() {
/*  915 */       return (this.prev != -1);
/*      */     }
/*      */ 
/*      */     
/*      */     public K next() {
/*  920 */       if (!hasNext()) throw new NoSuchElementException(); 
/*  921 */       this.curr = this.next;
/*  922 */       this.next = (int)ReferenceLinkedOpenHashSet.this.link[this.curr];
/*  923 */       this.prev = this.curr;
/*  924 */       if (this.index >= 0) this.index++;
/*      */       
/*  926 */       return ReferenceLinkedOpenHashSet.this.key[this.curr];
/*      */     }
/*      */ 
/*      */     
/*      */     public K previous() {
/*  931 */       if (!hasPrevious()) throw new NoSuchElementException(); 
/*  932 */       this.curr = this.prev;
/*  933 */       this.prev = (int)(ReferenceLinkedOpenHashSet.this.link[this.curr] >>> 32L);
/*  934 */       this.next = this.curr;
/*  935 */       if (this.index >= 0) this.index--; 
/*  936 */       return ReferenceLinkedOpenHashSet.this.key[this.curr];
/*      */     }
/*      */ 
/*      */     
/*      */     public void forEachRemaining(Consumer<? super K> action) {
/*  941 */       K[] key = ReferenceLinkedOpenHashSet.this.key;
/*  942 */       long[] link = ReferenceLinkedOpenHashSet.this.link;
/*  943 */       while (this.next != -1) {
/*  944 */         this.curr = this.next;
/*  945 */         this.next = (int)link[this.curr];
/*  946 */         this.prev = this.curr;
/*  947 */         if (this.index >= 0) this.index++;
/*      */         
/*  949 */         action.accept(key[this.curr]);
/*      */       } 
/*      */     }
/*      */     
/*      */     private final void ensureIndexKnown() {
/*  954 */       if (this.index >= 0)
/*  955 */         return;  if (this.prev == -1) {
/*  956 */         this.index = 0;
/*      */         return;
/*      */       } 
/*  959 */       if (this.next == -1) {
/*  960 */         this.index = ReferenceLinkedOpenHashSet.this.size;
/*      */         return;
/*      */       } 
/*  963 */       int pos = ReferenceLinkedOpenHashSet.this.first;
/*  964 */       this.index = 1;
/*  965 */       while (pos != this.prev) {
/*  966 */         pos = (int)ReferenceLinkedOpenHashSet.this.link[pos];
/*  967 */         this.index++;
/*      */       } 
/*      */     }
/*      */ 
/*      */     
/*      */     public int nextIndex() {
/*  973 */       ensureIndexKnown();
/*  974 */       return this.index;
/*      */     }
/*      */ 
/*      */     
/*      */     public int previousIndex() {
/*  979 */       ensureIndexKnown();
/*  980 */       return this.index - 1;
/*      */     }
/*      */ 
/*      */     
/*      */     public void remove() {
/*  985 */       ensureIndexKnown();
/*  986 */       if (this.curr == -1) throw new IllegalStateException(); 
/*  987 */       if (this.curr == this.prev)
/*      */       
/*      */       { 
/*  990 */         this.index--;
/*  991 */         this.prev = (int)(ReferenceLinkedOpenHashSet.this.link[this.curr] >>> 32L); }
/*  992 */       else { this.next = (int)ReferenceLinkedOpenHashSet.this.link[this.curr]; }
/*  993 */        ReferenceLinkedOpenHashSet.this.size--;
/*      */ 
/*      */       
/*  996 */       if (this.prev == -1) { ReferenceLinkedOpenHashSet.this.first = this.next; }
/*  997 */       else { ReferenceLinkedOpenHashSet.this.link[this.prev] = ReferenceLinkedOpenHashSet.this.link[this.prev] ^ (ReferenceLinkedOpenHashSet.this.link[this.prev] ^ this.next & 0xFFFFFFFFL) & 0xFFFFFFFFL; }
/*  998 */        if (this.next == -1) { ReferenceLinkedOpenHashSet.this.last = this.prev; }
/*  999 */       else { ReferenceLinkedOpenHashSet.this.link[this.next] = ReferenceLinkedOpenHashSet.this.link[this.next] ^ (ReferenceLinkedOpenHashSet.this.link[this.next] ^ (this.prev & 0xFFFFFFFFL) << 32L) & 0xFFFFFFFF00000000L; }
/* 1000 */        int pos = this.curr;
/* 1001 */       this.curr = -1;
/* 1002 */       if (pos == ReferenceLinkedOpenHashSet.this.n) {
/* 1003 */         ReferenceLinkedOpenHashSet.this.containsNull = false;
/* 1004 */         ReferenceLinkedOpenHashSet.this.key[ReferenceLinkedOpenHashSet.this.n] = null;
/*      */       } else {
/*      */         
/* 1007 */         K[] key = ReferenceLinkedOpenHashSet.this.key; while (true) {
/*      */           K curr;
/*      */           int last;
/* 1010 */           pos = (last = pos) + 1 & ReferenceLinkedOpenHashSet.this.mask;
/*      */           while (true) {
/* 1012 */             if ((curr = key[pos]) == null) {
/* 1013 */               key[last] = null;
/*      */               return;
/*      */             } 
/* 1016 */             int slot = HashCommon.mix(System.identityHashCode(curr)) & ReferenceLinkedOpenHashSet.this.mask;
/* 1017 */             if ((last <= pos) ? (last >= slot || slot > pos) : (last >= slot && slot > pos))
/* 1018 */               break;  pos = pos + 1 & ReferenceLinkedOpenHashSet.this.mask;
/*      */           } 
/* 1020 */           key[last] = curr;
/* 1021 */           if (this.next == pos) this.next = last; 
/* 1022 */           if (this.prev == pos) this.prev = last; 
/* 1023 */           ReferenceLinkedOpenHashSet.this.fixPointers(pos, last);
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
/* 1039 */     return new SetIterator(from);
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
/* 1050 */     return new SetIterator();
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
/* 1070 */     return ObjectSpliterators.asSpliterator(iterator(), Size64.sizeOf(this), 81);
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public void forEach(Consumer<? super K> action) {
/* 1076 */     int next = this.first;
/* 1077 */     while (next != -1) {
/* 1078 */       int curr = next;
/* 1079 */       next = (int)this.link[curr];
/*      */       
/* 1081 */       action.accept(this.key[curr]);
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
/* 1099 */     return trim(this.size);
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
/* 1121 */     int l = HashCommon.nextPowerOfTwo((int)Math.ceil((n / this.f)));
/* 1122 */     if (l >= this.n || this.size > HashCommon.maxFill(l, this.f)) return true; 
/*      */     try {
/* 1124 */       rehash(l);
/* 1125 */     } catch (OutOfMemoryError cantDoIt) {
/* 1126 */       return false;
/*      */     } 
/* 1128 */     return true;
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
/* 1143 */     K[] key = this.key;
/* 1144 */     int mask = newN - 1;
/* 1145 */     K[] newKey = (K[])new Object[newN + 1];
/* 1146 */     int i = this.first, prev = -1, newPrev = -1;
/* 1147 */     long[] link = this.link;
/* 1148 */     long[] newLink = new long[newN + 1];
/* 1149 */     this.first = -1;
/* 1150 */     for (int j = this.size; j-- != 0; ) {
/* 1151 */       int pos; if (key[i] == null) { pos = newN; }
/*      */       else
/* 1153 */       { pos = HashCommon.mix(System.identityHashCode(key[i])) & mask;
/* 1154 */         for (; newKey[pos] != null; pos = pos + 1 & mask); }
/*      */       
/* 1156 */       newKey[pos] = key[i];
/* 1157 */       if (prev != -1) {
/* 1158 */         newLink[newPrev] = newLink[newPrev] ^ (newLink[newPrev] ^ pos & 0xFFFFFFFFL) & 0xFFFFFFFFL;
/* 1159 */         newLink[pos] = newLink[pos] ^ (newLink[pos] ^ (newPrev & 0xFFFFFFFFL) << 32L) & 0xFFFFFFFF00000000L;
/* 1160 */         newPrev = pos;
/*      */       } else {
/* 1162 */         newPrev = this.first = pos;
/*      */         
/* 1164 */         newLink[pos] = -1L;
/*      */       } 
/* 1166 */       int t = i;
/* 1167 */       i = (int)link[i];
/* 1168 */       prev = t;
/*      */     } 
/* 1170 */     this.link = newLink;
/* 1171 */     this.last = newPrev;
/* 1172 */     if (newPrev != -1)
/*      */     {
/* 1174 */       newLink[newPrev] = newLink[newPrev] | 0xFFFFFFFFL; } 
/* 1175 */     this.n = newN;
/* 1176 */     this.mask = mask;
/* 1177 */     this.maxFill = HashCommon.maxFill(this.n, this.f);
/* 1178 */     this.key = newKey;
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
/*      */   public ReferenceLinkedOpenHashSet<K> clone() {
/*      */     ReferenceLinkedOpenHashSet<K> c;
/*      */     try {
/* 1195 */       c = (ReferenceLinkedOpenHashSet<K>)super.clone();
/* 1196 */     } catch (CloneNotSupportedException cantHappen) {
/* 1197 */       throw new InternalError();
/*      */     } 
/* 1199 */     c.key = (K[])this.key.clone();
/* 1200 */     c.containsNull = this.containsNull;
/* 1201 */     c.link = (long[])this.link.clone();
/* 1202 */     return c;
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
/* 1216 */     int h = 0;
/* 1217 */     for (int j = realSize(), i = 0; j-- != 0; ) {
/* 1218 */       for (; this.key[i] == null; i++);
/* 1219 */       if (this != this.key[i]) h += System.identityHashCode(this.key[i]); 
/* 1220 */       i++;
/*      */     } 
/*      */     
/* 1223 */     return h;
/*      */   }
/*      */   
/*      */   private void writeObject(ObjectOutputStream s) throws IOException {
/* 1227 */     ObjectIterator<K> i = iterator();
/* 1228 */     s.defaultWriteObject();
/* 1229 */     for (int j = this.size; j-- != 0; s.writeObject(i.next()));
/*      */   }
/*      */ 
/*      */   
/*      */   private void readObject(ObjectInputStream s) throws IOException, ClassNotFoundException {
/* 1234 */     s.defaultReadObject();
/* 1235 */     this.n = HashCommon.arraySize(this.size, this.f);
/* 1236 */     this.maxFill = HashCommon.maxFill(this.n, this.f);
/* 1237 */     this.mask = this.n - 1;
/* 1238 */     K[] key = this.key = (K[])new Object[this.n + 1];
/* 1239 */     long[] link = this.link = new long[this.n + 1];
/* 1240 */     int prev = -1;
/* 1241 */     this.first = this.last = -1;
/*      */     
/* 1243 */     for (int i = this.size; i-- != 0; ) {
/* 1244 */       int pos; K k = (K)s.readObject();
/* 1245 */       if (k == null)
/* 1246 */       { pos = this.n;
/* 1247 */         this.containsNull = true; }
/*      */       
/* 1249 */       else if (key[pos = HashCommon.mix(System.identityHashCode(k)) & this.mask] != null) { while (key[pos = pos + 1 & this.mask] != null); }
/*      */       
/* 1251 */       key[pos] = k;
/* 1252 */       if (this.first != -1) {
/* 1253 */         link[prev] = link[prev] ^ (link[prev] ^ pos & 0xFFFFFFFFL) & 0xFFFFFFFFL;
/* 1254 */         link[pos] = link[pos] ^ (link[pos] ^ (prev & 0xFFFFFFFFL) << 32L) & 0xFFFFFFFF00000000L;
/* 1255 */         prev = pos; continue;
/*      */       } 
/* 1257 */       prev = this.first = pos;
/*      */       
/* 1259 */       link[pos] = link[pos] | 0xFFFFFFFF00000000L;
/*      */     } 
/*      */     
/* 1262 */     this.last = prev;
/* 1263 */     if (prev != -1)
/*      */     {
/* 1265 */       link[prev] = link[prev] | 0xFFFFFFFFL;
/*      */     }
/*      */   }
/*      */   
/*      */   private void checkTable() {}
/*      */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\i\\unimi\dsi\fastutil\objects\ReferenceLinkedOpenHashSet.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */