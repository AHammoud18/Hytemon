/*      */ package it.unimi.dsi.fastutil.objects;
/*      */ 
/*      */ import it.unimi.dsi.fastutil.Hash;
/*      */ import it.unimi.dsi.fastutil.HashCommon;
/*      */ import it.unimi.dsi.fastutil.SafeMath;
/*      */ import it.unimi.dsi.fastutil.floats.AbstractFloatCollection;
/*      */ import it.unimi.dsi.fastutil.floats.FloatCollection;
/*      */ import it.unimi.dsi.fastutil.floats.FloatConsumer;
/*      */ import it.unimi.dsi.fastutil.floats.FloatIterator;
/*      */ import it.unimi.dsi.fastutil.floats.FloatSpliterator;
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
/*      */ import java.util.function.ToDoubleFunction;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ public class Object2FloatOpenCustomHashMap<K>
/*      */   extends AbstractObject2FloatMap<K>
/*      */   implements Serializable, Cloneable, Hash
/*      */ {
/*      */   private static final long serialVersionUID = 0L;
/*      */   private static final boolean ASSERTS = false;
/*      */   protected transient K[] key;
/*      */   protected transient float[] value;
/*      */   protected transient int mask;
/*      */   protected transient boolean containsNullKey;
/*      */   protected Hash.Strategy<? super K> strategy;
/*      */   protected transient int n;
/*      */   protected transient int maxFill;
/*      */   protected final transient int minN;
/*      */   protected int size;
/*      */   protected final float f;
/*      */   protected transient Object2FloatMap.FastEntrySet<K> entries;
/*      */   protected transient ObjectSet<K> keys;
/*      */   protected transient FloatCollection values;
/*      */   
/*      */   public Object2FloatOpenCustomHashMap(int expected, float f, Hash.Strategy<? super K> strategy) {
/*  100 */     this.strategy = strategy;
/*  101 */     if (f <= 0.0F || f >= 1.0F) throw new IllegalArgumentException("Load factor must be greater than 0 and smaller than 1"); 
/*  102 */     if (expected < 0) throw new IllegalArgumentException("The expected number of elements must be nonnegative"); 
/*  103 */     this.f = f;
/*  104 */     this.minN = this.n = HashCommon.arraySize(expected, f);
/*  105 */     this.mask = this.n - 1;
/*  106 */     this.maxFill = HashCommon.maxFill(this.n, f);
/*  107 */     this.key = (K[])new Object[this.n + 1];
/*  108 */     this.value = new float[this.n + 1];
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Object2FloatOpenCustomHashMap(int expected, Hash.Strategy<? super K> strategy) {
/*  118 */     this(expected, 0.75F, strategy);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Object2FloatOpenCustomHashMap(Hash.Strategy<? super K> strategy) {
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
/*      */   public Object2FloatOpenCustomHashMap(Map<? extends K, ? extends Float> m, float f, Hash.Strategy<? super K> strategy) {
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
/*      */   public Object2FloatOpenCustomHashMap(Map<? extends K, ? extends Float> m, Hash.Strategy<? super K> strategy) {
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
/*      */   public Object2FloatOpenCustomHashMap(Object2FloatMap<K> m, float f, Hash.Strategy<? super K> strategy) {
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
/*      */   public Object2FloatOpenCustomHashMap(Object2FloatMap<K> m, Hash.Strategy<? super K> strategy) {
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
/*      */   public Object2FloatOpenCustomHashMap(K[] k, float[] v, float f, Hash.Strategy<? super K> strategy) {
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
/*      */   public Object2FloatOpenCustomHashMap(K[] k, float[] v, Hash.Strategy<? super K> strategy) {
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
/*      */   private float removeEntry(int pos) {
/*  234 */     float oldValue = this.value[pos];
/*  235 */     this.size--;
/*  236 */     shiftKeys(pos);
/*  237 */     if (this.n > this.minN && this.size < this.maxFill / 4 && this.n > 16) rehash(this.n / 2); 
/*  238 */     return oldValue;
/*      */   }
/*      */   
/*      */   private float removeNullEntry() {
/*  242 */     this.containsNullKey = false;
/*  243 */     this.key[this.n] = null;
/*  244 */     float oldValue = this.value[this.n];
/*  245 */     this.size--;
/*  246 */     if (this.n > this.minN && this.size < this.maxFill / 4 && this.n > 16) rehash(this.n / 2); 
/*  247 */     return oldValue;
/*      */   }
/*      */ 
/*      */   
/*      */   public void putAll(Map<? extends K, ? extends Float> m) {
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
/*      */   private void insert(int pos, K k, float v) {
/*  275 */     if (pos == this.n) this.containsNullKey = true; 
/*  276 */     this.key[pos] = k;
/*  277 */     this.value[pos] = v;
/*  278 */     if (this.size++ >= this.maxFill) rehash(HashCommon.arraySize(this.size + 1, this.f));
/*      */   
/*      */   }
/*      */ 
/*      */   
/*      */   public float put(K k, float v) {
/*  284 */     int pos = find(k);
/*  285 */     if (pos < 0) {
/*  286 */       insert(-pos - 1, k, v);
/*  287 */       return this.defRetValue;
/*      */     } 
/*  289 */     float oldValue = this.value[pos];
/*  290 */     this.value[pos] = v;
/*  291 */     return oldValue;
/*      */   }
/*      */   
/*      */   private float addToValue(int pos, float incr) {
/*  295 */     float oldValue = this.value[pos];
/*  296 */     this.value[pos] = oldValue + incr;
/*  297 */     return oldValue;
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
/*      */   public float addTo(K k, float incr) {
/*      */     int pos;
/*  315 */     if (this.strategy.equals(k, null)) {
/*  316 */       if (this.containsNullKey) return addToValue(this.n, incr); 
/*  317 */       pos = this.n;
/*  318 */       this.containsNullKey = true;
/*      */     } else {
/*      */       
/*  321 */       K[] key = this.key;
/*      */       K curr;
/*  323 */       if ((curr = key[pos = HashCommon.mix(this.strategy.hashCode(k)) & this.mask]) != null) {
/*  324 */         if (this.strategy.equals(curr, k)) return addToValue(pos, incr); 
/*  325 */         while ((curr = key[pos = pos + 1 & this.mask]) != null) { if (this.strategy.equals(curr, k)) return addToValue(pos, incr);  }
/*      */       
/*      */       } 
/*  328 */     }  this.key[pos] = k;
/*  329 */     this.value[pos] = this.defRetValue + incr;
/*  330 */     if (this.size++ >= this.maxFill) rehash(HashCommon.arraySize(this.size + 1, this.f));
/*      */     
/*  332 */     return this.defRetValue;
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
/*  345 */     K[] key = this.key; while (true) {
/*      */       K curr; int last;
/*  347 */       pos = (last = pos) + 1 & this.mask;
/*      */       while (true) {
/*  349 */         if ((curr = key[pos]) == null) {
/*  350 */           key[last] = null;
/*      */           return;
/*      */         } 
/*  353 */         int slot = HashCommon.mix(this.strategy.hashCode(curr)) & this.mask;
/*  354 */         if ((last <= pos) ? (last >= slot || slot > pos) : (last >= slot && slot > pos))
/*  355 */           break;  pos = pos + 1 & this.mask;
/*      */       } 
/*  357 */       key[last] = curr;
/*  358 */       this.value[last] = this.value[pos];
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public float removeFloat(Object k) {
/*  365 */     if (this.strategy.equals(k, null)) {
/*  366 */       if (this.containsNullKey) return removeNullEntry(); 
/*  367 */       return this.defRetValue;
/*      */     } 
/*      */     
/*  370 */     K[] key = this.key;
/*      */     K curr;
/*      */     int pos;
/*  373 */     if ((curr = key[pos = HashCommon.mix(this.strategy.hashCode(k)) & this.mask]) == null) return this.defRetValue; 
/*  374 */     if (this.strategy.equals(k, curr)) return removeEntry(pos); 
/*      */     while (true) {
/*  376 */       if ((curr = key[pos = pos + 1 & this.mask]) == null) return this.defRetValue; 
/*  377 */       if (this.strategy.equals(k, curr)) return removeEntry(pos);
/*      */     
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   public float getFloat(Object k) {
/*  384 */     if (this.strategy.equals(k, null)) return this.containsNullKey ? this.value[this.n] : this.defRetValue;
/*      */     
/*  386 */     K[] key = this.key;
/*      */     K curr;
/*      */     int pos;
/*  389 */     if ((curr = key[pos = HashCommon.mix(this.strategy.hashCode(k)) & this.mask]) == null) return this.defRetValue; 
/*  390 */     if (this.strategy.equals(k, curr)) return this.value[pos];
/*      */     
/*      */     while (true) {
/*  393 */       if ((curr = key[pos = pos + 1 & this.mask]) == null) return this.defRetValue; 
/*  394 */       if (this.strategy.equals(k, curr)) return this.value[pos];
/*      */     
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   public boolean containsKey(Object k) {
/*  401 */     if (this.strategy.equals(k, null)) return this.containsNullKey;
/*      */     
/*  403 */     K[] key = this.key;
/*      */     K curr;
/*      */     int pos;
/*  406 */     if ((curr = key[pos = HashCommon.mix(this.strategy.hashCode(k)) & this.mask]) == null) return false; 
/*  407 */     if (this.strategy.equals(k, curr)) return true;
/*      */     
/*      */     while (true) {
/*  410 */       if ((curr = key[pos = pos + 1 & this.mask]) == null) return false; 
/*  411 */       if (this.strategy.equals(k, curr)) return true;
/*      */     
/*      */     } 
/*      */   }
/*      */   
/*      */   public boolean containsValue(float v) {
/*  417 */     float[] value = this.value;
/*  418 */     K[] key = this.key;
/*  419 */     if (this.containsNullKey && Float.floatToIntBits(value[this.n]) == Float.floatToIntBits(v)) return true; 
/*  420 */     for (int i = this.n; i-- != 0;) { if (key[i] != null && Float.floatToIntBits(value[i]) == Float.floatToIntBits(v)) return true;  }
/*  421 */      return false;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public float getOrDefault(Object k, float defaultValue) {
/*  428 */     if (this.strategy.equals(k, null)) return this.containsNullKey ? this.value[this.n] : defaultValue;
/*      */     
/*  430 */     K[] key = this.key;
/*      */     K curr;
/*      */     int pos;
/*  433 */     if ((curr = key[pos = HashCommon.mix(this.strategy.hashCode(k)) & this.mask]) == null) return defaultValue; 
/*  434 */     if (this.strategy.equals(k, curr)) return this.value[pos];
/*      */     
/*      */     while (true) {
/*  437 */       if ((curr = key[pos = pos + 1 & this.mask]) == null) return defaultValue; 
/*  438 */       if (this.strategy.equals(k, curr)) return this.value[pos];
/*      */     
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   public float putIfAbsent(K k, float v) {
/*  445 */     int pos = find(k);
/*  446 */     if (pos >= 0) return this.value[pos]; 
/*  447 */     insert(-pos - 1, k, v);
/*  448 */     return this.defRetValue;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean remove(Object k, float v) {
/*  455 */     if (this.strategy.equals(k, null)) {
/*  456 */       if (this.containsNullKey && Float.floatToIntBits(v) == Float.floatToIntBits(this.value[this.n])) {
/*  457 */         removeNullEntry();
/*  458 */         return true;
/*      */       } 
/*  460 */       return false;
/*      */     } 
/*      */     
/*  463 */     K[] key = this.key;
/*      */     K curr;
/*      */     int pos;
/*  466 */     if ((curr = key[pos = HashCommon.mix(this.strategy.hashCode(k)) & this.mask]) == null) return false; 
/*  467 */     if (this.strategy.equals(k, curr) && Float.floatToIntBits(v) == Float.floatToIntBits(this.value[pos])) {
/*  468 */       removeEntry(pos);
/*  469 */       return true;
/*      */     } 
/*      */     while (true) {
/*  472 */       if ((curr = key[pos = pos + 1 & this.mask]) == null) return false; 
/*  473 */       if (this.strategy.equals(k, curr) && Float.floatToIntBits(v) == Float.floatToIntBits(this.value[pos])) {
/*  474 */         removeEntry(pos);
/*  475 */         return true;
/*      */       } 
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean replace(K k, float oldValue, float v) {
/*  483 */     int pos = find(k);
/*  484 */     if (pos < 0 || Float.floatToIntBits(oldValue) != Float.floatToIntBits(this.value[pos])) return false; 
/*  485 */     this.value[pos] = v;
/*  486 */     return true;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public float replace(K k, float v) {
/*  492 */     int pos = find(k);
/*  493 */     if (pos < 0) return this.defRetValue; 
/*  494 */     float oldValue = this.value[pos];
/*  495 */     this.value[pos] = v;
/*  496 */     return oldValue;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public float computeIfAbsent(K k, ToDoubleFunction<? super K> mappingFunction) {
/*  502 */     Objects.requireNonNull(mappingFunction);
/*  503 */     int pos = find(k);
/*  504 */     if (pos >= 0) return this.value[pos]; 
/*  505 */     float newValue = SafeMath.safeDoubleToFloat(mappingFunction.applyAsDouble(k));
/*  506 */     insert(-pos - 1, k, newValue);
/*  507 */     return newValue;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public float computeIfAbsent(K key, Object2FloatFunction<? super K> mappingFunction) {
/*  513 */     Objects.requireNonNull(mappingFunction);
/*  514 */     int pos = find(key);
/*  515 */     if (pos >= 0) return this.value[pos]; 
/*  516 */     if (!mappingFunction.containsKey(key)) return this.defRetValue; 
/*  517 */     float newValue = mappingFunction.getFloat(key);
/*  518 */     insert(-pos - 1, key, newValue);
/*  519 */     return newValue;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public float computeFloatIfPresent(K k, BiFunction<? super K, ? super Float, ? extends Float> remappingFunction) {
/*  525 */     Objects.requireNonNull(remappingFunction);
/*  526 */     int pos = find(k);
/*  527 */     if (pos < 0) return this.defRetValue; 
/*  528 */     Float newValue = remappingFunction.apply(k, Float.valueOf(this.value[pos]));
/*  529 */     if (newValue == null) {
/*  530 */       if (this.strategy.equals(k, null)) { removeNullEntry(); }
/*  531 */       else { removeEntry(pos); }
/*  532 */        return this.defRetValue;
/*      */     } 
/*  534 */     this.value[pos] = newValue.floatValue(); return newValue.floatValue();
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public float computeFloat(K k, BiFunction<? super K, ? super Float, ? extends Float> remappingFunction) {
/*  540 */     Objects.requireNonNull(remappingFunction);
/*  541 */     int pos = find(k);
/*  542 */     Float newValue = remappingFunction.apply(k, (pos >= 0) ? Float.valueOf(this.value[pos]) : null);
/*  543 */     if (newValue == null) {
/*  544 */       if (pos >= 0)
/*  545 */         if (this.strategy.equals(k, null)) { removeNullEntry(); }
/*  546 */         else { removeEntry(pos); }
/*      */          
/*  548 */       return this.defRetValue;
/*      */     } 
/*  550 */     float newVal = newValue.floatValue();
/*  551 */     if (pos < 0) {
/*  552 */       insert(-pos - 1, k, newVal);
/*  553 */       return newVal;
/*      */     } 
/*  555 */     this.value[pos] = newVal; return newVal;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public float merge(K k, float v, BiFunction<? super Float, ? super Float, ? extends Float> remappingFunction) {
/*  561 */     Objects.requireNonNull(remappingFunction);
/*      */     
/*  563 */     int pos = find(k);
/*  564 */     if (pos < 0) {
/*  565 */       if (pos < 0) { insert(-pos - 1, k, v); }
/*  566 */       else { this.value[pos] = v; }
/*  567 */        return v;
/*      */     } 
/*  569 */     Float newValue = remappingFunction.apply(Float.valueOf(this.value[pos]), Float.valueOf(v));
/*  570 */     if (newValue == null) {
/*  571 */       if (this.strategy.equals(k, null)) { removeNullEntry(); }
/*  572 */       else { removeEntry(pos); }
/*  573 */        return this.defRetValue;
/*      */     } 
/*  575 */     this.value[pos] = newValue.floatValue(); return newValue.floatValue();
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
/*  586 */     if (this.size == 0)
/*  587 */       return;  this.size = 0;
/*  588 */     this.containsNullKey = false;
/*  589 */     Arrays.fill((Object[])this.key, (Object)null);
/*      */   }
/*      */ 
/*      */   
/*      */   public int size() {
/*  594 */     return this.size;
/*      */   }
/*      */ 
/*      */   
/*      */   public boolean isEmpty() {
/*  599 */     return (this.size == 0);
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   final class MapEntry
/*      */     implements Object2FloatMap.Entry<K>, Map.Entry<K, Float>, ObjectFloatPair<K>
/*      */   {
/*      */     int index;
/*      */ 
/*      */ 
/*      */     
/*      */     MapEntry(int index) {
/*  612 */       this.index = index;
/*      */     }
/*      */ 
/*      */     
/*      */     MapEntry() {}
/*      */ 
/*      */     
/*      */     public K getKey() {
/*  620 */       return Object2FloatOpenCustomHashMap.this.key[this.index];
/*      */     }
/*      */ 
/*      */     
/*      */     public K left() {
/*  625 */       return Object2FloatOpenCustomHashMap.this.key[this.index];
/*      */     }
/*      */ 
/*      */     
/*      */     public float getFloatValue() {
/*  630 */       return Object2FloatOpenCustomHashMap.this.value[this.index];
/*      */     }
/*      */ 
/*      */     
/*      */     public float rightFloat() {
/*  635 */       return Object2FloatOpenCustomHashMap.this.value[this.index];
/*      */     }
/*      */ 
/*      */     
/*      */     public float setValue(float v) {
/*  640 */       float oldValue = Object2FloatOpenCustomHashMap.this.value[this.index];
/*  641 */       Object2FloatOpenCustomHashMap.this.value[this.index] = v;
/*  642 */       return oldValue;
/*      */     }
/*      */ 
/*      */     
/*      */     public ObjectFloatPair<K> right(float v) {
/*  647 */       Object2FloatOpenCustomHashMap.this.value[this.index] = v;
/*  648 */       return this;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     @Deprecated
/*      */     public Float getValue() {
/*  659 */       return Float.valueOf(Object2FloatOpenCustomHashMap.this.value[this.index]);
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     @Deprecated
/*      */     public Float setValue(Float v) {
/*  670 */       return Float.valueOf(setValue(v.floatValue()));
/*      */     }
/*      */ 
/*      */ 
/*      */     
/*      */     public boolean equals(Object o) {
/*  676 */       if (!(o instanceof Map.Entry)) return false; 
/*  677 */       Map.Entry<K, Float> e = (Map.Entry<K, Float>)o;
/*  678 */       return (Object2FloatOpenCustomHashMap.this.strategy.equals(Object2FloatOpenCustomHashMap.this.key[this.index], e.getKey()) && Float.floatToIntBits(Object2FloatOpenCustomHashMap.this.value[this.index]) == Float.floatToIntBits(((Float)e.getValue()).floatValue()));
/*      */     }
/*      */ 
/*      */     
/*      */     public int hashCode() {
/*  683 */       return Object2FloatOpenCustomHashMap.this.strategy.hashCode(Object2FloatOpenCustomHashMap.this.key[this.index]) ^ HashCommon.float2int(Object2FloatOpenCustomHashMap.this.value[this.index]);
/*      */     }
/*      */ 
/*      */     
/*      */     public String toString() {
/*  688 */       return (new StringBuilder()).append(Object2FloatOpenCustomHashMap.this.key[this.index]).append("=>").append(Object2FloatOpenCustomHashMap.this.value[this.index]).toString();
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
/*  699 */     int pos = Object2FloatOpenCustomHashMap.this.n;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  705 */     int last = -1;
/*      */     
/*  707 */     int c = Object2FloatOpenCustomHashMap.this.size;
/*      */     
/*  709 */     boolean mustReturnNullKey = Object2FloatOpenCustomHashMap.this.containsNullKey;
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     ObjectArrayList<K> wrapped;
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public boolean hasNext() {
/*  720 */       return (this.c != 0);
/*      */     }
/*      */     
/*      */     public int nextEntry() {
/*  724 */       if (!hasNext()) throw new NoSuchElementException(); 
/*  725 */       this.c--;
/*  726 */       if (this.mustReturnNullKey) {
/*  727 */         this.mustReturnNullKey = false;
/*  728 */         return this.last = Object2FloatOpenCustomHashMap.this.n;
/*      */       } 
/*  730 */       K[] key = Object2FloatOpenCustomHashMap.this.key;
/*      */       while (true) {
/*  732 */         if (--this.pos < 0) {
/*      */           
/*  734 */           this.last = Integer.MIN_VALUE;
/*  735 */           K k = this.wrapped.get(-this.pos - 1);
/*  736 */           int p = HashCommon.mix(Object2FloatOpenCustomHashMap.this.strategy.hashCode(k)) & Object2FloatOpenCustomHashMap.this.mask;
/*  737 */           for (; !Object2FloatOpenCustomHashMap.this.strategy.equals(k, key[p]); p = p + 1 & Object2FloatOpenCustomHashMap.this.mask);
/*  738 */           return p;
/*      */         } 
/*  740 */         if (key[this.pos] != null) return this.last = this.pos; 
/*      */       } 
/*      */     }
/*      */     
/*      */     public void forEachRemaining(ConsumerType action) {
/*  745 */       if (this.mustReturnNullKey) {
/*  746 */         this.mustReturnNullKey = false;
/*  747 */         acceptOnIndex(action, this.last = Object2FloatOpenCustomHashMap.this.n);
/*  748 */         this.c--;
/*      */       } 
/*  750 */       K[] key = Object2FloatOpenCustomHashMap.this.key;
/*  751 */       while (this.c != 0) {
/*  752 */         if (--this.pos < 0) {
/*      */           
/*  754 */           this.last = Integer.MIN_VALUE;
/*  755 */           K k = this.wrapped.get(-this.pos - 1);
/*  756 */           int p = HashCommon.mix(Object2FloatOpenCustomHashMap.this.strategy.hashCode(k)) & Object2FloatOpenCustomHashMap.this.mask;
/*  757 */           for (; !Object2FloatOpenCustomHashMap.this.strategy.equals(k, key[p]); p = p + 1 & Object2FloatOpenCustomHashMap.this.mask);
/*  758 */           acceptOnIndex(action, p);
/*  759 */           this.c--; continue;
/*  760 */         }  if (key[this.pos] != null) {
/*  761 */           acceptOnIndex(action, this.last = this.pos);
/*  762 */           this.c--;
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
/*  777 */       K[] key = Object2FloatOpenCustomHashMap.this.key; while (true) {
/*      */         K curr; int last;
/*  779 */         pos = (last = pos) + 1 & Object2FloatOpenCustomHashMap.this.mask;
/*      */         while (true) {
/*  781 */           if ((curr = key[pos]) == null) {
/*  782 */             key[last] = null;
/*      */             return;
/*      */           } 
/*  785 */           int slot = HashCommon.mix(Object2FloatOpenCustomHashMap.this.strategy.hashCode(curr)) & Object2FloatOpenCustomHashMap.this.mask;
/*  786 */           if ((last <= pos) ? (last >= slot || slot > pos) : (last >= slot && slot > pos))
/*  787 */             break;  pos = pos + 1 & Object2FloatOpenCustomHashMap.this.mask;
/*      */         } 
/*  789 */         if (pos < last) {
/*  790 */           if (this.wrapped == null) this.wrapped = new ObjectArrayList<>(2); 
/*  791 */           this.wrapped.add(key[pos]);
/*      */         } 
/*  793 */         key[last] = curr;
/*  794 */         Object2FloatOpenCustomHashMap.this.value[last] = Object2FloatOpenCustomHashMap.this.value[pos];
/*      */       } 
/*      */     }
/*      */     
/*      */     public void remove() {
/*  799 */       if (this.last == -1) throw new IllegalStateException(); 
/*  800 */       if (this.last == Object2FloatOpenCustomHashMap.this.n)
/*  801 */       { Object2FloatOpenCustomHashMap.this.containsNullKey = false;
/*  802 */         Object2FloatOpenCustomHashMap.this.key[Object2FloatOpenCustomHashMap.this.n] = null; }
/*  803 */       else if (this.pos >= 0) { shiftKeys(this.last); }
/*      */       else
/*      */       
/*  806 */       { Object2FloatOpenCustomHashMap.this.removeFloat(this.wrapped.set(-this.pos - 1, null));
/*  807 */         this.last = -1;
/*      */         return; }
/*      */       
/*  810 */       Object2FloatOpenCustomHashMap.this.size--;
/*  811 */       this.last = -1;
/*      */     }
/*      */ 
/*      */     
/*      */     public int skip(int n) {
/*  816 */       int i = n;
/*  817 */       for (; i-- != 0 && hasNext(); nextEntry());
/*  818 */       return n - i - 1;
/*      */     }
/*      */     
/*      */     private MapIterator() {}
/*      */     
/*      */     abstract void acceptOnIndex(ConsumerType param1ConsumerType, int param1Int);
/*      */   }
/*      */   
/*      */   private final class EntryIterator extends MapIterator<Consumer<? super Object2FloatMap.Entry<K>>> implements ObjectIterator<Object2FloatMap.Entry<K>> { public Object2FloatOpenCustomHashMap<K>.MapEntry next() {
/*  827 */       return this.entry = new Object2FloatOpenCustomHashMap.MapEntry(nextEntry());
/*      */     }
/*      */     private Object2FloatOpenCustomHashMap<K>.MapEntry entry;
/*      */     private EntryIterator() {}
/*      */     
/*      */     final void acceptOnIndex(Consumer<? super Object2FloatMap.Entry<K>> action, int index) {
/*  833 */       action.accept(this.entry = new Object2FloatOpenCustomHashMap.MapEntry(index));
/*      */     }
/*      */ 
/*      */     
/*      */     public void remove() {
/*  838 */       super.remove();
/*  839 */       this.entry.index = -1;
/*      */     } }
/*      */   
/*      */   private final class FastEntryIterator extends MapIterator<Consumer<? super Object2FloatMap.Entry<K>>> implements ObjectIterator<Object2FloatMap.Entry<K>> {
/*      */     private FastEntryIterator() {
/*  844 */       this.entry = new Object2FloatOpenCustomHashMap.MapEntry();
/*      */     }
/*      */     private final Object2FloatOpenCustomHashMap<K>.MapEntry entry;
/*      */     public Object2FloatOpenCustomHashMap<K>.MapEntry next() {
/*  848 */       this.entry.index = nextEntry();
/*  849 */       return this.entry;
/*      */     }
/*      */ 
/*      */ 
/*      */     
/*      */     final void acceptOnIndex(Consumer<? super Object2FloatMap.Entry<K>> action, int index) {
/*  855 */       this.entry.index = index;
/*  856 */       action.accept(this.entry);
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private abstract class MapSpliterator<ConsumerType, SplitType extends MapSpliterator<ConsumerType, SplitType>>
/*      */   {
/*  865 */     int pos = 0;
/*      */     
/*  867 */     int max = Object2FloatOpenCustomHashMap.this.n;
/*      */     
/*  869 */     int c = 0;
/*      */     
/*  871 */     boolean mustReturnNull = Object2FloatOpenCustomHashMap.this.containsNullKey;
/*      */     
/*      */     boolean hasSplit = false;
/*      */     
/*      */     MapSpliterator() {}
/*      */     
/*      */     MapSpliterator(int pos, int max, boolean mustReturnNull, boolean hasSplit) {
/*  878 */       this.pos = pos;
/*  879 */       this.max = max;
/*  880 */       this.mustReturnNull = mustReturnNull;
/*  881 */       this.hasSplit = hasSplit;
/*      */     }
/*      */     
/*      */     abstract void acceptOnIndex(ConsumerType param1ConsumerType, int param1Int);
/*      */     
/*      */     abstract SplitType makeForSplit(int param1Int1, int param1Int2, boolean param1Boolean);
/*      */     
/*      */     public boolean tryAdvance(ConsumerType action) {
/*  889 */       if (this.mustReturnNull) {
/*  890 */         this.mustReturnNull = false;
/*  891 */         this.c++;
/*  892 */         acceptOnIndex(action, Object2FloatOpenCustomHashMap.this.n);
/*  893 */         return true;
/*      */       } 
/*  895 */       K[] key = Object2FloatOpenCustomHashMap.this.key;
/*  896 */       while (this.pos < this.max) {
/*  897 */         if (key[this.pos] != null) {
/*  898 */           this.c++;
/*  899 */           acceptOnIndex(action, this.pos++);
/*  900 */           return true;
/*      */         } 
/*  902 */         this.pos++;
/*      */       } 
/*  904 */       return false;
/*      */     }
/*      */     
/*      */     public void forEachRemaining(ConsumerType action) {
/*  908 */       if (this.mustReturnNull) {
/*  909 */         this.mustReturnNull = false;
/*  910 */         this.c++;
/*  911 */         acceptOnIndex(action, Object2FloatOpenCustomHashMap.this.n);
/*      */       } 
/*  913 */       K[] key = Object2FloatOpenCustomHashMap.this.key;
/*  914 */       while (this.pos < this.max) {
/*  915 */         if (key[this.pos] != null) {
/*  916 */           acceptOnIndex(action, this.pos);
/*  917 */           this.c++;
/*      */         } 
/*  919 */         this.pos++;
/*      */       } 
/*      */     }
/*      */     
/*      */     public long estimateSize() {
/*  924 */       if (!this.hasSplit)
/*      */       {
/*  926 */         return (Object2FloatOpenCustomHashMap.this.size - this.c);
/*      */       }
/*      */ 
/*      */ 
/*      */       
/*  931 */       return Math.min((Object2FloatOpenCustomHashMap.this.size - this.c), (long)(Object2FloatOpenCustomHashMap.this.realSize() / Object2FloatOpenCustomHashMap.this.n * (this.max - this.pos)) + (this.mustReturnNull ? 1L : 0L));
/*      */     }
/*      */ 
/*      */     
/*      */     public SplitType trySplit() {
/*  936 */       if (this.pos >= this.max - 1) return null; 
/*  937 */       int retLen = this.max - this.pos >> 1;
/*  938 */       if (retLen <= 1) return null; 
/*  939 */       int myNewPos = this.pos + retLen;
/*  940 */       int retPos = this.pos;
/*  941 */       int retMax = myNewPos;
/*      */ 
/*      */ 
/*      */       
/*  945 */       SplitType split = makeForSplit(retPos, retMax, this.mustReturnNull);
/*  946 */       this.pos = myNewPos;
/*  947 */       this.mustReturnNull = false;
/*  948 */       this.hasSplit = true;
/*  949 */       return split;
/*      */     }
/*      */     
/*      */     public long skip(long n) {
/*  953 */       if (n < 0L) throw new IllegalArgumentException("Argument must be nonnegative: " + n); 
/*  954 */       if (n == 0L) return 0L; 
/*  955 */       long skipped = 0L;
/*  956 */       if (this.mustReturnNull) {
/*  957 */         this.mustReturnNull = false;
/*  958 */         skipped++;
/*  959 */         n--;
/*      */       } 
/*  961 */       K[] key = Object2FloatOpenCustomHashMap.this.key;
/*  962 */       while (this.pos < this.max && n > 0L) {
/*  963 */         if (key[this.pos++] != null) {
/*  964 */           skipped++;
/*  965 */           n--;
/*      */         } 
/*      */       } 
/*  968 */       return skipped;
/*      */     }
/*      */   }
/*      */   
/*      */   private final class EntrySpliterator
/*      */     extends MapSpliterator<Consumer<? super Object2FloatMap.Entry<K>>, EntrySpliterator> implements ObjectSpliterator<Object2FloatMap.Entry<K>> {
/*      */     private static final int POST_SPLIT_CHARACTERISTICS = 1;
/*      */     
/*      */     EntrySpliterator() {}
/*      */     
/*      */     EntrySpliterator(int pos, int max, boolean mustReturnNull, boolean hasSplit) {
/*  979 */       super(pos, max, mustReturnNull, hasSplit);
/*      */     }
/*      */ 
/*      */     
/*      */     public int characteristics() {
/*  984 */       return this.hasSplit ? 1 : 65;
/*      */     }
/*      */ 
/*      */     
/*      */     final void acceptOnIndex(Consumer<? super Object2FloatMap.Entry<K>> action, int index) {
/*  989 */       action.accept(new Object2FloatOpenCustomHashMap.MapEntry(index));
/*      */     }
/*      */ 
/*      */     
/*      */     final EntrySpliterator makeForSplit(int pos, int max, boolean mustReturnNull) {
/*  994 */       return new EntrySpliterator(pos, max, mustReturnNull, true);
/*      */     } }
/*      */   
/*      */   private final class MapEntrySet extends AbstractObjectSet<Object2FloatMap.Entry<K>> implements Object2FloatMap.FastEntrySet<K> {
/*      */     private MapEntrySet() {}
/*      */     
/*      */     public ObjectIterator<Object2FloatMap.Entry<K>> iterator() {
/* 1001 */       return new Object2FloatOpenCustomHashMap.EntryIterator();
/*      */     }
/*      */ 
/*      */     
/*      */     public ObjectIterator<Object2FloatMap.Entry<K>> fastIterator() {
/* 1006 */       return new Object2FloatOpenCustomHashMap.FastEntryIterator();
/*      */     }
/*      */ 
/*      */     
/*      */     public ObjectSpliterator<Object2FloatMap.Entry<K>> spliterator() {
/* 1011 */       return new Object2FloatOpenCustomHashMap.EntrySpliterator();
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public boolean contains(Object o) {
/* 1018 */       if (!(o instanceof Map.Entry)) return false; 
/* 1019 */       Map.Entry<?, ?> e = (Map.Entry<?, ?>)o;
/* 1020 */       if (e.getValue() == null || !(e.getValue() instanceof Float)) return false; 
/* 1021 */       K k = (K)e.getKey();
/* 1022 */       float v = ((Float)e.getValue()).floatValue();
/* 1023 */       if (Object2FloatOpenCustomHashMap.this.strategy.equals(k, null)) return (Object2FloatOpenCustomHashMap.this.containsNullKey && Float.floatToIntBits(Object2FloatOpenCustomHashMap.this.value[Object2FloatOpenCustomHashMap.this.n]) == Float.floatToIntBits(v));
/*      */       
/* 1025 */       K[] key = Object2FloatOpenCustomHashMap.this.key;
/*      */       K curr;
/*      */       int pos;
/* 1028 */       if ((curr = key[pos = HashCommon.mix(Object2FloatOpenCustomHashMap.this.strategy.hashCode(k)) & Object2FloatOpenCustomHashMap.this.mask]) == null) return false; 
/* 1029 */       if (Object2FloatOpenCustomHashMap.this.strategy.equals(k, curr)) return (Float.floatToIntBits(Object2FloatOpenCustomHashMap.this.value[pos]) == Float.floatToIntBits(v));
/*      */       
/*      */       while (true) {
/* 1032 */         if ((curr = key[pos = pos + 1 & Object2FloatOpenCustomHashMap.this.mask]) == null) return false; 
/* 1033 */         if (Object2FloatOpenCustomHashMap.this.strategy.equals(k, curr)) return (Float.floatToIntBits(Object2FloatOpenCustomHashMap.this.value[pos]) == Float.floatToIntBits(v));
/*      */       
/*      */       } 
/*      */     }
/*      */ 
/*      */     
/*      */     public boolean remove(Object o) {
/* 1040 */       if (!(o instanceof Map.Entry)) return false; 
/* 1041 */       Map.Entry<?, ?> e = (Map.Entry<?, ?>)o;
/* 1042 */       if (e.getValue() == null || !(e.getValue() instanceof Float)) return false; 
/* 1043 */       K k = (K)e.getKey();
/* 1044 */       float v = ((Float)e.getValue()).floatValue();
/* 1045 */       if (Object2FloatOpenCustomHashMap.this.strategy.equals(k, null)) {
/* 1046 */         if (Object2FloatOpenCustomHashMap.this.containsNullKey && Float.floatToIntBits(Object2FloatOpenCustomHashMap.this.value[Object2FloatOpenCustomHashMap.this.n]) == Float.floatToIntBits(v)) {
/* 1047 */           Object2FloatOpenCustomHashMap.this.removeNullEntry();
/* 1048 */           return true;
/*      */         } 
/* 1050 */         return false;
/*      */       } 
/*      */       
/* 1053 */       K[] key = Object2FloatOpenCustomHashMap.this.key;
/*      */       K curr;
/*      */       int pos;
/* 1056 */       if ((curr = key[pos = HashCommon.mix(Object2FloatOpenCustomHashMap.this.strategy.hashCode(k)) & Object2FloatOpenCustomHashMap.this.mask]) == null) return false; 
/* 1057 */       if (Object2FloatOpenCustomHashMap.this.strategy.equals(curr, k)) {
/* 1058 */         if (Float.floatToIntBits(Object2FloatOpenCustomHashMap.this.value[pos]) == Float.floatToIntBits(v)) {
/* 1059 */           Object2FloatOpenCustomHashMap.this.removeEntry(pos);
/* 1060 */           return true;
/*      */         } 
/* 1062 */         return false;
/*      */       } 
/*      */       while (true) {
/* 1065 */         if ((curr = key[pos = pos + 1 & Object2FloatOpenCustomHashMap.this.mask]) == null) return false; 
/* 1066 */         if (Object2FloatOpenCustomHashMap.this.strategy.equals(curr, k) && 
/* 1067 */           Float.floatToIntBits(Object2FloatOpenCustomHashMap.this.value[pos]) == Float.floatToIntBits(v)) {
/* 1068 */           Object2FloatOpenCustomHashMap.this.removeEntry(pos);
/* 1069 */           return true;
/*      */         } 
/*      */       } 
/*      */     }
/*      */ 
/*      */ 
/*      */     
/*      */     public int size() {
/* 1077 */       return Object2FloatOpenCustomHashMap.this.size;
/*      */     }
/*      */ 
/*      */     
/*      */     public void clear() {
/* 1082 */       Object2FloatOpenCustomHashMap.this.clear();
/*      */     }
/*      */ 
/*      */ 
/*      */     
/*      */     public void forEach(Consumer<? super Object2FloatMap.Entry<K>> consumer) {
/* 1088 */       if (Object2FloatOpenCustomHashMap.this.containsNullKey) consumer.accept(new Object2FloatOpenCustomHashMap.MapEntry(Object2FloatOpenCustomHashMap.this.n)); 
/* 1089 */       for (int pos = Object2FloatOpenCustomHashMap.this.n; pos-- != 0;) { if (Object2FloatOpenCustomHashMap.this.key[pos] != null) consumer.accept(new Object2FloatOpenCustomHashMap.MapEntry(pos));
/*      */          }
/*      */     
/*      */     }
/*      */     
/*      */     public void fastForEach(Consumer<? super Object2FloatMap.Entry<K>> consumer) {
/* 1095 */       Object2FloatOpenCustomHashMap<K>.MapEntry entry = new Object2FloatOpenCustomHashMap.MapEntry();
/* 1096 */       if (Object2FloatOpenCustomHashMap.this.containsNullKey) {
/* 1097 */         entry.index = Object2FloatOpenCustomHashMap.this.n;
/* 1098 */         consumer.accept(entry);
/*      */       } 
/* 1100 */       for (int pos = Object2FloatOpenCustomHashMap.this.n; pos-- != 0;) { if (Object2FloatOpenCustomHashMap.this.key[pos] != null) {
/* 1101 */           entry.index = pos;
/* 1102 */           consumer.accept(entry);
/*      */         }  }
/*      */     
/*      */     }
/*      */   }
/*      */   
/*      */   public Object2FloatMap.FastEntrySet<K> object2FloatEntrySet() {
/* 1109 */     if (this.entries == null) this.entries = new MapEntrySet(); 
/* 1110 */     return this.entries;
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
/* 1131 */       action.accept(Object2FloatOpenCustomHashMap.this.key[index]);
/*      */     }
/*      */ 
/*      */     
/*      */     public K next() {
/* 1136 */       return Object2FloatOpenCustomHashMap.this.key[nextEntry()];
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
/* 1147 */       super(pos, max, mustReturnNull, hasSplit);
/*      */     }
/*      */ 
/*      */     
/*      */     public int characteristics() {
/* 1152 */       return this.hasSplit ? 1 : 65;
/*      */     }
/*      */ 
/*      */     
/*      */     final void acceptOnIndex(Consumer<? super K> action, int index) {
/* 1157 */       action.accept(Object2FloatOpenCustomHashMap.this.key[index]);
/*      */     }
/*      */ 
/*      */     
/*      */     final KeySpliterator makeForSplit(int pos, int max, boolean mustReturnNull) {
/* 1162 */       return new KeySpliterator(pos, max, mustReturnNull, true);
/*      */     } }
/*      */   
/*      */   private final class KeySet extends AbstractObjectSet<K> {
/*      */     private KeySet() {}
/*      */     
/*      */     public ObjectIterator<K> iterator() {
/* 1169 */       return new Object2FloatOpenCustomHashMap.KeyIterator();
/*      */     }
/*      */ 
/*      */     
/*      */     public ObjectSpliterator<K> spliterator() {
/* 1174 */       return new Object2FloatOpenCustomHashMap.KeySpliterator();
/*      */     }
/*      */ 
/*      */ 
/*      */     
/*      */     public void forEach(Consumer<? super K> consumer) {
/* 1180 */       if (Object2FloatOpenCustomHashMap.this.containsNullKey) consumer.accept(Object2FloatOpenCustomHashMap.this.key[Object2FloatOpenCustomHashMap.this.n]); 
/* 1181 */       for (int pos = Object2FloatOpenCustomHashMap.this.n; pos-- != 0; ) {
/* 1182 */         K k = Object2FloatOpenCustomHashMap.this.key[pos];
/* 1183 */         if (k != null) consumer.accept(k);
/*      */       
/*      */       } 
/*      */     }
/*      */     
/*      */     public int size() {
/* 1189 */       return Object2FloatOpenCustomHashMap.this.size;
/*      */     }
/*      */ 
/*      */     
/*      */     public boolean contains(Object k) {
/* 1194 */       return Object2FloatOpenCustomHashMap.this.containsKey(k);
/*      */     }
/*      */ 
/*      */     
/*      */     public boolean remove(Object k) {
/* 1199 */       int oldSize = Object2FloatOpenCustomHashMap.this.size;
/* 1200 */       Object2FloatOpenCustomHashMap.this.removeFloat(k);
/* 1201 */       return (Object2FloatOpenCustomHashMap.this.size != oldSize);
/*      */     }
/*      */ 
/*      */     
/*      */     public void clear() {
/* 1206 */       Object2FloatOpenCustomHashMap.this.clear();
/*      */     }
/*      */   }
/*      */ 
/*      */   
/*      */   public ObjectSet<K> keySet() {
/* 1212 */     if (this.keys == null) this.keys = new KeySet(); 
/* 1213 */     return this.keys;
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
/*      */     extends MapIterator<FloatConsumer>
/*      */     implements FloatIterator
/*      */   {
/*      */     final void acceptOnIndex(FloatConsumer action, int index) {
/* 1234 */       action.accept(Object2FloatOpenCustomHashMap.this.value[index]);
/*      */     }
/*      */ 
/*      */     
/*      */     public float nextFloat() {
/* 1239 */       return Object2FloatOpenCustomHashMap.this.value[nextEntry()];
/*      */     }
/*      */   }
/*      */   
/*      */   private final class ValueSpliterator
/*      */     extends MapSpliterator<FloatConsumer, ValueSpliterator> implements FloatSpliterator {
/*      */     private static final int POST_SPLIT_CHARACTERISTICS = 256;
/*      */     
/*      */     ValueSpliterator() {}
/*      */     
/*      */     ValueSpliterator(int pos, int max, boolean mustReturnNull, boolean hasSplit) {
/* 1250 */       super(pos, max, mustReturnNull, hasSplit);
/*      */     }
/*      */ 
/*      */     
/*      */     public int characteristics() {
/* 1255 */       return this.hasSplit ? 256 : 320;
/*      */     }
/*      */ 
/*      */     
/*      */     final void acceptOnIndex(FloatConsumer action, int index) {
/* 1260 */       action.accept(Object2FloatOpenCustomHashMap.this.value[index]);
/*      */     }
/*      */ 
/*      */     
/*      */     final ValueSpliterator makeForSplit(int pos, int max, boolean mustReturnNull) {
/* 1265 */       return new ValueSpliterator(pos, max, mustReturnNull, true);
/*      */     }
/*      */   }
/*      */ 
/*      */   
/*      */   public FloatCollection values() {
/* 1271 */     if (this.values == null) this.values = (FloatCollection)new AbstractFloatCollection()
/*      */         {
/*      */           public FloatIterator iterator() {
/* 1274 */             return new Object2FloatOpenCustomHashMap.ValueIterator();
/*      */           }
/*      */ 
/*      */           
/*      */           public FloatSpliterator spliterator() {
/* 1279 */             return new Object2FloatOpenCustomHashMap.ValueSpliterator();
/*      */           }
/*      */ 
/*      */ 
/*      */           
/*      */           public void forEach(FloatConsumer consumer) {
/* 1285 */             if (Object2FloatOpenCustomHashMap.this.containsNullKey) consumer.accept(Object2FloatOpenCustomHashMap.this.value[Object2FloatOpenCustomHashMap.this.n]); 
/* 1286 */             for (int pos = Object2FloatOpenCustomHashMap.this.n; pos-- != 0;) { if (Object2FloatOpenCustomHashMap.this.key[pos] != null) consumer.accept(Object2FloatOpenCustomHashMap.this.value[pos]);  }
/*      */           
/*      */           }
/*      */           
/*      */           public int size() {
/* 1291 */             return Object2FloatOpenCustomHashMap.this.size;
/*      */           }
/*      */ 
/*      */           
/*      */           public boolean contains(float v) {
/* 1296 */             return Object2FloatOpenCustomHashMap.this.containsValue(v);
/*      */           }
/*      */ 
/*      */           
/*      */           public void clear() {
/* 1301 */             Object2FloatOpenCustomHashMap.this.clear();
/*      */           }
/*      */         }; 
/* 1304 */     return this.values;
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
/* 1321 */     return trim(this.size);
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
/* 1343 */     int l = HashCommon.nextPowerOfTwo((int)Math.ceil((n / this.f)));
/* 1344 */     if (l >= this.n || this.size > HashCommon.maxFill(l, this.f)) return true; 
/*      */     try {
/* 1346 */       rehash(l);
/* 1347 */     } catch (OutOfMemoryError cantDoIt) {
/* 1348 */       return false;
/*      */     } 
/* 1350 */     return true;
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
/* 1365 */     K[] key = this.key;
/* 1366 */     float[] value = this.value;
/* 1367 */     int mask = newN - 1;
/* 1368 */     K[] newKey = (K[])new Object[newN + 1];
/* 1369 */     float[] newValue = new float[newN + 1];
/* 1370 */     int i = this.n;
/* 1371 */     for (int j = realSize(); j-- != 0; ) {
/* 1372 */       while (key[--i] == null); int pos;
/* 1373 */       if (newKey[pos = HashCommon.mix(this.strategy.hashCode(key[i])) & mask] != null) while (newKey[pos = pos + 1 & mask] != null); 
/* 1374 */       newKey[pos] = key[i];
/* 1375 */       newValue[pos] = value[i];
/*      */     } 
/* 1377 */     newValue[newN] = value[this.n];
/* 1378 */     this.n = newN;
/* 1379 */     this.mask = mask;
/* 1380 */     this.maxFill = HashCommon.maxFill(this.n, this.f);
/* 1381 */     this.key = newKey;
/* 1382 */     this.value = newValue;
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
/*      */   public Object2FloatOpenCustomHashMap<K> clone() {
/*      */     Object2FloatOpenCustomHashMap<K> c;
/*      */     try {
/* 1399 */       c = (Object2FloatOpenCustomHashMap<K>)super.clone();
/* 1400 */     } catch (CloneNotSupportedException cantHappen) {
/* 1401 */       throw new InternalError();
/*      */     } 
/* 1403 */     c.keys = null;
/* 1404 */     c.values = null;
/* 1405 */     c.entries = null;
/* 1406 */     c.containsNullKey = this.containsNullKey;
/* 1407 */     c.key = (K[])this.key.clone();
/* 1408 */     c.value = (float[])this.value.clone();
/* 1409 */     c.strategy = this.strategy;
/* 1410 */     return c;
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
/* 1424 */     int h = 0;
/* 1425 */     for (int j = realSize(), i = 0, t = 0; j-- != 0; ) {
/* 1426 */       for (; this.key[i] == null; i++);
/* 1427 */       if (this != this.key[i]) t = this.strategy.hashCode(this.key[i]); 
/* 1428 */       t ^= HashCommon.float2int(this.value[i]);
/* 1429 */       h += t;
/* 1430 */       i++;
/*      */     } 
/*      */     
/* 1433 */     if (this.containsNullKey) h += HashCommon.float2int(this.value[this.n]); 
/* 1434 */     return h;
/*      */   }
/*      */   
/*      */   private void writeObject(ObjectOutputStream s) throws IOException {
/* 1438 */     K[] key = this.key;
/* 1439 */     float[] value = this.value;
/* 1440 */     EntryIterator i = new EntryIterator();
/* 1441 */     s.defaultWriteObject();
/* 1442 */     for (int j = this.size; j-- != 0; ) {
/* 1443 */       int e = i.nextEntry();
/* 1444 */       s.writeObject(key[e]);
/* 1445 */       s.writeFloat(value[e]);
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   private void readObject(ObjectInputStream s) throws IOException, ClassNotFoundException {
/* 1451 */     s.defaultReadObject();
/* 1452 */     this.n = HashCommon.arraySize(this.size, this.f);
/* 1453 */     this.maxFill = HashCommon.maxFill(this.n, this.f);
/* 1454 */     this.mask = this.n - 1;
/* 1455 */     K[] key = this.key = (K[])new Object[this.n + 1];
/* 1456 */     float[] value = this.value = new float[this.n + 1];
/*      */ 
/*      */     
/* 1459 */     for (int i = this.size; i-- != 0; ) {
/* 1460 */       int pos; K k = (K)s.readObject();
/* 1461 */       float v = s.readFloat();
/* 1462 */       if (this.strategy.equals(k, null)) {
/* 1463 */         pos = this.n;
/* 1464 */         this.containsNullKey = true;
/*      */       } else {
/* 1466 */         pos = HashCommon.mix(this.strategy.hashCode(k)) & this.mask;
/* 1467 */         for (; key[pos] != null; pos = pos + 1 & this.mask);
/*      */       } 
/* 1469 */       key[pos] = k;
/* 1470 */       value[pos] = v;
/*      */     } 
/*      */   }
/*      */   
/*      */   private void checkTable() {}
/*      */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\i\\unimi\dsi\fastutil\objects\Object2FloatOpenCustomHashMap.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */