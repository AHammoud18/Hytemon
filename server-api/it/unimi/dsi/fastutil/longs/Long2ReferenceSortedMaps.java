/*     */ package it.unimi.dsi.fastutil.longs;
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
/*     */ public final class Long2ReferenceSortedMaps
/*     */ {
/*     */   public static Comparator<? super Map.Entry<Long, ?>> entryComparator(LongComparator comparator) {
/*  45 */     return (x, y) -> comparator.compare(((Long)x.getKey()).longValue(), ((Long)y.getKey()).longValue());
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
/*     */   public static <V> ObjectBidirectionalIterator<Long2ReferenceMap.Entry<V>> fastIterator(Long2ReferenceSortedMap<V> map) {
/*  60 */     ObjectSortedSet<Long2ReferenceMap.Entry<V>> entries = map.long2ReferenceEntrySet();
/*  61 */     return (entries instanceof Long2ReferenceSortedMap.FastSortedEntrySet) ? ((Long2ReferenceSortedMap.FastSortedEntrySet)entries).fastIterator() : entries.iterator();
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
/*     */   public static <V> ObjectBidirectionalIterable<Long2ReferenceMap.Entry<V>> fastIterable(Long2ReferenceSortedMap<V> map) {
/*  76 */     ObjectSortedSet<Long2ReferenceMap.Entry<V>> entries = map.long2ReferenceEntrySet();
/*  77 */     Objects.requireNonNull((Long2ReferenceSortedMap.FastSortedEntrySet)entries); return (entries instanceof Long2ReferenceSortedMap.FastSortedEntrySet) ? (Long2ReferenceSortedMap.FastSortedEntrySet)entries::fastIterator : (ObjectBidirectionalIterable<Long2ReferenceMap.Entry<V>>)entries;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static class EmptySortedMap<V>
/*     */     extends Long2ReferenceMaps.EmptyMap<V>
/*     */     implements Long2ReferenceSortedMap<V>, Serializable, Cloneable
/*     */   {
/*     */     private static final long serialVersionUID = -7046029254386353129L;
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public LongComparator comparator() {
/*  94 */       return null;
/*     */     }
/*     */ 
/*     */     
/*     */     public ObjectSortedSet<Long2ReferenceMap.Entry<V>> long2ReferenceEntrySet() {
/*  99 */       return (ObjectSortedSet<Long2ReferenceMap.Entry<V>>)ObjectSortedSets.EMPTY_SET;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     @Deprecated
/*     */     public ObjectSortedSet<Map.Entry<Long, V>> entrySet() {
/* 110 */       return (ObjectSortedSet<Map.Entry<Long, V>>)ObjectSortedSets.EMPTY_SET;
/*     */     }
/*     */ 
/*     */     
/*     */     public LongSortedSet keySet() {
/* 115 */       return LongSortedSets.EMPTY_SET;
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     public Long2ReferenceSortedMap<V> subMap(long from, long to) {
/* 121 */       return Long2ReferenceSortedMaps.EMPTY_MAP;
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     public Long2ReferenceSortedMap<V> headMap(long to) {
/* 127 */       return Long2ReferenceSortedMaps.EMPTY_MAP;
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     public Long2ReferenceSortedMap<V> tailMap(long from) {
/* 133 */       return Long2ReferenceSortedMaps.EMPTY_MAP;
/*     */     }
/*     */ 
/*     */     
/*     */     public long firstLongKey() {
/* 138 */       throw new NoSuchElementException();
/*     */     }
/*     */ 
/*     */     
/*     */     public long lastLongKey() {
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
/*     */     public Long2ReferenceSortedMap<V> headMap(Long oto) {
/* 154 */       return headMap(oto.longValue());
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     @Deprecated
/*     */     public Long2ReferenceSortedMap<V> tailMap(Long ofrom) {
/* 165 */       return tailMap(ofrom.longValue());
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     @Deprecated
/*     */     public Long2ReferenceSortedMap<V> subMap(Long ofrom, Long oto) {
/* 176 */       return subMap(ofrom.longValue(), oto.longValue());
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     @Deprecated
/*     */     public Long firstKey() {
/* 187 */       return Long.valueOf(firstLongKey());
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     @Deprecated
/*     */     public Long lastKey() {
/* 198 */       return Long.valueOf(lastLongKey());
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
/*     */   public static <V> Long2ReferenceSortedMap<V> emptyMap() {
/* 218 */     return EMPTY_MAP;
/*     */   }
/*     */ 
/*     */   
/*     */   public static class Singleton<V>
/*     */     extends Long2ReferenceMaps.Singleton<V>
/*     */     implements Long2ReferenceSortedMap<V>, Serializable, Cloneable
/*     */   {
/*     */     private static final long serialVersionUID = -7046029254386353129L;
/*     */     
/*     */     protected final LongComparator comparator;
/*     */ 
/*     */     
/*     */     protected Singleton(long key, V value, LongComparator comparator) {
/* 232 */       super(key, value);
/* 233 */       this.comparator = comparator;
/*     */     }
/*     */     
/*     */     protected Singleton(long key, V value) {
/* 237 */       this(key, value, (LongComparator)null);
/*     */     }
/*     */     
/*     */     final int compare(long k1, long k2) {
/* 241 */       return (this.comparator == null) ? Long.compare(k1, k2) : this.comparator.compare(k1, k2);
/*     */     }
/*     */ 
/*     */     
/*     */     public LongComparator comparator() {
/* 246 */       return this.comparator;
/*     */     }
/*     */ 
/*     */     
/*     */     public ObjectSortedSet<Long2ReferenceMap.Entry<V>> long2ReferenceEntrySet() {
/* 251 */       if (this.entries == null) this.entries = (ObjectSet<Long2ReferenceMap.Entry<V>>)ObjectSortedSets.singleton(new AbstractLong2ReferenceMap.BasicEntry<>(this.key, this.value), Long2ReferenceSortedMaps.entryComparator(this.comparator)); 
/* 252 */       return (ObjectSortedSet<Long2ReferenceMap.Entry<V>>)this.entries;
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
/*     */     public ObjectSortedSet<Map.Entry<Long, V>> entrySet() {
/* 264 */       return (ObjectSortedSet)long2ReferenceEntrySet();
/*     */     }
/*     */ 
/*     */     
/*     */     public LongSortedSet keySet() {
/* 269 */       if (this.keys == null) this.keys = LongSortedSets.singleton(this.key, this.comparator); 
/* 270 */       return (LongSortedSet)this.keys;
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     public Long2ReferenceSortedMap<V> subMap(long from, long to) {
/* 276 */       if (compare(from, this.key) <= 0 && compare(this.key, to) < 0) return this; 
/* 277 */       return Long2ReferenceSortedMaps.EMPTY_MAP;
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     public Long2ReferenceSortedMap<V> headMap(long to) {
/* 283 */       if (compare(this.key, to) < 0) return this; 
/* 284 */       return Long2ReferenceSortedMaps.EMPTY_MAP;
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     public Long2ReferenceSortedMap<V> tailMap(long from) {
/* 290 */       if (compare(from, this.key) <= 0) return this; 
/* 291 */       return Long2ReferenceSortedMaps.EMPTY_MAP;
/*     */     }
/*     */ 
/*     */     
/*     */     public long firstLongKey() {
/* 296 */       return this.key;
/*     */     }
/*     */ 
/*     */     
/*     */     public long lastLongKey() {
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
/*     */     public Long2ReferenceSortedMap<V> headMap(Long oto) {
/* 312 */       return headMap(oto.longValue());
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     @Deprecated
/*     */     public Long2ReferenceSortedMap<V> tailMap(Long ofrom) {
/* 323 */       return tailMap(ofrom.longValue());
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     @Deprecated
/*     */     public Long2ReferenceSortedMap<V> subMap(Long ofrom, Long oto) {
/* 334 */       return subMap(ofrom.longValue(), oto.longValue());
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     @Deprecated
/*     */     public Long firstKey() {
/* 345 */       return Long.valueOf(firstLongKey());
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     @Deprecated
/*     */     public Long lastKey() {
/* 356 */       return Long.valueOf(lastLongKey());
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
/*     */   public static <V> Long2ReferenceSortedMap<V> singleton(Long key, V value) {
/* 372 */     return new Singleton<>(key.longValue(), value);
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
/*     */   public static <V> Long2ReferenceSortedMap<V> singleton(Long key, V value, LongComparator comparator) {
/* 388 */     return new Singleton<>(key.longValue(), value, comparator);
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
/*     */   public static <V> Long2ReferenceSortedMap<V> singleton(long key, V value) {
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
/*     */   public static <V> Long2ReferenceSortedMap<V> singleton(long key, V value, LongComparator comparator) {
/* 419 */     return new Singleton<>(key, value, comparator);
/*     */   }
/*     */   
/*     */   public static class SynchronizedSortedMap<V>
/*     */     extends Long2ReferenceMaps.SynchronizedMap<V> implements Long2ReferenceSortedMap<V>, Serializable {
/*     */     private static final long serialVersionUID = -7046029254386353129L;
/*     */     protected final Long2ReferenceSortedMap<V> sortedMap;
/*     */     
/*     */     protected SynchronizedSortedMap(Long2ReferenceSortedMap<V> m, Object sync) {
/* 428 */       super(m, sync);
/* 429 */       this.sortedMap = m;
/*     */     }
/*     */     
/*     */     protected SynchronizedSortedMap(Long2ReferenceSortedMap<V> m) {
/* 433 */       super(m);
/* 434 */       this.sortedMap = m;
/*     */     }
/*     */ 
/*     */     
/*     */     public LongComparator comparator() {
/* 439 */       synchronized (this.sync) {
/* 440 */         return this.sortedMap.comparator();
/*     */       } 
/*     */     }
/*     */ 
/*     */     
/*     */     public ObjectSortedSet<Long2ReferenceMap.Entry<V>> long2ReferenceEntrySet() {
/* 446 */       if (this.entries == null) this.entries = (ObjectSet<Long2ReferenceMap.Entry<V>>)ObjectSortedSets.synchronize(this.sortedMap.long2ReferenceEntrySet(), this.sync); 
/* 447 */       return (ObjectSortedSet<Long2ReferenceMap.Entry<V>>)this.entries;
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
/*     */     public ObjectSortedSet<Map.Entry<Long, V>> entrySet() {
/* 459 */       return (ObjectSortedSet)long2ReferenceEntrySet();
/*     */     }
/*     */ 
/*     */     
/*     */     public LongSortedSet keySet() {
/* 464 */       if (this.keys == null) this.keys = LongSortedSets.synchronize(this.sortedMap.keySet(), this.sync); 
/* 465 */       return (LongSortedSet)this.keys;
/*     */     }
/*     */ 
/*     */     
/*     */     public Long2ReferenceSortedMap<V> subMap(long from, long to) {
/* 470 */       return new SynchronizedSortedMap(this.sortedMap.subMap(from, to), this.sync);
/*     */     }
/*     */ 
/*     */     
/*     */     public Long2ReferenceSortedMap<V> headMap(long to) {
/* 475 */       return new SynchronizedSortedMap(this.sortedMap.headMap(to), this.sync);
/*     */     }
/*     */ 
/*     */     
/*     */     public Long2ReferenceSortedMap<V> tailMap(long from) {
/* 480 */       return new SynchronizedSortedMap(this.sortedMap.tailMap(from), this.sync);
/*     */     }
/*     */ 
/*     */     
/*     */     public long firstLongKey() {
/* 485 */       synchronized (this.sync) {
/* 486 */         return this.sortedMap.firstLongKey();
/*     */       } 
/*     */     }
/*     */ 
/*     */     
/*     */     public long lastLongKey() {
/* 492 */       synchronized (this.sync) {
/* 493 */         return this.sortedMap.lastLongKey();
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
/*     */     public Long firstKey() {
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
/*     */     public Long lastKey() {
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
/*     */     public Long2ReferenceSortedMap<V> subMap(Long from, Long to) {
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
/*     */     public Long2ReferenceSortedMap<V> headMap(Long to) {
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
/*     */     public Long2ReferenceSortedMap<V> tailMap(Long from) {
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
/*     */   public static <V> Long2ReferenceSortedMap<V> synchronize(Long2ReferenceSortedMap<V> m) {
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
/*     */   public static <V> Long2ReferenceSortedMap<V> synchronize(Long2ReferenceSortedMap<V> m, Object sync) {
/* 578 */     return new SynchronizedSortedMap<>(m, sync);
/*     */   }
/*     */   
/*     */   public static class UnmodifiableSortedMap<V>
/*     */     extends Long2ReferenceMaps.UnmodifiableMap<V> implements Long2ReferenceSortedMap<V>, Serializable {
/*     */     private static final long serialVersionUID = -7046029254386353129L;
/*     */     protected final Long2ReferenceSortedMap<? extends V> sortedMap;
/*     */     
/*     */     protected UnmodifiableSortedMap(Long2ReferenceSortedMap<? extends V> m) {
/* 587 */       super(m);
/* 588 */       this.sortedMap = m;
/*     */     }
/*     */ 
/*     */     
/*     */     public LongComparator comparator() {
/* 593 */       return this.sortedMap.comparator();
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     public ObjectSortedSet<Long2ReferenceMap.Entry<V>> long2ReferenceEntrySet() {
/* 599 */       if (this.entries == null) this.entries = (ObjectSet<Long2ReferenceMap.Entry<V>>)ObjectSortedSets.unmodifiable(this.sortedMap.long2ReferenceEntrySet()); 
/* 600 */       return (ObjectSortedSet<Long2ReferenceMap.Entry<V>>)this.entries;
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
/*     */     public ObjectSortedSet<Map.Entry<Long, V>> entrySet() {
/* 612 */       return (ObjectSortedSet)long2ReferenceEntrySet();
/*     */     }
/*     */ 
/*     */     
/*     */     public LongSortedSet keySet() {
/* 617 */       if (this.keys == null) this.keys = LongSortedSets.unmodifiable(this.sortedMap.keySet()); 
/* 618 */       return (LongSortedSet)this.keys;
/*     */     }
/*     */ 
/*     */     
/*     */     public Long2ReferenceSortedMap<V> subMap(long from, long to) {
/* 623 */       return new UnmodifiableSortedMap(this.sortedMap.subMap(from, to));
/*     */     }
/*     */ 
/*     */     
/*     */     public Long2ReferenceSortedMap<V> headMap(long to) {
/* 628 */       return new UnmodifiableSortedMap(this.sortedMap.headMap(to));
/*     */     }
/*     */ 
/*     */     
/*     */     public Long2ReferenceSortedMap<V> tailMap(long from) {
/* 633 */       return new UnmodifiableSortedMap(this.sortedMap.tailMap(from));
/*     */     }
/*     */ 
/*     */     
/*     */     public long firstLongKey() {
/* 638 */       return this.sortedMap.firstLongKey();
/*     */     }
/*     */ 
/*     */     
/*     */     public long lastLongKey() {
/* 643 */       return this.sortedMap.lastLongKey();
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     @Deprecated
/*     */     public Long firstKey() {
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
/*     */     public Long lastKey() {
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
/*     */     public Long2ReferenceSortedMap<V> subMap(Long from, Long to) {
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
/*     */     public Long2ReferenceSortedMap<V> headMap(Long to) {
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
/*     */     public Long2ReferenceSortedMap<V> tailMap(Long from) {
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
/*     */   public static <V> Long2ReferenceSortedMap<V> unmodifiable(Long2ReferenceSortedMap<? extends V> m) {
/* 710 */     return new UnmodifiableSortedMap<>(m);
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\i\\unimi\dsi\fastutil\longs\Long2ReferenceSortedMaps.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */