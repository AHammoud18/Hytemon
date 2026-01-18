/*      */ package it.unimi.dsi.fastutil.floats;
/*      */ 
/*      */ import it.unimi.dsi.fastutil.Hash;
/*      */ import it.unimi.dsi.fastutil.HashCommon;
/*      */ import it.unimi.dsi.fastutil.Size64;
/*      */ import it.unimi.dsi.fastutil.longs.AbstractLongCollection;
/*      */ import it.unimi.dsi.fastutil.longs.LongCollection;
/*      */ import it.unimi.dsi.fastutil.longs.LongIterator;
/*      */ import it.unimi.dsi.fastutil.longs.LongListIterator;
/*      */ import it.unimi.dsi.fastutil.longs.LongSpliterator;
/*      */ import it.unimi.dsi.fastutil.longs.LongSpliterators;
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
/*      */ import java.util.function.DoubleFunction;
/*      */ import java.util.function.DoubleToLongFunction;
/*      */ import java.util.function.LongConsumer;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ public class Float2LongLinkedOpenHashMap
/*      */   extends AbstractFloat2LongSortedMap
/*      */   implements Serializable, Cloneable, Hash
/*      */ {
/*      */   private static final long serialVersionUID = 0L;
/*      */   private static final boolean ASSERTS = false;
/*      */   protected transient float[] key;
/*      */   protected transient long[] value;
/*      */   protected transient int mask;
/*      */   protected transient boolean containsNullKey;
/*  107 */   protected transient int first = -1;
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*  112 */   protected transient int last = -1;
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
/*      */   protected transient Float2LongSortedMap.FastSortedEntrySet entries;
/*      */ 
/*      */   
/*      */   protected transient FloatSortedSet keys;
/*      */ 
/*      */   
/*      */   protected transient LongCollection values;
/*      */ 
/*      */ 
/*      */   
/*      */   public Float2LongLinkedOpenHashMap(int expected, float f) {
/*  147 */     if (f <= 0.0F || f >= 1.0F) throw new IllegalArgumentException("Load factor must be greater than 0 and smaller than 1"); 
/*  148 */     if (expected < 0) throw new IllegalArgumentException("The expected number of elements must be nonnegative"); 
/*  149 */     this.f = f;
/*  150 */     this.minN = this.n = HashCommon.arraySize(expected, f);
/*  151 */     this.mask = this.n - 1;
/*  152 */     this.maxFill = HashCommon.maxFill(this.n, f);
/*  153 */     this.key = new float[this.n + 1];
/*  154 */     this.value = new long[this.n + 1];
/*  155 */     this.link = new long[this.n + 1];
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Float2LongLinkedOpenHashMap(int expected) {
/*  164 */     this(expected, 0.75F);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Float2LongLinkedOpenHashMap() {
/*  172 */     this(16, 0.75F);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Float2LongLinkedOpenHashMap(Map<? extends Float, ? extends Long> m, float f) {
/*  182 */     this(m.size(), f);
/*  183 */     putAll(m);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Float2LongLinkedOpenHashMap(Map<? extends Float, ? extends Long> m) {
/*  192 */     this(m, 0.75F);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Float2LongLinkedOpenHashMap(Float2LongMap m, float f) {
/*  202 */     this(m.size(), f);
/*  203 */     putAll(m);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Float2LongLinkedOpenHashMap(Float2LongMap m) {
/*  213 */     this(m, 0.75F);
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
/*      */   public Float2LongLinkedOpenHashMap(float[] k, long[] v, float f) {
/*  225 */     this(k.length, f);
/*  226 */     if (k.length != v.length) throw new IllegalArgumentException("The key array and the value array have different lengths (" + k.length + " and " + v.length + ")"); 
/*  227 */     for (int i = 0; i < k.length; ) { put(k[i], v[i]); i++; }
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
/*      */   public Float2LongLinkedOpenHashMap(float[] k, long[] v) {
/*  239 */     this(k, v, 0.75F);
/*      */   }
/*      */   
/*      */   private int realSize() {
/*  243 */     return this.containsNullKey ? (this.size - 1) : this.size;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void ensureCapacity(int capacity) {
/*  253 */     int needed = HashCommon.arraySize(capacity, this.f);
/*  254 */     if (needed > this.n) rehash(needed); 
/*      */   }
/*      */   
/*      */   private void tryCapacity(long capacity) {
/*  258 */     int needed = (int)Math.min(1073741824L, Math.max(2L, HashCommon.nextPowerOfTwo((long)Math.ceil(((float)capacity / this.f)))));
/*  259 */     if (needed > this.n) rehash(needed); 
/*      */   }
/*      */   
/*      */   private long removeEntry(int pos) {
/*  263 */     long oldValue = this.value[pos];
/*  264 */     this.size--;
/*  265 */     fixPointers(pos);
/*  266 */     shiftKeys(pos);
/*  267 */     if (this.n > this.minN && this.size < this.maxFill / 4 && this.n > 16) rehash(this.n / 2); 
/*  268 */     return oldValue;
/*      */   }
/*      */   
/*      */   private long removeNullEntry() {
/*  272 */     this.containsNullKey = false;
/*  273 */     long oldValue = this.value[this.n];
/*  274 */     this.size--;
/*  275 */     fixPointers(this.n);
/*  276 */     if (this.n > this.minN && this.size < this.maxFill / 4 && this.n > 16) rehash(this.n / 2); 
/*  277 */     return oldValue;
/*      */   }
/*      */ 
/*      */   
/*      */   public void putAll(Map<? extends Float, ? extends Long> m) {
/*  282 */     if (this.f <= 0.5D) { ensureCapacity(m.size()); }
/*  283 */     else { tryCapacity((size() + m.size())); }
/*      */     
/*  285 */     super.putAll(m);
/*      */   }
/*      */   
/*      */   private int find(float k) {
/*  289 */     if (Float.floatToIntBits(k) == 0) return this.containsNullKey ? this.n : -(this.n + 1);
/*      */     
/*  291 */     float[] key = this.key;
/*      */     float curr;
/*      */     int pos;
/*  294 */     if (Float.floatToIntBits(curr = key[pos = HashCommon.mix(HashCommon.float2int(k)) & this.mask]) == 0) return -(pos + 1); 
/*  295 */     if (Float.floatToIntBits(k) == Float.floatToIntBits(curr)) return pos;
/*      */     
/*      */     while (true) {
/*  298 */       if (Float.floatToIntBits(curr = key[pos = pos + 1 & this.mask]) == 0) return -(pos + 1); 
/*  299 */       if (Float.floatToIntBits(k) == Float.floatToIntBits(curr)) return pos; 
/*      */     } 
/*      */   }
/*      */   
/*      */   private void insert(int pos, float k, long v) {
/*  304 */     if (pos == this.n) this.containsNullKey = true; 
/*  305 */     this.key[pos] = k;
/*  306 */     this.value[pos] = v;
/*  307 */     if (this.size == 0) {
/*  308 */       this.first = this.last = pos;
/*      */       
/*  310 */       this.link[pos] = -1L;
/*      */     } else {
/*  312 */       this.link[this.last] = this.link[this.last] ^ (this.link[this.last] ^ pos & 0xFFFFFFFFL) & 0xFFFFFFFFL;
/*  313 */       this.link[pos] = (this.last & 0xFFFFFFFFL) << 32L | 0xFFFFFFFFL;
/*  314 */       this.last = pos;
/*      */     } 
/*  316 */     if (this.size++ >= this.maxFill) rehash(HashCommon.arraySize(this.size + 1, this.f));
/*      */   
/*      */   }
/*      */ 
/*      */   
/*      */   public long put(float k, long v) {
/*  322 */     int pos = find(k);
/*  323 */     if (pos < 0) {
/*  324 */       insert(-pos - 1, k, v);
/*  325 */       return this.defRetValue;
/*      */     } 
/*  327 */     long oldValue = this.value[pos];
/*  328 */     this.value[pos] = v;
/*  329 */     return oldValue;
/*      */   }
/*      */   
/*      */   private long addToValue(int pos, long incr) {
/*  333 */     long oldValue = this.value[pos];
/*  334 */     this.value[pos] = oldValue + incr;
/*  335 */     return oldValue;
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
/*      */   public long addTo(float k, long incr) {
/*      */     int pos;
/*  353 */     if (Float.floatToIntBits(k) == 0) {
/*  354 */       if (this.containsNullKey) return addToValue(this.n, incr); 
/*  355 */       pos = this.n;
/*  356 */       this.containsNullKey = true;
/*      */     } else {
/*      */       
/*  359 */       float[] key = this.key;
/*      */       float curr;
/*  361 */       if (Float.floatToIntBits(curr = key[pos = HashCommon.mix(HashCommon.float2int(k)) & this.mask]) != 0) {
/*  362 */         if (Float.floatToIntBits(curr) == Float.floatToIntBits(k)) return addToValue(pos, incr); 
/*  363 */         while (Float.floatToIntBits(curr = key[pos = pos + 1 & this.mask]) != 0) { if (Float.floatToIntBits(curr) == Float.floatToIntBits(k)) return addToValue(pos, incr);  }
/*      */       
/*      */       } 
/*  366 */     }  this.key[pos] = k;
/*  367 */     this.value[pos] = this.defRetValue + incr;
/*  368 */     if (this.size == 0) {
/*  369 */       this.first = this.last = pos;
/*      */       
/*  371 */       this.link[pos] = -1L;
/*      */     } else {
/*  373 */       this.link[this.last] = this.link[this.last] ^ (this.link[this.last] ^ pos & 0xFFFFFFFFL) & 0xFFFFFFFFL;
/*  374 */       this.link[pos] = (this.last & 0xFFFFFFFFL) << 32L | 0xFFFFFFFFL;
/*  375 */       this.last = pos;
/*      */     } 
/*  377 */     if (this.size++ >= this.maxFill) rehash(HashCommon.arraySize(this.size + 1, this.f));
/*      */     
/*  379 */     return this.defRetValue;
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
/*  392 */     float[] key = this.key; while (true) {
/*      */       float curr; int last;
/*  394 */       pos = (last = pos) + 1 & this.mask;
/*      */       while (true) {
/*  396 */         if (Float.floatToIntBits(curr = key[pos]) == 0) {
/*  397 */           key[last] = 0.0F;
/*      */           return;
/*      */         } 
/*  400 */         int slot = HashCommon.mix(HashCommon.float2int(curr)) & this.mask;
/*  401 */         if ((last <= pos) ? (last >= slot || slot > pos) : (last >= slot && slot > pos))
/*  402 */           break;  pos = pos + 1 & this.mask;
/*      */       } 
/*  404 */       key[last] = curr;
/*  405 */       this.value[last] = this.value[pos];
/*  406 */       fixPointers(pos, last);
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public long remove(float k) {
/*  413 */     if (Float.floatToIntBits(k) == 0) {
/*  414 */       if (this.containsNullKey) return removeNullEntry(); 
/*  415 */       return this.defRetValue;
/*      */     } 
/*      */     
/*  418 */     float[] key = this.key;
/*      */     float curr;
/*      */     int pos;
/*  421 */     if (Float.floatToIntBits(curr = key[pos = HashCommon.mix(HashCommon.float2int(k)) & this.mask]) == 0) return this.defRetValue; 
/*  422 */     if (Float.floatToIntBits(k) == Float.floatToIntBits(curr)) return removeEntry(pos); 
/*      */     while (true) {
/*  424 */       if (Float.floatToIntBits(curr = key[pos = pos + 1 & this.mask]) == 0) return this.defRetValue; 
/*  425 */       if (Float.floatToIntBits(k) == Float.floatToIntBits(curr)) return removeEntry(pos); 
/*      */     } 
/*      */   }
/*      */   
/*      */   private long setValue(int pos, long v) {
/*  430 */     long oldValue = this.value[pos];
/*  431 */     this.value[pos] = v;
/*  432 */     return oldValue;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public long removeFirstLong() {
/*  442 */     if (this.size == 0) throw new NoSuchElementException(); 
/*  443 */     int pos = this.first;
/*      */     
/*  445 */     if (this.size == 1) { this.first = this.last = -1; }
/*      */     else
/*  447 */     { this.first = (int)this.link[pos];
/*  448 */       if (0 <= this.first)
/*      */       {
/*  450 */         this.link[this.first] = this.link[this.first] | 0xFFFFFFFF00000000L;
/*      */       } }
/*      */     
/*  453 */     this.size--;
/*  454 */     long v = this.value[pos];
/*  455 */     if (pos == this.n)
/*  456 */     { this.containsNullKey = false; }
/*  457 */     else { shiftKeys(pos); }
/*  458 */      if (this.n > this.minN && this.size < this.maxFill / 4 && this.n > 16) rehash(this.n / 2); 
/*  459 */     return v;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public long removeLastLong() {
/*  469 */     if (this.size == 0) throw new NoSuchElementException(); 
/*  470 */     int pos = this.last;
/*      */     
/*  472 */     if (this.size == 1) { this.first = this.last = -1; }
/*      */     else
/*  474 */     { this.last = (int)(this.link[pos] >>> 32L);
/*  475 */       if (0 <= this.last)
/*      */       {
/*  477 */         this.link[this.last] = this.link[this.last] | 0xFFFFFFFFL;
/*      */       } }
/*      */     
/*  480 */     this.size--;
/*  481 */     long v = this.value[pos];
/*  482 */     if (pos == this.n)
/*  483 */     { this.containsNullKey = false; }
/*  484 */     else { shiftKeys(pos); }
/*  485 */      if (this.n > this.minN && this.size < this.maxFill / 4 && this.n > 16) rehash(this.n / 2); 
/*  486 */     return v;
/*      */   }
/*      */   
/*      */   private void moveIndexToFirst(int i) {
/*  490 */     if (this.size == 1 || this.first == i)
/*  491 */       return;  if (this.last == i) {
/*  492 */       this.last = (int)(this.link[i] >>> 32L);
/*      */       
/*  494 */       this.link[this.last] = this.link[this.last] | 0xFFFFFFFFL;
/*      */     } else {
/*  496 */       long linki = this.link[i];
/*  497 */       int prev = (int)(linki >>> 32L);
/*  498 */       int next = (int)linki;
/*  499 */       this.link[prev] = this.link[prev] ^ (this.link[prev] ^ linki & 0xFFFFFFFFL) & 0xFFFFFFFFL;
/*  500 */       this.link[next] = this.link[next] ^ (this.link[next] ^ linki & 0xFFFFFFFF00000000L) & 0xFFFFFFFF00000000L;
/*      */     } 
/*  502 */     this.link[this.first] = this.link[this.first] ^ (this.link[this.first] ^ (i & 0xFFFFFFFFL) << 32L) & 0xFFFFFFFF00000000L;
/*  503 */     this.link[i] = 0xFFFFFFFF00000000L | this.first & 0xFFFFFFFFL;
/*  504 */     this.first = i;
/*      */   }
/*      */   
/*      */   private void moveIndexToLast(int i) {
/*  508 */     if (this.size == 1 || this.last == i)
/*  509 */       return;  if (this.first == i) {
/*  510 */       this.first = (int)this.link[i];
/*      */       
/*  512 */       this.link[this.first] = this.link[this.first] | 0xFFFFFFFF00000000L;
/*      */     } else {
/*  514 */       long linki = this.link[i];
/*  515 */       int prev = (int)(linki >>> 32L);
/*  516 */       int next = (int)linki;
/*  517 */       this.link[prev] = this.link[prev] ^ (this.link[prev] ^ linki & 0xFFFFFFFFL) & 0xFFFFFFFFL;
/*  518 */       this.link[next] = this.link[next] ^ (this.link[next] ^ linki & 0xFFFFFFFF00000000L) & 0xFFFFFFFF00000000L;
/*      */     } 
/*  520 */     this.link[this.last] = this.link[this.last] ^ (this.link[this.last] ^ i & 0xFFFFFFFFL) & 0xFFFFFFFFL;
/*  521 */     this.link[i] = (this.last & 0xFFFFFFFFL) << 32L | 0xFFFFFFFFL;
/*  522 */     this.last = i;
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
/*      */   public long getAndMoveToFirst(float k) {
/*  534 */     if (Float.floatToIntBits(k) == 0) {
/*  535 */       if (this.containsNullKey) {
/*  536 */         moveIndexToFirst(this.n);
/*  537 */         return this.value[this.n];
/*      */       } 
/*  539 */       return this.defRetValue;
/*      */     } 
/*      */     
/*  542 */     float[] key = this.key;
/*      */     float curr;
/*      */     int pos;
/*  545 */     if (Float.floatToIntBits(curr = key[pos = HashCommon.mix(HashCommon.float2int(k)) & this.mask]) == 0) return this.defRetValue; 
/*  546 */     if (Float.floatToIntBits(k) == Float.floatToIntBits(curr)) {
/*  547 */       moveIndexToFirst(pos);
/*  548 */       return this.value[pos];
/*      */     } 
/*      */     
/*      */     while (true) {
/*  552 */       if (Float.floatToIntBits(curr = key[pos = pos + 1 & this.mask]) == 0) return this.defRetValue; 
/*  553 */       if (Float.floatToIntBits(k) == Float.floatToIntBits(curr)) {
/*  554 */         moveIndexToFirst(pos);
/*  555 */         return this.value[pos];
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
/*      */   public long getAndMoveToLast(float k) {
/*  569 */     if (Float.floatToIntBits(k) == 0) {
/*  570 */       if (this.containsNullKey) {
/*  571 */         moveIndexToLast(this.n);
/*  572 */         return this.value[this.n];
/*      */       } 
/*  574 */       return this.defRetValue;
/*      */     } 
/*      */     
/*  577 */     float[] key = this.key;
/*      */     float curr;
/*      */     int pos;
/*  580 */     if (Float.floatToIntBits(curr = key[pos = HashCommon.mix(HashCommon.float2int(k)) & this.mask]) == 0) return this.defRetValue; 
/*  581 */     if (Float.floatToIntBits(k) == Float.floatToIntBits(curr)) {
/*  582 */       moveIndexToLast(pos);
/*  583 */       return this.value[pos];
/*      */     } 
/*      */     
/*      */     while (true) {
/*  587 */       if (Float.floatToIntBits(curr = key[pos = pos + 1 & this.mask]) == 0) return this.defRetValue; 
/*  588 */       if (Float.floatToIntBits(k) == Float.floatToIntBits(curr)) {
/*  589 */         moveIndexToLast(pos);
/*  590 */         return this.value[pos];
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
/*      */   public long putAndMoveToFirst(float k, long v) {
/*      */     int pos;
/*  606 */     if (Float.floatToIntBits(k) == 0) {
/*  607 */       if (this.containsNullKey) {
/*  608 */         moveIndexToFirst(this.n);
/*  609 */         return setValue(this.n, v);
/*      */       } 
/*  611 */       this.containsNullKey = true;
/*  612 */       pos = this.n;
/*      */     } else {
/*      */       
/*  615 */       float[] key = this.key;
/*      */       float curr;
/*  617 */       if (Float.floatToIntBits(curr = key[pos = HashCommon.mix(HashCommon.float2int(k)) & this.mask]) != 0) {
/*  618 */         if (Float.floatToIntBits(curr) == Float.floatToIntBits(k)) {
/*  619 */           moveIndexToFirst(pos);
/*  620 */           return setValue(pos, v);
/*      */         } 
/*  622 */         while (Float.floatToIntBits(curr = key[pos = pos + 1 & this.mask]) != 0) { if (Float.floatToIntBits(curr) == Float.floatToIntBits(k)) {
/*  623 */             moveIndexToFirst(pos);
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
/*  635 */       this.link[this.first] = this.link[this.first] ^ (this.link[this.first] ^ (pos & 0xFFFFFFFFL) << 32L) & 0xFFFFFFFF00000000L;
/*  636 */       this.link[pos] = 0xFFFFFFFF00000000L | this.first & 0xFFFFFFFFL;
/*  637 */       this.first = pos;
/*      */     } 
/*  639 */     if (this.size++ >= this.maxFill) rehash(HashCommon.arraySize(this.size, this.f));
/*      */     
/*  641 */     return this.defRetValue;
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
/*      */   public long putAndMoveToLast(float k, long v) {
/*      */     int pos;
/*  655 */     if (Float.floatToIntBits(k) == 0) {
/*  656 */       if (this.containsNullKey) {
/*  657 */         moveIndexToLast(this.n);
/*  658 */         return setValue(this.n, v);
/*      */       } 
/*  660 */       this.containsNullKey = true;
/*  661 */       pos = this.n;
/*      */     } else {
/*      */       
/*  664 */       float[] key = this.key;
/*      */       float curr;
/*  666 */       if (Float.floatToIntBits(curr = key[pos = HashCommon.mix(HashCommon.float2int(k)) & this.mask]) != 0) {
/*  667 */         if (Float.floatToIntBits(curr) == Float.floatToIntBits(k)) {
/*  668 */           moveIndexToLast(pos);
/*  669 */           return setValue(pos, v);
/*      */         } 
/*  671 */         while (Float.floatToIntBits(curr = key[pos = pos + 1 & this.mask]) != 0) { if (Float.floatToIntBits(curr) == Float.floatToIntBits(k)) {
/*  672 */             moveIndexToLast(pos);
/*  673 */             return setValue(pos, v);
/*      */           }  }
/*      */       
/*      */       } 
/*  677 */     }  this.key[pos] = k;
/*  678 */     this.value[pos] = v;
/*  679 */     if (this.size == 0) {
/*  680 */       this.first = this.last = pos;
/*      */       
/*  682 */       this.link[pos] = -1L;
/*      */     } else {
/*  684 */       this.link[this.last] = this.link[this.last] ^ (this.link[this.last] ^ pos & 0xFFFFFFFFL) & 0xFFFFFFFFL;
/*  685 */       this.link[pos] = (this.last & 0xFFFFFFFFL) << 32L | 0xFFFFFFFFL;
/*  686 */       this.last = pos;
/*      */     } 
/*  688 */     if (this.size++ >= this.maxFill) rehash(HashCommon.arraySize(this.size, this.f));
/*      */     
/*  690 */     return this.defRetValue;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public long get(float k) {
/*  696 */     if (Float.floatToIntBits(k) == 0) return this.containsNullKey ? this.value[this.n] : this.defRetValue;
/*      */     
/*  698 */     float[] key = this.key;
/*      */     float curr;
/*      */     int pos;
/*  701 */     if (Float.floatToIntBits(curr = key[pos = HashCommon.mix(HashCommon.float2int(k)) & this.mask]) == 0) return this.defRetValue; 
/*  702 */     if (Float.floatToIntBits(k) == Float.floatToIntBits(curr)) return this.value[pos];
/*      */     
/*      */     while (true) {
/*  705 */       if (Float.floatToIntBits(curr = key[pos = pos + 1 & this.mask]) == 0) return this.defRetValue; 
/*  706 */       if (Float.floatToIntBits(k) == Float.floatToIntBits(curr)) return this.value[pos];
/*      */     
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   public boolean containsKey(float k) {
/*  713 */     if (Float.floatToIntBits(k) == 0) return this.containsNullKey;
/*      */     
/*  715 */     float[] key = this.key;
/*      */     float curr;
/*      */     int pos;
/*  718 */     if (Float.floatToIntBits(curr = key[pos = HashCommon.mix(HashCommon.float2int(k)) & this.mask]) == 0) return false; 
/*  719 */     if (Float.floatToIntBits(k) == Float.floatToIntBits(curr)) return true;
/*      */     
/*      */     while (true) {
/*  722 */       if (Float.floatToIntBits(curr = key[pos = pos + 1 & this.mask]) == 0) return false; 
/*  723 */       if (Float.floatToIntBits(k) == Float.floatToIntBits(curr)) return true;
/*      */     
/*      */     } 
/*      */   }
/*      */   
/*      */   public boolean containsValue(long v) {
/*  729 */     long[] value = this.value;
/*  730 */     float[] key = this.key;
/*  731 */     if (this.containsNullKey && value[this.n] == v) return true; 
/*  732 */     for (int i = this.n; i-- != 0;) { if (Float.floatToIntBits(key[i]) != 0 && value[i] == v) return true;  }
/*  733 */      return false;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public long getOrDefault(float k, long defaultValue) {
/*  740 */     if (Float.floatToIntBits(k) == 0) return this.containsNullKey ? this.value[this.n] : defaultValue;
/*      */     
/*  742 */     float[] key = this.key;
/*      */     float curr;
/*      */     int pos;
/*  745 */     if (Float.floatToIntBits(curr = key[pos = HashCommon.mix(HashCommon.float2int(k)) & this.mask]) == 0) return defaultValue; 
/*  746 */     if (Float.floatToIntBits(k) == Float.floatToIntBits(curr)) return this.value[pos];
/*      */     
/*      */     while (true) {
/*  749 */       if (Float.floatToIntBits(curr = key[pos = pos + 1 & this.mask]) == 0) return defaultValue; 
/*  750 */       if (Float.floatToIntBits(k) == Float.floatToIntBits(curr)) return this.value[pos];
/*      */     
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   public long putIfAbsent(float k, long v) {
/*  757 */     int pos = find(k);
/*  758 */     if (pos >= 0) return this.value[pos]; 
/*  759 */     insert(-pos - 1, k, v);
/*  760 */     return this.defRetValue;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean remove(float k, long v) {
/*  767 */     if (Float.floatToIntBits(k) == 0) {
/*  768 */       if (this.containsNullKey && v == this.value[this.n]) {
/*  769 */         removeNullEntry();
/*  770 */         return true;
/*      */       } 
/*  772 */       return false;
/*      */     } 
/*      */     
/*  775 */     float[] key = this.key;
/*      */     float curr;
/*      */     int pos;
/*  778 */     if (Float.floatToIntBits(curr = key[pos = HashCommon.mix(HashCommon.float2int(k)) & this.mask]) == 0) return false; 
/*  779 */     if (Float.floatToIntBits(k) == Float.floatToIntBits(curr) && v == this.value[pos]) {
/*  780 */       removeEntry(pos);
/*  781 */       return true;
/*      */     } 
/*      */     while (true) {
/*  784 */       if (Float.floatToIntBits(curr = key[pos = pos + 1 & this.mask]) == 0) return false; 
/*  785 */       if (Float.floatToIntBits(k) == Float.floatToIntBits(curr) && v == this.value[pos]) {
/*  786 */         removeEntry(pos);
/*  787 */         return true;
/*      */       } 
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean replace(float k, long oldValue, long v) {
/*  795 */     int pos = find(k);
/*  796 */     if (pos < 0 || oldValue != this.value[pos]) return false; 
/*  797 */     this.value[pos] = v;
/*  798 */     return true;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public long replace(float k, long v) {
/*  804 */     int pos = find(k);
/*  805 */     if (pos < 0) return this.defRetValue; 
/*  806 */     long oldValue = this.value[pos];
/*  807 */     this.value[pos] = v;
/*  808 */     return oldValue;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public long computeIfAbsent(float k, DoubleToLongFunction mappingFunction) {
/*  814 */     Objects.requireNonNull(mappingFunction);
/*  815 */     int pos = find(k);
/*  816 */     if (pos >= 0) return this.value[pos]; 
/*  817 */     long newValue = mappingFunction.applyAsLong(k);
/*  818 */     insert(-pos - 1, k, newValue);
/*  819 */     return newValue;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public long computeIfAbsent(float key, Float2LongFunction mappingFunction) {
/*  825 */     Objects.requireNonNull(mappingFunction);
/*  826 */     int pos = find(key);
/*  827 */     if (pos >= 0) return this.value[pos]; 
/*  828 */     if (!mappingFunction.containsKey(key)) return this.defRetValue; 
/*  829 */     long newValue = mappingFunction.get(key);
/*  830 */     insert(-pos - 1, key, newValue);
/*  831 */     return newValue;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public long computeIfAbsentNullable(float k, DoubleFunction<? extends Long> mappingFunction) {
/*  837 */     Objects.requireNonNull(mappingFunction);
/*  838 */     int pos = find(k);
/*  839 */     if (pos >= 0) return this.value[pos]; 
/*  840 */     Long newValue = mappingFunction.apply(k);
/*  841 */     if (newValue == null) return this.defRetValue; 
/*  842 */     long v = newValue.longValue();
/*  843 */     insert(-pos - 1, k, v);
/*  844 */     return v;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public long computeIfPresent(float k, BiFunction<? super Float, ? super Long, ? extends Long> remappingFunction) {
/*  850 */     Objects.requireNonNull(remappingFunction);
/*  851 */     int pos = find(k);
/*  852 */     if (pos < 0) return this.defRetValue; 
/*  853 */     Long newValue = remappingFunction.apply(Float.valueOf(k), Long.valueOf(this.value[pos]));
/*  854 */     if (newValue == null) {
/*  855 */       if (Float.floatToIntBits(k) == 0) { removeNullEntry(); }
/*  856 */       else { removeEntry(pos); }
/*  857 */        return this.defRetValue;
/*      */     } 
/*  859 */     this.value[pos] = newValue.longValue(); return newValue.longValue();
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public long compute(float k, BiFunction<? super Float, ? super Long, ? extends Long> remappingFunction) {
/*  865 */     Objects.requireNonNull(remappingFunction);
/*  866 */     int pos = find(k);
/*  867 */     Long newValue = remappingFunction.apply(Float.valueOf(k), (pos >= 0) ? Long.valueOf(this.value[pos]) : null);
/*  868 */     if (newValue == null) {
/*  869 */       if (pos >= 0)
/*  870 */         if (Float.floatToIntBits(k) == 0) { removeNullEntry(); }
/*  871 */         else { removeEntry(pos); }
/*      */          
/*  873 */       return this.defRetValue;
/*      */     } 
/*  875 */     long newVal = newValue.longValue();
/*  876 */     if (pos < 0) {
/*  877 */       insert(-pos - 1, k, newVal);
/*  878 */       return newVal;
/*      */     } 
/*  880 */     this.value[pos] = newVal; return newVal;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public long merge(float k, long v, BiFunction<? super Long, ? super Long, ? extends Long> remappingFunction) {
/*  886 */     Objects.requireNonNull(remappingFunction);
/*      */     
/*  888 */     int pos = find(k);
/*  889 */     if (pos < 0) {
/*  890 */       if (pos < 0) { insert(-pos - 1, k, v); }
/*  891 */       else { this.value[pos] = v; }
/*  892 */        return v;
/*      */     } 
/*  894 */     Long newValue = remappingFunction.apply(Long.valueOf(this.value[pos]), Long.valueOf(v));
/*  895 */     if (newValue == null) {
/*  896 */       if (Float.floatToIntBits(k) == 0) { removeNullEntry(); }
/*  897 */       else { removeEntry(pos); }
/*  898 */        return this.defRetValue;
/*      */     } 
/*  900 */     this.value[pos] = newValue.longValue(); return newValue.longValue();
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
/*  911 */     if (this.size == 0)
/*  912 */       return;  this.size = 0;
/*  913 */     this.containsNullKey = false;
/*  914 */     Arrays.fill(this.key, 0.0F);
/*  915 */     this.first = this.last = -1;
/*      */   }
/*      */ 
/*      */   
/*      */   public int size() {
/*  920 */     return this.size;
/*      */   }
/*      */ 
/*      */   
/*      */   public boolean isEmpty() {
/*  925 */     return (this.size == 0);
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   final class MapEntry
/*      */     implements Float2LongMap.Entry, Map.Entry<Float, Long>, FloatLongPair
/*      */   {
/*      */     int index;
/*      */ 
/*      */ 
/*      */     
/*      */     MapEntry(int index) {
/*  938 */       this.index = index;
/*      */     }
/*      */ 
/*      */     
/*      */     MapEntry() {}
/*      */ 
/*      */     
/*      */     public float getFloatKey() {
/*  946 */       return Float2LongLinkedOpenHashMap.this.key[this.index];
/*      */     }
/*      */ 
/*      */     
/*      */     public float leftFloat() {
/*  951 */       return Float2LongLinkedOpenHashMap.this.key[this.index];
/*      */     }
/*      */ 
/*      */     
/*      */     public long getLongValue() {
/*  956 */       return Float2LongLinkedOpenHashMap.this.value[this.index];
/*      */     }
/*      */ 
/*      */     
/*      */     public long rightLong() {
/*  961 */       return Float2LongLinkedOpenHashMap.this.value[this.index];
/*      */     }
/*      */ 
/*      */     
/*      */     public long setValue(long v) {
/*  966 */       long oldValue = Float2LongLinkedOpenHashMap.this.value[this.index];
/*  967 */       Float2LongLinkedOpenHashMap.this.value[this.index] = v;
/*  968 */       return oldValue;
/*      */     }
/*      */ 
/*      */     
/*      */     public FloatLongPair right(long v) {
/*  973 */       Float2LongLinkedOpenHashMap.this.value[this.index] = v;
/*  974 */       return this;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     @Deprecated
/*      */     public Float getKey() {
/*  985 */       return Float.valueOf(Float2LongLinkedOpenHashMap.this.key[this.index]);
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     @Deprecated
/*      */     public Long getValue() {
/*  996 */       return Long.valueOf(Float2LongLinkedOpenHashMap.this.value[this.index]);
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     @Deprecated
/*      */     public Long setValue(Long v) {
/* 1007 */       return Long.valueOf(setValue(v.longValue()));
/*      */     }
/*      */ 
/*      */ 
/*      */     
/*      */     public boolean equals(Object o) {
/* 1013 */       if (!(o instanceof Map.Entry)) return false; 
/* 1014 */       Map.Entry<Float, Long> e = (Map.Entry<Float, Long>)o;
/* 1015 */       return (Float.floatToIntBits(Float2LongLinkedOpenHashMap.this.key[this.index]) == Float.floatToIntBits(((Float)e.getKey()).floatValue()) && Float2LongLinkedOpenHashMap.this.value[this.index] == ((Long)e.getValue()).longValue());
/*      */     }
/*      */ 
/*      */     
/*      */     public int hashCode() {
/* 1020 */       return HashCommon.float2int(Float2LongLinkedOpenHashMap.this.key[this.index]) ^ HashCommon.long2int(Float2LongLinkedOpenHashMap.this.value[this.index]);
/*      */     }
/*      */ 
/*      */     
/*      */     public String toString() {
/* 1025 */       return Float2LongLinkedOpenHashMap.this.key[this.index] + "=>" + Float2LongLinkedOpenHashMap.this.value[this.index];
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
/* 1036 */     if (this.size == 0) {
/* 1037 */       this.first = this.last = -1;
/*      */       return;
/*      */     } 
/* 1040 */     if (this.first == i) {
/* 1041 */       this.first = (int)this.link[i];
/* 1042 */       if (0 <= this.first)
/*      */       {
/* 1044 */         this.link[this.first] = this.link[this.first] | 0xFFFFFFFF00000000L;
/*      */       }
/*      */       return;
/*      */     } 
/* 1048 */     if (this.last == i) {
/* 1049 */       this.last = (int)(this.link[i] >>> 32L);
/* 1050 */       if (0 <= this.last)
/*      */       {
/* 1052 */         this.link[this.last] = this.link[this.last] | 0xFFFFFFFFL;
/*      */       }
/*      */       return;
/*      */     } 
/* 1056 */     long linki = this.link[i];
/* 1057 */     int prev = (int)(linki >>> 32L);
/* 1058 */     int next = (int)linki;
/* 1059 */     this.link[prev] = this.link[prev] ^ (this.link[prev] ^ linki & 0xFFFFFFFFL) & 0xFFFFFFFFL;
/* 1060 */     this.link[next] = this.link[next] ^ (this.link[next] ^ linki & 0xFFFFFFFF00000000L) & 0xFFFFFFFF00000000L;
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
/* 1072 */     if (this.size == 1) {
/* 1073 */       this.first = this.last = d;
/*      */       
/* 1075 */       this.link[d] = -1L;
/*      */       return;
/*      */     } 
/* 1078 */     if (this.first == s) {
/* 1079 */       this.first = d;
/* 1080 */       this.link[(int)this.link[s]] = this.link[(int)this.link[s]] ^ (this.link[(int)this.link[s]] ^ (d & 0xFFFFFFFFL) << 32L) & 0xFFFFFFFF00000000L;
/* 1081 */       this.link[d] = this.link[s];
/*      */       return;
/*      */     } 
/* 1084 */     if (this.last == s) {
/* 1085 */       this.last = d;
/* 1086 */       this.link[(int)(this.link[s] >>> 32L)] = this.link[(int)(this.link[s] >>> 32L)] ^ (this.link[(int)(this.link[s] >>> 32L)] ^ d & 0xFFFFFFFFL) & 0xFFFFFFFFL;
/* 1087 */       this.link[d] = this.link[s];
/*      */       return;
/*      */     } 
/* 1090 */     long links = this.link[s];
/* 1091 */     int prev = (int)(links >>> 32L);
/* 1092 */     int next = (int)links;
/* 1093 */     this.link[prev] = this.link[prev] ^ (this.link[prev] ^ d & 0xFFFFFFFFL) & 0xFFFFFFFFL;
/* 1094 */     this.link[next] = this.link[next] ^ (this.link[next] ^ (d & 0xFFFFFFFFL) << 32L) & 0xFFFFFFFF00000000L;
/* 1095 */     this.link[d] = links;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public float firstFloatKey() {
/* 1105 */     if (this.size == 0) throw new NoSuchElementException(); 
/* 1106 */     return this.key[this.first];
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public float lastFloatKey() {
/* 1116 */     if (this.size == 0) throw new NoSuchElementException(); 
/* 1117 */     return this.key[this.last];
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Float2LongSortedMap tailMap(float from) {
/* 1127 */     throw new UnsupportedOperationException();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Float2LongSortedMap headMap(float to) {
/* 1137 */     throw new UnsupportedOperationException();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Float2LongSortedMap subMap(float from, float to) {
/* 1147 */     throw new UnsupportedOperationException();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public FloatComparator comparator() {
/* 1157 */     return null;
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
/* 1172 */     int prev = -1;
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 1177 */     int next = -1;
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 1182 */     int curr = -1;
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 1187 */     int index = -1;
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     protected MapIterator() {
/* 1193 */       this.next = Float2LongLinkedOpenHashMap.this.first;
/* 1194 */       this.index = 0;
/*      */     }
/*      */     
/*      */     private MapIterator(float from) {
/* 1198 */       if (Float.floatToIntBits(from) == 0) {
/* 1199 */         if (Float2LongLinkedOpenHashMap.this.containsNullKey) {
/* 1200 */           this.next = (int)Float2LongLinkedOpenHashMap.this.link[Float2LongLinkedOpenHashMap.this.n];
/* 1201 */           this.prev = Float2LongLinkedOpenHashMap.this.n; return;
/*      */         } 
/* 1203 */         throw new NoSuchElementException("The key " + from + " does not belong to this map.");
/*      */       } 
/* 1205 */       if (Float.floatToIntBits(Float2LongLinkedOpenHashMap.this.key[Float2LongLinkedOpenHashMap.this.last]) == Float.floatToIntBits(from)) {
/* 1206 */         this.prev = Float2LongLinkedOpenHashMap.this.last;
/* 1207 */         this.index = Float2LongLinkedOpenHashMap.this.size;
/*      */         
/*      */         return;
/*      */       } 
/* 1211 */       int pos = HashCommon.mix(HashCommon.float2int(from)) & Float2LongLinkedOpenHashMap.this.mask;
/*      */       
/* 1213 */       while (Float.floatToIntBits(Float2LongLinkedOpenHashMap.this.key[pos]) != 0) {
/* 1214 */         if (Float.floatToIntBits(Float2LongLinkedOpenHashMap.this.key[pos]) == Float.floatToIntBits(from)) {
/*      */           
/* 1216 */           this.next = (int)Float2LongLinkedOpenHashMap.this.link[pos];
/* 1217 */           this.prev = pos;
/*      */           return;
/*      */         } 
/* 1220 */         pos = pos + 1 & Float2LongLinkedOpenHashMap.this.mask;
/*      */       } 
/* 1222 */       throw new NoSuchElementException("The key " + from + " does not belong to this map.");
/*      */     }
/*      */     
/*      */     public boolean hasNext() {
/* 1226 */       return (this.next != -1);
/*      */     }
/*      */     
/*      */     public boolean hasPrevious() {
/* 1230 */       return (this.prev != -1);
/*      */     }
/*      */     
/*      */     private final void ensureIndexKnown() {
/* 1234 */       if (this.index >= 0)
/* 1235 */         return;  if (this.prev == -1) {
/* 1236 */         this.index = 0;
/*      */         return;
/*      */       } 
/* 1239 */       if (this.next == -1) {
/* 1240 */         this.index = Float2LongLinkedOpenHashMap.this.size;
/*      */         return;
/*      */       } 
/* 1243 */       int pos = Float2LongLinkedOpenHashMap.this.first;
/* 1244 */       this.index = 1;
/* 1245 */       while (pos != this.prev) {
/* 1246 */         pos = (int)Float2LongLinkedOpenHashMap.this.link[pos];
/* 1247 */         this.index++;
/*      */       } 
/*      */     }
/*      */     
/*      */     public int nextIndex() {
/* 1252 */       ensureIndexKnown();
/* 1253 */       return this.index;
/*      */     }
/*      */     
/*      */     public int previousIndex() {
/* 1257 */       ensureIndexKnown();
/* 1258 */       return this.index - 1;
/*      */     }
/*      */     
/*      */     public int nextEntry() {
/* 1262 */       if (!hasNext()) throw new NoSuchElementException(); 
/* 1263 */       this.curr = this.next;
/* 1264 */       this.next = (int)Float2LongLinkedOpenHashMap.this.link[this.curr];
/* 1265 */       this.prev = this.curr;
/* 1266 */       if (this.index >= 0) this.index++; 
/* 1267 */       return this.curr;
/*      */     }
/*      */     
/*      */     public int previousEntry() {
/* 1271 */       if (!hasPrevious()) throw new NoSuchElementException(); 
/* 1272 */       this.curr = this.prev;
/* 1273 */       this.prev = (int)(Float2LongLinkedOpenHashMap.this.link[this.curr] >>> 32L);
/* 1274 */       this.next = this.curr;
/* 1275 */       if (this.index >= 0) this.index--; 
/* 1276 */       return this.curr;
/*      */     }
/*      */     
/*      */     public void forEachRemaining(ConsumerType action) {
/* 1280 */       while (hasNext()) {
/* 1281 */         this.curr = this.next;
/* 1282 */         this.next = (int)Float2LongLinkedOpenHashMap.this.link[this.curr];
/* 1283 */         this.prev = this.curr;
/* 1284 */         if (this.index >= 0) this.index++; 
/* 1285 */         acceptOnIndex(action, this.curr);
/*      */       } 
/*      */     }
/*      */     
/*      */     public void remove() {
/* 1290 */       ensureIndexKnown();
/* 1291 */       if (this.curr == -1) throw new IllegalStateException(); 
/* 1292 */       if (this.curr == this.prev)
/*      */       
/*      */       { 
/* 1295 */         this.index--;
/* 1296 */         this.prev = (int)(Float2LongLinkedOpenHashMap.this.link[this.curr] >>> 32L); }
/* 1297 */       else { this.next = (int)Float2LongLinkedOpenHashMap.this.link[this.curr]; }
/* 1298 */        Float2LongLinkedOpenHashMap.this.size--;
/*      */ 
/*      */       
/* 1301 */       if (this.prev == -1) { Float2LongLinkedOpenHashMap.this.first = this.next; }
/* 1302 */       else { Float2LongLinkedOpenHashMap.this.link[this.prev] = Float2LongLinkedOpenHashMap.this.link[this.prev] ^ (Float2LongLinkedOpenHashMap.this.link[this.prev] ^ this.next & 0xFFFFFFFFL) & 0xFFFFFFFFL; }
/* 1303 */        if (this.next == -1) { Float2LongLinkedOpenHashMap.this.last = this.prev; }
/* 1304 */       else { Float2LongLinkedOpenHashMap.this.link[this.next] = Float2LongLinkedOpenHashMap.this.link[this.next] ^ (Float2LongLinkedOpenHashMap.this.link[this.next] ^ (this.prev & 0xFFFFFFFFL) << 32L) & 0xFFFFFFFF00000000L; }
/* 1305 */        int pos = this.curr;
/* 1306 */       this.curr = -1;
/* 1307 */       if (pos == Float2LongLinkedOpenHashMap.this.n) {
/* 1308 */         Float2LongLinkedOpenHashMap.this.containsNullKey = false;
/*      */       } else {
/*      */         
/* 1311 */         float[] key = Float2LongLinkedOpenHashMap.this.key; while (true) {
/*      */           float curr;
/*      */           int last;
/* 1314 */           pos = (last = pos) + 1 & Float2LongLinkedOpenHashMap.this.mask;
/*      */           while (true) {
/* 1316 */             if (Float.floatToIntBits(curr = key[pos]) == 0) {
/* 1317 */               key[last] = 0.0F;
/*      */               return;
/*      */             } 
/* 1320 */             int slot = HashCommon.mix(HashCommon.float2int(curr)) & Float2LongLinkedOpenHashMap.this.mask;
/* 1321 */             if ((last <= pos) ? (last >= slot || slot > pos) : (last >= slot && slot > pos))
/* 1322 */               break;  pos = pos + 1 & Float2LongLinkedOpenHashMap.this.mask;
/*      */           } 
/* 1324 */           key[last] = curr;
/* 1325 */           Float2LongLinkedOpenHashMap.this.value[last] = Float2LongLinkedOpenHashMap.this.value[pos];
/* 1326 */           if (this.next == pos) this.next = last; 
/* 1327 */           if (this.prev == pos) this.prev = last; 
/* 1328 */           Float2LongLinkedOpenHashMap.this.fixPointers(pos, last);
/*      */         } 
/*      */       } 
/*      */     }
/*      */     
/*      */     public int skip(int n) {
/* 1334 */       int i = n;
/* 1335 */       for (; i-- != 0 && hasNext(); nextEntry());
/* 1336 */       return n - i - 1;
/*      */     }
/*      */     
/*      */     public int back(int n) {
/* 1340 */       int i = n;
/* 1341 */       for (; i-- != 0 && hasPrevious(); previousEntry());
/* 1342 */       return n - i - 1;
/*      */     }
/*      */     
/*      */     public void set(Float2LongMap.Entry ok) {
/* 1346 */       throw new UnsupportedOperationException();
/*      */     }
/*      */     
/*      */     public void add(Float2LongMap.Entry ok) {
/* 1350 */       throw new UnsupportedOperationException();
/*      */     }
/*      */     
/*      */     abstract void acceptOnIndex(ConsumerType param1ConsumerType, int param1Int); }
/*      */   
/*      */   private final class EntryIterator extends MapIterator<Consumer<? super Float2LongMap.Entry>> implements ObjectListIterator<Float2LongMap.Entry> {
/*      */     private Float2LongLinkedOpenHashMap.MapEntry entry;
/*      */     
/*      */     public EntryIterator() {}
/*      */     
/*      */     public EntryIterator(float from) {
/* 1361 */       super(from);
/*      */     }
/*      */ 
/*      */ 
/*      */     
/*      */     final void acceptOnIndex(Consumer<? super Float2LongMap.Entry> action, int index) {
/* 1367 */       action.accept(new Float2LongLinkedOpenHashMap.MapEntry(index));
/*      */     }
/*      */ 
/*      */     
/*      */     public Float2LongLinkedOpenHashMap.MapEntry next() {
/* 1372 */       return this.entry = new Float2LongLinkedOpenHashMap.MapEntry(nextEntry());
/*      */     }
/*      */ 
/*      */     
/*      */     public Float2LongLinkedOpenHashMap.MapEntry previous() {
/* 1377 */       return this.entry = new Float2LongLinkedOpenHashMap.MapEntry(previousEntry());
/*      */     }
/*      */ 
/*      */     
/*      */     public void remove() {
/* 1382 */       super.remove();
/* 1383 */       this.entry.index = -1;
/*      */     }
/*      */   }
/*      */   
/*      */   private final class FastEntryIterator extends MapIterator<Consumer<? super Float2LongMap.Entry>> implements ObjectListIterator<Float2LongMap.Entry> {
/* 1388 */     final Float2LongLinkedOpenHashMap.MapEntry entry = new Float2LongLinkedOpenHashMap.MapEntry();
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public FastEntryIterator(float from) {
/* 1394 */       super(from);
/*      */     }
/*      */ 
/*      */ 
/*      */     
/*      */     final void acceptOnIndex(Consumer<? super Float2LongMap.Entry> action, int index) {
/* 1400 */       this.entry.index = index;
/* 1401 */       action.accept(this.entry);
/*      */     }
/*      */ 
/*      */     
/*      */     public Float2LongLinkedOpenHashMap.MapEntry next() {
/* 1406 */       this.entry.index = nextEntry();
/* 1407 */       return this.entry;
/*      */     }
/*      */ 
/*      */     
/*      */     public Float2LongLinkedOpenHashMap.MapEntry previous() {
/* 1412 */       this.entry.index = previousEntry();
/* 1413 */       return this.entry;
/*      */     }
/*      */     
/*      */     public FastEntryIterator() {} }
/*      */   
/*      */   private final class MapEntrySet extends AbstractObjectSortedSet<Float2LongMap.Entry> implements Float2LongSortedMap.FastSortedEntrySet {
/*      */     private static final int SPLITERATOR_CHARACTERISTICS = 81;
/*      */     
/*      */     public ObjectBidirectionalIterator<Float2LongMap.Entry> iterator() {
/* 1422 */       return (ObjectBidirectionalIterator<Float2LongMap.Entry>)new Float2LongLinkedOpenHashMap.EntryIterator();
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
/*      */     public ObjectSpliterator<Float2LongMap.Entry> spliterator() {
/* 1440 */       return ObjectSpliterators.asSpliterator((ObjectIterator)iterator(), Size64.sizeOf(Float2LongLinkedOpenHashMap.this), 81);
/*      */     }
/*      */ 
/*      */     
/*      */     public Comparator<? super Float2LongMap.Entry> comparator() {
/* 1445 */       return null;
/*      */     }
/*      */ 
/*      */     
/*      */     public ObjectSortedSet<Float2LongMap.Entry> subSet(Float2LongMap.Entry fromElement, Float2LongMap.Entry toElement) {
/* 1450 */       throw new UnsupportedOperationException();
/*      */     }
/*      */ 
/*      */     
/*      */     public ObjectSortedSet<Float2LongMap.Entry> headSet(Float2LongMap.Entry toElement) {
/* 1455 */       throw new UnsupportedOperationException();
/*      */     }
/*      */ 
/*      */     
/*      */     public ObjectSortedSet<Float2LongMap.Entry> tailSet(Float2LongMap.Entry fromElement) {
/* 1460 */       throw new UnsupportedOperationException();
/*      */     }
/*      */ 
/*      */     
/*      */     public Float2LongMap.Entry first() {
/* 1465 */       if (Float2LongLinkedOpenHashMap.this.size == 0) throw new NoSuchElementException(); 
/* 1466 */       return new Float2LongLinkedOpenHashMap.MapEntry(Float2LongLinkedOpenHashMap.this.first);
/*      */     }
/*      */ 
/*      */     
/*      */     public Float2LongMap.Entry last() {
/* 1471 */       if (Float2LongLinkedOpenHashMap.this.size == 0) throw new NoSuchElementException(); 
/* 1472 */       return new Float2LongLinkedOpenHashMap.MapEntry(Float2LongLinkedOpenHashMap.this.last);
/*      */     }
/*      */ 
/*      */ 
/*      */     
/*      */     public boolean contains(Object o) {
/* 1478 */       if (!(o instanceof Map.Entry)) return false; 
/* 1479 */       Map.Entry<?, ?> e = (Map.Entry<?, ?>)o;
/* 1480 */       if (e.getKey() == null || !(e.getKey() instanceof Float)) return false; 
/* 1481 */       if (e.getValue() == null || !(e.getValue() instanceof Long)) return false; 
/* 1482 */       float k = ((Float)e.getKey()).floatValue();
/* 1483 */       long v = ((Long)e.getValue()).longValue();
/* 1484 */       if (Float.floatToIntBits(k) == 0) return (Float2LongLinkedOpenHashMap.this.containsNullKey && Float2LongLinkedOpenHashMap.this.value[Float2LongLinkedOpenHashMap.this.n] == v);
/*      */       
/* 1486 */       float[] key = Float2LongLinkedOpenHashMap.this.key;
/*      */       float curr;
/*      */       int pos;
/* 1489 */       if (Float.floatToIntBits(curr = key[pos = HashCommon.mix(HashCommon.float2int(k)) & Float2LongLinkedOpenHashMap.this.mask]) == 0) return false; 
/* 1490 */       if (Float.floatToIntBits(k) == Float.floatToIntBits(curr)) return (Float2LongLinkedOpenHashMap.this.value[pos] == v);
/*      */       
/*      */       while (true) {
/* 1493 */         if (Float.floatToIntBits(curr = key[pos = pos + 1 & Float2LongLinkedOpenHashMap.this.mask]) == 0) return false; 
/* 1494 */         if (Float.floatToIntBits(k) == Float.floatToIntBits(curr)) return (Float2LongLinkedOpenHashMap.this.value[pos] == v);
/*      */       
/*      */       } 
/*      */     }
/*      */ 
/*      */     
/*      */     public boolean remove(Object o) {
/* 1501 */       if (!(o instanceof Map.Entry)) return false; 
/* 1502 */       Map.Entry<?, ?> e = (Map.Entry<?, ?>)o;
/* 1503 */       if (e.getKey() == null || !(e.getKey() instanceof Float)) return false; 
/* 1504 */       if (e.getValue() == null || !(e.getValue() instanceof Long)) return false; 
/* 1505 */       float k = ((Float)e.getKey()).floatValue();
/* 1506 */       long v = ((Long)e.getValue()).longValue();
/* 1507 */       if (Float.floatToIntBits(k) == 0) {
/* 1508 */         if (Float2LongLinkedOpenHashMap.this.containsNullKey && Float2LongLinkedOpenHashMap.this.value[Float2LongLinkedOpenHashMap.this.n] == v) {
/* 1509 */           Float2LongLinkedOpenHashMap.this.removeNullEntry();
/* 1510 */           return true;
/*      */         } 
/* 1512 */         return false;
/*      */       } 
/*      */       
/* 1515 */       float[] key = Float2LongLinkedOpenHashMap.this.key;
/*      */       float curr;
/*      */       int pos;
/* 1518 */       if (Float.floatToIntBits(curr = key[pos = HashCommon.mix(HashCommon.float2int(k)) & Float2LongLinkedOpenHashMap.this.mask]) == 0) return false; 
/* 1519 */       if (Float.floatToIntBits(curr) == Float.floatToIntBits(k)) {
/* 1520 */         if (Float2LongLinkedOpenHashMap.this.value[pos] == v) {
/* 1521 */           Float2LongLinkedOpenHashMap.this.removeEntry(pos);
/* 1522 */           return true;
/*      */         } 
/* 1524 */         return false;
/*      */       } 
/*      */       while (true) {
/* 1527 */         if (Float.floatToIntBits(curr = key[pos = pos + 1 & Float2LongLinkedOpenHashMap.this.mask]) == 0) return false; 
/* 1528 */         if (Float.floatToIntBits(curr) == Float.floatToIntBits(k) && 
/* 1529 */           Float2LongLinkedOpenHashMap.this.value[pos] == v) {
/* 1530 */           Float2LongLinkedOpenHashMap.this.removeEntry(pos);
/* 1531 */           return true;
/*      */         } 
/*      */       } 
/*      */     }
/*      */ 
/*      */ 
/*      */     
/*      */     public int size() {
/* 1539 */       return Float2LongLinkedOpenHashMap.this.size;
/*      */     }
/*      */ 
/*      */     
/*      */     public void clear() {
/* 1544 */       Float2LongLinkedOpenHashMap.this.clear();
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
/*      */     public ObjectListIterator<Float2LongMap.Entry> iterator(Float2LongMap.Entry from) {
/* 1557 */       return new Float2LongLinkedOpenHashMap.EntryIterator(from.getFloatKey());
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public ObjectListIterator<Float2LongMap.Entry> fastIterator() {
/* 1568 */       return new Float2LongLinkedOpenHashMap.FastEntryIterator();
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
/*      */     public ObjectListIterator<Float2LongMap.Entry> fastIterator(Float2LongMap.Entry from) {
/* 1581 */       return new Float2LongLinkedOpenHashMap.FastEntryIterator(from.getFloatKey());
/*      */     }
/*      */ 
/*      */ 
/*      */     
/*      */     public void forEach(Consumer<? super Float2LongMap.Entry> consumer) {
/* 1587 */       for (int i = Float2LongLinkedOpenHashMap.this.size, next = Float2LongLinkedOpenHashMap.this.first; i-- != 0; ) {
/* 1588 */         int curr = next;
/* 1589 */         next = (int)Float2LongLinkedOpenHashMap.this.link[curr];
/* 1590 */         consumer.accept(new Float2LongLinkedOpenHashMap.MapEntry(curr));
/*      */       } 
/*      */     }
/*      */ 
/*      */ 
/*      */     
/*      */     public void fastForEach(Consumer<? super Float2LongMap.Entry> consumer) {
/* 1597 */       Float2LongLinkedOpenHashMap.MapEntry entry = new Float2LongLinkedOpenHashMap.MapEntry();
/* 1598 */       for (int i = Float2LongLinkedOpenHashMap.this.size, next = Float2LongLinkedOpenHashMap.this.first; i-- != 0; ) {
/* 1599 */         entry.index = next;
/* 1600 */         next = (int)Float2LongLinkedOpenHashMap.this.link[next];
/* 1601 */         consumer.accept(entry);
/*      */       } 
/*      */     }
/*      */   }
/*      */ 
/*      */   
/*      */   public Float2LongSortedMap.FastSortedEntrySet float2LongEntrySet() {
/* 1608 */     if (this.entries == null) this.entries = new MapEntrySet(); 
/* 1609 */     return this.entries;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private final class KeyIterator
/*      */     extends MapIterator<FloatConsumer>
/*      */     implements FloatListIterator
/*      */   {
/*      */     public KeyIterator(float k) {
/* 1622 */       super(k);
/*      */     }
/*      */ 
/*      */     
/*      */     public float previousFloat() {
/* 1627 */       return Float2LongLinkedOpenHashMap.this.key[previousEntry()];
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
/*      */     final void acceptOnIndex(FloatConsumer action, int index) {
/* 1639 */       action.accept(Float2LongLinkedOpenHashMap.this.key[index]);
/*      */     }
/*      */ 
/*      */     
/*      */     public float nextFloat() {
/* 1644 */       return Float2LongLinkedOpenHashMap.this.key[nextEntry()];
/*      */     } }
/*      */   
/*      */   private final class KeySet extends AbstractFloatSortedSet {
/*      */     private static final int SPLITERATOR_CHARACTERISTICS = 337;
/*      */     
/*      */     private KeySet() {}
/*      */     
/*      */     public FloatListIterator iterator(float from) {
/* 1653 */       return new Float2LongLinkedOpenHashMap.KeyIterator(from);
/*      */     }
/*      */ 
/*      */     
/*      */     public FloatListIterator iterator() {
/* 1658 */       return new Float2LongLinkedOpenHashMap.KeyIterator();
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public FloatSpliterator spliterator() {
/* 1668 */       return FloatSpliterators.asSpliterator(iterator(), Size64.sizeOf(Float2LongLinkedOpenHashMap.this), 337);
/*      */     }
/*      */ 
/*      */ 
/*      */     
/*      */     public void forEach(FloatConsumer consumer) {
/* 1674 */       for (int i = Float2LongLinkedOpenHashMap.this.size, next = Float2LongLinkedOpenHashMap.this.first; i-- != 0; ) {
/* 1675 */         int curr = next;
/* 1676 */         next = (int)Float2LongLinkedOpenHashMap.this.link[curr];
/* 1677 */         consumer.accept(Float2LongLinkedOpenHashMap.this.key[curr]);
/*      */       } 
/*      */     }
/*      */ 
/*      */     
/*      */     public int size() {
/* 1683 */       return Float2LongLinkedOpenHashMap.this.size;
/*      */     }
/*      */ 
/*      */     
/*      */     public boolean contains(float k) {
/* 1688 */       return Float2LongLinkedOpenHashMap.this.containsKey(k);
/*      */     }
/*      */ 
/*      */     
/*      */     public boolean remove(float k) {
/* 1693 */       int oldSize = Float2LongLinkedOpenHashMap.this.size;
/* 1694 */       Float2LongLinkedOpenHashMap.this.remove(k);
/* 1695 */       return (Float2LongLinkedOpenHashMap.this.size != oldSize);
/*      */     }
/*      */ 
/*      */     
/*      */     public void clear() {
/* 1700 */       Float2LongLinkedOpenHashMap.this.clear();
/*      */     }
/*      */ 
/*      */     
/*      */     public float firstFloat() {
/* 1705 */       if (Float2LongLinkedOpenHashMap.this.size == 0) throw new NoSuchElementException(); 
/* 1706 */       return Float2LongLinkedOpenHashMap.this.key[Float2LongLinkedOpenHashMap.this.first];
/*      */     }
/*      */ 
/*      */     
/*      */     public float lastFloat() {
/* 1711 */       if (Float2LongLinkedOpenHashMap.this.size == 0) throw new NoSuchElementException(); 
/* 1712 */       return Float2LongLinkedOpenHashMap.this.key[Float2LongLinkedOpenHashMap.this.last];
/*      */     }
/*      */ 
/*      */     
/*      */     public FloatComparator comparator() {
/* 1717 */       return null;
/*      */     }
/*      */ 
/*      */     
/*      */     public FloatSortedSet tailSet(float from) {
/* 1722 */       throw new UnsupportedOperationException();
/*      */     }
/*      */ 
/*      */     
/*      */     public FloatSortedSet headSet(float to) {
/* 1727 */       throw new UnsupportedOperationException();
/*      */     }
/*      */ 
/*      */     
/*      */     public FloatSortedSet subSet(float from, float to) {
/* 1732 */       throw new UnsupportedOperationException();
/*      */     }
/*      */   }
/*      */ 
/*      */   
/*      */   public FloatSortedSet keySet() {
/* 1738 */     if (this.keys == null) this.keys = new KeySet(); 
/* 1739 */     return this.keys;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private final class ValueIterator
/*      */     extends MapIterator<LongConsumer>
/*      */     implements LongListIterator
/*      */   {
/*      */     public long previousLong() {
/* 1753 */       return Float2LongLinkedOpenHashMap.this.value[previousEntry()];
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
/*      */     final void acceptOnIndex(LongConsumer action, int index) {
/* 1765 */       action.accept(Float2LongLinkedOpenHashMap.this.value[index]);
/*      */     }
/*      */ 
/*      */     
/*      */     public long nextLong() {
/* 1770 */       return Float2LongLinkedOpenHashMap.this.value[nextEntry()];
/*      */     }
/*      */   }
/*      */ 
/*      */   
/*      */   public LongCollection values() {
/* 1776 */     if (this.values == null) this.values = (LongCollection)new AbstractLongCollection()
/*      */         {
/*      */           private static final int SPLITERATOR_CHARACTERISTICS = 336;
/*      */           
/*      */           public LongIterator iterator() {
/* 1781 */             return (LongIterator)new Float2LongLinkedOpenHashMap.ValueIterator();
/*      */           }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */           
/*      */           public LongSpliterator spliterator() {
/* 1791 */             return LongSpliterators.asSpliterator(iterator(), Size64.sizeOf(Float2LongLinkedOpenHashMap.this), 336);
/*      */           }
/*      */ 
/*      */ 
/*      */           
/*      */           public void forEach(LongConsumer consumer) {
/* 1797 */             for (int i = Float2LongLinkedOpenHashMap.this.size, next = Float2LongLinkedOpenHashMap.this.first; i-- != 0; ) {
/* 1798 */               int curr = next;
/* 1799 */               next = (int)Float2LongLinkedOpenHashMap.this.link[curr];
/* 1800 */               consumer.accept(Float2LongLinkedOpenHashMap.this.value[curr]);
/*      */             } 
/*      */           }
/*      */ 
/*      */           
/*      */           public int size() {
/* 1806 */             return Float2LongLinkedOpenHashMap.this.size;
/*      */           }
/*      */ 
/*      */           
/*      */           public boolean contains(long v) {
/* 1811 */             return Float2LongLinkedOpenHashMap.this.containsValue(v);
/*      */           }
/*      */ 
/*      */           
/*      */           public void clear() {
/* 1816 */             Float2LongLinkedOpenHashMap.this.clear();
/*      */           }
/*      */         }; 
/* 1819 */     return this.values;
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
/* 1836 */     return trim(this.size);
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
/* 1858 */     int l = HashCommon.nextPowerOfTwo((int)Math.ceil((n / this.f)));
/* 1859 */     if (l >= this.n || this.size > HashCommon.maxFill(l, this.f)) return true; 
/*      */     try {
/* 1861 */       rehash(l);
/* 1862 */     } catch (OutOfMemoryError cantDoIt) {
/* 1863 */       return false;
/*      */     } 
/* 1865 */     return true;
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
/* 1880 */     float[] key = this.key;
/* 1881 */     long[] value = this.value;
/* 1882 */     int mask = newN - 1;
/* 1883 */     float[] newKey = new float[newN + 1];
/* 1884 */     long[] newValue = new long[newN + 1];
/* 1885 */     int i = this.first, prev = -1, newPrev = -1;
/* 1886 */     long[] link = this.link;
/* 1887 */     long[] newLink = new long[newN + 1];
/* 1888 */     this.first = -1;
/* 1889 */     for (int j = this.size; j-- != 0; ) {
/* 1890 */       int pos; if (Float.floatToIntBits(key[i]) == 0) { pos = newN; }
/*      */       else
/* 1892 */       { pos = HashCommon.mix(HashCommon.float2int(key[i])) & mask;
/* 1893 */         for (; Float.floatToIntBits(newKey[pos]) != 0; pos = pos + 1 & mask); }
/*      */       
/* 1895 */       newKey[pos] = key[i];
/* 1896 */       newValue[pos] = value[i];
/* 1897 */       if (prev != -1) {
/* 1898 */         newLink[newPrev] = newLink[newPrev] ^ (newLink[newPrev] ^ pos & 0xFFFFFFFFL) & 0xFFFFFFFFL;
/* 1899 */         newLink[pos] = newLink[pos] ^ (newLink[pos] ^ (newPrev & 0xFFFFFFFFL) << 32L) & 0xFFFFFFFF00000000L;
/* 1900 */         newPrev = pos;
/*      */       } else {
/* 1902 */         newPrev = this.first = pos;
/*      */         
/* 1904 */         newLink[pos] = -1L;
/*      */       } 
/* 1906 */       int t = i;
/* 1907 */       i = (int)link[i];
/* 1908 */       prev = t;
/*      */     } 
/* 1910 */     this.link = newLink;
/* 1911 */     this.last = newPrev;
/* 1912 */     if (newPrev != -1)
/*      */     {
/* 1914 */       newLink[newPrev] = newLink[newPrev] | 0xFFFFFFFFL; } 
/* 1915 */     this.n = newN;
/* 1916 */     this.mask = mask;
/* 1917 */     this.maxFill = HashCommon.maxFill(this.n, this.f);
/* 1918 */     this.key = newKey;
/* 1919 */     this.value = newValue;
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
/*      */   public Float2LongLinkedOpenHashMap clone() {
/*      */     Float2LongLinkedOpenHashMap c;
/*      */     try {
/* 1936 */       c = (Float2LongLinkedOpenHashMap)super.clone();
/* 1937 */     } catch (CloneNotSupportedException cantHappen) {
/* 1938 */       throw new InternalError();
/*      */     } 
/* 1940 */     c.keys = null;
/* 1941 */     c.values = null;
/* 1942 */     c.entries = null;
/* 1943 */     c.containsNullKey = this.containsNullKey;
/* 1944 */     c.key = (float[])this.key.clone();
/* 1945 */     c.value = (long[])this.value.clone();
/* 1946 */     c.link = (long[])this.link.clone();
/* 1947 */     return c;
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
/* 1961 */     int h = 0;
/* 1962 */     for (int j = realSize(), i = 0, t = 0; j-- != 0; ) {
/* 1963 */       for (; Float.floatToIntBits(this.key[i]) == 0; i++);
/* 1964 */       t = HashCommon.float2int(this.key[i]);
/* 1965 */       t ^= HashCommon.long2int(this.value[i]);
/* 1966 */       h += t;
/* 1967 */       i++;
/*      */     } 
/*      */     
/* 1970 */     if (this.containsNullKey) h += HashCommon.long2int(this.value[this.n]); 
/* 1971 */     return h;
/*      */   }
/*      */   
/*      */   private void writeObject(ObjectOutputStream s) throws IOException {
/* 1975 */     float[] key = this.key;
/* 1976 */     long[] value = this.value;
/* 1977 */     EntryIterator i = new EntryIterator();
/* 1978 */     s.defaultWriteObject();
/* 1979 */     for (int j = this.size; j-- != 0; ) {
/* 1980 */       int e = i.nextEntry();
/* 1981 */       s.writeFloat(key[e]);
/* 1982 */       s.writeLong(value[e]);
/*      */     } 
/*      */   }
/*      */   
/*      */   private void readObject(ObjectInputStream s) throws IOException, ClassNotFoundException {
/* 1987 */     s.defaultReadObject();
/* 1988 */     this.n = HashCommon.arraySize(this.size, this.f);
/* 1989 */     this.maxFill = HashCommon.maxFill(this.n, this.f);
/* 1990 */     this.mask = this.n - 1;
/* 1991 */     float[] key = this.key = new float[this.n + 1];
/* 1992 */     long[] value = this.value = new long[this.n + 1];
/* 1993 */     long[] link = this.link = new long[this.n + 1];
/* 1994 */     int prev = -1;
/* 1995 */     this.first = this.last = -1;
/*      */ 
/*      */     
/* 1998 */     for (int i = this.size; i-- != 0; ) {
/* 1999 */       int pos; float k = s.readFloat();
/* 2000 */       long v = s.readLong();
/* 2001 */       if (Float.floatToIntBits(k) == 0) {
/* 2002 */         pos = this.n;
/* 2003 */         this.containsNullKey = true;
/*      */       } else {
/* 2005 */         pos = HashCommon.mix(HashCommon.float2int(k)) & this.mask;
/* 2006 */         for (; Float.floatToIntBits(key[pos]) != 0; pos = pos + 1 & this.mask);
/*      */       } 
/* 2008 */       key[pos] = k;
/* 2009 */       value[pos] = v;
/* 2010 */       if (this.first != -1) {
/* 2011 */         link[prev] = link[prev] ^ (link[prev] ^ pos & 0xFFFFFFFFL) & 0xFFFFFFFFL;
/* 2012 */         link[pos] = link[pos] ^ (link[pos] ^ (prev & 0xFFFFFFFFL) << 32L) & 0xFFFFFFFF00000000L;
/* 2013 */         prev = pos; continue;
/*      */       } 
/* 2015 */       prev = this.first = pos;
/*      */       
/* 2017 */       link[pos] = link[pos] | 0xFFFFFFFF00000000L;
/*      */     } 
/*      */     
/* 2020 */     this.last = prev;
/* 2021 */     if (prev != -1)
/*      */     {
/* 2023 */       link[prev] = link[prev] | 0xFFFFFFFFL;
/*      */     }
/*      */   }
/*      */   
/*      */   private void checkTable() {}
/*      */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\i\\unimi\dsi\fastutil\floats\Float2LongLinkedOpenHashMap.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */