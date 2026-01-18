/*      */ package it.unimi.dsi.fastutil.shorts;
/*      */ 
/*      */ import it.unimi.dsi.fastutil.Hash;
/*      */ import it.unimi.dsi.fastutil.HashCommon;
/*      */ import it.unimi.dsi.fastutil.booleans.AbstractBooleanCollection;
/*      */ import it.unimi.dsi.fastutil.booleans.BooleanCollection;
/*      */ import it.unimi.dsi.fastutil.booleans.BooleanConsumer;
/*      */ import it.unimi.dsi.fastutil.booleans.BooleanIterator;
/*      */ import it.unimi.dsi.fastutil.booleans.BooleanSpliterator;
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
/*      */ import java.util.function.IntPredicate;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ public class Short2BooleanOpenCustomHashMap
/*      */   extends AbstractShort2BooleanMap
/*      */   implements Serializable, Cloneable, Hash
/*      */ {
/*      */   private static final long serialVersionUID = 0L;
/*      */   private static final boolean ASSERTS = false;
/*      */   protected transient short[] key;
/*      */   protected transient boolean[] value;
/*      */   protected transient int mask;
/*      */   protected transient boolean containsNullKey;
/*      */   protected ShortHash.Strategy strategy;
/*      */   protected transient int n;
/*      */   protected transient int maxFill;
/*      */   protected final transient int minN;
/*      */   protected int size;
/*      */   protected final float f;
/*      */   protected transient Short2BooleanMap.FastEntrySet entries;
/*      */   protected transient ShortSet keys;
/*      */   protected transient BooleanCollection values;
/*      */   
/*      */   public Short2BooleanOpenCustomHashMap(int expected, float f, ShortHash.Strategy strategy) {
/*  104 */     this.strategy = strategy;
/*  105 */     if (f <= 0.0F || f >= 1.0F) throw new IllegalArgumentException("Load factor must be greater than 0 and smaller than 1"); 
/*  106 */     if (expected < 0) throw new IllegalArgumentException("The expected number of elements must be nonnegative"); 
/*  107 */     this.f = f;
/*  108 */     this.minN = this.n = HashCommon.arraySize(expected, f);
/*  109 */     this.mask = this.n - 1;
/*  110 */     this.maxFill = HashCommon.maxFill(this.n, f);
/*  111 */     this.key = new short[this.n + 1];
/*  112 */     this.value = new boolean[this.n + 1];
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Short2BooleanOpenCustomHashMap(int expected, ShortHash.Strategy strategy) {
/*  122 */     this(expected, 0.75F, strategy);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Short2BooleanOpenCustomHashMap(ShortHash.Strategy strategy) {
/*  132 */     this(16, 0.75F, strategy);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Short2BooleanOpenCustomHashMap(Map<? extends Short, ? extends Boolean> m, float f, ShortHash.Strategy strategy) {
/*  143 */     this(m.size(), f, strategy);
/*  144 */     putAll(m);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Short2BooleanOpenCustomHashMap(Map<? extends Short, ? extends Boolean> m, ShortHash.Strategy strategy) {
/*  154 */     this(m, 0.75F, strategy);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Short2BooleanOpenCustomHashMap(Short2BooleanMap m, float f, ShortHash.Strategy strategy) {
/*  165 */     this(m.size(), f, strategy);
/*  166 */     putAll(m);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Short2BooleanOpenCustomHashMap(Short2BooleanMap m, ShortHash.Strategy strategy) {
/*  177 */     this(m, 0.75F, strategy);
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
/*      */   public Short2BooleanOpenCustomHashMap(short[] k, boolean[] v, float f, ShortHash.Strategy strategy) {
/*  190 */     this(k.length, f, strategy);
/*  191 */     if (k.length != v.length) throw new IllegalArgumentException("The key array and the value array have different lengths (" + k.length + " and " + v.length + ")"); 
/*  192 */     for (int i = 0; i < k.length; ) { put(k[i], v[i]); i++; }
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
/*      */   public Short2BooleanOpenCustomHashMap(short[] k, boolean[] v, ShortHash.Strategy strategy) {
/*  205 */     this(k, v, 0.75F, strategy);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public ShortHash.Strategy strategy() {
/*  214 */     return this.strategy;
/*      */   }
/*      */   
/*      */   private int realSize() {
/*  218 */     return this.containsNullKey ? (this.size - 1) : this.size;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void ensureCapacity(int capacity) {
/*  228 */     int needed = HashCommon.arraySize(capacity, this.f);
/*  229 */     if (needed > this.n) rehash(needed); 
/*      */   }
/*      */   
/*      */   private void tryCapacity(long capacity) {
/*  233 */     int needed = (int)Math.min(1073741824L, Math.max(2L, HashCommon.nextPowerOfTwo((long)Math.ceil(((float)capacity / this.f)))));
/*  234 */     if (needed > this.n) rehash(needed); 
/*      */   }
/*      */   
/*      */   private boolean removeEntry(int pos) {
/*  238 */     boolean oldValue = this.value[pos];
/*  239 */     this.size--;
/*  240 */     shiftKeys(pos);
/*  241 */     if (this.n > this.minN && this.size < this.maxFill / 4 && this.n > 16) rehash(this.n / 2); 
/*  242 */     return oldValue;
/*      */   }
/*      */   
/*      */   private boolean removeNullEntry() {
/*  246 */     this.containsNullKey = false;
/*  247 */     boolean oldValue = this.value[this.n];
/*  248 */     this.size--;
/*  249 */     if (this.n > this.minN && this.size < this.maxFill / 4 && this.n > 16) rehash(this.n / 2); 
/*  250 */     return oldValue;
/*      */   }
/*      */ 
/*      */   
/*      */   public void putAll(Map<? extends Short, ? extends Boolean> m) {
/*  255 */     if (this.f <= 0.5D) { ensureCapacity(m.size()); }
/*  256 */     else { tryCapacity((size() + m.size())); }
/*      */     
/*  258 */     super.putAll(m);
/*      */   }
/*      */   
/*      */   private int find(short k) {
/*  262 */     if (this.strategy.equals(k, (short)0)) return this.containsNullKey ? this.n : -(this.n + 1);
/*      */     
/*  264 */     short[] key = this.key;
/*      */     short curr;
/*      */     int pos;
/*  267 */     if ((curr = key[pos = HashCommon.mix(this.strategy.hashCode(k)) & this.mask]) == 0) return -(pos + 1); 
/*  268 */     if (this.strategy.equals(k, curr)) return pos;
/*      */     
/*      */     while (true) {
/*  271 */       if ((curr = key[pos = pos + 1 & this.mask]) == 0) return -(pos + 1); 
/*  272 */       if (this.strategy.equals(k, curr)) return pos; 
/*      */     } 
/*      */   }
/*      */   
/*      */   private void insert(int pos, short k, boolean v) {
/*  277 */     if (pos == this.n) this.containsNullKey = true; 
/*  278 */     this.key[pos] = k;
/*  279 */     this.value[pos] = v;
/*  280 */     if (this.size++ >= this.maxFill) rehash(HashCommon.arraySize(this.size + 1, this.f));
/*      */   
/*      */   }
/*      */ 
/*      */   
/*      */   public boolean put(short k, boolean v) {
/*  286 */     int pos = find(k);
/*  287 */     if (pos < 0) {
/*  288 */       insert(-pos - 1, k, v);
/*  289 */       return this.defRetValue;
/*      */     } 
/*  291 */     boolean oldValue = this.value[pos];
/*  292 */     this.value[pos] = v;
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
/*      */   protected final void shiftKeys(int pos) {
/*  306 */     short[] key = this.key; while (true) {
/*      */       short curr; int last;
/*  308 */       pos = (last = pos) + 1 & this.mask;
/*      */       while (true) {
/*  310 */         if ((curr = key[pos]) == 0) {
/*  311 */           key[last] = 0;
/*      */           return;
/*      */         } 
/*  314 */         int slot = HashCommon.mix(this.strategy.hashCode(curr)) & this.mask;
/*  315 */         if ((last <= pos) ? (last >= slot || slot > pos) : (last >= slot && slot > pos))
/*  316 */           break;  pos = pos + 1 & this.mask;
/*      */       } 
/*  318 */       key[last] = curr;
/*  319 */       this.value[last] = this.value[pos];
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean remove(short k) {
/*  326 */     if (this.strategy.equals(k, (short)0)) {
/*  327 */       if (this.containsNullKey) return removeNullEntry(); 
/*  328 */       return this.defRetValue;
/*      */     } 
/*      */     
/*  331 */     short[] key = this.key;
/*      */     short curr;
/*      */     int pos;
/*  334 */     if ((curr = key[pos = HashCommon.mix(this.strategy.hashCode(k)) & this.mask]) == 0) return this.defRetValue; 
/*  335 */     if (this.strategy.equals(k, curr)) return removeEntry(pos); 
/*      */     while (true) {
/*  337 */       if ((curr = key[pos = pos + 1 & this.mask]) == 0) return this.defRetValue; 
/*  338 */       if (this.strategy.equals(k, curr)) return removeEntry(pos);
/*      */     
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   public boolean get(short k) {
/*  345 */     if (this.strategy.equals(k, (short)0)) return this.containsNullKey ? this.value[this.n] : this.defRetValue;
/*      */     
/*  347 */     short[] key = this.key;
/*      */     short curr;
/*      */     int pos;
/*  350 */     if ((curr = key[pos = HashCommon.mix(this.strategy.hashCode(k)) & this.mask]) == 0) return this.defRetValue; 
/*  351 */     if (this.strategy.equals(k, curr)) return this.value[pos];
/*      */     
/*      */     while (true) {
/*  354 */       if ((curr = key[pos = pos + 1 & this.mask]) == 0) return this.defRetValue; 
/*  355 */       if (this.strategy.equals(k, curr)) return this.value[pos];
/*      */     
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   public boolean containsKey(short k) {
/*  362 */     if (this.strategy.equals(k, (short)0)) return this.containsNullKey;
/*      */     
/*  364 */     short[] key = this.key;
/*      */     short curr;
/*      */     int pos;
/*  367 */     if ((curr = key[pos = HashCommon.mix(this.strategy.hashCode(k)) & this.mask]) == 0) return false; 
/*  368 */     if (this.strategy.equals(k, curr)) return true;
/*      */     
/*      */     while (true) {
/*  371 */       if ((curr = key[pos = pos + 1 & this.mask]) == 0) return false; 
/*  372 */       if (this.strategy.equals(k, curr)) return true;
/*      */     
/*      */     } 
/*      */   }
/*      */   
/*      */   public boolean containsValue(boolean v) {
/*  378 */     boolean[] value = this.value;
/*  379 */     short[] key = this.key;
/*  380 */     if (this.containsNullKey && value[this.n] == v) return true; 
/*  381 */     for (int i = this.n; i-- != 0;) { if (key[i] != 0 && value[i] == v) return true;  }
/*  382 */      return false;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean getOrDefault(short k, boolean defaultValue) {
/*  389 */     if (this.strategy.equals(k, (short)0)) return this.containsNullKey ? this.value[this.n] : defaultValue;
/*      */     
/*  391 */     short[] key = this.key;
/*      */     short curr;
/*      */     int pos;
/*  394 */     if ((curr = key[pos = HashCommon.mix(this.strategy.hashCode(k)) & this.mask]) == 0) return defaultValue; 
/*  395 */     if (this.strategy.equals(k, curr)) return this.value[pos];
/*      */     
/*      */     while (true) {
/*  398 */       if ((curr = key[pos = pos + 1 & this.mask]) == 0) return defaultValue; 
/*  399 */       if (this.strategy.equals(k, curr)) return this.value[pos];
/*      */     
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   public boolean putIfAbsent(short k, boolean v) {
/*  406 */     int pos = find(k);
/*  407 */     if (pos >= 0) return this.value[pos]; 
/*  408 */     insert(-pos - 1, k, v);
/*  409 */     return this.defRetValue;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean remove(short k, boolean v) {
/*  416 */     if (this.strategy.equals(k, (short)0)) {
/*  417 */       if (this.containsNullKey && v == this.value[this.n]) {
/*  418 */         removeNullEntry();
/*  419 */         return true;
/*      */       } 
/*  421 */       return false;
/*      */     } 
/*      */     
/*  424 */     short[] key = this.key;
/*      */     short curr;
/*      */     int pos;
/*  427 */     if ((curr = key[pos = HashCommon.mix(this.strategy.hashCode(k)) & this.mask]) == 0) return false; 
/*  428 */     if (this.strategy.equals(k, curr) && v == this.value[pos]) {
/*  429 */       removeEntry(pos);
/*  430 */       return true;
/*      */     } 
/*      */     while (true) {
/*  433 */       if ((curr = key[pos = pos + 1 & this.mask]) == 0) return false; 
/*  434 */       if (this.strategy.equals(k, curr) && v == this.value[pos]) {
/*  435 */         removeEntry(pos);
/*  436 */         return true;
/*      */       } 
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean replace(short k, boolean oldValue, boolean v) {
/*  444 */     int pos = find(k);
/*  445 */     if (pos < 0 || oldValue != this.value[pos]) return false; 
/*  446 */     this.value[pos] = v;
/*  447 */     return true;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean replace(short k, boolean v) {
/*  453 */     int pos = find(k);
/*  454 */     if (pos < 0) return this.defRetValue; 
/*  455 */     boolean oldValue = this.value[pos];
/*  456 */     this.value[pos] = v;
/*  457 */     return oldValue;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean computeIfAbsent(short k, IntPredicate mappingFunction) {
/*  463 */     Objects.requireNonNull(mappingFunction);
/*  464 */     int pos = find(k);
/*  465 */     if (pos >= 0) return this.value[pos]; 
/*  466 */     boolean newValue = mappingFunction.test(k);
/*  467 */     insert(-pos - 1, k, newValue);
/*  468 */     return newValue;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean computeIfAbsent(short key, Short2BooleanFunction mappingFunction) {
/*  474 */     Objects.requireNonNull(mappingFunction);
/*  475 */     int pos = find(key);
/*  476 */     if (pos >= 0) return this.value[pos]; 
/*  477 */     if (!mappingFunction.containsKey(key)) return this.defRetValue; 
/*  478 */     boolean newValue = mappingFunction.get(key);
/*  479 */     insert(-pos - 1, key, newValue);
/*  480 */     return newValue;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean computeIfAbsentNullable(short k, IntFunction<? extends Boolean> mappingFunction) {
/*  486 */     Objects.requireNonNull(mappingFunction);
/*  487 */     int pos = find(k);
/*  488 */     if (pos >= 0) return this.value[pos]; 
/*  489 */     Boolean newValue = mappingFunction.apply(k);
/*  490 */     if (newValue == null) return this.defRetValue; 
/*  491 */     boolean v = newValue.booleanValue();
/*  492 */     insert(-pos - 1, k, v);
/*  493 */     return v;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean computeIfPresent(short k, BiFunction<? super Short, ? super Boolean, ? extends Boolean> remappingFunction) {
/*  499 */     Objects.requireNonNull(remappingFunction);
/*  500 */     int pos = find(k);
/*  501 */     if (pos < 0) return this.defRetValue; 
/*  502 */     Boolean newValue = remappingFunction.apply(Short.valueOf(k), Boolean.valueOf(this.value[pos]));
/*  503 */     if (newValue == null) {
/*  504 */       if (this.strategy.equals(k, (short)0)) { removeNullEntry(); }
/*  505 */       else { removeEntry(pos); }
/*  506 */        return this.defRetValue;
/*      */     } 
/*  508 */     this.value[pos] = newValue.booleanValue(); return newValue.booleanValue();
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean compute(short k, BiFunction<? super Short, ? super Boolean, ? extends Boolean> remappingFunction) {
/*  514 */     Objects.requireNonNull(remappingFunction);
/*  515 */     int pos = find(k);
/*  516 */     Boolean newValue = remappingFunction.apply(Short.valueOf(k), (pos >= 0) ? Boolean.valueOf(this.value[pos]) : null);
/*  517 */     if (newValue == null) {
/*  518 */       if (pos >= 0)
/*  519 */         if (this.strategy.equals(k, (short)0)) { removeNullEntry(); }
/*  520 */         else { removeEntry(pos); }
/*      */          
/*  522 */       return this.defRetValue;
/*      */     } 
/*  524 */     boolean newVal = newValue.booleanValue();
/*  525 */     if (pos < 0) {
/*  526 */       insert(-pos - 1, k, newVal);
/*  527 */       return newVal;
/*      */     } 
/*  529 */     this.value[pos] = newVal; return newVal;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean merge(short k, boolean v, BiFunction<? super Boolean, ? super Boolean, ? extends Boolean> remappingFunction) {
/*  535 */     Objects.requireNonNull(remappingFunction);
/*      */     
/*  537 */     int pos = find(k);
/*  538 */     if (pos < 0) {
/*  539 */       if (pos < 0) { insert(-pos - 1, k, v); }
/*  540 */       else { this.value[pos] = v; }
/*  541 */        return v;
/*      */     } 
/*  543 */     Boolean newValue = remappingFunction.apply(Boolean.valueOf(this.value[pos]), Boolean.valueOf(v));
/*  544 */     if (newValue == null) {
/*  545 */       if (this.strategy.equals(k, (short)0)) { removeNullEntry(); }
/*  546 */       else { removeEntry(pos); }
/*  547 */        return this.defRetValue;
/*      */     } 
/*  549 */     this.value[pos] = newValue.booleanValue(); return newValue.booleanValue();
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
/*  560 */     if (this.size == 0)
/*  561 */       return;  this.size = 0;
/*  562 */     this.containsNullKey = false;
/*  563 */     Arrays.fill(this.key, (short)0);
/*      */   }
/*      */ 
/*      */   
/*      */   public int size() {
/*  568 */     return this.size;
/*      */   }
/*      */ 
/*      */   
/*      */   public boolean isEmpty() {
/*  573 */     return (this.size == 0);
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   final class MapEntry
/*      */     implements Short2BooleanMap.Entry, Map.Entry<Short, Boolean>, ShortBooleanPair
/*      */   {
/*      */     int index;
/*      */ 
/*      */ 
/*      */     
/*      */     MapEntry(int index) {
/*  586 */       this.index = index;
/*      */     }
/*      */ 
/*      */     
/*      */     MapEntry() {}
/*      */ 
/*      */     
/*      */     public short getShortKey() {
/*  594 */       return Short2BooleanOpenCustomHashMap.this.key[this.index];
/*      */     }
/*      */ 
/*      */     
/*      */     public short leftShort() {
/*  599 */       return Short2BooleanOpenCustomHashMap.this.key[this.index];
/*      */     }
/*      */ 
/*      */     
/*      */     public boolean getBooleanValue() {
/*  604 */       return Short2BooleanOpenCustomHashMap.this.value[this.index];
/*      */     }
/*      */ 
/*      */     
/*      */     public boolean rightBoolean() {
/*  609 */       return Short2BooleanOpenCustomHashMap.this.value[this.index];
/*      */     }
/*      */ 
/*      */     
/*      */     public boolean setValue(boolean v) {
/*  614 */       boolean oldValue = Short2BooleanOpenCustomHashMap.this.value[this.index];
/*  615 */       Short2BooleanOpenCustomHashMap.this.value[this.index] = v;
/*  616 */       return oldValue;
/*      */     }
/*      */ 
/*      */     
/*      */     public ShortBooleanPair right(boolean v) {
/*  621 */       Short2BooleanOpenCustomHashMap.this.value[this.index] = v;
/*  622 */       return this;
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
/*  633 */       return Short.valueOf(Short2BooleanOpenCustomHashMap.this.key[this.index]);
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     @Deprecated
/*      */     public Boolean getValue() {
/*  644 */       return Boolean.valueOf(Short2BooleanOpenCustomHashMap.this.value[this.index]);
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     @Deprecated
/*      */     public Boolean setValue(Boolean v) {
/*  655 */       return Boolean.valueOf(setValue(v.booleanValue()));
/*      */     }
/*      */ 
/*      */ 
/*      */     
/*      */     public boolean equals(Object o) {
/*  661 */       if (!(o instanceof Map.Entry)) return false; 
/*  662 */       Map.Entry<Short, Boolean> e = (Map.Entry<Short, Boolean>)o;
/*  663 */       return (Short2BooleanOpenCustomHashMap.this.strategy.equals(Short2BooleanOpenCustomHashMap.this.key[this.index], ((Short)e.getKey()).shortValue()) && Short2BooleanOpenCustomHashMap.this.value[this.index] == ((Boolean)e.getValue()).booleanValue());
/*      */     }
/*      */ 
/*      */     
/*      */     public int hashCode() {
/*  668 */       return Short2BooleanOpenCustomHashMap.this.strategy.hashCode(Short2BooleanOpenCustomHashMap.this.key[this.index]) ^ (Short2BooleanOpenCustomHashMap.this.value[this.index] ? 1231 : 1237);
/*      */     }
/*      */ 
/*      */     
/*      */     public String toString() {
/*  673 */       return Short2BooleanOpenCustomHashMap.this.key[this.index] + "=>" + Short2BooleanOpenCustomHashMap.this.value[this.index];
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
/*  684 */     int pos = Short2BooleanOpenCustomHashMap.this.n;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  690 */     int last = -1;
/*      */     
/*  692 */     int c = Short2BooleanOpenCustomHashMap.this.size;
/*      */     
/*  694 */     boolean mustReturnNullKey = Short2BooleanOpenCustomHashMap.this.containsNullKey;
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
/*  705 */       return (this.c != 0);
/*      */     }
/*      */     
/*      */     public int nextEntry() {
/*  709 */       if (!hasNext()) throw new NoSuchElementException(); 
/*  710 */       this.c--;
/*  711 */       if (this.mustReturnNullKey) {
/*  712 */         this.mustReturnNullKey = false;
/*  713 */         return this.last = Short2BooleanOpenCustomHashMap.this.n;
/*      */       } 
/*  715 */       short[] key = Short2BooleanOpenCustomHashMap.this.key;
/*      */       while (true) {
/*  717 */         if (--this.pos < 0) {
/*      */           
/*  719 */           this.last = Integer.MIN_VALUE;
/*  720 */           short k = this.wrapped.getShort(-this.pos - 1);
/*  721 */           int p = HashCommon.mix(Short2BooleanOpenCustomHashMap.this.strategy.hashCode(k)) & Short2BooleanOpenCustomHashMap.this.mask;
/*  722 */           for (; !Short2BooleanOpenCustomHashMap.this.strategy.equals(k, key[p]); p = p + 1 & Short2BooleanOpenCustomHashMap.this.mask);
/*  723 */           return p;
/*      */         } 
/*  725 */         if (key[this.pos] != 0) return this.last = this.pos; 
/*      */       } 
/*      */     }
/*      */     
/*      */     public void forEachRemaining(ConsumerType action) {
/*  730 */       if (this.mustReturnNullKey) {
/*  731 */         this.mustReturnNullKey = false;
/*  732 */         acceptOnIndex(action, this.last = Short2BooleanOpenCustomHashMap.this.n);
/*  733 */         this.c--;
/*      */       } 
/*  735 */       short[] key = Short2BooleanOpenCustomHashMap.this.key;
/*  736 */       while (this.c != 0) {
/*  737 */         if (--this.pos < 0) {
/*      */           
/*  739 */           this.last = Integer.MIN_VALUE;
/*  740 */           short k = this.wrapped.getShort(-this.pos - 1);
/*  741 */           int p = HashCommon.mix(Short2BooleanOpenCustomHashMap.this.strategy.hashCode(k)) & Short2BooleanOpenCustomHashMap.this.mask;
/*  742 */           for (; !Short2BooleanOpenCustomHashMap.this.strategy.equals(k, key[p]); p = p + 1 & Short2BooleanOpenCustomHashMap.this.mask);
/*  743 */           acceptOnIndex(action, p);
/*  744 */           this.c--; continue;
/*  745 */         }  if (key[this.pos] != 0) {
/*  746 */           acceptOnIndex(action, this.last = this.pos);
/*  747 */           this.c--;
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
/*  762 */       short[] key = Short2BooleanOpenCustomHashMap.this.key; while (true) {
/*      */         short curr; int last;
/*  764 */         pos = (last = pos) + 1 & Short2BooleanOpenCustomHashMap.this.mask;
/*      */         while (true) {
/*  766 */           if ((curr = key[pos]) == 0) {
/*  767 */             key[last] = 0;
/*      */             return;
/*      */           } 
/*  770 */           int slot = HashCommon.mix(Short2BooleanOpenCustomHashMap.this.strategy.hashCode(curr)) & Short2BooleanOpenCustomHashMap.this.mask;
/*  771 */           if ((last <= pos) ? (last >= slot || slot > pos) : (last >= slot && slot > pos))
/*  772 */             break;  pos = pos + 1 & Short2BooleanOpenCustomHashMap.this.mask;
/*      */         } 
/*  774 */         if (pos < last) {
/*  775 */           if (this.wrapped == null) this.wrapped = new ShortArrayList(2); 
/*  776 */           this.wrapped.add(key[pos]);
/*      */         } 
/*  778 */         key[last] = curr;
/*  779 */         Short2BooleanOpenCustomHashMap.this.value[last] = Short2BooleanOpenCustomHashMap.this.value[pos];
/*      */       } 
/*      */     }
/*      */     
/*      */     public void remove() {
/*  784 */       if (this.last == -1) throw new IllegalStateException(); 
/*  785 */       if (this.last == Short2BooleanOpenCustomHashMap.this.n)
/*  786 */       { Short2BooleanOpenCustomHashMap.this.containsNullKey = false; }
/*  787 */       else if (this.pos >= 0) { shiftKeys(this.last); }
/*      */       else
/*      */       
/*  790 */       { Short2BooleanOpenCustomHashMap.this.remove(this.wrapped.getShort(-this.pos - 1));
/*  791 */         this.last = -1;
/*      */         return; }
/*      */       
/*  794 */       Short2BooleanOpenCustomHashMap.this.size--;
/*  795 */       this.last = -1;
/*      */     }
/*      */ 
/*      */     
/*      */     public int skip(int n) {
/*  800 */       int i = n;
/*  801 */       for (; i-- != 0 && hasNext(); nextEntry());
/*  802 */       return n - i - 1;
/*      */     }
/*      */     
/*      */     private MapIterator() {}
/*      */     
/*      */     abstract void acceptOnIndex(ConsumerType param1ConsumerType, int param1Int);
/*      */   }
/*      */   
/*      */   private final class EntryIterator extends MapIterator<Consumer<? super Short2BooleanMap.Entry>> implements ObjectIterator<Short2BooleanMap.Entry> { public Short2BooleanOpenCustomHashMap.MapEntry next() {
/*  811 */       return this.entry = new Short2BooleanOpenCustomHashMap.MapEntry(nextEntry());
/*      */     }
/*      */     private Short2BooleanOpenCustomHashMap.MapEntry entry;
/*      */     private EntryIterator() {}
/*      */     
/*      */     final void acceptOnIndex(Consumer<? super Short2BooleanMap.Entry> action, int index) {
/*  817 */       action.accept(this.entry = new Short2BooleanOpenCustomHashMap.MapEntry(index));
/*      */     }
/*      */ 
/*      */     
/*      */     public void remove() {
/*  822 */       super.remove();
/*  823 */       this.entry.index = -1;
/*      */     } }
/*      */   
/*      */   private final class FastEntryIterator extends MapIterator<Consumer<? super Short2BooleanMap.Entry>> implements ObjectIterator<Short2BooleanMap.Entry> {
/*      */     private FastEntryIterator() {
/*  828 */       this.entry = new Short2BooleanOpenCustomHashMap.MapEntry();
/*      */     }
/*      */     private final Short2BooleanOpenCustomHashMap.MapEntry entry;
/*      */     public Short2BooleanOpenCustomHashMap.MapEntry next() {
/*  832 */       this.entry.index = nextEntry();
/*  833 */       return this.entry;
/*      */     }
/*      */ 
/*      */ 
/*      */     
/*      */     final void acceptOnIndex(Consumer<? super Short2BooleanMap.Entry> action, int index) {
/*  839 */       this.entry.index = index;
/*  840 */       action.accept(this.entry);
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private abstract class MapSpliterator<ConsumerType, SplitType extends MapSpliterator<ConsumerType, SplitType>>
/*      */   {
/*  849 */     int pos = 0;
/*      */     
/*  851 */     int max = Short2BooleanOpenCustomHashMap.this.n;
/*      */     
/*  853 */     int c = 0;
/*      */     
/*  855 */     boolean mustReturnNull = Short2BooleanOpenCustomHashMap.this.containsNullKey;
/*      */     
/*      */     boolean hasSplit = false;
/*      */     
/*      */     MapSpliterator() {}
/*      */     
/*      */     MapSpliterator(int pos, int max, boolean mustReturnNull, boolean hasSplit) {
/*  862 */       this.pos = pos;
/*  863 */       this.max = max;
/*  864 */       this.mustReturnNull = mustReturnNull;
/*  865 */       this.hasSplit = hasSplit;
/*      */     }
/*      */     
/*      */     abstract void acceptOnIndex(ConsumerType param1ConsumerType, int param1Int);
/*      */     
/*      */     abstract SplitType makeForSplit(int param1Int1, int param1Int2, boolean param1Boolean);
/*      */     
/*      */     public boolean tryAdvance(ConsumerType action) {
/*  873 */       if (this.mustReturnNull) {
/*  874 */         this.mustReturnNull = false;
/*  875 */         this.c++;
/*  876 */         acceptOnIndex(action, Short2BooleanOpenCustomHashMap.this.n);
/*  877 */         return true;
/*      */       } 
/*  879 */       short[] key = Short2BooleanOpenCustomHashMap.this.key;
/*  880 */       while (this.pos < this.max) {
/*  881 */         if (key[this.pos] != 0) {
/*  882 */           this.c++;
/*  883 */           acceptOnIndex(action, this.pos++);
/*  884 */           return true;
/*      */         } 
/*  886 */         this.pos++;
/*      */       } 
/*  888 */       return false;
/*      */     }
/*      */     
/*      */     public void forEachRemaining(ConsumerType action) {
/*  892 */       if (this.mustReturnNull) {
/*  893 */         this.mustReturnNull = false;
/*  894 */         this.c++;
/*  895 */         acceptOnIndex(action, Short2BooleanOpenCustomHashMap.this.n);
/*      */       } 
/*  897 */       short[] key = Short2BooleanOpenCustomHashMap.this.key;
/*  898 */       while (this.pos < this.max) {
/*  899 */         if (key[this.pos] != 0) {
/*  900 */           acceptOnIndex(action, this.pos);
/*  901 */           this.c++;
/*      */         } 
/*  903 */         this.pos++;
/*      */       } 
/*      */     }
/*      */     
/*      */     public long estimateSize() {
/*  908 */       if (!this.hasSplit)
/*      */       {
/*  910 */         return (Short2BooleanOpenCustomHashMap.this.size - this.c);
/*      */       }
/*      */ 
/*      */ 
/*      */       
/*  915 */       return Math.min((Short2BooleanOpenCustomHashMap.this.size - this.c), (long)(Short2BooleanOpenCustomHashMap.this.realSize() / Short2BooleanOpenCustomHashMap.this.n * (this.max - this.pos)) + (this.mustReturnNull ? 1L : 0L));
/*      */     }
/*      */ 
/*      */     
/*      */     public SplitType trySplit() {
/*  920 */       if (this.pos >= this.max - 1) return null; 
/*  921 */       int retLen = this.max - this.pos >> 1;
/*  922 */       if (retLen <= 1) return null; 
/*  923 */       int myNewPos = this.pos + retLen;
/*  924 */       int retPos = this.pos;
/*  925 */       int retMax = myNewPos;
/*      */ 
/*      */ 
/*      */       
/*  929 */       SplitType split = makeForSplit(retPos, retMax, this.mustReturnNull);
/*  930 */       this.pos = myNewPos;
/*  931 */       this.mustReturnNull = false;
/*  932 */       this.hasSplit = true;
/*  933 */       return split;
/*      */     }
/*      */     
/*      */     public long skip(long n) {
/*  937 */       if (n < 0L) throw new IllegalArgumentException("Argument must be nonnegative: " + n); 
/*  938 */       if (n == 0L) return 0L; 
/*  939 */       long skipped = 0L;
/*  940 */       if (this.mustReturnNull) {
/*  941 */         this.mustReturnNull = false;
/*  942 */         skipped++;
/*  943 */         n--;
/*      */       } 
/*  945 */       short[] key = Short2BooleanOpenCustomHashMap.this.key;
/*  946 */       while (this.pos < this.max && n > 0L) {
/*  947 */         if (key[this.pos++] != 0) {
/*  948 */           skipped++;
/*  949 */           n--;
/*      */         } 
/*      */       } 
/*  952 */       return skipped;
/*      */     }
/*      */   }
/*      */   
/*      */   private final class EntrySpliterator
/*      */     extends MapSpliterator<Consumer<? super Short2BooleanMap.Entry>, EntrySpliterator> implements ObjectSpliterator<Short2BooleanMap.Entry> {
/*      */     private static final int POST_SPLIT_CHARACTERISTICS = 1;
/*      */     
/*      */     EntrySpliterator() {}
/*      */     
/*      */     EntrySpliterator(int pos, int max, boolean mustReturnNull, boolean hasSplit) {
/*  963 */       super(pos, max, mustReturnNull, hasSplit);
/*      */     }
/*      */ 
/*      */     
/*      */     public int characteristics() {
/*  968 */       return this.hasSplit ? 1 : 65;
/*      */     }
/*      */ 
/*      */     
/*      */     final void acceptOnIndex(Consumer<? super Short2BooleanMap.Entry> action, int index) {
/*  973 */       action.accept(new Short2BooleanOpenCustomHashMap.MapEntry(index));
/*      */     }
/*      */ 
/*      */     
/*      */     final EntrySpliterator makeForSplit(int pos, int max, boolean mustReturnNull) {
/*  978 */       return new EntrySpliterator(pos, max, mustReturnNull, true);
/*      */     } }
/*      */   
/*      */   private final class MapEntrySet extends AbstractObjectSet<Short2BooleanMap.Entry> implements Short2BooleanMap.FastEntrySet {
/*      */     private MapEntrySet() {}
/*      */     
/*      */     public ObjectIterator<Short2BooleanMap.Entry> iterator() {
/*  985 */       return new Short2BooleanOpenCustomHashMap.EntryIterator();
/*      */     }
/*      */ 
/*      */     
/*      */     public ObjectIterator<Short2BooleanMap.Entry> fastIterator() {
/*  990 */       return new Short2BooleanOpenCustomHashMap.FastEntryIterator();
/*      */     }
/*      */ 
/*      */     
/*      */     public ObjectSpliterator<Short2BooleanMap.Entry> spliterator() {
/*  995 */       return new Short2BooleanOpenCustomHashMap.EntrySpliterator();
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public boolean contains(Object o) {
/* 1002 */       if (!(o instanceof Map.Entry)) return false; 
/* 1003 */       Map.Entry<?, ?> e = (Map.Entry<?, ?>)o;
/* 1004 */       if (e.getKey() == null || !(e.getKey() instanceof Short)) return false; 
/* 1005 */       if (e.getValue() == null || !(e.getValue() instanceof Boolean)) return false; 
/* 1006 */       short k = ((Short)e.getKey()).shortValue();
/* 1007 */       boolean v = ((Boolean)e.getValue()).booleanValue();
/* 1008 */       if (Short2BooleanOpenCustomHashMap.this.strategy.equals(k, (short)0)) return (Short2BooleanOpenCustomHashMap.this.containsNullKey && Short2BooleanOpenCustomHashMap.this.value[Short2BooleanOpenCustomHashMap.this.n] == v);
/*      */       
/* 1010 */       short[] key = Short2BooleanOpenCustomHashMap.this.key;
/*      */       short curr;
/*      */       int pos;
/* 1013 */       if ((curr = key[pos = HashCommon.mix(Short2BooleanOpenCustomHashMap.this.strategy.hashCode(k)) & Short2BooleanOpenCustomHashMap.this.mask]) == 0) return false; 
/* 1014 */       if (Short2BooleanOpenCustomHashMap.this.strategy.equals(k, curr)) return (Short2BooleanOpenCustomHashMap.this.value[pos] == v);
/*      */       
/*      */       while (true) {
/* 1017 */         if ((curr = key[pos = pos + 1 & Short2BooleanOpenCustomHashMap.this.mask]) == 0) return false; 
/* 1018 */         if (Short2BooleanOpenCustomHashMap.this.strategy.equals(k, curr)) return (Short2BooleanOpenCustomHashMap.this.value[pos] == v);
/*      */       
/*      */       } 
/*      */     }
/*      */ 
/*      */     
/*      */     public boolean remove(Object o) {
/* 1025 */       if (!(o instanceof Map.Entry)) return false; 
/* 1026 */       Map.Entry<?, ?> e = (Map.Entry<?, ?>)o;
/* 1027 */       if (e.getKey() == null || !(e.getKey() instanceof Short)) return false; 
/* 1028 */       if (e.getValue() == null || !(e.getValue() instanceof Boolean)) return false; 
/* 1029 */       short k = ((Short)e.getKey()).shortValue();
/* 1030 */       boolean v = ((Boolean)e.getValue()).booleanValue();
/* 1031 */       if (Short2BooleanOpenCustomHashMap.this.strategy.equals(k, (short)0)) {
/* 1032 */         if (Short2BooleanOpenCustomHashMap.this.containsNullKey && Short2BooleanOpenCustomHashMap.this.value[Short2BooleanOpenCustomHashMap.this.n] == v) {
/* 1033 */           Short2BooleanOpenCustomHashMap.this.removeNullEntry();
/* 1034 */           return true;
/*      */         } 
/* 1036 */         return false;
/*      */       } 
/*      */       
/* 1039 */       short[] key = Short2BooleanOpenCustomHashMap.this.key;
/*      */       short curr;
/*      */       int pos;
/* 1042 */       if ((curr = key[pos = HashCommon.mix(Short2BooleanOpenCustomHashMap.this.strategy.hashCode(k)) & Short2BooleanOpenCustomHashMap.this.mask]) == 0) return false; 
/* 1043 */       if (Short2BooleanOpenCustomHashMap.this.strategy.equals(curr, k)) {
/* 1044 */         if (Short2BooleanOpenCustomHashMap.this.value[pos] == v) {
/* 1045 */           Short2BooleanOpenCustomHashMap.this.removeEntry(pos);
/* 1046 */           return true;
/*      */         } 
/* 1048 */         return false;
/*      */       } 
/*      */       while (true) {
/* 1051 */         if ((curr = key[pos = pos + 1 & Short2BooleanOpenCustomHashMap.this.mask]) == 0) return false; 
/* 1052 */         if (Short2BooleanOpenCustomHashMap.this.strategy.equals(curr, k) && 
/* 1053 */           Short2BooleanOpenCustomHashMap.this.value[pos] == v) {
/* 1054 */           Short2BooleanOpenCustomHashMap.this.removeEntry(pos);
/* 1055 */           return true;
/*      */         } 
/*      */       } 
/*      */     }
/*      */ 
/*      */ 
/*      */     
/*      */     public int size() {
/* 1063 */       return Short2BooleanOpenCustomHashMap.this.size;
/*      */     }
/*      */ 
/*      */     
/*      */     public void clear() {
/* 1068 */       Short2BooleanOpenCustomHashMap.this.clear();
/*      */     }
/*      */ 
/*      */ 
/*      */     
/*      */     public void forEach(Consumer<? super Short2BooleanMap.Entry> consumer) {
/* 1074 */       if (Short2BooleanOpenCustomHashMap.this.containsNullKey) consumer.accept(new Short2BooleanOpenCustomHashMap.MapEntry(Short2BooleanOpenCustomHashMap.this.n)); 
/* 1075 */       for (int pos = Short2BooleanOpenCustomHashMap.this.n; pos-- != 0;) { if (Short2BooleanOpenCustomHashMap.this.key[pos] != 0) consumer.accept(new Short2BooleanOpenCustomHashMap.MapEntry(pos));
/*      */          }
/*      */     
/*      */     }
/*      */     
/*      */     public void fastForEach(Consumer<? super Short2BooleanMap.Entry> consumer) {
/* 1081 */       Short2BooleanOpenCustomHashMap.MapEntry entry = new Short2BooleanOpenCustomHashMap.MapEntry();
/* 1082 */       if (Short2BooleanOpenCustomHashMap.this.containsNullKey) {
/* 1083 */         entry.index = Short2BooleanOpenCustomHashMap.this.n;
/* 1084 */         consumer.accept(entry);
/*      */       } 
/* 1086 */       for (int pos = Short2BooleanOpenCustomHashMap.this.n; pos-- != 0;) { if (Short2BooleanOpenCustomHashMap.this.key[pos] != 0) {
/* 1087 */           entry.index = pos;
/* 1088 */           consumer.accept(entry);
/*      */         }  }
/*      */     
/*      */     }
/*      */   }
/*      */   
/*      */   public Short2BooleanMap.FastEntrySet short2BooleanEntrySet() {
/* 1095 */     if (this.entries == null) this.entries = new MapEntrySet(); 
/* 1096 */     return this.entries;
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
/* 1117 */       action.accept(Short2BooleanOpenCustomHashMap.this.key[index]);
/*      */     }
/*      */ 
/*      */     
/*      */     public short nextShort() {
/* 1122 */       return Short2BooleanOpenCustomHashMap.this.key[nextEntry()];
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
/* 1133 */       super(pos, max, mustReturnNull, hasSplit);
/*      */     }
/*      */ 
/*      */     
/*      */     public int characteristics() {
/* 1138 */       return this.hasSplit ? 257 : 321;
/*      */     }
/*      */ 
/*      */     
/*      */     final void acceptOnIndex(ShortConsumer action, int index) {
/* 1143 */       action.accept(Short2BooleanOpenCustomHashMap.this.key[index]);
/*      */     }
/*      */ 
/*      */     
/*      */     final KeySpliterator makeForSplit(int pos, int max, boolean mustReturnNull) {
/* 1148 */       return new KeySpliterator(pos, max, mustReturnNull, true);
/*      */     } }
/*      */   
/*      */   private final class KeySet extends AbstractShortSet {
/*      */     private KeySet() {}
/*      */     
/*      */     public ShortIterator iterator() {
/* 1155 */       return new Short2BooleanOpenCustomHashMap.KeyIterator();
/*      */     }
/*      */ 
/*      */     
/*      */     public ShortSpliterator spliterator() {
/* 1160 */       return new Short2BooleanOpenCustomHashMap.KeySpliterator();
/*      */     }
/*      */ 
/*      */ 
/*      */     
/*      */     public void forEach(ShortConsumer consumer) {
/* 1166 */       if (Short2BooleanOpenCustomHashMap.this.containsNullKey) consumer.accept(Short2BooleanOpenCustomHashMap.this.key[Short2BooleanOpenCustomHashMap.this.n]); 
/* 1167 */       for (int pos = Short2BooleanOpenCustomHashMap.this.n; pos-- != 0; ) {
/* 1168 */         short k = Short2BooleanOpenCustomHashMap.this.key[pos];
/* 1169 */         if (k != 0) consumer.accept(k);
/*      */       
/*      */       } 
/*      */     }
/*      */     
/*      */     public int size() {
/* 1175 */       return Short2BooleanOpenCustomHashMap.this.size;
/*      */     }
/*      */ 
/*      */     
/*      */     public boolean contains(short k) {
/* 1180 */       return Short2BooleanOpenCustomHashMap.this.containsKey(k);
/*      */     }
/*      */ 
/*      */     
/*      */     public boolean remove(short k) {
/* 1185 */       int oldSize = Short2BooleanOpenCustomHashMap.this.size;
/* 1186 */       Short2BooleanOpenCustomHashMap.this.remove(k);
/* 1187 */       return (Short2BooleanOpenCustomHashMap.this.size != oldSize);
/*      */     }
/*      */ 
/*      */     
/*      */     public void clear() {
/* 1192 */       Short2BooleanOpenCustomHashMap.this.clear();
/*      */     }
/*      */   }
/*      */ 
/*      */   
/*      */   public ShortSet keySet() {
/* 1198 */     if (this.keys == null) this.keys = new KeySet(); 
/* 1199 */     return this.keys;
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
/*      */     extends MapIterator<BooleanConsumer>
/*      */     implements BooleanIterator
/*      */   {
/*      */     final void acceptOnIndex(BooleanConsumer action, int index) {
/* 1220 */       action.accept(Short2BooleanOpenCustomHashMap.this.value[index]);
/*      */     }
/*      */ 
/*      */     
/*      */     public boolean nextBoolean() {
/* 1225 */       return Short2BooleanOpenCustomHashMap.this.value[nextEntry()];
/*      */     }
/*      */   }
/*      */   
/*      */   private final class ValueSpliterator
/*      */     extends MapSpliterator<BooleanConsumer, ValueSpliterator> implements BooleanSpliterator {
/*      */     private static final int POST_SPLIT_CHARACTERISTICS = 256;
/*      */     
/*      */     ValueSpliterator() {}
/*      */     
/*      */     ValueSpliterator(int pos, int max, boolean mustReturnNull, boolean hasSplit) {
/* 1236 */       super(pos, max, mustReturnNull, hasSplit);
/*      */     }
/*      */ 
/*      */     
/*      */     public int characteristics() {
/* 1241 */       return this.hasSplit ? 256 : 320;
/*      */     }
/*      */ 
/*      */     
/*      */     final void acceptOnIndex(BooleanConsumer action, int index) {
/* 1246 */       action.accept(Short2BooleanOpenCustomHashMap.this.value[index]);
/*      */     }
/*      */ 
/*      */     
/*      */     final ValueSpliterator makeForSplit(int pos, int max, boolean mustReturnNull) {
/* 1251 */       return new ValueSpliterator(pos, max, mustReturnNull, true);
/*      */     }
/*      */   }
/*      */ 
/*      */   
/*      */   public BooleanCollection values() {
/* 1257 */     if (this.values == null) this.values = (BooleanCollection)new AbstractBooleanCollection()
/*      */         {
/*      */           public BooleanIterator iterator() {
/* 1260 */             return new Short2BooleanOpenCustomHashMap.ValueIterator();
/*      */           }
/*      */ 
/*      */           
/*      */           public BooleanSpliterator spliterator() {
/* 1265 */             return new Short2BooleanOpenCustomHashMap.ValueSpliterator();
/*      */           }
/*      */ 
/*      */ 
/*      */           
/*      */           public void forEach(BooleanConsumer consumer) {
/* 1271 */             if (Short2BooleanOpenCustomHashMap.this.containsNullKey) consumer.accept(Short2BooleanOpenCustomHashMap.this.value[Short2BooleanOpenCustomHashMap.this.n]); 
/* 1272 */             for (int pos = Short2BooleanOpenCustomHashMap.this.n; pos-- != 0;) { if (Short2BooleanOpenCustomHashMap.this.key[pos] != 0) consumer.accept(Short2BooleanOpenCustomHashMap.this.value[pos]);  }
/*      */           
/*      */           }
/*      */           
/*      */           public int size() {
/* 1277 */             return Short2BooleanOpenCustomHashMap.this.size;
/*      */           }
/*      */ 
/*      */           
/*      */           public boolean contains(boolean v) {
/* 1282 */             return Short2BooleanOpenCustomHashMap.this.containsValue(v);
/*      */           }
/*      */ 
/*      */           
/*      */           public void clear() {
/* 1287 */             Short2BooleanOpenCustomHashMap.this.clear();
/*      */           }
/*      */         }; 
/* 1290 */     return this.values;
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
/* 1307 */     return trim(this.size);
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
/* 1329 */     int l = HashCommon.nextPowerOfTwo((int)Math.ceil((n / this.f)));
/* 1330 */     if (l >= this.n || this.size > HashCommon.maxFill(l, this.f)) return true; 
/*      */     try {
/* 1332 */       rehash(l);
/* 1333 */     } catch (OutOfMemoryError cantDoIt) {
/* 1334 */       return false;
/*      */     } 
/* 1336 */     return true;
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
/* 1351 */     short[] key = this.key;
/* 1352 */     boolean[] value = this.value;
/* 1353 */     int mask = newN - 1;
/* 1354 */     short[] newKey = new short[newN + 1];
/* 1355 */     boolean[] newValue = new boolean[newN + 1];
/* 1356 */     int i = this.n;
/* 1357 */     for (int j = realSize(); j-- != 0; ) {
/* 1358 */       while (key[--i] == 0); int pos;
/* 1359 */       if (newKey[pos = HashCommon.mix(this.strategy.hashCode(key[i])) & mask] != 0) while (newKey[pos = pos + 1 & mask] != 0); 
/* 1360 */       newKey[pos] = key[i];
/* 1361 */       newValue[pos] = value[i];
/*      */     } 
/* 1363 */     newValue[newN] = value[this.n];
/* 1364 */     this.n = newN;
/* 1365 */     this.mask = mask;
/* 1366 */     this.maxFill = HashCommon.maxFill(this.n, this.f);
/* 1367 */     this.key = newKey;
/* 1368 */     this.value = newValue;
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
/*      */   public Short2BooleanOpenCustomHashMap clone() {
/*      */     Short2BooleanOpenCustomHashMap c;
/*      */     try {
/* 1385 */       c = (Short2BooleanOpenCustomHashMap)super.clone();
/* 1386 */     } catch (CloneNotSupportedException cantHappen) {
/* 1387 */       throw new InternalError();
/*      */     } 
/* 1389 */     c.keys = null;
/* 1390 */     c.values = null;
/* 1391 */     c.entries = null;
/* 1392 */     c.containsNullKey = this.containsNullKey;
/* 1393 */     c.key = (short[])this.key.clone();
/* 1394 */     c.value = (boolean[])this.value.clone();
/* 1395 */     c.strategy = this.strategy;
/* 1396 */     return c;
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
/* 1410 */     int h = 0;
/* 1411 */     for (int j = realSize(), i = 0, t = 0; j-- != 0; ) {
/* 1412 */       for (; this.key[i] == 0; i++);
/* 1413 */       t = this.strategy.hashCode(this.key[i]);
/* 1414 */       t ^= this.value[i] ? 1231 : 1237;
/* 1415 */       h += t;
/* 1416 */       i++;
/*      */     } 
/*      */     
/* 1419 */     if (this.containsNullKey) h += this.value[this.n] ? 1231 : 1237; 
/* 1420 */     return h;
/*      */   }
/*      */   
/*      */   private void writeObject(ObjectOutputStream s) throws IOException {
/* 1424 */     short[] key = this.key;
/* 1425 */     boolean[] value = this.value;
/* 1426 */     EntryIterator i = new EntryIterator();
/* 1427 */     s.defaultWriteObject();
/* 1428 */     for (int j = this.size; j-- != 0; ) {
/* 1429 */       int e = i.nextEntry();
/* 1430 */       s.writeShort(key[e]);
/* 1431 */       s.writeBoolean(value[e]);
/*      */     } 
/*      */   }
/*      */   
/*      */   private void readObject(ObjectInputStream s) throws IOException, ClassNotFoundException {
/* 1436 */     s.defaultReadObject();
/* 1437 */     this.n = HashCommon.arraySize(this.size, this.f);
/* 1438 */     this.maxFill = HashCommon.maxFill(this.n, this.f);
/* 1439 */     this.mask = this.n - 1;
/* 1440 */     short[] key = this.key = new short[this.n + 1];
/* 1441 */     boolean[] value = this.value = new boolean[this.n + 1];
/*      */ 
/*      */     
/* 1444 */     for (int i = this.size; i-- != 0; ) {
/* 1445 */       int pos; short k = s.readShort();
/* 1446 */       boolean v = s.readBoolean();
/* 1447 */       if (this.strategy.equals(k, (short)0)) {
/* 1448 */         pos = this.n;
/* 1449 */         this.containsNullKey = true;
/*      */       } else {
/* 1451 */         pos = HashCommon.mix(this.strategy.hashCode(k)) & this.mask;
/* 1452 */         for (; key[pos] != 0; pos = pos + 1 & this.mask);
/*      */       } 
/* 1454 */       key[pos] = k;
/* 1455 */       value[pos] = v;
/*      */     } 
/*      */   }
/*      */   
/*      */   private void checkTable() {}
/*      */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\i\\unimi\dsi\fastutil\shorts\Short2BooleanOpenCustomHashMap.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */