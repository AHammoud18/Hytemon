/*      */ package it.unimi.dsi.fastutil.ints;
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
/*      */ import java.util.Spliterator;
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
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ public class IntLinkedOpenHashSet
/*      */   extends AbstractIntSortedSet
/*      */   implements Serializable, Cloneable, Hash
/*      */ {
/*      */   private static final long serialVersionUID = 0L;
/*      */   private static final boolean ASSERTS = false;
/*      */   protected transient int[] key;
/*      */   protected transient int mask;
/*      */   protected transient boolean containsNull;
/*   88 */   protected transient int first = -1;
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*   93 */   protected transient int last = -1;
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
/*      */   protected final float f;
/*      */ 
/*      */   
/*      */   private static final int SPLITERATOR_CHARACTERISTICS = 337;
/*      */ 
/*      */ 
/*      */   
/*      */   public IntLinkedOpenHashSet(int expected, float f) {
/*  124 */     if (f <= 0.0F || f >= 1.0F) throw new IllegalArgumentException("Load factor must be greater than 0 and smaller than 1"); 
/*  125 */     if (expected < 0) throw new IllegalArgumentException("The expected number of elements must be nonnegative"); 
/*  126 */     this.f = f;
/*  127 */     this.minN = this.n = HashCommon.arraySize(expected, f);
/*  128 */     this.mask = this.n - 1;
/*  129 */     this.maxFill = HashCommon.maxFill(this.n, f);
/*  130 */     this.key = new int[this.n + 1];
/*  131 */     this.link = new long[this.n + 1];
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public IntLinkedOpenHashSet(int expected) {
/*  140 */     this(expected, 0.75F);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public IntLinkedOpenHashSet() {
/*  148 */     this(16, 0.75F);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public IntLinkedOpenHashSet(Collection<? extends Integer> c, float f) {
/*  158 */     this(c.size(), f);
/*  159 */     addAll(c);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public IntLinkedOpenHashSet(Collection<? extends Integer> c) {
/*  169 */     this(c, 0.75F);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public IntLinkedOpenHashSet(IntCollection c, float f) {
/*  179 */     this(c.size(), f);
/*  180 */     addAll(c);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public IntLinkedOpenHashSet(IntCollection c) {
/*  190 */     this(c, 0.75F);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public IntLinkedOpenHashSet(IntIterator i, float f) {
/*  200 */     this(16, f);
/*  201 */     for (; i.hasNext(); add(i.nextInt()));
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public IntLinkedOpenHashSet(IntIterator i) {
/*  211 */     this(i, 0.75F);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public IntLinkedOpenHashSet(Iterator<?> i, float f) {
/*  221 */     this(IntIterators.asIntIterator(i), f);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public IntLinkedOpenHashSet(Iterator<?> i) {
/*  231 */     this(IntIterators.asIntIterator(i));
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
/*      */   public IntLinkedOpenHashSet(int[] a, int offset, int length, float f) {
/*  243 */     this((length < 0) ? 0 : length, f);
/*  244 */     IntArrays.ensureOffsetLength(a, offset, length);
/*  245 */     for (int i = 0; i < length; ) { add(a[offset + i]); i++; }
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
/*      */   public IntLinkedOpenHashSet(int[] a, int offset, int length) {
/*  257 */     this(a, offset, length, 0.75F);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public IntLinkedOpenHashSet(int[] a, float f) {
/*  267 */     this(a, 0, a.length, f);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public IntLinkedOpenHashSet(int[] a) {
/*  277 */     this(a, 0.75F);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static IntLinkedOpenHashSet of() {
/*  286 */     return new IntLinkedOpenHashSet();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static IntLinkedOpenHashSet of(int e) {
/*  297 */     IntLinkedOpenHashSet result = new IntLinkedOpenHashSet(1, 0.75F);
/*  298 */     result.add(e);
/*  299 */     return result;
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
/*      */   public static IntLinkedOpenHashSet of(int e0, int e1) {
/*  313 */     IntLinkedOpenHashSet result = new IntLinkedOpenHashSet(2, 0.75F);
/*  314 */     result.add(e0);
/*  315 */     if (!result.add(e1)) {
/*  316 */       throw new IllegalArgumentException("Duplicate element: " + e1);
/*      */     }
/*  318 */     return result;
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
/*      */   public static IntLinkedOpenHashSet of(int e0, int e1, int e2) {
/*  333 */     IntLinkedOpenHashSet result = new IntLinkedOpenHashSet(3, 0.75F);
/*  334 */     result.add(e0);
/*  335 */     if (!result.add(e1)) {
/*  336 */       throw new IllegalArgumentException("Duplicate element: " + e1);
/*      */     }
/*  338 */     if (!result.add(e2)) {
/*  339 */       throw new IllegalArgumentException("Duplicate element: " + e2);
/*      */     }
/*  341 */     return result;
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
/*      */   public static IntLinkedOpenHashSet of(int... a) {
/*  355 */     IntLinkedOpenHashSet result = new IntLinkedOpenHashSet(a.length, 0.75F);
/*  356 */     for (int element : a) {
/*  357 */       if (!result.add(element)) {
/*  358 */         throw new IllegalArgumentException("Duplicate element " + element);
/*      */       }
/*      */     } 
/*  361 */     return result;
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
/*      */   public static IntLinkedOpenHashSet toSet(IntStream stream) {
/*  375 */     return stream.<IntLinkedOpenHashSet>collect(IntLinkedOpenHashSet::new, IntLinkedOpenHashSet::add, IntLinkedOpenHashSet::addAll);
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
/*      */   public static IntLinkedOpenHashSet toSetWithExpectedSize(IntStream stream, int expectedSize) {
/*  390 */     if (expectedSize <= 16)
/*      */     {
/*      */       
/*  393 */       return toSet(stream);
/*      */     }
/*  395 */     return stream.<IntLinkedOpenHashSet>collect(new IntCollections.SizeDecreasingSupplier<>(expectedSize, size -> (size <= 16) ? new IntLinkedOpenHashSet() : new IntLinkedOpenHashSet(size)), IntLinkedOpenHashSet::add, IntLinkedOpenHashSet::addAll);
/*      */   }
/*      */   
/*      */   private int realSize() {
/*  399 */     return this.containsNull ? (this.size - 1) : this.size;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void ensureCapacity(int capacity) {
/*  409 */     int needed = HashCommon.arraySize(capacity, this.f);
/*  410 */     if (needed > this.n) rehash(needed); 
/*      */   }
/*      */   
/*      */   private void tryCapacity(long capacity) {
/*  414 */     int needed = (int)Math.min(1073741824L, Math.max(2L, HashCommon.nextPowerOfTwo((long)Math.ceil(((float)capacity / this.f)))));
/*  415 */     if (needed > this.n) rehash(needed);
/*      */   
/*      */   }
/*      */   
/*      */   public boolean addAll(IntCollection c) {
/*  420 */     if (this.f <= 0.5D) { ensureCapacity(c.size()); }
/*  421 */     else { tryCapacity((size() + c.size())); }
/*      */     
/*  423 */     return super.addAll(c);
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean addAll(Collection<? extends Integer> c) {
/*  429 */     if (this.f <= 0.5D) { ensureCapacity(c.size()); }
/*  430 */     else { tryCapacity((size() + c.size())); }
/*      */     
/*  432 */     return super.addAll(c);
/*      */   }
/*      */ 
/*      */   
/*      */   public boolean add(int k) {
/*      */     int pos;
/*  438 */     if (k == 0) {
/*  439 */       if (this.containsNull) return false; 
/*  440 */       pos = this.n;
/*  441 */       this.containsNull = true;
/*      */     } else {
/*      */       
/*  444 */       int[] key = this.key;
/*      */       int curr;
/*  446 */       if ((curr = key[pos = HashCommon.mix(k) & this.mask]) != 0) {
/*  447 */         if (curr == k) return false; 
/*  448 */         while ((curr = key[pos = pos + 1 & this.mask]) != 0) { if (curr == k) return false;  }
/*      */       
/*  450 */       }  key[pos] = k;
/*      */     } 
/*  452 */     if (this.size == 0) {
/*  453 */       this.first = this.last = pos;
/*      */       
/*  455 */       this.link[pos] = -1L;
/*      */     } else {
/*  457 */       this.link[this.last] = this.link[this.last] ^ (this.link[this.last] ^ pos & 0xFFFFFFFFL) & 0xFFFFFFFFL;
/*  458 */       this.link[pos] = (this.last & 0xFFFFFFFFL) << 32L | 0xFFFFFFFFL;
/*  459 */       this.last = pos;
/*      */     } 
/*  461 */     if (this.size++ >= this.maxFill) rehash(HashCommon.arraySize(this.size + 1, this.f));
/*      */     
/*  463 */     return true;
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
/*  476 */     int[] key = this.key; while (true) {
/*      */       int curr, last;
/*  478 */       pos = (last = pos) + 1 & this.mask;
/*      */       while (true) {
/*  480 */         if ((curr = key[pos]) == 0) {
/*  481 */           key[last] = 0;
/*      */           return;
/*      */         } 
/*  484 */         int slot = HashCommon.mix(curr) & this.mask;
/*  485 */         if ((last <= pos) ? (last >= slot || slot > pos) : (last >= slot && slot > pos))
/*  486 */           break;  pos = pos + 1 & this.mask;
/*      */       } 
/*  488 */       key[last] = curr;
/*  489 */       fixPointers(pos, last);
/*      */     } 
/*      */   }
/*      */   
/*      */   private boolean removeEntry(int pos) {
/*  494 */     this.size--;
/*  495 */     fixPointers(pos);
/*  496 */     shiftKeys(pos);
/*  497 */     if (this.n > this.minN && this.size < this.maxFill / 4 && this.n > 16) rehash(this.n / 2); 
/*  498 */     return true;
/*      */   }
/*      */   
/*      */   private boolean removeNullEntry() {
/*  502 */     this.containsNull = false;
/*  503 */     this.key[this.n] = 0;
/*  504 */     this.size--;
/*  505 */     fixPointers(this.n);
/*  506 */     if (this.n > this.minN && this.size < this.maxFill / 4 && this.n > 16) rehash(this.n / 2); 
/*  507 */     return true;
/*      */   }
/*      */ 
/*      */   
/*      */   public boolean remove(int k) {
/*  512 */     if (k == 0) {
/*  513 */       if (this.containsNull) return removeNullEntry(); 
/*  514 */       return false;
/*      */     } 
/*      */     
/*  517 */     int[] key = this.key;
/*      */     
/*      */     int curr, pos;
/*  520 */     if ((curr = key[pos = HashCommon.mix(k) & this.mask]) == 0) return false; 
/*  521 */     if (k == curr) return removeEntry(pos); 
/*      */     while (true) {
/*  523 */       if ((curr = key[pos = pos + 1 & this.mask]) == 0) return false; 
/*  524 */       if (k == curr) return removeEntry(pos);
/*      */     
/*      */     } 
/*      */   }
/*      */   
/*      */   public boolean contains(int k) {
/*  530 */     if (k == 0) return this.containsNull;
/*      */     
/*  532 */     int[] key = this.key;
/*      */     
/*      */     int curr, pos;
/*  535 */     if ((curr = key[pos = HashCommon.mix(k) & this.mask]) == 0) return false; 
/*  536 */     if (k == curr) return true; 
/*      */     while (true) {
/*  538 */       if ((curr = key[pos = pos + 1 & this.mask]) == 0) return false; 
/*  539 */       if (k == curr) return true;
/*      */     
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public int removeFirstInt() {
/*  550 */     if (this.size == 0) throw new NoSuchElementException(); 
/*  551 */     int pos = this.first;
/*      */     
/*  553 */     if (this.size == 1) { this.first = this.last = -1; }
/*      */     else
/*  555 */     { this.first = (int)this.link[pos];
/*  556 */       if (0 <= this.first)
/*      */       {
/*  558 */         this.link[this.first] = this.link[this.first] | 0xFFFFFFFF00000000L;
/*      */       } }
/*      */     
/*  561 */     int k = this.key[pos];
/*  562 */     this.size--;
/*  563 */     if (k == 0)
/*  564 */     { this.containsNull = false;
/*  565 */       this.key[this.n] = 0; }
/*  566 */     else { shiftKeys(pos); }
/*  567 */      if (this.n > this.minN && this.size < this.maxFill / 4 && this.n > 16) rehash(this.n / 2); 
/*  568 */     return k;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public int removeLastInt() {
/*  578 */     if (this.size == 0) throw new NoSuchElementException(); 
/*  579 */     int pos = this.last;
/*      */     
/*  581 */     if (this.size == 1) { this.first = this.last = -1; }
/*      */     else
/*  583 */     { this.last = (int)(this.link[pos] >>> 32L);
/*  584 */       if (0 <= this.last)
/*      */       {
/*  586 */         this.link[this.last] = this.link[this.last] | 0xFFFFFFFFL;
/*      */       } }
/*      */     
/*  589 */     int k = this.key[pos];
/*  590 */     this.size--;
/*  591 */     if (k == 0)
/*  592 */     { this.containsNull = false;
/*  593 */       this.key[this.n] = 0; }
/*  594 */     else { shiftKeys(pos); }
/*  595 */      if (this.n > this.minN && this.size < this.maxFill / 4 && this.n > 16) rehash(this.n / 2); 
/*  596 */     return k;
/*      */   }
/*      */   
/*      */   private void moveIndexToFirst(int i) {
/*  600 */     if (this.size == 1 || this.first == i)
/*  601 */       return;  if (this.last == i) {
/*  602 */       this.last = (int)(this.link[i] >>> 32L);
/*      */       
/*  604 */       this.link[this.last] = this.link[this.last] | 0xFFFFFFFFL;
/*      */     } else {
/*  606 */       long linki = this.link[i];
/*  607 */       int prev = (int)(linki >>> 32L);
/*  608 */       int next = (int)linki;
/*  609 */       this.link[prev] = this.link[prev] ^ (this.link[prev] ^ linki & 0xFFFFFFFFL) & 0xFFFFFFFFL;
/*  610 */       this.link[next] = this.link[next] ^ (this.link[next] ^ linki & 0xFFFFFFFF00000000L) & 0xFFFFFFFF00000000L;
/*      */     } 
/*  612 */     this.link[this.first] = this.link[this.first] ^ (this.link[this.first] ^ (i & 0xFFFFFFFFL) << 32L) & 0xFFFFFFFF00000000L;
/*  613 */     this.link[i] = 0xFFFFFFFF00000000L | this.first & 0xFFFFFFFFL;
/*  614 */     this.first = i;
/*      */   }
/*      */   
/*      */   private void moveIndexToLast(int i) {
/*  618 */     if (this.size == 1 || this.last == i)
/*  619 */       return;  if (this.first == i) {
/*  620 */       this.first = (int)this.link[i];
/*      */       
/*  622 */       this.link[this.first] = this.link[this.first] | 0xFFFFFFFF00000000L;
/*      */     } else {
/*  624 */       long linki = this.link[i];
/*  625 */       int prev = (int)(linki >>> 32L);
/*  626 */       int next = (int)linki;
/*  627 */       this.link[prev] = this.link[prev] ^ (this.link[prev] ^ linki & 0xFFFFFFFFL) & 0xFFFFFFFFL;
/*  628 */       this.link[next] = this.link[next] ^ (this.link[next] ^ linki & 0xFFFFFFFF00000000L) & 0xFFFFFFFF00000000L;
/*      */     } 
/*  630 */     this.link[this.last] = this.link[this.last] ^ (this.link[this.last] ^ i & 0xFFFFFFFFL) & 0xFFFFFFFFL;
/*  631 */     this.link[i] = (this.last & 0xFFFFFFFFL) << 32L | 0xFFFFFFFFL;
/*  632 */     this.last = i;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean addAndMoveToFirst(int k) {
/*      */     int pos;
/*  644 */     if (k == 0) {
/*  645 */       if (this.containsNull) {
/*  646 */         moveIndexToFirst(this.n);
/*  647 */         return false;
/*      */       } 
/*  649 */       this.containsNull = true;
/*  650 */       pos = this.n;
/*      */     } else {
/*      */       
/*  653 */       int[] key = this.key;
/*  654 */       pos = HashCommon.mix(k) & this.mask;
/*      */       
/*  656 */       while (key[pos] != 0) {
/*  657 */         if (k == key[pos]) {
/*  658 */           moveIndexToFirst(pos);
/*  659 */           return false;
/*      */         } 
/*  661 */         pos = pos + 1 & this.mask;
/*      */       } 
/*      */     } 
/*  664 */     this.key[pos] = k;
/*  665 */     if (this.size == 0) {
/*  666 */       this.first = this.last = pos;
/*      */       
/*  668 */       this.link[pos] = -1L;
/*      */     } else {
/*  670 */       this.link[this.first] = this.link[this.first] ^ (this.link[this.first] ^ (pos & 0xFFFFFFFFL) << 32L) & 0xFFFFFFFF00000000L;
/*  671 */       this.link[pos] = 0xFFFFFFFF00000000L | this.first & 0xFFFFFFFFL;
/*  672 */       this.first = pos;
/*      */     } 
/*  674 */     if (this.size++ >= this.maxFill) rehash(HashCommon.arraySize(this.size, this.f));
/*      */     
/*  676 */     return true;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean addAndMoveToLast(int k) {
/*      */     int pos;
/*  688 */     if (k == 0) {
/*  689 */       if (this.containsNull) {
/*  690 */         moveIndexToLast(this.n);
/*  691 */         return false;
/*      */       } 
/*  693 */       this.containsNull = true;
/*  694 */       pos = this.n;
/*      */     } else {
/*      */       
/*  697 */       int[] key = this.key;
/*  698 */       pos = HashCommon.mix(k) & this.mask;
/*      */       
/*  700 */       while (key[pos] != 0) {
/*  701 */         if (k == key[pos]) {
/*  702 */           moveIndexToLast(pos);
/*  703 */           return false;
/*      */         } 
/*  705 */         pos = pos + 1 & this.mask;
/*      */       } 
/*      */     } 
/*  708 */     this.key[pos] = k;
/*  709 */     if (this.size == 0) {
/*  710 */       this.first = this.last = pos;
/*      */       
/*  712 */       this.link[pos] = -1L;
/*      */     } else {
/*  714 */       this.link[this.last] = this.link[this.last] ^ (this.link[this.last] ^ pos & 0xFFFFFFFFL) & 0xFFFFFFFFL;
/*  715 */       this.link[pos] = (this.last & 0xFFFFFFFFL) << 32L | 0xFFFFFFFFL;
/*  716 */       this.last = pos;
/*      */     } 
/*  718 */     if (this.size++ >= this.maxFill) rehash(HashCommon.arraySize(this.size, this.f));
/*      */     
/*  720 */     return true;
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
/*  731 */     if (this.size == 0)
/*  732 */       return;  this.size = 0;
/*  733 */     this.containsNull = false;
/*  734 */     Arrays.fill(this.key, 0);
/*  735 */     this.first = this.last = -1;
/*      */   }
/*      */ 
/*      */   
/*      */   public int size() {
/*  740 */     return this.size;
/*      */   }
/*      */ 
/*      */   
/*      */   public boolean isEmpty() {
/*  745 */     return (this.size == 0);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected void fixPointers(int i) {
/*  755 */     if (this.size == 0) {
/*  756 */       this.first = this.last = -1;
/*      */       return;
/*      */     } 
/*  759 */     if (this.first == i) {
/*  760 */       this.first = (int)this.link[i];
/*  761 */       if (0 <= this.first)
/*      */       {
/*  763 */         this.link[this.first] = this.link[this.first] | 0xFFFFFFFF00000000L;
/*      */       }
/*      */       return;
/*      */     } 
/*  767 */     if (this.last == i) {
/*  768 */       this.last = (int)(this.link[i] >>> 32L);
/*  769 */       if (0 <= this.last)
/*      */       {
/*  771 */         this.link[this.last] = this.link[this.last] | 0xFFFFFFFFL;
/*      */       }
/*      */       return;
/*      */     } 
/*  775 */     long linki = this.link[i];
/*  776 */     int prev = (int)(linki >>> 32L);
/*  777 */     int next = (int)linki;
/*  778 */     this.link[prev] = this.link[prev] ^ (this.link[prev] ^ linki & 0xFFFFFFFFL) & 0xFFFFFFFFL;
/*  779 */     this.link[next] = this.link[next] ^ (this.link[next] ^ linki & 0xFFFFFFFF00000000L) & 0xFFFFFFFF00000000L;
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
/*  790 */     if (this.size == 1) {
/*  791 */       this.first = this.last = d;
/*      */       
/*  793 */       this.link[d] = -1L;
/*      */       return;
/*      */     } 
/*  796 */     if (this.first == s) {
/*  797 */       this.first = d;
/*  798 */       this.link[(int)this.link[s]] = this.link[(int)this.link[s]] ^ (this.link[(int)this.link[s]] ^ (d & 0xFFFFFFFFL) << 32L) & 0xFFFFFFFF00000000L;
/*  799 */       this.link[d] = this.link[s];
/*      */       return;
/*      */     } 
/*  802 */     if (this.last == s) {
/*  803 */       this.last = d;
/*  804 */       this.link[(int)(this.link[s] >>> 32L)] = this.link[(int)(this.link[s] >>> 32L)] ^ (this.link[(int)(this.link[s] >>> 32L)] ^ d & 0xFFFFFFFFL) & 0xFFFFFFFFL;
/*  805 */       this.link[d] = this.link[s];
/*      */       return;
/*      */     } 
/*  808 */     long links = this.link[s];
/*  809 */     int prev = (int)(links >>> 32L);
/*  810 */     int next = (int)links;
/*  811 */     this.link[prev] = this.link[prev] ^ (this.link[prev] ^ d & 0xFFFFFFFFL) & 0xFFFFFFFFL;
/*  812 */     this.link[next] = this.link[next] ^ (this.link[next] ^ (d & 0xFFFFFFFFL) << 32L) & 0xFFFFFFFF00000000L;
/*  813 */     this.link[d] = links;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public int firstInt() {
/*  823 */     if (this.size == 0) throw new NoSuchElementException(); 
/*  824 */     return this.key[this.first];
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public int lastInt() {
/*  834 */     if (this.size == 0) throw new NoSuchElementException(); 
/*  835 */     return this.key[this.last];
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public IntSortedSet tailSet(int from) {
/*  845 */     throw new UnsupportedOperationException();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public IntSortedSet headSet(int to) {
/*  855 */     throw new UnsupportedOperationException();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public IntSortedSet subSet(int from, int to) {
/*  865 */     throw new UnsupportedOperationException();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public IntComparator comparator() {
/*  875 */     return null;
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
/*      */     implements IntListIterator
/*      */   {
/*  890 */     int prev = -1;
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  895 */     int next = -1;
/*      */     
/*  897 */     int curr = -1;
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  902 */     int index = -1;
/*      */     
/*      */     SetIterator() {
/*  905 */       this.next = IntLinkedOpenHashSet.this.first;
/*  906 */       this.index = 0;
/*      */     }
/*      */     
/*      */     SetIterator(int from) {
/*  910 */       if (from == 0) {
/*  911 */         if (IntLinkedOpenHashSet.this.containsNull) {
/*  912 */           this.next = (int)IntLinkedOpenHashSet.this.link[IntLinkedOpenHashSet.this.n];
/*  913 */           this.prev = IntLinkedOpenHashSet.this.n; return;
/*      */         } 
/*  915 */         throw new NoSuchElementException("The key " + from + " does not belong to this set.");
/*      */       } 
/*  917 */       if (IntLinkedOpenHashSet.this.key[IntLinkedOpenHashSet.this.last] == from) {
/*  918 */         this.prev = IntLinkedOpenHashSet.this.last;
/*  919 */         this.index = IntLinkedOpenHashSet.this.size;
/*      */         
/*      */         return;
/*      */       } 
/*  923 */       int[] key = IntLinkedOpenHashSet.this.key;
/*  924 */       int pos = HashCommon.mix(from) & IntLinkedOpenHashSet.this.mask;
/*      */       
/*  926 */       while (key[pos] != 0) {
/*  927 */         if (key[pos] == from) {
/*      */           
/*  929 */           this.next = (int)IntLinkedOpenHashSet.this.link[pos];
/*  930 */           this.prev = pos;
/*      */           return;
/*      */         } 
/*  933 */         pos = pos + 1 & IntLinkedOpenHashSet.this.mask;
/*      */       } 
/*  935 */       throw new NoSuchElementException("The key " + from + " does not belong to this set.");
/*      */     }
/*      */ 
/*      */     
/*      */     public boolean hasNext() {
/*  940 */       return (this.next != -1);
/*      */     }
/*      */ 
/*      */     
/*      */     public boolean hasPrevious() {
/*  945 */       return (this.prev != -1);
/*      */     }
/*      */ 
/*      */     
/*      */     public int nextInt() {
/*  950 */       if (!hasNext()) throw new NoSuchElementException(); 
/*  951 */       this.curr = this.next;
/*  952 */       this.next = (int)IntLinkedOpenHashSet.this.link[this.curr];
/*  953 */       this.prev = this.curr;
/*  954 */       if (this.index >= 0) this.index++;
/*      */       
/*  956 */       return IntLinkedOpenHashSet.this.key[this.curr];
/*      */     }
/*      */ 
/*      */     
/*      */     public int previousInt() {
/*  961 */       if (!hasPrevious()) throw new NoSuchElementException(); 
/*  962 */       this.curr = this.prev;
/*  963 */       this.prev = (int)(IntLinkedOpenHashSet.this.link[this.curr] >>> 32L);
/*  964 */       this.next = this.curr;
/*  965 */       if (this.index >= 0) this.index--; 
/*  966 */       return IntLinkedOpenHashSet.this.key[this.curr];
/*      */     }
/*      */ 
/*      */     
/*      */     public void forEachRemaining(IntConsumer action) {
/*  971 */       int[] key = IntLinkedOpenHashSet.this.key;
/*  972 */       long[] link = IntLinkedOpenHashSet.this.link;
/*  973 */       while (this.next != -1) {
/*  974 */         this.curr = this.next;
/*  975 */         this.next = (int)link[this.curr];
/*  976 */         this.prev = this.curr;
/*  977 */         if (this.index >= 0) this.index++;
/*      */         
/*  979 */         action.accept(key[this.curr]);
/*      */       } 
/*      */     }
/*      */     
/*      */     private final void ensureIndexKnown() {
/*  984 */       if (this.index >= 0)
/*  985 */         return;  if (this.prev == -1) {
/*  986 */         this.index = 0;
/*      */         return;
/*      */       } 
/*  989 */       if (this.next == -1) {
/*  990 */         this.index = IntLinkedOpenHashSet.this.size;
/*      */         return;
/*      */       } 
/*  993 */       int pos = IntLinkedOpenHashSet.this.first;
/*  994 */       this.index = 1;
/*  995 */       while (pos != this.prev) {
/*  996 */         pos = (int)IntLinkedOpenHashSet.this.link[pos];
/*  997 */         this.index++;
/*      */       } 
/*      */     }
/*      */ 
/*      */     
/*      */     public int nextIndex() {
/* 1003 */       ensureIndexKnown();
/* 1004 */       return this.index;
/*      */     }
/*      */ 
/*      */     
/*      */     public int previousIndex() {
/* 1009 */       ensureIndexKnown();
/* 1010 */       return this.index - 1;
/*      */     }
/*      */ 
/*      */     
/*      */     public void remove() {
/* 1015 */       ensureIndexKnown();
/* 1016 */       if (this.curr == -1) throw new IllegalStateException(); 
/* 1017 */       if (this.curr == this.prev)
/*      */       
/*      */       { 
/* 1020 */         this.index--;
/* 1021 */         this.prev = (int)(IntLinkedOpenHashSet.this.link[this.curr] >>> 32L); }
/* 1022 */       else { this.next = (int)IntLinkedOpenHashSet.this.link[this.curr]; }
/* 1023 */        IntLinkedOpenHashSet.this.size--;
/*      */ 
/*      */       
/* 1026 */       if (this.prev == -1) { IntLinkedOpenHashSet.this.first = this.next; }
/* 1027 */       else { IntLinkedOpenHashSet.this.link[this.prev] = IntLinkedOpenHashSet.this.link[this.prev] ^ (IntLinkedOpenHashSet.this.link[this.prev] ^ this.next & 0xFFFFFFFFL) & 0xFFFFFFFFL; }
/* 1028 */        if (this.next == -1) { IntLinkedOpenHashSet.this.last = this.prev; }
/* 1029 */       else { IntLinkedOpenHashSet.this.link[this.next] = IntLinkedOpenHashSet.this.link[this.next] ^ (IntLinkedOpenHashSet.this.link[this.next] ^ (this.prev & 0xFFFFFFFFL) << 32L) & 0xFFFFFFFF00000000L; }
/* 1030 */        int pos = this.curr;
/* 1031 */       this.curr = -1;
/* 1032 */       if (pos == IntLinkedOpenHashSet.this.n) {
/* 1033 */         IntLinkedOpenHashSet.this.containsNull = false;
/* 1034 */         IntLinkedOpenHashSet.this.key[IntLinkedOpenHashSet.this.n] = 0;
/*      */       } else {
/*      */         
/* 1037 */         int[] key = IntLinkedOpenHashSet.this.key;
/*      */         while (true) {
/*      */           int curr, last;
/* 1040 */           pos = (last = pos) + 1 & IntLinkedOpenHashSet.this.mask;
/*      */           while (true) {
/* 1042 */             if ((curr = key[pos]) == 0) {
/* 1043 */               key[last] = 0;
/*      */               return;
/*      */             } 
/* 1046 */             int slot = HashCommon.mix(curr) & IntLinkedOpenHashSet.this.mask;
/* 1047 */             if ((last <= pos) ? (last >= slot || slot > pos) : (last >= slot && slot > pos))
/* 1048 */               break;  pos = pos + 1 & IntLinkedOpenHashSet.this.mask;
/*      */           } 
/* 1050 */           key[last] = curr;
/* 1051 */           if (this.next == pos) this.next = last; 
/* 1052 */           if (this.prev == pos) this.prev = last; 
/* 1053 */           IntLinkedOpenHashSet.this.fixPointers(pos, last);
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
/*      */   public IntListIterator iterator(int from) {
/* 1069 */     return new SetIterator(from);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public IntListIterator iterator() {
/* 1080 */     return new SetIterator();
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
/*      */   public IntSpliterator spliterator() {
/* 1100 */     return IntSpliterators.asSpliterator(iterator(), Size64.sizeOf(this), 337);
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public void forEach(IntConsumer action) {
/* 1106 */     int next = this.first;
/* 1107 */     while (next != -1) {
/* 1108 */       int curr = next;
/* 1109 */       next = (int)this.link[curr];
/*      */       
/* 1111 */       action.accept(this.key[curr]);
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
/* 1129 */     return trim(this.size);
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
/* 1151 */     int l = HashCommon.nextPowerOfTwo((int)Math.ceil((n / this.f)));
/* 1152 */     if (l >= this.n || this.size > HashCommon.maxFill(l, this.f)) return true; 
/*      */     try {
/* 1154 */       rehash(l);
/* 1155 */     } catch (OutOfMemoryError cantDoIt) {
/* 1156 */       return false;
/*      */     } 
/* 1158 */     return true;
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
/* 1173 */     int[] key = this.key;
/* 1174 */     int mask = newN - 1;
/* 1175 */     int[] newKey = new int[newN + 1];
/* 1176 */     int i = this.first, prev = -1, newPrev = -1;
/* 1177 */     long[] link = this.link;
/* 1178 */     long[] newLink = new long[newN + 1];
/* 1179 */     this.first = -1;
/* 1180 */     for (int j = this.size; j-- != 0; ) {
/* 1181 */       int pos; if (key[i] == 0) { pos = newN; }
/*      */       else
/* 1183 */       { pos = HashCommon.mix(key[i]) & mask;
/* 1184 */         for (; newKey[pos] != 0; pos = pos + 1 & mask); }
/*      */       
/* 1186 */       newKey[pos] = key[i];
/* 1187 */       if (prev != -1) {
/* 1188 */         newLink[newPrev] = newLink[newPrev] ^ (newLink[newPrev] ^ pos & 0xFFFFFFFFL) & 0xFFFFFFFFL;
/* 1189 */         newLink[pos] = newLink[pos] ^ (newLink[pos] ^ (newPrev & 0xFFFFFFFFL) << 32L) & 0xFFFFFFFF00000000L;
/* 1190 */         newPrev = pos;
/*      */       } else {
/* 1192 */         newPrev = this.first = pos;
/*      */         
/* 1194 */         newLink[pos] = -1L;
/*      */       } 
/* 1196 */       int t = i;
/* 1197 */       i = (int)link[i];
/* 1198 */       prev = t;
/*      */     } 
/* 1200 */     this.link = newLink;
/* 1201 */     this.last = newPrev;
/* 1202 */     if (newPrev != -1)
/*      */     {
/* 1204 */       newLink[newPrev] = newLink[newPrev] | 0xFFFFFFFFL; } 
/* 1205 */     this.n = newN;
/* 1206 */     this.mask = mask;
/* 1207 */     this.maxFill = HashCommon.maxFill(this.n, this.f);
/* 1208 */     this.key = newKey;
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
/*      */   public IntLinkedOpenHashSet clone() {
/*      */     IntLinkedOpenHashSet c;
/*      */     try {
/* 1225 */       c = (IntLinkedOpenHashSet)super.clone();
/* 1226 */     } catch (CloneNotSupportedException cantHappen) {
/* 1227 */       throw new InternalError();
/*      */     } 
/* 1229 */     c.key = (int[])this.key.clone();
/* 1230 */     c.containsNull = this.containsNull;
/* 1231 */     c.link = (long[])this.link.clone();
/* 1232 */     return c;
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
/* 1246 */     int h = 0;
/* 1247 */     for (int j = realSize(), i = 0; j-- != 0; ) {
/* 1248 */       for (; this.key[i] == 0; i++);
/* 1249 */       h += this.key[i];
/* 1250 */       i++;
/*      */     } 
/*      */     
/* 1253 */     return h;
/*      */   }
/*      */   
/*      */   private void writeObject(ObjectOutputStream s) throws IOException {
/* 1257 */     IntIterator i = iterator();
/* 1258 */     s.defaultWriteObject();
/* 1259 */     for (int j = this.size; j-- != 0; s.writeInt(i.nextInt()));
/*      */   }
/*      */   
/*      */   private void readObject(ObjectInputStream s) throws IOException, ClassNotFoundException {
/* 1263 */     s.defaultReadObject();
/* 1264 */     this.n = HashCommon.arraySize(this.size, this.f);
/* 1265 */     this.maxFill = HashCommon.maxFill(this.n, this.f);
/* 1266 */     this.mask = this.n - 1;
/* 1267 */     int[] key = this.key = new int[this.n + 1];
/* 1268 */     long[] link = this.link = new long[this.n + 1];
/* 1269 */     int prev = -1;
/* 1270 */     this.first = this.last = -1;
/*      */     
/* 1272 */     for (int i = this.size; i-- != 0; ) {
/* 1273 */       int pos, k = s.readInt();
/* 1274 */       if (k == 0)
/* 1275 */       { pos = this.n;
/* 1276 */         this.containsNull = true; }
/*      */       
/* 1278 */       else if (key[pos = HashCommon.mix(k) & this.mask] != 0) { while (key[pos = pos + 1 & this.mask] != 0); }
/*      */       
/* 1280 */       key[pos] = k;
/* 1281 */       if (this.first != -1) {
/* 1282 */         link[prev] = link[prev] ^ (link[prev] ^ pos & 0xFFFFFFFFL) & 0xFFFFFFFFL;
/* 1283 */         link[pos] = link[pos] ^ (link[pos] ^ (prev & 0xFFFFFFFFL) << 32L) & 0xFFFFFFFF00000000L;
/* 1284 */         prev = pos; continue;
/*      */       } 
/* 1286 */       prev = this.first = pos;
/*      */       
/* 1288 */       link[pos] = link[pos] | 0xFFFFFFFF00000000L;
/*      */     } 
/*      */     
/* 1291 */     this.last = prev;
/* 1292 */     if (prev != -1)
/*      */     {
/* 1294 */       link[prev] = link[prev] | 0xFFFFFFFFL;
/*      */     }
/*      */   }
/*      */   
/*      */   private void checkTable() {}
/*      */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\i\\unimi\dsi\fastutil\ints\IntLinkedOpenHashSet.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */