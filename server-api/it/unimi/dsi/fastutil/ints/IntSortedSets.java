/*     */ package it.unimi.dsi.fastutil.ints;
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
/*     */ public final class IntSortedSets
/*     */ {
/*     */   public static class EmptySet
/*     */     extends IntSets.EmptySet
/*     */     implements IntSortedSet, Serializable, Cloneable
/*     */   {
/*     */     private static final long serialVersionUID = -7046029254386353129L;
/*     */     
/*     */     public IntBidirectionalIterator iterator(int from) {
/*  47 */       return IntIterators.EMPTY_ITERATOR;
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     public IntSortedSet subSet(int from, int to) {
/*  53 */       return IntSortedSets.EMPTY_SET;
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     public IntSortedSet headSet(int from) {
/*  59 */       return IntSortedSets.EMPTY_SET;
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     public IntSortedSet tailSet(int to) {
/*  65 */       return IntSortedSets.EMPTY_SET;
/*     */     }
/*     */ 
/*     */     
/*     */     public int firstInt() {
/*  70 */       throw new NoSuchElementException();
/*     */     }
/*     */ 
/*     */     
/*     */     public int lastInt() {
/*  75 */       throw new NoSuchElementException();
/*     */     }
/*     */ 
/*     */     
/*     */     public IntComparator comparator() {
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
/*     */     public IntSortedSet subSet(Integer from, Integer to) {
/*  91 */       return IntSortedSets.EMPTY_SET;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     @Deprecated
/*     */     public IntSortedSet headSet(Integer from) {
/* 102 */       return IntSortedSets.EMPTY_SET;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     @Deprecated
/*     */     public IntSortedSet tailSet(Integer to) {
/* 113 */       return IntSortedSets.EMPTY_SET;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     @Deprecated
/*     */     public Integer first() {
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
/*     */     public Integer last() {
/* 135 */       throw new NoSuchElementException();
/*     */     }
/*     */ 
/*     */     
/*     */     public Object clone() {
/* 140 */       return IntSortedSets.EMPTY_SET;
/*     */     }
/*     */     
/*     */     private Object readResolve() {
/* 144 */       return IntSortedSets.EMPTY_SET;
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
/*     */     extends IntSets.Singleton
/*     */     implements IntSortedSet, Serializable, Cloneable
/*     */   {
/*     */     private static final long serialVersionUID = -7046029254386353129L;
/*     */     
/*     */     final IntComparator comparator;
/*     */ 
/*     */     
/*     */     protected Singleton(int element, IntComparator comparator) {
/* 167 */       super(element);
/* 168 */       this.comparator = comparator;
/*     */     }
/*     */     
/*     */     Singleton(int element) {
/* 172 */       this(element, (IntComparator)null);
/*     */     }
/*     */     
/*     */     final int compare(int k1, int k2) {
/* 176 */       return (this.comparator == null) ? Integer.compare(k1, k2) : this.comparator.compare(k1, k2);
/*     */     }
/*     */ 
/*     */     
/*     */     public IntBidirectionalIterator iterator(int from) {
/* 181 */       IntBidirectionalIterator i = iterator();
/* 182 */       if (compare(this.element, from) <= 0) i.nextInt(); 
/* 183 */       return i;
/*     */     }
/*     */ 
/*     */     
/*     */     public IntComparator comparator() {
/* 188 */       return this.comparator;
/*     */     }
/*     */ 
/*     */     
/*     */     public IntSpliterator spliterator() {
/* 193 */       return IntSpliterators.singleton(this.element, this.comparator);
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     public IntSortedSet subSet(int from, int to) {
/* 199 */       if (compare(from, this.element) <= 0 && compare(this.element, to) < 0) return this; 
/* 200 */       return IntSortedSets.EMPTY_SET;
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     public IntSortedSet headSet(int to) {
/* 206 */       if (compare(this.element, to) < 0) return this; 
/* 207 */       return IntSortedSets.EMPTY_SET;
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     public IntSortedSet tailSet(int from) {
/* 213 */       if (compare(from, this.element) <= 0) return this; 
/* 214 */       return IntSortedSets.EMPTY_SET;
/*     */     }
/*     */ 
/*     */     
/*     */     public int firstInt() {
/* 219 */       return this.element;
/*     */     }
/*     */ 
/*     */     
/*     */     public int lastInt() {
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
/*     */     public IntSortedSet subSet(Integer from, Integer to) {
/* 235 */       return subSet(from.intValue(), to.intValue());
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     @Deprecated
/*     */     public IntSortedSet headSet(Integer to) {
/* 246 */       return headSet(to.intValue());
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     @Deprecated
/*     */     public IntSortedSet tailSet(Integer from) {
/* 257 */       return tailSet(from.intValue());
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     @Deprecated
/*     */     public Integer first() {
/* 268 */       return Integer.valueOf(this.element);
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     @Deprecated
/*     */     public Integer last() {
/* 279 */       return Integer.valueOf(this.element);
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
/*     */   public static IntSortedSet singleton(int element) {
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
/*     */   public static IntSortedSet singleton(int element, IntComparator comparator) {
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
/*     */   public static IntSortedSet singleton(Object element) {
/* 314 */     return new Singleton(((Integer)element).intValue());
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
/*     */   public static IntSortedSet singleton(Object element, IntComparator comparator) {
/* 326 */     return new Singleton(((Integer)element).intValue(), comparator);
/*     */   }
/*     */   
/*     */   public static class SynchronizedSortedSet
/*     */     extends IntSets.SynchronizedSet implements IntSortedSet, Serializable {
/*     */     private static final long serialVersionUID = -7046029254386353129L;
/*     */     protected final IntSortedSet sortedSet;
/*     */     
/*     */     protected SynchronizedSortedSet(IntSortedSet s, Object sync) {
/* 335 */       super(s, sync);
/* 336 */       this.sortedSet = s;
/*     */     }
/*     */     
/*     */     protected SynchronizedSortedSet(IntSortedSet s) {
/* 340 */       super(s);
/* 341 */       this.sortedSet = s;
/*     */     }
/*     */ 
/*     */     
/*     */     public IntComparator comparator() {
/* 346 */       synchronized (this.sync) {
/* 347 */         return this.sortedSet.comparator();
/*     */       } 
/*     */     }
/*     */ 
/*     */     
/*     */     public IntSortedSet subSet(int from, int to) {
/* 353 */       return new SynchronizedSortedSet(this.sortedSet.subSet(from, to), this.sync);
/*     */     }
/*     */ 
/*     */     
/*     */     public IntSortedSet headSet(int to) {
/* 358 */       return new SynchronizedSortedSet(this.sortedSet.headSet(to), this.sync);
/*     */     }
/*     */ 
/*     */     
/*     */     public IntSortedSet tailSet(int from) {
/* 363 */       return new SynchronizedSortedSet(this.sortedSet.tailSet(from), this.sync);
/*     */     }
/*     */ 
/*     */     
/*     */     public IntBidirectionalIterator iterator() {
/* 368 */       return this.sortedSet.iterator();
/*     */     }
/*     */ 
/*     */     
/*     */     public IntBidirectionalIterator iterator(int from) {
/* 373 */       return this.sortedSet.iterator(from);
/*     */     }
/*     */ 
/*     */     
/*     */     public int firstInt() {
/* 378 */       synchronized (this.sync) {
/* 379 */         return this.sortedSet.firstInt();
/*     */       } 
/*     */     }
/*     */ 
/*     */     
/*     */     public int lastInt() {
/* 385 */       synchronized (this.sync) {
/* 386 */         return this.sortedSet.lastInt();
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
/*     */     public Integer first() {
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
/*     */     public Integer last() {
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
/*     */     public IntSortedSet subSet(Integer from, Integer to) {
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
/*     */     public IntSortedSet headSet(Integer to) {
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
/*     */     public IntSortedSet tailSet(Integer from) {
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
/*     */   public static IntSortedSet synchronize(IntSortedSet s) {
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
/*     */   public static IntSortedSet synchronize(IntSortedSet s, Object sync) {
/* 471 */     return new SynchronizedSortedSet(s, sync);
/*     */   }
/*     */   
/*     */   public static class UnmodifiableSortedSet
/*     */     extends IntSets.UnmodifiableSet implements IntSortedSet, Serializable {
/*     */     private static final long serialVersionUID = -7046029254386353129L;
/*     */     protected final IntSortedSet sortedSet;
/*     */     
/*     */     protected UnmodifiableSortedSet(IntSortedSet s) {
/* 480 */       super(s);
/* 481 */       this.sortedSet = s;
/*     */     }
/*     */ 
/*     */     
/*     */     public IntComparator comparator() {
/* 486 */       return this.sortedSet.comparator();
/*     */     }
/*     */ 
/*     */     
/*     */     public IntSortedSet subSet(int from, int to) {
/* 491 */       return new UnmodifiableSortedSet(this.sortedSet.subSet(from, to));
/*     */     }
/*     */ 
/*     */     
/*     */     public IntSortedSet headSet(int to) {
/* 496 */       return new UnmodifiableSortedSet(this.sortedSet.headSet(to));
/*     */     }
/*     */ 
/*     */     
/*     */     public IntSortedSet tailSet(int from) {
/* 501 */       return new UnmodifiableSortedSet(this.sortedSet.tailSet(from));
/*     */     }
/*     */ 
/*     */     
/*     */     public IntBidirectionalIterator iterator() {
/* 506 */       return IntIterators.unmodifiable(this.sortedSet.iterator());
/*     */     }
/*     */ 
/*     */     
/*     */     public IntBidirectionalIterator iterator(int from) {
/* 511 */       return IntIterators.unmodifiable(this.sortedSet.iterator(from));
/*     */     }
/*     */ 
/*     */     
/*     */     public int firstInt() {
/* 516 */       return this.sortedSet.firstInt();
/*     */     }
/*     */ 
/*     */     
/*     */     public int lastInt() {
/* 521 */       return this.sortedSet.lastInt();
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     @Deprecated
/*     */     public Integer first() {
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
/*     */     public Integer last() {
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
/*     */     public IntSortedSet subSet(Integer from, Integer to) {
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
/*     */     public IntSortedSet headSet(Integer to) {
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
/*     */     public IntSortedSet tailSet(Integer from) {
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
/*     */   public static IntSortedSet unmodifiable(IntSortedSet s) {
/* 588 */     return new UnmodifiableSortedSet(s);
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\i\\unimi\dsi\fastutil\ints\IntSortedSets.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */