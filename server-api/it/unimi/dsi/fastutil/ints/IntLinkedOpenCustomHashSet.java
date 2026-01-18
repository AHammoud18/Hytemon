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
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ public class IntLinkedOpenCustomHashSet
/*      */   extends AbstractIntSortedSet
/*      */   implements Serializable, Cloneable, Hash
/*      */ {
/*      */   private static final long serialVersionUID = 0L;
/*      */   private static final boolean ASSERTS = false;
/*      */   protected transient int[] key;
/*      */   protected transient int mask;
/*      */   protected transient boolean containsNull;
/*      */   protected IntHash.Strategy strategy;
/*   90 */   protected transient int first = -1;
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*   95 */   protected transient int last = -1;
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
/*      */   private static final int SPLITERATOR_CHARACTERISTICS = 337;
/*      */ 
/*      */ 
/*      */   
/*      */   public IntLinkedOpenCustomHashSet(int expected, float f, IntHash.Strategy strategy) {
/*  127 */     this.strategy = strategy;
/*  128 */     if (f <= 0.0F || f >= 1.0F) throw new IllegalArgumentException("Load factor must be greater than 0 and smaller than 1"); 
/*  129 */     if (expected < 0) throw new IllegalArgumentException("The expected number of elements must be nonnegative"); 
/*  130 */     this.f = f;
/*  131 */     this.minN = this.n = HashCommon.arraySize(expected, f);
/*  132 */     this.mask = this.n - 1;
/*  133 */     this.maxFill = HashCommon.maxFill(this.n, f);
/*  134 */     this.key = new int[this.n + 1];
/*  135 */     this.link = new long[this.n + 1];
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public IntLinkedOpenCustomHashSet(int expected, IntHash.Strategy strategy) {
/*  145 */     this(expected, 0.75F, strategy);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public IntLinkedOpenCustomHashSet(IntHash.Strategy strategy) {
/*  155 */     this(16, 0.75F, strategy);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public IntLinkedOpenCustomHashSet(Collection<? extends Integer> c, float f, IntHash.Strategy strategy) {
/*  166 */     this(c.size(), f, strategy);
/*  167 */     addAll(c);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public IntLinkedOpenCustomHashSet(Collection<? extends Integer> c, IntHash.Strategy strategy) {
/*  178 */     this(c, 0.75F, strategy);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public IntLinkedOpenCustomHashSet(IntCollection c, float f, IntHash.Strategy strategy) {
/*  189 */     this(c.size(), f, strategy);
/*  190 */     addAll(c);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public IntLinkedOpenCustomHashSet(IntCollection c, IntHash.Strategy strategy) {
/*  201 */     this(c, 0.75F, strategy);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public IntLinkedOpenCustomHashSet(IntIterator i, float f, IntHash.Strategy strategy) {
/*  212 */     this(16, f, strategy);
/*  213 */     for (; i.hasNext(); add(i.nextInt()));
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public IntLinkedOpenCustomHashSet(IntIterator i, IntHash.Strategy strategy) {
/*  224 */     this(i, 0.75F, strategy);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public IntLinkedOpenCustomHashSet(Iterator<?> i, float f, IntHash.Strategy strategy) {
/*  235 */     this(IntIterators.asIntIterator(i), f, strategy);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public IntLinkedOpenCustomHashSet(Iterator<?> i, IntHash.Strategy strategy) {
/*  246 */     this(IntIterators.asIntIterator(i), strategy);
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
/*      */   public IntLinkedOpenCustomHashSet(int[] a, int offset, int length, float f, IntHash.Strategy strategy) {
/*  259 */     this((length < 0) ? 0 : length, f, strategy);
/*  260 */     IntArrays.ensureOffsetLength(a, offset, length);
/*  261 */     for (int i = 0; i < length; ) { add(a[offset + i]); i++; }
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
/*      */   
/*      */   public IntLinkedOpenCustomHashSet(int[] a, int offset, int length, IntHash.Strategy strategy) {
/*  274 */     this(a, offset, length, 0.75F, strategy);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public IntLinkedOpenCustomHashSet(int[] a, float f, IntHash.Strategy strategy) {
/*  285 */     this(a, 0, a.length, f, strategy);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public IntLinkedOpenCustomHashSet(int[] a, IntHash.Strategy strategy) {
/*  296 */     this(a, 0.75F, strategy);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public IntHash.Strategy strategy() {
/*  305 */     return this.strategy;
/*      */   }
/*      */   
/*      */   private int realSize() {
/*  309 */     return this.containsNull ? (this.size - 1) : this.size;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void ensureCapacity(int capacity) {
/*  319 */     int needed = HashCommon.arraySize(capacity, this.f);
/*  320 */     if (needed > this.n) rehash(needed); 
/*      */   }
/*      */   
/*      */   private void tryCapacity(long capacity) {
/*  324 */     int needed = (int)Math.min(1073741824L, Math.max(2L, HashCommon.nextPowerOfTwo((long)Math.ceil(((float)capacity / this.f)))));
/*  325 */     if (needed > this.n) rehash(needed);
/*      */   
/*      */   }
/*      */   
/*      */   public boolean addAll(IntCollection c) {
/*  330 */     if (this.f <= 0.5D) { ensureCapacity(c.size()); }
/*  331 */     else { tryCapacity((size() + c.size())); }
/*      */     
/*  333 */     return super.addAll(c);
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean addAll(Collection<? extends Integer> c) {
/*  339 */     if (this.f <= 0.5D) { ensureCapacity(c.size()); }
/*  340 */     else { tryCapacity((size() + c.size())); }
/*      */     
/*  342 */     return super.addAll(c);
/*      */   }
/*      */ 
/*      */   
/*      */   public boolean add(int k) {
/*      */     int pos;
/*  348 */     if (this.strategy.equals(k, 0)) {
/*  349 */       if (this.containsNull) return false; 
/*  350 */       pos = this.n;
/*  351 */       this.containsNull = true;
/*  352 */       this.key[this.n] = k;
/*      */     } else {
/*      */       
/*  355 */       int[] key = this.key;
/*      */       int curr;
/*  357 */       if ((curr = key[pos = HashCommon.mix(this.strategy.hashCode(k)) & this.mask]) != 0) {
/*  358 */         if (this.strategy.equals(curr, k)) return false; 
/*  359 */         while ((curr = key[pos = pos + 1 & this.mask]) != 0) { if (this.strategy.equals(curr, k)) return false;  }
/*      */       
/*  361 */       }  key[pos] = k;
/*      */     } 
/*  363 */     if (this.size == 0) {
/*  364 */       this.first = this.last = pos;
/*      */       
/*  366 */       this.link[pos] = -1L;
/*      */     } else {
/*  368 */       this.link[this.last] = this.link[this.last] ^ (this.link[this.last] ^ pos & 0xFFFFFFFFL) & 0xFFFFFFFFL;
/*  369 */       this.link[pos] = (this.last & 0xFFFFFFFFL) << 32L | 0xFFFFFFFFL;
/*  370 */       this.last = pos;
/*      */     } 
/*  372 */     if (this.size++ >= this.maxFill) rehash(HashCommon.arraySize(this.size + 1, this.f));
/*      */     
/*  374 */     return true;
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
/*  387 */     int[] key = this.key; while (true) {
/*      */       int curr, last;
/*  389 */       pos = (last = pos) + 1 & this.mask;
/*      */       while (true) {
/*  391 */         if ((curr = key[pos]) == 0) {
/*  392 */           key[last] = 0;
/*      */           return;
/*      */         } 
/*  395 */         int slot = HashCommon.mix(this.strategy.hashCode(curr)) & this.mask;
/*  396 */         if ((last <= pos) ? (last >= slot || slot > pos) : (last >= slot && slot > pos))
/*  397 */           break;  pos = pos + 1 & this.mask;
/*      */       } 
/*  399 */       key[last] = curr;
/*  400 */       fixPointers(pos, last);
/*      */     } 
/*      */   }
/*      */   
/*      */   private boolean removeEntry(int pos) {
/*  405 */     this.size--;
/*  406 */     fixPointers(pos);
/*  407 */     shiftKeys(pos);
/*  408 */     if (this.n > this.minN && this.size < this.maxFill / 4 && this.n > 16) rehash(this.n / 2); 
/*  409 */     return true;
/*      */   }
/*      */   
/*      */   private boolean removeNullEntry() {
/*  413 */     this.containsNull = false;
/*  414 */     this.key[this.n] = 0;
/*  415 */     this.size--;
/*  416 */     fixPointers(this.n);
/*  417 */     if (this.n > this.minN && this.size < this.maxFill / 4 && this.n > 16) rehash(this.n / 2); 
/*  418 */     return true;
/*      */   }
/*      */ 
/*      */   
/*      */   public boolean remove(int k) {
/*  423 */     if (this.strategy.equals(k, 0)) {
/*  424 */       if (this.containsNull) return removeNullEntry(); 
/*  425 */       return false;
/*      */     } 
/*      */     
/*  428 */     int[] key = this.key;
/*      */     
/*      */     int curr, pos;
/*  431 */     if ((curr = key[pos = HashCommon.mix(this.strategy.hashCode(k)) & this.mask]) == 0) return false; 
/*  432 */     if (this.strategy.equals(k, curr)) return removeEntry(pos); 
/*      */     while (true) {
/*  434 */       if ((curr = key[pos = pos + 1 & this.mask]) == 0) return false; 
/*  435 */       if (this.strategy.equals(k, curr)) return removeEntry(pos);
/*      */     
/*      */     } 
/*      */   }
/*      */   
/*      */   public boolean contains(int k) {
/*  441 */     if (this.strategy.equals(k, 0)) return this.containsNull;
/*      */     
/*  443 */     int[] key = this.key;
/*      */     
/*      */     int curr, pos;
/*  446 */     if ((curr = key[pos = HashCommon.mix(this.strategy.hashCode(k)) & this.mask]) == 0) return false; 
/*  447 */     if (this.strategy.equals(k, curr)) return true; 
/*      */     while (true) {
/*  449 */       if ((curr = key[pos = pos + 1 & this.mask]) == 0) return false; 
/*  450 */       if (this.strategy.equals(k, curr)) return true;
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
/*  461 */     if (this.size == 0) throw new NoSuchElementException(); 
/*  462 */     int pos = this.first;
/*      */     
/*  464 */     if (this.size == 1) { this.first = this.last = -1; }
/*      */     else
/*  466 */     { this.first = (int)this.link[pos];
/*  467 */       if (0 <= this.first)
/*      */       {
/*  469 */         this.link[this.first] = this.link[this.first] | 0xFFFFFFFF00000000L;
/*      */       } }
/*      */     
/*  472 */     int k = this.key[pos];
/*  473 */     this.size--;
/*  474 */     if (this.strategy.equals(k, 0))
/*  475 */     { this.containsNull = false;
/*  476 */       this.key[this.n] = 0; }
/*  477 */     else { shiftKeys(pos); }
/*  478 */      if (this.n > this.minN && this.size < this.maxFill / 4 && this.n > 16) rehash(this.n / 2); 
/*  479 */     return k;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public int removeLastInt() {
/*  489 */     if (this.size == 0) throw new NoSuchElementException(); 
/*  490 */     int pos = this.last;
/*      */     
/*  492 */     if (this.size == 1) { this.first = this.last = -1; }
/*      */     else
/*  494 */     { this.last = (int)(this.link[pos] >>> 32L);
/*  495 */       if (0 <= this.last)
/*      */       {
/*  497 */         this.link[this.last] = this.link[this.last] | 0xFFFFFFFFL;
/*      */       } }
/*      */     
/*  500 */     int k = this.key[pos];
/*  501 */     this.size--;
/*  502 */     if (this.strategy.equals(k, 0))
/*  503 */     { this.containsNull = false;
/*  504 */       this.key[this.n] = 0; }
/*  505 */     else { shiftKeys(pos); }
/*  506 */      if (this.n > this.minN && this.size < this.maxFill / 4 && this.n > 16) rehash(this.n / 2); 
/*  507 */     return k;
/*      */   }
/*      */   
/*      */   private void moveIndexToFirst(int i) {
/*  511 */     if (this.size == 1 || this.first == i)
/*  512 */       return;  if (this.last == i) {
/*  513 */       this.last = (int)(this.link[i] >>> 32L);
/*      */       
/*  515 */       this.link[this.last] = this.link[this.last] | 0xFFFFFFFFL;
/*      */     } else {
/*  517 */       long linki = this.link[i];
/*  518 */       int prev = (int)(linki >>> 32L);
/*  519 */       int next = (int)linki;
/*  520 */       this.link[prev] = this.link[prev] ^ (this.link[prev] ^ linki & 0xFFFFFFFFL) & 0xFFFFFFFFL;
/*  521 */       this.link[next] = this.link[next] ^ (this.link[next] ^ linki & 0xFFFFFFFF00000000L) & 0xFFFFFFFF00000000L;
/*      */     } 
/*  523 */     this.link[this.first] = this.link[this.first] ^ (this.link[this.first] ^ (i & 0xFFFFFFFFL) << 32L) & 0xFFFFFFFF00000000L;
/*  524 */     this.link[i] = 0xFFFFFFFF00000000L | this.first & 0xFFFFFFFFL;
/*  525 */     this.first = i;
/*      */   }
/*      */   
/*      */   private void moveIndexToLast(int i) {
/*  529 */     if (this.size == 1 || this.last == i)
/*  530 */       return;  if (this.first == i) {
/*  531 */       this.first = (int)this.link[i];
/*      */       
/*  533 */       this.link[this.first] = this.link[this.first] | 0xFFFFFFFF00000000L;
/*      */     } else {
/*  535 */       long linki = this.link[i];
/*  536 */       int prev = (int)(linki >>> 32L);
/*  537 */       int next = (int)linki;
/*  538 */       this.link[prev] = this.link[prev] ^ (this.link[prev] ^ linki & 0xFFFFFFFFL) & 0xFFFFFFFFL;
/*  539 */       this.link[next] = this.link[next] ^ (this.link[next] ^ linki & 0xFFFFFFFF00000000L) & 0xFFFFFFFF00000000L;
/*      */     } 
/*  541 */     this.link[this.last] = this.link[this.last] ^ (this.link[this.last] ^ i & 0xFFFFFFFFL) & 0xFFFFFFFFL;
/*  542 */     this.link[i] = (this.last & 0xFFFFFFFFL) << 32L | 0xFFFFFFFFL;
/*  543 */     this.last = i;
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
/*  555 */     if (this.strategy.equals(k, 0)) {
/*  556 */       if (this.containsNull) {
/*  557 */         moveIndexToFirst(this.n);
/*  558 */         return false;
/*      */       } 
/*  560 */       this.containsNull = true;
/*  561 */       pos = this.n;
/*      */     } else {
/*      */       
/*  564 */       int[] key = this.key;
/*  565 */       pos = HashCommon.mix(this.strategy.hashCode(k)) & this.mask;
/*      */       
/*  567 */       while (key[pos] != 0) {
/*  568 */         if (this.strategy.equals(k, key[pos])) {
/*  569 */           moveIndexToFirst(pos);
/*  570 */           return false;
/*      */         } 
/*  572 */         pos = pos + 1 & this.mask;
/*      */       } 
/*      */     } 
/*  575 */     this.key[pos] = k;
/*  576 */     if (this.size == 0) {
/*  577 */       this.first = this.last = pos;
/*      */       
/*  579 */       this.link[pos] = -1L;
/*      */     } else {
/*  581 */       this.link[this.first] = this.link[this.first] ^ (this.link[this.first] ^ (pos & 0xFFFFFFFFL) << 32L) & 0xFFFFFFFF00000000L;
/*  582 */       this.link[pos] = 0xFFFFFFFF00000000L | this.first & 0xFFFFFFFFL;
/*  583 */       this.first = pos;
/*      */     } 
/*  585 */     if (this.size++ >= this.maxFill) rehash(HashCommon.arraySize(this.size, this.f));
/*      */     
/*  587 */     return true;
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
/*  599 */     if (this.strategy.equals(k, 0)) {
/*  600 */       if (this.containsNull) {
/*  601 */         moveIndexToLast(this.n);
/*  602 */         return false;
/*      */       } 
/*  604 */       this.containsNull = true;
/*  605 */       pos = this.n;
/*      */     } else {
/*      */       
/*  608 */       int[] key = this.key;
/*  609 */       pos = HashCommon.mix(this.strategy.hashCode(k)) & this.mask;
/*      */       
/*  611 */       while (key[pos] != 0) {
/*  612 */         if (this.strategy.equals(k, key[pos])) {
/*  613 */           moveIndexToLast(pos);
/*  614 */           return false;
/*      */         } 
/*  616 */         pos = pos + 1 & this.mask;
/*      */       } 
/*      */     } 
/*  619 */     this.key[pos] = k;
/*  620 */     if (this.size == 0) {
/*  621 */       this.first = this.last = pos;
/*      */       
/*  623 */       this.link[pos] = -1L;
/*      */     } else {
/*  625 */       this.link[this.last] = this.link[this.last] ^ (this.link[this.last] ^ pos & 0xFFFFFFFFL) & 0xFFFFFFFFL;
/*  626 */       this.link[pos] = (this.last & 0xFFFFFFFFL) << 32L | 0xFFFFFFFFL;
/*  627 */       this.last = pos;
/*      */     } 
/*  629 */     if (this.size++ >= this.maxFill) rehash(HashCommon.arraySize(this.size, this.f));
/*      */     
/*  631 */     return true;
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
/*  642 */     if (this.size == 0)
/*  643 */       return;  this.size = 0;
/*  644 */     this.containsNull = false;
/*  645 */     Arrays.fill(this.key, 0);
/*  646 */     this.first = this.last = -1;
/*      */   }
/*      */ 
/*      */   
/*      */   public int size() {
/*  651 */     return this.size;
/*      */   }
/*      */ 
/*      */   
/*      */   public boolean isEmpty() {
/*  656 */     return (this.size == 0);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected void fixPointers(int i) {
/*  666 */     if (this.size == 0) {
/*  667 */       this.first = this.last = -1;
/*      */       return;
/*      */     } 
/*  670 */     if (this.first == i) {
/*  671 */       this.first = (int)this.link[i];
/*  672 */       if (0 <= this.first)
/*      */       {
/*  674 */         this.link[this.first] = this.link[this.first] | 0xFFFFFFFF00000000L;
/*      */       }
/*      */       return;
/*      */     } 
/*  678 */     if (this.last == i) {
/*  679 */       this.last = (int)(this.link[i] >>> 32L);
/*  680 */       if (0 <= this.last)
/*      */       {
/*  682 */         this.link[this.last] = this.link[this.last] | 0xFFFFFFFFL;
/*      */       }
/*      */       return;
/*      */     } 
/*  686 */     long linki = this.link[i];
/*  687 */     int prev = (int)(linki >>> 32L);
/*  688 */     int next = (int)linki;
/*  689 */     this.link[prev] = this.link[prev] ^ (this.link[prev] ^ linki & 0xFFFFFFFFL) & 0xFFFFFFFFL;
/*  690 */     this.link[next] = this.link[next] ^ (this.link[next] ^ linki & 0xFFFFFFFF00000000L) & 0xFFFFFFFF00000000L;
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
/*  701 */     if (this.size == 1) {
/*  702 */       this.first = this.last = d;
/*      */       
/*  704 */       this.link[d] = -1L;
/*      */       return;
/*      */     } 
/*  707 */     if (this.first == s) {
/*  708 */       this.first = d;
/*  709 */       this.link[(int)this.link[s]] = this.link[(int)this.link[s]] ^ (this.link[(int)this.link[s]] ^ (d & 0xFFFFFFFFL) << 32L) & 0xFFFFFFFF00000000L;
/*  710 */       this.link[d] = this.link[s];
/*      */       return;
/*      */     } 
/*  713 */     if (this.last == s) {
/*  714 */       this.last = d;
/*  715 */       this.link[(int)(this.link[s] >>> 32L)] = this.link[(int)(this.link[s] >>> 32L)] ^ (this.link[(int)(this.link[s] >>> 32L)] ^ d & 0xFFFFFFFFL) & 0xFFFFFFFFL;
/*  716 */       this.link[d] = this.link[s];
/*      */       return;
/*      */     } 
/*  719 */     long links = this.link[s];
/*  720 */     int prev = (int)(links >>> 32L);
/*  721 */     int next = (int)links;
/*  722 */     this.link[prev] = this.link[prev] ^ (this.link[prev] ^ d & 0xFFFFFFFFL) & 0xFFFFFFFFL;
/*  723 */     this.link[next] = this.link[next] ^ (this.link[next] ^ (d & 0xFFFFFFFFL) << 32L) & 0xFFFFFFFF00000000L;
/*  724 */     this.link[d] = links;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public int firstInt() {
/*  734 */     if (this.size == 0) throw new NoSuchElementException(); 
/*  735 */     return this.key[this.first];
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public int lastInt() {
/*  745 */     if (this.size == 0) throw new NoSuchElementException(); 
/*  746 */     return this.key[this.last];
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public IntSortedSet tailSet(int from) {
/*  756 */     throw new UnsupportedOperationException();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public IntSortedSet headSet(int to) {
/*  766 */     throw new UnsupportedOperationException();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public IntSortedSet subSet(int from, int to) {
/*  776 */     throw new UnsupportedOperationException();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public IntComparator comparator() {
/*  786 */     return null;
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
/*  801 */     int prev = -1;
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  806 */     int next = -1;
/*      */     
/*  808 */     int curr = -1;
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  813 */     int index = -1;
/*      */     
/*      */     SetIterator() {
/*  816 */       this.next = IntLinkedOpenCustomHashSet.this.first;
/*  817 */       this.index = 0;
/*      */     }
/*      */     
/*      */     SetIterator(int from) {
/*  821 */       if (IntLinkedOpenCustomHashSet.this.strategy.equals(from, 0)) {
/*  822 */         if (IntLinkedOpenCustomHashSet.this.containsNull) {
/*  823 */           this.next = (int)IntLinkedOpenCustomHashSet.this.link[IntLinkedOpenCustomHashSet.this.n];
/*  824 */           this.prev = IntLinkedOpenCustomHashSet.this.n; return;
/*      */         } 
/*  826 */         throw new NoSuchElementException("The key " + from + " does not belong to this set.");
/*      */       } 
/*  828 */       if (IntLinkedOpenCustomHashSet.this.strategy.equals(IntLinkedOpenCustomHashSet.this.key[IntLinkedOpenCustomHashSet.this.last], from)) {
/*  829 */         this.prev = IntLinkedOpenCustomHashSet.this.last;
/*  830 */         this.index = IntLinkedOpenCustomHashSet.this.size;
/*      */         
/*      */         return;
/*      */       } 
/*  834 */       int[] key = IntLinkedOpenCustomHashSet.this.key;
/*  835 */       int pos = HashCommon.mix(IntLinkedOpenCustomHashSet.this.strategy.hashCode(from)) & IntLinkedOpenCustomHashSet.this.mask;
/*      */       
/*  837 */       while (key[pos] != 0) {
/*  838 */         if (IntLinkedOpenCustomHashSet.this.strategy.equals(key[pos], from)) {
/*      */           
/*  840 */           this.next = (int)IntLinkedOpenCustomHashSet.this.link[pos];
/*  841 */           this.prev = pos;
/*      */           return;
/*      */         } 
/*  844 */         pos = pos + 1 & IntLinkedOpenCustomHashSet.this.mask;
/*      */       } 
/*  846 */       throw new NoSuchElementException("The key " + from + " does not belong to this set.");
/*      */     }
/*      */ 
/*      */     
/*      */     public boolean hasNext() {
/*  851 */       return (this.next != -1);
/*      */     }
/*      */ 
/*      */     
/*      */     public boolean hasPrevious() {
/*  856 */       return (this.prev != -1);
/*      */     }
/*      */ 
/*      */     
/*      */     public int nextInt() {
/*  861 */       if (!hasNext()) throw new NoSuchElementException(); 
/*  862 */       this.curr = this.next;
/*  863 */       this.next = (int)IntLinkedOpenCustomHashSet.this.link[this.curr];
/*  864 */       this.prev = this.curr;
/*  865 */       if (this.index >= 0) this.index++;
/*      */       
/*  867 */       return IntLinkedOpenCustomHashSet.this.key[this.curr];
/*      */     }
/*      */ 
/*      */     
/*      */     public int previousInt() {
/*  872 */       if (!hasPrevious()) throw new NoSuchElementException(); 
/*  873 */       this.curr = this.prev;
/*  874 */       this.prev = (int)(IntLinkedOpenCustomHashSet.this.link[this.curr] >>> 32L);
/*  875 */       this.next = this.curr;
/*  876 */       if (this.index >= 0) this.index--; 
/*  877 */       return IntLinkedOpenCustomHashSet.this.key[this.curr];
/*      */     }
/*      */ 
/*      */     
/*      */     public void forEachRemaining(IntConsumer action) {
/*  882 */       int[] key = IntLinkedOpenCustomHashSet.this.key;
/*  883 */       long[] link = IntLinkedOpenCustomHashSet.this.link;
/*  884 */       while (this.next != -1) {
/*  885 */         this.curr = this.next;
/*  886 */         this.next = (int)link[this.curr];
/*  887 */         this.prev = this.curr;
/*  888 */         if (this.index >= 0) this.index++;
/*      */         
/*  890 */         action.accept(key[this.curr]);
/*      */       } 
/*      */     }
/*      */     
/*      */     private final void ensureIndexKnown() {
/*  895 */       if (this.index >= 0)
/*  896 */         return;  if (this.prev == -1) {
/*  897 */         this.index = 0;
/*      */         return;
/*      */       } 
/*  900 */       if (this.next == -1) {
/*  901 */         this.index = IntLinkedOpenCustomHashSet.this.size;
/*      */         return;
/*      */       } 
/*  904 */       int pos = IntLinkedOpenCustomHashSet.this.first;
/*  905 */       this.index = 1;
/*  906 */       while (pos != this.prev) {
/*  907 */         pos = (int)IntLinkedOpenCustomHashSet.this.link[pos];
/*  908 */         this.index++;
/*      */       } 
/*      */     }
/*      */ 
/*      */     
/*      */     public int nextIndex() {
/*  914 */       ensureIndexKnown();
/*  915 */       return this.index;
/*      */     }
/*      */ 
/*      */     
/*      */     public int previousIndex() {
/*  920 */       ensureIndexKnown();
/*  921 */       return this.index - 1;
/*      */     }
/*      */ 
/*      */     
/*      */     public void remove() {
/*  926 */       ensureIndexKnown();
/*  927 */       if (this.curr == -1) throw new IllegalStateException(); 
/*  928 */       if (this.curr == this.prev)
/*      */       
/*      */       { 
/*  931 */         this.index--;
/*  932 */         this.prev = (int)(IntLinkedOpenCustomHashSet.this.link[this.curr] >>> 32L); }
/*  933 */       else { this.next = (int)IntLinkedOpenCustomHashSet.this.link[this.curr]; }
/*  934 */        IntLinkedOpenCustomHashSet.this.size--;
/*      */ 
/*      */       
/*  937 */       if (this.prev == -1) { IntLinkedOpenCustomHashSet.this.first = this.next; }
/*  938 */       else { IntLinkedOpenCustomHashSet.this.link[this.prev] = IntLinkedOpenCustomHashSet.this.link[this.prev] ^ (IntLinkedOpenCustomHashSet.this.link[this.prev] ^ this.next & 0xFFFFFFFFL) & 0xFFFFFFFFL; }
/*  939 */        if (this.next == -1) { IntLinkedOpenCustomHashSet.this.last = this.prev; }
/*  940 */       else { IntLinkedOpenCustomHashSet.this.link[this.next] = IntLinkedOpenCustomHashSet.this.link[this.next] ^ (IntLinkedOpenCustomHashSet.this.link[this.next] ^ (this.prev & 0xFFFFFFFFL) << 32L) & 0xFFFFFFFF00000000L; }
/*  941 */        int pos = this.curr;
/*  942 */       this.curr = -1;
/*  943 */       if (pos == IntLinkedOpenCustomHashSet.this.n) {
/*  944 */         IntLinkedOpenCustomHashSet.this.containsNull = false;
/*  945 */         IntLinkedOpenCustomHashSet.this.key[IntLinkedOpenCustomHashSet.this.n] = 0;
/*      */       } else {
/*      */         
/*  948 */         int[] key = IntLinkedOpenCustomHashSet.this.key;
/*      */         while (true) {
/*      */           int curr, last;
/*  951 */           pos = (last = pos) + 1 & IntLinkedOpenCustomHashSet.this.mask;
/*      */           while (true) {
/*  953 */             if ((curr = key[pos]) == 0) {
/*  954 */               key[last] = 0;
/*      */               return;
/*      */             } 
/*  957 */             int slot = HashCommon.mix(IntLinkedOpenCustomHashSet.this.strategy.hashCode(curr)) & IntLinkedOpenCustomHashSet.this.mask;
/*  958 */             if ((last <= pos) ? (last >= slot || slot > pos) : (last >= slot && slot > pos))
/*  959 */               break;  pos = pos + 1 & IntLinkedOpenCustomHashSet.this.mask;
/*      */           } 
/*  961 */           key[last] = curr;
/*  962 */           if (this.next == pos) this.next = last; 
/*  963 */           if (this.prev == pos) this.prev = last; 
/*  964 */           IntLinkedOpenCustomHashSet.this.fixPointers(pos, last);
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
/*  980 */     return new SetIterator(from);
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
/*  991 */     return new SetIterator();
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
/* 1011 */     return IntSpliterators.asSpliterator(iterator(), Size64.sizeOf(this), 337);
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public void forEach(IntConsumer action) {
/* 1017 */     int next = this.first;
/* 1018 */     while (next != -1) {
/* 1019 */       int curr = next;
/* 1020 */       next = (int)this.link[curr];
/*      */       
/* 1022 */       action.accept(this.key[curr]);
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
/* 1040 */     return trim(this.size);
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
/* 1062 */     int l = HashCommon.nextPowerOfTwo((int)Math.ceil((n / this.f)));
/* 1063 */     if (l >= this.n || this.size > HashCommon.maxFill(l, this.f)) return true; 
/*      */     try {
/* 1065 */       rehash(l);
/* 1066 */     } catch (OutOfMemoryError cantDoIt) {
/* 1067 */       return false;
/*      */     } 
/* 1069 */     return true;
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
/* 1084 */     int[] key = this.key;
/* 1085 */     int mask = newN - 1;
/* 1086 */     int[] newKey = new int[newN + 1];
/* 1087 */     int i = this.first, prev = -1, newPrev = -1;
/* 1088 */     long[] link = this.link;
/* 1089 */     long[] newLink = new long[newN + 1];
/* 1090 */     this.first = -1;
/* 1091 */     for (int j = this.size; j-- != 0; ) {
/* 1092 */       int pos; if (this.strategy.equals(key[i], 0)) { pos = newN; }
/*      */       else
/* 1094 */       { pos = HashCommon.mix(this.strategy.hashCode(key[i])) & mask;
/* 1095 */         for (; newKey[pos] != 0; pos = pos + 1 & mask); }
/*      */       
/* 1097 */       newKey[pos] = key[i];
/* 1098 */       if (prev != -1) {
/* 1099 */         newLink[newPrev] = newLink[newPrev] ^ (newLink[newPrev] ^ pos & 0xFFFFFFFFL) & 0xFFFFFFFFL;
/* 1100 */         newLink[pos] = newLink[pos] ^ (newLink[pos] ^ (newPrev & 0xFFFFFFFFL) << 32L) & 0xFFFFFFFF00000000L;
/* 1101 */         newPrev = pos;
/*      */       } else {
/* 1103 */         newPrev = this.first = pos;
/*      */         
/* 1105 */         newLink[pos] = -1L;
/*      */       } 
/* 1107 */       int t = i;
/* 1108 */       i = (int)link[i];
/* 1109 */       prev = t;
/*      */     } 
/* 1111 */     this.link = newLink;
/* 1112 */     this.last = newPrev;
/* 1113 */     if (newPrev != -1)
/*      */     {
/* 1115 */       newLink[newPrev] = newLink[newPrev] | 0xFFFFFFFFL; } 
/* 1116 */     this.n = newN;
/* 1117 */     this.mask = mask;
/* 1118 */     this.maxFill = HashCommon.maxFill(this.n, this.f);
/* 1119 */     this.key = newKey;
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
/*      */   public IntLinkedOpenCustomHashSet clone() {
/*      */     IntLinkedOpenCustomHashSet c;
/*      */     try {
/* 1136 */       c = (IntLinkedOpenCustomHashSet)super.clone();
/* 1137 */     } catch (CloneNotSupportedException cantHappen) {
/* 1138 */       throw new InternalError();
/*      */     } 
/* 1140 */     c.key = (int[])this.key.clone();
/* 1141 */     c.containsNull = this.containsNull;
/* 1142 */     c.link = (long[])this.link.clone();
/* 1143 */     c.strategy = this.strategy;
/* 1144 */     return c;
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
/* 1158 */     int h = 0;
/* 1159 */     for (int j = realSize(), i = 0; j-- != 0; ) {
/* 1160 */       for (; this.key[i] == 0; i++);
/* 1161 */       h += this.strategy.hashCode(this.key[i]);
/* 1162 */       i++;
/*      */     } 
/*      */     
/* 1165 */     return h;
/*      */   }
/*      */   
/*      */   private void writeObject(ObjectOutputStream s) throws IOException {
/* 1169 */     IntIterator i = iterator();
/* 1170 */     s.defaultWriteObject();
/* 1171 */     for (int j = this.size; j-- != 0; s.writeInt(i.nextInt()));
/*      */   }
/*      */   
/*      */   private void readObject(ObjectInputStream s) throws IOException, ClassNotFoundException {
/* 1175 */     s.defaultReadObject();
/* 1176 */     this.n = HashCommon.arraySize(this.size, this.f);
/* 1177 */     this.maxFill = HashCommon.maxFill(this.n, this.f);
/* 1178 */     this.mask = this.n - 1;
/* 1179 */     int[] key = this.key = new int[this.n + 1];
/* 1180 */     long[] link = this.link = new long[this.n + 1];
/* 1181 */     int prev = -1;
/* 1182 */     this.first = this.last = -1;
/*      */     
/* 1184 */     for (int i = this.size; i-- != 0; ) {
/* 1185 */       int pos, k = s.readInt();
/* 1186 */       if (this.strategy.equals(k, 0))
/* 1187 */       { pos = this.n;
/* 1188 */         this.containsNull = true; }
/*      */       
/* 1190 */       else if (key[pos = HashCommon.mix(this.strategy.hashCode(k)) & this.mask] != 0) { while (key[pos = pos + 1 & this.mask] != 0); }
/*      */       
/* 1192 */       key[pos] = k;
/* 1193 */       if (this.first != -1) {
/* 1194 */         link[prev] = link[prev] ^ (link[prev] ^ pos & 0xFFFFFFFFL) & 0xFFFFFFFFL;
/* 1195 */         link[pos] = link[pos] ^ (link[pos] ^ (prev & 0xFFFFFFFFL) << 32L) & 0xFFFFFFFF00000000L;
/* 1196 */         prev = pos; continue;
/*      */       } 
/* 1198 */       prev = this.first = pos;
/*      */       
/* 1200 */       link[pos] = link[pos] | 0xFFFFFFFF00000000L;
/*      */     } 
/*      */     
/* 1203 */     this.last = prev;
/* 1204 */     if (prev != -1)
/*      */     {
/* 1206 */       link[prev] = link[prev] | 0xFFFFFFFFL;
/*      */     }
/*      */   }
/*      */   
/*      */   private void checkTable() {}
/*      */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\i\\unimi\dsi\fastutil\ints\IntLinkedOpenCustomHashSet.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */