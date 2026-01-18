/*      */ package it.unimi.dsi.fastutil.objects;
/*      */ 
/*      */ import it.unimi.dsi.fastutil.Hash;
/*      */ import it.unimi.dsi.fastutil.HashCommon;
/*      */ import it.unimi.dsi.fastutil.doubles.AbstractDoubleCollection;
/*      */ import it.unimi.dsi.fastutil.doubles.DoubleCollection;
/*      */ import it.unimi.dsi.fastutil.doubles.DoubleIterator;
/*      */ import it.unimi.dsi.fastutil.doubles.DoubleSpliterator;
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
/*      */ import java.util.function.ToDoubleFunction;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ public class Reference2DoubleOpenHashMap<K>
/*      */   extends AbstractReference2DoubleMap<K>
/*      */   implements Serializable, Cloneable, Hash
/*      */ {
/*      */   private static final long serialVersionUID = 0L;
/*      */   private static final boolean ASSERTS = false;
/*      */   protected transient K[] key;
/*      */   protected transient double[] value;
/*      */   protected transient int mask;
/*      */   protected transient boolean containsNullKey;
/*      */   protected transient int n;
/*      */   protected transient int maxFill;
/*      */   protected final transient int minN;
/*      */   protected int size;
/*      */   protected final float f;
/*      */   protected transient Reference2DoubleMap.FastEntrySet<K> entries;
/*      */   protected transient ReferenceSet<K> keys;
/*      */   protected transient DoubleCollection values;
/*      */   
/*      */   public Reference2DoubleOpenHashMap(int expected, float f) {
/*   95 */     if (f <= 0.0F || f >= 1.0F) throw new IllegalArgumentException("Load factor must be greater than 0 and smaller than 1"); 
/*   96 */     if (expected < 0) throw new IllegalArgumentException("The expected number of elements must be nonnegative"); 
/*   97 */     this.f = f;
/*   98 */     this.minN = this.n = HashCommon.arraySize(expected, f);
/*   99 */     this.mask = this.n - 1;
/*  100 */     this.maxFill = HashCommon.maxFill(this.n, f);
/*  101 */     this.key = (K[])new Object[this.n + 1];
/*  102 */     this.value = new double[this.n + 1];
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Reference2DoubleOpenHashMap(int expected) {
/*  111 */     this(expected, 0.75F);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Reference2DoubleOpenHashMap() {
/*  119 */     this(16, 0.75F);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Reference2DoubleOpenHashMap(Map<? extends K, ? extends Double> m, float f) {
/*  129 */     this(m.size(), f);
/*  130 */     putAll(m);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Reference2DoubleOpenHashMap(Map<? extends K, ? extends Double> m) {
/*  139 */     this(m, 0.75F);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Reference2DoubleOpenHashMap(Reference2DoubleMap<K> m, float f) {
/*  149 */     this(m.size(), f);
/*  150 */     putAll(m);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Reference2DoubleOpenHashMap(Reference2DoubleMap<K> m) {
/*  160 */     this(m, 0.75F);
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
/*      */   public Reference2DoubleOpenHashMap(K[] k, double[] v, float f) {
/*  172 */     this(k.length, f);
/*  173 */     if (k.length != v.length) throw new IllegalArgumentException("The key array and the value array have different lengths (" + k.length + " and " + v.length + ")"); 
/*  174 */     for (int i = 0; i < k.length; ) { put(k[i], v[i]); i++; }
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
/*      */   public Reference2DoubleOpenHashMap(K[] k, double[] v) {
/*  186 */     this(k, v, 0.75F);
/*      */   }
/*      */   
/*      */   private int realSize() {
/*  190 */     return this.containsNullKey ? (this.size - 1) : this.size;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void ensureCapacity(int capacity) {
/*  200 */     int needed = HashCommon.arraySize(capacity, this.f);
/*  201 */     if (needed > this.n) rehash(needed); 
/*      */   }
/*      */   
/*      */   private void tryCapacity(long capacity) {
/*  205 */     int needed = (int)Math.min(1073741824L, Math.max(2L, HashCommon.nextPowerOfTwo((long)Math.ceil(((float)capacity / this.f)))));
/*  206 */     if (needed > this.n) rehash(needed); 
/*      */   }
/*      */   
/*      */   private double removeEntry(int pos) {
/*  210 */     double oldValue = this.value[pos];
/*  211 */     this.size--;
/*  212 */     shiftKeys(pos);
/*  213 */     if (this.n > this.minN && this.size < this.maxFill / 4 && this.n > 16) rehash(this.n / 2); 
/*  214 */     return oldValue;
/*      */   }
/*      */   
/*      */   private double removeNullEntry() {
/*  218 */     this.containsNullKey = false;
/*  219 */     this.key[this.n] = null;
/*  220 */     double oldValue = this.value[this.n];
/*  221 */     this.size--;
/*  222 */     if (this.n > this.minN && this.size < this.maxFill / 4 && this.n > 16) rehash(this.n / 2); 
/*  223 */     return oldValue;
/*      */   }
/*      */ 
/*      */   
/*      */   public void putAll(Map<? extends K, ? extends Double> m) {
/*  228 */     if (this.f <= 0.5D) { ensureCapacity(m.size()); }
/*  229 */     else { tryCapacity((size() + m.size())); }
/*      */     
/*  231 */     super.putAll(m);
/*      */   }
/*      */ 
/*      */   
/*      */   private int find(K k) {
/*  236 */     if (k == null) return this.containsNullKey ? this.n : -(this.n + 1);
/*      */     
/*  238 */     K[] key = this.key;
/*      */     K curr;
/*      */     int pos;
/*  241 */     if ((curr = key[pos = HashCommon.mix(System.identityHashCode(k)) & this.mask]) == null) return -(pos + 1); 
/*  242 */     if (k == curr) return pos;
/*      */     
/*      */     while (true) {
/*  245 */       if ((curr = key[pos = pos + 1 & this.mask]) == null) return -(pos + 1); 
/*  246 */       if (k == curr) return pos; 
/*      */     } 
/*      */   }
/*      */   
/*      */   private void insert(int pos, K k, double v) {
/*  251 */     if (pos == this.n) this.containsNullKey = true; 
/*  252 */     this.key[pos] = k;
/*  253 */     this.value[pos] = v;
/*  254 */     if (this.size++ >= this.maxFill) rehash(HashCommon.arraySize(this.size + 1, this.f));
/*      */   
/*      */   }
/*      */ 
/*      */   
/*      */   public double put(K k, double v) {
/*  260 */     int pos = find(k);
/*  261 */     if (pos < 0) {
/*  262 */       insert(-pos - 1, k, v);
/*  263 */       return this.defRetValue;
/*      */     } 
/*  265 */     double oldValue = this.value[pos];
/*  266 */     this.value[pos] = v;
/*  267 */     return oldValue;
/*      */   }
/*      */   
/*      */   private double addToValue(int pos, double incr) {
/*  271 */     double oldValue = this.value[pos];
/*  272 */     this.value[pos] = oldValue + incr;
/*  273 */     return oldValue;
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
/*      */   public double addTo(K k, double incr) {
/*      */     int pos;
/*  291 */     if (k == null) {
/*  292 */       if (this.containsNullKey) return addToValue(this.n, incr); 
/*  293 */       pos = this.n;
/*  294 */       this.containsNullKey = true;
/*      */     } else {
/*      */       
/*  297 */       K[] key = this.key;
/*      */       K curr;
/*  299 */       if ((curr = key[pos = HashCommon.mix(System.identityHashCode(k)) & this.mask]) != null) {
/*  300 */         if (curr == k) return addToValue(pos, incr); 
/*  301 */         while ((curr = key[pos = pos + 1 & this.mask]) != null) { if (curr == k) return addToValue(pos, incr);  }
/*      */       
/*      */       } 
/*  304 */     }  this.key[pos] = k;
/*  305 */     this.value[pos] = this.defRetValue + incr;
/*  306 */     if (this.size++ >= this.maxFill) rehash(HashCommon.arraySize(this.size + 1, this.f));
/*      */     
/*  308 */     return this.defRetValue;
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
/*  321 */     K[] key = this.key; while (true) {
/*      */       K curr; int last;
/*  323 */       pos = (last = pos) + 1 & this.mask;
/*      */       while (true) {
/*  325 */         if ((curr = key[pos]) == null) {
/*  326 */           key[last] = null;
/*      */           return;
/*      */         } 
/*  329 */         int slot = HashCommon.mix(System.identityHashCode(curr)) & this.mask;
/*  330 */         if ((last <= pos) ? (last >= slot || slot > pos) : (last >= slot && slot > pos))
/*  331 */           break;  pos = pos + 1 & this.mask;
/*      */       } 
/*  333 */       key[last] = curr;
/*  334 */       this.value[last] = this.value[pos];
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public double removeDouble(Object k) {
/*  341 */     if (k == null) {
/*  342 */       if (this.containsNullKey) return removeNullEntry(); 
/*  343 */       return this.defRetValue;
/*      */     } 
/*      */     
/*  346 */     K[] key = this.key;
/*      */     K curr;
/*      */     int pos;
/*  349 */     if ((curr = key[pos = HashCommon.mix(System.identityHashCode(k)) & this.mask]) == null) return this.defRetValue; 
/*  350 */     if (k == curr) return removeEntry(pos); 
/*      */     while (true) {
/*  352 */       if ((curr = key[pos = pos + 1 & this.mask]) == null) return this.defRetValue; 
/*  353 */       if (k == curr) return removeEntry(pos);
/*      */     
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   public double getDouble(Object k) {
/*  360 */     if (k == null) return this.containsNullKey ? this.value[this.n] : this.defRetValue;
/*      */     
/*  362 */     K[] key = this.key;
/*      */     K curr;
/*      */     int pos;
/*  365 */     if ((curr = key[pos = HashCommon.mix(System.identityHashCode(k)) & this.mask]) == null) return this.defRetValue; 
/*  366 */     if (k == curr) return this.value[pos];
/*      */     
/*      */     while (true) {
/*  369 */       if ((curr = key[pos = pos + 1 & this.mask]) == null) return this.defRetValue; 
/*  370 */       if (k == curr) return this.value[pos];
/*      */     
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   public boolean containsKey(Object k) {
/*  377 */     if (k == null) return this.containsNullKey;
/*      */     
/*  379 */     K[] key = this.key;
/*      */     K curr;
/*      */     int pos;
/*  382 */     if ((curr = key[pos = HashCommon.mix(System.identityHashCode(k)) & this.mask]) == null) return false; 
/*  383 */     if (k == curr) return true;
/*      */     
/*      */     while (true) {
/*  386 */       if ((curr = key[pos = pos + 1 & this.mask]) == null) return false; 
/*  387 */       if (k == curr) return true;
/*      */     
/*      */     } 
/*      */   }
/*      */   
/*      */   public boolean containsValue(double v) {
/*  393 */     double[] value = this.value;
/*  394 */     K[] key = this.key;
/*  395 */     if (this.containsNullKey && Double.doubleToLongBits(value[this.n]) == Double.doubleToLongBits(v)) return true; 
/*  396 */     for (int i = this.n; i-- != 0;) { if (key[i] != null && Double.doubleToLongBits(value[i]) == Double.doubleToLongBits(v)) return true;  }
/*  397 */      return false;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public double getOrDefault(Object k, double defaultValue) {
/*  404 */     if (k == null) return this.containsNullKey ? this.value[this.n] : defaultValue;
/*      */     
/*  406 */     K[] key = this.key;
/*      */     K curr;
/*      */     int pos;
/*  409 */     if ((curr = key[pos = HashCommon.mix(System.identityHashCode(k)) & this.mask]) == null) return defaultValue; 
/*  410 */     if (k == curr) return this.value[pos];
/*      */     
/*      */     while (true) {
/*  413 */       if ((curr = key[pos = pos + 1 & this.mask]) == null) return defaultValue; 
/*  414 */       if (k == curr) return this.value[pos];
/*      */     
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   public double putIfAbsent(K k, double v) {
/*  421 */     int pos = find(k);
/*  422 */     if (pos >= 0) return this.value[pos]; 
/*  423 */     insert(-pos - 1, k, v);
/*  424 */     return this.defRetValue;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean remove(Object k, double v) {
/*  431 */     if (k == null) {
/*  432 */       if (this.containsNullKey && Double.doubleToLongBits(v) == Double.doubleToLongBits(this.value[this.n])) {
/*  433 */         removeNullEntry();
/*  434 */         return true;
/*      */       } 
/*  436 */       return false;
/*      */     } 
/*      */     
/*  439 */     K[] key = this.key;
/*      */     K curr;
/*      */     int pos;
/*  442 */     if ((curr = key[pos = HashCommon.mix(System.identityHashCode(k)) & this.mask]) == null) return false; 
/*  443 */     if (k == curr && Double.doubleToLongBits(v) == Double.doubleToLongBits(this.value[pos])) {
/*  444 */       removeEntry(pos);
/*  445 */       return true;
/*      */     } 
/*      */     while (true) {
/*  448 */       if ((curr = key[pos = pos + 1 & this.mask]) == null) return false; 
/*  449 */       if (k == curr && Double.doubleToLongBits(v) == Double.doubleToLongBits(this.value[pos])) {
/*  450 */         removeEntry(pos);
/*  451 */         return true;
/*      */       } 
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean replace(K k, double oldValue, double v) {
/*  459 */     int pos = find(k);
/*  460 */     if (pos < 0 || Double.doubleToLongBits(oldValue) != Double.doubleToLongBits(this.value[pos])) return false; 
/*  461 */     this.value[pos] = v;
/*  462 */     return true;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public double replace(K k, double v) {
/*  468 */     int pos = find(k);
/*  469 */     if (pos < 0) return this.defRetValue; 
/*  470 */     double oldValue = this.value[pos];
/*  471 */     this.value[pos] = v;
/*  472 */     return oldValue;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public double computeIfAbsent(K k, ToDoubleFunction<? super K> mappingFunction) {
/*  478 */     Objects.requireNonNull(mappingFunction);
/*  479 */     int pos = find(k);
/*  480 */     if (pos >= 0) return this.value[pos]; 
/*  481 */     double newValue = mappingFunction.applyAsDouble(k);
/*  482 */     insert(-pos - 1, k, newValue);
/*  483 */     return newValue;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public double computeIfAbsent(K key, Reference2DoubleFunction<? super K> mappingFunction) {
/*  489 */     Objects.requireNonNull(mappingFunction);
/*  490 */     int pos = find(key);
/*  491 */     if (pos >= 0) return this.value[pos]; 
/*  492 */     if (!mappingFunction.containsKey(key)) return this.defRetValue; 
/*  493 */     double newValue = mappingFunction.getDouble(key);
/*  494 */     insert(-pos - 1, key, newValue);
/*  495 */     return newValue;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public double computeDoubleIfPresent(K k, BiFunction<? super K, ? super Double, ? extends Double> remappingFunction) {
/*  501 */     Objects.requireNonNull(remappingFunction);
/*  502 */     int pos = find(k);
/*  503 */     if (pos < 0) return this.defRetValue; 
/*  504 */     Double newValue = remappingFunction.apply(k, Double.valueOf(this.value[pos]));
/*  505 */     if (newValue == null) {
/*  506 */       if (k == null) { removeNullEntry(); }
/*  507 */       else { removeEntry(pos); }
/*  508 */        return this.defRetValue;
/*      */     } 
/*  510 */     this.value[pos] = newValue.doubleValue(); return newValue.doubleValue();
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public double computeDouble(K k, BiFunction<? super K, ? super Double, ? extends Double> remappingFunction) {
/*  516 */     Objects.requireNonNull(remappingFunction);
/*  517 */     int pos = find(k);
/*  518 */     Double newValue = remappingFunction.apply(k, (pos >= 0) ? Double.valueOf(this.value[pos]) : null);
/*  519 */     if (newValue == null) {
/*  520 */       if (pos >= 0)
/*  521 */         if (k == null) { removeNullEntry(); }
/*  522 */         else { removeEntry(pos); }
/*      */          
/*  524 */       return this.defRetValue;
/*      */     } 
/*  526 */     double newVal = newValue.doubleValue();
/*  527 */     if (pos < 0) {
/*  528 */       insert(-pos - 1, k, newVal);
/*  529 */       return newVal;
/*      */     } 
/*  531 */     this.value[pos] = newVal; return newVal;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public double merge(K k, double v, BiFunction<? super Double, ? super Double, ? extends Double> remappingFunction) {
/*  537 */     Objects.requireNonNull(remappingFunction);
/*      */     
/*  539 */     int pos = find(k);
/*  540 */     if (pos < 0) {
/*  541 */       if (pos < 0) { insert(-pos - 1, k, v); }
/*  542 */       else { this.value[pos] = v; }
/*  543 */        return v;
/*      */     } 
/*  545 */     Double newValue = remappingFunction.apply(Double.valueOf(this.value[pos]), Double.valueOf(v));
/*  546 */     if (newValue == null) {
/*  547 */       if (k == null) { removeNullEntry(); }
/*  548 */       else { removeEntry(pos); }
/*  549 */        return this.defRetValue;
/*      */     } 
/*  551 */     this.value[pos] = newValue.doubleValue(); return newValue.doubleValue();
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
/*  562 */     if (this.size == 0)
/*  563 */       return;  this.size = 0;
/*  564 */     this.containsNullKey = false;
/*  565 */     Arrays.fill((Object[])this.key, (Object)null);
/*      */   }
/*      */ 
/*      */   
/*      */   public int size() {
/*  570 */     return this.size;
/*      */   }
/*      */ 
/*      */   
/*      */   public boolean isEmpty() {
/*  575 */     return (this.size == 0);
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   final class MapEntry
/*      */     implements Reference2DoubleMap.Entry<K>, Map.Entry<K, Double>, ReferenceDoublePair<K>
/*      */   {
/*      */     int index;
/*      */ 
/*      */ 
/*      */     
/*      */     MapEntry(int index) {
/*  588 */       this.index = index;
/*      */     }
/*      */ 
/*      */     
/*      */     MapEntry() {}
/*      */ 
/*      */     
/*      */     public K getKey() {
/*  596 */       return Reference2DoubleOpenHashMap.this.key[this.index];
/*      */     }
/*      */ 
/*      */     
/*      */     public K left() {
/*  601 */       return Reference2DoubleOpenHashMap.this.key[this.index];
/*      */     }
/*      */ 
/*      */     
/*      */     public double getDoubleValue() {
/*  606 */       return Reference2DoubleOpenHashMap.this.value[this.index];
/*      */     }
/*      */ 
/*      */     
/*      */     public double rightDouble() {
/*  611 */       return Reference2DoubleOpenHashMap.this.value[this.index];
/*      */     }
/*      */ 
/*      */     
/*      */     public double setValue(double v) {
/*  616 */       double oldValue = Reference2DoubleOpenHashMap.this.value[this.index];
/*  617 */       Reference2DoubleOpenHashMap.this.value[this.index] = v;
/*  618 */       return oldValue;
/*      */     }
/*      */ 
/*      */     
/*      */     public ReferenceDoublePair<K> right(double v) {
/*  623 */       Reference2DoubleOpenHashMap.this.value[this.index] = v;
/*  624 */       return this;
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
/*  635 */       return Double.valueOf(Reference2DoubleOpenHashMap.this.value[this.index]);
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
/*  646 */       return Double.valueOf(setValue(v.doubleValue()));
/*      */     }
/*      */ 
/*      */ 
/*      */     
/*      */     public boolean equals(Object o) {
/*  652 */       if (!(o instanceof Map.Entry)) return false; 
/*  653 */       Map.Entry<K, Double> e = (Map.Entry<K, Double>)o;
/*  654 */       return (Reference2DoubleOpenHashMap.this.key[this.index] == e.getKey() && Double.doubleToLongBits(Reference2DoubleOpenHashMap.this.value[this.index]) == Double.doubleToLongBits(((Double)e.getValue()).doubleValue()));
/*      */     }
/*      */ 
/*      */     
/*      */     public int hashCode() {
/*  659 */       return System.identityHashCode(Reference2DoubleOpenHashMap.this.key[this.index]) ^ HashCommon.double2int(Reference2DoubleOpenHashMap.this.value[this.index]);
/*      */     }
/*      */ 
/*      */     
/*      */     public String toString() {
/*  664 */       return (new StringBuilder()).append(Reference2DoubleOpenHashMap.this.key[this.index]).append("=>").append(Reference2DoubleOpenHashMap.this.value[this.index]).toString();
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
/*  675 */     int pos = Reference2DoubleOpenHashMap.this.n;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  681 */     int last = -1;
/*      */     
/*  683 */     int c = Reference2DoubleOpenHashMap.this.size;
/*      */     
/*  685 */     boolean mustReturnNullKey = Reference2DoubleOpenHashMap.this.containsNullKey;
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     ReferenceArrayList<K> wrapped;
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public boolean hasNext() {
/*  696 */       return (this.c != 0);
/*      */     }
/*      */     
/*      */     public int nextEntry() {
/*  700 */       if (!hasNext()) throw new NoSuchElementException(); 
/*  701 */       this.c--;
/*  702 */       if (this.mustReturnNullKey) {
/*  703 */         this.mustReturnNullKey = false;
/*  704 */         return this.last = Reference2DoubleOpenHashMap.this.n;
/*      */       } 
/*  706 */       K[] key = Reference2DoubleOpenHashMap.this.key;
/*      */       while (true) {
/*  708 */         if (--this.pos < 0) {
/*      */           
/*  710 */           this.last = Integer.MIN_VALUE;
/*  711 */           K k = this.wrapped.get(-this.pos - 1);
/*  712 */           int p = HashCommon.mix(System.identityHashCode(k)) & Reference2DoubleOpenHashMap.this.mask;
/*  713 */           for (; k != key[p]; p = p + 1 & Reference2DoubleOpenHashMap.this.mask);
/*  714 */           return p;
/*      */         } 
/*  716 */         if (key[this.pos] != null) return this.last = this.pos; 
/*      */       } 
/*      */     }
/*      */     
/*      */     public void forEachRemaining(ConsumerType action) {
/*  721 */       if (this.mustReturnNullKey) {
/*  722 */         this.mustReturnNullKey = false;
/*  723 */         acceptOnIndex(action, this.last = Reference2DoubleOpenHashMap.this.n);
/*  724 */         this.c--;
/*      */       } 
/*  726 */       K[] key = Reference2DoubleOpenHashMap.this.key;
/*  727 */       while (this.c != 0) {
/*  728 */         if (--this.pos < 0) {
/*      */           
/*  730 */           this.last = Integer.MIN_VALUE;
/*  731 */           K k = this.wrapped.get(-this.pos - 1);
/*  732 */           int p = HashCommon.mix(System.identityHashCode(k)) & Reference2DoubleOpenHashMap.this.mask;
/*  733 */           for (; k != key[p]; p = p + 1 & Reference2DoubleOpenHashMap.this.mask);
/*  734 */           acceptOnIndex(action, p);
/*  735 */           this.c--; continue;
/*  736 */         }  if (key[this.pos] != null) {
/*  737 */           acceptOnIndex(action, this.last = this.pos);
/*  738 */           this.c--;
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
/*  753 */       K[] key = Reference2DoubleOpenHashMap.this.key; while (true) {
/*      */         K curr; int last;
/*  755 */         pos = (last = pos) + 1 & Reference2DoubleOpenHashMap.this.mask;
/*      */         while (true) {
/*  757 */           if ((curr = key[pos]) == null) {
/*  758 */             key[last] = null;
/*      */             return;
/*      */           } 
/*  761 */           int slot = HashCommon.mix(System.identityHashCode(curr)) & Reference2DoubleOpenHashMap.this.mask;
/*  762 */           if ((last <= pos) ? (last >= slot || slot > pos) : (last >= slot && slot > pos))
/*  763 */             break;  pos = pos + 1 & Reference2DoubleOpenHashMap.this.mask;
/*      */         } 
/*  765 */         if (pos < last) {
/*  766 */           if (this.wrapped == null) this.wrapped = new ReferenceArrayList<>(2); 
/*  767 */           this.wrapped.add(key[pos]);
/*      */         } 
/*  769 */         key[last] = curr;
/*  770 */         Reference2DoubleOpenHashMap.this.value[last] = Reference2DoubleOpenHashMap.this.value[pos];
/*      */       } 
/*      */     }
/*      */     
/*      */     public void remove() {
/*  775 */       if (this.last == -1) throw new IllegalStateException(); 
/*  776 */       if (this.last == Reference2DoubleOpenHashMap.this.n)
/*  777 */       { Reference2DoubleOpenHashMap.this.containsNullKey = false;
/*  778 */         Reference2DoubleOpenHashMap.this.key[Reference2DoubleOpenHashMap.this.n] = null; }
/*  779 */       else if (this.pos >= 0) { shiftKeys(this.last); }
/*      */       else
/*      */       
/*  782 */       { Reference2DoubleOpenHashMap.this.removeDouble(this.wrapped.set(-this.pos - 1, null));
/*  783 */         this.last = -1;
/*      */         return; }
/*      */       
/*  786 */       Reference2DoubleOpenHashMap.this.size--;
/*  787 */       this.last = -1;
/*      */     }
/*      */ 
/*      */     
/*      */     public int skip(int n) {
/*  792 */       int i = n;
/*  793 */       for (; i-- != 0 && hasNext(); nextEntry());
/*  794 */       return n - i - 1;
/*      */     }
/*      */     
/*      */     private MapIterator() {}
/*      */     
/*      */     abstract void acceptOnIndex(ConsumerType param1ConsumerType, int param1Int);
/*      */   }
/*      */   
/*      */   private final class EntryIterator extends MapIterator<Consumer<? super Reference2DoubleMap.Entry<K>>> implements ObjectIterator<Reference2DoubleMap.Entry<K>> { public Reference2DoubleOpenHashMap<K>.MapEntry next() {
/*  803 */       return this.entry = new Reference2DoubleOpenHashMap.MapEntry(nextEntry());
/*      */     }
/*      */     private Reference2DoubleOpenHashMap<K>.MapEntry entry;
/*      */     private EntryIterator() {}
/*      */     
/*      */     final void acceptOnIndex(Consumer<? super Reference2DoubleMap.Entry<K>> action, int index) {
/*  809 */       action.accept(this.entry = new Reference2DoubleOpenHashMap.MapEntry(index));
/*      */     }
/*      */ 
/*      */     
/*      */     public void remove() {
/*  814 */       super.remove();
/*  815 */       this.entry.index = -1;
/*      */     } }
/*      */   
/*      */   private final class FastEntryIterator extends MapIterator<Consumer<? super Reference2DoubleMap.Entry<K>>> implements ObjectIterator<Reference2DoubleMap.Entry<K>> {
/*      */     private FastEntryIterator() {
/*  820 */       this.entry = new Reference2DoubleOpenHashMap.MapEntry();
/*      */     }
/*      */     private final Reference2DoubleOpenHashMap<K>.MapEntry entry;
/*      */     public Reference2DoubleOpenHashMap<K>.MapEntry next() {
/*  824 */       this.entry.index = nextEntry();
/*  825 */       return this.entry;
/*      */     }
/*      */ 
/*      */ 
/*      */     
/*      */     final void acceptOnIndex(Consumer<? super Reference2DoubleMap.Entry<K>> action, int index) {
/*  831 */       this.entry.index = index;
/*  832 */       action.accept(this.entry);
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private abstract class MapSpliterator<ConsumerType, SplitType extends MapSpliterator<ConsumerType, SplitType>>
/*      */   {
/*  841 */     int pos = 0;
/*      */     
/*  843 */     int max = Reference2DoubleOpenHashMap.this.n;
/*      */     
/*  845 */     int c = 0;
/*      */     
/*  847 */     boolean mustReturnNull = Reference2DoubleOpenHashMap.this.containsNullKey;
/*      */     
/*      */     boolean hasSplit = false;
/*      */     
/*      */     MapSpliterator() {}
/*      */     
/*      */     MapSpliterator(int pos, int max, boolean mustReturnNull, boolean hasSplit) {
/*  854 */       this.pos = pos;
/*  855 */       this.max = max;
/*  856 */       this.mustReturnNull = mustReturnNull;
/*  857 */       this.hasSplit = hasSplit;
/*      */     }
/*      */     
/*      */     abstract void acceptOnIndex(ConsumerType param1ConsumerType, int param1Int);
/*      */     
/*      */     abstract SplitType makeForSplit(int param1Int1, int param1Int2, boolean param1Boolean);
/*      */     
/*      */     public boolean tryAdvance(ConsumerType action) {
/*  865 */       if (this.mustReturnNull) {
/*  866 */         this.mustReturnNull = false;
/*  867 */         this.c++;
/*  868 */         acceptOnIndex(action, Reference2DoubleOpenHashMap.this.n);
/*  869 */         return true;
/*      */       } 
/*  871 */       K[] key = Reference2DoubleOpenHashMap.this.key;
/*  872 */       while (this.pos < this.max) {
/*  873 */         if (key[this.pos] != null) {
/*  874 */           this.c++;
/*  875 */           acceptOnIndex(action, this.pos++);
/*  876 */           return true;
/*      */         } 
/*  878 */         this.pos++;
/*      */       } 
/*  880 */       return false;
/*      */     }
/*      */     
/*      */     public void forEachRemaining(ConsumerType action) {
/*  884 */       if (this.mustReturnNull) {
/*  885 */         this.mustReturnNull = false;
/*  886 */         this.c++;
/*  887 */         acceptOnIndex(action, Reference2DoubleOpenHashMap.this.n);
/*      */       } 
/*  889 */       K[] key = Reference2DoubleOpenHashMap.this.key;
/*  890 */       while (this.pos < this.max) {
/*  891 */         if (key[this.pos] != null) {
/*  892 */           acceptOnIndex(action, this.pos);
/*  893 */           this.c++;
/*      */         } 
/*  895 */         this.pos++;
/*      */       } 
/*      */     }
/*      */     
/*      */     public long estimateSize() {
/*  900 */       if (!this.hasSplit)
/*      */       {
/*  902 */         return (Reference2DoubleOpenHashMap.this.size - this.c);
/*      */       }
/*      */ 
/*      */ 
/*      */       
/*  907 */       return Math.min((Reference2DoubleOpenHashMap.this.size - this.c), (long)(Reference2DoubleOpenHashMap.this.realSize() / Reference2DoubleOpenHashMap.this.n * (this.max - this.pos)) + (this.mustReturnNull ? 1L : 0L));
/*      */     }
/*      */ 
/*      */     
/*      */     public SplitType trySplit() {
/*  912 */       if (this.pos >= this.max - 1) return null; 
/*  913 */       int retLen = this.max - this.pos >> 1;
/*  914 */       if (retLen <= 1) return null; 
/*  915 */       int myNewPos = this.pos + retLen;
/*  916 */       int retPos = this.pos;
/*  917 */       int retMax = myNewPos;
/*      */ 
/*      */ 
/*      */       
/*  921 */       SplitType split = makeForSplit(retPos, retMax, this.mustReturnNull);
/*  922 */       this.pos = myNewPos;
/*  923 */       this.mustReturnNull = false;
/*  924 */       this.hasSplit = true;
/*  925 */       return split;
/*      */     }
/*      */     
/*      */     public long skip(long n) {
/*  929 */       if (n < 0L) throw new IllegalArgumentException("Argument must be nonnegative: " + n); 
/*  930 */       if (n == 0L) return 0L; 
/*  931 */       long skipped = 0L;
/*  932 */       if (this.mustReturnNull) {
/*  933 */         this.mustReturnNull = false;
/*  934 */         skipped++;
/*  935 */         n--;
/*      */       } 
/*  937 */       K[] key = Reference2DoubleOpenHashMap.this.key;
/*  938 */       while (this.pos < this.max && n > 0L) {
/*  939 */         if (key[this.pos++] != null) {
/*  940 */           skipped++;
/*  941 */           n--;
/*      */         } 
/*      */       } 
/*  944 */       return skipped;
/*      */     }
/*      */   }
/*      */   
/*      */   private final class EntrySpliterator
/*      */     extends MapSpliterator<Consumer<? super Reference2DoubleMap.Entry<K>>, EntrySpliterator> implements ObjectSpliterator<Reference2DoubleMap.Entry<K>> {
/*      */     private static final int POST_SPLIT_CHARACTERISTICS = 1;
/*      */     
/*      */     EntrySpliterator() {}
/*      */     
/*      */     EntrySpliterator(int pos, int max, boolean mustReturnNull, boolean hasSplit) {
/*  955 */       super(pos, max, mustReturnNull, hasSplit);
/*      */     }
/*      */ 
/*      */     
/*      */     public int characteristics() {
/*  960 */       return this.hasSplit ? 1 : 65;
/*      */     }
/*      */ 
/*      */     
/*      */     final void acceptOnIndex(Consumer<? super Reference2DoubleMap.Entry<K>> action, int index) {
/*  965 */       action.accept(new Reference2DoubleOpenHashMap.MapEntry(index));
/*      */     }
/*      */ 
/*      */     
/*      */     final EntrySpliterator makeForSplit(int pos, int max, boolean mustReturnNull) {
/*  970 */       return new EntrySpliterator(pos, max, mustReturnNull, true);
/*      */     } }
/*      */   
/*      */   private final class MapEntrySet extends AbstractObjectSet<Reference2DoubleMap.Entry<K>> implements Reference2DoubleMap.FastEntrySet<K> {
/*      */     private MapEntrySet() {}
/*      */     
/*      */     public ObjectIterator<Reference2DoubleMap.Entry<K>> iterator() {
/*  977 */       return new Reference2DoubleOpenHashMap.EntryIterator();
/*      */     }
/*      */ 
/*      */     
/*      */     public ObjectIterator<Reference2DoubleMap.Entry<K>> fastIterator() {
/*  982 */       return new Reference2DoubleOpenHashMap.FastEntryIterator();
/*      */     }
/*      */ 
/*      */     
/*      */     public ObjectSpliterator<Reference2DoubleMap.Entry<K>> spliterator() {
/*  987 */       return new Reference2DoubleOpenHashMap.EntrySpliterator();
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public boolean contains(Object o) {
/*  994 */       if (!(o instanceof Map.Entry)) return false; 
/*  995 */       Map.Entry<?, ?> e = (Map.Entry<?, ?>)o;
/*  996 */       if (e.getValue() == null || !(e.getValue() instanceof Double)) return false; 
/*  997 */       K k = (K)e.getKey();
/*  998 */       double v = ((Double)e.getValue()).doubleValue();
/*  999 */       if (k == null) return (Reference2DoubleOpenHashMap.this.containsNullKey && Double.doubleToLongBits(Reference2DoubleOpenHashMap.this.value[Reference2DoubleOpenHashMap.this.n]) == Double.doubleToLongBits(v));
/*      */       
/* 1001 */       K[] key = Reference2DoubleOpenHashMap.this.key;
/*      */       K curr;
/*      */       int pos;
/* 1004 */       if ((curr = key[pos = HashCommon.mix(System.identityHashCode(k)) & Reference2DoubleOpenHashMap.this.mask]) == null) return false; 
/* 1005 */       if (k == curr) return (Double.doubleToLongBits(Reference2DoubleOpenHashMap.this.value[pos]) == Double.doubleToLongBits(v));
/*      */       
/*      */       while (true) {
/* 1008 */         if ((curr = key[pos = pos + 1 & Reference2DoubleOpenHashMap.this.mask]) == null) return false; 
/* 1009 */         if (k == curr) return (Double.doubleToLongBits(Reference2DoubleOpenHashMap.this.value[pos]) == Double.doubleToLongBits(v));
/*      */       
/*      */       } 
/*      */     }
/*      */ 
/*      */     
/*      */     public boolean remove(Object o) {
/* 1016 */       if (!(o instanceof Map.Entry)) return false; 
/* 1017 */       Map.Entry<?, ?> e = (Map.Entry<?, ?>)o;
/* 1018 */       if (e.getValue() == null || !(e.getValue() instanceof Double)) return false; 
/* 1019 */       K k = (K)e.getKey();
/* 1020 */       double v = ((Double)e.getValue()).doubleValue();
/* 1021 */       if (k == null) {
/* 1022 */         if (Reference2DoubleOpenHashMap.this.containsNullKey && Double.doubleToLongBits(Reference2DoubleOpenHashMap.this.value[Reference2DoubleOpenHashMap.this.n]) == Double.doubleToLongBits(v)) {
/* 1023 */           Reference2DoubleOpenHashMap.this.removeNullEntry();
/* 1024 */           return true;
/*      */         } 
/* 1026 */         return false;
/*      */       } 
/*      */       
/* 1029 */       K[] key = Reference2DoubleOpenHashMap.this.key;
/*      */       K curr;
/*      */       int pos;
/* 1032 */       if ((curr = key[pos = HashCommon.mix(System.identityHashCode(k)) & Reference2DoubleOpenHashMap.this.mask]) == null) return false; 
/* 1033 */       if (curr == k) {
/* 1034 */         if (Double.doubleToLongBits(Reference2DoubleOpenHashMap.this.value[pos]) == Double.doubleToLongBits(v)) {
/* 1035 */           Reference2DoubleOpenHashMap.this.removeEntry(pos);
/* 1036 */           return true;
/*      */         } 
/* 1038 */         return false;
/*      */       } 
/*      */       while (true) {
/* 1041 */         if ((curr = key[pos = pos + 1 & Reference2DoubleOpenHashMap.this.mask]) == null) return false; 
/* 1042 */         if (curr == k && 
/* 1043 */           Double.doubleToLongBits(Reference2DoubleOpenHashMap.this.value[pos]) == Double.doubleToLongBits(v)) {
/* 1044 */           Reference2DoubleOpenHashMap.this.removeEntry(pos);
/* 1045 */           return true;
/*      */         } 
/*      */       } 
/*      */     }
/*      */ 
/*      */ 
/*      */     
/*      */     public int size() {
/* 1053 */       return Reference2DoubleOpenHashMap.this.size;
/*      */     }
/*      */ 
/*      */     
/*      */     public void clear() {
/* 1058 */       Reference2DoubleOpenHashMap.this.clear();
/*      */     }
/*      */ 
/*      */ 
/*      */     
/*      */     public void forEach(Consumer<? super Reference2DoubleMap.Entry<K>> consumer) {
/* 1064 */       if (Reference2DoubleOpenHashMap.this.containsNullKey) consumer.accept(new Reference2DoubleOpenHashMap.MapEntry(Reference2DoubleOpenHashMap.this.n)); 
/* 1065 */       for (int pos = Reference2DoubleOpenHashMap.this.n; pos-- != 0;) { if (Reference2DoubleOpenHashMap.this.key[pos] != null) consumer.accept(new Reference2DoubleOpenHashMap.MapEntry(pos));
/*      */          }
/*      */     
/*      */     }
/*      */     
/*      */     public void fastForEach(Consumer<? super Reference2DoubleMap.Entry<K>> consumer) {
/* 1071 */       Reference2DoubleOpenHashMap<K>.MapEntry entry = new Reference2DoubleOpenHashMap.MapEntry();
/* 1072 */       if (Reference2DoubleOpenHashMap.this.containsNullKey) {
/* 1073 */         entry.index = Reference2DoubleOpenHashMap.this.n;
/* 1074 */         consumer.accept(entry);
/*      */       } 
/* 1076 */       for (int pos = Reference2DoubleOpenHashMap.this.n; pos-- != 0;) { if (Reference2DoubleOpenHashMap.this.key[pos] != null) {
/* 1077 */           entry.index = pos;
/* 1078 */           consumer.accept(entry);
/*      */         }  }
/*      */     
/*      */     }
/*      */   }
/*      */   
/*      */   public Reference2DoubleMap.FastEntrySet<K> reference2DoubleEntrySet() {
/* 1085 */     if (this.entries == null) this.entries = new MapEntrySet(); 
/* 1086 */     return this.entries;
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
/*      */     extends MapIterator<Consumer<? super K>>
/*      */     implements ObjectIterator<K>
/*      */   {
/*      */     final void acceptOnIndex(Consumer<? super K> action, int index) {
/* 1107 */       action.accept(Reference2DoubleOpenHashMap.this.key[index]);
/*      */     }
/*      */ 
/*      */     
/*      */     public K next() {
/* 1112 */       return Reference2DoubleOpenHashMap.this.key[nextEntry()];
/*      */     }
/*      */   }
/*      */   
/*      */   private final class KeySpliterator
/*      */     extends MapSpliterator<Consumer<? super K>, KeySpliterator> implements ObjectSpliterator<K> {
/*      */     private static final int POST_SPLIT_CHARACTERISTICS = 1;
/*      */     
/*      */     KeySpliterator() {}
/*      */     
/*      */     KeySpliterator(int pos, int max, boolean mustReturnNull, boolean hasSplit) {
/* 1123 */       super(pos, max, mustReturnNull, hasSplit);
/*      */     }
/*      */ 
/*      */     
/*      */     public int characteristics() {
/* 1128 */       return this.hasSplit ? 1 : 65;
/*      */     }
/*      */ 
/*      */     
/*      */     final void acceptOnIndex(Consumer<? super K> action, int index) {
/* 1133 */       action.accept(Reference2DoubleOpenHashMap.this.key[index]);
/*      */     }
/*      */ 
/*      */     
/*      */     final KeySpliterator makeForSplit(int pos, int max, boolean mustReturnNull) {
/* 1138 */       return new KeySpliterator(pos, max, mustReturnNull, true);
/*      */     } }
/*      */   
/*      */   private final class KeySet extends AbstractReferenceSet<K> {
/*      */     private KeySet() {}
/*      */     
/*      */     public ObjectIterator<K> iterator() {
/* 1145 */       return new Reference2DoubleOpenHashMap.KeyIterator();
/*      */     }
/*      */ 
/*      */     
/*      */     public ObjectSpliterator<K> spliterator() {
/* 1150 */       return new Reference2DoubleOpenHashMap.KeySpliterator();
/*      */     }
/*      */ 
/*      */ 
/*      */     
/*      */     public void forEach(Consumer<? super K> consumer) {
/* 1156 */       if (Reference2DoubleOpenHashMap.this.containsNullKey) consumer.accept(Reference2DoubleOpenHashMap.this.key[Reference2DoubleOpenHashMap.this.n]); 
/* 1157 */       for (int pos = Reference2DoubleOpenHashMap.this.n; pos-- != 0; ) {
/* 1158 */         K k = Reference2DoubleOpenHashMap.this.key[pos];
/* 1159 */         if (k != null) consumer.accept(k);
/*      */       
/*      */       } 
/*      */     }
/*      */     
/*      */     public int size() {
/* 1165 */       return Reference2DoubleOpenHashMap.this.size;
/*      */     }
/*      */ 
/*      */     
/*      */     public boolean contains(Object k) {
/* 1170 */       return Reference2DoubleOpenHashMap.this.containsKey(k);
/*      */     }
/*      */ 
/*      */     
/*      */     public boolean remove(Object k) {
/* 1175 */       int oldSize = Reference2DoubleOpenHashMap.this.size;
/* 1176 */       Reference2DoubleOpenHashMap.this.removeDouble(k);
/* 1177 */       return (Reference2DoubleOpenHashMap.this.size != oldSize);
/*      */     }
/*      */ 
/*      */     
/*      */     public void clear() {
/* 1182 */       Reference2DoubleOpenHashMap.this.clear();
/*      */     }
/*      */   }
/*      */ 
/*      */   
/*      */   public ReferenceSet<K> keySet() {
/* 1188 */     if (this.keys == null) this.keys = new KeySet(); 
/* 1189 */     return this.keys;
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
/* 1210 */       action.accept(Reference2DoubleOpenHashMap.this.value[index]);
/*      */     }
/*      */ 
/*      */     
/*      */     public double nextDouble() {
/* 1215 */       return Reference2DoubleOpenHashMap.this.value[nextEntry()];
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
/* 1226 */       super(pos, max, mustReturnNull, hasSplit);
/*      */     }
/*      */ 
/*      */     
/*      */     public int characteristics() {
/* 1231 */       return this.hasSplit ? 256 : 320;
/*      */     }
/*      */ 
/*      */     
/*      */     final void acceptOnIndex(DoubleConsumer action, int index) {
/* 1236 */       action.accept(Reference2DoubleOpenHashMap.this.value[index]);
/*      */     }
/*      */ 
/*      */     
/*      */     final ValueSpliterator makeForSplit(int pos, int max, boolean mustReturnNull) {
/* 1241 */       return new ValueSpliterator(pos, max, mustReturnNull, true);
/*      */     }
/*      */   }
/*      */ 
/*      */   
/*      */   public DoubleCollection values() {
/* 1247 */     if (this.values == null) this.values = (DoubleCollection)new AbstractDoubleCollection()
/*      */         {
/*      */           public DoubleIterator iterator() {
/* 1250 */             return new Reference2DoubleOpenHashMap.ValueIterator();
/*      */           }
/*      */ 
/*      */           
/*      */           public DoubleSpliterator spliterator() {
/* 1255 */             return new Reference2DoubleOpenHashMap.ValueSpliterator();
/*      */           }
/*      */ 
/*      */ 
/*      */           
/*      */           public void forEach(DoubleConsumer consumer) {
/* 1261 */             if (Reference2DoubleOpenHashMap.this.containsNullKey) consumer.accept(Reference2DoubleOpenHashMap.this.value[Reference2DoubleOpenHashMap.this.n]); 
/* 1262 */             for (int pos = Reference2DoubleOpenHashMap.this.n; pos-- != 0;) { if (Reference2DoubleOpenHashMap.this.key[pos] != null) consumer.accept(Reference2DoubleOpenHashMap.this.value[pos]);  }
/*      */           
/*      */           }
/*      */           
/*      */           public int size() {
/* 1267 */             return Reference2DoubleOpenHashMap.this.size;
/*      */           }
/*      */ 
/*      */           
/*      */           public boolean contains(double v) {
/* 1272 */             return Reference2DoubleOpenHashMap.this.containsValue(v);
/*      */           }
/*      */ 
/*      */           
/*      */           public void clear() {
/* 1277 */             Reference2DoubleOpenHashMap.this.clear();
/*      */           }
/*      */         }; 
/* 1280 */     return this.values;
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
/* 1297 */     return trim(this.size);
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
/* 1319 */     int l = HashCommon.nextPowerOfTwo((int)Math.ceil((n / this.f)));
/* 1320 */     if (l >= this.n || this.size > HashCommon.maxFill(l, this.f)) return true; 
/*      */     try {
/* 1322 */       rehash(l);
/* 1323 */     } catch (OutOfMemoryError cantDoIt) {
/* 1324 */       return false;
/*      */     } 
/* 1326 */     return true;
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
/* 1341 */     K[] key = this.key;
/* 1342 */     double[] value = this.value;
/* 1343 */     int mask = newN - 1;
/* 1344 */     K[] newKey = (K[])new Object[newN + 1];
/* 1345 */     double[] newValue = new double[newN + 1];
/* 1346 */     int i = this.n;
/* 1347 */     for (int j = realSize(); j-- != 0; ) {
/* 1348 */       while (key[--i] == null); int pos;
/* 1349 */       if (newKey[pos = HashCommon.mix(System.identityHashCode(key[i])) & mask] != null) while (newKey[pos = pos + 1 & mask] != null); 
/* 1350 */       newKey[pos] = key[i];
/* 1351 */       newValue[pos] = value[i];
/*      */     } 
/* 1353 */     newValue[newN] = value[this.n];
/* 1354 */     this.n = newN;
/* 1355 */     this.mask = mask;
/* 1356 */     this.maxFill = HashCommon.maxFill(this.n, this.f);
/* 1357 */     this.key = newKey;
/* 1358 */     this.value = newValue;
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
/*      */   public Reference2DoubleOpenHashMap<K> clone() {
/*      */     Reference2DoubleOpenHashMap<K> c;
/*      */     try {
/* 1375 */       c = (Reference2DoubleOpenHashMap<K>)super.clone();
/* 1376 */     } catch (CloneNotSupportedException cantHappen) {
/* 1377 */       throw new InternalError();
/*      */     } 
/* 1379 */     c.keys = null;
/* 1380 */     c.values = null;
/* 1381 */     c.entries = null;
/* 1382 */     c.containsNullKey = this.containsNullKey;
/* 1383 */     c.key = (K[])this.key.clone();
/* 1384 */     c.value = (double[])this.value.clone();
/* 1385 */     return c;
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
/* 1399 */     int h = 0;
/* 1400 */     for (int j = realSize(), i = 0, t = 0; j-- != 0; ) {
/* 1401 */       for (; this.key[i] == null; i++);
/* 1402 */       if (this != this.key[i]) t = System.identityHashCode(this.key[i]); 
/* 1403 */       t ^= HashCommon.double2int(this.value[i]);
/* 1404 */       h += t;
/* 1405 */       i++;
/*      */     } 
/*      */     
/* 1408 */     if (this.containsNullKey) h += HashCommon.double2int(this.value[this.n]); 
/* 1409 */     return h;
/*      */   }
/*      */   
/*      */   private void writeObject(ObjectOutputStream s) throws IOException {
/* 1413 */     K[] key = this.key;
/* 1414 */     double[] value = this.value;
/* 1415 */     EntryIterator i = new EntryIterator();
/* 1416 */     s.defaultWriteObject();
/* 1417 */     for (int j = this.size; j-- != 0; ) {
/* 1418 */       int e = i.nextEntry();
/* 1419 */       s.writeObject(key[e]);
/* 1420 */       s.writeDouble(value[e]);
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   private void readObject(ObjectInputStream s) throws IOException, ClassNotFoundException {
/* 1426 */     s.defaultReadObject();
/* 1427 */     this.n = HashCommon.arraySize(this.size, this.f);
/* 1428 */     this.maxFill = HashCommon.maxFill(this.n, this.f);
/* 1429 */     this.mask = this.n - 1;
/* 1430 */     K[] key = this.key = (K[])new Object[this.n + 1];
/* 1431 */     double[] value = this.value = new double[this.n + 1];
/*      */ 
/*      */     
/* 1434 */     for (int i = this.size; i-- != 0; ) {
/* 1435 */       int pos; K k = (K)s.readObject();
/* 1436 */       double v = s.readDouble();
/* 1437 */       if (k == null) {
/* 1438 */         pos = this.n;
/* 1439 */         this.containsNullKey = true;
/*      */       } else {
/* 1441 */         pos = HashCommon.mix(System.identityHashCode(k)) & this.mask;
/* 1442 */         for (; key[pos] != null; pos = pos + 1 & this.mask);
/*      */       } 
/* 1444 */       key[pos] = k;
/* 1445 */       value[pos] = v;
/*      */     } 
/*      */   }
/*      */   
/*      */   private void checkTable() {}
/*      */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\i\\unimi\dsi\fastutil\objects\Reference2DoubleOpenHashMap.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */