/*     */ package it.unimi.dsi.fastutil.objects;
/*     */ 
/*     */ import it.unimi.dsi.fastutil.BigArrays;
/*     */ import it.unimi.dsi.fastutil.Hash;
/*     */ import it.unimi.dsi.fastutil.HashCommon;
/*     */ import it.unimi.dsi.fastutil.Size64;
/*     */ import java.io.IOException;
/*     */ import java.io.ObjectInputStream;
/*     */ import java.io.ObjectOutputStream;
/*     */ import java.io.Serializable;
/*     */ import java.util.Collection;
/*     */ import java.util.Iterator;
/*     */ import java.util.NoSuchElementException;
/*     */ import java.util.Spliterator;
/*     */ import java.util.function.Consumer;
/*     */ import java.util.function.Supplier;
/*     */ import java.util.stream.Collector;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class ReferenceOpenHashBigSet<K>
/*     */   extends AbstractReferenceSet<K>
/*     */   implements Serializable, Cloneable, Hash, Size64
/*     */ {
/*     */   private static final long serialVersionUID = 0L;
/*     */   private static final boolean ASSERTS = false;
/*     */   protected transient K[][] key;
/*     */   protected transient long mask;
/*     */   protected transient int segmentMask;
/*     */   protected transient int baseMask;
/*     */   protected transient boolean containsNull;
/*     */   protected transient long n;
/*     */   protected transient long maxFill;
/*     */   protected final transient long minN;
/*     */   protected final float f;
/*     */   protected long size;
/*     */   
/*     */   private void initMasks() {
/*  84 */     this.mask = this.n - 1L;
/*     */ 
/*     */ 
/*     */     
/*  88 */     this.segmentMask = (this.key[0]).length - 1;
/*  89 */     this.baseMask = this.key.length - 1;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ReferenceOpenHashBigSet(long expected, float f) {
/* 103 */     if (f <= 0.0F || f > 1.0F) throw new IllegalArgumentException("Load factor must be greater than 0 and smaller than or equal to 1"); 
/* 104 */     if (this.n < 0L) throw new IllegalArgumentException("The expected number of elements must be nonnegative"); 
/* 105 */     this.f = f;
/* 106 */     this.minN = this.n = HashCommon.bigArraySize(expected, f);
/* 107 */     this.maxFill = HashCommon.maxFill(this.n, f);
/* 108 */     this.key = (K[][])ObjectBigArrays.newBigArray(this.n);
/* 109 */     initMasks();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ReferenceOpenHashBigSet(long expected) {
/* 118 */     this(expected, 0.75F);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ReferenceOpenHashBigSet() {
/* 126 */     this(16L, 0.75F);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ReferenceOpenHashBigSet(Collection<? extends K> c, float f) {
/* 136 */     this(Size64.sizeOf(c), f);
/* 137 */     addAll(c);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ReferenceOpenHashBigSet(Collection<? extends K> c) {
/* 147 */     this(c, 0.75F);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ReferenceOpenHashBigSet(ReferenceCollection<? extends K> c, float f) {
/* 157 */     this(Size64.sizeOf(c), f);
/* 158 */     addAll(c);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ReferenceOpenHashBigSet(ReferenceCollection<? extends K> c) {
/* 168 */     this(c, 0.75F);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ReferenceOpenHashBigSet(Iterator<? extends K> i, float f) {
/* 178 */     this(16L, f);
/* 179 */     for (; i.hasNext(); add(i.next()));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ReferenceOpenHashBigSet(Iterator<? extends K> i) {
/* 189 */     this(i, 0.75F);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ReferenceOpenHashBigSet(K[] a, int offset, int length, float f) {
/* 201 */     this((length < 0) ? 0L : length, f);
/* 202 */     ObjectArrays.ensureOffsetLength(a, offset, length);
/* 203 */     for (int i = 0; i < length; ) { add(a[offset + i]); i++; }
/*     */   
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ReferenceOpenHashBigSet(K[] a, int offset, int length) {
/* 215 */     this(a, offset, length, 0.75F);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ReferenceOpenHashBigSet(K[] a, float f) {
/* 225 */     this(a, 0, a.length, f);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ReferenceOpenHashBigSet(K[] a) {
/* 235 */     this(a, 0.75F);
/*     */   }
/*     */ 
/*     */   
/*     */   private ReferenceOpenHashBigSet<K> combine(ReferenceOpenHashBigSet<? extends K> toAddFrom) {
/* 240 */     addAll(toAddFrom);
/* 241 */     return this;
/*     */   }
/*     */   
/* 244 */   private static final Collector<Object, ?, ReferenceOpenHashBigSet<Object>> TO_SET_COLLECTOR = (Collector)Collector.of(ReferenceOpenHashBigSet::new, ReferenceOpenHashBigSet::add, ReferenceOpenHashBigSet::combine, new Collector.Characteristics[0]);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static <K> Collector<K, ?, ReferenceOpenHashBigSet<K>> toBigSet() {
/* 251 */     return (Collector)TO_SET_COLLECTOR;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static <K> Collector<K, ?, ReferenceOpenHashBigSet<K>> toBigSetWithExpectedSize(long expectedSize) {
/* 258 */     return (Collector)Collector.of(() -> new ReferenceOpenHashBigSet(expectedSize), ReferenceOpenHashBigSet::add, ReferenceOpenHashBigSet::combine, new Collector.Characteristics[0]);
/*     */   }
/*     */   
/*     */   private long realSize() {
/* 262 */     return this.containsNull ? (this.size - 1L) : this.size;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void ensureCapacity(long capacity) {
/* 272 */     long needed = HashCommon.bigArraySize(capacity, this.f);
/* 273 */     if (needed > this.n) rehash(needed);
/*     */   
/*     */   }
/*     */   
/*     */   public boolean addAll(Collection<? extends K> c) {
/* 278 */     long size = Size64.sizeOf(c);
/*     */     
/* 280 */     if (this.f <= 0.5D) { ensureCapacity(size); }
/* 281 */     else { ensureCapacity(size64() + size); }
/* 282 */      return super.addAll(c);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean add(K k) {
/* 288 */     if (k == null) {
/* 289 */       if (this.containsNull) return false; 
/* 290 */       this.containsNull = true;
/*     */     } else {
/*     */       
/* 293 */       K[][] key = this.key;
/* 294 */       long h = HashCommon.mix(System.identityHashCode(k)); int displ, base;
/*     */       K curr;
/* 296 */       if ((curr = key[base = (int)((h & this.mask) >>> 27L)][displ = (int)(h & this.segmentMask)]) != null) {
/* 297 */         if (curr == k) return false;  while (true) {
/* 298 */           if ((curr = key[base = base + (((displ = displ + 1 & this.segmentMask) == 0) ? 1 : 0) & this.baseMask][displ]) != null) { if (curr == k) return false;  continue; }  break;
/*     */         } 
/* 300 */       }  key[base][displ] = k;
/*     */     } 
/* 302 */     if (this.size++ >= this.maxFill) rehash(2L * this.n);
/*     */     
/* 304 */     return true;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected final void shiftKeys(long pos) {
/* 316 */     K[][] key = this.key; while (true) {
/*     */       long last;
/* 318 */       pos = (last = pos) + 1L & this.mask;
/*     */       while (true) {
/* 320 */         if (BigArrays.get((Object[][])key, pos) == null) {
/* 321 */           BigArrays.set((Object[][])key, last, null);
/*     */           return;
/*     */         } 
/* 324 */         long slot = HashCommon.mix(System.identityHashCode(BigArrays.get((Object[][])key, pos))) & this.mask;
/* 325 */         if ((last <= pos) ? (last >= slot || slot > pos) : (last >= slot && slot > pos))
/* 326 */           break;  pos = pos + 1L & this.mask;
/*     */       } 
/* 328 */       BigArrays.set((Object[][])key, last, BigArrays.get((Object[][])key, pos));
/*     */     } 
/*     */   }
/*     */   
/*     */   private boolean removeEntry(int base, int displ) {
/* 333 */     this.size--;
/* 334 */     shiftKeys(base * 134217728L + displ);
/* 335 */     if (this.n > this.minN && this.size < this.maxFill / 4L && this.n > 16L) rehash(this.n / 2L); 
/* 336 */     return true;
/*     */   }
/*     */   
/*     */   private boolean removeNullEntry() {
/* 340 */     this.containsNull = false;
/* 341 */     this.size--;
/* 342 */     if (this.n > this.minN && this.size < this.maxFill / 4L && this.n > 16L) rehash(this.n / 2L); 
/* 343 */     return true;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean remove(Object k) {
/* 348 */     if (k == null) {
/* 349 */       if (this.containsNull) return removeNullEntry(); 
/* 350 */       return false;
/*     */     } 
/*     */     
/* 353 */     K[][] key = this.key;
/* 354 */     long h = HashCommon.mix(System.identityHashCode(k));
/*     */     K curr;
/*     */     int displ, base;
/* 357 */     if ((curr = key[base = (int)((h & this.mask) >>> 27L)][displ = (int)(h & this.segmentMask)]) == null) return false; 
/* 358 */     if (curr == k) return removeEntry(base, displ); 
/*     */     while (true) {
/* 360 */       if ((curr = key[base = base + (((displ = displ + 1 & this.segmentMask) == 0) ? 1 : 0) & this.baseMask][displ]) == null) return false; 
/* 361 */       if (curr == k) return removeEntry(base, displ);
/*     */     
/*     */     } 
/*     */   }
/*     */   
/*     */   public boolean contains(Object k) {
/* 367 */     if (k == null) return this.containsNull;
/*     */     
/* 369 */     K[][] key = this.key;
/* 370 */     long h = HashCommon.mix(System.identityHashCode(k));
/*     */     K curr;
/*     */     int displ, base;
/* 373 */     if ((curr = key[base = (int)((h & this.mask) >>> 27L)][displ = (int)(h & this.segmentMask)]) == null) return false; 
/* 374 */     if (curr == k) return true; 
/*     */     while (true) {
/* 376 */       if ((curr = key[base = base + (((displ = displ + 1 & this.segmentMask) == 0) ? 1 : 0) & this.baseMask][displ]) == null) return false; 
/* 377 */       if (curr == k) return true;
/*     */     
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void clear() {
/* 393 */     if (this.size == 0L)
/* 394 */       return;  this.size = 0L;
/* 395 */     this.containsNull = false;
/* 396 */     BigArrays.fill((Object[][])this.key, null);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private class SetIterator
/*     */     implements ObjectIterator<K>
/*     */   {
/* 406 */     int base = ReferenceOpenHashBigSet.this.key.length;
/*     */ 
/*     */ 
/*     */     
/*     */     int displ;
/*     */ 
/*     */ 
/*     */     
/* 414 */     long last = -1L;
/*     */     
/* 416 */     long c = ReferenceOpenHashBigSet.this.size;
/*     */     
/* 418 */     boolean mustReturnNull = ReferenceOpenHashBigSet.this.containsNull;
/*     */ 
/*     */ 
/*     */     
/*     */     ReferenceArrayList<K> wrapped;
/*     */ 
/*     */ 
/*     */     
/*     */     public boolean hasNext() {
/* 427 */       return (this.c != 0L);
/*     */     }
/*     */ 
/*     */     
/*     */     public K next() {
/* 432 */       if (!hasNext()) throw new NoSuchElementException(); 
/* 433 */       this.c--;
/* 434 */       if (this.mustReturnNull) {
/* 435 */         this.mustReturnNull = false;
/* 436 */         this.last = ReferenceOpenHashBigSet.this.n;
/* 437 */         return null;
/*     */       } 
/* 439 */       K[][] key = ReferenceOpenHashBigSet.this.key;
/*     */       while (true) {
/* 441 */         if (this.displ == 0 && this.base <= 0) {
/*     */           
/* 443 */           this.last = Long.MIN_VALUE;
/* 444 */           return this.wrapped.get(---this.base - 1);
/*     */         } 
/* 446 */         if (this.displ-- == 0) this.displ = (key[--this.base]).length - 1; 
/* 447 */         K k = key[this.base][this.displ];
/* 448 */         if (k != null) {
/* 449 */           this.last = this.base * 134217728L + this.displ;
/* 450 */           return k;
/*     */         } 
/*     */       } 
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     private final void shiftKeys(long pos) {
/* 465 */       K[][] key = ReferenceOpenHashBigSet.this.key; while (true) {
/*     */         K curr; long last;
/* 467 */         pos = (last = pos) + 1L & ReferenceOpenHashBigSet.this.mask;
/*     */         while (true) {
/* 469 */           if ((curr = (K)BigArrays.get((Object[][])key, pos)) == null) {
/* 470 */             BigArrays.set((Object[][])key, last, null);
/*     */             return;
/*     */           } 
/* 473 */           long slot = HashCommon.mix(System.identityHashCode(curr)) & ReferenceOpenHashBigSet.this.mask;
/* 474 */           if ((last <= pos) ? (last >= slot || slot > pos) : (last >= slot && slot > pos))
/* 475 */             break;  pos = pos + 1L & ReferenceOpenHashBigSet.this.mask;
/*     */         } 
/* 477 */         if (pos < last) {
/* 478 */           if (this.wrapped == null) this.wrapped = new ReferenceArrayList<>(); 
/* 479 */           this.wrapped.add((K)BigArrays.get((Object[][])key, pos));
/*     */         } 
/* 481 */         BigArrays.set((Object[][])key, last, curr);
/*     */       } 
/*     */     }
/*     */ 
/*     */     
/*     */     public void remove() {
/* 487 */       if (this.last == -1L) throw new IllegalStateException(); 
/* 488 */       if (this.last == ReferenceOpenHashBigSet.this.n) { ReferenceOpenHashBigSet.this.containsNull = false; }
/* 489 */       else if (this.base >= 0) { shiftKeys(this.last); }
/*     */       else
/*     */       
/* 492 */       { ReferenceOpenHashBigSet.this.remove(this.wrapped.set(-this.base - 1, null));
/* 493 */         this.last = -1L;
/*     */         return; }
/*     */       
/* 496 */       ReferenceOpenHashBigSet.this.size--;
/* 497 */       this.last = -1L;
/*     */     }
/*     */     
/*     */     private SetIterator() {}
/*     */   }
/*     */   
/*     */   public ObjectIterator<K> iterator() {
/* 504 */     return new SetIterator();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private class SetSpliterator
/*     */     implements ObjectSpliterator<K>
/*     */   {
/*     */     private static final int POST_SPLIT_CHARACTERISTICS = 1;
/*     */ 
/*     */ 
/*     */     
/* 518 */     long pos = 0L;
/*     */     
/* 520 */     long max = ReferenceOpenHashBigSet.this.n;
/*     */     
/* 522 */     long c = 0L;
/*     */     
/* 524 */     boolean mustReturnNull = ReferenceOpenHashBigSet.this.containsNull;
/*     */ 
/*     */     
/*     */     boolean hasSplit = false;
/*     */ 
/*     */     
/*     */     SetSpliterator(long pos, long max, boolean mustReturnNull, boolean hasSplit) {
/* 531 */       this.pos = pos;
/* 532 */       this.max = max;
/* 533 */       this.mustReturnNull = mustReturnNull;
/* 534 */       this.hasSplit = hasSplit;
/*     */     }
/*     */ 
/*     */     
/*     */     public boolean tryAdvance(Consumer<? super K> action) {
/* 539 */       if (this.mustReturnNull) {
/* 540 */         this.mustReturnNull = false;
/* 541 */         this.c++;
/* 542 */         action.accept(null);
/* 543 */         return true;
/*     */       } 
/* 545 */       K[][] key = ReferenceOpenHashBigSet.this.key;
/* 546 */       while (this.pos < this.max) {
/* 547 */         K gotten = (K)BigArrays.get((Object[][])key, this.pos);
/* 548 */         if (gotten != null) {
/* 549 */           this.c++;
/* 550 */           this.pos++;
/* 551 */           action.accept(gotten);
/* 552 */           return true;
/*     */         } 
/* 554 */         this.pos++;
/*     */       } 
/*     */       
/* 557 */       return false;
/*     */     }
/*     */ 
/*     */     
/*     */     public void forEachRemaining(Consumer<? super K> action) {
/* 562 */       if (this.mustReturnNull) {
/* 563 */         this.mustReturnNull = false;
/* 564 */         action.accept(null);
/* 565 */         this.c++;
/*     */       } 
/* 567 */       K[][] key = ReferenceOpenHashBigSet.this.key;
/* 568 */       while (this.pos < this.max) {
/* 569 */         K gotten = (K)BigArrays.get((Object[][])key, this.pos);
/* 570 */         if (gotten != null) {
/* 571 */           action.accept(gotten);
/* 572 */           this.c++;
/*     */         } 
/* 574 */         this.pos++;
/*     */       } 
/*     */     }
/*     */ 
/*     */     
/*     */     public int characteristics() {
/* 580 */       return this.hasSplit ? 1 : 65;
/*     */     }
/*     */ 
/*     */     
/*     */     public long estimateSize() {
/* 585 */       if (!this.hasSplit)
/*     */       {
/* 587 */         return ReferenceOpenHashBigSet.this.size - this.c;
/*     */       }
/*     */ 
/*     */ 
/*     */       
/* 592 */       return Math.min(ReferenceOpenHashBigSet.this.size - this.c, (long)(ReferenceOpenHashBigSet.this.realSize() / ReferenceOpenHashBigSet.this.n * (this.max - this.pos)) + (this.mustReturnNull ? 1L : 0L));
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     public SetSpliterator trySplit() {
/* 598 */       if (this.pos >= this.max - 1L) return null; 
/* 599 */       long retLen = this.max - this.pos >> 1L;
/* 600 */       if (retLen <= 1L) return null; 
/* 601 */       long myNewPos = this.pos + retLen;
/*     */ 
/*     */       
/* 604 */       myNewPos = BigArrays.nearestSegmentStart(myNewPos, this.pos + 1L, this.max - 1L);
/* 605 */       long retPos = this.pos;
/* 606 */       long retMax = myNewPos;
/*     */ 
/*     */ 
/*     */       
/* 610 */       SetSpliterator split = new SetSpliterator(retPos, retMax, this.mustReturnNull, true);
/* 611 */       this.pos = myNewPos;
/* 612 */       this.mustReturnNull = false;
/* 613 */       this.hasSplit = true;
/* 614 */       return split;
/*     */     }
/*     */ 
/*     */     
/*     */     public long skip(long n) {
/* 619 */       if (n < 0L) throw new IllegalArgumentException("Argument must be nonnegative: " + n); 
/* 620 */       if (n == 0L) return 0L; 
/* 621 */       long skipped = 0L;
/* 622 */       if (this.mustReturnNull) {
/* 623 */         this.mustReturnNull = false;
/* 624 */         skipped++;
/* 625 */         n--;
/*     */       } 
/* 627 */       K[][] key = ReferenceOpenHashBigSet.this.key;
/* 628 */       while (this.pos < this.max && n > 0L) {
/* 629 */         if (BigArrays.get((Object[][])key, this.pos++) != null) {
/* 630 */           skipped++;
/* 631 */           n--;
/*     */         } 
/*     */       } 
/* 634 */       return skipped;
/*     */     }
/*     */     
/*     */     SetSpliterator() {} }
/*     */   
/*     */   public ObjectSpliterator<K> spliterator() {
/* 640 */     return new SetSpliterator();
/*     */   }
/*     */ 
/*     */   
/*     */   public void forEach(Consumer<? super K> action) {
/* 645 */     if (this.containsNull) {
/* 646 */       action.accept(null);
/*     */     }
/* 648 */     long pos = 0L;
/* 649 */     long max = this.n;
/* 650 */     K[][] key = this.key;
/* 651 */     while (pos < max) {
/* 652 */       K gotten = (K)BigArrays.get((Object[][])key, pos++);
/* 653 */       if (gotten != null) {
/* 654 */         action.accept(gotten);
/*     */       }
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean trim() {
/* 673 */     return trim(this.size);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean trim(long n) {
/* 695 */     long l = HashCommon.bigArraySize(n, this.f);
/* 696 */     if (l >= this.n || this.size > HashCommon.maxFill(l, this.f)) return true; 
/*     */     try {
/* 698 */       rehash(l);
/* 699 */     } catch (OutOfMemoryError cantDoIt) {
/* 700 */       return false;
/*     */     } 
/* 702 */     return true;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void rehash(long newN) {
/* 717 */     K[][] key = this.key;
/* 718 */     K[][] newKey = (K[][])ObjectBigArrays.newBigArray(newN);
/* 719 */     long mask = newN - 1L;
/* 720 */     int newSegmentMask = (newKey[0]).length - 1;
/* 721 */     int newBaseMask = newKey.length - 1;
/* 722 */     int base = 0, displ = 0;
/*     */ 
/*     */     
/* 725 */     for (long i = realSize(); i-- != 0L; ) {
/* 726 */       for (; key[base][displ] == null; base += ((displ = displ + 1 & this.segmentMask) == 0) ? 1 : 0);
/* 727 */       K k = key[base][displ];
/* 728 */       long h = HashCommon.mix(System.identityHashCode(k));
/*     */       int b, d;
/* 730 */       if (newKey[b = (int)((h & mask) >>> 27L)][d = (int)(h & newSegmentMask)] != null) while (true) { if (newKey[b = b + (((d = d + 1 & newSegmentMask) == 0) ? 1 : 0) & newBaseMask][d] != null)
/* 731 */             continue;  break; }   newKey[b][d] = k;
/* 732 */       base += ((displ = displ + 1 & this.segmentMask) == 0) ? 1 : 0;
/*     */     } 
/* 734 */     this.n = newN;
/* 735 */     this.key = newKey;
/* 736 */     initMasks();
/* 737 */     this.maxFill = HashCommon.maxFill(this.n, this.f);
/*     */   }
/*     */ 
/*     */   
/*     */   @Deprecated
/*     */   public int size() {
/* 743 */     return (int)Math.min(2147483647L, this.size);
/*     */   }
/*     */ 
/*     */   
/*     */   public long size64() {
/* 748 */     return this.size;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isEmpty() {
/* 753 */     return (this.size == 0L);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ReferenceOpenHashBigSet<K> clone() {
/*     */     ReferenceOpenHashBigSet<K> c;
/*     */     try {
/* 770 */       c = (ReferenceOpenHashBigSet<K>)super.clone();
/* 771 */     } catch (CloneNotSupportedException cantHappen) {
/* 772 */       throw new InternalError();
/*     */     } 
/* 774 */     c.key = (K[][])BigArrays.copy((Object[][])this.key);
/* 775 */     c.containsNull = this.containsNull;
/* 776 */     return c;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int hashCode() {
/* 790 */     K[][] key = this.key;
/* 791 */     int h = 0, base = 0, displ = 0;
/* 792 */     for (long j = realSize(); j-- != 0L; ) {
/* 793 */       for (; key[base][displ] == null; base += ((displ = displ + 1 & this.segmentMask) == 0) ? 1 : 0);
/* 794 */       if (this != key[base][displ]) h += System.identityHashCode(key[base][displ]); 
/* 795 */       base += ((displ = displ + 1 & this.segmentMask) == 0) ? 1 : 0;
/*     */     } 
/* 797 */     return h;
/*     */   }
/*     */   
/*     */   private void writeObject(ObjectOutputStream s) throws IOException {
/* 801 */     ObjectIterator<K> i = iterator();
/* 802 */     s.defaultWriteObject();
/* 803 */     for (long j = this.size; j-- != 0L; s.writeObject(i.next()));
/*     */   }
/*     */ 
/*     */   
/*     */   private void readObject(ObjectInputStream s) throws IOException, ClassNotFoundException {
/* 808 */     s.defaultReadObject();
/* 809 */     this.n = HashCommon.bigArraySize(this.size, this.f);
/* 810 */     this.maxFill = HashCommon.maxFill(this.n, this.f);
/* 811 */     K[][] key = this.key = (K[][])ObjectBigArrays.newBigArray(this.n);
/* 812 */     initMasks();
/*     */ 
/*     */ 
/*     */     
/* 816 */     for (long i = this.size; i-- != 0L; ) {
/* 817 */       K k = (K)s.readObject();
/* 818 */       if (k == null) { this.containsNull = true; continue; }
/*     */       
/* 820 */       long h = HashCommon.mix(System.identityHashCode(k)); int base, displ;
/* 821 */       if (key[base = (int)((h & this.mask) >>> 27L)][displ = (int)(h & this.segmentMask)] != null) while (true) { if (key[base = base + (((displ = displ + 1 & this.segmentMask) == 0) ? 1 : 0) & this.baseMask][displ] != null)
/* 822 */             continue;  break; }   key[base][displ] = k;
/*     */     } 
/*     */   }
/*     */   
/*     */   private void checkTable() {}
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\i\\unimi\dsi\fastutil\objects\ReferenceOpenHashBigSet.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */