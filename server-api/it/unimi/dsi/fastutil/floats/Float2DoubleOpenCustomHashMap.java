/*      */ package it.unimi.dsi.fastutil.floats;
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
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ public class Float2DoubleOpenCustomHashMap
/*      */   extends AbstractFloat2DoubleMap
/*      */   implements Serializable, Cloneable, Hash
/*      */ {
/*      */   private static final long serialVersionUID = 0L;
/*      */   private static final boolean ASSERTS = false;
/*      */   protected transient float[] key;
/*      */   protected transient double[] value;
/*      */   protected transient int mask;
/*      */   protected transient boolean containsNullKey;
/*      */   protected FloatHash.Strategy strategy;
/*      */   protected transient int n;
/*      */   protected transient int maxFill;
/*      */   protected final transient int minN;
/*      */   protected int size;
/*      */   protected final float f;
/*      */   protected transient Float2DoubleMap.FastEntrySet entries;
/*      */   protected transient FloatSet keys;
/*      */   protected transient DoubleCollection values;
/*      */   
/*      */   public Float2DoubleOpenCustomHashMap(int expected, float f, FloatHash.Strategy strategy) {
/*  103 */     this.strategy = strategy;
/*  104 */     if (f <= 0.0F || f >= 1.0F) throw new IllegalArgumentException("Load factor must be greater than 0 and smaller than 1"); 
/*  105 */     if (expected < 0) throw new IllegalArgumentException("The expected number of elements must be nonnegative"); 
/*  106 */     this.f = f;
/*  107 */     this.minN = this.n = HashCommon.arraySize(expected, f);
/*  108 */     this.mask = this.n - 1;
/*  109 */     this.maxFill = HashCommon.maxFill(this.n, f);
/*  110 */     this.key = new float[this.n + 1];
/*  111 */     this.value = new double[this.n + 1];
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Float2DoubleOpenCustomHashMap(int expected, FloatHash.Strategy strategy) {
/*  121 */     this(expected, 0.75F, strategy);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Float2DoubleOpenCustomHashMap(FloatHash.Strategy strategy) {
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
/*      */   public Float2DoubleOpenCustomHashMap(Map<? extends Float, ? extends Double> m, float f, FloatHash.Strategy strategy) {
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
/*      */   public Float2DoubleOpenCustomHashMap(Map<? extends Float, ? extends Double> m, FloatHash.Strategy strategy) {
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
/*      */   public Float2DoubleOpenCustomHashMap(Float2DoubleMap m, float f, FloatHash.Strategy strategy) {
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
/*      */   public Float2DoubleOpenCustomHashMap(Float2DoubleMap m, FloatHash.Strategy strategy) {
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
/*      */   public Float2DoubleOpenCustomHashMap(float[] k, double[] v, float f, FloatHash.Strategy strategy) {
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
/*      */   public Float2DoubleOpenCustomHashMap(float[] k, double[] v, FloatHash.Strategy strategy) {
/*  204 */     this(k, v, 0.75F, strategy);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public FloatHash.Strategy strategy() {
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
/*      */   private double removeEntry(int pos) {
/*  237 */     double oldValue = this.value[pos];
/*  238 */     this.size--;
/*  239 */     shiftKeys(pos);
/*  240 */     if (this.n > this.minN && this.size < this.maxFill / 4 && this.n > 16) rehash(this.n / 2); 
/*  241 */     return oldValue;
/*      */   }
/*      */   
/*      */   private double removeNullEntry() {
/*  245 */     this.containsNullKey = false;
/*  246 */     double oldValue = this.value[this.n];
/*  247 */     this.size--;
/*  248 */     if (this.n > this.minN && this.size < this.maxFill / 4 && this.n > 16) rehash(this.n / 2); 
/*  249 */     return oldValue;
/*      */   }
/*      */ 
/*      */   
/*      */   public void putAll(Map<? extends Float, ? extends Double> m) {
/*  254 */     if (this.f <= 0.5D) { ensureCapacity(m.size()); }
/*  255 */     else { tryCapacity((size() + m.size())); }
/*      */     
/*  257 */     super.putAll(m);
/*      */   }
/*      */   
/*      */   private int find(float k) {
/*  261 */     if (this.strategy.equals(k, 0.0F)) return this.containsNullKey ? this.n : -(this.n + 1);
/*      */     
/*  263 */     float[] key = this.key;
/*      */     float curr;
/*      */     int pos;
/*  266 */     if (Float.floatToIntBits(curr = key[pos = HashCommon.mix(this.strategy.hashCode(k)) & this.mask]) == 0) return -(pos + 1); 
/*  267 */     if (this.strategy.equals(k, curr)) return pos;
/*      */     
/*      */     while (true) {
/*  270 */       if (Float.floatToIntBits(curr = key[pos = pos + 1 & this.mask]) == 0) return -(pos + 1); 
/*  271 */       if (this.strategy.equals(k, curr)) return pos; 
/*      */     } 
/*      */   }
/*      */   
/*      */   private void insert(int pos, float k, double v) {
/*  276 */     if (pos == this.n) this.containsNullKey = true; 
/*  277 */     this.key[pos] = k;
/*  278 */     this.value[pos] = v;
/*  279 */     if (this.size++ >= this.maxFill) rehash(HashCommon.arraySize(this.size + 1, this.f));
/*      */   
/*      */   }
/*      */ 
/*      */   
/*      */   public double put(float k, double v) {
/*  285 */     int pos = find(k);
/*  286 */     if (pos < 0) {
/*  287 */       insert(-pos - 1, k, v);
/*  288 */       return this.defRetValue;
/*      */     } 
/*  290 */     double oldValue = this.value[pos];
/*  291 */     this.value[pos] = v;
/*  292 */     return oldValue;
/*      */   }
/*      */   
/*      */   private double addToValue(int pos, double incr) {
/*  296 */     double oldValue = this.value[pos];
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
/*      */   public double addTo(float k, double incr) {
/*      */     int pos;
/*  316 */     if (this.strategy.equals(k, 0.0F)) {
/*  317 */       if (this.containsNullKey) return addToValue(this.n, incr); 
/*  318 */       pos = this.n;
/*  319 */       this.containsNullKey = true;
/*      */     } else {
/*      */       
/*  322 */       float[] key = this.key;
/*      */       float curr;
/*  324 */       if (Float.floatToIntBits(curr = key[pos = HashCommon.mix(this.strategy.hashCode(k)) & this.mask]) != 0) {
/*  325 */         if (this.strategy.equals(curr, k)) return addToValue(pos, incr); 
/*  326 */         while (Float.floatToIntBits(curr = key[pos = pos + 1 & this.mask]) != 0) { if (this.strategy.equals(curr, k)) return addToValue(pos, incr);  }
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
/*  346 */     float[] key = this.key; while (true) {
/*      */       float curr; int last;
/*  348 */       pos = (last = pos) + 1 & this.mask;
/*      */       while (true) {
/*  350 */         if (Float.floatToIntBits(curr = key[pos]) == 0) {
/*  351 */           key[last] = 0.0F;
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
/*      */   public double remove(float k) {
/*  366 */     if (this.strategy.equals(k, 0.0F)) {
/*  367 */       if (this.containsNullKey) return removeNullEntry(); 
/*  368 */       return this.defRetValue;
/*      */     } 
/*      */     
/*  371 */     float[] key = this.key;
/*      */     float curr;
/*      */     int pos;
/*  374 */     if (Float.floatToIntBits(curr = key[pos = HashCommon.mix(this.strategy.hashCode(k)) & this.mask]) == 0) return this.defRetValue; 
/*  375 */     if (this.strategy.equals(k, curr)) return removeEntry(pos); 
/*      */     while (true) {
/*  377 */       if (Float.floatToIntBits(curr = key[pos = pos + 1 & this.mask]) == 0) return this.defRetValue; 
/*  378 */       if (this.strategy.equals(k, curr)) return removeEntry(pos);
/*      */     
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   public double get(float k) {
/*  385 */     if (this.strategy.equals(k, 0.0F)) return this.containsNullKey ? this.value[this.n] : this.defRetValue;
/*      */     
/*  387 */     float[] key = this.key;
/*      */     float curr;
/*      */     int pos;
/*  390 */     if (Float.floatToIntBits(curr = key[pos = HashCommon.mix(this.strategy.hashCode(k)) & this.mask]) == 0) return this.defRetValue; 
/*  391 */     if (this.strategy.equals(k, curr)) return this.value[pos];
/*      */     
/*      */     while (true) {
/*  394 */       if (Float.floatToIntBits(curr = key[pos = pos + 1 & this.mask]) == 0) return this.defRetValue; 
/*  395 */       if (this.strategy.equals(k, curr)) return this.value[pos];
/*      */     
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   public boolean containsKey(float k) {
/*  402 */     if (this.strategy.equals(k, 0.0F)) return this.containsNullKey;
/*      */     
/*  404 */     float[] key = this.key;
/*      */     float curr;
/*      */     int pos;
/*  407 */     if (Float.floatToIntBits(curr = key[pos = HashCommon.mix(this.strategy.hashCode(k)) & this.mask]) == 0) return false; 
/*  408 */     if (this.strategy.equals(k, curr)) return true;
/*      */     
/*      */     while (true) {
/*  411 */       if (Float.floatToIntBits(curr = key[pos = pos + 1 & this.mask]) == 0) return false; 
/*  412 */       if (this.strategy.equals(k, curr)) return true;
/*      */     
/*      */     } 
/*      */   }
/*      */   
/*      */   public boolean containsValue(double v) {
/*  418 */     double[] value = this.value;
/*  419 */     float[] key = this.key;
/*  420 */     if (this.containsNullKey && Double.doubleToLongBits(value[this.n]) == Double.doubleToLongBits(v)) return true; 
/*  421 */     for (int i = this.n; i-- != 0;) { if (Float.floatToIntBits(key[i]) != 0 && Double.doubleToLongBits(value[i]) == Double.doubleToLongBits(v)) return true;  }
/*  422 */      return false;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public double getOrDefault(float k, double defaultValue) {
/*  429 */     if (this.strategy.equals(k, 0.0F)) return this.containsNullKey ? this.value[this.n] : defaultValue;
/*      */     
/*  431 */     float[] key = this.key;
/*      */     float curr;
/*      */     int pos;
/*  434 */     if (Float.floatToIntBits(curr = key[pos = HashCommon.mix(this.strategy.hashCode(k)) & this.mask]) == 0) return defaultValue; 
/*  435 */     if (this.strategy.equals(k, curr)) return this.value[pos];
/*      */     
/*      */     while (true) {
/*  438 */       if (Float.floatToIntBits(curr = key[pos = pos + 1 & this.mask]) == 0) return defaultValue; 
/*  439 */       if (this.strategy.equals(k, curr)) return this.value[pos];
/*      */     
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   public double putIfAbsent(float k, double v) {
/*  446 */     int pos = find(k);
/*  447 */     if (pos >= 0) return this.value[pos]; 
/*  448 */     insert(-pos - 1, k, v);
/*  449 */     return this.defRetValue;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean remove(float k, double v) {
/*  456 */     if (this.strategy.equals(k, 0.0F)) {
/*  457 */       if (this.containsNullKey && Double.doubleToLongBits(v) == Double.doubleToLongBits(this.value[this.n])) {
/*  458 */         removeNullEntry();
/*  459 */         return true;
/*      */       } 
/*  461 */       return false;
/*      */     } 
/*      */     
/*  464 */     float[] key = this.key;
/*      */     float curr;
/*      */     int pos;
/*  467 */     if (Float.floatToIntBits(curr = key[pos = HashCommon.mix(this.strategy.hashCode(k)) & this.mask]) == 0) return false; 
/*  468 */     if (this.strategy.equals(k, curr) && Double.doubleToLongBits(v) == Double.doubleToLongBits(this.value[pos])) {
/*  469 */       removeEntry(pos);
/*  470 */       return true;
/*      */     } 
/*      */     while (true) {
/*  473 */       if (Float.floatToIntBits(curr = key[pos = pos + 1 & this.mask]) == 0) return false; 
/*  474 */       if (this.strategy.equals(k, curr) && Double.doubleToLongBits(v) == Double.doubleToLongBits(this.value[pos])) {
/*  475 */         removeEntry(pos);
/*  476 */         return true;
/*      */       } 
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean replace(float k, double oldValue, double v) {
/*  484 */     int pos = find(k);
/*  485 */     if (pos < 0 || Double.doubleToLongBits(oldValue) != Double.doubleToLongBits(this.value[pos])) return false; 
/*  486 */     this.value[pos] = v;
/*  487 */     return true;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public double replace(float k, double v) {
/*  493 */     int pos = find(k);
/*  494 */     if (pos < 0) return this.defRetValue; 
/*  495 */     double oldValue = this.value[pos];
/*  496 */     this.value[pos] = v;
/*  497 */     return oldValue;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public double computeIfAbsent(float k, DoubleUnaryOperator mappingFunction) {
/*  503 */     Objects.requireNonNull(mappingFunction);
/*  504 */     int pos = find(k);
/*  505 */     if (pos >= 0) return this.value[pos]; 
/*  506 */     double newValue = mappingFunction.applyAsDouble(k);
/*  507 */     insert(-pos - 1, k, newValue);
/*  508 */     return newValue;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public double computeIfAbsent(float key, Float2DoubleFunction mappingFunction) {
/*  514 */     Objects.requireNonNull(mappingFunction);
/*  515 */     int pos = find(key);
/*  516 */     if (pos >= 0) return this.value[pos]; 
/*  517 */     if (!mappingFunction.containsKey(key)) return this.defRetValue; 
/*  518 */     double newValue = mappingFunction.get(key);
/*  519 */     insert(-pos - 1, key, newValue);
/*  520 */     return newValue;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public double computeIfAbsentNullable(float k, DoubleFunction<? extends Double> mappingFunction) {
/*  526 */     Objects.requireNonNull(mappingFunction);
/*  527 */     int pos = find(k);
/*  528 */     if (pos >= 0) return this.value[pos]; 
/*  529 */     Double newValue = mappingFunction.apply(k);
/*  530 */     if (newValue == null) return this.defRetValue; 
/*  531 */     double v = newValue.doubleValue();
/*  532 */     insert(-pos - 1, k, v);
/*  533 */     return v;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public double computeIfPresent(float k, BiFunction<? super Float, ? super Double, ? extends Double> remappingFunction) {
/*  539 */     Objects.requireNonNull(remappingFunction);
/*  540 */     int pos = find(k);
/*  541 */     if (pos < 0) return this.defRetValue; 
/*  542 */     Double newValue = remappingFunction.apply(Float.valueOf(k), Double.valueOf(this.value[pos]));
/*  543 */     if (newValue == null) {
/*  544 */       if (this.strategy.equals(k, 0.0F)) { removeNullEntry(); }
/*  545 */       else { removeEntry(pos); }
/*  546 */        return this.defRetValue;
/*      */     } 
/*  548 */     this.value[pos] = newValue.doubleValue(); return newValue.doubleValue();
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public double compute(float k, BiFunction<? super Float, ? super Double, ? extends Double> remappingFunction) {
/*  554 */     Objects.requireNonNull(remappingFunction);
/*  555 */     int pos = find(k);
/*  556 */     Double newValue = remappingFunction.apply(Float.valueOf(k), (pos >= 0) ? Double.valueOf(this.value[pos]) : null);
/*  557 */     if (newValue == null) {
/*  558 */       if (pos >= 0)
/*  559 */         if (this.strategy.equals(k, 0.0F)) { removeNullEntry(); }
/*  560 */         else { removeEntry(pos); }
/*      */          
/*  562 */       return this.defRetValue;
/*      */     } 
/*  564 */     double newVal = newValue.doubleValue();
/*  565 */     if (pos < 0) {
/*  566 */       insert(-pos - 1, k, newVal);
/*  567 */       return newVal;
/*      */     } 
/*  569 */     this.value[pos] = newVal; return newVal;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public double merge(float k, double v, BiFunction<? super Double, ? super Double, ? extends Double> remappingFunction) {
/*  575 */     Objects.requireNonNull(remappingFunction);
/*      */     
/*  577 */     int pos = find(k);
/*  578 */     if (pos < 0) {
/*  579 */       if (pos < 0) { insert(-pos - 1, k, v); }
/*  580 */       else { this.value[pos] = v; }
/*  581 */        return v;
/*      */     } 
/*  583 */     Double newValue = remappingFunction.apply(Double.valueOf(this.value[pos]), Double.valueOf(v));
/*  584 */     if (newValue == null) {
/*  585 */       if (this.strategy.equals(k, 0.0F)) { removeNullEntry(); }
/*  586 */       else { removeEntry(pos); }
/*  587 */        return this.defRetValue;
/*      */     } 
/*  589 */     this.value[pos] = newValue.doubleValue(); return newValue.doubleValue();
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
/*  603 */     Arrays.fill(this.key, 0.0F);
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
/*      */     implements Float2DoubleMap.Entry, Map.Entry<Float, Double>, FloatDoublePair
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
/*      */     public float getFloatKey() {
/*  634 */       return Float2DoubleOpenCustomHashMap.this.key[this.index];
/*      */     }
/*      */ 
/*      */     
/*      */     public float leftFloat() {
/*  639 */       return Float2DoubleOpenCustomHashMap.this.key[this.index];
/*      */     }
/*      */ 
/*      */     
/*      */     public double getDoubleValue() {
/*  644 */       return Float2DoubleOpenCustomHashMap.this.value[this.index];
/*      */     }
/*      */ 
/*      */     
/*      */     public double rightDouble() {
/*  649 */       return Float2DoubleOpenCustomHashMap.this.value[this.index];
/*      */     }
/*      */ 
/*      */     
/*      */     public double setValue(double v) {
/*  654 */       double oldValue = Float2DoubleOpenCustomHashMap.this.value[this.index];
/*  655 */       Float2DoubleOpenCustomHashMap.this.value[this.index] = v;
/*  656 */       return oldValue;
/*      */     }
/*      */ 
/*      */     
/*      */     public FloatDoublePair right(double v) {
/*  661 */       Float2DoubleOpenCustomHashMap.this.value[this.index] = v;
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
/*      */     public Float getKey() {
/*  673 */       return Float.valueOf(Float2DoubleOpenCustomHashMap.this.key[this.index]);
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
/*  684 */       return Double.valueOf(Float2DoubleOpenCustomHashMap.this.value[this.index]);
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
/*  695 */       return Double.valueOf(setValue(v.doubleValue()));
/*      */     }
/*      */ 
/*      */ 
/*      */     
/*      */     public boolean equals(Object o) {
/*  701 */       if (!(o instanceof Map.Entry)) return false; 
/*  702 */       Map.Entry<Float, Double> e = (Map.Entry<Float, Double>)o;
/*  703 */       return (Float2DoubleOpenCustomHashMap.this.strategy.equals(Float2DoubleOpenCustomHashMap.this.key[this.index], ((Float)e.getKey()).floatValue()) && Double.doubleToLongBits(Float2DoubleOpenCustomHashMap.this.value[this.index]) == Double.doubleToLongBits(((Double)e.getValue()).doubleValue()));
/*      */     }
/*      */ 
/*      */     
/*      */     public int hashCode() {
/*  708 */       return Float2DoubleOpenCustomHashMap.this.strategy.hashCode(Float2DoubleOpenCustomHashMap.this.key[this.index]) ^ HashCommon.double2int(Float2DoubleOpenCustomHashMap.this.value[this.index]);
/*      */     }
/*      */ 
/*      */     
/*      */     public String toString() {
/*  713 */       return Float2DoubleOpenCustomHashMap.this.key[this.index] + "=>" + Float2DoubleOpenCustomHashMap.this.value[this.index];
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
/*  724 */     int pos = Float2DoubleOpenCustomHashMap.this.n;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  730 */     int last = -1;
/*      */     
/*  732 */     int c = Float2DoubleOpenCustomHashMap.this.size;
/*      */     
/*  734 */     boolean mustReturnNullKey = Float2DoubleOpenCustomHashMap.this.containsNullKey;
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
/*  745 */       return (this.c != 0);
/*      */     }
/*      */     
/*      */     public int nextEntry() {
/*  749 */       if (!hasNext()) throw new NoSuchElementException(); 
/*  750 */       this.c--;
/*  751 */       if (this.mustReturnNullKey) {
/*  752 */         this.mustReturnNullKey = false;
/*  753 */         return this.last = Float2DoubleOpenCustomHashMap.this.n;
/*      */       } 
/*  755 */       float[] key = Float2DoubleOpenCustomHashMap.this.key;
/*      */       while (true) {
/*  757 */         if (--this.pos < 0) {
/*      */           
/*  759 */           this.last = Integer.MIN_VALUE;
/*  760 */           float k = this.wrapped.getFloat(-this.pos - 1);
/*  761 */           int p = HashCommon.mix(Float2DoubleOpenCustomHashMap.this.strategy.hashCode(k)) & Float2DoubleOpenCustomHashMap.this.mask;
/*  762 */           for (; !Float2DoubleOpenCustomHashMap.this.strategy.equals(k, key[p]); p = p + 1 & Float2DoubleOpenCustomHashMap.this.mask);
/*  763 */           return p;
/*      */         } 
/*  765 */         if (Float.floatToIntBits(key[this.pos]) != 0) return this.last = this.pos; 
/*      */       } 
/*      */     }
/*      */     
/*      */     public void forEachRemaining(ConsumerType action) {
/*  770 */       if (this.mustReturnNullKey) {
/*  771 */         this.mustReturnNullKey = false;
/*  772 */         acceptOnIndex(action, this.last = Float2DoubleOpenCustomHashMap.this.n);
/*  773 */         this.c--;
/*      */       } 
/*  775 */       float[] key = Float2DoubleOpenCustomHashMap.this.key;
/*  776 */       while (this.c != 0) {
/*  777 */         if (--this.pos < 0) {
/*      */           
/*  779 */           this.last = Integer.MIN_VALUE;
/*  780 */           float k = this.wrapped.getFloat(-this.pos - 1);
/*  781 */           int p = HashCommon.mix(Float2DoubleOpenCustomHashMap.this.strategy.hashCode(k)) & Float2DoubleOpenCustomHashMap.this.mask;
/*  782 */           for (; !Float2DoubleOpenCustomHashMap.this.strategy.equals(k, key[p]); p = p + 1 & Float2DoubleOpenCustomHashMap.this.mask);
/*  783 */           acceptOnIndex(action, p);
/*  784 */           this.c--; continue;
/*  785 */         }  if (Float.floatToIntBits(key[this.pos]) != 0) {
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
/*  802 */       float[] key = Float2DoubleOpenCustomHashMap.this.key; while (true) {
/*      */         float curr; int last;
/*  804 */         pos = (last = pos) + 1 & Float2DoubleOpenCustomHashMap.this.mask;
/*      */         while (true) {
/*  806 */           if (Float.floatToIntBits(curr = key[pos]) == 0) {
/*  807 */             key[last] = 0.0F;
/*      */             return;
/*      */           } 
/*  810 */           int slot = HashCommon.mix(Float2DoubleOpenCustomHashMap.this.strategy.hashCode(curr)) & Float2DoubleOpenCustomHashMap.this.mask;
/*  811 */           if ((last <= pos) ? (last >= slot || slot > pos) : (last >= slot && slot > pos))
/*  812 */             break;  pos = pos + 1 & Float2DoubleOpenCustomHashMap.this.mask;
/*      */         } 
/*  814 */         if (pos < last) {
/*  815 */           if (this.wrapped == null) this.wrapped = new FloatArrayList(2); 
/*  816 */           this.wrapped.add(key[pos]);
/*      */         } 
/*  818 */         key[last] = curr;
/*  819 */         Float2DoubleOpenCustomHashMap.this.value[last] = Float2DoubleOpenCustomHashMap.this.value[pos];
/*      */       } 
/*      */     }
/*      */     
/*      */     public void remove() {
/*  824 */       if (this.last == -1) throw new IllegalStateException(); 
/*  825 */       if (this.last == Float2DoubleOpenCustomHashMap.this.n)
/*  826 */       { Float2DoubleOpenCustomHashMap.this.containsNullKey = false; }
/*  827 */       else if (this.pos >= 0) { shiftKeys(this.last); }
/*      */       else
/*      */       
/*  830 */       { Float2DoubleOpenCustomHashMap.this.remove(this.wrapped.getFloat(-this.pos - 1));
/*  831 */         this.last = -1;
/*      */         return; }
/*      */       
/*  834 */       Float2DoubleOpenCustomHashMap.this.size--;
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
/*      */   private final class EntryIterator extends MapIterator<Consumer<? super Float2DoubleMap.Entry>> implements ObjectIterator<Float2DoubleMap.Entry> { public Float2DoubleOpenCustomHashMap.MapEntry next() {
/*  851 */       return this.entry = new Float2DoubleOpenCustomHashMap.MapEntry(nextEntry());
/*      */     }
/*      */     private Float2DoubleOpenCustomHashMap.MapEntry entry;
/*      */     private EntryIterator() {}
/*      */     
/*      */     final void acceptOnIndex(Consumer<? super Float2DoubleMap.Entry> action, int index) {
/*  857 */       action.accept(this.entry = new Float2DoubleOpenCustomHashMap.MapEntry(index));
/*      */     }
/*      */ 
/*      */     
/*      */     public void remove() {
/*  862 */       super.remove();
/*  863 */       this.entry.index = -1;
/*      */     } }
/*      */   
/*      */   private final class FastEntryIterator extends MapIterator<Consumer<? super Float2DoubleMap.Entry>> implements ObjectIterator<Float2DoubleMap.Entry> {
/*      */     private FastEntryIterator() {
/*  868 */       this.entry = new Float2DoubleOpenCustomHashMap.MapEntry();
/*      */     }
/*      */     private final Float2DoubleOpenCustomHashMap.MapEntry entry;
/*      */     public Float2DoubleOpenCustomHashMap.MapEntry next() {
/*  872 */       this.entry.index = nextEntry();
/*  873 */       return this.entry;
/*      */     }
/*      */ 
/*      */ 
/*      */     
/*      */     final void acceptOnIndex(Consumer<? super Float2DoubleMap.Entry> action, int index) {
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
/*  891 */     int max = Float2DoubleOpenCustomHashMap.this.n;
/*      */     
/*  893 */     int c = 0;
/*      */     
/*  895 */     boolean mustReturnNull = Float2DoubleOpenCustomHashMap.this.containsNullKey;
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
/*  916 */         acceptOnIndex(action, Float2DoubleOpenCustomHashMap.this.n);
/*  917 */         return true;
/*      */       } 
/*  919 */       float[] key = Float2DoubleOpenCustomHashMap.this.key;
/*  920 */       while (this.pos < this.max) {
/*  921 */         if (Float.floatToIntBits(key[this.pos]) != 0) {
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
/*  935 */         acceptOnIndex(action, Float2DoubleOpenCustomHashMap.this.n);
/*      */       } 
/*  937 */       float[] key = Float2DoubleOpenCustomHashMap.this.key;
/*  938 */       while (this.pos < this.max) {
/*  939 */         if (Float.floatToIntBits(key[this.pos]) != 0) {
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
/*  950 */         return (Float2DoubleOpenCustomHashMap.this.size - this.c);
/*      */       }
/*      */ 
/*      */ 
/*      */       
/*  955 */       return Math.min((Float2DoubleOpenCustomHashMap.this.size - this.c), (long)(Float2DoubleOpenCustomHashMap.this.realSize() / Float2DoubleOpenCustomHashMap.this.n * (this.max - this.pos)) + (this.mustReturnNull ? 1L : 0L));
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
/*  985 */       float[] key = Float2DoubleOpenCustomHashMap.this.key;
/*  986 */       while (this.pos < this.max && n > 0L) {
/*  987 */         if (Float.floatToIntBits(key[this.pos++]) != 0) {
/*  988 */           skipped++;
/*  989 */           n--;
/*      */         } 
/*      */       } 
/*  992 */       return skipped;
/*      */     }
/*      */   }
/*      */   
/*      */   private final class EntrySpliterator
/*      */     extends MapSpliterator<Consumer<? super Float2DoubleMap.Entry>, EntrySpliterator> implements ObjectSpliterator<Float2DoubleMap.Entry> {
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
/*      */     final void acceptOnIndex(Consumer<? super Float2DoubleMap.Entry> action, int index) {
/* 1013 */       action.accept(new Float2DoubleOpenCustomHashMap.MapEntry(index));
/*      */     }
/*      */ 
/*      */     
/*      */     final EntrySpliterator makeForSplit(int pos, int max, boolean mustReturnNull) {
/* 1018 */       return new EntrySpliterator(pos, max, mustReturnNull, true);
/*      */     } }
/*      */   
/*      */   private final class MapEntrySet extends AbstractObjectSet<Float2DoubleMap.Entry> implements Float2DoubleMap.FastEntrySet {
/*      */     private MapEntrySet() {}
/*      */     
/*      */     public ObjectIterator<Float2DoubleMap.Entry> iterator() {
/* 1025 */       return new Float2DoubleOpenCustomHashMap.EntryIterator();
/*      */     }
/*      */ 
/*      */     
/*      */     public ObjectIterator<Float2DoubleMap.Entry> fastIterator() {
/* 1030 */       return new Float2DoubleOpenCustomHashMap.FastEntryIterator();
/*      */     }
/*      */ 
/*      */     
/*      */     public ObjectSpliterator<Float2DoubleMap.Entry> spliterator() {
/* 1035 */       return new Float2DoubleOpenCustomHashMap.EntrySpliterator();
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public boolean contains(Object o) {
/* 1042 */       if (!(o instanceof Map.Entry)) return false; 
/* 1043 */       Map.Entry<?, ?> e = (Map.Entry<?, ?>)o;
/* 1044 */       if (e.getKey() == null || !(e.getKey() instanceof Float)) return false; 
/* 1045 */       if (e.getValue() == null || !(e.getValue() instanceof Double)) return false; 
/* 1046 */       float k = ((Float)e.getKey()).floatValue();
/* 1047 */       double v = ((Double)e.getValue()).doubleValue();
/* 1048 */       if (Float2DoubleOpenCustomHashMap.this.strategy.equals(k, 0.0F)) return (Float2DoubleOpenCustomHashMap.this.containsNullKey && Double.doubleToLongBits(Float2DoubleOpenCustomHashMap.this.value[Float2DoubleOpenCustomHashMap.this.n]) == Double.doubleToLongBits(v));
/*      */       
/* 1050 */       float[] key = Float2DoubleOpenCustomHashMap.this.key;
/*      */       float curr;
/*      */       int pos;
/* 1053 */       if (Float.floatToIntBits(curr = key[pos = HashCommon.mix(Float2DoubleOpenCustomHashMap.this.strategy.hashCode(k)) & Float2DoubleOpenCustomHashMap.this.mask]) == 0) return false; 
/* 1054 */       if (Float2DoubleOpenCustomHashMap.this.strategy.equals(k, curr)) return (Double.doubleToLongBits(Float2DoubleOpenCustomHashMap.this.value[pos]) == Double.doubleToLongBits(v));
/*      */       
/*      */       while (true) {
/* 1057 */         if (Float.floatToIntBits(curr = key[pos = pos + 1 & Float2DoubleOpenCustomHashMap.this.mask]) == 0) return false; 
/* 1058 */         if (Float2DoubleOpenCustomHashMap.this.strategy.equals(k, curr)) return (Double.doubleToLongBits(Float2DoubleOpenCustomHashMap.this.value[pos]) == Double.doubleToLongBits(v));
/*      */       
/*      */       } 
/*      */     }
/*      */ 
/*      */     
/*      */     public boolean remove(Object o) {
/* 1065 */       if (!(o instanceof Map.Entry)) return false; 
/* 1066 */       Map.Entry<?, ?> e = (Map.Entry<?, ?>)o;
/* 1067 */       if (e.getKey() == null || !(e.getKey() instanceof Float)) return false; 
/* 1068 */       if (e.getValue() == null || !(e.getValue() instanceof Double)) return false; 
/* 1069 */       float k = ((Float)e.getKey()).floatValue();
/* 1070 */       double v = ((Double)e.getValue()).doubleValue();
/* 1071 */       if (Float2DoubleOpenCustomHashMap.this.strategy.equals(k, 0.0F)) {
/* 1072 */         if (Float2DoubleOpenCustomHashMap.this.containsNullKey && Double.doubleToLongBits(Float2DoubleOpenCustomHashMap.this.value[Float2DoubleOpenCustomHashMap.this.n]) == Double.doubleToLongBits(v)) {
/* 1073 */           Float2DoubleOpenCustomHashMap.this.removeNullEntry();
/* 1074 */           return true;
/*      */         } 
/* 1076 */         return false;
/*      */       } 
/*      */       
/* 1079 */       float[] key = Float2DoubleOpenCustomHashMap.this.key;
/*      */       float curr;
/*      */       int pos;
/* 1082 */       if (Float.floatToIntBits(curr = key[pos = HashCommon.mix(Float2DoubleOpenCustomHashMap.this.strategy.hashCode(k)) & Float2DoubleOpenCustomHashMap.this.mask]) == 0) return false; 
/* 1083 */       if (Float2DoubleOpenCustomHashMap.this.strategy.equals(curr, k)) {
/* 1084 */         if (Double.doubleToLongBits(Float2DoubleOpenCustomHashMap.this.value[pos]) == Double.doubleToLongBits(v)) {
/* 1085 */           Float2DoubleOpenCustomHashMap.this.removeEntry(pos);
/* 1086 */           return true;
/*      */         } 
/* 1088 */         return false;
/*      */       } 
/*      */       while (true) {
/* 1091 */         if (Float.floatToIntBits(curr = key[pos = pos + 1 & Float2DoubleOpenCustomHashMap.this.mask]) == 0) return false; 
/* 1092 */         if (Float2DoubleOpenCustomHashMap.this.strategy.equals(curr, k) && 
/* 1093 */           Double.doubleToLongBits(Float2DoubleOpenCustomHashMap.this.value[pos]) == Double.doubleToLongBits(v)) {
/* 1094 */           Float2DoubleOpenCustomHashMap.this.removeEntry(pos);
/* 1095 */           return true;
/*      */         } 
/*      */       } 
/*      */     }
/*      */ 
/*      */ 
/*      */     
/*      */     public int size() {
/* 1103 */       return Float2DoubleOpenCustomHashMap.this.size;
/*      */     }
/*      */ 
/*      */     
/*      */     public void clear() {
/* 1108 */       Float2DoubleOpenCustomHashMap.this.clear();
/*      */     }
/*      */ 
/*      */ 
/*      */     
/*      */     public void forEach(Consumer<? super Float2DoubleMap.Entry> consumer) {
/* 1114 */       if (Float2DoubleOpenCustomHashMap.this.containsNullKey) consumer.accept(new Float2DoubleOpenCustomHashMap.MapEntry(Float2DoubleOpenCustomHashMap.this.n)); 
/* 1115 */       for (int pos = Float2DoubleOpenCustomHashMap.this.n; pos-- != 0;) { if (Float.floatToIntBits(Float2DoubleOpenCustomHashMap.this.key[pos]) != 0) consumer.accept(new Float2DoubleOpenCustomHashMap.MapEntry(pos));
/*      */          }
/*      */     
/*      */     }
/*      */     
/*      */     public void fastForEach(Consumer<? super Float2DoubleMap.Entry> consumer) {
/* 1121 */       Float2DoubleOpenCustomHashMap.MapEntry entry = new Float2DoubleOpenCustomHashMap.MapEntry();
/* 1122 */       if (Float2DoubleOpenCustomHashMap.this.containsNullKey) {
/* 1123 */         entry.index = Float2DoubleOpenCustomHashMap.this.n;
/* 1124 */         consumer.accept(entry);
/*      */       } 
/* 1126 */       for (int pos = Float2DoubleOpenCustomHashMap.this.n; pos-- != 0;) { if (Float.floatToIntBits(Float2DoubleOpenCustomHashMap.this.key[pos]) != 0) {
/* 1127 */           entry.index = pos;
/* 1128 */           consumer.accept(entry);
/*      */         }  }
/*      */     
/*      */     }
/*      */   }
/*      */   
/*      */   public Float2DoubleMap.FastEntrySet float2DoubleEntrySet() {
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
/*      */     extends MapIterator<FloatConsumer>
/*      */     implements FloatIterator
/*      */   {
/*      */     final void acceptOnIndex(FloatConsumer action, int index) {
/* 1157 */       action.accept(Float2DoubleOpenCustomHashMap.this.key[index]);
/*      */     }
/*      */ 
/*      */     
/*      */     public float nextFloat() {
/* 1162 */       return Float2DoubleOpenCustomHashMap.this.key[nextEntry()];
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
/* 1173 */       super(pos, max, mustReturnNull, hasSplit);
/*      */     }
/*      */ 
/*      */     
/*      */     public int characteristics() {
/* 1178 */       return this.hasSplit ? 257 : 321;
/*      */     }
/*      */ 
/*      */     
/*      */     final void acceptOnIndex(FloatConsumer action, int index) {
/* 1183 */       action.accept(Float2DoubleOpenCustomHashMap.this.key[index]);
/*      */     }
/*      */ 
/*      */     
/*      */     final KeySpliterator makeForSplit(int pos, int max, boolean mustReturnNull) {
/* 1188 */       return new KeySpliterator(pos, max, mustReturnNull, true);
/*      */     } }
/*      */   
/*      */   private final class KeySet extends AbstractFloatSet {
/*      */     private KeySet() {}
/*      */     
/*      */     public FloatIterator iterator() {
/* 1195 */       return new Float2DoubleOpenCustomHashMap.KeyIterator();
/*      */     }
/*      */ 
/*      */     
/*      */     public FloatSpliterator spliterator() {
/* 1200 */       return new Float2DoubleOpenCustomHashMap.KeySpliterator();
/*      */     }
/*      */ 
/*      */ 
/*      */     
/*      */     public void forEach(FloatConsumer consumer) {
/* 1206 */       if (Float2DoubleOpenCustomHashMap.this.containsNullKey) consumer.accept(Float2DoubleOpenCustomHashMap.this.key[Float2DoubleOpenCustomHashMap.this.n]); 
/* 1207 */       for (int pos = Float2DoubleOpenCustomHashMap.this.n; pos-- != 0; ) {
/* 1208 */         float k = Float2DoubleOpenCustomHashMap.this.key[pos];
/* 1209 */         if (Float.floatToIntBits(k) != 0) consumer.accept(k);
/*      */       
/*      */       } 
/*      */     }
/*      */     
/*      */     public int size() {
/* 1215 */       return Float2DoubleOpenCustomHashMap.this.size;
/*      */     }
/*      */ 
/*      */     
/*      */     public boolean contains(float k) {
/* 1220 */       return Float2DoubleOpenCustomHashMap.this.containsKey(k);
/*      */     }
/*      */ 
/*      */     
/*      */     public boolean remove(float k) {
/* 1225 */       int oldSize = Float2DoubleOpenCustomHashMap.this.size;
/* 1226 */       Float2DoubleOpenCustomHashMap.this.remove(k);
/* 1227 */       return (Float2DoubleOpenCustomHashMap.this.size != oldSize);
/*      */     }
/*      */ 
/*      */     
/*      */     public void clear() {
/* 1232 */       Float2DoubleOpenCustomHashMap.this.clear();
/*      */     }
/*      */   }
/*      */ 
/*      */   
/*      */   public FloatSet keySet() {
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
/*      */     extends MapIterator<DoubleConsumer>
/*      */     implements DoubleIterator
/*      */   {
/*      */     final void acceptOnIndex(DoubleConsumer action, int index) {
/* 1260 */       action.accept(Float2DoubleOpenCustomHashMap.this.value[index]);
/*      */     }
/*      */ 
/*      */     
/*      */     public double nextDouble() {
/* 1265 */       return Float2DoubleOpenCustomHashMap.this.value[nextEntry()];
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
/* 1276 */       super(pos, max, mustReturnNull, hasSplit);
/*      */     }
/*      */ 
/*      */     
/*      */     public int characteristics() {
/* 1281 */       return this.hasSplit ? 256 : 320;
/*      */     }
/*      */ 
/*      */     
/*      */     final void acceptOnIndex(DoubleConsumer action, int index) {
/* 1286 */       action.accept(Float2DoubleOpenCustomHashMap.this.value[index]);
/*      */     }
/*      */ 
/*      */     
/*      */     final ValueSpliterator makeForSplit(int pos, int max, boolean mustReturnNull) {
/* 1291 */       return new ValueSpliterator(pos, max, mustReturnNull, true);
/*      */     }
/*      */   }
/*      */ 
/*      */   
/*      */   public DoubleCollection values() {
/* 1297 */     if (this.values == null) this.values = (DoubleCollection)new AbstractDoubleCollection()
/*      */         {
/*      */           public DoubleIterator iterator() {
/* 1300 */             return new Float2DoubleOpenCustomHashMap.ValueIterator();
/*      */           }
/*      */ 
/*      */           
/*      */           public DoubleSpliterator spliterator() {
/* 1305 */             return new Float2DoubleOpenCustomHashMap.ValueSpliterator();
/*      */           }
/*      */ 
/*      */ 
/*      */           
/*      */           public void forEach(DoubleConsumer consumer) {
/* 1311 */             if (Float2DoubleOpenCustomHashMap.this.containsNullKey) consumer.accept(Float2DoubleOpenCustomHashMap.this.value[Float2DoubleOpenCustomHashMap.this.n]); 
/* 1312 */             for (int pos = Float2DoubleOpenCustomHashMap.this.n; pos-- != 0;) { if (Float.floatToIntBits(Float2DoubleOpenCustomHashMap.this.key[pos]) != 0) consumer.accept(Float2DoubleOpenCustomHashMap.this.value[pos]);  }
/*      */           
/*      */           }
/*      */           
/*      */           public int size() {
/* 1317 */             return Float2DoubleOpenCustomHashMap.this.size;
/*      */           }
/*      */ 
/*      */           
/*      */           public boolean contains(double v) {
/* 1322 */             return Float2DoubleOpenCustomHashMap.this.containsValue(v);
/*      */           }
/*      */ 
/*      */           
/*      */           public void clear() {
/* 1327 */             Float2DoubleOpenCustomHashMap.this.clear();
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
/* 1391 */     float[] key = this.key;
/* 1392 */     double[] value = this.value;
/* 1393 */     int mask = newN - 1;
/* 1394 */     float[] newKey = new float[newN + 1];
/* 1395 */     double[] newValue = new double[newN + 1];
/* 1396 */     int i = this.n;
/* 1397 */     for (int j = realSize(); j-- != 0; ) {
/* 1398 */       while (Float.floatToIntBits(key[--i]) == 0); int pos;
/* 1399 */       if (Float.floatToIntBits(newKey[pos = HashCommon.mix(this.strategy.hashCode(key[i])) & mask]) != 0) while (Float.floatToIntBits(newKey[pos = pos + 1 & mask]) != 0); 
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
/*      */   public Float2DoubleOpenCustomHashMap clone() {
/*      */     Float2DoubleOpenCustomHashMap c;
/*      */     try {
/* 1425 */       c = (Float2DoubleOpenCustomHashMap)super.clone();
/* 1426 */     } catch (CloneNotSupportedException cantHappen) {
/* 1427 */       throw new InternalError();
/*      */     } 
/* 1429 */     c.keys = null;
/* 1430 */     c.values = null;
/* 1431 */     c.entries = null;
/* 1432 */     c.containsNullKey = this.containsNullKey;
/* 1433 */     c.key = (float[])this.key.clone();
/* 1434 */     c.value = (double[])this.value.clone();
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
/* 1452 */       for (; Float.floatToIntBits(this.key[i]) == 0; i++);
/* 1453 */       t = this.strategy.hashCode(this.key[i]);
/* 1454 */       t ^= HashCommon.double2int(this.value[i]);
/* 1455 */       h += t;
/* 1456 */       i++;
/*      */     } 
/*      */     
/* 1459 */     if (this.containsNullKey) h += HashCommon.double2int(this.value[this.n]); 
/* 1460 */     return h;
/*      */   }
/*      */   
/*      */   private void writeObject(ObjectOutputStream s) throws IOException {
/* 1464 */     float[] key = this.key;
/* 1465 */     double[] value = this.value;
/* 1466 */     EntryIterator i = new EntryIterator();
/* 1467 */     s.defaultWriteObject();
/* 1468 */     for (int j = this.size; j-- != 0; ) {
/* 1469 */       int e = i.nextEntry();
/* 1470 */       s.writeFloat(key[e]);
/* 1471 */       s.writeDouble(value[e]);
/*      */     } 
/*      */   }
/*      */   
/*      */   private void readObject(ObjectInputStream s) throws IOException, ClassNotFoundException {
/* 1476 */     s.defaultReadObject();
/* 1477 */     this.n = HashCommon.arraySize(this.size, this.f);
/* 1478 */     this.maxFill = HashCommon.maxFill(this.n, this.f);
/* 1479 */     this.mask = this.n - 1;
/* 1480 */     float[] key = this.key = new float[this.n + 1];
/* 1481 */     double[] value = this.value = new double[this.n + 1];
/*      */ 
/*      */     
/* 1484 */     for (int i = this.size; i-- != 0; ) {
/* 1485 */       int pos; float k = s.readFloat();
/* 1486 */       double v = s.readDouble();
/* 1487 */       if (this.strategy.equals(k, 0.0F)) {
/* 1488 */         pos = this.n;
/* 1489 */         this.containsNullKey = true;
/*      */       } else {
/* 1491 */         pos = HashCommon.mix(this.strategy.hashCode(k)) & this.mask;
/* 1492 */         for (; Float.floatToIntBits(key[pos]) != 0; pos = pos + 1 & this.mask);
/*      */       } 
/* 1494 */       key[pos] = k;
/* 1495 */       value[pos] = v;
/*      */     } 
/*      */   }
/*      */   
/*      */   private void checkTable() {}
/*      */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\i\\unimi\dsi\fastutil\floats\Float2DoubleOpenCustomHashMap.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */