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
/*     */ public final class Object2ByteSortedMaps
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
/*     */   public static <K> ObjectBidirectionalIterator<Object2ByteMap.Entry<K>> fastIterator(Object2ByteSortedMap<K> map) {
/*  56 */     ObjectSortedSet<Object2ByteMap.Entry<K>> entries = map.object2ByteEntrySet();
/*  57 */     return (entries instanceof Object2ByteSortedMap.FastSortedEntrySet) ? ((Object2ByteSortedMap.FastSortedEntrySet)entries).fastIterator() : entries.iterator();
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
/*     */   public static <K> ObjectBidirectionalIterable<Object2ByteMap.Entry<K>> fastIterable(Object2ByteSortedMap<K> map) {
/*  72 */     ObjectSortedSet<Object2ByteMap.Entry<K>> entries = map.object2ByteEntrySet();
/*  73 */     Objects.requireNonNull((Object2ByteSortedMap.FastSortedEntrySet)entries); return (entries instanceof Object2ByteSortedMap.FastSortedEntrySet) ? (Object2ByteSortedMap.FastSortedEntrySet)entries::fastIterator : entries;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static class EmptySortedMap<K>
/*     */     extends Object2ByteMaps.EmptyMap<K>
/*     */     implements Object2ByteSortedMap<K>, Serializable, Cloneable
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
/*     */     public ObjectSortedSet<Object2ByteMap.Entry<K>> object2ByteEntrySet() {
/*  95 */       return ObjectSortedSets.EMPTY_SET;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     @Deprecated
/*     */     public ObjectSortedSet<Map.Entry<K, Byte>> entrySet() {
/* 106 */       return ObjectSortedSets.EMPTY_SET;
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     public ObjectSortedSet<K> keySet() {
/* 112 */       return ObjectSortedSets.EMPTY_SET;
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     public Object2ByteSortedMap<K> subMap(K from, K to) {
/* 118 */       return Object2ByteSortedMaps.EMPTY_MAP;
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     public Object2ByteSortedMap<K> headMap(K to) {
/* 124 */       return Object2ByteSortedMaps.EMPTY_MAP;
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     public Object2ByteSortedMap<K> tailMap(K from) {
/* 130 */       return Object2ByteSortedMaps.EMPTY_MAP;
/*     */     }
/*     */ 
/*     */     
/*     */     public K firstKey() {
/* 135 */       throw new NoSuchElementException();
/*     */     }
/*     */ 
/*     */     
/*     */     public K lastKey() {
/* 140 */       throw new NoSuchElementException();
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 148 */   public static final EmptySortedMap EMPTY_MAP = new EmptySortedMap();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static <K> Object2ByteSortedMap<K> emptyMap() {
/* 160 */     return EMPTY_MAP;
/*     */   }
/*     */ 
/*     */   
/*     */   public static class Singleton<K>
/*     */     extends Object2ByteMaps.Singleton<K>
/*     */     implements Object2ByteSortedMap<K>, Serializable, Cloneable
/*     */   {
/*     */     private static final long serialVersionUID = -7046029254386353129L;
/*     */     
/*     */     protected final Comparator<? super K> comparator;
/*     */ 
/*     */     
/*     */     protected Singleton(K key, byte value, Comparator<? super K> comparator) {
/* 174 */       super(key, value);
/* 175 */       this.comparator = comparator;
/*     */     }
/*     */     
/*     */     protected Singleton(K key, byte value) {
/* 179 */       this(key, value, (Comparator<? super K>)null);
/*     */     }
/*     */ 
/*     */     
/*     */     final int compare(K k1, K k2) {
/* 184 */       return (this.comparator == null) ? ((Comparable<K>)k1).compareTo(k2) : this.comparator.compare(k1, k2);
/*     */     }
/*     */ 
/*     */     
/*     */     public Comparator<? super K> comparator() {
/* 189 */       return this.comparator;
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     public ObjectSortedSet<Object2ByteMap.Entry<K>> object2ByteEntrySet() {
/* 195 */       if (this.entries == null) this.entries = ObjectSortedSets.singleton(new AbstractObject2ByteMap.BasicEntry<>(this.key, this.value), (Comparator)Object2ByteSortedMaps.entryComparator(this.comparator)); 
/* 196 */       return (ObjectSortedSet<Object2ByteMap.Entry<K>>)this.entries;
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
/*     */     public ObjectSortedSet<Map.Entry<K, Byte>> entrySet() {
/* 208 */       return (ObjectSortedSet)object2ByteEntrySet();
/*     */     }
/*     */ 
/*     */     
/*     */     public ObjectSortedSet<K> keySet() {
/* 213 */       if (this.keys == null) this.keys = ObjectSortedSets.singleton(this.key, this.comparator); 
/* 214 */       return (ObjectSortedSet<K>)this.keys;
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     public Object2ByteSortedMap<K> subMap(K from, K to) {
/* 220 */       if (compare(from, this.key) <= 0 && compare(this.key, to) < 0) return this; 
/* 221 */       return Object2ByteSortedMaps.EMPTY_MAP;
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     public Object2ByteSortedMap<K> headMap(K to) {
/* 227 */       if (compare(this.key, to) < 0) return this; 
/* 228 */       return Object2ByteSortedMaps.EMPTY_MAP;
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     public Object2ByteSortedMap<K> tailMap(K from) {
/* 234 */       if (compare(from, this.key) <= 0) return this; 
/* 235 */       return Object2ByteSortedMaps.EMPTY_MAP;
/*     */     }
/*     */ 
/*     */     
/*     */     public K firstKey() {
/* 240 */       return this.key;
/*     */     }
/*     */ 
/*     */     
/*     */     public K lastKey() {
/* 245 */       return this.key;
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
/*     */   public static <K> Object2ByteSortedMap<K> singleton(K key, Byte value) {
/* 261 */     return new Singleton<>(key, value.byteValue());
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
/*     */   public static <K> Object2ByteSortedMap<K> singleton(K key, Byte value, Comparator<? super K> comparator) {
/* 277 */     return new Singleton<>(key, value.byteValue(), comparator);
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
/*     */   public static <K> Object2ByteSortedMap<K> singleton(K key, byte value) {
/* 292 */     return new Singleton<>(key, value);
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
/*     */   public static <K> Object2ByteSortedMap<K> singleton(K key, byte value, Comparator<? super K> comparator) {
/* 308 */     return new Singleton<>(key, value, comparator);
/*     */   }
/*     */   
/*     */   public static class SynchronizedSortedMap<K>
/*     */     extends Object2ByteMaps.SynchronizedMap<K> implements Object2ByteSortedMap<K>, Serializable {
/*     */     private static final long serialVersionUID = -7046029254386353129L;
/*     */     protected final Object2ByteSortedMap<K> sortedMap;
/*     */     
/*     */     protected SynchronizedSortedMap(Object2ByteSortedMap<K> m, Object sync) {
/* 317 */       super(m, sync);
/* 318 */       this.sortedMap = m;
/*     */     }
/*     */     
/*     */     protected SynchronizedSortedMap(Object2ByteSortedMap<K> m) {
/* 322 */       super(m);
/* 323 */       this.sortedMap = m;
/*     */     }
/*     */ 
/*     */     
/*     */     public Comparator<? super K> comparator() {
/* 328 */       synchronized (this.sync) {
/* 329 */         return this.sortedMap.comparator();
/*     */       } 
/*     */     }
/*     */ 
/*     */     
/*     */     public ObjectSortedSet<Object2ByteMap.Entry<K>> object2ByteEntrySet() {
/* 335 */       if (this.entries == null) this.entries = ObjectSortedSets.synchronize(this.sortedMap.object2ByteEntrySet(), this.sync); 
/* 336 */       return (ObjectSortedSet<Object2ByteMap.Entry<K>>)this.entries;
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
/*     */     public ObjectSortedSet<Map.Entry<K, Byte>> entrySet() {
/* 348 */       return (ObjectSortedSet)object2ByteEntrySet();
/*     */     }
/*     */ 
/*     */     
/*     */     public ObjectSortedSet<K> keySet() {
/* 353 */       if (this.keys == null) this.keys = ObjectSortedSets.synchronize(this.sortedMap.keySet(), this.sync); 
/* 354 */       return (ObjectSortedSet<K>)this.keys;
/*     */     }
/*     */ 
/*     */     
/*     */     public Object2ByteSortedMap<K> subMap(K from, K to) {
/* 359 */       return new SynchronizedSortedMap(this.sortedMap.subMap(from, to), this.sync);
/*     */     }
/*     */ 
/*     */     
/*     */     public Object2ByteSortedMap<K> headMap(K to) {
/* 364 */       return new SynchronizedSortedMap(this.sortedMap.headMap(to), this.sync);
/*     */     }
/*     */ 
/*     */     
/*     */     public Object2ByteSortedMap<K> tailMap(K from) {
/* 369 */       return new SynchronizedSortedMap(this.sortedMap.tailMap(from), this.sync);
/*     */     }
/*     */ 
/*     */     
/*     */     public K firstKey() {
/* 374 */       synchronized (this.sync) {
/* 375 */         return this.sortedMap.firstKey();
/*     */       } 
/*     */     }
/*     */ 
/*     */     
/*     */     public K lastKey() {
/* 381 */       synchronized (this.sync) {
/* 382 */         return this.sortedMap.lastKey();
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
/*     */   public static <K> Object2ByteSortedMap<K> synchronize(Object2ByteSortedMap<K> m) {
/* 395 */     return new SynchronizedSortedMap<>(m);
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
/*     */   public static <K> Object2ByteSortedMap<K> synchronize(Object2ByteSortedMap<K> m, Object sync) {
/* 408 */     return new SynchronizedSortedMap<>(m, sync);
/*     */   }
/*     */   
/*     */   public static class UnmodifiableSortedMap<K>
/*     */     extends Object2ByteMaps.UnmodifiableMap<K> implements Object2ByteSortedMap<K>, Serializable {
/*     */     private static final long serialVersionUID = -7046029254386353129L;
/*     */     protected final Object2ByteSortedMap<K> sortedMap;
/*     */     
/*     */     protected UnmodifiableSortedMap(Object2ByteSortedMap<K> m) {
/* 417 */       super(m);
/* 418 */       this.sortedMap = m;
/*     */     }
/*     */ 
/*     */     
/*     */     public Comparator<? super K> comparator() {
/* 423 */       return this.sortedMap.comparator();
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     public ObjectSortedSet<Object2ByteMap.Entry<K>> object2ByteEntrySet() {
/* 429 */       if (this.entries == null) this.entries = ObjectSortedSets.unmodifiable(this.sortedMap.object2ByteEntrySet()); 
/* 430 */       return (ObjectSortedSet<Object2ByteMap.Entry<K>>)this.entries;
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
/*     */     public ObjectSortedSet<Map.Entry<K, Byte>> entrySet() {
/* 442 */       return (ObjectSortedSet)object2ByteEntrySet();
/*     */     }
/*     */ 
/*     */     
/*     */     public ObjectSortedSet<K> keySet() {
/* 447 */       if (this.keys == null) this.keys = ObjectSortedSets.unmodifiable(this.sortedMap.keySet()); 
/* 448 */       return (ObjectSortedSet<K>)this.keys;
/*     */     }
/*     */ 
/*     */     
/*     */     public Object2ByteSortedMap<K> subMap(K from, K to) {
/* 453 */       return new UnmodifiableSortedMap(this.sortedMap.subMap(from, to));
/*     */     }
/*     */ 
/*     */     
/*     */     public Object2ByteSortedMap<K> headMap(K to) {
/* 458 */       return new UnmodifiableSortedMap(this.sortedMap.headMap(to));
/*     */     }
/*     */ 
/*     */     
/*     */     public Object2ByteSortedMap<K> tailMap(K from) {
/* 463 */       return new UnmodifiableSortedMap(this.sortedMap.tailMap(from));
/*     */     }
/*     */ 
/*     */     
/*     */     public K firstKey() {
/* 468 */       return this.sortedMap.firstKey();
/*     */     }
/*     */ 
/*     */     
/*     */     public K lastKey() {
/* 473 */       return this.sortedMap.lastKey();
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
/*     */   public static <K> Object2ByteSortedMap<K> unmodifiable(Object2ByteSortedMap<K> m) {
/* 485 */     return new UnmodifiableSortedMap<>(m);
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\i\\unimi\dsi\fastutil\objects\Object2ByteSortedMaps.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */