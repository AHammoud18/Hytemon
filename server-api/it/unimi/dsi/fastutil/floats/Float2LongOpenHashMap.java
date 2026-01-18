/*      */ package it.unimi.dsi.fastutil.floats;
/*      */ 
/*      */ import it.unimi.dsi.fastutil.Hash;
/*      */ import it.unimi.dsi.fastutil.HashCommon;
/*      */ import it.unimi.dsi.fastutil.longs.AbstractLongCollection;
/*      */ import it.unimi.dsi.fastutil.longs.LongCollection;
/*      */ import it.unimi.dsi.fastutil.longs.LongIterator;
/*      */ import it.unimi.dsi.fastutil.longs.LongSpliterator;
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
/*      */ import java.util.function.DoubleFunction;
/*      */ import java.util.function.DoubleToLongFunction;
/*      */ import java.util.function.LongConsumer;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ public class Float2LongOpenHashMap
/*      */   extends AbstractFloat2LongMap
/*      */   implements Serializable, Cloneable, Hash
/*      */ {
/*      */   private static final long serialVersionUID = 0L;
/*      */   private static final boolean ASSERTS = false;
/*      */   protected transient float[] key;
/*      */   protected transient long[] value;
/*      */   protected transient int mask;
/*      */   protected transient boolean containsNullKey;
/*      */   protected transient int n;
/*      */   protected transient int maxFill;
/*      */   protected final transient int minN;
/*      */   protected int size;
/*      */   protected final float f;
/*      */   protected transient Float2LongMap.FastEntrySet entries;
/*      */   protected transient FloatSet keys;
/*      */   protected transient LongCollection values;
/*      */   
/*      */   public Float2LongOpenHashMap(int expected, float f) {
/*   99 */     if (f <= 0.0F || f >= 1.0F) throw new IllegalArgumentException("Load factor must be greater than 0 and smaller than 1"); 
/*  100 */     if (expected < 0) throw new IllegalArgumentException("The expected number of elements must be nonnegative"); 
/*  101 */     this.f = f;
/*  102 */     this.minN = this.n = HashCommon.arraySize(expected, f);
/*  103 */     this.mask = this.n - 1;
/*  104 */     this.maxFill = HashCommon.maxFill(this.n, f);
/*  105 */     this.key = new float[this.n + 1];
/*  106 */     this.value = new long[this.n + 1];
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Float2LongOpenHashMap(int expected) {
/*  115 */     this(expected, 0.75F);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Float2LongOpenHashMap() {
/*  123 */     this(16, 0.75F);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Float2LongOpenHashMap(Map<? extends Float, ? extends Long> m, float f) {
/*  133 */     this(m.size(), f);
/*  134 */     putAll(m);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Float2LongOpenHashMap(Map<? extends Float, ? extends Long> m) {
/*  143 */     this(m, 0.75F);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Float2LongOpenHashMap(Float2LongMap m, float f) {
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
/*      */   public Float2LongOpenHashMap(Float2LongMap m) {
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
/*      */   public Float2LongOpenHashMap(float[] k, long[] v, float f) {
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
/*      */   public Float2LongOpenHashMap(float[] k, long[] v) {
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
/*      */   private long removeEntry(int pos) {
/*  214 */     long oldValue = this.value[pos];
/*  215 */     this.size--;
/*  216 */     shiftKeys(pos);
/*  217 */     if (this.n > this.minN && this.size < this.maxFill / 4 && this.n > 16) rehash(this.n / 2); 
/*  218 */     return oldValue;
/*      */   }
/*      */   
/*      */   private long removeNullEntry() {
/*  222 */     this.containsNullKey = false;
/*  223 */     long oldValue = this.value[this.n];
/*  224 */     this.size--;
/*  225 */     if (this.n > this.minN && this.size < this.maxFill / 4 && this.n > 16) rehash(this.n / 2); 
/*  226 */     return oldValue;
/*      */   }
/*      */ 
/*      */   
/*      */   public void putAll(Map<? extends Float, ? extends Long> m) {
/*  231 */     if (this.f <= 0.5D) { ensureCapacity(m.size()); }
/*  232 */     else { tryCapacity((size() + m.size())); }
/*      */     
/*  234 */     super.putAll(m);
/*      */   }
/*      */   
/*      */   private int find(float k) {
/*  238 */     if (Float.floatToIntBits(k) == 0) return this.containsNullKey ? this.n : -(this.n + 1);
/*      */     
/*  240 */     float[] key = this.key;
/*      */     float curr;
/*      */     int pos;
/*  243 */     if (Float.floatToIntBits(curr = key[pos = HashCommon.mix(HashCommon.float2int(k)) & this.mask]) == 0) return -(pos + 1); 
/*  244 */     if (Float.floatToIntBits(k) == Float.floatToIntBits(curr)) return pos;
/*      */     
/*      */     while (true) {
/*  247 */       if (Float.floatToIntBits(curr = key[pos = pos + 1 & this.mask]) == 0) return -(pos + 1); 
/*  248 */       if (Float.floatToIntBits(k) == Float.floatToIntBits(curr)) return pos; 
/*      */     } 
/*      */   }
/*      */   
/*      */   private void insert(int pos, float k, long v) {
/*  253 */     if (pos == this.n) this.containsNullKey = true; 
/*  254 */     this.key[pos] = k;
/*  255 */     this.value[pos] = v;
/*  256 */     if (this.size++ >= this.maxFill) rehash(HashCommon.arraySize(this.size + 1, this.f));
/*      */   
/*      */   }
/*      */ 
/*      */   
/*      */   public long put(float k, long v) {
/*  262 */     int pos = find(k);
/*  263 */     if (pos < 0) {
/*  264 */       insert(-pos - 1, k, v);
/*  265 */       return this.defRetValue;
/*      */     } 
/*  267 */     long oldValue = this.value[pos];
/*  268 */     this.value[pos] = v;
/*  269 */     return oldValue;
/*      */   }
/*      */   
/*      */   private long addToValue(int pos, long incr) {
/*  273 */     long oldValue = this.value[pos];
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
/*      */   public long addTo(float k, long incr) {
/*      */     int pos;
/*  293 */     if (Float.floatToIntBits(k) == 0) {
/*  294 */       if (this.containsNullKey) return addToValue(this.n, incr); 
/*  295 */       pos = this.n;
/*  296 */       this.containsNullKey = true;
/*      */     } else {
/*      */       
/*  299 */       float[] key = this.key;
/*      */       float curr;
/*  301 */       if (Float.floatToIntBits(curr = key[pos = HashCommon.mix(HashCommon.float2int(k)) & this.mask]) != 0) {
/*  302 */         if (Float.floatToIntBits(curr) == Float.floatToIntBits(k)) return addToValue(pos, incr); 
/*  303 */         while (Float.floatToIntBits(curr = key[pos = pos + 1 & this.mask]) != 0) { if (Float.floatToIntBits(curr) == Float.floatToIntBits(k)) return addToValue(pos, incr);  }
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
/*  323 */     float[] key = this.key; while (true) {
/*      */       float curr; int last;
/*  325 */       pos = (last = pos) + 1 & this.mask;
/*      */       while (true) {
/*  327 */         if (Float.floatToIntBits(curr = key[pos]) == 0) {
/*  328 */           key[last] = 0.0F;
/*      */           return;
/*      */         } 
/*  331 */         int slot = HashCommon.mix(HashCommon.float2int(curr)) & this.mask;
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
/*      */   public long remove(float k) {
/*  343 */     if (Float.floatToIntBits(k) == 0) {
/*  344 */       if (this.containsNullKey) return removeNullEntry(); 
/*  345 */       return this.defRetValue;
/*      */     } 
/*      */     
/*  348 */     float[] key = this.key;
/*      */     float curr;
/*      */     int pos;
/*  351 */     if (Float.floatToIntBits(curr = key[pos = HashCommon.mix(HashCommon.float2int(k)) & this.mask]) == 0) return this.defRetValue; 
/*  352 */     if (Float.floatToIntBits(k) == Float.floatToIntBits(curr)) return removeEntry(pos); 
/*      */     while (true) {
/*  354 */       if (Float.floatToIntBits(curr = key[pos = pos + 1 & this.mask]) == 0) return this.defRetValue; 
/*  355 */       if (Float.floatToIntBits(k) == Float.floatToIntBits(curr)) return removeEntry(pos);
/*      */     
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   public long get(float k) {
/*  362 */     if (Float.floatToIntBits(k) == 0) return this.containsNullKey ? this.value[this.n] : this.defRetValue;
/*      */     
/*  364 */     float[] key = this.key;
/*      */     float curr;
/*      */     int pos;
/*  367 */     if (Float.floatToIntBits(curr = key[pos = HashCommon.mix(HashCommon.float2int(k)) & this.mask]) == 0) return this.defRetValue; 
/*  368 */     if (Float.floatToIntBits(k) == Float.floatToIntBits(curr)) return this.value[pos];
/*      */     
/*      */     while (true) {
/*  371 */       if (Float.floatToIntBits(curr = key[pos = pos + 1 & this.mask]) == 0) return this.defRetValue; 
/*  372 */       if (Float.floatToIntBits(k) == Float.floatToIntBits(curr)) return this.value[pos];
/*      */     
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   public boolean containsKey(float k) {
/*  379 */     if (Float.floatToIntBits(k) == 0) return this.containsNullKey;
/*      */     
/*  381 */     float[] key = this.key;
/*      */     float curr;
/*      */     int pos;
/*  384 */     if (Float.floatToIntBits(curr = key[pos = HashCommon.mix(HashCommon.float2int(k)) & this.mask]) == 0) return false; 
/*  385 */     if (Float.floatToIntBits(k) == Float.floatToIntBits(curr)) return true;
/*      */     
/*      */     while (true) {
/*  388 */       if (Float.floatToIntBits(curr = key[pos = pos + 1 & this.mask]) == 0) return false; 
/*  389 */       if (Float.floatToIntBits(k) == Float.floatToIntBits(curr)) return true;
/*      */     
/*      */     } 
/*      */   }
/*      */   
/*      */   public boolean containsValue(long v) {
/*  395 */     long[] value = this.value;
/*  396 */     float[] key = this.key;
/*  397 */     if (this.containsNullKey && value[this.n] == v) return true; 
/*  398 */     for (int i = this.n; i-- != 0;) { if (Float.floatToIntBits(key[i]) != 0 && value[i] == v) return true;  }
/*  399 */      return false;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public long getOrDefault(float k, long defaultValue) {
/*  406 */     if (Float.floatToIntBits(k) == 0) return this.containsNullKey ? this.value[this.n] : defaultValue;
/*      */     
/*  408 */     float[] key = this.key;
/*      */     float curr;
/*      */     int pos;
/*  411 */     if (Float.floatToIntBits(curr = key[pos = HashCommon.mix(HashCommon.float2int(k)) & this.mask]) == 0) return defaultValue; 
/*  412 */     if (Float.floatToIntBits(k) == Float.floatToIntBits(curr)) return this.value[pos];
/*      */     
/*      */     while (true) {
/*  415 */       if (Float.floatToIntBits(curr = key[pos = pos + 1 & this.mask]) == 0) return defaultValue; 
/*  416 */       if (Float.floatToIntBits(k) == Float.floatToIntBits(curr)) return this.value[pos];
/*      */     
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   public long putIfAbsent(float k, long v) {
/*  423 */     int pos = find(k);
/*  424 */     if (pos >= 0) return this.value[pos]; 
/*  425 */     insert(-pos - 1, k, v);
/*  426 */     return this.defRetValue;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean remove(float k, long v) {
/*  433 */     if (Float.floatToIntBits(k) == 0) {
/*  434 */       if (this.containsNullKey && v == this.value[this.n]) {
/*  435 */         removeNullEntry();
/*  436 */         return true;
/*      */       } 
/*  438 */       return false;
/*      */     } 
/*      */     
/*  441 */     float[] key = this.key;
/*      */     float curr;
/*      */     int pos;
/*  444 */     if (Float.floatToIntBits(curr = key[pos = HashCommon.mix(HashCommon.float2int(k)) & this.mask]) == 0) return false; 
/*  445 */     if (Float.floatToIntBits(k) == Float.floatToIntBits(curr) && v == this.value[pos]) {
/*  446 */       removeEntry(pos);
/*  447 */       return true;
/*      */     } 
/*      */     while (true) {
/*  450 */       if (Float.floatToIntBits(curr = key[pos = pos + 1 & this.mask]) == 0) return false; 
/*  451 */       if (Float.floatToIntBits(k) == Float.floatToIntBits(curr) && v == this.value[pos]) {
/*  452 */         removeEntry(pos);
/*  453 */         return true;
/*      */       } 
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean replace(float k, long oldValue, long v) {
/*  461 */     int pos = find(k);
/*  462 */     if (pos < 0 || oldValue != this.value[pos]) return false; 
/*  463 */     this.value[pos] = v;
/*  464 */     return true;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public long replace(float k, long v) {
/*  470 */     int pos = find(k);
/*  471 */     if (pos < 0) return this.defRetValue; 
/*  472 */     long oldValue = this.value[pos];
/*  473 */     this.value[pos] = v;
/*  474 */     return oldValue;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public long computeIfAbsent(float k, DoubleToLongFunction mappingFunction) {
/*  480 */     Objects.requireNonNull(mappingFunction);
/*  481 */     int pos = find(k);
/*  482 */     if (pos >= 0) return this.value[pos]; 
/*  483 */     long newValue = mappingFunction.applyAsLong(k);
/*  484 */     insert(-pos - 1, k, newValue);
/*  485 */     return newValue;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public long computeIfAbsent(float key, Float2LongFunction mappingFunction) {
/*  491 */     Objects.requireNonNull(mappingFunction);
/*  492 */     int pos = find(key);
/*  493 */     if (pos >= 0) return this.value[pos]; 
/*  494 */     if (!mappingFunction.containsKey(key)) return this.defRetValue; 
/*  495 */     long newValue = mappingFunction.get(key);
/*  496 */     insert(-pos - 1, key, newValue);
/*  497 */     return newValue;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public long computeIfAbsentNullable(float k, DoubleFunction<? extends Long> mappingFunction) {
/*  503 */     Objects.requireNonNull(mappingFunction);
/*  504 */     int pos = find(k);
/*  505 */     if (pos >= 0) return this.value[pos]; 
/*  506 */     Long newValue = mappingFunction.apply(k);
/*  507 */     if (newValue == null) return this.defRetValue; 
/*  508 */     long v = newValue.longValue();
/*  509 */     insert(-pos - 1, k, v);
/*  510 */     return v;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public long computeIfPresent(float k, BiFunction<? super Float, ? super Long, ? extends Long> remappingFunction) {
/*  516 */     Objects.requireNonNull(remappingFunction);
/*  517 */     int pos = find(k);
/*  518 */     if (pos < 0) return this.defRetValue; 
/*  519 */     Long newValue = remappingFunction.apply(Float.valueOf(k), Long.valueOf(this.value[pos]));
/*  520 */     if (newValue == null) {
/*  521 */       if (Float.floatToIntBits(k) == 0) { removeNullEntry(); }
/*  522 */       else { removeEntry(pos); }
/*  523 */        return this.defRetValue;
/*      */     } 
/*  525 */     this.value[pos] = newValue.longValue(); return newValue.longValue();
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public long compute(float k, BiFunction<? super Float, ? super Long, ? extends Long> remappingFunction) {
/*  531 */     Objects.requireNonNull(remappingFunction);
/*  532 */     int pos = find(k);
/*  533 */     Long newValue = remappingFunction.apply(Float.valueOf(k), (pos >= 0) ? Long.valueOf(this.value[pos]) : null);
/*  534 */     if (newValue == null) {
/*  535 */       if (pos >= 0)
/*  536 */         if (Float.floatToIntBits(k) == 0) { removeNullEntry(); }
/*  537 */         else { removeEntry(pos); }
/*      */          
/*  539 */       return this.defRetValue;
/*      */     } 
/*  541 */     long newVal = newValue.longValue();
/*  542 */     if (pos < 0) {
/*  543 */       insert(-pos - 1, k, newVal);
/*  544 */       return newVal;
/*      */     } 
/*  546 */     this.value[pos] = newVal; return newVal;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public long merge(float k, long v, BiFunction<? super Long, ? super Long, ? extends Long> remappingFunction) {
/*  552 */     Objects.requireNonNull(remappingFunction);
/*      */     
/*  554 */     int pos = find(k);
/*  555 */     if (pos < 0) {
/*  556 */       if (pos < 0) { insert(-pos - 1, k, v); }
/*  557 */       else { this.value[pos] = v; }
/*  558 */        return v;
/*      */     } 
/*  560 */     Long newValue = remappingFunction.apply(Long.valueOf(this.value[pos]), Long.valueOf(v));
/*  561 */     if (newValue == null) {
/*  562 */       if (Float.floatToIntBits(k) == 0) { removeNullEntry(); }
/*  563 */       else { removeEntry(pos); }
/*  564 */        return this.defRetValue;
/*      */     } 
/*  566 */     this.value[pos] = newValue.longValue(); return newValue.longValue();
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
/*  580 */     Arrays.fill(this.key, 0.0F);
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
/*      */     implements Float2LongMap.Entry, Map.Entry<Float, Long>, FloatLongPair
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
/*      */     public float getFloatKey() {
/*  611 */       return Float2LongOpenHashMap.this.key[this.index];
/*      */     }
/*      */ 
/*      */     
/*      */     public float leftFloat() {
/*  616 */       return Float2LongOpenHashMap.this.key[this.index];
/*      */     }
/*      */ 
/*      */     
/*      */     public long getLongValue() {
/*  621 */       return Float2LongOpenHashMap.this.value[this.index];
/*      */     }
/*      */ 
/*      */     
/*      */     public long rightLong() {
/*  626 */       return Float2LongOpenHashMap.this.value[this.index];
/*      */     }
/*      */ 
/*      */     
/*      */     public long setValue(long v) {
/*  631 */       long oldValue = Float2LongOpenHashMap.this.value[this.index];
/*  632 */       Float2LongOpenHashMap.this.value[this.index] = v;
/*  633 */       return oldValue;
/*      */     }
/*      */ 
/*      */     
/*      */     public FloatLongPair right(long v) {
/*  638 */       Float2LongOpenHashMap.this.value[this.index] = v;
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
/*      */     public Float getKey() {
/*  650 */       return Float.valueOf(Float2LongOpenHashMap.this.key[this.index]);
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
/*  661 */       return Long.valueOf(Float2LongOpenHashMap.this.value[this.index]);
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
/*  672 */       return Long.valueOf(setValue(v.longValue()));
/*      */     }
/*      */ 
/*      */ 
/*      */     
/*      */     public boolean equals(Object o) {
/*  678 */       if (!(o instanceof Map.Entry)) return false; 
/*  679 */       Map.Entry<Float, Long> e = (Map.Entry<Float, Long>)o;
/*  680 */       return (Float.floatToIntBits(Float2LongOpenHashMap.this.key[this.index]) == Float.floatToIntBits(((Float)e.getKey()).floatValue()) && Float2LongOpenHashMap.this.value[this.index] == ((Long)e.getValue()).longValue());
/*      */     }
/*      */ 
/*      */     
/*      */     public int hashCode() {
/*  685 */       return HashCommon.float2int(Float2LongOpenHashMap.this.key[this.index]) ^ HashCommon.long2int(Float2LongOpenHashMap.this.value[this.index]);
/*      */     }
/*      */ 
/*      */     
/*      */     public String toString() {
/*  690 */       return Float2LongOpenHashMap.this.key[this.index] + "=>" + Float2LongOpenHashMap.this.value[this.index];
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
/*  701 */     int pos = Float2LongOpenHashMap.this.n;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  707 */     int last = -1;
/*      */     
/*  709 */     int c = Float2LongOpenHashMap.this.size;
/*      */     
/*  711 */     boolean mustReturnNullKey = Float2LongOpenHashMap.this.containsNullKey;
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
/*  722 */       return (this.c != 0);
/*      */     }
/*      */     
/*      */     public int nextEntry() {
/*  726 */       if (!hasNext()) throw new NoSuchElementException(); 
/*  727 */       this.c--;
/*  728 */       if (this.mustReturnNullKey) {
/*  729 */         this.mustReturnNullKey = false;
/*  730 */         return this.last = Float2LongOpenHashMap.this.n;
/*      */       } 
/*  732 */       float[] key = Float2LongOpenHashMap.this.key;
/*      */       while (true) {
/*  734 */         if (--this.pos < 0) {
/*      */           
/*  736 */           this.last = Integer.MIN_VALUE;
/*  737 */           float k = this.wrapped.getFloat(-this.pos - 1);
/*  738 */           int p = HashCommon.mix(HashCommon.float2int(k)) & Float2LongOpenHashMap.this.mask;
/*  739 */           for (; Float.floatToIntBits(k) != Float.floatToIntBits(key[p]); p = p + 1 & Float2LongOpenHashMap.this.mask);
/*  740 */           return p;
/*      */         } 
/*  742 */         if (Float.floatToIntBits(key[this.pos]) != 0) return this.last = this.pos; 
/*      */       } 
/*      */     }
/*      */     
/*      */     public void forEachRemaining(ConsumerType action) {
/*  747 */       if (this.mustReturnNullKey) {
/*  748 */         this.mustReturnNullKey = false;
/*  749 */         acceptOnIndex(action, this.last = Float2LongOpenHashMap.this.n);
/*  750 */         this.c--;
/*      */       } 
/*  752 */       float[] key = Float2LongOpenHashMap.this.key;
/*  753 */       while (this.c != 0) {
/*  754 */         if (--this.pos < 0) {
/*      */           
/*  756 */           this.last = Integer.MIN_VALUE;
/*  757 */           float k = this.wrapped.getFloat(-this.pos - 1);
/*  758 */           int p = HashCommon.mix(HashCommon.float2int(k)) & Float2LongOpenHashMap.this.mask;
/*  759 */           for (; Float.floatToIntBits(k) != Float.floatToIntBits(key[p]); p = p + 1 & Float2LongOpenHashMap.this.mask);
/*  760 */           acceptOnIndex(action, p);
/*  761 */           this.c--; continue;
/*  762 */         }  if (Float.floatToIntBits(key[this.pos]) != 0) {
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
/*  779 */       float[] key = Float2LongOpenHashMap.this.key; while (true) {
/*      */         float curr; int last;
/*  781 */         pos = (last = pos) + 1 & Float2LongOpenHashMap.this.mask;
/*      */         while (true) {
/*  783 */           if (Float.floatToIntBits(curr = key[pos]) == 0) {
/*  784 */             key[last] = 0.0F;
/*      */             return;
/*      */           } 
/*  787 */           int slot = HashCommon.mix(HashCommon.float2int(curr)) & Float2LongOpenHashMap.this.mask;
/*  788 */           if ((last <= pos) ? (last >= slot || slot > pos) : (last >= slot && slot > pos))
/*  789 */             break;  pos = pos + 1 & Float2LongOpenHashMap.this.mask;
/*      */         } 
/*  791 */         if (pos < last) {
/*  792 */           if (this.wrapped == null) this.wrapped = new FloatArrayList(2); 
/*  793 */           this.wrapped.add(key[pos]);
/*      */         } 
/*  795 */         key[last] = curr;
/*  796 */         Float2LongOpenHashMap.this.value[last] = Float2LongOpenHashMap.this.value[pos];
/*      */       } 
/*      */     }
/*      */     
/*      */     public void remove() {
/*  801 */       if (this.last == -1) throw new IllegalStateException(); 
/*  802 */       if (this.last == Float2LongOpenHashMap.this.n)
/*  803 */       { Float2LongOpenHashMap.this.containsNullKey = false; }
/*  804 */       else if (this.pos >= 0) { shiftKeys(this.last); }
/*      */       else
/*      */       
/*  807 */       { Float2LongOpenHashMap.this.remove(this.wrapped.getFloat(-this.pos - 1));
/*  808 */         this.last = -1;
/*      */         return; }
/*      */       
/*  811 */       Float2LongOpenHashMap.this.size--;
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
/*      */   private final class EntryIterator extends MapIterator<Consumer<? super Float2LongMap.Entry>> implements ObjectIterator<Float2LongMap.Entry> { public Float2LongOpenHashMap.MapEntry next() {
/*  828 */       return this.entry = new Float2LongOpenHashMap.MapEntry(nextEntry());
/*      */     }
/*      */     private Float2LongOpenHashMap.MapEntry entry;
/*      */     private EntryIterator() {}
/*      */     
/*      */     final void acceptOnIndex(Consumer<? super Float2LongMap.Entry> action, int index) {
/*  834 */       action.accept(this.entry = new Float2LongOpenHashMap.MapEntry(index));
/*      */     }
/*      */ 
/*      */     
/*      */     public void remove() {
/*  839 */       super.remove();
/*  840 */       this.entry.index = -1;
/*      */     } }
/*      */   
/*      */   private final class FastEntryIterator extends MapIterator<Consumer<? super Float2LongMap.Entry>> implements ObjectIterator<Float2LongMap.Entry> {
/*      */     private FastEntryIterator() {
/*  845 */       this.entry = new Float2LongOpenHashMap.MapEntry();
/*      */     }
/*      */     private final Float2LongOpenHashMap.MapEntry entry;
/*      */     public Float2LongOpenHashMap.MapEntry next() {
/*  849 */       this.entry.index = nextEntry();
/*  850 */       return this.entry;
/*      */     }
/*      */ 
/*      */ 
/*      */     
/*      */     final void acceptOnIndex(Consumer<? super Float2LongMap.Entry> action, int index) {
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
/*  868 */     int max = Float2LongOpenHashMap.this.n;
/*      */     
/*  870 */     int c = 0;
/*      */     
/*  872 */     boolean mustReturnNull = Float2LongOpenHashMap.this.containsNullKey;
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
/*  893 */         acceptOnIndex(action, Float2LongOpenHashMap.this.n);
/*  894 */         return true;
/*      */       } 
/*  896 */       float[] key = Float2LongOpenHashMap.this.key;
/*  897 */       while (this.pos < this.max) {
/*  898 */         if (Float.floatToIntBits(key[this.pos]) != 0) {
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
/*  912 */         acceptOnIndex(action, Float2LongOpenHashMap.this.n);
/*      */       } 
/*  914 */       float[] key = Float2LongOpenHashMap.this.key;
/*  915 */       while (this.pos < this.max) {
/*  916 */         if (Float.floatToIntBits(key[this.pos]) != 0) {
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
/*  927 */         return (Float2LongOpenHashMap.this.size - this.c);
/*      */       }
/*      */ 
/*      */ 
/*      */       
/*  932 */       return Math.min((Float2LongOpenHashMap.this.size - this.c), (long)(Float2LongOpenHashMap.this.realSize() / Float2LongOpenHashMap.this.n * (this.max - this.pos)) + (this.mustReturnNull ? 1L : 0L));
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
/*  962 */       float[] key = Float2LongOpenHashMap.this.key;
/*  963 */       while (this.pos < this.max && n > 0L) {
/*  964 */         if (Float.floatToIntBits(key[this.pos++]) != 0) {
/*  965 */           skipped++;
/*  966 */           n--;
/*      */         } 
/*      */       } 
/*  969 */       return skipped;
/*      */     }
/*      */   }
/*      */   
/*      */   private final class EntrySpliterator
/*      */     extends MapSpliterator<Consumer<? super Float2LongMap.Entry>, EntrySpliterator> implements ObjectSpliterator<Float2LongMap.Entry> {
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
/*      */     final void acceptOnIndex(Consumer<? super Float2LongMap.Entry> action, int index) {
/*  990 */       action.accept(new Float2LongOpenHashMap.MapEntry(index));
/*      */     }
/*      */ 
/*      */     
/*      */     final EntrySpliterator makeForSplit(int pos, int max, boolean mustReturnNull) {
/*  995 */       return new EntrySpliterator(pos, max, mustReturnNull, true);
/*      */     } }
/*      */   
/*      */   private final class MapEntrySet extends AbstractObjectSet<Float2LongMap.Entry> implements Float2LongMap.FastEntrySet {
/*      */     private MapEntrySet() {}
/*      */     
/*      */     public ObjectIterator<Float2LongMap.Entry> iterator() {
/* 1002 */       return new Float2LongOpenHashMap.EntryIterator();
/*      */     }
/*      */ 
/*      */     
/*      */     public ObjectIterator<Float2LongMap.Entry> fastIterator() {
/* 1007 */       return new Float2LongOpenHashMap.FastEntryIterator();
/*      */     }
/*      */ 
/*      */     
/*      */     public ObjectSpliterator<Float2LongMap.Entry> spliterator() {
/* 1012 */       return new Float2LongOpenHashMap.EntrySpliterator();
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public boolean contains(Object o) {
/* 1019 */       if (!(o instanceof Map.Entry)) return false; 
/* 1020 */       Map.Entry<?, ?> e = (Map.Entry<?, ?>)o;
/* 1021 */       if (e.getKey() == null || !(e.getKey() instanceof Float)) return false; 
/* 1022 */       if (e.getValue() == null || !(e.getValue() instanceof Long)) return false; 
/* 1023 */       float k = ((Float)e.getKey()).floatValue();
/* 1024 */       long v = ((Long)e.getValue()).longValue();
/* 1025 */       if (Float.floatToIntBits(k) == 0) return (Float2LongOpenHashMap.this.containsNullKey && Float2LongOpenHashMap.this.value[Float2LongOpenHashMap.this.n] == v);
/*      */       
/* 1027 */       float[] key = Float2LongOpenHashMap.this.key;
/*      */       float curr;
/*      */       int pos;
/* 1030 */       if (Float.floatToIntBits(curr = key[pos = HashCommon.mix(HashCommon.float2int(k)) & Float2LongOpenHashMap.this.mask]) == 0) return false; 
/* 1031 */       if (Float.floatToIntBits(k) == Float.floatToIntBits(curr)) return (Float2LongOpenHashMap.this.value[pos] == v);
/*      */       
/*      */       while (true) {
/* 1034 */         if (Float.floatToIntBits(curr = key[pos = pos + 1 & Float2LongOpenHashMap.this.mask]) == 0) return false; 
/* 1035 */         if (Float.floatToIntBits(k) == Float.floatToIntBits(curr)) return (Float2LongOpenHashMap.this.value[pos] == v);
/*      */       
/*      */       } 
/*      */     }
/*      */ 
/*      */     
/*      */     public boolean remove(Object o) {
/* 1042 */       if (!(o instanceof Map.Entry)) return false; 
/* 1043 */       Map.Entry<?, ?> e = (Map.Entry<?, ?>)o;
/* 1044 */       if (e.getKey() == null || !(e.getKey() instanceof Float)) return false; 
/* 1045 */       if (e.getValue() == null || !(e.getValue() instanceof Long)) return false; 
/* 1046 */       float k = ((Float)e.getKey()).floatValue();
/* 1047 */       long v = ((Long)e.getValue()).longValue();
/* 1048 */       if (Float.floatToIntBits(k) == 0) {
/* 1049 */         if (Float2LongOpenHashMap.this.containsNullKey && Float2LongOpenHashMap.this.value[Float2LongOpenHashMap.this.n] == v) {
/* 1050 */           Float2LongOpenHashMap.this.removeNullEntry();
/* 1051 */           return true;
/*      */         } 
/* 1053 */         return false;
/*      */       } 
/*      */       
/* 1056 */       float[] key = Float2LongOpenHashMap.this.key;
/*      */       float curr;
/*      */       int pos;
/* 1059 */       if (Float.floatToIntBits(curr = key[pos = HashCommon.mix(HashCommon.float2int(k)) & Float2LongOpenHashMap.this.mask]) == 0) return false; 
/* 1060 */       if (Float.floatToIntBits(curr) == Float.floatToIntBits(k)) {
/* 1061 */         if (Float2LongOpenHashMap.this.value[pos] == v) {
/* 1062 */           Float2LongOpenHashMap.this.removeEntry(pos);
/* 1063 */           return true;
/*      */         } 
/* 1065 */         return false;
/*      */       } 
/*      */       while (true) {
/* 1068 */         if (Float.floatToIntBits(curr = key[pos = pos + 1 & Float2LongOpenHashMap.this.mask]) == 0) return false; 
/* 1069 */         if (Float.floatToIntBits(curr) == Float.floatToIntBits(k) && 
/* 1070 */           Float2LongOpenHashMap.this.value[pos] == v) {
/* 1071 */           Float2LongOpenHashMap.this.removeEntry(pos);
/* 1072 */           return true;
/*      */         } 
/*      */       } 
/*      */     }
/*      */ 
/*      */ 
/*      */     
/*      */     public int size() {
/* 1080 */       return Float2LongOpenHashMap.this.size;
/*      */     }
/*      */ 
/*      */     
/*      */     public void clear() {
/* 1085 */       Float2LongOpenHashMap.this.clear();
/*      */     }
/*      */ 
/*      */ 
/*      */     
/*      */     public void forEach(Consumer<? super Float2LongMap.Entry> consumer) {
/* 1091 */       if (Float2LongOpenHashMap.this.containsNullKey) consumer.accept(new Float2LongOpenHashMap.MapEntry(Float2LongOpenHashMap.this.n)); 
/* 1092 */       for (int pos = Float2LongOpenHashMap.this.n; pos-- != 0;) { if (Float.floatToIntBits(Float2LongOpenHashMap.this.key[pos]) != 0) consumer.accept(new Float2LongOpenHashMap.MapEntry(pos));
/*      */          }
/*      */     
/*      */     }
/*      */     
/*      */     public void fastForEach(Consumer<? super Float2LongMap.Entry> consumer) {
/* 1098 */       Float2LongOpenHashMap.MapEntry entry = new Float2LongOpenHashMap.MapEntry();
/* 1099 */       if (Float2LongOpenHashMap.this.containsNullKey) {
/* 1100 */         entry.index = Float2LongOpenHashMap.this.n;
/* 1101 */         consumer.accept(entry);
/*      */       } 
/* 1103 */       for (int pos = Float2LongOpenHashMap.this.n; pos-- != 0;) { if (Float.floatToIntBits(Float2LongOpenHashMap.this.key[pos]) != 0) {
/* 1104 */           entry.index = pos;
/* 1105 */           consumer.accept(entry);
/*      */         }  }
/*      */     
/*      */     }
/*      */   }
/*      */   
/*      */   public Float2LongMap.FastEntrySet float2LongEntrySet() {
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
/*      */     extends MapIterator<FloatConsumer>
/*      */     implements FloatIterator
/*      */   {
/*      */     final void acceptOnIndex(FloatConsumer action, int index) {
/* 1134 */       action.accept(Float2LongOpenHashMap.this.key[index]);
/*      */     }
/*      */ 
/*      */     
/*      */     public float nextFloat() {
/* 1139 */       return Float2LongOpenHashMap.this.key[nextEntry()];
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
/* 1150 */       super(pos, max, mustReturnNull, hasSplit);
/*      */     }
/*      */ 
/*      */     
/*      */     public int characteristics() {
/* 1155 */       return this.hasSplit ? 257 : 321;
/*      */     }
/*      */ 
/*      */     
/*      */     final void acceptOnIndex(FloatConsumer action, int index) {
/* 1160 */       action.accept(Float2LongOpenHashMap.this.key[index]);
/*      */     }
/*      */ 
/*      */     
/*      */     final KeySpliterator makeForSplit(int pos, int max, boolean mustReturnNull) {
/* 1165 */       return new KeySpliterator(pos, max, mustReturnNull, true);
/*      */     } }
/*      */   
/*      */   private final class KeySet extends AbstractFloatSet {
/*      */     private KeySet() {}
/*      */     
/*      */     public FloatIterator iterator() {
/* 1172 */       return new Float2LongOpenHashMap.KeyIterator();
/*      */     }
/*      */ 
/*      */     
/*      */     public FloatSpliterator spliterator() {
/* 1177 */       return new Float2LongOpenHashMap.KeySpliterator();
/*      */     }
/*      */ 
/*      */ 
/*      */     
/*      */     public void forEach(FloatConsumer consumer) {
/* 1183 */       if (Float2LongOpenHashMap.this.containsNullKey) consumer.accept(Float2LongOpenHashMap.this.key[Float2LongOpenHashMap.this.n]); 
/* 1184 */       for (int pos = Float2LongOpenHashMap.this.n; pos-- != 0; ) {
/* 1185 */         float k = Float2LongOpenHashMap.this.key[pos];
/* 1186 */         if (Float.floatToIntBits(k) != 0) consumer.accept(k);
/*      */       
/*      */       } 
/*      */     }
/*      */     
/*      */     public int size() {
/* 1192 */       return Float2LongOpenHashMap.this.size;
/*      */     }
/*      */ 
/*      */     
/*      */     public boolean contains(float k) {
/* 1197 */       return Float2LongOpenHashMap.this.containsKey(k);
/*      */     }
/*      */ 
/*      */     
/*      */     public boolean remove(float k) {
/* 1202 */       int oldSize = Float2LongOpenHashMap.this.size;
/* 1203 */       Float2LongOpenHashMap.this.remove(k);
/* 1204 */       return (Float2LongOpenHashMap.this.size != oldSize);
/*      */     }
/*      */ 
/*      */     
/*      */     public void clear() {
/* 1209 */       Float2LongOpenHashMap.this.clear();
/*      */     }
/*      */   }
/*      */ 
/*      */   
/*      */   public FloatSet keySet() {
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
/*      */     extends MapIterator<LongConsumer>
/*      */     implements LongIterator
/*      */   {
/*      */     final void acceptOnIndex(LongConsumer action, int index) {
/* 1237 */       action.accept(Float2LongOpenHashMap.this.value[index]);
/*      */     }
/*      */ 
/*      */     
/*      */     public long nextLong() {
/* 1242 */       return Float2LongOpenHashMap.this.value[nextEntry()];
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
/* 1253 */       super(pos, max, mustReturnNull, hasSplit);
/*      */     }
/*      */ 
/*      */     
/*      */     public int characteristics() {
/* 1258 */       return this.hasSplit ? 256 : 320;
/*      */     }
/*      */ 
/*      */     
/*      */     final void acceptOnIndex(LongConsumer action, int index) {
/* 1263 */       action.accept(Float2LongOpenHashMap.this.value[index]);
/*      */     }
/*      */ 
/*      */     
/*      */     final ValueSpliterator makeForSplit(int pos, int max, boolean mustReturnNull) {
/* 1268 */       return new ValueSpliterator(pos, max, mustReturnNull, true);
/*      */     }
/*      */   }
/*      */ 
/*      */   
/*      */   public LongCollection values() {
/* 1274 */     if (this.values == null) this.values = (LongCollection)new AbstractLongCollection()
/*      */         {
/*      */           public LongIterator iterator() {
/* 1277 */             return new Float2LongOpenHashMap.ValueIterator();
/*      */           }
/*      */ 
/*      */           
/*      */           public LongSpliterator spliterator() {
/* 1282 */             return new Float2LongOpenHashMap.ValueSpliterator();
/*      */           }
/*      */ 
/*      */ 
/*      */           
/*      */           public void forEach(LongConsumer consumer) {
/* 1288 */             if (Float2LongOpenHashMap.this.containsNullKey) consumer.accept(Float2LongOpenHashMap.this.value[Float2LongOpenHashMap.this.n]); 
/* 1289 */             for (int pos = Float2LongOpenHashMap.this.n; pos-- != 0;) { if (Float.floatToIntBits(Float2LongOpenHashMap.this.key[pos]) != 0) consumer.accept(Float2LongOpenHashMap.this.value[pos]);  }
/*      */           
/*      */           }
/*      */           
/*      */           public int size() {
/* 1294 */             return Float2LongOpenHashMap.this.size;
/*      */           }
/*      */ 
/*      */           
/*      */           public boolean contains(long v) {
/* 1299 */             return Float2LongOpenHashMap.this.containsValue(v);
/*      */           }
/*      */ 
/*      */           
/*      */           public void clear() {
/* 1304 */             Float2LongOpenHashMap.this.clear();
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
/* 1368 */     float[] key = this.key;
/* 1369 */     long[] value = this.value;
/* 1370 */     int mask = newN - 1;
/* 1371 */     float[] newKey = new float[newN + 1];
/* 1372 */     long[] newValue = new long[newN + 1];
/* 1373 */     int i = this.n;
/* 1374 */     for (int j = realSize(); j-- != 0; ) {
/* 1375 */       while (Float.floatToIntBits(key[--i]) == 0); int pos;
/* 1376 */       if (Float.floatToIntBits(newKey[pos = HashCommon.mix(HashCommon.float2int(key[i])) & mask]) != 0) while (Float.floatToIntBits(newKey[pos = pos + 1 & mask]) != 0); 
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
/*      */   public Float2LongOpenHashMap clone() {
/*      */     Float2LongOpenHashMap c;
/*      */     try {
/* 1402 */       c = (Float2LongOpenHashMap)super.clone();
/* 1403 */     } catch (CloneNotSupportedException cantHappen) {
/* 1404 */       throw new InternalError();
/*      */     } 
/* 1406 */     c.keys = null;
/* 1407 */     c.values = null;
/* 1408 */     c.entries = null;
/* 1409 */     c.containsNullKey = this.containsNullKey;
/* 1410 */     c.key = (float[])this.key.clone();
/* 1411 */     c.value = (long[])this.value.clone();
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
/* 1428 */       for (; Float.floatToIntBits(this.key[i]) == 0; i++);
/* 1429 */       t = HashCommon.float2int(this.key[i]);
/* 1430 */       t ^= HashCommon.long2int(this.value[i]);
/* 1431 */       h += t;
/* 1432 */       i++;
/*      */     } 
/*      */     
/* 1435 */     if (this.containsNullKey) h += HashCommon.long2int(this.value[this.n]); 
/* 1436 */     return h;
/*      */   }
/*      */   
/*      */   private void writeObject(ObjectOutputStream s) throws IOException {
/* 1440 */     float[] key = this.key;
/* 1441 */     long[] value = this.value;
/* 1442 */     EntryIterator i = new EntryIterator();
/* 1443 */     s.defaultWriteObject();
/* 1444 */     for (int j = this.size; j-- != 0; ) {
/* 1445 */       int e = i.nextEntry();
/* 1446 */       s.writeFloat(key[e]);
/* 1447 */       s.writeLong(value[e]);
/*      */     } 
/*      */   }
/*      */   
/*      */   private void readObject(ObjectInputStream s) throws IOException, ClassNotFoundException {
/* 1452 */     s.defaultReadObject();
/* 1453 */     this.n = HashCommon.arraySize(this.size, this.f);
/* 1454 */     this.maxFill = HashCommon.maxFill(this.n, this.f);
/* 1455 */     this.mask = this.n - 1;
/* 1456 */     float[] key = this.key = new float[this.n + 1];
/* 1457 */     long[] value = this.value = new long[this.n + 1];
/*      */ 
/*      */     
/* 1460 */     for (int i = this.size; i-- != 0; ) {
/* 1461 */       int pos; float k = s.readFloat();
/* 1462 */       long v = s.readLong();
/* 1463 */       if (Float.floatToIntBits(k) == 0) {
/* 1464 */         pos = this.n;
/* 1465 */         this.containsNullKey = true;
/*      */       } else {
/* 1467 */         pos = HashCommon.mix(HashCommon.float2int(k)) & this.mask;
/* 1468 */         for (; Float.floatToIntBits(key[pos]) != 0; pos = pos + 1 & this.mask);
/*      */       } 
/* 1470 */       key[pos] = k;
/* 1471 */       value[pos] = v;
/*      */     } 
/*      */   }
/*      */   
/*      */   private void checkTable() {}
/*      */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\i\\unimi\dsi\fastutil\floats\Float2LongOpenHashMap.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */