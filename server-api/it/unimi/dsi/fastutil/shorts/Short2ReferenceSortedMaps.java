/*     */ package it.unimi.dsi.fastutil.shorts;
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
/*     */ public final class Short2ReferenceSortedMaps
/*     */ {
/*     */   public static Comparator<? super Map.Entry<Short, ?>> entryComparator(ShortComparator comparator) {
/*  45 */     return (x, y) -> comparator.compare(((Short)x.getKey()).shortValue(), ((Short)y.getKey()).shortValue());
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
/*     */   public static <V> ObjectBidirectionalIterator<Short2ReferenceMap.Entry<V>> fastIterator(Short2ReferenceSortedMap<V> map) {
/*  60 */     ObjectSortedSet<Short2ReferenceMap.Entry<V>> entries = map.short2ReferenceEntrySet();
/*  61 */     return (entries instanceof Short2ReferenceSortedMap.FastSortedEntrySet) ? ((Short2ReferenceSortedMap.FastSortedEntrySet)entries).fastIterator() : entries.iterator();
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
/*     */   public static <V> ObjectBidirectionalIterable<Short2ReferenceMap.Entry<V>> fastIterable(Short2ReferenceSortedMap<V> map) {
/*  76 */     ObjectSortedSet<Short2ReferenceMap.Entry<V>> entries = map.short2ReferenceEntrySet();
/*  77 */     Objects.requireNonNull((Short2ReferenceSortedMap.FastSortedEntrySet)entries); return (entries instanceof Short2ReferenceSortedMap.FastSortedEntrySet) ? (Short2ReferenceSortedMap.FastSortedEntrySet)entries::fastIterator : (ObjectBidirectionalIterable<Short2ReferenceMap.Entry<V>>)entries;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static class EmptySortedMap<V>
/*     */     extends Short2ReferenceMaps.EmptyMap<V>
/*     */     implements Short2ReferenceSortedMap<V>, Serializable, Cloneable
/*     */   {
/*     */     private static final long serialVersionUID = -7046029254386353129L;
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public ShortComparator comparator() {
/*  94 */       return null;
/*     */     }
/*     */ 
/*     */     
/*     */     public ObjectSortedSet<Short2ReferenceMap.Entry<V>> short2ReferenceEntrySet() {
/*  99 */       return (ObjectSortedSet<Short2ReferenceMap.Entry<V>>)ObjectSortedSets.EMPTY_SET;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     @Deprecated
/*     */     public ObjectSortedSet<Map.Entry<Short, V>> entrySet() {
/* 110 */       return (ObjectSortedSet<Map.Entry<Short, V>>)ObjectSortedSets.EMPTY_SET;
/*     */     }
/*     */ 
/*     */     
/*     */     public ShortSortedSet keySet() {
/* 115 */       return ShortSortedSets.EMPTY_SET;
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     public Short2ReferenceSortedMap<V> subMap(short from, short to) {
/* 121 */       return Short2ReferenceSortedMaps.EMPTY_MAP;
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     public Short2ReferenceSortedMap<V> headMap(short to) {
/* 127 */       return Short2ReferenceSortedMaps.EMPTY_MAP;
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     public Short2ReferenceSortedMap<V> tailMap(short from) {
/* 133 */       return Short2ReferenceSortedMaps.EMPTY_MAP;
/*     */     }
/*     */ 
/*     */     
/*     */     public short firstShortKey() {
/* 138 */       throw new NoSuchElementException();
/*     */     }
/*     */ 
/*     */     
/*     */     public short lastShortKey() {
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
/*     */     public Short2ReferenceSortedMap<V> headMap(Short oto) {
/* 154 */       return headMap(oto.shortValue());
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     @Deprecated
/*     */     public Short2ReferenceSortedMap<V> tailMap(Short ofrom) {
/* 165 */       return tailMap(ofrom.shortValue());
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     @Deprecated
/*     */     public Short2ReferenceSortedMap<V> subMap(Short ofrom, Short oto) {
/* 176 */       return subMap(ofrom.shortValue(), oto.shortValue());
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     @Deprecated
/*     */     public Short firstKey() {
/* 187 */       return Short.valueOf(firstShortKey());
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     @Deprecated
/*     */     public Short lastKey() {
/* 198 */       return Short.valueOf(lastShortKey());
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
/*     */   public static <V> Short2ReferenceSortedMap<V> emptyMap() {
/* 218 */     return EMPTY_MAP;
/*     */   }
/*     */ 
/*     */   
/*     */   public static class Singleton<V>
/*     */     extends Short2ReferenceMaps.Singleton<V>
/*     */     implements Short2ReferenceSortedMap<V>, Serializable, Cloneable
/*     */   {
/*     */     private static final long serialVersionUID = -7046029254386353129L;
/*     */     
/*     */     protected final ShortComparator comparator;
/*     */ 
/*     */     
/*     */     protected Singleton(short key, V value, ShortComparator comparator) {
/* 232 */       super(key, value);
/* 233 */       this.comparator = comparator;
/*     */     }
/*     */     
/*     */     protected Singleton(short key, V value) {
/* 237 */       this(key, value, (ShortComparator)null);
/*     */     }
/*     */     
/*     */     final int compare(short k1, short k2) {
/* 241 */       return (this.comparator == null) ? Short.compare(k1, k2) : this.comparator.compare(k1, k2);
/*     */     }
/*     */ 
/*     */     
/*     */     public ShortComparator comparator() {
/* 246 */       return this.comparator;
/*     */     }
/*     */ 
/*     */     
/*     */     public ObjectSortedSet<Short2ReferenceMap.Entry<V>> short2ReferenceEntrySet() {
/* 251 */       if (this.entries == null) this.entries = (ObjectSet<Short2ReferenceMap.Entry<V>>)ObjectSortedSets.singleton(new AbstractShort2ReferenceMap.BasicEntry<>(this.key, this.value), Short2ReferenceSortedMaps.entryComparator(this.comparator)); 
/* 252 */       return (ObjectSortedSet<Short2ReferenceMap.Entry<V>>)this.entries;
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
/*     */     public ObjectSortedSet<Map.Entry<Short, V>> entrySet() {
/* 264 */       return (ObjectSortedSet)short2ReferenceEntrySet();
/*     */     }
/*     */ 
/*     */     
/*     */     public ShortSortedSet keySet() {
/* 269 */       if (this.keys == null) this.keys = ShortSortedSets.singleton(this.key, this.comparator); 
/* 270 */       return (ShortSortedSet)this.keys;
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     public Short2ReferenceSortedMap<V> subMap(short from, short to) {
/* 276 */       if (compare(from, this.key) <= 0 && compare(this.key, to) < 0) return this; 
/* 277 */       return Short2ReferenceSortedMaps.EMPTY_MAP;
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     public Short2ReferenceSortedMap<V> headMap(short to) {
/* 283 */       if (compare(this.key, to) < 0) return this; 
/* 284 */       return Short2ReferenceSortedMaps.EMPTY_MAP;
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     public Short2ReferenceSortedMap<V> tailMap(short from) {
/* 290 */       if (compare(from, this.key) <= 0) return this; 
/* 291 */       return Short2ReferenceSortedMaps.EMPTY_MAP;
/*     */     }
/*     */ 
/*     */     
/*     */     public short firstShortKey() {
/* 296 */       return this.key;
/*     */     }
/*     */ 
/*     */     
/*     */     public short lastShortKey() {
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
/*     */     public Short2ReferenceSortedMap<V> headMap(Short oto) {
/* 312 */       return headMap(oto.shortValue());
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     @Deprecated
/*     */     public Short2ReferenceSortedMap<V> tailMap(Short ofrom) {
/* 323 */       return tailMap(ofrom.shortValue());
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     @Deprecated
/*     */     public Short2ReferenceSortedMap<V> subMap(Short ofrom, Short oto) {
/* 334 */       return subMap(ofrom.shortValue(), oto.shortValue());
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     @Deprecated
/*     */     public Short firstKey() {
/* 345 */       return Short.valueOf(firstShortKey());
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     @Deprecated
/*     */     public Short lastKey() {
/* 356 */       return Short.valueOf(lastShortKey());
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
/*     */   public static <V> Short2ReferenceSortedMap<V> singleton(Short key, V value) {
/* 372 */     return new Singleton<>(key.shortValue(), value);
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
/*     */   public static <V> Short2ReferenceSortedMap<V> singleton(Short key, V value, ShortComparator comparator) {
/* 388 */     return new Singleton<>(key.shortValue(), value, comparator);
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
/*     */   public static <V> Short2ReferenceSortedMap<V> singleton(short key, V value) {
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
/*     */   public static <V> Short2ReferenceSortedMap<V> singleton(short key, V value, ShortComparator comparator) {
/* 419 */     return new Singleton<>(key, value, comparator);
/*     */   }
/*     */   
/*     */   public static class SynchronizedSortedMap<V>
/*     */     extends Short2ReferenceMaps.SynchronizedMap<V> implements Short2ReferenceSortedMap<V>, Serializable {
/*     */     private static final long serialVersionUID = -7046029254386353129L;
/*     */     protected final Short2ReferenceSortedMap<V> sortedMap;
/*     */     
/*     */     protected SynchronizedSortedMap(Short2ReferenceSortedMap<V> m, Object sync) {
/* 428 */       super(m, sync);
/* 429 */       this.sortedMap = m;
/*     */     }
/*     */     
/*     */     protected SynchronizedSortedMap(Short2ReferenceSortedMap<V> m) {
/* 433 */       super(m);
/* 434 */       this.sortedMap = m;
/*     */     }
/*     */ 
/*     */     
/*     */     public ShortComparator comparator() {
/* 439 */       synchronized (this.sync) {
/* 440 */         return this.sortedMap.comparator();
/*     */       } 
/*     */     }
/*     */ 
/*     */     
/*     */     public ObjectSortedSet<Short2ReferenceMap.Entry<V>> short2ReferenceEntrySet() {
/* 446 */       if (this.entries == null) this.entries = (ObjectSet<Short2ReferenceMap.Entry<V>>)ObjectSortedSets.synchronize(this.sortedMap.short2ReferenceEntrySet(), this.sync); 
/* 447 */       return (ObjectSortedSet<Short2ReferenceMap.Entry<V>>)this.entries;
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
/*     */     public ObjectSortedSet<Map.Entry<Short, V>> entrySet() {
/* 459 */       return (ObjectSortedSet)short2ReferenceEntrySet();
/*     */     }
/*     */ 
/*     */     
/*     */     public ShortSortedSet keySet() {
/* 464 */       if (this.keys == null) this.keys = ShortSortedSets.synchronize(this.sortedMap.keySet(), this.sync); 
/* 465 */       return (ShortSortedSet)this.keys;
/*     */     }
/*     */ 
/*     */     
/*     */     public Short2ReferenceSortedMap<V> subMap(short from, short to) {
/* 470 */       return new SynchronizedSortedMap(this.sortedMap.subMap(from, to), this.sync);
/*     */     }
/*     */ 
/*     */     
/*     */     public Short2ReferenceSortedMap<V> headMap(short to) {
/* 475 */       return new SynchronizedSortedMap(this.sortedMap.headMap(to), this.sync);
/*     */     }
/*     */ 
/*     */     
/*     */     public Short2ReferenceSortedMap<V> tailMap(short from) {
/* 480 */       return new SynchronizedSortedMap(this.sortedMap.tailMap(from), this.sync);
/*     */     }
/*     */ 
/*     */     
/*     */     public short firstShortKey() {
/* 485 */       synchronized (this.sync) {
/* 486 */         return this.sortedMap.firstShortKey();
/*     */       } 
/*     */     }
/*     */ 
/*     */     
/*     */     public short lastShortKey() {
/* 492 */       synchronized (this.sync) {
/* 493 */         return this.sortedMap.lastShortKey();
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
/*     */     public Short firstKey() {
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
/*     */     public Short lastKey() {
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
/*     */     public Short2ReferenceSortedMap<V> subMap(Short from, Short to) {
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
/*     */     public Short2ReferenceSortedMap<V> headMap(Short to) {
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
/*     */     public Short2ReferenceSortedMap<V> tailMap(Short from) {
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
/*     */   public static <V> Short2ReferenceSortedMap<V> synchronize(Short2ReferenceSortedMap<V> m) {
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
/*     */   public static <V> Short2ReferenceSortedMap<V> synchronize(Short2ReferenceSortedMap<V> m, Object sync) {
/* 578 */     return new SynchronizedSortedMap<>(m, sync);
/*     */   }
/*     */   
/*     */   public static class UnmodifiableSortedMap<V>
/*     */     extends Short2ReferenceMaps.UnmodifiableMap<V> implements Short2ReferenceSortedMap<V>, Serializable {
/*     */     private static final long serialVersionUID = -7046029254386353129L;
/*     */     protected final Short2ReferenceSortedMap<? extends V> sortedMap;
/*     */     
/*     */     protected UnmodifiableSortedMap(Short2ReferenceSortedMap<? extends V> m) {
/* 587 */       super(m);
/* 588 */       this.sortedMap = m;
/*     */     }
/*     */ 
/*     */     
/*     */     public ShortComparator comparator() {
/* 593 */       return this.sortedMap.comparator();
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     public ObjectSortedSet<Short2ReferenceMap.Entry<V>> short2ReferenceEntrySet() {
/* 599 */       if (this.entries == null) this.entries = (ObjectSet<Short2ReferenceMap.Entry<V>>)ObjectSortedSets.unmodifiable(this.sortedMap.short2ReferenceEntrySet()); 
/* 600 */       return (ObjectSortedSet<Short2ReferenceMap.Entry<V>>)this.entries;
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
/*     */     public ObjectSortedSet<Map.Entry<Short, V>> entrySet() {
/* 612 */       return (ObjectSortedSet)short2ReferenceEntrySet();
/*     */     }
/*     */ 
/*     */     
/*     */     public ShortSortedSet keySet() {
/* 617 */       if (this.keys == null) this.keys = ShortSortedSets.unmodifiable(this.sortedMap.keySet()); 
/* 618 */       return (ShortSortedSet)this.keys;
/*     */     }
/*     */ 
/*     */     
/*     */     public Short2ReferenceSortedMap<V> subMap(short from, short to) {
/* 623 */       return new UnmodifiableSortedMap(this.sortedMap.subMap(from, to));
/*     */     }
/*     */ 
/*     */     
/*     */     public Short2ReferenceSortedMap<V> headMap(short to) {
/* 628 */       return new UnmodifiableSortedMap(this.sortedMap.headMap(to));
/*     */     }
/*     */ 
/*     */     
/*     */     public Short2ReferenceSortedMap<V> tailMap(short from) {
/* 633 */       return new UnmodifiableSortedMap(this.sortedMap.tailMap(from));
/*     */     }
/*     */ 
/*     */     
/*     */     public short firstShortKey() {
/* 638 */       return this.sortedMap.firstShortKey();
/*     */     }
/*     */ 
/*     */     
/*     */     public short lastShortKey() {
/* 643 */       return this.sortedMap.lastShortKey();
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     @Deprecated
/*     */     public Short firstKey() {
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
/*     */     public Short lastKey() {
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
/*     */     public Short2ReferenceSortedMap<V> subMap(Short from, Short to) {
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
/*     */     public Short2ReferenceSortedMap<V> headMap(Short to) {
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
/*     */     public Short2ReferenceSortedMap<V> tailMap(Short from) {
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
/*     */   public static <V> Short2ReferenceSortedMap<V> unmodifiable(Short2ReferenceSortedMap<? extends V> m) {
/* 710 */     return new UnmodifiableSortedMap<>(m);
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\i\\unimi\dsi\fastutil\shorts\Short2ReferenceSortedMaps.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */