/*      */ package it.unimi.dsi.fastutil.longs;
/*      */ 
/*      */ import it.unimi.dsi.fastutil.Hash;
/*      */ import it.unimi.dsi.fastutil.HashCommon;
/*      */ import it.unimi.dsi.fastutil.doubles.AbstractDoubleCollection;
/*      */ import it.unimi.dsi.fastutil.doubles.DoubleCollection;
/*      */ import it.unimi.dsi.fastutil.doubles.DoubleIterator;
/*      */ import it.unimi.dsi.fastutil.doubles.DoubleSpliterator;
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
/*      */ import java.util.function.DoubleConsumer;
/*      */ import java.util.function.LongConsumer;
/*      */ import java.util.function.LongFunction;
/*      */ import java.util.function.LongToDoubleFunction;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ public class Long2DoubleOpenHashMap
/*      */   extends AbstractLong2DoubleMap
/*      */   implements Serializable, Cloneable, Hash
/*      */ {
/*      */   private static final long serialVersionUID = 0L;
/*      */   private static final boolean ASSERTS = false;
/*      */   protected transient long[] key;
/*      */   protected transient double[] value;
/*      */   protected transient int mask;
/*      */   protected transient boolean containsNullKey;
/*      */   protected transient int n;
/*      */   protected transient int maxFill;
/*      */   protected final transient int minN;
/*      */   protected int size;
/*      */   protected final float f;
/*      */   protected transient Long2DoubleMap.FastEntrySet entries;
/*      */   protected transient LongSet keys;
/*      */   protected transient DoubleCollection values;
/*      */   
/*      */   public Long2DoubleOpenHashMap(int expected, float f) {
/*   99 */     if (f <= 0.0F || f >= 1.0F) throw new IllegalArgumentException("Load factor must be greater than 0 and smaller than 1"); 
/*  100 */     if (expected < 0) throw new IllegalArgumentException("The expected number of elements must be nonnegative"); 
/*  101 */     this.f = f;
/*  102 */     this.minN = this.n = HashCommon.arraySize(expected, f);
/*  103 */     this.mask = this.n - 1;
/*  104 */     this.maxFill = HashCommon.maxFill(this.n, f);
/*  105 */     this.key = new long[this.n + 1];
/*  106 */     this.value = new double[this.n + 1];
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Long2DoubleOpenHashMap(int expected) {
/*  115 */     this(expected, 0.75F);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Long2DoubleOpenHashMap() {
/*  123 */     this(16, 0.75F);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Long2DoubleOpenHashMap(Map<? extends Long, ? extends Double> m, float f) {
/*  133 */     this(m.size(), f);
/*  134 */     putAll(m);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Long2DoubleOpenHashMap(Map<? extends Long, ? extends Double> m) {
/*  143 */     this(m, 0.75F);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Long2DoubleOpenHashMap(Long2DoubleMap m, float f) {
/*  153 */     this(m.size(), f);
/*  154 */     putAll(m);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Long2DoubleOpenHashMap(Long2DoubleMap m) {
/*  164 */     this(m, 0.75F);
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
/*      */   public Long2DoubleOpenHashMap(long[] k, double[] v, float f) {
/*  176 */     this(k.length, f);
/*  177 */     if (k.length != v.length) throw new IllegalArgumentException("The key array and the value array have different lengths (" + k.length + " and " + v.length + ")"); 
/*  178 */     for (int i = 0; i < k.length; ) { put(k[i], v[i]); i++; }
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
/*      */   public Long2DoubleOpenHashMap(long[] k, double[] v) {
/*  190 */     this(k, v, 0.75F);
/*      */   }
/*      */   
/*      */   private int realSize() {
/*  194 */     return this.containsNullKey ? (this.size - 1) : this.size;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void ensureCapacity(int capacity) {
/*  204 */     int needed = HashCommon.arraySize(capacity, this.f);
/*  205 */     if (needed > this.n) rehash(needed); 
/*      */   }
/*      */   
/*      */   private void tryCapacity(long capacity) {
/*  209 */     int needed = (int)Math.min(1073741824L, Math.max(2L, HashCommon.nextPowerOfTwo((long)Math.ceil(((float)capacity / this.f)))));
/*  210 */     if (needed > this.n) rehash(needed); 
/*      */   }
/*      */   
/*      */   private double removeEntry(int pos) {
/*  214 */     double oldValue = this.value[pos];
/*  215 */     this.size--;
/*  216 */     shiftKeys(pos);
/*  217 */     if (this.n > this.minN && this.size < this.maxFill / 4 && this.n > 16) rehash(this.n / 2); 
/*  218 */     return oldValue;
/*      */   }
/*      */   
/*      */   private double removeNullEntry() {
/*  222 */     this.containsNullKey = false;
/*  223 */     double oldValue = this.value[this.n];
/*  224 */     this.size--;
/*  225 */     if (this.n > this.minN && this.size < this.maxFill / 4 && this.n > 16) rehash(this.n / 2); 
/*  226 */     return oldValue;
/*      */   }
/*      */ 
/*      */   
/*      */   public void putAll(Map<? extends Long, ? extends Double> m) {
/*  231 */     if (this.f <= 0.5D) { ensureCapacity(m.size()); }
/*  232 */     else { tryCapacity((size() + m.size())); }
/*      */     
/*  234 */     super.putAll(m);
/*      */   }
/*      */   
/*      */   private int find(long k) {
/*  238 */     if (k == 0L) return this.containsNullKey ? this.n : -(this.n + 1);
/*      */     
/*  240 */     long[] key = this.key;
/*      */     long curr;
/*      */     int pos;
/*  243 */     if ((curr = key[pos = (int)HashCommon.mix(k) & this.mask]) == 0L) return -(pos + 1); 
/*  244 */     if (k == curr) return pos;
/*      */     
/*      */     while (true) {
/*  247 */       if ((curr = key[pos = pos + 1 & this.mask]) == 0L) return -(pos + 1); 
/*  248 */       if (k == curr) return pos; 
/*      */     } 
/*      */   }
/*      */   
/*      */   private void insert(int pos, long k, double v) {
/*  253 */     if (pos == this.n) this.containsNullKey = true; 
/*  254 */     this.key[pos] = k;
/*  255 */     this.value[pos] = v;
/*  256 */     if (this.size++ >= this.maxFill) rehash(HashCommon.arraySize(this.size + 1, this.f));
/*      */   
/*      */   }
/*      */ 
/*      */   
/*      */   public double put(long k, double v) {
/*  262 */     int pos = find(k);
/*  263 */     if (pos < 0) {
/*  264 */       insert(-pos - 1, k, v);
/*  265 */       return this.defRetValue;
/*      */     } 
/*  267 */     double oldValue = this.value[pos];
/*  268 */     this.value[pos] = v;
/*  269 */     return oldValue;
/*      */   }
/*      */   
/*      */   private double addToValue(int pos, double incr) {
/*  273 */     double oldValue = this.value[pos];
/*  274 */     this.value[pos] = oldValue + incr;
/*  275 */     return oldValue;
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
/*      */   public double addTo(long k, double incr) {
/*      */     int pos;
/*  293 */     if (k == 0L) {
/*  294 */       if (this.containsNullKey) return addToValue(this.n, incr); 
/*  295 */       pos = this.n;
/*  296 */       this.containsNullKey = true;
/*      */     } else {
/*      */       
/*  299 */       long[] key = this.key;
/*      */       long curr;
/*  301 */       if ((curr = key[pos = (int)HashCommon.mix(k) & this.mask]) != 0L) {
/*  302 */         if (curr == k) return addToValue(pos, incr); 
/*  303 */         while ((curr = key[pos = pos + 1 & this.mask]) != 0L) { if (curr == k) return addToValue(pos, incr);  }
/*      */       
/*      */       } 
/*  306 */     }  this.key[pos] = k;
/*  307 */     this.value[pos] = this.defRetValue + incr;
/*  308 */     if (this.size++ >= this.maxFill) rehash(HashCommon.arraySize(this.size + 1, this.f));
/*      */     
/*  310 */     return this.defRetValue;
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
/*  323 */     long[] key = this.key; while (true) {
/*      */       long curr; int last;
/*  325 */       pos = (last = pos) + 1 & this.mask;
/*      */       while (true) {
/*  327 */         if ((curr = key[pos]) == 0L) {
/*  328 */           key[last] = 0L;
/*      */           return;
/*      */         } 
/*  331 */         int slot = (int)HashCommon.mix(curr) & this.mask;
/*  332 */         if ((last <= pos) ? (last >= slot || slot > pos) : (last >= slot && slot > pos))
/*  333 */           break;  pos = pos + 1 & this.mask;
/*      */       } 
/*  335 */       key[last] = curr;
/*  336 */       this.value[last] = this.value[pos];
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public double remove(long k) {
/*  343 */     if (k == 0L) {
/*  344 */       if (this.containsNullKey) return removeNullEntry(); 
/*  345 */       return this.defRetValue;
/*      */     } 
/*      */     
/*  348 */     long[] key = this.key;
/*      */     long curr;
/*      */     int pos;
/*  351 */     if ((curr = key[pos = (int)HashCommon.mix(k) & this.mask]) == 0L) return this.defRetValue; 
/*  352 */     if (k == curr) return removeEntry(pos); 
/*      */     while (true) {
/*  354 */       if ((curr = key[pos = pos + 1 & this.mask]) == 0L) return this.defRetValue; 
/*  355 */       if (k == curr) return removeEntry(pos);
/*      */     
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   public double get(long k) {
/*  362 */     if (k == 0L) return this.containsNullKey ? this.value[this.n] : this.defRetValue;
/*      */     
/*  364 */     long[] key = this.key;
/*      */     long curr;
/*      */     int pos;
/*  367 */     if ((curr = key[pos = (int)HashCommon.mix(k) & this.mask]) == 0L) return this.defRetValue; 
/*  368 */     if (k == curr) return this.value[pos];
/*      */     
/*      */     while (true) {
/*  371 */       if ((curr = key[pos = pos + 1 & this.mask]) == 0L) return this.defRetValue; 
/*  372 */       if (k == curr) return this.value[pos];
/*      */     
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   public boolean containsKey(long k) {
/*  379 */     if (k == 0L) return this.containsNullKey;
/*      */     
/*  381 */     long[] key = this.key;
/*      */     long curr;
/*      */     int pos;
/*  384 */     if ((curr = key[pos = (int)HashCommon.mix(k) & this.mask]) == 0L) return false; 
/*  385 */     if (k == curr) return true;
/*      */     
/*      */     while (true) {
/*  388 */       if ((curr = key[pos = pos + 1 & this.mask]) == 0L) return false; 
/*  389 */       if (k == curr) return true;
/*      */     
/*      */     } 
/*      */   }
/*      */   
/*      */   public boolean containsValue(double v) {
/*  395 */     double[] value = this.value;
/*  396 */     long[] key = this.key;
/*  397 */     if (this.containsNullKey && Double.doubleToLongBits(value[this.n]) == Double.doubleToLongBits(v)) return true; 
/*  398 */     for (int i = this.n; i-- != 0;) { if (key[i] != 0L && Double.doubleToLongBits(value[i]) == Double.doubleToLongBits(v)) return true;  }
/*  399 */      return false;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public double getOrDefault(long k, double defaultValue) {
/*  406 */     if (k == 0L) return this.containsNullKey ? this.value[this.n] : defaultValue;
/*      */     
/*  408 */     long[] key = this.key;
/*      */     long curr;
/*      */     int pos;
/*  411 */     if ((curr = key[pos = (int)HashCommon.mix(k) & this.mask]) == 0L) return defaultValue; 
/*  412 */     if (k == curr) return this.value[pos];
/*      */     
/*      */     while (true) {
/*  415 */       if ((curr = key[pos = pos + 1 & this.mask]) == 0L) return defaultValue; 
/*  416 */       if (k == curr) return this.value[pos];
/*      */     
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   public double putIfAbsent(long k, double v) {
/*  423 */     int pos = find(k);
/*  424 */     if (pos >= 0) return this.value[pos]; 
/*  425 */     insert(-pos - 1, k, v);
/*  426 */     return this.defRetValue;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean remove(long k, double v) {
/*  433 */     if (k == 0L) {
/*  434 */       if (this.containsNullKey && Double.doubleToLongBits(v) == Double.doubleToLongBits(this.value[this.n])) {
/*  435 */         removeNullEntry();
/*  436 */         return true;
/*      */       } 
/*  438 */       return false;
/*      */     } 
/*      */     
/*  441 */     long[] key = this.key;
/*      */     long curr;
/*      */     int pos;
/*  444 */     if ((curr = key[pos = (int)HashCommon.mix(k) & this.mask]) == 0L) return false; 
/*  445 */     if (k == curr && Double.doubleToLongBits(v) == Double.doubleToLongBits(this.value[pos])) {
/*  446 */       removeEntry(pos);
/*  447 */       return true;
/*      */     } 
/*      */     while (true) {
/*  450 */       if ((curr = key[pos = pos + 1 & this.mask]) == 0L) return false; 
/*  451 */       if (k == curr && Double.doubleToLongBits(v) == Double.doubleToLongBits(this.value[pos])) {
/*  452 */         removeEntry(pos);
/*  453 */         return true;
/*      */       } 
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean replace(long k, double oldValue, double v) {
/*  461 */     int pos = find(k);
/*  462 */     if (pos < 0 || Double.doubleToLongBits(oldValue) != Double.doubleToLongBits(this.value[pos])) return false; 
/*  463 */     this.value[pos] = v;
/*  464 */     return true;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public double replace(long k, double v) {
/*  470 */     int pos = find(k);
/*  471 */     if (pos < 0) return this.defRetValue; 
/*  472 */     double oldValue = this.value[pos];
/*  473 */     this.value[pos] = v;
/*  474 */     return oldValue;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public double computeIfAbsent(long k, LongToDoubleFunction mappingFunction) {
/*  480 */     Objects.requireNonNull(mappingFunction);
/*  481 */     int pos = find(k);
/*  482 */     if (pos >= 0) return this.value[pos]; 
/*  483 */     double newValue = mappingFunction.applyAsDouble(k);
/*  484 */     insert(-pos - 1, k, newValue);
/*  485 */     return newValue;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public double computeIfAbsent(long key, Long2DoubleFunction mappingFunction) {
/*  491 */     Objects.requireNonNull(mappingFunction);
/*  492 */     int pos = find(key);
/*  493 */     if (pos >= 0) return this.value[pos]; 
/*  494 */     if (!mappingFunction.containsKey(key)) return this.defRetValue; 
/*  495 */     double newValue = mappingFunction.get(key);
/*  496 */     insert(-pos - 1, key, newValue);
/*  497 */     return newValue;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public double computeIfAbsentNullable(long k, LongFunction<? extends Double> mappingFunction) {
/*  503 */     Objects.requireNonNull(mappingFunction);
/*  504 */     int pos = find(k);
/*  505 */     if (pos >= 0) return this.value[pos]; 
/*  506 */     Double newValue = mappingFunction.apply(k);
/*  507 */     if (newValue == null) return this.defRetValue; 
/*  508 */     double v = newValue.doubleValue();
/*  509 */     insert(-pos - 1, k, v);
/*  510 */     return v;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public double computeIfPresent(long k, BiFunction<? super Long, ? super Double, ? extends Double> remappingFunction) {
/*  516 */     Objects.requireNonNull(remappingFunction);
/*  517 */     int pos = find(k);
/*  518 */     if (pos < 0) return this.defRetValue; 
/*  519 */     Double newValue = remappingFunction.apply(Long.valueOf(k), Double.valueOf(this.value[pos]));
/*  520 */     if (newValue == null) {
/*  521 */       if (k == 0L) { removeNullEntry(); }
/*  522 */       else { removeEntry(pos); }
/*  523 */        return this.defRetValue;
/*      */     } 
/*  525 */     this.value[pos] = newValue.doubleValue(); return newValue.doubleValue();
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public double compute(long k, BiFunction<? super Long, ? super Double, ? extends Double> remappingFunction) {
/*  531 */     Objects.requireNonNull(remappingFunction);
/*  532 */     int pos = find(k);
/*  533 */     Double newValue = remappingFunction.apply(Long.valueOf(k), (pos >= 0) ? Double.valueOf(this.value[pos]) : null);
/*  534 */     if (newValue == null) {
/*  535 */       if (pos >= 0)
/*  536 */         if (k == 0L) { removeNullEntry(); }
/*  537 */         else { removeEntry(pos); }
/*      */          
/*  539 */       return this.defRetValue;
/*      */     } 
/*  541 */     double newVal = newValue.doubleValue();
/*  542 */     if (pos < 0) {
/*  543 */       insert(-pos - 1, k, newVal);
/*  544 */       return newVal;
/*      */     } 
/*  546 */     this.value[pos] = newVal; return newVal;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public double merge(long k, double v, BiFunction<? super Double, ? super Double, ? extends Double> remappingFunction) {
/*  552 */     Objects.requireNonNull(remappingFunction);
/*      */     
/*  554 */     int pos = find(k);
/*  555 */     if (pos < 0) {
/*  556 */       if (pos < 0) { insert(-pos - 1, k, v); }
/*  557 */       else { this.value[pos] = v; }
/*  558 */        return v;
/*      */     } 
/*  560 */     Double newValue = remappingFunction.apply(Double.valueOf(this.value[pos]), Double.valueOf(v));
/*  561 */     if (newValue == null) {
/*  562 */       if (k == 0L) { removeNullEntry(); }
/*  563 */       else { removeEntry(pos); }
/*  564 */        return this.defRetValue;
/*      */     } 
/*  566 */     this.value[pos] = newValue.doubleValue(); return newValue.doubleValue();
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
/*  577 */     if (this.size == 0)
/*  578 */       return;  this.size = 0;
/*  579 */     this.containsNullKey = false;
/*  580 */     Arrays.fill(this.key, 0L);
/*      */   }
/*      */ 
/*      */   
/*      */   public int size() {
/*  585 */     return this.size;
/*      */   }
/*      */ 
/*      */   
/*      */   public boolean isEmpty() {
/*  590 */     return (this.size == 0);
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   final class MapEntry
/*      */     implements Long2DoubleMap.Entry, Map.Entry<Long, Double>, LongDoublePair
/*      */   {
/*      */     int index;
/*      */ 
/*      */ 
/*      */     
/*      */     MapEntry(int index) {
/*  603 */       this.index = index;
/*      */     }
/*      */ 
/*      */     
/*      */     MapEntry() {}
/*      */ 
/*      */     
/*      */     public long getLongKey() {
/*  611 */       return Long2DoubleOpenHashMap.this.key[this.index];
/*      */     }
/*      */ 
/*      */     
/*      */     public long leftLong() {
/*  616 */       return Long2DoubleOpenHashMap.this.key[this.index];
/*      */     }
/*      */ 
/*      */     
/*      */     public double getDoubleValue() {
/*  621 */       return Long2DoubleOpenHashMap.this.value[this.index];
/*      */     }
/*      */ 
/*      */     
/*      */     public double rightDouble() {
/*  626 */       return Long2DoubleOpenHashMap.this.value[this.index];
/*      */     }
/*      */ 
/*      */     
/*      */     public double setValue(double v) {
/*  631 */       double oldValue = Long2DoubleOpenHashMap.this.value[this.index];
/*  632 */       Long2DoubleOpenHashMap.this.value[this.index] = v;
/*  633 */       return oldValue;
/*      */     }
/*      */ 
/*      */     
/*      */     public LongDoublePair right(double v) {
/*  638 */       Long2DoubleOpenHashMap.this.value[this.index] = v;
/*  639 */       return this;
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
/*  650 */       return Long.valueOf(Long2DoubleOpenHashMap.this.key[this.index]);
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
/*  661 */       return Double.valueOf(Long2DoubleOpenHashMap.this.value[this.index]);
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
/*  672 */       return Double.valueOf(setValue(v.doubleValue()));
/*      */     }
/*      */ 
/*      */ 
/*      */     
/*      */     public boolean equals(Object o) {
/*  678 */       if (!(o instanceof Map.Entry)) return false; 
/*  679 */       Map.Entry<Long, Double> e = (Map.Entry<Long, Double>)o;
/*  680 */       return (Long2DoubleOpenHashMap.this.key[this.index] == ((Long)e.getKey()).longValue() && Double.doubleToLongBits(Long2DoubleOpenHashMap.this.value[this.index]) == Double.doubleToLongBits(((Double)e.getValue()).doubleValue()));
/*      */     }
/*      */ 
/*      */     
/*      */     public int hashCode() {
/*  685 */       return HashCommon.long2int(Long2DoubleOpenHashMap.this.key[this.index]) ^ HashCommon.double2int(Long2DoubleOpenHashMap.this.value[this.index]);
/*      */     }
/*      */ 
/*      */     
/*      */     public String toString() {
/*  690 */       return Long2DoubleOpenHashMap.this.key[this.index] + "=>" + Long2DoubleOpenHashMap.this.value[this.index];
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
/*  701 */     int pos = Long2DoubleOpenHashMap.this.n;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  707 */     int last = -1;
/*      */     
/*  709 */     int c = Long2DoubleOpenHashMap.this.size;
/*      */     
/*  711 */     boolean mustReturnNullKey = Long2DoubleOpenHashMap.this.containsNullKey;
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
/*  722 */       return (this.c != 0);
/*      */     }
/*      */     
/*      */     public int nextEntry() {
/*  726 */       if (!hasNext()) throw new NoSuchElementException(); 
/*  727 */       this.c--;
/*  728 */       if (this.mustReturnNullKey) {
/*  729 */         this.mustReturnNullKey = false;
/*  730 */         return this.last = Long2DoubleOpenHashMap.this.n;
/*      */       } 
/*  732 */       long[] key = Long2DoubleOpenHashMap.this.key;
/*      */       while (true) {
/*  734 */         if (--this.pos < 0) {
/*      */           
/*  736 */           this.last = Integer.MIN_VALUE;
/*  737 */           long k = this.wrapped.getLong(-this.pos - 1);
/*  738 */           int p = (int)HashCommon.mix(k) & Long2DoubleOpenHashMap.this.mask;
/*  739 */           for (; k != key[p]; p = p + 1 & Long2DoubleOpenHashMap.this.mask);
/*  740 */           return p;
/*      */         } 
/*  742 */         if (key[this.pos] != 0L) return this.last = this.pos; 
/*      */       } 
/*      */     }
/*      */     
/*      */     public void forEachRemaining(ConsumerType action) {
/*  747 */       if (this.mustReturnNullKey) {
/*  748 */         this.mustReturnNullKey = false;
/*  749 */         acceptOnIndex(action, this.last = Long2DoubleOpenHashMap.this.n);
/*  750 */         this.c--;
/*      */       } 
/*  752 */       long[] key = Long2DoubleOpenHashMap.this.key;
/*  753 */       while (this.c != 0) {
/*  754 */         if (--this.pos < 0) {
/*      */           
/*  756 */           this.last = Integer.MIN_VALUE;
/*  757 */           long k = this.wrapped.getLong(-this.pos - 1);
/*  758 */           int p = (int)HashCommon.mix(k) & Long2DoubleOpenHashMap.this.mask;
/*  759 */           for (; k != key[p]; p = p + 1 & Long2DoubleOpenHashMap.this.mask);
/*  760 */           acceptOnIndex(action, p);
/*  761 */           this.c--; continue;
/*  762 */         }  if (key[this.pos] != 0L) {
/*  763 */           acceptOnIndex(action, this.last = this.pos);
/*  764 */           this.c--;
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
/*  779 */       long[] key = Long2DoubleOpenHashMap.this.key; while (true) {
/*      */         long curr; int last;
/*  781 */         pos = (last = pos) + 1 & Long2DoubleOpenHashMap.this.mask;
/*      */         while (true) {
/*  783 */           if ((curr = key[pos]) == 0L) {
/*  784 */             key[last] = 0L;
/*      */             return;
/*      */           } 
/*  787 */           int slot = (int)HashCommon.mix(curr) & Long2DoubleOpenHashMap.this.mask;
/*  788 */           if ((last <= pos) ? (last >= slot || slot > pos) : (last >= slot && slot > pos))
/*  789 */             break;  pos = pos + 1 & Long2DoubleOpenHashMap.this.mask;
/*      */         } 
/*  791 */         if (pos < last) {
/*  792 */           if (this.wrapped == null) this.wrapped = new LongArrayList(2); 
/*  793 */           this.wrapped.add(key[pos]);
/*      */         } 
/*  795 */         key[last] = curr;
/*  796 */         Long2DoubleOpenHashMap.this.value[last] = Long2DoubleOpenHashMap.this.value[pos];
/*      */       } 
/*      */     }
/*      */     
/*      */     public void remove() {
/*  801 */       if (this.last == -1) throw new IllegalStateException(); 
/*  802 */       if (this.last == Long2DoubleOpenHashMap.this.n)
/*  803 */       { Long2DoubleOpenHashMap.this.containsNullKey = false; }
/*  804 */       else if (this.pos >= 0) { shiftKeys(this.last); }
/*      */       else
/*      */       
/*  807 */       { Long2DoubleOpenHashMap.this.remove(this.wrapped.getLong(-this.pos - 1));
/*  808 */         this.last = -1;
/*      */         return; }
/*      */       
/*  811 */       Long2DoubleOpenHashMap.this.size--;
/*  812 */       this.last = -1;
/*      */     }
/*      */ 
/*      */     
/*      */     public int skip(int n) {
/*  817 */       int i = n;
/*  818 */       for (; i-- != 0 && hasNext(); nextEntry());
/*  819 */       return n - i - 1;
/*      */     }
/*      */     
/*      */     private MapIterator() {}
/*      */     
/*      */     abstract void acceptOnIndex(ConsumerType param1ConsumerType, int param1Int);
/*      */   }
/*      */   
/*      */   private final class EntryIterator extends MapIterator<Consumer<? super Long2DoubleMap.Entry>> implements ObjectIterator<Long2DoubleMap.Entry> { public Long2DoubleOpenHashMap.MapEntry next() {
/*  828 */       return this.entry = new Long2DoubleOpenHashMap.MapEntry(nextEntry());
/*      */     }
/*      */     private Long2DoubleOpenHashMap.MapEntry entry;
/*      */     private EntryIterator() {}
/*      */     
/*      */     final void acceptOnIndex(Consumer<? super Long2DoubleMap.Entry> action, int index) {
/*  834 */       action.accept(this.entry = new Long2DoubleOpenHashMap.MapEntry(index));
/*      */     }
/*      */ 
/*      */     
/*      */     public void remove() {
/*  839 */       super.remove();
/*  840 */       this.entry.index = -1;
/*      */     } }
/*      */   
/*      */   private final class FastEntryIterator extends MapIterator<Consumer<? super Long2DoubleMap.Entry>> implements ObjectIterator<Long2DoubleMap.Entry> {
/*      */     private FastEntryIterator() {
/*  845 */       this.entry = new Long2DoubleOpenHashMap.MapEntry();
/*      */     }
/*      */     private final Long2DoubleOpenHashMap.MapEntry entry;
/*      */     public Long2DoubleOpenHashMap.MapEntry next() {
/*  849 */       this.entry.index = nextEntry();
/*  850 */       return this.entry;
/*      */     }
/*      */ 
/*      */ 
/*      */     
/*      */     final void acceptOnIndex(Consumer<? super Long2DoubleMap.Entry> action, int index) {
/*  856 */       this.entry.index = index;
/*  857 */       action.accept(this.entry);
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private abstract class MapSpliterator<ConsumerType, SplitType extends MapSpliterator<ConsumerType, SplitType>>
/*      */   {
/*  866 */     int pos = 0;
/*      */     
/*  868 */     int max = Long2DoubleOpenHashMap.this.n;
/*      */     
/*  870 */     int c = 0;
/*      */     
/*  872 */     boolean mustReturnNull = Long2DoubleOpenHashMap.this.containsNullKey;
/*      */     
/*      */     boolean hasSplit = false;
/*      */     
/*      */     MapSpliterator() {}
/*      */     
/*      */     MapSpliterator(int pos, int max, boolean mustReturnNull, boolean hasSplit) {
/*  879 */       this.pos = pos;
/*  880 */       this.max = max;
/*  881 */       this.mustReturnNull = mustReturnNull;
/*  882 */       this.hasSplit = hasSplit;
/*      */     }
/*      */     
/*      */     abstract void acceptOnIndex(ConsumerType param1ConsumerType, int param1Int);
/*      */     
/*      */     abstract SplitType makeForSplit(int param1Int1, int param1Int2, boolean param1Boolean);
/*      */     
/*      */     public boolean tryAdvance(ConsumerType action) {
/*  890 */       if (this.mustReturnNull) {
/*  891 */         this.mustReturnNull = false;
/*  892 */         this.c++;
/*  893 */         acceptOnIndex(action, Long2DoubleOpenHashMap.this.n);
/*  894 */         return true;
/*      */       } 
/*  896 */       long[] key = Long2DoubleOpenHashMap.this.key;
/*  897 */       while (this.pos < this.max) {
/*  898 */         if (key[this.pos] != 0L) {
/*  899 */           this.c++;
/*  900 */           acceptOnIndex(action, this.pos++);
/*  901 */           return true;
/*      */         } 
/*  903 */         this.pos++;
/*      */       } 
/*  905 */       return false;
/*      */     }
/*      */     
/*      */     public void forEachRemaining(ConsumerType action) {
/*  909 */       if (this.mustReturnNull) {
/*  910 */         this.mustReturnNull = false;
/*  911 */         this.c++;
/*  912 */         acceptOnIndex(action, Long2DoubleOpenHashMap.this.n);
/*      */       } 
/*  914 */       long[] key = Long2DoubleOpenHashMap.this.key;
/*  915 */       while (this.pos < this.max) {
/*  916 */         if (key[this.pos] != 0L) {
/*  917 */           acceptOnIndex(action, this.pos);
/*  918 */           this.c++;
/*      */         } 
/*  920 */         this.pos++;
/*      */       } 
/*      */     }
/*      */     
/*      */     public long estimateSize() {
/*  925 */       if (!this.hasSplit)
/*      */       {
/*  927 */         return (Long2DoubleOpenHashMap.this.size - this.c);
/*      */       }
/*      */ 
/*      */ 
/*      */       
/*  932 */       return Math.min((Long2DoubleOpenHashMap.this.size - this.c), (long)(Long2DoubleOpenHashMap.this.realSize() / Long2DoubleOpenHashMap.this.n * (this.max - this.pos)) + (this.mustReturnNull ? 1L : 0L));
/*      */     }
/*      */ 
/*      */     
/*      */     public SplitType trySplit() {
/*  937 */       if (this.pos >= this.max - 1) return null; 
/*  938 */       int retLen = this.max - this.pos >> 1;
/*  939 */       if (retLen <= 1) return null; 
/*  940 */       int myNewPos = this.pos + retLen;
/*  941 */       int retPos = this.pos;
/*  942 */       int retMax = myNewPos;
/*      */ 
/*      */ 
/*      */       
/*  946 */       SplitType split = makeForSplit(retPos, retMax, this.mustReturnNull);
/*  947 */       this.pos = myNewPos;
/*  948 */       this.mustReturnNull = false;
/*  949 */       this.hasSplit = true;
/*  950 */       return split;
/*      */     }
/*      */     
/*      */     public long skip(long n) {
/*  954 */       if (n < 0L) throw new IllegalArgumentException("Argument must be nonnegative: " + n); 
/*  955 */       if (n == 0L) return 0L; 
/*  956 */       long skipped = 0L;
/*  957 */       if (this.mustReturnNull) {
/*  958 */         this.mustReturnNull = false;
/*  959 */         skipped++;
/*  960 */         n--;
/*      */       } 
/*  962 */       long[] key = Long2DoubleOpenHashMap.this.key;
/*  963 */       while (this.pos < this.max && n > 0L) {
/*  964 */         if (key[this.pos++] != 0L) {
/*  965 */           skipped++;
/*  966 */           n--;
/*      */         } 
/*      */       } 
/*  969 */       return skipped;
/*      */     }
/*      */   }
/*      */   
/*      */   private final class EntrySpliterator
/*      */     extends MapSpliterator<Consumer<? super Long2DoubleMap.Entry>, EntrySpliterator> implements ObjectSpliterator<Long2DoubleMap.Entry> {
/*      */     private static final int POST_SPLIT_CHARACTERISTICS = 1;
/*      */     
/*      */     EntrySpliterator() {}
/*      */     
/*      */     EntrySpliterator(int pos, int max, boolean mustReturnNull, boolean hasSplit) {
/*  980 */       super(pos, max, mustReturnNull, hasSplit);
/*      */     }
/*      */ 
/*      */     
/*      */     public int characteristics() {
/*  985 */       return this.hasSplit ? 1 : 65;
/*      */     }
/*      */ 
/*      */     
/*      */     final void acceptOnIndex(Consumer<? super Long2DoubleMap.Entry> action, int index) {
/*  990 */       action.accept(new Long2DoubleOpenHashMap.MapEntry(index));
/*      */     }
/*      */ 
/*      */     
/*      */     final EntrySpliterator makeForSplit(int pos, int max, boolean mustReturnNull) {
/*  995 */       return new EntrySpliterator(pos, max, mustReturnNull, true);
/*      */     } }
/*      */   
/*      */   private final class MapEntrySet extends AbstractObjectSet<Long2DoubleMap.Entry> implements Long2DoubleMap.FastEntrySet {
/*      */     private MapEntrySet() {}
/*      */     
/*      */     public ObjectIterator<Long2DoubleMap.Entry> iterator() {
/* 1002 */       return new Long2DoubleOpenHashMap.EntryIterator();
/*      */     }
/*      */ 
/*      */     
/*      */     public ObjectIterator<Long2DoubleMap.Entry> fastIterator() {
/* 1007 */       return new Long2DoubleOpenHashMap.FastEntryIterator();
/*      */     }
/*      */ 
/*      */     
/*      */     public ObjectSpliterator<Long2DoubleMap.Entry> spliterator() {
/* 1012 */       return new Long2DoubleOpenHashMap.EntrySpliterator();
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public boolean contains(Object o) {
/* 1019 */       if (!(o instanceof Map.Entry)) return false; 
/* 1020 */       Map.Entry<?, ?> e = (Map.Entry<?, ?>)o;
/* 1021 */       if (e.getKey() == null || !(e.getKey() instanceof Long)) return false; 
/* 1022 */       if (e.getValue() == null || !(e.getValue() instanceof Double)) return false; 
/* 1023 */       long k = ((Long)e.getKey()).longValue();
/* 1024 */       double v = ((Double)e.getValue()).doubleValue();
/* 1025 */       if (k == 0L) return (Long2DoubleOpenHashMap.this.containsNullKey && Double.doubleToLongBits(Long2DoubleOpenHashMap.this.value[Long2DoubleOpenHashMap.this.n]) == Double.doubleToLongBits(v));
/*      */       
/* 1027 */       long[] key = Long2DoubleOpenHashMap.this.key;
/*      */       long curr;
/*      */       int pos;
/* 1030 */       if ((curr = key[pos = (int)HashCommon.mix(k) & Long2DoubleOpenHashMap.this.mask]) == 0L) return false; 
/* 1031 */       if (k == curr) return (Double.doubleToLongBits(Long2DoubleOpenHashMap.this.value[pos]) == Double.doubleToLongBits(v));
/*      */       
/*      */       while (true) {
/* 1034 */         if ((curr = key[pos = pos + 1 & Long2DoubleOpenHashMap.this.mask]) == 0L) return false; 
/* 1035 */         if (k == curr) return (Double.doubleToLongBits(Long2DoubleOpenHashMap.this.value[pos]) == Double.doubleToLongBits(v));
/*      */       
/*      */       } 
/*      */     }
/*      */ 
/*      */     
/*      */     public boolean remove(Object o) {
/* 1042 */       if (!(o instanceof Map.Entry)) return false; 
/* 1043 */       Map.Entry<?, ?> e = (Map.Entry<?, ?>)o;
/* 1044 */       if (e.getKey() == null || !(e.getKey() instanceof Long)) return false; 
/* 1045 */       if (e.getValue() == null || !(e.getValue() instanceof Double)) return false; 
/* 1046 */       long k = ((Long)e.getKey()).longValue();
/* 1047 */       double v = ((Double)e.getValue()).doubleValue();
/* 1048 */       if (k == 0L) {
/* 1049 */         if (Long2DoubleOpenHashMap.this.containsNullKey && Double.doubleToLongBits(Long2DoubleOpenHashMap.this.value[Long2DoubleOpenHashMap.this.n]) == Double.doubleToLongBits(v)) {
/* 1050 */           Long2DoubleOpenHashMap.this.removeNullEntry();
/* 1051 */           return true;
/*      */         } 
/* 1053 */         return false;
/*      */       } 
/*      */       
/* 1056 */       long[] key = Long2DoubleOpenHashMap.this.key;
/*      */       long curr;
/*      */       int pos;
/* 1059 */       if ((curr = key[pos = (int)HashCommon.mix(k) & Long2DoubleOpenHashMap.this.mask]) == 0L) return false; 
/* 1060 */       if (curr == k) {
/* 1061 */         if (Double.doubleToLongBits(Long2DoubleOpenHashMap.this.value[pos]) == Double.doubleToLongBits(v)) {
/* 1062 */           Long2DoubleOpenHashMap.this.removeEntry(pos);
/* 1063 */           return true;
/*      */         } 
/* 1065 */         return false;
/*      */       } 
/*      */       while (true) {
/* 1068 */         if ((curr = key[pos = pos + 1 & Long2DoubleOpenHashMap.this.mask]) == 0L) return false; 
/* 1069 */         if (curr == k && 
/* 1070 */           Double.doubleToLongBits(Long2DoubleOpenHashMap.this.value[pos]) == Double.doubleToLongBits(v)) {
/* 1071 */           Long2DoubleOpenHashMap.this.removeEntry(pos);
/* 1072 */           return true;
/*      */         } 
/*      */       } 
/*      */     }
/*      */ 
/*      */ 
/*      */     
/*      */     public int size() {
/* 1080 */       return Long2DoubleOpenHashMap.this.size;
/*      */     }
/*      */ 
/*      */     
/*      */     public void clear() {
/* 1085 */       Long2DoubleOpenHashMap.this.clear();
/*      */     }
/*      */ 
/*      */ 
/*      */     
/*      */     public void forEach(Consumer<? super Long2DoubleMap.Entry> consumer) {
/* 1091 */       if (Long2DoubleOpenHashMap.this.containsNullKey) consumer.accept(new Long2DoubleOpenHashMap.MapEntry(Long2DoubleOpenHashMap.this.n)); 
/* 1092 */       for (int pos = Long2DoubleOpenHashMap.this.n; pos-- != 0;) { if (Long2DoubleOpenHashMap.this.key[pos] != 0L) consumer.accept(new Long2DoubleOpenHashMap.MapEntry(pos));
/*      */          }
/*      */     
/*      */     }
/*      */     
/*      */     public void fastForEach(Consumer<? super Long2DoubleMap.Entry> consumer) {
/* 1098 */       Long2DoubleOpenHashMap.MapEntry entry = new Long2DoubleOpenHashMap.MapEntry();
/* 1099 */       if (Long2DoubleOpenHashMap.this.containsNullKey) {
/* 1100 */         entry.index = Long2DoubleOpenHashMap.this.n;
/* 1101 */         consumer.accept(entry);
/*      */       } 
/* 1103 */       for (int pos = Long2DoubleOpenHashMap.this.n; pos-- != 0;) { if (Long2DoubleOpenHashMap.this.key[pos] != 0L) {
/* 1104 */           entry.index = pos;
/* 1105 */           consumer.accept(entry);
/*      */         }  }
/*      */     
/*      */     }
/*      */   }
/*      */   
/*      */   public Long2DoubleMap.FastEntrySet long2DoubleEntrySet() {
/* 1112 */     if (this.entries == null) this.entries = new MapEntrySet(); 
/* 1113 */     return this.entries;
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
/* 1134 */       action.accept(Long2DoubleOpenHashMap.this.key[index]);
/*      */     }
/*      */ 
/*      */     
/*      */     public long nextLong() {
/* 1139 */       return Long2DoubleOpenHashMap.this.key[nextEntry()];
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
/* 1150 */       super(pos, max, mustReturnNull, hasSplit);
/*      */     }
/*      */ 
/*      */     
/*      */     public int characteristics() {
/* 1155 */       return this.hasSplit ? 257 : 321;
/*      */     }
/*      */ 
/*      */     
/*      */     final void acceptOnIndex(LongConsumer action, int index) {
/* 1160 */       action.accept(Long2DoubleOpenHashMap.this.key[index]);
/*      */     }
/*      */ 
/*      */     
/*      */     final KeySpliterator makeForSplit(int pos, int max, boolean mustReturnNull) {
/* 1165 */       return new KeySpliterator(pos, max, mustReturnNull, true);
/*      */     } }
/*      */   
/*      */   private final class KeySet extends AbstractLongSet {
/*      */     private KeySet() {}
/*      */     
/*      */     public LongIterator iterator() {
/* 1172 */       return new Long2DoubleOpenHashMap.KeyIterator();
/*      */     }
/*      */ 
/*      */     
/*      */     public LongSpliterator spliterator() {
/* 1177 */       return new Long2DoubleOpenHashMap.KeySpliterator();
/*      */     }
/*      */ 
/*      */ 
/*      */     
/*      */     public void forEach(LongConsumer consumer) {
/* 1183 */       if (Long2DoubleOpenHashMap.this.containsNullKey) consumer.accept(Long2DoubleOpenHashMap.this.key[Long2DoubleOpenHashMap.this.n]); 
/* 1184 */       for (int pos = Long2DoubleOpenHashMap.this.n; pos-- != 0; ) {
/* 1185 */         long k = Long2DoubleOpenHashMap.this.key[pos];
/* 1186 */         if (k != 0L) consumer.accept(k);
/*      */       
/*      */       } 
/*      */     }
/*      */     
/*      */     public int size() {
/* 1192 */       return Long2DoubleOpenHashMap.this.size;
/*      */     }
/*      */ 
/*      */     
/*      */     public boolean contains(long k) {
/* 1197 */       return Long2DoubleOpenHashMap.this.containsKey(k);
/*      */     }
/*      */ 
/*      */     
/*      */     public boolean remove(long k) {
/* 1202 */       int oldSize = Long2DoubleOpenHashMap.this.size;
/* 1203 */       Long2DoubleOpenHashMap.this.remove(k);
/* 1204 */       return (Long2DoubleOpenHashMap.this.size != oldSize);
/*      */     }
/*      */ 
/*      */     
/*      */     public void clear() {
/* 1209 */       Long2DoubleOpenHashMap.this.clear();
/*      */     }
/*      */   }
/*      */ 
/*      */   
/*      */   public LongSet keySet() {
/* 1215 */     if (this.keys == null) this.keys = new KeySet(); 
/* 1216 */     return this.keys;
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
/* 1237 */       action.accept(Long2DoubleOpenHashMap.this.value[index]);
/*      */     }
/*      */ 
/*      */     
/*      */     public double nextDouble() {
/* 1242 */       return Long2DoubleOpenHashMap.this.value[nextEntry()];
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
/* 1253 */       super(pos, max, mustReturnNull, hasSplit);
/*      */     }
/*      */ 
/*      */     
/*      */     public int characteristics() {
/* 1258 */       return this.hasSplit ? 256 : 320;
/*      */     }
/*      */ 
/*      */     
/*      */     final void acceptOnIndex(DoubleConsumer action, int index) {
/* 1263 */       action.accept(Long2DoubleOpenHashMap.this.value[index]);
/*      */     }
/*      */ 
/*      */     
/*      */     final ValueSpliterator makeForSplit(int pos, int max, boolean mustReturnNull) {
/* 1268 */       return new ValueSpliterator(pos, max, mustReturnNull, true);
/*      */     }
/*      */   }
/*      */ 
/*      */   
/*      */   public DoubleCollection values() {
/* 1274 */     if (this.values == null) this.values = (DoubleCollection)new AbstractDoubleCollection()
/*      */         {
/*      */           public DoubleIterator iterator() {
/* 1277 */             return new Long2DoubleOpenHashMap.ValueIterator();
/*      */           }
/*      */ 
/*      */           
/*      */           public DoubleSpliterator spliterator() {
/* 1282 */             return new Long2DoubleOpenHashMap.ValueSpliterator();
/*      */           }
/*      */ 
/*      */ 
/*      */           
/*      */           public void forEach(DoubleConsumer consumer) {
/* 1288 */             if (Long2DoubleOpenHashMap.this.containsNullKey) consumer.accept(Long2DoubleOpenHashMap.this.value[Long2DoubleOpenHashMap.this.n]); 
/* 1289 */             for (int pos = Long2DoubleOpenHashMap.this.n; pos-- != 0;) { if (Long2DoubleOpenHashMap.this.key[pos] != 0L) consumer.accept(Long2DoubleOpenHashMap.this.value[pos]);  }
/*      */           
/*      */           }
/*      */           
/*      */           public int size() {
/* 1294 */             return Long2DoubleOpenHashMap.this.size;
/*      */           }
/*      */ 
/*      */           
/*      */           public boolean contains(double v) {
/* 1299 */             return Long2DoubleOpenHashMap.this.containsValue(v);
/*      */           }
/*      */ 
/*      */           
/*      */           public void clear() {
/* 1304 */             Long2DoubleOpenHashMap.this.clear();
/*      */           }
/*      */         }; 
/* 1307 */     return this.values;
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
/* 1324 */     return trim(this.size);
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
/* 1346 */     int l = HashCommon.nextPowerOfTwo((int)Math.ceil((n / this.f)));
/* 1347 */     if (l >= this.n || this.size > HashCommon.maxFill(l, this.f)) return true; 
/*      */     try {
/* 1349 */       rehash(l);
/* 1350 */     } catch (OutOfMemoryError cantDoIt) {
/* 1351 */       return false;
/*      */     } 
/* 1353 */     return true;
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
/* 1368 */     long[] key = this.key;
/* 1369 */     double[] value = this.value;
/* 1370 */     int mask = newN - 1;
/* 1371 */     long[] newKey = new long[newN + 1];
/* 1372 */     double[] newValue = new double[newN + 1];
/* 1373 */     int i = this.n;
/* 1374 */     for (int j = realSize(); j-- != 0; ) {
/* 1375 */       while (key[--i] == 0L); int pos;
/* 1376 */       if (newKey[pos = (int)HashCommon.mix(key[i]) & mask] != 0L) while (newKey[pos = pos + 1 & mask] != 0L); 
/* 1377 */       newKey[pos] = key[i];
/* 1378 */       newValue[pos] = value[i];
/*      */     } 
/* 1380 */     newValue[newN] = value[this.n];
/* 1381 */     this.n = newN;
/* 1382 */     this.mask = mask;
/* 1383 */     this.maxFill = HashCommon.maxFill(this.n, this.f);
/* 1384 */     this.key = newKey;
/* 1385 */     this.value = newValue;
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
/*      */   public Long2DoubleOpenHashMap clone() {
/*      */     Long2DoubleOpenHashMap c;
/*      */     try {
/* 1402 */       c = (Long2DoubleOpenHashMap)super.clone();
/* 1403 */     } catch (CloneNotSupportedException cantHappen) {
/* 1404 */       throw new InternalError();
/*      */     } 
/* 1406 */     c.keys = null;
/* 1407 */     c.values = null;
/* 1408 */     c.entries = null;
/* 1409 */     c.containsNullKey = this.containsNullKey;
/* 1410 */     c.key = (long[])this.key.clone();
/* 1411 */     c.value = (double[])this.value.clone();
/* 1412 */     return c;
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
/* 1426 */     int h = 0;
/* 1427 */     for (int j = realSize(), i = 0, t = 0; j-- != 0; ) {
/* 1428 */       for (; this.key[i] == 0L; i++);
/* 1429 */       t = HashCommon.long2int(this.key[i]);
/* 1430 */       t ^= HashCommon.double2int(this.value[i]);
/* 1431 */       h += t;
/* 1432 */       i++;
/*      */     } 
/*      */     
/* 1435 */     if (this.containsNullKey) h += HashCommon.double2int(this.value[this.n]); 
/* 1436 */     return h;
/*      */   }
/*      */   
/*      */   private void writeObject(ObjectOutputStream s) throws IOException {
/* 1440 */     long[] key = this.key;
/* 1441 */     double[] value = this.value;
/* 1442 */     EntryIterator i = new EntryIterator();
/* 1443 */     s.defaultWriteObject();
/* 1444 */     for (int j = this.size; j-- != 0; ) {
/* 1445 */       int e = i.nextEntry();
/* 1446 */       s.writeLong(key[e]);
/* 1447 */       s.writeDouble(value[e]);
/*      */     } 
/*      */   }
/*      */   
/*      */   private void readObject(ObjectInputStream s) throws IOException, ClassNotFoundException {
/* 1452 */     s.defaultReadObject();
/* 1453 */     this.n = HashCommon.arraySize(this.size, this.f);
/* 1454 */     this.maxFill = HashCommon.maxFill(this.n, this.f);
/* 1455 */     this.mask = this.n - 1;
/* 1456 */     long[] key = this.key = new long[this.n + 1];
/* 1457 */     double[] value = this.value = new double[this.n + 1];
/*      */ 
/*      */     
/* 1460 */     for (int i = this.size; i-- != 0; ) {
/* 1461 */       int pos; long k = s.readLong();
/* 1462 */       double v = s.readDouble();
/* 1463 */       if (k == 0L) {
/* 1464 */         pos = this.n;
/* 1465 */         this.containsNullKey = true;
/*      */       } else {
/* 1467 */         pos = (int)HashCommon.mix(k) & this.mask;
/* 1468 */         for (; key[pos] != 0L; pos = pos + 1 & this.mask);
/*      */       } 
/* 1470 */       key[pos] = k;
/* 1471 */       value[pos] = v;
/*      */     } 
/*      */   }
/*      */   
/*      */   private void checkTable() {}
/*      */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\i\\unimi\dsi\fastutil\longs\Long2DoubleOpenHashMap.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */