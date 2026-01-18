/*      */ package it.unimi.dsi.fastutil.objects;
/*      */ 
/*      */ import it.unimi.dsi.fastutil.Hash;
/*      */ import it.unimi.dsi.fastutil.HashCommon;
/*      */ import it.unimi.dsi.fastutil.Pair;
/*      */ import it.unimi.dsi.fastutil.Size64;
/*      */ import java.io.IOException;
/*      */ import java.io.ObjectInputStream;
/*      */ import java.io.ObjectOutputStream;
/*      */ import java.io.Serializable;
/*      */ import java.util.Arrays;
/*      */ import java.util.Collection;
/*      */ import java.util.Comparator;
/*      */ import java.util.Iterator;
/*      */ import java.util.Map;
/*      */ import java.util.NoSuchElementException;
/*      */ import java.util.Objects;
/*      */ import java.util.Set;
/*      */ import java.util.SortedMap;
/*      */ import java.util.SortedSet;
/*      */ import java.util.Spliterator;
/*      */ import java.util.function.BiFunction;
/*      */ import java.util.function.Consumer;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ public class Object2ReferenceLinkedOpenHashMap<K, V>
/*      */   extends AbstractObject2ReferenceSortedMap<K, V>
/*      */   implements Serializable, Cloneable, Hash
/*      */ {
/*      */   private static final long serialVersionUID = 0L;
/*      */   private static final boolean ASSERTS = false;
/*      */   protected transient K[] key;
/*      */   protected transient V[] value;
/*      */   protected transient int mask;
/*      */   protected transient boolean containsNullKey;
/*   95 */   protected transient int first = -1;
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*  100 */   protected transient int last = -1;
/*      */ 
/*      */ 
/*      */   
/*      */   protected transient long[] link;
/*      */ 
/*      */ 
/*      */   
/*      */   protected transient int n;
/*      */ 
/*      */ 
/*      */   
/*      */   protected transient int maxFill;
/*      */ 
/*      */   
/*      */   protected final transient int minN;
/*      */ 
/*      */   
/*      */   protected int size;
/*      */ 
/*      */   
/*      */   protected final float f;
/*      */ 
/*      */   
/*      */   protected transient Object2ReferenceSortedMap.FastSortedEntrySet<K, V> entries;
/*      */ 
/*      */   
/*      */   protected transient ObjectSortedSet<K> keys;
/*      */ 
/*      */   
/*      */   protected transient ReferenceCollection<V> values;
/*      */ 
/*      */ 
/*      */   
/*      */   public Object2ReferenceLinkedOpenHashMap(int expected, float f) {
/*  135 */     if (f <= 0.0F || f >= 1.0F) throw new IllegalArgumentException("Load factor must be greater than 0 and smaller than 1"); 
/*  136 */     if (expected < 0) throw new IllegalArgumentException("The expected number of elements must be nonnegative"); 
/*  137 */     this.f = f;
/*  138 */     this.minN = this.n = HashCommon.arraySize(expected, f);
/*  139 */     this.mask = this.n - 1;
/*  140 */     this.maxFill = HashCommon.maxFill(this.n, f);
/*  141 */     this.key = (K[])new Object[this.n + 1];
/*  142 */     this.value = (V[])new Object[this.n + 1];
/*  143 */     this.link = new long[this.n + 1];
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Object2ReferenceLinkedOpenHashMap(int expected) {
/*  152 */     this(expected, 0.75F);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Object2ReferenceLinkedOpenHashMap() {
/*  160 */     this(16, 0.75F);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Object2ReferenceLinkedOpenHashMap(Map<? extends K, ? extends V> m, float f) {
/*  170 */     this(m.size(), f);
/*  171 */     putAll(m);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Object2ReferenceLinkedOpenHashMap(Map<? extends K, ? extends V> m) {
/*  180 */     this(m, 0.75F);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Object2ReferenceLinkedOpenHashMap(Object2ReferenceMap<K, V> m, float f) {
/*  190 */     this(m.size(), f);
/*  191 */     putAll(m);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Object2ReferenceLinkedOpenHashMap(Object2ReferenceMap<K, V> m) {
/*  201 */     this(m, 0.75F);
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
/*      */   public Object2ReferenceLinkedOpenHashMap(K[] k, V[] v, float f) {
/*  213 */     this(k.length, f);
/*  214 */     if (k.length != v.length) throw new IllegalArgumentException("The key array and the value array have different lengths (" + k.length + " and " + v.length + ")"); 
/*  215 */     for (int i = 0; i < k.length; ) { put(k[i], v[i]); i++; }
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
/*      */   public Object2ReferenceLinkedOpenHashMap(K[] k, V[] v) {
/*  227 */     this(k, v, 0.75F);
/*      */   }
/*      */   
/*      */   private int realSize() {
/*  231 */     return this.containsNullKey ? (this.size - 1) : this.size;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void ensureCapacity(int capacity) {
/*  241 */     int needed = HashCommon.arraySize(capacity, this.f);
/*  242 */     if (needed > this.n) rehash(needed); 
/*      */   }
/*      */   
/*      */   private void tryCapacity(long capacity) {
/*  246 */     int needed = (int)Math.min(1073741824L, Math.max(2L, HashCommon.nextPowerOfTwo((long)Math.ceil(((float)capacity / this.f)))));
/*  247 */     if (needed > this.n) rehash(needed); 
/*      */   }
/*      */   
/*      */   private V removeEntry(int pos) {
/*  251 */     V oldValue = this.value[pos];
/*  252 */     this.value[pos] = null;
/*  253 */     this.size--;
/*  254 */     fixPointers(pos);
/*  255 */     shiftKeys(pos);
/*  256 */     if (this.n > this.minN && this.size < this.maxFill / 4 && this.n > 16) rehash(this.n / 2); 
/*  257 */     return oldValue;
/*      */   }
/*      */   
/*      */   private V removeNullEntry() {
/*  261 */     this.containsNullKey = false;
/*  262 */     this.key[this.n] = null;
/*  263 */     V oldValue = this.value[this.n];
/*  264 */     this.value[this.n] = null;
/*  265 */     this.size--;
/*  266 */     fixPointers(this.n);
/*  267 */     if (this.n > this.minN && this.size < this.maxFill / 4 && this.n > 16) rehash(this.n / 2); 
/*  268 */     return oldValue;
/*      */   }
/*      */ 
/*      */   
/*      */   public void putAll(Map<? extends K, ? extends V> m) {
/*  273 */     if (this.f <= 0.5D) { ensureCapacity(m.size()); }
/*  274 */     else { tryCapacity((size() + m.size())); }
/*      */     
/*  276 */     super.putAll(m);
/*      */   }
/*      */ 
/*      */   
/*      */   private int find(K k) {
/*  281 */     if (k == null) return this.containsNullKey ? this.n : -(this.n + 1);
/*      */     
/*  283 */     K[] key = this.key;
/*      */     K curr;
/*      */     int pos;
/*  286 */     if ((curr = key[pos = HashCommon.mix(k.hashCode()) & this.mask]) == null) return -(pos + 1); 
/*  287 */     if (k.equals(curr)) return pos;
/*      */     
/*      */     while (true) {
/*  290 */       if ((curr = key[pos = pos + 1 & this.mask]) == null) return -(pos + 1); 
/*  291 */       if (k.equals(curr)) return pos; 
/*      */     } 
/*      */   }
/*      */   
/*      */   private void insert(int pos, K k, V v) {
/*  296 */     if (pos == this.n) this.containsNullKey = true; 
/*  297 */     this.key[pos] = k;
/*  298 */     this.value[pos] = v;
/*  299 */     if (this.size == 0) {
/*  300 */       this.first = this.last = pos;
/*      */       
/*  302 */       this.link[pos] = -1L;
/*      */     } else {
/*  304 */       this.link[this.last] = this.link[this.last] ^ (this.link[this.last] ^ pos & 0xFFFFFFFFL) & 0xFFFFFFFFL;
/*  305 */       this.link[pos] = (this.last & 0xFFFFFFFFL) << 32L | 0xFFFFFFFFL;
/*  306 */       this.last = pos;
/*      */     } 
/*  308 */     if (this.size++ >= this.maxFill) rehash(HashCommon.arraySize(this.size + 1, this.f));
/*      */   
/*      */   }
/*      */ 
/*      */   
/*      */   public V put(K k, V v) {
/*  314 */     int pos = find(k);
/*  315 */     if (pos < 0) {
/*  316 */       insert(-pos - 1, k, v);
/*  317 */       return this.defRetValue;
/*      */     } 
/*  319 */     V oldValue = this.value[pos];
/*  320 */     this.value[pos] = v;
/*  321 */     return oldValue;
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
/*  334 */     K[] key = this.key; while (true) {
/*      */       K curr; int last;
/*  336 */       pos = (last = pos) + 1 & this.mask;
/*      */       while (true) {
/*  338 */         if ((curr = key[pos]) == null) {
/*  339 */           key[last] = null;
/*  340 */           this.value[last] = null;
/*      */           return;
/*      */         } 
/*  343 */         int slot = HashCommon.mix(curr.hashCode()) & this.mask;
/*  344 */         if ((last <= pos) ? (last >= slot || slot > pos) : (last >= slot && slot > pos))
/*  345 */           break;  pos = pos + 1 & this.mask;
/*      */       } 
/*  347 */       key[last] = curr;
/*  348 */       this.value[last] = this.value[pos];
/*  349 */       fixPointers(pos, last);
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public V remove(Object k) {
/*  356 */     if (k == null) {
/*  357 */       if (this.containsNullKey) return removeNullEntry(); 
/*  358 */       return this.defRetValue;
/*      */     } 
/*      */     
/*  361 */     K[] key = this.key;
/*      */     K curr;
/*      */     int pos;
/*  364 */     if ((curr = key[pos = HashCommon.mix(k.hashCode()) & this.mask]) == null) return this.defRetValue; 
/*  365 */     if (k.equals(curr)) return removeEntry(pos); 
/*      */     while (true) {
/*  367 */       if ((curr = key[pos = pos + 1 & this.mask]) == null) return this.defRetValue; 
/*  368 */       if (k.equals(curr)) return removeEntry(pos); 
/*      */     } 
/*      */   }
/*      */   
/*      */   private V setValue(int pos, V v) {
/*  373 */     V oldValue = this.value[pos];
/*  374 */     this.value[pos] = v;
/*  375 */     return oldValue;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public V removeFirst() {
/*  385 */     if (this.size == 0) throw new NoSuchElementException(); 
/*  386 */     int pos = this.first;
/*      */     
/*  388 */     if (this.size == 1) { this.first = this.last = -1; }
/*      */     else
/*  390 */     { this.first = (int)this.link[pos];
/*  391 */       if (0 <= this.first)
/*      */       {
/*  393 */         this.link[this.first] = this.link[this.first] | 0xFFFFFFFF00000000L;
/*      */       } }
/*      */     
/*  396 */     this.size--;
/*  397 */     V v = this.value[pos];
/*  398 */     if (pos == this.n)
/*  399 */     { this.containsNullKey = false;
/*  400 */       this.key[this.n] = null;
/*  401 */       this.value[this.n] = null; }
/*  402 */     else { shiftKeys(pos); }
/*  403 */      if (this.n > this.minN && this.size < this.maxFill / 4 && this.n > 16) rehash(this.n / 2); 
/*  404 */     return v;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public V removeLast() {
/*  414 */     if (this.size == 0) throw new NoSuchElementException(); 
/*  415 */     int pos = this.last;
/*      */     
/*  417 */     if (this.size == 1) { this.first = this.last = -1; }
/*      */     else
/*  419 */     { this.last = (int)(this.link[pos] >>> 32L);
/*  420 */       if (0 <= this.last)
/*      */       {
/*  422 */         this.link[this.last] = this.link[this.last] | 0xFFFFFFFFL;
/*      */       } }
/*      */     
/*  425 */     this.size--;
/*  426 */     V v = this.value[pos];
/*  427 */     if (pos == this.n)
/*  428 */     { this.containsNullKey = false;
/*  429 */       this.key[this.n] = null;
/*  430 */       this.value[this.n] = null; }
/*  431 */     else { shiftKeys(pos); }
/*  432 */      if (this.n > this.minN && this.size < this.maxFill / 4 && this.n > 16) rehash(this.n / 2); 
/*  433 */     return v;
/*      */   }
/*      */   
/*      */   private void moveIndexToFirst(int i) {
/*  437 */     if (this.size == 1 || this.first == i)
/*  438 */       return;  if (this.last == i) {
/*  439 */       this.last = (int)(this.link[i] >>> 32L);
/*      */       
/*  441 */       this.link[this.last] = this.link[this.last] | 0xFFFFFFFFL;
/*      */     } else {
/*  443 */       long linki = this.link[i];
/*  444 */       int prev = (int)(linki >>> 32L);
/*  445 */       int next = (int)linki;
/*  446 */       this.link[prev] = this.link[prev] ^ (this.link[prev] ^ linki & 0xFFFFFFFFL) & 0xFFFFFFFFL;
/*  447 */       this.link[next] = this.link[next] ^ (this.link[next] ^ linki & 0xFFFFFFFF00000000L) & 0xFFFFFFFF00000000L;
/*      */     } 
/*  449 */     this.link[this.first] = this.link[this.first] ^ (this.link[this.first] ^ (i & 0xFFFFFFFFL) << 32L) & 0xFFFFFFFF00000000L;
/*  450 */     this.link[i] = 0xFFFFFFFF00000000L | this.first & 0xFFFFFFFFL;
/*  451 */     this.first = i;
/*      */   }
/*      */   
/*      */   private void moveIndexToLast(int i) {
/*  455 */     if (this.size == 1 || this.last == i)
/*  456 */       return;  if (this.first == i) {
/*  457 */       this.first = (int)this.link[i];
/*      */       
/*  459 */       this.link[this.first] = this.link[this.first] | 0xFFFFFFFF00000000L;
/*      */     } else {
/*  461 */       long linki = this.link[i];
/*  462 */       int prev = (int)(linki >>> 32L);
/*  463 */       int next = (int)linki;
/*  464 */       this.link[prev] = this.link[prev] ^ (this.link[prev] ^ linki & 0xFFFFFFFFL) & 0xFFFFFFFFL;
/*  465 */       this.link[next] = this.link[next] ^ (this.link[next] ^ linki & 0xFFFFFFFF00000000L) & 0xFFFFFFFF00000000L;
/*      */     } 
/*  467 */     this.link[this.last] = this.link[this.last] ^ (this.link[this.last] ^ i & 0xFFFFFFFFL) & 0xFFFFFFFFL;
/*  468 */     this.link[i] = (this.last & 0xFFFFFFFFL) << 32L | 0xFFFFFFFFL;
/*  469 */     this.last = i;
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
/*      */   public V getAndMoveToFirst(K k) {
/*  481 */     if (k == null) {
/*  482 */       if (this.containsNullKey) {
/*  483 */         moveIndexToFirst(this.n);
/*  484 */         return this.value[this.n];
/*      */       } 
/*  486 */       return this.defRetValue;
/*      */     } 
/*      */     
/*  489 */     K[] key = this.key;
/*      */     K curr;
/*      */     int pos;
/*  492 */     if ((curr = key[pos = HashCommon.mix(k.hashCode()) & this.mask]) == null) return this.defRetValue; 
/*  493 */     if (k.equals(curr)) {
/*  494 */       moveIndexToFirst(pos);
/*  495 */       return this.value[pos];
/*      */     } 
/*      */     
/*      */     while (true) {
/*  499 */       if ((curr = key[pos = pos + 1 & this.mask]) == null) return this.defRetValue; 
/*  500 */       if (k.equals(curr)) {
/*  501 */         moveIndexToFirst(pos);
/*  502 */         return this.value[pos];
/*      */       } 
/*      */     } 
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
/*      */   public V getAndMoveToLast(K k) {
/*  516 */     if (k == null) {
/*  517 */       if (this.containsNullKey) {
/*  518 */         moveIndexToLast(this.n);
/*  519 */         return this.value[this.n];
/*      */       } 
/*  521 */       return this.defRetValue;
/*      */     } 
/*      */     
/*  524 */     K[] key = this.key;
/*      */     K curr;
/*      */     int pos;
/*  527 */     if ((curr = key[pos = HashCommon.mix(k.hashCode()) & this.mask]) == null) return this.defRetValue; 
/*  528 */     if (k.equals(curr)) {
/*  529 */       moveIndexToLast(pos);
/*  530 */       return this.value[pos];
/*      */     } 
/*      */     
/*      */     while (true) {
/*  534 */       if ((curr = key[pos = pos + 1 & this.mask]) == null) return this.defRetValue; 
/*  535 */       if (k.equals(curr)) {
/*  536 */         moveIndexToLast(pos);
/*  537 */         return this.value[pos];
/*      */       } 
/*      */     } 
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
/*      */   public V putAndMoveToFirst(K k, V v) {
/*      */     int pos;
/*  553 */     if (k == null) {
/*  554 */       if (this.containsNullKey) {
/*  555 */         moveIndexToFirst(this.n);
/*  556 */         return setValue(this.n, v);
/*      */       } 
/*  558 */       this.containsNullKey = true;
/*  559 */       pos = this.n;
/*      */     } else {
/*      */       
/*  562 */       K[] key = this.key;
/*      */       K curr;
/*  564 */       if ((curr = key[pos = HashCommon.mix(k.hashCode()) & this.mask]) != null) {
/*  565 */         if (curr.equals(k)) {
/*  566 */           moveIndexToFirst(pos);
/*  567 */           return setValue(pos, v);
/*      */         } 
/*  569 */         while ((curr = key[pos = pos + 1 & this.mask]) != null) { if (curr.equals(k)) {
/*  570 */             moveIndexToFirst(pos);
/*  571 */             return setValue(pos, v);
/*      */           }  }
/*      */       
/*      */       } 
/*  575 */     }  this.key[pos] = k;
/*  576 */     this.value[pos] = v;
/*  577 */     if (this.size == 0) {
/*  578 */       this.first = this.last = pos;
/*      */       
/*  580 */       this.link[pos] = -1L;
/*      */     } else {
/*  582 */       this.link[this.first] = this.link[this.first] ^ (this.link[this.first] ^ (pos & 0xFFFFFFFFL) << 32L) & 0xFFFFFFFF00000000L;
/*  583 */       this.link[pos] = 0xFFFFFFFF00000000L | this.first & 0xFFFFFFFFL;
/*  584 */       this.first = pos;
/*      */     } 
/*  586 */     if (this.size++ >= this.maxFill) rehash(HashCommon.arraySize(this.size, this.f));
/*      */     
/*  588 */     return this.defRetValue;
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
/*      */   public V putAndMoveToLast(K k, V v) {
/*      */     int pos;
/*  602 */     if (k == null) {
/*  603 */       if (this.containsNullKey) {
/*  604 */         moveIndexToLast(this.n);
/*  605 */         return setValue(this.n, v);
/*      */       } 
/*  607 */       this.containsNullKey = true;
/*  608 */       pos = this.n;
/*      */     } else {
/*      */       
/*  611 */       K[] key = this.key;
/*      */       K curr;
/*  613 */       if ((curr = key[pos = HashCommon.mix(k.hashCode()) & this.mask]) != null) {
/*  614 */         if (curr.equals(k)) {
/*  615 */           moveIndexToLast(pos);
/*  616 */           return setValue(pos, v);
/*      */         } 
/*  618 */         while ((curr = key[pos = pos + 1 & this.mask]) != null) { if (curr.equals(k)) {
/*  619 */             moveIndexToLast(pos);
/*  620 */             return setValue(pos, v);
/*      */           }  }
/*      */       
/*      */       } 
/*  624 */     }  this.key[pos] = k;
/*  625 */     this.value[pos] = v;
/*  626 */     if (this.size == 0) {
/*  627 */       this.first = this.last = pos;
/*      */       
/*  629 */       this.link[pos] = -1L;
/*      */     } else {
/*  631 */       this.link[this.last] = this.link[this.last] ^ (this.link[this.last] ^ pos & 0xFFFFFFFFL) & 0xFFFFFFFFL;
/*  632 */       this.link[pos] = (this.last & 0xFFFFFFFFL) << 32L | 0xFFFFFFFFL;
/*  633 */       this.last = pos;
/*      */     } 
/*  635 */     if (this.size++ >= this.maxFill) rehash(HashCommon.arraySize(this.size, this.f));
/*      */     
/*  637 */     return this.defRetValue;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public V get(Object k) {
/*  643 */     if (k == null) return this.containsNullKey ? this.value[this.n] : this.defRetValue;
/*      */     
/*  645 */     K[] key = this.key;
/*      */     K curr;
/*      */     int pos;
/*  648 */     if ((curr = key[pos = HashCommon.mix(k.hashCode()) & this.mask]) == null) return this.defRetValue; 
/*  649 */     if (k.equals(curr)) return this.value[pos];
/*      */     
/*      */     while (true) {
/*  652 */       if ((curr = key[pos = pos + 1 & this.mask]) == null) return this.defRetValue; 
/*  653 */       if (k.equals(curr)) return this.value[pos];
/*      */     
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   public boolean containsKey(Object k) {
/*  660 */     if (k == null) return this.containsNullKey;
/*      */     
/*  662 */     K[] key = this.key;
/*      */     K curr;
/*      */     int pos;
/*  665 */     if ((curr = key[pos = HashCommon.mix(k.hashCode()) & this.mask]) == null) return false; 
/*  666 */     if (k.equals(curr)) return true;
/*      */     
/*      */     while (true) {
/*  669 */       if ((curr = key[pos = pos + 1 & this.mask]) == null) return false; 
/*  670 */       if (k.equals(curr)) return true;
/*      */     
/*      */     } 
/*      */   }
/*      */   
/*      */   public boolean containsValue(Object v) {
/*  676 */     V[] value = this.value;
/*  677 */     K[] key = this.key;
/*  678 */     if (this.containsNullKey && value[this.n] == v) return true; 
/*  679 */     for (int i = this.n; i-- != 0;) { if (key[i] != null && value[i] == v) return true;  }
/*  680 */      return false;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public V getOrDefault(Object k, V defaultValue) {
/*  687 */     if (k == null) return this.containsNullKey ? this.value[this.n] : defaultValue;
/*      */     
/*  689 */     K[] key = this.key;
/*      */     K curr;
/*      */     int pos;
/*  692 */     if ((curr = key[pos = HashCommon.mix(k.hashCode()) & this.mask]) == null) return defaultValue; 
/*  693 */     if (k.equals(curr)) return this.value[pos];
/*      */     
/*      */     while (true) {
/*  696 */       if ((curr = key[pos = pos + 1 & this.mask]) == null) return defaultValue; 
/*  697 */       if (k.equals(curr)) return this.value[pos];
/*      */     
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   public V putIfAbsent(K k, V v) {
/*  704 */     int pos = find(k);
/*  705 */     if (pos >= 0) return this.value[pos]; 
/*  706 */     insert(-pos - 1, k, v);
/*  707 */     return this.defRetValue;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean remove(Object k, Object v) {
/*  714 */     if (k == null) {
/*  715 */       if (this.containsNullKey && v == this.value[this.n]) {
/*  716 */         removeNullEntry();
/*  717 */         return true;
/*      */       } 
/*  719 */       return false;
/*      */     } 
/*      */     
/*  722 */     K[] key = this.key;
/*      */     K curr;
/*      */     int pos;
/*  725 */     if ((curr = key[pos = HashCommon.mix(k.hashCode()) & this.mask]) == null) return false; 
/*  726 */     if (k.equals(curr) && v == this.value[pos]) {
/*  727 */       removeEntry(pos);
/*  728 */       return true;
/*      */     } 
/*      */     while (true) {
/*  731 */       if ((curr = key[pos = pos + 1 & this.mask]) == null) return false; 
/*  732 */       if (k.equals(curr) && v == this.value[pos]) {
/*  733 */         removeEntry(pos);
/*  734 */         return true;
/*      */       } 
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean replace(K k, V oldValue, V v) {
/*  742 */     int pos = find(k);
/*  743 */     if (pos < 0 || oldValue != this.value[pos]) return false; 
/*  744 */     this.value[pos] = v;
/*  745 */     return true;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public V replace(K k, V v) {
/*  751 */     int pos = find(k);
/*  752 */     if (pos < 0) return this.defRetValue; 
/*  753 */     V oldValue = this.value[pos];
/*  754 */     this.value[pos] = v;
/*  755 */     return oldValue;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public V computeIfAbsent(K key, Object2ReferenceFunction<? super K, ? extends V> mappingFunction) {
/*  761 */     Objects.requireNonNull(mappingFunction);
/*  762 */     int pos = find(key);
/*  763 */     if (pos >= 0) return this.value[pos]; 
/*  764 */     if (!mappingFunction.containsKey(key)) return this.defRetValue; 
/*  765 */     V newValue = mappingFunction.get(key);
/*  766 */     insert(-pos - 1, key, newValue);
/*  767 */     return newValue;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public V computeIfPresent(K k, BiFunction<? super K, ? super V, ? extends V> remappingFunction) {
/*  773 */     Objects.requireNonNull(remappingFunction);
/*  774 */     int pos = find(k);
/*  775 */     if (pos < 0) return this.defRetValue; 
/*  776 */     if (this.value[pos] == null) return this.defRetValue; 
/*  777 */     V newValue = remappingFunction.apply(k, this.value[pos]);
/*  778 */     if (newValue == null) {
/*  779 */       if (k == null) { removeNullEntry(); }
/*  780 */       else { removeEntry(pos); }
/*  781 */        return this.defRetValue;
/*      */     } 
/*  783 */     this.value[pos] = newValue; return newValue;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public V compute(K k, BiFunction<? super K, ? super V, ? extends V> remappingFunction) {
/*  789 */     Objects.requireNonNull(remappingFunction);
/*  790 */     int pos = find(k);
/*  791 */     V newValue = remappingFunction.apply(k, (pos >= 0) ? this.value[pos] : null);
/*  792 */     if (newValue == null) {
/*  793 */       if (pos >= 0)
/*  794 */         if (k == null) { removeNullEntry(); }
/*  795 */         else { removeEntry(pos); }
/*      */          
/*  797 */       return this.defRetValue;
/*      */     } 
/*  799 */     V newVal = newValue;
/*  800 */     if (pos < 0) {
/*  801 */       insert(-pos - 1, k, newVal);
/*  802 */       return newVal;
/*      */     } 
/*  804 */     this.value[pos] = newVal; return newVal;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public V merge(K k, V v, BiFunction<? super V, ? super V, ? extends V> remappingFunction) {
/*  810 */     Objects.requireNonNull(remappingFunction);
/*  811 */     Objects.requireNonNull(v);
/*  812 */     int pos = find(k);
/*  813 */     if (pos < 0 || this.value[pos] == null) {
/*  814 */       if (pos < 0) { insert(-pos - 1, k, v); }
/*  815 */       else { this.value[pos] = v; }
/*  816 */        return v;
/*      */     } 
/*  818 */     V newValue = remappingFunction.apply(this.value[pos], v);
/*  819 */     if (newValue == null) {
/*  820 */       if (k == null) { removeNullEntry(); }
/*  821 */       else { removeEntry(pos); }
/*  822 */        return this.defRetValue;
/*      */     } 
/*  824 */     this.value[pos] = newValue; return newValue;
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
/*  835 */     if (this.size == 0)
/*  836 */       return;  this.size = 0;
/*  837 */     this.containsNullKey = false;
/*  838 */     Arrays.fill((Object[])this.key, (Object)null);
/*  839 */     Arrays.fill((Object[])this.value, (Object)null);
/*  840 */     this.first = this.last = -1;
/*      */   }
/*      */ 
/*      */   
/*      */   public int size() {
/*  845 */     return this.size;
/*      */   }
/*      */ 
/*      */   
/*      */   public boolean isEmpty() {
/*  850 */     return (this.size == 0);
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   final class MapEntry
/*      */     implements Object2ReferenceMap.Entry<K, V>, Map.Entry<K, V>, ObjectReferencePair<K, V>
/*      */   {
/*      */     int index;
/*      */ 
/*      */ 
/*      */     
/*      */     MapEntry(int index) {
/*  863 */       this.index = index;
/*      */     }
/*      */ 
/*      */     
/*      */     MapEntry() {}
/*      */ 
/*      */     
/*      */     public K getKey() {
/*  871 */       return Object2ReferenceLinkedOpenHashMap.this.key[this.index];
/*      */     }
/*      */ 
/*      */     
/*      */     public K left() {
/*  876 */       return Object2ReferenceLinkedOpenHashMap.this.key[this.index];
/*      */     }
/*      */ 
/*      */     
/*      */     public V getValue() {
/*  881 */       return Object2ReferenceLinkedOpenHashMap.this.value[this.index];
/*      */     }
/*      */ 
/*      */     
/*      */     public V right() {
/*  886 */       return Object2ReferenceLinkedOpenHashMap.this.value[this.index];
/*      */     }
/*      */ 
/*      */     
/*      */     public V setValue(V v) {
/*  891 */       V oldValue = Object2ReferenceLinkedOpenHashMap.this.value[this.index];
/*  892 */       Object2ReferenceLinkedOpenHashMap.this.value[this.index] = v;
/*  893 */       return oldValue;
/*      */     }
/*      */ 
/*      */     
/*      */     public ObjectReferencePair<K, V> right(V v) {
/*  898 */       Object2ReferenceLinkedOpenHashMap.this.value[this.index] = v;
/*  899 */       return this;
/*      */     }
/*      */ 
/*      */ 
/*      */     
/*      */     public boolean equals(Object o) {
/*  905 */       if (!(o instanceof Map.Entry)) return false; 
/*  906 */       Map.Entry<K, V> e = (Map.Entry<K, V>)o;
/*  907 */       return (Objects.equals(Object2ReferenceLinkedOpenHashMap.this.key[this.index], e.getKey()) && Object2ReferenceLinkedOpenHashMap.this.value[this.index] == e.getValue());
/*      */     }
/*      */ 
/*      */     
/*      */     public int hashCode() {
/*  912 */       return ((Object2ReferenceLinkedOpenHashMap.this.key[this.index] == null) ? 0 : Object2ReferenceLinkedOpenHashMap.this.key[this.index].hashCode()) ^ ((Object2ReferenceLinkedOpenHashMap.this.value[this.index] == null) ? 0 : System.identityHashCode(Object2ReferenceLinkedOpenHashMap.this.value[this.index]));
/*      */     }
/*      */ 
/*      */     
/*      */     public String toString() {
/*  917 */       return (new StringBuilder()).append(Object2ReferenceLinkedOpenHashMap.this.key[this.index]).append("=>").append(Object2ReferenceLinkedOpenHashMap.this.value[this.index]).toString();
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected void fixPointers(int i) {
/*  928 */     if (this.size == 0) {
/*  929 */       this.first = this.last = -1;
/*      */       return;
/*      */     } 
/*  932 */     if (this.first == i) {
/*  933 */       this.first = (int)this.link[i];
/*  934 */       if (0 <= this.first)
/*      */       {
/*  936 */         this.link[this.first] = this.link[this.first] | 0xFFFFFFFF00000000L;
/*      */       }
/*      */       return;
/*      */     } 
/*  940 */     if (this.last == i) {
/*  941 */       this.last = (int)(this.link[i] >>> 32L);
/*  942 */       if (0 <= this.last)
/*      */       {
/*  944 */         this.link[this.last] = this.link[this.last] | 0xFFFFFFFFL;
/*      */       }
/*      */       return;
/*      */     } 
/*  948 */     long linki = this.link[i];
/*  949 */     int prev = (int)(linki >>> 32L);
/*  950 */     int next = (int)linki;
/*  951 */     this.link[prev] = this.link[prev] ^ (this.link[prev] ^ linki & 0xFFFFFFFFL) & 0xFFFFFFFFL;
/*  952 */     this.link[next] = this.link[next] ^ (this.link[next] ^ linki & 0xFFFFFFFF00000000L) & 0xFFFFFFFF00000000L;
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
/*      */   protected void fixPointers(int s, int d) {
/*  964 */     if (this.size == 1) {
/*  965 */       this.first = this.last = d;
/*      */       
/*  967 */       this.link[d] = -1L;
/*      */       return;
/*      */     } 
/*  970 */     if (this.first == s) {
/*  971 */       this.first = d;
/*  972 */       this.link[(int)this.link[s]] = this.link[(int)this.link[s]] ^ (this.link[(int)this.link[s]] ^ (d & 0xFFFFFFFFL) << 32L) & 0xFFFFFFFF00000000L;
/*  973 */       this.link[d] = this.link[s];
/*      */       return;
/*      */     } 
/*  976 */     if (this.last == s) {
/*  977 */       this.last = d;
/*  978 */       this.link[(int)(this.link[s] >>> 32L)] = this.link[(int)(this.link[s] >>> 32L)] ^ (this.link[(int)(this.link[s] >>> 32L)] ^ d & 0xFFFFFFFFL) & 0xFFFFFFFFL;
/*  979 */       this.link[d] = this.link[s];
/*      */       return;
/*      */     } 
/*  982 */     long links = this.link[s];
/*  983 */     int prev = (int)(links >>> 32L);
/*  984 */     int next = (int)links;
/*  985 */     this.link[prev] = this.link[prev] ^ (this.link[prev] ^ d & 0xFFFFFFFFL) & 0xFFFFFFFFL;
/*  986 */     this.link[next] = this.link[next] ^ (this.link[next] ^ (d & 0xFFFFFFFFL) << 32L) & 0xFFFFFFFF00000000L;
/*  987 */     this.link[d] = links;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public K firstKey() {
/*  997 */     if (this.size == 0) throw new NoSuchElementException(); 
/*  998 */     return this.key[this.first];
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public K lastKey() {
/* 1008 */     if (this.size == 0) throw new NoSuchElementException(); 
/* 1009 */     return this.key[this.last];
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Object2ReferenceSortedMap<K, V> tailMap(K from) {
/* 1019 */     throw new UnsupportedOperationException();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Object2ReferenceSortedMap<K, V> headMap(K to) {
/* 1029 */     throw new UnsupportedOperationException();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Object2ReferenceSortedMap<K, V> subMap(K from, K to) {
/* 1039 */     throw new UnsupportedOperationException();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Comparator<? super K> comparator() {
/* 1049 */     return null;
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
/*      */   private abstract class MapIterator<ConsumerType>
/*      */   {
/* 1064 */     int prev = -1;
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 1069 */     int next = -1;
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 1074 */     int curr = -1;
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 1079 */     int index = -1;
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     protected MapIterator() {
/* 1085 */       this.next = Object2ReferenceLinkedOpenHashMap.this.first;
/* 1086 */       this.index = 0;
/*      */     }
/*      */     
/*      */     private MapIterator(K from) {
/* 1090 */       if (from == null) {
/* 1091 */         if (Object2ReferenceLinkedOpenHashMap.this.containsNullKey) {
/* 1092 */           this.next = (int)Object2ReferenceLinkedOpenHashMap.this.link[Object2ReferenceLinkedOpenHashMap.this.n];
/* 1093 */           this.prev = Object2ReferenceLinkedOpenHashMap.this.n; return;
/*      */         } 
/* 1095 */         throw new NoSuchElementException("The key " + from + " does not belong to this map.");
/*      */       } 
/* 1097 */       if (Objects.equals(Object2ReferenceLinkedOpenHashMap.this.key[Object2ReferenceLinkedOpenHashMap.this.last], from)) {
/* 1098 */         this.prev = Object2ReferenceLinkedOpenHashMap.this.last;
/* 1099 */         this.index = Object2ReferenceLinkedOpenHashMap.this.size;
/*      */         
/*      */         return;
/*      */       } 
/* 1103 */       int pos = HashCommon.mix(from.hashCode()) & Object2ReferenceLinkedOpenHashMap.this.mask;
/*      */       
/* 1105 */       while (Object2ReferenceLinkedOpenHashMap.this.key[pos] != null) {
/* 1106 */         if (Object2ReferenceLinkedOpenHashMap.this.key[pos].equals(from)) {
/*      */           
/* 1108 */           this.next = (int)Object2ReferenceLinkedOpenHashMap.this.link[pos];
/* 1109 */           this.prev = pos;
/*      */           return;
/*      */         } 
/* 1112 */         pos = pos + 1 & Object2ReferenceLinkedOpenHashMap.this.mask;
/*      */       } 
/* 1114 */       throw new NoSuchElementException("The key " + from + " does not belong to this map.");
/*      */     }
/*      */     
/*      */     public boolean hasNext() {
/* 1118 */       return (this.next != -1);
/*      */     }
/*      */     
/*      */     public boolean hasPrevious() {
/* 1122 */       return (this.prev != -1);
/*      */     }
/*      */     
/*      */     private final void ensureIndexKnown() {
/* 1126 */       if (this.index >= 0)
/* 1127 */         return;  if (this.prev == -1) {
/* 1128 */         this.index = 0;
/*      */         return;
/*      */       } 
/* 1131 */       if (this.next == -1) {
/* 1132 */         this.index = Object2ReferenceLinkedOpenHashMap.this.size;
/*      */         return;
/*      */       } 
/* 1135 */       int pos = Object2ReferenceLinkedOpenHashMap.this.first;
/* 1136 */       this.index = 1;
/* 1137 */       while (pos != this.prev) {
/* 1138 */         pos = (int)Object2ReferenceLinkedOpenHashMap.this.link[pos];
/* 1139 */         this.index++;
/*      */       } 
/*      */     }
/*      */     
/*      */     public int nextIndex() {
/* 1144 */       ensureIndexKnown();
/* 1145 */       return this.index;
/*      */     }
/*      */     
/*      */     public int previousIndex() {
/* 1149 */       ensureIndexKnown();
/* 1150 */       return this.index - 1;
/*      */     }
/*      */     
/*      */     public int nextEntry() {
/* 1154 */       if (!hasNext()) throw new NoSuchElementException(); 
/* 1155 */       this.curr = this.next;
/* 1156 */       this.next = (int)Object2ReferenceLinkedOpenHashMap.this.link[this.curr];
/* 1157 */       this.prev = this.curr;
/* 1158 */       if (this.index >= 0) this.index++; 
/* 1159 */       return this.curr;
/*      */     }
/*      */     
/*      */     public int previousEntry() {
/* 1163 */       if (!hasPrevious()) throw new NoSuchElementException(); 
/* 1164 */       this.curr = this.prev;
/* 1165 */       this.prev = (int)(Object2ReferenceLinkedOpenHashMap.this.link[this.curr] >>> 32L);
/* 1166 */       this.next = this.curr;
/* 1167 */       if (this.index >= 0) this.index--; 
/* 1168 */       return this.curr;
/*      */     }
/*      */     
/*      */     public void forEachRemaining(ConsumerType action) {
/* 1172 */       while (hasNext()) {
/* 1173 */         this.curr = this.next;
/* 1174 */         this.next = (int)Object2ReferenceLinkedOpenHashMap.this.link[this.curr];
/* 1175 */         this.prev = this.curr;
/* 1176 */         if (this.index >= 0) this.index++; 
/* 1177 */         acceptOnIndex(action, this.curr);
/*      */       } 
/*      */     }
/*      */     
/*      */     public void remove() {
/* 1182 */       ensureIndexKnown();
/* 1183 */       if (this.curr == -1) throw new IllegalStateException(); 
/* 1184 */       if (this.curr == this.prev)
/*      */       
/*      */       { 
/* 1187 */         this.index--;
/* 1188 */         this.prev = (int)(Object2ReferenceLinkedOpenHashMap.this.link[this.curr] >>> 32L); }
/* 1189 */       else { this.next = (int)Object2ReferenceLinkedOpenHashMap.this.link[this.curr]; }
/* 1190 */        Object2ReferenceLinkedOpenHashMap.this.size--;
/*      */ 
/*      */       
/* 1193 */       if (this.prev == -1) { Object2ReferenceLinkedOpenHashMap.this.first = this.next; }
/* 1194 */       else { Object2ReferenceLinkedOpenHashMap.this.link[this.prev] = Object2ReferenceLinkedOpenHashMap.this.link[this.prev] ^ (Object2ReferenceLinkedOpenHashMap.this.link[this.prev] ^ this.next & 0xFFFFFFFFL) & 0xFFFFFFFFL; }
/* 1195 */        if (this.next == -1) { Object2ReferenceLinkedOpenHashMap.this.last = this.prev; }
/* 1196 */       else { Object2ReferenceLinkedOpenHashMap.this.link[this.next] = Object2ReferenceLinkedOpenHashMap.this.link[this.next] ^ (Object2ReferenceLinkedOpenHashMap.this.link[this.next] ^ (this.prev & 0xFFFFFFFFL) << 32L) & 0xFFFFFFFF00000000L; }
/* 1197 */        int pos = this.curr;
/* 1198 */       this.curr = -1;
/* 1199 */       if (pos == Object2ReferenceLinkedOpenHashMap.this.n) {
/* 1200 */         Object2ReferenceLinkedOpenHashMap.this.containsNullKey = false;
/* 1201 */         Object2ReferenceLinkedOpenHashMap.this.key[Object2ReferenceLinkedOpenHashMap.this.n] = null;
/* 1202 */         Object2ReferenceLinkedOpenHashMap.this.value[Object2ReferenceLinkedOpenHashMap.this.n] = null;
/*      */       } else {
/*      */         
/* 1205 */         K[] key = Object2ReferenceLinkedOpenHashMap.this.key; while (true) {
/*      */           K curr;
/*      */           int last;
/* 1208 */           pos = (last = pos) + 1 & Object2ReferenceLinkedOpenHashMap.this.mask;
/*      */           while (true) {
/* 1210 */             if ((curr = key[pos]) == null) {
/* 1211 */               key[last] = null;
/* 1212 */               Object2ReferenceLinkedOpenHashMap.this.value[last] = null;
/*      */               return;
/*      */             } 
/* 1215 */             int slot = HashCommon.mix(curr.hashCode()) & Object2ReferenceLinkedOpenHashMap.this.mask;
/* 1216 */             if ((last <= pos) ? (last >= slot || slot > pos) : (last >= slot && slot > pos))
/* 1217 */               break;  pos = pos + 1 & Object2ReferenceLinkedOpenHashMap.this.mask;
/*      */           } 
/* 1219 */           key[last] = curr;
/* 1220 */           Object2ReferenceLinkedOpenHashMap.this.value[last] = Object2ReferenceLinkedOpenHashMap.this.value[pos];
/* 1221 */           if (this.next == pos) this.next = last; 
/* 1222 */           if (this.prev == pos) this.prev = last; 
/* 1223 */           Object2ReferenceLinkedOpenHashMap.this.fixPointers(pos, last);
/*      */         } 
/*      */       } 
/*      */     }
/*      */     
/*      */     public int skip(int n) {
/* 1229 */       int i = n;
/* 1230 */       for (; i-- != 0 && hasNext(); nextEntry());
/* 1231 */       return n - i - 1;
/*      */     }
/*      */     
/*      */     public int back(int n) {
/* 1235 */       int i = n;
/* 1236 */       for (; i-- != 0 && hasPrevious(); previousEntry());
/* 1237 */       return n - i - 1;
/*      */     }
/*      */     
/*      */     public void set(Object2ReferenceMap.Entry<K, V> ok) {
/* 1241 */       throw new UnsupportedOperationException();
/*      */     }
/*      */     
/*      */     public void add(Object2ReferenceMap.Entry<K, V> ok) {
/* 1245 */       throw new UnsupportedOperationException();
/*      */     }
/*      */     
/*      */     abstract void acceptOnIndex(ConsumerType param1ConsumerType, int param1Int); }
/*      */   
/*      */   private final class EntryIterator extends MapIterator<Consumer<? super Object2ReferenceMap.Entry<K, V>>> implements ObjectListIterator<Object2ReferenceMap.Entry<K, V>> {
/*      */     private Object2ReferenceLinkedOpenHashMap<K, V>.MapEntry entry;
/*      */     
/*      */     public EntryIterator() {}
/*      */     
/*      */     public EntryIterator(K from) {
/* 1256 */       super(from);
/*      */     }
/*      */ 
/*      */ 
/*      */     
/*      */     final void acceptOnIndex(Consumer<? super Object2ReferenceMap.Entry<K, V>> action, int index) {
/* 1262 */       action.accept(new Object2ReferenceLinkedOpenHashMap.MapEntry(index));
/*      */     }
/*      */ 
/*      */     
/*      */     public Object2ReferenceLinkedOpenHashMap<K, V>.MapEntry next() {
/* 1267 */       return this.entry = new Object2ReferenceLinkedOpenHashMap.MapEntry(nextEntry());
/*      */     }
/*      */ 
/*      */     
/*      */     public Object2ReferenceLinkedOpenHashMap<K, V>.MapEntry previous() {
/* 1272 */       return this.entry = new Object2ReferenceLinkedOpenHashMap.MapEntry(previousEntry());
/*      */     }
/*      */ 
/*      */     
/*      */     public void remove() {
/* 1277 */       super.remove();
/* 1278 */       this.entry.index = -1;
/*      */     }
/*      */   }
/*      */   
/*      */   private final class FastEntryIterator extends MapIterator<Consumer<? super Object2ReferenceMap.Entry<K, V>>> implements ObjectListIterator<Object2ReferenceMap.Entry<K, V>> {
/* 1283 */     final Object2ReferenceLinkedOpenHashMap<K, V>.MapEntry entry = new Object2ReferenceLinkedOpenHashMap.MapEntry();
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public FastEntryIterator(K from) {
/* 1289 */       super(from);
/*      */     }
/*      */ 
/*      */ 
/*      */     
/*      */     final void acceptOnIndex(Consumer<? super Object2ReferenceMap.Entry<K, V>> action, int index) {
/* 1295 */       this.entry.index = index;
/* 1296 */       action.accept(this.entry);
/*      */     }
/*      */ 
/*      */     
/*      */     public Object2ReferenceLinkedOpenHashMap<K, V>.MapEntry next() {
/* 1301 */       this.entry.index = nextEntry();
/* 1302 */       return this.entry;
/*      */     }
/*      */ 
/*      */     
/*      */     public Object2ReferenceLinkedOpenHashMap<K, V>.MapEntry previous() {
/* 1307 */       this.entry.index = previousEntry();
/* 1308 */       return this.entry;
/*      */     }
/*      */     
/*      */     public FastEntryIterator() {} }
/*      */   
/*      */   private final class MapEntrySet extends AbstractObjectSortedSet<Object2ReferenceMap.Entry<K, V>> implements Object2ReferenceSortedMap.FastSortedEntrySet<K, V> {
/*      */     private static final int SPLITERATOR_CHARACTERISTICS = 81;
/*      */     
/*      */     public ObjectBidirectionalIterator<Object2ReferenceMap.Entry<K, V>> iterator() {
/* 1317 */       return new Object2ReferenceLinkedOpenHashMap.EntryIterator();
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     private MapEntrySet() {}
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public ObjectSpliterator<Object2ReferenceMap.Entry<K, V>> spliterator() {
/* 1335 */       return ObjectSpliterators.asSpliterator(iterator(), Size64.sizeOf(Object2ReferenceLinkedOpenHashMap.this), 81);
/*      */     }
/*      */ 
/*      */     
/*      */     public Comparator<? super Object2ReferenceMap.Entry<K, V>> comparator() {
/* 1340 */       return null;
/*      */     }
/*      */ 
/*      */     
/*      */     public ObjectSortedSet<Object2ReferenceMap.Entry<K, V>> subSet(Object2ReferenceMap.Entry<K, V> fromElement, Object2ReferenceMap.Entry<K, V> toElement) {
/* 1345 */       throw new UnsupportedOperationException();
/*      */     }
/*      */ 
/*      */     
/*      */     public ObjectSortedSet<Object2ReferenceMap.Entry<K, V>> headSet(Object2ReferenceMap.Entry<K, V> toElement) {
/* 1350 */       throw new UnsupportedOperationException();
/*      */     }
/*      */ 
/*      */     
/*      */     public ObjectSortedSet<Object2ReferenceMap.Entry<K, V>> tailSet(Object2ReferenceMap.Entry<K, V> fromElement) {
/* 1355 */       throw new UnsupportedOperationException();
/*      */     }
/*      */ 
/*      */     
/*      */     public Object2ReferenceMap.Entry<K, V> first() {
/* 1360 */       if (Object2ReferenceLinkedOpenHashMap.this.size == 0) throw new NoSuchElementException(); 
/* 1361 */       return new Object2ReferenceLinkedOpenHashMap.MapEntry(Object2ReferenceLinkedOpenHashMap.this.first);
/*      */     }
/*      */ 
/*      */     
/*      */     public Object2ReferenceMap.Entry<K, V> last() {
/* 1366 */       if (Object2ReferenceLinkedOpenHashMap.this.size == 0) throw new NoSuchElementException(); 
/* 1367 */       return new Object2ReferenceLinkedOpenHashMap.MapEntry(Object2ReferenceLinkedOpenHashMap.this.last);
/*      */     }
/*      */ 
/*      */ 
/*      */     
/*      */     public boolean contains(Object o) {
/* 1373 */       if (!(o instanceof Map.Entry)) return false; 
/* 1374 */       Map.Entry<?, ?> e = (Map.Entry<?, ?>)o;
/* 1375 */       K k = (K)e.getKey();
/* 1376 */       V v = (V)e.getValue();
/* 1377 */       if (k == null) return (Object2ReferenceLinkedOpenHashMap.this.containsNullKey && Object2ReferenceLinkedOpenHashMap.this.value[Object2ReferenceLinkedOpenHashMap.this.n] == v);
/*      */       
/* 1379 */       K[] key = Object2ReferenceLinkedOpenHashMap.this.key;
/*      */       K curr;
/*      */       int pos;
/* 1382 */       if ((curr = key[pos = HashCommon.mix(k.hashCode()) & Object2ReferenceLinkedOpenHashMap.this.mask]) == null) return false; 
/* 1383 */       if (k.equals(curr)) return (Object2ReferenceLinkedOpenHashMap.this.value[pos] == v);
/*      */       
/*      */       while (true) {
/* 1386 */         if ((curr = key[pos = pos + 1 & Object2ReferenceLinkedOpenHashMap.this.mask]) == null) return false; 
/* 1387 */         if (k.equals(curr)) return (Object2ReferenceLinkedOpenHashMap.this.value[pos] == v);
/*      */       
/*      */       } 
/*      */     }
/*      */ 
/*      */     
/*      */     public boolean remove(Object o) {
/* 1394 */       if (!(o instanceof Map.Entry)) return false; 
/* 1395 */       Map.Entry<?, ?> e = (Map.Entry<?, ?>)o;
/* 1396 */       K k = (K)e.getKey();
/* 1397 */       V v = (V)e.getValue();
/* 1398 */       if (k == null) {
/* 1399 */         if (Object2ReferenceLinkedOpenHashMap.this.containsNullKey && Object2ReferenceLinkedOpenHashMap.this.value[Object2ReferenceLinkedOpenHashMap.this.n] == v) {
/* 1400 */           Object2ReferenceLinkedOpenHashMap.this.removeNullEntry();
/* 1401 */           return true;
/*      */         } 
/* 1403 */         return false;
/*      */       } 
/*      */       
/* 1406 */       K[] key = Object2ReferenceLinkedOpenHashMap.this.key;
/*      */       K curr;
/*      */       int pos;
/* 1409 */       if ((curr = key[pos = HashCommon.mix(k.hashCode()) & Object2ReferenceLinkedOpenHashMap.this.mask]) == null) return false; 
/* 1410 */       if (curr.equals(k)) {
/* 1411 */         if (Object2ReferenceLinkedOpenHashMap.this.value[pos] == v) {
/* 1412 */           Object2ReferenceLinkedOpenHashMap.this.removeEntry(pos);
/* 1413 */           return true;
/*      */         } 
/* 1415 */         return false;
/*      */       } 
/*      */       while (true) {
/* 1418 */         if ((curr = key[pos = pos + 1 & Object2ReferenceLinkedOpenHashMap.this.mask]) == null) return false; 
/* 1419 */         if (curr.equals(k) && 
/* 1420 */           Object2ReferenceLinkedOpenHashMap.this.value[pos] == v) {
/* 1421 */           Object2ReferenceLinkedOpenHashMap.this.removeEntry(pos);
/* 1422 */           return true;
/*      */         } 
/*      */       } 
/*      */     }
/*      */ 
/*      */ 
/*      */     
/*      */     public int size() {
/* 1430 */       return Object2ReferenceLinkedOpenHashMap.this.size;
/*      */     }
/*      */ 
/*      */     
/*      */     public void clear() {
/* 1435 */       Object2ReferenceLinkedOpenHashMap.this.clear();
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
/*      */     public ObjectListIterator<Object2ReferenceMap.Entry<K, V>> iterator(Object2ReferenceMap.Entry<K, V> from) {
/* 1448 */       return new Object2ReferenceLinkedOpenHashMap.EntryIterator(from.getKey());
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public ObjectListIterator<Object2ReferenceMap.Entry<K, V>> fastIterator() {
/* 1459 */       return new Object2ReferenceLinkedOpenHashMap.FastEntryIterator();
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
/*      */     public ObjectListIterator<Object2ReferenceMap.Entry<K, V>> fastIterator(Object2ReferenceMap.Entry<K, V> from) {
/* 1472 */       return new Object2ReferenceLinkedOpenHashMap.FastEntryIterator(from.getKey());
/*      */     }
/*      */ 
/*      */ 
/*      */     
/*      */     public void forEach(Consumer<? super Object2ReferenceMap.Entry<K, V>> consumer) {
/* 1478 */       for (int i = Object2ReferenceLinkedOpenHashMap.this.size, next = Object2ReferenceLinkedOpenHashMap.this.first; i-- != 0; ) {
/* 1479 */         int curr = next;
/* 1480 */         next = (int)Object2ReferenceLinkedOpenHashMap.this.link[curr];
/* 1481 */         consumer.accept(new Object2ReferenceLinkedOpenHashMap.MapEntry(curr));
/*      */       } 
/*      */     }
/*      */ 
/*      */ 
/*      */     
/*      */     public void fastForEach(Consumer<? super Object2ReferenceMap.Entry<K, V>> consumer) {
/* 1488 */       Object2ReferenceLinkedOpenHashMap<K, V>.MapEntry entry = new Object2ReferenceLinkedOpenHashMap.MapEntry();
/* 1489 */       for (int i = Object2ReferenceLinkedOpenHashMap.this.size, next = Object2ReferenceLinkedOpenHashMap.this.first; i-- != 0; ) {
/* 1490 */         entry.index = next;
/* 1491 */         next = (int)Object2ReferenceLinkedOpenHashMap.this.link[next];
/* 1492 */         consumer.accept(entry);
/*      */       } 
/*      */     }
/*      */   }
/*      */ 
/*      */   
/*      */   public Object2ReferenceSortedMap.FastSortedEntrySet<K, V> object2ReferenceEntrySet() {
/* 1499 */     if (this.entries == null) this.entries = new MapEntrySet(); 
/* 1500 */     return this.entries;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private final class KeyIterator
/*      */     extends MapIterator<Consumer<? super K>>
/*      */     implements ObjectListIterator<K>
/*      */   {
/*      */     public KeyIterator(K k) {
/* 1513 */       super(k);
/*      */     }
/*      */ 
/*      */     
/*      */     public K previous() {
/* 1518 */       return Object2ReferenceLinkedOpenHashMap.this.key[previousEntry()];
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public KeyIterator() {}
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     final void acceptOnIndex(Consumer<? super K> action, int index) {
/* 1530 */       action.accept(Object2ReferenceLinkedOpenHashMap.this.key[index]);
/*      */     }
/*      */ 
/*      */     
/*      */     public K next() {
/* 1535 */       return Object2ReferenceLinkedOpenHashMap.this.key[nextEntry()];
/*      */     } }
/*      */   
/*      */   private final class KeySet extends AbstractObjectSortedSet<K> {
/*      */     private static final int SPLITERATOR_CHARACTERISTICS = 81;
/*      */     
/*      */     private KeySet() {}
/*      */     
/*      */     public ObjectListIterator<K> iterator(K from) {
/* 1544 */       return new Object2ReferenceLinkedOpenHashMap.KeyIterator(from);
/*      */     }
/*      */ 
/*      */     
/*      */     public ObjectListIterator<K> iterator() {
/* 1549 */       return new Object2ReferenceLinkedOpenHashMap.KeyIterator();
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public ObjectSpliterator<K> spliterator() {
/* 1559 */       return ObjectSpliterators.asSpliterator(iterator(), Size64.sizeOf(Object2ReferenceLinkedOpenHashMap.this), 81);
/*      */     }
/*      */ 
/*      */ 
/*      */     
/*      */     public void forEach(Consumer<? super K> consumer) {
/* 1565 */       for (int i = Object2ReferenceLinkedOpenHashMap.this.size, next = Object2ReferenceLinkedOpenHashMap.this.first; i-- != 0; ) {
/* 1566 */         int curr = next;
/* 1567 */         next = (int)Object2ReferenceLinkedOpenHashMap.this.link[curr];
/* 1568 */         consumer.accept(Object2ReferenceLinkedOpenHashMap.this.key[curr]);
/*      */       } 
/*      */     }
/*      */ 
/*      */     
/*      */     public int size() {
/* 1574 */       return Object2ReferenceLinkedOpenHashMap.this.size;
/*      */     }
/*      */ 
/*      */     
/*      */     public boolean contains(Object k) {
/* 1579 */       return Object2ReferenceLinkedOpenHashMap.this.containsKey(k);
/*      */     }
/*      */ 
/*      */     
/*      */     public boolean remove(Object k) {
/* 1584 */       int oldSize = Object2ReferenceLinkedOpenHashMap.this.size;
/* 1585 */       Object2ReferenceLinkedOpenHashMap.this.remove(k);
/* 1586 */       return (Object2ReferenceLinkedOpenHashMap.this.size != oldSize);
/*      */     }
/*      */ 
/*      */     
/*      */     public void clear() {
/* 1591 */       Object2ReferenceLinkedOpenHashMap.this.clear();
/*      */     }
/*      */ 
/*      */     
/*      */     public K first() {
/* 1596 */       if (Object2ReferenceLinkedOpenHashMap.this.size == 0) throw new NoSuchElementException(); 
/* 1597 */       return Object2ReferenceLinkedOpenHashMap.this.key[Object2ReferenceLinkedOpenHashMap.this.first];
/*      */     }
/*      */ 
/*      */     
/*      */     public K last() {
/* 1602 */       if (Object2ReferenceLinkedOpenHashMap.this.size == 0) throw new NoSuchElementException(); 
/* 1603 */       return Object2ReferenceLinkedOpenHashMap.this.key[Object2ReferenceLinkedOpenHashMap.this.last];
/*      */     }
/*      */ 
/*      */     
/*      */     public Comparator<? super K> comparator() {
/* 1608 */       return null;
/*      */     }
/*      */ 
/*      */     
/*      */     public ObjectSortedSet<K> tailSet(K from) {
/* 1613 */       throw new UnsupportedOperationException();
/*      */     }
/*      */ 
/*      */     
/*      */     public ObjectSortedSet<K> headSet(K to) {
/* 1618 */       throw new UnsupportedOperationException();
/*      */     }
/*      */ 
/*      */     
/*      */     public ObjectSortedSet<K> subSet(K from, K to) {
/* 1623 */       throw new UnsupportedOperationException();
/*      */     }
/*      */   }
/*      */ 
/*      */   
/*      */   public ObjectSortedSet<K> keySet() {
/* 1629 */     if (this.keys == null) this.keys = new KeySet(); 
/* 1630 */     return this.keys;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private final class ValueIterator
/*      */     extends MapIterator<Consumer<? super V>>
/*      */     implements ObjectListIterator<V>
/*      */   {
/*      */     public V previous() {
/* 1644 */       return Object2ReferenceLinkedOpenHashMap.this.value[previousEntry()];
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
/*      */     final void acceptOnIndex(Consumer<? super V> action, int index) {
/* 1656 */       action.accept(Object2ReferenceLinkedOpenHashMap.this.value[index]);
/*      */     }
/*      */ 
/*      */     
/*      */     public V next() {
/* 1661 */       return Object2ReferenceLinkedOpenHashMap.this.value[nextEntry()];
/*      */     }
/*      */   }
/*      */ 
/*      */   
/*      */   public ReferenceCollection<V> values() {
/* 1667 */     if (this.values == null) this.values = new AbstractReferenceCollection<V>()
/*      */         {
/*      */           private static final int SPLITERATOR_CHARACTERISTICS = 80;
/*      */           
/*      */           public ObjectIterator<V> iterator() {
/* 1672 */             return new Object2ReferenceLinkedOpenHashMap.ValueIterator();
/*      */           }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */           
/*      */           public ObjectSpliterator<V> spliterator() {
/* 1682 */             return ObjectSpliterators.asSpliterator(iterator(), Size64.sizeOf(Object2ReferenceLinkedOpenHashMap.this), 80);
/*      */           }
/*      */ 
/*      */ 
/*      */           
/*      */           public void forEach(Consumer<? super V> consumer) {
/* 1688 */             for (int i = Object2ReferenceLinkedOpenHashMap.this.size, next = Object2ReferenceLinkedOpenHashMap.this.first; i-- != 0; ) {
/* 1689 */               int curr = next;
/* 1690 */               next = (int)Object2ReferenceLinkedOpenHashMap.this.link[curr];
/* 1691 */               consumer.accept(Object2ReferenceLinkedOpenHashMap.this.value[curr]);
/*      */             } 
/*      */           }
/*      */ 
/*      */           
/*      */           public int size() {
/* 1697 */             return Object2ReferenceLinkedOpenHashMap.this.size;
/*      */           }
/*      */ 
/*      */           
/*      */           public boolean contains(Object v) {
/* 1702 */             return Object2ReferenceLinkedOpenHashMap.this.containsValue(v);
/*      */           }
/*      */ 
/*      */           
/*      */           public void clear() {
/* 1707 */             Object2ReferenceLinkedOpenHashMap.this.clear();
/*      */           }
/*      */         }; 
/* 1710 */     return this.values;
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
/* 1727 */     return trim(this.size);
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
/* 1749 */     int l = HashCommon.nextPowerOfTwo((int)Math.ceil((n / this.f)));
/* 1750 */     if (l >= this.n || this.size > HashCommon.maxFill(l, this.f)) return true; 
/*      */     try {
/* 1752 */       rehash(l);
/* 1753 */     } catch (OutOfMemoryError cantDoIt) {
/* 1754 */       return false;
/*      */     } 
/* 1756 */     return true;
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
/* 1771 */     K[] key = this.key;
/* 1772 */     V[] value = this.value;
/* 1773 */     int mask = newN - 1;
/* 1774 */     K[] newKey = (K[])new Object[newN + 1];
/* 1775 */     V[] newValue = (V[])new Object[newN + 1];
/* 1776 */     int i = this.first, prev = -1, newPrev = -1;
/* 1777 */     long[] link = this.link;
/* 1778 */     long[] newLink = new long[newN + 1];
/* 1779 */     this.first = -1;
/* 1780 */     for (int j = this.size; j-- != 0; ) {
/* 1781 */       int pos; if (key[i] == null) { pos = newN; }
/*      */       else
/* 1783 */       { pos = HashCommon.mix(key[i].hashCode()) & mask;
/* 1784 */         for (; newKey[pos] != null; pos = pos + 1 & mask); }
/*      */       
/* 1786 */       newKey[pos] = key[i];
/* 1787 */       newValue[pos] = value[i];
/* 1788 */       if (prev != -1) {
/* 1789 */         newLink[newPrev] = newLink[newPrev] ^ (newLink[newPrev] ^ pos & 0xFFFFFFFFL) & 0xFFFFFFFFL;
/* 1790 */         newLink[pos] = newLink[pos] ^ (newLink[pos] ^ (newPrev & 0xFFFFFFFFL) << 32L) & 0xFFFFFFFF00000000L;
/* 1791 */         newPrev = pos;
/*      */       } else {
/* 1793 */         newPrev = this.first = pos;
/*      */         
/* 1795 */         newLink[pos] = -1L;
/*      */       } 
/* 1797 */       int t = i;
/* 1798 */       i = (int)link[i];
/* 1799 */       prev = t;
/*      */     } 
/* 1801 */     this.link = newLink;
/* 1802 */     this.last = newPrev;
/* 1803 */     if (newPrev != -1)
/*      */     {
/* 1805 */       newLink[newPrev] = newLink[newPrev] | 0xFFFFFFFFL; } 
/* 1806 */     this.n = newN;
/* 1807 */     this.mask = mask;
/* 1808 */     this.maxFill = HashCommon.maxFill(this.n, this.f);
/* 1809 */     this.key = newKey;
/* 1810 */     this.value = newValue;
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
/*      */   public Object2ReferenceLinkedOpenHashMap<K, V> clone() {
/*      */     Object2ReferenceLinkedOpenHashMap<K, V> c;
/*      */     try {
/* 1827 */       c = (Object2ReferenceLinkedOpenHashMap<K, V>)super.clone();
/* 1828 */     } catch (CloneNotSupportedException cantHappen) {
/* 1829 */       throw new InternalError();
/*      */     } 
/* 1831 */     c.keys = null;
/* 1832 */     c.values = null;
/* 1833 */     c.entries = null;
/* 1834 */     c.containsNullKey = this.containsNullKey;
/* 1835 */     c.key = (K[])this.key.clone();
/* 1836 */     c.value = (V[])this.value.clone();
/* 1837 */     c.link = (long[])this.link.clone();
/* 1838 */     return c;
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
/* 1852 */     int h = 0;
/* 1853 */     for (int j = realSize(), i = 0, t = 0; j-- != 0; ) {
/* 1854 */       for (; this.key[i] == null; i++);
/* 1855 */       if (this != this.key[i]) t = this.key[i].hashCode(); 
/* 1856 */       if (this != this.value[i]) t ^= (this.value[i] == null) ? 0 : System.identityHashCode(this.value[i]); 
/* 1857 */       h += t;
/* 1858 */       i++;
/*      */     } 
/*      */     
/* 1861 */     if (this.containsNullKey) h += (this.value[this.n] == null) ? 0 : System.identityHashCode(this.value[this.n]); 
/* 1862 */     return h;
/*      */   }
/*      */   
/*      */   private void writeObject(ObjectOutputStream s) throws IOException {
/* 1866 */     K[] key = this.key;
/* 1867 */     V[] value = this.value;
/* 1868 */     EntryIterator i = new EntryIterator();
/* 1869 */     s.defaultWriteObject();
/* 1870 */     for (int j = this.size; j-- != 0; ) {
/* 1871 */       int e = i.nextEntry();
/* 1872 */       s.writeObject(key[e]);
/* 1873 */       s.writeObject(value[e]);
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   private void readObject(ObjectInputStream s) throws IOException, ClassNotFoundException {
/* 1879 */     s.defaultReadObject();
/* 1880 */     this.n = HashCommon.arraySize(this.size, this.f);
/* 1881 */     this.maxFill = HashCommon.maxFill(this.n, this.f);
/* 1882 */     this.mask = this.n - 1;
/* 1883 */     K[] key = this.key = (K[])new Object[this.n + 1];
/* 1884 */     V[] value = this.value = (V[])new Object[this.n + 1];
/* 1885 */     long[] link = this.link = new long[this.n + 1];
/* 1886 */     int prev = -1;
/* 1887 */     this.first = this.last = -1;
/*      */ 
/*      */     
/* 1890 */     for (int i = this.size; i-- != 0; ) {
/* 1891 */       int pos; K k = (K)s.readObject();
/* 1892 */       V v = (V)s.readObject();
/* 1893 */       if (k == null) {
/* 1894 */         pos = this.n;
/* 1895 */         this.containsNullKey = true;
/*      */       } else {
/* 1897 */         pos = HashCommon.mix(k.hashCode()) & this.mask;
/* 1898 */         for (; key[pos] != null; pos = pos + 1 & this.mask);
/*      */       } 
/* 1900 */       key[pos] = k;
/* 1901 */       value[pos] = v;
/* 1902 */       if (this.first != -1) {
/* 1903 */         link[prev] = link[prev] ^ (link[prev] ^ pos & 0xFFFFFFFFL) & 0xFFFFFFFFL;
/* 1904 */         link[pos] = link[pos] ^ (link[pos] ^ (prev & 0xFFFFFFFFL) << 32L) & 0xFFFFFFFF00000000L;
/* 1905 */         prev = pos; continue;
/*      */       } 
/* 1907 */       prev = this.first = pos;
/*      */       
/* 1909 */       link[pos] = link[pos] | 0xFFFFFFFF00000000L;
/*      */     } 
/*      */     
/* 1912 */     this.last = prev;
/* 1913 */     if (prev != -1)
/*      */     {
/* 1915 */       link[prev] = link[prev] | 0xFFFFFFFFL;
/*      */     }
/*      */   }
/*      */   
/*      */   private void checkTable() {}
/*      */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\i\\unimi\dsi\fastutil\objects\Object2ReferenceLinkedOpenHashMap.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */