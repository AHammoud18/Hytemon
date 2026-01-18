/*      */ package it.unimi.dsi.fastutil.objects;
/*      */ 
/*      */ import it.unimi.dsi.fastutil.Hash;
/*      */ import it.unimi.dsi.fastutil.HashCommon;
/*      */ import it.unimi.dsi.fastutil.ints.AbstractIntCollection;
/*      */ import it.unimi.dsi.fastutil.ints.IntCollection;
/*      */ import it.unimi.dsi.fastutil.ints.IntIterator;
/*      */ import it.unimi.dsi.fastutil.ints.IntSpliterator;
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
/*      */ import java.util.function.ToIntFunction;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ public class Object2IntOpenHashMap<K>
/*      */   extends AbstractObject2IntMap<K>
/*      */   implements Serializable, Cloneable, Hash
/*      */ {
/*      */   private static final long serialVersionUID = 0L;
/*      */   private static final boolean ASSERTS = false;
/*      */   protected transient K[] key;
/*      */   protected transient int[] value;
/*      */   protected transient int mask;
/*      */   protected transient boolean containsNullKey;
/*      */   protected transient int n;
/*      */   protected transient int maxFill;
/*      */   protected final transient int minN;
/*      */   protected int size;
/*      */   protected final float f;
/*      */   protected transient Object2IntMap.FastEntrySet<K> entries;
/*      */   protected transient ObjectSet<K> keys;
/*      */   protected transient IntCollection values;
/*      */   
/*      */   public Object2IntOpenHashMap(int expected, float f) {
/*   95 */     if (f <= 0.0F || f >= 1.0F) throw new IllegalArgumentException("Load factor must be greater than 0 and smaller than 1"); 
/*   96 */     if (expected < 0) throw new IllegalArgumentException("The expected number of elements must be nonnegative"); 
/*   97 */     this.f = f;
/*   98 */     this.minN = this.n = HashCommon.arraySize(expected, f);
/*   99 */     this.mask = this.n - 1;
/*  100 */     this.maxFill = HashCommon.maxFill(this.n, f);
/*  101 */     this.key = (K[])new Object[this.n + 1];
/*  102 */     this.value = new int[this.n + 1];
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Object2IntOpenHashMap(int expected) {
/*  111 */     this(expected, 0.75F);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Object2IntOpenHashMap() {
/*  119 */     this(16, 0.75F);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Object2IntOpenHashMap(Map<? extends K, ? extends Integer> m, float f) {
/*  129 */     this(m.size(), f);
/*  130 */     putAll(m);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Object2IntOpenHashMap(Map<? extends K, ? extends Integer> m) {
/*  139 */     this(m, 0.75F);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Object2IntOpenHashMap(Object2IntMap<K> m, float f) {
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
/*      */   public Object2IntOpenHashMap(Object2IntMap<K> m) {
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
/*      */   public Object2IntOpenHashMap(K[] k, int[] v, float f) {
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
/*      */   public Object2IntOpenHashMap(K[] k, int[] v) {
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
/*      */   private int removeEntry(int pos) {
/*  210 */     int oldValue = this.value[pos];
/*  211 */     this.size--;
/*  212 */     shiftKeys(pos);
/*  213 */     if (this.n > this.minN && this.size < this.maxFill / 4 && this.n > 16) rehash(this.n / 2); 
/*  214 */     return oldValue;
/*      */   }
/*      */   
/*      */   private int removeNullEntry() {
/*  218 */     this.containsNullKey = false;
/*  219 */     this.key[this.n] = null;
/*  220 */     int oldValue = this.value[this.n];
/*  221 */     this.size--;
/*  222 */     if (this.n > this.minN && this.size < this.maxFill / 4 && this.n > 16) rehash(this.n / 2); 
/*  223 */     return oldValue;
/*      */   }
/*      */ 
/*      */   
/*      */   public void putAll(Map<? extends K, ? extends Integer> m) {
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
/*  241 */     if ((curr = key[pos = HashCommon.mix(k.hashCode()) & this.mask]) == null) return -(pos + 1); 
/*  242 */     if (k.equals(curr)) return pos;
/*      */     
/*      */     while (true) {
/*  245 */       if ((curr = key[pos = pos + 1 & this.mask]) == null) return -(pos + 1); 
/*  246 */       if (k.equals(curr)) return pos; 
/*      */     } 
/*      */   }
/*      */   
/*      */   private void insert(int pos, K k, int v) {
/*  251 */     if (pos == this.n) this.containsNullKey = true; 
/*  252 */     this.key[pos] = k;
/*  253 */     this.value[pos] = v;
/*  254 */     if (this.size++ >= this.maxFill) rehash(HashCommon.arraySize(this.size + 1, this.f));
/*      */   
/*      */   }
/*      */ 
/*      */   
/*      */   public int put(K k, int v) {
/*  260 */     int pos = find(k);
/*  261 */     if (pos < 0) {
/*  262 */       insert(-pos - 1, k, v);
/*  263 */       return this.defRetValue;
/*      */     } 
/*  265 */     int oldValue = this.value[pos];
/*  266 */     this.value[pos] = v;
/*  267 */     return oldValue;
/*      */   }
/*      */   
/*      */   private int addToValue(int pos, int incr) {
/*  271 */     int oldValue = this.value[pos];
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
/*      */   public int addTo(K k, int incr) {
/*      */     int pos;
/*  291 */     if (k == null) {
/*  292 */       if (this.containsNullKey) return addToValue(this.n, incr); 
/*  293 */       pos = this.n;
/*  294 */       this.containsNullKey = true;
/*      */     } else {
/*      */       
/*  297 */       K[] key = this.key;
/*      */       K curr;
/*  299 */       if ((curr = key[pos = HashCommon.mix(k.hashCode()) & this.mask]) != null) {
/*  300 */         if (curr.equals(k)) return addToValue(pos, incr); 
/*  301 */         while ((curr = key[pos = pos + 1 & this.mask]) != null) { if (curr.equals(k)) return addToValue(pos, incr);  }
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
/*  329 */         int slot = HashCommon.mix(curr.hashCode()) & this.mask;
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
/*      */   public int removeInt(Object k) {
/*  341 */     if (k == null) {
/*  342 */       if (this.containsNullKey) return removeNullEntry(); 
/*  343 */       return this.defRetValue;
/*      */     } 
/*      */     
/*  346 */     K[] key = this.key;
/*      */     K curr;
/*      */     int pos;
/*  349 */     if ((curr = key[pos = HashCommon.mix(k.hashCode()) & this.mask]) == null) return this.defRetValue; 
/*  350 */     if (k.equals(curr)) return removeEntry(pos); 
/*      */     while (true) {
/*  352 */       if ((curr = key[pos = pos + 1 & this.mask]) == null) return this.defRetValue; 
/*  353 */       if (k.equals(curr)) return removeEntry(pos);
/*      */     
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   public int getInt(Object k) {
/*  360 */     if (k == null) return this.containsNullKey ? this.value[this.n] : this.defRetValue;
/*      */     
/*  362 */     K[] key = this.key;
/*      */     K curr;
/*      */     int pos;
/*  365 */     if ((curr = key[pos = HashCommon.mix(k.hashCode()) & this.mask]) == null) return this.defRetValue; 
/*  366 */     if (k.equals(curr)) return this.value[pos];
/*      */     
/*      */     while (true) {
/*  369 */       if ((curr = key[pos = pos + 1 & this.mask]) == null) return this.defRetValue; 
/*  370 */       if (k.equals(curr)) return this.value[pos];
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
/*  382 */     if ((curr = key[pos = HashCommon.mix(k.hashCode()) & this.mask]) == null) return false; 
/*  383 */     if (k.equals(curr)) return true;
/*      */     
/*      */     while (true) {
/*  386 */       if ((curr = key[pos = pos + 1 & this.mask]) == null) return false; 
/*  387 */       if (k.equals(curr)) return true;
/*      */     
/*      */     } 
/*      */   }
/*      */   
/*      */   public boolean containsValue(int v) {
/*  393 */     int[] value = this.value;
/*  394 */     K[] key = this.key;
/*  395 */     if (this.containsNullKey && value[this.n] == v) return true; 
/*  396 */     for (int i = this.n; i-- != 0;) { if (key[i] != null && value[i] == v) return true;  }
/*  397 */      return false;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public int getOrDefault(Object k, int defaultValue) {
/*  404 */     if (k == null) return this.containsNullKey ? this.value[this.n] : defaultValue;
/*      */     
/*  406 */     K[] key = this.key;
/*      */     K curr;
/*      */     int pos;
/*  409 */     if ((curr = key[pos = HashCommon.mix(k.hashCode()) & this.mask]) == null) return defaultValue; 
/*  410 */     if (k.equals(curr)) return this.value[pos];
/*      */     
/*      */     while (true) {
/*  413 */       if ((curr = key[pos = pos + 1 & this.mask]) == null) return defaultValue; 
/*  414 */       if (k.equals(curr)) return this.value[pos];
/*      */     
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   public int putIfAbsent(K k, int v) {
/*  421 */     int pos = find(k);
/*  422 */     if (pos >= 0) return this.value[pos]; 
/*  423 */     insert(-pos - 1, k, v);
/*  424 */     return this.defRetValue;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean remove(Object k, int v) {
/*  431 */     if (k == null) {
/*  432 */       if (this.containsNullKey && v == this.value[this.n]) {
/*  433 */         removeNullEntry();
/*  434 */         return true;
/*      */       } 
/*  436 */       return false;
/*      */     } 
/*      */     
/*  439 */     K[] key = this.key;
/*      */     K curr;
/*      */     int pos;
/*  442 */     if ((curr = key[pos = HashCommon.mix(k.hashCode()) & this.mask]) == null) return false; 
/*  443 */     if (k.equals(curr) && v == this.value[pos]) {
/*  444 */       removeEntry(pos);
/*  445 */       return true;
/*      */     } 
/*      */     while (true) {
/*  448 */       if ((curr = key[pos = pos + 1 & this.mask]) == null) return false; 
/*  449 */       if (k.equals(curr) && v == this.value[pos]) {
/*  450 */         removeEntry(pos);
/*  451 */         return true;
/*      */       } 
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean replace(K k, int oldValue, int v) {
/*  459 */     int pos = find(k);
/*  460 */     if (pos < 0 || oldValue != this.value[pos]) return false; 
/*  461 */     this.value[pos] = v;
/*  462 */     return true;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public int replace(K k, int v) {
/*  468 */     int pos = find(k);
/*  469 */     if (pos < 0) return this.defRetValue; 
/*  470 */     int oldValue = this.value[pos];
/*  471 */     this.value[pos] = v;
/*  472 */     return oldValue;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public int computeIfAbsent(K k, ToIntFunction<? super K> mappingFunction) {
/*  478 */     Objects.requireNonNull(mappingFunction);
/*  479 */     int pos = find(k);
/*  480 */     if (pos >= 0) return this.value[pos]; 
/*  481 */     int newValue = mappingFunction.applyAsInt(k);
/*  482 */     insert(-pos - 1, k, newValue);
/*  483 */     return newValue;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public int computeIfAbsent(K key, Object2IntFunction<? super K> mappingFunction) {
/*  489 */     Objects.requireNonNull(mappingFunction);
/*  490 */     int pos = find(key);
/*  491 */     if (pos >= 0) return this.value[pos]; 
/*  492 */     if (!mappingFunction.containsKey(key)) return this.defRetValue; 
/*  493 */     int newValue = mappingFunction.getInt(key);
/*  494 */     insert(-pos - 1, key, newValue);
/*  495 */     return newValue;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public int computeIntIfPresent(K k, BiFunction<? super K, ? super Integer, ? extends Integer> remappingFunction) {
/*  501 */     Objects.requireNonNull(remappingFunction);
/*  502 */     int pos = find(k);
/*  503 */     if (pos < 0) return this.defRetValue; 
/*  504 */     Integer newValue = remappingFunction.apply(k, Integer.valueOf(this.value[pos]));
/*  505 */     if (newValue == null) {
/*  506 */       if (k == null) { removeNullEntry(); }
/*  507 */       else { removeEntry(pos); }
/*  508 */        return this.defRetValue;
/*      */     } 
/*  510 */     this.value[pos] = newValue.intValue(); return newValue.intValue();
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public int computeInt(K k, BiFunction<? super K, ? super Integer, ? extends Integer> remappingFunction) {
/*  516 */     Objects.requireNonNull(remappingFunction);
/*  517 */     int pos = find(k);
/*  518 */     Integer newValue = remappingFunction.apply(k, (pos >= 0) ? Integer.valueOf(this.value[pos]) : null);
/*  519 */     if (newValue == null) {
/*  520 */       if (pos >= 0)
/*  521 */         if (k == null) { removeNullEntry(); }
/*  522 */         else { removeEntry(pos); }
/*      */          
/*  524 */       return this.defRetValue;
/*      */     } 
/*  526 */     int newVal = newValue.intValue();
/*  527 */     if (pos < 0) {
/*  528 */       insert(-pos - 1, k, newVal);
/*  529 */       return newVal;
/*      */     } 
/*  531 */     this.value[pos] = newVal; return newVal;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public int merge(K k, int v, BiFunction<? super Integer, ? super Integer, ? extends Integer> remappingFunction) {
/*  537 */     Objects.requireNonNull(remappingFunction);
/*      */     
/*  539 */     int pos = find(k);
/*  540 */     if (pos < 0) {
/*  541 */       if (pos < 0) { insert(-pos - 1, k, v); }
/*  542 */       else { this.value[pos] = v; }
/*  543 */        return v;
/*      */     } 
/*  545 */     Integer newValue = remappingFunction.apply(Integer.valueOf(this.value[pos]), Integer.valueOf(v));
/*  546 */     if (newValue == null) {
/*  547 */       if (k == null) { removeNullEntry(); }
/*  548 */       else { removeEntry(pos); }
/*  549 */        return this.defRetValue;
/*      */     } 
/*  551 */     this.value[pos] = newValue.intValue(); return newValue.intValue();
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
/*      */     implements Object2IntMap.Entry<K>, Map.Entry<K, Integer>, ObjectIntPair<K>
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
/*  596 */       return Object2IntOpenHashMap.this.key[this.index];
/*      */     }
/*      */ 
/*      */     
/*      */     public K left() {
/*  601 */       return Object2IntOpenHashMap.this.key[this.index];
/*      */     }
/*      */ 
/*      */     
/*      */     public int getIntValue() {
/*  606 */       return Object2IntOpenHashMap.this.value[this.index];
/*      */     }
/*      */ 
/*      */     
/*      */     public int rightInt() {
/*  611 */       return Object2IntOpenHashMap.this.value[this.index];
/*      */     }
/*      */ 
/*      */     
/*      */     public int setValue(int v) {
/*  616 */       int oldValue = Object2IntOpenHashMap.this.value[this.index];
/*  617 */       Object2IntOpenHashMap.this.value[this.index] = v;
/*  618 */       return oldValue;
/*      */     }
/*      */ 
/*      */     
/*      */     public ObjectIntPair<K> right(int v) {
/*  623 */       Object2IntOpenHashMap.this.value[this.index] = v;
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
/*      */     public Integer getValue() {
/*  635 */       return Integer.valueOf(Object2IntOpenHashMap.this.value[this.index]);
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
/*  646 */       return Integer.valueOf(setValue(v.intValue()));
/*      */     }
/*      */ 
/*      */ 
/*      */     
/*      */     public boolean equals(Object o) {
/*  652 */       if (!(o instanceof Map.Entry)) return false; 
/*  653 */       Map.Entry<K, Integer> e = (Map.Entry<K, Integer>)o;
/*  654 */       return (Objects.equals(Object2IntOpenHashMap.this.key[this.index], e.getKey()) && Object2IntOpenHashMap.this.value[this.index] == ((Integer)e.getValue()).intValue());
/*      */     }
/*      */ 
/*      */     
/*      */     public int hashCode() {
/*  659 */       return ((Object2IntOpenHashMap.this.key[this.index] == null) ? 0 : Object2IntOpenHashMap.this.key[this.index].hashCode()) ^ Object2IntOpenHashMap.this.value[this.index];
/*      */     }
/*      */ 
/*      */     
/*      */     public String toString() {
/*  664 */       return (new StringBuilder()).append(Object2IntOpenHashMap.this.key[this.index]).append("=>").append(Object2IntOpenHashMap.this.value[this.index]).toString();
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
/*  675 */     int pos = Object2IntOpenHashMap.this.n;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  681 */     int last = -1;
/*      */     
/*  683 */     int c = Object2IntOpenHashMap.this.size;
/*      */     
/*  685 */     boolean mustReturnNullKey = Object2IntOpenHashMap.this.containsNullKey;
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     ObjectArrayList<K> wrapped;
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
/*  704 */         return this.last = Object2IntOpenHashMap.this.n;
/*      */       } 
/*  706 */       K[] key = Object2IntOpenHashMap.this.key;
/*      */       while (true) {
/*  708 */         if (--this.pos < 0) {
/*      */           
/*  710 */           this.last = Integer.MIN_VALUE;
/*  711 */           K k = this.wrapped.get(-this.pos - 1);
/*  712 */           int p = HashCommon.mix(k.hashCode()) & Object2IntOpenHashMap.this.mask;
/*  713 */           for (; !k.equals(key[p]); p = p + 1 & Object2IntOpenHashMap.this.mask);
/*  714 */           return p;
/*      */         } 
/*  716 */         if (key[this.pos] != null) return this.last = this.pos; 
/*      */       } 
/*      */     }
/*      */     
/*      */     public void forEachRemaining(ConsumerType action) {
/*  721 */       if (this.mustReturnNullKey) {
/*  722 */         this.mustReturnNullKey = false;
/*  723 */         acceptOnIndex(action, this.last = Object2IntOpenHashMap.this.n);
/*  724 */         this.c--;
/*      */       } 
/*  726 */       K[] key = Object2IntOpenHashMap.this.key;
/*  727 */       while (this.c != 0) {
/*  728 */         if (--this.pos < 0) {
/*      */           
/*  730 */           this.last = Integer.MIN_VALUE;
/*  731 */           K k = this.wrapped.get(-this.pos - 1);
/*  732 */           int p = HashCommon.mix(k.hashCode()) & Object2IntOpenHashMap.this.mask;
/*  733 */           for (; !k.equals(key[p]); p = p + 1 & Object2IntOpenHashMap.this.mask);
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
/*  753 */       K[] key = Object2IntOpenHashMap.this.key; while (true) {
/*      */         K curr; int last;
/*  755 */         pos = (last = pos) + 1 & Object2IntOpenHashMap.this.mask;
/*      */         while (true) {
/*  757 */           if ((curr = key[pos]) == null) {
/*  758 */             key[last] = null;
/*      */             return;
/*      */           } 
/*  761 */           int slot = HashCommon.mix(curr.hashCode()) & Object2IntOpenHashMap.this.mask;
/*  762 */           if ((last <= pos) ? (last >= slot || slot > pos) : (last >= slot && slot > pos))
/*  763 */             break;  pos = pos + 1 & Object2IntOpenHashMap.this.mask;
/*      */         } 
/*  765 */         if (pos < last) {
/*  766 */           if (this.wrapped == null) this.wrapped = new ObjectArrayList<>(2); 
/*  767 */           this.wrapped.add(key[pos]);
/*      */         } 
/*  769 */         key[last] = curr;
/*  770 */         Object2IntOpenHashMap.this.value[last] = Object2IntOpenHashMap.this.value[pos];
/*      */       } 
/*      */     }
/*      */     
/*      */     public void remove() {
/*  775 */       if (this.last == -1) throw new IllegalStateException(); 
/*  776 */       if (this.last == Object2IntOpenHashMap.this.n)
/*  777 */       { Object2IntOpenHashMap.this.containsNullKey = false;
/*  778 */         Object2IntOpenHashMap.this.key[Object2IntOpenHashMap.this.n] = null; }
/*  779 */       else if (this.pos >= 0) { shiftKeys(this.last); }
/*      */       else
/*      */       
/*  782 */       { Object2IntOpenHashMap.this.removeInt(this.wrapped.set(-this.pos - 1, null));
/*  783 */         this.last = -1;
/*      */         return; }
/*      */       
/*  786 */       Object2IntOpenHashMap.this.size--;
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
/*      */   private final class EntryIterator extends MapIterator<Consumer<? super Object2IntMap.Entry<K>>> implements ObjectIterator<Object2IntMap.Entry<K>> { public Object2IntOpenHashMap<K>.MapEntry next() {
/*  803 */       return this.entry = new Object2IntOpenHashMap.MapEntry(nextEntry());
/*      */     }
/*      */     private Object2IntOpenHashMap<K>.MapEntry entry;
/*      */     private EntryIterator() {}
/*      */     
/*      */     final void acceptOnIndex(Consumer<? super Object2IntMap.Entry<K>> action, int index) {
/*  809 */       action.accept(this.entry = new Object2IntOpenHashMap.MapEntry(index));
/*      */     }
/*      */ 
/*      */     
/*      */     public void remove() {
/*  814 */       super.remove();
/*  815 */       this.entry.index = -1;
/*      */     } }
/*      */   
/*      */   private final class FastEntryIterator extends MapIterator<Consumer<? super Object2IntMap.Entry<K>>> implements ObjectIterator<Object2IntMap.Entry<K>> {
/*      */     private FastEntryIterator() {
/*  820 */       this.entry = new Object2IntOpenHashMap.MapEntry();
/*      */     }
/*      */     private final Object2IntOpenHashMap<K>.MapEntry entry;
/*      */     public Object2IntOpenHashMap<K>.MapEntry next() {
/*  824 */       this.entry.index = nextEntry();
/*  825 */       return this.entry;
/*      */     }
/*      */ 
/*      */ 
/*      */     
/*      */     final void acceptOnIndex(Consumer<? super Object2IntMap.Entry<K>> action, int index) {
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
/*  843 */     int max = Object2IntOpenHashMap.this.n;
/*      */     
/*  845 */     int c = 0;
/*      */     
/*  847 */     boolean mustReturnNull = Object2IntOpenHashMap.this.containsNullKey;
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
/*  868 */         acceptOnIndex(action, Object2IntOpenHashMap.this.n);
/*  869 */         return true;
/*      */       } 
/*  871 */       K[] key = Object2IntOpenHashMap.this.key;
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
/*  887 */         acceptOnIndex(action, Object2IntOpenHashMap.this.n);
/*      */       } 
/*  889 */       K[] key = Object2IntOpenHashMap.this.key;
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
/*  902 */         return (Object2IntOpenHashMap.this.size - this.c);
/*      */       }
/*      */ 
/*      */ 
/*      */       
/*  907 */       return Math.min((Object2IntOpenHashMap.this.size - this.c), (long)(Object2IntOpenHashMap.this.realSize() / Object2IntOpenHashMap.this.n * (this.max - this.pos)) + (this.mustReturnNull ? 1L : 0L));
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
/*  937 */       K[] key = Object2IntOpenHashMap.this.key;
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
/*      */     extends MapSpliterator<Consumer<? super Object2IntMap.Entry<K>>, EntrySpliterator> implements ObjectSpliterator<Object2IntMap.Entry<K>> {
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
/*      */     final void acceptOnIndex(Consumer<? super Object2IntMap.Entry<K>> action, int index) {
/*  965 */       action.accept(new Object2IntOpenHashMap.MapEntry(index));
/*      */     }
/*      */ 
/*      */     
/*      */     final EntrySpliterator makeForSplit(int pos, int max, boolean mustReturnNull) {
/*  970 */       return new EntrySpliterator(pos, max, mustReturnNull, true);
/*      */     } }
/*      */   
/*      */   private final class MapEntrySet extends AbstractObjectSet<Object2IntMap.Entry<K>> implements Object2IntMap.FastEntrySet<K> {
/*      */     private MapEntrySet() {}
/*      */     
/*      */     public ObjectIterator<Object2IntMap.Entry<K>> iterator() {
/*  977 */       return new Object2IntOpenHashMap.EntryIterator();
/*      */     }
/*      */ 
/*      */     
/*      */     public ObjectIterator<Object2IntMap.Entry<K>> fastIterator() {
/*  982 */       return new Object2IntOpenHashMap.FastEntryIterator();
/*      */     }
/*      */ 
/*      */     
/*      */     public ObjectSpliterator<Object2IntMap.Entry<K>> spliterator() {
/*  987 */       return new Object2IntOpenHashMap.EntrySpliterator();
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public boolean contains(Object o) {
/*  994 */       if (!(o instanceof Map.Entry)) return false; 
/*  995 */       Map.Entry<?, ?> e = (Map.Entry<?, ?>)o;
/*  996 */       if (e.getValue() == null || !(e.getValue() instanceof Integer)) return false; 
/*  997 */       K k = (K)e.getKey();
/*  998 */       int v = ((Integer)e.getValue()).intValue();
/*  999 */       if (k == null) return (Object2IntOpenHashMap.this.containsNullKey && Object2IntOpenHashMap.this.value[Object2IntOpenHashMap.this.n] == v);
/*      */       
/* 1001 */       K[] key = Object2IntOpenHashMap.this.key;
/*      */       K curr;
/*      */       int pos;
/* 1004 */       if ((curr = key[pos = HashCommon.mix(k.hashCode()) & Object2IntOpenHashMap.this.mask]) == null) return false; 
/* 1005 */       if (k.equals(curr)) return (Object2IntOpenHashMap.this.value[pos] == v);
/*      */       
/*      */       while (true) {
/* 1008 */         if ((curr = key[pos = pos + 1 & Object2IntOpenHashMap.this.mask]) == null) return false; 
/* 1009 */         if (k.equals(curr)) return (Object2IntOpenHashMap.this.value[pos] == v);
/*      */       
/*      */       } 
/*      */     }
/*      */ 
/*      */     
/*      */     public boolean remove(Object o) {
/* 1016 */       if (!(o instanceof Map.Entry)) return false; 
/* 1017 */       Map.Entry<?, ?> e = (Map.Entry<?, ?>)o;
/* 1018 */       if (e.getValue() == null || !(e.getValue() instanceof Integer)) return false; 
/* 1019 */       K k = (K)e.getKey();
/* 1020 */       int v = ((Integer)e.getValue()).intValue();
/* 1021 */       if (k == null) {
/* 1022 */         if (Object2IntOpenHashMap.this.containsNullKey && Object2IntOpenHashMap.this.value[Object2IntOpenHashMap.this.n] == v) {
/* 1023 */           Object2IntOpenHashMap.this.removeNullEntry();
/* 1024 */           return true;
/*      */         } 
/* 1026 */         return false;
/*      */       } 
/*      */       
/* 1029 */       K[] key = Object2IntOpenHashMap.this.key;
/*      */       K curr;
/*      */       int pos;
/* 1032 */       if ((curr = key[pos = HashCommon.mix(k.hashCode()) & Object2IntOpenHashMap.this.mask]) == null) return false; 
/* 1033 */       if (curr.equals(k)) {
/* 1034 */         if (Object2IntOpenHashMap.this.value[pos] == v) {
/* 1035 */           Object2IntOpenHashMap.this.removeEntry(pos);
/* 1036 */           return true;
/*      */         } 
/* 1038 */         return false;
/*      */       } 
/*      */       while (true) {
/* 1041 */         if ((curr = key[pos = pos + 1 & Object2IntOpenHashMap.this.mask]) == null) return false; 
/* 1042 */         if (curr.equals(k) && 
/* 1043 */           Object2IntOpenHashMap.this.value[pos] == v) {
/* 1044 */           Object2IntOpenHashMap.this.removeEntry(pos);
/* 1045 */           return true;
/*      */         } 
/*      */       } 
/*      */     }
/*      */ 
/*      */ 
/*      */     
/*      */     public int size() {
/* 1053 */       return Object2IntOpenHashMap.this.size;
/*      */     }
/*      */ 
/*      */     
/*      */     public void clear() {
/* 1058 */       Object2IntOpenHashMap.this.clear();
/*      */     }
/*      */ 
/*      */ 
/*      */     
/*      */     public void forEach(Consumer<? super Object2IntMap.Entry<K>> consumer) {
/* 1064 */       if (Object2IntOpenHashMap.this.containsNullKey) consumer.accept(new Object2IntOpenHashMap.MapEntry(Object2IntOpenHashMap.this.n)); 
/* 1065 */       for (int pos = Object2IntOpenHashMap.this.n; pos-- != 0;) { if (Object2IntOpenHashMap.this.key[pos] != null) consumer.accept(new Object2IntOpenHashMap.MapEntry(pos));
/*      */          }
/*      */     
/*      */     }
/*      */     
/*      */     public void fastForEach(Consumer<? super Object2IntMap.Entry<K>> consumer) {
/* 1071 */       Object2IntOpenHashMap<K>.MapEntry entry = new Object2IntOpenHashMap.MapEntry();
/* 1072 */       if (Object2IntOpenHashMap.this.containsNullKey) {
/* 1073 */         entry.index = Object2IntOpenHashMap.this.n;
/* 1074 */         consumer.accept(entry);
/*      */       } 
/* 1076 */       for (int pos = Object2IntOpenHashMap.this.n; pos-- != 0;) { if (Object2IntOpenHashMap.this.key[pos] != null) {
/* 1077 */           entry.index = pos;
/* 1078 */           consumer.accept(entry);
/*      */         }  }
/*      */     
/*      */     }
/*      */   }
/*      */   
/*      */   public Object2IntMap.FastEntrySet<K> object2IntEntrySet() {
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
/* 1107 */       action.accept(Object2IntOpenHashMap.this.key[index]);
/*      */     }
/*      */ 
/*      */     
/*      */     public K next() {
/* 1112 */       return Object2IntOpenHashMap.this.key[nextEntry()];
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
/* 1133 */       action.accept(Object2IntOpenHashMap.this.key[index]);
/*      */     }
/*      */ 
/*      */     
/*      */     final KeySpliterator makeForSplit(int pos, int max, boolean mustReturnNull) {
/* 1138 */       return new KeySpliterator(pos, max, mustReturnNull, true);
/*      */     } }
/*      */   
/*      */   private final class KeySet extends AbstractObjectSet<K> {
/*      */     private KeySet() {}
/*      */     
/*      */     public ObjectIterator<K> iterator() {
/* 1145 */       return new Object2IntOpenHashMap.KeyIterator();
/*      */     }
/*      */ 
/*      */     
/*      */     public ObjectSpliterator<K> spliterator() {
/* 1150 */       return new Object2IntOpenHashMap.KeySpliterator();
/*      */     }
/*      */ 
/*      */ 
/*      */     
/*      */     public void forEach(Consumer<? super K> consumer) {
/* 1156 */       if (Object2IntOpenHashMap.this.containsNullKey) consumer.accept(Object2IntOpenHashMap.this.key[Object2IntOpenHashMap.this.n]); 
/* 1157 */       for (int pos = Object2IntOpenHashMap.this.n; pos-- != 0; ) {
/* 1158 */         K k = Object2IntOpenHashMap.this.key[pos];
/* 1159 */         if (k != null) consumer.accept(k);
/*      */       
/*      */       } 
/*      */     }
/*      */     
/*      */     public int size() {
/* 1165 */       return Object2IntOpenHashMap.this.size;
/*      */     }
/*      */ 
/*      */     
/*      */     public boolean contains(Object k) {
/* 1170 */       return Object2IntOpenHashMap.this.containsKey(k);
/*      */     }
/*      */ 
/*      */     
/*      */     public boolean remove(Object k) {
/* 1175 */       int oldSize = Object2IntOpenHashMap.this.size;
/* 1176 */       Object2IntOpenHashMap.this.removeInt(k);
/* 1177 */       return (Object2IntOpenHashMap.this.size != oldSize);
/*      */     }
/*      */ 
/*      */     
/*      */     public void clear() {
/* 1182 */       Object2IntOpenHashMap.this.clear();
/*      */     }
/*      */   }
/*      */ 
/*      */   
/*      */   public ObjectSet<K> keySet() {
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
/*      */     extends MapIterator<IntConsumer>
/*      */     implements IntIterator
/*      */   {
/*      */     final void acceptOnIndex(IntConsumer action, int index) {
/* 1210 */       action.accept(Object2IntOpenHashMap.this.value[index]);
/*      */     }
/*      */ 
/*      */     
/*      */     public int nextInt() {
/* 1215 */       return Object2IntOpenHashMap.this.value[nextEntry()];
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
/* 1226 */       super(pos, max, mustReturnNull, hasSplit);
/*      */     }
/*      */ 
/*      */     
/*      */     public int characteristics() {
/* 1231 */       return this.hasSplit ? 256 : 320;
/*      */     }
/*      */ 
/*      */     
/*      */     final void acceptOnIndex(IntConsumer action, int index) {
/* 1236 */       action.accept(Object2IntOpenHashMap.this.value[index]);
/*      */     }
/*      */ 
/*      */     
/*      */     final ValueSpliterator makeForSplit(int pos, int max, boolean mustReturnNull) {
/* 1241 */       return new ValueSpliterator(pos, max, mustReturnNull, true);
/*      */     }
/*      */   }
/*      */ 
/*      */   
/*      */   public IntCollection values() {
/* 1247 */     if (this.values == null) this.values = (IntCollection)new AbstractIntCollection()
/*      */         {
/*      */           public IntIterator iterator() {
/* 1250 */             return new Object2IntOpenHashMap.ValueIterator();
/*      */           }
/*      */ 
/*      */           
/*      */           public IntSpliterator spliterator() {
/* 1255 */             return new Object2IntOpenHashMap.ValueSpliterator();
/*      */           }
/*      */ 
/*      */ 
/*      */           
/*      */           public void forEach(IntConsumer consumer) {
/* 1261 */             if (Object2IntOpenHashMap.this.containsNullKey) consumer.accept(Object2IntOpenHashMap.this.value[Object2IntOpenHashMap.this.n]); 
/* 1262 */             for (int pos = Object2IntOpenHashMap.this.n; pos-- != 0;) { if (Object2IntOpenHashMap.this.key[pos] != null) consumer.accept(Object2IntOpenHashMap.this.value[pos]);  }
/*      */           
/*      */           }
/*      */           
/*      */           public int size() {
/* 1267 */             return Object2IntOpenHashMap.this.size;
/*      */           }
/*      */ 
/*      */           
/*      */           public boolean contains(int v) {
/* 1272 */             return Object2IntOpenHashMap.this.containsValue(v);
/*      */           }
/*      */ 
/*      */           
/*      */           public void clear() {
/* 1277 */             Object2IntOpenHashMap.this.clear();
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
/* 1342 */     int[] value = this.value;
/* 1343 */     int mask = newN - 1;
/* 1344 */     K[] newKey = (K[])new Object[newN + 1];
/* 1345 */     int[] newValue = new int[newN + 1];
/* 1346 */     int i = this.n;
/* 1347 */     for (int j = realSize(); j-- != 0; ) {
/* 1348 */       while (key[--i] == null); int pos;
/* 1349 */       if (newKey[pos = HashCommon.mix(key[i].hashCode()) & mask] != null) while (newKey[pos = pos + 1 & mask] != null); 
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
/*      */   public Object2IntOpenHashMap<K> clone() {
/*      */     Object2IntOpenHashMap<K> c;
/*      */     try {
/* 1375 */       c = (Object2IntOpenHashMap<K>)super.clone();
/* 1376 */     } catch (CloneNotSupportedException cantHappen) {
/* 1377 */       throw new InternalError();
/*      */     } 
/* 1379 */     c.keys = null;
/* 1380 */     c.values = null;
/* 1381 */     c.entries = null;
/* 1382 */     c.containsNullKey = this.containsNullKey;
/* 1383 */     c.key = (K[])this.key.clone();
/* 1384 */     c.value = (int[])this.value.clone();
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
/* 1402 */       if (this != this.key[i]) t = this.key[i].hashCode(); 
/* 1403 */       t ^= this.value[i];
/* 1404 */       h += t;
/* 1405 */       i++;
/*      */     } 
/*      */     
/* 1408 */     if (this.containsNullKey) h += this.value[this.n]; 
/* 1409 */     return h;
/*      */   }
/*      */   
/*      */   private void writeObject(ObjectOutputStream s) throws IOException {
/* 1413 */     K[] key = this.key;
/* 1414 */     int[] value = this.value;
/* 1415 */     EntryIterator i = new EntryIterator();
/* 1416 */     s.defaultWriteObject();
/* 1417 */     for (int j = this.size; j-- != 0; ) {
/* 1418 */       int e = i.nextEntry();
/* 1419 */       s.writeObject(key[e]);
/* 1420 */       s.writeInt(value[e]);
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
/* 1431 */     int[] value = this.value = new int[this.n + 1];
/*      */ 
/*      */     
/* 1434 */     for (int i = this.size; i-- != 0; ) {
/* 1435 */       int pos; K k = (K)s.readObject();
/* 1436 */       int v = s.readInt();
/* 1437 */       if (k == null) {
/* 1438 */         pos = this.n;
/* 1439 */         this.containsNullKey = true;
/*      */       } else {
/* 1441 */         pos = HashCommon.mix(k.hashCode()) & this.mask;
/* 1442 */         for (; key[pos] != null; pos = pos + 1 & this.mask);
/*      */       } 
/* 1444 */       key[pos] = k;
/* 1445 */       value[pos] = v;
/*      */     } 
/*      */   }
/*      */   
/*      */   private void checkTable() {}
/*      */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\i\\unimi\dsi\fastutil\objects\Object2IntOpenHashMap.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */