/*      */ package it.unimi.dsi.fastutil.doubles;
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
/*      */ public class Double2DoubleOpenCustomHashMap
/*      */   extends AbstractDouble2DoubleMap
/*      */   implements Serializable, Cloneable, Hash
/*      */ {
/*      */   private static final long serialVersionUID = 0L;
/*      */   private static final boolean ASSERTS = false;
/*      */   protected transient double[] key;
/*      */   protected transient double[] value;
/*      */   protected transient int mask;
/*      */   protected transient boolean containsNullKey;
/*      */   protected DoubleHash.Strategy strategy;
/*      */   protected transient int n;
/*      */   protected transient int maxFill;
/*      */   protected final transient int minN;
/*      */   protected int size;
/*      */   protected final float f;
/*      */   protected transient Double2DoubleMap.FastEntrySet entries;
/*      */   protected transient DoubleSet keys;
/*      */   protected transient DoubleCollection values;
/*      */   
/*      */   public Double2DoubleOpenCustomHashMap(int expected, float f, DoubleHash.Strategy strategy) {
/*   98 */     this.strategy = strategy;
/*   99 */     if (f <= 0.0F || f >= 1.0F) throw new IllegalArgumentException("Load factor must be greater than 0 and smaller than 1"); 
/*  100 */     if (expected < 0) throw new IllegalArgumentException("The expected number of elements must be nonnegative"); 
/*  101 */     this.f = f;
/*  102 */     this.minN = this.n = HashCommon.arraySize(expected, f);
/*  103 */     this.mask = this.n - 1;
/*  104 */     this.maxFill = HashCommon.maxFill(this.n, f);
/*  105 */     this.key = new double[this.n + 1];
/*  106 */     this.value = new double[this.n + 1];
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Double2DoubleOpenCustomHashMap(int expected, DoubleHash.Strategy strategy) {
/*  116 */     this(expected, 0.75F, strategy);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Double2DoubleOpenCustomHashMap(DoubleHash.Strategy strategy) {
/*  126 */     this(16, 0.75F, strategy);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Double2DoubleOpenCustomHashMap(Map<? extends Double, ? extends Double> m, float f, DoubleHash.Strategy strategy) {
/*  137 */     this(m.size(), f, strategy);
/*  138 */     putAll(m);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Double2DoubleOpenCustomHashMap(Map<? extends Double, ? extends Double> m, DoubleHash.Strategy strategy) {
/*  148 */     this(m, 0.75F, strategy);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Double2DoubleOpenCustomHashMap(Double2DoubleMap m, float f, DoubleHash.Strategy strategy) {
/*  159 */     this(m.size(), f, strategy);
/*  160 */     putAll(m);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Double2DoubleOpenCustomHashMap(Double2DoubleMap m, DoubleHash.Strategy strategy) {
/*  171 */     this(m, 0.75F, strategy);
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
/*      */   public Double2DoubleOpenCustomHashMap(double[] k, double[] v, float f, DoubleHash.Strategy strategy) {
/*  184 */     this(k.length, f, strategy);
/*  185 */     if (k.length != v.length) throw new IllegalArgumentException("The key array and the value array have different lengths (" + k.length + " and " + v.length + ")"); 
/*  186 */     for (int i = 0; i < k.length; ) { put(k[i], v[i]); i++; }
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
/*      */   public Double2DoubleOpenCustomHashMap(double[] k, double[] v, DoubleHash.Strategy strategy) {
/*  199 */     this(k, v, 0.75F, strategy);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public DoubleHash.Strategy strategy() {
/*  208 */     return this.strategy;
/*      */   }
/*      */   
/*      */   private int realSize() {
/*  212 */     return this.containsNullKey ? (this.size - 1) : this.size;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void ensureCapacity(int capacity) {
/*  222 */     int needed = HashCommon.arraySize(capacity, this.f);
/*  223 */     if (needed > this.n) rehash(needed); 
/*      */   }
/*      */   
/*      */   private void tryCapacity(long capacity) {
/*  227 */     int needed = (int)Math.min(1073741824L, Math.max(2L, HashCommon.nextPowerOfTwo((long)Math.ceil(((float)capacity / this.f)))));
/*  228 */     if (needed > this.n) rehash(needed); 
/*      */   }
/*      */   
/*      */   private double removeEntry(int pos) {
/*  232 */     double oldValue = this.value[pos];
/*  233 */     this.size--;
/*  234 */     shiftKeys(pos);
/*  235 */     if (this.n > this.minN && this.size < this.maxFill / 4 && this.n > 16) rehash(this.n / 2); 
/*  236 */     return oldValue;
/*      */   }
/*      */   
/*      */   private double removeNullEntry() {
/*  240 */     this.containsNullKey = false;
/*  241 */     double oldValue = this.value[this.n];
/*  242 */     this.size--;
/*  243 */     if (this.n > this.minN && this.size < this.maxFill / 4 && this.n > 16) rehash(this.n / 2); 
/*  244 */     return oldValue;
/*      */   }
/*      */ 
/*      */   
/*      */   public void putAll(Map<? extends Double, ? extends Double> m) {
/*  249 */     if (this.f <= 0.5D) { ensureCapacity(m.size()); }
/*  250 */     else { tryCapacity((size() + m.size())); }
/*      */     
/*  252 */     super.putAll(m);
/*      */   }
/*      */   
/*      */   private int find(double k) {
/*  256 */     if (this.strategy.equals(k, 0.0D)) return this.containsNullKey ? this.n : -(this.n + 1);
/*      */     
/*  258 */     double[] key = this.key;
/*      */     double curr;
/*      */     int pos;
/*  261 */     if (Double.doubleToLongBits(curr = key[pos = HashCommon.mix(this.strategy.hashCode(k)) & this.mask]) == 0L) return -(pos + 1); 
/*  262 */     if (this.strategy.equals(k, curr)) return pos;
/*      */     
/*      */     while (true) {
/*  265 */       if (Double.doubleToLongBits(curr = key[pos = pos + 1 & this.mask]) == 0L) return -(pos + 1); 
/*  266 */       if (this.strategy.equals(k, curr)) return pos; 
/*      */     } 
/*      */   }
/*      */   
/*      */   private void insert(int pos, double k, double v) {
/*  271 */     if (pos == this.n) this.containsNullKey = true; 
/*  272 */     this.key[pos] = k;
/*  273 */     this.value[pos] = v;
/*  274 */     if (this.size++ >= this.maxFill) rehash(HashCommon.arraySize(this.size + 1, this.f));
/*      */   
/*      */   }
/*      */ 
/*      */   
/*      */   public double put(double k, double v) {
/*  280 */     int pos = find(k);
/*  281 */     if (pos < 0) {
/*  282 */       insert(-pos - 1, k, v);
/*  283 */       return this.defRetValue;
/*      */     } 
/*  285 */     double oldValue = this.value[pos];
/*  286 */     this.value[pos] = v;
/*  287 */     return oldValue;
/*      */   }
/*      */   
/*      */   private double addToValue(int pos, double incr) {
/*  291 */     double oldValue = this.value[pos];
/*  292 */     this.value[pos] = oldValue + incr;
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
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public double addTo(double k, double incr) {
/*      */     int pos;
/*  311 */     if (this.strategy.equals(k, 0.0D)) {
/*  312 */       if (this.containsNullKey) return addToValue(this.n, incr); 
/*  313 */       pos = this.n;
/*  314 */       this.containsNullKey = true;
/*      */     } else {
/*      */       
/*  317 */       double[] key = this.key;
/*      */       double curr;
/*  319 */       if (Double.doubleToLongBits(curr = key[pos = HashCommon.mix(this.strategy.hashCode(k)) & this.mask]) != 0L) {
/*  320 */         if (this.strategy.equals(curr, k)) return addToValue(pos, incr); 
/*  321 */         while (Double.doubleToLongBits(curr = key[pos = pos + 1 & this.mask]) != 0L) { if (this.strategy.equals(curr, k)) return addToValue(pos, incr);  }
/*      */       
/*      */       } 
/*  324 */     }  this.key[pos] = k;
/*  325 */     this.value[pos] = this.defRetValue + incr;
/*  326 */     if (this.size++ >= this.maxFill) rehash(HashCommon.arraySize(this.size + 1, this.f));
/*      */     
/*  328 */     return this.defRetValue;
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
/*  341 */     double[] key = this.key; while (true) {
/*      */       double curr; int last;
/*  343 */       pos = (last = pos) + 1 & this.mask;
/*      */       while (true) {
/*  345 */         if (Double.doubleToLongBits(curr = key[pos]) == 0L) {
/*  346 */           key[last] = 0.0D;
/*      */           return;
/*      */         } 
/*  349 */         int slot = HashCommon.mix(this.strategy.hashCode(curr)) & this.mask;
/*  350 */         if ((last <= pos) ? (last >= slot || slot > pos) : (last >= slot && slot > pos))
/*  351 */           break;  pos = pos + 1 & this.mask;
/*      */       } 
/*  353 */       key[last] = curr;
/*  354 */       this.value[last] = this.value[pos];
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public double remove(double k) {
/*  361 */     if (this.strategy.equals(k, 0.0D)) {
/*  362 */       if (this.containsNullKey) return removeNullEntry(); 
/*  363 */       return this.defRetValue;
/*      */     } 
/*      */     
/*  366 */     double[] key = this.key;
/*      */     double curr;
/*      */     int pos;
/*  369 */     if (Double.doubleToLongBits(curr = key[pos = HashCommon.mix(this.strategy.hashCode(k)) & this.mask]) == 0L) return this.defRetValue; 
/*  370 */     if (this.strategy.equals(k, curr)) return removeEntry(pos); 
/*      */     while (true) {
/*  372 */       if (Double.doubleToLongBits(curr = key[pos = pos + 1 & this.mask]) == 0L) return this.defRetValue; 
/*  373 */       if (this.strategy.equals(k, curr)) return removeEntry(pos);
/*      */     
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   public double get(double k) {
/*  380 */     if (this.strategy.equals(k, 0.0D)) return this.containsNullKey ? this.value[this.n] : this.defRetValue;
/*      */     
/*  382 */     double[] key = this.key;
/*      */     double curr;
/*      */     int pos;
/*  385 */     if (Double.doubleToLongBits(curr = key[pos = HashCommon.mix(this.strategy.hashCode(k)) & this.mask]) == 0L) return this.defRetValue; 
/*  386 */     if (this.strategy.equals(k, curr)) return this.value[pos];
/*      */     
/*      */     while (true) {
/*  389 */       if (Double.doubleToLongBits(curr = key[pos = pos + 1 & this.mask]) == 0L) return this.defRetValue; 
/*  390 */       if (this.strategy.equals(k, curr)) return this.value[pos];
/*      */     
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   public boolean containsKey(double k) {
/*  397 */     if (this.strategy.equals(k, 0.0D)) return this.containsNullKey;
/*      */     
/*  399 */     double[] key = this.key;
/*      */     double curr;
/*      */     int pos;
/*  402 */     if (Double.doubleToLongBits(curr = key[pos = HashCommon.mix(this.strategy.hashCode(k)) & this.mask]) == 0L) return false; 
/*  403 */     if (this.strategy.equals(k, curr)) return true;
/*      */     
/*      */     while (true) {
/*  406 */       if (Double.doubleToLongBits(curr = key[pos = pos + 1 & this.mask]) == 0L) return false; 
/*  407 */       if (this.strategy.equals(k, curr)) return true;
/*      */     
/*      */     } 
/*      */   }
/*      */   
/*      */   public boolean containsValue(double v) {
/*  413 */     double[] value = this.value;
/*  414 */     double[] key = this.key;
/*  415 */     if (this.containsNullKey && Double.doubleToLongBits(value[this.n]) == Double.doubleToLongBits(v)) return true; 
/*  416 */     for (int i = this.n; i-- != 0;) { if (Double.doubleToLongBits(key[i]) != 0L && Double.doubleToLongBits(value[i]) == Double.doubleToLongBits(v)) return true;  }
/*  417 */      return false;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public double getOrDefault(double k, double defaultValue) {
/*  424 */     if (this.strategy.equals(k, 0.0D)) return this.containsNullKey ? this.value[this.n] : defaultValue;
/*      */     
/*  426 */     double[] key = this.key;
/*      */     double curr;
/*      */     int pos;
/*  429 */     if (Double.doubleToLongBits(curr = key[pos = HashCommon.mix(this.strategy.hashCode(k)) & this.mask]) == 0L) return defaultValue; 
/*  430 */     if (this.strategy.equals(k, curr)) return this.value[pos];
/*      */     
/*      */     while (true) {
/*  433 */       if (Double.doubleToLongBits(curr = key[pos = pos + 1 & this.mask]) == 0L) return defaultValue; 
/*  434 */       if (this.strategy.equals(k, curr)) return this.value[pos];
/*      */     
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   public double putIfAbsent(double k, double v) {
/*  441 */     int pos = find(k);
/*  442 */     if (pos >= 0) return this.value[pos]; 
/*  443 */     insert(-pos - 1, k, v);
/*  444 */     return this.defRetValue;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean remove(double k, double v) {
/*  451 */     if (this.strategy.equals(k, 0.0D)) {
/*  452 */       if (this.containsNullKey && Double.doubleToLongBits(v) == Double.doubleToLongBits(this.value[this.n])) {
/*  453 */         removeNullEntry();
/*  454 */         return true;
/*      */       } 
/*  456 */       return false;
/*      */     } 
/*      */     
/*  459 */     double[] key = this.key;
/*      */     double curr;
/*      */     int pos;
/*  462 */     if (Double.doubleToLongBits(curr = key[pos = HashCommon.mix(this.strategy.hashCode(k)) & this.mask]) == 0L) return false; 
/*  463 */     if (this.strategy.equals(k, curr) && Double.doubleToLongBits(v) == Double.doubleToLongBits(this.value[pos])) {
/*  464 */       removeEntry(pos);
/*  465 */       return true;
/*      */     } 
/*      */     while (true) {
/*  468 */       if (Double.doubleToLongBits(curr = key[pos = pos + 1 & this.mask]) == 0L) return false; 
/*  469 */       if (this.strategy.equals(k, curr) && Double.doubleToLongBits(v) == Double.doubleToLongBits(this.value[pos])) {
/*  470 */         removeEntry(pos);
/*  471 */         return true;
/*      */       } 
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean replace(double k, double oldValue, double v) {
/*  479 */     int pos = find(k);
/*  480 */     if (pos < 0 || Double.doubleToLongBits(oldValue) != Double.doubleToLongBits(this.value[pos])) return false; 
/*  481 */     this.value[pos] = v;
/*  482 */     return true;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public double replace(double k, double v) {
/*  488 */     int pos = find(k);
/*  489 */     if (pos < 0) return this.defRetValue; 
/*  490 */     double oldValue = this.value[pos];
/*  491 */     this.value[pos] = v;
/*  492 */     return oldValue;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public double computeIfAbsent(double k, DoubleUnaryOperator mappingFunction) {
/*  498 */     Objects.requireNonNull(mappingFunction);
/*  499 */     int pos = find(k);
/*  500 */     if (pos >= 0) return this.value[pos]; 
/*  501 */     double newValue = mappingFunction.applyAsDouble(k);
/*  502 */     insert(-pos - 1, k, newValue);
/*  503 */     return newValue;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public double computeIfAbsent(double key, Double2DoubleFunction mappingFunction) {
/*  509 */     Objects.requireNonNull(mappingFunction);
/*  510 */     int pos = find(key);
/*  511 */     if (pos >= 0) return this.value[pos]; 
/*  512 */     if (!mappingFunction.containsKey(key)) return this.defRetValue; 
/*  513 */     double newValue = mappingFunction.get(key);
/*  514 */     insert(-pos - 1, key, newValue);
/*  515 */     return newValue;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public double computeIfAbsentNullable(double k, DoubleFunction<? extends Double> mappingFunction) {
/*  521 */     Objects.requireNonNull(mappingFunction);
/*  522 */     int pos = find(k);
/*  523 */     if (pos >= 0) return this.value[pos]; 
/*  524 */     Double newValue = mappingFunction.apply(k);
/*  525 */     if (newValue == null) return this.defRetValue; 
/*  526 */     double v = newValue.doubleValue();
/*  527 */     insert(-pos - 1, k, v);
/*  528 */     return v;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public double computeIfPresent(double k, BiFunction<? super Double, ? super Double, ? extends Double> remappingFunction) {
/*  534 */     Objects.requireNonNull(remappingFunction);
/*  535 */     int pos = find(k);
/*  536 */     if (pos < 0) return this.defRetValue; 
/*  537 */     Double newValue = remappingFunction.apply(Double.valueOf(k), Double.valueOf(this.value[pos]));
/*  538 */     if (newValue == null) {
/*  539 */       if (this.strategy.equals(k, 0.0D)) { removeNullEntry(); }
/*  540 */       else { removeEntry(pos); }
/*  541 */        return this.defRetValue;
/*      */     } 
/*  543 */     this.value[pos] = newValue.doubleValue(); return newValue.doubleValue();
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public double compute(double k, BiFunction<? super Double, ? super Double, ? extends Double> remappingFunction) {
/*  549 */     Objects.requireNonNull(remappingFunction);
/*  550 */     int pos = find(k);
/*  551 */     Double newValue = remappingFunction.apply(Double.valueOf(k), (pos >= 0) ? Double.valueOf(this.value[pos]) : null);
/*  552 */     if (newValue == null) {
/*  553 */       if (pos >= 0)
/*  554 */         if (this.strategy.equals(k, 0.0D)) { removeNullEntry(); }
/*  555 */         else { removeEntry(pos); }
/*      */          
/*  557 */       return this.defRetValue;
/*      */     } 
/*  559 */     double newVal = newValue.doubleValue();
/*  560 */     if (pos < 0) {
/*  561 */       insert(-pos - 1, k, newVal);
/*  562 */       return newVal;
/*      */     } 
/*  564 */     this.value[pos] = newVal; return newVal;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public double merge(double k, double v, BiFunction<? super Double, ? super Double, ? extends Double> remappingFunction) {
/*  570 */     Objects.requireNonNull(remappingFunction);
/*      */     
/*  572 */     int pos = find(k);
/*  573 */     if (pos < 0) {
/*  574 */       if (pos < 0) { insert(-pos - 1, k, v); }
/*  575 */       else { this.value[pos] = v; }
/*  576 */        return v;
/*      */     } 
/*  578 */     Double newValue = remappingFunction.apply(Double.valueOf(this.value[pos]), Double.valueOf(v));
/*  579 */     if (newValue == null) {
/*  580 */       if (this.strategy.equals(k, 0.0D)) { removeNullEntry(); }
/*  581 */       else { removeEntry(pos); }
/*  582 */        return this.defRetValue;
/*      */     } 
/*  584 */     this.value[pos] = newValue.doubleValue(); return newValue.doubleValue();
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
/*  595 */     if (this.size == 0)
/*  596 */       return;  this.size = 0;
/*  597 */     this.containsNullKey = false;
/*  598 */     Arrays.fill(this.key, 0.0D);
/*      */   }
/*      */ 
/*      */   
/*      */   public int size() {
/*  603 */     return this.size;
/*      */   }
/*      */ 
/*      */   
/*      */   public boolean isEmpty() {
/*  608 */     return (this.size == 0);
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   final class MapEntry
/*      */     implements Double2DoubleMap.Entry, Map.Entry<Double, Double>, DoubleDoublePair
/*      */   {
/*      */     int index;
/*      */ 
/*      */ 
/*      */     
/*      */     MapEntry(int index) {
/*  621 */       this.index = index;
/*      */     }
/*      */ 
/*      */     
/*      */     MapEntry() {}
/*      */ 
/*      */     
/*      */     public double getDoubleKey() {
/*  629 */       return Double2DoubleOpenCustomHashMap.this.key[this.index];
/*      */     }
/*      */ 
/*      */     
/*      */     public double leftDouble() {
/*  634 */       return Double2DoubleOpenCustomHashMap.this.key[this.index];
/*      */     }
/*      */ 
/*      */     
/*      */     public double getDoubleValue() {
/*  639 */       return Double2DoubleOpenCustomHashMap.this.value[this.index];
/*      */     }
/*      */ 
/*      */     
/*      */     public double rightDouble() {
/*  644 */       return Double2DoubleOpenCustomHashMap.this.value[this.index];
/*      */     }
/*      */ 
/*      */     
/*      */     public double setValue(double v) {
/*  649 */       double oldValue = Double2DoubleOpenCustomHashMap.this.value[this.index];
/*  650 */       Double2DoubleOpenCustomHashMap.this.value[this.index] = v;
/*  651 */       return oldValue;
/*      */     }
/*      */ 
/*      */     
/*      */     public DoubleDoublePair right(double v) {
/*  656 */       Double2DoubleOpenCustomHashMap.this.value[this.index] = v;
/*  657 */       return this;
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
/*  668 */       return Double.valueOf(Double2DoubleOpenCustomHashMap.this.key[this.index]);
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
/*  679 */       return Double.valueOf(Double2DoubleOpenCustomHashMap.this.value[this.index]);
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
/*  690 */       return Double.valueOf(setValue(v.doubleValue()));
/*      */     }
/*      */ 
/*      */ 
/*      */     
/*      */     public boolean equals(Object o) {
/*  696 */       if (!(o instanceof Map.Entry)) return false; 
/*  697 */       Map.Entry<Double, Double> e = (Map.Entry<Double, Double>)o;
/*  698 */       return (Double2DoubleOpenCustomHashMap.this.strategy.equals(Double2DoubleOpenCustomHashMap.this.key[this.index], ((Double)e.getKey()).doubleValue()) && Double.doubleToLongBits(Double2DoubleOpenCustomHashMap.this.value[this.index]) == Double.doubleToLongBits(((Double)e.getValue()).doubleValue()));
/*      */     }
/*      */ 
/*      */     
/*      */     public int hashCode() {
/*  703 */       return Double2DoubleOpenCustomHashMap.this.strategy.hashCode(Double2DoubleOpenCustomHashMap.this.key[this.index]) ^ HashCommon.double2int(Double2DoubleOpenCustomHashMap.this.value[this.index]);
/*      */     }
/*      */ 
/*      */     
/*      */     public String toString() {
/*  708 */       return Double2DoubleOpenCustomHashMap.this.key[this.index] + "=>" + Double2DoubleOpenCustomHashMap.this.value[this.index];
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
/*  719 */     int pos = Double2DoubleOpenCustomHashMap.this.n;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  725 */     int last = -1;
/*      */     
/*  727 */     int c = Double2DoubleOpenCustomHashMap.this.size;
/*      */     
/*  729 */     boolean mustReturnNullKey = Double2DoubleOpenCustomHashMap.this.containsNullKey;
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
/*  740 */       return (this.c != 0);
/*      */     }
/*      */     
/*      */     public int nextEntry() {
/*  744 */       if (!hasNext()) throw new NoSuchElementException(); 
/*  745 */       this.c--;
/*  746 */       if (this.mustReturnNullKey) {
/*  747 */         this.mustReturnNullKey = false;
/*  748 */         return this.last = Double2DoubleOpenCustomHashMap.this.n;
/*      */       } 
/*  750 */       double[] key = Double2DoubleOpenCustomHashMap.this.key;
/*      */       while (true) {
/*  752 */         if (--this.pos < 0) {
/*      */           
/*  754 */           this.last = Integer.MIN_VALUE;
/*  755 */           double k = this.wrapped.getDouble(-this.pos - 1);
/*  756 */           int p = HashCommon.mix(Double2DoubleOpenCustomHashMap.this.strategy.hashCode(k)) & Double2DoubleOpenCustomHashMap.this.mask;
/*  757 */           for (; !Double2DoubleOpenCustomHashMap.this.strategy.equals(k, key[p]); p = p + 1 & Double2DoubleOpenCustomHashMap.this.mask);
/*  758 */           return p;
/*      */         } 
/*  760 */         if (Double.doubleToLongBits(key[this.pos]) != 0L) return this.last = this.pos; 
/*      */       } 
/*      */     }
/*      */     
/*      */     public void forEachRemaining(ConsumerType action) {
/*  765 */       if (this.mustReturnNullKey) {
/*  766 */         this.mustReturnNullKey = false;
/*  767 */         acceptOnIndex(action, this.last = Double2DoubleOpenCustomHashMap.this.n);
/*  768 */         this.c--;
/*      */       } 
/*  770 */       double[] key = Double2DoubleOpenCustomHashMap.this.key;
/*  771 */       while (this.c != 0) {
/*  772 */         if (--this.pos < 0) {
/*      */           
/*  774 */           this.last = Integer.MIN_VALUE;
/*  775 */           double k = this.wrapped.getDouble(-this.pos - 1);
/*  776 */           int p = HashCommon.mix(Double2DoubleOpenCustomHashMap.this.strategy.hashCode(k)) & Double2DoubleOpenCustomHashMap.this.mask;
/*  777 */           for (; !Double2DoubleOpenCustomHashMap.this.strategy.equals(k, key[p]); p = p + 1 & Double2DoubleOpenCustomHashMap.this.mask);
/*  778 */           acceptOnIndex(action, p);
/*  779 */           this.c--; continue;
/*  780 */         }  if (Double.doubleToLongBits(key[this.pos]) != 0L) {
/*  781 */           acceptOnIndex(action, this.last = this.pos);
/*  782 */           this.c--;
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
/*  797 */       double[] key = Double2DoubleOpenCustomHashMap.this.key; while (true) {
/*      */         double curr; int last;
/*  799 */         pos = (last = pos) + 1 & Double2DoubleOpenCustomHashMap.this.mask;
/*      */         while (true) {
/*  801 */           if (Double.doubleToLongBits(curr = key[pos]) == 0L) {
/*  802 */             key[last] = 0.0D;
/*      */             return;
/*      */           } 
/*  805 */           int slot = HashCommon.mix(Double2DoubleOpenCustomHashMap.this.strategy.hashCode(curr)) & Double2DoubleOpenCustomHashMap.this.mask;
/*  806 */           if ((last <= pos) ? (last >= slot || slot > pos) : (last >= slot && slot > pos))
/*  807 */             break;  pos = pos + 1 & Double2DoubleOpenCustomHashMap.this.mask;
/*      */         } 
/*  809 */         if (pos < last) {
/*  810 */           if (this.wrapped == null) this.wrapped = new DoubleArrayList(2); 
/*  811 */           this.wrapped.add(key[pos]);
/*      */         } 
/*  813 */         key[last] = curr;
/*  814 */         Double2DoubleOpenCustomHashMap.this.value[last] = Double2DoubleOpenCustomHashMap.this.value[pos];
/*      */       } 
/*      */     }
/*      */     
/*      */     public void remove() {
/*  819 */       if (this.last == -1) throw new IllegalStateException(); 
/*  820 */       if (this.last == Double2DoubleOpenCustomHashMap.this.n)
/*  821 */       { Double2DoubleOpenCustomHashMap.this.containsNullKey = false; }
/*  822 */       else if (this.pos >= 0) { shiftKeys(this.last); }
/*      */       else
/*      */       
/*  825 */       { Double2DoubleOpenCustomHashMap.this.remove(this.wrapped.getDouble(-this.pos - 1));
/*  826 */         this.last = -1;
/*      */         return; }
/*      */       
/*  829 */       Double2DoubleOpenCustomHashMap.this.size--;
/*  830 */       this.last = -1;
/*      */     }
/*      */ 
/*      */     
/*      */     public int skip(int n) {
/*  835 */       int i = n;
/*  836 */       for (; i-- != 0 && hasNext(); nextEntry());
/*  837 */       return n - i - 1;
/*      */     }
/*      */     
/*      */     private MapIterator() {}
/*      */     
/*      */     abstract void acceptOnIndex(ConsumerType param1ConsumerType, int param1Int);
/*      */   }
/*      */   
/*      */   private final class EntryIterator extends MapIterator<Consumer<? super Double2DoubleMap.Entry>> implements ObjectIterator<Double2DoubleMap.Entry> { public Double2DoubleOpenCustomHashMap.MapEntry next() {
/*  846 */       return this.entry = new Double2DoubleOpenCustomHashMap.MapEntry(nextEntry());
/*      */     }
/*      */     private Double2DoubleOpenCustomHashMap.MapEntry entry;
/*      */     private EntryIterator() {}
/*      */     
/*      */     final void acceptOnIndex(Consumer<? super Double2DoubleMap.Entry> action, int index) {
/*  852 */       action.accept(this.entry = new Double2DoubleOpenCustomHashMap.MapEntry(index));
/*      */     }
/*      */ 
/*      */     
/*      */     public void remove() {
/*  857 */       super.remove();
/*  858 */       this.entry.index = -1;
/*      */     } }
/*      */   
/*      */   private final class FastEntryIterator extends MapIterator<Consumer<? super Double2DoubleMap.Entry>> implements ObjectIterator<Double2DoubleMap.Entry> {
/*      */     private FastEntryIterator() {
/*  863 */       this.entry = new Double2DoubleOpenCustomHashMap.MapEntry();
/*      */     }
/*      */     private final Double2DoubleOpenCustomHashMap.MapEntry entry;
/*      */     public Double2DoubleOpenCustomHashMap.MapEntry next() {
/*  867 */       this.entry.index = nextEntry();
/*  868 */       return this.entry;
/*      */     }
/*      */ 
/*      */ 
/*      */     
/*      */     final void acceptOnIndex(Consumer<? super Double2DoubleMap.Entry> action, int index) {
/*  874 */       this.entry.index = index;
/*  875 */       action.accept(this.entry);
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private abstract class MapSpliterator<ConsumerType, SplitType extends MapSpliterator<ConsumerType, SplitType>>
/*      */   {
/*  884 */     int pos = 0;
/*      */     
/*  886 */     int max = Double2DoubleOpenCustomHashMap.this.n;
/*      */     
/*  888 */     int c = 0;
/*      */     
/*  890 */     boolean mustReturnNull = Double2DoubleOpenCustomHashMap.this.containsNullKey;
/*      */     
/*      */     boolean hasSplit = false;
/*      */     
/*      */     MapSpliterator() {}
/*      */     
/*      */     MapSpliterator(int pos, int max, boolean mustReturnNull, boolean hasSplit) {
/*  897 */       this.pos = pos;
/*  898 */       this.max = max;
/*  899 */       this.mustReturnNull = mustReturnNull;
/*  900 */       this.hasSplit = hasSplit;
/*      */     }
/*      */     
/*      */     abstract void acceptOnIndex(ConsumerType param1ConsumerType, int param1Int);
/*      */     
/*      */     abstract SplitType makeForSplit(int param1Int1, int param1Int2, boolean param1Boolean);
/*      */     
/*      */     public boolean tryAdvance(ConsumerType action) {
/*  908 */       if (this.mustReturnNull) {
/*  909 */         this.mustReturnNull = false;
/*  910 */         this.c++;
/*  911 */         acceptOnIndex(action, Double2DoubleOpenCustomHashMap.this.n);
/*  912 */         return true;
/*      */       } 
/*  914 */       double[] key = Double2DoubleOpenCustomHashMap.this.key;
/*  915 */       while (this.pos < this.max) {
/*  916 */         if (Double.doubleToLongBits(key[this.pos]) != 0L) {
/*  917 */           this.c++;
/*  918 */           acceptOnIndex(action, this.pos++);
/*  919 */           return true;
/*      */         } 
/*  921 */         this.pos++;
/*      */       } 
/*  923 */       return false;
/*      */     }
/*      */     
/*      */     public void forEachRemaining(ConsumerType action) {
/*  927 */       if (this.mustReturnNull) {
/*  928 */         this.mustReturnNull = false;
/*  929 */         this.c++;
/*  930 */         acceptOnIndex(action, Double2DoubleOpenCustomHashMap.this.n);
/*      */       } 
/*  932 */       double[] key = Double2DoubleOpenCustomHashMap.this.key;
/*  933 */       while (this.pos < this.max) {
/*  934 */         if (Double.doubleToLongBits(key[this.pos]) != 0L) {
/*  935 */           acceptOnIndex(action, this.pos);
/*  936 */           this.c++;
/*      */         } 
/*  938 */         this.pos++;
/*      */       } 
/*      */     }
/*      */     
/*      */     public long estimateSize() {
/*  943 */       if (!this.hasSplit)
/*      */       {
/*  945 */         return (Double2DoubleOpenCustomHashMap.this.size - this.c);
/*      */       }
/*      */ 
/*      */ 
/*      */       
/*  950 */       return Math.min((Double2DoubleOpenCustomHashMap.this.size - this.c), (long)(Double2DoubleOpenCustomHashMap.this.realSize() / Double2DoubleOpenCustomHashMap.this.n * (this.max - this.pos)) + (this.mustReturnNull ? 1L : 0L));
/*      */     }
/*      */ 
/*      */     
/*      */     public SplitType trySplit() {
/*  955 */       if (this.pos >= this.max - 1) return null; 
/*  956 */       int retLen = this.max - this.pos >> 1;
/*  957 */       if (retLen <= 1) return null; 
/*  958 */       int myNewPos = this.pos + retLen;
/*  959 */       int retPos = this.pos;
/*  960 */       int retMax = myNewPos;
/*      */ 
/*      */ 
/*      */       
/*  964 */       SplitType split = makeForSplit(retPos, retMax, this.mustReturnNull);
/*  965 */       this.pos = myNewPos;
/*  966 */       this.mustReturnNull = false;
/*  967 */       this.hasSplit = true;
/*  968 */       return split;
/*      */     }
/*      */     
/*      */     public long skip(long n) {
/*  972 */       if (n < 0L) throw new IllegalArgumentException("Argument must be nonnegative: " + n); 
/*  973 */       if (n == 0L) return 0L; 
/*  974 */       long skipped = 0L;
/*  975 */       if (this.mustReturnNull) {
/*  976 */         this.mustReturnNull = false;
/*  977 */         skipped++;
/*  978 */         n--;
/*      */       } 
/*  980 */       double[] key = Double2DoubleOpenCustomHashMap.this.key;
/*  981 */       while (this.pos < this.max && n > 0L) {
/*  982 */         if (Double.doubleToLongBits(key[this.pos++]) != 0L) {
/*  983 */           skipped++;
/*  984 */           n--;
/*      */         } 
/*      */       } 
/*  987 */       return skipped;
/*      */     }
/*      */   }
/*      */   
/*      */   private final class EntrySpliterator
/*      */     extends MapSpliterator<Consumer<? super Double2DoubleMap.Entry>, EntrySpliterator> implements ObjectSpliterator<Double2DoubleMap.Entry> {
/*      */     private static final int POST_SPLIT_CHARACTERISTICS = 1;
/*      */     
/*      */     EntrySpliterator() {}
/*      */     
/*      */     EntrySpliterator(int pos, int max, boolean mustReturnNull, boolean hasSplit) {
/*  998 */       super(pos, max, mustReturnNull, hasSplit);
/*      */     }
/*      */ 
/*      */     
/*      */     public int characteristics() {
/* 1003 */       return this.hasSplit ? 1 : 65;
/*      */     }
/*      */ 
/*      */     
/*      */     final void acceptOnIndex(Consumer<? super Double2DoubleMap.Entry> action, int index) {
/* 1008 */       action.accept(new Double2DoubleOpenCustomHashMap.MapEntry(index));
/*      */     }
/*      */ 
/*      */     
/*      */     final EntrySpliterator makeForSplit(int pos, int max, boolean mustReturnNull) {
/* 1013 */       return new EntrySpliterator(pos, max, mustReturnNull, true);
/*      */     } }
/*      */   
/*      */   private final class MapEntrySet extends AbstractObjectSet<Double2DoubleMap.Entry> implements Double2DoubleMap.FastEntrySet {
/*      */     private MapEntrySet() {}
/*      */     
/*      */     public ObjectIterator<Double2DoubleMap.Entry> iterator() {
/* 1020 */       return new Double2DoubleOpenCustomHashMap.EntryIterator();
/*      */     }
/*      */ 
/*      */     
/*      */     public ObjectIterator<Double2DoubleMap.Entry> fastIterator() {
/* 1025 */       return new Double2DoubleOpenCustomHashMap.FastEntryIterator();
/*      */     }
/*      */ 
/*      */     
/*      */     public ObjectSpliterator<Double2DoubleMap.Entry> spliterator() {
/* 1030 */       return new Double2DoubleOpenCustomHashMap.EntrySpliterator();
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public boolean contains(Object o) {
/* 1037 */       if (!(o instanceof Map.Entry)) return false; 
/* 1038 */       Map.Entry<?, ?> e = (Map.Entry<?, ?>)o;
/* 1039 */       if (e.getKey() == null || !(e.getKey() instanceof Double)) return false; 
/* 1040 */       if (e.getValue() == null || !(e.getValue() instanceof Double)) return false; 
/* 1041 */       double k = ((Double)e.getKey()).doubleValue();
/* 1042 */       double v = ((Double)e.getValue()).doubleValue();
/* 1043 */       if (Double2DoubleOpenCustomHashMap.this.strategy.equals(k, 0.0D)) return (Double2DoubleOpenCustomHashMap.this.containsNullKey && Double.doubleToLongBits(Double2DoubleOpenCustomHashMap.this.value[Double2DoubleOpenCustomHashMap.this.n]) == Double.doubleToLongBits(v));
/*      */       
/* 1045 */       double[] key = Double2DoubleOpenCustomHashMap.this.key;
/*      */       double curr;
/*      */       int pos;
/* 1048 */       if (Double.doubleToLongBits(curr = key[pos = HashCommon.mix(Double2DoubleOpenCustomHashMap.this.strategy.hashCode(k)) & Double2DoubleOpenCustomHashMap.this.mask]) == 0L) return false; 
/* 1049 */       if (Double2DoubleOpenCustomHashMap.this.strategy.equals(k, curr)) return (Double.doubleToLongBits(Double2DoubleOpenCustomHashMap.this.value[pos]) == Double.doubleToLongBits(v));
/*      */       
/*      */       while (true) {
/* 1052 */         if (Double.doubleToLongBits(curr = key[pos = pos + 1 & Double2DoubleOpenCustomHashMap.this.mask]) == 0L) return false; 
/* 1053 */         if (Double2DoubleOpenCustomHashMap.this.strategy.equals(k, curr)) return (Double.doubleToLongBits(Double2DoubleOpenCustomHashMap.this.value[pos]) == Double.doubleToLongBits(v));
/*      */       
/*      */       } 
/*      */     }
/*      */ 
/*      */     
/*      */     public boolean remove(Object o) {
/* 1060 */       if (!(o instanceof Map.Entry)) return false; 
/* 1061 */       Map.Entry<?, ?> e = (Map.Entry<?, ?>)o;
/* 1062 */       if (e.getKey() == null || !(e.getKey() instanceof Double)) return false; 
/* 1063 */       if (e.getValue() == null || !(e.getValue() instanceof Double)) return false; 
/* 1064 */       double k = ((Double)e.getKey()).doubleValue();
/* 1065 */       double v = ((Double)e.getValue()).doubleValue();
/* 1066 */       if (Double2DoubleOpenCustomHashMap.this.strategy.equals(k, 0.0D)) {
/* 1067 */         if (Double2DoubleOpenCustomHashMap.this.containsNullKey && Double.doubleToLongBits(Double2DoubleOpenCustomHashMap.this.value[Double2DoubleOpenCustomHashMap.this.n]) == Double.doubleToLongBits(v)) {
/* 1068 */           Double2DoubleOpenCustomHashMap.this.removeNullEntry();
/* 1069 */           return true;
/*      */         } 
/* 1071 */         return false;
/*      */       } 
/*      */       
/* 1074 */       double[] key = Double2DoubleOpenCustomHashMap.this.key;
/*      */       double curr;
/*      */       int pos;
/* 1077 */       if (Double.doubleToLongBits(curr = key[pos = HashCommon.mix(Double2DoubleOpenCustomHashMap.this.strategy.hashCode(k)) & Double2DoubleOpenCustomHashMap.this.mask]) == 0L) return false; 
/* 1078 */       if (Double2DoubleOpenCustomHashMap.this.strategy.equals(curr, k)) {
/* 1079 */         if (Double.doubleToLongBits(Double2DoubleOpenCustomHashMap.this.value[pos]) == Double.doubleToLongBits(v)) {
/* 1080 */           Double2DoubleOpenCustomHashMap.this.removeEntry(pos);
/* 1081 */           return true;
/*      */         } 
/* 1083 */         return false;
/*      */       } 
/*      */       while (true) {
/* 1086 */         if (Double.doubleToLongBits(curr = key[pos = pos + 1 & Double2DoubleOpenCustomHashMap.this.mask]) == 0L) return false; 
/* 1087 */         if (Double2DoubleOpenCustomHashMap.this.strategy.equals(curr, k) && 
/* 1088 */           Double.doubleToLongBits(Double2DoubleOpenCustomHashMap.this.value[pos]) == Double.doubleToLongBits(v)) {
/* 1089 */           Double2DoubleOpenCustomHashMap.this.removeEntry(pos);
/* 1090 */           return true;
/*      */         } 
/*      */       } 
/*      */     }
/*      */ 
/*      */ 
/*      */     
/*      */     public int size() {
/* 1098 */       return Double2DoubleOpenCustomHashMap.this.size;
/*      */     }
/*      */ 
/*      */     
/*      */     public void clear() {
/* 1103 */       Double2DoubleOpenCustomHashMap.this.clear();
/*      */     }
/*      */ 
/*      */ 
/*      */     
/*      */     public void forEach(Consumer<? super Double2DoubleMap.Entry> consumer) {
/* 1109 */       if (Double2DoubleOpenCustomHashMap.this.containsNullKey) consumer.accept(new Double2DoubleOpenCustomHashMap.MapEntry(Double2DoubleOpenCustomHashMap.this.n)); 
/* 1110 */       for (int pos = Double2DoubleOpenCustomHashMap.this.n; pos-- != 0;) { if (Double.doubleToLongBits(Double2DoubleOpenCustomHashMap.this.key[pos]) != 0L) consumer.accept(new Double2DoubleOpenCustomHashMap.MapEntry(pos));
/*      */          }
/*      */     
/*      */     }
/*      */     
/*      */     public void fastForEach(Consumer<? super Double2DoubleMap.Entry> consumer) {
/* 1116 */       Double2DoubleOpenCustomHashMap.MapEntry entry = new Double2DoubleOpenCustomHashMap.MapEntry();
/* 1117 */       if (Double2DoubleOpenCustomHashMap.this.containsNullKey) {
/* 1118 */         entry.index = Double2DoubleOpenCustomHashMap.this.n;
/* 1119 */         consumer.accept(entry);
/*      */       } 
/* 1121 */       for (int pos = Double2DoubleOpenCustomHashMap.this.n; pos-- != 0;) { if (Double.doubleToLongBits(Double2DoubleOpenCustomHashMap.this.key[pos]) != 0L) {
/* 1122 */           entry.index = pos;
/* 1123 */           consumer.accept(entry);
/*      */         }  }
/*      */     
/*      */     }
/*      */   }
/*      */   
/*      */   public Double2DoubleMap.FastEntrySet double2DoubleEntrySet() {
/* 1130 */     if (this.entries == null) this.entries = new MapEntrySet(); 
/* 1131 */     return this.entries;
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
/* 1152 */       action.accept(Double2DoubleOpenCustomHashMap.this.key[index]);
/*      */     }
/*      */ 
/*      */     
/*      */     public double nextDouble() {
/* 1157 */       return Double2DoubleOpenCustomHashMap.this.key[nextEntry()];
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
/* 1168 */       super(pos, max, mustReturnNull, hasSplit);
/*      */     }
/*      */ 
/*      */     
/*      */     public int characteristics() {
/* 1173 */       return this.hasSplit ? 257 : 321;
/*      */     }
/*      */ 
/*      */     
/*      */     final void acceptOnIndex(DoubleConsumer action, int index) {
/* 1178 */       action.accept(Double2DoubleOpenCustomHashMap.this.key[index]);
/*      */     }
/*      */ 
/*      */     
/*      */     final KeySpliterator makeForSplit(int pos, int max, boolean mustReturnNull) {
/* 1183 */       return new KeySpliterator(pos, max, mustReturnNull, true);
/*      */     } }
/*      */   
/*      */   private final class KeySet extends AbstractDoubleSet {
/*      */     private KeySet() {}
/*      */     
/*      */     public DoubleIterator iterator() {
/* 1190 */       return new Double2DoubleOpenCustomHashMap.KeyIterator();
/*      */     }
/*      */ 
/*      */     
/*      */     public DoubleSpliterator spliterator() {
/* 1195 */       return new Double2DoubleOpenCustomHashMap.KeySpliterator();
/*      */     }
/*      */ 
/*      */ 
/*      */     
/*      */     public void forEach(DoubleConsumer consumer) {
/* 1201 */       if (Double2DoubleOpenCustomHashMap.this.containsNullKey) consumer.accept(Double2DoubleOpenCustomHashMap.this.key[Double2DoubleOpenCustomHashMap.this.n]); 
/* 1202 */       for (int pos = Double2DoubleOpenCustomHashMap.this.n; pos-- != 0; ) {
/* 1203 */         double k = Double2DoubleOpenCustomHashMap.this.key[pos];
/* 1204 */         if (Double.doubleToLongBits(k) != 0L) consumer.accept(k);
/*      */       
/*      */       } 
/*      */     }
/*      */     
/*      */     public int size() {
/* 1210 */       return Double2DoubleOpenCustomHashMap.this.size;
/*      */     }
/*      */ 
/*      */     
/*      */     public boolean contains(double k) {
/* 1215 */       return Double2DoubleOpenCustomHashMap.this.containsKey(k);
/*      */     }
/*      */ 
/*      */     
/*      */     public boolean remove(double k) {
/* 1220 */       int oldSize = Double2DoubleOpenCustomHashMap.this.size;
/* 1221 */       Double2DoubleOpenCustomHashMap.this.remove(k);
/* 1222 */       return (Double2DoubleOpenCustomHashMap.this.size != oldSize);
/*      */     }
/*      */ 
/*      */     
/*      */     public void clear() {
/* 1227 */       Double2DoubleOpenCustomHashMap.this.clear();
/*      */     }
/*      */   }
/*      */ 
/*      */   
/*      */   public DoubleSet keySet() {
/* 1233 */     if (this.keys == null) this.keys = new KeySet(); 
/* 1234 */     return this.keys;
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
/* 1255 */       action.accept(Double2DoubleOpenCustomHashMap.this.value[index]);
/*      */     }
/*      */ 
/*      */     
/*      */     public double nextDouble() {
/* 1260 */       return Double2DoubleOpenCustomHashMap.this.value[nextEntry()];
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
/* 1271 */       super(pos, max, mustReturnNull, hasSplit);
/*      */     }
/*      */ 
/*      */     
/*      */     public int characteristics() {
/* 1276 */       return this.hasSplit ? 256 : 320;
/*      */     }
/*      */ 
/*      */     
/*      */     final void acceptOnIndex(DoubleConsumer action, int index) {
/* 1281 */       action.accept(Double2DoubleOpenCustomHashMap.this.value[index]);
/*      */     }
/*      */ 
/*      */     
/*      */     final ValueSpliterator makeForSplit(int pos, int max, boolean mustReturnNull) {
/* 1286 */       return new ValueSpliterator(pos, max, mustReturnNull, true);
/*      */     }
/*      */   }
/*      */ 
/*      */   
/*      */   public DoubleCollection values() {
/* 1292 */     if (this.values == null) this.values = new AbstractDoubleCollection()
/*      */         {
/*      */           public DoubleIterator iterator() {
/* 1295 */             return new Double2DoubleOpenCustomHashMap.ValueIterator();
/*      */           }
/*      */ 
/*      */           
/*      */           public DoubleSpliterator spliterator() {
/* 1300 */             return new Double2DoubleOpenCustomHashMap.ValueSpliterator();
/*      */           }
/*      */ 
/*      */ 
/*      */           
/*      */           public void forEach(DoubleConsumer consumer) {
/* 1306 */             if (Double2DoubleOpenCustomHashMap.this.containsNullKey) consumer.accept(Double2DoubleOpenCustomHashMap.this.value[Double2DoubleOpenCustomHashMap.this.n]); 
/* 1307 */             for (int pos = Double2DoubleOpenCustomHashMap.this.n; pos-- != 0;) { if (Double.doubleToLongBits(Double2DoubleOpenCustomHashMap.this.key[pos]) != 0L) consumer.accept(Double2DoubleOpenCustomHashMap.this.value[pos]);  }
/*      */           
/*      */           }
/*      */           
/*      */           public int size() {
/* 1312 */             return Double2DoubleOpenCustomHashMap.this.size;
/*      */           }
/*      */ 
/*      */           
/*      */           public boolean contains(double v) {
/* 1317 */             return Double2DoubleOpenCustomHashMap.this.containsValue(v);
/*      */           }
/*      */ 
/*      */           
/*      */           public void clear() {
/* 1322 */             Double2DoubleOpenCustomHashMap.this.clear();
/*      */           }
/*      */         }; 
/* 1325 */     return this.values;
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
/* 1342 */     return trim(this.size);
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
/* 1364 */     int l = HashCommon.nextPowerOfTwo((int)Math.ceil((n / this.f)));
/* 1365 */     if (l >= this.n || this.size > HashCommon.maxFill(l, this.f)) return true; 
/*      */     try {
/* 1367 */       rehash(l);
/* 1368 */     } catch (OutOfMemoryError cantDoIt) {
/* 1369 */       return false;
/*      */     } 
/* 1371 */     return true;
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
/* 1386 */     double[] key = this.key;
/* 1387 */     double[] value = this.value;
/* 1388 */     int mask = newN - 1;
/* 1389 */     double[] newKey = new double[newN + 1];
/* 1390 */     double[] newValue = new double[newN + 1];
/* 1391 */     int i = this.n;
/* 1392 */     for (int j = realSize(); j-- != 0; ) {
/* 1393 */       while (Double.doubleToLongBits(key[--i]) == 0L); int pos;
/* 1394 */       if (Double.doubleToLongBits(newKey[pos = HashCommon.mix(this.strategy.hashCode(key[i])) & mask]) != 0L) while (Double.doubleToLongBits(newKey[pos = pos + 1 & mask]) != 0L); 
/* 1395 */       newKey[pos] = key[i];
/* 1396 */       newValue[pos] = value[i];
/*      */     } 
/* 1398 */     newValue[newN] = value[this.n];
/* 1399 */     this.n = newN;
/* 1400 */     this.mask = mask;
/* 1401 */     this.maxFill = HashCommon.maxFill(this.n, this.f);
/* 1402 */     this.key = newKey;
/* 1403 */     this.value = newValue;
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
/*      */   public Double2DoubleOpenCustomHashMap clone() {
/*      */     Double2DoubleOpenCustomHashMap c;
/*      */     try {
/* 1420 */       c = (Double2DoubleOpenCustomHashMap)super.clone();
/* 1421 */     } catch (CloneNotSupportedException cantHappen) {
/* 1422 */       throw new InternalError();
/*      */     } 
/* 1424 */     c.keys = null;
/* 1425 */     c.values = null;
/* 1426 */     c.entries = null;
/* 1427 */     c.containsNullKey = this.containsNullKey;
/* 1428 */     c.key = (double[])this.key.clone();
/* 1429 */     c.value = (double[])this.value.clone();
/* 1430 */     c.strategy = this.strategy;
/* 1431 */     return c;
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
/* 1445 */     int h = 0;
/* 1446 */     for (int j = realSize(), i = 0, t = 0; j-- != 0; ) {
/* 1447 */       for (; Double.doubleToLongBits(this.key[i]) == 0L; i++);
/* 1448 */       t = this.strategy.hashCode(this.key[i]);
/* 1449 */       t ^= HashCommon.double2int(this.value[i]);
/* 1450 */       h += t;
/* 1451 */       i++;
/*      */     } 
/*      */     
/* 1454 */     if (this.containsNullKey) h += HashCommon.double2int(this.value[this.n]); 
/* 1455 */     return h;
/*      */   }
/*      */   
/*      */   private void writeObject(ObjectOutputStream s) throws IOException {
/* 1459 */     double[] key = this.key;
/* 1460 */     double[] value = this.value;
/* 1461 */     EntryIterator i = new EntryIterator();
/* 1462 */     s.defaultWriteObject();
/* 1463 */     for (int j = this.size; j-- != 0; ) {
/* 1464 */       int e = i.nextEntry();
/* 1465 */       s.writeDouble(key[e]);
/* 1466 */       s.writeDouble(value[e]);
/*      */     } 
/*      */   }
/*      */   
/*      */   private void readObject(ObjectInputStream s) throws IOException, ClassNotFoundException {
/* 1471 */     s.defaultReadObject();
/* 1472 */     this.n = HashCommon.arraySize(this.size, this.f);
/* 1473 */     this.maxFill = HashCommon.maxFill(this.n, this.f);
/* 1474 */     this.mask = this.n - 1;
/* 1475 */     double[] key = this.key = new double[this.n + 1];
/* 1476 */     double[] value = this.value = new double[this.n + 1];
/*      */ 
/*      */     
/* 1479 */     for (int i = this.size; i-- != 0; ) {
/* 1480 */       int pos; double k = s.readDouble();
/* 1481 */       double v = s.readDouble();
/* 1482 */       if (this.strategy.equals(k, 0.0D)) {
/* 1483 */         pos = this.n;
/* 1484 */         this.containsNullKey = true;
/*      */       } else {
/* 1486 */         pos = HashCommon.mix(this.strategy.hashCode(k)) & this.mask;
/* 1487 */         for (; Double.doubleToLongBits(key[pos]) != 0L; pos = pos + 1 & this.mask);
/*      */       } 
/* 1489 */       key[pos] = k;
/* 1490 */       value[pos] = v;
/*      */     } 
/*      */   }
/*      */   
/*      */   private void checkTable() {}
/*      */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\i\\unimi\dsi\fastutil\doubles\Double2DoubleOpenCustomHashMap.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */