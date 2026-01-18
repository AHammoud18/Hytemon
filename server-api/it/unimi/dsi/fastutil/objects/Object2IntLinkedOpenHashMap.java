/*      */ package it.unimi.dsi.fastutil.objects;
/*      */ 
/*      */ import it.unimi.dsi.fastutil.Hash;
/*      */ import it.unimi.dsi.fastutil.HashCommon;
/*      */ import it.unimi.dsi.fastutil.Size64;
/*      */ import it.unimi.dsi.fastutil.ints.AbstractIntCollection;
/*      */ import it.unimi.dsi.fastutil.ints.IntCollection;
/*      */ import it.unimi.dsi.fastutil.ints.IntIterator;
/*      */ import it.unimi.dsi.fastutil.ints.IntListIterator;
/*      */ import it.unimi.dsi.fastutil.ints.IntSpliterator;
/*      */ import it.unimi.dsi.fastutil.ints.IntSpliterators;
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
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ public class Object2IntLinkedOpenHashMap<K>
/*      */   extends AbstractObject2IntSortedMap<K>
/*      */   implements Serializable, Cloneable, Hash
/*      */ {
/*      */   private static final long serialVersionUID = 0L;
/*      */   private static final boolean ASSERTS = false;
/*      */   protected transient K[] key;
/*      */   protected transient int[] value;
/*      */   protected transient int mask;
/*      */   protected transient boolean containsNullKey;
/*  101 */   protected transient int first = -1;
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*  106 */   protected transient int last = -1;
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
/*      */   protected transient Object2IntSortedMap.FastSortedEntrySet<K> entries;
/*      */ 
/*      */   
/*      */   protected transient ObjectSortedSet<K> keys;
/*      */ 
/*      */   
/*      */   protected transient IntCollection values;
/*      */ 
/*      */ 
/*      */   
/*      */   public Object2IntLinkedOpenHashMap(int expected, float f) {
/*  141 */     if (f <= 0.0F || f >= 1.0F) throw new IllegalArgumentException("Load factor must be greater than 0 and smaller than 1"); 
/*  142 */     if (expected < 0) throw new IllegalArgumentException("The expected number of elements must be nonnegative"); 
/*  143 */     this.f = f;
/*  144 */     this.minN = this.n = HashCommon.arraySize(expected, f);
/*  145 */     this.mask = this.n - 1;
/*  146 */     this.maxFill = HashCommon.maxFill(this.n, f);
/*  147 */     this.key = (K[])new Object[this.n + 1];
/*  148 */     this.value = new int[this.n + 1];
/*  149 */     this.link = new long[this.n + 1];
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Object2IntLinkedOpenHashMap(int expected) {
/*  158 */     this(expected, 0.75F);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Object2IntLinkedOpenHashMap() {
/*  166 */     this(16, 0.75F);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Object2IntLinkedOpenHashMap(Map<? extends K, ? extends Integer> m, float f) {
/*  176 */     this(m.size(), f);
/*  177 */     putAll(m);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Object2IntLinkedOpenHashMap(Map<? extends K, ? extends Integer> m) {
/*  186 */     this(m, 0.75F);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Object2IntLinkedOpenHashMap(Object2IntMap<K> m, float f) {
/*  196 */     this(m.size(), f);
/*  197 */     putAll(m);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Object2IntLinkedOpenHashMap(Object2IntMap<K> m) {
/*  207 */     this(m, 0.75F);
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
/*      */   public Object2IntLinkedOpenHashMap(K[] k, int[] v, float f) {
/*  219 */     this(k.length, f);
/*  220 */     if (k.length != v.length) throw new IllegalArgumentException("The key array and the value array have different lengths (" + k.length + " and " + v.length + ")"); 
/*  221 */     for (int i = 0; i < k.length; ) { put(k[i], v[i]); i++; }
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
/*      */   public Object2IntLinkedOpenHashMap(K[] k, int[] v) {
/*  233 */     this(k, v, 0.75F);
/*      */   }
/*      */   
/*      */   private int realSize() {
/*  237 */     return this.containsNullKey ? (this.size - 1) : this.size;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void ensureCapacity(int capacity) {
/*  247 */     int needed = HashCommon.arraySize(capacity, this.f);
/*  248 */     if (needed > this.n) rehash(needed); 
/*      */   }
/*      */   
/*      */   private void tryCapacity(long capacity) {
/*  252 */     int needed = (int)Math.min(1073741824L, Math.max(2L, HashCommon.nextPowerOfTwo((long)Math.ceil(((float)capacity / this.f)))));
/*  253 */     if (needed > this.n) rehash(needed); 
/*      */   }
/*      */   
/*      */   private int removeEntry(int pos) {
/*  257 */     int oldValue = this.value[pos];
/*  258 */     this.size--;
/*  259 */     fixPointers(pos);
/*  260 */     shiftKeys(pos);
/*  261 */     if (this.n > this.minN && this.size < this.maxFill / 4 && this.n > 16) rehash(this.n / 2); 
/*  262 */     return oldValue;
/*      */   }
/*      */   
/*      */   private int removeNullEntry() {
/*  266 */     this.containsNullKey = false;
/*  267 */     this.key[this.n] = null;
/*  268 */     int oldValue = this.value[this.n];
/*  269 */     this.size--;
/*  270 */     fixPointers(this.n);
/*  271 */     if (this.n > this.minN && this.size < this.maxFill / 4 && this.n > 16) rehash(this.n / 2); 
/*  272 */     return oldValue;
/*      */   }
/*      */ 
/*      */   
/*      */   public void putAll(Map<? extends K, ? extends Integer> m) {
/*  277 */     if (this.f <= 0.5D) { ensureCapacity(m.size()); }
/*  278 */     else { tryCapacity((size() + m.size())); }
/*      */     
/*  280 */     super.putAll(m);
/*      */   }
/*      */ 
/*      */   
/*      */   private int find(K k) {
/*  285 */     if (k == null) return this.containsNullKey ? this.n : -(this.n + 1);
/*      */     
/*  287 */     K[] key = this.key;
/*      */     K curr;
/*      */     int pos;
/*  290 */     if ((curr = key[pos = HashCommon.mix(k.hashCode()) & this.mask]) == null) return -(pos + 1); 
/*  291 */     if (k.equals(curr)) return pos;
/*      */     
/*      */     while (true) {
/*  294 */       if ((curr = key[pos = pos + 1 & this.mask]) == null) return -(pos + 1); 
/*  295 */       if (k.equals(curr)) return pos; 
/*      */     } 
/*      */   }
/*      */   
/*      */   private void insert(int pos, K k, int v) {
/*  300 */     if (pos == this.n) this.containsNullKey = true; 
/*  301 */     this.key[pos] = k;
/*  302 */     this.value[pos] = v;
/*  303 */     if (this.size == 0) {
/*  304 */       this.first = this.last = pos;
/*      */       
/*  306 */       this.link[pos] = -1L;
/*      */     } else {
/*  308 */       this.link[this.last] = this.link[this.last] ^ (this.link[this.last] ^ pos & 0xFFFFFFFFL) & 0xFFFFFFFFL;
/*  309 */       this.link[pos] = (this.last & 0xFFFFFFFFL) << 32L | 0xFFFFFFFFL;
/*  310 */       this.last = pos;
/*      */     } 
/*  312 */     if (this.size++ >= this.maxFill) rehash(HashCommon.arraySize(this.size + 1, this.f));
/*      */   
/*      */   }
/*      */ 
/*      */   
/*      */   public int put(K k, int v) {
/*  318 */     int pos = find(k);
/*  319 */     if (pos < 0) {
/*  320 */       insert(-pos - 1, k, v);
/*  321 */       return this.defRetValue;
/*      */     } 
/*  323 */     int oldValue = this.value[pos];
/*  324 */     this.value[pos] = v;
/*  325 */     return oldValue;
/*      */   }
/*      */   
/*      */   private int addToValue(int pos, int incr) {
/*  329 */     int oldValue = this.value[pos];
/*  330 */     this.value[pos] = oldValue + incr;
/*  331 */     return oldValue;
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
/*  349 */     if (k == null) {
/*  350 */       if (this.containsNullKey) return addToValue(this.n, incr); 
/*  351 */       pos = this.n;
/*  352 */       this.containsNullKey = true;
/*      */     } else {
/*      */       
/*  355 */       K[] key = this.key;
/*      */       K curr;
/*  357 */       if ((curr = key[pos = HashCommon.mix(k.hashCode()) & this.mask]) != null) {
/*  358 */         if (curr.equals(k)) return addToValue(pos, incr); 
/*  359 */         while ((curr = key[pos = pos + 1 & this.mask]) != null) { if (curr.equals(k)) return addToValue(pos, incr);  }
/*      */       
/*      */       } 
/*  362 */     }  this.key[pos] = k;
/*  363 */     this.value[pos] = this.defRetValue + incr;
/*  364 */     if (this.size == 0) {
/*  365 */       this.first = this.last = pos;
/*      */       
/*  367 */       this.link[pos] = -1L;
/*      */     } else {
/*  369 */       this.link[this.last] = this.link[this.last] ^ (this.link[this.last] ^ pos & 0xFFFFFFFFL) & 0xFFFFFFFFL;
/*  370 */       this.link[pos] = (this.last & 0xFFFFFFFFL) << 32L | 0xFFFFFFFFL;
/*  371 */       this.last = pos;
/*      */     } 
/*  373 */     if (this.size++ >= this.maxFill) rehash(HashCommon.arraySize(this.size + 1, this.f));
/*      */     
/*  375 */     return this.defRetValue;
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
/*  388 */     K[] key = this.key; while (true) {
/*      */       K curr; int last;
/*  390 */       pos = (last = pos) + 1 & this.mask;
/*      */       while (true) {
/*  392 */         if ((curr = key[pos]) == null) {
/*  393 */           key[last] = null;
/*      */           return;
/*      */         } 
/*  396 */         int slot = HashCommon.mix(curr.hashCode()) & this.mask;
/*  397 */         if ((last <= pos) ? (last >= slot || slot > pos) : (last >= slot && slot > pos))
/*  398 */           break;  pos = pos + 1 & this.mask;
/*      */       } 
/*  400 */       key[last] = curr;
/*  401 */       this.value[last] = this.value[pos];
/*  402 */       fixPointers(pos, last);
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public int removeInt(Object k) {
/*  409 */     if (k == null) {
/*  410 */       if (this.containsNullKey) return removeNullEntry(); 
/*  411 */       return this.defRetValue;
/*      */     } 
/*      */     
/*  414 */     K[] key = this.key;
/*      */     K curr;
/*      */     int pos;
/*  417 */     if ((curr = key[pos = HashCommon.mix(k.hashCode()) & this.mask]) == null) return this.defRetValue; 
/*  418 */     if (k.equals(curr)) return removeEntry(pos); 
/*      */     while (true) {
/*  420 */       if ((curr = key[pos = pos + 1 & this.mask]) == null) return this.defRetValue; 
/*  421 */       if (k.equals(curr)) return removeEntry(pos); 
/*      */     } 
/*      */   }
/*      */   
/*      */   private int setValue(int pos, int v) {
/*  426 */     int oldValue = this.value[pos];
/*  427 */     this.value[pos] = v;
/*  428 */     return oldValue;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public int removeFirstInt() {
/*  438 */     if (this.size == 0) throw new NoSuchElementException(); 
/*  439 */     int pos = this.first;
/*      */     
/*  441 */     if (this.size == 1) { this.first = this.last = -1; }
/*      */     else
/*  443 */     { this.first = (int)this.link[pos];
/*  444 */       if (0 <= this.first)
/*      */       {
/*  446 */         this.link[this.first] = this.link[this.first] | 0xFFFFFFFF00000000L;
/*      */       } }
/*      */     
/*  449 */     this.size--;
/*  450 */     int v = this.value[pos];
/*  451 */     if (pos == this.n)
/*  452 */     { this.containsNullKey = false;
/*  453 */       this.key[this.n] = null; }
/*  454 */     else { shiftKeys(pos); }
/*  455 */      if (this.n > this.minN && this.size < this.maxFill / 4 && this.n > 16) rehash(this.n / 2); 
/*  456 */     return v;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public int removeLastInt() {
/*  466 */     if (this.size == 0) throw new NoSuchElementException(); 
/*  467 */     int pos = this.last;
/*      */     
/*  469 */     if (this.size == 1) { this.first = this.last = -1; }
/*      */     else
/*  471 */     { this.last = (int)(this.link[pos] >>> 32L);
/*  472 */       if (0 <= this.last)
/*      */       {
/*  474 */         this.link[this.last] = this.link[this.last] | 0xFFFFFFFFL;
/*      */       } }
/*      */     
/*  477 */     this.size--;
/*  478 */     int v = this.value[pos];
/*  479 */     if (pos == this.n)
/*  480 */     { this.containsNullKey = false;
/*  481 */       this.key[this.n] = null; }
/*  482 */     else { shiftKeys(pos); }
/*  483 */      if (this.n > this.minN && this.size < this.maxFill / 4 && this.n > 16) rehash(this.n / 2); 
/*  484 */     return v;
/*      */   }
/*      */   
/*      */   private void moveIndexToFirst(int i) {
/*  488 */     if (this.size == 1 || this.first == i)
/*  489 */       return;  if (this.last == i) {
/*  490 */       this.last = (int)(this.link[i] >>> 32L);
/*      */       
/*  492 */       this.link[this.last] = this.link[this.last] | 0xFFFFFFFFL;
/*      */     } else {
/*  494 */       long linki = this.link[i];
/*  495 */       int prev = (int)(linki >>> 32L);
/*  496 */       int next = (int)linki;
/*  497 */       this.link[prev] = this.link[prev] ^ (this.link[prev] ^ linki & 0xFFFFFFFFL) & 0xFFFFFFFFL;
/*  498 */       this.link[next] = this.link[next] ^ (this.link[next] ^ linki & 0xFFFFFFFF00000000L) & 0xFFFFFFFF00000000L;
/*      */     } 
/*  500 */     this.link[this.first] = this.link[this.first] ^ (this.link[this.first] ^ (i & 0xFFFFFFFFL) << 32L) & 0xFFFFFFFF00000000L;
/*  501 */     this.link[i] = 0xFFFFFFFF00000000L | this.first & 0xFFFFFFFFL;
/*  502 */     this.first = i;
/*      */   }
/*      */   
/*      */   private void moveIndexToLast(int i) {
/*  506 */     if (this.size == 1 || this.last == i)
/*  507 */       return;  if (this.first == i) {
/*  508 */       this.first = (int)this.link[i];
/*      */       
/*  510 */       this.link[this.first] = this.link[this.first] | 0xFFFFFFFF00000000L;
/*      */     } else {
/*  512 */       long linki = this.link[i];
/*  513 */       int prev = (int)(linki >>> 32L);
/*  514 */       int next = (int)linki;
/*  515 */       this.link[prev] = this.link[prev] ^ (this.link[prev] ^ linki & 0xFFFFFFFFL) & 0xFFFFFFFFL;
/*  516 */       this.link[next] = this.link[next] ^ (this.link[next] ^ linki & 0xFFFFFFFF00000000L) & 0xFFFFFFFF00000000L;
/*      */     } 
/*  518 */     this.link[this.last] = this.link[this.last] ^ (this.link[this.last] ^ i & 0xFFFFFFFFL) & 0xFFFFFFFFL;
/*  519 */     this.link[i] = (this.last & 0xFFFFFFFFL) << 32L | 0xFFFFFFFFL;
/*  520 */     this.last = i;
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
/*      */   public int getAndMoveToFirst(K k) {
/*  532 */     if (k == null) {
/*  533 */       if (this.containsNullKey) {
/*  534 */         moveIndexToFirst(this.n);
/*  535 */         return this.value[this.n];
/*      */       } 
/*  537 */       return this.defRetValue;
/*      */     } 
/*      */     
/*  540 */     K[] key = this.key;
/*      */     K curr;
/*      */     int pos;
/*  543 */     if ((curr = key[pos = HashCommon.mix(k.hashCode()) & this.mask]) == null) return this.defRetValue; 
/*  544 */     if (k.equals(curr)) {
/*  545 */       moveIndexToFirst(pos);
/*  546 */       return this.value[pos];
/*      */     } 
/*      */     
/*      */     while (true) {
/*  550 */       if ((curr = key[pos = pos + 1 & this.mask]) == null) return this.defRetValue; 
/*  551 */       if (k.equals(curr)) {
/*  552 */         moveIndexToFirst(pos);
/*  553 */         return this.value[pos];
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
/*      */   public int getAndMoveToLast(K k) {
/*  567 */     if (k == null) {
/*  568 */       if (this.containsNullKey) {
/*  569 */         moveIndexToLast(this.n);
/*  570 */         return this.value[this.n];
/*      */       } 
/*  572 */       return this.defRetValue;
/*      */     } 
/*      */     
/*  575 */     K[] key = this.key;
/*      */     K curr;
/*      */     int pos;
/*  578 */     if ((curr = key[pos = HashCommon.mix(k.hashCode()) & this.mask]) == null) return this.defRetValue; 
/*  579 */     if (k.equals(curr)) {
/*  580 */       moveIndexToLast(pos);
/*  581 */       return this.value[pos];
/*      */     } 
/*      */     
/*      */     while (true) {
/*  585 */       if ((curr = key[pos = pos + 1 & this.mask]) == null) return this.defRetValue; 
/*  586 */       if (k.equals(curr)) {
/*  587 */         moveIndexToLast(pos);
/*  588 */         return this.value[pos];
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
/*      */   public int putAndMoveToFirst(K k, int v) {
/*      */     int pos;
/*  604 */     if (k == null) {
/*  605 */       if (this.containsNullKey) {
/*  606 */         moveIndexToFirst(this.n);
/*  607 */         return setValue(this.n, v);
/*      */       } 
/*  609 */       this.containsNullKey = true;
/*  610 */       pos = this.n;
/*      */     } else {
/*      */       
/*  613 */       K[] key = this.key;
/*      */       K curr;
/*  615 */       if ((curr = key[pos = HashCommon.mix(k.hashCode()) & this.mask]) != null) {
/*  616 */         if (curr.equals(k)) {
/*  617 */           moveIndexToFirst(pos);
/*  618 */           return setValue(pos, v);
/*      */         } 
/*  620 */         while ((curr = key[pos = pos + 1 & this.mask]) != null) { if (curr.equals(k)) {
/*  621 */             moveIndexToFirst(pos);
/*  622 */             return setValue(pos, v);
/*      */           }  }
/*      */       
/*      */       } 
/*  626 */     }  this.key[pos] = k;
/*  627 */     this.value[pos] = v;
/*  628 */     if (this.size == 0) {
/*  629 */       this.first = this.last = pos;
/*      */       
/*  631 */       this.link[pos] = -1L;
/*      */     } else {
/*  633 */       this.link[this.first] = this.link[this.first] ^ (this.link[this.first] ^ (pos & 0xFFFFFFFFL) << 32L) & 0xFFFFFFFF00000000L;
/*  634 */       this.link[pos] = 0xFFFFFFFF00000000L | this.first & 0xFFFFFFFFL;
/*  635 */       this.first = pos;
/*      */     } 
/*  637 */     if (this.size++ >= this.maxFill) rehash(HashCommon.arraySize(this.size, this.f));
/*      */     
/*  639 */     return this.defRetValue;
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
/*      */   public int putAndMoveToLast(K k, int v) {
/*      */     int pos;
/*  653 */     if (k == null) {
/*  654 */       if (this.containsNullKey) {
/*  655 */         moveIndexToLast(this.n);
/*  656 */         return setValue(this.n, v);
/*      */       } 
/*  658 */       this.containsNullKey = true;
/*  659 */       pos = this.n;
/*      */     } else {
/*      */       
/*  662 */       K[] key = this.key;
/*      */       K curr;
/*  664 */       if ((curr = key[pos = HashCommon.mix(k.hashCode()) & this.mask]) != null) {
/*  665 */         if (curr.equals(k)) {
/*  666 */           moveIndexToLast(pos);
/*  667 */           return setValue(pos, v);
/*      */         } 
/*  669 */         while ((curr = key[pos = pos + 1 & this.mask]) != null) { if (curr.equals(k)) {
/*  670 */             moveIndexToLast(pos);
/*  671 */             return setValue(pos, v);
/*      */           }  }
/*      */       
/*      */       } 
/*  675 */     }  this.key[pos] = k;
/*  676 */     this.value[pos] = v;
/*  677 */     if (this.size == 0) {
/*  678 */       this.first = this.last = pos;
/*      */       
/*  680 */       this.link[pos] = -1L;
/*      */     } else {
/*  682 */       this.link[this.last] = this.link[this.last] ^ (this.link[this.last] ^ pos & 0xFFFFFFFFL) & 0xFFFFFFFFL;
/*  683 */       this.link[pos] = (this.last & 0xFFFFFFFFL) << 32L | 0xFFFFFFFFL;
/*  684 */       this.last = pos;
/*      */     } 
/*  686 */     if (this.size++ >= this.maxFill) rehash(HashCommon.arraySize(this.size, this.f));
/*      */     
/*  688 */     return this.defRetValue;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public int getInt(Object k) {
/*  694 */     if (k == null) return this.containsNullKey ? this.value[this.n] : this.defRetValue;
/*      */     
/*  696 */     K[] key = this.key;
/*      */     K curr;
/*      */     int pos;
/*  699 */     if ((curr = key[pos = HashCommon.mix(k.hashCode()) & this.mask]) == null) return this.defRetValue; 
/*  700 */     if (k.equals(curr)) return this.value[pos];
/*      */     
/*      */     while (true) {
/*  703 */       if ((curr = key[pos = pos + 1 & this.mask]) == null) return this.defRetValue; 
/*  704 */       if (k.equals(curr)) return this.value[pos];
/*      */     
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   public boolean containsKey(Object k) {
/*  711 */     if (k == null) return this.containsNullKey;
/*      */     
/*  713 */     K[] key = this.key;
/*      */     K curr;
/*      */     int pos;
/*  716 */     if ((curr = key[pos = HashCommon.mix(k.hashCode()) & this.mask]) == null) return false; 
/*  717 */     if (k.equals(curr)) return true;
/*      */     
/*      */     while (true) {
/*  720 */       if ((curr = key[pos = pos + 1 & this.mask]) == null) return false; 
/*  721 */       if (k.equals(curr)) return true;
/*      */     
/*      */     } 
/*      */   }
/*      */   
/*      */   public boolean containsValue(int v) {
/*  727 */     int[] value = this.value;
/*  728 */     K[] key = this.key;
/*  729 */     if (this.containsNullKey && value[this.n] == v) return true; 
/*  730 */     for (int i = this.n; i-- != 0;) { if (key[i] != null && value[i] == v) return true;  }
/*  731 */      return false;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public int getOrDefault(Object k, int defaultValue) {
/*  738 */     if (k == null) return this.containsNullKey ? this.value[this.n] : defaultValue;
/*      */     
/*  740 */     K[] key = this.key;
/*      */     K curr;
/*      */     int pos;
/*  743 */     if ((curr = key[pos = HashCommon.mix(k.hashCode()) & this.mask]) == null) return defaultValue; 
/*  744 */     if (k.equals(curr)) return this.value[pos];
/*      */     
/*      */     while (true) {
/*  747 */       if ((curr = key[pos = pos + 1 & this.mask]) == null) return defaultValue; 
/*  748 */       if (k.equals(curr)) return this.value[pos];
/*      */     
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   public int putIfAbsent(K k, int v) {
/*  755 */     int pos = find(k);
/*  756 */     if (pos >= 0) return this.value[pos]; 
/*  757 */     insert(-pos - 1, k, v);
/*  758 */     return this.defRetValue;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean remove(Object k, int v) {
/*  765 */     if (k == null) {
/*  766 */       if (this.containsNullKey && v == this.value[this.n]) {
/*  767 */         removeNullEntry();
/*  768 */         return true;
/*      */       } 
/*  770 */       return false;
/*      */     } 
/*      */     
/*  773 */     K[] key = this.key;
/*      */     K curr;
/*      */     int pos;
/*  776 */     if ((curr = key[pos = HashCommon.mix(k.hashCode()) & this.mask]) == null) return false; 
/*  777 */     if (k.equals(curr) && v == this.value[pos]) {
/*  778 */       removeEntry(pos);
/*  779 */       return true;
/*      */     } 
/*      */     while (true) {
/*  782 */       if ((curr = key[pos = pos + 1 & this.mask]) == null) return false; 
/*  783 */       if (k.equals(curr) && v == this.value[pos]) {
/*  784 */         removeEntry(pos);
/*  785 */         return true;
/*      */       } 
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean replace(K k, int oldValue, int v) {
/*  793 */     int pos = find(k);
/*  794 */     if (pos < 0 || oldValue != this.value[pos]) return false; 
/*  795 */     this.value[pos] = v;
/*  796 */     return true;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public int replace(K k, int v) {
/*  802 */     int pos = find(k);
/*  803 */     if (pos < 0) return this.defRetValue; 
/*  804 */     int oldValue = this.value[pos];
/*  805 */     this.value[pos] = v;
/*  806 */     return oldValue;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public int computeIfAbsent(K k, ToIntFunction<? super K> mappingFunction) {
/*  812 */     Objects.requireNonNull(mappingFunction);
/*  813 */     int pos = find(k);
/*  814 */     if (pos >= 0) return this.value[pos]; 
/*  815 */     int newValue = mappingFunction.applyAsInt(k);
/*  816 */     insert(-pos - 1, k, newValue);
/*  817 */     return newValue;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public int computeIfAbsent(K key, Object2IntFunction<? super K> mappingFunction) {
/*  823 */     Objects.requireNonNull(mappingFunction);
/*  824 */     int pos = find(key);
/*  825 */     if (pos >= 0) return this.value[pos]; 
/*  826 */     if (!mappingFunction.containsKey(key)) return this.defRetValue; 
/*  827 */     int newValue = mappingFunction.getInt(key);
/*  828 */     insert(-pos - 1, key, newValue);
/*  829 */     return newValue;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public int computeIntIfPresent(K k, BiFunction<? super K, ? super Integer, ? extends Integer> remappingFunction) {
/*  835 */     Objects.requireNonNull(remappingFunction);
/*  836 */     int pos = find(k);
/*  837 */     if (pos < 0) return this.defRetValue; 
/*  838 */     Integer newValue = remappingFunction.apply(k, Integer.valueOf(this.value[pos]));
/*  839 */     if (newValue == null) {
/*  840 */       if (k == null) { removeNullEntry(); }
/*  841 */       else { removeEntry(pos); }
/*  842 */        return this.defRetValue;
/*      */     } 
/*  844 */     this.value[pos] = newValue.intValue(); return newValue.intValue();
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public int computeInt(K k, BiFunction<? super K, ? super Integer, ? extends Integer> remappingFunction) {
/*  850 */     Objects.requireNonNull(remappingFunction);
/*  851 */     int pos = find(k);
/*  852 */     Integer newValue = remappingFunction.apply(k, (pos >= 0) ? Integer.valueOf(this.value[pos]) : null);
/*  853 */     if (newValue == null) {
/*  854 */       if (pos >= 0)
/*  855 */         if (k == null) { removeNullEntry(); }
/*  856 */         else { removeEntry(pos); }
/*      */          
/*  858 */       return this.defRetValue;
/*      */     } 
/*  860 */     int newVal = newValue.intValue();
/*  861 */     if (pos < 0) {
/*  862 */       insert(-pos - 1, k, newVal);
/*  863 */       return newVal;
/*      */     } 
/*  865 */     this.value[pos] = newVal; return newVal;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public int merge(K k, int v, BiFunction<? super Integer, ? super Integer, ? extends Integer> remappingFunction) {
/*  871 */     Objects.requireNonNull(remappingFunction);
/*      */     
/*  873 */     int pos = find(k);
/*  874 */     if (pos < 0) {
/*  875 */       if (pos < 0) { insert(-pos - 1, k, v); }
/*  876 */       else { this.value[pos] = v; }
/*  877 */        return v;
/*      */     } 
/*  879 */     Integer newValue = remappingFunction.apply(Integer.valueOf(this.value[pos]), Integer.valueOf(v));
/*  880 */     if (newValue == null) {
/*  881 */       if (k == null) { removeNullEntry(); }
/*  882 */       else { removeEntry(pos); }
/*  883 */        return this.defRetValue;
/*      */     } 
/*  885 */     this.value[pos] = newValue.intValue(); return newValue.intValue();
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
/*  896 */     if (this.size == 0)
/*  897 */       return;  this.size = 0;
/*  898 */     this.containsNullKey = false;
/*  899 */     Arrays.fill((Object[])this.key, (Object)null);
/*  900 */     this.first = this.last = -1;
/*      */   }
/*      */ 
/*      */   
/*      */   public int size() {
/*  905 */     return this.size;
/*      */   }
/*      */ 
/*      */   
/*      */   public boolean isEmpty() {
/*  910 */     return (this.size == 0);
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
/*  923 */       this.index = index;
/*      */     }
/*      */ 
/*      */     
/*      */     MapEntry() {}
/*      */ 
/*      */     
/*      */     public K getKey() {
/*  931 */       return Object2IntLinkedOpenHashMap.this.key[this.index];
/*      */     }
/*      */ 
/*      */     
/*      */     public K left() {
/*  936 */       return Object2IntLinkedOpenHashMap.this.key[this.index];
/*      */     }
/*      */ 
/*      */     
/*      */     public int getIntValue() {
/*  941 */       return Object2IntLinkedOpenHashMap.this.value[this.index];
/*      */     }
/*      */ 
/*      */     
/*      */     public int rightInt() {
/*  946 */       return Object2IntLinkedOpenHashMap.this.value[this.index];
/*      */     }
/*      */ 
/*      */     
/*      */     public int setValue(int v) {
/*  951 */       int oldValue = Object2IntLinkedOpenHashMap.this.value[this.index];
/*  952 */       Object2IntLinkedOpenHashMap.this.value[this.index] = v;
/*  953 */       return oldValue;
/*      */     }
/*      */ 
/*      */     
/*      */     public ObjectIntPair<K> right(int v) {
/*  958 */       Object2IntLinkedOpenHashMap.this.value[this.index] = v;
/*  959 */       return this;
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
/*  970 */       return Integer.valueOf(Object2IntLinkedOpenHashMap.this.value[this.index]);
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
/*  981 */       return Integer.valueOf(setValue(v.intValue()));
/*      */     }
/*      */ 
/*      */ 
/*      */     
/*      */     public boolean equals(Object o) {
/*  987 */       if (!(o instanceof Map.Entry)) return false; 
/*  988 */       Map.Entry<K, Integer> e = (Map.Entry<K, Integer>)o;
/*  989 */       return (Objects.equals(Object2IntLinkedOpenHashMap.this.key[this.index], e.getKey()) && Object2IntLinkedOpenHashMap.this.value[this.index] == ((Integer)e.getValue()).intValue());
/*      */     }
/*      */ 
/*      */     
/*      */     public int hashCode() {
/*  994 */       return ((Object2IntLinkedOpenHashMap.this.key[this.index] == null) ? 0 : Object2IntLinkedOpenHashMap.this.key[this.index].hashCode()) ^ Object2IntLinkedOpenHashMap.this.value[this.index];
/*      */     }
/*      */ 
/*      */     
/*      */     public String toString() {
/*  999 */       return (new StringBuilder()).append(Object2IntLinkedOpenHashMap.this.key[this.index]).append("=>").append(Object2IntLinkedOpenHashMap.this.value[this.index]).toString();
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
/* 1010 */     if (this.size == 0) {
/* 1011 */       this.first = this.last = -1;
/*      */       return;
/*      */     } 
/* 1014 */     if (this.first == i) {
/* 1015 */       this.first = (int)this.link[i];
/* 1016 */       if (0 <= this.first)
/*      */       {
/* 1018 */         this.link[this.first] = this.link[this.first] | 0xFFFFFFFF00000000L;
/*      */       }
/*      */       return;
/*      */     } 
/* 1022 */     if (this.last == i) {
/* 1023 */       this.last = (int)(this.link[i] >>> 32L);
/* 1024 */       if (0 <= this.last)
/*      */       {
/* 1026 */         this.link[this.last] = this.link[this.last] | 0xFFFFFFFFL;
/*      */       }
/*      */       return;
/*      */     } 
/* 1030 */     long linki = this.link[i];
/* 1031 */     int prev = (int)(linki >>> 32L);
/* 1032 */     int next = (int)linki;
/* 1033 */     this.link[prev] = this.link[prev] ^ (this.link[prev] ^ linki & 0xFFFFFFFFL) & 0xFFFFFFFFL;
/* 1034 */     this.link[next] = this.link[next] ^ (this.link[next] ^ linki & 0xFFFFFFFF00000000L) & 0xFFFFFFFF00000000L;
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
/* 1046 */     if (this.size == 1) {
/* 1047 */       this.first = this.last = d;
/*      */       
/* 1049 */       this.link[d] = -1L;
/*      */       return;
/*      */     } 
/* 1052 */     if (this.first == s) {
/* 1053 */       this.first = d;
/* 1054 */       this.link[(int)this.link[s]] = this.link[(int)this.link[s]] ^ (this.link[(int)this.link[s]] ^ (d & 0xFFFFFFFFL) << 32L) & 0xFFFFFFFF00000000L;
/* 1055 */       this.link[d] = this.link[s];
/*      */       return;
/*      */     } 
/* 1058 */     if (this.last == s) {
/* 1059 */       this.last = d;
/* 1060 */       this.link[(int)(this.link[s] >>> 32L)] = this.link[(int)(this.link[s] >>> 32L)] ^ (this.link[(int)(this.link[s] >>> 32L)] ^ d & 0xFFFFFFFFL) & 0xFFFFFFFFL;
/* 1061 */       this.link[d] = this.link[s];
/*      */       return;
/*      */     } 
/* 1064 */     long links = this.link[s];
/* 1065 */     int prev = (int)(links >>> 32L);
/* 1066 */     int next = (int)links;
/* 1067 */     this.link[prev] = this.link[prev] ^ (this.link[prev] ^ d & 0xFFFFFFFFL) & 0xFFFFFFFFL;
/* 1068 */     this.link[next] = this.link[next] ^ (this.link[next] ^ (d & 0xFFFFFFFFL) << 32L) & 0xFFFFFFFF00000000L;
/* 1069 */     this.link[d] = links;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public K firstKey() {
/* 1079 */     if (this.size == 0) throw new NoSuchElementException(); 
/* 1080 */     return this.key[this.first];
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public K lastKey() {
/* 1090 */     if (this.size == 0) throw new NoSuchElementException(); 
/* 1091 */     return this.key[this.last];
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Object2IntSortedMap<K> tailMap(K from) {
/* 1101 */     throw new UnsupportedOperationException();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Object2IntSortedMap<K> headMap(K to) {
/* 1111 */     throw new UnsupportedOperationException();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Object2IntSortedMap<K> subMap(K from, K to) {
/* 1121 */     throw new UnsupportedOperationException();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Comparator<? super K> comparator() {
/* 1131 */     return null;
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
/* 1146 */     int prev = -1;
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 1151 */     int next = -1;
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 1156 */     int curr = -1;
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 1161 */     int index = -1;
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     protected MapIterator() {
/* 1167 */       this.next = Object2IntLinkedOpenHashMap.this.first;
/* 1168 */       this.index = 0;
/*      */     }
/*      */     
/*      */     private MapIterator(K from) {
/* 1172 */       if (from == null) {
/* 1173 */         if (Object2IntLinkedOpenHashMap.this.containsNullKey) {
/* 1174 */           this.next = (int)Object2IntLinkedOpenHashMap.this.link[Object2IntLinkedOpenHashMap.this.n];
/* 1175 */           this.prev = Object2IntLinkedOpenHashMap.this.n; return;
/*      */         } 
/* 1177 */         throw new NoSuchElementException("The key " + from + " does not belong to this map.");
/*      */       } 
/* 1179 */       if (Objects.equals(Object2IntLinkedOpenHashMap.this.key[Object2IntLinkedOpenHashMap.this.last], from)) {
/* 1180 */         this.prev = Object2IntLinkedOpenHashMap.this.last;
/* 1181 */         this.index = Object2IntLinkedOpenHashMap.this.size;
/*      */         
/*      */         return;
/*      */       } 
/* 1185 */       int pos = HashCommon.mix(from.hashCode()) & Object2IntLinkedOpenHashMap.this.mask;
/*      */       
/* 1187 */       while (Object2IntLinkedOpenHashMap.this.key[pos] != null) {
/* 1188 */         if (Object2IntLinkedOpenHashMap.this.key[pos].equals(from)) {
/*      */           
/* 1190 */           this.next = (int)Object2IntLinkedOpenHashMap.this.link[pos];
/* 1191 */           this.prev = pos;
/*      */           return;
/*      */         } 
/* 1194 */         pos = pos + 1 & Object2IntLinkedOpenHashMap.this.mask;
/*      */       } 
/* 1196 */       throw new NoSuchElementException("The key " + from + " does not belong to this map.");
/*      */     }
/*      */     
/*      */     public boolean hasNext() {
/* 1200 */       return (this.next != -1);
/*      */     }
/*      */     
/*      */     public boolean hasPrevious() {
/* 1204 */       return (this.prev != -1);
/*      */     }
/*      */     
/*      */     private final void ensureIndexKnown() {
/* 1208 */       if (this.index >= 0)
/* 1209 */         return;  if (this.prev == -1) {
/* 1210 */         this.index = 0;
/*      */         return;
/*      */       } 
/* 1213 */       if (this.next == -1) {
/* 1214 */         this.index = Object2IntLinkedOpenHashMap.this.size;
/*      */         return;
/*      */       } 
/* 1217 */       int pos = Object2IntLinkedOpenHashMap.this.first;
/* 1218 */       this.index = 1;
/* 1219 */       while (pos != this.prev) {
/* 1220 */         pos = (int)Object2IntLinkedOpenHashMap.this.link[pos];
/* 1221 */         this.index++;
/*      */       } 
/*      */     }
/*      */     
/*      */     public int nextIndex() {
/* 1226 */       ensureIndexKnown();
/* 1227 */       return this.index;
/*      */     }
/*      */     
/*      */     public int previousIndex() {
/* 1231 */       ensureIndexKnown();
/* 1232 */       return this.index - 1;
/*      */     }
/*      */     
/*      */     public int nextEntry() {
/* 1236 */       if (!hasNext()) throw new NoSuchElementException(); 
/* 1237 */       this.curr = this.next;
/* 1238 */       this.next = (int)Object2IntLinkedOpenHashMap.this.link[this.curr];
/* 1239 */       this.prev = this.curr;
/* 1240 */       if (this.index >= 0) this.index++; 
/* 1241 */       return this.curr;
/*      */     }
/*      */     
/*      */     public int previousEntry() {
/* 1245 */       if (!hasPrevious()) throw new NoSuchElementException(); 
/* 1246 */       this.curr = this.prev;
/* 1247 */       this.prev = (int)(Object2IntLinkedOpenHashMap.this.link[this.curr] >>> 32L);
/* 1248 */       this.next = this.curr;
/* 1249 */       if (this.index >= 0) this.index--; 
/* 1250 */       return this.curr;
/*      */     }
/*      */     
/*      */     public void forEachRemaining(ConsumerType action) {
/* 1254 */       while (hasNext()) {
/* 1255 */         this.curr = this.next;
/* 1256 */         this.next = (int)Object2IntLinkedOpenHashMap.this.link[this.curr];
/* 1257 */         this.prev = this.curr;
/* 1258 */         if (this.index >= 0) this.index++; 
/* 1259 */         acceptOnIndex(action, this.curr);
/*      */       } 
/*      */     }
/*      */     
/*      */     public void remove() {
/* 1264 */       ensureIndexKnown();
/* 1265 */       if (this.curr == -1) throw new IllegalStateException(); 
/* 1266 */       if (this.curr == this.prev)
/*      */       
/*      */       { 
/* 1269 */         this.index--;
/* 1270 */         this.prev = (int)(Object2IntLinkedOpenHashMap.this.link[this.curr] >>> 32L); }
/* 1271 */       else { this.next = (int)Object2IntLinkedOpenHashMap.this.link[this.curr]; }
/* 1272 */        Object2IntLinkedOpenHashMap.this.size--;
/*      */ 
/*      */       
/* 1275 */       if (this.prev == -1) { Object2IntLinkedOpenHashMap.this.first = this.next; }
/* 1276 */       else { Object2IntLinkedOpenHashMap.this.link[this.prev] = Object2IntLinkedOpenHashMap.this.link[this.prev] ^ (Object2IntLinkedOpenHashMap.this.link[this.prev] ^ this.next & 0xFFFFFFFFL) & 0xFFFFFFFFL; }
/* 1277 */        if (this.next == -1) { Object2IntLinkedOpenHashMap.this.last = this.prev; }
/* 1278 */       else { Object2IntLinkedOpenHashMap.this.link[this.next] = Object2IntLinkedOpenHashMap.this.link[this.next] ^ (Object2IntLinkedOpenHashMap.this.link[this.next] ^ (this.prev & 0xFFFFFFFFL) << 32L) & 0xFFFFFFFF00000000L; }
/* 1279 */        int pos = this.curr;
/* 1280 */       this.curr = -1;
/* 1281 */       if (pos == Object2IntLinkedOpenHashMap.this.n) {
/* 1282 */         Object2IntLinkedOpenHashMap.this.containsNullKey = false;
/* 1283 */         Object2IntLinkedOpenHashMap.this.key[Object2IntLinkedOpenHashMap.this.n] = null;
/*      */       } else {
/*      */         
/* 1286 */         K[] key = Object2IntLinkedOpenHashMap.this.key; while (true) {
/*      */           K curr;
/*      */           int last;
/* 1289 */           pos = (last = pos) + 1 & Object2IntLinkedOpenHashMap.this.mask;
/*      */           while (true) {
/* 1291 */             if ((curr = key[pos]) == null) {
/* 1292 */               key[last] = null;
/*      */               return;
/*      */             } 
/* 1295 */             int slot = HashCommon.mix(curr.hashCode()) & Object2IntLinkedOpenHashMap.this.mask;
/* 1296 */             if ((last <= pos) ? (last >= slot || slot > pos) : (last >= slot && slot > pos))
/* 1297 */               break;  pos = pos + 1 & Object2IntLinkedOpenHashMap.this.mask;
/*      */           } 
/* 1299 */           key[last] = curr;
/* 1300 */           Object2IntLinkedOpenHashMap.this.value[last] = Object2IntLinkedOpenHashMap.this.value[pos];
/* 1301 */           if (this.next == pos) this.next = last; 
/* 1302 */           if (this.prev == pos) this.prev = last; 
/* 1303 */           Object2IntLinkedOpenHashMap.this.fixPointers(pos, last);
/*      */         } 
/*      */       } 
/*      */     }
/*      */     
/*      */     public int skip(int n) {
/* 1309 */       int i = n;
/* 1310 */       for (; i-- != 0 && hasNext(); nextEntry());
/* 1311 */       return n - i - 1;
/*      */     }
/*      */     
/*      */     public int back(int n) {
/* 1315 */       int i = n;
/* 1316 */       for (; i-- != 0 && hasPrevious(); previousEntry());
/* 1317 */       return n - i - 1;
/*      */     }
/*      */     
/*      */     public void set(Object2IntMap.Entry<K> ok) {
/* 1321 */       throw new UnsupportedOperationException();
/*      */     }
/*      */     
/*      */     public void add(Object2IntMap.Entry<K> ok) {
/* 1325 */       throw new UnsupportedOperationException();
/*      */     }
/*      */     
/*      */     abstract void acceptOnIndex(ConsumerType param1ConsumerType, int param1Int); }
/*      */   
/*      */   private final class EntryIterator extends MapIterator<Consumer<? super Object2IntMap.Entry<K>>> implements ObjectListIterator<Object2IntMap.Entry<K>> {
/*      */     private Object2IntLinkedOpenHashMap<K>.MapEntry entry;
/*      */     
/*      */     public EntryIterator() {}
/*      */     
/*      */     public EntryIterator(K from) {
/* 1336 */       super(from);
/*      */     }
/*      */ 
/*      */ 
/*      */     
/*      */     final void acceptOnIndex(Consumer<? super Object2IntMap.Entry<K>> action, int index) {
/* 1342 */       action.accept(new Object2IntLinkedOpenHashMap.MapEntry(index));
/*      */     }
/*      */ 
/*      */     
/*      */     public Object2IntLinkedOpenHashMap<K>.MapEntry next() {
/* 1347 */       return this.entry = new Object2IntLinkedOpenHashMap.MapEntry(nextEntry());
/*      */     }
/*      */ 
/*      */     
/*      */     public Object2IntLinkedOpenHashMap<K>.MapEntry previous() {
/* 1352 */       return this.entry = new Object2IntLinkedOpenHashMap.MapEntry(previousEntry());
/*      */     }
/*      */ 
/*      */     
/*      */     public void remove() {
/* 1357 */       super.remove();
/* 1358 */       this.entry.index = -1;
/*      */     }
/*      */   }
/*      */   
/*      */   private final class FastEntryIterator extends MapIterator<Consumer<? super Object2IntMap.Entry<K>>> implements ObjectListIterator<Object2IntMap.Entry<K>> {
/* 1363 */     final Object2IntLinkedOpenHashMap<K>.MapEntry entry = new Object2IntLinkedOpenHashMap.MapEntry();
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public FastEntryIterator(K from) {
/* 1369 */       super(from);
/*      */     }
/*      */ 
/*      */ 
/*      */     
/*      */     final void acceptOnIndex(Consumer<? super Object2IntMap.Entry<K>> action, int index) {
/* 1375 */       this.entry.index = index;
/* 1376 */       action.accept(this.entry);
/*      */     }
/*      */ 
/*      */     
/*      */     public Object2IntLinkedOpenHashMap<K>.MapEntry next() {
/* 1381 */       this.entry.index = nextEntry();
/* 1382 */       return this.entry;
/*      */     }
/*      */ 
/*      */     
/*      */     public Object2IntLinkedOpenHashMap<K>.MapEntry previous() {
/* 1387 */       this.entry.index = previousEntry();
/* 1388 */       return this.entry;
/*      */     }
/*      */     
/*      */     public FastEntryIterator() {} }
/*      */   
/*      */   private final class MapEntrySet extends AbstractObjectSortedSet<Object2IntMap.Entry<K>> implements Object2IntSortedMap.FastSortedEntrySet<K> {
/*      */     private static final int SPLITERATOR_CHARACTERISTICS = 81;
/*      */     
/*      */     public ObjectBidirectionalIterator<Object2IntMap.Entry<K>> iterator() {
/* 1397 */       return new Object2IntLinkedOpenHashMap.EntryIterator();
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
/*      */     public ObjectSpliterator<Object2IntMap.Entry<K>> spliterator() {
/* 1415 */       return ObjectSpliterators.asSpliterator(iterator(), Size64.sizeOf(Object2IntLinkedOpenHashMap.this), 81);
/*      */     }
/*      */ 
/*      */     
/*      */     public Comparator<? super Object2IntMap.Entry<K>> comparator() {
/* 1420 */       return null;
/*      */     }
/*      */ 
/*      */     
/*      */     public ObjectSortedSet<Object2IntMap.Entry<K>> subSet(Object2IntMap.Entry<K> fromElement, Object2IntMap.Entry<K> toElement) {
/* 1425 */       throw new UnsupportedOperationException();
/*      */     }
/*      */ 
/*      */     
/*      */     public ObjectSortedSet<Object2IntMap.Entry<K>> headSet(Object2IntMap.Entry<K> toElement) {
/* 1430 */       throw new UnsupportedOperationException();
/*      */     }
/*      */ 
/*      */     
/*      */     public ObjectSortedSet<Object2IntMap.Entry<K>> tailSet(Object2IntMap.Entry<K> fromElement) {
/* 1435 */       throw new UnsupportedOperationException();
/*      */     }
/*      */ 
/*      */     
/*      */     public Object2IntMap.Entry<K> first() {
/* 1440 */       if (Object2IntLinkedOpenHashMap.this.size == 0) throw new NoSuchElementException(); 
/* 1441 */       return new Object2IntLinkedOpenHashMap.MapEntry(Object2IntLinkedOpenHashMap.this.first);
/*      */     }
/*      */ 
/*      */     
/*      */     public Object2IntMap.Entry<K> last() {
/* 1446 */       if (Object2IntLinkedOpenHashMap.this.size == 0) throw new NoSuchElementException(); 
/* 1447 */       return new Object2IntLinkedOpenHashMap.MapEntry(Object2IntLinkedOpenHashMap.this.last);
/*      */     }
/*      */ 
/*      */ 
/*      */     
/*      */     public boolean contains(Object o) {
/* 1453 */       if (!(o instanceof Map.Entry)) return false; 
/* 1454 */       Map.Entry<?, ?> e = (Map.Entry<?, ?>)o;
/* 1455 */       if (e.getValue() == null || !(e.getValue() instanceof Integer)) return false; 
/* 1456 */       K k = (K)e.getKey();
/* 1457 */       int v = ((Integer)e.getValue()).intValue();
/* 1458 */       if (k == null) return (Object2IntLinkedOpenHashMap.this.containsNullKey && Object2IntLinkedOpenHashMap.this.value[Object2IntLinkedOpenHashMap.this.n] == v);
/*      */       
/* 1460 */       K[] key = Object2IntLinkedOpenHashMap.this.key;
/*      */       K curr;
/*      */       int pos;
/* 1463 */       if ((curr = key[pos = HashCommon.mix(k.hashCode()) & Object2IntLinkedOpenHashMap.this.mask]) == null) return false; 
/* 1464 */       if (k.equals(curr)) return (Object2IntLinkedOpenHashMap.this.value[pos] == v);
/*      */       
/*      */       while (true) {
/* 1467 */         if ((curr = key[pos = pos + 1 & Object2IntLinkedOpenHashMap.this.mask]) == null) return false; 
/* 1468 */         if (k.equals(curr)) return (Object2IntLinkedOpenHashMap.this.value[pos] == v);
/*      */       
/*      */       } 
/*      */     }
/*      */ 
/*      */     
/*      */     public boolean remove(Object o) {
/* 1475 */       if (!(o instanceof Map.Entry)) return false; 
/* 1476 */       Map.Entry<?, ?> e = (Map.Entry<?, ?>)o;
/* 1477 */       if (e.getValue() == null || !(e.getValue() instanceof Integer)) return false; 
/* 1478 */       K k = (K)e.getKey();
/* 1479 */       int v = ((Integer)e.getValue()).intValue();
/* 1480 */       if (k == null) {
/* 1481 */         if (Object2IntLinkedOpenHashMap.this.containsNullKey && Object2IntLinkedOpenHashMap.this.value[Object2IntLinkedOpenHashMap.this.n] == v) {
/* 1482 */           Object2IntLinkedOpenHashMap.this.removeNullEntry();
/* 1483 */           return true;
/*      */         } 
/* 1485 */         return false;
/*      */       } 
/*      */       
/* 1488 */       K[] key = Object2IntLinkedOpenHashMap.this.key;
/*      */       K curr;
/*      */       int pos;
/* 1491 */       if ((curr = key[pos = HashCommon.mix(k.hashCode()) & Object2IntLinkedOpenHashMap.this.mask]) == null) return false; 
/* 1492 */       if (curr.equals(k)) {
/* 1493 */         if (Object2IntLinkedOpenHashMap.this.value[pos] == v) {
/* 1494 */           Object2IntLinkedOpenHashMap.this.removeEntry(pos);
/* 1495 */           return true;
/*      */         } 
/* 1497 */         return false;
/*      */       } 
/*      */       while (true) {
/* 1500 */         if ((curr = key[pos = pos + 1 & Object2IntLinkedOpenHashMap.this.mask]) == null) return false; 
/* 1501 */         if (curr.equals(k) && 
/* 1502 */           Object2IntLinkedOpenHashMap.this.value[pos] == v) {
/* 1503 */           Object2IntLinkedOpenHashMap.this.removeEntry(pos);
/* 1504 */           return true;
/*      */         } 
/*      */       } 
/*      */     }
/*      */ 
/*      */ 
/*      */     
/*      */     public int size() {
/* 1512 */       return Object2IntLinkedOpenHashMap.this.size;
/*      */     }
/*      */ 
/*      */     
/*      */     public void clear() {
/* 1517 */       Object2IntLinkedOpenHashMap.this.clear();
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
/*      */     public ObjectListIterator<Object2IntMap.Entry<K>> iterator(Object2IntMap.Entry<K> from) {
/* 1530 */       return new Object2IntLinkedOpenHashMap.EntryIterator(from.getKey());
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public ObjectListIterator<Object2IntMap.Entry<K>> fastIterator() {
/* 1541 */       return new Object2IntLinkedOpenHashMap.FastEntryIterator();
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
/*      */     public ObjectListIterator<Object2IntMap.Entry<K>> fastIterator(Object2IntMap.Entry<K> from) {
/* 1554 */       return new Object2IntLinkedOpenHashMap.FastEntryIterator(from.getKey());
/*      */     }
/*      */ 
/*      */ 
/*      */     
/*      */     public void forEach(Consumer<? super Object2IntMap.Entry<K>> consumer) {
/* 1560 */       for (int i = Object2IntLinkedOpenHashMap.this.size, next = Object2IntLinkedOpenHashMap.this.first; i-- != 0; ) {
/* 1561 */         int curr = next;
/* 1562 */         next = (int)Object2IntLinkedOpenHashMap.this.link[curr];
/* 1563 */         consumer.accept(new Object2IntLinkedOpenHashMap.MapEntry(curr));
/*      */       } 
/*      */     }
/*      */ 
/*      */ 
/*      */     
/*      */     public void fastForEach(Consumer<? super Object2IntMap.Entry<K>> consumer) {
/* 1570 */       Object2IntLinkedOpenHashMap<K>.MapEntry entry = new Object2IntLinkedOpenHashMap.MapEntry();
/* 1571 */       for (int i = Object2IntLinkedOpenHashMap.this.size, next = Object2IntLinkedOpenHashMap.this.first; i-- != 0; ) {
/* 1572 */         entry.index = next;
/* 1573 */         next = (int)Object2IntLinkedOpenHashMap.this.link[next];
/* 1574 */         consumer.accept(entry);
/*      */       } 
/*      */     }
/*      */   }
/*      */ 
/*      */   
/*      */   public Object2IntSortedMap.FastSortedEntrySet<K> object2IntEntrySet() {
/* 1581 */     if (this.entries == null) this.entries = new MapEntrySet(); 
/* 1582 */     return this.entries;
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
/* 1595 */       super(k);
/*      */     }
/*      */ 
/*      */     
/*      */     public K previous() {
/* 1600 */       return Object2IntLinkedOpenHashMap.this.key[previousEntry()];
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
/* 1612 */       action.accept(Object2IntLinkedOpenHashMap.this.key[index]);
/*      */     }
/*      */ 
/*      */     
/*      */     public K next() {
/* 1617 */       return Object2IntLinkedOpenHashMap.this.key[nextEntry()];
/*      */     } }
/*      */   
/*      */   private final class KeySet extends AbstractObjectSortedSet<K> {
/*      */     private static final int SPLITERATOR_CHARACTERISTICS = 81;
/*      */     
/*      */     private KeySet() {}
/*      */     
/*      */     public ObjectListIterator<K> iterator(K from) {
/* 1626 */       return new Object2IntLinkedOpenHashMap.KeyIterator(from);
/*      */     }
/*      */ 
/*      */     
/*      */     public ObjectListIterator<K> iterator() {
/* 1631 */       return new Object2IntLinkedOpenHashMap.KeyIterator();
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public ObjectSpliterator<K> spliterator() {
/* 1641 */       return ObjectSpliterators.asSpliterator(iterator(), Size64.sizeOf(Object2IntLinkedOpenHashMap.this), 81);
/*      */     }
/*      */ 
/*      */ 
/*      */     
/*      */     public void forEach(Consumer<? super K> consumer) {
/* 1647 */       for (int i = Object2IntLinkedOpenHashMap.this.size, next = Object2IntLinkedOpenHashMap.this.first; i-- != 0; ) {
/* 1648 */         int curr = next;
/* 1649 */         next = (int)Object2IntLinkedOpenHashMap.this.link[curr];
/* 1650 */         consumer.accept(Object2IntLinkedOpenHashMap.this.key[curr]);
/*      */       } 
/*      */     }
/*      */ 
/*      */     
/*      */     public int size() {
/* 1656 */       return Object2IntLinkedOpenHashMap.this.size;
/*      */     }
/*      */ 
/*      */     
/*      */     public boolean contains(Object k) {
/* 1661 */       return Object2IntLinkedOpenHashMap.this.containsKey(k);
/*      */     }
/*      */ 
/*      */     
/*      */     public boolean remove(Object k) {
/* 1666 */       int oldSize = Object2IntLinkedOpenHashMap.this.size;
/* 1667 */       Object2IntLinkedOpenHashMap.this.removeInt(k);
/* 1668 */       return (Object2IntLinkedOpenHashMap.this.size != oldSize);
/*      */     }
/*      */ 
/*      */     
/*      */     public void clear() {
/* 1673 */       Object2IntLinkedOpenHashMap.this.clear();
/*      */     }
/*      */ 
/*      */     
/*      */     public K first() {
/* 1678 */       if (Object2IntLinkedOpenHashMap.this.size == 0) throw new NoSuchElementException(); 
/* 1679 */       return Object2IntLinkedOpenHashMap.this.key[Object2IntLinkedOpenHashMap.this.first];
/*      */     }
/*      */ 
/*      */     
/*      */     public K last() {
/* 1684 */       if (Object2IntLinkedOpenHashMap.this.size == 0) throw new NoSuchElementException(); 
/* 1685 */       return Object2IntLinkedOpenHashMap.this.key[Object2IntLinkedOpenHashMap.this.last];
/*      */     }
/*      */ 
/*      */     
/*      */     public Comparator<? super K> comparator() {
/* 1690 */       return null;
/*      */     }
/*      */ 
/*      */     
/*      */     public ObjectSortedSet<K> tailSet(K from) {
/* 1695 */       throw new UnsupportedOperationException();
/*      */     }
/*      */ 
/*      */     
/*      */     public ObjectSortedSet<K> headSet(K to) {
/* 1700 */       throw new UnsupportedOperationException();
/*      */     }
/*      */ 
/*      */     
/*      */     public ObjectSortedSet<K> subSet(K from, K to) {
/* 1705 */       throw new UnsupportedOperationException();
/*      */     }
/*      */   }
/*      */ 
/*      */   
/*      */   public ObjectSortedSet<K> keySet() {
/* 1711 */     if (this.keys == null) this.keys = new KeySet(); 
/* 1712 */     return this.keys;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private final class ValueIterator
/*      */     extends MapIterator<IntConsumer>
/*      */     implements IntListIterator
/*      */   {
/*      */     public int previousInt() {
/* 1726 */       return Object2IntLinkedOpenHashMap.this.value[previousEntry()];
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
/*      */     final void acceptOnIndex(IntConsumer action, int index) {
/* 1738 */       action.accept(Object2IntLinkedOpenHashMap.this.value[index]);
/*      */     }
/*      */ 
/*      */     
/*      */     public int nextInt() {
/* 1743 */       return Object2IntLinkedOpenHashMap.this.value[nextEntry()];
/*      */     }
/*      */   }
/*      */ 
/*      */   
/*      */   public IntCollection values() {
/* 1749 */     if (this.values == null) this.values = (IntCollection)new AbstractIntCollection()
/*      */         {
/*      */           private static final int SPLITERATOR_CHARACTERISTICS = 336;
/*      */           
/*      */           public IntIterator iterator() {
/* 1754 */             return (IntIterator)new Object2IntLinkedOpenHashMap.ValueIterator();
/*      */           }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */           
/*      */           public IntSpliterator spliterator() {
/* 1764 */             return IntSpliterators.asSpliterator(iterator(), Size64.sizeOf(Object2IntLinkedOpenHashMap.this), 336);
/*      */           }
/*      */ 
/*      */ 
/*      */           
/*      */           public void forEach(IntConsumer consumer) {
/* 1770 */             for (int i = Object2IntLinkedOpenHashMap.this.size, next = Object2IntLinkedOpenHashMap.this.first; i-- != 0; ) {
/* 1771 */               int curr = next;
/* 1772 */               next = (int)Object2IntLinkedOpenHashMap.this.link[curr];
/* 1773 */               consumer.accept(Object2IntLinkedOpenHashMap.this.value[curr]);
/*      */             } 
/*      */           }
/*      */ 
/*      */           
/*      */           public int size() {
/* 1779 */             return Object2IntLinkedOpenHashMap.this.size;
/*      */           }
/*      */ 
/*      */           
/*      */           public boolean contains(int v) {
/* 1784 */             return Object2IntLinkedOpenHashMap.this.containsValue(v);
/*      */           }
/*      */ 
/*      */           
/*      */           public void clear() {
/* 1789 */             Object2IntLinkedOpenHashMap.this.clear();
/*      */           }
/*      */         }; 
/* 1792 */     return this.values;
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
/* 1809 */     return trim(this.size);
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
/* 1831 */     int l = HashCommon.nextPowerOfTwo((int)Math.ceil((n / this.f)));
/* 1832 */     if (l >= this.n || this.size > HashCommon.maxFill(l, this.f)) return true; 
/*      */     try {
/* 1834 */       rehash(l);
/* 1835 */     } catch (OutOfMemoryError cantDoIt) {
/* 1836 */       return false;
/*      */     } 
/* 1838 */     return true;
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
/* 1853 */     K[] key = this.key;
/* 1854 */     int[] value = this.value;
/* 1855 */     int mask = newN - 1;
/* 1856 */     K[] newKey = (K[])new Object[newN + 1];
/* 1857 */     int[] newValue = new int[newN + 1];
/* 1858 */     int i = this.first, prev = -1, newPrev = -1;
/* 1859 */     long[] link = this.link;
/* 1860 */     long[] newLink = new long[newN + 1];
/* 1861 */     this.first = -1;
/* 1862 */     for (int j = this.size; j-- != 0; ) {
/* 1863 */       int pos; if (key[i] == null) { pos = newN; }
/*      */       else
/* 1865 */       { pos = HashCommon.mix(key[i].hashCode()) & mask;
/* 1866 */         for (; newKey[pos] != null; pos = pos + 1 & mask); }
/*      */       
/* 1868 */       newKey[pos] = key[i];
/* 1869 */       newValue[pos] = value[i];
/* 1870 */       if (prev != -1) {
/* 1871 */         newLink[newPrev] = newLink[newPrev] ^ (newLink[newPrev] ^ pos & 0xFFFFFFFFL) & 0xFFFFFFFFL;
/* 1872 */         newLink[pos] = newLink[pos] ^ (newLink[pos] ^ (newPrev & 0xFFFFFFFFL) << 32L) & 0xFFFFFFFF00000000L;
/* 1873 */         newPrev = pos;
/*      */       } else {
/* 1875 */         newPrev = this.first = pos;
/*      */         
/* 1877 */         newLink[pos] = -1L;
/*      */       } 
/* 1879 */       int t = i;
/* 1880 */       i = (int)link[i];
/* 1881 */       prev = t;
/*      */     } 
/* 1883 */     this.link = newLink;
/* 1884 */     this.last = newPrev;
/* 1885 */     if (newPrev != -1)
/*      */     {
/* 1887 */       newLink[newPrev] = newLink[newPrev] | 0xFFFFFFFFL; } 
/* 1888 */     this.n = newN;
/* 1889 */     this.mask = mask;
/* 1890 */     this.maxFill = HashCommon.maxFill(this.n, this.f);
/* 1891 */     this.key = newKey;
/* 1892 */     this.value = newValue;
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
/*      */   public Object2IntLinkedOpenHashMap<K> clone() {
/*      */     Object2IntLinkedOpenHashMap<K> c;
/*      */     try {
/* 1909 */       c = (Object2IntLinkedOpenHashMap<K>)super.clone();
/* 1910 */     } catch (CloneNotSupportedException cantHappen) {
/* 1911 */       throw new InternalError();
/*      */     } 
/* 1913 */     c.keys = null;
/* 1914 */     c.values = null;
/* 1915 */     c.entries = null;
/* 1916 */     c.containsNullKey = this.containsNullKey;
/* 1917 */     c.key = (K[])this.key.clone();
/* 1918 */     c.value = (int[])this.value.clone();
/* 1919 */     c.link = (long[])this.link.clone();
/* 1920 */     return c;
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
/* 1934 */     int h = 0;
/* 1935 */     for (int j = realSize(), i = 0, t = 0; j-- != 0; ) {
/* 1936 */       for (; this.key[i] == null; i++);
/* 1937 */       if (this != this.key[i]) t = this.key[i].hashCode(); 
/* 1938 */       t ^= this.value[i];
/* 1939 */       h += t;
/* 1940 */       i++;
/*      */     } 
/*      */     
/* 1943 */     if (this.containsNullKey) h += this.value[this.n]; 
/* 1944 */     return h;
/*      */   }
/*      */   
/*      */   private void writeObject(ObjectOutputStream s) throws IOException {
/* 1948 */     K[] key = this.key;
/* 1949 */     int[] value = this.value;
/* 1950 */     EntryIterator i = new EntryIterator();
/* 1951 */     s.defaultWriteObject();
/* 1952 */     for (int j = this.size; j-- != 0; ) {
/* 1953 */       int e = i.nextEntry();
/* 1954 */       s.writeObject(key[e]);
/* 1955 */       s.writeInt(value[e]);
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   private void readObject(ObjectInputStream s) throws IOException, ClassNotFoundException {
/* 1961 */     s.defaultReadObject();
/* 1962 */     this.n = HashCommon.arraySize(this.size, this.f);
/* 1963 */     this.maxFill = HashCommon.maxFill(this.n, this.f);
/* 1964 */     this.mask = this.n - 1;
/* 1965 */     K[] key = this.key = (K[])new Object[this.n + 1];
/* 1966 */     int[] value = this.value = new int[this.n + 1];
/* 1967 */     long[] link = this.link = new long[this.n + 1];
/* 1968 */     int prev = -1;
/* 1969 */     this.first = this.last = -1;
/*      */ 
/*      */     
/* 1972 */     for (int i = this.size; i-- != 0; ) {
/* 1973 */       int pos; K k = (K)s.readObject();
/* 1974 */       int v = s.readInt();
/* 1975 */       if (k == null) {
/* 1976 */         pos = this.n;
/* 1977 */         this.containsNullKey = true;
/*      */       } else {
/* 1979 */         pos = HashCommon.mix(k.hashCode()) & this.mask;
/* 1980 */         for (; key[pos] != null; pos = pos + 1 & this.mask);
/*      */       } 
/* 1982 */       key[pos] = k;
/* 1983 */       value[pos] = v;
/* 1984 */       if (this.first != -1) {
/* 1985 */         link[prev] = link[prev] ^ (link[prev] ^ pos & 0xFFFFFFFFL) & 0xFFFFFFFFL;
/* 1986 */         link[pos] = link[pos] ^ (link[pos] ^ (prev & 0xFFFFFFFFL) << 32L) & 0xFFFFFFFF00000000L;
/* 1987 */         prev = pos; continue;
/*      */       } 
/* 1989 */       prev = this.first = pos;
/*      */       
/* 1991 */       link[pos] = link[pos] | 0xFFFFFFFF00000000L;
/*      */     } 
/*      */     
/* 1994 */     this.last = prev;
/* 1995 */     if (prev != -1)
/*      */     {
/* 1997 */       link[prev] = link[prev] | 0xFFFFFFFFL;
/*      */     }
/*      */   }
/*      */   
/*      */   private void checkTable() {}
/*      */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\i\\unimi\dsi\fastutil\objects\Object2IntLinkedOpenHashMap.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */