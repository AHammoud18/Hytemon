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
/*     */ public final class Double2LongSortedMaps
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
/*     */   public static ObjectBidirectionalIterator<Double2LongMap.Entry> fastIterator(Double2LongSortedMap map) {
/*  60 */     ObjectSortedSet<Double2LongMap.Entry> entries = map.double2LongEntrySet();
/*  61 */     return (entries instanceof Double2LongSortedMap.FastSortedEntrySet) ? ((Double2LongSortedMap.FastSortedEntrySet)entries).fastIterator() : entries.iterator();
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
/*     */   public static ObjectBidirectionalIterable<Double2LongMap.Entry> fastIterable(Double2LongSortedMap map) {
/*  76 */     ObjectSortedSet<Double2LongMap.Entry> entries = map.double2LongEntrySet();
/*  77 */     Objects.requireNonNull((Double2LongSortedMap.FastSortedEntrySet)entries); return (entries instanceof Double2LongSortedMap.FastSortedEntrySet) ? (Double2LongSortedMap.FastSortedEntrySet)entries::fastIterator : (ObjectBidirectionalIterable<Double2LongMap.Entry>)entries;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static class EmptySortedMap
/*     */     extends Double2LongMaps.EmptyMap
/*     */     implements Double2LongSortedMap, Serializable, Cloneable
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
/*     */     public ObjectSortedSet<Double2LongMap.Entry> double2LongEntrySet() {
/*  99 */       return (ObjectSortedSet<Double2LongMap.Entry>)ObjectSortedSets.EMPTY_SET;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     @Deprecated
/*     */     public ObjectSortedSet<Map.Entry<Double, Long>> entrySet() {
/* 110 */       return (ObjectSortedSet<Map.Entry<Double, Long>>)ObjectSortedSets.EMPTY_SET;
/*     */     }
/*     */ 
/*     */     
/*     */     public DoubleSortedSet keySet() {
/* 115 */       return DoubleSortedSets.EMPTY_SET;
/*     */     }
/*     */ 
/*     */     
/*     */     public Double2LongSortedMap subMap(double from, double to) {
/* 120 */       return Double2LongSortedMaps.EMPTY_MAP;
/*     */     }
/*     */ 
/*     */     
/*     */     public Double2LongSortedMap headMap(double to) {
/* 125 */       return Double2LongSortedMaps.EMPTY_MAP;
/*     */     }
/*     */ 
/*     */     
/*     */     public Double2LongSortedMap tailMap(double from) {
/* 130 */       return Double2LongSortedMaps.EMPTY_MAP;
/*     */     }
/*     */ 
/*     */     
/*     */     public double firstDoubleKey() {
/* 135 */       throw new NoSuchElementException();
/*     */     }
/*     */ 
/*     */     
/*     */     public double lastDoubleKey() {
/* 140 */       throw new NoSuchElementException();
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     @Deprecated
/*     */     public Double2LongSortedMap headMap(Double oto) {
/* 151 */       return headMap(oto.doubleValue());
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     @Deprecated
/*     */     public Double2LongSortedMap tailMap(Double ofrom) {
/* 162 */       return tailMap(ofrom.doubleValue());
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     @Deprecated
/*     */     public Double2LongSortedMap subMap(Double ofrom, Double oto) {
/* 173 */       return subMap(ofrom.doubleValue(), oto.doubleValue());
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
/* 184 */       return Double.valueOf(firstDoubleKey());
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
/* 195 */       return Double.valueOf(lastDoubleKey());
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 203 */   public static final EmptySortedMap EMPTY_MAP = new EmptySortedMap();
/*     */ 
/*     */   
/*     */   public static class Singleton
/*     */     extends Double2LongMaps.Singleton
/*     */     implements Double2LongSortedMap, Serializable, Cloneable
/*     */   {
/*     */     private static final long serialVersionUID = -7046029254386353129L;
/*     */     
/*     */     protected final DoubleComparator comparator;
/*     */ 
/*     */     
/*     */     protected Singleton(double key, long value, DoubleComparator comparator) {
/* 216 */       super(key, value);
/* 217 */       this.comparator = comparator;
/*     */     }
/*     */     
/*     */     protected Singleton(double key, long value) {
/* 221 */       this(key, value, (DoubleComparator)null);
/*     */     }
/*     */     
/*     */     final int compare(double k1, double k2) {
/* 225 */       return (this.comparator == null) ? Double.compare(k1, k2) : this.comparator.compare(k1, k2);
/*     */     }
/*     */ 
/*     */     
/*     */     public DoubleComparator comparator() {
/* 230 */       return this.comparator;
/*     */     }
/*     */ 
/*     */     
/*     */     public ObjectSortedSet<Double2LongMap.Entry> double2LongEntrySet() {
/* 235 */       if (this.entries == null) this.entries = (ObjectSet<Double2LongMap.Entry>)ObjectSortedSets.singleton(new AbstractDouble2LongMap.BasicEntry(this.key, this.value), Double2LongSortedMaps.entryComparator(this.comparator)); 
/* 236 */       return (ObjectSortedSet<Double2LongMap.Entry>)this.entries;
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
/*     */     public ObjectSortedSet<Map.Entry<Double, Long>> entrySet() {
/* 248 */       return (ObjectSortedSet)double2LongEntrySet();
/*     */     }
/*     */ 
/*     */     
/*     */     public DoubleSortedSet keySet() {
/* 253 */       if (this.keys == null) this.keys = DoubleSortedSets.singleton(this.key, this.comparator); 
/* 254 */       return (DoubleSortedSet)this.keys;
/*     */     }
/*     */ 
/*     */     
/*     */     public Double2LongSortedMap subMap(double from, double to) {
/* 259 */       if (compare(from, this.key) <= 0 && compare(this.key, to) < 0) return this; 
/* 260 */       return Double2LongSortedMaps.EMPTY_MAP;
/*     */     }
/*     */ 
/*     */     
/*     */     public Double2LongSortedMap headMap(double to) {
/* 265 */       if (compare(this.key, to) < 0) return this; 
/* 266 */       return Double2LongSortedMaps.EMPTY_MAP;
/*     */     }
/*     */ 
/*     */     
/*     */     public Double2LongSortedMap tailMap(double from) {
/* 271 */       if (compare(from, this.key) <= 0) return this; 
/* 272 */       return Double2LongSortedMaps.EMPTY_MAP;
/*     */     }
/*     */ 
/*     */     
/*     */     public double firstDoubleKey() {
/* 277 */       return this.key;
/*     */     }
/*     */ 
/*     */     
/*     */     public double lastDoubleKey() {
/* 282 */       return this.key;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     @Deprecated
/*     */     public Double2LongSortedMap headMap(Double oto) {
/* 293 */       return headMap(oto.doubleValue());
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     @Deprecated
/*     */     public Double2LongSortedMap tailMap(Double ofrom) {
/* 304 */       return tailMap(ofrom.doubleValue());
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     @Deprecated
/*     */     public Double2LongSortedMap subMap(Double ofrom, Double oto) {
/* 315 */       return subMap(ofrom.doubleValue(), oto.doubleValue());
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
/* 326 */       return Double.valueOf(firstDoubleKey());
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
/* 337 */       return Double.valueOf(lastDoubleKey());
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
/*     */   public static Double2LongSortedMap singleton(Double key, Long value) {
/* 353 */     return new Singleton(key.doubleValue(), value.longValue());
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
/*     */   public static Double2LongSortedMap singleton(Double key, Long value, DoubleComparator comparator) {
/* 369 */     return new Singleton(key.doubleValue(), value.longValue(), comparator);
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
/*     */   public static Double2LongSortedMap singleton(double key, long value) {
/* 384 */     return new Singleton(key, value);
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
/*     */   public static Double2LongSortedMap singleton(double key, long value, DoubleComparator comparator) {
/* 400 */     return new Singleton(key, value, comparator);
/*     */   }
/*     */   
/*     */   public static class SynchronizedSortedMap
/*     */     extends Double2LongMaps.SynchronizedMap implements Double2LongSortedMap, Serializable {
/*     */     private static final long serialVersionUID = -7046029254386353129L;
/*     */     protected final Double2LongSortedMap sortedMap;
/*     */     
/*     */     protected SynchronizedSortedMap(Double2LongSortedMap m, Object sync) {
/* 409 */       super(m, sync);
/* 410 */       this.sortedMap = m;
/*     */     }
/*     */     
/*     */     protected SynchronizedSortedMap(Double2LongSortedMap m) {
/* 414 */       super(m);
/* 415 */       this.sortedMap = m;
/*     */     }
/*     */ 
/*     */     
/*     */     public DoubleComparator comparator() {
/* 420 */       synchronized (this.sync) {
/* 421 */         return this.sortedMap.comparator();
/*     */       } 
/*     */     }
/*     */ 
/*     */     
/*     */     public ObjectSortedSet<Double2LongMap.Entry> double2LongEntrySet() {
/* 427 */       if (this.entries == null) this.entries = (ObjectSet<Double2LongMap.Entry>)ObjectSortedSets.synchronize(this.sortedMap.double2LongEntrySet(), this.sync); 
/* 428 */       return (ObjectSortedSet<Double2LongMap.Entry>)this.entries;
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
/*     */     public ObjectSortedSet<Map.Entry<Double, Long>> entrySet() {
/* 440 */       return (ObjectSortedSet)double2LongEntrySet();
/*     */     }
/*     */ 
/*     */     
/*     */     public DoubleSortedSet keySet() {
/* 445 */       if (this.keys == null) this.keys = DoubleSortedSets.synchronize(this.sortedMap.keySet(), this.sync); 
/* 446 */       return (DoubleSortedSet)this.keys;
/*     */     }
/*     */ 
/*     */     
/*     */     public Double2LongSortedMap subMap(double from, double to) {
/* 451 */       return new SynchronizedSortedMap(this.sortedMap.subMap(from, to), this.sync);
/*     */     }
/*     */ 
/*     */     
/*     */     public Double2LongSortedMap headMap(double to) {
/* 456 */       return new SynchronizedSortedMap(this.sortedMap.headMap(to), this.sync);
/*     */     }
/*     */ 
/*     */     
/*     */     public Double2LongSortedMap tailMap(double from) {
/* 461 */       return new SynchronizedSortedMap(this.sortedMap.tailMap(from), this.sync);
/*     */     }
/*     */ 
/*     */     
/*     */     public double firstDoubleKey() {
/* 466 */       synchronized (this.sync) {
/* 467 */         return this.sortedMap.firstDoubleKey();
/*     */       } 
/*     */     }
/*     */ 
/*     */     
/*     */     public double lastDoubleKey() {
/* 473 */       synchronized (this.sync) {
/* 474 */         return this.sortedMap.lastDoubleKey();
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
/* 486 */       synchronized (this.sync) {
/* 487 */         return this.sortedMap.firstKey();
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
/* 499 */       synchronized (this.sync) {
/* 500 */         return this.sortedMap.lastKey();
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
/*     */     public Double2LongSortedMap subMap(Double from, Double to) {
/* 512 */       return new SynchronizedSortedMap(this.sortedMap.subMap(from, to), this.sync);
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     @Deprecated
/*     */     public Double2LongSortedMap headMap(Double to) {
/* 523 */       return new SynchronizedSortedMap(this.sortedMap.headMap(to), this.sync);
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     @Deprecated
/*     */     public Double2LongSortedMap tailMap(Double from) {
/* 534 */       return new SynchronizedSortedMap(this.sortedMap.tailMap(from), this.sync);
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
/*     */   public static Double2LongSortedMap synchronize(Double2LongSortedMap m) {
/* 546 */     return new SynchronizedSortedMap(m);
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
/*     */   public static Double2LongSortedMap synchronize(Double2LongSortedMap m, Object sync) {
/* 559 */     return new SynchronizedSortedMap(m, sync);
/*     */   }
/*     */   
/*     */   public static class UnmodifiableSortedMap
/*     */     extends Double2LongMaps.UnmodifiableMap implements Double2LongSortedMap, Serializable {
/*     */     private static final long serialVersionUID = -7046029254386353129L;
/*     */     protected final Double2LongSortedMap sortedMap;
/*     */     
/*     */     protected UnmodifiableSortedMap(Double2LongSortedMap m) {
/* 568 */       super(m);
/* 569 */       this.sortedMap = m;
/*     */     }
/*     */ 
/*     */     
/*     */     public DoubleComparator comparator() {
/* 574 */       return this.sortedMap.comparator();
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     public ObjectSortedSet<Double2LongMap.Entry> double2LongEntrySet() {
/* 580 */       if (this.entries == null) this.entries = (ObjectSet<Double2LongMap.Entry>)ObjectSortedSets.unmodifiable(this.sortedMap.double2LongEntrySet()); 
/* 581 */       return (ObjectSortedSet<Double2LongMap.Entry>)this.entries;
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
/*     */     public ObjectSortedSet<Map.Entry<Double, Long>> entrySet() {
/* 593 */       return (ObjectSortedSet)double2LongEntrySet();
/*     */     }
/*     */ 
/*     */     
/*     */     public DoubleSortedSet keySet() {
/* 598 */       if (this.keys == null) this.keys = DoubleSortedSets.unmodifiable(this.sortedMap.keySet()); 
/* 599 */       return (DoubleSortedSet)this.keys;
/*     */     }
/*     */ 
/*     */     
/*     */     public Double2LongSortedMap subMap(double from, double to) {
/* 604 */       return new UnmodifiableSortedMap(this.sortedMap.subMap(from, to));
/*     */     }
/*     */ 
/*     */     
/*     */     public Double2LongSortedMap headMap(double to) {
/* 609 */       return new UnmodifiableSortedMap(this.sortedMap.headMap(to));
/*     */     }
/*     */ 
/*     */     
/*     */     public Double2LongSortedMap tailMap(double from) {
/* 614 */       return new UnmodifiableSortedMap(this.sortedMap.tailMap(from));
/*     */     }
/*     */ 
/*     */     
/*     */     public double firstDoubleKey() {
/* 619 */       return this.sortedMap.firstDoubleKey();
/*     */     }
/*     */ 
/*     */     
/*     */     public double lastDoubleKey() {
/* 624 */       return this.sortedMap.lastDoubleKey();
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
/* 635 */       return this.sortedMap.firstKey();
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
/* 646 */       return this.sortedMap.lastKey();
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     @Deprecated
/*     */     public Double2LongSortedMap subMap(Double from, Double to) {
/* 657 */       return new UnmodifiableSortedMap(this.sortedMap.subMap(from, to));
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     @Deprecated
/*     */     public Double2LongSortedMap headMap(Double to) {
/* 668 */       return new UnmodifiableSortedMap(this.sortedMap.headMap(to));
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     @Deprecated
/*     */     public Double2LongSortedMap tailMap(Double from) {
/* 679 */       return new UnmodifiableSortedMap(this.sortedMap.tailMap(from));
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
/*     */   public static Double2LongSortedMap unmodifiable(Double2LongSortedMap m) {
/* 691 */     return new UnmodifiableSortedMap(m);
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\i\\unimi\dsi\fastutil\doubles\Double2LongSortedMaps.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */