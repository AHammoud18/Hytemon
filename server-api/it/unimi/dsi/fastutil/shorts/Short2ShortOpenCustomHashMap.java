/*      */ package it.unimi.dsi.fastutil.shorts;
/*      */ 
/*      */ import it.unimi.dsi.fastutil.Hash;
/*      */ import it.unimi.dsi.fastutil.HashCommon;
/*      */ import it.unimi.dsi.fastutil.SafeMath;
/*      */ import it.unimi.dsi.fastutil.objects.AbstractObjectSet;
/*      */ import it.unimi.dsi.fastutil.objects.ObjectIterator;
/*      */ import it.unimi.dsi.fastutil.objects.ObjectSet;
/*      */ import it.unimi.dsi.fastutil.objects.ObjectSpliterator;
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
/*      */ import java.util.function.IntFunction;
/*      */ import java.util.function.IntUnaryOperator;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ public class Short2ShortOpenCustomHashMap
/*      */   extends AbstractShort2ShortMap
/*      */   implements Serializable, Cloneable, Hash
/*      */ {
/*      */   private static final long serialVersionUID = 0L;
/*      */   private static final boolean ASSERTS = false;
/*      */   protected transient short[] key;
/*      */   protected transient short[] value;
/*      */   protected transient int mask;
/*      */   protected transient boolean containsNullKey;
/*      */   protected ShortHash.Strategy strategy;
/*      */   protected transient int n;
/*      */   protected transient int maxFill;
/*      */   protected final transient int minN;
/*      */   protected int size;
/*      */   protected final float f;
/*      */   protected transient Short2ShortMap.FastEntrySet entries;
/*      */   protected transient ShortSet keys;
/*      */   protected transient ShortCollection values;
/*      */   
/*      */   public Short2ShortOpenCustomHashMap(int expected, float f, ShortHash.Strategy strategy) {
/*   98 */     this.strategy = strategy;
/*   99 */     if (f <= 0.0F || f >= 1.0F) throw new IllegalArgumentException("Load factor must be greater than 0 and smaller than 1"); 
/*  100 */     if (expected < 0) throw new IllegalArgumentException("The expected number of elements must be nonnegative"); 
/*  101 */     this.f = f;
/*  102 */     this.minN = this.n = HashCommon.arraySize(expected, f);
/*  103 */     this.mask = this.n - 1;
/*  104 */     this.maxFill = HashCommon.maxFill(this.n, f);
/*  105 */     this.key = new short[this.n + 1];
/*  106 */     this.value = new short[this.n + 1];
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Short2ShortOpenCustomHashMap(int expected, ShortHash.Strategy strategy) {
/*  116 */     this(expected, 0.75F, strategy);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Short2ShortOpenCustomHashMap(ShortHash.Strategy strategy) {
/*  126 */     this(16, 0.75F, strategy);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Short2ShortOpenCustomHashMap(Map<? extends Short, ? extends Short> m, float f, ShortHash.Strategy strategy) {
/*  137 */     this(m.size(), f, strategy);
/*  138 */     putAll(m);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Short2ShortOpenCustomHashMap(Map<? extends Short, ? extends Short> m, ShortHash.Strategy strategy) {
/*  148 */     this(m, 0.75F, strategy);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Short2ShortOpenCustomHashMap(Short2ShortMap m, float f, ShortHash.Strategy strategy) {
/*  159 */     this(m.size(), f, strategy);
/*  160 */     putAll(m);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Short2ShortOpenCustomHashMap(Short2ShortMap m, ShortHash.Strategy strategy) {
/*  171 */     this(m, 0.75F, strategy);
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
/*      */   public Short2ShortOpenCustomHashMap(short[] k, short[] v, float f, ShortHash.Strategy strategy) {
/*  184 */     this(k.length, f, strategy);
/*  185 */     if (k.length != v.length) throw new IllegalArgumentException("The key array and the value array have different lengths (" + k.length + " and " + v.length + ")"); 
/*  186 */     for (int i = 0; i < k.length; ) { put(k[i], v[i]); i++; }
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
/*      */   public Short2ShortOpenCustomHashMap(short[] k, short[] v, ShortHash.Strategy strategy) {
/*  199 */     this(k, v, 0.75F, strategy);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public ShortHash.Strategy strategy() {
/*  208 */     return this.strategy;
/*      */   }
/*      */   
/*      */   private int realSize() {
/*  212 */     return this.containsNullKey ? (this.size - 1) : this.size;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void ensureCapacity(int capacity) {
/*  222 */     int needed = HashCommon.arraySize(capacity, this.f);
/*  223 */     if (needed > this.n) rehash(needed); 
/*      */   }
/*      */   
/*      */   private void tryCapacity(long capacity) {
/*  227 */     int needed = (int)Math.min(1073741824L, Math.max(2L, HashCommon.nextPowerOfTwo((long)Math.ceil(((float)capacity / this.f)))));
/*  228 */     if (needed > this.n) rehash(needed); 
/*      */   }
/*      */   
/*      */   private short removeEntry(int pos) {
/*  232 */     short oldValue = this.value[pos];
/*  233 */     this.size--;
/*  234 */     shiftKeys(pos);
/*  235 */     if (this.n > this.minN && this.size < this.maxFill / 4 && this.n > 16) rehash(this.n / 2); 
/*  236 */     return oldValue;
/*      */   }
/*      */   
/*      */   private short removeNullEntry() {
/*  240 */     this.containsNullKey = false;
/*  241 */     short oldValue = this.value[this.n];
/*  242 */     this.size--;
/*  243 */     if (this.n > this.minN && this.size < this.maxFill / 4 && this.n > 16) rehash(this.n / 2); 
/*  244 */     return oldValue;
/*      */   }
/*      */ 
/*      */   
/*      */   public void putAll(Map<? extends Short, ? extends Short> m) {
/*  249 */     if (this.f <= 0.5D) { ensureCapacity(m.size()); }
/*  250 */     else { tryCapacity((size() + m.size())); }
/*      */     
/*  252 */     super.putAll(m);
/*      */   }
/*      */   
/*      */   private int find(short k) {
/*  256 */     if (this.strategy.equals(k, (short)0)) return this.containsNullKey ? this.n : -(this.n + 1);
/*      */     
/*  258 */     short[] key = this.key;
/*      */     short curr;
/*      */     int pos;
/*  261 */     if ((curr = key[pos = HashCommon.mix(this.strategy.hashCode(k)) & this.mask]) == 0) return -(pos + 1); 
/*  262 */     if (this.strategy.equals(k, curr)) return pos;
/*      */     
/*      */     while (true) {
/*  265 */       if ((curr = key[pos = pos + 1 & this.mask]) == 0) return -(pos + 1); 
/*  266 */       if (this.strategy.equals(k, curr)) return pos; 
/*      */     } 
/*      */   }
/*      */   
/*      */   private void insert(int pos, short k, short v) {
/*  271 */     if (pos == this.n) this.containsNullKey = true; 
/*  272 */     this.key[pos] = k;
/*  273 */     this.value[pos] = v;
/*  274 */     if (this.size++ >= this.maxFill) rehash(HashCommon.arraySize(this.size + 1, this.f));
/*      */   
/*      */   }
/*      */ 
/*      */   
/*      */   public short put(short k, short v) {
/*  280 */     int pos = find(k);
/*  281 */     if (pos < 0) {
/*  282 */       insert(-pos - 1, k, v);
/*  283 */       return this.defRetValue;
/*      */     } 
/*  285 */     short oldValue = this.value[pos];
/*  286 */     this.value[pos] = v;
/*  287 */     return oldValue;
/*      */   }
/*      */   
/*      */   private short addToValue(int pos, short incr) {
/*  291 */     short oldValue = this.value[pos];
/*  292 */     this.value[pos] = (short)(oldValue + incr);
/*  293 */     return oldValue;
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
/*      */   public short addTo(short k, short incr) {
/*      */     int pos;
/*  311 */     if (this.strategy.equals(k, (short)0)) {
/*  312 */       if (this.containsNullKey) return addToValue(this.n, incr); 
/*  313 */       pos = this.n;
/*  314 */       this.containsNullKey = true;
/*      */     } else {
/*      */       
/*  317 */       short[] key = this.key;
/*      */       short curr;
/*  319 */       if ((curr = key[pos = HashCommon.mix(this.strategy.hashCode(k)) & this.mask]) != 0) {
/*  320 */         if (this.strategy.equals(curr, k)) return addToValue(pos, incr); 
/*  321 */         while ((curr = key[pos = pos + 1 & this.mask]) != 0) { if (this.strategy.equals(curr, k)) return addToValue(pos, incr);  }
/*      */       
/*      */       } 
/*  324 */     }  this.key[pos] = k;
/*  325 */     this.value[pos] = (short)(this.defRetValue + incr);
/*  326 */     if (this.size++ >= this.maxFill) rehash(HashCommon.arraySize(this.size + 1, this.f));
/*      */     
/*  328 */     return this.defRetValue;
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
/*  341 */     short[] key = this.key; while (true) {
/*      */       short curr; int last;
/*  343 */       pos = (last = pos) + 1 & this.mask;
/*      */       while (true) {
/*  345 */         if ((curr = key[pos]) == 0) {
/*  346 */           key[last] = 0;
/*      */           return;
/*      */         } 
/*  349 */         int slot = HashCommon.mix(this.strategy.hashCode(curr)) & this.mask;
/*  350 */         if ((last <= pos) ? (last >= slot || slot > pos) : (last >= slot && slot > pos))
/*  351 */           break;  pos = pos + 1 & this.mask;
/*      */       } 
/*  353 */       key[last] = curr;
/*  354 */       this.value[last] = this.value[pos];
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public short remove(short k) {
/*  361 */     if (this.strategy.equals(k, (short)0)) {
/*  362 */       if (this.containsNullKey) return removeNullEntry(); 
/*  363 */       return this.defRetValue;
/*      */     } 
/*      */     
/*  366 */     short[] key = this.key;
/*      */     short curr;
/*      */     int pos;
/*  369 */     if ((curr = key[pos = HashCommon.mix(this.strategy.hashCode(k)) & this.mask]) == 0) return this.defRetValue; 
/*  370 */     if (this.strategy.equals(k, curr)) return removeEntry(pos); 
/*      */     while (true) {
/*  372 */       if ((curr = key[pos = pos + 1 & this.mask]) == 0) return this.defRetValue; 
/*  373 */       if (this.strategy.equals(k, curr)) return removeEntry(pos);
/*      */     
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   public short get(short k) {
/*  380 */     if (this.strategy.equals(k, (short)0)) return this.containsNullKey ? this.value[this.n] : this.defRetValue;
/*      */     
/*  382 */     short[] key = this.key;
/*      */     short curr;
/*      */     int pos;
/*  385 */     if ((curr = key[pos = HashCommon.mix(this.strategy.hashCode(k)) & this.mask]) == 0) return this.defRetValue; 
/*  386 */     if (this.strategy.equals(k, curr)) return this.value[pos];
/*      */     
/*      */     while (true) {
/*  389 */       if ((curr = key[pos = pos + 1 & this.mask]) == 0) return this.defRetValue; 
/*  390 */       if (this.strategy.equals(k, curr)) return this.value[pos];
/*      */     
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   public boolean containsKey(short k) {
/*  397 */     if (this.strategy.equals(k, (short)0)) return this.containsNullKey;
/*      */     
/*  399 */     short[] key = this.key;
/*      */     short curr;
/*      */     int pos;
/*  402 */     if ((curr = key[pos = HashCommon.mix(this.strategy.hashCode(k)) & this.mask]) == 0) return false; 
/*  403 */     if (this.strategy.equals(k, curr)) return true;
/*      */     
/*      */     while (true) {
/*  406 */       if ((curr = key[pos = pos + 1 & this.mask]) == 0) return false; 
/*  407 */       if (this.strategy.equals(k, curr)) return true;
/*      */     
/*      */     } 
/*      */   }
/*      */   
/*      */   public boolean containsValue(short v) {
/*  413 */     short[] value = this.value;
/*  414 */     short[] key = this.key;
/*  415 */     if (this.containsNullKey && value[this.n] == v) return true; 
/*  416 */     for (int i = this.n; i-- != 0;) { if (key[i] != 0 && value[i] == v) return true;  }
/*  417 */      return false;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public short getOrDefault(short k, short defaultValue) {
/*  424 */     if (this.strategy.equals(k, (short)0)) return this.containsNullKey ? this.value[this.n] : defaultValue;
/*      */     
/*  426 */     short[] key = this.key;
/*      */     short curr;
/*      */     int pos;
/*  429 */     if ((curr = key[pos = HashCommon.mix(this.strategy.hashCode(k)) & this.mask]) == 0) return defaultValue; 
/*  430 */     if (this.strategy.equals(k, curr)) return this.value[pos];
/*      */     
/*      */     while (true) {
/*  433 */       if ((curr = key[pos = pos + 1 & this.mask]) == 0) return defaultValue; 
/*  434 */       if (this.strategy.equals(k, curr)) return this.value[pos];
/*      */     
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   public short putIfAbsent(short k, short v) {
/*  441 */     int pos = find(k);
/*  442 */     if (pos >= 0) return this.value[pos]; 
/*  443 */     insert(-pos - 1, k, v);
/*  444 */     return this.defRetValue;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean remove(short k, short v) {
/*  451 */     if (this.strategy.equals(k, (short)0)) {
/*  452 */       if (this.containsNullKey && v == this.value[this.n]) {
/*  453 */         removeNullEntry();
/*  454 */         return true;
/*      */       } 
/*  456 */       return false;
/*      */     } 
/*      */     
/*  459 */     short[] key = this.key;
/*      */     short curr;
/*      */     int pos;
/*  462 */     if ((curr = key[pos = HashCommon.mix(this.strategy.hashCode(k)) & this.mask]) == 0) return false; 
/*  463 */     if (this.strategy.equals(k, curr) && v == this.value[pos]) {
/*  464 */       removeEntry(pos);
/*  465 */       return true;
/*      */     } 
/*      */     while (true) {
/*  468 */       if ((curr = key[pos = pos + 1 & this.mask]) == 0) return false; 
/*  469 */       if (this.strategy.equals(k, curr) && v == this.value[pos]) {
/*  470 */         removeEntry(pos);
/*  471 */         return true;
/*      */       } 
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean replace(short k, short oldValue, short v) {
/*  479 */     int pos = find(k);
/*  480 */     if (pos < 0 || oldValue != this.value[pos]) return false; 
/*  481 */     this.value[pos] = v;
/*  482 */     return true;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public short replace(short k, short v) {
/*  488 */     int pos = find(k);
/*  489 */     if (pos < 0) return this.defRetValue; 
/*  490 */     short oldValue = this.value[pos];
/*  491 */     this.value[pos] = v;
/*  492 */     return oldValue;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public short computeIfAbsent(short k, IntUnaryOperator mappingFunction) {
/*  498 */     Objects.requireNonNull(mappingFunction);
/*  499 */     int pos = find(k);
/*  500 */     if (pos >= 0) return this.value[pos]; 
/*  501 */     short newValue = SafeMath.safeIntToShort(mappingFunction.applyAsInt(k));
/*  502 */     insert(-pos - 1, k, newValue);
/*  503 */     return newValue;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public short computeIfAbsent(short key, Short2ShortFunction mappingFunction) {
/*  509 */     Objects.requireNonNull(mappingFunction);
/*  510 */     int pos = find(key);
/*  511 */     if (pos >= 0) return this.value[pos]; 
/*  512 */     if (!mappingFunction.containsKey(key)) return this.defRetValue; 
/*  513 */     short newValue = mappingFunction.get(key);
/*  514 */     insert(-pos - 1, key, newValue);
/*  515 */     return newValue;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public short computeIfAbsentNullable(short k, IntFunction<? extends Short> mappingFunction) {
/*  521 */     Objects.requireNonNull(mappingFunction);
/*  522 */     int pos = find(k);
/*  523 */     if (pos >= 0) return this.value[pos]; 
/*  524 */     Short newValue = mappingFunction.apply(k);
/*  525 */     if (newValue == null) return this.defRetValue; 
/*  526 */     short v = newValue.shortValue();
/*  527 */     insert(-pos - 1, k, v);
/*  528 */     return v;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public short computeIfPresent(short k, BiFunction<? super Short, ? super Short, ? extends Short> remappingFunction) {
/*  534 */     Objects.requireNonNull(remappingFunction);
/*  535 */     int pos = find(k);
/*  536 */     if (pos < 0) return this.defRetValue; 
/*  537 */     Short newValue = remappingFunction.apply(Short.valueOf(k), Short.valueOf(this.value[pos]));
/*  538 */     if (newValue == null) {
/*  539 */       if (this.strategy.equals(k, (short)0)) { removeNullEntry(); }
/*  540 */       else { removeEntry(pos); }
/*  541 */        return this.defRetValue;
/*      */     } 
/*  543 */     this.value[pos] = newValue.shortValue(); return newValue.shortValue();
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public short compute(short k, BiFunction<? super Short, ? super Short, ? extends Short> remappingFunction) {
/*  549 */     Objects.requireNonNull(remappingFunction);
/*  550 */     int pos = find(k);
/*  551 */     Short newValue = remappingFunction.apply(Short.valueOf(k), (pos >= 0) ? Short.valueOf(this.value[pos]) : null);
/*  552 */     if (newValue == null) {
/*  553 */       if (pos >= 0)
/*  554 */         if (this.strategy.equals(k, (short)0)) { removeNullEntry(); }
/*  555 */         else { removeEntry(pos); }
/*      */          
/*  557 */       return this.defRetValue;
/*      */     } 
/*  559 */     short newVal = newValue.shortValue();
/*  560 */     if (pos < 0) {
/*  561 */       insert(-pos - 1, k, newVal);
/*  562 */       return newVal;
/*      */     } 
/*  564 */     this.value[pos] = newVal; return newVal;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public short merge(short k, short v, BiFunction<? super Short, ? super Short, ? extends Short> remappingFunction) {
/*  570 */     Objects.requireNonNull(remappingFunction);
/*      */     
/*  572 */     int pos = find(k);
/*  573 */     if (pos < 0) {
/*  574 */       if (pos < 0) { insert(-pos - 1, k, v); }
/*  575 */       else { this.value[pos] = v; }
/*  576 */        return v;
/*      */     } 
/*  578 */     Short newValue = remappingFunction.apply(Short.valueOf(this.value[pos]), Short.valueOf(v));
/*  579 */     if (newValue == null) {
/*  580 */       if (this.strategy.equals(k, (short)0)) { removeNullEntry(); }
/*  581 */       else { removeEntry(pos); }
/*  582 */        return this.defRetValue;
/*      */     } 
/*  584 */     this.value[pos] = newValue.shortValue(); return newValue.shortValue();
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
/*  595 */     if (this.size == 0)
/*  596 */       return;  this.size = 0;
/*  597 */     this.containsNullKey = false;
/*  598 */     Arrays.fill(this.key, (short)0);
/*      */   }
/*      */ 
/*      */   
/*      */   public int size() {
/*  603 */     return this.size;
/*      */   }
/*      */ 
/*      */   
/*      */   public boolean isEmpty() {
/*  608 */     return (this.size == 0);
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   final class MapEntry
/*      */     implements Short2ShortMap.Entry, Map.Entry<Short, Short>, ShortShortPair
/*      */   {
/*      */     int index;
/*      */ 
/*      */ 
/*      */     
/*      */     MapEntry(int index) {
/*  621 */       this.index = index;
/*      */     }
/*      */ 
/*      */     
/*      */     MapEntry() {}
/*      */ 
/*      */     
/*      */     public short getShortKey() {
/*  629 */       return Short2ShortOpenCustomHashMap.this.key[this.index];
/*      */     }
/*      */ 
/*      */     
/*      */     public short leftShort() {
/*  634 */       return Short2ShortOpenCustomHashMap.this.key[this.index];
/*      */     }
/*      */ 
/*      */     
/*      */     public short getShortValue() {
/*  639 */       return Short2ShortOpenCustomHashMap.this.value[this.index];
/*      */     }
/*      */ 
/*      */     
/*      */     public short rightShort() {
/*  644 */       return Short2ShortOpenCustomHashMap.this.value[this.index];
/*      */     }
/*      */ 
/*      */     
/*      */     public short setValue(short v) {
/*  649 */       short oldValue = Short2ShortOpenCustomHashMap.this.value[this.index];
/*  650 */       Short2ShortOpenCustomHashMap.this.value[this.index] = v;
/*  651 */       return oldValue;
/*      */     }
/*      */ 
/*      */     
/*      */     public ShortShortPair right(short v) {
/*  656 */       Short2ShortOpenCustomHashMap.this.value[this.index] = v;
/*  657 */       return this;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     @Deprecated
/*      */     public Short getKey() {
/*  668 */       return Short.valueOf(Short2ShortOpenCustomHashMap.this.key[this.index]);
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
/*  679 */       return Short.valueOf(Short2ShortOpenCustomHashMap.this.value[this.index]);
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
/*  690 */       return Short.valueOf(setValue(v.shortValue()));
/*      */     }
/*      */ 
/*      */ 
/*      */     
/*      */     public boolean equals(Object o) {
/*  696 */       if (!(o instanceof Map.Entry)) return false; 
/*  697 */       Map.Entry<Short, Short> e = (Map.Entry<Short, Short>)o;
/*  698 */       return (Short2ShortOpenCustomHashMap.this.strategy.equals(Short2ShortOpenCustomHashMap.this.key[this.index], ((Short)e.getKey()).shortValue()) && Short2ShortOpenCustomHashMap.this.value[this.index] == ((Short)e.getValue()).shortValue());
/*      */     }
/*      */ 
/*      */     
/*      */     public int hashCode() {
/*  703 */       return Short2ShortOpenCustomHashMap.this.strategy.hashCode(Short2ShortOpenCustomHashMap.this.key[this.index]) ^ Short2ShortOpenCustomHashMap.this.value[this.index];
/*      */     }
/*      */ 
/*      */     
/*      */     public String toString() {
/*  708 */       return Short2ShortOpenCustomHashMap.this.key[this.index] + "=>" + Short2ShortOpenCustomHashMap.this.value[this.index];
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
/*  719 */     int pos = Short2ShortOpenCustomHashMap.this.n;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  725 */     int last = -1;
/*      */     
/*  727 */     int c = Short2ShortOpenCustomHashMap.this.size;
/*      */     
/*  729 */     boolean mustReturnNullKey = Short2ShortOpenCustomHashMap.this.containsNullKey;
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     ShortArrayList wrapped;
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public boolean hasNext() {
/*  740 */       return (this.c != 0);
/*      */     }
/*      */     
/*      */     public int nextEntry() {
/*  744 */       if (!hasNext()) throw new NoSuchElementException(); 
/*  745 */       this.c--;
/*  746 */       if (this.mustReturnNullKey) {
/*  747 */         this.mustReturnNullKey = false;
/*  748 */         return this.last = Short2ShortOpenCustomHashMap.this.n;
/*      */       } 
/*  750 */       short[] key = Short2ShortOpenCustomHashMap.this.key;
/*      */       while (true) {
/*  752 */         if (--this.pos < 0) {
/*      */           
/*  754 */           this.last = Integer.MIN_VALUE;
/*  755 */           short k = this.wrapped.getShort(-this.pos - 1);
/*  756 */           int p = HashCommon.mix(Short2ShortOpenCustomHashMap.this.strategy.hashCode(k)) & Short2ShortOpenCustomHashMap.this.mask;
/*  757 */           for (; !Short2ShortOpenCustomHashMap.this.strategy.equals(k, key[p]); p = p + 1 & Short2ShortOpenCustomHashMap.this.mask);
/*  758 */           return p;
/*      */         } 
/*  760 */         if (key[this.pos] != 0) return this.last = this.pos; 
/*      */       } 
/*      */     }
/*      */     
/*      */     public void forEachRemaining(ConsumerType action) {
/*  765 */       if (this.mustReturnNullKey) {
/*  766 */         this.mustReturnNullKey = false;
/*  767 */         acceptOnIndex(action, this.last = Short2ShortOpenCustomHashMap.this.n);
/*  768 */         this.c--;
/*      */       } 
/*  770 */       short[] key = Short2ShortOpenCustomHashMap.this.key;
/*  771 */       while (this.c != 0) {
/*  772 */         if (--this.pos < 0) {
/*      */           
/*  774 */           this.last = Integer.MIN_VALUE;
/*  775 */           short k = this.wrapped.getShort(-this.pos - 1);
/*  776 */           int p = HashCommon.mix(Short2ShortOpenCustomHashMap.this.strategy.hashCode(k)) & Short2ShortOpenCustomHashMap.this.mask;
/*  777 */           for (; !Short2ShortOpenCustomHashMap.this.strategy.equals(k, key[p]); p = p + 1 & Short2ShortOpenCustomHashMap.this.mask);
/*  778 */           acceptOnIndex(action, p);
/*  779 */           this.c--; continue;
/*  780 */         }  if (key[this.pos] != 0) {
/*  781 */           acceptOnIndex(action, this.last = this.pos);
/*  782 */           this.c--;
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
/*  797 */       short[] key = Short2ShortOpenCustomHashMap.this.key; while (true) {
/*      */         short curr; int last;
/*  799 */         pos = (last = pos) + 1 & Short2ShortOpenCustomHashMap.this.mask;
/*      */         while (true) {
/*  801 */           if ((curr = key[pos]) == 0) {
/*  802 */             key[last] = 0;
/*      */             return;
/*      */           } 
/*  805 */           int slot = HashCommon.mix(Short2ShortOpenCustomHashMap.this.strategy.hashCode(curr)) & Short2ShortOpenCustomHashMap.this.mask;
/*  806 */           if ((last <= pos) ? (last >= slot || slot > pos) : (last >= slot && slot > pos))
/*  807 */             break;  pos = pos + 1 & Short2ShortOpenCustomHashMap.this.mask;
/*      */         } 
/*  809 */         if (pos < last) {
/*  810 */           if (this.wrapped == null) this.wrapped = new ShortArrayList(2); 
/*  811 */           this.wrapped.add(key[pos]);
/*      */         } 
/*  813 */         key[last] = curr;
/*  814 */         Short2ShortOpenCustomHashMap.this.value[last] = Short2ShortOpenCustomHashMap.this.value[pos];
/*      */       } 
/*      */     }
/*      */     
/*      */     public void remove() {
/*  819 */       if (this.last == -1) throw new IllegalStateException(); 
/*  820 */       if (this.last == Short2ShortOpenCustomHashMap.this.n)
/*  821 */       { Short2ShortOpenCustomHashMap.this.containsNullKey = false; }
/*  822 */       else if (this.pos >= 0) { shiftKeys(this.last); }
/*      */       else
/*      */       
/*  825 */       { Short2ShortOpenCustomHashMap.this.remove(this.wrapped.getShort(-this.pos - 1));
/*  826 */         this.last = -1;
/*      */         return; }
/*      */       
/*  829 */       Short2ShortOpenCustomHashMap.this.size--;
/*  830 */       this.last = -1;
/*      */     }
/*      */ 
/*      */     
/*      */     public int skip(int n) {
/*  835 */       int i = n;
/*  836 */       for (; i-- != 0 && hasNext(); nextEntry());
/*  837 */       return n - i - 1;
/*      */     }
/*      */     
/*      */     private MapIterator() {}
/*      */     
/*      */     abstract void acceptOnIndex(ConsumerType param1ConsumerType, int param1Int);
/*      */   }
/*      */   
/*      */   private final class EntryIterator extends MapIterator<Consumer<? super Short2ShortMap.Entry>> implements ObjectIterator<Short2ShortMap.Entry> { public Short2ShortOpenCustomHashMap.MapEntry next() {
/*  846 */       return this.entry = new Short2ShortOpenCustomHashMap.MapEntry(nextEntry());
/*      */     }
/*      */     private Short2ShortOpenCustomHashMap.MapEntry entry;
/*      */     private EntryIterator() {}
/*      */     
/*      */     final void acceptOnIndex(Consumer<? super Short2ShortMap.Entry> action, int index) {
/*  852 */       action.accept(this.entry = new Short2ShortOpenCustomHashMap.MapEntry(index));
/*      */     }
/*      */ 
/*      */     
/*      */     public void remove() {
/*  857 */       super.remove();
/*  858 */       this.entry.index = -1;
/*      */     } }
/*      */   
/*      */   private final class FastEntryIterator extends MapIterator<Consumer<? super Short2ShortMap.Entry>> implements ObjectIterator<Short2ShortMap.Entry> {
/*      */     private FastEntryIterator() {
/*  863 */       this.entry = new Short2ShortOpenCustomHashMap.MapEntry();
/*      */     }
/*      */     private final Short2ShortOpenCustomHashMap.MapEntry entry;
/*      */     public Short2ShortOpenCustomHashMap.MapEntry next() {
/*  867 */       this.entry.index = nextEntry();
/*  868 */       return this.entry;
/*      */     }
/*      */ 
/*      */ 
/*      */     
/*      */     final void acceptOnIndex(Consumer<? super Short2ShortMap.Entry> action, int index) {
/*  874 */       this.entry.index = index;
/*  875 */       action.accept(this.entry);
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private abstract class MapSpliterator<ConsumerType, SplitType extends MapSpliterator<ConsumerType, SplitType>>
/*      */   {
/*  884 */     int pos = 0;
/*      */     
/*  886 */     int max = Short2ShortOpenCustomHashMap.this.n;
/*      */     
/*  888 */     int c = 0;
/*      */     
/*  890 */     boolean mustReturnNull = Short2ShortOpenCustomHashMap.this.containsNullKey;
/*      */     
/*      */     boolean hasSplit = false;
/*      */     
/*      */     MapSpliterator() {}
/*      */     
/*      */     MapSpliterator(int pos, int max, boolean mustReturnNull, boolean hasSplit) {
/*  897 */       this.pos = pos;
/*  898 */       this.max = max;
/*  899 */       this.mustReturnNull = mustReturnNull;
/*  900 */       this.hasSplit = hasSplit;
/*      */     }
/*      */     
/*      */     abstract void acceptOnIndex(ConsumerType param1ConsumerType, int param1Int);
/*      */     
/*      */     abstract SplitType makeForSplit(int param1Int1, int param1Int2, boolean param1Boolean);
/*      */     
/*      */     public boolean tryAdvance(ConsumerType action) {
/*  908 */       if (this.mustReturnNull) {
/*  909 */         this.mustReturnNull = false;
/*  910 */         this.c++;
/*  911 */         acceptOnIndex(action, Short2ShortOpenCustomHashMap.this.n);
/*  912 */         return true;
/*      */       } 
/*  914 */       short[] key = Short2ShortOpenCustomHashMap.this.key;
/*  915 */       while (this.pos < this.max) {
/*  916 */         if (key[this.pos] != 0) {
/*  917 */           this.c++;
/*  918 */           acceptOnIndex(action, this.pos++);
/*  919 */           return true;
/*      */         } 
/*  921 */         this.pos++;
/*      */       } 
/*  923 */       return false;
/*      */     }
/*      */     
/*      */     public void forEachRemaining(ConsumerType action) {
/*  927 */       if (this.mustReturnNull) {
/*  928 */         this.mustReturnNull = false;
/*  929 */         this.c++;
/*  930 */         acceptOnIndex(action, Short2ShortOpenCustomHashMap.this.n);
/*      */       } 
/*  932 */       short[] key = Short2ShortOpenCustomHashMap.this.key;
/*  933 */       while (this.pos < this.max) {
/*  934 */         if (key[this.pos] != 0) {
/*  935 */           acceptOnIndex(action, this.pos);
/*  936 */           this.c++;
/*      */         } 
/*  938 */         this.pos++;
/*      */       } 
/*      */     }
/*      */     
/*      */     public long estimateSize() {
/*  943 */       if (!this.hasSplit)
/*      */       {
/*  945 */         return (Short2ShortOpenCustomHashMap.this.size - this.c);
/*      */       }
/*      */ 
/*      */ 
/*      */       
/*  950 */       return Math.min((Short2ShortOpenCustomHashMap.this.size - this.c), (long)(Short2ShortOpenCustomHashMap.this.realSize() / Short2ShortOpenCustomHashMap.this.n * (this.max - this.pos)) + (this.mustReturnNull ? 1L : 0L));
/*      */     }
/*      */ 
/*      */     
/*      */     public SplitType trySplit() {
/*  955 */       if (this.pos >= this.max - 1) return null; 
/*  956 */       int retLen = this.max - this.pos >> 1;
/*  957 */       if (retLen <= 1) return null; 
/*  958 */       int myNewPos = this.pos + retLen;
/*  959 */       int retPos = this.pos;
/*  960 */       int retMax = myNewPos;
/*      */ 
/*      */ 
/*      */       
/*  964 */       SplitType split = makeForSplit(retPos, retMax, this.mustReturnNull);
/*  965 */       this.pos = myNewPos;
/*  966 */       this.mustReturnNull = false;
/*  967 */       this.hasSplit = true;
/*  968 */       return split;
/*      */     }
/*      */     
/*      */     public long skip(long n) {
/*  972 */       if (n < 0L) throw new IllegalArgumentException("Argument must be nonnegative: " + n); 
/*  973 */       if (n == 0L) return 0L; 
/*  974 */       long skipped = 0L;
/*  975 */       if (this.mustReturnNull) {
/*  976 */         this.mustReturnNull = false;
/*  977 */         skipped++;
/*  978 */         n--;
/*      */       } 
/*  980 */       short[] key = Short2ShortOpenCustomHashMap.this.key;
/*  981 */       while (this.pos < this.max && n > 0L) {
/*  982 */         if (key[this.pos++] != 0) {
/*  983 */           skipped++;
/*  984 */           n--;
/*      */         } 
/*      */       } 
/*  987 */       return skipped;
/*      */     }
/*      */   }
/*      */   
/*      */   private final class EntrySpliterator
/*      */     extends MapSpliterator<Consumer<? super Short2ShortMap.Entry>, EntrySpliterator> implements ObjectSpliterator<Short2ShortMap.Entry> {
/*      */     private static final int POST_SPLIT_CHARACTERISTICS = 1;
/*      */     
/*      */     EntrySpliterator() {}
/*      */     
/*      */     EntrySpliterator(int pos, int max, boolean mustReturnNull, boolean hasSplit) {
/*  998 */       super(pos, max, mustReturnNull, hasSplit);
/*      */     }
/*      */ 
/*      */     
/*      */     public int characteristics() {
/* 1003 */       return this.hasSplit ? 1 : 65;
/*      */     }
/*      */ 
/*      */     
/*      */     final void acceptOnIndex(Consumer<? super Short2ShortMap.Entry> action, int index) {
/* 1008 */       action.accept(new Short2ShortOpenCustomHashMap.MapEntry(index));
/*      */     }
/*      */ 
/*      */     
/*      */     final EntrySpliterator makeForSplit(int pos, int max, boolean mustReturnNull) {
/* 1013 */       return new EntrySpliterator(pos, max, mustReturnNull, true);
/*      */     } }
/*      */   
/*      */   private final class MapEntrySet extends AbstractObjectSet<Short2ShortMap.Entry> implements Short2ShortMap.FastEntrySet {
/*      */     private MapEntrySet() {}
/*      */     
/*      */     public ObjectIterator<Short2ShortMap.Entry> iterator() {
/* 1020 */       return new Short2ShortOpenCustomHashMap.EntryIterator();
/*      */     }
/*      */ 
/*      */     
/*      */     public ObjectIterator<Short2ShortMap.Entry> fastIterator() {
/* 1025 */       return new Short2ShortOpenCustomHashMap.FastEntryIterator();
/*      */     }
/*      */ 
/*      */     
/*      */     public ObjectSpliterator<Short2ShortMap.Entry> spliterator() {
/* 1030 */       return new Short2ShortOpenCustomHashMap.EntrySpliterator();
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public boolean contains(Object o) {
/* 1037 */       if (!(o instanceof Map.Entry)) return false; 
/* 1038 */       Map.Entry<?, ?> e = (Map.Entry<?, ?>)o;
/* 1039 */       if (e.getKey() == null || !(e.getKey() instanceof Short)) return false; 
/* 1040 */       if (e.getValue() == null || !(e.getValue() instanceof Short)) return false; 
/* 1041 */       short k = ((Short)e.getKey()).shortValue();
/* 1042 */       short v = ((Short)e.getValue()).shortValue();
/* 1043 */       if (Short2ShortOpenCustomHashMap.this.strategy.equals(k, (short)0)) return (Short2ShortOpenCustomHashMap.this.containsNullKey && Short2ShortOpenCustomHashMap.this.value[Short2ShortOpenCustomHashMap.this.n] == v);
/*      */       
/* 1045 */       short[] key = Short2ShortOpenCustomHashMap.this.key;
/*      */       short curr;
/*      */       int pos;
/* 1048 */       if ((curr = key[pos = HashCommon.mix(Short2ShortOpenCustomHashMap.this.strategy.hashCode(k)) & Short2ShortOpenCustomHashMap.this.mask]) == 0) return false; 
/* 1049 */       if (Short2ShortOpenCustomHashMap.this.strategy.equals(k, curr)) return (Short2ShortOpenCustomHashMap.this.value[pos] == v);
/*      */       
/*      */       while (true) {
/* 1052 */         if ((curr = key[pos = pos + 1 & Short2ShortOpenCustomHashMap.this.mask]) == 0) return false; 
/* 1053 */         if (Short2ShortOpenCustomHashMap.this.strategy.equals(k, curr)) return (Short2ShortOpenCustomHashMap.this.value[pos] == v);
/*      */       
/*      */       } 
/*      */     }
/*      */ 
/*      */     
/*      */     public boolean remove(Object o) {
/* 1060 */       if (!(o instanceof Map.Entry)) return false; 
/* 1061 */       Map.Entry<?, ?> e = (Map.Entry<?, ?>)o;
/* 1062 */       if (e.getKey() == null || !(e.getKey() instanceof Short)) return false; 
/* 1063 */       if (e.getValue() == null || !(e.getValue() instanceof Short)) return false; 
/* 1064 */       short k = ((Short)e.getKey()).shortValue();
/* 1065 */       short v = ((Short)e.getValue()).shortValue();
/* 1066 */       if (Short2ShortOpenCustomHashMap.this.strategy.equals(k, (short)0)) {
/* 1067 */         if (Short2ShortOpenCustomHashMap.this.containsNullKey && Short2ShortOpenCustomHashMap.this.value[Short2ShortOpenCustomHashMap.this.n] == v) {
/* 1068 */           Short2ShortOpenCustomHashMap.this.removeNullEntry();
/* 1069 */           return true;
/*      */         } 
/* 1071 */         return false;
/*      */       } 
/*      */       
/* 1074 */       short[] key = Short2ShortOpenCustomHashMap.this.key;
/*      */       short curr;
/*      */       int pos;
/* 1077 */       if ((curr = key[pos = HashCommon.mix(Short2ShortOpenCustomHashMap.this.strategy.hashCode(k)) & Short2ShortOpenCustomHashMap.this.mask]) == 0) return false; 
/* 1078 */       if (Short2ShortOpenCustomHashMap.this.strategy.equals(curr, k)) {
/* 1079 */         if (Short2ShortOpenCustomHashMap.this.value[pos] == v) {
/* 1080 */           Short2ShortOpenCustomHashMap.this.removeEntry(pos);
/* 1081 */           return true;
/*      */         } 
/* 1083 */         return false;
/*      */       } 
/*      */       while (true) {
/* 1086 */         if ((curr = key[pos = pos + 1 & Short2ShortOpenCustomHashMap.this.mask]) == 0) return false; 
/* 1087 */         if (Short2ShortOpenCustomHashMap.this.strategy.equals(curr, k) && 
/* 1088 */           Short2ShortOpenCustomHashMap.this.value[pos] == v) {
/* 1089 */           Short2ShortOpenCustomHashMap.this.removeEntry(pos);
/* 1090 */           return true;
/*      */         } 
/*      */       } 
/*      */     }
/*      */ 
/*      */ 
/*      */     
/*      */     public int size() {
/* 1098 */       return Short2ShortOpenCustomHashMap.this.size;
/*      */     }
/*      */ 
/*      */     
/*      */     public void clear() {
/* 1103 */       Short2ShortOpenCustomHashMap.this.clear();
/*      */     }
/*      */ 
/*      */ 
/*      */     
/*      */     public void forEach(Consumer<? super Short2ShortMap.Entry> consumer) {
/* 1109 */       if (Short2ShortOpenCustomHashMap.this.containsNullKey) consumer.accept(new Short2ShortOpenCustomHashMap.MapEntry(Short2ShortOpenCustomHashMap.this.n)); 
/* 1110 */       for (int pos = Short2ShortOpenCustomHashMap.this.n; pos-- != 0;) { if (Short2ShortOpenCustomHashMap.this.key[pos] != 0) consumer.accept(new Short2ShortOpenCustomHashMap.MapEntry(pos));
/*      */          }
/*      */     
/*      */     }
/*      */     
/*      */     public void fastForEach(Consumer<? super Short2ShortMap.Entry> consumer) {
/* 1116 */       Short2ShortOpenCustomHashMap.MapEntry entry = new Short2ShortOpenCustomHashMap.MapEntry();
/* 1117 */       if (Short2ShortOpenCustomHashMap.this.containsNullKey) {
/* 1118 */         entry.index = Short2ShortOpenCustomHashMap.this.n;
/* 1119 */         consumer.accept(entry);
/*      */       } 
/* 1121 */       for (int pos = Short2ShortOpenCustomHashMap.this.n; pos-- != 0;) { if (Short2ShortOpenCustomHashMap.this.key[pos] != 0) {
/* 1122 */           entry.index = pos;
/* 1123 */           consumer.accept(entry);
/*      */         }  }
/*      */     
/*      */     }
/*      */   }
/*      */   
/*      */   public Short2ShortMap.FastEntrySet short2ShortEntrySet() {
/* 1130 */     if (this.entries == null) this.entries = new MapEntrySet(); 
/* 1131 */     return this.entries;
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
/*      */     extends MapIterator<ShortConsumer>
/*      */     implements ShortIterator
/*      */   {
/*      */     final void acceptOnIndex(ShortConsumer action, int index) {
/* 1152 */       action.accept(Short2ShortOpenCustomHashMap.this.key[index]);
/*      */     }
/*      */ 
/*      */     
/*      */     public short nextShort() {
/* 1157 */       return Short2ShortOpenCustomHashMap.this.key[nextEntry()];
/*      */     }
/*      */   }
/*      */   
/*      */   private final class KeySpliterator
/*      */     extends MapSpliterator<ShortConsumer, KeySpliterator> implements ShortSpliterator {
/*      */     private static final int POST_SPLIT_CHARACTERISTICS = 257;
/*      */     
/*      */     KeySpliterator() {}
/*      */     
/*      */     KeySpliterator(int pos, int max, boolean mustReturnNull, boolean hasSplit) {
/* 1168 */       super(pos, max, mustReturnNull, hasSplit);
/*      */     }
/*      */ 
/*      */     
/*      */     public int characteristics() {
/* 1173 */       return this.hasSplit ? 257 : 321;
/*      */     }
/*      */ 
/*      */     
/*      */     final void acceptOnIndex(ShortConsumer action, int index) {
/* 1178 */       action.accept(Short2ShortOpenCustomHashMap.this.key[index]);
/*      */     }
/*      */ 
/*      */     
/*      */     final KeySpliterator makeForSplit(int pos, int max, boolean mustReturnNull) {
/* 1183 */       return new KeySpliterator(pos, max, mustReturnNull, true);
/*      */     } }
/*      */   
/*      */   private final class KeySet extends AbstractShortSet {
/*      */     private KeySet() {}
/*      */     
/*      */     public ShortIterator iterator() {
/* 1190 */       return new Short2ShortOpenCustomHashMap.KeyIterator();
/*      */     }
/*      */ 
/*      */     
/*      */     public ShortSpliterator spliterator() {
/* 1195 */       return new Short2ShortOpenCustomHashMap.KeySpliterator();
/*      */     }
/*      */ 
/*      */ 
/*      */     
/*      */     public void forEach(ShortConsumer consumer) {
/* 1201 */       if (Short2ShortOpenCustomHashMap.this.containsNullKey) consumer.accept(Short2ShortOpenCustomHashMap.this.key[Short2ShortOpenCustomHashMap.this.n]); 
/* 1202 */       for (int pos = Short2ShortOpenCustomHashMap.this.n; pos-- != 0; ) {
/* 1203 */         short k = Short2ShortOpenCustomHashMap.this.key[pos];
/* 1204 */         if (k != 0) consumer.accept(k);
/*      */       
/*      */       } 
/*      */     }
/*      */     
/*      */     public int size() {
/* 1210 */       return Short2ShortOpenCustomHashMap.this.size;
/*      */     }
/*      */ 
/*      */     
/*      */     public boolean contains(short k) {
/* 1215 */       return Short2ShortOpenCustomHashMap.this.containsKey(k);
/*      */     }
/*      */ 
/*      */     
/*      */     public boolean remove(short k) {
/* 1220 */       int oldSize = Short2ShortOpenCustomHashMap.this.size;
/* 1221 */       Short2ShortOpenCustomHashMap.this.remove(k);
/* 1222 */       return (Short2ShortOpenCustomHashMap.this.size != oldSize);
/*      */     }
/*      */ 
/*      */     
/*      */     public void clear() {
/* 1227 */       Short2ShortOpenCustomHashMap.this.clear();
/*      */     }
/*      */   }
/*      */ 
/*      */   
/*      */   public ShortSet keySet() {
/* 1233 */     if (this.keys == null) this.keys = new KeySet(); 
/* 1234 */     return this.keys;
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
/*      */     extends MapIterator<ShortConsumer>
/*      */     implements ShortIterator
/*      */   {
/*      */     final void acceptOnIndex(ShortConsumer action, int index) {
/* 1255 */       action.accept(Short2ShortOpenCustomHashMap.this.value[index]);
/*      */     }
/*      */ 
/*      */     
/*      */     public short nextShort() {
/* 1260 */       return Short2ShortOpenCustomHashMap.this.value[nextEntry()];
/*      */     }
/*      */   }
/*      */   
/*      */   private final class ValueSpliterator
/*      */     extends MapSpliterator<ShortConsumer, ValueSpliterator> implements ShortSpliterator {
/*      */     private static final int POST_SPLIT_CHARACTERISTICS = 256;
/*      */     
/*      */     ValueSpliterator() {}
/*      */     
/*      */     ValueSpliterator(int pos, int max, boolean mustReturnNull, boolean hasSplit) {
/* 1271 */       super(pos, max, mustReturnNull, hasSplit);
/*      */     }
/*      */ 
/*      */     
/*      */     public int characteristics() {
/* 1276 */       return this.hasSplit ? 256 : 320;
/*      */     }
/*      */ 
/*      */     
/*      */     final void acceptOnIndex(ShortConsumer action, int index) {
/* 1281 */       action.accept(Short2ShortOpenCustomHashMap.this.value[index]);
/*      */     }
/*      */ 
/*      */     
/*      */     final ValueSpliterator makeForSplit(int pos, int max, boolean mustReturnNull) {
/* 1286 */       return new ValueSpliterator(pos, max, mustReturnNull, true);
/*      */     }
/*      */   }
/*      */ 
/*      */   
/*      */   public ShortCollection values() {
/* 1292 */     if (this.values == null) this.values = new AbstractShortCollection()
/*      */         {
/*      */           public ShortIterator iterator() {
/* 1295 */             return new Short2ShortOpenCustomHashMap.ValueIterator();
/*      */           }
/*      */ 
/*      */           
/*      */           public ShortSpliterator spliterator() {
/* 1300 */             return new Short2ShortOpenCustomHashMap.ValueSpliterator();
/*      */           }
/*      */ 
/*      */ 
/*      */           
/*      */           public void forEach(ShortConsumer consumer) {
/* 1306 */             if (Short2ShortOpenCustomHashMap.this.containsNullKey) consumer.accept(Short2ShortOpenCustomHashMap.this.value[Short2ShortOpenCustomHashMap.this.n]); 
/* 1307 */             for (int pos = Short2ShortOpenCustomHashMap.this.n; pos-- != 0;) { if (Short2ShortOpenCustomHashMap.this.key[pos] != 0) consumer.accept(Short2ShortOpenCustomHashMap.this.value[pos]);  }
/*      */           
/*      */           }
/*      */           
/*      */           public int size() {
/* 1312 */             return Short2ShortOpenCustomHashMap.this.size;
/*      */           }
/*      */ 
/*      */           
/*      */           public boolean contains(short v) {
/* 1317 */             return Short2ShortOpenCustomHashMap.this.containsValue(v);
/*      */           }
/*      */ 
/*      */           
/*      */           public void clear() {
/* 1322 */             Short2ShortOpenCustomHashMap.this.clear();
/*      */           }
/*      */         }; 
/* 1325 */     return this.values;
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
/* 1342 */     return trim(this.size);
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
/* 1364 */     int l = HashCommon.nextPowerOfTwo((int)Math.ceil((n / this.f)));
/* 1365 */     if (l >= this.n || this.size > HashCommon.maxFill(l, this.f)) return true; 
/*      */     try {
/* 1367 */       rehash(l);
/* 1368 */     } catch (OutOfMemoryError cantDoIt) {
/* 1369 */       return false;
/*      */     } 
/* 1371 */     return true;
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
/* 1386 */     short[] key = this.key;
/* 1387 */     short[] value = this.value;
/* 1388 */     int mask = newN - 1;
/* 1389 */     short[] newKey = new short[newN + 1];
/* 1390 */     short[] newValue = new short[newN + 1];
/* 1391 */     int i = this.n;
/* 1392 */     for (int j = realSize(); j-- != 0; ) {
/* 1393 */       while (key[--i] == 0); int pos;
/* 1394 */       if (newKey[pos = HashCommon.mix(this.strategy.hashCode(key[i])) & mask] != 0) while (newKey[pos = pos + 1 & mask] != 0); 
/* 1395 */       newKey[pos] = key[i];
/* 1396 */       newValue[pos] = value[i];
/*      */     } 
/* 1398 */     newValue[newN] = value[this.n];
/* 1399 */     this.n = newN;
/* 1400 */     this.mask = mask;
/* 1401 */     this.maxFill = HashCommon.maxFill(this.n, this.f);
/* 1402 */     this.key = newKey;
/* 1403 */     this.value = newValue;
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
/*      */   public Short2ShortOpenCustomHashMap clone() {
/*      */     Short2ShortOpenCustomHashMap c;
/*      */     try {
/* 1420 */       c = (Short2ShortOpenCustomHashMap)super.clone();
/* 1421 */     } catch (CloneNotSupportedException cantHappen) {
/* 1422 */       throw new InternalError();
/*      */     } 
/* 1424 */     c.keys = null;
/* 1425 */     c.values = null;
/* 1426 */     c.entries = null;
/* 1427 */     c.containsNullKey = this.containsNullKey;
/* 1428 */     c.key = (short[])this.key.clone();
/* 1429 */     c.value = (short[])this.value.clone();
/* 1430 */     c.strategy = this.strategy;
/* 1431 */     return c;
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
/* 1445 */     int h = 0;
/* 1446 */     for (int j = realSize(), i = 0, t = 0; j-- != 0; ) {
/* 1447 */       for (; this.key[i] == 0; i++);
/* 1448 */       t = this.strategy.hashCode(this.key[i]);
/* 1449 */       t ^= this.value[i];
/* 1450 */       h += t;
/* 1451 */       i++;
/*      */     } 
/*      */     
/* 1454 */     if (this.containsNullKey) h += this.value[this.n]; 
/* 1455 */     return h;
/*      */   }
/*      */   
/*      */   private void writeObject(ObjectOutputStream s) throws IOException {
/* 1459 */     short[] key = this.key;
/* 1460 */     short[] value = this.value;
/* 1461 */     EntryIterator i = new EntryIterator();
/* 1462 */     s.defaultWriteObject();
/* 1463 */     for (int j = this.size; j-- != 0; ) {
/* 1464 */       int e = i.nextEntry();
/* 1465 */       s.writeShort(key[e]);
/* 1466 */       s.writeShort(value[e]);
/*      */     } 
/*      */   }
/*      */   
/*      */   private void readObject(ObjectInputStream s) throws IOException, ClassNotFoundException {
/* 1471 */     s.defaultReadObject();
/* 1472 */     this.n = HashCommon.arraySize(this.size, this.f);
/* 1473 */     this.maxFill = HashCommon.maxFill(this.n, this.f);
/* 1474 */     this.mask = this.n - 1;
/* 1475 */     short[] key = this.key = new short[this.n + 1];
/* 1476 */     short[] value = this.value = new short[this.n + 1];
/*      */ 
/*      */     
/* 1479 */     for (int i = this.size; i-- != 0; ) {
/* 1480 */       int pos; short k = s.readShort();
/* 1481 */       short v = s.readShort();
/* 1482 */       if (this.strategy.equals(k, (short)0)) {
/* 1483 */         pos = this.n;
/* 1484 */         this.containsNullKey = true;
/*      */       } else {
/* 1486 */         pos = HashCommon.mix(this.strategy.hashCode(k)) & this.mask;
/* 1487 */         for (; key[pos] != 0; pos = pos + 1 & this.mask);
/*      */       } 
/* 1489 */       key[pos] = k;
/* 1490 */       value[pos] = v;
/*      */     } 
/*      */   }
/*      */   
/*      */   private void checkTable() {}
/*      */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\i\\unimi\dsi\fastutil\shorts\Short2ShortOpenCustomHashMap.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */