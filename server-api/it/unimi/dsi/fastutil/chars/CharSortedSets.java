/*     */ package it.unimi.dsi.fastutil.chars;
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
/*     */ public final class CharSortedSets
/*     */ {
/*     */   public static class EmptySet
/*     */     extends CharSets.EmptySet
/*     */     implements CharSortedSet, Serializable, Cloneable
/*     */   {
/*     */     private static final long serialVersionUID = -7046029254386353129L;
/*     */     
/*     */     public CharBidirectionalIterator iterator(char from) {
/*  49 */       return CharIterators.EMPTY_ITERATOR;
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     public CharSortedSet subSet(char from, char to) {
/*  55 */       return CharSortedSets.EMPTY_SET;
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     public CharSortedSet headSet(char from) {
/*  61 */       return CharSortedSets.EMPTY_SET;
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     public CharSortedSet tailSet(char to) {
/*  67 */       return CharSortedSets.EMPTY_SET;
/*     */     }
/*     */ 
/*     */     
/*     */     public char firstChar() {
/*  72 */       throw new NoSuchElementException();
/*     */     }
/*     */ 
/*     */     
/*     */     public char lastChar() {
/*  77 */       throw new NoSuchElementException();
/*     */     }
/*     */ 
/*     */     
/*     */     public CharComparator comparator() {
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
/*     */     public CharSortedSet subSet(Character from, Character to) {
/*  93 */       return CharSortedSets.EMPTY_SET;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     @Deprecated
/*     */     public CharSortedSet headSet(Character from) {
/* 104 */       return CharSortedSets.EMPTY_SET;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     @Deprecated
/*     */     public CharSortedSet tailSet(Character to) {
/* 115 */       return CharSortedSets.EMPTY_SET;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     @Deprecated
/*     */     public Character first() {
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
/*     */     public Character last() {
/* 137 */       throw new NoSuchElementException();
/*     */     }
/*     */ 
/*     */     
/*     */     public Object clone() {
/* 142 */       return CharSortedSets.EMPTY_SET;
/*     */     }
/*     */     
/*     */     private Object readResolve() {
/* 146 */       return CharSortedSets.EMPTY_SET;
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
/*     */     extends CharSets.Singleton
/*     */     implements CharSortedSet, Serializable, Cloneable
/*     */   {
/*     */     private static final long serialVersionUID = -7046029254386353129L;
/*     */     
/*     */     final CharComparator comparator;
/*     */ 
/*     */     
/*     */     protected Singleton(char element, CharComparator comparator) {
/* 169 */       super(element);
/* 170 */       this.comparator = comparator;
/*     */     }
/*     */     
/*     */     Singleton(char element) {
/* 174 */       this(element, (CharComparator)null);
/*     */     }
/*     */     
/*     */     final int compare(char k1, char k2) {
/* 178 */       return (this.comparator == null) ? Character.compare(k1, k2) : this.comparator.compare(k1, k2);
/*     */     }
/*     */ 
/*     */     
/*     */     public CharBidirectionalIterator iterator(char from) {
/* 183 */       CharBidirectionalIterator i = iterator();
/* 184 */       if (compare(this.element, from) <= 0) i.nextChar(); 
/* 185 */       return i;
/*     */     }
/*     */ 
/*     */     
/*     */     public CharComparator comparator() {
/* 190 */       return this.comparator;
/*     */     }
/*     */ 
/*     */     
/*     */     public CharSpliterator spliterator() {
/* 195 */       return CharSpliterators.singleton(this.element, this.comparator);
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     public CharSortedSet subSet(char from, char to) {
/* 201 */       if (compare(from, this.element) <= 0 && compare(this.element, to) < 0) return this; 
/* 202 */       return CharSortedSets.EMPTY_SET;
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     public CharSortedSet headSet(char to) {
/* 208 */       if (compare(this.element, to) < 0) return this; 
/* 209 */       return CharSortedSets.EMPTY_SET;
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     public CharSortedSet tailSet(char from) {
/* 215 */       if (compare(from, this.element) <= 0) return this; 
/* 216 */       return CharSortedSets.EMPTY_SET;
/*     */     }
/*     */ 
/*     */     
/*     */     public char firstChar() {
/* 221 */       return this.element;
/*     */     }
/*     */ 
/*     */     
/*     */     public char lastChar() {
/* 226 */       return this.element;
/*     */     }
/*     */ 
/*     */     
/*     */     public IntSpliterator intSpliterator() {
/* 231 */       return IntSpliterators.singleton(this.element, (left, right) -> comparator().compare(SafeMath.safeIntToChar(left), SafeMath.safeIntToChar(right)));
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     @Deprecated
/*     */     public CharSortedSet subSet(Character from, Character to) {
/* 242 */       return subSet(from.charValue(), to.charValue());
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     @Deprecated
/*     */     public CharSortedSet headSet(Character to) {
/* 253 */       return headSet(to.charValue());
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     @Deprecated
/*     */     public CharSortedSet tailSet(Character from) {
/* 264 */       return tailSet(from.charValue());
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     @Deprecated
/*     */     public Character first() {
/* 275 */       return Character.valueOf(this.element);
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     @Deprecated
/*     */     public Character last() {
/* 286 */       return Character.valueOf(this.element);
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
/*     */   public static CharSortedSet singleton(char element) {
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
/*     */   public static CharSortedSet singleton(char element, CharComparator comparator) {
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
/*     */   public static CharSortedSet singleton(Object element) {
/* 321 */     return new Singleton(((Character)element).charValue());
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
/*     */   public static CharSortedSet singleton(Object element, CharComparator comparator) {
/* 333 */     return new Singleton(((Character)element).charValue(), comparator);
/*     */   }
/*     */   
/*     */   public static class SynchronizedSortedSet
/*     */     extends CharSets.SynchronizedSet implements CharSortedSet, Serializable {
/*     */     private static final long serialVersionUID = -7046029254386353129L;
/*     */     protected final CharSortedSet sortedSet;
/*     */     
/*     */     protected SynchronizedSortedSet(CharSortedSet s, Object sync) {
/* 342 */       super(s, sync);
/* 343 */       this.sortedSet = s;
/*     */     }
/*     */     
/*     */     protected SynchronizedSortedSet(CharSortedSet s) {
/* 347 */       super(s);
/* 348 */       this.sortedSet = s;
/*     */     }
/*     */ 
/*     */     
/*     */     public CharComparator comparator() {
/* 353 */       synchronized (this.sync) {
/* 354 */         return this.sortedSet.comparator();
/*     */       } 
/*     */     }
/*     */ 
/*     */     
/*     */     public CharSortedSet subSet(char from, char to) {
/* 360 */       return new SynchronizedSortedSet(this.sortedSet.subSet(from, to), this.sync);
/*     */     }
/*     */ 
/*     */     
/*     */     public CharSortedSet headSet(char to) {
/* 365 */       return new SynchronizedSortedSet(this.sortedSet.headSet(to), this.sync);
/*     */     }
/*     */ 
/*     */     
/*     */     public CharSortedSet tailSet(char from) {
/* 370 */       return new SynchronizedSortedSet(this.sortedSet.tailSet(from), this.sync);
/*     */     }
/*     */ 
/*     */     
/*     */     public CharBidirectionalIterator iterator() {
/* 375 */       return this.sortedSet.iterator();
/*     */     }
/*     */ 
/*     */     
/*     */     public CharBidirectionalIterator iterator(char from) {
/* 380 */       return this.sortedSet.iterator(from);
/*     */     }
/*     */ 
/*     */     
/*     */     public char firstChar() {
/* 385 */       synchronized (this.sync) {
/* 386 */         return this.sortedSet.firstChar();
/*     */       } 
/*     */     }
/*     */ 
/*     */     
/*     */     public char lastChar() {
/* 392 */       synchronized (this.sync) {
/* 393 */         return this.sortedSet.lastChar();
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
/*     */     public Character first() {
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
/*     */     public Character last() {
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
/*     */     public CharSortedSet subSet(Character from, Character to) {
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
/*     */     public CharSortedSet headSet(Character to) {
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
/*     */     public CharSortedSet tailSet(Character from) {
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
/*     */   public static CharSortedSet synchronize(CharSortedSet s) {
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
/*     */   public static CharSortedSet synchronize(CharSortedSet s, Object sync) {
/* 478 */     return new SynchronizedSortedSet(s, sync);
/*     */   }
/*     */   
/*     */   public static class UnmodifiableSortedSet
/*     */     extends CharSets.UnmodifiableSet implements CharSortedSet, Serializable {
/*     */     private static final long serialVersionUID = -7046029254386353129L;
/*     */     protected final CharSortedSet sortedSet;
/*     */     
/*     */     protected UnmodifiableSortedSet(CharSortedSet s) {
/* 487 */       super(s);
/* 488 */       this.sortedSet = s;
/*     */     }
/*     */ 
/*     */     
/*     */     public CharComparator comparator() {
/* 493 */       return this.sortedSet.comparator();
/*     */     }
/*     */ 
/*     */     
/*     */     public CharSortedSet subSet(char from, char to) {
/* 498 */       return new UnmodifiableSortedSet(this.sortedSet.subSet(from, to));
/*     */     }
/*     */ 
/*     */     
/*     */     public CharSortedSet headSet(char to) {
/* 503 */       return new UnmodifiableSortedSet(this.sortedSet.headSet(to));
/*     */     }
/*     */ 
/*     */     
/*     */     public CharSortedSet tailSet(char from) {
/* 508 */       return new UnmodifiableSortedSet(this.sortedSet.tailSet(from));
/*     */     }
/*     */ 
/*     */     
/*     */     public CharBidirectionalIterator iterator() {
/* 513 */       return CharIterators.unmodifiable(this.sortedSet.iterator());
/*     */     }
/*     */ 
/*     */     
/*     */     public CharBidirectionalIterator iterator(char from) {
/* 518 */       return CharIterators.unmodifiable(this.sortedSet.iterator(from));
/*     */     }
/*     */ 
/*     */     
/*     */     public char firstChar() {
/* 523 */       return this.sortedSet.firstChar();
/*     */     }
/*     */ 
/*     */     
/*     */     public char lastChar() {
/* 528 */       return this.sortedSet.lastChar();
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     @Deprecated
/*     */     public Character first() {
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
/*     */     public Character last() {
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
/*     */     public CharSortedSet subSet(Character from, Character to) {
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
/*     */     public CharSortedSet headSet(Character to) {
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
/*     */     public CharSortedSet tailSet(Character from) {
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
/*     */   public static CharSortedSet unmodifiable(CharSortedSet s) {
/* 595 */     return new UnmodifiableSortedSet(s);
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\i\\unimi\dsi\fastutil\chars\CharSortedSets.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */