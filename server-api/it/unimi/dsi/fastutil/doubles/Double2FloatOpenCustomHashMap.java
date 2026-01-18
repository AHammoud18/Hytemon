/*      */ package it.unimi.dsi.fastutil.doubles;
/*      */ 
/*      */ import it.unimi.dsi.fastutil.Hash;
/*      */ import it.unimi.dsi.fastutil.HashCommon;
/*      */ import it.unimi.dsi.fastutil.SafeMath;
/*      */ import it.unimi.dsi.fastutil.floats.AbstractFloatCollection;
/*      */ import it.unimi.dsi.fastutil.floats.FloatCollection;
/*      */ import it.unimi.dsi.fastutil.floats.FloatConsumer;
/*      */ import it.unimi.dsi.fastutil.floats.FloatIterator;
/*      */ import it.unimi.dsi.fastutil.floats.FloatSpliterator;
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
/*      */ public class Double2FloatOpenCustomHashMap
/*      */   extends AbstractDouble2FloatMap
/*      */   implements Serializable, Cloneable, Hash
/*      */ {
/*      */   private static final long serialVersionUID = 0L;
/*      */   private static final boolean ASSERTS = false;
/*      */   protected transient double[] key;
/*      */   protected transient float[] value;
/*      */   protected transient int mask;
/*      */   protected transient boolean containsNullKey;
/*      */   protected DoubleHash.Strategy strategy;
/*      */   protected transient int n;
/*      */   protected transient int maxFill;
/*      */   protected final transient int minN;
/*      */   protected int size;
/*      */   protected final float f;
/*      */   protected transient Double2FloatMap.FastEntrySet entries;
/*      */   protected transient DoubleSet keys;
/*      */   protected transient FloatCollection values;
/*      */   
/*      */   public Double2FloatOpenCustomHashMap(int expected, float f, DoubleHash.Strategy strategy) {
/*  104 */     this.strategy = strategy;
/*  105 */     if (f <= 0.0F || f >= 1.0F) throw new IllegalArgumentException("Load factor must be greater than 0 and smaller than 1"); 
/*  106 */     if (expected < 0) throw new IllegalArgumentException("The expected number of elements must be nonnegative"); 
/*  107 */     this.f = f;
/*  108 */     this.minN = this.n = HashCommon.arraySize(expected, f);
/*  109 */     this.mask = this.n - 1;
/*  110 */     this.maxFill = HashCommon.maxFill(this.n, f);
/*  111 */     this.key = new double[this.n + 1];
/*  112 */     this.value = new float[this.n + 1];
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Double2FloatOpenCustomHashMap(int expected, DoubleHash.Strategy strategy) {
/*  122 */     this(expected, 0.75F, strategy);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Double2FloatOpenCustomHashMap(DoubleHash.Strategy strategy) {
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
/*      */   public Double2FloatOpenCustomHashMap(Map<? extends Double, ? extends Float> m, float f, DoubleHash.Strategy strategy) {
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
/*      */   public Double2FloatOpenCustomHashMap(Map<? extends Double, ? extends Float> m, DoubleHash.Strategy strategy) {
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
/*      */   public Double2FloatOpenCustomHashMap(Double2FloatMap m, float f, DoubleHash.Strategy strategy) {
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
/*      */   public Double2FloatOpenCustomHashMap(Double2FloatMap m, DoubleHash.Strategy strategy) {
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
/*      */   public Double2FloatOpenCustomHashMap(double[] k, float[] v, float f, DoubleHash.Strategy strategy) {
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
/*      */   public Double2FloatOpenCustomHashMap(double[] k, float[] v, DoubleHash.Strategy strategy) {
/*  205 */     this(k, v, 0.75F, strategy);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public DoubleHash.Strategy strategy() {
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
/*      */   private float removeEntry(int pos) {
/*  238 */     float oldValue = this.value[pos];
/*  239 */     this.size--;
/*  240 */     shiftKeys(pos);
/*  241 */     if (this.n > this.minN && this.size < this.maxFill / 4 && this.n > 16) rehash(this.n / 2); 
/*  242 */     return oldValue;
/*      */   }
/*      */   
/*      */   private float removeNullEntry() {
/*  246 */     this.containsNullKey = false;
/*  247 */     float oldValue = this.value[this.n];
/*  248 */     this.size--;
/*  249 */     if (this.n > this.minN && this.size < this.maxFill / 4 && this.n > 16) rehash(this.n / 2); 
/*  250 */     return oldValue;
/*      */   }
/*      */ 
/*      */   
/*      */   public void putAll(Map<? extends Double, ? extends Float> m) {
/*  255 */     if (this.f <= 0.5D) { ensureCapacity(m.size()); }
/*  256 */     else { tryCapacity((size() + m.size())); }
/*      */     
/*  258 */     super.putAll(m);
/*      */   }
/*      */   
/*      */   private int find(double k) {
/*  262 */     if (this.strategy.equals(k, 0.0D)) return this.containsNullKey ? this.n : -(this.n + 1);
/*      */     
/*  264 */     double[] key = this.key;
/*      */     double curr;
/*      */     int pos;
/*  267 */     if (Double.doubleToLongBits(curr = key[pos = HashCommon.mix(this.strategy.hashCode(k)) & this.mask]) == 0L) return -(pos + 1); 
/*  268 */     if (this.strategy.equals(k, curr)) return pos;
/*      */     
/*      */     while (true) {
/*  271 */       if (Double.doubleToLongBits(curr = key[pos = pos + 1 & this.mask]) == 0L) return -(pos + 1); 
/*  272 */       if (this.strategy.equals(k, curr)) return pos; 
/*      */     } 
/*      */   }
/*      */   
/*      */   private void insert(int pos, double k, float v) {
/*  277 */     if (pos == this.n) this.containsNullKey = true; 
/*  278 */     this.key[pos] = k;
/*  279 */     this.value[pos] = v;
/*  280 */     if (this.size++ >= this.maxFill) rehash(HashCommon.arraySize(this.size + 1, this.f));
/*      */   
/*      */   }
/*      */ 
/*      */   
/*      */   public float put(double k, float v) {
/*  286 */     int pos = find(k);
/*  287 */     if (pos < 0) {
/*  288 */       insert(-pos - 1, k, v);
/*  289 */       return this.defRetValue;
/*      */     } 
/*  291 */     float oldValue = this.value[pos];
/*  292 */     this.value[pos] = v;
/*  293 */     return oldValue;
/*      */   }
/*      */   
/*      */   private float addToValue(int pos, float incr) {
/*  297 */     float oldValue = this.value[pos];
/*  298 */     this.value[pos] = oldValue + incr;
/*  299 */     return oldValue;
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
/*      */   public float addTo(double k, float incr) {
/*      */     int pos;
/*  317 */     if (this.strategy.equals(k, 0.0D)) {
/*  318 */       if (this.containsNullKey) return addToValue(this.n, incr); 
/*  319 */       pos = this.n;
/*  320 */       this.containsNullKey = true;
/*      */     } else {
/*      */       
/*  323 */       double[] key = this.key;
/*      */       double curr;
/*  325 */       if (Double.doubleToLongBits(curr = key[pos = HashCommon.mix(this.strategy.hashCode(k)) & this.mask]) != 0L) {
/*  326 */         if (this.strategy.equals(curr, k)) return addToValue(pos, incr); 
/*  327 */         while (Double.doubleToLongBits(curr = key[pos = pos + 1 & this.mask]) != 0L) { if (this.strategy.equals(curr, k)) return addToValue(pos, incr);  }
/*      */       
/*      */       } 
/*  330 */     }  this.key[pos] = k;
/*  331 */     this.value[pos] = this.defRetValue + incr;
/*  332 */     if (this.size++ >= this.maxFill) rehash(HashCommon.arraySize(this.size + 1, this.f));
/*      */     
/*  334 */     return this.defRetValue;
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
/*  347 */     double[] key = this.key; while (true) {
/*      */       double curr; int last;
/*  349 */       pos = (last = pos) + 1 & this.mask;
/*      */       while (true) {
/*  351 */         if (Double.doubleToLongBits(curr = key[pos]) == 0L) {
/*  352 */           key[last] = 0.0D;
/*      */           return;
/*      */         } 
/*  355 */         int slot = HashCommon.mix(this.strategy.hashCode(curr)) & this.mask;
/*  356 */         if ((last <= pos) ? (last >= slot || slot > pos) : (last >= slot && slot > pos))
/*  357 */           break;  pos = pos + 1 & this.mask;
/*      */       } 
/*  359 */       key[last] = curr;
/*  360 */       this.value[last] = this.value[pos];
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public float remove(double k) {
/*  367 */     if (this.strategy.equals(k, 0.0D)) {
/*  368 */       if (this.containsNullKey) return removeNullEntry(); 
/*  369 */       return this.defRetValue;
/*      */     } 
/*      */     
/*  372 */     double[] key = this.key;
/*      */     double curr;
/*      */     int pos;
/*  375 */     if (Double.doubleToLongBits(curr = key[pos = HashCommon.mix(this.strategy.hashCode(k)) & this.mask]) == 0L) return this.defRetValue; 
/*  376 */     if (this.strategy.equals(k, curr)) return removeEntry(pos); 
/*      */     while (true) {
/*  378 */       if (Double.doubleToLongBits(curr = key[pos = pos + 1 & this.mask]) == 0L) return this.defRetValue; 
/*  379 */       if (this.strategy.equals(k, curr)) return removeEntry(pos);
/*      */     
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   public float get(double k) {
/*  386 */     if (this.strategy.equals(k, 0.0D)) return this.containsNullKey ? this.value[this.n] : this.defRetValue;
/*      */     
/*  388 */     double[] key = this.key;
/*      */     double curr;
/*      */     int pos;
/*  391 */     if (Double.doubleToLongBits(curr = key[pos = HashCommon.mix(this.strategy.hashCode(k)) & this.mask]) == 0L) return this.defRetValue; 
/*  392 */     if (this.strategy.equals(k, curr)) return this.value[pos];
/*      */     
/*      */     while (true) {
/*  395 */       if (Double.doubleToLongBits(curr = key[pos = pos + 1 & this.mask]) == 0L) return this.defRetValue; 
/*  396 */       if (this.strategy.equals(k, curr)) return this.value[pos];
/*      */     
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   public boolean containsKey(double k) {
/*  403 */     if (this.strategy.equals(k, 0.0D)) return this.containsNullKey;
/*      */     
/*  405 */     double[] key = this.key;
/*      */     double curr;
/*      */     int pos;
/*  408 */     if (Double.doubleToLongBits(curr = key[pos = HashCommon.mix(this.strategy.hashCode(k)) & this.mask]) == 0L) return false; 
/*  409 */     if (this.strategy.equals(k, curr)) return true;
/*      */     
/*      */     while (true) {
/*  412 */       if (Double.doubleToLongBits(curr = key[pos = pos + 1 & this.mask]) == 0L) return false; 
/*  413 */       if (this.strategy.equals(k, curr)) return true;
/*      */     
/*      */     } 
/*      */   }
/*      */   
/*      */   public boolean containsValue(float v) {
/*  419 */     float[] value = this.value;
/*  420 */     double[] key = this.key;
/*  421 */     if (this.containsNullKey && Float.floatToIntBits(value[this.n]) == Float.floatToIntBits(v)) return true; 
/*  422 */     for (int i = this.n; i-- != 0;) { if (Double.doubleToLongBits(key[i]) != 0L && Float.floatToIntBits(value[i]) == Float.floatToIntBits(v)) return true;  }
/*  423 */      return false;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public float getOrDefault(double k, float defaultValue) {
/*  430 */     if (this.strategy.equals(k, 0.0D)) return this.containsNullKey ? this.value[this.n] : defaultValue;
/*      */     
/*  432 */     double[] key = this.key;
/*      */     double curr;
/*      */     int pos;
/*  435 */     if (Double.doubleToLongBits(curr = key[pos = HashCommon.mix(this.strategy.hashCode(k)) & this.mask]) == 0L) return defaultValue; 
/*  436 */     if (this.strategy.equals(k, curr)) return this.value[pos];
/*      */     
/*      */     while (true) {
/*  439 */       if (Double.doubleToLongBits(curr = key[pos = pos + 1 & this.mask]) == 0L) return defaultValue; 
/*  440 */       if (this.strategy.equals(k, curr)) return this.value[pos];
/*      */     
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   public float putIfAbsent(double k, float v) {
/*  447 */     int pos = find(k);
/*  448 */     if (pos >= 0) return this.value[pos]; 
/*  449 */     insert(-pos - 1, k, v);
/*  450 */     return this.defRetValue;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean remove(double k, float v) {
/*  457 */     if (this.strategy.equals(k, 0.0D)) {
/*  458 */       if (this.containsNullKey && Float.floatToIntBits(v) == Float.floatToIntBits(this.value[this.n])) {
/*  459 */         removeNullEntry();
/*  460 */         return true;
/*      */       } 
/*  462 */       return false;
/*      */     } 
/*      */     
/*  465 */     double[] key = this.key;
/*      */     double curr;
/*      */     int pos;
/*  468 */     if (Double.doubleToLongBits(curr = key[pos = HashCommon.mix(this.strategy.hashCode(k)) & this.mask]) == 0L) return false; 
/*  469 */     if (this.strategy.equals(k, curr) && Float.floatToIntBits(v) == Float.floatToIntBits(this.value[pos])) {
/*  470 */       removeEntry(pos);
/*  471 */       return true;
/*      */     } 
/*      */     while (true) {
/*  474 */       if (Double.doubleToLongBits(curr = key[pos = pos + 1 & this.mask]) == 0L) return false; 
/*  475 */       if (this.strategy.equals(k, curr) && Float.floatToIntBits(v) == Float.floatToIntBits(this.value[pos])) {
/*  476 */         removeEntry(pos);
/*  477 */         return true;
/*      */       } 
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean replace(double k, float oldValue, float v) {
/*  485 */     int pos = find(k);
/*  486 */     if (pos < 0 || Float.floatToIntBits(oldValue) != Float.floatToIntBits(this.value[pos])) return false; 
/*  487 */     this.value[pos] = v;
/*  488 */     return true;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public float replace(double k, float v) {
/*  494 */     int pos = find(k);
/*  495 */     if (pos < 0) return this.defRetValue; 
/*  496 */     float oldValue = this.value[pos];
/*  497 */     this.value[pos] = v;
/*  498 */     return oldValue;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public float computeIfAbsent(double k, DoubleUnaryOperator mappingFunction) {
/*  504 */     Objects.requireNonNull(mappingFunction);
/*  505 */     int pos = find(k);
/*  506 */     if (pos >= 0) return this.value[pos]; 
/*  507 */     float newValue = SafeMath.safeDoubleToFloat(mappingFunction.applyAsDouble(k));
/*  508 */     insert(-pos - 1, k, newValue);
/*  509 */     return newValue;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public float computeIfAbsent(double key, Double2FloatFunction mappingFunction) {
/*  515 */     Objects.requireNonNull(mappingFunction);
/*  516 */     int pos = find(key);
/*  517 */     if (pos >= 0) return this.value[pos]; 
/*  518 */     if (!mappingFunction.containsKey(key)) return this.defRetValue; 
/*  519 */     float newValue = mappingFunction.get(key);
/*  520 */     insert(-pos - 1, key, newValue);
/*  521 */     return newValue;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public float computeIfAbsentNullable(double k, DoubleFunction<? extends Float> mappingFunction) {
/*  527 */     Objects.requireNonNull(mappingFunction);
/*  528 */     int pos = find(k);
/*  529 */     if (pos >= 0) return this.value[pos]; 
/*  530 */     Float newValue = mappingFunction.apply(k);
/*  531 */     if (newValue == null) return this.defRetValue; 
/*  532 */     float v = newValue.floatValue();
/*  533 */     insert(-pos - 1, k, v);
/*  534 */     return v;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public float computeIfPresent(double k, BiFunction<? super Double, ? super Float, ? extends Float> remappingFunction) {
/*  540 */     Objects.requireNonNull(remappingFunction);
/*  541 */     int pos = find(k);
/*  542 */     if (pos < 0) return this.defRetValue; 
/*  543 */     Float newValue = remappingFunction.apply(Double.valueOf(k), Float.valueOf(this.value[pos]));
/*  544 */     if (newValue == null) {
/*  545 */       if (this.strategy.equals(k, 0.0D)) { removeNullEntry(); }
/*  546 */       else { removeEntry(pos); }
/*  547 */        return this.defRetValue;
/*      */     } 
/*  549 */     this.value[pos] = newValue.floatValue(); return newValue.floatValue();
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public float compute(double k, BiFunction<? super Double, ? super Float, ? extends Float> remappingFunction) {
/*  555 */     Objects.requireNonNull(remappingFunction);
/*  556 */     int pos = find(k);
/*  557 */     Float newValue = remappingFunction.apply(Double.valueOf(k), (pos >= 0) ? Float.valueOf(this.value[pos]) : null);
/*  558 */     if (newValue == null) {
/*  559 */       if (pos >= 0)
/*  560 */         if (this.strategy.equals(k, 0.0D)) { removeNullEntry(); }
/*  561 */         else { removeEntry(pos); }
/*      */          
/*  563 */       return this.defRetValue;
/*      */     } 
/*  565 */     float newVal = newValue.floatValue();
/*  566 */     if (pos < 0) {
/*  567 */       insert(-pos - 1, k, newVal);
/*  568 */       return newVal;
/*      */     } 
/*  570 */     this.value[pos] = newVal; return newVal;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public float merge(double k, float v, BiFunction<? super Float, ? super Float, ? extends Float> remappingFunction) {
/*  576 */     Objects.requireNonNull(remappingFunction);
/*      */     
/*  578 */     int pos = find(k);
/*  579 */     if (pos < 0) {
/*  580 */       if (pos < 0) { insert(-pos - 1, k, v); }
/*  581 */       else { this.value[pos] = v; }
/*  582 */        return v;
/*      */     } 
/*  584 */     Float newValue = remappingFunction.apply(Float.valueOf(this.value[pos]), Float.valueOf(v));
/*  585 */     if (newValue == null) {
/*  586 */       if (this.strategy.equals(k, 0.0D)) { removeNullEntry(); }
/*  587 */       else { removeEntry(pos); }
/*  588 */        return this.defRetValue;
/*      */     } 
/*  590 */     this.value[pos] = newValue.floatValue(); return newValue.floatValue();
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
/*  601 */     if (this.size == 0)
/*  602 */       return;  this.size = 0;
/*  603 */     this.containsNullKey = false;
/*  604 */     Arrays.fill(this.key, 0.0D);
/*      */   }
/*      */ 
/*      */   
/*      */   public int size() {
/*  609 */     return this.size;
/*      */   }
/*      */ 
/*      */   
/*      */   public boolean isEmpty() {
/*  614 */     return (this.size == 0);
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   final class MapEntry
/*      */     implements Double2FloatMap.Entry, Map.Entry<Double, Float>, DoubleFloatPair
/*      */   {
/*      */     int index;
/*      */ 
/*      */ 
/*      */     
/*      */     MapEntry(int index) {
/*  627 */       this.index = index;
/*      */     }
/*      */ 
/*      */     
/*      */     MapEntry() {}
/*      */ 
/*      */     
/*      */     public double getDoubleKey() {
/*  635 */       return Double2FloatOpenCustomHashMap.this.key[this.index];
/*      */     }
/*      */ 
/*      */     
/*      */     public double leftDouble() {
/*  640 */       return Double2FloatOpenCustomHashMap.this.key[this.index];
/*      */     }
/*      */ 
/*      */     
/*      */     public float getFloatValue() {
/*  645 */       return Double2FloatOpenCustomHashMap.this.value[this.index];
/*      */     }
/*      */ 
/*      */     
/*      */     public float rightFloat() {
/*  650 */       return Double2FloatOpenCustomHashMap.this.value[this.index];
/*      */     }
/*      */ 
/*      */     
/*      */     public float setValue(float v) {
/*  655 */       float oldValue = Double2FloatOpenCustomHashMap.this.value[this.index];
/*  656 */       Double2FloatOpenCustomHashMap.this.value[this.index] = v;
/*  657 */       return oldValue;
/*      */     }
/*      */ 
/*      */     
/*      */     public DoubleFloatPair right(float v) {
/*  662 */       Double2FloatOpenCustomHashMap.this.value[this.index] = v;
/*  663 */       return this;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     @Deprecated
/*      */     public Double getKey() {
/*  674 */       return Double.valueOf(Double2FloatOpenCustomHashMap.this.key[this.index]);
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
/*  685 */       return Float.valueOf(Double2FloatOpenCustomHashMap.this.value[this.index]);
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
/*  696 */       return Float.valueOf(setValue(v.floatValue()));
/*      */     }
/*      */ 
/*      */ 
/*      */     
/*      */     public boolean equals(Object o) {
/*  702 */       if (!(o instanceof Map.Entry)) return false; 
/*  703 */       Map.Entry<Double, Float> e = (Map.Entry<Double, Float>)o;
/*  704 */       return (Double2FloatOpenCustomHashMap.this.strategy.equals(Double2FloatOpenCustomHashMap.this.key[this.index], ((Double)e.getKey()).doubleValue()) && Float.floatToIntBits(Double2FloatOpenCustomHashMap.this.value[this.index]) == Float.floatToIntBits(((Float)e.getValue()).floatValue()));
/*      */     }
/*      */ 
/*      */     
/*      */     public int hashCode() {
/*  709 */       return Double2FloatOpenCustomHashMap.this.strategy.hashCode(Double2FloatOpenCustomHashMap.this.key[this.index]) ^ HashCommon.float2int(Double2FloatOpenCustomHashMap.this.value[this.index]);
/*      */     }
/*      */ 
/*      */     
/*      */     public String toString() {
/*  714 */       return Double2FloatOpenCustomHashMap.this.key[this.index] + "=>" + Double2FloatOpenCustomHashMap.this.value[this.index];
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
/*  725 */     int pos = Double2FloatOpenCustomHashMap.this.n;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  731 */     int last = -1;
/*      */     
/*  733 */     int c = Double2FloatOpenCustomHashMap.this.size;
/*      */     
/*  735 */     boolean mustReturnNullKey = Double2FloatOpenCustomHashMap.this.containsNullKey;
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     DoubleArrayList wrapped;
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public boolean hasNext() {
/*  746 */       return (this.c != 0);
/*      */     }
/*      */     
/*      */     public int nextEntry() {
/*  750 */       if (!hasNext()) throw new NoSuchElementException(); 
/*  751 */       this.c--;
/*  752 */       if (this.mustReturnNullKey) {
/*  753 */         this.mustReturnNullKey = false;
/*  754 */         return this.last = Double2FloatOpenCustomHashMap.this.n;
/*      */       } 
/*  756 */       double[] key = Double2FloatOpenCustomHashMap.this.key;
/*      */       while (true) {
/*  758 */         if (--this.pos < 0) {
/*      */           
/*  760 */           this.last = Integer.MIN_VALUE;
/*  761 */           double k = this.wrapped.getDouble(-this.pos - 1);
/*  762 */           int p = HashCommon.mix(Double2FloatOpenCustomHashMap.this.strategy.hashCode(k)) & Double2FloatOpenCustomHashMap.this.mask;
/*  763 */           for (; !Double2FloatOpenCustomHashMap.this.strategy.equals(k, key[p]); p = p + 1 & Double2FloatOpenCustomHashMap.this.mask);
/*  764 */           return p;
/*      */         } 
/*  766 */         if (Double.doubleToLongBits(key[this.pos]) != 0L) return this.last = this.pos; 
/*      */       } 
/*      */     }
/*      */     
/*      */     public void forEachRemaining(ConsumerType action) {
/*  771 */       if (this.mustReturnNullKey) {
/*  772 */         this.mustReturnNullKey = false;
/*  773 */         acceptOnIndex(action, this.last = Double2FloatOpenCustomHashMap.this.n);
/*  774 */         this.c--;
/*      */       } 
/*  776 */       double[] key = Double2FloatOpenCustomHashMap.this.key;
/*  777 */       while (this.c != 0) {
/*  778 */         if (--this.pos < 0) {
/*      */           
/*  780 */           this.last = Integer.MIN_VALUE;
/*  781 */           double k = this.wrapped.getDouble(-this.pos - 1);
/*  782 */           int p = HashCommon.mix(Double2FloatOpenCustomHashMap.this.strategy.hashCode(k)) & Double2FloatOpenCustomHashMap.this.mask;
/*  783 */           for (; !Double2FloatOpenCustomHashMap.this.strategy.equals(k, key[p]); p = p + 1 & Double2FloatOpenCustomHashMap.this.mask);
/*  784 */           acceptOnIndex(action, p);
/*  785 */           this.c--; continue;
/*  786 */         }  if (Double.doubleToLongBits(key[this.pos]) != 0L) {
/*  787 */           acceptOnIndex(action, this.last = this.pos);
/*  788 */           this.c--;
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
/*  803 */       double[] key = Double2FloatOpenCustomHashMap.this.key; while (true) {
/*      */         double curr; int last;
/*  805 */         pos = (last = pos) + 1 & Double2FloatOpenCustomHashMap.this.mask;
/*      */         while (true) {
/*  807 */           if (Double.doubleToLongBits(curr = key[pos]) == 0L) {
/*  808 */             key[last] = 0.0D;
/*      */             return;
/*      */           } 
/*  811 */           int slot = HashCommon.mix(Double2FloatOpenCustomHashMap.this.strategy.hashCode(curr)) & Double2FloatOpenCustomHashMap.this.mask;
/*  812 */           if ((last <= pos) ? (last >= slot || slot > pos) : (last >= slot && slot > pos))
/*  813 */             break;  pos = pos + 1 & Double2FloatOpenCustomHashMap.this.mask;
/*      */         } 
/*  815 */         if (pos < last) {
/*  816 */           if (this.wrapped == null) this.wrapped = new DoubleArrayList(2); 
/*  817 */           this.wrapped.add(key[pos]);
/*      */         } 
/*  819 */         key[last] = curr;
/*  820 */         Double2FloatOpenCustomHashMap.this.value[last] = Double2FloatOpenCustomHashMap.this.value[pos];
/*      */       } 
/*      */     }
/*      */     
/*      */     public void remove() {
/*  825 */       if (this.last == -1) throw new IllegalStateException(); 
/*  826 */       if (this.last == Double2FloatOpenCustomHashMap.this.n)
/*  827 */       { Double2FloatOpenCustomHashMap.this.containsNullKey = false; }
/*  828 */       else if (this.pos >= 0) { shiftKeys(this.last); }
/*      */       else
/*      */       
/*  831 */       { Double2FloatOpenCustomHashMap.this.remove(this.wrapped.getDouble(-this.pos - 1));
/*  832 */         this.last = -1;
/*      */         return; }
/*      */       
/*  835 */       Double2FloatOpenCustomHashMap.this.size--;
/*  836 */       this.last = -1;
/*      */     }
/*      */ 
/*      */     
/*      */     public int skip(int n) {
/*  841 */       int i = n;
/*  842 */       for (; i-- != 0 && hasNext(); nextEntry());
/*  843 */       return n - i - 1;
/*      */     }
/*      */     
/*      */     private MapIterator() {}
/*      */     
/*      */     abstract void acceptOnIndex(ConsumerType param1ConsumerType, int param1Int);
/*      */   }
/*      */   
/*      */   private final class EntryIterator extends MapIterator<Consumer<? super Double2FloatMap.Entry>> implements ObjectIterator<Double2FloatMap.Entry> { public Double2FloatOpenCustomHashMap.MapEntry next() {
/*  852 */       return this.entry = new Double2FloatOpenCustomHashMap.MapEntry(nextEntry());
/*      */     }
/*      */     private Double2FloatOpenCustomHashMap.MapEntry entry;
/*      */     private EntryIterator() {}
/*      */     
/*      */     final void acceptOnIndex(Consumer<? super Double2FloatMap.Entry> action, int index) {
/*  858 */       action.accept(this.entry = new Double2FloatOpenCustomHashMap.MapEntry(index));
/*      */     }
/*      */ 
/*      */     
/*      */     public void remove() {
/*  863 */       super.remove();
/*  864 */       this.entry.index = -1;
/*      */     } }
/*      */   
/*      */   private final class FastEntryIterator extends MapIterator<Consumer<? super Double2FloatMap.Entry>> implements ObjectIterator<Double2FloatMap.Entry> {
/*      */     private FastEntryIterator() {
/*  869 */       this.entry = new Double2FloatOpenCustomHashMap.MapEntry();
/*      */     }
/*      */     private final Double2FloatOpenCustomHashMap.MapEntry entry;
/*      */     public Double2FloatOpenCustomHashMap.MapEntry next() {
/*  873 */       this.entry.index = nextEntry();
/*  874 */       return this.entry;
/*      */     }
/*      */ 
/*      */ 
/*      */     
/*      */     final void acceptOnIndex(Consumer<? super Double2FloatMap.Entry> action, int index) {
/*  880 */       this.entry.index = index;
/*  881 */       action.accept(this.entry);
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private abstract class MapSpliterator<ConsumerType, SplitType extends MapSpliterator<ConsumerType, SplitType>>
/*      */   {
/*  890 */     int pos = 0;
/*      */     
/*  892 */     int max = Double2FloatOpenCustomHashMap.this.n;
/*      */     
/*  894 */     int c = 0;
/*      */     
/*  896 */     boolean mustReturnNull = Double2FloatOpenCustomHashMap.this.containsNullKey;
/*      */     
/*      */     boolean hasSplit = false;
/*      */     
/*      */     MapSpliterator() {}
/*      */     
/*      */     MapSpliterator(int pos, int max, boolean mustReturnNull, boolean hasSplit) {
/*  903 */       this.pos = pos;
/*  904 */       this.max = max;
/*  905 */       this.mustReturnNull = mustReturnNull;
/*  906 */       this.hasSplit = hasSplit;
/*      */     }
/*      */     
/*      */     abstract void acceptOnIndex(ConsumerType param1ConsumerType, int param1Int);
/*      */     
/*      */     abstract SplitType makeForSplit(int param1Int1, int param1Int2, boolean param1Boolean);
/*      */     
/*      */     public boolean tryAdvance(ConsumerType action) {
/*  914 */       if (this.mustReturnNull) {
/*  915 */         this.mustReturnNull = false;
/*  916 */         this.c++;
/*  917 */         acceptOnIndex(action, Double2FloatOpenCustomHashMap.this.n);
/*  918 */         return true;
/*      */       } 
/*  920 */       double[] key = Double2FloatOpenCustomHashMap.this.key;
/*  921 */       while (this.pos < this.max) {
/*  922 */         if (Double.doubleToLongBits(key[this.pos]) != 0L) {
/*  923 */           this.c++;
/*  924 */           acceptOnIndex(action, this.pos++);
/*  925 */           return true;
/*      */         } 
/*  927 */         this.pos++;
/*      */       } 
/*  929 */       return false;
/*      */     }
/*      */     
/*      */     public void forEachRemaining(ConsumerType action) {
/*  933 */       if (this.mustReturnNull) {
/*  934 */         this.mustReturnNull = false;
/*  935 */         this.c++;
/*  936 */         acceptOnIndex(action, Double2FloatOpenCustomHashMap.this.n);
/*      */       } 
/*  938 */       double[] key = Double2FloatOpenCustomHashMap.this.key;
/*  939 */       while (this.pos < this.max) {
/*  940 */         if (Double.doubleToLongBits(key[this.pos]) != 0L) {
/*  941 */           acceptOnIndex(action, this.pos);
/*  942 */           this.c++;
/*      */         } 
/*  944 */         this.pos++;
/*      */       } 
/*      */     }
/*      */     
/*      */     public long estimateSize() {
/*  949 */       if (!this.hasSplit)
/*      */       {
/*  951 */         return (Double2FloatOpenCustomHashMap.this.size - this.c);
/*      */       }
/*      */ 
/*      */ 
/*      */       
/*  956 */       return Math.min((Double2FloatOpenCustomHashMap.this.size - this.c), (long)(Double2FloatOpenCustomHashMap.this.realSize() / Double2FloatOpenCustomHashMap.this.n * (this.max - this.pos)) + (this.mustReturnNull ? 1L : 0L));
/*      */     }
/*      */ 
/*      */     
/*      */     public SplitType trySplit() {
/*  961 */       if (this.pos >= this.max - 1) return null; 
/*  962 */       int retLen = this.max - this.pos >> 1;
/*  963 */       if (retLen <= 1) return null; 
/*  964 */       int myNewPos = this.pos + retLen;
/*  965 */       int retPos = this.pos;
/*  966 */       int retMax = myNewPos;
/*      */ 
/*      */ 
/*      */       
/*  970 */       SplitType split = makeForSplit(retPos, retMax, this.mustReturnNull);
/*  971 */       this.pos = myNewPos;
/*  972 */       this.mustReturnNull = false;
/*  973 */       this.hasSplit = true;
/*  974 */       return split;
/*      */     }
/*      */     
/*      */     public long skip(long n) {
/*  978 */       if (n < 0L) throw new IllegalArgumentException("Argument must be nonnegative: " + n); 
/*  979 */       if (n == 0L) return 0L; 
/*  980 */       long skipped = 0L;
/*  981 */       if (this.mustReturnNull) {
/*  982 */         this.mustReturnNull = false;
/*  983 */         skipped++;
/*  984 */         n--;
/*      */       } 
/*  986 */       double[] key = Double2FloatOpenCustomHashMap.this.key;
/*  987 */       while (this.pos < this.max && n > 0L) {
/*  988 */         if (Double.doubleToLongBits(key[this.pos++]) != 0L) {
/*  989 */           skipped++;
/*  990 */           n--;
/*      */         } 
/*      */       } 
/*  993 */       return skipped;
/*      */     }
/*      */   }
/*      */   
/*      */   private final class EntrySpliterator
/*      */     extends MapSpliterator<Consumer<? super Double2FloatMap.Entry>, EntrySpliterator> implements ObjectSpliterator<Double2FloatMap.Entry> {
/*      */     private static final int POST_SPLIT_CHARACTERISTICS = 1;
/*      */     
/*      */     EntrySpliterator() {}
/*      */     
/*      */     EntrySpliterator(int pos, int max, boolean mustReturnNull, boolean hasSplit) {
/* 1004 */       super(pos, max, mustReturnNull, hasSplit);
/*      */     }
/*      */ 
/*      */     
/*      */     public int characteristics() {
/* 1009 */       return this.hasSplit ? 1 : 65;
/*      */     }
/*      */ 
/*      */     
/*      */     final void acceptOnIndex(Consumer<? super Double2FloatMap.Entry> action, int index) {
/* 1014 */       action.accept(new Double2FloatOpenCustomHashMap.MapEntry(index));
/*      */     }
/*      */ 
/*      */     
/*      */     final EntrySpliterator makeForSplit(int pos, int max, boolean mustReturnNull) {
/* 1019 */       return new EntrySpliterator(pos, max, mustReturnNull, true);
/*      */     } }
/*      */   
/*      */   private final class MapEntrySet extends AbstractObjectSet<Double2FloatMap.Entry> implements Double2FloatMap.FastEntrySet {
/*      */     private MapEntrySet() {}
/*      */     
/*      */     public ObjectIterator<Double2FloatMap.Entry> iterator() {
/* 1026 */       return new Double2FloatOpenCustomHashMap.EntryIterator();
/*      */     }
/*      */ 
/*      */     
/*      */     public ObjectIterator<Double2FloatMap.Entry> fastIterator() {
/* 1031 */       return new Double2FloatOpenCustomHashMap.FastEntryIterator();
/*      */     }
/*      */ 
/*      */     
/*      */     public ObjectSpliterator<Double2FloatMap.Entry> spliterator() {
/* 1036 */       return new Double2FloatOpenCustomHashMap.EntrySpliterator();
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public boolean contains(Object o) {
/* 1043 */       if (!(o instanceof Map.Entry)) return false; 
/* 1044 */       Map.Entry<?, ?> e = (Map.Entry<?, ?>)o;
/* 1045 */       if (e.getKey() == null || !(e.getKey() instanceof Double)) return false; 
/* 1046 */       if (e.getValue() == null || !(e.getValue() instanceof Float)) return false; 
/* 1047 */       double k = ((Double)e.getKey()).doubleValue();
/* 1048 */       float v = ((Float)e.getValue()).floatValue();
/* 1049 */       if (Double2FloatOpenCustomHashMap.this.strategy.equals(k, 0.0D)) return (Double2FloatOpenCustomHashMap.this.containsNullKey && Float.floatToIntBits(Double2FloatOpenCustomHashMap.this.value[Double2FloatOpenCustomHashMap.this.n]) == Float.floatToIntBits(v));
/*      */       
/* 1051 */       double[] key = Double2FloatOpenCustomHashMap.this.key;
/*      */       double curr;
/*      */       int pos;
/* 1054 */       if (Double.doubleToLongBits(curr = key[pos = HashCommon.mix(Double2FloatOpenCustomHashMap.this.strategy.hashCode(k)) & Double2FloatOpenCustomHashMap.this.mask]) == 0L) return false; 
/* 1055 */       if (Double2FloatOpenCustomHashMap.this.strategy.equals(k, curr)) return (Float.floatToIntBits(Double2FloatOpenCustomHashMap.this.value[pos]) == Float.floatToIntBits(v));
/*      */       
/*      */       while (true) {
/* 1058 */         if (Double.doubleToLongBits(curr = key[pos = pos + 1 & Double2FloatOpenCustomHashMap.this.mask]) == 0L) return false; 
/* 1059 */         if (Double2FloatOpenCustomHashMap.this.strategy.equals(k, curr)) return (Float.floatToIntBits(Double2FloatOpenCustomHashMap.this.value[pos]) == Float.floatToIntBits(v));
/*      */       
/*      */       } 
/*      */     }
/*      */ 
/*      */     
/*      */     public boolean remove(Object o) {
/* 1066 */       if (!(o instanceof Map.Entry)) return false; 
/* 1067 */       Map.Entry<?, ?> e = (Map.Entry<?, ?>)o;
/* 1068 */       if (e.getKey() == null || !(e.getKey() instanceof Double)) return false; 
/* 1069 */       if (e.getValue() == null || !(e.getValue() instanceof Float)) return false; 
/* 1070 */       double k = ((Double)e.getKey()).doubleValue();
/* 1071 */       float v = ((Float)e.getValue()).floatValue();
/* 1072 */       if (Double2FloatOpenCustomHashMap.this.strategy.equals(k, 0.0D)) {
/* 1073 */         if (Double2FloatOpenCustomHashMap.this.containsNullKey && Float.floatToIntBits(Double2FloatOpenCustomHashMap.this.value[Double2FloatOpenCustomHashMap.this.n]) == Float.floatToIntBits(v)) {
/* 1074 */           Double2FloatOpenCustomHashMap.this.removeNullEntry();
/* 1075 */           return true;
/*      */         } 
/* 1077 */         return false;
/*      */       } 
/*      */       
/* 1080 */       double[] key = Double2FloatOpenCustomHashMap.this.key;
/*      */       double curr;
/*      */       int pos;
/* 1083 */       if (Double.doubleToLongBits(curr = key[pos = HashCommon.mix(Double2FloatOpenCustomHashMap.this.strategy.hashCode(k)) & Double2FloatOpenCustomHashMap.this.mask]) == 0L) return false; 
/* 1084 */       if (Double2FloatOpenCustomHashMap.this.strategy.equals(curr, k)) {
/* 1085 */         if (Float.floatToIntBits(Double2FloatOpenCustomHashMap.this.value[pos]) == Float.floatToIntBits(v)) {
/* 1086 */           Double2FloatOpenCustomHashMap.this.removeEntry(pos);
/* 1087 */           return true;
/*      */         } 
/* 1089 */         return false;
/*      */       } 
/*      */       while (true) {
/* 1092 */         if (Double.doubleToLongBits(curr = key[pos = pos + 1 & Double2FloatOpenCustomHashMap.this.mask]) == 0L) return false; 
/* 1093 */         if (Double2FloatOpenCustomHashMap.this.strategy.equals(curr, k) && 
/* 1094 */           Float.floatToIntBits(Double2FloatOpenCustomHashMap.this.value[pos]) == Float.floatToIntBits(v)) {
/* 1095 */           Double2FloatOpenCustomHashMap.this.removeEntry(pos);
/* 1096 */           return true;
/*      */         } 
/*      */       } 
/*      */     }
/*      */ 
/*      */ 
/*      */     
/*      */     public int size() {
/* 1104 */       return Double2FloatOpenCustomHashMap.this.size;
/*      */     }
/*      */ 
/*      */     
/*      */     public void clear() {
/* 1109 */       Double2FloatOpenCustomHashMap.this.clear();
/*      */     }
/*      */ 
/*      */ 
/*      */     
/*      */     public void forEach(Consumer<? super Double2FloatMap.Entry> consumer) {
/* 1115 */       if (Double2FloatOpenCustomHashMap.this.containsNullKey) consumer.accept(new Double2FloatOpenCustomHashMap.MapEntry(Double2FloatOpenCustomHashMap.this.n)); 
/* 1116 */       for (int pos = Double2FloatOpenCustomHashMap.this.n; pos-- != 0;) { if (Double.doubleToLongBits(Double2FloatOpenCustomHashMap.this.key[pos]) != 0L) consumer.accept(new Double2FloatOpenCustomHashMap.MapEntry(pos));
/*      */          }
/*      */     
/*      */     }
/*      */     
/*      */     public void fastForEach(Consumer<? super Double2FloatMap.Entry> consumer) {
/* 1122 */       Double2FloatOpenCustomHashMap.MapEntry entry = new Double2FloatOpenCustomHashMap.MapEntry();
/* 1123 */       if (Double2FloatOpenCustomHashMap.this.containsNullKey) {
/* 1124 */         entry.index = Double2FloatOpenCustomHashMap.this.n;
/* 1125 */         consumer.accept(entry);
/*      */       } 
/* 1127 */       for (int pos = Double2FloatOpenCustomHashMap.this.n; pos-- != 0;) { if (Double.doubleToLongBits(Double2FloatOpenCustomHashMap.this.key[pos]) != 0L) {
/* 1128 */           entry.index = pos;
/* 1129 */           consumer.accept(entry);
/*      */         }  }
/*      */     
/*      */     }
/*      */   }
/*      */   
/*      */   public Double2FloatMap.FastEntrySet double2FloatEntrySet() {
/* 1136 */     if (this.entries == null) this.entries = new MapEntrySet(); 
/* 1137 */     return this.entries;
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
/*      */     extends MapIterator<DoubleConsumer>
/*      */     implements DoubleIterator
/*      */   {
/*      */     final void acceptOnIndex(DoubleConsumer action, int index) {
/* 1158 */       action.accept(Double2FloatOpenCustomHashMap.this.key[index]);
/*      */     }
/*      */ 
/*      */     
/*      */     public double nextDouble() {
/* 1163 */       return Double2FloatOpenCustomHashMap.this.key[nextEntry()];
/*      */     }
/*      */   }
/*      */   
/*      */   private final class KeySpliterator
/*      */     extends MapSpliterator<DoubleConsumer, KeySpliterator> implements DoubleSpliterator {
/*      */     private static final int POST_SPLIT_CHARACTERISTICS = 257;
/*      */     
/*      */     KeySpliterator() {}
/*      */     
/*      */     KeySpliterator(int pos, int max, boolean mustReturnNull, boolean hasSplit) {
/* 1174 */       super(pos, max, mustReturnNull, hasSplit);
/*      */     }
/*      */ 
/*      */     
/*      */     public int characteristics() {
/* 1179 */       return this.hasSplit ? 257 : 321;
/*      */     }
/*      */ 
/*      */     
/*      */     final void acceptOnIndex(DoubleConsumer action, int index) {
/* 1184 */       action.accept(Double2FloatOpenCustomHashMap.this.key[index]);
/*      */     }
/*      */ 
/*      */     
/*      */     final KeySpliterator makeForSplit(int pos, int max, boolean mustReturnNull) {
/* 1189 */       return new KeySpliterator(pos, max, mustReturnNull, true);
/*      */     } }
/*      */   
/*      */   private final class KeySet extends AbstractDoubleSet {
/*      */     private KeySet() {}
/*      */     
/*      */     public DoubleIterator iterator() {
/* 1196 */       return new Double2FloatOpenCustomHashMap.KeyIterator();
/*      */     }
/*      */ 
/*      */     
/*      */     public DoubleSpliterator spliterator() {
/* 1201 */       return new Double2FloatOpenCustomHashMap.KeySpliterator();
/*      */     }
/*      */ 
/*      */ 
/*      */     
/*      */     public void forEach(DoubleConsumer consumer) {
/* 1207 */       if (Double2FloatOpenCustomHashMap.this.containsNullKey) consumer.accept(Double2FloatOpenCustomHashMap.this.key[Double2FloatOpenCustomHashMap.this.n]); 
/* 1208 */       for (int pos = Double2FloatOpenCustomHashMap.this.n; pos-- != 0; ) {
/* 1209 */         double k = Double2FloatOpenCustomHashMap.this.key[pos];
/* 1210 */         if (Double.doubleToLongBits(k) != 0L) consumer.accept(k);
/*      */       
/*      */       } 
/*      */     }
/*      */     
/*      */     public int size() {
/* 1216 */       return Double2FloatOpenCustomHashMap.this.size;
/*      */     }
/*      */ 
/*      */     
/*      */     public boolean contains(double k) {
/* 1221 */       return Double2FloatOpenCustomHashMap.this.containsKey(k);
/*      */     }
/*      */ 
/*      */     
/*      */     public boolean remove(double k) {
/* 1226 */       int oldSize = Double2FloatOpenCustomHashMap.this.size;
/* 1227 */       Double2FloatOpenCustomHashMap.this.remove(k);
/* 1228 */       return (Double2FloatOpenCustomHashMap.this.size != oldSize);
/*      */     }
/*      */ 
/*      */     
/*      */     public void clear() {
/* 1233 */       Double2FloatOpenCustomHashMap.this.clear();
/*      */     }
/*      */   }
/*      */ 
/*      */   
/*      */   public DoubleSet keySet() {
/* 1239 */     if (this.keys == null) this.keys = new KeySet(); 
/* 1240 */     return this.keys;
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
/* 1261 */       action.accept(Double2FloatOpenCustomHashMap.this.value[index]);
/*      */     }
/*      */ 
/*      */     
/*      */     public float nextFloat() {
/* 1266 */       return Double2FloatOpenCustomHashMap.this.value[nextEntry()];
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
/* 1277 */       super(pos, max, mustReturnNull, hasSplit);
/*      */     }
/*      */ 
/*      */     
/*      */     public int characteristics() {
/* 1282 */       return this.hasSplit ? 256 : 320;
/*      */     }
/*      */ 
/*      */     
/*      */     final void acceptOnIndex(FloatConsumer action, int index) {
/* 1287 */       action.accept(Double2FloatOpenCustomHashMap.this.value[index]);
/*      */     }
/*      */ 
/*      */     
/*      */     final ValueSpliterator makeForSplit(int pos, int max, boolean mustReturnNull) {
/* 1292 */       return new ValueSpliterator(pos, max, mustReturnNull, true);
/*      */     }
/*      */   }
/*      */ 
/*      */   
/*      */   public FloatCollection values() {
/* 1298 */     if (this.values == null) this.values = (FloatCollection)new AbstractFloatCollection()
/*      */         {
/*      */           public FloatIterator iterator() {
/* 1301 */             return new Double2FloatOpenCustomHashMap.ValueIterator();
/*      */           }
/*      */ 
/*      */           
/*      */           public FloatSpliterator spliterator() {
/* 1306 */             return new Double2FloatOpenCustomHashMap.ValueSpliterator();
/*      */           }
/*      */ 
/*      */ 
/*      */           
/*      */           public void forEach(FloatConsumer consumer) {
/* 1312 */             if (Double2FloatOpenCustomHashMap.this.containsNullKey) consumer.accept(Double2FloatOpenCustomHashMap.this.value[Double2FloatOpenCustomHashMap.this.n]); 
/* 1313 */             for (int pos = Double2FloatOpenCustomHashMap.this.n; pos-- != 0;) { if (Double.doubleToLongBits(Double2FloatOpenCustomHashMap.this.key[pos]) != 0L) consumer.accept(Double2FloatOpenCustomHashMap.this.value[pos]);  }
/*      */           
/*      */           }
/*      */           
/*      */           public int size() {
/* 1318 */             return Double2FloatOpenCustomHashMap.this.size;
/*      */           }
/*      */ 
/*      */           
/*      */           public boolean contains(float v) {
/* 1323 */             return Double2FloatOpenCustomHashMap.this.containsValue(v);
/*      */           }
/*      */ 
/*      */           
/*      */           public void clear() {
/* 1328 */             Double2FloatOpenCustomHashMap.this.clear();
/*      */           }
/*      */         }; 
/* 1331 */     return this.values;
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
/* 1348 */     return trim(this.size);
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
/* 1370 */     int l = HashCommon.nextPowerOfTwo((int)Math.ceil((n / this.f)));
/* 1371 */     if (l >= this.n || this.size > HashCommon.maxFill(l, this.f)) return true; 
/*      */     try {
/* 1373 */       rehash(l);
/* 1374 */     } catch (OutOfMemoryError cantDoIt) {
/* 1375 */       return false;
/*      */     } 
/* 1377 */     return true;
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
/* 1392 */     double[] key = this.key;
/* 1393 */     float[] value = this.value;
/* 1394 */     int mask = newN - 1;
/* 1395 */     double[] newKey = new double[newN + 1];
/* 1396 */     float[] newValue = new float[newN + 1];
/* 1397 */     int i = this.n;
/* 1398 */     for (int j = realSize(); j-- != 0; ) {
/* 1399 */       while (Double.doubleToLongBits(key[--i]) == 0L); int pos;
/* 1400 */       if (Double.doubleToLongBits(newKey[pos = HashCommon.mix(this.strategy.hashCode(key[i])) & mask]) != 0L) while (Double.doubleToLongBits(newKey[pos = pos + 1 & mask]) != 0L); 
/* 1401 */       newKey[pos] = key[i];
/* 1402 */       newValue[pos] = value[i];
/*      */     } 
/* 1404 */     newValue[newN] = value[this.n];
/* 1405 */     this.n = newN;
/* 1406 */     this.mask = mask;
/* 1407 */     this.maxFill = HashCommon.maxFill(this.n, this.f);
/* 1408 */     this.key = newKey;
/* 1409 */     this.value = newValue;
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
/*      */   public Double2FloatOpenCustomHashMap clone() {
/*      */     Double2FloatOpenCustomHashMap c;
/*      */     try {
/* 1426 */       c = (Double2FloatOpenCustomHashMap)super.clone();
/* 1427 */     } catch (CloneNotSupportedException cantHappen) {
/* 1428 */       throw new InternalError();
/*      */     } 
/* 1430 */     c.keys = null;
/* 1431 */     c.values = null;
/* 1432 */     c.entries = null;
/* 1433 */     c.containsNullKey = this.containsNullKey;
/* 1434 */     c.key = (double[])this.key.clone();
/* 1435 */     c.value = (float[])this.value.clone();
/* 1436 */     c.strategy = this.strategy;
/* 1437 */     return c;
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
/* 1451 */     int h = 0;
/* 1452 */     for (int j = realSize(), i = 0, t = 0; j-- != 0; ) {
/* 1453 */       for (; Double.doubleToLongBits(this.key[i]) == 0L; i++);
/* 1454 */       t = this.strategy.hashCode(this.key[i]);
/* 1455 */       t ^= HashCommon.float2int(this.value[i]);
/* 1456 */       h += t;
/* 1457 */       i++;
/*      */     } 
/*      */     
/* 1460 */     if (this.containsNullKey) h += HashCommon.float2int(this.value[this.n]); 
/* 1461 */     return h;
/*      */   }
/*      */   
/*      */   private void writeObject(ObjectOutputStream s) throws IOException {
/* 1465 */     double[] key = this.key;
/* 1466 */     float[] value = this.value;
/* 1467 */     EntryIterator i = new EntryIterator();
/* 1468 */     s.defaultWriteObject();
/* 1469 */     for (int j = this.size; j-- != 0; ) {
/* 1470 */       int e = i.nextEntry();
/* 1471 */       s.writeDouble(key[e]);
/* 1472 */       s.writeFloat(value[e]);
/*      */     } 
/*      */   }
/*      */   
/*      */   private void readObject(ObjectInputStream s) throws IOException, ClassNotFoundException {
/* 1477 */     s.defaultReadObject();
/* 1478 */     this.n = HashCommon.arraySize(this.size, this.f);
/* 1479 */     this.maxFill = HashCommon.maxFill(this.n, this.f);
/* 1480 */     this.mask = this.n - 1;
/* 1481 */     double[] key = this.key = new double[this.n + 1];
/* 1482 */     float[] value = this.value = new float[this.n + 1];
/*      */ 
/*      */     
/* 1485 */     for (int i = this.size; i-- != 0; ) {
/* 1486 */       int pos; double k = s.readDouble();
/* 1487 */       float v = s.readFloat();
/* 1488 */       if (this.strategy.equals(k, 0.0D)) {
/* 1489 */         pos = this.n;
/* 1490 */         this.containsNullKey = true;
/*      */       } else {
/* 1492 */         pos = HashCommon.mix(this.strategy.hashCode(k)) & this.mask;
/* 1493 */         for (; Double.doubleToLongBits(key[pos]) != 0L; pos = pos + 1 & this.mask);
/*      */       } 
/* 1495 */       key[pos] = k;
/* 1496 */       value[pos] = v;
/*      */     } 
/*      */   }
/*      */   
/*      */   private void checkTable() {}
/*      */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\i\\unimi\dsi\fastutil\doubles\Double2FloatOpenCustomHashMap.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */