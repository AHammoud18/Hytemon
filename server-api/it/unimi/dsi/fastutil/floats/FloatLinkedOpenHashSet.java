/*      */ package it.unimi.dsi.fastutil.floats;
/*      */ 
/*      */ import it.unimi.dsi.fastutil.Hash;
/*      */ import it.unimi.dsi.fastutil.HashCommon;
/*      */ import it.unimi.dsi.fastutil.Size64;
/*      */ import java.io.IOException;
/*      */ import java.io.ObjectInputStream;
/*      */ import java.io.ObjectOutputStream;
/*      */ import java.io.Serializable;
/*      */ import java.util.Arrays;
/*      */ import java.util.Collection;
/*      */ import java.util.Comparator;
/*      */ import java.util.Iterator;
/*      */ import java.util.NoSuchElementException;
/*      */ import java.util.Spliterator;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ public class FloatLinkedOpenHashSet
/*      */   extends AbstractFloatSortedSet
/*      */   implements Serializable, Cloneable, Hash
/*      */ {
/*      */   private static final long serialVersionUID = 0L;
/*      */   private static final boolean ASSERTS = false;
/*      */   protected transient float[] key;
/*      */   protected transient int mask;
/*      */   protected transient boolean containsNull;
/*   88 */   protected transient int first = -1;
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*   93 */   protected transient int last = -1;
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
/*      */   
/*      */   protected int size;
/*      */ 
/*      */   
/*      */   protected final float f;
/*      */ 
/*      */   
/*      */   private static final int SPLITERATOR_CHARACTERISTICS = 337;
/*      */ 
/*      */ 
/*      */   
/*      */   public FloatLinkedOpenHashSet(int expected, float f) {
/*  124 */     if (f <= 0.0F || f >= 1.0F) throw new IllegalArgumentException("Load factor must be greater than 0 and smaller than 1"); 
/*  125 */     if (expected < 0) throw new IllegalArgumentException("The expected number of elements must be nonnegative"); 
/*  126 */     this.f = f;
/*  127 */     this.minN = this.n = HashCommon.arraySize(expected, f);
/*  128 */     this.mask = this.n - 1;
/*  129 */     this.maxFill = HashCommon.maxFill(this.n, f);
/*  130 */     this.key = new float[this.n + 1];
/*  131 */     this.link = new long[this.n + 1];
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public FloatLinkedOpenHashSet(int expected) {
/*  140 */     this(expected, 0.75F);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public FloatLinkedOpenHashSet() {
/*  148 */     this(16, 0.75F);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public FloatLinkedOpenHashSet(Collection<? extends Float> c, float f) {
/*  158 */     this(c.size(), f);
/*  159 */     addAll(c);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public FloatLinkedOpenHashSet(Collection<? extends Float> c) {
/*  169 */     this(c, 0.75F);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public FloatLinkedOpenHashSet(FloatCollection c, float f) {
/*  179 */     this(c.size(), f);
/*  180 */     addAll(c);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public FloatLinkedOpenHashSet(FloatCollection c) {
/*  190 */     this(c, 0.75F);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public FloatLinkedOpenHashSet(FloatIterator i, float f) {
/*  200 */     this(16, f);
/*  201 */     for (; i.hasNext(); add(i.nextFloat()));
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public FloatLinkedOpenHashSet(FloatIterator i) {
/*  211 */     this(i, 0.75F);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public FloatLinkedOpenHashSet(Iterator<?> i, float f) {
/*  221 */     this(FloatIterators.asFloatIterator(i), f);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public FloatLinkedOpenHashSet(Iterator<?> i) {
/*  231 */     this(FloatIterators.asFloatIterator(i));
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
/*      */   public FloatLinkedOpenHashSet(float[] a, int offset, int length, float f) {
/*  243 */     this((length < 0) ? 0 : length, f);
/*  244 */     FloatArrays.ensureOffsetLength(a, offset, length);
/*  245 */     for (int i = 0; i < length; ) { add(a[offset + i]); i++; }
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
/*      */   public FloatLinkedOpenHashSet(float[] a, int offset, int length) {
/*  257 */     this(a, offset, length, 0.75F);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public FloatLinkedOpenHashSet(float[] a, float f) {
/*  267 */     this(a, 0, a.length, f);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public FloatLinkedOpenHashSet(float[] a) {
/*  277 */     this(a, 0.75F);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static FloatLinkedOpenHashSet of() {
/*  286 */     return new FloatLinkedOpenHashSet();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static FloatLinkedOpenHashSet of(float e) {
/*  297 */     FloatLinkedOpenHashSet result = new FloatLinkedOpenHashSet(1, 0.75F);
/*  298 */     result.add(e);
/*  299 */     return result;
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
/*      */   public static FloatLinkedOpenHashSet of(float e0, float e1) {
/*  313 */     FloatLinkedOpenHashSet result = new FloatLinkedOpenHashSet(2, 0.75F);
/*  314 */     result.add(e0);
/*  315 */     if (!result.add(e1)) {
/*  316 */       throw new IllegalArgumentException("Duplicate element: " + e1);
/*      */     }
/*  318 */     return result;
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
/*      */   public static FloatLinkedOpenHashSet of(float e0, float e1, float e2) {
/*  333 */     FloatLinkedOpenHashSet result = new FloatLinkedOpenHashSet(3, 0.75F);
/*  334 */     result.add(e0);
/*  335 */     if (!result.add(e1)) {
/*  336 */       throw new IllegalArgumentException("Duplicate element: " + e1);
/*      */     }
/*  338 */     if (!result.add(e2)) {
/*  339 */       throw new IllegalArgumentException("Duplicate element: " + e2);
/*      */     }
/*  341 */     return result;
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
/*      */   public static FloatLinkedOpenHashSet of(float... a) {
/*  355 */     FloatLinkedOpenHashSet result = new FloatLinkedOpenHashSet(a.length, 0.75F);
/*  356 */     for (float element : a) {
/*  357 */       if (!result.add(element)) {
/*  358 */         throw new IllegalArgumentException("Duplicate element " + element);
/*      */       }
/*      */     } 
/*  361 */     return result;
/*      */   }
/*      */   
/*      */   private int realSize() {
/*  365 */     return this.containsNull ? (this.size - 1) : this.size;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void ensureCapacity(int capacity) {
/*  375 */     int needed = HashCommon.arraySize(capacity, this.f);
/*  376 */     if (needed > this.n) rehash(needed); 
/*      */   }
/*      */   
/*      */   private void tryCapacity(long capacity) {
/*  380 */     int needed = (int)Math.min(1073741824L, Math.max(2L, HashCommon.nextPowerOfTwo((long)Math.ceil(((float)capacity / this.f)))));
/*  381 */     if (needed > this.n) rehash(needed);
/*      */   
/*      */   }
/*      */   
/*      */   public boolean addAll(FloatCollection c) {
/*  386 */     if (this.f <= 0.5D) { ensureCapacity(c.size()); }
/*  387 */     else { tryCapacity((size() + c.size())); }
/*      */     
/*  389 */     return super.addAll(c);
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean addAll(Collection<? extends Float> c) {
/*  395 */     if (this.f <= 0.5D) { ensureCapacity(c.size()); }
/*  396 */     else { tryCapacity((size() + c.size())); }
/*      */     
/*  398 */     return super.addAll(c);
/*      */   }
/*      */ 
/*      */   
/*      */   public boolean add(float k) {
/*      */     int pos;
/*  404 */     if (Float.floatToIntBits(k) == 0) {
/*  405 */       if (this.containsNull) return false; 
/*  406 */       pos = this.n;
/*  407 */       this.containsNull = true;
/*      */     } else {
/*      */       
/*  410 */       float[] key = this.key;
/*      */       float curr;
/*  412 */       if (Float.floatToIntBits(curr = key[pos = HashCommon.mix(HashCommon.float2int(k)) & this.mask]) != 0) {
/*  413 */         if (Float.floatToIntBits(curr) == Float.floatToIntBits(k)) return false; 
/*  414 */         while (Float.floatToIntBits(curr = key[pos = pos + 1 & this.mask]) != 0) { if (Float.floatToIntBits(curr) == Float.floatToIntBits(k)) return false;  }
/*      */       
/*  416 */       }  key[pos] = k;
/*      */     } 
/*  418 */     if (this.size == 0) {
/*  419 */       this.first = this.last = pos;
/*      */       
/*  421 */       this.link[pos] = -1L;
/*      */     } else {
/*  423 */       this.link[this.last] = this.link[this.last] ^ (this.link[this.last] ^ pos & 0xFFFFFFFFL) & 0xFFFFFFFFL;
/*  424 */       this.link[pos] = (this.last & 0xFFFFFFFFL) << 32L | 0xFFFFFFFFL;
/*  425 */       this.last = pos;
/*      */     } 
/*  427 */     if (this.size++ >= this.maxFill) rehash(HashCommon.arraySize(this.size + 1, this.f));
/*      */     
/*  429 */     return true;
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
/*  442 */     float[] key = this.key; while (true) {
/*      */       float curr; int last;
/*  444 */       pos = (last = pos) + 1 & this.mask;
/*      */       while (true) {
/*  446 */         if (Float.floatToIntBits(curr = key[pos]) == 0) {
/*  447 */           key[last] = 0.0F;
/*      */           return;
/*      */         } 
/*  450 */         int slot = HashCommon.mix(HashCommon.float2int(curr)) & this.mask;
/*  451 */         if ((last <= pos) ? (last >= slot || slot > pos) : (last >= slot && slot > pos))
/*  452 */           break;  pos = pos + 1 & this.mask;
/*      */       } 
/*  454 */       key[last] = curr;
/*  455 */       fixPointers(pos, last);
/*      */     } 
/*      */   }
/*      */   
/*      */   private boolean removeEntry(int pos) {
/*  460 */     this.size--;
/*  461 */     fixPointers(pos);
/*  462 */     shiftKeys(pos);
/*  463 */     if (this.n > this.minN && this.size < this.maxFill / 4 && this.n > 16) rehash(this.n / 2); 
/*  464 */     return true;
/*      */   }
/*      */   
/*      */   private boolean removeNullEntry() {
/*  468 */     this.containsNull = false;
/*  469 */     this.key[this.n] = 0.0F;
/*  470 */     this.size--;
/*  471 */     fixPointers(this.n);
/*  472 */     if (this.n > this.minN && this.size < this.maxFill / 4 && this.n > 16) rehash(this.n / 2); 
/*  473 */     return true;
/*      */   }
/*      */ 
/*      */   
/*      */   public boolean remove(float k) {
/*  478 */     if (Float.floatToIntBits(k) == 0) {
/*  479 */       if (this.containsNull) return removeNullEntry(); 
/*  480 */       return false;
/*      */     } 
/*      */     
/*  483 */     float[] key = this.key;
/*      */     float curr;
/*      */     int pos;
/*  486 */     if (Float.floatToIntBits(curr = key[pos = HashCommon.mix(HashCommon.float2int(k)) & this.mask]) == 0) return false; 
/*  487 */     if (Float.floatToIntBits(k) == Float.floatToIntBits(curr)) return removeEntry(pos); 
/*      */     while (true) {
/*  489 */       if (Float.floatToIntBits(curr = key[pos = pos + 1 & this.mask]) == 0) return false; 
/*  490 */       if (Float.floatToIntBits(k) == Float.floatToIntBits(curr)) return removeEntry(pos);
/*      */     
/*      */     } 
/*      */   }
/*      */   
/*      */   public boolean contains(float k) {
/*  496 */     if (Float.floatToIntBits(k) == 0) return this.containsNull;
/*      */     
/*  498 */     float[] key = this.key;
/*      */     float curr;
/*      */     int pos;
/*  501 */     if (Float.floatToIntBits(curr = key[pos = HashCommon.mix(HashCommon.float2int(k)) & this.mask]) == 0) return false; 
/*  502 */     if (Float.floatToIntBits(k) == Float.floatToIntBits(curr)) return true; 
/*      */     while (true) {
/*  504 */       if (Float.floatToIntBits(curr = key[pos = pos + 1 & this.mask]) == 0) return false; 
/*  505 */       if (Float.floatToIntBits(k) == Float.floatToIntBits(curr)) return true;
/*      */     
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public float removeFirstFloat() {
/*  516 */     if (this.size == 0) throw new NoSuchElementException(); 
/*  517 */     int pos = this.first;
/*      */     
/*  519 */     if (this.size == 1) { this.first = this.last = -1; }
/*      */     else
/*  521 */     { this.first = (int)this.link[pos];
/*  522 */       if (0 <= this.first)
/*      */       {
/*  524 */         this.link[this.first] = this.link[this.first] | 0xFFFFFFFF00000000L;
/*      */       } }
/*      */     
/*  527 */     float k = this.key[pos];
/*  528 */     this.size--;
/*  529 */     if (Float.floatToIntBits(k) == 0)
/*  530 */     { this.containsNull = false;
/*  531 */       this.key[this.n] = 0.0F; }
/*  532 */     else { shiftKeys(pos); }
/*  533 */      if (this.n > this.minN && this.size < this.maxFill / 4 && this.n > 16) rehash(this.n / 2); 
/*  534 */     return k;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public float removeLastFloat() {
/*  544 */     if (this.size == 0) throw new NoSuchElementException(); 
/*  545 */     int pos = this.last;
/*      */     
/*  547 */     if (this.size == 1) { this.first = this.last = -1; }
/*      */     else
/*  549 */     { this.last = (int)(this.link[pos] >>> 32L);
/*  550 */       if (0 <= this.last)
/*      */       {
/*  552 */         this.link[this.last] = this.link[this.last] | 0xFFFFFFFFL;
/*      */       } }
/*      */     
/*  555 */     float k = this.key[pos];
/*  556 */     this.size--;
/*  557 */     if (Float.floatToIntBits(k) == 0)
/*  558 */     { this.containsNull = false;
/*  559 */       this.key[this.n] = 0.0F; }
/*  560 */     else { shiftKeys(pos); }
/*  561 */      if (this.n > this.minN && this.size < this.maxFill / 4 && this.n > 16) rehash(this.n / 2); 
/*  562 */     return k;
/*      */   }
/*      */   
/*      */   private void moveIndexToFirst(int i) {
/*  566 */     if (this.size == 1 || this.first == i)
/*  567 */       return;  if (this.last == i) {
/*  568 */       this.last = (int)(this.link[i] >>> 32L);
/*      */       
/*  570 */       this.link[this.last] = this.link[this.last] | 0xFFFFFFFFL;
/*      */     } else {
/*  572 */       long linki = this.link[i];
/*  573 */       int prev = (int)(linki >>> 32L);
/*  574 */       int next = (int)linki;
/*  575 */       this.link[prev] = this.link[prev] ^ (this.link[prev] ^ linki & 0xFFFFFFFFL) & 0xFFFFFFFFL;
/*  576 */       this.link[next] = this.link[next] ^ (this.link[next] ^ linki & 0xFFFFFFFF00000000L) & 0xFFFFFFFF00000000L;
/*      */     } 
/*  578 */     this.link[this.first] = this.link[this.first] ^ (this.link[this.first] ^ (i & 0xFFFFFFFFL) << 32L) & 0xFFFFFFFF00000000L;
/*  579 */     this.link[i] = 0xFFFFFFFF00000000L | this.first & 0xFFFFFFFFL;
/*  580 */     this.first = i;
/*      */   }
/*      */   
/*      */   private void moveIndexToLast(int i) {
/*  584 */     if (this.size == 1 || this.last == i)
/*  585 */       return;  if (this.first == i) {
/*  586 */       this.first = (int)this.link[i];
/*      */       
/*  588 */       this.link[this.first] = this.link[this.first] | 0xFFFFFFFF00000000L;
/*      */     } else {
/*  590 */       long linki = this.link[i];
/*  591 */       int prev = (int)(linki >>> 32L);
/*  592 */       int next = (int)linki;
/*  593 */       this.link[prev] = this.link[prev] ^ (this.link[prev] ^ linki & 0xFFFFFFFFL) & 0xFFFFFFFFL;
/*  594 */       this.link[next] = this.link[next] ^ (this.link[next] ^ linki & 0xFFFFFFFF00000000L) & 0xFFFFFFFF00000000L;
/*      */     } 
/*  596 */     this.link[this.last] = this.link[this.last] ^ (this.link[this.last] ^ i & 0xFFFFFFFFL) & 0xFFFFFFFFL;
/*  597 */     this.link[i] = (this.last & 0xFFFFFFFFL) << 32L | 0xFFFFFFFFL;
/*  598 */     this.last = i;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean addAndMoveToFirst(float k) {
/*      */     int pos;
/*  610 */     if (Float.floatToIntBits(k) == 0) {
/*  611 */       if (this.containsNull) {
/*  612 */         moveIndexToFirst(this.n);
/*  613 */         return false;
/*      */       } 
/*  615 */       this.containsNull = true;
/*  616 */       pos = this.n;
/*      */     } else {
/*      */       
/*  619 */       float[] key = this.key;
/*  620 */       pos = HashCommon.mix(HashCommon.float2int(k)) & this.mask;
/*      */       
/*  622 */       while (Float.floatToIntBits(key[pos]) != 0) {
/*  623 */         if (Float.floatToIntBits(k) == Float.floatToIntBits(key[pos])) {
/*  624 */           moveIndexToFirst(pos);
/*  625 */           return false;
/*      */         } 
/*  627 */         pos = pos + 1 & this.mask;
/*      */       } 
/*      */     } 
/*  630 */     this.key[pos] = k;
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
/*  642 */     return true;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean addAndMoveToLast(float k) {
/*      */     int pos;
/*  654 */     if (Float.floatToIntBits(k) == 0) {
/*  655 */       if (this.containsNull) {
/*  656 */         moveIndexToLast(this.n);
/*  657 */         return false;
/*      */       } 
/*  659 */       this.containsNull = true;
/*  660 */       pos = this.n;
/*      */     } else {
/*      */       
/*  663 */       float[] key = this.key;
/*  664 */       pos = HashCommon.mix(HashCommon.float2int(k)) & this.mask;
/*      */       
/*  666 */       while (Float.floatToIntBits(key[pos]) != 0) {
/*  667 */         if (Float.floatToIntBits(k) == Float.floatToIntBits(key[pos])) {
/*  668 */           moveIndexToLast(pos);
/*  669 */           return false;
/*      */         } 
/*  671 */         pos = pos + 1 & this.mask;
/*      */       } 
/*      */     } 
/*  674 */     this.key[pos] = k;
/*  675 */     if (this.size == 0) {
/*  676 */       this.first = this.last = pos;
/*      */       
/*  678 */       this.link[pos] = -1L;
/*      */     } else {
/*  680 */       this.link[this.last] = this.link[this.last] ^ (this.link[this.last] ^ pos & 0xFFFFFFFFL) & 0xFFFFFFFFL;
/*  681 */       this.link[pos] = (this.last & 0xFFFFFFFFL) << 32L | 0xFFFFFFFFL;
/*  682 */       this.last = pos;
/*      */     } 
/*  684 */     if (this.size++ >= this.maxFill) rehash(HashCommon.arraySize(this.size, this.f));
/*      */     
/*  686 */     return true;
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
/*  697 */     if (this.size == 0)
/*  698 */       return;  this.size = 0;
/*  699 */     this.containsNull = false;
/*  700 */     Arrays.fill(this.key, 0.0F);
/*  701 */     this.first = this.last = -1;
/*      */   }
/*      */ 
/*      */   
/*      */   public int size() {
/*  706 */     return this.size;
/*      */   }
/*      */ 
/*      */   
/*      */   public boolean isEmpty() {
/*  711 */     return (this.size == 0);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected void fixPointers(int i) {
/*  721 */     if (this.size == 0) {
/*  722 */       this.first = this.last = -1;
/*      */       return;
/*      */     } 
/*  725 */     if (this.first == i) {
/*  726 */       this.first = (int)this.link[i];
/*  727 */       if (0 <= this.first)
/*      */       {
/*  729 */         this.link[this.first] = this.link[this.first] | 0xFFFFFFFF00000000L;
/*      */       }
/*      */       return;
/*      */     } 
/*  733 */     if (this.last == i) {
/*  734 */       this.last = (int)(this.link[i] >>> 32L);
/*  735 */       if (0 <= this.last)
/*      */       {
/*  737 */         this.link[this.last] = this.link[this.last] | 0xFFFFFFFFL;
/*      */       }
/*      */       return;
/*      */     } 
/*  741 */     long linki = this.link[i];
/*  742 */     int prev = (int)(linki >>> 32L);
/*  743 */     int next = (int)linki;
/*  744 */     this.link[prev] = this.link[prev] ^ (this.link[prev] ^ linki & 0xFFFFFFFFL) & 0xFFFFFFFFL;
/*  745 */     this.link[next] = this.link[next] ^ (this.link[next] ^ linki & 0xFFFFFFFF00000000L) & 0xFFFFFFFF00000000L;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected void fixPointers(int s, int d) {
/*  756 */     if (this.size == 1) {
/*  757 */       this.first = this.last = d;
/*      */       
/*  759 */       this.link[d] = -1L;
/*      */       return;
/*      */     } 
/*  762 */     if (this.first == s) {
/*  763 */       this.first = d;
/*  764 */       this.link[(int)this.link[s]] = this.link[(int)this.link[s]] ^ (this.link[(int)this.link[s]] ^ (d & 0xFFFFFFFFL) << 32L) & 0xFFFFFFFF00000000L;
/*  765 */       this.link[d] = this.link[s];
/*      */       return;
/*      */     } 
/*  768 */     if (this.last == s) {
/*  769 */       this.last = d;
/*  770 */       this.link[(int)(this.link[s] >>> 32L)] = this.link[(int)(this.link[s] >>> 32L)] ^ (this.link[(int)(this.link[s] >>> 32L)] ^ d & 0xFFFFFFFFL) & 0xFFFFFFFFL;
/*  771 */       this.link[d] = this.link[s];
/*      */       return;
/*      */     } 
/*  774 */     long links = this.link[s];
/*  775 */     int prev = (int)(links >>> 32L);
/*  776 */     int next = (int)links;
/*  777 */     this.link[prev] = this.link[prev] ^ (this.link[prev] ^ d & 0xFFFFFFFFL) & 0xFFFFFFFFL;
/*  778 */     this.link[next] = this.link[next] ^ (this.link[next] ^ (d & 0xFFFFFFFFL) << 32L) & 0xFFFFFFFF00000000L;
/*  779 */     this.link[d] = links;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public float firstFloat() {
/*  789 */     if (this.size == 0) throw new NoSuchElementException(); 
/*  790 */     return this.key[this.first];
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public float lastFloat() {
/*  800 */     if (this.size == 0) throw new NoSuchElementException(); 
/*  801 */     return this.key[this.last];
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public FloatSortedSet tailSet(float from) {
/*  811 */     throw new UnsupportedOperationException();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public FloatSortedSet headSet(float to) {
/*  821 */     throw new UnsupportedOperationException();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public FloatSortedSet subSet(float from, float to) {
/*  831 */     throw new UnsupportedOperationException();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public FloatComparator comparator() {
/*  841 */     return null;
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
/*      */   private final class SetIterator
/*      */     implements FloatListIterator
/*      */   {
/*  856 */     int prev = -1;
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  861 */     int next = -1;
/*      */     
/*  863 */     int curr = -1;
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  868 */     int index = -1;
/*      */     
/*      */     SetIterator() {
/*  871 */       this.next = FloatLinkedOpenHashSet.this.first;
/*  872 */       this.index = 0;
/*      */     }
/*      */     
/*      */     SetIterator(float from) {
/*  876 */       if (Float.floatToIntBits(from) == 0) {
/*  877 */         if (FloatLinkedOpenHashSet.this.containsNull) {
/*  878 */           this.next = (int)FloatLinkedOpenHashSet.this.link[FloatLinkedOpenHashSet.this.n];
/*  879 */           this.prev = FloatLinkedOpenHashSet.this.n; return;
/*      */         } 
/*  881 */         throw new NoSuchElementException("The key " + from + " does not belong to this set.");
/*      */       } 
/*  883 */       if (Float.floatToIntBits(FloatLinkedOpenHashSet.this.key[FloatLinkedOpenHashSet.this.last]) == Float.floatToIntBits(from)) {
/*  884 */         this.prev = FloatLinkedOpenHashSet.this.last;
/*  885 */         this.index = FloatLinkedOpenHashSet.this.size;
/*      */         
/*      */         return;
/*      */       } 
/*  889 */       float[] key = FloatLinkedOpenHashSet.this.key;
/*  890 */       int pos = HashCommon.mix(HashCommon.float2int(from)) & FloatLinkedOpenHashSet.this.mask;
/*      */       
/*  892 */       while (Float.floatToIntBits(key[pos]) != 0) {
/*  893 */         if (Float.floatToIntBits(key[pos]) == Float.floatToIntBits(from)) {
/*      */           
/*  895 */           this.next = (int)FloatLinkedOpenHashSet.this.link[pos];
/*  896 */           this.prev = pos;
/*      */           return;
/*      */         } 
/*  899 */         pos = pos + 1 & FloatLinkedOpenHashSet.this.mask;
/*      */       } 
/*  901 */       throw new NoSuchElementException("The key " + from + " does not belong to this set.");
/*      */     }
/*      */ 
/*      */     
/*      */     public boolean hasNext() {
/*  906 */       return (this.next != -1);
/*      */     }
/*      */ 
/*      */     
/*      */     public boolean hasPrevious() {
/*  911 */       return (this.prev != -1);
/*      */     }
/*      */ 
/*      */     
/*      */     public float nextFloat() {
/*  916 */       if (!hasNext()) throw new NoSuchElementException(); 
/*  917 */       this.curr = this.next;
/*  918 */       this.next = (int)FloatLinkedOpenHashSet.this.link[this.curr];
/*  919 */       this.prev = this.curr;
/*  920 */       if (this.index >= 0) this.index++;
/*      */       
/*  922 */       return FloatLinkedOpenHashSet.this.key[this.curr];
/*      */     }
/*      */ 
/*      */     
/*      */     public float previousFloat() {
/*  927 */       if (!hasPrevious()) throw new NoSuchElementException(); 
/*  928 */       this.curr = this.prev;
/*  929 */       this.prev = (int)(FloatLinkedOpenHashSet.this.link[this.curr] >>> 32L);
/*  930 */       this.next = this.curr;
/*  931 */       if (this.index >= 0) this.index--; 
/*  932 */       return FloatLinkedOpenHashSet.this.key[this.curr];
/*      */     }
/*      */ 
/*      */     
/*      */     public void forEachRemaining(FloatConsumer action) {
/*  937 */       float[] key = FloatLinkedOpenHashSet.this.key;
/*  938 */       long[] link = FloatLinkedOpenHashSet.this.link;
/*  939 */       while (this.next != -1) {
/*  940 */         this.curr = this.next;
/*  941 */         this.next = (int)link[this.curr];
/*  942 */         this.prev = this.curr;
/*  943 */         if (this.index >= 0) this.index++;
/*      */         
/*  945 */         action.accept(key[this.curr]);
/*      */       } 
/*      */     }
/*      */     
/*      */     private final void ensureIndexKnown() {
/*  950 */       if (this.index >= 0)
/*  951 */         return;  if (this.prev == -1) {
/*  952 */         this.index = 0;
/*      */         return;
/*      */       } 
/*  955 */       if (this.next == -1) {
/*  956 */         this.index = FloatLinkedOpenHashSet.this.size;
/*      */         return;
/*      */       } 
/*  959 */       int pos = FloatLinkedOpenHashSet.this.first;
/*  960 */       this.index = 1;
/*  961 */       while (pos != this.prev) {
/*  962 */         pos = (int)FloatLinkedOpenHashSet.this.link[pos];
/*  963 */         this.index++;
/*      */       } 
/*      */     }
/*      */ 
/*      */     
/*      */     public int nextIndex() {
/*  969 */       ensureIndexKnown();
/*  970 */       return this.index;
/*      */     }
/*      */ 
/*      */     
/*      */     public int previousIndex() {
/*  975 */       ensureIndexKnown();
/*  976 */       return this.index - 1;
/*      */     }
/*      */ 
/*      */     
/*      */     public void remove() {
/*  981 */       ensureIndexKnown();
/*  982 */       if (this.curr == -1) throw new IllegalStateException(); 
/*  983 */       if (this.curr == this.prev)
/*      */       
/*      */       { 
/*  986 */         this.index--;
/*  987 */         this.prev = (int)(FloatLinkedOpenHashSet.this.link[this.curr] >>> 32L); }
/*  988 */       else { this.next = (int)FloatLinkedOpenHashSet.this.link[this.curr]; }
/*  989 */        FloatLinkedOpenHashSet.this.size--;
/*      */ 
/*      */       
/*  992 */       if (this.prev == -1) { FloatLinkedOpenHashSet.this.first = this.next; }
/*  993 */       else { FloatLinkedOpenHashSet.this.link[this.prev] = FloatLinkedOpenHashSet.this.link[this.prev] ^ (FloatLinkedOpenHashSet.this.link[this.prev] ^ this.next & 0xFFFFFFFFL) & 0xFFFFFFFFL; }
/*  994 */        if (this.next == -1) { FloatLinkedOpenHashSet.this.last = this.prev; }
/*  995 */       else { FloatLinkedOpenHashSet.this.link[this.next] = FloatLinkedOpenHashSet.this.link[this.next] ^ (FloatLinkedOpenHashSet.this.link[this.next] ^ (this.prev & 0xFFFFFFFFL) << 32L) & 0xFFFFFFFF00000000L; }
/*  996 */        int pos = this.curr;
/*  997 */       this.curr = -1;
/*  998 */       if (pos == FloatLinkedOpenHashSet.this.n) {
/*  999 */         FloatLinkedOpenHashSet.this.containsNull = false;
/* 1000 */         FloatLinkedOpenHashSet.this.key[FloatLinkedOpenHashSet.this.n] = 0.0F;
/*      */       } else {
/*      */         
/* 1003 */         float[] key = FloatLinkedOpenHashSet.this.key; while (true) {
/*      */           float curr;
/*      */           int last;
/* 1006 */           pos = (last = pos) + 1 & FloatLinkedOpenHashSet.this.mask;
/*      */           while (true) {
/* 1008 */             if (Float.floatToIntBits(curr = key[pos]) == 0) {
/* 1009 */               key[last] = 0.0F;
/*      */               return;
/*      */             } 
/* 1012 */             int slot = HashCommon.mix(HashCommon.float2int(curr)) & FloatLinkedOpenHashSet.this.mask;
/* 1013 */             if ((last <= pos) ? (last >= slot || slot > pos) : (last >= slot && slot > pos))
/* 1014 */               break;  pos = pos + 1 & FloatLinkedOpenHashSet.this.mask;
/*      */           } 
/* 1016 */           key[last] = curr;
/* 1017 */           if (this.next == pos) this.next = last; 
/* 1018 */           if (this.prev == pos) this.prev = last; 
/* 1019 */           FloatLinkedOpenHashSet.this.fixPointers(pos, last);
/*      */         } 
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
/*      */   public FloatListIterator iterator(float from) {
/* 1035 */     return new SetIterator(from);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public FloatListIterator iterator() {
/* 1046 */     return new SetIterator();
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
/*      */   public FloatSpliterator spliterator() {
/* 1066 */     return FloatSpliterators.asSpliterator(iterator(), Size64.sizeOf(this), 337);
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public void forEach(FloatConsumer action) {
/* 1072 */     int next = this.first;
/* 1073 */     while (next != -1) {
/* 1074 */       int curr = next;
/* 1075 */       next = (int)this.link[curr];
/*      */       
/* 1077 */       action.accept(this.key[curr]);
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
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean trim() {
/* 1095 */     return trim(this.size);
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
/* 1117 */     int l = HashCommon.nextPowerOfTwo((int)Math.ceil((n / this.f)));
/* 1118 */     if (l >= this.n || this.size > HashCommon.maxFill(l, this.f)) return true; 
/*      */     try {
/* 1120 */       rehash(l);
/* 1121 */     } catch (OutOfMemoryError cantDoIt) {
/* 1122 */       return false;
/*      */     } 
/* 1124 */     return true;
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
/* 1139 */     float[] key = this.key;
/* 1140 */     int mask = newN - 1;
/* 1141 */     float[] newKey = new float[newN + 1];
/* 1142 */     int i = this.first, prev = -1, newPrev = -1;
/* 1143 */     long[] link = this.link;
/* 1144 */     long[] newLink = new long[newN + 1];
/* 1145 */     this.first = -1;
/* 1146 */     for (int j = this.size; j-- != 0; ) {
/* 1147 */       int pos; if (Float.floatToIntBits(key[i]) == 0) { pos = newN; }
/*      */       else
/* 1149 */       { pos = HashCommon.mix(HashCommon.float2int(key[i])) & mask;
/* 1150 */         for (; Float.floatToIntBits(newKey[pos]) != 0; pos = pos + 1 & mask); }
/*      */       
/* 1152 */       newKey[pos] = key[i];
/* 1153 */       if (prev != -1) {
/* 1154 */         newLink[newPrev] = newLink[newPrev] ^ (newLink[newPrev] ^ pos & 0xFFFFFFFFL) & 0xFFFFFFFFL;
/* 1155 */         newLink[pos] = newLink[pos] ^ (newLink[pos] ^ (newPrev & 0xFFFFFFFFL) << 32L) & 0xFFFFFFFF00000000L;
/* 1156 */         newPrev = pos;
/*      */       } else {
/* 1158 */         newPrev = this.first = pos;
/*      */         
/* 1160 */         newLink[pos] = -1L;
/*      */       } 
/* 1162 */       int t = i;
/* 1163 */       i = (int)link[i];
/* 1164 */       prev = t;
/*      */     } 
/* 1166 */     this.link = newLink;
/* 1167 */     this.last = newPrev;
/* 1168 */     if (newPrev != -1)
/*      */     {
/* 1170 */       newLink[newPrev] = newLink[newPrev] | 0xFFFFFFFFL; } 
/* 1171 */     this.n = newN;
/* 1172 */     this.mask = mask;
/* 1173 */     this.maxFill = HashCommon.maxFill(this.n, this.f);
/* 1174 */     this.key = newKey;
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
/*      */   public FloatLinkedOpenHashSet clone() {
/*      */     FloatLinkedOpenHashSet c;
/*      */     try {
/* 1191 */       c = (FloatLinkedOpenHashSet)super.clone();
/* 1192 */     } catch (CloneNotSupportedException cantHappen) {
/* 1193 */       throw new InternalError();
/*      */     } 
/* 1195 */     c.key = (float[])this.key.clone();
/* 1196 */     c.containsNull = this.containsNull;
/* 1197 */     c.link = (long[])this.link.clone();
/* 1198 */     return c;
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
/* 1212 */     int h = 0;
/* 1213 */     for (int j = realSize(), i = 0; j-- != 0; ) {
/* 1214 */       for (; Float.floatToIntBits(this.key[i]) == 0; i++);
/* 1215 */       h += HashCommon.float2int(this.key[i]);
/* 1216 */       i++;
/*      */     } 
/*      */     
/* 1219 */     return h;
/*      */   }
/*      */   
/*      */   private void writeObject(ObjectOutputStream s) throws IOException {
/* 1223 */     FloatIterator i = iterator();
/* 1224 */     s.defaultWriteObject();
/* 1225 */     for (int j = this.size; j-- != 0; s.writeFloat(i.nextFloat()));
/*      */   }
/*      */   
/*      */   private void readObject(ObjectInputStream s) throws IOException, ClassNotFoundException {
/* 1229 */     s.defaultReadObject();
/* 1230 */     this.n = HashCommon.arraySize(this.size, this.f);
/* 1231 */     this.maxFill = HashCommon.maxFill(this.n, this.f);
/* 1232 */     this.mask = this.n - 1;
/* 1233 */     float[] key = this.key = new float[this.n + 1];
/* 1234 */     long[] link = this.link = new long[this.n + 1];
/* 1235 */     int prev = -1;
/* 1236 */     this.first = this.last = -1;
/*      */     
/* 1238 */     for (int i = this.size; i-- != 0; ) {
/* 1239 */       int pos; float k = s.readFloat();
/* 1240 */       if (Float.floatToIntBits(k) == 0)
/* 1241 */       { pos = this.n;
/* 1242 */         this.containsNull = true; }
/*      */       
/* 1244 */       else if (Float.floatToIntBits(key[pos = HashCommon.mix(HashCommon.float2int(k)) & this.mask]) != 0) { while (Float.floatToIntBits(key[pos = pos + 1 & this.mask]) != 0); }
/*      */       
/* 1246 */       key[pos] = k;
/* 1247 */       if (this.first != -1) {
/* 1248 */         link[prev] = link[prev] ^ (link[prev] ^ pos & 0xFFFFFFFFL) & 0xFFFFFFFFL;
/* 1249 */         link[pos] = link[pos] ^ (link[pos] ^ (prev & 0xFFFFFFFFL) << 32L) & 0xFFFFFFFF00000000L;
/* 1250 */         prev = pos; continue;
/*      */       } 
/* 1252 */       prev = this.first = pos;
/*      */       
/* 1254 */       link[pos] = link[pos] | 0xFFFFFFFF00000000L;
/*      */     } 
/*      */     
/* 1257 */     this.last = prev;
/* 1258 */     if (prev != -1)
/*      */     {
/* 1260 */       link[prev] = link[prev] | 0xFFFFFFFFL;
/*      */     }
/*      */   }
/*      */   
/*      */   private void checkTable() {}
/*      */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\i\\unimi\dsi\fastutil\floats\FloatLinkedOpenHashSet.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */