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
/*     */ public final class Object2BooleanSortedMaps
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
/*     */   public static <K> ObjectBidirectionalIterator<Object2BooleanMap.Entry<K>> fastIterator(Object2BooleanSortedMap<K> map) {
/*  56 */     ObjectSortedSet<Object2BooleanMap.Entry<K>> entries = map.object2BooleanEntrySet();
/*  57 */     return (entries instanceof Object2BooleanSortedMap.FastSortedEntrySet) ? ((Object2BooleanSortedMap.FastSortedEntrySet)entries).fastIterator() : entries.iterator();
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
/*     */   public static <K> ObjectBidirectionalIterable<Object2BooleanMap.Entry<K>> fastIterable(Object2BooleanSortedMap<K> map) {
/*  72 */     ObjectSortedSet<Object2BooleanMap.Entry<K>> entries = map.object2BooleanEntrySet();
/*  73 */     Objects.requireNonNull((Object2BooleanSortedMap.FastSortedEntrySet)entries); return (entries instanceof Object2BooleanSortedMap.FastSortedEntrySet) ? (Object2BooleanSortedMap.FastSortedEntrySet)entries::fastIterator : entries;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static class EmptySortedMap<K>
/*     */     extends Object2BooleanMaps.EmptyMap<K>
/*     */     implements Object2BooleanSortedMap<K>, Serializable, Cloneable
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
/*     */     public ObjectSortedSet<Object2BooleanMap.Entry<K>> object2BooleanEntrySet() {
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
/*     */     public ObjectSortedSet<Map.Entry<K, Boolean>> entrySet() {
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
/*     */     public Object2BooleanSortedMap<K> subMap(K from, K to) {
/* 118 */       return Object2BooleanSortedMaps.EMPTY_MAP;
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     public Object2BooleanSortedMap<K> headMap(K to) {
/* 124 */       return Object2BooleanSortedMaps.EMPTY_MAP;
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     public Object2BooleanSortedMap<K> tailMap(K from) {
/* 130 */       return Object2BooleanSortedMaps.EMPTY_MAP;
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
/*     */   public static <K> Object2BooleanSortedMap<K> emptyMap() {
/* 160 */     return EMPTY_MAP;
/*     */   }
/*     */ 
/*     */   
/*     */   public static class Singleton<K>
/*     */     extends Object2BooleanMaps.Singleton<K>
/*     */     implements Object2BooleanSortedMap<K>, Serializable, Cloneable
/*     */   {
/*     */     private static final long serialVersionUID = -7046029254386353129L;
/*     */     
/*     */     protected final Comparator<? super K> comparator;
/*     */ 
/*     */     
/*     */     protected Singleton(K key, boolean value, Comparator<? super K> comparator) {
/* 174 */       super(key, value);
/* 175 */       this.comparator = comparator;
/*     */     }
/*     */     
/*     */     protected Singleton(K key, boolean value) {
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
/*     */     public ObjectSortedSet<Object2BooleanMap.Entry<K>> object2BooleanEntrySet() {
/* 195 */       if (this.entries == null) this.entries = ObjectSortedSets.singleton(new AbstractObject2BooleanMap.BasicEntry<>(this.key, this.value), (Comparator)Object2BooleanSortedMaps.entryComparator(this.comparator)); 
/* 196 */       return (ObjectSortedSet<Object2BooleanMap.Entry<K>>)this.entries;
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
/*     */     public ObjectSortedSet<Map.Entry<K, Boolean>> entrySet() {
/* 208 */       return (ObjectSortedSet)object2BooleanEntrySet();
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
/*     */     public Object2BooleanSortedMap<K> subMap(K from, K to) {
/* 220 */       if (compare(from, this.key) <= 0 && compare(this.key, to) < 0) return this; 
/* 221 */       return Object2BooleanSortedMaps.EMPTY_MAP;
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     public Object2BooleanSortedMap<K> headMap(K to) {
/* 227 */       if (compare(this.key, to) < 0) return this; 
/* 228 */       return Object2BooleanSortedMaps.EMPTY_MAP;
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     public Object2BooleanSortedMap<K> tailMap(K from) {
/* 234 */       if (compare(from, this.key) <= 0) return this; 
/* 235 */       return Object2BooleanSortedMaps.EMPTY_MAP;
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
/*     */   public static <K> Object2BooleanSortedMap<K> singleton(K key, Boolean value) {
/* 261 */     return new Singleton<>(key, value.booleanValue());
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
/*     */   public static <K> Object2BooleanSortedMap<K> singleton(K key, Boolean value, Comparator<? super K> comparator) {
/* 277 */     return new Singleton<>(key, value.booleanValue(), comparator);
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
/*     */   public static <K> Object2BooleanSortedMap<K> singleton(K key, boolean value) {
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
/*     */   public static <K> Object2BooleanSortedMap<K> singleton(K key, boolean value, Comparator<? super K> comparator) {
/* 308 */     return new Singleton<>(key, value, comparator);
/*     */   }
/*     */   
/*     */   public static class SynchronizedSortedMap<K>
/*     */     extends Object2BooleanMaps.SynchronizedMap<K> implements Object2BooleanSortedMap<K>, Serializable {
/*     */     private static final long serialVersionUID = -7046029254386353129L;
/*     */     protected final Object2BooleanSortedMap<K> sortedMap;
/*     */     
/*     */     protected SynchronizedSortedMap(Object2BooleanSortedMap<K> m, Object sync) {
/* 317 */       super(m, sync);
/* 318 */       this.sortedMap = m;
/*     */     }
/*     */     
/*     */     protected SynchronizedSortedMap(Object2BooleanSortedMap<K> m) {
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
/*     */     public ObjectSortedSet<Object2BooleanMap.Entry<K>> object2BooleanEntrySet() {
/* 335 */       if (this.entries == null) this.entries = ObjectSortedSets.synchronize(this.sortedMap.object2BooleanEntrySet(), this.sync); 
/* 336 */       return (ObjectSortedSet<Object2BooleanMap.Entry<K>>)this.entries;
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
/*     */     public ObjectSortedSet<Map.Entry<K, Boolean>> entrySet() {
/* 348 */       return (ObjectSortedSet)object2BooleanEntrySet();
/*     */     }
/*     */ 
/*     */     
/*     */     public ObjectSortedSet<K> keySet() {
/* 353 */       if (this.keys == null) this.keys = ObjectSortedSets.synchronize(this.sortedMap.keySet(), this.sync); 
/* 354 */       return (ObjectSortedSet<K>)this.keys;
/*     */     }
/*     */ 
/*     */     
/*     */     public Object2BooleanSortedMap<K> subMap(K from, K to) {
/* 359 */       return new SynchronizedSortedMap(this.sortedMap.subMap(from, to), this.sync);
/*     */     }
/*     */ 
/*     */     
/*     */     public Object2BooleanSortedMap<K> headMap(K to) {
/* 364 */       return new SynchronizedSortedMap(this.sortedMap.headMap(to), this.sync);
/*     */     }
/*     */ 
/*     */     
/*     */     public Object2BooleanSortedMap<K> tailMap(K from) {
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
/*     */   public static <K> Object2BooleanSortedMap<K> synchronize(Object2BooleanSortedMap<K> m) {
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
/*     */   public static <K> Object2BooleanSortedMap<K> synchronize(Object2BooleanSortedMap<K> m, Object sync) {
/* 408 */     return new SynchronizedSortedMap<>(m, sync);
/*     */   }
/*     */   
/*     */   public static class UnmodifiableSortedMap<K>
/*     */     extends Object2BooleanMaps.UnmodifiableMap<K> implements Object2BooleanSortedMap<K>, Serializable {
/*     */     private static final long serialVersionUID = -7046029254386353129L;
/*     */     protected final Object2BooleanSortedMap<K> sortedMap;
/*     */     
/*     */     protected UnmodifiableSortedMap(Object2BooleanSortedMap<K> m) {
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
/*     */     public ObjectSortedSet<Object2BooleanMap.Entry<K>> object2BooleanEntrySet() {
/* 429 */       if (this.entries == null) this.entries = ObjectSortedSets.unmodifiable(this.sortedMap.object2BooleanEntrySet()); 
/* 430 */       return (ObjectSortedSet<Object2BooleanMap.Entry<K>>)this.entries;
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
/*     */     public ObjectSortedSet<Map.Entry<K, Boolean>> entrySet() {
/* 442 */       return (ObjectSortedSet)object2BooleanEntrySet();
/*     */     }
/*     */ 
/*     */     
/*     */     public ObjectSortedSet<K> keySet() {
/* 447 */       if (this.keys == null) this.keys = ObjectSortedSets.unmodifiable(this.sortedMap.keySet()); 
/* 448 */       return (ObjectSortedSet<K>)this.keys;
/*     */     }
/*     */ 
/*     */     
/*     */     public Object2BooleanSortedMap<K> subMap(K from, K to) {
/* 453 */       return new UnmodifiableSortedMap(this.sortedMap.subMap(from, to));
/*     */     }
/*     */ 
/*     */     
/*     */     public Object2BooleanSortedMap<K> headMap(K to) {
/* 458 */       return new UnmodifiableSortedMap(this.sortedMap.headMap(to));
/*     */     }
/*     */ 
/*     */     
/*     */     public Object2BooleanSortedMap<K> tailMap(K from) {
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
/*     */   public static <K> Object2BooleanSortedMap<K> unmodifiable(Object2BooleanSortedMap<K> m) {
/* 485 */     return new UnmodifiableSortedMap<>(m);
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\i\\unimi\dsi\fastutil\objects\Object2BooleanSortedMaps.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */