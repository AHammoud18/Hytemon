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
/*      */ public class Reference2BooleanLinkedOpenHashMap<K>
/*      */   extends AbstractReference2BooleanSortedMap<K>
/*      */   implements Serializable, Cloneable, Hash
/*      */ {
/*      */   private static final long serialVersionUID = 0L;
/*      */   private static final boolean ASSERTS = false;
/*      */   protected transient K[] key;
/*      */   protected transient boolean[] value;
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
/*      */   protected transient Reference2BooleanSortedMap.FastSortedEntrySet<K> entries;
/*      */ 
/*      */   
/*      */   protected transient ReferenceSortedSet<K> keys;
/*      */ 
/*      */   
/*      */   protected transient BooleanCollection values;
/*      */ 
/*      */ 
/*      */   
/*      */   public Reference2BooleanLinkedOpenHashMap(int expected, float f) {
/*  142 */     if (f <= 0.0F || f >= 1.0F) throw new IllegalArgumentException("Load factor must be greater than 0 and smaller than 1"); 
/*  143 */     if (expected < 0) throw new IllegalArgumentException("The expected number of elements must be nonnegative"); 
/*  144 */     this.f = f;
/*  145 */     this.minN = this.n = HashCommon.arraySize(expected, f);
/*  146 */     this.mask = this.n - 1;
/*  147 */     this.maxFill = HashCommon.maxFill(this.n, f);
/*  148 */     this.key = (K[])new Object[this.n + 1];
/*  149 */     this.value = new boolean[this.n + 1];
/*  150 */     this.link = new long[this.n + 1];
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Reference2BooleanLinkedOpenHashMap(int expected) {
/*  159 */     this(expected, 0.75F);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Reference2BooleanLinkedOpenHashMap() {
/*  167 */     this(16, 0.75F);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Reference2BooleanLinkedOpenHashMap(Map<? extends K, ? extends Boolean> m, float f) {
/*  177 */     this(m.size(), f);
/*  178 */     putAll(m);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Reference2BooleanLinkedOpenHashMap(Map<? extends K, ? extends Boolean> m) {
/*  187 */     this(m, 0.75F);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Reference2BooleanLinkedOpenHashMap(Reference2BooleanMap<K> m, float f) {
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
/*      */   public Reference2BooleanLinkedOpenHashMap(Reference2BooleanMap<K> m) {
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
/*      */   public Reference2BooleanLinkedOpenHashMap(K[] k, boolean[] v, float f) {
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
/*      */   public Reference2BooleanLinkedOpenHashMap(K[] k, boolean[] v) {
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
/*      */   private boolean removeEntry(int pos) {
/*  258 */     boolean oldValue = this.value[pos];
/*  259 */     this.size--;
/*  260 */     fixPointers(pos);
/*  261 */     shiftKeys(pos);
/*  262 */     if (this.n > this.minN && this.size < this.maxFill / 4 && this.n > 16) rehash(this.n / 2); 
/*  263 */     return oldValue;
/*      */   }
/*      */   
/*      */   private boolean removeNullEntry() {
/*  267 */     this.containsNullKey = false;
/*  268 */     this.key[this.n] = null;
/*  269 */     boolean oldValue = this.value[this.n];
/*  270 */     this.size--;
/*  271 */     fixPointers(this.n);
/*  272 */     if (this.n > this.minN && this.size < this.maxFill / 4 && this.n > 16) rehash(this.n / 2); 
/*  273 */     return oldValue;
/*      */   }
/*      */ 
/*      */   
/*      */   public void putAll(Map<? extends K, ? extends Boolean> m) {
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
/*  291 */     if ((curr = key[pos = HashCommon.mix(System.identityHashCode(k)) & this.mask]) == null) return -(pos + 1); 
/*  292 */     if (k == curr) return pos;
/*      */     
/*      */     while (true) {
/*  295 */       if ((curr = key[pos = pos + 1 & this.mask]) == null) return -(pos + 1); 
/*  296 */       if (k == curr) return pos; 
/*      */     } 
/*      */   }
/*      */   
/*      */   private void insert(int pos, K k, boolean v) {
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
/*      */   public boolean put(K k, boolean v) {
/*  319 */     int pos = find(k);
/*  320 */     if (pos < 0) {
/*  321 */       insert(-pos - 1, k, v);
/*  322 */       return this.defRetValue;
/*      */     } 
/*  324 */     boolean oldValue = this.value[pos];
/*  325 */     this.value[pos] = v;
/*  326 */     return oldValue;
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
/*  339 */     K[] key = this.key; while (true) {
/*      */       K curr; int last;
/*  341 */       pos = (last = pos) + 1 & this.mask;
/*      */       while (true) {
/*  343 */         if ((curr = key[pos]) == null) {
/*  344 */           key[last] = null;
/*      */           return;
/*      */         } 
/*  347 */         int slot = HashCommon.mix(System.identityHashCode(curr)) & this.mask;
/*  348 */         if ((last <= pos) ? (last >= slot || slot > pos) : (last >= slot && slot > pos))
/*  349 */           break;  pos = pos + 1 & this.mask;
/*      */       } 
/*  351 */       key[last] = curr;
/*  352 */       this.value[last] = this.value[pos];
/*  353 */       fixPointers(pos, last);
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean removeBoolean(Object k) {
/*  360 */     if (k == null) {
/*  361 */       if (this.containsNullKey) return removeNullEntry(); 
/*  362 */       return this.defRetValue;
/*      */     } 
/*      */     
/*  365 */     K[] key = this.key;
/*      */     K curr;
/*      */     int pos;
/*  368 */     if ((curr = key[pos = HashCommon.mix(System.identityHashCode(k)) & this.mask]) == null) return this.defRetValue; 
/*  369 */     if (k == curr) return removeEntry(pos); 
/*      */     while (true) {
/*  371 */       if ((curr = key[pos = pos + 1 & this.mask]) == null) return this.defRetValue; 
/*  372 */       if (k == curr) return removeEntry(pos); 
/*      */     } 
/*      */   }
/*      */   
/*      */   private boolean setValue(int pos, boolean v) {
/*  377 */     boolean oldValue = this.value[pos];
/*  378 */     this.value[pos] = v;
/*  379 */     return oldValue;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean removeFirstBoolean() {
/*  389 */     if (this.size == 0) throw new NoSuchElementException(); 
/*  390 */     int pos = this.first;
/*      */     
/*  392 */     if (this.size == 1) { this.first = this.last = -1; }
/*      */     else
/*  394 */     { this.first = (int)this.link[pos];
/*  395 */       if (0 <= this.first)
/*      */       {
/*  397 */         this.link[this.first] = this.link[this.first] | 0xFFFFFFFF00000000L;
/*      */       } }
/*      */     
/*  400 */     this.size--;
/*  401 */     boolean v = this.value[pos];
/*  402 */     if (pos == this.n)
/*  403 */     { this.containsNullKey = false;
/*  404 */       this.key[this.n] = null; }
/*  405 */     else { shiftKeys(pos); }
/*  406 */      if (this.n > this.minN && this.size < this.maxFill / 4 && this.n > 16) rehash(this.n / 2); 
/*  407 */     return v;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean removeLastBoolean() {
/*  417 */     if (this.size == 0) throw new NoSuchElementException(); 
/*  418 */     int pos = this.last;
/*      */     
/*  420 */     if (this.size == 1) { this.first = this.last = -1; }
/*      */     else
/*  422 */     { this.last = (int)(this.link[pos] >>> 32L);
/*  423 */       if (0 <= this.last)
/*      */       {
/*  425 */         this.link[this.last] = this.link[this.last] | 0xFFFFFFFFL;
/*      */       } }
/*      */     
/*  428 */     this.size--;
/*  429 */     boolean v = this.value[pos];
/*  430 */     if (pos == this.n)
/*  431 */     { this.containsNullKey = false;
/*  432 */       this.key[this.n] = null; }
/*  433 */     else { shiftKeys(pos); }
/*  434 */      if (this.n > this.minN && this.size < this.maxFill / 4 && this.n > 16) rehash(this.n / 2); 
/*  435 */     return v;
/*      */   }
/*      */   
/*      */   private void moveIndexToFirst(int i) {
/*  439 */     if (this.size == 1 || this.first == i)
/*  440 */       return;  if (this.last == i) {
/*  441 */       this.last = (int)(this.link[i] >>> 32L);
/*      */       
/*  443 */       this.link[this.last] = this.link[this.last] | 0xFFFFFFFFL;
/*      */     } else {
/*  445 */       long linki = this.link[i];
/*  446 */       int prev = (int)(linki >>> 32L);
/*  447 */       int next = (int)linki;
/*  448 */       this.link[prev] = this.link[prev] ^ (this.link[prev] ^ linki & 0xFFFFFFFFL) & 0xFFFFFFFFL;
/*  449 */       this.link[next] = this.link[next] ^ (this.link[next] ^ linki & 0xFFFFFFFF00000000L) & 0xFFFFFFFF00000000L;
/*      */     } 
/*  451 */     this.link[this.first] = this.link[this.first] ^ (this.link[this.first] ^ (i & 0xFFFFFFFFL) << 32L) & 0xFFFFFFFF00000000L;
/*  452 */     this.link[i] = 0xFFFFFFFF00000000L | this.first & 0xFFFFFFFFL;
/*  453 */     this.first = i;
/*      */   }
/*      */   
/*      */   private void moveIndexToLast(int i) {
/*  457 */     if (this.size == 1 || this.last == i)
/*  458 */       return;  if (this.first == i) {
/*  459 */       this.first = (int)this.link[i];
/*      */       
/*  461 */       this.link[this.first] = this.link[this.first] | 0xFFFFFFFF00000000L;
/*      */     } else {
/*  463 */       long linki = this.link[i];
/*  464 */       int prev = (int)(linki >>> 32L);
/*  465 */       int next = (int)linki;
/*  466 */       this.link[prev] = this.link[prev] ^ (this.link[prev] ^ linki & 0xFFFFFFFFL) & 0xFFFFFFFFL;
/*  467 */       this.link[next] = this.link[next] ^ (this.link[next] ^ linki & 0xFFFFFFFF00000000L) & 0xFFFFFFFF00000000L;
/*      */     } 
/*  469 */     this.link[this.last] = this.link[this.last] ^ (this.link[this.last] ^ i & 0xFFFFFFFFL) & 0xFFFFFFFFL;
/*  470 */     this.link[i] = (this.last & 0xFFFFFFFFL) << 32L | 0xFFFFFFFFL;
/*  471 */     this.last = i;
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
/*  483 */     if (k == null) {
/*  484 */       if (this.containsNullKey) {
/*  485 */         moveIndexToFirst(this.n);
/*  486 */         return this.value[this.n];
/*      */       } 
/*  488 */       return this.defRetValue;
/*      */     } 
/*      */     
/*  491 */     K[] key = this.key;
/*      */     K curr;
/*      */     int pos;
/*  494 */     if ((curr = key[pos = HashCommon.mix(System.identityHashCode(k)) & this.mask]) == null) return this.defRetValue; 
/*  495 */     if (k == curr) {
/*  496 */       moveIndexToFirst(pos);
/*  497 */       return this.value[pos];
/*      */     } 
/*      */     
/*      */     while (true) {
/*  501 */       if ((curr = key[pos = pos + 1 & this.mask]) == null) return this.defRetValue; 
/*  502 */       if (k == curr) {
/*  503 */         moveIndexToFirst(pos);
/*  504 */         return this.value[pos];
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
/*  518 */     if (k == null) {
/*  519 */       if (this.containsNullKey) {
/*  520 */         moveIndexToLast(this.n);
/*  521 */         return this.value[this.n];
/*      */       } 
/*  523 */       return this.defRetValue;
/*      */     } 
/*      */     
/*  526 */     K[] key = this.key;
/*      */     K curr;
/*      */     int pos;
/*  529 */     if ((curr = key[pos = HashCommon.mix(System.identityHashCode(k)) & this.mask]) == null) return this.defRetValue; 
/*  530 */     if (k == curr) {
/*  531 */       moveIndexToLast(pos);
/*  532 */       return this.value[pos];
/*      */     } 
/*      */     
/*      */     while (true) {
/*  536 */       if ((curr = key[pos = pos + 1 & this.mask]) == null) return this.defRetValue; 
/*  537 */       if (k == curr) {
/*  538 */         moveIndexToLast(pos);
/*  539 */         return this.value[pos];
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
/*  555 */     if (k == null) {
/*  556 */       if (this.containsNullKey) {
/*  557 */         moveIndexToFirst(this.n);
/*  558 */         return setValue(this.n, v);
/*      */       } 
/*  560 */       this.containsNullKey = true;
/*  561 */       pos = this.n;
/*      */     } else {
/*      */       
/*  564 */       K[] key = this.key;
/*      */       K curr;
/*  566 */       if ((curr = key[pos = HashCommon.mix(System.identityHashCode(k)) & this.mask]) != null) {
/*  567 */         if (curr == k) {
/*  568 */           moveIndexToFirst(pos);
/*  569 */           return setValue(pos, v);
/*      */         } 
/*  571 */         while ((curr = key[pos = pos + 1 & this.mask]) != null) { if (curr == k) {
/*  572 */             moveIndexToFirst(pos);
/*  573 */             return setValue(pos, v);
/*      */           }  }
/*      */       
/*      */       } 
/*  577 */     }  this.key[pos] = k;
/*  578 */     this.value[pos] = v;
/*  579 */     if (this.size == 0) {
/*  580 */       this.first = this.last = pos;
/*      */       
/*  582 */       this.link[pos] = -1L;
/*      */     } else {
/*  584 */       this.link[this.first] = this.link[this.first] ^ (this.link[this.first] ^ (pos & 0xFFFFFFFFL) << 32L) & 0xFFFFFFFF00000000L;
/*  585 */       this.link[pos] = 0xFFFFFFFF00000000L | this.first & 0xFFFFFFFFL;
/*  586 */       this.first = pos;
/*      */     } 
/*  588 */     if (this.size++ >= this.maxFill) rehash(HashCommon.arraySize(this.size, this.f));
/*      */     
/*  590 */     return this.defRetValue;
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
/*  604 */     if (k == null) {
/*  605 */       if (this.containsNullKey) {
/*  606 */         moveIndexToLast(this.n);
/*  607 */         return setValue(this.n, v);
/*      */       } 
/*  609 */       this.containsNullKey = true;
/*  610 */       pos = this.n;
/*      */     } else {
/*      */       
/*  613 */       K[] key = this.key;
/*      */       K curr;
/*  615 */       if ((curr = key[pos = HashCommon.mix(System.identityHashCode(k)) & this.mask]) != null) {
/*  616 */         if (curr == k) {
/*  617 */           moveIndexToLast(pos);
/*  618 */           return setValue(pos, v);
/*      */         } 
/*  620 */         while ((curr = key[pos = pos + 1 & this.mask]) != null) { if (curr == k) {
/*  621 */             moveIndexToLast(pos);
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
/*  633 */       this.link[this.last] = this.link[this.last] ^ (this.link[this.last] ^ pos & 0xFFFFFFFFL) & 0xFFFFFFFFL;
/*  634 */       this.link[pos] = (this.last & 0xFFFFFFFFL) << 32L | 0xFFFFFFFFL;
/*  635 */       this.last = pos;
/*      */     } 
/*  637 */     if (this.size++ >= this.maxFill) rehash(HashCommon.arraySize(this.size, this.f));
/*      */     
/*  639 */     return this.defRetValue;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean getBoolean(Object k) {
/*  645 */     if (k == null) return this.containsNullKey ? this.value[this.n] : this.defRetValue;
/*      */     
/*  647 */     K[] key = this.key;
/*      */     K curr;
/*      */     int pos;
/*  650 */     if ((curr = key[pos = HashCommon.mix(System.identityHashCode(k)) & this.mask]) == null) return this.defRetValue; 
/*  651 */     if (k == curr) return this.value[pos];
/*      */     
/*      */     while (true) {
/*  654 */       if ((curr = key[pos = pos + 1 & this.mask]) == null) return this.defRetValue; 
/*  655 */       if (k == curr) return this.value[pos];
/*      */     
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   public boolean containsKey(Object k) {
/*  662 */     if (k == null) return this.containsNullKey;
/*      */     
/*  664 */     K[] key = this.key;
/*      */     K curr;
/*      */     int pos;
/*  667 */     if ((curr = key[pos = HashCommon.mix(System.identityHashCode(k)) & this.mask]) == null) return false; 
/*  668 */     if (k == curr) return true;
/*      */     
/*      */     while (true) {
/*  671 */       if ((curr = key[pos = pos + 1 & this.mask]) == null) return false; 
/*  672 */       if (k == curr) return true;
/*      */     
/*      */     } 
/*      */   }
/*      */   
/*      */   public boolean containsValue(boolean v) {
/*  678 */     boolean[] value = this.value;
/*  679 */     K[] key = this.key;
/*  680 */     if (this.containsNullKey && value[this.n] == v) return true; 
/*  681 */     for (int i = this.n; i-- != 0;) { if (key[i] != null && value[i] == v) return true;  }
/*  682 */      return false;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean getOrDefault(Object k, boolean defaultValue) {
/*  689 */     if (k == null) return this.containsNullKey ? this.value[this.n] : defaultValue;
/*      */     
/*  691 */     K[] key = this.key;
/*      */     K curr;
/*      */     int pos;
/*  694 */     if ((curr = key[pos = HashCommon.mix(System.identityHashCode(k)) & this.mask]) == null) return defaultValue; 
/*  695 */     if (k == curr) return this.value[pos];
/*      */     
/*      */     while (true) {
/*  698 */       if ((curr = key[pos = pos + 1 & this.mask]) == null) return defaultValue; 
/*  699 */       if (k == curr) return this.value[pos];
/*      */     
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   public boolean putIfAbsent(K k, boolean v) {
/*  706 */     int pos = find(k);
/*  707 */     if (pos >= 0) return this.value[pos]; 
/*  708 */     insert(-pos - 1, k, v);
/*  709 */     return this.defRetValue;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean remove(Object k, boolean v) {
/*  716 */     if (k == null) {
/*  717 */       if (this.containsNullKey && v == this.value[this.n]) {
/*  718 */         removeNullEntry();
/*  719 */         return true;
/*      */       } 
/*  721 */       return false;
/*      */     } 
/*      */     
/*  724 */     K[] key = this.key;
/*      */     K curr;
/*      */     int pos;
/*  727 */     if ((curr = key[pos = HashCommon.mix(System.identityHashCode(k)) & this.mask]) == null) return false; 
/*  728 */     if (k == curr && v == this.value[pos]) {
/*  729 */       removeEntry(pos);
/*  730 */       return true;
/*      */     } 
/*      */     while (true) {
/*  733 */       if ((curr = key[pos = pos + 1 & this.mask]) == null) return false; 
/*  734 */       if (k == curr && v == this.value[pos]) {
/*  735 */         removeEntry(pos);
/*  736 */         return true;
/*      */       } 
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean replace(K k, boolean oldValue, boolean v) {
/*  744 */     int pos = find(k);
/*  745 */     if (pos < 0 || oldValue != this.value[pos]) return false; 
/*  746 */     this.value[pos] = v;
/*  747 */     return true;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean replace(K k, boolean v) {
/*  753 */     int pos = find(k);
/*  754 */     if (pos < 0) return this.defRetValue; 
/*  755 */     boolean oldValue = this.value[pos];
/*  756 */     this.value[pos] = v;
/*  757 */     return oldValue;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean computeIfAbsent(K k, Predicate<? super K> mappingFunction) {
/*  763 */     Objects.requireNonNull(mappingFunction);
/*  764 */     int pos = find(k);
/*  765 */     if (pos >= 0) return this.value[pos]; 
/*  766 */     boolean newValue = mappingFunction.test(k);
/*  767 */     insert(-pos - 1, k, newValue);
/*  768 */     return newValue;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean computeIfAbsent(K key, Reference2BooleanFunction<? super K> mappingFunction) {
/*  774 */     Objects.requireNonNull(mappingFunction);
/*  775 */     int pos = find(key);
/*  776 */     if (pos >= 0) return this.value[pos]; 
/*  777 */     if (!mappingFunction.containsKey(key)) return this.defRetValue; 
/*  778 */     boolean newValue = mappingFunction.getBoolean(key);
/*  779 */     insert(-pos - 1, key, newValue);
/*  780 */     return newValue;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean computeBooleanIfPresent(K k, BiFunction<? super K, ? super Boolean, ? extends Boolean> remappingFunction) {
/*  786 */     Objects.requireNonNull(remappingFunction);
/*  787 */     int pos = find(k);
/*  788 */     if (pos < 0) return this.defRetValue; 
/*  789 */     Boolean newValue = remappingFunction.apply(k, Boolean.valueOf(this.value[pos]));
/*  790 */     if (newValue == null) {
/*  791 */       if (k == null) { removeNullEntry(); }
/*  792 */       else { removeEntry(pos); }
/*  793 */        return this.defRetValue;
/*      */     } 
/*  795 */     this.value[pos] = newValue.booleanValue(); return newValue.booleanValue();
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean computeBoolean(K k, BiFunction<? super K, ? super Boolean, ? extends Boolean> remappingFunction) {
/*  801 */     Objects.requireNonNull(remappingFunction);
/*  802 */     int pos = find(k);
/*  803 */     Boolean newValue = remappingFunction.apply(k, (pos >= 0) ? Boolean.valueOf(this.value[pos]) : null);
/*  804 */     if (newValue == null) {
/*  805 */       if (pos >= 0)
/*  806 */         if (k == null) { removeNullEntry(); }
/*  807 */         else { removeEntry(pos); }
/*      */          
/*  809 */       return this.defRetValue;
/*      */     } 
/*  811 */     boolean newVal = newValue.booleanValue();
/*  812 */     if (pos < 0) {
/*  813 */       insert(-pos - 1, k, newVal);
/*  814 */       return newVal;
/*      */     } 
/*  816 */     this.value[pos] = newVal; return newVal;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean merge(K k, boolean v, BiFunction<? super Boolean, ? super Boolean, ? extends Boolean> remappingFunction) {
/*  822 */     Objects.requireNonNull(remappingFunction);
/*      */     
/*  824 */     int pos = find(k);
/*  825 */     if (pos < 0) {
/*  826 */       if (pos < 0) { insert(-pos - 1, k, v); }
/*  827 */       else { this.value[pos] = v; }
/*  828 */        return v;
/*      */     } 
/*  830 */     Boolean newValue = remappingFunction.apply(Boolean.valueOf(this.value[pos]), Boolean.valueOf(v));
/*  831 */     if (newValue == null) {
/*  832 */       if (k == null) { removeNullEntry(); }
/*  833 */       else { removeEntry(pos); }
/*  834 */        return this.defRetValue;
/*      */     } 
/*  836 */     this.value[pos] = newValue.booleanValue(); return newValue.booleanValue();
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
/*  847 */     if (this.size == 0)
/*  848 */       return;  this.size = 0;
/*  849 */     this.containsNullKey = false;
/*  850 */     Arrays.fill((Object[])this.key, (Object)null);
/*  851 */     this.first = this.last = -1;
/*      */   }
/*      */ 
/*      */   
/*      */   public int size() {
/*  856 */     return this.size;
/*      */   }
/*      */ 
/*      */   
/*      */   public boolean isEmpty() {
/*  861 */     return (this.size == 0);
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
/*  874 */       this.index = index;
/*      */     }
/*      */ 
/*      */     
/*      */     MapEntry() {}
/*      */ 
/*      */     
/*      */     public K getKey() {
/*  882 */       return Reference2BooleanLinkedOpenHashMap.this.key[this.index];
/*      */     }
/*      */ 
/*      */     
/*      */     public K left() {
/*  887 */       return Reference2BooleanLinkedOpenHashMap.this.key[this.index];
/*      */     }
/*      */ 
/*      */     
/*      */     public boolean getBooleanValue() {
/*  892 */       return Reference2BooleanLinkedOpenHashMap.this.value[this.index];
/*      */     }
/*      */ 
/*      */     
/*      */     public boolean rightBoolean() {
/*  897 */       return Reference2BooleanLinkedOpenHashMap.this.value[this.index];
/*      */     }
/*      */ 
/*      */     
/*      */     public boolean setValue(boolean v) {
/*  902 */       boolean oldValue = Reference2BooleanLinkedOpenHashMap.this.value[this.index];
/*  903 */       Reference2BooleanLinkedOpenHashMap.this.value[this.index] = v;
/*  904 */       return oldValue;
/*      */     }
/*      */ 
/*      */     
/*      */     public ReferenceBooleanPair<K> right(boolean v) {
/*  909 */       Reference2BooleanLinkedOpenHashMap.this.value[this.index] = v;
/*  910 */       return this;
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
/*  921 */       return Boolean.valueOf(Reference2BooleanLinkedOpenHashMap.this.value[this.index]);
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
/*  932 */       return Boolean.valueOf(setValue(v.booleanValue()));
/*      */     }
/*      */ 
/*      */ 
/*      */     
/*      */     public boolean equals(Object o) {
/*  938 */       if (!(o instanceof Map.Entry)) return false; 
/*  939 */       Map.Entry<K, Boolean> e = (Map.Entry<K, Boolean>)o;
/*  940 */       return (Reference2BooleanLinkedOpenHashMap.this.key[this.index] == e.getKey() && Reference2BooleanLinkedOpenHashMap.this.value[this.index] == ((Boolean)e.getValue()).booleanValue());
/*      */     }
/*      */ 
/*      */     
/*      */     public int hashCode() {
/*  945 */       return System.identityHashCode(Reference2BooleanLinkedOpenHashMap.this.key[this.index]) ^ (Reference2BooleanLinkedOpenHashMap.this.value[this.index] ? 1231 : 1237);
/*      */     }
/*      */ 
/*      */     
/*      */     public String toString() {
/*  950 */       return (new StringBuilder()).append(Reference2BooleanLinkedOpenHashMap.this.key[this.index]).append("=>").append(Reference2BooleanLinkedOpenHashMap.this.value[this.index]).toString();
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
/*  961 */     if (this.size == 0) {
/*  962 */       this.first = this.last = -1;
/*      */       return;
/*      */     } 
/*  965 */     if (this.first == i) {
/*  966 */       this.first = (int)this.link[i];
/*  967 */       if (0 <= this.first)
/*      */       {
/*  969 */         this.link[this.first] = this.link[this.first] | 0xFFFFFFFF00000000L;
/*      */       }
/*      */       return;
/*      */     } 
/*  973 */     if (this.last == i) {
/*  974 */       this.last = (int)(this.link[i] >>> 32L);
/*  975 */       if (0 <= this.last)
/*      */       {
/*  977 */         this.link[this.last] = this.link[this.last] | 0xFFFFFFFFL;
/*      */       }
/*      */       return;
/*      */     } 
/*  981 */     long linki = this.link[i];
/*  982 */     int prev = (int)(linki >>> 32L);
/*  983 */     int next = (int)linki;
/*  984 */     this.link[prev] = this.link[prev] ^ (this.link[prev] ^ linki & 0xFFFFFFFFL) & 0xFFFFFFFFL;
/*  985 */     this.link[next] = this.link[next] ^ (this.link[next] ^ linki & 0xFFFFFFFF00000000L) & 0xFFFFFFFF00000000L;
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
/*  997 */     if (this.size == 1) {
/*  998 */       this.first = this.last = d;
/*      */       
/* 1000 */       this.link[d] = -1L;
/*      */       return;
/*      */     } 
/* 1003 */     if (this.first == s) {
/* 1004 */       this.first = d;
/* 1005 */       this.link[(int)this.link[s]] = this.link[(int)this.link[s]] ^ (this.link[(int)this.link[s]] ^ (d & 0xFFFFFFFFL) << 32L) & 0xFFFFFFFF00000000L;
/* 1006 */       this.link[d] = this.link[s];
/*      */       return;
/*      */     } 
/* 1009 */     if (this.last == s) {
/* 1010 */       this.last = d;
/* 1011 */       this.link[(int)(this.link[s] >>> 32L)] = this.link[(int)(this.link[s] >>> 32L)] ^ (this.link[(int)(this.link[s] >>> 32L)] ^ d & 0xFFFFFFFFL) & 0xFFFFFFFFL;
/* 1012 */       this.link[d] = this.link[s];
/*      */       return;
/*      */     } 
/* 1015 */     long links = this.link[s];
/* 1016 */     int prev = (int)(links >>> 32L);
/* 1017 */     int next = (int)links;
/* 1018 */     this.link[prev] = this.link[prev] ^ (this.link[prev] ^ d & 0xFFFFFFFFL) & 0xFFFFFFFFL;
/* 1019 */     this.link[next] = this.link[next] ^ (this.link[next] ^ (d & 0xFFFFFFFFL) << 32L) & 0xFFFFFFFF00000000L;
/* 1020 */     this.link[d] = links;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public K firstKey() {
/* 1030 */     if (this.size == 0) throw new NoSuchElementException(); 
/* 1031 */     return this.key[this.first];
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public K lastKey() {
/* 1041 */     if (this.size == 0) throw new NoSuchElementException(); 
/* 1042 */     return this.key[this.last];
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Reference2BooleanSortedMap<K> tailMap(K from) {
/* 1052 */     throw new UnsupportedOperationException();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Reference2BooleanSortedMap<K> headMap(K to) {
/* 1062 */     throw new UnsupportedOperationException();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Reference2BooleanSortedMap<K> subMap(K from, K to) {
/* 1072 */     throw new UnsupportedOperationException();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Comparator<? super K> comparator() {
/* 1082 */     return null;
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
/* 1097 */     int prev = -1;
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 1102 */     int next = -1;
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 1107 */     int curr = -1;
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 1112 */     int index = -1;
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     protected MapIterator() {
/* 1118 */       this.next = Reference2BooleanLinkedOpenHashMap.this.first;
/* 1119 */       this.index = 0;
/*      */     }
/*      */     
/*      */     private MapIterator(K from) {
/* 1123 */       if (from == null) {
/* 1124 */         if (Reference2BooleanLinkedOpenHashMap.this.containsNullKey) {
/* 1125 */           this.next = (int)Reference2BooleanLinkedOpenHashMap.this.link[Reference2BooleanLinkedOpenHashMap.this.n];
/* 1126 */           this.prev = Reference2BooleanLinkedOpenHashMap.this.n; return;
/*      */         } 
/* 1128 */         throw new NoSuchElementException("The key " + from + " does not belong to this map.");
/*      */       } 
/* 1130 */       if (Reference2BooleanLinkedOpenHashMap.this.key[Reference2BooleanLinkedOpenHashMap.this.last] == from) {
/* 1131 */         this.prev = Reference2BooleanLinkedOpenHashMap.this.last;
/* 1132 */         this.index = Reference2BooleanLinkedOpenHashMap.this.size;
/*      */         
/*      */         return;
/*      */       } 
/* 1136 */       int pos = HashCommon.mix(System.identityHashCode(from)) & Reference2BooleanLinkedOpenHashMap.this.mask;
/*      */       
/* 1138 */       while (Reference2BooleanLinkedOpenHashMap.this.key[pos] != null) {
/* 1139 */         if (Reference2BooleanLinkedOpenHashMap.this.key[pos] == from) {
/*      */           
/* 1141 */           this.next = (int)Reference2BooleanLinkedOpenHashMap.this.link[pos];
/* 1142 */           this.prev = pos;
/*      */           return;
/*      */         } 
/* 1145 */         pos = pos + 1 & Reference2BooleanLinkedOpenHashMap.this.mask;
/*      */       } 
/* 1147 */       throw new NoSuchElementException("The key " + from + " does not belong to this map.");
/*      */     }
/*      */     
/*      */     public boolean hasNext() {
/* 1151 */       return (this.next != -1);
/*      */     }
/*      */     
/*      */     public boolean hasPrevious() {
/* 1155 */       return (this.prev != -1);
/*      */     }
/*      */     
/*      */     private final void ensureIndexKnown() {
/* 1159 */       if (this.index >= 0)
/* 1160 */         return;  if (this.prev == -1) {
/* 1161 */         this.index = 0;
/*      */         return;
/*      */       } 
/* 1164 */       if (this.next == -1) {
/* 1165 */         this.index = Reference2BooleanLinkedOpenHashMap.this.size;
/*      */         return;
/*      */       } 
/* 1168 */       int pos = Reference2BooleanLinkedOpenHashMap.this.first;
/* 1169 */       this.index = 1;
/* 1170 */       while (pos != this.prev) {
/* 1171 */         pos = (int)Reference2BooleanLinkedOpenHashMap.this.link[pos];
/* 1172 */         this.index++;
/*      */       } 
/*      */     }
/*      */     
/*      */     public int nextIndex() {
/* 1177 */       ensureIndexKnown();
/* 1178 */       return this.index;
/*      */     }
/*      */     
/*      */     public int previousIndex() {
/* 1182 */       ensureIndexKnown();
/* 1183 */       return this.index - 1;
/*      */     }
/*      */     
/*      */     public int nextEntry() {
/* 1187 */       if (!hasNext()) throw new NoSuchElementException(); 
/* 1188 */       this.curr = this.next;
/* 1189 */       this.next = (int)Reference2BooleanLinkedOpenHashMap.this.link[this.curr];
/* 1190 */       this.prev = this.curr;
/* 1191 */       if (this.index >= 0) this.index++; 
/* 1192 */       return this.curr;
/*      */     }
/*      */     
/*      */     public int previousEntry() {
/* 1196 */       if (!hasPrevious()) throw new NoSuchElementException(); 
/* 1197 */       this.curr = this.prev;
/* 1198 */       this.prev = (int)(Reference2BooleanLinkedOpenHashMap.this.link[this.curr] >>> 32L);
/* 1199 */       this.next = this.curr;
/* 1200 */       if (this.index >= 0) this.index--; 
/* 1201 */       return this.curr;
/*      */     }
/*      */     
/*      */     public void forEachRemaining(ConsumerType action) {
/* 1205 */       while (hasNext()) {
/* 1206 */         this.curr = this.next;
/* 1207 */         this.next = (int)Reference2BooleanLinkedOpenHashMap.this.link[this.curr];
/* 1208 */         this.prev = this.curr;
/* 1209 */         if (this.index >= 0) this.index++; 
/* 1210 */         acceptOnIndex(action, this.curr);
/*      */       } 
/*      */     }
/*      */     
/*      */     public void remove() {
/* 1215 */       ensureIndexKnown();
/* 1216 */       if (this.curr == -1) throw new IllegalStateException(); 
/* 1217 */       if (this.curr == this.prev)
/*      */       
/*      */       { 
/* 1220 */         this.index--;
/* 1221 */         this.prev = (int)(Reference2BooleanLinkedOpenHashMap.this.link[this.curr] >>> 32L); }
/* 1222 */       else { this.next = (int)Reference2BooleanLinkedOpenHashMap.this.link[this.curr]; }
/* 1223 */        Reference2BooleanLinkedOpenHashMap.this.size--;
/*      */ 
/*      */       
/* 1226 */       if (this.prev == -1) { Reference2BooleanLinkedOpenHashMap.this.first = this.next; }
/* 1227 */       else { Reference2BooleanLinkedOpenHashMap.this.link[this.prev] = Reference2BooleanLinkedOpenHashMap.this.link[this.prev] ^ (Reference2BooleanLinkedOpenHashMap.this.link[this.prev] ^ this.next & 0xFFFFFFFFL) & 0xFFFFFFFFL; }
/* 1228 */        if (this.next == -1) { Reference2BooleanLinkedOpenHashMap.this.last = this.prev; }
/* 1229 */       else { Reference2BooleanLinkedOpenHashMap.this.link[this.next] = Reference2BooleanLinkedOpenHashMap.this.link[this.next] ^ (Reference2BooleanLinkedOpenHashMap.this.link[this.next] ^ (this.prev & 0xFFFFFFFFL) << 32L) & 0xFFFFFFFF00000000L; }
/* 1230 */        int pos = this.curr;
/* 1231 */       this.curr = -1;
/* 1232 */       if (pos == Reference2BooleanLinkedOpenHashMap.this.n) {
/* 1233 */         Reference2BooleanLinkedOpenHashMap.this.containsNullKey = false;
/* 1234 */         Reference2BooleanLinkedOpenHashMap.this.key[Reference2BooleanLinkedOpenHashMap.this.n] = null;
/*      */       } else {
/*      */         
/* 1237 */         K[] key = Reference2BooleanLinkedOpenHashMap.this.key; while (true) {
/*      */           K curr;
/*      */           int last;
/* 1240 */           pos = (last = pos) + 1 & Reference2BooleanLinkedOpenHashMap.this.mask;
/*      */           while (true) {
/* 1242 */             if ((curr = key[pos]) == null) {
/* 1243 */               key[last] = null;
/*      */               return;
/*      */             } 
/* 1246 */             int slot = HashCommon.mix(System.identityHashCode(curr)) & Reference2BooleanLinkedOpenHashMap.this.mask;
/* 1247 */             if ((last <= pos) ? (last >= slot || slot > pos) : (last >= slot && slot > pos))
/* 1248 */               break;  pos = pos + 1 & Reference2BooleanLinkedOpenHashMap.this.mask;
/*      */           } 
/* 1250 */           key[last] = curr;
/* 1251 */           Reference2BooleanLinkedOpenHashMap.this.value[last] = Reference2BooleanLinkedOpenHashMap.this.value[pos];
/* 1252 */           if (this.next == pos) this.next = last; 
/* 1253 */           if (this.prev == pos) this.prev = last; 
/* 1254 */           Reference2BooleanLinkedOpenHashMap.this.fixPointers(pos, last);
/*      */         } 
/*      */       } 
/*      */     }
/*      */     
/*      */     public int skip(int n) {
/* 1260 */       int i = n;
/* 1261 */       for (; i-- != 0 && hasNext(); nextEntry());
/* 1262 */       return n - i - 1;
/*      */     }
/*      */     
/*      */     public int back(int n) {
/* 1266 */       int i = n;
/* 1267 */       for (; i-- != 0 && hasPrevious(); previousEntry());
/* 1268 */       return n - i - 1;
/*      */     }
/*      */     
/*      */     public void set(Reference2BooleanMap.Entry<K> ok) {
/* 1272 */       throw new UnsupportedOperationException();
/*      */     }
/*      */     
/*      */     public void add(Reference2BooleanMap.Entry<K> ok) {
/* 1276 */       throw new UnsupportedOperationException();
/*      */     }
/*      */     
/*      */     abstract void acceptOnIndex(ConsumerType param1ConsumerType, int param1Int); }
/*      */   
/*      */   private final class EntryIterator extends MapIterator<Consumer<? super Reference2BooleanMap.Entry<K>>> implements ObjectListIterator<Reference2BooleanMap.Entry<K>> {
/*      */     private Reference2BooleanLinkedOpenHashMap<K>.MapEntry entry;
/*      */     
/*      */     public EntryIterator() {}
/*      */     
/*      */     public EntryIterator(K from) {
/* 1287 */       super(from);
/*      */     }
/*      */ 
/*      */ 
/*      */     
/*      */     final void acceptOnIndex(Consumer<? super Reference2BooleanMap.Entry<K>> action, int index) {
/* 1293 */       action.accept(new Reference2BooleanLinkedOpenHashMap.MapEntry(index));
/*      */     }
/*      */ 
/*      */     
/*      */     public Reference2BooleanLinkedOpenHashMap<K>.MapEntry next() {
/* 1298 */       return this.entry = new Reference2BooleanLinkedOpenHashMap.MapEntry(nextEntry());
/*      */     }
/*      */ 
/*      */     
/*      */     public Reference2BooleanLinkedOpenHashMap<K>.MapEntry previous() {
/* 1303 */       return this.entry = new Reference2BooleanLinkedOpenHashMap.MapEntry(previousEntry());
/*      */     }
/*      */ 
/*      */     
/*      */     public void remove() {
/* 1308 */       super.remove();
/* 1309 */       this.entry.index = -1;
/*      */     }
/*      */   }
/*      */   
/*      */   private final class FastEntryIterator extends MapIterator<Consumer<? super Reference2BooleanMap.Entry<K>>> implements ObjectListIterator<Reference2BooleanMap.Entry<K>> {
/* 1314 */     final Reference2BooleanLinkedOpenHashMap<K>.MapEntry entry = new Reference2BooleanLinkedOpenHashMap.MapEntry();
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public FastEntryIterator(K from) {
/* 1320 */       super(from);
/*      */     }
/*      */ 
/*      */ 
/*      */     
/*      */     final void acceptOnIndex(Consumer<? super Reference2BooleanMap.Entry<K>> action, int index) {
/* 1326 */       this.entry.index = index;
/* 1327 */       action.accept(this.entry);
/*      */     }
/*      */ 
/*      */     
/*      */     public Reference2BooleanLinkedOpenHashMap<K>.MapEntry next() {
/* 1332 */       this.entry.index = nextEntry();
/* 1333 */       return this.entry;
/*      */     }
/*      */ 
/*      */     
/*      */     public Reference2BooleanLinkedOpenHashMap<K>.MapEntry previous() {
/* 1338 */       this.entry.index = previousEntry();
/* 1339 */       return this.entry;
/*      */     }
/*      */     
/*      */     public FastEntryIterator() {} }
/*      */   
/*      */   private final class MapEntrySet extends AbstractObjectSortedSet<Reference2BooleanMap.Entry<K>> implements Reference2BooleanSortedMap.FastSortedEntrySet<K> {
/*      */     private static final int SPLITERATOR_CHARACTERISTICS = 81;
/*      */     
/*      */     public ObjectBidirectionalIterator<Reference2BooleanMap.Entry<K>> iterator() {
/* 1348 */       return new Reference2BooleanLinkedOpenHashMap.EntryIterator();
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
/*      */     public ObjectSpliterator<Reference2BooleanMap.Entry<K>> spliterator() {
/* 1366 */       return ObjectSpliterators.asSpliterator(iterator(), Size64.sizeOf(Reference2BooleanLinkedOpenHashMap.this), 81);
/*      */     }
/*      */ 
/*      */     
/*      */     public Comparator<? super Reference2BooleanMap.Entry<K>> comparator() {
/* 1371 */       return null;
/*      */     }
/*      */ 
/*      */     
/*      */     public ObjectSortedSet<Reference2BooleanMap.Entry<K>> subSet(Reference2BooleanMap.Entry<K> fromElement, Reference2BooleanMap.Entry<K> toElement) {
/* 1376 */       throw new UnsupportedOperationException();
/*      */     }
/*      */ 
/*      */     
/*      */     public ObjectSortedSet<Reference2BooleanMap.Entry<K>> headSet(Reference2BooleanMap.Entry<K> toElement) {
/* 1381 */       throw new UnsupportedOperationException();
/*      */     }
/*      */ 
/*      */     
/*      */     public ObjectSortedSet<Reference2BooleanMap.Entry<K>> tailSet(Reference2BooleanMap.Entry<K> fromElement) {
/* 1386 */       throw new UnsupportedOperationException();
/*      */     }
/*      */ 
/*      */     
/*      */     public Reference2BooleanMap.Entry<K> first() {
/* 1391 */       if (Reference2BooleanLinkedOpenHashMap.this.size == 0) throw new NoSuchElementException(); 
/* 1392 */       return new Reference2BooleanLinkedOpenHashMap.MapEntry(Reference2BooleanLinkedOpenHashMap.this.first);
/*      */     }
/*      */ 
/*      */     
/*      */     public Reference2BooleanMap.Entry<K> last() {
/* 1397 */       if (Reference2BooleanLinkedOpenHashMap.this.size == 0) throw new NoSuchElementException(); 
/* 1398 */       return new Reference2BooleanLinkedOpenHashMap.MapEntry(Reference2BooleanLinkedOpenHashMap.this.last);
/*      */     }
/*      */ 
/*      */ 
/*      */     
/*      */     public boolean contains(Object o) {
/* 1404 */       if (!(o instanceof Map.Entry)) return false; 
/* 1405 */       Map.Entry<?, ?> e = (Map.Entry<?, ?>)o;
/* 1406 */       if (e.getValue() == null || !(e.getValue() instanceof Boolean)) return false; 
/* 1407 */       K k = (K)e.getKey();
/* 1408 */       boolean v = ((Boolean)e.getValue()).booleanValue();
/* 1409 */       if (k == null) return (Reference2BooleanLinkedOpenHashMap.this.containsNullKey && Reference2BooleanLinkedOpenHashMap.this.value[Reference2BooleanLinkedOpenHashMap.this.n] == v);
/*      */       
/* 1411 */       K[] key = Reference2BooleanLinkedOpenHashMap.this.key;
/*      */       K curr;
/*      */       int pos;
/* 1414 */       if ((curr = key[pos = HashCommon.mix(System.identityHashCode(k)) & Reference2BooleanLinkedOpenHashMap.this.mask]) == null) return false; 
/* 1415 */       if (k == curr) return (Reference2BooleanLinkedOpenHashMap.this.value[pos] == v);
/*      */       
/*      */       while (true) {
/* 1418 */         if ((curr = key[pos = pos + 1 & Reference2BooleanLinkedOpenHashMap.this.mask]) == null) return false; 
/* 1419 */         if (k == curr) return (Reference2BooleanLinkedOpenHashMap.this.value[pos] == v);
/*      */       
/*      */       } 
/*      */     }
/*      */ 
/*      */     
/*      */     public boolean remove(Object o) {
/* 1426 */       if (!(o instanceof Map.Entry)) return false; 
/* 1427 */       Map.Entry<?, ?> e = (Map.Entry<?, ?>)o;
/* 1428 */       if (e.getValue() == null || !(e.getValue() instanceof Boolean)) return false; 
/* 1429 */       K k = (K)e.getKey();
/* 1430 */       boolean v = ((Boolean)e.getValue()).booleanValue();
/* 1431 */       if (k == null) {
/* 1432 */         if (Reference2BooleanLinkedOpenHashMap.this.containsNullKey && Reference2BooleanLinkedOpenHashMap.this.value[Reference2BooleanLinkedOpenHashMap.this.n] == v) {
/* 1433 */           Reference2BooleanLinkedOpenHashMap.this.removeNullEntry();
/* 1434 */           return true;
/*      */         } 
/* 1436 */         return false;
/*      */       } 
/*      */       
/* 1439 */       K[] key = Reference2BooleanLinkedOpenHashMap.this.key;
/*      */       K curr;
/*      */       int pos;
/* 1442 */       if ((curr = key[pos = HashCommon.mix(System.identityHashCode(k)) & Reference2BooleanLinkedOpenHashMap.this.mask]) == null) return false; 
/* 1443 */       if (curr == k) {
/* 1444 */         if (Reference2BooleanLinkedOpenHashMap.this.value[pos] == v) {
/* 1445 */           Reference2BooleanLinkedOpenHashMap.this.removeEntry(pos);
/* 1446 */           return true;
/*      */         } 
/* 1448 */         return false;
/*      */       } 
/*      */       while (true) {
/* 1451 */         if ((curr = key[pos = pos + 1 & Reference2BooleanLinkedOpenHashMap.this.mask]) == null) return false; 
/* 1452 */         if (curr == k && 
/* 1453 */           Reference2BooleanLinkedOpenHashMap.this.value[pos] == v) {
/* 1454 */           Reference2BooleanLinkedOpenHashMap.this.removeEntry(pos);
/* 1455 */           return true;
/*      */         } 
/*      */       } 
/*      */     }
/*      */ 
/*      */ 
/*      */     
/*      */     public int size() {
/* 1463 */       return Reference2BooleanLinkedOpenHashMap.this.size;
/*      */     }
/*      */ 
/*      */     
/*      */     public void clear() {
/* 1468 */       Reference2BooleanLinkedOpenHashMap.this.clear();
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
/*      */     public ObjectListIterator<Reference2BooleanMap.Entry<K>> iterator(Reference2BooleanMap.Entry<K> from) {
/* 1481 */       return new Reference2BooleanLinkedOpenHashMap.EntryIterator(from.getKey());
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public ObjectListIterator<Reference2BooleanMap.Entry<K>> fastIterator() {
/* 1492 */       return new Reference2BooleanLinkedOpenHashMap.FastEntryIterator();
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
/*      */     public ObjectListIterator<Reference2BooleanMap.Entry<K>> fastIterator(Reference2BooleanMap.Entry<K> from) {
/* 1505 */       return new Reference2BooleanLinkedOpenHashMap.FastEntryIterator(from.getKey());
/*      */     }
/*      */ 
/*      */ 
/*      */     
/*      */     public void forEach(Consumer<? super Reference2BooleanMap.Entry<K>> consumer) {
/* 1511 */       for (int i = Reference2BooleanLinkedOpenHashMap.this.size, next = Reference2BooleanLinkedOpenHashMap.this.first; i-- != 0; ) {
/* 1512 */         int curr = next;
/* 1513 */         next = (int)Reference2BooleanLinkedOpenHashMap.this.link[curr];
/* 1514 */         consumer.accept(new Reference2BooleanLinkedOpenHashMap.MapEntry(curr));
/*      */       } 
/*      */     }
/*      */ 
/*      */ 
/*      */     
/*      */     public void fastForEach(Consumer<? super Reference2BooleanMap.Entry<K>> consumer) {
/* 1521 */       Reference2BooleanLinkedOpenHashMap<K>.MapEntry entry = new Reference2BooleanLinkedOpenHashMap.MapEntry();
/* 1522 */       for (int i = Reference2BooleanLinkedOpenHashMap.this.size, next = Reference2BooleanLinkedOpenHashMap.this.first; i-- != 0; ) {
/* 1523 */         entry.index = next;
/* 1524 */         next = (int)Reference2BooleanLinkedOpenHashMap.this.link[next];
/* 1525 */         consumer.accept(entry);
/*      */       } 
/*      */     }
/*      */   }
/*      */ 
/*      */   
/*      */   public Reference2BooleanSortedMap.FastSortedEntrySet<K> reference2BooleanEntrySet() {
/* 1532 */     if (this.entries == null) this.entries = new MapEntrySet(); 
/* 1533 */     return this.entries;
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
/* 1546 */       super(k);
/*      */     }
/*      */ 
/*      */     
/*      */     public K previous() {
/* 1551 */       return Reference2BooleanLinkedOpenHashMap.this.key[previousEntry()];
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
/* 1563 */       action.accept(Reference2BooleanLinkedOpenHashMap.this.key[index]);
/*      */     }
/*      */ 
/*      */     
/*      */     public K next() {
/* 1568 */       return Reference2BooleanLinkedOpenHashMap.this.key[nextEntry()];
/*      */     } }
/*      */   
/*      */   private final class KeySet extends AbstractReferenceSortedSet<K> {
/*      */     private static final int SPLITERATOR_CHARACTERISTICS = 81;
/*      */     
/*      */     private KeySet() {}
/*      */     
/*      */     public ObjectListIterator<K> iterator(K from) {
/* 1577 */       return new Reference2BooleanLinkedOpenHashMap.KeyIterator(from);
/*      */     }
/*      */ 
/*      */     
/*      */     public ObjectListIterator<K> iterator() {
/* 1582 */       return new Reference2BooleanLinkedOpenHashMap.KeyIterator();
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public ObjectSpliterator<K> spliterator() {
/* 1592 */       return ObjectSpliterators.asSpliterator(iterator(), Size64.sizeOf(Reference2BooleanLinkedOpenHashMap.this), 81);
/*      */     }
/*      */ 
/*      */ 
/*      */     
/*      */     public void forEach(Consumer<? super K> consumer) {
/* 1598 */       for (int i = Reference2BooleanLinkedOpenHashMap.this.size, next = Reference2BooleanLinkedOpenHashMap.this.first; i-- != 0; ) {
/* 1599 */         int curr = next;
/* 1600 */         next = (int)Reference2BooleanLinkedOpenHashMap.this.link[curr];
/* 1601 */         consumer.accept(Reference2BooleanLinkedOpenHashMap.this.key[curr]);
/*      */       } 
/*      */     }
/*      */ 
/*      */     
/*      */     public int size() {
/* 1607 */       return Reference2BooleanLinkedOpenHashMap.this.size;
/*      */     }
/*      */ 
/*      */     
/*      */     public boolean contains(Object k) {
/* 1612 */       return Reference2BooleanLinkedOpenHashMap.this.containsKey(k);
/*      */     }
/*      */ 
/*      */     
/*      */     public boolean remove(Object k) {
/* 1617 */       int oldSize = Reference2BooleanLinkedOpenHashMap.this.size;
/* 1618 */       Reference2BooleanLinkedOpenHashMap.this.removeBoolean(k);
/* 1619 */       return (Reference2BooleanLinkedOpenHashMap.this.size != oldSize);
/*      */     }
/*      */ 
/*      */     
/*      */     public void clear() {
/* 1624 */       Reference2BooleanLinkedOpenHashMap.this.clear();
/*      */     }
/*      */ 
/*      */     
/*      */     public K first() {
/* 1629 */       if (Reference2BooleanLinkedOpenHashMap.this.size == 0) throw new NoSuchElementException(); 
/* 1630 */       return Reference2BooleanLinkedOpenHashMap.this.key[Reference2BooleanLinkedOpenHashMap.this.first];
/*      */     }
/*      */ 
/*      */     
/*      */     public K last() {
/* 1635 */       if (Reference2BooleanLinkedOpenHashMap.this.size == 0) throw new NoSuchElementException(); 
/* 1636 */       return Reference2BooleanLinkedOpenHashMap.this.key[Reference2BooleanLinkedOpenHashMap.this.last];
/*      */     }
/*      */ 
/*      */     
/*      */     public Comparator<? super K> comparator() {
/* 1641 */       return null;
/*      */     }
/*      */ 
/*      */     
/*      */     public ReferenceSortedSet<K> tailSet(K from) {
/* 1646 */       throw new UnsupportedOperationException();
/*      */     }
/*      */ 
/*      */     
/*      */     public ReferenceSortedSet<K> headSet(K to) {
/* 1651 */       throw new UnsupportedOperationException();
/*      */     }
/*      */ 
/*      */     
/*      */     public ReferenceSortedSet<K> subSet(K from, K to) {
/* 1656 */       throw new UnsupportedOperationException();
/*      */     }
/*      */   }
/*      */ 
/*      */   
/*      */   public ReferenceSortedSet<K> keySet() {
/* 1662 */     if (this.keys == null) this.keys = new KeySet(); 
/* 1663 */     return this.keys;
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
/* 1677 */       return Reference2BooleanLinkedOpenHashMap.this.value[previousEntry()];
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
/* 1689 */       action.accept(Reference2BooleanLinkedOpenHashMap.this.value[index]);
/*      */     }
/*      */ 
/*      */     
/*      */     public boolean nextBoolean() {
/* 1694 */       return Reference2BooleanLinkedOpenHashMap.this.value[nextEntry()];
/*      */     }
/*      */   }
/*      */ 
/*      */   
/*      */   public BooleanCollection values() {
/* 1700 */     if (this.values == null) this.values = (BooleanCollection)new AbstractBooleanCollection()
/*      */         {
/*      */           private static final int SPLITERATOR_CHARACTERISTICS = 336;
/*      */           
/*      */           public BooleanIterator iterator() {
/* 1705 */             return (BooleanIterator)new Reference2BooleanLinkedOpenHashMap.ValueIterator();
/*      */           }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */           
/*      */           public BooleanSpliterator spliterator() {
/* 1715 */             return BooleanSpliterators.asSpliterator(iterator(), Size64.sizeOf(Reference2BooleanLinkedOpenHashMap.this), 336);
/*      */           }
/*      */ 
/*      */ 
/*      */           
/*      */           public void forEach(BooleanConsumer consumer) {
/* 1721 */             for (int i = Reference2BooleanLinkedOpenHashMap.this.size, next = Reference2BooleanLinkedOpenHashMap.this.first; i-- != 0; ) {
/* 1722 */               int curr = next;
/* 1723 */               next = (int)Reference2BooleanLinkedOpenHashMap.this.link[curr];
/* 1724 */               consumer.accept(Reference2BooleanLinkedOpenHashMap.this.value[curr]);
/*      */             } 
/*      */           }
/*      */ 
/*      */           
/*      */           public int size() {
/* 1730 */             return Reference2BooleanLinkedOpenHashMap.this.size;
/*      */           }
/*      */ 
/*      */           
/*      */           public boolean contains(boolean v) {
/* 1735 */             return Reference2BooleanLinkedOpenHashMap.this.containsValue(v);
/*      */           }
/*      */ 
/*      */           
/*      */           public void clear() {
/* 1740 */             Reference2BooleanLinkedOpenHashMap.this.clear();
/*      */           }
/*      */         }; 
/* 1743 */     return this.values;
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
/* 1760 */     return trim(this.size);
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
/* 1782 */     int l = HashCommon.nextPowerOfTwo((int)Math.ceil((n / this.f)));
/* 1783 */     if (l >= this.n || this.size > HashCommon.maxFill(l, this.f)) return true; 
/*      */     try {
/* 1785 */       rehash(l);
/* 1786 */     } catch (OutOfMemoryError cantDoIt) {
/* 1787 */       return false;
/*      */     } 
/* 1789 */     return true;
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
/* 1804 */     K[] key = this.key;
/* 1805 */     boolean[] value = this.value;
/* 1806 */     int mask = newN - 1;
/* 1807 */     K[] newKey = (K[])new Object[newN + 1];
/* 1808 */     boolean[] newValue = new boolean[newN + 1];
/* 1809 */     int i = this.first, prev = -1, newPrev = -1;
/* 1810 */     long[] link = this.link;
/* 1811 */     long[] newLink = new long[newN + 1];
/* 1812 */     this.first = -1;
/* 1813 */     for (int j = this.size; j-- != 0; ) {
/* 1814 */       int pos; if (key[i] == null) { pos = newN; }
/*      */       else
/* 1816 */       { pos = HashCommon.mix(System.identityHashCode(key[i])) & mask;
/* 1817 */         for (; newKey[pos] != null; pos = pos + 1 & mask); }
/*      */       
/* 1819 */       newKey[pos] = key[i];
/* 1820 */       newValue[pos] = value[i];
/* 1821 */       if (prev != -1) {
/* 1822 */         newLink[newPrev] = newLink[newPrev] ^ (newLink[newPrev] ^ pos & 0xFFFFFFFFL) & 0xFFFFFFFFL;
/* 1823 */         newLink[pos] = newLink[pos] ^ (newLink[pos] ^ (newPrev & 0xFFFFFFFFL) << 32L) & 0xFFFFFFFF00000000L;
/* 1824 */         newPrev = pos;
/*      */       } else {
/* 1826 */         newPrev = this.first = pos;
/*      */         
/* 1828 */         newLink[pos] = -1L;
/*      */       } 
/* 1830 */       int t = i;
/* 1831 */       i = (int)link[i];
/* 1832 */       prev = t;
/*      */     } 
/* 1834 */     this.link = newLink;
/* 1835 */     this.last = newPrev;
/* 1836 */     if (newPrev != -1)
/*      */     {
/* 1838 */       newLink[newPrev] = newLink[newPrev] | 0xFFFFFFFFL; } 
/* 1839 */     this.n = newN;
/* 1840 */     this.mask = mask;
/* 1841 */     this.maxFill = HashCommon.maxFill(this.n, this.f);
/* 1842 */     this.key = newKey;
/* 1843 */     this.value = newValue;
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
/*      */   public Reference2BooleanLinkedOpenHashMap<K> clone() {
/*      */     Reference2BooleanLinkedOpenHashMap<K> c;
/*      */     try {
/* 1860 */       c = (Reference2BooleanLinkedOpenHashMap<K>)super.clone();
/* 1861 */     } catch (CloneNotSupportedException cantHappen) {
/* 1862 */       throw new InternalError();
/*      */     } 
/* 1864 */     c.keys = null;
/* 1865 */     c.values = null;
/* 1866 */     c.entries = null;
/* 1867 */     c.containsNullKey = this.containsNullKey;
/* 1868 */     c.key = (K[])this.key.clone();
/* 1869 */     c.value = (boolean[])this.value.clone();
/* 1870 */     c.link = (long[])this.link.clone();
/* 1871 */     return c;
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
/* 1885 */     int h = 0;
/* 1886 */     for (int j = realSize(), i = 0, t = 0; j-- != 0; ) {
/* 1887 */       for (; this.key[i] == null; i++);
/* 1888 */       if (this != this.key[i]) t = System.identityHashCode(this.key[i]); 
/* 1889 */       t ^= this.value[i] ? 1231 : 1237;
/* 1890 */       h += t;
/* 1891 */       i++;
/*      */     } 
/*      */     
/* 1894 */     if (this.containsNullKey) h += this.value[this.n] ? 1231 : 1237; 
/* 1895 */     return h;
/*      */   }
/*      */   
/*      */   private void writeObject(ObjectOutputStream s) throws IOException {
/* 1899 */     K[] key = this.key;
/* 1900 */     boolean[] value = this.value;
/* 1901 */     EntryIterator i = new EntryIterator();
/* 1902 */     s.defaultWriteObject();
/* 1903 */     for (int j = this.size; j-- != 0; ) {
/* 1904 */       int e = i.nextEntry();
/* 1905 */       s.writeObject(key[e]);
/* 1906 */       s.writeBoolean(value[e]);
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   private void readObject(ObjectInputStream s) throws IOException, ClassNotFoundException {
/* 1912 */     s.defaultReadObject();
/* 1913 */     this.n = HashCommon.arraySize(this.size, this.f);
/* 1914 */     this.maxFill = HashCommon.maxFill(this.n, this.f);
/* 1915 */     this.mask = this.n - 1;
/* 1916 */     K[] key = this.key = (K[])new Object[this.n + 1];
/* 1917 */     boolean[] value = this.value = new boolean[this.n + 1];
/* 1918 */     long[] link = this.link = new long[this.n + 1];
/* 1919 */     int prev = -1;
/* 1920 */     this.first = this.last = -1;
/*      */ 
/*      */     
/* 1923 */     for (int i = this.size; i-- != 0; ) {
/* 1924 */       int pos; K k = (K)s.readObject();
/* 1925 */       boolean v = s.readBoolean();
/* 1926 */       if (k == null) {
/* 1927 */         pos = this.n;
/* 1928 */         this.containsNullKey = true;
/*      */       } else {
/* 1930 */         pos = HashCommon.mix(System.identityHashCode(k)) & this.mask;
/* 1931 */         for (; key[pos] != null; pos = pos + 1 & this.mask);
/*      */       } 
/* 1933 */       key[pos] = k;
/* 1934 */       value[pos] = v;
/* 1935 */       if (this.first != -1) {
/* 1936 */         link[prev] = link[prev] ^ (link[prev] ^ pos & 0xFFFFFFFFL) & 0xFFFFFFFFL;
/* 1937 */         link[pos] = link[pos] ^ (link[pos] ^ (prev & 0xFFFFFFFFL) << 32L) & 0xFFFFFFFF00000000L;
/* 1938 */         prev = pos; continue;
/*      */       } 
/* 1940 */       prev = this.first = pos;
/*      */       
/* 1942 */       link[pos] = link[pos] | 0xFFFFFFFF00000000L;
/*      */     } 
/*      */     
/* 1945 */     this.last = prev;
/* 1946 */     if (prev != -1)
/*      */     {
/* 1948 */       link[prev] = link[prev] | 0xFFFFFFFFL;
/*      */     }
/*      */   }
/*      */   
/*      */   private void checkTable() {}
/*      */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\i\\unimi\dsi\fastutil\objects\Reference2BooleanLinkedOpenHashMap.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */