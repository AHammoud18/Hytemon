/*      */ package it.unimi.dsi.fastutil.objects;
/*      */ 
/*      */ import it.unimi.dsi.fastutil.Hash;
/*      */ import it.unimi.dsi.fastutil.HashCommon;
/*      */ import it.unimi.dsi.fastutil.booleans.AbstractBooleanCollection;
/*      */ import it.unimi.dsi.fastutil.booleans.BooleanCollection;
/*      */ import it.unimi.dsi.fastutil.booleans.BooleanConsumer;
/*      */ import it.unimi.dsi.fastutil.booleans.BooleanIterator;
/*      */ import it.unimi.dsi.fastutil.booleans.BooleanSpliterator;
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
/*      */ import java.util.function.Predicate;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ public class Reference2BooleanOpenCustomHashMap<K>
/*      */   extends AbstractReference2BooleanMap<K>
/*      */   implements Serializable, Cloneable, Hash
/*      */ {
/*      */   private static final long serialVersionUID = 0L;
/*      */   private static final boolean ASSERTS = false;
/*      */   protected transient K[] key;
/*      */   protected transient boolean[] value;
/*      */   protected transient int mask;
/*      */   protected transient boolean containsNullKey;
/*      */   protected Hash.Strategy<? super K> strategy;
/*      */   protected transient int n;
/*      */   protected transient int maxFill;
/*      */   protected final transient int minN;
/*      */   protected int size;
/*      */   protected final float f;
/*      */   protected transient Reference2BooleanMap.FastEntrySet<K> entries;
/*      */   protected transient ReferenceSet<K> keys;
/*      */   protected transient BooleanCollection values;
/*      */   
/*      */   public Reference2BooleanOpenCustomHashMap(int expected, float f, Hash.Strategy<? super K> strategy) {
/*  100 */     this.strategy = strategy;
/*  101 */     if (f <= 0.0F || f >= 1.0F) throw new IllegalArgumentException("Load factor must be greater than 0 and smaller than 1"); 
/*  102 */     if (expected < 0) throw new IllegalArgumentException("The expected number of elements must be nonnegative"); 
/*  103 */     this.f = f;
/*  104 */     this.minN = this.n = HashCommon.arraySize(expected, f);
/*  105 */     this.mask = this.n - 1;
/*  106 */     this.maxFill = HashCommon.maxFill(this.n, f);
/*  107 */     this.key = (K[])new Object[this.n + 1];
/*  108 */     this.value = new boolean[this.n + 1];
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Reference2BooleanOpenCustomHashMap(int expected, Hash.Strategy<? super K> strategy) {
/*  118 */     this(expected, 0.75F, strategy);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Reference2BooleanOpenCustomHashMap(Hash.Strategy<? super K> strategy) {
/*  128 */     this(16, 0.75F, strategy);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Reference2BooleanOpenCustomHashMap(Map<? extends K, ? extends Boolean> m, float f, Hash.Strategy<? super K> strategy) {
/*  139 */     this(m.size(), f, strategy);
/*  140 */     putAll(m);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Reference2BooleanOpenCustomHashMap(Map<? extends K, ? extends Boolean> m, Hash.Strategy<? super K> strategy) {
/*  150 */     this(m, 0.75F, strategy);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Reference2BooleanOpenCustomHashMap(Reference2BooleanMap<K> m, float f, Hash.Strategy<? super K> strategy) {
/*  161 */     this(m.size(), f, strategy);
/*  162 */     putAll(m);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Reference2BooleanOpenCustomHashMap(Reference2BooleanMap<K> m, Hash.Strategy<? super K> strategy) {
/*  173 */     this(m, 0.75F, strategy);
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
/*      */   public Reference2BooleanOpenCustomHashMap(K[] k, boolean[] v, float f, Hash.Strategy<? super K> strategy) {
/*  186 */     this(k.length, f, strategy);
/*  187 */     if (k.length != v.length) throw new IllegalArgumentException("The key array and the value array have different lengths (" + k.length + " and " + v.length + ")"); 
/*  188 */     for (int i = 0; i < k.length; ) { put(k[i], v[i]); i++; }
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
/*      */   public Reference2BooleanOpenCustomHashMap(K[] k, boolean[] v, Hash.Strategy<? super K> strategy) {
/*  201 */     this(k, v, 0.75F, strategy);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Hash.Strategy<? super K> strategy() {
/*  210 */     return this.strategy;
/*      */   }
/*      */   
/*      */   private int realSize() {
/*  214 */     return this.containsNullKey ? (this.size - 1) : this.size;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void ensureCapacity(int capacity) {
/*  224 */     int needed = HashCommon.arraySize(capacity, this.f);
/*  225 */     if (needed > this.n) rehash(needed); 
/*      */   }
/*      */   
/*      */   private void tryCapacity(long capacity) {
/*  229 */     int needed = (int)Math.min(1073741824L, Math.max(2L, HashCommon.nextPowerOfTwo((long)Math.ceil(((float)capacity / this.f)))));
/*  230 */     if (needed > this.n) rehash(needed); 
/*      */   }
/*      */   
/*      */   private boolean removeEntry(int pos) {
/*  234 */     boolean oldValue = this.value[pos];
/*  235 */     this.size--;
/*  236 */     shiftKeys(pos);
/*  237 */     if (this.n > this.minN && this.size < this.maxFill / 4 && this.n > 16) rehash(this.n / 2); 
/*  238 */     return oldValue;
/*      */   }
/*      */   
/*      */   private boolean removeNullEntry() {
/*  242 */     this.containsNullKey = false;
/*  243 */     this.key[this.n] = null;
/*  244 */     boolean oldValue = this.value[this.n];
/*  245 */     this.size--;
/*  246 */     if (this.n > this.minN && this.size < this.maxFill / 4 && this.n > 16) rehash(this.n / 2); 
/*  247 */     return oldValue;
/*      */   }
/*      */ 
/*      */   
/*      */   public void putAll(Map<? extends K, ? extends Boolean> m) {
/*  252 */     if (this.f <= 0.5D) { ensureCapacity(m.size()); }
/*  253 */     else { tryCapacity((size() + m.size())); }
/*      */     
/*  255 */     super.putAll(m);
/*      */   }
/*      */ 
/*      */   
/*      */   private int find(K k) {
/*  260 */     if (this.strategy.equals(k, null)) return this.containsNullKey ? this.n : -(this.n + 1);
/*      */     
/*  262 */     K[] key = this.key;
/*      */     K curr;
/*      */     int pos;
/*  265 */     if ((curr = key[pos = HashCommon.mix(this.strategy.hashCode(k)) & this.mask]) == null) return -(pos + 1); 
/*  266 */     if (this.strategy.equals(k, curr)) return pos;
/*      */     
/*      */     while (true) {
/*  269 */       if ((curr = key[pos = pos + 1 & this.mask]) == null) return -(pos + 1); 
/*  270 */       if (this.strategy.equals(k, curr)) return pos; 
/*      */     } 
/*      */   }
/*      */   
/*      */   private void insert(int pos, K k, boolean v) {
/*  275 */     if (pos == this.n) this.containsNullKey = true; 
/*  276 */     this.key[pos] = k;
/*  277 */     this.value[pos] = v;
/*  278 */     if (this.size++ >= this.maxFill) rehash(HashCommon.arraySize(this.size + 1, this.f));
/*      */   
/*      */   }
/*      */ 
/*      */   
/*      */   public boolean put(K k, boolean v) {
/*  284 */     int pos = find(k);
/*  285 */     if (pos < 0) {
/*  286 */       insert(-pos - 1, k, v);
/*  287 */       return this.defRetValue;
/*      */     } 
/*  289 */     boolean oldValue = this.value[pos];
/*  290 */     this.value[pos] = v;
/*  291 */     return oldValue;
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
/*  304 */     K[] key = this.key; while (true) {
/*      */       K curr; int last;
/*  306 */       pos = (last = pos) + 1 & this.mask;
/*      */       while (true) {
/*  308 */         if ((curr = key[pos]) == null) {
/*  309 */           key[last] = null;
/*      */           return;
/*      */         } 
/*  312 */         int slot = HashCommon.mix(this.strategy.hashCode(curr)) & this.mask;
/*  313 */         if ((last <= pos) ? (last >= slot || slot > pos) : (last >= slot && slot > pos))
/*  314 */           break;  pos = pos + 1 & this.mask;
/*      */       } 
/*  316 */       key[last] = curr;
/*  317 */       this.value[last] = this.value[pos];
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean removeBoolean(Object k) {
/*  324 */     if (this.strategy.equals(k, null)) {
/*  325 */       if (this.containsNullKey) return removeNullEntry(); 
/*  326 */       return this.defRetValue;
/*      */     } 
/*      */     
/*  329 */     K[] key = this.key;
/*      */     K curr;
/*      */     int pos;
/*  332 */     if ((curr = key[pos = HashCommon.mix(this.strategy.hashCode(k)) & this.mask]) == null) return this.defRetValue; 
/*  333 */     if (this.strategy.equals(k, curr)) return removeEntry(pos); 
/*      */     while (true) {
/*  335 */       if ((curr = key[pos = pos + 1 & this.mask]) == null) return this.defRetValue; 
/*  336 */       if (this.strategy.equals(k, curr)) return removeEntry(pos);
/*      */     
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   public boolean getBoolean(Object k) {
/*  343 */     if (this.strategy.equals(k, null)) return this.containsNullKey ? this.value[this.n] : this.defRetValue;
/*      */     
/*  345 */     K[] key = this.key;
/*      */     K curr;
/*      */     int pos;
/*  348 */     if ((curr = key[pos = HashCommon.mix(this.strategy.hashCode(k)) & this.mask]) == null) return this.defRetValue; 
/*  349 */     if (this.strategy.equals(k, curr)) return this.value[pos];
/*      */     
/*      */     while (true) {
/*  352 */       if ((curr = key[pos = pos + 1 & this.mask]) == null) return this.defRetValue; 
/*  353 */       if (this.strategy.equals(k, curr)) return this.value[pos];
/*      */     
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   public boolean containsKey(Object k) {
/*  360 */     if (this.strategy.equals(k, null)) return this.containsNullKey;
/*      */     
/*  362 */     K[] key = this.key;
/*      */     K curr;
/*      */     int pos;
/*  365 */     if ((curr = key[pos = HashCommon.mix(this.strategy.hashCode(k)) & this.mask]) == null) return false; 
/*  366 */     if (this.strategy.equals(k, curr)) return true;
/*      */     
/*      */     while (true) {
/*  369 */       if ((curr = key[pos = pos + 1 & this.mask]) == null) return false; 
/*  370 */       if (this.strategy.equals(k, curr)) return true;
/*      */     
/*      */     } 
/*      */   }
/*      */   
/*      */   public boolean containsValue(boolean v) {
/*  376 */     boolean[] value = this.value;
/*  377 */     K[] key = this.key;
/*  378 */     if (this.containsNullKey && value[this.n] == v) return true; 
/*  379 */     for (int i = this.n; i-- != 0;) { if (key[i] != null && value[i] == v) return true;  }
/*  380 */      return false;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean getOrDefault(Object k, boolean defaultValue) {
/*  387 */     if (this.strategy.equals(k, null)) return this.containsNullKey ? this.value[this.n] : defaultValue;
/*      */     
/*  389 */     K[] key = this.key;
/*      */     K curr;
/*      */     int pos;
/*  392 */     if ((curr = key[pos = HashCommon.mix(this.strategy.hashCode(k)) & this.mask]) == null) return defaultValue; 
/*  393 */     if (this.strategy.equals(k, curr)) return this.value[pos];
/*      */     
/*      */     while (true) {
/*  396 */       if ((curr = key[pos = pos + 1 & this.mask]) == null) return defaultValue; 
/*  397 */       if (this.strategy.equals(k, curr)) return this.value[pos];
/*      */     
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   public boolean putIfAbsent(K k, boolean v) {
/*  404 */     int pos = find(k);
/*  405 */     if (pos >= 0) return this.value[pos]; 
/*  406 */     insert(-pos - 1, k, v);
/*  407 */     return this.defRetValue;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean remove(Object k, boolean v) {
/*  414 */     if (this.strategy.equals(k, null)) {
/*  415 */       if (this.containsNullKey && v == this.value[this.n]) {
/*  416 */         removeNullEntry();
/*  417 */         return true;
/*      */       } 
/*  419 */       return false;
/*      */     } 
/*      */     
/*  422 */     K[] key = this.key;
/*      */     K curr;
/*      */     int pos;
/*  425 */     if ((curr = key[pos = HashCommon.mix(this.strategy.hashCode(k)) & this.mask]) == null) return false; 
/*  426 */     if (this.strategy.equals(k, curr) && v == this.value[pos]) {
/*  427 */       removeEntry(pos);
/*  428 */       return true;
/*      */     } 
/*      */     while (true) {
/*  431 */       if ((curr = key[pos = pos + 1 & this.mask]) == null) return false; 
/*  432 */       if (this.strategy.equals(k, curr) && v == this.value[pos]) {
/*  433 */         removeEntry(pos);
/*  434 */         return true;
/*      */       } 
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean replace(K k, boolean oldValue, boolean v) {
/*  442 */     int pos = find(k);
/*  443 */     if (pos < 0 || oldValue != this.value[pos]) return false; 
/*  444 */     this.value[pos] = v;
/*  445 */     return true;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean replace(K k, boolean v) {
/*  451 */     int pos = find(k);
/*  452 */     if (pos < 0) return this.defRetValue; 
/*  453 */     boolean oldValue = this.value[pos];
/*  454 */     this.value[pos] = v;
/*  455 */     return oldValue;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean computeIfAbsent(K k, Predicate<? super K> mappingFunction) {
/*  461 */     Objects.requireNonNull(mappingFunction);
/*  462 */     int pos = find(k);
/*  463 */     if (pos >= 0) return this.value[pos]; 
/*  464 */     boolean newValue = mappingFunction.test(k);
/*  465 */     insert(-pos - 1, k, newValue);
/*  466 */     return newValue;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean computeIfAbsent(K key, Reference2BooleanFunction<? super K> mappingFunction) {
/*  472 */     Objects.requireNonNull(mappingFunction);
/*  473 */     int pos = find(key);
/*  474 */     if (pos >= 0) return this.value[pos]; 
/*  475 */     if (!mappingFunction.containsKey(key)) return this.defRetValue; 
/*  476 */     boolean newValue = mappingFunction.getBoolean(key);
/*  477 */     insert(-pos - 1, key, newValue);
/*  478 */     return newValue;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean computeBooleanIfPresent(K k, BiFunction<? super K, ? super Boolean, ? extends Boolean> remappingFunction) {
/*  484 */     Objects.requireNonNull(remappingFunction);
/*  485 */     int pos = find(k);
/*  486 */     if (pos < 0) return this.defRetValue; 
/*  487 */     Boolean newValue = remappingFunction.apply(k, Boolean.valueOf(this.value[pos]));
/*  488 */     if (newValue == null) {
/*  489 */       if (this.strategy.equals(k, null)) { removeNullEntry(); }
/*  490 */       else { removeEntry(pos); }
/*  491 */        return this.defRetValue;
/*      */     } 
/*  493 */     this.value[pos] = newValue.booleanValue(); return newValue.booleanValue();
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean computeBoolean(K k, BiFunction<? super K, ? super Boolean, ? extends Boolean> remappingFunction) {
/*  499 */     Objects.requireNonNull(remappingFunction);
/*  500 */     int pos = find(k);
/*  501 */     Boolean newValue = remappingFunction.apply(k, (pos >= 0) ? Boolean.valueOf(this.value[pos]) : null);
/*  502 */     if (newValue == null) {
/*  503 */       if (pos >= 0)
/*  504 */         if (this.strategy.equals(k, null)) { removeNullEntry(); }
/*  505 */         else { removeEntry(pos); }
/*      */          
/*  507 */       return this.defRetValue;
/*      */     } 
/*  509 */     boolean newVal = newValue.booleanValue();
/*  510 */     if (pos < 0) {
/*  511 */       insert(-pos - 1, k, newVal);
/*  512 */       return newVal;
/*      */     } 
/*  514 */     this.value[pos] = newVal; return newVal;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean merge(K k, boolean v, BiFunction<? super Boolean, ? super Boolean, ? extends Boolean> remappingFunction) {
/*  520 */     Objects.requireNonNull(remappingFunction);
/*      */     
/*  522 */     int pos = find(k);
/*  523 */     if (pos < 0) {
/*  524 */       if (pos < 0) { insert(-pos - 1, k, v); }
/*  525 */       else { this.value[pos] = v; }
/*  526 */        return v;
/*      */     } 
/*  528 */     Boolean newValue = remappingFunction.apply(Boolean.valueOf(this.value[pos]), Boolean.valueOf(v));
/*  529 */     if (newValue == null) {
/*  530 */       if (this.strategy.equals(k, null)) { removeNullEntry(); }
/*  531 */       else { removeEntry(pos); }
/*  532 */        return this.defRetValue;
/*      */     } 
/*  534 */     this.value[pos] = newValue.booleanValue(); return newValue.booleanValue();
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
/*  545 */     if (this.size == 0)
/*  546 */       return;  this.size = 0;
/*  547 */     this.containsNullKey = false;
/*  548 */     Arrays.fill((Object[])this.key, (Object)null);
/*      */   }
/*      */ 
/*      */   
/*      */   public int size() {
/*  553 */     return this.size;
/*      */   }
/*      */ 
/*      */   
/*      */   public boolean isEmpty() {
/*  558 */     return (this.size == 0);
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   final class MapEntry
/*      */     implements Reference2BooleanMap.Entry<K>, Map.Entry<K, Boolean>, ReferenceBooleanPair<K>
/*      */   {
/*      */     int index;
/*      */ 
/*      */ 
/*      */     
/*      */     MapEntry(int index) {
/*  571 */       this.index = index;
/*      */     }
/*      */ 
/*      */     
/*      */     MapEntry() {}
/*      */ 
/*      */     
/*      */     public K getKey() {
/*  579 */       return Reference2BooleanOpenCustomHashMap.this.key[this.index];
/*      */     }
/*      */ 
/*      */     
/*      */     public K left() {
/*  584 */       return Reference2BooleanOpenCustomHashMap.this.key[this.index];
/*      */     }
/*      */ 
/*      */     
/*      */     public boolean getBooleanValue() {
/*  589 */       return Reference2BooleanOpenCustomHashMap.this.value[this.index];
/*      */     }
/*      */ 
/*      */     
/*      */     public boolean rightBoolean() {
/*  594 */       return Reference2BooleanOpenCustomHashMap.this.value[this.index];
/*      */     }
/*      */ 
/*      */     
/*      */     public boolean setValue(boolean v) {
/*  599 */       boolean oldValue = Reference2BooleanOpenCustomHashMap.this.value[this.index];
/*  600 */       Reference2BooleanOpenCustomHashMap.this.value[this.index] = v;
/*  601 */       return oldValue;
/*      */     }
/*      */ 
/*      */     
/*      */     public ReferenceBooleanPair<K> right(boolean v) {
/*  606 */       Reference2BooleanOpenCustomHashMap.this.value[this.index] = v;
/*  607 */       return this;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     @Deprecated
/*      */     public Boolean getValue() {
/*  618 */       return Boolean.valueOf(Reference2BooleanOpenCustomHashMap.this.value[this.index]);
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     @Deprecated
/*      */     public Boolean setValue(Boolean v) {
/*  629 */       return Boolean.valueOf(setValue(v.booleanValue()));
/*      */     }
/*      */ 
/*      */ 
/*      */     
/*      */     public boolean equals(Object o) {
/*  635 */       if (!(o instanceof Map.Entry)) return false; 
/*  636 */       Map.Entry<K, Boolean> e = (Map.Entry<K, Boolean>)o;
/*  637 */       return (Reference2BooleanOpenCustomHashMap.this.strategy.equals(Reference2BooleanOpenCustomHashMap.this.key[this.index], e.getKey()) && Reference2BooleanOpenCustomHashMap.this.value[this.index] == ((Boolean)e.getValue()).booleanValue());
/*      */     }
/*      */ 
/*      */     
/*      */     public int hashCode() {
/*  642 */       return Reference2BooleanOpenCustomHashMap.this.strategy.hashCode(Reference2BooleanOpenCustomHashMap.this.key[this.index]) ^ (Reference2BooleanOpenCustomHashMap.this.value[this.index] ? 1231 : 1237);
/*      */     }
/*      */ 
/*      */     
/*      */     public String toString() {
/*  647 */       return (new StringBuilder()).append(Reference2BooleanOpenCustomHashMap.this.key[this.index]).append("=>").append(Reference2BooleanOpenCustomHashMap.this.value[this.index]).toString();
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
/*  658 */     int pos = Reference2BooleanOpenCustomHashMap.this.n;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  664 */     int last = -1;
/*      */     
/*  666 */     int c = Reference2BooleanOpenCustomHashMap.this.size;
/*      */     
/*  668 */     boolean mustReturnNullKey = Reference2BooleanOpenCustomHashMap.this.containsNullKey;
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
/*  679 */       return (this.c != 0);
/*      */     }
/*      */     
/*      */     public int nextEntry() {
/*  683 */       if (!hasNext()) throw new NoSuchElementException(); 
/*  684 */       this.c--;
/*  685 */       if (this.mustReturnNullKey) {
/*  686 */         this.mustReturnNullKey = false;
/*  687 */         return this.last = Reference2BooleanOpenCustomHashMap.this.n;
/*      */       } 
/*  689 */       K[] key = Reference2BooleanOpenCustomHashMap.this.key;
/*      */       while (true) {
/*  691 */         if (--this.pos < 0) {
/*      */           
/*  693 */           this.last = Integer.MIN_VALUE;
/*  694 */           K k = this.wrapped.get(-this.pos - 1);
/*  695 */           int p = HashCommon.mix(Reference2BooleanOpenCustomHashMap.this.strategy.hashCode(k)) & Reference2BooleanOpenCustomHashMap.this.mask;
/*  696 */           for (; !Reference2BooleanOpenCustomHashMap.this.strategy.equals(k, key[p]); p = p + 1 & Reference2BooleanOpenCustomHashMap.this.mask);
/*  697 */           return p;
/*      */         } 
/*  699 */         if (key[this.pos] != null) return this.last = this.pos; 
/*      */       } 
/*      */     }
/*      */     
/*      */     public void forEachRemaining(ConsumerType action) {
/*  704 */       if (this.mustReturnNullKey) {
/*  705 */         this.mustReturnNullKey = false;
/*  706 */         acceptOnIndex(action, this.last = Reference2BooleanOpenCustomHashMap.this.n);
/*  707 */         this.c--;
/*      */       } 
/*  709 */       K[] key = Reference2BooleanOpenCustomHashMap.this.key;
/*  710 */       while (this.c != 0) {
/*  711 */         if (--this.pos < 0) {
/*      */           
/*  713 */           this.last = Integer.MIN_VALUE;
/*  714 */           K k = this.wrapped.get(-this.pos - 1);
/*  715 */           int p = HashCommon.mix(Reference2BooleanOpenCustomHashMap.this.strategy.hashCode(k)) & Reference2BooleanOpenCustomHashMap.this.mask;
/*  716 */           for (; !Reference2BooleanOpenCustomHashMap.this.strategy.equals(k, key[p]); p = p + 1 & Reference2BooleanOpenCustomHashMap.this.mask);
/*  717 */           acceptOnIndex(action, p);
/*  718 */           this.c--; continue;
/*  719 */         }  if (key[this.pos] != null) {
/*  720 */           acceptOnIndex(action, this.last = this.pos);
/*  721 */           this.c--;
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
/*  736 */       K[] key = Reference2BooleanOpenCustomHashMap.this.key; while (true) {
/*      */         K curr; int last;
/*  738 */         pos = (last = pos) + 1 & Reference2BooleanOpenCustomHashMap.this.mask;
/*      */         while (true) {
/*  740 */           if ((curr = key[pos]) == null) {
/*  741 */             key[last] = null;
/*      */             return;
/*      */           } 
/*  744 */           int slot = HashCommon.mix(Reference2BooleanOpenCustomHashMap.this.strategy.hashCode(curr)) & Reference2BooleanOpenCustomHashMap.this.mask;
/*  745 */           if ((last <= pos) ? (last >= slot || slot > pos) : (last >= slot && slot > pos))
/*  746 */             break;  pos = pos + 1 & Reference2BooleanOpenCustomHashMap.this.mask;
/*      */         } 
/*  748 */         if (pos < last) {
/*  749 */           if (this.wrapped == null) this.wrapped = new ReferenceArrayList<>(2); 
/*  750 */           this.wrapped.add(key[pos]);
/*      */         } 
/*  752 */         key[last] = curr;
/*  753 */         Reference2BooleanOpenCustomHashMap.this.value[last] = Reference2BooleanOpenCustomHashMap.this.value[pos];
/*      */       } 
/*      */     }
/*      */     
/*      */     public void remove() {
/*  758 */       if (this.last == -1) throw new IllegalStateException(); 
/*  759 */       if (this.last == Reference2BooleanOpenCustomHashMap.this.n)
/*  760 */       { Reference2BooleanOpenCustomHashMap.this.containsNullKey = false;
/*  761 */         Reference2BooleanOpenCustomHashMap.this.key[Reference2BooleanOpenCustomHashMap.this.n] = null; }
/*  762 */       else if (this.pos >= 0) { shiftKeys(this.last); }
/*      */       else
/*      */       
/*  765 */       { Reference2BooleanOpenCustomHashMap.this.removeBoolean(this.wrapped.set(-this.pos - 1, null));
/*  766 */         this.last = -1;
/*      */         return; }
/*      */       
/*  769 */       Reference2BooleanOpenCustomHashMap.this.size--;
/*  770 */       this.last = -1;
/*      */     }
/*      */ 
/*      */     
/*      */     public int skip(int n) {
/*  775 */       int i = n;
/*  776 */       for (; i-- != 0 && hasNext(); nextEntry());
/*  777 */       return n - i - 1;
/*      */     }
/*      */     
/*      */     private MapIterator() {}
/*      */     
/*      */     abstract void acceptOnIndex(ConsumerType param1ConsumerType, int param1Int);
/*      */   }
/*      */   
/*      */   private final class EntryIterator extends MapIterator<Consumer<? super Reference2BooleanMap.Entry<K>>> implements ObjectIterator<Reference2BooleanMap.Entry<K>> { public Reference2BooleanOpenCustomHashMap<K>.MapEntry next() {
/*  786 */       return this.entry = new Reference2BooleanOpenCustomHashMap.MapEntry(nextEntry());
/*      */     }
/*      */     private Reference2BooleanOpenCustomHashMap<K>.MapEntry entry;
/*      */     private EntryIterator() {}
/*      */     
/*      */     final void acceptOnIndex(Consumer<? super Reference2BooleanMap.Entry<K>> action, int index) {
/*  792 */       action.accept(this.entry = new Reference2BooleanOpenCustomHashMap.MapEntry(index));
/*      */     }
/*      */ 
/*      */     
/*      */     public void remove() {
/*  797 */       super.remove();
/*  798 */       this.entry.index = -1;
/*      */     } }
/*      */   
/*      */   private final class FastEntryIterator extends MapIterator<Consumer<? super Reference2BooleanMap.Entry<K>>> implements ObjectIterator<Reference2BooleanMap.Entry<K>> {
/*      */     private FastEntryIterator() {
/*  803 */       this.entry = new Reference2BooleanOpenCustomHashMap.MapEntry();
/*      */     }
/*      */     private final Reference2BooleanOpenCustomHashMap<K>.MapEntry entry;
/*      */     public Reference2BooleanOpenCustomHashMap<K>.MapEntry next() {
/*  807 */       this.entry.index = nextEntry();
/*  808 */       return this.entry;
/*      */     }
/*      */ 
/*      */ 
/*      */     
/*      */     final void acceptOnIndex(Consumer<? super Reference2BooleanMap.Entry<K>> action, int index) {
/*  814 */       this.entry.index = index;
/*  815 */       action.accept(this.entry);
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private abstract class MapSpliterator<ConsumerType, SplitType extends MapSpliterator<ConsumerType, SplitType>>
/*      */   {
/*  824 */     int pos = 0;
/*      */     
/*  826 */     int max = Reference2BooleanOpenCustomHashMap.this.n;
/*      */     
/*  828 */     int c = 0;
/*      */     
/*  830 */     boolean mustReturnNull = Reference2BooleanOpenCustomHashMap.this.containsNullKey;
/*      */     
/*      */     boolean hasSplit = false;
/*      */     
/*      */     MapSpliterator() {}
/*      */     
/*      */     MapSpliterator(int pos, int max, boolean mustReturnNull, boolean hasSplit) {
/*  837 */       this.pos = pos;
/*  838 */       this.max = max;
/*  839 */       this.mustReturnNull = mustReturnNull;
/*  840 */       this.hasSplit = hasSplit;
/*      */     }
/*      */     
/*      */     abstract void acceptOnIndex(ConsumerType param1ConsumerType, int param1Int);
/*      */     
/*      */     abstract SplitType makeForSplit(int param1Int1, int param1Int2, boolean param1Boolean);
/*      */     
/*      */     public boolean tryAdvance(ConsumerType action) {
/*  848 */       if (this.mustReturnNull) {
/*  849 */         this.mustReturnNull = false;
/*  850 */         this.c++;
/*  851 */         acceptOnIndex(action, Reference2BooleanOpenCustomHashMap.this.n);
/*  852 */         return true;
/*      */       } 
/*  854 */       K[] key = Reference2BooleanOpenCustomHashMap.this.key;
/*  855 */       while (this.pos < this.max) {
/*  856 */         if (key[this.pos] != null) {
/*  857 */           this.c++;
/*  858 */           acceptOnIndex(action, this.pos++);
/*  859 */           return true;
/*      */         } 
/*  861 */         this.pos++;
/*      */       } 
/*  863 */       return false;
/*      */     }
/*      */     
/*      */     public void forEachRemaining(ConsumerType action) {
/*  867 */       if (this.mustReturnNull) {
/*  868 */         this.mustReturnNull = false;
/*  869 */         this.c++;
/*  870 */         acceptOnIndex(action, Reference2BooleanOpenCustomHashMap.this.n);
/*      */       } 
/*  872 */       K[] key = Reference2BooleanOpenCustomHashMap.this.key;
/*  873 */       while (this.pos < this.max) {
/*  874 */         if (key[this.pos] != null) {
/*  875 */           acceptOnIndex(action, this.pos);
/*  876 */           this.c++;
/*      */         } 
/*  878 */         this.pos++;
/*      */       } 
/*      */     }
/*      */     
/*      */     public long estimateSize() {
/*  883 */       if (!this.hasSplit)
/*      */       {
/*  885 */         return (Reference2BooleanOpenCustomHashMap.this.size - this.c);
/*      */       }
/*      */ 
/*      */ 
/*      */       
/*  890 */       return Math.min((Reference2BooleanOpenCustomHashMap.this.size - this.c), (long)(Reference2BooleanOpenCustomHashMap.this.realSize() / Reference2BooleanOpenCustomHashMap.this.n * (this.max - this.pos)) + (this.mustReturnNull ? 1L : 0L));
/*      */     }
/*      */ 
/*      */     
/*      */     public SplitType trySplit() {
/*  895 */       if (this.pos >= this.max - 1) return null; 
/*  896 */       int retLen = this.max - this.pos >> 1;
/*  897 */       if (retLen <= 1) return null; 
/*  898 */       int myNewPos = this.pos + retLen;
/*  899 */       int retPos = this.pos;
/*  900 */       int retMax = myNewPos;
/*      */ 
/*      */ 
/*      */       
/*  904 */       SplitType split = makeForSplit(retPos, retMax, this.mustReturnNull);
/*  905 */       this.pos = myNewPos;
/*  906 */       this.mustReturnNull = false;
/*  907 */       this.hasSplit = true;
/*  908 */       return split;
/*      */     }
/*      */     
/*      */     public long skip(long n) {
/*  912 */       if (n < 0L) throw new IllegalArgumentException("Argument must be nonnegative: " + n); 
/*  913 */       if (n == 0L) return 0L; 
/*  914 */       long skipped = 0L;
/*  915 */       if (this.mustReturnNull) {
/*  916 */         this.mustReturnNull = false;
/*  917 */         skipped++;
/*  918 */         n--;
/*      */       } 
/*  920 */       K[] key = Reference2BooleanOpenCustomHashMap.this.key;
/*  921 */       while (this.pos < this.max && n > 0L) {
/*  922 */         if (key[this.pos++] != null) {
/*  923 */           skipped++;
/*  924 */           n--;
/*      */         } 
/*      */       } 
/*  927 */       return skipped;
/*      */     }
/*      */   }
/*      */   
/*      */   private final class EntrySpliterator
/*      */     extends MapSpliterator<Consumer<? super Reference2BooleanMap.Entry<K>>, EntrySpliterator> implements ObjectSpliterator<Reference2BooleanMap.Entry<K>> {
/*      */     private static final int POST_SPLIT_CHARACTERISTICS = 1;
/*      */     
/*      */     EntrySpliterator() {}
/*      */     
/*      */     EntrySpliterator(int pos, int max, boolean mustReturnNull, boolean hasSplit) {
/*  938 */       super(pos, max, mustReturnNull, hasSplit);
/*      */     }
/*      */ 
/*      */     
/*      */     public int characteristics() {
/*  943 */       return this.hasSplit ? 1 : 65;
/*      */     }
/*      */ 
/*      */     
/*      */     final void acceptOnIndex(Consumer<? super Reference2BooleanMap.Entry<K>> action, int index) {
/*  948 */       action.accept(new Reference2BooleanOpenCustomHashMap.MapEntry(index));
/*      */     }
/*      */ 
/*      */     
/*      */     final EntrySpliterator makeForSplit(int pos, int max, boolean mustReturnNull) {
/*  953 */       return new EntrySpliterator(pos, max, mustReturnNull, true);
/*      */     } }
/*      */   
/*      */   private final class MapEntrySet extends AbstractObjectSet<Reference2BooleanMap.Entry<K>> implements Reference2BooleanMap.FastEntrySet<K> {
/*      */     private MapEntrySet() {}
/*      */     
/*      */     public ObjectIterator<Reference2BooleanMap.Entry<K>> iterator() {
/*  960 */       return new Reference2BooleanOpenCustomHashMap.EntryIterator();
/*      */     }
/*      */ 
/*      */     
/*      */     public ObjectIterator<Reference2BooleanMap.Entry<K>> fastIterator() {
/*  965 */       return new Reference2BooleanOpenCustomHashMap.FastEntryIterator();
/*      */     }
/*      */ 
/*      */     
/*      */     public ObjectSpliterator<Reference2BooleanMap.Entry<K>> spliterator() {
/*  970 */       return new Reference2BooleanOpenCustomHashMap.EntrySpliterator();
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public boolean contains(Object o) {
/*  977 */       if (!(o instanceof Map.Entry)) return false; 
/*  978 */       Map.Entry<?, ?> e = (Map.Entry<?, ?>)o;
/*  979 */       if (e.getValue() == null || !(e.getValue() instanceof Boolean)) return false; 
/*  980 */       K k = (K)e.getKey();
/*  981 */       boolean v = ((Boolean)e.getValue()).booleanValue();
/*  982 */       if (Reference2BooleanOpenCustomHashMap.this.strategy.equals(k, null)) return (Reference2BooleanOpenCustomHashMap.this.containsNullKey && Reference2BooleanOpenCustomHashMap.this.value[Reference2BooleanOpenCustomHashMap.this.n] == v);
/*      */       
/*  984 */       K[] key = Reference2BooleanOpenCustomHashMap.this.key;
/*      */       K curr;
/*      */       int pos;
/*  987 */       if ((curr = key[pos = HashCommon.mix(Reference2BooleanOpenCustomHashMap.this.strategy.hashCode(k)) & Reference2BooleanOpenCustomHashMap.this.mask]) == null) return false; 
/*  988 */       if (Reference2BooleanOpenCustomHashMap.this.strategy.equals(k, curr)) return (Reference2BooleanOpenCustomHashMap.this.value[pos] == v);
/*      */       
/*      */       while (true) {
/*  991 */         if ((curr = key[pos = pos + 1 & Reference2BooleanOpenCustomHashMap.this.mask]) == null) return false; 
/*  992 */         if (Reference2BooleanOpenCustomHashMap.this.strategy.equals(k, curr)) return (Reference2BooleanOpenCustomHashMap.this.value[pos] == v);
/*      */       
/*      */       } 
/*      */     }
/*      */ 
/*      */     
/*      */     public boolean remove(Object o) {
/*  999 */       if (!(o instanceof Map.Entry)) return false; 
/* 1000 */       Map.Entry<?, ?> e = (Map.Entry<?, ?>)o;
/* 1001 */       if (e.getValue() == null || !(e.getValue() instanceof Boolean)) return false; 
/* 1002 */       K k = (K)e.getKey();
/* 1003 */       boolean v = ((Boolean)e.getValue()).booleanValue();
/* 1004 */       if (Reference2BooleanOpenCustomHashMap.this.strategy.equals(k, null)) {
/* 1005 */         if (Reference2BooleanOpenCustomHashMap.this.containsNullKey && Reference2BooleanOpenCustomHashMap.this.value[Reference2BooleanOpenCustomHashMap.this.n] == v) {
/* 1006 */           Reference2BooleanOpenCustomHashMap.this.removeNullEntry();
/* 1007 */           return true;
/*      */         } 
/* 1009 */         return false;
/*      */       } 
/*      */       
/* 1012 */       K[] key = Reference2BooleanOpenCustomHashMap.this.key;
/*      */       K curr;
/*      */       int pos;
/* 1015 */       if ((curr = key[pos = HashCommon.mix(Reference2BooleanOpenCustomHashMap.this.strategy.hashCode(k)) & Reference2BooleanOpenCustomHashMap.this.mask]) == null) return false; 
/* 1016 */       if (Reference2BooleanOpenCustomHashMap.this.strategy.equals(curr, k)) {
/* 1017 */         if (Reference2BooleanOpenCustomHashMap.this.value[pos] == v) {
/* 1018 */           Reference2BooleanOpenCustomHashMap.this.removeEntry(pos);
/* 1019 */           return true;
/*      */         } 
/* 1021 */         return false;
/*      */       } 
/*      */       while (true) {
/* 1024 */         if ((curr = key[pos = pos + 1 & Reference2BooleanOpenCustomHashMap.this.mask]) == null) return false; 
/* 1025 */         if (Reference2BooleanOpenCustomHashMap.this.strategy.equals(curr, k) && 
/* 1026 */           Reference2BooleanOpenCustomHashMap.this.value[pos] == v) {
/* 1027 */           Reference2BooleanOpenCustomHashMap.this.removeEntry(pos);
/* 1028 */           return true;
/*      */         } 
/*      */       } 
/*      */     }
/*      */ 
/*      */ 
/*      */     
/*      */     public int size() {
/* 1036 */       return Reference2BooleanOpenCustomHashMap.this.size;
/*      */     }
/*      */ 
/*      */     
/*      */     public void clear() {
/* 1041 */       Reference2BooleanOpenCustomHashMap.this.clear();
/*      */     }
/*      */ 
/*      */ 
/*      */     
/*      */     public void forEach(Consumer<? super Reference2BooleanMap.Entry<K>> consumer) {
/* 1047 */       if (Reference2BooleanOpenCustomHashMap.this.containsNullKey) consumer.accept(new Reference2BooleanOpenCustomHashMap.MapEntry(Reference2BooleanOpenCustomHashMap.this.n)); 
/* 1048 */       for (int pos = Reference2BooleanOpenCustomHashMap.this.n; pos-- != 0;) { if (Reference2BooleanOpenCustomHashMap.this.key[pos] != null) consumer.accept(new Reference2BooleanOpenCustomHashMap.MapEntry(pos));
/*      */          }
/*      */     
/*      */     }
/*      */     
/*      */     public void fastForEach(Consumer<? super Reference2BooleanMap.Entry<K>> consumer) {
/* 1054 */       Reference2BooleanOpenCustomHashMap<K>.MapEntry entry = new Reference2BooleanOpenCustomHashMap.MapEntry();
/* 1055 */       if (Reference2BooleanOpenCustomHashMap.this.containsNullKey) {
/* 1056 */         entry.index = Reference2BooleanOpenCustomHashMap.this.n;
/* 1057 */         consumer.accept(entry);
/*      */       } 
/* 1059 */       for (int pos = Reference2BooleanOpenCustomHashMap.this.n; pos-- != 0;) { if (Reference2BooleanOpenCustomHashMap.this.key[pos] != null) {
/* 1060 */           entry.index = pos;
/* 1061 */           consumer.accept(entry);
/*      */         }  }
/*      */     
/*      */     }
/*      */   }
/*      */   
/*      */   public Reference2BooleanMap.FastEntrySet<K> reference2BooleanEntrySet() {
/* 1068 */     if (this.entries == null) this.entries = new MapEntrySet(); 
/* 1069 */     return this.entries;
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
/* 1090 */       action.accept(Reference2BooleanOpenCustomHashMap.this.key[index]);
/*      */     }
/*      */ 
/*      */     
/*      */     public K next() {
/* 1095 */       return Reference2BooleanOpenCustomHashMap.this.key[nextEntry()];
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
/* 1106 */       super(pos, max, mustReturnNull, hasSplit);
/*      */     }
/*      */ 
/*      */     
/*      */     public int characteristics() {
/* 1111 */       return this.hasSplit ? 1 : 65;
/*      */     }
/*      */ 
/*      */     
/*      */     final void acceptOnIndex(Consumer<? super K> action, int index) {
/* 1116 */       action.accept(Reference2BooleanOpenCustomHashMap.this.key[index]);
/*      */     }
/*      */ 
/*      */     
/*      */     final KeySpliterator makeForSplit(int pos, int max, boolean mustReturnNull) {
/* 1121 */       return new KeySpliterator(pos, max, mustReturnNull, true);
/*      */     } }
/*      */   
/*      */   private final class KeySet extends AbstractReferenceSet<K> {
/*      */     private KeySet() {}
/*      */     
/*      */     public ObjectIterator<K> iterator() {
/* 1128 */       return new Reference2BooleanOpenCustomHashMap.KeyIterator();
/*      */     }
/*      */ 
/*      */     
/*      */     public ObjectSpliterator<K> spliterator() {
/* 1133 */       return new Reference2BooleanOpenCustomHashMap.KeySpliterator();
/*      */     }
/*      */ 
/*      */ 
/*      */     
/*      */     public void forEach(Consumer<? super K> consumer) {
/* 1139 */       if (Reference2BooleanOpenCustomHashMap.this.containsNullKey) consumer.accept(Reference2BooleanOpenCustomHashMap.this.key[Reference2BooleanOpenCustomHashMap.this.n]); 
/* 1140 */       for (int pos = Reference2BooleanOpenCustomHashMap.this.n; pos-- != 0; ) {
/* 1141 */         K k = Reference2BooleanOpenCustomHashMap.this.key[pos];
/* 1142 */         if (k != null) consumer.accept(k);
/*      */       
/*      */       } 
/*      */     }
/*      */     
/*      */     public int size() {
/* 1148 */       return Reference2BooleanOpenCustomHashMap.this.size;
/*      */     }
/*      */ 
/*      */     
/*      */     public boolean contains(Object k) {
/* 1153 */       return Reference2BooleanOpenCustomHashMap.this.containsKey(k);
/*      */     }
/*      */ 
/*      */     
/*      */     public boolean remove(Object k) {
/* 1158 */       int oldSize = Reference2BooleanOpenCustomHashMap.this.size;
/* 1159 */       Reference2BooleanOpenCustomHashMap.this.removeBoolean(k);
/* 1160 */       return (Reference2BooleanOpenCustomHashMap.this.size != oldSize);
/*      */     }
/*      */ 
/*      */     
/*      */     public void clear() {
/* 1165 */       Reference2BooleanOpenCustomHashMap.this.clear();
/*      */     }
/*      */   }
/*      */ 
/*      */   
/*      */   public ReferenceSet<K> keySet() {
/* 1171 */     if (this.keys == null) this.keys = new KeySet(); 
/* 1172 */     return this.keys;
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
/*      */     extends MapIterator<BooleanConsumer>
/*      */     implements BooleanIterator
/*      */   {
/*      */     final void acceptOnIndex(BooleanConsumer action, int index) {
/* 1193 */       action.accept(Reference2BooleanOpenCustomHashMap.this.value[index]);
/*      */     }
/*      */ 
/*      */     
/*      */     public boolean nextBoolean() {
/* 1198 */       return Reference2BooleanOpenCustomHashMap.this.value[nextEntry()];
/*      */     }
/*      */   }
/*      */   
/*      */   private final class ValueSpliterator
/*      */     extends MapSpliterator<BooleanConsumer, ValueSpliterator> implements BooleanSpliterator {
/*      */     private static final int POST_SPLIT_CHARACTERISTICS = 256;
/*      */     
/*      */     ValueSpliterator() {}
/*      */     
/*      */     ValueSpliterator(int pos, int max, boolean mustReturnNull, boolean hasSplit) {
/* 1209 */       super(pos, max, mustReturnNull, hasSplit);
/*      */     }
/*      */ 
/*      */     
/*      */     public int characteristics() {
/* 1214 */       return this.hasSplit ? 256 : 320;
/*      */     }
/*      */ 
/*      */     
/*      */     final void acceptOnIndex(BooleanConsumer action, int index) {
/* 1219 */       action.accept(Reference2BooleanOpenCustomHashMap.this.value[index]);
/*      */     }
/*      */ 
/*      */     
/*      */     final ValueSpliterator makeForSplit(int pos, int max, boolean mustReturnNull) {
/* 1224 */       return new ValueSpliterator(pos, max, mustReturnNull, true);
/*      */     }
/*      */   }
/*      */ 
/*      */   
/*      */   public BooleanCollection values() {
/* 1230 */     if (this.values == null) this.values = (BooleanCollection)new AbstractBooleanCollection()
/*      */         {
/*      */           public BooleanIterator iterator() {
/* 1233 */             return new Reference2BooleanOpenCustomHashMap.ValueIterator();
/*      */           }
/*      */ 
/*      */           
/*      */           public BooleanSpliterator spliterator() {
/* 1238 */             return new Reference2BooleanOpenCustomHashMap.ValueSpliterator();
/*      */           }
/*      */ 
/*      */ 
/*      */           
/*      */           public void forEach(BooleanConsumer consumer) {
/* 1244 */             if (Reference2BooleanOpenCustomHashMap.this.containsNullKey) consumer.accept(Reference2BooleanOpenCustomHashMap.this.value[Reference2BooleanOpenCustomHashMap.this.n]); 
/* 1245 */             for (int pos = Reference2BooleanOpenCustomHashMap.this.n; pos-- != 0;) { if (Reference2BooleanOpenCustomHashMap.this.key[pos] != null) consumer.accept(Reference2BooleanOpenCustomHashMap.this.value[pos]);  }
/*      */           
/*      */           }
/*      */           
/*      */           public int size() {
/* 1250 */             return Reference2BooleanOpenCustomHashMap.this.size;
/*      */           }
/*      */ 
/*      */           
/*      */           public boolean contains(boolean v) {
/* 1255 */             return Reference2BooleanOpenCustomHashMap.this.containsValue(v);
/*      */           }
/*      */ 
/*      */           
/*      */           public void clear() {
/* 1260 */             Reference2BooleanOpenCustomHashMap.this.clear();
/*      */           }
/*      */         }; 
/* 1263 */     return this.values;
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
/* 1280 */     return trim(this.size);
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
/* 1302 */     int l = HashCommon.nextPowerOfTwo((int)Math.ceil((n / this.f)));
/* 1303 */     if (l >= this.n || this.size > HashCommon.maxFill(l, this.f)) return true; 
/*      */     try {
/* 1305 */       rehash(l);
/* 1306 */     } catch (OutOfMemoryError cantDoIt) {
/* 1307 */       return false;
/*      */     } 
/* 1309 */     return true;
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
/* 1324 */     K[] key = this.key;
/* 1325 */     boolean[] value = this.value;
/* 1326 */     int mask = newN - 1;
/* 1327 */     K[] newKey = (K[])new Object[newN + 1];
/* 1328 */     boolean[] newValue = new boolean[newN + 1];
/* 1329 */     int i = this.n;
/* 1330 */     for (int j = realSize(); j-- != 0; ) {
/* 1331 */       while (key[--i] == null); int pos;
/* 1332 */       if (newKey[pos = HashCommon.mix(this.strategy.hashCode(key[i])) & mask] != null) while (newKey[pos = pos + 1 & mask] != null); 
/* 1333 */       newKey[pos] = key[i];
/* 1334 */       newValue[pos] = value[i];
/*      */     } 
/* 1336 */     newValue[newN] = value[this.n];
/* 1337 */     this.n = newN;
/* 1338 */     this.mask = mask;
/* 1339 */     this.maxFill = HashCommon.maxFill(this.n, this.f);
/* 1340 */     this.key = newKey;
/* 1341 */     this.value = newValue;
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
/*      */   public Reference2BooleanOpenCustomHashMap<K> clone() {
/*      */     Reference2BooleanOpenCustomHashMap<K> c;
/*      */     try {
/* 1358 */       c = (Reference2BooleanOpenCustomHashMap<K>)super.clone();
/* 1359 */     } catch (CloneNotSupportedException cantHappen) {
/* 1360 */       throw new InternalError();
/*      */     } 
/* 1362 */     c.keys = null;
/* 1363 */     c.values = null;
/* 1364 */     c.entries = null;
/* 1365 */     c.containsNullKey = this.containsNullKey;
/* 1366 */     c.key = (K[])this.key.clone();
/* 1367 */     c.value = (boolean[])this.value.clone();
/* 1368 */     c.strategy = this.strategy;
/* 1369 */     return c;
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
/* 1383 */     int h = 0;
/* 1384 */     for (int j = realSize(), i = 0, t = 0; j-- != 0; ) {
/* 1385 */       for (; this.key[i] == null; i++);
/* 1386 */       if (this != this.key[i]) t = this.strategy.hashCode(this.key[i]); 
/* 1387 */       t ^= this.value[i] ? 1231 : 1237;
/* 1388 */       h += t;
/* 1389 */       i++;
/*      */     } 
/*      */     
/* 1392 */     if (this.containsNullKey) h += this.value[this.n] ? 1231 : 1237; 
/* 1393 */     return h;
/*      */   }
/*      */   
/*      */   private void writeObject(ObjectOutputStream s) throws IOException {
/* 1397 */     K[] key = this.key;
/* 1398 */     boolean[] value = this.value;
/* 1399 */     EntryIterator i = new EntryIterator();
/* 1400 */     s.defaultWriteObject();
/* 1401 */     for (int j = this.size; j-- != 0; ) {
/* 1402 */       int e = i.nextEntry();
/* 1403 */       s.writeObject(key[e]);
/* 1404 */       s.writeBoolean(value[e]);
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   private void readObject(ObjectInputStream s) throws IOException, ClassNotFoundException {
/* 1410 */     s.defaultReadObject();
/* 1411 */     this.n = HashCommon.arraySize(this.size, this.f);
/* 1412 */     this.maxFill = HashCommon.maxFill(this.n, this.f);
/* 1413 */     this.mask = this.n - 1;
/* 1414 */     K[] key = this.key = (K[])new Object[this.n + 1];
/* 1415 */     boolean[] value = this.value = new boolean[this.n + 1];
/*      */ 
/*      */     
/* 1418 */     for (int i = this.size; i-- != 0; ) {
/* 1419 */       int pos; K k = (K)s.readObject();
/* 1420 */       boolean v = s.readBoolean();
/* 1421 */       if (this.strategy.equals(k, null)) {
/* 1422 */         pos = this.n;
/* 1423 */         this.containsNullKey = true;
/*      */       } else {
/* 1425 */         pos = HashCommon.mix(this.strategy.hashCode(k)) & this.mask;
/* 1426 */         for (; key[pos] != null; pos = pos + 1 & this.mask);
/*      */       } 
/* 1428 */       key[pos] = k;
/* 1429 */       value[pos] = v;
/*      */     } 
/*      */   }
/*      */   
/*      */   private void checkTable() {}
/*      */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\i\\unimi\dsi\fastutil\objects\Reference2BooleanOpenCustomHashMap.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */