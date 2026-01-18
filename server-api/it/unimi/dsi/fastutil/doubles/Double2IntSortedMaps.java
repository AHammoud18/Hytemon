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
/*     */ public final class Double2IntSortedMaps
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
/*     */   public static ObjectBidirectionalIterator<Double2IntMap.Entry> fastIterator(Double2IntSortedMap map) {
/*  60 */     ObjectSortedSet<Double2IntMap.Entry> entries = map.double2IntEntrySet();
/*  61 */     return (entries instanceof Double2IntSortedMap.FastSortedEntrySet) ? ((Double2IntSortedMap.FastSortedEntrySet)entries).fastIterator() : entries.iterator();
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
/*     */   public static ObjectBidirectionalIterable<Double2IntMap.Entry> fastIterable(Double2IntSortedMap map) {
/*  76 */     ObjectSortedSet<Double2IntMap.Entry> entries = map.double2IntEntrySet();
/*  77 */     Objects.requireNonNull((Double2IntSortedMap.FastSortedEntrySet)entries); return (entries instanceof Double2IntSortedMap.FastSortedEntrySet) ? (Double2IntSortedMap.FastSortedEntrySet)entries::fastIterator : (ObjectBidirectionalIterable<Double2IntMap.Entry>)entries;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static class EmptySortedMap
/*     */     extends Double2IntMaps.EmptyMap
/*     */     implements Double2IntSortedMap, Serializable, Cloneable
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
/*     */     public ObjectSortedSet<Double2IntMap.Entry> double2IntEntrySet() {
/*  99 */       return (ObjectSortedSet<Double2IntMap.Entry>)ObjectSortedSets.EMPTY_SET;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     @Deprecated
/*     */     public ObjectSortedSet<Map.Entry<Double, Integer>> entrySet() {
/* 110 */       return (ObjectSortedSet<Map.Entry<Double, Integer>>)ObjectSortedSets.EMPTY_SET;
/*     */     }
/*     */ 
/*     */     
/*     */     public DoubleSortedSet keySet() {
/* 115 */       return DoubleSortedSets.EMPTY_SET;
/*     */     }
/*     */ 
/*     */     
/*     */     public Double2IntSortedMap subMap(double from, double to) {
/* 120 */       return Double2IntSortedMaps.EMPTY_MAP;
/*     */     }
/*     */ 
/*     */     
/*     */     public Double2IntSortedMap headMap(double to) {
/* 125 */       return Double2IntSortedMaps.EMPTY_MAP;
/*     */     }
/*     */ 
/*     */     
/*     */     public Double2IntSortedMap tailMap(double from) {
/* 130 */       return Double2IntSortedMaps.EMPTY_MAP;
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
/*     */     public Double2IntSortedMap headMap(Double oto) {
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
/*     */     public Double2IntSortedMap tailMap(Double ofrom) {
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
/*     */     public Double2IntSortedMap subMap(Double ofrom, Double oto) {
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
/*     */     extends Double2IntMaps.Singleton
/*     */     implements Double2IntSortedMap, Serializable, Cloneable
/*     */   {
/*     */     private static final long serialVersionUID = -7046029254386353129L;
/*     */     
/*     */     protected final DoubleComparator comparator;
/*     */ 
/*     */     
/*     */     protected Singleton(double key, int value, DoubleComparator comparator) {
/* 216 */       super(key, value);
/* 217 */       this.comparator = comparator;
/*     */     }
/*     */     
/*     */     protected Singleton(double key, int value) {
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
/*     */     public ObjectSortedSet<Double2IntMap.Entry> double2IntEntrySet() {
/* 235 */       if (this.entries == null) this.entries = (ObjectSet<Double2IntMap.Entry>)ObjectSortedSets.singleton(new AbstractDouble2IntMap.BasicEntry(this.key, this.value), Double2IntSortedMaps.entryComparator(this.comparator)); 
/* 236 */       return (ObjectSortedSet<Double2IntMap.Entry>)this.entries;
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
/*     */     public ObjectSortedSet<Map.Entry<Double, Integer>> entrySet() {
/* 248 */       return (ObjectSortedSet)double2IntEntrySet();
/*     */     }
/*     */ 
/*     */     
/*     */     public DoubleSortedSet keySet() {
/* 253 */       if (this.keys == null) this.keys = DoubleSortedSets.singleton(this.key, this.comparator); 
/* 254 */       return (DoubleSortedSet)this.keys;
/*     */     }
/*     */ 
/*     */     
/*     */     public Double2IntSortedMap subMap(double from, double to) {
/* 259 */       if (compare(from, this.key) <= 0 && compare(this.key, to) < 0) return this; 
/* 260 */       return Double2IntSortedMaps.EMPTY_MAP;
/*     */     }
/*     */ 
/*     */     
/*     */     public Double2IntSortedMap headMap(double to) {
/* 265 */       if (compare(this.key, to) < 0) return this; 
/* 266 */       return Double2IntSortedMaps.EMPTY_MAP;
/*     */     }
/*     */ 
/*     */     
/*     */     public Double2IntSortedMap tailMap(double from) {
/* 271 */       if (compare(from, this.key) <= 0) return this; 
/* 272 */       return Double2IntSortedMaps.EMPTY_MAP;
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
/*     */     public Double2IntSortedMap headMap(Double oto) {
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
/*     */     public Double2IntSortedMap tailMap(Double ofrom) {
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
/*     */     public Double2IntSortedMap subMap(Double ofrom, Double oto) {
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
/*     */   public static Double2IntSortedMap singleton(Double key, Integer value) {
/* 353 */     return new Singleton(key.doubleValue(), value.intValue());
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
/*     */   public static Double2IntSortedMap singleton(Double key, Integer value, DoubleComparator comparator) {
/* 369 */     return new Singleton(key.doubleValue(), value.intValue(), comparator);
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
/*     */   public static Double2IntSortedMap singleton(double key, int value) {
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
/*     */   public static Double2IntSortedMap singleton(double key, int value, DoubleComparator comparator) {
/* 400 */     return new Singleton(key, value, comparator);
/*     */   }
/*     */   
/*     */   public static class SynchronizedSortedMap
/*     */     extends Double2IntMaps.SynchronizedMap implements Double2IntSortedMap, Serializable {
/*     */     private static final long serialVersionUID = -7046029254386353129L;
/*     */     protected final Double2IntSortedMap sortedMap;
/*     */     
/*     */     protected SynchronizedSortedMap(Double2IntSortedMap m, Object sync) {
/* 409 */       super(m, sync);
/* 410 */       this.sortedMap = m;
/*     */     }
/*     */     
/*     */     protected SynchronizedSortedMap(Double2IntSortedMap m) {
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
/*     */     public ObjectSortedSet<Double2IntMap.Entry> double2IntEntrySet() {
/* 427 */       if (this.entries == null) this.entries = (ObjectSet<Double2IntMap.Entry>)ObjectSortedSets.synchronize(this.sortedMap.double2IntEntrySet(), this.sync); 
/* 428 */       return (ObjectSortedSet<Double2IntMap.Entry>)this.entries;
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
/*     */     public ObjectSortedSet<Map.Entry<Double, Integer>> entrySet() {
/* 440 */       return (ObjectSortedSet)double2IntEntrySet();
/*     */     }
/*     */ 
/*     */     
/*     */     public DoubleSortedSet keySet() {
/* 445 */       if (this.keys == null) this.keys = DoubleSortedSets.synchronize(this.sortedMap.keySet(), this.sync); 
/* 446 */       return (DoubleSortedSet)this.keys;
/*     */     }
/*     */ 
/*     */     
/*     */     public Double2IntSortedMap subMap(double from, double to) {
/* 451 */       return new SynchronizedSortedMap(this.sortedMap.subMap(from, to), this.sync);
/*     */     }
/*     */ 
/*     */     
/*     */     public Double2IntSortedMap headMap(double to) {
/* 456 */       return new SynchronizedSortedMap(this.sortedMap.headMap(to), this.sync);
/*     */     }
/*     */ 
/*     */     
/*     */     public Double2IntSortedMap tailMap(double from) {
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
/*     */     public Double2IntSortedMap subMap(Double from, Double to) {
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
/*     */     public Double2IntSortedMap headMap(Double to) {
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
/*     */     public Double2IntSortedMap tailMap(Double from) {
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
/*     */   public static Double2IntSortedMap synchronize(Double2IntSortedMap m) {
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
/*     */   public static Double2IntSortedMap synchronize(Double2IntSortedMap m, Object sync) {
/* 559 */     return new SynchronizedSortedMap(m, sync);
/*     */   }
/*     */   
/*     */   public static class UnmodifiableSortedMap
/*     */     extends Double2IntMaps.UnmodifiableMap implements Double2IntSortedMap, Serializable {
/*     */     private static final long serialVersionUID = -7046029254386353129L;
/*     */     protected final Double2IntSortedMap sortedMap;
/*     */     
/*     */     protected UnmodifiableSortedMap(Double2IntSortedMap m) {
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
/*     */     public ObjectSortedSet<Double2IntMap.Entry> double2IntEntrySet() {
/* 580 */       if (this.entries == null) this.entries = (ObjectSet<Double2IntMap.Entry>)ObjectSortedSets.unmodifiable(this.sortedMap.double2IntEntrySet()); 
/* 581 */       return (ObjectSortedSet<Double2IntMap.Entry>)this.entries;
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
/*     */     public ObjectSortedSet<Map.Entry<Double, Integer>> entrySet() {
/* 593 */       return (ObjectSortedSet)double2IntEntrySet();
/*     */     }
/*     */ 
/*     */     
/*     */     public DoubleSortedSet keySet() {
/* 598 */       if (this.keys == null) this.keys = DoubleSortedSets.unmodifiable(this.sortedMap.keySet()); 
/* 599 */       return (DoubleSortedSet)this.keys;
/*     */     }
/*     */ 
/*     */     
/*     */     public Double2IntSortedMap subMap(double from, double to) {
/* 604 */       return new UnmodifiableSortedMap(this.sortedMap.subMap(from, to));
/*     */     }
/*     */ 
/*     */     
/*     */     public Double2IntSortedMap headMap(double to) {
/* 609 */       return new UnmodifiableSortedMap(this.sortedMap.headMap(to));
/*     */     }
/*     */ 
/*     */     
/*     */     public Double2IntSortedMap tailMap(double from) {
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
/*     */     public Double2IntSortedMap subMap(Double from, Double to) {
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
/*     */     public Double2IntSortedMap headMap(Double to) {
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
/*     */     public Double2IntSortedMap tailMap(Double from) {
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
/*     */   public static Double2IntSortedMap unmodifiable(Double2IntSortedMap m) {
/* 691 */     return new UnmodifiableSortedMap(m);
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\i\\unimi\dsi\fastutil\doubles\Double2IntSortedMaps.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */