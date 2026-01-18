/*      */ package it.unimi.dsi.fastutil.objects;
/*      */ 
/*      */ import it.unimi.dsi.fastutil.Hash;
/*      */ import it.unimi.dsi.fastutil.HashCommon;
/*      */ import it.unimi.dsi.fastutil.doubles.AbstractDoubleCollection;
/*      */ import it.unimi.dsi.fastutil.doubles.DoubleCollection;
/*      */ import it.unimi.dsi.fastutil.doubles.DoubleIterator;
/*      */ import it.unimi.dsi.fastutil.doubles.DoubleSpliterator;
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
/*      */ import java.util.function.DoubleConsumer;
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
/*      */ public class Object2DoubleOpenCustomHashMap<K>
/*      */   extends AbstractObject2DoubleMap<K>
/*      */   implements Serializable, Cloneable, Hash
/*      */ {
/*      */   private static final long serialVersionUID = 0L;
/*      */   private static final boolean ASSERTS = false;
/*      */   protected transient K[] key;
/*      */   protected transient double[] value;
/*      */   protected transient int mask;
/*      */   protected transient boolean containsNullKey;
/*      */   protected Hash.Strategy<? super K> strategy;
/*      */   protected transient int n;
/*      */   protected transient int maxFill;
/*      */   protected final transient int minN;
/*      */   protected int size;
/*      */   protected final float f;
/*      */   protected transient Object2DoubleMap.FastEntrySet<K> entries;
/*      */   protected transient ObjectSet<K> keys;
/*      */   protected transient DoubleCollection values;
/*      */   
/*      */   public Object2DoubleOpenCustomHashMap(int expected, float f, Hash.Strategy<? super K> strategy) {
/*   99 */     this.strategy = strategy;
/*  100 */     if (f <= 0.0F || f >= 1.0F) throw new IllegalArgumentException("Load factor must be greater than 0 and smaller than 1"); 
/*  101 */     if (expected < 0) throw new IllegalArgumentException("The expected number of elements must be nonnegative"); 
/*  102 */     this.f = f;
/*  103 */     this.minN = this.n = HashCommon.arraySize(expected, f);
/*  104 */     this.mask = this.n - 1;
/*  105 */     this.maxFill = HashCommon.maxFill(this.n, f);
/*  106 */     this.key = (K[])new Object[this.n + 1];
/*  107 */     this.value = new double[this.n + 1];
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Object2DoubleOpenCustomHashMap(int expected, Hash.Strategy<? super K> strategy) {
/*  117 */     this(expected, 0.75F, strategy);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Object2DoubleOpenCustomHashMap(Hash.Strategy<? super K> strategy) {
/*  127 */     this(16, 0.75F, strategy);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Object2DoubleOpenCustomHashMap(Map<? extends K, ? extends Double> m, float f, Hash.Strategy<? super K> strategy) {
/*  138 */     this(m.size(), f, strategy);
/*  139 */     putAll(m);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Object2DoubleOpenCustomHashMap(Map<? extends K, ? extends Double> m, Hash.Strategy<? super K> strategy) {
/*  149 */     this(m, 0.75F, strategy);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Object2DoubleOpenCustomHashMap(Object2DoubleMap<K> m, float f, Hash.Strategy<? super K> strategy) {
/*  160 */     this(m.size(), f, strategy);
/*  161 */     putAll(m);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Object2DoubleOpenCustomHashMap(Object2DoubleMap<K> m, Hash.Strategy<? super K> strategy) {
/*  172 */     this(m, 0.75F, strategy);
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
/*      */   public Object2DoubleOpenCustomHashMap(K[] k, double[] v, float f, Hash.Strategy<? super K> strategy) {
/*  185 */     this(k.length, f, strategy);
/*  186 */     if (k.length != v.length) throw new IllegalArgumentException("The key array and the value array have different lengths (" + k.length + " and " + v.length + ")"); 
/*  187 */     for (int i = 0; i < k.length; ) { put(k[i], v[i]); i++; }
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
/*      */   public Object2DoubleOpenCustomHashMap(K[] k, double[] v, Hash.Strategy<? super K> strategy) {
/*  200 */     this(k, v, 0.75F, strategy);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Hash.Strategy<? super K> strategy() {
/*  209 */     return this.strategy;
/*      */   }
/*      */   
/*      */   private int realSize() {
/*  213 */     return this.containsNullKey ? (this.size - 1) : this.size;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void ensureCapacity(int capacity) {
/*  223 */     int needed = HashCommon.arraySize(capacity, this.f);
/*  224 */     if (needed > this.n) rehash(needed); 
/*      */   }
/*      */   
/*      */   private void tryCapacity(long capacity) {
/*  228 */     int needed = (int)Math.min(1073741824L, Math.max(2L, HashCommon.nextPowerOfTwo((long)Math.ceil(((float)capacity / this.f)))));
/*  229 */     if (needed > this.n) rehash(needed); 
/*      */   }
/*      */   
/*      */   private double removeEntry(int pos) {
/*  233 */     double oldValue = this.value[pos];
/*  234 */     this.size--;
/*  235 */     shiftKeys(pos);
/*  236 */     if (this.n > this.minN && this.size < this.maxFill / 4 && this.n > 16) rehash(this.n / 2); 
/*  237 */     return oldValue;
/*      */   }
/*      */   
/*      */   private double removeNullEntry() {
/*  241 */     this.containsNullKey = false;
/*  242 */     this.key[this.n] = null;
/*  243 */     double oldValue = this.value[this.n];
/*  244 */     this.size--;
/*  245 */     if (this.n > this.minN && this.size < this.maxFill / 4 && this.n > 16) rehash(this.n / 2); 
/*  246 */     return oldValue;
/*      */   }
/*      */ 
/*      */   
/*      */   public void putAll(Map<? extends K, ? extends Double> m) {
/*  251 */     if (this.f <= 0.5D) { ensureCapacity(m.size()); }
/*  252 */     else { tryCapacity((size() + m.size())); }
/*      */     
/*  254 */     super.putAll(m);
/*      */   }
/*      */ 
/*      */   
/*      */   private int find(K k) {
/*  259 */     if (this.strategy.equals(k, null)) return this.containsNullKey ? this.n : -(this.n + 1);
/*      */     
/*  261 */     K[] key = this.key;
/*      */     K curr;
/*      */     int pos;
/*  264 */     if ((curr = key[pos = HashCommon.mix(this.strategy.hashCode(k)) & this.mask]) == null) return -(pos + 1); 
/*  265 */     if (this.strategy.equals(k, curr)) return pos;
/*      */     
/*      */     while (true) {
/*  268 */       if ((curr = key[pos = pos + 1 & this.mask]) == null) return -(pos + 1); 
/*  269 */       if (this.strategy.equals(k, curr)) return pos; 
/*      */     } 
/*      */   }
/*      */   
/*      */   private void insert(int pos, K k, double v) {
/*  274 */     if (pos == this.n) this.containsNullKey = true; 
/*  275 */     this.key[pos] = k;
/*  276 */     this.value[pos] = v;
/*  277 */     if (this.size++ >= this.maxFill) rehash(HashCommon.arraySize(this.size + 1, this.f));
/*      */   
/*      */   }
/*      */ 
/*      */   
/*      */   public double put(K k, double v) {
/*  283 */     int pos = find(k);
/*  284 */     if (pos < 0) {
/*  285 */       insert(-pos - 1, k, v);
/*  286 */       return this.defRetValue;
/*      */     } 
/*  288 */     double oldValue = this.value[pos];
/*  289 */     this.value[pos] = v;
/*  290 */     return oldValue;
/*      */   }
/*      */   
/*      */   private double addToValue(int pos, double incr) {
/*  294 */     double oldValue = this.value[pos];
/*  295 */     this.value[pos] = oldValue + incr;
/*  296 */     return oldValue;
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
/*      */   public double addTo(K k, double incr) {
/*      */     int pos;
/*  314 */     if (this.strategy.equals(k, null)) {
/*  315 */       if (this.containsNullKey) return addToValue(this.n, incr); 
/*  316 */       pos = this.n;
/*  317 */       this.containsNullKey = true;
/*      */     } else {
/*      */       
/*  320 */       K[] key = this.key;
/*      */       K curr;
/*  322 */       if ((curr = key[pos = HashCommon.mix(this.strategy.hashCode(k)) & this.mask]) != null) {
/*  323 */         if (this.strategy.equals(curr, k)) return addToValue(pos, incr); 
/*  324 */         while ((curr = key[pos = pos + 1 & this.mask]) != null) { if (this.strategy.equals(curr, k)) return addToValue(pos, incr);  }
/*      */       
/*      */       } 
/*  327 */     }  this.key[pos] = k;
/*  328 */     this.value[pos] = this.defRetValue + incr;
/*  329 */     if (this.size++ >= this.maxFill) rehash(HashCommon.arraySize(this.size + 1, this.f));
/*      */     
/*  331 */     return this.defRetValue;
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
/*  344 */     K[] key = this.key; while (true) {
/*      */       K curr; int last;
/*  346 */       pos = (last = pos) + 1 & this.mask;
/*      */       while (true) {
/*  348 */         if ((curr = key[pos]) == null) {
/*  349 */           key[last] = null;
/*      */           return;
/*      */         } 
/*  352 */         int slot = HashCommon.mix(this.strategy.hashCode(curr)) & this.mask;
/*  353 */         if ((last <= pos) ? (last >= slot || slot > pos) : (last >= slot && slot > pos))
/*  354 */           break;  pos = pos + 1 & this.mask;
/*      */       } 
/*  356 */       key[last] = curr;
/*  357 */       this.value[last] = this.value[pos];
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public double removeDouble(Object k) {
/*  364 */     if (this.strategy.equals(k, null)) {
/*  365 */       if (this.containsNullKey) return removeNullEntry(); 
/*  366 */       return this.defRetValue;
/*      */     } 
/*      */     
/*  369 */     K[] key = this.key;
/*      */     K curr;
/*      */     int pos;
/*  372 */     if ((curr = key[pos = HashCommon.mix(this.strategy.hashCode(k)) & this.mask]) == null) return this.defRetValue; 
/*  373 */     if (this.strategy.equals(k, curr)) return removeEntry(pos); 
/*      */     while (true) {
/*  375 */       if ((curr = key[pos = pos + 1 & this.mask]) == null) return this.defRetValue; 
/*  376 */       if (this.strategy.equals(k, curr)) return removeEntry(pos);
/*      */     
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   public double getDouble(Object k) {
/*  383 */     if (this.strategy.equals(k, null)) return this.containsNullKey ? this.value[this.n] : this.defRetValue;
/*      */     
/*  385 */     K[] key = this.key;
/*      */     K curr;
/*      */     int pos;
/*  388 */     if ((curr = key[pos = HashCommon.mix(this.strategy.hashCode(k)) & this.mask]) == null) return this.defRetValue; 
/*  389 */     if (this.strategy.equals(k, curr)) return this.value[pos];
/*      */     
/*      */     while (true) {
/*  392 */       if ((curr = key[pos = pos + 1 & this.mask]) == null) return this.defRetValue; 
/*  393 */       if (this.strategy.equals(k, curr)) return this.value[pos];
/*      */     
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   public boolean containsKey(Object k) {
/*  400 */     if (this.strategy.equals(k, null)) return this.containsNullKey;
/*      */     
/*  402 */     K[] key = this.key;
/*      */     K curr;
/*      */     int pos;
/*  405 */     if ((curr = key[pos = HashCommon.mix(this.strategy.hashCode(k)) & this.mask]) == null) return false; 
/*  406 */     if (this.strategy.equals(k, curr)) return true;
/*      */     
/*      */     while (true) {
/*  409 */       if ((curr = key[pos = pos + 1 & this.mask]) == null) return false; 
/*  410 */       if (this.strategy.equals(k, curr)) return true;
/*      */     
/*      */     } 
/*      */   }
/*      */   
/*      */   public boolean containsValue(double v) {
/*  416 */     double[] value = this.value;
/*  417 */     K[] key = this.key;
/*  418 */     if (this.containsNullKey && Double.doubleToLongBits(value[this.n]) == Double.doubleToLongBits(v)) return true; 
/*  419 */     for (int i = this.n; i-- != 0;) { if (key[i] != null && Double.doubleToLongBits(value[i]) == Double.doubleToLongBits(v)) return true;  }
/*  420 */      return false;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public double getOrDefault(Object k, double defaultValue) {
/*  427 */     if (this.strategy.equals(k, null)) return this.containsNullKey ? this.value[this.n] : defaultValue;
/*      */     
/*  429 */     K[] key = this.key;
/*      */     K curr;
/*      */     int pos;
/*  432 */     if ((curr = key[pos = HashCommon.mix(this.strategy.hashCode(k)) & this.mask]) == null) return defaultValue; 
/*  433 */     if (this.strategy.equals(k, curr)) return this.value[pos];
/*      */     
/*      */     while (true) {
/*  436 */       if ((curr = key[pos = pos + 1 & this.mask]) == null) return defaultValue; 
/*  437 */       if (this.strategy.equals(k, curr)) return this.value[pos];
/*      */     
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   public double putIfAbsent(K k, double v) {
/*  444 */     int pos = find(k);
/*  445 */     if (pos >= 0) return this.value[pos]; 
/*  446 */     insert(-pos - 1, k, v);
/*  447 */     return this.defRetValue;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean remove(Object k, double v) {
/*  454 */     if (this.strategy.equals(k, null)) {
/*  455 */       if (this.containsNullKey && Double.doubleToLongBits(v) == Double.doubleToLongBits(this.value[this.n])) {
/*  456 */         removeNullEntry();
/*  457 */         return true;
/*      */       } 
/*  459 */       return false;
/*      */     } 
/*      */     
/*  462 */     K[] key = this.key;
/*      */     K curr;
/*      */     int pos;
/*  465 */     if ((curr = key[pos = HashCommon.mix(this.strategy.hashCode(k)) & this.mask]) == null) return false; 
/*  466 */     if (this.strategy.equals(k, curr) && Double.doubleToLongBits(v) == Double.doubleToLongBits(this.value[pos])) {
/*  467 */       removeEntry(pos);
/*  468 */       return true;
/*      */     } 
/*      */     while (true) {
/*  471 */       if ((curr = key[pos = pos + 1 & this.mask]) == null) return false; 
/*  472 */       if (this.strategy.equals(k, curr) && Double.doubleToLongBits(v) == Double.doubleToLongBits(this.value[pos])) {
/*  473 */         removeEntry(pos);
/*  474 */         return true;
/*      */       } 
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean replace(K k, double oldValue, double v) {
/*  482 */     int pos = find(k);
/*  483 */     if (pos < 0 || Double.doubleToLongBits(oldValue) != Double.doubleToLongBits(this.value[pos])) return false; 
/*  484 */     this.value[pos] = v;
/*  485 */     return true;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public double replace(K k, double v) {
/*  491 */     int pos = find(k);
/*  492 */     if (pos < 0) return this.defRetValue; 
/*  493 */     double oldValue = this.value[pos];
/*  494 */     this.value[pos] = v;
/*  495 */     return oldValue;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public double computeIfAbsent(K k, ToDoubleFunction<? super K> mappingFunction) {
/*  501 */     Objects.requireNonNull(mappingFunction);
/*  502 */     int pos = find(k);
/*  503 */     if (pos >= 0) return this.value[pos]; 
/*  504 */     double newValue = mappingFunction.applyAsDouble(k);
/*  505 */     insert(-pos - 1, k, newValue);
/*  506 */     return newValue;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public double computeIfAbsent(K key, Object2DoubleFunction<? super K> mappingFunction) {
/*  512 */     Objects.requireNonNull(mappingFunction);
/*  513 */     int pos = find(key);
/*  514 */     if (pos >= 0) return this.value[pos]; 
/*  515 */     if (!mappingFunction.containsKey(key)) return this.defRetValue; 
/*  516 */     double newValue = mappingFunction.getDouble(key);
/*  517 */     insert(-pos - 1, key, newValue);
/*  518 */     return newValue;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public double computeDoubleIfPresent(K k, BiFunction<? super K, ? super Double, ? extends Double> remappingFunction) {
/*  524 */     Objects.requireNonNull(remappingFunction);
/*  525 */     int pos = find(k);
/*  526 */     if (pos < 0) return this.defRetValue; 
/*  527 */     Double newValue = remappingFunction.apply(k, Double.valueOf(this.value[pos]));
/*  528 */     if (newValue == null) {
/*  529 */       if (this.strategy.equals(k, null)) { removeNullEntry(); }
/*  530 */       else { removeEntry(pos); }
/*  531 */        return this.defRetValue;
/*      */     } 
/*  533 */     this.value[pos] = newValue.doubleValue(); return newValue.doubleValue();
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public double computeDouble(K k, BiFunction<? super K, ? super Double, ? extends Double> remappingFunction) {
/*  539 */     Objects.requireNonNull(remappingFunction);
/*  540 */     int pos = find(k);
/*  541 */     Double newValue = remappingFunction.apply(k, (pos >= 0) ? Double.valueOf(this.value[pos]) : null);
/*  542 */     if (newValue == null) {
/*  543 */       if (pos >= 0)
/*  544 */         if (this.strategy.equals(k, null)) { removeNullEntry(); }
/*  545 */         else { removeEntry(pos); }
/*      */          
/*  547 */       return this.defRetValue;
/*      */     } 
/*  549 */     double newVal = newValue.doubleValue();
/*  550 */     if (pos < 0) {
/*  551 */       insert(-pos - 1, k, newVal);
/*  552 */       return newVal;
/*      */     } 
/*  554 */     this.value[pos] = newVal; return newVal;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public double merge(K k, double v, BiFunction<? super Double, ? super Double, ? extends Double> remappingFunction) {
/*  560 */     Objects.requireNonNull(remappingFunction);
/*      */     
/*  562 */     int pos = find(k);
/*  563 */     if (pos < 0) {
/*  564 */       if (pos < 0) { insert(-pos - 1, k, v); }
/*  565 */       else { this.value[pos] = v; }
/*  566 */        return v;
/*      */     } 
/*  568 */     Double newValue = remappingFunction.apply(Double.valueOf(this.value[pos]), Double.valueOf(v));
/*  569 */     if (newValue == null) {
/*  570 */       if (this.strategy.equals(k, null)) { removeNullEntry(); }
/*  571 */       else { removeEntry(pos); }
/*  572 */        return this.defRetValue;
/*      */     } 
/*  574 */     this.value[pos] = newValue.doubleValue(); return newValue.doubleValue();
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
/*  585 */     if (this.size == 0)
/*  586 */       return;  this.size = 0;
/*  587 */     this.containsNullKey = false;
/*  588 */     Arrays.fill((Object[])this.key, (Object)null);
/*      */   }
/*      */ 
/*      */   
/*      */   public int size() {
/*  593 */     return this.size;
/*      */   }
/*      */ 
/*      */   
/*      */   public boolean isEmpty() {
/*  598 */     return (this.size == 0);
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   final class MapEntry
/*      */     implements Object2DoubleMap.Entry<K>, Map.Entry<K, Double>, ObjectDoublePair<K>
/*      */   {
/*      */     int index;
/*      */ 
/*      */ 
/*      */     
/*      */     MapEntry(int index) {
/*  611 */       this.index = index;
/*      */     }
/*      */ 
/*      */     
/*      */     MapEntry() {}
/*      */ 
/*      */     
/*      */     public K getKey() {
/*  619 */       return Object2DoubleOpenCustomHashMap.this.key[this.index];
/*      */     }
/*      */ 
/*      */     
/*      */     public K left() {
/*  624 */       return Object2DoubleOpenCustomHashMap.this.key[this.index];
/*      */     }
/*      */ 
/*      */     
/*      */     public double getDoubleValue() {
/*  629 */       return Object2DoubleOpenCustomHashMap.this.value[this.index];
/*      */     }
/*      */ 
/*      */     
/*      */     public double rightDouble() {
/*  634 */       return Object2DoubleOpenCustomHashMap.this.value[this.index];
/*      */     }
/*      */ 
/*      */     
/*      */     public double setValue(double v) {
/*  639 */       double oldValue = Object2DoubleOpenCustomHashMap.this.value[this.index];
/*  640 */       Object2DoubleOpenCustomHashMap.this.value[this.index] = v;
/*  641 */       return oldValue;
/*      */     }
/*      */ 
/*      */     
/*      */     public ObjectDoublePair<K> right(double v) {
/*  646 */       Object2DoubleOpenCustomHashMap.this.value[this.index] = v;
/*  647 */       return this;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     @Deprecated
/*      */     public Double getValue() {
/*  658 */       return Double.valueOf(Object2DoubleOpenCustomHashMap.this.value[this.index]);
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     @Deprecated
/*      */     public Double setValue(Double v) {
/*  669 */       return Double.valueOf(setValue(v.doubleValue()));
/*      */     }
/*      */ 
/*      */ 
/*      */     
/*      */     public boolean equals(Object o) {
/*  675 */       if (!(o instanceof Map.Entry)) return false; 
/*  676 */       Map.Entry<K, Double> e = (Map.Entry<K, Double>)o;
/*  677 */       return (Object2DoubleOpenCustomHashMap.this.strategy.equals(Object2DoubleOpenCustomHashMap.this.key[this.index], e.getKey()) && Double.doubleToLongBits(Object2DoubleOpenCustomHashMap.this.value[this.index]) == Double.doubleToLongBits(((Double)e.getValue()).doubleValue()));
/*      */     }
/*      */ 
/*      */     
/*      */     public int hashCode() {
/*  682 */       return Object2DoubleOpenCustomHashMap.this.strategy.hashCode(Object2DoubleOpenCustomHashMap.this.key[this.index]) ^ HashCommon.double2int(Object2DoubleOpenCustomHashMap.this.value[this.index]);
/*      */     }
/*      */ 
/*      */     
/*      */     public String toString() {
/*  687 */       return (new StringBuilder()).append(Object2DoubleOpenCustomHashMap.this.key[this.index]).append("=>").append(Object2DoubleOpenCustomHashMap.this.value[this.index]).toString();
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
/*  698 */     int pos = Object2DoubleOpenCustomHashMap.this.n;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  704 */     int last = -1;
/*      */     
/*  706 */     int c = Object2DoubleOpenCustomHashMap.this.size;
/*      */     
/*  708 */     boolean mustReturnNullKey = Object2DoubleOpenCustomHashMap.this.containsNullKey;
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
/*  719 */       return (this.c != 0);
/*      */     }
/*      */     
/*      */     public int nextEntry() {
/*  723 */       if (!hasNext()) throw new NoSuchElementException(); 
/*  724 */       this.c--;
/*  725 */       if (this.mustReturnNullKey) {
/*  726 */         this.mustReturnNullKey = false;
/*  727 */         return this.last = Object2DoubleOpenCustomHashMap.this.n;
/*      */       } 
/*  729 */       K[] key = Object2DoubleOpenCustomHashMap.this.key;
/*      */       while (true) {
/*  731 */         if (--this.pos < 0) {
/*      */           
/*  733 */           this.last = Integer.MIN_VALUE;
/*  734 */           K k = this.wrapped.get(-this.pos - 1);
/*  735 */           int p = HashCommon.mix(Object2DoubleOpenCustomHashMap.this.strategy.hashCode(k)) & Object2DoubleOpenCustomHashMap.this.mask;
/*  736 */           for (; !Object2DoubleOpenCustomHashMap.this.strategy.equals(k, key[p]); p = p + 1 & Object2DoubleOpenCustomHashMap.this.mask);
/*  737 */           return p;
/*      */         } 
/*  739 */         if (key[this.pos] != null) return this.last = this.pos; 
/*      */       } 
/*      */     }
/*      */     
/*      */     public void forEachRemaining(ConsumerType action) {
/*  744 */       if (this.mustReturnNullKey) {
/*  745 */         this.mustReturnNullKey = false;
/*  746 */         acceptOnIndex(action, this.last = Object2DoubleOpenCustomHashMap.this.n);
/*  747 */         this.c--;
/*      */       } 
/*  749 */       K[] key = Object2DoubleOpenCustomHashMap.this.key;
/*  750 */       while (this.c != 0) {
/*  751 */         if (--this.pos < 0) {
/*      */           
/*  753 */           this.last = Integer.MIN_VALUE;
/*  754 */           K k = this.wrapped.get(-this.pos - 1);
/*  755 */           int p = HashCommon.mix(Object2DoubleOpenCustomHashMap.this.strategy.hashCode(k)) & Object2DoubleOpenCustomHashMap.this.mask;
/*  756 */           for (; !Object2DoubleOpenCustomHashMap.this.strategy.equals(k, key[p]); p = p + 1 & Object2DoubleOpenCustomHashMap.this.mask);
/*  757 */           acceptOnIndex(action, p);
/*  758 */           this.c--; continue;
/*  759 */         }  if (key[this.pos] != null) {
/*  760 */           acceptOnIndex(action, this.last = this.pos);
/*  761 */           this.c--;
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
/*  776 */       K[] key = Object2DoubleOpenCustomHashMap.this.key; while (true) {
/*      */         K curr; int last;
/*  778 */         pos = (last = pos) + 1 & Object2DoubleOpenCustomHashMap.this.mask;
/*      */         while (true) {
/*  780 */           if ((curr = key[pos]) == null) {
/*  781 */             key[last] = null;
/*      */             return;
/*      */           } 
/*  784 */           int slot = HashCommon.mix(Object2DoubleOpenCustomHashMap.this.strategy.hashCode(curr)) & Object2DoubleOpenCustomHashMap.this.mask;
/*  785 */           if ((last <= pos) ? (last >= slot || slot > pos) : (last >= slot && slot > pos))
/*  786 */             break;  pos = pos + 1 & Object2DoubleOpenCustomHashMap.this.mask;
/*      */         } 
/*  788 */         if (pos < last) {
/*  789 */           if (this.wrapped == null) this.wrapped = new ObjectArrayList<>(2); 
/*  790 */           this.wrapped.add(key[pos]);
/*      */         } 
/*  792 */         key[last] = curr;
/*  793 */         Object2DoubleOpenCustomHashMap.this.value[last] = Object2DoubleOpenCustomHashMap.this.value[pos];
/*      */       } 
/*      */     }
/*      */     
/*      */     public void remove() {
/*  798 */       if (this.last == -1) throw new IllegalStateException(); 
/*  799 */       if (this.last == Object2DoubleOpenCustomHashMap.this.n)
/*  800 */       { Object2DoubleOpenCustomHashMap.this.containsNullKey = false;
/*  801 */         Object2DoubleOpenCustomHashMap.this.key[Object2DoubleOpenCustomHashMap.this.n] = null; }
/*  802 */       else if (this.pos >= 0) { shiftKeys(this.last); }
/*      */       else
/*      */       
/*  805 */       { Object2DoubleOpenCustomHashMap.this.removeDouble(this.wrapped.set(-this.pos - 1, null));
/*  806 */         this.last = -1;
/*      */         return; }
/*      */       
/*  809 */       Object2DoubleOpenCustomHashMap.this.size--;
/*  810 */       this.last = -1;
/*      */     }
/*      */ 
/*      */     
/*      */     public int skip(int n) {
/*  815 */       int i = n;
/*  816 */       for (; i-- != 0 && hasNext(); nextEntry());
/*  817 */       return n - i - 1;
/*      */     }
/*      */     
/*      */     private MapIterator() {}
/*      */     
/*      */     abstract void acceptOnIndex(ConsumerType param1ConsumerType, int param1Int);
/*      */   }
/*      */   
/*      */   private final class EntryIterator extends MapIterator<Consumer<? super Object2DoubleMap.Entry<K>>> implements ObjectIterator<Object2DoubleMap.Entry<K>> { public Object2DoubleOpenCustomHashMap<K>.MapEntry next() {
/*  826 */       return this.entry = new Object2DoubleOpenCustomHashMap.MapEntry(nextEntry());
/*      */     }
/*      */     private Object2DoubleOpenCustomHashMap<K>.MapEntry entry;
/*      */     private EntryIterator() {}
/*      */     
/*      */     final void acceptOnIndex(Consumer<? super Object2DoubleMap.Entry<K>> action, int index) {
/*  832 */       action.accept(this.entry = new Object2DoubleOpenCustomHashMap.MapEntry(index));
/*      */     }
/*      */ 
/*      */     
/*      */     public void remove() {
/*  837 */       super.remove();
/*  838 */       this.entry.index = -1;
/*      */     } }
/*      */   
/*      */   private final class FastEntryIterator extends MapIterator<Consumer<? super Object2DoubleMap.Entry<K>>> implements ObjectIterator<Object2DoubleMap.Entry<K>> {
/*      */     private FastEntryIterator() {
/*  843 */       this.entry = new Object2DoubleOpenCustomHashMap.MapEntry();
/*      */     }
/*      */     private final Object2DoubleOpenCustomHashMap<K>.MapEntry entry;
/*      */     public Object2DoubleOpenCustomHashMap<K>.MapEntry next() {
/*  847 */       this.entry.index = nextEntry();
/*  848 */       return this.entry;
/*      */     }
/*      */ 
/*      */ 
/*      */     
/*      */     final void acceptOnIndex(Consumer<? super Object2DoubleMap.Entry<K>> action, int index) {
/*  854 */       this.entry.index = index;
/*  855 */       action.accept(this.entry);
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private abstract class MapSpliterator<ConsumerType, SplitType extends MapSpliterator<ConsumerType, SplitType>>
/*      */   {
/*  864 */     int pos = 0;
/*      */     
/*  866 */     int max = Object2DoubleOpenCustomHashMap.this.n;
/*      */     
/*  868 */     int c = 0;
/*      */     
/*  870 */     boolean mustReturnNull = Object2DoubleOpenCustomHashMap.this.containsNullKey;
/*      */     
/*      */     boolean hasSplit = false;
/*      */     
/*      */     MapSpliterator() {}
/*      */     
/*      */     MapSpliterator(int pos, int max, boolean mustReturnNull, boolean hasSplit) {
/*  877 */       this.pos = pos;
/*  878 */       this.max = max;
/*  879 */       this.mustReturnNull = mustReturnNull;
/*  880 */       this.hasSplit = hasSplit;
/*      */     }
/*      */     
/*      */     abstract void acceptOnIndex(ConsumerType param1ConsumerType, int param1Int);
/*      */     
/*      */     abstract SplitType makeForSplit(int param1Int1, int param1Int2, boolean param1Boolean);
/*      */     
/*      */     public boolean tryAdvance(ConsumerType action) {
/*  888 */       if (this.mustReturnNull) {
/*  889 */         this.mustReturnNull = false;
/*  890 */         this.c++;
/*  891 */         acceptOnIndex(action, Object2DoubleOpenCustomHashMap.this.n);
/*  892 */         return true;
/*      */       } 
/*  894 */       K[] key = Object2DoubleOpenCustomHashMap.this.key;
/*  895 */       while (this.pos < this.max) {
/*  896 */         if (key[this.pos] != null) {
/*  897 */           this.c++;
/*  898 */           acceptOnIndex(action, this.pos++);
/*  899 */           return true;
/*      */         } 
/*  901 */         this.pos++;
/*      */       } 
/*  903 */       return false;
/*      */     }
/*      */     
/*      */     public void forEachRemaining(ConsumerType action) {
/*  907 */       if (this.mustReturnNull) {
/*  908 */         this.mustReturnNull = false;
/*  909 */         this.c++;
/*  910 */         acceptOnIndex(action, Object2DoubleOpenCustomHashMap.this.n);
/*      */       } 
/*  912 */       K[] key = Object2DoubleOpenCustomHashMap.this.key;
/*  913 */       while (this.pos < this.max) {
/*  914 */         if (key[this.pos] != null) {
/*  915 */           acceptOnIndex(action, this.pos);
/*  916 */           this.c++;
/*      */         } 
/*  918 */         this.pos++;
/*      */       } 
/*      */     }
/*      */     
/*      */     public long estimateSize() {
/*  923 */       if (!this.hasSplit)
/*      */       {
/*  925 */         return (Object2DoubleOpenCustomHashMap.this.size - this.c);
/*      */       }
/*      */ 
/*      */ 
/*      */       
/*  930 */       return Math.min((Object2DoubleOpenCustomHashMap.this.size - this.c), (long)(Object2DoubleOpenCustomHashMap.this.realSize() / Object2DoubleOpenCustomHashMap.this.n * (this.max - this.pos)) + (this.mustReturnNull ? 1L : 0L));
/*      */     }
/*      */ 
/*      */     
/*      */     public SplitType trySplit() {
/*  935 */       if (this.pos >= this.max - 1) return null; 
/*  936 */       int retLen = this.max - this.pos >> 1;
/*  937 */       if (retLen <= 1) return null; 
/*  938 */       int myNewPos = this.pos + retLen;
/*  939 */       int retPos = this.pos;
/*  940 */       int retMax = myNewPos;
/*      */ 
/*      */ 
/*      */       
/*  944 */       SplitType split = makeForSplit(retPos, retMax, this.mustReturnNull);
/*  945 */       this.pos = myNewPos;
/*  946 */       this.mustReturnNull = false;
/*  947 */       this.hasSplit = true;
/*  948 */       return split;
/*      */     }
/*      */     
/*      */     public long skip(long n) {
/*  952 */       if (n < 0L) throw new IllegalArgumentException("Argument must be nonnegative: " + n); 
/*  953 */       if (n == 0L) return 0L; 
/*  954 */       long skipped = 0L;
/*  955 */       if (this.mustReturnNull) {
/*  956 */         this.mustReturnNull = false;
/*  957 */         skipped++;
/*  958 */         n--;
/*      */       } 
/*  960 */       K[] key = Object2DoubleOpenCustomHashMap.this.key;
/*  961 */       while (this.pos < this.max && n > 0L) {
/*  962 */         if (key[this.pos++] != null) {
/*  963 */           skipped++;
/*  964 */           n--;
/*      */         } 
/*      */       } 
/*  967 */       return skipped;
/*      */     }
/*      */   }
/*      */   
/*      */   private final class EntrySpliterator
/*      */     extends MapSpliterator<Consumer<? super Object2DoubleMap.Entry<K>>, EntrySpliterator> implements ObjectSpliterator<Object2DoubleMap.Entry<K>> {
/*      */     private static final int POST_SPLIT_CHARACTERISTICS = 1;
/*      */     
/*      */     EntrySpliterator() {}
/*      */     
/*      */     EntrySpliterator(int pos, int max, boolean mustReturnNull, boolean hasSplit) {
/*  978 */       super(pos, max, mustReturnNull, hasSplit);
/*      */     }
/*      */ 
/*      */     
/*      */     public int characteristics() {
/*  983 */       return this.hasSplit ? 1 : 65;
/*      */     }
/*      */ 
/*      */     
/*      */     final void acceptOnIndex(Consumer<? super Object2DoubleMap.Entry<K>> action, int index) {
/*  988 */       action.accept(new Object2DoubleOpenCustomHashMap.MapEntry(index));
/*      */     }
/*      */ 
/*      */     
/*      */     final EntrySpliterator makeForSplit(int pos, int max, boolean mustReturnNull) {
/*  993 */       return new EntrySpliterator(pos, max, mustReturnNull, true);
/*      */     } }
/*      */   
/*      */   private final class MapEntrySet extends AbstractObjectSet<Object2DoubleMap.Entry<K>> implements Object2DoubleMap.FastEntrySet<K> {
/*      */     private MapEntrySet() {}
/*      */     
/*      */     public ObjectIterator<Object2DoubleMap.Entry<K>> iterator() {
/* 1000 */       return new Object2DoubleOpenCustomHashMap.EntryIterator();
/*      */     }
/*      */ 
/*      */     
/*      */     public ObjectIterator<Object2DoubleMap.Entry<K>> fastIterator() {
/* 1005 */       return new Object2DoubleOpenCustomHashMap.FastEntryIterator();
/*      */     }
/*      */ 
/*      */     
/*      */     public ObjectSpliterator<Object2DoubleMap.Entry<K>> spliterator() {
/* 1010 */       return new Object2DoubleOpenCustomHashMap.EntrySpliterator();
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public boolean contains(Object o) {
/* 1017 */       if (!(o instanceof Map.Entry)) return false; 
/* 1018 */       Map.Entry<?, ?> e = (Map.Entry<?, ?>)o;
/* 1019 */       if (e.getValue() == null || !(e.getValue() instanceof Double)) return false; 
/* 1020 */       K k = (K)e.getKey();
/* 1021 */       double v = ((Double)e.getValue()).doubleValue();
/* 1022 */       if (Object2DoubleOpenCustomHashMap.this.strategy.equals(k, null)) return (Object2DoubleOpenCustomHashMap.this.containsNullKey && Double.doubleToLongBits(Object2DoubleOpenCustomHashMap.this.value[Object2DoubleOpenCustomHashMap.this.n]) == Double.doubleToLongBits(v));
/*      */       
/* 1024 */       K[] key = Object2DoubleOpenCustomHashMap.this.key;
/*      */       K curr;
/*      */       int pos;
/* 1027 */       if ((curr = key[pos = HashCommon.mix(Object2DoubleOpenCustomHashMap.this.strategy.hashCode(k)) & Object2DoubleOpenCustomHashMap.this.mask]) == null) return false; 
/* 1028 */       if (Object2DoubleOpenCustomHashMap.this.strategy.equals(k, curr)) return (Double.doubleToLongBits(Object2DoubleOpenCustomHashMap.this.value[pos]) == Double.doubleToLongBits(v));
/*      */       
/*      */       while (true) {
/* 1031 */         if ((curr = key[pos = pos + 1 & Object2DoubleOpenCustomHashMap.this.mask]) == null) return false; 
/* 1032 */         if (Object2DoubleOpenCustomHashMap.this.strategy.equals(k, curr)) return (Double.doubleToLongBits(Object2DoubleOpenCustomHashMap.this.value[pos]) == Double.doubleToLongBits(v));
/*      */       
/*      */       } 
/*      */     }
/*      */ 
/*      */     
/*      */     public boolean remove(Object o) {
/* 1039 */       if (!(o instanceof Map.Entry)) return false; 
/* 1040 */       Map.Entry<?, ?> e = (Map.Entry<?, ?>)o;
/* 1041 */       if (e.getValue() == null || !(e.getValue() instanceof Double)) return false; 
/* 1042 */       K k = (K)e.getKey();
/* 1043 */       double v = ((Double)e.getValue()).doubleValue();
/* 1044 */       if (Object2DoubleOpenCustomHashMap.this.strategy.equals(k, null)) {
/* 1045 */         if (Object2DoubleOpenCustomHashMap.this.containsNullKey && Double.doubleToLongBits(Object2DoubleOpenCustomHashMap.this.value[Object2DoubleOpenCustomHashMap.this.n]) == Double.doubleToLongBits(v)) {
/* 1046 */           Object2DoubleOpenCustomHashMap.this.removeNullEntry();
/* 1047 */           return true;
/*      */         } 
/* 1049 */         return false;
/*      */       } 
/*      */       
/* 1052 */       K[] key = Object2DoubleOpenCustomHashMap.this.key;
/*      */       K curr;
/*      */       int pos;
/* 1055 */       if ((curr = key[pos = HashCommon.mix(Object2DoubleOpenCustomHashMap.this.strategy.hashCode(k)) & Object2DoubleOpenCustomHashMap.this.mask]) == null) return false; 
/* 1056 */       if (Object2DoubleOpenCustomHashMap.this.strategy.equals(curr, k)) {
/* 1057 */         if (Double.doubleToLongBits(Object2DoubleOpenCustomHashMap.this.value[pos]) == Double.doubleToLongBits(v)) {
/* 1058 */           Object2DoubleOpenCustomHashMap.this.removeEntry(pos);
/* 1059 */           return true;
/*      */         } 
/* 1061 */         return false;
/*      */       } 
/*      */       while (true) {
/* 1064 */         if ((curr = key[pos = pos + 1 & Object2DoubleOpenCustomHashMap.this.mask]) == null) return false; 
/* 1065 */         if (Object2DoubleOpenCustomHashMap.this.strategy.equals(curr, k) && 
/* 1066 */           Double.doubleToLongBits(Object2DoubleOpenCustomHashMap.this.value[pos]) == Double.doubleToLongBits(v)) {
/* 1067 */           Object2DoubleOpenCustomHashMap.this.removeEntry(pos);
/* 1068 */           return true;
/*      */         } 
/*      */       } 
/*      */     }
/*      */ 
/*      */ 
/*      */     
/*      */     public int size() {
/* 1076 */       return Object2DoubleOpenCustomHashMap.this.size;
/*      */     }
/*      */ 
/*      */     
/*      */     public void clear() {
/* 1081 */       Object2DoubleOpenCustomHashMap.this.clear();
/*      */     }
/*      */ 
/*      */ 
/*      */     
/*      */     public void forEach(Consumer<? super Object2DoubleMap.Entry<K>> consumer) {
/* 1087 */       if (Object2DoubleOpenCustomHashMap.this.containsNullKey) consumer.accept(new Object2DoubleOpenCustomHashMap.MapEntry(Object2DoubleOpenCustomHashMap.this.n)); 
/* 1088 */       for (int pos = Object2DoubleOpenCustomHashMap.this.n; pos-- != 0;) { if (Object2DoubleOpenCustomHashMap.this.key[pos] != null) consumer.accept(new Object2DoubleOpenCustomHashMap.MapEntry(pos));
/*      */          }
/*      */     
/*      */     }
/*      */     
/*      */     public void fastForEach(Consumer<? super Object2DoubleMap.Entry<K>> consumer) {
/* 1094 */       Object2DoubleOpenCustomHashMap<K>.MapEntry entry = new Object2DoubleOpenCustomHashMap.MapEntry();
/* 1095 */       if (Object2DoubleOpenCustomHashMap.this.containsNullKey) {
/* 1096 */         entry.index = Object2DoubleOpenCustomHashMap.this.n;
/* 1097 */         consumer.accept(entry);
/*      */       } 
/* 1099 */       for (int pos = Object2DoubleOpenCustomHashMap.this.n; pos-- != 0;) { if (Object2DoubleOpenCustomHashMap.this.key[pos] != null) {
/* 1100 */           entry.index = pos;
/* 1101 */           consumer.accept(entry);
/*      */         }  }
/*      */     
/*      */     }
/*      */   }
/*      */   
/*      */   public Object2DoubleMap.FastEntrySet<K> object2DoubleEntrySet() {
/* 1108 */     if (this.entries == null) this.entries = new MapEntrySet(); 
/* 1109 */     return this.entries;
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
/* 1130 */       action.accept(Object2DoubleOpenCustomHashMap.this.key[index]);
/*      */     }
/*      */ 
/*      */     
/*      */     public K next() {
/* 1135 */       return Object2DoubleOpenCustomHashMap.this.key[nextEntry()];
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
/* 1146 */       super(pos, max, mustReturnNull, hasSplit);
/*      */     }
/*      */ 
/*      */     
/*      */     public int characteristics() {
/* 1151 */       return this.hasSplit ? 1 : 65;
/*      */     }
/*      */ 
/*      */     
/*      */     final void acceptOnIndex(Consumer<? super K> action, int index) {
/* 1156 */       action.accept(Object2DoubleOpenCustomHashMap.this.key[index]);
/*      */     }
/*      */ 
/*      */     
/*      */     final KeySpliterator makeForSplit(int pos, int max, boolean mustReturnNull) {
/* 1161 */       return new KeySpliterator(pos, max, mustReturnNull, true);
/*      */     } }
/*      */   
/*      */   private final class KeySet extends AbstractObjectSet<K> {
/*      */     private KeySet() {}
/*      */     
/*      */     public ObjectIterator<K> iterator() {
/* 1168 */       return new Object2DoubleOpenCustomHashMap.KeyIterator();
/*      */     }
/*      */ 
/*      */     
/*      */     public ObjectSpliterator<K> spliterator() {
/* 1173 */       return new Object2DoubleOpenCustomHashMap.KeySpliterator();
/*      */     }
/*      */ 
/*      */ 
/*      */     
/*      */     public void forEach(Consumer<? super K> consumer) {
/* 1179 */       if (Object2DoubleOpenCustomHashMap.this.containsNullKey) consumer.accept(Object2DoubleOpenCustomHashMap.this.key[Object2DoubleOpenCustomHashMap.this.n]); 
/* 1180 */       for (int pos = Object2DoubleOpenCustomHashMap.this.n; pos-- != 0; ) {
/* 1181 */         K k = Object2DoubleOpenCustomHashMap.this.key[pos];
/* 1182 */         if (k != null) consumer.accept(k);
/*      */       
/*      */       } 
/*      */     }
/*      */     
/*      */     public int size() {
/* 1188 */       return Object2DoubleOpenCustomHashMap.this.size;
/*      */     }
/*      */ 
/*      */     
/*      */     public boolean contains(Object k) {
/* 1193 */       return Object2DoubleOpenCustomHashMap.this.containsKey(k);
/*      */     }
/*      */ 
/*      */     
/*      */     public boolean remove(Object k) {
/* 1198 */       int oldSize = Object2DoubleOpenCustomHashMap.this.size;
/* 1199 */       Object2DoubleOpenCustomHashMap.this.removeDouble(k);
/* 1200 */       return (Object2DoubleOpenCustomHashMap.this.size != oldSize);
/*      */     }
/*      */ 
/*      */     
/*      */     public void clear() {
/* 1205 */       Object2DoubleOpenCustomHashMap.this.clear();
/*      */     }
/*      */   }
/*      */ 
/*      */   
/*      */   public ObjectSet<K> keySet() {
/* 1211 */     if (this.keys == null) this.keys = new KeySet(); 
/* 1212 */     return this.keys;
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
/*      */     extends MapIterator<DoubleConsumer>
/*      */     implements DoubleIterator
/*      */   {
/*      */     final void acceptOnIndex(DoubleConsumer action, int index) {
/* 1233 */       action.accept(Object2DoubleOpenCustomHashMap.this.value[index]);
/*      */     }
/*      */ 
/*      */     
/*      */     public double nextDouble() {
/* 1238 */       return Object2DoubleOpenCustomHashMap.this.value[nextEntry()];
/*      */     }
/*      */   }
/*      */   
/*      */   private final class ValueSpliterator
/*      */     extends MapSpliterator<DoubleConsumer, ValueSpliterator> implements DoubleSpliterator {
/*      */     private static final int POST_SPLIT_CHARACTERISTICS = 256;
/*      */     
/*      */     ValueSpliterator() {}
/*      */     
/*      */     ValueSpliterator(int pos, int max, boolean mustReturnNull, boolean hasSplit) {
/* 1249 */       super(pos, max, mustReturnNull, hasSplit);
/*      */     }
/*      */ 
/*      */     
/*      */     public int characteristics() {
/* 1254 */       return this.hasSplit ? 256 : 320;
/*      */     }
/*      */ 
/*      */     
/*      */     final void acceptOnIndex(DoubleConsumer action, int index) {
/* 1259 */       action.accept(Object2DoubleOpenCustomHashMap.this.value[index]);
/*      */     }
/*      */ 
/*      */     
/*      */     final ValueSpliterator makeForSplit(int pos, int max, boolean mustReturnNull) {
/* 1264 */       return new ValueSpliterator(pos, max, mustReturnNull, true);
/*      */     }
/*      */   }
/*      */ 
/*      */   
/*      */   public DoubleCollection values() {
/* 1270 */     if (this.values == null) this.values = (DoubleCollection)new AbstractDoubleCollection()
/*      */         {
/*      */           public DoubleIterator iterator() {
/* 1273 */             return new Object2DoubleOpenCustomHashMap.ValueIterator();
/*      */           }
/*      */ 
/*      */           
/*      */           public DoubleSpliterator spliterator() {
/* 1278 */             return new Object2DoubleOpenCustomHashMap.ValueSpliterator();
/*      */           }
/*      */ 
/*      */ 
/*      */           
/*      */           public void forEach(DoubleConsumer consumer) {
/* 1284 */             if (Object2DoubleOpenCustomHashMap.this.containsNullKey) consumer.accept(Object2DoubleOpenCustomHashMap.this.value[Object2DoubleOpenCustomHashMap.this.n]); 
/* 1285 */             for (int pos = Object2DoubleOpenCustomHashMap.this.n; pos-- != 0;) { if (Object2DoubleOpenCustomHashMap.this.key[pos] != null) consumer.accept(Object2DoubleOpenCustomHashMap.this.value[pos]);  }
/*      */           
/*      */           }
/*      */           
/*      */           public int size() {
/* 1290 */             return Object2DoubleOpenCustomHashMap.this.size;
/*      */           }
/*      */ 
/*      */           
/*      */           public boolean contains(double v) {
/* 1295 */             return Object2DoubleOpenCustomHashMap.this.containsValue(v);
/*      */           }
/*      */ 
/*      */           
/*      */           public void clear() {
/* 1300 */             Object2DoubleOpenCustomHashMap.this.clear();
/*      */           }
/*      */         }; 
/* 1303 */     return this.values;
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
/* 1320 */     return trim(this.size);
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
/* 1342 */     int l = HashCommon.nextPowerOfTwo((int)Math.ceil((n / this.f)));
/* 1343 */     if (l >= this.n || this.size > HashCommon.maxFill(l, this.f)) return true; 
/*      */     try {
/* 1345 */       rehash(l);
/* 1346 */     } catch (OutOfMemoryError cantDoIt) {
/* 1347 */       return false;
/*      */     } 
/* 1349 */     return true;
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
/* 1364 */     K[] key = this.key;
/* 1365 */     double[] value = this.value;
/* 1366 */     int mask = newN - 1;
/* 1367 */     K[] newKey = (K[])new Object[newN + 1];
/* 1368 */     double[] newValue = new double[newN + 1];
/* 1369 */     int i = this.n;
/* 1370 */     for (int j = realSize(); j-- != 0; ) {
/* 1371 */       while (key[--i] == null); int pos;
/* 1372 */       if (newKey[pos = HashCommon.mix(this.strategy.hashCode(key[i])) & mask] != null) while (newKey[pos = pos + 1 & mask] != null); 
/* 1373 */       newKey[pos] = key[i];
/* 1374 */       newValue[pos] = value[i];
/*      */     } 
/* 1376 */     newValue[newN] = value[this.n];
/* 1377 */     this.n = newN;
/* 1378 */     this.mask = mask;
/* 1379 */     this.maxFill = HashCommon.maxFill(this.n, this.f);
/* 1380 */     this.key = newKey;
/* 1381 */     this.value = newValue;
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
/*      */   public Object2DoubleOpenCustomHashMap<K> clone() {
/*      */     Object2DoubleOpenCustomHashMap<K> c;
/*      */     try {
/* 1398 */       c = (Object2DoubleOpenCustomHashMap<K>)super.clone();
/* 1399 */     } catch (CloneNotSupportedException cantHappen) {
/* 1400 */       throw new InternalError();
/*      */     } 
/* 1402 */     c.keys = null;
/* 1403 */     c.values = null;
/* 1404 */     c.entries = null;
/* 1405 */     c.containsNullKey = this.containsNullKey;
/* 1406 */     c.key = (K[])this.key.clone();
/* 1407 */     c.value = (double[])this.value.clone();
/* 1408 */     c.strategy = this.strategy;
/* 1409 */     return c;
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
/* 1423 */     int h = 0;
/* 1424 */     for (int j = realSize(), i = 0, t = 0; j-- != 0; ) {
/* 1425 */       for (; this.key[i] == null; i++);
/* 1426 */       if (this != this.key[i]) t = this.strategy.hashCode(this.key[i]); 
/* 1427 */       t ^= HashCommon.double2int(this.value[i]);
/* 1428 */       h += t;
/* 1429 */       i++;
/*      */     } 
/*      */     
/* 1432 */     if (this.containsNullKey) h += HashCommon.double2int(this.value[this.n]); 
/* 1433 */     return h;
/*      */   }
/*      */   
/*      */   private void writeObject(ObjectOutputStream s) throws IOException {
/* 1437 */     K[] key = this.key;
/* 1438 */     double[] value = this.value;
/* 1439 */     EntryIterator i = new EntryIterator();
/* 1440 */     s.defaultWriteObject();
/* 1441 */     for (int j = this.size; j-- != 0; ) {
/* 1442 */       int e = i.nextEntry();
/* 1443 */       s.writeObject(key[e]);
/* 1444 */       s.writeDouble(value[e]);
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   private void readObject(ObjectInputStream s) throws IOException, ClassNotFoundException {
/* 1450 */     s.defaultReadObject();
/* 1451 */     this.n = HashCommon.arraySize(this.size, this.f);
/* 1452 */     this.maxFill = HashCommon.maxFill(this.n, this.f);
/* 1453 */     this.mask = this.n - 1;
/* 1454 */     K[] key = this.key = (K[])new Object[this.n + 1];
/* 1455 */     double[] value = this.value = new double[this.n + 1];
/*      */ 
/*      */     
/* 1458 */     for (int i = this.size; i-- != 0; ) {
/* 1459 */       int pos; K k = (K)s.readObject();
/* 1460 */       double v = s.readDouble();
/* 1461 */       if (this.strategy.equals(k, null)) {
/* 1462 */         pos = this.n;
/* 1463 */         this.containsNullKey = true;
/*      */       } else {
/* 1465 */         pos = HashCommon.mix(this.strategy.hashCode(k)) & this.mask;
/* 1466 */         for (; key[pos] != null; pos = pos + 1 & this.mask);
/*      */       } 
/* 1468 */       key[pos] = k;
/* 1469 */       value[pos] = v;
/*      */     } 
/*      */   }
/*      */   
/*      */   private void checkTable() {}
/*      */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\i\\unimi\dsi\fastutil\objects\Object2DoubleOpenCustomHashMap.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */