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
/*     */ public class ObjectOpenHashBigSet<K>
/*     */   extends AbstractObjectSet<K>
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
/*     */   public ObjectOpenHashBigSet(long expected, float f) {
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
/*     */   public ObjectOpenHashBigSet(long expected) {
/* 118 */     this(expected, 0.75F);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ObjectOpenHashBigSet() {
/* 126 */     this(16L, 0.75F);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ObjectOpenHashBigSet(Collection<? extends K> c, float f) {
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
/*     */   public ObjectOpenHashBigSet(Collection<? extends K> c) {
/* 147 */     this(c, 0.75F);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ObjectOpenHashBigSet(ObjectCollection<? extends K> c, float f) {
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
/*     */   public ObjectOpenHashBigSet(ObjectCollection<? extends K> c) {
/* 168 */     this(c, 0.75F);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ObjectOpenHashBigSet(Iterator<? extends K> i, float f) {
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
/*     */   public ObjectOpenHashBigSet(Iterator<? extends K> i) {
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
/*     */   public ObjectOpenHashBigSet(K[] a, int offset, int length, float f) {
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
/*     */   public ObjectOpenHashBigSet(K[] a, int offset, int length) {
/* 215 */     this(a, offset, length, 0.75F);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ObjectOpenHashBigSet(K[] a, float f) {
/* 225 */     this(a, 0, a.length, f);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ObjectOpenHashBigSet(K[] a) {
/* 235 */     this(a, 0.75F);
/*     */   }
/*     */ 
/*     */   
/*     */   private ObjectOpenHashBigSet<K> combine(ObjectOpenHashBigSet<? extends K> toAddFrom) {
/* 240 */     addAll(toAddFrom);
/* 241 */     return this;
/*     */   }
/*     */   
/* 244 */   private static final Collector<Object, ?, ObjectOpenHashBigSet<Object>> TO_SET_COLLECTOR = (Collector)Collector.of(ObjectOpenHashBigSet::new, ObjectOpenHashBigSet::add, ObjectOpenHashBigSet::combine, new Collector.Characteristics[0]);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static <K> Collector<K, ?, ObjectOpenHashBigSet<K>> toBigSet() {
/* 251 */     return (Collector)TO_SET_COLLECTOR;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static <K> Collector<K, ?, ObjectOpenHashBigSet<K>> toBigSetWithExpectedSize(long expectedSize) {
/* 258 */     return (Collector)Collector.of(() -> new ObjectOpenHashBigSet(expectedSize), ObjectOpenHashBigSet::add, ObjectOpenHashBigSet::combine, new Collector.Characteristics[0]);
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
/* 294 */       long h = HashCommon.mix(k.hashCode()); int displ, base;
/*     */       K curr;
/* 296 */       if ((curr = key[base = (int)((h & this.mask) >>> 27L)][displ = (int)(h & this.segmentMask)]) != null) {
/* 297 */         if (curr.equals(k)) return false;  while (true) {
/* 298 */           if ((curr = key[base = base + (((displ = displ + 1 & this.segmentMask) == 0) ? 1 : 0) & this.baseMask][displ]) != null) { if (curr.equals(k)) return false;  continue; }  break;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public K addOrGet(K k) {
/* 322 */     if (k == null) {
/* 323 */       if (this.containsNull) return null; 
/* 324 */       this.containsNull = true;
/*     */     } else {
/*     */       
/* 327 */       K[][] key = this.key;
/* 328 */       long h = HashCommon.mix(k.hashCode()); int displ, base;
/*     */       K curr;
/* 330 */       if ((curr = key[base = (int)((h & this.mask) >>> 27L)][displ = (int)(h & this.segmentMask)]) != null) {
/* 331 */         if (curr.equals(k)) return curr;  while (true) {
/* 332 */           if ((curr = key[base = base + (((displ = displ + 1 & this.segmentMask) == 0) ? 1 : 0) & this.baseMask][displ]) != null) { if (curr.equals(k)) return curr;  continue; }  break;
/*     */         } 
/* 334 */       }  key[base][displ] = k;
/*     */     } 
/* 336 */     if (this.size++ >= this.maxFill) rehash(2L * this.n);
/*     */     
/* 338 */     return k;
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
/* 350 */     K[][] key = this.key; while (true) {
/*     */       long last;
/* 352 */       pos = (last = pos) + 1L & this.mask;
/*     */       while (true) {
/* 354 */         if (BigArrays.get((Object[][])key, pos) == null) {
/* 355 */           BigArrays.set((Object[][])key, last, null);
/*     */           return;
/*     */         } 
/* 358 */         long slot = HashCommon.mix(BigArrays.get((Object[][])key, pos).hashCode()) & this.mask;
/* 359 */         if ((last <= pos) ? (last >= slot || slot > pos) : (last >= slot && slot > pos))
/* 360 */           break;  pos = pos + 1L & this.mask;
/*     */       } 
/* 362 */       BigArrays.set((Object[][])key, last, BigArrays.get((Object[][])key, pos));
/*     */     } 
/*     */   }
/*     */   
/*     */   private boolean removeEntry(int base, int displ) {
/* 367 */     this.size--;
/* 368 */     shiftKeys(base * 134217728L + displ);
/* 369 */     if (this.n > this.minN && this.size < this.maxFill / 4L && this.n > 16L) rehash(this.n / 2L); 
/* 370 */     return true;
/*     */   }
/*     */   
/*     */   private boolean removeNullEntry() {
/* 374 */     this.containsNull = false;
/* 375 */     this.size--;
/* 376 */     if (this.n > this.minN && this.size < this.maxFill / 4L && this.n > 16L) rehash(this.n / 2L); 
/* 377 */     return true;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean remove(Object k) {
/* 382 */     if (k == null) {
/* 383 */       if (this.containsNull) return removeNullEntry(); 
/* 384 */       return false;
/*     */     } 
/*     */     
/* 387 */     K[][] key = this.key;
/* 388 */     long h = HashCommon.mix(k.hashCode());
/*     */     K curr;
/*     */     int displ, base;
/* 391 */     if ((curr = key[base = (int)((h & this.mask) >>> 27L)][displ = (int)(h & this.segmentMask)]) == null) return false; 
/* 392 */     if (curr.equals(k)) return removeEntry(base, displ); 
/*     */     while (true) {
/* 394 */       if ((curr = key[base = base + (((displ = displ + 1 & this.segmentMask) == 0) ? 1 : 0) & this.baseMask][displ]) == null) return false; 
/* 395 */       if (curr.equals(k)) return removeEntry(base, displ);
/*     */     
/*     */     } 
/*     */   }
/*     */   
/*     */   public boolean contains(Object k) {
/* 401 */     if (k == null) return this.containsNull;
/*     */     
/* 403 */     K[][] key = this.key;
/* 404 */     long h = HashCommon.mix(k.hashCode());
/*     */     K curr;
/*     */     int displ, base;
/* 407 */     if ((curr = key[base = (int)((h & this.mask) >>> 27L)][displ = (int)(h & this.segmentMask)]) == null) return false; 
/* 408 */     if (curr.equals(k)) return true; 
/*     */     while (true) {
/* 410 */       if ((curr = key[base = base + (((displ = displ + 1 & this.segmentMask) == 0) ? 1 : 0) & this.baseMask][displ]) == null) return false; 
/* 411 */       if (curr.equals(k)) return true;
/*     */     
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public K get(Object k) {
/* 421 */     if (k == null) return null;
/*     */     
/* 423 */     K[][] key = this.key;
/* 424 */     long h = HashCommon.mix(k.hashCode());
/*     */     K curr;
/*     */     int displ, base;
/* 427 */     if ((curr = key[base = (int)((h & this.mask) >>> 27L)][displ = (int)(h & this.segmentMask)]) == null) return null; 
/* 428 */     if (curr.equals(k)) return curr; 
/*     */     while (true) {
/* 430 */       if ((curr = key[base = base + (((displ = displ + 1 & this.segmentMask) == 0) ? 1 : 0) & this.baseMask][displ]) == null) return null; 
/* 431 */       if (curr.equals(k)) return curr;
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
/* 447 */     if (this.size == 0L)
/* 448 */       return;  this.size = 0L;
/* 449 */     this.containsNull = false;
/* 450 */     BigArrays.fill((Object[][])this.key, null);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private class SetIterator
/*     */     implements ObjectIterator<K>
/*     */   {
/* 460 */     int base = ObjectOpenHashBigSet.this.key.length;
/*     */ 
/*     */ 
/*     */     
/*     */     int displ;
/*     */ 
/*     */ 
/*     */     
/* 468 */     long last = -1L;
/*     */     
/* 470 */     long c = ObjectOpenHashBigSet.this.size;
/*     */     
/* 472 */     boolean mustReturnNull = ObjectOpenHashBigSet.this.containsNull;
/*     */ 
/*     */ 
/*     */     
/*     */     ObjectArrayList<K> wrapped;
/*     */ 
/*     */ 
/*     */     
/*     */     public boolean hasNext() {
/* 481 */       return (this.c != 0L);
/*     */     }
/*     */ 
/*     */     
/*     */     public K next() {
/* 486 */       if (!hasNext()) throw new NoSuchElementException(); 
/* 487 */       this.c--;
/* 488 */       if (this.mustReturnNull) {
/* 489 */         this.mustReturnNull = false;
/* 490 */         this.last = ObjectOpenHashBigSet.this.n;
/* 491 */         return null;
/*     */       } 
/* 493 */       K[][] key = ObjectOpenHashBigSet.this.key;
/*     */       while (true) {
/* 495 */         if (this.displ == 0 && this.base <= 0) {
/*     */           
/* 497 */           this.last = Long.MIN_VALUE;
/* 498 */           return this.wrapped.get(---this.base - 1);
/*     */         } 
/* 500 */         if (this.displ-- == 0) this.displ = (key[--this.base]).length - 1; 
/* 501 */         K k = key[this.base][this.displ];
/* 502 */         if (k != null) {
/* 503 */           this.last = this.base * 134217728L + this.displ;
/* 504 */           return k;
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
/* 519 */       K[][] key = ObjectOpenHashBigSet.this.key; while (true) {
/*     */         K curr; long last;
/* 521 */         pos = (last = pos) + 1L & ObjectOpenHashBigSet.this.mask;
/*     */         while (true) {
/* 523 */           if ((curr = (K)BigArrays.get((Object[][])key, pos)) == null) {
/* 524 */             BigArrays.set((Object[][])key, last, null);
/*     */             return;
/*     */           } 
/* 527 */           long slot = HashCommon.mix(curr.hashCode()) & ObjectOpenHashBigSet.this.mask;
/* 528 */           if ((last <= pos) ? (last >= slot || slot > pos) : (last >= slot && slot > pos))
/* 529 */             break;  pos = pos + 1L & ObjectOpenHashBigSet.this.mask;
/*     */         } 
/* 531 */         if (pos < last) {
/* 532 */           if (this.wrapped == null) this.wrapped = new ObjectArrayList<>(); 
/* 533 */           this.wrapped.add((K)BigArrays.get((Object[][])key, pos));
/*     */         } 
/* 535 */         BigArrays.set((Object[][])key, last, curr);
/*     */       } 
/*     */     }
/*     */ 
/*     */     
/*     */     public void remove() {
/* 541 */       if (this.last == -1L) throw new IllegalStateException(); 
/* 542 */       if (this.last == ObjectOpenHashBigSet.this.n) { ObjectOpenHashBigSet.this.containsNull = false; }
/* 543 */       else if (this.base >= 0) { shiftKeys(this.last); }
/*     */       else
/*     */       
/* 546 */       { ObjectOpenHashBigSet.this.remove(this.wrapped.set(-this.base - 1, null));
/* 547 */         this.last = -1L;
/*     */         return; }
/*     */       
/* 550 */       ObjectOpenHashBigSet.this.size--;
/* 551 */       this.last = -1L;
/*     */     }
/*     */     
/*     */     private SetIterator() {}
/*     */   }
/*     */   
/*     */   public ObjectIterator<K> iterator() {
/* 558 */     return new SetIterator();
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
/* 572 */     long pos = 0L;
/*     */     
/* 574 */     long max = ObjectOpenHashBigSet.this.n;
/*     */     
/* 576 */     long c = 0L;
/*     */     
/* 578 */     boolean mustReturnNull = ObjectOpenHashBigSet.this.containsNull;
/*     */ 
/*     */     
/*     */     boolean hasSplit = false;
/*     */ 
/*     */     
/*     */     SetSpliterator(long pos, long max, boolean mustReturnNull, boolean hasSplit) {
/* 585 */       this.pos = pos;
/* 586 */       this.max = max;
/* 587 */       this.mustReturnNull = mustReturnNull;
/* 588 */       this.hasSplit = hasSplit;
/*     */     }
/*     */ 
/*     */     
/*     */     public boolean tryAdvance(Consumer<? super K> action) {
/* 593 */       if (this.mustReturnNull) {
/* 594 */         this.mustReturnNull = false;
/* 595 */         this.c++;
/* 596 */         action.accept(null);
/* 597 */         return true;
/*     */       } 
/* 599 */       K[][] key = ObjectOpenHashBigSet.this.key;
/* 600 */       while (this.pos < this.max) {
/* 601 */         K gotten = (K)BigArrays.get((Object[][])key, this.pos);
/* 602 */         if (gotten != null) {
/* 603 */           this.c++;
/* 604 */           this.pos++;
/* 605 */           action.accept(gotten);
/* 606 */           return true;
/*     */         } 
/* 608 */         this.pos++;
/*     */       } 
/*     */       
/* 611 */       return false;
/*     */     }
/*     */ 
/*     */     
/*     */     public void forEachRemaining(Consumer<? super K> action) {
/* 616 */       if (this.mustReturnNull) {
/* 617 */         this.mustReturnNull = false;
/* 618 */         action.accept(null);
/* 619 */         this.c++;
/*     */       } 
/* 621 */       K[][] key = ObjectOpenHashBigSet.this.key;
/* 622 */       while (this.pos < this.max) {
/* 623 */         K gotten = (K)BigArrays.get((Object[][])key, this.pos);
/* 624 */         if (gotten != null) {
/* 625 */           action.accept(gotten);
/* 626 */           this.c++;
/*     */         } 
/* 628 */         this.pos++;
/*     */       } 
/*     */     }
/*     */ 
/*     */     
/*     */     public int characteristics() {
/* 634 */       return this.hasSplit ? 1 : 65;
/*     */     }
/*     */ 
/*     */     
/*     */     public long estimateSize() {
/* 639 */       if (!this.hasSplit)
/*     */       {
/* 641 */         return ObjectOpenHashBigSet.this.size - this.c;
/*     */       }
/*     */ 
/*     */ 
/*     */       
/* 646 */       return Math.min(ObjectOpenHashBigSet.this.size - this.c, (long)(ObjectOpenHashBigSet.this.realSize() / ObjectOpenHashBigSet.this.n * (this.max - this.pos)) + (this.mustReturnNull ? 1L : 0L));
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     public SetSpliterator trySplit() {
/* 652 */       if (this.pos >= this.max - 1L) return null; 
/* 653 */       long retLen = this.max - this.pos >> 1L;
/* 654 */       if (retLen <= 1L) return null; 
/* 655 */       long myNewPos = this.pos + retLen;
/*     */ 
/*     */       
/* 658 */       myNewPos = BigArrays.nearestSegmentStart(myNewPos, this.pos + 1L, this.max - 1L);
/* 659 */       long retPos = this.pos;
/* 660 */       long retMax = myNewPos;
/*     */ 
/*     */ 
/*     */       
/* 664 */       SetSpliterator split = new SetSpliterator(retPos, retMax, this.mustReturnNull, true);
/* 665 */       this.pos = myNewPos;
/* 666 */       this.mustReturnNull = false;
/* 667 */       this.hasSplit = true;
/* 668 */       return split;
/*     */     }
/*     */ 
/*     */     
/*     */     public long skip(long n) {
/* 673 */       if (n < 0L) throw new IllegalArgumentException("Argument must be nonnegative: " + n); 
/* 674 */       if (n == 0L) return 0L; 
/* 675 */       long skipped = 0L;
/* 676 */       if (this.mustReturnNull) {
/* 677 */         this.mustReturnNull = false;
/* 678 */         skipped++;
/* 679 */         n--;
/*     */       } 
/* 681 */       K[][] key = ObjectOpenHashBigSet.this.key;
/* 682 */       while (this.pos < this.max && n > 0L) {
/* 683 */         if (BigArrays.get((Object[][])key, this.pos++) != null) {
/* 684 */           skipped++;
/* 685 */           n--;
/*     */         } 
/*     */       } 
/* 688 */       return skipped;
/*     */     }
/*     */     
/*     */     SetSpliterator() {} }
/*     */   
/*     */   public ObjectSpliterator<K> spliterator() {
/* 694 */     return new SetSpliterator();
/*     */   }
/*     */ 
/*     */   
/*     */   public void forEach(Consumer<? super K> action) {
/* 699 */     if (this.containsNull) {
/* 700 */       action.accept(null);
/*     */     }
/* 702 */     long pos = 0L;
/* 703 */     long max = this.n;
/* 704 */     K[][] key = this.key;
/* 705 */     while (pos < max) {
/* 706 */       K gotten = (K)BigArrays.get((Object[][])key, pos++);
/* 707 */       if (gotten != null) {
/* 708 */         action.accept(gotten);
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
/* 727 */     return trim(this.size);
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
/* 749 */     long l = HashCommon.bigArraySize(n, this.f);
/* 750 */     if (l >= this.n || this.size > HashCommon.maxFill(l, this.f)) return true; 
/*     */     try {
/* 752 */       rehash(l);
/* 753 */     } catch (OutOfMemoryError cantDoIt) {
/* 754 */       return false;
/*     */     } 
/* 756 */     return true;
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
/* 771 */     K[][] key = this.key;
/* 772 */     K[][] newKey = (K[][])ObjectBigArrays.newBigArray(newN);
/* 773 */     long mask = newN - 1L;
/* 774 */     int newSegmentMask = (newKey[0]).length - 1;
/* 775 */     int newBaseMask = newKey.length - 1;
/* 776 */     int base = 0, displ = 0;
/*     */ 
/*     */     
/* 779 */     for (long i = realSize(); i-- != 0L; ) {
/* 780 */       for (; key[base][displ] == null; base += ((displ = displ + 1 & this.segmentMask) == 0) ? 1 : 0);
/* 781 */       K k = key[base][displ];
/* 782 */       long h = HashCommon.mix(k.hashCode());
/*     */       int b, d;
/* 784 */       if (newKey[b = (int)((h & mask) >>> 27L)][d = (int)(h & newSegmentMask)] != null) while (true) { if (newKey[b = b + (((d = d + 1 & newSegmentMask) == 0) ? 1 : 0) & newBaseMask][d] != null)
/* 785 */             continue;  break; }   newKey[b][d] = k;
/* 786 */       base += ((displ = displ + 1 & this.segmentMask) == 0) ? 1 : 0;
/*     */     } 
/* 788 */     this.n = newN;
/* 789 */     this.key = newKey;
/* 790 */     initMasks();
/* 791 */     this.maxFill = HashCommon.maxFill(this.n, this.f);
/*     */   }
/*     */ 
/*     */   
/*     */   @Deprecated
/*     */   public int size() {
/* 797 */     return (int)Math.min(2147483647L, this.size);
/*     */   }
/*     */ 
/*     */   
/*     */   public long size64() {
/* 802 */     return this.size;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isEmpty() {
/* 807 */     return (this.size == 0L);
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
/*     */   public ObjectOpenHashBigSet<K> clone() {
/*     */     ObjectOpenHashBigSet<K> c;
/*     */     try {
/* 824 */       c = (ObjectOpenHashBigSet<K>)super.clone();
/* 825 */     } catch (CloneNotSupportedException cantHappen) {
/* 826 */       throw new InternalError();
/*     */     } 
/* 828 */     c.key = (K[][])BigArrays.copy((Object[][])this.key);
/* 829 */     c.containsNull = this.containsNull;
/* 830 */     return c;
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
/* 844 */     K[][] key = this.key;
/* 845 */     int h = 0, base = 0, displ = 0;
/* 846 */     for (long j = realSize(); j-- != 0L; ) {
/* 847 */       for (; key[base][displ] == null; base += ((displ = displ + 1 & this.segmentMask) == 0) ? 1 : 0);
/* 848 */       if (this != key[base][displ]) h += key[base][displ].hashCode(); 
/* 849 */       base += ((displ = displ + 1 & this.segmentMask) == 0) ? 1 : 0;
/*     */     } 
/* 851 */     return h;
/*     */   }
/*     */   
/*     */   private void writeObject(ObjectOutputStream s) throws IOException {
/* 855 */     ObjectIterator<K> i = iterator();
/* 856 */     s.defaultWriteObject();
/* 857 */     for (long j = this.size; j-- != 0L; s.writeObject(i.next()));
/*     */   }
/*     */ 
/*     */   
/*     */   private void readObject(ObjectInputStream s) throws IOException, ClassNotFoundException {
/* 862 */     s.defaultReadObject();
/* 863 */     this.n = HashCommon.bigArraySize(this.size, this.f);
/* 864 */     this.maxFill = HashCommon.maxFill(this.n, this.f);
/* 865 */     K[][] key = this.key = (K[][])ObjectBigArrays.newBigArray(this.n);
/* 866 */     initMasks();
/*     */ 
/*     */ 
/*     */     
/* 870 */     for (long i = this.size; i-- != 0L; ) {
/* 871 */       K k = (K)s.readObject();
/* 872 */       if (k == null) { this.containsNull = true; continue; }
/*     */       
/* 874 */       long h = HashCommon.mix(k.hashCode()); int base, displ;
/* 875 */       if (key[base = (int)((h & this.mask) >>> 27L)][displ = (int)(h & this.segmentMask)] != null) while (true) { if (key[base = base + (((displ = displ + 1 & this.segmentMask) == 0) ? 1 : 0) & this.baseMask][displ] != null)
/* 876 */             continue;  break; }   key[base][displ] = k;
/*     */     } 
/*     */   }
/*     */   
/*     */   private void checkTable() {}
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\i\\unimi\dsi\fastutil\objects\ObjectOpenHashBigSet.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */