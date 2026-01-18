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
/*     */ public final class Object2ReferenceSortedMaps
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
/*     */   public static <K, V> ObjectBidirectionalIterator<Object2ReferenceMap.Entry<K, V>> fastIterator(Object2ReferenceSortedMap<K, V> map) {
/*  56 */     ObjectSortedSet<Object2ReferenceMap.Entry<K, V>> entries = map.object2ReferenceEntrySet();
/*  57 */     return (entries instanceof Object2ReferenceSortedMap.FastSortedEntrySet) ? ((Object2ReferenceSortedMap.FastSortedEntrySet)entries).fastIterator() : entries.iterator();
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
/*     */   public static <K, V> ObjectBidirectionalIterable<Object2ReferenceMap.Entry<K, V>> fastIterable(Object2ReferenceSortedMap<K, V> map) {
/*  72 */     ObjectSortedSet<Object2ReferenceMap.Entry<K, V>> entries = map.object2ReferenceEntrySet();
/*  73 */     Objects.requireNonNull((Object2ReferenceSortedMap.FastSortedEntrySet)entries); return (entries instanceof Object2ReferenceSortedMap.FastSortedEntrySet) ? (Object2ReferenceSortedMap.FastSortedEntrySet)entries::fastIterator : entries;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static class EmptySortedMap<K, V>
/*     */     extends Object2ReferenceMaps.EmptyMap<K, V>
/*     */     implements Object2ReferenceSortedMap<K, V>, Serializable, Cloneable
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
/*     */     public ObjectSortedSet<Object2ReferenceMap.Entry<K, V>> object2ReferenceEntrySet() {
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
/*     */     public ObjectSortedSet<K> keySet() {
/* 107 */       return ObjectSortedSets.EMPTY_SET;
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     public Object2ReferenceSortedMap<K, V> subMap(K from, K to) {
/* 113 */       return Object2ReferenceSortedMaps.EMPTY_MAP;
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     public Object2ReferenceSortedMap<K, V> headMap(K to) {
/* 119 */       return Object2ReferenceSortedMaps.EMPTY_MAP;
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     public Object2ReferenceSortedMap<K, V> tailMap(K from) {
/* 125 */       return Object2ReferenceSortedMaps.EMPTY_MAP;
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
/*     */   public static <K, V> Object2ReferenceSortedMap<K, V> emptyMap() {
/* 155 */     return EMPTY_MAP;
/*     */   }
/*     */ 
/*     */   
/*     */   public static class Singleton<K, V>
/*     */     extends Object2ReferenceMaps.Singleton<K, V>
/*     */     implements Object2ReferenceSortedMap<K, V>, Serializable, Cloneable
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
/*     */     public ObjectSortedSet<Object2ReferenceMap.Entry<K, V>> object2ReferenceEntrySet() {
/* 190 */       if (this.entries == null) this.entries = ObjectSortedSets.singleton(new AbstractObject2ReferenceMap.BasicEntry<>(this.key, this.value), (Comparator)Object2ReferenceSortedMaps.entryComparator(this.comparator)); 
/* 191 */       return (ObjectSortedSet<Object2ReferenceMap.Entry<K, V>>)this.entries;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public ObjectSortedSet<Map.Entry<K, V>> entrySet() {
/* 198 */       return (ObjectSortedSet)object2ReferenceEntrySet();
/*     */     }
/*     */ 
/*     */     
/*     */     public ObjectSortedSet<K> keySet() {
/* 203 */       if (this.keys == null) this.keys = ObjectSortedSets.singleton(this.key, this.comparator); 
/* 204 */       return (ObjectSortedSet<K>)this.keys;
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     public Object2ReferenceSortedMap<K, V> subMap(K from, K to) {
/* 210 */       if (compare(from, this.key) <= 0 && compare(this.key, to) < 0) return this; 
/* 211 */       return Object2ReferenceSortedMaps.EMPTY_MAP;
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     public Object2ReferenceSortedMap<K, V> headMap(K to) {
/* 217 */       if (compare(this.key, to) < 0) return this; 
/* 218 */       return Object2ReferenceSortedMaps.EMPTY_MAP;
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     public Object2ReferenceSortedMap<K, V> tailMap(K from) {
/* 224 */       if (compare(from, this.key) <= 0) return this; 
/* 225 */       return Object2ReferenceSortedMaps.EMPTY_MAP;
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
/*     */   public static <K, V> Object2ReferenceSortedMap<K, V> singleton(K key, V value) {
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
/*     */   public static <K, V> Object2ReferenceSortedMap<K, V> singleton(K key, V value, Comparator<? super K> comparator) {
/* 267 */     return new Singleton<>(key, value, comparator);
/*     */   }
/*     */   
/*     */   public static class SynchronizedSortedMap<K, V>
/*     */     extends Object2ReferenceMaps.SynchronizedMap<K, V> implements Object2ReferenceSortedMap<K, V>, Serializable {
/*     */     private static final long serialVersionUID = -7046029254386353129L;
/*     */     protected final Object2ReferenceSortedMap<K, V> sortedMap;
/*     */     
/*     */     protected SynchronizedSortedMap(Object2ReferenceSortedMap<K, V> m, Object sync) {
/* 276 */       super(m, sync);
/* 277 */       this.sortedMap = m;
/*     */     }
/*     */     
/*     */     protected SynchronizedSortedMap(Object2ReferenceSortedMap<K, V> m) {
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
/*     */     public ObjectSortedSet<Object2ReferenceMap.Entry<K, V>> object2ReferenceEntrySet() {
/* 294 */       if (this.entries == null) this.entries = ObjectSortedSets.synchronize(this.sortedMap.object2ReferenceEntrySet(), this.sync); 
/* 295 */       return (ObjectSortedSet<Object2ReferenceMap.Entry<K, V>>)this.entries;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public ObjectSortedSet<Map.Entry<K, V>> entrySet() {
/* 302 */       return (ObjectSortedSet)object2ReferenceEntrySet();
/*     */     }
/*     */ 
/*     */     
/*     */     public ObjectSortedSet<K> keySet() {
/* 307 */       if (this.keys == null) this.keys = ObjectSortedSets.synchronize(this.sortedMap.keySet(), this.sync); 
/* 308 */       return (ObjectSortedSet<K>)this.keys;
/*     */     }
/*     */ 
/*     */     
/*     */     public Object2ReferenceSortedMap<K, V> subMap(K from, K to) {
/* 313 */       return new SynchronizedSortedMap(this.sortedMap.subMap(from, to), this.sync);
/*     */     }
/*     */ 
/*     */     
/*     */     public Object2ReferenceSortedMap<K, V> headMap(K to) {
/* 318 */       return new SynchronizedSortedMap(this.sortedMap.headMap(to), this.sync);
/*     */     }
/*     */ 
/*     */     
/*     */     public Object2ReferenceSortedMap<K, V> tailMap(K from) {
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
/*     */   public static <K, V> Object2ReferenceSortedMap<K, V> synchronize(Object2ReferenceSortedMap<K, V> m) {
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
/*     */   public static <K, V> Object2ReferenceSortedMap<K, V> synchronize(Object2ReferenceSortedMap<K, V> m, Object sync) {
/* 362 */     return new SynchronizedSortedMap<>(m, sync);
/*     */   }
/*     */   
/*     */   public static class UnmodifiableSortedMap<K, V>
/*     */     extends Object2ReferenceMaps.UnmodifiableMap<K, V> implements Object2ReferenceSortedMap<K, V>, Serializable {
/*     */     private static final long serialVersionUID = -7046029254386353129L;
/*     */     protected final Object2ReferenceSortedMap<K, ? extends V> sortedMap;
/*     */     
/*     */     protected UnmodifiableSortedMap(Object2ReferenceSortedMap<K, ? extends V> m) {
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
/*     */     public ObjectSortedSet<Object2ReferenceMap.Entry<K, V>> object2ReferenceEntrySet() {
/* 383 */       if (this.entries == null) this.entries = ObjectSortedSets.unmodifiable((ObjectSortedSet)this.sortedMap.object2ReferenceEntrySet()); 
/* 384 */       return (ObjectSortedSet<Object2ReferenceMap.Entry<K, V>>)this.entries;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public ObjectSortedSet<Map.Entry<K, V>> entrySet() {
/* 391 */       return (ObjectSortedSet)object2ReferenceEntrySet();
/*     */     }
/*     */ 
/*     */     
/*     */     public ObjectSortedSet<K> keySet() {
/* 396 */       if (this.keys == null) this.keys = ObjectSortedSets.unmodifiable(this.sortedMap.keySet()); 
/* 397 */       return (ObjectSortedSet<K>)this.keys;
/*     */     }
/*     */ 
/*     */     
/*     */     public Object2ReferenceSortedMap<K, V> subMap(K from, K to) {
/* 402 */       return new UnmodifiableSortedMap(this.sortedMap.subMap(from, to));
/*     */     }
/*     */ 
/*     */     
/*     */     public Object2ReferenceSortedMap<K, V> headMap(K to) {
/* 407 */       return new UnmodifiableSortedMap(this.sortedMap.headMap(to));
/*     */     }
/*     */ 
/*     */     
/*     */     public Object2ReferenceSortedMap<K, V> tailMap(K from) {
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
/*     */   public static <K, V> Object2ReferenceSortedMap<K, V> unmodifiable(Object2ReferenceSortedMap<K, ? extends V> m) {
/* 434 */     return new UnmodifiableSortedMap<>(m);
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\i\\unimi\dsi\fastutil\objects\Object2ReferenceSortedMaps.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */