/*     */ package it.unimi.dsi.fastutil.shorts;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ public class ShortOpenCustomHashSet
/*     */   extends AbstractShortSet
/*     */   implements Serializable, Cloneable, Hash
/*     */ {
/*     */   private static final long serialVersionUID = 0L;
/*     */   private static final boolean ASSERTS = false;
/*     */   protected transient short[] key;
/*     */   protected transient int mask;
/*     */   protected transient boolean containsNull;
/*     */   protected ShortHash.Strategy strategy;
/*     */   protected transient int n;
/*     */   protected transient int maxFill;
/*     */   protected final transient int minN;
/*     */   protected int size;
/*     */   protected final float f;
/*     */   
/*     */   public ShortOpenCustomHashSet(int expected, float f, ShortHash.Strategy strategy) {
/*  84 */     this.strategy = strategy;
/*  85 */     if (f <= 0.0F || f >= 1.0F) throw new IllegalArgumentException("Load factor must be greater than 0 and smaller than 1"); 
/*  86 */     if (expected < 0) throw new IllegalArgumentException("The expected number of elements must be nonnegative"); 
/*  87 */     this.f = f;
/*  88 */     this.minN = this.n = HashCommon.arraySize(expected, f);
/*  89 */     this.mask = this.n - 1;
/*  90 */     this.maxFill = HashCommon.maxFill(this.n, f);
/*  91 */     this.key = new short[this.n + 1];
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ShortOpenCustomHashSet(int expected, ShortHash.Strategy strategy) {
/* 101 */     this(expected, 0.75F, strategy);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ShortOpenCustomHashSet(ShortHash.Strategy strategy) {
/* 111 */     this(16, 0.75F, strategy);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ShortOpenCustomHashSet(Collection<? extends Short> c, float f, ShortHash.Strategy strategy) {
/* 122 */     this(c.size(), f, strategy);
/* 123 */     addAll(c);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ShortOpenCustomHashSet(Collection<? extends Short> c, ShortHash.Strategy strategy) {
/* 134 */     this(c, 0.75F, strategy);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ShortOpenCustomHashSet(ShortCollection c, float f, ShortHash.Strategy strategy) {
/* 145 */     this(c.size(), f, strategy);
/* 146 */     addAll(c);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ShortOpenCustomHashSet(ShortCollection c, ShortHash.Strategy strategy) {
/* 157 */     this(c, 0.75F, strategy);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ShortOpenCustomHashSet(ShortIterator i, float f, ShortHash.Strategy strategy) {
/* 168 */     this(16, f, strategy);
/* 169 */     for (; i.hasNext(); add(i.nextShort()));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ShortOpenCustomHashSet(ShortIterator i, ShortHash.Strategy strategy) {
/* 180 */     this(i, 0.75F, strategy);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ShortOpenCustomHashSet(Iterator<?> i, float f, ShortHash.Strategy strategy) {
/* 191 */     this(ShortIterators.asShortIterator(i), f, strategy);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ShortOpenCustomHashSet(Iterator<?> i, ShortHash.Strategy strategy) {
/* 202 */     this(ShortIterators.asShortIterator(i), strategy);
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
/*     */   public ShortOpenCustomHashSet(short[] a, int offset, int length, float f, ShortHash.Strategy strategy) {
/* 215 */     this((length < 0) ? 0 : length, f, strategy);
/* 216 */     ShortArrays.ensureOffsetLength(a, offset, length);
/* 217 */     for (int i = 0; i < length; ) { add(a[offset + i]); i++; }
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
/*     */   public ShortOpenCustomHashSet(short[] a, int offset, int length, ShortHash.Strategy strategy) {
/* 230 */     this(a, offset, length, 0.75F, strategy);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ShortOpenCustomHashSet(short[] a, float f, ShortHash.Strategy strategy) {
/* 241 */     this(a, 0, a.length, f, strategy);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ShortOpenCustomHashSet(short[] a, ShortHash.Strategy strategy) {
/* 252 */     this(a, 0.75F, strategy);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ShortHash.Strategy strategy() {
/* 261 */     return this.strategy;
/*     */   }
/*     */   
/*     */   private int realSize() {
/* 265 */     return this.containsNull ? (this.size - 1) : this.size;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void ensureCapacity(int capacity) {
/* 275 */     int needed = HashCommon.arraySize(capacity, this.f);
/* 276 */     if (needed > this.n) rehash(needed); 
/*     */   }
/*     */   
/*     */   private void tryCapacity(long capacity) {
/* 280 */     int needed = (int)Math.min(1073741824L, Math.max(2L, HashCommon.nextPowerOfTwo((long)Math.ceil(((float)capacity / this.f)))));
/* 281 */     if (needed > this.n) rehash(needed);
/*     */   
/*     */   }
/*     */   
/*     */   public boolean addAll(ShortCollection c) {
/* 286 */     if (this.f <= 0.5D) { ensureCapacity(c.size()); }
/* 287 */     else { tryCapacity((size() + c.size())); }
/*     */     
/* 289 */     return super.addAll(c);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean addAll(Collection<? extends Short> c) {
/* 295 */     if (this.f <= 0.5D) { ensureCapacity(c.size()); }
/* 296 */     else { tryCapacity((size() + c.size())); }
/*     */     
/* 298 */     return super.addAll(c);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean add(short k) {
/* 304 */     if (this.strategy.equals(k, (short)0)) {
/* 305 */       if (this.containsNull) return false; 
/* 306 */       this.containsNull = true;
/* 307 */       this.key[this.n] = k;
/*     */     } else {
/*     */       
/* 310 */       short[] key = this.key; int pos;
/*     */       short curr;
/* 312 */       if ((curr = key[pos = HashCommon.mix(this.strategy.hashCode(k)) & this.mask]) != 0) {
/* 313 */         if (this.strategy.equals(curr, k)) return false; 
/* 314 */         while ((curr = key[pos = pos + 1 & this.mask]) != 0) { if (this.strategy.equals(curr, k)) return false;  }
/*     */       
/* 316 */       }  key[pos] = k;
/*     */     } 
/* 318 */     if (this.size++ >= this.maxFill) rehash(HashCommon.arraySize(this.size + 1, this.f));
/*     */     
/* 320 */     return true;
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
/* 333 */     short[] key = this.key; while (true) {
/*     */       short curr; int last;
/* 335 */       pos = (last = pos) + 1 & this.mask;
/*     */       while (true) {
/* 337 */         if ((curr = key[pos]) == 0) {
/* 338 */           key[last] = 0;
/*     */           return;
/*     */         } 
/* 341 */         int slot = HashCommon.mix(this.strategy.hashCode(curr)) & this.mask;
/* 342 */         if ((last <= pos) ? (last >= slot || slot > pos) : (last >= slot && slot > pos))
/* 343 */           break;  pos = pos + 1 & this.mask;
/*     */       } 
/* 345 */       key[last] = curr;
/*     */     } 
/*     */   }
/*     */   
/*     */   private boolean removeEntry(int pos) {
/* 350 */     this.size--;
/* 351 */     shiftKeys(pos);
/* 352 */     if (this.n > this.minN && this.size < this.maxFill / 4 && this.n > 16) rehash(this.n / 2); 
/* 353 */     return true;
/*     */   }
/*     */   
/*     */   private boolean removeNullEntry() {
/* 357 */     this.containsNull = false;
/* 358 */     this.key[this.n] = 0;
/* 359 */     this.size--;
/* 360 */     if (this.n > this.minN && this.size < this.maxFill / 4 && this.n > 16) rehash(this.n / 2); 
/* 361 */     return true;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean remove(short k) {
/* 366 */     if (this.strategy.equals(k, (short)0)) {
/* 367 */       if (this.containsNull) return removeNullEntry(); 
/* 368 */       return false;
/*     */     } 
/*     */     
/* 371 */     short[] key = this.key;
/*     */     short curr;
/*     */     int pos;
/* 374 */     if ((curr = key[pos = HashCommon.mix(this.strategy.hashCode(k)) & this.mask]) == 0) return false; 
/* 375 */     if (this.strategy.equals(k, curr)) return removeEntry(pos); 
/*     */     while (true) {
/* 377 */       if ((curr = key[pos = pos + 1 & this.mask]) == 0) return false; 
/* 378 */       if (this.strategy.equals(k, curr)) return removeEntry(pos);
/*     */     
/*     */     } 
/*     */   }
/*     */   
/*     */   public boolean contains(short k) {
/* 384 */     if (this.strategy.equals(k, (short)0)) return this.containsNull;
/*     */     
/* 386 */     short[] key = this.key;
/*     */     short curr;
/*     */     int pos;
/* 389 */     if ((curr = key[pos = HashCommon.mix(this.strategy.hashCode(k)) & this.mask]) == 0) return false; 
/* 390 */     if (this.strategy.equals(k, curr)) return true; 
/*     */     while (true) {
/* 392 */       if ((curr = key[pos = pos + 1 & this.mask]) == 0) return false; 
/* 393 */       if (this.strategy.equals(k, curr)) return true;
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
/* 405 */     if (this.size == 0)
/* 406 */       return;  this.size = 0;
/* 407 */     this.containsNull = false;
/* 408 */     Arrays.fill(this.key, (short)0);
/*     */   }
/*     */ 
/*     */   
/*     */   public int size() {
/* 413 */     return this.size;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isEmpty() {
/* 418 */     return (this.size == 0);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private final class SetIterator
/*     */     implements ShortIterator
/*     */   {
/* 427 */     int pos = ShortOpenCustomHashSet.this.n;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 433 */     int last = -1;
/*     */     
/* 435 */     int c = ShortOpenCustomHashSet.this.size;
/*     */     
/* 437 */     boolean mustReturnNull = ShortOpenCustomHashSet.this.containsNull;
/*     */ 
/*     */ 
/*     */     
/*     */     ShortArrayList wrapped;
/*     */ 
/*     */ 
/*     */     
/*     */     public boolean hasNext() {
/* 446 */       return (this.c != 0);
/*     */     }
/*     */ 
/*     */     
/*     */     public short nextShort() {
/* 451 */       if (!hasNext()) throw new NoSuchElementException(); 
/* 452 */       this.c--;
/* 453 */       if (this.mustReturnNull) {
/* 454 */         this.mustReturnNull = false;
/* 455 */         this.last = ShortOpenCustomHashSet.this.n;
/* 456 */         return ShortOpenCustomHashSet.this.key[ShortOpenCustomHashSet.this.n];
/*     */       } 
/* 458 */       short[] key = ShortOpenCustomHashSet.this.key;
/*     */       while (true) {
/* 460 */         if (--this.pos < 0) {
/*     */           
/* 462 */           this.last = Integer.MIN_VALUE;
/* 463 */           return this.wrapped.getShort(-this.pos - 1);
/*     */         } 
/* 465 */         if (key[this.pos] != 0) return key[this.last = this.pos];
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
/* 479 */       short[] key = ShortOpenCustomHashSet.this.key; while (true) {
/*     */         short curr; int last;
/* 481 */         pos = (last = pos) + 1 & ShortOpenCustomHashSet.this.mask;
/*     */         while (true) {
/* 483 */           if ((curr = key[pos]) == 0) {
/* 484 */             key[last] = 0;
/*     */             return;
/*     */           } 
/* 487 */           int slot = HashCommon.mix(ShortOpenCustomHashSet.this.strategy.hashCode(curr)) & ShortOpenCustomHashSet.this.mask;
/* 488 */           if ((last <= pos) ? (last >= slot || slot > pos) : (last >= slot && slot > pos))
/* 489 */             break;  pos = pos + 1 & ShortOpenCustomHashSet.this.mask;
/*     */         } 
/* 491 */         if (pos < last) {
/* 492 */           if (this.wrapped == null) this.wrapped = new ShortArrayList(2); 
/* 493 */           this.wrapped.add(key[pos]);
/*     */         } 
/* 495 */         key[last] = curr;
/*     */       } 
/*     */     }
/*     */ 
/*     */     
/*     */     public void remove() {
/* 501 */       if (this.last == -1) throw new IllegalStateException(); 
/* 502 */       if (this.last == ShortOpenCustomHashSet.this.n)
/* 503 */       { ShortOpenCustomHashSet.this.containsNull = false;
/* 504 */         ShortOpenCustomHashSet.this.key[ShortOpenCustomHashSet.this.n] = 0; }
/* 505 */       else if (this.pos >= 0) { shiftKeys(this.last); }
/*     */       else
/*     */       
/* 508 */       { ShortOpenCustomHashSet.this.remove(this.wrapped.getShort(-this.pos - 1));
/* 509 */         this.last = -1;
/*     */         return; }
/*     */       
/* 512 */       ShortOpenCustomHashSet.this.size--;
/* 513 */       this.last = -1;
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     public void forEachRemaining(ShortConsumer action) {
/* 519 */       short[] key = ShortOpenCustomHashSet.this.key;
/* 520 */       if (this.mustReturnNull) {
/* 521 */         this.mustReturnNull = false;
/* 522 */         this.last = ShortOpenCustomHashSet.this.n;
/* 523 */         action.accept(key[ShortOpenCustomHashSet.this.n]);
/* 524 */         this.c--;
/*     */       } 
/* 526 */       while (this.c != 0) {
/* 527 */         if (--this.pos < 0) {
/*     */           
/* 529 */           this.last = Integer.MIN_VALUE;
/* 530 */           action.accept(this.wrapped.getShort(-this.pos - 1));
/* 531 */           this.c--; continue;
/* 532 */         }  if (key[this.pos] != 0) {
/* 533 */           action.accept(key[this.last = this.pos]);
/* 534 */           this.c--;
/*     */         } 
/*     */       } 
/*     */     }
/*     */     
/*     */     private SetIterator() {} }
/*     */   
/*     */   public ShortIterator iterator() {
/* 542 */     return new SetIterator();
/*     */   }
/*     */ 
/*     */   
/*     */   private final class SetSpliterator
/*     */     implements ShortSpliterator
/*     */   {
/*     */     private static final int POST_SPLIT_CHARACTERISTICS = 257;
/*     */     
/* 551 */     int pos = 0;
/*     */     
/* 553 */     int max = ShortOpenCustomHashSet.this.n;
/*     */     
/* 555 */     int c = 0;
/*     */     
/* 557 */     boolean mustReturnNull = ShortOpenCustomHashSet.this.containsNull;
/*     */ 
/*     */     
/*     */     boolean hasSplit = false;
/*     */ 
/*     */     
/*     */     SetSpliterator(int pos, int max, boolean mustReturnNull, boolean hasSplit) {
/* 564 */       this.pos = pos;
/* 565 */       this.max = max;
/* 566 */       this.mustReturnNull = mustReturnNull;
/* 567 */       this.hasSplit = hasSplit;
/*     */     }
/*     */ 
/*     */     
/*     */     public boolean tryAdvance(ShortConsumer action) {
/* 572 */       if (this.mustReturnNull) {
/* 573 */         this.mustReturnNull = false;
/* 574 */         this.c++;
/* 575 */         action.accept(ShortOpenCustomHashSet.this.key[ShortOpenCustomHashSet.this.n]);
/* 576 */         return true;
/*     */       } 
/* 578 */       short[] key = ShortOpenCustomHashSet.this.key;
/* 579 */       while (this.pos < this.max) {
/* 580 */         if (key[this.pos] != 0) {
/* 581 */           this.c++;
/* 582 */           action.accept(key[this.pos++]);
/* 583 */           return true;
/*     */         } 
/* 585 */         this.pos++;
/*     */       } 
/*     */       
/* 588 */       return false;
/*     */     }
/*     */ 
/*     */     
/*     */     public void forEachRemaining(ShortConsumer action) {
/* 593 */       short[] key = ShortOpenCustomHashSet.this.key;
/* 594 */       if (this.mustReturnNull) {
/* 595 */         this.mustReturnNull = false;
/* 596 */         action.accept(key[ShortOpenCustomHashSet.this.n]);
/* 597 */         this.c++;
/*     */       } 
/* 599 */       while (this.pos < this.max) {
/* 600 */         if (key[this.pos] != 0) {
/* 601 */           action.accept(key[this.pos]);
/* 602 */           this.c++;
/*     */         } 
/* 604 */         this.pos++;
/*     */       } 
/*     */     }
/*     */ 
/*     */     
/*     */     public int characteristics() {
/* 610 */       return this.hasSplit ? 257 : 321;
/*     */     }
/*     */ 
/*     */     
/*     */     public long estimateSize() {
/* 615 */       if (!this.hasSplit)
/*     */       {
/* 617 */         return (ShortOpenCustomHashSet.this.size - this.c);
/*     */       }
/*     */ 
/*     */ 
/*     */       
/* 622 */       return Math.min((ShortOpenCustomHashSet.this.size - this.c), (long)(ShortOpenCustomHashSet.this.realSize() / ShortOpenCustomHashSet.this.n * (this.max - this.pos)) + (this.mustReturnNull ? 1L : 0L));
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     public SetSpliterator trySplit() {
/* 628 */       if (this.pos >= this.max - 1) return null; 
/* 629 */       int retLen = this.max - this.pos >> 1;
/* 630 */       if (retLen <= 1) return null; 
/* 631 */       int myNewPos = this.pos + retLen;
/* 632 */       int retPos = this.pos;
/* 633 */       int retMax = myNewPos;
/*     */ 
/*     */ 
/*     */       
/* 637 */       SetSpliterator split = new SetSpliterator(retPos, retMax, this.mustReturnNull, true);
/* 638 */       this.pos = myNewPos;
/* 639 */       this.mustReturnNull = false;
/* 640 */       this.hasSplit = true;
/* 641 */       return split;
/*     */     }
/*     */ 
/*     */     
/*     */     public long skip(long n) {
/* 646 */       if (n < 0L) throw new IllegalArgumentException("Argument must be nonnegative: " + n); 
/* 647 */       if (n == 0L) return 0L; 
/* 648 */       long skipped = 0L;
/* 649 */       if (this.mustReturnNull) {
/* 650 */         this.mustReturnNull = false;
/* 651 */         skipped++;
/* 652 */         n--;
/*     */       } 
/* 654 */       short[] key = ShortOpenCustomHashSet.this.key;
/* 655 */       while (this.pos < this.max && n > 0L) {
/* 656 */         if (key[this.pos++] != 0) {
/* 657 */           skipped++;
/* 658 */           n--;
/*     */         } 
/*     */       } 
/* 661 */       return skipped;
/*     */     }
/*     */     
/*     */     SetSpliterator() {} }
/*     */   
/*     */   public ShortSpliterator spliterator() {
/* 667 */     return new SetSpliterator();
/*     */   }
/*     */ 
/*     */   
/*     */   public void forEach(ShortConsumer action) {
/* 672 */     if (this.containsNull) action.accept(this.key[this.n]); 
/* 673 */     short[] key = this.key;
/* 674 */     for (int pos = this.n; pos-- != 0;) { if (key[pos] != 0) action.accept(key[pos]);
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
/* 691 */     return trim(this.size);
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
/* 713 */     int l = HashCommon.nextPowerOfTwo((int)Math.ceil((n / this.f)));
/* 714 */     if (l >= this.n || this.size > HashCommon.maxFill(l, this.f)) return true; 
/*     */     try {
/* 716 */       rehash(l);
/* 717 */     } catch (OutOfMemoryError cantDoIt) {
/* 718 */       return false;
/*     */     } 
/* 720 */     return true;
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
/* 735 */     short[] key = this.key;
/* 736 */     int mask = newN - 1;
/* 737 */     short[] newKey = new short[newN + 1];
/* 738 */     int i = this.n;
/* 739 */     for (int j = realSize(); j-- != 0; ) {
/* 740 */       while (key[--i] == 0); int pos;
/* 741 */       if (newKey[pos = HashCommon.mix(this.strategy.hashCode(key[i])) & mask] != 0) while (newKey[pos = pos + 1 & mask] != 0); 
/* 742 */       newKey[pos] = key[i];
/*     */     } 
/* 744 */     this.n = newN;
/* 745 */     this.mask = mask;
/* 746 */     this.maxFill = HashCommon.maxFill(this.n, this.f);
/* 747 */     this.key = newKey;
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
/*     */   public ShortOpenCustomHashSet clone() {
/*     */     ShortOpenCustomHashSet c;
/*     */     try {
/* 764 */       c = (ShortOpenCustomHashSet)super.clone();
/* 765 */     } catch (CloneNotSupportedException cantHappen) {
/* 766 */       throw new InternalError();
/*     */     } 
/* 768 */     c.key = (short[])this.key.clone();
/* 769 */     c.containsNull = this.containsNull;
/* 770 */     c.strategy = this.strategy;
/* 771 */     return c;
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
/* 785 */     int h = 0;
/* 786 */     for (int j = realSize(), i = 0; j-- != 0; ) {
/* 787 */       for (; this.key[i] == 0; i++);
/* 788 */       h += this.strategy.hashCode(this.key[i]);
/* 789 */       i++;
/*     */     } 
/*     */     
/* 792 */     return h;
/*     */   }
/*     */   
/*     */   private void writeObject(ObjectOutputStream s) throws IOException {
/* 796 */     ShortIterator i = iterator();
/* 797 */     s.defaultWriteObject();
/* 798 */     for (int j = this.size; j-- != 0; s.writeShort(i.nextShort()));
/*     */   }
/*     */   
/*     */   private void readObject(ObjectInputStream s) throws IOException, ClassNotFoundException {
/* 802 */     s.defaultReadObject();
/* 803 */     this.n = HashCommon.arraySize(this.size, this.f);
/* 804 */     this.maxFill = HashCommon.maxFill(this.n, this.f);
/* 805 */     this.mask = this.n - 1;
/* 806 */     short[] key = this.key = new short[this.n + 1];
/*     */     
/* 808 */     for (int i = this.size; i-- != 0; ) {
/* 809 */       int pos; short k = s.readShort();
/* 810 */       if (this.strategy.equals(k, (short)0))
/* 811 */       { pos = this.n;
/* 812 */         this.containsNull = true; }
/*     */       
/* 814 */       else if (key[pos = HashCommon.mix(this.strategy.hashCode(k)) & this.mask] != 0) { while (key[pos = pos + 1 & this.mask] != 0); }
/*     */       
/* 816 */       key[pos] = k;
/*     */     } 
/*     */   }
/*     */   
/*     */   private void checkTable() {}
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\i\\unimi\dsi\fastutil\shorts\ShortOpenCustomHashSet.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */