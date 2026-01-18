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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class ObjectOpenCustomHashSet<K>
/*     */   extends AbstractObjectSet<K>
/*     */   implements Serializable, Cloneable, Hash
/*     */ {
/*     */   private static final long serialVersionUID = 0L;
/*     */   private static final boolean ASSERTS = false;
/*     */   protected transient K[] key;
/*     */   protected transient int mask;
/*     */   protected transient boolean containsNull;
/*     */   protected Hash.Strategy<? super K> strategy;
/*     */   protected transient int n;
/*     */   protected transient int maxFill;
/*     */   protected final transient int minN;
/*     */   protected int size;
/*     */   protected final float f;
/*     */   
/*     */   public ObjectOpenCustomHashSet(int expected, float f, Hash.Strategy<? super K> strategy) {
/*  85 */     this.strategy = strategy;
/*  86 */     if (f <= 0.0F || f >= 1.0F) throw new IllegalArgumentException("Load factor must be greater than 0 and smaller than 1"); 
/*  87 */     if (expected < 0) throw new IllegalArgumentException("The expected number of elements must be nonnegative"); 
/*  88 */     this.f = f;
/*  89 */     this.minN = this.n = HashCommon.arraySize(expected, f);
/*  90 */     this.mask = this.n - 1;
/*  91 */     this.maxFill = HashCommon.maxFill(this.n, f);
/*  92 */     this.key = (K[])new Object[this.n + 1];
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ObjectOpenCustomHashSet(int expected, Hash.Strategy<? super K> strategy) {
/* 102 */     this(expected, 0.75F, strategy);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ObjectOpenCustomHashSet(Hash.Strategy<? super K> strategy) {
/* 112 */     this(16, 0.75F, strategy);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ObjectOpenCustomHashSet(Collection<? extends K> c, float f, Hash.Strategy<? super K> strategy) {
/* 123 */     this(c.size(), f, strategy);
/* 124 */     addAll(c);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ObjectOpenCustomHashSet(Collection<? extends K> c, Hash.Strategy<? super K> strategy) {
/* 135 */     this(c, 0.75F, strategy);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ObjectOpenCustomHashSet(ObjectCollection<? extends K> c, float f, Hash.Strategy<? super K> strategy) {
/* 146 */     this(c.size(), f, strategy);
/* 147 */     addAll(c);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ObjectOpenCustomHashSet(ObjectCollection<? extends K> c, Hash.Strategy<? super K> strategy) {
/* 158 */     this(c, 0.75F, strategy);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ObjectOpenCustomHashSet(Iterator<? extends K> i, float f, Hash.Strategy<? super K> strategy) {
/* 169 */     this(16, f, strategy);
/* 170 */     for (; i.hasNext(); add(i.next()));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ObjectOpenCustomHashSet(Iterator<? extends K> i, Hash.Strategy<? super K> strategy) {
/* 181 */     this(i, 0.75F, strategy);
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
/*     */   public ObjectOpenCustomHashSet(K[] a, int offset, int length, float f, Hash.Strategy<? super K> strategy) {
/* 194 */     this((length < 0) ? 0 : length, f, strategy);
/* 195 */     ObjectArrays.ensureOffsetLength(a, offset, length);
/* 196 */     for (int i = 0; i < length; ) { add(a[offset + i]); i++; }
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
/*     */   public ObjectOpenCustomHashSet(K[] a, int offset, int length, Hash.Strategy<? super K> strategy) {
/* 209 */     this(a, offset, length, 0.75F, strategy);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ObjectOpenCustomHashSet(K[] a, float f, Hash.Strategy<? super K> strategy) {
/* 220 */     this(a, 0, a.length, f, strategy);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ObjectOpenCustomHashSet(K[] a, Hash.Strategy<? super K> strategy) {
/* 231 */     this(a, 0.75F, strategy);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Hash.Strategy<? super K> strategy() {
/* 240 */     return this.strategy;
/*     */   }
/*     */   
/*     */   private int realSize() {
/* 244 */     return this.containsNull ? (this.size - 1) : this.size;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void ensureCapacity(int capacity) {
/* 254 */     int needed = HashCommon.arraySize(capacity, this.f);
/* 255 */     if (needed > this.n) rehash(needed); 
/*     */   }
/*     */   
/*     */   private void tryCapacity(long capacity) {
/* 259 */     int needed = (int)Math.min(1073741824L, Math.max(2L, HashCommon.nextPowerOfTwo((long)Math.ceil(((float)capacity / this.f)))));
/* 260 */     if (needed > this.n) rehash(needed);
/*     */   
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean addAll(Collection<? extends K> c) {
/* 266 */     if (this.f <= 0.5D) { ensureCapacity(c.size()); }
/* 267 */     else { tryCapacity((size() + c.size())); }
/*     */     
/* 269 */     return super.addAll(c);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean add(K k) {
/* 275 */     if (this.strategy.equals(k, null)) {
/* 276 */       if (this.containsNull) return false; 
/* 277 */       this.containsNull = true;
/* 278 */       this.key[this.n] = k;
/*     */     } else {
/*     */       
/* 281 */       K[] key = this.key; int pos;
/*     */       K curr;
/* 283 */       if ((curr = key[pos = HashCommon.mix(this.strategy.hashCode(k)) & this.mask]) != null) {
/* 284 */         if (this.strategy.equals(curr, k)) return false; 
/* 285 */         while ((curr = key[pos = pos + 1 & this.mask]) != null) { if (this.strategy.equals(curr, k)) return false;  }
/*     */       
/* 287 */       }  key[pos] = k;
/*     */     } 
/* 289 */     if (this.size++ >= this.maxFill) rehash(HashCommon.arraySize(this.size + 1, this.f));
/*     */     
/* 291 */     return true;
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
/* 309 */     if (this.strategy.equals(k, null)) {
/* 310 */       if (this.containsNull) return this.key[this.n]; 
/* 311 */       this.containsNull = true;
/* 312 */       this.key[this.n] = k;
/*     */     } else {
/*     */       
/* 315 */       K[] key = this.key; int pos;
/*     */       K curr;
/* 317 */       if ((curr = key[pos = HashCommon.mix(this.strategy.hashCode(k)) & this.mask]) != null) {
/* 318 */         if (this.strategy.equals(curr, k)) return curr; 
/* 319 */         while ((curr = key[pos = pos + 1 & this.mask]) != null) { if (this.strategy.equals(curr, k)) return curr;  }
/*     */       
/* 321 */       }  key[pos] = k;
/*     */     } 
/* 323 */     if (this.size++ >= this.maxFill) rehash(HashCommon.arraySize(this.size + 1, this.f));
/*     */     
/* 325 */     return k;
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
/* 338 */     K[] key = this.key; while (true) {
/*     */       K curr; int last;
/* 340 */       pos = (last = pos) + 1 & this.mask;
/*     */       while (true) {
/* 342 */         if ((curr = key[pos]) == null) {
/* 343 */           key[last] = null;
/*     */           return;
/*     */         } 
/* 346 */         int slot = HashCommon.mix(this.strategy.hashCode(curr)) & this.mask;
/* 347 */         if ((last <= pos) ? (last >= slot || slot > pos) : (last >= slot && slot > pos))
/* 348 */           break;  pos = pos + 1 & this.mask;
/*     */       } 
/* 350 */       key[last] = curr;
/*     */     } 
/*     */   }
/*     */   
/*     */   private boolean removeEntry(int pos) {
/* 355 */     this.size--;
/* 356 */     shiftKeys(pos);
/* 357 */     if (this.n > this.minN && this.size < this.maxFill / 4 && this.n > 16) rehash(this.n / 2); 
/* 358 */     return true;
/*     */   }
/*     */   
/*     */   private boolean removeNullEntry() {
/* 362 */     this.containsNull = false;
/* 363 */     this.key[this.n] = null;
/* 364 */     this.size--;
/* 365 */     if (this.n > this.minN && this.size < this.maxFill / 4 && this.n > 16) rehash(this.n / 2); 
/* 366 */     return true;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean remove(Object k) {
/* 372 */     if (this.strategy.equals(k, null)) {
/* 373 */       if (this.containsNull) return removeNullEntry(); 
/* 374 */       return false;
/*     */     } 
/*     */     
/* 377 */     K[] key = this.key;
/*     */     K curr;
/*     */     int pos;
/* 380 */     if ((curr = key[pos = HashCommon.mix(this.strategy.hashCode(k)) & this.mask]) == null) return false; 
/* 381 */     if (this.strategy.equals(k, curr)) return removeEntry(pos); 
/*     */     while (true) {
/* 383 */       if ((curr = key[pos = pos + 1 & this.mask]) == null) return false; 
/* 384 */       if (this.strategy.equals(k, curr)) return removeEntry(pos);
/*     */     
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean contains(Object k) {
/* 391 */     if (this.strategy.equals(k, null)) return this.containsNull;
/*     */     
/* 393 */     K[] key = this.key;
/*     */     K curr;
/*     */     int pos;
/* 396 */     if ((curr = key[pos = HashCommon.mix(this.strategy.hashCode(k)) & this.mask]) == null) return false; 
/* 397 */     if (this.strategy.equals(k, curr)) return true; 
/*     */     while (true) {
/* 399 */       if ((curr = key[pos = pos + 1 & this.mask]) == null) return false; 
/* 400 */       if (this.strategy.equals(k, curr)) return true;
/*     */     
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public K get(Object k) {
/* 411 */     if (this.strategy.equals(k, null)) return this.key[this.n];
/*     */ 
/*     */     
/* 414 */     K[] key = this.key;
/*     */     K curr;
/*     */     int pos;
/* 417 */     if ((curr = key[pos = HashCommon.mix(this.strategy.hashCode(k)) & this.mask]) == null) return null; 
/* 418 */     if (this.strategy.equals(k, curr)) return curr;
/*     */     
/*     */     while (true) {
/* 421 */       if ((curr = key[pos = pos + 1 & this.mask]) == null) return null; 
/* 422 */       if (this.strategy.equals(k, curr)) return curr;
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
/* 434 */     if (this.size == 0)
/* 435 */       return;  this.size = 0;
/* 436 */     this.containsNull = false;
/* 437 */     Arrays.fill((Object[])this.key, (Object)null);
/*     */   }
/*     */ 
/*     */   
/*     */   public int size() {
/* 442 */     return this.size;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isEmpty() {
/* 447 */     return (this.size == 0);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private final class SetIterator
/*     */     implements ObjectIterator<K>
/*     */   {
/* 456 */     int pos = ObjectOpenCustomHashSet.this.n;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 462 */     int last = -1;
/*     */     
/* 464 */     int c = ObjectOpenCustomHashSet.this.size;
/*     */     
/* 466 */     boolean mustReturnNull = ObjectOpenCustomHashSet.this.containsNull;
/*     */ 
/*     */ 
/*     */     
/*     */     ObjectArrayList<K> wrapped;
/*     */ 
/*     */ 
/*     */     
/*     */     public boolean hasNext() {
/* 475 */       return (this.c != 0);
/*     */     }
/*     */ 
/*     */     
/*     */     public K next() {
/* 480 */       if (!hasNext()) throw new NoSuchElementException(); 
/* 481 */       this.c--;
/* 482 */       if (this.mustReturnNull) {
/* 483 */         this.mustReturnNull = false;
/* 484 */         this.last = ObjectOpenCustomHashSet.this.n;
/* 485 */         return ObjectOpenCustomHashSet.this.key[ObjectOpenCustomHashSet.this.n];
/*     */       } 
/* 487 */       K[] key = ObjectOpenCustomHashSet.this.key;
/*     */       while (true) {
/* 489 */         if (--this.pos < 0) {
/*     */           
/* 491 */           this.last = Integer.MIN_VALUE;
/* 492 */           return this.wrapped.get(-this.pos - 1);
/*     */         } 
/* 494 */         if (key[this.pos] != null) return key[this.last = this.pos];
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
/* 508 */       K[] key = ObjectOpenCustomHashSet.this.key; while (true) {
/*     */         K curr; int last;
/* 510 */         pos = (last = pos) + 1 & ObjectOpenCustomHashSet.this.mask;
/*     */         while (true) {
/* 512 */           if ((curr = key[pos]) == null) {
/* 513 */             key[last] = null;
/*     */             return;
/*     */           } 
/* 516 */           int slot = HashCommon.mix(ObjectOpenCustomHashSet.this.strategy.hashCode(curr)) & ObjectOpenCustomHashSet.this.mask;
/* 517 */           if ((last <= pos) ? (last >= slot || slot > pos) : (last >= slot && slot > pos))
/* 518 */             break;  pos = pos + 1 & ObjectOpenCustomHashSet.this.mask;
/*     */         } 
/* 520 */         if (pos < last) {
/* 521 */           if (this.wrapped == null) this.wrapped = new ObjectArrayList<>(2); 
/* 522 */           this.wrapped.add(key[pos]);
/*     */         } 
/* 524 */         key[last] = curr;
/*     */       } 
/*     */     }
/*     */ 
/*     */     
/*     */     public void remove() {
/* 530 */       if (this.last == -1) throw new IllegalStateException(); 
/* 531 */       if (this.last == ObjectOpenCustomHashSet.this.n)
/* 532 */       { ObjectOpenCustomHashSet.this.containsNull = false;
/* 533 */         ObjectOpenCustomHashSet.this.key[ObjectOpenCustomHashSet.this.n] = null; }
/* 534 */       else if (this.pos >= 0) { shiftKeys(this.last); }
/*     */       else
/*     */       
/* 537 */       { ObjectOpenCustomHashSet.this.remove(this.wrapped.set(-this.pos - 1, null));
/* 538 */         this.last = -1;
/*     */         return; }
/*     */       
/* 541 */       ObjectOpenCustomHashSet.this.size--;
/* 542 */       this.last = -1;
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     public void forEachRemaining(Consumer<? super K> action) {
/* 548 */       K[] key = ObjectOpenCustomHashSet.this.key;
/* 549 */       if (this.mustReturnNull) {
/* 550 */         this.mustReturnNull = false;
/* 551 */         this.last = ObjectOpenCustomHashSet.this.n;
/* 552 */         action.accept(key[ObjectOpenCustomHashSet.this.n]);
/* 553 */         this.c--;
/*     */       } 
/* 555 */       while (this.c != 0) {
/* 556 */         if (--this.pos < 0) {
/*     */           
/* 558 */           this.last = Integer.MIN_VALUE;
/* 559 */           action.accept(this.wrapped.get(-this.pos - 1));
/* 560 */           this.c--; continue;
/* 561 */         }  if (key[this.pos] != null) {
/* 562 */           action.accept(key[this.last = this.pos]);
/* 563 */           this.c--;
/*     */         } 
/*     */       } 
/*     */     }
/*     */     
/*     */     private SetIterator() {} }
/*     */   
/*     */   public ObjectIterator<K> iterator() {
/* 571 */     return new SetIterator();
/*     */   }
/*     */ 
/*     */   
/*     */   private final class SetSpliterator
/*     */     implements ObjectSpliterator<K>
/*     */   {
/*     */     private static final int POST_SPLIT_CHARACTERISTICS = 1;
/*     */     
/* 580 */     int pos = 0;
/*     */     
/* 582 */     int max = ObjectOpenCustomHashSet.this.n;
/*     */     
/* 584 */     int c = 0;
/*     */     
/* 586 */     boolean mustReturnNull = ObjectOpenCustomHashSet.this.containsNull;
/*     */ 
/*     */     
/*     */     boolean hasSplit = false;
/*     */ 
/*     */     
/*     */     SetSpliterator(int pos, int max, boolean mustReturnNull, boolean hasSplit) {
/* 593 */       this.pos = pos;
/* 594 */       this.max = max;
/* 595 */       this.mustReturnNull = mustReturnNull;
/* 596 */       this.hasSplit = hasSplit;
/*     */     }
/*     */ 
/*     */     
/*     */     public boolean tryAdvance(Consumer<? super K> action) {
/* 601 */       if (this.mustReturnNull) {
/* 602 */         this.mustReturnNull = false;
/* 603 */         this.c++;
/* 604 */         action.accept(ObjectOpenCustomHashSet.this.key[ObjectOpenCustomHashSet.this.n]);
/* 605 */         return true;
/*     */       } 
/* 607 */       K[] key = ObjectOpenCustomHashSet.this.key;
/* 608 */       while (this.pos < this.max) {
/* 609 */         if (key[this.pos] != null) {
/* 610 */           this.c++;
/* 611 */           action.accept(key[this.pos++]);
/* 612 */           return true;
/*     */         } 
/* 614 */         this.pos++;
/*     */       } 
/*     */       
/* 617 */       return false;
/*     */     }
/*     */ 
/*     */     
/*     */     public void forEachRemaining(Consumer<? super K> action) {
/* 622 */       K[] key = ObjectOpenCustomHashSet.this.key;
/* 623 */       if (this.mustReturnNull) {
/* 624 */         this.mustReturnNull = false;
/* 625 */         action.accept(key[ObjectOpenCustomHashSet.this.n]);
/* 626 */         this.c++;
/*     */       } 
/* 628 */       while (this.pos < this.max) {
/* 629 */         if (key[this.pos] != null) {
/* 630 */           action.accept(key[this.pos]);
/* 631 */           this.c++;
/*     */         } 
/* 633 */         this.pos++;
/*     */       } 
/*     */     }
/*     */ 
/*     */     
/*     */     public int characteristics() {
/* 639 */       return this.hasSplit ? 1 : 65;
/*     */     }
/*     */ 
/*     */     
/*     */     public long estimateSize() {
/* 644 */       if (!this.hasSplit)
/*     */       {
/* 646 */         return (ObjectOpenCustomHashSet.this.size - this.c);
/*     */       }
/*     */ 
/*     */ 
/*     */       
/* 651 */       return Math.min((ObjectOpenCustomHashSet.this.size - this.c), (long)(ObjectOpenCustomHashSet.this.realSize() / ObjectOpenCustomHashSet.this.n * (this.max - this.pos)) + (this.mustReturnNull ? 1L : 0L));
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     public SetSpliterator trySplit() {
/* 657 */       if (this.pos >= this.max - 1) return null; 
/* 658 */       int retLen = this.max - this.pos >> 1;
/* 659 */       if (retLen <= 1) return null; 
/* 660 */       int myNewPos = this.pos + retLen;
/* 661 */       int retPos = this.pos;
/* 662 */       int retMax = myNewPos;
/*     */ 
/*     */ 
/*     */       
/* 666 */       SetSpliterator split = new SetSpliterator(retPos, retMax, this.mustReturnNull, true);
/* 667 */       this.pos = myNewPos;
/* 668 */       this.mustReturnNull = false;
/* 669 */       this.hasSplit = true;
/* 670 */       return split;
/*     */     }
/*     */ 
/*     */     
/*     */     public long skip(long n) {
/* 675 */       if (n < 0L) throw new IllegalArgumentException("Argument must be nonnegative: " + n); 
/* 676 */       if (n == 0L) return 0L; 
/* 677 */       long skipped = 0L;
/* 678 */       if (this.mustReturnNull) {
/* 679 */         this.mustReturnNull = false;
/* 680 */         skipped++;
/* 681 */         n--;
/*     */       } 
/* 683 */       K[] key = ObjectOpenCustomHashSet.this.key;
/* 684 */       while (this.pos < this.max && n > 0L) {
/* 685 */         if (key[this.pos++] != null) {
/* 686 */           skipped++;
/* 687 */           n--;
/*     */         } 
/*     */       } 
/* 690 */       return skipped;
/*     */     }
/*     */     
/*     */     SetSpliterator() {} }
/*     */   
/*     */   public ObjectSpliterator<K> spliterator() {
/* 696 */     return new SetSpliterator();
/*     */   }
/*     */ 
/*     */   
/*     */   public void forEach(Consumer<? super K> action) {
/* 701 */     if (this.containsNull) action.accept(this.key[this.n]); 
/* 702 */     K[] key = this.key;
/* 703 */     for (int pos = this.n; pos-- != 0;) { if (key[pos] != null) action.accept(key[pos]);
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
/* 720 */     return trim(this.size);
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
/* 742 */     int l = HashCommon.nextPowerOfTwo((int)Math.ceil((n / this.f)));
/* 743 */     if (l >= this.n || this.size > HashCommon.maxFill(l, this.f)) return true; 
/*     */     try {
/* 745 */       rehash(l);
/* 746 */     } catch (OutOfMemoryError cantDoIt) {
/* 747 */       return false;
/*     */     } 
/* 749 */     return true;
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
/* 764 */     K[] key = this.key;
/* 765 */     int mask = newN - 1;
/* 766 */     K[] newKey = (K[])new Object[newN + 1];
/* 767 */     int i = this.n;
/* 768 */     for (int j = realSize(); j-- != 0; ) {
/* 769 */       while (key[--i] == null); int pos;
/* 770 */       if (newKey[pos = HashCommon.mix(this.strategy.hashCode(key[i])) & mask] != null) while (newKey[pos = pos + 1 & mask] != null); 
/* 771 */       newKey[pos] = key[i];
/*     */     } 
/* 773 */     this.n = newN;
/* 774 */     this.mask = mask;
/* 775 */     this.maxFill = HashCommon.maxFill(this.n, this.f);
/* 776 */     this.key = newKey;
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
/*     */   public ObjectOpenCustomHashSet<K> clone() {
/*     */     ObjectOpenCustomHashSet<K> c;
/*     */     try {
/* 793 */       c = (ObjectOpenCustomHashSet<K>)super.clone();
/* 794 */     } catch (CloneNotSupportedException cantHappen) {
/* 795 */       throw new InternalError();
/*     */     } 
/* 797 */     c.key = (K[])this.key.clone();
/* 798 */     c.containsNull = this.containsNull;
/* 799 */     c.strategy = this.strategy;
/* 800 */     return c;
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
/* 814 */     int h = 0;
/* 815 */     for (int j = realSize(), i = 0; j-- != 0; ) {
/* 816 */       for (; this.key[i] == null; i++);
/* 817 */       if (this != this.key[i]) h += this.strategy.hashCode(this.key[i]); 
/* 818 */       i++;
/*     */     } 
/*     */     
/* 821 */     return h;
/*     */   }
/*     */   
/*     */   private void writeObject(ObjectOutputStream s) throws IOException {
/* 825 */     ObjectIterator<K> i = iterator();
/* 826 */     s.defaultWriteObject();
/* 827 */     for (int j = this.size; j-- != 0; s.writeObject(i.next()));
/*     */   }
/*     */ 
/*     */   
/*     */   private void readObject(ObjectInputStream s) throws IOException, ClassNotFoundException {
/* 832 */     s.defaultReadObject();
/* 833 */     this.n = HashCommon.arraySize(this.size, this.f);
/* 834 */     this.maxFill = HashCommon.maxFill(this.n, this.f);
/* 835 */     this.mask = this.n - 1;
/* 836 */     K[] key = this.key = (K[])new Object[this.n + 1];
/*     */     
/* 838 */     for (int i = this.size; i-- != 0; ) {
/* 839 */       int pos; K k = (K)s.readObject();
/* 840 */       if (this.strategy.equals(k, null))
/* 841 */       { pos = this.n;
/* 842 */         this.containsNull = true; }
/*     */       
/* 844 */       else if (key[pos = HashCommon.mix(this.strategy.hashCode(k)) & this.mask] != null) { while (key[pos = pos + 1 & this.mask] != null); }
/*     */       
/* 846 */       key[pos] = k;
/*     */     } 
/*     */   }
/*     */   
/*     */   private void checkTable() {}
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\i\\unimi\dsi\fastutil\objects\ObjectOpenCustomHashSet.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */