/*      */ package it.unimi.dsi.fastutil.floats;
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
/*      */ import java.util.function.DoubleFunction;
/*      */ import java.util.function.DoubleUnaryOperator;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ public class Float2FloatOpenHashMap
/*      */   extends AbstractFloat2FloatMap
/*      */   implements Serializable, Cloneable, Hash
/*      */ {
/*      */   private static final long serialVersionUID = 0L;
/*      */   private static final boolean ASSERTS = false;
/*      */   protected transient float[] key;
/*      */   protected transient float[] value;
/*      */   protected transient int mask;
/*      */   protected transient boolean containsNullKey;
/*      */   protected transient int n;
/*      */   protected transient int maxFill;
/*      */   protected final transient int minN;
/*      */   protected int size;
/*      */   protected final float f;
/*      */   protected transient Float2FloatMap.FastEntrySet entries;
/*      */   protected transient FloatSet keys;
/*      */   protected transient FloatCollection values;
/*      */   
/*      */   public Float2FloatOpenHashMap(int expected, float f) {
/*   94 */     if (f <= 0.0F || f >= 1.0F) throw new IllegalArgumentException("Load factor must be greater than 0 and smaller than 1"); 
/*   95 */     if (expected < 0) throw new IllegalArgumentException("The expected number of elements must be nonnegative"); 
/*   96 */     this.f = f;
/*   97 */     this.minN = this.n = HashCommon.arraySize(expected, f);
/*   98 */     this.mask = this.n - 1;
/*   99 */     this.maxFill = HashCommon.maxFill(this.n, f);
/*  100 */     this.key = new float[this.n + 1];
/*  101 */     this.value = new float[this.n + 1];
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Float2FloatOpenHashMap(int expected) {
/*  110 */     this(expected, 0.75F);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Float2FloatOpenHashMap() {
/*  118 */     this(16, 0.75F);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Float2FloatOpenHashMap(Map<? extends Float, ? extends Float> m, float f) {
/*  128 */     this(m.size(), f);
/*  129 */     putAll(m);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Float2FloatOpenHashMap(Map<? extends Float, ? extends Float> m) {
/*  138 */     this(m, 0.75F);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Float2FloatOpenHashMap(Float2FloatMap m, float f) {
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
/*      */   public Float2FloatOpenHashMap(Float2FloatMap m) {
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
/*      */   public Float2FloatOpenHashMap(float[] k, float[] v, float f) {
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
/*      */   public Float2FloatOpenHashMap(float[] k, float[] v) {
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
/*      */   private float removeEntry(int pos) {
/*  209 */     float oldValue = this.value[pos];
/*  210 */     this.size--;
/*  211 */     shiftKeys(pos);
/*  212 */     if (this.n > this.minN && this.size < this.maxFill / 4 && this.n > 16) rehash(this.n / 2); 
/*  213 */     return oldValue;
/*      */   }
/*      */   
/*      */   private float removeNullEntry() {
/*  217 */     this.containsNullKey = false;
/*  218 */     float oldValue = this.value[this.n];
/*  219 */     this.size--;
/*  220 */     if (this.n > this.minN && this.size < this.maxFill / 4 && this.n > 16) rehash(this.n / 2); 
/*  221 */     return oldValue;
/*      */   }
/*      */ 
/*      */   
/*      */   public void putAll(Map<? extends Float, ? extends Float> m) {
/*  226 */     if (this.f <= 0.5D) { ensureCapacity(m.size()); }
/*  227 */     else { tryCapacity((size() + m.size())); }
/*      */     
/*  229 */     super.putAll(m);
/*      */   }
/*      */   
/*      */   private int find(float k) {
/*  233 */     if (Float.floatToIntBits(k) == 0) return this.containsNullKey ? this.n : -(this.n + 1);
/*      */     
/*  235 */     float[] key = this.key;
/*      */     float curr;
/*      */     int pos;
/*  238 */     if (Float.floatToIntBits(curr = key[pos = HashCommon.mix(HashCommon.float2int(k)) & this.mask]) == 0) return -(pos + 1); 
/*  239 */     if (Float.floatToIntBits(k) == Float.floatToIntBits(curr)) return pos;
/*      */     
/*      */     while (true) {
/*  242 */       if (Float.floatToIntBits(curr = key[pos = pos + 1 & this.mask]) == 0) return -(pos + 1); 
/*  243 */       if (Float.floatToIntBits(k) == Float.floatToIntBits(curr)) return pos; 
/*      */     } 
/*      */   }
/*      */   
/*      */   private void insert(int pos, float k, float v) {
/*  248 */     if (pos == this.n) this.containsNullKey = true; 
/*  249 */     this.key[pos] = k;
/*  250 */     this.value[pos] = v;
/*  251 */     if (this.size++ >= this.maxFill) rehash(HashCommon.arraySize(this.size + 1, this.f));
/*      */   
/*      */   }
/*      */ 
/*      */   
/*      */   public float put(float k, float v) {
/*  257 */     int pos = find(k);
/*  258 */     if (pos < 0) {
/*  259 */       insert(-pos - 1, k, v);
/*  260 */       return this.defRetValue;
/*      */     } 
/*  262 */     float oldValue = this.value[pos];
/*  263 */     this.value[pos] = v;
/*  264 */     return oldValue;
/*      */   }
/*      */   
/*      */   private float addToValue(int pos, float incr) {
/*  268 */     float oldValue = this.value[pos];
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
/*      */   public float addTo(float k, float incr) {
/*      */     int pos;
/*  288 */     if (Float.floatToIntBits(k) == 0) {
/*  289 */       if (this.containsNullKey) return addToValue(this.n, incr); 
/*  290 */       pos = this.n;
/*  291 */       this.containsNullKey = true;
/*      */     } else {
/*      */       
/*  294 */       float[] key = this.key;
/*      */       float curr;
/*  296 */       if (Float.floatToIntBits(curr = key[pos = HashCommon.mix(HashCommon.float2int(k)) & this.mask]) != 0) {
/*  297 */         if (Float.floatToIntBits(curr) == Float.floatToIntBits(k)) return addToValue(pos, incr); 
/*  298 */         while (Float.floatToIntBits(curr = key[pos = pos + 1 & this.mask]) != 0) { if (Float.floatToIntBits(curr) == Float.floatToIntBits(k)) return addToValue(pos, incr);  }
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
/*  318 */     float[] key = this.key; while (true) {
/*      */       float curr; int last;
/*  320 */       pos = (last = pos) + 1 & this.mask;
/*      */       while (true) {
/*  322 */         if (Float.floatToIntBits(curr = key[pos]) == 0) {
/*  323 */           key[last] = 0.0F;
/*      */           return;
/*      */         } 
/*  326 */         int slot = HashCommon.mix(HashCommon.float2int(curr)) & this.mask;
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
/*      */   public float remove(float k) {
/*  338 */     if (Float.floatToIntBits(k) == 0) {
/*  339 */       if (this.containsNullKey) return removeNullEntry(); 
/*  340 */       return this.defRetValue;
/*      */     } 
/*      */     
/*  343 */     float[] key = this.key;
/*      */     float curr;
/*      */     int pos;
/*  346 */     if (Float.floatToIntBits(curr = key[pos = HashCommon.mix(HashCommon.float2int(k)) & this.mask]) == 0) return this.defRetValue; 
/*  347 */     if (Float.floatToIntBits(k) == Float.floatToIntBits(curr)) return removeEntry(pos); 
/*      */     while (true) {
/*  349 */       if (Float.floatToIntBits(curr = key[pos = pos + 1 & this.mask]) == 0) return this.defRetValue; 
/*  350 */       if (Float.floatToIntBits(k) == Float.floatToIntBits(curr)) return removeEntry(pos);
/*      */     
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   public float get(float k) {
/*  357 */     if (Float.floatToIntBits(k) == 0) return this.containsNullKey ? this.value[this.n] : this.defRetValue;
/*      */     
/*  359 */     float[] key = this.key;
/*      */     float curr;
/*      */     int pos;
/*  362 */     if (Float.floatToIntBits(curr = key[pos = HashCommon.mix(HashCommon.float2int(k)) & this.mask]) == 0) return this.defRetValue; 
/*  363 */     if (Float.floatToIntBits(k) == Float.floatToIntBits(curr)) return this.value[pos];
/*      */     
/*      */     while (true) {
/*  366 */       if (Float.floatToIntBits(curr = key[pos = pos + 1 & this.mask]) == 0) return this.defRetValue; 
/*  367 */       if (Float.floatToIntBits(k) == Float.floatToIntBits(curr)) return this.value[pos];
/*      */     
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   public boolean containsKey(float k) {
/*  374 */     if (Float.floatToIntBits(k) == 0) return this.containsNullKey;
/*      */     
/*  376 */     float[] key = this.key;
/*      */     float curr;
/*      */     int pos;
/*  379 */     if (Float.floatToIntBits(curr = key[pos = HashCommon.mix(HashCommon.float2int(k)) & this.mask]) == 0) return false; 
/*  380 */     if (Float.floatToIntBits(k) == Float.floatToIntBits(curr)) return true;
/*      */     
/*      */     while (true) {
/*  383 */       if (Float.floatToIntBits(curr = key[pos = pos + 1 & this.mask]) == 0) return false; 
/*  384 */       if (Float.floatToIntBits(k) == Float.floatToIntBits(curr)) return true;
/*      */     
/*      */     } 
/*      */   }
/*      */   
/*      */   public boolean containsValue(float v) {
/*  390 */     float[] value = this.value;
/*  391 */     float[] key = this.key;
/*  392 */     if (this.containsNullKey && Float.floatToIntBits(value[this.n]) == Float.floatToIntBits(v)) return true; 
/*  393 */     for (int i = this.n; i-- != 0;) { if (Float.floatToIntBits(key[i]) != 0 && Float.floatToIntBits(value[i]) == Float.floatToIntBits(v)) return true;  }
/*  394 */      return false;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public float getOrDefault(float k, float defaultValue) {
/*  401 */     if (Float.floatToIntBits(k) == 0) return this.containsNullKey ? this.value[this.n] : defaultValue;
/*      */     
/*  403 */     float[] key = this.key;
/*      */     float curr;
/*      */     int pos;
/*  406 */     if (Float.floatToIntBits(curr = key[pos = HashCommon.mix(HashCommon.float2int(k)) & this.mask]) == 0) return defaultValue; 
/*  407 */     if (Float.floatToIntBits(k) == Float.floatToIntBits(curr)) return this.value[pos];
/*      */     
/*      */     while (true) {
/*  410 */       if (Float.floatToIntBits(curr = key[pos = pos + 1 & this.mask]) == 0) return defaultValue; 
/*  411 */       if (Float.floatToIntBits(k) == Float.floatToIntBits(curr)) return this.value[pos];
/*      */     
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   public float putIfAbsent(float k, float v) {
/*  418 */     int pos = find(k);
/*  419 */     if (pos >= 0) return this.value[pos]; 
/*  420 */     insert(-pos - 1, k, v);
/*  421 */     return this.defRetValue;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean remove(float k, float v) {
/*  428 */     if (Float.floatToIntBits(k) == 0) {
/*  429 */       if (this.containsNullKey && Float.floatToIntBits(v) == Float.floatToIntBits(this.value[this.n])) {
/*  430 */         removeNullEntry();
/*  431 */         return true;
/*      */       } 
/*  433 */       return false;
/*      */     } 
/*      */     
/*  436 */     float[] key = this.key;
/*      */     float curr;
/*      */     int pos;
/*  439 */     if (Float.floatToIntBits(curr = key[pos = HashCommon.mix(HashCommon.float2int(k)) & this.mask]) == 0) return false; 
/*  440 */     if (Float.floatToIntBits(k) == Float.floatToIntBits(curr) && Float.floatToIntBits(v) == Float.floatToIntBits(this.value[pos])) {
/*  441 */       removeEntry(pos);
/*  442 */       return true;
/*      */     } 
/*      */     while (true) {
/*  445 */       if (Float.floatToIntBits(curr = key[pos = pos + 1 & this.mask]) == 0) return false; 
/*  446 */       if (Float.floatToIntBits(k) == Float.floatToIntBits(curr) && Float.floatToIntBits(v) == Float.floatToIntBits(this.value[pos])) {
/*  447 */         removeEntry(pos);
/*  448 */         return true;
/*      */       } 
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean replace(float k, float oldValue, float v) {
/*  456 */     int pos = find(k);
/*  457 */     if (pos < 0 || Float.floatToIntBits(oldValue) != Float.floatToIntBits(this.value[pos])) return false; 
/*  458 */     this.value[pos] = v;
/*  459 */     return true;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public float replace(float k, float v) {
/*  465 */     int pos = find(k);
/*  466 */     if (pos < 0) return this.defRetValue; 
/*  467 */     float oldValue = this.value[pos];
/*  468 */     this.value[pos] = v;
/*  469 */     return oldValue;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public float computeIfAbsent(float k, DoubleUnaryOperator mappingFunction) {
/*  475 */     Objects.requireNonNull(mappingFunction);
/*  476 */     int pos = find(k);
/*  477 */     if (pos >= 0) return this.value[pos]; 
/*  478 */     float newValue = SafeMath.safeDoubleToFloat(mappingFunction.applyAsDouble(k));
/*  479 */     insert(-pos - 1, k, newValue);
/*  480 */     return newValue;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public float computeIfAbsent(float key, Float2FloatFunction mappingFunction) {
/*  486 */     Objects.requireNonNull(mappingFunction);
/*  487 */     int pos = find(key);
/*  488 */     if (pos >= 0) return this.value[pos]; 
/*  489 */     if (!mappingFunction.containsKey(key)) return this.defRetValue; 
/*  490 */     float newValue = mappingFunction.get(key);
/*  491 */     insert(-pos - 1, key, newValue);
/*  492 */     return newValue;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public float computeIfAbsentNullable(float k, DoubleFunction<? extends Float> mappingFunction) {
/*  498 */     Objects.requireNonNull(mappingFunction);
/*  499 */     int pos = find(k);
/*  500 */     if (pos >= 0) return this.value[pos]; 
/*  501 */     Float newValue = mappingFunction.apply(k);
/*  502 */     if (newValue == null) return this.defRetValue; 
/*  503 */     float v = newValue.floatValue();
/*  504 */     insert(-pos - 1, k, v);
/*  505 */     return v;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public float computeIfPresent(float k, BiFunction<? super Float, ? super Float, ? extends Float> remappingFunction) {
/*  511 */     Objects.requireNonNull(remappingFunction);
/*  512 */     int pos = find(k);
/*  513 */     if (pos < 0) return this.defRetValue; 
/*  514 */     Float newValue = remappingFunction.apply(Float.valueOf(k), Float.valueOf(this.value[pos]));
/*  515 */     if (newValue == null) {
/*  516 */       if (Float.floatToIntBits(k) == 0) { removeNullEntry(); }
/*  517 */       else { removeEntry(pos); }
/*  518 */        return this.defRetValue;
/*      */     } 
/*  520 */     this.value[pos] = newValue.floatValue(); return newValue.floatValue();
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public float compute(float k, BiFunction<? super Float, ? super Float, ? extends Float> remappingFunction) {
/*  526 */     Objects.requireNonNull(remappingFunction);
/*  527 */     int pos = find(k);
/*  528 */     Float newValue = remappingFunction.apply(Float.valueOf(k), (pos >= 0) ? Float.valueOf(this.value[pos]) : null);
/*  529 */     if (newValue == null) {
/*  530 */       if (pos >= 0)
/*  531 */         if (Float.floatToIntBits(k) == 0) { removeNullEntry(); }
/*  532 */         else { removeEntry(pos); }
/*      */          
/*  534 */       return this.defRetValue;
/*      */     } 
/*  536 */     float newVal = newValue.floatValue();
/*  537 */     if (pos < 0) {
/*  538 */       insert(-pos - 1, k, newVal);
/*  539 */       return newVal;
/*      */     } 
/*  541 */     this.value[pos] = newVal; return newVal;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public float merge(float k, float v, BiFunction<? super Float, ? super Float, ? extends Float> remappingFunction) {
/*  547 */     Objects.requireNonNull(remappingFunction);
/*      */     
/*  549 */     int pos = find(k);
/*  550 */     if (pos < 0) {
/*  551 */       if (pos < 0) { insert(-pos - 1, k, v); }
/*  552 */       else { this.value[pos] = v; }
/*  553 */        return v;
/*      */     } 
/*  555 */     Float newValue = remappingFunction.apply(Float.valueOf(this.value[pos]), Float.valueOf(v));
/*  556 */     if (newValue == null) {
/*  557 */       if (Float.floatToIntBits(k) == 0) { removeNullEntry(); }
/*  558 */       else { removeEntry(pos); }
/*  559 */        return this.defRetValue;
/*      */     } 
/*  561 */     this.value[pos] = newValue.floatValue(); return newValue.floatValue();
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
/*  575 */     Arrays.fill(this.key, 0.0F);
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
/*      */     implements Float2FloatMap.Entry, Map.Entry<Float, Float>, FloatFloatPair
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
/*      */     public float getFloatKey() {
/*  606 */       return Float2FloatOpenHashMap.this.key[this.index];
/*      */     }
/*      */ 
/*      */     
/*      */     public float leftFloat() {
/*  611 */       return Float2FloatOpenHashMap.this.key[this.index];
/*      */     }
/*      */ 
/*      */     
/*      */     public float getFloatValue() {
/*  616 */       return Float2FloatOpenHashMap.this.value[this.index];
/*      */     }
/*      */ 
/*      */     
/*      */     public float rightFloat() {
/*  621 */       return Float2FloatOpenHashMap.this.value[this.index];
/*      */     }
/*      */ 
/*      */     
/*      */     public float setValue(float v) {
/*  626 */       float oldValue = Float2FloatOpenHashMap.this.value[this.index];
/*  627 */       Float2FloatOpenHashMap.this.value[this.index] = v;
/*  628 */       return oldValue;
/*      */     }
/*      */ 
/*      */     
/*      */     public FloatFloatPair right(float v) {
/*  633 */       Float2FloatOpenHashMap.this.value[this.index] = v;
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
/*      */     public Float getKey() {
/*  645 */       return Float.valueOf(Float2FloatOpenHashMap.this.key[this.index]);
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     @Deprecated
/*      */     public Float getValue() {
/*  656 */       return Float.valueOf(Float2FloatOpenHashMap.this.value[this.index]);
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     @Deprecated
/*      */     public Float setValue(Float v) {
/*  667 */       return Float.valueOf(setValue(v.floatValue()));
/*      */     }
/*      */ 
/*      */ 
/*      */     
/*      */     public boolean equals(Object o) {
/*  673 */       if (!(o instanceof Map.Entry)) return false; 
/*  674 */       Map.Entry<Float, Float> e = (Map.Entry<Float, Float>)o;
/*  675 */       return (Float.floatToIntBits(Float2FloatOpenHashMap.this.key[this.index]) == Float.floatToIntBits(((Float)e.getKey()).floatValue()) && Float.floatToIntBits(Float2FloatOpenHashMap.this.value[this.index]) == Float.floatToIntBits(((Float)e.getValue()).floatValue()));
/*      */     }
/*      */ 
/*      */     
/*      */     public int hashCode() {
/*  680 */       return HashCommon.float2int(Float2FloatOpenHashMap.this.key[this.index]) ^ HashCommon.float2int(Float2FloatOpenHashMap.this.value[this.index]);
/*      */     }
/*      */ 
/*      */     
/*      */     public String toString() {
/*  685 */       return Float2FloatOpenHashMap.this.key[this.index] + "=>" + Float2FloatOpenHashMap.this.value[this.index];
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
/*  696 */     int pos = Float2FloatOpenHashMap.this.n;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  702 */     int last = -1;
/*      */     
/*  704 */     int c = Float2FloatOpenHashMap.this.size;
/*      */     
/*  706 */     boolean mustReturnNullKey = Float2FloatOpenHashMap.this.containsNullKey;
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
/*  717 */       return (this.c != 0);
/*      */     }
/*      */     
/*      */     public int nextEntry() {
/*  721 */       if (!hasNext()) throw new NoSuchElementException(); 
/*  722 */       this.c--;
/*  723 */       if (this.mustReturnNullKey) {
/*  724 */         this.mustReturnNullKey = false;
/*  725 */         return this.last = Float2FloatOpenHashMap.this.n;
/*      */       } 
/*  727 */       float[] key = Float2FloatOpenHashMap.this.key;
/*      */       while (true) {
/*  729 */         if (--this.pos < 0) {
/*      */           
/*  731 */           this.last = Integer.MIN_VALUE;
/*  732 */           float k = this.wrapped.getFloat(-this.pos - 1);
/*  733 */           int p = HashCommon.mix(HashCommon.float2int(k)) & Float2FloatOpenHashMap.this.mask;
/*  734 */           for (; Float.floatToIntBits(k) != Float.floatToIntBits(key[p]); p = p + 1 & Float2FloatOpenHashMap.this.mask);
/*  735 */           return p;
/*      */         } 
/*  737 */         if (Float.floatToIntBits(key[this.pos]) != 0) return this.last = this.pos; 
/*      */       } 
/*      */     }
/*      */     
/*      */     public void forEachRemaining(ConsumerType action) {
/*  742 */       if (this.mustReturnNullKey) {
/*  743 */         this.mustReturnNullKey = false;
/*  744 */         acceptOnIndex(action, this.last = Float2FloatOpenHashMap.this.n);
/*  745 */         this.c--;
/*      */       } 
/*  747 */       float[] key = Float2FloatOpenHashMap.this.key;
/*  748 */       while (this.c != 0) {
/*  749 */         if (--this.pos < 0) {
/*      */           
/*  751 */           this.last = Integer.MIN_VALUE;
/*  752 */           float k = this.wrapped.getFloat(-this.pos - 1);
/*  753 */           int p = HashCommon.mix(HashCommon.float2int(k)) & Float2FloatOpenHashMap.this.mask;
/*  754 */           for (; Float.floatToIntBits(k) != Float.floatToIntBits(key[p]); p = p + 1 & Float2FloatOpenHashMap.this.mask);
/*  755 */           acceptOnIndex(action, p);
/*  756 */           this.c--; continue;
/*  757 */         }  if (Float.floatToIntBits(key[this.pos]) != 0) {
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
/*  774 */       float[] key = Float2FloatOpenHashMap.this.key; while (true) {
/*      */         float curr; int last;
/*  776 */         pos = (last = pos) + 1 & Float2FloatOpenHashMap.this.mask;
/*      */         while (true) {
/*  778 */           if (Float.floatToIntBits(curr = key[pos]) == 0) {
/*  779 */             key[last] = 0.0F;
/*      */             return;
/*      */           } 
/*  782 */           int slot = HashCommon.mix(HashCommon.float2int(curr)) & Float2FloatOpenHashMap.this.mask;
/*  783 */           if ((last <= pos) ? (last >= slot || slot > pos) : (last >= slot && slot > pos))
/*  784 */             break;  pos = pos + 1 & Float2FloatOpenHashMap.this.mask;
/*      */         } 
/*  786 */         if (pos < last) {
/*  787 */           if (this.wrapped == null) this.wrapped = new FloatArrayList(2); 
/*  788 */           this.wrapped.add(key[pos]);
/*      */         } 
/*  790 */         key[last] = curr;
/*  791 */         Float2FloatOpenHashMap.this.value[last] = Float2FloatOpenHashMap.this.value[pos];
/*      */       } 
/*      */     }
/*      */     
/*      */     public void remove() {
/*  796 */       if (this.last == -1) throw new IllegalStateException(); 
/*  797 */       if (this.last == Float2FloatOpenHashMap.this.n)
/*  798 */       { Float2FloatOpenHashMap.this.containsNullKey = false; }
/*  799 */       else if (this.pos >= 0) { shiftKeys(this.last); }
/*      */       else
/*      */       
/*  802 */       { Float2FloatOpenHashMap.this.remove(this.wrapped.getFloat(-this.pos - 1));
/*  803 */         this.last = -1;
/*      */         return; }
/*      */       
/*  806 */       Float2FloatOpenHashMap.this.size--;
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
/*      */   private final class EntryIterator extends MapIterator<Consumer<? super Float2FloatMap.Entry>> implements ObjectIterator<Float2FloatMap.Entry> { public Float2FloatOpenHashMap.MapEntry next() {
/*  823 */       return this.entry = new Float2FloatOpenHashMap.MapEntry(nextEntry());
/*      */     }
/*      */     private Float2FloatOpenHashMap.MapEntry entry;
/*      */     private EntryIterator() {}
/*      */     
/*      */     final void acceptOnIndex(Consumer<? super Float2FloatMap.Entry> action, int index) {
/*  829 */       action.accept(this.entry = new Float2FloatOpenHashMap.MapEntry(index));
/*      */     }
/*      */ 
/*      */     
/*      */     public void remove() {
/*  834 */       super.remove();
/*  835 */       this.entry.index = -1;
/*      */     } }
/*      */   
/*      */   private final class FastEntryIterator extends MapIterator<Consumer<? super Float2FloatMap.Entry>> implements ObjectIterator<Float2FloatMap.Entry> {
/*      */     private FastEntryIterator() {
/*  840 */       this.entry = new Float2FloatOpenHashMap.MapEntry();
/*      */     }
/*      */     private final Float2FloatOpenHashMap.MapEntry entry;
/*      */     public Float2FloatOpenHashMap.MapEntry next() {
/*  844 */       this.entry.index = nextEntry();
/*  845 */       return this.entry;
/*      */     }
/*      */ 
/*      */ 
/*      */     
/*      */     final void acceptOnIndex(Consumer<? super Float2FloatMap.Entry> action, int index) {
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
/*  863 */     int max = Float2FloatOpenHashMap.this.n;
/*      */     
/*  865 */     int c = 0;
/*      */     
/*  867 */     boolean mustReturnNull = Float2FloatOpenHashMap.this.containsNullKey;
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
/*  888 */         acceptOnIndex(action, Float2FloatOpenHashMap.this.n);
/*  889 */         return true;
/*      */       } 
/*  891 */       float[] key = Float2FloatOpenHashMap.this.key;
/*  892 */       while (this.pos < this.max) {
/*  893 */         if (Float.floatToIntBits(key[this.pos]) != 0) {
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
/*  907 */         acceptOnIndex(action, Float2FloatOpenHashMap.this.n);
/*      */       } 
/*  909 */       float[] key = Float2FloatOpenHashMap.this.key;
/*  910 */       while (this.pos < this.max) {
/*  911 */         if (Float.floatToIntBits(key[this.pos]) != 0) {
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
/*  922 */         return (Float2FloatOpenHashMap.this.size - this.c);
/*      */       }
/*      */ 
/*      */ 
/*      */       
/*  927 */       return Math.min((Float2FloatOpenHashMap.this.size - this.c), (long)(Float2FloatOpenHashMap.this.realSize() / Float2FloatOpenHashMap.this.n * (this.max - this.pos)) + (this.mustReturnNull ? 1L : 0L));
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
/*  957 */       float[] key = Float2FloatOpenHashMap.this.key;
/*  958 */       while (this.pos < this.max && n > 0L) {
/*  959 */         if (Float.floatToIntBits(key[this.pos++]) != 0) {
/*  960 */           skipped++;
/*  961 */           n--;
/*      */         } 
/*      */       } 
/*  964 */       return skipped;
/*      */     }
/*      */   }
/*      */   
/*      */   private final class EntrySpliterator
/*      */     extends MapSpliterator<Consumer<? super Float2FloatMap.Entry>, EntrySpliterator> implements ObjectSpliterator<Float2FloatMap.Entry> {
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
/*      */     final void acceptOnIndex(Consumer<? super Float2FloatMap.Entry> action, int index) {
/*  985 */       action.accept(new Float2FloatOpenHashMap.MapEntry(index));
/*      */     }
/*      */ 
/*      */     
/*      */     final EntrySpliterator makeForSplit(int pos, int max, boolean mustReturnNull) {
/*  990 */       return new EntrySpliterator(pos, max, mustReturnNull, true);
/*      */     } }
/*      */   
/*      */   private final class MapEntrySet extends AbstractObjectSet<Float2FloatMap.Entry> implements Float2FloatMap.FastEntrySet {
/*      */     private MapEntrySet() {}
/*      */     
/*      */     public ObjectIterator<Float2FloatMap.Entry> iterator() {
/*  997 */       return new Float2FloatOpenHashMap.EntryIterator();
/*      */     }
/*      */ 
/*      */     
/*      */     public ObjectIterator<Float2FloatMap.Entry> fastIterator() {
/* 1002 */       return new Float2FloatOpenHashMap.FastEntryIterator();
/*      */     }
/*      */ 
/*      */     
/*      */     public ObjectSpliterator<Float2FloatMap.Entry> spliterator() {
/* 1007 */       return new Float2FloatOpenHashMap.EntrySpliterator();
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public boolean contains(Object o) {
/* 1014 */       if (!(o instanceof Map.Entry)) return false; 
/* 1015 */       Map.Entry<?, ?> e = (Map.Entry<?, ?>)o;
/* 1016 */       if (e.getKey() == null || !(e.getKey() instanceof Float)) return false; 
/* 1017 */       if (e.getValue() == null || !(e.getValue() instanceof Float)) return false; 
/* 1018 */       float k = ((Float)e.getKey()).floatValue();
/* 1019 */       float v = ((Float)e.getValue()).floatValue();
/* 1020 */       if (Float.floatToIntBits(k) == 0) return (Float2FloatOpenHashMap.this.containsNullKey && Float.floatToIntBits(Float2FloatOpenHashMap.this.value[Float2FloatOpenHashMap.this.n]) == Float.floatToIntBits(v));
/*      */       
/* 1022 */       float[] key = Float2FloatOpenHashMap.this.key;
/*      */       float curr;
/*      */       int pos;
/* 1025 */       if (Float.floatToIntBits(curr = key[pos = HashCommon.mix(HashCommon.float2int(k)) & Float2FloatOpenHashMap.this.mask]) == 0) return false; 
/* 1026 */       if (Float.floatToIntBits(k) == Float.floatToIntBits(curr)) return (Float.floatToIntBits(Float2FloatOpenHashMap.this.value[pos]) == Float.floatToIntBits(v));
/*      */       
/*      */       while (true) {
/* 1029 */         if (Float.floatToIntBits(curr = key[pos = pos + 1 & Float2FloatOpenHashMap.this.mask]) == 0) return false; 
/* 1030 */         if (Float.floatToIntBits(k) == Float.floatToIntBits(curr)) return (Float.floatToIntBits(Float2FloatOpenHashMap.this.value[pos]) == Float.floatToIntBits(v));
/*      */       
/*      */       } 
/*      */     }
/*      */ 
/*      */     
/*      */     public boolean remove(Object o) {
/* 1037 */       if (!(o instanceof Map.Entry)) return false; 
/* 1038 */       Map.Entry<?, ?> e = (Map.Entry<?, ?>)o;
/* 1039 */       if (e.getKey() == null || !(e.getKey() instanceof Float)) return false; 
/* 1040 */       if (e.getValue() == null || !(e.getValue() instanceof Float)) return false; 
/* 1041 */       float k = ((Float)e.getKey()).floatValue();
/* 1042 */       float v = ((Float)e.getValue()).floatValue();
/* 1043 */       if (Float.floatToIntBits(k) == 0) {
/* 1044 */         if (Float2FloatOpenHashMap.this.containsNullKey && Float.floatToIntBits(Float2FloatOpenHashMap.this.value[Float2FloatOpenHashMap.this.n]) == Float.floatToIntBits(v)) {
/* 1045 */           Float2FloatOpenHashMap.this.removeNullEntry();
/* 1046 */           return true;
/*      */         } 
/* 1048 */         return false;
/*      */       } 
/*      */       
/* 1051 */       float[] key = Float2FloatOpenHashMap.this.key;
/*      */       float curr;
/*      */       int pos;
/* 1054 */       if (Float.floatToIntBits(curr = key[pos = HashCommon.mix(HashCommon.float2int(k)) & Float2FloatOpenHashMap.this.mask]) == 0) return false; 
/* 1055 */       if (Float.floatToIntBits(curr) == Float.floatToIntBits(k)) {
/* 1056 */         if (Float.floatToIntBits(Float2FloatOpenHashMap.this.value[pos]) == Float.floatToIntBits(v)) {
/* 1057 */           Float2FloatOpenHashMap.this.removeEntry(pos);
/* 1058 */           return true;
/*      */         } 
/* 1060 */         return false;
/*      */       } 
/*      */       while (true) {
/* 1063 */         if (Float.floatToIntBits(curr = key[pos = pos + 1 & Float2FloatOpenHashMap.this.mask]) == 0) return false; 
/* 1064 */         if (Float.floatToIntBits(curr) == Float.floatToIntBits(k) && 
/* 1065 */           Float.floatToIntBits(Float2FloatOpenHashMap.this.value[pos]) == Float.floatToIntBits(v)) {
/* 1066 */           Float2FloatOpenHashMap.this.removeEntry(pos);
/* 1067 */           return true;
/*      */         } 
/*      */       } 
/*      */     }
/*      */ 
/*      */ 
/*      */     
/*      */     public int size() {
/* 1075 */       return Float2FloatOpenHashMap.this.size;
/*      */     }
/*      */ 
/*      */     
/*      */     public void clear() {
/* 1080 */       Float2FloatOpenHashMap.this.clear();
/*      */     }
/*      */ 
/*      */ 
/*      */     
/*      */     public void forEach(Consumer<? super Float2FloatMap.Entry> consumer) {
/* 1086 */       if (Float2FloatOpenHashMap.this.containsNullKey) consumer.accept(new Float2FloatOpenHashMap.MapEntry(Float2FloatOpenHashMap.this.n)); 
/* 1087 */       for (int pos = Float2FloatOpenHashMap.this.n; pos-- != 0;) { if (Float.floatToIntBits(Float2FloatOpenHashMap.this.key[pos]) != 0) consumer.accept(new Float2FloatOpenHashMap.MapEntry(pos));
/*      */          }
/*      */     
/*      */     }
/*      */     
/*      */     public void fastForEach(Consumer<? super Float2FloatMap.Entry> consumer) {
/* 1093 */       Float2FloatOpenHashMap.MapEntry entry = new Float2FloatOpenHashMap.MapEntry();
/* 1094 */       if (Float2FloatOpenHashMap.this.containsNullKey) {
/* 1095 */         entry.index = Float2FloatOpenHashMap.this.n;
/* 1096 */         consumer.accept(entry);
/*      */       } 
/* 1098 */       for (int pos = Float2FloatOpenHashMap.this.n; pos-- != 0;) { if (Float.floatToIntBits(Float2FloatOpenHashMap.this.key[pos]) != 0) {
/* 1099 */           entry.index = pos;
/* 1100 */           consumer.accept(entry);
/*      */         }  }
/*      */     
/*      */     }
/*      */   }
/*      */   
/*      */   public Float2FloatMap.FastEntrySet float2FloatEntrySet() {
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
/*      */     extends MapIterator<FloatConsumer>
/*      */     implements FloatIterator
/*      */   {
/*      */     final void acceptOnIndex(FloatConsumer action, int index) {
/* 1129 */       action.accept(Float2FloatOpenHashMap.this.key[index]);
/*      */     }
/*      */ 
/*      */     
/*      */     public float nextFloat() {
/* 1134 */       return Float2FloatOpenHashMap.this.key[nextEntry()];
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
/* 1145 */       super(pos, max, mustReturnNull, hasSplit);
/*      */     }
/*      */ 
/*      */     
/*      */     public int characteristics() {
/* 1150 */       return this.hasSplit ? 257 : 321;
/*      */     }
/*      */ 
/*      */     
/*      */     final void acceptOnIndex(FloatConsumer action, int index) {
/* 1155 */       action.accept(Float2FloatOpenHashMap.this.key[index]);
/*      */     }
/*      */ 
/*      */     
/*      */     final KeySpliterator makeForSplit(int pos, int max, boolean mustReturnNull) {
/* 1160 */       return new KeySpliterator(pos, max, mustReturnNull, true);
/*      */     } }
/*      */   
/*      */   private final class KeySet extends AbstractFloatSet {
/*      */     private KeySet() {}
/*      */     
/*      */     public FloatIterator iterator() {
/* 1167 */       return new Float2FloatOpenHashMap.KeyIterator();
/*      */     }
/*      */ 
/*      */     
/*      */     public FloatSpliterator spliterator() {
/* 1172 */       return new Float2FloatOpenHashMap.KeySpliterator();
/*      */     }
/*      */ 
/*      */ 
/*      */     
/*      */     public void forEach(FloatConsumer consumer) {
/* 1178 */       if (Float2FloatOpenHashMap.this.containsNullKey) consumer.accept(Float2FloatOpenHashMap.this.key[Float2FloatOpenHashMap.this.n]); 
/* 1179 */       for (int pos = Float2FloatOpenHashMap.this.n; pos-- != 0; ) {
/* 1180 */         float k = Float2FloatOpenHashMap.this.key[pos];
/* 1181 */         if (Float.floatToIntBits(k) != 0) consumer.accept(k);
/*      */       
/*      */       } 
/*      */     }
/*      */     
/*      */     public int size() {
/* 1187 */       return Float2FloatOpenHashMap.this.size;
/*      */     }
/*      */ 
/*      */     
/*      */     public boolean contains(float k) {
/* 1192 */       return Float2FloatOpenHashMap.this.containsKey(k);
/*      */     }
/*      */ 
/*      */     
/*      */     public boolean remove(float k) {
/* 1197 */       int oldSize = Float2FloatOpenHashMap.this.size;
/* 1198 */       Float2FloatOpenHashMap.this.remove(k);
/* 1199 */       return (Float2FloatOpenHashMap.this.size != oldSize);
/*      */     }
/*      */ 
/*      */     
/*      */     public void clear() {
/* 1204 */       Float2FloatOpenHashMap.this.clear();
/*      */     }
/*      */   }
/*      */ 
/*      */   
/*      */   public FloatSet keySet() {
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
/*      */     extends MapIterator<FloatConsumer>
/*      */     implements FloatIterator
/*      */   {
/*      */     final void acceptOnIndex(FloatConsumer action, int index) {
/* 1232 */       action.accept(Float2FloatOpenHashMap.this.value[index]);
/*      */     }
/*      */ 
/*      */     
/*      */     public float nextFloat() {
/* 1237 */       return Float2FloatOpenHashMap.this.value[nextEntry()];
/*      */     }
/*      */   }
/*      */   
/*      */   private final class ValueSpliterator
/*      */     extends MapSpliterator<FloatConsumer, ValueSpliterator> implements FloatSpliterator {
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
/*      */     final void acceptOnIndex(FloatConsumer action, int index) {
/* 1258 */       action.accept(Float2FloatOpenHashMap.this.value[index]);
/*      */     }
/*      */ 
/*      */     
/*      */     final ValueSpliterator makeForSplit(int pos, int max, boolean mustReturnNull) {
/* 1263 */       return new ValueSpliterator(pos, max, mustReturnNull, true);
/*      */     }
/*      */   }
/*      */ 
/*      */   
/*      */   public FloatCollection values() {
/* 1269 */     if (this.values == null) this.values = new AbstractFloatCollection()
/*      */         {
/*      */           public FloatIterator iterator() {
/* 1272 */             return new Float2FloatOpenHashMap.ValueIterator();
/*      */           }
/*      */ 
/*      */           
/*      */           public FloatSpliterator spliterator() {
/* 1277 */             return new Float2FloatOpenHashMap.ValueSpliterator();
/*      */           }
/*      */ 
/*      */ 
/*      */           
/*      */           public void forEach(FloatConsumer consumer) {
/* 1283 */             if (Float2FloatOpenHashMap.this.containsNullKey) consumer.accept(Float2FloatOpenHashMap.this.value[Float2FloatOpenHashMap.this.n]); 
/* 1284 */             for (int pos = Float2FloatOpenHashMap.this.n; pos-- != 0;) { if (Float.floatToIntBits(Float2FloatOpenHashMap.this.key[pos]) != 0) consumer.accept(Float2FloatOpenHashMap.this.value[pos]);  }
/*      */           
/*      */           }
/*      */           
/*      */           public int size() {
/* 1289 */             return Float2FloatOpenHashMap.this.size;
/*      */           }
/*      */ 
/*      */           
/*      */           public boolean contains(float v) {
/* 1294 */             return Float2FloatOpenHashMap.this.containsValue(v);
/*      */           }
/*      */ 
/*      */           
/*      */           public void clear() {
/* 1299 */             Float2FloatOpenHashMap.this.clear();
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
/* 1363 */     float[] key = this.key;
/* 1364 */     float[] value = this.value;
/* 1365 */     int mask = newN - 1;
/* 1366 */     float[] newKey = new float[newN + 1];
/* 1367 */     float[] newValue = new float[newN + 1];
/* 1368 */     int i = this.n;
/* 1369 */     for (int j = realSize(); j-- != 0; ) {
/* 1370 */       while (Float.floatToIntBits(key[--i]) == 0); int pos;
/* 1371 */       if (Float.floatToIntBits(newKey[pos = HashCommon.mix(HashCommon.float2int(key[i])) & mask]) != 0) while (Float.floatToIntBits(newKey[pos = pos + 1 & mask]) != 0); 
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
/*      */   public Float2FloatOpenHashMap clone() {
/*      */     Float2FloatOpenHashMap c;
/*      */     try {
/* 1397 */       c = (Float2FloatOpenHashMap)super.clone();
/* 1398 */     } catch (CloneNotSupportedException cantHappen) {
/* 1399 */       throw new InternalError();
/*      */     } 
/* 1401 */     c.keys = null;
/* 1402 */     c.values = null;
/* 1403 */     c.entries = null;
/* 1404 */     c.containsNullKey = this.containsNullKey;
/* 1405 */     c.key = (float[])this.key.clone();
/* 1406 */     c.value = (float[])this.value.clone();
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
/* 1423 */       for (; Float.floatToIntBits(this.key[i]) == 0; i++);
/* 1424 */       t = HashCommon.float2int(this.key[i]);
/* 1425 */       t ^= HashCommon.float2int(this.value[i]);
/* 1426 */       h += t;
/* 1427 */       i++;
/*      */     } 
/*      */     
/* 1430 */     if (this.containsNullKey) h += HashCommon.float2int(this.value[this.n]); 
/* 1431 */     return h;
/*      */   }
/*      */   
/*      */   private void writeObject(ObjectOutputStream s) throws IOException {
/* 1435 */     float[] key = this.key;
/* 1436 */     float[] value = this.value;
/* 1437 */     EntryIterator i = new EntryIterator();
/* 1438 */     s.defaultWriteObject();
/* 1439 */     for (int j = this.size; j-- != 0; ) {
/* 1440 */       int e = i.nextEntry();
/* 1441 */       s.writeFloat(key[e]);
/* 1442 */       s.writeFloat(value[e]);
/*      */     } 
/*      */   }
/*      */   
/*      */   private void readObject(ObjectInputStream s) throws IOException, ClassNotFoundException {
/* 1447 */     s.defaultReadObject();
/* 1448 */     this.n = HashCommon.arraySize(this.size, this.f);
/* 1449 */     this.maxFill = HashCommon.maxFill(this.n, this.f);
/* 1450 */     this.mask = this.n - 1;
/* 1451 */     float[] key = this.key = new float[this.n + 1];
/* 1452 */     float[] value = this.value = new float[this.n + 1];
/*      */ 
/*      */     
/* 1455 */     for (int i = this.size; i-- != 0; ) {
/* 1456 */       int pos; float k = s.readFloat();
/* 1457 */       float v = s.readFloat();
/* 1458 */       if (Float.floatToIntBits(k) == 0) {
/* 1459 */         pos = this.n;
/* 1460 */         this.containsNullKey = true;
/*      */       } else {
/* 1462 */         pos = HashCommon.mix(HashCommon.float2int(k)) & this.mask;
/* 1463 */         for (; Float.floatToIntBits(key[pos]) != 0; pos = pos + 1 & this.mask);
/*      */       } 
/* 1465 */       key[pos] = k;
/* 1466 */       value[pos] = v;
/*      */     } 
/*      */   }
/*      */   
/*      */   private void checkTable() {}
/*      */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\i\\unimi\dsi\fastutil\floats\Float2FloatOpenHashMap.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */