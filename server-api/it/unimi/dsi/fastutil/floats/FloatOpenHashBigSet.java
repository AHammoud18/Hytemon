/*     */ package it.unimi.dsi.fastutil.floats;
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
/*     */ 
/*     */ public class FloatOpenHashBigSet
/*     */   extends AbstractFloatSet
/*     */   implements Serializable, Cloneable, Hash, Size64
/*     */ {
/*     */   private static final long serialVersionUID = 0L;
/*     */   private static final boolean ASSERTS = false;
/*     */   protected transient float[][] key;
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
/*     */   public FloatOpenHashBigSet(long expected, float f) {
/* 101 */     if (f <= 0.0F || f > 1.0F) throw new IllegalArgumentException("Load factor must be greater than 0 and smaller than or equal to 1"); 
/* 102 */     if (this.n < 0L) throw new IllegalArgumentException("The expected number of elements must be nonnegative"); 
/* 103 */     this.f = f;
/* 104 */     this.minN = this.n = HashCommon.bigArraySize(expected, f);
/* 105 */     this.maxFill = HashCommon.maxFill(this.n, f);
/* 106 */     this.key = FloatBigArrays.newBigArray(this.n);
/* 107 */     initMasks();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public FloatOpenHashBigSet(long expected) {
/* 116 */     this(expected, 0.75F);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public FloatOpenHashBigSet() {
/* 124 */     this(16L, 0.75F);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public FloatOpenHashBigSet(Collection<? extends Float> c, float f) {
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
/*     */   public FloatOpenHashBigSet(Collection<? extends Float> c) {
/* 145 */     this(c, 0.75F);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public FloatOpenHashBigSet(FloatCollection c, float f) {
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
/*     */   public FloatOpenHashBigSet(FloatCollection c) {
/* 166 */     this(c, 0.75F);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public FloatOpenHashBigSet(FloatIterator i, float f) {
/* 176 */     this(16L, f);
/* 177 */     for (; i.hasNext(); add(i.nextFloat()));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public FloatOpenHashBigSet(FloatIterator i) {
/* 187 */     this(i, 0.75F);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public FloatOpenHashBigSet(Iterator<?> i, float f) {
/* 197 */     this(FloatIterators.asFloatIterator(i), f);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public FloatOpenHashBigSet(Iterator<?> i) {
/* 207 */     this(FloatIterators.asFloatIterator(i));
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
/*     */   public FloatOpenHashBigSet(float[] a, int offset, int length, float f) {
/* 219 */     this((length < 0) ? 0L : length, f);
/* 220 */     FloatArrays.ensureOffsetLength(a, offset, length);
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
/*     */   public FloatOpenHashBigSet(float[] a, int offset, int length) {
/* 233 */     this(a, offset, length, 0.75F);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public FloatOpenHashBigSet(float[] a, float f) {
/* 243 */     this(a, 0, a.length, f);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public FloatOpenHashBigSet(float[] a) {
/* 253 */     this(a, 0.75F);
/*     */   }
/*     */   
/*     */   private long realSize() {
/* 257 */     return this.containsNull ? (this.size - 1L) : this.size;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void ensureCapacity(long capacity) {
/* 267 */     long needed = HashCommon.bigArraySize(capacity, this.f);
/* 268 */     if (needed > this.n) rehash(needed);
/*     */   
/*     */   }
/*     */   
/*     */   public boolean addAll(Collection<? extends Float> c) {
/* 273 */     long size = Size64.sizeOf(c);
/*     */     
/* 275 */     if (this.f <= 0.5D) { ensureCapacity(size); }
/* 276 */     else { ensureCapacity(size64() + size); }
/* 277 */      return super.addAll(c);
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean addAll(FloatCollection c) {
/* 282 */     long size = Size64.sizeOf(c);
/* 283 */     if (this.f <= 0.5D) { ensureCapacity(size); }
/* 284 */     else { ensureCapacity(size64() + size); }
/* 285 */      return super.addAll(c);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean add(float k) {
/* 291 */     if (Float.floatToIntBits(k) == 0) {
/* 292 */       if (this.containsNull) return false; 
/* 293 */       this.containsNull = true;
/*     */     } else {
/*     */       
/* 296 */       float[][] key = this.key;
/* 297 */       long h = HashCommon.mix(HashCommon.float2int(k)); int displ, base;
/*     */       float curr;
/* 299 */       if (Float.floatToIntBits(curr = key[base = (int)((h & this.mask) >>> 27L)][displ = (int)(h & this.segmentMask)]) != 0) {
/* 300 */         if (Float.floatToIntBits(curr) == Float.floatToIntBits(k)) return false;  while (true) {
/* 301 */           if (Float.floatToIntBits(curr = key[base = base + (((displ = displ + 1 & this.segmentMask) == 0) ? 1 : 0) & this.baseMask][displ]) != 0) { if (Float.floatToIntBits(curr) == Float.floatToIntBits(k)) return false;  continue; }  break;
/*     */         } 
/* 303 */       }  key[base][displ] = k;
/*     */     } 
/* 305 */     if (this.size++ >= this.maxFill) rehash(2L * this.n);
/*     */     
/* 307 */     return true;
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
/* 319 */     float[][] key = this.key; while (true) {
/*     */       long last;
/* 321 */       pos = (last = pos) + 1L & this.mask;
/*     */       while (true) {
/* 323 */         if (Float.floatToIntBits(BigArrays.get(key, pos)) == 0) {
/* 324 */           BigArrays.set(key, last, 0.0F);
/*     */           return;
/*     */         } 
/* 327 */         long slot = HashCommon.mix(HashCommon.float2int(BigArrays.get(key, pos))) & this.mask;
/* 328 */         if ((last <= pos) ? (last >= slot || slot > pos) : (last >= slot && slot > pos))
/* 329 */           break;  pos = pos + 1L & this.mask;
/*     */       } 
/* 331 */       BigArrays.set(key, last, BigArrays.get(key, pos));
/*     */     } 
/*     */   }
/*     */   
/*     */   private boolean removeEntry(int base, int displ) {
/* 336 */     this.size--;
/* 337 */     shiftKeys(base * 134217728L + displ);
/* 338 */     if (this.n > this.minN && this.size < this.maxFill / 4L && this.n > 16L) rehash(this.n / 2L); 
/* 339 */     return true;
/*     */   }
/*     */   
/*     */   private boolean removeNullEntry() {
/* 343 */     this.containsNull = false;
/* 344 */     this.size--;
/* 345 */     if (this.n > this.minN && this.size < this.maxFill / 4L && this.n > 16L) rehash(this.n / 2L); 
/* 346 */     return true;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean remove(float k) {
/* 351 */     if (Float.floatToIntBits(k) == 0) {
/* 352 */       if (this.containsNull) return removeNullEntry(); 
/* 353 */       return false;
/*     */     } 
/*     */     
/* 356 */     float[][] key = this.key;
/* 357 */     long h = HashCommon.mix(HashCommon.float2int(k));
/*     */     float curr;
/*     */     int displ, base;
/* 360 */     if (Float.floatToIntBits(curr = key[base = (int)((h & this.mask) >>> 27L)][displ = (int)(h & this.segmentMask)]) == 0) return false; 
/* 361 */     if (Float.floatToIntBits(curr) == Float.floatToIntBits(k)) return removeEntry(base, displ); 
/*     */     while (true) {
/* 363 */       if (Float.floatToIntBits(curr = key[base = base + (((displ = displ + 1 & this.segmentMask) == 0) ? 1 : 0) & this.baseMask][displ]) == 0) return false; 
/* 364 */       if (Float.floatToIntBits(curr) == Float.floatToIntBits(k)) return removeEntry(base, displ);
/*     */     
/*     */     } 
/*     */   }
/*     */   
/*     */   public boolean contains(float k) {
/* 370 */     if (Float.floatToIntBits(k) == 0) return this.containsNull;
/*     */     
/* 372 */     float[][] key = this.key;
/* 373 */     long h = HashCommon.mix(HashCommon.float2int(k));
/*     */     float curr;
/*     */     int displ, base;
/* 376 */     if (Float.floatToIntBits(curr = key[base = (int)((h & this.mask) >>> 27L)][displ = (int)(h & this.segmentMask)]) == 0) return false; 
/* 377 */     if (Float.floatToIntBits(curr) == Float.floatToIntBits(k)) return true; 
/*     */     while (true) {
/* 379 */       if (Float.floatToIntBits(curr = key[base = base + (((displ = displ + 1 & this.segmentMask) == 0) ? 1 : 0) & this.baseMask][displ]) == 0) return false; 
/* 380 */       if (Float.floatToIntBits(curr) == Float.floatToIntBits(k)) return true;
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
/* 396 */     if (this.size == 0L)
/* 397 */       return;  this.size = 0L;
/* 398 */     this.containsNull = false;
/* 399 */     BigArrays.fill(this.key, 0.0F);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private class SetIterator
/*     */     implements FloatIterator
/*     */   {
/* 409 */     int base = FloatOpenHashBigSet.this.key.length;
/*     */ 
/*     */ 
/*     */     
/*     */     int displ;
/*     */ 
/*     */ 
/*     */     
/* 417 */     long last = -1L;
/*     */     
/* 419 */     long c = FloatOpenHashBigSet.this.size;
/*     */     
/* 421 */     boolean mustReturnNull = FloatOpenHashBigSet.this.containsNull;
/*     */ 
/*     */ 
/*     */     
/*     */     FloatArrayList wrapped;
/*     */ 
/*     */ 
/*     */     
/*     */     public boolean hasNext() {
/* 430 */       return (this.c != 0L);
/*     */     }
/*     */ 
/*     */     
/*     */     public float nextFloat() {
/* 435 */       if (!hasNext()) throw new NoSuchElementException(); 
/* 436 */       this.c--;
/* 437 */       if (this.mustReturnNull) {
/* 438 */         this.mustReturnNull = false;
/* 439 */         this.last = FloatOpenHashBigSet.this.n;
/* 440 */         return 0.0F;
/*     */       } 
/* 442 */       float[][] key = FloatOpenHashBigSet.this.key;
/*     */       while (true) {
/* 444 */         if (this.displ == 0 && this.base <= 0) {
/*     */           
/* 446 */           this.last = Long.MIN_VALUE;
/* 447 */           return this.wrapped.getFloat(---this.base - 1);
/*     */         } 
/* 449 */         if (this.displ-- == 0) this.displ = (key[--this.base]).length - 1; 
/* 450 */         float k = key[this.base][this.displ];
/* 451 */         if (Float.floatToIntBits(k) != 0) {
/* 452 */           this.last = this.base * 134217728L + this.displ;
/* 453 */           return k;
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
/* 468 */       float[][] key = FloatOpenHashBigSet.this.key; while (true) {
/*     */         float curr; long last;
/* 470 */         pos = (last = pos) + 1L & FloatOpenHashBigSet.this.mask;
/*     */         while (true) {
/* 472 */           if (Float.floatToIntBits(curr = BigArrays.get(key, pos)) == 0) {
/* 473 */             BigArrays.set(key, last, 0.0F);
/*     */             return;
/*     */           } 
/* 476 */           long slot = HashCommon.mix(HashCommon.float2int(curr)) & FloatOpenHashBigSet.this.mask;
/* 477 */           if ((last <= pos) ? (last >= slot || slot > pos) : (last >= slot && slot > pos))
/* 478 */             break;  pos = pos + 1L & FloatOpenHashBigSet.this.mask;
/*     */         } 
/* 480 */         if (pos < last) {
/* 481 */           if (this.wrapped == null) this.wrapped = new FloatArrayList(); 
/* 482 */           this.wrapped.add(BigArrays.get(key, pos));
/*     */         } 
/* 484 */         BigArrays.set(key, last, curr);
/*     */       } 
/*     */     }
/*     */ 
/*     */     
/*     */     public void remove() {
/* 490 */       if (this.last == -1L) throw new IllegalStateException(); 
/* 491 */       if (this.last == FloatOpenHashBigSet.this.n) { FloatOpenHashBigSet.this.containsNull = false; }
/* 492 */       else if (this.base >= 0) { shiftKeys(this.last); }
/*     */       else
/*     */       
/* 495 */       { FloatOpenHashBigSet.this.remove(this.wrapped.getFloat(-this.base - 1));
/* 496 */         this.last = -1L;
/*     */         return; }
/*     */       
/* 499 */       FloatOpenHashBigSet.this.size--;
/* 500 */       this.last = -1L;
/*     */     }
/*     */     
/*     */     private SetIterator() {}
/*     */   }
/*     */   
/*     */   public FloatIterator iterator() {
/* 507 */     return new SetIterator();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private class SetSpliterator
/*     */     implements FloatSpliterator
/*     */   {
/*     */     private static final int POST_SPLIT_CHARACTERISTICS = 257;
/*     */ 
/*     */ 
/*     */     
/* 521 */     long pos = 0L;
/*     */     
/* 523 */     long max = FloatOpenHashBigSet.this.n;
/*     */     
/* 525 */     long c = 0L;
/*     */     
/* 527 */     boolean mustReturnNull = FloatOpenHashBigSet.this.containsNull;
/*     */ 
/*     */     
/*     */     boolean hasSplit = false;
/*     */ 
/*     */     
/*     */     SetSpliterator(long pos, long max, boolean mustReturnNull, boolean hasSplit) {
/* 534 */       this.pos = pos;
/* 535 */       this.max = max;
/* 536 */       this.mustReturnNull = mustReturnNull;
/* 537 */       this.hasSplit = hasSplit;
/*     */     }
/*     */ 
/*     */     
/*     */     public boolean tryAdvance(FloatConsumer action) {
/* 542 */       if (this.mustReturnNull) {
/* 543 */         this.mustReturnNull = false;
/* 544 */         this.c++;
/* 545 */         action.accept(0.0F);
/* 546 */         return true;
/*     */       } 
/* 548 */       float[][] key = FloatOpenHashBigSet.this.key;
/* 549 */       while (this.pos < this.max) {
/* 550 */         float gotten = BigArrays.get(key, this.pos);
/* 551 */         if (Float.floatToIntBits(gotten) != 0) {
/* 552 */           this.c++;
/* 553 */           this.pos++;
/* 554 */           action.accept(gotten);
/* 555 */           return true;
/*     */         } 
/* 557 */         this.pos++;
/*     */       } 
/*     */       
/* 560 */       return false;
/*     */     }
/*     */ 
/*     */     
/*     */     public void forEachRemaining(FloatConsumer action) {
/* 565 */       if (this.mustReturnNull) {
/* 566 */         this.mustReturnNull = false;
/* 567 */         action.accept(0.0F);
/* 568 */         this.c++;
/*     */       } 
/* 570 */       float[][] key = FloatOpenHashBigSet.this.key;
/* 571 */       while (this.pos < this.max) {
/* 572 */         float gotten = BigArrays.get(key, this.pos);
/* 573 */         if (Float.floatToIntBits(gotten) != 0) {
/* 574 */           action.accept(gotten);
/* 575 */           this.c++;
/*     */         } 
/* 577 */         this.pos++;
/*     */       } 
/*     */     }
/*     */ 
/*     */     
/*     */     public int characteristics() {
/* 583 */       return this.hasSplit ? 257 : 321;
/*     */     }
/*     */ 
/*     */     
/*     */     public long estimateSize() {
/* 588 */       if (!this.hasSplit)
/*     */       {
/* 590 */         return FloatOpenHashBigSet.this.size - this.c;
/*     */       }
/*     */ 
/*     */ 
/*     */       
/* 595 */       return Math.min(FloatOpenHashBigSet.this.size - this.c, (long)(FloatOpenHashBigSet.this.realSize() / FloatOpenHashBigSet.this.n * (this.max - this.pos)) + (this.mustReturnNull ? 1L : 0L));
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     public SetSpliterator trySplit() {
/* 601 */       if (this.pos >= this.max - 1L) return null; 
/* 602 */       long retLen = this.max - this.pos >> 1L;
/* 603 */       if (retLen <= 1L) return null; 
/* 604 */       long myNewPos = this.pos + retLen;
/*     */ 
/*     */       
/* 607 */       myNewPos = BigArrays.nearestSegmentStart(myNewPos, this.pos + 1L, this.max - 1L);
/* 608 */       long retPos = this.pos;
/* 609 */       long retMax = myNewPos;
/*     */ 
/*     */ 
/*     */       
/* 613 */       SetSpliterator split = new SetSpliterator(retPos, retMax, this.mustReturnNull, true);
/* 614 */       this.pos = myNewPos;
/* 615 */       this.mustReturnNull = false;
/* 616 */       this.hasSplit = true;
/* 617 */       return split;
/*     */     }
/*     */ 
/*     */     
/*     */     public long skip(long n) {
/* 622 */       if (n < 0L) throw new IllegalArgumentException("Argument must be nonnegative: " + n); 
/* 623 */       if (n == 0L) return 0L; 
/* 624 */       long skipped = 0L;
/* 625 */       if (this.mustReturnNull) {
/* 626 */         this.mustReturnNull = false;
/* 627 */         skipped++;
/* 628 */         n--;
/*     */       } 
/* 630 */       float[][] key = FloatOpenHashBigSet.this.key;
/* 631 */       while (this.pos < this.max && n > 0L) {
/* 632 */         if (Float.floatToIntBits(BigArrays.get(key, this.pos++)) != 0) {
/* 633 */           skipped++;
/* 634 */           n--;
/*     */         } 
/*     */       } 
/* 637 */       return skipped;
/*     */     }
/*     */     
/*     */     SetSpliterator() {} }
/*     */   
/*     */   public FloatSpliterator spliterator() {
/* 643 */     return new SetSpliterator();
/*     */   }
/*     */ 
/*     */   
/*     */   public void forEach(FloatConsumer action) {
/* 648 */     if (this.containsNull) {
/* 649 */       action.accept(0.0F);
/*     */     }
/* 651 */     long pos = 0L;
/* 652 */     long max = this.n;
/* 653 */     float[][] key = this.key;
/* 654 */     while (pos < max) {
/* 655 */       float gotten = BigArrays.get(key, pos++);
/* 656 */       if (Float.floatToIntBits(gotten) != 0) {
/* 657 */         action.accept(gotten);
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
/* 676 */     return trim(this.size);
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
/* 698 */     long l = HashCommon.bigArraySize(n, this.f);
/* 699 */     if (l >= this.n || this.size > HashCommon.maxFill(l, this.f)) return true; 
/*     */     try {
/* 701 */       rehash(l);
/* 702 */     } catch (OutOfMemoryError cantDoIt) {
/* 703 */       return false;
/*     */     } 
/* 705 */     return true;
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
/* 720 */     float[][] key = this.key;
/* 721 */     float[][] newKey = FloatBigArrays.newBigArray(newN);
/* 722 */     long mask = newN - 1L;
/* 723 */     int newSegmentMask = (newKey[0]).length - 1;
/* 724 */     int newBaseMask = newKey.length - 1;
/* 725 */     int base = 0, displ = 0;
/*     */ 
/*     */     
/* 728 */     for (long i = realSize(); i-- != 0L; ) {
/* 729 */       for (; Float.floatToIntBits(key[base][displ]) == 0; base += ((displ = displ + 1 & this.segmentMask) == 0) ? 1 : 0);
/* 730 */       float k = key[base][displ];
/* 731 */       long h = HashCommon.mix(HashCommon.float2int(k));
/*     */       int b, d;
/* 733 */       if (Float.floatToIntBits(newKey[b = (int)((h & mask) >>> 27L)][d = (int)(h & newSegmentMask)]) != 0) while (true) { if (Float.floatToIntBits(newKey[b = b + (((d = d + 1 & newSegmentMask) == 0) ? 1 : 0) & newBaseMask][d]) != 0)
/* 734 */             continue;  break; }   newKey[b][d] = k;
/* 735 */       base += ((displ = displ + 1 & this.segmentMask) == 0) ? 1 : 0;
/*     */     } 
/* 737 */     this.n = newN;
/* 738 */     this.key = newKey;
/* 739 */     initMasks();
/* 740 */     this.maxFill = HashCommon.maxFill(this.n, this.f);
/*     */   }
/*     */ 
/*     */   
/*     */   @Deprecated
/*     */   public int size() {
/* 746 */     return (int)Math.min(2147483647L, this.size);
/*     */   }
/*     */ 
/*     */   
/*     */   public long size64() {
/* 751 */     return this.size;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isEmpty() {
/* 756 */     return (this.size == 0L);
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
/*     */   public FloatOpenHashBigSet clone() {
/*     */     FloatOpenHashBigSet c;
/*     */     try {
/* 773 */       c = (FloatOpenHashBigSet)super.clone();
/* 774 */     } catch (CloneNotSupportedException cantHappen) {
/* 775 */       throw new InternalError();
/*     */     } 
/* 777 */     c.key = BigArrays.copy(this.key);
/* 778 */     c.containsNull = this.containsNull;
/* 779 */     return c;
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
/* 793 */     float[][] key = this.key;
/* 794 */     int h = 0, base = 0, displ = 0;
/* 795 */     for (long j = realSize(); j-- != 0L; ) {
/* 796 */       for (; Float.floatToIntBits(key[base][displ]) == 0; base += ((displ = displ + 1 & this.segmentMask) == 0) ? 1 : 0);
/* 797 */       h += HashCommon.float2int(key[base][displ]);
/* 798 */       base += ((displ = displ + 1 & this.segmentMask) == 0) ? 1 : 0;
/*     */     } 
/* 800 */     return h;
/*     */   }
/*     */   
/*     */   private void writeObject(ObjectOutputStream s) throws IOException {
/* 804 */     FloatIterator i = iterator();
/* 805 */     s.defaultWriteObject();
/* 806 */     for (long j = this.size; j-- != 0L; s.writeFloat(i.nextFloat()));
/*     */   }
/*     */   
/*     */   private void readObject(ObjectInputStream s) throws IOException, ClassNotFoundException {
/* 810 */     s.defaultReadObject();
/* 811 */     this.n = HashCommon.bigArraySize(this.size, this.f);
/* 812 */     this.maxFill = HashCommon.maxFill(this.n, this.f);
/* 813 */     float[][] key = this.key = FloatBigArrays.newBigArray(this.n);
/* 814 */     initMasks();
/*     */ 
/*     */ 
/*     */     
/* 818 */     for (long i = this.size; i-- != 0L; ) {
/* 819 */       float k = s.readFloat();
/* 820 */       if (Float.floatToIntBits(k) == 0) { this.containsNull = true; continue; }
/*     */       
/* 822 */       long h = HashCommon.mix(HashCommon.float2int(k)); int base, displ;
/* 823 */       if (Float.floatToIntBits(key[base = (int)((h & this.mask) >>> 27L)][displ = (int)(h & this.segmentMask)]) != 0) while (true) { if (Float.floatToIntBits(key[base = base + (((displ = displ + 1 & this.segmentMask) == 0) ? 1 : 0) & this.baseMask][displ]) != 0)
/* 824 */             continue;  break; }   key[base][displ] = k;
/*     */     } 
/*     */   }
/*     */   
/*     */   private void checkTable() {}
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\i\\unimi\dsi\fastutil\floats\FloatOpenHashBigSet.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */