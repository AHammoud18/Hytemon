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
/*      */ 
/*      */ 
/*      */ 
/*      */ public class Reference2ObjectOpenCustomHashMap<K, V>
/*      */   extends AbstractReference2ObjectMap<K, V>
/*      */   implements Serializable, Cloneable, Hash
/*      */ {
/*      */   private static final long serialVersionUID = 0L;
/*      */   private static final boolean ASSERTS = false;
/*      */   protected transient K[] key;
/*      */   protected transient V[] value;
/*      */   protected transient int mask;
/*      */   protected transient boolean containsNullKey;
/*      */   protected Hash.Strategy<? super K> strategy;
/*      */   protected transient int n;
/*      */   protected transient int maxFill;
/*      */   protected final transient int minN;
/*      */   protected int size;
/*      */   protected final float f;
/*      */   protected transient Reference2ObjectMap.FastEntrySet<K, V> entries;
/*      */   protected transient ReferenceSet<K> keys;
/*      */   protected transient ObjectCollection<V> values;
/*      */   
/*      */   public Reference2ObjectOpenCustomHashMap(int expected, float f, Hash.Strategy<? super K> strategy) {
/*   94 */     this.strategy = strategy;
/*   95 */     if (f <= 0.0F || f >= 1.0F) throw new IllegalArgumentException("Load factor must be greater than 0 and smaller than 1"); 
/*   96 */     if (expected < 0) throw new IllegalArgumentException("The expected number of elements must be nonnegative"); 
/*   97 */     this.f = f;
/*   98 */     this.minN = this.n = HashCommon.arraySize(expected, f);
/*   99 */     this.mask = this.n - 1;
/*  100 */     this.maxFill = HashCommon.maxFill(this.n, f);
/*  101 */     this.key = (K[])new Object[this.n + 1];
/*  102 */     this.value = (V[])new Object[this.n + 1];
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Reference2ObjectOpenCustomHashMap(int expected, Hash.Strategy<? super K> strategy) {
/*  112 */     this(expected, 0.75F, strategy);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Reference2ObjectOpenCustomHashMap(Hash.Strategy<? super K> strategy) {
/*  122 */     this(16, 0.75F, strategy);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Reference2ObjectOpenCustomHashMap(Map<? extends K, ? extends V> m, float f, Hash.Strategy<? super K> strategy) {
/*  133 */     this(m.size(), f, strategy);
/*  134 */     putAll(m);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Reference2ObjectOpenCustomHashMap(Map<? extends K, ? extends V> m, Hash.Strategy<? super K> strategy) {
/*  144 */     this(m, 0.75F, strategy);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Reference2ObjectOpenCustomHashMap(Reference2ObjectMap<K, V> m, float f, Hash.Strategy<? super K> strategy) {
/*  155 */     this(m.size(), f, strategy);
/*  156 */     putAll(m);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Reference2ObjectOpenCustomHashMap(Reference2ObjectMap<K, V> m, Hash.Strategy<? super K> strategy) {
/*  167 */     this(m, 0.75F, strategy);
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
/*      */   public Reference2ObjectOpenCustomHashMap(K[] k, V[] v, float f, Hash.Strategy<? super K> strategy) {
/*  180 */     this(k.length, f, strategy);
/*  181 */     if (k.length != v.length) throw new IllegalArgumentException("The key array and the value array have different lengths (" + k.length + " and " + v.length + ")"); 
/*  182 */     for (int i = 0; i < k.length; ) { put(k[i], v[i]); i++; }
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
/*      */   public Reference2ObjectOpenCustomHashMap(K[] k, V[] v, Hash.Strategy<? super K> strategy) {
/*  195 */     this(k, v, 0.75F, strategy);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Hash.Strategy<? super K> strategy() {
/*  204 */     return this.strategy;
/*      */   }
/*      */   
/*      */   private int realSize() {
/*  208 */     return this.containsNullKey ? (this.size - 1) : this.size;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void ensureCapacity(int capacity) {
/*  218 */     int needed = HashCommon.arraySize(capacity, this.f);
/*  219 */     if (needed > this.n) rehash(needed); 
/*      */   }
/*      */   
/*      */   private void tryCapacity(long capacity) {
/*  223 */     int needed = (int)Math.min(1073741824L, Math.max(2L, HashCommon.nextPowerOfTwo((long)Math.ceil(((float)capacity / this.f)))));
/*  224 */     if (needed > this.n) rehash(needed); 
/*      */   }
/*      */   
/*      */   private V removeEntry(int pos) {
/*  228 */     V oldValue = this.value[pos];
/*  229 */     this.value[pos] = null;
/*  230 */     this.size--;
/*  231 */     shiftKeys(pos);
/*  232 */     if (this.n > this.minN && this.size < this.maxFill / 4 && this.n > 16) rehash(this.n / 2); 
/*  233 */     return oldValue;
/*      */   }
/*      */   
/*      */   private V removeNullEntry() {
/*  237 */     this.containsNullKey = false;
/*  238 */     this.key[this.n] = null;
/*  239 */     V oldValue = this.value[this.n];
/*  240 */     this.value[this.n] = null;
/*  241 */     this.size--;
/*  242 */     if (this.n > this.minN && this.size < this.maxFill / 4 && this.n > 16) rehash(this.n / 2); 
/*  243 */     return oldValue;
/*      */   }
/*      */ 
/*      */   
/*      */   public void putAll(Map<? extends K, ? extends V> m) {
/*  248 */     if (this.f <= 0.5D) { ensureCapacity(m.size()); }
/*  249 */     else { tryCapacity((size() + m.size())); }
/*      */     
/*  251 */     super.putAll(m);
/*      */   }
/*      */ 
/*      */   
/*      */   private int find(K k) {
/*  256 */     if (this.strategy.equals(k, null)) return this.containsNullKey ? this.n : -(this.n + 1);
/*      */     
/*  258 */     K[] key = this.key;
/*      */     K curr;
/*      */     int pos;
/*  261 */     if ((curr = key[pos = HashCommon.mix(this.strategy.hashCode(k)) & this.mask]) == null) return -(pos + 1); 
/*  262 */     if (this.strategy.equals(k, curr)) return pos;
/*      */     
/*      */     while (true) {
/*  265 */       if ((curr = key[pos = pos + 1 & this.mask]) == null) return -(pos + 1); 
/*  266 */       if (this.strategy.equals(k, curr)) return pos; 
/*      */     } 
/*      */   }
/*      */   
/*      */   private void insert(int pos, K k, V v) {
/*  271 */     if (pos == this.n) this.containsNullKey = true; 
/*  272 */     this.key[pos] = k;
/*  273 */     this.value[pos] = v;
/*  274 */     if (this.size++ >= this.maxFill) rehash(HashCommon.arraySize(this.size + 1, this.f));
/*      */   
/*      */   }
/*      */ 
/*      */   
/*      */   public V put(K k, V v) {
/*  280 */     int pos = find(k);
/*  281 */     if (pos < 0) {
/*  282 */       insert(-pos - 1, k, v);
/*  283 */       return this.defRetValue;
/*      */     } 
/*  285 */     V oldValue = this.value[pos];
/*  286 */     this.value[pos] = v;
/*  287 */     return oldValue;
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
/*  300 */     K[] key = this.key; while (true) {
/*      */       K curr; int last;
/*  302 */       pos = (last = pos) + 1 & this.mask;
/*      */       while (true) {
/*  304 */         if ((curr = key[pos]) == null) {
/*  305 */           key[last] = null;
/*  306 */           this.value[last] = null;
/*      */           return;
/*      */         } 
/*  309 */         int slot = HashCommon.mix(this.strategy.hashCode(curr)) & this.mask;
/*  310 */         if ((last <= pos) ? (last >= slot || slot > pos) : (last >= slot && slot > pos))
/*  311 */           break;  pos = pos + 1 & this.mask;
/*      */       } 
/*  313 */       key[last] = curr;
/*  314 */       this.value[last] = this.value[pos];
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public V remove(Object k) {
/*  321 */     if (this.strategy.equals(k, null)) {
/*  322 */       if (this.containsNullKey) return removeNullEntry(); 
/*  323 */       return this.defRetValue;
/*      */     } 
/*      */     
/*  326 */     K[] key = this.key;
/*      */     K curr;
/*      */     int pos;
/*  329 */     if ((curr = key[pos = HashCommon.mix(this.strategy.hashCode(k)) & this.mask]) == null) return this.defRetValue; 
/*  330 */     if (this.strategy.equals(k, curr)) return removeEntry(pos); 
/*      */     while (true) {
/*  332 */       if ((curr = key[pos = pos + 1 & this.mask]) == null) return this.defRetValue; 
/*  333 */       if (this.strategy.equals(k, curr)) return removeEntry(pos);
/*      */     
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   public V get(Object k) {
/*  340 */     if (this.strategy.equals(k, null)) return this.containsNullKey ? this.value[this.n] : this.defRetValue;
/*      */     
/*  342 */     K[] key = this.key;
/*      */     K curr;
/*      */     int pos;
/*  345 */     if ((curr = key[pos = HashCommon.mix(this.strategy.hashCode(k)) & this.mask]) == null) return this.defRetValue; 
/*  346 */     if (this.strategy.equals(k, curr)) return this.value[pos];
/*      */     
/*      */     while (true) {
/*  349 */       if ((curr = key[pos = pos + 1 & this.mask]) == null) return this.defRetValue; 
/*  350 */       if (this.strategy.equals(k, curr)) return this.value[pos];
/*      */     
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   public boolean containsKey(Object k) {
/*  357 */     if (this.strategy.equals(k, null)) return this.containsNullKey;
/*      */     
/*  359 */     K[] key = this.key;
/*      */     K curr;
/*      */     int pos;
/*  362 */     if ((curr = key[pos = HashCommon.mix(this.strategy.hashCode(k)) & this.mask]) == null) return false; 
/*  363 */     if (this.strategy.equals(k, curr)) return true;
/*      */     
/*      */     while (true) {
/*  366 */       if ((curr = key[pos = pos + 1 & this.mask]) == null) return false; 
/*  367 */       if (this.strategy.equals(k, curr)) return true;
/*      */     
/*      */     } 
/*      */   }
/*      */   
/*      */   public boolean containsValue(Object v) {
/*  373 */     V[] value = this.value;
/*  374 */     K[] key = this.key;
/*  375 */     if (this.containsNullKey && Objects.equals(value[this.n], v)) return true; 
/*  376 */     for (int i = this.n; i-- != 0;) { if (key[i] != null && Objects.equals(value[i], v)) return true;  }
/*  377 */      return false;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public V getOrDefault(Object k, V defaultValue) {
/*  384 */     if (this.strategy.equals(k, null)) return this.containsNullKey ? this.value[this.n] : defaultValue;
/*      */     
/*  386 */     K[] key = this.key;
/*      */     K curr;
/*      */     int pos;
/*  389 */     if ((curr = key[pos = HashCommon.mix(this.strategy.hashCode(k)) & this.mask]) == null) return defaultValue; 
/*  390 */     if (this.strategy.equals(k, curr)) return this.value[pos];
/*      */     
/*      */     while (true) {
/*  393 */       if ((curr = key[pos = pos + 1 & this.mask]) == null) return defaultValue; 
/*  394 */       if (this.strategy.equals(k, curr)) return this.value[pos];
/*      */     
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   public V putIfAbsent(K k, V v) {
/*  401 */     int pos = find(k);
/*  402 */     if (pos >= 0) return this.value[pos]; 
/*  403 */     insert(-pos - 1, k, v);
/*  404 */     return this.defRetValue;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean remove(Object k, Object v) {
/*  411 */     if (this.strategy.equals(k, null)) {
/*  412 */       if (this.containsNullKey && Objects.equals(v, this.value[this.n])) {
/*  413 */         removeNullEntry();
/*  414 */         return true;
/*      */       } 
/*  416 */       return false;
/*      */     } 
/*      */     
/*  419 */     K[] key = this.key;
/*      */     K curr;
/*      */     int pos;
/*  422 */     if ((curr = key[pos = HashCommon.mix(this.strategy.hashCode(k)) & this.mask]) == null) return false; 
/*  423 */     if (this.strategy.equals(k, curr) && Objects.equals(v, this.value[pos])) {
/*  424 */       removeEntry(pos);
/*  425 */       return true;
/*      */     } 
/*      */     while (true) {
/*  428 */       if ((curr = key[pos = pos + 1 & this.mask]) == null) return false; 
/*  429 */       if (this.strategy.equals(k, curr) && Objects.equals(v, this.value[pos])) {
/*  430 */         removeEntry(pos);
/*  431 */         return true;
/*      */       } 
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean replace(K k, V oldValue, V v) {
/*  439 */     int pos = find(k);
/*  440 */     if (pos < 0 || !Objects.equals(oldValue, this.value[pos])) return false; 
/*  441 */     this.value[pos] = v;
/*  442 */     return true;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public V replace(K k, V v) {
/*  448 */     int pos = find(k);
/*  449 */     if (pos < 0) return this.defRetValue; 
/*  450 */     V oldValue = this.value[pos];
/*  451 */     this.value[pos] = v;
/*  452 */     return oldValue;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public V computeIfAbsent(K key, Reference2ObjectFunction<? super K, ? extends V> mappingFunction) {
/*  458 */     Objects.requireNonNull(mappingFunction);
/*  459 */     int pos = find(key);
/*  460 */     if (pos >= 0) return this.value[pos]; 
/*  461 */     if (!mappingFunction.containsKey(key)) return this.defRetValue; 
/*  462 */     V newValue = mappingFunction.get(key);
/*  463 */     insert(-pos - 1, key, newValue);
/*  464 */     return newValue;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public V computeIfPresent(K k, BiFunction<? super K, ? super V, ? extends V> remappingFunction) {
/*  470 */     Objects.requireNonNull(remappingFunction);
/*  471 */     int pos = find(k);
/*  472 */     if (pos < 0) return this.defRetValue; 
/*  473 */     if (this.value[pos] == null) return this.defRetValue; 
/*  474 */     V newValue = remappingFunction.apply(k, this.value[pos]);
/*  475 */     if (newValue == null) {
/*  476 */       if (this.strategy.equals(k, null)) { removeNullEntry(); }
/*  477 */       else { removeEntry(pos); }
/*  478 */        return this.defRetValue;
/*      */     } 
/*  480 */     this.value[pos] = newValue; return newValue;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public V compute(K k, BiFunction<? super K, ? super V, ? extends V> remappingFunction) {
/*  486 */     Objects.requireNonNull(remappingFunction);
/*  487 */     int pos = find(k);
/*  488 */     V newValue = remappingFunction.apply(k, (pos >= 0) ? this.value[pos] : null);
/*  489 */     if (newValue == null) {
/*  490 */       if (pos >= 0)
/*  491 */         if (this.strategy.equals(k, null)) { removeNullEntry(); }
/*  492 */         else { removeEntry(pos); }
/*      */          
/*  494 */       return this.defRetValue;
/*      */     } 
/*  496 */     V newVal = newValue;
/*  497 */     if (pos < 0) {
/*  498 */       insert(-pos - 1, k, newVal);
/*  499 */       return newVal;
/*      */     } 
/*  501 */     this.value[pos] = newVal; return newVal;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public V merge(K k, V v, BiFunction<? super V, ? super V, ? extends V> remappingFunction) {
/*  507 */     Objects.requireNonNull(remappingFunction);
/*  508 */     Objects.requireNonNull(v);
/*  509 */     int pos = find(k);
/*  510 */     if (pos < 0 || this.value[pos] == null) {
/*  511 */       if (pos < 0) { insert(-pos - 1, k, v); }
/*  512 */       else { this.value[pos] = v; }
/*  513 */        return v;
/*      */     } 
/*  515 */     V newValue = remappingFunction.apply(this.value[pos], v);
/*  516 */     if (newValue == null) {
/*  517 */       if (this.strategy.equals(k, null)) { removeNullEntry(); }
/*  518 */       else { removeEntry(pos); }
/*  519 */        return this.defRetValue;
/*      */     } 
/*  521 */     this.value[pos] = newValue; return newValue;
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
/*  532 */     if (this.size == 0)
/*  533 */       return;  this.size = 0;
/*  534 */     this.containsNullKey = false;
/*  535 */     Arrays.fill((Object[])this.key, (Object)null);
/*  536 */     Arrays.fill((Object[])this.value, (Object)null);
/*      */   }
/*      */ 
/*      */   
/*      */   public int size() {
/*  541 */     return this.size;
/*      */   }
/*      */ 
/*      */   
/*      */   public boolean isEmpty() {
/*  546 */     return (this.size == 0);
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   final class MapEntry
/*      */     implements Reference2ObjectMap.Entry<K, V>, Map.Entry<K, V>, ReferenceObjectPair<K, V>
/*      */   {
/*      */     int index;
/*      */ 
/*      */ 
/*      */     
/*      */     MapEntry(int index) {
/*  559 */       this.index = index;
/*      */     }
/*      */ 
/*      */     
/*      */     MapEntry() {}
/*      */ 
/*      */     
/*      */     public K getKey() {
/*  567 */       return Reference2ObjectOpenCustomHashMap.this.key[this.index];
/*      */     }
/*      */ 
/*      */     
/*      */     public K left() {
/*  572 */       return Reference2ObjectOpenCustomHashMap.this.key[this.index];
/*      */     }
/*      */ 
/*      */     
/*      */     public V getValue() {
/*  577 */       return Reference2ObjectOpenCustomHashMap.this.value[this.index];
/*      */     }
/*      */ 
/*      */     
/*      */     public V right() {
/*  582 */       return Reference2ObjectOpenCustomHashMap.this.value[this.index];
/*      */     }
/*      */ 
/*      */     
/*      */     public V setValue(V v) {
/*  587 */       V oldValue = Reference2ObjectOpenCustomHashMap.this.value[this.index];
/*  588 */       Reference2ObjectOpenCustomHashMap.this.value[this.index] = v;
/*  589 */       return oldValue;
/*      */     }
/*      */ 
/*      */     
/*      */     public ReferenceObjectPair<K, V> right(V v) {
/*  594 */       Reference2ObjectOpenCustomHashMap.this.value[this.index] = v;
/*  595 */       return this;
/*      */     }
/*      */ 
/*      */ 
/*      */     
/*      */     public boolean equals(Object o) {
/*  601 */       if (!(o instanceof Map.Entry)) return false; 
/*  602 */       Map.Entry<K, V> e = (Map.Entry<K, V>)o;
/*  603 */       return (Reference2ObjectOpenCustomHashMap.this.strategy.equals(Reference2ObjectOpenCustomHashMap.this.key[this.index], e.getKey()) && Objects.equals(Reference2ObjectOpenCustomHashMap.this.value[this.index], e.getValue()));
/*      */     }
/*      */ 
/*      */     
/*      */     public int hashCode() {
/*  608 */       return Reference2ObjectOpenCustomHashMap.this.strategy.hashCode(Reference2ObjectOpenCustomHashMap.this.key[this.index]) ^ ((Reference2ObjectOpenCustomHashMap.this.value[this.index] == null) ? 0 : Reference2ObjectOpenCustomHashMap.this.value[this.index].hashCode());
/*      */     }
/*      */ 
/*      */     
/*      */     public String toString() {
/*  613 */       return (new StringBuilder()).append(Reference2ObjectOpenCustomHashMap.this.key[this.index]).append("=>").append(Reference2ObjectOpenCustomHashMap.this.value[this.index]).toString();
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
/*  624 */     int pos = Reference2ObjectOpenCustomHashMap.this.n;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  630 */     int last = -1;
/*      */     
/*  632 */     int c = Reference2ObjectOpenCustomHashMap.this.size;
/*      */     
/*  634 */     boolean mustReturnNullKey = Reference2ObjectOpenCustomHashMap.this.containsNullKey;
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
/*  645 */       return (this.c != 0);
/*      */     }
/*      */     
/*      */     public int nextEntry() {
/*  649 */       if (!hasNext()) throw new NoSuchElementException(); 
/*  650 */       this.c--;
/*  651 */       if (this.mustReturnNullKey) {
/*  652 */         this.mustReturnNullKey = false;
/*  653 */         return this.last = Reference2ObjectOpenCustomHashMap.this.n;
/*      */       } 
/*  655 */       K[] key = Reference2ObjectOpenCustomHashMap.this.key;
/*      */       while (true) {
/*  657 */         if (--this.pos < 0) {
/*      */           
/*  659 */           this.last = Integer.MIN_VALUE;
/*  660 */           K k = this.wrapped.get(-this.pos - 1);
/*  661 */           int p = HashCommon.mix(Reference2ObjectOpenCustomHashMap.this.strategy.hashCode(k)) & Reference2ObjectOpenCustomHashMap.this.mask;
/*  662 */           for (; !Reference2ObjectOpenCustomHashMap.this.strategy.equals(k, key[p]); p = p + 1 & Reference2ObjectOpenCustomHashMap.this.mask);
/*  663 */           return p;
/*      */         } 
/*  665 */         if (key[this.pos] != null) return this.last = this.pos; 
/*      */       } 
/*      */     }
/*      */     
/*      */     public void forEachRemaining(ConsumerType action) {
/*  670 */       if (this.mustReturnNullKey) {
/*  671 */         this.mustReturnNullKey = false;
/*  672 */         acceptOnIndex(action, this.last = Reference2ObjectOpenCustomHashMap.this.n);
/*  673 */         this.c--;
/*      */       } 
/*  675 */       K[] key = Reference2ObjectOpenCustomHashMap.this.key;
/*  676 */       while (this.c != 0) {
/*  677 */         if (--this.pos < 0) {
/*      */           
/*  679 */           this.last = Integer.MIN_VALUE;
/*  680 */           K k = this.wrapped.get(-this.pos - 1);
/*  681 */           int p = HashCommon.mix(Reference2ObjectOpenCustomHashMap.this.strategy.hashCode(k)) & Reference2ObjectOpenCustomHashMap.this.mask;
/*  682 */           for (; !Reference2ObjectOpenCustomHashMap.this.strategy.equals(k, key[p]); p = p + 1 & Reference2ObjectOpenCustomHashMap.this.mask);
/*  683 */           acceptOnIndex(action, p);
/*  684 */           this.c--; continue;
/*  685 */         }  if (key[this.pos] != null) {
/*  686 */           acceptOnIndex(action, this.last = this.pos);
/*  687 */           this.c--;
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
/*  702 */       K[] key = Reference2ObjectOpenCustomHashMap.this.key; while (true) {
/*      */         K curr; int last;
/*  704 */         pos = (last = pos) + 1 & Reference2ObjectOpenCustomHashMap.this.mask;
/*      */         while (true) {
/*  706 */           if ((curr = key[pos]) == null) {
/*  707 */             key[last] = null;
/*  708 */             Reference2ObjectOpenCustomHashMap.this.value[last] = null;
/*      */             return;
/*      */           } 
/*  711 */           int slot = HashCommon.mix(Reference2ObjectOpenCustomHashMap.this.strategy.hashCode(curr)) & Reference2ObjectOpenCustomHashMap.this.mask;
/*  712 */           if ((last <= pos) ? (last >= slot || slot > pos) : (last >= slot && slot > pos))
/*  713 */             break;  pos = pos + 1 & Reference2ObjectOpenCustomHashMap.this.mask;
/*      */         } 
/*  715 */         if (pos < last) {
/*  716 */           if (this.wrapped == null) this.wrapped = new ReferenceArrayList<>(2); 
/*  717 */           this.wrapped.add(key[pos]);
/*      */         } 
/*  719 */         key[last] = curr;
/*  720 */         Reference2ObjectOpenCustomHashMap.this.value[last] = Reference2ObjectOpenCustomHashMap.this.value[pos];
/*      */       } 
/*      */     }
/*      */     
/*      */     public void remove() {
/*  725 */       if (this.last == -1) throw new IllegalStateException(); 
/*  726 */       if (this.last == Reference2ObjectOpenCustomHashMap.this.n)
/*  727 */       { Reference2ObjectOpenCustomHashMap.this.containsNullKey = false;
/*  728 */         Reference2ObjectOpenCustomHashMap.this.key[Reference2ObjectOpenCustomHashMap.this.n] = null;
/*  729 */         Reference2ObjectOpenCustomHashMap.this.value[Reference2ObjectOpenCustomHashMap.this.n] = null; }
/*  730 */       else if (this.pos >= 0) { shiftKeys(this.last); }
/*      */       else
/*      */       
/*  733 */       { Reference2ObjectOpenCustomHashMap.this.remove(this.wrapped.set(-this.pos - 1, null));
/*  734 */         this.last = -1;
/*      */         return; }
/*      */       
/*  737 */       Reference2ObjectOpenCustomHashMap.this.size--;
/*  738 */       this.last = -1;
/*      */     }
/*      */ 
/*      */     
/*      */     public int skip(int n) {
/*  743 */       int i = n;
/*  744 */       for (; i-- != 0 && hasNext(); nextEntry());
/*  745 */       return n - i - 1;
/*      */     }
/*      */     
/*      */     private MapIterator() {}
/*      */     
/*      */     abstract void acceptOnIndex(ConsumerType param1ConsumerType, int param1Int);
/*      */   }
/*      */   
/*      */   private final class EntryIterator extends MapIterator<Consumer<? super Reference2ObjectMap.Entry<K, V>>> implements ObjectIterator<Reference2ObjectMap.Entry<K, V>> { public Reference2ObjectOpenCustomHashMap<K, V>.MapEntry next() {
/*  754 */       return this.entry = new Reference2ObjectOpenCustomHashMap.MapEntry(nextEntry());
/*      */     }
/*      */     private Reference2ObjectOpenCustomHashMap<K, V>.MapEntry entry;
/*      */     private EntryIterator() {}
/*      */     
/*      */     final void acceptOnIndex(Consumer<? super Reference2ObjectMap.Entry<K, V>> action, int index) {
/*  760 */       action.accept(this.entry = new Reference2ObjectOpenCustomHashMap.MapEntry(index));
/*      */     }
/*      */ 
/*      */     
/*      */     public void remove() {
/*  765 */       super.remove();
/*  766 */       this.entry.index = -1;
/*      */     } }
/*      */   
/*      */   private final class FastEntryIterator extends MapIterator<Consumer<? super Reference2ObjectMap.Entry<K, V>>> implements ObjectIterator<Reference2ObjectMap.Entry<K, V>> {
/*      */     private FastEntryIterator() {
/*  771 */       this.entry = new Reference2ObjectOpenCustomHashMap.MapEntry();
/*      */     }
/*      */     private final Reference2ObjectOpenCustomHashMap<K, V>.MapEntry entry;
/*      */     public Reference2ObjectOpenCustomHashMap<K, V>.MapEntry next() {
/*  775 */       this.entry.index = nextEntry();
/*  776 */       return this.entry;
/*      */     }
/*      */ 
/*      */ 
/*      */     
/*      */     final void acceptOnIndex(Consumer<? super Reference2ObjectMap.Entry<K, V>> action, int index) {
/*  782 */       this.entry.index = index;
/*  783 */       action.accept(this.entry);
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private abstract class MapSpliterator<ConsumerType, SplitType extends MapSpliterator<ConsumerType, SplitType>>
/*      */   {
/*  792 */     int pos = 0;
/*      */     
/*  794 */     int max = Reference2ObjectOpenCustomHashMap.this.n;
/*      */     
/*  796 */     int c = 0;
/*      */     
/*  798 */     boolean mustReturnNull = Reference2ObjectOpenCustomHashMap.this.containsNullKey;
/*      */     
/*      */     boolean hasSplit = false;
/*      */     
/*      */     MapSpliterator() {}
/*      */     
/*      */     MapSpliterator(int pos, int max, boolean mustReturnNull, boolean hasSplit) {
/*  805 */       this.pos = pos;
/*  806 */       this.max = max;
/*  807 */       this.mustReturnNull = mustReturnNull;
/*  808 */       this.hasSplit = hasSplit;
/*      */     }
/*      */     
/*      */     abstract void acceptOnIndex(ConsumerType param1ConsumerType, int param1Int);
/*      */     
/*      */     abstract SplitType makeForSplit(int param1Int1, int param1Int2, boolean param1Boolean);
/*      */     
/*      */     public boolean tryAdvance(ConsumerType action) {
/*  816 */       if (this.mustReturnNull) {
/*  817 */         this.mustReturnNull = false;
/*  818 */         this.c++;
/*  819 */         acceptOnIndex(action, Reference2ObjectOpenCustomHashMap.this.n);
/*  820 */         return true;
/*      */       } 
/*  822 */       K[] key = Reference2ObjectOpenCustomHashMap.this.key;
/*  823 */       while (this.pos < this.max) {
/*  824 */         if (key[this.pos] != null) {
/*  825 */           this.c++;
/*  826 */           acceptOnIndex(action, this.pos++);
/*  827 */           return true;
/*      */         } 
/*  829 */         this.pos++;
/*      */       } 
/*  831 */       return false;
/*      */     }
/*      */     
/*      */     public void forEachRemaining(ConsumerType action) {
/*  835 */       if (this.mustReturnNull) {
/*  836 */         this.mustReturnNull = false;
/*  837 */         this.c++;
/*  838 */         acceptOnIndex(action, Reference2ObjectOpenCustomHashMap.this.n);
/*      */       } 
/*  840 */       K[] key = Reference2ObjectOpenCustomHashMap.this.key;
/*  841 */       while (this.pos < this.max) {
/*  842 */         if (key[this.pos] != null) {
/*  843 */           acceptOnIndex(action, this.pos);
/*  844 */           this.c++;
/*      */         } 
/*  846 */         this.pos++;
/*      */       } 
/*      */     }
/*      */     
/*      */     public long estimateSize() {
/*  851 */       if (!this.hasSplit)
/*      */       {
/*  853 */         return (Reference2ObjectOpenCustomHashMap.this.size - this.c);
/*      */       }
/*      */ 
/*      */ 
/*      */       
/*  858 */       return Math.min((Reference2ObjectOpenCustomHashMap.this.size - this.c), (long)(Reference2ObjectOpenCustomHashMap.this.realSize() / Reference2ObjectOpenCustomHashMap.this.n * (this.max - this.pos)) + (this.mustReturnNull ? 1L : 0L));
/*      */     }
/*      */ 
/*      */     
/*      */     public SplitType trySplit() {
/*  863 */       if (this.pos >= this.max - 1) return null; 
/*  864 */       int retLen = this.max - this.pos >> 1;
/*  865 */       if (retLen <= 1) return null; 
/*  866 */       int myNewPos = this.pos + retLen;
/*  867 */       int retPos = this.pos;
/*  868 */       int retMax = myNewPos;
/*      */ 
/*      */ 
/*      */       
/*  872 */       SplitType split = makeForSplit(retPos, retMax, this.mustReturnNull);
/*  873 */       this.pos = myNewPos;
/*  874 */       this.mustReturnNull = false;
/*  875 */       this.hasSplit = true;
/*  876 */       return split;
/*      */     }
/*      */     
/*      */     public long skip(long n) {
/*  880 */       if (n < 0L) throw new IllegalArgumentException("Argument must be nonnegative: " + n); 
/*  881 */       if (n == 0L) return 0L; 
/*  882 */       long skipped = 0L;
/*  883 */       if (this.mustReturnNull) {
/*  884 */         this.mustReturnNull = false;
/*  885 */         skipped++;
/*  886 */         n--;
/*      */       } 
/*  888 */       K[] key = Reference2ObjectOpenCustomHashMap.this.key;
/*  889 */       while (this.pos < this.max && n > 0L) {
/*  890 */         if (key[this.pos++] != null) {
/*  891 */           skipped++;
/*  892 */           n--;
/*      */         } 
/*      */       } 
/*  895 */       return skipped;
/*      */     }
/*      */   }
/*      */   
/*      */   private final class EntrySpliterator
/*      */     extends MapSpliterator<Consumer<? super Reference2ObjectMap.Entry<K, V>>, EntrySpliterator> implements ObjectSpliterator<Reference2ObjectMap.Entry<K, V>> {
/*      */     private static final int POST_SPLIT_CHARACTERISTICS = 1;
/*      */     
/*      */     EntrySpliterator() {}
/*      */     
/*      */     EntrySpliterator(int pos, int max, boolean mustReturnNull, boolean hasSplit) {
/*  906 */       super(pos, max, mustReturnNull, hasSplit);
/*      */     }
/*      */ 
/*      */     
/*      */     public int characteristics() {
/*  911 */       return this.hasSplit ? 1 : 65;
/*      */     }
/*      */ 
/*      */     
/*      */     final void acceptOnIndex(Consumer<? super Reference2ObjectMap.Entry<K, V>> action, int index) {
/*  916 */       action.accept(new Reference2ObjectOpenCustomHashMap.MapEntry(index));
/*      */     }
/*      */ 
/*      */     
/*      */     final EntrySpliterator makeForSplit(int pos, int max, boolean mustReturnNull) {
/*  921 */       return new EntrySpliterator(pos, max, mustReturnNull, true);
/*      */     } }
/*      */   
/*      */   private final class MapEntrySet extends AbstractObjectSet<Reference2ObjectMap.Entry<K, V>> implements Reference2ObjectMap.FastEntrySet<K, V> {
/*      */     private MapEntrySet() {}
/*      */     
/*      */     public ObjectIterator<Reference2ObjectMap.Entry<K, V>> iterator() {
/*  928 */       return new Reference2ObjectOpenCustomHashMap.EntryIterator();
/*      */     }
/*      */ 
/*      */     
/*      */     public ObjectIterator<Reference2ObjectMap.Entry<K, V>> fastIterator() {
/*  933 */       return new Reference2ObjectOpenCustomHashMap.FastEntryIterator();
/*      */     }
/*      */ 
/*      */     
/*      */     public ObjectSpliterator<Reference2ObjectMap.Entry<K, V>> spliterator() {
/*  938 */       return new Reference2ObjectOpenCustomHashMap.EntrySpliterator();
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public boolean contains(Object o) {
/*  945 */       if (!(o instanceof Map.Entry)) return false; 
/*  946 */       Map.Entry<?, ?> e = (Map.Entry<?, ?>)o;
/*  947 */       K k = (K)e.getKey();
/*  948 */       V v = (V)e.getValue();
/*  949 */       if (Reference2ObjectOpenCustomHashMap.this.strategy.equals(k, null)) return (Reference2ObjectOpenCustomHashMap.this.containsNullKey && Objects.equals(Reference2ObjectOpenCustomHashMap.this.value[Reference2ObjectOpenCustomHashMap.this.n], v));
/*      */       
/*  951 */       K[] key = Reference2ObjectOpenCustomHashMap.this.key;
/*      */       K curr;
/*      */       int pos;
/*  954 */       if ((curr = key[pos = HashCommon.mix(Reference2ObjectOpenCustomHashMap.this.strategy.hashCode(k)) & Reference2ObjectOpenCustomHashMap.this.mask]) == null) return false; 
/*  955 */       if (Reference2ObjectOpenCustomHashMap.this.strategy.equals(k, curr)) return Objects.equals(Reference2ObjectOpenCustomHashMap.this.value[pos], v);
/*      */       
/*      */       while (true) {
/*  958 */         if ((curr = key[pos = pos + 1 & Reference2ObjectOpenCustomHashMap.this.mask]) == null) return false; 
/*  959 */         if (Reference2ObjectOpenCustomHashMap.this.strategy.equals(k, curr)) return Objects.equals(Reference2ObjectOpenCustomHashMap.this.value[pos], v);
/*      */       
/*      */       } 
/*      */     }
/*      */ 
/*      */     
/*      */     public boolean remove(Object o) {
/*  966 */       if (!(o instanceof Map.Entry)) return false; 
/*  967 */       Map.Entry<?, ?> e = (Map.Entry<?, ?>)o;
/*  968 */       K k = (K)e.getKey();
/*  969 */       V v = (V)e.getValue();
/*  970 */       if (Reference2ObjectOpenCustomHashMap.this.strategy.equals(k, null)) {
/*  971 */         if (Reference2ObjectOpenCustomHashMap.this.containsNullKey && Objects.equals(Reference2ObjectOpenCustomHashMap.this.value[Reference2ObjectOpenCustomHashMap.this.n], v)) {
/*  972 */           Reference2ObjectOpenCustomHashMap.this.removeNullEntry();
/*  973 */           return true;
/*      */         } 
/*  975 */         return false;
/*      */       } 
/*      */       
/*  978 */       K[] key = Reference2ObjectOpenCustomHashMap.this.key;
/*      */       K curr;
/*      */       int pos;
/*  981 */       if ((curr = key[pos = HashCommon.mix(Reference2ObjectOpenCustomHashMap.this.strategy.hashCode(k)) & Reference2ObjectOpenCustomHashMap.this.mask]) == null) return false; 
/*  982 */       if (Reference2ObjectOpenCustomHashMap.this.strategy.equals(curr, k)) {
/*  983 */         if (Objects.equals(Reference2ObjectOpenCustomHashMap.this.value[pos], v)) {
/*  984 */           Reference2ObjectOpenCustomHashMap.this.removeEntry(pos);
/*  985 */           return true;
/*      */         } 
/*  987 */         return false;
/*      */       } 
/*      */       while (true) {
/*  990 */         if ((curr = key[pos = pos + 1 & Reference2ObjectOpenCustomHashMap.this.mask]) == null) return false; 
/*  991 */         if (Reference2ObjectOpenCustomHashMap.this.strategy.equals(curr, k) && 
/*  992 */           Objects.equals(Reference2ObjectOpenCustomHashMap.this.value[pos], v)) {
/*  993 */           Reference2ObjectOpenCustomHashMap.this.removeEntry(pos);
/*  994 */           return true;
/*      */         } 
/*      */       } 
/*      */     }
/*      */ 
/*      */ 
/*      */     
/*      */     public int size() {
/* 1002 */       return Reference2ObjectOpenCustomHashMap.this.size;
/*      */     }
/*      */ 
/*      */     
/*      */     public void clear() {
/* 1007 */       Reference2ObjectOpenCustomHashMap.this.clear();
/*      */     }
/*      */ 
/*      */ 
/*      */     
/*      */     public void forEach(Consumer<? super Reference2ObjectMap.Entry<K, V>> consumer) {
/* 1013 */       if (Reference2ObjectOpenCustomHashMap.this.containsNullKey) consumer.accept(new Reference2ObjectOpenCustomHashMap.MapEntry(Reference2ObjectOpenCustomHashMap.this.n)); 
/* 1014 */       for (int pos = Reference2ObjectOpenCustomHashMap.this.n; pos-- != 0;) { if (Reference2ObjectOpenCustomHashMap.this.key[pos] != null) consumer.accept(new Reference2ObjectOpenCustomHashMap.MapEntry(pos));
/*      */          }
/*      */     
/*      */     }
/*      */     
/*      */     public void fastForEach(Consumer<? super Reference2ObjectMap.Entry<K, V>> consumer) {
/* 1020 */       Reference2ObjectOpenCustomHashMap<K, V>.MapEntry entry = new Reference2ObjectOpenCustomHashMap.MapEntry();
/* 1021 */       if (Reference2ObjectOpenCustomHashMap.this.containsNullKey) {
/* 1022 */         entry.index = Reference2ObjectOpenCustomHashMap.this.n;
/* 1023 */         consumer.accept(entry);
/*      */       } 
/* 1025 */       for (int pos = Reference2ObjectOpenCustomHashMap.this.n; pos-- != 0;) { if (Reference2ObjectOpenCustomHashMap.this.key[pos] != null) {
/* 1026 */           entry.index = pos;
/* 1027 */           consumer.accept(entry);
/*      */         }  }
/*      */     
/*      */     }
/*      */   }
/*      */   
/*      */   public Reference2ObjectMap.FastEntrySet<K, V> reference2ObjectEntrySet() {
/* 1034 */     if (this.entries == null) this.entries = new MapEntrySet(); 
/* 1035 */     return this.entries;
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
/* 1056 */       action.accept(Reference2ObjectOpenCustomHashMap.this.key[index]);
/*      */     }
/*      */ 
/*      */     
/*      */     public K next() {
/* 1061 */       return Reference2ObjectOpenCustomHashMap.this.key[nextEntry()];
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
/* 1072 */       super(pos, max, mustReturnNull, hasSplit);
/*      */     }
/*      */ 
/*      */     
/*      */     public int characteristics() {
/* 1077 */       return this.hasSplit ? 1 : 65;
/*      */     }
/*      */ 
/*      */     
/*      */     final void acceptOnIndex(Consumer<? super K> action, int index) {
/* 1082 */       action.accept(Reference2ObjectOpenCustomHashMap.this.key[index]);
/*      */     }
/*      */ 
/*      */     
/*      */     final KeySpliterator makeForSplit(int pos, int max, boolean mustReturnNull) {
/* 1087 */       return new KeySpliterator(pos, max, mustReturnNull, true);
/*      */     } }
/*      */   
/*      */   private final class KeySet extends AbstractReferenceSet<K> {
/*      */     private KeySet() {}
/*      */     
/*      */     public ObjectIterator<K> iterator() {
/* 1094 */       return new Reference2ObjectOpenCustomHashMap.KeyIterator();
/*      */     }
/*      */ 
/*      */     
/*      */     public ObjectSpliterator<K> spliterator() {
/* 1099 */       return new Reference2ObjectOpenCustomHashMap.KeySpliterator();
/*      */     }
/*      */ 
/*      */ 
/*      */     
/*      */     public void forEach(Consumer<? super K> consumer) {
/* 1105 */       if (Reference2ObjectOpenCustomHashMap.this.containsNullKey) consumer.accept(Reference2ObjectOpenCustomHashMap.this.key[Reference2ObjectOpenCustomHashMap.this.n]); 
/* 1106 */       for (int pos = Reference2ObjectOpenCustomHashMap.this.n; pos-- != 0; ) {
/* 1107 */         K k = Reference2ObjectOpenCustomHashMap.this.key[pos];
/* 1108 */         if (k != null) consumer.accept(k);
/*      */       
/*      */       } 
/*      */     }
/*      */     
/*      */     public int size() {
/* 1114 */       return Reference2ObjectOpenCustomHashMap.this.size;
/*      */     }
/*      */ 
/*      */     
/*      */     public boolean contains(Object k) {
/* 1119 */       return Reference2ObjectOpenCustomHashMap.this.containsKey(k);
/*      */     }
/*      */ 
/*      */     
/*      */     public boolean remove(Object k) {
/* 1124 */       int oldSize = Reference2ObjectOpenCustomHashMap.this.size;
/* 1125 */       Reference2ObjectOpenCustomHashMap.this.remove(k);
/* 1126 */       return (Reference2ObjectOpenCustomHashMap.this.size != oldSize);
/*      */     }
/*      */ 
/*      */     
/*      */     public void clear() {
/* 1131 */       Reference2ObjectOpenCustomHashMap.this.clear();
/*      */     }
/*      */   }
/*      */ 
/*      */   
/*      */   public ReferenceSet<K> keySet() {
/* 1137 */     if (this.keys == null) this.keys = new KeySet(); 
/* 1138 */     return this.keys;
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
/* 1159 */       action.accept(Reference2ObjectOpenCustomHashMap.this.value[index]);
/*      */     }
/*      */ 
/*      */     
/*      */     public V next() {
/* 1164 */       return Reference2ObjectOpenCustomHashMap.this.value[nextEntry()];
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
/* 1175 */       super(pos, max, mustReturnNull, hasSplit);
/*      */     }
/*      */ 
/*      */     
/*      */     public int characteristics() {
/* 1180 */       return this.hasSplit ? 0 : 64;
/*      */     }
/*      */ 
/*      */     
/*      */     final void acceptOnIndex(Consumer<? super V> action, int index) {
/* 1185 */       action.accept(Reference2ObjectOpenCustomHashMap.this.value[index]);
/*      */     }
/*      */ 
/*      */     
/*      */     final ValueSpliterator makeForSplit(int pos, int max, boolean mustReturnNull) {
/* 1190 */       return new ValueSpliterator(pos, max, mustReturnNull, true);
/*      */     }
/*      */   }
/*      */ 
/*      */   
/*      */   public ObjectCollection<V> values() {
/* 1196 */     if (this.values == null) this.values = new AbstractObjectCollection<V>()
/*      */         {
/*      */           public ObjectIterator<V> iterator() {
/* 1199 */             return new Reference2ObjectOpenCustomHashMap.ValueIterator();
/*      */           }
/*      */ 
/*      */           
/*      */           public ObjectSpliterator<V> spliterator() {
/* 1204 */             return new Reference2ObjectOpenCustomHashMap.ValueSpliterator();
/*      */           }
/*      */ 
/*      */ 
/*      */           
/*      */           public void forEach(Consumer<? super V> consumer) {
/* 1210 */             if (Reference2ObjectOpenCustomHashMap.this.containsNullKey) consumer.accept(Reference2ObjectOpenCustomHashMap.this.value[Reference2ObjectOpenCustomHashMap.this.n]); 
/* 1211 */             for (int pos = Reference2ObjectOpenCustomHashMap.this.n; pos-- != 0;) { if (Reference2ObjectOpenCustomHashMap.this.key[pos] != null) consumer.accept(Reference2ObjectOpenCustomHashMap.this.value[pos]);  }
/*      */           
/*      */           }
/*      */           
/*      */           public int size() {
/* 1216 */             return Reference2ObjectOpenCustomHashMap.this.size;
/*      */           }
/*      */ 
/*      */           
/*      */           public boolean contains(Object v) {
/* 1221 */             return Reference2ObjectOpenCustomHashMap.this.containsValue(v);
/*      */           }
/*      */ 
/*      */           
/*      */           public void clear() {
/* 1226 */             Reference2ObjectOpenCustomHashMap.this.clear();
/*      */           }
/*      */         }; 
/* 1229 */     return this.values;
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
/* 1246 */     return trim(this.size);
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
/* 1268 */     int l = HashCommon.nextPowerOfTwo((int)Math.ceil((n / this.f)));
/* 1269 */     if (l >= this.n || this.size > HashCommon.maxFill(l, this.f)) return true; 
/*      */     try {
/* 1271 */       rehash(l);
/* 1272 */     } catch (OutOfMemoryError cantDoIt) {
/* 1273 */       return false;
/*      */     } 
/* 1275 */     return true;
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
/* 1290 */     K[] key = this.key;
/* 1291 */     V[] value = this.value;
/* 1292 */     int mask = newN - 1;
/* 1293 */     K[] newKey = (K[])new Object[newN + 1];
/* 1294 */     V[] newValue = (V[])new Object[newN + 1];
/* 1295 */     int i = this.n;
/* 1296 */     for (int j = realSize(); j-- != 0; ) {
/* 1297 */       while (key[--i] == null); int pos;
/* 1298 */       if (newKey[pos = HashCommon.mix(this.strategy.hashCode(key[i])) & mask] != null) while (newKey[pos = pos + 1 & mask] != null); 
/* 1299 */       newKey[pos] = key[i];
/* 1300 */       newValue[pos] = value[i];
/*      */     } 
/* 1302 */     newValue[newN] = value[this.n];
/* 1303 */     this.n = newN;
/* 1304 */     this.mask = mask;
/* 1305 */     this.maxFill = HashCommon.maxFill(this.n, this.f);
/* 1306 */     this.key = newKey;
/* 1307 */     this.value = newValue;
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
/*      */   public Reference2ObjectOpenCustomHashMap<K, V> clone() {
/*      */     Reference2ObjectOpenCustomHashMap<K, V> c;
/*      */     try {
/* 1324 */       c = (Reference2ObjectOpenCustomHashMap<K, V>)super.clone();
/* 1325 */     } catch (CloneNotSupportedException cantHappen) {
/* 1326 */       throw new InternalError();
/*      */     } 
/* 1328 */     c.keys = null;
/* 1329 */     c.values = null;
/* 1330 */     c.entries = null;
/* 1331 */     c.containsNullKey = this.containsNullKey;
/* 1332 */     c.key = (K[])this.key.clone();
/* 1333 */     c.value = (V[])this.value.clone();
/* 1334 */     c.strategy = this.strategy;
/* 1335 */     return c;
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
/* 1349 */     int h = 0;
/* 1350 */     for (int j = realSize(), i = 0, t = 0; j-- != 0; ) {
/* 1351 */       for (; this.key[i] == null; i++);
/* 1352 */       if (this != this.key[i]) t = this.strategy.hashCode(this.key[i]); 
/* 1353 */       if (this != this.value[i]) t ^= (this.value[i] == null) ? 0 : this.value[i].hashCode(); 
/* 1354 */       h += t;
/* 1355 */       i++;
/*      */     } 
/*      */     
/* 1358 */     if (this.containsNullKey) h += (this.value[this.n] == null) ? 0 : this.value[this.n].hashCode(); 
/* 1359 */     return h;
/*      */   }
/*      */   
/*      */   private void writeObject(ObjectOutputStream s) throws IOException {
/* 1363 */     K[] key = this.key;
/* 1364 */     V[] value = this.value;
/* 1365 */     EntryIterator i = new EntryIterator();
/* 1366 */     s.defaultWriteObject();
/* 1367 */     for (int j = this.size; j-- != 0; ) {
/* 1368 */       int e = i.nextEntry();
/* 1369 */       s.writeObject(key[e]);
/* 1370 */       s.writeObject(value[e]);
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   private void readObject(ObjectInputStream s) throws IOException, ClassNotFoundException {
/* 1376 */     s.defaultReadObject();
/* 1377 */     this.n = HashCommon.arraySize(this.size, this.f);
/* 1378 */     this.maxFill = HashCommon.maxFill(this.n, this.f);
/* 1379 */     this.mask = this.n - 1;
/* 1380 */     K[] key = this.key = (K[])new Object[this.n + 1];
/* 1381 */     V[] value = this.value = (V[])new Object[this.n + 1];
/*      */ 
/*      */     
/* 1384 */     for (int i = this.size; i-- != 0; ) {
/* 1385 */       int pos; K k = (K)s.readObject();
/* 1386 */       V v = (V)s.readObject();
/* 1387 */       if (this.strategy.equals(k, null)) {
/* 1388 */         pos = this.n;
/* 1389 */         this.containsNullKey = true;
/*      */       } else {
/* 1391 */         pos = HashCommon.mix(this.strategy.hashCode(k)) & this.mask;
/* 1392 */         for (; key[pos] != null; pos = pos + 1 & this.mask);
/*      */       } 
/* 1394 */       key[pos] = k;
/* 1395 */       value[pos] = v;
/*      */     } 
/*      */   }
/*      */   
/*      */   private void checkTable() {}
/*      */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\i\\unimi\dsi\fastutil\objects\Reference2ObjectOpenCustomHashMap.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */