/*      */ package it.unimi.dsi.fastutil.bytes;
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
/*      */ 
/*      */ public class Byte2ByteLinkedOpenHashMap
/*      */   extends AbstractByte2ByteSortedMap
/*      */   implements Serializable, Cloneable, Hash
/*      */ {
/*      */   private static final long serialVersionUID = 0L;
/*      */   private static final boolean ASSERTS = false;
/*      */   protected transient byte[] key;
/*      */   protected transient byte[] value;
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
/*      */   protected transient Byte2ByteSortedMap.FastSortedEntrySet entries;
/*      */ 
/*      */   
/*      */   protected transient ByteSortedSet keys;
/*      */ 
/*      */   
/*      */   protected transient ByteCollection values;
/*      */ 
/*      */ 
/*      */   
/*      */   public Byte2ByteLinkedOpenHashMap(int expected, float f) {
/*  141 */     if (f <= 0.0F || f >= 1.0F) throw new IllegalArgumentException("Load factor must be greater than 0 and smaller than 1"); 
/*  142 */     if (expected < 0) throw new IllegalArgumentException("The expected number of elements must be nonnegative"); 
/*  143 */     this.f = f;
/*  144 */     this.minN = this.n = HashCommon.arraySize(expected, f);
/*  145 */     this.mask = this.n - 1;
/*  146 */     this.maxFill = HashCommon.maxFill(this.n, f);
/*  147 */     this.key = new byte[this.n + 1];
/*  148 */     this.value = new byte[this.n + 1];
/*  149 */     this.link = new long[this.n + 1];
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Byte2ByteLinkedOpenHashMap(int expected) {
/*  158 */     this(expected, 0.75F);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Byte2ByteLinkedOpenHashMap() {
/*  166 */     this(16, 0.75F);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Byte2ByteLinkedOpenHashMap(Map<? extends Byte, ? extends Byte> m, float f) {
/*  176 */     this(m.size(), f);
/*  177 */     putAll(m);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Byte2ByteLinkedOpenHashMap(Map<? extends Byte, ? extends Byte> m) {
/*  186 */     this(m, 0.75F);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Byte2ByteLinkedOpenHashMap(Byte2ByteMap m, float f) {
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
/*      */   public Byte2ByteLinkedOpenHashMap(Byte2ByteMap m) {
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
/*      */   public Byte2ByteLinkedOpenHashMap(byte[] k, byte[] v, float f) {
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
/*      */   public Byte2ByteLinkedOpenHashMap(byte[] k, byte[] v) {
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
/*      */   private byte removeEntry(int pos) {
/*  257 */     byte oldValue = this.value[pos];
/*  258 */     this.size--;
/*  259 */     fixPointers(pos);
/*  260 */     shiftKeys(pos);
/*  261 */     if (this.n > this.minN && this.size < this.maxFill / 4 && this.n > 16) rehash(this.n / 2); 
/*  262 */     return oldValue;
/*      */   }
/*      */   
/*      */   private byte removeNullEntry() {
/*  266 */     this.containsNullKey = false;
/*  267 */     byte oldValue = this.value[this.n];
/*  268 */     this.size--;
/*  269 */     fixPointers(this.n);
/*  270 */     if (this.n > this.minN && this.size < this.maxFill / 4 && this.n > 16) rehash(this.n / 2); 
/*  271 */     return oldValue;
/*      */   }
/*      */ 
/*      */   
/*      */   public void putAll(Map<? extends Byte, ? extends Byte> m) {
/*  276 */     if (this.f <= 0.5D) { ensureCapacity(m.size()); }
/*  277 */     else { tryCapacity((size() + m.size())); }
/*      */     
/*  279 */     super.putAll(m);
/*      */   }
/*      */   
/*      */   private int find(byte k) {
/*  283 */     if (k == 0) return this.containsNullKey ? this.n : -(this.n + 1);
/*      */     
/*  285 */     byte[] key = this.key;
/*      */     byte curr;
/*      */     int pos;
/*  288 */     if ((curr = key[pos = HashCommon.mix(k) & this.mask]) == 0) return -(pos + 1); 
/*  289 */     if (k == curr) return pos;
/*      */     
/*      */     while (true) {
/*  292 */       if ((curr = key[pos = pos + 1 & this.mask]) == 0) return -(pos + 1); 
/*  293 */       if (k == curr) return pos; 
/*      */     } 
/*      */   }
/*      */   
/*      */   private void insert(int pos, byte k, byte v) {
/*  298 */     if (pos == this.n) this.containsNullKey = true; 
/*  299 */     this.key[pos] = k;
/*  300 */     this.value[pos] = v;
/*  301 */     if (this.size == 0) {
/*  302 */       this.first = this.last = pos;
/*      */       
/*  304 */       this.link[pos] = -1L;
/*      */     } else {
/*  306 */       this.link[this.last] = this.link[this.last] ^ (this.link[this.last] ^ pos & 0xFFFFFFFFL) & 0xFFFFFFFFL;
/*  307 */       this.link[pos] = (this.last & 0xFFFFFFFFL) << 32L | 0xFFFFFFFFL;
/*  308 */       this.last = pos;
/*      */     } 
/*  310 */     if (this.size++ >= this.maxFill) rehash(HashCommon.arraySize(this.size + 1, this.f));
/*      */   
/*      */   }
/*      */ 
/*      */   
/*      */   public byte put(byte k, byte v) {
/*  316 */     int pos = find(k);
/*  317 */     if (pos < 0) {
/*  318 */       insert(-pos - 1, k, v);
/*  319 */       return this.defRetValue;
/*      */     } 
/*  321 */     byte oldValue = this.value[pos];
/*  322 */     this.value[pos] = v;
/*  323 */     return oldValue;
/*      */   }
/*      */   
/*      */   private byte addToValue(int pos, byte incr) {
/*  327 */     byte oldValue = this.value[pos];
/*  328 */     this.value[pos] = (byte)(oldValue + incr);
/*  329 */     return oldValue;
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
/*      */   public byte addTo(byte k, byte incr) {
/*      */     int pos;
/*  347 */     if (k == 0) {
/*  348 */       if (this.containsNullKey) return addToValue(this.n, incr); 
/*  349 */       pos = this.n;
/*  350 */       this.containsNullKey = true;
/*      */     } else {
/*      */       
/*  353 */       byte[] key = this.key;
/*      */       byte curr;
/*  355 */       if ((curr = key[pos = HashCommon.mix(k) & this.mask]) != 0) {
/*  356 */         if (curr == k) return addToValue(pos, incr); 
/*  357 */         while ((curr = key[pos = pos + 1 & this.mask]) != 0) { if (curr == k) return addToValue(pos, incr);  }
/*      */       
/*      */       } 
/*  360 */     }  this.key[pos] = k;
/*  361 */     this.value[pos] = (byte)(this.defRetValue + incr);
/*  362 */     if (this.size == 0) {
/*  363 */       this.first = this.last = pos;
/*      */       
/*  365 */       this.link[pos] = -1L;
/*      */     } else {
/*  367 */       this.link[this.last] = this.link[this.last] ^ (this.link[this.last] ^ pos & 0xFFFFFFFFL) & 0xFFFFFFFFL;
/*  368 */       this.link[pos] = (this.last & 0xFFFFFFFFL) << 32L | 0xFFFFFFFFL;
/*  369 */       this.last = pos;
/*      */     } 
/*  371 */     if (this.size++ >= this.maxFill) rehash(HashCommon.arraySize(this.size + 1, this.f));
/*      */     
/*  373 */     return this.defRetValue;
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
/*  386 */     byte[] key = this.key; while (true) {
/*      */       byte curr; int last;
/*  388 */       pos = (last = pos) + 1 & this.mask;
/*      */       while (true) {
/*  390 */         if ((curr = key[pos]) == 0) {
/*  391 */           key[last] = 0;
/*      */           return;
/*      */         } 
/*  394 */         int slot = HashCommon.mix(curr) & this.mask;
/*  395 */         if ((last <= pos) ? (last >= slot || slot > pos) : (last >= slot && slot > pos))
/*  396 */           break;  pos = pos + 1 & this.mask;
/*      */       } 
/*  398 */       key[last] = curr;
/*  399 */       this.value[last] = this.value[pos];
/*  400 */       fixPointers(pos, last);
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public byte remove(byte k) {
/*  407 */     if (k == 0) {
/*  408 */       if (this.containsNullKey) return removeNullEntry(); 
/*  409 */       return this.defRetValue;
/*      */     } 
/*      */     
/*  412 */     byte[] key = this.key;
/*      */     byte curr;
/*      */     int pos;
/*  415 */     if ((curr = key[pos = HashCommon.mix(k) & this.mask]) == 0) return this.defRetValue; 
/*  416 */     if (k == curr) return removeEntry(pos); 
/*      */     while (true) {
/*  418 */       if ((curr = key[pos = pos + 1 & this.mask]) == 0) return this.defRetValue; 
/*  419 */       if (k == curr) return removeEntry(pos); 
/*      */     } 
/*      */   }
/*      */   
/*      */   private byte setValue(int pos, byte v) {
/*  424 */     byte oldValue = this.value[pos];
/*  425 */     this.value[pos] = v;
/*  426 */     return oldValue;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public byte removeFirstByte() {
/*  436 */     if (this.size == 0) throw new NoSuchElementException(); 
/*  437 */     int pos = this.first;
/*      */     
/*  439 */     if (this.size == 1) { this.first = this.last = -1; }
/*      */     else
/*  441 */     { this.first = (int)this.link[pos];
/*  442 */       if (0 <= this.first)
/*      */       {
/*  444 */         this.link[this.first] = this.link[this.first] | 0xFFFFFFFF00000000L;
/*      */       } }
/*      */     
/*  447 */     this.size--;
/*  448 */     byte v = this.value[pos];
/*  449 */     if (pos == this.n)
/*  450 */     { this.containsNullKey = false; }
/*  451 */     else { shiftKeys(pos); }
/*  452 */      if (this.n > this.minN && this.size < this.maxFill / 4 && this.n > 16) rehash(this.n / 2); 
/*  453 */     return v;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public byte removeLastByte() {
/*  463 */     if (this.size == 0) throw new NoSuchElementException(); 
/*  464 */     int pos = this.last;
/*      */     
/*  466 */     if (this.size == 1) { this.first = this.last = -1; }
/*      */     else
/*  468 */     { this.last = (int)(this.link[pos] >>> 32L);
/*  469 */       if (0 <= this.last)
/*      */       {
/*  471 */         this.link[this.last] = this.link[this.last] | 0xFFFFFFFFL;
/*      */       } }
/*      */     
/*  474 */     this.size--;
/*  475 */     byte v = this.value[pos];
/*  476 */     if (pos == this.n)
/*  477 */     { this.containsNullKey = false; }
/*  478 */     else { shiftKeys(pos); }
/*  479 */      if (this.n > this.minN && this.size < this.maxFill / 4 && this.n > 16) rehash(this.n / 2); 
/*  480 */     return v;
/*      */   }
/*      */   
/*      */   private void moveIndexToFirst(int i) {
/*  484 */     if (this.size == 1 || this.first == i)
/*  485 */       return;  if (this.last == i) {
/*  486 */       this.last = (int)(this.link[i] >>> 32L);
/*      */       
/*  488 */       this.link[this.last] = this.link[this.last] | 0xFFFFFFFFL;
/*      */     } else {
/*  490 */       long linki = this.link[i];
/*  491 */       int prev = (int)(linki >>> 32L);
/*  492 */       int next = (int)linki;
/*  493 */       this.link[prev] = this.link[prev] ^ (this.link[prev] ^ linki & 0xFFFFFFFFL) & 0xFFFFFFFFL;
/*  494 */       this.link[next] = this.link[next] ^ (this.link[next] ^ linki & 0xFFFFFFFF00000000L) & 0xFFFFFFFF00000000L;
/*      */     } 
/*  496 */     this.link[this.first] = this.link[this.first] ^ (this.link[this.first] ^ (i & 0xFFFFFFFFL) << 32L) & 0xFFFFFFFF00000000L;
/*  497 */     this.link[i] = 0xFFFFFFFF00000000L | this.first & 0xFFFFFFFFL;
/*  498 */     this.first = i;
/*      */   }
/*      */   
/*      */   private void moveIndexToLast(int i) {
/*  502 */     if (this.size == 1 || this.last == i)
/*  503 */       return;  if (this.first == i) {
/*  504 */       this.first = (int)this.link[i];
/*      */       
/*  506 */       this.link[this.first] = this.link[this.first] | 0xFFFFFFFF00000000L;
/*      */     } else {
/*  508 */       long linki = this.link[i];
/*  509 */       int prev = (int)(linki >>> 32L);
/*  510 */       int next = (int)linki;
/*  511 */       this.link[prev] = this.link[prev] ^ (this.link[prev] ^ linki & 0xFFFFFFFFL) & 0xFFFFFFFFL;
/*  512 */       this.link[next] = this.link[next] ^ (this.link[next] ^ linki & 0xFFFFFFFF00000000L) & 0xFFFFFFFF00000000L;
/*      */     } 
/*  514 */     this.link[this.last] = this.link[this.last] ^ (this.link[this.last] ^ i & 0xFFFFFFFFL) & 0xFFFFFFFFL;
/*  515 */     this.link[i] = (this.last & 0xFFFFFFFFL) << 32L | 0xFFFFFFFFL;
/*  516 */     this.last = i;
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
/*      */   public byte getAndMoveToFirst(byte k) {
/*  528 */     if (k == 0) {
/*  529 */       if (this.containsNullKey) {
/*  530 */         moveIndexToFirst(this.n);
/*  531 */         return this.value[this.n];
/*      */       } 
/*  533 */       return this.defRetValue;
/*      */     } 
/*      */     
/*  536 */     byte[] key = this.key;
/*      */     byte curr;
/*      */     int pos;
/*  539 */     if ((curr = key[pos = HashCommon.mix(k) & this.mask]) == 0) return this.defRetValue; 
/*  540 */     if (k == curr) {
/*  541 */       moveIndexToFirst(pos);
/*  542 */       return this.value[pos];
/*      */     } 
/*      */     
/*      */     while (true) {
/*  546 */       if ((curr = key[pos = pos + 1 & this.mask]) == 0) return this.defRetValue; 
/*  547 */       if (k == curr) {
/*  548 */         moveIndexToFirst(pos);
/*  549 */         return this.value[pos];
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
/*      */   public byte getAndMoveToLast(byte k) {
/*  563 */     if (k == 0) {
/*  564 */       if (this.containsNullKey) {
/*  565 */         moveIndexToLast(this.n);
/*  566 */         return this.value[this.n];
/*      */       } 
/*  568 */       return this.defRetValue;
/*      */     } 
/*      */     
/*  571 */     byte[] key = this.key;
/*      */     byte curr;
/*      */     int pos;
/*  574 */     if ((curr = key[pos = HashCommon.mix(k) & this.mask]) == 0) return this.defRetValue; 
/*  575 */     if (k == curr) {
/*  576 */       moveIndexToLast(pos);
/*  577 */       return this.value[pos];
/*      */     } 
/*      */     
/*      */     while (true) {
/*  581 */       if ((curr = key[pos = pos + 1 & this.mask]) == 0) return this.defRetValue; 
/*  582 */       if (k == curr) {
/*  583 */         moveIndexToLast(pos);
/*  584 */         return this.value[pos];
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
/*      */   public byte putAndMoveToFirst(byte k, byte v) {
/*      */     int pos;
/*  600 */     if (k == 0) {
/*  601 */       if (this.containsNullKey) {
/*  602 */         moveIndexToFirst(this.n);
/*  603 */         return setValue(this.n, v);
/*      */       } 
/*  605 */       this.containsNullKey = true;
/*  606 */       pos = this.n;
/*      */     } else {
/*      */       
/*  609 */       byte[] key = this.key;
/*      */       byte curr;
/*  611 */       if ((curr = key[pos = HashCommon.mix(k) & this.mask]) != 0) {
/*  612 */         if (curr == k) {
/*  613 */           moveIndexToFirst(pos);
/*  614 */           return setValue(pos, v);
/*      */         } 
/*  616 */         while ((curr = key[pos = pos + 1 & this.mask]) != 0) { if (curr == k) {
/*  617 */             moveIndexToFirst(pos);
/*  618 */             return setValue(pos, v);
/*      */           }  }
/*      */       
/*      */       } 
/*  622 */     }  this.key[pos] = k;
/*  623 */     this.value[pos] = v;
/*  624 */     if (this.size == 0) {
/*  625 */       this.first = this.last = pos;
/*      */       
/*  627 */       this.link[pos] = -1L;
/*      */     } else {
/*  629 */       this.link[this.first] = this.link[this.first] ^ (this.link[this.first] ^ (pos & 0xFFFFFFFFL) << 32L) & 0xFFFFFFFF00000000L;
/*  630 */       this.link[pos] = 0xFFFFFFFF00000000L | this.first & 0xFFFFFFFFL;
/*  631 */       this.first = pos;
/*      */     } 
/*  633 */     if (this.size++ >= this.maxFill) rehash(HashCommon.arraySize(this.size, this.f));
/*      */     
/*  635 */     return this.defRetValue;
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
/*      */   public byte putAndMoveToLast(byte k, byte v) {
/*      */     int pos;
/*  649 */     if (k == 0) {
/*  650 */       if (this.containsNullKey) {
/*  651 */         moveIndexToLast(this.n);
/*  652 */         return setValue(this.n, v);
/*      */       } 
/*  654 */       this.containsNullKey = true;
/*  655 */       pos = this.n;
/*      */     } else {
/*      */       
/*  658 */       byte[] key = this.key;
/*      */       byte curr;
/*  660 */       if ((curr = key[pos = HashCommon.mix(k) & this.mask]) != 0) {
/*  661 */         if (curr == k) {
/*  662 */           moveIndexToLast(pos);
/*  663 */           return setValue(pos, v);
/*      */         } 
/*  665 */         while ((curr = key[pos = pos + 1 & this.mask]) != 0) { if (curr == k) {
/*  666 */             moveIndexToLast(pos);
/*  667 */             return setValue(pos, v);
/*      */           }  }
/*      */       
/*      */       } 
/*  671 */     }  this.key[pos] = k;
/*  672 */     this.value[pos] = v;
/*  673 */     if (this.size == 0) {
/*  674 */       this.first = this.last = pos;
/*      */       
/*  676 */       this.link[pos] = -1L;
/*      */     } else {
/*  678 */       this.link[this.last] = this.link[this.last] ^ (this.link[this.last] ^ pos & 0xFFFFFFFFL) & 0xFFFFFFFFL;
/*  679 */       this.link[pos] = (this.last & 0xFFFFFFFFL) << 32L | 0xFFFFFFFFL;
/*  680 */       this.last = pos;
/*      */     } 
/*  682 */     if (this.size++ >= this.maxFill) rehash(HashCommon.arraySize(this.size, this.f));
/*      */     
/*  684 */     return this.defRetValue;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public byte get(byte k) {
/*  690 */     if (k == 0) return this.containsNullKey ? this.value[this.n] : this.defRetValue;
/*      */     
/*  692 */     byte[] key = this.key;
/*      */     byte curr;
/*      */     int pos;
/*  695 */     if ((curr = key[pos = HashCommon.mix(k) & this.mask]) == 0) return this.defRetValue; 
/*  696 */     if (k == curr) return this.value[pos];
/*      */     
/*      */     while (true) {
/*  699 */       if ((curr = key[pos = pos + 1 & this.mask]) == 0) return this.defRetValue; 
/*  700 */       if (k == curr) return this.value[pos];
/*      */     
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   public boolean containsKey(byte k) {
/*  707 */     if (k == 0) return this.containsNullKey;
/*      */     
/*  709 */     byte[] key = this.key;
/*      */     byte curr;
/*      */     int pos;
/*  712 */     if ((curr = key[pos = HashCommon.mix(k) & this.mask]) == 0) return false; 
/*  713 */     if (k == curr) return true;
/*      */     
/*      */     while (true) {
/*  716 */       if ((curr = key[pos = pos + 1 & this.mask]) == 0) return false; 
/*  717 */       if (k == curr) return true;
/*      */     
/*      */     } 
/*      */   }
/*      */   
/*      */   public boolean containsValue(byte v) {
/*  723 */     byte[] value = this.value;
/*  724 */     byte[] key = this.key;
/*  725 */     if (this.containsNullKey && value[this.n] == v) return true; 
/*  726 */     for (int i = this.n; i-- != 0;) { if (key[i] != 0 && value[i] == v) return true;  }
/*  727 */      return false;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public byte getOrDefault(byte k, byte defaultValue) {
/*  734 */     if (k == 0) return this.containsNullKey ? this.value[this.n] : defaultValue;
/*      */     
/*  736 */     byte[] key = this.key;
/*      */     byte curr;
/*      */     int pos;
/*  739 */     if ((curr = key[pos = HashCommon.mix(k) & this.mask]) == 0) return defaultValue; 
/*  740 */     if (k == curr) return this.value[pos];
/*      */     
/*      */     while (true) {
/*  743 */       if ((curr = key[pos = pos + 1 & this.mask]) == 0) return defaultValue; 
/*  744 */       if (k == curr) return this.value[pos];
/*      */     
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   public byte putIfAbsent(byte k, byte v) {
/*  751 */     int pos = find(k);
/*  752 */     if (pos >= 0) return this.value[pos]; 
/*  753 */     insert(-pos - 1, k, v);
/*  754 */     return this.defRetValue;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean remove(byte k, byte v) {
/*  761 */     if (k == 0) {
/*  762 */       if (this.containsNullKey && v == this.value[this.n]) {
/*  763 */         removeNullEntry();
/*  764 */         return true;
/*      */       } 
/*  766 */       return false;
/*      */     } 
/*      */     
/*  769 */     byte[] key = this.key;
/*      */     byte curr;
/*      */     int pos;
/*  772 */     if ((curr = key[pos = HashCommon.mix(k) & this.mask]) == 0) return false; 
/*  773 */     if (k == curr && v == this.value[pos]) {
/*  774 */       removeEntry(pos);
/*  775 */       return true;
/*      */     } 
/*      */     while (true) {
/*  778 */       if ((curr = key[pos = pos + 1 & this.mask]) == 0) return false; 
/*  779 */       if (k == curr && v == this.value[pos]) {
/*  780 */         removeEntry(pos);
/*  781 */         return true;
/*      */       } 
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean replace(byte k, byte oldValue, byte v) {
/*  789 */     int pos = find(k);
/*  790 */     if (pos < 0 || oldValue != this.value[pos]) return false; 
/*  791 */     this.value[pos] = v;
/*  792 */     return true;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public byte replace(byte k, byte v) {
/*  798 */     int pos = find(k);
/*  799 */     if (pos < 0) return this.defRetValue; 
/*  800 */     byte oldValue = this.value[pos];
/*  801 */     this.value[pos] = v;
/*  802 */     return oldValue;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public byte computeIfAbsent(byte k, IntUnaryOperator mappingFunction) {
/*  808 */     Objects.requireNonNull(mappingFunction);
/*  809 */     int pos = find(k);
/*  810 */     if (pos >= 0) return this.value[pos]; 
/*  811 */     byte newValue = SafeMath.safeIntToByte(mappingFunction.applyAsInt(k));
/*  812 */     insert(-pos - 1, k, newValue);
/*  813 */     return newValue;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public byte computeIfAbsent(byte key, Byte2ByteFunction mappingFunction) {
/*  819 */     Objects.requireNonNull(mappingFunction);
/*  820 */     int pos = find(key);
/*  821 */     if (pos >= 0) return this.value[pos]; 
/*  822 */     if (!mappingFunction.containsKey(key)) return this.defRetValue; 
/*  823 */     byte newValue = mappingFunction.get(key);
/*  824 */     insert(-pos - 1, key, newValue);
/*  825 */     return newValue;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public byte computeIfAbsentNullable(byte k, IntFunction<? extends Byte> mappingFunction) {
/*  831 */     Objects.requireNonNull(mappingFunction);
/*  832 */     int pos = find(k);
/*  833 */     if (pos >= 0) return this.value[pos]; 
/*  834 */     Byte newValue = mappingFunction.apply(k);
/*  835 */     if (newValue == null) return this.defRetValue; 
/*  836 */     byte v = newValue.byteValue();
/*  837 */     insert(-pos - 1, k, v);
/*  838 */     return v;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public byte computeIfPresent(byte k, BiFunction<? super Byte, ? super Byte, ? extends Byte> remappingFunction) {
/*  844 */     Objects.requireNonNull(remappingFunction);
/*  845 */     int pos = find(k);
/*  846 */     if (pos < 0) return this.defRetValue; 
/*  847 */     Byte newValue = remappingFunction.apply(Byte.valueOf(k), Byte.valueOf(this.value[pos]));
/*  848 */     if (newValue == null) {
/*  849 */       if (k == 0) { removeNullEntry(); }
/*  850 */       else { removeEntry(pos); }
/*  851 */        return this.defRetValue;
/*      */     } 
/*  853 */     this.value[pos] = newValue.byteValue(); return newValue.byteValue();
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public byte compute(byte k, BiFunction<? super Byte, ? super Byte, ? extends Byte> remappingFunction) {
/*  859 */     Objects.requireNonNull(remappingFunction);
/*  860 */     int pos = find(k);
/*  861 */     Byte newValue = remappingFunction.apply(Byte.valueOf(k), (pos >= 0) ? Byte.valueOf(this.value[pos]) : null);
/*  862 */     if (newValue == null) {
/*  863 */       if (pos >= 0)
/*  864 */         if (k == 0) { removeNullEntry(); }
/*  865 */         else { removeEntry(pos); }
/*      */          
/*  867 */       return this.defRetValue;
/*      */     } 
/*  869 */     byte newVal = newValue.byteValue();
/*  870 */     if (pos < 0) {
/*  871 */       insert(-pos - 1, k, newVal);
/*  872 */       return newVal;
/*      */     } 
/*  874 */     this.value[pos] = newVal; return newVal;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public byte merge(byte k, byte v, BiFunction<? super Byte, ? super Byte, ? extends Byte> remappingFunction) {
/*  880 */     Objects.requireNonNull(remappingFunction);
/*      */     
/*  882 */     int pos = find(k);
/*  883 */     if (pos < 0) {
/*  884 */       if (pos < 0) { insert(-pos - 1, k, v); }
/*  885 */       else { this.value[pos] = v; }
/*  886 */        return v;
/*      */     } 
/*  888 */     Byte newValue = remappingFunction.apply(Byte.valueOf(this.value[pos]), Byte.valueOf(v));
/*  889 */     if (newValue == null) {
/*  890 */       if (k == 0) { removeNullEntry(); }
/*  891 */       else { removeEntry(pos); }
/*  892 */        return this.defRetValue;
/*      */     } 
/*  894 */     this.value[pos] = newValue.byteValue(); return newValue.byteValue();
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
/*  905 */     if (this.size == 0)
/*  906 */       return;  this.size = 0;
/*  907 */     this.containsNullKey = false;
/*  908 */     Arrays.fill(this.key, (byte)0);
/*  909 */     this.first = this.last = -1;
/*      */   }
/*      */ 
/*      */   
/*      */   public int size() {
/*  914 */     return this.size;
/*      */   }
/*      */ 
/*      */   
/*      */   public boolean isEmpty() {
/*  919 */     return (this.size == 0);
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   final class MapEntry
/*      */     implements Byte2ByteMap.Entry, Map.Entry<Byte, Byte>, ByteBytePair
/*      */   {
/*      */     int index;
/*      */ 
/*      */ 
/*      */     
/*      */     MapEntry(int index) {
/*  932 */       this.index = index;
/*      */     }
/*      */ 
/*      */     
/*      */     MapEntry() {}
/*      */ 
/*      */     
/*      */     public byte getByteKey() {
/*  940 */       return Byte2ByteLinkedOpenHashMap.this.key[this.index];
/*      */     }
/*      */ 
/*      */     
/*      */     public byte leftByte() {
/*  945 */       return Byte2ByteLinkedOpenHashMap.this.key[this.index];
/*      */     }
/*      */ 
/*      */     
/*      */     public byte getByteValue() {
/*  950 */       return Byte2ByteLinkedOpenHashMap.this.value[this.index];
/*      */     }
/*      */ 
/*      */     
/*      */     public byte rightByte() {
/*  955 */       return Byte2ByteLinkedOpenHashMap.this.value[this.index];
/*      */     }
/*      */ 
/*      */     
/*      */     public byte setValue(byte v) {
/*  960 */       byte oldValue = Byte2ByteLinkedOpenHashMap.this.value[this.index];
/*  961 */       Byte2ByteLinkedOpenHashMap.this.value[this.index] = v;
/*  962 */       return oldValue;
/*      */     }
/*      */ 
/*      */     
/*      */     public ByteBytePair right(byte v) {
/*  967 */       Byte2ByteLinkedOpenHashMap.this.value[this.index] = v;
/*  968 */       return this;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     @Deprecated
/*      */     public Byte getKey() {
/*  979 */       return Byte.valueOf(Byte2ByteLinkedOpenHashMap.this.key[this.index]);
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
/*  990 */       return Byte.valueOf(Byte2ByteLinkedOpenHashMap.this.value[this.index]);
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
/* 1001 */       return Byte.valueOf(setValue(v.byteValue()));
/*      */     }
/*      */ 
/*      */ 
/*      */     
/*      */     public boolean equals(Object o) {
/* 1007 */       if (!(o instanceof Map.Entry)) return false; 
/* 1008 */       Map.Entry<Byte, Byte> e = (Map.Entry<Byte, Byte>)o;
/* 1009 */       return (Byte2ByteLinkedOpenHashMap.this.key[this.index] == ((Byte)e.getKey()).byteValue() && Byte2ByteLinkedOpenHashMap.this.value[this.index] == ((Byte)e.getValue()).byteValue());
/*      */     }
/*      */ 
/*      */     
/*      */     public int hashCode() {
/* 1014 */       return Byte2ByteLinkedOpenHashMap.this.key[this.index] ^ Byte2ByteLinkedOpenHashMap.this.value[this.index];
/*      */     }
/*      */ 
/*      */     
/*      */     public String toString() {
/* 1019 */       return Byte2ByteLinkedOpenHashMap.this.key[this.index] + "=>" + Byte2ByteLinkedOpenHashMap.this.value[this.index];
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
/* 1030 */     if (this.size == 0) {
/* 1031 */       this.first = this.last = -1;
/*      */       return;
/*      */     } 
/* 1034 */     if (this.first == i) {
/* 1035 */       this.first = (int)this.link[i];
/* 1036 */       if (0 <= this.first)
/*      */       {
/* 1038 */         this.link[this.first] = this.link[this.first] | 0xFFFFFFFF00000000L;
/*      */       }
/*      */       return;
/*      */     } 
/* 1042 */     if (this.last == i) {
/* 1043 */       this.last = (int)(this.link[i] >>> 32L);
/* 1044 */       if (0 <= this.last)
/*      */       {
/* 1046 */         this.link[this.last] = this.link[this.last] | 0xFFFFFFFFL;
/*      */       }
/*      */       return;
/*      */     } 
/* 1050 */     long linki = this.link[i];
/* 1051 */     int prev = (int)(linki >>> 32L);
/* 1052 */     int next = (int)linki;
/* 1053 */     this.link[prev] = this.link[prev] ^ (this.link[prev] ^ linki & 0xFFFFFFFFL) & 0xFFFFFFFFL;
/* 1054 */     this.link[next] = this.link[next] ^ (this.link[next] ^ linki & 0xFFFFFFFF00000000L) & 0xFFFFFFFF00000000L;
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
/* 1066 */     if (this.size == 1) {
/* 1067 */       this.first = this.last = d;
/*      */       
/* 1069 */       this.link[d] = -1L;
/*      */       return;
/*      */     } 
/* 1072 */     if (this.first == s) {
/* 1073 */       this.first = d;
/* 1074 */       this.link[(int)this.link[s]] = this.link[(int)this.link[s]] ^ (this.link[(int)this.link[s]] ^ (d & 0xFFFFFFFFL) << 32L) & 0xFFFFFFFF00000000L;
/* 1075 */       this.link[d] = this.link[s];
/*      */       return;
/*      */     } 
/* 1078 */     if (this.last == s) {
/* 1079 */       this.last = d;
/* 1080 */       this.link[(int)(this.link[s] >>> 32L)] = this.link[(int)(this.link[s] >>> 32L)] ^ (this.link[(int)(this.link[s] >>> 32L)] ^ d & 0xFFFFFFFFL) & 0xFFFFFFFFL;
/* 1081 */       this.link[d] = this.link[s];
/*      */       return;
/*      */     } 
/* 1084 */     long links = this.link[s];
/* 1085 */     int prev = (int)(links >>> 32L);
/* 1086 */     int next = (int)links;
/* 1087 */     this.link[prev] = this.link[prev] ^ (this.link[prev] ^ d & 0xFFFFFFFFL) & 0xFFFFFFFFL;
/* 1088 */     this.link[next] = this.link[next] ^ (this.link[next] ^ (d & 0xFFFFFFFFL) << 32L) & 0xFFFFFFFF00000000L;
/* 1089 */     this.link[d] = links;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public byte firstByteKey() {
/* 1099 */     if (this.size == 0) throw new NoSuchElementException(); 
/* 1100 */     return this.key[this.first];
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public byte lastByteKey() {
/* 1110 */     if (this.size == 0) throw new NoSuchElementException(); 
/* 1111 */     return this.key[this.last];
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Byte2ByteSortedMap tailMap(byte from) {
/* 1121 */     throw new UnsupportedOperationException();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Byte2ByteSortedMap headMap(byte to) {
/* 1131 */     throw new UnsupportedOperationException();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Byte2ByteSortedMap subMap(byte from, byte to) {
/* 1141 */     throw new UnsupportedOperationException();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public ByteComparator comparator() {
/* 1151 */     return null;
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
/* 1166 */     int prev = -1;
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 1171 */     int next = -1;
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 1176 */     int curr = -1;
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 1181 */     int index = -1;
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     protected MapIterator() {
/* 1187 */       this.next = Byte2ByteLinkedOpenHashMap.this.first;
/* 1188 */       this.index = 0;
/*      */     }
/*      */     
/*      */     private MapIterator(byte from) {
/* 1192 */       if (from == 0) {
/* 1193 */         if (Byte2ByteLinkedOpenHashMap.this.containsNullKey) {
/* 1194 */           this.next = (int)Byte2ByteLinkedOpenHashMap.this.link[Byte2ByteLinkedOpenHashMap.this.n];
/* 1195 */           this.prev = Byte2ByteLinkedOpenHashMap.this.n; return;
/*      */         } 
/* 1197 */         throw new NoSuchElementException("The key " + from + " does not belong to this map.");
/*      */       } 
/* 1199 */       if (Byte2ByteLinkedOpenHashMap.this.key[Byte2ByteLinkedOpenHashMap.this.last] == from) {
/* 1200 */         this.prev = Byte2ByteLinkedOpenHashMap.this.last;
/* 1201 */         this.index = Byte2ByteLinkedOpenHashMap.this.size;
/*      */         
/*      */         return;
/*      */       } 
/* 1205 */       int pos = HashCommon.mix(from) & Byte2ByteLinkedOpenHashMap.this.mask;
/*      */       
/* 1207 */       while (Byte2ByteLinkedOpenHashMap.this.key[pos] != 0) {
/* 1208 */         if (Byte2ByteLinkedOpenHashMap.this.key[pos] == from) {
/*      */           
/* 1210 */           this.next = (int)Byte2ByteLinkedOpenHashMap.this.link[pos];
/* 1211 */           this.prev = pos;
/*      */           return;
/*      */         } 
/* 1214 */         pos = pos + 1 & Byte2ByteLinkedOpenHashMap.this.mask;
/*      */       } 
/* 1216 */       throw new NoSuchElementException("The key " + from + " does not belong to this map.");
/*      */     }
/*      */     
/*      */     public boolean hasNext() {
/* 1220 */       return (this.next != -1);
/*      */     }
/*      */     
/*      */     public boolean hasPrevious() {
/* 1224 */       return (this.prev != -1);
/*      */     }
/*      */     
/*      */     private final void ensureIndexKnown() {
/* 1228 */       if (this.index >= 0)
/* 1229 */         return;  if (this.prev == -1) {
/* 1230 */         this.index = 0;
/*      */         return;
/*      */       } 
/* 1233 */       if (this.next == -1) {
/* 1234 */         this.index = Byte2ByteLinkedOpenHashMap.this.size;
/*      */         return;
/*      */       } 
/* 1237 */       int pos = Byte2ByteLinkedOpenHashMap.this.first;
/* 1238 */       this.index = 1;
/* 1239 */       while (pos != this.prev) {
/* 1240 */         pos = (int)Byte2ByteLinkedOpenHashMap.this.link[pos];
/* 1241 */         this.index++;
/*      */       } 
/*      */     }
/*      */     
/*      */     public int nextIndex() {
/* 1246 */       ensureIndexKnown();
/* 1247 */       return this.index;
/*      */     }
/*      */     
/*      */     public int previousIndex() {
/* 1251 */       ensureIndexKnown();
/* 1252 */       return this.index - 1;
/*      */     }
/*      */     
/*      */     public int nextEntry() {
/* 1256 */       if (!hasNext()) throw new NoSuchElementException(); 
/* 1257 */       this.curr = this.next;
/* 1258 */       this.next = (int)Byte2ByteLinkedOpenHashMap.this.link[this.curr];
/* 1259 */       this.prev = this.curr;
/* 1260 */       if (this.index >= 0) this.index++; 
/* 1261 */       return this.curr;
/*      */     }
/*      */     
/*      */     public int previousEntry() {
/* 1265 */       if (!hasPrevious()) throw new NoSuchElementException(); 
/* 1266 */       this.curr = this.prev;
/* 1267 */       this.prev = (int)(Byte2ByteLinkedOpenHashMap.this.link[this.curr] >>> 32L);
/* 1268 */       this.next = this.curr;
/* 1269 */       if (this.index >= 0) this.index--; 
/* 1270 */       return this.curr;
/*      */     }
/*      */     
/*      */     public void forEachRemaining(ConsumerType action) {
/* 1274 */       while (hasNext()) {
/* 1275 */         this.curr = this.next;
/* 1276 */         this.next = (int)Byte2ByteLinkedOpenHashMap.this.link[this.curr];
/* 1277 */         this.prev = this.curr;
/* 1278 */         if (this.index >= 0) this.index++; 
/* 1279 */         acceptOnIndex(action, this.curr);
/*      */       } 
/*      */     }
/*      */     
/*      */     public void remove() {
/* 1284 */       ensureIndexKnown();
/* 1285 */       if (this.curr == -1) throw new IllegalStateException(); 
/* 1286 */       if (this.curr == this.prev)
/*      */       
/*      */       { 
/* 1289 */         this.index--;
/* 1290 */         this.prev = (int)(Byte2ByteLinkedOpenHashMap.this.link[this.curr] >>> 32L); }
/* 1291 */       else { this.next = (int)Byte2ByteLinkedOpenHashMap.this.link[this.curr]; }
/* 1292 */        Byte2ByteLinkedOpenHashMap.this.size--;
/*      */ 
/*      */       
/* 1295 */       if (this.prev == -1) { Byte2ByteLinkedOpenHashMap.this.first = this.next; }
/* 1296 */       else { Byte2ByteLinkedOpenHashMap.this.link[this.prev] = Byte2ByteLinkedOpenHashMap.this.link[this.prev] ^ (Byte2ByteLinkedOpenHashMap.this.link[this.prev] ^ this.next & 0xFFFFFFFFL) & 0xFFFFFFFFL; }
/* 1297 */        if (this.next == -1) { Byte2ByteLinkedOpenHashMap.this.last = this.prev; }
/* 1298 */       else { Byte2ByteLinkedOpenHashMap.this.link[this.next] = Byte2ByteLinkedOpenHashMap.this.link[this.next] ^ (Byte2ByteLinkedOpenHashMap.this.link[this.next] ^ (this.prev & 0xFFFFFFFFL) << 32L) & 0xFFFFFFFF00000000L; }
/* 1299 */        int pos = this.curr;
/* 1300 */       this.curr = -1;
/* 1301 */       if (pos == Byte2ByteLinkedOpenHashMap.this.n) {
/* 1302 */         Byte2ByteLinkedOpenHashMap.this.containsNullKey = false;
/*      */       } else {
/*      */         
/* 1305 */         byte[] key = Byte2ByteLinkedOpenHashMap.this.key; while (true) {
/*      */           byte curr;
/*      */           int last;
/* 1308 */           pos = (last = pos) + 1 & Byte2ByteLinkedOpenHashMap.this.mask;
/*      */           while (true) {
/* 1310 */             if ((curr = key[pos]) == 0) {
/* 1311 */               key[last] = 0;
/*      */               return;
/*      */             } 
/* 1314 */             int slot = HashCommon.mix(curr) & Byte2ByteLinkedOpenHashMap.this.mask;
/* 1315 */             if ((last <= pos) ? (last >= slot || slot > pos) : (last >= slot && slot > pos))
/* 1316 */               break;  pos = pos + 1 & Byte2ByteLinkedOpenHashMap.this.mask;
/*      */           } 
/* 1318 */           key[last] = curr;
/* 1319 */           Byte2ByteLinkedOpenHashMap.this.value[last] = Byte2ByteLinkedOpenHashMap.this.value[pos];
/* 1320 */           if (this.next == pos) this.next = last; 
/* 1321 */           if (this.prev == pos) this.prev = last; 
/* 1322 */           Byte2ByteLinkedOpenHashMap.this.fixPointers(pos, last);
/*      */         } 
/*      */       } 
/*      */     }
/*      */     
/*      */     public int skip(int n) {
/* 1328 */       int i = n;
/* 1329 */       for (; i-- != 0 && hasNext(); nextEntry());
/* 1330 */       return n - i - 1;
/*      */     }
/*      */     
/*      */     public int back(int n) {
/* 1334 */       int i = n;
/* 1335 */       for (; i-- != 0 && hasPrevious(); previousEntry());
/* 1336 */       return n - i - 1;
/*      */     }
/*      */     
/*      */     public void set(Byte2ByteMap.Entry ok) {
/* 1340 */       throw new UnsupportedOperationException();
/*      */     }
/*      */     
/*      */     public void add(Byte2ByteMap.Entry ok) {
/* 1344 */       throw new UnsupportedOperationException();
/*      */     }
/*      */     
/*      */     abstract void acceptOnIndex(ConsumerType param1ConsumerType, int param1Int); }
/*      */   
/*      */   private final class EntryIterator extends MapIterator<Consumer<? super Byte2ByteMap.Entry>> implements ObjectListIterator<Byte2ByteMap.Entry> {
/*      */     private Byte2ByteLinkedOpenHashMap.MapEntry entry;
/*      */     
/*      */     public EntryIterator() {}
/*      */     
/*      */     public EntryIterator(byte from) {
/* 1355 */       super(from);
/*      */     }
/*      */ 
/*      */ 
/*      */     
/*      */     final void acceptOnIndex(Consumer<? super Byte2ByteMap.Entry> action, int index) {
/* 1361 */       action.accept(new Byte2ByteLinkedOpenHashMap.MapEntry(index));
/*      */     }
/*      */ 
/*      */     
/*      */     public Byte2ByteLinkedOpenHashMap.MapEntry next() {
/* 1366 */       return this.entry = new Byte2ByteLinkedOpenHashMap.MapEntry(nextEntry());
/*      */     }
/*      */ 
/*      */     
/*      */     public Byte2ByteLinkedOpenHashMap.MapEntry previous() {
/* 1371 */       return this.entry = new Byte2ByteLinkedOpenHashMap.MapEntry(previousEntry());
/*      */     }
/*      */ 
/*      */     
/*      */     public void remove() {
/* 1376 */       super.remove();
/* 1377 */       this.entry.index = -1;
/*      */     }
/*      */   }
/*      */   
/*      */   private final class FastEntryIterator extends MapIterator<Consumer<? super Byte2ByteMap.Entry>> implements ObjectListIterator<Byte2ByteMap.Entry> {
/* 1382 */     final Byte2ByteLinkedOpenHashMap.MapEntry entry = new Byte2ByteLinkedOpenHashMap.MapEntry();
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public FastEntryIterator(byte from) {
/* 1388 */       super(from);
/*      */     }
/*      */ 
/*      */ 
/*      */     
/*      */     final void acceptOnIndex(Consumer<? super Byte2ByteMap.Entry> action, int index) {
/* 1394 */       this.entry.index = index;
/* 1395 */       action.accept(this.entry);
/*      */     }
/*      */ 
/*      */     
/*      */     public Byte2ByteLinkedOpenHashMap.MapEntry next() {
/* 1400 */       this.entry.index = nextEntry();
/* 1401 */       return this.entry;
/*      */     }
/*      */ 
/*      */     
/*      */     public Byte2ByteLinkedOpenHashMap.MapEntry previous() {
/* 1406 */       this.entry.index = previousEntry();
/* 1407 */       return this.entry;
/*      */     }
/*      */     
/*      */     public FastEntryIterator() {} }
/*      */   
/*      */   private final class MapEntrySet extends AbstractObjectSortedSet<Byte2ByteMap.Entry> implements Byte2ByteSortedMap.FastSortedEntrySet {
/*      */     private static final int SPLITERATOR_CHARACTERISTICS = 81;
/*      */     
/*      */     public ObjectBidirectionalIterator<Byte2ByteMap.Entry> iterator() {
/* 1416 */       return (ObjectBidirectionalIterator<Byte2ByteMap.Entry>)new Byte2ByteLinkedOpenHashMap.EntryIterator();
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
/*      */     public ObjectSpliterator<Byte2ByteMap.Entry> spliterator() {
/* 1434 */       return ObjectSpliterators.asSpliterator((ObjectIterator)iterator(), Size64.sizeOf(Byte2ByteLinkedOpenHashMap.this), 81);
/*      */     }
/*      */ 
/*      */     
/*      */     public Comparator<? super Byte2ByteMap.Entry> comparator() {
/* 1439 */       return null;
/*      */     }
/*      */ 
/*      */     
/*      */     public ObjectSortedSet<Byte2ByteMap.Entry> subSet(Byte2ByteMap.Entry fromElement, Byte2ByteMap.Entry toElement) {
/* 1444 */       throw new UnsupportedOperationException();
/*      */     }
/*      */ 
/*      */     
/*      */     public ObjectSortedSet<Byte2ByteMap.Entry> headSet(Byte2ByteMap.Entry toElement) {
/* 1449 */       throw new UnsupportedOperationException();
/*      */     }
/*      */ 
/*      */     
/*      */     public ObjectSortedSet<Byte2ByteMap.Entry> tailSet(Byte2ByteMap.Entry fromElement) {
/* 1454 */       throw new UnsupportedOperationException();
/*      */     }
/*      */ 
/*      */     
/*      */     public Byte2ByteMap.Entry first() {
/* 1459 */       if (Byte2ByteLinkedOpenHashMap.this.size == 0) throw new NoSuchElementException(); 
/* 1460 */       return new Byte2ByteLinkedOpenHashMap.MapEntry(Byte2ByteLinkedOpenHashMap.this.first);
/*      */     }
/*      */ 
/*      */     
/*      */     public Byte2ByteMap.Entry last() {
/* 1465 */       if (Byte2ByteLinkedOpenHashMap.this.size == 0) throw new NoSuchElementException(); 
/* 1466 */       return new Byte2ByteLinkedOpenHashMap.MapEntry(Byte2ByteLinkedOpenHashMap.this.last);
/*      */     }
/*      */ 
/*      */ 
/*      */     
/*      */     public boolean contains(Object o) {
/* 1472 */       if (!(o instanceof Map.Entry)) return false; 
/* 1473 */       Map.Entry<?, ?> e = (Map.Entry<?, ?>)o;
/* 1474 */       if (e.getKey() == null || !(e.getKey() instanceof Byte)) return false; 
/* 1475 */       if (e.getValue() == null || !(e.getValue() instanceof Byte)) return false; 
/* 1476 */       byte k = ((Byte)e.getKey()).byteValue();
/* 1477 */       byte v = ((Byte)e.getValue()).byteValue();
/* 1478 */       if (k == 0) return (Byte2ByteLinkedOpenHashMap.this.containsNullKey && Byte2ByteLinkedOpenHashMap.this.value[Byte2ByteLinkedOpenHashMap.this.n] == v);
/*      */       
/* 1480 */       byte[] key = Byte2ByteLinkedOpenHashMap.this.key;
/*      */       byte curr;
/*      */       int pos;
/* 1483 */       if ((curr = key[pos = HashCommon.mix(k) & Byte2ByteLinkedOpenHashMap.this.mask]) == 0) return false; 
/* 1484 */       if (k == curr) return (Byte2ByteLinkedOpenHashMap.this.value[pos] == v);
/*      */       
/*      */       while (true) {
/* 1487 */         if ((curr = key[pos = pos + 1 & Byte2ByteLinkedOpenHashMap.this.mask]) == 0) return false; 
/* 1488 */         if (k == curr) return (Byte2ByteLinkedOpenHashMap.this.value[pos] == v);
/*      */       
/*      */       } 
/*      */     }
/*      */ 
/*      */     
/*      */     public boolean remove(Object o) {
/* 1495 */       if (!(o instanceof Map.Entry)) return false; 
/* 1496 */       Map.Entry<?, ?> e = (Map.Entry<?, ?>)o;
/* 1497 */       if (e.getKey() == null || !(e.getKey() instanceof Byte)) return false; 
/* 1498 */       if (e.getValue() == null || !(e.getValue() instanceof Byte)) return false; 
/* 1499 */       byte k = ((Byte)e.getKey()).byteValue();
/* 1500 */       byte v = ((Byte)e.getValue()).byteValue();
/* 1501 */       if (k == 0) {
/* 1502 */         if (Byte2ByteLinkedOpenHashMap.this.containsNullKey && Byte2ByteLinkedOpenHashMap.this.value[Byte2ByteLinkedOpenHashMap.this.n] == v) {
/* 1503 */           Byte2ByteLinkedOpenHashMap.this.removeNullEntry();
/* 1504 */           return true;
/*      */         } 
/* 1506 */         return false;
/*      */       } 
/*      */       
/* 1509 */       byte[] key = Byte2ByteLinkedOpenHashMap.this.key;
/*      */       byte curr;
/*      */       int pos;
/* 1512 */       if ((curr = key[pos = HashCommon.mix(k) & Byte2ByteLinkedOpenHashMap.this.mask]) == 0) return false; 
/* 1513 */       if (curr == k) {
/* 1514 */         if (Byte2ByteLinkedOpenHashMap.this.value[pos] == v) {
/* 1515 */           Byte2ByteLinkedOpenHashMap.this.removeEntry(pos);
/* 1516 */           return true;
/*      */         } 
/* 1518 */         return false;
/*      */       } 
/*      */       while (true) {
/* 1521 */         if ((curr = key[pos = pos + 1 & Byte2ByteLinkedOpenHashMap.this.mask]) == 0) return false; 
/* 1522 */         if (curr == k && 
/* 1523 */           Byte2ByteLinkedOpenHashMap.this.value[pos] == v) {
/* 1524 */           Byte2ByteLinkedOpenHashMap.this.removeEntry(pos);
/* 1525 */           return true;
/*      */         } 
/*      */       } 
/*      */     }
/*      */ 
/*      */ 
/*      */     
/*      */     public int size() {
/* 1533 */       return Byte2ByteLinkedOpenHashMap.this.size;
/*      */     }
/*      */ 
/*      */     
/*      */     public void clear() {
/* 1538 */       Byte2ByteLinkedOpenHashMap.this.clear();
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
/*      */     public ObjectListIterator<Byte2ByteMap.Entry> iterator(Byte2ByteMap.Entry from) {
/* 1551 */       return new Byte2ByteLinkedOpenHashMap.EntryIterator(from.getByteKey());
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public ObjectListIterator<Byte2ByteMap.Entry> fastIterator() {
/* 1562 */       return new Byte2ByteLinkedOpenHashMap.FastEntryIterator();
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
/*      */     public ObjectListIterator<Byte2ByteMap.Entry> fastIterator(Byte2ByteMap.Entry from) {
/* 1575 */       return new Byte2ByteLinkedOpenHashMap.FastEntryIterator(from.getByteKey());
/*      */     }
/*      */ 
/*      */ 
/*      */     
/*      */     public void forEach(Consumer<? super Byte2ByteMap.Entry> consumer) {
/* 1581 */       for (int i = Byte2ByteLinkedOpenHashMap.this.size, next = Byte2ByteLinkedOpenHashMap.this.first; i-- != 0; ) {
/* 1582 */         int curr = next;
/* 1583 */         next = (int)Byte2ByteLinkedOpenHashMap.this.link[curr];
/* 1584 */         consumer.accept(new Byte2ByteLinkedOpenHashMap.MapEntry(curr));
/*      */       } 
/*      */     }
/*      */ 
/*      */ 
/*      */     
/*      */     public void fastForEach(Consumer<? super Byte2ByteMap.Entry> consumer) {
/* 1591 */       Byte2ByteLinkedOpenHashMap.MapEntry entry = new Byte2ByteLinkedOpenHashMap.MapEntry();
/* 1592 */       for (int i = Byte2ByteLinkedOpenHashMap.this.size, next = Byte2ByteLinkedOpenHashMap.this.first; i-- != 0; ) {
/* 1593 */         entry.index = next;
/* 1594 */         next = (int)Byte2ByteLinkedOpenHashMap.this.link[next];
/* 1595 */         consumer.accept(entry);
/*      */       } 
/*      */     }
/*      */   }
/*      */ 
/*      */   
/*      */   public Byte2ByteSortedMap.FastSortedEntrySet byte2ByteEntrySet() {
/* 1602 */     if (this.entries == null) this.entries = new MapEntrySet(); 
/* 1603 */     return this.entries;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private final class KeyIterator
/*      */     extends MapIterator<ByteConsumer>
/*      */     implements ByteListIterator
/*      */   {
/*      */     public KeyIterator(byte k) {
/* 1616 */       super(k);
/*      */     }
/*      */ 
/*      */     
/*      */     public byte previousByte() {
/* 1621 */       return Byte2ByteLinkedOpenHashMap.this.key[previousEntry()];
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
/*      */     final void acceptOnIndex(ByteConsumer action, int index) {
/* 1633 */       action.accept(Byte2ByteLinkedOpenHashMap.this.key[index]);
/*      */     }
/*      */ 
/*      */     
/*      */     public byte nextByte() {
/* 1638 */       return Byte2ByteLinkedOpenHashMap.this.key[nextEntry()];
/*      */     } }
/*      */   
/*      */   private final class KeySet extends AbstractByteSortedSet {
/*      */     private static final int SPLITERATOR_CHARACTERISTICS = 337;
/*      */     
/*      */     private KeySet() {}
/*      */     
/*      */     public ByteListIterator iterator(byte from) {
/* 1647 */       return new Byte2ByteLinkedOpenHashMap.KeyIterator(from);
/*      */     }
/*      */ 
/*      */     
/*      */     public ByteListIterator iterator() {
/* 1652 */       return new Byte2ByteLinkedOpenHashMap.KeyIterator();
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public ByteSpliterator spliterator() {
/* 1662 */       return ByteSpliterators.asSpliterator(iterator(), Size64.sizeOf(Byte2ByteLinkedOpenHashMap.this), 337);
/*      */     }
/*      */ 
/*      */ 
/*      */     
/*      */     public void forEach(ByteConsumer consumer) {
/* 1668 */       for (int i = Byte2ByteLinkedOpenHashMap.this.size, next = Byte2ByteLinkedOpenHashMap.this.first; i-- != 0; ) {
/* 1669 */         int curr = next;
/* 1670 */         next = (int)Byte2ByteLinkedOpenHashMap.this.link[curr];
/* 1671 */         consumer.accept(Byte2ByteLinkedOpenHashMap.this.key[curr]);
/*      */       } 
/*      */     }
/*      */ 
/*      */     
/*      */     public int size() {
/* 1677 */       return Byte2ByteLinkedOpenHashMap.this.size;
/*      */     }
/*      */ 
/*      */     
/*      */     public boolean contains(byte k) {
/* 1682 */       return Byte2ByteLinkedOpenHashMap.this.containsKey(k);
/*      */     }
/*      */ 
/*      */     
/*      */     public boolean remove(byte k) {
/* 1687 */       int oldSize = Byte2ByteLinkedOpenHashMap.this.size;
/* 1688 */       Byte2ByteLinkedOpenHashMap.this.remove(k);
/* 1689 */       return (Byte2ByteLinkedOpenHashMap.this.size != oldSize);
/*      */     }
/*      */ 
/*      */     
/*      */     public void clear() {
/* 1694 */       Byte2ByteLinkedOpenHashMap.this.clear();
/*      */     }
/*      */ 
/*      */     
/*      */     public byte firstByte() {
/* 1699 */       if (Byte2ByteLinkedOpenHashMap.this.size == 0) throw new NoSuchElementException(); 
/* 1700 */       return Byte2ByteLinkedOpenHashMap.this.key[Byte2ByteLinkedOpenHashMap.this.first];
/*      */     }
/*      */ 
/*      */     
/*      */     public byte lastByte() {
/* 1705 */       if (Byte2ByteLinkedOpenHashMap.this.size == 0) throw new NoSuchElementException(); 
/* 1706 */       return Byte2ByteLinkedOpenHashMap.this.key[Byte2ByteLinkedOpenHashMap.this.last];
/*      */     }
/*      */ 
/*      */     
/*      */     public ByteComparator comparator() {
/* 1711 */       return null;
/*      */     }
/*      */ 
/*      */     
/*      */     public ByteSortedSet tailSet(byte from) {
/* 1716 */       throw new UnsupportedOperationException();
/*      */     }
/*      */ 
/*      */     
/*      */     public ByteSortedSet headSet(byte to) {
/* 1721 */       throw new UnsupportedOperationException();
/*      */     }
/*      */ 
/*      */     
/*      */     public ByteSortedSet subSet(byte from, byte to) {
/* 1726 */       throw new UnsupportedOperationException();
/*      */     }
/*      */   }
/*      */ 
/*      */   
/*      */   public ByteSortedSet keySet() {
/* 1732 */     if (this.keys == null) this.keys = new KeySet(); 
/* 1733 */     return this.keys;
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
/* 1747 */       return Byte2ByteLinkedOpenHashMap.this.value[previousEntry()];
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
/* 1759 */       action.accept(Byte2ByteLinkedOpenHashMap.this.value[index]);
/*      */     }
/*      */ 
/*      */     
/*      */     public byte nextByte() {
/* 1764 */       return Byte2ByteLinkedOpenHashMap.this.value[nextEntry()];
/*      */     }
/*      */   }
/*      */ 
/*      */   
/*      */   public ByteCollection values() {
/* 1770 */     if (this.values == null) this.values = new AbstractByteCollection()
/*      */         {
/*      */           private static final int SPLITERATOR_CHARACTERISTICS = 336;
/*      */           
/*      */           public ByteIterator iterator() {
/* 1775 */             return new Byte2ByteLinkedOpenHashMap.ValueIterator();
/*      */           }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */           
/*      */           public ByteSpliterator spliterator() {
/* 1785 */             return ByteSpliterators.asSpliterator(iterator(), Size64.sizeOf(Byte2ByteLinkedOpenHashMap.this), 336);
/*      */           }
/*      */ 
/*      */ 
/*      */           
/*      */           public void forEach(ByteConsumer consumer) {
/* 1791 */             for (int i = Byte2ByteLinkedOpenHashMap.this.size, next = Byte2ByteLinkedOpenHashMap.this.first; i-- != 0; ) {
/* 1792 */               int curr = next;
/* 1793 */               next = (int)Byte2ByteLinkedOpenHashMap.this.link[curr];
/* 1794 */               consumer.accept(Byte2ByteLinkedOpenHashMap.this.value[curr]);
/*      */             } 
/*      */           }
/*      */ 
/*      */           
/*      */           public int size() {
/* 1800 */             return Byte2ByteLinkedOpenHashMap.this.size;
/*      */           }
/*      */ 
/*      */           
/*      */           public boolean contains(byte v) {
/* 1805 */             return Byte2ByteLinkedOpenHashMap.this.containsValue(v);
/*      */           }
/*      */ 
/*      */           
/*      */           public void clear() {
/* 1810 */             Byte2ByteLinkedOpenHashMap.this.clear();
/*      */           }
/*      */         }; 
/* 1813 */     return this.values;
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
/* 1830 */     return trim(this.size);
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
/* 1852 */     int l = HashCommon.nextPowerOfTwo((int)Math.ceil((n / this.f)));
/* 1853 */     if (l >= this.n || this.size > HashCommon.maxFill(l, this.f)) return true; 
/*      */     try {
/* 1855 */       rehash(l);
/* 1856 */     } catch (OutOfMemoryError cantDoIt) {
/* 1857 */       return false;
/*      */     } 
/* 1859 */     return true;
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
/* 1874 */     byte[] key = this.key;
/* 1875 */     byte[] value = this.value;
/* 1876 */     int mask = newN - 1;
/* 1877 */     byte[] newKey = new byte[newN + 1];
/* 1878 */     byte[] newValue = new byte[newN + 1];
/* 1879 */     int i = this.first, prev = -1, newPrev = -1;
/* 1880 */     long[] link = this.link;
/* 1881 */     long[] newLink = new long[newN + 1];
/* 1882 */     this.first = -1;
/* 1883 */     for (int j = this.size; j-- != 0; ) {
/* 1884 */       int pos; if (key[i] == 0) { pos = newN; }
/*      */       else
/* 1886 */       { pos = HashCommon.mix(key[i]) & mask;
/* 1887 */         for (; newKey[pos] != 0; pos = pos + 1 & mask); }
/*      */       
/* 1889 */       newKey[pos] = key[i];
/* 1890 */       newValue[pos] = value[i];
/* 1891 */       if (prev != -1) {
/* 1892 */         newLink[newPrev] = newLink[newPrev] ^ (newLink[newPrev] ^ pos & 0xFFFFFFFFL) & 0xFFFFFFFFL;
/* 1893 */         newLink[pos] = newLink[pos] ^ (newLink[pos] ^ (newPrev & 0xFFFFFFFFL) << 32L) & 0xFFFFFFFF00000000L;
/* 1894 */         newPrev = pos;
/*      */       } else {
/* 1896 */         newPrev = this.first = pos;
/*      */         
/* 1898 */         newLink[pos] = -1L;
/*      */       } 
/* 1900 */       int t = i;
/* 1901 */       i = (int)link[i];
/* 1902 */       prev = t;
/*      */     } 
/* 1904 */     this.link = newLink;
/* 1905 */     this.last = newPrev;
/* 1906 */     if (newPrev != -1)
/*      */     {
/* 1908 */       newLink[newPrev] = newLink[newPrev] | 0xFFFFFFFFL; } 
/* 1909 */     this.n = newN;
/* 1910 */     this.mask = mask;
/* 1911 */     this.maxFill = HashCommon.maxFill(this.n, this.f);
/* 1912 */     this.key = newKey;
/* 1913 */     this.value = newValue;
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
/*      */   public Byte2ByteLinkedOpenHashMap clone() {
/*      */     Byte2ByteLinkedOpenHashMap c;
/*      */     try {
/* 1930 */       c = (Byte2ByteLinkedOpenHashMap)super.clone();
/* 1931 */     } catch (CloneNotSupportedException cantHappen) {
/* 1932 */       throw new InternalError();
/*      */     } 
/* 1934 */     c.keys = null;
/* 1935 */     c.values = null;
/* 1936 */     c.entries = null;
/* 1937 */     c.containsNullKey = this.containsNullKey;
/* 1938 */     c.key = (byte[])this.key.clone();
/* 1939 */     c.value = (byte[])this.value.clone();
/* 1940 */     c.link = (long[])this.link.clone();
/* 1941 */     return c;
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
/* 1955 */     int h = 0;
/* 1956 */     for (int j = realSize(), i = 0, t = 0; j-- != 0; ) {
/* 1957 */       for (; this.key[i] == 0; i++);
/* 1958 */       t = this.key[i];
/* 1959 */       t ^= this.value[i];
/* 1960 */       h += t;
/* 1961 */       i++;
/*      */     } 
/*      */     
/* 1964 */     if (this.containsNullKey) h += this.value[this.n]; 
/* 1965 */     return h;
/*      */   }
/*      */   
/*      */   private void writeObject(ObjectOutputStream s) throws IOException {
/* 1969 */     byte[] key = this.key;
/* 1970 */     byte[] value = this.value;
/* 1971 */     EntryIterator i = new EntryIterator();
/* 1972 */     s.defaultWriteObject();
/* 1973 */     for (int j = this.size; j-- != 0; ) {
/* 1974 */       int e = i.nextEntry();
/* 1975 */       s.writeByte(key[e]);
/* 1976 */       s.writeByte(value[e]);
/*      */     } 
/*      */   }
/*      */   
/*      */   private void readObject(ObjectInputStream s) throws IOException, ClassNotFoundException {
/* 1981 */     s.defaultReadObject();
/* 1982 */     this.n = HashCommon.arraySize(this.size, this.f);
/* 1983 */     this.maxFill = HashCommon.maxFill(this.n, this.f);
/* 1984 */     this.mask = this.n - 1;
/* 1985 */     byte[] key = this.key = new byte[this.n + 1];
/* 1986 */     byte[] value = this.value = new byte[this.n + 1];
/* 1987 */     long[] link = this.link = new long[this.n + 1];
/* 1988 */     int prev = -1;
/* 1989 */     this.first = this.last = -1;
/*      */ 
/*      */     
/* 1992 */     for (int i = this.size; i-- != 0; ) {
/* 1993 */       int pos; byte k = s.readByte();
/* 1994 */       byte v = s.readByte();
/* 1995 */       if (k == 0) {
/* 1996 */         pos = this.n;
/* 1997 */         this.containsNullKey = true;
/*      */       } else {
/* 1999 */         pos = HashCommon.mix(k) & this.mask;
/* 2000 */         for (; key[pos] != 0; pos = pos + 1 & this.mask);
/*      */       } 
/* 2002 */       key[pos] = k;
/* 2003 */       value[pos] = v;
/* 2004 */       if (this.first != -1) {
/* 2005 */         link[prev] = link[prev] ^ (link[prev] ^ pos & 0xFFFFFFFFL) & 0xFFFFFFFFL;
/* 2006 */         link[pos] = link[pos] ^ (link[pos] ^ (prev & 0xFFFFFFFFL) << 32L) & 0xFFFFFFFF00000000L;
/* 2007 */         prev = pos; continue;
/*      */       } 
/* 2009 */       prev = this.first = pos;
/*      */       
/* 2011 */       link[pos] = link[pos] | 0xFFFFFFFF00000000L;
/*      */     } 
/*      */     
/* 2014 */     this.last = prev;
/* 2015 */     if (prev != -1)
/*      */     {
/* 2017 */       link[prev] = link[prev] | 0xFFFFFFFFL;
/*      */     }
/*      */   }
/*      */   
/*      */   private void checkTable() {}
/*      */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\i\\unimi\dsi\fastutil\bytes\Byte2ByteLinkedOpenHashMap.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */