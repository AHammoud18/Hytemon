/*      */ package it.unimi.dsi.fastutil.ints;
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
/*      */ import it.unimi.dsi.fastutil.objects.AbstractObjectSortedSet;
/*      */ import it.unimi.dsi.fastutil.objects.ObjectBidirectionalIterator;
/*      */ import it.unimi.dsi.fastutil.objects.ObjectIterator;
/*      */ import it.unimi.dsi.fastutil.objects.ObjectListIterator;
/*      */ import it.unimi.dsi.fastutil.objects.ObjectSet;
/*      */ import it.unimi.dsi.fastutil.objects.ObjectSortedSet;
/*      */ import it.unimi.dsi.fastutil.objects.ObjectSpliterator;
/*      */ import it.unimi.dsi.fastutil.objects.ObjectSpliterators;
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
/*      */ import java.util.function.IntPredicate;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ public class Int2BooleanLinkedOpenHashMap
/*      */   extends AbstractInt2BooleanSortedMap
/*      */   implements Serializable, Cloneable, Hash
/*      */ {
/*      */   private static final long serialVersionUID = 0L;
/*      */   private static final boolean ASSERTS = false;
/*      */   protected transient int[] key;
/*      */   protected transient boolean[] value;
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
/*      */   protected transient Int2BooleanSortedMap.FastSortedEntrySet entries;
/*      */ 
/*      */   
/*      */   protected transient IntSortedSet keys;
/*      */ 
/*      */   
/*      */   protected transient BooleanCollection values;
/*      */ 
/*      */ 
/*      */   
/*      */   public Int2BooleanLinkedOpenHashMap(int expected, float f) {
/*  148 */     if (f <= 0.0F || f >= 1.0F) throw new IllegalArgumentException("Load factor must be greater than 0 and smaller than 1"); 
/*  149 */     if (expected < 0) throw new IllegalArgumentException("The expected number of elements must be nonnegative"); 
/*  150 */     this.f = f;
/*  151 */     this.minN = this.n = HashCommon.arraySize(expected, f);
/*  152 */     this.mask = this.n - 1;
/*  153 */     this.maxFill = HashCommon.maxFill(this.n, f);
/*  154 */     this.key = new int[this.n + 1];
/*  155 */     this.value = new boolean[this.n + 1];
/*  156 */     this.link = new long[this.n + 1];
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Int2BooleanLinkedOpenHashMap(int expected) {
/*  165 */     this(expected, 0.75F);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Int2BooleanLinkedOpenHashMap() {
/*  173 */     this(16, 0.75F);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Int2BooleanLinkedOpenHashMap(Map<? extends Integer, ? extends Boolean> m, float f) {
/*  183 */     this(m.size(), f);
/*  184 */     putAll(m);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Int2BooleanLinkedOpenHashMap(Map<? extends Integer, ? extends Boolean> m) {
/*  193 */     this(m, 0.75F);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Int2BooleanLinkedOpenHashMap(Int2BooleanMap m, float f) {
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
/*      */   public Int2BooleanLinkedOpenHashMap(Int2BooleanMap m) {
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
/*      */   public Int2BooleanLinkedOpenHashMap(int[] k, boolean[] v, float f) {
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
/*      */   public Int2BooleanLinkedOpenHashMap(int[] k, boolean[] v) {
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
/*      */   private boolean removeEntry(int pos) {
/*  264 */     boolean oldValue = this.value[pos];
/*  265 */     this.size--;
/*  266 */     fixPointers(pos);
/*  267 */     shiftKeys(pos);
/*  268 */     if (this.n > this.minN && this.size < this.maxFill / 4 && this.n > 16) rehash(this.n / 2); 
/*  269 */     return oldValue;
/*      */   }
/*      */   
/*      */   private boolean removeNullEntry() {
/*  273 */     this.containsNullKey = false;
/*  274 */     boolean oldValue = this.value[this.n];
/*  275 */     this.size--;
/*  276 */     fixPointers(this.n);
/*  277 */     if (this.n > this.minN && this.size < this.maxFill / 4 && this.n > 16) rehash(this.n / 2); 
/*  278 */     return oldValue;
/*      */   }
/*      */ 
/*      */   
/*      */   public void putAll(Map<? extends Integer, ? extends Boolean> m) {
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
/*      */   private void insert(int pos, int k, boolean v) {
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
/*      */   public boolean put(int k, boolean v) {
/*  323 */     int pos = find(k);
/*  324 */     if (pos < 0) {
/*  325 */       insert(-pos - 1, k, v);
/*  326 */       return this.defRetValue;
/*      */     } 
/*  328 */     boolean oldValue = this.value[pos];
/*  329 */     this.value[pos] = v;
/*  330 */     return oldValue;
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
/*  343 */     int[] key = this.key; while (true) {
/*      */       int curr, last;
/*  345 */       pos = (last = pos) + 1 & this.mask;
/*      */       while (true) {
/*  347 */         if ((curr = key[pos]) == 0) {
/*  348 */           key[last] = 0;
/*      */           return;
/*      */         } 
/*  351 */         int slot = HashCommon.mix(curr) & this.mask;
/*  352 */         if ((last <= pos) ? (last >= slot || slot > pos) : (last >= slot && slot > pos))
/*  353 */           break;  pos = pos + 1 & this.mask;
/*      */       } 
/*  355 */       key[last] = curr;
/*  356 */       this.value[last] = this.value[pos];
/*  357 */       fixPointers(pos, last);
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean remove(int k) {
/*  364 */     if (k == 0) {
/*  365 */       if (this.containsNullKey) return removeNullEntry(); 
/*  366 */       return this.defRetValue;
/*      */     } 
/*      */     
/*  369 */     int[] key = this.key;
/*      */     
/*      */     int curr, pos;
/*  372 */     if ((curr = key[pos = HashCommon.mix(k) & this.mask]) == 0) return this.defRetValue; 
/*  373 */     if (k == curr) return removeEntry(pos); 
/*      */     while (true) {
/*  375 */       if ((curr = key[pos = pos + 1 & this.mask]) == 0) return this.defRetValue; 
/*  376 */       if (k == curr) return removeEntry(pos); 
/*      */     } 
/*      */   }
/*      */   
/*      */   private boolean setValue(int pos, boolean v) {
/*  381 */     boolean oldValue = this.value[pos];
/*  382 */     this.value[pos] = v;
/*  383 */     return oldValue;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean removeFirstBoolean() {
/*  393 */     if (this.size == 0) throw new NoSuchElementException(); 
/*  394 */     int pos = this.first;
/*      */     
/*  396 */     if (this.size == 1) { this.first = this.last = -1; }
/*      */     else
/*  398 */     { this.first = (int)this.link[pos];
/*  399 */       if (0 <= this.first)
/*      */       {
/*  401 */         this.link[this.first] = this.link[this.first] | 0xFFFFFFFF00000000L;
/*      */       } }
/*      */     
/*  404 */     this.size--;
/*  405 */     boolean v = this.value[pos];
/*  406 */     if (pos == this.n)
/*  407 */     { this.containsNullKey = false; }
/*  408 */     else { shiftKeys(pos); }
/*  409 */      if (this.n > this.minN && this.size < this.maxFill / 4 && this.n > 16) rehash(this.n / 2); 
/*  410 */     return v;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean removeLastBoolean() {
/*  420 */     if (this.size == 0) throw new NoSuchElementException(); 
/*  421 */     int pos = this.last;
/*      */     
/*  423 */     if (this.size == 1) { this.first = this.last = -1; }
/*      */     else
/*  425 */     { this.last = (int)(this.link[pos] >>> 32L);
/*  426 */       if (0 <= this.last)
/*      */       {
/*  428 */         this.link[this.last] = this.link[this.last] | 0xFFFFFFFFL;
/*      */       } }
/*      */     
/*  431 */     this.size--;
/*  432 */     boolean v = this.value[pos];
/*  433 */     if (pos == this.n)
/*  434 */     { this.containsNullKey = false; }
/*  435 */     else { shiftKeys(pos); }
/*  436 */      if (this.n > this.minN && this.size < this.maxFill / 4 && this.n > 16) rehash(this.n / 2); 
/*  437 */     return v;
/*      */   }
/*      */   
/*      */   private void moveIndexToFirst(int i) {
/*  441 */     if (this.size == 1 || this.first == i)
/*  442 */       return;  if (this.last == i) {
/*  443 */       this.last = (int)(this.link[i] >>> 32L);
/*      */       
/*  445 */       this.link[this.last] = this.link[this.last] | 0xFFFFFFFFL;
/*      */     } else {
/*  447 */       long linki = this.link[i];
/*  448 */       int prev = (int)(linki >>> 32L);
/*  449 */       int next = (int)linki;
/*  450 */       this.link[prev] = this.link[prev] ^ (this.link[prev] ^ linki & 0xFFFFFFFFL) & 0xFFFFFFFFL;
/*  451 */       this.link[next] = this.link[next] ^ (this.link[next] ^ linki & 0xFFFFFFFF00000000L) & 0xFFFFFFFF00000000L;
/*      */     } 
/*  453 */     this.link[this.first] = this.link[this.first] ^ (this.link[this.first] ^ (i & 0xFFFFFFFFL) << 32L) & 0xFFFFFFFF00000000L;
/*  454 */     this.link[i] = 0xFFFFFFFF00000000L | this.first & 0xFFFFFFFFL;
/*  455 */     this.first = i;
/*      */   }
/*      */   
/*      */   private void moveIndexToLast(int i) {
/*  459 */     if (this.size == 1 || this.last == i)
/*  460 */       return;  if (this.first == i) {
/*  461 */       this.first = (int)this.link[i];
/*      */       
/*  463 */       this.link[this.first] = this.link[this.first] | 0xFFFFFFFF00000000L;
/*      */     } else {
/*  465 */       long linki = this.link[i];
/*  466 */       int prev = (int)(linki >>> 32L);
/*  467 */       int next = (int)linki;
/*  468 */       this.link[prev] = this.link[prev] ^ (this.link[prev] ^ linki & 0xFFFFFFFFL) & 0xFFFFFFFFL;
/*  469 */       this.link[next] = this.link[next] ^ (this.link[next] ^ linki & 0xFFFFFFFF00000000L) & 0xFFFFFFFF00000000L;
/*      */     } 
/*  471 */     this.link[this.last] = this.link[this.last] ^ (this.link[this.last] ^ i & 0xFFFFFFFFL) & 0xFFFFFFFFL;
/*  472 */     this.link[i] = (this.last & 0xFFFFFFFFL) << 32L | 0xFFFFFFFFL;
/*  473 */     this.last = i;
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
/*      */   public boolean getAndMoveToFirst(int k) {
/*  485 */     if (k == 0) {
/*  486 */       if (this.containsNullKey) {
/*  487 */         moveIndexToFirst(this.n);
/*  488 */         return this.value[this.n];
/*      */       } 
/*  490 */       return this.defRetValue;
/*      */     } 
/*      */     
/*  493 */     int[] key = this.key;
/*      */     
/*      */     int curr, pos;
/*  496 */     if ((curr = key[pos = HashCommon.mix(k) & this.mask]) == 0) return this.defRetValue; 
/*  497 */     if (k == curr) {
/*  498 */       moveIndexToFirst(pos);
/*  499 */       return this.value[pos];
/*      */     } 
/*      */     
/*      */     while (true) {
/*  503 */       if ((curr = key[pos = pos + 1 & this.mask]) == 0) return this.defRetValue; 
/*  504 */       if (k == curr) {
/*  505 */         moveIndexToFirst(pos);
/*  506 */         return this.value[pos];
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
/*      */   public boolean getAndMoveToLast(int k) {
/*  520 */     if (k == 0) {
/*  521 */       if (this.containsNullKey) {
/*  522 */         moveIndexToLast(this.n);
/*  523 */         return this.value[this.n];
/*      */       } 
/*  525 */       return this.defRetValue;
/*      */     } 
/*      */     
/*  528 */     int[] key = this.key;
/*      */     
/*      */     int curr, pos;
/*  531 */     if ((curr = key[pos = HashCommon.mix(k) & this.mask]) == 0) return this.defRetValue; 
/*  532 */     if (k == curr) {
/*  533 */       moveIndexToLast(pos);
/*  534 */       return this.value[pos];
/*      */     } 
/*      */     
/*      */     while (true) {
/*  538 */       if ((curr = key[pos = pos + 1 & this.mask]) == 0) return this.defRetValue; 
/*  539 */       if (k == curr) {
/*  540 */         moveIndexToLast(pos);
/*  541 */         return this.value[pos];
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
/*      */   public boolean putAndMoveToFirst(int k, boolean v) {
/*      */     int pos;
/*  557 */     if (k == 0) {
/*  558 */       if (this.containsNullKey) {
/*  559 */         moveIndexToFirst(this.n);
/*  560 */         return setValue(this.n, v);
/*      */       } 
/*  562 */       this.containsNullKey = true;
/*  563 */       pos = this.n;
/*      */     } else {
/*      */       
/*  566 */       int[] key = this.key;
/*      */       int curr;
/*  568 */       if ((curr = key[pos = HashCommon.mix(k) & this.mask]) != 0) {
/*  569 */         if (curr == k) {
/*  570 */           moveIndexToFirst(pos);
/*  571 */           return setValue(pos, v);
/*      */         } 
/*  573 */         while ((curr = key[pos = pos + 1 & this.mask]) != 0) { if (curr == k) {
/*  574 */             moveIndexToFirst(pos);
/*  575 */             return setValue(pos, v);
/*      */           }  }
/*      */       
/*      */       } 
/*  579 */     }  this.key[pos] = k;
/*  580 */     this.value[pos] = v;
/*  581 */     if (this.size == 0) {
/*  582 */       this.first = this.last = pos;
/*      */       
/*  584 */       this.link[pos] = -1L;
/*      */     } else {
/*  586 */       this.link[this.first] = this.link[this.first] ^ (this.link[this.first] ^ (pos & 0xFFFFFFFFL) << 32L) & 0xFFFFFFFF00000000L;
/*  587 */       this.link[pos] = 0xFFFFFFFF00000000L | this.first & 0xFFFFFFFFL;
/*  588 */       this.first = pos;
/*      */     } 
/*  590 */     if (this.size++ >= this.maxFill) rehash(HashCommon.arraySize(this.size, this.f));
/*      */     
/*  592 */     return this.defRetValue;
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
/*      */   public boolean putAndMoveToLast(int k, boolean v) {
/*      */     int pos;
/*  606 */     if (k == 0) {
/*  607 */       if (this.containsNullKey) {
/*  608 */         moveIndexToLast(this.n);
/*  609 */         return setValue(this.n, v);
/*      */       } 
/*  611 */       this.containsNullKey = true;
/*  612 */       pos = this.n;
/*      */     } else {
/*      */       
/*  615 */       int[] key = this.key;
/*      */       int curr;
/*  617 */       if ((curr = key[pos = HashCommon.mix(k) & this.mask]) != 0) {
/*  618 */         if (curr == k) {
/*  619 */           moveIndexToLast(pos);
/*  620 */           return setValue(pos, v);
/*      */         } 
/*  622 */         while ((curr = key[pos = pos + 1 & this.mask]) != 0) { if (curr == k) {
/*  623 */             moveIndexToLast(pos);
/*  624 */             return setValue(pos, v);
/*      */           }  }
/*      */       
/*      */       } 
/*  628 */     }  this.key[pos] = k;
/*  629 */     this.value[pos] = v;
/*  630 */     if (this.size == 0) {
/*  631 */       this.first = this.last = pos;
/*      */       
/*  633 */       this.link[pos] = -1L;
/*      */     } else {
/*  635 */       this.link[this.last] = this.link[this.last] ^ (this.link[this.last] ^ pos & 0xFFFFFFFFL) & 0xFFFFFFFFL;
/*  636 */       this.link[pos] = (this.last & 0xFFFFFFFFL) << 32L | 0xFFFFFFFFL;
/*  637 */       this.last = pos;
/*      */     } 
/*  639 */     if (this.size++ >= this.maxFill) rehash(HashCommon.arraySize(this.size, this.f));
/*      */     
/*  641 */     return this.defRetValue;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean get(int k) {
/*  647 */     if (k == 0) return this.containsNullKey ? this.value[this.n] : this.defRetValue;
/*      */     
/*  649 */     int[] key = this.key;
/*      */     
/*      */     int curr, pos;
/*  652 */     if ((curr = key[pos = HashCommon.mix(k) & this.mask]) == 0) return this.defRetValue; 
/*  653 */     if (k == curr) return this.value[pos];
/*      */     
/*      */     while (true) {
/*  656 */       if ((curr = key[pos = pos + 1 & this.mask]) == 0) return this.defRetValue; 
/*  657 */       if (k == curr) return this.value[pos];
/*      */     
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   public boolean containsKey(int k) {
/*  664 */     if (k == 0) return this.containsNullKey;
/*      */     
/*  666 */     int[] key = this.key;
/*      */     
/*      */     int curr, pos;
/*  669 */     if ((curr = key[pos = HashCommon.mix(k) & this.mask]) == 0) return false; 
/*  670 */     if (k == curr) return true;
/*      */     
/*      */     while (true) {
/*  673 */       if ((curr = key[pos = pos + 1 & this.mask]) == 0) return false; 
/*  674 */       if (k == curr) return true;
/*      */     
/*      */     } 
/*      */   }
/*      */   
/*      */   public boolean containsValue(boolean v) {
/*  680 */     boolean[] value = this.value;
/*  681 */     int[] key = this.key;
/*  682 */     if (this.containsNullKey && value[this.n] == v) return true; 
/*  683 */     for (int i = this.n; i-- != 0;) { if (key[i] != 0 && value[i] == v) return true;  }
/*  684 */      return false;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean getOrDefault(int k, boolean defaultValue) {
/*  691 */     if (k == 0) return this.containsNullKey ? this.value[this.n] : defaultValue;
/*      */     
/*  693 */     int[] key = this.key;
/*      */     
/*      */     int curr, pos;
/*  696 */     if ((curr = key[pos = HashCommon.mix(k) & this.mask]) == 0) return defaultValue; 
/*  697 */     if (k == curr) return this.value[pos];
/*      */     
/*      */     while (true) {
/*  700 */       if ((curr = key[pos = pos + 1 & this.mask]) == 0) return defaultValue; 
/*  701 */       if (k == curr) return this.value[pos];
/*      */     
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   public boolean putIfAbsent(int k, boolean v) {
/*  708 */     int pos = find(k);
/*  709 */     if (pos >= 0) return this.value[pos]; 
/*  710 */     insert(-pos - 1, k, v);
/*  711 */     return this.defRetValue;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean remove(int k, boolean v) {
/*  718 */     if (k == 0) {
/*  719 */       if (this.containsNullKey && v == this.value[this.n]) {
/*  720 */         removeNullEntry();
/*  721 */         return true;
/*      */       } 
/*  723 */       return false;
/*      */     } 
/*      */     
/*  726 */     int[] key = this.key;
/*      */     
/*      */     int curr, pos;
/*  729 */     if ((curr = key[pos = HashCommon.mix(k) & this.mask]) == 0) return false; 
/*  730 */     if (k == curr && v == this.value[pos]) {
/*  731 */       removeEntry(pos);
/*  732 */       return true;
/*      */     } 
/*      */     while (true) {
/*  735 */       if ((curr = key[pos = pos + 1 & this.mask]) == 0) return false; 
/*  736 */       if (k == curr && v == this.value[pos]) {
/*  737 */         removeEntry(pos);
/*  738 */         return true;
/*      */       } 
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean replace(int k, boolean oldValue, boolean v) {
/*  746 */     int pos = find(k);
/*  747 */     if (pos < 0 || oldValue != this.value[pos]) return false; 
/*  748 */     this.value[pos] = v;
/*  749 */     return true;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean replace(int k, boolean v) {
/*  755 */     int pos = find(k);
/*  756 */     if (pos < 0) return this.defRetValue; 
/*  757 */     boolean oldValue = this.value[pos];
/*  758 */     this.value[pos] = v;
/*  759 */     return oldValue;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean computeIfAbsent(int k, IntPredicate mappingFunction) {
/*  765 */     Objects.requireNonNull(mappingFunction);
/*  766 */     int pos = find(k);
/*  767 */     if (pos >= 0) return this.value[pos]; 
/*  768 */     boolean newValue = mappingFunction.test(k);
/*  769 */     insert(-pos - 1, k, newValue);
/*  770 */     return newValue;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean computeIfAbsent(int key, Int2BooleanFunction mappingFunction) {
/*  776 */     Objects.requireNonNull(mappingFunction);
/*  777 */     int pos = find(key);
/*  778 */     if (pos >= 0) return this.value[pos]; 
/*  779 */     if (!mappingFunction.containsKey(key)) return this.defRetValue; 
/*  780 */     boolean newValue = mappingFunction.get(key);
/*  781 */     insert(-pos - 1, key, newValue);
/*  782 */     return newValue;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean computeIfAbsentNullable(int k, IntFunction<? extends Boolean> mappingFunction) {
/*  788 */     Objects.requireNonNull(mappingFunction);
/*  789 */     int pos = find(k);
/*  790 */     if (pos >= 0) return this.value[pos]; 
/*  791 */     Boolean newValue = mappingFunction.apply(k);
/*  792 */     if (newValue == null) return this.defRetValue; 
/*  793 */     boolean v = newValue.booleanValue();
/*  794 */     insert(-pos - 1, k, v);
/*  795 */     return v;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean computeIfPresent(int k, BiFunction<? super Integer, ? super Boolean, ? extends Boolean> remappingFunction) {
/*  801 */     Objects.requireNonNull(remappingFunction);
/*  802 */     int pos = find(k);
/*  803 */     if (pos < 0) return this.defRetValue; 
/*  804 */     Boolean newValue = remappingFunction.apply(Integer.valueOf(k), Boolean.valueOf(this.value[pos]));
/*  805 */     if (newValue == null) {
/*  806 */       if (k == 0) { removeNullEntry(); }
/*  807 */       else { removeEntry(pos); }
/*  808 */        return this.defRetValue;
/*      */     } 
/*  810 */     this.value[pos] = newValue.booleanValue(); return newValue.booleanValue();
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean compute(int k, BiFunction<? super Integer, ? super Boolean, ? extends Boolean> remappingFunction) {
/*  816 */     Objects.requireNonNull(remappingFunction);
/*  817 */     int pos = find(k);
/*  818 */     Boolean newValue = remappingFunction.apply(Integer.valueOf(k), (pos >= 0) ? Boolean.valueOf(this.value[pos]) : null);
/*  819 */     if (newValue == null) {
/*  820 */       if (pos >= 0)
/*  821 */         if (k == 0) { removeNullEntry(); }
/*  822 */         else { removeEntry(pos); }
/*      */          
/*  824 */       return this.defRetValue;
/*      */     } 
/*  826 */     boolean newVal = newValue.booleanValue();
/*  827 */     if (pos < 0) {
/*  828 */       insert(-pos - 1, k, newVal);
/*  829 */       return newVal;
/*      */     } 
/*  831 */     this.value[pos] = newVal; return newVal;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean merge(int k, boolean v, BiFunction<? super Boolean, ? super Boolean, ? extends Boolean> remappingFunction) {
/*  837 */     Objects.requireNonNull(remappingFunction);
/*      */     
/*  839 */     int pos = find(k);
/*  840 */     if (pos < 0) {
/*  841 */       if (pos < 0) { insert(-pos - 1, k, v); }
/*  842 */       else { this.value[pos] = v; }
/*  843 */        return v;
/*      */     } 
/*  845 */     Boolean newValue = remappingFunction.apply(Boolean.valueOf(this.value[pos]), Boolean.valueOf(v));
/*  846 */     if (newValue == null) {
/*  847 */       if (k == 0) { removeNullEntry(); }
/*  848 */       else { removeEntry(pos); }
/*  849 */        return this.defRetValue;
/*      */     } 
/*  851 */     this.value[pos] = newValue.booleanValue(); return newValue.booleanValue();
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
/*  862 */     if (this.size == 0)
/*  863 */       return;  this.size = 0;
/*  864 */     this.containsNullKey = false;
/*  865 */     Arrays.fill(this.key, 0);
/*  866 */     this.first = this.last = -1;
/*      */   }
/*      */ 
/*      */   
/*      */   public int size() {
/*  871 */     return this.size;
/*      */   }
/*      */ 
/*      */   
/*      */   public boolean isEmpty() {
/*  876 */     return (this.size == 0);
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   final class MapEntry
/*      */     implements Int2BooleanMap.Entry, Map.Entry<Integer, Boolean>, IntBooleanPair
/*      */   {
/*      */     int index;
/*      */ 
/*      */ 
/*      */     
/*      */     MapEntry(int index) {
/*  889 */       this.index = index;
/*      */     }
/*      */ 
/*      */     
/*      */     MapEntry() {}
/*      */ 
/*      */     
/*      */     public int getIntKey() {
/*  897 */       return Int2BooleanLinkedOpenHashMap.this.key[this.index];
/*      */     }
/*      */ 
/*      */     
/*      */     public int leftInt() {
/*  902 */       return Int2BooleanLinkedOpenHashMap.this.key[this.index];
/*      */     }
/*      */ 
/*      */     
/*      */     public boolean getBooleanValue() {
/*  907 */       return Int2BooleanLinkedOpenHashMap.this.value[this.index];
/*      */     }
/*      */ 
/*      */     
/*      */     public boolean rightBoolean() {
/*  912 */       return Int2BooleanLinkedOpenHashMap.this.value[this.index];
/*      */     }
/*      */ 
/*      */     
/*      */     public boolean setValue(boolean v) {
/*  917 */       boolean oldValue = Int2BooleanLinkedOpenHashMap.this.value[this.index];
/*  918 */       Int2BooleanLinkedOpenHashMap.this.value[this.index] = v;
/*  919 */       return oldValue;
/*      */     }
/*      */ 
/*      */     
/*      */     public IntBooleanPair right(boolean v) {
/*  924 */       Int2BooleanLinkedOpenHashMap.this.value[this.index] = v;
/*  925 */       return this;
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
/*  936 */       return Integer.valueOf(Int2BooleanLinkedOpenHashMap.this.key[this.index]);
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
/*  947 */       return Boolean.valueOf(Int2BooleanLinkedOpenHashMap.this.value[this.index]);
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
/*  958 */       return Boolean.valueOf(setValue(v.booleanValue()));
/*      */     }
/*      */ 
/*      */ 
/*      */     
/*      */     public boolean equals(Object o) {
/*  964 */       if (!(o instanceof Map.Entry)) return false; 
/*  965 */       Map.Entry<Integer, Boolean> e = (Map.Entry<Integer, Boolean>)o;
/*  966 */       return (Int2BooleanLinkedOpenHashMap.this.key[this.index] == ((Integer)e.getKey()).intValue() && Int2BooleanLinkedOpenHashMap.this.value[this.index] == ((Boolean)e.getValue()).booleanValue());
/*      */     }
/*      */ 
/*      */     
/*      */     public int hashCode() {
/*  971 */       return Int2BooleanLinkedOpenHashMap.this.key[this.index] ^ (Int2BooleanLinkedOpenHashMap.this.value[this.index] ? 1231 : 1237);
/*      */     }
/*      */ 
/*      */     
/*      */     public String toString() {
/*  976 */       return Int2BooleanLinkedOpenHashMap.this.key[this.index] + "=>" + Int2BooleanLinkedOpenHashMap.this.value[this.index];
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
/*  987 */     if (this.size == 0) {
/*  988 */       this.first = this.last = -1;
/*      */       return;
/*      */     } 
/*  991 */     if (this.first == i) {
/*  992 */       this.first = (int)this.link[i];
/*  993 */       if (0 <= this.first)
/*      */       {
/*  995 */         this.link[this.first] = this.link[this.first] | 0xFFFFFFFF00000000L;
/*      */       }
/*      */       return;
/*      */     } 
/*  999 */     if (this.last == i) {
/* 1000 */       this.last = (int)(this.link[i] >>> 32L);
/* 1001 */       if (0 <= this.last)
/*      */       {
/* 1003 */         this.link[this.last] = this.link[this.last] | 0xFFFFFFFFL;
/*      */       }
/*      */       return;
/*      */     } 
/* 1007 */     long linki = this.link[i];
/* 1008 */     int prev = (int)(linki >>> 32L);
/* 1009 */     int next = (int)linki;
/* 1010 */     this.link[prev] = this.link[prev] ^ (this.link[prev] ^ linki & 0xFFFFFFFFL) & 0xFFFFFFFFL;
/* 1011 */     this.link[next] = this.link[next] ^ (this.link[next] ^ linki & 0xFFFFFFFF00000000L) & 0xFFFFFFFF00000000L;
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
/* 1023 */     if (this.size == 1) {
/* 1024 */       this.first = this.last = d;
/*      */       
/* 1026 */       this.link[d] = -1L;
/*      */       return;
/*      */     } 
/* 1029 */     if (this.first == s) {
/* 1030 */       this.first = d;
/* 1031 */       this.link[(int)this.link[s]] = this.link[(int)this.link[s]] ^ (this.link[(int)this.link[s]] ^ (d & 0xFFFFFFFFL) << 32L) & 0xFFFFFFFF00000000L;
/* 1032 */       this.link[d] = this.link[s];
/*      */       return;
/*      */     } 
/* 1035 */     if (this.last == s) {
/* 1036 */       this.last = d;
/* 1037 */       this.link[(int)(this.link[s] >>> 32L)] = this.link[(int)(this.link[s] >>> 32L)] ^ (this.link[(int)(this.link[s] >>> 32L)] ^ d & 0xFFFFFFFFL) & 0xFFFFFFFFL;
/* 1038 */       this.link[d] = this.link[s];
/*      */       return;
/*      */     } 
/* 1041 */     long links = this.link[s];
/* 1042 */     int prev = (int)(links >>> 32L);
/* 1043 */     int next = (int)links;
/* 1044 */     this.link[prev] = this.link[prev] ^ (this.link[prev] ^ d & 0xFFFFFFFFL) & 0xFFFFFFFFL;
/* 1045 */     this.link[next] = this.link[next] ^ (this.link[next] ^ (d & 0xFFFFFFFFL) << 32L) & 0xFFFFFFFF00000000L;
/* 1046 */     this.link[d] = links;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public int firstIntKey() {
/* 1056 */     if (this.size == 0) throw new NoSuchElementException(); 
/* 1057 */     return this.key[this.first];
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public int lastIntKey() {
/* 1067 */     if (this.size == 0) throw new NoSuchElementException(); 
/* 1068 */     return this.key[this.last];
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Int2BooleanSortedMap tailMap(int from) {
/* 1078 */     throw new UnsupportedOperationException();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Int2BooleanSortedMap headMap(int to) {
/* 1088 */     throw new UnsupportedOperationException();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Int2BooleanSortedMap subMap(int from, int to) {
/* 1098 */     throw new UnsupportedOperationException();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public IntComparator comparator() {
/* 1108 */     return null;
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
/* 1123 */     int prev = -1;
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 1128 */     int next = -1;
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 1133 */     int curr = -1;
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 1138 */     int index = -1;
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     protected MapIterator() {
/* 1144 */       this.next = Int2BooleanLinkedOpenHashMap.this.first;
/* 1145 */       this.index = 0;
/*      */     }
/*      */     
/*      */     private MapIterator(int from) {
/* 1149 */       if (from == 0) {
/* 1150 */         if (Int2BooleanLinkedOpenHashMap.this.containsNullKey) {
/* 1151 */           this.next = (int)Int2BooleanLinkedOpenHashMap.this.link[Int2BooleanLinkedOpenHashMap.this.n];
/* 1152 */           this.prev = Int2BooleanLinkedOpenHashMap.this.n; return;
/*      */         } 
/* 1154 */         throw new NoSuchElementException("The key " + from + " does not belong to this map.");
/*      */       } 
/* 1156 */       if (Int2BooleanLinkedOpenHashMap.this.key[Int2BooleanLinkedOpenHashMap.this.last] == from) {
/* 1157 */         this.prev = Int2BooleanLinkedOpenHashMap.this.last;
/* 1158 */         this.index = Int2BooleanLinkedOpenHashMap.this.size;
/*      */         
/*      */         return;
/*      */       } 
/* 1162 */       int pos = HashCommon.mix(from) & Int2BooleanLinkedOpenHashMap.this.mask;
/*      */       
/* 1164 */       while (Int2BooleanLinkedOpenHashMap.this.key[pos] != 0) {
/* 1165 */         if (Int2BooleanLinkedOpenHashMap.this.key[pos] == from) {
/*      */           
/* 1167 */           this.next = (int)Int2BooleanLinkedOpenHashMap.this.link[pos];
/* 1168 */           this.prev = pos;
/*      */           return;
/*      */         } 
/* 1171 */         pos = pos + 1 & Int2BooleanLinkedOpenHashMap.this.mask;
/*      */       } 
/* 1173 */       throw new NoSuchElementException("The key " + from + " does not belong to this map.");
/*      */     }
/*      */     
/*      */     public boolean hasNext() {
/* 1177 */       return (this.next != -1);
/*      */     }
/*      */     
/*      */     public boolean hasPrevious() {
/* 1181 */       return (this.prev != -1);
/*      */     }
/*      */     
/*      */     private final void ensureIndexKnown() {
/* 1185 */       if (this.index >= 0)
/* 1186 */         return;  if (this.prev == -1) {
/* 1187 */         this.index = 0;
/*      */         return;
/*      */       } 
/* 1190 */       if (this.next == -1) {
/* 1191 */         this.index = Int2BooleanLinkedOpenHashMap.this.size;
/*      */         return;
/*      */       } 
/* 1194 */       int pos = Int2BooleanLinkedOpenHashMap.this.first;
/* 1195 */       this.index = 1;
/* 1196 */       while (pos != this.prev) {
/* 1197 */         pos = (int)Int2BooleanLinkedOpenHashMap.this.link[pos];
/* 1198 */         this.index++;
/*      */       } 
/*      */     }
/*      */     
/*      */     public int nextIndex() {
/* 1203 */       ensureIndexKnown();
/* 1204 */       return this.index;
/*      */     }
/*      */     
/*      */     public int previousIndex() {
/* 1208 */       ensureIndexKnown();
/* 1209 */       return this.index - 1;
/*      */     }
/*      */     
/*      */     public int nextEntry() {
/* 1213 */       if (!hasNext()) throw new NoSuchElementException(); 
/* 1214 */       this.curr = this.next;
/* 1215 */       this.next = (int)Int2BooleanLinkedOpenHashMap.this.link[this.curr];
/* 1216 */       this.prev = this.curr;
/* 1217 */       if (this.index >= 0) this.index++; 
/* 1218 */       return this.curr;
/*      */     }
/*      */     
/*      */     public int previousEntry() {
/* 1222 */       if (!hasPrevious()) throw new NoSuchElementException(); 
/* 1223 */       this.curr = this.prev;
/* 1224 */       this.prev = (int)(Int2BooleanLinkedOpenHashMap.this.link[this.curr] >>> 32L);
/* 1225 */       this.next = this.curr;
/* 1226 */       if (this.index >= 0) this.index--; 
/* 1227 */       return this.curr;
/*      */     }
/*      */     
/*      */     public void forEachRemaining(ConsumerType action) {
/* 1231 */       while (hasNext()) {
/* 1232 */         this.curr = this.next;
/* 1233 */         this.next = (int)Int2BooleanLinkedOpenHashMap.this.link[this.curr];
/* 1234 */         this.prev = this.curr;
/* 1235 */         if (this.index >= 0) this.index++; 
/* 1236 */         acceptOnIndex(action, this.curr);
/*      */       } 
/*      */     }
/*      */     
/*      */     public void remove() {
/* 1241 */       ensureIndexKnown();
/* 1242 */       if (this.curr == -1) throw new IllegalStateException(); 
/* 1243 */       if (this.curr == this.prev)
/*      */       
/*      */       { 
/* 1246 */         this.index--;
/* 1247 */         this.prev = (int)(Int2BooleanLinkedOpenHashMap.this.link[this.curr] >>> 32L); }
/* 1248 */       else { this.next = (int)Int2BooleanLinkedOpenHashMap.this.link[this.curr]; }
/* 1249 */        Int2BooleanLinkedOpenHashMap.this.size--;
/*      */ 
/*      */       
/* 1252 */       if (this.prev == -1) { Int2BooleanLinkedOpenHashMap.this.first = this.next; }
/* 1253 */       else { Int2BooleanLinkedOpenHashMap.this.link[this.prev] = Int2BooleanLinkedOpenHashMap.this.link[this.prev] ^ (Int2BooleanLinkedOpenHashMap.this.link[this.prev] ^ this.next & 0xFFFFFFFFL) & 0xFFFFFFFFL; }
/* 1254 */        if (this.next == -1) { Int2BooleanLinkedOpenHashMap.this.last = this.prev; }
/* 1255 */       else { Int2BooleanLinkedOpenHashMap.this.link[this.next] = Int2BooleanLinkedOpenHashMap.this.link[this.next] ^ (Int2BooleanLinkedOpenHashMap.this.link[this.next] ^ (this.prev & 0xFFFFFFFFL) << 32L) & 0xFFFFFFFF00000000L; }
/* 1256 */        int pos = this.curr;
/* 1257 */       this.curr = -1;
/* 1258 */       if (pos == Int2BooleanLinkedOpenHashMap.this.n) {
/* 1259 */         Int2BooleanLinkedOpenHashMap.this.containsNullKey = false;
/*      */       } else {
/*      */         
/* 1262 */         int[] key = Int2BooleanLinkedOpenHashMap.this.key;
/*      */         while (true) {
/*      */           int curr, last;
/* 1265 */           pos = (last = pos) + 1 & Int2BooleanLinkedOpenHashMap.this.mask;
/*      */           while (true) {
/* 1267 */             if ((curr = key[pos]) == 0) {
/* 1268 */               key[last] = 0;
/*      */               return;
/*      */             } 
/* 1271 */             int slot = HashCommon.mix(curr) & Int2BooleanLinkedOpenHashMap.this.mask;
/* 1272 */             if ((last <= pos) ? (last >= slot || slot > pos) : (last >= slot && slot > pos))
/* 1273 */               break;  pos = pos + 1 & Int2BooleanLinkedOpenHashMap.this.mask;
/*      */           } 
/* 1275 */           key[last] = curr;
/* 1276 */           Int2BooleanLinkedOpenHashMap.this.value[last] = Int2BooleanLinkedOpenHashMap.this.value[pos];
/* 1277 */           if (this.next == pos) this.next = last; 
/* 1278 */           if (this.prev == pos) this.prev = last; 
/* 1279 */           Int2BooleanLinkedOpenHashMap.this.fixPointers(pos, last);
/*      */         } 
/*      */       } 
/*      */     }
/*      */     
/*      */     public int skip(int n) {
/* 1285 */       int i = n;
/* 1286 */       for (; i-- != 0 && hasNext(); nextEntry());
/* 1287 */       return n - i - 1;
/*      */     }
/*      */     
/*      */     public int back(int n) {
/* 1291 */       int i = n;
/* 1292 */       for (; i-- != 0 && hasPrevious(); previousEntry());
/* 1293 */       return n - i - 1;
/*      */     }
/*      */     
/*      */     public void set(Int2BooleanMap.Entry ok) {
/* 1297 */       throw new UnsupportedOperationException();
/*      */     }
/*      */     
/*      */     public void add(Int2BooleanMap.Entry ok) {
/* 1301 */       throw new UnsupportedOperationException();
/*      */     }
/*      */     
/*      */     abstract void acceptOnIndex(ConsumerType param1ConsumerType, int param1Int); }
/*      */   
/*      */   private final class EntryIterator extends MapIterator<Consumer<? super Int2BooleanMap.Entry>> implements ObjectListIterator<Int2BooleanMap.Entry> {
/*      */     private Int2BooleanLinkedOpenHashMap.MapEntry entry;
/*      */     
/*      */     public EntryIterator() {}
/*      */     
/*      */     public EntryIterator(int from) {
/* 1312 */       super(from);
/*      */     }
/*      */ 
/*      */ 
/*      */     
/*      */     final void acceptOnIndex(Consumer<? super Int2BooleanMap.Entry> action, int index) {
/* 1318 */       action.accept(new Int2BooleanLinkedOpenHashMap.MapEntry(index));
/*      */     }
/*      */ 
/*      */     
/*      */     public Int2BooleanLinkedOpenHashMap.MapEntry next() {
/* 1323 */       return this.entry = new Int2BooleanLinkedOpenHashMap.MapEntry(nextEntry());
/*      */     }
/*      */ 
/*      */     
/*      */     public Int2BooleanLinkedOpenHashMap.MapEntry previous() {
/* 1328 */       return this.entry = new Int2BooleanLinkedOpenHashMap.MapEntry(previousEntry());
/*      */     }
/*      */ 
/*      */     
/*      */     public void remove() {
/* 1333 */       super.remove();
/* 1334 */       this.entry.index = -1;
/*      */     }
/*      */   }
/*      */   
/*      */   private final class FastEntryIterator extends MapIterator<Consumer<? super Int2BooleanMap.Entry>> implements ObjectListIterator<Int2BooleanMap.Entry> {
/* 1339 */     final Int2BooleanLinkedOpenHashMap.MapEntry entry = new Int2BooleanLinkedOpenHashMap.MapEntry();
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public FastEntryIterator(int from) {
/* 1345 */       super(from);
/*      */     }
/*      */ 
/*      */ 
/*      */     
/*      */     final void acceptOnIndex(Consumer<? super Int2BooleanMap.Entry> action, int index) {
/* 1351 */       this.entry.index = index;
/* 1352 */       action.accept(this.entry);
/*      */     }
/*      */ 
/*      */     
/*      */     public Int2BooleanLinkedOpenHashMap.MapEntry next() {
/* 1357 */       this.entry.index = nextEntry();
/* 1358 */       return this.entry;
/*      */     }
/*      */ 
/*      */     
/*      */     public Int2BooleanLinkedOpenHashMap.MapEntry previous() {
/* 1363 */       this.entry.index = previousEntry();
/* 1364 */       return this.entry;
/*      */     }
/*      */     
/*      */     public FastEntryIterator() {} }
/*      */   
/*      */   private final class MapEntrySet extends AbstractObjectSortedSet<Int2BooleanMap.Entry> implements Int2BooleanSortedMap.FastSortedEntrySet {
/*      */     private static final int SPLITERATOR_CHARACTERISTICS = 81;
/*      */     
/*      */     public ObjectBidirectionalIterator<Int2BooleanMap.Entry> iterator() {
/* 1373 */       return (ObjectBidirectionalIterator<Int2BooleanMap.Entry>)new Int2BooleanLinkedOpenHashMap.EntryIterator();
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
/*      */     public ObjectSpliterator<Int2BooleanMap.Entry> spliterator() {
/* 1391 */       return ObjectSpliterators.asSpliterator((ObjectIterator)iterator(), Size64.sizeOf(Int2BooleanLinkedOpenHashMap.this), 81);
/*      */     }
/*      */ 
/*      */     
/*      */     public Comparator<? super Int2BooleanMap.Entry> comparator() {
/* 1396 */       return null;
/*      */     }
/*      */ 
/*      */     
/*      */     public ObjectSortedSet<Int2BooleanMap.Entry> subSet(Int2BooleanMap.Entry fromElement, Int2BooleanMap.Entry toElement) {
/* 1401 */       throw new UnsupportedOperationException();
/*      */     }
/*      */ 
/*      */     
/*      */     public ObjectSortedSet<Int2BooleanMap.Entry> headSet(Int2BooleanMap.Entry toElement) {
/* 1406 */       throw new UnsupportedOperationException();
/*      */     }
/*      */ 
/*      */     
/*      */     public ObjectSortedSet<Int2BooleanMap.Entry> tailSet(Int2BooleanMap.Entry fromElement) {
/* 1411 */       throw new UnsupportedOperationException();
/*      */     }
/*      */ 
/*      */     
/*      */     public Int2BooleanMap.Entry first() {
/* 1416 */       if (Int2BooleanLinkedOpenHashMap.this.size == 0) throw new NoSuchElementException(); 
/* 1417 */       return new Int2BooleanLinkedOpenHashMap.MapEntry(Int2BooleanLinkedOpenHashMap.this.first);
/*      */     }
/*      */ 
/*      */     
/*      */     public Int2BooleanMap.Entry last() {
/* 1422 */       if (Int2BooleanLinkedOpenHashMap.this.size == 0) throw new NoSuchElementException(); 
/* 1423 */       return new Int2BooleanLinkedOpenHashMap.MapEntry(Int2BooleanLinkedOpenHashMap.this.last);
/*      */     }
/*      */ 
/*      */ 
/*      */     
/*      */     public boolean contains(Object o) {
/* 1429 */       if (!(o instanceof Map.Entry)) return false; 
/* 1430 */       Map.Entry<?, ?> e = (Map.Entry<?, ?>)o;
/* 1431 */       if (e.getKey() == null || !(e.getKey() instanceof Integer)) return false; 
/* 1432 */       if (e.getValue() == null || !(e.getValue() instanceof Boolean)) return false; 
/* 1433 */       int k = ((Integer)e.getKey()).intValue();
/* 1434 */       boolean v = ((Boolean)e.getValue()).booleanValue();
/* 1435 */       if (k == 0) return (Int2BooleanLinkedOpenHashMap.this.containsNullKey && Int2BooleanLinkedOpenHashMap.this.value[Int2BooleanLinkedOpenHashMap.this.n] == v);
/*      */       
/* 1437 */       int[] key = Int2BooleanLinkedOpenHashMap.this.key;
/*      */       
/*      */       int curr, pos;
/* 1440 */       if ((curr = key[pos = HashCommon.mix(k) & Int2BooleanLinkedOpenHashMap.this.mask]) == 0) return false; 
/* 1441 */       if (k == curr) return (Int2BooleanLinkedOpenHashMap.this.value[pos] == v);
/*      */       
/*      */       while (true) {
/* 1444 */         if ((curr = key[pos = pos + 1 & Int2BooleanLinkedOpenHashMap.this.mask]) == 0) return false; 
/* 1445 */         if (k == curr) return (Int2BooleanLinkedOpenHashMap.this.value[pos] == v);
/*      */       
/*      */       } 
/*      */     }
/*      */ 
/*      */     
/*      */     public boolean remove(Object o) {
/* 1452 */       if (!(o instanceof Map.Entry)) return false; 
/* 1453 */       Map.Entry<?, ?> e = (Map.Entry<?, ?>)o;
/* 1454 */       if (e.getKey() == null || !(e.getKey() instanceof Integer)) return false; 
/* 1455 */       if (e.getValue() == null || !(e.getValue() instanceof Boolean)) return false; 
/* 1456 */       int k = ((Integer)e.getKey()).intValue();
/* 1457 */       boolean v = ((Boolean)e.getValue()).booleanValue();
/* 1458 */       if (k == 0) {
/* 1459 */         if (Int2BooleanLinkedOpenHashMap.this.containsNullKey && Int2BooleanLinkedOpenHashMap.this.value[Int2BooleanLinkedOpenHashMap.this.n] == v) {
/* 1460 */           Int2BooleanLinkedOpenHashMap.this.removeNullEntry();
/* 1461 */           return true;
/*      */         } 
/* 1463 */         return false;
/*      */       } 
/*      */       
/* 1466 */       int[] key = Int2BooleanLinkedOpenHashMap.this.key;
/*      */       
/*      */       int curr, pos;
/* 1469 */       if ((curr = key[pos = HashCommon.mix(k) & Int2BooleanLinkedOpenHashMap.this.mask]) == 0) return false; 
/* 1470 */       if (curr == k) {
/* 1471 */         if (Int2BooleanLinkedOpenHashMap.this.value[pos] == v) {
/* 1472 */           Int2BooleanLinkedOpenHashMap.this.removeEntry(pos);
/* 1473 */           return true;
/*      */         } 
/* 1475 */         return false;
/*      */       } 
/*      */       while (true) {
/* 1478 */         if ((curr = key[pos = pos + 1 & Int2BooleanLinkedOpenHashMap.this.mask]) == 0) return false; 
/* 1479 */         if (curr == k && 
/* 1480 */           Int2BooleanLinkedOpenHashMap.this.value[pos] == v) {
/* 1481 */           Int2BooleanLinkedOpenHashMap.this.removeEntry(pos);
/* 1482 */           return true;
/*      */         } 
/*      */       } 
/*      */     }
/*      */ 
/*      */ 
/*      */     
/*      */     public int size() {
/* 1490 */       return Int2BooleanLinkedOpenHashMap.this.size;
/*      */     }
/*      */ 
/*      */     
/*      */     public void clear() {
/* 1495 */       Int2BooleanLinkedOpenHashMap.this.clear();
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
/*      */     public ObjectListIterator<Int2BooleanMap.Entry> iterator(Int2BooleanMap.Entry from) {
/* 1508 */       return new Int2BooleanLinkedOpenHashMap.EntryIterator(from.getIntKey());
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public ObjectListIterator<Int2BooleanMap.Entry> fastIterator() {
/* 1519 */       return new Int2BooleanLinkedOpenHashMap.FastEntryIterator();
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
/*      */     public ObjectListIterator<Int2BooleanMap.Entry> fastIterator(Int2BooleanMap.Entry from) {
/* 1532 */       return new Int2BooleanLinkedOpenHashMap.FastEntryIterator(from.getIntKey());
/*      */     }
/*      */ 
/*      */ 
/*      */     
/*      */     public void forEach(Consumer<? super Int2BooleanMap.Entry> consumer) {
/* 1538 */       for (int i = Int2BooleanLinkedOpenHashMap.this.size, next = Int2BooleanLinkedOpenHashMap.this.first; i-- != 0; ) {
/* 1539 */         int curr = next;
/* 1540 */         next = (int)Int2BooleanLinkedOpenHashMap.this.link[curr];
/* 1541 */         consumer.accept(new Int2BooleanLinkedOpenHashMap.MapEntry(curr));
/*      */       } 
/*      */     }
/*      */ 
/*      */ 
/*      */     
/*      */     public void fastForEach(Consumer<? super Int2BooleanMap.Entry> consumer) {
/* 1548 */       Int2BooleanLinkedOpenHashMap.MapEntry entry = new Int2BooleanLinkedOpenHashMap.MapEntry();
/* 1549 */       for (int i = Int2BooleanLinkedOpenHashMap.this.size, next = Int2BooleanLinkedOpenHashMap.this.first; i-- != 0; ) {
/* 1550 */         entry.index = next;
/* 1551 */         next = (int)Int2BooleanLinkedOpenHashMap.this.link[next];
/* 1552 */         consumer.accept(entry);
/*      */       } 
/*      */     }
/*      */   }
/*      */ 
/*      */   
/*      */   public Int2BooleanSortedMap.FastSortedEntrySet int2BooleanEntrySet() {
/* 1559 */     if (this.entries == null) this.entries = new MapEntrySet(); 
/* 1560 */     return this.entries;
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
/* 1573 */       super(k);
/*      */     }
/*      */ 
/*      */     
/*      */     public int previousInt() {
/* 1578 */       return Int2BooleanLinkedOpenHashMap.this.key[previousEntry()];
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
/* 1590 */       action.accept(Int2BooleanLinkedOpenHashMap.this.key[index]);
/*      */     }
/*      */ 
/*      */     
/*      */     public int nextInt() {
/* 1595 */       return Int2BooleanLinkedOpenHashMap.this.key[nextEntry()];
/*      */     } }
/*      */   
/*      */   private final class KeySet extends AbstractIntSortedSet {
/*      */     private static final int SPLITERATOR_CHARACTERISTICS = 337;
/*      */     
/*      */     private KeySet() {}
/*      */     
/*      */     public IntListIterator iterator(int from) {
/* 1604 */       return new Int2BooleanLinkedOpenHashMap.KeyIterator(from);
/*      */     }
/*      */ 
/*      */     
/*      */     public IntListIterator iterator() {
/* 1609 */       return new Int2BooleanLinkedOpenHashMap.KeyIterator();
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public IntSpliterator spliterator() {
/* 1619 */       return IntSpliterators.asSpliterator(iterator(), Size64.sizeOf(Int2BooleanLinkedOpenHashMap.this), 337);
/*      */     }
/*      */ 
/*      */ 
/*      */     
/*      */     public void forEach(IntConsumer consumer) {
/* 1625 */       for (int i = Int2BooleanLinkedOpenHashMap.this.size, next = Int2BooleanLinkedOpenHashMap.this.first; i-- != 0; ) {
/* 1626 */         int curr = next;
/* 1627 */         next = (int)Int2BooleanLinkedOpenHashMap.this.link[curr];
/* 1628 */         consumer.accept(Int2BooleanLinkedOpenHashMap.this.key[curr]);
/*      */       } 
/*      */     }
/*      */ 
/*      */     
/*      */     public int size() {
/* 1634 */       return Int2BooleanLinkedOpenHashMap.this.size;
/*      */     }
/*      */ 
/*      */     
/*      */     public boolean contains(int k) {
/* 1639 */       return Int2BooleanLinkedOpenHashMap.this.containsKey(k);
/*      */     }
/*      */ 
/*      */     
/*      */     public boolean remove(int k) {
/* 1644 */       int oldSize = Int2BooleanLinkedOpenHashMap.this.size;
/* 1645 */       Int2BooleanLinkedOpenHashMap.this.remove(k);
/* 1646 */       return (Int2BooleanLinkedOpenHashMap.this.size != oldSize);
/*      */     }
/*      */ 
/*      */     
/*      */     public void clear() {
/* 1651 */       Int2BooleanLinkedOpenHashMap.this.clear();
/*      */     }
/*      */ 
/*      */     
/*      */     public int firstInt() {
/* 1656 */       if (Int2BooleanLinkedOpenHashMap.this.size == 0) throw new NoSuchElementException(); 
/* 1657 */       return Int2BooleanLinkedOpenHashMap.this.key[Int2BooleanLinkedOpenHashMap.this.first];
/*      */     }
/*      */ 
/*      */     
/*      */     public int lastInt() {
/* 1662 */       if (Int2BooleanLinkedOpenHashMap.this.size == 0) throw new NoSuchElementException(); 
/* 1663 */       return Int2BooleanLinkedOpenHashMap.this.key[Int2BooleanLinkedOpenHashMap.this.last];
/*      */     }
/*      */ 
/*      */     
/*      */     public IntComparator comparator() {
/* 1668 */       return null;
/*      */     }
/*      */ 
/*      */     
/*      */     public IntSortedSet tailSet(int from) {
/* 1673 */       throw new UnsupportedOperationException();
/*      */     }
/*      */ 
/*      */     
/*      */     public IntSortedSet headSet(int to) {
/* 1678 */       throw new UnsupportedOperationException();
/*      */     }
/*      */ 
/*      */     
/*      */     public IntSortedSet subSet(int from, int to) {
/* 1683 */       throw new UnsupportedOperationException();
/*      */     }
/*      */   }
/*      */ 
/*      */   
/*      */   public IntSortedSet keySet() {
/* 1689 */     if (this.keys == null) this.keys = new KeySet(); 
/* 1690 */     return this.keys;
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
/* 1704 */       return Int2BooleanLinkedOpenHashMap.this.value[previousEntry()];
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
/* 1716 */       action.accept(Int2BooleanLinkedOpenHashMap.this.value[index]);
/*      */     }
/*      */ 
/*      */     
/*      */     public boolean nextBoolean() {
/* 1721 */       return Int2BooleanLinkedOpenHashMap.this.value[nextEntry()];
/*      */     }
/*      */   }
/*      */ 
/*      */   
/*      */   public BooleanCollection values() {
/* 1727 */     if (this.values == null) this.values = (BooleanCollection)new AbstractBooleanCollection()
/*      */         {
/*      */           private static final int SPLITERATOR_CHARACTERISTICS = 336;
/*      */           
/*      */           public BooleanIterator iterator() {
/* 1732 */             return (BooleanIterator)new Int2BooleanLinkedOpenHashMap.ValueIterator();
/*      */           }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */           
/*      */           public BooleanSpliterator spliterator() {
/* 1742 */             return BooleanSpliterators.asSpliterator(iterator(), Size64.sizeOf(Int2BooleanLinkedOpenHashMap.this), 336);
/*      */           }
/*      */ 
/*      */ 
/*      */           
/*      */           public void forEach(BooleanConsumer consumer) {
/* 1748 */             for (int i = Int2BooleanLinkedOpenHashMap.this.size, next = Int2BooleanLinkedOpenHashMap.this.first; i-- != 0; ) {
/* 1749 */               int curr = next;
/* 1750 */               next = (int)Int2BooleanLinkedOpenHashMap.this.link[curr];
/* 1751 */               consumer.accept(Int2BooleanLinkedOpenHashMap.this.value[curr]);
/*      */             } 
/*      */           }
/*      */ 
/*      */           
/*      */           public int size() {
/* 1757 */             return Int2BooleanLinkedOpenHashMap.this.size;
/*      */           }
/*      */ 
/*      */           
/*      */           public boolean contains(boolean v) {
/* 1762 */             return Int2BooleanLinkedOpenHashMap.this.containsValue(v);
/*      */           }
/*      */ 
/*      */           
/*      */           public void clear() {
/* 1767 */             Int2BooleanLinkedOpenHashMap.this.clear();
/*      */           }
/*      */         }; 
/* 1770 */     return this.values;
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
/* 1787 */     return trim(this.size);
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
/* 1809 */     int l = HashCommon.nextPowerOfTwo((int)Math.ceil((n / this.f)));
/* 1810 */     if (l >= this.n || this.size > HashCommon.maxFill(l, this.f)) return true; 
/*      */     try {
/* 1812 */       rehash(l);
/* 1813 */     } catch (OutOfMemoryError cantDoIt) {
/* 1814 */       return false;
/*      */     } 
/* 1816 */     return true;
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
/* 1831 */     int[] key = this.key;
/* 1832 */     boolean[] value = this.value;
/* 1833 */     int mask = newN - 1;
/* 1834 */     int[] newKey = new int[newN + 1];
/* 1835 */     boolean[] newValue = new boolean[newN + 1];
/* 1836 */     int i = this.first, prev = -1, newPrev = -1;
/* 1837 */     long[] link = this.link;
/* 1838 */     long[] newLink = new long[newN + 1];
/* 1839 */     this.first = -1;
/* 1840 */     for (int j = this.size; j-- != 0; ) {
/* 1841 */       int pos; if (key[i] == 0) { pos = newN; }
/*      */       else
/* 1843 */       { pos = HashCommon.mix(key[i]) & mask;
/* 1844 */         for (; newKey[pos] != 0; pos = pos + 1 & mask); }
/*      */       
/* 1846 */       newKey[pos] = key[i];
/* 1847 */       newValue[pos] = value[i];
/* 1848 */       if (prev != -1) {
/* 1849 */         newLink[newPrev] = newLink[newPrev] ^ (newLink[newPrev] ^ pos & 0xFFFFFFFFL) & 0xFFFFFFFFL;
/* 1850 */         newLink[pos] = newLink[pos] ^ (newLink[pos] ^ (newPrev & 0xFFFFFFFFL) << 32L) & 0xFFFFFFFF00000000L;
/* 1851 */         newPrev = pos;
/*      */       } else {
/* 1853 */         newPrev = this.first = pos;
/*      */         
/* 1855 */         newLink[pos] = -1L;
/*      */       } 
/* 1857 */       int t = i;
/* 1858 */       i = (int)link[i];
/* 1859 */       prev = t;
/*      */     } 
/* 1861 */     this.link = newLink;
/* 1862 */     this.last = newPrev;
/* 1863 */     if (newPrev != -1)
/*      */     {
/* 1865 */       newLink[newPrev] = newLink[newPrev] | 0xFFFFFFFFL; } 
/* 1866 */     this.n = newN;
/* 1867 */     this.mask = mask;
/* 1868 */     this.maxFill = HashCommon.maxFill(this.n, this.f);
/* 1869 */     this.key = newKey;
/* 1870 */     this.value = newValue;
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
/*      */   public Int2BooleanLinkedOpenHashMap clone() {
/*      */     Int2BooleanLinkedOpenHashMap c;
/*      */     try {
/* 1887 */       c = (Int2BooleanLinkedOpenHashMap)super.clone();
/* 1888 */     } catch (CloneNotSupportedException cantHappen) {
/* 1889 */       throw new InternalError();
/*      */     } 
/* 1891 */     c.keys = null;
/* 1892 */     c.values = null;
/* 1893 */     c.entries = null;
/* 1894 */     c.containsNullKey = this.containsNullKey;
/* 1895 */     c.key = (int[])this.key.clone();
/* 1896 */     c.value = (boolean[])this.value.clone();
/* 1897 */     c.link = (long[])this.link.clone();
/* 1898 */     return c;
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
/* 1912 */     int h = 0;
/* 1913 */     for (int j = realSize(), i = 0, t = 0; j-- != 0; ) {
/* 1914 */       for (; this.key[i] == 0; i++);
/* 1915 */       t = this.key[i];
/* 1916 */       t ^= this.value[i] ? 1231 : 1237;
/* 1917 */       h += t;
/* 1918 */       i++;
/*      */     } 
/*      */     
/* 1921 */     if (this.containsNullKey) h += this.value[this.n] ? 1231 : 1237; 
/* 1922 */     return h;
/*      */   }
/*      */   
/*      */   private void writeObject(ObjectOutputStream s) throws IOException {
/* 1926 */     int[] key = this.key;
/* 1927 */     boolean[] value = this.value;
/* 1928 */     EntryIterator i = new EntryIterator();
/* 1929 */     s.defaultWriteObject();
/* 1930 */     for (int j = this.size; j-- != 0; ) {
/* 1931 */       int e = i.nextEntry();
/* 1932 */       s.writeInt(key[e]);
/* 1933 */       s.writeBoolean(value[e]);
/*      */     } 
/*      */   }
/*      */   
/*      */   private void readObject(ObjectInputStream s) throws IOException, ClassNotFoundException {
/* 1938 */     s.defaultReadObject();
/* 1939 */     this.n = HashCommon.arraySize(this.size, this.f);
/* 1940 */     this.maxFill = HashCommon.maxFill(this.n, this.f);
/* 1941 */     this.mask = this.n - 1;
/* 1942 */     int[] key = this.key = new int[this.n + 1];
/* 1943 */     boolean[] value = this.value = new boolean[this.n + 1];
/* 1944 */     long[] link = this.link = new long[this.n + 1];
/* 1945 */     int prev = -1;
/* 1946 */     this.first = this.last = -1;
/*      */ 
/*      */     
/* 1949 */     for (int i = this.size; i-- != 0; ) {
/* 1950 */       int pos, k = s.readInt();
/* 1951 */       boolean v = s.readBoolean();
/* 1952 */       if (k == 0) {
/* 1953 */         pos = this.n;
/* 1954 */         this.containsNullKey = true;
/*      */       } else {
/* 1956 */         pos = HashCommon.mix(k) & this.mask;
/* 1957 */         for (; key[pos] != 0; pos = pos + 1 & this.mask);
/*      */       } 
/* 1959 */       key[pos] = k;
/* 1960 */       value[pos] = v;
/* 1961 */       if (this.first != -1) {
/* 1962 */         link[prev] = link[prev] ^ (link[prev] ^ pos & 0xFFFFFFFFL) & 0xFFFFFFFFL;
/* 1963 */         link[pos] = link[pos] ^ (link[pos] ^ (prev & 0xFFFFFFFFL) << 32L) & 0xFFFFFFFF00000000L;
/* 1964 */         prev = pos; continue;
/*      */       } 
/* 1966 */       prev = this.first = pos;
/*      */       
/* 1968 */       link[pos] = link[pos] | 0xFFFFFFFF00000000L;
/*      */     } 
/*      */     
/* 1971 */     this.last = prev;
/* 1972 */     if (prev != -1)
/*      */     {
/* 1974 */       link[prev] = link[prev] | 0xFFFFFFFFL;
/*      */     }
/*      */   }
/*      */   
/*      */   private void checkTable() {}
/*      */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\i\\unimi\dsi\fastutil\ints\Int2BooleanLinkedOpenHashMap.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */