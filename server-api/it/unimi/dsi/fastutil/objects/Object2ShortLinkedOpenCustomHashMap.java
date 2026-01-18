/*      */ package it.unimi.dsi.fastutil.objects;
/*      */ 
/*      */ import it.unimi.dsi.fastutil.Hash;
/*      */ import it.unimi.dsi.fastutil.HashCommon;
/*      */ import it.unimi.dsi.fastutil.SafeMath;
/*      */ import it.unimi.dsi.fastutil.Size64;
/*      */ import it.unimi.dsi.fastutil.shorts.AbstractShortCollection;
/*      */ import it.unimi.dsi.fastutil.shorts.ShortCollection;
/*      */ import it.unimi.dsi.fastutil.shorts.ShortConsumer;
/*      */ import it.unimi.dsi.fastutil.shorts.ShortIterator;
/*      */ import it.unimi.dsi.fastutil.shorts.ShortListIterator;
/*      */ import it.unimi.dsi.fastutil.shorts.ShortSpliterator;
/*      */ import it.unimi.dsi.fastutil.shorts.ShortSpliterators;
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
/*      */ import java.util.function.ToIntFunction;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ public class Object2ShortLinkedOpenCustomHashMap<K>
/*      */   extends AbstractObject2ShortSortedMap<K>
/*      */   implements Serializable, Cloneable, Hash
/*      */ {
/*      */   private static final long serialVersionUID = 0L;
/*      */   private static final boolean ASSERTS = false;
/*      */   protected transient K[] key;
/*      */   protected transient short[] value;
/*      */   protected transient int mask;
/*      */   protected transient boolean containsNullKey;
/*      */   protected Hash.Strategy<? super K> strategy;
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
/*      */   protected transient Object2ShortSortedMap.FastSortedEntrySet<K> entries;
/*      */ 
/*      */   
/*      */   protected transient ObjectSortedSet<K> keys;
/*      */ 
/*      */   
/*      */   protected transient ShortCollection values;
/*      */ 
/*      */ 
/*      */   
/*      */   public Object2ShortLinkedOpenCustomHashMap(int expected, float f, Hash.Strategy<? super K> strategy) {
/*  145 */     this.strategy = strategy;
/*  146 */     if (f <= 0.0F || f >= 1.0F) throw new IllegalArgumentException("Load factor must be greater than 0 and smaller than 1"); 
/*  147 */     if (expected < 0) throw new IllegalArgumentException("The expected number of elements must be nonnegative"); 
/*  148 */     this.f = f;
/*  149 */     this.minN = this.n = HashCommon.arraySize(expected, f);
/*  150 */     this.mask = this.n - 1;
/*  151 */     this.maxFill = HashCommon.maxFill(this.n, f);
/*  152 */     this.key = (K[])new Object[this.n + 1];
/*  153 */     this.value = new short[this.n + 1];
/*  154 */     this.link = new long[this.n + 1];
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Object2ShortLinkedOpenCustomHashMap(int expected, Hash.Strategy<? super K> strategy) {
/*  164 */     this(expected, 0.75F, strategy);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Object2ShortLinkedOpenCustomHashMap(Hash.Strategy<? super K> strategy) {
/*  174 */     this(16, 0.75F, strategy);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Object2ShortLinkedOpenCustomHashMap(Map<? extends K, ? extends Short> m, float f, Hash.Strategy<? super K> strategy) {
/*  185 */     this(m.size(), f, strategy);
/*  186 */     putAll(m);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Object2ShortLinkedOpenCustomHashMap(Map<? extends K, ? extends Short> m, Hash.Strategy<? super K> strategy) {
/*  196 */     this(m, 0.75F, strategy);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Object2ShortLinkedOpenCustomHashMap(Object2ShortMap<K> m, float f, Hash.Strategy<? super K> strategy) {
/*  207 */     this(m.size(), f, strategy);
/*  208 */     putAll(m);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Object2ShortLinkedOpenCustomHashMap(Object2ShortMap<K> m, Hash.Strategy<? super K> strategy) {
/*  219 */     this(m, 0.75F, strategy);
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
/*      */   public Object2ShortLinkedOpenCustomHashMap(K[] k, short[] v, float f, Hash.Strategy<? super K> strategy) {
/*  232 */     this(k.length, f, strategy);
/*  233 */     if (k.length != v.length) throw new IllegalArgumentException("The key array and the value array have different lengths (" + k.length + " and " + v.length + ")"); 
/*  234 */     for (int i = 0; i < k.length; ) { put(k[i], v[i]); i++; }
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
/*      */   public Object2ShortLinkedOpenCustomHashMap(K[] k, short[] v, Hash.Strategy<? super K> strategy) {
/*  247 */     this(k, v, 0.75F, strategy);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Hash.Strategy<? super K> strategy() {
/*  256 */     return this.strategy;
/*      */   }
/*      */   
/*      */   private int realSize() {
/*  260 */     return this.containsNullKey ? (this.size - 1) : this.size;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void ensureCapacity(int capacity) {
/*  270 */     int needed = HashCommon.arraySize(capacity, this.f);
/*  271 */     if (needed > this.n) rehash(needed); 
/*      */   }
/*      */   
/*      */   private void tryCapacity(long capacity) {
/*  275 */     int needed = (int)Math.min(1073741824L, Math.max(2L, HashCommon.nextPowerOfTwo((long)Math.ceil(((float)capacity / this.f)))));
/*  276 */     if (needed > this.n) rehash(needed); 
/*      */   }
/*      */   
/*      */   private short removeEntry(int pos) {
/*  280 */     short oldValue = this.value[pos];
/*  281 */     this.size--;
/*  282 */     fixPointers(pos);
/*  283 */     shiftKeys(pos);
/*  284 */     if (this.n > this.minN && this.size < this.maxFill / 4 && this.n > 16) rehash(this.n / 2); 
/*  285 */     return oldValue;
/*      */   }
/*      */   
/*      */   private short removeNullEntry() {
/*  289 */     this.containsNullKey = false;
/*  290 */     this.key[this.n] = null;
/*  291 */     short oldValue = this.value[this.n];
/*  292 */     this.size--;
/*  293 */     fixPointers(this.n);
/*  294 */     if (this.n > this.minN && this.size < this.maxFill / 4 && this.n > 16) rehash(this.n / 2); 
/*  295 */     return oldValue;
/*      */   }
/*      */ 
/*      */   
/*      */   public void putAll(Map<? extends K, ? extends Short> m) {
/*  300 */     if (this.f <= 0.5D) { ensureCapacity(m.size()); }
/*  301 */     else { tryCapacity((size() + m.size())); }
/*      */     
/*  303 */     super.putAll(m);
/*      */   }
/*      */ 
/*      */   
/*      */   private int find(K k) {
/*  308 */     if (this.strategy.equals(k, null)) return this.containsNullKey ? this.n : -(this.n + 1);
/*      */     
/*  310 */     K[] key = this.key;
/*      */     K curr;
/*      */     int pos;
/*  313 */     if ((curr = key[pos = HashCommon.mix(this.strategy.hashCode(k)) & this.mask]) == null) return -(pos + 1); 
/*  314 */     if (this.strategy.equals(k, curr)) return pos;
/*      */     
/*      */     while (true) {
/*  317 */       if ((curr = key[pos = pos + 1 & this.mask]) == null) return -(pos + 1); 
/*  318 */       if (this.strategy.equals(k, curr)) return pos; 
/*      */     } 
/*      */   }
/*      */   
/*      */   private void insert(int pos, K k, short v) {
/*  323 */     if (pos == this.n) this.containsNullKey = true; 
/*  324 */     this.key[pos] = k;
/*  325 */     this.value[pos] = v;
/*  326 */     if (this.size == 0) {
/*  327 */       this.first = this.last = pos;
/*      */       
/*  329 */       this.link[pos] = -1L;
/*      */     } else {
/*  331 */       this.link[this.last] = this.link[this.last] ^ (this.link[this.last] ^ pos & 0xFFFFFFFFL) & 0xFFFFFFFFL;
/*  332 */       this.link[pos] = (this.last & 0xFFFFFFFFL) << 32L | 0xFFFFFFFFL;
/*  333 */       this.last = pos;
/*      */     } 
/*  335 */     if (this.size++ >= this.maxFill) rehash(HashCommon.arraySize(this.size + 1, this.f));
/*      */   
/*      */   }
/*      */ 
/*      */   
/*      */   public short put(K k, short v) {
/*  341 */     int pos = find(k);
/*  342 */     if (pos < 0) {
/*  343 */       insert(-pos - 1, k, v);
/*  344 */       return this.defRetValue;
/*      */     } 
/*  346 */     short oldValue = this.value[pos];
/*  347 */     this.value[pos] = v;
/*  348 */     return oldValue;
/*      */   }
/*      */   
/*      */   private short addToValue(int pos, short incr) {
/*  352 */     short oldValue = this.value[pos];
/*  353 */     this.value[pos] = (short)(oldValue + incr);
/*  354 */     return oldValue;
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
/*      */   public short addTo(K k, short incr) {
/*      */     int pos;
/*  372 */     if (this.strategy.equals(k, null)) {
/*  373 */       if (this.containsNullKey) return addToValue(this.n, incr); 
/*  374 */       pos = this.n;
/*  375 */       this.containsNullKey = true;
/*      */     } else {
/*      */       
/*  378 */       K[] key = this.key;
/*      */       K curr;
/*  380 */       if ((curr = key[pos = HashCommon.mix(this.strategy.hashCode(k)) & this.mask]) != null) {
/*  381 */         if (this.strategy.equals(curr, k)) return addToValue(pos, incr); 
/*  382 */         while ((curr = key[pos = pos + 1 & this.mask]) != null) { if (this.strategy.equals(curr, k)) return addToValue(pos, incr);  }
/*      */       
/*      */       } 
/*  385 */     }  this.key[pos] = k;
/*  386 */     this.value[pos] = (short)(this.defRetValue + incr);
/*  387 */     if (this.size == 0) {
/*  388 */       this.first = this.last = pos;
/*      */       
/*  390 */       this.link[pos] = -1L;
/*      */     } else {
/*  392 */       this.link[this.last] = this.link[this.last] ^ (this.link[this.last] ^ pos & 0xFFFFFFFFL) & 0xFFFFFFFFL;
/*  393 */       this.link[pos] = (this.last & 0xFFFFFFFFL) << 32L | 0xFFFFFFFFL;
/*  394 */       this.last = pos;
/*      */     } 
/*  396 */     if (this.size++ >= this.maxFill) rehash(HashCommon.arraySize(this.size + 1, this.f));
/*      */     
/*  398 */     return this.defRetValue;
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
/*  411 */     K[] key = this.key; while (true) {
/*      */       K curr; int last;
/*  413 */       pos = (last = pos) + 1 & this.mask;
/*      */       while (true) {
/*  415 */         if ((curr = key[pos]) == null) {
/*  416 */           key[last] = null;
/*      */           return;
/*      */         } 
/*  419 */         int slot = HashCommon.mix(this.strategy.hashCode(curr)) & this.mask;
/*  420 */         if ((last <= pos) ? (last >= slot || slot > pos) : (last >= slot && slot > pos))
/*  421 */           break;  pos = pos + 1 & this.mask;
/*      */       } 
/*  423 */       key[last] = curr;
/*  424 */       this.value[last] = this.value[pos];
/*  425 */       fixPointers(pos, last);
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public short removeShort(Object k) {
/*  432 */     if (this.strategy.equals(k, null)) {
/*  433 */       if (this.containsNullKey) return removeNullEntry(); 
/*  434 */       return this.defRetValue;
/*      */     } 
/*      */     
/*  437 */     K[] key = this.key;
/*      */     K curr;
/*      */     int pos;
/*  440 */     if ((curr = key[pos = HashCommon.mix(this.strategy.hashCode(k)) & this.mask]) == null) return this.defRetValue; 
/*  441 */     if (this.strategy.equals(k, curr)) return removeEntry(pos); 
/*      */     while (true) {
/*  443 */       if ((curr = key[pos = pos + 1 & this.mask]) == null) return this.defRetValue; 
/*  444 */       if (this.strategy.equals(k, curr)) return removeEntry(pos); 
/*      */     } 
/*      */   }
/*      */   
/*      */   private short setValue(int pos, short v) {
/*  449 */     short oldValue = this.value[pos];
/*  450 */     this.value[pos] = v;
/*  451 */     return oldValue;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public short removeFirstShort() {
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
/*  472 */     this.size--;
/*  473 */     short v = this.value[pos];
/*  474 */     if (pos == this.n)
/*  475 */     { this.containsNullKey = false;
/*  476 */       this.key[this.n] = null; }
/*  477 */     else { shiftKeys(pos); }
/*  478 */      if (this.n > this.minN && this.size < this.maxFill / 4 && this.n > 16) rehash(this.n / 2); 
/*  479 */     return v;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public short removeLastShort() {
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
/*  500 */     this.size--;
/*  501 */     short v = this.value[pos];
/*  502 */     if (pos == this.n)
/*  503 */     { this.containsNullKey = false;
/*  504 */       this.key[this.n] = null; }
/*  505 */     else { shiftKeys(pos); }
/*  506 */      if (this.n > this.minN && this.size < this.maxFill / 4 && this.n > 16) rehash(this.n / 2); 
/*  507 */     return v;
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
/*      */   
/*      */   public short getAndMoveToFirst(K k) {
/*  555 */     if (this.strategy.equals(k, null)) {
/*  556 */       if (this.containsNullKey) {
/*  557 */         moveIndexToFirst(this.n);
/*  558 */         return this.value[this.n];
/*      */       } 
/*  560 */       return this.defRetValue;
/*      */     } 
/*      */     
/*  563 */     K[] key = this.key;
/*      */     K curr;
/*      */     int pos;
/*  566 */     if ((curr = key[pos = HashCommon.mix(this.strategy.hashCode(k)) & this.mask]) == null) return this.defRetValue; 
/*  567 */     if (this.strategy.equals(k, curr)) {
/*  568 */       moveIndexToFirst(pos);
/*  569 */       return this.value[pos];
/*      */     } 
/*      */     
/*      */     while (true) {
/*  573 */       if ((curr = key[pos = pos + 1 & this.mask]) == null) return this.defRetValue; 
/*  574 */       if (this.strategy.equals(k, curr)) {
/*  575 */         moveIndexToFirst(pos);
/*  576 */         return this.value[pos];
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
/*      */   public short getAndMoveToLast(K k) {
/*  590 */     if (this.strategy.equals(k, null)) {
/*  591 */       if (this.containsNullKey) {
/*  592 */         moveIndexToLast(this.n);
/*  593 */         return this.value[this.n];
/*      */       } 
/*  595 */       return this.defRetValue;
/*      */     } 
/*      */     
/*  598 */     K[] key = this.key;
/*      */     K curr;
/*      */     int pos;
/*  601 */     if ((curr = key[pos = HashCommon.mix(this.strategy.hashCode(k)) & this.mask]) == null) return this.defRetValue; 
/*  602 */     if (this.strategy.equals(k, curr)) {
/*  603 */       moveIndexToLast(pos);
/*  604 */       return this.value[pos];
/*      */     } 
/*      */     
/*      */     while (true) {
/*  608 */       if ((curr = key[pos = pos + 1 & this.mask]) == null) return this.defRetValue; 
/*  609 */       if (this.strategy.equals(k, curr)) {
/*  610 */         moveIndexToLast(pos);
/*  611 */         return this.value[pos];
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
/*      */   public short putAndMoveToFirst(K k, short v) {
/*      */     int pos;
/*  627 */     if (this.strategy.equals(k, null)) {
/*  628 */       if (this.containsNullKey) {
/*  629 */         moveIndexToFirst(this.n);
/*  630 */         return setValue(this.n, v);
/*      */       } 
/*  632 */       this.containsNullKey = true;
/*  633 */       pos = this.n;
/*      */     } else {
/*      */       
/*  636 */       K[] key = this.key;
/*      */       K curr;
/*  638 */       if ((curr = key[pos = HashCommon.mix(this.strategy.hashCode(k)) & this.mask]) != null) {
/*  639 */         if (this.strategy.equals(curr, k)) {
/*  640 */           moveIndexToFirst(pos);
/*  641 */           return setValue(pos, v);
/*      */         } 
/*  643 */         while ((curr = key[pos = pos + 1 & this.mask]) != null) { if (this.strategy.equals(curr, k)) {
/*  644 */             moveIndexToFirst(pos);
/*  645 */             return setValue(pos, v);
/*      */           }  }
/*      */       
/*      */       } 
/*  649 */     }  this.key[pos] = k;
/*  650 */     this.value[pos] = v;
/*  651 */     if (this.size == 0) {
/*  652 */       this.first = this.last = pos;
/*      */       
/*  654 */       this.link[pos] = -1L;
/*      */     } else {
/*  656 */       this.link[this.first] = this.link[this.first] ^ (this.link[this.first] ^ (pos & 0xFFFFFFFFL) << 32L) & 0xFFFFFFFF00000000L;
/*  657 */       this.link[pos] = 0xFFFFFFFF00000000L | this.first & 0xFFFFFFFFL;
/*  658 */       this.first = pos;
/*      */     } 
/*  660 */     if (this.size++ >= this.maxFill) rehash(HashCommon.arraySize(this.size, this.f));
/*      */     
/*  662 */     return this.defRetValue;
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
/*      */   public short putAndMoveToLast(K k, short v) {
/*      */     int pos;
/*  676 */     if (this.strategy.equals(k, null)) {
/*  677 */       if (this.containsNullKey) {
/*  678 */         moveIndexToLast(this.n);
/*  679 */         return setValue(this.n, v);
/*      */       } 
/*  681 */       this.containsNullKey = true;
/*  682 */       pos = this.n;
/*      */     } else {
/*      */       
/*  685 */       K[] key = this.key;
/*      */       K curr;
/*  687 */       if ((curr = key[pos = HashCommon.mix(this.strategy.hashCode(k)) & this.mask]) != null) {
/*  688 */         if (this.strategy.equals(curr, k)) {
/*  689 */           moveIndexToLast(pos);
/*  690 */           return setValue(pos, v);
/*      */         } 
/*  692 */         while ((curr = key[pos = pos + 1 & this.mask]) != null) { if (this.strategy.equals(curr, k)) {
/*  693 */             moveIndexToLast(pos);
/*  694 */             return setValue(pos, v);
/*      */           }  }
/*      */       
/*      */       } 
/*  698 */     }  this.key[pos] = k;
/*  699 */     this.value[pos] = v;
/*  700 */     if (this.size == 0) {
/*  701 */       this.first = this.last = pos;
/*      */       
/*  703 */       this.link[pos] = -1L;
/*      */     } else {
/*  705 */       this.link[this.last] = this.link[this.last] ^ (this.link[this.last] ^ pos & 0xFFFFFFFFL) & 0xFFFFFFFFL;
/*  706 */       this.link[pos] = (this.last & 0xFFFFFFFFL) << 32L | 0xFFFFFFFFL;
/*  707 */       this.last = pos;
/*      */     } 
/*  709 */     if (this.size++ >= this.maxFill) rehash(HashCommon.arraySize(this.size, this.f));
/*      */     
/*  711 */     return this.defRetValue;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public short getShort(Object k) {
/*  717 */     if (this.strategy.equals(k, null)) return this.containsNullKey ? this.value[this.n] : this.defRetValue;
/*      */     
/*  719 */     K[] key = this.key;
/*      */     K curr;
/*      */     int pos;
/*  722 */     if ((curr = key[pos = HashCommon.mix(this.strategy.hashCode(k)) & this.mask]) == null) return this.defRetValue; 
/*  723 */     if (this.strategy.equals(k, curr)) return this.value[pos];
/*      */     
/*      */     while (true) {
/*  726 */       if ((curr = key[pos = pos + 1 & this.mask]) == null) return this.defRetValue; 
/*  727 */       if (this.strategy.equals(k, curr)) return this.value[pos];
/*      */     
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   public boolean containsKey(Object k) {
/*  734 */     if (this.strategy.equals(k, null)) return this.containsNullKey;
/*      */     
/*  736 */     K[] key = this.key;
/*      */     K curr;
/*      */     int pos;
/*  739 */     if ((curr = key[pos = HashCommon.mix(this.strategy.hashCode(k)) & this.mask]) == null) return false; 
/*  740 */     if (this.strategy.equals(k, curr)) return true;
/*      */     
/*      */     while (true) {
/*  743 */       if ((curr = key[pos = pos + 1 & this.mask]) == null) return false; 
/*  744 */       if (this.strategy.equals(k, curr)) return true;
/*      */     
/*      */     } 
/*      */   }
/*      */   
/*      */   public boolean containsValue(short v) {
/*  750 */     short[] value = this.value;
/*  751 */     K[] key = this.key;
/*  752 */     if (this.containsNullKey && value[this.n] == v) return true; 
/*  753 */     for (int i = this.n; i-- != 0;) { if (key[i] != null && value[i] == v) return true;  }
/*  754 */      return false;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public short getOrDefault(Object k, short defaultValue) {
/*  761 */     if (this.strategy.equals(k, null)) return this.containsNullKey ? this.value[this.n] : defaultValue;
/*      */     
/*  763 */     K[] key = this.key;
/*      */     K curr;
/*      */     int pos;
/*  766 */     if ((curr = key[pos = HashCommon.mix(this.strategy.hashCode(k)) & this.mask]) == null) return defaultValue; 
/*  767 */     if (this.strategy.equals(k, curr)) return this.value[pos];
/*      */     
/*      */     while (true) {
/*  770 */       if ((curr = key[pos = pos + 1 & this.mask]) == null) return defaultValue; 
/*  771 */       if (this.strategy.equals(k, curr)) return this.value[pos];
/*      */     
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   public short putIfAbsent(K k, short v) {
/*  778 */     int pos = find(k);
/*  779 */     if (pos >= 0) return this.value[pos]; 
/*  780 */     insert(-pos - 1, k, v);
/*  781 */     return this.defRetValue;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean remove(Object k, short v) {
/*  788 */     if (this.strategy.equals(k, null)) {
/*  789 */       if (this.containsNullKey && v == this.value[this.n]) {
/*  790 */         removeNullEntry();
/*  791 */         return true;
/*      */       } 
/*  793 */       return false;
/*      */     } 
/*      */     
/*  796 */     K[] key = this.key;
/*      */     K curr;
/*      */     int pos;
/*  799 */     if ((curr = key[pos = HashCommon.mix(this.strategy.hashCode(k)) & this.mask]) == null) return false; 
/*  800 */     if (this.strategy.equals(k, curr) && v == this.value[pos]) {
/*  801 */       removeEntry(pos);
/*  802 */       return true;
/*      */     } 
/*      */     while (true) {
/*  805 */       if ((curr = key[pos = pos + 1 & this.mask]) == null) return false; 
/*  806 */       if (this.strategy.equals(k, curr) && v == this.value[pos]) {
/*  807 */         removeEntry(pos);
/*  808 */         return true;
/*      */       } 
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean replace(K k, short oldValue, short v) {
/*  816 */     int pos = find(k);
/*  817 */     if (pos < 0 || oldValue != this.value[pos]) return false; 
/*  818 */     this.value[pos] = v;
/*  819 */     return true;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public short replace(K k, short v) {
/*  825 */     int pos = find(k);
/*  826 */     if (pos < 0) return this.defRetValue; 
/*  827 */     short oldValue = this.value[pos];
/*  828 */     this.value[pos] = v;
/*  829 */     return oldValue;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public short computeIfAbsent(K k, ToIntFunction<? super K> mappingFunction) {
/*  835 */     Objects.requireNonNull(mappingFunction);
/*  836 */     int pos = find(k);
/*  837 */     if (pos >= 0) return this.value[pos]; 
/*  838 */     short newValue = SafeMath.safeIntToShort(mappingFunction.applyAsInt(k));
/*  839 */     insert(-pos - 1, k, newValue);
/*  840 */     return newValue;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public short computeIfAbsent(K key, Object2ShortFunction<? super K> mappingFunction) {
/*  846 */     Objects.requireNonNull(mappingFunction);
/*  847 */     int pos = find(key);
/*  848 */     if (pos >= 0) return this.value[pos]; 
/*  849 */     if (!mappingFunction.containsKey(key)) return this.defRetValue; 
/*  850 */     short newValue = mappingFunction.getShort(key);
/*  851 */     insert(-pos - 1, key, newValue);
/*  852 */     return newValue;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public short computeShortIfPresent(K k, BiFunction<? super K, ? super Short, ? extends Short> remappingFunction) {
/*  858 */     Objects.requireNonNull(remappingFunction);
/*  859 */     int pos = find(k);
/*  860 */     if (pos < 0) return this.defRetValue; 
/*  861 */     Short newValue = remappingFunction.apply(k, Short.valueOf(this.value[pos]));
/*  862 */     if (newValue == null) {
/*  863 */       if (this.strategy.equals(k, null)) { removeNullEntry(); }
/*  864 */       else { removeEntry(pos); }
/*  865 */        return this.defRetValue;
/*      */     } 
/*  867 */     this.value[pos] = newValue.shortValue(); return newValue.shortValue();
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public short computeShort(K k, BiFunction<? super K, ? super Short, ? extends Short> remappingFunction) {
/*  873 */     Objects.requireNonNull(remappingFunction);
/*  874 */     int pos = find(k);
/*  875 */     Short newValue = remappingFunction.apply(k, (pos >= 0) ? Short.valueOf(this.value[pos]) : null);
/*  876 */     if (newValue == null) {
/*  877 */       if (pos >= 0)
/*  878 */         if (this.strategy.equals(k, null)) { removeNullEntry(); }
/*  879 */         else { removeEntry(pos); }
/*      */          
/*  881 */       return this.defRetValue;
/*      */     } 
/*  883 */     short newVal = newValue.shortValue();
/*  884 */     if (pos < 0) {
/*  885 */       insert(-pos - 1, k, newVal);
/*  886 */       return newVal;
/*      */     } 
/*  888 */     this.value[pos] = newVal; return newVal;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public short merge(K k, short v, BiFunction<? super Short, ? super Short, ? extends Short> remappingFunction) {
/*  894 */     Objects.requireNonNull(remappingFunction);
/*      */     
/*  896 */     int pos = find(k);
/*  897 */     if (pos < 0) {
/*  898 */       if (pos < 0) { insert(-pos - 1, k, v); }
/*  899 */       else { this.value[pos] = v; }
/*  900 */        return v;
/*      */     } 
/*  902 */     Short newValue = remappingFunction.apply(Short.valueOf(this.value[pos]), Short.valueOf(v));
/*  903 */     if (newValue == null) {
/*  904 */       if (this.strategy.equals(k, null)) { removeNullEntry(); }
/*  905 */       else { removeEntry(pos); }
/*  906 */        return this.defRetValue;
/*      */     } 
/*  908 */     this.value[pos] = newValue.shortValue(); return newValue.shortValue();
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
/*  919 */     if (this.size == 0)
/*  920 */       return;  this.size = 0;
/*  921 */     this.containsNullKey = false;
/*  922 */     Arrays.fill((Object[])this.key, (Object)null);
/*  923 */     this.first = this.last = -1;
/*      */   }
/*      */ 
/*      */   
/*      */   public int size() {
/*  928 */     return this.size;
/*      */   }
/*      */ 
/*      */   
/*      */   public boolean isEmpty() {
/*  933 */     return (this.size == 0);
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   final class MapEntry
/*      */     implements Object2ShortMap.Entry<K>, Map.Entry<K, Short>, ObjectShortPair<K>
/*      */   {
/*      */     int index;
/*      */ 
/*      */ 
/*      */     
/*      */     MapEntry(int index) {
/*  946 */       this.index = index;
/*      */     }
/*      */ 
/*      */     
/*      */     MapEntry() {}
/*      */ 
/*      */     
/*      */     public K getKey() {
/*  954 */       return Object2ShortLinkedOpenCustomHashMap.this.key[this.index];
/*      */     }
/*      */ 
/*      */     
/*      */     public K left() {
/*  959 */       return Object2ShortLinkedOpenCustomHashMap.this.key[this.index];
/*      */     }
/*      */ 
/*      */     
/*      */     public short getShortValue() {
/*  964 */       return Object2ShortLinkedOpenCustomHashMap.this.value[this.index];
/*      */     }
/*      */ 
/*      */     
/*      */     public short rightShort() {
/*  969 */       return Object2ShortLinkedOpenCustomHashMap.this.value[this.index];
/*      */     }
/*      */ 
/*      */     
/*      */     public short setValue(short v) {
/*  974 */       short oldValue = Object2ShortLinkedOpenCustomHashMap.this.value[this.index];
/*  975 */       Object2ShortLinkedOpenCustomHashMap.this.value[this.index] = v;
/*  976 */       return oldValue;
/*      */     }
/*      */ 
/*      */     
/*      */     public ObjectShortPair<K> right(short v) {
/*  981 */       Object2ShortLinkedOpenCustomHashMap.this.value[this.index] = v;
/*  982 */       return this;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     @Deprecated
/*      */     public Short getValue() {
/*  993 */       return Short.valueOf(Object2ShortLinkedOpenCustomHashMap.this.value[this.index]);
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     @Deprecated
/*      */     public Short setValue(Short v) {
/* 1004 */       return Short.valueOf(setValue(v.shortValue()));
/*      */     }
/*      */ 
/*      */ 
/*      */     
/*      */     public boolean equals(Object o) {
/* 1010 */       if (!(o instanceof Map.Entry)) return false; 
/* 1011 */       Map.Entry<K, Short> e = (Map.Entry<K, Short>)o;
/* 1012 */       return (Object2ShortLinkedOpenCustomHashMap.this.strategy.equals(Object2ShortLinkedOpenCustomHashMap.this.key[this.index], e.getKey()) && Object2ShortLinkedOpenCustomHashMap.this.value[this.index] == ((Short)e.getValue()).shortValue());
/*      */     }
/*      */ 
/*      */     
/*      */     public int hashCode() {
/* 1017 */       return Object2ShortLinkedOpenCustomHashMap.this.strategy.hashCode(Object2ShortLinkedOpenCustomHashMap.this.key[this.index]) ^ Object2ShortLinkedOpenCustomHashMap.this.value[this.index];
/*      */     }
/*      */ 
/*      */     
/*      */     public String toString() {
/* 1022 */       return (new StringBuilder()).append(Object2ShortLinkedOpenCustomHashMap.this.key[this.index]).append("=>").append(Object2ShortLinkedOpenCustomHashMap.this.value[this.index]).toString();
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
/* 1033 */     if (this.size == 0) {
/* 1034 */       this.first = this.last = -1;
/*      */       return;
/*      */     } 
/* 1037 */     if (this.first == i) {
/* 1038 */       this.first = (int)this.link[i];
/* 1039 */       if (0 <= this.first)
/*      */       {
/* 1041 */         this.link[this.first] = this.link[this.first] | 0xFFFFFFFF00000000L;
/*      */       }
/*      */       return;
/*      */     } 
/* 1045 */     if (this.last == i) {
/* 1046 */       this.last = (int)(this.link[i] >>> 32L);
/* 1047 */       if (0 <= this.last)
/*      */       {
/* 1049 */         this.link[this.last] = this.link[this.last] | 0xFFFFFFFFL;
/*      */       }
/*      */       return;
/*      */     } 
/* 1053 */     long linki = this.link[i];
/* 1054 */     int prev = (int)(linki >>> 32L);
/* 1055 */     int next = (int)linki;
/* 1056 */     this.link[prev] = this.link[prev] ^ (this.link[prev] ^ linki & 0xFFFFFFFFL) & 0xFFFFFFFFL;
/* 1057 */     this.link[next] = this.link[next] ^ (this.link[next] ^ linki & 0xFFFFFFFF00000000L) & 0xFFFFFFFF00000000L;
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
/* 1069 */     if (this.size == 1) {
/* 1070 */       this.first = this.last = d;
/*      */       
/* 1072 */       this.link[d] = -1L;
/*      */       return;
/*      */     } 
/* 1075 */     if (this.first == s) {
/* 1076 */       this.first = d;
/* 1077 */       this.link[(int)this.link[s]] = this.link[(int)this.link[s]] ^ (this.link[(int)this.link[s]] ^ (d & 0xFFFFFFFFL) << 32L) & 0xFFFFFFFF00000000L;
/* 1078 */       this.link[d] = this.link[s];
/*      */       return;
/*      */     } 
/* 1081 */     if (this.last == s) {
/* 1082 */       this.last = d;
/* 1083 */       this.link[(int)(this.link[s] >>> 32L)] = this.link[(int)(this.link[s] >>> 32L)] ^ (this.link[(int)(this.link[s] >>> 32L)] ^ d & 0xFFFFFFFFL) & 0xFFFFFFFFL;
/* 1084 */       this.link[d] = this.link[s];
/*      */       return;
/*      */     } 
/* 1087 */     long links = this.link[s];
/* 1088 */     int prev = (int)(links >>> 32L);
/* 1089 */     int next = (int)links;
/* 1090 */     this.link[prev] = this.link[prev] ^ (this.link[prev] ^ d & 0xFFFFFFFFL) & 0xFFFFFFFFL;
/* 1091 */     this.link[next] = this.link[next] ^ (this.link[next] ^ (d & 0xFFFFFFFFL) << 32L) & 0xFFFFFFFF00000000L;
/* 1092 */     this.link[d] = links;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public K firstKey() {
/* 1102 */     if (this.size == 0) throw new NoSuchElementException(); 
/* 1103 */     return this.key[this.first];
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public K lastKey() {
/* 1113 */     if (this.size == 0) throw new NoSuchElementException(); 
/* 1114 */     return this.key[this.last];
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Object2ShortSortedMap<K> tailMap(K from) {
/* 1124 */     throw new UnsupportedOperationException();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Object2ShortSortedMap<K> headMap(K to) {
/* 1134 */     throw new UnsupportedOperationException();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Object2ShortSortedMap<K> subMap(K from, K to) {
/* 1144 */     throw new UnsupportedOperationException();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Comparator<? super K> comparator() {
/* 1154 */     return null;
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
/* 1169 */     int prev = -1;
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 1174 */     int next = -1;
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 1179 */     int curr = -1;
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 1184 */     int index = -1;
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     protected MapIterator() {
/* 1190 */       this.next = Object2ShortLinkedOpenCustomHashMap.this.first;
/* 1191 */       this.index = 0;
/*      */     }
/*      */     
/*      */     private MapIterator(K from) {
/* 1195 */       if (Object2ShortLinkedOpenCustomHashMap.this.strategy.equals(from, null)) {
/* 1196 */         if (Object2ShortLinkedOpenCustomHashMap.this.containsNullKey) {
/* 1197 */           this.next = (int)Object2ShortLinkedOpenCustomHashMap.this.link[Object2ShortLinkedOpenCustomHashMap.this.n];
/* 1198 */           this.prev = Object2ShortLinkedOpenCustomHashMap.this.n; return;
/*      */         } 
/* 1200 */         throw new NoSuchElementException("The key " + from + " does not belong to this map.");
/*      */       } 
/* 1202 */       if (Object2ShortLinkedOpenCustomHashMap.this.strategy.equals(Object2ShortLinkedOpenCustomHashMap.this.key[Object2ShortLinkedOpenCustomHashMap.this.last], from)) {
/* 1203 */         this.prev = Object2ShortLinkedOpenCustomHashMap.this.last;
/* 1204 */         this.index = Object2ShortLinkedOpenCustomHashMap.this.size;
/*      */         
/*      */         return;
/*      */       } 
/* 1208 */       int pos = HashCommon.mix(Object2ShortLinkedOpenCustomHashMap.this.strategy.hashCode(from)) & Object2ShortLinkedOpenCustomHashMap.this.mask;
/*      */       
/* 1210 */       while (Object2ShortLinkedOpenCustomHashMap.this.key[pos] != null) {
/* 1211 */         if (Object2ShortLinkedOpenCustomHashMap.this.strategy.equals(Object2ShortLinkedOpenCustomHashMap.this.key[pos], from)) {
/*      */           
/* 1213 */           this.next = (int)Object2ShortLinkedOpenCustomHashMap.this.link[pos];
/* 1214 */           this.prev = pos;
/*      */           return;
/*      */         } 
/* 1217 */         pos = pos + 1 & Object2ShortLinkedOpenCustomHashMap.this.mask;
/*      */       } 
/* 1219 */       throw new NoSuchElementException("The key " + from + " does not belong to this map.");
/*      */     }
/*      */     
/*      */     public boolean hasNext() {
/* 1223 */       return (this.next != -1);
/*      */     }
/*      */     
/*      */     public boolean hasPrevious() {
/* 1227 */       return (this.prev != -1);
/*      */     }
/*      */     
/*      */     private final void ensureIndexKnown() {
/* 1231 */       if (this.index >= 0)
/* 1232 */         return;  if (this.prev == -1) {
/* 1233 */         this.index = 0;
/*      */         return;
/*      */       } 
/* 1236 */       if (this.next == -1) {
/* 1237 */         this.index = Object2ShortLinkedOpenCustomHashMap.this.size;
/*      */         return;
/*      */       } 
/* 1240 */       int pos = Object2ShortLinkedOpenCustomHashMap.this.first;
/* 1241 */       this.index = 1;
/* 1242 */       while (pos != this.prev) {
/* 1243 */         pos = (int)Object2ShortLinkedOpenCustomHashMap.this.link[pos];
/* 1244 */         this.index++;
/*      */       } 
/*      */     }
/*      */     
/*      */     public int nextIndex() {
/* 1249 */       ensureIndexKnown();
/* 1250 */       return this.index;
/*      */     }
/*      */     
/*      */     public int previousIndex() {
/* 1254 */       ensureIndexKnown();
/* 1255 */       return this.index - 1;
/*      */     }
/*      */     
/*      */     public int nextEntry() {
/* 1259 */       if (!hasNext()) throw new NoSuchElementException(); 
/* 1260 */       this.curr = this.next;
/* 1261 */       this.next = (int)Object2ShortLinkedOpenCustomHashMap.this.link[this.curr];
/* 1262 */       this.prev = this.curr;
/* 1263 */       if (this.index >= 0) this.index++; 
/* 1264 */       return this.curr;
/*      */     }
/*      */     
/*      */     public int previousEntry() {
/* 1268 */       if (!hasPrevious()) throw new NoSuchElementException(); 
/* 1269 */       this.curr = this.prev;
/* 1270 */       this.prev = (int)(Object2ShortLinkedOpenCustomHashMap.this.link[this.curr] >>> 32L);
/* 1271 */       this.next = this.curr;
/* 1272 */       if (this.index >= 0) this.index--; 
/* 1273 */       return this.curr;
/*      */     }
/*      */     
/*      */     public void forEachRemaining(ConsumerType action) {
/* 1277 */       while (hasNext()) {
/* 1278 */         this.curr = this.next;
/* 1279 */         this.next = (int)Object2ShortLinkedOpenCustomHashMap.this.link[this.curr];
/* 1280 */         this.prev = this.curr;
/* 1281 */         if (this.index >= 0) this.index++; 
/* 1282 */         acceptOnIndex(action, this.curr);
/*      */       } 
/*      */     }
/*      */     
/*      */     public void remove() {
/* 1287 */       ensureIndexKnown();
/* 1288 */       if (this.curr == -1) throw new IllegalStateException(); 
/* 1289 */       if (this.curr == this.prev)
/*      */       
/*      */       { 
/* 1292 */         this.index--;
/* 1293 */         this.prev = (int)(Object2ShortLinkedOpenCustomHashMap.this.link[this.curr] >>> 32L); }
/* 1294 */       else { this.next = (int)Object2ShortLinkedOpenCustomHashMap.this.link[this.curr]; }
/* 1295 */        Object2ShortLinkedOpenCustomHashMap.this.size--;
/*      */ 
/*      */       
/* 1298 */       if (this.prev == -1) { Object2ShortLinkedOpenCustomHashMap.this.first = this.next; }
/* 1299 */       else { Object2ShortLinkedOpenCustomHashMap.this.link[this.prev] = Object2ShortLinkedOpenCustomHashMap.this.link[this.prev] ^ (Object2ShortLinkedOpenCustomHashMap.this.link[this.prev] ^ this.next & 0xFFFFFFFFL) & 0xFFFFFFFFL; }
/* 1300 */        if (this.next == -1) { Object2ShortLinkedOpenCustomHashMap.this.last = this.prev; }
/* 1301 */       else { Object2ShortLinkedOpenCustomHashMap.this.link[this.next] = Object2ShortLinkedOpenCustomHashMap.this.link[this.next] ^ (Object2ShortLinkedOpenCustomHashMap.this.link[this.next] ^ (this.prev & 0xFFFFFFFFL) << 32L) & 0xFFFFFFFF00000000L; }
/* 1302 */        int pos = this.curr;
/* 1303 */       this.curr = -1;
/* 1304 */       if (pos == Object2ShortLinkedOpenCustomHashMap.this.n) {
/* 1305 */         Object2ShortLinkedOpenCustomHashMap.this.containsNullKey = false;
/* 1306 */         Object2ShortLinkedOpenCustomHashMap.this.key[Object2ShortLinkedOpenCustomHashMap.this.n] = null;
/*      */       } else {
/*      */         
/* 1309 */         K[] key = Object2ShortLinkedOpenCustomHashMap.this.key; while (true) {
/*      */           K curr;
/*      */           int last;
/* 1312 */           pos = (last = pos) + 1 & Object2ShortLinkedOpenCustomHashMap.this.mask;
/*      */           while (true) {
/* 1314 */             if ((curr = key[pos]) == null) {
/* 1315 */               key[last] = null;
/*      */               return;
/*      */             } 
/* 1318 */             int slot = HashCommon.mix(Object2ShortLinkedOpenCustomHashMap.this.strategy.hashCode(curr)) & Object2ShortLinkedOpenCustomHashMap.this.mask;
/* 1319 */             if ((last <= pos) ? (last >= slot || slot > pos) : (last >= slot && slot > pos))
/* 1320 */               break;  pos = pos + 1 & Object2ShortLinkedOpenCustomHashMap.this.mask;
/*      */           } 
/* 1322 */           key[last] = curr;
/* 1323 */           Object2ShortLinkedOpenCustomHashMap.this.value[last] = Object2ShortLinkedOpenCustomHashMap.this.value[pos];
/* 1324 */           if (this.next == pos) this.next = last; 
/* 1325 */           if (this.prev == pos) this.prev = last; 
/* 1326 */           Object2ShortLinkedOpenCustomHashMap.this.fixPointers(pos, last);
/*      */         } 
/*      */       } 
/*      */     }
/*      */     
/*      */     public int skip(int n) {
/* 1332 */       int i = n;
/* 1333 */       for (; i-- != 0 && hasNext(); nextEntry());
/* 1334 */       return n - i - 1;
/*      */     }
/*      */     
/*      */     public int back(int n) {
/* 1338 */       int i = n;
/* 1339 */       for (; i-- != 0 && hasPrevious(); previousEntry());
/* 1340 */       return n - i - 1;
/*      */     }
/*      */     
/*      */     public void set(Object2ShortMap.Entry<K> ok) {
/* 1344 */       throw new UnsupportedOperationException();
/*      */     }
/*      */     
/*      */     public void add(Object2ShortMap.Entry<K> ok) {
/* 1348 */       throw new UnsupportedOperationException();
/*      */     }
/*      */     
/*      */     abstract void acceptOnIndex(ConsumerType param1ConsumerType, int param1Int); }
/*      */   
/*      */   private final class EntryIterator extends MapIterator<Consumer<? super Object2ShortMap.Entry<K>>> implements ObjectListIterator<Object2ShortMap.Entry<K>> {
/*      */     private Object2ShortLinkedOpenCustomHashMap<K>.MapEntry entry;
/*      */     
/*      */     public EntryIterator() {}
/*      */     
/*      */     public EntryIterator(K from) {
/* 1359 */       super(from);
/*      */     }
/*      */ 
/*      */ 
/*      */     
/*      */     final void acceptOnIndex(Consumer<? super Object2ShortMap.Entry<K>> action, int index) {
/* 1365 */       action.accept(new Object2ShortLinkedOpenCustomHashMap.MapEntry(index));
/*      */     }
/*      */ 
/*      */     
/*      */     public Object2ShortLinkedOpenCustomHashMap<K>.MapEntry next() {
/* 1370 */       return this.entry = new Object2ShortLinkedOpenCustomHashMap.MapEntry(nextEntry());
/*      */     }
/*      */ 
/*      */     
/*      */     public Object2ShortLinkedOpenCustomHashMap<K>.MapEntry previous() {
/* 1375 */       return this.entry = new Object2ShortLinkedOpenCustomHashMap.MapEntry(previousEntry());
/*      */     }
/*      */ 
/*      */     
/*      */     public void remove() {
/* 1380 */       super.remove();
/* 1381 */       this.entry.index = -1;
/*      */     }
/*      */   }
/*      */   
/*      */   private final class FastEntryIterator extends MapIterator<Consumer<? super Object2ShortMap.Entry<K>>> implements ObjectListIterator<Object2ShortMap.Entry<K>> {
/* 1386 */     final Object2ShortLinkedOpenCustomHashMap<K>.MapEntry entry = new Object2ShortLinkedOpenCustomHashMap.MapEntry();
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public FastEntryIterator(K from) {
/* 1392 */       super(from);
/*      */     }
/*      */ 
/*      */ 
/*      */     
/*      */     final void acceptOnIndex(Consumer<? super Object2ShortMap.Entry<K>> action, int index) {
/* 1398 */       this.entry.index = index;
/* 1399 */       action.accept(this.entry);
/*      */     }
/*      */ 
/*      */     
/*      */     public Object2ShortLinkedOpenCustomHashMap<K>.MapEntry next() {
/* 1404 */       this.entry.index = nextEntry();
/* 1405 */       return this.entry;
/*      */     }
/*      */ 
/*      */     
/*      */     public Object2ShortLinkedOpenCustomHashMap<K>.MapEntry previous() {
/* 1410 */       this.entry.index = previousEntry();
/* 1411 */       return this.entry;
/*      */     }
/*      */     
/*      */     public FastEntryIterator() {} }
/*      */   
/*      */   private final class MapEntrySet extends AbstractObjectSortedSet<Object2ShortMap.Entry<K>> implements Object2ShortSortedMap.FastSortedEntrySet<K> {
/*      */     private static final int SPLITERATOR_CHARACTERISTICS = 81;
/*      */     
/*      */     public ObjectBidirectionalIterator<Object2ShortMap.Entry<K>> iterator() {
/* 1420 */       return new Object2ShortLinkedOpenCustomHashMap.EntryIterator();
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
/*      */     public ObjectSpliterator<Object2ShortMap.Entry<K>> spliterator() {
/* 1438 */       return ObjectSpliterators.asSpliterator(iterator(), Size64.sizeOf(Object2ShortLinkedOpenCustomHashMap.this), 81);
/*      */     }
/*      */ 
/*      */     
/*      */     public Comparator<? super Object2ShortMap.Entry<K>> comparator() {
/* 1443 */       return null;
/*      */     }
/*      */ 
/*      */     
/*      */     public ObjectSortedSet<Object2ShortMap.Entry<K>> subSet(Object2ShortMap.Entry<K> fromElement, Object2ShortMap.Entry<K> toElement) {
/* 1448 */       throw new UnsupportedOperationException();
/*      */     }
/*      */ 
/*      */     
/*      */     public ObjectSortedSet<Object2ShortMap.Entry<K>> headSet(Object2ShortMap.Entry<K> toElement) {
/* 1453 */       throw new UnsupportedOperationException();
/*      */     }
/*      */ 
/*      */     
/*      */     public ObjectSortedSet<Object2ShortMap.Entry<K>> tailSet(Object2ShortMap.Entry<K> fromElement) {
/* 1458 */       throw new UnsupportedOperationException();
/*      */     }
/*      */ 
/*      */     
/*      */     public Object2ShortMap.Entry<K> first() {
/* 1463 */       if (Object2ShortLinkedOpenCustomHashMap.this.size == 0) throw new NoSuchElementException(); 
/* 1464 */       return new Object2ShortLinkedOpenCustomHashMap.MapEntry(Object2ShortLinkedOpenCustomHashMap.this.first);
/*      */     }
/*      */ 
/*      */     
/*      */     public Object2ShortMap.Entry<K> last() {
/* 1469 */       if (Object2ShortLinkedOpenCustomHashMap.this.size == 0) throw new NoSuchElementException(); 
/* 1470 */       return new Object2ShortLinkedOpenCustomHashMap.MapEntry(Object2ShortLinkedOpenCustomHashMap.this.last);
/*      */     }
/*      */ 
/*      */ 
/*      */     
/*      */     public boolean contains(Object o) {
/* 1476 */       if (!(o instanceof Map.Entry)) return false; 
/* 1477 */       Map.Entry<?, ?> e = (Map.Entry<?, ?>)o;
/* 1478 */       if (e.getValue() == null || !(e.getValue() instanceof Short)) return false; 
/* 1479 */       K k = (K)e.getKey();
/* 1480 */       short v = ((Short)e.getValue()).shortValue();
/* 1481 */       if (Object2ShortLinkedOpenCustomHashMap.this.strategy.equals(k, null)) return (Object2ShortLinkedOpenCustomHashMap.this.containsNullKey && Object2ShortLinkedOpenCustomHashMap.this.value[Object2ShortLinkedOpenCustomHashMap.this.n] == v);
/*      */       
/* 1483 */       K[] key = Object2ShortLinkedOpenCustomHashMap.this.key;
/*      */       K curr;
/*      */       int pos;
/* 1486 */       if ((curr = key[pos = HashCommon.mix(Object2ShortLinkedOpenCustomHashMap.this.strategy.hashCode(k)) & Object2ShortLinkedOpenCustomHashMap.this.mask]) == null) return false; 
/* 1487 */       if (Object2ShortLinkedOpenCustomHashMap.this.strategy.equals(k, curr)) return (Object2ShortLinkedOpenCustomHashMap.this.value[pos] == v);
/*      */       
/*      */       while (true) {
/* 1490 */         if ((curr = key[pos = pos + 1 & Object2ShortLinkedOpenCustomHashMap.this.mask]) == null) return false; 
/* 1491 */         if (Object2ShortLinkedOpenCustomHashMap.this.strategy.equals(k, curr)) return (Object2ShortLinkedOpenCustomHashMap.this.value[pos] == v);
/*      */       
/*      */       } 
/*      */     }
/*      */ 
/*      */     
/*      */     public boolean remove(Object o) {
/* 1498 */       if (!(o instanceof Map.Entry)) return false; 
/* 1499 */       Map.Entry<?, ?> e = (Map.Entry<?, ?>)o;
/* 1500 */       if (e.getValue() == null || !(e.getValue() instanceof Short)) return false; 
/* 1501 */       K k = (K)e.getKey();
/* 1502 */       short v = ((Short)e.getValue()).shortValue();
/* 1503 */       if (Object2ShortLinkedOpenCustomHashMap.this.strategy.equals(k, null)) {
/* 1504 */         if (Object2ShortLinkedOpenCustomHashMap.this.containsNullKey && Object2ShortLinkedOpenCustomHashMap.this.value[Object2ShortLinkedOpenCustomHashMap.this.n] == v) {
/* 1505 */           Object2ShortLinkedOpenCustomHashMap.this.removeNullEntry();
/* 1506 */           return true;
/*      */         } 
/* 1508 */         return false;
/*      */       } 
/*      */       
/* 1511 */       K[] key = Object2ShortLinkedOpenCustomHashMap.this.key;
/*      */       K curr;
/*      */       int pos;
/* 1514 */       if ((curr = key[pos = HashCommon.mix(Object2ShortLinkedOpenCustomHashMap.this.strategy.hashCode(k)) & Object2ShortLinkedOpenCustomHashMap.this.mask]) == null) return false; 
/* 1515 */       if (Object2ShortLinkedOpenCustomHashMap.this.strategy.equals(curr, k)) {
/* 1516 */         if (Object2ShortLinkedOpenCustomHashMap.this.value[pos] == v) {
/* 1517 */           Object2ShortLinkedOpenCustomHashMap.this.removeEntry(pos);
/* 1518 */           return true;
/*      */         } 
/* 1520 */         return false;
/*      */       } 
/*      */       while (true) {
/* 1523 */         if ((curr = key[pos = pos + 1 & Object2ShortLinkedOpenCustomHashMap.this.mask]) == null) return false; 
/* 1524 */         if (Object2ShortLinkedOpenCustomHashMap.this.strategy.equals(curr, k) && 
/* 1525 */           Object2ShortLinkedOpenCustomHashMap.this.value[pos] == v) {
/* 1526 */           Object2ShortLinkedOpenCustomHashMap.this.removeEntry(pos);
/* 1527 */           return true;
/*      */         } 
/*      */       } 
/*      */     }
/*      */ 
/*      */ 
/*      */     
/*      */     public int size() {
/* 1535 */       return Object2ShortLinkedOpenCustomHashMap.this.size;
/*      */     }
/*      */ 
/*      */     
/*      */     public void clear() {
/* 1540 */       Object2ShortLinkedOpenCustomHashMap.this.clear();
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
/*      */     public ObjectListIterator<Object2ShortMap.Entry<K>> iterator(Object2ShortMap.Entry<K> from) {
/* 1553 */       return new Object2ShortLinkedOpenCustomHashMap.EntryIterator(from.getKey());
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public ObjectListIterator<Object2ShortMap.Entry<K>> fastIterator() {
/* 1564 */       return new Object2ShortLinkedOpenCustomHashMap.FastEntryIterator();
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
/*      */     public ObjectListIterator<Object2ShortMap.Entry<K>> fastIterator(Object2ShortMap.Entry<K> from) {
/* 1577 */       return new Object2ShortLinkedOpenCustomHashMap.FastEntryIterator(from.getKey());
/*      */     }
/*      */ 
/*      */ 
/*      */     
/*      */     public void forEach(Consumer<? super Object2ShortMap.Entry<K>> consumer) {
/* 1583 */       for (int i = Object2ShortLinkedOpenCustomHashMap.this.size, next = Object2ShortLinkedOpenCustomHashMap.this.first; i-- != 0; ) {
/* 1584 */         int curr = next;
/* 1585 */         next = (int)Object2ShortLinkedOpenCustomHashMap.this.link[curr];
/* 1586 */         consumer.accept(new Object2ShortLinkedOpenCustomHashMap.MapEntry(curr));
/*      */       } 
/*      */     }
/*      */ 
/*      */ 
/*      */     
/*      */     public void fastForEach(Consumer<? super Object2ShortMap.Entry<K>> consumer) {
/* 1593 */       Object2ShortLinkedOpenCustomHashMap<K>.MapEntry entry = new Object2ShortLinkedOpenCustomHashMap.MapEntry();
/* 1594 */       for (int i = Object2ShortLinkedOpenCustomHashMap.this.size, next = Object2ShortLinkedOpenCustomHashMap.this.first; i-- != 0; ) {
/* 1595 */         entry.index = next;
/* 1596 */         next = (int)Object2ShortLinkedOpenCustomHashMap.this.link[next];
/* 1597 */         consumer.accept(entry);
/*      */       } 
/*      */     }
/*      */   }
/*      */ 
/*      */   
/*      */   public Object2ShortSortedMap.FastSortedEntrySet<K> object2ShortEntrySet() {
/* 1604 */     if (this.entries == null) this.entries = new MapEntrySet(); 
/* 1605 */     return this.entries;
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
/* 1618 */       super(k);
/*      */     }
/*      */ 
/*      */     
/*      */     public K previous() {
/* 1623 */       return Object2ShortLinkedOpenCustomHashMap.this.key[previousEntry()];
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
/* 1635 */       action.accept(Object2ShortLinkedOpenCustomHashMap.this.key[index]);
/*      */     }
/*      */ 
/*      */     
/*      */     public K next() {
/* 1640 */       return Object2ShortLinkedOpenCustomHashMap.this.key[nextEntry()];
/*      */     } }
/*      */   
/*      */   private final class KeySet extends AbstractObjectSortedSet<K> {
/*      */     private static final int SPLITERATOR_CHARACTERISTICS = 81;
/*      */     
/*      */     private KeySet() {}
/*      */     
/*      */     public ObjectListIterator<K> iterator(K from) {
/* 1649 */       return new Object2ShortLinkedOpenCustomHashMap.KeyIterator(from);
/*      */     }
/*      */ 
/*      */     
/*      */     public ObjectListIterator<K> iterator() {
/* 1654 */       return new Object2ShortLinkedOpenCustomHashMap.KeyIterator();
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public ObjectSpliterator<K> spliterator() {
/* 1664 */       return ObjectSpliterators.asSpliterator(iterator(), Size64.sizeOf(Object2ShortLinkedOpenCustomHashMap.this), 81);
/*      */     }
/*      */ 
/*      */ 
/*      */     
/*      */     public void forEach(Consumer<? super K> consumer) {
/* 1670 */       for (int i = Object2ShortLinkedOpenCustomHashMap.this.size, next = Object2ShortLinkedOpenCustomHashMap.this.first; i-- != 0; ) {
/* 1671 */         int curr = next;
/* 1672 */         next = (int)Object2ShortLinkedOpenCustomHashMap.this.link[curr];
/* 1673 */         consumer.accept(Object2ShortLinkedOpenCustomHashMap.this.key[curr]);
/*      */       } 
/*      */     }
/*      */ 
/*      */     
/*      */     public int size() {
/* 1679 */       return Object2ShortLinkedOpenCustomHashMap.this.size;
/*      */     }
/*      */ 
/*      */     
/*      */     public boolean contains(Object k) {
/* 1684 */       return Object2ShortLinkedOpenCustomHashMap.this.containsKey(k);
/*      */     }
/*      */ 
/*      */     
/*      */     public boolean remove(Object k) {
/* 1689 */       int oldSize = Object2ShortLinkedOpenCustomHashMap.this.size;
/* 1690 */       Object2ShortLinkedOpenCustomHashMap.this.removeShort(k);
/* 1691 */       return (Object2ShortLinkedOpenCustomHashMap.this.size != oldSize);
/*      */     }
/*      */ 
/*      */     
/*      */     public void clear() {
/* 1696 */       Object2ShortLinkedOpenCustomHashMap.this.clear();
/*      */     }
/*      */ 
/*      */     
/*      */     public K first() {
/* 1701 */       if (Object2ShortLinkedOpenCustomHashMap.this.size == 0) throw new NoSuchElementException(); 
/* 1702 */       return Object2ShortLinkedOpenCustomHashMap.this.key[Object2ShortLinkedOpenCustomHashMap.this.first];
/*      */     }
/*      */ 
/*      */     
/*      */     public K last() {
/* 1707 */       if (Object2ShortLinkedOpenCustomHashMap.this.size == 0) throw new NoSuchElementException(); 
/* 1708 */       return Object2ShortLinkedOpenCustomHashMap.this.key[Object2ShortLinkedOpenCustomHashMap.this.last];
/*      */     }
/*      */ 
/*      */     
/*      */     public Comparator<? super K> comparator() {
/* 1713 */       return null;
/*      */     }
/*      */ 
/*      */     
/*      */     public ObjectSortedSet<K> tailSet(K from) {
/* 1718 */       throw new UnsupportedOperationException();
/*      */     }
/*      */ 
/*      */     
/*      */     public ObjectSortedSet<K> headSet(K to) {
/* 1723 */       throw new UnsupportedOperationException();
/*      */     }
/*      */ 
/*      */     
/*      */     public ObjectSortedSet<K> subSet(K from, K to) {
/* 1728 */       throw new UnsupportedOperationException();
/*      */     }
/*      */   }
/*      */ 
/*      */   
/*      */   public ObjectSortedSet<K> keySet() {
/* 1734 */     if (this.keys == null) this.keys = new KeySet(); 
/* 1735 */     return this.keys;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private final class ValueIterator
/*      */     extends MapIterator<ShortConsumer>
/*      */     implements ShortListIterator
/*      */   {
/*      */     public short previousShort() {
/* 1749 */       return Object2ShortLinkedOpenCustomHashMap.this.value[previousEntry()];
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
/*      */     final void acceptOnIndex(ShortConsumer action, int index) {
/* 1761 */       action.accept(Object2ShortLinkedOpenCustomHashMap.this.value[index]);
/*      */     }
/*      */ 
/*      */     
/*      */     public short nextShort() {
/* 1766 */       return Object2ShortLinkedOpenCustomHashMap.this.value[nextEntry()];
/*      */     }
/*      */   }
/*      */ 
/*      */   
/*      */   public ShortCollection values() {
/* 1772 */     if (this.values == null) this.values = (ShortCollection)new AbstractShortCollection()
/*      */         {
/*      */           private static final int SPLITERATOR_CHARACTERISTICS = 336;
/*      */           
/*      */           public ShortIterator iterator() {
/* 1777 */             return (ShortIterator)new Object2ShortLinkedOpenCustomHashMap.ValueIterator();
/*      */           }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */           
/*      */           public ShortSpliterator spliterator() {
/* 1787 */             return ShortSpliterators.asSpliterator(iterator(), Size64.sizeOf(Object2ShortLinkedOpenCustomHashMap.this), 336);
/*      */           }
/*      */ 
/*      */ 
/*      */           
/*      */           public void forEach(ShortConsumer consumer) {
/* 1793 */             for (int i = Object2ShortLinkedOpenCustomHashMap.this.size, next = Object2ShortLinkedOpenCustomHashMap.this.first; i-- != 0; ) {
/* 1794 */               int curr = next;
/* 1795 */               next = (int)Object2ShortLinkedOpenCustomHashMap.this.link[curr];
/* 1796 */               consumer.accept(Object2ShortLinkedOpenCustomHashMap.this.value[curr]);
/*      */             } 
/*      */           }
/*      */ 
/*      */           
/*      */           public int size() {
/* 1802 */             return Object2ShortLinkedOpenCustomHashMap.this.size;
/*      */           }
/*      */ 
/*      */           
/*      */           public boolean contains(short v) {
/* 1807 */             return Object2ShortLinkedOpenCustomHashMap.this.containsValue(v);
/*      */           }
/*      */ 
/*      */           
/*      */           public void clear() {
/* 1812 */             Object2ShortLinkedOpenCustomHashMap.this.clear();
/*      */           }
/*      */         }; 
/* 1815 */     return this.values;
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
/* 1832 */     return trim(this.size);
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
/* 1854 */     int l = HashCommon.nextPowerOfTwo((int)Math.ceil((n / this.f)));
/* 1855 */     if (l >= this.n || this.size > HashCommon.maxFill(l, this.f)) return true; 
/*      */     try {
/* 1857 */       rehash(l);
/* 1858 */     } catch (OutOfMemoryError cantDoIt) {
/* 1859 */       return false;
/*      */     } 
/* 1861 */     return true;
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
/* 1876 */     K[] key = this.key;
/* 1877 */     short[] value = this.value;
/* 1878 */     int mask = newN - 1;
/* 1879 */     K[] newKey = (K[])new Object[newN + 1];
/* 1880 */     short[] newValue = new short[newN + 1];
/* 1881 */     int i = this.first, prev = -1, newPrev = -1;
/* 1882 */     long[] link = this.link;
/* 1883 */     long[] newLink = new long[newN + 1];
/* 1884 */     this.first = -1;
/* 1885 */     for (int j = this.size; j-- != 0; ) {
/* 1886 */       int pos; if (this.strategy.equals(key[i], null)) { pos = newN; }
/*      */       else
/* 1888 */       { pos = HashCommon.mix(this.strategy.hashCode(key[i])) & mask;
/* 1889 */         for (; newKey[pos] != null; pos = pos + 1 & mask); }
/*      */       
/* 1891 */       newKey[pos] = key[i];
/* 1892 */       newValue[pos] = value[i];
/* 1893 */       if (prev != -1) {
/* 1894 */         newLink[newPrev] = newLink[newPrev] ^ (newLink[newPrev] ^ pos & 0xFFFFFFFFL) & 0xFFFFFFFFL;
/* 1895 */         newLink[pos] = newLink[pos] ^ (newLink[pos] ^ (newPrev & 0xFFFFFFFFL) << 32L) & 0xFFFFFFFF00000000L;
/* 1896 */         newPrev = pos;
/*      */       } else {
/* 1898 */         newPrev = this.first = pos;
/*      */         
/* 1900 */         newLink[pos] = -1L;
/*      */       } 
/* 1902 */       int t = i;
/* 1903 */       i = (int)link[i];
/* 1904 */       prev = t;
/*      */     } 
/* 1906 */     this.link = newLink;
/* 1907 */     this.last = newPrev;
/* 1908 */     if (newPrev != -1)
/*      */     {
/* 1910 */       newLink[newPrev] = newLink[newPrev] | 0xFFFFFFFFL; } 
/* 1911 */     this.n = newN;
/* 1912 */     this.mask = mask;
/* 1913 */     this.maxFill = HashCommon.maxFill(this.n, this.f);
/* 1914 */     this.key = newKey;
/* 1915 */     this.value = newValue;
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
/*      */   public Object2ShortLinkedOpenCustomHashMap<K> clone() {
/*      */     Object2ShortLinkedOpenCustomHashMap<K> c;
/*      */     try {
/* 1932 */       c = (Object2ShortLinkedOpenCustomHashMap<K>)super.clone();
/* 1933 */     } catch (CloneNotSupportedException cantHappen) {
/* 1934 */       throw new InternalError();
/*      */     } 
/* 1936 */     c.keys = null;
/* 1937 */     c.values = null;
/* 1938 */     c.entries = null;
/* 1939 */     c.containsNullKey = this.containsNullKey;
/* 1940 */     c.key = (K[])this.key.clone();
/* 1941 */     c.value = (short[])this.value.clone();
/* 1942 */     c.link = (long[])this.link.clone();
/* 1943 */     c.strategy = this.strategy;
/* 1944 */     return c;
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
/* 1958 */     int h = 0;
/* 1959 */     for (int j = realSize(), i = 0, t = 0; j-- != 0; ) {
/* 1960 */       for (; this.key[i] == null; i++);
/* 1961 */       if (this != this.key[i]) t = this.strategy.hashCode(this.key[i]); 
/* 1962 */       t ^= this.value[i];
/* 1963 */       h += t;
/* 1964 */       i++;
/*      */     } 
/*      */     
/* 1967 */     if (this.containsNullKey) h += this.value[this.n]; 
/* 1968 */     return h;
/*      */   }
/*      */   
/*      */   private void writeObject(ObjectOutputStream s) throws IOException {
/* 1972 */     K[] key = this.key;
/* 1973 */     short[] value = this.value;
/* 1974 */     EntryIterator i = new EntryIterator();
/* 1975 */     s.defaultWriteObject();
/* 1976 */     for (int j = this.size; j-- != 0; ) {
/* 1977 */       int e = i.nextEntry();
/* 1978 */       s.writeObject(key[e]);
/* 1979 */       s.writeShort(value[e]);
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   private void readObject(ObjectInputStream s) throws IOException, ClassNotFoundException {
/* 1985 */     s.defaultReadObject();
/* 1986 */     this.n = HashCommon.arraySize(this.size, this.f);
/* 1987 */     this.maxFill = HashCommon.maxFill(this.n, this.f);
/* 1988 */     this.mask = this.n - 1;
/* 1989 */     K[] key = this.key = (K[])new Object[this.n + 1];
/* 1990 */     short[] value = this.value = new short[this.n + 1];
/* 1991 */     long[] link = this.link = new long[this.n + 1];
/* 1992 */     int prev = -1;
/* 1993 */     this.first = this.last = -1;
/*      */ 
/*      */     
/* 1996 */     for (int i = this.size; i-- != 0; ) {
/* 1997 */       int pos; K k = (K)s.readObject();
/* 1998 */       short v = s.readShort();
/* 1999 */       if (this.strategy.equals(k, null)) {
/* 2000 */         pos = this.n;
/* 2001 */         this.containsNullKey = true;
/*      */       } else {
/* 2003 */         pos = HashCommon.mix(this.strategy.hashCode(k)) & this.mask;
/* 2004 */         for (; key[pos] != null; pos = pos + 1 & this.mask);
/*      */       } 
/* 2006 */       key[pos] = k;
/* 2007 */       value[pos] = v;
/* 2008 */       if (this.first != -1) {
/* 2009 */         link[prev] = link[prev] ^ (link[prev] ^ pos & 0xFFFFFFFFL) & 0xFFFFFFFFL;
/* 2010 */         link[pos] = link[pos] ^ (link[pos] ^ (prev & 0xFFFFFFFFL) << 32L) & 0xFFFFFFFF00000000L;
/* 2011 */         prev = pos; continue;
/*      */       } 
/* 2013 */       prev = this.first = pos;
/*      */       
/* 2015 */       link[pos] = link[pos] | 0xFFFFFFFF00000000L;
/*      */     } 
/*      */     
/* 2018 */     this.last = prev;
/* 2019 */     if (prev != -1)
/*      */     {
/* 2021 */       link[prev] = link[prev] | 0xFFFFFFFFL;
/*      */     }
/*      */   }
/*      */   
/*      */   private void checkTable() {}
/*      */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\i\\unimi\dsi\fastutil\objects\Object2ShortLinkedOpenCustomHashMap.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */