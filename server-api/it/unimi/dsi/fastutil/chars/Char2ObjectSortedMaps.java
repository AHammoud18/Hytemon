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
/*     */ public final class Char2ObjectSortedMaps
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
/*     */   public static <V> ObjectBidirectionalIterator<Char2ObjectMap.Entry<V>> fastIterator(Char2ObjectSortedMap<V> map) {
/*  60 */     ObjectSortedSet<Char2ObjectMap.Entry<V>> entries = map.char2ObjectEntrySet();
/*  61 */     return (entries instanceof Char2ObjectSortedMap.FastSortedEntrySet) ? ((Char2ObjectSortedMap.FastSortedEntrySet)entries).fastIterator() : entries.iterator();
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
/*     */   public static <V> ObjectBidirectionalIterable<Char2ObjectMap.Entry<V>> fastIterable(Char2ObjectSortedMap<V> map) {
/*  76 */     ObjectSortedSet<Char2ObjectMap.Entry<V>> entries = map.char2ObjectEntrySet();
/*  77 */     Objects.requireNonNull((Char2ObjectSortedMap.FastSortedEntrySet)entries); return (entries instanceof Char2ObjectSortedMap.FastSortedEntrySet) ? (Char2ObjectSortedMap.FastSortedEntrySet)entries::fastIterator : (ObjectBidirectionalIterable<Char2ObjectMap.Entry<V>>)entries;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static class EmptySortedMap<V>
/*     */     extends Char2ObjectMaps.EmptyMap<V>
/*     */     implements Char2ObjectSortedMap<V>, Serializable, Cloneable
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
/*     */     public ObjectSortedSet<Char2ObjectMap.Entry<V>> char2ObjectEntrySet() {
/*  99 */       return (ObjectSortedSet<Char2ObjectMap.Entry<V>>)ObjectSortedSets.EMPTY_SET;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     @Deprecated
/*     */     public ObjectSortedSet<Map.Entry<Character, V>> entrySet() {
/* 110 */       return (ObjectSortedSet<Map.Entry<Character, V>>)ObjectSortedSets.EMPTY_SET;
/*     */     }
/*     */ 
/*     */     
/*     */     public CharSortedSet keySet() {
/* 115 */       return CharSortedSets.EMPTY_SET;
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     public Char2ObjectSortedMap<V> subMap(char from, char to) {
/* 121 */       return Char2ObjectSortedMaps.EMPTY_MAP;
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     public Char2ObjectSortedMap<V> headMap(char to) {
/* 127 */       return Char2ObjectSortedMaps.EMPTY_MAP;
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     public Char2ObjectSortedMap<V> tailMap(char from) {
/* 133 */       return Char2ObjectSortedMaps.EMPTY_MAP;
/*     */     }
/*     */ 
/*     */     
/*     */     public char firstCharKey() {
/* 138 */       throw new NoSuchElementException();
/*     */     }
/*     */ 
/*     */     
/*     */     public char lastCharKey() {
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
/*     */     public Char2ObjectSortedMap<V> headMap(Character oto) {
/* 154 */       return headMap(oto.charValue());
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     @Deprecated
/*     */     public Char2ObjectSortedMap<V> tailMap(Character ofrom) {
/* 165 */       return tailMap(ofrom.charValue());
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     @Deprecated
/*     */     public Char2ObjectSortedMap<V> subMap(Character ofrom, Character oto) {
/* 176 */       return subMap(ofrom.charValue(), oto.charValue());
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
/* 187 */       return Character.valueOf(firstCharKey());
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
/* 198 */       return Character.valueOf(lastCharKey());
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
/*     */   public static <V> Char2ObjectSortedMap<V> emptyMap() {
/* 218 */     return EMPTY_MAP;
/*     */   }
/*     */ 
/*     */   
/*     */   public static class Singleton<V>
/*     */     extends Char2ObjectMaps.Singleton<V>
/*     */     implements Char2ObjectSortedMap<V>, Serializable, Cloneable
/*     */   {
/*     */     private static final long serialVersionUID = -7046029254386353129L;
/*     */     
/*     */     protected final CharComparator comparator;
/*     */ 
/*     */     
/*     */     protected Singleton(char key, V value, CharComparator comparator) {
/* 232 */       super(key, value);
/* 233 */       this.comparator = comparator;
/*     */     }
/*     */     
/*     */     protected Singleton(char key, V value) {
/* 237 */       this(key, value, (CharComparator)null);
/*     */     }
/*     */     
/*     */     final int compare(char k1, char k2) {
/* 241 */       return (this.comparator == null) ? Character.compare(k1, k2) : this.comparator.compare(k1, k2);
/*     */     }
/*     */ 
/*     */     
/*     */     public CharComparator comparator() {
/* 246 */       return this.comparator;
/*     */     }
/*     */ 
/*     */     
/*     */     public ObjectSortedSet<Char2ObjectMap.Entry<V>> char2ObjectEntrySet() {
/* 251 */       if (this.entries == null) this.entries = (ObjectSet<Char2ObjectMap.Entry<V>>)ObjectSortedSets.singleton(new AbstractChar2ObjectMap.BasicEntry<>(this.key, this.value), Char2ObjectSortedMaps.entryComparator(this.comparator)); 
/* 252 */       return (ObjectSortedSet<Char2ObjectMap.Entry<V>>)this.entries;
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
/*     */     public ObjectSortedSet<Map.Entry<Character, V>> entrySet() {
/* 264 */       return (ObjectSortedSet)char2ObjectEntrySet();
/*     */     }
/*     */ 
/*     */     
/*     */     public CharSortedSet keySet() {
/* 269 */       if (this.keys == null) this.keys = CharSortedSets.singleton(this.key, this.comparator); 
/* 270 */       return (CharSortedSet)this.keys;
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     public Char2ObjectSortedMap<V> subMap(char from, char to) {
/* 276 */       if (compare(from, this.key) <= 0 && compare(this.key, to) < 0) return this; 
/* 277 */       return Char2ObjectSortedMaps.EMPTY_MAP;
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     public Char2ObjectSortedMap<V> headMap(char to) {
/* 283 */       if (compare(this.key, to) < 0) return this; 
/* 284 */       return Char2ObjectSortedMaps.EMPTY_MAP;
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     public Char2ObjectSortedMap<V> tailMap(char from) {
/* 290 */       if (compare(from, this.key) <= 0) return this; 
/* 291 */       return Char2ObjectSortedMaps.EMPTY_MAP;
/*     */     }
/*     */ 
/*     */     
/*     */     public char firstCharKey() {
/* 296 */       return this.key;
/*     */     }
/*     */ 
/*     */     
/*     */     public char lastCharKey() {
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
/*     */     public Char2ObjectSortedMap<V> headMap(Character oto) {
/* 312 */       return headMap(oto.charValue());
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     @Deprecated
/*     */     public Char2ObjectSortedMap<V> tailMap(Character ofrom) {
/* 323 */       return tailMap(ofrom.charValue());
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     @Deprecated
/*     */     public Char2ObjectSortedMap<V> subMap(Character ofrom, Character oto) {
/* 334 */       return subMap(ofrom.charValue(), oto.charValue());
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
/* 345 */       return Character.valueOf(firstCharKey());
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
/* 356 */       return Character.valueOf(lastCharKey());
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
/*     */   public static <V> Char2ObjectSortedMap<V> singleton(Character key, V value) {
/* 372 */     return new Singleton<>(key.charValue(), value);
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
/*     */   public static <V> Char2ObjectSortedMap<V> singleton(Character key, V value, CharComparator comparator) {
/* 388 */     return new Singleton<>(key.charValue(), value, comparator);
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
/*     */   public static <V> Char2ObjectSortedMap<V> singleton(char key, V value) {
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
/*     */   public static <V> Char2ObjectSortedMap<V> singleton(char key, V value, CharComparator comparator) {
/* 419 */     return new Singleton<>(key, value, comparator);
/*     */   }
/*     */   
/*     */   public static class SynchronizedSortedMap<V>
/*     */     extends Char2ObjectMaps.SynchronizedMap<V> implements Char2ObjectSortedMap<V>, Serializable {
/*     */     private static final long serialVersionUID = -7046029254386353129L;
/*     */     protected final Char2ObjectSortedMap<V> sortedMap;
/*     */     
/*     */     protected SynchronizedSortedMap(Char2ObjectSortedMap<V> m, Object sync) {
/* 428 */       super(m, sync);
/* 429 */       this.sortedMap = m;
/*     */     }
/*     */     
/*     */     protected SynchronizedSortedMap(Char2ObjectSortedMap<V> m) {
/* 433 */       super(m);
/* 434 */       this.sortedMap = m;
/*     */     }
/*     */ 
/*     */     
/*     */     public CharComparator comparator() {
/* 439 */       synchronized (this.sync) {
/* 440 */         return this.sortedMap.comparator();
/*     */       } 
/*     */     }
/*     */ 
/*     */     
/*     */     public ObjectSortedSet<Char2ObjectMap.Entry<V>> char2ObjectEntrySet() {
/* 446 */       if (this.entries == null) this.entries = (ObjectSet<Char2ObjectMap.Entry<V>>)ObjectSortedSets.synchronize(this.sortedMap.char2ObjectEntrySet(), this.sync); 
/* 447 */       return (ObjectSortedSet<Char2ObjectMap.Entry<V>>)this.entries;
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
/*     */     public ObjectSortedSet<Map.Entry<Character, V>> entrySet() {
/* 459 */       return (ObjectSortedSet)char2ObjectEntrySet();
/*     */     }
/*     */ 
/*     */     
/*     */     public CharSortedSet keySet() {
/* 464 */       if (this.keys == null) this.keys = CharSortedSets.synchronize(this.sortedMap.keySet(), this.sync); 
/* 465 */       return (CharSortedSet)this.keys;
/*     */     }
/*     */ 
/*     */     
/*     */     public Char2ObjectSortedMap<V> subMap(char from, char to) {
/* 470 */       return new SynchronizedSortedMap(this.sortedMap.subMap(from, to), this.sync);
/*     */     }
/*     */ 
/*     */     
/*     */     public Char2ObjectSortedMap<V> headMap(char to) {
/* 475 */       return new SynchronizedSortedMap(this.sortedMap.headMap(to), this.sync);
/*     */     }
/*     */ 
/*     */     
/*     */     public Char2ObjectSortedMap<V> tailMap(char from) {
/* 480 */       return new SynchronizedSortedMap(this.sortedMap.tailMap(from), this.sync);
/*     */     }
/*     */ 
/*     */     
/*     */     public char firstCharKey() {
/* 485 */       synchronized (this.sync) {
/* 486 */         return this.sortedMap.firstCharKey();
/*     */       } 
/*     */     }
/*     */ 
/*     */     
/*     */     public char lastCharKey() {
/* 492 */       synchronized (this.sync) {
/* 493 */         return this.sortedMap.lastCharKey();
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
/*     */     public Character lastKey() {
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
/*     */     public Char2ObjectSortedMap<V> subMap(Character from, Character to) {
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
/*     */     public Char2ObjectSortedMap<V> headMap(Character to) {
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
/*     */     public Char2ObjectSortedMap<V> tailMap(Character from) {
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
/*     */   public static <V> Char2ObjectSortedMap<V> synchronize(Char2ObjectSortedMap<V> m) {
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
/*     */   public static <V> Char2ObjectSortedMap<V> synchronize(Char2ObjectSortedMap<V> m, Object sync) {
/* 578 */     return new SynchronizedSortedMap<>(m, sync);
/*     */   }
/*     */   
/*     */   public static class UnmodifiableSortedMap<V>
/*     */     extends Char2ObjectMaps.UnmodifiableMap<V> implements Char2ObjectSortedMap<V>, Serializable {
/*     */     private static final long serialVersionUID = -7046029254386353129L;
/*     */     protected final Char2ObjectSortedMap<? extends V> sortedMap;
/*     */     
/*     */     protected UnmodifiableSortedMap(Char2ObjectSortedMap<? extends V> m) {
/* 587 */       super(m);
/* 588 */       this.sortedMap = m;
/*     */     }
/*     */ 
/*     */     
/*     */     public CharComparator comparator() {
/* 593 */       return this.sortedMap.comparator();
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     public ObjectSortedSet<Char2ObjectMap.Entry<V>> char2ObjectEntrySet() {
/* 599 */       if (this.entries == null) this.entries = (ObjectSet<Char2ObjectMap.Entry<V>>)ObjectSortedSets.unmodifiable(this.sortedMap.char2ObjectEntrySet()); 
/* 600 */       return (ObjectSortedSet<Char2ObjectMap.Entry<V>>)this.entries;
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
/*     */     public ObjectSortedSet<Map.Entry<Character, V>> entrySet() {
/* 612 */       return (ObjectSortedSet)char2ObjectEntrySet();
/*     */     }
/*     */ 
/*     */     
/*     */     public CharSortedSet keySet() {
/* 617 */       if (this.keys == null) this.keys = CharSortedSets.unmodifiable(this.sortedMap.keySet()); 
/* 618 */       return (CharSortedSet)this.keys;
/*     */     }
/*     */ 
/*     */     
/*     */     public Char2ObjectSortedMap<V> subMap(char from, char to) {
/* 623 */       return new UnmodifiableSortedMap(this.sortedMap.subMap(from, to));
/*     */     }
/*     */ 
/*     */     
/*     */     public Char2ObjectSortedMap<V> headMap(char to) {
/* 628 */       return new UnmodifiableSortedMap(this.sortedMap.headMap(to));
/*     */     }
/*     */ 
/*     */     
/*     */     public Char2ObjectSortedMap<V> tailMap(char from) {
/* 633 */       return new UnmodifiableSortedMap(this.sortedMap.tailMap(from));
/*     */     }
/*     */ 
/*     */     
/*     */     public char firstCharKey() {
/* 638 */       return this.sortedMap.firstCharKey();
/*     */     }
/*     */ 
/*     */     
/*     */     public char lastCharKey() {
/* 643 */       return this.sortedMap.lastCharKey();
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
/*     */     public Character lastKey() {
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
/*     */     public Char2ObjectSortedMap<V> subMap(Character from, Character to) {
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
/*     */     public Char2ObjectSortedMap<V> headMap(Character to) {
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
/*     */     public Char2ObjectSortedMap<V> tailMap(Character from) {
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
/*     */   public static <V> Char2ObjectSortedMap<V> unmodifiable(Char2ObjectSortedMap<? extends V> m) {
/* 710 */     return new UnmodifiableSortedMap<>(m);
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\i\\unimi\dsi\fastutil\chars\Char2ObjectSortedMaps.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */