/*      */ package it.unimi.dsi.fastutil.longs;
/*      */ 
/*      */ import it.unimi.dsi.fastutil.Hash;
/*      */ import it.unimi.dsi.fastutil.HashCommon;
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
/*      */ import java.util.function.LongConsumer;
/*      */ import java.util.function.LongFunction;
/*      */ import java.util.function.LongUnaryOperator;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ public class Long2LongOpenCustomHashMap
/*      */   extends AbstractLong2LongMap
/*      */   implements Serializable, Cloneable, Hash
/*      */ {
/*      */   private static final long serialVersionUID = 0L;
/*      */   private static final boolean ASSERTS = false;
/*      */   protected transient long[] key;
/*      */   protected transient long[] value;
/*      */   protected transient int mask;
/*      */   protected transient boolean containsNullKey;
/*      */   protected LongHash.Strategy strategy;
/*      */   protected transient int n;
/*      */   protected transient int maxFill;
/*      */   protected final transient int minN;
/*      */   protected int size;
/*      */   protected final float f;
/*      */   protected transient Long2LongMap.FastEntrySet entries;
/*      */   protected transient LongSet keys;
/*      */   protected transient LongCollection values;
/*      */   
/*      */   public Long2LongOpenCustomHashMap(int expected, float f, LongHash.Strategy strategy) {
/*   98 */     this.strategy = strategy;
/*   99 */     if (f <= 0.0F || f >= 1.0F) throw new IllegalArgumentException("Load factor must be greater than 0 and smaller than 1"); 
/*  100 */     if (expected < 0) throw new IllegalArgumentException("The expected number of elements must be nonnegative"); 
/*  101 */     this.f = f;
/*  102 */     this.minN = this.n = HashCommon.arraySize(expected, f);
/*  103 */     this.mask = this.n - 1;
/*  104 */     this.maxFill = HashCommon.maxFill(this.n, f);
/*  105 */     this.key = new long[this.n + 1];
/*  106 */     this.value = new long[this.n + 1];
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Long2LongOpenCustomHashMap(int expected, LongHash.Strategy strategy) {
/*  116 */     this(expected, 0.75F, strategy);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Long2LongOpenCustomHashMap(LongHash.Strategy strategy) {
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
/*      */   public Long2LongOpenCustomHashMap(Map<? extends Long, ? extends Long> m, float f, LongHash.Strategy strategy) {
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
/*      */   public Long2LongOpenCustomHashMap(Map<? extends Long, ? extends Long> m, LongHash.Strategy strategy) {
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
/*      */   public Long2LongOpenCustomHashMap(Long2LongMap m, float f, LongHash.Strategy strategy) {
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
/*      */   public Long2LongOpenCustomHashMap(Long2LongMap m, LongHash.Strategy strategy) {
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
/*      */   public Long2LongOpenCustomHashMap(long[] k, long[] v, float f, LongHash.Strategy strategy) {
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
/*      */   public Long2LongOpenCustomHashMap(long[] k, long[] v, LongHash.Strategy strategy) {
/*  199 */     this(k, v, 0.75F, strategy);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public LongHash.Strategy strategy() {
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
/*      */   private long removeEntry(int pos) {
/*  232 */     long oldValue = this.value[pos];
/*  233 */     this.size--;
/*  234 */     shiftKeys(pos);
/*  235 */     if (this.n > this.minN && this.size < this.maxFill / 4 && this.n > 16) rehash(this.n / 2); 
/*  236 */     return oldValue;
/*      */   }
/*      */   
/*      */   private long removeNullEntry() {
/*  240 */     this.containsNullKey = false;
/*  241 */     long oldValue = this.value[this.n];
/*  242 */     this.size--;
/*  243 */     if (this.n > this.minN && this.size < this.maxFill / 4 && this.n > 16) rehash(this.n / 2); 
/*  244 */     return oldValue;
/*      */   }
/*      */ 
/*      */   
/*      */   public void putAll(Map<? extends Long, ? extends Long> m) {
/*  249 */     if (this.f <= 0.5D) { ensureCapacity(m.size()); }
/*  250 */     else { tryCapacity((size() + m.size())); }
/*      */     
/*  252 */     super.putAll(m);
/*      */   }
/*      */   
/*      */   private int find(long k) {
/*  256 */     if (this.strategy.equals(k, 0L)) return this.containsNullKey ? this.n : -(this.n + 1);
/*      */     
/*  258 */     long[] key = this.key;
/*      */     long curr;
/*      */     int pos;
/*  261 */     if ((curr = key[pos = HashCommon.mix(this.strategy.hashCode(k)) & this.mask]) == 0L) return -(pos + 1); 
/*  262 */     if (this.strategy.equals(k, curr)) return pos;
/*      */     
/*      */     while (true) {
/*  265 */       if ((curr = key[pos = pos + 1 & this.mask]) == 0L) return -(pos + 1); 
/*  266 */       if (this.strategy.equals(k, curr)) return pos; 
/*      */     } 
/*      */   }
/*      */   
/*      */   private void insert(int pos, long k, long v) {
/*  271 */     if (pos == this.n) this.containsNullKey = true; 
/*  272 */     this.key[pos] = k;
/*  273 */     this.value[pos] = v;
/*  274 */     if (this.size++ >= this.maxFill) rehash(HashCommon.arraySize(this.size + 1, this.f));
/*      */   
/*      */   }
/*      */ 
/*      */   
/*      */   public long put(long k, long v) {
/*  280 */     int pos = find(k);
/*  281 */     if (pos < 0) {
/*  282 */       insert(-pos - 1, k, v);
/*  283 */       return this.defRetValue;
/*      */     } 
/*  285 */     long oldValue = this.value[pos];
/*  286 */     this.value[pos] = v;
/*  287 */     return oldValue;
/*      */   }
/*      */   
/*      */   private long addToValue(int pos, long incr) {
/*  291 */     long oldValue = this.value[pos];
/*  292 */     this.value[pos] = oldValue + incr;
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
/*      */   public long addTo(long k, long incr) {
/*      */     int pos;
/*  311 */     if (this.strategy.equals(k, 0L)) {
/*  312 */       if (this.containsNullKey) return addToValue(this.n, incr); 
/*  313 */       pos = this.n;
/*  314 */       this.containsNullKey = true;
/*      */     } else {
/*      */       
/*  317 */       long[] key = this.key;
/*      */       long curr;
/*  319 */       if ((curr = key[pos = HashCommon.mix(this.strategy.hashCode(k)) & this.mask]) != 0L) {
/*  320 */         if (this.strategy.equals(curr, k)) return addToValue(pos, incr); 
/*  321 */         while ((curr = key[pos = pos + 1 & this.mask]) != 0L) { if (this.strategy.equals(curr, k)) return addToValue(pos, incr);  }
/*      */       
/*      */       } 
/*  324 */     }  this.key[pos] = k;
/*  325 */     this.value[pos] = this.defRetValue + incr;
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
/*  341 */     long[] key = this.key; while (true) {
/*      */       long curr; int last;
/*  343 */       pos = (last = pos) + 1 & this.mask;
/*      */       while (true) {
/*  345 */         if ((curr = key[pos]) == 0L) {
/*  346 */           key[last] = 0L;
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
/*      */   public long remove(long k) {
/*  361 */     if (this.strategy.equals(k, 0L)) {
/*  362 */       if (this.containsNullKey) return removeNullEntry(); 
/*  363 */       return this.defRetValue;
/*      */     } 
/*      */     
/*  366 */     long[] key = this.key;
/*      */     long curr;
/*      */     int pos;
/*  369 */     if ((curr = key[pos = HashCommon.mix(this.strategy.hashCode(k)) & this.mask]) == 0L) return this.defRetValue; 
/*  370 */     if (this.strategy.equals(k, curr)) return removeEntry(pos); 
/*      */     while (true) {
/*  372 */       if ((curr = key[pos = pos + 1 & this.mask]) == 0L) return this.defRetValue; 
/*  373 */       if (this.strategy.equals(k, curr)) return removeEntry(pos);
/*      */     
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   public long get(long k) {
/*  380 */     if (this.strategy.equals(k, 0L)) return this.containsNullKey ? this.value[this.n] : this.defRetValue;
/*      */     
/*  382 */     long[] key = this.key;
/*      */     long curr;
/*      */     int pos;
/*  385 */     if ((curr = key[pos = HashCommon.mix(this.strategy.hashCode(k)) & this.mask]) == 0L) return this.defRetValue; 
/*  386 */     if (this.strategy.equals(k, curr)) return this.value[pos];
/*      */     
/*      */     while (true) {
/*  389 */       if ((curr = key[pos = pos + 1 & this.mask]) == 0L) return this.defRetValue; 
/*  390 */       if (this.strategy.equals(k, curr)) return this.value[pos];
/*      */     
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   public boolean containsKey(long k) {
/*  397 */     if (this.strategy.equals(k, 0L)) return this.containsNullKey;
/*      */     
/*  399 */     long[] key = this.key;
/*      */     long curr;
/*      */     int pos;
/*  402 */     if ((curr = key[pos = HashCommon.mix(this.strategy.hashCode(k)) & this.mask]) == 0L) return false; 
/*  403 */     if (this.strategy.equals(k, curr)) return true;
/*      */     
/*      */     while (true) {
/*  406 */       if ((curr = key[pos = pos + 1 & this.mask]) == 0L) return false; 
/*  407 */       if (this.strategy.equals(k, curr)) return true;
/*      */     
/*      */     } 
/*      */   }
/*      */   
/*      */   public boolean containsValue(long v) {
/*  413 */     long[] value = this.value;
/*  414 */     long[] key = this.key;
/*  415 */     if (this.containsNullKey && value[this.n] == v) return true; 
/*  416 */     for (int i = this.n; i-- != 0;) { if (key[i] != 0L && value[i] == v) return true;  }
/*  417 */      return false;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public long getOrDefault(long k, long defaultValue) {
/*  424 */     if (this.strategy.equals(k, 0L)) return this.containsNullKey ? this.value[this.n] : defaultValue;
/*      */     
/*  426 */     long[] key = this.key;
/*      */     long curr;
/*      */     int pos;
/*  429 */     if ((curr = key[pos = HashCommon.mix(this.strategy.hashCode(k)) & this.mask]) == 0L) return defaultValue; 
/*  430 */     if (this.strategy.equals(k, curr)) return this.value[pos];
/*      */     
/*      */     while (true) {
/*  433 */       if ((curr = key[pos = pos + 1 & this.mask]) == 0L) return defaultValue; 
/*  434 */       if (this.strategy.equals(k, curr)) return this.value[pos];
/*      */     
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   public long putIfAbsent(long k, long v) {
/*  441 */     int pos = find(k);
/*  442 */     if (pos >= 0) return this.value[pos]; 
/*  443 */     insert(-pos - 1, k, v);
/*  444 */     return this.defRetValue;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean remove(long k, long v) {
/*  451 */     if (this.strategy.equals(k, 0L)) {
/*  452 */       if (this.containsNullKey && v == this.value[this.n]) {
/*  453 */         removeNullEntry();
/*  454 */         return true;
/*      */       } 
/*  456 */       return false;
/*      */     } 
/*      */     
/*  459 */     long[] key = this.key;
/*      */     long curr;
/*      */     int pos;
/*  462 */     if ((curr = key[pos = HashCommon.mix(this.strategy.hashCode(k)) & this.mask]) == 0L) return false; 
/*  463 */     if (this.strategy.equals(k, curr) && v == this.value[pos]) {
/*  464 */       removeEntry(pos);
/*  465 */       return true;
/*      */     } 
/*      */     while (true) {
/*  468 */       if ((curr = key[pos = pos + 1 & this.mask]) == 0L) return false; 
/*  469 */       if (this.strategy.equals(k, curr) && v == this.value[pos]) {
/*  470 */         removeEntry(pos);
/*  471 */         return true;
/*      */       } 
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean replace(long k, long oldValue, long v) {
/*  479 */     int pos = find(k);
/*  480 */     if (pos < 0 || oldValue != this.value[pos]) return false; 
/*  481 */     this.value[pos] = v;
/*  482 */     return true;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public long replace(long k, long v) {
/*  488 */     int pos = find(k);
/*  489 */     if (pos < 0) return this.defRetValue; 
/*  490 */     long oldValue = this.value[pos];
/*  491 */     this.value[pos] = v;
/*  492 */     return oldValue;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public long computeIfAbsent(long k, LongUnaryOperator mappingFunction) {
/*  498 */     Objects.requireNonNull(mappingFunction);
/*  499 */     int pos = find(k);
/*  500 */     if (pos >= 0) return this.value[pos]; 
/*  501 */     long newValue = mappingFunction.applyAsLong(k);
/*  502 */     insert(-pos - 1, k, newValue);
/*  503 */     return newValue;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public long computeIfAbsent(long key, Long2LongFunction mappingFunction) {
/*  509 */     Objects.requireNonNull(mappingFunction);
/*  510 */     int pos = find(key);
/*  511 */     if (pos >= 0) return this.value[pos]; 
/*  512 */     if (!mappingFunction.containsKey(key)) return this.defRetValue; 
/*  513 */     long newValue = mappingFunction.get(key);
/*  514 */     insert(-pos - 1, key, newValue);
/*  515 */     return newValue;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public long computeIfAbsentNullable(long k, LongFunction<? extends Long> mappingFunction) {
/*  521 */     Objects.requireNonNull(mappingFunction);
/*  522 */     int pos = find(k);
/*  523 */     if (pos >= 0) return this.value[pos]; 
/*  524 */     Long newValue = mappingFunction.apply(k);
/*  525 */     if (newValue == null) return this.defRetValue; 
/*  526 */     long v = newValue.longValue();
/*  527 */     insert(-pos - 1, k, v);
/*  528 */     return v;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public long computeIfPresent(long k, BiFunction<? super Long, ? super Long, ? extends Long> remappingFunction) {
/*  534 */     Objects.requireNonNull(remappingFunction);
/*  535 */     int pos = find(k);
/*  536 */     if (pos < 0) return this.defRetValue; 
/*  537 */     Long newValue = remappingFunction.apply(Long.valueOf(k), Long.valueOf(this.value[pos]));
/*  538 */     if (newValue == null) {
/*  539 */       if (this.strategy.equals(k, 0L)) { removeNullEntry(); }
/*  540 */       else { removeEntry(pos); }
/*  541 */        return this.defRetValue;
/*      */     } 
/*  543 */     this.value[pos] = newValue.longValue(); return newValue.longValue();
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public long compute(long k, BiFunction<? super Long, ? super Long, ? extends Long> remappingFunction) {
/*  549 */     Objects.requireNonNull(remappingFunction);
/*  550 */     int pos = find(k);
/*  551 */     Long newValue = remappingFunction.apply(Long.valueOf(k), (pos >= 0) ? Long.valueOf(this.value[pos]) : null);
/*  552 */     if (newValue == null) {
/*  553 */       if (pos >= 0)
/*  554 */         if (this.strategy.equals(k, 0L)) { removeNullEntry(); }
/*  555 */         else { removeEntry(pos); }
/*      */          
/*  557 */       return this.defRetValue;
/*      */     } 
/*  559 */     long newVal = newValue.longValue();
/*  560 */     if (pos < 0) {
/*  561 */       insert(-pos - 1, k, newVal);
/*  562 */       return newVal;
/*      */     } 
/*  564 */     this.value[pos] = newVal; return newVal;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public long merge(long k, long v, BiFunction<? super Long, ? super Long, ? extends Long> remappingFunction) {
/*  570 */     Objects.requireNonNull(remappingFunction);
/*      */     
/*  572 */     int pos = find(k);
/*  573 */     if (pos < 0) {
/*  574 */       if (pos < 0) { insert(-pos - 1, k, v); }
/*  575 */       else { this.value[pos] = v; }
/*  576 */        return v;
/*      */     } 
/*  578 */     Long newValue = remappingFunction.apply(Long.valueOf(this.value[pos]), Long.valueOf(v));
/*  579 */     if (newValue == null) {
/*  580 */       if (this.strategy.equals(k, 0L)) { removeNullEntry(); }
/*  581 */       else { removeEntry(pos); }
/*  582 */        return this.defRetValue;
/*      */     } 
/*  584 */     this.value[pos] = newValue.longValue(); return newValue.longValue();
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
/*  598 */     Arrays.fill(this.key, 0L);
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
/*      */     implements Long2LongMap.Entry, Map.Entry<Long, Long>, LongLongPair
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
/*      */     public long getLongKey() {
/*  629 */       return Long2LongOpenCustomHashMap.this.key[this.index];
/*      */     }
/*      */ 
/*      */     
/*      */     public long leftLong() {
/*  634 */       return Long2LongOpenCustomHashMap.this.key[this.index];
/*      */     }
/*      */ 
/*      */     
/*      */     public long getLongValue() {
/*  639 */       return Long2LongOpenCustomHashMap.this.value[this.index];
/*      */     }
/*      */ 
/*      */     
/*      */     public long rightLong() {
/*  644 */       return Long2LongOpenCustomHashMap.this.value[this.index];
/*      */     }
/*      */ 
/*      */     
/*      */     public long setValue(long v) {
/*  649 */       long oldValue = Long2LongOpenCustomHashMap.this.value[this.index];
/*  650 */       Long2LongOpenCustomHashMap.this.value[this.index] = v;
/*  651 */       return oldValue;
/*      */     }
/*      */ 
/*      */     
/*      */     public LongLongPair right(long v) {
/*  656 */       Long2LongOpenCustomHashMap.this.value[this.index] = v;
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
/*      */     public Long getKey() {
/*  668 */       return Long.valueOf(Long2LongOpenCustomHashMap.this.key[this.index]);
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     @Deprecated
/*      */     public Long getValue() {
/*  679 */       return Long.valueOf(Long2LongOpenCustomHashMap.this.value[this.index]);
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     @Deprecated
/*      */     public Long setValue(Long v) {
/*  690 */       return Long.valueOf(setValue(v.longValue()));
/*      */     }
/*      */ 
/*      */ 
/*      */     
/*      */     public boolean equals(Object o) {
/*  696 */       if (!(o instanceof Map.Entry)) return false; 
/*  697 */       Map.Entry<Long, Long> e = (Map.Entry<Long, Long>)o;
/*  698 */       return (Long2LongOpenCustomHashMap.this.strategy.equals(Long2LongOpenCustomHashMap.this.key[this.index], ((Long)e.getKey()).longValue()) && Long2LongOpenCustomHashMap.this.value[this.index] == ((Long)e.getValue()).longValue());
/*      */     }
/*      */ 
/*      */     
/*      */     public int hashCode() {
/*  703 */       return Long2LongOpenCustomHashMap.this.strategy.hashCode(Long2LongOpenCustomHashMap.this.key[this.index]) ^ HashCommon.long2int(Long2LongOpenCustomHashMap.this.value[this.index]);
/*      */     }
/*      */ 
/*      */     
/*      */     public String toString() {
/*  708 */       return Long2LongOpenCustomHashMap.this.key[this.index] + "=>" + Long2LongOpenCustomHashMap.this.value[this.index];
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
/*  719 */     int pos = Long2LongOpenCustomHashMap.this.n;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  725 */     int last = -1;
/*      */     
/*  727 */     int c = Long2LongOpenCustomHashMap.this.size;
/*      */     
/*  729 */     boolean mustReturnNullKey = Long2LongOpenCustomHashMap.this.containsNullKey;
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     LongArrayList wrapped;
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
/*  748 */         return this.last = Long2LongOpenCustomHashMap.this.n;
/*      */       } 
/*  750 */       long[] key = Long2LongOpenCustomHashMap.this.key;
/*      */       while (true) {
/*  752 */         if (--this.pos < 0) {
/*      */           
/*  754 */           this.last = Integer.MIN_VALUE;
/*  755 */           long k = this.wrapped.getLong(-this.pos - 1);
/*  756 */           int p = HashCommon.mix(Long2LongOpenCustomHashMap.this.strategy.hashCode(k)) & Long2LongOpenCustomHashMap.this.mask;
/*  757 */           for (; !Long2LongOpenCustomHashMap.this.strategy.equals(k, key[p]); p = p + 1 & Long2LongOpenCustomHashMap.this.mask);
/*  758 */           return p;
/*      */         } 
/*  760 */         if (key[this.pos] != 0L) return this.last = this.pos; 
/*      */       } 
/*      */     }
/*      */     
/*      */     public void forEachRemaining(ConsumerType action) {
/*  765 */       if (this.mustReturnNullKey) {
/*  766 */         this.mustReturnNullKey = false;
/*  767 */         acceptOnIndex(action, this.last = Long2LongOpenCustomHashMap.this.n);
/*  768 */         this.c--;
/*      */       } 
/*  770 */       long[] key = Long2LongOpenCustomHashMap.this.key;
/*  771 */       while (this.c != 0) {
/*  772 */         if (--this.pos < 0) {
/*      */           
/*  774 */           this.last = Integer.MIN_VALUE;
/*  775 */           long k = this.wrapped.getLong(-this.pos - 1);
/*  776 */           int p = HashCommon.mix(Long2LongOpenCustomHashMap.this.strategy.hashCode(k)) & Long2LongOpenCustomHashMap.this.mask;
/*  777 */           for (; !Long2LongOpenCustomHashMap.this.strategy.equals(k, key[p]); p = p + 1 & Long2LongOpenCustomHashMap.this.mask);
/*  778 */           acceptOnIndex(action, p);
/*  779 */           this.c--; continue;
/*  780 */         }  if (key[this.pos] != 0L) {
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
/*  797 */       long[] key = Long2LongOpenCustomHashMap.this.key; while (true) {
/*      */         long curr; int last;
/*  799 */         pos = (last = pos) + 1 & Long2LongOpenCustomHashMap.this.mask;
/*      */         while (true) {
/*  801 */           if ((curr = key[pos]) == 0L) {
/*  802 */             key[last] = 0L;
/*      */             return;
/*      */           } 
/*  805 */           int slot = HashCommon.mix(Long2LongOpenCustomHashMap.this.strategy.hashCode(curr)) & Long2LongOpenCustomHashMap.this.mask;
/*  806 */           if ((last <= pos) ? (last >= slot || slot > pos) : (last >= slot && slot > pos))
/*  807 */             break;  pos = pos + 1 & Long2LongOpenCustomHashMap.this.mask;
/*      */         } 
/*  809 */         if (pos < last) {
/*  810 */           if (this.wrapped == null) this.wrapped = new LongArrayList(2); 
/*  811 */           this.wrapped.add(key[pos]);
/*      */         } 
/*  813 */         key[last] = curr;
/*  814 */         Long2LongOpenCustomHashMap.this.value[last] = Long2LongOpenCustomHashMap.this.value[pos];
/*      */       } 
/*      */     }
/*      */     
/*      */     public void remove() {
/*  819 */       if (this.last == -1) throw new IllegalStateException(); 
/*  820 */       if (this.last == Long2LongOpenCustomHashMap.this.n)
/*  821 */       { Long2LongOpenCustomHashMap.this.containsNullKey = false; }
/*  822 */       else if (this.pos >= 0) { shiftKeys(this.last); }
/*      */       else
/*      */       
/*  825 */       { Long2LongOpenCustomHashMap.this.remove(this.wrapped.getLong(-this.pos - 1));
/*  826 */         this.last = -1;
/*      */         return; }
/*      */       
/*  829 */       Long2LongOpenCustomHashMap.this.size--;
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
/*      */   private final class EntryIterator extends MapIterator<Consumer<? super Long2LongMap.Entry>> implements ObjectIterator<Long2LongMap.Entry> { public Long2LongOpenCustomHashMap.MapEntry next() {
/*  846 */       return this.entry = new Long2LongOpenCustomHashMap.MapEntry(nextEntry());
/*      */     }
/*      */     private Long2LongOpenCustomHashMap.MapEntry entry;
/*      */     private EntryIterator() {}
/*      */     
/*      */     final void acceptOnIndex(Consumer<? super Long2LongMap.Entry> action, int index) {
/*  852 */       action.accept(this.entry = new Long2LongOpenCustomHashMap.MapEntry(index));
/*      */     }
/*      */ 
/*      */     
/*      */     public void remove() {
/*  857 */       super.remove();
/*  858 */       this.entry.index = -1;
/*      */     } }
/*      */   
/*      */   private final class FastEntryIterator extends MapIterator<Consumer<? super Long2LongMap.Entry>> implements ObjectIterator<Long2LongMap.Entry> {
/*      */     private FastEntryIterator() {
/*  863 */       this.entry = new Long2LongOpenCustomHashMap.MapEntry();
/*      */     }
/*      */     private final Long2LongOpenCustomHashMap.MapEntry entry;
/*      */     public Long2LongOpenCustomHashMap.MapEntry next() {
/*  867 */       this.entry.index = nextEntry();
/*  868 */       return this.entry;
/*      */     }
/*      */ 
/*      */ 
/*      */     
/*      */     final void acceptOnIndex(Consumer<? super Long2LongMap.Entry> action, int index) {
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
/*  886 */     int max = Long2LongOpenCustomHashMap.this.n;
/*      */     
/*  888 */     int c = 0;
/*      */     
/*  890 */     boolean mustReturnNull = Long2LongOpenCustomHashMap.this.containsNullKey;
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
/*  911 */         acceptOnIndex(action, Long2LongOpenCustomHashMap.this.n);
/*  912 */         return true;
/*      */       } 
/*  914 */       long[] key = Long2LongOpenCustomHashMap.this.key;
/*  915 */       while (this.pos < this.max) {
/*  916 */         if (key[this.pos] != 0L) {
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
/*  930 */         acceptOnIndex(action, Long2LongOpenCustomHashMap.this.n);
/*      */       } 
/*  932 */       long[] key = Long2LongOpenCustomHashMap.this.key;
/*  933 */       while (this.pos < this.max) {
/*  934 */         if (key[this.pos] != 0L) {
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
/*  945 */         return (Long2LongOpenCustomHashMap.this.size - this.c);
/*      */       }
/*      */ 
/*      */ 
/*      */       
/*  950 */       return Math.min((Long2LongOpenCustomHashMap.this.size - this.c), (long)(Long2LongOpenCustomHashMap.this.realSize() / Long2LongOpenCustomHashMap.this.n * (this.max - this.pos)) + (this.mustReturnNull ? 1L : 0L));
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
/*  980 */       long[] key = Long2LongOpenCustomHashMap.this.key;
/*  981 */       while (this.pos < this.max && n > 0L) {
/*  982 */         if (key[this.pos++] != 0L) {
/*  983 */           skipped++;
/*  984 */           n--;
/*      */         } 
/*      */       } 
/*  987 */       return skipped;
/*      */     }
/*      */   }
/*      */   
/*      */   private final class EntrySpliterator
/*      */     extends MapSpliterator<Consumer<? super Long2LongMap.Entry>, EntrySpliterator> implements ObjectSpliterator<Long2LongMap.Entry> {
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
/*      */     final void acceptOnIndex(Consumer<? super Long2LongMap.Entry> action, int index) {
/* 1008 */       action.accept(new Long2LongOpenCustomHashMap.MapEntry(index));
/*      */     }
/*      */ 
/*      */     
/*      */     final EntrySpliterator makeForSplit(int pos, int max, boolean mustReturnNull) {
/* 1013 */       return new EntrySpliterator(pos, max, mustReturnNull, true);
/*      */     } }
/*      */   
/*      */   private final class MapEntrySet extends AbstractObjectSet<Long2LongMap.Entry> implements Long2LongMap.FastEntrySet {
/*      */     private MapEntrySet() {}
/*      */     
/*      */     public ObjectIterator<Long2LongMap.Entry> iterator() {
/* 1020 */       return new Long2LongOpenCustomHashMap.EntryIterator();
/*      */     }
/*      */ 
/*      */     
/*      */     public ObjectIterator<Long2LongMap.Entry> fastIterator() {
/* 1025 */       return new Long2LongOpenCustomHashMap.FastEntryIterator();
/*      */     }
/*      */ 
/*      */     
/*      */     public ObjectSpliterator<Long2LongMap.Entry> spliterator() {
/* 1030 */       return new Long2LongOpenCustomHashMap.EntrySpliterator();
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public boolean contains(Object o) {
/* 1037 */       if (!(o instanceof Map.Entry)) return false; 
/* 1038 */       Map.Entry<?, ?> e = (Map.Entry<?, ?>)o;
/* 1039 */       if (e.getKey() == null || !(e.getKey() instanceof Long)) return false; 
/* 1040 */       if (e.getValue() == null || !(e.getValue() instanceof Long)) return false; 
/* 1041 */       long k = ((Long)e.getKey()).longValue();
/* 1042 */       long v = ((Long)e.getValue()).longValue();
/* 1043 */       if (Long2LongOpenCustomHashMap.this.strategy.equals(k, 0L)) return (Long2LongOpenCustomHashMap.this.containsNullKey && Long2LongOpenCustomHashMap.this.value[Long2LongOpenCustomHashMap.this.n] == v);
/*      */       
/* 1045 */       long[] key = Long2LongOpenCustomHashMap.this.key;
/*      */       long curr;
/*      */       int pos;
/* 1048 */       if ((curr = key[pos = HashCommon.mix(Long2LongOpenCustomHashMap.this.strategy.hashCode(k)) & Long2LongOpenCustomHashMap.this.mask]) == 0L) return false; 
/* 1049 */       if (Long2LongOpenCustomHashMap.this.strategy.equals(k, curr)) return (Long2LongOpenCustomHashMap.this.value[pos] == v);
/*      */       
/*      */       while (true) {
/* 1052 */         if ((curr = key[pos = pos + 1 & Long2LongOpenCustomHashMap.this.mask]) == 0L) return false; 
/* 1053 */         if (Long2LongOpenCustomHashMap.this.strategy.equals(k, curr)) return (Long2LongOpenCustomHashMap.this.value[pos] == v);
/*      */       
/*      */       } 
/*      */     }
/*      */ 
/*      */     
/*      */     public boolean remove(Object o) {
/* 1060 */       if (!(o instanceof Map.Entry)) return false; 
/* 1061 */       Map.Entry<?, ?> e = (Map.Entry<?, ?>)o;
/* 1062 */       if (e.getKey() == null || !(e.getKey() instanceof Long)) return false; 
/* 1063 */       if (e.getValue() == null || !(e.getValue() instanceof Long)) return false; 
/* 1064 */       long k = ((Long)e.getKey()).longValue();
/* 1065 */       long v = ((Long)e.getValue()).longValue();
/* 1066 */       if (Long2LongOpenCustomHashMap.this.strategy.equals(k, 0L)) {
/* 1067 */         if (Long2LongOpenCustomHashMap.this.containsNullKey && Long2LongOpenCustomHashMap.this.value[Long2LongOpenCustomHashMap.this.n] == v) {
/* 1068 */           Long2LongOpenCustomHashMap.this.removeNullEntry();
/* 1069 */           return true;
/*      */         } 
/* 1071 */         return false;
/*      */       } 
/*      */       
/* 1074 */       long[] key = Long2LongOpenCustomHashMap.this.key;
/*      */       long curr;
/*      */       int pos;
/* 1077 */       if ((curr = key[pos = HashCommon.mix(Long2LongOpenCustomHashMap.this.strategy.hashCode(k)) & Long2LongOpenCustomHashMap.this.mask]) == 0L) return false; 
/* 1078 */       if (Long2LongOpenCustomHashMap.this.strategy.equals(curr, k)) {
/* 1079 */         if (Long2LongOpenCustomHashMap.this.value[pos] == v) {
/* 1080 */           Long2LongOpenCustomHashMap.this.removeEntry(pos);
/* 1081 */           return true;
/*      */         } 
/* 1083 */         return false;
/*      */       } 
/*      */       while (true) {
/* 1086 */         if ((curr = key[pos = pos + 1 & Long2LongOpenCustomHashMap.this.mask]) == 0L) return false; 
/* 1087 */         if (Long2LongOpenCustomHashMap.this.strategy.equals(curr, k) && 
/* 1088 */           Long2LongOpenCustomHashMap.this.value[pos] == v) {
/* 1089 */           Long2LongOpenCustomHashMap.this.removeEntry(pos);
/* 1090 */           return true;
/*      */         } 
/*      */       } 
/*      */     }
/*      */ 
/*      */ 
/*      */     
/*      */     public int size() {
/* 1098 */       return Long2LongOpenCustomHashMap.this.size;
/*      */     }
/*      */ 
/*      */     
/*      */     public void clear() {
/* 1103 */       Long2LongOpenCustomHashMap.this.clear();
/*      */     }
/*      */ 
/*      */ 
/*      */     
/*      */     public void forEach(Consumer<? super Long2LongMap.Entry> consumer) {
/* 1109 */       if (Long2LongOpenCustomHashMap.this.containsNullKey) consumer.accept(new Long2LongOpenCustomHashMap.MapEntry(Long2LongOpenCustomHashMap.this.n)); 
/* 1110 */       for (int pos = Long2LongOpenCustomHashMap.this.n; pos-- != 0;) { if (Long2LongOpenCustomHashMap.this.key[pos] != 0L) consumer.accept(new Long2LongOpenCustomHashMap.MapEntry(pos));
/*      */          }
/*      */     
/*      */     }
/*      */     
/*      */     public void fastForEach(Consumer<? super Long2LongMap.Entry> consumer) {
/* 1116 */       Long2LongOpenCustomHashMap.MapEntry entry = new Long2LongOpenCustomHashMap.MapEntry();
/* 1117 */       if (Long2LongOpenCustomHashMap.this.containsNullKey) {
/* 1118 */         entry.index = Long2LongOpenCustomHashMap.this.n;
/* 1119 */         consumer.accept(entry);
/*      */       } 
/* 1121 */       for (int pos = Long2LongOpenCustomHashMap.this.n; pos-- != 0;) { if (Long2LongOpenCustomHashMap.this.key[pos] != 0L) {
/* 1122 */           entry.index = pos;
/* 1123 */           consumer.accept(entry);
/*      */         }  }
/*      */     
/*      */     }
/*      */   }
/*      */   
/*      */   public Long2LongMap.FastEntrySet long2LongEntrySet() {
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
/*      */     extends MapIterator<LongConsumer>
/*      */     implements LongIterator
/*      */   {
/*      */     final void acceptOnIndex(LongConsumer action, int index) {
/* 1152 */       action.accept(Long2LongOpenCustomHashMap.this.key[index]);
/*      */     }
/*      */ 
/*      */     
/*      */     public long nextLong() {
/* 1157 */       return Long2LongOpenCustomHashMap.this.key[nextEntry()];
/*      */     }
/*      */   }
/*      */   
/*      */   private final class KeySpliterator
/*      */     extends MapSpliterator<LongConsumer, KeySpliterator> implements LongSpliterator {
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
/*      */     final void acceptOnIndex(LongConsumer action, int index) {
/* 1178 */       action.accept(Long2LongOpenCustomHashMap.this.key[index]);
/*      */     }
/*      */ 
/*      */     
/*      */     final KeySpliterator makeForSplit(int pos, int max, boolean mustReturnNull) {
/* 1183 */       return new KeySpliterator(pos, max, mustReturnNull, true);
/*      */     } }
/*      */   
/*      */   private final class KeySet extends AbstractLongSet {
/*      */     private KeySet() {}
/*      */     
/*      */     public LongIterator iterator() {
/* 1190 */       return new Long2LongOpenCustomHashMap.KeyIterator();
/*      */     }
/*      */ 
/*      */     
/*      */     public LongSpliterator spliterator() {
/* 1195 */       return new Long2LongOpenCustomHashMap.KeySpliterator();
/*      */     }
/*      */ 
/*      */ 
/*      */     
/*      */     public void forEach(LongConsumer consumer) {
/* 1201 */       if (Long2LongOpenCustomHashMap.this.containsNullKey) consumer.accept(Long2LongOpenCustomHashMap.this.key[Long2LongOpenCustomHashMap.this.n]); 
/* 1202 */       for (int pos = Long2LongOpenCustomHashMap.this.n; pos-- != 0; ) {
/* 1203 */         long k = Long2LongOpenCustomHashMap.this.key[pos];
/* 1204 */         if (k != 0L) consumer.accept(k);
/*      */       
/*      */       } 
/*      */     }
/*      */     
/*      */     public int size() {
/* 1210 */       return Long2LongOpenCustomHashMap.this.size;
/*      */     }
/*      */ 
/*      */     
/*      */     public boolean contains(long k) {
/* 1215 */       return Long2LongOpenCustomHashMap.this.containsKey(k);
/*      */     }
/*      */ 
/*      */     
/*      */     public boolean remove(long k) {
/* 1220 */       int oldSize = Long2LongOpenCustomHashMap.this.size;
/* 1221 */       Long2LongOpenCustomHashMap.this.remove(k);
/* 1222 */       return (Long2LongOpenCustomHashMap.this.size != oldSize);
/*      */     }
/*      */ 
/*      */     
/*      */     public void clear() {
/* 1227 */       Long2LongOpenCustomHashMap.this.clear();
/*      */     }
/*      */   }
/*      */ 
/*      */   
/*      */   public LongSet keySet() {
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
/*      */     extends MapIterator<LongConsumer>
/*      */     implements LongIterator
/*      */   {
/*      */     final void acceptOnIndex(LongConsumer action, int index) {
/* 1255 */       action.accept(Long2LongOpenCustomHashMap.this.value[index]);
/*      */     }
/*      */ 
/*      */     
/*      */     public long nextLong() {
/* 1260 */       return Long2LongOpenCustomHashMap.this.value[nextEntry()];
/*      */     }
/*      */   }
/*      */   
/*      */   private final class ValueSpliterator
/*      */     extends MapSpliterator<LongConsumer, ValueSpliterator> implements LongSpliterator {
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
/*      */     final void acceptOnIndex(LongConsumer action, int index) {
/* 1281 */       action.accept(Long2LongOpenCustomHashMap.this.value[index]);
/*      */     }
/*      */ 
/*      */     
/*      */     final ValueSpliterator makeForSplit(int pos, int max, boolean mustReturnNull) {
/* 1286 */       return new ValueSpliterator(pos, max, mustReturnNull, true);
/*      */     }
/*      */   }
/*      */ 
/*      */   
/*      */   public LongCollection values() {
/* 1292 */     if (this.values == null) this.values = new AbstractLongCollection()
/*      */         {
/*      */           public LongIterator iterator() {
/* 1295 */             return new Long2LongOpenCustomHashMap.ValueIterator();
/*      */           }
/*      */ 
/*      */           
/*      */           public LongSpliterator spliterator() {
/* 1300 */             return new Long2LongOpenCustomHashMap.ValueSpliterator();
/*      */           }
/*      */ 
/*      */ 
/*      */           
/*      */           public void forEach(LongConsumer consumer) {
/* 1306 */             if (Long2LongOpenCustomHashMap.this.containsNullKey) consumer.accept(Long2LongOpenCustomHashMap.this.value[Long2LongOpenCustomHashMap.this.n]); 
/* 1307 */             for (int pos = Long2LongOpenCustomHashMap.this.n; pos-- != 0;) { if (Long2LongOpenCustomHashMap.this.key[pos] != 0L) consumer.accept(Long2LongOpenCustomHashMap.this.value[pos]);  }
/*      */           
/*      */           }
/*      */           
/*      */           public int size() {
/* 1312 */             return Long2LongOpenCustomHashMap.this.size;
/*      */           }
/*      */ 
/*      */           
/*      */           public boolean contains(long v) {
/* 1317 */             return Long2LongOpenCustomHashMap.this.containsValue(v);
/*      */           }
/*      */ 
/*      */           
/*      */           public void clear() {
/* 1322 */             Long2LongOpenCustomHashMap.this.clear();
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
/* 1386 */     long[] key = this.key;
/* 1387 */     long[] value = this.value;
/* 1388 */     int mask = newN - 1;
/* 1389 */     long[] newKey = new long[newN + 1];
/* 1390 */     long[] newValue = new long[newN + 1];
/* 1391 */     int i = this.n;
/* 1392 */     for (int j = realSize(); j-- != 0; ) {
/* 1393 */       while (key[--i] == 0L); int pos;
/* 1394 */       if (newKey[pos = HashCommon.mix(this.strategy.hashCode(key[i])) & mask] != 0L) while (newKey[pos = pos + 1 & mask] != 0L); 
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
/*      */   public Long2LongOpenCustomHashMap clone() {
/*      */     Long2LongOpenCustomHashMap c;
/*      */     try {
/* 1420 */       c = (Long2LongOpenCustomHashMap)super.clone();
/* 1421 */     } catch (CloneNotSupportedException cantHappen) {
/* 1422 */       throw new InternalError();
/*      */     } 
/* 1424 */     c.keys = null;
/* 1425 */     c.values = null;
/* 1426 */     c.entries = null;
/* 1427 */     c.containsNullKey = this.containsNullKey;
/* 1428 */     c.key = (long[])this.key.clone();
/* 1429 */     c.value = (long[])this.value.clone();
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
/* 1447 */       for (; this.key[i] == 0L; i++);
/* 1448 */       t = this.strategy.hashCode(this.key[i]);
/* 1449 */       t ^= HashCommon.long2int(this.value[i]);
/* 1450 */       h += t;
/* 1451 */       i++;
/*      */     } 
/*      */     
/* 1454 */     if (this.containsNullKey) h += HashCommon.long2int(this.value[this.n]); 
/* 1455 */     return h;
/*      */   }
/*      */   
/*      */   private void writeObject(ObjectOutputStream s) throws IOException {
/* 1459 */     long[] key = this.key;
/* 1460 */     long[] value = this.value;
/* 1461 */     EntryIterator i = new EntryIterator();
/* 1462 */     s.defaultWriteObject();
/* 1463 */     for (int j = this.size; j-- != 0; ) {
/* 1464 */       int e = i.nextEntry();
/* 1465 */       s.writeLong(key[e]);
/* 1466 */       s.writeLong(value[e]);
/*      */     } 
/*      */   }
/*      */   
/*      */   private void readObject(ObjectInputStream s) throws IOException, ClassNotFoundException {
/* 1471 */     s.defaultReadObject();
/* 1472 */     this.n = HashCommon.arraySize(this.size, this.f);
/* 1473 */     this.maxFill = HashCommon.maxFill(this.n, this.f);
/* 1474 */     this.mask = this.n - 1;
/* 1475 */     long[] key = this.key = new long[this.n + 1];
/* 1476 */     long[] value = this.value = new long[this.n + 1];
/*      */ 
/*      */     
/* 1479 */     for (int i = this.size; i-- != 0; ) {
/* 1480 */       int pos; long k = s.readLong();
/* 1481 */       long v = s.readLong();
/* 1482 */       if (this.strategy.equals(k, 0L)) {
/* 1483 */         pos = this.n;
/* 1484 */         this.containsNullKey = true;
/*      */       } else {
/* 1486 */         pos = HashCommon.mix(this.strategy.hashCode(k)) & this.mask;
/* 1487 */         for (; key[pos] != 0L; pos = pos + 1 & this.mask);
/*      */       } 
/* 1489 */       key[pos] = k;
/* 1490 */       value[pos] = v;
/*      */     } 
/*      */   }
/*      */   
/*      */   private void checkTable() {}
/*      */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\i\\unimi\dsi\fastutil\longs\Long2LongOpenCustomHashMap.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */