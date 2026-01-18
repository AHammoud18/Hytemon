/*      */ package it.unimi.dsi.fastutil.objects;
/*      */ 
/*      */ import it.unimi.dsi.fastutil.Hash;
/*      */ import it.unimi.dsi.fastutil.HashCommon;
/*      */ import it.unimi.dsi.fastutil.Size64;
/*      */ import it.unimi.dsi.fastutil.longs.AbstractLongCollection;
/*      */ import it.unimi.dsi.fastutil.longs.LongCollection;
/*      */ import it.unimi.dsi.fastutil.longs.LongIterator;
/*      */ import it.unimi.dsi.fastutil.longs.LongListIterator;
/*      */ import it.unimi.dsi.fastutil.longs.LongSpliterator;
/*      */ import it.unimi.dsi.fastutil.longs.LongSpliterators;
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
/*      */ import java.util.SortedMap;
/*      */ import java.util.SortedSet;
/*      */ import java.util.Spliterator;
/*      */ import java.util.function.BiFunction;
/*      */ import java.util.function.Consumer;
/*      */ import java.util.function.LongConsumer;
/*      */ import java.util.function.ToLongFunction;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ public class Object2LongLinkedOpenCustomHashMap<K>
/*      */   extends AbstractObject2LongSortedMap<K>
/*      */   implements Serializable, Cloneable, Hash
/*      */ {
/*      */   private static final long serialVersionUID = 0L;
/*      */   private static final boolean ASSERTS = false;
/*      */   protected transient K[] key;
/*      */   protected transient long[] value;
/*      */   protected transient int mask;
/*      */   protected transient boolean containsNullKey;
/*      */   protected Hash.Strategy<? super K> strategy;
/*  103 */   protected transient int first = -1;
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*  108 */   protected transient int last = -1;
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
/*      */   protected int size;
/*      */ 
/*      */   
/*      */   protected final float f;
/*      */ 
/*      */   
/*      */   protected transient Object2LongSortedMap.FastSortedEntrySet<K> entries;
/*      */ 
/*      */   
/*      */   protected transient ObjectSortedSet<K> keys;
/*      */ 
/*      */   
/*      */   protected transient LongCollection values;
/*      */ 
/*      */ 
/*      */   
/*      */   public Object2LongLinkedOpenCustomHashMap(int expected, float f, Hash.Strategy<? super K> strategy) {
/*  144 */     this.strategy = strategy;
/*  145 */     if (f <= 0.0F || f >= 1.0F) throw new IllegalArgumentException("Load factor must be greater than 0 and smaller than 1"); 
/*  146 */     if (expected < 0) throw new IllegalArgumentException("The expected number of elements must be nonnegative"); 
/*  147 */     this.f = f;
/*  148 */     this.minN = this.n = HashCommon.arraySize(expected, f);
/*  149 */     this.mask = this.n - 1;
/*  150 */     this.maxFill = HashCommon.maxFill(this.n, f);
/*  151 */     this.key = (K[])new Object[this.n + 1];
/*  152 */     this.value = new long[this.n + 1];
/*  153 */     this.link = new long[this.n + 1];
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Object2LongLinkedOpenCustomHashMap(int expected, Hash.Strategy<? super K> strategy) {
/*  163 */     this(expected, 0.75F, strategy);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Object2LongLinkedOpenCustomHashMap(Hash.Strategy<? super K> strategy) {
/*  173 */     this(16, 0.75F, strategy);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Object2LongLinkedOpenCustomHashMap(Map<? extends K, ? extends Long> m, float f, Hash.Strategy<? super K> strategy) {
/*  184 */     this(m.size(), f, strategy);
/*  185 */     putAll(m);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Object2LongLinkedOpenCustomHashMap(Map<? extends K, ? extends Long> m, Hash.Strategy<? super K> strategy) {
/*  195 */     this(m, 0.75F, strategy);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Object2LongLinkedOpenCustomHashMap(Object2LongMap<K> m, float f, Hash.Strategy<? super K> strategy) {
/*  206 */     this(m.size(), f, strategy);
/*  207 */     putAll(m);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Object2LongLinkedOpenCustomHashMap(Object2LongMap<K> m, Hash.Strategy<? super K> strategy) {
/*  218 */     this(m, 0.75F, strategy);
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
/*      */   public Object2LongLinkedOpenCustomHashMap(K[] k, long[] v, float f, Hash.Strategy<? super K> strategy) {
/*  231 */     this(k.length, f, strategy);
/*  232 */     if (k.length != v.length) throw new IllegalArgumentException("The key array and the value array have different lengths (" + k.length + " and " + v.length + ")"); 
/*  233 */     for (int i = 0; i < k.length; ) { put(k[i], v[i]); i++; }
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
/*      */   public Object2LongLinkedOpenCustomHashMap(K[] k, long[] v, Hash.Strategy<? super K> strategy) {
/*  246 */     this(k, v, 0.75F, strategy);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Hash.Strategy<? super K> strategy() {
/*  255 */     return this.strategy;
/*      */   }
/*      */   
/*      */   private int realSize() {
/*  259 */     return this.containsNullKey ? (this.size - 1) : this.size;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void ensureCapacity(int capacity) {
/*  269 */     int needed = HashCommon.arraySize(capacity, this.f);
/*  270 */     if (needed > this.n) rehash(needed); 
/*      */   }
/*      */   
/*      */   private void tryCapacity(long capacity) {
/*  274 */     int needed = (int)Math.min(1073741824L, Math.max(2L, HashCommon.nextPowerOfTwo((long)Math.ceil(((float)capacity / this.f)))));
/*  275 */     if (needed > this.n) rehash(needed); 
/*      */   }
/*      */   
/*      */   private long removeEntry(int pos) {
/*  279 */     long oldValue = this.value[pos];
/*  280 */     this.size--;
/*  281 */     fixPointers(pos);
/*  282 */     shiftKeys(pos);
/*  283 */     if (this.n > this.minN && this.size < this.maxFill / 4 && this.n > 16) rehash(this.n / 2); 
/*  284 */     return oldValue;
/*      */   }
/*      */   
/*      */   private long removeNullEntry() {
/*  288 */     this.containsNullKey = false;
/*  289 */     this.key[this.n] = null;
/*  290 */     long oldValue = this.value[this.n];
/*  291 */     this.size--;
/*  292 */     fixPointers(this.n);
/*  293 */     if (this.n > this.minN && this.size < this.maxFill / 4 && this.n > 16) rehash(this.n / 2); 
/*  294 */     return oldValue;
/*      */   }
/*      */ 
/*      */   
/*      */   public void putAll(Map<? extends K, ? extends Long> m) {
/*  299 */     if (this.f <= 0.5D) { ensureCapacity(m.size()); }
/*  300 */     else { tryCapacity((size() + m.size())); }
/*      */     
/*  302 */     super.putAll(m);
/*      */   }
/*      */ 
/*      */   
/*      */   private int find(K k) {
/*  307 */     if (this.strategy.equals(k, null)) return this.containsNullKey ? this.n : -(this.n + 1);
/*      */     
/*  309 */     K[] key = this.key;
/*      */     K curr;
/*      */     int pos;
/*  312 */     if ((curr = key[pos = HashCommon.mix(this.strategy.hashCode(k)) & this.mask]) == null) return -(pos + 1); 
/*  313 */     if (this.strategy.equals(k, curr)) return pos;
/*      */     
/*      */     while (true) {
/*  316 */       if ((curr = key[pos = pos + 1 & this.mask]) == null) return -(pos + 1); 
/*  317 */       if (this.strategy.equals(k, curr)) return pos; 
/*      */     } 
/*      */   }
/*      */   
/*      */   private void insert(int pos, K k, long v) {
/*  322 */     if (pos == this.n) this.containsNullKey = true; 
/*  323 */     this.key[pos] = k;
/*  324 */     this.value[pos] = v;
/*  325 */     if (this.size == 0) {
/*  326 */       this.first = this.last = pos;
/*      */       
/*  328 */       this.link[pos] = -1L;
/*      */     } else {
/*  330 */       this.link[this.last] = this.link[this.last] ^ (this.link[this.last] ^ pos & 0xFFFFFFFFL) & 0xFFFFFFFFL;
/*  331 */       this.link[pos] = (this.last & 0xFFFFFFFFL) << 32L | 0xFFFFFFFFL;
/*  332 */       this.last = pos;
/*      */     } 
/*  334 */     if (this.size++ >= this.maxFill) rehash(HashCommon.arraySize(this.size + 1, this.f));
/*      */   
/*      */   }
/*      */ 
/*      */   
/*      */   public long put(K k, long v) {
/*  340 */     int pos = find(k);
/*  341 */     if (pos < 0) {
/*  342 */       insert(-pos - 1, k, v);
/*  343 */       return this.defRetValue;
/*      */     } 
/*  345 */     long oldValue = this.value[pos];
/*  346 */     this.value[pos] = v;
/*  347 */     return oldValue;
/*      */   }
/*      */   
/*      */   private long addToValue(int pos, long incr) {
/*  351 */     long oldValue = this.value[pos];
/*  352 */     this.value[pos] = oldValue + incr;
/*  353 */     return oldValue;
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
/*      */   public long addTo(K k, long incr) {
/*      */     int pos;
/*  371 */     if (this.strategy.equals(k, null)) {
/*  372 */       if (this.containsNullKey) return addToValue(this.n, incr); 
/*  373 */       pos = this.n;
/*  374 */       this.containsNullKey = true;
/*      */     } else {
/*      */       
/*  377 */       K[] key = this.key;
/*      */       K curr;
/*  379 */       if ((curr = key[pos = HashCommon.mix(this.strategy.hashCode(k)) & this.mask]) != null) {
/*  380 */         if (this.strategy.equals(curr, k)) return addToValue(pos, incr); 
/*  381 */         while ((curr = key[pos = pos + 1 & this.mask]) != null) { if (this.strategy.equals(curr, k)) return addToValue(pos, incr);  }
/*      */       
/*      */       } 
/*  384 */     }  this.key[pos] = k;
/*  385 */     this.value[pos] = this.defRetValue + incr;
/*  386 */     if (this.size == 0) {
/*  387 */       this.first = this.last = pos;
/*      */       
/*  389 */       this.link[pos] = -1L;
/*      */     } else {
/*  391 */       this.link[this.last] = this.link[this.last] ^ (this.link[this.last] ^ pos & 0xFFFFFFFFL) & 0xFFFFFFFFL;
/*  392 */       this.link[pos] = (this.last & 0xFFFFFFFFL) << 32L | 0xFFFFFFFFL;
/*  393 */       this.last = pos;
/*      */     } 
/*  395 */     if (this.size++ >= this.maxFill) rehash(HashCommon.arraySize(this.size + 1, this.f));
/*      */     
/*  397 */     return this.defRetValue;
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
/*  410 */     K[] key = this.key; while (true) {
/*      */       K curr; int last;
/*  412 */       pos = (last = pos) + 1 & this.mask;
/*      */       while (true) {
/*  414 */         if ((curr = key[pos]) == null) {
/*  415 */           key[last] = null;
/*      */           return;
/*      */         } 
/*  418 */         int slot = HashCommon.mix(this.strategy.hashCode(curr)) & this.mask;
/*  419 */         if ((last <= pos) ? (last >= slot || slot > pos) : (last >= slot && slot > pos))
/*  420 */           break;  pos = pos + 1 & this.mask;
/*      */       } 
/*  422 */       key[last] = curr;
/*  423 */       this.value[last] = this.value[pos];
/*  424 */       fixPointers(pos, last);
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public long removeLong(Object k) {
/*  431 */     if (this.strategy.equals(k, null)) {
/*  432 */       if (this.containsNullKey) return removeNullEntry(); 
/*  433 */       return this.defRetValue;
/*      */     } 
/*      */     
/*  436 */     K[] key = this.key;
/*      */     K curr;
/*      */     int pos;
/*  439 */     if ((curr = key[pos = HashCommon.mix(this.strategy.hashCode(k)) & this.mask]) == null) return this.defRetValue; 
/*  440 */     if (this.strategy.equals(k, curr)) return removeEntry(pos); 
/*      */     while (true) {
/*  442 */       if ((curr = key[pos = pos + 1 & this.mask]) == null) return this.defRetValue; 
/*  443 */       if (this.strategy.equals(k, curr)) return removeEntry(pos); 
/*      */     } 
/*      */   }
/*      */   
/*      */   private long setValue(int pos, long v) {
/*  448 */     long oldValue = this.value[pos];
/*  449 */     this.value[pos] = v;
/*  450 */     return oldValue;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public long removeFirstLong() {
/*  460 */     if (this.size == 0) throw new NoSuchElementException(); 
/*  461 */     int pos = this.first;
/*      */     
/*  463 */     if (this.size == 1) { this.first = this.last = -1; }
/*      */     else
/*  465 */     { this.first = (int)this.link[pos];
/*  466 */       if (0 <= this.first)
/*      */       {
/*  468 */         this.link[this.first] = this.link[this.first] | 0xFFFFFFFF00000000L;
/*      */       } }
/*      */     
/*  471 */     this.size--;
/*  472 */     long v = this.value[pos];
/*  473 */     if (pos == this.n)
/*  474 */     { this.containsNullKey = false;
/*  475 */       this.key[this.n] = null; }
/*  476 */     else { shiftKeys(pos); }
/*  477 */      if (this.n > this.minN && this.size < this.maxFill / 4 && this.n > 16) rehash(this.n / 2); 
/*  478 */     return v;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public long removeLastLong() {
/*  488 */     if (this.size == 0) throw new NoSuchElementException(); 
/*  489 */     int pos = this.last;
/*      */     
/*  491 */     if (this.size == 1) { this.first = this.last = -1; }
/*      */     else
/*  493 */     { this.last = (int)(this.link[pos] >>> 32L);
/*  494 */       if (0 <= this.last)
/*      */       {
/*  496 */         this.link[this.last] = this.link[this.last] | 0xFFFFFFFFL;
/*      */       } }
/*      */     
/*  499 */     this.size--;
/*  500 */     long v = this.value[pos];
/*  501 */     if (pos == this.n)
/*  502 */     { this.containsNullKey = false;
/*  503 */       this.key[this.n] = null; }
/*  504 */     else { shiftKeys(pos); }
/*  505 */      if (this.n > this.minN && this.size < this.maxFill / 4 && this.n > 16) rehash(this.n / 2); 
/*  506 */     return v;
/*      */   }
/*      */   
/*      */   private void moveIndexToFirst(int i) {
/*  510 */     if (this.size == 1 || this.first == i)
/*  511 */       return;  if (this.last == i) {
/*  512 */       this.last = (int)(this.link[i] >>> 32L);
/*      */       
/*  514 */       this.link[this.last] = this.link[this.last] | 0xFFFFFFFFL;
/*      */     } else {
/*  516 */       long linki = this.link[i];
/*  517 */       int prev = (int)(linki >>> 32L);
/*  518 */       int next = (int)linki;
/*  519 */       this.link[prev] = this.link[prev] ^ (this.link[prev] ^ linki & 0xFFFFFFFFL) & 0xFFFFFFFFL;
/*  520 */       this.link[next] = this.link[next] ^ (this.link[next] ^ linki & 0xFFFFFFFF00000000L) & 0xFFFFFFFF00000000L;
/*      */     } 
/*  522 */     this.link[this.first] = this.link[this.first] ^ (this.link[this.first] ^ (i & 0xFFFFFFFFL) << 32L) & 0xFFFFFFFF00000000L;
/*  523 */     this.link[i] = 0xFFFFFFFF00000000L | this.first & 0xFFFFFFFFL;
/*  524 */     this.first = i;
/*      */   }
/*      */   
/*      */   private void moveIndexToLast(int i) {
/*  528 */     if (this.size == 1 || this.last == i)
/*  529 */       return;  if (this.first == i) {
/*  530 */       this.first = (int)this.link[i];
/*      */       
/*  532 */       this.link[this.first] = this.link[this.first] | 0xFFFFFFFF00000000L;
/*      */     } else {
/*  534 */       long linki = this.link[i];
/*  535 */       int prev = (int)(linki >>> 32L);
/*  536 */       int next = (int)linki;
/*  537 */       this.link[prev] = this.link[prev] ^ (this.link[prev] ^ linki & 0xFFFFFFFFL) & 0xFFFFFFFFL;
/*  538 */       this.link[next] = this.link[next] ^ (this.link[next] ^ linki & 0xFFFFFFFF00000000L) & 0xFFFFFFFF00000000L;
/*      */     } 
/*  540 */     this.link[this.last] = this.link[this.last] ^ (this.link[this.last] ^ i & 0xFFFFFFFFL) & 0xFFFFFFFFL;
/*  541 */     this.link[i] = (this.last & 0xFFFFFFFFL) << 32L | 0xFFFFFFFFL;
/*  542 */     this.last = i;
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
/*      */   public long getAndMoveToFirst(K k) {
/*  554 */     if (this.strategy.equals(k, null)) {
/*  555 */       if (this.containsNullKey) {
/*  556 */         moveIndexToFirst(this.n);
/*  557 */         return this.value[this.n];
/*      */       } 
/*  559 */       return this.defRetValue;
/*      */     } 
/*      */     
/*  562 */     K[] key = this.key;
/*      */     K curr;
/*      */     int pos;
/*  565 */     if ((curr = key[pos = HashCommon.mix(this.strategy.hashCode(k)) & this.mask]) == null) return this.defRetValue; 
/*  566 */     if (this.strategy.equals(k, curr)) {
/*  567 */       moveIndexToFirst(pos);
/*  568 */       return this.value[pos];
/*      */     } 
/*      */     
/*      */     while (true) {
/*  572 */       if ((curr = key[pos = pos + 1 & this.mask]) == null) return this.defRetValue; 
/*  573 */       if (this.strategy.equals(k, curr)) {
/*  574 */         moveIndexToFirst(pos);
/*  575 */         return this.value[pos];
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
/*      */   public long getAndMoveToLast(K k) {
/*  589 */     if (this.strategy.equals(k, null)) {
/*  590 */       if (this.containsNullKey) {
/*  591 */         moveIndexToLast(this.n);
/*  592 */         return this.value[this.n];
/*      */       } 
/*  594 */       return this.defRetValue;
/*      */     } 
/*      */     
/*  597 */     K[] key = this.key;
/*      */     K curr;
/*      */     int pos;
/*  600 */     if ((curr = key[pos = HashCommon.mix(this.strategy.hashCode(k)) & this.mask]) == null) return this.defRetValue; 
/*  601 */     if (this.strategy.equals(k, curr)) {
/*  602 */       moveIndexToLast(pos);
/*  603 */       return this.value[pos];
/*      */     } 
/*      */     
/*      */     while (true) {
/*  607 */       if ((curr = key[pos = pos + 1 & this.mask]) == null) return this.defRetValue; 
/*  608 */       if (this.strategy.equals(k, curr)) {
/*  609 */         moveIndexToLast(pos);
/*  610 */         return this.value[pos];
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
/*      */   public long putAndMoveToFirst(K k, long v) {
/*      */     int pos;
/*  626 */     if (this.strategy.equals(k, null)) {
/*  627 */       if (this.containsNullKey) {
/*  628 */         moveIndexToFirst(this.n);
/*  629 */         return setValue(this.n, v);
/*      */       } 
/*  631 */       this.containsNullKey = true;
/*  632 */       pos = this.n;
/*      */     } else {
/*      */       
/*  635 */       K[] key = this.key;
/*      */       K curr;
/*  637 */       if ((curr = key[pos = HashCommon.mix(this.strategy.hashCode(k)) & this.mask]) != null) {
/*  638 */         if (this.strategy.equals(curr, k)) {
/*  639 */           moveIndexToFirst(pos);
/*  640 */           return setValue(pos, v);
/*      */         } 
/*  642 */         while ((curr = key[pos = pos + 1 & this.mask]) != null) { if (this.strategy.equals(curr, k)) {
/*  643 */             moveIndexToFirst(pos);
/*  644 */             return setValue(pos, v);
/*      */           }  }
/*      */       
/*      */       } 
/*  648 */     }  this.key[pos] = k;
/*  649 */     this.value[pos] = v;
/*  650 */     if (this.size == 0) {
/*  651 */       this.first = this.last = pos;
/*      */       
/*  653 */       this.link[pos] = -1L;
/*      */     } else {
/*  655 */       this.link[this.first] = this.link[this.first] ^ (this.link[this.first] ^ (pos & 0xFFFFFFFFL) << 32L) & 0xFFFFFFFF00000000L;
/*  656 */       this.link[pos] = 0xFFFFFFFF00000000L | this.first & 0xFFFFFFFFL;
/*  657 */       this.first = pos;
/*      */     } 
/*  659 */     if (this.size++ >= this.maxFill) rehash(HashCommon.arraySize(this.size, this.f));
/*      */     
/*  661 */     return this.defRetValue;
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
/*      */   public long putAndMoveToLast(K k, long v) {
/*      */     int pos;
/*  675 */     if (this.strategy.equals(k, null)) {
/*  676 */       if (this.containsNullKey) {
/*  677 */         moveIndexToLast(this.n);
/*  678 */         return setValue(this.n, v);
/*      */       } 
/*  680 */       this.containsNullKey = true;
/*  681 */       pos = this.n;
/*      */     } else {
/*      */       
/*  684 */       K[] key = this.key;
/*      */       K curr;
/*  686 */       if ((curr = key[pos = HashCommon.mix(this.strategy.hashCode(k)) & this.mask]) != null) {
/*  687 */         if (this.strategy.equals(curr, k)) {
/*  688 */           moveIndexToLast(pos);
/*  689 */           return setValue(pos, v);
/*      */         } 
/*  691 */         while ((curr = key[pos = pos + 1 & this.mask]) != null) { if (this.strategy.equals(curr, k)) {
/*  692 */             moveIndexToLast(pos);
/*  693 */             return setValue(pos, v);
/*      */           }  }
/*      */       
/*      */       } 
/*  697 */     }  this.key[pos] = k;
/*  698 */     this.value[pos] = v;
/*  699 */     if (this.size == 0) {
/*  700 */       this.first = this.last = pos;
/*      */       
/*  702 */       this.link[pos] = -1L;
/*      */     } else {
/*  704 */       this.link[this.last] = this.link[this.last] ^ (this.link[this.last] ^ pos & 0xFFFFFFFFL) & 0xFFFFFFFFL;
/*  705 */       this.link[pos] = (this.last & 0xFFFFFFFFL) << 32L | 0xFFFFFFFFL;
/*  706 */       this.last = pos;
/*      */     } 
/*  708 */     if (this.size++ >= this.maxFill) rehash(HashCommon.arraySize(this.size, this.f));
/*      */     
/*  710 */     return this.defRetValue;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public long getLong(Object k) {
/*  716 */     if (this.strategy.equals(k, null)) return this.containsNullKey ? this.value[this.n] : this.defRetValue;
/*      */     
/*  718 */     K[] key = this.key;
/*      */     K curr;
/*      */     int pos;
/*  721 */     if ((curr = key[pos = HashCommon.mix(this.strategy.hashCode(k)) & this.mask]) == null) return this.defRetValue; 
/*  722 */     if (this.strategy.equals(k, curr)) return this.value[pos];
/*      */     
/*      */     while (true) {
/*  725 */       if ((curr = key[pos = pos + 1 & this.mask]) == null) return this.defRetValue; 
/*  726 */       if (this.strategy.equals(k, curr)) return this.value[pos];
/*      */     
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   public boolean containsKey(Object k) {
/*  733 */     if (this.strategy.equals(k, null)) return this.containsNullKey;
/*      */     
/*  735 */     K[] key = this.key;
/*      */     K curr;
/*      */     int pos;
/*  738 */     if ((curr = key[pos = HashCommon.mix(this.strategy.hashCode(k)) & this.mask]) == null) return false; 
/*  739 */     if (this.strategy.equals(k, curr)) return true;
/*      */     
/*      */     while (true) {
/*  742 */       if ((curr = key[pos = pos + 1 & this.mask]) == null) return false; 
/*  743 */       if (this.strategy.equals(k, curr)) return true;
/*      */     
/*      */     } 
/*      */   }
/*      */   
/*      */   public boolean containsValue(long v) {
/*  749 */     long[] value = this.value;
/*  750 */     K[] key = this.key;
/*  751 */     if (this.containsNullKey && value[this.n] == v) return true; 
/*  752 */     for (int i = this.n; i-- != 0;) { if (key[i] != null && value[i] == v) return true;  }
/*  753 */      return false;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public long getOrDefault(Object k, long defaultValue) {
/*  760 */     if (this.strategy.equals(k, null)) return this.containsNullKey ? this.value[this.n] : defaultValue;
/*      */     
/*  762 */     K[] key = this.key;
/*      */     K curr;
/*      */     int pos;
/*  765 */     if ((curr = key[pos = HashCommon.mix(this.strategy.hashCode(k)) & this.mask]) == null) return defaultValue; 
/*  766 */     if (this.strategy.equals(k, curr)) return this.value[pos];
/*      */     
/*      */     while (true) {
/*  769 */       if ((curr = key[pos = pos + 1 & this.mask]) == null) return defaultValue; 
/*  770 */       if (this.strategy.equals(k, curr)) return this.value[pos];
/*      */     
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   public long putIfAbsent(K k, long v) {
/*  777 */     int pos = find(k);
/*  778 */     if (pos >= 0) return this.value[pos]; 
/*  779 */     insert(-pos - 1, k, v);
/*  780 */     return this.defRetValue;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean remove(Object k, long v) {
/*  787 */     if (this.strategy.equals(k, null)) {
/*  788 */       if (this.containsNullKey && v == this.value[this.n]) {
/*  789 */         removeNullEntry();
/*  790 */         return true;
/*      */       } 
/*  792 */       return false;
/*      */     } 
/*      */     
/*  795 */     K[] key = this.key;
/*      */     K curr;
/*      */     int pos;
/*  798 */     if ((curr = key[pos = HashCommon.mix(this.strategy.hashCode(k)) & this.mask]) == null) return false; 
/*  799 */     if (this.strategy.equals(k, curr) && v == this.value[pos]) {
/*  800 */       removeEntry(pos);
/*  801 */       return true;
/*      */     } 
/*      */     while (true) {
/*  804 */       if ((curr = key[pos = pos + 1 & this.mask]) == null) return false; 
/*  805 */       if (this.strategy.equals(k, curr) && v == this.value[pos]) {
/*  806 */         removeEntry(pos);
/*  807 */         return true;
/*      */       } 
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean replace(K k, long oldValue, long v) {
/*  815 */     int pos = find(k);
/*  816 */     if (pos < 0 || oldValue != this.value[pos]) return false; 
/*  817 */     this.value[pos] = v;
/*  818 */     return true;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public long replace(K k, long v) {
/*  824 */     int pos = find(k);
/*  825 */     if (pos < 0) return this.defRetValue; 
/*  826 */     long oldValue = this.value[pos];
/*  827 */     this.value[pos] = v;
/*  828 */     return oldValue;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public long computeIfAbsent(K k, ToLongFunction<? super K> mappingFunction) {
/*  834 */     Objects.requireNonNull(mappingFunction);
/*  835 */     int pos = find(k);
/*  836 */     if (pos >= 0) return this.value[pos]; 
/*  837 */     long newValue = mappingFunction.applyAsLong(k);
/*  838 */     insert(-pos - 1, k, newValue);
/*  839 */     return newValue;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public long computeIfAbsent(K key, Object2LongFunction<? super K> mappingFunction) {
/*  845 */     Objects.requireNonNull(mappingFunction);
/*  846 */     int pos = find(key);
/*  847 */     if (pos >= 0) return this.value[pos]; 
/*  848 */     if (!mappingFunction.containsKey(key)) return this.defRetValue; 
/*  849 */     long newValue = mappingFunction.getLong(key);
/*  850 */     insert(-pos - 1, key, newValue);
/*  851 */     return newValue;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public long computeLongIfPresent(K k, BiFunction<? super K, ? super Long, ? extends Long> remappingFunction) {
/*  857 */     Objects.requireNonNull(remappingFunction);
/*  858 */     int pos = find(k);
/*  859 */     if (pos < 0) return this.defRetValue; 
/*  860 */     Long newValue = remappingFunction.apply(k, Long.valueOf(this.value[pos]));
/*  861 */     if (newValue == null) {
/*  862 */       if (this.strategy.equals(k, null)) { removeNullEntry(); }
/*  863 */       else { removeEntry(pos); }
/*  864 */        return this.defRetValue;
/*      */     } 
/*  866 */     this.value[pos] = newValue.longValue(); return newValue.longValue();
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public long computeLong(K k, BiFunction<? super K, ? super Long, ? extends Long> remappingFunction) {
/*  872 */     Objects.requireNonNull(remappingFunction);
/*  873 */     int pos = find(k);
/*  874 */     Long newValue = remappingFunction.apply(k, (pos >= 0) ? Long.valueOf(this.value[pos]) : null);
/*  875 */     if (newValue == null) {
/*  876 */       if (pos >= 0)
/*  877 */         if (this.strategy.equals(k, null)) { removeNullEntry(); }
/*  878 */         else { removeEntry(pos); }
/*      */          
/*  880 */       return this.defRetValue;
/*      */     } 
/*  882 */     long newVal = newValue.longValue();
/*  883 */     if (pos < 0) {
/*  884 */       insert(-pos - 1, k, newVal);
/*  885 */       return newVal;
/*      */     } 
/*  887 */     this.value[pos] = newVal; return newVal;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public long merge(K k, long v, BiFunction<? super Long, ? super Long, ? extends Long> remappingFunction) {
/*  893 */     Objects.requireNonNull(remappingFunction);
/*      */     
/*  895 */     int pos = find(k);
/*  896 */     if (pos < 0) {
/*  897 */       if (pos < 0) { insert(-pos - 1, k, v); }
/*  898 */       else { this.value[pos] = v; }
/*  899 */        return v;
/*      */     } 
/*  901 */     Long newValue = remappingFunction.apply(Long.valueOf(this.value[pos]), Long.valueOf(v));
/*  902 */     if (newValue == null) {
/*  903 */       if (this.strategy.equals(k, null)) { removeNullEntry(); }
/*  904 */       else { removeEntry(pos); }
/*  905 */        return this.defRetValue;
/*      */     } 
/*  907 */     this.value[pos] = newValue.longValue(); return newValue.longValue();
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
/*  918 */     if (this.size == 0)
/*  919 */       return;  this.size = 0;
/*  920 */     this.containsNullKey = false;
/*  921 */     Arrays.fill((Object[])this.key, (Object)null);
/*  922 */     this.first = this.last = -1;
/*      */   }
/*      */ 
/*      */   
/*      */   public int size() {
/*  927 */     return this.size;
/*      */   }
/*      */ 
/*      */   
/*      */   public boolean isEmpty() {
/*  932 */     return (this.size == 0);
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   final class MapEntry
/*      */     implements Object2LongMap.Entry<K>, Map.Entry<K, Long>, ObjectLongPair<K>
/*      */   {
/*      */     int index;
/*      */ 
/*      */ 
/*      */     
/*      */     MapEntry(int index) {
/*  945 */       this.index = index;
/*      */     }
/*      */ 
/*      */     
/*      */     MapEntry() {}
/*      */ 
/*      */     
/*      */     public K getKey() {
/*  953 */       return Object2LongLinkedOpenCustomHashMap.this.key[this.index];
/*      */     }
/*      */ 
/*      */     
/*      */     public K left() {
/*  958 */       return Object2LongLinkedOpenCustomHashMap.this.key[this.index];
/*      */     }
/*      */ 
/*      */     
/*      */     public long getLongValue() {
/*  963 */       return Object2LongLinkedOpenCustomHashMap.this.value[this.index];
/*      */     }
/*      */ 
/*      */     
/*      */     public long rightLong() {
/*  968 */       return Object2LongLinkedOpenCustomHashMap.this.value[this.index];
/*      */     }
/*      */ 
/*      */     
/*      */     public long setValue(long v) {
/*  973 */       long oldValue = Object2LongLinkedOpenCustomHashMap.this.value[this.index];
/*  974 */       Object2LongLinkedOpenCustomHashMap.this.value[this.index] = v;
/*  975 */       return oldValue;
/*      */     }
/*      */ 
/*      */     
/*      */     public ObjectLongPair<K> right(long v) {
/*  980 */       Object2LongLinkedOpenCustomHashMap.this.value[this.index] = v;
/*  981 */       return this;
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
/*  992 */       return Long.valueOf(Object2LongLinkedOpenCustomHashMap.this.value[this.index]);
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
/* 1003 */       return Long.valueOf(setValue(v.longValue()));
/*      */     }
/*      */ 
/*      */ 
/*      */     
/*      */     public boolean equals(Object o) {
/* 1009 */       if (!(o instanceof Map.Entry)) return false; 
/* 1010 */       Map.Entry<K, Long> e = (Map.Entry<K, Long>)o;
/* 1011 */       return (Object2LongLinkedOpenCustomHashMap.this.strategy.equals(Object2LongLinkedOpenCustomHashMap.this.key[this.index], e.getKey()) && Object2LongLinkedOpenCustomHashMap.this.value[this.index] == ((Long)e.getValue()).longValue());
/*      */     }
/*      */ 
/*      */     
/*      */     public int hashCode() {
/* 1016 */       return Object2LongLinkedOpenCustomHashMap.this.strategy.hashCode(Object2LongLinkedOpenCustomHashMap.this.key[this.index]) ^ HashCommon.long2int(Object2LongLinkedOpenCustomHashMap.this.value[this.index]);
/*      */     }
/*      */ 
/*      */     
/*      */     public String toString() {
/* 1021 */       return (new StringBuilder()).append(Object2LongLinkedOpenCustomHashMap.this.key[this.index]).append("=>").append(Object2LongLinkedOpenCustomHashMap.this.value[this.index]).toString();
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
/* 1032 */     if (this.size == 0) {
/* 1033 */       this.first = this.last = -1;
/*      */       return;
/*      */     } 
/* 1036 */     if (this.first == i) {
/* 1037 */       this.first = (int)this.link[i];
/* 1038 */       if (0 <= this.first)
/*      */       {
/* 1040 */         this.link[this.first] = this.link[this.first] | 0xFFFFFFFF00000000L;
/*      */       }
/*      */       return;
/*      */     } 
/* 1044 */     if (this.last == i) {
/* 1045 */       this.last = (int)(this.link[i] >>> 32L);
/* 1046 */       if (0 <= this.last)
/*      */       {
/* 1048 */         this.link[this.last] = this.link[this.last] | 0xFFFFFFFFL;
/*      */       }
/*      */       return;
/*      */     } 
/* 1052 */     long linki = this.link[i];
/* 1053 */     int prev = (int)(linki >>> 32L);
/* 1054 */     int next = (int)linki;
/* 1055 */     this.link[prev] = this.link[prev] ^ (this.link[prev] ^ linki & 0xFFFFFFFFL) & 0xFFFFFFFFL;
/* 1056 */     this.link[next] = this.link[next] ^ (this.link[next] ^ linki & 0xFFFFFFFF00000000L) & 0xFFFFFFFF00000000L;
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
/* 1068 */     if (this.size == 1) {
/* 1069 */       this.first = this.last = d;
/*      */       
/* 1071 */       this.link[d] = -1L;
/*      */       return;
/*      */     } 
/* 1074 */     if (this.first == s) {
/* 1075 */       this.first = d;
/* 1076 */       this.link[(int)this.link[s]] = this.link[(int)this.link[s]] ^ (this.link[(int)this.link[s]] ^ (d & 0xFFFFFFFFL) << 32L) & 0xFFFFFFFF00000000L;
/* 1077 */       this.link[d] = this.link[s];
/*      */       return;
/*      */     } 
/* 1080 */     if (this.last == s) {
/* 1081 */       this.last = d;
/* 1082 */       this.link[(int)(this.link[s] >>> 32L)] = this.link[(int)(this.link[s] >>> 32L)] ^ (this.link[(int)(this.link[s] >>> 32L)] ^ d & 0xFFFFFFFFL) & 0xFFFFFFFFL;
/* 1083 */       this.link[d] = this.link[s];
/*      */       return;
/*      */     } 
/* 1086 */     long links = this.link[s];
/* 1087 */     int prev = (int)(links >>> 32L);
/* 1088 */     int next = (int)links;
/* 1089 */     this.link[prev] = this.link[prev] ^ (this.link[prev] ^ d & 0xFFFFFFFFL) & 0xFFFFFFFFL;
/* 1090 */     this.link[next] = this.link[next] ^ (this.link[next] ^ (d & 0xFFFFFFFFL) << 32L) & 0xFFFFFFFF00000000L;
/* 1091 */     this.link[d] = links;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public K firstKey() {
/* 1101 */     if (this.size == 0) throw new NoSuchElementException(); 
/* 1102 */     return this.key[this.first];
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public K lastKey() {
/* 1112 */     if (this.size == 0) throw new NoSuchElementException(); 
/* 1113 */     return this.key[this.last];
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Object2LongSortedMap<K> tailMap(K from) {
/* 1123 */     throw new UnsupportedOperationException();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Object2LongSortedMap<K> headMap(K to) {
/* 1133 */     throw new UnsupportedOperationException();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Object2LongSortedMap<K> subMap(K from, K to) {
/* 1143 */     throw new UnsupportedOperationException();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Comparator<? super K> comparator() {
/* 1153 */     return null;
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
/* 1168 */     int prev = -1;
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 1173 */     int next = -1;
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 1178 */     int curr = -1;
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 1183 */     int index = -1;
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     protected MapIterator() {
/* 1189 */       this.next = Object2LongLinkedOpenCustomHashMap.this.first;
/* 1190 */       this.index = 0;
/*      */     }
/*      */     
/*      */     private MapIterator(K from) {
/* 1194 */       if (Object2LongLinkedOpenCustomHashMap.this.strategy.equals(from, null)) {
/* 1195 */         if (Object2LongLinkedOpenCustomHashMap.this.containsNullKey) {
/* 1196 */           this.next = (int)Object2LongLinkedOpenCustomHashMap.this.link[Object2LongLinkedOpenCustomHashMap.this.n];
/* 1197 */           this.prev = Object2LongLinkedOpenCustomHashMap.this.n; return;
/*      */         } 
/* 1199 */         throw new NoSuchElementException("The key " + from + " does not belong to this map.");
/*      */       } 
/* 1201 */       if (Object2LongLinkedOpenCustomHashMap.this.strategy.equals(Object2LongLinkedOpenCustomHashMap.this.key[Object2LongLinkedOpenCustomHashMap.this.last], from)) {
/* 1202 */         this.prev = Object2LongLinkedOpenCustomHashMap.this.last;
/* 1203 */         this.index = Object2LongLinkedOpenCustomHashMap.this.size;
/*      */         
/*      */         return;
/*      */       } 
/* 1207 */       int pos = HashCommon.mix(Object2LongLinkedOpenCustomHashMap.this.strategy.hashCode(from)) & Object2LongLinkedOpenCustomHashMap.this.mask;
/*      */       
/* 1209 */       while (Object2LongLinkedOpenCustomHashMap.this.key[pos] != null) {
/* 1210 */         if (Object2LongLinkedOpenCustomHashMap.this.strategy.equals(Object2LongLinkedOpenCustomHashMap.this.key[pos], from)) {
/*      */           
/* 1212 */           this.next = (int)Object2LongLinkedOpenCustomHashMap.this.link[pos];
/* 1213 */           this.prev = pos;
/*      */           return;
/*      */         } 
/* 1216 */         pos = pos + 1 & Object2LongLinkedOpenCustomHashMap.this.mask;
/*      */       } 
/* 1218 */       throw new NoSuchElementException("The key " + from + " does not belong to this map.");
/*      */     }
/*      */     
/*      */     public boolean hasNext() {
/* 1222 */       return (this.next != -1);
/*      */     }
/*      */     
/*      */     public boolean hasPrevious() {
/* 1226 */       return (this.prev != -1);
/*      */     }
/*      */     
/*      */     private final void ensureIndexKnown() {
/* 1230 */       if (this.index >= 0)
/* 1231 */         return;  if (this.prev == -1) {
/* 1232 */         this.index = 0;
/*      */         return;
/*      */       } 
/* 1235 */       if (this.next == -1) {
/* 1236 */         this.index = Object2LongLinkedOpenCustomHashMap.this.size;
/*      */         return;
/*      */       } 
/* 1239 */       int pos = Object2LongLinkedOpenCustomHashMap.this.first;
/* 1240 */       this.index = 1;
/* 1241 */       while (pos != this.prev) {
/* 1242 */         pos = (int)Object2LongLinkedOpenCustomHashMap.this.link[pos];
/* 1243 */         this.index++;
/*      */       } 
/*      */     }
/*      */     
/*      */     public int nextIndex() {
/* 1248 */       ensureIndexKnown();
/* 1249 */       return this.index;
/*      */     }
/*      */     
/*      */     public int previousIndex() {
/* 1253 */       ensureIndexKnown();
/* 1254 */       return this.index - 1;
/*      */     }
/*      */     
/*      */     public int nextEntry() {
/* 1258 */       if (!hasNext()) throw new NoSuchElementException(); 
/* 1259 */       this.curr = this.next;
/* 1260 */       this.next = (int)Object2LongLinkedOpenCustomHashMap.this.link[this.curr];
/* 1261 */       this.prev = this.curr;
/* 1262 */       if (this.index >= 0) this.index++; 
/* 1263 */       return this.curr;
/*      */     }
/*      */     
/*      */     public int previousEntry() {
/* 1267 */       if (!hasPrevious()) throw new NoSuchElementException(); 
/* 1268 */       this.curr = this.prev;
/* 1269 */       this.prev = (int)(Object2LongLinkedOpenCustomHashMap.this.link[this.curr] >>> 32L);
/* 1270 */       this.next = this.curr;
/* 1271 */       if (this.index >= 0) this.index--; 
/* 1272 */       return this.curr;
/*      */     }
/*      */     
/*      */     public void forEachRemaining(ConsumerType action) {
/* 1276 */       while (hasNext()) {
/* 1277 */         this.curr = this.next;
/* 1278 */         this.next = (int)Object2LongLinkedOpenCustomHashMap.this.link[this.curr];
/* 1279 */         this.prev = this.curr;
/* 1280 */         if (this.index >= 0) this.index++; 
/* 1281 */         acceptOnIndex(action, this.curr);
/*      */       } 
/*      */     }
/*      */     
/*      */     public void remove() {
/* 1286 */       ensureIndexKnown();
/* 1287 */       if (this.curr == -1) throw new IllegalStateException(); 
/* 1288 */       if (this.curr == this.prev)
/*      */       
/*      */       { 
/* 1291 */         this.index--;
/* 1292 */         this.prev = (int)(Object2LongLinkedOpenCustomHashMap.this.link[this.curr] >>> 32L); }
/* 1293 */       else { this.next = (int)Object2LongLinkedOpenCustomHashMap.this.link[this.curr]; }
/* 1294 */        Object2LongLinkedOpenCustomHashMap.this.size--;
/*      */ 
/*      */       
/* 1297 */       if (this.prev == -1) { Object2LongLinkedOpenCustomHashMap.this.first = this.next; }
/* 1298 */       else { Object2LongLinkedOpenCustomHashMap.this.link[this.prev] = Object2LongLinkedOpenCustomHashMap.this.link[this.prev] ^ (Object2LongLinkedOpenCustomHashMap.this.link[this.prev] ^ this.next & 0xFFFFFFFFL) & 0xFFFFFFFFL; }
/* 1299 */        if (this.next == -1) { Object2LongLinkedOpenCustomHashMap.this.last = this.prev; }
/* 1300 */       else { Object2LongLinkedOpenCustomHashMap.this.link[this.next] = Object2LongLinkedOpenCustomHashMap.this.link[this.next] ^ (Object2LongLinkedOpenCustomHashMap.this.link[this.next] ^ (this.prev & 0xFFFFFFFFL) << 32L) & 0xFFFFFFFF00000000L; }
/* 1301 */        int pos = this.curr;
/* 1302 */       this.curr = -1;
/* 1303 */       if (pos == Object2LongLinkedOpenCustomHashMap.this.n) {
/* 1304 */         Object2LongLinkedOpenCustomHashMap.this.containsNullKey = false;
/* 1305 */         Object2LongLinkedOpenCustomHashMap.this.key[Object2LongLinkedOpenCustomHashMap.this.n] = null;
/*      */       } else {
/*      */         
/* 1308 */         K[] key = Object2LongLinkedOpenCustomHashMap.this.key; while (true) {
/*      */           K curr;
/*      */           int last;
/* 1311 */           pos = (last = pos) + 1 & Object2LongLinkedOpenCustomHashMap.this.mask;
/*      */           while (true) {
/* 1313 */             if ((curr = key[pos]) == null) {
/* 1314 */               key[last] = null;
/*      */               return;
/*      */             } 
/* 1317 */             int slot = HashCommon.mix(Object2LongLinkedOpenCustomHashMap.this.strategy.hashCode(curr)) & Object2LongLinkedOpenCustomHashMap.this.mask;
/* 1318 */             if ((last <= pos) ? (last >= slot || slot > pos) : (last >= slot && slot > pos))
/* 1319 */               break;  pos = pos + 1 & Object2LongLinkedOpenCustomHashMap.this.mask;
/*      */           } 
/* 1321 */           key[last] = curr;
/* 1322 */           Object2LongLinkedOpenCustomHashMap.this.value[last] = Object2LongLinkedOpenCustomHashMap.this.value[pos];
/* 1323 */           if (this.next == pos) this.next = last; 
/* 1324 */           if (this.prev == pos) this.prev = last; 
/* 1325 */           Object2LongLinkedOpenCustomHashMap.this.fixPointers(pos, last);
/*      */         } 
/*      */       } 
/*      */     }
/*      */     
/*      */     public int skip(int n) {
/* 1331 */       int i = n;
/* 1332 */       for (; i-- != 0 && hasNext(); nextEntry());
/* 1333 */       return n - i - 1;
/*      */     }
/*      */     
/*      */     public int back(int n) {
/* 1337 */       int i = n;
/* 1338 */       for (; i-- != 0 && hasPrevious(); previousEntry());
/* 1339 */       return n - i - 1;
/*      */     }
/*      */     
/*      */     public void set(Object2LongMap.Entry<K> ok) {
/* 1343 */       throw new UnsupportedOperationException();
/*      */     }
/*      */     
/*      */     public void add(Object2LongMap.Entry<K> ok) {
/* 1347 */       throw new UnsupportedOperationException();
/*      */     }
/*      */     
/*      */     abstract void acceptOnIndex(ConsumerType param1ConsumerType, int param1Int); }
/*      */   
/*      */   private final class EntryIterator extends MapIterator<Consumer<? super Object2LongMap.Entry<K>>> implements ObjectListIterator<Object2LongMap.Entry<K>> {
/*      */     private Object2LongLinkedOpenCustomHashMap<K>.MapEntry entry;
/*      */     
/*      */     public EntryIterator() {}
/*      */     
/*      */     public EntryIterator(K from) {
/* 1358 */       super(from);
/*      */     }
/*      */ 
/*      */ 
/*      */     
/*      */     final void acceptOnIndex(Consumer<? super Object2LongMap.Entry<K>> action, int index) {
/* 1364 */       action.accept(new Object2LongLinkedOpenCustomHashMap.MapEntry(index));
/*      */     }
/*      */ 
/*      */     
/*      */     public Object2LongLinkedOpenCustomHashMap<K>.MapEntry next() {
/* 1369 */       return this.entry = new Object2LongLinkedOpenCustomHashMap.MapEntry(nextEntry());
/*      */     }
/*      */ 
/*      */     
/*      */     public Object2LongLinkedOpenCustomHashMap<K>.MapEntry previous() {
/* 1374 */       return this.entry = new Object2LongLinkedOpenCustomHashMap.MapEntry(previousEntry());
/*      */     }
/*      */ 
/*      */     
/*      */     public void remove() {
/* 1379 */       super.remove();
/* 1380 */       this.entry.index = -1;
/*      */     }
/*      */   }
/*      */   
/*      */   private final class FastEntryIterator extends MapIterator<Consumer<? super Object2LongMap.Entry<K>>> implements ObjectListIterator<Object2LongMap.Entry<K>> {
/* 1385 */     final Object2LongLinkedOpenCustomHashMap<K>.MapEntry entry = new Object2LongLinkedOpenCustomHashMap.MapEntry();
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public FastEntryIterator(K from) {
/* 1391 */       super(from);
/*      */     }
/*      */ 
/*      */ 
/*      */     
/*      */     final void acceptOnIndex(Consumer<? super Object2LongMap.Entry<K>> action, int index) {
/* 1397 */       this.entry.index = index;
/* 1398 */       action.accept(this.entry);
/*      */     }
/*      */ 
/*      */     
/*      */     public Object2LongLinkedOpenCustomHashMap<K>.MapEntry next() {
/* 1403 */       this.entry.index = nextEntry();
/* 1404 */       return this.entry;
/*      */     }
/*      */ 
/*      */     
/*      */     public Object2LongLinkedOpenCustomHashMap<K>.MapEntry previous() {
/* 1409 */       this.entry.index = previousEntry();
/* 1410 */       return this.entry;
/*      */     }
/*      */     
/*      */     public FastEntryIterator() {} }
/*      */   
/*      */   private final class MapEntrySet extends AbstractObjectSortedSet<Object2LongMap.Entry<K>> implements Object2LongSortedMap.FastSortedEntrySet<K> {
/*      */     private static final int SPLITERATOR_CHARACTERISTICS = 81;
/*      */     
/*      */     public ObjectBidirectionalIterator<Object2LongMap.Entry<K>> iterator() {
/* 1419 */       return new Object2LongLinkedOpenCustomHashMap.EntryIterator();
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
/*      */     public ObjectSpliterator<Object2LongMap.Entry<K>> spliterator() {
/* 1437 */       return ObjectSpliterators.asSpliterator(iterator(), Size64.sizeOf(Object2LongLinkedOpenCustomHashMap.this), 81);
/*      */     }
/*      */ 
/*      */     
/*      */     public Comparator<? super Object2LongMap.Entry<K>> comparator() {
/* 1442 */       return null;
/*      */     }
/*      */ 
/*      */     
/*      */     public ObjectSortedSet<Object2LongMap.Entry<K>> subSet(Object2LongMap.Entry<K> fromElement, Object2LongMap.Entry<K> toElement) {
/* 1447 */       throw new UnsupportedOperationException();
/*      */     }
/*      */ 
/*      */     
/*      */     public ObjectSortedSet<Object2LongMap.Entry<K>> headSet(Object2LongMap.Entry<K> toElement) {
/* 1452 */       throw new UnsupportedOperationException();
/*      */     }
/*      */ 
/*      */     
/*      */     public ObjectSortedSet<Object2LongMap.Entry<K>> tailSet(Object2LongMap.Entry<K> fromElement) {
/* 1457 */       throw new UnsupportedOperationException();
/*      */     }
/*      */ 
/*      */     
/*      */     public Object2LongMap.Entry<K> first() {
/* 1462 */       if (Object2LongLinkedOpenCustomHashMap.this.size == 0) throw new NoSuchElementException(); 
/* 1463 */       return new Object2LongLinkedOpenCustomHashMap.MapEntry(Object2LongLinkedOpenCustomHashMap.this.first);
/*      */     }
/*      */ 
/*      */     
/*      */     public Object2LongMap.Entry<K> last() {
/* 1468 */       if (Object2LongLinkedOpenCustomHashMap.this.size == 0) throw new NoSuchElementException(); 
/* 1469 */       return new Object2LongLinkedOpenCustomHashMap.MapEntry(Object2LongLinkedOpenCustomHashMap.this.last);
/*      */     }
/*      */ 
/*      */ 
/*      */     
/*      */     public boolean contains(Object o) {
/* 1475 */       if (!(o instanceof Map.Entry)) return false; 
/* 1476 */       Map.Entry<?, ?> e = (Map.Entry<?, ?>)o;
/* 1477 */       if (e.getValue() == null || !(e.getValue() instanceof Long)) return false; 
/* 1478 */       K k = (K)e.getKey();
/* 1479 */       long v = ((Long)e.getValue()).longValue();
/* 1480 */       if (Object2LongLinkedOpenCustomHashMap.this.strategy.equals(k, null)) return (Object2LongLinkedOpenCustomHashMap.this.containsNullKey && Object2LongLinkedOpenCustomHashMap.this.value[Object2LongLinkedOpenCustomHashMap.this.n] == v);
/*      */       
/* 1482 */       K[] key = Object2LongLinkedOpenCustomHashMap.this.key;
/*      */       K curr;
/*      */       int pos;
/* 1485 */       if ((curr = key[pos = HashCommon.mix(Object2LongLinkedOpenCustomHashMap.this.strategy.hashCode(k)) & Object2LongLinkedOpenCustomHashMap.this.mask]) == null) return false; 
/* 1486 */       if (Object2LongLinkedOpenCustomHashMap.this.strategy.equals(k, curr)) return (Object2LongLinkedOpenCustomHashMap.this.value[pos] == v);
/*      */       
/*      */       while (true) {
/* 1489 */         if ((curr = key[pos = pos + 1 & Object2LongLinkedOpenCustomHashMap.this.mask]) == null) return false; 
/* 1490 */         if (Object2LongLinkedOpenCustomHashMap.this.strategy.equals(k, curr)) return (Object2LongLinkedOpenCustomHashMap.this.value[pos] == v);
/*      */       
/*      */       } 
/*      */     }
/*      */ 
/*      */     
/*      */     public boolean remove(Object o) {
/* 1497 */       if (!(o instanceof Map.Entry)) return false; 
/* 1498 */       Map.Entry<?, ?> e = (Map.Entry<?, ?>)o;
/* 1499 */       if (e.getValue() == null || !(e.getValue() instanceof Long)) return false; 
/* 1500 */       K k = (K)e.getKey();
/* 1501 */       long v = ((Long)e.getValue()).longValue();
/* 1502 */       if (Object2LongLinkedOpenCustomHashMap.this.strategy.equals(k, null)) {
/* 1503 */         if (Object2LongLinkedOpenCustomHashMap.this.containsNullKey && Object2LongLinkedOpenCustomHashMap.this.value[Object2LongLinkedOpenCustomHashMap.this.n] == v) {
/* 1504 */           Object2LongLinkedOpenCustomHashMap.this.removeNullEntry();
/* 1505 */           return true;
/*      */         } 
/* 1507 */         return false;
/*      */       } 
/*      */       
/* 1510 */       K[] key = Object2LongLinkedOpenCustomHashMap.this.key;
/*      */       K curr;
/*      */       int pos;
/* 1513 */       if ((curr = key[pos = HashCommon.mix(Object2LongLinkedOpenCustomHashMap.this.strategy.hashCode(k)) & Object2LongLinkedOpenCustomHashMap.this.mask]) == null) return false; 
/* 1514 */       if (Object2LongLinkedOpenCustomHashMap.this.strategy.equals(curr, k)) {
/* 1515 */         if (Object2LongLinkedOpenCustomHashMap.this.value[pos] == v) {
/* 1516 */           Object2LongLinkedOpenCustomHashMap.this.removeEntry(pos);
/* 1517 */           return true;
/*      */         } 
/* 1519 */         return false;
/*      */       } 
/*      */       while (true) {
/* 1522 */         if ((curr = key[pos = pos + 1 & Object2LongLinkedOpenCustomHashMap.this.mask]) == null) return false; 
/* 1523 */         if (Object2LongLinkedOpenCustomHashMap.this.strategy.equals(curr, k) && 
/* 1524 */           Object2LongLinkedOpenCustomHashMap.this.value[pos] == v) {
/* 1525 */           Object2LongLinkedOpenCustomHashMap.this.removeEntry(pos);
/* 1526 */           return true;
/*      */         } 
/*      */       } 
/*      */     }
/*      */ 
/*      */ 
/*      */     
/*      */     public int size() {
/* 1534 */       return Object2LongLinkedOpenCustomHashMap.this.size;
/*      */     }
/*      */ 
/*      */     
/*      */     public void clear() {
/* 1539 */       Object2LongLinkedOpenCustomHashMap.this.clear();
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
/*      */     public ObjectListIterator<Object2LongMap.Entry<K>> iterator(Object2LongMap.Entry<K> from) {
/* 1552 */       return new Object2LongLinkedOpenCustomHashMap.EntryIterator(from.getKey());
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public ObjectListIterator<Object2LongMap.Entry<K>> fastIterator() {
/* 1563 */       return new Object2LongLinkedOpenCustomHashMap.FastEntryIterator();
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
/*      */     public ObjectListIterator<Object2LongMap.Entry<K>> fastIterator(Object2LongMap.Entry<K> from) {
/* 1576 */       return new Object2LongLinkedOpenCustomHashMap.FastEntryIterator(from.getKey());
/*      */     }
/*      */ 
/*      */ 
/*      */     
/*      */     public void forEach(Consumer<? super Object2LongMap.Entry<K>> consumer) {
/* 1582 */       for (int i = Object2LongLinkedOpenCustomHashMap.this.size, next = Object2LongLinkedOpenCustomHashMap.this.first; i-- != 0; ) {
/* 1583 */         int curr = next;
/* 1584 */         next = (int)Object2LongLinkedOpenCustomHashMap.this.link[curr];
/* 1585 */         consumer.accept(new Object2LongLinkedOpenCustomHashMap.MapEntry(curr));
/*      */       } 
/*      */     }
/*      */ 
/*      */ 
/*      */     
/*      */     public void fastForEach(Consumer<? super Object2LongMap.Entry<K>> consumer) {
/* 1592 */       Object2LongLinkedOpenCustomHashMap<K>.MapEntry entry = new Object2LongLinkedOpenCustomHashMap.MapEntry();
/* 1593 */       for (int i = Object2LongLinkedOpenCustomHashMap.this.size, next = Object2LongLinkedOpenCustomHashMap.this.first; i-- != 0; ) {
/* 1594 */         entry.index = next;
/* 1595 */         next = (int)Object2LongLinkedOpenCustomHashMap.this.link[next];
/* 1596 */         consumer.accept(entry);
/*      */       } 
/*      */     }
/*      */   }
/*      */ 
/*      */   
/*      */   public Object2LongSortedMap.FastSortedEntrySet<K> object2LongEntrySet() {
/* 1603 */     if (this.entries == null) this.entries = new MapEntrySet(); 
/* 1604 */     return this.entries;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private final class KeyIterator
/*      */     extends MapIterator<Consumer<? super K>>
/*      */     implements ObjectListIterator<K>
/*      */   {
/*      */     public KeyIterator(K k) {
/* 1617 */       super(k);
/*      */     }
/*      */ 
/*      */     
/*      */     public K previous() {
/* 1622 */       return Object2LongLinkedOpenCustomHashMap.this.key[previousEntry()];
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
/*      */     final void acceptOnIndex(Consumer<? super K> action, int index) {
/* 1634 */       action.accept(Object2LongLinkedOpenCustomHashMap.this.key[index]);
/*      */     }
/*      */ 
/*      */     
/*      */     public K next() {
/* 1639 */       return Object2LongLinkedOpenCustomHashMap.this.key[nextEntry()];
/*      */     } }
/*      */   
/*      */   private final class KeySet extends AbstractObjectSortedSet<K> {
/*      */     private static final int SPLITERATOR_CHARACTERISTICS = 81;
/*      */     
/*      */     private KeySet() {}
/*      */     
/*      */     public ObjectListIterator<K> iterator(K from) {
/* 1648 */       return new Object2LongLinkedOpenCustomHashMap.KeyIterator(from);
/*      */     }
/*      */ 
/*      */     
/*      */     public ObjectListIterator<K> iterator() {
/* 1653 */       return new Object2LongLinkedOpenCustomHashMap.KeyIterator();
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public ObjectSpliterator<K> spliterator() {
/* 1663 */       return ObjectSpliterators.asSpliterator(iterator(), Size64.sizeOf(Object2LongLinkedOpenCustomHashMap.this), 81);
/*      */     }
/*      */ 
/*      */ 
/*      */     
/*      */     public void forEach(Consumer<? super K> consumer) {
/* 1669 */       for (int i = Object2LongLinkedOpenCustomHashMap.this.size, next = Object2LongLinkedOpenCustomHashMap.this.first; i-- != 0; ) {
/* 1670 */         int curr = next;
/* 1671 */         next = (int)Object2LongLinkedOpenCustomHashMap.this.link[curr];
/* 1672 */         consumer.accept(Object2LongLinkedOpenCustomHashMap.this.key[curr]);
/*      */       } 
/*      */     }
/*      */ 
/*      */     
/*      */     public int size() {
/* 1678 */       return Object2LongLinkedOpenCustomHashMap.this.size;
/*      */     }
/*      */ 
/*      */     
/*      */     public boolean contains(Object k) {
/* 1683 */       return Object2LongLinkedOpenCustomHashMap.this.containsKey(k);
/*      */     }
/*      */ 
/*      */     
/*      */     public boolean remove(Object k) {
/* 1688 */       int oldSize = Object2LongLinkedOpenCustomHashMap.this.size;
/* 1689 */       Object2LongLinkedOpenCustomHashMap.this.removeLong(k);
/* 1690 */       return (Object2LongLinkedOpenCustomHashMap.this.size != oldSize);
/*      */     }
/*      */ 
/*      */     
/*      */     public void clear() {
/* 1695 */       Object2LongLinkedOpenCustomHashMap.this.clear();
/*      */     }
/*      */ 
/*      */     
/*      */     public K first() {
/* 1700 */       if (Object2LongLinkedOpenCustomHashMap.this.size == 0) throw new NoSuchElementException(); 
/* 1701 */       return Object2LongLinkedOpenCustomHashMap.this.key[Object2LongLinkedOpenCustomHashMap.this.first];
/*      */     }
/*      */ 
/*      */     
/*      */     public K last() {
/* 1706 */       if (Object2LongLinkedOpenCustomHashMap.this.size == 0) throw new NoSuchElementException(); 
/* 1707 */       return Object2LongLinkedOpenCustomHashMap.this.key[Object2LongLinkedOpenCustomHashMap.this.last];
/*      */     }
/*      */ 
/*      */     
/*      */     public Comparator<? super K> comparator() {
/* 1712 */       return null;
/*      */     }
/*      */ 
/*      */     
/*      */     public ObjectSortedSet<K> tailSet(K from) {
/* 1717 */       throw new UnsupportedOperationException();
/*      */     }
/*      */ 
/*      */     
/*      */     public ObjectSortedSet<K> headSet(K to) {
/* 1722 */       throw new UnsupportedOperationException();
/*      */     }
/*      */ 
/*      */     
/*      */     public ObjectSortedSet<K> subSet(K from, K to) {
/* 1727 */       throw new UnsupportedOperationException();
/*      */     }
/*      */   }
/*      */ 
/*      */   
/*      */   public ObjectSortedSet<K> keySet() {
/* 1733 */     if (this.keys == null) this.keys = new KeySet(); 
/* 1734 */     return this.keys;
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
/* 1748 */       return Object2LongLinkedOpenCustomHashMap.this.value[previousEntry()];
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
/* 1760 */       action.accept(Object2LongLinkedOpenCustomHashMap.this.value[index]);
/*      */     }
/*      */ 
/*      */     
/*      */     public long nextLong() {
/* 1765 */       return Object2LongLinkedOpenCustomHashMap.this.value[nextEntry()];
/*      */     }
/*      */   }
/*      */ 
/*      */   
/*      */   public LongCollection values() {
/* 1771 */     if (this.values == null) this.values = (LongCollection)new AbstractLongCollection()
/*      */         {
/*      */           private static final int SPLITERATOR_CHARACTERISTICS = 336;
/*      */           
/*      */           public LongIterator iterator() {
/* 1776 */             return (LongIterator)new Object2LongLinkedOpenCustomHashMap.ValueIterator();
/*      */           }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */           
/*      */           public LongSpliterator spliterator() {
/* 1786 */             return LongSpliterators.asSpliterator(iterator(), Size64.sizeOf(Object2LongLinkedOpenCustomHashMap.this), 336);
/*      */           }
/*      */ 
/*      */ 
/*      */           
/*      */           public void forEach(LongConsumer consumer) {
/* 1792 */             for (int i = Object2LongLinkedOpenCustomHashMap.this.size, next = Object2LongLinkedOpenCustomHashMap.this.first; i-- != 0; ) {
/* 1793 */               int curr = next;
/* 1794 */               next = (int)Object2LongLinkedOpenCustomHashMap.this.link[curr];
/* 1795 */               consumer.accept(Object2LongLinkedOpenCustomHashMap.this.value[curr]);
/*      */             } 
/*      */           }
/*      */ 
/*      */           
/*      */           public int size() {
/* 1801 */             return Object2LongLinkedOpenCustomHashMap.this.size;
/*      */           }
/*      */ 
/*      */           
/*      */           public boolean contains(long v) {
/* 1806 */             return Object2LongLinkedOpenCustomHashMap.this.containsValue(v);
/*      */           }
/*      */ 
/*      */           
/*      */           public void clear() {
/* 1811 */             Object2LongLinkedOpenCustomHashMap.this.clear();
/*      */           }
/*      */         }; 
/* 1814 */     return this.values;
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
/* 1831 */     return trim(this.size);
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
/* 1853 */     int l = HashCommon.nextPowerOfTwo((int)Math.ceil((n / this.f)));
/* 1854 */     if (l >= this.n || this.size > HashCommon.maxFill(l, this.f)) return true; 
/*      */     try {
/* 1856 */       rehash(l);
/* 1857 */     } catch (OutOfMemoryError cantDoIt) {
/* 1858 */       return false;
/*      */     } 
/* 1860 */     return true;
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
/* 1875 */     K[] key = this.key;
/* 1876 */     long[] value = this.value;
/* 1877 */     int mask = newN - 1;
/* 1878 */     K[] newKey = (K[])new Object[newN + 1];
/* 1879 */     long[] newValue = new long[newN + 1];
/* 1880 */     int i = this.first, prev = -1, newPrev = -1;
/* 1881 */     long[] link = this.link;
/* 1882 */     long[] newLink = new long[newN + 1];
/* 1883 */     this.first = -1;
/* 1884 */     for (int j = this.size; j-- != 0; ) {
/* 1885 */       int pos; if (this.strategy.equals(key[i], null)) { pos = newN; }
/*      */       else
/* 1887 */       { pos = HashCommon.mix(this.strategy.hashCode(key[i])) & mask;
/* 1888 */         for (; newKey[pos] != null; pos = pos + 1 & mask); }
/*      */       
/* 1890 */       newKey[pos] = key[i];
/* 1891 */       newValue[pos] = value[i];
/* 1892 */       if (prev != -1) {
/* 1893 */         newLink[newPrev] = newLink[newPrev] ^ (newLink[newPrev] ^ pos & 0xFFFFFFFFL) & 0xFFFFFFFFL;
/* 1894 */         newLink[pos] = newLink[pos] ^ (newLink[pos] ^ (newPrev & 0xFFFFFFFFL) << 32L) & 0xFFFFFFFF00000000L;
/* 1895 */         newPrev = pos;
/*      */       } else {
/* 1897 */         newPrev = this.first = pos;
/*      */         
/* 1899 */         newLink[pos] = -1L;
/*      */       } 
/* 1901 */       int t = i;
/* 1902 */       i = (int)link[i];
/* 1903 */       prev = t;
/*      */     } 
/* 1905 */     this.link = newLink;
/* 1906 */     this.last = newPrev;
/* 1907 */     if (newPrev != -1)
/*      */     {
/* 1909 */       newLink[newPrev] = newLink[newPrev] | 0xFFFFFFFFL; } 
/* 1910 */     this.n = newN;
/* 1911 */     this.mask = mask;
/* 1912 */     this.maxFill = HashCommon.maxFill(this.n, this.f);
/* 1913 */     this.key = newKey;
/* 1914 */     this.value = newValue;
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
/*      */   public Object2LongLinkedOpenCustomHashMap<K> clone() {
/*      */     Object2LongLinkedOpenCustomHashMap<K> c;
/*      */     try {
/* 1931 */       c = (Object2LongLinkedOpenCustomHashMap<K>)super.clone();
/* 1932 */     } catch (CloneNotSupportedException cantHappen) {
/* 1933 */       throw new InternalError();
/*      */     } 
/* 1935 */     c.keys = null;
/* 1936 */     c.values = null;
/* 1937 */     c.entries = null;
/* 1938 */     c.containsNullKey = this.containsNullKey;
/* 1939 */     c.key = (K[])this.key.clone();
/* 1940 */     c.value = (long[])this.value.clone();
/* 1941 */     c.link = (long[])this.link.clone();
/* 1942 */     c.strategy = this.strategy;
/* 1943 */     return c;
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
/* 1957 */     int h = 0;
/* 1958 */     for (int j = realSize(), i = 0, t = 0; j-- != 0; ) {
/* 1959 */       for (; this.key[i] == null; i++);
/* 1960 */       if (this != this.key[i]) t = this.strategy.hashCode(this.key[i]); 
/* 1961 */       t ^= HashCommon.long2int(this.value[i]);
/* 1962 */       h += t;
/* 1963 */       i++;
/*      */     } 
/*      */     
/* 1966 */     if (this.containsNullKey) h += HashCommon.long2int(this.value[this.n]); 
/* 1967 */     return h;
/*      */   }
/*      */   
/*      */   private void writeObject(ObjectOutputStream s) throws IOException {
/* 1971 */     K[] key = this.key;
/* 1972 */     long[] value = this.value;
/* 1973 */     EntryIterator i = new EntryIterator();
/* 1974 */     s.defaultWriteObject();
/* 1975 */     for (int j = this.size; j-- != 0; ) {
/* 1976 */       int e = i.nextEntry();
/* 1977 */       s.writeObject(key[e]);
/* 1978 */       s.writeLong(value[e]);
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   private void readObject(ObjectInputStream s) throws IOException, ClassNotFoundException {
/* 1984 */     s.defaultReadObject();
/* 1985 */     this.n = HashCommon.arraySize(this.size, this.f);
/* 1986 */     this.maxFill = HashCommon.maxFill(this.n, this.f);
/* 1987 */     this.mask = this.n - 1;
/* 1988 */     K[] key = this.key = (K[])new Object[this.n + 1];
/* 1989 */     long[] value = this.value = new long[this.n + 1];
/* 1990 */     long[] link = this.link = new long[this.n + 1];
/* 1991 */     int prev = -1;
/* 1992 */     this.first = this.last = -1;
/*      */ 
/*      */     
/* 1995 */     for (int i = this.size; i-- != 0; ) {
/* 1996 */       int pos; K k = (K)s.readObject();
/* 1997 */       long v = s.readLong();
/* 1998 */       if (this.strategy.equals(k, null)) {
/* 1999 */         pos = this.n;
/* 2000 */         this.containsNullKey = true;
/*      */       } else {
/* 2002 */         pos = HashCommon.mix(this.strategy.hashCode(k)) & this.mask;
/* 2003 */         for (; key[pos] != null; pos = pos + 1 & this.mask);
/*      */       } 
/* 2005 */       key[pos] = k;
/* 2006 */       value[pos] = v;
/* 2007 */       if (this.first != -1) {
/* 2008 */         link[prev] = link[prev] ^ (link[prev] ^ pos & 0xFFFFFFFFL) & 0xFFFFFFFFL;
/* 2009 */         link[pos] = link[pos] ^ (link[pos] ^ (prev & 0xFFFFFFFFL) << 32L) & 0xFFFFFFFF00000000L;
/* 2010 */         prev = pos; continue;
/*      */       } 
/* 2012 */       prev = this.first = pos;
/*      */       
/* 2014 */       link[pos] = link[pos] | 0xFFFFFFFF00000000L;
/*      */     } 
/*      */     
/* 2017 */     this.last = prev;
/* 2018 */     if (prev != -1)
/*      */     {
/* 2020 */       link[prev] = link[prev] | 0xFFFFFFFFL;
/*      */     }
/*      */   }
/*      */   
/*      */   private void checkTable() {}
/*      */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\i\\unimi\dsi\fastutil\objects\Object2LongLinkedOpenCustomHashMap.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */