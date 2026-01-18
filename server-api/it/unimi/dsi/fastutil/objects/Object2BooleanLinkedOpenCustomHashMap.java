/*      */ package it.unimi.dsi.fastutil.objects;
/*      */ 
/*      */ import it.unimi.dsi.fastutil.Hash;
/*      */ import it.unimi.dsi.fastutil.HashCommon;
/*      */ import it.unimi.dsi.fastutil.Size64;
/*      */ import it.unimi.dsi.fastutil.booleans.AbstractBooleanCollection;
/*      */ import it.unimi.dsi.fastutil.booleans.BooleanCollection;
/*      */ import it.unimi.dsi.fastutil.booleans.BooleanConsumer;
/*      */ import it.unimi.dsi.fastutil.booleans.BooleanIterator;
/*      */ import it.unimi.dsi.fastutil.booleans.BooleanListIterator;
/*      */ import it.unimi.dsi.fastutil.booleans.BooleanSpliterator;
/*      */ import it.unimi.dsi.fastutil.booleans.BooleanSpliterators;
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
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ public class Object2BooleanLinkedOpenCustomHashMap<K>
/*      */   extends AbstractObject2BooleanSortedMap<K>
/*      */   implements Serializable, Cloneable, Hash
/*      */ {
/*      */   private static final long serialVersionUID = 0L;
/*      */   private static final boolean ASSERTS = false;
/*      */   protected transient K[] key;
/*      */   protected transient boolean[] value;
/*      */   protected transient int mask;
/*      */   protected transient boolean containsNullKey;
/*      */   protected Hash.Strategy<? super K> strategy;
/*  104 */   protected transient int first = -1;
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*  109 */   protected transient int last = -1;
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
/*      */   protected transient Object2BooleanSortedMap.FastSortedEntrySet<K> entries;
/*      */ 
/*      */   
/*      */   protected transient ObjectSortedSet<K> keys;
/*      */ 
/*      */   
/*      */   protected transient BooleanCollection values;
/*      */ 
/*      */ 
/*      */   
/*      */   public Object2BooleanLinkedOpenCustomHashMap(int expected, float f, Hash.Strategy<? super K> strategy) {
/*  145 */     this.strategy = strategy;
/*  146 */     if (f <= 0.0F || f >= 1.0F) throw new IllegalArgumentException("Load factor must be greater than 0 and smaller than 1"); 
/*  147 */     if (expected < 0) throw new IllegalArgumentException("The expected number of elements must be nonnegative"); 
/*  148 */     this.f = f;
/*  149 */     this.minN = this.n = HashCommon.arraySize(expected, f);
/*  150 */     this.mask = this.n - 1;
/*  151 */     this.maxFill = HashCommon.maxFill(this.n, f);
/*  152 */     this.key = (K[])new Object[this.n + 1];
/*  153 */     this.value = new boolean[this.n + 1];
/*  154 */     this.link = new long[this.n + 1];
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Object2BooleanLinkedOpenCustomHashMap(int expected, Hash.Strategy<? super K> strategy) {
/*  164 */     this(expected, 0.75F, strategy);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Object2BooleanLinkedOpenCustomHashMap(Hash.Strategy<? super K> strategy) {
/*  174 */     this(16, 0.75F, strategy);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Object2BooleanLinkedOpenCustomHashMap(Map<? extends K, ? extends Boolean> m, float f, Hash.Strategy<? super K> strategy) {
/*  185 */     this(m.size(), f, strategy);
/*  186 */     putAll(m);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Object2BooleanLinkedOpenCustomHashMap(Map<? extends K, ? extends Boolean> m, Hash.Strategy<? super K> strategy) {
/*  196 */     this(m, 0.75F, strategy);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Object2BooleanLinkedOpenCustomHashMap(Object2BooleanMap<K> m, float f, Hash.Strategy<? super K> strategy) {
/*  207 */     this(m.size(), f, strategy);
/*  208 */     putAll(m);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Object2BooleanLinkedOpenCustomHashMap(Object2BooleanMap<K> m, Hash.Strategy<? super K> strategy) {
/*  219 */     this(m, 0.75F, strategy);
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
/*      */   public Object2BooleanLinkedOpenCustomHashMap(K[] k, boolean[] v, float f, Hash.Strategy<? super K> strategy) {
/*  232 */     this(k.length, f, strategy);
/*  233 */     if (k.length != v.length) throw new IllegalArgumentException("The key array and the value array have different lengths (" + k.length + " and " + v.length + ")"); 
/*  234 */     for (int i = 0; i < k.length; ) { put(k[i], v[i]); i++; }
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
/*      */   public Object2BooleanLinkedOpenCustomHashMap(K[] k, boolean[] v, Hash.Strategy<? super K> strategy) {
/*  247 */     this(k, v, 0.75F, strategy);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Hash.Strategy<? super K> strategy() {
/*  256 */     return this.strategy;
/*      */   }
/*      */   
/*      */   private int realSize() {
/*  260 */     return this.containsNullKey ? (this.size - 1) : this.size;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void ensureCapacity(int capacity) {
/*  270 */     int needed = HashCommon.arraySize(capacity, this.f);
/*  271 */     if (needed > this.n) rehash(needed); 
/*      */   }
/*      */   
/*      */   private void tryCapacity(long capacity) {
/*  275 */     int needed = (int)Math.min(1073741824L, Math.max(2L, HashCommon.nextPowerOfTwo((long)Math.ceil(((float)capacity / this.f)))));
/*  276 */     if (needed > this.n) rehash(needed); 
/*      */   }
/*      */   
/*      */   private boolean removeEntry(int pos) {
/*  280 */     boolean oldValue = this.value[pos];
/*  281 */     this.size--;
/*  282 */     fixPointers(pos);
/*  283 */     shiftKeys(pos);
/*  284 */     if (this.n > this.minN && this.size < this.maxFill / 4 && this.n > 16) rehash(this.n / 2); 
/*  285 */     return oldValue;
/*      */   }
/*      */   
/*      */   private boolean removeNullEntry() {
/*  289 */     this.containsNullKey = false;
/*  290 */     this.key[this.n] = null;
/*  291 */     boolean oldValue = this.value[this.n];
/*  292 */     this.size--;
/*  293 */     fixPointers(this.n);
/*  294 */     if (this.n > this.minN && this.size < this.maxFill / 4 && this.n > 16) rehash(this.n / 2); 
/*  295 */     return oldValue;
/*      */   }
/*      */ 
/*      */   
/*      */   public void putAll(Map<? extends K, ? extends Boolean> m) {
/*  300 */     if (this.f <= 0.5D) { ensureCapacity(m.size()); }
/*  301 */     else { tryCapacity((size() + m.size())); }
/*      */     
/*  303 */     super.putAll(m);
/*      */   }
/*      */ 
/*      */   
/*      */   private int find(K k) {
/*  308 */     if (this.strategy.equals(k, null)) return this.containsNullKey ? this.n : -(this.n + 1);
/*      */     
/*  310 */     K[] key = this.key;
/*      */     K curr;
/*      */     int pos;
/*  313 */     if ((curr = key[pos = HashCommon.mix(this.strategy.hashCode(k)) & this.mask]) == null) return -(pos + 1); 
/*  314 */     if (this.strategy.equals(k, curr)) return pos;
/*      */     
/*      */     while (true) {
/*  317 */       if ((curr = key[pos = pos + 1 & this.mask]) == null) return -(pos + 1); 
/*  318 */       if (this.strategy.equals(k, curr)) return pos; 
/*      */     } 
/*      */   }
/*      */   
/*      */   private void insert(int pos, K k, boolean v) {
/*  323 */     if (pos == this.n) this.containsNullKey = true; 
/*  324 */     this.key[pos] = k;
/*  325 */     this.value[pos] = v;
/*  326 */     if (this.size == 0) {
/*  327 */       this.first = this.last = pos;
/*      */       
/*  329 */       this.link[pos] = -1L;
/*      */     } else {
/*  331 */       this.link[this.last] = this.link[this.last] ^ (this.link[this.last] ^ pos & 0xFFFFFFFFL) & 0xFFFFFFFFL;
/*  332 */       this.link[pos] = (this.last & 0xFFFFFFFFL) << 32L | 0xFFFFFFFFL;
/*  333 */       this.last = pos;
/*      */     } 
/*  335 */     if (this.size++ >= this.maxFill) rehash(HashCommon.arraySize(this.size + 1, this.f));
/*      */   
/*      */   }
/*      */ 
/*      */   
/*      */   public boolean put(K k, boolean v) {
/*  341 */     int pos = find(k);
/*  342 */     if (pos < 0) {
/*  343 */       insert(-pos - 1, k, v);
/*  344 */       return this.defRetValue;
/*      */     } 
/*  346 */     boolean oldValue = this.value[pos];
/*  347 */     this.value[pos] = v;
/*  348 */     return oldValue;
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
/*  361 */     K[] key = this.key; while (true) {
/*      */       K curr; int last;
/*  363 */       pos = (last = pos) + 1 & this.mask;
/*      */       while (true) {
/*  365 */         if ((curr = key[pos]) == null) {
/*  366 */           key[last] = null;
/*      */           return;
/*      */         } 
/*  369 */         int slot = HashCommon.mix(this.strategy.hashCode(curr)) & this.mask;
/*  370 */         if ((last <= pos) ? (last >= slot || slot > pos) : (last >= slot && slot > pos))
/*  371 */           break;  pos = pos + 1 & this.mask;
/*      */       } 
/*  373 */       key[last] = curr;
/*  374 */       this.value[last] = this.value[pos];
/*  375 */       fixPointers(pos, last);
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean removeBoolean(Object k) {
/*  382 */     if (this.strategy.equals(k, null)) {
/*  383 */       if (this.containsNullKey) return removeNullEntry(); 
/*  384 */       return this.defRetValue;
/*      */     } 
/*      */     
/*  387 */     K[] key = this.key;
/*      */     K curr;
/*      */     int pos;
/*  390 */     if ((curr = key[pos = HashCommon.mix(this.strategy.hashCode(k)) & this.mask]) == null) return this.defRetValue; 
/*  391 */     if (this.strategy.equals(k, curr)) return removeEntry(pos); 
/*      */     while (true) {
/*  393 */       if ((curr = key[pos = pos + 1 & this.mask]) == null) return this.defRetValue; 
/*  394 */       if (this.strategy.equals(k, curr)) return removeEntry(pos); 
/*      */     } 
/*      */   }
/*      */   
/*      */   private boolean setValue(int pos, boolean v) {
/*  399 */     boolean oldValue = this.value[pos];
/*  400 */     this.value[pos] = v;
/*  401 */     return oldValue;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean removeFirstBoolean() {
/*  411 */     if (this.size == 0) throw new NoSuchElementException(); 
/*  412 */     int pos = this.first;
/*      */     
/*  414 */     if (this.size == 1) { this.first = this.last = -1; }
/*      */     else
/*  416 */     { this.first = (int)this.link[pos];
/*  417 */       if (0 <= this.first)
/*      */       {
/*  419 */         this.link[this.first] = this.link[this.first] | 0xFFFFFFFF00000000L;
/*      */       } }
/*      */     
/*  422 */     this.size--;
/*  423 */     boolean v = this.value[pos];
/*  424 */     if (pos == this.n)
/*  425 */     { this.containsNullKey = false;
/*  426 */       this.key[this.n] = null; }
/*  427 */     else { shiftKeys(pos); }
/*  428 */      if (this.n > this.minN && this.size < this.maxFill / 4 && this.n > 16) rehash(this.n / 2); 
/*  429 */     return v;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean removeLastBoolean() {
/*  439 */     if (this.size == 0) throw new NoSuchElementException(); 
/*  440 */     int pos = this.last;
/*      */     
/*  442 */     if (this.size == 1) { this.first = this.last = -1; }
/*      */     else
/*  444 */     { this.last = (int)(this.link[pos] >>> 32L);
/*  445 */       if (0 <= this.last)
/*      */       {
/*  447 */         this.link[this.last] = this.link[this.last] | 0xFFFFFFFFL;
/*      */       } }
/*      */     
/*  450 */     this.size--;
/*  451 */     boolean v = this.value[pos];
/*  452 */     if (pos == this.n)
/*  453 */     { this.containsNullKey = false;
/*  454 */       this.key[this.n] = null; }
/*  455 */     else { shiftKeys(pos); }
/*  456 */      if (this.n > this.minN && this.size < this.maxFill / 4 && this.n > 16) rehash(this.n / 2); 
/*  457 */     return v;
/*      */   }
/*      */   
/*      */   private void moveIndexToFirst(int i) {
/*  461 */     if (this.size == 1 || this.first == i)
/*  462 */       return;  if (this.last == i) {
/*  463 */       this.last = (int)(this.link[i] >>> 32L);
/*      */       
/*  465 */       this.link[this.last] = this.link[this.last] | 0xFFFFFFFFL;
/*      */     } else {
/*  467 */       long linki = this.link[i];
/*  468 */       int prev = (int)(linki >>> 32L);
/*  469 */       int next = (int)linki;
/*  470 */       this.link[prev] = this.link[prev] ^ (this.link[prev] ^ linki & 0xFFFFFFFFL) & 0xFFFFFFFFL;
/*  471 */       this.link[next] = this.link[next] ^ (this.link[next] ^ linki & 0xFFFFFFFF00000000L) & 0xFFFFFFFF00000000L;
/*      */     } 
/*  473 */     this.link[this.first] = this.link[this.first] ^ (this.link[this.first] ^ (i & 0xFFFFFFFFL) << 32L) & 0xFFFFFFFF00000000L;
/*  474 */     this.link[i] = 0xFFFFFFFF00000000L | this.first & 0xFFFFFFFFL;
/*  475 */     this.first = i;
/*      */   }
/*      */   
/*      */   private void moveIndexToLast(int i) {
/*  479 */     if (this.size == 1 || this.last == i)
/*  480 */       return;  if (this.first == i) {
/*  481 */       this.first = (int)this.link[i];
/*      */       
/*  483 */       this.link[this.first] = this.link[this.first] | 0xFFFFFFFF00000000L;
/*      */     } else {
/*  485 */       long linki = this.link[i];
/*  486 */       int prev = (int)(linki >>> 32L);
/*  487 */       int next = (int)linki;
/*  488 */       this.link[prev] = this.link[prev] ^ (this.link[prev] ^ linki & 0xFFFFFFFFL) & 0xFFFFFFFFL;
/*  489 */       this.link[next] = this.link[next] ^ (this.link[next] ^ linki & 0xFFFFFFFF00000000L) & 0xFFFFFFFF00000000L;
/*      */     } 
/*  491 */     this.link[this.last] = this.link[this.last] ^ (this.link[this.last] ^ i & 0xFFFFFFFFL) & 0xFFFFFFFFL;
/*  492 */     this.link[i] = (this.last & 0xFFFFFFFFL) << 32L | 0xFFFFFFFFL;
/*  493 */     this.last = i;
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
/*      */   public boolean getAndMoveToFirst(K k) {
/*  505 */     if (this.strategy.equals(k, null)) {
/*  506 */       if (this.containsNullKey) {
/*  507 */         moveIndexToFirst(this.n);
/*  508 */         return this.value[this.n];
/*      */       } 
/*  510 */       return this.defRetValue;
/*      */     } 
/*      */     
/*  513 */     K[] key = this.key;
/*      */     K curr;
/*      */     int pos;
/*  516 */     if ((curr = key[pos = HashCommon.mix(this.strategy.hashCode(k)) & this.mask]) == null) return this.defRetValue; 
/*  517 */     if (this.strategy.equals(k, curr)) {
/*  518 */       moveIndexToFirst(pos);
/*  519 */       return this.value[pos];
/*      */     } 
/*      */     
/*      */     while (true) {
/*  523 */       if ((curr = key[pos = pos + 1 & this.mask]) == null) return this.defRetValue; 
/*  524 */       if (this.strategy.equals(k, curr)) {
/*  525 */         moveIndexToFirst(pos);
/*  526 */         return this.value[pos];
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
/*      */   public boolean getAndMoveToLast(K k) {
/*  540 */     if (this.strategy.equals(k, null)) {
/*  541 */       if (this.containsNullKey) {
/*  542 */         moveIndexToLast(this.n);
/*  543 */         return this.value[this.n];
/*      */       } 
/*  545 */       return this.defRetValue;
/*      */     } 
/*      */     
/*  548 */     K[] key = this.key;
/*      */     K curr;
/*      */     int pos;
/*  551 */     if ((curr = key[pos = HashCommon.mix(this.strategy.hashCode(k)) & this.mask]) == null) return this.defRetValue; 
/*  552 */     if (this.strategy.equals(k, curr)) {
/*  553 */       moveIndexToLast(pos);
/*  554 */       return this.value[pos];
/*      */     } 
/*      */     
/*      */     while (true) {
/*  558 */       if ((curr = key[pos = pos + 1 & this.mask]) == null) return this.defRetValue; 
/*  559 */       if (this.strategy.equals(k, curr)) {
/*  560 */         moveIndexToLast(pos);
/*  561 */         return this.value[pos];
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
/*      */   public boolean putAndMoveToFirst(K k, boolean v) {
/*      */     int pos;
/*  577 */     if (this.strategy.equals(k, null)) {
/*  578 */       if (this.containsNullKey) {
/*  579 */         moveIndexToFirst(this.n);
/*  580 */         return setValue(this.n, v);
/*      */       } 
/*  582 */       this.containsNullKey = true;
/*  583 */       pos = this.n;
/*      */     } else {
/*      */       
/*  586 */       K[] key = this.key;
/*      */       K curr;
/*  588 */       if ((curr = key[pos = HashCommon.mix(this.strategy.hashCode(k)) & this.mask]) != null) {
/*  589 */         if (this.strategy.equals(curr, k)) {
/*  590 */           moveIndexToFirst(pos);
/*  591 */           return setValue(pos, v);
/*      */         } 
/*  593 */         while ((curr = key[pos = pos + 1 & this.mask]) != null) { if (this.strategy.equals(curr, k)) {
/*  594 */             moveIndexToFirst(pos);
/*  595 */             return setValue(pos, v);
/*      */           }  }
/*      */       
/*      */       } 
/*  599 */     }  this.key[pos] = k;
/*  600 */     this.value[pos] = v;
/*  601 */     if (this.size == 0) {
/*  602 */       this.first = this.last = pos;
/*      */       
/*  604 */       this.link[pos] = -1L;
/*      */     } else {
/*  606 */       this.link[this.first] = this.link[this.first] ^ (this.link[this.first] ^ (pos & 0xFFFFFFFFL) << 32L) & 0xFFFFFFFF00000000L;
/*  607 */       this.link[pos] = 0xFFFFFFFF00000000L | this.first & 0xFFFFFFFFL;
/*  608 */       this.first = pos;
/*      */     } 
/*  610 */     if (this.size++ >= this.maxFill) rehash(HashCommon.arraySize(this.size, this.f));
/*      */     
/*  612 */     return this.defRetValue;
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
/*      */   public boolean putAndMoveToLast(K k, boolean v) {
/*      */     int pos;
/*  626 */     if (this.strategy.equals(k, null)) {
/*  627 */       if (this.containsNullKey) {
/*  628 */         moveIndexToLast(this.n);
/*  629 */         return setValue(this.n, v);
/*      */       } 
/*  631 */       this.containsNullKey = true;
/*  632 */       pos = this.n;
/*      */     } else {
/*      */       
/*  635 */       K[] key = this.key;
/*      */       K curr;
/*  637 */       if ((curr = key[pos = HashCommon.mix(this.strategy.hashCode(k)) & this.mask]) != null) {
/*  638 */         if (this.strategy.equals(curr, k)) {
/*  639 */           moveIndexToLast(pos);
/*  640 */           return setValue(pos, v);
/*      */         } 
/*  642 */         while ((curr = key[pos = pos + 1 & this.mask]) != null) { if (this.strategy.equals(curr, k)) {
/*  643 */             moveIndexToLast(pos);
/*  644 */             return setValue(pos, v);
/*      */           }  }
/*      */       
/*      */       } 
/*  648 */     }  this.key[pos] = k;
/*  649 */     this.value[pos] = v;
/*  650 */     if (this.size == 0) {
/*  651 */       this.first = this.last = pos;
/*      */       
/*  653 */       this.link[pos] = -1L;
/*      */     } else {
/*  655 */       this.link[this.last] = this.link[this.last] ^ (this.link[this.last] ^ pos & 0xFFFFFFFFL) & 0xFFFFFFFFL;
/*  656 */       this.link[pos] = (this.last & 0xFFFFFFFFL) << 32L | 0xFFFFFFFFL;
/*  657 */       this.last = pos;
/*      */     } 
/*  659 */     if (this.size++ >= this.maxFill) rehash(HashCommon.arraySize(this.size, this.f));
/*      */     
/*  661 */     return this.defRetValue;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean getBoolean(Object k) {
/*  667 */     if (this.strategy.equals(k, null)) return this.containsNullKey ? this.value[this.n] : this.defRetValue;
/*      */     
/*  669 */     K[] key = this.key;
/*      */     K curr;
/*      */     int pos;
/*  672 */     if ((curr = key[pos = HashCommon.mix(this.strategy.hashCode(k)) & this.mask]) == null) return this.defRetValue; 
/*  673 */     if (this.strategy.equals(k, curr)) return this.value[pos];
/*      */     
/*      */     while (true) {
/*  676 */       if ((curr = key[pos = pos + 1 & this.mask]) == null) return this.defRetValue; 
/*  677 */       if (this.strategy.equals(k, curr)) return this.value[pos];
/*      */     
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   public boolean containsKey(Object k) {
/*  684 */     if (this.strategy.equals(k, null)) return this.containsNullKey;
/*      */     
/*  686 */     K[] key = this.key;
/*      */     K curr;
/*      */     int pos;
/*  689 */     if ((curr = key[pos = HashCommon.mix(this.strategy.hashCode(k)) & this.mask]) == null) return false; 
/*  690 */     if (this.strategy.equals(k, curr)) return true;
/*      */     
/*      */     while (true) {
/*  693 */       if ((curr = key[pos = pos + 1 & this.mask]) == null) return false; 
/*  694 */       if (this.strategy.equals(k, curr)) return true;
/*      */     
/*      */     } 
/*      */   }
/*      */   
/*      */   public boolean containsValue(boolean v) {
/*  700 */     boolean[] value = this.value;
/*  701 */     K[] key = this.key;
/*  702 */     if (this.containsNullKey && value[this.n] == v) return true; 
/*  703 */     for (int i = this.n; i-- != 0;) { if (key[i] != null && value[i] == v) return true;  }
/*  704 */      return false;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean getOrDefault(Object k, boolean defaultValue) {
/*  711 */     if (this.strategy.equals(k, null)) return this.containsNullKey ? this.value[this.n] : defaultValue;
/*      */     
/*  713 */     K[] key = this.key;
/*      */     K curr;
/*      */     int pos;
/*  716 */     if ((curr = key[pos = HashCommon.mix(this.strategy.hashCode(k)) & this.mask]) == null) return defaultValue; 
/*  717 */     if (this.strategy.equals(k, curr)) return this.value[pos];
/*      */     
/*      */     while (true) {
/*  720 */       if ((curr = key[pos = pos + 1 & this.mask]) == null) return defaultValue; 
/*  721 */       if (this.strategy.equals(k, curr)) return this.value[pos];
/*      */     
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   public boolean putIfAbsent(K k, boolean v) {
/*  728 */     int pos = find(k);
/*  729 */     if (pos >= 0) return this.value[pos]; 
/*  730 */     insert(-pos - 1, k, v);
/*  731 */     return this.defRetValue;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean remove(Object k, boolean v) {
/*  738 */     if (this.strategy.equals(k, null)) {
/*  739 */       if (this.containsNullKey && v == this.value[this.n]) {
/*  740 */         removeNullEntry();
/*  741 */         return true;
/*      */       } 
/*  743 */       return false;
/*      */     } 
/*      */     
/*  746 */     K[] key = this.key;
/*      */     K curr;
/*      */     int pos;
/*  749 */     if ((curr = key[pos = HashCommon.mix(this.strategy.hashCode(k)) & this.mask]) == null) return false; 
/*  750 */     if (this.strategy.equals(k, curr) && v == this.value[pos]) {
/*  751 */       removeEntry(pos);
/*  752 */       return true;
/*      */     } 
/*      */     while (true) {
/*  755 */       if ((curr = key[pos = pos + 1 & this.mask]) == null) return false; 
/*  756 */       if (this.strategy.equals(k, curr) && v == this.value[pos]) {
/*  757 */         removeEntry(pos);
/*  758 */         return true;
/*      */       } 
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean replace(K k, boolean oldValue, boolean v) {
/*  766 */     int pos = find(k);
/*  767 */     if (pos < 0 || oldValue != this.value[pos]) return false; 
/*  768 */     this.value[pos] = v;
/*  769 */     return true;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean replace(K k, boolean v) {
/*  775 */     int pos = find(k);
/*  776 */     if (pos < 0) return this.defRetValue; 
/*  777 */     boolean oldValue = this.value[pos];
/*  778 */     this.value[pos] = v;
/*  779 */     return oldValue;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean computeIfAbsent(K k, Predicate<? super K> mappingFunction) {
/*  785 */     Objects.requireNonNull(mappingFunction);
/*  786 */     int pos = find(k);
/*  787 */     if (pos >= 0) return this.value[pos]; 
/*  788 */     boolean newValue = mappingFunction.test(k);
/*  789 */     insert(-pos - 1, k, newValue);
/*  790 */     return newValue;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean computeIfAbsent(K key, Object2BooleanFunction<? super K> mappingFunction) {
/*  796 */     Objects.requireNonNull(mappingFunction);
/*  797 */     int pos = find(key);
/*  798 */     if (pos >= 0) return this.value[pos]; 
/*  799 */     if (!mappingFunction.containsKey(key)) return this.defRetValue; 
/*  800 */     boolean newValue = mappingFunction.getBoolean(key);
/*  801 */     insert(-pos - 1, key, newValue);
/*  802 */     return newValue;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean computeBooleanIfPresent(K k, BiFunction<? super K, ? super Boolean, ? extends Boolean> remappingFunction) {
/*  808 */     Objects.requireNonNull(remappingFunction);
/*  809 */     int pos = find(k);
/*  810 */     if (pos < 0) return this.defRetValue; 
/*  811 */     Boolean newValue = remappingFunction.apply(k, Boolean.valueOf(this.value[pos]));
/*  812 */     if (newValue == null) {
/*  813 */       if (this.strategy.equals(k, null)) { removeNullEntry(); }
/*  814 */       else { removeEntry(pos); }
/*  815 */        return this.defRetValue;
/*      */     } 
/*  817 */     this.value[pos] = newValue.booleanValue(); return newValue.booleanValue();
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean computeBoolean(K k, BiFunction<? super K, ? super Boolean, ? extends Boolean> remappingFunction) {
/*  823 */     Objects.requireNonNull(remappingFunction);
/*  824 */     int pos = find(k);
/*  825 */     Boolean newValue = remappingFunction.apply(k, (pos >= 0) ? Boolean.valueOf(this.value[pos]) : null);
/*  826 */     if (newValue == null) {
/*  827 */       if (pos >= 0)
/*  828 */         if (this.strategy.equals(k, null)) { removeNullEntry(); }
/*  829 */         else { removeEntry(pos); }
/*      */          
/*  831 */       return this.defRetValue;
/*      */     } 
/*  833 */     boolean newVal = newValue.booleanValue();
/*  834 */     if (pos < 0) {
/*  835 */       insert(-pos - 1, k, newVal);
/*  836 */       return newVal;
/*      */     } 
/*  838 */     this.value[pos] = newVal; return newVal;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean merge(K k, boolean v, BiFunction<? super Boolean, ? super Boolean, ? extends Boolean> remappingFunction) {
/*  844 */     Objects.requireNonNull(remappingFunction);
/*      */     
/*  846 */     int pos = find(k);
/*  847 */     if (pos < 0) {
/*  848 */       if (pos < 0) { insert(-pos - 1, k, v); }
/*  849 */       else { this.value[pos] = v; }
/*  850 */        return v;
/*      */     } 
/*  852 */     Boolean newValue = remappingFunction.apply(Boolean.valueOf(this.value[pos]), Boolean.valueOf(v));
/*  853 */     if (newValue == null) {
/*  854 */       if (this.strategy.equals(k, null)) { removeNullEntry(); }
/*  855 */       else { removeEntry(pos); }
/*  856 */        return this.defRetValue;
/*      */     } 
/*  858 */     this.value[pos] = newValue.booleanValue(); return newValue.booleanValue();
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
/*  869 */     if (this.size == 0)
/*  870 */       return;  this.size = 0;
/*  871 */     this.containsNullKey = false;
/*  872 */     Arrays.fill((Object[])this.key, (Object)null);
/*  873 */     this.first = this.last = -1;
/*      */   }
/*      */ 
/*      */   
/*      */   public int size() {
/*  878 */     return this.size;
/*      */   }
/*      */ 
/*      */   
/*      */   public boolean isEmpty() {
/*  883 */     return (this.size == 0);
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   final class MapEntry
/*      */     implements Object2BooleanMap.Entry<K>, Map.Entry<K, Boolean>, ObjectBooleanPair<K>
/*      */   {
/*      */     int index;
/*      */ 
/*      */ 
/*      */     
/*      */     MapEntry(int index) {
/*  896 */       this.index = index;
/*      */     }
/*      */ 
/*      */     
/*      */     MapEntry() {}
/*      */ 
/*      */     
/*      */     public K getKey() {
/*  904 */       return Object2BooleanLinkedOpenCustomHashMap.this.key[this.index];
/*      */     }
/*      */ 
/*      */     
/*      */     public K left() {
/*  909 */       return Object2BooleanLinkedOpenCustomHashMap.this.key[this.index];
/*      */     }
/*      */ 
/*      */     
/*      */     public boolean getBooleanValue() {
/*  914 */       return Object2BooleanLinkedOpenCustomHashMap.this.value[this.index];
/*      */     }
/*      */ 
/*      */     
/*      */     public boolean rightBoolean() {
/*  919 */       return Object2BooleanLinkedOpenCustomHashMap.this.value[this.index];
/*      */     }
/*      */ 
/*      */     
/*      */     public boolean setValue(boolean v) {
/*  924 */       boolean oldValue = Object2BooleanLinkedOpenCustomHashMap.this.value[this.index];
/*  925 */       Object2BooleanLinkedOpenCustomHashMap.this.value[this.index] = v;
/*  926 */       return oldValue;
/*      */     }
/*      */ 
/*      */     
/*      */     public ObjectBooleanPair<K> right(boolean v) {
/*  931 */       Object2BooleanLinkedOpenCustomHashMap.this.value[this.index] = v;
/*  932 */       return this;
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
/*  943 */       return Boolean.valueOf(Object2BooleanLinkedOpenCustomHashMap.this.value[this.index]);
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
/*  954 */       return Boolean.valueOf(setValue(v.booleanValue()));
/*      */     }
/*      */ 
/*      */ 
/*      */     
/*      */     public boolean equals(Object o) {
/*  960 */       if (!(o instanceof Map.Entry)) return false; 
/*  961 */       Map.Entry<K, Boolean> e = (Map.Entry<K, Boolean>)o;
/*  962 */       return (Object2BooleanLinkedOpenCustomHashMap.this.strategy.equals(Object2BooleanLinkedOpenCustomHashMap.this.key[this.index], e.getKey()) && Object2BooleanLinkedOpenCustomHashMap.this.value[this.index] == ((Boolean)e.getValue()).booleanValue());
/*      */     }
/*      */ 
/*      */     
/*      */     public int hashCode() {
/*  967 */       return Object2BooleanLinkedOpenCustomHashMap.this.strategy.hashCode(Object2BooleanLinkedOpenCustomHashMap.this.key[this.index]) ^ (Object2BooleanLinkedOpenCustomHashMap.this.value[this.index] ? 1231 : 1237);
/*      */     }
/*      */ 
/*      */     
/*      */     public String toString() {
/*  972 */       return (new StringBuilder()).append(Object2BooleanLinkedOpenCustomHashMap.this.key[this.index]).append("=>").append(Object2BooleanLinkedOpenCustomHashMap.this.value[this.index]).toString();
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
/*  983 */     if (this.size == 0) {
/*  984 */       this.first = this.last = -1;
/*      */       return;
/*      */     } 
/*  987 */     if (this.first == i) {
/*  988 */       this.first = (int)this.link[i];
/*  989 */       if (0 <= this.first)
/*      */       {
/*  991 */         this.link[this.first] = this.link[this.first] | 0xFFFFFFFF00000000L;
/*      */       }
/*      */       return;
/*      */     } 
/*  995 */     if (this.last == i) {
/*  996 */       this.last = (int)(this.link[i] >>> 32L);
/*  997 */       if (0 <= this.last)
/*      */       {
/*  999 */         this.link[this.last] = this.link[this.last] | 0xFFFFFFFFL;
/*      */       }
/*      */       return;
/*      */     } 
/* 1003 */     long linki = this.link[i];
/* 1004 */     int prev = (int)(linki >>> 32L);
/* 1005 */     int next = (int)linki;
/* 1006 */     this.link[prev] = this.link[prev] ^ (this.link[prev] ^ linki & 0xFFFFFFFFL) & 0xFFFFFFFFL;
/* 1007 */     this.link[next] = this.link[next] ^ (this.link[next] ^ linki & 0xFFFFFFFF00000000L) & 0xFFFFFFFF00000000L;
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
/* 1019 */     if (this.size == 1) {
/* 1020 */       this.first = this.last = d;
/*      */       
/* 1022 */       this.link[d] = -1L;
/*      */       return;
/*      */     } 
/* 1025 */     if (this.first == s) {
/* 1026 */       this.first = d;
/* 1027 */       this.link[(int)this.link[s]] = this.link[(int)this.link[s]] ^ (this.link[(int)this.link[s]] ^ (d & 0xFFFFFFFFL) << 32L) & 0xFFFFFFFF00000000L;
/* 1028 */       this.link[d] = this.link[s];
/*      */       return;
/*      */     } 
/* 1031 */     if (this.last == s) {
/* 1032 */       this.last = d;
/* 1033 */       this.link[(int)(this.link[s] >>> 32L)] = this.link[(int)(this.link[s] >>> 32L)] ^ (this.link[(int)(this.link[s] >>> 32L)] ^ d & 0xFFFFFFFFL) & 0xFFFFFFFFL;
/* 1034 */       this.link[d] = this.link[s];
/*      */       return;
/*      */     } 
/* 1037 */     long links = this.link[s];
/* 1038 */     int prev = (int)(links >>> 32L);
/* 1039 */     int next = (int)links;
/* 1040 */     this.link[prev] = this.link[prev] ^ (this.link[prev] ^ d & 0xFFFFFFFFL) & 0xFFFFFFFFL;
/* 1041 */     this.link[next] = this.link[next] ^ (this.link[next] ^ (d & 0xFFFFFFFFL) << 32L) & 0xFFFFFFFF00000000L;
/* 1042 */     this.link[d] = links;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public K firstKey() {
/* 1052 */     if (this.size == 0) throw new NoSuchElementException(); 
/* 1053 */     return this.key[this.first];
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public K lastKey() {
/* 1063 */     if (this.size == 0) throw new NoSuchElementException(); 
/* 1064 */     return this.key[this.last];
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Object2BooleanSortedMap<K> tailMap(K from) {
/* 1074 */     throw new UnsupportedOperationException();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Object2BooleanSortedMap<K> headMap(K to) {
/* 1084 */     throw new UnsupportedOperationException();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Object2BooleanSortedMap<K> subMap(K from, K to) {
/* 1094 */     throw new UnsupportedOperationException();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Comparator<? super K> comparator() {
/* 1104 */     return null;
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
/* 1119 */     int prev = -1;
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 1124 */     int next = -1;
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 1129 */     int curr = -1;
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 1134 */     int index = -1;
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     protected MapIterator() {
/* 1140 */       this.next = Object2BooleanLinkedOpenCustomHashMap.this.first;
/* 1141 */       this.index = 0;
/*      */     }
/*      */     
/*      */     private MapIterator(K from) {
/* 1145 */       if (Object2BooleanLinkedOpenCustomHashMap.this.strategy.equals(from, null)) {
/* 1146 */         if (Object2BooleanLinkedOpenCustomHashMap.this.containsNullKey) {
/* 1147 */           this.next = (int)Object2BooleanLinkedOpenCustomHashMap.this.link[Object2BooleanLinkedOpenCustomHashMap.this.n];
/* 1148 */           this.prev = Object2BooleanLinkedOpenCustomHashMap.this.n; return;
/*      */         } 
/* 1150 */         throw new NoSuchElementException("The key " + from + " does not belong to this map.");
/*      */       } 
/* 1152 */       if (Object2BooleanLinkedOpenCustomHashMap.this.strategy.equals(Object2BooleanLinkedOpenCustomHashMap.this.key[Object2BooleanLinkedOpenCustomHashMap.this.last], from)) {
/* 1153 */         this.prev = Object2BooleanLinkedOpenCustomHashMap.this.last;
/* 1154 */         this.index = Object2BooleanLinkedOpenCustomHashMap.this.size;
/*      */         
/*      */         return;
/*      */       } 
/* 1158 */       int pos = HashCommon.mix(Object2BooleanLinkedOpenCustomHashMap.this.strategy.hashCode(from)) & Object2BooleanLinkedOpenCustomHashMap.this.mask;
/*      */       
/* 1160 */       while (Object2BooleanLinkedOpenCustomHashMap.this.key[pos] != null) {
/* 1161 */         if (Object2BooleanLinkedOpenCustomHashMap.this.strategy.equals(Object2BooleanLinkedOpenCustomHashMap.this.key[pos], from)) {
/*      */           
/* 1163 */           this.next = (int)Object2BooleanLinkedOpenCustomHashMap.this.link[pos];
/* 1164 */           this.prev = pos;
/*      */           return;
/*      */         } 
/* 1167 */         pos = pos + 1 & Object2BooleanLinkedOpenCustomHashMap.this.mask;
/*      */       } 
/* 1169 */       throw new NoSuchElementException("The key " + from + " does not belong to this map.");
/*      */     }
/*      */     
/*      */     public boolean hasNext() {
/* 1173 */       return (this.next != -1);
/*      */     }
/*      */     
/*      */     public boolean hasPrevious() {
/* 1177 */       return (this.prev != -1);
/*      */     }
/*      */     
/*      */     private final void ensureIndexKnown() {
/* 1181 */       if (this.index >= 0)
/* 1182 */         return;  if (this.prev == -1) {
/* 1183 */         this.index = 0;
/*      */         return;
/*      */       } 
/* 1186 */       if (this.next == -1) {
/* 1187 */         this.index = Object2BooleanLinkedOpenCustomHashMap.this.size;
/*      */         return;
/*      */       } 
/* 1190 */       int pos = Object2BooleanLinkedOpenCustomHashMap.this.first;
/* 1191 */       this.index = 1;
/* 1192 */       while (pos != this.prev) {
/* 1193 */         pos = (int)Object2BooleanLinkedOpenCustomHashMap.this.link[pos];
/* 1194 */         this.index++;
/*      */       } 
/*      */     }
/*      */     
/*      */     public int nextIndex() {
/* 1199 */       ensureIndexKnown();
/* 1200 */       return this.index;
/*      */     }
/*      */     
/*      */     public int previousIndex() {
/* 1204 */       ensureIndexKnown();
/* 1205 */       return this.index - 1;
/*      */     }
/*      */     
/*      */     public int nextEntry() {
/* 1209 */       if (!hasNext()) throw new NoSuchElementException(); 
/* 1210 */       this.curr = this.next;
/* 1211 */       this.next = (int)Object2BooleanLinkedOpenCustomHashMap.this.link[this.curr];
/* 1212 */       this.prev = this.curr;
/* 1213 */       if (this.index >= 0) this.index++; 
/* 1214 */       return this.curr;
/*      */     }
/*      */     
/*      */     public int previousEntry() {
/* 1218 */       if (!hasPrevious()) throw new NoSuchElementException(); 
/* 1219 */       this.curr = this.prev;
/* 1220 */       this.prev = (int)(Object2BooleanLinkedOpenCustomHashMap.this.link[this.curr] >>> 32L);
/* 1221 */       this.next = this.curr;
/* 1222 */       if (this.index >= 0) this.index--; 
/* 1223 */       return this.curr;
/*      */     }
/*      */     
/*      */     public void forEachRemaining(ConsumerType action) {
/* 1227 */       while (hasNext()) {
/* 1228 */         this.curr = this.next;
/* 1229 */         this.next = (int)Object2BooleanLinkedOpenCustomHashMap.this.link[this.curr];
/* 1230 */         this.prev = this.curr;
/* 1231 */         if (this.index >= 0) this.index++; 
/* 1232 */         acceptOnIndex(action, this.curr);
/*      */       } 
/*      */     }
/*      */     
/*      */     public void remove() {
/* 1237 */       ensureIndexKnown();
/* 1238 */       if (this.curr == -1) throw new IllegalStateException(); 
/* 1239 */       if (this.curr == this.prev)
/*      */       
/*      */       { 
/* 1242 */         this.index--;
/* 1243 */         this.prev = (int)(Object2BooleanLinkedOpenCustomHashMap.this.link[this.curr] >>> 32L); }
/* 1244 */       else { this.next = (int)Object2BooleanLinkedOpenCustomHashMap.this.link[this.curr]; }
/* 1245 */        Object2BooleanLinkedOpenCustomHashMap.this.size--;
/*      */ 
/*      */       
/* 1248 */       if (this.prev == -1) { Object2BooleanLinkedOpenCustomHashMap.this.first = this.next; }
/* 1249 */       else { Object2BooleanLinkedOpenCustomHashMap.this.link[this.prev] = Object2BooleanLinkedOpenCustomHashMap.this.link[this.prev] ^ (Object2BooleanLinkedOpenCustomHashMap.this.link[this.prev] ^ this.next & 0xFFFFFFFFL) & 0xFFFFFFFFL; }
/* 1250 */        if (this.next == -1) { Object2BooleanLinkedOpenCustomHashMap.this.last = this.prev; }
/* 1251 */       else { Object2BooleanLinkedOpenCustomHashMap.this.link[this.next] = Object2BooleanLinkedOpenCustomHashMap.this.link[this.next] ^ (Object2BooleanLinkedOpenCustomHashMap.this.link[this.next] ^ (this.prev & 0xFFFFFFFFL) << 32L) & 0xFFFFFFFF00000000L; }
/* 1252 */        int pos = this.curr;
/* 1253 */       this.curr = -1;
/* 1254 */       if (pos == Object2BooleanLinkedOpenCustomHashMap.this.n) {
/* 1255 */         Object2BooleanLinkedOpenCustomHashMap.this.containsNullKey = false;
/* 1256 */         Object2BooleanLinkedOpenCustomHashMap.this.key[Object2BooleanLinkedOpenCustomHashMap.this.n] = null;
/*      */       } else {
/*      */         
/* 1259 */         K[] key = Object2BooleanLinkedOpenCustomHashMap.this.key; while (true) {
/*      */           K curr;
/*      */           int last;
/* 1262 */           pos = (last = pos) + 1 & Object2BooleanLinkedOpenCustomHashMap.this.mask;
/*      */           while (true) {
/* 1264 */             if ((curr = key[pos]) == null) {
/* 1265 */               key[last] = null;
/*      */               return;
/*      */             } 
/* 1268 */             int slot = HashCommon.mix(Object2BooleanLinkedOpenCustomHashMap.this.strategy.hashCode(curr)) & Object2BooleanLinkedOpenCustomHashMap.this.mask;
/* 1269 */             if ((last <= pos) ? (last >= slot || slot > pos) : (last >= slot && slot > pos))
/* 1270 */               break;  pos = pos + 1 & Object2BooleanLinkedOpenCustomHashMap.this.mask;
/*      */           } 
/* 1272 */           key[last] = curr;
/* 1273 */           Object2BooleanLinkedOpenCustomHashMap.this.value[last] = Object2BooleanLinkedOpenCustomHashMap.this.value[pos];
/* 1274 */           if (this.next == pos) this.next = last; 
/* 1275 */           if (this.prev == pos) this.prev = last; 
/* 1276 */           Object2BooleanLinkedOpenCustomHashMap.this.fixPointers(pos, last);
/*      */         } 
/*      */       } 
/*      */     }
/*      */     
/*      */     public int skip(int n) {
/* 1282 */       int i = n;
/* 1283 */       for (; i-- != 0 && hasNext(); nextEntry());
/* 1284 */       return n - i - 1;
/*      */     }
/*      */     
/*      */     public int back(int n) {
/* 1288 */       int i = n;
/* 1289 */       for (; i-- != 0 && hasPrevious(); previousEntry());
/* 1290 */       return n - i - 1;
/*      */     }
/*      */     
/*      */     public void set(Object2BooleanMap.Entry<K> ok) {
/* 1294 */       throw new UnsupportedOperationException();
/*      */     }
/*      */     
/*      */     public void add(Object2BooleanMap.Entry<K> ok) {
/* 1298 */       throw new UnsupportedOperationException();
/*      */     }
/*      */     
/*      */     abstract void acceptOnIndex(ConsumerType param1ConsumerType, int param1Int); }
/*      */   
/*      */   private final class EntryIterator extends MapIterator<Consumer<? super Object2BooleanMap.Entry<K>>> implements ObjectListIterator<Object2BooleanMap.Entry<K>> {
/*      */     private Object2BooleanLinkedOpenCustomHashMap<K>.MapEntry entry;
/*      */     
/*      */     public EntryIterator() {}
/*      */     
/*      */     public EntryIterator(K from) {
/* 1309 */       super(from);
/*      */     }
/*      */ 
/*      */ 
/*      */     
/*      */     final void acceptOnIndex(Consumer<? super Object2BooleanMap.Entry<K>> action, int index) {
/* 1315 */       action.accept(new Object2BooleanLinkedOpenCustomHashMap.MapEntry(index));
/*      */     }
/*      */ 
/*      */     
/*      */     public Object2BooleanLinkedOpenCustomHashMap<K>.MapEntry next() {
/* 1320 */       return this.entry = new Object2BooleanLinkedOpenCustomHashMap.MapEntry(nextEntry());
/*      */     }
/*      */ 
/*      */     
/*      */     public Object2BooleanLinkedOpenCustomHashMap<K>.MapEntry previous() {
/* 1325 */       return this.entry = new Object2BooleanLinkedOpenCustomHashMap.MapEntry(previousEntry());
/*      */     }
/*      */ 
/*      */     
/*      */     public void remove() {
/* 1330 */       super.remove();
/* 1331 */       this.entry.index = -1;
/*      */     }
/*      */   }
/*      */   
/*      */   private final class FastEntryIterator extends MapIterator<Consumer<? super Object2BooleanMap.Entry<K>>> implements ObjectListIterator<Object2BooleanMap.Entry<K>> {
/* 1336 */     final Object2BooleanLinkedOpenCustomHashMap<K>.MapEntry entry = new Object2BooleanLinkedOpenCustomHashMap.MapEntry();
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public FastEntryIterator(K from) {
/* 1342 */       super(from);
/*      */     }
/*      */ 
/*      */ 
/*      */     
/*      */     final void acceptOnIndex(Consumer<? super Object2BooleanMap.Entry<K>> action, int index) {
/* 1348 */       this.entry.index = index;
/* 1349 */       action.accept(this.entry);
/*      */     }
/*      */ 
/*      */     
/*      */     public Object2BooleanLinkedOpenCustomHashMap<K>.MapEntry next() {
/* 1354 */       this.entry.index = nextEntry();
/* 1355 */       return this.entry;
/*      */     }
/*      */ 
/*      */     
/*      */     public Object2BooleanLinkedOpenCustomHashMap<K>.MapEntry previous() {
/* 1360 */       this.entry.index = previousEntry();
/* 1361 */       return this.entry;
/*      */     }
/*      */     
/*      */     public FastEntryIterator() {} }
/*      */   
/*      */   private final class MapEntrySet extends AbstractObjectSortedSet<Object2BooleanMap.Entry<K>> implements Object2BooleanSortedMap.FastSortedEntrySet<K> {
/*      */     private static final int SPLITERATOR_CHARACTERISTICS = 81;
/*      */     
/*      */     public ObjectBidirectionalIterator<Object2BooleanMap.Entry<K>> iterator() {
/* 1370 */       return new Object2BooleanLinkedOpenCustomHashMap.EntryIterator();
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
/*      */     public ObjectSpliterator<Object2BooleanMap.Entry<K>> spliterator() {
/* 1388 */       return ObjectSpliterators.asSpliterator(iterator(), Size64.sizeOf(Object2BooleanLinkedOpenCustomHashMap.this), 81);
/*      */     }
/*      */ 
/*      */     
/*      */     public Comparator<? super Object2BooleanMap.Entry<K>> comparator() {
/* 1393 */       return null;
/*      */     }
/*      */ 
/*      */     
/*      */     public ObjectSortedSet<Object2BooleanMap.Entry<K>> subSet(Object2BooleanMap.Entry<K> fromElement, Object2BooleanMap.Entry<K> toElement) {
/* 1398 */       throw new UnsupportedOperationException();
/*      */     }
/*      */ 
/*      */     
/*      */     public ObjectSortedSet<Object2BooleanMap.Entry<K>> headSet(Object2BooleanMap.Entry<K> toElement) {
/* 1403 */       throw new UnsupportedOperationException();
/*      */     }
/*      */ 
/*      */     
/*      */     public ObjectSortedSet<Object2BooleanMap.Entry<K>> tailSet(Object2BooleanMap.Entry<K> fromElement) {
/* 1408 */       throw new UnsupportedOperationException();
/*      */     }
/*      */ 
/*      */     
/*      */     public Object2BooleanMap.Entry<K> first() {
/* 1413 */       if (Object2BooleanLinkedOpenCustomHashMap.this.size == 0) throw new NoSuchElementException(); 
/* 1414 */       return new Object2BooleanLinkedOpenCustomHashMap.MapEntry(Object2BooleanLinkedOpenCustomHashMap.this.first);
/*      */     }
/*      */ 
/*      */     
/*      */     public Object2BooleanMap.Entry<K> last() {
/* 1419 */       if (Object2BooleanLinkedOpenCustomHashMap.this.size == 0) throw new NoSuchElementException(); 
/* 1420 */       return new Object2BooleanLinkedOpenCustomHashMap.MapEntry(Object2BooleanLinkedOpenCustomHashMap.this.last);
/*      */     }
/*      */ 
/*      */ 
/*      */     
/*      */     public boolean contains(Object o) {
/* 1426 */       if (!(o instanceof Map.Entry)) return false; 
/* 1427 */       Map.Entry<?, ?> e = (Map.Entry<?, ?>)o;
/* 1428 */       if (e.getValue() == null || !(e.getValue() instanceof Boolean)) return false; 
/* 1429 */       K k = (K)e.getKey();
/* 1430 */       boolean v = ((Boolean)e.getValue()).booleanValue();
/* 1431 */       if (Object2BooleanLinkedOpenCustomHashMap.this.strategy.equals(k, null)) return (Object2BooleanLinkedOpenCustomHashMap.this.containsNullKey && Object2BooleanLinkedOpenCustomHashMap.this.value[Object2BooleanLinkedOpenCustomHashMap.this.n] == v);
/*      */       
/* 1433 */       K[] key = Object2BooleanLinkedOpenCustomHashMap.this.key;
/*      */       K curr;
/*      */       int pos;
/* 1436 */       if ((curr = key[pos = HashCommon.mix(Object2BooleanLinkedOpenCustomHashMap.this.strategy.hashCode(k)) & Object2BooleanLinkedOpenCustomHashMap.this.mask]) == null) return false; 
/* 1437 */       if (Object2BooleanLinkedOpenCustomHashMap.this.strategy.equals(k, curr)) return (Object2BooleanLinkedOpenCustomHashMap.this.value[pos] == v);
/*      */       
/*      */       while (true) {
/* 1440 */         if ((curr = key[pos = pos + 1 & Object2BooleanLinkedOpenCustomHashMap.this.mask]) == null) return false; 
/* 1441 */         if (Object2BooleanLinkedOpenCustomHashMap.this.strategy.equals(k, curr)) return (Object2BooleanLinkedOpenCustomHashMap.this.value[pos] == v);
/*      */       
/*      */       } 
/*      */     }
/*      */ 
/*      */     
/*      */     public boolean remove(Object o) {
/* 1448 */       if (!(o instanceof Map.Entry)) return false; 
/* 1449 */       Map.Entry<?, ?> e = (Map.Entry<?, ?>)o;
/* 1450 */       if (e.getValue() == null || !(e.getValue() instanceof Boolean)) return false; 
/* 1451 */       K k = (K)e.getKey();
/* 1452 */       boolean v = ((Boolean)e.getValue()).booleanValue();
/* 1453 */       if (Object2BooleanLinkedOpenCustomHashMap.this.strategy.equals(k, null)) {
/* 1454 */         if (Object2BooleanLinkedOpenCustomHashMap.this.containsNullKey && Object2BooleanLinkedOpenCustomHashMap.this.value[Object2BooleanLinkedOpenCustomHashMap.this.n] == v) {
/* 1455 */           Object2BooleanLinkedOpenCustomHashMap.this.removeNullEntry();
/* 1456 */           return true;
/*      */         } 
/* 1458 */         return false;
/*      */       } 
/*      */       
/* 1461 */       K[] key = Object2BooleanLinkedOpenCustomHashMap.this.key;
/*      */       K curr;
/*      */       int pos;
/* 1464 */       if ((curr = key[pos = HashCommon.mix(Object2BooleanLinkedOpenCustomHashMap.this.strategy.hashCode(k)) & Object2BooleanLinkedOpenCustomHashMap.this.mask]) == null) return false; 
/* 1465 */       if (Object2BooleanLinkedOpenCustomHashMap.this.strategy.equals(curr, k)) {
/* 1466 */         if (Object2BooleanLinkedOpenCustomHashMap.this.value[pos] == v) {
/* 1467 */           Object2BooleanLinkedOpenCustomHashMap.this.removeEntry(pos);
/* 1468 */           return true;
/*      */         } 
/* 1470 */         return false;
/*      */       } 
/*      */       while (true) {
/* 1473 */         if ((curr = key[pos = pos + 1 & Object2BooleanLinkedOpenCustomHashMap.this.mask]) == null) return false; 
/* 1474 */         if (Object2BooleanLinkedOpenCustomHashMap.this.strategy.equals(curr, k) && 
/* 1475 */           Object2BooleanLinkedOpenCustomHashMap.this.value[pos] == v) {
/* 1476 */           Object2BooleanLinkedOpenCustomHashMap.this.removeEntry(pos);
/* 1477 */           return true;
/*      */         } 
/*      */       } 
/*      */     }
/*      */ 
/*      */ 
/*      */     
/*      */     public int size() {
/* 1485 */       return Object2BooleanLinkedOpenCustomHashMap.this.size;
/*      */     }
/*      */ 
/*      */     
/*      */     public void clear() {
/* 1490 */       Object2BooleanLinkedOpenCustomHashMap.this.clear();
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
/*      */     public ObjectListIterator<Object2BooleanMap.Entry<K>> iterator(Object2BooleanMap.Entry<K> from) {
/* 1503 */       return new Object2BooleanLinkedOpenCustomHashMap.EntryIterator(from.getKey());
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public ObjectListIterator<Object2BooleanMap.Entry<K>> fastIterator() {
/* 1514 */       return new Object2BooleanLinkedOpenCustomHashMap.FastEntryIterator();
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
/*      */     public ObjectListIterator<Object2BooleanMap.Entry<K>> fastIterator(Object2BooleanMap.Entry<K> from) {
/* 1527 */       return new Object2BooleanLinkedOpenCustomHashMap.FastEntryIterator(from.getKey());
/*      */     }
/*      */ 
/*      */ 
/*      */     
/*      */     public void forEach(Consumer<? super Object2BooleanMap.Entry<K>> consumer) {
/* 1533 */       for (int i = Object2BooleanLinkedOpenCustomHashMap.this.size, next = Object2BooleanLinkedOpenCustomHashMap.this.first; i-- != 0; ) {
/* 1534 */         int curr = next;
/* 1535 */         next = (int)Object2BooleanLinkedOpenCustomHashMap.this.link[curr];
/* 1536 */         consumer.accept(new Object2BooleanLinkedOpenCustomHashMap.MapEntry(curr));
/*      */       } 
/*      */     }
/*      */ 
/*      */ 
/*      */     
/*      */     public void fastForEach(Consumer<? super Object2BooleanMap.Entry<K>> consumer) {
/* 1543 */       Object2BooleanLinkedOpenCustomHashMap<K>.MapEntry entry = new Object2BooleanLinkedOpenCustomHashMap.MapEntry();
/* 1544 */       for (int i = Object2BooleanLinkedOpenCustomHashMap.this.size, next = Object2BooleanLinkedOpenCustomHashMap.this.first; i-- != 0; ) {
/* 1545 */         entry.index = next;
/* 1546 */         next = (int)Object2BooleanLinkedOpenCustomHashMap.this.link[next];
/* 1547 */         consumer.accept(entry);
/*      */       } 
/*      */     }
/*      */   }
/*      */ 
/*      */   
/*      */   public Object2BooleanSortedMap.FastSortedEntrySet<K> object2BooleanEntrySet() {
/* 1554 */     if (this.entries == null) this.entries = new MapEntrySet(); 
/* 1555 */     return this.entries;
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
/* 1568 */       super(k);
/*      */     }
/*      */ 
/*      */     
/*      */     public K previous() {
/* 1573 */       return Object2BooleanLinkedOpenCustomHashMap.this.key[previousEntry()];
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
/* 1585 */       action.accept(Object2BooleanLinkedOpenCustomHashMap.this.key[index]);
/*      */     }
/*      */ 
/*      */     
/*      */     public K next() {
/* 1590 */       return Object2BooleanLinkedOpenCustomHashMap.this.key[nextEntry()];
/*      */     } }
/*      */   
/*      */   private final class KeySet extends AbstractObjectSortedSet<K> {
/*      */     private static final int SPLITERATOR_CHARACTERISTICS = 81;
/*      */     
/*      */     private KeySet() {}
/*      */     
/*      */     public ObjectListIterator<K> iterator(K from) {
/* 1599 */       return new Object2BooleanLinkedOpenCustomHashMap.KeyIterator(from);
/*      */     }
/*      */ 
/*      */     
/*      */     public ObjectListIterator<K> iterator() {
/* 1604 */       return new Object2BooleanLinkedOpenCustomHashMap.KeyIterator();
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public ObjectSpliterator<K> spliterator() {
/* 1614 */       return ObjectSpliterators.asSpliterator(iterator(), Size64.sizeOf(Object2BooleanLinkedOpenCustomHashMap.this), 81);
/*      */     }
/*      */ 
/*      */ 
/*      */     
/*      */     public void forEach(Consumer<? super K> consumer) {
/* 1620 */       for (int i = Object2BooleanLinkedOpenCustomHashMap.this.size, next = Object2BooleanLinkedOpenCustomHashMap.this.first; i-- != 0; ) {
/* 1621 */         int curr = next;
/* 1622 */         next = (int)Object2BooleanLinkedOpenCustomHashMap.this.link[curr];
/* 1623 */         consumer.accept(Object2BooleanLinkedOpenCustomHashMap.this.key[curr]);
/*      */       } 
/*      */     }
/*      */ 
/*      */     
/*      */     public int size() {
/* 1629 */       return Object2BooleanLinkedOpenCustomHashMap.this.size;
/*      */     }
/*      */ 
/*      */     
/*      */     public boolean contains(Object k) {
/* 1634 */       return Object2BooleanLinkedOpenCustomHashMap.this.containsKey(k);
/*      */     }
/*      */ 
/*      */     
/*      */     public boolean remove(Object k) {
/* 1639 */       int oldSize = Object2BooleanLinkedOpenCustomHashMap.this.size;
/* 1640 */       Object2BooleanLinkedOpenCustomHashMap.this.removeBoolean(k);
/* 1641 */       return (Object2BooleanLinkedOpenCustomHashMap.this.size != oldSize);
/*      */     }
/*      */ 
/*      */     
/*      */     public void clear() {
/* 1646 */       Object2BooleanLinkedOpenCustomHashMap.this.clear();
/*      */     }
/*      */ 
/*      */     
/*      */     public K first() {
/* 1651 */       if (Object2BooleanLinkedOpenCustomHashMap.this.size == 0) throw new NoSuchElementException(); 
/* 1652 */       return Object2BooleanLinkedOpenCustomHashMap.this.key[Object2BooleanLinkedOpenCustomHashMap.this.first];
/*      */     }
/*      */ 
/*      */     
/*      */     public K last() {
/* 1657 */       if (Object2BooleanLinkedOpenCustomHashMap.this.size == 0) throw new NoSuchElementException(); 
/* 1658 */       return Object2BooleanLinkedOpenCustomHashMap.this.key[Object2BooleanLinkedOpenCustomHashMap.this.last];
/*      */     }
/*      */ 
/*      */     
/*      */     public Comparator<? super K> comparator() {
/* 1663 */       return null;
/*      */     }
/*      */ 
/*      */     
/*      */     public ObjectSortedSet<K> tailSet(K from) {
/* 1668 */       throw new UnsupportedOperationException();
/*      */     }
/*      */ 
/*      */     
/*      */     public ObjectSortedSet<K> headSet(K to) {
/* 1673 */       throw new UnsupportedOperationException();
/*      */     }
/*      */ 
/*      */     
/*      */     public ObjectSortedSet<K> subSet(K from, K to) {
/* 1678 */       throw new UnsupportedOperationException();
/*      */     }
/*      */   }
/*      */ 
/*      */   
/*      */   public ObjectSortedSet<K> keySet() {
/* 1684 */     if (this.keys == null) this.keys = new KeySet(); 
/* 1685 */     return this.keys;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private final class ValueIterator
/*      */     extends MapIterator<BooleanConsumer>
/*      */     implements BooleanListIterator
/*      */   {
/*      */     public boolean previousBoolean() {
/* 1699 */       return Object2BooleanLinkedOpenCustomHashMap.this.value[previousEntry()];
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
/*      */     final void acceptOnIndex(BooleanConsumer action, int index) {
/* 1711 */       action.accept(Object2BooleanLinkedOpenCustomHashMap.this.value[index]);
/*      */     }
/*      */ 
/*      */     
/*      */     public boolean nextBoolean() {
/* 1716 */       return Object2BooleanLinkedOpenCustomHashMap.this.value[nextEntry()];
/*      */     }
/*      */   }
/*      */ 
/*      */   
/*      */   public BooleanCollection values() {
/* 1722 */     if (this.values == null) this.values = (BooleanCollection)new AbstractBooleanCollection()
/*      */         {
/*      */           private static final int SPLITERATOR_CHARACTERISTICS = 336;
/*      */           
/*      */           public BooleanIterator iterator() {
/* 1727 */             return (BooleanIterator)new Object2BooleanLinkedOpenCustomHashMap.ValueIterator();
/*      */           }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */           
/*      */           public BooleanSpliterator spliterator() {
/* 1737 */             return BooleanSpliterators.asSpliterator(iterator(), Size64.sizeOf(Object2BooleanLinkedOpenCustomHashMap.this), 336);
/*      */           }
/*      */ 
/*      */ 
/*      */           
/*      */           public void forEach(BooleanConsumer consumer) {
/* 1743 */             for (int i = Object2BooleanLinkedOpenCustomHashMap.this.size, next = Object2BooleanLinkedOpenCustomHashMap.this.first; i-- != 0; ) {
/* 1744 */               int curr = next;
/* 1745 */               next = (int)Object2BooleanLinkedOpenCustomHashMap.this.link[curr];
/* 1746 */               consumer.accept(Object2BooleanLinkedOpenCustomHashMap.this.value[curr]);
/*      */             } 
/*      */           }
/*      */ 
/*      */           
/*      */           public int size() {
/* 1752 */             return Object2BooleanLinkedOpenCustomHashMap.this.size;
/*      */           }
/*      */ 
/*      */           
/*      */           public boolean contains(boolean v) {
/* 1757 */             return Object2BooleanLinkedOpenCustomHashMap.this.containsValue(v);
/*      */           }
/*      */ 
/*      */           
/*      */           public void clear() {
/* 1762 */             Object2BooleanLinkedOpenCustomHashMap.this.clear();
/*      */           }
/*      */         }; 
/* 1765 */     return this.values;
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
/* 1782 */     return trim(this.size);
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
/* 1804 */     int l = HashCommon.nextPowerOfTwo((int)Math.ceil((n / this.f)));
/* 1805 */     if (l >= this.n || this.size > HashCommon.maxFill(l, this.f)) return true; 
/*      */     try {
/* 1807 */       rehash(l);
/* 1808 */     } catch (OutOfMemoryError cantDoIt) {
/* 1809 */       return false;
/*      */     } 
/* 1811 */     return true;
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
/* 1826 */     K[] key = this.key;
/* 1827 */     boolean[] value = this.value;
/* 1828 */     int mask = newN - 1;
/* 1829 */     K[] newKey = (K[])new Object[newN + 1];
/* 1830 */     boolean[] newValue = new boolean[newN + 1];
/* 1831 */     int i = this.first, prev = -1, newPrev = -1;
/* 1832 */     long[] link = this.link;
/* 1833 */     long[] newLink = new long[newN + 1];
/* 1834 */     this.first = -1;
/* 1835 */     for (int j = this.size; j-- != 0; ) {
/* 1836 */       int pos; if (this.strategy.equals(key[i], null)) { pos = newN; }
/*      */       else
/* 1838 */       { pos = HashCommon.mix(this.strategy.hashCode(key[i])) & mask;
/* 1839 */         for (; newKey[pos] != null; pos = pos + 1 & mask); }
/*      */       
/* 1841 */       newKey[pos] = key[i];
/* 1842 */       newValue[pos] = value[i];
/* 1843 */       if (prev != -1) {
/* 1844 */         newLink[newPrev] = newLink[newPrev] ^ (newLink[newPrev] ^ pos & 0xFFFFFFFFL) & 0xFFFFFFFFL;
/* 1845 */         newLink[pos] = newLink[pos] ^ (newLink[pos] ^ (newPrev & 0xFFFFFFFFL) << 32L) & 0xFFFFFFFF00000000L;
/* 1846 */         newPrev = pos;
/*      */       } else {
/* 1848 */         newPrev = this.first = pos;
/*      */         
/* 1850 */         newLink[pos] = -1L;
/*      */       } 
/* 1852 */       int t = i;
/* 1853 */       i = (int)link[i];
/* 1854 */       prev = t;
/*      */     } 
/* 1856 */     this.link = newLink;
/* 1857 */     this.last = newPrev;
/* 1858 */     if (newPrev != -1)
/*      */     {
/* 1860 */       newLink[newPrev] = newLink[newPrev] | 0xFFFFFFFFL; } 
/* 1861 */     this.n = newN;
/* 1862 */     this.mask = mask;
/* 1863 */     this.maxFill = HashCommon.maxFill(this.n, this.f);
/* 1864 */     this.key = newKey;
/* 1865 */     this.value = newValue;
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
/*      */   public Object2BooleanLinkedOpenCustomHashMap<K> clone() {
/*      */     Object2BooleanLinkedOpenCustomHashMap<K> c;
/*      */     try {
/* 1882 */       c = (Object2BooleanLinkedOpenCustomHashMap<K>)super.clone();
/* 1883 */     } catch (CloneNotSupportedException cantHappen) {
/* 1884 */       throw new InternalError();
/*      */     } 
/* 1886 */     c.keys = null;
/* 1887 */     c.values = null;
/* 1888 */     c.entries = null;
/* 1889 */     c.containsNullKey = this.containsNullKey;
/* 1890 */     c.key = (K[])this.key.clone();
/* 1891 */     c.value = (boolean[])this.value.clone();
/* 1892 */     c.link = (long[])this.link.clone();
/* 1893 */     c.strategy = this.strategy;
/* 1894 */     return c;
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
/* 1908 */     int h = 0;
/* 1909 */     for (int j = realSize(), i = 0, t = 0; j-- != 0; ) {
/* 1910 */       for (; this.key[i] == null; i++);
/* 1911 */       if (this != this.key[i]) t = this.strategy.hashCode(this.key[i]); 
/* 1912 */       t ^= this.value[i] ? 1231 : 1237;
/* 1913 */       h += t;
/* 1914 */       i++;
/*      */     } 
/*      */     
/* 1917 */     if (this.containsNullKey) h += this.value[this.n] ? 1231 : 1237; 
/* 1918 */     return h;
/*      */   }
/*      */   
/*      */   private void writeObject(ObjectOutputStream s) throws IOException {
/* 1922 */     K[] key = this.key;
/* 1923 */     boolean[] value = this.value;
/* 1924 */     EntryIterator i = new EntryIterator();
/* 1925 */     s.defaultWriteObject();
/* 1926 */     for (int j = this.size; j-- != 0; ) {
/* 1927 */       int e = i.nextEntry();
/* 1928 */       s.writeObject(key[e]);
/* 1929 */       s.writeBoolean(value[e]);
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   private void readObject(ObjectInputStream s) throws IOException, ClassNotFoundException {
/* 1935 */     s.defaultReadObject();
/* 1936 */     this.n = HashCommon.arraySize(this.size, this.f);
/* 1937 */     this.maxFill = HashCommon.maxFill(this.n, this.f);
/* 1938 */     this.mask = this.n - 1;
/* 1939 */     K[] key = this.key = (K[])new Object[this.n + 1];
/* 1940 */     boolean[] value = this.value = new boolean[this.n + 1];
/* 1941 */     long[] link = this.link = new long[this.n + 1];
/* 1942 */     int prev = -1;
/* 1943 */     this.first = this.last = -1;
/*      */ 
/*      */     
/* 1946 */     for (int i = this.size; i-- != 0; ) {
/* 1947 */       int pos; K k = (K)s.readObject();
/* 1948 */       boolean v = s.readBoolean();
/* 1949 */       if (this.strategy.equals(k, null)) {
/* 1950 */         pos = this.n;
/* 1951 */         this.containsNullKey = true;
/*      */       } else {
/* 1953 */         pos = HashCommon.mix(this.strategy.hashCode(k)) & this.mask;
/* 1954 */         for (; key[pos] != null; pos = pos + 1 & this.mask);
/*      */       } 
/* 1956 */       key[pos] = k;
/* 1957 */       value[pos] = v;
/* 1958 */       if (this.first != -1) {
/* 1959 */         link[prev] = link[prev] ^ (link[prev] ^ pos & 0xFFFFFFFFL) & 0xFFFFFFFFL;
/* 1960 */         link[pos] = link[pos] ^ (link[pos] ^ (prev & 0xFFFFFFFFL) << 32L) & 0xFFFFFFFF00000000L;
/* 1961 */         prev = pos; continue;
/*      */       } 
/* 1963 */       prev = this.first = pos;
/*      */       
/* 1965 */       link[pos] = link[pos] | 0xFFFFFFFF00000000L;
/*      */     } 
/*      */     
/* 1968 */     this.last = prev;
/* 1969 */     if (prev != -1)
/*      */     {
/* 1971 */       link[prev] = link[prev] | 0xFFFFFFFFL;
/*      */     }
/*      */   }
/*      */   
/*      */   private void checkTable() {}
/*      */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\i\\unimi\dsi\fastutil\objects\Object2BooleanLinkedOpenCustomHashMap.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */