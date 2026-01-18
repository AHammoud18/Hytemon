/*      */ package it.unimi.dsi.fastutil.doubles;
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
/*      */ import java.util.function.DoubleConsumer;
/*      */ import java.util.function.Supplier;
/*      */ import java.util.stream.DoubleStream;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ public class DoubleLinkedOpenHashSet
/*      */   extends AbstractDoubleSortedSet
/*      */   implements Serializable, Cloneable, Hash
/*      */ {
/*      */   private static final long serialVersionUID = 0L;
/*      */   private static final boolean ASSERTS = false;
/*      */   protected transient double[] key;
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
/*      */   public DoubleLinkedOpenHashSet(int expected, float f) {
/*  124 */     if (f <= 0.0F || f >= 1.0F) throw new IllegalArgumentException("Load factor must be greater than 0 and smaller than 1"); 
/*  125 */     if (expected < 0) throw new IllegalArgumentException("The expected number of elements must be nonnegative"); 
/*  126 */     this.f = f;
/*  127 */     this.minN = this.n = HashCommon.arraySize(expected, f);
/*  128 */     this.mask = this.n - 1;
/*  129 */     this.maxFill = HashCommon.maxFill(this.n, f);
/*  130 */     this.key = new double[this.n + 1];
/*  131 */     this.link = new long[this.n + 1];
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public DoubleLinkedOpenHashSet(int expected) {
/*  140 */     this(expected, 0.75F);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public DoubleLinkedOpenHashSet() {
/*  148 */     this(16, 0.75F);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public DoubleLinkedOpenHashSet(Collection<? extends Double> c, float f) {
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
/*      */   public DoubleLinkedOpenHashSet(Collection<? extends Double> c) {
/*  169 */     this(c, 0.75F);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public DoubleLinkedOpenHashSet(DoubleCollection c, float f) {
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
/*      */   public DoubleLinkedOpenHashSet(DoubleCollection c) {
/*  190 */     this(c, 0.75F);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public DoubleLinkedOpenHashSet(DoubleIterator i, float f) {
/*  200 */     this(16, f);
/*  201 */     for (; i.hasNext(); add(i.nextDouble()));
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public DoubleLinkedOpenHashSet(DoubleIterator i) {
/*  211 */     this(i, 0.75F);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public DoubleLinkedOpenHashSet(Iterator<?> i, float f) {
/*  221 */     this(DoubleIterators.asDoubleIterator(i), f);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public DoubleLinkedOpenHashSet(Iterator<?> i) {
/*  231 */     this(DoubleIterators.asDoubleIterator(i));
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
/*      */   public DoubleLinkedOpenHashSet(double[] a, int offset, int length, float f) {
/*  243 */     this((length < 0) ? 0 : length, f);
/*  244 */     DoubleArrays.ensureOffsetLength(a, offset, length);
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
/*      */   public DoubleLinkedOpenHashSet(double[] a, int offset, int length) {
/*  257 */     this(a, offset, length, 0.75F);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public DoubleLinkedOpenHashSet(double[] a, float f) {
/*  267 */     this(a, 0, a.length, f);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public DoubleLinkedOpenHashSet(double[] a) {
/*  277 */     this(a, 0.75F);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static DoubleLinkedOpenHashSet of() {
/*  286 */     return new DoubleLinkedOpenHashSet();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static DoubleLinkedOpenHashSet of(double e) {
/*  297 */     DoubleLinkedOpenHashSet result = new DoubleLinkedOpenHashSet(1, 0.75F);
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
/*      */   public static DoubleLinkedOpenHashSet of(double e0, double e1) {
/*  313 */     DoubleLinkedOpenHashSet result = new DoubleLinkedOpenHashSet(2, 0.75F);
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
/*      */   public static DoubleLinkedOpenHashSet of(double e0, double e1, double e2) {
/*  333 */     DoubleLinkedOpenHashSet result = new DoubleLinkedOpenHashSet(3, 0.75F);
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
/*      */   public static DoubleLinkedOpenHashSet of(double... a) {
/*  355 */     DoubleLinkedOpenHashSet result = new DoubleLinkedOpenHashSet(a.length, 0.75F);
/*  356 */     for (double element : a) {
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
/*      */   public static DoubleLinkedOpenHashSet toSet(DoubleStream stream) {
/*  375 */     return stream.<DoubleLinkedOpenHashSet>collect(DoubleLinkedOpenHashSet::new, DoubleLinkedOpenHashSet::add, DoubleLinkedOpenHashSet::addAll);
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
/*      */   public static DoubleLinkedOpenHashSet toSetWithExpectedSize(DoubleStream stream, int expectedSize) {
/*  390 */     if (expectedSize <= 16)
/*      */     {
/*      */       
/*  393 */       return toSet(stream);
/*      */     }
/*  395 */     return stream.<DoubleLinkedOpenHashSet>collect(new DoubleCollections.SizeDecreasingSupplier<>(expectedSize, size -> (size <= 16) ? new DoubleLinkedOpenHashSet() : new DoubleLinkedOpenHashSet(size)), DoubleLinkedOpenHashSet::add, DoubleLinkedOpenHashSet::addAll);
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
/*      */   public boolean addAll(DoubleCollection c) {
/*  420 */     if (this.f <= 0.5D) { ensureCapacity(c.size()); }
/*  421 */     else { tryCapacity((size() + c.size())); }
/*      */     
/*  423 */     return super.addAll(c);
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean addAll(Collection<? extends Double> c) {
/*  429 */     if (this.f <= 0.5D) { ensureCapacity(c.size()); }
/*  430 */     else { tryCapacity((size() + c.size())); }
/*      */     
/*  432 */     return super.addAll(c);
/*      */   }
/*      */ 
/*      */   
/*      */   public boolean add(double k) {
/*      */     int pos;
/*  438 */     if (Double.doubleToLongBits(k) == 0L) {
/*  439 */       if (this.containsNull) return false; 
/*  440 */       pos = this.n;
/*  441 */       this.containsNull = true;
/*      */     } else {
/*      */       
/*  444 */       double[] key = this.key;
/*      */       double curr;
/*  446 */       if (Double.doubleToLongBits(curr = key[pos = (int)HashCommon.mix(Double.doubleToRawLongBits(k)) & this.mask]) != 0L) {
/*  447 */         if (Double.doubleToLongBits(curr) == Double.doubleToLongBits(k)) return false; 
/*  448 */         while (Double.doubleToLongBits(curr = key[pos = pos + 1 & this.mask]) != 0L) { if (Double.doubleToLongBits(curr) == Double.doubleToLongBits(k)) return false;  }
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
/*  476 */     double[] key = this.key; while (true) {
/*      */       double curr; int last;
/*  478 */       pos = (last = pos) + 1 & this.mask;
/*      */       while (true) {
/*  480 */         if (Double.doubleToLongBits(curr = key[pos]) == 0L) {
/*  481 */           key[last] = 0.0D;
/*      */           return;
/*      */         } 
/*  484 */         int slot = (int)HashCommon.mix(Double.doubleToRawLongBits(curr)) & this.mask;
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
/*  503 */     this.key[this.n] = 0.0D;
/*  504 */     this.size--;
/*  505 */     fixPointers(this.n);
/*  506 */     if (this.n > this.minN && this.size < this.maxFill / 4 && this.n > 16) rehash(this.n / 2); 
/*  507 */     return true;
/*      */   }
/*      */ 
/*      */   
/*      */   public boolean remove(double k) {
/*  512 */     if (Double.doubleToLongBits(k) == 0L) {
/*  513 */       if (this.containsNull) return removeNullEntry(); 
/*  514 */       return false;
/*      */     } 
/*      */     
/*  517 */     double[] key = this.key;
/*      */     double curr;
/*      */     int pos;
/*  520 */     if (Double.doubleToLongBits(curr = key[pos = (int)HashCommon.mix(Double.doubleToRawLongBits(k)) & this.mask]) == 0L) return false; 
/*  521 */     if (Double.doubleToLongBits(k) == Double.doubleToLongBits(curr)) return removeEntry(pos); 
/*      */     while (true) {
/*  523 */       if (Double.doubleToLongBits(curr = key[pos = pos + 1 & this.mask]) == 0L) return false; 
/*  524 */       if (Double.doubleToLongBits(k) == Double.doubleToLongBits(curr)) return removeEntry(pos);
/*      */     
/*      */     } 
/*      */   }
/*      */   
/*      */   public boolean contains(double k) {
/*  530 */     if (Double.doubleToLongBits(k) == 0L) return this.containsNull;
/*      */     
/*  532 */     double[] key = this.key;
/*      */     double curr;
/*      */     int pos;
/*  535 */     if (Double.doubleToLongBits(curr = key[pos = (int)HashCommon.mix(Double.doubleToRawLongBits(k)) & this.mask]) == 0L) return false; 
/*  536 */     if (Double.doubleToLongBits(k) == Double.doubleToLongBits(curr)) return true; 
/*      */     while (true) {
/*  538 */       if (Double.doubleToLongBits(curr = key[pos = pos + 1 & this.mask]) == 0L) return false; 
/*  539 */       if (Double.doubleToLongBits(k) == Double.doubleToLongBits(curr)) return true;
/*      */     
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public double removeFirstDouble() {
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
/*  561 */     double k = this.key[pos];
/*  562 */     this.size--;
/*  563 */     if (Double.doubleToLongBits(k) == 0L)
/*  564 */     { this.containsNull = false;
/*  565 */       this.key[this.n] = 0.0D; }
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
/*      */   public double removeLastDouble() {
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
/*  589 */     double k = this.key[pos];
/*  590 */     this.size--;
/*  591 */     if (Double.doubleToLongBits(k) == 0L)
/*  592 */     { this.containsNull = false;
/*  593 */       this.key[this.n] = 0.0D; }
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
/*      */   public boolean addAndMoveToFirst(double k) {
/*      */     int pos;
/*  644 */     if (Double.doubleToLongBits(k) == 0L) {
/*  645 */       if (this.containsNull) {
/*  646 */         moveIndexToFirst(this.n);
/*  647 */         return false;
/*      */       } 
/*  649 */       this.containsNull = true;
/*  650 */       pos = this.n;
/*      */     } else {
/*      */       
/*  653 */       double[] key = this.key;
/*  654 */       pos = (int)HashCommon.mix(Double.doubleToRawLongBits(k)) & this.mask;
/*      */       
/*  656 */       while (Double.doubleToLongBits(key[pos]) != 0L) {
/*  657 */         if (Double.doubleToLongBits(k) == Double.doubleToLongBits(key[pos])) {
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
/*      */   public boolean addAndMoveToLast(double k) {
/*      */     int pos;
/*  688 */     if (Double.doubleToLongBits(k) == 0L) {
/*  689 */       if (this.containsNull) {
/*  690 */         moveIndexToLast(this.n);
/*  691 */         return false;
/*      */       } 
/*  693 */       this.containsNull = true;
/*  694 */       pos = this.n;
/*      */     } else {
/*      */       
/*  697 */       double[] key = this.key;
/*  698 */       pos = (int)HashCommon.mix(Double.doubleToRawLongBits(k)) & this.mask;
/*      */       
/*  700 */       while (Double.doubleToLongBits(key[pos]) != 0L) {
/*  701 */         if (Double.doubleToLongBits(k) == Double.doubleToLongBits(key[pos])) {
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
/*  734 */     Arrays.fill(this.key, 0.0D);
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
/*      */   public double firstDouble() {
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
/*      */   public double lastDouble() {
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
/*      */   public DoubleSortedSet tailSet(double from) {
/*  845 */     throw new UnsupportedOperationException();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public DoubleSortedSet headSet(double to) {
/*  855 */     throw new UnsupportedOperationException();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public DoubleSortedSet subSet(double from, double to) {
/*  865 */     throw new UnsupportedOperationException();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public DoubleComparator comparator() {
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
/*      */     implements DoubleListIterator
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
/*  905 */       this.next = DoubleLinkedOpenHashSet.this.first;
/*  906 */       this.index = 0;
/*      */     }
/*      */     
/*      */     SetIterator(double from) {
/*  910 */       if (Double.doubleToLongBits(from) == 0L) {
/*  911 */         if (DoubleLinkedOpenHashSet.this.containsNull) {
/*  912 */           this.next = (int)DoubleLinkedOpenHashSet.this.link[DoubleLinkedOpenHashSet.this.n];
/*  913 */           this.prev = DoubleLinkedOpenHashSet.this.n; return;
/*      */         } 
/*  915 */         throw new NoSuchElementException("The key " + from + " does not belong to this set.");
/*      */       } 
/*  917 */       if (Double.doubleToLongBits(DoubleLinkedOpenHashSet.this.key[DoubleLinkedOpenHashSet.this.last]) == Double.doubleToLongBits(from)) {
/*  918 */         this.prev = DoubleLinkedOpenHashSet.this.last;
/*  919 */         this.index = DoubleLinkedOpenHashSet.this.size;
/*      */         
/*      */         return;
/*      */       } 
/*  923 */       double[] key = DoubleLinkedOpenHashSet.this.key;
/*  924 */       int pos = (int)HashCommon.mix(Double.doubleToRawLongBits(from)) & DoubleLinkedOpenHashSet.this.mask;
/*      */       
/*  926 */       while (Double.doubleToLongBits(key[pos]) != 0L) {
/*  927 */         if (Double.doubleToLongBits(key[pos]) == Double.doubleToLongBits(from)) {
/*      */           
/*  929 */           this.next = (int)DoubleLinkedOpenHashSet.this.link[pos];
/*  930 */           this.prev = pos;
/*      */           return;
/*      */         } 
/*  933 */         pos = pos + 1 & DoubleLinkedOpenHashSet.this.mask;
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
/*      */     public double nextDouble() {
/*  950 */       if (!hasNext()) throw new NoSuchElementException(); 
/*  951 */       this.curr = this.next;
/*  952 */       this.next = (int)DoubleLinkedOpenHashSet.this.link[this.curr];
/*  953 */       this.prev = this.curr;
/*  954 */       if (this.index >= 0) this.index++;
/*      */       
/*  956 */       return DoubleLinkedOpenHashSet.this.key[this.curr];
/*      */     }
/*      */ 
/*      */     
/*      */     public double previousDouble() {
/*  961 */       if (!hasPrevious()) throw new NoSuchElementException(); 
/*  962 */       this.curr = this.prev;
/*  963 */       this.prev = (int)(DoubleLinkedOpenHashSet.this.link[this.curr] >>> 32L);
/*  964 */       this.next = this.curr;
/*  965 */       if (this.index >= 0) this.index--; 
/*  966 */       return DoubleLinkedOpenHashSet.this.key[this.curr];
/*      */     }
/*      */ 
/*      */     
/*      */     public void forEachRemaining(DoubleConsumer action) {
/*  971 */       double[] key = DoubleLinkedOpenHashSet.this.key;
/*  972 */       long[] link = DoubleLinkedOpenHashSet.this.link;
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
/*  990 */         this.index = DoubleLinkedOpenHashSet.this.size;
/*      */         return;
/*      */       } 
/*  993 */       int pos = DoubleLinkedOpenHashSet.this.first;
/*  994 */       this.index = 1;
/*  995 */       while (pos != this.prev) {
/*  996 */         pos = (int)DoubleLinkedOpenHashSet.this.link[pos];
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
/* 1021 */         this.prev = (int)(DoubleLinkedOpenHashSet.this.link[this.curr] >>> 32L); }
/* 1022 */       else { this.next = (int)DoubleLinkedOpenHashSet.this.link[this.curr]; }
/* 1023 */        DoubleLinkedOpenHashSet.this.size--;
/*      */ 
/*      */       
/* 1026 */       if (this.prev == -1) { DoubleLinkedOpenHashSet.this.first = this.next; }
/* 1027 */       else { DoubleLinkedOpenHashSet.this.link[this.prev] = DoubleLinkedOpenHashSet.this.link[this.prev] ^ (DoubleLinkedOpenHashSet.this.link[this.prev] ^ this.next & 0xFFFFFFFFL) & 0xFFFFFFFFL; }
/* 1028 */        if (this.next == -1) { DoubleLinkedOpenHashSet.this.last = this.prev; }
/* 1029 */       else { DoubleLinkedOpenHashSet.this.link[this.next] = DoubleLinkedOpenHashSet.this.link[this.next] ^ (DoubleLinkedOpenHashSet.this.link[this.next] ^ (this.prev & 0xFFFFFFFFL) << 32L) & 0xFFFFFFFF00000000L; }
/* 1030 */        int pos = this.curr;
/* 1031 */       this.curr = -1;
/* 1032 */       if (pos == DoubleLinkedOpenHashSet.this.n) {
/* 1033 */         DoubleLinkedOpenHashSet.this.containsNull = false;
/* 1034 */         DoubleLinkedOpenHashSet.this.key[DoubleLinkedOpenHashSet.this.n] = 0.0D;
/*      */       } else {
/*      */         
/* 1037 */         double[] key = DoubleLinkedOpenHashSet.this.key; while (true) {
/*      */           double curr;
/*      */           int last;
/* 1040 */           pos = (last = pos) + 1 & DoubleLinkedOpenHashSet.this.mask;
/*      */           while (true) {
/* 1042 */             if (Double.doubleToLongBits(curr = key[pos]) == 0L) {
/* 1043 */               key[last] = 0.0D;
/*      */               return;
/*      */             } 
/* 1046 */             int slot = (int)HashCommon.mix(Double.doubleToRawLongBits(curr)) & DoubleLinkedOpenHashSet.this.mask;
/* 1047 */             if ((last <= pos) ? (last >= slot || slot > pos) : (last >= slot && slot > pos))
/* 1048 */               break;  pos = pos + 1 & DoubleLinkedOpenHashSet.this.mask;
/*      */           } 
/* 1050 */           key[last] = curr;
/* 1051 */           if (this.next == pos) this.next = last; 
/* 1052 */           if (this.prev == pos) this.prev = last; 
/* 1053 */           DoubleLinkedOpenHashSet.this.fixPointers(pos, last);
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
/*      */   public DoubleListIterator iterator(double from) {
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
/*      */   public DoubleListIterator iterator() {
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
/*      */   public DoubleSpliterator spliterator() {
/* 1100 */     return DoubleSpliterators.asSpliterator(iterator(), Size64.sizeOf(this), 337);
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public void forEach(DoubleConsumer action) {
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
/* 1173 */     double[] key = this.key;
/* 1174 */     int mask = newN - 1;
/* 1175 */     double[] newKey = new double[newN + 1];
/* 1176 */     int i = this.first, prev = -1, newPrev = -1;
/* 1177 */     long[] link = this.link;
/* 1178 */     long[] newLink = new long[newN + 1];
/* 1179 */     this.first = -1;
/* 1180 */     for (int j = this.size; j-- != 0; ) {
/* 1181 */       int pos; if (Double.doubleToLongBits(key[i]) == 0L) { pos = newN; }
/*      */       else
/* 1183 */       { pos = (int)HashCommon.mix(Double.doubleToRawLongBits(key[i])) & mask;
/* 1184 */         for (; Double.doubleToLongBits(newKey[pos]) != 0L; pos = pos + 1 & mask); }
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
/*      */   public DoubleLinkedOpenHashSet clone() {
/*      */     DoubleLinkedOpenHashSet c;
/*      */     try {
/* 1225 */       c = (DoubleLinkedOpenHashSet)super.clone();
/* 1226 */     } catch (CloneNotSupportedException cantHappen) {
/* 1227 */       throw new InternalError();
/*      */     } 
/* 1229 */     c.key = (double[])this.key.clone();
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
/* 1248 */       for (; Double.doubleToLongBits(this.key[i]) == 0L; i++);
/* 1249 */       h += HashCommon.double2int(this.key[i]);
/* 1250 */       i++;
/*      */     } 
/*      */     
/* 1253 */     return h;
/*      */   }
/*      */   
/*      */   private void writeObject(ObjectOutputStream s) throws IOException {
/* 1257 */     DoubleIterator i = iterator();
/* 1258 */     s.defaultWriteObject();
/* 1259 */     for (int j = this.size; j-- != 0; s.writeDouble(i.nextDouble()));
/*      */   }
/*      */   
/*      */   private void readObject(ObjectInputStream s) throws IOException, ClassNotFoundException {
/* 1263 */     s.defaultReadObject();
/* 1264 */     this.n = HashCommon.arraySize(this.size, this.f);
/* 1265 */     this.maxFill = HashCommon.maxFill(this.n, this.f);
/* 1266 */     this.mask = this.n - 1;
/* 1267 */     double[] key = this.key = new double[this.n + 1];
/* 1268 */     long[] link = this.link = new long[this.n + 1];
/* 1269 */     int prev = -1;
/* 1270 */     this.first = this.last = -1;
/*      */     
/* 1272 */     for (int i = this.size; i-- != 0; ) {
/* 1273 */       int pos; double k = s.readDouble();
/* 1274 */       if (Double.doubleToLongBits(k) == 0L)
/* 1275 */       { pos = this.n;
/* 1276 */         this.containsNull = true; }
/*      */       
/* 1278 */       else if (Double.doubleToLongBits(key[pos = (int)HashCommon.mix(Double.doubleToRawLongBits(k)) & this.mask]) != 0L) { while (Double.doubleToLongBits(key[pos = pos + 1 & this.mask]) != 0L); }
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


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\i\\unimi\dsi\fastutil\doubles\DoubleLinkedOpenHashSet.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */