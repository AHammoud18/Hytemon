/*     */ package it.unimi.dsi.fastutil.objects;
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
/*     */ 
/*     */ public class ReferenceOpenHashSet<K>
/*     */   extends AbstractReferenceSet<K>
/*     */   implements Serializable, Cloneable, Hash
/*     */ {
/*     */   private static final long serialVersionUID = 0L;
/*     */   private static final boolean ASSERTS = false;
/*     */   protected transient K[] key;
/*     */   protected transient int mask;
/*     */   protected transient boolean containsNull;
/*     */   protected transient int n;
/*     */   protected transient int maxFill;
/*     */   protected final transient int minN;
/*     */   protected int size;
/*     */   protected final float f;
/*     */   
/*     */   public ReferenceOpenHashSet(int expected, float f) {
/*  82 */     if (f <= 0.0F || f >= 1.0F) throw new IllegalArgumentException("Load factor must be greater than 0 and smaller than 1"); 
/*  83 */     if (expected < 0) throw new IllegalArgumentException("The expected number of elements must be nonnegative"); 
/*  84 */     this.f = f;
/*  85 */     this.minN = this.n = HashCommon.arraySize(expected, f);
/*  86 */     this.mask = this.n - 1;
/*  87 */     this.maxFill = HashCommon.maxFill(this.n, f);
/*  88 */     this.key = (K[])new Object[this.n + 1];
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ReferenceOpenHashSet(int expected) {
/*  97 */     this(expected, 0.75F);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ReferenceOpenHashSet() {
/* 105 */     this(16, 0.75F);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ReferenceOpenHashSet(Collection<? extends K> c, float f) {
/* 115 */     this(c.size(), f);
/* 116 */     addAll(c);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ReferenceOpenHashSet(Collection<? extends K> c) {
/* 126 */     this(c, 0.75F);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ReferenceOpenHashSet(ReferenceCollection<? extends K> c, float f) {
/* 136 */     this(c.size(), f);
/* 137 */     addAll(c);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ReferenceOpenHashSet(ReferenceCollection<? extends K> c) {
/* 147 */     this(c, 0.75F);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ReferenceOpenHashSet(Iterator<? extends K> i, float f) {
/* 157 */     this(16, f);
/* 158 */     for (; i.hasNext(); add(i.next()));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ReferenceOpenHashSet(Iterator<? extends K> i) {
/* 168 */     this(i, 0.75F);
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
/*     */   public ReferenceOpenHashSet(K[] a, int offset, int length, float f) {
/* 180 */     this((length < 0) ? 0 : length, f);
/* 181 */     ObjectArrays.ensureOffsetLength(a, offset, length);
/* 182 */     for (int i = 0; i < length; ) { add(a[offset + i]); i++; }
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
/*     */   public ReferenceOpenHashSet(K[] a, int offset, int length) {
/* 194 */     this(a, offset, length, 0.75F);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ReferenceOpenHashSet(K[] a, float f) {
/* 204 */     this(a, 0, a.length, f);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ReferenceOpenHashSet(K[] a) {
/* 214 */     this(a, 0.75F);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static <K> ReferenceOpenHashSet<K> of() {
/* 223 */     return new ReferenceOpenHashSet<>();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static <K> ReferenceOpenHashSet<K> of(K e) {
/* 234 */     ReferenceOpenHashSet<K> result = new ReferenceOpenHashSet<>(1, 0.75F);
/* 235 */     result.add(e);
/* 236 */     return result;
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
/*     */   public static <K> ReferenceOpenHashSet<K> of(K e0, K e1) {
/* 250 */     ReferenceOpenHashSet<K> result = new ReferenceOpenHashSet<>(2, 0.75F);
/* 251 */     result.add(e0);
/* 252 */     if (!result.add(e1)) {
/* 253 */       throw new IllegalArgumentException("Duplicate element: " + e1);
/*     */     }
/* 255 */     return result;
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
/*     */   public static <K> ReferenceOpenHashSet<K> of(K e0, K e1, K e2) {
/* 270 */     ReferenceOpenHashSet<K> result = new ReferenceOpenHashSet<>(3, 0.75F);
/* 271 */     result.add(e0);
/* 272 */     if (!result.add(e1)) {
/* 273 */       throw new IllegalArgumentException("Duplicate element: " + e1);
/*     */     }
/* 275 */     if (!result.add(e2)) {
/* 276 */       throw new IllegalArgumentException("Duplicate element: " + e2);
/*     */     }
/* 278 */     return result;
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
/*     */   @SafeVarargs
/*     */   public static <K> ReferenceOpenHashSet<K> of(K... a) {
/* 292 */     ReferenceOpenHashSet<K> result = new ReferenceOpenHashSet<>(a.length, 0.75F);
/* 293 */     for (K element : a) {
/* 294 */       if (!result.add(element)) {
/* 295 */         throw new IllegalArgumentException("Duplicate element " + element);
/*     */       }
/*     */     } 
/* 298 */     return result;
/*     */   }
/*     */ 
/*     */   
/*     */   private ReferenceOpenHashSet<K> combine(ReferenceOpenHashSet<? extends K> toAddFrom) {
/* 303 */     addAll(toAddFrom);
/* 304 */     return this;
/*     */   }
/*     */   
/* 307 */   private static final Collector<Object, ?, ReferenceOpenHashSet<Object>> TO_SET_COLLECTOR = (Collector)Collector.of(ReferenceOpenHashSet::new, ReferenceOpenHashSet::add, ReferenceOpenHashSet::combine, new Collector.Characteristics[] { Collector.Characteristics.UNORDERED });
/*     */ 
/*     */ 
/*     */   
/*     */   public static <K> Collector<K, ?, ReferenceOpenHashSet<K>> toSet() {
/* 312 */     return (Collector)TO_SET_COLLECTOR;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static <K> Collector<K, ?, ReferenceOpenHashSet<K>> toSetWithExpectedSize(int expectedSize) {
/* 320 */     if (expectedSize <= 16)
/*     */     {
/*     */       
/* 323 */       return toSet();
/*     */     }
/* 325 */     return (Collector)Collector.of(new ReferenceCollections.SizeDecreasingSupplier<>(expectedSize, size -> (size <= 16) ? new ReferenceOpenHashSet() : new ReferenceOpenHashSet(size)), ReferenceOpenHashSet::add, ReferenceOpenHashSet::combine, new Collector.Characteristics[] { Collector.Characteristics.UNORDERED });
/*     */   }
/*     */   
/*     */   private int realSize() {
/* 329 */     return this.containsNull ? (this.size - 1) : this.size;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void ensureCapacity(int capacity) {
/* 339 */     int needed = HashCommon.arraySize(capacity, this.f);
/* 340 */     if (needed > this.n) rehash(needed); 
/*     */   }
/*     */   
/*     */   private void tryCapacity(long capacity) {
/* 344 */     int needed = (int)Math.min(1073741824L, Math.max(2L, HashCommon.nextPowerOfTwo((long)Math.ceil(((float)capacity / this.f)))));
/* 345 */     if (needed > this.n) rehash(needed);
/*     */   
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean addAll(Collection<? extends K> c) {
/* 351 */     if (this.f <= 0.5D) { ensureCapacity(c.size()); }
/* 352 */     else { tryCapacity((size() + c.size())); }
/*     */     
/* 354 */     return super.addAll(c);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean add(K k) {
/* 360 */     if (k == null) {
/* 361 */       if (this.containsNull) return false; 
/* 362 */       this.containsNull = true;
/*     */     } else {
/*     */       
/* 365 */       K[] key = this.key; int pos;
/*     */       K curr;
/* 367 */       if ((curr = key[pos = HashCommon.mix(System.identityHashCode(k)) & this.mask]) != null) {
/* 368 */         if (curr == k) return false; 
/* 369 */         while ((curr = key[pos = pos + 1 & this.mask]) != null) { if (curr == k) return false;  }
/*     */       
/* 371 */       }  key[pos] = k;
/*     */     } 
/* 373 */     if (this.size++ >= this.maxFill) rehash(HashCommon.arraySize(this.size + 1, this.f));
/*     */     
/* 375 */     return true;
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
/* 388 */     K[] key = this.key; while (true) {
/*     */       K curr; int last;
/* 390 */       pos = (last = pos) + 1 & this.mask;
/*     */       while (true) {
/* 392 */         if ((curr = key[pos]) == null) {
/* 393 */           key[last] = null;
/*     */           return;
/*     */         } 
/* 396 */         int slot = HashCommon.mix(System.identityHashCode(curr)) & this.mask;
/* 397 */         if ((last <= pos) ? (last >= slot || slot > pos) : (last >= slot && slot > pos))
/* 398 */           break;  pos = pos + 1 & this.mask;
/*     */       } 
/* 400 */       key[last] = curr;
/*     */     } 
/*     */   }
/*     */   
/*     */   private boolean removeEntry(int pos) {
/* 405 */     this.size--;
/* 406 */     shiftKeys(pos);
/* 407 */     if (this.n > this.minN && this.size < this.maxFill / 4 && this.n > 16) rehash(this.n / 2); 
/* 408 */     return true;
/*     */   }
/*     */   
/*     */   private boolean removeNullEntry() {
/* 412 */     this.containsNull = false;
/* 413 */     this.key[this.n] = null;
/* 414 */     this.size--;
/* 415 */     if (this.n > this.minN && this.size < this.maxFill / 4 && this.n > 16) rehash(this.n / 2); 
/* 416 */     return true;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean remove(Object k) {
/* 422 */     if (k == null) {
/* 423 */       if (this.containsNull) return removeNullEntry(); 
/* 424 */       return false;
/*     */     } 
/*     */     
/* 427 */     K[] key = this.key;
/*     */     K curr;
/*     */     int pos;
/* 430 */     if ((curr = key[pos = HashCommon.mix(System.identityHashCode(k)) & this.mask]) == null) return false; 
/* 431 */     if (k == curr) return removeEntry(pos); 
/*     */     while (true) {
/* 433 */       if ((curr = key[pos = pos + 1 & this.mask]) == null) return false; 
/* 434 */       if (k == curr) return removeEntry(pos);
/*     */     
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean contains(Object k) {
/* 441 */     if (k == null) return this.containsNull;
/*     */     
/* 443 */     K[] key = this.key;
/*     */     K curr;
/*     */     int pos;
/* 446 */     if ((curr = key[pos = HashCommon.mix(System.identityHashCode(k)) & this.mask]) == null) return false; 
/* 447 */     if (k == curr) return true; 
/*     */     while (true) {
/* 449 */       if ((curr = key[pos = pos + 1 & this.mask]) == null) return false; 
/* 450 */       if (k == curr) return true;
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
/* 462 */     if (this.size == 0)
/* 463 */       return;  this.size = 0;
/* 464 */     this.containsNull = false;
/* 465 */     Arrays.fill((Object[])this.key, (Object)null);
/*     */   }
/*     */ 
/*     */   
/*     */   public int size() {
/* 470 */     return this.size;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isEmpty() {
/* 475 */     return (this.size == 0);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private final class SetIterator
/*     */     implements ObjectIterator<K>
/*     */   {
/* 484 */     int pos = ReferenceOpenHashSet.this.n;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 490 */     int last = -1;
/*     */     
/* 492 */     int c = ReferenceOpenHashSet.this.size;
/*     */     
/* 494 */     boolean mustReturnNull = ReferenceOpenHashSet.this.containsNull;
/*     */ 
/*     */ 
/*     */     
/*     */     ReferenceArrayList<K> wrapped;
/*     */ 
/*     */ 
/*     */     
/*     */     public boolean hasNext() {
/* 503 */       return (this.c != 0);
/*     */     }
/*     */ 
/*     */     
/*     */     public K next() {
/* 508 */       if (!hasNext()) throw new NoSuchElementException(); 
/* 509 */       this.c--;
/* 510 */       if (this.mustReturnNull) {
/* 511 */         this.mustReturnNull = false;
/* 512 */         this.last = ReferenceOpenHashSet.this.n;
/* 513 */         return ReferenceOpenHashSet.this.key[ReferenceOpenHashSet.this.n];
/*     */       } 
/* 515 */       K[] key = ReferenceOpenHashSet.this.key;
/*     */       while (true) {
/* 517 */         if (--this.pos < 0) {
/*     */           
/* 519 */           this.last = Integer.MIN_VALUE;
/* 520 */           return this.wrapped.get(-this.pos - 1);
/*     */         } 
/* 522 */         if (key[this.pos] != null) return key[this.last = this.pos];
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
/* 536 */       K[] key = ReferenceOpenHashSet.this.key; while (true) {
/*     */         K curr; int last;
/* 538 */         pos = (last = pos) + 1 & ReferenceOpenHashSet.this.mask;
/*     */         while (true) {
/* 540 */           if ((curr = key[pos]) == null) {
/* 541 */             key[last] = null;
/*     */             return;
/*     */           } 
/* 544 */           int slot = HashCommon.mix(System.identityHashCode(curr)) & ReferenceOpenHashSet.this.mask;
/* 545 */           if ((last <= pos) ? (last >= slot || slot > pos) : (last >= slot && slot > pos))
/* 546 */             break;  pos = pos + 1 & ReferenceOpenHashSet.this.mask;
/*     */         } 
/* 548 */         if (pos < last) {
/* 549 */           if (this.wrapped == null) this.wrapped = new ReferenceArrayList<>(2); 
/* 550 */           this.wrapped.add(key[pos]);
/*     */         } 
/* 552 */         key[last] = curr;
/*     */       } 
/*     */     }
/*     */ 
/*     */     
/*     */     public void remove() {
/* 558 */       if (this.last == -1) throw new IllegalStateException(); 
/* 559 */       if (this.last == ReferenceOpenHashSet.this.n)
/* 560 */       { ReferenceOpenHashSet.this.containsNull = false;
/* 561 */         ReferenceOpenHashSet.this.key[ReferenceOpenHashSet.this.n] = null; }
/* 562 */       else if (this.pos >= 0) { shiftKeys(this.last); }
/*     */       else
/*     */       
/* 565 */       { ReferenceOpenHashSet.this.remove(this.wrapped.set(-this.pos - 1, null));
/* 566 */         this.last = -1;
/*     */         return; }
/*     */       
/* 569 */       ReferenceOpenHashSet.this.size--;
/* 570 */       this.last = -1;
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     public void forEachRemaining(Consumer<? super K> action) {
/* 576 */       K[] key = ReferenceOpenHashSet.this.key;
/* 577 */       if (this.mustReturnNull) {
/* 578 */         this.mustReturnNull = false;
/* 579 */         this.last = ReferenceOpenHashSet.this.n;
/* 580 */         action.accept(key[ReferenceOpenHashSet.this.n]);
/* 581 */         this.c--;
/*     */       } 
/* 583 */       while (this.c != 0) {
/* 584 */         if (--this.pos < 0) {
/*     */           
/* 586 */           this.last = Integer.MIN_VALUE;
/* 587 */           action.accept(this.wrapped.get(-this.pos - 1));
/* 588 */           this.c--; continue;
/* 589 */         }  if (key[this.pos] != null) {
/* 590 */           action.accept(key[this.last = this.pos]);
/* 591 */           this.c--;
/*     */         } 
/*     */       } 
/*     */     }
/*     */     
/*     */     private SetIterator() {} }
/*     */   
/*     */   public ObjectIterator<K> iterator() {
/* 599 */     return new SetIterator();
/*     */   }
/*     */ 
/*     */   
/*     */   private final class SetSpliterator
/*     */     implements ObjectSpliterator<K>
/*     */   {
/*     */     private static final int POST_SPLIT_CHARACTERISTICS = 1;
/*     */     
/* 608 */     int pos = 0;
/*     */     
/* 610 */     int max = ReferenceOpenHashSet.this.n;
/*     */     
/* 612 */     int c = 0;
/*     */     
/* 614 */     boolean mustReturnNull = ReferenceOpenHashSet.this.containsNull;
/*     */ 
/*     */     
/*     */     boolean hasSplit = false;
/*     */ 
/*     */     
/*     */     SetSpliterator(int pos, int max, boolean mustReturnNull, boolean hasSplit) {
/* 621 */       this.pos = pos;
/* 622 */       this.max = max;
/* 623 */       this.mustReturnNull = mustReturnNull;
/* 624 */       this.hasSplit = hasSplit;
/*     */     }
/*     */ 
/*     */     
/*     */     public boolean tryAdvance(Consumer<? super K> action) {
/* 629 */       if (this.mustReturnNull) {
/* 630 */         this.mustReturnNull = false;
/* 631 */         this.c++;
/* 632 */         action.accept(ReferenceOpenHashSet.this.key[ReferenceOpenHashSet.this.n]);
/* 633 */         return true;
/*     */       } 
/* 635 */       K[] key = ReferenceOpenHashSet.this.key;
/* 636 */       while (this.pos < this.max) {
/* 637 */         if (key[this.pos] != null) {
/* 638 */           this.c++;
/* 639 */           action.accept(key[this.pos++]);
/* 640 */           return true;
/*     */         } 
/* 642 */         this.pos++;
/*     */       } 
/*     */       
/* 645 */       return false;
/*     */     }
/*     */ 
/*     */     
/*     */     public void forEachRemaining(Consumer<? super K> action) {
/* 650 */       K[] key = ReferenceOpenHashSet.this.key;
/* 651 */       if (this.mustReturnNull) {
/* 652 */         this.mustReturnNull = false;
/* 653 */         action.accept(key[ReferenceOpenHashSet.this.n]);
/* 654 */         this.c++;
/*     */       } 
/* 656 */       while (this.pos < this.max) {
/* 657 */         if (key[this.pos] != null) {
/* 658 */           action.accept(key[this.pos]);
/* 659 */           this.c++;
/*     */         } 
/* 661 */         this.pos++;
/*     */       } 
/*     */     }
/*     */ 
/*     */     
/*     */     public int characteristics() {
/* 667 */       return this.hasSplit ? 1 : 65;
/*     */     }
/*     */ 
/*     */     
/*     */     public long estimateSize() {
/* 672 */       if (!this.hasSplit)
/*     */       {
/* 674 */         return (ReferenceOpenHashSet.this.size - this.c);
/*     */       }
/*     */ 
/*     */ 
/*     */       
/* 679 */       return Math.min((ReferenceOpenHashSet.this.size - this.c), (long)(ReferenceOpenHashSet.this.realSize() / ReferenceOpenHashSet.this.n * (this.max - this.pos)) + (this.mustReturnNull ? 1L : 0L));
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     public SetSpliterator trySplit() {
/* 685 */       if (this.pos >= this.max - 1) return null; 
/* 686 */       int retLen = this.max - this.pos >> 1;
/* 687 */       if (retLen <= 1) return null; 
/* 688 */       int myNewPos = this.pos + retLen;
/* 689 */       int retPos = this.pos;
/* 690 */       int retMax = myNewPos;
/*     */ 
/*     */ 
/*     */       
/* 694 */       SetSpliterator split = new SetSpliterator(retPos, retMax, this.mustReturnNull, true);
/* 695 */       this.pos = myNewPos;
/* 696 */       this.mustReturnNull = false;
/* 697 */       this.hasSplit = true;
/* 698 */       return split;
/*     */     }
/*     */ 
/*     */     
/*     */     public long skip(long n) {
/* 703 */       if (n < 0L) throw new IllegalArgumentException("Argument must be nonnegative: " + n); 
/* 704 */       if (n == 0L) return 0L; 
/* 705 */       long skipped = 0L;
/* 706 */       if (this.mustReturnNull) {
/* 707 */         this.mustReturnNull = false;
/* 708 */         skipped++;
/* 709 */         n--;
/*     */       } 
/* 711 */       K[] key = ReferenceOpenHashSet.this.key;
/* 712 */       while (this.pos < this.max && n > 0L) {
/* 713 */         if (key[this.pos++] != null) {
/* 714 */           skipped++;
/* 715 */           n--;
/*     */         } 
/*     */       } 
/* 718 */       return skipped;
/*     */     }
/*     */     
/*     */     SetSpliterator() {} }
/*     */   
/*     */   public ObjectSpliterator<K> spliterator() {
/* 724 */     return new SetSpliterator();
/*     */   }
/*     */ 
/*     */   
/*     */   public void forEach(Consumer<? super K> action) {
/* 729 */     if (this.containsNull) action.accept(this.key[this.n]); 
/* 730 */     K[] key = this.key;
/* 731 */     for (int pos = this.n; pos-- != 0;) { if (key[pos] != null) action.accept(key[pos]);
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
/* 748 */     return trim(this.size);
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
/* 770 */     int l = HashCommon.nextPowerOfTwo((int)Math.ceil((n / this.f)));
/* 771 */     if (l >= this.n || this.size > HashCommon.maxFill(l, this.f)) return true; 
/*     */     try {
/* 773 */       rehash(l);
/* 774 */     } catch (OutOfMemoryError cantDoIt) {
/* 775 */       return false;
/*     */     } 
/* 777 */     return true;
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
/* 792 */     K[] key = this.key;
/* 793 */     int mask = newN - 1;
/* 794 */     K[] newKey = (K[])new Object[newN + 1];
/* 795 */     int i = this.n;
/* 796 */     for (int j = realSize(); j-- != 0; ) {
/* 797 */       while (key[--i] == null); int pos;
/* 798 */       if (newKey[pos = HashCommon.mix(System.identityHashCode(key[i])) & mask] != null) while (newKey[pos = pos + 1 & mask] != null); 
/* 799 */       newKey[pos] = key[i];
/*     */     } 
/* 801 */     this.n = newN;
/* 802 */     this.mask = mask;
/* 803 */     this.maxFill = HashCommon.maxFill(this.n, this.f);
/* 804 */     this.key = newKey;
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
/*     */   public ReferenceOpenHashSet<K> clone() {
/*     */     ReferenceOpenHashSet<K> c;
/*     */     try {
/* 821 */       c = (ReferenceOpenHashSet<K>)super.clone();
/* 822 */     } catch (CloneNotSupportedException cantHappen) {
/* 823 */       throw new InternalError();
/*     */     } 
/* 825 */     c.key = (K[])this.key.clone();
/* 826 */     c.containsNull = this.containsNull;
/* 827 */     return c;
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
/* 841 */     int h = 0;
/* 842 */     for (int j = realSize(), i = 0; j-- != 0; ) {
/* 843 */       for (; this.key[i] == null; i++);
/* 844 */       if (this != this.key[i]) h += System.identityHashCode(this.key[i]); 
/* 845 */       i++;
/*     */     } 
/*     */     
/* 848 */     return h;
/*     */   }
/*     */   
/*     */   private void writeObject(ObjectOutputStream s) throws IOException {
/* 852 */     ObjectIterator<K> i = iterator();
/* 853 */     s.defaultWriteObject();
/* 854 */     for (int j = this.size; j-- != 0; s.writeObject(i.next()));
/*     */   }
/*     */ 
/*     */   
/*     */   private void readObject(ObjectInputStream s) throws IOException, ClassNotFoundException {
/* 859 */     s.defaultReadObject();
/* 860 */     this.n = HashCommon.arraySize(this.size, this.f);
/* 861 */     this.maxFill = HashCommon.maxFill(this.n, this.f);
/* 862 */     this.mask = this.n - 1;
/* 863 */     K[] key = this.key = (K[])new Object[this.n + 1];
/*     */     
/* 865 */     for (int i = this.size; i-- != 0; ) {
/* 866 */       int pos; K k = (K)s.readObject();
/* 867 */       if (k == null)
/* 868 */       { pos = this.n;
/* 869 */         this.containsNull = true; }
/*     */       
/* 871 */       else if (key[pos = HashCommon.mix(System.identityHashCode(k)) & this.mask] != null) { while (key[pos = pos + 1 & this.mask] != null); }
/*     */       
/* 873 */       key[pos] = k;
/*     */     } 
/*     */   }
/*     */   
/*     */   private void checkTable() {}
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\i\\unimi\dsi\fastutil\objects\ReferenceOpenHashSet.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */