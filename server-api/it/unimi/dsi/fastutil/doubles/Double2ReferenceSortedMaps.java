/*     */ package it.unimi.dsi.fastutil.doubles;
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
/*     */ public final class Double2ReferenceSortedMaps
/*     */ {
/*     */   public static Comparator<? super Map.Entry<Double, ?>> entryComparator(DoubleComparator comparator) {
/*  45 */     return (x, y) -> comparator.compare(((Double)x.getKey()).doubleValue(), ((Double)y.getKey()).doubleValue());
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
/*     */   public static <V> ObjectBidirectionalIterator<Double2ReferenceMap.Entry<V>> fastIterator(Double2ReferenceSortedMap<V> map) {
/*  60 */     ObjectSortedSet<Double2ReferenceMap.Entry<V>> entries = map.double2ReferenceEntrySet();
/*  61 */     return (entries instanceof Double2ReferenceSortedMap.FastSortedEntrySet) ? ((Double2ReferenceSortedMap.FastSortedEntrySet)entries).fastIterator() : entries.iterator();
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
/*     */   public static <V> ObjectBidirectionalIterable<Double2ReferenceMap.Entry<V>> fastIterable(Double2ReferenceSortedMap<V> map) {
/*  76 */     ObjectSortedSet<Double2ReferenceMap.Entry<V>> entries = map.double2ReferenceEntrySet();
/*  77 */     Objects.requireNonNull((Double2ReferenceSortedMap.FastSortedEntrySet)entries); return (entries instanceof Double2ReferenceSortedMap.FastSortedEntrySet) ? (Double2ReferenceSortedMap.FastSortedEntrySet)entries::fastIterator : (ObjectBidirectionalIterable<Double2ReferenceMap.Entry<V>>)entries;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static class EmptySortedMap<V>
/*     */     extends Double2ReferenceMaps.EmptyMap<V>
/*     */     implements Double2ReferenceSortedMap<V>, Serializable, Cloneable
/*     */   {
/*     */     private static final long serialVersionUID = -7046029254386353129L;
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public DoubleComparator comparator() {
/*  94 */       return null;
/*     */     }
/*     */ 
/*     */     
/*     */     public ObjectSortedSet<Double2ReferenceMap.Entry<V>> double2ReferenceEntrySet() {
/*  99 */       return (ObjectSortedSet<Double2ReferenceMap.Entry<V>>)ObjectSortedSets.EMPTY_SET;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     @Deprecated
/*     */     public ObjectSortedSet<Map.Entry<Double, V>> entrySet() {
/* 110 */       return (ObjectSortedSet<Map.Entry<Double, V>>)ObjectSortedSets.EMPTY_SET;
/*     */     }
/*     */ 
/*     */     
/*     */     public DoubleSortedSet keySet() {
/* 115 */       return DoubleSortedSets.EMPTY_SET;
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     public Double2ReferenceSortedMap<V> subMap(double from, double to) {
/* 121 */       return Double2ReferenceSortedMaps.EMPTY_MAP;
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     public Double2ReferenceSortedMap<V> headMap(double to) {
/* 127 */       return Double2ReferenceSortedMaps.EMPTY_MAP;
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     public Double2ReferenceSortedMap<V> tailMap(double from) {
/* 133 */       return Double2ReferenceSortedMaps.EMPTY_MAP;
/*     */     }
/*     */ 
/*     */     
/*     */     public double firstDoubleKey() {
/* 138 */       throw new NoSuchElementException();
/*     */     }
/*     */ 
/*     */     
/*     */     public double lastDoubleKey() {
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
/*     */     public Double2ReferenceSortedMap<V> headMap(Double oto) {
/* 154 */       return headMap(oto.doubleValue());
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     @Deprecated
/*     */     public Double2ReferenceSortedMap<V> tailMap(Double ofrom) {
/* 165 */       return tailMap(ofrom.doubleValue());
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     @Deprecated
/*     */     public Double2ReferenceSortedMap<V> subMap(Double ofrom, Double oto) {
/* 176 */       return subMap(ofrom.doubleValue(), oto.doubleValue());
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     @Deprecated
/*     */     public Double firstKey() {
/* 187 */       return Double.valueOf(firstDoubleKey());
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     @Deprecated
/*     */     public Double lastKey() {
/* 198 */       return Double.valueOf(lastDoubleKey());
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
/*     */   public static <V> Double2ReferenceSortedMap<V> emptyMap() {
/* 218 */     return EMPTY_MAP;
/*     */   }
/*     */ 
/*     */   
/*     */   public static class Singleton<V>
/*     */     extends Double2ReferenceMaps.Singleton<V>
/*     */     implements Double2ReferenceSortedMap<V>, Serializable, Cloneable
/*     */   {
/*     */     private static final long serialVersionUID = -7046029254386353129L;
/*     */     
/*     */     protected final DoubleComparator comparator;
/*     */ 
/*     */     
/*     */     protected Singleton(double key, V value, DoubleComparator comparator) {
/* 232 */       super(key, value);
/* 233 */       this.comparator = comparator;
/*     */     }
/*     */     
/*     */     protected Singleton(double key, V value) {
/* 237 */       this(key, value, (DoubleComparator)null);
/*     */     }
/*     */     
/*     */     final int compare(double k1, double k2) {
/* 241 */       return (this.comparator == null) ? Double.compare(k1, k2) : this.comparator.compare(k1, k2);
/*     */     }
/*     */ 
/*     */     
/*     */     public DoubleComparator comparator() {
/* 246 */       return this.comparator;
/*     */     }
/*     */ 
/*     */     
/*     */     public ObjectSortedSet<Double2ReferenceMap.Entry<V>> double2ReferenceEntrySet() {
/* 251 */       if (this.entries == null) this.entries = (ObjectSet<Double2ReferenceMap.Entry<V>>)ObjectSortedSets.singleton(new AbstractDouble2ReferenceMap.BasicEntry<>(this.key, this.value), Double2ReferenceSortedMaps.entryComparator(this.comparator)); 
/* 252 */       return (ObjectSortedSet<Double2ReferenceMap.Entry<V>>)this.entries;
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
/*     */     public ObjectSortedSet<Map.Entry<Double, V>> entrySet() {
/* 264 */       return (ObjectSortedSet)double2ReferenceEntrySet();
/*     */     }
/*     */ 
/*     */     
/*     */     public DoubleSortedSet keySet() {
/* 269 */       if (this.keys == null) this.keys = DoubleSortedSets.singleton(this.key, this.comparator); 
/* 270 */       return (DoubleSortedSet)this.keys;
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     public Double2ReferenceSortedMap<V> subMap(double from, double to) {
/* 276 */       if (compare(from, this.key) <= 0 && compare(this.key, to) < 0) return this; 
/* 277 */       return Double2ReferenceSortedMaps.EMPTY_MAP;
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     public Double2ReferenceSortedMap<V> headMap(double to) {
/* 283 */       if (compare(this.key, to) < 0) return this; 
/* 284 */       return Double2ReferenceSortedMaps.EMPTY_MAP;
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     public Double2ReferenceSortedMap<V> tailMap(double from) {
/* 290 */       if (compare(from, this.key) <= 0) return this; 
/* 291 */       return Double2ReferenceSortedMaps.EMPTY_MAP;
/*     */     }
/*     */ 
/*     */     
/*     */     public double firstDoubleKey() {
/* 296 */       return this.key;
/*     */     }
/*     */ 
/*     */     
/*     */     public double lastDoubleKey() {
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
/*     */     public Double2ReferenceSortedMap<V> headMap(Double oto) {
/* 312 */       return headMap(oto.doubleValue());
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     @Deprecated
/*     */     public Double2ReferenceSortedMap<V> tailMap(Double ofrom) {
/* 323 */       return tailMap(ofrom.doubleValue());
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     @Deprecated
/*     */     public Double2ReferenceSortedMap<V> subMap(Double ofrom, Double oto) {
/* 334 */       return subMap(ofrom.doubleValue(), oto.doubleValue());
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     @Deprecated
/*     */     public Double firstKey() {
/* 345 */       return Double.valueOf(firstDoubleKey());
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     @Deprecated
/*     */     public Double lastKey() {
/* 356 */       return Double.valueOf(lastDoubleKey());
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
/*     */   public static <V> Double2ReferenceSortedMap<V> singleton(Double key, V value) {
/* 372 */     return new Singleton<>(key.doubleValue(), value);
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
/*     */   public static <V> Double2ReferenceSortedMap<V> singleton(Double key, V value, DoubleComparator comparator) {
/* 388 */     return new Singleton<>(key.doubleValue(), value, comparator);
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
/*     */   public static <V> Double2ReferenceSortedMap<V> singleton(double key, V value) {
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
/*     */   public static <V> Double2ReferenceSortedMap<V> singleton(double key, V value, DoubleComparator comparator) {
/* 419 */     return new Singleton<>(key, value, comparator);
/*     */   }
/*     */   
/*     */   public static class SynchronizedSortedMap<V>
/*     */     extends Double2ReferenceMaps.SynchronizedMap<V> implements Double2ReferenceSortedMap<V>, Serializable {
/*     */     private static final long serialVersionUID = -7046029254386353129L;
/*     */     protected final Double2ReferenceSortedMap<V> sortedMap;
/*     */     
/*     */     protected SynchronizedSortedMap(Double2ReferenceSortedMap<V> m, Object sync) {
/* 428 */       super(m, sync);
/* 429 */       this.sortedMap = m;
/*     */     }
/*     */     
/*     */     protected SynchronizedSortedMap(Double2ReferenceSortedMap<V> m) {
/* 433 */       super(m);
/* 434 */       this.sortedMap = m;
/*     */     }
/*     */ 
/*     */     
/*     */     public DoubleComparator comparator() {
/* 439 */       synchronized (this.sync) {
/* 440 */         return this.sortedMap.comparator();
/*     */       } 
/*     */     }
/*     */ 
/*     */     
/*     */     public ObjectSortedSet<Double2ReferenceMap.Entry<V>> double2ReferenceEntrySet() {
/* 446 */       if (this.entries == null) this.entries = (ObjectSet<Double2ReferenceMap.Entry<V>>)ObjectSortedSets.synchronize(this.sortedMap.double2ReferenceEntrySet(), this.sync); 
/* 447 */       return (ObjectSortedSet<Double2ReferenceMap.Entry<V>>)this.entries;
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
/*     */     public ObjectSortedSet<Map.Entry<Double, V>> entrySet() {
/* 459 */       return (ObjectSortedSet)double2ReferenceEntrySet();
/*     */     }
/*     */ 
/*     */     
/*     */     public DoubleSortedSet keySet() {
/* 464 */       if (this.keys == null) this.keys = DoubleSortedSets.synchronize(this.sortedMap.keySet(), this.sync); 
/* 465 */       return (DoubleSortedSet)this.keys;
/*     */     }
/*     */ 
/*     */     
/*     */     public Double2ReferenceSortedMap<V> subMap(double from, double to) {
/* 470 */       return new SynchronizedSortedMap(this.sortedMap.subMap(from, to), this.sync);
/*     */     }
/*     */ 
/*     */     
/*     */     public Double2ReferenceSortedMap<V> headMap(double to) {
/* 475 */       return new SynchronizedSortedMap(this.sortedMap.headMap(to), this.sync);
/*     */     }
/*     */ 
/*     */     
/*     */     public Double2ReferenceSortedMap<V> tailMap(double from) {
/* 480 */       return new SynchronizedSortedMap(this.sortedMap.tailMap(from), this.sync);
/*     */     }
/*     */ 
/*     */     
/*     */     public double firstDoubleKey() {
/* 485 */       synchronized (this.sync) {
/* 486 */         return this.sortedMap.firstDoubleKey();
/*     */       } 
/*     */     }
/*     */ 
/*     */     
/*     */     public double lastDoubleKey() {
/* 492 */       synchronized (this.sync) {
/* 493 */         return this.sortedMap.lastDoubleKey();
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
/*     */     public Double firstKey() {
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
/*     */     public Double lastKey() {
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
/*     */     public Double2ReferenceSortedMap<V> subMap(Double from, Double to) {
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
/*     */     public Double2ReferenceSortedMap<V> headMap(Double to) {
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
/*     */     public Double2ReferenceSortedMap<V> tailMap(Double from) {
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
/*     */   public static <V> Double2ReferenceSortedMap<V> synchronize(Double2ReferenceSortedMap<V> m) {
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
/*     */   public static <V> Double2ReferenceSortedMap<V> synchronize(Double2ReferenceSortedMap<V> m, Object sync) {
/* 578 */     return new SynchronizedSortedMap<>(m, sync);
/*     */   }
/*     */   
/*     */   public static class UnmodifiableSortedMap<V>
/*     */     extends Double2ReferenceMaps.UnmodifiableMap<V> implements Double2ReferenceSortedMap<V>, Serializable {
/*     */     private static final long serialVersionUID = -7046029254386353129L;
/*     */     protected final Double2ReferenceSortedMap<? extends V> sortedMap;
/*     */     
/*     */     protected UnmodifiableSortedMap(Double2ReferenceSortedMap<? extends V> m) {
/* 587 */       super(m);
/* 588 */       this.sortedMap = m;
/*     */     }
/*     */ 
/*     */     
/*     */     public DoubleComparator comparator() {
/* 593 */       return this.sortedMap.comparator();
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     public ObjectSortedSet<Double2ReferenceMap.Entry<V>> double2ReferenceEntrySet() {
/* 599 */       if (this.entries == null) this.entries = (ObjectSet<Double2ReferenceMap.Entry<V>>)ObjectSortedSets.unmodifiable(this.sortedMap.double2ReferenceEntrySet()); 
/* 600 */       return (ObjectSortedSet<Double2ReferenceMap.Entry<V>>)this.entries;
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
/*     */     public ObjectSortedSet<Map.Entry<Double, V>> entrySet() {
/* 612 */       return (ObjectSortedSet)double2ReferenceEntrySet();
/*     */     }
/*     */ 
/*     */     
/*     */     public DoubleSortedSet keySet() {
/* 617 */       if (this.keys == null) this.keys = DoubleSortedSets.unmodifiable(this.sortedMap.keySet()); 
/* 618 */       return (DoubleSortedSet)this.keys;
/*     */     }
/*     */ 
/*     */     
/*     */     public Double2ReferenceSortedMap<V> subMap(double from, double to) {
/* 623 */       return new UnmodifiableSortedMap(this.sortedMap.subMap(from, to));
/*     */     }
/*     */ 
/*     */     
/*     */     public Double2ReferenceSortedMap<V> headMap(double to) {
/* 628 */       return new UnmodifiableSortedMap(this.sortedMap.headMap(to));
/*     */     }
/*     */ 
/*     */     
/*     */     public Double2ReferenceSortedMap<V> tailMap(double from) {
/* 633 */       return new UnmodifiableSortedMap(this.sortedMap.tailMap(from));
/*     */     }
/*     */ 
/*     */     
/*     */     public double firstDoubleKey() {
/* 638 */       return this.sortedMap.firstDoubleKey();
/*     */     }
/*     */ 
/*     */     
/*     */     public double lastDoubleKey() {
/* 643 */       return this.sortedMap.lastDoubleKey();
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     @Deprecated
/*     */     public Double firstKey() {
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
/*     */     public Double lastKey() {
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
/*     */     public Double2ReferenceSortedMap<V> subMap(Double from, Double to) {
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
/*     */     public Double2ReferenceSortedMap<V> headMap(Double to) {
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
/*     */     public Double2ReferenceSortedMap<V> tailMap(Double from) {
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
/*     */   public static <V> Double2ReferenceSortedMap<V> unmodifiable(Double2ReferenceSortedMap<? extends V> m) {
/* 710 */     return new UnmodifiableSortedMap<>(m);
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\i\\unimi\dsi\fastutil\doubles\Double2ReferenceSortedMaps.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */