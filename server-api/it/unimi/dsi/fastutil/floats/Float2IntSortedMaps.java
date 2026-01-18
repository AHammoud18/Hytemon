/*     */ package it.unimi.dsi.fastutil.floats;
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
/*     */ public final class Float2IntSortedMaps
/*     */ {
/*     */   public static Comparator<? super Map.Entry<Float, ?>> entryComparator(FloatComparator comparator) {
/*  45 */     return (x, y) -> comparator.compare(((Float)x.getKey()).floatValue(), ((Float)y.getKey()).floatValue());
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
/*     */   public static ObjectBidirectionalIterator<Float2IntMap.Entry> fastIterator(Float2IntSortedMap map) {
/*  60 */     ObjectSortedSet<Float2IntMap.Entry> entries = map.float2IntEntrySet();
/*  61 */     return (entries instanceof Float2IntSortedMap.FastSortedEntrySet) ? ((Float2IntSortedMap.FastSortedEntrySet)entries).fastIterator() : entries.iterator();
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
/*     */   public static ObjectBidirectionalIterable<Float2IntMap.Entry> fastIterable(Float2IntSortedMap map) {
/*  76 */     ObjectSortedSet<Float2IntMap.Entry> entries = map.float2IntEntrySet();
/*  77 */     Objects.requireNonNull((Float2IntSortedMap.FastSortedEntrySet)entries); return (entries instanceof Float2IntSortedMap.FastSortedEntrySet) ? (Float2IntSortedMap.FastSortedEntrySet)entries::fastIterator : (ObjectBidirectionalIterable<Float2IntMap.Entry>)entries;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static class EmptySortedMap
/*     */     extends Float2IntMaps.EmptyMap
/*     */     implements Float2IntSortedMap, Serializable, Cloneable
/*     */   {
/*     */     private static final long serialVersionUID = -7046029254386353129L;
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public FloatComparator comparator() {
/*  94 */       return null;
/*     */     }
/*     */ 
/*     */     
/*     */     public ObjectSortedSet<Float2IntMap.Entry> float2IntEntrySet() {
/*  99 */       return (ObjectSortedSet<Float2IntMap.Entry>)ObjectSortedSets.EMPTY_SET;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     @Deprecated
/*     */     public ObjectSortedSet<Map.Entry<Float, Integer>> entrySet() {
/* 110 */       return (ObjectSortedSet<Map.Entry<Float, Integer>>)ObjectSortedSets.EMPTY_SET;
/*     */     }
/*     */ 
/*     */     
/*     */     public FloatSortedSet keySet() {
/* 115 */       return FloatSortedSets.EMPTY_SET;
/*     */     }
/*     */ 
/*     */     
/*     */     public Float2IntSortedMap subMap(float from, float to) {
/* 120 */       return Float2IntSortedMaps.EMPTY_MAP;
/*     */     }
/*     */ 
/*     */     
/*     */     public Float2IntSortedMap headMap(float to) {
/* 125 */       return Float2IntSortedMaps.EMPTY_MAP;
/*     */     }
/*     */ 
/*     */     
/*     */     public Float2IntSortedMap tailMap(float from) {
/* 130 */       return Float2IntSortedMaps.EMPTY_MAP;
/*     */     }
/*     */ 
/*     */     
/*     */     public float firstFloatKey() {
/* 135 */       throw new NoSuchElementException();
/*     */     }
/*     */ 
/*     */     
/*     */     public float lastFloatKey() {
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
/*     */     public Float2IntSortedMap headMap(Float oto) {
/* 151 */       return headMap(oto.floatValue());
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     @Deprecated
/*     */     public Float2IntSortedMap tailMap(Float ofrom) {
/* 162 */       return tailMap(ofrom.floatValue());
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     @Deprecated
/*     */     public Float2IntSortedMap subMap(Float ofrom, Float oto) {
/* 173 */       return subMap(ofrom.floatValue(), oto.floatValue());
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     @Deprecated
/*     */     public Float firstKey() {
/* 184 */       return Float.valueOf(firstFloatKey());
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     @Deprecated
/*     */     public Float lastKey() {
/* 195 */       return Float.valueOf(lastFloatKey());
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
/*     */     extends Float2IntMaps.Singleton
/*     */     implements Float2IntSortedMap, Serializable, Cloneable
/*     */   {
/*     */     private static final long serialVersionUID = -7046029254386353129L;
/*     */     
/*     */     protected final FloatComparator comparator;
/*     */ 
/*     */     
/*     */     protected Singleton(float key, int value, FloatComparator comparator) {
/* 216 */       super(key, value);
/* 217 */       this.comparator = comparator;
/*     */     }
/*     */     
/*     */     protected Singleton(float key, int value) {
/* 221 */       this(key, value, (FloatComparator)null);
/*     */     }
/*     */     
/*     */     final int compare(float k1, float k2) {
/* 225 */       return (this.comparator == null) ? Float.compare(k1, k2) : this.comparator.compare(k1, k2);
/*     */     }
/*     */ 
/*     */     
/*     */     public FloatComparator comparator() {
/* 230 */       return this.comparator;
/*     */     }
/*     */ 
/*     */     
/*     */     public ObjectSortedSet<Float2IntMap.Entry> float2IntEntrySet() {
/* 235 */       if (this.entries == null) this.entries = (ObjectSet<Float2IntMap.Entry>)ObjectSortedSets.singleton(new AbstractFloat2IntMap.BasicEntry(this.key, this.value), Float2IntSortedMaps.entryComparator(this.comparator)); 
/* 236 */       return (ObjectSortedSet<Float2IntMap.Entry>)this.entries;
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
/*     */     public ObjectSortedSet<Map.Entry<Float, Integer>> entrySet() {
/* 248 */       return (ObjectSortedSet)float2IntEntrySet();
/*     */     }
/*     */ 
/*     */     
/*     */     public FloatSortedSet keySet() {
/* 253 */       if (this.keys == null) this.keys = FloatSortedSets.singleton(this.key, this.comparator); 
/* 254 */       return (FloatSortedSet)this.keys;
/*     */     }
/*     */ 
/*     */     
/*     */     public Float2IntSortedMap subMap(float from, float to) {
/* 259 */       if (compare(from, this.key) <= 0 && compare(this.key, to) < 0) return this; 
/* 260 */       return Float2IntSortedMaps.EMPTY_MAP;
/*     */     }
/*     */ 
/*     */     
/*     */     public Float2IntSortedMap headMap(float to) {
/* 265 */       if (compare(this.key, to) < 0) return this; 
/* 266 */       return Float2IntSortedMaps.EMPTY_MAP;
/*     */     }
/*     */ 
/*     */     
/*     */     public Float2IntSortedMap tailMap(float from) {
/* 271 */       if (compare(from, this.key) <= 0) return this; 
/* 272 */       return Float2IntSortedMaps.EMPTY_MAP;
/*     */     }
/*     */ 
/*     */     
/*     */     public float firstFloatKey() {
/* 277 */       return this.key;
/*     */     }
/*     */ 
/*     */     
/*     */     public float lastFloatKey() {
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
/*     */     public Float2IntSortedMap headMap(Float oto) {
/* 293 */       return headMap(oto.floatValue());
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     @Deprecated
/*     */     public Float2IntSortedMap tailMap(Float ofrom) {
/* 304 */       return tailMap(ofrom.floatValue());
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     @Deprecated
/*     */     public Float2IntSortedMap subMap(Float ofrom, Float oto) {
/* 315 */       return subMap(ofrom.floatValue(), oto.floatValue());
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     @Deprecated
/*     */     public Float firstKey() {
/* 326 */       return Float.valueOf(firstFloatKey());
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     @Deprecated
/*     */     public Float lastKey() {
/* 337 */       return Float.valueOf(lastFloatKey());
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
/*     */   public static Float2IntSortedMap singleton(Float key, Integer value) {
/* 353 */     return new Singleton(key.floatValue(), value.intValue());
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
/*     */   public static Float2IntSortedMap singleton(Float key, Integer value, FloatComparator comparator) {
/* 369 */     return new Singleton(key.floatValue(), value.intValue(), comparator);
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
/*     */   public static Float2IntSortedMap singleton(float key, int value) {
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
/*     */   public static Float2IntSortedMap singleton(float key, int value, FloatComparator comparator) {
/* 400 */     return new Singleton(key, value, comparator);
/*     */   }
/*     */   
/*     */   public static class SynchronizedSortedMap
/*     */     extends Float2IntMaps.SynchronizedMap implements Float2IntSortedMap, Serializable {
/*     */     private static final long serialVersionUID = -7046029254386353129L;
/*     */     protected final Float2IntSortedMap sortedMap;
/*     */     
/*     */     protected SynchronizedSortedMap(Float2IntSortedMap m, Object sync) {
/* 409 */       super(m, sync);
/* 410 */       this.sortedMap = m;
/*     */     }
/*     */     
/*     */     protected SynchronizedSortedMap(Float2IntSortedMap m) {
/* 414 */       super(m);
/* 415 */       this.sortedMap = m;
/*     */     }
/*     */ 
/*     */     
/*     */     public FloatComparator comparator() {
/* 420 */       synchronized (this.sync) {
/* 421 */         return this.sortedMap.comparator();
/*     */       } 
/*     */     }
/*     */ 
/*     */     
/*     */     public ObjectSortedSet<Float2IntMap.Entry> float2IntEntrySet() {
/* 427 */       if (this.entries == null) this.entries = (ObjectSet<Float2IntMap.Entry>)ObjectSortedSets.synchronize(this.sortedMap.float2IntEntrySet(), this.sync); 
/* 428 */       return (ObjectSortedSet<Float2IntMap.Entry>)this.entries;
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
/*     */     public ObjectSortedSet<Map.Entry<Float, Integer>> entrySet() {
/* 440 */       return (ObjectSortedSet)float2IntEntrySet();
/*     */     }
/*     */ 
/*     */     
/*     */     public FloatSortedSet keySet() {
/* 445 */       if (this.keys == null) this.keys = FloatSortedSets.synchronize(this.sortedMap.keySet(), this.sync); 
/* 446 */       return (FloatSortedSet)this.keys;
/*     */     }
/*     */ 
/*     */     
/*     */     public Float2IntSortedMap subMap(float from, float to) {
/* 451 */       return new SynchronizedSortedMap(this.sortedMap.subMap(from, to), this.sync);
/*     */     }
/*     */ 
/*     */     
/*     */     public Float2IntSortedMap headMap(float to) {
/* 456 */       return new SynchronizedSortedMap(this.sortedMap.headMap(to), this.sync);
/*     */     }
/*     */ 
/*     */     
/*     */     public Float2IntSortedMap tailMap(float from) {
/* 461 */       return new SynchronizedSortedMap(this.sortedMap.tailMap(from), this.sync);
/*     */     }
/*     */ 
/*     */     
/*     */     public float firstFloatKey() {
/* 466 */       synchronized (this.sync) {
/* 467 */         return this.sortedMap.firstFloatKey();
/*     */       } 
/*     */     }
/*     */ 
/*     */     
/*     */     public float lastFloatKey() {
/* 473 */       synchronized (this.sync) {
/* 474 */         return this.sortedMap.lastFloatKey();
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
/*     */     public Float firstKey() {
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
/*     */     public Float lastKey() {
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
/*     */     public Float2IntSortedMap subMap(Float from, Float to) {
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
/*     */     public Float2IntSortedMap headMap(Float to) {
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
/*     */     public Float2IntSortedMap tailMap(Float from) {
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
/*     */   public static Float2IntSortedMap synchronize(Float2IntSortedMap m) {
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
/*     */   public static Float2IntSortedMap synchronize(Float2IntSortedMap m, Object sync) {
/* 559 */     return new SynchronizedSortedMap(m, sync);
/*     */   }
/*     */   
/*     */   public static class UnmodifiableSortedMap
/*     */     extends Float2IntMaps.UnmodifiableMap implements Float2IntSortedMap, Serializable {
/*     */     private static final long serialVersionUID = -7046029254386353129L;
/*     */     protected final Float2IntSortedMap sortedMap;
/*     */     
/*     */     protected UnmodifiableSortedMap(Float2IntSortedMap m) {
/* 568 */       super(m);
/* 569 */       this.sortedMap = m;
/*     */     }
/*     */ 
/*     */     
/*     */     public FloatComparator comparator() {
/* 574 */       return this.sortedMap.comparator();
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     public ObjectSortedSet<Float2IntMap.Entry> float2IntEntrySet() {
/* 580 */       if (this.entries == null) this.entries = (ObjectSet<Float2IntMap.Entry>)ObjectSortedSets.unmodifiable(this.sortedMap.float2IntEntrySet()); 
/* 581 */       return (ObjectSortedSet<Float2IntMap.Entry>)this.entries;
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
/*     */     public ObjectSortedSet<Map.Entry<Float, Integer>> entrySet() {
/* 593 */       return (ObjectSortedSet)float2IntEntrySet();
/*     */     }
/*     */ 
/*     */     
/*     */     public FloatSortedSet keySet() {
/* 598 */       if (this.keys == null) this.keys = FloatSortedSets.unmodifiable(this.sortedMap.keySet()); 
/* 599 */       return (FloatSortedSet)this.keys;
/*     */     }
/*     */ 
/*     */     
/*     */     public Float2IntSortedMap subMap(float from, float to) {
/* 604 */       return new UnmodifiableSortedMap(this.sortedMap.subMap(from, to));
/*     */     }
/*     */ 
/*     */     
/*     */     public Float2IntSortedMap headMap(float to) {
/* 609 */       return new UnmodifiableSortedMap(this.sortedMap.headMap(to));
/*     */     }
/*     */ 
/*     */     
/*     */     public Float2IntSortedMap tailMap(float from) {
/* 614 */       return new UnmodifiableSortedMap(this.sortedMap.tailMap(from));
/*     */     }
/*     */ 
/*     */     
/*     */     public float firstFloatKey() {
/* 619 */       return this.sortedMap.firstFloatKey();
/*     */     }
/*     */ 
/*     */     
/*     */     public float lastFloatKey() {
/* 624 */       return this.sortedMap.lastFloatKey();
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     @Deprecated
/*     */     public Float firstKey() {
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
/*     */     public Float lastKey() {
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
/*     */     public Float2IntSortedMap subMap(Float from, Float to) {
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
/*     */     public Float2IntSortedMap headMap(Float to) {
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
/*     */     public Float2IntSortedMap tailMap(Float from) {
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
/*     */   public static Float2IntSortedMap unmodifiable(Float2IntSortedMap m) {
/* 691 */     return new UnmodifiableSortedMap(m);
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\i\\unimi\dsi\fastutil\floats\Float2IntSortedMaps.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */