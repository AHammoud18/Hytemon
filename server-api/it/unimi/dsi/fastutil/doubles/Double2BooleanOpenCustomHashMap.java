/*      */ package it.unimi.dsi.fastutil.doubles;
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
/*      */ import java.util.function.DoubleConsumer;
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
/*      */ 
/*      */ 
/*      */ public class Double2BooleanOpenCustomHashMap
/*      */   extends AbstractDouble2BooleanMap
/*      */   implements Serializable, Cloneable, Hash
/*      */ {
/*      */   private static final long serialVersionUID = 0L;
/*      */   private static final boolean ASSERTS = false;
/*      */   protected transient double[] key;
/*      */   protected transient boolean[] value;
/*      */   protected transient int mask;
/*      */   protected transient boolean containsNullKey;
/*      */   protected DoubleHash.Strategy strategy;
/*      */   protected transient int n;
/*      */   protected transient int maxFill;
/*      */   protected final transient int minN;
/*      */   protected int size;
/*      */   protected final float f;
/*      */   protected transient Double2BooleanMap.FastEntrySet entries;
/*      */   protected transient DoubleSet keys;
/*      */   protected transient BooleanCollection values;
/*      */   
/*      */   public Double2BooleanOpenCustomHashMap(int expected, float f, DoubleHash.Strategy strategy) {
/*  104 */     this.strategy = strategy;
/*  105 */     if (f <= 0.0F || f >= 1.0F) throw new IllegalArgumentException("Load factor must be greater than 0 and smaller than 1"); 
/*  106 */     if (expected < 0) throw new IllegalArgumentException("The expected number of elements must be nonnegative"); 
/*  107 */     this.f = f;
/*  108 */     this.minN = this.n = HashCommon.arraySize(expected, f);
/*  109 */     this.mask = this.n - 1;
/*  110 */     this.maxFill = HashCommon.maxFill(this.n, f);
/*  111 */     this.key = new double[this.n + 1];
/*  112 */     this.value = new boolean[this.n + 1];
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Double2BooleanOpenCustomHashMap(int expected, DoubleHash.Strategy strategy) {
/*  122 */     this(expected, 0.75F, strategy);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Double2BooleanOpenCustomHashMap(DoubleHash.Strategy strategy) {
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
/*      */   public Double2BooleanOpenCustomHashMap(Map<? extends Double, ? extends Boolean> m, float f, DoubleHash.Strategy strategy) {
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
/*      */   public Double2BooleanOpenCustomHashMap(Map<? extends Double, ? extends Boolean> m, DoubleHash.Strategy strategy) {
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
/*      */   public Double2BooleanOpenCustomHashMap(Double2BooleanMap m, float f, DoubleHash.Strategy strategy) {
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
/*      */   public Double2BooleanOpenCustomHashMap(Double2BooleanMap m, DoubleHash.Strategy strategy) {
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
/*      */   public Double2BooleanOpenCustomHashMap(double[] k, boolean[] v, float f, DoubleHash.Strategy strategy) {
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
/*      */   public Double2BooleanOpenCustomHashMap(double[] k, boolean[] v, DoubleHash.Strategy strategy) {
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
/*      */   public void putAll(Map<? extends Double, ? extends Boolean> m) {
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
/*      */   private void insert(int pos, double k, boolean v) {
/*  277 */     if (pos == this.n) this.containsNullKey = true; 
/*  278 */     this.key[pos] = k;
/*  279 */     this.value[pos] = v;
/*  280 */     if (this.size++ >= this.maxFill) rehash(HashCommon.arraySize(this.size + 1, this.f));
/*      */   
/*      */   }
/*      */ 
/*      */   
/*      */   public boolean put(double k, boolean v) {
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
/*  306 */     double[] key = this.key; while (true) {
/*      */       double curr; int last;
/*  308 */       pos = (last = pos) + 1 & this.mask;
/*      */       while (true) {
/*  310 */         if (Double.doubleToLongBits(curr = key[pos]) == 0L) {
/*  311 */           key[last] = 0.0D;
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
/*      */   public boolean remove(double k) {
/*  326 */     if (this.strategy.equals(k, 0.0D)) {
/*  327 */       if (this.containsNullKey) return removeNullEntry(); 
/*  328 */       return this.defRetValue;
/*      */     } 
/*      */     
/*  331 */     double[] key = this.key;
/*      */     double curr;
/*      */     int pos;
/*  334 */     if (Double.doubleToLongBits(curr = key[pos = HashCommon.mix(this.strategy.hashCode(k)) & this.mask]) == 0L) return this.defRetValue; 
/*  335 */     if (this.strategy.equals(k, curr)) return removeEntry(pos); 
/*      */     while (true) {
/*  337 */       if (Double.doubleToLongBits(curr = key[pos = pos + 1 & this.mask]) == 0L) return this.defRetValue; 
/*  338 */       if (this.strategy.equals(k, curr)) return removeEntry(pos);
/*      */     
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   public boolean get(double k) {
/*  345 */     if (this.strategy.equals(k, 0.0D)) return this.containsNullKey ? this.value[this.n] : this.defRetValue;
/*      */     
/*  347 */     double[] key = this.key;
/*      */     double curr;
/*      */     int pos;
/*  350 */     if (Double.doubleToLongBits(curr = key[pos = HashCommon.mix(this.strategy.hashCode(k)) & this.mask]) == 0L) return this.defRetValue; 
/*  351 */     if (this.strategy.equals(k, curr)) return this.value[pos];
/*      */     
/*      */     while (true) {
/*  354 */       if (Double.doubleToLongBits(curr = key[pos = pos + 1 & this.mask]) == 0L) return this.defRetValue; 
/*  355 */       if (this.strategy.equals(k, curr)) return this.value[pos];
/*      */     
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   public boolean containsKey(double k) {
/*  362 */     if (this.strategy.equals(k, 0.0D)) return this.containsNullKey;
/*      */     
/*  364 */     double[] key = this.key;
/*      */     double curr;
/*      */     int pos;
/*  367 */     if (Double.doubleToLongBits(curr = key[pos = HashCommon.mix(this.strategy.hashCode(k)) & this.mask]) == 0L) return false; 
/*  368 */     if (this.strategy.equals(k, curr)) return true;
/*      */     
/*      */     while (true) {
/*  371 */       if (Double.doubleToLongBits(curr = key[pos = pos + 1 & this.mask]) == 0L) return false; 
/*  372 */       if (this.strategy.equals(k, curr)) return true;
/*      */     
/*      */     } 
/*      */   }
/*      */   
/*      */   public boolean containsValue(boolean v) {
/*  378 */     boolean[] value = this.value;
/*  379 */     double[] key = this.key;
/*  380 */     if (this.containsNullKey && value[this.n] == v) return true; 
/*  381 */     for (int i = this.n; i-- != 0;) { if (Double.doubleToLongBits(key[i]) != 0L && value[i] == v) return true;  }
/*  382 */      return false;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean getOrDefault(double k, boolean defaultValue) {
/*  389 */     if (this.strategy.equals(k, 0.0D)) return this.containsNullKey ? this.value[this.n] : defaultValue;
/*      */     
/*  391 */     double[] key = this.key;
/*      */     double curr;
/*      */     int pos;
/*  394 */     if (Double.doubleToLongBits(curr = key[pos = HashCommon.mix(this.strategy.hashCode(k)) & this.mask]) == 0L) return defaultValue; 
/*  395 */     if (this.strategy.equals(k, curr)) return this.value[pos];
/*      */     
/*      */     while (true) {
/*  398 */       if (Double.doubleToLongBits(curr = key[pos = pos + 1 & this.mask]) == 0L) return defaultValue; 
/*  399 */       if (this.strategy.equals(k, curr)) return this.value[pos];
/*      */     
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   public boolean putIfAbsent(double k, boolean v) {
/*  406 */     int pos = find(k);
/*  407 */     if (pos >= 0) return this.value[pos]; 
/*  408 */     insert(-pos - 1, k, v);
/*  409 */     return this.defRetValue;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean remove(double k, boolean v) {
/*  416 */     if (this.strategy.equals(k, 0.0D)) {
/*  417 */       if (this.containsNullKey && v == this.value[this.n]) {
/*  418 */         removeNullEntry();
/*  419 */         return true;
/*      */       } 
/*  421 */       return false;
/*      */     } 
/*      */     
/*  424 */     double[] key = this.key;
/*      */     double curr;
/*      */     int pos;
/*  427 */     if (Double.doubleToLongBits(curr = key[pos = HashCommon.mix(this.strategy.hashCode(k)) & this.mask]) == 0L) return false; 
/*  428 */     if (this.strategy.equals(k, curr) && v == this.value[pos]) {
/*  429 */       removeEntry(pos);
/*  430 */       return true;
/*      */     } 
/*      */     while (true) {
/*  433 */       if (Double.doubleToLongBits(curr = key[pos = pos + 1 & this.mask]) == 0L) return false; 
/*  434 */       if (this.strategy.equals(k, curr) && v == this.value[pos]) {
/*  435 */         removeEntry(pos);
/*  436 */         return true;
/*      */       } 
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean replace(double k, boolean oldValue, boolean v) {
/*  444 */     int pos = find(k);
/*  445 */     if (pos < 0 || oldValue != this.value[pos]) return false; 
/*  446 */     this.value[pos] = v;
/*  447 */     return true;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean replace(double k, boolean v) {
/*  453 */     int pos = find(k);
/*  454 */     if (pos < 0) return this.defRetValue; 
/*  455 */     boolean oldValue = this.value[pos];
/*  456 */     this.value[pos] = v;
/*  457 */     return oldValue;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean computeIfAbsent(double k, DoublePredicate mappingFunction) {
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
/*      */   public boolean computeIfAbsent(double key, Double2BooleanFunction mappingFunction) {
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
/*      */   public boolean computeIfAbsentNullable(double k, DoubleFunction<? extends Boolean> mappingFunction) {
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
/*      */   public boolean computeIfPresent(double k, BiFunction<? super Double, ? super Boolean, ? extends Boolean> remappingFunction) {
/*  499 */     Objects.requireNonNull(remappingFunction);
/*  500 */     int pos = find(k);
/*  501 */     if (pos < 0) return this.defRetValue; 
/*  502 */     Boolean newValue = remappingFunction.apply(Double.valueOf(k), Boolean.valueOf(this.value[pos]));
/*  503 */     if (newValue == null) {
/*  504 */       if (this.strategy.equals(k, 0.0D)) { removeNullEntry(); }
/*  505 */       else { removeEntry(pos); }
/*  506 */        return this.defRetValue;
/*      */     } 
/*  508 */     this.value[pos] = newValue.booleanValue(); return newValue.booleanValue();
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean compute(double k, BiFunction<? super Double, ? super Boolean, ? extends Boolean> remappingFunction) {
/*  514 */     Objects.requireNonNull(remappingFunction);
/*  515 */     int pos = find(k);
/*  516 */     Boolean newValue = remappingFunction.apply(Double.valueOf(k), (pos >= 0) ? Boolean.valueOf(this.value[pos]) : null);
/*  517 */     if (newValue == null) {
/*  518 */       if (pos >= 0)
/*  519 */         if (this.strategy.equals(k, 0.0D)) { removeNullEntry(); }
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
/*      */   public boolean merge(double k, boolean v, BiFunction<? super Boolean, ? super Boolean, ? extends Boolean> remappingFunction) {
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
/*  545 */       if (this.strategy.equals(k, 0.0D)) { removeNullEntry(); }
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
/*  563 */     Arrays.fill(this.key, 0.0D);
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
/*      */     implements Double2BooleanMap.Entry, Map.Entry<Double, Boolean>, DoubleBooleanPair
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
/*      */     public double getDoubleKey() {
/*  594 */       return Double2BooleanOpenCustomHashMap.this.key[this.index];
/*      */     }
/*      */ 
/*      */     
/*      */     public double leftDouble() {
/*  599 */       return Double2BooleanOpenCustomHashMap.this.key[this.index];
/*      */     }
/*      */ 
/*      */     
/*      */     public boolean getBooleanValue() {
/*  604 */       return Double2BooleanOpenCustomHashMap.this.value[this.index];
/*      */     }
/*      */ 
/*      */     
/*      */     public boolean rightBoolean() {
/*  609 */       return Double2BooleanOpenCustomHashMap.this.value[this.index];
/*      */     }
/*      */ 
/*      */     
/*      */     public boolean setValue(boolean v) {
/*  614 */       boolean oldValue = Double2BooleanOpenCustomHashMap.this.value[this.index];
/*  615 */       Double2BooleanOpenCustomHashMap.this.value[this.index] = v;
/*  616 */       return oldValue;
/*      */     }
/*      */ 
/*      */     
/*      */     public DoubleBooleanPair right(boolean v) {
/*  621 */       Double2BooleanOpenCustomHashMap.this.value[this.index] = v;
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
/*      */     public Double getKey() {
/*  633 */       return Double.valueOf(Double2BooleanOpenCustomHashMap.this.key[this.index]);
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
/*  644 */       return Boolean.valueOf(Double2BooleanOpenCustomHashMap.this.value[this.index]);
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
/*  662 */       Map.Entry<Double, Boolean> e = (Map.Entry<Double, Boolean>)o;
/*  663 */       return (Double2BooleanOpenCustomHashMap.this.strategy.equals(Double2BooleanOpenCustomHashMap.this.key[this.index], ((Double)e.getKey()).doubleValue()) && Double2BooleanOpenCustomHashMap.this.value[this.index] == ((Boolean)e.getValue()).booleanValue());
/*      */     }
/*      */ 
/*      */     
/*      */     public int hashCode() {
/*  668 */       return Double2BooleanOpenCustomHashMap.this.strategy.hashCode(Double2BooleanOpenCustomHashMap.this.key[this.index]) ^ (Double2BooleanOpenCustomHashMap.this.value[this.index] ? 1231 : 1237);
/*      */     }
/*      */ 
/*      */     
/*      */     public String toString() {
/*  673 */       return Double2BooleanOpenCustomHashMap.this.key[this.index] + "=>" + Double2BooleanOpenCustomHashMap.this.value[this.index];
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
/*  684 */     int pos = Double2BooleanOpenCustomHashMap.this.n;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  690 */     int last = -1;
/*      */     
/*  692 */     int c = Double2BooleanOpenCustomHashMap.this.size;
/*      */     
/*  694 */     boolean mustReturnNullKey = Double2BooleanOpenCustomHashMap.this.containsNullKey;
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
/*  705 */       return (this.c != 0);
/*      */     }
/*      */     
/*      */     public int nextEntry() {
/*  709 */       if (!hasNext()) throw new NoSuchElementException(); 
/*  710 */       this.c--;
/*  711 */       if (this.mustReturnNullKey) {
/*  712 */         this.mustReturnNullKey = false;
/*  713 */         return this.last = Double2BooleanOpenCustomHashMap.this.n;
/*      */       } 
/*  715 */       double[] key = Double2BooleanOpenCustomHashMap.this.key;
/*      */       while (true) {
/*  717 */         if (--this.pos < 0) {
/*      */           
/*  719 */           this.last = Integer.MIN_VALUE;
/*  720 */           double k = this.wrapped.getDouble(-this.pos - 1);
/*  721 */           int p = HashCommon.mix(Double2BooleanOpenCustomHashMap.this.strategy.hashCode(k)) & Double2BooleanOpenCustomHashMap.this.mask;
/*  722 */           for (; !Double2BooleanOpenCustomHashMap.this.strategy.equals(k, key[p]); p = p + 1 & Double2BooleanOpenCustomHashMap.this.mask);
/*  723 */           return p;
/*      */         } 
/*  725 */         if (Double.doubleToLongBits(key[this.pos]) != 0L) return this.last = this.pos; 
/*      */       } 
/*      */     }
/*      */     
/*      */     public void forEachRemaining(ConsumerType action) {
/*  730 */       if (this.mustReturnNullKey) {
/*  731 */         this.mustReturnNullKey = false;
/*  732 */         acceptOnIndex(action, this.last = Double2BooleanOpenCustomHashMap.this.n);
/*  733 */         this.c--;
/*      */       } 
/*  735 */       double[] key = Double2BooleanOpenCustomHashMap.this.key;
/*  736 */       while (this.c != 0) {
/*  737 */         if (--this.pos < 0) {
/*      */           
/*  739 */           this.last = Integer.MIN_VALUE;
/*  740 */           double k = this.wrapped.getDouble(-this.pos - 1);
/*  741 */           int p = HashCommon.mix(Double2BooleanOpenCustomHashMap.this.strategy.hashCode(k)) & Double2BooleanOpenCustomHashMap.this.mask;
/*  742 */           for (; !Double2BooleanOpenCustomHashMap.this.strategy.equals(k, key[p]); p = p + 1 & Double2BooleanOpenCustomHashMap.this.mask);
/*  743 */           acceptOnIndex(action, p);
/*  744 */           this.c--; continue;
/*  745 */         }  if (Double.doubleToLongBits(key[this.pos]) != 0L) {
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
/*  762 */       double[] key = Double2BooleanOpenCustomHashMap.this.key; while (true) {
/*      */         double curr; int last;
/*  764 */         pos = (last = pos) + 1 & Double2BooleanOpenCustomHashMap.this.mask;
/*      */         while (true) {
/*  766 */           if (Double.doubleToLongBits(curr = key[pos]) == 0L) {
/*  767 */             key[last] = 0.0D;
/*      */             return;
/*      */           } 
/*  770 */           int slot = HashCommon.mix(Double2BooleanOpenCustomHashMap.this.strategy.hashCode(curr)) & Double2BooleanOpenCustomHashMap.this.mask;
/*  771 */           if ((last <= pos) ? (last >= slot || slot > pos) : (last >= slot && slot > pos))
/*  772 */             break;  pos = pos + 1 & Double2BooleanOpenCustomHashMap.this.mask;
/*      */         } 
/*  774 */         if (pos < last) {
/*  775 */           if (this.wrapped == null) this.wrapped = new DoubleArrayList(2); 
/*  776 */           this.wrapped.add(key[pos]);
/*      */         } 
/*  778 */         key[last] = curr;
/*  779 */         Double2BooleanOpenCustomHashMap.this.value[last] = Double2BooleanOpenCustomHashMap.this.value[pos];
/*      */       } 
/*      */     }
/*      */     
/*      */     public void remove() {
/*  784 */       if (this.last == -1) throw new IllegalStateException(); 
/*  785 */       if (this.last == Double2BooleanOpenCustomHashMap.this.n)
/*  786 */       { Double2BooleanOpenCustomHashMap.this.containsNullKey = false; }
/*  787 */       else if (this.pos >= 0) { shiftKeys(this.last); }
/*      */       else
/*      */       
/*  790 */       { Double2BooleanOpenCustomHashMap.this.remove(this.wrapped.getDouble(-this.pos - 1));
/*  791 */         this.last = -1;
/*      */         return; }
/*      */       
/*  794 */       Double2BooleanOpenCustomHashMap.this.size--;
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
/*      */   private final class EntryIterator extends MapIterator<Consumer<? super Double2BooleanMap.Entry>> implements ObjectIterator<Double2BooleanMap.Entry> { public Double2BooleanOpenCustomHashMap.MapEntry next() {
/*  811 */       return this.entry = new Double2BooleanOpenCustomHashMap.MapEntry(nextEntry());
/*      */     }
/*      */     private Double2BooleanOpenCustomHashMap.MapEntry entry;
/*      */     private EntryIterator() {}
/*      */     
/*      */     final void acceptOnIndex(Consumer<? super Double2BooleanMap.Entry> action, int index) {
/*  817 */       action.accept(this.entry = new Double2BooleanOpenCustomHashMap.MapEntry(index));
/*      */     }
/*      */ 
/*      */     
/*      */     public void remove() {
/*  822 */       super.remove();
/*  823 */       this.entry.index = -1;
/*      */     } }
/*      */   
/*      */   private final class FastEntryIterator extends MapIterator<Consumer<? super Double2BooleanMap.Entry>> implements ObjectIterator<Double2BooleanMap.Entry> {
/*      */     private FastEntryIterator() {
/*  828 */       this.entry = new Double2BooleanOpenCustomHashMap.MapEntry();
/*      */     }
/*      */     private final Double2BooleanOpenCustomHashMap.MapEntry entry;
/*      */     public Double2BooleanOpenCustomHashMap.MapEntry next() {
/*  832 */       this.entry.index = nextEntry();
/*  833 */       return this.entry;
/*      */     }
/*      */ 
/*      */ 
/*      */     
/*      */     final void acceptOnIndex(Consumer<? super Double2BooleanMap.Entry> action, int index) {
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
/*  851 */     int max = Double2BooleanOpenCustomHashMap.this.n;
/*      */     
/*  853 */     int c = 0;
/*      */     
/*  855 */     boolean mustReturnNull = Double2BooleanOpenCustomHashMap.this.containsNullKey;
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
/*  876 */         acceptOnIndex(action, Double2BooleanOpenCustomHashMap.this.n);
/*  877 */         return true;
/*      */       } 
/*  879 */       double[] key = Double2BooleanOpenCustomHashMap.this.key;
/*  880 */       while (this.pos < this.max) {
/*  881 */         if (Double.doubleToLongBits(key[this.pos]) != 0L) {
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
/*  895 */         acceptOnIndex(action, Double2BooleanOpenCustomHashMap.this.n);
/*      */       } 
/*  897 */       double[] key = Double2BooleanOpenCustomHashMap.this.key;
/*  898 */       while (this.pos < this.max) {
/*  899 */         if (Double.doubleToLongBits(key[this.pos]) != 0L) {
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
/*  910 */         return (Double2BooleanOpenCustomHashMap.this.size - this.c);
/*      */       }
/*      */ 
/*      */ 
/*      */       
/*  915 */       return Math.min((Double2BooleanOpenCustomHashMap.this.size - this.c), (long)(Double2BooleanOpenCustomHashMap.this.realSize() / Double2BooleanOpenCustomHashMap.this.n * (this.max - this.pos)) + (this.mustReturnNull ? 1L : 0L));
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
/*  945 */       double[] key = Double2BooleanOpenCustomHashMap.this.key;
/*  946 */       while (this.pos < this.max && n > 0L) {
/*  947 */         if (Double.doubleToLongBits(key[this.pos++]) != 0L) {
/*  948 */           skipped++;
/*  949 */           n--;
/*      */         } 
/*      */       } 
/*  952 */       return skipped;
/*      */     }
/*      */   }
/*      */   
/*      */   private final class EntrySpliterator
/*      */     extends MapSpliterator<Consumer<? super Double2BooleanMap.Entry>, EntrySpliterator> implements ObjectSpliterator<Double2BooleanMap.Entry> {
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
/*      */     final void acceptOnIndex(Consumer<? super Double2BooleanMap.Entry> action, int index) {
/*  973 */       action.accept(new Double2BooleanOpenCustomHashMap.MapEntry(index));
/*      */     }
/*      */ 
/*      */     
/*      */     final EntrySpliterator makeForSplit(int pos, int max, boolean mustReturnNull) {
/*  978 */       return new EntrySpliterator(pos, max, mustReturnNull, true);
/*      */     } }
/*      */   
/*      */   private final class MapEntrySet extends AbstractObjectSet<Double2BooleanMap.Entry> implements Double2BooleanMap.FastEntrySet {
/*      */     private MapEntrySet() {}
/*      */     
/*      */     public ObjectIterator<Double2BooleanMap.Entry> iterator() {
/*  985 */       return new Double2BooleanOpenCustomHashMap.EntryIterator();
/*      */     }
/*      */ 
/*      */     
/*      */     public ObjectIterator<Double2BooleanMap.Entry> fastIterator() {
/*  990 */       return new Double2BooleanOpenCustomHashMap.FastEntryIterator();
/*      */     }
/*      */ 
/*      */     
/*      */     public ObjectSpliterator<Double2BooleanMap.Entry> spliterator() {
/*  995 */       return new Double2BooleanOpenCustomHashMap.EntrySpliterator();
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public boolean contains(Object o) {
/* 1002 */       if (!(o instanceof Map.Entry)) return false; 
/* 1003 */       Map.Entry<?, ?> e = (Map.Entry<?, ?>)o;
/* 1004 */       if (e.getKey() == null || !(e.getKey() instanceof Double)) return false; 
/* 1005 */       if (e.getValue() == null || !(e.getValue() instanceof Boolean)) return false; 
/* 1006 */       double k = ((Double)e.getKey()).doubleValue();
/* 1007 */       boolean v = ((Boolean)e.getValue()).booleanValue();
/* 1008 */       if (Double2BooleanOpenCustomHashMap.this.strategy.equals(k, 0.0D)) return (Double2BooleanOpenCustomHashMap.this.containsNullKey && Double2BooleanOpenCustomHashMap.this.value[Double2BooleanOpenCustomHashMap.this.n] == v);
/*      */       
/* 1010 */       double[] key = Double2BooleanOpenCustomHashMap.this.key;
/*      */       double curr;
/*      */       int pos;
/* 1013 */       if (Double.doubleToLongBits(curr = key[pos = HashCommon.mix(Double2BooleanOpenCustomHashMap.this.strategy.hashCode(k)) & Double2BooleanOpenCustomHashMap.this.mask]) == 0L) return false; 
/* 1014 */       if (Double2BooleanOpenCustomHashMap.this.strategy.equals(k, curr)) return (Double2BooleanOpenCustomHashMap.this.value[pos] == v);
/*      */       
/*      */       while (true) {
/* 1017 */         if (Double.doubleToLongBits(curr = key[pos = pos + 1 & Double2BooleanOpenCustomHashMap.this.mask]) == 0L) return false; 
/* 1018 */         if (Double2BooleanOpenCustomHashMap.this.strategy.equals(k, curr)) return (Double2BooleanOpenCustomHashMap.this.value[pos] == v);
/*      */       
/*      */       } 
/*      */     }
/*      */ 
/*      */     
/*      */     public boolean remove(Object o) {
/* 1025 */       if (!(o instanceof Map.Entry)) return false; 
/* 1026 */       Map.Entry<?, ?> e = (Map.Entry<?, ?>)o;
/* 1027 */       if (e.getKey() == null || !(e.getKey() instanceof Double)) return false; 
/* 1028 */       if (e.getValue() == null || !(e.getValue() instanceof Boolean)) return false; 
/* 1029 */       double k = ((Double)e.getKey()).doubleValue();
/* 1030 */       boolean v = ((Boolean)e.getValue()).booleanValue();
/* 1031 */       if (Double2BooleanOpenCustomHashMap.this.strategy.equals(k, 0.0D)) {
/* 1032 */         if (Double2BooleanOpenCustomHashMap.this.containsNullKey && Double2BooleanOpenCustomHashMap.this.value[Double2BooleanOpenCustomHashMap.this.n] == v) {
/* 1033 */           Double2BooleanOpenCustomHashMap.this.removeNullEntry();
/* 1034 */           return true;
/*      */         } 
/* 1036 */         return false;
/*      */       } 
/*      */       
/* 1039 */       double[] key = Double2BooleanOpenCustomHashMap.this.key;
/*      */       double curr;
/*      */       int pos;
/* 1042 */       if (Double.doubleToLongBits(curr = key[pos = HashCommon.mix(Double2BooleanOpenCustomHashMap.this.strategy.hashCode(k)) & Double2BooleanOpenCustomHashMap.this.mask]) == 0L) return false; 
/* 1043 */       if (Double2BooleanOpenCustomHashMap.this.strategy.equals(curr, k)) {
/* 1044 */         if (Double2BooleanOpenCustomHashMap.this.value[pos] == v) {
/* 1045 */           Double2BooleanOpenCustomHashMap.this.removeEntry(pos);
/* 1046 */           return true;
/*      */         } 
/* 1048 */         return false;
/*      */       } 
/*      */       while (true) {
/* 1051 */         if (Double.doubleToLongBits(curr = key[pos = pos + 1 & Double2BooleanOpenCustomHashMap.this.mask]) == 0L) return false; 
/* 1052 */         if (Double2BooleanOpenCustomHashMap.this.strategy.equals(curr, k) && 
/* 1053 */           Double2BooleanOpenCustomHashMap.this.value[pos] == v) {
/* 1054 */           Double2BooleanOpenCustomHashMap.this.removeEntry(pos);
/* 1055 */           return true;
/*      */         } 
/*      */       } 
/*      */     }
/*      */ 
/*      */ 
/*      */     
/*      */     public int size() {
/* 1063 */       return Double2BooleanOpenCustomHashMap.this.size;
/*      */     }
/*      */ 
/*      */     
/*      */     public void clear() {
/* 1068 */       Double2BooleanOpenCustomHashMap.this.clear();
/*      */     }
/*      */ 
/*      */ 
/*      */     
/*      */     public void forEach(Consumer<? super Double2BooleanMap.Entry> consumer) {
/* 1074 */       if (Double2BooleanOpenCustomHashMap.this.containsNullKey) consumer.accept(new Double2BooleanOpenCustomHashMap.MapEntry(Double2BooleanOpenCustomHashMap.this.n)); 
/* 1075 */       for (int pos = Double2BooleanOpenCustomHashMap.this.n; pos-- != 0;) { if (Double.doubleToLongBits(Double2BooleanOpenCustomHashMap.this.key[pos]) != 0L) consumer.accept(new Double2BooleanOpenCustomHashMap.MapEntry(pos));
/*      */          }
/*      */     
/*      */     }
/*      */     
/*      */     public void fastForEach(Consumer<? super Double2BooleanMap.Entry> consumer) {
/* 1081 */       Double2BooleanOpenCustomHashMap.MapEntry entry = new Double2BooleanOpenCustomHashMap.MapEntry();
/* 1082 */       if (Double2BooleanOpenCustomHashMap.this.containsNullKey) {
/* 1083 */         entry.index = Double2BooleanOpenCustomHashMap.this.n;
/* 1084 */         consumer.accept(entry);
/*      */       } 
/* 1086 */       for (int pos = Double2BooleanOpenCustomHashMap.this.n; pos-- != 0;) { if (Double.doubleToLongBits(Double2BooleanOpenCustomHashMap.this.key[pos]) != 0L) {
/* 1087 */           entry.index = pos;
/* 1088 */           consumer.accept(entry);
/*      */         }  }
/*      */     
/*      */     }
/*      */   }
/*      */   
/*      */   public Double2BooleanMap.FastEntrySet double2BooleanEntrySet() {
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
/*      */     extends MapIterator<DoubleConsumer>
/*      */     implements DoubleIterator
/*      */   {
/*      */     final void acceptOnIndex(DoubleConsumer action, int index) {
/* 1117 */       action.accept(Double2BooleanOpenCustomHashMap.this.key[index]);
/*      */     }
/*      */ 
/*      */     
/*      */     public double nextDouble() {
/* 1122 */       return Double2BooleanOpenCustomHashMap.this.key[nextEntry()];
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
/* 1133 */       super(pos, max, mustReturnNull, hasSplit);
/*      */     }
/*      */ 
/*      */     
/*      */     public int characteristics() {
/* 1138 */       return this.hasSplit ? 257 : 321;
/*      */     }
/*      */ 
/*      */     
/*      */     final void acceptOnIndex(DoubleConsumer action, int index) {
/* 1143 */       action.accept(Double2BooleanOpenCustomHashMap.this.key[index]);
/*      */     }
/*      */ 
/*      */     
/*      */     final KeySpliterator makeForSplit(int pos, int max, boolean mustReturnNull) {
/* 1148 */       return new KeySpliterator(pos, max, mustReturnNull, true);
/*      */     } }
/*      */   
/*      */   private final class KeySet extends AbstractDoubleSet {
/*      */     private KeySet() {}
/*      */     
/*      */     public DoubleIterator iterator() {
/* 1155 */       return new Double2BooleanOpenCustomHashMap.KeyIterator();
/*      */     }
/*      */ 
/*      */     
/*      */     public DoubleSpliterator spliterator() {
/* 1160 */       return new Double2BooleanOpenCustomHashMap.KeySpliterator();
/*      */     }
/*      */ 
/*      */ 
/*      */     
/*      */     public void forEach(DoubleConsumer consumer) {
/* 1166 */       if (Double2BooleanOpenCustomHashMap.this.containsNullKey) consumer.accept(Double2BooleanOpenCustomHashMap.this.key[Double2BooleanOpenCustomHashMap.this.n]); 
/* 1167 */       for (int pos = Double2BooleanOpenCustomHashMap.this.n; pos-- != 0; ) {
/* 1168 */         double k = Double2BooleanOpenCustomHashMap.this.key[pos];
/* 1169 */         if (Double.doubleToLongBits(k) != 0L) consumer.accept(k);
/*      */       
/*      */       } 
/*      */     }
/*      */     
/*      */     public int size() {
/* 1175 */       return Double2BooleanOpenCustomHashMap.this.size;
/*      */     }
/*      */ 
/*      */     
/*      */     public boolean contains(double k) {
/* 1180 */       return Double2BooleanOpenCustomHashMap.this.containsKey(k);
/*      */     }
/*      */ 
/*      */     
/*      */     public boolean remove(double k) {
/* 1185 */       int oldSize = Double2BooleanOpenCustomHashMap.this.size;
/* 1186 */       Double2BooleanOpenCustomHashMap.this.remove(k);
/* 1187 */       return (Double2BooleanOpenCustomHashMap.this.size != oldSize);
/*      */     }
/*      */ 
/*      */     
/*      */     public void clear() {
/* 1192 */       Double2BooleanOpenCustomHashMap.this.clear();
/*      */     }
/*      */   }
/*      */ 
/*      */   
/*      */   public DoubleSet keySet() {
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
/* 1220 */       action.accept(Double2BooleanOpenCustomHashMap.this.value[index]);
/*      */     }
/*      */ 
/*      */     
/*      */     public boolean nextBoolean() {
/* 1225 */       return Double2BooleanOpenCustomHashMap.this.value[nextEntry()];
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
/* 1246 */       action.accept(Double2BooleanOpenCustomHashMap.this.value[index]);
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
/* 1260 */             return new Double2BooleanOpenCustomHashMap.ValueIterator();
/*      */           }
/*      */ 
/*      */           
/*      */           public BooleanSpliterator spliterator() {
/* 1265 */             return new Double2BooleanOpenCustomHashMap.ValueSpliterator();
/*      */           }
/*      */ 
/*      */ 
/*      */           
/*      */           public void forEach(BooleanConsumer consumer) {
/* 1271 */             if (Double2BooleanOpenCustomHashMap.this.containsNullKey) consumer.accept(Double2BooleanOpenCustomHashMap.this.value[Double2BooleanOpenCustomHashMap.this.n]); 
/* 1272 */             for (int pos = Double2BooleanOpenCustomHashMap.this.n; pos-- != 0;) { if (Double.doubleToLongBits(Double2BooleanOpenCustomHashMap.this.key[pos]) != 0L) consumer.accept(Double2BooleanOpenCustomHashMap.this.value[pos]);  }
/*      */           
/*      */           }
/*      */           
/*      */           public int size() {
/* 1277 */             return Double2BooleanOpenCustomHashMap.this.size;
/*      */           }
/*      */ 
/*      */           
/*      */           public boolean contains(boolean v) {
/* 1282 */             return Double2BooleanOpenCustomHashMap.this.containsValue(v);
/*      */           }
/*      */ 
/*      */           
/*      */           public void clear() {
/* 1287 */             Double2BooleanOpenCustomHashMap.this.clear();
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
/* 1351 */     double[] key = this.key;
/* 1352 */     boolean[] value = this.value;
/* 1353 */     int mask = newN - 1;
/* 1354 */     double[] newKey = new double[newN + 1];
/* 1355 */     boolean[] newValue = new boolean[newN + 1];
/* 1356 */     int i = this.n;
/* 1357 */     for (int j = realSize(); j-- != 0; ) {
/* 1358 */       while (Double.doubleToLongBits(key[--i]) == 0L); int pos;
/* 1359 */       if (Double.doubleToLongBits(newKey[pos = HashCommon.mix(this.strategy.hashCode(key[i])) & mask]) != 0L) while (Double.doubleToLongBits(newKey[pos = pos + 1 & mask]) != 0L); 
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
/*      */   public Double2BooleanOpenCustomHashMap clone() {
/*      */     Double2BooleanOpenCustomHashMap c;
/*      */     try {
/* 1385 */       c = (Double2BooleanOpenCustomHashMap)super.clone();
/* 1386 */     } catch (CloneNotSupportedException cantHappen) {
/* 1387 */       throw new InternalError();
/*      */     } 
/* 1389 */     c.keys = null;
/* 1390 */     c.values = null;
/* 1391 */     c.entries = null;
/* 1392 */     c.containsNullKey = this.containsNullKey;
/* 1393 */     c.key = (double[])this.key.clone();
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
/* 1412 */       for (; Double.doubleToLongBits(this.key[i]) == 0L; i++);
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
/* 1424 */     double[] key = this.key;
/* 1425 */     boolean[] value = this.value;
/* 1426 */     EntryIterator i = new EntryIterator();
/* 1427 */     s.defaultWriteObject();
/* 1428 */     for (int j = this.size; j-- != 0; ) {
/* 1429 */       int e = i.nextEntry();
/* 1430 */       s.writeDouble(key[e]);
/* 1431 */       s.writeBoolean(value[e]);
/*      */     } 
/*      */   }
/*      */   
/*      */   private void readObject(ObjectInputStream s) throws IOException, ClassNotFoundException {
/* 1436 */     s.defaultReadObject();
/* 1437 */     this.n = HashCommon.arraySize(this.size, this.f);
/* 1438 */     this.maxFill = HashCommon.maxFill(this.n, this.f);
/* 1439 */     this.mask = this.n - 1;
/* 1440 */     double[] key = this.key = new double[this.n + 1];
/* 1441 */     boolean[] value = this.value = new boolean[this.n + 1];
/*      */ 
/*      */     
/* 1444 */     for (int i = this.size; i-- != 0; ) {
/* 1445 */       int pos; double k = s.readDouble();
/* 1446 */       boolean v = s.readBoolean();
/* 1447 */       if (this.strategy.equals(k, 0.0D)) {
/* 1448 */         pos = this.n;
/* 1449 */         this.containsNullKey = true;
/*      */       } else {
/* 1451 */         pos = HashCommon.mix(this.strategy.hashCode(k)) & this.mask;
/* 1452 */         for (; Double.doubleToLongBits(key[pos]) != 0L; pos = pos + 1 & this.mask);
/*      */       } 
/* 1454 */       key[pos] = k;
/* 1455 */       value[pos] = v;
/*      */     } 
/*      */   }
/*      */   
/*      */   private void checkTable() {}
/*      */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\i\\unimi\dsi\fastutil\doubles\Double2BooleanOpenCustomHashMap.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */