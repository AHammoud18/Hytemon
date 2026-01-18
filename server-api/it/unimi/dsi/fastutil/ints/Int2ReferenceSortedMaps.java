/*     */ package it.unimi.dsi.fastutil.ints;
/*     */ 
/*     */ import it.unimi.dsi.fastutil.objects.ObjectBidirectionalIterable;
/*     */ import it.unimi.dsi.fastutil.objects.ObjectBidirectionalIterator;
/*     */ import it.unimi.dsi.fastutil.objects.ObjectSet;
/*     */ import it.unimi.dsi.fastutil.objects.ObjectSortedSet;
/*     */ import it.unimi.dsi.fastutil.objects.ObjectSortedSets;
/*     */ import java.io.Serializable;
/*     */ import java.util.Comparator;
/*     */ import java.util.Map;
/*     */ import java.util.NoSuchElementException;
/*     */ import java.util.Objects;
/*     */ import java.util.Set;
/*     */ import java.util.SortedMap;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public final class Int2ReferenceSortedMaps
/*     */ {
/*     */   public static Comparator<? super Map.Entry<Integer, ?>> entryComparator(IntComparator comparator) {
/*  45 */     return (x, y) -> comparator.compare(((Integer)x.getKey()).intValue(), ((Integer)y.getKey()).intValue());
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
/*     */   public static <V> ObjectBidirectionalIterator<Int2ReferenceMap.Entry<V>> fastIterator(Int2ReferenceSortedMap<V> map) {
/*  60 */     ObjectSortedSet<Int2ReferenceMap.Entry<V>> entries = map.int2ReferenceEntrySet();
/*  61 */     return (entries instanceof Int2ReferenceSortedMap.FastSortedEntrySet) ? ((Int2ReferenceSortedMap.FastSortedEntrySet)entries).fastIterator() : entries.iterator();
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
/*     */   public static <V> ObjectBidirectionalIterable<Int2ReferenceMap.Entry<V>> fastIterable(Int2ReferenceSortedMap<V> map) {
/*  76 */     ObjectSortedSet<Int2ReferenceMap.Entry<V>> entries = map.int2ReferenceEntrySet();
/*  77 */     Objects.requireNonNull((Int2ReferenceSortedMap.FastSortedEntrySet)entries); return (entries instanceof Int2ReferenceSortedMap.FastSortedEntrySet) ? (Int2ReferenceSortedMap.FastSortedEntrySet)entries::fastIterator : (ObjectBidirectionalIterable<Int2ReferenceMap.Entry<V>>)entries;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static class EmptySortedMap<V>
/*     */     extends Int2ReferenceMaps.EmptyMap<V>
/*     */     implements Int2ReferenceSortedMap<V>, Serializable, Cloneable
/*     */   {
/*     */     private static final long serialVersionUID = -7046029254386353129L;
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public IntComparator comparator() {
/*  94 */       return null;
/*     */     }
/*     */ 
/*     */     
/*     */     public ObjectSortedSet<Int2ReferenceMap.Entry<V>> int2ReferenceEntrySet() {
/*  99 */       return (ObjectSortedSet<Int2ReferenceMap.Entry<V>>)ObjectSortedSets.EMPTY_SET;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     @Deprecated
/*     */     public ObjectSortedSet<Map.Entry<Integer, V>> entrySet() {
/* 110 */       return (ObjectSortedSet<Map.Entry<Integer, V>>)ObjectSortedSets.EMPTY_SET;
/*     */     }
/*     */ 
/*     */     
/*     */     public IntSortedSet keySet() {
/* 115 */       return IntSortedSets.EMPTY_SET;
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     public Int2ReferenceSortedMap<V> subMap(int from, int to) {
/* 121 */       return Int2ReferenceSortedMaps.EMPTY_MAP;
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     public Int2ReferenceSortedMap<V> headMap(int to) {
/* 127 */       return Int2ReferenceSortedMaps.EMPTY_MAP;
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     public Int2ReferenceSortedMap<V> tailMap(int from) {
/* 133 */       return Int2ReferenceSortedMaps.EMPTY_MAP;
/*     */     }
/*     */ 
/*     */     
/*     */     public int firstIntKey() {
/* 138 */       throw new NoSuchElementException();
/*     */     }
/*     */ 
/*     */     
/*     */     public int lastIntKey() {
/* 143 */       throw new NoSuchElementException();
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     @Deprecated
/*     */     public Int2ReferenceSortedMap<V> headMap(Integer oto) {
/* 154 */       return headMap(oto.intValue());
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     @Deprecated
/*     */     public Int2ReferenceSortedMap<V> tailMap(Integer ofrom) {
/* 165 */       return tailMap(ofrom.intValue());
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     @Deprecated
/*     */     public Int2ReferenceSortedMap<V> subMap(Integer ofrom, Integer oto) {
/* 176 */       return subMap(ofrom.intValue(), oto.intValue());
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     @Deprecated
/*     */     public Integer firstKey() {
/* 187 */       return Integer.valueOf(firstIntKey());
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     @Deprecated
/*     */     public Integer lastKey() {
/* 198 */       return Integer.valueOf(lastIntKey());
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 206 */   public static final EmptySortedMap EMPTY_MAP = new EmptySortedMap();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static <V> Int2ReferenceSortedMap<V> emptyMap() {
/* 218 */     return EMPTY_MAP;
/*     */   }
/*     */ 
/*     */   
/*     */   public static class Singleton<V>
/*     */     extends Int2ReferenceMaps.Singleton<V>
/*     */     implements Int2ReferenceSortedMap<V>, Serializable, Cloneable
/*     */   {
/*     */     private static final long serialVersionUID = -7046029254386353129L;
/*     */     
/*     */     protected final IntComparator comparator;
/*     */ 
/*     */     
/*     */     protected Singleton(int key, V value, IntComparator comparator) {
/* 232 */       super(key, value);
/* 233 */       this.comparator = comparator;
/*     */     }
/*     */     
/*     */     protected Singleton(int key, V value) {
/* 237 */       this(key, value, (IntComparator)null);
/*     */     }
/*     */     
/*     */     final int compare(int k1, int k2) {
/* 241 */       return (this.comparator == null) ? Integer.compare(k1, k2) : this.comparator.compare(k1, k2);
/*     */     }
/*     */ 
/*     */     
/*     */     public IntComparator comparator() {
/* 246 */       return this.comparator;
/*     */     }
/*     */ 
/*     */     
/*     */     public ObjectSortedSet<Int2ReferenceMap.Entry<V>> int2ReferenceEntrySet() {
/* 251 */       if (this.entries == null) this.entries = (ObjectSet<Int2ReferenceMap.Entry<V>>)ObjectSortedSets.singleton(new AbstractInt2ReferenceMap.BasicEntry<>(this.key, this.value), Int2ReferenceSortedMaps.entryComparator(this.comparator)); 
/* 252 */       return (ObjectSortedSet<Int2ReferenceMap.Entry<V>>)this.entries;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     @Deprecated
/*     */     public ObjectSortedSet<Map.Entry<Integer, V>> entrySet() {
/* 264 */       return (ObjectSortedSet)int2ReferenceEntrySet();
/*     */     }
/*     */ 
/*     */     
/*     */     public IntSortedSet keySet() {
/* 269 */       if (this.keys == null) this.keys = IntSortedSets.singleton(this.key, this.comparator); 
/* 270 */       return (IntSortedSet)this.keys;
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     public Int2ReferenceSortedMap<V> subMap(int from, int to) {
/* 276 */       if (compare(from, this.key) <= 0 && compare(this.key, to) < 0) return this; 
/* 277 */       return Int2ReferenceSortedMaps.EMPTY_MAP;
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     public Int2ReferenceSortedMap<V> headMap(int to) {
/* 283 */       if (compare(this.key, to) < 0) return this; 
/* 284 */       return Int2ReferenceSortedMaps.EMPTY_MAP;
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     public Int2ReferenceSortedMap<V> tailMap(int from) {
/* 290 */       if (compare(from, this.key) <= 0) return this; 
/* 291 */       return Int2ReferenceSortedMaps.EMPTY_MAP;
/*     */     }
/*     */ 
/*     */     
/*     */     public int firstIntKey() {
/* 296 */       return this.key;
/*     */     }
/*     */ 
/*     */     
/*     */     public int lastIntKey() {
/* 301 */       return this.key;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     @Deprecated
/*     */     public Int2ReferenceSortedMap<V> headMap(Integer oto) {
/* 312 */       return headMap(oto.intValue());
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     @Deprecated
/*     */     public Int2ReferenceSortedMap<V> tailMap(Integer ofrom) {
/* 323 */       return tailMap(ofrom.intValue());
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     @Deprecated
/*     */     public Int2ReferenceSortedMap<V> subMap(Integer ofrom, Integer oto) {
/* 334 */       return subMap(ofrom.intValue(), oto.intValue());
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     @Deprecated
/*     */     public Integer firstKey() {
/* 345 */       return Integer.valueOf(firstIntKey());
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     @Deprecated
/*     */     public Integer lastKey() {
/* 356 */       return Integer.valueOf(lastIntKey());
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
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static <V> Int2ReferenceSortedMap<V> singleton(Integer key, V value) {
/* 372 */     return new Singleton<>(key.intValue(), value);
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
/*     */   public static <V> Int2ReferenceSortedMap<V> singleton(Integer key, V value, IntComparator comparator) {
/* 388 */     return new Singleton<>(key.intValue(), value, comparator);
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
/*     */   public static <V> Int2ReferenceSortedMap<V> singleton(int key, V value) {
/* 403 */     return new Singleton<>(key, value);
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
/*     */   public static <V> Int2ReferenceSortedMap<V> singleton(int key, V value, IntComparator comparator) {
/* 419 */     return new Singleton<>(key, value, comparator);
/*     */   }
/*     */   
/*     */   public static class SynchronizedSortedMap<V>
/*     */     extends Int2ReferenceMaps.SynchronizedMap<V> implements Int2ReferenceSortedMap<V>, Serializable {
/*     */     private static final long serialVersionUID = -7046029254386353129L;
/*     */     protected final Int2ReferenceSortedMap<V> sortedMap;
/*     */     
/*     */     protected SynchronizedSortedMap(Int2ReferenceSortedMap<V> m, Object sync) {
/* 428 */       super(m, sync);
/* 429 */       this.sortedMap = m;
/*     */     }
/*     */     
/*     */     protected SynchronizedSortedMap(Int2ReferenceSortedMap<V> m) {
/* 433 */       super(m);
/* 434 */       this.sortedMap = m;
/*     */     }
/*     */ 
/*     */     
/*     */     public IntComparator comparator() {
/* 439 */       synchronized (this.sync) {
/* 440 */         return this.sortedMap.comparator();
/*     */       } 
/*     */     }
/*     */ 
/*     */     
/*     */     public ObjectSortedSet<Int2ReferenceMap.Entry<V>> int2ReferenceEntrySet() {
/* 446 */       if (this.entries == null) this.entries = (ObjectSet<Int2ReferenceMap.Entry<V>>)ObjectSortedSets.synchronize(this.sortedMap.int2ReferenceEntrySet(), this.sync); 
/* 447 */       return (ObjectSortedSet<Int2ReferenceMap.Entry<V>>)this.entries;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     @Deprecated
/*     */     public ObjectSortedSet<Map.Entry<Integer, V>> entrySet() {
/* 459 */       return (ObjectSortedSet)int2ReferenceEntrySet();
/*     */     }
/*     */ 
/*     */     
/*     */     public IntSortedSet keySet() {
/* 464 */       if (this.keys == null) this.keys = IntSortedSets.synchronize(this.sortedMap.keySet(), this.sync); 
/* 465 */       return (IntSortedSet)this.keys;
/*     */     }
/*     */ 
/*     */     
/*     */     public Int2ReferenceSortedMap<V> subMap(int from, int to) {
/* 470 */       return new SynchronizedSortedMap(this.sortedMap.subMap(from, to), this.sync);
/*     */     }
/*     */ 
/*     */     
/*     */     public Int2ReferenceSortedMap<V> headMap(int to) {
/* 475 */       return new SynchronizedSortedMap(this.sortedMap.headMap(to), this.sync);
/*     */     }
/*     */ 
/*     */     
/*     */     public Int2ReferenceSortedMap<V> tailMap(int from) {
/* 480 */       return new SynchronizedSortedMap(this.sortedMap.tailMap(from), this.sync);
/*     */     }
/*     */ 
/*     */     
/*     */     public int firstIntKey() {
/* 485 */       synchronized (this.sync) {
/* 486 */         return this.sortedMap.firstIntKey();
/*     */       } 
/*     */     }
/*     */ 
/*     */     
/*     */     public int lastIntKey() {
/* 492 */       synchronized (this.sync) {
/* 493 */         return this.sortedMap.lastIntKey();
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
/*     */     public Integer firstKey() {
/* 505 */       synchronized (this.sync) {
/* 506 */         return this.sortedMap.firstKey();
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
/*     */     public Integer lastKey() {
/* 518 */       synchronized (this.sync) {
/* 519 */         return this.sortedMap.lastKey();
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
/*     */     public Int2ReferenceSortedMap<V> subMap(Integer from, Integer to) {
/* 531 */       return new SynchronizedSortedMap(this.sortedMap.subMap(from, to), this.sync);
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     @Deprecated
/*     */     public Int2ReferenceSortedMap<V> headMap(Integer to) {
/* 542 */       return new SynchronizedSortedMap(this.sortedMap.headMap(to), this.sync);
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     @Deprecated
/*     */     public Int2ReferenceSortedMap<V> tailMap(Integer from) {
/* 553 */       return new SynchronizedSortedMap(this.sortedMap.tailMap(from), this.sync);
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
/*     */   public static <V> Int2ReferenceSortedMap<V> synchronize(Int2ReferenceSortedMap<V> m) {
/* 565 */     return new SynchronizedSortedMap<>(m);
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
/*     */   public static <V> Int2ReferenceSortedMap<V> synchronize(Int2ReferenceSortedMap<V> m, Object sync) {
/* 578 */     return new SynchronizedSortedMap<>(m, sync);
/*     */   }
/*     */   
/*     */   public static class UnmodifiableSortedMap<V>
/*     */     extends Int2ReferenceMaps.UnmodifiableMap<V> implements Int2ReferenceSortedMap<V>, Serializable {
/*     */     private static final long serialVersionUID = -7046029254386353129L;
/*     */     protected final Int2ReferenceSortedMap<? extends V> sortedMap;
/*     */     
/*     */     protected UnmodifiableSortedMap(Int2ReferenceSortedMap<? extends V> m) {
/* 587 */       super(m);
/* 588 */       this.sortedMap = m;
/*     */     }
/*     */ 
/*     */     
/*     */     public IntComparator comparator() {
/* 593 */       return this.sortedMap.comparator();
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     public ObjectSortedSet<Int2ReferenceMap.Entry<V>> int2ReferenceEntrySet() {
/* 599 */       if (this.entries == null) this.entries = (ObjectSet<Int2ReferenceMap.Entry<V>>)ObjectSortedSets.unmodifiable(this.sortedMap.int2ReferenceEntrySet()); 
/* 600 */       return (ObjectSortedSet<Int2ReferenceMap.Entry<V>>)this.entries;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     @Deprecated
/*     */     public ObjectSortedSet<Map.Entry<Integer, V>> entrySet() {
/* 612 */       return (ObjectSortedSet)int2ReferenceEntrySet();
/*     */     }
/*     */ 
/*     */     
/*     */     public IntSortedSet keySet() {
/* 617 */       if (this.keys == null) this.keys = IntSortedSets.unmodifiable(this.sortedMap.keySet()); 
/* 618 */       return (IntSortedSet)this.keys;
/*     */     }
/*     */ 
/*     */     
/*     */     public Int2ReferenceSortedMap<V> subMap(int from, int to) {
/* 623 */       return new UnmodifiableSortedMap(this.sortedMap.subMap(from, to));
/*     */     }
/*     */ 
/*     */     
/*     */     public Int2ReferenceSortedMap<V> headMap(int to) {
/* 628 */       return new UnmodifiableSortedMap(this.sortedMap.headMap(to));
/*     */     }
/*     */ 
/*     */     
/*     */     public Int2ReferenceSortedMap<V> tailMap(int from) {
/* 633 */       return new UnmodifiableSortedMap(this.sortedMap.tailMap(from));
/*     */     }
/*     */ 
/*     */     
/*     */     public int firstIntKey() {
/* 638 */       return this.sortedMap.firstIntKey();
/*     */     }
/*     */ 
/*     */     
/*     */     public int lastIntKey() {
/* 643 */       return this.sortedMap.lastIntKey();
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     @Deprecated
/*     */     public Integer firstKey() {
/* 654 */       return this.sortedMap.firstKey();
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     @Deprecated
/*     */     public Integer lastKey() {
/* 665 */       return this.sortedMap.lastKey();
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     @Deprecated
/*     */     public Int2ReferenceSortedMap<V> subMap(Integer from, Integer to) {
/* 676 */       return new UnmodifiableSortedMap(this.sortedMap.subMap(from, to));
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     @Deprecated
/*     */     public Int2ReferenceSortedMap<V> headMap(Integer to) {
/* 687 */       return new UnmodifiableSortedMap(this.sortedMap.headMap(to));
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     @Deprecated
/*     */     public Int2ReferenceSortedMap<V> tailMap(Integer from) {
/* 698 */       return new UnmodifiableSortedMap(this.sortedMap.tailMap(from));
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
/*     */   public static <V> Int2ReferenceSortedMap<V> unmodifiable(Int2ReferenceSortedMap<? extends V> m) {
/* 710 */     return new UnmodifiableSortedMap<>(m);
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\i\\unimi\dsi\fastutil\ints\Int2ReferenceSortedMaps.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */