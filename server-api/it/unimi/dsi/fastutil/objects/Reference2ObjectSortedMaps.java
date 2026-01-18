/*     */ package it.unimi.dsi.fastutil.objects;
/*     */ 
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
/*     */ 
/*     */ public final class Reference2ObjectSortedMaps
/*     */ {
/*     */   public static <K> Comparator<? super Map.Entry<K, ?>> entryComparator(Comparator<? super K> comparator) {
/*  41 */     return (x, y) -> comparator.compare(x.getKey(), y.getKey());
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
/*     */   public static <K, V> ObjectBidirectionalIterator<Reference2ObjectMap.Entry<K, V>> fastIterator(Reference2ObjectSortedMap<K, V> map) {
/*  56 */     ObjectSortedSet<Reference2ObjectMap.Entry<K, V>> entries = map.reference2ObjectEntrySet();
/*  57 */     return (entries instanceof Reference2ObjectSortedMap.FastSortedEntrySet) ? ((Reference2ObjectSortedMap.FastSortedEntrySet)entries).fastIterator() : entries.iterator();
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
/*     */   public static <K, V> ObjectBidirectionalIterable<Reference2ObjectMap.Entry<K, V>> fastIterable(Reference2ObjectSortedMap<K, V> map) {
/*  72 */     ObjectSortedSet<Reference2ObjectMap.Entry<K, V>> entries = map.reference2ObjectEntrySet();
/*  73 */     Objects.requireNonNull((Reference2ObjectSortedMap.FastSortedEntrySet)entries); return (entries instanceof Reference2ObjectSortedMap.FastSortedEntrySet) ? (Reference2ObjectSortedMap.FastSortedEntrySet)entries::fastIterator : entries;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static class EmptySortedMap<K, V>
/*     */     extends Reference2ObjectMaps.EmptyMap<K, V>
/*     */     implements Reference2ObjectSortedMap<K, V>, Serializable, Cloneable
/*     */   {
/*     */     private static final long serialVersionUID = -7046029254386353129L;
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public Comparator<? super K> comparator() {
/*  90 */       return null;
/*     */     }
/*     */ 
/*     */     
/*     */     public ObjectSortedSet<Reference2ObjectMap.Entry<K, V>> reference2ObjectEntrySet() {
/*  95 */       return ObjectSortedSets.EMPTY_SET;
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     public ObjectSortedSet<Map.Entry<K, V>> entrySet() {
/* 101 */       return ObjectSortedSets.EMPTY_SET;
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     public ReferenceSortedSet<K> keySet() {
/* 107 */       return ReferenceSortedSets.EMPTY_SET;
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     public Reference2ObjectSortedMap<K, V> subMap(K from, K to) {
/* 113 */       return Reference2ObjectSortedMaps.EMPTY_MAP;
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     public Reference2ObjectSortedMap<K, V> headMap(K to) {
/* 119 */       return Reference2ObjectSortedMaps.EMPTY_MAP;
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     public Reference2ObjectSortedMap<K, V> tailMap(K from) {
/* 125 */       return Reference2ObjectSortedMaps.EMPTY_MAP;
/*     */     }
/*     */ 
/*     */     
/*     */     public K firstKey() {
/* 130 */       throw new NoSuchElementException();
/*     */     }
/*     */ 
/*     */     
/*     */     public K lastKey() {
/* 135 */       throw new NoSuchElementException();
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 143 */   public static final EmptySortedMap EMPTY_MAP = new EmptySortedMap<>();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static <K, V> Reference2ObjectSortedMap<K, V> emptyMap() {
/* 155 */     return EMPTY_MAP;
/*     */   }
/*     */ 
/*     */   
/*     */   public static class Singleton<K, V>
/*     */     extends Reference2ObjectMaps.Singleton<K, V>
/*     */     implements Reference2ObjectSortedMap<K, V>, Serializable, Cloneable
/*     */   {
/*     */     private static final long serialVersionUID = -7046029254386353129L;
/*     */     
/*     */     protected final Comparator<? super K> comparator;
/*     */ 
/*     */     
/*     */     protected Singleton(K key, V value, Comparator<? super K> comparator) {
/* 169 */       super(key, value);
/* 170 */       this.comparator = comparator;
/*     */     }
/*     */     
/*     */     protected Singleton(K key, V value) {
/* 174 */       this(key, value, (Comparator<? super K>)null);
/*     */     }
/*     */ 
/*     */     
/*     */     final int compare(K k1, K k2) {
/* 179 */       return (this.comparator == null) ? ((Comparable<K>)k1).compareTo(k2) : this.comparator.compare(k1, k2);
/*     */     }
/*     */ 
/*     */     
/*     */     public Comparator<? super K> comparator() {
/* 184 */       return this.comparator;
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     public ObjectSortedSet<Reference2ObjectMap.Entry<K, V>> reference2ObjectEntrySet() {
/* 190 */       if (this.entries == null) this.entries = ObjectSortedSets.singleton(new AbstractReference2ObjectMap.BasicEntry<>(this.key, this.value), (Comparator)Reference2ObjectSortedMaps.entryComparator(this.comparator)); 
/* 191 */       return (ObjectSortedSet<Reference2ObjectMap.Entry<K, V>>)this.entries;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public ObjectSortedSet<Map.Entry<K, V>> entrySet() {
/* 198 */       return (ObjectSortedSet)reference2ObjectEntrySet();
/*     */     }
/*     */ 
/*     */     
/*     */     public ReferenceSortedSet<K> keySet() {
/* 203 */       if (this.keys == null) this.keys = ReferenceSortedSets.singleton(this.key, this.comparator); 
/* 204 */       return (ReferenceSortedSet<K>)this.keys;
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     public Reference2ObjectSortedMap<K, V> subMap(K from, K to) {
/* 210 */       if (compare(from, this.key) <= 0 && compare(this.key, to) < 0) return this; 
/* 211 */       return Reference2ObjectSortedMaps.EMPTY_MAP;
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     public Reference2ObjectSortedMap<K, V> headMap(K to) {
/* 217 */       if (compare(this.key, to) < 0) return this; 
/* 218 */       return Reference2ObjectSortedMaps.EMPTY_MAP;
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     public Reference2ObjectSortedMap<K, V> tailMap(K from) {
/* 224 */       if (compare(from, this.key) <= 0) return this; 
/* 225 */       return Reference2ObjectSortedMaps.EMPTY_MAP;
/*     */     }
/*     */ 
/*     */     
/*     */     public K firstKey() {
/* 230 */       return this.key;
/*     */     }
/*     */ 
/*     */     
/*     */     public K lastKey() {
/* 235 */       return this.key;
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
/*     */   public static <K, V> Reference2ObjectSortedMap<K, V> singleton(K key, V value) {
/* 251 */     return new Singleton<>(key, value);
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
/*     */   public static <K, V> Reference2ObjectSortedMap<K, V> singleton(K key, V value, Comparator<? super K> comparator) {
/* 267 */     return new Singleton<>(key, value, comparator);
/*     */   }
/*     */   
/*     */   public static class SynchronizedSortedMap<K, V>
/*     */     extends Reference2ObjectMaps.SynchronizedMap<K, V> implements Reference2ObjectSortedMap<K, V>, Serializable {
/*     */     private static final long serialVersionUID = -7046029254386353129L;
/*     */     protected final Reference2ObjectSortedMap<K, V> sortedMap;
/*     */     
/*     */     protected SynchronizedSortedMap(Reference2ObjectSortedMap<K, V> m, Object sync) {
/* 276 */       super(m, sync);
/* 277 */       this.sortedMap = m;
/*     */     }
/*     */     
/*     */     protected SynchronizedSortedMap(Reference2ObjectSortedMap<K, V> m) {
/* 281 */       super(m);
/* 282 */       this.sortedMap = m;
/*     */     }
/*     */ 
/*     */     
/*     */     public Comparator<? super K> comparator() {
/* 287 */       synchronized (this.sync) {
/* 288 */         return this.sortedMap.comparator();
/*     */       } 
/*     */     }
/*     */ 
/*     */     
/*     */     public ObjectSortedSet<Reference2ObjectMap.Entry<K, V>> reference2ObjectEntrySet() {
/* 294 */       if (this.entries == null) this.entries = ObjectSortedSets.synchronize(this.sortedMap.reference2ObjectEntrySet(), this.sync); 
/* 295 */       return (ObjectSortedSet<Reference2ObjectMap.Entry<K, V>>)this.entries;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public ObjectSortedSet<Map.Entry<K, V>> entrySet() {
/* 302 */       return (ObjectSortedSet)reference2ObjectEntrySet();
/*     */     }
/*     */ 
/*     */     
/*     */     public ReferenceSortedSet<K> keySet() {
/* 307 */       if (this.keys == null) this.keys = ReferenceSortedSets.synchronize(this.sortedMap.keySet(), this.sync); 
/* 308 */       return (ReferenceSortedSet<K>)this.keys;
/*     */     }
/*     */ 
/*     */     
/*     */     public Reference2ObjectSortedMap<K, V> subMap(K from, K to) {
/* 313 */       return new SynchronizedSortedMap(this.sortedMap.subMap(from, to), this.sync);
/*     */     }
/*     */ 
/*     */     
/*     */     public Reference2ObjectSortedMap<K, V> headMap(K to) {
/* 318 */       return new SynchronizedSortedMap(this.sortedMap.headMap(to), this.sync);
/*     */     }
/*     */ 
/*     */     
/*     */     public Reference2ObjectSortedMap<K, V> tailMap(K from) {
/* 323 */       return new SynchronizedSortedMap(this.sortedMap.tailMap(from), this.sync);
/*     */     }
/*     */ 
/*     */     
/*     */     public K firstKey() {
/* 328 */       synchronized (this.sync) {
/* 329 */         return this.sortedMap.firstKey();
/*     */       } 
/*     */     }
/*     */ 
/*     */     
/*     */     public K lastKey() {
/* 335 */       synchronized (this.sync) {
/* 336 */         return this.sortedMap.lastKey();
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
/*     */   public static <K, V> Reference2ObjectSortedMap<K, V> synchronize(Reference2ObjectSortedMap<K, V> m) {
/* 349 */     return new SynchronizedSortedMap<>(m);
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
/*     */   public static <K, V> Reference2ObjectSortedMap<K, V> synchronize(Reference2ObjectSortedMap<K, V> m, Object sync) {
/* 362 */     return new SynchronizedSortedMap<>(m, sync);
/*     */   }
/*     */   
/*     */   public static class UnmodifiableSortedMap<K, V>
/*     */     extends Reference2ObjectMaps.UnmodifiableMap<K, V> implements Reference2ObjectSortedMap<K, V>, Serializable {
/*     */     private static final long serialVersionUID = -7046029254386353129L;
/*     */     protected final Reference2ObjectSortedMap<K, ? extends V> sortedMap;
/*     */     
/*     */     protected UnmodifiableSortedMap(Reference2ObjectSortedMap<K, ? extends V> m) {
/* 371 */       super(m);
/* 372 */       this.sortedMap = m;
/*     */     }
/*     */ 
/*     */     
/*     */     public Comparator<? super K> comparator() {
/* 377 */       return this.sortedMap.comparator();
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     public ObjectSortedSet<Reference2ObjectMap.Entry<K, V>> reference2ObjectEntrySet() {
/* 383 */       if (this.entries == null) this.entries = ObjectSortedSets.unmodifiable((ObjectSortedSet)this.sortedMap.reference2ObjectEntrySet()); 
/* 384 */       return (ObjectSortedSet<Reference2ObjectMap.Entry<K, V>>)this.entries;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public ObjectSortedSet<Map.Entry<K, V>> entrySet() {
/* 391 */       return (ObjectSortedSet)reference2ObjectEntrySet();
/*     */     }
/*     */ 
/*     */     
/*     */     public ReferenceSortedSet<K> keySet() {
/* 396 */       if (this.keys == null) this.keys = ReferenceSortedSets.unmodifiable(this.sortedMap.keySet()); 
/* 397 */       return (ReferenceSortedSet<K>)this.keys;
/*     */     }
/*     */ 
/*     */     
/*     */     public Reference2ObjectSortedMap<K, V> subMap(K from, K to) {
/* 402 */       return new UnmodifiableSortedMap(this.sortedMap.subMap(from, to));
/*     */     }
/*     */ 
/*     */     
/*     */     public Reference2ObjectSortedMap<K, V> headMap(K to) {
/* 407 */       return new UnmodifiableSortedMap(this.sortedMap.headMap(to));
/*     */     }
/*     */ 
/*     */     
/*     */     public Reference2ObjectSortedMap<K, V> tailMap(K from) {
/* 412 */       return new UnmodifiableSortedMap(this.sortedMap.tailMap(from));
/*     */     }
/*     */ 
/*     */     
/*     */     public K firstKey() {
/* 417 */       return this.sortedMap.firstKey();
/*     */     }
/*     */ 
/*     */     
/*     */     public K lastKey() {
/* 422 */       return this.sortedMap.lastKey();
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
/*     */   public static <K, V> Reference2ObjectSortedMap<K, V> unmodifiable(Reference2ObjectSortedMap<K, ? extends V> m) {
/* 434 */     return new UnmodifiableSortedMap<>(m);
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\i\\unimi\dsi\fastutil\objects\Reference2ObjectSortedMaps.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */