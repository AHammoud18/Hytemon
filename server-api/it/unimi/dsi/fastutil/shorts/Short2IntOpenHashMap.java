/*      */ package it.unimi.dsi.fastutil.shorts;
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
/*      */ public class Short2IntOpenHashMap
/*      */   extends AbstractShort2IntMap
/*      */   implements Serializable, Cloneable, Hash
/*      */ {
/*      */   private static final long serialVersionUID = 0L;
/*      */   private static final boolean ASSERTS = false;
/*      */   protected transient short[] key;
/*      */   protected transient int[] value;
/*      */   protected transient int mask;
/*      */   protected transient boolean containsNullKey;
/*      */   protected transient int n;
/*      */   protected transient int maxFill;
/*      */   protected final transient int minN;
/*      */   protected int size;
/*      */   protected final float f;
/*      */   protected transient Short2IntMap.FastEntrySet entries;
/*      */   protected transient ShortSet keys;
/*      */   protected transient IntCollection values;
/*      */   
/*      */   public Short2IntOpenHashMap(int expected, float f) {
/*   99 */     if (f <= 0.0F || f >= 1.0F) throw new IllegalArgumentException("Load factor must be greater than 0 and smaller than 1"); 
/*  100 */     if (expected < 0) throw new IllegalArgumentException("The expected number of elements must be nonnegative"); 
/*  101 */     this.f = f;
/*  102 */     this.minN = this.n = HashCommon.arraySize(expected, f);
/*  103 */     this.mask = this.n - 1;
/*  104 */     this.maxFill = HashCommon.maxFill(this.n, f);
/*  105 */     this.key = new short[this.n + 1];
/*  106 */     this.value = new int[this.n + 1];
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Short2IntOpenHashMap(int expected) {
/*  115 */     this(expected, 0.75F);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Short2IntOpenHashMap() {
/*  123 */     this(16, 0.75F);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Short2IntOpenHashMap(Map<? extends Short, ? extends Integer> m, float f) {
/*  133 */     this(m.size(), f);
/*  134 */     putAll(m);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Short2IntOpenHashMap(Map<? extends Short, ? extends Integer> m) {
/*  143 */     this(m, 0.75F);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Short2IntOpenHashMap(Short2IntMap m, float f) {
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
/*      */   public Short2IntOpenHashMap(Short2IntMap m) {
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
/*      */   public Short2IntOpenHashMap(short[] k, int[] v, float f) {
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
/*      */   public Short2IntOpenHashMap(short[] k, int[] v) {
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
/*      */   private int removeEntry(int pos) {
/*  214 */     int oldValue = this.value[pos];
/*  215 */     this.size--;
/*  216 */     shiftKeys(pos);
/*  217 */     if (this.n > this.minN && this.size < this.maxFill / 4 && this.n > 16) rehash(this.n / 2); 
/*  218 */     return oldValue;
/*      */   }
/*      */   
/*      */   private int removeNullEntry() {
/*  222 */     this.containsNullKey = false;
/*  223 */     int oldValue = this.value[this.n];
/*  224 */     this.size--;
/*  225 */     if (this.n > this.minN && this.size < this.maxFill / 4 && this.n > 16) rehash(this.n / 2); 
/*  226 */     return oldValue;
/*      */   }
/*      */ 
/*      */   
/*      */   public void putAll(Map<? extends Short, ? extends Integer> m) {
/*  231 */     if (this.f <= 0.5D) { ensureCapacity(m.size()); }
/*  232 */     else { tryCapacity((size() + m.size())); }
/*      */     
/*  234 */     super.putAll(m);
/*      */   }
/*      */   
/*      */   private int find(short k) {
/*  238 */     if (k == 0) return this.containsNullKey ? this.n : -(this.n + 1);
/*      */     
/*  240 */     short[] key = this.key;
/*      */     short curr;
/*      */     int pos;
/*  243 */     if ((curr = key[pos = HashCommon.mix(k) & this.mask]) == 0) return -(pos + 1); 
/*  244 */     if (k == curr) return pos;
/*      */     
/*      */     while (true) {
/*  247 */       if ((curr = key[pos = pos + 1 & this.mask]) == 0) return -(pos + 1); 
/*  248 */       if (k == curr) return pos; 
/*      */     } 
/*      */   }
/*      */   
/*      */   private void insert(int pos, short k, int v) {
/*  253 */     if (pos == this.n) this.containsNullKey = true; 
/*  254 */     this.key[pos] = k;
/*  255 */     this.value[pos] = v;
/*  256 */     if (this.size++ >= this.maxFill) rehash(HashCommon.arraySize(this.size + 1, this.f));
/*      */   
/*      */   }
/*      */ 
/*      */   
/*      */   public int put(short k, int v) {
/*  262 */     int pos = find(k);
/*  263 */     if (pos < 0) {
/*  264 */       insert(-pos - 1, k, v);
/*  265 */       return this.defRetValue;
/*      */     } 
/*  267 */     int oldValue = this.value[pos];
/*  268 */     this.value[pos] = v;
/*  269 */     return oldValue;
/*      */   }
/*      */   
/*      */   private int addToValue(int pos, int incr) {
/*  273 */     int oldValue = this.value[pos];
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
/*      */   public int addTo(short k, int incr) {
/*      */     int pos;
/*  293 */     if (k == 0) {
/*  294 */       if (this.containsNullKey) return addToValue(this.n, incr); 
/*  295 */       pos = this.n;
/*  296 */       this.containsNullKey = true;
/*      */     } else {
/*      */       
/*  299 */       short[] key = this.key;
/*      */       short curr;
/*  301 */       if ((curr = key[pos = HashCommon.mix(k) & this.mask]) != 0) {
/*  302 */         if (curr == k) return addToValue(pos, incr); 
/*  303 */         while ((curr = key[pos = pos + 1 & this.mask]) != 0) { if (curr == k) return addToValue(pos, incr);  }
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
/*  323 */     short[] key = this.key; while (true) {
/*      */       short curr; int last;
/*  325 */       pos = (last = pos) + 1 & this.mask;
/*      */       while (true) {
/*  327 */         if ((curr = key[pos]) == 0) {
/*  328 */           key[last] = 0;
/*      */           return;
/*      */         } 
/*  331 */         int slot = HashCommon.mix(curr) & this.mask;
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
/*      */   public int remove(short k) {
/*  343 */     if (k == 0) {
/*  344 */       if (this.containsNullKey) return removeNullEntry(); 
/*  345 */       return this.defRetValue;
/*      */     } 
/*      */     
/*  348 */     short[] key = this.key;
/*      */     short curr;
/*      */     int pos;
/*  351 */     if ((curr = key[pos = HashCommon.mix(k) & this.mask]) == 0) return this.defRetValue; 
/*  352 */     if (k == curr) return removeEntry(pos); 
/*      */     while (true) {
/*  354 */       if ((curr = key[pos = pos + 1 & this.mask]) == 0) return this.defRetValue; 
/*  355 */       if (k == curr) return removeEntry(pos);
/*      */     
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   public int get(short k) {
/*  362 */     if (k == 0) return this.containsNullKey ? this.value[this.n] : this.defRetValue;
/*      */     
/*  364 */     short[] key = this.key;
/*      */     short curr;
/*      */     int pos;
/*  367 */     if ((curr = key[pos = HashCommon.mix(k) & this.mask]) == 0) return this.defRetValue; 
/*  368 */     if (k == curr) return this.value[pos];
/*      */     
/*      */     while (true) {
/*  371 */       if ((curr = key[pos = pos + 1 & this.mask]) == 0) return this.defRetValue; 
/*  372 */       if (k == curr) return this.value[pos];
/*      */     
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   public boolean containsKey(short k) {
/*  379 */     if (k == 0) return this.containsNullKey;
/*      */     
/*  381 */     short[] key = this.key;
/*      */     short curr;
/*      */     int pos;
/*  384 */     if ((curr = key[pos = HashCommon.mix(k) & this.mask]) == 0) return false; 
/*  385 */     if (k == curr) return true;
/*      */     
/*      */     while (true) {
/*  388 */       if ((curr = key[pos = pos + 1 & this.mask]) == 0) return false; 
/*  389 */       if (k == curr) return true;
/*      */     
/*      */     } 
/*      */   }
/*      */   
/*      */   public boolean containsValue(int v) {
/*  395 */     int[] value = this.value;
/*  396 */     short[] key = this.key;
/*  397 */     if (this.containsNullKey && value[this.n] == v) return true; 
/*  398 */     for (int i = this.n; i-- != 0;) { if (key[i] != 0 && value[i] == v) return true;  }
/*  399 */      return false;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public int getOrDefault(short k, int defaultValue) {
/*  406 */     if (k == 0) return this.containsNullKey ? this.value[this.n] : defaultValue;
/*      */     
/*  408 */     short[] key = this.key;
/*      */     short curr;
/*      */     int pos;
/*  411 */     if ((curr = key[pos = HashCommon.mix(k) & this.mask]) == 0) return defaultValue; 
/*  412 */     if (k == curr) return this.value[pos];
/*      */     
/*      */     while (true) {
/*  415 */       if ((curr = key[pos = pos + 1 & this.mask]) == 0) return defaultValue; 
/*  416 */       if (k == curr) return this.value[pos];
/*      */     
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   public int putIfAbsent(short k, int v) {
/*  423 */     int pos = find(k);
/*  424 */     if (pos >= 0) return this.value[pos]; 
/*  425 */     insert(-pos - 1, k, v);
/*  426 */     return this.defRetValue;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean remove(short k, int v) {
/*  433 */     if (k == 0) {
/*  434 */       if (this.containsNullKey && v == this.value[this.n]) {
/*  435 */         removeNullEntry();
/*  436 */         return true;
/*      */       } 
/*  438 */       return false;
/*      */     } 
/*      */     
/*  441 */     short[] key = this.key;
/*      */     short curr;
/*      */     int pos;
/*  444 */     if ((curr = key[pos = HashCommon.mix(k) & this.mask]) == 0) return false; 
/*  445 */     if (k == curr && v == this.value[pos]) {
/*  446 */       removeEntry(pos);
/*  447 */       return true;
/*      */     } 
/*      */     while (true) {
/*  450 */       if ((curr = key[pos = pos + 1 & this.mask]) == 0) return false; 
/*  451 */       if (k == curr && v == this.value[pos]) {
/*  452 */         removeEntry(pos);
/*  453 */         return true;
/*      */       } 
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean replace(short k, int oldValue, int v) {
/*  461 */     int pos = find(k);
/*  462 */     if (pos < 0 || oldValue != this.value[pos]) return false; 
/*  463 */     this.value[pos] = v;
/*  464 */     return true;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public int replace(short k, int v) {
/*  470 */     int pos = find(k);
/*  471 */     if (pos < 0) return this.defRetValue; 
/*  472 */     int oldValue = this.value[pos];
/*  473 */     this.value[pos] = v;
/*  474 */     return oldValue;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public int computeIfAbsent(short k, IntUnaryOperator mappingFunction) {
/*  480 */     Objects.requireNonNull(mappingFunction);
/*  481 */     int pos = find(k);
/*  482 */     if (pos >= 0) return this.value[pos]; 
/*  483 */     int newValue = mappingFunction.applyAsInt(k);
/*  484 */     insert(-pos - 1, k, newValue);
/*  485 */     return newValue;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public int computeIfAbsent(short key, Short2IntFunction mappingFunction) {
/*  491 */     Objects.requireNonNull(mappingFunction);
/*  492 */     int pos = find(key);
/*  493 */     if (pos >= 0) return this.value[pos]; 
/*  494 */     if (!mappingFunction.containsKey(key)) return this.defRetValue; 
/*  495 */     int newValue = mappingFunction.get(key);
/*  496 */     insert(-pos - 1, key, newValue);
/*  497 */     return newValue;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public int computeIfAbsentNullable(short k, IntFunction<? extends Integer> mappingFunction) {
/*  503 */     Objects.requireNonNull(mappingFunction);
/*  504 */     int pos = find(k);
/*  505 */     if (pos >= 0) return this.value[pos]; 
/*  506 */     Integer newValue = mappingFunction.apply(k);
/*  507 */     if (newValue == null) return this.defRetValue; 
/*  508 */     int v = newValue.intValue();
/*  509 */     insert(-pos - 1, k, v);
/*  510 */     return v;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public int computeIfPresent(short k, BiFunction<? super Short, ? super Integer, ? extends Integer> remappingFunction) {
/*  516 */     Objects.requireNonNull(remappingFunction);
/*  517 */     int pos = find(k);
/*  518 */     if (pos < 0) return this.defRetValue; 
/*  519 */     Integer newValue = remappingFunction.apply(Short.valueOf(k), Integer.valueOf(this.value[pos]));
/*  520 */     if (newValue == null) {
/*  521 */       if (k == 0) { removeNullEntry(); }
/*  522 */       else { removeEntry(pos); }
/*  523 */        return this.defRetValue;
/*      */     } 
/*  525 */     this.value[pos] = newValue.intValue(); return newValue.intValue();
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public int compute(short k, BiFunction<? super Short, ? super Integer, ? extends Integer> remappingFunction) {
/*  531 */     Objects.requireNonNull(remappingFunction);
/*  532 */     int pos = find(k);
/*  533 */     Integer newValue = remappingFunction.apply(Short.valueOf(k), (pos >= 0) ? Integer.valueOf(this.value[pos]) : null);
/*  534 */     if (newValue == null) {
/*  535 */       if (pos >= 0)
/*  536 */         if (k == 0) { removeNullEntry(); }
/*  537 */         else { removeEntry(pos); }
/*      */          
/*  539 */       return this.defRetValue;
/*      */     } 
/*  541 */     int newVal = newValue.intValue();
/*  542 */     if (pos < 0) {
/*  543 */       insert(-pos - 1, k, newVal);
/*  544 */       return newVal;
/*      */     } 
/*  546 */     this.value[pos] = newVal; return newVal;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public int merge(short k, int v, BiFunction<? super Integer, ? super Integer, ? extends Integer> remappingFunction) {
/*  552 */     Objects.requireNonNull(remappingFunction);
/*      */     
/*  554 */     int pos = find(k);
/*  555 */     if (pos < 0) {
/*  556 */       if (pos < 0) { insert(-pos - 1, k, v); }
/*  557 */       else { this.value[pos] = v; }
/*  558 */        return v;
/*      */     } 
/*  560 */     Integer newValue = remappingFunction.apply(Integer.valueOf(this.value[pos]), Integer.valueOf(v));
/*  561 */     if (newValue == null) {
/*  562 */       if (k == 0) { removeNullEntry(); }
/*  563 */       else { removeEntry(pos); }
/*  564 */        return this.defRetValue;
/*      */     } 
/*  566 */     this.value[pos] = newValue.intValue(); return newValue.intValue();
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
/*  580 */     Arrays.fill(this.key, (short)0);
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
/*      */     implements Short2IntMap.Entry, Map.Entry<Short, Integer>, ShortIntPair
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
/*      */     public short getShortKey() {
/*  611 */       return Short2IntOpenHashMap.this.key[this.index];
/*      */     }
/*      */ 
/*      */     
/*      */     public short leftShort() {
/*  616 */       return Short2IntOpenHashMap.this.key[this.index];
/*      */     }
/*      */ 
/*      */     
/*      */     public int getIntValue() {
/*  621 */       return Short2IntOpenHashMap.this.value[this.index];
/*      */     }
/*      */ 
/*      */     
/*      */     public int rightInt() {
/*  626 */       return Short2IntOpenHashMap.this.value[this.index];
/*      */     }
/*      */ 
/*      */     
/*      */     public int setValue(int v) {
/*  631 */       int oldValue = Short2IntOpenHashMap.this.value[this.index];
/*  632 */       Short2IntOpenHashMap.this.value[this.index] = v;
/*  633 */       return oldValue;
/*      */     }
/*      */ 
/*      */     
/*      */     public ShortIntPair right(int v) {
/*  638 */       Short2IntOpenHashMap.this.value[this.index] = v;
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
/*      */     public Short getKey() {
/*  650 */       return Short.valueOf(Short2IntOpenHashMap.this.key[this.index]);
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
/*  661 */       return Integer.valueOf(Short2IntOpenHashMap.this.value[this.index]);
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
/*  672 */       return Integer.valueOf(setValue(v.intValue()));
/*      */     }
/*      */ 
/*      */ 
/*      */     
/*      */     public boolean equals(Object o) {
/*  678 */       if (!(o instanceof Map.Entry)) return false; 
/*  679 */       Map.Entry<Short, Integer> e = (Map.Entry<Short, Integer>)o;
/*  680 */       return (Short2IntOpenHashMap.this.key[this.index] == ((Short)e.getKey()).shortValue() && Short2IntOpenHashMap.this.value[this.index] == ((Integer)e.getValue()).intValue());
/*      */     }
/*      */ 
/*      */     
/*      */     public int hashCode() {
/*  685 */       return Short2IntOpenHashMap.this.key[this.index] ^ Short2IntOpenHashMap.this.value[this.index];
/*      */     }
/*      */ 
/*      */     
/*      */     public String toString() {
/*  690 */       return Short2IntOpenHashMap.this.key[this.index] + "=>" + Short2IntOpenHashMap.this.value[this.index];
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
/*  701 */     int pos = Short2IntOpenHashMap.this.n;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  707 */     int last = -1;
/*      */     
/*  709 */     int c = Short2IntOpenHashMap.this.size;
/*      */     
/*  711 */     boolean mustReturnNullKey = Short2IntOpenHashMap.this.containsNullKey;
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
/*  722 */       return (this.c != 0);
/*      */     }
/*      */     
/*      */     public int nextEntry() {
/*  726 */       if (!hasNext()) throw new NoSuchElementException(); 
/*  727 */       this.c--;
/*  728 */       if (this.mustReturnNullKey) {
/*  729 */         this.mustReturnNullKey = false;
/*  730 */         return this.last = Short2IntOpenHashMap.this.n;
/*      */       } 
/*  732 */       short[] key = Short2IntOpenHashMap.this.key;
/*      */       while (true) {
/*  734 */         if (--this.pos < 0) {
/*      */           
/*  736 */           this.last = Integer.MIN_VALUE;
/*  737 */           short k = this.wrapped.getShort(-this.pos - 1);
/*  738 */           int p = HashCommon.mix(k) & Short2IntOpenHashMap.this.mask;
/*  739 */           for (; k != key[p]; p = p + 1 & Short2IntOpenHashMap.this.mask);
/*  740 */           return p;
/*      */         } 
/*  742 */         if (key[this.pos] != 0) return this.last = this.pos; 
/*      */       } 
/*      */     }
/*      */     
/*      */     public void forEachRemaining(ConsumerType action) {
/*  747 */       if (this.mustReturnNullKey) {
/*  748 */         this.mustReturnNullKey = false;
/*  749 */         acceptOnIndex(action, this.last = Short2IntOpenHashMap.this.n);
/*  750 */         this.c--;
/*      */       } 
/*  752 */       short[] key = Short2IntOpenHashMap.this.key;
/*  753 */       while (this.c != 0) {
/*  754 */         if (--this.pos < 0) {
/*      */           
/*  756 */           this.last = Integer.MIN_VALUE;
/*  757 */           short k = this.wrapped.getShort(-this.pos - 1);
/*  758 */           int p = HashCommon.mix(k) & Short2IntOpenHashMap.this.mask;
/*  759 */           for (; k != key[p]; p = p + 1 & Short2IntOpenHashMap.this.mask);
/*  760 */           acceptOnIndex(action, p);
/*  761 */           this.c--; continue;
/*  762 */         }  if (key[this.pos] != 0) {
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
/*  779 */       short[] key = Short2IntOpenHashMap.this.key; while (true) {
/*      */         short curr; int last;
/*  781 */         pos = (last = pos) + 1 & Short2IntOpenHashMap.this.mask;
/*      */         while (true) {
/*  783 */           if ((curr = key[pos]) == 0) {
/*  784 */             key[last] = 0;
/*      */             return;
/*      */           } 
/*  787 */           int slot = HashCommon.mix(curr) & Short2IntOpenHashMap.this.mask;
/*  788 */           if ((last <= pos) ? (last >= slot || slot > pos) : (last >= slot && slot > pos))
/*  789 */             break;  pos = pos + 1 & Short2IntOpenHashMap.this.mask;
/*      */         } 
/*  791 */         if (pos < last) {
/*  792 */           if (this.wrapped == null) this.wrapped = new ShortArrayList(2); 
/*  793 */           this.wrapped.add(key[pos]);
/*      */         } 
/*  795 */         key[last] = curr;
/*  796 */         Short2IntOpenHashMap.this.value[last] = Short2IntOpenHashMap.this.value[pos];
/*      */       } 
/*      */     }
/*      */     
/*      */     public void remove() {
/*  801 */       if (this.last == -1) throw new IllegalStateException(); 
/*  802 */       if (this.last == Short2IntOpenHashMap.this.n)
/*  803 */       { Short2IntOpenHashMap.this.containsNullKey = false; }
/*  804 */       else if (this.pos >= 0) { shiftKeys(this.last); }
/*      */       else
/*      */       
/*  807 */       { Short2IntOpenHashMap.this.remove(this.wrapped.getShort(-this.pos - 1));
/*  808 */         this.last = -1;
/*      */         return; }
/*      */       
/*  811 */       Short2IntOpenHashMap.this.size--;
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
/*      */   private final class EntryIterator extends MapIterator<Consumer<? super Short2IntMap.Entry>> implements ObjectIterator<Short2IntMap.Entry> { public Short2IntOpenHashMap.MapEntry next() {
/*  828 */       return this.entry = new Short2IntOpenHashMap.MapEntry(nextEntry());
/*      */     }
/*      */     private Short2IntOpenHashMap.MapEntry entry;
/*      */     private EntryIterator() {}
/*      */     
/*      */     final void acceptOnIndex(Consumer<? super Short2IntMap.Entry> action, int index) {
/*  834 */       action.accept(this.entry = new Short2IntOpenHashMap.MapEntry(index));
/*      */     }
/*      */ 
/*      */     
/*      */     public void remove() {
/*  839 */       super.remove();
/*  840 */       this.entry.index = -1;
/*      */     } }
/*      */   
/*      */   private final class FastEntryIterator extends MapIterator<Consumer<? super Short2IntMap.Entry>> implements ObjectIterator<Short2IntMap.Entry> {
/*      */     private FastEntryIterator() {
/*  845 */       this.entry = new Short2IntOpenHashMap.MapEntry();
/*      */     }
/*      */     private final Short2IntOpenHashMap.MapEntry entry;
/*      */     public Short2IntOpenHashMap.MapEntry next() {
/*  849 */       this.entry.index = nextEntry();
/*  850 */       return this.entry;
/*      */     }
/*      */ 
/*      */ 
/*      */     
/*      */     final void acceptOnIndex(Consumer<? super Short2IntMap.Entry> action, int index) {
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
/*  868 */     int max = Short2IntOpenHashMap.this.n;
/*      */     
/*  870 */     int c = 0;
/*      */     
/*  872 */     boolean mustReturnNull = Short2IntOpenHashMap.this.containsNullKey;
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
/*  893 */         acceptOnIndex(action, Short2IntOpenHashMap.this.n);
/*  894 */         return true;
/*      */       } 
/*  896 */       short[] key = Short2IntOpenHashMap.this.key;
/*  897 */       while (this.pos < this.max) {
/*  898 */         if (key[this.pos] != 0) {
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
/*  912 */         acceptOnIndex(action, Short2IntOpenHashMap.this.n);
/*      */       } 
/*  914 */       short[] key = Short2IntOpenHashMap.this.key;
/*  915 */       while (this.pos < this.max) {
/*  916 */         if (key[this.pos] != 0) {
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
/*  927 */         return (Short2IntOpenHashMap.this.size - this.c);
/*      */       }
/*      */ 
/*      */ 
/*      */       
/*  932 */       return Math.min((Short2IntOpenHashMap.this.size - this.c), (long)(Short2IntOpenHashMap.this.realSize() / Short2IntOpenHashMap.this.n * (this.max - this.pos)) + (this.mustReturnNull ? 1L : 0L));
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
/*  962 */       short[] key = Short2IntOpenHashMap.this.key;
/*  963 */       while (this.pos < this.max && n > 0L) {
/*  964 */         if (key[this.pos++] != 0) {
/*  965 */           skipped++;
/*  966 */           n--;
/*      */         } 
/*      */       } 
/*  969 */       return skipped;
/*      */     }
/*      */   }
/*      */   
/*      */   private final class EntrySpliterator
/*      */     extends MapSpliterator<Consumer<? super Short2IntMap.Entry>, EntrySpliterator> implements ObjectSpliterator<Short2IntMap.Entry> {
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
/*      */     final void acceptOnIndex(Consumer<? super Short2IntMap.Entry> action, int index) {
/*  990 */       action.accept(new Short2IntOpenHashMap.MapEntry(index));
/*      */     }
/*      */ 
/*      */     
/*      */     final EntrySpliterator makeForSplit(int pos, int max, boolean mustReturnNull) {
/*  995 */       return new EntrySpliterator(pos, max, mustReturnNull, true);
/*      */     } }
/*      */   
/*      */   private final class MapEntrySet extends AbstractObjectSet<Short2IntMap.Entry> implements Short2IntMap.FastEntrySet {
/*      */     private MapEntrySet() {}
/*      */     
/*      */     public ObjectIterator<Short2IntMap.Entry> iterator() {
/* 1002 */       return new Short2IntOpenHashMap.EntryIterator();
/*      */     }
/*      */ 
/*      */     
/*      */     public ObjectIterator<Short2IntMap.Entry> fastIterator() {
/* 1007 */       return new Short2IntOpenHashMap.FastEntryIterator();
/*      */     }
/*      */ 
/*      */     
/*      */     public ObjectSpliterator<Short2IntMap.Entry> spliterator() {
/* 1012 */       return new Short2IntOpenHashMap.EntrySpliterator();
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public boolean contains(Object o) {
/* 1019 */       if (!(o instanceof Map.Entry)) return false; 
/* 1020 */       Map.Entry<?, ?> e = (Map.Entry<?, ?>)o;
/* 1021 */       if (e.getKey() == null || !(e.getKey() instanceof Short)) return false; 
/* 1022 */       if (e.getValue() == null || !(e.getValue() instanceof Integer)) return false; 
/* 1023 */       short k = ((Short)e.getKey()).shortValue();
/* 1024 */       int v = ((Integer)e.getValue()).intValue();
/* 1025 */       if (k == 0) return (Short2IntOpenHashMap.this.containsNullKey && Short2IntOpenHashMap.this.value[Short2IntOpenHashMap.this.n] == v);
/*      */       
/* 1027 */       short[] key = Short2IntOpenHashMap.this.key;
/*      */       short curr;
/*      */       int pos;
/* 1030 */       if ((curr = key[pos = HashCommon.mix(k) & Short2IntOpenHashMap.this.mask]) == 0) return false; 
/* 1031 */       if (k == curr) return (Short2IntOpenHashMap.this.value[pos] == v);
/*      */       
/*      */       while (true) {
/* 1034 */         if ((curr = key[pos = pos + 1 & Short2IntOpenHashMap.this.mask]) == 0) return false; 
/* 1035 */         if (k == curr) return (Short2IntOpenHashMap.this.value[pos] == v);
/*      */       
/*      */       } 
/*      */     }
/*      */ 
/*      */     
/*      */     public boolean remove(Object o) {
/* 1042 */       if (!(o instanceof Map.Entry)) return false; 
/* 1043 */       Map.Entry<?, ?> e = (Map.Entry<?, ?>)o;
/* 1044 */       if (e.getKey() == null || !(e.getKey() instanceof Short)) return false; 
/* 1045 */       if (e.getValue() == null || !(e.getValue() instanceof Integer)) return false; 
/* 1046 */       short k = ((Short)e.getKey()).shortValue();
/* 1047 */       int v = ((Integer)e.getValue()).intValue();
/* 1048 */       if (k == 0) {
/* 1049 */         if (Short2IntOpenHashMap.this.containsNullKey && Short2IntOpenHashMap.this.value[Short2IntOpenHashMap.this.n] == v) {
/* 1050 */           Short2IntOpenHashMap.this.removeNullEntry();
/* 1051 */           return true;
/*      */         } 
/* 1053 */         return false;
/*      */       } 
/*      */       
/* 1056 */       short[] key = Short2IntOpenHashMap.this.key;
/*      */       short curr;
/*      */       int pos;
/* 1059 */       if ((curr = key[pos = HashCommon.mix(k) & Short2IntOpenHashMap.this.mask]) == 0) return false; 
/* 1060 */       if (curr == k) {
/* 1061 */         if (Short2IntOpenHashMap.this.value[pos] == v) {
/* 1062 */           Short2IntOpenHashMap.this.removeEntry(pos);
/* 1063 */           return true;
/*      */         } 
/* 1065 */         return false;
/*      */       } 
/*      */       while (true) {
/* 1068 */         if ((curr = key[pos = pos + 1 & Short2IntOpenHashMap.this.mask]) == 0) return false; 
/* 1069 */         if (curr == k && 
/* 1070 */           Short2IntOpenHashMap.this.value[pos] == v) {
/* 1071 */           Short2IntOpenHashMap.this.removeEntry(pos);
/* 1072 */           return true;
/*      */         } 
/*      */       } 
/*      */     }
/*      */ 
/*      */ 
/*      */     
/*      */     public int size() {
/* 1080 */       return Short2IntOpenHashMap.this.size;
/*      */     }
/*      */ 
/*      */     
/*      */     public void clear() {
/* 1085 */       Short2IntOpenHashMap.this.clear();
/*      */     }
/*      */ 
/*      */ 
/*      */     
/*      */     public void forEach(Consumer<? super Short2IntMap.Entry> consumer) {
/* 1091 */       if (Short2IntOpenHashMap.this.containsNullKey) consumer.accept(new Short2IntOpenHashMap.MapEntry(Short2IntOpenHashMap.this.n)); 
/* 1092 */       for (int pos = Short2IntOpenHashMap.this.n; pos-- != 0;) { if (Short2IntOpenHashMap.this.key[pos] != 0) consumer.accept(new Short2IntOpenHashMap.MapEntry(pos));
/*      */          }
/*      */     
/*      */     }
/*      */     
/*      */     public void fastForEach(Consumer<? super Short2IntMap.Entry> consumer) {
/* 1098 */       Short2IntOpenHashMap.MapEntry entry = new Short2IntOpenHashMap.MapEntry();
/* 1099 */       if (Short2IntOpenHashMap.this.containsNullKey) {
/* 1100 */         entry.index = Short2IntOpenHashMap.this.n;
/* 1101 */         consumer.accept(entry);
/*      */       } 
/* 1103 */       for (int pos = Short2IntOpenHashMap.this.n; pos-- != 0;) { if (Short2IntOpenHashMap.this.key[pos] != 0) {
/* 1104 */           entry.index = pos;
/* 1105 */           consumer.accept(entry);
/*      */         }  }
/*      */     
/*      */     }
/*      */   }
/*      */   
/*      */   public Short2IntMap.FastEntrySet short2IntEntrySet() {
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
/*      */     extends MapIterator<ShortConsumer>
/*      */     implements ShortIterator
/*      */   {
/*      */     final void acceptOnIndex(ShortConsumer action, int index) {
/* 1134 */       action.accept(Short2IntOpenHashMap.this.key[index]);
/*      */     }
/*      */ 
/*      */     
/*      */     public short nextShort() {
/* 1139 */       return Short2IntOpenHashMap.this.key[nextEntry()];
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
/* 1150 */       super(pos, max, mustReturnNull, hasSplit);
/*      */     }
/*      */ 
/*      */     
/*      */     public int characteristics() {
/* 1155 */       return this.hasSplit ? 257 : 321;
/*      */     }
/*      */ 
/*      */     
/*      */     final void acceptOnIndex(ShortConsumer action, int index) {
/* 1160 */       action.accept(Short2IntOpenHashMap.this.key[index]);
/*      */     }
/*      */ 
/*      */     
/*      */     final KeySpliterator makeForSplit(int pos, int max, boolean mustReturnNull) {
/* 1165 */       return new KeySpliterator(pos, max, mustReturnNull, true);
/*      */     } }
/*      */   
/*      */   private final class KeySet extends AbstractShortSet {
/*      */     private KeySet() {}
/*      */     
/*      */     public ShortIterator iterator() {
/* 1172 */       return new Short2IntOpenHashMap.KeyIterator();
/*      */     }
/*      */ 
/*      */     
/*      */     public ShortSpliterator spliterator() {
/* 1177 */       return new Short2IntOpenHashMap.KeySpliterator();
/*      */     }
/*      */ 
/*      */ 
/*      */     
/*      */     public void forEach(ShortConsumer consumer) {
/* 1183 */       if (Short2IntOpenHashMap.this.containsNullKey) consumer.accept(Short2IntOpenHashMap.this.key[Short2IntOpenHashMap.this.n]); 
/* 1184 */       for (int pos = Short2IntOpenHashMap.this.n; pos-- != 0; ) {
/* 1185 */         short k = Short2IntOpenHashMap.this.key[pos];
/* 1186 */         if (k != 0) consumer.accept(k);
/*      */       
/*      */       } 
/*      */     }
/*      */     
/*      */     public int size() {
/* 1192 */       return Short2IntOpenHashMap.this.size;
/*      */     }
/*      */ 
/*      */     
/*      */     public boolean contains(short k) {
/* 1197 */       return Short2IntOpenHashMap.this.containsKey(k);
/*      */     }
/*      */ 
/*      */     
/*      */     public boolean remove(short k) {
/* 1202 */       int oldSize = Short2IntOpenHashMap.this.size;
/* 1203 */       Short2IntOpenHashMap.this.remove(k);
/* 1204 */       return (Short2IntOpenHashMap.this.size != oldSize);
/*      */     }
/*      */ 
/*      */     
/*      */     public void clear() {
/* 1209 */       Short2IntOpenHashMap.this.clear();
/*      */     }
/*      */   }
/*      */ 
/*      */   
/*      */   public ShortSet keySet() {
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
/*      */     extends MapIterator<IntConsumer>
/*      */     implements IntIterator
/*      */   {
/*      */     final void acceptOnIndex(IntConsumer action, int index) {
/* 1237 */       action.accept(Short2IntOpenHashMap.this.value[index]);
/*      */     }
/*      */ 
/*      */     
/*      */     public int nextInt() {
/* 1242 */       return Short2IntOpenHashMap.this.value[nextEntry()];
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
/* 1253 */       super(pos, max, mustReturnNull, hasSplit);
/*      */     }
/*      */ 
/*      */     
/*      */     public int characteristics() {
/* 1258 */       return this.hasSplit ? 256 : 320;
/*      */     }
/*      */ 
/*      */     
/*      */     final void acceptOnIndex(IntConsumer action, int index) {
/* 1263 */       action.accept(Short2IntOpenHashMap.this.value[index]);
/*      */     }
/*      */ 
/*      */     
/*      */     final ValueSpliterator makeForSplit(int pos, int max, boolean mustReturnNull) {
/* 1268 */       return new ValueSpliterator(pos, max, mustReturnNull, true);
/*      */     }
/*      */   }
/*      */ 
/*      */   
/*      */   public IntCollection values() {
/* 1274 */     if (this.values == null) this.values = (IntCollection)new AbstractIntCollection()
/*      */         {
/*      */           public IntIterator iterator() {
/* 1277 */             return new Short2IntOpenHashMap.ValueIterator();
/*      */           }
/*      */ 
/*      */           
/*      */           public IntSpliterator spliterator() {
/* 1282 */             return new Short2IntOpenHashMap.ValueSpliterator();
/*      */           }
/*      */ 
/*      */ 
/*      */           
/*      */           public void forEach(IntConsumer consumer) {
/* 1288 */             if (Short2IntOpenHashMap.this.containsNullKey) consumer.accept(Short2IntOpenHashMap.this.value[Short2IntOpenHashMap.this.n]); 
/* 1289 */             for (int pos = Short2IntOpenHashMap.this.n; pos-- != 0;) { if (Short2IntOpenHashMap.this.key[pos] != 0) consumer.accept(Short2IntOpenHashMap.this.value[pos]);  }
/*      */           
/*      */           }
/*      */           
/*      */           public int size() {
/* 1294 */             return Short2IntOpenHashMap.this.size;
/*      */           }
/*      */ 
/*      */           
/*      */           public boolean contains(int v) {
/* 1299 */             return Short2IntOpenHashMap.this.containsValue(v);
/*      */           }
/*      */ 
/*      */           
/*      */           public void clear() {
/* 1304 */             Short2IntOpenHashMap.this.clear();
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
/* 1368 */     short[] key = this.key;
/* 1369 */     int[] value = this.value;
/* 1370 */     int mask = newN - 1;
/* 1371 */     short[] newKey = new short[newN + 1];
/* 1372 */     int[] newValue = new int[newN + 1];
/* 1373 */     int i = this.n;
/* 1374 */     for (int j = realSize(); j-- != 0; ) {
/* 1375 */       while (key[--i] == 0); int pos;
/* 1376 */       if (newKey[pos = HashCommon.mix(key[i]) & mask] != 0) while (newKey[pos = pos + 1 & mask] != 0); 
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
/*      */   public Short2IntOpenHashMap clone() {
/*      */     Short2IntOpenHashMap c;
/*      */     try {
/* 1402 */       c = (Short2IntOpenHashMap)super.clone();
/* 1403 */     } catch (CloneNotSupportedException cantHappen) {
/* 1404 */       throw new InternalError();
/*      */     } 
/* 1406 */     c.keys = null;
/* 1407 */     c.values = null;
/* 1408 */     c.entries = null;
/* 1409 */     c.containsNullKey = this.containsNullKey;
/* 1410 */     c.key = (short[])this.key.clone();
/* 1411 */     c.value = (int[])this.value.clone();
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
/* 1428 */       for (; this.key[i] == 0; i++);
/* 1429 */       t = this.key[i];
/* 1430 */       t ^= this.value[i];
/* 1431 */       h += t;
/* 1432 */       i++;
/*      */     } 
/*      */     
/* 1435 */     if (this.containsNullKey) h += this.value[this.n]; 
/* 1436 */     return h;
/*      */   }
/*      */   
/*      */   private void writeObject(ObjectOutputStream s) throws IOException {
/* 1440 */     short[] key = this.key;
/* 1441 */     int[] value = this.value;
/* 1442 */     EntryIterator i = new EntryIterator();
/* 1443 */     s.defaultWriteObject();
/* 1444 */     for (int j = this.size; j-- != 0; ) {
/* 1445 */       int e = i.nextEntry();
/* 1446 */       s.writeShort(key[e]);
/* 1447 */       s.writeInt(value[e]);
/*      */     } 
/*      */   }
/*      */   
/*      */   private void readObject(ObjectInputStream s) throws IOException, ClassNotFoundException {
/* 1452 */     s.defaultReadObject();
/* 1453 */     this.n = HashCommon.arraySize(this.size, this.f);
/* 1454 */     this.maxFill = HashCommon.maxFill(this.n, this.f);
/* 1455 */     this.mask = this.n - 1;
/* 1456 */     short[] key = this.key = new short[this.n + 1];
/* 1457 */     int[] value = this.value = new int[this.n + 1];
/*      */ 
/*      */     
/* 1460 */     for (int i = this.size; i-- != 0; ) {
/* 1461 */       int pos; short k = s.readShort();
/* 1462 */       int v = s.readInt();
/* 1463 */       if (k == 0) {
/* 1464 */         pos = this.n;
/* 1465 */         this.containsNullKey = true;
/*      */       } else {
/* 1467 */         pos = HashCommon.mix(k) & this.mask;
/* 1468 */         for (; key[pos] != 0; pos = pos + 1 & this.mask);
/*      */       } 
/* 1470 */       key[pos] = k;
/* 1471 */       value[pos] = v;
/*      */     } 
/*      */   }
/*      */   
/*      */   private void checkTable() {}
/*      */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\i\\unimi\dsi\fastutil\shorts\Short2IntOpenHashMap.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */