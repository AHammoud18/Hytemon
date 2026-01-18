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
/*     */ public final class Short2CharSortedMaps
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
/*     */   public static ObjectBidirectionalIterator<Short2CharMap.Entry> fastIterator(Short2CharSortedMap map) {
/*  60 */     ObjectSortedSet<Short2CharMap.Entry> entries = map.short2CharEntrySet();
/*  61 */     return (entries instanceof Short2CharSortedMap.FastSortedEntrySet) ? ((Short2CharSortedMap.FastSortedEntrySet)entries).fastIterator() : entries.iterator();
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
/*     */   public static ObjectBidirectionalIterable<Short2CharMap.Entry> fastIterable(Short2CharSortedMap map) {
/*  76 */     ObjectSortedSet<Short2CharMap.Entry> entries = map.short2CharEntrySet();
/*  77 */     Objects.requireNonNull((Short2CharSortedMap.FastSortedEntrySet)entries); return (entries instanceof Short2CharSortedMap.FastSortedEntrySet) ? (Short2CharSortedMap.FastSortedEntrySet)entries::fastIterator : (ObjectBidirectionalIterable<Short2CharMap.Entry>)entries;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static class EmptySortedMap
/*     */     extends Short2CharMaps.EmptyMap
/*     */     implements Short2CharSortedMap, Serializable, Cloneable
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
/*     */     public ObjectSortedSet<Short2CharMap.Entry> short2CharEntrySet() {
/*  99 */       return (ObjectSortedSet<Short2CharMap.Entry>)ObjectSortedSets.EMPTY_SET;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     @Deprecated
/*     */     public ObjectSortedSet<Map.Entry<Short, Character>> entrySet() {
/* 110 */       return (ObjectSortedSet<Map.Entry<Short, Character>>)ObjectSortedSets.EMPTY_SET;
/*     */     }
/*     */ 
/*     */     
/*     */     public ShortSortedSet keySet() {
/* 115 */       return ShortSortedSets.EMPTY_SET;
/*     */     }
/*     */ 
/*     */     
/*     */     public Short2CharSortedMap subMap(short from, short to) {
/* 120 */       return Short2CharSortedMaps.EMPTY_MAP;
/*     */     }
/*     */ 
/*     */     
/*     */     public Short2CharSortedMap headMap(short to) {
/* 125 */       return Short2CharSortedMaps.EMPTY_MAP;
/*     */     }
/*     */ 
/*     */     
/*     */     public Short2CharSortedMap tailMap(short from) {
/* 130 */       return Short2CharSortedMaps.EMPTY_MAP;
/*     */     }
/*     */ 
/*     */     
/*     */     public short firstShortKey() {
/* 135 */       throw new NoSuchElementException();
/*     */     }
/*     */ 
/*     */     
/*     */     public short lastShortKey() {
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
/*     */     public Short2CharSortedMap headMap(Short oto) {
/* 151 */       return headMap(oto.shortValue());
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     @Deprecated
/*     */     public Short2CharSortedMap tailMap(Short ofrom) {
/* 162 */       return tailMap(ofrom.shortValue());
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     @Deprecated
/*     */     public Short2CharSortedMap subMap(Short ofrom, Short oto) {
/* 173 */       return subMap(ofrom.shortValue(), oto.shortValue());
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
/* 184 */       return Short.valueOf(firstShortKey());
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
/* 195 */       return Short.valueOf(lastShortKey());
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
/*     */     extends Short2CharMaps.Singleton
/*     */     implements Short2CharSortedMap, Serializable, Cloneable
/*     */   {
/*     */     private static final long serialVersionUID = -7046029254386353129L;
/*     */     
/*     */     protected final ShortComparator comparator;
/*     */ 
/*     */     
/*     */     protected Singleton(short key, char value, ShortComparator comparator) {
/* 216 */       super(key, value);
/* 217 */       this.comparator = comparator;
/*     */     }
/*     */     
/*     */     protected Singleton(short key, char value) {
/* 221 */       this(key, value, (ShortComparator)null);
/*     */     }
/*     */     
/*     */     final int compare(short k1, short k2) {
/* 225 */       return (this.comparator == null) ? Short.compare(k1, k2) : this.comparator.compare(k1, k2);
/*     */     }
/*     */ 
/*     */     
/*     */     public ShortComparator comparator() {
/* 230 */       return this.comparator;
/*     */     }
/*     */ 
/*     */     
/*     */     public ObjectSortedSet<Short2CharMap.Entry> short2CharEntrySet() {
/* 235 */       if (this.entries == null) this.entries = (ObjectSet<Short2CharMap.Entry>)ObjectSortedSets.singleton(new AbstractShort2CharMap.BasicEntry(this.key, this.value), Short2CharSortedMaps.entryComparator(this.comparator)); 
/* 236 */       return (ObjectSortedSet<Short2CharMap.Entry>)this.entries;
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
/*     */     public ObjectSortedSet<Map.Entry<Short, Character>> entrySet() {
/* 248 */       return (ObjectSortedSet)short2CharEntrySet();
/*     */     }
/*     */ 
/*     */     
/*     */     public ShortSortedSet keySet() {
/* 253 */       if (this.keys == null) this.keys = ShortSortedSets.singleton(this.key, this.comparator); 
/* 254 */       return (ShortSortedSet)this.keys;
/*     */     }
/*     */ 
/*     */     
/*     */     public Short2CharSortedMap subMap(short from, short to) {
/* 259 */       if (compare(from, this.key) <= 0 && compare(this.key, to) < 0) return this; 
/* 260 */       return Short2CharSortedMaps.EMPTY_MAP;
/*     */     }
/*     */ 
/*     */     
/*     */     public Short2CharSortedMap headMap(short to) {
/* 265 */       if (compare(this.key, to) < 0) return this; 
/* 266 */       return Short2CharSortedMaps.EMPTY_MAP;
/*     */     }
/*     */ 
/*     */     
/*     */     public Short2CharSortedMap tailMap(short from) {
/* 271 */       if (compare(from, this.key) <= 0) return this; 
/* 272 */       return Short2CharSortedMaps.EMPTY_MAP;
/*     */     }
/*     */ 
/*     */     
/*     */     public short firstShortKey() {
/* 277 */       return this.key;
/*     */     }
/*     */ 
/*     */     
/*     */     public short lastShortKey() {
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
/*     */     public Short2CharSortedMap headMap(Short oto) {
/* 293 */       return headMap(oto.shortValue());
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     @Deprecated
/*     */     public Short2CharSortedMap tailMap(Short ofrom) {
/* 304 */       return tailMap(ofrom.shortValue());
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     @Deprecated
/*     */     public Short2CharSortedMap subMap(Short ofrom, Short oto) {
/* 315 */       return subMap(ofrom.shortValue(), oto.shortValue());
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
/* 326 */       return Short.valueOf(firstShortKey());
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
/* 337 */       return Short.valueOf(lastShortKey());
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
/*     */   public static Short2CharSortedMap singleton(Short key, Character value) {
/* 353 */     return new Singleton(key.shortValue(), value.charValue());
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
/*     */   public static Short2CharSortedMap singleton(Short key, Character value, ShortComparator comparator) {
/* 369 */     return new Singleton(key.shortValue(), value.charValue(), comparator);
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
/*     */   public static Short2CharSortedMap singleton(short key, char value) {
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
/*     */   public static Short2CharSortedMap singleton(short key, char value, ShortComparator comparator) {
/* 400 */     return new Singleton(key, value, comparator);
/*     */   }
/*     */   
/*     */   public static class SynchronizedSortedMap
/*     */     extends Short2CharMaps.SynchronizedMap implements Short2CharSortedMap, Serializable {
/*     */     private static final long serialVersionUID = -7046029254386353129L;
/*     */     protected final Short2CharSortedMap sortedMap;
/*     */     
/*     */     protected SynchronizedSortedMap(Short2CharSortedMap m, Object sync) {
/* 409 */       super(m, sync);
/* 410 */       this.sortedMap = m;
/*     */     }
/*     */     
/*     */     protected SynchronizedSortedMap(Short2CharSortedMap m) {
/* 414 */       super(m);
/* 415 */       this.sortedMap = m;
/*     */     }
/*     */ 
/*     */     
/*     */     public ShortComparator comparator() {
/* 420 */       synchronized (this.sync) {
/* 421 */         return this.sortedMap.comparator();
/*     */       } 
/*     */     }
/*     */ 
/*     */     
/*     */     public ObjectSortedSet<Short2CharMap.Entry> short2CharEntrySet() {
/* 427 */       if (this.entries == null) this.entries = (ObjectSet<Short2CharMap.Entry>)ObjectSortedSets.synchronize(this.sortedMap.short2CharEntrySet(), this.sync); 
/* 428 */       return (ObjectSortedSet<Short2CharMap.Entry>)this.entries;
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
/*     */     public ObjectSortedSet<Map.Entry<Short, Character>> entrySet() {
/* 440 */       return (ObjectSortedSet)short2CharEntrySet();
/*     */     }
/*     */ 
/*     */     
/*     */     public ShortSortedSet keySet() {
/* 445 */       if (this.keys == null) this.keys = ShortSortedSets.synchronize(this.sortedMap.keySet(), this.sync); 
/* 446 */       return (ShortSortedSet)this.keys;
/*     */     }
/*     */ 
/*     */     
/*     */     public Short2CharSortedMap subMap(short from, short to) {
/* 451 */       return new SynchronizedSortedMap(this.sortedMap.subMap(from, to), this.sync);
/*     */     }
/*     */ 
/*     */     
/*     */     public Short2CharSortedMap headMap(short to) {
/* 456 */       return new SynchronizedSortedMap(this.sortedMap.headMap(to), this.sync);
/*     */     }
/*     */ 
/*     */     
/*     */     public Short2CharSortedMap tailMap(short from) {
/* 461 */       return new SynchronizedSortedMap(this.sortedMap.tailMap(from), this.sync);
/*     */     }
/*     */ 
/*     */     
/*     */     public short firstShortKey() {
/* 466 */       synchronized (this.sync) {
/* 467 */         return this.sortedMap.firstShortKey();
/*     */       } 
/*     */     }
/*     */ 
/*     */     
/*     */     public short lastShortKey() {
/* 473 */       synchronized (this.sync) {
/* 474 */         return this.sortedMap.lastShortKey();
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
/*     */     public Short lastKey() {
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
/*     */     public Short2CharSortedMap subMap(Short from, Short to) {
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
/*     */     public Short2CharSortedMap headMap(Short to) {
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
/*     */     public Short2CharSortedMap tailMap(Short from) {
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
/*     */   public static Short2CharSortedMap synchronize(Short2CharSortedMap m) {
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
/*     */   public static Short2CharSortedMap synchronize(Short2CharSortedMap m, Object sync) {
/* 559 */     return new SynchronizedSortedMap(m, sync);
/*     */   }
/*     */   
/*     */   public static class UnmodifiableSortedMap
/*     */     extends Short2CharMaps.UnmodifiableMap implements Short2CharSortedMap, Serializable {
/*     */     private static final long serialVersionUID = -7046029254386353129L;
/*     */     protected final Short2CharSortedMap sortedMap;
/*     */     
/*     */     protected UnmodifiableSortedMap(Short2CharSortedMap m) {
/* 568 */       super(m);
/* 569 */       this.sortedMap = m;
/*     */     }
/*     */ 
/*     */     
/*     */     public ShortComparator comparator() {
/* 574 */       return this.sortedMap.comparator();
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     public ObjectSortedSet<Short2CharMap.Entry> short2CharEntrySet() {
/* 580 */       if (this.entries == null) this.entries = (ObjectSet<Short2CharMap.Entry>)ObjectSortedSets.unmodifiable(this.sortedMap.short2CharEntrySet()); 
/* 581 */       return (ObjectSortedSet<Short2CharMap.Entry>)this.entries;
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
/*     */     public ObjectSortedSet<Map.Entry<Short, Character>> entrySet() {
/* 593 */       return (ObjectSortedSet)short2CharEntrySet();
/*     */     }
/*     */ 
/*     */     
/*     */     public ShortSortedSet keySet() {
/* 598 */       if (this.keys == null) this.keys = ShortSortedSets.unmodifiable(this.sortedMap.keySet()); 
/* 599 */       return (ShortSortedSet)this.keys;
/*     */     }
/*     */ 
/*     */     
/*     */     public Short2CharSortedMap subMap(short from, short to) {
/* 604 */       return new UnmodifiableSortedMap(this.sortedMap.subMap(from, to));
/*     */     }
/*     */ 
/*     */     
/*     */     public Short2CharSortedMap headMap(short to) {
/* 609 */       return new UnmodifiableSortedMap(this.sortedMap.headMap(to));
/*     */     }
/*     */ 
/*     */     
/*     */     public Short2CharSortedMap tailMap(short from) {
/* 614 */       return new UnmodifiableSortedMap(this.sortedMap.tailMap(from));
/*     */     }
/*     */ 
/*     */     
/*     */     public short firstShortKey() {
/* 619 */       return this.sortedMap.firstShortKey();
/*     */     }
/*     */ 
/*     */     
/*     */     public short lastShortKey() {
/* 624 */       return this.sortedMap.lastShortKey();
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
/*     */     public Short lastKey() {
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
/*     */     public Short2CharSortedMap subMap(Short from, Short to) {
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
/*     */     public Short2CharSortedMap headMap(Short to) {
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
/*     */     public Short2CharSortedMap tailMap(Short from) {
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
/*     */   public static Short2CharSortedMap unmodifiable(Short2CharSortedMap m) {
/* 691 */     return new UnmodifiableSortedMap(m);
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\i\\unimi\dsi\fastutil\shorts\Short2CharSortedMaps.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */