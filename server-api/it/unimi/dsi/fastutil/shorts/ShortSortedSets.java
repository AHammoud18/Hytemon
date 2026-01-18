/*     */ package it.unimi.dsi.fastutil.shorts;
/*     */ 
/*     */ import it.unimi.dsi.fastutil.SafeMath;
/*     */ import it.unimi.dsi.fastutil.ints.IntSpliterator;
/*     */ import it.unimi.dsi.fastutil.ints.IntSpliterators;
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
/*     */ public final class ShortSortedSets
/*     */ {
/*     */   public static class EmptySet
/*     */     extends ShortSets.EmptySet
/*     */     implements ShortSortedSet, Serializable, Cloneable
/*     */   {
/*     */     private static final long serialVersionUID = -7046029254386353129L;
/*     */     
/*     */     public ShortBidirectionalIterator iterator(short from) {
/*  49 */       return ShortIterators.EMPTY_ITERATOR;
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     public ShortSortedSet subSet(short from, short to) {
/*  55 */       return ShortSortedSets.EMPTY_SET;
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     public ShortSortedSet headSet(short from) {
/*  61 */       return ShortSortedSets.EMPTY_SET;
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     public ShortSortedSet tailSet(short to) {
/*  67 */       return ShortSortedSets.EMPTY_SET;
/*     */     }
/*     */ 
/*     */     
/*     */     public short firstShort() {
/*  72 */       throw new NoSuchElementException();
/*     */     }
/*     */ 
/*     */     
/*     */     public short lastShort() {
/*  77 */       throw new NoSuchElementException();
/*     */     }
/*     */ 
/*     */     
/*     */     public ShortComparator comparator() {
/*  82 */       return null;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     @Deprecated
/*     */     public ShortSortedSet subSet(Short from, Short to) {
/*  93 */       return ShortSortedSets.EMPTY_SET;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     @Deprecated
/*     */     public ShortSortedSet headSet(Short from) {
/* 104 */       return ShortSortedSets.EMPTY_SET;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     @Deprecated
/*     */     public ShortSortedSet tailSet(Short to) {
/* 115 */       return ShortSortedSets.EMPTY_SET;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     @Deprecated
/*     */     public Short first() {
/* 126 */       throw new NoSuchElementException();
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     @Deprecated
/*     */     public Short last() {
/* 137 */       throw new NoSuchElementException();
/*     */     }
/*     */ 
/*     */     
/*     */     public Object clone() {
/* 142 */       return ShortSortedSets.EMPTY_SET;
/*     */     }
/*     */     
/*     */     private Object readResolve() {
/* 146 */       return ShortSortedSets.EMPTY_SET;
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 155 */   public static final EmptySet EMPTY_SET = new EmptySet();
/*     */ 
/*     */ 
/*     */   
/*     */   public static class Singleton
/*     */     extends ShortSets.Singleton
/*     */     implements ShortSortedSet, Serializable, Cloneable
/*     */   {
/*     */     private static final long serialVersionUID = -7046029254386353129L;
/*     */     
/*     */     final ShortComparator comparator;
/*     */ 
/*     */     
/*     */     protected Singleton(short element, ShortComparator comparator) {
/* 169 */       super(element);
/* 170 */       this.comparator = comparator;
/*     */     }
/*     */     
/*     */     Singleton(short element) {
/* 174 */       this(element, (ShortComparator)null);
/*     */     }
/*     */     
/*     */     final int compare(short k1, short k2) {
/* 178 */       return (this.comparator == null) ? Short.compare(k1, k2) : this.comparator.compare(k1, k2);
/*     */     }
/*     */ 
/*     */     
/*     */     public ShortBidirectionalIterator iterator(short from) {
/* 183 */       ShortBidirectionalIterator i = iterator();
/* 184 */       if (compare(this.element, from) <= 0) i.nextShort(); 
/* 185 */       return i;
/*     */     }
/*     */ 
/*     */     
/*     */     public ShortComparator comparator() {
/* 190 */       return this.comparator;
/*     */     }
/*     */ 
/*     */     
/*     */     public ShortSpliterator spliterator() {
/* 195 */       return ShortSpliterators.singleton(this.element, this.comparator);
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     public ShortSortedSet subSet(short from, short to) {
/* 201 */       if (compare(from, this.element) <= 0 && compare(this.element, to) < 0) return this; 
/* 202 */       return ShortSortedSets.EMPTY_SET;
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     public ShortSortedSet headSet(short to) {
/* 208 */       if (compare(this.element, to) < 0) return this; 
/* 209 */       return ShortSortedSets.EMPTY_SET;
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     public ShortSortedSet tailSet(short from) {
/* 215 */       if (compare(from, this.element) <= 0) return this; 
/* 216 */       return ShortSortedSets.EMPTY_SET;
/*     */     }
/*     */ 
/*     */     
/*     */     public short firstShort() {
/* 221 */       return this.element;
/*     */     }
/*     */ 
/*     */     
/*     */     public short lastShort() {
/* 226 */       return this.element;
/*     */     }
/*     */ 
/*     */     
/*     */     public IntSpliterator intSpliterator() {
/* 231 */       return IntSpliterators.singleton(this.element, (left, right) -> comparator().compare(SafeMath.safeIntToShort(left), SafeMath.safeIntToShort(right)));
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     @Deprecated
/*     */     public ShortSortedSet subSet(Short from, Short to) {
/* 242 */       return subSet(from.shortValue(), to.shortValue());
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     @Deprecated
/*     */     public ShortSortedSet headSet(Short to) {
/* 253 */       return headSet(to.shortValue());
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     @Deprecated
/*     */     public ShortSortedSet tailSet(Short from) {
/* 264 */       return tailSet(from.shortValue());
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     @Deprecated
/*     */     public Short first() {
/* 275 */       return Short.valueOf(this.element);
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     @Deprecated
/*     */     public Short last() {
/* 286 */       return Short.valueOf(this.element);
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
/*     */   public static ShortSortedSet singleton(short element) {
/* 298 */     return new Singleton(element);
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
/*     */   public static ShortSortedSet singleton(short element, ShortComparator comparator) {
/* 310 */     return new Singleton(element, comparator);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static ShortSortedSet singleton(Object element) {
/* 321 */     return new Singleton(((Short)element).shortValue());
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
/*     */   public static ShortSortedSet singleton(Object element, ShortComparator comparator) {
/* 333 */     return new Singleton(((Short)element).shortValue(), comparator);
/*     */   }
/*     */   
/*     */   public static class SynchronizedSortedSet
/*     */     extends ShortSets.SynchronizedSet implements ShortSortedSet, Serializable {
/*     */     private static final long serialVersionUID = -7046029254386353129L;
/*     */     protected final ShortSortedSet sortedSet;
/*     */     
/*     */     protected SynchronizedSortedSet(ShortSortedSet s, Object sync) {
/* 342 */       super(s, sync);
/* 343 */       this.sortedSet = s;
/*     */     }
/*     */     
/*     */     protected SynchronizedSortedSet(ShortSortedSet s) {
/* 347 */       super(s);
/* 348 */       this.sortedSet = s;
/*     */     }
/*     */ 
/*     */     
/*     */     public ShortComparator comparator() {
/* 353 */       synchronized (this.sync) {
/* 354 */         return this.sortedSet.comparator();
/*     */       } 
/*     */     }
/*     */ 
/*     */     
/*     */     public ShortSortedSet subSet(short from, short to) {
/* 360 */       return new SynchronizedSortedSet(this.sortedSet.subSet(from, to), this.sync);
/*     */     }
/*     */ 
/*     */     
/*     */     public ShortSortedSet headSet(short to) {
/* 365 */       return new SynchronizedSortedSet(this.sortedSet.headSet(to), this.sync);
/*     */     }
/*     */ 
/*     */     
/*     */     public ShortSortedSet tailSet(short from) {
/* 370 */       return new SynchronizedSortedSet(this.sortedSet.tailSet(from), this.sync);
/*     */     }
/*     */ 
/*     */     
/*     */     public ShortBidirectionalIterator iterator() {
/* 375 */       return this.sortedSet.iterator();
/*     */     }
/*     */ 
/*     */     
/*     */     public ShortBidirectionalIterator iterator(short from) {
/* 380 */       return this.sortedSet.iterator(from);
/*     */     }
/*     */ 
/*     */     
/*     */     public short firstShort() {
/* 385 */       synchronized (this.sync) {
/* 386 */         return this.sortedSet.firstShort();
/*     */       } 
/*     */     }
/*     */ 
/*     */     
/*     */     public short lastShort() {
/* 392 */       synchronized (this.sync) {
/* 393 */         return this.sortedSet.lastShort();
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
/*     */     public Short first() {
/* 405 */       synchronized (this.sync) {
/* 406 */         return this.sortedSet.first();
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
/*     */     public Short last() {
/* 418 */       synchronized (this.sync) {
/* 419 */         return this.sortedSet.last();
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
/*     */     public ShortSortedSet subSet(Short from, Short to) {
/* 431 */       return new SynchronizedSortedSet(this.sortedSet.subSet(from, to), this.sync);
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     @Deprecated
/*     */     public ShortSortedSet headSet(Short to) {
/* 442 */       return new SynchronizedSortedSet(this.sortedSet.headSet(to), this.sync);
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     @Deprecated
/*     */     public ShortSortedSet tailSet(Short from) {
/* 453 */       return new SynchronizedSortedSet(this.sortedSet.tailSet(from), this.sync);
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
/*     */   public static ShortSortedSet synchronize(ShortSortedSet s) {
/* 465 */     return new SynchronizedSortedSet(s);
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
/*     */   public static ShortSortedSet synchronize(ShortSortedSet s, Object sync) {
/* 478 */     return new SynchronizedSortedSet(s, sync);
/*     */   }
/*     */   
/*     */   public static class UnmodifiableSortedSet
/*     */     extends ShortSets.UnmodifiableSet implements ShortSortedSet, Serializable {
/*     */     private static final long serialVersionUID = -7046029254386353129L;
/*     */     protected final ShortSortedSet sortedSet;
/*     */     
/*     */     protected UnmodifiableSortedSet(ShortSortedSet s) {
/* 487 */       super(s);
/* 488 */       this.sortedSet = s;
/*     */     }
/*     */ 
/*     */     
/*     */     public ShortComparator comparator() {
/* 493 */       return this.sortedSet.comparator();
/*     */     }
/*     */ 
/*     */     
/*     */     public ShortSortedSet subSet(short from, short to) {
/* 498 */       return new UnmodifiableSortedSet(this.sortedSet.subSet(from, to));
/*     */     }
/*     */ 
/*     */     
/*     */     public ShortSortedSet headSet(short to) {
/* 503 */       return new UnmodifiableSortedSet(this.sortedSet.headSet(to));
/*     */     }
/*     */ 
/*     */     
/*     */     public ShortSortedSet tailSet(short from) {
/* 508 */       return new UnmodifiableSortedSet(this.sortedSet.tailSet(from));
/*     */     }
/*     */ 
/*     */     
/*     */     public ShortBidirectionalIterator iterator() {
/* 513 */       return ShortIterators.unmodifiable(this.sortedSet.iterator());
/*     */     }
/*     */ 
/*     */     
/*     */     public ShortBidirectionalIterator iterator(short from) {
/* 518 */       return ShortIterators.unmodifiable(this.sortedSet.iterator(from));
/*     */     }
/*     */ 
/*     */     
/*     */     public short firstShort() {
/* 523 */       return this.sortedSet.firstShort();
/*     */     }
/*     */ 
/*     */     
/*     */     public short lastShort() {
/* 528 */       return this.sortedSet.lastShort();
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     @Deprecated
/*     */     public Short first() {
/* 539 */       return this.sortedSet.first();
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     @Deprecated
/*     */     public Short last() {
/* 550 */       return this.sortedSet.last();
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     @Deprecated
/*     */     public ShortSortedSet subSet(Short from, Short to) {
/* 561 */       return new UnmodifiableSortedSet(this.sortedSet.subSet(from, to));
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     @Deprecated
/*     */     public ShortSortedSet headSet(Short to) {
/* 572 */       return new UnmodifiableSortedSet(this.sortedSet.headSet(to));
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     @Deprecated
/*     */     public ShortSortedSet tailSet(Short from) {
/* 583 */       return new UnmodifiableSortedSet(this.sortedSet.tailSet(from));
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
/*     */   public static ShortSortedSet unmodifiable(ShortSortedSet s) {
/* 595 */     return new UnmodifiableSortedSet(s);
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\i\\unimi\dsi\fastutil\shorts\ShortSortedSets.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */