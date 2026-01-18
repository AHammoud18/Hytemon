/*     */ package it.unimi.dsi.fastutil.chars;
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
/*     */ public final class Char2ByteSortedMaps
/*     */ {
/*     */   public static Comparator<? super Map.Entry<Character, ?>> entryComparator(CharComparator comparator) {
/*  45 */     return (x, y) -> comparator.compare(((Character)x.getKey()).charValue(), ((Character)y.getKey()).charValue());
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
/*     */   public static ObjectBidirectionalIterator<Char2ByteMap.Entry> fastIterator(Char2ByteSortedMap map) {
/*  60 */     ObjectSortedSet<Char2ByteMap.Entry> entries = map.char2ByteEntrySet();
/*  61 */     return (entries instanceof Char2ByteSortedMap.FastSortedEntrySet) ? ((Char2ByteSortedMap.FastSortedEntrySet)entries).fastIterator() : entries.iterator();
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
/*     */   public static ObjectBidirectionalIterable<Char2ByteMap.Entry> fastIterable(Char2ByteSortedMap map) {
/*  76 */     ObjectSortedSet<Char2ByteMap.Entry> entries = map.char2ByteEntrySet();
/*  77 */     Objects.requireNonNull((Char2ByteSortedMap.FastSortedEntrySet)entries); return (entries instanceof Char2ByteSortedMap.FastSortedEntrySet) ? (Char2ByteSortedMap.FastSortedEntrySet)entries::fastIterator : (ObjectBidirectionalIterable<Char2ByteMap.Entry>)entries;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static class EmptySortedMap
/*     */     extends Char2ByteMaps.EmptyMap
/*     */     implements Char2ByteSortedMap, Serializable, Cloneable
/*     */   {
/*     */     private static final long serialVersionUID = -7046029254386353129L;
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public CharComparator comparator() {
/*  94 */       return null;
/*     */     }
/*     */ 
/*     */     
/*     */     public ObjectSortedSet<Char2ByteMap.Entry> char2ByteEntrySet() {
/*  99 */       return (ObjectSortedSet<Char2ByteMap.Entry>)ObjectSortedSets.EMPTY_SET;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     @Deprecated
/*     */     public ObjectSortedSet<Map.Entry<Character, Byte>> entrySet() {
/* 110 */       return (ObjectSortedSet<Map.Entry<Character, Byte>>)ObjectSortedSets.EMPTY_SET;
/*     */     }
/*     */ 
/*     */     
/*     */     public CharSortedSet keySet() {
/* 115 */       return CharSortedSets.EMPTY_SET;
/*     */     }
/*     */ 
/*     */     
/*     */     public Char2ByteSortedMap subMap(char from, char to) {
/* 120 */       return Char2ByteSortedMaps.EMPTY_MAP;
/*     */     }
/*     */ 
/*     */     
/*     */     public Char2ByteSortedMap headMap(char to) {
/* 125 */       return Char2ByteSortedMaps.EMPTY_MAP;
/*     */     }
/*     */ 
/*     */     
/*     */     public Char2ByteSortedMap tailMap(char from) {
/* 130 */       return Char2ByteSortedMaps.EMPTY_MAP;
/*     */     }
/*     */ 
/*     */     
/*     */     public char firstCharKey() {
/* 135 */       throw new NoSuchElementException();
/*     */     }
/*     */ 
/*     */     
/*     */     public char lastCharKey() {
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
/*     */     public Char2ByteSortedMap headMap(Character oto) {
/* 151 */       return headMap(oto.charValue());
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     @Deprecated
/*     */     public Char2ByteSortedMap tailMap(Character ofrom) {
/* 162 */       return tailMap(ofrom.charValue());
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     @Deprecated
/*     */     public Char2ByteSortedMap subMap(Character ofrom, Character oto) {
/* 173 */       return subMap(ofrom.charValue(), oto.charValue());
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     @Deprecated
/*     */     public Character firstKey() {
/* 184 */       return Character.valueOf(firstCharKey());
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     @Deprecated
/*     */     public Character lastKey() {
/* 195 */       return Character.valueOf(lastCharKey());
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
/*     */     extends Char2ByteMaps.Singleton
/*     */     implements Char2ByteSortedMap, Serializable, Cloneable
/*     */   {
/*     */     private static final long serialVersionUID = -7046029254386353129L;
/*     */     
/*     */     protected final CharComparator comparator;
/*     */ 
/*     */     
/*     */     protected Singleton(char key, byte value, CharComparator comparator) {
/* 216 */       super(key, value);
/* 217 */       this.comparator = comparator;
/*     */     }
/*     */     
/*     */     protected Singleton(char key, byte value) {
/* 221 */       this(key, value, (CharComparator)null);
/*     */     }
/*     */     
/*     */     final int compare(char k1, char k2) {
/* 225 */       return (this.comparator == null) ? Character.compare(k1, k2) : this.comparator.compare(k1, k2);
/*     */     }
/*     */ 
/*     */     
/*     */     public CharComparator comparator() {
/* 230 */       return this.comparator;
/*     */     }
/*     */ 
/*     */     
/*     */     public ObjectSortedSet<Char2ByteMap.Entry> char2ByteEntrySet() {
/* 235 */       if (this.entries == null) this.entries = (ObjectSet<Char2ByteMap.Entry>)ObjectSortedSets.singleton(new AbstractChar2ByteMap.BasicEntry(this.key, this.value), Char2ByteSortedMaps.entryComparator(this.comparator)); 
/* 236 */       return (ObjectSortedSet<Char2ByteMap.Entry>)this.entries;
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
/*     */     public ObjectSortedSet<Map.Entry<Character, Byte>> entrySet() {
/* 248 */       return (ObjectSortedSet)char2ByteEntrySet();
/*     */     }
/*     */ 
/*     */     
/*     */     public CharSortedSet keySet() {
/* 253 */       if (this.keys == null) this.keys = CharSortedSets.singleton(this.key, this.comparator); 
/* 254 */       return (CharSortedSet)this.keys;
/*     */     }
/*     */ 
/*     */     
/*     */     public Char2ByteSortedMap subMap(char from, char to) {
/* 259 */       if (compare(from, this.key) <= 0 && compare(this.key, to) < 0) return this; 
/* 260 */       return Char2ByteSortedMaps.EMPTY_MAP;
/*     */     }
/*     */ 
/*     */     
/*     */     public Char2ByteSortedMap headMap(char to) {
/* 265 */       if (compare(this.key, to) < 0) return this; 
/* 266 */       return Char2ByteSortedMaps.EMPTY_MAP;
/*     */     }
/*     */ 
/*     */     
/*     */     public Char2ByteSortedMap tailMap(char from) {
/* 271 */       if (compare(from, this.key) <= 0) return this; 
/* 272 */       return Char2ByteSortedMaps.EMPTY_MAP;
/*     */     }
/*     */ 
/*     */     
/*     */     public char firstCharKey() {
/* 277 */       return this.key;
/*     */     }
/*     */ 
/*     */     
/*     */     public char lastCharKey() {
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
/*     */     public Char2ByteSortedMap headMap(Character oto) {
/* 293 */       return headMap(oto.charValue());
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     @Deprecated
/*     */     public Char2ByteSortedMap tailMap(Character ofrom) {
/* 304 */       return tailMap(ofrom.charValue());
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     @Deprecated
/*     */     public Char2ByteSortedMap subMap(Character ofrom, Character oto) {
/* 315 */       return subMap(ofrom.charValue(), oto.charValue());
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     @Deprecated
/*     */     public Character firstKey() {
/* 326 */       return Character.valueOf(firstCharKey());
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     @Deprecated
/*     */     public Character lastKey() {
/* 337 */       return Character.valueOf(lastCharKey());
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
/*     */   public static Char2ByteSortedMap singleton(Character key, Byte value) {
/* 353 */     return new Singleton(key.charValue(), value.byteValue());
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
/*     */   public static Char2ByteSortedMap singleton(Character key, Byte value, CharComparator comparator) {
/* 369 */     return new Singleton(key.charValue(), value.byteValue(), comparator);
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
/*     */   public static Char2ByteSortedMap singleton(char key, byte value) {
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
/*     */   public static Char2ByteSortedMap singleton(char key, byte value, CharComparator comparator) {
/* 400 */     return new Singleton(key, value, comparator);
/*     */   }
/*     */   
/*     */   public static class SynchronizedSortedMap
/*     */     extends Char2ByteMaps.SynchronizedMap implements Char2ByteSortedMap, Serializable {
/*     */     private static final long serialVersionUID = -7046029254386353129L;
/*     */     protected final Char2ByteSortedMap sortedMap;
/*     */     
/*     */     protected SynchronizedSortedMap(Char2ByteSortedMap m, Object sync) {
/* 409 */       super(m, sync);
/* 410 */       this.sortedMap = m;
/*     */     }
/*     */     
/*     */     protected SynchronizedSortedMap(Char2ByteSortedMap m) {
/* 414 */       super(m);
/* 415 */       this.sortedMap = m;
/*     */     }
/*     */ 
/*     */     
/*     */     public CharComparator comparator() {
/* 420 */       synchronized (this.sync) {
/* 421 */         return this.sortedMap.comparator();
/*     */       } 
/*     */     }
/*     */ 
/*     */     
/*     */     public ObjectSortedSet<Char2ByteMap.Entry> char2ByteEntrySet() {
/* 427 */       if (this.entries == null) this.entries = (ObjectSet<Char2ByteMap.Entry>)ObjectSortedSets.synchronize(this.sortedMap.char2ByteEntrySet(), this.sync); 
/* 428 */       return (ObjectSortedSet<Char2ByteMap.Entry>)this.entries;
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
/*     */     public ObjectSortedSet<Map.Entry<Character, Byte>> entrySet() {
/* 440 */       return (ObjectSortedSet)char2ByteEntrySet();
/*     */     }
/*     */ 
/*     */     
/*     */     public CharSortedSet keySet() {
/* 445 */       if (this.keys == null) this.keys = CharSortedSets.synchronize(this.sortedMap.keySet(), this.sync); 
/* 446 */       return (CharSortedSet)this.keys;
/*     */     }
/*     */ 
/*     */     
/*     */     public Char2ByteSortedMap subMap(char from, char to) {
/* 451 */       return new SynchronizedSortedMap(this.sortedMap.subMap(from, to), this.sync);
/*     */     }
/*     */ 
/*     */     
/*     */     public Char2ByteSortedMap headMap(char to) {
/* 456 */       return new SynchronizedSortedMap(this.sortedMap.headMap(to), this.sync);
/*     */     }
/*     */ 
/*     */     
/*     */     public Char2ByteSortedMap tailMap(char from) {
/* 461 */       return new SynchronizedSortedMap(this.sortedMap.tailMap(from), this.sync);
/*     */     }
/*     */ 
/*     */     
/*     */     public char firstCharKey() {
/* 466 */       synchronized (this.sync) {
/* 467 */         return this.sortedMap.firstCharKey();
/*     */       } 
/*     */     }
/*     */ 
/*     */     
/*     */     public char lastCharKey() {
/* 473 */       synchronized (this.sync) {
/* 474 */         return this.sortedMap.lastCharKey();
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
/*     */     public Character firstKey() {
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
/*     */     public Character lastKey() {
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
/*     */     public Char2ByteSortedMap subMap(Character from, Character to) {
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
/*     */     public Char2ByteSortedMap headMap(Character to) {
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
/*     */     public Char2ByteSortedMap tailMap(Character from) {
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
/*     */   public static Char2ByteSortedMap synchronize(Char2ByteSortedMap m) {
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
/*     */   public static Char2ByteSortedMap synchronize(Char2ByteSortedMap m, Object sync) {
/* 559 */     return new SynchronizedSortedMap(m, sync);
/*     */   }
/*     */   
/*     */   public static class UnmodifiableSortedMap
/*     */     extends Char2ByteMaps.UnmodifiableMap implements Char2ByteSortedMap, Serializable {
/*     */     private static final long serialVersionUID = -7046029254386353129L;
/*     */     protected final Char2ByteSortedMap sortedMap;
/*     */     
/*     */     protected UnmodifiableSortedMap(Char2ByteSortedMap m) {
/* 568 */       super(m);
/* 569 */       this.sortedMap = m;
/*     */     }
/*     */ 
/*     */     
/*     */     public CharComparator comparator() {
/* 574 */       return this.sortedMap.comparator();
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     public ObjectSortedSet<Char2ByteMap.Entry> char2ByteEntrySet() {
/* 580 */       if (this.entries == null) this.entries = (ObjectSet<Char2ByteMap.Entry>)ObjectSortedSets.unmodifiable(this.sortedMap.char2ByteEntrySet()); 
/* 581 */       return (ObjectSortedSet<Char2ByteMap.Entry>)this.entries;
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
/*     */     public ObjectSortedSet<Map.Entry<Character, Byte>> entrySet() {
/* 593 */       return (ObjectSortedSet)char2ByteEntrySet();
/*     */     }
/*     */ 
/*     */     
/*     */     public CharSortedSet keySet() {
/* 598 */       if (this.keys == null) this.keys = CharSortedSets.unmodifiable(this.sortedMap.keySet()); 
/* 599 */       return (CharSortedSet)this.keys;
/*     */     }
/*     */ 
/*     */     
/*     */     public Char2ByteSortedMap subMap(char from, char to) {
/* 604 */       return new UnmodifiableSortedMap(this.sortedMap.subMap(from, to));
/*     */     }
/*     */ 
/*     */     
/*     */     public Char2ByteSortedMap headMap(char to) {
/* 609 */       return new UnmodifiableSortedMap(this.sortedMap.headMap(to));
/*     */     }
/*     */ 
/*     */     
/*     */     public Char2ByteSortedMap tailMap(char from) {
/* 614 */       return new UnmodifiableSortedMap(this.sortedMap.tailMap(from));
/*     */     }
/*     */ 
/*     */     
/*     */     public char firstCharKey() {
/* 619 */       return this.sortedMap.firstCharKey();
/*     */     }
/*     */ 
/*     */     
/*     */     public char lastCharKey() {
/* 624 */       return this.sortedMap.lastCharKey();
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     @Deprecated
/*     */     public Character firstKey() {
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
/*     */     public Character lastKey() {
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
/*     */     public Char2ByteSortedMap subMap(Character from, Character to) {
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
/*     */     public Char2ByteSortedMap headMap(Character to) {
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
/*     */     public Char2ByteSortedMap tailMap(Character from) {
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
/*     */   public static Char2ByteSortedMap unmodifiable(Char2ByteSortedMap m) {
/* 691 */     return new UnmodifiableSortedMap(m);
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\i\\unimi\dsi\fastutil\chars\Char2ByteSortedMaps.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */