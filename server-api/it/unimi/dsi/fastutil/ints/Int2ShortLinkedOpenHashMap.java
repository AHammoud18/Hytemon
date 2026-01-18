/*      */ package it.unimi.dsi.fastutil.ints;
/*      */ 
/*      */ import it.unimi.dsi.fastutil.Hash;
/*      */ import it.unimi.dsi.fastutil.HashCommon;
/*      */ import it.unimi.dsi.fastutil.SafeMath;
/*      */ import it.unimi.dsi.fastutil.Size64;
/*      */ import it.unimi.dsi.fastutil.objects.AbstractObjectSortedSet;
/*      */ import it.unimi.dsi.fastutil.objects.ObjectBidirectionalIterator;
/*      */ import it.unimi.dsi.fastutil.objects.ObjectIterator;
/*      */ import it.unimi.dsi.fastutil.objects.ObjectListIterator;
/*      */ import it.unimi.dsi.fastutil.objects.ObjectSet;
/*      */ import it.unimi.dsi.fastutil.objects.ObjectSortedSet;
/*      */ import it.unimi.dsi.fastutil.objects.ObjectSpliterator;
/*      */ import it.unimi.dsi.fastutil.objects.ObjectSpliterators;
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
/*      */ import java.util.SortedSet;
/*      */ import java.util.Spliterator;
/*      */ import java.util.function.BiFunction;
/*      */ import java.util.function.Consumer;
/*      */ import java.util.function.IntConsumer;
/*      */ import java.util.function.IntFunction;
/*      */ import java.util.function.IntUnaryOperator;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ public class Int2ShortLinkedOpenHashMap
/*      */   extends AbstractInt2ShortSortedMap
/*      */   implements Serializable, Cloneable, Hash
/*      */ {
/*      */   private static final long serialVersionUID = 0L;
/*      */   private static final boolean ASSERTS = false;
/*      */   protected transient int[] key;
/*      */   protected transient short[] value;
/*      */   protected transient int mask;
/*      */   protected transient boolean containsNullKey;
/*  108 */   protected transient int first = -1;
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*  113 */   protected transient int last = -1;
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
/*      */   protected transient Int2ShortSortedMap.FastSortedEntrySet entries;
/*      */ 
/*      */   
/*      */   protected transient IntSortedSet keys;
/*      */ 
/*      */   
/*      */   protected transient ShortCollection values;
/*      */ 
/*      */ 
/*      */   
/*      */   public Int2ShortLinkedOpenHashMap(int expected, float f) {
/*  148 */     if (f <= 0.0F || f >= 1.0F) throw new IllegalArgumentException("Load factor must be greater than 0 and smaller than 1"); 
/*  149 */     if (expected < 0) throw new IllegalArgumentException("The expected number of elements must be nonnegative"); 
/*  150 */     this.f = f;
/*  151 */     this.minN = this.n = HashCommon.arraySize(expected, f);
/*  152 */     this.mask = this.n - 1;
/*  153 */     this.maxFill = HashCommon.maxFill(this.n, f);
/*  154 */     this.key = new int[this.n + 1];
/*  155 */     this.value = new short[this.n + 1];
/*  156 */     this.link = new long[this.n + 1];
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Int2ShortLinkedOpenHashMap(int expected) {
/*  165 */     this(expected, 0.75F);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Int2ShortLinkedOpenHashMap() {
/*  173 */     this(16, 0.75F);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Int2ShortLinkedOpenHashMap(Map<? extends Integer, ? extends Short> m, float f) {
/*  183 */     this(m.size(), f);
/*  184 */     putAll(m);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Int2ShortLinkedOpenHashMap(Map<? extends Integer, ? extends Short> m) {
/*  193 */     this(m, 0.75F);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Int2ShortLinkedOpenHashMap(Int2ShortMap m, float f) {
/*  203 */     this(m.size(), f);
/*  204 */     putAll(m);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Int2ShortLinkedOpenHashMap(Int2ShortMap m) {
/*  214 */     this(m, 0.75F);
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
/*      */   public Int2ShortLinkedOpenHashMap(int[] k, short[] v, float f) {
/*  226 */     this(k.length, f);
/*  227 */     if (k.length != v.length) throw new IllegalArgumentException("The key array and the value array have different lengths (" + k.length + " and " + v.length + ")"); 
/*  228 */     for (int i = 0; i < k.length; ) { put(k[i], v[i]); i++; }
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
/*      */   public Int2ShortLinkedOpenHashMap(int[] k, short[] v) {
/*  240 */     this(k, v, 0.75F);
/*      */   }
/*      */   
/*      */   private int realSize() {
/*  244 */     return this.containsNullKey ? (this.size - 1) : this.size;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void ensureCapacity(int capacity) {
/*  254 */     int needed = HashCommon.arraySize(capacity, this.f);
/*  255 */     if (needed > this.n) rehash(needed); 
/*      */   }
/*      */   
/*      */   private void tryCapacity(long capacity) {
/*  259 */     int needed = (int)Math.min(1073741824L, Math.max(2L, HashCommon.nextPowerOfTwo((long)Math.ceil(((float)capacity / this.f)))));
/*  260 */     if (needed > this.n) rehash(needed); 
/*      */   }
/*      */   
/*      */   private short removeEntry(int pos) {
/*  264 */     short oldValue = this.value[pos];
/*  265 */     this.size--;
/*  266 */     fixPointers(pos);
/*  267 */     shiftKeys(pos);
/*  268 */     if (this.n > this.minN && this.size < this.maxFill / 4 && this.n > 16) rehash(this.n / 2); 
/*  269 */     return oldValue;
/*      */   }
/*      */   
/*      */   private short removeNullEntry() {
/*  273 */     this.containsNullKey = false;
/*  274 */     short oldValue = this.value[this.n];
/*  275 */     this.size--;
/*  276 */     fixPointers(this.n);
/*  277 */     if (this.n > this.minN && this.size < this.maxFill / 4 && this.n > 16) rehash(this.n / 2); 
/*  278 */     return oldValue;
/*      */   }
/*      */ 
/*      */   
/*      */   public void putAll(Map<? extends Integer, ? extends Short> m) {
/*  283 */     if (this.f <= 0.5D) { ensureCapacity(m.size()); }
/*  284 */     else { tryCapacity((size() + m.size())); }
/*      */     
/*  286 */     super.putAll(m);
/*      */   }
/*      */   
/*      */   private int find(int k) {
/*  290 */     if (k == 0) return this.containsNullKey ? this.n : -(this.n + 1);
/*      */     
/*  292 */     int[] key = this.key;
/*      */     
/*      */     int curr, pos;
/*  295 */     if ((curr = key[pos = HashCommon.mix(k) & this.mask]) == 0) return -(pos + 1); 
/*  296 */     if (k == curr) return pos;
/*      */     
/*      */     while (true) {
/*  299 */       if ((curr = key[pos = pos + 1 & this.mask]) == 0) return -(pos + 1); 
/*  300 */       if (k == curr) return pos; 
/*      */     } 
/*      */   }
/*      */   
/*      */   private void insert(int pos, int k, short v) {
/*  305 */     if (pos == this.n) this.containsNullKey = true; 
/*  306 */     this.key[pos] = k;
/*  307 */     this.value[pos] = v;
/*  308 */     if (this.size == 0) {
/*  309 */       this.first = this.last = pos;
/*      */       
/*  311 */       this.link[pos] = -1L;
/*      */     } else {
/*  313 */       this.link[this.last] = this.link[this.last] ^ (this.link[this.last] ^ pos & 0xFFFFFFFFL) & 0xFFFFFFFFL;
/*  314 */       this.link[pos] = (this.last & 0xFFFFFFFFL) << 32L | 0xFFFFFFFFL;
/*  315 */       this.last = pos;
/*      */     } 
/*  317 */     if (this.size++ >= this.maxFill) rehash(HashCommon.arraySize(this.size + 1, this.f));
/*      */   
/*      */   }
/*      */ 
/*      */   
/*      */   public short put(int k, short v) {
/*  323 */     int pos = find(k);
/*  324 */     if (pos < 0) {
/*  325 */       insert(-pos - 1, k, v);
/*  326 */       return this.defRetValue;
/*      */     } 
/*  328 */     short oldValue = this.value[pos];
/*  329 */     this.value[pos] = v;
/*  330 */     return oldValue;
/*      */   }
/*      */   
/*      */   private short addToValue(int pos, short incr) {
/*  334 */     short oldValue = this.value[pos];
/*  335 */     this.value[pos] = (short)(oldValue + incr);
/*  336 */     return oldValue;
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
/*      */   public short addTo(int k, short incr) {
/*      */     int pos;
/*  354 */     if (k == 0) {
/*  355 */       if (this.containsNullKey) return addToValue(this.n, incr); 
/*  356 */       pos = this.n;
/*  357 */       this.containsNullKey = true;
/*      */     } else {
/*      */       
/*  360 */       int[] key = this.key;
/*      */       int curr;
/*  362 */       if ((curr = key[pos = HashCommon.mix(k) & this.mask]) != 0) {
/*  363 */         if (curr == k) return addToValue(pos, incr); 
/*  364 */         while ((curr = key[pos = pos + 1 & this.mask]) != 0) { if (curr == k) return addToValue(pos, incr);  }
/*      */       
/*      */       } 
/*  367 */     }  this.key[pos] = k;
/*  368 */     this.value[pos] = (short)(this.defRetValue + incr);
/*  369 */     if (this.size == 0) {
/*  370 */       this.first = this.last = pos;
/*      */       
/*  372 */       this.link[pos] = -1L;
/*      */     } else {
/*  374 */       this.link[this.last] = this.link[this.last] ^ (this.link[this.last] ^ pos & 0xFFFFFFFFL) & 0xFFFFFFFFL;
/*  375 */       this.link[pos] = (this.last & 0xFFFFFFFFL) << 32L | 0xFFFFFFFFL;
/*  376 */       this.last = pos;
/*      */     } 
/*  378 */     if (this.size++ >= this.maxFill) rehash(HashCommon.arraySize(this.size + 1, this.f));
/*      */     
/*  380 */     return this.defRetValue;
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
/*  393 */     int[] key = this.key; while (true) {
/*      */       int curr, last;
/*  395 */       pos = (last = pos) + 1 & this.mask;
/*      */       while (true) {
/*  397 */         if ((curr = key[pos]) == 0) {
/*  398 */           key[last] = 0;
/*      */           return;
/*      */         } 
/*  401 */         int slot = HashCommon.mix(curr) & this.mask;
/*  402 */         if ((last <= pos) ? (last >= slot || slot > pos) : (last >= slot && slot > pos))
/*  403 */           break;  pos = pos + 1 & this.mask;
/*      */       } 
/*  405 */       key[last] = curr;
/*  406 */       this.value[last] = this.value[pos];
/*  407 */       fixPointers(pos, last);
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public short remove(int k) {
/*  414 */     if (k == 0) {
/*  415 */       if (this.containsNullKey) return removeNullEntry(); 
/*  416 */       return this.defRetValue;
/*      */     } 
/*      */     
/*  419 */     int[] key = this.key;
/*      */     
/*      */     int curr, pos;
/*  422 */     if ((curr = key[pos = HashCommon.mix(k) & this.mask]) == 0) return this.defRetValue; 
/*  423 */     if (k == curr) return removeEntry(pos); 
/*      */     while (true) {
/*  425 */       if ((curr = key[pos = pos + 1 & this.mask]) == 0) return this.defRetValue; 
/*  426 */       if (k == curr) return removeEntry(pos); 
/*      */     } 
/*      */   }
/*      */   
/*      */   private short setValue(int pos, short v) {
/*  431 */     short oldValue = this.value[pos];
/*  432 */     this.value[pos] = v;
/*  433 */     return oldValue;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public short removeFirstShort() {
/*  443 */     if (this.size == 0) throw new NoSuchElementException(); 
/*  444 */     int pos = this.first;
/*      */     
/*  446 */     if (this.size == 1) { this.first = this.last = -1; }
/*      */     else
/*  448 */     { this.first = (int)this.link[pos];
/*  449 */       if (0 <= this.first)
/*      */       {
/*  451 */         this.link[this.first] = this.link[this.first] | 0xFFFFFFFF00000000L;
/*      */       } }
/*      */     
/*  454 */     this.size--;
/*  455 */     short v = this.value[pos];
/*  456 */     if (pos == this.n)
/*  457 */     { this.containsNullKey = false; }
/*  458 */     else { shiftKeys(pos); }
/*  459 */      if (this.n > this.minN && this.size < this.maxFill / 4 && this.n > 16) rehash(this.n / 2); 
/*  460 */     return v;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public short removeLastShort() {
/*  470 */     if (this.size == 0) throw new NoSuchElementException(); 
/*  471 */     int pos = this.last;
/*      */     
/*  473 */     if (this.size == 1) { this.first = this.last = -1; }
/*      */     else
/*  475 */     { this.last = (int)(this.link[pos] >>> 32L);
/*  476 */       if (0 <= this.last)
/*      */       {
/*  478 */         this.link[this.last] = this.link[this.last] | 0xFFFFFFFFL;
/*      */       } }
/*      */     
/*  481 */     this.size--;
/*  482 */     short v = this.value[pos];
/*  483 */     if (pos == this.n)
/*  484 */     { this.containsNullKey = false; }
/*  485 */     else { shiftKeys(pos); }
/*  486 */      if (this.n > this.minN && this.size < this.maxFill / 4 && this.n > 16) rehash(this.n / 2); 
/*  487 */     return v;
/*      */   }
/*      */   
/*      */   private void moveIndexToFirst(int i) {
/*  491 */     if (this.size == 1 || this.first == i)
/*  492 */       return;  if (this.last == i) {
/*  493 */       this.last = (int)(this.link[i] >>> 32L);
/*      */       
/*  495 */       this.link[this.last] = this.link[this.last] | 0xFFFFFFFFL;
/*      */     } else {
/*  497 */       long linki = this.link[i];
/*  498 */       int prev = (int)(linki >>> 32L);
/*  499 */       int next = (int)linki;
/*  500 */       this.link[prev] = this.link[prev] ^ (this.link[prev] ^ linki & 0xFFFFFFFFL) & 0xFFFFFFFFL;
/*  501 */       this.link[next] = this.link[next] ^ (this.link[next] ^ linki & 0xFFFFFFFF00000000L) & 0xFFFFFFFF00000000L;
/*      */     } 
/*  503 */     this.link[this.first] = this.link[this.first] ^ (this.link[this.first] ^ (i & 0xFFFFFFFFL) << 32L) & 0xFFFFFFFF00000000L;
/*  504 */     this.link[i] = 0xFFFFFFFF00000000L | this.first & 0xFFFFFFFFL;
/*  505 */     this.first = i;
/*      */   }
/*      */   
/*      */   private void moveIndexToLast(int i) {
/*  509 */     if (this.size == 1 || this.last == i)
/*  510 */       return;  if (this.first == i) {
/*  511 */       this.first = (int)this.link[i];
/*      */       
/*  513 */       this.link[this.first] = this.link[this.first] | 0xFFFFFFFF00000000L;
/*      */     } else {
/*  515 */       long linki = this.link[i];
/*  516 */       int prev = (int)(linki >>> 32L);
/*  517 */       int next = (int)linki;
/*  518 */       this.link[prev] = this.link[prev] ^ (this.link[prev] ^ linki & 0xFFFFFFFFL) & 0xFFFFFFFFL;
/*  519 */       this.link[next] = this.link[next] ^ (this.link[next] ^ linki & 0xFFFFFFFF00000000L) & 0xFFFFFFFF00000000L;
/*      */     } 
/*  521 */     this.link[this.last] = this.link[this.last] ^ (this.link[this.last] ^ i & 0xFFFFFFFFL) & 0xFFFFFFFFL;
/*  522 */     this.link[i] = (this.last & 0xFFFFFFFFL) << 32L | 0xFFFFFFFFL;
/*  523 */     this.last = i;
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
/*      */   public short getAndMoveToFirst(int k) {
/*  535 */     if (k == 0) {
/*  536 */       if (this.containsNullKey) {
/*  537 */         moveIndexToFirst(this.n);
/*  538 */         return this.value[this.n];
/*      */       } 
/*  540 */       return this.defRetValue;
/*      */     } 
/*      */     
/*  543 */     int[] key = this.key;
/*      */     
/*      */     int curr, pos;
/*  546 */     if ((curr = key[pos = HashCommon.mix(k) & this.mask]) == 0) return this.defRetValue; 
/*  547 */     if (k == curr) {
/*  548 */       moveIndexToFirst(pos);
/*  549 */       return this.value[pos];
/*      */     } 
/*      */     
/*      */     while (true) {
/*  553 */       if ((curr = key[pos = pos + 1 & this.mask]) == 0) return this.defRetValue; 
/*  554 */       if (k == curr) {
/*  555 */         moveIndexToFirst(pos);
/*  556 */         return this.value[pos];
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
/*      */   public short getAndMoveToLast(int k) {
/*  570 */     if (k == 0) {
/*  571 */       if (this.containsNullKey) {
/*  572 */         moveIndexToLast(this.n);
/*  573 */         return this.value[this.n];
/*      */       } 
/*  575 */       return this.defRetValue;
/*      */     } 
/*      */     
/*  578 */     int[] key = this.key;
/*      */     
/*      */     int curr, pos;
/*  581 */     if ((curr = key[pos = HashCommon.mix(k) & this.mask]) == 0) return this.defRetValue; 
/*  582 */     if (k == curr) {
/*  583 */       moveIndexToLast(pos);
/*  584 */       return this.value[pos];
/*      */     } 
/*      */     
/*      */     while (true) {
/*  588 */       if ((curr = key[pos = pos + 1 & this.mask]) == 0) return this.defRetValue; 
/*  589 */       if (k == curr) {
/*  590 */         moveIndexToLast(pos);
/*  591 */         return this.value[pos];
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
/*      */   public short putAndMoveToFirst(int k, short v) {
/*      */     int pos;
/*  607 */     if (k == 0) {
/*  608 */       if (this.containsNullKey) {
/*  609 */         moveIndexToFirst(this.n);
/*  610 */         return setValue(this.n, v);
/*      */       } 
/*  612 */       this.containsNullKey = true;
/*  613 */       pos = this.n;
/*      */     } else {
/*      */       
/*  616 */       int[] key = this.key;
/*      */       int curr;
/*  618 */       if ((curr = key[pos = HashCommon.mix(k) & this.mask]) != 0) {
/*  619 */         if (curr == k) {
/*  620 */           moveIndexToFirst(pos);
/*  621 */           return setValue(pos, v);
/*      */         } 
/*  623 */         while ((curr = key[pos = pos + 1 & this.mask]) != 0) { if (curr == k) {
/*  624 */             moveIndexToFirst(pos);
/*  625 */             return setValue(pos, v);
/*      */           }  }
/*      */       
/*      */       } 
/*  629 */     }  this.key[pos] = k;
/*  630 */     this.value[pos] = v;
/*  631 */     if (this.size == 0) {
/*  632 */       this.first = this.last = pos;
/*      */       
/*  634 */       this.link[pos] = -1L;
/*      */     } else {
/*  636 */       this.link[this.first] = this.link[this.first] ^ (this.link[this.first] ^ (pos & 0xFFFFFFFFL) << 32L) & 0xFFFFFFFF00000000L;
/*  637 */       this.link[pos] = 0xFFFFFFFF00000000L | this.first & 0xFFFFFFFFL;
/*  638 */       this.first = pos;
/*      */     } 
/*  640 */     if (this.size++ >= this.maxFill) rehash(HashCommon.arraySize(this.size, this.f));
/*      */     
/*  642 */     return this.defRetValue;
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
/*      */   public short putAndMoveToLast(int k, short v) {
/*      */     int pos;
/*  656 */     if (k == 0) {
/*  657 */       if (this.containsNullKey) {
/*  658 */         moveIndexToLast(this.n);
/*  659 */         return setValue(this.n, v);
/*      */       } 
/*  661 */       this.containsNullKey = true;
/*  662 */       pos = this.n;
/*      */     } else {
/*      */       
/*  665 */       int[] key = this.key;
/*      */       int curr;
/*  667 */       if ((curr = key[pos = HashCommon.mix(k) & this.mask]) != 0) {
/*  668 */         if (curr == k) {
/*  669 */           moveIndexToLast(pos);
/*  670 */           return setValue(pos, v);
/*      */         } 
/*  672 */         while ((curr = key[pos = pos + 1 & this.mask]) != 0) { if (curr == k) {
/*  673 */             moveIndexToLast(pos);
/*  674 */             return setValue(pos, v);
/*      */           }  }
/*      */       
/*      */       } 
/*  678 */     }  this.key[pos] = k;
/*  679 */     this.value[pos] = v;
/*  680 */     if (this.size == 0) {
/*  681 */       this.first = this.last = pos;
/*      */       
/*  683 */       this.link[pos] = -1L;
/*      */     } else {
/*  685 */       this.link[this.last] = this.link[this.last] ^ (this.link[this.last] ^ pos & 0xFFFFFFFFL) & 0xFFFFFFFFL;
/*  686 */       this.link[pos] = (this.last & 0xFFFFFFFFL) << 32L | 0xFFFFFFFFL;
/*  687 */       this.last = pos;
/*      */     } 
/*  689 */     if (this.size++ >= this.maxFill) rehash(HashCommon.arraySize(this.size, this.f));
/*      */     
/*  691 */     return this.defRetValue;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public short get(int k) {
/*  697 */     if (k == 0) return this.containsNullKey ? this.value[this.n] : this.defRetValue;
/*      */     
/*  699 */     int[] key = this.key;
/*      */     
/*      */     int curr, pos;
/*  702 */     if ((curr = key[pos = HashCommon.mix(k) & this.mask]) == 0) return this.defRetValue; 
/*  703 */     if (k == curr) return this.value[pos];
/*      */     
/*      */     while (true) {
/*  706 */       if ((curr = key[pos = pos + 1 & this.mask]) == 0) return this.defRetValue; 
/*  707 */       if (k == curr) return this.value[pos];
/*      */     
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   public boolean containsKey(int k) {
/*  714 */     if (k == 0) return this.containsNullKey;
/*      */     
/*  716 */     int[] key = this.key;
/*      */     
/*      */     int curr, pos;
/*  719 */     if ((curr = key[pos = HashCommon.mix(k) & this.mask]) == 0) return false; 
/*  720 */     if (k == curr) return true;
/*      */     
/*      */     while (true) {
/*  723 */       if ((curr = key[pos = pos + 1 & this.mask]) == 0) return false; 
/*  724 */       if (k == curr) return true;
/*      */     
/*      */     } 
/*      */   }
/*      */   
/*      */   public boolean containsValue(short v) {
/*  730 */     short[] value = this.value;
/*  731 */     int[] key = this.key;
/*  732 */     if (this.containsNullKey && value[this.n] == v) return true; 
/*  733 */     for (int i = this.n; i-- != 0;) { if (key[i] != 0 && value[i] == v) return true;  }
/*  734 */      return false;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public short getOrDefault(int k, short defaultValue) {
/*  741 */     if (k == 0) return this.containsNullKey ? this.value[this.n] : defaultValue;
/*      */     
/*  743 */     int[] key = this.key;
/*      */     
/*      */     int curr, pos;
/*  746 */     if ((curr = key[pos = HashCommon.mix(k) & this.mask]) == 0) return defaultValue; 
/*  747 */     if (k == curr) return this.value[pos];
/*      */     
/*      */     while (true) {
/*  750 */       if ((curr = key[pos = pos + 1 & this.mask]) == 0) return defaultValue; 
/*  751 */       if (k == curr) return this.value[pos];
/*      */     
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   public short putIfAbsent(int k, short v) {
/*  758 */     int pos = find(k);
/*  759 */     if (pos >= 0) return this.value[pos]; 
/*  760 */     insert(-pos - 1, k, v);
/*  761 */     return this.defRetValue;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean remove(int k, short v) {
/*  768 */     if (k == 0) {
/*  769 */       if (this.containsNullKey && v == this.value[this.n]) {
/*  770 */         removeNullEntry();
/*  771 */         return true;
/*      */       } 
/*  773 */       return false;
/*      */     } 
/*      */     
/*  776 */     int[] key = this.key;
/*      */     
/*      */     int curr, pos;
/*  779 */     if ((curr = key[pos = HashCommon.mix(k) & this.mask]) == 0) return false; 
/*  780 */     if (k == curr && v == this.value[pos]) {
/*  781 */       removeEntry(pos);
/*  782 */       return true;
/*      */     } 
/*      */     while (true) {
/*  785 */       if ((curr = key[pos = pos + 1 & this.mask]) == 0) return false; 
/*  786 */       if (k == curr && v == this.value[pos]) {
/*  787 */         removeEntry(pos);
/*  788 */         return true;
/*      */       } 
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean replace(int k, short oldValue, short v) {
/*  796 */     int pos = find(k);
/*  797 */     if (pos < 0 || oldValue != this.value[pos]) return false; 
/*  798 */     this.value[pos] = v;
/*  799 */     return true;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public short replace(int k, short v) {
/*  805 */     int pos = find(k);
/*  806 */     if (pos < 0) return this.defRetValue; 
/*  807 */     short oldValue = this.value[pos];
/*  808 */     this.value[pos] = v;
/*  809 */     return oldValue;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public short computeIfAbsent(int k, IntUnaryOperator mappingFunction) {
/*  815 */     Objects.requireNonNull(mappingFunction);
/*  816 */     int pos = find(k);
/*  817 */     if (pos >= 0) return this.value[pos]; 
/*  818 */     short newValue = SafeMath.safeIntToShort(mappingFunction.applyAsInt(k));
/*  819 */     insert(-pos - 1, k, newValue);
/*  820 */     return newValue;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public short computeIfAbsent(int key, Int2ShortFunction mappingFunction) {
/*  826 */     Objects.requireNonNull(mappingFunction);
/*  827 */     int pos = find(key);
/*  828 */     if (pos >= 0) return this.value[pos]; 
/*  829 */     if (!mappingFunction.containsKey(key)) return this.defRetValue; 
/*  830 */     short newValue = mappingFunction.get(key);
/*  831 */     insert(-pos - 1, key, newValue);
/*  832 */     return newValue;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public short computeIfAbsentNullable(int k, IntFunction<? extends Short> mappingFunction) {
/*  838 */     Objects.requireNonNull(mappingFunction);
/*  839 */     int pos = find(k);
/*  840 */     if (pos >= 0) return this.value[pos]; 
/*  841 */     Short newValue = mappingFunction.apply(k);
/*  842 */     if (newValue == null) return this.defRetValue; 
/*  843 */     short v = newValue.shortValue();
/*  844 */     insert(-pos - 1, k, v);
/*  845 */     return v;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public short computeIfPresent(int k, BiFunction<? super Integer, ? super Short, ? extends Short> remappingFunction) {
/*  851 */     Objects.requireNonNull(remappingFunction);
/*  852 */     int pos = find(k);
/*  853 */     if (pos < 0) return this.defRetValue; 
/*  854 */     Short newValue = remappingFunction.apply(Integer.valueOf(k), Short.valueOf(this.value[pos]));
/*  855 */     if (newValue == null) {
/*  856 */       if (k == 0) { removeNullEntry(); }
/*  857 */       else { removeEntry(pos); }
/*  858 */        return this.defRetValue;
/*      */     } 
/*  860 */     this.value[pos] = newValue.shortValue(); return newValue.shortValue();
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public short compute(int k, BiFunction<? super Integer, ? super Short, ? extends Short> remappingFunction) {
/*  866 */     Objects.requireNonNull(remappingFunction);
/*  867 */     int pos = find(k);
/*  868 */     Short newValue = remappingFunction.apply(Integer.valueOf(k), (pos >= 0) ? Short.valueOf(this.value[pos]) : null);
/*  869 */     if (newValue == null) {
/*  870 */       if (pos >= 0)
/*  871 */         if (k == 0) { removeNullEntry(); }
/*  872 */         else { removeEntry(pos); }
/*      */          
/*  874 */       return this.defRetValue;
/*      */     } 
/*  876 */     short newVal = newValue.shortValue();
/*  877 */     if (pos < 0) {
/*  878 */       insert(-pos - 1, k, newVal);
/*  879 */       return newVal;
/*      */     } 
/*  881 */     this.value[pos] = newVal; return newVal;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public short merge(int k, short v, BiFunction<? super Short, ? super Short, ? extends Short> remappingFunction) {
/*  887 */     Objects.requireNonNull(remappingFunction);
/*      */     
/*  889 */     int pos = find(k);
/*  890 */     if (pos < 0) {
/*  891 */       if (pos < 0) { insert(-pos - 1, k, v); }
/*  892 */       else { this.value[pos] = v; }
/*  893 */        return v;
/*      */     } 
/*  895 */     Short newValue = remappingFunction.apply(Short.valueOf(this.value[pos]), Short.valueOf(v));
/*  896 */     if (newValue == null) {
/*  897 */       if (k == 0) { removeNullEntry(); }
/*  898 */       else { removeEntry(pos); }
/*  899 */        return this.defRetValue;
/*      */     } 
/*  901 */     this.value[pos] = newValue.shortValue(); return newValue.shortValue();
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
/*  912 */     if (this.size == 0)
/*  913 */       return;  this.size = 0;
/*  914 */     this.containsNullKey = false;
/*  915 */     Arrays.fill(this.key, 0);
/*  916 */     this.first = this.last = -1;
/*      */   }
/*      */ 
/*      */   
/*      */   public int size() {
/*  921 */     return this.size;
/*      */   }
/*      */ 
/*      */   
/*      */   public boolean isEmpty() {
/*  926 */     return (this.size == 0);
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   final class MapEntry
/*      */     implements Int2ShortMap.Entry, Map.Entry<Integer, Short>, IntShortPair
/*      */   {
/*      */     int index;
/*      */ 
/*      */ 
/*      */     
/*      */     MapEntry(int index) {
/*  939 */       this.index = index;
/*      */     }
/*      */ 
/*      */     
/*      */     MapEntry() {}
/*      */ 
/*      */     
/*      */     public int getIntKey() {
/*  947 */       return Int2ShortLinkedOpenHashMap.this.key[this.index];
/*      */     }
/*      */ 
/*      */     
/*      */     public int leftInt() {
/*  952 */       return Int2ShortLinkedOpenHashMap.this.key[this.index];
/*      */     }
/*      */ 
/*      */     
/*      */     public short getShortValue() {
/*  957 */       return Int2ShortLinkedOpenHashMap.this.value[this.index];
/*      */     }
/*      */ 
/*      */     
/*      */     public short rightShort() {
/*  962 */       return Int2ShortLinkedOpenHashMap.this.value[this.index];
/*      */     }
/*      */ 
/*      */     
/*      */     public short setValue(short v) {
/*  967 */       short oldValue = Int2ShortLinkedOpenHashMap.this.value[this.index];
/*  968 */       Int2ShortLinkedOpenHashMap.this.value[this.index] = v;
/*  969 */       return oldValue;
/*      */     }
/*      */ 
/*      */     
/*      */     public IntShortPair right(short v) {
/*  974 */       Int2ShortLinkedOpenHashMap.this.value[this.index] = v;
/*  975 */       return this;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     @Deprecated
/*      */     public Integer getKey() {
/*  986 */       return Integer.valueOf(Int2ShortLinkedOpenHashMap.this.key[this.index]);
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
/*  997 */       return Short.valueOf(Int2ShortLinkedOpenHashMap.this.value[this.index]);
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
/* 1008 */       return Short.valueOf(setValue(v.shortValue()));
/*      */     }
/*      */ 
/*      */ 
/*      */     
/*      */     public boolean equals(Object o) {
/* 1014 */       if (!(o instanceof Map.Entry)) return false; 
/* 1015 */       Map.Entry<Integer, Short> e = (Map.Entry<Integer, Short>)o;
/* 1016 */       return (Int2ShortLinkedOpenHashMap.this.key[this.index] == ((Integer)e.getKey()).intValue() && Int2ShortLinkedOpenHashMap.this.value[this.index] == ((Short)e.getValue()).shortValue());
/*      */     }
/*      */ 
/*      */     
/*      */     public int hashCode() {
/* 1021 */       return Int2ShortLinkedOpenHashMap.this.key[this.index] ^ Int2ShortLinkedOpenHashMap.this.value[this.index];
/*      */     }
/*      */ 
/*      */     
/*      */     public String toString() {
/* 1026 */       return Int2ShortLinkedOpenHashMap.this.key[this.index] + "=>" + Int2ShortLinkedOpenHashMap.this.value[this.index];
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
/* 1037 */     if (this.size == 0) {
/* 1038 */       this.first = this.last = -1;
/*      */       return;
/*      */     } 
/* 1041 */     if (this.first == i) {
/* 1042 */       this.first = (int)this.link[i];
/* 1043 */       if (0 <= this.first)
/*      */       {
/* 1045 */         this.link[this.first] = this.link[this.first] | 0xFFFFFFFF00000000L;
/*      */       }
/*      */       return;
/*      */     } 
/* 1049 */     if (this.last == i) {
/* 1050 */       this.last = (int)(this.link[i] >>> 32L);
/* 1051 */       if (0 <= this.last)
/*      */       {
/* 1053 */         this.link[this.last] = this.link[this.last] | 0xFFFFFFFFL;
/*      */       }
/*      */       return;
/*      */     } 
/* 1057 */     long linki = this.link[i];
/* 1058 */     int prev = (int)(linki >>> 32L);
/* 1059 */     int next = (int)linki;
/* 1060 */     this.link[prev] = this.link[prev] ^ (this.link[prev] ^ linki & 0xFFFFFFFFL) & 0xFFFFFFFFL;
/* 1061 */     this.link[next] = this.link[next] ^ (this.link[next] ^ linki & 0xFFFFFFFF00000000L) & 0xFFFFFFFF00000000L;
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
/* 1073 */     if (this.size == 1) {
/* 1074 */       this.first = this.last = d;
/*      */       
/* 1076 */       this.link[d] = -1L;
/*      */       return;
/*      */     } 
/* 1079 */     if (this.first == s) {
/* 1080 */       this.first = d;
/* 1081 */       this.link[(int)this.link[s]] = this.link[(int)this.link[s]] ^ (this.link[(int)this.link[s]] ^ (d & 0xFFFFFFFFL) << 32L) & 0xFFFFFFFF00000000L;
/* 1082 */       this.link[d] = this.link[s];
/*      */       return;
/*      */     } 
/* 1085 */     if (this.last == s) {
/* 1086 */       this.last = d;
/* 1087 */       this.link[(int)(this.link[s] >>> 32L)] = this.link[(int)(this.link[s] >>> 32L)] ^ (this.link[(int)(this.link[s] >>> 32L)] ^ d & 0xFFFFFFFFL) & 0xFFFFFFFFL;
/* 1088 */       this.link[d] = this.link[s];
/*      */       return;
/*      */     } 
/* 1091 */     long links = this.link[s];
/* 1092 */     int prev = (int)(links >>> 32L);
/* 1093 */     int next = (int)links;
/* 1094 */     this.link[prev] = this.link[prev] ^ (this.link[prev] ^ d & 0xFFFFFFFFL) & 0xFFFFFFFFL;
/* 1095 */     this.link[next] = this.link[next] ^ (this.link[next] ^ (d & 0xFFFFFFFFL) << 32L) & 0xFFFFFFFF00000000L;
/* 1096 */     this.link[d] = links;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public int firstIntKey() {
/* 1106 */     if (this.size == 0) throw new NoSuchElementException(); 
/* 1107 */     return this.key[this.first];
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public int lastIntKey() {
/* 1117 */     if (this.size == 0) throw new NoSuchElementException(); 
/* 1118 */     return this.key[this.last];
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Int2ShortSortedMap tailMap(int from) {
/* 1128 */     throw new UnsupportedOperationException();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Int2ShortSortedMap headMap(int to) {
/* 1138 */     throw new UnsupportedOperationException();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Int2ShortSortedMap subMap(int from, int to) {
/* 1148 */     throw new UnsupportedOperationException();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public IntComparator comparator() {
/* 1158 */     return null;
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
/* 1173 */     int prev = -1;
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 1178 */     int next = -1;
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 1183 */     int curr = -1;
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 1188 */     int index = -1;
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     protected MapIterator() {
/* 1194 */       this.next = Int2ShortLinkedOpenHashMap.this.first;
/* 1195 */       this.index = 0;
/*      */     }
/*      */     
/*      */     private MapIterator(int from) {
/* 1199 */       if (from == 0) {
/* 1200 */         if (Int2ShortLinkedOpenHashMap.this.containsNullKey) {
/* 1201 */           this.next = (int)Int2ShortLinkedOpenHashMap.this.link[Int2ShortLinkedOpenHashMap.this.n];
/* 1202 */           this.prev = Int2ShortLinkedOpenHashMap.this.n; return;
/*      */         } 
/* 1204 */         throw new NoSuchElementException("The key " + from + " does not belong to this map.");
/*      */       } 
/* 1206 */       if (Int2ShortLinkedOpenHashMap.this.key[Int2ShortLinkedOpenHashMap.this.last] == from) {
/* 1207 */         this.prev = Int2ShortLinkedOpenHashMap.this.last;
/* 1208 */         this.index = Int2ShortLinkedOpenHashMap.this.size;
/*      */         
/*      */         return;
/*      */       } 
/* 1212 */       int pos = HashCommon.mix(from) & Int2ShortLinkedOpenHashMap.this.mask;
/*      */       
/* 1214 */       while (Int2ShortLinkedOpenHashMap.this.key[pos] != 0) {
/* 1215 */         if (Int2ShortLinkedOpenHashMap.this.key[pos] == from) {
/*      */           
/* 1217 */           this.next = (int)Int2ShortLinkedOpenHashMap.this.link[pos];
/* 1218 */           this.prev = pos;
/*      */           return;
/*      */         } 
/* 1221 */         pos = pos + 1 & Int2ShortLinkedOpenHashMap.this.mask;
/*      */       } 
/* 1223 */       throw new NoSuchElementException("The key " + from + " does not belong to this map.");
/*      */     }
/*      */     
/*      */     public boolean hasNext() {
/* 1227 */       return (this.next != -1);
/*      */     }
/*      */     
/*      */     public boolean hasPrevious() {
/* 1231 */       return (this.prev != -1);
/*      */     }
/*      */     
/*      */     private final void ensureIndexKnown() {
/* 1235 */       if (this.index >= 0)
/* 1236 */         return;  if (this.prev == -1) {
/* 1237 */         this.index = 0;
/*      */         return;
/*      */       } 
/* 1240 */       if (this.next == -1) {
/* 1241 */         this.index = Int2ShortLinkedOpenHashMap.this.size;
/*      */         return;
/*      */       } 
/* 1244 */       int pos = Int2ShortLinkedOpenHashMap.this.first;
/* 1245 */       this.index = 1;
/* 1246 */       while (pos != this.prev) {
/* 1247 */         pos = (int)Int2ShortLinkedOpenHashMap.this.link[pos];
/* 1248 */         this.index++;
/*      */       } 
/*      */     }
/*      */     
/*      */     public int nextIndex() {
/* 1253 */       ensureIndexKnown();
/* 1254 */       return this.index;
/*      */     }
/*      */     
/*      */     public int previousIndex() {
/* 1258 */       ensureIndexKnown();
/* 1259 */       return this.index - 1;
/*      */     }
/*      */     
/*      */     public int nextEntry() {
/* 1263 */       if (!hasNext()) throw new NoSuchElementException(); 
/* 1264 */       this.curr = this.next;
/* 1265 */       this.next = (int)Int2ShortLinkedOpenHashMap.this.link[this.curr];
/* 1266 */       this.prev = this.curr;
/* 1267 */       if (this.index >= 0) this.index++; 
/* 1268 */       return this.curr;
/*      */     }
/*      */     
/*      */     public int previousEntry() {
/* 1272 */       if (!hasPrevious()) throw new NoSuchElementException(); 
/* 1273 */       this.curr = this.prev;
/* 1274 */       this.prev = (int)(Int2ShortLinkedOpenHashMap.this.link[this.curr] >>> 32L);
/* 1275 */       this.next = this.curr;
/* 1276 */       if (this.index >= 0) this.index--; 
/* 1277 */       return this.curr;
/*      */     }
/*      */     
/*      */     public void forEachRemaining(ConsumerType action) {
/* 1281 */       while (hasNext()) {
/* 1282 */         this.curr = this.next;
/* 1283 */         this.next = (int)Int2ShortLinkedOpenHashMap.this.link[this.curr];
/* 1284 */         this.prev = this.curr;
/* 1285 */         if (this.index >= 0) this.index++; 
/* 1286 */         acceptOnIndex(action, this.curr);
/*      */       } 
/*      */     }
/*      */     
/*      */     public void remove() {
/* 1291 */       ensureIndexKnown();
/* 1292 */       if (this.curr == -1) throw new IllegalStateException(); 
/* 1293 */       if (this.curr == this.prev)
/*      */       
/*      */       { 
/* 1296 */         this.index--;
/* 1297 */         this.prev = (int)(Int2ShortLinkedOpenHashMap.this.link[this.curr] >>> 32L); }
/* 1298 */       else { this.next = (int)Int2ShortLinkedOpenHashMap.this.link[this.curr]; }
/* 1299 */        Int2ShortLinkedOpenHashMap.this.size--;
/*      */ 
/*      */       
/* 1302 */       if (this.prev == -1) { Int2ShortLinkedOpenHashMap.this.first = this.next; }
/* 1303 */       else { Int2ShortLinkedOpenHashMap.this.link[this.prev] = Int2ShortLinkedOpenHashMap.this.link[this.prev] ^ (Int2ShortLinkedOpenHashMap.this.link[this.prev] ^ this.next & 0xFFFFFFFFL) & 0xFFFFFFFFL; }
/* 1304 */        if (this.next == -1) { Int2ShortLinkedOpenHashMap.this.last = this.prev; }
/* 1305 */       else { Int2ShortLinkedOpenHashMap.this.link[this.next] = Int2ShortLinkedOpenHashMap.this.link[this.next] ^ (Int2ShortLinkedOpenHashMap.this.link[this.next] ^ (this.prev & 0xFFFFFFFFL) << 32L) & 0xFFFFFFFF00000000L; }
/* 1306 */        int pos = this.curr;
/* 1307 */       this.curr = -1;
/* 1308 */       if (pos == Int2ShortLinkedOpenHashMap.this.n) {
/* 1309 */         Int2ShortLinkedOpenHashMap.this.containsNullKey = false;
/*      */       } else {
/*      */         
/* 1312 */         int[] key = Int2ShortLinkedOpenHashMap.this.key;
/*      */         while (true) {
/*      */           int curr, last;
/* 1315 */           pos = (last = pos) + 1 & Int2ShortLinkedOpenHashMap.this.mask;
/*      */           while (true) {
/* 1317 */             if ((curr = key[pos]) == 0) {
/* 1318 */               key[last] = 0;
/*      */               return;
/*      */             } 
/* 1321 */             int slot = HashCommon.mix(curr) & Int2ShortLinkedOpenHashMap.this.mask;
/* 1322 */             if ((last <= pos) ? (last >= slot || slot > pos) : (last >= slot && slot > pos))
/* 1323 */               break;  pos = pos + 1 & Int2ShortLinkedOpenHashMap.this.mask;
/*      */           } 
/* 1325 */           key[last] = curr;
/* 1326 */           Int2ShortLinkedOpenHashMap.this.value[last] = Int2ShortLinkedOpenHashMap.this.value[pos];
/* 1327 */           if (this.next == pos) this.next = last; 
/* 1328 */           if (this.prev == pos) this.prev = last; 
/* 1329 */           Int2ShortLinkedOpenHashMap.this.fixPointers(pos, last);
/*      */         } 
/*      */       } 
/*      */     }
/*      */     
/*      */     public int skip(int n) {
/* 1335 */       int i = n;
/* 1336 */       for (; i-- != 0 && hasNext(); nextEntry());
/* 1337 */       return n - i - 1;
/*      */     }
/*      */     
/*      */     public int back(int n) {
/* 1341 */       int i = n;
/* 1342 */       for (; i-- != 0 && hasPrevious(); previousEntry());
/* 1343 */       return n - i - 1;
/*      */     }
/*      */     
/*      */     public void set(Int2ShortMap.Entry ok) {
/* 1347 */       throw new UnsupportedOperationException();
/*      */     }
/*      */     
/*      */     public void add(Int2ShortMap.Entry ok) {
/* 1351 */       throw new UnsupportedOperationException();
/*      */     }
/*      */     
/*      */     abstract void acceptOnIndex(ConsumerType param1ConsumerType, int param1Int); }
/*      */   
/*      */   private final class EntryIterator extends MapIterator<Consumer<? super Int2ShortMap.Entry>> implements ObjectListIterator<Int2ShortMap.Entry> {
/*      */     private Int2ShortLinkedOpenHashMap.MapEntry entry;
/*      */     
/*      */     public EntryIterator() {}
/*      */     
/*      */     public EntryIterator(int from) {
/* 1362 */       super(from);
/*      */     }
/*      */ 
/*      */ 
/*      */     
/*      */     final void acceptOnIndex(Consumer<? super Int2ShortMap.Entry> action, int index) {
/* 1368 */       action.accept(new Int2ShortLinkedOpenHashMap.MapEntry(index));
/*      */     }
/*      */ 
/*      */     
/*      */     public Int2ShortLinkedOpenHashMap.MapEntry next() {
/* 1373 */       return this.entry = new Int2ShortLinkedOpenHashMap.MapEntry(nextEntry());
/*      */     }
/*      */ 
/*      */     
/*      */     public Int2ShortLinkedOpenHashMap.MapEntry previous() {
/* 1378 */       return this.entry = new Int2ShortLinkedOpenHashMap.MapEntry(previousEntry());
/*      */     }
/*      */ 
/*      */     
/*      */     public void remove() {
/* 1383 */       super.remove();
/* 1384 */       this.entry.index = -1;
/*      */     }
/*      */   }
/*      */   
/*      */   private final class FastEntryIterator extends MapIterator<Consumer<? super Int2ShortMap.Entry>> implements ObjectListIterator<Int2ShortMap.Entry> {
/* 1389 */     final Int2ShortLinkedOpenHashMap.MapEntry entry = new Int2ShortLinkedOpenHashMap.MapEntry();
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public FastEntryIterator(int from) {
/* 1395 */       super(from);
/*      */     }
/*      */ 
/*      */ 
/*      */     
/*      */     final void acceptOnIndex(Consumer<? super Int2ShortMap.Entry> action, int index) {
/* 1401 */       this.entry.index = index;
/* 1402 */       action.accept(this.entry);
/*      */     }
/*      */ 
/*      */     
/*      */     public Int2ShortLinkedOpenHashMap.MapEntry next() {
/* 1407 */       this.entry.index = nextEntry();
/* 1408 */       return this.entry;
/*      */     }
/*      */ 
/*      */     
/*      */     public Int2ShortLinkedOpenHashMap.MapEntry previous() {
/* 1413 */       this.entry.index = previousEntry();
/* 1414 */       return this.entry;
/*      */     }
/*      */     
/*      */     public FastEntryIterator() {} }
/*      */   
/*      */   private final class MapEntrySet extends AbstractObjectSortedSet<Int2ShortMap.Entry> implements Int2ShortSortedMap.FastSortedEntrySet {
/*      */     private static final int SPLITERATOR_CHARACTERISTICS = 81;
/*      */     
/*      */     public ObjectBidirectionalIterator<Int2ShortMap.Entry> iterator() {
/* 1423 */       return (ObjectBidirectionalIterator<Int2ShortMap.Entry>)new Int2ShortLinkedOpenHashMap.EntryIterator();
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
/*      */     public ObjectSpliterator<Int2ShortMap.Entry> spliterator() {
/* 1441 */       return ObjectSpliterators.asSpliterator((ObjectIterator)iterator(), Size64.sizeOf(Int2ShortLinkedOpenHashMap.this), 81);
/*      */     }
/*      */ 
/*      */     
/*      */     public Comparator<? super Int2ShortMap.Entry> comparator() {
/* 1446 */       return null;
/*      */     }
/*      */ 
/*      */     
/*      */     public ObjectSortedSet<Int2ShortMap.Entry> subSet(Int2ShortMap.Entry fromElement, Int2ShortMap.Entry toElement) {
/* 1451 */       throw new UnsupportedOperationException();
/*      */     }
/*      */ 
/*      */     
/*      */     public ObjectSortedSet<Int2ShortMap.Entry> headSet(Int2ShortMap.Entry toElement) {
/* 1456 */       throw new UnsupportedOperationException();
/*      */     }
/*      */ 
/*      */     
/*      */     public ObjectSortedSet<Int2ShortMap.Entry> tailSet(Int2ShortMap.Entry fromElement) {
/* 1461 */       throw new UnsupportedOperationException();
/*      */     }
/*      */ 
/*      */     
/*      */     public Int2ShortMap.Entry first() {
/* 1466 */       if (Int2ShortLinkedOpenHashMap.this.size == 0) throw new NoSuchElementException(); 
/* 1467 */       return new Int2ShortLinkedOpenHashMap.MapEntry(Int2ShortLinkedOpenHashMap.this.first);
/*      */     }
/*      */ 
/*      */     
/*      */     public Int2ShortMap.Entry last() {
/* 1472 */       if (Int2ShortLinkedOpenHashMap.this.size == 0) throw new NoSuchElementException(); 
/* 1473 */       return new Int2ShortLinkedOpenHashMap.MapEntry(Int2ShortLinkedOpenHashMap.this.last);
/*      */     }
/*      */ 
/*      */ 
/*      */     
/*      */     public boolean contains(Object o) {
/* 1479 */       if (!(o instanceof Map.Entry)) return false; 
/* 1480 */       Map.Entry<?, ?> e = (Map.Entry<?, ?>)o;
/* 1481 */       if (e.getKey() == null || !(e.getKey() instanceof Integer)) return false; 
/* 1482 */       if (e.getValue() == null || !(e.getValue() instanceof Short)) return false; 
/* 1483 */       int k = ((Integer)e.getKey()).intValue();
/* 1484 */       short v = ((Short)e.getValue()).shortValue();
/* 1485 */       if (k == 0) return (Int2ShortLinkedOpenHashMap.this.containsNullKey && Int2ShortLinkedOpenHashMap.this.value[Int2ShortLinkedOpenHashMap.this.n] == v);
/*      */       
/* 1487 */       int[] key = Int2ShortLinkedOpenHashMap.this.key;
/*      */       
/*      */       int curr, pos;
/* 1490 */       if ((curr = key[pos = HashCommon.mix(k) & Int2ShortLinkedOpenHashMap.this.mask]) == 0) return false; 
/* 1491 */       if (k == curr) return (Int2ShortLinkedOpenHashMap.this.value[pos] == v);
/*      */       
/*      */       while (true) {
/* 1494 */         if ((curr = key[pos = pos + 1 & Int2ShortLinkedOpenHashMap.this.mask]) == 0) return false; 
/* 1495 */         if (k == curr) return (Int2ShortLinkedOpenHashMap.this.value[pos] == v);
/*      */       
/*      */       } 
/*      */     }
/*      */ 
/*      */     
/*      */     public boolean remove(Object o) {
/* 1502 */       if (!(o instanceof Map.Entry)) return false; 
/* 1503 */       Map.Entry<?, ?> e = (Map.Entry<?, ?>)o;
/* 1504 */       if (e.getKey() == null || !(e.getKey() instanceof Integer)) return false; 
/* 1505 */       if (e.getValue() == null || !(e.getValue() instanceof Short)) return false; 
/* 1506 */       int k = ((Integer)e.getKey()).intValue();
/* 1507 */       short v = ((Short)e.getValue()).shortValue();
/* 1508 */       if (k == 0) {
/* 1509 */         if (Int2ShortLinkedOpenHashMap.this.containsNullKey && Int2ShortLinkedOpenHashMap.this.value[Int2ShortLinkedOpenHashMap.this.n] == v) {
/* 1510 */           Int2ShortLinkedOpenHashMap.this.removeNullEntry();
/* 1511 */           return true;
/*      */         } 
/* 1513 */         return false;
/*      */       } 
/*      */       
/* 1516 */       int[] key = Int2ShortLinkedOpenHashMap.this.key;
/*      */       
/*      */       int curr, pos;
/* 1519 */       if ((curr = key[pos = HashCommon.mix(k) & Int2ShortLinkedOpenHashMap.this.mask]) == 0) return false; 
/* 1520 */       if (curr == k) {
/* 1521 */         if (Int2ShortLinkedOpenHashMap.this.value[pos] == v) {
/* 1522 */           Int2ShortLinkedOpenHashMap.this.removeEntry(pos);
/* 1523 */           return true;
/*      */         } 
/* 1525 */         return false;
/*      */       } 
/*      */       while (true) {
/* 1528 */         if ((curr = key[pos = pos + 1 & Int2ShortLinkedOpenHashMap.this.mask]) == 0) return false; 
/* 1529 */         if (curr == k && 
/* 1530 */           Int2ShortLinkedOpenHashMap.this.value[pos] == v) {
/* 1531 */           Int2ShortLinkedOpenHashMap.this.removeEntry(pos);
/* 1532 */           return true;
/*      */         } 
/*      */       } 
/*      */     }
/*      */ 
/*      */ 
/*      */     
/*      */     public int size() {
/* 1540 */       return Int2ShortLinkedOpenHashMap.this.size;
/*      */     }
/*      */ 
/*      */     
/*      */     public void clear() {
/* 1545 */       Int2ShortLinkedOpenHashMap.this.clear();
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
/*      */     public ObjectListIterator<Int2ShortMap.Entry> iterator(Int2ShortMap.Entry from) {
/* 1558 */       return new Int2ShortLinkedOpenHashMap.EntryIterator(from.getIntKey());
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public ObjectListIterator<Int2ShortMap.Entry> fastIterator() {
/* 1569 */       return new Int2ShortLinkedOpenHashMap.FastEntryIterator();
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
/*      */     public ObjectListIterator<Int2ShortMap.Entry> fastIterator(Int2ShortMap.Entry from) {
/* 1582 */       return new Int2ShortLinkedOpenHashMap.FastEntryIterator(from.getIntKey());
/*      */     }
/*      */ 
/*      */ 
/*      */     
/*      */     public void forEach(Consumer<? super Int2ShortMap.Entry> consumer) {
/* 1588 */       for (int i = Int2ShortLinkedOpenHashMap.this.size, next = Int2ShortLinkedOpenHashMap.this.first; i-- != 0; ) {
/* 1589 */         int curr = next;
/* 1590 */         next = (int)Int2ShortLinkedOpenHashMap.this.link[curr];
/* 1591 */         consumer.accept(new Int2ShortLinkedOpenHashMap.MapEntry(curr));
/*      */       } 
/*      */     }
/*      */ 
/*      */ 
/*      */     
/*      */     public void fastForEach(Consumer<? super Int2ShortMap.Entry> consumer) {
/* 1598 */       Int2ShortLinkedOpenHashMap.MapEntry entry = new Int2ShortLinkedOpenHashMap.MapEntry();
/* 1599 */       for (int i = Int2ShortLinkedOpenHashMap.this.size, next = Int2ShortLinkedOpenHashMap.this.first; i-- != 0; ) {
/* 1600 */         entry.index = next;
/* 1601 */         next = (int)Int2ShortLinkedOpenHashMap.this.link[next];
/* 1602 */         consumer.accept(entry);
/*      */       } 
/*      */     }
/*      */   }
/*      */ 
/*      */   
/*      */   public Int2ShortSortedMap.FastSortedEntrySet int2ShortEntrySet() {
/* 1609 */     if (this.entries == null) this.entries = new MapEntrySet(); 
/* 1610 */     return this.entries;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private final class KeyIterator
/*      */     extends MapIterator<IntConsumer>
/*      */     implements IntListIterator
/*      */   {
/*      */     public KeyIterator(int k) {
/* 1623 */       super(k);
/*      */     }
/*      */ 
/*      */     
/*      */     public int previousInt() {
/* 1628 */       return Int2ShortLinkedOpenHashMap.this.key[previousEntry()];
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
/*      */     final void acceptOnIndex(IntConsumer action, int index) {
/* 1640 */       action.accept(Int2ShortLinkedOpenHashMap.this.key[index]);
/*      */     }
/*      */ 
/*      */     
/*      */     public int nextInt() {
/* 1645 */       return Int2ShortLinkedOpenHashMap.this.key[nextEntry()];
/*      */     } }
/*      */   
/*      */   private final class KeySet extends AbstractIntSortedSet {
/*      */     private static final int SPLITERATOR_CHARACTERISTICS = 337;
/*      */     
/*      */     private KeySet() {}
/*      */     
/*      */     public IntListIterator iterator(int from) {
/* 1654 */       return new Int2ShortLinkedOpenHashMap.KeyIterator(from);
/*      */     }
/*      */ 
/*      */     
/*      */     public IntListIterator iterator() {
/* 1659 */       return new Int2ShortLinkedOpenHashMap.KeyIterator();
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public IntSpliterator spliterator() {
/* 1669 */       return IntSpliterators.asSpliterator(iterator(), Size64.sizeOf(Int2ShortLinkedOpenHashMap.this), 337);
/*      */     }
/*      */ 
/*      */ 
/*      */     
/*      */     public void forEach(IntConsumer consumer) {
/* 1675 */       for (int i = Int2ShortLinkedOpenHashMap.this.size, next = Int2ShortLinkedOpenHashMap.this.first; i-- != 0; ) {
/* 1676 */         int curr = next;
/* 1677 */         next = (int)Int2ShortLinkedOpenHashMap.this.link[curr];
/* 1678 */         consumer.accept(Int2ShortLinkedOpenHashMap.this.key[curr]);
/*      */       } 
/*      */     }
/*      */ 
/*      */     
/*      */     public int size() {
/* 1684 */       return Int2ShortLinkedOpenHashMap.this.size;
/*      */     }
/*      */ 
/*      */     
/*      */     public boolean contains(int k) {
/* 1689 */       return Int2ShortLinkedOpenHashMap.this.containsKey(k);
/*      */     }
/*      */ 
/*      */     
/*      */     public boolean remove(int k) {
/* 1694 */       int oldSize = Int2ShortLinkedOpenHashMap.this.size;
/* 1695 */       Int2ShortLinkedOpenHashMap.this.remove(k);
/* 1696 */       return (Int2ShortLinkedOpenHashMap.this.size != oldSize);
/*      */     }
/*      */ 
/*      */     
/*      */     public void clear() {
/* 1701 */       Int2ShortLinkedOpenHashMap.this.clear();
/*      */     }
/*      */ 
/*      */     
/*      */     public int firstInt() {
/* 1706 */       if (Int2ShortLinkedOpenHashMap.this.size == 0) throw new NoSuchElementException(); 
/* 1707 */       return Int2ShortLinkedOpenHashMap.this.key[Int2ShortLinkedOpenHashMap.this.first];
/*      */     }
/*      */ 
/*      */     
/*      */     public int lastInt() {
/* 1712 */       if (Int2ShortLinkedOpenHashMap.this.size == 0) throw new NoSuchElementException(); 
/* 1713 */       return Int2ShortLinkedOpenHashMap.this.key[Int2ShortLinkedOpenHashMap.this.last];
/*      */     }
/*      */ 
/*      */     
/*      */     public IntComparator comparator() {
/* 1718 */       return null;
/*      */     }
/*      */ 
/*      */     
/*      */     public IntSortedSet tailSet(int from) {
/* 1723 */       throw new UnsupportedOperationException();
/*      */     }
/*      */ 
/*      */     
/*      */     public IntSortedSet headSet(int to) {
/* 1728 */       throw new UnsupportedOperationException();
/*      */     }
/*      */ 
/*      */     
/*      */     public IntSortedSet subSet(int from, int to) {
/* 1733 */       throw new UnsupportedOperationException();
/*      */     }
/*      */   }
/*      */ 
/*      */   
/*      */   public IntSortedSet keySet() {
/* 1739 */     if (this.keys == null) this.keys = new KeySet(); 
/* 1740 */     return this.keys;
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
/* 1754 */       return Int2ShortLinkedOpenHashMap.this.value[previousEntry()];
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
/* 1766 */       action.accept(Int2ShortLinkedOpenHashMap.this.value[index]);
/*      */     }
/*      */ 
/*      */     
/*      */     public short nextShort() {
/* 1771 */       return Int2ShortLinkedOpenHashMap.this.value[nextEntry()];
/*      */     }
/*      */   }
/*      */ 
/*      */   
/*      */   public ShortCollection values() {
/* 1777 */     if (this.values == null) this.values = (ShortCollection)new AbstractShortCollection()
/*      */         {
/*      */           private static final int SPLITERATOR_CHARACTERISTICS = 336;
/*      */           
/*      */           public ShortIterator iterator() {
/* 1782 */             return (ShortIterator)new Int2ShortLinkedOpenHashMap.ValueIterator();
/*      */           }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */           
/*      */           public ShortSpliterator spliterator() {
/* 1792 */             return ShortSpliterators.asSpliterator(iterator(), Size64.sizeOf(Int2ShortLinkedOpenHashMap.this), 336);
/*      */           }
/*      */ 
/*      */ 
/*      */           
/*      */           public void forEach(ShortConsumer consumer) {
/* 1798 */             for (int i = Int2ShortLinkedOpenHashMap.this.size, next = Int2ShortLinkedOpenHashMap.this.first; i-- != 0; ) {
/* 1799 */               int curr = next;
/* 1800 */               next = (int)Int2ShortLinkedOpenHashMap.this.link[curr];
/* 1801 */               consumer.accept(Int2ShortLinkedOpenHashMap.this.value[curr]);
/*      */             } 
/*      */           }
/*      */ 
/*      */           
/*      */           public int size() {
/* 1807 */             return Int2ShortLinkedOpenHashMap.this.size;
/*      */           }
/*      */ 
/*      */           
/*      */           public boolean contains(short v) {
/* 1812 */             return Int2ShortLinkedOpenHashMap.this.containsValue(v);
/*      */           }
/*      */ 
/*      */           
/*      */           public void clear() {
/* 1817 */             Int2ShortLinkedOpenHashMap.this.clear();
/*      */           }
/*      */         }; 
/* 1820 */     return this.values;
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
/* 1837 */     return trim(this.size);
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
/* 1859 */     int l = HashCommon.nextPowerOfTwo((int)Math.ceil((n / this.f)));
/* 1860 */     if (l >= this.n || this.size > HashCommon.maxFill(l, this.f)) return true; 
/*      */     try {
/* 1862 */       rehash(l);
/* 1863 */     } catch (OutOfMemoryError cantDoIt) {
/* 1864 */       return false;
/*      */     } 
/* 1866 */     return true;
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
/* 1881 */     int[] key = this.key;
/* 1882 */     short[] value = this.value;
/* 1883 */     int mask = newN - 1;
/* 1884 */     int[] newKey = new int[newN + 1];
/* 1885 */     short[] newValue = new short[newN + 1];
/* 1886 */     int i = this.first, prev = -1, newPrev = -1;
/* 1887 */     long[] link = this.link;
/* 1888 */     long[] newLink = new long[newN + 1];
/* 1889 */     this.first = -1;
/* 1890 */     for (int j = this.size; j-- != 0; ) {
/* 1891 */       int pos; if (key[i] == 0) { pos = newN; }
/*      */       else
/* 1893 */       { pos = HashCommon.mix(key[i]) & mask;
/* 1894 */         for (; newKey[pos] != 0; pos = pos + 1 & mask); }
/*      */       
/* 1896 */       newKey[pos] = key[i];
/* 1897 */       newValue[pos] = value[i];
/* 1898 */       if (prev != -1) {
/* 1899 */         newLink[newPrev] = newLink[newPrev] ^ (newLink[newPrev] ^ pos & 0xFFFFFFFFL) & 0xFFFFFFFFL;
/* 1900 */         newLink[pos] = newLink[pos] ^ (newLink[pos] ^ (newPrev & 0xFFFFFFFFL) << 32L) & 0xFFFFFFFF00000000L;
/* 1901 */         newPrev = pos;
/*      */       } else {
/* 1903 */         newPrev = this.first = pos;
/*      */         
/* 1905 */         newLink[pos] = -1L;
/*      */       } 
/* 1907 */       int t = i;
/* 1908 */       i = (int)link[i];
/* 1909 */       prev = t;
/*      */     } 
/* 1911 */     this.link = newLink;
/* 1912 */     this.last = newPrev;
/* 1913 */     if (newPrev != -1)
/*      */     {
/* 1915 */       newLink[newPrev] = newLink[newPrev] | 0xFFFFFFFFL; } 
/* 1916 */     this.n = newN;
/* 1917 */     this.mask = mask;
/* 1918 */     this.maxFill = HashCommon.maxFill(this.n, this.f);
/* 1919 */     this.key = newKey;
/* 1920 */     this.value = newValue;
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
/*      */   public Int2ShortLinkedOpenHashMap clone() {
/*      */     Int2ShortLinkedOpenHashMap c;
/*      */     try {
/* 1937 */       c = (Int2ShortLinkedOpenHashMap)super.clone();
/* 1938 */     } catch (CloneNotSupportedException cantHappen) {
/* 1939 */       throw new InternalError();
/*      */     } 
/* 1941 */     c.keys = null;
/* 1942 */     c.values = null;
/* 1943 */     c.entries = null;
/* 1944 */     c.containsNullKey = this.containsNullKey;
/* 1945 */     c.key = (int[])this.key.clone();
/* 1946 */     c.value = (short[])this.value.clone();
/* 1947 */     c.link = (long[])this.link.clone();
/* 1948 */     return c;
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
/* 1962 */     int h = 0;
/* 1963 */     for (int j = realSize(), i = 0, t = 0; j-- != 0; ) {
/* 1964 */       for (; this.key[i] == 0; i++);
/* 1965 */       t = this.key[i];
/* 1966 */       t ^= this.value[i];
/* 1967 */       h += t;
/* 1968 */       i++;
/*      */     } 
/*      */     
/* 1971 */     if (this.containsNullKey) h += this.value[this.n]; 
/* 1972 */     return h;
/*      */   }
/*      */   
/*      */   private void writeObject(ObjectOutputStream s) throws IOException {
/* 1976 */     int[] key = this.key;
/* 1977 */     short[] value = this.value;
/* 1978 */     EntryIterator i = new EntryIterator();
/* 1979 */     s.defaultWriteObject();
/* 1980 */     for (int j = this.size; j-- != 0; ) {
/* 1981 */       int e = i.nextEntry();
/* 1982 */       s.writeInt(key[e]);
/* 1983 */       s.writeShort(value[e]);
/*      */     } 
/*      */   }
/*      */   
/*      */   private void readObject(ObjectInputStream s) throws IOException, ClassNotFoundException {
/* 1988 */     s.defaultReadObject();
/* 1989 */     this.n = HashCommon.arraySize(this.size, this.f);
/* 1990 */     this.maxFill = HashCommon.maxFill(this.n, this.f);
/* 1991 */     this.mask = this.n - 1;
/* 1992 */     int[] key = this.key = new int[this.n + 1];
/* 1993 */     short[] value = this.value = new short[this.n + 1];
/* 1994 */     long[] link = this.link = new long[this.n + 1];
/* 1995 */     int prev = -1;
/* 1996 */     this.first = this.last = -1;
/*      */ 
/*      */     
/* 1999 */     for (int i = this.size; i-- != 0; ) {
/* 2000 */       int pos, k = s.readInt();
/* 2001 */       short v = s.readShort();
/* 2002 */       if (k == 0) {
/* 2003 */         pos = this.n;
/* 2004 */         this.containsNullKey = true;
/*      */       } else {
/* 2006 */         pos = HashCommon.mix(k) & this.mask;
/* 2007 */         for (; key[pos] != 0; pos = pos + 1 & this.mask);
/*      */       } 
/* 2009 */       key[pos] = k;
/* 2010 */       value[pos] = v;
/* 2011 */       if (this.first != -1) {
/* 2012 */         link[prev] = link[prev] ^ (link[prev] ^ pos & 0xFFFFFFFFL) & 0xFFFFFFFFL;
/* 2013 */         link[pos] = link[pos] ^ (link[pos] ^ (prev & 0xFFFFFFFFL) << 32L) & 0xFFFFFFFF00000000L;
/* 2014 */         prev = pos; continue;
/*      */       } 
/* 2016 */       prev = this.first = pos;
/*      */       
/* 2018 */       link[pos] = link[pos] | 0xFFFFFFFF00000000L;
/*      */     } 
/*      */     
/* 2021 */     this.last = prev;
/* 2022 */     if (prev != -1)
/*      */     {
/* 2024 */       link[prev] = link[prev] | 0xFFFFFFFFL;
/*      */     }
/*      */   }
/*      */   
/*      */   private void checkTable() {}
/*      */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\i\\unimi\dsi\fastutil\ints\Int2ShortLinkedOpenHashMap.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */