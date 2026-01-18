/*     */ package it.unimi.dsi.fastutil.doubles;
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
/*     */ import java.util.function.DoubleConsumer;
/*     */ import java.util.function.Supplier;
/*     */ import java.util.stream.DoubleStream;
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
/*     */ public class DoubleOpenHashBigSet
/*     */   extends AbstractDoubleSet
/*     */   implements Serializable, Cloneable, Hash, Size64
/*     */ {
/*     */   private static final long serialVersionUID = 0L;
/*     */   private static final boolean ASSERTS = false;
/*     */   protected transient double[][] key;
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
/*  82 */     this.mask = this.n - 1L;
/*     */ 
/*     */ 
/*     */     
/*  86 */     this.segmentMask = (this.key[0]).length - 1;
/*  87 */     this.baseMask = this.key.length - 1;
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
/*     */   public DoubleOpenHashBigSet(long expected, float f) {
/* 101 */     if (f <= 0.0F || f > 1.0F) throw new IllegalArgumentException("Load factor must be greater than 0 and smaller than or equal to 1"); 
/* 102 */     if (this.n < 0L) throw new IllegalArgumentException("The expected number of elements must be nonnegative"); 
/* 103 */     this.f = f;
/* 104 */     this.minN = this.n = HashCommon.bigArraySize(expected, f);
/* 105 */     this.maxFill = HashCommon.maxFill(this.n, f);
/* 106 */     this.key = DoubleBigArrays.newBigArray(this.n);
/* 107 */     initMasks();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public DoubleOpenHashBigSet(long expected) {
/* 116 */     this(expected, 0.75F);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public DoubleOpenHashBigSet() {
/* 124 */     this(16L, 0.75F);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public DoubleOpenHashBigSet(Collection<? extends Double> c, float f) {
/* 134 */     this(Size64.sizeOf(c), f);
/* 135 */     addAll(c);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public DoubleOpenHashBigSet(Collection<? extends Double> c) {
/* 145 */     this(c, 0.75F);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public DoubleOpenHashBigSet(DoubleCollection c, float f) {
/* 155 */     this(Size64.sizeOf(c), f);
/* 156 */     addAll(c);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public DoubleOpenHashBigSet(DoubleCollection c) {
/* 166 */     this(c, 0.75F);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public DoubleOpenHashBigSet(DoubleIterator i, float f) {
/* 176 */     this(16L, f);
/* 177 */     for (; i.hasNext(); add(i.nextDouble()));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public DoubleOpenHashBigSet(DoubleIterator i) {
/* 187 */     this(i, 0.75F);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public DoubleOpenHashBigSet(Iterator<?> i, float f) {
/* 197 */     this(DoubleIterators.asDoubleIterator(i), f);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public DoubleOpenHashBigSet(Iterator<?> i) {
/* 207 */     this(DoubleIterators.asDoubleIterator(i));
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
/*     */   public DoubleOpenHashBigSet(double[] a, int offset, int length, float f) {
/* 219 */     this((length < 0) ? 0L : length, f);
/* 220 */     DoubleArrays.ensureOffsetLength(a, offset, length);
/* 221 */     for (int i = 0; i < length; ) { add(a[offset + i]); i++; }
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
/*     */   public DoubleOpenHashBigSet(double[] a, int offset, int length) {
/* 233 */     this(a, offset, length, 0.75F);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public DoubleOpenHashBigSet(double[] a, float f) {
/* 243 */     this(a, 0, a.length, f);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public DoubleOpenHashBigSet(double[] a) {
/* 253 */     this(a, 0.75F);
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
/*     */   public static DoubleOpenHashBigSet toBigSet(DoubleStream stream) {
/* 267 */     return stream.<DoubleOpenHashBigSet>collect(DoubleOpenHashBigSet::new, DoubleOpenHashBigSet::add, DoubleOpenHashBigSet::addAll);
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
/*     */   public static DoubleOpenHashBigSet toBigSetWithExpectedSize(DoubleStream stream, long expectedSize) {
/* 281 */     return stream.<DoubleOpenHashBigSet>collect(() -> new DoubleOpenHashBigSet(expectedSize), DoubleOpenHashBigSet::add, DoubleOpenHashBigSet::addAll);
/*     */   }
/*     */   
/*     */   private long realSize() {
/* 285 */     return this.containsNull ? (this.size - 1L) : this.size;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void ensureCapacity(long capacity) {
/* 295 */     long needed = HashCommon.bigArraySize(capacity, this.f);
/* 296 */     if (needed > this.n) rehash(needed);
/*     */   
/*     */   }
/*     */   
/*     */   public boolean addAll(Collection<? extends Double> c) {
/* 301 */     long size = Size64.sizeOf(c);
/*     */     
/* 303 */     if (this.f <= 0.5D) { ensureCapacity(size); }
/* 304 */     else { ensureCapacity(size64() + size); }
/* 305 */      return super.addAll(c);
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean addAll(DoubleCollection c) {
/* 310 */     long size = Size64.sizeOf(c);
/* 311 */     if (this.f <= 0.5D) { ensureCapacity(size); }
/* 312 */     else { ensureCapacity(size64() + size); }
/* 313 */      return super.addAll(c);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean add(double k) {
/* 319 */     if (Double.doubleToLongBits(k) == 0L) {
/* 320 */       if (this.containsNull) return false; 
/* 321 */       this.containsNull = true;
/*     */     } else {
/*     */       
/* 324 */       double[][] key = this.key;
/* 325 */       long h = HashCommon.mix(Double.doubleToRawLongBits(k)); int displ, base;
/*     */       double curr;
/* 327 */       if (Double.doubleToLongBits(curr = key[base = (int)((h & this.mask) >>> 27L)][displ = (int)(h & this.segmentMask)]) != 0L) {
/* 328 */         if (Double.doubleToLongBits(curr) == Double.doubleToLongBits(k)) return false;  while (true) {
/* 329 */           if (Double.doubleToLongBits(curr = key[base = base + (((displ = displ + 1 & this.segmentMask) == 0) ? 1 : 0) & this.baseMask][displ]) != 0L) { if (Double.doubleToLongBits(curr) == Double.doubleToLongBits(k)) return false;  continue; }  break;
/*     */         } 
/* 331 */       }  key[base][displ] = k;
/*     */     } 
/* 333 */     if (this.size++ >= this.maxFill) rehash(2L * this.n);
/*     */     
/* 335 */     return true;
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
/* 347 */     double[][] key = this.key; while (true) {
/*     */       long last;
/* 349 */       pos = (last = pos) + 1L & this.mask;
/*     */       while (true) {
/* 351 */         if (Double.doubleToLongBits(BigArrays.get(key, pos)) == 0L) {
/* 352 */           BigArrays.set(key, last, 0.0D);
/*     */           return;
/*     */         } 
/* 355 */         long slot = HashCommon.mix(Double.doubleToRawLongBits(BigArrays.get(key, pos))) & this.mask;
/* 356 */         if ((last <= pos) ? (last >= slot || slot > pos) : (last >= slot && slot > pos))
/* 357 */           break;  pos = pos + 1L & this.mask;
/*     */       } 
/* 359 */       BigArrays.set(key, last, BigArrays.get(key, pos));
/*     */     } 
/*     */   }
/*     */   
/*     */   private boolean removeEntry(int base, int displ) {
/* 364 */     this.size--;
/* 365 */     shiftKeys(base * 134217728L + displ);
/* 366 */     if (this.n > this.minN && this.size < this.maxFill / 4L && this.n > 16L) rehash(this.n / 2L); 
/* 367 */     return true;
/*     */   }
/*     */   
/*     */   private boolean removeNullEntry() {
/* 371 */     this.containsNull = false;
/* 372 */     this.size--;
/* 373 */     if (this.n > this.minN && this.size < this.maxFill / 4L && this.n > 16L) rehash(this.n / 2L); 
/* 374 */     return true;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean remove(double k) {
/* 379 */     if (Double.doubleToLongBits(k) == 0L) {
/* 380 */       if (this.containsNull) return removeNullEntry(); 
/* 381 */       return false;
/*     */     } 
/*     */     
/* 384 */     double[][] key = this.key;
/* 385 */     long h = HashCommon.mix(Double.doubleToRawLongBits(k));
/*     */     double curr;
/*     */     int displ, base;
/* 388 */     if (Double.doubleToLongBits(curr = key[base = (int)((h & this.mask) >>> 27L)][displ = (int)(h & this.segmentMask)]) == 0L) return false; 
/* 389 */     if (Double.doubleToLongBits(curr) == Double.doubleToLongBits(k)) return removeEntry(base, displ); 
/*     */     while (true) {
/* 391 */       if (Double.doubleToLongBits(curr = key[base = base + (((displ = displ + 1 & this.segmentMask) == 0) ? 1 : 0) & this.baseMask][displ]) == 0L) return false; 
/* 392 */       if (Double.doubleToLongBits(curr) == Double.doubleToLongBits(k)) return removeEntry(base, displ);
/*     */     
/*     */     } 
/*     */   }
/*     */   
/*     */   public boolean contains(double k) {
/* 398 */     if (Double.doubleToLongBits(k) == 0L) return this.containsNull;
/*     */     
/* 400 */     double[][] key = this.key;
/* 401 */     long h = HashCommon.mix(Double.doubleToRawLongBits(k));
/*     */     double curr;
/*     */     int displ, base;
/* 404 */     if (Double.doubleToLongBits(curr = key[base = (int)((h & this.mask) >>> 27L)][displ = (int)(h & this.segmentMask)]) == 0L) return false; 
/* 405 */     if (Double.doubleToLongBits(curr) == Double.doubleToLongBits(k)) return true; 
/*     */     while (true) {
/* 407 */       if (Double.doubleToLongBits(curr = key[base = base + (((displ = displ + 1 & this.segmentMask) == 0) ? 1 : 0) & this.baseMask][displ]) == 0L) return false; 
/* 408 */       if (Double.doubleToLongBits(curr) == Double.doubleToLongBits(k)) return true;
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
/* 424 */     if (this.size == 0L)
/* 425 */       return;  this.size = 0L;
/* 426 */     this.containsNull = false;
/* 427 */     BigArrays.fill(this.key, 0.0D);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private class SetIterator
/*     */     implements DoubleIterator
/*     */   {
/* 437 */     int base = DoubleOpenHashBigSet.this.key.length;
/*     */ 
/*     */ 
/*     */     
/*     */     int displ;
/*     */ 
/*     */ 
/*     */     
/* 445 */     long last = -1L;
/*     */     
/* 447 */     long c = DoubleOpenHashBigSet.this.size;
/*     */     
/* 449 */     boolean mustReturnNull = DoubleOpenHashBigSet.this.containsNull;
/*     */ 
/*     */ 
/*     */     
/*     */     DoubleArrayList wrapped;
/*     */ 
/*     */ 
/*     */     
/*     */     public boolean hasNext() {
/* 458 */       return (this.c != 0L);
/*     */     }
/*     */ 
/*     */     
/*     */     public double nextDouble() {
/* 463 */       if (!hasNext()) throw new NoSuchElementException(); 
/* 464 */       this.c--;
/* 465 */       if (this.mustReturnNull) {
/* 466 */         this.mustReturnNull = false;
/* 467 */         this.last = DoubleOpenHashBigSet.this.n;
/* 468 */         return 0.0D;
/*     */       } 
/* 470 */       double[][] key = DoubleOpenHashBigSet.this.key;
/*     */       while (true) {
/* 472 */         if (this.displ == 0 && this.base <= 0) {
/*     */           
/* 474 */           this.last = Long.MIN_VALUE;
/* 475 */           return this.wrapped.getDouble(---this.base - 1);
/*     */         } 
/* 477 */         if (this.displ-- == 0) this.displ = (key[--this.base]).length - 1; 
/* 478 */         double k = key[this.base][this.displ];
/* 479 */         if (Double.doubleToLongBits(k) != 0L) {
/* 480 */           this.last = this.base * 134217728L + this.displ;
/* 481 */           return k;
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
/* 496 */       double[][] key = DoubleOpenHashBigSet.this.key; while (true) {
/*     */         double curr; long last;
/* 498 */         pos = (last = pos) + 1L & DoubleOpenHashBigSet.this.mask;
/*     */         while (true) {
/* 500 */           if (Double.doubleToLongBits(curr = BigArrays.get(key, pos)) == 0L) {
/* 501 */             BigArrays.set(key, last, 0.0D);
/*     */             return;
/*     */           } 
/* 504 */           long slot = HashCommon.mix(Double.doubleToRawLongBits(curr)) & DoubleOpenHashBigSet.this.mask;
/* 505 */           if ((last <= pos) ? (last >= slot || slot > pos) : (last >= slot && slot > pos))
/* 506 */             break;  pos = pos + 1L & DoubleOpenHashBigSet.this.mask;
/*     */         } 
/* 508 */         if (pos < last) {
/* 509 */           if (this.wrapped == null) this.wrapped = new DoubleArrayList(); 
/* 510 */           this.wrapped.add(BigArrays.get(key, pos));
/*     */         } 
/* 512 */         BigArrays.set(key, last, curr);
/*     */       } 
/*     */     }
/*     */ 
/*     */     
/*     */     public void remove() {
/* 518 */       if (this.last == -1L) throw new IllegalStateException(); 
/* 519 */       if (this.last == DoubleOpenHashBigSet.this.n) { DoubleOpenHashBigSet.this.containsNull = false; }
/* 520 */       else if (this.base >= 0) { shiftKeys(this.last); }
/*     */       else
/*     */       
/* 523 */       { DoubleOpenHashBigSet.this.remove(this.wrapped.getDouble(-this.base - 1));
/* 524 */         this.last = -1L;
/*     */         return; }
/*     */       
/* 527 */       DoubleOpenHashBigSet.this.size--;
/* 528 */       this.last = -1L;
/*     */     }
/*     */     
/*     */     private SetIterator() {}
/*     */   }
/*     */   
/*     */   public DoubleIterator iterator() {
/* 535 */     return new SetIterator();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private class SetSpliterator
/*     */     implements DoubleSpliterator
/*     */   {
/*     */     private static final int POST_SPLIT_CHARACTERISTICS = 257;
/*     */ 
/*     */ 
/*     */     
/* 549 */     long pos = 0L;
/*     */     
/* 551 */     long max = DoubleOpenHashBigSet.this.n;
/*     */     
/* 553 */     long c = 0L;
/*     */     
/* 555 */     boolean mustReturnNull = DoubleOpenHashBigSet.this.containsNull;
/*     */ 
/*     */     
/*     */     boolean hasSplit = false;
/*     */ 
/*     */     
/*     */     SetSpliterator(long pos, long max, boolean mustReturnNull, boolean hasSplit) {
/* 562 */       this.pos = pos;
/* 563 */       this.max = max;
/* 564 */       this.mustReturnNull = mustReturnNull;
/* 565 */       this.hasSplit = hasSplit;
/*     */     }
/*     */ 
/*     */     
/*     */     public boolean tryAdvance(DoubleConsumer action) {
/* 570 */       if (this.mustReturnNull) {
/* 571 */         this.mustReturnNull = false;
/* 572 */         this.c++;
/* 573 */         action.accept(0.0D);
/* 574 */         return true;
/*     */       } 
/* 576 */       double[][] key = DoubleOpenHashBigSet.this.key;
/* 577 */       while (this.pos < this.max) {
/* 578 */         double gotten = BigArrays.get(key, this.pos);
/* 579 */         if (Double.doubleToLongBits(gotten) != 0L) {
/* 580 */           this.c++;
/* 581 */           this.pos++;
/* 582 */           action.accept(gotten);
/* 583 */           return true;
/*     */         } 
/* 585 */         this.pos++;
/*     */       } 
/*     */       
/* 588 */       return false;
/*     */     }
/*     */ 
/*     */     
/*     */     public void forEachRemaining(DoubleConsumer action) {
/* 593 */       if (this.mustReturnNull) {
/* 594 */         this.mustReturnNull = false;
/* 595 */         action.accept(0.0D);
/* 596 */         this.c++;
/*     */       } 
/* 598 */       double[][] key = DoubleOpenHashBigSet.this.key;
/* 599 */       while (this.pos < this.max) {
/* 600 */         double gotten = BigArrays.get(key, this.pos);
/* 601 */         if (Double.doubleToLongBits(gotten) != 0L) {
/* 602 */           action.accept(gotten);
/* 603 */           this.c++;
/*     */         } 
/* 605 */         this.pos++;
/*     */       } 
/*     */     }
/*     */ 
/*     */     
/*     */     public int characteristics() {
/* 611 */       return this.hasSplit ? 257 : 321;
/*     */     }
/*     */ 
/*     */     
/*     */     public long estimateSize() {
/* 616 */       if (!this.hasSplit)
/*     */       {
/* 618 */         return DoubleOpenHashBigSet.this.size - this.c;
/*     */       }
/*     */ 
/*     */ 
/*     */       
/* 623 */       return Math.min(DoubleOpenHashBigSet.this.size - this.c, (long)(DoubleOpenHashBigSet.this.realSize() / DoubleOpenHashBigSet.this.n * (this.max - this.pos)) + (this.mustReturnNull ? 1L : 0L));
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     public SetSpliterator trySplit() {
/* 629 */       if (this.pos >= this.max - 1L) return null; 
/* 630 */       long retLen = this.max - this.pos >> 1L;
/* 631 */       if (retLen <= 1L) return null; 
/* 632 */       long myNewPos = this.pos + retLen;
/*     */ 
/*     */       
/* 635 */       myNewPos = BigArrays.nearestSegmentStart(myNewPos, this.pos + 1L, this.max - 1L);
/* 636 */       long retPos = this.pos;
/* 637 */       long retMax = myNewPos;
/*     */ 
/*     */ 
/*     */       
/* 641 */       SetSpliterator split = new SetSpliterator(retPos, retMax, this.mustReturnNull, true);
/* 642 */       this.pos = myNewPos;
/* 643 */       this.mustReturnNull = false;
/* 644 */       this.hasSplit = true;
/* 645 */       return split;
/*     */     }
/*     */ 
/*     */     
/*     */     public long skip(long n) {
/* 650 */       if (n < 0L) throw new IllegalArgumentException("Argument must be nonnegative: " + n); 
/* 651 */       if (n == 0L) return 0L; 
/* 652 */       long skipped = 0L;
/* 653 */       if (this.mustReturnNull) {
/* 654 */         this.mustReturnNull = false;
/* 655 */         skipped++;
/* 656 */         n--;
/*     */       } 
/* 658 */       double[][] key = DoubleOpenHashBigSet.this.key;
/* 659 */       while (this.pos < this.max && n > 0L) {
/* 660 */         if (Double.doubleToLongBits(BigArrays.get(key, this.pos++)) != 0L) {
/* 661 */           skipped++;
/* 662 */           n--;
/*     */         } 
/*     */       } 
/* 665 */       return skipped;
/*     */     }
/*     */     
/*     */     SetSpliterator() {} }
/*     */   
/*     */   public DoubleSpliterator spliterator() {
/* 671 */     return new SetSpliterator();
/*     */   }
/*     */ 
/*     */   
/*     */   public void forEach(DoubleConsumer action) {
/* 676 */     if (this.containsNull) {
/* 677 */       action.accept(0.0D);
/*     */     }
/* 679 */     long pos = 0L;
/* 680 */     long max = this.n;
/* 681 */     double[][] key = this.key;
/* 682 */     while (pos < max) {
/* 683 */       double gotten = BigArrays.get(key, pos++);
/* 684 */       if (Double.doubleToLongBits(gotten) != 0L) {
/* 685 */         action.accept(gotten);
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
/* 704 */     return trim(this.size);
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
/* 726 */     long l = HashCommon.bigArraySize(n, this.f);
/* 727 */     if (l >= this.n || this.size > HashCommon.maxFill(l, this.f)) return true; 
/*     */     try {
/* 729 */       rehash(l);
/* 730 */     } catch (OutOfMemoryError cantDoIt) {
/* 731 */       return false;
/*     */     } 
/* 733 */     return true;
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
/* 748 */     double[][] key = this.key;
/* 749 */     double[][] newKey = DoubleBigArrays.newBigArray(newN);
/* 750 */     long mask = newN - 1L;
/* 751 */     int newSegmentMask = (newKey[0]).length - 1;
/* 752 */     int newBaseMask = newKey.length - 1;
/* 753 */     int base = 0, displ = 0;
/*     */ 
/*     */     
/* 756 */     for (long i = realSize(); i-- != 0L; ) {
/* 757 */       for (; Double.doubleToLongBits(key[base][displ]) == 0L; base += ((displ = displ + 1 & this.segmentMask) == 0) ? 1 : 0);
/* 758 */       double k = key[base][displ];
/* 759 */       long h = HashCommon.mix(Double.doubleToRawLongBits(k));
/*     */       int b, d;
/* 761 */       if (Double.doubleToLongBits(newKey[b = (int)((h & mask) >>> 27L)][d = (int)(h & newSegmentMask)]) != 0L) while (true) { if (Double.doubleToLongBits(newKey[b = b + (((d = d + 1 & newSegmentMask) == 0) ? 1 : 0) & newBaseMask][d]) != 0L)
/* 762 */             continue;  break; }   newKey[b][d] = k;
/* 763 */       base += ((displ = displ + 1 & this.segmentMask) == 0) ? 1 : 0;
/*     */     } 
/* 765 */     this.n = newN;
/* 766 */     this.key = newKey;
/* 767 */     initMasks();
/* 768 */     this.maxFill = HashCommon.maxFill(this.n, this.f);
/*     */   }
/*     */ 
/*     */   
/*     */   @Deprecated
/*     */   public int size() {
/* 774 */     return (int)Math.min(2147483647L, this.size);
/*     */   }
/*     */ 
/*     */   
/*     */   public long size64() {
/* 779 */     return this.size;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isEmpty() {
/* 784 */     return (this.size == 0L);
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
/*     */   public DoubleOpenHashBigSet clone() {
/*     */     DoubleOpenHashBigSet c;
/*     */     try {
/* 801 */       c = (DoubleOpenHashBigSet)super.clone();
/* 802 */     } catch (CloneNotSupportedException cantHappen) {
/* 803 */       throw new InternalError();
/*     */     } 
/* 805 */     c.key = BigArrays.copy(this.key);
/* 806 */     c.containsNull = this.containsNull;
/* 807 */     return c;
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
/* 821 */     double[][] key = this.key;
/* 822 */     int h = 0, base = 0, displ = 0;
/* 823 */     for (long j = realSize(); j-- != 0L; ) {
/* 824 */       for (; Double.doubleToLongBits(key[base][displ]) == 0L; base += ((displ = displ + 1 & this.segmentMask) == 0) ? 1 : 0);
/* 825 */       h += HashCommon.double2int(key[base][displ]);
/* 826 */       base += ((displ = displ + 1 & this.segmentMask) == 0) ? 1 : 0;
/*     */     } 
/* 828 */     return h;
/*     */   }
/*     */   
/*     */   private void writeObject(ObjectOutputStream s) throws IOException {
/* 832 */     DoubleIterator i = iterator();
/* 833 */     s.defaultWriteObject();
/* 834 */     for (long j = this.size; j-- != 0L; s.writeDouble(i.nextDouble()));
/*     */   }
/*     */   
/*     */   private void readObject(ObjectInputStream s) throws IOException, ClassNotFoundException {
/* 838 */     s.defaultReadObject();
/* 839 */     this.n = HashCommon.bigArraySize(this.size, this.f);
/* 840 */     this.maxFill = HashCommon.maxFill(this.n, this.f);
/* 841 */     double[][] key = this.key = DoubleBigArrays.newBigArray(this.n);
/* 842 */     initMasks();
/*     */ 
/*     */ 
/*     */     
/* 846 */     for (long i = this.size; i-- != 0L; ) {
/* 847 */       double k = s.readDouble();
/* 848 */       if (Double.doubleToLongBits(k) == 0L) { this.containsNull = true; continue; }
/*     */       
/* 850 */       long h = HashCommon.mix(Double.doubleToRawLongBits(k)); int base, displ;
/* 851 */       if (Double.doubleToLongBits(key[base = (int)((h & this.mask) >>> 27L)][displ = (int)(h & this.segmentMask)]) != 0L) while (true) { if (Double.doubleToLongBits(key[base = base + (((displ = displ + 1 & this.segmentMask) == 0) ? 1 : 0) & this.baseMask][displ]) != 0L)
/* 852 */             continue;  break; }   key[base][displ] = k;
/*     */     } 
/*     */   }
/*     */   
/*     */   private void checkTable() {}
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\i\\unimi\dsi\fastutil\doubles\DoubleOpenHashBigSet.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */