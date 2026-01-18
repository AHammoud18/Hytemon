/*      */ package it.unimi.dsi.fastutil.longs;
/*      */ 
/*      */ import it.unimi.dsi.fastutil.Hash;
/*      */ import it.unimi.dsi.fastutil.HashCommon;
/*      */ import it.unimi.dsi.fastutil.Pair;
/*      */ import it.unimi.dsi.fastutil.Size64;
/*      */ import it.unimi.dsi.fastutil.objects.AbstractObjectCollection;
/*      */ import it.unimi.dsi.fastutil.objects.AbstractObjectSortedSet;
/*      */ import it.unimi.dsi.fastutil.objects.ObjectBidirectionalIterator;
/*      */ import it.unimi.dsi.fastutil.objects.ObjectCollection;
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
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ public class Long2ObjectLinkedOpenHashMap<V>
/*      */   extends AbstractLong2ObjectSortedMap<V>
/*      */   implements Serializable, Cloneable, Hash
/*      */ {
/*      */   private static final long serialVersionUID = 0L;
/*      */   private static final boolean ASSERTS = false;
/*      */   protected transient long[] key;
/*      */   protected transient V[] value;
/*      */   protected transient int mask;
/*      */   protected transient boolean containsNullKey;
/*  104 */   protected transient int first = -1;
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*  109 */   protected transient int last = -1;
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
/*      */   protected transient Long2ObjectSortedMap.FastSortedEntrySet<V> entries;
/*      */ 
/*      */   
/*      */   protected transient LongSortedSet keys;
/*      */ 
/*      */   
/*      */   protected transient ObjectCollection<V> values;
/*      */ 
/*      */ 
/*      */   
/*      */   public Long2ObjectLinkedOpenHashMap(int expected, float f) {
/*  144 */     if (f <= 0.0F || f >= 1.0F) throw new IllegalArgumentException("Load factor must be greater than 0 and smaller than 1"); 
/*  145 */     if (expected < 0) throw new IllegalArgumentException("The expected number of elements must be nonnegative"); 
/*  146 */     this.f = f;
/*  147 */     this.minN = this.n = HashCommon.arraySize(expected, f);
/*  148 */     this.mask = this.n - 1;
/*  149 */     this.maxFill = HashCommon.maxFill(this.n, f);
/*  150 */     this.key = new long[this.n + 1];
/*  151 */     this.value = (V[])new Object[this.n + 1];
/*  152 */     this.link = new long[this.n + 1];
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Long2ObjectLinkedOpenHashMap(int expected) {
/*  161 */     this(expected, 0.75F);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Long2ObjectLinkedOpenHashMap() {
/*  169 */     this(16, 0.75F);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Long2ObjectLinkedOpenHashMap(Map<? extends Long, ? extends V> m, float f) {
/*  179 */     this(m.size(), f);
/*  180 */     putAll(m);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Long2ObjectLinkedOpenHashMap(Map<? extends Long, ? extends V> m) {
/*  189 */     this(m, 0.75F);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Long2ObjectLinkedOpenHashMap(Long2ObjectMap<V> m, float f) {
/*  199 */     this(m.size(), f);
/*  200 */     putAll(m);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Long2ObjectLinkedOpenHashMap(Long2ObjectMap<V> m) {
/*  210 */     this(m, 0.75F);
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
/*      */   public Long2ObjectLinkedOpenHashMap(long[] k, V[] v, float f) {
/*  222 */     this(k.length, f);
/*  223 */     if (k.length != v.length) throw new IllegalArgumentException("The key array and the value array have different lengths (" + k.length + " and " + v.length + ")"); 
/*  224 */     for (int i = 0; i < k.length; ) { put(k[i], v[i]); i++; }
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
/*      */   public Long2ObjectLinkedOpenHashMap(long[] k, V[] v) {
/*  236 */     this(k, v, 0.75F);
/*      */   }
/*      */   
/*      */   private int realSize() {
/*  240 */     return this.containsNullKey ? (this.size - 1) : this.size;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void ensureCapacity(int capacity) {
/*  250 */     int needed = HashCommon.arraySize(capacity, this.f);
/*  251 */     if (needed > this.n) rehash(needed); 
/*      */   }
/*      */   
/*      */   private void tryCapacity(long capacity) {
/*  255 */     int needed = (int)Math.min(1073741824L, Math.max(2L, HashCommon.nextPowerOfTwo((long)Math.ceil(((float)capacity / this.f)))));
/*  256 */     if (needed > this.n) rehash(needed); 
/*      */   }
/*      */   
/*      */   private V removeEntry(int pos) {
/*  260 */     V oldValue = this.value[pos];
/*  261 */     this.value[pos] = null;
/*  262 */     this.size--;
/*  263 */     fixPointers(pos);
/*  264 */     shiftKeys(pos);
/*  265 */     if (this.n > this.minN && this.size < this.maxFill / 4 && this.n > 16) rehash(this.n / 2); 
/*  266 */     return oldValue;
/*      */   }
/*      */   
/*      */   private V removeNullEntry() {
/*  270 */     this.containsNullKey = false;
/*  271 */     V oldValue = this.value[this.n];
/*  272 */     this.value[this.n] = null;
/*  273 */     this.size--;
/*  274 */     fixPointers(this.n);
/*  275 */     if (this.n > this.minN && this.size < this.maxFill / 4 && this.n > 16) rehash(this.n / 2); 
/*  276 */     return oldValue;
/*      */   }
/*      */ 
/*      */   
/*      */   public void putAll(Map<? extends Long, ? extends V> m) {
/*  281 */     if (this.f <= 0.5D) { ensureCapacity(m.size()); }
/*  282 */     else { tryCapacity((size() + m.size())); }
/*      */     
/*  284 */     super.putAll(m);
/*      */   }
/*      */   
/*      */   private int find(long k) {
/*  288 */     if (k == 0L) return this.containsNullKey ? this.n : -(this.n + 1);
/*      */     
/*  290 */     long[] key = this.key;
/*      */     long curr;
/*      */     int pos;
/*  293 */     if ((curr = key[pos = (int)HashCommon.mix(k) & this.mask]) == 0L) return -(pos + 1); 
/*  294 */     if (k == curr) return pos;
/*      */     
/*      */     while (true) {
/*  297 */       if ((curr = key[pos = pos + 1 & this.mask]) == 0L) return -(pos + 1); 
/*  298 */       if (k == curr) return pos; 
/*      */     } 
/*      */   }
/*      */   
/*      */   private void insert(int pos, long k, V v) {
/*  303 */     if (pos == this.n) this.containsNullKey = true; 
/*  304 */     this.key[pos] = k;
/*  305 */     this.value[pos] = v;
/*  306 */     if (this.size == 0) {
/*  307 */       this.first = this.last = pos;
/*      */       
/*  309 */       this.link[pos] = -1L;
/*      */     } else {
/*  311 */       this.link[this.last] = this.link[this.last] ^ (this.link[this.last] ^ pos & 0xFFFFFFFFL) & 0xFFFFFFFFL;
/*  312 */       this.link[pos] = (this.last & 0xFFFFFFFFL) << 32L | 0xFFFFFFFFL;
/*  313 */       this.last = pos;
/*      */     } 
/*  315 */     if (this.size++ >= this.maxFill) rehash(HashCommon.arraySize(this.size + 1, this.f));
/*      */   
/*      */   }
/*      */ 
/*      */   
/*      */   public V put(long k, V v) {
/*  321 */     int pos = find(k);
/*  322 */     if (pos < 0) {
/*  323 */       insert(-pos - 1, k, v);
/*  324 */       return this.defRetValue;
/*      */     } 
/*  326 */     V oldValue = this.value[pos];
/*  327 */     this.value[pos] = v;
/*  328 */     return oldValue;
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
/*  341 */     long[] key = this.key; while (true) {
/*      */       long curr; int last;
/*  343 */       pos = (last = pos) + 1 & this.mask;
/*      */       while (true) {
/*  345 */         if ((curr = key[pos]) == 0L) {
/*  346 */           key[last] = 0L;
/*  347 */           this.value[last] = null;
/*      */           return;
/*      */         } 
/*  350 */         int slot = (int)HashCommon.mix(curr) & this.mask;
/*  351 */         if ((last <= pos) ? (last >= slot || slot > pos) : (last >= slot && slot > pos))
/*  352 */           break;  pos = pos + 1 & this.mask;
/*      */       } 
/*  354 */       key[last] = curr;
/*  355 */       this.value[last] = this.value[pos];
/*  356 */       fixPointers(pos, last);
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public V remove(long k) {
/*  363 */     if (k == 0L) {
/*  364 */       if (this.containsNullKey) return removeNullEntry(); 
/*  365 */       return this.defRetValue;
/*      */     } 
/*      */     
/*  368 */     long[] key = this.key;
/*      */     long curr;
/*      */     int pos;
/*  371 */     if ((curr = key[pos = (int)HashCommon.mix(k) & this.mask]) == 0L) return this.defRetValue; 
/*  372 */     if (k == curr) return removeEntry(pos); 
/*      */     while (true) {
/*  374 */       if ((curr = key[pos = pos + 1 & this.mask]) == 0L) return this.defRetValue; 
/*  375 */       if (k == curr) return removeEntry(pos); 
/*      */     } 
/*      */   }
/*      */   
/*      */   private V setValue(int pos, V v) {
/*  380 */     V oldValue = this.value[pos];
/*  381 */     this.value[pos] = v;
/*  382 */     return oldValue;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public V removeFirst() {
/*  392 */     if (this.size == 0) throw new NoSuchElementException(); 
/*  393 */     int pos = this.first;
/*      */     
/*  395 */     if (this.size == 1) { this.first = this.last = -1; }
/*      */     else
/*  397 */     { this.first = (int)this.link[pos];
/*  398 */       if (0 <= this.first)
/*      */       {
/*  400 */         this.link[this.first] = this.link[this.first] | 0xFFFFFFFF00000000L;
/*      */       } }
/*      */     
/*  403 */     this.size--;
/*  404 */     V v = this.value[pos];
/*  405 */     if (pos == this.n)
/*  406 */     { this.containsNullKey = false;
/*  407 */       this.value[this.n] = null; }
/*  408 */     else { shiftKeys(pos); }
/*  409 */      if (this.n > this.minN && this.size < this.maxFill / 4 && this.n > 16) rehash(this.n / 2); 
/*  410 */     return v;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public V removeLast() {
/*  420 */     if (this.size == 0) throw new NoSuchElementException(); 
/*  421 */     int pos = this.last;
/*      */     
/*  423 */     if (this.size == 1) { this.first = this.last = -1; }
/*      */     else
/*  425 */     { this.last = (int)(this.link[pos] >>> 32L);
/*  426 */       if (0 <= this.last)
/*      */       {
/*  428 */         this.link[this.last] = this.link[this.last] | 0xFFFFFFFFL;
/*      */       } }
/*      */     
/*  431 */     this.size--;
/*  432 */     V v = this.value[pos];
/*  433 */     if (pos == this.n)
/*  434 */     { this.containsNullKey = false;
/*  435 */       this.value[this.n] = null; }
/*  436 */     else { shiftKeys(pos); }
/*  437 */      if (this.n > this.minN && this.size < this.maxFill / 4 && this.n > 16) rehash(this.n / 2); 
/*  438 */     return v;
/*      */   }
/*      */   
/*      */   private void moveIndexToFirst(int i) {
/*  442 */     if (this.size == 1 || this.first == i)
/*  443 */       return;  if (this.last == i) {
/*  444 */       this.last = (int)(this.link[i] >>> 32L);
/*      */       
/*  446 */       this.link[this.last] = this.link[this.last] | 0xFFFFFFFFL;
/*      */     } else {
/*  448 */       long linki = this.link[i];
/*  449 */       int prev = (int)(linki >>> 32L);
/*  450 */       int next = (int)linki;
/*  451 */       this.link[prev] = this.link[prev] ^ (this.link[prev] ^ linki & 0xFFFFFFFFL) & 0xFFFFFFFFL;
/*  452 */       this.link[next] = this.link[next] ^ (this.link[next] ^ linki & 0xFFFFFFFF00000000L) & 0xFFFFFFFF00000000L;
/*      */     } 
/*  454 */     this.link[this.first] = this.link[this.first] ^ (this.link[this.first] ^ (i & 0xFFFFFFFFL) << 32L) & 0xFFFFFFFF00000000L;
/*  455 */     this.link[i] = 0xFFFFFFFF00000000L | this.first & 0xFFFFFFFFL;
/*  456 */     this.first = i;
/*      */   }
/*      */   
/*      */   private void moveIndexToLast(int i) {
/*  460 */     if (this.size == 1 || this.last == i)
/*  461 */       return;  if (this.first == i) {
/*  462 */       this.first = (int)this.link[i];
/*      */       
/*  464 */       this.link[this.first] = this.link[this.first] | 0xFFFFFFFF00000000L;
/*      */     } else {
/*  466 */       long linki = this.link[i];
/*  467 */       int prev = (int)(linki >>> 32L);
/*  468 */       int next = (int)linki;
/*  469 */       this.link[prev] = this.link[prev] ^ (this.link[prev] ^ linki & 0xFFFFFFFFL) & 0xFFFFFFFFL;
/*  470 */       this.link[next] = this.link[next] ^ (this.link[next] ^ linki & 0xFFFFFFFF00000000L) & 0xFFFFFFFF00000000L;
/*      */     } 
/*  472 */     this.link[this.last] = this.link[this.last] ^ (this.link[this.last] ^ i & 0xFFFFFFFFL) & 0xFFFFFFFFL;
/*  473 */     this.link[i] = (this.last & 0xFFFFFFFFL) << 32L | 0xFFFFFFFFL;
/*  474 */     this.last = i;
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
/*      */   public V getAndMoveToFirst(long k) {
/*  486 */     if (k == 0L) {
/*  487 */       if (this.containsNullKey) {
/*  488 */         moveIndexToFirst(this.n);
/*  489 */         return this.value[this.n];
/*      */       } 
/*  491 */       return this.defRetValue;
/*      */     } 
/*      */     
/*  494 */     long[] key = this.key;
/*      */     long curr;
/*      */     int pos;
/*  497 */     if ((curr = key[pos = (int)HashCommon.mix(k) & this.mask]) == 0L) return this.defRetValue; 
/*  498 */     if (k == curr) {
/*  499 */       moveIndexToFirst(pos);
/*  500 */       return this.value[pos];
/*      */     } 
/*      */     
/*      */     while (true) {
/*  504 */       if ((curr = key[pos = pos + 1 & this.mask]) == 0L) return this.defRetValue; 
/*  505 */       if (k == curr) {
/*  506 */         moveIndexToFirst(pos);
/*  507 */         return this.value[pos];
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
/*      */   public V getAndMoveToLast(long k) {
/*  521 */     if (k == 0L) {
/*  522 */       if (this.containsNullKey) {
/*  523 */         moveIndexToLast(this.n);
/*  524 */         return this.value[this.n];
/*      */       } 
/*  526 */       return this.defRetValue;
/*      */     } 
/*      */     
/*  529 */     long[] key = this.key;
/*      */     long curr;
/*      */     int pos;
/*  532 */     if ((curr = key[pos = (int)HashCommon.mix(k) & this.mask]) == 0L) return this.defRetValue; 
/*  533 */     if (k == curr) {
/*  534 */       moveIndexToLast(pos);
/*  535 */       return this.value[pos];
/*      */     } 
/*      */     
/*      */     while (true) {
/*  539 */       if ((curr = key[pos = pos + 1 & this.mask]) == 0L) return this.defRetValue; 
/*  540 */       if (k == curr) {
/*  541 */         moveIndexToLast(pos);
/*  542 */         return this.value[pos];
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
/*      */   public V putAndMoveToFirst(long k, V v) {
/*      */     int pos;
/*  558 */     if (k == 0L) {
/*  559 */       if (this.containsNullKey) {
/*  560 */         moveIndexToFirst(this.n);
/*  561 */         return setValue(this.n, v);
/*      */       } 
/*  563 */       this.containsNullKey = true;
/*  564 */       pos = this.n;
/*      */     } else {
/*      */       
/*  567 */       long[] key = this.key;
/*      */       long curr;
/*  569 */       if ((curr = key[pos = (int)HashCommon.mix(k) & this.mask]) != 0L) {
/*  570 */         if (curr == k) {
/*  571 */           moveIndexToFirst(pos);
/*  572 */           return setValue(pos, v);
/*      */         } 
/*  574 */         while ((curr = key[pos = pos + 1 & this.mask]) != 0L) { if (curr == k) {
/*  575 */             moveIndexToFirst(pos);
/*  576 */             return setValue(pos, v);
/*      */           }  }
/*      */       
/*      */       } 
/*  580 */     }  this.key[pos] = k;
/*  581 */     this.value[pos] = v;
/*  582 */     if (this.size == 0) {
/*  583 */       this.first = this.last = pos;
/*      */       
/*  585 */       this.link[pos] = -1L;
/*      */     } else {
/*  587 */       this.link[this.first] = this.link[this.first] ^ (this.link[this.first] ^ (pos & 0xFFFFFFFFL) << 32L) & 0xFFFFFFFF00000000L;
/*  588 */       this.link[pos] = 0xFFFFFFFF00000000L | this.first & 0xFFFFFFFFL;
/*  589 */       this.first = pos;
/*      */     } 
/*  591 */     if (this.size++ >= this.maxFill) rehash(HashCommon.arraySize(this.size, this.f));
/*      */     
/*  593 */     return this.defRetValue;
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
/*      */   public V putAndMoveToLast(long k, V v) {
/*      */     int pos;
/*  607 */     if (k == 0L) {
/*  608 */       if (this.containsNullKey) {
/*  609 */         moveIndexToLast(this.n);
/*  610 */         return setValue(this.n, v);
/*      */       } 
/*  612 */       this.containsNullKey = true;
/*  613 */       pos = this.n;
/*      */     } else {
/*      */       
/*  616 */       long[] key = this.key;
/*      */       long curr;
/*  618 */       if ((curr = key[pos = (int)HashCommon.mix(k) & this.mask]) != 0L) {
/*  619 */         if (curr == k) {
/*  620 */           moveIndexToLast(pos);
/*  621 */           return setValue(pos, v);
/*      */         } 
/*  623 */         while ((curr = key[pos = pos + 1 & this.mask]) != 0L) { if (curr == k) {
/*  624 */             moveIndexToLast(pos);
/*  625 */             return setValue(pos, v);
/*      */           }  }
/*      */       
/*      */       } 
/*  629 */     }  this.key[pos] = k;
/*  630 */     this.value[pos] = v;
/*  631 */     if (this.size == 0) {
/*  632 */       this.first = this.last = pos;
/*      */       
/*  634 */       this.link[pos] = -1L;
/*      */     } else {
/*  636 */       this.link[this.last] = this.link[this.last] ^ (this.link[this.last] ^ pos & 0xFFFFFFFFL) & 0xFFFFFFFFL;
/*  637 */       this.link[pos] = (this.last & 0xFFFFFFFFL) << 32L | 0xFFFFFFFFL;
/*  638 */       this.last = pos;
/*      */     } 
/*  640 */     if (this.size++ >= this.maxFill) rehash(HashCommon.arraySize(this.size, this.f));
/*      */     
/*  642 */     return this.defRetValue;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public V get(long k) {
/*  648 */     if (k == 0L) return this.containsNullKey ? this.value[this.n] : this.defRetValue;
/*      */     
/*  650 */     long[] key = this.key;
/*      */     long curr;
/*      */     int pos;
/*  653 */     if ((curr = key[pos = (int)HashCommon.mix(k) & this.mask]) == 0L) return this.defRetValue; 
/*  654 */     if (k == curr) return this.value[pos];
/*      */     
/*      */     while (true) {
/*  657 */       if ((curr = key[pos = pos + 1 & this.mask]) == 0L) return this.defRetValue; 
/*  658 */       if (k == curr) return this.value[pos];
/*      */     
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   public boolean containsKey(long k) {
/*  665 */     if (k == 0L) return this.containsNullKey;
/*      */     
/*  667 */     long[] key = this.key;
/*      */     long curr;
/*      */     int pos;
/*  670 */     if ((curr = key[pos = (int)HashCommon.mix(k) & this.mask]) == 0L) return false; 
/*  671 */     if (k == curr) return true;
/*      */     
/*      */     while (true) {
/*  674 */       if ((curr = key[pos = pos + 1 & this.mask]) == 0L) return false; 
/*  675 */       if (k == curr) return true;
/*      */     
/*      */     } 
/*      */   }
/*      */   
/*      */   public boolean containsValue(Object v) {
/*  681 */     V[] value = this.value;
/*  682 */     long[] key = this.key;
/*  683 */     if (this.containsNullKey && Objects.equals(value[this.n], v)) return true; 
/*  684 */     for (int i = this.n; i-- != 0;) { if (key[i] != 0L && Objects.equals(value[i], v)) return true;  }
/*  685 */      return false;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public V getOrDefault(long k, V defaultValue) {
/*  692 */     if (k == 0L) return this.containsNullKey ? this.value[this.n] : defaultValue;
/*      */     
/*  694 */     long[] key = this.key;
/*      */     long curr;
/*      */     int pos;
/*  697 */     if ((curr = key[pos = (int)HashCommon.mix(k) & this.mask]) == 0L) return defaultValue; 
/*  698 */     if (k == curr) return this.value[pos];
/*      */     
/*      */     while (true) {
/*  701 */       if ((curr = key[pos = pos + 1 & this.mask]) == 0L) return defaultValue; 
/*  702 */       if (k == curr) return this.value[pos];
/*      */     
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   public V putIfAbsent(long k, V v) {
/*  709 */     int pos = find(k);
/*  710 */     if (pos >= 0) return this.value[pos]; 
/*  711 */     insert(-pos - 1, k, v);
/*  712 */     return this.defRetValue;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean remove(long k, Object v) {
/*  719 */     if (k == 0L) {
/*  720 */       if (this.containsNullKey && Objects.equals(v, this.value[this.n])) {
/*  721 */         removeNullEntry();
/*  722 */         return true;
/*      */       } 
/*  724 */       return false;
/*      */     } 
/*      */     
/*  727 */     long[] key = this.key;
/*      */     long curr;
/*      */     int pos;
/*  730 */     if ((curr = key[pos = (int)HashCommon.mix(k) & this.mask]) == 0L) return false; 
/*  731 */     if (k == curr && Objects.equals(v, this.value[pos])) {
/*  732 */       removeEntry(pos);
/*  733 */       return true;
/*      */     } 
/*      */     while (true) {
/*  736 */       if ((curr = key[pos = pos + 1 & this.mask]) == 0L) return false; 
/*  737 */       if (k == curr && Objects.equals(v, this.value[pos])) {
/*  738 */         removeEntry(pos);
/*  739 */         return true;
/*      */       } 
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean replace(long k, V oldValue, V v) {
/*  747 */     int pos = find(k);
/*  748 */     if (pos < 0 || !Objects.equals(oldValue, this.value[pos])) return false; 
/*  749 */     this.value[pos] = v;
/*  750 */     return true;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public V replace(long k, V v) {
/*  756 */     int pos = find(k);
/*  757 */     if (pos < 0) return this.defRetValue; 
/*  758 */     V oldValue = this.value[pos];
/*  759 */     this.value[pos] = v;
/*  760 */     return oldValue;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public V computeIfAbsent(long k, LongFunction<? extends V> mappingFunction) {
/*  766 */     Objects.requireNonNull(mappingFunction);
/*  767 */     int pos = find(k);
/*  768 */     if (pos >= 0) return this.value[pos]; 
/*  769 */     V newValue = mappingFunction.apply(k);
/*  770 */     insert(-pos - 1, k, newValue);
/*  771 */     return newValue;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public V computeIfAbsent(long key, Long2ObjectFunction<? extends V> mappingFunction) {
/*  777 */     Objects.requireNonNull(mappingFunction);
/*  778 */     int pos = find(key);
/*  779 */     if (pos >= 0) return this.value[pos]; 
/*  780 */     if (!mappingFunction.containsKey(key)) return this.defRetValue; 
/*  781 */     V newValue = mappingFunction.get(key);
/*  782 */     insert(-pos - 1, key, newValue);
/*  783 */     return newValue;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public V computeIfPresent(long k, BiFunction<? super Long, ? super V, ? extends V> remappingFunction) {
/*  789 */     Objects.requireNonNull(remappingFunction);
/*  790 */     int pos = find(k);
/*  791 */     if (pos < 0) return this.defRetValue; 
/*  792 */     if (this.value[pos] == null) return this.defRetValue; 
/*  793 */     V newValue = remappingFunction.apply(Long.valueOf(k), this.value[pos]);
/*  794 */     if (newValue == null) {
/*  795 */       if (k == 0L) { removeNullEntry(); }
/*  796 */       else { removeEntry(pos); }
/*  797 */        return this.defRetValue;
/*      */     } 
/*  799 */     this.value[pos] = newValue; return newValue;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public V compute(long k, BiFunction<? super Long, ? super V, ? extends V> remappingFunction) {
/*  805 */     Objects.requireNonNull(remappingFunction);
/*  806 */     int pos = find(k);
/*  807 */     V newValue = remappingFunction.apply(Long.valueOf(k), (pos >= 0) ? this.value[pos] : null);
/*  808 */     if (newValue == null) {
/*  809 */       if (pos >= 0)
/*  810 */         if (k == 0L) { removeNullEntry(); }
/*  811 */         else { removeEntry(pos); }
/*      */          
/*  813 */       return this.defRetValue;
/*      */     } 
/*  815 */     V newVal = newValue;
/*  816 */     if (pos < 0) {
/*  817 */       insert(-pos - 1, k, newVal);
/*  818 */       return newVal;
/*      */     } 
/*  820 */     this.value[pos] = newVal; return newVal;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public V merge(long k, V v, BiFunction<? super V, ? super V, ? extends V> remappingFunction) {
/*  826 */     Objects.requireNonNull(remappingFunction);
/*  827 */     Objects.requireNonNull(v);
/*  828 */     int pos = find(k);
/*  829 */     if (pos < 0 || this.value[pos] == null) {
/*  830 */       if (pos < 0) { insert(-pos - 1, k, v); }
/*  831 */       else { this.value[pos] = v; }
/*  832 */        return v;
/*      */     } 
/*  834 */     V newValue = remappingFunction.apply(this.value[pos], v);
/*  835 */     if (newValue == null) {
/*  836 */       if (k == 0L) { removeNullEntry(); }
/*  837 */       else { removeEntry(pos); }
/*  838 */        return this.defRetValue;
/*      */     } 
/*  840 */     this.value[pos] = newValue; return newValue;
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
/*  851 */     if (this.size == 0)
/*  852 */       return;  this.size = 0;
/*  853 */     this.containsNullKey = false;
/*  854 */     Arrays.fill(this.key, 0L);
/*  855 */     Arrays.fill((Object[])this.value, (Object)null);
/*  856 */     this.first = this.last = -1;
/*      */   }
/*      */ 
/*      */   
/*      */   public int size() {
/*  861 */     return this.size;
/*      */   }
/*      */ 
/*      */   
/*      */   public boolean isEmpty() {
/*  866 */     return (this.size == 0);
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   final class MapEntry
/*      */     implements Long2ObjectMap.Entry<V>, Map.Entry<Long, V>, LongObjectPair<V>
/*      */   {
/*      */     int index;
/*      */ 
/*      */ 
/*      */     
/*      */     MapEntry(int index) {
/*  879 */       this.index = index;
/*      */     }
/*      */ 
/*      */     
/*      */     MapEntry() {}
/*      */ 
/*      */     
/*      */     public long getLongKey() {
/*  887 */       return Long2ObjectLinkedOpenHashMap.this.key[this.index];
/*      */     }
/*      */ 
/*      */     
/*      */     public long leftLong() {
/*  892 */       return Long2ObjectLinkedOpenHashMap.this.key[this.index];
/*      */     }
/*      */ 
/*      */     
/*      */     public V getValue() {
/*  897 */       return Long2ObjectLinkedOpenHashMap.this.value[this.index];
/*      */     }
/*      */ 
/*      */     
/*      */     public V right() {
/*  902 */       return Long2ObjectLinkedOpenHashMap.this.value[this.index];
/*      */     }
/*      */ 
/*      */     
/*      */     public V setValue(V v) {
/*  907 */       V oldValue = Long2ObjectLinkedOpenHashMap.this.value[this.index];
/*  908 */       Long2ObjectLinkedOpenHashMap.this.value[this.index] = v;
/*  909 */       return oldValue;
/*      */     }
/*      */ 
/*      */     
/*      */     public LongObjectPair<V> right(V v) {
/*  914 */       Long2ObjectLinkedOpenHashMap.this.value[this.index] = v;
/*  915 */       return this;
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
/*  926 */       return Long.valueOf(Long2ObjectLinkedOpenHashMap.this.key[this.index]);
/*      */     }
/*      */ 
/*      */ 
/*      */     
/*      */     public boolean equals(Object o) {
/*  932 */       if (!(o instanceof Map.Entry)) return false; 
/*  933 */       Map.Entry<Long, V> e = (Map.Entry<Long, V>)o;
/*  934 */       return (Long2ObjectLinkedOpenHashMap.this.key[this.index] == ((Long)e.getKey()).longValue() && Objects.equals(Long2ObjectLinkedOpenHashMap.this.value[this.index], e.getValue()));
/*      */     }
/*      */ 
/*      */     
/*      */     public int hashCode() {
/*  939 */       return HashCommon.long2int(Long2ObjectLinkedOpenHashMap.this.key[this.index]) ^ ((Long2ObjectLinkedOpenHashMap.this.value[this.index] == null) ? 0 : Long2ObjectLinkedOpenHashMap.this.value[this.index].hashCode());
/*      */     }
/*      */ 
/*      */     
/*      */     public String toString() {
/*  944 */       return Long2ObjectLinkedOpenHashMap.this.key[this.index] + "=>" + Long2ObjectLinkedOpenHashMap.this.value[this.index];
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
/*  955 */     if (this.size == 0) {
/*  956 */       this.first = this.last = -1;
/*      */       return;
/*      */     } 
/*  959 */     if (this.first == i) {
/*  960 */       this.first = (int)this.link[i];
/*  961 */       if (0 <= this.first)
/*      */       {
/*  963 */         this.link[this.first] = this.link[this.first] | 0xFFFFFFFF00000000L;
/*      */       }
/*      */       return;
/*      */     } 
/*  967 */     if (this.last == i) {
/*  968 */       this.last = (int)(this.link[i] >>> 32L);
/*  969 */       if (0 <= this.last)
/*      */       {
/*  971 */         this.link[this.last] = this.link[this.last] | 0xFFFFFFFFL;
/*      */       }
/*      */       return;
/*      */     } 
/*  975 */     long linki = this.link[i];
/*  976 */     int prev = (int)(linki >>> 32L);
/*  977 */     int next = (int)linki;
/*  978 */     this.link[prev] = this.link[prev] ^ (this.link[prev] ^ linki & 0xFFFFFFFFL) & 0xFFFFFFFFL;
/*  979 */     this.link[next] = this.link[next] ^ (this.link[next] ^ linki & 0xFFFFFFFF00000000L) & 0xFFFFFFFF00000000L;
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
/*  991 */     if (this.size == 1) {
/*  992 */       this.first = this.last = d;
/*      */       
/*  994 */       this.link[d] = -1L;
/*      */       return;
/*      */     } 
/*  997 */     if (this.first == s) {
/*  998 */       this.first = d;
/*  999 */       this.link[(int)this.link[s]] = this.link[(int)this.link[s]] ^ (this.link[(int)this.link[s]] ^ (d & 0xFFFFFFFFL) << 32L) & 0xFFFFFFFF00000000L;
/* 1000 */       this.link[d] = this.link[s];
/*      */       return;
/*      */     } 
/* 1003 */     if (this.last == s) {
/* 1004 */       this.last = d;
/* 1005 */       this.link[(int)(this.link[s] >>> 32L)] = this.link[(int)(this.link[s] >>> 32L)] ^ (this.link[(int)(this.link[s] >>> 32L)] ^ d & 0xFFFFFFFFL) & 0xFFFFFFFFL;
/* 1006 */       this.link[d] = this.link[s];
/*      */       return;
/*      */     } 
/* 1009 */     long links = this.link[s];
/* 1010 */     int prev = (int)(links >>> 32L);
/* 1011 */     int next = (int)links;
/* 1012 */     this.link[prev] = this.link[prev] ^ (this.link[prev] ^ d & 0xFFFFFFFFL) & 0xFFFFFFFFL;
/* 1013 */     this.link[next] = this.link[next] ^ (this.link[next] ^ (d & 0xFFFFFFFFL) << 32L) & 0xFFFFFFFF00000000L;
/* 1014 */     this.link[d] = links;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public long firstLongKey() {
/* 1024 */     if (this.size == 0) throw new NoSuchElementException(); 
/* 1025 */     return this.key[this.first];
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public long lastLongKey() {
/* 1035 */     if (this.size == 0) throw new NoSuchElementException(); 
/* 1036 */     return this.key[this.last];
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Long2ObjectSortedMap<V> tailMap(long from) {
/* 1046 */     throw new UnsupportedOperationException();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Long2ObjectSortedMap<V> headMap(long to) {
/* 1056 */     throw new UnsupportedOperationException();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Long2ObjectSortedMap<V> subMap(long from, long to) {
/* 1066 */     throw new UnsupportedOperationException();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public LongComparator comparator() {
/* 1076 */     return null;
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
/* 1091 */     int prev = -1;
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 1096 */     int next = -1;
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 1101 */     int curr = -1;
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 1106 */     int index = -1;
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     protected MapIterator() {
/* 1112 */       this.next = Long2ObjectLinkedOpenHashMap.this.first;
/* 1113 */       this.index = 0;
/*      */     }
/*      */     
/*      */     private MapIterator(long from) {
/* 1117 */       if (from == 0L) {
/* 1118 */         if (Long2ObjectLinkedOpenHashMap.this.containsNullKey) {
/* 1119 */           this.next = (int)Long2ObjectLinkedOpenHashMap.this.link[Long2ObjectLinkedOpenHashMap.this.n];
/* 1120 */           this.prev = Long2ObjectLinkedOpenHashMap.this.n; return;
/*      */         } 
/* 1122 */         throw new NoSuchElementException("The key " + from + " does not belong to this map.");
/*      */       } 
/* 1124 */       if (Long2ObjectLinkedOpenHashMap.this.key[Long2ObjectLinkedOpenHashMap.this.last] == from) {
/* 1125 */         this.prev = Long2ObjectLinkedOpenHashMap.this.last;
/* 1126 */         this.index = Long2ObjectLinkedOpenHashMap.this.size;
/*      */         
/*      */         return;
/*      */       } 
/* 1130 */       int pos = (int)HashCommon.mix(from) & Long2ObjectLinkedOpenHashMap.this.mask;
/*      */       
/* 1132 */       while (Long2ObjectLinkedOpenHashMap.this.key[pos] != 0L) {
/* 1133 */         if (Long2ObjectLinkedOpenHashMap.this.key[pos] == from) {
/*      */           
/* 1135 */           this.next = (int)Long2ObjectLinkedOpenHashMap.this.link[pos];
/* 1136 */           this.prev = pos;
/*      */           return;
/*      */         } 
/* 1139 */         pos = pos + 1 & Long2ObjectLinkedOpenHashMap.this.mask;
/*      */       } 
/* 1141 */       throw new NoSuchElementException("The key " + from + " does not belong to this map.");
/*      */     }
/*      */     
/*      */     public boolean hasNext() {
/* 1145 */       return (this.next != -1);
/*      */     }
/*      */     
/*      */     public boolean hasPrevious() {
/* 1149 */       return (this.prev != -1);
/*      */     }
/*      */     
/*      */     private final void ensureIndexKnown() {
/* 1153 */       if (this.index >= 0)
/* 1154 */         return;  if (this.prev == -1) {
/* 1155 */         this.index = 0;
/*      */         return;
/*      */       } 
/* 1158 */       if (this.next == -1) {
/* 1159 */         this.index = Long2ObjectLinkedOpenHashMap.this.size;
/*      */         return;
/*      */       } 
/* 1162 */       int pos = Long2ObjectLinkedOpenHashMap.this.first;
/* 1163 */       this.index = 1;
/* 1164 */       while (pos != this.prev) {
/* 1165 */         pos = (int)Long2ObjectLinkedOpenHashMap.this.link[pos];
/* 1166 */         this.index++;
/*      */       } 
/*      */     }
/*      */     
/*      */     public int nextIndex() {
/* 1171 */       ensureIndexKnown();
/* 1172 */       return this.index;
/*      */     }
/*      */     
/*      */     public int previousIndex() {
/* 1176 */       ensureIndexKnown();
/* 1177 */       return this.index - 1;
/*      */     }
/*      */     
/*      */     public int nextEntry() {
/* 1181 */       if (!hasNext()) throw new NoSuchElementException(); 
/* 1182 */       this.curr = this.next;
/* 1183 */       this.next = (int)Long2ObjectLinkedOpenHashMap.this.link[this.curr];
/* 1184 */       this.prev = this.curr;
/* 1185 */       if (this.index >= 0) this.index++; 
/* 1186 */       return this.curr;
/*      */     }
/*      */     
/*      */     public int previousEntry() {
/* 1190 */       if (!hasPrevious()) throw new NoSuchElementException(); 
/* 1191 */       this.curr = this.prev;
/* 1192 */       this.prev = (int)(Long2ObjectLinkedOpenHashMap.this.link[this.curr] >>> 32L);
/* 1193 */       this.next = this.curr;
/* 1194 */       if (this.index >= 0) this.index--; 
/* 1195 */       return this.curr;
/*      */     }
/*      */     
/*      */     public void forEachRemaining(ConsumerType action) {
/* 1199 */       while (hasNext()) {
/* 1200 */         this.curr = this.next;
/* 1201 */         this.next = (int)Long2ObjectLinkedOpenHashMap.this.link[this.curr];
/* 1202 */         this.prev = this.curr;
/* 1203 */         if (this.index >= 0) this.index++; 
/* 1204 */         acceptOnIndex(action, this.curr);
/*      */       } 
/*      */     }
/*      */     
/*      */     public void remove() {
/* 1209 */       ensureIndexKnown();
/* 1210 */       if (this.curr == -1) throw new IllegalStateException(); 
/* 1211 */       if (this.curr == this.prev)
/*      */       
/*      */       { 
/* 1214 */         this.index--;
/* 1215 */         this.prev = (int)(Long2ObjectLinkedOpenHashMap.this.link[this.curr] >>> 32L); }
/* 1216 */       else { this.next = (int)Long2ObjectLinkedOpenHashMap.this.link[this.curr]; }
/* 1217 */        Long2ObjectLinkedOpenHashMap.this.size--;
/*      */ 
/*      */       
/* 1220 */       if (this.prev == -1) { Long2ObjectLinkedOpenHashMap.this.first = this.next; }
/* 1221 */       else { Long2ObjectLinkedOpenHashMap.this.link[this.prev] = Long2ObjectLinkedOpenHashMap.this.link[this.prev] ^ (Long2ObjectLinkedOpenHashMap.this.link[this.prev] ^ this.next & 0xFFFFFFFFL) & 0xFFFFFFFFL; }
/* 1222 */        if (this.next == -1) { Long2ObjectLinkedOpenHashMap.this.last = this.prev; }
/* 1223 */       else { Long2ObjectLinkedOpenHashMap.this.link[this.next] = Long2ObjectLinkedOpenHashMap.this.link[this.next] ^ (Long2ObjectLinkedOpenHashMap.this.link[this.next] ^ (this.prev & 0xFFFFFFFFL) << 32L) & 0xFFFFFFFF00000000L; }
/* 1224 */        int pos = this.curr;
/* 1225 */       this.curr = -1;
/* 1226 */       if (pos == Long2ObjectLinkedOpenHashMap.this.n) {
/* 1227 */         Long2ObjectLinkedOpenHashMap.this.containsNullKey = false;
/* 1228 */         Long2ObjectLinkedOpenHashMap.this.value[Long2ObjectLinkedOpenHashMap.this.n] = null;
/*      */       } else {
/*      */         
/* 1231 */         long[] key = Long2ObjectLinkedOpenHashMap.this.key; while (true) {
/*      */           long curr;
/*      */           int last;
/* 1234 */           pos = (last = pos) + 1 & Long2ObjectLinkedOpenHashMap.this.mask;
/*      */           while (true) {
/* 1236 */             if ((curr = key[pos]) == 0L) {
/* 1237 */               key[last] = 0L;
/* 1238 */               Long2ObjectLinkedOpenHashMap.this.value[last] = null;
/*      */               return;
/*      */             } 
/* 1241 */             int slot = (int)HashCommon.mix(curr) & Long2ObjectLinkedOpenHashMap.this.mask;
/* 1242 */             if ((last <= pos) ? (last >= slot || slot > pos) : (last >= slot && slot > pos))
/* 1243 */               break;  pos = pos + 1 & Long2ObjectLinkedOpenHashMap.this.mask;
/*      */           } 
/* 1245 */           key[last] = curr;
/* 1246 */           Long2ObjectLinkedOpenHashMap.this.value[last] = Long2ObjectLinkedOpenHashMap.this.value[pos];
/* 1247 */           if (this.next == pos) this.next = last; 
/* 1248 */           if (this.prev == pos) this.prev = last; 
/* 1249 */           Long2ObjectLinkedOpenHashMap.this.fixPointers(pos, last);
/*      */         } 
/*      */       } 
/*      */     }
/*      */     
/*      */     public int skip(int n) {
/* 1255 */       int i = n;
/* 1256 */       for (; i-- != 0 && hasNext(); nextEntry());
/* 1257 */       return n - i - 1;
/*      */     }
/*      */     
/*      */     public int back(int n) {
/* 1261 */       int i = n;
/* 1262 */       for (; i-- != 0 && hasPrevious(); previousEntry());
/* 1263 */       return n - i - 1;
/*      */     }
/*      */     
/*      */     public void set(Long2ObjectMap.Entry<V> ok) {
/* 1267 */       throw new UnsupportedOperationException();
/*      */     }
/*      */     
/*      */     public void add(Long2ObjectMap.Entry<V> ok) {
/* 1271 */       throw new UnsupportedOperationException();
/*      */     }
/*      */     
/*      */     abstract void acceptOnIndex(ConsumerType param1ConsumerType, int param1Int); }
/*      */   
/*      */   private final class EntryIterator extends MapIterator<Consumer<? super Long2ObjectMap.Entry<V>>> implements ObjectListIterator<Long2ObjectMap.Entry<V>> {
/*      */     private Long2ObjectLinkedOpenHashMap<V>.MapEntry entry;
/*      */     
/*      */     public EntryIterator() {}
/*      */     
/*      */     public EntryIterator(long from) {
/* 1282 */       super(from);
/*      */     }
/*      */ 
/*      */ 
/*      */     
/*      */     final void acceptOnIndex(Consumer<? super Long2ObjectMap.Entry<V>> action, int index) {
/* 1288 */       action.accept(new Long2ObjectLinkedOpenHashMap.MapEntry(index));
/*      */     }
/*      */ 
/*      */     
/*      */     public Long2ObjectLinkedOpenHashMap<V>.MapEntry next() {
/* 1293 */       return this.entry = new Long2ObjectLinkedOpenHashMap.MapEntry(nextEntry());
/*      */     }
/*      */ 
/*      */     
/*      */     public Long2ObjectLinkedOpenHashMap<V>.MapEntry previous() {
/* 1298 */       return this.entry = new Long2ObjectLinkedOpenHashMap.MapEntry(previousEntry());
/*      */     }
/*      */ 
/*      */     
/*      */     public void remove() {
/* 1303 */       super.remove();
/* 1304 */       this.entry.index = -1;
/*      */     }
/*      */   }
/*      */   
/*      */   private final class FastEntryIterator extends MapIterator<Consumer<? super Long2ObjectMap.Entry<V>>> implements ObjectListIterator<Long2ObjectMap.Entry<V>> {
/* 1309 */     final Long2ObjectLinkedOpenHashMap<V>.MapEntry entry = new Long2ObjectLinkedOpenHashMap.MapEntry();
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public FastEntryIterator(long from) {
/* 1315 */       super(from);
/*      */     }
/*      */ 
/*      */ 
/*      */     
/*      */     final void acceptOnIndex(Consumer<? super Long2ObjectMap.Entry<V>> action, int index) {
/* 1321 */       this.entry.index = index;
/* 1322 */       action.accept(this.entry);
/*      */     }
/*      */ 
/*      */     
/*      */     public Long2ObjectLinkedOpenHashMap<V>.MapEntry next() {
/* 1327 */       this.entry.index = nextEntry();
/* 1328 */       return this.entry;
/*      */     }
/*      */ 
/*      */     
/*      */     public Long2ObjectLinkedOpenHashMap<V>.MapEntry previous() {
/* 1333 */       this.entry.index = previousEntry();
/* 1334 */       return this.entry;
/*      */     }
/*      */     
/*      */     public FastEntryIterator() {} }
/*      */   
/*      */   private final class MapEntrySet extends AbstractObjectSortedSet<Long2ObjectMap.Entry<V>> implements Long2ObjectSortedMap.FastSortedEntrySet<V> {
/*      */     private static final int SPLITERATOR_CHARACTERISTICS = 81;
/*      */     
/*      */     public ObjectBidirectionalIterator<Long2ObjectMap.Entry<V>> iterator() {
/* 1343 */       return (ObjectBidirectionalIterator<Long2ObjectMap.Entry<V>>)new Long2ObjectLinkedOpenHashMap.EntryIterator();
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
/*      */     public ObjectSpliterator<Long2ObjectMap.Entry<V>> spliterator() {
/* 1361 */       return ObjectSpliterators.asSpliterator((ObjectIterator)iterator(), Size64.sizeOf(Long2ObjectLinkedOpenHashMap.this), 81);
/*      */     }
/*      */ 
/*      */     
/*      */     public Comparator<? super Long2ObjectMap.Entry<V>> comparator() {
/* 1366 */       return null;
/*      */     }
/*      */ 
/*      */     
/*      */     public ObjectSortedSet<Long2ObjectMap.Entry<V>> subSet(Long2ObjectMap.Entry<V> fromElement, Long2ObjectMap.Entry<V> toElement) {
/* 1371 */       throw new UnsupportedOperationException();
/*      */     }
/*      */ 
/*      */     
/*      */     public ObjectSortedSet<Long2ObjectMap.Entry<V>> headSet(Long2ObjectMap.Entry<V> toElement) {
/* 1376 */       throw new UnsupportedOperationException();
/*      */     }
/*      */ 
/*      */     
/*      */     public ObjectSortedSet<Long2ObjectMap.Entry<V>> tailSet(Long2ObjectMap.Entry<V> fromElement) {
/* 1381 */       throw new UnsupportedOperationException();
/*      */     }
/*      */ 
/*      */     
/*      */     public Long2ObjectMap.Entry<V> first() {
/* 1386 */       if (Long2ObjectLinkedOpenHashMap.this.size == 0) throw new NoSuchElementException(); 
/* 1387 */       return new Long2ObjectLinkedOpenHashMap.MapEntry(Long2ObjectLinkedOpenHashMap.this.first);
/*      */     }
/*      */ 
/*      */     
/*      */     public Long2ObjectMap.Entry<V> last() {
/* 1392 */       if (Long2ObjectLinkedOpenHashMap.this.size == 0) throw new NoSuchElementException(); 
/* 1393 */       return new Long2ObjectLinkedOpenHashMap.MapEntry(Long2ObjectLinkedOpenHashMap.this.last);
/*      */     }
/*      */ 
/*      */ 
/*      */     
/*      */     public boolean contains(Object o) {
/* 1399 */       if (!(o instanceof Map.Entry)) return false; 
/* 1400 */       Map.Entry<?, ?> e = (Map.Entry<?, ?>)o;
/* 1401 */       if (e.getKey() == null || !(e.getKey() instanceof Long)) return false; 
/* 1402 */       long k = ((Long)e.getKey()).longValue();
/* 1403 */       V v = (V)e.getValue();
/* 1404 */       if (k == 0L) return (Long2ObjectLinkedOpenHashMap.this.containsNullKey && Objects.equals(Long2ObjectLinkedOpenHashMap.this.value[Long2ObjectLinkedOpenHashMap.this.n], v));
/*      */       
/* 1406 */       long[] key = Long2ObjectLinkedOpenHashMap.this.key;
/*      */       long curr;
/*      */       int pos;
/* 1409 */       if ((curr = key[pos = (int)HashCommon.mix(k) & Long2ObjectLinkedOpenHashMap.this.mask]) == 0L) return false; 
/* 1410 */       if (k == curr) return Objects.equals(Long2ObjectLinkedOpenHashMap.this.value[pos], v);
/*      */       
/*      */       while (true) {
/* 1413 */         if ((curr = key[pos = pos + 1 & Long2ObjectLinkedOpenHashMap.this.mask]) == 0L) return false; 
/* 1414 */         if (k == curr) return Objects.equals(Long2ObjectLinkedOpenHashMap.this.value[pos], v);
/*      */       
/*      */       } 
/*      */     }
/*      */ 
/*      */     
/*      */     public boolean remove(Object o) {
/* 1421 */       if (!(o instanceof Map.Entry)) return false; 
/* 1422 */       Map.Entry<?, ?> e = (Map.Entry<?, ?>)o;
/* 1423 */       if (e.getKey() == null || !(e.getKey() instanceof Long)) return false; 
/* 1424 */       long k = ((Long)e.getKey()).longValue();
/* 1425 */       V v = (V)e.getValue();
/* 1426 */       if (k == 0L) {
/* 1427 */         if (Long2ObjectLinkedOpenHashMap.this.containsNullKey && Objects.equals(Long2ObjectLinkedOpenHashMap.this.value[Long2ObjectLinkedOpenHashMap.this.n], v)) {
/* 1428 */           Long2ObjectLinkedOpenHashMap.this.removeNullEntry();
/* 1429 */           return true;
/*      */         } 
/* 1431 */         return false;
/*      */       } 
/*      */       
/* 1434 */       long[] key = Long2ObjectLinkedOpenHashMap.this.key;
/*      */       long curr;
/*      */       int pos;
/* 1437 */       if ((curr = key[pos = (int)HashCommon.mix(k) & Long2ObjectLinkedOpenHashMap.this.mask]) == 0L) return false; 
/* 1438 */       if (curr == k) {
/* 1439 */         if (Objects.equals(Long2ObjectLinkedOpenHashMap.this.value[pos], v)) {
/* 1440 */           Long2ObjectLinkedOpenHashMap.this.removeEntry(pos);
/* 1441 */           return true;
/*      */         } 
/* 1443 */         return false;
/*      */       } 
/*      */       while (true) {
/* 1446 */         if ((curr = key[pos = pos + 1 & Long2ObjectLinkedOpenHashMap.this.mask]) == 0L) return false; 
/* 1447 */         if (curr == k && 
/* 1448 */           Objects.equals(Long2ObjectLinkedOpenHashMap.this.value[pos], v)) {
/* 1449 */           Long2ObjectLinkedOpenHashMap.this.removeEntry(pos);
/* 1450 */           return true;
/*      */         } 
/*      */       } 
/*      */     }
/*      */ 
/*      */ 
/*      */     
/*      */     public int size() {
/* 1458 */       return Long2ObjectLinkedOpenHashMap.this.size;
/*      */     }
/*      */ 
/*      */     
/*      */     public void clear() {
/* 1463 */       Long2ObjectLinkedOpenHashMap.this.clear();
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
/*      */     public ObjectListIterator<Long2ObjectMap.Entry<V>> iterator(Long2ObjectMap.Entry<V> from) {
/* 1476 */       return new Long2ObjectLinkedOpenHashMap.EntryIterator(from.getLongKey());
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public ObjectListIterator<Long2ObjectMap.Entry<V>> fastIterator() {
/* 1487 */       return new Long2ObjectLinkedOpenHashMap.FastEntryIterator();
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
/*      */     public ObjectListIterator<Long2ObjectMap.Entry<V>> fastIterator(Long2ObjectMap.Entry<V> from) {
/* 1500 */       return new Long2ObjectLinkedOpenHashMap.FastEntryIterator(from.getLongKey());
/*      */     }
/*      */ 
/*      */ 
/*      */     
/*      */     public void forEach(Consumer<? super Long2ObjectMap.Entry<V>> consumer) {
/* 1506 */       for (int i = Long2ObjectLinkedOpenHashMap.this.size, next = Long2ObjectLinkedOpenHashMap.this.first; i-- != 0; ) {
/* 1507 */         int curr = next;
/* 1508 */         next = (int)Long2ObjectLinkedOpenHashMap.this.link[curr];
/* 1509 */         consumer.accept(new Long2ObjectLinkedOpenHashMap.MapEntry(curr));
/*      */       } 
/*      */     }
/*      */ 
/*      */ 
/*      */     
/*      */     public void fastForEach(Consumer<? super Long2ObjectMap.Entry<V>> consumer) {
/* 1516 */       Long2ObjectLinkedOpenHashMap<V>.MapEntry entry = new Long2ObjectLinkedOpenHashMap.MapEntry();
/* 1517 */       for (int i = Long2ObjectLinkedOpenHashMap.this.size, next = Long2ObjectLinkedOpenHashMap.this.first; i-- != 0; ) {
/* 1518 */         entry.index = next;
/* 1519 */         next = (int)Long2ObjectLinkedOpenHashMap.this.link[next];
/* 1520 */         consumer.accept(entry);
/*      */       } 
/*      */     }
/*      */   }
/*      */ 
/*      */   
/*      */   public Long2ObjectSortedMap.FastSortedEntrySet<V> long2ObjectEntrySet() {
/* 1527 */     if (this.entries == null) this.entries = new MapEntrySet(); 
/* 1528 */     return this.entries;
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
/* 1541 */       super(k);
/*      */     }
/*      */ 
/*      */     
/*      */     public long previousLong() {
/* 1546 */       return Long2ObjectLinkedOpenHashMap.this.key[previousEntry()];
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
/* 1558 */       action.accept(Long2ObjectLinkedOpenHashMap.this.key[index]);
/*      */     }
/*      */ 
/*      */     
/*      */     public long nextLong() {
/* 1563 */       return Long2ObjectLinkedOpenHashMap.this.key[nextEntry()];
/*      */     } }
/*      */   
/*      */   private final class KeySet extends AbstractLongSortedSet {
/*      */     private static final int SPLITERATOR_CHARACTERISTICS = 337;
/*      */     
/*      */     private KeySet() {}
/*      */     
/*      */     public LongListIterator iterator(long from) {
/* 1572 */       return new Long2ObjectLinkedOpenHashMap.KeyIterator(from);
/*      */     }
/*      */ 
/*      */     
/*      */     public LongListIterator iterator() {
/* 1577 */       return new Long2ObjectLinkedOpenHashMap.KeyIterator();
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public LongSpliterator spliterator() {
/* 1587 */       return LongSpliterators.asSpliterator(iterator(), Size64.sizeOf(Long2ObjectLinkedOpenHashMap.this), 337);
/*      */     }
/*      */ 
/*      */ 
/*      */     
/*      */     public void forEach(LongConsumer consumer) {
/* 1593 */       for (int i = Long2ObjectLinkedOpenHashMap.this.size, next = Long2ObjectLinkedOpenHashMap.this.first; i-- != 0; ) {
/* 1594 */         int curr = next;
/* 1595 */         next = (int)Long2ObjectLinkedOpenHashMap.this.link[curr];
/* 1596 */         consumer.accept(Long2ObjectLinkedOpenHashMap.this.key[curr]);
/*      */       } 
/*      */     }
/*      */ 
/*      */     
/*      */     public int size() {
/* 1602 */       return Long2ObjectLinkedOpenHashMap.this.size;
/*      */     }
/*      */ 
/*      */     
/*      */     public boolean contains(long k) {
/* 1607 */       return Long2ObjectLinkedOpenHashMap.this.containsKey(k);
/*      */     }
/*      */ 
/*      */     
/*      */     public boolean remove(long k) {
/* 1612 */       int oldSize = Long2ObjectLinkedOpenHashMap.this.size;
/* 1613 */       Long2ObjectLinkedOpenHashMap.this.remove(k);
/* 1614 */       return (Long2ObjectLinkedOpenHashMap.this.size != oldSize);
/*      */     }
/*      */ 
/*      */     
/*      */     public void clear() {
/* 1619 */       Long2ObjectLinkedOpenHashMap.this.clear();
/*      */     }
/*      */ 
/*      */     
/*      */     public long firstLong() {
/* 1624 */       if (Long2ObjectLinkedOpenHashMap.this.size == 0) throw new NoSuchElementException(); 
/* 1625 */       return Long2ObjectLinkedOpenHashMap.this.key[Long2ObjectLinkedOpenHashMap.this.first];
/*      */     }
/*      */ 
/*      */     
/*      */     public long lastLong() {
/* 1630 */       if (Long2ObjectLinkedOpenHashMap.this.size == 0) throw new NoSuchElementException(); 
/* 1631 */       return Long2ObjectLinkedOpenHashMap.this.key[Long2ObjectLinkedOpenHashMap.this.last];
/*      */     }
/*      */ 
/*      */     
/*      */     public LongComparator comparator() {
/* 1636 */       return null;
/*      */     }
/*      */ 
/*      */     
/*      */     public LongSortedSet tailSet(long from) {
/* 1641 */       throw new UnsupportedOperationException();
/*      */     }
/*      */ 
/*      */     
/*      */     public LongSortedSet headSet(long to) {
/* 1646 */       throw new UnsupportedOperationException();
/*      */     }
/*      */ 
/*      */     
/*      */     public LongSortedSet subSet(long from, long to) {
/* 1651 */       throw new UnsupportedOperationException();
/*      */     }
/*      */   }
/*      */ 
/*      */   
/*      */   public LongSortedSet keySet() {
/* 1657 */     if (this.keys == null) this.keys = new KeySet(); 
/* 1658 */     return this.keys;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private final class ValueIterator
/*      */     extends MapIterator<Consumer<? super V>>
/*      */     implements ObjectListIterator<V>
/*      */   {
/*      */     public V previous() {
/* 1672 */       return Long2ObjectLinkedOpenHashMap.this.value[previousEntry()];
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
/*      */     final void acceptOnIndex(Consumer<? super V> action, int index) {
/* 1684 */       action.accept(Long2ObjectLinkedOpenHashMap.this.value[index]);
/*      */     }
/*      */ 
/*      */     
/*      */     public V next() {
/* 1689 */       return Long2ObjectLinkedOpenHashMap.this.value[nextEntry()];
/*      */     }
/*      */   }
/*      */ 
/*      */   
/*      */   public ObjectCollection<V> values() {
/* 1695 */     if (this.values == null) this.values = (ObjectCollection<V>)new AbstractObjectCollection<V>()
/*      */         {
/*      */           private static final int SPLITERATOR_CHARACTERISTICS = 80;
/*      */           
/*      */           public ObjectIterator<V> iterator() {
/* 1700 */             return (ObjectIterator<V>)new Long2ObjectLinkedOpenHashMap.ValueIterator();
/*      */           }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */           
/*      */           public ObjectSpliterator<V> spliterator() {
/* 1710 */             return ObjectSpliterators.asSpliterator(iterator(), Size64.sizeOf(Long2ObjectLinkedOpenHashMap.this), 80);
/*      */           }
/*      */ 
/*      */ 
/*      */           
/*      */           public void forEach(Consumer<? super V> consumer) {
/* 1716 */             for (int i = Long2ObjectLinkedOpenHashMap.this.size, next = Long2ObjectLinkedOpenHashMap.this.first; i-- != 0; ) {
/* 1717 */               int curr = next;
/* 1718 */               next = (int)Long2ObjectLinkedOpenHashMap.this.link[curr];
/* 1719 */               consumer.accept(Long2ObjectLinkedOpenHashMap.this.value[curr]);
/*      */             } 
/*      */           }
/*      */ 
/*      */           
/*      */           public int size() {
/* 1725 */             return Long2ObjectLinkedOpenHashMap.this.size;
/*      */           }
/*      */ 
/*      */           
/*      */           public boolean contains(Object v) {
/* 1730 */             return Long2ObjectLinkedOpenHashMap.this.containsValue(v);
/*      */           }
/*      */ 
/*      */           
/*      */           public void clear() {
/* 1735 */             Long2ObjectLinkedOpenHashMap.this.clear();
/*      */           }
/*      */         }; 
/* 1738 */     return this.values;
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
/* 1755 */     return trim(this.size);
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
/* 1777 */     int l = HashCommon.nextPowerOfTwo((int)Math.ceil((n / this.f)));
/* 1778 */     if (l >= this.n || this.size > HashCommon.maxFill(l, this.f)) return true; 
/*      */     try {
/* 1780 */       rehash(l);
/* 1781 */     } catch (OutOfMemoryError cantDoIt) {
/* 1782 */       return false;
/*      */     } 
/* 1784 */     return true;
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
/* 1799 */     long[] key = this.key;
/* 1800 */     V[] value = this.value;
/* 1801 */     int mask = newN - 1;
/* 1802 */     long[] newKey = new long[newN + 1];
/* 1803 */     V[] newValue = (V[])new Object[newN + 1];
/* 1804 */     int i = this.first, prev = -1, newPrev = -1;
/* 1805 */     long[] link = this.link;
/* 1806 */     long[] newLink = new long[newN + 1];
/* 1807 */     this.first = -1;
/* 1808 */     for (int j = this.size; j-- != 0; ) {
/* 1809 */       int pos; if (key[i] == 0L) { pos = newN; }
/*      */       else
/* 1811 */       { pos = (int)HashCommon.mix(key[i]) & mask;
/* 1812 */         for (; newKey[pos] != 0L; pos = pos + 1 & mask); }
/*      */       
/* 1814 */       newKey[pos] = key[i];
/* 1815 */       newValue[pos] = value[i];
/* 1816 */       if (prev != -1) {
/* 1817 */         newLink[newPrev] = newLink[newPrev] ^ (newLink[newPrev] ^ pos & 0xFFFFFFFFL) & 0xFFFFFFFFL;
/* 1818 */         newLink[pos] = newLink[pos] ^ (newLink[pos] ^ (newPrev & 0xFFFFFFFFL) << 32L) & 0xFFFFFFFF00000000L;
/* 1819 */         newPrev = pos;
/*      */       } else {
/* 1821 */         newPrev = this.first = pos;
/*      */         
/* 1823 */         newLink[pos] = -1L;
/*      */       } 
/* 1825 */       int t = i;
/* 1826 */       i = (int)link[i];
/* 1827 */       prev = t;
/*      */     } 
/* 1829 */     this.link = newLink;
/* 1830 */     this.last = newPrev;
/* 1831 */     if (newPrev != -1)
/*      */     {
/* 1833 */       newLink[newPrev] = newLink[newPrev] | 0xFFFFFFFFL; } 
/* 1834 */     this.n = newN;
/* 1835 */     this.mask = mask;
/* 1836 */     this.maxFill = HashCommon.maxFill(this.n, this.f);
/* 1837 */     this.key = newKey;
/* 1838 */     this.value = newValue;
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
/*      */   public Long2ObjectLinkedOpenHashMap<V> clone() {
/*      */     Long2ObjectLinkedOpenHashMap<V> c;
/*      */     try {
/* 1855 */       c = (Long2ObjectLinkedOpenHashMap<V>)super.clone();
/* 1856 */     } catch (CloneNotSupportedException cantHappen) {
/* 1857 */       throw new InternalError();
/*      */     } 
/* 1859 */     c.keys = null;
/* 1860 */     c.values = null;
/* 1861 */     c.entries = null;
/* 1862 */     c.containsNullKey = this.containsNullKey;
/* 1863 */     c.key = (long[])this.key.clone();
/* 1864 */     c.value = (V[])this.value.clone();
/* 1865 */     c.link = (long[])this.link.clone();
/* 1866 */     return c;
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
/* 1880 */     int h = 0;
/* 1881 */     for (int j = realSize(), i = 0, t = 0; j-- != 0; ) {
/* 1882 */       for (; this.key[i] == 0L; i++);
/* 1883 */       t = HashCommon.long2int(this.key[i]);
/* 1884 */       if (this != this.value[i]) t ^= (this.value[i] == null) ? 0 : this.value[i].hashCode(); 
/* 1885 */       h += t;
/* 1886 */       i++;
/*      */     } 
/*      */     
/* 1889 */     if (this.containsNullKey) h += (this.value[this.n] == null) ? 0 : this.value[this.n].hashCode(); 
/* 1890 */     return h;
/*      */   }
/*      */   
/*      */   private void writeObject(ObjectOutputStream s) throws IOException {
/* 1894 */     long[] key = this.key;
/* 1895 */     V[] value = this.value;
/* 1896 */     EntryIterator i = new EntryIterator();
/* 1897 */     s.defaultWriteObject();
/* 1898 */     for (int j = this.size; j-- != 0; ) {
/* 1899 */       int e = i.nextEntry();
/* 1900 */       s.writeLong(key[e]);
/* 1901 */       s.writeObject(value[e]);
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   private void readObject(ObjectInputStream s) throws IOException, ClassNotFoundException {
/* 1907 */     s.defaultReadObject();
/* 1908 */     this.n = HashCommon.arraySize(this.size, this.f);
/* 1909 */     this.maxFill = HashCommon.maxFill(this.n, this.f);
/* 1910 */     this.mask = this.n - 1;
/* 1911 */     long[] key = this.key = new long[this.n + 1];
/* 1912 */     V[] value = this.value = (V[])new Object[this.n + 1];
/* 1913 */     long[] link = this.link = new long[this.n + 1];
/* 1914 */     int prev = -1;
/* 1915 */     this.first = this.last = -1;
/*      */ 
/*      */     
/* 1918 */     for (int i = this.size; i-- != 0; ) {
/* 1919 */       int pos; long k = s.readLong();
/* 1920 */       V v = (V)s.readObject();
/* 1921 */       if (k == 0L) {
/* 1922 */         pos = this.n;
/* 1923 */         this.containsNullKey = true;
/*      */       } else {
/* 1925 */         pos = (int)HashCommon.mix(k) & this.mask;
/* 1926 */         for (; key[pos] != 0L; pos = pos + 1 & this.mask);
/*      */       } 
/* 1928 */       key[pos] = k;
/* 1929 */       value[pos] = v;
/* 1930 */       if (this.first != -1) {
/* 1931 */         link[prev] = link[prev] ^ (link[prev] ^ pos & 0xFFFFFFFFL) & 0xFFFFFFFFL;
/* 1932 */         link[pos] = link[pos] ^ (link[pos] ^ (prev & 0xFFFFFFFFL) << 32L) & 0xFFFFFFFF00000000L;
/* 1933 */         prev = pos; continue;
/*      */       } 
/* 1935 */       prev = this.first = pos;
/*      */       
/* 1937 */       link[pos] = link[pos] | 0xFFFFFFFF00000000L;
/*      */     } 
/*      */     
/* 1940 */     this.last = prev;
/* 1941 */     if (prev != -1)
/*      */     {
/* 1943 */       link[prev] = link[prev] | 0xFFFFFFFFL;
/*      */     }
/*      */   }
/*      */   
/*      */   private void checkTable() {}
/*      */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\i\\unimi\dsi\fastutil\longs\Long2ObjectLinkedOpenHashMap.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */