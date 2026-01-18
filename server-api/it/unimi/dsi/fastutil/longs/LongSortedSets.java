/*     */ package it.unimi.dsi.fastutil.longs;
/*     */ 
/*     */ import java.io.Serializable;
/*     */ import java.util.Comparator;
/*     */ import java.util.Iterator;
/*     */ import java.util.NoSuchElementException;
/*     */ import java.util.SortedSet;
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
/*     */ public final class LongSortedSets
/*     */ {
/*     */   public static class EmptySet
/*     */     extends LongSets.EmptySet
/*     */     implements LongSortedSet, Serializable, Cloneable
/*     */   {
/*     */     private static final long serialVersionUID = -7046029254386353129L;
/*     */     
/*     */     public LongBidirectionalIterator iterator(long from) {
/*  47 */       return LongIterators.EMPTY_ITERATOR;
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     public LongSortedSet subSet(long from, long to) {
/*  53 */       return LongSortedSets.EMPTY_SET;
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     public LongSortedSet headSet(long from) {
/*  59 */       return LongSortedSets.EMPTY_SET;
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     public LongSortedSet tailSet(long to) {
/*  65 */       return LongSortedSets.EMPTY_SET;
/*     */     }
/*     */ 
/*     */     
/*     */     public long firstLong() {
/*  70 */       throw new NoSuchElementException();
/*     */     }
/*     */ 
/*     */     
/*     */     public long lastLong() {
/*  75 */       throw new NoSuchElementException();
/*     */     }
/*     */ 
/*     */     
/*     */     public LongComparator comparator() {
/*  80 */       return null;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     @Deprecated
/*     */     public LongSortedSet subSet(Long from, Long to) {
/*  91 */       return LongSortedSets.EMPTY_SET;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     @Deprecated
/*     */     public LongSortedSet headSet(Long from) {
/* 102 */       return LongSortedSets.EMPTY_SET;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     @Deprecated
/*     */     public LongSortedSet tailSet(Long to) {
/* 113 */       return LongSortedSets.EMPTY_SET;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     @Deprecated
/*     */     public Long first() {
/* 124 */       throw new NoSuchElementException();
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     @Deprecated
/*     */     public Long last() {
/* 135 */       throw new NoSuchElementException();
/*     */     }
/*     */ 
/*     */     
/*     */     public Object clone() {
/* 140 */       return LongSortedSets.EMPTY_SET;
/*     */     }
/*     */     
/*     */     private Object readResolve() {
/* 144 */       return LongSortedSets.EMPTY_SET;
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 153 */   public static final EmptySet EMPTY_SET = new EmptySet();
/*     */ 
/*     */ 
/*     */   
/*     */   public static class Singleton
/*     */     extends LongSets.Singleton
/*     */     implements LongSortedSet, Serializable, Cloneable
/*     */   {
/*     */     private static final long serialVersionUID = -7046029254386353129L;
/*     */     
/*     */     final LongComparator comparator;
/*     */ 
/*     */     
/*     */     protected Singleton(long element, LongComparator comparator) {
/* 167 */       super(element);
/* 168 */       this.comparator = comparator;
/*     */     }
/*     */     
/*     */     Singleton(long element) {
/* 172 */       this(element, (LongComparator)null);
/*     */     }
/*     */     
/*     */     final int compare(long k1, long k2) {
/* 176 */       return (this.comparator == null) ? Long.compare(k1, k2) : this.comparator.compare(k1, k2);
/*     */     }
/*     */ 
/*     */     
/*     */     public LongBidirectionalIterator iterator(long from) {
/* 181 */       LongBidirectionalIterator i = iterator();
/* 182 */       if (compare(this.element, from) <= 0) i.nextLong(); 
/* 183 */       return i;
/*     */     }
/*     */ 
/*     */     
/*     */     public LongComparator comparator() {
/* 188 */       return this.comparator;
/*     */     }
/*     */ 
/*     */     
/*     */     public LongSpliterator spliterator() {
/* 193 */       return LongSpliterators.singleton(this.element, this.comparator);
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     public LongSortedSet subSet(long from, long to) {
/* 199 */       if (compare(from, this.element) <= 0 && compare(this.element, to) < 0) return this; 
/* 200 */       return LongSortedSets.EMPTY_SET;
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     public LongSortedSet headSet(long to) {
/* 206 */       if (compare(this.element, to) < 0) return this; 
/* 207 */       return LongSortedSets.EMPTY_SET;
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     public LongSortedSet tailSet(long from) {
/* 213 */       if (compare(from, this.element) <= 0) return this; 
/* 214 */       return LongSortedSets.EMPTY_SET;
/*     */     }
/*     */ 
/*     */     
/*     */     public long firstLong() {
/* 219 */       return this.element;
/*     */     }
/*     */ 
/*     */     
/*     */     public long lastLong() {
/* 224 */       return this.element;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     @Deprecated
/*     */     public LongSortedSet subSet(Long from, Long to) {
/* 235 */       return subSet(from.longValue(), to.longValue());
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     @Deprecated
/*     */     public LongSortedSet headSet(Long to) {
/* 246 */       return headSet(to.longValue());
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     @Deprecated
/*     */     public LongSortedSet tailSet(Long from) {
/* 257 */       return tailSet(from.longValue());
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     @Deprecated
/*     */     public Long first() {
/* 268 */       return Long.valueOf(this.element);
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     @Deprecated
/*     */     public Long last() {
/* 279 */       return Long.valueOf(this.element);
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
/*     */   public static LongSortedSet singleton(long element) {
/* 291 */     return new Singleton(element);
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
/*     */   public static LongSortedSet singleton(long element, LongComparator comparator) {
/* 303 */     return new Singleton(element, comparator);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static LongSortedSet singleton(Object element) {
/* 314 */     return new Singleton(((Long)element).longValue());
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
/*     */   public static LongSortedSet singleton(Object element, LongComparator comparator) {
/* 326 */     return new Singleton(((Long)element).longValue(), comparator);
/*     */   }
/*     */   
/*     */   public static class SynchronizedSortedSet
/*     */     extends LongSets.SynchronizedSet implements LongSortedSet, Serializable {
/*     */     private static final long serialVersionUID = -7046029254386353129L;
/*     */     protected final LongSortedSet sortedSet;
/*     */     
/*     */     protected SynchronizedSortedSet(LongSortedSet s, Object sync) {
/* 335 */       super(s, sync);
/* 336 */       this.sortedSet = s;
/*     */     }
/*     */     
/*     */     protected SynchronizedSortedSet(LongSortedSet s) {
/* 340 */       super(s);
/* 341 */       this.sortedSet = s;
/*     */     }
/*     */ 
/*     */     
/*     */     public LongComparator comparator() {
/* 346 */       synchronized (this.sync) {
/* 347 */         return this.sortedSet.comparator();
/*     */       } 
/*     */     }
/*     */ 
/*     */     
/*     */     public LongSortedSet subSet(long from, long to) {
/* 353 */       return new SynchronizedSortedSet(this.sortedSet.subSet(from, to), this.sync);
/*     */     }
/*     */ 
/*     */     
/*     */     public LongSortedSet headSet(long to) {
/* 358 */       return new SynchronizedSortedSet(this.sortedSet.headSet(to), this.sync);
/*     */     }
/*     */ 
/*     */     
/*     */     public LongSortedSet tailSet(long from) {
/* 363 */       return new SynchronizedSortedSet(this.sortedSet.tailSet(from), this.sync);
/*     */     }
/*     */ 
/*     */     
/*     */     public LongBidirectionalIterator iterator() {
/* 368 */       return this.sortedSet.iterator();
/*     */     }
/*     */ 
/*     */     
/*     */     public LongBidirectionalIterator iterator(long from) {
/* 373 */       return this.sortedSet.iterator(from);
/*     */     }
/*     */ 
/*     */     
/*     */     public long firstLong() {
/* 378 */       synchronized (this.sync) {
/* 379 */         return this.sortedSet.firstLong();
/*     */       } 
/*     */     }
/*     */ 
/*     */     
/*     */     public long lastLong() {
/* 385 */       synchronized (this.sync) {
/* 386 */         return this.sortedSet.lastLong();
/*     */       } 
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     @Deprecated
/*     */     public Long first() {
/* 398 */       synchronized (this.sync) {
/* 399 */         return this.sortedSet.first();
/*     */       } 
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     @Deprecated
/*     */     public Long last() {
/* 411 */       synchronized (this.sync) {
/* 412 */         return this.sortedSet.last();
/*     */       } 
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     @Deprecated
/*     */     public LongSortedSet subSet(Long from, Long to) {
/* 424 */       return new SynchronizedSortedSet(this.sortedSet.subSet(from, to), this.sync);
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     @Deprecated
/*     */     public LongSortedSet headSet(Long to) {
/* 435 */       return new SynchronizedSortedSet(this.sortedSet.headSet(to), this.sync);
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     @Deprecated
/*     */     public LongSortedSet tailSet(Long from) {
/* 446 */       return new SynchronizedSortedSet(this.sortedSet.tailSet(from), this.sync);
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
/*     */   public static LongSortedSet synchronize(LongSortedSet s) {
/* 458 */     return new SynchronizedSortedSet(s);
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
/*     */   public static LongSortedSet synchronize(LongSortedSet s, Object sync) {
/* 471 */     return new SynchronizedSortedSet(s, sync);
/*     */   }
/*     */   
/*     */   public static class UnmodifiableSortedSet
/*     */     extends LongSets.UnmodifiableSet implements LongSortedSet, Serializable {
/*     */     private static final long serialVersionUID = -7046029254386353129L;
/*     */     protected final LongSortedSet sortedSet;
/*     */     
/*     */     protected UnmodifiableSortedSet(LongSortedSet s) {
/* 480 */       super(s);
/* 481 */       this.sortedSet = s;
/*     */     }
/*     */ 
/*     */     
/*     */     public LongComparator comparator() {
/* 486 */       return this.sortedSet.comparator();
/*     */     }
/*     */ 
/*     */     
/*     */     public LongSortedSet subSet(long from, long to) {
/* 491 */       return new UnmodifiableSortedSet(this.sortedSet.subSet(from, to));
/*     */     }
/*     */ 
/*     */     
/*     */     public LongSortedSet headSet(long to) {
/* 496 */       return new UnmodifiableSortedSet(this.sortedSet.headSet(to));
/*     */     }
/*     */ 
/*     */     
/*     */     public LongSortedSet tailSet(long from) {
/* 501 */       return new UnmodifiableSortedSet(this.sortedSet.tailSet(from));
/*     */     }
/*     */ 
/*     */     
/*     */     public LongBidirectionalIterator iterator() {
/* 506 */       return LongIterators.unmodifiable(this.sortedSet.iterator());
/*     */     }
/*     */ 
/*     */     
/*     */     public LongBidirectionalIterator iterator(long from) {
/* 511 */       return LongIterators.unmodifiable(this.sortedSet.iterator(from));
/*     */     }
/*     */ 
/*     */     
/*     */     public long firstLong() {
/* 516 */       return this.sortedSet.firstLong();
/*     */     }
/*     */ 
/*     */     
/*     */     public long lastLong() {
/* 521 */       return this.sortedSet.lastLong();
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     @Deprecated
/*     */     public Long first() {
/* 532 */       return this.sortedSet.first();
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     @Deprecated
/*     */     public Long last() {
/* 543 */       return this.sortedSet.last();
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     @Deprecated
/*     */     public LongSortedSet subSet(Long from, Long to) {
/* 554 */       return new UnmodifiableSortedSet(this.sortedSet.subSet(from, to));
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     @Deprecated
/*     */     public LongSortedSet headSet(Long to) {
/* 565 */       return new UnmodifiableSortedSet(this.sortedSet.headSet(to));
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     @Deprecated
/*     */     public LongSortedSet tailSet(Long from) {
/* 576 */       return new UnmodifiableSortedSet(this.sortedSet.tailSet(from));
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
/*     */   public static LongSortedSet unmodifiable(LongSortedSet s) {
/* 588 */     return new UnmodifiableSortedSet(s);
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\i\\unimi\dsi\fastutil\longs\LongSortedSets.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */