/*     */ package it.unimi.dsi.fastutil.ints;
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
/*     */ import java.util.function.IntConsumer;
/*     */ import java.util.function.Supplier;
/*     */ import java.util.stream.IntStream;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class IntOpenHashSet
/*     */   extends AbstractIntSet
/*     */   implements Serializable, Cloneable, Hash
/*     */ {
/*     */   private static final long serialVersionUID = 0L;
/*     */   private static final boolean ASSERTS = false;
/*     */   protected transient int[] key;
/*     */   protected transient int mask;
/*     */   protected transient boolean containsNull;
/*     */   protected transient int n;
/*     */   protected transient int maxFill;
/*     */   protected final transient int minN;
/*     */   protected int size;
/*     */   protected final float f;
/*     */   
/*     */   public IntOpenHashSet(int expected, float f) {
/*  80 */     if (f <= 0.0F || f >= 1.0F) throw new IllegalArgumentException("Load factor must be greater than 0 and smaller than 1"); 
/*  81 */     if (expected < 0) throw new IllegalArgumentException("The expected number of elements must be nonnegative"); 
/*  82 */     this.f = f;
/*  83 */     this.minN = this.n = HashCommon.arraySize(expected, f);
/*  84 */     this.mask = this.n - 1;
/*  85 */     this.maxFill = HashCommon.maxFill(this.n, f);
/*  86 */     this.key = new int[this.n + 1];
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public IntOpenHashSet(int expected) {
/*  95 */     this(expected, 0.75F);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public IntOpenHashSet() {
/* 103 */     this(16, 0.75F);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public IntOpenHashSet(Collection<? extends Integer> c, float f) {
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
/*     */   public IntOpenHashSet(Collection<? extends Integer> c) {
/* 124 */     this(c, 0.75F);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public IntOpenHashSet(IntCollection c, float f) {
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
/*     */   public IntOpenHashSet(IntCollection c) {
/* 145 */     this(c, 0.75F);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public IntOpenHashSet(IntIterator i, float f) {
/* 155 */     this(16, f);
/* 156 */     for (; i.hasNext(); add(i.nextInt()));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public IntOpenHashSet(IntIterator i) {
/* 166 */     this(i, 0.75F);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public IntOpenHashSet(Iterator<?> i, float f) {
/* 176 */     this(IntIterators.asIntIterator(i), f);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public IntOpenHashSet(Iterator<?> i) {
/* 186 */     this(IntIterators.asIntIterator(i));
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
/*     */   public IntOpenHashSet(int[] a, int offset, int length, float f) {
/* 198 */     this((length < 0) ? 0 : length, f);
/* 199 */     IntArrays.ensureOffsetLength(a, offset, length);
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
/*     */   public IntOpenHashSet(int[] a, int offset, int length) {
/* 212 */     this(a, offset, length, 0.75F);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public IntOpenHashSet(int[] a, float f) {
/* 222 */     this(a, 0, a.length, f);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public IntOpenHashSet(int[] a) {
/* 232 */     this(a, 0.75F);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static IntOpenHashSet of() {
/* 241 */     return new IntOpenHashSet();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static IntOpenHashSet of(int e) {
/* 252 */     IntOpenHashSet result = new IntOpenHashSet(1, 0.75F);
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
/*     */   public static IntOpenHashSet of(int e0, int e1) {
/* 268 */     IntOpenHashSet result = new IntOpenHashSet(2, 0.75F);
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
/*     */   public static IntOpenHashSet of(int e0, int e1, int e2) {
/* 288 */     IntOpenHashSet result = new IntOpenHashSet(3, 0.75F);
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
/*     */   public static IntOpenHashSet of(int... a) {
/* 310 */     IntOpenHashSet result = new IntOpenHashSet(a.length, 0.75F);
/* 311 */     for (int element : a) {
/* 312 */       if (!result.add(element)) {
/* 313 */         throw new IllegalArgumentException("Duplicate element " + element);
/*     */       }
/*     */     } 
/* 316 */     return result;
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
/*     */   public static IntOpenHashSet toSet(IntStream stream) {
/* 330 */     return stream.<IntOpenHashSet>collect(IntOpenHashSet::new, IntOpenHashSet::add, IntOpenHashSet::addAll);
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
/*     */   public static IntOpenHashSet toSetWithExpectedSize(IntStream stream, int expectedSize) {
/* 345 */     if (expectedSize <= 16)
/*     */     {
/*     */       
/* 348 */       return toSet(stream);
/*     */     }
/* 350 */     return stream.<IntOpenHashSet>collect(new IntCollections.SizeDecreasingSupplier<>(expectedSize, size -> (size <= 16) ? new IntOpenHashSet() : new IntOpenHashSet(size)), IntOpenHashSet::add, IntOpenHashSet::addAll);
/*     */   }
/*     */   
/*     */   private int realSize() {
/* 354 */     return this.containsNull ? (this.size - 1) : this.size;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void ensureCapacity(int capacity) {
/* 364 */     int needed = HashCommon.arraySize(capacity, this.f);
/* 365 */     if (needed > this.n) rehash(needed); 
/*     */   }
/*     */   
/*     */   private void tryCapacity(long capacity) {
/* 369 */     int needed = (int)Math.min(1073741824L, Math.max(2L, HashCommon.nextPowerOfTwo((long)Math.ceil(((float)capacity / this.f)))));
/* 370 */     if (needed > this.n) rehash(needed);
/*     */   
/*     */   }
/*     */   
/*     */   public boolean addAll(IntCollection c) {
/* 375 */     if (this.f <= 0.5D) { ensureCapacity(c.size()); }
/* 376 */     else { tryCapacity((size() + c.size())); }
/*     */     
/* 378 */     return super.addAll(c);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean addAll(Collection<? extends Integer> c) {
/* 384 */     if (this.f <= 0.5D) { ensureCapacity(c.size()); }
/* 385 */     else { tryCapacity((size() + c.size())); }
/*     */     
/* 387 */     return super.addAll(c);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean add(int k) {
/* 393 */     if (k == 0) {
/* 394 */       if (this.containsNull) return false; 
/* 395 */       this.containsNull = true;
/*     */     } else {
/*     */       
/* 398 */       int[] key = this.key;
/*     */       int pos, curr;
/* 400 */       if ((curr = key[pos = HashCommon.mix(k) & this.mask]) != 0) {
/* 401 */         if (curr == k) return false; 
/* 402 */         while ((curr = key[pos = pos + 1 & this.mask]) != 0) { if (curr == k) return false;  }
/*     */       
/* 404 */       }  key[pos] = k;
/*     */     } 
/* 406 */     if (this.size++ >= this.maxFill) rehash(HashCommon.arraySize(this.size + 1, this.f));
/*     */     
/* 408 */     return true;
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
/* 421 */     int[] key = this.key; while (true) {
/*     */       int curr, last;
/* 423 */       pos = (last = pos) + 1 & this.mask;
/*     */       while (true) {
/* 425 */         if ((curr = key[pos]) == 0) {
/* 426 */           key[last] = 0;
/*     */           return;
/*     */         } 
/* 429 */         int slot = HashCommon.mix(curr) & this.mask;
/* 430 */         if ((last <= pos) ? (last >= slot || slot > pos) : (last >= slot && slot > pos))
/* 431 */           break;  pos = pos + 1 & this.mask;
/*     */       } 
/* 433 */       key[last] = curr;
/*     */     } 
/*     */   }
/*     */   
/*     */   private boolean removeEntry(int pos) {
/* 438 */     this.size--;
/* 439 */     shiftKeys(pos);
/* 440 */     if (this.n > this.minN && this.size < this.maxFill / 4 && this.n > 16) rehash(this.n / 2); 
/* 441 */     return true;
/*     */   }
/*     */   
/*     */   private boolean removeNullEntry() {
/* 445 */     this.containsNull = false;
/* 446 */     this.key[this.n] = 0;
/* 447 */     this.size--;
/* 448 */     if (this.n > this.minN && this.size < this.maxFill / 4 && this.n > 16) rehash(this.n / 2); 
/* 449 */     return true;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean remove(int k) {
/* 454 */     if (k == 0) {
/* 455 */       if (this.containsNull) return removeNullEntry(); 
/* 456 */       return false;
/*     */     } 
/*     */     
/* 459 */     int[] key = this.key;
/*     */     
/*     */     int curr, pos;
/* 462 */     if ((curr = key[pos = HashCommon.mix(k) & this.mask]) == 0) return false; 
/* 463 */     if (k == curr) return removeEntry(pos); 
/*     */     while (true) {
/* 465 */       if ((curr = key[pos = pos + 1 & this.mask]) == 0) return false; 
/* 466 */       if (k == curr) return removeEntry(pos);
/*     */     
/*     */     } 
/*     */   }
/*     */   
/*     */   public boolean contains(int k) {
/* 472 */     if (k == 0) return this.containsNull;
/*     */     
/* 474 */     int[] key = this.key;
/*     */     
/*     */     int curr, pos;
/* 477 */     if ((curr = key[pos = HashCommon.mix(k) & this.mask]) == 0) return false; 
/* 478 */     if (k == curr) return true; 
/*     */     while (true) {
/* 480 */       if ((curr = key[pos = pos + 1 & this.mask]) == 0) return false; 
/* 481 */       if (k == curr) return true;
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
/* 493 */     if (this.size == 0)
/* 494 */       return;  this.size = 0;
/* 495 */     this.containsNull = false;
/* 496 */     Arrays.fill(this.key, 0);
/*     */   }
/*     */ 
/*     */   
/*     */   public int size() {
/* 501 */     return this.size;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isEmpty() {
/* 506 */     return (this.size == 0);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private final class SetIterator
/*     */     implements IntIterator
/*     */   {
/* 515 */     int pos = IntOpenHashSet.this.n;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 521 */     int last = -1;
/*     */     
/* 523 */     int c = IntOpenHashSet.this.size;
/*     */     
/* 525 */     boolean mustReturnNull = IntOpenHashSet.this.containsNull;
/*     */ 
/*     */ 
/*     */     
/*     */     IntArrayList wrapped;
/*     */ 
/*     */ 
/*     */     
/*     */     public boolean hasNext() {
/* 534 */       return (this.c != 0);
/*     */     }
/*     */ 
/*     */     
/*     */     public int nextInt() {
/* 539 */       if (!hasNext()) throw new NoSuchElementException(); 
/* 540 */       this.c--;
/* 541 */       if (this.mustReturnNull) {
/* 542 */         this.mustReturnNull = false;
/* 543 */         this.last = IntOpenHashSet.this.n;
/* 544 */         return IntOpenHashSet.this.key[IntOpenHashSet.this.n];
/*     */       } 
/* 546 */       int[] key = IntOpenHashSet.this.key;
/*     */       while (true) {
/* 548 */         if (--this.pos < 0) {
/*     */           
/* 550 */           this.last = Integer.MIN_VALUE;
/* 551 */           return this.wrapped.getInt(-this.pos - 1);
/*     */         } 
/* 553 */         if (key[this.pos] != 0) return key[this.last = this.pos];
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
/* 567 */       int[] key = IntOpenHashSet.this.key; while (true) {
/*     */         int curr, last;
/* 569 */         pos = (last = pos) + 1 & IntOpenHashSet.this.mask;
/*     */         while (true) {
/* 571 */           if ((curr = key[pos]) == 0) {
/* 572 */             key[last] = 0;
/*     */             return;
/*     */           } 
/* 575 */           int slot = HashCommon.mix(curr) & IntOpenHashSet.this.mask;
/* 576 */           if ((last <= pos) ? (last >= slot || slot > pos) : (last >= slot && slot > pos))
/* 577 */             break;  pos = pos + 1 & IntOpenHashSet.this.mask;
/*     */         } 
/* 579 */         if (pos < last) {
/* 580 */           if (this.wrapped == null) this.wrapped = new IntArrayList(2); 
/* 581 */           this.wrapped.add(key[pos]);
/*     */         } 
/* 583 */         key[last] = curr;
/*     */       } 
/*     */     }
/*     */ 
/*     */     
/*     */     public void remove() {
/* 589 */       if (this.last == -1) throw new IllegalStateException(); 
/* 590 */       if (this.last == IntOpenHashSet.this.n)
/* 591 */       { IntOpenHashSet.this.containsNull = false;
/* 592 */         IntOpenHashSet.this.key[IntOpenHashSet.this.n] = 0; }
/* 593 */       else if (this.pos >= 0) { shiftKeys(this.last); }
/*     */       else
/*     */       
/* 596 */       { IntOpenHashSet.this.remove(this.wrapped.getInt(-this.pos - 1));
/* 597 */         this.last = -1;
/*     */         return; }
/*     */       
/* 600 */       IntOpenHashSet.this.size--;
/* 601 */       this.last = -1;
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     public void forEachRemaining(IntConsumer action) {
/* 607 */       int[] key = IntOpenHashSet.this.key;
/* 608 */       if (this.mustReturnNull) {
/* 609 */         this.mustReturnNull = false;
/* 610 */         this.last = IntOpenHashSet.this.n;
/* 611 */         action.accept(key[IntOpenHashSet.this.n]);
/* 612 */         this.c--;
/*     */       } 
/* 614 */       while (this.c != 0) {
/* 615 */         if (--this.pos < 0) {
/*     */           
/* 617 */           this.last = Integer.MIN_VALUE;
/* 618 */           action.accept(this.wrapped.getInt(-this.pos - 1));
/* 619 */           this.c--; continue;
/* 620 */         }  if (key[this.pos] != 0) {
/* 621 */           action.accept(key[this.last = this.pos]);
/* 622 */           this.c--;
/*     */         } 
/*     */       } 
/*     */     }
/*     */     
/*     */     private SetIterator() {} }
/*     */   
/*     */   public IntIterator iterator() {
/* 630 */     return new SetIterator();
/*     */   }
/*     */ 
/*     */   
/*     */   private final class SetSpliterator
/*     */     implements IntSpliterator
/*     */   {
/*     */     private static final int POST_SPLIT_CHARACTERISTICS = 257;
/*     */     
/* 639 */     int pos = 0;
/*     */     
/* 641 */     int max = IntOpenHashSet.this.n;
/*     */     
/* 643 */     int c = 0;
/*     */     
/* 645 */     boolean mustReturnNull = IntOpenHashSet.this.containsNull;
/*     */ 
/*     */     
/*     */     boolean hasSplit = false;
/*     */ 
/*     */     
/*     */     SetSpliterator(int pos, int max, boolean mustReturnNull, boolean hasSplit) {
/* 652 */       this.pos = pos;
/* 653 */       this.max = max;
/* 654 */       this.mustReturnNull = mustReturnNull;
/* 655 */       this.hasSplit = hasSplit;
/*     */     }
/*     */ 
/*     */     
/*     */     public boolean tryAdvance(IntConsumer action) {
/* 660 */       if (this.mustReturnNull) {
/* 661 */         this.mustReturnNull = false;
/* 662 */         this.c++;
/* 663 */         action.accept(IntOpenHashSet.this.key[IntOpenHashSet.this.n]);
/* 664 */         return true;
/*     */       } 
/* 666 */       int[] key = IntOpenHashSet.this.key;
/* 667 */       while (this.pos < this.max) {
/* 668 */         if (key[this.pos] != 0) {
/* 669 */           this.c++;
/* 670 */           action.accept(key[this.pos++]);
/* 671 */           return true;
/*     */         } 
/* 673 */         this.pos++;
/*     */       } 
/*     */       
/* 676 */       return false;
/*     */     }
/*     */ 
/*     */     
/*     */     public void forEachRemaining(IntConsumer action) {
/* 681 */       int[] key = IntOpenHashSet.this.key;
/* 682 */       if (this.mustReturnNull) {
/* 683 */         this.mustReturnNull = false;
/* 684 */         action.accept(key[IntOpenHashSet.this.n]);
/* 685 */         this.c++;
/*     */       } 
/* 687 */       while (this.pos < this.max) {
/* 688 */         if (key[this.pos] != 0) {
/* 689 */           action.accept(key[this.pos]);
/* 690 */           this.c++;
/*     */         } 
/* 692 */         this.pos++;
/*     */       } 
/*     */     }
/*     */ 
/*     */     
/*     */     public int characteristics() {
/* 698 */       return this.hasSplit ? 257 : 321;
/*     */     }
/*     */ 
/*     */     
/*     */     public long estimateSize() {
/* 703 */       if (!this.hasSplit)
/*     */       {
/* 705 */         return (IntOpenHashSet.this.size - this.c);
/*     */       }
/*     */ 
/*     */ 
/*     */       
/* 710 */       return Math.min((IntOpenHashSet.this.size - this.c), (long)(IntOpenHashSet.this.realSize() / IntOpenHashSet.this.n * (this.max - this.pos)) + (this.mustReturnNull ? 1L : 0L));
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     public SetSpliterator trySplit() {
/* 716 */       if (this.pos >= this.max - 1) return null; 
/* 717 */       int retLen = this.max - this.pos >> 1;
/* 718 */       if (retLen <= 1) return null; 
/* 719 */       int myNewPos = this.pos + retLen;
/* 720 */       int retPos = this.pos;
/* 721 */       int retMax = myNewPos;
/*     */ 
/*     */ 
/*     */       
/* 725 */       SetSpliterator split = new SetSpliterator(retPos, retMax, this.mustReturnNull, true);
/* 726 */       this.pos = myNewPos;
/* 727 */       this.mustReturnNull = false;
/* 728 */       this.hasSplit = true;
/* 729 */       return split;
/*     */     }
/*     */ 
/*     */     
/*     */     public long skip(long n) {
/* 734 */       if (n < 0L) throw new IllegalArgumentException("Argument must be nonnegative: " + n); 
/* 735 */       if (n == 0L) return 0L; 
/* 736 */       long skipped = 0L;
/* 737 */       if (this.mustReturnNull) {
/* 738 */         this.mustReturnNull = false;
/* 739 */         skipped++;
/* 740 */         n--;
/*     */       } 
/* 742 */       int[] key = IntOpenHashSet.this.key;
/* 743 */       while (this.pos < this.max && n > 0L) {
/* 744 */         if (key[this.pos++] != 0) {
/* 745 */           skipped++;
/* 746 */           n--;
/*     */         } 
/*     */       } 
/* 749 */       return skipped;
/*     */     }
/*     */     
/*     */     SetSpliterator() {} }
/*     */   
/*     */   public IntSpliterator spliterator() {
/* 755 */     return new SetSpliterator();
/*     */   }
/*     */ 
/*     */   
/*     */   public void forEach(IntConsumer action) {
/* 760 */     if (this.containsNull) action.accept(this.key[this.n]); 
/* 761 */     int[] key = this.key;
/* 762 */     for (int pos = this.n; pos-- != 0;) { if (key[pos] != 0) action.accept(key[pos]);
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
/* 779 */     return trim(this.size);
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
/* 801 */     int l = HashCommon.nextPowerOfTwo((int)Math.ceil((n / this.f)));
/* 802 */     if (l >= this.n || this.size > HashCommon.maxFill(l, this.f)) return true; 
/*     */     try {
/* 804 */       rehash(l);
/* 805 */     } catch (OutOfMemoryError cantDoIt) {
/* 806 */       return false;
/*     */     } 
/* 808 */     return true;
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
/* 823 */     int[] key = this.key;
/* 824 */     int mask = newN - 1;
/* 825 */     int[] newKey = new int[newN + 1];
/* 826 */     int i = this.n;
/* 827 */     for (int j = realSize(); j-- != 0; ) {
/* 828 */       while (key[--i] == 0); int pos;
/* 829 */       if (newKey[pos = HashCommon.mix(key[i]) & mask] != 0) while (newKey[pos = pos + 1 & mask] != 0); 
/* 830 */       newKey[pos] = key[i];
/*     */     } 
/* 832 */     this.n = newN;
/* 833 */     this.mask = mask;
/* 834 */     this.maxFill = HashCommon.maxFill(this.n, this.f);
/* 835 */     this.key = newKey;
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
/*     */   public IntOpenHashSet clone() {
/*     */     IntOpenHashSet c;
/*     */     try {
/* 852 */       c = (IntOpenHashSet)super.clone();
/* 853 */     } catch (CloneNotSupportedException cantHappen) {
/* 854 */       throw new InternalError();
/*     */     } 
/* 856 */     c.key = (int[])this.key.clone();
/* 857 */     c.containsNull = this.containsNull;
/* 858 */     return c;
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
/* 872 */     int h = 0;
/* 873 */     for (int j = realSize(), i = 0; j-- != 0; ) {
/* 874 */       for (; this.key[i] == 0; i++);
/* 875 */       h += this.key[i];
/* 876 */       i++;
/*     */     } 
/*     */     
/* 879 */     return h;
/*     */   }
/*     */   
/*     */   private void writeObject(ObjectOutputStream s) throws IOException {
/* 883 */     IntIterator i = iterator();
/* 884 */     s.defaultWriteObject();
/* 885 */     for (int j = this.size; j-- != 0; s.writeInt(i.nextInt()));
/*     */   }
/*     */   
/*     */   private void readObject(ObjectInputStream s) throws IOException, ClassNotFoundException {
/* 889 */     s.defaultReadObject();
/* 890 */     this.n = HashCommon.arraySize(this.size, this.f);
/* 891 */     this.maxFill = HashCommon.maxFill(this.n, this.f);
/* 892 */     this.mask = this.n - 1;
/* 893 */     int[] key = this.key = new int[this.n + 1];
/*     */     
/* 895 */     for (int i = this.size; i-- != 0; ) {
/* 896 */       int pos, k = s.readInt();
/* 897 */       if (k == 0)
/* 898 */       { pos = this.n;
/* 899 */         this.containsNull = true; }
/*     */       
/* 901 */       else if (key[pos = HashCommon.mix(k) & this.mask] != 0) { while (key[pos = pos + 1 & this.mask] != 0); }
/*     */       
/* 903 */       key[pos] = k;
/*     */     } 
/*     */   }
/*     */   
/*     */   private void checkTable() {}
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\i\\unimi\dsi\fastutil\ints\IntOpenHashSet.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */