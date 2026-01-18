/*      */ package it.unimi.dsi.fastutil.objects;
/*      */ 
/*      */ import it.unimi.dsi.fastutil.Hash;
/*      */ import it.unimi.dsi.fastutil.HashCommon;
/*      */ import it.unimi.dsi.fastutil.Pair;
/*      */ import java.io.IOException;
/*      */ import java.io.ObjectInputStream;
/*      */ import java.io.ObjectOutputStream;
/*      */ import java.io.Serializable;
/*      */ import java.util.Arrays;
/*      */ import java.util.Collection;
/*      */ import java.util.Iterator;
/*      */ import java.util.Map;
/*      */ import java.util.NoSuchElementException;
/*      */ import java.util.Objects;
/*      */ import java.util.Set;
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
/*      */ public class Reference2ReferenceOpenHashMap<K, V>
/*      */   extends AbstractReference2ReferenceMap<K, V>
/*      */   implements Serializable, Cloneable, Hash
/*      */ {
/*      */   private static final long serialVersionUID = 0L;
/*      */   private static final boolean ASSERTS = false;
/*      */   protected transient K[] key;
/*      */   protected transient V[] value;
/*      */   protected transient int mask;
/*      */   protected transient boolean containsNullKey;
/*      */   protected transient int n;
/*      */   protected transient int maxFill;
/*      */   protected final transient int minN;
/*      */   protected int size;
/*      */   protected final float f;
/*      */   protected transient Reference2ReferenceMap.FastEntrySet<K, V> entries;
/*      */   protected transient ReferenceSet<K> keys;
/*      */   protected transient ReferenceCollection<V> values;
/*      */   
/*      */   public Reference2ReferenceOpenHashMap(int expected, float f) {
/*   90 */     if (f <= 0.0F || f >= 1.0F) throw new IllegalArgumentException("Load factor must be greater than 0 and smaller than 1"); 
/*   91 */     if (expected < 0) throw new IllegalArgumentException("The expected number of elements must be nonnegative"); 
/*   92 */     this.f = f;
/*   93 */     this.minN = this.n = HashCommon.arraySize(expected, f);
/*   94 */     this.mask = this.n - 1;
/*   95 */     this.maxFill = HashCommon.maxFill(this.n, f);
/*   96 */     this.key = (K[])new Object[this.n + 1];
/*   97 */     this.value = (V[])new Object[this.n + 1];
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Reference2ReferenceOpenHashMap(int expected) {
/*  106 */     this(expected, 0.75F);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Reference2ReferenceOpenHashMap() {
/*  114 */     this(16, 0.75F);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Reference2ReferenceOpenHashMap(Map<? extends K, ? extends V> m, float f) {
/*  124 */     this(m.size(), f);
/*  125 */     putAll(m);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Reference2ReferenceOpenHashMap(Map<? extends K, ? extends V> m) {
/*  134 */     this(m, 0.75F);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Reference2ReferenceOpenHashMap(Reference2ReferenceMap<K, V> m, float f) {
/*  144 */     this(m.size(), f);
/*  145 */     putAll(m);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Reference2ReferenceOpenHashMap(Reference2ReferenceMap<K, V> m) {
/*  155 */     this(m, 0.75F);
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
/*      */   public Reference2ReferenceOpenHashMap(K[] k, V[] v, float f) {
/*  167 */     this(k.length, f);
/*  168 */     if (k.length != v.length) throw new IllegalArgumentException("The key array and the value array have different lengths (" + k.length + " and " + v.length + ")"); 
/*  169 */     for (int i = 0; i < k.length; ) { put(k[i], v[i]); i++; }
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
/*      */   public Reference2ReferenceOpenHashMap(K[] k, V[] v) {
/*  181 */     this(k, v, 0.75F);
/*      */   }
/*      */   
/*      */   private int realSize() {
/*  185 */     return this.containsNullKey ? (this.size - 1) : this.size;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void ensureCapacity(int capacity) {
/*  195 */     int needed = HashCommon.arraySize(capacity, this.f);
/*  196 */     if (needed > this.n) rehash(needed); 
/*      */   }
/*      */   
/*      */   private void tryCapacity(long capacity) {
/*  200 */     int needed = (int)Math.min(1073741824L, Math.max(2L, HashCommon.nextPowerOfTwo((long)Math.ceil(((float)capacity / this.f)))));
/*  201 */     if (needed > this.n) rehash(needed); 
/*      */   }
/*      */   
/*      */   private V removeEntry(int pos) {
/*  205 */     V oldValue = this.value[pos];
/*  206 */     this.value[pos] = null;
/*  207 */     this.size--;
/*  208 */     shiftKeys(pos);
/*  209 */     if (this.n > this.minN && this.size < this.maxFill / 4 && this.n > 16) rehash(this.n / 2); 
/*  210 */     return oldValue;
/*      */   }
/*      */   
/*      */   private V removeNullEntry() {
/*  214 */     this.containsNullKey = false;
/*  215 */     this.key[this.n] = null;
/*  216 */     V oldValue = this.value[this.n];
/*  217 */     this.value[this.n] = null;
/*  218 */     this.size--;
/*  219 */     if (this.n > this.minN && this.size < this.maxFill / 4 && this.n > 16) rehash(this.n / 2); 
/*  220 */     return oldValue;
/*      */   }
/*      */ 
/*      */   
/*      */   public void putAll(Map<? extends K, ? extends V> m) {
/*  225 */     if (this.f <= 0.5D) { ensureCapacity(m.size()); }
/*  226 */     else { tryCapacity((size() + m.size())); }
/*      */     
/*  228 */     super.putAll(m);
/*      */   }
/*      */ 
/*      */   
/*      */   private int find(K k) {
/*  233 */     if (k == null) return this.containsNullKey ? this.n : -(this.n + 1);
/*      */     
/*  235 */     K[] key = this.key;
/*      */     K curr;
/*      */     int pos;
/*  238 */     if ((curr = key[pos = HashCommon.mix(System.identityHashCode(k)) & this.mask]) == null) return -(pos + 1); 
/*  239 */     if (k == curr) return pos;
/*      */     
/*      */     while (true) {
/*  242 */       if ((curr = key[pos = pos + 1 & this.mask]) == null) return -(pos + 1); 
/*  243 */       if (k == curr) return pos; 
/*      */     } 
/*      */   }
/*      */   
/*      */   private void insert(int pos, K k, V v) {
/*  248 */     if (pos == this.n) this.containsNullKey = true; 
/*  249 */     this.key[pos] = k;
/*  250 */     this.value[pos] = v;
/*  251 */     if (this.size++ >= this.maxFill) rehash(HashCommon.arraySize(this.size + 1, this.f));
/*      */   
/*      */   }
/*      */ 
/*      */   
/*      */   public V put(K k, V v) {
/*  257 */     int pos = find(k);
/*  258 */     if (pos < 0) {
/*  259 */       insert(-pos - 1, k, v);
/*  260 */       return this.defRetValue;
/*      */     } 
/*  262 */     V oldValue = this.value[pos];
/*  263 */     this.value[pos] = v;
/*  264 */     return oldValue;
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
/*  277 */     K[] key = this.key; while (true) {
/*      */       K curr; int last;
/*  279 */       pos = (last = pos) + 1 & this.mask;
/*      */       while (true) {
/*  281 */         if ((curr = key[pos]) == null) {
/*  282 */           key[last] = null;
/*  283 */           this.value[last] = null;
/*      */           return;
/*      */         } 
/*  286 */         int slot = HashCommon.mix(System.identityHashCode(curr)) & this.mask;
/*  287 */         if ((last <= pos) ? (last >= slot || slot > pos) : (last >= slot && slot > pos))
/*  288 */           break;  pos = pos + 1 & this.mask;
/*      */       } 
/*  290 */       key[last] = curr;
/*  291 */       this.value[last] = this.value[pos];
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public V remove(Object k) {
/*  298 */     if (k == null) {
/*  299 */       if (this.containsNullKey) return removeNullEntry(); 
/*  300 */       return this.defRetValue;
/*      */     } 
/*      */     
/*  303 */     K[] key = this.key;
/*      */     K curr;
/*      */     int pos;
/*  306 */     if ((curr = key[pos = HashCommon.mix(System.identityHashCode(k)) & this.mask]) == null) return this.defRetValue; 
/*  307 */     if (k == curr) return removeEntry(pos); 
/*      */     while (true) {
/*  309 */       if ((curr = key[pos = pos + 1 & this.mask]) == null) return this.defRetValue; 
/*  310 */       if (k == curr) return removeEntry(pos);
/*      */     
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   public V get(Object k) {
/*  317 */     if (k == null) return this.containsNullKey ? this.value[this.n] : this.defRetValue;
/*      */     
/*  319 */     K[] key = this.key;
/*      */     K curr;
/*      */     int pos;
/*  322 */     if ((curr = key[pos = HashCommon.mix(System.identityHashCode(k)) & this.mask]) == null) return this.defRetValue; 
/*  323 */     if (k == curr) return this.value[pos];
/*      */     
/*      */     while (true) {
/*  326 */       if ((curr = key[pos = pos + 1 & this.mask]) == null) return this.defRetValue; 
/*  327 */       if (k == curr) return this.value[pos];
/*      */     
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   public boolean containsKey(Object k) {
/*  334 */     if (k == null) return this.containsNullKey;
/*      */     
/*  336 */     K[] key = this.key;
/*      */     K curr;
/*      */     int pos;
/*  339 */     if ((curr = key[pos = HashCommon.mix(System.identityHashCode(k)) & this.mask]) == null) return false; 
/*  340 */     if (k == curr) return true;
/*      */     
/*      */     while (true) {
/*  343 */       if ((curr = key[pos = pos + 1 & this.mask]) == null) return false; 
/*  344 */       if (k == curr) return true;
/*      */     
/*      */     } 
/*      */   }
/*      */   
/*      */   public boolean containsValue(Object v) {
/*  350 */     V[] value = this.value;
/*  351 */     K[] key = this.key;
/*  352 */     if (this.containsNullKey && value[this.n] == v) return true; 
/*  353 */     for (int i = this.n; i-- != 0;) { if (key[i] != null && value[i] == v) return true;  }
/*  354 */      return false;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public V getOrDefault(Object k, V defaultValue) {
/*  361 */     if (k == null) return this.containsNullKey ? this.value[this.n] : defaultValue;
/*      */     
/*  363 */     K[] key = this.key;
/*      */     K curr;
/*      */     int pos;
/*  366 */     if ((curr = key[pos = HashCommon.mix(System.identityHashCode(k)) & this.mask]) == null) return defaultValue; 
/*  367 */     if (k == curr) return this.value[pos];
/*      */     
/*      */     while (true) {
/*  370 */       if ((curr = key[pos = pos + 1 & this.mask]) == null) return defaultValue; 
/*  371 */       if (k == curr) return this.value[pos];
/*      */     
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   public V putIfAbsent(K k, V v) {
/*  378 */     int pos = find(k);
/*  379 */     if (pos >= 0) return this.value[pos]; 
/*  380 */     insert(-pos - 1, k, v);
/*  381 */     return this.defRetValue;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean remove(Object k, Object v) {
/*  388 */     if (k == null) {
/*  389 */       if (this.containsNullKey && v == this.value[this.n]) {
/*  390 */         removeNullEntry();
/*  391 */         return true;
/*      */       } 
/*  393 */       return false;
/*      */     } 
/*      */     
/*  396 */     K[] key = this.key;
/*      */     K curr;
/*      */     int pos;
/*  399 */     if ((curr = key[pos = HashCommon.mix(System.identityHashCode(k)) & this.mask]) == null) return false; 
/*  400 */     if (k == curr && v == this.value[pos]) {
/*  401 */       removeEntry(pos);
/*  402 */       return true;
/*      */     } 
/*      */     while (true) {
/*  405 */       if ((curr = key[pos = pos + 1 & this.mask]) == null) return false; 
/*  406 */       if (k == curr && v == this.value[pos]) {
/*  407 */         removeEntry(pos);
/*  408 */         return true;
/*      */       } 
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean replace(K k, V oldValue, V v) {
/*  416 */     int pos = find(k);
/*  417 */     if (pos < 0 || oldValue != this.value[pos]) return false; 
/*  418 */     this.value[pos] = v;
/*  419 */     return true;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public V replace(K k, V v) {
/*  425 */     int pos = find(k);
/*  426 */     if (pos < 0) return this.defRetValue; 
/*  427 */     V oldValue = this.value[pos];
/*  428 */     this.value[pos] = v;
/*  429 */     return oldValue;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public V computeIfAbsent(K key, Reference2ReferenceFunction<? super K, ? extends V> mappingFunction) {
/*  435 */     Objects.requireNonNull(mappingFunction);
/*  436 */     int pos = find(key);
/*  437 */     if (pos >= 0) return this.value[pos]; 
/*  438 */     if (!mappingFunction.containsKey(key)) return this.defRetValue; 
/*  439 */     V newValue = mappingFunction.get(key);
/*  440 */     insert(-pos - 1, key, newValue);
/*  441 */     return newValue;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public V computeIfPresent(K k, BiFunction<? super K, ? super V, ? extends V> remappingFunction) {
/*  447 */     Objects.requireNonNull(remappingFunction);
/*  448 */     int pos = find(k);
/*  449 */     if (pos < 0) return this.defRetValue; 
/*  450 */     if (this.value[pos] == null) return this.defRetValue; 
/*  451 */     V newValue = remappingFunction.apply(k, this.value[pos]);
/*  452 */     if (newValue == null) {
/*  453 */       if (k == null) { removeNullEntry(); }
/*  454 */       else { removeEntry(pos); }
/*  455 */        return this.defRetValue;
/*      */     } 
/*  457 */     this.value[pos] = newValue; return newValue;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public V compute(K k, BiFunction<? super K, ? super V, ? extends V> remappingFunction) {
/*  463 */     Objects.requireNonNull(remappingFunction);
/*  464 */     int pos = find(k);
/*  465 */     V newValue = remappingFunction.apply(k, (pos >= 0) ? this.value[pos] : null);
/*  466 */     if (newValue == null) {
/*  467 */       if (pos >= 0)
/*  468 */         if (k == null) { removeNullEntry(); }
/*  469 */         else { removeEntry(pos); }
/*      */          
/*  471 */       return this.defRetValue;
/*      */     } 
/*  473 */     V newVal = newValue;
/*  474 */     if (pos < 0) {
/*  475 */       insert(-pos - 1, k, newVal);
/*  476 */       return newVal;
/*      */     } 
/*  478 */     this.value[pos] = newVal; return newVal;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public V merge(K k, V v, BiFunction<? super V, ? super V, ? extends V> remappingFunction) {
/*  484 */     Objects.requireNonNull(remappingFunction);
/*  485 */     Objects.requireNonNull(v);
/*  486 */     int pos = find(k);
/*  487 */     if (pos < 0 || this.value[pos] == null) {
/*  488 */       if (pos < 0) { insert(-pos - 1, k, v); }
/*  489 */       else { this.value[pos] = v; }
/*  490 */        return v;
/*      */     } 
/*  492 */     V newValue = remappingFunction.apply(this.value[pos], v);
/*  493 */     if (newValue == null) {
/*  494 */       if (k == null) { removeNullEntry(); }
/*  495 */       else { removeEntry(pos); }
/*  496 */        return this.defRetValue;
/*      */     } 
/*  498 */     this.value[pos] = newValue; return newValue;
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
/*  509 */     if (this.size == 0)
/*  510 */       return;  this.size = 0;
/*  511 */     this.containsNullKey = false;
/*  512 */     Arrays.fill((Object[])this.key, (Object)null);
/*  513 */     Arrays.fill((Object[])this.value, (Object)null);
/*      */   }
/*      */ 
/*      */   
/*      */   public int size() {
/*  518 */     return this.size;
/*      */   }
/*      */ 
/*      */   
/*      */   public boolean isEmpty() {
/*  523 */     return (this.size == 0);
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   final class MapEntry
/*      */     implements Reference2ReferenceMap.Entry<K, V>, Map.Entry<K, V>, ReferenceReferencePair<K, V>
/*      */   {
/*      */     int index;
/*      */ 
/*      */ 
/*      */     
/*      */     MapEntry(int index) {
/*  536 */       this.index = index;
/*      */     }
/*      */ 
/*      */     
/*      */     MapEntry() {}
/*      */ 
/*      */     
/*      */     public K getKey() {
/*  544 */       return Reference2ReferenceOpenHashMap.this.key[this.index];
/*      */     }
/*      */ 
/*      */     
/*      */     public K left() {
/*  549 */       return Reference2ReferenceOpenHashMap.this.key[this.index];
/*      */     }
/*      */ 
/*      */     
/*      */     public V getValue() {
/*  554 */       return Reference2ReferenceOpenHashMap.this.value[this.index];
/*      */     }
/*      */ 
/*      */     
/*      */     public V right() {
/*  559 */       return Reference2ReferenceOpenHashMap.this.value[this.index];
/*      */     }
/*      */ 
/*      */     
/*      */     public V setValue(V v) {
/*  564 */       V oldValue = Reference2ReferenceOpenHashMap.this.value[this.index];
/*  565 */       Reference2ReferenceOpenHashMap.this.value[this.index] = v;
/*  566 */       return oldValue;
/*      */     }
/*      */ 
/*      */     
/*      */     public ReferenceReferencePair<K, V> right(V v) {
/*  571 */       Reference2ReferenceOpenHashMap.this.value[this.index] = v;
/*  572 */       return this;
/*      */     }
/*      */ 
/*      */ 
/*      */     
/*      */     public boolean equals(Object o) {
/*  578 */       if (!(o instanceof Map.Entry)) return false; 
/*  579 */       Map.Entry<K, V> e = (Map.Entry<K, V>)o;
/*  580 */       return (Reference2ReferenceOpenHashMap.this.key[this.index] == e.getKey() && Reference2ReferenceOpenHashMap.this.value[this.index] == e.getValue());
/*      */     }
/*      */ 
/*      */     
/*      */     public int hashCode() {
/*  585 */       return System.identityHashCode(Reference2ReferenceOpenHashMap.this.key[this.index]) ^ ((Reference2ReferenceOpenHashMap.this.value[this.index] == null) ? 0 : System.identityHashCode(Reference2ReferenceOpenHashMap.this.value[this.index]));
/*      */     }
/*      */ 
/*      */     
/*      */     public String toString() {
/*  590 */       return (new StringBuilder()).append(Reference2ReferenceOpenHashMap.this.key[this.index]).append("=>").append(Reference2ReferenceOpenHashMap.this.value[this.index]).toString();
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private abstract class MapIterator<ConsumerType>
/*      */   {
/*  601 */     int pos = Reference2ReferenceOpenHashMap.this.n;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  607 */     int last = -1;
/*      */     
/*  609 */     int c = Reference2ReferenceOpenHashMap.this.size;
/*      */     
/*  611 */     boolean mustReturnNullKey = Reference2ReferenceOpenHashMap.this.containsNullKey;
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     ReferenceArrayList<K> wrapped;
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public boolean hasNext() {
/*  622 */       return (this.c != 0);
/*      */     }
/*      */     
/*      */     public int nextEntry() {
/*  626 */       if (!hasNext()) throw new NoSuchElementException(); 
/*  627 */       this.c--;
/*  628 */       if (this.mustReturnNullKey) {
/*  629 */         this.mustReturnNullKey = false;
/*  630 */         return this.last = Reference2ReferenceOpenHashMap.this.n;
/*      */       } 
/*  632 */       K[] key = Reference2ReferenceOpenHashMap.this.key;
/*      */       while (true) {
/*  634 */         if (--this.pos < 0) {
/*      */           
/*  636 */           this.last = Integer.MIN_VALUE;
/*  637 */           K k = this.wrapped.get(-this.pos - 1);
/*  638 */           int p = HashCommon.mix(System.identityHashCode(k)) & Reference2ReferenceOpenHashMap.this.mask;
/*  639 */           for (; k != key[p]; p = p + 1 & Reference2ReferenceOpenHashMap.this.mask);
/*  640 */           return p;
/*      */         } 
/*  642 */         if (key[this.pos] != null) return this.last = this.pos; 
/*      */       } 
/*      */     }
/*      */     
/*      */     public void forEachRemaining(ConsumerType action) {
/*  647 */       if (this.mustReturnNullKey) {
/*  648 */         this.mustReturnNullKey = false;
/*  649 */         acceptOnIndex(action, this.last = Reference2ReferenceOpenHashMap.this.n);
/*  650 */         this.c--;
/*      */       } 
/*  652 */       K[] key = Reference2ReferenceOpenHashMap.this.key;
/*  653 */       while (this.c != 0) {
/*  654 */         if (--this.pos < 0) {
/*      */           
/*  656 */           this.last = Integer.MIN_VALUE;
/*  657 */           K k = this.wrapped.get(-this.pos - 1);
/*  658 */           int p = HashCommon.mix(System.identityHashCode(k)) & Reference2ReferenceOpenHashMap.this.mask;
/*  659 */           for (; k != key[p]; p = p + 1 & Reference2ReferenceOpenHashMap.this.mask);
/*  660 */           acceptOnIndex(action, p);
/*  661 */           this.c--; continue;
/*  662 */         }  if (key[this.pos] != null) {
/*  663 */           acceptOnIndex(action, this.last = this.pos);
/*  664 */           this.c--;
/*      */         } 
/*      */       } 
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
/*      */     private void shiftKeys(int pos) {
/*  679 */       K[] key = Reference2ReferenceOpenHashMap.this.key; while (true) {
/*      */         K curr; int last;
/*  681 */         pos = (last = pos) + 1 & Reference2ReferenceOpenHashMap.this.mask;
/*      */         while (true) {
/*  683 */           if ((curr = key[pos]) == null) {
/*  684 */             key[last] = null;
/*  685 */             Reference2ReferenceOpenHashMap.this.value[last] = null;
/*      */             return;
/*      */           } 
/*  688 */           int slot = HashCommon.mix(System.identityHashCode(curr)) & Reference2ReferenceOpenHashMap.this.mask;
/*  689 */           if ((last <= pos) ? (last >= slot || slot > pos) : (last >= slot && slot > pos))
/*  690 */             break;  pos = pos + 1 & Reference2ReferenceOpenHashMap.this.mask;
/*      */         } 
/*  692 */         if (pos < last) {
/*  693 */           if (this.wrapped == null) this.wrapped = new ReferenceArrayList<>(2); 
/*  694 */           this.wrapped.add(key[pos]);
/*      */         } 
/*  696 */         key[last] = curr;
/*  697 */         Reference2ReferenceOpenHashMap.this.value[last] = Reference2ReferenceOpenHashMap.this.value[pos];
/*      */       } 
/*      */     }
/*      */     
/*      */     public void remove() {
/*  702 */       if (this.last == -1) throw new IllegalStateException(); 
/*  703 */       if (this.last == Reference2ReferenceOpenHashMap.this.n)
/*  704 */       { Reference2ReferenceOpenHashMap.this.containsNullKey = false;
/*  705 */         Reference2ReferenceOpenHashMap.this.key[Reference2ReferenceOpenHashMap.this.n] = null;
/*  706 */         Reference2ReferenceOpenHashMap.this.value[Reference2ReferenceOpenHashMap.this.n] = null; }
/*  707 */       else if (this.pos >= 0) { shiftKeys(this.last); }
/*      */       else
/*      */       
/*  710 */       { Reference2ReferenceOpenHashMap.this.remove(this.wrapped.set(-this.pos - 1, null));
/*  711 */         this.last = -1;
/*      */         return; }
/*      */       
/*  714 */       Reference2ReferenceOpenHashMap.this.size--;
/*  715 */       this.last = -1;
/*      */     }
/*      */ 
/*      */     
/*      */     public int skip(int n) {
/*  720 */       int i = n;
/*  721 */       for (; i-- != 0 && hasNext(); nextEntry());
/*  722 */       return n - i - 1;
/*      */     }
/*      */     
/*      */     private MapIterator() {}
/*      */     
/*      */     abstract void acceptOnIndex(ConsumerType param1ConsumerType, int param1Int);
/*      */   }
/*      */   
/*      */   private final class EntryIterator extends MapIterator<Consumer<? super Reference2ReferenceMap.Entry<K, V>>> implements ObjectIterator<Reference2ReferenceMap.Entry<K, V>> { public Reference2ReferenceOpenHashMap<K, V>.MapEntry next() {
/*  731 */       return this.entry = new Reference2ReferenceOpenHashMap.MapEntry(nextEntry());
/*      */     }
/*      */     private Reference2ReferenceOpenHashMap<K, V>.MapEntry entry;
/*      */     private EntryIterator() {}
/*      */     
/*      */     final void acceptOnIndex(Consumer<? super Reference2ReferenceMap.Entry<K, V>> action, int index) {
/*  737 */       action.accept(this.entry = new Reference2ReferenceOpenHashMap.MapEntry(index));
/*      */     }
/*      */ 
/*      */     
/*      */     public void remove() {
/*  742 */       super.remove();
/*  743 */       this.entry.index = -1;
/*      */     } }
/*      */   
/*      */   private final class FastEntryIterator extends MapIterator<Consumer<? super Reference2ReferenceMap.Entry<K, V>>> implements ObjectIterator<Reference2ReferenceMap.Entry<K, V>> {
/*      */     private FastEntryIterator() {
/*  748 */       this.entry = new Reference2ReferenceOpenHashMap.MapEntry();
/*      */     }
/*      */     private final Reference2ReferenceOpenHashMap<K, V>.MapEntry entry;
/*      */     public Reference2ReferenceOpenHashMap<K, V>.MapEntry next() {
/*  752 */       this.entry.index = nextEntry();
/*  753 */       return this.entry;
/*      */     }
/*      */ 
/*      */ 
/*      */     
/*      */     final void acceptOnIndex(Consumer<? super Reference2ReferenceMap.Entry<K, V>> action, int index) {
/*  759 */       this.entry.index = index;
/*  760 */       action.accept(this.entry);
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private abstract class MapSpliterator<ConsumerType, SplitType extends MapSpliterator<ConsumerType, SplitType>>
/*      */   {
/*  769 */     int pos = 0;
/*      */     
/*  771 */     int max = Reference2ReferenceOpenHashMap.this.n;
/*      */     
/*  773 */     int c = 0;
/*      */     
/*  775 */     boolean mustReturnNull = Reference2ReferenceOpenHashMap.this.containsNullKey;
/*      */     
/*      */     boolean hasSplit = false;
/*      */     
/*      */     MapSpliterator() {}
/*      */     
/*      */     MapSpliterator(int pos, int max, boolean mustReturnNull, boolean hasSplit) {
/*  782 */       this.pos = pos;
/*  783 */       this.max = max;
/*  784 */       this.mustReturnNull = mustReturnNull;
/*  785 */       this.hasSplit = hasSplit;
/*      */     }
/*      */     
/*      */     abstract void acceptOnIndex(ConsumerType param1ConsumerType, int param1Int);
/*      */     
/*      */     abstract SplitType makeForSplit(int param1Int1, int param1Int2, boolean param1Boolean);
/*      */     
/*      */     public boolean tryAdvance(ConsumerType action) {
/*  793 */       if (this.mustReturnNull) {
/*  794 */         this.mustReturnNull = false;
/*  795 */         this.c++;
/*  796 */         acceptOnIndex(action, Reference2ReferenceOpenHashMap.this.n);
/*  797 */         return true;
/*      */       } 
/*  799 */       K[] key = Reference2ReferenceOpenHashMap.this.key;
/*  800 */       while (this.pos < this.max) {
/*  801 */         if (key[this.pos] != null) {
/*  802 */           this.c++;
/*  803 */           acceptOnIndex(action, this.pos++);
/*  804 */           return true;
/*      */         } 
/*  806 */         this.pos++;
/*      */       } 
/*  808 */       return false;
/*      */     }
/*      */     
/*      */     public void forEachRemaining(ConsumerType action) {
/*  812 */       if (this.mustReturnNull) {
/*  813 */         this.mustReturnNull = false;
/*  814 */         this.c++;
/*  815 */         acceptOnIndex(action, Reference2ReferenceOpenHashMap.this.n);
/*      */       } 
/*  817 */       K[] key = Reference2ReferenceOpenHashMap.this.key;
/*  818 */       while (this.pos < this.max) {
/*  819 */         if (key[this.pos] != null) {
/*  820 */           acceptOnIndex(action, this.pos);
/*  821 */           this.c++;
/*      */         } 
/*  823 */         this.pos++;
/*      */       } 
/*      */     }
/*      */     
/*      */     public long estimateSize() {
/*  828 */       if (!this.hasSplit)
/*      */       {
/*  830 */         return (Reference2ReferenceOpenHashMap.this.size - this.c);
/*      */       }
/*      */ 
/*      */ 
/*      */       
/*  835 */       return Math.min((Reference2ReferenceOpenHashMap.this.size - this.c), (long)(Reference2ReferenceOpenHashMap.this.realSize() / Reference2ReferenceOpenHashMap.this.n * (this.max - this.pos)) + (this.mustReturnNull ? 1L : 0L));
/*      */     }
/*      */ 
/*      */     
/*      */     public SplitType trySplit() {
/*  840 */       if (this.pos >= this.max - 1) return null; 
/*  841 */       int retLen = this.max - this.pos >> 1;
/*  842 */       if (retLen <= 1) return null; 
/*  843 */       int myNewPos = this.pos + retLen;
/*  844 */       int retPos = this.pos;
/*  845 */       int retMax = myNewPos;
/*      */ 
/*      */ 
/*      */       
/*  849 */       SplitType split = makeForSplit(retPos, retMax, this.mustReturnNull);
/*  850 */       this.pos = myNewPos;
/*  851 */       this.mustReturnNull = false;
/*  852 */       this.hasSplit = true;
/*  853 */       return split;
/*      */     }
/*      */     
/*      */     public long skip(long n) {
/*  857 */       if (n < 0L) throw new IllegalArgumentException("Argument must be nonnegative: " + n); 
/*  858 */       if (n == 0L) return 0L; 
/*  859 */       long skipped = 0L;
/*  860 */       if (this.mustReturnNull) {
/*  861 */         this.mustReturnNull = false;
/*  862 */         skipped++;
/*  863 */         n--;
/*      */       } 
/*  865 */       K[] key = Reference2ReferenceOpenHashMap.this.key;
/*  866 */       while (this.pos < this.max && n > 0L) {
/*  867 */         if (key[this.pos++] != null) {
/*  868 */           skipped++;
/*  869 */           n--;
/*      */         } 
/*      */       } 
/*  872 */       return skipped;
/*      */     }
/*      */   }
/*      */   
/*      */   private final class EntrySpliterator
/*      */     extends MapSpliterator<Consumer<? super Reference2ReferenceMap.Entry<K, V>>, EntrySpliterator> implements ObjectSpliterator<Reference2ReferenceMap.Entry<K, V>> {
/*      */     private static final int POST_SPLIT_CHARACTERISTICS = 1;
/*      */     
/*      */     EntrySpliterator() {}
/*      */     
/*      */     EntrySpliterator(int pos, int max, boolean mustReturnNull, boolean hasSplit) {
/*  883 */       super(pos, max, mustReturnNull, hasSplit);
/*      */     }
/*      */ 
/*      */     
/*      */     public int characteristics() {
/*  888 */       return this.hasSplit ? 1 : 65;
/*      */     }
/*      */ 
/*      */     
/*      */     final void acceptOnIndex(Consumer<? super Reference2ReferenceMap.Entry<K, V>> action, int index) {
/*  893 */       action.accept(new Reference2ReferenceOpenHashMap.MapEntry(index));
/*      */     }
/*      */ 
/*      */     
/*      */     final EntrySpliterator makeForSplit(int pos, int max, boolean mustReturnNull) {
/*  898 */       return new EntrySpliterator(pos, max, mustReturnNull, true);
/*      */     } }
/*      */   
/*      */   private final class MapEntrySet extends AbstractObjectSet<Reference2ReferenceMap.Entry<K, V>> implements Reference2ReferenceMap.FastEntrySet<K, V> {
/*      */     private MapEntrySet() {}
/*      */     
/*      */     public ObjectIterator<Reference2ReferenceMap.Entry<K, V>> iterator() {
/*  905 */       return new Reference2ReferenceOpenHashMap.EntryIterator();
/*      */     }
/*      */ 
/*      */     
/*      */     public ObjectIterator<Reference2ReferenceMap.Entry<K, V>> fastIterator() {
/*  910 */       return new Reference2ReferenceOpenHashMap.FastEntryIterator();
/*      */     }
/*      */ 
/*      */     
/*      */     public ObjectSpliterator<Reference2ReferenceMap.Entry<K, V>> spliterator() {
/*  915 */       return new Reference2ReferenceOpenHashMap.EntrySpliterator();
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public boolean contains(Object o) {
/*  922 */       if (!(o instanceof Map.Entry)) return false; 
/*  923 */       Map.Entry<?, ?> e = (Map.Entry<?, ?>)o;
/*  924 */       K k = (K)e.getKey();
/*  925 */       V v = (V)e.getValue();
/*  926 */       if (k == null) return (Reference2ReferenceOpenHashMap.this.containsNullKey && Reference2ReferenceOpenHashMap.this.value[Reference2ReferenceOpenHashMap.this.n] == v);
/*      */       
/*  928 */       K[] key = Reference2ReferenceOpenHashMap.this.key;
/*      */       K curr;
/*      */       int pos;
/*  931 */       if ((curr = key[pos = HashCommon.mix(System.identityHashCode(k)) & Reference2ReferenceOpenHashMap.this.mask]) == null) return false; 
/*  932 */       if (k == curr) return (Reference2ReferenceOpenHashMap.this.value[pos] == v);
/*      */       
/*      */       while (true) {
/*  935 */         if ((curr = key[pos = pos + 1 & Reference2ReferenceOpenHashMap.this.mask]) == null) return false; 
/*  936 */         if (k == curr) return (Reference2ReferenceOpenHashMap.this.value[pos] == v);
/*      */       
/*      */       } 
/*      */     }
/*      */ 
/*      */     
/*      */     public boolean remove(Object o) {
/*  943 */       if (!(o instanceof Map.Entry)) return false; 
/*  944 */       Map.Entry<?, ?> e = (Map.Entry<?, ?>)o;
/*  945 */       K k = (K)e.getKey();
/*  946 */       V v = (V)e.getValue();
/*  947 */       if (k == null) {
/*  948 */         if (Reference2ReferenceOpenHashMap.this.containsNullKey && Reference2ReferenceOpenHashMap.this.value[Reference2ReferenceOpenHashMap.this.n] == v) {
/*  949 */           Reference2ReferenceOpenHashMap.this.removeNullEntry();
/*  950 */           return true;
/*      */         } 
/*  952 */         return false;
/*      */       } 
/*      */       
/*  955 */       K[] key = Reference2ReferenceOpenHashMap.this.key;
/*      */       K curr;
/*      */       int pos;
/*  958 */       if ((curr = key[pos = HashCommon.mix(System.identityHashCode(k)) & Reference2ReferenceOpenHashMap.this.mask]) == null) return false; 
/*  959 */       if (curr == k) {
/*  960 */         if (Reference2ReferenceOpenHashMap.this.value[pos] == v) {
/*  961 */           Reference2ReferenceOpenHashMap.this.removeEntry(pos);
/*  962 */           return true;
/*      */         } 
/*  964 */         return false;
/*      */       } 
/*      */       while (true) {
/*  967 */         if ((curr = key[pos = pos + 1 & Reference2ReferenceOpenHashMap.this.mask]) == null) return false; 
/*  968 */         if (curr == k && 
/*  969 */           Reference2ReferenceOpenHashMap.this.value[pos] == v) {
/*  970 */           Reference2ReferenceOpenHashMap.this.removeEntry(pos);
/*  971 */           return true;
/*      */         } 
/*      */       } 
/*      */     }
/*      */ 
/*      */ 
/*      */     
/*      */     public int size() {
/*  979 */       return Reference2ReferenceOpenHashMap.this.size;
/*      */     }
/*      */ 
/*      */     
/*      */     public void clear() {
/*  984 */       Reference2ReferenceOpenHashMap.this.clear();
/*      */     }
/*      */ 
/*      */ 
/*      */     
/*      */     public void forEach(Consumer<? super Reference2ReferenceMap.Entry<K, V>> consumer) {
/*  990 */       if (Reference2ReferenceOpenHashMap.this.containsNullKey) consumer.accept(new Reference2ReferenceOpenHashMap.MapEntry(Reference2ReferenceOpenHashMap.this.n)); 
/*  991 */       for (int pos = Reference2ReferenceOpenHashMap.this.n; pos-- != 0;) { if (Reference2ReferenceOpenHashMap.this.key[pos] != null) consumer.accept(new Reference2ReferenceOpenHashMap.MapEntry(pos));
/*      */          }
/*      */     
/*      */     }
/*      */     
/*      */     public void fastForEach(Consumer<? super Reference2ReferenceMap.Entry<K, V>> consumer) {
/*  997 */       Reference2ReferenceOpenHashMap<K, V>.MapEntry entry = new Reference2ReferenceOpenHashMap.MapEntry();
/*  998 */       if (Reference2ReferenceOpenHashMap.this.containsNullKey) {
/*  999 */         entry.index = Reference2ReferenceOpenHashMap.this.n;
/* 1000 */         consumer.accept(entry);
/*      */       } 
/* 1002 */       for (int pos = Reference2ReferenceOpenHashMap.this.n; pos-- != 0;) { if (Reference2ReferenceOpenHashMap.this.key[pos] != null) {
/* 1003 */           entry.index = pos;
/* 1004 */           consumer.accept(entry);
/*      */         }  }
/*      */     
/*      */     }
/*      */   }
/*      */   
/*      */   public Reference2ReferenceMap.FastEntrySet<K, V> reference2ReferenceEntrySet() {
/* 1011 */     if (this.entries == null) this.entries = new MapEntrySet(); 
/* 1012 */     return this.entries;
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
/*      */   private final class KeyIterator
/*      */     extends MapIterator<Consumer<? super K>>
/*      */     implements ObjectIterator<K>
/*      */   {
/*      */     final void acceptOnIndex(Consumer<? super K> action, int index) {
/* 1033 */       action.accept(Reference2ReferenceOpenHashMap.this.key[index]);
/*      */     }
/*      */ 
/*      */     
/*      */     public K next() {
/* 1038 */       return Reference2ReferenceOpenHashMap.this.key[nextEntry()];
/*      */     }
/*      */   }
/*      */   
/*      */   private final class KeySpliterator
/*      */     extends MapSpliterator<Consumer<? super K>, KeySpliterator> implements ObjectSpliterator<K> {
/*      */     private static final int POST_SPLIT_CHARACTERISTICS = 1;
/*      */     
/*      */     KeySpliterator() {}
/*      */     
/*      */     KeySpliterator(int pos, int max, boolean mustReturnNull, boolean hasSplit) {
/* 1049 */       super(pos, max, mustReturnNull, hasSplit);
/*      */     }
/*      */ 
/*      */     
/*      */     public int characteristics() {
/* 1054 */       return this.hasSplit ? 1 : 65;
/*      */     }
/*      */ 
/*      */     
/*      */     final void acceptOnIndex(Consumer<? super K> action, int index) {
/* 1059 */       action.accept(Reference2ReferenceOpenHashMap.this.key[index]);
/*      */     }
/*      */ 
/*      */     
/*      */     final KeySpliterator makeForSplit(int pos, int max, boolean mustReturnNull) {
/* 1064 */       return new KeySpliterator(pos, max, mustReturnNull, true);
/*      */     } }
/*      */   
/*      */   private final class KeySet extends AbstractReferenceSet<K> {
/*      */     private KeySet() {}
/*      */     
/*      */     public ObjectIterator<K> iterator() {
/* 1071 */       return new Reference2ReferenceOpenHashMap.KeyIterator();
/*      */     }
/*      */ 
/*      */     
/*      */     public ObjectSpliterator<K> spliterator() {
/* 1076 */       return new Reference2ReferenceOpenHashMap.KeySpliterator();
/*      */     }
/*      */ 
/*      */ 
/*      */     
/*      */     public void forEach(Consumer<? super K> consumer) {
/* 1082 */       if (Reference2ReferenceOpenHashMap.this.containsNullKey) consumer.accept(Reference2ReferenceOpenHashMap.this.key[Reference2ReferenceOpenHashMap.this.n]); 
/* 1083 */       for (int pos = Reference2ReferenceOpenHashMap.this.n; pos-- != 0; ) {
/* 1084 */         K k = Reference2ReferenceOpenHashMap.this.key[pos];
/* 1085 */         if (k != null) consumer.accept(k);
/*      */       
/*      */       } 
/*      */     }
/*      */     
/*      */     public int size() {
/* 1091 */       return Reference2ReferenceOpenHashMap.this.size;
/*      */     }
/*      */ 
/*      */     
/*      */     public boolean contains(Object k) {
/* 1096 */       return Reference2ReferenceOpenHashMap.this.containsKey(k);
/*      */     }
/*      */ 
/*      */     
/*      */     public boolean remove(Object k) {
/* 1101 */       int oldSize = Reference2ReferenceOpenHashMap.this.size;
/* 1102 */       Reference2ReferenceOpenHashMap.this.remove(k);
/* 1103 */       return (Reference2ReferenceOpenHashMap.this.size != oldSize);
/*      */     }
/*      */ 
/*      */     
/*      */     public void clear() {
/* 1108 */       Reference2ReferenceOpenHashMap.this.clear();
/*      */     }
/*      */   }
/*      */ 
/*      */   
/*      */   public ReferenceSet<K> keySet() {
/* 1114 */     if (this.keys == null) this.keys = new KeySet(); 
/* 1115 */     return this.keys;
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
/*      */   private final class ValueIterator
/*      */     extends MapIterator<Consumer<? super V>>
/*      */     implements ObjectIterator<V>
/*      */   {
/*      */     final void acceptOnIndex(Consumer<? super V> action, int index) {
/* 1136 */       action.accept(Reference2ReferenceOpenHashMap.this.value[index]);
/*      */     }
/*      */ 
/*      */     
/*      */     public V next() {
/* 1141 */       return Reference2ReferenceOpenHashMap.this.value[nextEntry()];
/*      */     }
/*      */   }
/*      */   
/*      */   private final class ValueSpliterator
/*      */     extends MapSpliterator<Consumer<? super V>, ValueSpliterator> implements ObjectSpliterator<V> {
/*      */     private static final int POST_SPLIT_CHARACTERISTICS = 0;
/*      */     
/*      */     ValueSpliterator() {}
/*      */     
/*      */     ValueSpliterator(int pos, int max, boolean mustReturnNull, boolean hasSplit) {
/* 1152 */       super(pos, max, mustReturnNull, hasSplit);
/*      */     }
/*      */ 
/*      */     
/*      */     public int characteristics() {
/* 1157 */       return this.hasSplit ? 0 : 64;
/*      */     }
/*      */ 
/*      */     
/*      */     final void acceptOnIndex(Consumer<? super V> action, int index) {
/* 1162 */       action.accept(Reference2ReferenceOpenHashMap.this.value[index]);
/*      */     }
/*      */ 
/*      */     
/*      */     final ValueSpliterator makeForSplit(int pos, int max, boolean mustReturnNull) {
/* 1167 */       return new ValueSpliterator(pos, max, mustReturnNull, true);
/*      */     }
/*      */   }
/*      */ 
/*      */   
/*      */   public ReferenceCollection<V> values() {
/* 1173 */     if (this.values == null) this.values = new AbstractReferenceCollection<V>()
/*      */         {
/*      */           public ObjectIterator<V> iterator() {
/* 1176 */             return new Reference2ReferenceOpenHashMap.ValueIterator();
/*      */           }
/*      */ 
/*      */           
/*      */           public ObjectSpliterator<V> spliterator() {
/* 1181 */             return new Reference2ReferenceOpenHashMap.ValueSpliterator();
/*      */           }
/*      */ 
/*      */ 
/*      */           
/*      */           public void forEach(Consumer<? super V> consumer) {
/* 1187 */             if (Reference2ReferenceOpenHashMap.this.containsNullKey) consumer.accept(Reference2ReferenceOpenHashMap.this.value[Reference2ReferenceOpenHashMap.this.n]); 
/* 1188 */             for (int pos = Reference2ReferenceOpenHashMap.this.n; pos-- != 0;) { if (Reference2ReferenceOpenHashMap.this.key[pos] != null) consumer.accept(Reference2ReferenceOpenHashMap.this.value[pos]);  }
/*      */           
/*      */           }
/*      */           
/*      */           public int size() {
/* 1193 */             return Reference2ReferenceOpenHashMap.this.size;
/*      */           }
/*      */ 
/*      */           
/*      */           public boolean contains(Object v) {
/* 1198 */             return Reference2ReferenceOpenHashMap.this.containsValue(v);
/*      */           }
/*      */ 
/*      */           
/*      */           public void clear() {
/* 1203 */             Reference2ReferenceOpenHashMap.this.clear();
/*      */           }
/*      */         }; 
/* 1206 */     return this.values;
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
/* 1223 */     return trim(this.size);
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
/* 1245 */     int l = HashCommon.nextPowerOfTwo((int)Math.ceil((n / this.f)));
/* 1246 */     if (l >= this.n || this.size > HashCommon.maxFill(l, this.f)) return true; 
/*      */     try {
/* 1248 */       rehash(l);
/* 1249 */     } catch (OutOfMemoryError cantDoIt) {
/* 1250 */       return false;
/*      */     } 
/* 1252 */     return true;
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
/* 1267 */     K[] key = this.key;
/* 1268 */     V[] value = this.value;
/* 1269 */     int mask = newN - 1;
/* 1270 */     K[] newKey = (K[])new Object[newN + 1];
/* 1271 */     V[] newValue = (V[])new Object[newN + 1];
/* 1272 */     int i = this.n;
/* 1273 */     for (int j = realSize(); j-- != 0; ) {
/* 1274 */       while (key[--i] == null); int pos;
/* 1275 */       if (newKey[pos = HashCommon.mix(System.identityHashCode(key[i])) & mask] != null) while (newKey[pos = pos + 1 & mask] != null); 
/* 1276 */       newKey[pos] = key[i];
/* 1277 */       newValue[pos] = value[i];
/*      */     } 
/* 1279 */     newValue[newN] = value[this.n];
/* 1280 */     this.n = newN;
/* 1281 */     this.mask = mask;
/* 1282 */     this.maxFill = HashCommon.maxFill(this.n, this.f);
/* 1283 */     this.key = newKey;
/* 1284 */     this.value = newValue;
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
/*      */   public Reference2ReferenceOpenHashMap<K, V> clone() {
/*      */     Reference2ReferenceOpenHashMap<K, V> c;
/*      */     try {
/* 1301 */       c = (Reference2ReferenceOpenHashMap<K, V>)super.clone();
/* 1302 */     } catch (CloneNotSupportedException cantHappen) {
/* 1303 */       throw new InternalError();
/*      */     } 
/* 1305 */     c.keys = null;
/* 1306 */     c.values = null;
/* 1307 */     c.entries = null;
/* 1308 */     c.containsNullKey = this.containsNullKey;
/* 1309 */     c.key = (K[])this.key.clone();
/* 1310 */     c.value = (V[])this.value.clone();
/* 1311 */     return c;
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
/* 1325 */     int h = 0;
/* 1326 */     for (int j = realSize(), i = 0, t = 0; j-- != 0; ) {
/* 1327 */       for (; this.key[i] == null; i++);
/* 1328 */       if (this != this.key[i]) t = System.identityHashCode(this.key[i]); 
/* 1329 */       if (this != this.value[i]) t ^= (this.value[i] == null) ? 0 : System.identityHashCode(this.value[i]); 
/* 1330 */       h += t;
/* 1331 */       i++;
/*      */     } 
/*      */     
/* 1334 */     if (this.containsNullKey) h += (this.value[this.n] == null) ? 0 : System.identityHashCode(this.value[this.n]); 
/* 1335 */     return h;
/*      */   }
/*      */   
/*      */   private void writeObject(ObjectOutputStream s) throws IOException {
/* 1339 */     K[] key = this.key;
/* 1340 */     V[] value = this.value;
/* 1341 */     EntryIterator i = new EntryIterator();
/* 1342 */     s.defaultWriteObject();
/* 1343 */     for (int j = this.size; j-- != 0; ) {
/* 1344 */       int e = i.nextEntry();
/* 1345 */       s.writeObject(key[e]);
/* 1346 */       s.writeObject(value[e]);
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   private void readObject(ObjectInputStream s) throws IOException, ClassNotFoundException {
/* 1352 */     s.defaultReadObject();
/* 1353 */     this.n = HashCommon.arraySize(this.size, this.f);
/* 1354 */     this.maxFill = HashCommon.maxFill(this.n, this.f);
/* 1355 */     this.mask = this.n - 1;
/* 1356 */     K[] key = this.key = (K[])new Object[this.n + 1];
/* 1357 */     V[] value = this.value = (V[])new Object[this.n + 1];
/*      */ 
/*      */     
/* 1360 */     for (int i = this.size; i-- != 0; ) {
/* 1361 */       int pos; K k = (K)s.readObject();
/* 1362 */       V v = (V)s.readObject();
/* 1363 */       if (k == null) {
/* 1364 */         pos = this.n;
/* 1365 */         this.containsNullKey = true;
/*      */       } else {
/* 1367 */         pos = HashCommon.mix(System.identityHashCode(k)) & this.mask;
/* 1368 */         for (; key[pos] != null; pos = pos + 1 & this.mask);
/*      */       } 
/* 1370 */       key[pos] = k;
/* 1371 */       value[pos] = v;
/*      */     } 
/*      */   }
/*      */   
/*      */   private void checkTable() {}
/*      */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\i\\unimi\dsi\fastutil\objects\Reference2ReferenceOpenHashMap.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */