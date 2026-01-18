/*      */ package it.unimi.dsi.fastutil.longs;
/*      */ 
/*      */ import it.unimi.dsi.fastutil.Hash;
/*      */ import it.unimi.dsi.fastutil.HashCommon;
/*      */ import it.unimi.dsi.fastutil.ints.AbstractIntCollection;
/*      */ import it.unimi.dsi.fastutil.ints.IntCollection;
/*      */ import it.unimi.dsi.fastutil.ints.IntIterator;
/*      */ import it.unimi.dsi.fastutil.ints.IntSpliterator;
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
/*      */ import java.util.function.IntConsumer;
/*      */ import java.util.function.LongConsumer;
/*      */ import java.util.function.LongFunction;
/*      */ import java.util.function.LongToIntFunction;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ public class Long2IntOpenCustomHashMap
/*      */   extends AbstractLong2IntMap
/*      */   implements Serializable, Cloneable, Hash
/*      */ {
/*      */   private static final long serialVersionUID = 0L;
/*      */   private static final boolean ASSERTS = false;
/*      */   protected transient long[] key;
/*      */   protected transient int[] value;
/*      */   protected transient int mask;
/*      */   protected transient boolean containsNullKey;
/*      */   protected LongHash.Strategy strategy;
/*      */   protected transient int n;
/*      */   protected transient int maxFill;
/*      */   protected final transient int minN;
/*      */   protected int size;
/*      */   protected final float f;
/*      */   protected transient Long2IntMap.FastEntrySet entries;
/*      */   protected transient LongSet keys;
/*      */   protected transient IntCollection values;
/*      */   
/*      */   public Long2IntOpenCustomHashMap(int expected, float f, LongHash.Strategy strategy) {
/*  103 */     this.strategy = strategy;
/*  104 */     if (f <= 0.0F || f >= 1.0F) throw new IllegalArgumentException("Load factor must be greater than 0 and smaller than 1"); 
/*  105 */     if (expected < 0) throw new IllegalArgumentException("The expected number of elements must be nonnegative"); 
/*  106 */     this.f = f;
/*  107 */     this.minN = this.n = HashCommon.arraySize(expected, f);
/*  108 */     this.mask = this.n - 1;
/*  109 */     this.maxFill = HashCommon.maxFill(this.n, f);
/*  110 */     this.key = new long[this.n + 1];
/*  111 */     this.value = new int[this.n + 1];
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Long2IntOpenCustomHashMap(int expected, LongHash.Strategy strategy) {
/*  121 */     this(expected, 0.75F, strategy);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Long2IntOpenCustomHashMap(LongHash.Strategy strategy) {
/*  131 */     this(16, 0.75F, strategy);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Long2IntOpenCustomHashMap(Map<? extends Long, ? extends Integer> m, float f, LongHash.Strategy strategy) {
/*  142 */     this(m.size(), f, strategy);
/*  143 */     putAll(m);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Long2IntOpenCustomHashMap(Map<? extends Long, ? extends Integer> m, LongHash.Strategy strategy) {
/*  153 */     this(m, 0.75F, strategy);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Long2IntOpenCustomHashMap(Long2IntMap m, float f, LongHash.Strategy strategy) {
/*  164 */     this(m.size(), f, strategy);
/*  165 */     putAll(m);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Long2IntOpenCustomHashMap(Long2IntMap m, LongHash.Strategy strategy) {
/*  176 */     this(m, 0.75F, strategy);
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
/*      */   public Long2IntOpenCustomHashMap(long[] k, int[] v, float f, LongHash.Strategy strategy) {
/*  189 */     this(k.length, f, strategy);
/*  190 */     if (k.length != v.length) throw new IllegalArgumentException("The key array and the value array have different lengths (" + k.length + " and " + v.length + ")"); 
/*  191 */     for (int i = 0; i < k.length; ) { put(k[i], v[i]); i++; }
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
/*      */   public Long2IntOpenCustomHashMap(long[] k, int[] v, LongHash.Strategy strategy) {
/*  204 */     this(k, v, 0.75F, strategy);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public LongHash.Strategy strategy() {
/*  213 */     return this.strategy;
/*      */   }
/*      */   
/*      */   private int realSize() {
/*  217 */     return this.containsNullKey ? (this.size - 1) : this.size;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void ensureCapacity(int capacity) {
/*  227 */     int needed = HashCommon.arraySize(capacity, this.f);
/*  228 */     if (needed > this.n) rehash(needed); 
/*      */   }
/*      */   
/*      */   private void tryCapacity(long capacity) {
/*  232 */     int needed = (int)Math.min(1073741824L, Math.max(2L, HashCommon.nextPowerOfTwo((long)Math.ceil(((float)capacity / this.f)))));
/*  233 */     if (needed > this.n) rehash(needed); 
/*      */   }
/*      */   
/*      */   private int removeEntry(int pos) {
/*  237 */     int oldValue = this.value[pos];
/*  238 */     this.size--;
/*  239 */     shiftKeys(pos);
/*  240 */     if (this.n > this.minN && this.size < this.maxFill / 4 && this.n > 16) rehash(this.n / 2); 
/*  241 */     return oldValue;
/*      */   }
/*      */   
/*      */   private int removeNullEntry() {
/*  245 */     this.containsNullKey = false;
/*  246 */     int oldValue = this.value[this.n];
/*  247 */     this.size--;
/*  248 */     if (this.n > this.minN && this.size < this.maxFill / 4 && this.n > 16) rehash(this.n / 2); 
/*  249 */     return oldValue;
/*      */   }
/*      */ 
/*      */   
/*      */   public void putAll(Map<? extends Long, ? extends Integer> m) {
/*  254 */     if (this.f <= 0.5D) { ensureCapacity(m.size()); }
/*  255 */     else { tryCapacity((size() + m.size())); }
/*      */     
/*  257 */     super.putAll(m);
/*      */   }
/*      */   
/*      */   private int find(long k) {
/*  261 */     if (this.strategy.equals(k, 0L)) return this.containsNullKey ? this.n : -(this.n + 1);
/*      */     
/*  263 */     long[] key = this.key;
/*      */     long curr;
/*      */     int pos;
/*  266 */     if ((curr = key[pos = HashCommon.mix(this.strategy.hashCode(k)) & this.mask]) == 0L) return -(pos + 1); 
/*  267 */     if (this.strategy.equals(k, curr)) return pos;
/*      */     
/*      */     while (true) {
/*  270 */       if ((curr = key[pos = pos + 1 & this.mask]) == 0L) return -(pos + 1); 
/*  271 */       if (this.strategy.equals(k, curr)) return pos; 
/*      */     } 
/*      */   }
/*      */   
/*      */   private void insert(int pos, long k, int v) {
/*  276 */     if (pos == this.n) this.containsNullKey = true; 
/*  277 */     this.key[pos] = k;
/*  278 */     this.value[pos] = v;
/*  279 */     if (this.size++ >= this.maxFill) rehash(HashCommon.arraySize(this.size + 1, this.f));
/*      */   
/*      */   }
/*      */ 
/*      */   
/*      */   public int put(long k, int v) {
/*  285 */     int pos = find(k);
/*  286 */     if (pos < 0) {
/*  287 */       insert(-pos - 1, k, v);
/*  288 */       return this.defRetValue;
/*      */     } 
/*  290 */     int oldValue = this.value[pos];
/*  291 */     this.value[pos] = v;
/*  292 */     return oldValue;
/*      */   }
/*      */   
/*      */   private int addToValue(int pos, int incr) {
/*  296 */     int oldValue = this.value[pos];
/*  297 */     this.value[pos] = oldValue + incr;
/*  298 */     return oldValue;
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
/*      */   public int addTo(long k, int incr) {
/*      */     int pos;
/*  316 */     if (this.strategy.equals(k, 0L)) {
/*  317 */       if (this.containsNullKey) return addToValue(this.n, incr); 
/*  318 */       pos = this.n;
/*  319 */       this.containsNullKey = true;
/*      */     } else {
/*      */       
/*  322 */       long[] key = this.key;
/*      */       long curr;
/*  324 */       if ((curr = key[pos = HashCommon.mix(this.strategy.hashCode(k)) & this.mask]) != 0L) {
/*  325 */         if (this.strategy.equals(curr, k)) return addToValue(pos, incr); 
/*  326 */         while ((curr = key[pos = pos + 1 & this.mask]) != 0L) { if (this.strategy.equals(curr, k)) return addToValue(pos, incr);  }
/*      */       
/*      */       } 
/*  329 */     }  this.key[pos] = k;
/*  330 */     this.value[pos] = this.defRetValue + incr;
/*  331 */     if (this.size++ >= this.maxFill) rehash(HashCommon.arraySize(this.size + 1, this.f));
/*      */     
/*  333 */     return this.defRetValue;
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
/*  346 */     long[] key = this.key; while (true) {
/*      */       long curr; int last;
/*  348 */       pos = (last = pos) + 1 & this.mask;
/*      */       while (true) {
/*  350 */         if ((curr = key[pos]) == 0L) {
/*  351 */           key[last] = 0L;
/*      */           return;
/*      */         } 
/*  354 */         int slot = HashCommon.mix(this.strategy.hashCode(curr)) & this.mask;
/*  355 */         if ((last <= pos) ? (last >= slot || slot > pos) : (last >= slot && slot > pos))
/*  356 */           break;  pos = pos + 1 & this.mask;
/*      */       } 
/*  358 */       key[last] = curr;
/*  359 */       this.value[last] = this.value[pos];
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public int remove(long k) {
/*  366 */     if (this.strategy.equals(k, 0L)) {
/*  367 */       if (this.containsNullKey) return removeNullEntry(); 
/*  368 */       return this.defRetValue;
/*      */     } 
/*      */     
/*  371 */     long[] key = this.key;
/*      */     long curr;
/*      */     int pos;
/*  374 */     if ((curr = key[pos = HashCommon.mix(this.strategy.hashCode(k)) & this.mask]) == 0L) return this.defRetValue; 
/*  375 */     if (this.strategy.equals(k, curr)) return removeEntry(pos); 
/*      */     while (true) {
/*  377 */       if ((curr = key[pos = pos + 1 & this.mask]) == 0L) return this.defRetValue; 
/*  378 */       if (this.strategy.equals(k, curr)) return removeEntry(pos);
/*      */     
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   public int get(long k) {
/*  385 */     if (this.strategy.equals(k, 0L)) return this.containsNullKey ? this.value[this.n] : this.defRetValue;
/*      */     
/*  387 */     long[] key = this.key;
/*      */     long curr;
/*      */     int pos;
/*  390 */     if ((curr = key[pos = HashCommon.mix(this.strategy.hashCode(k)) & this.mask]) == 0L) return this.defRetValue; 
/*  391 */     if (this.strategy.equals(k, curr)) return this.value[pos];
/*      */     
/*      */     while (true) {
/*  394 */       if ((curr = key[pos = pos + 1 & this.mask]) == 0L) return this.defRetValue; 
/*  395 */       if (this.strategy.equals(k, curr)) return this.value[pos];
/*      */     
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   public boolean containsKey(long k) {
/*  402 */     if (this.strategy.equals(k, 0L)) return this.containsNullKey;
/*      */     
/*  404 */     long[] key = this.key;
/*      */     long curr;
/*      */     int pos;
/*  407 */     if ((curr = key[pos = HashCommon.mix(this.strategy.hashCode(k)) & this.mask]) == 0L) return false; 
/*  408 */     if (this.strategy.equals(k, curr)) return true;
/*      */     
/*      */     while (true) {
/*  411 */       if ((curr = key[pos = pos + 1 & this.mask]) == 0L) return false; 
/*  412 */       if (this.strategy.equals(k, curr)) return true;
/*      */     
/*      */     } 
/*      */   }
/*      */   
/*      */   public boolean containsValue(int v) {
/*  418 */     int[] value = this.value;
/*  419 */     long[] key = this.key;
/*  420 */     if (this.containsNullKey && value[this.n] == v) return true; 
/*  421 */     for (int i = this.n; i-- != 0;) { if (key[i] != 0L && value[i] == v) return true;  }
/*  422 */      return false;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public int getOrDefault(long k, int defaultValue) {
/*  429 */     if (this.strategy.equals(k, 0L)) return this.containsNullKey ? this.value[this.n] : defaultValue;
/*      */     
/*  431 */     long[] key = this.key;
/*      */     long curr;
/*      */     int pos;
/*  434 */     if ((curr = key[pos = HashCommon.mix(this.strategy.hashCode(k)) & this.mask]) == 0L) return defaultValue; 
/*  435 */     if (this.strategy.equals(k, curr)) return this.value[pos];
/*      */     
/*      */     while (true) {
/*  438 */       if ((curr = key[pos = pos + 1 & this.mask]) == 0L) return defaultValue; 
/*  439 */       if (this.strategy.equals(k, curr)) return this.value[pos];
/*      */     
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   public int putIfAbsent(long k, int v) {
/*  446 */     int pos = find(k);
/*  447 */     if (pos >= 0) return this.value[pos]; 
/*  448 */     insert(-pos - 1, k, v);
/*  449 */     return this.defRetValue;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean remove(long k, int v) {
/*  456 */     if (this.strategy.equals(k, 0L)) {
/*  457 */       if (this.containsNullKey && v == this.value[this.n]) {
/*  458 */         removeNullEntry();
/*  459 */         return true;
/*      */       } 
/*  461 */       return false;
/*      */     } 
/*      */     
/*  464 */     long[] key = this.key;
/*      */     long curr;
/*      */     int pos;
/*  467 */     if ((curr = key[pos = HashCommon.mix(this.strategy.hashCode(k)) & this.mask]) == 0L) return false; 
/*  468 */     if (this.strategy.equals(k, curr) && v == this.value[pos]) {
/*  469 */       removeEntry(pos);
/*  470 */       return true;
/*      */     } 
/*      */     while (true) {
/*  473 */       if ((curr = key[pos = pos + 1 & this.mask]) == 0L) return false; 
/*  474 */       if (this.strategy.equals(k, curr) && v == this.value[pos]) {
/*  475 */         removeEntry(pos);
/*  476 */         return true;
/*      */       } 
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean replace(long k, int oldValue, int v) {
/*  484 */     int pos = find(k);
/*  485 */     if (pos < 0 || oldValue != this.value[pos]) return false; 
/*  486 */     this.value[pos] = v;
/*  487 */     return true;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public int replace(long k, int v) {
/*  493 */     int pos = find(k);
/*  494 */     if (pos < 0) return this.defRetValue; 
/*  495 */     int oldValue = this.value[pos];
/*  496 */     this.value[pos] = v;
/*  497 */     return oldValue;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public int computeIfAbsent(long k, LongToIntFunction mappingFunction) {
/*  503 */     Objects.requireNonNull(mappingFunction);
/*  504 */     int pos = find(k);
/*  505 */     if (pos >= 0) return this.value[pos]; 
/*  506 */     int newValue = mappingFunction.applyAsInt(k);
/*  507 */     insert(-pos - 1, k, newValue);
/*  508 */     return newValue;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public int computeIfAbsent(long key, Long2IntFunction mappingFunction) {
/*  514 */     Objects.requireNonNull(mappingFunction);
/*  515 */     int pos = find(key);
/*  516 */     if (pos >= 0) return this.value[pos]; 
/*  517 */     if (!mappingFunction.containsKey(key)) return this.defRetValue; 
/*  518 */     int newValue = mappingFunction.get(key);
/*  519 */     insert(-pos - 1, key, newValue);
/*  520 */     return newValue;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public int computeIfAbsentNullable(long k, LongFunction<? extends Integer> mappingFunction) {
/*  526 */     Objects.requireNonNull(mappingFunction);
/*  527 */     int pos = find(k);
/*  528 */     if (pos >= 0) return this.value[pos]; 
/*  529 */     Integer newValue = mappingFunction.apply(k);
/*  530 */     if (newValue == null) return this.defRetValue; 
/*  531 */     int v = newValue.intValue();
/*  532 */     insert(-pos - 1, k, v);
/*  533 */     return v;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public int computeIfPresent(long k, BiFunction<? super Long, ? super Integer, ? extends Integer> remappingFunction) {
/*  539 */     Objects.requireNonNull(remappingFunction);
/*  540 */     int pos = find(k);
/*  541 */     if (pos < 0) return this.defRetValue; 
/*  542 */     Integer newValue = remappingFunction.apply(Long.valueOf(k), Integer.valueOf(this.value[pos]));
/*  543 */     if (newValue == null) {
/*  544 */       if (this.strategy.equals(k, 0L)) { removeNullEntry(); }
/*  545 */       else { removeEntry(pos); }
/*  546 */        return this.defRetValue;
/*      */     } 
/*  548 */     this.value[pos] = newValue.intValue(); return newValue.intValue();
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public int compute(long k, BiFunction<? super Long, ? super Integer, ? extends Integer> remappingFunction) {
/*  554 */     Objects.requireNonNull(remappingFunction);
/*  555 */     int pos = find(k);
/*  556 */     Integer newValue = remappingFunction.apply(Long.valueOf(k), (pos >= 0) ? Integer.valueOf(this.value[pos]) : null);
/*  557 */     if (newValue == null) {
/*  558 */       if (pos >= 0)
/*  559 */         if (this.strategy.equals(k, 0L)) { removeNullEntry(); }
/*  560 */         else { removeEntry(pos); }
/*      */          
/*  562 */       return this.defRetValue;
/*      */     } 
/*  564 */     int newVal = newValue.intValue();
/*  565 */     if (pos < 0) {
/*  566 */       insert(-pos - 1, k, newVal);
/*  567 */       return newVal;
/*      */     } 
/*  569 */     this.value[pos] = newVal; return newVal;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public int merge(long k, int v, BiFunction<? super Integer, ? super Integer, ? extends Integer> remappingFunction) {
/*  575 */     Objects.requireNonNull(remappingFunction);
/*      */     
/*  577 */     int pos = find(k);
/*  578 */     if (pos < 0) {
/*  579 */       if (pos < 0) { insert(-pos - 1, k, v); }
/*  580 */       else { this.value[pos] = v; }
/*  581 */        return v;
/*      */     } 
/*  583 */     Integer newValue = remappingFunction.apply(Integer.valueOf(this.value[pos]), Integer.valueOf(v));
/*  584 */     if (newValue == null) {
/*  585 */       if (this.strategy.equals(k, 0L)) { removeNullEntry(); }
/*  586 */       else { removeEntry(pos); }
/*  587 */        return this.defRetValue;
/*      */     } 
/*  589 */     this.value[pos] = newValue.intValue(); return newValue.intValue();
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
/*  600 */     if (this.size == 0)
/*  601 */       return;  this.size = 0;
/*  602 */     this.containsNullKey = false;
/*  603 */     Arrays.fill(this.key, 0L);
/*      */   }
/*      */ 
/*      */   
/*      */   public int size() {
/*  608 */     return this.size;
/*      */   }
/*      */ 
/*      */   
/*      */   public boolean isEmpty() {
/*  613 */     return (this.size == 0);
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   final class MapEntry
/*      */     implements Long2IntMap.Entry, Map.Entry<Long, Integer>, LongIntPair
/*      */   {
/*      */     int index;
/*      */ 
/*      */ 
/*      */     
/*      */     MapEntry(int index) {
/*  626 */       this.index = index;
/*      */     }
/*      */ 
/*      */     
/*      */     MapEntry() {}
/*      */ 
/*      */     
/*      */     public long getLongKey() {
/*  634 */       return Long2IntOpenCustomHashMap.this.key[this.index];
/*      */     }
/*      */ 
/*      */     
/*      */     public long leftLong() {
/*  639 */       return Long2IntOpenCustomHashMap.this.key[this.index];
/*      */     }
/*      */ 
/*      */     
/*      */     public int getIntValue() {
/*  644 */       return Long2IntOpenCustomHashMap.this.value[this.index];
/*      */     }
/*      */ 
/*      */     
/*      */     public int rightInt() {
/*  649 */       return Long2IntOpenCustomHashMap.this.value[this.index];
/*      */     }
/*      */ 
/*      */     
/*      */     public int setValue(int v) {
/*  654 */       int oldValue = Long2IntOpenCustomHashMap.this.value[this.index];
/*  655 */       Long2IntOpenCustomHashMap.this.value[this.index] = v;
/*  656 */       return oldValue;
/*      */     }
/*      */ 
/*      */     
/*      */     public LongIntPair right(int v) {
/*  661 */       Long2IntOpenCustomHashMap.this.value[this.index] = v;
/*  662 */       return this;
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
/*  673 */       return Long.valueOf(Long2IntOpenCustomHashMap.this.key[this.index]);
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     @Deprecated
/*      */     public Integer getValue() {
/*  684 */       return Integer.valueOf(Long2IntOpenCustomHashMap.this.value[this.index]);
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     @Deprecated
/*      */     public Integer setValue(Integer v) {
/*  695 */       return Integer.valueOf(setValue(v.intValue()));
/*      */     }
/*      */ 
/*      */ 
/*      */     
/*      */     public boolean equals(Object o) {
/*  701 */       if (!(o instanceof Map.Entry)) return false; 
/*  702 */       Map.Entry<Long, Integer> e = (Map.Entry<Long, Integer>)o;
/*  703 */       return (Long2IntOpenCustomHashMap.this.strategy.equals(Long2IntOpenCustomHashMap.this.key[this.index], ((Long)e.getKey()).longValue()) && Long2IntOpenCustomHashMap.this.value[this.index] == ((Integer)e.getValue()).intValue());
/*      */     }
/*      */ 
/*      */     
/*      */     public int hashCode() {
/*  708 */       return Long2IntOpenCustomHashMap.this.strategy.hashCode(Long2IntOpenCustomHashMap.this.key[this.index]) ^ Long2IntOpenCustomHashMap.this.value[this.index];
/*      */     }
/*      */ 
/*      */     
/*      */     public String toString() {
/*  713 */       return Long2IntOpenCustomHashMap.this.key[this.index] + "=>" + Long2IntOpenCustomHashMap.this.value[this.index];
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
/*  724 */     int pos = Long2IntOpenCustomHashMap.this.n;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  730 */     int last = -1;
/*      */     
/*  732 */     int c = Long2IntOpenCustomHashMap.this.size;
/*      */     
/*  734 */     boolean mustReturnNullKey = Long2IntOpenCustomHashMap.this.containsNullKey;
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
/*  745 */       return (this.c != 0);
/*      */     }
/*      */     
/*      */     public int nextEntry() {
/*  749 */       if (!hasNext()) throw new NoSuchElementException(); 
/*  750 */       this.c--;
/*  751 */       if (this.mustReturnNullKey) {
/*  752 */         this.mustReturnNullKey = false;
/*  753 */         return this.last = Long2IntOpenCustomHashMap.this.n;
/*      */       } 
/*  755 */       long[] key = Long2IntOpenCustomHashMap.this.key;
/*      */       while (true) {
/*  757 */         if (--this.pos < 0) {
/*      */           
/*  759 */           this.last = Integer.MIN_VALUE;
/*  760 */           long k = this.wrapped.getLong(-this.pos - 1);
/*  761 */           int p = HashCommon.mix(Long2IntOpenCustomHashMap.this.strategy.hashCode(k)) & Long2IntOpenCustomHashMap.this.mask;
/*  762 */           for (; !Long2IntOpenCustomHashMap.this.strategy.equals(k, key[p]); p = p + 1 & Long2IntOpenCustomHashMap.this.mask);
/*  763 */           return p;
/*      */         } 
/*  765 */         if (key[this.pos] != 0L) return this.last = this.pos; 
/*      */       } 
/*      */     }
/*      */     
/*      */     public void forEachRemaining(ConsumerType action) {
/*  770 */       if (this.mustReturnNullKey) {
/*  771 */         this.mustReturnNullKey = false;
/*  772 */         acceptOnIndex(action, this.last = Long2IntOpenCustomHashMap.this.n);
/*  773 */         this.c--;
/*      */       } 
/*  775 */       long[] key = Long2IntOpenCustomHashMap.this.key;
/*  776 */       while (this.c != 0) {
/*  777 */         if (--this.pos < 0) {
/*      */           
/*  779 */           this.last = Integer.MIN_VALUE;
/*  780 */           long k = this.wrapped.getLong(-this.pos - 1);
/*  781 */           int p = HashCommon.mix(Long2IntOpenCustomHashMap.this.strategy.hashCode(k)) & Long2IntOpenCustomHashMap.this.mask;
/*  782 */           for (; !Long2IntOpenCustomHashMap.this.strategy.equals(k, key[p]); p = p + 1 & Long2IntOpenCustomHashMap.this.mask);
/*  783 */           acceptOnIndex(action, p);
/*  784 */           this.c--; continue;
/*  785 */         }  if (key[this.pos] != 0L) {
/*  786 */           acceptOnIndex(action, this.last = this.pos);
/*  787 */           this.c--;
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
/*  802 */       long[] key = Long2IntOpenCustomHashMap.this.key; while (true) {
/*      */         long curr; int last;
/*  804 */         pos = (last = pos) + 1 & Long2IntOpenCustomHashMap.this.mask;
/*      */         while (true) {
/*  806 */           if ((curr = key[pos]) == 0L) {
/*  807 */             key[last] = 0L;
/*      */             return;
/*      */           } 
/*  810 */           int slot = HashCommon.mix(Long2IntOpenCustomHashMap.this.strategy.hashCode(curr)) & Long2IntOpenCustomHashMap.this.mask;
/*  811 */           if ((last <= pos) ? (last >= slot || slot > pos) : (last >= slot && slot > pos))
/*  812 */             break;  pos = pos + 1 & Long2IntOpenCustomHashMap.this.mask;
/*      */         } 
/*  814 */         if (pos < last) {
/*  815 */           if (this.wrapped == null) this.wrapped = new LongArrayList(2); 
/*  816 */           this.wrapped.add(key[pos]);
/*      */         } 
/*  818 */         key[last] = curr;
/*  819 */         Long2IntOpenCustomHashMap.this.value[last] = Long2IntOpenCustomHashMap.this.value[pos];
/*      */       } 
/*      */     }
/*      */     
/*      */     public void remove() {
/*  824 */       if (this.last == -1) throw new IllegalStateException(); 
/*  825 */       if (this.last == Long2IntOpenCustomHashMap.this.n)
/*  826 */       { Long2IntOpenCustomHashMap.this.containsNullKey = false; }
/*  827 */       else if (this.pos >= 0) { shiftKeys(this.last); }
/*      */       else
/*      */       
/*  830 */       { Long2IntOpenCustomHashMap.this.remove(this.wrapped.getLong(-this.pos - 1));
/*  831 */         this.last = -1;
/*      */         return; }
/*      */       
/*  834 */       Long2IntOpenCustomHashMap.this.size--;
/*  835 */       this.last = -1;
/*      */     }
/*      */ 
/*      */     
/*      */     public int skip(int n) {
/*  840 */       int i = n;
/*  841 */       for (; i-- != 0 && hasNext(); nextEntry());
/*  842 */       return n - i - 1;
/*      */     }
/*      */     
/*      */     private MapIterator() {}
/*      */     
/*      */     abstract void acceptOnIndex(ConsumerType param1ConsumerType, int param1Int);
/*      */   }
/*      */   
/*      */   private final class EntryIterator extends MapIterator<Consumer<? super Long2IntMap.Entry>> implements ObjectIterator<Long2IntMap.Entry> { public Long2IntOpenCustomHashMap.MapEntry next() {
/*  851 */       return this.entry = new Long2IntOpenCustomHashMap.MapEntry(nextEntry());
/*      */     }
/*      */     private Long2IntOpenCustomHashMap.MapEntry entry;
/*      */     private EntryIterator() {}
/*      */     
/*      */     final void acceptOnIndex(Consumer<? super Long2IntMap.Entry> action, int index) {
/*  857 */       action.accept(this.entry = new Long2IntOpenCustomHashMap.MapEntry(index));
/*      */     }
/*      */ 
/*      */     
/*      */     public void remove() {
/*  862 */       super.remove();
/*  863 */       this.entry.index = -1;
/*      */     } }
/*      */   
/*      */   private final class FastEntryIterator extends MapIterator<Consumer<? super Long2IntMap.Entry>> implements ObjectIterator<Long2IntMap.Entry> {
/*      */     private FastEntryIterator() {
/*  868 */       this.entry = new Long2IntOpenCustomHashMap.MapEntry();
/*      */     }
/*      */     private final Long2IntOpenCustomHashMap.MapEntry entry;
/*      */     public Long2IntOpenCustomHashMap.MapEntry next() {
/*  872 */       this.entry.index = nextEntry();
/*  873 */       return this.entry;
/*      */     }
/*      */ 
/*      */ 
/*      */     
/*      */     final void acceptOnIndex(Consumer<? super Long2IntMap.Entry> action, int index) {
/*  879 */       this.entry.index = index;
/*  880 */       action.accept(this.entry);
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private abstract class MapSpliterator<ConsumerType, SplitType extends MapSpliterator<ConsumerType, SplitType>>
/*      */   {
/*  889 */     int pos = 0;
/*      */     
/*  891 */     int max = Long2IntOpenCustomHashMap.this.n;
/*      */     
/*  893 */     int c = 0;
/*      */     
/*  895 */     boolean mustReturnNull = Long2IntOpenCustomHashMap.this.containsNullKey;
/*      */     
/*      */     boolean hasSplit = false;
/*      */     
/*      */     MapSpliterator() {}
/*      */     
/*      */     MapSpliterator(int pos, int max, boolean mustReturnNull, boolean hasSplit) {
/*  902 */       this.pos = pos;
/*  903 */       this.max = max;
/*  904 */       this.mustReturnNull = mustReturnNull;
/*  905 */       this.hasSplit = hasSplit;
/*      */     }
/*      */     
/*      */     abstract void acceptOnIndex(ConsumerType param1ConsumerType, int param1Int);
/*      */     
/*      */     abstract SplitType makeForSplit(int param1Int1, int param1Int2, boolean param1Boolean);
/*      */     
/*      */     public boolean tryAdvance(ConsumerType action) {
/*  913 */       if (this.mustReturnNull) {
/*  914 */         this.mustReturnNull = false;
/*  915 */         this.c++;
/*  916 */         acceptOnIndex(action, Long2IntOpenCustomHashMap.this.n);
/*  917 */         return true;
/*      */       } 
/*  919 */       long[] key = Long2IntOpenCustomHashMap.this.key;
/*  920 */       while (this.pos < this.max) {
/*  921 */         if (key[this.pos] != 0L) {
/*  922 */           this.c++;
/*  923 */           acceptOnIndex(action, this.pos++);
/*  924 */           return true;
/*      */         } 
/*  926 */         this.pos++;
/*      */       } 
/*  928 */       return false;
/*      */     }
/*      */     
/*      */     public void forEachRemaining(ConsumerType action) {
/*  932 */       if (this.mustReturnNull) {
/*  933 */         this.mustReturnNull = false;
/*  934 */         this.c++;
/*  935 */         acceptOnIndex(action, Long2IntOpenCustomHashMap.this.n);
/*      */       } 
/*  937 */       long[] key = Long2IntOpenCustomHashMap.this.key;
/*  938 */       while (this.pos < this.max) {
/*  939 */         if (key[this.pos] != 0L) {
/*  940 */           acceptOnIndex(action, this.pos);
/*  941 */           this.c++;
/*      */         } 
/*  943 */         this.pos++;
/*      */       } 
/*      */     }
/*      */     
/*      */     public long estimateSize() {
/*  948 */       if (!this.hasSplit)
/*      */       {
/*  950 */         return (Long2IntOpenCustomHashMap.this.size - this.c);
/*      */       }
/*      */ 
/*      */ 
/*      */       
/*  955 */       return Math.min((Long2IntOpenCustomHashMap.this.size - this.c), (long)(Long2IntOpenCustomHashMap.this.realSize() / Long2IntOpenCustomHashMap.this.n * (this.max - this.pos)) + (this.mustReturnNull ? 1L : 0L));
/*      */     }
/*      */ 
/*      */     
/*      */     public SplitType trySplit() {
/*  960 */       if (this.pos >= this.max - 1) return null; 
/*  961 */       int retLen = this.max - this.pos >> 1;
/*  962 */       if (retLen <= 1) return null; 
/*  963 */       int myNewPos = this.pos + retLen;
/*  964 */       int retPos = this.pos;
/*  965 */       int retMax = myNewPos;
/*      */ 
/*      */ 
/*      */       
/*  969 */       SplitType split = makeForSplit(retPos, retMax, this.mustReturnNull);
/*  970 */       this.pos = myNewPos;
/*  971 */       this.mustReturnNull = false;
/*  972 */       this.hasSplit = true;
/*  973 */       return split;
/*      */     }
/*      */     
/*      */     public long skip(long n) {
/*  977 */       if (n < 0L) throw new IllegalArgumentException("Argument must be nonnegative: " + n); 
/*  978 */       if (n == 0L) return 0L; 
/*  979 */       long skipped = 0L;
/*  980 */       if (this.mustReturnNull) {
/*  981 */         this.mustReturnNull = false;
/*  982 */         skipped++;
/*  983 */         n--;
/*      */       } 
/*  985 */       long[] key = Long2IntOpenCustomHashMap.this.key;
/*  986 */       while (this.pos < this.max && n > 0L) {
/*  987 */         if (key[this.pos++] != 0L) {
/*  988 */           skipped++;
/*  989 */           n--;
/*      */         } 
/*      */       } 
/*  992 */       return skipped;
/*      */     }
/*      */   }
/*      */   
/*      */   private final class EntrySpliterator
/*      */     extends MapSpliterator<Consumer<? super Long2IntMap.Entry>, EntrySpliterator> implements ObjectSpliterator<Long2IntMap.Entry> {
/*      */     private static final int POST_SPLIT_CHARACTERISTICS = 1;
/*      */     
/*      */     EntrySpliterator() {}
/*      */     
/*      */     EntrySpliterator(int pos, int max, boolean mustReturnNull, boolean hasSplit) {
/* 1003 */       super(pos, max, mustReturnNull, hasSplit);
/*      */     }
/*      */ 
/*      */     
/*      */     public int characteristics() {
/* 1008 */       return this.hasSplit ? 1 : 65;
/*      */     }
/*      */ 
/*      */     
/*      */     final void acceptOnIndex(Consumer<? super Long2IntMap.Entry> action, int index) {
/* 1013 */       action.accept(new Long2IntOpenCustomHashMap.MapEntry(index));
/*      */     }
/*      */ 
/*      */     
/*      */     final EntrySpliterator makeForSplit(int pos, int max, boolean mustReturnNull) {
/* 1018 */       return new EntrySpliterator(pos, max, mustReturnNull, true);
/*      */     } }
/*      */   
/*      */   private final class MapEntrySet extends AbstractObjectSet<Long2IntMap.Entry> implements Long2IntMap.FastEntrySet {
/*      */     private MapEntrySet() {}
/*      */     
/*      */     public ObjectIterator<Long2IntMap.Entry> iterator() {
/* 1025 */       return new Long2IntOpenCustomHashMap.EntryIterator();
/*      */     }
/*      */ 
/*      */     
/*      */     public ObjectIterator<Long2IntMap.Entry> fastIterator() {
/* 1030 */       return new Long2IntOpenCustomHashMap.FastEntryIterator();
/*      */     }
/*      */ 
/*      */     
/*      */     public ObjectSpliterator<Long2IntMap.Entry> spliterator() {
/* 1035 */       return new Long2IntOpenCustomHashMap.EntrySpliterator();
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public boolean contains(Object o) {
/* 1042 */       if (!(o instanceof Map.Entry)) return false; 
/* 1043 */       Map.Entry<?, ?> e = (Map.Entry<?, ?>)o;
/* 1044 */       if (e.getKey() == null || !(e.getKey() instanceof Long)) return false; 
/* 1045 */       if (e.getValue() == null || !(e.getValue() instanceof Integer)) return false; 
/* 1046 */       long k = ((Long)e.getKey()).longValue();
/* 1047 */       int v = ((Integer)e.getValue()).intValue();
/* 1048 */       if (Long2IntOpenCustomHashMap.this.strategy.equals(k, 0L)) return (Long2IntOpenCustomHashMap.this.containsNullKey && Long2IntOpenCustomHashMap.this.value[Long2IntOpenCustomHashMap.this.n] == v);
/*      */       
/* 1050 */       long[] key = Long2IntOpenCustomHashMap.this.key;
/*      */       long curr;
/*      */       int pos;
/* 1053 */       if ((curr = key[pos = HashCommon.mix(Long2IntOpenCustomHashMap.this.strategy.hashCode(k)) & Long2IntOpenCustomHashMap.this.mask]) == 0L) return false; 
/* 1054 */       if (Long2IntOpenCustomHashMap.this.strategy.equals(k, curr)) return (Long2IntOpenCustomHashMap.this.value[pos] == v);
/*      */       
/*      */       while (true) {
/* 1057 */         if ((curr = key[pos = pos + 1 & Long2IntOpenCustomHashMap.this.mask]) == 0L) return false; 
/* 1058 */         if (Long2IntOpenCustomHashMap.this.strategy.equals(k, curr)) return (Long2IntOpenCustomHashMap.this.value[pos] == v);
/*      */       
/*      */       } 
/*      */     }
/*      */ 
/*      */     
/*      */     public boolean remove(Object o) {
/* 1065 */       if (!(o instanceof Map.Entry)) return false; 
/* 1066 */       Map.Entry<?, ?> e = (Map.Entry<?, ?>)o;
/* 1067 */       if (e.getKey() == null || !(e.getKey() instanceof Long)) return false; 
/* 1068 */       if (e.getValue() == null || !(e.getValue() instanceof Integer)) return false; 
/* 1069 */       long k = ((Long)e.getKey()).longValue();
/* 1070 */       int v = ((Integer)e.getValue()).intValue();
/* 1071 */       if (Long2IntOpenCustomHashMap.this.strategy.equals(k, 0L)) {
/* 1072 */         if (Long2IntOpenCustomHashMap.this.containsNullKey && Long2IntOpenCustomHashMap.this.value[Long2IntOpenCustomHashMap.this.n] == v) {
/* 1073 */           Long2IntOpenCustomHashMap.this.removeNullEntry();
/* 1074 */           return true;
/*      */         } 
/* 1076 */         return false;
/*      */       } 
/*      */       
/* 1079 */       long[] key = Long2IntOpenCustomHashMap.this.key;
/*      */       long curr;
/*      */       int pos;
/* 1082 */       if ((curr = key[pos = HashCommon.mix(Long2IntOpenCustomHashMap.this.strategy.hashCode(k)) & Long2IntOpenCustomHashMap.this.mask]) == 0L) return false; 
/* 1083 */       if (Long2IntOpenCustomHashMap.this.strategy.equals(curr, k)) {
/* 1084 */         if (Long2IntOpenCustomHashMap.this.value[pos] == v) {
/* 1085 */           Long2IntOpenCustomHashMap.this.removeEntry(pos);
/* 1086 */           return true;
/*      */         } 
/* 1088 */         return false;
/*      */       } 
/*      */       while (true) {
/* 1091 */         if ((curr = key[pos = pos + 1 & Long2IntOpenCustomHashMap.this.mask]) == 0L) return false; 
/* 1092 */         if (Long2IntOpenCustomHashMap.this.strategy.equals(curr, k) && 
/* 1093 */           Long2IntOpenCustomHashMap.this.value[pos] == v) {
/* 1094 */           Long2IntOpenCustomHashMap.this.removeEntry(pos);
/* 1095 */           return true;
/*      */         } 
/*      */       } 
/*      */     }
/*      */ 
/*      */ 
/*      */     
/*      */     public int size() {
/* 1103 */       return Long2IntOpenCustomHashMap.this.size;
/*      */     }
/*      */ 
/*      */     
/*      */     public void clear() {
/* 1108 */       Long2IntOpenCustomHashMap.this.clear();
/*      */     }
/*      */ 
/*      */ 
/*      */     
/*      */     public void forEach(Consumer<? super Long2IntMap.Entry> consumer) {
/* 1114 */       if (Long2IntOpenCustomHashMap.this.containsNullKey) consumer.accept(new Long2IntOpenCustomHashMap.MapEntry(Long2IntOpenCustomHashMap.this.n)); 
/* 1115 */       for (int pos = Long2IntOpenCustomHashMap.this.n; pos-- != 0;) { if (Long2IntOpenCustomHashMap.this.key[pos] != 0L) consumer.accept(new Long2IntOpenCustomHashMap.MapEntry(pos));
/*      */          }
/*      */     
/*      */     }
/*      */     
/*      */     public void fastForEach(Consumer<? super Long2IntMap.Entry> consumer) {
/* 1121 */       Long2IntOpenCustomHashMap.MapEntry entry = new Long2IntOpenCustomHashMap.MapEntry();
/* 1122 */       if (Long2IntOpenCustomHashMap.this.containsNullKey) {
/* 1123 */         entry.index = Long2IntOpenCustomHashMap.this.n;
/* 1124 */         consumer.accept(entry);
/*      */       } 
/* 1126 */       for (int pos = Long2IntOpenCustomHashMap.this.n; pos-- != 0;) { if (Long2IntOpenCustomHashMap.this.key[pos] != 0L) {
/* 1127 */           entry.index = pos;
/* 1128 */           consumer.accept(entry);
/*      */         }  }
/*      */     
/*      */     }
/*      */   }
/*      */   
/*      */   public Long2IntMap.FastEntrySet long2IntEntrySet() {
/* 1135 */     if (this.entries == null) this.entries = new MapEntrySet(); 
/* 1136 */     return this.entries;
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
/* 1157 */       action.accept(Long2IntOpenCustomHashMap.this.key[index]);
/*      */     }
/*      */ 
/*      */     
/*      */     public long nextLong() {
/* 1162 */       return Long2IntOpenCustomHashMap.this.key[nextEntry()];
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
/* 1173 */       super(pos, max, mustReturnNull, hasSplit);
/*      */     }
/*      */ 
/*      */     
/*      */     public int characteristics() {
/* 1178 */       return this.hasSplit ? 257 : 321;
/*      */     }
/*      */ 
/*      */     
/*      */     final void acceptOnIndex(LongConsumer action, int index) {
/* 1183 */       action.accept(Long2IntOpenCustomHashMap.this.key[index]);
/*      */     }
/*      */ 
/*      */     
/*      */     final KeySpliterator makeForSplit(int pos, int max, boolean mustReturnNull) {
/* 1188 */       return new KeySpliterator(pos, max, mustReturnNull, true);
/*      */     } }
/*      */   
/*      */   private final class KeySet extends AbstractLongSet {
/*      */     private KeySet() {}
/*      */     
/*      */     public LongIterator iterator() {
/* 1195 */       return new Long2IntOpenCustomHashMap.KeyIterator();
/*      */     }
/*      */ 
/*      */     
/*      */     public LongSpliterator spliterator() {
/* 1200 */       return new Long2IntOpenCustomHashMap.KeySpliterator();
/*      */     }
/*      */ 
/*      */ 
/*      */     
/*      */     public void forEach(LongConsumer consumer) {
/* 1206 */       if (Long2IntOpenCustomHashMap.this.containsNullKey) consumer.accept(Long2IntOpenCustomHashMap.this.key[Long2IntOpenCustomHashMap.this.n]); 
/* 1207 */       for (int pos = Long2IntOpenCustomHashMap.this.n; pos-- != 0; ) {
/* 1208 */         long k = Long2IntOpenCustomHashMap.this.key[pos];
/* 1209 */         if (k != 0L) consumer.accept(k);
/*      */       
/*      */       } 
/*      */     }
/*      */     
/*      */     public int size() {
/* 1215 */       return Long2IntOpenCustomHashMap.this.size;
/*      */     }
/*      */ 
/*      */     
/*      */     public boolean contains(long k) {
/* 1220 */       return Long2IntOpenCustomHashMap.this.containsKey(k);
/*      */     }
/*      */ 
/*      */     
/*      */     public boolean remove(long k) {
/* 1225 */       int oldSize = Long2IntOpenCustomHashMap.this.size;
/* 1226 */       Long2IntOpenCustomHashMap.this.remove(k);
/* 1227 */       return (Long2IntOpenCustomHashMap.this.size != oldSize);
/*      */     }
/*      */ 
/*      */     
/*      */     public void clear() {
/* 1232 */       Long2IntOpenCustomHashMap.this.clear();
/*      */     }
/*      */   }
/*      */ 
/*      */   
/*      */   public LongSet keySet() {
/* 1238 */     if (this.keys == null) this.keys = new KeySet(); 
/* 1239 */     return this.keys;
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
/*      */     extends MapIterator<IntConsumer>
/*      */     implements IntIterator
/*      */   {
/*      */     final void acceptOnIndex(IntConsumer action, int index) {
/* 1260 */       action.accept(Long2IntOpenCustomHashMap.this.value[index]);
/*      */     }
/*      */ 
/*      */     
/*      */     public int nextInt() {
/* 1265 */       return Long2IntOpenCustomHashMap.this.value[nextEntry()];
/*      */     }
/*      */   }
/*      */   
/*      */   private final class ValueSpliterator
/*      */     extends MapSpliterator<IntConsumer, ValueSpliterator> implements IntSpliterator {
/*      */     private static final int POST_SPLIT_CHARACTERISTICS = 256;
/*      */     
/*      */     ValueSpliterator() {}
/*      */     
/*      */     ValueSpliterator(int pos, int max, boolean mustReturnNull, boolean hasSplit) {
/* 1276 */       super(pos, max, mustReturnNull, hasSplit);
/*      */     }
/*      */ 
/*      */     
/*      */     public int characteristics() {
/* 1281 */       return this.hasSplit ? 256 : 320;
/*      */     }
/*      */ 
/*      */     
/*      */     final void acceptOnIndex(IntConsumer action, int index) {
/* 1286 */       action.accept(Long2IntOpenCustomHashMap.this.value[index]);
/*      */     }
/*      */ 
/*      */     
/*      */     final ValueSpliterator makeForSplit(int pos, int max, boolean mustReturnNull) {
/* 1291 */       return new ValueSpliterator(pos, max, mustReturnNull, true);
/*      */     }
/*      */   }
/*      */ 
/*      */   
/*      */   public IntCollection values() {
/* 1297 */     if (this.values == null) this.values = (IntCollection)new AbstractIntCollection()
/*      */         {
/*      */           public IntIterator iterator() {
/* 1300 */             return new Long2IntOpenCustomHashMap.ValueIterator();
/*      */           }
/*      */ 
/*      */           
/*      */           public IntSpliterator spliterator() {
/* 1305 */             return new Long2IntOpenCustomHashMap.ValueSpliterator();
/*      */           }
/*      */ 
/*      */ 
/*      */           
/*      */           public void forEach(IntConsumer consumer) {
/* 1311 */             if (Long2IntOpenCustomHashMap.this.containsNullKey) consumer.accept(Long2IntOpenCustomHashMap.this.value[Long2IntOpenCustomHashMap.this.n]); 
/* 1312 */             for (int pos = Long2IntOpenCustomHashMap.this.n; pos-- != 0;) { if (Long2IntOpenCustomHashMap.this.key[pos] != 0L) consumer.accept(Long2IntOpenCustomHashMap.this.value[pos]);  }
/*      */           
/*      */           }
/*      */           
/*      */           public int size() {
/* 1317 */             return Long2IntOpenCustomHashMap.this.size;
/*      */           }
/*      */ 
/*      */           
/*      */           public boolean contains(int v) {
/* 1322 */             return Long2IntOpenCustomHashMap.this.containsValue(v);
/*      */           }
/*      */ 
/*      */           
/*      */           public void clear() {
/* 1327 */             Long2IntOpenCustomHashMap.this.clear();
/*      */           }
/*      */         }; 
/* 1330 */     return this.values;
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
/* 1347 */     return trim(this.size);
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
/* 1369 */     int l = HashCommon.nextPowerOfTwo((int)Math.ceil((n / this.f)));
/* 1370 */     if (l >= this.n || this.size > HashCommon.maxFill(l, this.f)) return true; 
/*      */     try {
/* 1372 */       rehash(l);
/* 1373 */     } catch (OutOfMemoryError cantDoIt) {
/* 1374 */       return false;
/*      */     } 
/* 1376 */     return true;
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
/* 1391 */     long[] key = this.key;
/* 1392 */     int[] value = this.value;
/* 1393 */     int mask = newN - 1;
/* 1394 */     long[] newKey = new long[newN + 1];
/* 1395 */     int[] newValue = new int[newN + 1];
/* 1396 */     int i = this.n;
/* 1397 */     for (int j = realSize(); j-- != 0; ) {
/* 1398 */       while (key[--i] == 0L); int pos;
/* 1399 */       if (newKey[pos = HashCommon.mix(this.strategy.hashCode(key[i])) & mask] != 0L) while (newKey[pos = pos + 1 & mask] != 0L); 
/* 1400 */       newKey[pos] = key[i];
/* 1401 */       newValue[pos] = value[i];
/*      */     } 
/* 1403 */     newValue[newN] = value[this.n];
/* 1404 */     this.n = newN;
/* 1405 */     this.mask = mask;
/* 1406 */     this.maxFill = HashCommon.maxFill(this.n, this.f);
/* 1407 */     this.key = newKey;
/* 1408 */     this.value = newValue;
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
/*      */   public Long2IntOpenCustomHashMap clone() {
/*      */     Long2IntOpenCustomHashMap c;
/*      */     try {
/* 1425 */       c = (Long2IntOpenCustomHashMap)super.clone();
/* 1426 */     } catch (CloneNotSupportedException cantHappen) {
/* 1427 */       throw new InternalError();
/*      */     } 
/* 1429 */     c.keys = null;
/* 1430 */     c.values = null;
/* 1431 */     c.entries = null;
/* 1432 */     c.containsNullKey = this.containsNullKey;
/* 1433 */     c.key = (long[])this.key.clone();
/* 1434 */     c.value = (int[])this.value.clone();
/* 1435 */     c.strategy = this.strategy;
/* 1436 */     return c;
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
/* 1450 */     int h = 0;
/* 1451 */     for (int j = realSize(), i = 0, t = 0; j-- != 0; ) {
/* 1452 */       for (; this.key[i] == 0L; i++);
/* 1453 */       t = this.strategy.hashCode(this.key[i]);
/* 1454 */       t ^= this.value[i];
/* 1455 */       h += t;
/* 1456 */       i++;
/*      */     } 
/*      */     
/* 1459 */     if (this.containsNullKey) h += this.value[this.n]; 
/* 1460 */     return h;
/*      */   }
/*      */   
/*      */   private void writeObject(ObjectOutputStream s) throws IOException {
/* 1464 */     long[] key = this.key;
/* 1465 */     int[] value = this.value;
/* 1466 */     EntryIterator i = new EntryIterator();
/* 1467 */     s.defaultWriteObject();
/* 1468 */     for (int j = this.size; j-- != 0; ) {
/* 1469 */       int e = i.nextEntry();
/* 1470 */       s.writeLong(key[e]);
/* 1471 */       s.writeInt(value[e]);
/*      */     } 
/*      */   }
/*      */   
/*      */   private void readObject(ObjectInputStream s) throws IOException, ClassNotFoundException {
/* 1476 */     s.defaultReadObject();
/* 1477 */     this.n = HashCommon.arraySize(this.size, this.f);
/* 1478 */     this.maxFill = HashCommon.maxFill(this.n, this.f);
/* 1479 */     this.mask = this.n - 1;
/* 1480 */     long[] key = this.key = new long[this.n + 1];
/* 1481 */     int[] value = this.value = new int[this.n + 1];
/*      */ 
/*      */     
/* 1484 */     for (int i = this.size; i-- != 0; ) {
/* 1485 */       int pos; long k = s.readLong();
/* 1486 */       int v = s.readInt();
/* 1487 */       if (this.strategy.equals(k, 0L)) {
/* 1488 */         pos = this.n;
/* 1489 */         this.containsNullKey = true;
/*      */       } else {
/* 1491 */         pos = HashCommon.mix(this.strategy.hashCode(k)) & this.mask;
/* 1492 */         for (; key[pos] != 0L; pos = pos + 1 & this.mask);
/*      */       } 
/* 1494 */       key[pos] = k;
/* 1495 */       value[pos] = v;
/*      */     } 
/*      */   }
/*      */   
/*      */   private void checkTable() {}
/*      */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\i\\unimi\dsi\fastutil\longs\Long2IntOpenCustomHashMap.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */