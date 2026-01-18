/*     */ package it.unimi.dsi.fastutil.booleans;
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
/*     */ public class BooleanOpenHashSet
/*     */   extends AbstractBooleanSet
/*     */   implements Serializable, Cloneable, Hash
/*     */ {
/*     */   private static final long serialVersionUID = 0L;
/*     */   private static final boolean ASSERTS = false;
/*     */   protected transient boolean[] key;
/*     */   protected transient int mask;
/*     */   protected transient boolean containsNull;
/*     */   protected transient int n;
/*     */   protected transient int maxFill;
/*     */   protected final transient int minN;
/*     */   protected int size;
/*     */   protected final float f;
/*     */   
/*     */   public BooleanOpenHashSet(int expected, float f) {
/*  80 */     if (f <= 0.0F || f >= 1.0F) throw new IllegalArgumentException("Load factor must be greater than 0 and smaller than 1"); 
/*  81 */     if (expected < 0) throw new IllegalArgumentException("The expected number of elements must be nonnegative"); 
/*  82 */     this.f = f;
/*  83 */     this.minN = this.n = HashCommon.arraySize(expected, f);
/*  84 */     this.mask = this.n - 1;
/*  85 */     this.maxFill = HashCommon.maxFill(this.n, f);
/*  86 */     this.key = new boolean[this.n + 1];
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public BooleanOpenHashSet(int expected) {
/*  95 */     this(expected, 0.75F);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public BooleanOpenHashSet() {
/* 103 */     this(16, 0.75F);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public BooleanOpenHashSet(Collection<? extends Boolean> c, float f) {
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
/*     */   public BooleanOpenHashSet(Collection<? extends Boolean> c) {
/* 124 */     this(c, 0.75F);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public BooleanOpenHashSet(BooleanCollection c, float f) {
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
/*     */   public BooleanOpenHashSet(BooleanCollection c) {
/* 145 */     this(c, 0.75F);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public BooleanOpenHashSet(BooleanIterator i, float f) {
/* 155 */     this(16, f);
/* 156 */     for (; i.hasNext(); add(i.nextBoolean()));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public BooleanOpenHashSet(BooleanIterator i) {
/* 166 */     this(i, 0.75F);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public BooleanOpenHashSet(Iterator<?> i, float f) {
/* 176 */     this(BooleanIterators.asBooleanIterator(i), f);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public BooleanOpenHashSet(Iterator<?> i) {
/* 186 */     this(BooleanIterators.asBooleanIterator(i));
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
/*     */   public BooleanOpenHashSet(boolean[] a, int offset, int length, float f) {
/* 198 */     this((length < 0) ? 0 : length, f);
/* 199 */     BooleanArrays.ensureOffsetLength(a, offset, length);
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
/*     */   public BooleanOpenHashSet(boolean[] a, int offset, int length) {
/* 212 */     this(a, offset, length, 0.75F);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public BooleanOpenHashSet(boolean[] a, float f) {
/* 222 */     this(a, 0, a.length, f);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public BooleanOpenHashSet(boolean[] a) {
/* 232 */     this(a, 0.75F);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static BooleanOpenHashSet of() {
/* 241 */     return new BooleanOpenHashSet();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static BooleanOpenHashSet of(boolean e) {
/* 252 */     BooleanOpenHashSet result = new BooleanOpenHashSet(1, 0.75F);
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
/*     */   public static BooleanOpenHashSet of(boolean e0, boolean e1) {
/* 268 */     BooleanOpenHashSet result = new BooleanOpenHashSet(2, 0.75F);
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
/*     */   public static BooleanOpenHashSet of(boolean e0, boolean e1, boolean e2) {
/* 288 */     BooleanOpenHashSet result = new BooleanOpenHashSet(3, 0.75F);
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
/*     */   public static BooleanOpenHashSet of(boolean... a) {
/* 310 */     BooleanOpenHashSet result = new BooleanOpenHashSet(a.length, 0.75F);
/* 311 */     for (boolean element : a) {
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
/*     */   public boolean addAll(BooleanCollection c) {
/* 341 */     if (this.f <= 0.5D) { ensureCapacity(c.size()); }
/* 342 */     else { tryCapacity((size() + c.size())); }
/*     */     
/* 344 */     return super.addAll(c);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean addAll(Collection<? extends Boolean> c) {
/* 350 */     if (this.f <= 0.5D) { ensureCapacity(c.size()); }
/* 351 */     else { tryCapacity((size() + c.size())); }
/*     */     
/* 353 */     return super.addAll(c);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean add(boolean k) {
/* 359 */     if (!k) {
/* 360 */       if (this.containsNull) return false; 
/* 361 */       this.containsNull = true;
/*     */     } else {
/*     */       
/* 364 */       boolean[] key = this.key; int pos;
/*     */       boolean curr;
/* 366 */       if (curr = key[pos = (k ? 262886248 : -878682501) & this.mask]) {
/* 367 */         if (curr == k) return false; 
/* 368 */         while (curr = key[pos = pos + 1 & this.mask]) { if (curr == k) return false;  }
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
/* 387 */     boolean[] key = this.key; while (true) {
/*     */       boolean curr; int last;
/* 389 */       pos = (last = pos) + 1 & this.mask;
/*     */       while (true) {
/* 391 */         if (!(curr = key[pos])) {
/* 392 */           key[last] = false;
/*     */           return;
/*     */         } 
/* 395 */         int slot = (curr ? 262886248 : -878682501) & this.mask;
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
/* 412 */     this.key[this.n] = false;
/* 413 */     this.size--;
/* 414 */     if (this.n > this.minN && this.size < this.maxFill / 4 && this.n > 16) rehash(this.n / 2); 
/* 415 */     return true;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean remove(boolean k) {
/* 420 */     if (!k) {
/* 421 */       if (this.containsNull) return removeNullEntry(); 
/* 422 */       return false;
/*     */     } 
/*     */     
/* 425 */     boolean[] key = this.key;
/*     */     boolean curr;
/*     */     int pos;
/* 428 */     if (!(curr = key[pos = (k ? 262886248 : -878682501) & this.mask])) return false; 
/* 429 */     if (k == curr) return removeEntry(pos); 
/*     */     while (true) {
/* 431 */       if (!(curr = key[pos = pos + 1 & this.mask])) return false; 
/* 432 */       if (k == curr) return removeEntry(pos);
/*     */     
/*     */     } 
/*     */   }
/*     */   
/*     */   public boolean contains(boolean k) {
/* 438 */     if (!k) return this.containsNull;
/*     */     
/* 440 */     boolean[] key = this.key;
/*     */     boolean curr;
/*     */     int pos;
/* 443 */     if (!(curr = key[pos = (k ? 262886248 : -878682501) & this.mask])) return false; 
/* 444 */     if (k == curr) return true; 
/*     */     while (true) {
/* 446 */       if (!(curr = key[pos = pos + 1 & this.mask])) return false; 
/* 447 */       if (k == curr) return true;
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
/* 462 */     Arrays.fill(this.key, false);
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
/*     */     implements BooleanIterator
/*     */   {
/* 481 */     int pos = BooleanOpenHashSet.this.n;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 487 */     int last = -1;
/*     */     
/* 489 */     int c = BooleanOpenHashSet.this.size;
/*     */     
/* 491 */     boolean mustReturnNull = BooleanOpenHashSet.this.containsNull;
/*     */ 
/*     */ 
/*     */     
/*     */     BooleanArrayList wrapped;
/*     */ 
/*     */ 
/*     */     
/*     */     public boolean hasNext() {
/* 500 */       return (this.c != 0);
/*     */     }
/*     */ 
/*     */     
/*     */     public boolean nextBoolean() {
/* 505 */       if (!hasNext()) throw new NoSuchElementException(); 
/* 506 */       this.c--;
/* 507 */       if (this.mustReturnNull) {
/* 508 */         this.mustReturnNull = false;
/* 509 */         this.last = BooleanOpenHashSet.this.n;
/* 510 */         return BooleanOpenHashSet.this.key[BooleanOpenHashSet.this.n];
/*     */       } 
/* 512 */       boolean[] key = BooleanOpenHashSet.this.key;
/*     */       while (true) {
/* 514 */         if (--this.pos < 0) {
/*     */           
/* 516 */           this.last = Integer.MIN_VALUE;
/* 517 */           return this.wrapped.getBoolean(-this.pos - 1);
/*     */         } 
/* 519 */         if (key[this.pos]) return key[this.last = this.pos];
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
/* 533 */       boolean[] key = BooleanOpenHashSet.this.key; while (true) {
/*     */         boolean curr; int last;
/* 535 */         pos = (last = pos) + 1 & BooleanOpenHashSet.this.mask;
/*     */         while (true) {
/* 537 */           if (!(curr = key[pos])) {
/* 538 */             key[last] = false;
/*     */             return;
/*     */           } 
/* 541 */           int slot = (curr ? 262886248 : -878682501) & BooleanOpenHashSet.this.mask;
/* 542 */           if ((last <= pos) ? (last >= slot || slot > pos) : (last >= slot && slot > pos))
/* 543 */             break;  pos = pos + 1 & BooleanOpenHashSet.this.mask;
/*     */         } 
/* 545 */         if (pos < last) {
/* 546 */           if (this.wrapped == null) this.wrapped = new BooleanArrayList(2); 
/* 547 */           this.wrapped.add(key[pos]);
/*     */         } 
/* 549 */         key[last] = curr;
/*     */       } 
/*     */     }
/*     */ 
/*     */     
/*     */     public void remove() {
/* 555 */       if (this.last == -1) throw new IllegalStateException(); 
/* 556 */       if (this.last == BooleanOpenHashSet.this.n)
/* 557 */       { BooleanOpenHashSet.this.containsNull = false;
/* 558 */         BooleanOpenHashSet.this.key[BooleanOpenHashSet.this.n] = false; }
/* 559 */       else if (this.pos >= 0) { shiftKeys(this.last); }
/*     */       else
/*     */       
/* 562 */       { BooleanOpenHashSet.this.remove(this.wrapped.getBoolean(-this.pos - 1));
/* 563 */         this.last = -1;
/*     */         return; }
/*     */       
/* 566 */       BooleanOpenHashSet.this.size--;
/* 567 */       this.last = -1;
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     public void forEachRemaining(BooleanConsumer action) {
/* 573 */       boolean[] key = BooleanOpenHashSet.this.key;
/* 574 */       if (this.mustReturnNull) {
/* 575 */         this.mustReturnNull = false;
/* 576 */         this.last = BooleanOpenHashSet.this.n;
/* 577 */         action.accept(key[BooleanOpenHashSet.this.n]);
/* 578 */         this.c--;
/*     */       } 
/* 580 */       while (this.c != 0) {
/* 581 */         if (--this.pos < 0) {
/*     */           
/* 583 */           this.last = Integer.MIN_VALUE;
/* 584 */           action.accept(this.wrapped.getBoolean(-this.pos - 1));
/* 585 */           this.c--; continue;
/* 586 */         }  if (key[this.pos]) {
/* 587 */           action.accept(key[this.last = this.pos]);
/* 588 */           this.c--;
/*     */         } 
/*     */       } 
/*     */     }
/*     */     
/*     */     private SetIterator() {} }
/*     */   
/*     */   public BooleanIterator iterator() {
/* 596 */     return new SetIterator();
/*     */   }
/*     */ 
/*     */   
/*     */   private final class SetSpliterator
/*     */     implements BooleanSpliterator
/*     */   {
/*     */     private static final int POST_SPLIT_CHARACTERISTICS = 257;
/*     */     
/* 605 */     int pos = 0;
/*     */     
/* 607 */     int max = BooleanOpenHashSet.this.n;
/*     */     
/* 609 */     int c = 0;
/*     */     
/* 611 */     boolean mustReturnNull = BooleanOpenHashSet.this.containsNull;
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
/*     */     public boolean tryAdvance(BooleanConsumer action) {
/* 626 */       if (this.mustReturnNull) {
/* 627 */         this.mustReturnNull = false;
/* 628 */         this.c++;
/* 629 */         action.accept(BooleanOpenHashSet.this.key[BooleanOpenHashSet.this.n]);
/* 630 */         return true;
/*     */       } 
/* 632 */       boolean[] key = BooleanOpenHashSet.this.key;
/* 633 */       while (this.pos < this.max) {
/* 634 */         if (key[this.pos]) {
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
/*     */     public void forEachRemaining(BooleanConsumer action) {
/* 647 */       boolean[] key = BooleanOpenHashSet.this.key;
/* 648 */       if (this.mustReturnNull) {
/* 649 */         this.mustReturnNull = false;
/* 650 */         action.accept(key[BooleanOpenHashSet.this.n]);
/* 651 */         this.c++;
/*     */       } 
/* 653 */       while (this.pos < this.max) {
/* 654 */         if (key[this.pos]) {
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
/* 671 */         return (BooleanOpenHashSet.this.size - this.c);
/*     */       }
/*     */ 
/*     */ 
/*     */       
/* 676 */       return Math.min((BooleanOpenHashSet.this.size - this.c), (long)(BooleanOpenHashSet.this.realSize() / BooleanOpenHashSet.this.n * (this.max - this.pos)) + (this.mustReturnNull ? 1L : 0L));
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
/* 708 */       boolean[] key = BooleanOpenHashSet.this.key;
/* 709 */       while (this.pos < this.max && n > 0L) {
/* 710 */         if (key[this.pos++]) {
/* 711 */           skipped++;
/* 712 */           n--;
/*     */         } 
/*     */       } 
/* 715 */       return skipped;
/*     */     }
/*     */     
/*     */     SetSpliterator() {} }
/*     */   
/*     */   public BooleanSpliterator spliterator() {
/* 721 */     return new SetSpliterator();
/*     */   }
/*     */ 
/*     */   
/*     */   public void forEach(BooleanConsumer action) {
/* 726 */     if (this.containsNull) action.accept(this.key[this.n]); 
/* 727 */     boolean[] key = this.key;
/* 728 */     for (int pos = this.n; pos-- != 0;) { if (key[pos]) action.accept(key[pos]);
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
/* 789 */     boolean[] key = this.key;
/* 790 */     int mask = newN - 1;
/* 791 */     boolean[] newKey = new boolean[newN + 1];
/* 792 */     int i = this.n;
/* 793 */     for (int j = realSize(); j-- != 0; ) {
/* 794 */       while (!key[--i]); int pos;
/* 795 */       if (newKey[pos = (key[i] ? 262886248 : -878682501) & mask]) while (newKey[pos = pos + 1 & mask]); 
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
/*     */   public BooleanOpenHashSet clone() {
/*     */     BooleanOpenHashSet c;
/*     */     try {
/* 818 */       c = (BooleanOpenHashSet)super.clone();
/* 819 */     } catch (CloneNotSupportedException cantHappen) {
/* 820 */       throw new InternalError();
/*     */     } 
/* 822 */     c.key = (boolean[])this.key.clone();
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
/* 840 */       for (; !this.key[i]; i++);
/* 841 */       h += this.key[i] ? 1231 : 1237;
/* 842 */       i++;
/*     */     } 
/*     */     
/* 845 */     return h;
/*     */   }
/*     */   
/*     */   private void writeObject(ObjectOutputStream s) throws IOException {
/* 849 */     BooleanIterator i = iterator();
/* 850 */     s.defaultWriteObject();
/* 851 */     for (int j = this.size; j-- != 0; s.writeBoolean(i.nextBoolean()));
/*     */   }
/*     */   
/*     */   private void readObject(ObjectInputStream s) throws IOException, ClassNotFoundException {
/* 855 */     s.defaultReadObject();
/* 856 */     this.n = HashCommon.arraySize(this.size, this.f);
/* 857 */     this.maxFill = HashCommon.maxFill(this.n, this.f);
/* 858 */     this.mask = this.n - 1;
/* 859 */     boolean[] key = this.key = new boolean[this.n + 1];
/*     */     
/* 861 */     for (int i = this.size; i-- != 0; ) {
/* 862 */       int pos; boolean k = s.readBoolean();
/* 863 */       if (!k)
/* 864 */       { pos = this.n;
/* 865 */         this.containsNull = true; }
/*     */       
/* 867 */       else if (key[pos = (k ? 262886248 : -878682501) & this.mask]) { while (key[pos = pos + 1 & this.mask]); }
/*     */       
/* 869 */       key[pos] = k;
/*     */     } 
/*     */   }
/*     */   
/*     */   private void checkTable() {}
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\i\\unimi\dsi\fastutil\booleans\BooleanOpenHashSet.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */