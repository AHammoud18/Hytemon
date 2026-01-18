/*      */ package it.unimi.dsi.fastutil.longs;
/*      */ 
/*      */ import it.unimi.dsi.fastutil.Hash;
/*      */ import it.unimi.dsi.fastutil.HashCommon;
/*      */ import it.unimi.dsi.fastutil.Size64;
/*      */ import it.unimi.dsi.fastutil.objects.AbstractObjectSortedSet;
/*      */ import it.unimi.dsi.fastutil.objects.ObjectBidirectionalIterator;
/*      */ import it.unimi.dsi.fastutil.objects.ObjectIterator;
/*      */ import it.unimi.dsi.fastutil.objects.ObjectListIterator;
/*      */ import it.unimi.dsi.fastutil.objects.ObjectSet;
/*      */ import it.unimi.dsi.fastutil.objects.ObjectSortedSet;
/*      */ import it.unimi.dsi.fastutil.objects.ObjectSpliterator;
/*      */ import it.unimi.dsi.fastutil.objects.ObjectSpliterators;
/*      */ import java.io.IOException;
/*      */ import java.io.ObjectInputStream;
/*      */ import java.io.ObjectOutputStream;
/*      */ import java.io.Serializable;
/*      */ import java.util.Arrays;
/*      */ import java.util.Collection;
/*      */ import java.util.Comparator;
/*      */ import java.util.Iterator;
/*      */ import java.util.Map;
/*      */ import java.util.NoSuchElementException;
/*      */ import java.util.Objects;
/*      */ import java.util.Set;
/*      */ import java.util.SortedSet;
/*      */ import java.util.Spliterator;
/*      */ import java.util.function.BiFunction;
/*      */ import java.util.function.Consumer;
/*      */ import java.util.function.LongConsumer;
/*      */ import java.util.function.LongFunction;
/*      */ import java.util.function.LongUnaryOperator;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ public class Long2LongLinkedOpenHashMap
/*      */   extends AbstractLong2LongSortedMap
/*      */   implements Serializable, Cloneable, Hash
/*      */ {
/*      */   private static final long serialVersionUID = 0L;
/*      */   private static final boolean ASSERTS = false;
/*      */   protected transient long[] key;
/*      */   protected transient long[] value;
/*      */   protected transient int mask;
/*      */   protected transient boolean containsNullKey;
/*  101 */   protected transient int first = -1;
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*  106 */   protected transient int last = -1;
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
/*      */   protected final transient int minN;
/*      */ 
/*      */   
/*      */   protected int size;
/*      */ 
/*      */   
/*      */   protected final float f;
/*      */ 
/*      */   
/*      */   protected transient Long2LongSortedMap.FastSortedEntrySet entries;
/*      */ 
/*      */   
/*      */   protected transient LongSortedSet keys;
/*      */ 
/*      */   
/*      */   protected transient LongCollection values;
/*      */ 
/*      */ 
/*      */   
/*      */   public Long2LongLinkedOpenHashMap(int expected, float f) {
/*  141 */     if (f <= 0.0F || f >= 1.0F) throw new IllegalArgumentException("Load factor must be greater than 0 and smaller than 1"); 
/*  142 */     if (expected < 0) throw new IllegalArgumentException("The expected number of elements must be nonnegative"); 
/*  143 */     this.f = f;
/*  144 */     this.minN = this.n = HashCommon.arraySize(expected, f);
/*  145 */     this.mask = this.n - 1;
/*  146 */     this.maxFill = HashCommon.maxFill(this.n, f);
/*  147 */     this.key = new long[this.n + 1];
/*  148 */     this.value = new long[this.n + 1];
/*  149 */     this.link = new long[this.n + 1];
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Long2LongLinkedOpenHashMap(int expected) {
/*  158 */     this(expected, 0.75F);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Long2LongLinkedOpenHashMap() {
/*  166 */     this(16, 0.75F);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Long2LongLinkedOpenHashMap(Map<? extends Long, ? extends Long> m, float f) {
/*  176 */     this(m.size(), f);
/*  177 */     putAll(m);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Long2LongLinkedOpenHashMap(Map<? extends Long, ? extends Long> m) {
/*  186 */     this(m, 0.75F);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Long2LongLinkedOpenHashMap(Long2LongMap m, float f) {
/*  196 */     this(m.size(), f);
/*  197 */     putAll(m);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Long2LongLinkedOpenHashMap(Long2LongMap m) {
/*  207 */     this(m, 0.75F);
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
/*      */   public Long2LongLinkedOpenHashMap(long[] k, long[] v, float f) {
/*  219 */     this(k.length, f);
/*  220 */     if (k.length != v.length) throw new IllegalArgumentException("The key array and the value array have different lengths (" + k.length + " and " + v.length + ")"); 
/*  221 */     for (int i = 0; i < k.length; ) { put(k[i], v[i]); i++; }
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
/*      */   public Long2LongLinkedOpenHashMap(long[] k, long[] v) {
/*  233 */     this(k, v, 0.75F);
/*      */   }
/*      */   
/*      */   private int realSize() {
/*  237 */     return this.containsNullKey ? (this.size - 1) : this.size;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void ensureCapacity(int capacity) {
/*  247 */     int needed = HashCommon.arraySize(capacity, this.f);
/*  248 */     if (needed > this.n) rehash(needed); 
/*      */   }
/*      */   
/*      */   private void tryCapacity(long capacity) {
/*  252 */     int needed = (int)Math.min(1073741824L, Math.max(2L, HashCommon.nextPowerOfTwo((long)Math.ceil(((float)capacity / this.f)))));
/*  253 */     if (needed > this.n) rehash(needed); 
/*      */   }
/*      */   
/*      */   private long removeEntry(int pos) {
/*  257 */     long oldValue = this.value[pos];
/*  258 */     this.size--;
/*  259 */     fixPointers(pos);
/*  260 */     shiftKeys(pos);
/*  261 */     if (this.n > this.minN && this.size < this.maxFill / 4 && this.n > 16) rehash(this.n / 2); 
/*  262 */     return oldValue;
/*      */   }
/*      */   
/*      */   private long removeNullEntry() {
/*  266 */     this.containsNullKey = false;
/*  267 */     long oldValue = this.value[this.n];
/*  268 */     this.size--;
/*  269 */     fixPointers(this.n);
/*  270 */     if (this.n > this.minN && this.size < this.maxFill / 4 && this.n > 16) rehash(this.n / 2); 
/*  271 */     return oldValue;
/*      */   }
/*      */ 
/*      */   
/*      */   public void putAll(Map<? extends Long, ? extends Long> m) {
/*  276 */     if (this.f <= 0.5D) { ensureCapacity(m.size()); }
/*  277 */     else { tryCapacity((size() + m.size())); }
/*      */     
/*  279 */     super.putAll(m);
/*      */   }
/*      */   
/*      */   private int find(long k) {
/*  283 */     if (k == 0L) return this.containsNullKey ? this.n : -(this.n + 1);
/*      */     
/*  285 */     long[] key = this.key;
/*      */     long curr;
/*      */     int pos;
/*  288 */     if ((curr = key[pos = (int)HashCommon.mix(k) & this.mask]) == 0L) return -(pos + 1); 
/*  289 */     if (k == curr) return pos;
/*      */     
/*      */     while (true) {
/*  292 */       if ((curr = key[pos = pos + 1 & this.mask]) == 0L) return -(pos + 1); 
/*  293 */       if (k == curr) return pos; 
/*      */     } 
/*      */   }
/*      */   
/*      */   private void insert(int pos, long k, long v) {
/*  298 */     if (pos == this.n) this.containsNullKey = true; 
/*  299 */     this.key[pos] = k;
/*  300 */     this.value[pos] = v;
/*  301 */     if (this.size == 0) {
/*  302 */       this.first = this.last = pos;
/*      */       
/*  304 */       this.link[pos] = -1L;
/*      */     } else {
/*  306 */       this.link[this.last] = this.link[this.last] ^ (this.link[this.last] ^ pos & 0xFFFFFFFFL) & 0xFFFFFFFFL;
/*  307 */       this.link[pos] = (this.last & 0xFFFFFFFFL) << 32L | 0xFFFFFFFFL;
/*  308 */       this.last = pos;
/*      */     } 
/*  310 */     if (this.size++ >= this.maxFill) rehash(HashCommon.arraySize(this.size + 1, this.f));
/*      */   
/*      */   }
/*      */ 
/*      */   
/*      */   public long put(long k, long v) {
/*  316 */     int pos = find(k);
/*  317 */     if (pos < 0) {
/*  318 */       insert(-pos - 1, k, v);
/*  319 */       return this.defRetValue;
/*      */     } 
/*  321 */     long oldValue = this.value[pos];
/*  322 */     this.value[pos] = v;
/*  323 */     return oldValue;
/*      */   }
/*      */   
/*      */   private long addToValue(int pos, long incr) {
/*  327 */     long oldValue = this.value[pos];
/*  328 */     this.value[pos] = oldValue + incr;
/*  329 */     return oldValue;
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
/*      */   public long addTo(long k, long incr) {
/*      */     int pos;
/*  347 */     if (k == 0L) {
/*  348 */       if (this.containsNullKey) return addToValue(this.n, incr); 
/*  349 */       pos = this.n;
/*  350 */       this.containsNullKey = true;
/*      */     } else {
/*      */       
/*  353 */       long[] key = this.key;
/*      */       long curr;
/*  355 */       if ((curr = key[pos = (int)HashCommon.mix(k) & this.mask]) != 0L) {
/*  356 */         if (curr == k) return addToValue(pos, incr); 
/*  357 */         while ((curr = key[pos = pos + 1 & this.mask]) != 0L) { if (curr == k) return addToValue(pos, incr);  }
/*      */       
/*      */       } 
/*  360 */     }  this.key[pos] = k;
/*  361 */     this.value[pos] = this.defRetValue + incr;
/*  362 */     if (this.size == 0) {
/*  363 */       this.first = this.last = pos;
/*      */       
/*  365 */       this.link[pos] = -1L;
/*      */     } else {
/*  367 */       this.link[this.last] = this.link[this.last] ^ (this.link[this.last] ^ pos & 0xFFFFFFFFL) & 0xFFFFFFFFL;
/*  368 */       this.link[pos] = (this.last & 0xFFFFFFFFL) << 32L | 0xFFFFFFFFL;
/*  369 */       this.last = pos;
/*      */     } 
/*  371 */     if (this.size++ >= this.maxFill) rehash(HashCommon.arraySize(this.size + 1, this.f));
/*      */     
/*  373 */     return this.defRetValue;
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
/*  386 */     long[] key = this.key; while (true) {
/*      */       long curr; int last;
/*  388 */       pos = (last = pos) + 1 & this.mask;
/*      */       while (true) {
/*  390 */         if ((curr = key[pos]) == 0L) {
/*  391 */           key[last] = 0L;
/*      */           return;
/*      */         } 
/*  394 */         int slot = (int)HashCommon.mix(curr) & this.mask;
/*  395 */         if ((last <= pos) ? (last >= slot || slot > pos) : (last >= slot && slot > pos))
/*  396 */           break;  pos = pos + 1 & this.mask;
/*      */       } 
/*  398 */       key[last] = curr;
/*  399 */       this.value[last] = this.value[pos];
/*  400 */       fixPointers(pos, last);
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public long remove(long k) {
/*  407 */     if (k == 0L) {
/*  408 */       if (this.containsNullKey) return removeNullEntry(); 
/*  409 */       return this.defRetValue;
/*      */     } 
/*      */     
/*  412 */     long[] key = this.key;
/*      */     long curr;
/*      */     int pos;
/*  415 */     if ((curr = key[pos = (int)HashCommon.mix(k) & this.mask]) == 0L) return this.defRetValue; 
/*  416 */     if (k == curr) return removeEntry(pos); 
/*      */     while (true) {
/*  418 */       if ((curr = key[pos = pos + 1 & this.mask]) == 0L) return this.defRetValue; 
/*  419 */       if (k == curr) return removeEntry(pos); 
/*      */     } 
/*      */   }
/*      */   
/*      */   private long setValue(int pos, long v) {
/*  424 */     long oldValue = this.value[pos];
/*  425 */     this.value[pos] = v;
/*  426 */     return oldValue;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public long removeFirstLong() {
/*  436 */     if (this.size == 0) throw new NoSuchElementException(); 
/*  437 */     int pos = this.first;
/*      */     
/*  439 */     if (this.size == 1) { this.first = this.last = -1; }
/*      */     else
/*  441 */     { this.first = (int)this.link[pos];
/*  442 */       if (0 <= this.first)
/*      */       {
/*  444 */         this.link[this.first] = this.link[this.first] | 0xFFFFFFFF00000000L;
/*      */       } }
/*      */     
/*  447 */     this.size--;
/*  448 */     long v = this.value[pos];
/*  449 */     if (pos == this.n)
/*  450 */     { this.containsNullKey = false; }
/*  451 */     else { shiftKeys(pos); }
/*  452 */      if (this.n > this.minN && this.size < this.maxFill / 4 && this.n > 16) rehash(this.n / 2); 
/*  453 */     return v;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public long removeLastLong() {
/*  463 */     if (this.size == 0) throw new NoSuchElementException(); 
/*  464 */     int pos = this.last;
/*      */     
/*  466 */     if (this.size == 1) { this.first = this.last = -1; }
/*      */     else
/*  468 */     { this.last = (int)(this.link[pos] >>> 32L);
/*  469 */       if (0 <= this.last)
/*      */       {
/*  471 */         this.link[this.last] = this.link[this.last] | 0xFFFFFFFFL;
/*      */       } }
/*      */     
/*  474 */     this.size--;
/*  475 */     long v = this.value[pos];
/*  476 */     if (pos == this.n)
/*  477 */     { this.containsNullKey = false; }
/*  478 */     else { shiftKeys(pos); }
/*  479 */      if (this.n > this.minN && this.size < this.maxFill / 4 && this.n > 16) rehash(this.n / 2); 
/*  480 */     return v;
/*      */   }
/*      */   
/*      */   private void moveIndexToFirst(int i) {
/*  484 */     if (this.size == 1 || this.first == i)
/*  485 */       return;  if (this.last == i) {
/*  486 */       this.last = (int)(this.link[i] >>> 32L);
/*      */       
/*  488 */       this.link[this.last] = this.link[this.last] | 0xFFFFFFFFL;
/*      */     } else {
/*  490 */       long linki = this.link[i];
/*  491 */       int prev = (int)(linki >>> 32L);
/*  492 */       int next = (int)linki;
/*  493 */       this.link[prev] = this.link[prev] ^ (this.link[prev] ^ linki & 0xFFFFFFFFL) & 0xFFFFFFFFL;
/*  494 */       this.link[next] = this.link[next] ^ (this.link[next] ^ linki & 0xFFFFFFFF00000000L) & 0xFFFFFFFF00000000L;
/*      */     } 
/*  496 */     this.link[this.first] = this.link[this.first] ^ (this.link[this.first] ^ (i & 0xFFFFFFFFL) << 32L) & 0xFFFFFFFF00000000L;
/*  497 */     this.link[i] = 0xFFFFFFFF00000000L | this.first & 0xFFFFFFFFL;
/*  498 */     this.first = i;
/*      */   }
/*      */   
/*      */   private void moveIndexToLast(int i) {
/*  502 */     if (this.size == 1 || this.last == i)
/*  503 */       return;  if (this.first == i) {
/*  504 */       this.first = (int)this.link[i];
/*      */       
/*  506 */       this.link[this.first] = this.link[this.first] | 0xFFFFFFFF00000000L;
/*      */     } else {
/*  508 */       long linki = this.link[i];
/*  509 */       int prev = (int)(linki >>> 32L);
/*  510 */       int next = (int)linki;
/*  511 */       this.link[prev] = this.link[prev] ^ (this.link[prev] ^ linki & 0xFFFFFFFFL) & 0xFFFFFFFFL;
/*  512 */       this.link[next] = this.link[next] ^ (this.link[next] ^ linki & 0xFFFFFFFF00000000L) & 0xFFFFFFFF00000000L;
/*      */     } 
/*  514 */     this.link[this.last] = this.link[this.last] ^ (this.link[this.last] ^ i & 0xFFFFFFFFL) & 0xFFFFFFFFL;
/*  515 */     this.link[i] = (this.last & 0xFFFFFFFFL) << 32L | 0xFFFFFFFFL;
/*  516 */     this.last = i;
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
/*      */   public long getAndMoveToFirst(long k) {
/*  528 */     if (k == 0L) {
/*  529 */       if (this.containsNullKey) {
/*  530 */         moveIndexToFirst(this.n);
/*  531 */         return this.value[this.n];
/*      */       } 
/*  533 */       return this.defRetValue;
/*      */     } 
/*      */     
/*  536 */     long[] key = this.key;
/*      */     long curr;
/*      */     int pos;
/*  539 */     if ((curr = key[pos = (int)HashCommon.mix(k) & this.mask]) == 0L) return this.defRetValue; 
/*  540 */     if (k == curr) {
/*  541 */       moveIndexToFirst(pos);
/*  542 */       return this.value[pos];
/*      */     } 
/*      */     
/*      */     while (true) {
/*  546 */       if ((curr = key[pos = pos + 1 & this.mask]) == 0L) return this.defRetValue; 
/*  547 */       if (k == curr) {
/*  548 */         moveIndexToFirst(pos);
/*  549 */         return this.value[pos];
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
/*      */   public long getAndMoveToLast(long k) {
/*  563 */     if (k == 0L) {
/*  564 */       if (this.containsNullKey) {
/*  565 */         moveIndexToLast(this.n);
/*  566 */         return this.value[this.n];
/*      */       } 
/*  568 */       return this.defRetValue;
/*      */     } 
/*      */     
/*  571 */     long[] key = this.key;
/*      */     long curr;
/*      */     int pos;
/*  574 */     if ((curr = key[pos = (int)HashCommon.mix(k) & this.mask]) == 0L) return this.defRetValue; 
/*  575 */     if (k == curr) {
/*  576 */       moveIndexToLast(pos);
/*  577 */       return this.value[pos];
/*      */     } 
/*      */     
/*      */     while (true) {
/*  581 */       if ((curr = key[pos = pos + 1 & this.mask]) == 0L) return this.defRetValue; 
/*  582 */       if (k == curr) {
/*  583 */         moveIndexToLast(pos);
/*  584 */         return this.value[pos];
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
/*      */   public long putAndMoveToFirst(long k, long v) {
/*      */     int pos;
/*  600 */     if (k == 0L) {
/*  601 */       if (this.containsNullKey) {
/*  602 */         moveIndexToFirst(this.n);
/*  603 */         return setValue(this.n, v);
/*      */       } 
/*  605 */       this.containsNullKey = true;
/*  606 */       pos = this.n;
/*      */     } else {
/*      */       
/*  609 */       long[] key = this.key;
/*      */       long curr;
/*  611 */       if ((curr = key[pos = (int)HashCommon.mix(k) & this.mask]) != 0L) {
/*  612 */         if (curr == k) {
/*  613 */           moveIndexToFirst(pos);
/*  614 */           return setValue(pos, v);
/*      */         } 
/*  616 */         while ((curr = key[pos = pos + 1 & this.mask]) != 0L) { if (curr == k) {
/*  617 */             moveIndexToFirst(pos);
/*  618 */             return setValue(pos, v);
/*      */           }  }
/*      */       
/*      */       } 
/*  622 */     }  this.key[pos] = k;
/*  623 */     this.value[pos] = v;
/*  624 */     if (this.size == 0) {
/*  625 */       this.first = this.last = pos;
/*      */       
/*  627 */       this.link[pos] = -1L;
/*      */     } else {
/*  629 */       this.link[this.first] = this.link[this.first] ^ (this.link[this.first] ^ (pos & 0xFFFFFFFFL) << 32L) & 0xFFFFFFFF00000000L;
/*  630 */       this.link[pos] = 0xFFFFFFFF00000000L | this.first & 0xFFFFFFFFL;
/*  631 */       this.first = pos;
/*      */     } 
/*  633 */     if (this.size++ >= this.maxFill) rehash(HashCommon.arraySize(this.size, this.f));
/*      */     
/*  635 */     return this.defRetValue;
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
/*      */   public long putAndMoveToLast(long k, long v) {
/*      */     int pos;
/*  649 */     if (k == 0L) {
/*  650 */       if (this.containsNullKey) {
/*  651 */         moveIndexToLast(this.n);
/*  652 */         return setValue(this.n, v);
/*      */       } 
/*  654 */       this.containsNullKey = true;
/*  655 */       pos = this.n;
/*      */     } else {
/*      */       
/*  658 */       long[] key = this.key;
/*      */       long curr;
/*  660 */       if ((curr = key[pos = (int)HashCommon.mix(k) & this.mask]) != 0L) {
/*  661 */         if (curr == k) {
/*  662 */           moveIndexToLast(pos);
/*  663 */           return setValue(pos, v);
/*      */         } 
/*  665 */         while ((curr = key[pos = pos + 1 & this.mask]) != 0L) { if (curr == k) {
/*  666 */             moveIndexToLast(pos);
/*  667 */             return setValue(pos, v);
/*      */           }  }
/*      */       
/*      */       } 
/*  671 */     }  this.key[pos] = k;
/*  672 */     this.value[pos] = v;
/*  673 */     if (this.size == 0) {
/*  674 */       this.first = this.last = pos;
/*      */       
/*  676 */       this.link[pos] = -1L;
/*      */     } else {
/*  678 */       this.link[this.last] = this.link[this.last] ^ (this.link[this.last] ^ pos & 0xFFFFFFFFL) & 0xFFFFFFFFL;
/*  679 */       this.link[pos] = (this.last & 0xFFFFFFFFL) << 32L | 0xFFFFFFFFL;
/*  680 */       this.last = pos;
/*      */     } 
/*  682 */     if (this.size++ >= this.maxFill) rehash(HashCommon.arraySize(this.size, this.f));
/*      */     
/*  684 */     return this.defRetValue;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public long get(long k) {
/*  690 */     if (k == 0L) return this.containsNullKey ? this.value[this.n] : this.defRetValue;
/*      */     
/*  692 */     long[] key = this.key;
/*      */     long curr;
/*      */     int pos;
/*  695 */     if ((curr = key[pos = (int)HashCommon.mix(k) & this.mask]) == 0L) return this.defRetValue; 
/*  696 */     if (k == curr) return this.value[pos];
/*      */     
/*      */     while (true) {
/*  699 */       if ((curr = key[pos = pos + 1 & this.mask]) == 0L) return this.defRetValue; 
/*  700 */       if (k == curr) return this.value[pos];
/*      */     
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   public boolean containsKey(long k) {
/*  707 */     if (k == 0L) return this.containsNullKey;
/*      */     
/*  709 */     long[] key = this.key;
/*      */     long curr;
/*      */     int pos;
/*  712 */     if ((curr = key[pos = (int)HashCommon.mix(k) & this.mask]) == 0L) return false; 
/*  713 */     if (k == curr) return true;
/*      */     
/*      */     while (true) {
/*  716 */       if ((curr = key[pos = pos + 1 & this.mask]) == 0L) return false; 
/*  717 */       if (k == curr) return true;
/*      */     
/*      */     } 
/*      */   }
/*      */   
/*      */   public boolean containsValue(long v) {
/*  723 */     long[] value = this.value;
/*  724 */     long[] key = this.key;
/*  725 */     if (this.containsNullKey && value[this.n] == v) return true; 
/*  726 */     for (int i = this.n; i-- != 0;) { if (key[i] != 0L && value[i] == v) return true;  }
/*  727 */      return false;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public long getOrDefault(long k, long defaultValue) {
/*  734 */     if (k == 0L) return this.containsNullKey ? this.value[this.n] : defaultValue;
/*      */     
/*  736 */     long[] key = this.key;
/*      */     long curr;
/*      */     int pos;
/*  739 */     if ((curr = key[pos = (int)HashCommon.mix(k) & this.mask]) == 0L) return defaultValue; 
/*  740 */     if (k == curr) return this.value[pos];
/*      */     
/*      */     while (true) {
/*  743 */       if ((curr = key[pos = pos + 1 & this.mask]) == 0L) return defaultValue; 
/*  744 */       if (k == curr) return this.value[pos];
/*      */     
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   public long putIfAbsent(long k, long v) {
/*  751 */     int pos = find(k);
/*  752 */     if (pos >= 0) return this.value[pos]; 
/*  753 */     insert(-pos - 1, k, v);
/*  754 */     return this.defRetValue;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean remove(long k, long v) {
/*  761 */     if (k == 0L) {
/*  762 */       if (this.containsNullKey && v == this.value[this.n]) {
/*  763 */         removeNullEntry();
/*  764 */         return true;
/*      */       } 
/*  766 */       return false;
/*      */     } 
/*      */     
/*  769 */     long[] key = this.key;
/*      */     long curr;
/*      */     int pos;
/*  772 */     if ((curr = key[pos = (int)HashCommon.mix(k) & this.mask]) == 0L) return false; 
/*  773 */     if (k == curr && v == this.value[pos]) {
/*  774 */       removeEntry(pos);
/*  775 */       return true;
/*      */     } 
/*      */     while (true) {
/*  778 */       if ((curr = key[pos = pos + 1 & this.mask]) == 0L) return false; 
/*  779 */       if (k == curr && v == this.value[pos]) {
/*  780 */         removeEntry(pos);
/*  781 */         return true;
/*      */       } 
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean replace(long k, long oldValue, long v) {
/*  789 */     int pos = find(k);
/*  790 */     if (pos < 0 || oldValue != this.value[pos]) return false; 
/*  791 */     this.value[pos] = v;
/*  792 */     return true;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public long replace(long k, long v) {
/*  798 */     int pos = find(k);
/*  799 */     if (pos < 0) return this.defRetValue; 
/*  800 */     long oldValue = this.value[pos];
/*  801 */     this.value[pos] = v;
/*  802 */     return oldValue;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public long computeIfAbsent(long k, LongUnaryOperator mappingFunction) {
/*  808 */     Objects.requireNonNull(mappingFunction);
/*  809 */     int pos = find(k);
/*  810 */     if (pos >= 0) return this.value[pos]; 
/*  811 */     long newValue = mappingFunction.applyAsLong(k);
/*  812 */     insert(-pos - 1, k, newValue);
/*  813 */     return newValue;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public long computeIfAbsent(long key, Long2LongFunction mappingFunction) {
/*  819 */     Objects.requireNonNull(mappingFunction);
/*  820 */     int pos = find(key);
/*  821 */     if (pos >= 0) return this.value[pos]; 
/*  822 */     if (!mappingFunction.containsKey(key)) return this.defRetValue; 
/*  823 */     long newValue = mappingFunction.get(key);
/*  824 */     insert(-pos - 1, key, newValue);
/*  825 */     return newValue;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public long computeIfAbsentNullable(long k, LongFunction<? extends Long> mappingFunction) {
/*  831 */     Objects.requireNonNull(mappingFunction);
/*  832 */     int pos = find(k);
/*  833 */     if (pos >= 0) return this.value[pos]; 
/*  834 */     Long newValue = mappingFunction.apply(k);
/*  835 */     if (newValue == null) return this.defRetValue; 
/*  836 */     long v = newValue.longValue();
/*  837 */     insert(-pos - 1, k, v);
/*  838 */     return v;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public long computeIfPresent(long k, BiFunction<? super Long, ? super Long, ? extends Long> remappingFunction) {
/*  844 */     Objects.requireNonNull(remappingFunction);
/*  845 */     int pos = find(k);
/*  846 */     if (pos < 0) return this.defRetValue; 
/*  847 */     Long newValue = remappingFunction.apply(Long.valueOf(k), Long.valueOf(this.value[pos]));
/*  848 */     if (newValue == null) {
/*  849 */       if (k == 0L) { removeNullEntry(); }
/*  850 */       else { removeEntry(pos); }
/*  851 */        return this.defRetValue;
/*      */     } 
/*  853 */     this.value[pos] = newValue.longValue(); return newValue.longValue();
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public long compute(long k, BiFunction<? super Long, ? super Long, ? extends Long> remappingFunction) {
/*  859 */     Objects.requireNonNull(remappingFunction);
/*  860 */     int pos = find(k);
/*  861 */     Long newValue = remappingFunction.apply(Long.valueOf(k), (pos >= 0) ? Long.valueOf(this.value[pos]) : null);
/*  862 */     if (newValue == null) {
/*  863 */       if (pos >= 0)
/*  864 */         if (k == 0L) { removeNullEntry(); }
/*  865 */         else { removeEntry(pos); }
/*      */          
/*  867 */       return this.defRetValue;
/*      */     } 
/*  869 */     long newVal = newValue.longValue();
/*  870 */     if (pos < 0) {
/*  871 */       insert(-pos - 1, k, newVal);
/*  872 */       return newVal;
/*      */     } 
/*  874 */     this.value[pos] = newVal; return newVal;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public long merge(long k, long v, BiFunction<? super Long, ? super Long, ? extends Long> remappingFunction) {
/*  880 */     Objects.requireNonNull(remappingFunction);
/*      */     
/*  882 */     int pos = find(k);
/*  883 */     if (pos < 0) {
/*  884 */       if (pos < 0) { insert(-pos - 1, k, v); }
/*  885 */       else { this.value[pos] = v; }
/*  886 */        return v;
/*      */     } 
/*  888 */     Long newValue = remappingFunction.apply(Long.valueOf(this.value[pos]), Long.valueOf(v));
/*  889 */     if (newValue == null) {
/*  890 */       if (k == 0L) { removeNullEntry(); }
/*  891 */       else { removeEntry(pos); }
/*  892 */        return this.defRetValue;
/*      */     } 
/*  894 */     this.value[pos] = newValue.longValue(); return newValue.longValue();
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
/*  905 */     if (this.size == 0)
/*  906 */       return;  this.size = 0;
/*  907 */     this.containsNullKey = false;
/*  908 */     Arrays.fill(this.key, 0L);
/*  909 */     this.first = this.last = -1;
/*      */   }
/*      */ 
/*      */   
/*      */   public int size() {
/*  914 */     return this.size;
/*      */   }
/*      */ 
/*      */   
/*      */   public boolean isEmpty() {
/*  919 */     return (this.size == 0);
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   final class MapEntry
/*      */     implements Long2LongMap.Entry, Map.Entry<Long, Long>, LongLongPair
/*      */   {
/*      */     int index;
/*      */ 
/*      */ 
/*      */     
/*      */     MapEntry(int index) {
/*  932 */       this.index = index;
/*      */     }
/*      */ 
/*      */     
/*      */     MapEntry() {}
/*      */ 
/*      */     
/*      */     public long getLongKey() {
/*  940 */       return Long2LongLinkedOpenHashMap.this.key[this.index];
/*      */     }
/*      */ 
/*      */     
/*      */     public long leftLong() {
/*  945 */       return Long2LongLinkedOpenHashMap.this.key[this.index];
/*      */     }
/*      */ 
/*      */     
/*      */     public long getLongValue() {
/*  950 */       return Long2LongLinkedOpenHashMap.this.value[this.index];
/*      */     }
/*      */ 
/*      */     
/*      */     public long rightLong() {
/*  955 */       return Long2LongLinkedOpenHashMap.this.value[this.index];
/*      */     }
/*      */ 
/*      */     
/*      */     public long setValue(long v) {
/*  960 */       long oldValue = Long2LongLinkedOpenHashMap.this.value[this.index];
/*  961 */       Long2LongLinkedOpenHashMap.this.value[this.index] = v;
/*  962 */       return oldValue;
/*      */     }
/*      */ 
/*      */     
/*      */     public LongLongPair right(long v) {
/*  967 */       Long2LongLinkedOpenHashMap.this.value[this.index] = v;
/*  968 */       return this;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     @Deprecated
/*      */     public Long getKey() {
/*  979 */       return Long.valueOf(Long2LongLinkedOpenHashMap.this.key[this.index]);
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     @Deprecated
/*      */     public Long getValue() {
/*  990 */       return Long.valueOf(Long2LongLinkedOpenHashMap.this.value[this.index]);
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     @Deprecated
/*      */     public Long setValue(Long v) {
/* 1001 */       return Long.valueOf(setValue(v.longValue()));
/*      */     }
/*      */ 
/*      */ 
/*      */     
/*      */     public boolean equals(Object o) {
/* 1007 */       if (!(o instanceof Map.Entry)) return false; 
/* 1008 */       Map.Entry<Long, Long> e = (Map.Entry<Long, Long>)o;
/* 1009 */       return (Long2LongLinkedOpenHashMap.this.key[this.index] == ((Long)e.getKey()).longValue() && Long2LongLinkedOpenHashMap.this.value[this.index] == ((Long)e.getValue()).longValue());
/*      */     }
/*      */ 
/*      */     
/*      */     public int hashCode() {
/* 1014 */       return HashCommon.long2int(Long2LongLinkedOpenHashMap.this.key[this.index]) ^ HashCommon.long2int(Long2LongLinkedOpenHashMap.this.value[this.index]);
/*      */     }
/*      */ 
/*      */     
/*      */     public String toString() {
/* 1019 */       return Long2LongLinkedOpenHashMap.this.key[this.index] + "=>" + Long2LongLinkedOpenHashMap.this.value[this.index];
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected void fixPointers(int i) {
/* 1030 */     if (this.size == 0) {
/* 1031 */       this.first = this.last = -1;
/*      */       return;
/*      */     } 
/* 1034 */     if (this.first == i) {
/* 1035 */       this.first = (int)this.link[i];
/* 1036 */       if (0 <= this.first)
/*      */       {
/* 1038 */         this.link[this.first] = this.link[this.first] | 0xFFFFFFFF00000000L;
/*      */       }
/*      */       return;
/*      */     } 
/* 1042 */     if (this.last == i) {
/* 1043 */       this.last = (int)(this.link[i] >>> 32L);
/* 1044 */       if (0 <= this.last)
/*      */       {
/* 1046 */         this.link[this.last] = this.link[this.last] | 0xFFFFFFFFL;
/*      */       }
/*      */       return;
/*      */     } 
/* 1050 */     long linki = this.link[i];
/* 1051 */     int prev = (int)(linki >>> 32L);
/* 1052 */     int next = (int)linki;
/* 1053 */     this.link[prev] = this.link[prev] ^ (this.link[prev] ^ linki & 0xFFFFFFFFL) & 0xFFFFFFFFL;
/* 1054 */     this.link[next] = this.link[next] ^ (this.link[next] ^ linki & 0xFFFFFFFF00000000L) & 0xFFFFFFFF00000000L;
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
/*      */   protected void fixPointers(int s, int d) {
/* 1066 */     if (this.size == 1) {
/* 1067 */       this.first = this.last = d;
/*      */       
/* 1069 */       this.link[d] = -1L;
/*      */       return;
/*      */     } 
/* 1072 */     if (this.first == s) {
/* 1073 */       this.first = d;
/* 1074 */       this.link[(int)this.link[s]] = this.link[(int)this.link[s]] ^ (this.link[(int)this.link[s]] ^ (d & 0xFFFFFFFFL) << 32L) & 0xFFFFFFFF00000000L;
/* 1075 */       this.link[d] = this.link[s];
/*      */       return;
/*      */     } 
/* 1078 */     if (this.last == s) {
/* 1079 */       this.last = d;
/* 1080 */       this.link[(int)(this.link[s] >>> 32L)] = this.link[(int)(this.link[s] >>> 32L)] ^ (this.link[(int)(this.link[s] >>> 32L)] ^ d & 0xFFFFFFFFL) & 0xFFFFFFFFL;
/* 1081 */       this.link[d] = this.link[s];
/*      */       return;
/*      */     } 
/* 1084 */     long links = this.link[s];
/* 1085 */     int prev = (int)(links >>> 32L);
/* 1086 */     int next = (int)links;
/* 1087 */     this.link[prev] = this.link[prev] ^ (this.link[prev] ^ d & 0xFFFFFFFFL) & 0xFFFFFFFFL;
/* 1088 */     this.link[next] = this.link[next] ^ (this.link[next] ^ (d & 0xFFFFFFFFL) << 32L) & 0xFFFFFFFF00000000L;
/* 1089 */     this.link[d] = links;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public long firstLongKey() {
/* 1099 */     if (this.size == 0) throw new NoSuchElementException(); 
/* 1100 */     return this.key[this.first];
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public long lastLongKey() {
/* 1110 */     if (this.size == 0) throw new NoSuchElementException(); 
/* 1111 */     return this.key[this.last];
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Long2LongSortedMap tailMap(long from) {
/* 1121 */     throw new UnsupportedOperationException();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Long2LongSortedMap headMap(long to) {
/* 1131 */     throw new UnsupportedOperationException();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Long2LongSortedMap subMap(long from, long to) {
/* 1141 */     throw new UnsupportedOperationException();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public LongComparator comparator() {
/* 1151 */     return null;
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
/*      */   private abstract class MapIterator<ConsumerType>
/*      */   {
/* 1166 */     int prev = -1;
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 1171 */     int next = -1;
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 1176 */     int curr = -1;
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 1181 */     int index = -1;
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     protected MapIterator() {
/* 1187 */       this.next = Long2LongLinkedOpenHashMap.this.first;
/* 1188 */       this.index = 0;
/*      */     }
/*      */     
/*      */     private MapIterator(long from) {
/* 1192 */       if (from == 0L) {
/* 1193 */         if (Long2LongLinkedOpenHashMap.this.containsNullKey) {
/* 1194 */           this.next = (int)Long2LongLinkedOpenHashMap.this.link[Long2LongLinkedOpenHashMap.this.n];
/* 1195 */           this.prev = Long2LongLinkedOpenHashMap.this.n; return;
/*      */         } 
/* 1197 */         throw new NoSuchElementException("The key " + from + " does not belong to this map.");
/*      */       } 
/* 1199 */       if (Long2LongLinkedOpenHashMap.this.key[Long2LongLinkedOpenHashMap.this.last] == from) {
/* 1200 */         this.prev = Long2LongLinkedOpenHashMap.this.last;
/* 1201 */         this.index = Long2LongLinkedOpenHashMap.this.size;
/*      */         
/*      */         return;
/*      */       } 
/* 1205 */       int pos = (int)HashCommon.mix(from) & Long2LongLinkedOpenHashMap.this.mask;
/*      */       
/* 1207 */       while (Long2LongLinkedOpenHashMap.this.key[pos] != 0L) {
/* 1208 */         if (Long2LongLinkedOpenHashMap.this.key[pos] == from) {
/*      */           
/* 1210 */           this.next = (int)Long2LongLinkedOpenHashMap.this.link[pos];
/* 1211 */           this.prev = pos;
/*      */           return;
/*      */         } 
/* 1214 */         pos = pos + 1 & Long2LongLinkedOpenHashMap.this.mask;
/*      */       } 
/* 1216 */       throw new NoSuchElementException("The key " + from + " does not belong to this map.");
/*      */     }
/*      */     
/*      */     public boolean hasNext() {
/* 1220 */       return (this.next != -1);
/*      */     }
/*      */     
/*      */     public boolean hasPrevious() {
/* 1224 */       return (this.prev != -1);
/*      */     }
/*      */     
/*      */     private final void ensureIndexKnown() {
/* 1228 */       if (this.index >= 0)
/* 1229 */         return;  if (this.prev == -1) {
/* 1230 */         this.index = 0;
/*      */         return;
/*      */       } 
/* 1233 */       if (this.next == -1) {
/* 1234 */         this.index = Long2LongLinkedOpenHashMap.this.size;
/*      */         return;
/*      */       } 
/* 1237 */       int pos = Long2LongLinkedOpenHashMap.this.first;
/* 1238 */       this.index = 1;
/* 1239 */       while (pos != this.prev) {
/* 1240 */         pos = (int)Long2LongLinkedOpenHashMap.this.link[pos];
/* 1241 */         this.index++;
/*      */       } 
/*      */     }
/*      */     
/*      */     public int nextIndex() {
/* 1246 */       ensureIndexKnown();
/* 1247 */       return this.index;
/*      */     }
/*      */     
/*      */     public int previousIndex() {
/* 1251 */       ensureIndexKnown();
/* 1252 */       return this.index - 1;
/*      */     }
/*      */     
/*      */     public int nextEntry() {
/* 1256 */       if (!hasNext()) throw new NoSuchElementException(); 
/* 1257 */       this.curr = this.next;
/* 1258 */       this.next = (int)Long2LongLinkedOpenHashMap.this.link[this.curr];
/* 1259 */       this.prev = this.curr;
/* 1260 */       if (this.index >= 0) this.index++; 
/* 1261 */       return this.curr;
/*      */     }
/*      */     
/*      */     public int previousEntry() {
/* 1265 */       if (!hasPrevious()) throw new NoSuchElementException(); 
/* 1266 */       this.curr = this.prev;
/* 1267 */       this.prev = (int)(Long2LongLinkedOpenHashMap.this.link[this.curr] >>> 32L);
/* 1268 */       this.next = this.curr;
/* 1269 */       if (this.index >= 0) this.index--; 
/* 1270 */       return this.curr;
/*      */     }
/*      */     
/*      */     public void forEachRemaining(ConsumerType action) {
/* 1274 */       while (hasNext()) {
/* 1275 */         this.curr = this.next;
/* 1276 */         this.next = (int)Long2LongLinkedOpenHashMap.this.link[this.curr];
/* 1277 */         this.prev = this.curr;
/* 1278 */         if (this.index >= 0) this.index++; 
/* 1279 */         acceptOnIndex(action, this.curr);
/*      */       } 
/*      */     }
/*      */     
/*      */     public void remove() {
/* 1284 */       ensureIndexKnown();
/* 1285 */       if (this.curr == -1) throw new IllegalStateException(); 
/* 1286 */       if (this.curr == this.prev)
/*      */       
/*      */       { 
/* 1289 */         this.index--;
/* 1290 */         this.prev = (int)(Long2LongLinkedOpenHashMap.this.link[this.curr] >>> 32L); }
/* 1291 */       else { this.next = (int)Long2LongLinkedOpenHashMap.this.link[this.curr]; }
/* 1292 */        Long2LongLinkedOpenHashMap.this.size--;
/*      */ 
/*      */       
/* 1295 */       if (this.prev == -1) { Long2LongLinkedOpenHashMap.this.first = this.next; }
/* 1296 */       else { Long2LongLinkedOpenHashMap.this.link[this.prev] = Long2LongLinkedOpenHashMap.this.link[this.prev] ^ (Long2LongLinkedOpenHashMap.this.link[this.prev] ^ this.next & 0xFFFFFFFFL) & 0xFFFFFFFFL; }
/* 1297 */        if (this.next == -1) { Long2LongLinkedOpenHashMap.this.last = this.prev; }
/* 1298 */       else { Long2LongLinkedOpenHashMap.this.link[this.next] = Long2LongLinkedOpenHashMap.this.link[this.next] ^ (Long2LongLinkedOpenHashMap.this.link[this.next] ^ (this.prev & 0xFFFFFFFFL) << 32L) & 0xFFFFFFFF00000000L; }
/* 1299 */        int pos = this.curr;
/* 1300 */       this.curr = -1;
/* 1301 */       if (pos == Long2LongLinkedOpenHashMap.this.n) {
/* 1302 */         Long2LongLinkedOpenHashMap.this.containsNullKey = false;
/*      */       } else {
/*      */         
/* 1305 */         long[] key = Long2LongLinkedOpenHashMap.this.key; while (true) {
/*      */           long curr;
/*      */           int last;
/* 1308 */           pos = (last = pos) + 1 & Long2LongLinkedOpenHashMap.this.mask;
/*      */           while (true) {
/* 1310 */             if ((curr = key[pos]) == 0L) {
/* 1311 */               key[last] = 0L;
/*      */               return;
/*      */             } 
/* 1314 */             int slot = (int)HashCommon.mix(curr) & Long2LongLinkedOpenHashMap.this.mask;
/* 1315 */             if ((last <= pos) ? (last >= slot || slot > pos) : (last >= slot && slot > pos))
/* 1316 */               break;  pos = pos + 1 & Long2LongLinkedOpenHashMap.this.mask;
/*      */           } 
/* 1318 */           key[last] = curr;
/* 1319 */           Long2LongLinkedOpenHashMap.this.value[last] = Long2LongLinkedOpenHashMap.this.value[pos];
/* 1320 */           if (this.next == pos) this.next = last; 
/* 1321 */           if (this.prev == pos) this.prev = last; 
/* 1322 */           Long2LongLinkedOpenHashMap.this.fixPointers(pos, last);
/*      */         } 
/*      */       } 
/*      */     }
/*      */     
/*      */     public int skip(int n) {
/* 1328 */       int i = n;
/* 1329 */       for (; i-- != 0 && hasNext(); nextEntry());
/* 1330 */       return n - i - 1;
/*      */     }
/*      */     
/*      */     public int back(int n) {
/* 1334 */       int i = n;
/* 1335 */       for (; i-- != 0 && hasPrevious(); previousEntry());
/* 1336 */       return n - i - 1;
/*      */     }
/*      */     
/*      */     public void set(Long2LongMap.Entry ok) {
/* 1340 */       throw new UnsupportedOperationException();
/*      */     }
/*      */     
/*      */     public void add(Long2LongMap.Entry ok) {
/* 1344 */       throw new UnsupportedOperationException();
/*      */     }
/*      */     
/*      */     abstract void acceptOnIndex(ConsumerType param1ConsumerType, int param1Int); }
/*      */   
/*      */   private final class EntryIterator extends MapIterator<Consumer<? super Long2LongMap.Entry>> implements ObjectListIterator<Long2LongMap.Entry> {
/*      */     private Long2LongLinkedOpenHashMap.MapEntry entry;
/*      */     
/*      */     public EntryIterator() {}
/*      */     
/*      */     public EntryIterator(long from) {
/* 1355 */       super(from);
/*      */     }
/*      */ 
/*      */ 
/*      */     
/*      */     final void acceptOnIndex(Consumer<? super Long2LongMap.Entry> action, int index) {
/* 1361 */       action.accept(new Long2LongLinkedOpenHashMap.MapEntry(index));
/*      */     }
/*      */ 
/*      */     
/*      */     public Long2LongLinkedOpenHashMap.MapEntry next() {
/* 1366 */       return this.entry = new Long2LongLinkedOpenHashMap.MapEntry(nextEntry());
/*      */     }
/*      */ 
/*      */     
/*      */     public Long2LongLinkedOpenHashMap.MapEntry previous() {
/* 1371 */       return this.entry = new Long2LongLinkedOpenHashMap.MapEntry(previousEntry());
/*      */     }
/*      */ 
/*      */     
/*      */     public void remove() {
/* 1376 */       super.remove();
/* 1377 */       this.entry.index = -1;
/*      */     }
/*      */   }
/*      */   
/*      */   private final class FastEntryIterator extends MapIterator<Consumer<? super Long2LongMap.Entry>> implements ObjectListIterator<Long2LongMap.Entry> {
/* 1382 */     final Long2LongLinkedOpenHashMap.MapEntry entry = new Long2LongLinkedOpenHashMap.MapEntry();
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public FastEntryIterator(long from) {
/* 1388 */       super(from);
/*      */     }
/*      */ 
/*      */ 
/*      */     
/*      */     final void acceptOnIndex(Consumer<? super Long2LongMap.Entry> action, int index) {
/* 1394 */       this.entry.index = index;
/* 1395 */       action.accept(this.entry);
/*      */     }
/*      */ 
/*      */     
/*      */     public Long2LongLinkedOpenHashMap.MapEntry next() {
/* 1400 */       this.entry.index = nextEntry();
/* 1401 */       return this.entry;
/*      */     }
/*      */ 
/*      */     
/*      */     public Long2LongLinkedOpenHashMap.MapEntry previous() {
/* 1406 */       this.entry.index = previousEntry();
/* 1407 */       return this.entry;
/*      */     }
/*      */     
/*      */     public FastEntryIterator() {} }
/*      */   
/*      */   private final class MapEntrySet extends AbstractObjectSortedSet<Long2LongMap.Entry> implements Long2LongSortedMap.FastSortedEntrySet {
/*      */     private static final int SPLITERATOR_CHARACTERISTICS = 81;
/*      */     
/*      */     public ObjectBidirectionalIterator<Long2LongMap.Entry> iterator() {
/* 1416 */       return (ObjectBidirectionalIterator<Long2LongMap.Entry>)new Long2LongLinkedOpenHashMap.EntryIterator();
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     private MapEntrySet() {}
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public ObjectSpliterator<Long2LongMap.Entry> spliterator() {
/* 1434 */       return ObjectSpliterators.asSpliterator((ObjectIterator)iterator(), Size64.sizeOf(Long2LongLinkedOpenHashMap.this), 81);
/*      */     }
/*      */ 
/*      */     
/*      */     public Comparator<? super Long2LongMap.Entry> comparator() {
/* 1439 */       return null;
/*      */     }
/*      */ 
/*      */     
/*      */     public ObjectSortedSet<Long2LongMap.Entry> subSet(Long2LongMap.Entry fromElement, Long2LongMap.Entry toElement) {
/* 1444 */       throw new UnsupportedOperationException();
/*      */     }
/*      */ 
/*      */     
/*      */     public ObjectSortedSet<Long2LongMap.Entry> headSet(Long2LongMap.Entry toElement) {
/* 1449 */       throw new UnsupportedOperationException();
/*      */     }
/*      */ 
/*      */     
/*      */     public ObjectSortedSet<Long2LongMap.Entry> tailSet(Long2LongMap.Entry fromElement) {
/* 1454 */       throw new UnsupportedOperationException();
/*      */     }
/*      */ 
/*      */     
/*      */     public Long2LongMap.Entry first() {
/* 1459 */       if (Long2LongLinkedOpenHashMap.this.size == 0) throw new NoSuchElementException(); 
/* 1460 */       return new Long2LongLinkedOpenHashMap.MapEntry(Long2LongLinkedOpenHashMap.this.first);
/*      */     }
/*      */ 
/*      */     
/*      */     public Long2LongMap.Entry last() {
/* 1465 */       if (Long2LongLinkedOpenHashMap.this.size == 0) throw new NoSuchElementException(); 
/* 1466 */       return new Long2LongLinkedOpenHashMap.MapEntry(Long2LongLinkedOpenHashMap.this.last);
/*      */     }
/*      */ 
/*      */ 
/*      */     
/*      */     public boolean contains(Object o) {
/* 1472 */       if (!(o instanceof Map.Entry)) return false; 
/* 1473 */       Map.Entry<?, ?> e = (Map.Entry<?, ?>)o;
/* 1474 */       if (e.getKey() == null || !(e.getKey() instanceof Long)) return false; 
/* 1475 */       if (e.getValue() == null || !(e.getValue() instanceof Long)) return false; 
/* 1476 */       long k = ((Long)e.getKey()).longValue();
/* 1477 */       long v = ((Long)e.getValue()).longValue();
/* 1478 */       if (k == 0L) return (Long2LongLinkedOpenHashMap.this.containsNullKey && Long2LongLinkedOpenHashMap.this.value[Long2LongLinkedOpenHashMap.this.n] == v);
/*      */       
/* 1480 */       long[] key = Long2LongLinkedOpenHashMap.this.key;
/*      */       long curr;
/*      */       int pos;
/* 1483 */       if ((curr = key[pos = (int)HashCommon.mix(k) & Long2LongLinkedOpenHashMap.this.mask]) == 0L) return false; 
/* 1484 */       if (k == curr) return (Long2LongLinkedOpenHashMap.this.value[pos] == v);
/*      */       
/*      */       while (true) {
/* 1487 */         if ((curr = key[pos = pos + 1 & Long2LongLinkedOpenHashMap.this.mask]) == 0L) return false; 
/* 1488 */         if (k == curr) return (Long2LongLinkedOpenHashMap.this.value[pos] == v);
/*      */       
/*      */       } 
/*      */     }
/*      */ 
/*      */     
/*      */     public boolean remove(Object o) {
/* 1495 */       if (!(o instanceof Map.Entry)) return false; 
/* 1496 */       Map.Entry<?, ?> e = (Map.Entry<?, ?>)o;
/* 1497 */       if (e.getKey() == null || !(e.getKey() instanceof Long)) return false; 
/* 1498 */       if (e.getValue() == null || !(e.getValue() instanceof Long)) return false; 
/* 1499 */       long k = ((Long)e.getKey()).longValue();
/* 1500 */       long v = ((Long)e.getValue()).longValue();
/* 1501 */       if (k == 0L) {
/* 1502 */         if (Long2LongLinkedOpenHashMap.this.containsNullKey && Long2LongLinkedOpenHashMap.this.value[Long2LongLinkedOpenHashMap.this.n] == v) {
/* 1503 */           Long2LongLinkedOpenHashMap.this.removeNullEntry();
/* 1504 */           return true;
/*      */         } 
/* 1506 */         return false;
/*      */       } 
/*      */       
/* 1509 */       long[] key = Long2LongLinkedOpenHashMap.this.key;
/*      */       long curr;
/*      */       int pos;
/* 1512 */       if ((curr = key[pos = (int)HashCommon.mix(k) & Long2LongLinkedOpenHashMap.this.mask]) == 0L) return false; 
/* 1513 */       if (curr == k) {
/* 1514 */         if (Long2LongLinkedOpenHashMap.this.value[pos] == v) {
/* 1515 */           Long2LongLinkedOpenHashMap.this.removeEntry(pos);
/* 1516 */           return true;
/*      */         } 
/* 1518 */         return false;
/*      */       } 
/*      */       while (true) {
/* 1521 */         if ((curr = key[pos = pos + 1 & Long2LongLinkedOpenHashMap.this.mask]) == 0L) return false; 
/* 1522 */         if (curr == k && 
/* 1523 */           Long2LongLinkedOpenHashMap.this.value[pos] == v) {
/* 1524 */           Long2LongLinkedOpenHashMap.this.removeEntry(pos);
/* 1525 */           return true;
/*      */         } 
/*      */       } 
/*      */     }
/*      */ 
/*      */ 
/*      */     
/*      */     public int size() {
/* 1533 */       return Long2LongLinkedOpenHashMap.this.size;
/*      */     }
/*      */ 
/*      */     
/*      */     public void clear() {
/* 1538 */       Long2LongLinkedOpenHashMap.this.clear();
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public ObjectListIterator<Long2LongMap.Entry> iterator(Long2LongMap.Entry from) {
/* 1551 */       return new Long2LongLinkedOpenHashMap.EntryIterator(from.getLongKey());
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public ObjectListIterator<Long2LongMap.Entry> fastIterator() {
/* 1562 */       return new Long2LongLinkedOpenHashMap.FastEntryIterator();
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public ObjectListIterator<Long2LongMap.Entry> fastIterator(Long2LongMap.Entry from) {
/* 1575 */       return new Long2LongLinkedOpenHashMap.FastEntryIterator(from.getLongKey());
/*      */     }
/*      */ 
/*      */ 
/*      */     
/*      */     public void forEach(Consumer<? super Long2LongMap.Entry> consumer) {
/* 1581 */       for (int i = Long2LongLinkedOpenHashMap.this.size, next = Long2LongLinkedOpenHashMap.this.first; i-- != 0; ) {
/* 1582 */         int curr = next;
/* 1583 */         next = (int)Long2LongLinkedOpenHashMap.this.link[curr];
/* 1584 */         consumer.accept(new Long2LongLinkedOpenHashMap.MapEntry(curr));
/*      */       } 
/*      */     }
/*      */ 
/*      */ 
/*      */     
/*      */     public void fastForEach(Consumer<? super Long2LongMap.Entry> consumer) {
/* 1591 */       Long2LongLinkedOpenHashMap.MapEntry entry = new Long2LongLinkedOpenHashMap.MapEntry();
/* 1592 */       for (int i = Long2LongLinkedOpenHashMap.this.size, next = Long2LongLinkedOpenHashMap.this.first; i-- != 0; ) {
/* 1593 */         entry.index = next;
/* 1594 */         next = (int)Long2LongLinkedOpenHashMap.this.link[next];
/* 1595 */         consumer.accept(entry);
/*      */       } 
/*      */     }
/*      */   }
/*      */ 
/*      */   
/*      */   public Long2LongSortedMap.FastSortedEntrySet long2LongEntrySet() {
/* 1602 */     if (this.entries == null) this.entries = new MapEntrySet(); 
/* 1603 */     return this.entries;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private final class KeyIterator
/*      */     extends MapIterator<LongConsumer>
/*      */     implements LongListIterator
/*      */   {
/*      */     public KeyIterator(long k) {
/* 1616 */       super(k);
/*      */     }
/*      */ 
/*      */     
/*      */     public long previousLong() {
/* 1621 */       return Long2LongLinkedOpenHashMap.this.key[previousEntry()];
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public KeyIterator() {}
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     final void acceptOnIndex(LongConsumer action, int index) {
/* 1633 */       action.accept(Long2LongLinkedOpenHashMap.this.key[index]);
/*      */     }
/*      */ 
/*      */     
/*      */     public long nextLong() {
/* 1638 */       return Long2LongLinkedOpenHashMap.this.key[nextEntry()];
/*      */     } }
/*      */   
/*      */   private final class KeySet extends AbstractLongSortedSet {
/*      */     private static final int SPLITERATOR_CHARACTERISTICS = 337;
/*      */     
/*      */     private KeySet() {}
/*      */     
/*      */     public LongListIterator iterator(long from) {
/* 1647 */       return new Long2LongLinkedOpenHashMap.KeyIterator(from);
/*      */     }
/*      */ 
/*      */     
/*      */     public LongListIterator iterator() {
/* 1652 */       return new Long2LongLinkedOpenHashMap.KeyIterator();
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public LongSpliterator spliterator() {
/* 1662 */       return LongSpliterators.asSpliterator(iterator(), Size64.sizeOf(Long2LongLinkedOpenHashMap.this), 337);
/*      */     }
/*      */ 
/*      */ 
/*      */     
/*      */     public void forEach(LongConsumer consumer) {
/* 1668 */       for (int i = Long2LongLinkedOpenHashMap.this.size, next = Long2LongLinkedOpenHashMap.this.first; i-- != 0; ) {
/* 1669 */         int curr = next;
/* 1670 */         next = (int)Long2LongLinkedOpenHashMap.this.link[curr];
/* 1671 */         consumer.accept(Long2LongLinkedOpenHashMap.this.key[curr]);
/*      */       } 
/*      */     }
/*      */ 
/*      */     
/*      */     public int size() {
/* 1677 */       return Long2LongLinkedOpenHashMap.this.size;
/*      */     }
/*      */ 
/*      */     
/*      */     public boolean contains(long k) {
/* 1682 */       return Long2LongLinkedOpenHashMap.this.containsKey(k);
/*      */     }
/*      */ 
/*      */     
/*      */     public boolean remove(long k) {
/* 1687 */       int oldSize = Long2LongLinkedOpenHashMap.this.size;
/* 1688 */       Long2LongLinkedOpenHashMap.this.remove(k);
/* 1689 */       return (Long2LongLinkedOpenHashMap.this.size != oldSize);
/*      */     }
/*      */ 
/*      */     
/*      */     public void clear() {
/* 1694 */       Long2LongLinkedOpenHashMap.this.clear();
/*      */     }
/*      */ 
/*      */     
/*      */     public long firstLong() {
/* 1699 */       if (Long2LongLinkedOpenHashMap.this.size == 0) throw new NoSuchElementException(); 
/* 1700 */       return Long2LongLinkedOpenHashMap.this.key[Long2LongLinkedOpenHashMap.this.first];
/*      */     }
/*      */ 
/*      */     
/*      */     public long lastLong() {
/* 1705 */       if (Long2LongLinkedOpenHashMap.this.size == 0) throw new NoSuchElementException(); 
/* 1706 */       return Long2LongLinkedOpenHashMap.this.key[Long2LongLinkedOpenHashMap.this.last];
/*      */     }
/*      */ 
/*      */     
/*      */     public LongComparator comparator() {
/* 1711 */       return null;
/*      */     }
/*      */ 
/*      */     
/*      */     public LongSortedSet tailSet(long from) {
/* 1716 */       throw new UnsupportedOperationException();
/*      */     }
/*      */ 
/*      */     
/*      */     public LongSortedSet headSet(long to) {
/* 1721 */       throw new UnsupportedOperationException();
/*      */     }
/*      */ 
/*      */     
/*      */     public LongSortedSet subSet(long from, long to) {
/* 1726 */       throw new UnsupportedOperationException();
/*      */     }
/*      */   }
/*      */ 
/*      */   
/*      */   public LongSortedSet keySet() {
/* 1732 */     if (this.keys == null) this.keys = new KeySet(); 
/* 1733 */     return this.keys;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private final class ValueIterator
/*      */     extends MapIterator<LongConsumer>
/*      */     implements LongListIterator
/*      */   {
/*      */     public long previousLong() {
/* 1747 */       return Long2LongLinkedOpenHashMap.this.value[previousEntry()];
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     final void acceptOnIndex(LongConsumer action, int index) {
/* 1759 */       action.accept(Long2LongLinkedOpenHashMap.this.value[index]);
/*      */     }
/*      */ 
/*      */     
/*      */     public long nextLong() {
/* 1764 */       return Long2LongLinkedOpenHashMap.this.value[nextEntry()];
/*      */     }
/*      */   }
/*      */ 
/*      */   
/*      */   public LongCollection values() {
/* 1770 */     if (this.values == null) this.values = new AbstractLongCollection()
/*      */         {
/*      */           private static final int SPLITERATOR_CHARACTERISTICS = 336;
/*      */           
/*      */           public LongIterator iterator() {
/* 1775 */             return new Long2LongLinkedOpenHashMap.ValueIterator();
/*      */           }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */           
/*      */           public LongSpliterator spliterator() {
/* 1785 */             return LongSpliterators.asSpliterator(iterator(), Size64.sizeOf(Long2LongLinkedOpenHashMap.this), 336);
/*      */           }
/*      */ 
/*      */ 
/*      */           
/*      */           public void forEach(LongConsumer consumer) {
/* 1791 */             for (int i = Long2LongLinkedOpenHashMap.this.size, next = Long2LongLinkedOpenHashMap.this.first; i-- != 0; ) {
/* 1792 */               int curr = next;
/* 1793 */               next = (int)Long2LongLinkedOpenHashMap.this.link[curr];
/* 1794 */               consumer.accept(Long2LongLinkedOpenHashMap.this.value[curr]);
/*      */             } 
/*      */           }
/*      */ 
/*      */           
/*      */           public int size() {
/* 1800 */             return Long2LongLinkedOpenHashMap.this.size;
/*      */           }
/*      */ 
/*      */           
/*      */           public boolean contains(long v) {
/* 1805 */             return Long2LongLinkedOpenHashMap.this.containsValue(v);
/*      */           }
/*      */ 
/*      */           
/*      */           public void clear() {
/* 1810 */             Long2LongLinkedOpenHashMap.this.clear();
/*      */           }
/*      */         }; 
/* 1813 */     return this.values;
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
/* 1830 */     return trim(this.size);
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
/* 1852 */     int l = HashCommon.nextPowerOfTwo((int)Math.ceil((n / this.f)));
/* 1853 */     if (l >= this.n || this.size > HashCommon.maxFill(l, this.f)) return true; 
/*      */     try {
/* 1855 */       rehash(l);
/* 1856 */     } catch (OutOfMemoryError cantDoIt) {
/* 1857 */       return false;
/*      */     } 
/* 1859 */     return true;
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
/* 1874 */     long[] key = this.key;
/* 1875 */     long[] value = this.value;
/* 1876 */     int mask = newN - 1;
/* 1877 */     long[] newKey = new long[newN + 1];
/* 1878 */     long[] newValue = new long[newN + 1];
/* 1879 */     int i = this.first, prev = -1, newPrev = -1;
/* 1880 */     long[] link = this.link;
/* 1881 */     long[] newLink = new long[newN + 1];
/* 1882 */     this.first = -1;
/* 1883 */     for (int j = this.size; j-- != 0; ) {
/* 1884 */       int pos; if (key[i] == 0L) { pos = newN; }
/*      */       else
/* 1886 */       { pos = (int)HashCommon.mix(key[i]) & mask;
/* 1887 */         for (; newKey[pos] != 0L; pos = pos + 1 & mask); }
/*      */       
/* 1889 */       newKey[pos] = key[i];
/* 1890 */       newValue[pos] = value[i];
/* 1891 */       if (prev != -1) {
/* 1892 */         newLink[newPrev] = newLink[newPrev] ^ (newLink[newPrev] ^ pos & 0xFFFFFFFFL) & 0xFFFFFFFFL;
/* 1893 */         newLink[pos] = newLink[pos] ^ (newLink[pos] ^ (newPrev & 0xFFFFFFFFL) << 32L) & 0xFFFFFFFF00000000L;
/* 1894 */         newPrev = pos;
/*      */       } else {
/* 1896 */         newPrev = this.first = pos;
/*      */         
/* 1898 */         newLink[pos] = -1L;
/*      */       } 
/* 1900 */       int t = i;
/* 1901 */       i = (int)link[i];
/* 1902 */       prev = t;
/*      */     } 
/* 1904 */     this.link = newLink;
/* 1905 */     this.last = newPrev;
/* 1906 */     if (newPrev != -1)
/*      */     {
/* 1908 */       newLink[newPrev] = newLink[newPrev] | 0xFFFFFFFFL; } 
/* 1909 */     this.n = newN;
/* 1910 */     this.mask = mask;
/* 1911 */     this.maxFill = HashCommon.maxFill(this.n, this.f);
/* 1912 */     this.key = newKey;
/* 1913 */     this.value = newValue;
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
/*      */   public Long2LongLinkedOpenHashMap clone() {
/*      */     Long2LongLinkedOpenHashMap c;
/*      */     try {
/* 1930 */       c = (Long2LongLinkedOpenHashMap)super.clone();
/* 1931 */     } catch (CloneNotSupportedException cantHappen) {
/* 1932 */       throw new InternalError();
/*      */     } 
/* 1934 */     c.keys = null;
/* 1935 */     c.values = null;
/* 1936 */     c.entries = null;
/* 1937 */     c.containsNullKey = this.containsNullKey;
/* 1938 */     c.key = (long[])this.key.clone();
/* 1939 */     c.value = (long[])this.value.clone();
/* 1940 */     c.link = (long[])this.link.clone();
/* 1941 */     return c;
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
/* 1955 */     int h = 0;
/* 1956 */     for (int j = realSize(), i = 0, t = 0; j-- != 0; ) {
/* 1957 */       for (; this.key[i] == 0L; i++);
/* 1958 */       t = HashCommon.long2int(this.key[i]);
/* 1959 */       t ^= HashCommon.long2int(this.value[i]);
/* 1960 */       h += t;
/* 1961 */       i++;
/*      */     } 
/*      */     
/* 1964 */     if (this.containsNullKey) h += HashCommon.long2int(this.value[this.n]); 
/* 1965 */     return h;
/*      */   }
/*      */   
/*      */   private void writeObject(ObjectOutputStream s) throws IOException {
/* 1969 */     long[] key = this.key;
/* 1970 */     long[] value = this.value;
/* 1971 */     EntryIterator i = new EntryIterator();
/* 1972 */     s.defaultWriteObject();
/* 1973 */     for (int j = this.size; j-- != 0; ) {
/* 1974 */       int e = i.nextEntry();
/* 1975 */       s.writeLong(key[e]);
/* 1976 */       s.writeLong(value[e]);
/*      */     } 
/*      */   }
/*      */   
/*      */   private void readObject(ObjectInputStream s) throws IOException, ClassNotFoundException {
/* 1981 */     s.defaultReadObject();
/* 1982 */     this.n = HashCommon.arraySize(this.size, this.f);
/* 1983 */     this.maxFill = HashCommon.maxFill(this.n, this.f);
/* 1984 */     this.mask = this.n - 1;
/* 1985 */     long[] key = this.key = new long[this.n + 1];
/* 1986 */     long[] value = this.value = new long[this.n + 1];
/* 1987 */     long[] link = this.link = new long[this.n + 1];
/* 1988 */     int prev = -1;
/* 1989 */     this.first = this.last = -1;
/*      */ 
/*      */     
/* 1992 */     for (int i = this.size; i-- != 0; ) {
/* 1993 */       int pos; long k = s.readLong();
/* 1994 */       long v = s.readLong();
/* 1995 */       if (k == 0L) {
/* 1996 */         pos = this.n;
/* 1997 */         this.containsNullKey = true;
/*      */       } else {
/* 1999 */         pos = (int)HashCommon.mix(k) & this.mask;
/* 2000 */         for (; key[pos] != 0L; pos = pos + 1 & this.mask);
/*      */       } 
/* 2002 */       key[pos] = k;
/* 2003 */       value[pos] = v;
/* 2004 */       if (this.first != -1) {
/* 2005 */         link[prev] = link[prev] ^ (link[prev] ^ pos & 0xFFFFFFFFL) & 0xFFFFFFFFL;
/* 2006 */         link[pos] = link[pos] ^ (link[pos] ^ (prev & 0xFFFFFFFFL) << 32L) & 0xFFFFFFFF00000000L;
/* 2007 */         prev = pos; continue;
/*      */       } 
/* 2009 */       prev = this.first = pos;
/*      */       
/* 2011 */       link[pos] = link[pos] | 0xFFFFFFFF00000000L;
/*      */     } 
/*      */     
/* 2014 */     this.last = prev;
/* 2015 */     if (prev != -1)
/*      */     {
/* 2017 */       link[prev] = link[prev] | 0xFFFFFFFFL;
/*      */     }
/*      */   }
/*      */   
/*      */   private void checkTable() {}
/*      */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\i\\unimi\dsi\fastutil\longs\Long2LongLinkedOpenHashMap.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */