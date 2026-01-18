/*     */ package it.unimi.dsi.fastutil.floats;
/*     */ 
/*     */ import it.unimi.dsi.fastutil.Hash;
/*     */ import it.unimi.dsi.fastutil.HashCommon;
/*     */ import java.io.IOException;
/*     */ import java.io.ObjectInputStream;
/*     */ import java.io.ObjectOutputStream;
/*     */ import java.io.Serializable;
/*     */ import java.util.Arrays;
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
/*     */ 
/*     */ public class FloatOpenHashSet
/*     */   extends AbstractFloatSet
/*     */   implements Serializable, Cloneable, Hash
/*     */ {
/*     */   private static final long serialVersionUID = 0L;
/*     */   private static final boolean ASSERTS = false;
/*     */   protected transient float[] key;
/*     */   protected transient int mask;
/*     */   protected transient boolean containsNull;
/*     */   protected transient int n;
/*     */   protected transient int maxFill;
/*     */   protected final transient int minN;
/*     */   protected int size;
/*     */   protected final float f;
/*     */   
/*     */   public FloatOpenHashSet(int expected, float f) {
/*  80 */     if (f <= 0.0F || f >= 1.0F) throw new IllegalArgumentException("Load factor must be greater than 0 and smaller than 1"); 
/*  81 */     if (expected < 0) throw new IllegalArgumentException("The expected number of elements must be nonnegative"); 
/*  82 */     this.f = f;
/*  83 */     this.minN = this.n = HashCommon.arraySize(expected, f);
/*  84 */     this.mask = this.n - 1;
/*  85 */     this.maxFill = HashCommon.maxFill(this.n, f);
/*  86 */     this.key = new float[this.n + 1];
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public FloatOpenHashSet(int expected) {
/*  95 */     this(expected, 0.75F);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public FloatOpenHashSet() {
/* 103 */     this(16, 0.75F);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public FloatOpenHashSet(Collection<? extends Float> c, float f) {
/* 113 */     this(c.size(), f);
/* 114 */     addAll(c);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public FloatOpenHashSet(Collection<? extends Float> c) {
/* 124 */     this(c, 0.75F);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public FloatOpenHashSet(FloatCollection c, float f) {
/* 134 */     this(c.size(), f);
/* 135 */     addAll(c);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public FloatOpenHashSet(FloatCollection c) {
/* 145 */     this(c, 0.75F);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public FloatOpenHashSet(FloatIterator i, float f) {
/* 155 */     this(16, f);
/* 156 */     for (; i.hasNext(); add(i.nextFloat()));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public FloatOpenHashSet(FloatIterator i) {
/* 166 */     this(i, 0.75F);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public FloatOpenHashSet(Iterator<?> i, float f) {
/* 176 */     this(FloatIterators.asFloatIterator(i), f);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public FloatOpenHashSet(Iterator<?> i) {
/* 186 */     this(FloatIterators.asFloatIterator(i));
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
/*     */   public FloatOpenHashSet(float[] a, int offset, int length, float f) {
/* 198 */     this((length < 0) ? 0 : length, f);
/* 199 */     FloatArrays.ensureOffsetLength(a, offset, length);
/* 200 */     for (int i = 0; i < length; ) { add(a[offset + i]); i++; }
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
/*     */   public FloatOpenHashSet(float[] a, int offset, int length) {
/* 212 */     this(a, offset, length, 0.75F);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public FloatOpenHashSet(float[] a, float f) {
/* 222 */     this(a, 0, a.length, f);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public FloatOpenHashSet(float[] a) {
/* 232 */     this(a, 0.75F);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static FloatOpenHashSet of() {
/* 241 */     return new FloatOpenHashSet();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static FloatOpenHashSet of(float e) {
/* 252 */     FloatOpenHashSet result = new FloatOpenHashSet(1, 0.75F);
/* 253 */     result.add(e);
/* 254 */     return result;
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
/*     */   public static FloatOpenHashSet of(float e0, float e1) {
/* 268 */     FloatOpenHashSet result = new FloatOpenHashSet(2, 0.75F);
/* 269 */     result.add(e0);
/* 270 */     if (!result.add(e1)) {
/* 271 */       throw new IllegalArgumentException("Duplicate element: " + e1);
/*     */     }
/* 273 */     return result;
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
/*     */   public static FloatOpenHashSet of(float e0, float e1, float e2) {
/* 288 */     FloatOpenHashSet result = new FloatOpenHashSet(3, 0.75F);
/* 289 */     result.add(e0);
/* 290 */     if (!result.add(e1)) {
/* 291 */       throw new IllegalArgumentException("Duplicate element: " + e1);
/*     */     }
/* 293 */     if (!result.add(e2)) {
/* 294 */       throw new IllegalArgumentException("Duplicate element: " + e2);
/*     */     }
/* 296 */     return result;
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
/*     */   public static FloatOpenHashSet of(float... a) {
/* 310 */     FloatOpenHashSet result = new FloatOpenHashSet(a.length, 0.75F);
/* 311 */     for (float element : a) {
/* 312 */       if (!result.add(element)) {
/* 313 */         throw new IllegalArgumentException("Duplicate element " + element);
/*     */       }
/*     */     } 
/* 316 */     return result;
/*     */   }
/*     */   
/*     */   private int realSize() {
/* 320 */     return this.containsNull ? (this.size - 1) : this.size;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void ensureCapacity(int capacity) {
/* 330 */     int needed = HashCommon.arraySize(capacity, this.f);
/* 331 */     if (needed > this.n) rehash(needed); 
/*     */   }
/*     */   
/*     */   private void tryCapacity(long capacity) {
/* 335 */     int needed = (int)Math.min(1073741824L, Math.max(2L, HashCommon.nextPowerOfTwo((long)Math.ceil(((float)capacity / this.f)))));
/* 336 */     if (needed > this.n) rehash(needed);
/*     */   
/*     */   }
/*     */   
/*     */   public boolean addAll(FloatCollection c) {
/* 341 */     if (this.f <= 0.5D) { ensureCapacity(c.size()); }
/* 342 */     else { tryCapacity((size() + c.size())); }
/*     */     
/* 344 */     return super.addAll(c);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean addAll(Collection<? extends Float> c) {
/* 350 */     if (this.f <= 0.5D) { ensureCapacity(c.size()); }
/* 351 */     else { tryCapacity((size() + c.size())); }
/*     */     
/* 353 */     return super.addAll(c);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean add(float k) {
/* 359 */     if (Float.floatToIntBits(k) == 0) {
/* 360 */       if (this.containsNull) return false; 
/* 361 */       this.containsNull = true;
/*     */     } else {
/*     */       
/* 364 */       float[] key = this.key; int pos;
/*     */       float curr;
/* 366 */       if (Float.floatToIntBits(curr = key[pos = HashCommon.mix(HashCommon.float2int(k)) & this.mask]) != 0) {
/* 367 */         if (Float.floatToIntBits(curr) == Float.floatToIntBits(k)) return false; 
/* 368 */         while (Float.floatToIntBits(curr = key[pos = pos + 1 & this.mask]) != 0) { if (Float.floatToIntBits(curr) == Float.floatToIntBits(k)) return false;  }
/*     */       
/* 370 */       }  key[pos] = k;
/*     */     } 
/* 372 */     if (this.size++ >= this.maxFill) rehash(HashCommon.arraySize(this.size + 1, this.f));
/*     */     
/* 374 */     return true;
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
/*     */   protected final void shiftKeys(int pos) {
/* 387 */     float[] key = this.key; while (true) {
/*     */       float curr; int last;
/* 389 */       pos = (last = pos) + 1 & this.mask;
/*     */       while (true) {
/* 391 */         if (Float.floatToIntBits(curr = key[pos]) == 0) {
/* 392 */           key[last] = 0.0F;
/*     */           return;
/*     */         } 
/* 395 */         int slot = HashCommon.mix(HashCommon.float2int(curr)) & this.mask;
/* 396 */         if ((last <= pos) ? (last >= slot || slot > pos) : (last >= slot && slot > pos))
/* 397 */           break;  pos = pos + 1 & this.mask;
/*     */       } 
/* 399 */       key[last] = curr;
/*     */     } 
/*     */   }
/*     */   
/*     */   private boolean removeEntry(int pos) {
/* 404 */     this.size--;
/* 405 */     shiftKeys(pos);
/* 406 */     if (this.n > this.minN && this.size < this.maxFill / 4 && this.n > 16) rehash(this.n / 2); 
/* 407 */     return true;
/*     */   }
/*     */   
/*     */   private boolean removeNullEntry() {
/* 411 */     this.containsNull = false;
/* 412 */     this.key[this.n] = 0.0F;
/* 413 */     this.size--;
/* 414 */     if (this.n > this.minN && this.size < this.maxFill / 4 && this.n > 16) rehash(this.n / 2); 
/* 415 */     return true;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean remove(float k) {
/* 420 */     if (Float.floatToIntBits(k) == 0) {
/* 421 */       if (this.containsNull) return removeNullEntry(); 
/* 422 */       return false;
/*     */     } 
/*     */     
/* 425 */     float[] key = this.key;
/*     */     float curr;
/*     */     int pos;
/* 428 */     if (Float.floatToIntBits(curr = key[pos = HashCommon.mix(HashCommon.float2int(k)) & this.mask]) == 0) return false; 
/* 429 */     if (Float.floatToIntBits(k) == Float.floatToIntBits(curr)) return removeEntry(pos); 
/*     */     while (true) {
/* 431 */       if (Float.floatToIntBits(curr = key[pos = pos + 1 & this.mask]) == 0) return false; 
/* 432 */       if (Float.floatToIntBits(k) == Float.floatToIntBits(curr)) return removeEntry(pos);
/*     */     
/*     */     } 
/*     */   }
/*     */   
/*     */   public boolean contains(float k) {
/* 438 */     if (Float.floatToIntBits(k) == 0) return this.containsNull;
/*     */     
/* 440 */     float[] key = this.key;
/*     */     float curr;
/*     */     int pos;
/* 443 */     if (Float.floatToIntBits(curr = key[pos = HashCommon.mix(HashCommon.float2int(k)) & this.mask]) == 0) return false; 
/* 444 */     if (Float.floatToIntBits(k) == Float.floatToIntBits(curr)) return true; 
/*     */     while (true) {
/* 446 */       if (Float.floatToIntBits(curr = key[pos = pos + 1 & this.mask]) == 0) return false; 
/* 447 */       if (Float.floatToIntBits(k) == Float.floatToIntBits(curr)) return true;
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
/*     */   public void clear() {
/* 459 */     if (this.size == 0)
/* 460 */       return;  this.size = 0;
/* 461 */     this.containsNull = false;
/* 462 */     Arrays.fill(this.key, 0.0F);
/*     */   }
/*     */ 
/*     */   
/*     */   public int size() {
/* 467 */     return this.size;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isEmpty() {
/* 472 */     return (this.size == 0);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private final class SetIterator
/*     */     implements FloatIterator
/*     */   {
/* 481 */     int pos = FloatOpenHashSet.this.n;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 487 */     int last = -1;
/*     */     
/* 489 */     int c = FloatOpenHashSet.this.size;
/*     */     
/* 491 */     boolean mustReturnNull = FloatOpenHashSet.this.containsNull;
/*     */ 
/*     */ 
/*     */     
/*     */     FloatArrayList wrapped;
/*     */ 
/*     */ 
/*     */     
/*     */     public boolean hasNext() {
/* 500 */       return (this.c != 0);
/*     */     }
/*     */ 
/*     */     
/*     */     public float nextFloat() {
/* 505 */       if (!hasNext()) throw new NoSuchElementException(); 
/* 506 */       this.c--;
/* 507 */       if (this.mustReturnNull) {
/* 508 */         this.mustReturnNull = false;
/* 509 */         this.last = FloatOpenHashSet.this.n;
/* 510 */         return FloatOpenHashSet.this.key[FloatOpenHashSet.this.n];
/*     */       } 
/* 512 */       float[] key = FloatOpenHashSet.this.key;
/*     */       while (true) {
/* 514 */         if (--this.pos < 0) {
/*     */           
/* 516 */           this.last = Integer.MIN_VALUE;
/* 517 */           return this.wrapped.getFloat(-this.pos - 1);
/*     */         } 
/* 519 */         if (Float.floatToIntBits(key[this.pos]) != 0) return key[this.last = this.pos];
/*     */       
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
/*     */     private final void shiftKeys(int pos) {
/* 533 */       float[] key = FloatOpenHashSet.this.key; while (true) {
/*     */         float curr; int last;
/* 535 */         pos = (last = pos) + 1 & FloatOpenHashSet.this.mask;
/*     */         while (true) {
/* 537 */           if (Float.floatToIntBits(curr = key[pos]) == 0) {
/* 538 */             key[last] = 0.0F;
/*     */             return;
/*     */           } 
/* 541 */           int slot = HashCommon.mix(HashCommon.float2int(curr)) & FloatOpenHashSet.this.mask;
/* 542 */           if ((last <= pos) ? (last >= slot || slot > pos) : (last >= slot && slot > pos))
/* 543 */             break;  pos = pos + 1 & FloatOpenHashSet.this.mask;
/*     */         } 
/* 545 */         if (pos < last) {
/* 546 */           if (this.wrapped == null) this.wrapped = new FloatArrayList(2); 
/* 547 */           this.wrapped.add(key[pos]);
/*     */         } 
/* 549 */         key[last] = curr;
/*     */       } 
/*     */     }
/*     */ 
/*     */     
/*     */     public void remove() {
/* 555 */       if (this.last == -1) throw new IllegalStateException(); 
/* 556 */       if (this.last == FloatOpenHashSet.this.n)
/* 557 */       { FloatOpenHashSet.this.containsNull = false;
/* 558 */         FloatOpenHashSet.this.key[FloatOpenHashSet.this.n] = 0.0F; }
/* 559 */       else if (this.pos >= 0) { shiftKeys(this.last); }
/*     */       else
/*     */       
/* 562 */       { FloatOpenHashSet.this.remove(this.wrapped.getFloat(-this.pos - 1));
/* 563 */         this.last = -1;
/*     */         return; }
/*     */       
/* 566 */       FloatOpenHashSet.this.size--;
/* 567 */       this.last = -1;
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     public void forEachRemaining(FloatConsumer action) {
/* 573 */       float[] key = FloatOpenHashSet.this.key;
/* 574 */       if (this.mustReturnNull) {
/* 575 */         this.mustReturnNull = false;
/* 576 */         this.last = FloatOpenHashSet.this.n;
/* 577 */         action.accept(key[FloatOpenHashSet.this.n]);
/* 578 */         this.c--;
/*     */       } 
/* 580 */       while (this.c != 0) {
/* 581 */         if (--this.pos < 0) {
/*     */           
/* 583 */           this.last = Integer.MIN_VALUE;
/* 584 */           action.accept(this.wrapped.getFloat(-this.pos - 1));
/* 585 */           this.c--; continue;
/* 586 */         }  if (Float.floatToIntBits(key[this.pos]) != 0) {
/* 587 */           action.accept(key[this.last = this.pos]);
/* 588 */           this.c--;
/*     */         } 
/*     */       } 
/*     */     }
/*     */     
/*     */     private SetIterator() {} }
/*     */   
/*     */   public FloatIterator iterator() {
/* 596 */     return new SetIterator();
/*     */   }
/*     */ 
/*     */   
/*     */   private final class SetSpliterator
/*     */     implements FloatSpliterator
/*     */   {
/*     */     private static final int POST_SPLIT_CHARACTERISTICS = 257;
/*     */     
/* 605 */     int pos = 0;
/*     */     
/* 607 */     int max = FloatOpenHashSet.this.n;
/*     */     
/* 609 */     int c = 0;
/*     */     
/* 611 */     boolean mustReturnNull = FloatOpenHashSet.this.containsNull;
/*     */ 
/*     */     
/*     */     boolean hasSplit = false;
/*     */ 
/*     */     
/*     */     SetSpliterator(int pos, int max, boolean mustReturnNull, boolean hasSplit) {
/* 618 */       this.pos = pos;
/* 619 */       this.max = max;
/* 620 */       this.mustReturnNull = mustReturnNull;
/* 621 */       this.hasSplit = hasSplit;
/*     */     }
/*     */ 
/*     */     
/*     */     public boolean tryAdvance(FloatConsumer action) {
/* 626 */       if (this.mustReturnNull) {
/* 627 */         this.mustReturnNull = false;
/* 628 */         this.c++;
/* 629 */         action.accept(FloatOpenHashSet.this.key[FloatOpenHashSet.this.n]);
/* 630 */         return true;
/*     */       } 
/* 632 */       float[] key = FloatOpenHashSet.this.key;
/* 633 */       while (this.pos < this.max) {
/* 634 */         if (Float.floatToIntBits(key[this.pos]) != 0) {
/* 635 */           this.c++;
/* 636 */           action.accept(key[this.pos++]);
/* 637 */           return true;
/*     */         } 
/* 639 */         this.pos++;
/*     */       } 
/*     */       
/* 642 */       return false;
/*     */     }
/*     */ 
/*     */     
/*     */     public void forEachRemaining(FloatConsumer action) {
/* 647 */       float[] key = FloatOpenHashSet.this.key;
/* 648 */       if (this.mustReturnNull) {
/* 649 */         this.mustReturnNull = false;
/* 650 */         action.accept(key[FloatOpenHashSet.this.n]);
/* 651 */         this.c++;
/*     */       } 
/* 653 */       while (this.pos < this.max) {
/* 654 */         if (Float.floatToIntBits(key[this.pos]) != 0) {
/* 655 */           action.accept(key[this.pos]);
/* 656 */           this.c++;
/*     */         } 
/* 658 */         this.pos++;
/*     */       } 
/*     */     }
/*     */ 
/*     */     
/*     */     public int characteristics() {
/* 664 */       return this.hasSplit ? 257 : 321;
/*     */     }
/*     */ 
/*     */     
/*     */     public long estimateSize() {
/* 669 */       if (!this.hasSplit)
/*     */       {
/* 671 */         return (FloatOpenHashSet.this.size - this.c);
/*     */       }
/*     */ 
/*     */ 
/*     */       
/* 676 */       return Math.min((FloatOpenHashSet.this.size - this.c), (long)(FloatOpenHashSet.this.realSize() / FloatOpenHashSet.this.n * (this.max - this.pos)) + (this.mustReturnNull ? 1L : 0L));
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     public SetSpliterator trySplit() {
/* 682 */       if (this.pos >= this.max - 1) return null; 
/* 683 */       int retLen = this.max - this.pos >> 1;
/* 684 */       if (retLen <= 1) return null; 
/* 685 */       int myNewPos = this.pos + retLen;
/* 686 */       int retPos = this.pos;
/* 687 */       int retMax = myNewPos;
/*     */ 
/*     */ 
/*     */       
/* 691 */       SetSpliterator split = new SetSpliterator(retPos, retMax, this.mustReturnNull, true);
/* 692 */       this.pos = myNewPos;
/* 693 */       this.mustReturnNull = false;
/* 694 */       this.hasSplit = true;
/* 695 */       return split;
/*     */     }
/*     */ 
/*     */     
/*     */     public long skip(long n) {
/* 700 */       if (n < 0L) throw new IllegalArgumentException("Argument must be nonnegative: " + n); 
/* 701 */       if (n == 0L) return 0L; 
/* 702 */       long skipped = 0L;
/* 703 */       if (this.mustReturnNull) {
/* 704 */         this.mustReturnNull = false;
/* 705 */         skipped++;
/* 706 */         n--;
/*     */       } 
/* 708 */       float[] key = FloatOpenHashSet.this.key;
/* 709 */       while (this.pos < this.max && n > 0L) {
/* 710 */         if (Float.floatToIntBits(key[this.pos++]) != 0) {
/* 711 */           skipped++;
/* 712 */           n--;
/*     */         } 
/*     */       } 
/* 715 */       return skipped;
/*     */     }
/*     */     
/*     */     SetSpliterator() {} }
/*     */   
/*     */   public FloatSpliterator spliterator() {
/* 721 */     return new SetSpliterator();
/*     */   }
/*     */ 
/*     */   
/*     */   public void forEach(FloatConsumer action) {
/* 726 */     if (this.containsNull) action.accept(this.key[this.n]); 
/* 727 */     float[] key = this.key;
/* 728 */     for (int pos = this.n; pos-- != 0;) { if (Float.floatToIntBits(key[pos]) != 0) action.accept(key[pos]);
/*     */        }
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
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean trim() {
/* 745 */     return trim(this.size);
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
/*     */   public boolean trim(int n) {
/* 767 */     int l = HashCommon.nextPowerOfTwo((int)Math.ceil((n / this.f)));
/* 768 */     if (l >= this.n || this.size > HashCommon.maxFill(l, this.f)) return true; 
/*     */     try {
/* 770 */       rehash(l);
/* 771 */     } catch (OutOfMemoryError cantDoIt) {
/* 772 */       return false;
/*     */     } 
/* 774 */     return true;
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
/*     */   protected void rehash(int newN) {
/* 789 */     float[] key = this.key;
/* 790 */     int mask = newN - 1;
/* 791 */     float[] newKey = new float[newN + 1];
/* 792 */     int i = this.n;
/* 793 */     for (int j = realSize(); j-- != 0; ) {
/* 794 */       while (Float.floatToIntBits(key[--i]) == 0); int pos;
/* 795 */       if (Float.floatToIntBits(newKey[pos = HashCommon.mix(HashCommon.float2int(key[i])) & mask]) != 0) while (Float.floatToIntBits(newKey[pos = pos + 1 & mask]) != 0); 
/* 796 */       newKey[pos] = key[i];
/*     */     } 
/* 798 */     this.n = newN;
/* 799 */     this.mask = mask;
/* 800 */     this.maxFill = HashCommon.maxFill(this.n, this.f);
/* 801 */     this.key = newKey;
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
/*     */   public FloatOpenHashSet clone() {
/*     */     FloatOpenHashSet c;
/*     */     try {
/* 818 */       c = (FloatOpenHashSet)super.clone();
/* 819 */     } catch (CloneNotSupportedException cantHappen) {
/* 820 */       throw new InternalError();
/*     */     } 
/* 822 */     c.key = (float[])this.key.clone();
/* 823 */     c.containsNull = this.containsNull;
/* 824 */     return c;
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
/* 838 */     int h = 0;
/* 839 */     for (int j = realSize(), i = 0; j-- != 0; ) {
/* 840 */       for (; Float.floatToIntBits(this.key[i]) == 0; i++);
/* 841 */       h += HashCommon.float2int(this.key[i]);
/* 842 */       i++;
/*     */     } 
/*     */     
/* 845 */     return h;
/*     */   }
/*     */   
/*     */   private void writeObject(ObjectOutputStream s) throws IOException {
/* 849 */     FloatIterator i = iterator();
/* 850 */     s.defaultWriteObject();
/* 851 */     for (int j = this.size; j-- != 0; s.writeFloat(i.nextFloat()));
/*     */   }
/*     */   
/*     */   private void readObject(ObjectInputStream s) throws IOException, ClassNotFoundException {
/* 855 */     s.defaultReadObject();
/* 856 */     this.n = HashCommon.arraySize(this.size, this.f);
/* 857 */     this.maxFill = HashCommon.maxFill(this.n, this.f);
/* 858 */     this.mask = this.n - 1;
/* 859 */     float[] key = this.key = new float[this.n + 1];
/*     */     
/* 861 */     for (int i = this.size; i-- != 0; ) {
/* 862 */       int pos; float k = s.readFloat();
/* 863 */       if (Float.floatToIntBits(k) == 0)
/* 864 */       { pos = this.n;
/* 865 */         this.containsNull = true; }
/*     */       
/* 867 */       else if (Float.floatToIntBits(key[pos = HashCommon.mix(HashCommon.float2int(k)) & this.mask]) != 0) { while (Float.floatToIntBits(key[pos = pos + 1 & this.mask]) != 0); }
/*     */       
/* 869 */       key[pos] = k;
/*     */     } 
/*     */   }
/*     */   
/*     */   private void checkTable() {}
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\i\\unimi\dsi\fastutil\floats\FloatOpenHashSet.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */