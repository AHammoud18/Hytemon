/*      */ package it.unimi.dsi.fastutil.floats;
/*      */ 
/*      */ import it.unimi.dsi.fastutil.Hash;
/*      */ import it.unimi.dsi.fastutil.HashCommon;
/*      */ import it.unimi.dsi.fastutil.Pair;
/*      */ import it.unimi.dsi.fastutil.objects.AbstractObjectSet;
/*      */ import it.unimi.dsi.fastutil.objects.AbstractReferenceCollection;
/*      */ import it.unimi.dsi.fastutil.objects.ObjectIterator;
/*      */ import it.unimi.dsi.fastutil.objects.ObjectSet;
/*      */ import it.unimi.dsi.fastutil.objects.ObjectSpliterator;
/*      */ import it.unimi.dsi.fastutil.objects.ReferenceCollection;
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
/*      */ import java.util.function.DoubleFunction;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ public class Float2ReferenceOpenCustomHashMap<V>
/*      */   extends AbstractFloat2ReferenceMap<V>
/*      */   implements Serializable, Cloneable, Hash
/*      */ {
/*      */   private static final long serialVersionUID = 0L;
/*      */   private static final boolean ASSERTS = false;
/*      */   protected transient float[] key;
/*      */   protected transient V[] value;
/*      */   protected transient int mask;
/*      */   protected transient boolean containsNullKey;
/*      */   protected FloatHash.Strategy strategy;
/*      */   protected transient int n;
/*      */   protected transient int maxFill;
/*      */   protected final transient int minN;
/*      */   protected int size;
/*      */   protected final float f;
/*      */   protected transient Float2ReferenceMap.FastEntrySet<V> entries;
/*      */   protected transient FloatSet keys;
/*      */   protected transient ReferenceCollection<V> values;
/*      */   
/*      */   public Float2ReferenceOpenCustomHashMap(int expected, float f, FloatHash.Strategy strategy) {
/*  100 */     this.strategy = strategy;
/*  101 */     if (f <= 0.0F || f >= 1.0F) throw new IllegalArgumentException("Load factor must be greater than 0 and smaller than 1"); 
/*  102 */     if (expected < 0) throw new IllegalArgumentException("The expected number of elements must be nonnegative"); 
/*  103 */     this.f = f;
/*  104 */     this.minN = this.n = HashCommon.arraySize(expected, f);
/*  105 */     this.mask = this.n - 1;
/*  106 */     this.maxFill = HashCommon.maxFill(this.n, f);
/*  107 */     this.key = new float[this.n + 1];
/*  108 */     this.value = (V[])new Object[this.n + 1];
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Float2ReferenceOpenCustomHashMap(int expected, FloatHash.Strategy strategy) {
/*  118 */     this(expected, 0.75F, strategy);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Float2ReferenceOpenCustomHashMap(FloatHash.Strategy strategy) {
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
/*      */   public Float2ReferenceOpenCustomHashMap(Map<? extends Float, ? extends V> m, float f, FloatHash.Strategy strategy) {
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
/*      */   public Float2ReferenceOpenCustomHashMap(Map<? extends Float, ? extends V> m, FloatHash.Strategy strategy) {
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
/*      */   public Float2ReferenceOpenCustomHashMap(Float2ReferenceMap<V> m, float f, FloatHash.Strategy strategy) {
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
/*      */   public Float2ReferenceOpenCustomHashMap(Float2ReferenceMap<V> m, FloatHash.Strategy strategy) {
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
/*      */   public Float2ReferenceOpenCustomHashMap(float[] k, V[] v, float f, FloatHash.Strategy strategy) {
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
/*      */   public Float2ReferenceOpenCustomHashMap(float[] k, V[] v, FloatHash.Strategy strategy) {
/*  201 */     this(k, v, 0.75F, strategy);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public FloatHash.Strategy strategy() {
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
/*      */   private V removeEntry(int pos) {
/*  234 */     V oldValue = this.value[pos];
/*  235 */     this.value[pos] = null;
/*  236 */     this.size--;
/*  237 */     shiftKeys(pos);
/*  238 */     if (this.n > this.minN && this.size < this.maxFill / 4 && this.n > 16) rehash(this.n / 2); 
/*  239 */     return oldValue;
/*      */   }
/*      */   
/*      */   private V removeNullEntry() {
/*  243 */     this.containsNullKey = false;
/*  244 */     V oldValue = this.value[this.n];
/*  245 */     this.value[this.n] = null;
/*  246 */     this.size--;
/*  247 */     if (this.n > this.minN && this.size < this.maxFill / 4 && this.n > 16) rehash(this.n / 2); 
/*  248 */     return oldValue;
/*      */   }
/*      */ 
/*      */   
/*      */   public void putAll(Map<? extends Float, ? extends V> m) {
/*  253 */     if (this.f <= 0.5D) { ensureCapacity(m.size()); }
/*  254 */     else { tryCapacity((size() + m.size())); }
/*      */     
/*  256 */     super.putAll(m);
/*      */   }
/*      */   
/*      */   private int find(float k) {
/*  260 */     if (this.strategy.equals(k, 0.0F)) return this.containsNullKey ? this.n : -(this.n + 1);
/*      */     
/*  262 */     float[] key = this.key;
/*      */     float curr;
/*      */     int pos;
/*  265 */     if (Float.floatToIntBits(curr = key[pos = HashCommon.mix(this.strategy.hashCode(k)) & this.mask]) == 0) return -(pos + 1); 
/*  266 */     if (this.strategy.equals(k, curr)) return pos;
/*      */     
/*      */     while (true) {
/*  269 */       if (Float.floatToIntBits(curr = key[pos = pos + 1 & this.mask]) == 0) return -(pos + 1); 
/*  270 */       if (this.strategy.equals(k, curr)) return pos; 
/*      */     } 
/*      */   }
/*      */   
/*      */   private void insert(int pos, float k, V v) {
/*  275 */     if (pos == this.n) this.containsNullKey = true; 
/*  276 */     this.key[pos] = k;
/*  277 */     this.value[pos] = v;
/*  278 */     if (this.size++ >= this.maxFill) rehash(HashCommon.arraySize(this.size + 1, this.f));
/*      */   
/*      */   }
/*      */ 
/*      */   
/*      */   public V put(float k, V v) {
/*  284 */     int pos = find(k);
/*  285 */     if (pos < 0) {
/*  286 */       insert(-pos - 1, k, v);
/*  287 */       return this.defRetValue;
/*      */     } 
/*  289 */     V oldValue = this.value[pos];
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
/*  304 */     float[] key = this.key; while (true) {
/*      */       float curr; int last;
/*  306 */       pos = (last = pos) + 1 & this.mask;
/*      */       while (true) {
/*  308 */         if (Float.floatToIntBits(curr = key[pos]) == 0) {
/*  309 */           key[last] = 0.0F;
/*  310 */           this.value[last] = null;
/*      */           return;
/*      */         } 
/*  313 */         int slot = HashCommon.mix(this.strategy.hashCode(curr)) & this.mask;
/*  314 */         if ((last <= pos) ? (last >= slot || slot > pos) : (last >= slot && slot > pos))
/*  315 */           break;  pos = pos + 1 & this.mask;
/*      */       } 
/*  317 */       key[last] = curr;
/*  318 */       this.value[last] = this.value[pos];
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public V remove(float k) {
/*  325 */     if (this.strategy.equals(k, 0.0F)) {
/*  326 */       if (this.containsNullKey) return removeNullEntry(); 
/*  327 */       return this.defRetValue;
/*      */     } 
/*      */     
/*  330 */     float[] key = this.key;
/*      */     float curr;
/*      */     int pos;
/*  333 */     if (Float.floatToIntBits(curr = key[pos = HashCommon.mix(this.strategy.hashCode(k)) & this.mask]) == 0) return this.defRetValue; 
/*  334 */     if (this.strategy.equals(k, curr)) return removeEntry(pos); 
/*      */     while (true) {
/*  336 */       if (Float.floatToIntBits(curr = key[pos = pos + 1 & this.mask]) == 0) return this.defRetValue; 
/*  337 */       if (this.strategy.equals(k, curr)) return removeEntry(pos);
/*      */     
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   public V get(float k) {
/*  344 */     if (this.strategy.equals(k, 0.0F)) return this.containsNullKey ? this.value[this.n] : this.defRetValue;
/*      */     
/*  346 */     float[] key = this.key;
/*      */     float curr;
/*      */     int pos;
/*  349 */     if (Float.floatToIntBits(curr = key[pos = HashCommon.mix(this.strategy.hashCode(k)) & this.mask]) == 0) return this.defRetValue; 
/*  350 */     if (this.strategy.equals(k, curr)) return this.value[pos];
/*      */     
/*      */     while (true) {
/*  353 */       if (Float.floatToIntBits(curr = key[pos = pos + 1 & this.mask]) == 0) return this.defRetValue; 
/*  354 */       if (this.strategy.equals(k, curr)) return this.value[pos];
/*      */     
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   public boolean containsKey(float k) {
/*  361 */     if (this.strategy.equals(k, 0.0F)) return this.containsNullKey;
/*      */     
/*  363 */     float[] key = this.key;
/*      */     float curr;
/*      */     int pos;
/*  366 */     if (Float.floatToIntBits(curr = key[pos = HashCommon.mix(this.strategy.hashCode(k)) & this.mask]) == 0) return false; 
/*  367 */     if (this.strategy.equals(k, curr)) return true;
/*      */     
/*      */     while (true) {
/*  370 */       if (Float.floatToIntBits(curr = key[pos = pos + 1 & this.mask]) == 0) return false; 
/*  371 */       if (this.strategy.equals(k, curr)) return true;
/*      */     
/*      */     } 
/*      */   }
/*      */   
/*      */   public boolean containsValue(Object v) {
/*  377 */     V[] value = this.value;
/*  378 */     float[] key = this.key;
/*  379 */     if (this.containsNullKey && value[this.n] == v) return true; 
/*  380 */     for (int i = this.n; i-- != 0;) { if (Float.floatToIntBits(key[i]) != 0 && value[i] == v) return true;  }
/*  381 */      return false;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public V getOrDefault(float k, V defaultValue) {
/*  388 */     if (this.strategy.equals(k, 0.0F)) return this.containsNullKey ? this.value[this.n] : defaultValue;
/*      */     
/*  390 */     float[] key = this.key;
/*      */     float curr;
/*      */     int pos;
/*  393 */     if (Float.floatToIntBits(curr = key[pos = HashCommon.mix(this.strategy.hashCode(k)) & this.mask]) == 0) return defaultValue; 
/*  394 */     if (this.strategy.equals(k, curr)) return this.value[pos];
/*      */     
/*      */     while (true) {
/*  397 */       if (Float.floatToIntBits(curr = key[pos = pos + 1 & this.mask]) == 0) return defaultValue; 
/*  398 */       if (this.strategy.equals(k, curr)) return this.value[pos];
/*      */     
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   public V putIfAbsent(float k, V v) {
/*  405 */     int pos = find(k);
/*  406 */     if (pos >= 0) return this.value[pos]; 
/*  407 */     insert(-pos - 1, k, v);
/*  408 */     return this.defRetValue;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean remove(float k, Object v) {
/*  415 */     if (this.strategy.equals(k, 0.0F)) {
/*  416 */       if (this.containsNullKey && v == this.value[this.n]) {
/*  417 */         removeNullEntry();
/*  418 */         return true;
/*      */       } 
/*  420 */       return false;
/*      */     } 
/*      */     
/*  423 */     float[] key = this.key;
/*      */     float curr;
/*      */     int pos;
/*  426 */     if (Float.floatToIntBits(curr = key[pos = HashCommon.mix(this.strategy.hashCode(k)) & this.mask]) == 0) return false; 
/*  427 */     if (this.strategy.equals(k, curr) && v == this.value[pos]) {
/*  428 */       removeEntry(pos);
/*  429 */       return true;
/*      */     } 
/*      */     while (true) {
/*  432 */       if (Float.floatToIntBits(curr = key[pos = pos + 1 & this.mask]) == 0) return false; 
/*  433 */       if (this.strategy.equals(k, curr) && v == this.value[pos]) {
/*  434 */         removeEntry(pos);
/*  435 */         return true;
/*      */       } 
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean replace(float k, V oldValue, V v) {
/*  443 */     int pos = find(k);
/*  444 */     if (pos < 0 || oldValue != this.value[pos]) return false; 
/*  445 */     this.value[pos] = v;
/*  446 */     return true;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public V replace(float k, V v) {
/*  452 */     int pos = find(k);
/*  453 */     if (pos < 0) return this.defRetValue; 
/*  454 */     V oldValue = this.value[pos];
/*  455 */     this.value[pos] = v;
/*  456 */     return oldValue;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public V computeIfAbsent(float k, DoubleFunction<? extends V> mappingFunction) {
/*  462 */     Objects.requireNonNull(mappingFunction);
/*  463 */     int pos = find(k);
/*  464 */     if (pos >= 0) return this.value[pos]; 
/*  465 */     V newValue = mappingFunction.apply(k);
/*  466 */     insert(-pos - 1, k, newValue);
/*  467 */     return newValue;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public V computeIfAbsent(float key, Float2ReferenceFunction<? extends V> mappingFunction) {
/*  473 */     Objects.requireNonNull(mappingFunction);
/*  474 */     int pos = find(key);
/*  475 */     if (pos >= 0) return this.value[pos]; 
/*  476 */     if (!mappingFunction.containsKey(key)) return this.defRetValue; 
/*  477 */     V newValue = mappingFunction.get(key);
/*  478 */     insert(-pos - 1, key, newValue);
/*  479 */     return newValue;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public V computeIfPresent(float k, BiFunction<? super Float, ? super V, ? extends V> remappingFunction) {
/*  485 */     Objects.requireNonNull(remappingFunction);
/*  486 */     int pos = find(k);
/*  487 */     if (pos < 0) return this.defRetValue; 
/*  488 */     if (this.value[pos] == null) return this.defRetValue; 
/*  489 */     V newValue = remappingFunction.apply(Float.valueOf(k), this.value[pos]);
/*  490 */     if (newValue == null) {
/*  491 */       if (this.strategy.equals(k, 0.0F)) { removeNullEntry(); }
/*  492 */       else { removeEntry(pos); }
/*  493 */        return this.defRetValue;
/*      */     } 
/*  495 */     this.value[pos] = newValue; return newValue;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public V compute(float k, BiFunction<? super Float, ? super V, ? extends V> remappingFunction) {
/*  501 */     Objects.requireNonNull(remappingFunction);
/*  502 */     int pos = find(k);
/*  503 */     V newValue = remappingFunction.apply(Float.valueOf(k), (pos >= 0) ? this.value[pos] : null);
/*  504 */     if (newValue == null) {
/*  505 */       if (pos >= 0)
/*  506 */         if (this.strategy.equals(k, 0.0F)) { removeNullEntry(); }
/*  507 */         else { removeEntry(pos); }
/*      */          
/*  509 */       return this.defRetValue;
/*      */     } 
/*  511 */     V newVal = newValue;
/*  512 */     if (pos < 0) {
/*  513 */       insert(-pos - 1, k, newVal);
/*  514 */       return newVal;
/*      */     } 
/*  516 */     this.value[pos] = newVal; return newVal;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public V merge(float k, V v, BiFunction<? super V, ? super V, ? extends V> remappingFunction) {
/*  522 */     Objects.requireNonNull(remappingFunction);
/*  523 */     Objects.requireNonNull(v);
/*  524 */     int pos = find(k);
/*  525 */     if (pos < 0 || this.value[pos] == null) {
/*  526 */       if (pos < 0) { insert(-pos - 1, k, v); }
/*  527 */       else { this.value[pos] = v; }
/*  528 */        return v;
/*      */     } 
/*  530 */     V newValue = remappingFunction.apply(this.value[pos], v);
/*  531 */     if (newValue == null) {
/*  532 */       if (this.strategy.equals(k, 0.0F)) { removeNullEntry(); }
/*  533 */       else { removeEntry(pos); }
/*  534 */        return this.defRetValue;
/*      */     } 
/*  536 */     this.value[pos] = newValue; return newValue;
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
/*  547 */     if (this.size == 0)
/*  548 */       return;  this.size = 0;
/*  549 */     this.containsNullKey = false;
/*  550 */     Arrays.fill(this.key, 0.0F);
/*  551 */     Arrays.fill((Object[])this.value, (Object)null);
/*      */   }
/*      */ 
/*      */   
/*      */   public int size() {
/*  556 */     return this.size;
/*      */   }
/*      */ 
/*      */   
/*      */   public boolean isEmpty() {
/*  561 */     return (this.size == 0);
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   final class MapEntry
/*      */     implements Float2ReferenceMap.Entry<V>, Map.Entry<Float, V>, FloatReferencePair<V>
/*      */   {
/*      */     int index;
/*      */ 
/*      */ 
/*      */     
/*      */     MapEntry(int index) {
/*  574 */       this.index = index;
/*      */     }
/*      */ 
/*      */     
/*      */     MapEntry() {}
/*      */ 
/*      */     
/*      */     public float getFloatKey() {
/*  582 */       return Float2ReferenceOpenCustomHashMap.this.key[this.index];
/*      */     }
/*      */ 
/*      */     
/*      */     public float leftFloat() {
/*  587 */       return Float2ReferenceOpenCustomHashMap.this.key[this.index];
/*      */     }
/*      */ 
/*      */     
/*      */     public V getValue() {
/*  592 */       return Float2ReferenceOpenCustomHashMap.this.value[this.index];
/*      */     }
/*      */ 
/*      */     
/*      */     public V right() {
/*  597 */       return Float2ReferenceOpenCustomHashMap.this.value[this.index];
/*      */     }
/*      */ 
/*      */     
/*      */     public V setValue(V v) {
/*  602 */       V oldValue = Float2ReferenceOpenCustomHashMap.this.value[this.index];
/*  603 */       Float2ReferenceOpenCustomHashMap.this.value[this.index] = v;
/*  604 */       return oldValue;
/*      */     }
/*      */ 
/*      */     
/*      */     public FloatReferencePair<V> right(V v) {
/*  609 */       Float2ReferenceOpenCustomHashMap.this.value[this.index] = v;
/*  610 */       return this;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     @Deprecated
/*      */     public Float getKey() {
/*  621 */       return Float.valueOf(Float2ReferenceOpenCustomHashMap.this.key[this.index]);
/*      */     }
/*      */ 
/*      */ 
/*      */     
/*      */     public boolean equals(Object o) {
/*  627 */       if (!(o instanceof Map.Entry)) return false; 
/*  628 */       Map.Entry<Float, V> e = (Map.Entry<Float, V>)o;
/*  629 */       return (Float2ReferenceOpenCustomHashMap.this.strategy.equals(Float2ReferenceOpenCustomHashMap.this.key[this.index], ((Float)e.getKey()).floatValue()) && Float2ReferenceOpenCustomHashMap.this.value[this.index] == e.getValue());
/*      */     }
/*      */ 
/*      */     
/*      */     public int hashCode() {
/*  634 */       return Float2ReferenceOpenCustomHashMap.this.strategy.hashCode(Float2ReferenceOpenCustomHashMap.this.key[this.index]) ^ ((Float2ReferenceOpenCustomHashMap.this.value[this.index] == null) ? 0 : System.identityHashCode(Float2ReferenceOpenCustomHashMap.this.value[this.index]));
/*      */     }
/*      */ 
/*      */     
/*      */     public String toString() {
/*  639 */       return Float2ReferenceOpenCustomHashMap.this.key[this.index] + "=>" + Float2ReferenceOpenCustomHashMap.this.value[this.index];
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
/*  650 */     int pos = Float2ReferenceOpenCustomHashMap.this.n;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  656 */     int last = -1;
/*      */     
/*  658 */     int c = Float2ReferenceOpenCustomHashMap.this.size;
/*      */     
/*  660 */     boolean mustReturnNullKey = Float2ReferenceOpenCustomHashMap.this.containsNullKey;
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     FloatArrayList wrapped;
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public boolean hasNext() {
/*  671 */       return (this.c != 0);
/*      */     }
/*      */     
/*      */     public int nextEntry() {
/*  675 */       if (!hasNext()) throw new NoSuchElementException(); 
/*  676 */       this.c--;
/*  677 */       if (this.mustReturnNullKey) {
/*  678 */         this.mustReturnNullKey = false;
/*  679 */         return this.last = Float2ReferenceOpenCustomHashMap.this.n;
/*      */       } 
/*  681 */       float[] key = Float2ReferenceOpenCustomHashMap.this.key;
/*      */       while (true) {
/*  683 */         if (--this.pos < 0) {
/*      */           
/*  685 */           this.last = Integer.MIN_VALUE;
/*  686 */           float k = this.wrapped.getFloat(-this.pos - 1);
/*  687 */           int p = HashCommon.mix(Float2ReferenceOpenCustomHashMap.this.strategy.hashCode(k)) & Float2ReferenceOpenCustomHashMap.this.mask;
/*  688 */           for (; !Float2ReferenceOpenCustomHashMap.this.strategy.equals(k, key[p]); p = p + 1 & Float2ReferenceOpenCustomHashMap.this.mask);
/*  689 */           return p;
/*      */         } 
/*  691 */         if (Float.floatToIntBits(key[this.pos]) != 0) return this.last = this.pos; 
/*      */       } 
/*      */     }
/*      */     
/*      */     public void forEachRemaining(ConsumerType action) {
/*  696 */       if (this.mustReturnNullKey) {
/*  697 */         this.mustReturnNullKey = false;
/*  698 */         acceptOnIndex(action, this.last = Float2ReferenceOpenCustomHashMap.this.n);
/*  699 */         this.c--;
/*      */       } 
/*  701 */       float[] key = Float2ReferenceOpenCustomHashMap.this.key;
/*  702 */       while (this.c != 0) {
/*  703 */         if (--this.pos < 0) {
/*      */           
/*  705 */           this.last = Integer.MIN_VALUE;
/*  706 */           float k = this.wrapped.getFloat(-this.pos - 1);
/*  707 */           int p = HashCommon.mix(Float2ReferenceOpenCustomHashMap.this.strategy.hashCode(k)) & Float2ReferenceOpenCustomHashMap.this.mask;
/*  708 */           for (; !Float2ReferenceOpenCustomHashMap.this.strategy.equals(k, key[p]); p = p + 1 & Float2ReferenceOpenCustomHashMap.this.mask);
/*  709 */           acceptOnIndex(action, p);
/*  710 */           this.c--; continue;
/*  711 */         }  if (Float.floatToIntBits(key[this.pos]) != 0) {
/*  712 */           acceptOnIndex(action, this.last = this.pos);
/*  713 */           this.c--;
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
/*  728 */       float[] key = Float2ReferenceOpenCustomHashMap.this.key; while (true) {
/*      */         float curr; int last;
/*  730 */         pos = (last = pos) + 1 & Float2ReferenceOpenCustomHashMap.this.mask;
/*      */         while (true) {
/*  732 */           if (Float.floatToIntBits(curr = key[pos]) == 0) {
/*  733 */             key[last] = 0.0F;
/*  734 */             Float2ReferenceOpenCustomHashMap.this.value[last] = null;
/*      */             return;
/*      */           } 
/*  737 */           int slot = HashCommon.mix(Float2ReferenceOpenCustomHashMap.this.strategy.hashCode(curr)) & Float2ReferenceOpenCustomHashMap.this.mask;
/*  738 */           if ((last <= pos) ? (last >= slot || slot > pos) : (last >= slot && slot > pos))
/*  739 */             break;  pos = pos + 1 & Float2ReferenceOpenCustomHashMap.this.mask;
/*      */         } 
/*  741 */         if (pos < last) {
/*  742 */           if (this.wrapped == null) this.wrapped = new FloatArrayList(2); 
/*  743 */           this.wrapped.add(key[pos]);
/*      */         } 
/*  745 */         key[last] = curr;
/*  746 */         Float2ReferenceOpenCustomHashMap.this.value[last] = Float2ReferenceOpenCustomHashMap.this.value[pos];
/*      */       } 
/*      */     }
/*      */     
/*      */     public void remove() {
/*  751 */       if (this.last == -1) throw new IllegalStateException(); 
/*  752 */       if (this.last == Float2ReferenceOpenCustomHashMap.this.n)
/*  753 */       { Float2ReferenceOpenCustomHashMap.this.containsNullKey = false;
/*  754 */         Float2ReferenceOpenCustomHashMap.this.value[Float2ReferenceOpenCustomHashMap.this.n] = null; }
/*  755 */       else if (this.pos >= 0) { shiftKeys(this.last); }
/*      */       else
/*      */       
/*  758 */       { Float2ReferenceOpenCustomHashMap.this.remove(this.wrapped.getFloat(-this.pos - 1));
/*  759 */         this.last = -1;
/*      */         return; }
/*      */       
/*  762 */       Float2ReferenceOpenCustomHashMap.this.size--;
/*  763 */       this.last = -1;
/*      */     }
/*      */ 
/*      */     
/*      */     public int skip(int n) {
/*  768 */       int i = n;
/*  769 */       for (; i-- != 0 && hasNext(); nextEntry());
/*  770 */       return n - i - 1;
/*      */     }
/*      */     
/*      */     private MapIterator() {}
/*      */     
/*      */     abstract void acceptOnIndex(ConsumerType param1ConsumerType, int param1Int);
/*      */   }
/*      */   
/*      */   private final class EntryIterator extends MapIterator<Consumer<? super Float2ReferenceMap.Entry<V>>> implements ObjectIterator<Float2ReferenceMap.Entry<V>> { public Float2ReferenceOpenCustomHashMap<V>.MapEntry next() {
/*  779 */       return this.entry = new Float2ReferenceOpenCustomHashMap.MapEntry(nextEntry());
/*      */     }
/*      */     private Float2ReferenceOpenCustomHashMap<V>.MapEntry entry;
/*      */     private EntryIterator() {}
/*      */     
/*      */     final void acceptOnIndex(Consumer<? super Float2ReferenceMap.Entry<V>> action, int index) {
/*  785 */       action.accept(this.entry = new Float2ReferenceOpenCustomHashMap.MapEntry(index));
/*      */     }
/*      */ 
/*      */     
/*      */     public void remove() {
/*  790 */       super.remove();
/*  791 */       this.entry.index = -1;
/*      */     } }
/*      */   
/*      */   private final class FastEntryIterator extends MapIterator<Consumer<? super Float2ReferenceMap.Entry<V>>> implements ObjectIterator<Float2ReferenceMap.Entry<V>> {
/*      */     private FastEntryIterator() {
/*  796 */       this.entry = new Float2ReferenceOpenCustomHashMap.MapEntry();
/*      */     }
/*      */     private final Float2ReferenceOpenCustomHashMap<V>.MapEntry entry;
/*      */     public Float2ReferenceOpenCustomHashMap<V>.MapEntry next() {
/*  800 */       this.entry.index = nextEntry();
/*  801 */       return this.entry;
/*      */     }
/*      */ 
/*      */ 
/*      */     
/*      */     final void acceptOnIndex(Consumer<? super Float2ReferenceMap.Entry<V>> action, int index) {
/*  807 */       this.entry.index = index;
/*  808 */       action.accept(this.entry);
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private abstract class MapSpliterator<ConsumerType, SplitType extends MapSpliterator<ConsumerType, SplitType>>
/*      */   {
/*  817 */     int pos = 0;
/*      */     
/*  819 */     int max = Float2ReferenceOpenCustomHashMap.this.n;
/*      */     
/*  821 */     int c = 0;
/*      */     
/*  823 */     boolean mustReturnNull = Float2ReferenceOpenCustomHashMap.this.containsNullKey;
/*      */     
/*      */     boolean hasSplit = false;
/*      */     
/*      */     MapSpliterator() {}
/*      */     
/*      */     MapSpliterator(int pos, int max, boolean mustReturnNull, boolean hasSplit) {
/*  830 */       this.pos = pos;
/*  831 */       this.max = max;
/*  832 */       this.mustReturnNull = mustReturnNull;
/*  833 */       this.hasSplit = hasSplit;
/*      */     }
/*      */     
/*      */     abstract void acceptOnIndex(ConsumerType param1ConsumerType, int param1Int);
/*      */     
/*      */     abstract SplitType makeForSplit(int param1Int1, int param1Int2, boolean param1Boolean);
/*      */     
/*      */     public boolean tryAdvance(ConsumerType action) {
/*  841 */       if (this.mustReturnNull) {
/*  842 */         this.mustReturnNull = false;
/*  843 */         this.c++;
/*  844 */         acceptOnIndex(action, Float2ReferenceOpenCustomHashMap.this.n);
/*  845 */         return true;
/*      */       } 
/*  847 */       float[] key = Float2ReferenceOpenCustomHashMap.this.key;
/*  848 */       while (this.pos < this.max) {
/*  849 */         if (Float.floatToIntBits(key[this.pos]) != 0) {
/*  850 */           this.c++;
/*  851 */           acceptOnIndex(action, this.pos++);
/*  852 */           return true;
/*      */         } 
/*  854 */         this.pos++;
/*      */       } 
/*  856 */       return false;
/*      */     }
/*      */     
/*      */     public void forEachRemaining(ConsumerType action) {
/*  860 */       if (this.mustReturnNull) {
/*  861 */         this.mustReturnNull = false;
/*  862 */         this.c++;
/*  863 */         acceptOnIndex(action, Float2ReferenceOpenCustomHashMap.this.n);
/*      */       } 
/*  865 */       float[] key = Float2ReferenceOpenCustomHashMap.this.key;
/*  866 */       while (this.pos < this.max) {
/*  867 */         if (Float.floatToIntBits(key[this.pos]) != 0) {
/*  868 */           acceptOnIndex(action, this.pos);
/*  869 */           this.c++;
/*      */         } 
/*  871 */         this.pos++;
/*      */       } 
/*      */     }
/*      */     
/*      */     public long estimateSize() {
/*  876 */       if (!this.hasSplit)
/*      */       {
/*  878 */         return (Float2ReferenceOpenCustomHashMap.this.size - this.c);
/*      */       }
/*      */ 
/*      */ 
/*      */       
/*  883 */       return Math.min((Float2ReferenceOpenCustomHashMap.this.size - this.c), (long)(Float2ReferenceOpenCustomHashMap.this.realSize() / Float2ReferenceOpenCustomHashMap.this.n * (this.max - this.pos)) + (this.mustReturnNull ? 1L : 0L));
/*      */     }
/*      */ 
/*      */     
/*      */     public SplitType trySplit() {
/*  888 */       if (this.pos >= this.max - 1) return null; 
/*  889 */       int retLen = this.max - this.pos >> 1;
/*  890 */       if (retLen <= 1) return null; 
/*  891 */       int myNewPos = this.pos + retLen;
/*  892 */       int retPos = this.pos;
/*  893 */       int retMax = myNewPos;
/*      */ 
/*      */ 
/*      */       
/*  897 */       SplitType split = makeForSplit(retPos, retMax, this.mustReturnNull);
/*  898 */       this.pos = myNewPos;
/*  899 */       this.mustReturnNull = false;
/*  900 */       this.hasSplit = true;
/*  901 */       return split;
/*      */     }
/*      */     
/*      */     public long skip(long n) {
/*  905 */       if (n < 0L) throw new IllegalArgumentException("Argument must be nonnegative: " + n); 
/*  906 */       if (n == 0L) return 0L; 
/*  907 */       long skipped = 0L;
/*  908 */       if (this.mustReturnNull) {
/*  909 */         this.mustReturnNull = false;
/*  910 */         skipped++;
/*  911 */         n--;
/*      */       } 
/*  913 */       float[] key = Float2ReferenceOpenCustomHashMap.this.key;
/*  914 */       while (this.pos < this.max && n > 0L) {
/*  915 */         if (Float.floatToIntBits(key[this.pos++]) != 0) {
/*  916 */           skipped++;
/*  917 */           n--;
/*      */         } 
/*      */       } 
/*  920 */       return skipped;
/*      */     }
/*      */   }
/*      */   
/*      */   private final class EntrySpliterator
/*      */     extends MapSpliterator<Consumer<? super Float2ReferenceMap.Entry<V>>, EntrySpliterator> implements ObjectSpliterator<Float2ReferenceMap.Entry<V>> {
/*      */     private static final int POST_SPLIT_CHARACTERISTICS = 1;
/*      */     
/*      */     EntrySpliterator() {}
/*      */     
/*      */     EntrySpliterator(int pos, int max, boolean mustReturnNull, boolean hasSplit) {
/*  931 */       super(pos, max, mustReturnNull, hasSplit);
/*      */     }
/*      */ 
/*      */     
/*      */     public int characteristics() {
/*  936 */       return this.hasSplit ? 1 : 65;
/*      */     }
/*      */ 
/*      */     
/*      */     final void acceptOnIndex(Consumer<? super Float2ReferenceMap.Entry<V>> action, int index) {
/*  941 */       action.accept(new Float2ReferenceOpenCustomHashMap.MapEntry(index));
/*      */     }
/*      */ 
/*      */     
/*      */     final EntrySpliterator makeForSplit(int pos, int max, boolean mustReturnNull) {
/*  946 */       return new EntrySpliterator(pos, max, mustReturnNull, true);
/*      */     } }
/*      */   
/*      */   private final class MapEntrySet extends AbstractObjectSet<Float2ReferenceMap.Entry<V>> implements Float2ReferenceMap.FastEntrySet<V> {
/*      */     private MapEntrySet() {}
/*      */     
/*      */     public ObjectIterator<Float2ReferenceMap.Entry<V>> iterator() {
/*  953 */       return new Float2ReferenceOpenCustomHashMap.EntryIterator();
/*      */     }
/*      */ 
/*      */     
/*      */     public ObjectIterator<Float2ReferenceMap.Entry<V>> fastIterator() {
/*  958 */       return new Float2ReferenceOpenCustomHashMap.FastEntryIterator();
/*      */     }
/*      */ 
/*      */     
/*      */     public ObjectSpliterator<Float2ReferenceMap.Entry<V>> spliterator() {
/*  963 */       return new Float2ReferenceOpenCustomHashMap.EntrySpliterator();
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public boolean contains(Object o) {
/*  970 */       if (!(o instanceof Map.Entry)) return false; 
/*  971 */       Map.Entry<?, ?> e = (Map.Entry<?, ?>)o;
/*  972 */       if (e.getKey() == null || !(e.getKey() instanceof Float)) return false; 
/*  973 */       float k = ((Float)e.getKey()).floatValue();
/*  974 */       V v = (V)e.getValue();
/*  975 */       if (Float2ReferenceOpenCustomHashMap.this.strategy.equals(k, 0.0F)) return (Float2ReferenceOpenCustomHashMap.this.containsNullKey && Float2ReferenceOpenCustomHashMap.this.value[Float2ReferenceOpenCustomHashMap.this.n] == v);
/*      */       
/*  977 */       float[] key = Float2ReferenceOpenCustomHashMap.this.key;
/*      */       float curr;
/*      */       int pos;
/*  980 */       if (Float.floatToIntBits(curr = key[pos = HashCommon.mix(Float2ReferenceOpenCustomHashMap.this.strategy.hashCode(k)) & Float2ReferenceOpenCustomHashMap.this.mask]) == 0) return false; 
/*  981 */       if (Float2ReferenceOpenCustomHashMap.this.strategy.equals(k, curr)) return (Float2ReferenceOpenCustomHashMap.this.value[pos] == v);
/*      */       
/*      */       while (true) {
/*  984 */         if (Float.floatToIntBits(curr = key[pos = pos + 1 & Float2ReferenceOpenCustomHashMap.this.mask]) == 0) return false; 
/*  985 */         if (Float2ReferenceOpenCustomHashMap.this.strategy.equals(k, curr)) return (Float2ReferenceOpenCustomHashMap.this.value[pos] == v);
/*      */       
/*      */       } 
/*      */     }
/*      */ 
/*      */     
/*      */     public boolean remove(Object o) {
/*  992 */       if (!(o instanceof Map.Entry)) return false; 
/*  993 */       Map.Entry<?, ?> e = (Map.Entry<?, ?>)o;
/*  994 */       if (e.getKey() == null || !(e.getKey() instanceof Float)) return false; 
/*  995 */       float k = ((Float)e.getKey()).floatValue();
/*  996 */       V v = (V)e.getValue();
/*  997 */       if (Float2ReferenceOpenCustomHashMap.this.strategy.equals(k, 0.0F)) {
/*  998 */         if (Float2ReferenceOpenCustomHashMap.this.containsNullKey && Float2ReferenceOpenCustomHashMap.this.value[Float2ReferenceOpenCustomHashMap.this.n] == v) {
/*  999 */           Float2ReferenceOpenCustomHashMap.this.removeNullEntry();
/* 1000 */           return true;
/*      */         } 
/* 1002 */         return false;
/*      */       } 
/*      */       
/* 1005 */       float[] key = Float2ReferenceOpenCustomHashMap.this.key;
/*      */       float curr;
/*      */       int pos;
/* 1008 */       if (Float.floatToIntBits(curr = key[pos = HashCommon.mix(Float2ReferenceOpenCustomHashMap.this.strategy.hashCode(k)) & Float2ReferenceOpenCustomHashMap.this.mask]) == 0) return false; 
/* 1009 */       if (Float2ReferenceOpenCustomHashMap.this.strategy.equals(curr, k)) {
/* 1010 */         if (Float2ReferenceOpenCustomHashMap.this.value[pos] == v) {
/* 1011 */           Float2ReferenceOpenCustomHashMap.this.removeEntry(pos);
/* 1012 */           return true;
/*      */         } 
/* 1014 */         return false;
/*      */       } 
/*      */       while (true) {
/* 1017 */         if (Float.floatToIntBits(curr = key[pos = pos + 1 & Float2ReferenceOpenCustomHashMap.this.mask]) == 0) return false; 
/* 1018 */         if (Float2ReferenceOpenCustomHashMap.this.strategy.equals(curr, k) && 
/* 1019 */           Float2ReferenceOpenCustomHashMap.this.value[pos] == v) {
/* 1020 */           Float2ReferenceOpenCustomHashMap.this.removeEntry(pos);
/* 1021 */           return true;
/*      */         } 
/*      */       } 
/*      */     }
/*      */ 
/*      */ 
/*      */     
/*      */     public int size() {
/* 1029 */       return Float2ReferenceOpenCustomHashMap.this.size;
/*      */     }
/*      */ 
/*      */     
/*      */     public void clear() {
/* 1034 */       Float2ReferenceOpenCustomHashMap.this.clear();
/*      */     }
/*      */ 
/*      */ 
/*      */     
/*      */     public void forEach(Consumer<? super Float2ReferenceMap.Entry<V>> consumer) {
/* 1040 */       if (Float2ReferenceOpenCustomHashMap.this.containsNullKey) consumer.accept(new Float2ReferenceOpenCustomHashMap.MapEntry(Float2ReferenceOpenCustomHashMap.this.n)); 
/* 1041 */       for (int pos = Float2ReferenceOpenCustomHashMap.this.n; pos-- != 0;) { if (Float.floatToIntBits(Float2ReferenceOpenCustomHashMap.this.key[pos]) != 0) consumer.accept(new Float2ReferenceOpenCustomHashMap.MapEntry(pos));
/*      */          }
/*      */     
/*      */     }
/*      */     
/*      */     public void fastForEach(Consumer<? super Float2ReferenceMap.Entry<V>> consumer) {
/* 1047 */       Float2ReferenceOpenCustomHashMap<V>.MapEntry entry = new Float2ReferenceOpenCustomHashMap.MapEntry();
/* 1048 */       if (Float2ReferenceOpenCustomHashMap.this.containsNullKey) {
/* 1049 */         entry.index = Float2ReferenceOpenCustomHashMap.this.n;
/* 1050 */         consumer.accept(entry);
/*      */       } 
/* 1052 */       for (int pos = Float2ReferenceOpenCustomHashMap.this.n; pos-- != 0;) { if (Float.floatToIntBits(Float2ReferenceOpenCustomHashMap.this.key[pos]) != 0) {
/* 1053 */           entry.index = pos;
/* 1054 */           consumer.accept(entry);
/*      */         }  }
/*      */     
/*      */     }
/*      */   }
/*      */   
/*      */   public Float2ReferenceMap.FastEntrySet<V> float2ReferenceEntrySet() {
/* 1061 */     if (this.entries == null) this.entries = new MapEntrySet(); 
/* 1062 */     return this.entries;
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
/*      */     extends MapIterator<FloatConsumer>
/*      */     implements FloatIterator
/*      */   {
/*      */     final void acceptOnIndex(FloatConsumer action, int index) {
/* 1083 */       action.accept(Float2ReferenceOpenCustomHashMap.this.key[index]);
/*      */     }
/*      */ 
/*      */     
/*      */     public float nextFloat() {
/* 1088 */       return Float2ReferenceOpenCustomHashMap.this.key[nextEntry()];
/*      */     }
/*      */   }
/*      */   
/*      */   private final class KeySpliterator
/*      */     extends MapSpliterator<FloatConsumer, KeySpliterator> implements FloatSpliterator {
/*      */     private static final int POST_SPLIT_CHARACTERISTICS = 257;
/*      */     
/*      */     KeySpliterator() {}
/*      */     
/*      */     KeySpliterator(int pos, int max, boolean mustReturnNull, boolean hasSplit) {
/* 1099 */       super(pos, max, mustReturnNull, hasSplit);
/*      */     }
/*      */ 
/*      */     
/*      */     public int characteristics() {
/* 1104 */       return this.hasSplit ? 257 : 321;
/*      */     }
/*      */ 
/*      */     
/*      */     final void acceptOnIndex(FloatConsumer action, int index) {
/* 1109 */       action.accept(Float2ReferenceOpenCustomHashMap.this.key[index]);
/*      */     }
/*      */ 
/*      */     
/*      */     final KeySpliterator makeForSplit(int pos, int max, boolean mustReturnNull) {
/* 1114 */       return new KeySpliterator(pos, max, mustReturnNull, true);
/*      */     } }
/*      */   
/*      */   private final class KeySet extends AbstractFloatSet {
/*      */     private KeySet() {}
/*      */     
/*      */     public FloatIterator iterator() {
/* 1121 */       return new Float2ReferenceOpenCustomHashMap.KeyIterator();
/*      */     }
/*      */ 
/*      */     
/*      */     public FloatSpliterator spliterator() {
/* 1126 */       return new Float2ReferenceOpenCustomHashMap.KeySpliterator();
/*      */     }
/*      */ 
/*      */ 
/*      */     
/*      */     public void forEach(FloatConsumer consumer) {
/* 1132 */       if (Float2ReferenceOpenCustomHashMap.this.containsNullKey) consumer.accept(Float2ReferenceOpenCustomHashMap.this.key[Float2ReferenceOpenCustomHashMap.this.n]); 
/* 1133 */       for (int pos = Float2ReferenceOpenCustomHashMap.this.n; pos-- != 0; ) {
/* 1134 */         float k = Float2ReferenceOpenCustomHashMap.this.key[pos];
/* 1135 */         if (Float.floatToIntBits(k) != 0) consumer.accept(k);
/*      */       
/*      */       } 
/*      */     }
/*      */     
/*      */     public int size() {
/* 1141 */       return Float2ReferenceOpenCustomHashMap.this.size;
/*      */     }
/*      */ 
/*      */     
/*      */     public boolean contains(float k) {
/* 1146 */       return Float2ReferenceOpenCustomHashMap.this.containsKey(k);
/*      */     }
/*      */ 
/*      */     
/*      */     public boolean remove(float k) {
/* 1151 */       int oldSize = Float2ReferenceOpenCustomHashMap.this.size;
/* 1152 */       Float2ReferenceOpenCustomHashMap.this.remove(k);
/* 1153 */       return (Float2ReferenceOpenCustomHashMap.this.size != oldSize);
/*      */     }
/*      */ 
/*      */     
/*      */     public void clear() {
/* 1158 */       Float2ReferenceOpenCustomHashMap.this.clear();
/*      */     }
/*      */   }
/*      */ 
/*      */   
/*      */   public FloatSet keySet() {
/* 1164 */     if (this.keys == null) this.keys = new KeySet(); 
/* 1165 */     return this.keys;
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
/* 1186 */       action.accept(Float2ReferenceOpenCustomHashMap.this.value[index]);
/*      */     }
/*      */ 
/*      */     
/*      */     public V next() {
/* 1191 */       return Float2ReferenceOpenCustomHashMap.this.value[nextEntry()];
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
/* 1202 */       super(pos, max, mustReturnNull, hasSplit);
/*      */     }
/*      */ 
/*      */     
/*      */     public int characteristics() {
/* 1207 */       return this.hasSplit ? 0 : 64;
/*      */     }
/*      */ 
/*      */     
/*      */     final void acceptOnIndex(Consumer<? super V> action, int index) {
/* 1212 */       action.accept(Float2ReferenceOpenCustomHashMap.this.value[index]);
/*      */     }
/*      */ 
/*      */     
/*      */     final ValueSpliterator makeForSplit(int pos, int max, boolean mustReturnNull) {
/* 1217 */       return new ValueSpliterator(pos, max, mustReturnNull, true);
/*      */     }
/*      */   }
/*      */ 
/*      */   
/*      */   public ReferenceCollection<V> values() {
/* 1223 */     if (this.values == null) this.values = (ReferenceCollection<V>)new AbstractReferenceCollection<V>()
/*      */         {
/*      */           public ObjectIterator<V> iterator() {
/* 1226 */             return new Float2ReferenceOpenCustomHashMap.ValueIterator();
/*      */           }
/*      */ 
/*      */           
/*      */           public ObjectSpliterator<V> spliterator() {
/* 1231 */             return new Float2ReferenceOpenCustomHashMap.ValueSpliterator();
/*      */           }
/*      */ 
/*      */ 
/*      */           
/*      */           public void forEach(Consumer<? super V> consumer) {
/* 1237 */             if (Float2ReferenceOpenCustomHashMap.this.containsNullKey) consumer.accept(Float2ReferenceOpenCustomHashMap.this.value[Float2ReferenceOpenCustomHashMap.this.n]); 
/* 1238 */             for (int pos = Float2ReferenceOpenCustomHashMap.this.n; pos-- != 0;) { if (Float.floatToIntBits(Float2ReferenceOpenCustomHashMap.this.key[pos]) != 0) consumer.accept(Float2ReferenceOpenCustomHashMap.this.value[pos]);  }
/*      */           
/*      */           }
/*      */           
/*      */           public int size() {
/* 1243 */             return Float2ReferenceOpenCustomHashMap.this.size;
/*      */           }
/*      */ 
/*      */           
/*      */           public boolean contains(Object v) {
/* 1248 */             return Float2ReferenceOpenCustomHashMap.this.containsValue(v);
/*      */           }
/*      */ 
/*      */           
/*      */           public void clear() {
/* 1253 */             Float2ReferenceOpenCustomHashMap.this.clear();
/*      */           }
/*      */         }; 
/* 1256 */     return this.values;
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
/* 1273 */     return trim(this.size);
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
/* 1295 */     int l = HashCommon.nextPowerOfTwo((int)Math.ceil((n / this.f)));
/* 1296 */     if (l >= this.n || this.size > HashCommon.maxFill(l, this.f)) return true; 
/*      */     try {
/* 1298 */       rehash(l);
/* 1299 */     } catch (OutOfMemoryError cantDoIt) {
/* 1300 */       return false;
/*      */     } 
/* 1302 */     return true;
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
/* 1317 */     float[] key = this.key;
/* 1318 */     V[] value = this.value;
/* 1319 */     int mask = newN - 1;
/* 1320 */     float[] newKey = new float[newN + 1];
/* 1321 */     V[] newValue = (V[])new Object[newN + 1];
/* 1322 */     int i = this.n;
/* 1323 */     for (int j = realSize(); j-- != 0; ) {
/* 1324 */       while (Float.floatToIntBits(key[--i]) == 0); int pos;
/* 1325 */       if (Float.floatToIntBits(newKey[pos = HashCommon.mix(this.strategy.hashCode(key[i])) & mask]) != 0) while (Float.floatToIntBits(newKey[pos = pos + 1 & mask]) != 0); 
/* 1326 */       newKey[pos] = key[i];
/* 1327 */       newValue[pos] = value[i];
/*      */     } 
/* 1329 */     newValue[newN] = value[this.n];
/* 1330 */     this.n = newN;
/* 1331 */     this.mask = mask;
/* 1332 */     this.maxFill = HashCommon.maxFill(this.n, this.f);
/* 1333 */     this.key = newKey;
/* 1334 */     this.value = newValue;
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
/*      */   public Float2ReferenceOpenCustomHashMap<V> clone() {
/*      */     Float2ReferenceOpenCustomHashMap<V> c;
/*      */     try {
/* 1351 */       c = (Float2ReferenceOpenCustomHashMap<V>)super.clone();
/* 1352 */     } catch (CloneNotSupportedException cantHappen) {
/* 1353 */       throw new InternalError();
/*      */     } 
/* 1355 */     c.keys = null;
/* 1356 */     c.values = null;
/* 1357 */     c.entries = null;
/* 1358 */     c.containsNullKey = this.containsNullKey;
/* 1359 */     c.key = (float[])this.key.clone();
/* 1360 */     c.value = (V[])this.value.clone();
/* 1361 */     c.strategy = this.strategy;
/* 1362 */     return c;
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
/* 1376 */     int h = 0;
/* 1377 */     for (int j = realSize(), i = 0, t = 0; j-- != 0; ) {
/* 1378 */       for (; Float.floatToIntBits(this.key[i]) == 0; i++);
/* 1379 */       t = this.strategy.hashCode(this.key[i]);
/* 1380 */       if (this != this.value[i]) t ^= (this.value[i] == null) ? 0 : System.identityHashCode(this.value[i]); 
/* 1381 */       h += t;
/* 1382 */       i++;
/*      */     } 
/*      */     
/* 1385 */     if (this.containsNullKey) h += (this.value[this.n] == null) ? 0 : System.identityHashCode(this.value[this.n]); 
/* 1386 */     return h;
/*      */   }
/*      */   
/*      */   private void writeObject(ObjectOutputStream s) throws IOException {
/* 1390 */     float[] key = this.key;
/* 1391 */     V[] value = this.value;
/* 1392 */     EntryIterator i = new EntryIterator();
/* 1393 */     s.defaultWriteObject();
/* 1394 */     for (int j = this.size; j-- != 0; ) {
/* 1395 */       int e = i.nextEntry();
/* 1396 */       s.writeFloat(key[e]);
/* 1397 */       s.writeObject(value[e]);
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   private void readObject(ObjectInputStream s) throws IOException, ClassNotFoundException {
/* 1403 */     s.defaultReadObject();
/* 1404 */     this.n = HashCommon.arraySize(this.size, this.f);
/* 1405 */     this.maxFill = HashCommon.maxFill(this.n, this.f);
/* 1406 */     this.mask = this.n - 1;
/* 1407 */     float[] key = this.key = new float[this.n + 1];
/* 1408 */     V[] value = this.value = (V[])new Object[this.n + 1];
/*      */ 
/*      */     
/* 1411 */     for (int i = this.size; i-- != 0; ) {
/* 1412 */       int pos; float k = s.readFloat();
/* 1413 */       V v = (V)s.readObject();
/* 1414 */       if (this.strategy.equals(k, 0.0F)) {
/* 1415 */         pos = this.n;
/* 1416 */         this.containsNullKey = true;
/*      */       } else {
/* 1418 */         pos = HashCommon.mix(this.strategy.hashCode(k)) & this.mask;
/* 1419 */         for (; Float.floatToIntBits(key[pos]) != 0; pos = pos + 1 & this.mask);
/*      */       } 
/* 1421 */       key[pos] = k;
/* 1422 */       value[pos] = v;
/*      */     } 
/*      */   }
/*      */   
/*      */   private void checkTable() {}
/*      */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\i\\unimi\dsi\fastutil\floats\Float2ReferenceOpenCustomHashMap.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */