/*      */ package it.unimi.dsi.fastutil.objects;
/*      */ 
/*      */ import it.unimi.dsi.fastutil.Hash;
/*      */ import it.unimi.dsi.fastutil.HashCommon;
/*      */ import it.unimi.dsi.fastutil.booleans.AbstractBooleanCollection;
/*      */ import it.unimi.dsi.fastutil.booleans.BooleanCollection;
/*      */ import it.unimi.dsi.fastutil.booleans.BooleanConsumer;
/*      */ import it.unimi.dsi.fastutil.booleans.BooleanIterator;
/*      */ import it.unimi.dsi.fastutil.booleans.BooleanSpliterator;
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
/*      */ import java.util.function.Predicate;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ public class Reference2BooleanOpenHashMap<K>
/*      */   extends AbstractReference2BooleanMap<K>
/*      */   implements Serializable, Cloneable, Hash
/*      */ {
/*      */   private static final long serialVersionUID = 0L;
/*      */   private static final boolean ASSERTS = false;
/*      */   protected transient K[] key;
/*      */   protected transient boolean[] value;
/*      */   protected transient int mask;
/*      */   protected transient boolean containsNullKey;
/*      */   protected transient int n;
/*      */   protected transient int maxFill;
/*      */   protected final transient int minN;
/*      */   protected int size;
/*      */   protected final float f;
/*      */   protected transient Reference2BooleanMap.FastEntrySet<K> entries;
/*      */   protected transient ReferenceSet<K> keys;
/*      */   protected transient BooleanCollection values;
/*      */   
/*      */   public Reference2BooleanOpenHashMap(int expected, float f) {
/*   96 */     if (f <= 0.0F || f >= 1.0F) throw new IllegalArgumentException("Load factor must be greater than 0 and smaller than 1"); 
/*   97 */     if (expected < 0) throw new IllegalArgumentException("The expected number of elements must be nonnegative"); 
/*   98 */     this.f = f;
/*   99 */     this.minN = this.n = HashCommon.arraySize(expected, f);
/*  100 */     this.mask = this.n - 1;
/*  101 */     this.maxFill = HashCommon.maxFill(this.n, f);
/*  102 */     this.key = (K[])new Object[this.n + 1];
/*  103 */     this.value = new boolean[this.n + 1];
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Reference2BooleanOpenHashMap(int expected) {
/*  112 */     this(expected, 0.75F);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Reference2BooleanOpenHashMap() {
/*  120 */     this(16, 0.75F);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Reference2BooleanOpenHashMap(Map<? extends K, ? extends Boolean> m, float f) {
/*  130 */     this(m.size(), f);
/*  131 */     putAll(m);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Reference2BooleanOpenHashMap(Map<? extends K, ? extends Boolean> m) {
/*  140 */     this(m, 0.75F);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Reference2BooleanOpenHashMap(Reference2BooleanMap<K> m, float f) {
/*  150 */     this(m.size(), f);
/*  151 */     putAll(m);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Reference2BooleanOpenHashMap(Reference2BooleanMap<K> m) {
/*  161 */     this(m, 0.75F);
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
/*      */   public Reference2BooleanOpenHashMap(K[] k, boolean[] v, float f) {
/*  173 */     this(k.length, f);
/*  174 */     if (k.length != v.length) throw new IllegalArgumentException("The key array and the value array have different lengths (" + k.length + " and " + v.length + ")"); 
/*  175 */     for (int i = 0; i < k.length; ) { put(k[i], v[i]); i++; }
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
/*      */   public Reference2BooleanOpenHashMap(K[] k, boolean[] v) {
/*  187 */     this(k, v, 0.75F);
/*      */   }
/*      */   
/*      */   private int realSize() {
/*  191 */     return this.containsNullKey ? (this.size - 1) : this.size;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void ensureCapacity(int capacity) {
/*  201 */     int needed = HashCommon.arraySize(capacity, this.f);
/*  202 */     if (needed > this.n) rehash(needed); 
/*      */   }
/*      */   
/*      */   private void tryCapacity(long capacity) {
/*  206 */     int needed = (int)Math.min(1073741824L, Math.max(2L, HashCommon.nextPowerOfTwo((long)Math.ceil(((float)capacity / this.f)))));
/*  207 */     if (needed > this.n) rehash(needed); 
/*      */   }
/*      */   
/*      */   private boolean removeEntry(int pos) {
/*  211 */     boolean oldValue = this.value[pos];
/*  212 */     this.size--;
/*  213 */     shiftKeys(pos);
/*  214 */     if (this.n > this.minN && this.size < this.maxFill / 4 && this.n > 16) rehash(this.n / 2); 
/*  215 */     return oldValue;
/*      */   }
/*      */   
/*      */   private boolean removeNullEntry() {
/*  219 */     this.containsNullKey = false;
/*  220 */     this.key[this.n] = null;
/*  221 */     boolean oldValue = this.value[this.n];
/*  222 */     this.size--;
/*  223 */     if (this.n > this.minN && this.size < this.maxFill / 4 && this.n > 16) rehash(this.n / 2); 
/*  224 */     return oldValue;
/*      */   }
/*      */ 
/*      */   
/*      */   public void putAll(Map<? extends K, ? extends Boolean> m) {
/*  229 */     if (this.f <= 0.5D) { ensureCapacity(m.size()); }
/*  230 */     else { tryCapacity((size() + m.size())); }
/*      */     
/*  232 */     super.putAll(m);
/*      */   }
/*      */ 
/*      */   
/*      */   private int find(K k) {
/*  237 */     if (k == null) return this.containsNullKey ? this.n : -(this.n + 1);
/*      */     
/*  239 */     K[] key = this.key;
/*      */     K curr;
/*      */     int pos;
/*  242 */     if ((curr = key[pos = HashCommon.mix(System.identityHashCode(k)) & this.mask]) == null) return -(pos + 1); 
/*  243 */     if (k == curr) return pos;
/*      */     
/*      */     while (true) {
/*  246 */       if ((curr = key[pos = pos + 1 & this.mask]) == null) return -(pos + 1); 
/*  247 */       if (k == curr) return pos; 
/*      */     } 
/*      */   }
/*      */   
/*      */   private void insert(int pos, K k, boolean v) {
/*  252 */     if (pos == this.n) this.containsNullKey = true; 
/*  253 */     this.key[pos] = k;
/*  254 */     this.value[pos] = v;
/*  255 */     if (this.size++ >= this.maxFill) rehash(HashCommon.arraySize(this.size + 1, this.f));
/*      */   
/*      */   }
/*      */ 
/*      */   
/*      */   public boolean put(K k, boolean v) {
/*  261 */     int pos = find(k);
/*  262 */     if (pos < 0) {
/*  263 */       insert(-pos - 1, k, v);
/*  264 */       return this.defRetValue;
/*      */     } 
/*  266 */     boolean oldValue = this.value[pos];
/*  267 */     this.value[pos] = v;
/*  268 */     return oldValue;
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
/*  281 */     K[] key = this.key; while (true) {
/*      */       K curr; int last;
/*  283 */       pos = (last = pos) + 1 & this.mask;
/*      */       while (true) {
/*  285 */         if ((curr = key[pos]) == null) {
/*  286 */           key[last] = null;
/*      */           return;
/*      */         } 
/*  289 */         int slot = HashCommon.mix(System.identityHashCode(curr)) & this.mask;
/*  290 */         if ((last <= pos) ? (last >= slot || slot > pos) : (last >= slot && slot > pos))
/*  291 */           break;  pos = pos + 1 & this.mask;
/*      */       } 
/*  293 */       key[last] = curr;
/*  294 */       this.value[last] = this.value[pos];
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean removeBoolean(Object k) {
/*  301 */     if (k == null) {
/*  302 */       if (this.containsNullKey) return removeNullEntry(); 
/*  303 */       return this.defRetValue;
/*      */     } 
/*      */     
/*  306 */     K[] key = this.key;
/*      */     K curr;
/*      */     int pos;
/*  309 */     if ((curr = key[pos = HashCommon.mix(System.identityHashCode(k)) & this.mask]) == null) return this.defRetValue; 
/*  310 */     if (k == curr) return removeEntry(pos); 
/*      */     while (true) {
/*  312 */       if ((curr = key[pos = pos + 1 & this.mask]) == null) return this.defRetValue; 
/*  313 */       if (k == curr) return removeEntry(pos);
/*      */     
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   public boolean getBoolean(Object k) {
/*  320 */     if (k == null) return this.containsNullKey ? this.value[this.n] : this.defRetValue;
/*      */     
/*  322 */     K[] key = this.key;
/*      */     K curr;
/*      */     int pos;
/*  325 */     if ((curr = key[pos = HashCommon.mix(System.identityHashCode(k)) & this.mask]) == null) return this.defRetValue; 
/*  326 */     if (k == curr) return this.value[pos];
/*      */     
/*      */     while (true) {
/*  329 */       if ((curr = key[pos = pos + 1 & this.mask]) == null) return this.defRetValue; 
/*  330 */       if (k == curr) return this.value[pos];
/*      */     
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   public boolean containsKey(Object k) {
/*  337 */     if (k == null) return this.containsNullKey;
/*      */     
/*  339 */     K[] key = this.key;
/*      */     K curr;
/*      */     int pos;
/*  342 */     if ((curr = key[pos = HashCommon.mix(System.identityHashCode(k)) & this.mask]) == null) return false; 
/*  343 */     if (k == curr) return true;
/*      */     
/*      */     while (true) {
/*  346 */       if ((curr = key[pos = pos + 1 & this.mask]) == null) return false; 
/*  347 */       if (k == curr) return true;
/*      */     
/*      */     } 
/*      */   }
/*      */   
/*      */   public boolean containsValue(boolean v) {
/*  353 */     boolean[] value = this.value;
/*  354 */     K[] key = this.key;
/*  355 */     if (this.containsNullKey && value[this.n] == v) return true; 
/*  356 */     for (int i = this.n; i-- != 0;) { if (key[i] != null && value[i] == v) return true;  }
/*  357 */      return false;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean getOrDefault(Object k, boolean defaultValue) {
/*  364 */     if (k == null) return this.containsNullKey ? this.value[this.n] : defaultValue;
/*      */     
/*  366 */     K[] key = this.key;
/*      */     K curr;
/*      */     int pos;
/*  369 */     if ((curr = key[pos = HashCommon.mix(System.identityHashCode(k)) & this.mask]) == null) return defaultValue; 
/*  370 */     if (k == curr) return this.value[pos];
/*      */     
/*      */     while (true) {
/*  373 */       if ((curr = key[pos = pos + 1 & this.mask]) == null) return defaultValue; 
/*  374 */       if (k == curr) return this.value[pos];
/*      */     
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   public boolean putIfAbsent(K k, boolean v) {
/*  381 */     int pos = find(k);
/*  382 */     if (pos >= 0) return this.value[pos]; 
/*  383 */     insert(-pos - 1, k, v);
/*  384 */     return this.defRetValue;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean remove(Object k, boolean v) {
/*  391 */     if (k == null) {
/*  392 */       if (this.containsNullKey && v == this.value[this.n]) {
/*  393 */         removeNullEntry();
/*  394 */         return true;
/*      */       } 
/*  396 */       return false;
/*      */     } 
/*      */     
/*  399 */     K[] key = this.key;
/*      */     K curr;
/*      */     int pos;
/*  402 */     if ((curr = key[pos = HashCommon.mix(System.identityHashCode(k)) & this.mask]) == null) return false; 
/*  403 */     if (k == curr && v == this.value[pos]) {
/*  404 */       removeEntry(pos);
/*  405 */       return true;
/*      */     } 
/*      */     while (true) {
/*  408 */       if ((curr = key[pos = pos + 1 & this.mask]) == null) return false; 
/*  409 */       if (k == curr && v == this.value[pos]) {
/*  410 */         removeEntry(pos);
/*  411 */         return true;
/*      */       } 
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean replace(K k, boolean oldValue, boolean v) {
/*  419 */     int pos = find(k);
/*  420 */     if (pos < 0 || oldValue != this.value[pos]) return false; 
/*  421 */     this.value[pos] = v;
/*  422 */     return true;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean replace(K k, boolean v) {
/*  428 */     int pos = find(k);
/*  429 */     if (pos < 0) return this.defRetValue; 
/*  430 */     boolean oldValue = this.value[pos];
/*  431 */     this.value[pos] = v;
/*  432 */     return oldValue;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean computeIfAbsent(K k, Predicate<? super K> mappingFunction) {
/*  438 */     Objects.requireNonNull(mappingFunction);
/*  439 */     int pos = find(k);
/*  440 */     if (pos >= 0) return this.value[pos]; 
/*  441 */     boolean newValue = mappingFunction.test(k);
/*  442 */     insert(-pos - 1, k, newValue);
/*  443 */     return newValue;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean computeIfAbsent(K key, Reference2BooleanFunction<? super K> mappingFunction) {
/*  449 */     Objects.requireNonNull(mappingFunction);
/*  450 */     int pos = find(key);
/*  451 */     if (pos >= 0) return this.value[pos]; 
/*  452 */     if (!mappingFunction.containsKey(key)) return this.defRetValue; 
/*  453 */     boolean newValue = mappingFunction.getBoolean(key);
/*  454 */     insert(-pos - 1, key, newValue);
/*  455 */     return newValue;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean computeBooleanIfPresent(K k, BiFunction<? super K, ? super Boolean, ? extends Boolean> remappingFunction) {
/*  461 */     Objects.requireNonNull(remappingFunction);
/*  462 */     int pos = find(k);
/*  463 */     if (pos < 0) return this.defRetValue; 
/*  464 */     Boolean newValue = remappingFunction.apply(k, Boolean.valueOf(this.value[pos]));
/*  465 */     if (newValue == null) {
/*  466 */       if (k == null) { removeNullEntry(); }
/*  467 */       else { removeEntry(pos); }
/*  468 */        return this.defRetValue;
/*      */     } 
/*  470 */     this.value[pos] = newValue.booleanValue(); return newValue.booleanValue();
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean computeBoolean(K k, BiFunction<? super K, ? super Boolean, ? extends Boolean> remappingFunction) {
/*  476 */     Objects.requireNonNull(remappingFunction);
/*  477 */     int pos = find(k);
/*  478 */     Boolean newValue = remappingFunction.apply(k, (pos >= 0) ? Boolean.valueOf(this.value[pos]) : null);
/*  479 */     if (newValue == null) {
/*  480 */       if (pos >= 0)
/*  481 */         if (k == null) { removeNullEntry(); }
/*  482 */         else { removeEntry(pos); }
/*      */          
/*  484 */       return this.defRetValue;
/*      */     } 
/*  486 */     boolean newVal = newValue.booleanValue();
/*  487 */     if (pos < 0) {
/*  488 */       insert(-pos - 1, k, newVal);
/*  489 */       return newVal;
/*      */     } 
/*  491 */     this.value[pos] = newVal; return newVal;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean merge(K k, boolean v, BiFunction<? super Boolean, ? super Boolean, ? extends Boolean> remappingFunction) {
/*  497 */     Objects.requireNonNull(remappingFunction);
/*      */     
/*  499 */     int pos = find(k);
/*  500 */     if (pos < 0) {
/*  501 */       if (pos < 0) { insert(-pos - 1, k, v); }
/*  502 */       else { this.value[pos] = v; }
/*  503 */        return v;
/*      */     } 
/*  505 */     Boolean newValue = remappingFunction.apply(Boolean.valueOf(this.value[pos]), Boolean.valueOf(v));
/*  506 */     if (newValue == null) {
/*  507 */       if (k == null) { removeNullEntry(); }
/*  508 */       else { removeEntry(pos); }
/*  509 */        return this.defRetValue;
/*      */     } 
/*  511 */     this.value[pos] = newValue.booleanValue(); return newValue.booleanValue();
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
/*  522 */     if (this.size == 0)
/*  523 */       return;  this.size = 0;
/*  524 */     this.containsNullKey = false;
/*  525 */     Arrays.fill((Object[])this.key, (Object)null);
/*      */   }
/*      */ 
/*      */   
/*      */   public int size() {
/*  530 */     return this.size;
/*      */   }
/*      */ 
/*      */   
/*      */   public boolean isEmpty() {
/*  535 */     return (this.size == 0);
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   final class MapEntry
/*      */     implements Reference2BooleanMap.Entry<K>, Map.Entry<K, Boolean>, ReferenceBooleanPair<K>
/*      */   {
/*      */     int index;
/*      */ 
/*      */ 
/*      */     
/*      */     MapEntry(int index) {
/*  548 */       this.index = index;
/*      */     }
/*      */ 
/*      */     
/*      */     MapEntry() {}
/*      */ 
/*      */     
/*      */     public K getKey() {
/*  556 */       return Reference2BooleanOpenHashMap.this.key[this.index];
/*      */     }
/*      */ 
/*      */     
/*      */     public K left() {
/*  561 */       return Reference2BooleanOpenHashMap.this.key[this.index];
/*      */     }
/*      */ 
/*      */     
/*      */     public boolean getBooleanValue() {
/*  566 */       return Reference2BooleanOpenHashMap.this.value[this.index];
/*      */     }
/*      */ 
/*      */     
/*      */     public boolean rightBoolean() {
/*  571 */       return Reference2BooleanOpenHashMap.this.value[this.index];
/*      */     }
/*      */ 
/*      */     
/*      */     public boolean setValue(boolean v) {
/*  576 */       boolean oldValue = Reference2BooleanOpenHashMap.this.value[this.index];
/*  577 */       Reference2BooleanOpenHashMap.this.value[this.index] = v;
/*  578 */       return oldValue;
/*      */     }
/*      */ 
/*      */     
/*      */     public ReferenceBooleanPair<K> right(boolean v) {
/*  583 */       Reference2BooleanOpenHashMap.this.value[this.index] = v;
/*  584 */       return this;
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
/*  595 */       return Boolean.valueOf(Reference2BooleanOpenHashMap.this.value[this.index]);
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
/*  606 */       return Boolean.valueOf(setValue(v.booleanValue()));
/*      */     }
/*      */ 
/*      */ 
/*      */     
/*      */     public boolean equals(Object o) {
/*  612 */       if (!(o instanceof Map.Entry)) return false; 
/*  613 */       Map.Entry<K, Boolean> e = (Map.Entry<K, Boolean>)o;
/*  614 */       return (Reference2BooleanOpenHashMap.this.key[this.index] == e.getKey() && Reference2BooleanOpenHashMap.this.value[this.index] == ((Boolean)e.getValue()).booleanValue());
/*      */     }
/*      */ 
/*      */     
/*      */     public int hashCode() {
/*  619 */       return System.identityHashCode(Reference2BooleanOpenHashMap.this.key[this.index]) ^ (Reference2BooleanOpenHashMap.this.value[this.index] ? 1231 : 1237);
/*      */     }
/*      */ 
/*      */     
/*      */     public String toString() {
/*  624 */       return (new StringBuilder()).append(Reference2BooleanOpenHashMap.this.key[this.index]).append("=>").append(Reference2BooleanOpenHashMap.this.value[this.index]).toString();
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
/*  635 */     int pos = Reference2BooleanOpenHashMap.this.n;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  641 */     int last = -1;
/*      */     
/*  643 */     int c = Reference2BooleanOpenHashMap.this.size;
/*      */     
/*  645 */     boolean mustReturnNullKey = Reference2BooleanOpenHashMap.this.containsNullKey;
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
/*  656 */       return (this.c != 0);
/*      */     }
/*      */     
/*      */     public int nextEntry() {
/*  660 */       if (!hasNext()) throw new NoSuchElementException(); 
/*  661 */       this.c--;
/*  662 */       if (this.mustReturnNullKey) {
/*  663 */         this.mustReturnNullKey = false;
/*  664 */         return this.last = Reference2BooleanOpenHashMap.this.n;
/*      */       } 
/*  666 */       K[] key = Reference2BooleanOpenHashMap.this.key;
/*      */       while (true) {
/*  668 */         if (--this.pos < 0) {
/*      */           
/*  670 */           this.last = Integer.MIN_VALUE;
/*  671 */           K k = this.wrapped.get(-this.pos - 1);
/*  672 */           int p = HashCommon.mix(System.identityHashCode(k)) & Reference2BooleanOpenHashMap.this.mask;
/*  673 */           for (; k != key[p]; p = p + 1 & Reference2BooleanOpenHashMap.this.mask);
/*  674 */           return p;
/*      */         } 
/*  676 */         if (key[this.pos] != null) return this.last = this.pos; 
/*      */       } 
/*      */     }
/*      */     
/*      */     public void forEachRemaining(ConsumerType action) {
/*  681 */       if (this.mustReturnNullKey) {
/*  682 */         this.mustReturnNullKey = false;
/*  683 */         acceptOnIndex(action, this.last = Reference2BooleanOpenHashMap.this.n);
/*  684 */         this.c--;
/*      */       } 
/*  686 */       K[] key = Reference2BooleanOpenHashMap.this.key;
/*  687 */       while (this.c != 0) {
/*  688 */         if (--this.pos < 0) {
/*      */           
/*  690 */           this.last = Integer.MIN_VALUE;
/*  691 */           K k = this.wrapped.get(-this.pos - 1);
/*  692 */           int p = HashCommon.mix(System.identityHashCode(k)) & Reference2BooleanOpenHashMap.this.mask;
/*  693 */           for (; k != key[p]; p = p + 1 & Reference2BooleanOpenHashMap.this.mask);
/*  694 */           acceptOnIndex(action, p);
/*  695 */           this.c--; continue;
/*  696 */         }  if (key[this.pos] != null) {
/*  697 */           acceptOnIndex(action, this.last = this.pos);
/*  698 */           this.c--;
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
/*  713 */       K[] key = Reference2BooleanOpenHashMap.this.key; while (true) {
/*      */         K curr; int last;
/*  715 */         pos = (last = pos) + 1 & Reference2BooleanOpenHashMap.this.mask;
/*      */         while (true) {
/*  717 */           if ((curr = key[pos]) == null) {
/*  718 */             key[last] = null;
/*      */             return;
/*      */           } 
/*  721 */           int slot = HashCommon.mix(System.identityHashCode(curr)) & Reference2BooleanOpenHashMap.this.mask;
/*  722 */           if ((last <= pos) ? (last >= slot || slot > pos) : (last >= slot && slot > pos))
/*  723 */             break;  pos = pos + 1 & Reference2BooleanOpenHashMap.this.mask;
/*      */         } 
/*  725 */         if (pos < last) {
/*  726 */           if (this.wrapped == null) this.wrapped = new ReferenceArrayList<>(2); 
/*  727 */           this.wrapped.add(key[pos]);
/*      */         } 
/*  729 */         key[last] = curr;
/*  730 */         Reference2BooleanOpenHashMap.this.value[last] = Reference2BooleanOpenHashMap.this.value[pos];
/*      */       } 
/*      */     }
/*      */     
/*      */     public void remove() {
/*  735 */       if (this.last == -1) throw new IllegalStateException(); 
/*  736 */       if (this.last == Reference2BooleanOpenHashMap.this.n)
/*  737 */       { Reference2BooleanOpenHashMap.this.containsNullKey = false;
/*  738 */         Reference2BooleanOpenHashMap.this.key[Reference2BooleanOpenHashMap.this.n] = null; }
/*  739 */       else if (this.pos >= 0) { shiftKeys(this.last); }
/*      */       else
/*      */       
/*  742 */       { Reference2BooleanOpenHashMap.this.removeBoolean(this.wrapped.set(-this.pos - 1, null));
/*  743 */         this.last = -1;
/*      */         return; }
/*      */       
/*  746 */       Reference2BooleanOpenHashMap.this.size--;
/*  747 */       this.last = -1;
/*      */     }
/*      */ 
/*      */     
/*      */     public int skip(int n) {
/*  752 */       int i = n;
/*  753 */       for (; i-- != 0 && hasNext(); nextEntry());
/*  754 */       return n - i - 1;
/*      */     }
/*      */     
/*      */     private MapIterator() {}
/*      */     
/*      */     abstract void acceptOnIndex(ConsumerType param1ConsumerType, int param1Int);
/*      */   }
/*      */   
/*      */   private final class EntryIterator extends MapIterator<Consumer<? super Reference2BooleanMap.Entry<K>>> implements ObjectIterator<Reference2BooleanMap.Entry<K>> { public Reference2BooleanOpenHashMap<K>.MapEntry next() {
/*  763 */       return this.entry = new Reference2BooleanOpenHashMap.MapEntry(nextEntry());
/*      */     }
/*      */     private Reference2BooleanOpenHashMap<K>.MapEntry entry;
/*      */     private EntryIterator() {}
/*      */     
/*      */     final void acceptOnIndex(Consumer<? super Reference2BooleanMap.Entry<K>> action, int index) {
/*  769 */       action.accept(this.entry = new Reference2BooleanOpenHashMap.MapEntry(index));
/*      */     }
/*      */ 
/*      */     
/*      */     public void remove() {
/*  774 */       super.remove();
/*  775 */       this.entry.index = -1;
/*      */     } }
/*      */   
/*      */   private final class FastEntryIterator extends MapIterator<Consumer<? super Reference2BooleanMap.Entry<K>>> implements ObjectIterator<Reference2BooleanMap.Entry<K>> {
/*      */     private FastEntryIterator() {
/*  780 */       this.entry = new Reference2BooleanOpenHashMap.MapEntry();
/*      */     }
/*      */     private final Reference2BooleanOpenHashMap<K>.MapEntry entry;
/*      */     public Reference2BooleanOpenHashMap<K>.MapEntry next() {
/*  784 */       this.entry.index = nextEntry();
/*  785 */       return this.entry;
/*      */     }
/*      */ 
/*      */ 
/*      */     
/*      */     final void acceptOnIndex(Consumer<? super Reference2BooleanMap.Entry<K>> action, int index) {
/*  791 */       this.entry.index = index;
/*  792 */       action.accept(this.entry);
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private abstract class MapSpliterator<ConsumerType, SplitType extends MapSpliterator<ConsumerType, SplitType>>
/*      */   {
/*  801 */     int pos = 0;
/*      */     
/*  803 */     int max = Reference2BooleanOpenHashMap.this.n;
/*      */     
/*  805 */     int c = 0;
/*      */     
/*  807 */     boolean mustReturnNull = Reference2BooleanOpenHashMap.this.containsNullKey;
/*      */     
/*      */     boolean hasSplit = false;
/*      */     
/*      */     MapSpliterator() {}
/*      */     
/*      */     MapSpliterator(int pos, int max, boolean mustReturnNull, boolean hasSplit) {
/*  814 */       this.pos = pos;
/*  815 */       this.max = max;
/*  816 */       this.mustReturnNull = mustReturnNull;
/*  817 */       this.hasSplit = hasSplit;
/*      */     }
/*      */     
/*      */     abstract void acceptOnIndex(ConsumerType param1ConsumerType, int param1Int);
/*      */     
/*      */     abstract SplitType makeForSplit(int param1Int1, int param1Int2, boolean param1Boolean);
/*      */     
/*      */     public boolean tryAdvance(ConsumerType action) {
/*  825 */       if (this.mustReturnNull) {
/*  826 */         this.mustReturnNull = false;
/*  827 */         this.c++;
/*  828 */         acceptOnIndex(action, Reference2BooleanOpenHashMap.this.n);
/*  829 */         return true;
/*      */       } 
/*  831 */       K[] key = Reference2BooleanOpenHashMap.this.key;
/*  832 */       while (this.pos < this.max) {
/*  833 */         if (key[this.pos] != null) {
/*  834 */           this.c++;
/*  835 */           acceptOnIndex(action, this.pos++);
/*  836 */           return true;
/*      */         } 
/*  838 */         this.pos++;
/*      */       } 
/*  840 */       return false;
/*      */     }
/*      */     
/*      */     public void forEachRemaining(ConsumerType action) {
/*  844 */       if (this.mustReturnNull) {
/*  845 */         this.mustReturnNull = false;
/*  846 */         this.c++;
/*  847 */         acceptOnIndex(action, Reference2BooleanOpenHashMap.this.n);
/*      */       } 
/*  849 */       K[] key = Reference2BooleanOpenHashMap.this.key;
/*  850 */       while (this.pos < this.max) {
/*  851 */         if (key[this.pos] != null) {
/*  852 */           acceptOnIndex(action, this.pos);
/*  853 */           this.c++;
/*      */         } 
/*  855 */         this.pos++;
/*      */       } 
/*      */     }
/*      */     
/*      */     public long estimateSize() {
/*  860 */       if (!this.hasSplit)
/*      */       {
/*  862 */         return (Reference2BooleanOpenHashMap.this.size - this.c);
/*      */       }
/*      */ 
/*      */ 
/*      */       
/*  867 */       return Math.min((Reference2BooleanOpenHashMap.this.size - this.c), (long)(Reference2BooleanOpenHashMap.this.realSize() / Reference2BooleanOpenHashMap.this.n * (this.max - this.pos)) + (this.mustReturnNull ? 1L : 0L));
/*      */     }
/*      */ 
/*      */     
/*      */     public SplitType trySplit() {
/*  872 */       if (this.pos >= this.max - 1) return null; 
/*  873 */       int retLen = this.max - this.pos >> 1;
/*  874 */       if (retLen <= 1) return null; 
/*  875 */       int myNewPos = this.pos + retLen;
/*  876 */       int retPos = this.pos;
/*  877 */       int retMax = myNewPos;
/*      */ 
/*      */ 
/*      */       
/*  881 */       SplitType split = makeForSplit(retPos, retMax, this.mustReturnNull);
/*  882 */       this.pos = myNewPos;
/*  883 */       this.mustReturnNull = false;
/*  884 */       this.hasSplit = true;
/*  885 */       return split;
/*      */     }
/*      */     
/*      */     public long skip(long n) {
/*  889 */       if (n < 0L) throw new IllegalArgumentException("Argument must be nonnegative: " + n); 
/*  890 */       if (n == 0L) return 0L; 
/*  891 */       long skipped = 0L;
/*  892 */       if (this.mustReturnNull) {
/*  893 */         this.mustReturnNull = false;
/*  894 */         skipped++;
/*  895 */         n--;
/*      */       } 
/*  897 */       K[] key = Reference2BooleanOpenHashMap.this.key;
/*  898 */       while (this.pos < this.max && n > 0L) {
/*  899 */         if (key[this.pos++] != null) {
/*  900 */           skipped++;
/*  901 */           n--;
/*      */         } 
/*      */       } 
/*  904 */       return skipped;
/*      */     }
/*      */   }
/*      */   
/*      */   private final class EntrySpliterator
/*      */     extends MapSpliterator<Consumer<? super Reference2BooleanMap.Entry<K>>, EntrySpliterator> implements ObjectSpliterator<Reference2BooleanMap.Entry<K>> {
/*      */     private static final int POST_SPLIT_CHARACTERISTICS = 1;
/*      */     
/*      */     EntrySpliterator() {}
/*      */     
/*      */     EntrySpliterator(int pos, int max, boolean mustReturnNull, boolean hasSplit) {
/*  915 */       super(pos, max, mustReturnNull, hasSplit);
/*      */     }
/*      */ 
/*      */     
/*      */     public int characteristics() {
/*  920 */       return this.hasSplit ? 1 : 65;
/*      */     }
/*      */ 
/*      */     
/*      */     final void acceptOnIndex(Consumer<? super Reference2BooleanMap.Entry<K>> action, int index) {
/*  925 */       action.accept(new Reference2BooleanOpenHashMap.MapEntry(index));
/*      */     }
/*      */ 
/*      */     
/*      */     final EntrySpliterator makeForSplit(int pos, int max, boolean mustReturnNull) {
/*  930 */       return new EntrySpliterator(pos, max, mustReturnNull, true);
/*      */     } }
/*      */   
/*      */   private final class MapEntrySet extends AbstractObjectSet<Reference2BooleanMap.Entry<K>> implements Reference2BooleanMap.FastEntrySet<K> {
/*      */     private MapEntrySet() {}
/*      */     
/*      */     public ObjectIterator<Reference2BooleanMap.Entry<K>> iterator() {
/*  937 */       return new Reference2BooleanOpenHashMap.EntryIterator();
/*      */     }
/*      */ 
/*      */     
/*      */     public ObjectIterator<Reference2BooleanMap.Entry<K>> fastIterator() {
/*  942 */       return new Reference2BooleanOpenHashMap.FastEntryIterator();
/*      */     }
/*      */ 
/*      */     
/*      */     public ObjectSpliterator<Reference2BooleanMap.Entry<K>> spliterator() {
/*  947 */       return new Reference2BooleanOpenHashMap.EntrySpliterator();
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public boolean contains(Object o) {
/*  954 */       if (!(o instanceof Map.Entry)) return false; 
/*  955 */       Map.Entry<?, ?> e = (Map.Entry<?, ?>)o;
/*  956 */       if (e.getValue() == null || !(e.getValue() instanceof Boolean)) return false; 
/*  957 */       K k = (K)e.getKey();
/*  958 */       boolean v = ((Boolean)e.getValue()).booleanValue();
/*  959 */       if (k == null) return (Reference2BooleanOpenHashMap.this.containsNullKey && Reference2BooleanOpenHashMap.this.value[Reference2BooleanOpenHashMap.this.n] == v);
/*      */       
/*  961 */       K[] key = Reference2BooleanOpenHashMap.this.key;
/*      */       K curr;
/*      */       int pos;
/*  964 */       if ((curr = key[pos = HashCommon.mix(System.identityHashCode(k)) & Reference2BooleanOpenHashMap.this.mask]) == null) return false; 
/*  965 */       if (k == curr) return (Reference2BooleanOpenHashMap.this.value[pos] == v);
/*      */       
/*      */       while (true) {
/*  968 */         if ((curr = key[pos = pos + 1 & Reference2BooleanOpenHashMap.this.mask]) == null) return false; 
/*  969 */         if (k == curr) return (Reference2BooleanOpenHashMap.this.value[pos] == v);
/*      */       
/*      */       } 
/*      */     }
/*      */ 
/*      */     
/*      */     public boolean remove(Object o) {
/*  976 */       if (!(o instanceof Map.Entry)) return false; 
/*  977 */       Map.Entry<?, ?> e = (Map.Entry<?, ?>)o;
/*  978 */       if (e.getValue() == null || !(e.getValue() instanceof Boolean)) return false; 
/*  979 */       K k = (K)e.getKey();
/*  980 */       boolean v = ((Boolean)e.getValue()).booleanValue();
/*  981 */       if (k == null) {
/*  982 */         if (Reference2BooleanOpenHashMap.this.containsNullKey && Reference2BooleanOpenHashMap.this.value[Reference2BooleanOpenHashMap.this.n] == v) {
/*  983 */           Reference2BooleanOpenHashMap.this.removeNullEntry();
/*  984 */           return true;
/*      */         } 
/*  986 */         return false;
/*      */       } 
/*      */       
/*  989 */       K[] key = Reference2BooleanOpenHashMap.this.key;
/*      */       K curr;
/*      */       int pos;
/*  992 */       if ((curr = key[pos = HashCommon.mix(System.identityHashCode(k)) & Reference2BooleanOpenHashMap.this.mask]) == null) return false; 
/*  993 */       if (curr == k) {
/*  994 */         if (Reference2BooleanOpenHashMap.this.value[pos] == v) {
/*  995 */           Reference2BooleanOpenHashMap.this.removeEntry(pos);
/*  996 */           return true;
/*      */         } 
/*  998 */         return false;
/*      */       } 
/*      */       while (true) {
/* 1001 */         if ((curr = key[pos = pos + 1 & Reference2BooleanOpenHashMap.this.mask]) == null) return false; 
/* 1002 */         if (curr == k && 
/* 1003 */           Reference2BooleanOpenHashMap.this.value[pos] == v) {
/* 1004 */           Reference2BooleanOpenHashMap.this.removeEntry(pos);
/* 1005 */           return true;
/*      */         } 
/*      */       } 
/*      */     }
/*      */ 
/*      */ 
/*      */     
/*      */     public int size() {
/* 1013 */       return Reference2BooleanOpenHashMap.this.size;
/*      */     }
/*      */ 
/*      */     
/*      */     public void clear() {
/* 1018 */       Reference2BooleanOpenHashMap.this.clear();
/*      */     }
/*      */ 
/*      */ 
/*      */     
/*      */     public void forEach(Consumer<? super Reference2BooleanMap.Entry<K>> consumer) {
/* 1024 */       if (Reference2BooleanOpenHashMap.this.containsNullKey) consumer.accept(new Reference2BooleanOpenHashMap.MapEntry(Reference2BooleanOpenHashMap.this.n)); 
/* 1025 */       for (int pos = Reference2BooleanOpenHashMap.this.n; pos-- != 0;) { if (Reference2BooleanOpenHashMap.this.key[pos] != null) consumer.accept(new Reference2BooleanOpenHashMap.MapEntry(pos));
/*      */          }
/*      */     
/*      */     }
/*      */     
/*      */     public void fastForEach(Consumer<? super Reference2BooleanMap.Entry<K>> consumer) {
/* 1031 */       Reference2BooleanOpenHashMap<K>.MapEntry entry = new Reference2BooleanOpenHashMap.MapEntry();
/* 1032 */       if (Reference2BooleanOpenHashMap.this.containsNullKey) {
/* 1033 */         entry.index = Reference2BooleanOpenHashMap.this.n;
/* 1034 */         consumer.accept(entry);
/*      */       } 
/* 1036 */       for (int pos = Reference2BooleanOpenHashMap.this.n; pos-- != 0;) { if (Reference2BooleanOpenHashMap.this.key[pos] != null) {
/* 1037 */           entry.index = pos;
/* 1038 */           consumer.accept(entry);
/*      */         }  }
/*      */     
/*      */     }
/*      */   }
/*      */   
/*      */   public Reference2BooleanMap.FastEntrySet<K> reference2BooleanEntrySet() {
/* 1045 */     if (this.entries == null) this.entries = new MapEntrySet(); 
/* 1046 */     return this.entries;
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
/* 1067 */       action.accept(Reference2BooleanOpenHashMap.this.key[index]);
/*      */     }
/*      */ 
/*      */     
/*      */     public K next() {
/* 1072 */       return Reference2BooleanOpenHashMap.this.key[nextEntry()];
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
/* 1083 */       super(pos, max, mustReturnNull, hasSplit);
/*      */     }
/*      */ 
/*      */     
/*      */     public int characteristics() {
/* 1088 */       return this.hasSplit ? 1 : 65;
/*      */     }
/*      */ 
/*      */     
/*      */     final void acceptOnIndex(Consumer<? super K> action, int index) {
/* 1093 */       action.accept(Reference2BooleanOpenHashMap.this.key[index]);
/*      */     }
/*      */ 
/*      */     
/*      */     final KeySpliterator makeForSplit(int pos, int max, boolean mustReturnNull) {
/* 1098 */       return new KeySpliterator(pos, max, mustReturnNull, true);
/*      */     } }
/*      */   
/*      */   private final class KeySet extends AbstractReferenceSet<K> {
/*      */     private KeySet() {}
/*      */     
/*      */     public ObjectIterator<K> iterator() {
/* 1105 */       return new Reference2BooleanOpenHashMap.KeyIterator();
/*      */     }
/*      */ 
/*      */     
/*      */     public ObjectSpliterator<K> spliterator() {
/* 1110 */       return new Reference2BooleanOpenHashMap.KeySpliterator();
/*      */     }
/*      */ 
/*      */ 
/*      */     
/*      */     public void forEach(Consumer<? super K> consumer) {
/* 1116 */       if (Reference2BooleanOpenHashMap.this.containsNullKey) consumer.accept(Reference2BooleanOpenHashMap.this.key[Reference2BooleanOpenHashMap.this.n]); 
/* 1117 */       for (int pos = Reference2BooleanOpenHashMap.this.n; pos-- != 0; ) {
/* 1118 */         K k = Reference2BooleanOpenHashMap.this.key[pos];
/* 1119 */         if (k != null) consumer.accept(k);
/*      */       
/*      */       } 
/*      */     }
/*      */     
/*      */     public int size() {
/* 1125 */       return Reference2BooleanOpenHashMap.this.size;
/*      */     }
/*      */ 
/*      */     
/*      */     public boolean contains(Object k) {
/* 1130 */       return Reference2BooleanOpenHashMap.this.containsKey(k);
/*      */     }
/*      */ 
/*      */     
/*      */     public boolean remove(Object k) {
/* 1135 */       int oldSize = Reference2BooleanOpenHashMap.this.size;
/* 1136 */       Reference2BooleanOpenHashMap.this.removeBoolean(k);
/* 1137 */       return (Reference2BooleanOpenHashMap.this.size != oldSize);
/*      */     }
/*      */ 
/*      */     
/*      */     public void clear() {
/* 1142 */       Reference2BooleanOpenHashMap.this.clear();
/*      */     }
/*      */   }
/*      */ 
/*      */   
/*      */   public ReferenceSet<K> keySet() {
/* 1148 */     if (this.keys == null) this.keys = new KeySet(); 
/* 1149 */     return this.keys;
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
/* 1170 */       action.accept(Reference2BooleanOpenHashMap.this.value[index]);
/*      */     }
/*      */ 
/*      */     
/*      */     public boolean nextBoolean() {
/* 1175 */       return Reference2BooleanOpenHashMap.this.value[nextEntry()];
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
/* 1186 */       super(pos, max, mustReturnNull, hasSplit);
/*      */     }
/*      */ 
/*      */     
/*      */     public int characteristics() {
/* 1191 */       return this.hasSplit ? 256 : 320;
/*      */     }
/*      */ 
/*      */     
/*      */     final void acceptOnIndex(BooleanConsumer action, int index) {
/* 1196 */       action.accept(Reference2BooleanOpenHashMap.this.value[index]);
/*      */     }
/*      */ 
/*      */     
/*      */     final ValueSpliterator makeForSplit(int pos, int max, boolean mustReturnNull) {
/* 1201 */       return new ValueSpliterator(pos, max, mustReturnNull, true);
/*      */     }
/*      */   }
/*      */ 
/*      */   
/*      */   public BooleanCollection values() {
/* 1207 */     if (this.values == null) this.values = (BooleanCollection)new AbstractBooleanCollection()
/*      */         {
/*      */           public BooleanIterator iterator() {
/* 1210 */             return new Reference2BooleanOpenHashMap.ValueIterator();
/*      */           }
/*      */ 
/*      */           
/*      */           public BooleanSpliterator spliterator() {
/* 1215 */             return new Reference2BooleanOpenHashMap.ValueSpliterator();
/*      */           }
/*      */ 
/*      */ 
/*      */           
/*      */           public void forEach(BooleanConsumer consumer) {
/* 1221 */             if (Reference2BooleanOpenHashMap.this.containsNullKey) consumer.accept(Reference2BooleanOpenHashMap.this.value[Reference2BooleanOpenHashMap.this.n]); 
/* 1222 */             for (int pos = Reference2BooleanOpenHashMap.this.n; pos-- != 0;) { if (Reference2BooleanOpenHashMap.this.key[pos] != null) consumer.accept(Reference2BooleanOpenHashMap.this.value[pos]);  }
/*      */           
/*      */           }
/*      */           
/*      */           public int size() {
/* 1227 */             return Reference2BooleanOpenHashMap.this.size;
/*      */           }
/*      */ 
/*      */           
/*      */           public boolean contains(boolean v) {
/* 1232 */             return Reference2BooleanOpenHashMap.this.containsValue(v);
/*      */           }
/*      */ 
/*      */           
/*      */           public void clear() {
/* 1237 */             Reference2BooleanOpenHashMap.this.clear();
/*      */           }
/*      */         }; 
/* 1240 */     return this.values;
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
/* 1257 */     return trim(this.size);
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
/* 1279 */     int l = HashCommon.nextPowerOfTwo((int)Math.ceil((n / this.f)));
/* 1280 */     if (l >= this.n || this.size > HashCommon.maxFill(l, this.f)) return true; 
/*      */     try {
/* 1282 */       rehash(l);
/* 1283 */     } catch (OutOfMemoryError cantDoIt) {
/* 1284 */       return false;
/*      */     } 
/* 1286 */     return true;
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
/* 1301 */     K[] key = this.key;
/* 1302 */     boolean[] value = this.value;
/* 1303 */     int mask = newN - 1;
/* 1304 */     K[] newKey = (K[])new Object[newN + 1];
/* 1305 */     boolean[] newValue = new boolean[newN + 1];
/* 1306 */     int i = this.n;
/* 1307 */     for (int j = realSize(); j-- != 0; ) {
/* 1308 */       while (key[--i] == null); int pos;
/* 1309 */       if (newKey[pos = HashCommon.mix(System.identityHashCode(key[i])) & mask] != null) while (newKey[pos = pos + 1 & mask] != null); 
/* 1310 */       newKey[pos] = key[i];
/* 1311 */       newValue[pos] = value[i];
/*      */     } 
/* 1313 */     newValue[newN] = value[this.n];
/* 1314 */     this.n = newN;
/* 1315 */     this.mask = mask;
/* 1316 */     this.maxFill = HashCommon.maxFill(this.n, this.f);
/* 1317 */     this.key = newKey;
/* 1318 */     this.value = newValue;
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
/*      */   public Reference2BooleanOpenHashMap<K> clone() {
/*      */     Reference2BooleanOpenHashMap<K> c;
/*      */     try {
/* 1335 */       c = (Reference2BooleanOpenHashMap<K>)super.clone();
/* 1336 */     } catch (CloneNotSupportedException cantHappen) {
/* 1337 */       throw new InternalError();
/*      */     } 
/* 1339 */     c.keys = null;
/* 1340 */     c.values = null;
/* 1341 */     c.entries = null;
/* 1342 */     c.containsNullKey = this.containsNullKey;
/* 1343 */     c.key = (K[])this.key.clone();
/* 1344 */     c.value = (boolean[])this.value.clone();
/* 1345 */     return c;
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
/* 1359 */     int h = 0;
/* 1360 */     for (int j = realSize(), i = 0, t = 0; j-- != 0; ) {
/* 1361 */       for (; this.key[i] == null; i++);
/* 1362 */       if (this != this.key[i]) t = System.identityHashCode(this.key[i]); 
/* 1363 */       t ^= this.value[i] ? 1231 : 1237;
/* 1364 */       h += t;
/* 1365 */       i++;
/*      */     } 
/*      */     
/* 1368 */     if (this.containsNullKey) h += this.value[this.n] ? 1231 : 1237; 
/* 1369 */     return h;
/*      */   }
/*      */   
/*      */   private void writeObject(ObjectOutputStream s) throws IOException {
/* 1373 */     K[] key = this.key;
/* 1374 */     boolean[] value = this.value;
/* 1375 */     EntryIterator i = new EntryIterator();
/* 1376 */     s.defaultWriteObject();
/* 1377 */     for (int j = this.size; j-- != 0; ) {
/* 1378 */       int e = i.nextEntry();
/* 1379 */       s.writeObject(key[e]);
/* 1380 */       s.writeBoolean(value[e]);
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   private void readObject(ObjectInputStream s) throws IOException, ClassNotFoundException {
/* 1386 */     s.defaultReadObject();
/* 1387 */     this.n = HashCommon.arraySize(this.size, this.f);
/* 1388 */     this.maxFill = HashCommon.maxFill(this.n, this.f);
/* 1389 */     this.mask = this.n - 1;
/* 1390 */     K[] key = this.key = (K[])new Object[this.n + 1];
/* 1391 */     boolean[] value = this.value = new boolean[this.n + 1];
/*      */ 
/*      */     
/* 1394 */     for (int i = this.size; i-- != 0; ) {
/* 1395 */       int pos; K k = (K)s.readObject();
/* 1396 */       boolean v = s.readBoolean();
/* 1397 */       if (k == null) {
/* 1398 */         pos = this.n;
/* 1399 */         this.containsNullKey = true;
/*      */       } else {
/* 1401 */         pos = HashCommon.mix(System.identityHashCode(k)) & this.mask;
/* 1402 */         for (; key[pos] != null; pos = pos + 1 & this.mask);
/*      */       } 
/* 1404 */       key[pos] = k;
/* 1405 */       value[pos] = v;
/*      */     } 
/*      */   }
/*      */   
/*      */   private void checkTable() {}
/*      */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\i\\unimi\dsi\fastutil\objects\Reference2BooleanOpenHashMap.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */