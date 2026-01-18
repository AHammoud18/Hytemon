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
/*      */ public class Reference2FloatOpenHashMap<K>
/*      */   extends AbstractReference2FloatMap<K>
/*      */   implements Serializable, Cloneable, Hash
/*      */ {
/*      */   private static final long serialVersionUID = 0L;
/*      */   private static final boolean ASSERTS = false;
/*      */   protected transient K[] key;
/*      */   protected transient float[] value;
/*      */   protected transient int mask;
/*      */   protected transient boolean containsNullKey;
/*      */   protected transient int n;
/*      */   protected transient int maxFill;
/*      */   protected final transient int minN;
/*      */   protected int size;
/*      */   protected final float f;
/*      */   protected transient Reference2FloatMap.FastEntrySet<K> entries;
/*      */   protected transient ReferenceSet<K> keys;
/*      */   protected transient FloatCollection values;
/*      */   
/*      */   public Reference2FloatOpenHashMap(int expected, float f) {
/*   96 */     if (f <= 0.0F || f >= 1.0F) throw new IllegalArgumentException("Load factor must be greater than 0 and smaller than 1"); 
/*   97 */     if (expected < 0) throw new IllegalArgumentException("The expected number of elements must be nonnegative"); 
/*   98 */     this.f = f;
/*   99 */     this.minN = this.n = HashCommon.arraySize(expected, f);
/*  100 */     this.mask = this.n - 1;
/*  101 */     this.maxFill = HashCommon.maxFill(this.n, f);
/*  102 */     this.key = (K[])new Object[this.n + 1];
/*  103 */     this.value = new float[this.n + 1];
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Reference2FloatOpenHashMap(int expected) {
/*  112 */     this(expected, 0.75F);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Reference2FloatOpenHashMap() {
/*  120 */     this(16, 0.75F);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Reference2FloatOpenHashMap(Map<? extends K, ? extends Float> m, float f) {
/*  130 */     this(m.size(), f);
/*  131 */     putAll(m);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Reference2FloatOpenHashMap(Map<? extends K, ? extends Float> m) {
/*  140 */     this(m, 0.75F);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Reference2FloatOpenHashMap(Reference2FloatMap<K> m, float f) {
/*  150 */     this(m.size(), f);
/*  151 */     putAll(m);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Reference2FloatOpenHashMap(Reference2FloatMap<K> m) {
/*  161 */     this(m, 0.75F);
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
/*      */   public Reference2FloatOpenHashMap(K[] k, float[] v, float f) {
/*  173 */     this(k.length, f);
/*  174 */     if (k.length != v.length) throw new IllegalArgumentException("The key array and the value array have different lengths (" + k.length + " and " + v.length + ")"); 
/*  175 */     for (int i = 0; i < k.length; ) { put(k[i], v[i]); i++; }
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
/*      */   public Reference2FloatOpenHashMap(K[] k, float[] v) {
/*  187 */     this(k, v, 0.75F);
/*      */   }
/*      */   
/*      */   private int realSize() {
/*  191 */     return this.containsNullKey ? (this.size - 1) : this.size;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void ensureCapacity(int capacity) {
/*  201 */     int needed = HashCommon.arraySize(capacity, this.f);
/*  202 */     if (needed > this.n) rehash(needed); 
/*      */   }
/*      */   
/*      */   private void tryCapacity(long capacity) {
/*  206 */     int needed = (int)Math.min(1073741824L, Math.max(2L, HashCommon.nextPowerOfTwo((long)Math.ceil(((float)capacity / this.f)))));
/*  207 */     if (needed > this.n) rehash(needed); 
/*      */   }
/*      */   
/*      */   private float removeEntry(int pos) {
/*  211 */     float oldValue = this.value[pos];
/*  212 */     this.size--;
/*  213 */     shiftKeys(pos);
/*  214 */     if (this.n > this.minN && this.size < this.maxFill / 4 && this.n > 16) rehash(this.n / 2); 
/*  215 */     return oldValue;
/*      */   }
/*      */   
/*      */   private float removeNullEntry() {
/*  219 */     this.containsNullKey = false;
/*  220 */     this.key[this.n] = null;
/*  221 */     float oldValue = this.value[this.n];
/*  222 */     this.size--;
/*  223 */     if (this.n > this.minN && this.size < this.maxFill / 4 && this.n > 16) rehash(this.n / 2); 
/*  224 */     return oldValue;
/*      */   }
/*      */ 
/*      */   
/*      */   public void putAll(Map<? extends K, ? extends Float> m) {
/*  229 */     if (this.f <= 0.5D) { ensureCapacity(m.size()); }
/*  230 */     else { tryCapacity((size() + m.size())); }
/*      */     
/*  232 */     super.putAll(m);
/*      */   }
/*      */ 
/*      */   
/*      */   private int find(K k) {
/*  237 */     if (k == null) return this.containsNullKey ? this.n : -(this.n + 1);
/*      */     
/*  239 */     K[] key = this.key;
/*      */     K curr;
/*      */     int pos;
/*  242 */     if ((curr = key[pos = HashCommon.mix(System.identityHashCode(k)) & this.mask]) == null) return -(pos + 1); 
/*  243 */     if (k == curr) return pos;
/*      */     
/*      */     while (true) {
/*  246 */       if ((curr = key[pos = pos + 1 & this.mask]) == null) return -(pos + 1); 
/*  247 */       if (k == curr) return pos; 
/*      */     } 
/*      */   }
/*      */   
/*      */   private void insert(int pos, K k, float v) {
/*  252 */     if (pos == this.n) this.containsNullKey = true; 
/*  253 */     this.key[pos] = k;
/*  254 */     this.value[pos] = v;
/*  255 */     if (this.size++ >= this.maxFill) rehash(HashCommon.arraySize(this.size + 1, this.f));
/*      */   
/*      */   }
/*      */ 
/*      */   
/*      */   public float put(K k, float v) {
/*  261 */     int pos = find(k);
/*  262 */     if (pos < 0) {
/*  263 */       insert(-pos - 1, k, v);
/*  264 */       return this.defRetValue;
/*      */     } 
/*  266 */     float oldValue = this.value[pos];
/*  267 */     this.value[pos] = v;
/*  268 */     return oldValue;
/*      */   }
/*      */   
/*      */   private float addToValue(int pos, float incr) {
/*  272 */     float oldValue = this.value[pos];
/*  273 */     this.value[pos] = oldValue + incr;
/*  274 */     return oldValue;
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
/*  292 */     if (k == null) {
/*  293 */       if (this.containsNullKey) return addToValue(this.n, incr); 
/*  294 */       pos = this.n;
/*  295 */       this.containsNullKey = true;
/*      */     } else {
/*      */       
/*  298 */       K[] key = this.key;
/*      */       K curr;
/*  300 */       if ((curr = key[pos = HashCommon.mix(System.identityHashCode(k)) & this.mask]) != null) {
/*  301 */         if (curr == k) return addToValue(pos, incr); 
/*  302 */         while ((curr = key[pos = pos + 1 & this.mask]) != null) { if (curr == k) return addToValue(pos, incr);  }
/*      */       
/*      */       } 
/*  305 */     }  this.key[pos] = k;
/*  306 */     this.value[pos] = this.defRetValue + incr;
/*  307 */     if (this.size++ >= this.maxFill) rehash(HashCommon.arraySize(this.size + 1, this.f));
/*      */     
/*  309 */     return this.defRetValue;
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
/*  322 */     K[] key = this.key; while (true) {
/*      */       K curr; int last;
/*  324 */       pos = (last = pos) + 1 & this.mask;
/*      */       while (true) {
/*  326 */         if ((curr = key[pos]) == null) {
/*  327 */           key[last] = null;
/*      */           return;
/*      */         } 
/*  330 */         int slot = HashCommon.mix(System.identityHashCode(curr)) & this.mask;
/*  331 */         if ((last <= pos) ? (last >= slot || slot > pos) : (last >= slot && slot > pos))
/*  332 */           break;  pos = pos + 1 & this.mask;
/*      */       } 
/*  334 */       key[last] = curr;
/*  335 */       this.value[last] = this.value[pos];
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public float removeFloat(Object k) {
/*  342 */     if (k == null) {
/*  343 */       if (this.containsNullKey) return removeNullEntry(); 
/*  344 */       return this.defRetValue;
/*      */     } 
/*      */     
/*  347 */     K[] key = this.key;
/*      */     K curr;
/*      */     int pos;
/*  350 */     if ((curr = key[pos = HashCommon.mix(System.identityHashCode(k)) & this.mask]) == null) return this.defRetValue; 
/*  351 */     if (k == curr) return removeEntry(pos); 
/*      */     while (true) {
/*  353 */       if ((curr = key[pos = pos + 1 & this.mask]) == null) return this.defRetValue; 
/*  354 */       if (k == curr) return removeEntry(pos);
/*      */     
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   public float getFloat(Object k) {
/*  361 */     if (k == null) return this.containsNullKey ? this.value[this.n] : this.defRetValue;
/*      */     
/*  363 */     K[] key = this.key;
/*      */     K curr;
/*      */     int pos;
/*  366 */     if ((curr = key[pos = HashCommon.mix(System.identityHashCode(k)) & this.mask]) == null) return this.defRetValue; 
/*  367 */     if (k == curr) return this.value[pos];
/*      */     
/*      */     while (true) {
/*  370 */       if ((curr = key[pos = pos + 1 & this.mask]) == null) return this.defRetValue; 
/*  371 */       if (k == curr) return this.value[pos];
/*      */     
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   public boolean containsKey(Object k) {
/*  378 */     if (k == null) return this.containsNullKey;
/*      */     
/*  380 */     K[] key = this.key;
/*      */     K curr;
/*      */     int pos;
/*  383 */     if ((curr = key[pos = HashCommon.mix(System.identityHashCode(k)) & this.mask]) == null) return false; 
/*  384 */     if (k == curr) return true;
/*      */     
/*      */     while (true) {
/*  387 */       if ((curr = key[pos = pos + 1 & this.mask]) == null) return false; 
/*  388 */       if (k == curr) return true;
/*      */     
/*      */     } 
/*      */   }
/*      */   
/*      */   public boolean containsValue(float v) {
/*  394 */     float[] value = this.value;
/*  395 */     K[] key = this.key;
/*  396 */     if (this.containsNullKey && Float.floatToIntBits(value[this.n]) == Float.floatToIntBits(v)) return true; 
/*  397 */     for (int i = this.n; i-- != 0;) { if (key[i] != null && Float.floatToIntBits(value[i]) == Float.floatToIntBits(v)) return true;  }
/*  398 */      return false;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public float getOrDefault(Object k, float defaultValue) {
/*  405 */     if (k == null) return this.containsNullKey ? this.value[this.n] : defaultValue;
/*      */     
/*  407 */     K[] key = this.key;
/*      */     K curr;
/*      */     int pos;
/*  410 */     if ((curr = key[pos = HashCommon.mix(System.identityHashCode(k)) & this.mask]) == null) return defaultValue; 
/*  411 */     if (k == curr) return this.value[pos];
/*      */     
/*      */     while (true) {
/*  414 */       if ((curr = key[pos = pos + 1 & this.mask]) == null) return defaultValue; 
/*  415 */       if (k == curr) return this.value[pos];
/*      */     
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   public float putIfAbsent(K k, float v) {
/*  422 */     int pos = find(k);
/*  423 */     if (pos >= 0) return this.value[pos]; 
/*  424 */     insert(-pos - 1, k, v);
/*  425 */     return this.defRetValue;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean remove(Object k, float v) {
/*  432 */     if (k == null) {
/*  433 */       if (this.containsNullKey && Float.floatToIntBits(v) == Float.floatToIntBits(this.value[this.n])) {
/*  434 */         removeNullEntry();
/*  435 */         return true;
/*      */       } 
/*  437 */       return false;
/*      */     } 
/*      */     
/*  440 */     K[] key = this.key;
/*      */     K curr;
/*      */     int pos;
/*  443 */     if ((curr = key[pos = HashCommon.mix(System.identityHashCode(k)) & this.mask]) == null) return false; 
/*  444 */     if (k == curr && Float.floatToIntBits(v) == Float.floatToIntBits(this.value[pos])) {
/*  445 */       removeEntry(pos);
/*  446 */       return true;
/*      */     } 
/*      */     while (true) {
/*  449 */       if ((curr = key[pos = pos + 1 & this.mask]) == null) return false; 
/*  450 */       if (k == curr && Float.floatToIntBits(v) == Float.floatToIntBits(this.value[pos])) {
/*  451 */         removeEntry(pos);
/*  452 */         return true;
/*      */       } 
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean replace(K k, float oldValue, float v) {
/*  460 */     int pos = find(k);
/*  461 */     if (pos < 0 || Float.floatToIntBits(oldValue) != Float.floatToIntBits(this.value[pos])) return false; 
/*  462 */     this.value[pos] = v;
/*  463 */     return true;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public float replace(K k, float v) {
/*  469 */     int pos = find(k);
/*  470 */     if (pos < 0) return this.defRetValue; 
/*  471 */     float oldValue = this.value[pos];
/*  472 */     this.value[pos] = v;
/*  473 */     return oldValue;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public float computeIfAbsent(K k, ToDoubleFunction<? super K> mappingFunction) {
/*  479 */     Objects.requireNonNull(mappingFunction);
/*  480 */     int pos = find(k);
/*  481 */     if (pos >= 0) return this.value[pos]; 
/*  482 */     float newValue = SafeMath.safeDoubleToFloat(mappingFunction.applyAsDouble(k));
/*  483 */     insert(-pos - 1, k, newValue);
/*  484 */     return newValue;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public float computeIfAbsent(K key, Reference2FloatFunction<? super K> mappingFunction) {
/*  490 */     Objects.requireNonNull(mappingFunction);
/*  491 */     int pos = find(key);
/*  492 */     if (pos >= 0) return this.value[pos]; 
/*  493 */     if (!mappingFunction.containsKey(key)) return this.defRetValue; 
/*  494 */     float newValue = mappingFunction.getFloat(key);
/*  495 */     insert(-pos - 1, key, newValue);
/*  496 */     return newValue;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public float computeFloatIfPresent(K k, BiFunction<? super K, ? super Float, ? extends Float> remappingFunction) {
/*  502 */     Objects.requireNonNull(remappingFunction);
/*  503 */     int pos = find(k);
/*  504 */     if (pos < 0) return this.defRetValue; 
/*  505 */     Float newValue = remappingFunction.apply(k, Float.valueOf(this.value[pos]));
/*  506 */     if (newValue == null) {
/*  507 */       if (k == null) { removeNullEntry(); }
/*  508 */       else { removeEntry(pos); }
/*  509 */        return this.defRetValue;
/*      */     } 
/*  511 */     this.value[pos] = newValue.floatValue(); return newValue.floatValue();
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public float computeFloat(K k, BiFunction<? super K, ? super Float, ? extends Float> remappingFunction) {
/*  517 */     Objects.requireNonNull(remappingFunction);
/*  518 */     int pos = find(k);
/*  519 */     Float newValue = remappingFunction.apply(k, (pos >= 0) ? Float.valueOf(this.value[pos]) : null);
/*  520 */     if (newValue == null) {
/*  521 */       if (pos >= 0)
/*  522 */         if (k == null) { removeNullEntry(); }
/*  523 */         else { removeEntry(pos); }
/*      */          
/*  525 */       return this.defRetValue;
/*      */     } 
/*  527 */     float newVal = newValue.floatValue();
/*  528 */     if (pos < 0) {
/*  529 */       insert(-pos - 1, k, newVal);
/*  530 */       return newVal;
/*      */     } 
/*  532 */     this.value[pos] = newVal; return newVal;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public float merge(K k, float v, BiFunction<? super Float, ? super Float, ? extends Float> remappingFunction) {
/*  538 */     Objects.requireNonNull(remappingFunction);
/*      */     
/*  540 */     int pos = find(k);
/*  541 */     if (pos < 0) {
/*  542 */       if (pos < 0) { insert(-pos - 1, k, v); }
/*  543 */       else { this.value[pos] = v; }
/*  544 */        return v;
/*      */     } 
/*  546 */     Float newValue = remappingFunction.apply(Float.valueOf(this.value[pos]), Float.valueOf(v));
/*  547 */     if (newValue == null) {
/*  548 */       if (k == null) { removeNullEntry(); }
/*  549 */       else { removeEntry(pos); }
/*  550 */        return this.defRetValue;
/*      */     } 
/*  552 */     this.value[pos] = newValue.floatValue(); return newValue.floatValue();
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
/*  563 */     if (this.size == 0)
/*  564 */       return;  this.size = 0;
/*  565 */     this.containsNullKey = false;
/*  566 */     Arrays.fill((Object[])this.key, (Object)null);
/*      */   }
/*      */ 
/*      */   
/*      */   public int size() {
/*  571 */     return this.size;
/*      */   }
/*      */ 
/*      */   
/*      */   public boolean isEmpty() {
/*  576 */     return (this.size == 0);
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   final class MapEntry
/*      */     implements Reference2FloatMap.Entry<K>, Map.Entry<K, Float>, ReferenceFloatPair<K>
/*      */   {
/*      */     int index;
/*      */ 
/*      */ 
/*      */     
/*      */     MapEntry(int index) {
/*  589 */       this.index = index;
/*      */     }
/*      */ 
/*      */     
/*      */     MapEntry() {}
/*      */ 
/*      */     
/*      */     public K getKey() {
/*  597 */       return Reference2FloatOpenHashMap.this.key[this.index];
/*      */     }
/*      */ 
/*      */     
/*      */     public K left() {
/*  602 */       return Reference2FloatOpenHashMap.this.key[this.index];
/*      */     }
/*      */ 
/*      */     
/*      */     public float getFloatValue() {
/*  607 */       return Reference2FloatOpenHashMap.this.value[this.index];
/*      */     }
/*      */ 
/*      */     
/*      */     public float rightFloat() {
/*  612 */       return Reference2FloatOpenHashMap.this.value[this.index];
/*      */     }
/*      */ 
/*      */     
/*      */     public float setValue(float v) {
/*  617 */       float oldValue = Reference2FloatOpenHashMap.this.value[this.index];
/*  618 */       Reference2FloatOpenHashMap.this.value[this.index] = v;
/*  619 */       return oldValue;
/*      */     }
/*      */ 
/*      */     
/*      */     public ReferenceFloatPair<K> right(float v) {
/*  624 */       Reference2FloatOpenHashMap.this.value[this.index] = v;
/*  625 */       return this;
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
/*  636 */       return Float.valueOf(Reference2FloatOpenHashMap.this.value[this.index]);
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
/*  647 */       return Float.valueOf(setValue(v.floatValue()));
/*      */     }
/*      */ 
/*      */ 
/*      */     
/*      */     public boolean equals(Object o) {
/*  653 */       if (!(o instanceof Map.Entry)) return false; 
/*  654 */       Map.Entry<K, Float> e = (Map.Entry<K, Float>)o;
/*  655 */       return (Reference2FloatOpenHashMap.this.key[this.index] == e.getKey() && Float.floatToIntBits(Reference2FloatOpenHashMap.this.value[this.index]) == Float.floatToIntBits(((Float)e.getValue()).floatValue()));
/*      */     }
/*      */ 
/*      */     
/*      */     public int hashCode() {
/*  660 */       return System.identityHashCode(Reference2FloatOpenHashMap.this.key[this.index]) ^ HashCommon.float2int(Reference2FloatOpenHashMap.this.value[this.index]);
/*      */     }
/*      */ 
/*      */     
/*      */     public String toString() {
/*  665 */       return (new StringBuilder()).append(Reference2FloatOpenHashMap.this.key[this.index]).append("=>").append(Reference2FloatOpenHashMap.this.value[this.index]).toString();
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
/*  676 */     int pos = Reference2FloatOpenHashMap.this.n;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  682 */     int last = -1;
/*      */     
/*  684 */     int c = Reference2FloatOpenHashMap.this.size;
/*      */     
/*  686 */     boolean mustReturnNullKey = Reference2FloatOpenHashMap.this.containsNullKey;
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
/*  697 */       return (this.c != 0);
/*      */     }
/*      */     
/*      */     public int nextEntry() {
/*  701 */       if (!hasNext()) throw new NoSuchElementException(); 
/*  702 */       this.c--;
/*  703 */       if (this.mustReturnNullKey) {
/*  704 */         this.mustReturnNullKey = false;
/*  705 */         return this.last = Reference2FloatOpenHashMap.this.n;
/*      */       } 
/*  707 */       K[] key = Reference2FloatOpenHashMap.this.key;
/*      */       while (true) {
/*  709 */         if (--this.pos < 0) {
/*      */           
/*  711 */           this.last = Integer.MIN_VALUE;
/*  712 */           K k = this.wrapped.get(-this.pos - 1);
/*  713 */           int p = HashCommon.mix(System.identityHashCode(k)) & Reference2FloatOpenHashMap.this.mask;
/*  714 */           for (; k != key[p]; p = p + 1 & Reference2FloatOpenHashMap.this.mask);
/*  715 */           return p;
/*      */         } 
/*  717 */         if (key[this.pos] != null) return this.last = this.pos; 
/*      */       } 
/*      */     }
/*      */     
/*      */     public void forEachRemaining(ConsumerType action) {
/*  722 */       if (this.mustReturnNullKey) {
/*  723 */         this.mustReturnNullKey = false;
/*  724 */         acceptOnIndex(action, this.last = Reference2FloatOpenHashMap.this.n);
/*  725 */         this.c--;
/*      */       } 
/*  727 */       K[] key = Reference2FloatOpenHashMap.this.key;
/*  728 */       while (this.c != 0) {
/*  729 */         if (--this.pos < 0) {
/*      */           
/*  731 */           this.last = Integer.MIN_VALUE;
/*  732 */           K k = this.wrapped.get(-this.pos - 1);
/*  733 */           int p = HashCommon.mix(System.identityHashCode(k)) & Reference2FloatOpenHashMap.this.mask;
/*  734 */           for (; k != key[p]; p = p + 1 & Reference2FloatOpenHashMap.this.mask);
/*  735 */           acceptOnIndex(action, p);
/*  736 */           this.c--; continue;
/*  737 */         }  if (key[this.pos] != null) {
/*  738 */           acceptOnIndex(action, this.last = this.pos);
/*  739 */           this.c--;
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
/*  754 */       K[] key = Reference2FloatOpenHashMap.this.key; while (true) {
/*      */         K curr; int last;
/*  756 */         pos = (last = pos) + 1 & Reference2FloatOpenHashMap.this.mask;
/*      */         while (true) {
/*  758 */           if ((curr = key[pos]) == null) {
/*  759 */             key[last] = null;
/*      */             return;
/*      */           } 
/*  762 */           int slot = HashCommon.mix(System.identityHashCode(curr)) & Reference2FloatOpenHashMap.this.mask;
/*  763 */           if ((last <= pos) ? (last >= slot || slot > pos) : (last >= slot && slot > pos))
/*  764 */             break;  pos = pos + 1 & Reference2FloatOpenHashMap.this.mask;
/*      */         } 
/*  766 */         if (pos < last) {
/*  767 */           if (this.wrapped == null) this.wrapped = new ReferenceArrayList<>(2); 
/*  768 */           this.wrapped.add(key[pos]);
/*      */         } 
/*  770 */         key[last] = curr;
/*  771 */         Reference2FloatOpenHashMap.this.value[last] = Reference2FloatOpenHashMap.this.value[pos];
/*      */       } 
/*      */     }
/*      */     
/*      */     public void remove() {
/*  776 */       if (this.last == -1) throw new IllegalStateException(); 
/*  777 */       if (this.last == Reference2FloatOpenHashMap.this.n)
/*  778 */       { Reference2FloatOpenHashMap.this.containsNullKey = false;
/*  779 */         Reference2FloatOpenHashMap.this.key[Reference2FloatOpenHashMap.this.n] = null; }
/*  780 */       else if (this.pos >= 0) { shiftKeys(this.last); }
/*      */       else
/*      */       
/*  783 */       { Reference2FloatOpenHashMap.this.removeFloat(this.wrapped.set(-this.pos - 1, null));
/*  784 */         this.last = -1;
/*      */         return; }
/*      */       
/*  787 */       Reference2FloatOpenHashMap.this.size--;
/*  788 */       this.last = -1;
/*      */     }
/*      */ 
/*      */     
/*      */     public int skip(int n) {
/*  793 */       int i = n;
/*  794 */       for (; i-- != 0 && hasNext(); nextEntry());
/*  795 */       return n - i - 1;
/*      */     }
/*      */     
/*      */     private MapIterator() {}
/*      */     
/*      */     abstract void acceptOnIndex(ConsumerType param1ConsumerType, int param1Int);
/*      */   }
/*      */   
/*      */   private final class EntryIterator extends MapIterator<Consumer<? super Reference2FloatMap.Entry<K>>> implements ObjectIterator<Reference2FloatMap.Entry<K>> { public Reference2FloatOpenHashMap<K>.MapEntry next() {
/*  804 */       return this.entry = new Reference2FloatOpenHashMap.MapEntry(nextEntry());
/*      */     }
/*      */     private Reference2FloatOpenHashMap<K>.MapEntry entry;
/*      */     private EntryIterator() {}
/*      */     
/*      */     final void acceptOnIndex(Consumer<? super Reference2FloatMap.Entry<K>> action, int index) {
/*  810 */       action.accept(this.entry = new Reference2FloatOpenHashMap.MapEntry(index));
/*      */     }
/*      */ 
/*      */     
/*      */     public void remove() {
/*  815 */       super.remove();
/*  816 */       this.entry.index = -1;
/*      */     } }
/*      */   
/*      */   private final class FastEntryIterator extends MapIterator<Consumer<? super Reference2FloatMap.Entry<K>>> implements ObjectIterator<Reference2FloatMap.Entry<K>> {
/*      */     private FastEntryIterator() {
/*  821 */       this.entry = new Reference2FloatOpenHashMap.MapEntry();
/*      */     }
/*      */     private final Reference2FloatOpenHashMap<K>.MapEntry entry;
/*      */     public Reference2FloatOpenHashMap<K>.MapEntry next() {
/*  825 */       this.entry.index = nextEntry();
/*  826 */       return this.entry;
/*      */     }
/*      */ 
/*      */ 
/*      */     
/*      */     final void acceptOnIndex(Consumer<? super Reference2FloatMap.Entry<K>> action, int index) {
/*  832 */       this.entry.index = index;
/*  833 */       action.accept(this.entry);
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private abstract class MapSpliterator<ConsumerType, SplitType extends MapSpliterator<ConsumerType, SplitType>>
/*      */   {
/*  842 */     int pos = 0;
/*      */     
/*  844 */     int max = Reference2FloatOpenHashMap.this.n;
/*      */     
/*  846 */     int c = 0;
/*      */     
/*  848 */     boolean mustReturnNull = Reference2FloatOpenHashMap.this.containsNullKey;
/*      */     
/*      */     boolean hasSplit = false;
/*      */     
/*      */     MapSpliterator() {}
/*      */     
/*      */     MapSpliterator(int pos, int max, boolean mustReturnNull, boolean hasSplit) {
/*  855 */       this.pos = pos;
/*  856 */       this.max = max;
/*  857 */       this.mustReturnNull = mustReturnNull;
/*  858 */       this.hasSplit = hasSplit;
/*      */     }
/*      */     
/*      */     abstract void acceptOnIndex(ConsumerType param1ConsumerType, int param1Int);
/*      */     
/*      */     abstract SplitType makeForSplit(int param1Int1, int param1Int2, boolean param1Boolean);
/*      */     
/*      */     public boolean tryAdvance(ConsumerType action) {
/*  866 */       if (this.mustReturnNull) {
/*  867 */         this.mustReturnNull = false;
/*  868 */         this.c++;
/*  869 */         acceptOnIndex(action, Reference2FloatOpenHashMap.this.n);
/*  870 */         return true;
/*      */       } 
/*  872 */       K[] key = Reference2FloatOpenHashMap.this.key;
/*  873 */       while (this.pos < this.max) {
/*  874 */         if (key[this.pos] != null) {
/*  875 */           this.c++;
/*  876 */           acceptOnIndex(action, this.pos++);
/*  877 */           return true;
/*      */         } 
/*  879 */         this.pos++;
/*      */       } 
/*  881 */       return false;
/*      */     }
/*      */     
/*      */     public void forEachRemaining(ConsumerType action) {
/*  885 */       if (this.mustReturnNull) {
/*  886 */         this.mustReturnNull = false;
/*  887 */         this.c++;
/*  888 */         acceptOnIndex(action, Reference2FloatOpenHashMap.this.n);
/*      */       } 
/*  890 */       K[] key = Reference2FloatOpenHashMap.this.key;
/*  891 */       while (this.pos < this.max) {
/*  892 */         if (key[this.pos] != null) {
/*  893 */           acceptOnIndex(action, this.pos);
/*  894 */           this.c++;
/*      */         } 
/*  896 */         this.pos++;
/*      */       } 
/*      */     }
/*      */     
/*      */     public long estimateSize() {
/*  901 */       if (!this.hasSplit)
/*      */       {
/*  903 */         return (Reference2FloatOpenHashMap.this.size - this.c);
/*      */       }
/*      */ 
/*      */ 
/*      */       
/*  908 */       return Math.min((Reference2FloatOpenHashMap.this.size - this.c), (long)(Reference2FloatOpenHashMap.this.realSize() / Reference2FloatOpenHashMap.this.n * (this.max - this.pos)) + (this.mustReturnNull ? 1L : 0L));
/*      */     }
/*      */ 
/*      */     
/*      */     public SplitType trySplit() {
/*  913 */       if (this.pos >= this.max - 1) return null; 
/*  914 */       int retLen = this.max - this.pos >> 1;
/*  915 */       if (retLen <= 1) return null; 
/*  916 */       int myNewPos = this.pos + retLen;
/*  917 */       int retPos = this.pos;
/*  918 */       int retMax = myNewPos;
/*      */ 
/*      */ 
/*      */       
/*  922 */       SplitType split = makeForSplit(retPos, retMax, this.mustReturnNull);
/*  923 */       this.pos = myNewPos;
/*  924 */       this.mustReturnNull = false;
/*  925 */       this.hasSplit = true;
/*  926 */       return split;
/*      */     }
/*      */     
/*      */     public long skip(long n) {
/*  930 */       if (n < 0L) throw new IllegalArgumentException("Argument must be nonnegative: " + n); 
/*  931 */       if (n == 0L) return 0L; 
/*  932 */       long skipped = 0L;
/*  933 */       if (this.mustReturnNull) {
/*  934 */         this.mustReturnNull = false;
/*  935 */         skipped++;
/*  936 */         n--;
/*      */       } 
/*  938 */       K[] key = Reference2FloatOpenHashMap.this.key;
/*  939 */       while (this.pos < this.max && n > 0L) {
/*  940 */         if (key[this.pos++] != null) {
/*  941 */           skipped++;
/*  942 */           n--;
/*      */         } 
/*      */       } 
/*  945 */       return skipped;
/*      */     }
/*      */   }
/*      */   
/*      */   private final class EntrySpliterator
/*      */     extends MapSpliterator<Consumer<? super Reference2FloatMap.Entry<K>>, EntrySpliterator> implements ObjectSpliterator<Reference2FloatMap.Entry<K>> {
/*      */     private static final int POST_SPLIT_CHARACTERISTICS = 1;
/*      */     
/*      */     EntrySpliterator() {}
/*      */     
/*      */     EntrySpliterator(int pos, int max, boolean mustReturnNull, boolean hasSplit) {
/*  956 */       super(pos, max, mustReturnNull, hasSplit);
/*      */     }
/*      */ 
/*      */     
/*      */     public int characteristics() {
/*  961 */       return this.hasSplit ? 1 : 65;
/*      */     }
/*      */ 
/*      */     
/*      */     final void acceptOnIndex(Consumer<? super Reference2FloatMap.Entry<K>> action, int index) {
/*  966 */       action.accept(new Reference2FloatOpenHashMap.MapEntry(index));
/*      */     }
/*      */ 
/*      */     
/*      */     final EntrySpliterator makeForSplit(int pos, int max, boolean mustReturnNull) {
/*  971 */       return new EntrySpliterator(pos, max, mustReturnNull, true);
/*      */     } }
/*      */   
/*      */   private final class MapEntrySet extends AbstractObjectSet<Reference2FloatMap.Entry<K>> implements Reference2FloatMap.FastEntrySet<K> {
/*      */     private MapEntrySet() {}
/*      */     
/*      */     public ObjectIterator<Reference2FloatMap.Entry<K>> iterator() {
/*  978 */       return new Reference2FloatOpenHashMap.EntryIterator();
/*      */     }
/*      */ 
/*      */     
/*      */     public ObjectIterator<Reference2FloatMap.Entry<K>> fastIterator() {
/*  983 */       return new Reference2FloatOpenHashMap.FastEntryIterator();
/*      */     }
/*      */ 
/*      */     
/*      */     public ObjectSpliterator<Reference2FloatMap.Entry<K>> spliterator() {
/*  988 */       return new Reference2FloatOpenHashMap.EntrySpliterator();
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public boolean contains(Object o) {
/*  995 */       if (!(o instanceof Map.Entry)) return false; 
/*  996 */       Map.Entry<?, ?> e = (Map.Entry<?, ?>)o;
/*  997 */       if (e.getValue() == null || !(e.getValue() instanceof Float)) return false; 
/*  998 */       K k = (K)e.getKey();
/*  999 */       float v = ((Float)e.getValue()).floatValue();
/* 1000 */       if (k == null) return (Reference2FloatOpenHashMap.this.containsNullKey && Float.floatToIntBits(Reference2FloatOpenHashMap.this.value[Reference2FloatOpenHashMap.this.n]) == Float.floatToIntBits(v));
/*      */       
/* 1002 */       K[] key = Reference2FloatOpenHashMap.this.key;
/*      */       K curr;
/*      */       int pos;
/* 1005 */       if ((curr = key[pos = HashCommon.mix(System.identityHashCode(k)) & Reference2FloatOpenHashMap.this.mask]) == null) return false; 
/* 1006 */       if (k == curr) return (Float.floatToIntBits(Reference2FloatOpenHashMap.this.value[pos]) == Float.floatToIntBits(v));
/*      */       
/*      */       while (true) {
/* 1009 */         if ((curr = key[pos = pos + 1 & Reference2FloatOpenHashMap.this.mask]) == null) return false; 
/* 1010 */         if (k == curr) return (Float.floatToIntBits(Reference2FloatOpenHashMap.this.value[pos]) == Float.floatToIntBits(v));
/*      */       
/*      */       } 
/*      */     }
/*      */ 
/*      */     
/*      */     public boolean remove(Object o) {
/* 1017 */       if (!(o instanceof Map.Entry)) return false; 
/* 1018 */       Map.Entry<?, ?> e = (Map.Entry<?, ?>)o;
/* 1019 */       if (e.getValue() == null || !(e.getValue() instanceof Float)) return false; 
/* 1020 */       K k = (K)e.getKey();
/* 1021 */       float v = ((Float)e.getValue()).floatValue();
/* 1022 */       if (k == null) {
/* 1023 */         if (Reference2FloatOpenHashMap.this.containsNullKey && Float.floatToIntBits(Reference2FloatOpenHashMap.this.value[Reference2FloatOpenHashMap.this.n]) == Float.floatToIntBits(v)) {
/* 1024 */           Reference2FloatOpenHashMap.this.removeNullEntry();
/* 1025 */           return true;
/*      */         } 
/* 1027 */         return false;
/*      */       } 
/*      */       
/* 1030 */       K[] key = Reference2FloatOpenHashMap.this.key;
/*      */       K curr;
/*      */       int pos;
/* 1033 */       if ((curr = key[pos = HashCommon.mix(System.identityHashCode(k)) & Reference2FloatOpenHashMap.this.mask]) == null) return false; 
/* 1034 */       if (curr == k) {
/* 1035 */         if (Float.floatToIntBits(Reference2FloatOpenHashMap.this.value[pos]) == Float.floatToIntBits(v)) {
/* 1036 */           Reference2FloatOpenHashMap.this.removeEntry(pos);
/* 1037 */           return true;
/*      */         } 
/* 1039 */         return false;
/*      */       } 
/*      */       while (true) {
/* 1042 */         if ((curr = key[pos = pos + 1 & Reference2FloatOpenHashMap.this.mask]) == null) return false; 
/* 1043 */         if (curr == k && 
/* 1044 */           Float.floatToIntBits(Reference2FloatOpenHashMap.this.value[pos]) == Float.floatToIntBits(v)) {
/* 1045 */           Reference2FloatOpenHashMap.this.removeEntry(pos);
/* 1046 */           return true;
/*      */         } 
/*      */       } 
/*      */     }
/*      */ 
/*      */ 
/*      */     
/*      */     public int size() {
/* 1054 */       return Reference2FloatOpenHashMap.this.size;
/*      */     }
/*      */ 
/*      */     
/*      */     public void clear() {
/* 1059 */       Reference2FloatOpenHashMap.this.clear();
/*      */     }
/*      */ 
/*      */ 
/*      */     
/*      */     public void forEach(Consumer<? super Reference2FloatMap.Entry<K>> consumer) {
/* 1065 */       if (Reference2FloatOpenHashMap.this.containsNullKey) consumer.accept(new Reference2FloatOpenHashMap.MapEntry(Reference2FloatOpenHashMap.this.n)); 
/* 1066 */       for (int pos = Reference2FloatOpenHashMap.this.n; pos-- != 0;) { if (Reference2FloatOpenHashMap.this.key[pos] != null) consumer.accept(new Reference2FloatOpenHashMap.MapEntry(pos));
/*      */          }
/*      */     
/*      */     }
/*      */     
/*      */     public void fastForEach(Consumer<? super Reference2FloatMap.Entry<K>> consumer) {
/* 1072 */       Reference2FloatOpenHashMap<K>.MapEntry entry = new Reference2FloatOpenHashMap.MapEntry();
/* 1073 */       if (Reference2FloatOpenHashMap.this.containsNullKey) {
/* 1074 */         entry.index = Reference2FloatOpenHashMap.this.n;
/* 1075 */         consumer.accept(entry);
/*      */       } 
/* 1077 */       for (int pos = Reference2FloatOpenHashMap.this.n; pos-- != 0;) { if (Reference2FloatOpenHashMap.this.key[pos] != null) {
/* 1078 */           entry.index = pos;
/* 1079 */           consumer.accept(entry);
/*      */         }  }
/*      */     
/*      */     }
/*      */   }
/*      */   
/*      */   public Reference2FloatMap.FastEntrySet<K> reference2FloatEntrySet() {
/* 1086 */     if (this.entries == null) this.entries = new MapEntrySet(); 
/* 1087 */     return this.entries;
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
/* 1108 */       action.accept(Reference2FloatOpenHashMap.this.key[index]);
/*      */     }
/*      */ 
/*      */     
/*      */     public K next() {
/* 1113 */       return Reference2FloatOpenHashMap.this.key[nextEntry()];
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
/* 1124 */       super(pos, max, mustReturnNull, hasSplit);
/*      */     }
/*      */ 
/*      */     
/*      */     public int characteristics() {
/* 1129 */       return this.hasSplit ? 1 : 65;
/*      */     }
/*      */ 
/*      */     
/*      */     final void acceptOnIndex(Consumer<? super K> action, int index) {
/* 1134 */       action.accept(Reference2FloatOpenHashMap.this.key[index]);
/*      */     }
/*      */ 
/*      */     
/*      */     final KeySpliterator makeForSplit(int pos, int max, boolean mustReturnNull) {
/* 1139 */       return new KeySpliterator(pos, max, mustReturnNull, true);
/*      */     } }
/*      */   
/*      */   private final class KeySet extends AbstractReferenceSet<K> {
/*      */     private KeySet() {}
/*      */     
/*      */     public ObjectIterator<K> iterator() {
/* 1146 */       return new Reference2FloatOpenHashMap.KeyIterator();
/*      */     }
/*      */ 
/*      */     
/*      */     public ObjectSpliterator<K> spliterator() {
/* 1151 */       return new Reference2FloatOpenHashMap.KeySpliterator();
/*      */     }
/*      */ 
/*      */ 
/*      */     
/*      */     public void forEach(Consumer<? super K> consumer) {
/* 1157 */       if (Reference2FloatOpenHashMap.this.containsNullKey) consumer.accept(Reference2FloatOpenHashMap.this.key[Reference2FloatOpenHashMap.this.n]); 
/* 1158 */       for (int pos = Reference2FloatOpenHashMap.this.n; pos-- != 0; ) {
/* 1159 */         K k = Reference2FloatOpenHashMap.this.key[pos];
/* 1160 */         if (k != null) consumer.accept(k);
/*      */       
/*      */       } 
/*      */     }
/*      */     
/*      */     public int size() {
/* 1166 */       return Reference2FloatOpenHashMap.this.size;
/*      */     }
/*      */ 
/*      */     
/*      */     public boolean contains(Object k) {
/* 1171 */       return Reference2FloatOpenHashMap.this.containsKey(k);
/*      */     }
/*      */ 
/*      */     
/*      */     public boolean remove(Object k) {
/* 1176 */       int oldSize = Reference2FloatOpenHashMap.this.size;
/* 1177 */       Reference2FloatOpenHashMap.this.removeFloat(k);
/* 1178 */       return (Reference2FloatOpenHashMap.this.size != oldSize);
/*      */     }
/*      */ 
/*      */     
/*      */     public void clear() {
/* 1183 */       Reference2FloatOpenHashMap.this.clear();
/*      */     }
/*      */   }
/*      */ 
/*      */   
/*      */   public ReferenceSet<K> keySet() {
/* 1189 */     if (this.keys == null) this.keys = new KeySet(); 
/* 1190 */     return this.keys;
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
/* 1211 */       action.accept(Reference2FloatOpenHashMap.this.value[index]);
/*      */     }
/*      */ 
/*      */     
/*      */     public float nextFloat() {
/* 1216 */       return Reference2FloatOpenHashMap.this.value[nextEntry()];
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
/* 1227 */       super(pos, max, mustReturnNull, hasSplit);
/*      */     }
/*      */ 
/*      */     
/*      */     public int characteristics() {
/* 1232 */       return this.hasSplit ? 256 : 320;
/*      */     }
/*      */ 
/*      */     
/*      */     final void acceptOnIndex(FloatConsumer action, int index) {
/* 1237 */       action.accept(Reference2FloatOpenHashMap.this.value[index]);
/*      */     }
/*      */ 
/*      */     
/*      */     final ValueSpliterator makeForSplit(int pos, int max, boolean mustReturnNull) {
/* 1242 */       return new ValueSpliterator(pos, max, mustReturnNull, true);
/*      */     }
/*      */   }
/*      */ 
/*      */   
/*      */   public FloatCollection values() {
/* 1248 */     if (this.values == null) this.values = (FloatCollection)new AbstractFloatCollection()
/*      */         {
/*      */           public FloatIterator iterator() {
/* 1251 */             return new Reference2FloatOpenHashMap.ValueIterator();
/*      */           }
/*      */ 
/*      */           
/*      */           public FloatSpliterator spliterator() {
/* 1256 */             return new Reference2FloatOpenHashMap.ValueSpliterator();
/*      */           }
/*      */ 
/*      */ 
/*      */           
/*      */           public void forEach(FloatConsumer consumer) {
/* 1262 */             if (Reference2FloatOpenHashMap.this.containsNullKey) consumer.accept(Reference2FloatOpenHashMap.this.value[Reference2FloatOpenHashMap.this.n]); 
/* 1263 */             for (int pos = Reference2FloatOpenHashMap.this.n; pos-- != 0;) { if (Reference2FloatOpenHashMap.this.key[pos] != null) consumer.accept(Reference2FloatOpenHashMap.this.value[pos]);  }
/*      */           
/*      */           }
/*      */           
/*      */           public int size() {
/* 1268 */             return Reference2FloatOpenHashMap.this.size;
/*      */           }
/*      */ 
/*      */           
/*      */           public boolean contains(float v) {
/* 1273 */             return Reference2FloatOpenHashMap.this.containsValue(v);
/*      */           }
/*      */ 
/*      */           
/*      */           public void clear() {
/* 1278 */             Reference2FloatOpenHashMap.this.clear();
/*      */           }
/*      */         }; 
/* 1281 */     return this.values;
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
/* 1298 */     return trim(this.size);
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
/* 1320 */     int l = HashCommon.nextPowerOfTwo((int)Math.ceil((n / this.f)));
/* 1321 */     if (l >= this.n || this.size > HashCommon.maxFill(l, this.f)) return true; 
/*      */     try {
/* 1323 */       rehash(l);
/* 1324 */     } catch (OutOfMemoryError cantDoIt) {
/* 1325 */       return false;
/*      */     } 
/* 1327 */     return true;
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
/* 1342 */     K[] key = this.key;
/* 1343 */     float[] value = this.value;
/* 1344 */     int mask = newN - 1;
/* 1345 */     K[] newKey = (K[])new Object[newN + 1];
/* 1346 */     float[] newValue = new float[newN + 1];
/* 1347 */     int i = this.n;
/* 1348 */     for (int j = realSize(); j-- != 0; ) {
/* 1349 */       while (key[--i] == null); int pos;
/* 1350 */       if (newKey[pos = HashCommon.mix(System.identityHashCode(key[i])) & mask] != null) while (newKey[pos = pos + 1 & mask] != null); 
/* 1351 */       newKey[pos] = key[i];
/* 1352 */       newValue[pos] = value[i];
/*      */     } 
/* 1354 */     newValue[newN] = value[this.n];
/* 1355 */     this.n = newN;
/* 1356 */     this.mask = mask;
/* 1357 */     this.maxFill = HashCommon.maxFill(this.n, this.f);
/* 1358 */     this.key = newKey;
/* 1359 */     this.value = newValue;
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
/*      */   public Reference2FloatOpenHashMap<K> clone() {
/*      */     Reference2FloatOpenHashMap<K> c;
/*      */     try {
/* 1376 */       c = (Reference2FloatOpenHashMap<K>)super.clone();
/* 1377 */     } catch (CloneNotSupportedException cantHappen) {
/* 1378 */       throw new InternalError();
/*      */     } 
/* 1380 */     c.keys = null;
/* 1381 */     c.values = null;
/* 1382 */     c.entries = null;
/* 1383 */     c.containsNullKey = this.containsNullKey;
/* 1384 */     c.key = (K[])this.key.clone();
/* 1385 */     c.value = (float[])this.value.clone();
/* 1386 */     return c;
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
/* 1400 */     int h = 0;
/* 1401 */     for (int j = realSize(), i = 0, t = 0; j-- != 0; ) {
/* 1402 */       for (; this.key[i] == null; i++);
/* 1403 */       if (this != this.key[i]) t = System.identityHashCode(this.key[i]); 
/* 1404 */       t ^= HashCommon.float2int(this.value[i]);
/* 1405 */       h += t;
/* 1406 */       i++;
/*      */     } 
/*      */     
/* 1409 */     if (this.containsNullKey) h += HashCommon.float2int(this.value[this.n]); 
/* 1410 */     return h;
/*      */   }
/*      */   
/*      */   private void writeObject(ObjectOutputStream s) throws IOException {
/* 1414 */     K[] key = this.key;
/* 1415 */     float[] value = this.value;
/* 1416 */     EntryIterator i = new EntryIterator();
/* 1417 */     s.defaultWriteObject();
/* 1418 */     for (int j = this.size; j-- != 0; ) {
/* 1419 */       int e = i.nextEntry();
/* 1420 */       s.writeObject(key[e]);
/* 1421 */       s.writeFloat(value[e]);
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   private void readObject(ObjectInputStream s) throws IOException, ClassNotFoundException {
/* 1427 */     s.defaultReadObject();
/* 1428 */     this.n = HashCommon.arraySize(this.size, this.f);
/* 1429 */     this.maxFill = HashCommon.maxFill(this.n, this.f);
/* 1430 */     this.mask = this.n - 1;
/* 1431 */     K[] key = this.key = (K[])new Object[this.n + 1];
/* 1432 */     float[] value = this.value = new float[this.n + 1];
/*      */ 
/*      */     
/* 1435 */     for (int i = this.size; i-- != 0; ) {
/* 1436 */       int pos; K k = (K)s.readObject();
/* 1437 */       float v = s.readFloat();
/* 1438 */       if (k == null) {
/* 1439 */         pos = this.n;
/* 1440 */         this.containsNullKey = true;
/*      */       } else {
/* 1442 */         pos = HashCommon.mix(System.identityHashCode(k)) & this.mask;
/* 1443 */         for (; key[pos] != null; pos = pos + 1 & this.mask);
/*      */       } 
/* 1445 */       key[pos] = k;
/* 1446 */       value[pos] = v;
/*      */     } 
/*      */   }
/*      */   
/*      */   private void checkTable() {}
/*      */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\i\\unimi\dsi\fastutil\objects\Reference2FloatOpenHashMap.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */