/*      */ package it.unimi.dsi.fastutil.floats;
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
/*      */ import java.util.function.DoubleFunction;
/*      */ import java.util.function.DoublePredicate;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ public class Float2BooleanOpenHashMap
/*      */   extends AbstractFloat2BooleanMap
/*      */   implements Serializable, Cloneable, Hash
/*      */ {
/*      */   private static final long serialVersionUID = 0L;
/*      */   private static final boolean ASSERTS = false;
/*      */   protected transient float[] key;
/*      */   protected transient boolean[] value;
/*      */   protected transient int mask;
/*      */   protected transient boolean containsNullKey;
/*      */   protected transient int n;
/*      */   protected transient int maxFill;
/*      */   protected final transient int minN;
/*      */   protected int size;
/*      */   protected final float f;
/*      */   protected transient Float2BooleanMap.FastEntrySet entries;
/*      */   protected transient FloatSet keys;
/*      */   protected transient BooleanCollection values;
/*      */   
/*      */   public Float2BooleanOpenHashMap(int expected, float f) {
/*  100 */     if (f <= 0.0F || f >= 1.0F) throw new IllegalArgumentException("Load factor must be greater than 0 and smaller than 1"); 
/*  101 */     if (expected < 0) throw new IllegalArgumentException("The expected number of elements must be nonnegative"); 
/*  102 */     this.f = f;
/*  103 */     this.minN = this.n = HashCommon.arraySize(expected, f);
/*  104 */     this.mask = this.n - 1;
/*  105 */     this.maxFill = HashCommon.maxFill(this.n, f);
/*  106 */     this.key = new float[this.n + 1];
/*  107 */     this.value = new boolean[this.n + 1];
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Float2BooleanOpenHashMap(int expected) {
/*  116 */     this(expected, 0.75F);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Float2BooleanOpenHashMap() {
/*  124 */     this(16, 0.75F);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Float2BooleanOpenHashMap(Map<? extends Float, ? extends Boolean> m, float f) {
/*  134 */     this(m.size(), f);
/*  135 */     putAll(m);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Float2BooleanOpenHashMap(Map<? extends Float, ? extends Boolean> m) {
/*  144 */     this(m, 0.75F);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Float2BooleanOpenHashMap(Float2BooleanMap m, float f) {
/*  154 */     this(m.size(), f);
/*  155 */     putAll(m);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Float2BooleanOpenHashMap(Float2BooleanMap m) {
/*  165 */     this(m, 0.75F);
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
/*      */   public Float2BooleanOpenHashMap(float[] k, boolean[] v, float f) {
/*  177 */     this(k.length, f);
/*  178 */     if (k.length != v.length) throw new IllegalArgumentException("The key array and the value array have different lengths (" + k.length + " and " + v.length + ")"); 
/*  179 */     for (int i = 0; i < k.length; ) { put(k[i], v[i]); i++; }
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
/*      */   public Float2BooleanOpenHashMap(float[] k, boolean[] v) {
/*  191 */     this(k, v, 0.75F);
/*      */   }
/*      */   
/*      */   private int realSize() {
/*  195 */     return this.containsNullKey ? (this.size - 1) : this.size;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void ensureCapacity(int capacity) {
/*  205 */     int needed = HashCommon.arraySize(capacity, this.f);
/*  206 */     if (needed > this.n) rehash(needed); 
/*      */   }
/*      */   
/*      */   private void tryCapacity(long capacity) {
/*  210 */     int needed = (int)Math.min(1073741824L, Math.max(2L, HashCommon.nextPowerOfTwo((long)Math.ceil(((float)capacity / this.f)))));
/*  211 */     if (needed > this.n) rehash(needed); 
/*      */   }
/*      */   
/*      */   private boolean removeEntry(int pos) {
/*  215 */     boolean oldValue = this.value[pos];
/*  216 */     this.size--;
/*  217 */     shiftKeys(pos);
/*  218 */     if (this.n > this.minN && this.size < this.maxFill / 4 && this.n > 16) rehash(this.n / 2); 
/*  219 */     return oldValue;
/*      */   }
/*      */   
/*      */   private boolean removeNullEntry() {
/*  223 */     this.containsNullKey = false;
/*  224 */     boolean oldValue = this.value[this.n];
/*  225 */     this.size--;
/*  226 */     if (this.n > this.minN && this.size < this.maxFill / 4 && this.n > 16) rehash(this.n / 2); 
/*  227 */     return oldValue;
/*      */   }
/*      */ 
/*      */   
/*      */   public void putAll(Map<? extends Float, ? extends Boolean> m) {
/*  232 */     if (this.f <= 0.5D) { ensureCapacity(m.size()); }
/*  233 */     else { tryCapacity((size() + m.size())); }
/*      */     
/*  235 */     super.putAll(m);
/*      */   }
/*      */   
/*      */   private int find(float k) {
/*  239 */     if (Float.floatToIntBits(k) == 0) return this.containsNullKey ? this.n : -(this.n + 1);
/*      */     
/*  241 */     float[] key = this.key;
/*      */     float curr;
/*      */     int pos;
/*  244 */     if (Float.floatToIntBits(curr = key[pos = HashCommon.mix(HashCommon.float2int(k)) & this.mask]) == 0) return -(pos + 1); 
/*  245 */     if (Float.floatToIntBits(k) == Float.floatToIntBits(curr)) return pos;
/*      */     
/*      */     while (true) {
/*  248 */       if (Float.floatToIntBits(curr = key[pos = pos + 1 & this.mask]) == 0) return -(pos + 1); 
/*  249 */       if (Float.floatToIntBits(k) == Float.floatToIntBits(curr)) return pos; 
/*      */     } 
/*      */   }
/*      */   
/*      */   private void insert(int pos, float k, boolean v) {
/*  254 */     if (pos == this.n) this.containsNullKey = true; 
/*  255 */     this.key[pos] = k;
/*  256 */     this.value[pos] = v;
/*  257 */     if (this.size++ >= this.maxFill) rehash(HashCommon.arraySize(this.size + 1, this.f));
/*      */   
/*      */   }
/*      */ 
/*      */   
/*      */   public boolean put(float k, boolean v) {
/*  263 */     int pos = find(k);
/*  264 */     if (pos < 0) {
/*  265 */       insert(-pos - 1, k, v);
/*  266 */       return this.defRetValue;
/*      */     } 
/*  268 */     boolean oldValue = this.value[pos];
/*  269 */     this.value[pos] = v;
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
/*      */   protected final void shiftKeys(int pos) {
/*  283 */     float[] key = this.key; while (true) {
/*      */       float curr; int last;
/*  285 */       pos = (last = pos) + 1 & this.mask;
/*      */       while (true) {
/*  287 */         if (Float.floatToIntBits(curr = key[pos]) == 0) {
/*  288 */           key[last] = 0.0F;
/*      */           return;
/*      */         } 
/*  291 */         int slot = HashCommon.mix(HashCommon.float2int(curr)) & this.mask;
/*  292 */         if ((last <= pos) ? (last >= slot || slot > pos) : (last >= slot && slot > pos))
/*  293 */           break;  pos = pos + 1 & this.mask;
/*      */       } 
/*  295 */       key[last] = curr;
/*  296 */       this.value[last] = this.value[pos];
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean remove(float k) {
/*  303 */     if (Float.floatToIntBits(k) == 0) {
/*  304 */       if (this.containsNullKey) return removeNullEntry(); 
/*  305 */       return this.defRetValue;
/*      */     } 
/*      */     
/*  308 */     float[] key = this.key;
/*      */     float curr;
/*      */     int pos;
/*  311 */     if (Float.floatToIntBits(curr = key[pos = HashCommon.mix(HashCommon.float2int(k)) & this.mask]) == 0) return this.defRetValue; 
/*  312 */     if (Float.floatToIntBits(k) == Float.floatToIntBits(curr)) return removeEntry(pos); 
/*      */     while (true) {
/*  314 */       if (Float.floatToIntBits(curr = key[pos = pos + 1 & this.mask]) == 0) return this.defRetValue; 
/*  315 */       if (Float.floatToIntBits(k) == Float.floatToIntBits(curr)) return removeEntry(pos);
/*      */     
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   public boolean get(float k) {
/*  322 */     if (Float.floatToIntBits(k) == 0) return this.containsNullKey ? this.value[this.n] : this.defRetValue;
/*      */     
/*  324 */     float[] key = this.key;
/*      */     float curr;
/*      */     int pos;
/*  327 */     if (Float.floatToIntBits(curr = key[pos = HashCommon.mix(HashCommon.float2int(k)) & this.mask]) == 0) return this.defRetValue; 
/*  328 */     if (Float.floatToIntBits(k) == Float.floatToIntBits(curr)) return this.value[pos];
/*      */     
/*      */     while (true) {
/*  331 */       if (Float.floatToIntBits(curr = key[pos = pos + 1 & this.mask]) == 0) return this.defRetValue; 
/*  332 */       if (Float.floatToIntBits(k) == Float.floatToIntBits(curr)) return this.value[pos];
/*      */     
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   public boolean containsKey(float k) {
/*  339 */     if (Float.floatToIntBits(k) == 0) return this.containsNullKey;
/*      */     
/*  341 */     float[] key = this.key;
/*      */     float curr;
/*      */     int pos;
/*  344 */     if (Float.floatToIntBits(curr = key[pos = HashCommon.mix(HashCommon.float2int(k)) & this.mask]) == 0) return false; 
/*  345 */     if (Float.floatToIntBits(k) == Float.floatToIntBits(curr)) return true;
/*      */     
/*      */     while (true) {
/*  348 */       if (Float.floatToIntBits(curr = key[pos = pos + 1 & this.mask]) == 0) return false; 
/*  349 */       if (Float.floatToIntBits(k) == Float.floatToIntBits(curr)) return true;
/*      */     
/*      */     } 
/*      */   }
/*      */   
/*      */   public boolean containsValue(boolean v) {
/*  355 */     boolean[] value = this.value;
/*  356 */     float[] key = this.key;
/*  357 */     if (this.containsNullKey && value[this.n] == v) return true; 
/*  358 */     for (int i = this.n; i-- != 0;) { if (Float.floatToIntBits(key[i]) != 0 && value[i] == v) return true;  }
/*  359 */      return false;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean getOrDefault(float k, boolean defaultValue) {
/*  366 */     if (Float.floatToIntBits(k) == 0) return this.containsNullKey ? this.value[this.n] : defaultValue;
/*      */     
/*  368 */     float[] key = this.key;
/*      */     float curr;
/*      */     int pos;
/*  371 */     if (Float.floatToIntBits(curr = key[pos = HashCommon.mix(HashCommon.float2int(k)) & this.mask]) == 0) return defaultValue; 
/*  372 */     if (Float.floatToIntBits(k) == Float.floatToIntBits(curr)) return this.value[pos];
/*      */     
/*      */     while (true) {
/*  375 */       if (Float.floatToIntBits(curr = key[pos = pos + 1 & this.mask]) == 0) return defaultValue; 
/*  376 */       if (Float.floatToIntBits(k) == Float.floatToIntBits(curr)) return this.value[pos];
/*      */     
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   public boolean putIfAbsent(float k, boolean v) {
/*  383 */     int pos = find(k);
/*  384 */     if (pos >= 0) return this.value[pos]; 
/*  385 */     insert(-pos - 1, k, v);
/*  386 */     return this.defRetValue;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean remove(float k, boolean v) {
/*  393 */     if (Float.floatToIntBits(k) == 0) {
/*  394 */       if (this.containsNullKey && v == this.value[this.n]) {
/*  395 */         removeNullEntry();
/*  396 */         return true;
/*      */       } 
/*  398 */       return false;
/*      */     } 
/*      */     
/*  401 */     float[] key = this.key;
/*      */     float curr;
/*      */     int pos;
/*  404 */     if (Float.floatToIntBits(curr = key[pos = HashCommon.mix(HashCommon.float2int(k)) & this.mask]) == 0) return false; 
/*  405 */     if (Float.floatToIntBits(k) == Float.floatToIntBits(curr) && v == this.value[pos]) {
/*  406 */       removeEntry(pos);
/*  407 */       return true;
/*      */     } 
/*      */     while (true) {
/*  410 */       if (Float.floatToIntBits(curr = key[pos = pos + 1 & this.mask]) == 0) return false; 
/*  411 */       if (Float.floatToIntBits(k) == Float.floatToIntBits(curr) && v == this.value[pos]) {
/*  412 */         removeEntry(pos);
/*  413 */         return true;
/*      */       } 
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean replace(float k, boolean oldValue, boolean v) {
/*  421 */     int pos = find(k);
/*  422 */     if (pos < 0 || oldValue != this.value[pos]) return false; 
/*  423 */     this.value[pos] = v;
/*  424 */     return true;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean replace(float k, boolean v) {
/*  430 */     int pos = find(k);
/*  431 */     if (pos < 0) return this.defRetValue; 
/*  432 */     boolean oldValue = this.value[pos];
/*  433 */     this.value[pos] = v;
/*  434 */     return oldValue;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean computeIfAbsent(float k, DoublePredicate mappingFunction) {
/*  440 */     Objects.requireNonNull(mappingFunction);
/*  441 */     int pos = find(k);
/*  442 */     if (pos >= 0) return this.value[pos]; 
/*  443 */     boolean newValue = mappingFunction.test(k);
/*  444 */     insert(-pos - 1, k, newValue);
/*  445 */     return newValue;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean computeIfAbsent(float key, Float2BooleanFunction mappingFunction) {
/*  451 */     Objects.requireNonNull(mappingFunction);
/*  452 */     int pos = find(key);
/*  453 */     if (pos >= 0) return this.value[pos]; 
/*  454 */     if (!mappingFunction.containsKey(key)) return this.defRetValue; 
/*  455 */     boolean newValue = mappingFunction.get(key);
/*  456 */     insert(-pos - 1, key, newValue);
/*  457 */     return newValue;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean computeIfAbsentNullable(float k, DoubleFunction<? extends Boolean> mappingFunction) {
/*  463 */     Objects.requireNonNull(mappingFunction);
/*  464 */     int pos = find(k);
/*  465 */     if (pos >= 0) return this.value[pos]; 
/*  466 */     Boolean newValue = mappingFunction.apply(k);
/*  467 */     if (newValue == null) return this.defRetValue; 
/*  468 */     boolean v = newValue.booleanValue();
/*  469 */     insert(-pos - 1, k, v);
/*  470 */     return v;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean computeIfPresent(float k, BiFunction<? super Float, ? super Boolean, ? extends Boolean> remappingFunction) {
/*  476 */     Objects.requireNonNull(remappingFunction);
/*  477 */     int pos = find(k);
/*  478 */     if (pos < 0) return this.defRetValue; 
/*  479 */     Boolean newValue = remappingFunction.apply(Float.valueOf(k), Boolean.valueOf(this.value[pos]));
/*  480 */     if (newValue == null) {
/*  481 */       if (Float.floatToIntBits(k) == 0) { removeNullEntry(); }
/*  482 */       else { removeEntry(pos); }
/*  483 */        return this.defRetValue;
/*      */     } 
/*  485 */     this.value[pos] = newValue.booleanValue(); return newValue.booleanValue();
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean compute(float k, BiFunction<? super Float, ? super Boolean, ? extends Boolean> remappingFunction) {
/*  491 */     Objects.requireNonNull(remappingFunction);
/*  492 */     int pos = find(k);
/*  493 */     Boolean newValue = remappingFunction.apply(Float.valueOf(k), (pos >= 0) ? Boolean.valueOf(this.value[pos]) : null);
/*  494 */     if (newValue == null) {
/*  495 */       if (pos >= 0)
/*  496 */         if (Float.floatToIntBits(k) == 0) { removeNullEntry(); }
/*  497 */         else { removeEntry(pos); }
/*      */          
/*  499 */       return this.defRetValue;
/*      */     } 
/*  501 */     boolean newVal = newValue.booleanValue();
/*  502 */     if (pos < 0) {
/*  503 */       insert(-pos - 1, k, newVal);
/*  504 */       return newVal;
/*      */     } 
/*  506 */     this.value[pos] = newVal; return newVal;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean merge(float k, boolean v, BiFunction<? super Boolean, ? super Boolean, ? extends Boolean> remappingFunction) {
/*  512 */     Objects.requireNonNull(remappingFunction);
/*      */     
/*  514 */     int pos = find(k);
/*  515 */     if (pos < 0) {
/*  516 */       if (pos < 0) { insert(-pos - 1, k, v); }
/*  517 */       else { this.value[pos] = v; }
/*  518 */        return v;
/*      */     } 
/*  520 */     Boolean newValue = remappingFunction.apply(Boolean.valueOf(this.value[pos]), Boolean.valueOf(v));
/*  521 */     if (newValue == null) {
/*  522 */       if (Float.floatToIntBits(k) == 0) { removeNullEntry(); }
/*  523 */       else { removeEntry(pos); }
/*  524 */        return this.defRetValue;
/*      */     } 
/*  526 */     this.value[pos] = newValue.booleanValue(); return newValue.booleanValue();
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
/*  537 */     if (this.size == 0)
/*  538 */       return;  this.size = 0;
/*  539 */     this.containsNullKey = false;
/*  540 */     Arrays.fill(this.key, 0.0F);
/*      */   }
/*      */ 
/*      */   
/*      */   public int size() {
/*  545 */     return this.size;
/*      */   }
/*      */ 
/*      */   
/*      */   public boolean isEmpty() {
/*  550 */     return (this.size == 0);
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   final class MapEntry
/*      */     implements Float2BooleanMap.Entry, Map.Entry<Float, Boolean>, FloatBooleanPair
/*      */   {
/*      */     int index;
/*      */ 
/*      */ 
/*      */     
/*      */     MapEntry(int index) {
/*  563 */       this.index = index;
/*      */     }
/*      */ 
/*      */     
/*      */     MapEntry() {}
/*      */ 
/*      */     
/*      */     public float getFloatKey() {
/*  571 */       return Float2BooleanOpenHashMap.this.key[this.index];
/*      */     }
/*      */ 
/*      */     
/*      */     public float leftFloat() {
/*  576 */       return Float2BooleanOpenHashMap.this.key[this.index];
/*      */     }
/*      */ 
/*      */     
/*      */     public boolean getBooleanValue() {
/*  581 */       return Float2BooleanOpenHashMap.this.value[this.index];
/*      */     }
/*      */ 
/*      */     
/*      */     public boolean rightBoolean() {
/*  586 */       return Float2BooleanOpenHashMap.this.value[this.index];
/*      */     }
/*      */ 
/*      */     
/*      */     public boolean setValue(boolean v) {
/*  591 */       boolean oldValue = Float2BooleanOpenHashMap.this.value[this.index];
/*  592 */       Float2BooleanOpenHashMap.this.value[this.index] = v;
/*  593 */       return oldValue;
/*      */     }
/*      */ 
/*      */     
/*      */     public FloatBooleanPair right(boolean v) {
/*  598 */       Float2BooleanOpenHashMap.this.value[this.index] = v;
/*  599 */       return this;
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
/*  610 */       return Float.valueOf(Float2BooleanOpenHashMap.this.key[this.index]);
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
/*  621 */       return Boolean.valueOf(Float2BooleanOpenHashMap.this.value[this.index]);
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
/*  632 */       return Boolean.valueOf(setValue(v.booleanValue()));
/*      */     }
/*      */ 
/*      */ 
/*      */     
/*      */     public boolean equals(Object o) {
/*  638 */       if (!(o instanceof Map.Entry)) return false; 
/*  639 */       Map.Entry<Float, Boolean> e = (Map.Entry<Float, Boolean>)o;
/*  640 */       return (Float.floatToIntBits(Float2BooleanOpenHashMap.this.key[this.index]) == Float.floatToIntBits(((Float)e.getKey()).floatValue()) && Float2BooleanOpenHashMap.this.value[this.index] == ((Boolean)e.getValue()).booleanValue());
/*      */     }
/*      */ 
/*      */     
/*      */     public int hashCode() {
/*  645 */       return HashCommon.float2int(Float2BooleanOpenHashMap.this.key[this.index]) ^ (Float2BooleanOpenHashMap.this.value[this.index] ? 1231 : 1237);
/*      */     }
/*      */ 
/*      */     
/*      */     public String toString() {
/*  650 */       return Float2BooleanOpenHashMap.this.key[this.index] + "=>" + Float2BooleanOpenHashMap.this.value[this.index];
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
/*  661 */     int pos = Float2BooleanOpenHashMap.this.n;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  667 */     int last = -1;
/*      */     
/*  669 */     int c = Float2BooleanOpenHashMap.this.size;
/*      */     
/*  671 */     boolean mustReturnNullKey = Float2BooleanOpenHashMap.this.containsNullKey;
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
/*  682 */       return (this.c != 0);
/*      */     }
/*      */     
/*      */     public int nextEntry() {
/*  686 */       if (!hasNext()) throw new NoSuchElementException(); 
/*  687 */       this.c--;
/*  688 */       if (this.mustReturnNullKey) {
/*  689 */         this.mustReturnNullKey = false;
/*  690 */         return this.last = Float2BooleanOpenHashMap.this.n;
/*      */       } 
/*  692 */       float[] key = Float2BooleanOpenHashMap.this.key;
/*      */       while (true) {
/*  694 */         if (--this.pos < 0) {
/*      */           
/*  696 */           this.last = Integer.MIN_VALUE;
/*  697 */           float k = this.wrapped.getFloat(-this.pos - 1);
/*  698 */           int p = HashCommon.mix(HashCommon.float2int(k)) & Float2BooleanOpenHashMap.this.mask;
/*  699 */           for (; Float.floatToIntBits(k) != Float.floatToIntBits(key[p]); p = p + 1 & Float2BooleanOpenHashMap.this.mask);
/*  700 */           return p;
/*      */         } 
/*  702 */         if (Float.floatToIntBits(key[this.pos]) != 0) return this.last = this.pos; 
/*      */       } 
/*      */     }
/*      */     
/*      */     public void forEachRemaining(ConsumerType action) {
/*  707 */       if (this.mustReturnNullKey) {
/*  708 */         this.mustReturnNullKey = false;
/*  709 */         acceptOnIndex(action, this.last = Float2BooleanOpenHashMap.this.n);
/*  710 */         this.c--;
/*      */       } 
/*  712 */       float[] key = Float2BooleanOpenHashMap.this.key;
/*  713 */       while (this.c != 0) {
/*  714 */         if (--this.pos < 0) {
/*      */           
/*  716 */           this.last = Integer.MIN_VALUE;
/*  717 */           float k = this.wrapped.getFloat(-this.pos - 1);
/*  718 */           int p = HashCommon.mix(HashCommon.float2int(k)) & Float2BooleanOpenHashMap.this.mask;
/*  719 */           for (; Float.floatToIntBits(k) != Float.floatToIntBits(key[p]); p = p + 1 & Float2BooleanOpenHashMap.this.mask);
/*  720 */           acceptOnIndex(action, p);
/*  721 */           this.c--; continue;
/*  722 */         }  if (Float.floatToIntBits(key[this.pos]) != 0) {
/*  723 */           acceptOnIndex(action, this.last = this.pos);
/*  724 */           this.c--;
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
/*  739 */       float[] key = Float2BooleanOpenHashMap.this.key; while (true) {
/*      */         float curr; int last;
/*  741 */         pos = (last = pos) + 1 & Float2BooleanOpenHashMap.this.mask;
/*      */         while (true) {
/*  743 */           if (Float.floatToIntBits(curr = key[pos]) == 0) {
/*  744 */             key[last] = 0.0F;
/*      */             return;
/*      */           } 
/*  747 */           int slot = HashCommon.mix(HashCommon.float2int(curr)) & Float2BooleanOpenHashMap.this.mask;
/*  748 */           if ((last <= pos) ? (last >= slot || slot > pos) : (last >= slot && slot > pos))
/*  749 */             break;  pos = pos + 1 & Float2BooleanOpenHashMap.this.mask;
/*      */         } 
/*  751 */         if (pos < last) {
/*  752 */           if (this.wrapped == null) this.wrapped = new FloatArrayList(2); 
/*  753 */           this.wrapped.add(key[pos]);
/*      */         } 
/*  755 */         key[last] = curr;
/*  756 */         Float2BooleanOpenHashMap.this.value[last] = Float2BooleanOpenHashMap.this.value[pos];
/*      */       } 
/*      */     }
/*      */     
/*      */     public void remove() {
/*  761 */       if (this.last == -1) throw new IllegalStateException(); 
/*  762 */       if (this.last == Float2BooleanOpenHashMap.this.n)
/*  763 */       { Float2BooleanOpenHashMap.this.containsNullKey = false; }
/*  764 */       else if (this.pos >= 0) { shiftKeys(this.last); }
/*      */       else
/*      */       
/*  767 */       { Float2BooleanOpenHashMap.this.remove(this.wrapped.getFloat(-this.pos - 1));
/*  768 */         this.last = -1;
/*      */         return; }
/*      */       
/*  771 */       Float2BooleanOpenHashMap.this.size--;
/*  772 */       this.last = -1;
/*      */     }
/*      */ 
/*      */     
/*      */     public int skip(int n) {
/*  777 */       int i = n;
/*  778 */       for (; i-- != 0 && hasNext(); nextEntry());
/*  779 */       return n - i - 1;
/*      */     }
/*      */     
/*      */     private MapIterator() {}
/*      */     
/*      */     abstract void acceptOnIndex(ConsumerType param1ConsumerType, int param1Int);
/*      */   }
/*      */   
/*      */   private final class EntryIterator extends MapIterator<Consumer<? super Float2BooleanMap.Entry>> implements ObjectIterator<Float2BooleanMap.Entry> { public Float2BooleanOpenHashMap.MapEntry next() {
/*  788 */       return this.entry = new Float2BooleanOpenHashMap.MapEntry(nextEntry());
/*      */     }
/*      */     private Float2BooleanOpenHashMap.MapEntry entry;
/*      */     private EntryIterator() {}
/*      */     
/*      */     final void acceptOnIndex(Consumer<? super Float2BooleanMap.Entry> action, int index) {
/*  794 */       action.accept(this.entry = new Float2BooleanOpenHashMap.MapEntry(index));
/*      */     }
/*      */ 
/*      */     
/*      */     public void remove() {
/*  799 */       super.remove();
/*  800 */       this.entry.index = -1;
/*      */     } }
/*      */   
/*      */   private final class FastEntryIterator extends MapIterator<Consumer<? super Float2BooleanMap.Entry>> implements ObjectIterator<Float2BooleanMap.Entry> {
/*      */     private FastEntryIterator() {
/*  805 */       this.entry = new Float2BooleanOpenHashMap.MapEntry();
/*      */     }
/*      */     private final Float2BooleanOpenHashMap.MapEntry entry;
/*      */     public Float2BooleanOpenHashMap.MapEntry next() {
/*  809 */       this.entry.index = nextEntry();
/*  810 */       return this.entry;
/*      */     }
/*      */ 
/*      */ 
/*      */     
/*      */     final void acceptOnIndex(Consumer<? super Float2BooleanMap.Entry> action, int index) {
/*  816 */       this.entry.index = index;
/*  817 */       action.accept(this.entry);
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private abstract class MapSpliterator<ConsumerType, SplitType extends MapSpliterator<ConsumerType, SplitType>>
/*      */   {
/*  826 */     int pos = 0;
/*      */     
/*  828 */     int max = Float2BooleanOpenHashMap.this.n;
/*      */     
/*  830 */     int c = 0;
/*      */     
/*  832 */     boolean mustReturnNull = Float2BooleanOpenHashMap.this.containsNullKey;
/*      */     
/*      */     boolean hasSplit = false;
/*      */     
/*      */     MapSpliterator() {}
/*      */     
/*      */     MapSpliterator(int pos, int max, boolean mustReturnNull, boolean hasSplit) {
/*  839 */       this.pos = pos;
/*  840 */       this.max = max;
/*  841 */       this.mustReturnNull = mustReturnNull;
/*  842 */       this.hasSplit = hasSplit;
/*      */     }
/*      */     
/*      */     abstract void acceptOnIndex(ConsumerType param1ConsumerType, int param1Int);
/*      */     
/*      */     abstract SplitType makeForSplit(int param1Int1, int param1Int2, boolean param1Boolean);
/*      */     
/*      */     public boolean tryAdvance(ConsumerType action) {
/*  850 */       if (this.mustReturnNull) {
/*  851 */         this.mustReturnNull = false;
/*  852 */         this.c++;
/*  853 */         acceptOnIndex(action, Float2BooleanOpenHashMap.this.n);
/*  854 */         return true;
/*      */       } 
/*  856 */       float[] key = Float2BooleanOpenHashMap.this.key;
/*  857 */       while (this.pos < this.max) {
/*  858 */         if (Float.floatToIntBits(key[this.pos]) != 0) {
/*  859 */           this.c++;
/*  860 */           acceptOnIndex(action, this.pos++);
/*  861 */           return true;
/*      */         } 
/*  863 */         this.pos++;
/*      */       } 
/*  865 */       return false;
/*      */     }
/*      */     
/*      */     public void forEachRemaining(ConsumerType action) {
/*  869 */       if (this.mustReturnNull) {
/*  870 */         this.mustReturnNull = false;
/*  871 */         this.c++;
/*  872 */         acceptOnIndex(action, Float2BooleanOpenHashMap.this.n);
/*      */       } 
/*  874 */       float[] key = Float2BooleanOpenHashMap.this.key;
/*  875 */       while (this.pos < this.max) {
/*  876 */         if (Float.floatToIntBits(key[this.pos]) != 0) {
/*  877 */           acceptOnIndex(action, this.pos);
/*  878 */           this.c++;
/*      */         } 
/*  880 */         this.pos++;
/*      */       } 
/*      */     }
/*      */     
/*      */     public long estimateSize() {
/*  885 */       if (!this.hasSplit)
/*      */       {
/*  887 */         return (Float2BooleanOpenHashMap.this.size - this.c);
/*      */       }
/*      */ 
/*      */ 
/*      */       
/*  892 */       return Math.min((Float2BooleanOpenHashMap.this.size - this.c), (long)(Float2BooleanOpenHashMap.this.realSize() / Float2BooleanOpenHashMap.this.n * (this.max - this.pos)) + (this.mustReturnNull ? 1L : 0L));
/*      */     }
/*      */ 
/*      */     
/*      */     public SplitType trySplit() {
/*  897 */       if (this.pos >= this.max - 1) return null; 
/*  898 */       int retLen = this.max - this.pos >> 1;
/*  899 */       if (retLen <= 1) return null; 
/*  900 */       int myNewPos = this.pos + retLen;
/*  901 */       int retPos = this.pos;
/*  902 */       int retMax = myNewPos;
/*      */ 
/*      */ 
/*      */       
/*  906 */       SplitType split = makeForSplit(retPos, retMax, this.mustReturnNull);
/*  907 */       this.pos = myNewPos;
/*  908 */       this.mustReturnNull = false;
/*  909 */       this.hasSplit = true;
/*  910 */       return split;
/*      */     }
/*      */     
/*      */     public long skip(long n) {
/*  914 */       if (n < 0L) throw new IllegalArgumentException("Argument must be nonnegative: " + n); 
/*  915 */       if (n == 0L) return 0L; 
/*  916 */       long skipped = 0L;
/*  917 */       if (this.mustReturnNull) {
/*  918 */         this.mustReturnNull = false;
/*  919 */         skipped++;
/*  920 */         n--;
/*      */       } 
/*  922 */       float[] key = Float2BooleanOpenHashMap.this.key;
/*  923 */       while (this.pos < this.max && n > 0L) {
/*  924 */         if (Float.floatToIntBits(key[this.pos++]) != 0) {
/*  925 */           skipped++;
/*  926 */           n--;
/*      */         } 
/*      */       } 
/*  929 */       return skipped;
/*      */     }
/*      */   }
/*      */   
/*      */   private final class EntrySpliterator
/*      */     extends MapSpliterator<Consumer<? super Float2BooleanMap.Entry>, EntrySpliterator> implements ObjectSpliterator<Float2BooleanMap.Entry> {
/*      */     private static final int POST_SPLIT_CHARACTERISTICS = 1;
/*      */     
/*      */     EntrySpliterator() {}
/*      */     
/*      */     EntrySpliterator(int pos, int max, boolean mustReturnNull, boolean hasSplit) {
/*  940 */       super(pos, max, mustReturnNull, hasSplit);
/*      */     }
/*      */ 
/*      */     
/*      */     public int characteristics() {
/*  945 */       return this.hasSplit ? 1 : 65;
/*      */     }
/*      */ 
/*      */     
/*      */     final void acceptOnIndex(Consumer<? super Float2BooleanMap.Entry> action, int index) {
/*  950 */       action.accept(new Float2BooleanOpenHashMap.MapEntry(index));
/*      */     }
/*      */ 
/*      */     
/*      */     final EntrySpliterator makeForSplit(int pos, int max, boolean mustReturnNull) {
/*  955 */       return new EntrySpliterator(pos, max, mustReturnNull, true);
/*      */     } }
/*      */   
/*      */   private final class MapEntrySet extends AbstractObjectSet<Float2BooleanMap.Entry> implements Float2BooleanMap.FastEntrySet {
/*      */     private MapEntrySet() {}
/*      */     
/*      */     public ObjectIterator<Float2BooleanMap.Entry> iterator() {
/*  962 */       return new Float2BooleanOpenHashMap.EntryIterator();
/*      */     }
/*      */ 
/*      */     
/*      */     public ObjectIterator<Float2BooleanMap.Entry> fastIterator() {
/*  967 */       return new Float2BooleanOpenHashMap.FastEntryIterator();
/*      */     }
/*      */ 
/*      */     
/*      */     public ObjectSpliterator<Float2BooleanMap.Entry> spliterator() {
/*  972 */       return new Float2BooleanOpenHashMap.EntrySpliterator();
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public boolean contains(Object o) {
/*  979 */       if (!(o instanceof Map.Entry)) return false; 
/*  980 */       Map.Entry<?, ?> e = (Map.Entry<?, ?>)o;
/*  981 */       if (e.getKey() == null || !(e.getKey() instanceof Float)) return false; 
/*  982 */       if (e.getValue() == null || !(e.getValue() instanceof Boolean)) return false; 
/*  983 */       float k = ((Float)e.getKey()).floatValue();
/*  984 */       boolean v = ((Boolean)e.getValue()).booleanValue();
/*  985 */       if (Float.floatToIntBits(k) == 0) return (Float2BooleanOpenHashMap.this.containsNullKey && Float2BooleanOpenHashMap.this.value[Float2BooleanOpenHashMap.this.n] == v);
/*      */       
/*  987 */       float[] key = Float2BooleanOpenHashMap.this.key;
/*      */       float curr;
/*      */       int pos;
/*  990 */       if (Float.floatToIntBits(curr = key[pos = HashCommon.mix(HashCommon.float2int(k)) & Float2BooleanOpenHashMap.this.mask]) == 0) return false; 
/*  991 */       if (Float.floatToIntBits(k) == Float.floatToIntBits(curr)) return (Float2BooleanOpenHashMap.this.value[pos] == v);
/*      */       
/*      */       while (true) {
/*  994 */         if (Float.floatToIntBits(curr = key[pos = pos + 1 & Float2BooleanOpenHashMap.this.mask]) == 0) return false; 
/*  995 */         if (Float.floatToIntBits(k) == Float.floatToIntBits(curr)) return (Float2BooleanOpenHashMap.this.value[pos] == v);
/*      */       
/*      */       } 
/*      */     }
/*      */ 
/*      */     
/*      */     public boolean remove(Object o) {
/* 1002 */       if (!(o instanceof Map.Entry)) return false; 
/* 1003 */       Map.Entry<?, ?> e = (Map.Entry<?, ?>)o;
/* 1004 */       if (e.getKey() == null || !(e.getKey() instanceof Float)) return false; 
/* 1005 */       if (e.getValue() == null || !(e.getValue() instanceof Boolean)) return false; 
/* 1006 */       float k = ((Float)e.getKey()).floatValue();
/* 1007 */       boolean v = ((Boolean)e.getValue()).booleanValue();
/* 1008 */       if (Float.floatToIntBits(k) == 0) {
/* 1009 */         if (Float2BooleanOpenHashMap.this.containsNullKey && Float2BooleanOpenHashMap.this.value[Float2BooleanOpenHashMap.this.n] == v) {
/* 1010 */           Float2BooleanOpenHashMap.this.removeNullEntry();
/* 1011 */           return true;
/*      */         } 
/* 1013 */         return false;
/*      */       } 
/*      */       
/* 1016 */       float[] key = Float2BooleanOpenHashMap.this.key;
/*      */       float curr;
/*      */       int pos;
/* 1019 */       if (Float.floatToIntBits(curr = key[pos = HashCommon.mix(HashCommon.float2int(k)) & Float2BooleanOpenHashMap.this.mask]) == 0) return false; 
/* 1020 */       if (Float.floatToIntBits(curr) == Float.floatToIntBits(k)) {
/* 1021 */         if (Float2BooleanOpenHashMap.this.value[pos] == v) {
/* 1022 */           Float2BooleanOpenHashMap.this.removeEntry(pos);
/* 1023 */           return true;
/*      */         } 
/* 1025 */         return false;
/*      */       } 
/*      */       while (true) {
/* 1028 */         if (Float.floatToIntBits(curr = key[pos = pos + 1 & Float2BooleanOpenHashMap.this.mask]) == 0) return false; 
/* 1029 */         if (Float.floatToIntBits(curr) == Float.floatToIntBits(k) && 
/* 1030 */           Float2BooleanOpenHashMap.this.value[pos] == v) {
/* 1031 */           Float2BooleanOpenHashMap.this.removeEntry(pos);
/* 1032 */           return true;
/*      */         } 
/*      */       } 
/*      */     }
/*      */ 
/*      */ 
/*      */     
/*      */     public int size() {
/* 1040 */       return Float2BooleanOpenHashMap.this.size;
/*      */     }
/*      */ 
/*      */     
/*      */     public void clear() {
/* 1045 */       Float2BooleanOpenHashMap.this.clear();
/*      */     }
/*      */ 
/*      */ 
/*      */     
/*      */     public void forEach(Consumer<? super Float2BooleanMap.Entry> consumer) {
/* 1051 */       if (Float2BooleanOpenHashMap.this.containsNullKey) consumer.accept(new Float2BooleanOpenHashMap.MapEntry(Float2BooleanOpenHashMap.this.n)); 
/* 1052 */       for (int pos = Float2BooleanOpenHashMap.this.n; pos-- != 0;) { if (Float.floatToIntBits(Float2BooleanOpenHashMap.this.key[pos]) != 0) consumer.accept(new Float2BooleanOpenHashMap.MapEntry(pos));
/*      */          }
/*      */     
/*      */     }
/*      */     
/*      */     public void fastForEach(Consumer<? super Float2BooleanMap.Entry> consumer) {
/* 1058 */       Float2BooleanOpenHashMap.MapEntry entry = new Float2BooleanOpenHashMap.MapEntry();
/* 1059 */       if (Float2BooleanOpenHashMap.this.containsNullKey) {
/* 1060 */         entry.index = Float2BooleanOpenHashMap.this.n;
/* 1061 */         consumer.accept(entry);
/*      */       } 
/* 1063 */       for (int pos = Float2BooleanOpenHashMap.this.n; pos-- != 0;) { if (Float.floatToIntBits(Float2BooleanOpenHashMap.this.key[pos]) != 0) {
/* 1064 */           entry.index = pos;
/* 1065 */           consumer.accept(entry);
/*      */         }  }
/*      */     
/*      */     }
/*      */   }
/*      */   
/*      */   public Float2BooleanMap.FastEntrySet float2BooleanEntrySet() {
/* 1072 */     if (this.entries == null) this.entries = new MapEntrySet(); 
/* 1073 */     return this.entries;
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
/* 1094 */       action.accept(Float2BooleanOpenHashMap.this.key[index]);
/*      */     }
/*      */ 
/*      */     
/*      */     public float nextFloat() {
/* 1099 */       return Float2BooleanOpenHashMap.this.key[nextEntry()];
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
/* 1110 */       super(pos, max, mustReturnNull, hasSplit);
/*      */     }
/*      */ 
/*      */     
/*      */     public int characteristics() {
/* 1115 */       return this.hasSplit ? 257 : 321;
/*      */     }
/*      */ 
/*      */     
/*      */     final void acceptOnIndex(FloatConsumer action, int index) {
/* 1120 */       action.accept(Float2BooleanOpenHashMap.this.key[index]);
/*      */     }
/*      */ 
/*      */     
/*      */     final KeySpliterator makeForSplit(int pos, int max, boolean mustReturnNull) {
/* 1125 */       return new KeySpliterator(pos, max, mustReturnNull, true);
/*      */     } }
/*      */   
/*      */   private final class KeySet extends AbstractFloatSet {
/*      */     private KeySet() {}
/*      */     
/*      */     public FloatIterator iterator() {
/* 1132 */       return new Float2BooleanOpenHashMap.KeyIterator();
/*      */     }
/*      */ 
/*      */     
/*      */     public FloatSpliterator spliterator() {
/* 1137 */       return new Float2BooleanOpenHashMap.KeySpliterator();
/*      */     }
/*      */ 
/*      */ 
/*      */     
/*      */     public void forEach(FloatConsumer consumer) {
/* 1143 */       if (Float2BooleanOpenHashMap.this.containsNullKey) consumer.accept(Float2BooleanOpenHashMap.this.key[Float2BooleanOpenHashMap.this.n]); 
/* 1144 */       for (int pos = Float2BooleanOpenHashMap.this.n; pos-- != 0; ) {
/* 1145 */         float k = Float2BooleanOpenHashMap.this.key[pos];
/* 1146 */         if (Float.floatToIntBits(k) != 0) consumer.accept(k);
/*      */       
/*      */       } 
/*      */     }
/*      */     
/*      */     public int size() {
/* 1152 */       return Float2BooleanOpenHashMap.this.size;
/*      */     }
/*      */ 
/*      */     
/*      */     public boolean contains(float k) {
/* 1157 */       return Float2BooleanOpenHashMap.this.containsKey(k);
/*      */     }
/*      */ 
/*      */     
/*      */     public boolean remove(float k) {
/* 1162 */       int oldSize = Float2BooleanOpenHashMap.this.size;
/* 1163 */       Float2BooleanOpenHashMap.this.remove(k);
/* 1164 */       return (Float2BooleanOpenHashMap.this.size != oldSize);
/*      */     }
/*      */ 
/*      */     
/*      */     public void clear() {
/* 1169 */       Float2BooleanOpenHashMap.this.clear();
/*      */     }
/*      */   }
/*      */ 
/*      */   
/*      */   public FloatSet keySet() {
/* 1175 */     if (this.keys == null) this.keys = new KeySet(); 
/* 1176 */     return this.keys;
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
/* 1197 */       action.accept(Float2BooleanOpenHashMap.this.value[index]);
/*      */     }
/*      */ 
/*      */     
/*      */     public boolean nextBoolean() {
/* 1202 */       return Float2BooleanOpenHashMap.this.value[nextEntry()];
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
/* 1213 */       super(pos, max, mustReturnNull, hasSplit);
/*      */     }
/*      */ 
/*      */     
/*      */     public int characteristics() {
/* 1218 */       return this.hasSplit ? 256 : 320;
/*      */     }
/*      */ 
/*      */     
/*      */     final void acceptOnIndex(BooleanConsumer action, int index) {
/* 1223 */       action.accept(Float2BooleanOpenHashMap.this.value[index]);
/*      */     }
/*      */ 
/*      */     
/*      */     final ValueSpliterator makeForSplit(int pos, int max, boolean mustReturnNull) {
/* 1228 */       return new ValueSpliterator(pos, max, mustReturnNull, true);
/*      */     }
/*      */   }
/*      */ 
/*      */   
/*      */   public BooleanCollection values() {
/* 1234 */     if (this.values == null) this.values = (BooleanCollection)new AbstractBooleanCollection()
/*      */         {
/*      */           public BooleanIterator iterator() {
/* 1237 */             return new Float2BooleanOpenHashMap.ValueIterator();
/*      */           }
/*      */ 
/*      */           
/*      */           public BooleanSpliterator spliterator() {
/* 1242 */             return new Float2BooleanOpenHashMap.ValueSpliterator();
/*      */           }
/*      */ 
/*      */ 
/*      */           
/*      */           public void forEach(BooleanConsumer consumer) {
/* 1248 */             if (Float2BooleanOpenHashMap.this.containsNullKey) consumer.accept(Float2BooleanOpenHashMap.this.value[Float2BooleanOpenHashMap.this.n]); 
/* 1249 */             for (int pos = Float2BooleanOpenHashMap.this.n; pos-- != 0;) { if (Float.floatToIntBits(Float2BooleanOpenHashMap.this.key[pos]) != 0) consumer.accept(Float2BooleanOpenHashMap.this.value[pos]);  }
/*      */           
/*      */           }
/*      */           
/*      */           public int size() {
/* 1254 */             return Float2BooleanOpenHashMap.this.size;
/*      */           }
/*      */ 
/*      */           
/*      */           public boolean contains(boolean v) {
/* 1259 */             return Float2BooleanOpenHashMap.this.containsValue(v);
/*      */           }
/*      */ 
/*      */           
/*      */           public void clear() {
/* 1264 */             Float2BooleanOpenHashMap.this.clear();
/*      */           }
/*      */         }; 
/* 1267 */     return this.values;
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
/* 1284 */     return trim(this.size);
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
/* 1306 */     int l = HashCommon.nextPowerOfTwo((int)Math.ceil((n / this.f)));
/* 1307 */     if (l >= this.n || this.size > HashCommon.maxFill(l, this.f)) return true; 
/*      */     try {
/* 1309 */       rehash(l);
/* 1310 */     } catch (OutOfMemoryError cantDoIt) {
/* 1311 */       return false;
/*      */     } 
/* 1313 */     return true;
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
/* 1328 */     float[] key = this.key;
/* 1329 */     boolean[] value = this.value;
/* 1330 */     int mask = newN - 1;
/* 1331 */     float[] newKey = new float[newN + 1];
/* 1332 */     boolean[] newValue = new boolean[newN + 1];
/* 1333 */     int i = this.n;
/* 1334 */     for (int j = realSize(); j-- != 0; ) {
/* 1335 */       while (Float.floatToIntBits(key[--i]) == 0); int pos;
/* 1336 */       if (Float.floatToIntBits(newKey[pos = HashCommon.mix(HashCommon.float2int(key[i])) & mask]) != 0) while (Float.floatToIntBits(newKey[pos = pos + 1 & mask]) != 0); 
/* 1337 */       newKey[pos] = key[i];
/* 1338 */       newValue[pos] = value[i];
/*      */     } 
/* 1340 */     newValue[newN] = value[this.n];
/* 1341 */     this.n = newN;
/* 1342 */     this.mask = mask;
/* 1343 */     this.maxFill = HashCommon.maxFill(this.n, this.f);
/* 1344 */     this.key = newKey;
/* 1345 */     this.value = newValue;
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
/*      */   public Float2BooleanOpenHashMap clone() {
/*      */     Float2BooleanOpenHashMap c;
/*      */     try {
/* 1362 */       c = (Float2BooleanOpenHashMap)super.clone();
/* 1363 */     } catch (CloneNotSupportedException cantHappen) {
/* 1364 */       throw new InternalError();
/*      */     } 
/* 1366 */     c.keys = null;
/* 1367 */     c.values = null;
/* 1368 */     c.entries = null;
/* 1369 */     c.containsNullKey = this.containsNullKey;
/* 1370 */     c.key = (float[])this.key.clone();
/* 1371 */     c.value = (boolean[])this.value.clone();
/* 1372 */     return c;
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
/* 1386 */     int h = 0;
/* 1387 */     for (int j = realSize(), i = 0, t = 0; j-- != 0; ) {
/* 1388 */       for (; Float.floatToIntBits(this.key[i]) == 0; i++);
/* 1389 */       t = HashCommon.float2int(this.key[i]);
/* 1390 */       t ^= this.value[i] ? 1231 : 1237;
/* 1391 */       h += t;
/* 1392 */       i++;
/*      */     } 
/*      */     
/* 1395 */     if (this.containsNullKey) h += this.value[this.n] ? 1231 : 1237; 
/* 1396 */     return h;
/*      */   }
/*      */   
/*      */   private void writeObject(ObjectOutputStream s) throws IOException {
/* 1400 */     float[] key = this.key;
/* 1401 */     boolean[] value = this.value;
/* 1402 */     EntryIterator i = new EntryIterator();
/* 1403 */     s.defaultWriteObject();
/* 1404 */     for (int j = this.size; j-- != 0; ) {
/* 1405 */       int e = i.nextEntry();
/* 1406 */       s.writeFloat(key[e]);
/* 1407 */       s.writeBoolean(value[e]);
/*      */     } 
/*      */   }
/*      */   
/*      */   private void readObject(ObjectInputStream s) throws IOException, ClassNotFoundException {
/* 1412 */     s.defaultReadObject();
/* 1413 */     this.n = HashCommon.arraySize(this.size, this.f);
/* 1414 */     this.maxFill = HashCommon.maxFill(this.n, this.f);
/* 1415 */     this.mask = this.n - 1;
/* 1416 */     float[] key = this.key = new float[this.n + 1];
/* 1417 */     boolean[] value = this.value = new boolean[this.n + 1];
/*      */ 
/*      */     
/* 1420 */     for (int i = this.size; i-- != 0; ) {
/* 1421 */       int pos; float k = s.readFloat();
/* 1422 */       boolean v = s.readBoolean();
/* 1423 */       if (Float.floatToIntBits(k) == 0) {
/* 1424 */         pos = this.n;
/* 1425 */         this.containsNullKey = true;
/*      */       } else {
/* 1427 */         pos = HashCommon.mix(HashCommon.float2int(k)) & this.mask;
/* 1428 */         for (; Float.floatToIntBits(key[pos]) != 0; pos = pos + 1 & this.mask);
/*      */       } 
/* 1430 */       key[pos] = k;
/* 1431 */       value[pos] = v;
/*      */     } 
/*      */   }
/*      */   
/*      */   private void checkTable() {}
/*      */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\i\\unimi\dsi\fastutil\floats\Float2BooleanOpenHashMap.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */