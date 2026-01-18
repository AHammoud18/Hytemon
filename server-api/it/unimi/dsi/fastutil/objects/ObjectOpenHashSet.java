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
/*     */ public class ObjectOpenHashSet<K>
/*     */   extends AbstractObjectSet<K>
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
/*     */   public ObjectOpenHashSet(int expected, float f) {
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
/*     */   public ObjectOpenHashSet(int expected) {
/*  97 */     this(expected, 0.75F);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ObjectOpenHashSet() {
/* 105 */     this(16, 0.75F);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ObjectOpenHashSet(Collection<? extends K> c, float f) {
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
/*     */   public ObjectOpenHashSet(Collection<? extends K> c) {
/* 126 */     this(c, 0.75F);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ObjectOpenHashSet(ObjectCollection<? extends K> c, float f) {
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
/*     */   public ObjectOpenHashSet(ObjectCollection<? extends K> c) {
/* 147 */     this(c, 0.75F);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ObjectOpenHashSet(Iterator<? extends K> i, float f) {
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
/*     */   public ObjectOpenHashSet(Iterator<? extends K> i) {
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
/*     */   public ObjectOpenHashSet(K[] a, int offset, int length, float f) {
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
/*     */   public ObjectOpenHashSet(K[] a, int offset, int length) {
/* 194 */     this(a, offset, length, 0.75F);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ObjectOpenHashSet(K[] a, float f) {
/* 204 */     this(a, 0, a.length, f);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ObjectOpenHashSet(K[] a) {
/* 214 */     this(a, 0.75F);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static <K> ObjectOpenHashSet<K> of() {
/* 223 */     return new ObjectOpenHashSet<>();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static <K> ObjectOpenHashSet<K> of(K e) {
/* 234 */     ObjectOpenHashSet<K> result = new ObjectOpenHashSet<>(1, 0.75F);
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
/*     */   public static <K> ObjectOpenHashSet<K> of(K e0, K e1) {
/* 250 */     ObjectOpenHashSet<K> result = new ObjectOpenHashSet<>(2, 0.75F);
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
/*     */   public static <K> ObjectOpenHashSet<K> of(K e0, K e1, K e2) {
/* 270 */     ObjectOpenHashSet<K> result = new ObjectOpenHashSet<>(3, 0.75F);
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
/*     */   public static <K> ObjectOpenHashSet<K> of(K... a) {
/* 292 */     ObjectOpenHashSet<K> result = new ObjectOpenHashSet<>(a.length, 0.75F);
/* 293 */     for (K element : a) {
/* 294 */       if (!result.add(element)) {
/* 295 */         throw new IllegalArgumentException("Duplicate element " + element);
/*     */       }
/*     */     } 
/* 298 */     return result;
/*     */   }
/*     */ 
/*     */   
/*     */   private ObjectOpenHashSet<K> combine(ObjectOpenHashSet<? extends K> toAddFrom) {
/* 303 */     addAll(toAddFrom);
/* 304 */     return this;
/*     */   }
/*     */   
/* 307 */   private static final Collector<Object, ?, ObjectOpenHashSet<Object>> TO_SET_COLLECTOR = (Collector)Collector.of(ObjectOpenHashSet::new, ObjectOpenHashSet::add, ObjectOpenHashSet::combine, new Collector.Characteristics[] { Collector.Characteristics.UNORDERED });
/*     */ 
/*     */ 
/*     */   
/*     */   public static <K> Collector<K, ?, ObjectOpenHashSet<K>> toSet() {
/* 312 */     return (Collector)TO_SET_COLLECTOR;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static <K> Collector<K, ?, ObjectOpenHashSet<K>> toSetWithExpectedSize(int expectedSize) {
/* 320 */     if (expectedSize <= 16)
/*     */     {
/*     */       
/* 323 */       return toSet();
/*     */     }
/* 325 */     return (Collector)Collector.of(new ObjectCollections.SizeDecreasingSupplier<>(expectedSize, size -> (size <= 16) ? new ObjectOpenHashSet() : new ObjectOpenHashSet(size)), ObjectOpenHashSet::add, ObjectOpenHashSet::combine, new Collector.Characteristics[] { Collector.Characteristics.UNORDERED });
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
/* 367 */       if ((curr = key[pos = HashCommon.mix(k.hashCode()) & this.mask]) != null) {
/* 368 */         if (curr.equals(k)) return false; 
/* 369 */         while ((curr = key[pos = pos + 1 & this.mask]) != null) { if (curr.equals(k)) return false;  }
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public K addOrGet(K k) {
/* 393 */     if (k == null) {
/* 394 */       if (this.containsNull) return this.key[this.n]; 
/* 395 */       this.containsNull = true;
/*     */     } else {
/*     */       
/* 398 */       K[] key = this.key; int pos;
/*     */       K curr;
/* 400 */       if ((curr = key[pos = HashCommon.mix(k.hashCode()) & this.mask]) != null) {
/* 401 */         if (curr.equals(k)) return curr; 
/* 402 */         while ((curr = key[pos = pos + 1 & this.mask]) != null) { if (curr.equals(k)) return curr;  }
/*     */       
/* 404 */       }  key[pos] = k;
/*     */     } 
/* 406 */     if (this.size++ >= this.maxFill) rehash(HashCommon.arraySize(this.size + 1, this.f));
/*     */     
/* 408 */     return k;
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
/* 421 */     K[] key = this.key; while (true) {
/*     */       K curr; int last;
/* 423 */       pos = (last = pos) + 1 & this.mask;
/*     */       while (true) {
/* 425 */         if ((curr = key[pos]) == null) {
/* 426 */           key[last] = null;
/*     */           return;
/*     */         } 
/* 429 */         int slot = HashCommon.mix(curr.hashCode()) & this.mask;
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
/* 446 */     this.key[this.n] = null;
/* 447 */     this.size--;
/* 448 */     if (this.n > this.minN && this.size < this.maxFill / 4 && this.n > 16) rehash(this.n / 2); 
/* 449 */     return true;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean remove(Object k) {
/* 455 */     if (k == null) {
/* 456 */       if (this.containsNull) return removeNullEntry(); 
/* 457 */       return false;
/*     */     } 
/*     */     
/* 460 */     K[] key = this.key;
/*     */     K curr;
/*     */     int pos;
/* 463 */     if ((curr = key[pos = HashCommon.mix(k.hashCode()) & this.mask]) == null) return false; 
/* 464 */     if (k.equals(curr)) return removeEntry(pos); 
/*     */     while (true) {
/* 466 */       if ((curr = key[pos = pos + 1 & this.mask]) == null) return false; 
/* 467 */       if (k.equals(curr)) return removeEntry(pos);
/*     */     
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean contains(Object k) {
/* 474 */     if (k == null) return this.containsNull;
/*     */     
/* 476 */     K[] key = this.key;
/*     */     K curr;
/*     */     int pos;
/* 479 */     if ((curr = key[pos = HashCommon.mix(k.hashCode()) & this.mask]) == null) return false; 
/* 480 */     if (k.equals(curr)) return true; 
/*     */     while (true) {
/* 482 */       if ((curr = key[pos = pos + 1 & this.mask]) == null) return false; 
/* 483 */       if (k.equals(curr)) return true;
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
/* 494 */     if (k == null) return this.key[this.n];
/*     */ 
/*     */     
/* 497 */     K[] key = this.key;
/*     */     K curr;
/*     */     int pos;
/* 500 */     if ((curr = key[pos = HashCommon.mix(k.hashCode()) & this.mask]) == null) return null; 
/* 501 */     if (k.equals(curr)) return curr;
/*     */     
/*     */     while (true) {
/* 504 */       if ((curr = key[pos = pos + 1 & this.mask]) == null) return null; 
/* 505 */       if (k.equals(curr)) return curr;
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
/* 517 */     if (this.size == 0)
/* 518 */       return;  this.size = 0;
/* 519 */     this.containsNull = false;
/* 520 */     Arrays.fill((Object[])this.key, (Object)null);
/*     */   }
/*     */ 
/*     */   
/*     */   public int size() {
/* 525 */     return this.size;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isEmpty() {
/* 530 */     return (this.size == 0);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private final class SetIterator
/*     */     implements ObjectIterator<K>
/*     */   {
/* 539 */     int pos = ObjectOpenHashSet.this.n;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 545 */     int last = -1;
/*     */     
/* 547 */     int c = ObjectOpenHashSet.this.size;
/*     */     
/* 549 */     boolean mustReturnNull = ObjectOpenHashSet.this.containsNull;
/*     */ 
/*     */ 
/*     */     
/*     */     ObjectArrayList<K> wrapped;
/*     */ 
/*     */ 
/*     */     
/*     */     public boolean hasNext() {
/* 558 */       return (this.c != 0);
/*     */     }
/*     */ 
/*     */     
/*     */     public K next() {
/* 563 */       if (!hasNext()) throw new NoSuchElementException(); 
/* 564 */       this.c--;
/* 565 */       if (this.mustReturnNull) {
/* 566 */         this.mustReturnNull = false;
/* 567 */         this.last = ObjectOpenHashSet.this.n;
/* 568 */         return ObjectOpenHashSet.this.key[ObjectOpenHashSet.this.n];
/*     */       } 
/* 570 */       K[] key = ObjectOpenHashSet.this.key;
/*     */       while (true) {
/* 572 */         if (--this.pos < 0) {
/*     */           
/* 574 */           this.last = Integer.MIN_VALUE;
/* 575 */           return this.wrapped.get(-this.pos - 1);
/*     */         } 
/* 577 */         if (key[this.pos] != null) return key[this.last = this.pos];
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
/* 591 */       K[] key = ObjectOpenHashSet.this.key; while (true) {
/*     */         K curr; int last;
/* 593 */         pos = (last = pos) + 1 & ObjectOpenHashSet.this.mask;
/*     */         while (true) {
/* 595 */           if ((curr = key[pos]) == null) {
/* 596 */             key[last] = null;
/*     */             return;
/*     */           } 
/* 599 */           int slot = HashCommon.mix(curr.hashCode()) & ObjectOpenHashSet.this.mask;
/* 600 */           if ((last <= pos) ? (last >= slot || slot > pos) : (last >= slot && slot > pos))
/* 601 */             break;  pos = pos + 1 & ObjectOpenHashSet.this.mask;
/*     */         } 
/* 603 */         if (pos < last) {
/* 604 */           if (this.wrapped == null) this.wrapped = new ObjectArrayList<>(2); 
/* 605 */           this.wrapped.add(key[pos]);
/*     */         } 
/* 607 */         key[last] = curr;
/*     */       } 
/*     */     }
/*     */ 
/*     */     
/*     */     public void remove() {
/* 613 */       if (this.last == -1) throw new IllegalStateException(); 
/* 614 */       if (this.last == ObjectOpenHashSet.this.n)
/* 615 */       { ObjectOpenHashSet.this.containsNull = false;
/* 616 */         ObjectOpenHashSet.this.key[ObjectOpenHashSet.this.n] = null; }
/* 617 */       else if (this.pos >= 0) { shiftKeys(this.last); }
/*     */       else
/*     */       
/* 620 */       { ObjectOpenHashSet.this.remove(this.wrapped.set(-this.pos - 1, null));
/* 621 */         this.last = -1;
/*     */         return; }
/*     */       
/* 624 */       ObjectOpenHashSet.this.size--;
/* 625 */       this.last = -1;
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     public void forEachRemaining(Consumer<? super K> action) {
/* 631 */       K[] key = ObjectOpenHashSet.this.key;
/* 632 */       if (this.mustReturnNull) {
/* 633 */         this.mustReturnNull = false;
/* 634 */         this.last = ObjectOpenHashSet.this.n;
/* 635 */         action.accept(key[ObjectOpenHashSet.this.n]);
/* 636 */         this.c--;
/*     */       } 
/* 638 */       while (this.c != 0) {
/* 639 */         if (--this.pos < 0) {
/*     */           
/* 641 */           this.last = Integer.MIN_VALUE;
/* 642 */           action.accept(this.wrapped.get(-this.pos - 1));
/* 643 */           this.c--; continue;
/* 644 */         }  if (key[this.pos] != null) {
/* 645 */           action.accept(key[this.last = this.pos]);
/* 646 */           this.c--;
/*     */         } 
/*     */       } 
/*     */     }
/*     */     
/*     */     private SetIterator() {} }
/*     */   
/*     */   public ObjectIterator<K> iterator() {
/* 654 */     return new SetIterator();
/*     */   }
/*     */ 
/*     */   
/*     */   private final class SetSpliterator
/*     */     implements ObjectSpliterator<K>
/*     */   {
/*     */     private static final int POST_SPLIT_CHARACTERISTICS = 1;
/*     */     
/* 663 */     int pos = 0;
/*     */     
/* 665 */     int max = ObjectOpenHashSet.this.n;
/*     */     
/* 667 */     int c = 0;
/*     */     
/* 669 */     boolean mustReturnNull = ObjectOpenHashSet.this.containsNull;
/*     */ 
/*     */     
/*     */     boolean hasSplit = false;
/*     */ 
/*     */     
/*     */     SetSpliterator(int pos, int max, boolean mustReturnNull, boolean hasSplit) {
/* 676 */       this.pos = pos;
/* 677 */       this.max = max;
/* 678 */       this.mustReturnNull = mustReturnNull;
/* 679 */       this.hasSplit = hasSplit;
/*     */     }
/*     */ 
/*     */     
/*     */     public boolean tryAdvance(Consumer<? super K> action) {
/* 684 */       if (this.mustReturnNull) {
/* 685 */         this.mustReturnNull = false;
/* 686 */         this.c++;
/* 687 */         action.accept(ObjectOpenHashSet.this.key[ObjectOpenHashSet.this.n]);
/* 688 */         return true;
/*     */       } 
/* 690 */       K[] key = ObjectOpenHashSet.this.key;
/* 691 */       while (this.pos < this.max) {
/* 692 */         if (key[this.pos] != null) {
/* 693 */           this.c++;
/* 694 */           action.accept(key[this.pos++]);
/* 695 */           return true;
/*     */         } 
/* 697 */         this.pos++;
/*     */       } 
/*     */       
/* 700 */       return false;
/*     */     }
/*     */ 
/*     */     
/*     */     public void forEachRemaining(Consumer<? super K> action) {
/* 705 */       K[] key = ObjectOpenHashSet.this.key;
/* 706 */       if (this.mustReturnNull) {
/* 707 */         this.mustReturnNull = false;
/* 708 */         action.accept(key[ObjectOpenHashSet.this.n]);
/* 709 */         this.c++;
/*     */       } 
/* 711 */       while (this.pos < this.max) {
/* 712 */         if (key[this.pos] != null) {
/* 713 */           action.accept(key[this.pos]);
/* 714 */           this.c++;
/*     */         } 
/* 716 */         this.pos++;
/*     */       } 
/*     */     }
/*     */ 
/*     */     
/*     */     public int characteristics() {
/* 722 */       return this.hasSplit ? 1 : 65;
/*     */     }
/*     */ 
/*     */     
/*     */     public long estimateSize() {
/* 727 */       if (!this.hasSplit)
/*     */       {
/* 729 */         return (ObjectOpenHashSet.this.size - this.c);
/*     */       }
/*     */ 
/*     */ 
/*     */       
/* 734 */       return Math.min((ObjectOpenHashSet.this.size - this.c), (long)(ObjectOpenHashSet.this.realSize() / ObjectOpenHashSet.this.n * (this.max - this.pos)) + (this.mustReturnNull ? 1L : 0L));
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     public SetSpliterator trySplit() {
/* 740 */       if (this.pos >= this.max - 1) return null; 
/* 741 */       int retLen = this.max - this.pos >> 1;
/* 742 */       if (retLen <= 1) return null; 
/* 743 */       int myNewPos = this.pos + retLen;
/* 744 */       int retPos = this.pos;
/* 745 */       int retMax = myNewPos;
/*     */ 
/*     */ 
/*     */       
/* 749 */       SetSpliterator split = new SetSpliterator(retPos, retMax, this.mustReturnNull, true);
/* 750 */       this.pos = myNewPos;
/* 751 */       this.mustReturnNull = false;
/* 752 */       this.hasSplit = true;
/* 753 */       return split;
/*     */     }
/*     */ 
/*     */     
/*     */     public long skip(long n) {
/* 758 */       if (n < 0L) throw new IllegalArgumentException("Argument must be nonnegative: " + n); 
/* 759 */       if (n == 0L) return 0L; 
/* 760 */       long skipped = 0L;
/* 761 */       if (this.mustReturnNull) {
/* 762 */         this.mustReturnNull = false;
/* 763 */         skipped++;
/* 764 */         n--;
/*     */       } 
/* 766 */       K[] key = ObjectOpenHashSet.this.key;
/* 767 */       while (this.pos < this.max && n > 0L) {
/* 768 */         if (key[this.pos++] != null) {
/* 769 */           skipped++;
/* 770 */           n--;
/*     */         } 
/*     */       } 
/* 773 */       return skipped;
/*     */     }
/*     */     
/*     */     SetSpliterator() {} }
/*     */   
/*     */   public ObjectSpliterator<K> spliterator() {
/* 779 */     return new SetSpliterator();
/*     */   }
/*     */ 
/*     */   
/*     */   public void forEach(Consumer<? super K> action) {
/* 784 */     if (this.containsNull) action.accept(this.key[this.n]); 
/* 785 */     K[] key = this.key;
/* 786 */     for (int pos = this.n; pos-- != 0;) { if (key[pos] != null) action.accept(key[pos]);
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
/* 803 */     return trim(this.size);
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
/* 825 */     int l = HashCommon.nextPowerOfTwo((int)Math.ceil((n / this.f)));
/* 826 */     if (l >= this.n || this.size > HashCommon.maxFill(l, this.f)) return true; 
/*     */     try {
/* 828 */       rehash(l);
/* 829 */     } catch (OutOfMemoryError cantDoIt) {
/* 830 */       return false;
/*     */     } 
/* 832 */     return true;
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
/* 847 */     K[] key = this.key;
/* 848 */     int mask = newN - 1;
/* 849 */     K[] newKey = (K[])new Object[newN + 1];
/* 850 */     int i = this.n;
/* 851 */     for (int j = realSize(); j-- != 0; ) {
/* 852 */       while (key[--i] == null); int pos;
/* 853 */       if (newKey[pos = HashCommon.mix(key[i].hashCode()) & mask] != null) while (newKey[pos = pos + 1 & mask] != null); 
/* 854 */       newKey[pos] = key[i];
/*     */     } 
/* 856 */     this.n = newN;
/* 857 */     this.mask = mask;
/* 858 */     this.maxFill = HashCommon.maxFill(this.n, this.f);
/* 859 */     this.key = newKey;
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
/*     */   public ObjectOpenHashSet<K> clone() {
/*     */     ObjectOpenHashSet<K> c;
/*     */     try {
/* 876 */       c = (ObjectOpenHashSet<K>)super.clone();
/* 877 */     } catch (CloneNotSupportedException cantHappen) {
/* 878 */       throw new InternalError();
/*     */     } 
/* 880 */     c.key = (K[])this.key.clone();
/* 881 */     c.containsNull = this.containsNull;
/* 882 */     return c;
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
/* 896 */     int h = 0;
/* 897 */     for (int j = realSize(), i = 0; j-- != 0; ) {
/* 898 */       for (; this.key[i] == null; i++);
/* 899 */       if (this != this.key[i]) h += this.key[i].hashCode(); 
/* 900 */       i++;
/*     */     } 
/*     */     
/* 903 */     return h;
/*     */   }
/*     */   
/*     */   private void writeObject(ObjectOutputStream s) throws IOException {
/* 907 */     ObjectIterator<K> i = iterator();
/* 908 */     s.defaultWriteObject();
/* 909 */     for (int j = this.size; j-- != 0; s.writeObject(i.next()));
/*     */   }
/*     */ 
/*     */   
/*     */   private void readObject(ObjectInputStream s) throws IOException, ClassNotFoundException {
/* 914 */     s.defaultReadObject();
/* 915 */     this.n = HashCommon.arraySize(this.size, this.f);
/* 916 */     this.maxFill = HashCommon.maxFill(this.n, this.f);
/* 917 */     this.mask = this.n - 1;
/* 918 */     K[] key = this.key = (K[])new Object[this.n + 1];
/*     */     
/* 920 */     for (int i = this.size; i-- != 0; ) {
/* 921 */       int pos; K k = (K)s.readObject();
/* 922 */       if (k == null)
/* 923 */       { pos = this.n;
/* 924 */         this.containsNull = true; }
/*     */       
/* 926 */       else if (key[pos = HashCommon.mix(k.hashCode()) & this.mask] != null) { while (key[pos = pos + 1 & this.mask] != null); }
/*     */       
/* 928 */       key[pos] = k;
/*     */     } 
/*     */   }
/*     */   
/*     */   private void checkTable() {}
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\i\\unimi\dsi\fastutil\objects\ObjectOpenHashSet.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */