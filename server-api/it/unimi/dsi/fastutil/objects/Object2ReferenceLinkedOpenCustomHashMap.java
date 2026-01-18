/*      */ package it.unimi.dsi.fastutil.objects;
/*      */ 
/*      */ import it.unimi.dsi.fastutil.Hash;
/*      */ import it.unimi.dsi.fastutil.HashCommon;
/*      */ import it.unimi.dsi.fastutil.Pair;
/*      */ import it.unimi.dsi.fastutil.Size64;
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
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ public class Object2ReferenceLinkedOpenCustomHashMap<K, V>
/*      */   extends AbstractObject2ReferenceSortedMap<K, V>
/*      */   implements Serializable, Cloneable, Hash
/*      */ {
/*      */   private static final long serialVersionUID = 0L;
/*      */   private static final boolean ASSERTS = false;
/*      */   protected transient K[] key;
/*      */   protected transient V[] value;
/*      */   protected transient int mask;
/*      */   protected transient boolean containsNullKey;
/*      */   protected Hash.Strategy<? super K> strategy;
/*   97 */   protected transient int first = -1;
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*  102 */   protected transient int last = -1;
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
/*      */   protected transient Object2ReferenceSortedMap.FastSortedEntrySet<K, V> entries;
/*      */ 
/*      */   
/*      */   protected transient ObjectSortedSet<K> keys;
/*      */ 
/*      */   
/*      */   protected transient ReferenceCollection<V> values;
/*      */ 
/*      */ 
/*      */   
/*      */   public Object2ReferenceLinkedOpenCustomHashMap(int expected, float f, Hash.Strategy<? super K> strategy) {
/*  138 */     this.strategy = strategy;
/*  139 */     if (f <= 0.0F || f >= 1.0F) throw new IllegalArgumentException("Load factor must be greater than 0 and smaller than 1"); 
/*  140 */     if (expected < 0) throw new IllegalArgumentException("The expected number of elements must be nonnegative"); 
/*  141 */     this.f = f;
/*  142 */     this.minN = this.n = HashCommon.arraySize(expected, f);
/*  143 */     this.mask = this.n - 1;
/*  144 */     this.maxFill = HashCommon.maxFill(this.n, f);
/*  145 */     this.key = (K[])new Object[this.n + 1];
/*  146 */     this.value = (V[])new Object[this.n + 1];
/*  147 */     this.link = new long[this.n + 1];
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Object2ReferenceLinkedOpenCustomHashMap(int expected, Hash.Strategy<? super K> strategy) {
/*  157 */     this(expected, 0.75F, strategy);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Object2ReferenceLinkedOpenCustomHashMap(Hash.Strategy<? super K> strategy) {
/*  167 */     this(16, 0.75F, strategy);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Object2ReferenceLinkedOpenCustomHashMap(Map<? extends K, ? extends V> m, float f, Hash.Strategy<? super K> strategy) {
/*  178 */     this(m.size(), f, strategy);
/*  179 */     putAll(m);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Object2ReferenceLinkedOpenCustomHashMap(Map<? extends K, ? extends V> m, Hash.Strategy<? super K> strategy) {
/*  189 */     this(m, 0.75F, strategy);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Object2ReferenceLinkedOpenCustomHashMap(Object2ReferenceMap<K, V> m, float f, Hash.Strategy<? super K> strategy) {
/*  200 */     this(m.size(), f, strategy);
/*  201 */     putAll(m);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Object2ReferenceLinkedOpenCustomHashMap(Object2ReferenceMap<K, V> m, Hash.Strategy<? super K> strategy) {
/*  212 */     this(m, 0.75F, strategy);
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
/*      */   public Object2ReferenceLinkedOpenCustomHashMap(K[] k, V[] v, float f, Hash.Strategy<? super K> strategy) {
/*  225 */     this(k.length, f, strategy);
/*  226 */     if (k.length != v.length) throw new IllegalArgumentException("The key array and the value array have different lengths (" + k.length + " and " + v.length + ")"); 
/*  227 */     for (int i = 0; i < k.length; ) { put(k[i], v[i]); i++; }
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
/*      */   public Object2ReferenceLinkedOpenCustomHashMap(K[] k, V[] v, Hash.Strategy<? super K> strategy) {
/*  240 */     this(k, v, 0.75F, strategy);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Hash.Strategy<? super K> strategy() {
/*  249 */     return this.strategy;
/*      */   }
/*      */   
/*      */   private int realSize() {
/*  253 */     return this.containsNullKey ? (this.size - 1) : this.size;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void ensureCapacity(int capacity) {
/*  263 */     int needed = HashCommon.arraySize(capacity, this.f);
/*  264 */     if (needed > this.n) rehash(needed); 
/*      */   }
/*      */   
/*      */   private void tryCapacity(long capacity) {
/*  268 */     int needed = (int)Math.min(1073741824L, Math.max(2L, HashCommon.nextPowerOfTwo((long)Math.ceil(((float)capacity / this.f)))));
/*  269 */     if (needed > this.n) rehash(needed); 
/*      */   }
/*      */   
/*      */   private V removeEntry(int pos) {
/*  273 */     V oldValue = this.value[pos];
/*  274 */     this.value[pos] = null;
/*  275 */     this.size--;
/*  276 */     fixPointers(pos);
/*  277 */     shiftKeys(pos);
/*  278 */     if (this.n > this.minN && this.size < this.maxFill / 4 && this.n > 16) rehash(this.n / 2); 
/*  279 */     return oldValue;
/*      */   }
/*      */   
/*      */   private V removeNullEntry() {
/*  283 */     this.containsNullKey = false;
/*  284 */     this.key[this.n] = null;
/*  285 */     V oldValue = this.value[this.n];
/*  286 */     this.value[this.n] = null;
/*  287 */     this.size--;
/*  288 */     fixPointers(this.n);
/*  289 */     if (this.n > this.minN && this.size < this.maxFill / 4 && this.n > 16) rehash(this.n / 2); 
/*  290 */     return oldValue;
/*      */   }
/*      */ 
/*      */   
/*      */   public void putAll(Map<? extends K, ? extends V> m) {
/*  295 */     if (this.f <= 0.5D) { ensureCapacity(m.size()); }
/*  296 */     else { tryCapacity((size() + m.size())); }
/*      */     
/*  298 */     super.putAll(m);
/*      */   }
/*      */ 
/*      */   
/*      */   private int find(K k) {
/*  303 */     if (this.strategy.equals(k, null)) return this.containsNullKey ? this.n : -(this.n + 1);
/*      */     
/*  305 */     K[] key = this.key;
/*      */     K curr;
/*      */     int pos;
/*  308 */     if ((curr = key[pos = HashCommon.mix(this.strategy.hashCode(k)) & this.mask]) == null) return -(pos + 1); 
/*  309 */     if (this.strategy.equals(k, curr)) return pos;
/*      */     
/*      */     while (true) {
/*  312 */       if ((curr = key[pos = pos + 1 & this.mask]) == null) return -(pos + 1); 
/*  313 */       if (this.strategy.equals(k, curr)) return pos; 
/*      */     } 
/*      */   }
/*      */   
/*      */   private void insert(int pos, K k, V v) {
/*  318 */     if (pos == this.n) this.containsNullKey = true; 
/*  319 */     this.key[pos] = k;
/*  320 */     this.value[pos] = v;
/*  321 */     if (this.size == 0) {
/*  322 */       this.first = this.last = pos;
/*      */       
/*  324 */       this.link[pos] = -1L;
/*      */     } else {
/*  326 */       this.link[this.last] = this.link[this.last] ^ (this.link[this.last] ^ pos & 0xFFFFFFFFL) & 0xFFFFFFFFL;
/*  327 */       this.link[pos] = (this.last & 0xFFFFFFFFL) << 32L | 0xFFFFFFFFL;
/*  328 */       this.last = pos;
/*      */     } 
/*  330 */     if (this.size++ >= this.maxFill) rehash(HashCommon.arraySize(this.size + 1, this.f));
/*      */   
/*      */   }
/*      */ 
/*      */   
/*      */   public V put(K k, V v) {
/*  336 */     int pos = find(k);
/*  337 */     if (pos < 0) {
/*  338 */       insert(-pos - 1, k, v);
/*  339 */       return this.defRetValue;
/*      */     } 
/*  341 */     V oldValue = this.value[pos];
/*  342 */     this.value[pos] = v;
/*  343 */     return oldValue;
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
/*  356 */     K[] key = this.key; while (true) {
/*      */       K curr; int last;
/*  358 */       pos = (last = pos) + 1 & this.mask;
/*      */       while (true) {
/*  360 */         if ((curr = key[pos]) == null) {
/*  361 */           key[last] = null;
/*  362 */           this.value[last] = null;
/*      */           return;
/*      */         } 
/*  365 */         int slot = HashCommon.mix(this.strategy.hashCode(curr)) & this.mask;
/*  366 */         if ((last <= pos) ? (last >= slot || slot > pos) : (last >= slot && slot > pos))
/*  367 */           break;  pos = pos + 1 & this.mask;
/*      */       } 
/*  369 */       key[last] = curr;
/*  370 */       this.value[last] = this.value[pos];
/*  371 */       fixPointers(pos, last);
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public V remove(Object k) {
/*  378 */     if (this.strategy.equals(k, null)) {
/*  379 */       if (this.containsNullKey) return removeNullEntry(); 
/*  380 */       return this.defRetValue;
/*      */     } 
/*      */     
/*  383 */     K[] key = this.key;
/*      */     K curr;
/*      */     int pos;
/*  386 */     if ((curr = key[pos = HashCommon.mix(this.strategy.hashCode(k)) & this.mask]) == null) return this.defRetValue; 
/*  387 */     if (this.strategy.equals(k, curr)) return removeEntry(pos); 
/*      */     while (true) {
/*  389 */       if ((curr = key[pos = pos + 1 & this.mask]) == null) return this.defRetValue; 
/*  390 */       if (this.strategy.equals(k, curr)) return removeEntry(pos); 
/*      */     } 
/*      */   }
/*      */   
/*      */   private V setValue(int pos, V v) {
/*  395 */     V oldValue = this.value[pos];
/*  396 */     this.value[pos] = v;
/*  397 */     return oldValue;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public V removeFirst() {
/*  407 */     if (this.size == 0) throw new NoSuchElementException(); 
/*  408 */     int pos = this.first;
/*      */     
/*  410 */     if (this.size == 1) { this.first = this.last = -1; }
/*      */     else
/*  412 */     { this.first = (int)this.link[pos];
/*  413 */       if (0 <= this.first)
/*      */       {
/*  415 */         this.link[this.first] = this.link[this.first] | 0xFFFFFFFF00000000L;
/*      */       } }
/*      */     
/*  418 */     this.size--;
/*  419 */     V v = this.value[pos];
/*  420 */     if (pos == this.n)
/*  421 */     { this.containsNullKey = false;
/*  422 */       this.key[this.n] = null;
/*  423 */       this.value[this.n] = null; }
/*  424 */     else { shiftKeys(pos); }
/*  425 */      if (this.n > this.minN && this.size < this.maxFill / 4 && this.n > 16) rehash(this.n / 2); 
/*  426 */     return v;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public V removeLast() {
/*  436 */     if (this.size == 0) throw new NoSuchElementException(); 
/*  437 */     int pos = this.last;
/*      */     
/*  439 */     if (this.size == 1) { this.first = this.last = -1; }
/*      */     else
/*  441 */     { this.last = (int)(this.link[pos] >>> 32L);
/*  442 */       if (0 <= this.last)
/*      */       {
/*  444 */         this.link[this.last] = this.link[this.last] | 0xFFFFFFFFL;
/*      */       } }
/*      */     
/*  447 */     this.size--;
/*  448 */     V v = this.value[pos];
/*  449 */     if (pos == this.n)
/*  450 */     { this.containsNullKey = false;
/*  451 */       this.key[this.n] = null;
/*  452 */       this.value[this.n] = null; }
/*  453 */     else { shiftKeys(pos); }
/*  454 */      if (this.n > this.minN && this.size < this.maxFill / 4 && this.n > 16) rehash(this.n / 2); 
/*  455 */     return v;
/*      */   }
/*      */   
/*      */   private void moveIndexToFirst(int i) {
/*  459 */     if (this.size == 1 || this.first == i)
/*  460 */       return;  if (this.last == i) {
/*  461 */       this.last = (int)(this.link[i] >>> 32L);
/*      */       
/*  463 */       this.link[this.last] = this.link[this.last] | 0xFFFFFFFFL;
/*      */     } else {
/*  465 */       long linki = this.link[i];
/*  466 */       int prev = (int)(linki >>> 32L);
/*  467 */       int next = (int)linki;
/*  468 */       this.link[prev] = this.link[prev] ^ (this.link[prev] ^ linki & 0xFFFFFFFFL) & 0xFFFFFFFFL;
/*  469 */       this.link[next] = this.link[next] ^ (this.link[next] ^ linki & 0xFFFFFFFF00000000L) & 0xFFFFFFFF00000000L;
/*      */     } 
/*  471 */     this.link[this.first] = this.link[this.first] ^ (this.link[this.first] ^ (i & 0xFFFFFFFFL) << 32L) & 0xFFFFFFFF00000000L;
/*  472 */     this.link[i] = 0xFFFFFFFF00000000L | this.first & 0xFFFFFFFFL;
/*  473 */     this.first = i;
/*      */   }
/*      */   
/*      */   private void moveIndexToLast(int i) {
/*  477 */     if (this.size == 1 || this.last == i)
/*  478 */       return;  if (this.first == i) {
/*  479 */       this.first = (int)this.link[i];
/*      */       
/*  481 */       this.link[this.first] = this.link[this.first] | 0xFFFFFFFF00000000L;
/*      */     } else {
/*  483 */       long linki = this.link[i];
/*  484 */       int prev = (int)(linki >>> 32L);
/*  485 */       int next = (int)linki;
/*  486 */       this.link[prev] = this.link[prev] ^ (this.link[prev] ^ linki & 0xFFFFFFFFL) & 0xFFFFFFFFL;
/*  487 */       this.link[next] = this.link[next] ^ (this.link[next] ^ linki & 0xFFFFFFFF00000000L) & 0xFFFFFFFF00000000L;
/*      */     } 
/*  489 */     this.link[this.last] = this.link[this.last] ^ (this.link[this.last] ^ i & 0xFFFFFFFFL) & 0xFFFFFFFFL;
/*  490 */     this.link[i] = (this.last & 0xFFFFFFFFL) << 32L | 0xFFFFFFFFL;
/*  491 */     this.last = i;
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
/*      */   public V getAndMoveToFirst(K k) {
/*  503 */     if (this.strategy.equals(k, null)) {
/*  504 */       if (this.containsNullKey) {
/*  505 */         moveIndexToFirst(this.n);
/*  506 */         return this.value[this.n];
/*      */       } 
/*  508 */       return this.defRetValue;
/*      */     } 
/*      */     
/*  511 */     K[] key = this.key;
/*      */     K curr;
/*      */     int pos;
/*  514 */     if ((curr = key[pos = HashCommon.mix(this.strategy.hashCode(k)) & this.mask]) == null) return this.defRetValue; 
/*  515 */     if (this.strategy.equals(k, curr)) {
/*  516 */       moveIndexToFirst(pos);
/*  517 */       return this.value[pos];
/*      */     } 
/*      */     
/*      */     while (true) {
/*  521 */       if ((curr = key[pos = pos + 1 & this.mask]) == null) return this.defRetValue; 
/*  522 */       if (this.strategy.equals(k, curr)) {
/*  523 */         moveIndexToFirst(pos);
/*  524 */         return this.value[pos];
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
/*      */   public V getAndMoveToLast(K k) {
/*  538 */     if (this.strategy.equals(k, null)) {
/*  539 */       if (this.containsNullKey) {
/*  540 */         moveIndexToLast(this.n);
/*  541 */         return this.value[this.n];
/*      */       } 
/*  543 */       return this.defRetValue;
/*      */     } 
/*      */     
/*  546 */     K[] key = this.key;
/*      */     K curr;
/*      */     int pos;
/*  549 */     if ((curr = key[pos = HashCommon.mix(this.strategy.hashCode(k)) & this.mask]) == null) return this.defRetValue; 
/*  550 */     if (this.strategy.equals(k, curr)) {
/*  551 */       moveIndexToLast(pos);
/*  552 */       return this.value[pos];
/*      */     } 
/*      */     
/*      */     while (true) {
/*  556 */       if ((curr = key[pos = pos + 1 & this.mask]) == null) return this.defRetValue; 
/*  557 */       if (this.strategy.equals(k, curr)) {
/*  558 */         moveIndexToLast(pos);
/*  559 */         return this.value[pos];
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
/*      */   public V putAndMoveToFirst(K k, V v) {
/*      */     int pos;
/*  575 */     if (this.strategy.equals(k, null)) {
/*  576 */       if (this.containsNullKey) {
/*  577 */         moveIndexToFirst(this.n);
/*  578 */         return setValue(this.n, v);
/*      */       } 
/*  580 */       this.containsNullKey = true;
/*  581 */       pos = this.n;
/*      */     } else {
/*      */       
/*  584 */       K[] key = this.key;
/*      */       K curr;
/*  586 */       if ((curr = key[pos = HashCommon.mix(this.strategy.hashCode(k)) & this.mask]) != null) {
/*  587 */         if (this.strategy.equals(curr, k)) {
/*  588 */           moveIndexToFirst(pos);
/*  589 */           return setValue(pos, v);
/*      */         } 
/*  591 */         while ((curr = key[pos = pos + 1 & this.mask]) != null) { if (this.strategy.equals(curr, k)) {
/*  592 */             moveIndexToFirst(pos);
/*  593 */             return setValue(pos, v);
/*      */           }  }
/*      */       
/*      */       } 
/*  597 */     }  this.key[pos] = k;
/*  598 */     this.value[pos] = v;
/*  599 */     if (this.size == 0) {
/*  600 */       this.first = this.last = pos;
/*      */       
/*  602 */       this.link[pos] = -1L;
/*      */     } else {
/*  604 */       this.link[this.first] = this.link[this.first] ^ (this.link[this.first] ^ (pos & 0xFFFFFFFFL) << 32L) & 0xFFFFFFFF00000000L;
/*  605 */       this.link[pos] = 0xFFFFFFFF00000000L | this.first & 0xFFFFFFFFL;
/*  606 */       this.first = pos;
/*      */     } 
/*  608 */     if (this.size++ >= this.maxFill) rehash(HashCommon.arraySize(this.size, this.f));
/*      */     
/*  610 */     return this.defRetValue;
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
/*      */   public V putAndMoveToLast(K k, V v) {
/*      */     int pos;
/*  624 */     if (this.strategy.equals(k, null)) {
/*  625 */       if (this.containsNullKey) {
/*  626 */         moveIndexToLast(this.n);
/*  627 */         return setValue(this.n, v);
/*      */       } 
/*  629 */       this.containsNullKey = true;
/*  630 */       pos = this.n;
/*      */     } else {
/*      */       
/*  633 */       K[] key = this.key;
/*      */       K curr;
/*  635 */       if ((curr = key[pos = HashCommon.mix(this.strategy.hashCode(k)) & this.mask]) != null) {
/*  636 */         if (this.strategy.equals(curr, k)) {
/*  637 */           moveIndexToLast(pos);
/*  638 */           return setValue(pos, v);
/*      */         } 
/*  640 */         while ((curr = key[pos = pos + 1 & this.mask]) != null) { if (this.strategy.equals(curr, k)) {
/*  641 */             moveIndexToLast(pos);
/*  642 */             return setValue(pos, v);
/*      */           }  }
/*      */       
/*      */       } 
/*  646 */     }  this.key[pos] = k;
/*  647 */     this.value[pos] = v;
/*  648 */     if (this.size == 0) {
/*  649 */       this.first = this.last = pos;
/*      */       
/*  651 */       this.link[pos] = -1L;
/*      */     } else {
/*  653 */       this.link[this.last] = this.link[this.last] ^ (this.link[this.last] ^ pos & 0xFFFFFFFFL) & 0xFFFFFFFFL;
/*  654 */       this.link[pos] = (this.last & 0xFFFFFFFFL) << 32L | 0xFFFFFFFFL;
/*  655 */       this.last = pos;
/*      */     } 
/*  657 */     if (this.size++ >= this.maxFill) rehash(HashCommon.arraySize(this.size, this.f));
/*      */     
/*  659 */     return this.defRetValue;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public V get(Object k) {
/*  665 */     if (this.strategy.equals(k, null)) return this.containsNullKey ? this.value[this.n] : this.defRetValue;
/*      */     
/*  667 */     K[] key = this.key;
/*      */     K curr;
/*      */     int pos;
/*  670 */     if ((curr = key[pos = HashCommon.mix(this.strategy.hashCode(k)) & this.mask]) == null) return this.defRetValue; 
/*  671 */     if (this.strategy.equals(k, curr)) return this.value[pos];
/*      */     
/*      */     while (true) {
/*  674 */       if ((curr = key[pos = pos + 1 & this.mask]) == null) return this.defRetValue; 
/*  675 */       if (this.strategy.equals(k, curr)) return this.value[pos];
/*      */     
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   public boolean containsKey(Object k) {
/*  682 */     if (this.strategy.equals(k, null)) return this.containsNullKey;
/*      */     
/*  684 */     K[] key = this.key;
/*      */     K curr;
/*      */     int pos;
/*  687 */     if ((curr = key[pos = HashCommon.mix(this.strategy.hashCode(k)) & this.mask]) == null) return false; 
/*  688 */     if (this.strategy.equals(k, curr)) return true;
/*      */     
/*      */     while (true) {
/*  691 */       if ((curr = key[pos = pos + 1 & this.mask]) == null) return false; 
/*  692 */       if (this.strategy.equals(k, curr)) return true;
/*      */     
/*      */     } 
/*      */   }
/*      */   
/*      */   public boolean containsValue(Object v) {
/*  698 */     V[] value = this.value;
/*  699 */     K[] key = this.key;
/*  700 */     if (this.containsNullKey && value[this.n] == v) return true; 
/*  701 */     for (int i = this.n; i-- != 0;) { if (key[i] != null && value[i] == v) return true;  }
/*  702 */      return false;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public V getOrDefault(Object k, V defaultValue) {
/*  709 */     if (this.strategy.equals(k, null)) return this.containsNullKey ? this.value[this.n] : defaultValue;
/*      */     
/*  711 */     K[] key = this.key;
/*      */     K curr;
/*      */     int pos;
/*  714 */     if ((curr = key[pos = HashCommon.mix(this.strategy.hashCode(k)) & this.mask]) == null) return defaultValue; 
/*  715 */     if (this.strategy.equals(k, curr)) return this.value[pos];
/*      */     
/*      */     while (true) {
/*  718 */       if ((curr = key[pos = pos + 1 & this.mask]) == null) return defaultValue; 
/*  719 */       if (this.strategy.equals(k, curr)) return this.value[pos];
/*      */     
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   public V putIfAbsent(K k, V v) {
/*  726 */     int pos = find(k);
/*  727 */     if (pos >= 0) return this.value[pos]; 
/*  728 */     insert(-pos - 1, k, v);
/*  729 */     return this.defRetValue;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean remove(Object k, Object v) {
/*  736 */     if (this.strategy.equals(k, null)) {
/*  737 */       if (this.containsNullKey && v == this.value[this.n]) {
/*  738 */         removeNullEntry();
/*  739 */         return true;
/*      */       } 
/*  741 */       return false;
/*      */     } 
/*      */     
/*  744 */     K[] key = this.key;
/*      */     K curr;
/*      */     int pos;
/*  747 */     if ((curr = key[pos = HashCommon.mix(this.strategy.hashCode(k)) & this.mask]) == null) return false; 
/*  748 */     if (this.strategy.equals(k, curr) && v == this.value[pos]) {
/*  749 */       removeEntry(pos);
/*  750 */       return true;
/*      */     } 
/*      */     while (true) {
/*  753 */       if ((curr = key[pos = pos + 1 & this.mask]) == null) return false; 
/*  754 */       if (this.strategy.equals(k, curr) && v == this.value[pos]) {
/*  755 */         removeEntry(pos);
/*  756 */         return true;
/*      */       } 
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean replace(K k, V oldValue, V v) {
/*  764 */     int pos = find(k);
/*  765 */     if (pos < 0 || oldValue != this.value[pos]) return false; 
/*  766 */     this.value[pos] = v;
/*  767 */     return true;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public V replace(K k, V v) {
/*  773 */     int pos = find(k);
/*  774 */     if (pos < 0) return this.defRetValue; 
/*  775 */     V oldValue = this.value[pos];
/*  776 */     this.value[pos] = v;
/*  777 */     return oldValue;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public V computeIfAbsent(K key, Object2ReferenceFunction<? super K, ? extends V> mappingFunction) {
/*  783 */     Objects.requireNonNull(mappingFunction);
/*  784 */     int pos = find(key);
/*  785 */     if (pos >= 0) return this.value[pos]; 
/*  786 */     if (!mappingFunction.containsKey(key)) return this.defRetValue; 
/*  787 */     V newValue = mappingFunction.get(key);
/*  788 */     insert(-pos - 1, key, newValue);
/*  789 */     return newValue;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public V computeIfPresent(K k, BiFunction<? super K, ? super V, ? extends V> remappingFunction) {
/*  795 */     Objects.requireNonNull(remappingFunction);
/*  796 */     int pos = find(k);
/*  797 */     if (pos < 0) return this.defRetValue; 
/*  798 */     if (this.value[pos] == null) return this.defRetValue; 
/*  799 */     V newValue = remappingFunction.apply(k, this.value[pos]);
/*  800 */     if (newValue == null) {
/*  801 */       if (this.strategy.equals(k, null)) { removeNullEntry(); }
/*  802 */       else { removeEntry(pos); }
/*  803 */        return this.defRetValue;
/*      */     } 
/*  805 */     this.value[pos] = newValue; return newValue;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public V compute(K k, BiFunction<? super K, ? super V, ? extends V> remappingFunction) {
/*  811 */     Objects.requireNonNull(remappingFunction);
/*  812 */     int pos = find(k);
/*  813 */     V newValue = remappingFunction.apply(k, (pos >= 0) ? this.value[pos] : null);
/*  814 */     if (newValue == null) {
/*  815 */       if (pos >= 0)
/*  816 */         if (this.strategy.equals(k, null)) { removeNullEntry(); }
/*  817 */         else { removeEntry(pos); }
/*      */          
/*  819 */       return this.defRetValue;
/*      */     } 
/*  821 */     V newVal = newValue;
/*  822 */     if (pos < 0) {
/*  823 */       insert(-pos - 1, k, newVal);
/*  824 */       return newVal;
/*      */     } 
/*  826 */     this.value[pos] = newVal; return newVal;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public V merge(K k, V v, BiFunction<? super V, ? super V, ? extends V> remappingFunction) {
/*  832 */     Objects.requireNonNull(remappingFunction);
/*  833 */     Objects.requireNonNull(v);
/*  834 */     int pos = find(k);
/*  835 */     if (pos < 0 || this.value[pos] == null) {
/*  836 */       if (pos < 0) { insert(-pos - 1, k, v); }
/*  837 */       else { this.value[pos] = v; }
/*  838 */        return v;
/*      */     } 
/*  840 */     V newValue = remappingFunction.apply(this.value[pos], v);
/*  841 */     if (newValue == null) {
/*  842 */       if (this.strategy.equals(k, null)) { removeNullEntry(); }
/*  843 */       else { removeEntry(pos); }
/*  844 */        return this.defRetValue;
/*      */     } 
/*  846 */     this.value[pos] = newValue; return newValue;
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
/*  857 */     if (this.size == 0)
/*  858 */       return;  this.size = 0;
/*  859 */     this.containsNullKey = false;
/*  860 */     Arrays.fill((Object[])this.key, (Object)null);
/*  861 */     Arrays.fill((Object[])this.value, (Object)null);
/*  862 */     this.first = this.last = -1;
/*      */   }
/*      */ 
/*      */   
/*      */   public int size() {
/*  867 */     return this.size;
/*      */   }
/*      */ 
/*      */   
/*      */   public boolean isEmpty() {
/*  872 */     return (this.size == 0);
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   final class MapEntry
/*      */     implements Object2ReferenceMap.Entry<K, V>, Map.Entry<K, V>, ObjectReferencePair<K, V>
/*      */   {
/*      */     int index;
/*      */ 
/*      */ 
/*      */     
/*      */     MapEntry(int index) {
/*  885 */       this.index = index;
/*      */     }
/*      */ 
/*      */     
/*      */     MapEntry() {}
/*      */ 
/*      */     
/*      */     public K getKey() {
/*  893 */       return Object2ReferenceLinkedOpenCustomHashMap.this.key[this.index];
/*      */     }
/*      */ 
/*      */     
/*      */     public K left() {
/*  898 */       return Object2ReferenceLinkedOpenCustomHashMap.this.key[this.index];
/*      */     }
/*      */ 
/*      */     
/*      */     public V getValue() {
/*  903 */       return Object2ReferenceLinkedOpenCustomHashMap.this.value[this.index];
/*      */     }
/*      */ 
/*      */     
/*      */     public V right() {
/*  908 */       return Object2ReferenceLinkedOpenCustomHashMap.this.value[this.index];
/*      */     }
/*      */ 
/*      */     
/*      */     public V setValue(V v) {
/*  913 */       V oldValue = Object2ReferenceLinkedOpenCustomHashMap.this.value[this.index];
/*  914 */       Object2ReferenceLinkedOpenCustomHashMap.this.value[this.index] = v;
/*  915 */       return oldValue;
/*      */     }
/*      */ 
/*      */     
/*      */     public ObjectReferencePair<K, V> right(V v) {
/*  920 */       Object2ReferenceLinkedOpenCustomHashMap.this.value[this.index] = v;
/*  921 */       return this;
/*      */     }
/*      */ 
/*      */ 
/*      */     
/*      */     public boolean equals(Object o) {
/*  927 */       if (!(o instanceof Map.Entry)) return false; 
/*  928 */       Map.Entry<K, V> e = (Map.Entry<K, V>)o;
/*  929 */       return (Object2ReferenceLinkedOpenCustomHashMap.this.strategy.equals(Object2ReferenceLinkedOpenCustomHashMap.this.key[this.index], e.getKey()) && Object2ReferenceLinkedOpenCustomHashMap.this.value[this.index] == e.getValue());
/*      */     }
/*      */ 
/*      */     
/*      */     public int hashCode() {
/*  934 */       return Object2ReferenceLinkedOpenCustomHashMap.this.strategy.hashCode(Object2ReferenceLinkedOpenCustomHashMap.this.key[this.index]) ^ ((Object2ReferenceLinkedOpenCustomHashMap.this.value[this.index] == null) ? 0 : System.identityHashCode(Object2ReferenceLinkedOpenCustomHashMap.this.value[this.index]));
/*      */     }
/*      */ 
/*      */     
/*      */     public String toString() {
/*  939 */       return (new StringBuilder()).append(Object2ReferenceLinkedOpenCustomHashMap.this.key[this.index]).append("=>").append(Object2ReferenceLinkedOpenCustomHashMap.this.value[this.index]).toString();
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
/*  950 */     if (this.size == 0) {
/*  951 */       this.first = this.last = -1;
/*      */       return;
/*      */     } 
/*  954 */     if (this.first == i) {
/*  955 */       this.first = (int)this.link[i];
/*  956 */       if (0 <= this.first)
/*      */       {
/*  958 */         this.link[this.first] = this.link[this.first] | 0xFFFFFFFF00000000L;
/*      */       }
/*      */       return;
/*      */     } 
/*  962 */     if (this.last == i) {
/*  963 */       this.last = (int)(this.link[i] >>> 32L);
/*  964 */       if (0 <= this.last)
/*      */       {
/*  966 */         this.link[this.last] = this.link[this.last] | 0xFFFFFFFFL;
/*      */       }
/*      */       return;
/*      */     } 
/*  970 */     long linki = this.link[i];
/*  971 */     int prev = (int)(linki >>> 32L);
/*  972 */     int next = (int)linki;
/*  973 */     this.link[prev] = this.link[prev] ^ (this.link[prev] ^ linki & 0xFFFFFFFFL) & 0xFFFFFFFFL;
/*  974 */     this.link[next] = this.link[next] ^ (this.link[next] ^ linki & 0xFFFFFFFF00000000L) & 0xFFFFFFFF00000000L;
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
/*  986 */     if (this.size == 1) {
/*  987 */       this.first = this.last = d;
/*      */       
/*  989 */       this.link[d] = -1L;
/*      */       return;
/*      */     } 
/*  992 */     if (this.first == s) {
/*  993 */       this.first = d;
/*  994 */       this.link[(int)this.link[s]] = this.link[(int)this.link[s]] ^ (this.link[(int)this.link[s]] ^ (d & 0xFFFFFFFFL) << 32L) & 0xFFFFFFFF00000000L;
/*  995 */       this.link[d] = this.link[s];
/*      */       return;
/*      */     } 
/*  998 */     if (this.last == s) {
/*  999 */       this.last = d;
/* 1000 */       this.link[(int)(this.link[s] >>> 32L)] = this.link[(int)(this.link[s] >>> 32L)] ^ (this.link[(int)(this.link[s] >>> 32L)] ^ d & 0xFFFFFFFFL) & 0xFFFFFFFFL;
/* 1001 */       this.link[d] = this.link[s];
/*      */       return;
/*      */     } 
/* 1004 */     long links = this.link[s];
/* 1005 */     int prev = (int)(links >>> 32L);
/* 1006 */     int next = (int)links;
/* 1007 */     this.link[prev] = this.link[prev] ^ (this.link[prev] ^ d & 0xFFFFFFFFL) & 0xFFFFFFFFL;
/* 1008 */     this.link[next] = this.link[next] ^ (this.link[next] ^ (d & 0xFFFFFFFFL) << 32L) & 0xFFFFFFFF00000000L;
/* 1009 */     this.link[d] = links;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public K firstKey() {
/* 1019 */     if (this.size == 0) throw new NoSuchElementException(); 
/* 1020 */     return this.key[this.first];
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public K lastKey() {
/* 1030 */     if (this.size == 0) throw new NoSuchElementException(); 
/* 1031 */     return this.key[this.last];
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Object2ReferenceSortedMap<K, V> tailMap(K from) {
/* 1041 */     throw new UnsupportedOperationException();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Object2ReferenceSortedMap<K, V> headMap(K to) {
/* 1051 */     throw new UnsupportedOperationException();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Object2ReferenceSortedMap<K, V> subMap(K from, K to) {
/* 1061 */     throw new UnsupportedOperationException();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Comparator<? super K> comparator() {
/* 1071 */     return null;
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
/* 1086 */     int prev = -1;
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 1091 */     int next = -1;
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 1096 */     int curr = -1;
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 1101 */     int index = -1;
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     protected MapIterator() {
/* 1107 */       this.next = Object2ReferenceLinkedOpenCustomHashMap.this.first;
/* 1108 */       this.index = 0;
/*      */     }
/*      */     
/*      */     private MapIterator(K from) {
/* 1112 */       if (Object2ReferenceLinkedOpenCustomHashMap.this.strategy.equals(from, null)) {
/* 1113 */         if (Object2ReferenceLinkedOpenCustomHashMap.this.containsNullKey) {
/* 1114 */           this.next = (int)Object2ReferenceLinkedOpenCustomHashMap.this.link[Object2ReferenceLinkedOpenCustomHashMap.this.n];
/* 1115 */           this.prev = Object2ReferenceLinkedOpenCustomHashMap.this.n; return;
/*      */         } 
/* 1117 */         throw new NoSuchElementException("The key " + from + " does not belong to this map.");
/*      */       } 
/* 1119 */       if (Object2ReferenceLinkedOpenCustomHashMap.this.strategy.equals(Object2ReferenceLinkedOpenCustomHashMap.this.key[Object2ReferenceLinkedOpenCustomHashMap.this.last], from)) {
/* 1120 */         this.prev = Object2ReferenceLinkedOpenCustomHashMap.this.last;
/* 1121 */         this.index = Object2ReferenceLinkedOpenCustomHashMap.this.size;
/*      */         
/*      */         return;
/*      */       } 
/* 1125 */       int pos = HashCommon.mix(Object2ReferenceLinkedOpenCustomHashMap.this.strategy.hashCode(from)) & Object2ReferenceLinkedOpenCustomHashMap.this.mask;
/*      */       
/* 1127 */       while (Object2ReferenceLinkedOpenCustomHashMap.this.key[pos] != null) {
/* 1128 */         if (Object2ReferenceLinkedOpenCustomHashMap.this.strategy.equals(Object2ReferenceLinkedOpenCustomHashMap.this.key[pos], from)) {
/*      */           
/* 1130 */           this.next = (int)Object2ReferenceLinkedOpenCustomHashMap.this.link[pos];
/* 1131 */           this.prev = pos;
/*      */           return;
/*      */         } 
/* 1134 */         pos = pos + 1 & Object2ReferenceLinkedOpenCustomHashMap.this.mask;
/*      */       } 
/* 1136 */       throw new NoSuchElementException("The key " + from + " does not belong to this map.");
/*      */     }
/*      */     
/*      */     public boolean hasNext() {
/* 1140 */       return (this.next != -1);
/*      */     }
/*      */     
/*      */     public boolean hasPrevious() {
/* 1144 */       return (this.prev != -1);
/*      */     }
/*      */     
/*      */     private final void ensureIndexKnown() {
/* 1148 */       if (this.index >= 0)
/* 1149 */         return;  if (this.prev == -1) {
/* 1150 */         this.index = 0;
/*      */         return;
/*      */       } 
/* 1153 */       if (this.next == -1) {
/* 1154 */         this.index = Object2ReferenceLinkedOpenCustomHashMap.this.size;
/*      */         return;
/*      */       } 
/* 1157 */       int pos = Object2ReferenceLinkedOpenCustomHashMap.this.first;
/* 1158 */       this.index = 1;
/* 1159 */       while (pos != this.prev) {
/* 1160 */         pos = (int)Object2ReferenceLinkedOpenCustomHashMap.this.link[pos];
/* 1161 */         this.index++;
/*      */       } 
/*      */     }
/*      */     
/*      */     public int nextIndex() {
/* 1166 */       ensureIndexKnown();
/* 1167 */       return this.index;
/*      */     }
/*      */     
/*      */     public int previousIndex() {
/* 1171 */       ensureIndexKnown();
/* 1172 */       return this.index - 1;
/*      */     }
/*      */     
/*      */     public int nextEntry() {
/* 1176 */       if (!hasNext()) throw new NoSuchElementException(); 
/* 1177 */       this.curr = this.next;
/* 1178 */       this.next = (int)Object2ReferenceLinkedOpenCustomHashMap.this.link[this.curr];
/* 1179 */       this.prev = this.curr;
/* 1180 */       if (this.index >= 0) this.index++; 
/* 1181 */       return this.curr;
/*      */     }
/*      */     
/*      */     public int previousEntry() {
/* 1185 */       if (!hasPrevious()) throw new NoSuchElementException(); 
/* 1186 */       this.curr = this.prev;
/* 1187 */       this.prev = (int)(Object2ReferenceLinkedOpenCustomHashMap.this.link[this.curr] >>> 32L);
/* 1188 */       this.next = this.curr;
/* 1189 */       if (this.index >= 0) this.index--; 
/* 1190 */       return this.curr;
/*      */     }
/*      */     
/*      */     public void forEachRemaining(ConsumerType action) {
/* 1194 */       while (hasNext()) {
/* 1195 */         this.curr = this.next;
/* 1196 */         this.next = (int)Object2ReferenceLinkedOpenCustomHashMap.this.link[this.curr];
/* 1197 */         this.prev = this.curr;
/* 1198 */         if (this.index >= 0) this.index++; 
/* 1199 */         acceptOnIndex(action, this.curr);
/*      */       } 
/*      */     }
/*      */     
/*      */     public void remove() {
/* 1204 */       ensureIndexKnown();
/* 1205 */       if (this.curr == -1) throw new IllegalStateException(); 
/* 1206 */       if (this.curr == this.prev)
/*      */       
/*      */       { 
/* 1209 */         this.index--;
/* 1210 */         this.prev = (int)(Object2ReferenceLinkedOpenCustomHashMap.this.link[this.curr] >>> 32L); }
/* 1211 */       else { this.next = (int)Object2ReferenceLinkedOpenCustomHashMap.this.link[this.curr]; }
/* 1212 */        Object2ReferenceLinkedOpenCustomHashMap.this.size--;
/*      */ 
/*      */       
/* 1215 */       if (this.prev == -1) { Object2ReferenceLinkedOpenCustomHashMap.this.first = this.next; }
/* 1216 */       else { Object2ReferenceLinkedOpenCustomHashMap.this.link[this.prev] = Object2ReferenceLinkedOpenCustomHashMap.this.link[this.prev] ^ (Object2ReferenceLinkedOpenCustomHashMap.this.link[this.prev] ^ this.next & 0xFFFFFFFFL) & 0xFFFFFFFFL; }
/* 1217 */        if (this.next == -1) { Object2ReferenceLinkedOpenCustomHashMap.this.last = this.prev; }
/* 1218 */       else { Object2ReferenceLinkedOpenCustomHashMap.this.link[this.next] = Object2ReferenceLinkedOpenCustomHashMap.this.link[this.next] ^ (Object2ReferenceLinkedOpenCustomHashMap.this.link[this.next] ^ (this.prev & 0xFFFFFFFFL) << 32L) & 0xFFFFFFFF00000000L; }
/* 1219 */        int pos = this.curr;
/* 1220 */       this.curr = -1;
/* 1221 */       if (pos == Object2ReferenceLinkedOpenCustomHashMap.this.n) {
/* 1222 */         Object2ReferenceLinkedOpenCustomHashMap.this.containsNullKey = false;
/* 1223 */         Object2ReferenceLinkedOpenCustomHashMap.this.key[Object2ReferenceLinkedOpenCustomHashMap.this.n] = null;
/* 1224 */         Object2ReferenceLinkedOpenCustomHashMap.this.value[Object2ReferenceLinkedOpenCustomHashMap.this.n] = null;
/*      */       } else {
/*      */         
/* 1227 */         K[] key = Object2ReferenceLinkedOpenCustomHashMap.this.key; while (true) {
/*      */           K curr;
/*      */           int last;
/* 1230 */           pos = (last = pos) + 1 & Object2ReferenceLinkedOpenCustomHashMap.this.mask;
/*      */           while (true) {
/* 1232 */             if ((curr = key[pos]) == null) {
/* 1233 */               key[last] = null;
/* 1234 */               Object2ReferenceLinkedOpenCustomHashMap.this.value[last] = null;
/*      */               return;
/*      */             } 
/* 1237 */             int slot = HashCommon.mix(Object2ReferenceLinkedOpenCustomHashMap.this.strategy.hashCode(curr)) & Object2ReferenceLinkedOpenCustomHashMap.this.mask;
/* 1238 */             if ((last <= pos) ? (last >= slot || slot > pos) : (last >= slot && slot > pos))
/* 1239 */               break;  pos = pos + 1 & Object2ReferenceLinkedOpenCustomHashMap.this.mask;
/*      */           } 
/* 1241 */           key[last] = curr;
/* 1242 */           Object2ReferenceLinkedOpenCustomHashMap.this.value[last] = Object2ReferenceLinkedOpenCustomHashMap.this.value[pos];
/* 1243 */           if (this.next == pos) this.next = last; 
/* 1244 */           if (this.prev == pos) this.prev = last; 
/* 1245 */           Object2ReferenceLinkedOpenCustomHashMap.this.fixPointers(pos, last);
/*      */         } 
/*      */       } 
/*      */     }
/*      */     
/*      */     public int skip(int n) {
/* 1251 */       int i = n;
/* 1252 */       for (; i-- != 0 && hasNext(); nextEntry());
/* 1253 */       return n - i - 1;
/*      */     }
/*      */     
/*      */     public int back(int n) {
/* 1257 */       int i = n;
/* 1258 */       for (; i-- != 0 && hasPrevious(); previousEntry());
/* 1259 */       return n - i - 1;
/*      */     }
/*      */     
/*      */     public void set(Object2ReferenceMap.Entry<K, V> ok) {
/* 1263 */       throw new UnsupportedOperationException();
/*      */     }
/*      */     
/*      */     public void add(Object2ReferenceMap.Entry<K, V> ok) {
/* 1267 */       throw new UnsupportedOperationException();
/*      */     }
/*      */     
/*      */     abstract void acceptOnIndex(ConsumerType param1ConsumerType, int param1Int); }
/*      */   
/*      */   private final class EntryIterator extends MapIterator<Consumer<? super Object2ReferenceMap.Entry<K, V>>> implements ObjectListIterator<Object2ReferenceMap.Entry<K, V>> {
/*      */     private Object2ReferenceLinkedOpenCustomHashMap<K, V>.MapEntry entry;
/*      */     
/*      */     public EntryIterator() {}
/*      */     
/*      */     public EntryIterator(K from) {
/* 1278 */       super(from);
/*      */     }
/*      */ 
/*      */ 
/*      */     
/*      */     final void acceptOnIndex(Consumer<? super Object2ReferenceMap.Entry<K, V>> action, int index) {
/* 1284 */       action.accept(new Object2ReferenceLinkedOpenCustomHashMap.MapEntry(index));
/*      */     }
/*      */ 
/*      */     
/*      */     public Object2ReferenceLinkedOpenCustomHashMap<K, V>.MapEntry next() {
/* 1289 */       return this.entry = new Object2ReferenceLinkedOpenCustomHashMap.MapEntry(nextEntry());
/*      */     }
/*      */ 
/*      */     
/*      */     public Object2ReferenceLinkedOpenCustomHashMap<K, V>.MapEntry previous() {
/* 1294 */       return this.entry = new Object2ReferenceLinkedOpenCustomHashMap.MapEntry(previousEntry());
/*      */     }
/*      */ 
/*      */     
/*      */     public void remove() {
/* 1299 */       super.remove();
/* 1300 */       this.entry.index = -1;
/*      */     }
/*      */   }
/*      */   
/*      */   private final class FastEntryIterator extends MapIterator<Consumer<? super Object2ReferenceMap.Entry<K, V>>> implements ObjectListIterator<Object2ReferenceMap.Entry<K, V>> {
/* 1305 */     final Object2ReferenceLinkedOpenCustomHashMap<K, V>.MapEntry entry = new Object2ReferenceLinkedOpenCustomHashMap.MapEntry();
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public FastEntryIterator(K from) {
/* 1311 */       super(from);
/*      */     }
/*      */ 
/*      */ 
/*      */     
/*      */     final void acceptOnIndex(Consumer<? super Object2ReferenceMap.Entry<K, V>> action, int index) {
/* 1317 */       this.entry.index = index;
/* 1318 */       action.accept(this.entry);
/*      */     }
/*      */ 
/*      */     
/*      */     public Object2ReferenceLinkedOpenCustomHashMap<K, V>.MapEntry next() {
/* 1323 */       this.entry.index = nextEntry();
/* 1324 */       return this.entry;
/*      */     }
/*      */ 
/*      */     
/*      */     public Object2ReferenceLinkedOpenCustomHashMap<K, V>.MapEntry previous() {
/* 1329 */       this.entry.index = previousEntry();
/* 1330 */       return this.entry;
/*      */     }
/*      */     
/*      */     public FastEntryIterator() {} }
/*      */   
/*      */   private final class MapEntrySet extends AbstractObjectSortedSet<Object2ReferenceMap.Entry<K, V>> implements Object2ReferenceSortedMap.FastSortedEntrySet<K, V> {
/*      */     private static final int SPLITERATOR_CHARACTERISTICS = 81;
/*      */     
/*      */     public ObjectBidirectionalIterator<Object2ReferenceMap.Entry<K, V>> iterator() {
/* 1339 */       return new Object2ReferenceLinkedOpenCustomHashMap.EntryIterator();
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
/*      */     public ObjectSpliterator<Object2ReferenceMap.Entry<K, V>> spliterator() {
/* 1357 */       return ObjectSpliterators.asSpliterator(iterator(), Size64.sizeOf(Object2ReferenceLinkedOpenCustomHashMap.this), 81);
/*      */     }
/*      */ 
/*      */     
/*      */     public Comparator<? super Object2ReferenceMap.Entry<K, V>> comparator() {
/* 1362 */       return null;
/*      */     }
/*      */ 
/*      */     
/*      */     public ObjectSortedSet<Object2ReferenceMap.Entry<K, V>> subSet(Object2ReferenceMap.Entry<K, V> fromElement, Object2ReferenceMap.Entry<K, V> toElement) {
/* 1367 */       throw new UnsupportedOperationException();
/*      */     }
/*      */ 
/*      */     
/*      */     public ObjectSortedSet<Object2ReferenceMap.Entry<K, V>> headSet(Object2ReferenceMap.Entry<K, V> toElement) {
/* 1372 */       throw new UnsupportedOperationException();
/*      */     }
/*      */ 
/*      */     
/*      */     public ObjectSortedSet<Object2ReferenceMap.Entry<K, V>> tailSet(Object2ReferenceMap.Entry<K, V> fromElement) {
/* 1377 */       throw new UnsupportedOperationException();
/*      */     }
/*      */ 
/*      */     
/*      */     public Object2ReferenceMap.Entry<K, V> first() {
/* 1382 */       if (Object2ReferenceLinkedOpenCustomHashMap.this.size == 0) throw new NoSuchElementException(); 
/* 1383 */       return new Object2ReferenceLinkedOpenCustomHashMap.MapEntry(Object2ReferenceLinkedOpenCustomHashMap.this.first);
/*      */     }
/*      */ 
/*      */     
/*      */     public Object2ReferenceMap.Entry<K, V> last() {
/* 1388 */       if (Object2ReferenceLinkedOpenCustomHashMap.this.size == 0) throw new NoSuchElementException(); 
/* 1389 */       return new Object2ReferenceLinkedOpenCustomHashMap.MapEntry(Object2ReferenceLinkedOpenCustomHashMap.this.last);
/*      */     }
/*      */ 
/*      */ 
/*      */     
/*      */     public boolean contains(Object o) {
/* 1395 */       if (!(o instanceof Map.Entry)) return false; 
/* 1396 */       Map.Entry<?, ?> e = (Map.Entry<?, ?>)o;
/* 1397 */       K k = (K)e.getKey();
/* 1398 */       V v = (V)e.getValue();
/* 1399 */       if (Object2ReferenceLinkedOpenCustomHashMap.this.strategy.equals(k, null)) return (Object2ReferenceLinkedOpenCustomHashMap.this.containsNullKey && Object2ReferenceLinkedOpenCustomHashMap.this.value[Object2ReferenceLinkedOpenCustomHashMap.this.n] == v);
/*      */       
/* 1401 */       K[] key = Object2ReferenceLinkedOpenCustomHashMap.this.key;
/*      */       K curr;
/*      */       int pos;
/* 1404 */       if ((curr = key[pos = HashCommon.mix(Object2ReferenceLinkedOpenCustomHashMap.this.strategy.hashCode(k)) & Object2ReferenceLinkedOpenCustomHashMap.this.mask]) == null) return false; 
/* 1405 */       if (Object2ReferenceLinkedOpenCustomHashMap.this.strategy.equals(k, curr)) return (Object2ReferenceLinkedOpenCustomHashMap.this.value[pos] == v);
/*      */       
/*      */       while (true) {
/* 1408 */         if ((curr = key[pos = pos + 1 & Object2ReferenceLinkedOpenCustomHashMap.this.mask]) == null) return false; 
/* 1409 */         if (Object2ReferenceLinkedOpenCustomHashMap.this.strategy.equals(k, curr)) return (Object2ReferenceLinkedOpenCustomHashMap.this.value[pos] == v);
/*      */       
/*      */       } 
/*      */     }
/*      */ 
/*      */     
/*      */     public boolean remove(Object o) {
/* 1416 */       if (!(o instanceof Map.Entry)) return false; 
/* 1417 */       Map.Entry<?, ?> e = (Map.Entry<?, ?>)o;
/* 1418 */       K k = (K)e.getKey();
/* 1419 */       V v = (V)e.getValue();
/* 1420 */       if (Object2ReferenceLinkedOpenCustomHashMap.this.strategy.equals(k, null)) {
/* 1421 */         if (Object2ReferenceLinkedOpenCustomHashMap.this.containsNullKey && Object2ReferenceLinkedOpenCustomHashMap.this.value[Object2ReferenceLinkedOpenCustomHashMap.this.n] == v) {
/* 1422 */           Object2ReferenceLinkedOpenCustomHashMap.this.removeNullEntry();
/* 1423 */           return true;
/*      */         } 
/* 1425 */         return false;
/*      */       } 
/*      */       
/* 1428 */       K[] key = Object2ReferenceLinkedOpenCustomHashMap.this.key;
/*      */       K curr;
/*      */       int pos;
/* 1431 */       if ((curr = key[pos = HashCommon.mix(Object2ReferenceLinkedOpenCustomHashMap.this.strategy.hashCode(k)) & Object2ReferenceLinkedOpenCustomHashMap.this.mask]) == null) return false; 
/* 1432 */       if (Object2ReferenceLinkedOpenCustomHashMap.this.strategy.equals(curr, k)) {
/* 1433 */         if (Object2ReferenceLinkedOpenCustomHashMap.this.value[pos] == v) {
/* 1434 */           Object2ReferenceLinkedOpenCustomHashMap.this.removeEntry(pos);
/* 1435 */           return true;
/*      */         } 
/* 1437 */         return false;
/*      */       } 
/*      */       while (true) {
/* 1440 */         if ((curr = key[pos = pos + 1 & Object2ReferenceLinkedOpenCustomHashMap.this.mask]) == null) return false; 
/* 1441 */         if (Object2ReferenceLinkedOpenCustomHashMap.this.strategy.equals(curr, k) && 
/* 1442 */           Object2ReferenceLinkedOpenCustomHashMap.this.value[pos] == v) {
/* 1443 */           Object2ReferenceLinkedOpenCustomHashMap.this.removeEntry(pos);
/* 1444 */           return true;
/*      */         } 
/*      */       } 
/*      */     }
/*      */ 
/*      */ 
/*      */     
/*      */     public int size() {
/* 1452 */       return Object2ReferenceLinkedOpenCustomHashMap.this.size;
/*      */     }
/*      */ 
/*      */     
/*      */     public void clear() {
/* 1457 */       Object2ReferenceLinkedOpenCustomHashMap.this.clear();
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
/*      */     public ObjectListIterator<Object2ReferenceMap.Entry<K, V>> iterator(Object2ReferenceMap.Entry<K, V> from) {
/* 1470 */       return new Object2ReferenceLinkedOpenCustomHashMap.EntryIterator(from.getKey());
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public ObjectListIterator<Object2ReferenceMap.Entry<K, V>> fastIterator() {
/* 1481 */       return new Object2ReferenceLinkedOpenCustomHashMap.FastEntryIterator();
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
/*      */     public ObjectListIterator<Object2ReferenceMap.Entry<K, V>> fastIterator(Object2ReferenceMap.Entry<K, V> from) {
/* 1494 */       return new Object2ReferenceLinkedOpenCustomHashMap.FastEntryIterator(from.getKey());
/*      */     }
/*      */ 
/*      */ 
/*      */     
/*      */     public void forEach(Consumer<? super Object2ReferenceMap.Entry<K, V>> consumer) {
/* 1500 */       for (int i = Object2ReferenceLinkedOpenCustomHashMap.this.size, next = Object2ReferenceLinkedOpenCustomHashMap.this.first; i-- != 0; ) {
/* 1501 */         int curr = next;
/* 1502 */         next = (int)Object2ReferenceLinkedOpenCustomHashMap.this.link[curr];
/* 1503 */         consumer.accept(new Object2ReferenceLinkedOpenCustomHashMap.MapEntry(curr));
/*      */       } 
/*      */     }
/*      */ 
/*      */ 
/*      */     
/*      */     public void fastForEach(Consumer<? super Object2ReferenceMap.Entry<K, V>> consumer) {
/* 1510 */       Object2ReferenceLinkedOpenCustomHashMap<K, V>.MapEntry entry = new Object2ReferenceLinkedOpenCustomHashMap.MapEntry();
/* 1511 */       for (int i = Object2ReferenceLinkedOpenCustomHashMap.this.size, next = Object2ReferenceLinkedOpenCustomHashMap.this.first; i-- != 0; ) {
/* 1512 */         entry.index = next;
/* 1513 */         next = (int)Object2ReferenceLinkedOpenCustomHashMap.this.link[next];
/* 1514 */         consumer.accept(entry);
/*      */       } 
/*      */     }
/*      */   }
/*      */ 
/*      */   
/*      */   public Object2ReferenceSortedMap.FastSortedEntrySet<K, V> object2ReferenceEntrySet() {
/* 1521 */     if (this.entries == null) this.entries = new MapEntrySet(); 
/* 1522 */     return this.entries;
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
/* 1535 */       super(k);
/*      */     }
/*      */ 
/*      */     
/*      */     public K previous() {
/* 1540 */       return Object2ReferenceLinkedOpenCustomHashMap.this.key[previousEntry()];
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
/* 1552 */       action.accept(Object2ReferenceLinkedOpenCustomHashMap.this.key[index]);
/*      */     }
/*      */ 
/*      */     
/*      */     public K next() {
/* 1557 */       return Object2ReferenceLinkedOpenCustomHashMap.this.key[nextEntry()];
/*      */     } }
/*      */   
/*      */   private final class KeySet extends AbstractObjectSortedSet<K> {
/*      */     private static final int SPLITERATOR_CHARACTERISTICS = 81;
/*      */     
/*      */     private KeySet() {}
/*      */     
/*      */     public ObjectListIterator<K> iterator(K from) {
/* 1566 */       return new Object2ReferenceLinkedOpenCustomHashMap.KeyIterator(from);
/*      */     }
/*      */ 
/*      */     
/*      */     public ObjectListIterator<K> iterator() {
/* 1571 */       return new Object2ReferenceLinkedOpenCustomHashMap.KeyIterator();
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public ObjectSpliterator<K> spliterator() {
/* 1581 */       return ObjectSpliterators.asSpliterator(iterator(), Size64.sizeOf(Object2ReferenceLinkedOpenCustomHashMap.this), 81);
/*      */     }
/*      */ 
/*      */ 
/*      */     
/*      */     public void forEach(Consumer<? super K> consumer) {
/* 1587 */       for (int i = Object2ReferenceLinkedOpenCustomHashMap.this.size, next = Object2ReferenceLinkedOpenCustomHashMap.this.first; i-- != 0; ) {
/* 1588 */         int curr = next;
/* 1589 */         next = (int)Object2ReferenceLinkedOpenCustomHashMap.this.link[curr];
/* 1590 */         consumer.accept(Object2ReferenceLinkedOpenCustomHashMap.this.key[curr]);
/*      */       } 
/*      */     }
/*      */ 
/*      */     
/*      */     public int size() {
/* 1596 */       return Object2ReferenceLinkedOpenCustomHashMap.this.size;
/*      */     }
/*      */ 
/*      */     
/*      */     public boolean contains(Object k) {
/* 1601 */       return Object2ReferenceLinkedOpenCustomHashMap.this.containsKey(k);
/*      */     }
/*      */ 
/*      */     
/*      */     public boolean remove(Object k) {
/* 1606 */       int oldSize = Object2ReferenceLinkedOpenCustomHashMap.this.size;
/* 1607 */       Object2ReferenceLinkedOpenCustomHashMap.this.remove(k);
/* 1608 */       return (Object2ReferenceLinkedOpenCustomHashMap.this.size != oldSize);
/*      */     }
/*      */ 
/*      */     
/*      */     public void clear() {
/* 1613 */       Object2ReferenceLinkedOpenCustomHashMap.this.clear();
/*      */     }
/*      */ 
/*      */     
/*      */     public K first() {
/* 1618 */       if (Object2ReferenceLinkedOpenCustomHashMap.this.size == 0) throw new NoSuchElementException(); 
/* 1619 */       return Object2ReferenceLinkedOpenCustomHashMap.this.key[Object2ReferenceLinkedOpenCustomHashMap.this.first];
/*      */     }
/*      */ 
/*      */     
/*      */     public K last() {
/* 1624 */       if (Object2ReferenceLinkedOpenCustomHashMap.this.size == 0) throw new NoSuchElementException(); 
/* 1625 */       return Object2ReferenceLinkedOpenCustomHashMap.this.key[Object2ReferenceLinkedOpenCustomHashMap.this.last];
/*      */     }
/*      */ 
/*      */     
/*      */     public Comparator<? super K> comparator() {
/* 1630 */       return null;
/*      */     }
/*      */ 
/*      */     
/*      */     public ObjectSortedSet<K> tailSet(K from) {
/* 1635 */       throw new UnsupportedOperationException();
/*      */     }
/*      */ 
/*      */     
/*      */     public ObjectSortedSet<K> headSet(K to) {
/* 1640 */       throw new UnsupportedOperationException();
/*      */     }
/*      */ 
/*      */     
/*      */     public ObjectSortedSet<K> subSet(K from, K to) {
/* 1645 */       throw new UnsupportedOperationException();
/*      */     }
/*      */   }
/*      */ 
/*      */   
/*      */   public ObjectSortedSet<K> keySet() {
/* 1651 */     if (this.keys == null) this.keys = new KeySet(); 
/* 1652 */     return this.keys;
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
/* 1666 */       return Object2ReferenceLinkedOpenCustomHashMap.this.value[previousEntry()];
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
/* 1678 */       action.accept(Object2ReferenceLinkedOpenCustomHashMap.this.value[index]);
/*      */     }
/*      */ 
/*      */     
/*      */     public V next() {
/* 1683 */       return Object2ReferenceLinkedOpenCustomHashMap.this.value[nextEntry()];
/*      */     }
/*      */   }
/*      */ 
/*      */   
/*      */   public ReferenceCollection<V> values() {
/* 1689 */     if (this.values == null) this.values = new AbstractReferenceCollection<V>()
/*      */         {
/*      */           private static final int SPLITERATOR_CHARACTERISTICS = 80;
/*      */           
/*      */           public ObjectIterator<V> iterator() {
/* 1694 */             return new Object2ReferenceLinkedOpenCustomHashMap.ValueIterator();
/*      */           }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */           
/*      */           public ObjectSpliterator<V> spliterator() {
/* 1704 */             return ObjectSpliterators.asSpliterator(iterator(), Size64.sizeOf(Object2ReferenceLinkedOpenCustomHashMap.this), 80);
/*      */           }
/*      */ 
/*      */ 
/*      */           
/*      */           public void forEach(Consumer<? super V> consumer) {
/* 1710 */             for (int i = Object2ReferenceLinkedOpenCustomHashMap.this.size, next = Object2ReferenceLinkedOpenCustomHashMap.this.first; i-- != 0; ) {
/* 1711 */               int curr = next;
/* 1712 */               next = (int)Object2ReferenceLinkedOpenCustomHashMap.this.link[curr];
/* 1713 */               consumer.accept(Object2ReferenceLinkedOpenCustomHashMap.this.value[curr]);
/*      */             } 
/*      */           }
/*      */ 
/*      */           
/*      */           public int size() {
/* 1719 */             return Object2ReferenceLinkedOpenCustomHashMap.this.size;
/*      */           }
/*      */ 
/*      */           
/*      */           public boolean contains(Object v) {
/* 1724 */             return Object2ReferenceLinkedOpenCustomHashMap.this.containsValue(v);
/*      */           }
/*      */ 
/*      */           
/*      */           public void clear() {
/* 1729 */             Object2ReferenceLinkedOpenCustomHashMap.this.clear();
/*      */           }
/*      */         }; 
/* 1732 */     return this.values;
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
/* 1749 */     return trim(this.size);
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
/* 1771 */     int l = HashCommon.nextPowerOfTwo((int)Math.ceil((n / this.f)));
/* 1772 */     if (l >= this.n || this.size > HashCommon.maxFill(l, this.f)) return true; 
/*      */     try {
/* 1774 */       rehash(l);
/* 1775 */     } catch (OutOfMemoryError cantDoIt) {
/* 1776 */       return false;
/*      */     } 
/* 1778 */     return true;
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
/* 1793 */     K[] key = this.key;
/* 1794 */     V[] value = this.value;
/* 1795 */     int mask = newN - 1;
/* 1796 */     K[] newKey = (K[])new Object[newN + 1];
/* 1797 */     V[] newValue = (V[])new Object[newN + 1];
/* 1798 */     int i = this.first, prev = -1, newPrev = -1;
/* 1799 */     long[] link = this.link;
/* 1800 */     long[] newLink = new long[newN + 1];
/* 1801 */     this.first = -1;
/* 1802 */     for (int j = this.size; j-- != 0; ) {
/* 1803 */       int pos; if (this.strategy.equals(key[i], null)) { pos = newN; }
/*      */       else
/* 1805 */       { pos = HashCommon.mix(this.strategy.hashCode(key[i])) & mask;
/* 1806 */         for (; newKey[pos] != null; pos = pos + 1 & mask); }
/*      */       
/* 1808 */       newKey[pos] = key[i];
/* 1809 */       newValue[pos] = value[i];
/* 1810 */       if (prev != -1) {
/* 1811 */         newLink[newPrev] = newLink[newPrev] ^ (newLink[newPrev] ^ pos & 0xFFFFFFFFL) & 0xFFFFFFFFL;
/* 1812 */         newLink[pos] = newLink[pos] ^ (newLink[pos] ^ (newPrev & 0xFFFFFFFFL) << 32L) & 0xFFFFFFFF00000000L;
/* 1813 */         newPrev = pos;
/*      */       } else {
/* 1815 */         newPrev = this.first = pos;
/*      */         
/* 1817 */         newLink[pos] = -1L;
/*      */       } 
/* 1819 */       int t = i;
/* 1820 */       i = (int)link[i];
/* 1821 */       prev = t;
/*      */     } 
/* 1823 */     this.link = newLink;
/* 1824 */     this.last = newPrev;
/* 1825 */     if (newPrev != -1)
/*      */     {
/* 1827 */       newLink[newPrev] = newLink[newPrev] | 0xFFFFFFFFL; } 
/* 1828 */     this.n = newN;
/* 1829 */     this.mask = mask;
/* 1830 */     this.maxFill = HashCommon.maxFill(this.n, this.f);
/* 1831 */     this.key = newKey;
/* 1832 */     this.value = newValue;
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
/*      */   public Object2ReferenceLinkedOpenCustomHashMap<K, V> clone() {
/*      */     Object2ReferenceLinkedOpenCustomHashMap<K, V> c;
/*      */     try {
/* 1849 */       c = (Object2ReferenceLinkedOpenCustomHashMap<K, V>)super.clone();
/* 1850 */     } catch (CloneNotSupportedException cantHappen) {
/* 1851 */       throw new InternalError();
/*      */     } 
/* 1853 */     c.keys = null;
/* 1854 */     c.values = null;
/* 1855 */     c.entries = null;
/* 1856 */     c.containsNullKey = this.containsNullKey;
/* 1857 */     c.key = (K[])this.key.clone();
/* 1858 */     c.value = (V[])this.value.clone();
/* 1859 */     c.link = (long[])this.link.clone();
/* 1860 */     c.strategy = this.strategy;
/* 1861 */     return c;
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
/* 1875 */     int h = 0;
/* 1876 */     for (int j = realSize(), i = 0, t = 0; j-- != 0; ) {
/* 1877 */       for (; this.key[i] == null; i++);
/* 1878 */       if (this != this.key[i]) t = this.strategy.hashCode(this.key[i]); 
/* 1879 */       if (this != this.value[i]) t ^= (this.value[i] == null) ? 0 : System.identityHashCode(this.value[i]); 
/* 1880 */       h += t;
/* 1881 */       i++;
/*      */     } 
/*      */     
/* 1884 */     if (this.containsNullKey) h += (this.value[this.n] == null) ? 0 : System.identityHashCode(this.value[this.n]); 
/* 1885 */     return h;
/*      */   }
/*      */   
/*      */   private void writeObject(ObjectOutputStream s) throws IOException {
/* 1889 */     K[] key = this.key;
/* 1890 */     V[] value = this.value;
/* 1891 */     EntryIterator i = new EntryIterator();
/* 1892 */     s.defaultWriteObject();
/* 1893 */     for (int j = this.size; j-- != 0; ) {
/* 1894 */       int e = i.nextEntry();
/* 1895 */       s.writeObject(key[e]);
/* 1896 */       s.writeObject(value[e]);
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   private void readObject(ObjectInputStream s) throws IOException, ClassNotFoundException {
/* 1902 */     s.defaultReadObject();
/* 1903 */     this.n = HashCommon.arraySize(this.size, this.f);
/* 1904 */     this.maxFill = HashCommon.maxFill(this.n, this.f);
/* 1905 */     this.mask = this.n - 1;
/* 1906 */     K[] key = this.key = (K[])new Object[this.n + 1];
/* 1907 */     V[] value = this.value = (V[])new Object[this.n + 1];
/* 1908 */     long[] link = this.link = new long[this.n + 1];
/* 1909 */     int prev = -1;
/* 1910 */     this.first = this.last = -1;
/*      */ 
/*      */     
/* 1913 */     for (int i = this.size; i-- != 0; ) {
/* 1914 */       int pos; K k = (K)s.readObject();
/* 1915 */       V v = (V)s.readObject();
/* 1916 */       if (this.strategy.equals(k, null)) {
/* 1917 */         pos = this.n;
/* 1918 */         this.containsNullKey = true;
/*      */       } else {
/* 1920 */         pos = HashCommon.mix(this.strategy.hashCode(k)) & this.mask;
/* 1921 */         for (; key[pos] != null; pos = pos + 1 & this.mask);
/*      */       } 
/* 1923 */       key[pos] = k;
/* 1924 */       value[pos] = v;
/* 1925 */       if (this.first != -1) {
/* 1926 */         link[prev] = link[prev] ^ (link[prev] ^ pos & 0xFFFFFFFFL) & 0xFFFFFFFFL;
/* 1927 */         link[pos] = link[pos] ^ (link[pos] ^ (prev & 0xFFFFFFFFL) << 32L) & 0xFFFFFFFF00000000L;
/* 1928 */         prev = pos; continue;
/*      */       } 
/* 1930 */       prev = this.first = pos;
/*      */       
/* 1932 */       link[pos] = link[pos] | 0xFFFFFFFF00000000L;
/*      */     } 
/*      */     
/* 1935 */     this.last = prev;
/* 1936 */     if (prev != -1)
/*      */     {
/* 1938 */       link[prev] = link[prev] | 0xFFFFFFFFL;
/*      */     }
/*      */   }
/*      */   
/*      */   private void checkTable() {}
/*      */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\i\\unimi\dsi\fastutil\objects\Object2ReferenceLinkedOpenCustomHashMap.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */