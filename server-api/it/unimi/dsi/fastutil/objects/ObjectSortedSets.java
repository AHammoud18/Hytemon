/*     */ package it.unimi.dsi.fastutil.objects;
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
/*     */ 
/*     */ public final class ObjectSortedSets
/*     */ {
/*     */   public static class EmptySet<K>
/*     */     extends ObjectSets.EmptySet<K>
/*     */     implements ObjectSortedSet<K>, Serializable, Cloneable
/*     */   {
/*     */     private static final long serialVersionUID = -7046029254386353129L;
/*     */     
/*     */     public ObjectBidirectionalIterator<K> iterator(K from) {
/*  48 */       return ObjectIterators.EMPTY_ITERATOR;
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     public ObjectSortedSet<K> subSet(K from, K to) {
/*  54 */       return ObjectSortedSets.EMPTY_SET;
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     public ObjectSortedSet<K> headSet(K from) {
/*  60 */       return ObjectSortedSets.EMPTY_SET;
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     public ObjectSortedSet<K> tailSet(K to) {
/*  66 */       return ObjectSortedSets.EMPTY_SET;
/*     */     }
/*     */ 
/*     */     
/*     */     public K first() {
/*  71 */       throw new NoSuchElementException();
/*     */     }
/*     */ 
/*     */     
/*     */     public K last() {
/*  76 */       throw new NoSuchElementException();
/*     */     }
/*     */ 
/*     */     
/*     */     public Comparator<? super K> comparator() {
/*  81 */       return null;
/*     */     }
/*     */ 
/*     */     
/*     */     public Object clone() {
/*  86 */       return ObjectSortedSets.EMPTY_SET;
/*     */     }
/*     */     
/*     */     private Object readResolve() {
/*  90 */       return ObjectSortedSets.EMPTY_SET;
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  99 */   public static final EmptySet EMPTY_SET = new EmptySet();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static <K> ObjectSet<K> emptySet() {
/* 111 */     return EMPTY_SET;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public static class Singleton<K>
/*     */     extends ObjectSets.Singleton<K>
/*     */     implements ObjectSortedSet<K>, Serializable, Cloneable
/*     */   {
/*     */     private static final long serialVersionUID = -7046029254386353129L;
/*     */     
/*     */     final Comparator<? super K> comparator;
/*     */ 
/*     */     
/*     */     protected Singleton(K element, Comparator<? super K> comparator) {
/* 126 */       super(element);
/* 127 */       this.comparator = comparator;
/*     */     }
/*     */     
/*     */     Singleton(K element) {
/* 131 */       this(element, (Comparator<? super K>)null);
/*     */     }
/*     */ 
/*     */     
/*     */     final int compare(K k1, K k2) {
/* 136 */       return (this.comparator == null) ? ((Comparable<K>)k1).compareTo(k2) : this.comparator.compare(k1, k2);
/*     */     }
/*     */ 
/*     */     
/*     */     public ObjectBidirectionalIterator<K> iterator(K from) {
/* 141 */       ObjectBidirectionalIterator<K> i = iterator();
/* 142 */       if (compare(this.element, from) <= 0) i.next(); 
/* 143 */       return i;
/*     */     }
/*     */ 
/*     */     
/*     */     public Comparator<? super K> comparator() {
/* 148 */       return this.comparator;
/*     */     }
/*     */ 
/*     */     
/*     */     public ObjectSpliterator<K> spliterator() {
/* 153 */       return ObjectSpliterators.singleton(this.element, this.comparator);
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     public ObjectSortedSet<K> subSet(K from, K to) {
/* 159 */       if (compare(from, this.element) <= 0 && compare(this.element, to) < 0) return this; 
/* 160 */       return ObjectSortedSets.EMPTY_SET;
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     public ObjectSortedSet<K> headSet(K to) {
/* 166 */       if (compare(this.element, to) < 0) return this; 
/* 167 */       return ObjectSortedSets.EMPTY_SET;
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     public ObjectSortedSet<K> tailSet(K from) {
/* 173 */       if (compare(from, this.element) <= 0) return this; 
/* 174 */       return ObjectSortedSets.EMPTY_SET;
/*     */     }
/*     */ 
/*     */     
/*     */     public K first() {
/* 179 */       return this.element;
/*     */     }
/*     */ 
/*     */     
/*     */     public K last() {
/* 184 */       return this.element;
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
/*     */   public static <K> ObjectSortedSet<K> singleton(K element) {
/* 196 */     return new Singleton<>(element);
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
/*     */   public static <K> ObjectSortedSet<K> singleton(K element, Comparator<? super K> comparator) {
/* 208 */     return new Singleton<>(element, comparator);
/*     */   }
/*     */   
/*     */   public static class SynchronizedSortedSet<K>
/*     */     extends ObjectSets.SynchronizedSet<K> implements ObjectSortedSet<K>, Serializable {
/*     */     private static final long serialVersionUID = -7046029254386353129L;
/*     */     protected final ObjectSortedSet<K> sortedSet;
/*     */     
/*     */     protected SynchronizedSortedSet(ObjectSortedSet<K> s, Object sync) {
/* 217 */       super(s, sync);
/* 218 */       this.sortedSet = s;
/*     */     }
/*     */     
/*     */     protected SynchronizedSortedSet(ObjectSortedSet<K> s) {
/* 222 */       super(s);
/* 223 */       this.sortedSet = s;
/*     */     }
/*     */ 
/*     */     
/*     */     public Comparator<? super K> comparator() {
/* 228 */       synchronized (this.sync) {
/* 229 */         return this.sortedSet.comparator();
/*     */       } 
/*     */     }
/*     */ 
/*     */     
/*     */     public ObjectSortedSet<K> subSet(K from, K to) {
/* 235 */       return new SynchronizedSortedSet(this.sortedSet.subSet(from, to), this.sync);
/*     */     }
/*     */ 
/*     */     
/*     */     public ObjectSortedSet<K> headSet(K to) {
/* 240 */       return new SynchronizedSortedSet(this.sortedSet.headSet(to), this.sync);
/*     */     }
/*     */ 
/*     */     
/*     */     public ObjectSortedSet<K> tailSet(K from) {
/* 245 */       return new SynchronizedSortedSet(this.sortedSet.tailSet(from), this.sync);
/*     */     }
/*     */ 
/*     */     
/*     */     public ObjectBidirectionalIterator<K> iterator() {
/* 250 */       return this.sortedSet.iterator();
/*     */     }
/*     */ 
/*     */     
/*     */     public ObjectBidirectionalIterator<K> iterator(K from) {
/* 255 */       return this.sortedSet.iterator(from);
/*     */     }
/*     */ 
/*     */     
/*     */     public K first() {
/* 260 */       synchronized (this.sync) {
/* 261 */         return this.sortedSet.first();
/*     */       } 
/*     */     }
/*     */ 
/*     */     
/*     */     public K last() {
/* 267 */       synchronized (this.sync) {
/* 268 */         return this.sortedSet.last();
/*     */       } 
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
/*     */   public static <K> ObjectSortedSet<K> synchronize(ObjectSortedSet<K> s) {
/* 281 */     return new SynchronizedSortedSet<>(s);
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
/*     */   public static <K> ObjectSortedSet<K> synchronize(ObjectSortedSet<K> s, Object sync) {
/* 294 */     return new SynchronizedSortedSet<>(s, sync);
/*     */   }
/*     */   
/*     */   public static class UnmodifiableSortedSet<K>
/*     */     extends ObjectSets.UnmodifiableSet<K> implements ObjectSortedSet<K>, Serializable {
/*     */     private static final long serialVersionUID = -7046029254386353129L;
/*     */     protected final ObjectSortedSet<K> sortedSet;
/*     */     
/*     */     protected UnmodifiableSortedSet(ObjectSortedSet<K> s) {
/* 303 */       super(s);
/* 304 */       this.sortedSet = s;
/*     */     }
/*     */ 
/*     */     
/*     */     public Comparator<? super K> comparator() {
/* 309 */       return this.sortedSet.comparator();
/*     */     }
/*     */ 
/*     */     
/*     */     public ObjectSortedSet<K> subSet(K from, K to) {
/* 314 */       return new UnmodifiableSortedSet(this.sortedSet.subSet(from, to));
/*     */     }
/*     */ 
/*     */     
/*     */     public ObjectSortedSet<K> headSet(K to) {
/* 319 */       return new UnmodifiableSortedSet(this.sortedSet.headSet(to));
/*     */     }
/*     */ 
/*     */     
/*     */     public ObjectSortedSet<K> tailSet(K from) {
/* 324 */       return new UnmodifiableSortedSet(this.sortedSet.tailSet(from));
/*     */     }
/*     */ 
/*     */     
/*     */     public ObjectBidirectionalIterator<K> iterator() {
/* 329 */       return ObjectIterators.unmodifiable(this.sortedSet.iterator());
/*     */     }
/*     */ 
/*     */     
/*     */     public ObjectBidirectionalIterator<K> iterator(K from) {
/* 334 */       return ObjectIterators.unmodifiable(this.sortedSet.iterator(from));
/*     */     }
/*     */ 
/*     */     
/*     */     public K first() {
/* 339 */       return this.sortedSet.first();
/*     */     }
/*     */ 
/*     */     
/*     */     public K last() {
/* 344 */       return this.sortedSet.last();
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
/*     */   public static <K> ObjectSortedSet<K> unmodifiable(ObjectSortedSet<K> s) {
/* 356 */     return new UnmodifiableSortedSet<>(s);
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\i\\unimi\dsi\fastutil\objects\ObjectSortedSets.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */