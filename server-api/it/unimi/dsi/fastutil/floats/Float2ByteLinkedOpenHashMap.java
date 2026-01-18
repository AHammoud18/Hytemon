/*      */ package it.unimi.dsi.fastutil.floats;
/*      */ 
/*      */ import it.unimi.dsi.fastutil.Hash;
/*      */ import it.unimi.dsi.fastutil.HashCommon;
/*      */ import it.unimi.dsi.fastutil.SafeMath;
/*      */ import it.unimi.dsi.fastutil.Size64;
/*      */ import it.unimi.dsi.fastutil.bytes.AbstractByteCollection;
/*      */ import it.unimi.dsi.fastutil.bytes.ByteCollection;
/*      */ import it.unimi.dsi.fastutil.bytes.ByteConsumer;
/*      */ import it.unimi.dsi.fastutil.bytes.ByteIterator;
/*      */ import it.unimi.dsi.fastutil.bytes.ByteListIterator;
/*      */ import it.unimi.dsi.fastutil.bytes.ByteSpliterator;
/*      */ import it.unimi.dsi.fastutil.bytes.ByteSpliterators;
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
/*      */ import java.util.function.DoubleToIntFunction;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ public class Float2ByteLinkedOpenHashMap
/*      */   extends AbstractFloat2ByteSortedMap
/*      */   implements Serializable, Cloneable, Hash
/*      */ {
/*      */   private static final long serialVersionUID = 0L;
/*      */   private static final boolean ASSERTS = false;
/*      */   protected transient float[] key;
/*      */   protected transient byte[] value;
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
/*      */   protected transient Float2ByteSortedMap.FastSortedEntrySet entries;
/*      */ 
/*      */   
/*      */   protected transient FloatSortedSet keys;
/*      */ 
/*      */   
/*      */   protected transient ByteCollection values;
/*      */ 
/*      */ 
/*      */   
/*      */   public Float2ByteLinkedOpenHashMap(int expected, float f) {
/*  148 */     if (f <= 0.0F || f >= 1.0F) throw new IllegalArgumentException("Load factor must be greater than 0 and smaller than 1"); 
/*  149 */     if (expected < 0) throw new IllegalArgumentException("The expected number of elements must be nonnegative"); 
/*  150 */     this.f = f;
/*  151 */     this.minN = this.n = HashCommon.arraySize(expected, f);
/*  152 */     this.mask = this.n - 1;
/*  153 */     this.maxFill = HashCommon.maxFill(this.n, f);
/*  154 */     this.key = new float[this.n + 1];
/*  155 */     this.value = new byte[this.n + 1];
/*  156 */     this.link = new long[this.n + 1];
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Float2ByteLinkedOpenHashMap(int expected) {
/*  165 */     this(expected, 0.75F);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Float2ByteLinkedOpenHashMap() {
/*  173 */     this(16, 0.75F);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Float2ByteLinkedOpenHashMap(Map<? extends Float, ? extends Byte> m, float f) {
/*  183 */     this(m.size(), f);
/*  184 */     putAll(m);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Float2ByteLinkedOpenHashMap(Map<? extends Float, ? extends Byte> m) {
/*  193 */     this(m, 0.75F);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Float2ByteLinkedOpenHashMap(Float2ByteMap m, float f) {
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
/*      */   public Float2ByteLinkedOpenHashMap(Float2ByteMap m) {
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
/*      */   public Float2ByteLinkedOpenHashMap(float[] k, byte[] v, float f) {
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
/*      */   public Float2ByteLinkedOpenHashMap(float[] k, byte[] v) {
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
/*      */   private byte removeEntry(int pos) {
/*  264 */     byte oldValue = this.value[pos];
/*  265 */     this.size--;
/*  266 */     fixPointers(pos);
/*  267 */     shiftKeys(pos);
/*  268 */     if (this.n > this.minN && this.size < this.maxFill / 4 && this.n > 16) rehash(this.n / 2); 
/*  269 */     return oldValue;
/*      */   }
/*      */   
/*      */   private byte removeNullEntry() {
/*  273 */     this.containsNullKey = false;
/*  274 */     byte oldValue = this.value[this.n];
/*  275 */     this.size--;
/*  276 */     fixPointers(this.n);
/*  277 */     if (this.n > this.minN && this.size < this.maxFill / 4 && this.n > 16) rehash(this.n / 2); 
/*  278 */     return oldValue;
/*      */   }
/*      */ 
/*      */   
/*      */   public void putAll(Map<? extends Float, ? extends Byte> m) {
/*  283 */     if (this.f <= 0.5D) { ensureCapacity(m.size()); }
/*  284 */     else { tryCapacity((size() + m.size())); }
/*      */     
/*  286 */     super.putAll(m);
/*      */   }
/*      */   
/*      */   private int find(float k) {
/*  290 */     if (Float.floatToIntBits(k) == 0) return this.containsNullKey ? this.n : -(this.n + 1);
/*      */     
/*  292 */     float[] key = this.key;
/*      */     float curr;
/*      */     int pos;
/*  295 */     if (Float.floatToIntBits(curr = key[pos = HashCommon.mix(HashCommon.float2int(k)) & this.mask]) == 0) return -(pos + 1); 
/*  296 */     if (Float.floatToIntBits(k) == Float.floatToIntBits(curr)) return pos;
/*      */     
/*      */     while (true) {
/*  299 */       if (Float.floatToIntBits(curr = key[pos = pos + 1 & this.mask]) == 0) return -(pos + 1); 
/*  300 */       if (Float.floatToIntBits(k) == Float.floatToIntBits(curr)) return pos; 
/*      */     } 
/*      */   }
/*      */   
/*      */   private void insert(int pos, float k, byte v) {
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
/*      */   public byte put(float k, byte v) {
/*  323 */     int pos = find(k);
/*  324 */     if (pos < 0) {
/*  325 */       insert(-pos - 1, k, v);
/*  326 */       return this.defRetValue;
/*      */     } 
/*  328 */     byte oldValue = this.value[pos];
/*  329 */     this.value[pos] = v;
/*  330 */     return oldValue;
/*      */   }
/*      */   
/*      */   private byte addToValue(int pos, byte incr) {
/*  334 */     byte oldValue = this.value[pos];
/*  335 */     this.value[pos] = (byte)(oldValue + incr);
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
/*      */   public byte addTo(float k, byte incr) {
/*      */     int pos;
/*  354 */     if (Float.floatToIntBits(k) == 0) {
/*  355 */       if (this.containsNullKey) return addToValue(this.n, incr); 
/*  356 */       pos = this.n;
/*  357 */       this.containsNullKey = true;
/*      */     } else {
/*      */       
/*  360 */       float[] key = this.key;
/*      */       float curr;
/*  362 */       if (Float.floatToIntBits(curr = key[pos = HashCommon.mix(HashCommon.float2int(k)) & this.mask]) != 0) {
/*  363 */         if (Float.floatToIntBits(curr) == Float.floatToIntBits(k)) return addToValue(pos, incr); 
/*  364 */         while (Float.floatToIntBits(curr = key[pos = pos + 1 & this.mask]) != 0) { if (Float.floatToIntBits(curr) == Float.floatToIntBits(k)) return addToValue(pos, incr);  }
/*      */       
/*      */       } 
/*  367 */     }  this.key[pos] = k;
/*  368 */     this.value[pos] = (byte)(this.defRetValue + incr);
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
/*  393 */     float[] key = this.key; while (true) {
/*      */       float curr; int last;
/*  395 */       pos = (last = pos) + 1 & this.mask;
/*      */       while (true) {
/*  397 */         if (Float.floatToIntBits(curr = key[pos]) == 0) {
/*  398 */           key[last] = 0.0F;
/*      */           return;
/*      */         } 
/*  401 */         int slot = HashCommon.mix(HashCommon.float2int(curr)) & this.mask;
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
/*      */   public byte remove(float k) {
/*  414 */     if (Float.floatToIntBits(k) == 0) {
/*  415 */       if (this.containsNullKey) return removeNullEntry(); 
/*  416 */       return this.defRetValue;
/*      */     } 
/*      */     
/*  419 */     float[] key = this.key;
/*      */     float curr;
/*      */     int pos;
/*  422 */     if (Float.floatToIntBits(curr = key[pos = HashCommon.mix(HashCommon.float2int(k)) & this.mask]) == 0) return this.defRetValue; 
/*  423 */     if (Float.floatToIntBits(k) == Float.floatToIntBits(curr)) return removeEntry(pos); 
/*      */     while (true) {
/*  425 */       if (Float.floatToIntBits(curr = key[pos = pos + 1 & this.mask]) == 0) return this.defRetValue; 
/*  426 */       if (Float.floatToIntBits(k) == Float.floatToIntBits(curr)) return removeEntry(pos); 
/*      */     } 
/*      */   }
/*      */   
/*      */   private byte setValue(int pos, byte v) {
/*  431 */     byte oldValue = this.value[pos];
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
/*      */   public byte removeFirstByte() {
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
/*  455 */     byte v = this.value[pos];
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
/*      */   public byte removeLastByte() {
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
/*  482 */     byte v = this.value[pos];
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
/*      */   public byte getAndMoveToFirst(float k) {
/*  535 */     if (Float.floatToIntBits(k) == 0) {
/*  536 */       if (this.containsNullKey) {
/*  537 */         moveIndexToFirst(this.n);
/*  538 */         return this.value[this.n];
/*      */       } 
/*  540 */       return this.defRetValue;
/*      */     } 
/*      */     
/*  543 */     float[] key = this.key;
/*      */     float curr;
/*      */     int pos;
/*  546 */     if (Float.floatToIntBits(curr = key[pos = HashCommon.mix(HashCommon.float2int(k)) & this.mask]) == 0) return this.defRetValue; 
/*  547 */     if (Float.floatToIntBits(k) == Float.floatToIntBits(curr)) {
/*  548 */       moveIndexToFirst(pos);
/*  549 */       return this.value[pos];
/*      */     } 
/*      */     
/*      */     while (true) {
/*  553 */       if (Float.floatToIntBits(curr = key[pos = pos + 1 & this.mask]) == 0) return this.defRetValue; 
/*  554 */       if (Float.floatToIntBits(k) == Float.floatToIntBits(curr)) {
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
/*      */   public byte getAndMoveToLast(float k) {
/*  570 */     if (Float.floatToIntBits(k) == 0) {
/*  571 */       if (this.containsNullKey) {
/*  572 */         moveIndexToLast(this.n);
/*  573 */         return this.value[this.n];
/*      */       } 
/*  575 */       return this.defRetValue;
/*      */     } 
/*      */     
/*  578 */     float[] key = this.key;
/*      */     float curr;
/*      */     int pos;
/*  581 */     if (Float.floatToIntBits(curr = key[pos = HashCommon.mix(HashCommon.float2int(k)) & this.mask]) == 0) return this.defRetValue; 
/*  582 */     if (Float.floatToIntBits(k) == Float.floatToIntBits(curr)) {
/*  583 */       moveIndexToLast(pos);
/*  584 */       return this.value[pos];
/*      */     } 
/*      */     
/*      */     while (true) {
/*  588 */       if (Float.floatToIntBits(curr = key[pos = pos + 1 & this.mask]) == 0) return this.defRetValue; 
/*  589 */       if (Float.floatToIntBits(k) == Float.floatToIntBits(curr)) {
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
/*      */   public byte putAndMoveToFirst(float k, byte v) {
/*      */     int pos;
/*  607 */     if (Float.floatToIntBits(k) == 0) {
/*  608 */       if (this.containsNullKey) {
/*  609 */         moveIndexToFirst(this.n);
/*  610 */         return setValue(this.n, v);
/*      */       } 
/*  612 */       this.containsNullKey = true;
/*  613 */       pos = this.n;
/*      */     } else {
/*      */       
/*  616 */       float[] key = this.key;
/*      */       float curr;
/*  618 */       if (Float.floatToIntBits(curr = key[pos = HashCommon.mix(HashCommon.float2int(k)) & this.mask]) != 0) {
/*  619 */         if (Float.floatToIntBits(curr) == Float.floatToIntBits(k)) {
/*  620 */           moveIndexToFirst(pos);
/*  621 */           return setValue(pos, v);
/*      */         } 
/*  623 */         while (Float.floatToIntBits(curr = key[pos = pos + 1 & this.mask]) != 0) { if (Float.floatToIntBits(curr) == Float.floatToIntBits(k)) {
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
/*      */   public byte putAndMoveToLast(float k, byte v) {
/*      */     int pos;
/*  656 */     if (Float.floatToIntBits(k) == 0) {
/*  657 */       if (this.containsNullKey) {
/*  658 */         moveIndexToLast(this.n);
/*  659 */         return setValue(this.n, v);
/*      */       } 
/*  661 */       this.containsNullKey = true;
/*  662 */       pos = this.n;
/*      */     } else {
/*      */       
/*  665 */       float[] key = this.key;
/*      */       float curr;
/*  667 */       if (Float.floatToIntBits(curr = key[pos = HashCommon.mix(HashCommon.float2int(k)) & this.mask]) != 0) {
/*  668 */         if (Float.floatToIntBits(curr) == Float.floatToIntBits(k)) {
/*  669 */           moveIndexToLast(pos);
/*  670 */           return setValue(pos, v);
/*      */         } 
/*  672 */         while (Float.floatToIntBits(curr = key[pos = pos + 1 & this.mask]) != 0) { if (Float.floatToIntBits(curr) == Float.floatToIntBits(k)) {
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
/*      */   public byte get(float k) {
/*  697 */     if (Float.floatToIntBits(k) == 0) return this.containsNullKey ? this.value[this.n] : this.defRetValue;
/*      */     
/*  699 */     float[] key = this.key;
/*      */     float curr;
/*      */     int pos;
/*  702 */     if (Float.floatToIntBits(curr = key[pos = HashCommon.mix(HashCommon.float2int(k)) & this.mask]) == 0) return this.defRetValue; 
/*  703 */     if (Float.floatToIntBits(k) == Float.floatToIntBits(curr)) return this.value[pos];
/*      */     
/*      */     while (true) {
/*  706 */       if (Float.floatToIntBits(curr = key[pos = pos + 1 & this.mask]) == 0) return this.defRetValue; 
/*  707 */       if (Float.floatToIntBits(k) == Float.floatToIntBits(curr)) return this.value[pos];
/*      */     
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   public boolean containsKey(float k) {
/*  714 */     if (Float.floatToIntBits(k) == 0) return this.containsNullKey;
/*      */     
/*  716 */     float[] key = this.key;
/*      */     float curr;
/*      */     int pos;
/*  719 */     if (Float.floatToIntBits(curr = key[pos = HashCommon.mix(HashCommon.float2int(k)) & this.mask]) == 0) return false; 
/*  720 */     if (Float.floatToIntBits(k) == Float.floatToIntBits(curr)) return true;
/*      */     
/*      */     while (true) {
/*  723 */       if (Float.floatToIntBits(curr = key[pos = pos + 1 & this.mask]) == 0) return false; 
/*  724 */       if (Float.floatToIntBits(k) == Float.floatToIntBits(curr)) return true;
/*      */     
/*      */     } 
/*      */   }
/*      */   
/*      */   public boolean containsValue(byte v) {
/*  730 */     byte[] value = this.value;
/*  731 */     float[] key = this.key;
/*  732 */     if (this.containsNullKey && value[this.n] == v) return true; 
/*  733 */     for (int i = this.n; i-- != 0;) { if (Float.floatToIntBits(key[i]) != 0 && value[i] == v) return true;  }
/*  734 */      return false;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public byte getOrDefault(float k, byte defaultValue) {
/*  741 */     if (Float.floatToIntBits(k) == 0) return this.containsNullKey ? this.value[this.n] : defaultValue;
/*      */     
/*  743 */     float[] key = this.key;
/*      */     float curr;
/*      */     int pos;
/*  746 */     if (Float.floatToIntBits(curr = key[pos = HashCommon.mix(HashCommon.float2int(k)) & this.mask]) == 0) return defaultValue; 
/*  747 */     if (Float.floatToIntBits(k) == Float.floatToIntBits(curr)) return this.value[pos];
/*      */     
/*      */     while (true) {
/*  750 */       if (Float.floatToIntBits(curr = key[pos = pos + 1 & this.mask]) == 0) return defaultValue; 
/*  751 */       if (Float.floatToIntBits(k) == Float.floatToIntBits(curr)) return this.value[pos];
/*      */     
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   public byte putIfAbsent(float k, byte v) {
/*  758 */     int pos = find(k);
/*  759 */     if (pos >= 0) return this.value[pos]; 
/*  760 */     insert(-pos - 1, k, v);
/*  761 */     return this.defRetValue;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean remove(float k, byte v) {
/*  768 */     if (Float.floatToIntBits(k) == 0) {
/*  769 */       if (this.containsNullKey && v == this.value[this.n]) {
/*  770 */         removeNullEntry();
/*  771 */         return true;
/*      */       } 
/*  773 */       return false;
/*      */     } 
/*      */     
/*  776 */     float[] key = this.key;
/*      */     float curr;
/*      */     int pos;
/*  779 */     if (Float.floatToIntBits(curr = key[pos = HashCommon.mix(HashCommon.float2int(k)) & this.mask]) == 0) return false; 
/*  780 */     if (Float.floatToIntBits(k) == Float.floatToIntBits(curr) && v == this.value[pos]) {
/*  781 */       removeEntry(pos);
/*  782 */       return true;
/*      */     } 
/*      */     while (true) {
/*  785 */       if (Float.floatToIntBits(curr = key[pos = pos + 1 & this.mask]) == 0) return false; 
/*  786 */       if (Float.floatToIntBits(k) == Float.floatToIntBits(curr) && v == this.value[pos]) {
/*  787 */         removeEntry(pos);
/*  788 */         return true;
/*      */       } 
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean replace(float k, byte oldValue, byte v) {
/*  796 */     int pos = find(k);
/*  797 */     if (pos < 0 || oldValue != this.value[pos]) return false; 
/*  798 */     this.value[pos] = v;
/*  799 */     return true;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public byte replace(float k, byte v) {
/*  805 */     int pos = find(k);
/*  806 */     if (pos < 0) return this.defRetValue; 
/*  807 */     byte oldValue = this.value[pos];
/*  808 */     this.value[pos] = v;
/*  809 */     return oldValue;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public byte computeIfAbsent(float k, DoubleToIntFunction mappingFunction) {
/*  815 */     Objects.requireNonNull(mappingFunction);
/*  816 */     int pos = find(k);
/*  817 */     if (pos >= 0) return this.value[pos]; 
/*  818 */     byte newValue = SafeMath.safeIntToByte(mappingFunction.applyAsInt(k));
/*  819 */     insert(-pos - 1, k, newValue);
/*  820 */     return newValue;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public byte computeIfAbsent(float key, Float2ByteFunction mappingFunction) {
/*  826 */     Objects.requireNonNull(mappingFunction);
/*  827 */     int pos = find(key);
/*  828 */     if (pos >= 0) return this.value[pos]; 
/*  829 */     if (!mappingFunction.containsKey(key)) return this.defRetValue; 
/*  830 */     byte newValue = mappingFunction.get(key);
/*  831 */     insert(-pos - 1, key, newValue);
/*  832 */     return newValue;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public byte computeIfAbsentNullable(float k, DoubleFunction<? extends Byte> mappingFunction) {
/*  838 */     Objects.requireNonNull(mappingFunction);
/*  839 */     int pos = find(k);
/*  840 */     if (pos >= 0) return this.value[pos]; 
/*  841 */     Byte newValue = mappingFunction.apply(k);
/*  842 */     if (newValue == null) return this.defRetValue; 
/*  843 */     byte v = newValue.byteValue();
/*  844 */     insert(-pos - 1, k, v);
/*  845 */     return v;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public byte computeIfPresent(float k, BiFunction<? super Float, ? super Byte, ? extends Byte> remappingFunction) {
/*  851 */     Objects.requireNonNull(remappingFunction);
/*  852 */     int pos = find(k);
/*  853 */     if (pos < 0) return this.defRetValue; 
/*  854 */     Byte newValue = remappingFunction.apply(Float.valueOf(k), Byte.valueOf(this.value[pos]));
/*  855 */     if (newValue == null) {
/*  856 */       if (Float.floatToIntBits(k) == 0) { removeNullEntry(); }
/*  857 */       else { removeEntry(pos); }
/*  858 */        return this.defRetValue;
/*      */     } 
/*  860 */     this.value[pos] = newValue.byteValue(); return newValue.byteValue();
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public byte compute(float k, BiFunction<? super Float, ? super Byte, ? extends Byte> remappingFunction) {
/*  866 */     Objects.requireNonNull(remappingFunction);
/*  867 */     int pos = find(k);
/*  868 */     Byte newValue = remappingFunction.apply(Float.valueOf(k), (pos >= 0) ? Byte.valueOf(this.value[pos]) : null);
/*  869 */     if (newValue == null) {
/*  870 */       if (pos >= 0)
/*  871 */         if (Float.floatToIntBits(k) == 0) { removeNullEntry(); }
/*  872 */         else { removeEntry(pos); }
/*      */          
/*  874 */       return this.defRetValue;
/*      */     } 
/*  876 */     byte newVal = newValue.byteValue();
/*  877 */     if (pos < 0) {
/*  878 */       insert(-pos - 1, k, newVal);
/*  879 */       return newVal;
/*      */     } 
/*  881 */     this.value[pos] = newVal; return newVal;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public byte merge(float k, byte v, BiFunction<? super Byte, ? super Byte, ? extends Byte> remappingFunction) {
/*  887 */     Objects.requireNonNull(remappingFunction);
/*      */     
/*  889 */     int pos = find(k);
/*  890 */     if (pos < 0) {
/*  891 */       if (pos < 0) { insert(-pos - 1, k, v); }
/*  892 */       else { this.value[pos] = v; }
/*  893 */        return v;
/*      */     } 
/*  895 */     Byte newValue = remappingFunction.apply(Byte.valueOf(this.value[pos]), Byte.valueOf(v));
/*  896 */     if (newValue == null) {
/*  897 */       if (Float.floatToIntBits(k) == 0) { removeNullEntry(); }
/*  898 */       else { removeEntry(pos); }
/*  899 */        return this.defRetValue;
/*      */     } 
/*  901 */     this.value[pos] = newValue.byteValue(); return newValue.byteValue();
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
/*  915 */     Arrays.fill(this.key, 0.0F);
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
/*      */     implements Float2ByteMap.Entry, Map.Entry<Float, Byte>, FloatBytePair
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
/*      */     public float getFloatKey() {
/*  947 */       return Float2ByteLinkedOpenHashMap.this.key[this.index];
/*      */     }
/*      */ 
/*      */     
/*      */     public float leftFloat() {
/*  952 */       return Float2ByteLinkedOpenHashMap.this.key[this.index];
/*      */     }
/*      */ 
/*      */     
/*      */     public byte getByteValue() {
/*  957 */       return Float2ByteLinkedOpenHashMap.this.value[this.index];
/*      */     }
/*      */ 
/*      */     
/*      */     public byte rightByte() {
/*  962 */       return Float2ByteLinkedOpenHashMap.this.value[this.index];
/*      */     }
/*      */ 
/*      */     
/*      */     public byte setValue(byte v) {
/*  967 */       byte oldValue = Float2ByteLinkedOpenHashMap.this.value[this.index];
/*  968 */       Float2ByteLinkedOpenHashMap.this.value[this.index] = v;
/*  969 */       return oldValue;
/*      */     }
/*      */ 
/*      */     
/*      */     public FloatBytePair right(byte v) {
/*  974 */       Float2ByteLinkedOpenHashMap.this.value[this.index] = v;
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
/*      */     public Float getKey() {
/*  986 */       return Float.valueOf(Float2ByteLinkedOpenHashMap.this.key[this.index]);
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     @Deprecated
/*      */     public Byte getValue() {
/*  997 */       return Byte.valueOf(Float2ByteLinkedOpenHashMap.this.value[this.index]);
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     @Deprecated
/*      */     public Byte setValue(Byte v) {
/* 1008 */       return Byte.valueOf(setValue(v.byteValue()));
/*      */     }
/*      */ 
/*      */ 
/*      */     
/*      */     public boolean equals(Object o) {
/* 1014 */       if (!(o instanceof Map.Entry)) return false; 
/* 1015 */       Map.Entry<Float, Byte> e = (Map.Entry<Float, Byte>)o;
/* 1016 */       return (Float.floatToIntBits(Float2ByteLinkedOpenHashMap.this.key[this.index]) == Float.floatToIntBits(((Float)e.getKey()).floatValue()) && Float2ByteLinkedOpenHashMap.this.value[this.index] == ((Byte)e.getValue()).byteValue());
/*      */     }
/*      */ 
/*      */     
/*      */     public int hashCode() {
/* 1021 */       return HashCommon.float2int(Float2ByteLinkedOpenHashMap.this.key[this.index]) ^ Float2ByteLinkedOpenHashMap.this.value[this.index];
/*      */     }
/*      */ 
/*      */     
/*      */     public String toString() {
/* 1026 */       return Float2ByteLinkedOpenHashMap.this.key[this.index] + "=>" + Float2ByteLinkedOpenHashMap.this.value[this.index];
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
/*      */   public float firstFloatKey() {
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
/*      */   public float lastFloatKey() {
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
/*      */   public Float2ByteSortedMap tailMap(float from) {
/* 1128 */     throw new UnsupportedOperationException();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Float2ByteSortedMap headMap(float to) {
/* 1138 */     throw new UnsupportedOperationException();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Float2ByteSortedMap subMap(float from, float to) {
/* 1148 */     throw new UnsupportedOperationException();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public FloatComparator comparator() {
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
/* 1194 */       this.next = Float2ByteLinkedOpenHashMap.this.first;
/* 1195 */       this.index = 0;
/*      */     }
/*      */     
/*      */     private MapIterator(float from) {
/* 1199 */       if (Float.floatToIntBits(from) == 0) {
/* 1200 */         if (Float2ByteLinkedOpenHashMap.this.containsNullKey) {
/* 1201 */           this.next = (int)Float2ByteLinkedOpenHashMap.this.link[Float2ByteLinkedOpenHashMap.this.n];
/* 1202 */           this.prev = Float2ByteLinkedOpenHashMap.this.n; return;
/*      */         } 
/* 1204 */         throw new NoSuchElementException("The key " + from + " does not belong to this map.");
/*      */       } 
/* 1206 */       if (Float.floatToIntBits(Float2ByteLinkedOpenHashMap.this.key[Float2ByteLinkedOpenHashMap.this.last]) == Float.floatToIntBits(from)) {
/* 1207 */         this.prev = Float2ByteLinkedOpenHashMap.this.last;
/* 1208 */         this.index = Float2ByteLinkedOpenHashMap.this.size;
/*      */         
/*      */         return;
/*      */       } 
/* 1212 */       int pos = HashCommon.mix(HashCommon.float2int(from)) & Float2ByteLinkedOpenHashMap.this.mask;
/*      */       
/* 1214 */       while (Float.floatToIntBits(Float2ByteLinkedOpenHashMap.this.key[pos]) != 0) {
/* 1215 */         if (Float.floatToIntBits(Float2ByteLinkedOpenHashMap.this.key[pos]) == Float.floatToIntBits(from)) {
/*      */           
/* 1217 */           this.next = (int)Float2ByteLinkedOpenHashMap.this.link[pos];
/* 1218 */           this.prev = pos;
/*      */           return;
/*      */         } 
/* 1221 */         pos = pos + 1 & Float2ByteLinkedOpenHashMap.this.mask;
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
/* 1241 */         this.index = Float2ByteLinkedOpenHashMap.this.size;
/*      */         return;
/*      */       } 
/* 1244 */       int pos = Float2ByteLinkedOpenHashMap.this.first;
/* 1245 */       this.index = 1;
/* 1246 */       while (pos != this.prev) {
/* 1247 */         pos = (int)Float2ByteLinkedOpenHashMap.this.link[pos];
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
/* 1265 */       this.next = (int)Float2ByteLinkedOpenHashMap.this.link[this.curr];
/* 1266 */       this.prev = this.curr;
/* 1267 */       if (this.index >= 0) this.index++; 
/* 1268 */       return this.curr;
/*      */     }
/*      */     
/*      */     public int previousEntry() {
/* 1272 */       if (!hasPrevious()) throw new NoSuchElementException(); 
/* 1273 */       this.curr = this.prev;
/* 1274 */       this.prev = (int)(Float2ByteLinkedOpenHashMap.this.link[this.curr] >>> 32L);
/* 1275 */       this.next = this.curr;
/* 1276 */       if (this.index >= 0) this.index--; 
/* 1277 */       return this.curr;
/*      */     }
/*      */     
/*      */     public void forEachRemaining(ConsumerType action) {
/* 1281 */       while (hasNext()) {
/* 1282 */         this.curr = this.next;
/* 1283 */         this.next = (int)Float2ByteLinkedOpenHashMap.this.link[this.curr];
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
/* 1297 */         this.prev = (int)(Float2ByteLinkedOpenHashMap.this.link[this.curr] >>> 32L); }
/* 1298 */       else { this.next = (int)Float2ByteLinkedOpenHashMap.this.link[this.curr]; }
/* 1299 */        Float2ByteLinkedOpenHashMap.this.size--;
/*      */ 
/*      */       
/* 1302 */       if (this.prev == -1) { Float2ByteLinkedOpenHashMap.this.first = this.next; }
/* 1303 */       else { Float2ByteLinkedOpenHashMap.this.link[this.prev] = Float2ByteLinkedOpenHashMap.this.link[this.prev] ^ (Float2ByteLinkedOpenHashMap.this.link[this.prev] ^ this.next & 0xFFFFFFFFL) & 0xFFFFFFFFL; }
/* 1304 */        if (this.next == -1) { Float2ByteLinkedOpenHashMap.this.last = this.prev; }
/* 1305 */       else { Float2ByteLinkedOpenHashMap.this.link[this.next] = Float2ByteLinkedOpenHashMap.this.link[this.next] ^ (Float2ByteLinkedOpenHashMap.this.link[this.next] ^ (this.prev & 0xFFFFFFFFL) << 32L) & 0xFFFFFFFF00000000L; }
/* 1306 */        int pos = this.curr;
/* 1307 */       this.curr = -1;
/* 1308 */       if (pos == Float2ByteLinkedOpenHashMap.this.n) {
/* 1309 */         Float2ByteLinkedOpenHashMap.this.containsNullKey = false;
/*      */       } else {
/*      */         
/* 1312 */         float[] key = Float2ByteLinkedOpenHashMap.this.key; while (true) {
/*      */           float curr;
/*      */           int last;
/* 1315 */           pos = (last = pos) + 1 & Float2ByteLinkedOpenHashMap.this.mask;
/*      */           while (true) {
/* 1317 */             if (Float.floatToIntBits(curr = key[pos]) == 0) {
/* 1318 */               key[last] = 0.0F;
/*      */               return;
/*      */             } 
/* 1321 */             int slot = HashCommon.mix(HashCommon.float2int(curr)) & Float2ByteLinkedOpenHashMap.this.mask;
/* 1322 */             if ((last <= pos) ? (last >= slot || slot > pos) : (last >= slot && slot > pos))
/* 1323 */               break;  pos = pos + 1 & Float2ByteLinkedOpenHashMap.this.mask;
/*      */           } 
/* 1325 */           key[last] = curr;
/* 1326 */           Float2ByteLinkedOpenHashMap.this.value[last] = Float2ByteLinkedOpenHashMap.this.value[pos];
/* 1327 */           if (this.next == pos) this.next = last; 
/* 1328 */           if (this.prev == pos) this.prev = last; 
/* 1329 */           Float2ByteLinkedOpenHashMap.this.fixPointers(pos, last);
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
/*      */     public void set(Float2ByteMap.Entry ok) {
/* 1347 */       throw new UnsupportedOperationException();
/*      */     }
/*      */     
/*      */     public void add(Float2ByteMap.Entry ok) {
/* 1351 */       throw new UnsupportedOperationException();
/*      */     }
/*      */     
/*      */     abstract void acceptOnIndex(ConsumerType param1ConsumerType, int param1Int); }
/*      */   
/*      */   private final class EntryIterator extends MapIterator<Consumer<? super Float2ByteMap.Entry>> implements ObjectListIterator<Float2ByteMap.Entry> {
/*      */     private Float2ByteLinkedOpenHashMap.MapEntry entry;
/*      */     
/*      */     public EntryIterator() {}
/*      */     
/*      */     public EntryIterator(float from) {
/* 1362 */       super(from);
/*      */     }
/*      */ 
/*      */ 
/*      */     
/*      */     final void acceptOnIndex(Consumer<? super Float2ByteMap.Entry> action, int index) {
/* 1368 */       action.accept(new Float2ByteLinkedOpenHashMap.MapEntry(index));
/*      */     }
/*      */ 
/*      */     
/*      */     public Float2ByteLinkedOpenHashMap.MapEntry next() {
/* 1373 */       return this.entry = new Float2ByteLinkedOpenHashMap.MapEntry(nextEntry());
/*      */     }
/*      */ 
/*      */     
/*      */     public Float2ByteLinkedOpenHashMap.MapEntry previous() {
/* 1378 */       return this.entry = new Float2ByteLinkedOpenHashMap.MapEntry(previousEntry());
/*      */     }
/*      */ 
/*      */     
/*      */     public void remove() {
/* 1383 */       super.remove();
/* 1384 */       this.entry.index = -1;
/*      */     }
/*      */   }
/*      */   
/*      */   private final class FastEntryIterator extends MapIterator<Consumer<? super Float2ByteMap.Entry>> implements ObjectListIterator<Float2ByteMap.Entry> {
/* 1389 */     final Float2ByteLinkedOpenHashMap.MapEntry entry = new Float2ByteLinkedOpenHashMap.MapEntry();
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public FastEntryIterator(float from) {
/* 1395 */       super(from);
/*      */     }
/*      */ 
/*      */ 
/*      */     
/*      */     final void acceptOnIndex(Consumer<? super Float2ByteMap.Entry> action, int index) {
/* 1401 */       this.entry.index = index;
/* 1402 */       action.accept(this.entry);
/*      */     }
/*      */ 
/*      */     
/*      */     public Float2ByteLinkedOpenHashMap.MapEntry next() {
/* 1407 */       this.entry.index = nextEntry();
/* 1408 */       return this.entry;
/*      */     }
/*      */ 
/*      */     
/*      */     public Float2ByteLinkedOpenHashMap.MapEntry previous() {
/* 1413 */       this.entry.index = previousEntry();
/* 1414 */       return this.entry;
/*      */     }
/*      */     
/*      */     public FastEntryIterator() {} }
/*      */   
/*      */   private final class MapEntrySet extends AbstractObjectSortedSet<Float2ByteMap.Entry> implements Float2ByteSortedMap.FastSortedEntrySet {
/*      */     private static final int SPLITERATOR_CHARACTERISTICS = 81;
/*      */     
/*      */     public ObjectBidirectionalIterator<Float2ByteMap.Entry> iterator() {
/* 1423 */       return (ObjectBidirectionalIterator<Float2ByteMap.Entry>)new Float2ByteLinkedOpenHashMap.EntryIterator();
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
/*      */     public ObjectSpliterator<Float2ByteMap.Entry> spliterator() {
/* 1441 */       return ObjectSpliterators.asSpliterator((ObjectIterator)iterator(), Size64.sizeOf(Float2ByteLinkedOpenHashMap.this), 81);
/*      */     }
/*      */ 
/*      */     
/*      */     public Comparator<? super Float2ByteMap.Entry> comparator() {
/* 1446 */       return null;
/*      */     }
/*      */ 
/*      */     
/*      */     public ObjectSortedSet<Float2ByteMap.Entry> subSet(Float2ByteMap.Entry fromElement, Float2ByteMap.Entry toElement) {
/* 1451 */       throw new UnsupportedOperationException();
/*      */     }
/*      */ 
/*      */     
/*      */     public ObjectSortedSet<Float2ByteMap.Entry> headSet(Float2ByteMap.Entry toElement) {
/* 1456 */       throw new UnsupportedOperationException();
/*      */     }
/*      */ 
/*      */     
/*      */     public ObjectSortedSet<Float2ByteMap.Entry> tailSet(Float2ByteMap.Entry fromElement) {
/* 1461 */       throw new UnsupportedOperationException();
/*      */     }
/*      */ 
/*      */     
/*      */     public Float2ByteMap.Entry first() {
/* 1466 */       if (Float2ByteLinkedOpenHashMap.this.size == 0) throw new NoSuchElementException(); 
/* 1467 */       return new Float2ByteLinkedOpenHashMap.MapEntry(Float2ByteLinkedOpenHashMap.this.first);
/*      */     }
/*      */ 
/*      */     
/*      */     public Float2ByteMap.Entry last() {
/* 1472 */       if (Float2ByteLinkedOpenHashMap.this.size == 0) throw new NoSuchElementException(); 
/* 1473 */       return new Float2ByteLinkedOpenHashMap.MapEntry(Float2ByteLinkedOpenHashMap.this.last);
/*      */     }
/*      */ 
/*      */ 
/*      */     
/*      */     public boolean contains(Object o) {
/* 1479 */       if (!(o instanceof Map.Entry)) return false; 
/* 1480 */       Map.Entry<?, ?> e = (Map.Entry<?, ?>)o;
/* 1481 */       if (e.getKey() == null || !(e.getKey() instanceof Float)) return false; 
/* 1482 */       if (e.getValue() == null || !(e.getValue() instanceof Byte)) return false; 
/* 1483 */       float k = ((Float)e.getKey()).floatValue();
/* 1484 */       byte v = ((Byte)e.getValue()).byteValue();
/* 1485 */       if (Float.floatToIntBits(k) == 0) return (Float2ByteLinkedOpenHashMap.this.containsNullKey && Float2ByteLinkedOpenHashMap.this.value[Float2ByteLinkedOpenHashMap.this.n] == v);
/*      */       
/* 1487 */       float[] key = Float2ByteLinkedOpenHashMap.this.key;
/*      */       float curr;
/*      */       int pos;
/* 1490 */       if (Float.floatToIntBits(curr = key[pos = HashCommon.mix(HashCommon.float2int(k)) & Float2ByteLinkedOpenHashMap.this.mask]) == 0) return false; 
/* 1491 */       if (Float.floatToIntBits(k) == Float.floatToIntBits(curr)) return (Float2ByteLinkedOpenHashMap.this.value[pos] == v);
/*      */       
/*      */       while (true) {
/* 1494 */         if (Float.floatToIntBits(curr = key[pos = pos + 1 & Float2ByteLinkedOpenHashMap.this.mask]) == 0) return false; 
/* 1495 */         if (Float.floatToIntBits(k) == Float.floatToIntBits(curr)) return (Float2ByteLinkedOpenHashMap.this.value[pos] == v);
/*      */       
/*      */       } 
/*      */     }
/*      */ 
/*      */     
/*      */     public boolean remove(Object o) {
/* 1502 */       if (!(o instanceof Map.Entry)) return false; 
/* 1503 */       Map.Entry<?, ?> e = (Map.Entry<?, ?>)o;
/* 1504 */       if (e.getKey() == null || !(e.getKey() instanceof Float)) return false; 
/* 1505 */       if (e.getValue() == null || !(e.getValue() instanceof Byte)) return false; 
/* 1506 */       float k = ((Float)e.getKey()).floatValue();
/* 1507 */       byte v = ((Byte)e.getValue()).byteValue();
/* 1508 */       if (Float.floatToIntBits(k) == 0) {
/* 1509 */         if (Float2ByteLinkedOpenHashMap.this.containsNullKey && Float2ByteLinkedOpenHashMap.this.value[Float2ByteLinkedOpenHashMap.this.n] == v) {
/* 1510 */           Float2ByteLinkedOpenHashMap.this.removeNullEntry();
/* 1511 */           return true;
/*      */         } 
/* 1513 */         return false;
/*      */       } 
/*      */       
/* 1516 */       float[] key = Float2ByteLinkedOpenHashMap.this.key;
/*      */       float curr;
/*      */       int pos;
/* 1519 */       if (Float.floatToIntBits(curr = key[pos = HashCommon.mix(HashCommon.float2int(k)) & Float2ByteLinkedOpenHashMap.this.mask]) == 0) return false; 
/* 1520 */       if (Float.floatToIntBits(curr) == Float.floatToIntBits(k)) {
/* 1521 */         if (Float2ByteLinkedOpenHashMap.this.value[pos] == v) {
/* 1522 */           Float2ByteLinkedOpenHashMap.this.removeEntry(pos);
/* 1523 */           return true;
/*      */         } 
/* 1525 */         return false;
/*      */       } 
/*      */       while (true) {
/* 1528 */         if (Float.floatToIntBits(curr = key[pos = pos + 1 & Float2ByteLinkedOpenHashMap.this.mask]) == 0) return false; 
/* 1529 */         if (Float.floatToIntBits(curr) == Float.floatToIntBits(k) && 
/* 1530 */           Float2ByteLinkedOpenHashMap.this.value[pos] == v) {
/* 1531 */           Float2ByteLinkedOpenHashMap.this.removeEntry(pos);
/* 1532 */           return true;
/*      */         } 
/*      */       } 
/*      */     }
/*      */ 
/*      */ 
/*      */     
/*      */     public int size() {
/* 1540 */       return Float2ByteLinkedOpenHashMap.this.size;
/*      */     }
/*      */ 
/*      */     
/*      */     public void clear() {
/* 1545 */       Float2ByteLinkedOpenHashMap.this.clear();
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
/*      */     public ObjectListIterator<Float2ByteMap.Entry> iterator(Float2ByteMap.Entry from) {
/* 1558 */       return new Float2ByteLinkedOpenHashMap.EntryIterator(from.getFloatKey());
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public ObjectListIterator<Float2ByteMap.Entry> fastIterator() {
/* 1569 */       return new Float2ByteLinkedOpenHashMap.FastEntryIterator();
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
/*      */     public ObjectListIterator<Float2ByteMap.Entry> fastIterator(Float2ByteMap.Entry from) {
/* 1582 */       return new Float2ByteLinkedOpenHashMap.FastEntryIterator(from.getFloatKey());
/*      */     }
/*      */ 
/*      */ 
/*      */     
/*      */     public void forEach(Consumer<? super Float2ByteMap.Entry> consumer) {
/* 1588 */       for (int i = Float2ByteLinkedOpenHashMap.this.size, next = Float2ByteLinkedOpenHashMap.this.first; i-- != 0; ) {
/* 1589 */         int curr = next;
/* 1590 */         next = (int)Float2ByteLinkedOpenHashMap.this.link[curr];
/* 1591 */         consumer.accept(new Float2ByteLinkedOpenHashMap.MapEntry(curr));
/*      */       } 
/*      */     }
/*      */ 
/*      */ 
/*      */     
/*      */     public void fastForEach(Consumer<? super Float2ByteMap.Entry> consumer) {
/* 1598 */       Float2ByteLinkedOpenHashMap.MapEntry entry = new Float2ByteLinkedOpenHashMap.MapEntry();
/* 1599 */       for (int i = Float2ByteLinkedOpenHashMap.this.size, next = Float2ByteLinkedOpenHashMap.this.first; i-- != 0; ) {
/* 1600 */         entry.index = next;
/* 1601 */         next = (int)Float2ByteLinkedOpenHashMap.this.link[next];
/* 1602 */         consumer.accept(entry);
/*      */       } 
/*      */     }
/*      */   }
/*      */ 
/*      */   
/*      */   public Float2ByteSortedMap.FastSortedEntrySet float2ByteEntrySet() {
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
/*      */     extends MapIterator<FloatConsumer>
/*      */     implements FloatListIterator
/*      */   {
/*      */     public KeyIterator(float k) {
/* 1623 */       super(k);
/*      */     }
/*      */ 
/*      */     
/*      */     public float previousFloat() {
/* 1628 */       return Float2ByteLinkedOpenHashMap.this.key[previousEntry()];
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
/* 1640 */       action.accept(Float2ByteLinkedOpenHashMap.this.key[index]);
/*      */     }
/*      */ 
/*      */     
/*      */     public float nextFloat() {
/* 1645 */       return Float2ByteLinkedOpenHashMap.this.key[nextEntry()];
/*      */     } }
/*      */   
/*      */   private final class KeySet extends AbstractFloatSortedSet {
/*      */     private static final int SPLITERATOR_CHARACTERISTICS = 337;
/*      */     
/*      */     private KeySet() {}
/*      */     
/*      */     public FloatListIterator iterator(float from) {
/* 1654 */       return new Float2ByteLinkedOpenHashMap.KeyIterator(from);
/*      */     }
/*      */ 
/*      */     
/*      */     public FloatListIterator iterator() {
/* 1659 */       return new Float2ByteLinkedOpenHashMap.KeyIterator();
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public FloatSpliterator spliterator() {
/* 1669 */       return FloatSpliterators.asSpliterator(iterator(), Size64.sizeOf(Float2ByteLinkedOpenHashMap.this), 337);
/*      */     }
/*      */ 
/*      */ 
/*      */     
/*      */     public void forEach(FloatConsumer consumer) {
/* 1675 */       for (int i = Float2ByteLinkedOpenHashMap.this.size, next = Float2ByteLinkedOpenHashMap.this.first; i-- != 0; ) {
/* 1676 */         int curr = next;
/* 1677 */         next = (int)Float2ByteLinkedOpenHashMap.this.link[curr];
/* 1678 */         consumer.accept(Float2ByteLinkedOpenHashMap.this.key[curr]);
/*      */       } 
/*      */     }
/*      */ 
/*      */     
/*      */     public int size() {
/* 1684 */       return Float2ByteLinkedOpenHashMap.this.size;
/*      */     }
/*      */ 
/*      */     
/*      */     public boolean contains(float k) {
/* 1689 */       return Float2ByteLinkedOpenHashMap.this.containsKey(k);
/*      */     }
/*      */ 
/*      */     
/*      */     public boolean remove(float k) {
/* 1694 */       int oldSize = Float2ByteLinkedOpenHashMap.this.size;
/* 1695 */       Float2ByteLinkedOpenHashMap.this.remove(k);
/* 1696 */       return (Float2ByteLinkedOpenHashMap.this.size != oldSize);
/*      */     }
/*      */ 
/*      */     
/*      */     public void clear() {
/* 1701 */       Float2ByteLinkedOpenHashMap.this.clear();
/*      */     }
/*      */ 
/*      */     
/*      */     public float firstFloat() {
/* 1706 */       if (Float2ByteLinkedOpenHashMap.this.size == 0) throw new NoSuchElementException(); 
/* 1707 */       return Float2ByteLinkedOpenHashMap.this.key[Float2ByteLinkedOpenHashMap.this.first];
/*      */     }
/*      */ 
/*      */     
/*      */     public float lastFloat() {
/* 1712 */       if (Float2ByteLinkedOpenHashMap.this.size == 0) throw new NoSuchElementException(); 
/* 1713 */       return Float2ByteLinkedOpenHashMap.this.key[Float2ByteLinkedOpenHashMap.this.last];
/*      */     }
/*      */ 
/*      */     
/*      */     public FloatComparator comparator() {
/* 1718 */       return null;
/*      */     }
/*      */ 
/*      */     
/*      */     public FloatSortedSet tailSet(float from) {
/* 1723 */       throw new UnsupportedOperationException();
/*      */     }
/*      */ 
/*      */     
/*      */     public FloatSortedSet headSet(float to) {
/* 1728 */       throw new UnsupportedOperationException();
/*      */     }
/*      */ 
/*      */     
/*      */     public FloatSortedSet subSet(float from, float to) {
/* 1733 */       throw new UnsupportedOperationException();
/*      */     }
/*      */   }
/*      */ 
/*      */   
/*      */   public FloatSortedSet keySet() {
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
/*      */     extends MapIterator<ByteConsumer>
/*      */     implements ByteListIterator
/*      */   {
/*      */     public byte previousByte() {
/* 1754 */       return Float2ByteLinkedOpenHashMap.this.value[previousEntry()];
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
/*      */     final void acceptOnIndex(ByteConsumer action, int index) {
/* 1766 */       action.accept(Float2ByteLinkedOpenHashMap.this.value[index]);
/*      */     }
/*      */ 
/*      */     
/*      */     public byte nextByte() {
/* 1771 */       return Float2ByteLinkedOpenHashMap.this.value[nextEntry()];
/*      */     }
/*      */   }
/*      */ 
/*      */   
/*      */   public ByteCollection values() {
/* 1777 */     if (this.values == null) this.values = (ByteCollection)new AbstractByteCollection()
/*      */         {
/*      */           private static final int SPLITERATOR_CHARACTERISTICS = 336;
/*      */           
/*      */           public ByteIterator iterator() {
/* 1782 */             return (ByteIterator)new Float2ByteLinkedOpenHashMap.ValueIterator();
/*      */           }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */           
/*      */           public ByteSpliterator spliterator() {
/* 1792 */             return ByteSpliterators.asSpliterator(iterator(), Size64.sizeOf(Float2ByteLinkedOpenHashMap.this), 336);
/*      */           }
/*      */ 
/*      */ 
/*      */           
/*      */           public void forEach(ByteConsumer consumer) {
/* 1798 */             for (int i = Float2ByteLinkedOpenHashMap.this.size, next = Float2ByteLinkedOpenHashMap.this.first; i-- != 0; ) {
/* 1799 */               int curr = next;
/* 1800 */               next = (int)Float2ByteLinkedOpenHashMap.this.link[curr];
/* 1801 */               consumer.accept(Float2ByteLinkedOpenHashMap.this.value[curr]);
/*      */             } 
/*      */           }
/*      */ 
/*      */           
/*      */           public int size() {
/* 1807 */             return Float2ByteLinkedOpenHashMap.this.size;
/*      */           }
/*      */ 
/*      */           
/*      */           public boolean contains(byte v) {
/* 1812 */             return Float2ByteLinkedOpenHashMap.this.containsValue(v);
/*      */           }
/*      */ 
/*      */           
/*      */           public void clear() {
/* 1817 */             Float2ByteLinkedOpenHashMap.this.clear();
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
/* 1881 */     float[] key = this.key;
/* 1882 */     byte[] value = this.value;
/* 1883 */     int mask = newN - 1;
/* 1884 */     float[] newKey = new float[newN + 1];
/* 1885 */     byte[] newValue = new byte[newN + 1];
/* 1886 */     int i = this.first, prev = -1, newPrev = -1;
/* 1887 */     long[] link = this.link;
/* 1888 */     long[] newLink = new long[newN + 1];
/* 1889 */     this.first = -1;
/* 1890 */     for (int j = this.size; j-- != 0; ) {
/* 1891 */       int pos; if (Float.floatToIntBits(key[i]) == 0) { pos = newN; }
/*      */       else
/* 1893 */       { pos = HashCommon.mix(HashCommon.float2int(key[i])) & mask;
/* 1894 */         for (; Float.floatToIntBits(newKey[pos]) != 0; pos = pos + 1 & mask); }
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
/*      */   public Float2ByteLinkedOpenHashMap clone() {
/*      */     Float2ByteLinkedOpenHashMap c;
/*      */     try {
/* 1937 */       c = (Float2ByteLinkedOpenHashMap)super.clone();
/* 1938 */     } catch (CloneNotSupportedException cantHappen) {
/* 1939 */       throw new InternalError();
/*      */     } 
/* 1941 */     c.keys = null;
/* 1942 */     c.values = null;
/* 1943 */     c.entries = null;
/* 1944 */     c.containsNullKey = this.containsNullKey;
/* 1945 */     c.key = (float[])this.key.clone();
/* 1946 */     c.value = (byte[])this.value.clone();
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
/* 1964 */       for (; Float.floatToIntBits(this.key[i]) == 0; i++);
/* 1965 */       t = HashCommon.float2int(this.key[i]);
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
/* 1976 */     float[] key = this.key;
/* 1977 */     byte[] value = this.value;
/* 1978 */     EntryIterator i = new EntryIterator();
/* 1979 */     s.defaultWriteObject();
/* 1980 */     for (int j = this.size; j-- != 0; ) {
/* 1981 */       int e = i.nextEntry();
/* 1982 */       s.writeFloat(key[e]);
/* 1983 */       s.writeByte(value[e]);
/*      */     } 
/*      */   }
/*      */   
/*      */   private void readObject(ObjectInputStream s) throws IOException, ClassNotFoundException {
/* 1988 */     s.defaultReadObject();
/* 1989 */     this.n = HashCommon.arraySize(this.size, this.f);
/* 1990 */     this.maxFill = HashCommon.maxFill(this.n, this.f);
/* 1991 */     this.mask = this.n - 1;
/* 1992 */     float[] key = this.key = new float[this.n + 1];
/* 1993 */     byte[] value = this.value = new byte[this.n + 1];
/* 1994 */     long[] link = this.link = new long[this.n + 1];
/* 1995 */     int prev = -1;
/* 1996 */     this.first = this.last = -1;
/*      */ 
/*      */     
/* 1999 */     for (int i = this.size; i-- != 0; ) {
/* 2000 */       int pos; float k = s.readFloat();
/* 2001 */       byte v = s.readByte();
/* 2002 */       if (Float.floatToIntBits(k) == 0) {
/* 2003 */         pos = this.n;
/* 2004 */         this.containsNullKey = true;
/*      */       } else {
/* 2006 */         pos = HashCommon.mix(HashCommon.float2int(k)) & this.mask;
/* 2007 */         for (; Float.floatToIntBits(key[pos]) != 0; pos = pos + 1 & this.mask);
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


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\i\\unimi\dsi\fastutil\floats\Float2ByteLinkedOpenHashMap.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */