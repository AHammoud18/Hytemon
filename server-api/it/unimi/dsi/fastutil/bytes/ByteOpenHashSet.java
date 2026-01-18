/*     */ package it.unimi.dsi.fastutil.bytes;
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
/*     */ public class ByteOpenHashSet
/*     */   extends AbstractByteSet
/*     */   implements Serializable, Cloneable, Hash
/*     */ {
/*     */   private static final long serialVersionUID = 0L;
/*     */   private static final boolean ASSERTS = false;
/*     */   protected transient byte[] key;
/*     */   protected transient int mask;
/*     */   protected transient boolean containsNull;
/*     */   protected transient int n;
/*     */   protected transient int maxFill;
/*     */   protected final transient int minN;
/*     */   protected int size;
/*     */   protected final float f;
/*     */   
/*     */   public ByteOpenHashSet(int expected, float f) {
/*  80 */     if (f <= 0.0F || f >= 1.0F) throw new IllegalArgumentException("Load factor must be greater than 0 and smaller than 1"); 
/*  81 */     if (expected < 0) throw new IllegalArgumentException("The expected number of elements must be nonnegative"); 
/*  82 */     this.f = f;
/*  83 */     this.minN = this.n = HashCommon.arraySize(expected, f);
/*  84 */     this.mask = this.n - 1;
/*  85 */     this.maxFill = HashCommon.maxFill(this.n, f);
/*  86 */     this.key = new byte[this.n + 1];
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ByteOpenHashSet(int expected) {
/*  95 */     this(expected, 0.75F);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ByteOpenHashSet() {
/* 103 */     this(16, 0.75F);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ByteOpenHashSet(Collection<? extends Byte> c, float f) {
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
/*     */   public ByteOpenHashSet(Collection<? extends Byte> c) {
/* 124 */     this(c, 0.75F);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ByteOpenHashSet(ByteCollection c, float f) {
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
/*     */   public ByteOpenHashSet(ByteCollection c) {
/* 145 */     this(c, 0.75F);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ByteOpenHashSet(ByteIterator i, float f) {
/* 155 */     this(16, f);
/* 156 */     for (; i.hasNext(); add(i.nextByte()));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ByteOpenHashSet(ByteIterator i) {
/* 166 */     this(i, 0.75F);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ByteOpenHashSet(Iterator<?> i, float f) {
/* 176 */     this(ByteIterators.asByteIterator(i), f);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ByteOpenHashSet(Iterator<?> i) {
/* 186 */     this(ByteIterators.asByteIterator(i));
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
/*     */   public ByteOpenHashSet(byte[] a, int offset, int length, float f) {
/* 198 */     this((length < 0) ? 0 : length, f);
/* 199 */     ByteArrays.ensureOffsetLength(a, offset, length);
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
/*     */   public ByteOpenHashSet(byte[] a, int offset, int length) {
/* 212 */     this(a, offset, length, 0.75F);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ByteOpenHashSet(byte[] a, float f) {
/* 222 */     this(a, 0, a.length, f);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ByteOpenHashSet(byte[] a) {
/* 232 */     this(a, 0.75F);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static ByteOpenHashSet of() {
/* 241 */     return new ByteOpenHashSet();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static ByteOpenHashSet of(byte e) {
/* 252 */     ByteOpenHashSet result = new ByteOpenHashSet(1, 0.75F);
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
/*     */   public static ByteOpenHashSet of(byte e0, byte e1) {
/* 268 */     ByteOpenHashSet result = new ByteOpenHashSet(2, 0.75F);
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
/*     */   public static ByteOpenHashSet of(byte e0, byte e1, byte e2) {
/* 288 */     ByteOpenHashSet result = new ByteOpenHashSet(3, 0.75F);
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
/*     */   public static ByteOpenHashSet of(byte... a) {
/* 310 */     ByteOpenHashSet result = new ByteOpenHashSet(a.length, 0.75F);
/* 311 */     for (byte element : a) {
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
/*     */   public boolean addAll(ByteCollection c) {
/* 341 */     if (this.f <= 0.5D) { ensureCapacity(c.size()); }
/* 342 */     else { tryCapacity((size() + c.size())); }
/*     */     
/* 344 */     return super.addAll(c);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean addAll(Collection<? extends Byte> c) {
/* 350 */     if (this.f <= 0.5D) { ensureCapacity(c.size()); }
/* 351 */     else { tryCapacity((size() + c.size())); }
/*     */     
/* 353 */     return super.addAll(c);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean add(byte k) {
/* 359 */     if (k == 0) {
/* 360 */       if (this.containsNull) return false; 
/* 361 */       this.containsNull = true;
/*     */     } else {
/*     */       
/* 364 */       byte[] key = this.key; int pos;
/*     */       byte curr;
/* 366 */       if ((curr = key[pos = HashCommon.mix(k) & this.mask]) != 0) {
/* 367 */         if (curr == k) return false; 
/* 368 */         while ((curr = key[pos = pos + 1 & this.mask]) != 0) { if (curr == k) return false;  }
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
/* 387 */     byte[] key = this.key; while (true) {
/*     */       byte curr; int last;
/* 389 */       pos = (last = pos) + 1 & this.mask;
/*     */       while (true) {
/* 391 */         if ((curr = key[pos]) == 0) {
/* 392 */           key[last] = 0;
/*     */           return;
/*     */         } 
/* 395 */         int slot = HashCommon.mix(curr) & this.mask;
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
/* 412 */     this.key[this.n] = 0;
/* 413 */     this.size--;
/* 414 */     if (this.n > this.minN && this.size < this.maxFill / 4 && this.n > 16) rehash(this.n / 2); 
/* 415 */     return true;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean remove(byte k) {
/* 420 */     if (k == 0) {
/* 421 */       if (this.containsNull) return removeNullEntry(); 
/* 422 */       return false;
/*     */     } 
/*     */     
/* 425 */     byte[] key = this.key;
/*     */     byte curr;
/*     */     int pos;
/* 428 */     if ((curr = key[pos = HashCommon.mix(k) & this.mask]) == 0) return false; 
/* 429 */     if (k == curr) return removeEntry(pos); 
/*     */     while (true) {
/* 431 */       if ((curr = key[pos = pos + 1 & this.mask]) == 0) return false; 
/* 432 */       if (k == curr) return removeEntry(pos);
/*     */     
/*     */     } 
/*     */   }
/*     */   
/*     */   public boolean contains(byte k) {
/* 438 */     if (k == 0) return this.containsNull;
/*     */     
/* 440 */     byte[] key = this.key;
/*     */     byte curr;
/*     */     int pos;
/* 443 */     if ((curr = key[pos = HashCommon.mix(k) & this.mask]) == 0) return false; 
/* 444 */     if (k == curr) return true; 
/*     */     while (true) {
/* 446 */       if ((curr = key[pos = pos + 1 & this.mask]) == 0) return false; 
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
/* 462 */     Arrays.fill(this.key, (byte)0);
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
/*     */     implements ByteIterator
/*     */   {
/* 481 */     int pos = ByteOpenHashSet.this.n;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 487 */     int last = -1;
/*     */     
/* 489 */     int c = ByteOpenHashSet.this.size;
/*     */     
/* 491 */     boolean mustReturnNull = ByteOpenHashSet.this.containsNull;
/*     */ 
/*     */ 
/*     */     
/*     */     ByteArrayList wrapped;
/*     */ 
/*     */ 
/*     */     
/*     */     public boolean hasNext() {
/* 500 */       return (this.c != 0);
/*     */     }
/*     */ 
/*     */     
/*     */     public byte nextByte() {
/* 505 */       if (!hasNext()) throw new NoSuchElementException(); 
/* 506 */       this.c--;
/* 507 */       if (this.mustReturnNull) {
/* 508 */         this.mustReturnNull = false;
/* 509 */         this.last = ByteOpenHashSet.this.n;
/* 510 */         return ByteOpenHashSet.this.key[ByteOpenHashSet.this.n];
/*     */       } 
/* 512 */       byte[] key = ByteOpenHashSet.this.key;
/*     */       while (true) {
/* 514 */         if (--this.pos < 0) {
/*     */           
/* 516 */           this.last = Integer.MIN_VALUE;
/* 517 */           return this.wrapped.getByte(-this.pos - 1);
/*     */         } 
/* 519 */         if (key[this.pos] != 0) return key[this.last = this.pos];
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
/* 533 */       byte[] key = ByteOpenHashSet.this.key; while (true) {
/*     */         byte curr; int last;
/* 535 */         pos = (last = pos) + 1 & ByteOpenHashSet.this.mask;
/*     */         while (true) {
/* 537 */           if ((curr = key[pos]) == 0) {
/* 538 */             key[last] = 0;
/*     */             return;
/*     */           } 
/* 541 */           int slot = HashCommon.mix(curr) & ByteOpenHashSet.this.mask;
/* 542 */           if ((last <= pos) ? (last >= slot || slot > pos) : (last >= slot && slot > pos))
/* 543 */             break;  pos = pos + 1 & ByteOpenHashSet.this.mask;
/*     */         } 
/* 545 */         if (pos < last) {
/* 546 */           if (this.wrapped == null) this.wrapped = new ByteArrayList(2); 
/* 547 */           this.wrapped.add(key[pos]);
/*     */         } 
/* 549 */         key[last] = curr;
/*     */       } 
/*     */     }
/*     */ 
/*     */     
/*     */     public void remove() {
/* 555 */       if (this.last == -1) throw new IllegalStateException(); 
/* 556 */       if (this.last == ByteOpenHashSet.this.n)
/* 557 */       { ByteOpenHashSet.this.containsNull = false;
/* 558 */         ByteOpenHashSet.this.key[ByteOpenHashSet.this.n] = 0; }
/* 559 */       else if (this.pos >= 0) { shiftKeys(this.last); }
/*     */       else
/*     */       
/* 562 */       { ByteOpenHashSet.this.remove(this.wrapped.getByte(-this.pos - 1));
/* 563 */         this.last = -1;
/*     */         return; }
/*     */       
/* 566 */       ByteOpenHashSet.this.size--;
/* 567 */       this.last = -1;
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     public void forEachRemaining(ByteConsumer action) {
/* 573 */       byte[] key = ByteOpenHashSet.this.key;
/* 574 */       if (this.mustReturnNull) {
/* 575 */         this.mustReturnNull = false;
/* 576 */         this.last = ByteOpenHashSet.this.n;
/* 577 */         action.accept(key[ByteOpenHashSet.this.n]);
/* 578 */         this.c--;
/*     */       } 
/* 580 */       while (this.c != 0) {
/* 581 */         if (--this.pos < 0) {
/*     */           
/* 583 */           this.last = Integer.MIN_VALUE;
/* 584 */           action.accept(this.wrapped.getByte(-this.pos - 1));
/* 585 */           this.c--; continue;
/* 586 */         }  if (key[this.pos] != 0) {
/* 587 */           action.accept(key[this.last = this.pos]);
/* 588 */           this.c--;
/*     */         } 
/*     */       } 
/*     */     }
/*     */     
/*     */     private SetIterator() {} }
/*     */   
/*     */   public ByteIterator iterator() {
/* 596 */     return new SetIterator();
/*     */   }
/*     */ 
/*     */   
/*     */   private final class SetSpliterator
/*     */     implements ByteSpliterator
/*     */   {
/*     */     private static final int POST_SPLIT_CHARACTERISTICS = 257;
/*     */     
/* 605 */     int pos = 0;
/*     */     
/* 607 */     int max = ByteOpenHashSet.this.n;
/*     */     
/* 609 */     int c = 0;
/*     */     
/* 611 */     boolean mustReturnNull = ByteOpenHashSet.this.containsNull;
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
/*     */     public boolean tryAdvance(ByteConsumer action) {
/* 626 */       if (this.mustReturnNull) {
/* 627 */         this.mustReturnNull = false;
/* 628 */         this.c++;
/* 629 */         action.accept(ByteOpenHashSet.this.key[ByteOpenHashSet.this.n]);
/* 630 */         return true;
/*     */       } 
/* 632 */       byte[] key = ByteOpenHashSet.this.key;
/* 633 */       while (this.pos < this.max) {
/* 634 */         if (key[this.pos] != 0) {
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
/*     */     public void forEachRemaining(ByteConsumer action) {
/* 647 */       byte[] key = ByteOpenHashSet.this.key;
/* 648 */       if (this.mustReturnNull) {
/* 649 */         this.mustReturnNull = false;
/* 650 */         action.accept(key[ByteOpenHashSet.this.n]);
/* 651 */         this.c++;
/*     */       } 
/* 653 */       while (this.pos < this.max) {
/* 654 */         if (key[this.pos] != 0) {
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
/* 671 */         return (ByteOpenHashSet.this.size - this.c);
/*     */       }
/*     */ 
/*     */ 
/*     */       
/* 676 */       return Math.min((ByteOpenHashSet.this.size - this.c), (long)(ByteOpenHashSet.this.realSize() / ByteOpenHashSet.this.n * (this.max - this.pos)) + (this.mustReturnNull ? 1L : 0L));
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
/* 708 */       byte[] key = ByteOpenHashSet.this.key;
/* 709 */       while (this.pos < this.max && n > 0L) {
/* 710 */         if (key[this.pos++] != 0) {
/* 711 */           skipped++;
/* 712 */           n--;
/*     */         } 
/*     */       } 
/* 715 */       return skipped;
/*     */     }
/*     */     
/*     */     SetSpliterator() {} }
/*     */   
/*     */   public ByteSpliterator spliterator() {
/* 721 */     return new SetSpliterator();
/*     */   }
/*     */ 
/*     */   
/*     */   public void forEach(ByteConsumer action) {
/* 726 */     if (this.containsNull) action.accept(this.key[this.n]); 
/* 727 */     byte[] key = this.key;
/* 728 */     for (int pos = this.n; pos-- != 0;) { if (key[pos] != 0) action.accept(key[pos]);
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
/* 789 */     byte[] key = this.key;
/* 790 */     int mask = newN - 1;
/* 791 */     byte[] newKey = new byte[newN + 1];
/* 792 */     int i = this.n;
/* 793 */     for (int j = realSize(); j-- != 0; ) {
/* 794 */       while (key[--i] == 0); int pos;
/* 795 */       if (newKey[pos = HashCommon.mix(key[i]) & mask] != 0) while (newKey[pos = pos + 1 & mask] != 0); 
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
/*     */   public ByteOpenHashSet clone() {
/*     */     ByteOpenHashSet c;
/*     */     try {
/* 818 */       c = (ByteOpenHashSet)super.clone();
/* 819 */     } catch (CloneNotSupportedException cantHappen) {
/* 820 */       throw new InternalError();
/*     */     } 
/* 822 */     c.key = (byte[])this.key.clone();
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
/* 840 */       for (; this.key[i] == 0; i++);
/* 841 */       h += this.key[i];
/* 842 */       i++;
/*     */     } 
/*     */     
/* 845 */     return h;
/*     */   }
/*     */   
/*     */   private void writeObject(ObjectOutputStream s) throws IOException {
/* 849 */     ByteIterator i = iterator();
/* 850 */     s.defaultWriteObject();
/* 851 */     for (int j = this.size; j-- != 0; s.writeByte(i.nextByte()));
/*     */   }
/*     */   
/*     */   private void readObject(ObjectInputStream s) throws IOException, ClassNotFoundException {
/* 855 */     s.defaultReadObject();
/* 856 */     this.n = HashCommon.arraySize(this.size, this.f);
/* 857 */     this.maxFill = HashCommon.maxFill(this.n, this.f);
/* 858 */     this.mask = this.n - 1;
/* 859 */     byte[] key = this.key = new byte[this.n + 1];
/*     */     
/* 861 */     for (int i = this.size; i-- != 0; ) {
/* 862 */       int pos; byte k = s.readByte();
/* 863 */       if (k == 0)
/* 864 */       { pos = this.n;
/* 865 */         this.containsNull = true; }
/*     */       
/* 867 */       else if (key[pos = HashCommon.mix(k) & this.mask] != 0) { while (key[pos = pos + 1 & this.mask] != 0); }
/*     */       
/* 869 */       key[pos] = k;
/*     */     } 
/*     */   }
/*     */   
/*     */   private void checkTable() {}
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\i\\unimi\dsi\fastutil\bytes\ByteOpenHashSet.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */