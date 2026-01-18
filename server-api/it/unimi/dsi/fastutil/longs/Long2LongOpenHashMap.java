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
/*      */ public class Long2LongOpenHashMap
/*      */   extends AbstractLong2LongMap
/*      */   implements Serializable, Cloneable, Hash
/*      */ {
/*      */   private static final long serialVersionUID = 0L;
/*      */   private static final boolean ASSERTS = false;
/*      */   protected transient long[] key;
/*      */   protected transient long[] value;
/*      */   protected transient int mask;
/*      */   protected transient boolean containsNullKey;
/*      */   protected transient int n;
/*      */   protected transient int maxFill;
/*      */   protected final transient int minN;
/*      */   protected int size;
/*      */   protected final float f;
/*      */   protected transient Long2LongMap.FastEntrySet entries;
/*      */   protected transient LongSet keys;
/*      */   protected transient LongCollection values;
/*      */   
/*      */   public Long2LongOpenHashMap(int expected, float f) {
/*   94 */     if (f <= 0.0F || f >= 1.0F) throw new IllegalArgumentException("Load factor must be greater than 0 and smaller than 1"); 
/*   95 */     if (expected < 0) throw new IllegalArgumentException("The expected number of elements must be nonnegative"); 
/*   96 */     this.f = f;
/*   97 */     this.minN = this.n = HashCommon.arraySize(expected, f);
/*   98 */     this.mask = this.n - 1;
/*   99 */     this.maxFill = HashCommon.maxFill(this.n, f);
/*  100 */     this.key = new long[this.n + 1];
/*  101 */     this.value = new long[this.n + 1];
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Long2LongOpenHashMap(int expected) {
/*  110 */     this(expected, 0.75F);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Long2LongOpenHashMap() {
/*  118 */     this(16, 0.75F);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Long2LongOpenHashMap(Map<? extends Long, ? extends Long> m, float f) {
/*  128 */     this(m.size(), f);
/*  129 */     putAll(m);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Long2LongOpenHashMap(Map<? extends Long, ? extends Long> m) {
/*  138 */     this(m, 0.75F);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Long2LongOpenHashMap(Long2LongMap m, float f) {
/*  148 */     this(m.size(), f);
/*  149 */     putAll(m);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Long2LongOpenHashMap(Long2LongMap m) {
/*  159 */     this(m, 0.75F);
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
/*      */   public Long2LongOpenHashMap(long[] k, long[] v, float f) {
/*  171 */     this(k.length, f);
/*  172 */     if (k.length != v.length) throw new IllegalArgumentException("The key array and the value array have different lengths (" + k.length + " and " + v.length + ")"); 
/*  173 */     for (int i = 0; i < k.length; ) { put(k[i], v[i]); i++; }
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
/*      */   public Long2LongOpenHashMap(long[] k, long[] v) {
/*  185 */     this(k, v, 0.75F);
/*      */   }
/*      */   
/*      */   private int realSize() {
/*  189 */     return this.containsNullKey ? (this.size - 1) : this.size;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void ensureCapacity(int capacity) {
/*  199 */     int needed = HashCommon.arraySize(capacity, this.f);
/*  200 */     if (needed > this.n) rehash(needed); 
/*      */   }
/*      */   
/*      */   private void tryCapacity(long capacity) {
/*  204 */     int needed = (int)Math.min(1073741824L, Math.max(2L, HashCommon.nextPowerOfTwo((long)Math.ceil(((float)capacity / this.f)))));
/*  205 */     if (needed > this.n) rehash(needed); 
/*      */   }
/*      */   
/*      */   private long removeEntry(int pos) {
/*  209 */     long oldValue = this.value[pos];
/*  210 */     this.size--;
/*  211 */     shiftKeys(pos);
/*  212 */     if (this.n > this.minN && this.size < this.maxFill / 4 && this.n > 16) rehash(this.n / 2); 
/*  213 */     return oldValue;
/*      */   }
/*      */   
/*      */   private long removeNullEntry() {
/*  217 */     this.containsNullKey = false;
/*  218 */     long oldValue = this.value[this.n];
/*  219 */     this.size--;
/*  220 */     if (this.n > this.minN && this.size < this.maxFill / 4 && this.n > 16) rehash(this.n / 2); 
/*  221 */     return oldValue;
/*      */   }
/*      */ 
/*      */   
/*      */   public void putAll(Map<? extends Long, ? extends Long> m) {
/*  226 */     if (this.f <= 0.5D) { ensureCapacity(m.size()); }
/*  227 */     else { tryCapacity((size() + m.size())); }
/*      */     
/*  229 */     super.putAll(m);
/*      */   }
/*      */   
/*      */   private int find(long k) {
/*  233 */     if (k == 0L) return this.containsNullKey ? this.n : -(this.n + 1);
/*      */     
/*  235 */     long[] key = this.key;
/*      */     long curr;
/*      */     int pos;
/*  238 */     if ((curr = key[pos = (int)HashCommon.mix(k) & this.mask]) == 0L) return -(pos + 1); 
/*  239 */     if (k == curr) return pos;
/*      */     
/*      */     while (true) {
/*  242 */       if ((curr = key[pos = pos + 1 & this.mask]) == 0L) return -(pos + 1); 
/*  243 */       if (k == curr) return pos; 
/*      */     } 
/*      */   }
/*      */   
/*      */   private void insert(int pos, long k, long v) {
/*  248 */     if (pos == this.n) this.containsNullKey = true; 
/*  249 */     this.key[pos] = k;
/*  250 */     this.value[pos] = v;
/*  251 */     if (this.size++ >= this.maxFill) rehash(HashCommon.arraySize(this.size + 1, this.f));
/*      */   
/*      */   }
/*      */ 
/*      */   
/*      */   public long put(long k, long v) {
/*  257 */     int pos = find(k);
/*  258 */     if (pos < 0) {
/*  259 */       insert(-pos - 1, k, v);
/*  260 */       return this.defRetValue;
/*      */     } 
/*  262 */     long oldValue = this.value[pos];
/*  263 */     this.value[pos] = v;
/*  264 */     return oldValue;
/*      */   }
/*      */   
/*      */   private long addToValue(int pos, long incr) {
/*  268 */     long oldValue = this.value[pos];
/*  269 */     this.value[pos] = oldValue + incr;
/*  270 */     return oldValue;
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
/*  288 */     if (k == 0L) {
/*  289 */       if (this.containsNullKey) return addToValue(this.n, incr); 
/*  290 */       pos = this.n;
/*  291 */       this.containsNullKey = true;
/*      */     } else {
/*      */       
/*  294 */       long[] key = this.key;
/*      */       long curr;
/*  296 */       if ((curr = key[pos = (int)HashCommon.mix(k) & this.mask]) != 0L) {
/*  297 */         if (curr == k) return addToValue(pos, incr); 
/*  298 */         while ((curr = key[pos = pos + 1 & this.mask]) != 0L) { if (curr == k) return addToValue(pos, incr);  }
/*      */       
/*      */       } 
/*  301 */     }  this.key[pos] = k;
/*  302 */     this.value[pos] = this.defRetValue + incr;
/*  303 */     if (this.size++ >= this.maxFill) rehash(HashCommon.arraySize(this.size + 1, this.f));
/*      */     
/*  305 */     return this.defRetValue;
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
/*  318 */     long[] key = this.key; while (true) {
/*      */       long curr; int last;
/*  320 */       pos = (last = pos) + 1 & this.mask;
/*      */       while (true) {
/*  322 */         if ((curr = key[pos]) == 0L) {
/*  323 */           key[last] = 0L;
/*      */           return;
/*      */         } 
/*  326 */         int slot = (int)HashCommon.mix(curr) & this.mask;
/*  327 */         if ((last <= pos) ? (last >= slot || slot > pos) : (last >= slot && slot > pos))
/*  328 */           break;  pos = pos + 1 & this.mask;
/*      */       } 
/*  330 */       key[last] = curr;
/*  331 */       this.value[last] = this.value[pos];
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public long remove(long k) {
/*  338 */     if (k == 0L) {
/*  339 */       if (this.containsNullKey) return removeNullEntry(); 
/*  340 */       return this.defRetValue;
/*      */     } 
/*      */     
/*  343 */     long[] key = this.key;
/*      */     long curr;
/*      */     int pos;
/*  346 */     if ((curr = key[pos = (int)HashCommon.mix(k) & this.mask]) == 0L) return this.defRetValue; 
/*  347 */     if (k == curr) return removeEntry(pos); 
/*      */     while (true) {
/*  349 */       if ((curr = key[pos = pos + 1 & this.mask]) == 0L) return this.defRetValue; 
/*  350 */       if (k == curr) return removeEntry(pos);
/*      */     
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   public long get(long k) {
/*  357 */     if (k == 0L) return this.containsNullKey ? this.value[this.n] : this.defRetValue;
/*      */     
/*  359 */     long[] key = this.key;
/*      */     long curr;
/*      */     int pos;
/*  362 */     if ((curr = key[pos = (int)HashCommon.mix(k) & this.mask]) == 0L) return this.defRetValue; 
/*  363 */     if (k == curr) return this.value[pos];
/*      */     
/*      */     while (true) {
/*  366 */       if ((curr = key[pos = pos + 1 & this.mask]) == 0L) return this.defRetValue; 
/*  367 */       if (k == curr) return this.value[pos];
/*      */     
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   public boolean containsKey(long k) {
/*  374 */     if (k == 0L) return this.containsNullKey;
/*      */     
/*  376 */     long[] key = this.key;
/*      */     long curr;
/*      */     int pos;
/*  379 */     if ((curr = key[pos = (int)HashCommon.mix(k) & this.mask]) == 0L) return false; 
/*  380 */     if (k == curr) return true;
/*      */     
/*      */     while (true) {
/*  383 */       if ((curr = key[pos = pos + 1 & this.mask]) == 0L) return false; 
/*  384 */       if (k == curr) return true;
/*      */     
/*      */     } 
/*      */   }
/*      */   
/*      */   public boolean containsValue(long v) {
/*  390 */     long[] value = this.value;
/*  391 */     long[] key = this.key;
/*  392 */     if (this.containsNullKey && value[this.n] == v) return true; 
/*  393 */     for (int i = this.n; i-- != 0;) { if (key[i] != 0L && value[i] == v) return true;  }
/*  394 */      return false;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public long getOrDefault(long k, long defaultValue) {
/*  401 */     if (k == 0L) return this.containsNullKey ? this.value[this.n] : defaultValue;
/*      */     
/*  403 */     long[] key = this.key;
/*      */     long curr;
/*      */     int pos;
/*  406 */     if ((curr = key[pos = (int)HashCommon.mix(k) & this.mask]) == 0L) return defaultValue; 
/*  407 */     if (k == curr) return this.value[pos];
/*      */     
/*      */     while (true) {
/*  410 */       if ((curr = key[pos = pos + 1 & this.mask]) == 0L) return defaultValue; 
/*  411 */       if (k == curr) return this.value[pos];
/*      */     
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   public long putIfAbsent(long k, long v) {
/*  418 */     int pos = find(k);
/*  419 */     if (pos >= 0) return this.value[pos]; 
/*  420 */     insert(-pos - 1, k, v);
/*  421 */     return this.defRetValue;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean remove(long k, long v) {
/*  428 */     if (k == 0L) {
/*  429 */       if (this.containsNullKey && v == this.value[this.n]) {
/*  430 */         removeNullEntry();
/*  431 */         return true;
/*      */       } 
/*  433 */       return false;
/*      */     } 
/*      */     
/*  436 */     long[] key = this.key;
/*      */     long curr;
/*      */     int pos;
/*  439 */     if ((curr = key[pos = (int)HashCommon.mix(k) & this.mask]) == 0L) return false; 
/*  440 */     if (k == curr && v == this.value[pos]) {
/*  441 */       removeEntry(pos);
/*  442 */       return true;
/*      */     } 
/*      */     while (true) {
/*  445 */       if ((curr = key[pos = pos + 1 & this.mask]) == 0L) return false; 
/*  446 */       if (k == curr && v == this.value[pos]) {
/*  447 */         removeEntry(pos);
/*  448 */         return true;
/*      */       } 
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean replace(long k, long oldValue, long v) {
/*  456 */     int pos = find(k);
/*  457 */     if (pos < 0 || oldValue != this.value[pos]) return false; 
/*  458 */     this.value[pos] = v;
/*  459 */     return true;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public long replace(long k, long v) {
/*  465 */     int pos = find(k);
/*  466 */     if (pos < 0) return this.defRetValue; 
/*  467 */     long oldValue = this.value[pos];
/*  468 */     this.value[pos] = v;
/*  469 */     return oldValue;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public long computeIfAbsent(long k, LongUnaryOperator mappingFunction) {
/*  475 */     Objects.requireNonNull(mappingFunction);
/*  476 */     int pos = find(k);
/*  477 */     if (pos >= 0) return this.value[pos]; 
/*  478 */     long newValue = mappingFunction.applyAsLong(k);
/*  479 */     insert(-pos - 1, k, newValue);
/*  480 */     return newValue;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public long computeIfAbsent(long key, Long2LongFunction mappingFunction) {
/*  486 */     Objects.requireNonNull(mappingFunction);
/*  487 */     int pos = find(key);
/*  488 */     if (pos >= 0) return this.value[pos]; 
/*  489 */     if (!mappingFunction.containsKey(key)) return this.defRetValue; 
/*  490 */     long newValue = mappingFunction.get(key);
/*  491 */     insert(-pos - 1, key, newValue);
/*  492 */     return newValue;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public long computeIfAbsentNullable(long k, LongFunction<? extends Long> mappingFunction) {
/*  498 */     Objects.requireNonNull(mappingFunction);
/*  499 */     int pos = find(k);
/*  500 */     if (pos >= 0) return this.value[pos]; 
/*  501 */     Long newValue = mappingFunction.apply(k);
/*  502 */     if (newValue == null) return this.defRetValue; 
/*  503 */     long v = newValue.longValue();
/*  504 */     insert(-pos - 1, k, v);
/*  505 */     return v;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public long computeIfPresent(long k, BiFunction<? super Long, ? super Long, ? extends Long> remappingFunction) {
/*  511 */     Objects.requireNonNull(remappingFunction);
/*  512 */     int pos = find(k);
/*  513 */     if (pos < 0) return this.defRetValue; 
/*  514 */     Long newValue = remappingFunction.apply(Long.valueOf(k), Long.valueOf(this.value[pos]));
/*  515 */     if (newValue == null) {
/*  516 */       if (k == 0L) { removeNullEntry(); }
/*  517 */       else { removeEntry(pos); }
/*  518 */        return this.defRetValue;
/*      */     } 
/*  520 */     this.value[pos] = newValue.longValue(); return newValue.longValue();
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public long compute(long k, BiFunction<? super Long, ? super Long, ? extends Long> remappingFunction) {
/*  526 */     Objects.requireNonNull(remappingFunction);
/*  527 */     int pos = find(k);
/*  528 */     Long newValue = remappingFunction.apply(Long.valueOf(k), (pos >= 0) ? Long.valueOf(this.value[pos]) : null);
/*  529 */     if (newValue == null) {
/*  530 */       if (pos >= 0)
/*  531 */         if (k == 0L) { removeNullEntry(); }
/*  532 */         else { removeEntry(pos); }
/*      */          
/*  534 */       return this.defRetValue;
/*      */     } 
/*  536 */     long newVal = newValue.longValue();
/*  537 */     if (pos < 0) {
/*  538 */       insert(-pos - 1, k, newVal);
/*  539 */       return newVal;
/*      */     } 
/*  541 */     this.value[pos] = newVal; return newVal;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public long merge(long k, long v, BiFunction<? super Long, ? super Long, ? extends Long> remappingFunction) {
/*  547 */     Objects.requireNonNull(remappingFunction);
/*      */     
/*  549 */     int pos = find(k);
/*  550 */     if (pos < 0) {
/*  551 */       if (pos < 0) { insert(-pos - 1, k, v); }
/*  552 */       else { this.value[pos] = v; }
/*  553 */        return v;
/*      */     } 
/*  555 */     Long newValue = remappingFunction.apply(Long.valueOf(this.value[pos]), Long.valueOf(v));
/*  556 */     if (newValue == null) {
/*  557 */       if (k == 0L) { removeNullEntry(); }
/*  558 */       else { removeEntry(pos); }
/*  559 */        return this.defRetValue;
/*      */     } 
/*  561 */     this.value[pos] = newValue.longValue(); return newValue.longValue();
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
/*  572 */     if (this.size == 0)
/*  573 */       return;  this.size = 0;
/*  574 */     this.containsNullKey = false;
/*  575 */     Arrays.fill(this.key, 0L);
/*      */   }
/*      */ 
/*      */   
/*      */   public int size() {
/*  580 */     return this.size;
/*      */   }
/*      */ 
/*      */   
/*      */   public boolean isEmpty() {
/*  585 */     return (this.size == 0);
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
/*  598 */       this.index = index;
/*      */     }
/*      */ 
/*      */     
/*      */     MapEntry() {}
/*      */ 
/*      */     
/*      */     public long getLongKey() {
/*  606 */       return Long2LongOpenHashMap.this.key[this.index];
/*      */     }
/*      */ 
/*      */     
/*      */     public long leftLong() {
/*  611 */       return Long2LongOpenHashMap.this.key[this.index];
/*      */     }
/*      */ 
/*      */     
/*      */     public long getLongValue() {
/*  616 */       return Long2LongOpenHashMap.this.value[this.index];
/*      */     }
/*      */ 
/*      */     
/*      */     public long rightLong() {
/*  621 */       return Long2LongOpenHashMap.this.value[this.index];
/*      */     }
/*      */ 
/*      */     
/*      */     public long setValue(long v) {
/*  626 */       long oldValue = Long2LongOpenHashMap.this.value[this.index];
/*  627 */       Long2LongOpenHashMap.this.value[this.index] = v;
/*  628 */       return oldValue;
/*      */     }
/*      */ 
/*      */     
/*      */     public LongLongPair right(long v) {
/*  633 */       Long2LongOpenHashMap.this.value[this.index] = v;
/*  634 */       return this;
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
/*  645 */       return Long.valueOf(Long2LongOpenHashMap.this.key[this.index]);
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
/*  656 */       return Long.valueOf(Long2LongOpenHashMap.this.value[this.index]);
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
/*  667 */       return Long.valueOf(setValue(v.longValue()));
/*      */     }
/*      */ 
/*      */ 
/*      */     
/*      */     public boolean equals(Object o) {
/*  673 */       if (!(o instanceof Map.Entry)) return false; 
/*  674 */       Map.Entry<Long, Long> e = (Map.Entry<Long, Long>)o;
/*  675 */       return (Long2LongOpenHashMap.this.key[this.index] == ((Long)e.getKey()).longValue() && Long2LongOpenHashMap.this.value[this.index] == ((Long)e.getValue()).longValue());
/*      */     }
/*      */ 
/*      */     
/*      */     public int hashCode() {
/*  680 */       return HashCommon.long2int(Long2LongOpenHashMap.this.key[this.index]) ^ HashCommon.long2int(Long2LongOpenHashMap.this.value[this.index]);
/*      */     }
/*      */ 
/*      */     
/*      */     public String toString() {
/*  685 */       return Long2LongOpenHashMap.this.key[this.index] + "=>" + Long2LongOpenHashMap.this.value[this.index];
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
/*  696 */     int pos = Long2LongOpenHashMap.this.n;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  702 */     int last = -1;
/*      */     
/*  704 */     int c = Long2LongOpenHashMap.this.size;
/*      */     
/*  706 */     boolean mustReturnNullKey = Long2LongOpenHashMap.this.containsNullKey;
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
/*  717 */       return (this.c != 0);
/*      */     }
/*      */     
/*      */     public int nextEntry() {
/*  721 */       if (!hasNext()) throw new NoSuchElementException(); 
/*  722 */       this.c--;
/*  723 */       if (this.mustReturnNullKey) {
/*  724 */         this.mustReturnNullKey = false;
/*  725 */         return this.last = Long2LongOpenHashMap.this.n;
/*      */       } 
/*  727 */       long[] key = Long2LongOpenHashMap.this.key;
/*      */       while (true) {
/*  729 */         if (--this.pos < 0) {
/*      */           
/*  731 */           this.last = Integer.MIN_VALUE;
/*  732 */           long k = this.wrapped.getLong(-this.pos - 1);
/*  733 */           int p = (int)HashCommon.mix(k) & Long2LongOpenHashMap.this.mask;
/*  734 */           for (; k != key[p]; p = p + 1 & Long2LongOpenHashMap.this.mask);
/*  735 */           return p;
/*      */         } 
/*  737 */         if (key[this.pos] != 0L) return this.last = this.pos; 
/*      */       } 
/*      */     }
/*      */     
/*      */     public void forEachRemaining(ConsumerType action) {
/*  742 */       if (this.mustReturnNullKey) {
/*  743 */         this.mustReturnNullKey = false;
/*  744 */         acceptOnIndex(action, this.last = Long2LongOpenHashMap.this.n);
/*  745 */         this.c--;
/*      */       } 
/*  747 */       long[] key = Long2LongOpenHashMap.this.key;
/*  748 */       while (this.c != 0) {
/*  749 */         if (--this.pos < 0) {
/*      */           
/*  751 */           this.last = Integer.MIN_VALUE;
/*  752 */           long k = this.wrapped.getLong(-this.pos - 1);
/*  753 */           int p = (int)HashCommon.mix(k) & Long2LongOpenHashMap.this.mask;
/*  754 */           for (; k != key[p]; p = p + 1 & Long2LongOpenHashMap.this.mask);
/*  755 */           acceptOnIndex(action, p);
/*  756 */           this.c--; continue;
/*  757 */         }  if (key[this.pos] != 0L) {
/*  758 */           acceptOnIndex(action, this.last = this.pos);
/*  759 */           this.c--;
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
/*  774 */       long[] key = Long2LongOpenHashMap.this.key; while (true) {
/*      */         long curr; int last;
/*  776 */         pos = (last = pos) + 1 & Long2LongOpenHashMap.this.mask;
/*      */         while (true) {
/*  778 */           if ((curr = key[pos]) == 0L) {
/*  779 */             key[last] = 0L;
/*      */             return;
/*      */           } 
/*  782 */           int slot = (int)HashCommon.mix(curr) & Long2LongOpenHashMap.this.mask;
/*  783 */           if ((last <= pos) ? (last >= slot || slot > pos) : (last >= slot && slot > pos))
/*  784 */             break;  pos = pos + 1 & Long2LongOpenHashMap.this.mask;
/*      */         } 
/*  786 */         if (pos < last) {
/*  787 */           if (this.wrapped == null) this.wrapped = new LongArrayList(2); 
/*  788 */           this.wrapped.add(key[pos]);
/*      */         } 
/*  790 */         key[last] = curr;
/*  791 */         Long2LongOpenHashMap.this.value[last] = Long2LongOpenHashMap.this.value[pos];
/*      */       } 
/*      */     }
/*      */     
/*      */     public void remove() {
/*  796 */       if (this.last == -1) throw new IllegalStateException(); 
/*  797 */       if (this.last == Long2LongOpenHashMap.this.n)
/*  798 */       { Long2LongOpenHashMap.this.containsNullKey = false; }
/*  799 */       else if (this.pos >= 0) { shiftKeys(this.last); }
/*      */       else
/*      */       
/*  802 */       { Long2LongOpenHashMap.this.remove(this.wrapped.getLong(-this.pos - 1));
/*  803 */         this.last = -1;
/*      */         return; }
/*      */       
/*  806 */       Long2LongOpenHashMap.this.size--;
/*  807 */       this.last = -1;
/*      */     }
/*      */ 
/*      */     
/*      */     public int skip(int n) {
/*  812 */       int i = n;
/*  813 */       for (; i-- != 0 && hasNext(); nextEntry());
/*  814 */       return n - i - 1;
/*      */     }
/*      */     
/*      */     private MapIterator() {}
/*      */     
/*      */     abstract void acceptOnIndex(ConsumerType param1ConsumerType, int param1Int);
/*      */   }
/*      */   
/*      */   private final class EntryIterator extends MapIterator<Consumer<? super Long2LongMap.Entry>> implements ObjectIterator<Long2LongMap.Entry> { public Long2LongOpenHashMap.MapEntry next() {
/*  823 */       return this.entry = new Long2LongOpenHashMap.MapEntry(nextEntry());
/*      */     }
/*      */     private Long2LongOpenHashMap.MapEntry entry;
/*      */     private EntryIterator() {}
/*      */     
/*      */     final void acceptOnIndex(Consumer<? super Long2LongMap.Entry> action, int index) {
/*  829 */       action.accept(this.entry = new Long2LongOpenHashMap.MapEntry(index));
/*      */     }
/*      */ 
/*      */     
/*      */     public void remove() {
/*  834 */       super.remove();
/*  835 */       this.entry.index = -1;
/*      */     } }
/*      */   
/*      */   private final class FastEntryIterator extends MapIterator<Consumer<? super Long2LongMap.Entry>> implements ObjectIterator<Long2LongMap.Entry> {
/*      */     private FastEntryIterator() {
/*  840 */       this.entry = new Long2LongOpenHashMap.MapEntry();
/*      */     }
/*      */     private final Long2LongOpenHashMap.MapEntry entry;
/*      */     public Long2LongOpenHashMap.MapEntry next() {
/*  844 */       this.entry.index = nextEntry();
/*  845 */       return this.entry;
/*      */     }
/*      */ 
/*      */ 
/*      */     
/*      */     final void acceptOnIndex(Consumer<? super Long2LongMap.Entry> action, int index) {
/*  851 */       this.entry.index = index;
/*  852 */       action.accept(this.entry);
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private abstract class MapSpliterator<ConsumerType, SplitType extends MapSpliterator<ConsumerType, SplitType>>
/*      */   {
/*  861 */     int pos = 0;
/*      */     
/*  863 */     int max = Long2LongOpenHashMap.this.n;
/*      */     
/*  865 */     int c = 0;
/*      */     
/*  867 */     boolean mustReturnNull = Long2LongOpenHashMap.this.containsNullKey;
/*      */     
/*      */     boolean hasSplit = false;
/*      */     
/*      */     MapSpliterator() {}
/*      */     
/*      */     MapSpliterator(int pos, int max, boolean mustReturnNull, boolean hasSplit) {
/*  874 */       this.pos = pos;
/*  875 */       this.max = max;
/*  876 */       this.mustReturnNull = mustReturnNull;
/*  877 */       this.hasSplit = hasSplit;
/*      */     }
/*      */     
/*      */     abstract void acceptOnIndex(ConsumerType param1ConsumerType, int param1Int);
/*      */     
/*      */     abstract SplitType makeForSplit(int param1Int1, int param1Int2, boolean param1Boolean);
/*      */     
/*      */     public boolean tryAdvance(ConsumerType action) {
/*  885 */       if (this.mustReturnNull) {
/*  886 */         this.mustReturnNull = false;
/*  887 */         this.c++;
/*  888 */         acceptOnIndex(action, Long2LongOpenHashMap.this.n);
/*  889 */         return true;
/*      */       } 
/*  891 */       long[] key = Long2LongOpenHashMap.this.key;
/*  892 */       while (this.pos < this.max) {
/*  893 */         if (key[this.pos] != 0L) {
/*  894 */           this.c++;
/*  895 */           acceptOnIndex(action, this.pos++);
/*  896 */           return true;
/*      */         } 
/*  898 */         this.pos++;
/*      */       } 
/*  900 */       return false;
/*      */     }
/*      */     
/*      */     public void forEachRemaining(ConsumerType action) {
/*  904 */       if (this.mustReturnNull) {
/*  905 */         this.mustReturnNull = false;
/*  906 */         this.c++;
/*  907 */         acceptOnIndex(action, Long2LongOpenHashMap.this.n);
/*      */       } 
/*  909 */       long[] key = Long2LongOpenHashMap.this.key;
/*  910 */       while (this.pos < this.max) {
/*  911 */         if (key[this.pos] != 0L) {
/*  912 */           acceptOnIndex(action, this.pos);
/*  913 */           this.c++;
/*      */         } 
/*  915 */         this.pos++;
/*      */       } 
/*      */     }
/*      */     
/*      */     public long estimateSize() {
/*  920 */       if (!this.hasSplit)
/*      */       {
/*  922 */         return (Long2LongOpenHashMap.this.size - this.c);
/*      */       }
/*      */ 
/*      */ 
/*      */       
/*  927 */       return Math.min((Long2LongOpenHashMap.this.size - this.c), (long)(Long2LongOpenHashMap.this.realSize() / Long2LongOpenHashMap.this.n * (this.max - this.pos)) + (this.mustReturnNull ? 1L : 0L));
/*      */     }
/*      */ 
/*      */     
/*      */     public SplitType trySplit() {
/*  932 */       if (this.pos >= this.max - 1) return null; 
/*  933 */       int retLen = this.max - this.pos >> 1;
/*  934 */       if (retLen <= 1) return null; 
/*  935 */       int myNewPos = this.pos + retLen;
/*  936 */       int retPos = this.pos;
/*  937 */       int retMax = myNewPos;
/*      */ 
/*      */ 
/*      */       
/*  941 */       SplitType split = makeForSplit(retPos, retMax, this.mustReturnNull);
/*  942 */       this.pos = myNewPos;
/*  943 */       this.mustReturnNull = false;
/*  944 */       this.hasSplit = true;
/*  945 */       return split;
/*      */     }
/*      */     
/*      */     public long skip(long n) {
/*  949 */       if (n < 0L) throw new IllegalArgumentException("Argument must be nonnegative: " + n); 
/*  950 */       if (n == 0L) return 0L; 
/*  951 */       long skipped = 0L;
/*  952 */       if (this.mustReturnNull) {
/*  953 */         this.mustReturnNull = false;
/*  954 */         skipped++;
/*  955 */         n--;
/*      */       } 
/*  957 */       long[] key = Long2LongOpenHashMap.this.key;
/*  958 */       while (this.pos < this.max && n > 0L) {
/*  959 */         if (key[this.pos++] != 0L) {
/*  960 */           skipped++;
/*  961 */           n--;
/*      */         } 
/*      */       } 
/*  964 */       return skipped;
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
/*  975 */       super(pos, max, mustReturnNull, hasSplit);
/*      */     }
/*      */ 
/*      */     
/*      */     public int characteristics() {
/*  980 */       return this.hasSplit ? 1 : 65;
/*      */     }
/*      */ 
/*      */     
/*      */     final void acceptOnIndex(Consumer<? super Long2LongMap.Entry> action, int index) {
/*  985 */       action.accept(new Long2LongOpenHashMap.MapEntry(index));
/*      */     }
/*      */ 
/*      */     
/*      */     final EntrySpliterator makeForSplit(int pos, int max, boolean mustReturnNull) {
/*  990 */       return new EntrySpliterator(pos, max, mustReturnNull, true);
/*      */     } }
/*      */   
/*      */   private final class MapEntrySet extends AbstractObjectSet<Long2LongMap.Entry> implements Long2LongMap.FastEntrySet {
/*      */     private MapEntrySet() {}
/*      */     
/*      */     public ObjectIterator<Long2LongMap.Entry> iterator() {
/*  997 */       return new Long2LongOpenHashMap.EntryIterator();
/*      */     }
/*      */ 
/*      */     
/*      */     public ObjectIterator<Long2LongMap.Entry> fastIterator() {
/* 1002 */       return new Long2LongOpenHashMap.FastEntryIterator();
/*      */     }
/*      */ 
/*      */     
/*      */     public ObjectSpliterator<Long2LongMap.Entry> spliterator() {
/* 1007 */       return new Long2LongOpenHashMap.EntrySpliterator();
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public boolean contains(Object o) {
/* 1014 */       if (!(o instanceof Map.Entry)) return false; 
/* 1015 */       Map.Entry<?, ?> e = (Map.Entry<?, ?>)o;
/* 1016 */       if (e.getKey() == null || !(e.getKey() instanceof Long)) return false; 
/* 1017 */       if (e.getValue() == null || !(e.getValue() instanceof Long)) return false; 
/* 1018 */       long k = ((Long)e.getKey()).longValue();
/* 1019 */       long v = ((Long)e.getValue()).longValue();
/* 1020 */       if (k == 0L) return (Long2LongOpenHashMap.this.containsNullKey && Long2LongOpenHashMap.this.value[Long2LongOpenHashMap.this.n] == v);
/*      */       
/* 1022 */       long[] key = Long2LongOpenHashMap.this.key;
/*      */       long curr;
/*      */       int pos;
/* 1025 */       if ((curr = key[pos = (int)HashCommon.mix(k) & Long2LongOpenHashMap.this.mask]) == 0L) return false; 
/* 1026 */       if (k == curr) return (Long2LongOpenHashMap.this.value[pos] == v);
/*      */       
/*      */       while (true) {
/* 1029 */         if ((curr = key[pos = pos + 1 & Long2LongOpenHashMap.this.mask]) == 0L) return false; 
/* 1030 */         if (k == curr) return (Long2LongOpenHashMap.this.value[pos] == v);
/*      */       
/*      */       } 
/*      */     }
/*      */ 
/*      */     
/*      */     public boolean remove(Object o) {
/* 1037 */       if (!(o instanceof Map.Entry)) return false; 
/* 1038 */       Map.Entry<?, ?> e = (Map.Entry<?, ?>)o;
/* 1039 */       if (e.getKey() == null || !(e.getKey() instanceof Long)) return false; 
/* 1040 */       if (e.getValue() == null || !(e.getValue() instanceof Long)) return false; 
/* 1041 */       long k = ((Long)e.getKey()).longValue();
/* 1042 */       long v = ((Long)e.getValue()).longValue();
/* 1043 */       if (k == 0L) {
/* 1044 */         if (Long2LongOpenHashMap.this.containsNullKey && Long2LongOpenHashMap.this.value[Long2LongOpenHashMap.this.n] == v) {
/* 1045 */           Long2LongOpenHashMap.this.removeNullEntry();
/* 1046 */           return true;
/*      */         } 
/* 1048 */         return false;
/*      */       } 
/*      */       
/* 1051 */       long[] key = Long2LongOpenHashMap.this.key;
/*      */       long curr;
/*      */       int pos;
/* 1054 */       if ((curr = key[pos = (int)HashCommon.mix(k) & Long2LongOpenHashMap.this.mask]) == 0L) return false; 
/* 1055 */       if (curr == k) {
/* 1056 */         if (Long2LongOpenHashMap.this.value[pos] == v) {
/* 1057 */           Long2LongOpenHashMap.this.removeEntry(pos);
/* 1058 */           return true;
/*      */         } 
/* 1060 */         return false;
/*      */       } 
/*      */       while (true) {
/* 1063 */         if ((curr = key[pos = pos + 1 & Long2LongOpenHashMap.this.mask]) == 0L) return false; 
/* 1064 */         if (curr == k && 
/* 1065 */           Long2LongOpenHashMap.this.value[pos] == v) {
/* 1066 */           Long2LongOpenHashMap.this.removeEntry(pos);
/* 1067 */           return true;
/*      */         } 
/*      */       } 
/*      */     }
/*      */ 
/*      */ 
/*      */     
/*      */     public int size() {
/* 1075 */       return Long2LongOpenHashMap.this.size;
/*      */     }
/*      */ 
/*      */     
/*      */     public void clear() {
/* 1080 */       Long2LongOpenHashMap.this.clear();
/*      */     }
/*      */ 
/*      */ 
/*      */     
/*      */     public void forEach(Consumer<? super Long2LongMap.Entry> consumer) {
/* 1086 */       if (Long2LongOpenHashMap.this.containsNullKey) consumer.accept(new Long2LongOpenHashMap.MapEntry(Long2LongOpenHashMap.this.n)); 
/* 1087 */       for (int pos = Long2LongOpenHashMap.this.n; pos-- != 0;) { if (Long2LongOpenHashMap.this.key[pos] != 0L) consumer.accept(new Long2LongOpenHashMap.MapEntry(pos));
/*      */          }
/*      */     
/*      */     }
/*      */     
/*      */     public void fastForEach(Consumer<? super Long2LongMap.Entry> consumer) {
/* 1093 */       Long2LongOpenHashMap.MapEntry entry = new Long2LongOpenHashMap.MapEntry();
/* 1094 */       if (Long2LongOpenHashMap.this.containsNullKey) {
/* 1095 */         entry.index = Long2LongOpenHashMap.this.n;
/* 1096 */         consumer.accept(entry);
/*      */       } 
/* 1098 */       for (int pos = Long2LongOpenHashMap.this.n; pos-- != 0;) { if (Long2LongOpenHashMap.this.key[pos] != 0L) {
/* 1099 */           entry.index = pos;
/* 1100 */           consumer.accept(entry);
/*      */         }  }
/*      */     
/*      */     }
/*      */   }
/*      */   
/*      */   public Long2LongMap.FastEntrySet long2LongEntrySet() {
/* 1107 */     if (this.entries == null) this.entries = new MapEntrySet(); 
/* 1108 */     return this.entries;
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
/* 1129 */       action.accept(Long2LongOpenHashMap.this.key[index]);
/*      */     }
/*      */ 
/*      */     
/*      */     public long nextLong() {
/* 1134 */       return Long2LongOpenHashMap.this.key[nextEntry()];
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
/* 1145 */       super(pos, max, mustReturnNull, hasSplit);
/*      */     }
/*      */ 
/*      */     
/*      */     public int characteristics() {
/* 1150 */       return this.hasSplit ? 257 : 321;
/*      */     }
/*      */ 
/*      */     
/*      */     final void acceptOnIndex(LongConsumer action, int index) {
/* 1155 */       action.accept(Long2LongOpenHashMap.this.key[index]);
/*      */     }
/*      */ 
/*      */     
/*      */     final KeySpliterator makeForSplit(int pos, int max, boolean mustReturnNull) {
/* 1160 */       return new KeySpliterator(pos, max, mustReturnNull, true);
/*      */     } }
/*      */   
/*      */   private final class KeySet extends AbstractLongSet {
/*      */     private KeySet() {}
/*      */     
/*      */     public LongIterator iterator() {
/* 1167 */       return new Long2LongOpenHashMap.KeyIterator();
/*      */     }
/*      */ 
/*      */     
/*      */     public LongSpliterator spliterator() {
/* 1172 */       return new Long2LongOpenHashMap.KeySpliterator();
/*      */     }
/*      */ 
/*      */ 
/*      */     
/*      */     public void forEach(LongConsumer consumer) {
/* 1178 */       if (Long2LongOpenHashMap.this.containsNullKey) consumer.accept(Long2LongOpenHashMap.this.key[Long2LongOpenHashMap.this.n]); 
/* 1179 */       for (int pos = Long2LongOpenHashMap.this.n; pos-- != 0; ) {
/* 1180 */         long k = Long2LongOpenHashMap.this.key[pos];
/* 1181 */         if (k != 0L) consumer.accept(k);
/*      */       
/*      */       } 
/*      */     }
/*      */     
/*      */     public int size() {
/* 1187 */       return Long2LongOpenHashMap.this.size;
/*      */     }
/*      */ 
/*      */     
/*      */     public boolean contains(long k) {
/* 1192 */       return Long2LongOpenHashMap.this.containsKey(k);
/*      */     }
/*      */ 
/*      */     
/*      */     public boolean remove(long k) {
/* 1197 */       int oldSize = Long2LongOpenHashMap.this.size;
/* 1198 */       Long2LongOpenHashMap.this.remove(k);
/* 1199 */       return (Long2LongOpenHashMap.this.size != oldSize);
/*      */     }
/*      */ 
/*      */     
/*      */     public void clear() {
/* 1204 */       Long2LongOpenHashMap.this.clear();
/*      */     }
/*      */   }
/*      */ 
/*      */   
/*      */   public LongSet keySet() {
/* 1210 */     if (this.keys == null) this.keys = new KeySet(); 
/* 1211 */     return this.keys;
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
/* 1232 */       action.accept(Long2LongOpenHashMap.this.value[index]);
/*      */     }
/*      */ 
/*      */     
/*      */     public long nextLong() {
/* 1237 */       return Long2LongOpenHashMap.this.value[nextEntry()];
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
/* 1248 */       super(pos, max, mustReturnNull, hasSplit);
/*      */     }
/*      */ 
/*      */     
/*      */     public int characteristics() {
/* 1253 */       return this.hasSplit ? 256 : 320;
/*      */     }
/*      */ 
/*      */     
/*      */     final void acceptOnIndex(LongConsumer action, int index) {
/* 1258 */       action.accept(Long2LongOpenHashMap.this.value[index]);
/*      */     }
/*      */ 
/*      */     
/*      */     final ValueSpliterator makeForSplit(int pos, int max, boolean mustReturnNull) {
/* 1263 */       return new ValueSpliterator(pos, max, mustReturnNull, true);
/*      */     }
/*      */   }
/*      */ 
/*      */   
/*      */   public LongCollection values() {
/* 1269 */     if (this.values == null) this.values = new AbstractLongCollection()
/*      */         {
/*      */           public LongIterator iterator() {
/* 1272 */             return new Long2LongOpenHashMap.ValueIterator();
/*      */           }
/*      */ 
/*      */           
/*      */           public LongSpliterator spliterator() {
/* 1277 */             return new Long2LongOpenHashMap.ValueSpliterator();
/*      */           }
/*      */ 
/*      */ 
/*      */           
/*      */           public void forEach(LongConsumer consumer) {
/* 1283 */             if (Long2LongOpenHashMap.this.containsNullKey) consumer.accept(Long2LongOpenHashMap.this.value[Long2LongOpenHashMap.this.n]); 
/* 1284 */             for (int pos = Long2LongOpenHashMap.this.n; pos-- != 0;) { if (Long2LongOpenHashMap.this.key[pos] != 0L) consumer.accept(Long2LongOpenHashMap.this.value[pos]);  }
/*      */           
/*      */           }
/*      */           
/*      */           public int size() {
/* 1289 */             return Long2LongOpenHashMap.this.size;
/*      */           }
/*      */ 
/*      */           
/*      */           public boolean contains(long v) {
/* 1294 */             return Long2LongOpenHashMap.this.containsValue(v);
/*      */           }
/*      */ 
/*      */           
/*      */           public void clear() {
/* 1299 */             Long2LongOpenHashMap.this.clear();
/*      */           }
/*      */         }; 
/* 1302 */     return this.values;
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
/* 1319 */     return trim(this.size);
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
/* 1341 */     int l = HashCommon.nextPowerOfTwo((int)Math.ceil((n / this.f)));
/* 1342 */     if (l >= this.n || this.size > HashCommon.maxFill(l, this.f)) return true; 
/*      */     try {
/* 1344 */       rehash(l);
/* 1345 */     } catch (OutOfMemoryError cantDoIt) {
/* 1346 */       return false;
/*      */     } 
/* 1348 */     return true;
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
/* 1363 */     long[] key = this.key;
/* 1364 */     long[] value = this.value;
/* 1365 */     int mask = newN - 1;
/* 1366 */     long[] newKey = new long[newN + 1];
/* 1367 */     long[] newValue = new long[newN + 1];
/* 1368 */     int i = this.n;
/* 1369 */     for (int j = realSize(); j-- != 0; ) {
/* 1370 */       while (key[--i] == 0L); int pos;
/* 1371 */       if (newKey[pos = (int)HashCommon.mix(key[i]) & mask] != 0L) while (newKey[pos = pos + 1 & mask] != 0L); 
/* 1372 */       newKey[pos] = key[i];
/* 1373 */       newValue[pos] = value[i];
/*      */     } 
/* 1375 */     newValue[newN] = value[this.n];
/* 1376 */     this.n = newN;
/* 1377 */     this.mask = mask;
/* 1378 */     this.maxFill = HashCommon.maxFill(this.n, this.f);
/* 1379 */     this.key = newKey;
/* 1380 */     this.value = newValue;
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
/*      */   public Long2LongOpenHashMap clone() {
/*      */     Long2LongOpenHashMap c;
/*      */     try {
/* 1397 */       c = (Long2LongOpenHashMap)super.clone();
/* 1398 */     } catch (CloneNotSupportedException cantHappen) {
/* 1399 */       throw new InternalError();
/*      */     } 
/* 1401 */     c.keys = null;
/* 1402 */     c.values = null;
/* 1403 */     c.entries = null;
/* 1404 */     c.containsNullKey = this.containsNullKey;
/* 1405 */     c.key = (long[])this.key.clone();
/* 1406 */     c.value = (long[])this.value.clone();
/* 1407 */     return c;
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
/* 1421 */     int h = 0;
/* 1422 */     for (int j = realSize(), i = 0, t = 0; j-- != 0; ) {
/* 1423 */       for (; this.key[i] == 0L; i++);
/* 1424 */       t = HashCommon.long2int(this.key[i]);
/* 1425 */       t ^= HashCommon.long2int(this.value[i]);
/* 1426 */       h += t;
/* 1427 */       i++;
/*      */     } 
/*      */     
/* 1430 */     if (this.containsNullKey) h += HashCommon.long2int(this.value[this.n]); 
/* 1431 */     return h;
/*      */   }
/*      */   
/*      */   private void writeObject(ObjectOutputStream s) throws IOException {
/* 1435 */     long[] key = this.key;
/* 1436 */     long[] value = this.value;
/* 1437 */     EntryIterator i = new EntryIterator();
/* 1438 */     s.defaultWriteObject();
/* 1439 */     for (int j = this.size; j-- != 0; ) {
/* 1440 */       int e = i.nextEntry();
/* 1441 */       s.writeLong(key[e]);
/* 1442 */       s.writeLong(value[e]);
/*      */     } 
/*      */   }
/*      */   
/*      */   private void readObject(ObjectInputStream s) throws IOException, ClassNotFoundException {
/* 1447 */     s.defaultReadObject();
/* 1448 */     this.n = HashCommon.arraySize(this.size, this.f);
/* 1449 */     this.maxFill = HashCommon.maxFill(this.n, this.f);
/* 1450 */     this.mask = this.n - 1;
/* 1451 */     long[] key = this.key = new long[this.n + 1];
/* 1452 */     long[] value = this.value = new long[this.n + 1];
/*      */ 
/*      */     
/* 1455 */     for (int i = this.size; i-- != 0; ) {
/* 1456 */       int pos; long k = s.readLong();
/* 1457 */       long v = s.readLong();
/* 1458 */       if (k == 0L) {
/* 1459 */         pos = this.n;
/* 1460 */         this.containsNullKey = true;
/*      */       } else {
/* 1462 */         pos = (int)HashCommon.mix(k) & this.mask;
/* 1463 */         for (; key[pos] != 0L; pos = pos + 1 & this.mask);
/*      */       } 
/* 1465 */       key[pos] = k;
/* 1466 */       value[pos] = v;
/*      */     } 
/*      */   }
/*      */   
/*      */   private void checkTable() {}
/*      */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\i\\unimi\dsi\fastutil\longs\Long2LongOpenHashMap.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */