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
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ public class ObjectLinkedOpenCustomHashSet<K>
/*      */   extends AbstractObjectSortedSet<K>
/*      */   implements Serializable, Cloneable, Hash
/*      */ {
/*      */   private static final long serialVersionUID = 0L;
/*      */   private static final boolean ASSERTS = false;
/*      */   protected transient K[] key;
/*      */   protected transient int mask;
/*      */   protected transient boolean containsNull;
/*      */   protected Hash.Strategy<? super K> strategy;
/*   92 */   protected transient int first = -1;
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*   97 */   protected transient int last = -1;
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
/*      */   private static final int SPLITERATOR_CHARACTERISTICS = 81;
/*      */ 
/*      */ 
/*      */   
/*      */   public ObjectLinkedOpenCustomHashSet(int expected, float f, Hash.Strategy<? super K> strategy) {
/*  129 */     this.strategy = strategy;
/*  130 */     if (f <= 0.0F || f >= 1.0F) throw new IllegalArgumentException("Load factor must be greater than 0 and smaller than 1"); 
/*  131 */     if (expected < 0) throw new IllegalArgumentException("The expected number of elements must be nonnegative"); 
/*  132 */     this.f = f;
/*  133 */     this.minN = this.n = HashCommon.arraySize(expected, f);
/*  134 */     this.mask = this.n - 1;
/*  135 */     this.maxFill = HashCommon.maxFill(this.n, f);
/*  136 */     this.key = (K[])new Object[this.n + 1];
/*  137 */     this.link = new long[this.n + 1];
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public ObjectLinkedOpenCustomHashSet(int expected, Hash.Strategy<? super K> strategy) {
/*  147 */     this(expected, 0.75F, strategy);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public ObjectLinkedOpenCustomHashSet(Hash.Strategy<? super K> strategy) {
/*  157 */     this(16, 0.75F, strategy);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public ObjectLinkedOpenCustomHashSet(Collection<? extends K> c, float f, Hash.Strategy<? super K> strategy) {
/*  168 */     this(c.size(), f, strategy);
/*  169 */     addAll(c);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public ObjectLinkedOpenCustomHashSet(Collection<? extends K> c, Hash.Strategy<? super K> strategy) {
/*  180 */     this(c, 0.75F, strategy);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public ObjectLinkedOpenCustomHashSet(ObjectCollection<? extends K> c, float f, Hash.Strategy<? super K> strategy) {
/*  191 */     this(c.size(), f, strategy);
/*  192 */     addAll(c);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public ObjectLinkedOpenCustomHashSet(ObjectCollection<? extends K> c, Hash.Strategy<? super K> strategy) {
/*  203 */     this(c, 0.75F, strategy);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public ObjectLinkedOpenCustomHashSet(Iterator<? extends K> i, float f, Hash.Strategy<? super K> strategy) {
/*  214 */     this(16, f, strategy);
/*  215 */     for (; i.hasNext(); add(i.next()));
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public ObjectLinkedOpenCustomHashSet(Iterator<? extends K> i, Hash.Strategy<? super K> strategy) {
/*  226 */     this(i, 0.75F, strategy);
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
/*      */   public ObjectLinkedOpenCustomHashSet(K[] a, int offset, int length, float f, Hash.Strategy<? super K> strategy) {
/*  239 */     this((length < 0) ? 0 : length, f, strategy);
/*  240 */     ObjectArrays.ensureOffsetLength(a, offset, length);
/*  241 */     for (int i = 0; i < length; ) { add(a[offset + i]); i++; }
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
/*      */   public ObjectLinkedOpenCustomHashSet(K[] a, int offset, int length, Hash.Strategy<? super K> strategy) {
/*  254 */     this(a, offset, length, 0.75F, strategy);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public ObjectLinkedOpenCustomHashSet(K[] a, float f, Hash.Strategy<? super K> strategy) {
/*  265 */     this(a, 0, a.length, f, strategy);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public ObjectLinkedOpenCustomHashSet(K[] a, Hash.Strategy<? super K> strategy) {
/*  276 */     this(a, 0.75F, strategy);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Hash.Strategy<? super K> strategy() {
/*  285 */     return this.strategy;
/*      */   }
/*      */   
/*      */   private int realSize() {
/*  289 */     return this.containsNull ? (this.size - 1) : this.size;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void ensureCapacity(int capacity) {
/*  299 */     int needed = HashCommon.arraySize(capacity, this.f);
/*  300 */     if (needed > this.n) rehash(needed); 
/*      */   }
/*      */   
/*      */   private void tryCapacity(long capacity) {
/*  304 */     int needed = (int)Math.min(1073741824L, Math.max(2L, HashCommon.nextPowerOfTwo((long)Math.ceil(((float)capacity / this.f)))));
/*  305 */     if (needed > this.n) rehash(needed);
/*      */   
/*      */   }
/*      */ 
/*      */   
/*      */   public boolean addAll(Collection<? extends K> c) {
/*  311 */     if (this.f <= 0.5D) { ensureCapacity(c.size()); }
/*  312 */     else { tryCapacity((size() + c.size())); }
/*      */     
/*  314 */     return super.addAll(c);
/*      */   }
/*      */ 
/*      */   
/*      */   public boolean add(K k) {
/*      */     int pos;
/*  320 */     if (this.strategy.equals(k, null)) {
/*  321 */       if (this.containsNull) return false; 
/*  322 */       pos = this.n;
/*  323 */       this.containsNull = true;
/*  324 */       this.key[this.n] = k;
/*      */     } else {
/*      */       
/*  327 */       K[] key = this.key;
/*      */       K curr;
/*  329 */       if ((curr = key[pos = HashCommon.mix(this.strategy.hashCode(k)) & this.mask]) != null) {
/*  330 */         if (this.strategy.equals(curr, k)) return false; 
/*  331 */         while ((curr = key[pos = pos + 1 & this.mask]) != null) { if (this.strategy.equals(curr, k)) return false;  }
/*      */       
/*  333 */       }  key[pos] = k;
/*      */     } 
/*  335 */     if (this.size == 0) {
/*  336 */       this.first = this.last = pos;
/*      */       
/*  338 */       this.link[pos] = -1L;
/*      */     } else {
/*  340 */       this.link[this.last] = this.link[this.last] ^ (this.link[this.last] ^ pos & 0xFFFFFFFFL) & 0xFFFFFFFFL;
/*  341 */       this.link[pos] = (this.last & 0xFFFFFFFFL) << 32L | 0xFFFFFFFFL;
/*  342 */       this.last = pos;
/*      */     } 
/*  344 */     if (this.size++ >= this.maxFill) rehash(HashCommon.arraySize(this.size + 1, this.f));
/*      */     
/*  346 */     return true;
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
/*  364 */     if (this.strategy.equals(k, null)) {
/*  365 */       if (this.containsNull) return this.key[this.n]; 
/*  366 */       pos = this.n;
/*  367 */       this.containsNull = true;
/*  368 */       this.key[this.n] = k;
/*      */     } else {
/*      */       
/*  371 */       K[] key = this.key;
/*      */       K curr;
/*  373 */       if ((curr = key[pos = HashCommon.mix(this.strategy.hashCode(k)) & this.mask]) != null) {
/*  374 */         if (this.strategy.equals(curr, k)) return curr; 
/*  375 */         while ((curr = key[pos = pos + 1 & this.mask]) != null) { if (this.strategy.equals(curr, k)) return curr;  }
/*      */       
/*  377 */       }  key[pos] = k;
/*      */     } 
/*  379 */     if (this.size == 0) {
/*  380 */       this.first = this.last = pos;
/*      */       
/*  382 */       this.link[pos] = -1L;
/*      */     } else {
/*  384 */       this.link[this.last] = this.link[this.last] ^ (this.link[this.last] ^ pos & 0xFFFFFFFFL) & 0xFFFFFFFFL;
/*  385 */       this.link[pos] = (this.last & 0xFFFFFFFFL) << 32L | 0xFFFFFFFFL;
/*  386 */       this.last = pos;
/*      */     } 
/*  388 */     if (this.size++ >= this.maxFill) rehash(HashCommon.arraySize(this.size + 1, this.f));
/*      */     
/*  390 */     return k;
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
/*  403 */     K[] key = this.key; while (true) {
/*      */       K curr; int last;
/*  405 */       pos = (last = pos) + 1 & this.mask;
/*      */       while (true) {
/*  407 */         if ((curr = key[pos]) == null) {
/*  408 */           key[last] = null;
/*      */           return;
/*      */         } 
/*  411 */         int slot = HashCommon.mix(this.strategy.hashCode(curr)) & this.mask;
/*  412 */         if ((last <= pos) ? (last >= slot || slot > pos) : (last >= slot && slot > pos))
/*  413 */           break;  pos = pos + 1 & this.mask;
/*      */       } 
/*  415 */       key[last] = curr;
/*  416 */       fixPointers(pos, last);
/*      */     } 
/*      */   }
/*      */   
/*      */   private boolean removeEntry(int pos) {
/*  421 */     this.size--;
/*  422 */     fixPointers(pos);
/*  423 */     shiftKeys(pos);
/*  424 */     if (this.n > this.minN && this.size < this.maxFill / 4 && this.n > 16) rehash(this.n / 2); 
/*  425 */     return true;
/*      */   }
/*      */   
/*      */   private boolean removeNullEntry() {
/*  429 */     this.containsNull = false;
/*  430 */     this.key[this.n] = null;
/*  431 */     this.size--;
/*  432 */     fixPointers(this.n);
/*  433 */     if (this.n > this.minN && this.size < this.maxFill / 4 && this.n > 16) rehash(this.n / 2); 
/*  434 */     return true;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean remove(Object k) {
/*  440 */     if (this.strategy.equals(k, null)) {
/*  441 */       if (this.containsNull) return removeNullEntry(); 
/*  442 */       return false;
/*      */     } 
/*      */     
/*  445 */     K[] key = this.key;
/*      */     K curr;
/*      */     int pos;
/*  448 */     if ((curr = key[pos = HashCommon.mix(this.strategy.hashCode(k)) & this.mask]) == null) return false; 
/*  449 */     if (this.strategy.equals(k, curr)) return removeEntry(pos); 
/*      */     while (true) {
/*  451 */       if ((curr = key[pos = pos + 1 & this.mask]) == null) return false; 
/*  452 */       if (this.strategy.equals(k, curr)) return removeEntry(pos);
/*      */     
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   public boolean contains(Object k) {
/*  459 */     if (this.strategy.equals(k, null)) return this.containsNull;
/*      */     
/*  461 */     K[] key = this.key;
/*      */     K curr;
/*      */     int pos;
/*  464 */     if ((curr = key[pos = HashCommon.mix(this.strategy.hashCode(k)) & this.mask]) == null) return false; 
/*  465 */     if (this.strategy.equals(k, curr)) return true; 
/*      */     while (true) {
/*  467 */       if ((curr = key[pos = pos + 1 & this.mask]) == null) return false; 
/*  468 */       if (this.strategy.equals(k, curr)) return true;
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
/*  479 */     if (this.strategy.equals(k, null)) return this.key[this.n];
/*      */ 
/*      */     
/*  482 */     K[] key = this.key;
/*      */     K curr;
/*      */     int pos;
/*  485 */     if ((curr = key[pos = HashCommon.mix(this.strategy.hashCode(k)) & this.mask]) == null) return null; 
/*  486 */     if (this.strategy.equals(k, curr)) return curr;
/*      */     
/*      */     while (true) {
/*  489 */       if ((curr = key[pos = pos + 1 & this.mask]) == null) return null; 
/*  490 */       if (this.strategy.equals(k, curr)) return curr;
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
/*  501 */     if (this.size == 0) throw new NoSuchElementException(); 
/*  502 */     int pos = this.first;
/*      */     
/*  504 */     if (this.size == 1) { this.first = this.last = -1; }
/*      */     else
/*  506 */     { this.first = (int)this.link[pos];
/*  507 */       if (0 <= this.first)
/*      */       {
/*  509 */         this.link[this.first] = this.link[this.first] | 0xFFFFFFFF00000000L;
/*      */       } }
/*      */     
/*  512 */     K k = this.key[pos];
/*  513 */     this.size--;
/*  514 */     if (this.strategy.equals(k, null))
/*  515 */     { this.containsNull = false;
/*  516 */       this.key[this.n] = null; }
/*  517 */     else { shiftKeys(pos); }
/*  518 */      if (this.n > this.minN && this.size < this.maxFill / 4 && this.n > 16) rehash(this.n / 2); 
/*  519 */     return k;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public K removeLast() {
/*  529 */     if (this.size == 0) throw new NoSuchElementException(); 
/*  530 */     int pos = this.last;
/*      */     
/*  532 */     if (this.size == 1) { this.first = this.last = -1; }
/*      */     else
/*  534 */     { this.last = (int)(this.link[pos] >>> 32L);
/*  535 */       if (0 <= this.last)
/*      */       {
/*  537 */         this.link[this.last] = this.link[this.last] | 0xFFFFFFFFL;
/*      */       } }
/*      */     
/*  540 */     K k = this.key[pos];
/*  541 */     this.size--;
/*  542 */     if (this.strategy.equals(k, null))
/*  543 */     { this.containsNull = false;
/*  544 */       this.key[this.n] = null; }
/*  545 */     else { shiftKeys(pos); }
/*  546 */      if (this.n > this.minN && this.size < this.maxFill / 4 && this.n > 16) rehash(this.n / 2); 
/*  547 */     return k;
/*      */   }
/*      */   
/*      */   private void moveIndexToFirst(int i) {
/*  551 */     if (this.size == 1 || this.first == i)
/*  552 */       return;  if (this.last == i) {
/*  553 */       this.last = (int)(this.link[i] >>> 32L);
/*      */       
/*  555 */       this.link[this.last] = this.link[this.last] | 0xFFFFFFFFL;
/*      */     } else {
/*  557 */       long linki = this.link[i];
/*  558 */       int prev = (int)(linki >>> 32L);
/*  559 */       int next = (int)linki;
/*  560 */       this.link[prev] = this.link[prev] ^ (this.link[prev] ^ linki & 0xFFFFFFFFL) & 0xFFFFFFFFL;
/*  561 */       this.link[next] = this.link[next] ^ (this.link[next] ^ linki & 0xFFFFFFFF00000000L) & 0xFFFFFFFF00000000L;
/*      */     } 
/*  563 */     this.link[this.first] = this.link[this.first] ^ (this.link[this.first] ^ (i & 0xFFFFFFFFL) << 32L) & 0xFFFFFFFF00000000L;
/*  564 */     this.link[i] = 0xFFFFFFFF00000000L | this.first & 0xFFFFFFFFL;
/*  565 */     this.first = i;
/*      */   }
/*      */   
/*      */   private void moveIndexToLast(int i) {
/*  569 */     if (this.size == 1 || this.last == i)
/*  570 */       return;  if (this.first == i) {
/*  571 */       this.first = (int)this.link[i];
/*      */       
/*  573 */       this.link[this.first] = this.link[this.first] | 0xFFFFFFFF00000000L;
/*      */     } else {
/*  575 */       long linki = this.link[i];
/*  576 */       int prev = (int)(linki >>> 32L);
/*  577 */       int next = (int)linki;
/*  578 */       this.link[prev] = this.link[prev] ^ (this.link[prev] ^ linki & 0xFFFFFFFFL) & 0xFFFFFFFFL;
/*  579 */       this.link[next] = this.link[next] ^ (this.link[next] ^ linki & 0xFFFFFFFF00000000L) & 0xFFFFFFFF00000000L;
/*      */     } 
/*  581 */     this.link[this.last] = this.link[this.last] ^ (this.link[this.last] ^ i & 0xFFFFFFFFL) & 0xFFFFFFFFL;
/*  582 */     this.link[i] = (this.last & 0xFFFFFFFFL) << 32L | 0xFFFFFFFFL;
/*  583 */     this.last = i;
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
/*  595 */     if (this.strategy.equals(k, null)) {
/*  596 */       if (this.containsNull) {
/*  597 */         moveIndexToFirst(this.n);
/*  598 */         return false;
/*      */       } 
/*  600 */       this.containsNull = true;
/*  601 */       pos = this.n;
/*      */     } else {
/*      */       
/*  604 */       K[] key = this.key;
/*  605 */       pos = HashCommon.mix(this.strategy.hashCode(k)) & this.mask;
/*      */       
/*  607 */       while (key[pos] != null) {
/*  608 */         if (this.strategy.equals(k, key[pos])) {
/*  609 */           moveIndexToFirst(pos);
/*  610 */           return false;
/*      */         } 
/*  612 */         pos = pos + 1 & this.mask;
/*      */       } 
/*      */     } 
/*  615 */     this.key[pos] = k;
/*  616 */     if (this.size == 0) {
/*  617 */       this.first = this.last = pos;
/*      */       
/*  619 */       this.link[pos] = -1L;
/*      */     } else {
/*  621 */       this.link[this.first] = this.link[this.first] ^ (this.link[this.first] ^ (pos & 0xFFFFFFFFL) << 32L) & 0xFFFFFFFF00000000L;
/*  622 */       this.link[pos] = 0xFFFFFFFF00000000L | this.first & 0xFFFFFFFFL;
/*  623 */       this.first = pos;
/*      */     } 
/*  625 */     if (this.size++ >= this.maxFill) rehash(HashCommon.arraySize(this.size, this.f));
/*      */     
/*  627 */     return true;
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
/*  639 */     if (this.strategy.equals(k, null)) {
/*  640 */       if (this.containsNull) {
/*  641 */         moveIndexToLast(this.n);
/*  642 */         return false;
/*      */       } 
/*  644 */       this.containsNull = true;
/*  645 */       pos = this.n;
/*      */     } else {
/*      */       
/*  648 */       K[] key = this.key;
/*  649 */       pos = HashCommon.mix(this.strategy.hashCode(k)) & this.mask;
/*      */       
/*  651 */       while (key[pos] != null) {
/*  652 */         if (this.strategy.equals(k, key[pos])) {
/*  653 */           moveIndexToLast(pos);
/*  654 */           return false;
/*      */         } 
/*  656 */         pos = pos + 1 & this.mask;
/*      */       } 
/*      */     } 
/*  659 */     this.key[pos] = k;
/*  660 */     if (this.size == 0) {
/*  661 */       this.first = this.last = pos;
/*      */       
/*  663 */       this.link[pos] = -1L;
/*      */     } else {
/*  665 */       this.link[this.last] = this.link[this.last] ^ (this.link[this.last] ^ pos & 0xFFFFFFFFL) & 0xFFFFFFFFL;
/*  666 */       this.link[pos] = (this.last & 0xFFFFFFFFL) << 32L | 0xFFFFFFFFL;
/*  667 */       this.last = pos;
/*      */     } 
/*  669 */     if (this.size++ >= this.maxFill) rehash(HashCommon.arraySize(this.size, this.f));
/*      */     
/*  671 */     return true;
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
/*  682 */     if (this.size == 0)
/*  683 */       return;  this.size = 0;
/*  684 */     this.containsNull = false;
/*  685 */     Arrays.fill((Object[])this.key, (Object)null);
/*  686 */     this.first = this.last = -1;
/*      */   }
/*      */ 
/*      */   
/*      */   public int size() {
/*  691 */     return this.size;
/*      */   }
/*      */ 
/*      */   
/*      */   public boolean isEmpty() {
/*  696 */     return (this.size == 0);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected void fixPointers(int i) {
/*  706 */     if (this.size == 0) {
/*  707 */       this.first = this.last = -1;
/*      */       return;
/*      */     } 
/*  710 */     if (this.first == i) {
/*  711 */       this.first = (int)this.link[i];
/*  712 */       if (0 <= this.first)
/*      */       {
/*  714 */         this.link[this.first] = this.link[this.first] | 0xFFFFFFFF00000000L;
/*      */       }
/*      */       return;
/*      */     } 
/*  718 */     if (this.last == i) {
/*  719 */       this.last = (int)(this.link[i] >>> 32L);
/*  720 */       if (0 <= this.last)
/*      */       {
/*  722 */         this.link[this.last] = this.link[this.last] | 0xFFFFFFFFL;
/*      */       }
/*      */       return;
/*      */     } 
/*  726 */     long linki = this.link[i];
/*  727 */     int prev = (int)(linki >>> 32L);
/*  728 */     int next = (int)linki;
/*  729 */     this.link[prev] = this.link[prev] ^ (this.link[prev] ^ linki & 0xFFFFFFFFL) & 0xFFFFFFFFL;
/*  730 */     this.link[next] = this.link[next] ^ (this.link[next] ^ linki & 0xFFFFFFFF00000000L) & 0xFFFFFFFF00000000L;
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
/*  741 */     if (this.size == 1) {
/*  742 */       this.first = this.last = d;
/*      */       
/*  744 */       this.link[d] = -1L;
/*      */       return;
/*      */     } 
/*  747 */     if (this.first == s) {
/*  748 */       this.first = d;
/*  749 */       this.link[(int)this.link[s]] = this.link[(int)this.link[s]] ^ (this.link[(int)this.link[s]] ^ (d & 0xFFFFFFFFL) << 32L) & 0xFFFFFFFF00000000L;
/*  750 */       this.link[d] = this.link[s];
/*      */       return;
/*      */     } 
/*  753 */     if (this.last == s) {
/*  754 */       this.last = d;
/*  755 */       this.link[(int)(this.link[s] >>> 32L)] = this.link[(int)(this.link[s] >>> 32L)] ^ (this.link[(int)(this.link[s] >>> 32L)] ^ d & 0xFFFFFFFFL) & 0xFFFFFFFFL;
/*  756 */       this.link[d] = this.link[s];
/*      */       return;
/*      */     } 
/*  759 */     long links = this.link[s];
/*  760 */     int prev = (int)(links >>> 32L);
/*  761 */     int next = (int)links;
/*  762 */     this.link[prev] = this.link[prev] ^ (this.link[prev] ^ d & 0xFFFFFFFFL) & 0xFFFFFFFFL;
/*  763 */     this.link[next] = this.link[next] ^ (this.link[next] ^ (d & 0xFFFFFFFFL) << 32L) & 0xFFFFFFFF00000000L;
/*  764 */     this.link[d] = links;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public K first() {
/*  774 */     if (this.size == 0) throw new NoSuchElementException(); 
/*  775 */     return this.key[this.first];
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public K last() {
/*  785 */     if (this.size == 0) throw new NoSuchElementException(); 
/*  786 */     return this.key[this.last];
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public ObjectSortedSet<K> tailSet(K from) {
/*  796 */     throw new UnsupportedOperationException();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public ObjectSortedSet<K> headSet(K to) {
/*  806 */     throw new UnsupportedOperationException();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public ObjectSortedSet<K> subSet(K from, K to) {
/*  816 */     throw new UnsupportedOperationException();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Comparator<? super K> comparator() {
/*  826 */     return null;
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
/*  841 */     int prev = -1;
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  846 */     int next = -1;
/*      */     
/*  848 */     int curr = -1;
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  853 */     int index = -1;
/*      */     
/*      */     SetIterator() {
/*  856 */       this.next = ObjectLinkedOpenCustomHashSet.this.first;
/*  857 */       this.index = 0;
/*      */     }
/*      */     
/*      */     SetIterator(K from) {
/*  861 */       if (ObjectLinkedOpenCustomHashSet.this.strategy.equals(from, null)) {
/*  862 */         if (ObjectLinkedOpenCustomHashSet.this.containsNull) {
/*  863 */           this.next = (int)ObjectLinkedOpenCustomHashSet.this.link[ObjectLinkedOpenCustomHashSet.this.n];
/*  864 */           this.prev = ObjectLinkedOpenCustomHashSet.this.n; return;
/*      */         } 
/*  866 */         throw new NoSuchElementException("The key " + from + " does not belong to this set.");
/*      */       } 
/*  868 */       if (ObjectLinkedOpenCustomHashSet.this.strategy.equals(ObjectLinkedOpenCustomHashSet.this.key[ObjectLinkedOpenCustomHashSet.this.last], from)) {
/*  869 */         this.prev = ObjectLinkedOpenCustomHashSet.this.last;
/*  870 */         this.index = ObjectLinkedOpenCustomHashSet.this.size;
/*      */         
/*      */         return;
/*      */       } 
/*  874 */       K[] key = ObjectLinkedOpenCustomHashSet.this.key;
/*  875 */       int pos = HashCommon.mix(ObjectLinkedOpenCustomHashSet.this.strategy.hashCode(from)) & ObjectLinkedOpenCustomHashSet.this.mask;
/*      */       
/*  877 */       while (key[pos] != null) {
/*  878 */         if (ObjectLinkedOpenCustomHashSet.this.strategy.equals(key[pos], from)) {
/*      */           
/*  880 */           this.next = (int)ObjectLinkedOpenCustomHashSet.this.link[pos];
/*  881 */           this.prev = pos;
/*      */           return;
/*      */         } 
/*  884 */         pos = pos + 1 & ObjectLinkedOpenCustomHashSet.this.mask;
/*      */       } 
/*  886 */       throw new NoSuchElementException("The key " + from + " does not belong to this set.");
/*      */     }
/*      */ 
/*      */     
/*      */     public boolean hasNext() {
/*  891 */       return (this.next != -1);
/*      */     }
/*      */ 
/*      */     
/*      */     public boolean hasPrevious() {
/*  896 */       return (this.prev != -1);
/*      */     }
/*      */ 
/*      */     
/*      */     public K next() {
/*  901 */       if (!hasNext()) throw new NoSuchElementException(); 
/*  902 */       this.curr = this.next;
/*  903 */       this.next = (int)ObjectLinkedOpenCustomHashSet.this.link[this.curr];
/*  904 */       this.prev = this.curr;
/*  905 */       if (this.index >= 0) this.index++;
/*      */       
/*  907 */       return ObjectLinkedOpenCustomHashSet.this.key[this.curr];
/*      */     }
/*      */ 
/*      */     
/*      */     public K previous() {
/*  912 */       if (!hasPrevious()) throw new NoSuchElementException(); 
/*  913 */       this.curr = this.prev;
/*  914 */       this.prev = (int)(ObjectLinkedOpenCustomHashSet.this.link[this.curr] >>> 32L);
/*  915 */       this.next = this.curr;
/*  916 */       if (this.index >= 0) this.index--; 
/*  917 */       return ObjectLinkedOpenCustomHashSet.this.key[this.curr];
/*      */     }
/*      */ 
/*      */     
/*      */     public void forEachRemaining(Consumer<? super K> action) {
/*  922 */       K[] key = ObjectLinkedOpenCustomHashSet.this.key;
/*  923 */       long[] link = ObjectLinkedOpenCustomHashSet.this.link;
/*  924 */       while (this.next != -1) {
/*  925 */         this.curr = this.next;
/*  926 */         this.next = (int)link[this.curr];
/*  927 */         this.prev = this.curr;
/*  928 */         if (this.index >= 0) this.index++;
/*      */         
/*  930 */         action.accept(key[this.curr]);
/*      */       } 
/*      */     }
/*      */     
/*      */     private final void ensureIndexKnown() {
/*  935 */       if (this.index >= 0)
/*  936 */         return;  if (this.prev == -1) {
/*  937 */         this.index = 0;
/*      */         return;
/*      */       } 
/*  940 */       if (this.next == -1) {
/*  941 */         this.index = ObjectLinkedOpenCustomHashSet.this.size;
/*      */         return;
/*      */       } 
/*  944 */       int pos = ObjectLinkedOpenCustomHashSet.this.first;
/*  945 */       this.index = 1;
/*  946 */       while (pos != this.prev) {
/*  947 */         pos = (int)ObjectLinkedOpenCustomHashSet.this.link[pos];
/*  948 */         this.index++;
/*      */       } 
/*      */     }
/*      */ 
/*      */     
/*      */     public int nextIndex() {
/*  954 */       ensureIndexKnown();
/*  955 */       return this.index;
/*      */     }
/*      */ 
/*      */     
/*      */     public int previousIndex() {
/*  960 */       ensureIndexKnown();
/*  961 */       return this.index - 1;
/*      */     }
/*      */ 
/*      */     
/*      */     public void remove() {
/*  966 */       ensureIndexKnown();
/*  967 */       if (this.curr == -1) throw new IllegalStateException(); 
/*  968 */       if (this.curr == this.prev)
/*      */       
/*      */       { 
/*  971 */         this.index--;
/*  972 */         this.prev = (int)(ObjectLinkedOpenCustomHashSet.this.link[this.curr] >>> 32L); }
/*  973 */       else { this.next = (int)ObjectLinkedOpenCustomHashSet.this.link[this.curr]; }
/*  974 */        ObjectLinkedOpenCustomHashSet.this.size--;
/*      */ 
/*      */       
/*  977 */       if (this.prev == -1) { ObjectLinkedOpenCustomHashSet.this.first = this.next; }
/*  978 */       else { ObjectLinkedOpenCustomHashSet.this.link[this.prev] = ObjectLinkedOpenCustomHashSet.this.link[this.prev] ^ (ObjectLinkedOpenCustomHashSet.this.link[this.prev] ^ this.next & 0xFFFFFFFFL) & 0xFFFFFFFFL; }
/*  979 */        if (this.next == -1) { ObjectLinkedOpenCustomHashSet.this.last = this.prev; }
/*  980 */       else { ObjectLinkedOpenCustomHashSet.this.link[this.next] = ObjectLinkedOpenCustomHashSet.this.link[this.next] ^ (ObjectLinkedOpenCustomHashSet.this.link[this.next] ^ (this.prev & 0xFFFFFFFFL) << 32L) & 0xFFFFFFFF00000000L; }
/*  981 */        int pos = this.curr;
/*  982 */       this.curr = -1;
/*  983 */       if (pos == ObjectLinkedOpenCustomHashSet.this.n) {
/*  984 */         ObjectLinkedOpenCustomHashSet.this.containsNull = false;
/*  985 */         ObjectLinkedOpenCustomHashSet.this.key[ObjectLinkedOpenCustomHashSet.this.n] = null;
/*      */       } else {
/*      */         
/*  988 */         K[] key = ObjectLinkedOpenCustomHashSet.this.key; while (true) {
/*      */           K curr;
/*      */           int last;
/*  991 */           pos = (last = pos) + 1 & ObjectLinkedOpenCustomHashSet.this.mask;
/*      */           while (true) {
/*  993 */             if ((curr = key[pos]) == null) {
/*  994 */               key[last] = null;
/*      */               return;
/*      */             } 
/*  997 */             int slot = HashCommon.mix(ObjectLinkedOpenCustomHashSet.this.strategy.hashCode(curr)) & ObjectLinkedOpenCustomHashSet.this.mask;
/*  998 */             if ((last <= pos) ? (last >= slot || slot > pos) : (last >= slot && slot > pos))
/*  999 */               break;  pos = pos + 1 & ObjectLinkedOpenCustomHashSet.this.mask;
/*      */           } 
/* 1001 */           key[last] = curr;
/* 1002 */           if (this.next == pos) this.next = last; 
/* 1003 */           if (this.prev == pos) this.prev = last; 
/* 1004 */           ObjectLinkedOpenCustomHashSet.this.fixPointers(pos, last);
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
/* 1020 */     return new SetIterator(from);
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
/* 1031 */     return new SetIterator();
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
/* 1051 */     return ObjectSpliterators.asSpliterator(iterator(), Size64.sizeOf(this), 81);
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public void forEach(Consumer<? super K> action) {
/* 1057 */     int next = this.first;
/* 1058 */     while (next != -1) {
/* 1059 */       int curr = next;
/* 1060 */       next = (int)this.link[curr];
/*      */       
/* 1062 */       action.accept(this.key[curr]);
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
/* 1080 */     return trim(this.size);
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
/* 1102 */     int l = HashCommon.nextPowerOfTwo((int)Math.ceil((n / this.f)));
/* 1103 */     if (l >= this.n || this.size > HashCommon.maxFill(l, this.f)) return true; 
/*      */     try {
/* 1105 */       rehash(l);
/* 1106 */     } catch (OutOfMemoryError cantDoIt) {
/* 1107 */       return false;
/*      */     } 
/* 1109 */     return true;
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
/* 1124 */     K[] key = this.key;
/* 1125 */     int mask = newN - 1;
/* 1126 */     K[] newKey = (K[])new Object[newN + 1];
/* 1127 */     int i = this.first, prev = -1, newPrev = -1;
/* 1128 */     long[] link = this.link;
/* 1129 */     long[] newLink = new long[newN + 1];
/* 1130 */     this.first = -1;
/* 1131 */     for (int j = this.size; j-- != 0; ) {
/* 1132 */       int pos; if (this.strategy.equals(key[i], null)) { pos = newN; }
/*      */       else
/* 1134 */       { pos = HashCommon.mix(this.strategy.hashCode(key[i])) & mask;
/* 1135 */         for (; newKey[pos] != null; pos = pos + 1 & mask); }
/*      */       
/* 1137 */       newKey[pos] = key[i];
/* 1138 */       if (prev != -1) {
/* 1139 */         newLink[newPrev] = newLink[newPrev] ^ (newLink[newPrev] ^ pos & 0xFFFFFFFFL) & 0xFFFFFFFFL;
/* 1140 */         newLink[pos] = newLink[pos] ^ (newLink[pos] ^ (newPrev & 0xFFFFFFFFL) << 32L) & 0xFFFFFFFF00000000L;
/* 1141 */         newPrev = pos;
/*      */       } else {
/* 1143 */         newPrev = this.first = pos;
/*      */         
/* 1145 */         newLink[pos] = -1L;
/*      */       } 
/* 1147 */       int t = i;
/* 1148 */       i = (int)link[i];
/* 1149 */       prev = t;
/*      */     } 
/* 1151 */     this.link = newLink;
/* 1152 */     this.last = newPrev;
/* 1153 */     if (newPrev != -1)
/*      */     {
/* 1155 */       newLink[newPrev] = newLink[newPrev] | 0xFFFFFFFFL; } 
/* 1156 */     this.n = newN;
/* 1157 */     this.mask = mask;
/* 1158 */     this.maxFill = HashCommon.maxFill(this.n, this.f);
/* 1159 */     this.key = newKey;
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
/*      */   public ObjectLinkedOpenCustomHashSet<K> clone() {
/*      */     ObjectLinkedOpenCustomHashSet<K> c;
/*      */     try {
/* 1176 */       c = (ObjectLinkedOpenCustomHashSet<K>)super.clone();
/* 1177 */     } catch (CloneNotSupportedException cantHappen) {
/* 1178 */       throw new InternalError();
/*      */     } 
/* 1180 */     c.key = (K[])this.key.clone();
/* 1181 */     c.containsNull = this.containsNull;
/* 1182 */     c.link = (long[])this.link.clone();
/* 1183 */     c.strategy = this.strategy;
/* 1184 */     return c;
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
/* 1198 */     int h = 0;
/* 1199 */     for (int j = realSize(), i = 0; j-- != 0; ) {
/* 1200 */       for (; this.key[i] == null; i++);
/* 1201 */       if (this != this.key[i]) h += this.strategy.hashCode(this.key[i]); 
/* 1202 */       i++;
/*      */     } 
/*      */     
/* 1205 */     return h;
/*      */   }
/*      */   
/*      */   private void writeObject(ObjectOutputStream s) throws IOException {
/* 1209 */     ObjectIterator<K> i = iterator();
/* 1210 */     s.defaultWriteObject();
/* 1211 */     for (int j = this.size; j-- != 0; s.writeObject(i.next()));
/*      */   }
/*      */ 
/*      */   
/*      */   private void readObject(ObjectInputStream s) throws IOException, ClassNotFoundException {
/* 1216 */     s.defaultReadObject();
/* 1217 */     this.n = HashCommon.arraySize(this.size, this.f);
/* 1218 */     this.maxFill = HashCommon.maxFill(this.n, this.f);
/* 1219 */     this.mask = this.n - 1;
/* 1220 */     K[] key = this.key = (K[])new Object[this.n + 1];
/* 1221 */     long[] link = this.link = new long[this.n + 1];
/* 1222 */     int prev = -1;
/* 1223 */     this.first = this.last = -1;
/*      */     
/* 1225 */     for (int i = this.size; i-- != 0; ) {
/* 1226 */       int pos; K k = (K)s.readObject();
/* 1227 */       if (this.strategy.equals(k, null))
/* 1228 */       { pos = this.n;
/* 1229 */         this.containsNull = true; }
/*      */       
/* 1231 */       else if (key[pos = HashCommon.mix(this.strategy.hashCode(k)) & this.mask] != null) { while (key[pos = pos + 1 & this.mask] != null); }
/*      */       
/* 1233 */       key[pos] = k;
/* 1234 */       if (this.first != -1) {
/* 1235 */         link[prev] = link[prev] ^ (link[prev] ^ pos & 0xFFFFFFFFL) & 0xFFFFFFFFL;
/* 1236 */         link[pos] = link[pos] ^ (link[pos] ^ (prev & 0xFFFFFFFFL) << 32L) & 0xFFFFFFFF00000000L;
/* 1237 */         prev = pos; continue;
/*      */       } 
/* 1239 */       prev = this.first = pos;
/*      */       
/* 1241 */       link[pos] = link[pos] | 0xFFFFFFFF00000000L;
/*      */     } 
/*      */     
/* 1244 */     this.last = prev;
/* 1245 */     if (prev != -1)
/*      */     {
/* 1247 */       link[prev] = link[prev] | 0xFFFFFFFFL;
/*      */     }
/*      */   }
/*      */   
/*      */   private void checkTable() {}
/*      */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\i\\unimi\dsi\fastutil\objects\ObjectLinkedOpenCustomHashSet.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */