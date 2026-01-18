/*      */ package it.unimi.dsi.fastutil.objects;
/*      */ 
/*      */ import it.unimi.dsi.fastutil.Hash;
/*      */ import it.unimi.dsi.fastutil.HashCommon;
/*      */ import it.unimi.dsi.fastutil.SafeMath;
/*      */ import it.unimi.dsi.fastutil.Size64;
/*      */ import it.unimi.dsi.fastutil.shorts.AbstractShortCollection;
/*      */ import it.unimi.dsi.fastutil.shorts.ShortCollection;
/*      */ import it.unimi.dsi.fastutil.shorts.ShortConsumer;
/*      */ import it.unimi.dsi.fastutil.shorts.ShortIterator;
/*      */ import it.unimi.dsi.fastutil.shorts.ShortListIterator;
/*      */ import it.unimi.dsi.fastutil.shorts.ShortSpliterator;
/*      */ import it.unimi.dsi.fastutil.shorts.ShortSpliterators;
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
/*      */ public class Object2ShortLinkedOpenHashMap<K>
/*      */   extends AbstractObject2ShortSortedMap<K>
/*      */   implements Serializable, Cloneable, Hash
/*      */ {
/*      */   private static final long serialVersionUID = 0L;
/*      */   private static final boolean ASSERTS = false;
/*      */   protected transient K[] key;
/*      */   protected transient short[] value;
/*      */   protected transient int mask;
/*      */   protected transient boolean containsNullKey;
/*  102 */   protected transient int first = -1;
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*  107 */   protected transient int last = -1;
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
/*      */   protected transient Object2ShortSortedMap.FastSortedEntrySet<K> entries;
/*      */ 
/*      */   
/*      */   protected transient ObjectSortedSet<K> keys;
/*      */ 
/*      */   
/*      */   protected transient ShortCollection values;
/*      */ 
/*      */ 
/*      */   
/*      */   public Object2ShortLinkedOpenHashMap(int expected, float f) {
/*  142 */     if (f <= 0.0F || f >= 1.0F) throw new IllegalArgumentException("Load factor must be greater than 0 and smaller than 1"); 
/*  143 */     if (expected < 0) throw new IllegalArgumentException("The expected number of elements must be nonnegative"); 
/*  144 */     this.f = f;
/*  145 */     this.minN = this.n = HashCommon.arraySize(expected, f);
/*  146 */     this.mask = this.n - 1;
/*  147 */     this.maxFill = HashCommon.maxFill(this.n, f);
/*  148 */     this.key = (K[])new Object[this.n + 1];
/*  149 */     this.value = new short[this.n + 1];
/*  150 */     this.link = new long[this.n + 1];
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Object2ShortLinkedOpenHashMap(int expected) {
/*  159 */     this(expected, 0.75F);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Object2ShortLinkedOpenHashMap() {
/*  167 */     this(16, 0.75F);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Object2ShortLinkedOpenHashMap(Map<? extends K, ? extends Short> m, float f) {
/*  177 */     this(m.size(), f);
/*  178 */     putAll(m);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Object2ShortLinkedOpenHashMap(Map<? extends K, ? extends Short> m) {
/*  187 */     this(m, 0.75F);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Object2ShortLinkedOpenHashMap(Object2ShortMap<K> m, float f) {
/*  197 */     this(m.size(), f);
/*  198 */     putAll(m);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Object2ShortLinkedOpenHashMap(Object2ShortMap<K> m) {
/*  208 */     this(m, 0.75F);
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
/*      */   public Object2ShortLinkedOpenHashMap(K[] k, short[] v, float f) {
/*  220 */     this(k.length, f);
/*  221 */     if (k.length != v.length) throw new IllegalArgumentException("The key array and the value array have different lengths (" + k.length + " and " + v.length + ")"); 
/*  222 */     for (int i = 0; i < k.length; ) { put(k[i], v[i]); i++; }
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
/*      */   public Object2ShortLinkedOpenHashMap(K[] k, short[] v) {
/*  234 */     this(k, v, 0.75F);
/*      */   }
/*      */   
/*      */   private int realSize() {
/*  238 */     return this.containsNullKey ? (this.size - 1) : this.size;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void ensureCapacity(int capacity) {
/*  248 */     int needed = HashCommon.arraySize(capacity, this.f);
/*  249 */     if (needed > this.n) rehash(needed); 
/*      */   }
/*      */   
/*      */   private void tryCapacity(long capacity) {
/*  253 */     int needed = (int)Math.min(1073741824L, Math.max(2L, HashCommon.nextPowerOfTwo((long)Math.ceil(((float)capacity / this.f)))));
/*  254 */     if (needed > this.n) rehash(needed); 
/*      */   }
/*      */   
/*      */   private short removeEntry(int pos) {
/*  258 */     short oldValue = this.value[pos];
/*  259 */     this.size--;
/*  260 */     fixPointers(pos);
/*  261 */     shiftKeys(pos);
/*  262 */     if (this.n > this.minN && this.size < this.maxFill / 4 && this.n > 16) rehash(this.n / 2); 
/*  263 */     return oldValue;
/*      */   }
/*      */   
/*      */   private short removeNullEntry() {
/*  267 */     this.containsNullKey = false;
/*  268 */     this.key[this.n] = null;
/*  269 */     short oldValue = this.value[this.n];
/*  270 */     this.size--;
/*  271 */     fixPointers(this.n);
/*  272 */     if (this.n > this.minN && this.size < this.maxFill / 4 && this.n > 16) rehash(this.n / 2); 
/*  273 */     return oldValue;
/*      */   }
/*      */ 
/*      */   
/*      */   public void putAll(Map<? extends K, ? extends Short> m) {
/*  278 */     if (this.f <= 0.5D) { ensureCapacity(m.size()); }
/*  279 */     else { tryCapacity((size() + m.size())); }
/*      */     
/*  281 */     super.putAll(m);
/*      */   }
/*      */ 
/*      */   
/*      */   private int find(K k) {
/*  286 */     if (k == null) return this.containsNullKey ? this.n : -(this.n + 1);
/*      */     
/*  288 */     K[] key = this.key;
/*      */     K curr;
/*      */     int pos;
/*  291 */     if ((curr = key[pos = HashCommon.mix(k.hashCode()) & this.mask]) == null) return -(pos + 1); 
/*  292 */     if (k.equals(curr)) return pos;
/*      */     
/*      */     while (true) {
/*  295 */       if ((curr = key[pos = pos + 1 & this.mask]) == null) return -(pos + 1); 
/*  296 */       if (k.equals(curr)) return pos; 
/*      */     } 
/*      */   }
/*      */   
/*      */   private void insert(int pos, K k, short v) {
/*  301 */     if (pos == this.n) this.containsNullKey = true; 
/*  302 */     this.key[pos] = k;
/*  303 */     this.value[pos] = v;
/*  304 */     if (this.size == 0) {
/*  305 */       this.first = this.last = pos;
/*      */       
/*  307 */       this.link[pos] = -1L;
/*      */     } else {
/*  309 */       this.link[this.last] = this.link[this.last] ^ (this.link[this.last] ^ pos & 0xFFFFFFFFL) & 0xFFFFFFFFL;
/*  310 */       this.link[pos] = (this.last & 0xFFFFFFFFL) << 32L | 0xFFFFFFFFL;
/*  311 */       this.last = pos;
/*      */     } 
/*  313 */     if (this.size++ >= this.maxFill) rehash(HashCommon.arraySize(this.size + 1, this.f));
/*      */   
/*      */   }
/*      */ 
/*      */   
/*      */   public short put(K k, short v) {
/*  319 */     int pos = find(k);
/*  320 */     if (pos < 0) {
/*  321 */       insert(-pos - 1, k, v);
/*  322 */       return this.defRetValue;
/*      */     } 
/*  324 */     short oldValue = this.value[pos];
/*  325 */     this.value[pos] = v;
/*  326 */     return oldValue;
/*      */   }
/*      */   
/*      */   private short addToValue(int pos, short incr) {
/*  330 */     short oldValue = this.value[pos];
/*  331 */     this.value[pos] = (short)(oldValue + incr);
/*  332 */     return oldValue;
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
/*      */   public short addTo(K k, short incr) {
/*      */     int pos;
/*  350 */     if (k == null) {
/*  351 */       if (this.containsNullKey) return addToValue(this.n, incr); 
/*  352 */       pos = this.n;
/*  353 */       this.containsNullKey = true;
/*      */     } else {
/*      */       
/*  356 */       K[] key = this.key;
/*      */       K curr;
/*  358 */       if ((curr = key[pos = HashCommon.mix(k.hashCode()) & this.mask]) != null) {
/*  359 */         if (curr.equals(k)) return addToValue(pos, incr); 
/*  360 */         while ((curr = key[pos = pos + 1 & this.mask]) != null) { if (curr.equals(k)) return addToValue(pos, incr);  }
/*      */       
/*      */       } 
/*  363 */     }  this.key[pos] = k;
/*  364 */     this.value[pos] = (short)(this.defRetValue + incr);
/*  365 */     if (this.size == 0) {
/*  366 */       this.first = this.last = pos;
/*      */       
/*  368 */       this.link[pos] = -1L;
/*      */     } else {
/*  370 */       this.link[this.last] = this.link[this.last] ^ (this.link[this.last] ^ pos & 0xFFFFFFFFL) & 0xFFFFFFFFL;
/*  371 */       this.link[pos] = (this.last & 0xFFFFFFFFL) << 32L | 0xFFFFFFFFL;
/*  372 */       this.last = pos;
/*      */     } 
/*  374 */     if (this.size++ >= this.maxFill) rehash(HashCommon.arraySize(this.size + 1, this.f));
/*      */     
/*  376 */     return this.defRetValue;
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
/*  389 */     K[] key = this.key; while (true) {
/*      */       K curr; int last;
/*  391 */       pos = (last = pos) + 1 & this.mask;
/*      */       while (true) {
/*  393 */         if ((curr = key[pos]) == null) {
/*  394 */           key[last] = null;
/*      */           return;
/*      */         } 
/*  397 */         int slot = HashCommon.mix(curr.hashCode()) & this.mask;
/*  398 */         if ((last <= pos) ? (last >= slot || slot > pos) : (last >= slot && slot > pos))
/*  399 */           break;  pos = pos + 1 & this.mask;
/*      */       } 
/*  401 */       key[last] = curr;
/*  402 */       this.value[last] = this.value[pos];
/*  403 */       fixPointers(pos, last);
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public short removeShort(Object k) {
/*  410 */     if (k == null) {
/*  411 */       if (this.containsNullKey) return removeNullEntry(); 
/*  412 */       return this.defRetValue;
/*      */     } 
/*      */     
/*  415 */     K[] key = this.key;
/*      */     K curr;
/*      */     int pos;
/*  418 */     if ((curr = key[pos = HashCommon.mix(k.hashCode()) & this.mask]) == null) return this.defRetValue; 
/*  419 */     if (k.equals(curr)) return removeEntry(pos); 
/*      */     while (true) {
/*  421 */       if ((curr = key[pos = pos + 1 & this.mask]) == null) return this.defRetValue; 
/*  422 */       if (k.equals(curr)) return removeEntry(pos); 
/*      */     } 
/*      */   }
/*      */   
/*      */   private short setValue(int pos, short v) {
/*  427 */     short oldValue = this.value[pos];
/*  428 */     this.value[pos] = v;
/*  429 */     return oldValue;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public short removeFirstShort() {
/*  439 */     if (this.size == 0) throw new NoSuchElementException(); 
/*  440 */     int pos = this.first;
/*      */     
/*  442 */     if (this.size == 1) { this.first = this.last = -1; }
/*      */     else
/*  444 */     { this.first = (int)this.link[pos];
/*  445 */       if (0 <= this.first)
/*      */       {
/*  447 */         this.link[this.first] = this.link[this.first] | 0xFFFFFFFF00000000L;
/*      */       } }
/*      */     
/*  450 */     this.size--;
/*  451 */     short v = this.value[pos];
/*  452 */     if (pos == this.n)
/*  453 */     { this.containsNullKey = false;
/*  454 */       this.key[this.n] = null; }
/*  455 */     else { shiftKeys(pos); }
/*  456 */      if (this.n > this.minN && this.size < this.maxFill / 4 && this.n > 16) rehash(this.n / 2); 
/*  457 */     return v;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public short removeLastShort() {
/*  467 */     if (this.size == 0) throw new NoSuchElementException(); 
/*  468 */     int pos = this.last;
/*      */     
/*  470 */     if (this.size == 1) { this.first = this.last = -1; }
/*      */     else
/*  472 */     { this.last = (int)(this.link[pos] >>> 32L);
/*  473 */       if (0 <= this.last)
/*      */       {
/*  475 */         this.link[this.last] = this.link[this.last] | 0xFFFFFFFFL;
/*      */       } }
/*      */     
/*  478 */     this.size--;
/*  479 */     short v = this.value[pos];
/*  480 */     if (pos == this.n)
/*  481 */     { this.containsNullKey = false;
/*  482 */       this.key[this.n] = null; }
/*  483 */     else { shiftKeys(pos); }
/*  484 */      if (this.n > this.minN && this.size < this.maxFill / 4 && this.n > 16) rehash(this.n / 2); 
/*  485 */     return v;
/*      */   }
/*      */   
/*      */   private void moveIndexToFirst(int i) {
/*  489 */     if (this.size == 1 || this.first == i)
/*  490 */       return;  if (this.last == i) {
/*  491 */       this.last = (int)(this.link[i] >>> 32L);
/*      */       
/*  493 */       this.link[this.last] = this.link[this.last] | 0xFFFFFFFFL;
/*      */     } else {
/*  495 */       long linki = this.link[i];
/*  496 */       int prev = (int)(linki >>> 32L);
/*  497 */       int next = (int)linki;
/*  498 */       this.link[prev] = this.link[prev] ^ (this.link[prev] ^ linki & 0xFFFFFFFFL) & 0xFFFFFFFFL;
/*  499 */       this.link[next] = this.link[next] ^ (this.link[next] ^ linki & 0xFFFFFFFF00000000L) & 0xFFFFFFFF00000000L;
/*      */     } 
/*  501 */     this.link[this.first] = this.link[this.first] ^ (this.link[this.first] ^ (i & 0xFFFFFFFFL) << 32L) & 0xFFFFFFFF00000000L;
/*  502 */     this.link[i] = 0xFFFFFFFF00000000L | this.first & 0xFFFFFFFFL;
/*  503 */     this.first = i;
/*      */   }
/*      */   
/*      */   private void moveIndexToLast(int i) {
/*  507 */     if (this.size == 1 || this.last == i)
/*  508 */       return;  if (this.first == i) {
/*  509 */       this.first = (int)this.link[i];
/*      */       
/*  511 */       this.link[this.first] = this.link[this.first] | 0xFFFFFFFF00000000L;
/*      */     } else {
/*  513 */       long linki = this.link[i];
/*  514 */       int prev = (int)(linki >>> 32L);
/*  515 */       int next = (int)linki;
/*  516 */       this.link[prev] = this.link[prev] ^ (this.link[prev] ^ linki & 0xFFFFFFFFL) & 0xFFFFFFFFL;
/*  517 */       this.link[next] = this.link[next] ^ (this.link[next] ^ linki & 0xFFFFFFFF00000000L) & 0xFFFFFFFF00000000L;
/*      */     } 
/*  519 */     this.link[this.last] = this.link[this.last] ^ (this.link[this.last] ^ i & 0xFFFFFFFFL) & 0xFFFFFFFFL;
/*  520 */     this.link[i] = (this.last & 0xFFFFFFFFL) << 32L | 0xFFFFFFFFL;
/*  521 */     this.last = i;
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
/*      */   public short getAndMoveToFirst(K k) {
/*  533 */     if (k == null) {
/*  534 */       if (this.containsNullKey) {
/*  535 */         moveIndexToFirst(this.n);
/*  536 */         return this.value[this.n];
/*      */       } 
/*  538 */       return this.defRetValue;
/*      */     } 
/*      */     
/*  541 */     K[] key = this.key;
/*      */     K curr;
/*      */     int pos;
/*  544 */     if ((curr = key[pos = HashCommon.mix(k.hashCode()) & this.mask]) == null) return this.defRetValue; 
/*  545 */     if (k.equals(curr)) {
/*  546 */       moveIndexToFirst(pos);
/*  547 */       return this.value[pos];
/*      */     } 
/*      */     
/*      */     while (true) {
/*  551 */       if ((curr = key[pos = pos + 1 & this.mask]) == null) return this.defRetValue; 
/*  552 */       if (k.equals(curr)) {
/*  553 */         moveIndexToFirst(pos);
/*  554 */         return this.value[pos];
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
/*      */   public short getAndMoveToLast(K k) {
/*  568 */     if (k == null) {
/*  569 */       if (this.containsNullKey) {
/*  570 */         moveIndexToLast(this.n);
/*  571 */         return this.value[this.n];
/*      */       } 
/*  573 */       return this.defRetValue;
/*      */     } 
/*      */     
/*  576 */     K[] key = this.key;
/*      */     K curr;
/*      */     int pos;
/*  579 */     if ((curr = key[pos = HashCommon.mix(k.hashCode()) & this.mask]) == null) return this.defRetValue; 
/*  580 */     if (k.equals(curr)) {
/*  581 */       moveIndexToLast(pos);
/*  582 */       return this.value[pos];
/*      */     } 
/*      */     
/*      */     while (true) {
/*  586 */       if ((curr = key[pos = pos + 1 & this.mask]) == null) return this.defRetValue; 
/*  587 */       if (k.equals(curr)) {
/*  588 */         moveIndexToLast(pos);
/*  589 */         return this.value[pos];
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
/*      */   public short putAndMoveToFirst(K k, short v) {
/*      */     int pos;
/*  605 */     if (k == null) {
/*  606 */       if (this.containsNullKey) {
/*  607 */         moveIndexToFirst(this.n);
/*  608 */         return setValue(this.n, v);
/*      */       } 
/*  610 */       this.containsNullKey = true;
/*  611 */       pos = this.n;
/*      */     } else {
/*      */       
/*  614 */       K[] key = this.key;
/*      */       K curr;
/*  616 */       if ((curr = key[pos = HashCommon.mix(k.hashCode()) & this.mask]) != null) {
/*  617 */         if (curr.equals(k)) {
/*  618 */           moveIndexToFirst(pos);
/*  619 */           return setValue(pos, v);
/*      */         } 
/*  621 */         while ((curr = key[pos = pos + 1 & this.mask]) != null) { if (curr.equals(k)) {
/*  622 */             moveIndexToFirst(pos);
/*  623 */             return setValue(pos, v);
/*      */           }  }
/*      */       
/*      */       } 
/*  627 */     }  this.key[pos] = k;
/*  628 */     this.value[pos] = v;
/*  629 */     if (this.size == 0) {
/*  630 */       this.first = this.last = pos;
/*      */       
/*  632 */       this.link[pos] = -1L;
/*      */     } else {
/*  634 */       this.link[this.first] = this.link[this.first] ^ (this.link[this.first] ^ (pos & 0xFFFFFFFFL) << 32L) & 0xFFFFFFFF00000000L;
/*  635 */       this.link[pos] = 0xFFFFFFFF00000000L | this.first & 0xFFFFFFFFL;
/*  636 */       this.first = pos;
/*      */     } 
/*  638 */     if (this.size++ >= this.maxFill) rehash(HashCommon.arraySize(this.size, this.f));
/*      */     
/*  640 */     return this.defRetValue;
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
/*      */   public short putAndMoveToLast(K k, short v) {
/*      */     int pos;
/*  654 */     if (k == null) {
/*  655 */       if (this.containsNullKey) {
/*  656 */         moveIndexToLast(this.n);
/*  657 */         return setValue(this.n, v);
/*      */       } 
/*  659 */       this.containsNullKey = true;
/*  660 */       pos = this.n;
/*      */     } else {
/*      */       
/*  663 */       K[] key = this.key;
/*      */       K curr;
/*  665 */       if ((curr = key[pos = HashCommon.mix(k.hashCode()) & this.mask]) != null) {
/*  666 */         if (curr.equals(k)) {
/*  667 */           moveIndexToLast(pos);
/*  668 */           return setValue(pos, v);
/*      */         } 
/*  670 */         while ((curr = key[pos = pos + 1 & this.mask]) != null) { if (curr.equals(k)) {
/*  671 */             moveIndexToLast(pos);
/*  672 */             return setValue(pos, v);
/*      */           }  }
/*      */       
/*      */       } 
/*  676 */     }  this.key[pos] = k;
/*  677 */     this.value[pos] = v;
/*  678 */     if (this.size == 0) {
/*  679 */       this.first = this.last = pos;
/*      */       
/*  681 */       this.link[pos] = -1L;
/*      */     } else {
/*  683 */       this.link[this.last] = this.link[this.last] ^ (this.link[this.last] ^ pos & 0xFFFFFFFFL) & 0xFFFFFFFFL;
/*  684 */       this.link[pos] = (this.last & 0xFFFFFFFFL) << 32L | 0xFFFFFFFFL;
/*  685 */       this.last = pos;
/*      */     } 
/*  687 */     if (this.size++ >= this.maxFill) rehash(HashCommon.arraySize(this.size, this.f));
/*      */     
/*  689 */     return this.defRetValue;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public short getShort(Object k) {
/*  695 */     if (k == null) return this.containsNullKey ? this.value[this.n] : this.defRetValue;
/*      */     
/*  697 */     K[] key = this.key;
/*      */     K curr;
/*      */     int pos;
/*  700 */     if ((curr = key[pos = HashCommon.mix(k.hashCode()) & this.mask]) == null) return this.defRetValue; 
/*  701 */     if (k.equals(curr)) return this.value[pos];
/*      */     
/*      */     while (true) {
/*  704 */       if ((curr = key[pos = pos + 1 & this.mask]) == null) return this.defRetValue; 
/*  705 */       if (k.equals(curr)) return this.value[pos];
/*      */     
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   public boolean containsKey(Object k) {
/*  712 */     if (k == null) return this.containsNullKey;
/*      */     
/*  714 */     K[] key = this.key;
/*      */     K curr;
/*      */     int pos;
/*  717 */     if ((curr = key[pos = HashCommon.mix(k.hashCode()) & this.mask]) == null) return false; 
/*  718 */     if (k.equals(curr)) return true;
/*      */     
/*      */     while (true) {
/*  721 */       if ((curr = key[pos = pos + 1 & this.mask]) == null) return false; 
/*  722 */       if (k.equals(curr)) return true;
/*      */     
/*      */     } 
/*      */   }
/*      */   
/*      */   public boolean containsValue(short v) {
/*  728 */     short[] value = this.value;
/*  729 */     K[] key = this.key;
/*  730 */     if (this.containsNullKey && value[this.n] == v) return true; 
/*  731 */     for (int i = this.n; i-- != 0;) { if (key[i] != null && value[i] == v) return true;  }
/*  732 */      return false;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public short getOrDefault(Object k, short defaultValue) {
/*  739 */     if (k == null) return this.containsNullKey ? this.value[this.n] : defaultValue;
/*      */     
/*  741 */     K[] key = this.key;
/*      */     K curr;
/*      */     int pos;
/*  744 */     if ((curr = key[pos = HashCommon.mix(k.hashCode()) & this.mask]) == null) return defaultValue; 
/*  745 */     if (k.equals(curr)) return this.value[pos];
/*      */     
/*      */     while (true) {
/*  748 */       if ((curr = key[pos = pos + 1 & this.mask]) == null) return defaultValue; 
/*  749 */       if (k.equals(curr)) return this.value[pos];
/*      */     
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   public short putIfAbsent(K k, short v) {
/*  756 */     int pos = find(k);
/*  757 */     if (pos >= 0) return this.value[pos]; 
/*  758 */     insert(-pos - 1, k, v);
/*  759 */     return this.defRetValue;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean remove(Object k, short v) {
/*  766 */     if (k == null) {
/*  767 */       if (this.containsNullKey && v == this.value[this.n]) {
/*  768 */         removeNullEntry();
/*  769 */         return true;
/*      */       } 
/*  771 */       return false;
/*      */     } 
/*      */     
/*  774 */     K[] key = this.key;
/*      */     K curr;
/*      */     int pos;
/*  777 */     if ((curr = key[pos = HashCommon.mix(k.hashCode()) & this.mask]) == null) return false; 
/*  778 */     if (k.equals(curr) && v == this.value[pos]) {
/*  779 */       removeEntry(pos);
/*  780 */       return true;
/*      */     } 
/*      */     while (true) {
/*  783 */       if ((curr = key[pos = pos + 1 & this.mask]) == null) return false; 
/*  784 */       if (k.equals(curr) && v == this.value[pos]) {
/*  785 */         removeEntry(pos);
/*  786 */         return true;
/*      */       } 
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean replace(K k, short oldValue, short v) {
/*  794 */     int pos = find(k);
/*  795 */     if (pos < 0 || oldValue != this.value[pos]) return false; 
/*  796 */     this.value[pos] = v;
/*  797 */     return true;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public short replace(K k, short v) {
/*  803 */     int pos = find(k);
/*  804 */     if (pos < 0) return this.defRetValue; 
/*  805 */     short oldValue = this.value[pos];
/*  806 */     this.value[pos] = v;
/*  807 */     return oldValue;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public short computeIfAbsent(K k, ToIntFunction<? super K> mappingFunction) {
/*  813 */     Objects.requireNonNull(mappingFunction);
/*  814 */     int pos = find(k);
/*  815 */     if (pos >= 0) return this.value[pos]; 
/*  816 */     short newValue = SafeMath.safeIntToShort(mappingFunction.applyAsInt(k));
/*  817 */     insert(-pos - 1, k, newValue);
/*  818 */     return newValue;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public short computeIfAbsent(K key, Object2ShortFunction<? super K> mappingFunction) {
/*  824 */     Objects.requireNonNull(mappingFunction);
/*  825 */     int pos = find(key);
/*  826 */     if (pos >= 0) return this.value[pos]; 
/*  827 */     if (!mappingFunction.containsKey(key)) return this.defRetValue; 
/*  828 */     short newValue = mappingFunction.getShort(key);
/*  829 */     insert(-pos - 1, key, newValue);
/*  830 */     return newValue;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public short computeShortIfPresent(K k, BiFunction<? super K, ? super Short, ? extends Short> remappingFunction) {
/*  836 */     Objects.requireNonNull(remappingFunction);
/*  837 */     int pos = find(k);
/*  838 */     if (pos < 0) return this.defRetValue; 
/*  839 */     Short newValue = remappingFunction.apply(k, Short.valueOf(this.value[pos]));
/*  840 */     if (newValue == null) {
/*  841 */       if (k == null) { removeNullEntry(); }
/*  842 */       else { removeEntry(pos); }
/*  843 */        return this.defRetValue;
/*      */     } 
/*  845 */     this.value[pos] = newValue.shortValue(); return newValue.shortValue();
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public short computeShort(K k, BiFunction<? super K, ? super Short, ? extends Short> remappingFunction) {
/*  851 */     Objects.requireNonNull(remappingFunction);
/*  852 */     int pos = find(k);
/*  853 */     Short newValue = remappingFunction.apply(k, (pos >= 0) ? Short.valueOf(this.value[pos]) : null);
/*  854 */     if (newValue == null) {
/*  855 */       if (pos >= 0)
/*  856 */         if (k == null) { removeNullEntry(); }
/*  857 */         else { removeEntry(pos); }
/*      */          
/*  859 */       return this.defRetValue;
/*      */     } 
/*  861 */     short newVal = newValue.shortValue();
/*  862 */     if (pos < 0) {
/*  863 */       insert(-pos - 1, k, newVal);
/*  864 */       return newVal;
/*      */     } 
/*  866 */     this.value[pos] = newVal; return newVal;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public short merge(K k, short v, BiFunction<? super Short, ? super Short, ? extends Short> remappingFunction) {
/*  872 */     Objects.requireNonNull(remappingFunction);
/*      */     
/*  874 */     int pos = find(k);
/*  875 */     if (pos < 0) {
/*  876 */       if (pos < 0) { insert(-pos - 1, k, v); }
/*  877 */       else { this.value[pos] = v; }
/*  878 */        return v;
/*      */     } 
/*  880 */     Short newValue = remappingFunction.apply(Short.valueOf(this.value[pos]), Short.valueOf(v));
/*  881 */     if (newValue == null) {
/*  882 */       if (k == null) { removeNullEntry(); }
/*  883 */       else { removeEntry(pos); }
/*  884 */        return this.defRetValue;
/*      */     } 
/*  886 */     this.value[pos] = newValue.shortValue(); return newValue.shortValue();
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
/*  897 */     if (this.size == 0)
/*  898 */       return;  this.size = 0;
/*  899 */     this.containsNullKey = false;
/*  900 */     Arrays.fill((Object[])this.key, (Object)null);
/*  901 */     this.first = this.last = -1;
/*      */   }
/*      */ 
/*      */   
/*      */   public int size() {
/*  906 */     return this.size;
/*      */   }
/*      */ 
/*      */   
/*      */   public boolean isEmpty() {
/*  911 */     return (this.size == 0);
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   final class MapEntry
/*      */     implements Object2ShortMap.Entry<K>, Map.Entry<K, Short>, ObjectShortPair<K>
/*      */   {
/*      */     int index;
/*      */ 
/*      */ 
/*      */     
/*      */     MapEntry(int index) {
/*  924 */       this.index = index;
/*      */     }
/*      */ 
/*      */     
/*      */     MapEntry() {}
/*      */ 
/*      */     
/*      */     public K getKey() {
/*  932 */       return Object2ShortLinkedOpenHashMap.this.key[this.index];
/*      */     }
/*      */ 
/*      */     
/*      */     public K left() {
/*  937 */       return Object2ShortLinkedOpenHashMap.this.key[this.index];
/*      */     }
/*      */ 
/*      */     
/*      */     public short getShortValue() {
/*  942 */       return Object2ShortLinkedOpenHashMap.this.value[this.index];
/*      */     }
/*      */ 
/*      */     
/*      */     public short rightShort() {
/*  947 */       return Object2ShortLinkedOpenHashMap.this.value[this.index];
/*      */     }
/*      */ 
/*      */     
/*      */     public short setValue(short v) {
/*  952 */       short oldValue = Object2ShortLinkedOpenHashMap.this.value[this.index];
/*  953 */       Object2ShortLinkedOpenHashMap.this.value[this.index] = v;
/*  954 */       return oldValue;
/*      */     }
/*      */ 
/*      */     
/*      */     public ObjectShortPair<K> right(short v) {
/*  959 */       Object2ShortLinkedOpenHashMap.this.value[this.index] = v;
/*  960 */       return this;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     @Deprecated
/*      */     public Short getValue() {
/*  971 */       return Short.valueOf(Object2ShortLinkedOpenHashMap.this.value[this.index]);
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     @Deprecated
/*      */     public Short setValue(Short v) {
/*  982 */       return Short.valueOf(setValue(v.shortValue()));
/*      */     }
/*      */ 
/*      */ 
/*      */     
/*      */     public boolean equals(Object o) {
/*  988 */       if (!(o instanceof Map.Entry)) return false; 
/*  989 */       Map.Entry<K, Short> e = (Map.Entry<K, Short>)o;
/*  990 */       return (Objects.equals(Object2ShortLinkedOpenHashMap.this.key[this.index], e.getKey()) && Object2ShortLinkedOpenHashMap.this.value[this.index] == ((Short)e.getValue()).shortValue());
/*      */     }
/*      */ 
/*      */     
/*      */     public int hashCode() {
/*  995 */       return ((Object2ShortLinkedOpenHashMap.this.key[this.index] == null) ? 0 : Object2ShortLinkedOpenHashMap.this.key[this.index].hashCode()) ^ Object2ShortLinkedOpenHashMap.this.value[this.index];
/*      */     }
/*      */ 
/*      */     
/*      */     public String toString() {
/* 1000 */       return (new StringBuilder()).append(Object2ShortLinkedOpenHashMap.this.key[this.index]).append("=>").append(Object2ShortLinkedOpenHashMap.this.value[this.index]).toString();
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
/* 1011 */     if (this.size == 0) {
/* 1012 */       this.first = this.last = -1;
/*      */       return;
/*      */     } 
/* 1015 */     if (this.first == i) {
/* 1016 */       this.first = (int)this.link[i];
/* 1017 */       if (0 <= this.first)
/*      */       {
/* 1019 */         this.link[this.first] = this.link[this.first] | 0xFFFFFFFF00000000L;
/*      */       }
/*      */       return;
/*      */     } 
/* 1023 */     if (this.last == i) {
/* 1024 */       this.last = (int)(this.link[i] >>> 32L);
/* 1025 */       if (0 <= this.last)
/*      */       {
/* 1027 */         this.link[this.last] = this.link[this.last] | 0xFFFFFFFFL;
/*      */       }
/*      */       return;
/*      */     } 
/* 1031 */     long linki = this.link[i];
/* 1032 */     int prev = (int)(linki >>> 32L);
/* 1033 */     int next = (int)linki;
/* 1034 */     this.link[prev] = this.link[prev] ^ (this.link[prev] ^ linki & 0xFFFFFFFFL) & 0xFFFFFFFFL;
/* 1035 */     this.link[next] = this.link[next] ^ (this.link[next] ^ linki & 0xFFFFFFFF00000000L) & 0xFFFFFFFF00000000L;
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
/* 1047 */     if (this.size == 1) {
/* 1048 */       this.first = this.last = d;
/*      */       
/* 1050 */       this.link[d] = -1L;
/*      */       return;
/*      */     } 
/* 1053 */     if (this.first == s) {
/* 1054 */       this.first = d;
/* 1055 */       this.link[(int)this.link[s]] = this.link[(int)this.link[s]] ^ (this.link[(int)this.link[s]] ^ (d & 0xFFFFFFFFL) << 32L) & 0xFFFFFFFF00000000L;
/* 1056 */       this.link[d] = this.link[s];
/*      */       return;
/*      */     } 
/* 1059 */     if (this.last == s) {
/* 1060 */       this.last = d;
/* 1061 */       this.link[(int)(this.link[s] >>> 32L)] = this.link[(int)(this.link[s] >>> 32L)] ^ (this.link[(int)(this.link[s] >>> 32L)] ^ d & 0xFFFFFFFFL) & 0xFFFFFFFFL;
/* 1062 */       this.link[d] = this.link[s];
/*      */       return;
/*      */     } 
/* 1065 */     long links = this.link[s];
/* 1066 */     int prev = (int)(links >>> 32L);
/* 1067 */     int next = (int)links;
/* 1068 */     this.link[prev] = this.link[prev] ^ (this.link[prev] ^ d & 0xFFFFFFFFL) & 0xFFFFFFFFL;
/* 1069 */     this.link[next] = this.link[next] ^ (this.link[next] ^ (d & 0xFFFFFFFFL) << 32L) & 0xFFFFFFFF00000000L;
/* 1070 */     this.link[d] = links;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public K firstKey() {
/* 1080 */     if (this.size == 0) throw new NoSuchElementException(); 
/* 1081 */     return this.key[this.first];
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public K lastKey() {
/* 1091 */     if (this.size == 0) throw new NoSuchElementException(); 
/* 1092 */     return this.key[this.last];
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Object2ShortSortedMap<K> tailMap(K from) {
/* 1102 */     throw new UnsupportedOperationException();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Object2ShortSortedMap<K> headMap(K to) {
/* 1112 */     throw new UnsupportedOperationException();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Object2ShortSortedMap<K> subMap(K from, K to) {
/* 1122 */     throw new UnsupportedOperationException();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Comparator<? super K> comparator() {
/* 1132 */     return null;
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
/* 1147 */     int prev = -1;
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 1152 */     int next = -1;
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 1157 */     int curr = -1;
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 1162 */     int index = -1;
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     protected MapIterator() {
/* 1168 */       this.next = Object2ShortLinkedOpenHashMap.this.first;
/* 1169 */       this.index = 0;
/*      */     }
/*      */     
/*      */     private MapIterator(K from) {
/* 1173 */       if (from == null) {
/* 1174 */         if (Object2ShortLinkedOpenHashMap.this.containsNullKey) {
/* 1175 */           this.next = (int)Object2ShortLinkedOpenHashMap.this.link[Object2ShortLinkedOpenHashMap.this.n];
/* 1176 */           this.prev = Object2ShortLinkedOpenHashMap.this.n; return;
/*      */         } 
/* 1178 */         throw new NoSuchElementException("The key " + from + " does not belong to this map.");
/*      */       } 
/* 1180 */       if (Objects.equals(Object2ShortLinkedOpenHashMap.this.key[Object2ShortLinkedOpenHashMap.this.last], from)) {
/* 1181 */         this.prev = Object2ShortLinkedOpenHashMap.this.last;
/* 1182 */         this.index = Object2ShortLinkedOpenHashMap.this.size;
/*      */         
/*      */         return;
/*      */       } 
/* 1186 */       int pos = HashCommon.mix(from.hashCode()) & Object2ShortLinkedOpenHashMap.this.mask;
/*      */       
/* 1188 */       while (Object2ShortLinkedOpenHashMap.this.key[pos] != null) {
/* 1189 */         if (Object2ShortLinkedOpenHashMap.this.key[pos].equals(from)) {
/*      */           
/* 1191 */           this.next = (int)Object2ShortLinkedOpenHashMap.this.link[pos];
/* 1192 */           this.prev = pos;
/*      */           return;
/*      */         } 
/* 1195 */         pos = pos + 1 & Object2ShortLinkedOpenHashMap.this.mask;
/*      */       } 
/* 1197 */       throw new NoSuchElementException("The key " + from + " does not belong to this map.");
/*      */     }
/*      */     
/*      */     public boolean hasNext() {
/* 1201 */       return (this.next != -1);
/*      */     }
/*      */     
/*      */     public boolean hasPrevious() {
/* 1205 */       return (this.prev != -1);
/*      */     }
/*      */     
/*      */     private final void ensureIndexKnown() {
/* 1209 */       if (this.index >= 0)
/* 1210 */         return;  if (this.prev == -1) {
/* 1211 */         this.index = 0;
/*      */         return;
/*      */       } 
/* 1214 */       if (this.next == -1) {
/* 1215 */         this.index = Object2ShortLinkedOpenHashMap.this.size;
/*      */         return;
/*      */       } 
/* 1218 */       int pos = Object2ShortLinkedOpenHashMap.this.first;
/* 1219 */       this.index = 1;
/* 1220 */       while (pos != this.prev) {
/* 1221 */         pos = (int)Object2ShortLinkedOpenHashMap.this.link[pos];
/* 1222 */         this.index++;
/*      */       } 
/*      */     }
/*      */     
/*      */     public int nextIndex() {
/* 1227 */       ensureIndexKnown();
/* 1228 */       return this.index;
/*      */     }
/*      */     
/*      */     public int previousIndex() {
/* 1232 */       ensureIndexKnown();
/* 1233 */       return this.index - 1;
/*      */     }
/*      */     
/*      */     public int nextEntry() {
/* 1237 */       if (!hasNext()) throw new NoSuchElementException(); 
/* 1238 */       this.curr = this.next;
/* 1239 */       this.next = (int)Object2ShortLinkedOpenHashMap.this.link[this.curr];
/* 1240 */       this.prev = this.curr;
/* 1241 */       if (this.index >= 0) this.index++; 
/* 1242 */       return this.curr;
/*      */     }
/*      */     
/*      */     public int previousEntry() {
/* 1246 */       if (!hasPrevious()) throw new NoSuchElementException(); 
/* 1247 */       this.curr = this.prev;
/* 1248 */       this.prev = (int)(Object2ShortLinkedOpenHashMap.this.link[this.curr] >>> 32L);
/* 1249 */       this.next = this.curr;
/* 1250 */       if (this.index >= 0) this.index--; 
/* 1251 */       return this.curr;
/*      */     }
/*      */     
/*      */     public void forEachRemaining(ConsumerType action) {
/* 1255 */       while (hasNext()) {
/* 1256 */         this.curr = this.next;
/* 1257 */         this.next = (int)Object2ShortLinkedOpenHashMap.this.link[this.curr];
/* 1258 */         this.prev = this.curr;
/* 1259 */         if (this.index >= 0) this.index++; 
/* 1260 */         acceptOnIndex(action, this.curr);
/*      */       } 
/*      */     }
/*      */     
/*      */     public void remove() {
/* 1265 */       ensureIndexKnown();
/* 1266 */       if (this.curr == -1) throw new IllegalStateException(); 
/* 1267 */       if (this.curr == this.prev)
/*      */       
/*      */       { 
/* 1270 */         this.index--;
/* 1271 */         this.prev = (int)(Object2ShortLinkedOpenHashMap.this.link[this.curr] >>> 32L); }
/* 1272 */       else { this.next = (int)Object2ShortLinkedOpenHashMap.this.link[this.curr]; }
/* 1273 */        Object2ShortLinkedOpenHashMap.this.size--;
/*      */ 
/*      */       
/* 1276 */       if (this.prev == -1) { Object2ShortLinkedOpenHashMap.this.first = this.next; }
/* 1277 */       else { Object2ShortLinkedOpenHashMap.this.link[this.prev] = Object2ShortLinkedOpenHashMap.this.link[this.prev] ^ (Object2ShortLinkedOpenHashMap.this.link[this.prev] ^ this.next & 0xFFFFFFFFL) & 0xFFFFFFFFL; }
/* 1278 */        if (this.next == -1) { Object2ShortLinkedOpenHashMap.this.last = this.prev; }
/* 1279 */       else { Object2ShortLinkedOpenHashMap.this.link[this.next] = Object2ShortLinkedOpenHashMap.this.link[this.next] ^ (Object2ShortLinkedOpenHashMap.this.link[this.next] ^ (this.prev & 0xFFFFFFFFL) << 32L) & 0xFFFFFFFF00000000L; }
/* 1280 */        int pos = this.curr;
/* 1281 */       this.curr = -1;
/* 1282 */       if (pos == Object2ShortLinkedOpenHashMap.this.n) {
/* 1283 */         Object2ShortLinkedOpenHashMap.this.containsNullKey = false;
/* 1284 */         Object2ShortLinkedOpenHashMap.this.key[Object2ShortLinkedOpenHashMap.this.n] = null;
/*      */       } else {
/*      */         
/* 1287 */         K[] key = Object2ShortLinkedOpenHashMap.this.key; while (true) {
/*      */           K curr;
/*      */           int last;
/* 1290 */           pos = (last = pos) + 1 & Object2ShortLinkedOpenHashMap.this.mask;
/*      */           while (true) {
/* 1292 */             if ((curr = key[pos]) == null) {
/* 1293 */               key[last] = null;
/*      */               return;
/*      */             } 
/* 1296 */             int slot = HashCommon.mix(curr.hashCode()) & Object2ShortLinkedOpenHashMap.this.mask;
/* 1297 */             if ((last <= pos) ? (last >= slot || slot > pos) : (last >= slot && slot > pos))
/* 1298 */               break;  pos = pos + 1 & Object2ShortLinkedOpenHashMap.this.mask;
/*      */           } 
/* 1300 */           key[last] = curr;
/* 1301 */           Object2ShortLinkedOpenHashMap.this.value[last] = Object2ShortLinkedOpenHashMap.this.value[pos];
/* 1302 */           if (this.next == pos) this.next = last; 
/* 1303 */           if (this.prev == pos) this.prev = last; 
/* 1304 */           Object2ShortLinkedOpenHashMap.this.fixPointers(pos, last);
/*      */         } 
/*      */       } 
/*      */     }
/*      */     
/*      */     public int skip(int n) {
/* 1310 */       int i = n;
/* 1311 */       for (; i-- != 0 && hasNext(); nextEntry());
/* 1312 */       return n - i - 1;
/*      */     }
/*      */     
/*      */     public int back(int n) {
/* 1316 */       int i = n;
/* 1317 */       for (; i-- != 0 && hasPrevious(); previousEntry());
/* 1318 */       return n - i - 1;
/*      */     }
/*      */     
/*      */     public void set(Object2ShortMap.Entry<K> ok) {
/* 1322 */       throw new UnsupportedOperationException();
/*      */     }
/*      */     
/*      */     public void add(Object2ShortMap.Entry<K> ok) {
/* 1326 */       throw new UnsupportedOperationException();
/*      */     }
/*      */     
/*      */     abstract void acceptOnIndex(ConsumerType param1ConsumerType, int param1Int); }
/*      */   
/*      */   private final class EntryIterator extends MapIterator<Consumer<? super Object2ShortMap.Entry<K>>> implements ObjectListIterator<Object2ShortMap.Entry<K>> {
/*      */     private Object2ShortLinkedOpenHashMap<K>.MapEntry entry;
/*      */     
/*      */     public EntryIterator() {}
/*      */     
/*      */     public EntryIterator(K from) {
/* 1337 */       super(from);
/*      */     }
/*      */ 
/*      */ 
/*      */     
/*      */     final void acceptOnIndex(Consumer<? super Object2ShortMap.Entry<K>> action, int index) {
/* 1343 */       action.accept(new Object2ShortLinkedOpenHashMap.MapEntry(index));
/*      */     }
/*      */ 
/*      */     
/*      */     public Object2ShortLinkedOpenHashMap<K>.MapEntry next() {
/* 1348 */       return this.entry = new Object2ShortLinkedOpenHashMap.MapEntry(nextEntry());
/*      */     }
/*      */ 
/*      */     
/*      */     public Object2ShortLinkedOpenHashMap<K>.MapEntry previous() {
/* 1353 */       return this.entry = new Object2ShortLinkedOpenHashMap.MapEntry(previousEntry());
/*      */     }
/*      */ 
/*      */     
/*      */     public void remove() {
/* 1358 */       super.remove();
/* 1359 */       this.entry.index = -1;
/*      */     }
/*      */   }
/*      */   
/*      */   private final class FastEntryIterator extends MapIterator<Consumer<? super Object2ShortMap.Entry<K>>> implements ObjectListIterator<Object2ShortMap.Entry<K>> {
/* 1364 */     final Object2ShortLinkedOpenHashMap<K>.MapEntry entry = new Object2ShortLinkedOpenHashMap.MapEntry();
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public FastEntryIterator(K from) {
/* 1370 */       super(from);
/*      */     }
/*      */ 
/*      */ 
/*      */     
/*      */     final void acceptOnIndex(Consumer<? super Object2ShortMap.Entry<K>> action, int index) {
/* 1376 */       this.entry.index = index;
/* 1377 */       action.accept(this.entry);
/*      */     }
/*      */ 
/*      */     
/*      */     public Object2ShortLinkedOpenHashMap<K>.MapEntry next() {
/* 1382 */       this.entry.index = nextEntry();
/* 1383 */       return this.entry;
/*      */     }
/*      */ 
/*      */     
/*      */     public Object2ShortLinkedOpenHashMap<K>.MapEntry previous() {
/* 1388 */       this.entry.index = previousEntry();
/* 1389 */       return this.entry;
/*      */     }
/*      */     
/*      */     public FastEntryIterator() {} }
/*      */   
/*      */   private final class MapEntrySet extends AbstractObjectSortedSet<Object2ShortMap.Entry<K>> implements Object2ShortSortedMap.FastSortedEntrySet<K> {
/*      */     private static final int SPLITERATOR_CHARACTERISTICS = 81;
/*      */     
/*      */     public ObjectBidirectionalIterator<Object2ShortMap.Entry<K>> iterator() {
/* 1398 */       return new Object2ShortLinkedOpenHashMap.EntryIterator();
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
/*      */     public ObjectSpliterator<Object2ShortMap.Entry<K>> spliterator() {
/* 1416 */       return ObjectSpliterators.asSpliterator(iterator(), Size64.sizeOf(Object2ShortLinkedOpenHashMap.this), 81);
/*      */     }
/*      */ 
/*      */     
/*      */     public Comparator<? super Object2ShortMap.Entry<K>> comparator() {
/* 1421 */       return null;
/*      */     }
/*      */ 
/*      */     
/*      */     public ObjectSortedSet<Object2ShortMap.Entry<K>> subSet(Object2ShortMap.Entry<K> fromElement, Object2ShortMap.Entry<K> toElement) {
/* 1426 */       throw new UnsupportedOperationException();
/*      */     }
/*      */ 
/*      */     
/*      */     public ObjectSortedSet<Object2ShortMap.Entry<K>> headSet(Object2ShortMap.Entry<K> toElement) {
/* 1431 */       throw new UnsupportedOperationException();
/*      */     }
/*      */ 
/*      */     
/*      */     public ObjectSortedSet<Object2ShortMap.Entry<K>> tailSet(Object2ShortMap.Entry<K> fromElement) {
/* 1436 */       throw new UnsupportedOperationException();
/*      */     }
/*      */ 
/*      */     
/*      */     public Object2ShortMap.Entry<K> first() {
/* 1441 */       if (Object2ShortLinkedOpenHashMap.this.size == 0) throw new NoSuchElementException(); 
/* 1442 */       return new Object2ShortLinkedOpenHashMap.MapEntry(Object2ShortLinkedOpenHashMap.this.first);
/*      */     }
/*      */ 
/*      */     
/*      */     public Object2ShortMap.Entry<K> last() {
/* 1447 */       if (Object2ShortLinkedOpenHashMap.this.size == 0) throw new NoSuchElementException(); 
/* 1448 */       return new Object2ShortLinkedOpenHashMap.MapEntry(Object2ShortLinkedOpenHashMap.this.last);
/*      */     }
/*      */ 
/*      */ 
/*      */     
/*      */     public boolean contains(Object o) {
/* 1454 */       if (!(o instanceof Map.Entry)) return false; 
/* 1455 */       Map.Entry<?, ?> e = (Map.Entry<?, ?>)o;
/* 1456 */       if (e.getValue() == null || !(e.getValue() instanceof Short)) return false; 
/* 1457 */       K k = (K)e.getKey();
/* 1458 */       short v = ((Short)e.getValue()).shortValue();
/* 1459 */       if (k == null) return (Object2ShortLinkedOpenHashMap.this.containsNullKey && Object2ShortLinkedOpenHashMap.this.value[Object2ShortLinkedOpenHashMap.this.n] == v);
/*      */       
/* 1461 */       K[] key = Object2ShortLinkedOpenHashMap.this.key;
/*      */       K curr;
/*      */       int pos;
/* 1464 */       if ((curr = key[pos = HashCommon.mix(k.hashCode()) & Object2ShortLinkedOpenHashMap.this.mask]) == null) return false; 
/* 1465 */       if (k.equals(curr)) return (Object2ShortLinkedOpenHashMap.this.value[pos] == v);
/*      */       
/*      */       while (true) {
/* 1468 */         if ((curr = key[pos = pos + 1 & Object2ShortLinkedOpenHashMap.this.mask]) == null) return false; 
/* 1469 */         if (k.equals(curr)) return (Object2ShortLinkedOpenHashMap.this.value[pos] == v);
/*      */       
/*      */       } 
/*      */     }
/*      */ 
/*      */     
/*      */     public boolean remove(Object o) {
/* 1476 */       if (!(o instanceof Map.Entry)) return false; 
/* 1477 */       Map.Entry<?, ?> e = (Map.Entry<?, ?>)o;
/* 1478 */       if (e.getValue() == null || !(e.getValue() instanceof Short)) return false; 
/* 1479 */       K k = (K)e.getKey();
/* 1480 */       short v = ((Short)e.getValue()).shortValue();
/* 1481 */       if (k == null) {
/* 1482 */         if (Object2ShortLinkedOpenHashMap.this.containsNullKey && Object2ShortLinkedOpenHashMap.this.value[Object2ShortLinkedOpenHashMap.this.n] == v) {
/* 1483 */           Object2ShortLinkedOpenHashMap.this.removeNullEntry();
/* 1484 */           return true;
/*      */         } 
/* 1486 */         return false;
/*      */       } 
/*      */       
/* 1489 */       K[] key = Object2ShortLinkedOpenHashMap.this.key;
/*      */       K curr;
/*      */       int pos;
/* 1492 */       if ((curr = key[pos = HashCommon.mix(k.hashCode()) & Object2ShortLinkedOpenHashMap.this.mask]) == null) return false; 
/* 1493 */       if (curr.equals(k)) {
/* 1494 */         if (Object2ShortLinkedOpenHashMap.this.value[pos] == v) {
/* 1495 */           Object2ShortLinkedOpenHashMap.this.removeEntry(pos);
/* 1496 */           return true;
/*      */         } 
/* 1498 */         return false;
/*      */       } 
/*      */       while (true) {
/* 1501 */         if ((curr = key[pos = pos + 1 & Object2ShortLinkedOpenHashMap.this.mask]) == null) return false; 
/* 1502 */         if (curr.equals(k) && 
/* 1503 */           Object2ShortLinkedOpenHashMap.this.value[pos] == v) {
/* 1504 */           Object2ShortLinkedOpenHashMap.this.removeEntry(pos);
/* 1505 */           return true;
/*      */         } 
/*      */       } 
/*      */     }
/*      */ 
/*      */ 
/*      */     
/*      */     public int size() {
/* 1513 */       return Object2ShortLinkedOpenHashMap.this.size;
/*      */     }
/*      */ 
/*      */     
/*      */     public void clear() {
/* 1518 */       Object2ShortLinkedOpenHashMap.this.clear();
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
/*      */     public ObjectListIterator<Object2ShortMap.Entry<K>> iterator(Object2ShortMap.Entry<K> from) {
/* 1531 */       return new Object2ShortLinkedOpenHashMap.EntryIterator(from.getKey());
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public ObjectListIterator<Object2ShortMap.Entry<K>> fastIterator() {
/* 1542 */       return new Object2ShortLinkedOpenHashMap.FastEntryIterator();
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
/*      */     public ObjectListIterator<Object2ShortMap.Entry<K>> fastIterator(Object2ShortMap.Entry<K> from) {
/* 1555 */       return new Object2ShortLinkedOpenHashMap.FastEntryIterator(from.getKey());
/*      */     }
/*      */ 
/*      */ 
/*      */     
/*      */     public void forEach(Consumer<? super Object2ShortMap.Entry<K>> consumer) {
/* 1561 */       for (int i = Object2ShortLinkedOpenHashMap.this.size, next = Object2ShortLinkedOpenHashMap.this.first; i-- != 0; ) {
/* 1562 */         int curr = next;
/* 1563 */         next = (int)Object2ShortLinkedOpenHashMap.this.link[curr];
/* 1564 */         consumer.accept(new Object2ShortLinkedOpenHashMap.MapEntry(curr));
/*      */       } 
/*      */     }
/*      */ 
/*      */ 
/*      */     
/*      */     public void fastForEach(Consumer<? super Object2ShortMap.Entry<K>> consumer) {
/* 1571 */       Object2ShortLinkedOpenHashMap<K>.MapEntry entry = new Object2ShortLinkedOpenHashMap.MapEntry();
/* 1572 */       for (int i = Object2ShortLinkedOpenHashMap.this.size, next = Object2ShortLinkedOpenHashMap.this.first; i-- != 0; ) {
/* 1573 */         entry.index = next;
/* 1574 */         next = (int)Object2ShortLinkedOpenHashMap.this.link[next];
/* 1575 */         consumer.accept(entry);
/*      */       } 
/*      */     }
/*      */   }
/*      */ 
/*      */   
/*      */   public Object2ShortSortedMap.FastSortedEntrySet<K> object2ShortEntrySet() {
/* 1582 */     if (this.entries == null) this.entries = new MapEntrySet(); 
/* 1583 */     return this.entries;
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
/* 1596 */       super(k);
/*      */     }
/*      */ 
/*      */     
/*      */     public K previous() {
/* 1601 */       return Object2ShortLinkedOpenHashMap.this.key[previousEntry()];
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
/* 1613 */       action.accept(Object2ShortLinkedOpenHashMap.this.key[index]);
/*      */     }
/*      */ 
/*      */     
/*      */     public K next() {
/* 1618 */       return Object2ShortLinkedOpenHashMap.this.key[nextEntry()];
/*      */     } }
/*      */   
/*      */   private final class KeySet extends AbstractObjectSortedSet<K> {
/*      */     private static final int SPLITERATOR_CHARACTERISTICS = 81;
/*      */     
/*      */     private KeySet() {}
/*      */     
/*      */     public ObjectListIterator<K> iterator(K from) {
/* 1627 */       return new Object2ShortLinkedOpenHashMap.KeyIterator(from);
/*      */     }
/*      */ 
/*      */     
/*      */     public ObjectListIterator<K> iterator() {
/* 1632 */       return new Object2ShortLinkedOpenHashMap.KeyIterator();
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public ObjectSpliterator<K> spliterator() {
/* 1642 */       return ObjectSpliterators.asSpliterator(iterator(), Size64.sizeOf(Object2ShortLinkedOpenHashMap.this), 81);
/*      */     }
/*      */ 
/*      */ 
/*      */     
/*      */     public void forEach(Consumer<? super K> consumer) {
/* 1648 */       for (int i = Object2ShortLinkedOpenHashMap.this.size, next = Object2ShortLinkedOpenHashMap.this.first; i-- != 0; ) {
/* 1649 */         int curr = next;
/* 1650 */         next = (int)Object2ShortLinkedOpenHashMap.this.link[curr];
/* 1651 */         consumer.accept(Object2ShortLinkedOpenHashMap.this.key[curr]);
/*      */       } 
/*      */     }
/*      */ 
/*      */     
/*      */     public int size() {
/* 1657 */       return Object2ShortLinkedOpenHashMap.this.size;
/*      */     }
/*      */ 
/*      */     
/*      */     public boolean contains(Object k) {
/* 1662 */       return Object2ShortLinkedOpenHashMap.this.containsKey(k);
/*      */     }
/*      */ 
/*      */     
/*      */     public boolean remove(Object k) {
/* 1667 */       int oldSize = Object2ShortLinkedOpenHashMap.this.size;
/* 1668 */       Object2ShortLinkedOpenHashMap.this.removeShort(k);
/* 1669 */       return (Object2ShortLinkedOpenHashMap.this.size != oldSize);
/*      */     }
/*      */ 
/*      */     
/*      */     public void clear() {
/* 1674 */       Object2ShortLinkedOpenHashMap.this.clear();
/*      */     }
/*      */ 
/*      */     
/*      */     public K first() {
/* 1679 */       if (Object2ShortLinkedOpenHashMap.this.size == 0) throw new NoSuchElementException(); 
/* 1680 */       return Object2ShortLinkedOpenHashMap.this.key[Object2ShortLinkedOpenHashMap.this.first];
/*      */     }
/*      */ 
/*      */     
/*      */     public K last() {
/* 1685 */       if (Object2ShortLinkedOpenHashMap.this.size == 0) throw new NoSuchElementException(); 
/* 1686 */       return Object2ShortLinkedOpenHashMap.this.key[Object2ShortLinkedOpenHashMap.this.last];
/*      */     }
/*      */ 
/*      */     
/*      */     public Comparator<? super K> comparator() {
/* 1691 */       return null;
/*      */     }
/*      */ 
/*      */     
/*      */     public ObjectSortedSet<K> tailSet(K from) {
/* 1696 */       throw new UnsupportedOperationException();
/*      */     }
/*      */ 
/*      */     
/*      */     public ObjectSortedSet<K> headSet(K to) {
/* 1701 */       throw new UnsupportedOperationException();
/*      */     }
/*      */ 
/*      */     
/*      */     public ObjectSortedSet<K> subSet(K from, K to) {
/* 1706 */       throw new UnsupportedOperationException();
/*      */     }
/*      */   }
/*      */ 
/*      */   
/*      */   public ObjectSortedSet<K> keySet() {
/* 1712 */     if (this.keys == null) this.keys = new KeySet(); 
/* 1713 */     return this.keys;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private final class ValueIterator
/*      */     extends MapIterator<ShortConsumer>
/*      */     implements ShortListIterator
/*      */   {
/*      */     public short previousShort() {
/* 1727 */       return Object2ShortLinkedOpenHashMap.this.value[previousEntry()];
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
/*      */     final void acceptOnIndex(ShortConsumer action, int index) {
/* 1739 */       action.accept(Object2ShortLinkedOpenHashMap.this.value[index]);
/*      */     }
/*      */ 
/*      */     
/*      */     public short nextShort() {
/* 1744 */       return Object2ShortLinkedOpenHashMap.this.value[nextEntry()];
/*      */     }
/*      */   }
/*      */ 
/*      */   
/*      */   public ShortCollection values() {
/* 1750 */     if (this.values == null) this.values = (ShortCollection)new AbstractShortCollection()
/*      */         {
/*      */           private static final int SPLITERATOR_CHARACTERISTICS = 336;
/*      */           
/*      */           public ShortIterator iterator() {
/* 1755 */             return (ShortIterator)new Object2ShortLinkedOpenHashMap.ValueIterator();
/*      */           }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */           
/*      */           public ShortSpliterator spliterator() {
/* 1765 */             return ShortSpliterators.asSpliterator(iterator(), Size64.sizeOf(Object2ShortLinkedOpenHashMap.this), 336);
/*      */           }
/*      */ 
/*      */ 
/*      */           
/*      */           public void forEach(ShortConsumer consumer) {
/* 1771 */             for (int i = Object2ShortLinkedOpenHashMap.this.size, next = Object2ShortLinkedOpenHashMap.this.first; i-- != 0; ) {
/* 1772 */               int curr = next;
/* 1773 */               next = (int)Object2ShortLinkedOpenHashMap.this.link[curr];
/* 1774 */               consumer.accept(Object2ShortLinkedOpenHashMap.this.value[curr]);
/*      */             } 
/*      */           }
/*      */ 
/*      */           
/*      */           public int size() {
/* 1780 */             return Object2ShortLinkedOpenHashMap.this.size;
/*      */           }
/*      */ 
/*      */           
/*      */           public boolean contains(short v) {
/* 1785 */             return Object2ShortLinkedOpenHashMap.this.containsValue(v);
/*      */           }
/*      */ 
/*      */           
/*      */           public void clear() {
/* 1790 */             Object2ShortLinkedOpenHashMap.this.clear();
/*      */           }
/*      */         }; 
/* 1793 */     return this.values;
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
/* 1810 */     return trim(this.size);
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
/* 1832 */     int l = HashCommon.nextPowerOfTwo((int)Math.ceil((n / this.f)));
/* 1833 */     if (l >= this.n || this.size > HashCommon.maxFill(l, this.f)) return true; 
/*      */     try {
/* 1835 */       rehash(l);
/* 1836 */     } catch (OutOfMemoryError cantDoIt) {
/* 1837 */       return false;
/*      */     } 
/* 1839 */     return true;
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
/* 1854 */     K[] key = this.key;
/* 1855 */     short[] value = this.value;
/* 1856 */     int mask = newN - 1;
/* 1857 */     K[] newKey = (K[])new Object[newN + 1];
/* 1858 */     short[] newValue = new short[newN + 1];
/* 1859 */     int i = this.first, prev = -1, newPrev = -1;
/* 1860 */     long[] link = this.link;
/* 1861 */     long[] newLink = new long[newN + 1];
/* 1862 */     this.first = -1;
/* 1863 */     for (int j = this.size; j-- != 0; ) {
/* 1864 */       int pos; if (key[i] == null) { pos = newN; }
/*      */       else
/* 1866 */       { pos = HashCommon.mix(key[i].hashCode()) & mask;
/* 1867 */         for (; newKey[pos] != null; pos = pos + 1 & mask); }
/*      */       
/* 1869 */       newKey[pos] = key[i];
/* 1870 */       newValue[pos] = value[i];
/* 1871 */       if (prev != -1) {
/* 1872 */         newLink[newPrev] = newLink[newPrev] ^ (newLink[newPrev] ^ pos & 0xFFFFFFFFL) & 0xFFFFFFFFL;
/* 1873 */         newLink[pos] = newLink[pos] ^ (newLink[pos] ^ (newPrev & 0xFFFFFFFFL) << 32L) & 0xFFFFFFFF00000000L;
/* 1874 */         newPrev = pos;
/*      */       } else {
/* 1876 */         newPrev = this.first = pos;
/*      */         
/* 1878 */         newLink[pos] = -1L;
/*      */       } 
/* 1880 */       int t = i;
/* 1881 */       i = (int)link[i];
/* 1882 */       prev = t;
/*      */     } 
/* 1884 */     this.link = newLink;
/* 1885 */     this.last = newPrev;
/* 1886 */     if (newPrev != -1)
/*      */     {
/* 1888 */       newLink[newPrev] = newLink[newPrev] | 0xFFFFFFFFL; } 
/* 1889 */     this.n = newN;
/* 1890 */     this.mask = mask;
/* 1891 */     this.maxFill = HashCommon.maxFill(this.n, this.f);
/* 1892 */     this.key = newKey;
/* 1893 */     this.value = newValue;
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
/*      */   public Object2ShortLinkedOpenHashMap<K> clone() {
/*      */     Object2ShortLinkedOpenHashMap<K> c;
/*      */     try {
/* 1910 */       c = (Object2ShortLinkedOpenHashMap<K>)super.clone();
/* 1911 */     } catch (CloneNotSupportedException cantHappen) {
/* 1912 */       throw new InternalError();
/*      */     } 
/* 1914 */     c.keys = null;
/* 1915 */     c.values = null;
/* 1916 */     c.entries = null;
/* 1917 */     c.containsNullKey = this.containsNullKey;
/* 1918 */     c.key = (K[])this.key.clone();
/* 1919 */     c.value = (short[])this.value.clone();
/* 1920 */     c.link = (long[])this.link.clone();
/* 1921 */     return c;
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
/* 1935 */     int h = 0;
/* 1936 */     for (int j = realSize(), i = 0, t = 0; j-- != 0; ) {
/* 1937 */       for (; this.key[i] == null; i++);
/* 1938 */       if (this != this.key[i]) t = this.key[i].hashCode(); 
/* 1939 */       t ^= this.value[i];
/* 1940 */       h += t;
/* 1941 */       i++;
/*      */     } 
/*      */     
/* 1944 */     if (this.containsNullKey) h += this.value[this.n]; 
/* 1945 */     return h;
/*      */   }
/*      */   
/*      */   private void writeObject(ObjectOutputStream s) throws IOException {
/* 1949 */     K[] key = this.key;
/* 1950 */     short[] value = this.value;
/* 1951 */     EntryIterator i = new EntryIterator();
/* 1952 */     s.defaultWriteObject();
/* 1953 */     for (int j = this.size; j-- != 0; ) {
/* 1954 */       int e = i.nextEntry();
/* 1955 */       s.writeObject(key[e]);
/* 1956 */       s.writeShort(value[e]);
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   private void readObject(ObjectInputStream s) throws IOException, ClassNotFoundException {
/* 1962 */     s.defaultReadObject();
/* 1963 */     this.n = HashCommon.arraySize(this.size, this.f);
/* 1964 */     this.maxFill = HashCommon.maxFill(this.n, this.f);
/* 1965 */     this.mask = this.n - 1;
/* 1966 */     K[] key = this.key = (K[])new Object[this.n + 1];
/* 1967 */     short[] value = this.value = new short[this.n + 1];
/* 1968 */     long[] link = this.link = new long[this.n + 1];
/* 1969 */     int prev = -1;
/* 1970 */     this.first = this.last = -1;
/*      */ 
/*      */     
/* 1973 */     for (int i = this.size; i-- != 0; ) {
/* 1974 */       int pos; K k = (K)s.readObject();
/* 1975 */       short v = s.readShort();
/* 1976 */       if (k == null) {
/* 1977 */         pos = this.n;
/* 1978 */         this.containsNullKey = true;
/*      */       } else {
/* 1980 */         pos = HashCommon.mix(k.hashCode()) & this.mask;
/* 1981 */         for (; key[pos] != null; pos = pos + 1 & this.mask);
/*      */       } 
/* 1983 */       key[pos] = k;
/* 1984 */       value[pos] = v;
/* 1985 */       if (this.first != -1) {
/* 1986 */         link[prev] = link[prev] ^ (link[prev] ^ pos & 0xFFFFFFFFL) & 0xFFFFFFFFL;
/* 1987 */         link[pos] = link[pos] ^ (link[pos] ^ (prev & 0xFFFFFFFFL) << 32L) & 0xFFFFFFFF00000000L;
/* 1988 */         prev = pos; continue;
/*      */       } 
/* 1990 */       prev = this.first = pos;
/*      */       
/* 1992 */       link[pos] = link[pos] | 0xFFFFFFFF00000000L;
/*      */     } 
/*      */     
/* 1995 */     this.last = prev;
/* 1996 */     if (prev != -1)
/*      */     {
/* 1998 */       link[prev] = link[prev] | 0xFFFFFFFFL;
/*      */     }
/*      */   }
/*      */   
/*      */   private void checkTable() {}
/*      */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\i\\unimi\dsi\fastutil\objects\Object2ShortLinkedOpenHashMap.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */