/*     */ package it.unimi.dsi.fastutil.chars;
/*     */ 
/*     */ import it.unimi.dsi.fastutil.objects.ObjectIterable;
/*     */ import it.unimi.dsi.fastutil.objects.ObjectIterator;
/*     */ import it.unimi.dsi.fastutil.objects.ObjectSet;
/*     */ import it.unimi.dsi.fastutil.objects.ObjectSets;
/*     */ import it.unimi.dsi.fastutil.objects.ObjectSpliterator;
/*     */ import it.unimi.dsi.fastutil.objects.ReferenceCollection;
/*     */ import it.unimi.dsi.fastutil.objects.ReferenceCollections;
/*     */ import it.unimi.dsi.fastutil.objects.ReferenceSets;
/*     */ import java.io.IOException;
/*     */ import java.io.ObjectOutputStream;
/*     */ import java.io.Serializable;
/*     */ import java.util.Collection;
/*     */ import java.util.Iterator;
/*     */ import java.util.Map;
/*     */ import java.util.Set;
/*     */ import java.util.Spliterator;
/*     */ import java.util.function.BiConsumer;
/*     */ import java.util.function.BiFunction;
/*     */ import java.util.function.Consumer;
/*     */ import java.util.function.Function;
/*     */ import java.util.function.IntFunction;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public final class Char2ReferenceMaps
/*     */ {
/*     */   public static <V> ObjectIterator<Char2ReferenceMap.Entry<V>> fastIterator(Char2ReferenceMap<V> map) {
/*  49 */     ObjectSet<Char2ReferenceMap.Entry<V>> entries = map.char2ReferenceEntrySet();
/*  50 */     return (entries instanceof Char2ReferenceMap.FastEntrySet) ? ((Char2ReferenceMap.FastEntrySet)entries).fastIterator() : entries.iterator();
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
/*     */   public static <V> void fastForEach(Char2ReferenceMap<V> map, Consumer<? super Char2ReferenceMap.Entry<V>> consumer) {
/*  65 */     ObjectSet<Char2ReferenceMap.Entry<V>> entries = map.char2ReferenceEntrySet();
/*  66 */     if (entries instanceof Char2ReferenceMap.FastEntrySet) { ((Char2ReferenceMap.FastEntrySet)entries).fastForEach(consumer); }
/*  67 */     else { entries.forEach(consumer); }
/*     */   
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
/*     */   public static <V> ObjectIterable<Char2ReferenceMap.Entry<V>> fastIterable(Char2ReferenceMap<V> map) {
/*  82 */     final ObjectSet<Char2ReferenceMap.Entry<V>> entries = map.char2ReferenceEntrySet();
/*  83 */     return (entries instanceof Char2ReferenceMap.FastEntrySet) ? new ObjectIterable<Char2ReferenceMap.Entry<V>>()
/*     */       {
/*     */         public ObjectIterator<Char2ReferenceMap.Entry<V>> iterator() {
/*  86 */           return ((Char2ReferenceMap.FastEntrySet<V>)entries).fastIterator();
/*     */         }
/*     */ 
/*     */         
/*     */         public ObjectSpliterator<Char2ReferenceMap.Entry<V>> spliterator() {
/*  91 */           return entries.spliterator();
/*     */         }
/*     */ 
/*     */         
/*     */         public void forEach(Consumer<? super Char2ReferenceMap.Entry<V>> consumer) {
/*  96 */           ((Char2ReferenceMap.FastEntrySet<V>)entries).fastForEach(consumer);
/*     */         }
/*  98 */       } : (ObjectIterable<Char2ReferenceMap.Entry<V>>)entries;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static class EmptyMap<V>
/*     */     extends Char2ReferenceFunctions.EmptyFunction<V>
/*     */     implements Char2ReferenceMap<V>, Serializable, Cloneable
/*     */   {
/*     */     private static final long serialVersionUID = -7046029254386353129L;
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public boolean containsValue(Object v) {
/* 115 */       return false;
/*     */     }
/*     */ 
/*     */     
/*     */     @Deprecated
/*     */     public V getOrDefault(Object key, V defaultValue) {
/* 121 */       return defaultValue;
/*     */     }
/*     */ 
/*     */     
/*     */     public V getOrDefault(char key, V defaultValue) {
/* 126 */       return defaultValue;
/*     */     }
/*     */ 
/*     */     
/*     */     public void putAll(Map<? extends Character, ? extends V> m) {
/* 131 */       throw new UnsupportedOperationException();
/*     */     }
/*     */ 
/*     */     
/*     */     public ObjectSet<Char2ReferenceMap.Entry<V>> char2ReferenceEntrySet() {
/* 136 */       return (ObjectSet<Char2ReferenceMap.Entry<V>>)ObjectSets.EMPTY_SET;
/*     */     }
/*     */ 
/*     */     
/*     */     public CharSet keySet() {
/* 141 */       return CharSets.EMPTY_SET;
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     public ReferenceCollection<V> values() {
/* 147 */       return (ReferenceCollection<V>)ReferenceSets.EMPTY_SET;
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     public void forEach(BiConsumer<? super Character, ? super V> consumer) {}
/*     */ 
/*     */     
/*     */     public Object clone() {
/* 156 */       return Char2ReferenceMaps.EMPTY_MAP;
/*     */     }
/*     */ 
/*     */     
/*     */     public boolean isEmpty() {
/* 161 */       return true;
/*     */     }
/*     */ 
/*     */     
/*     */     public int hashCode() {
/* 166 */       return 0;
/*     */     }
/*     */ 
/*     */     
/*     */     public boolean equals(Object o) {
/* 171 */       if (!(o instanceof Map)) return false; 
/* 172 */       return ((Map)o).isEmpty();
/*     */     }
/*     */ 
/*     */     
/*     */     public String toString() {
/* 177 */       return "{}";
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 185 */   public static final EmptyMap EMPTY_MAP = new EmptyMap();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static <V> Char2ReferenceMap<V> emptyMap() {
/* 197 */     return EMPTY_MAP;
/*     */   }
/*     */ 
/*     */   
/*     */   public static class Singleton<V>
/*     */     extends Char2ReferenceFunctions.Singleton<V>
/*     */     implements Char2ReferenceMap<V>, Serializable, Cloneable
/*     */   {
/*     */     private static final long serialVersionUID = -7046029254386353129L;
/*     */     
/*     */     protected transient ObjectSet<Char2ReferenceMap.Entry<V>> entries;
/*     */     
/*     */     protected transient CharSet keys;
/*     */     protected transient ReferenceCollection<V> values;
/*     */     
/*     */     protected Singleton(char key, V value) {
/* 213 */       super(key, value);
/*     */     }
/*     */ 
/*     */     
/*     */     public boolean containsValue(Object v) {
/* 218 */       return (this.value == v);
/*     */     }
/*     */ 
/*     */     
/*     */     public void putAll(Map<? extends Character, ? extends V> m) {
/* 223 */       throw new UnsupportedOperationException();
/*     */     }
/*     */ 
/*     */     
/*     */     public ObjectSet<Char2ReferenceMap.Entry<V>> char2ReferenceEntrySet() {
/* 228 */       if (this.entries == null) this.entries = ObjectSets.singleton(new AbstractChar2ReferenceMap.BasicEntry<>(this.key, this.value)); 
/* 229 */       return this.entries;
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
/*     */     public ObjectSet<Map.Entry<Character, V>> entrySet() {
/* 241 */       return (ObjectSet)char2ReferenceEntrySet();
/*     */     }
/*     */ 
/*     */     
/*     */     public CharSet keySet() {
/* 246 */       if (this.keys == null) this.keys = CharSets.singleton(this.key); 
/* 247 */       return this.keys;
/*     */     }
/*     */ 
/*     */     
/*     */     public ReferenceCollection<V> values() {
/* 252 */       if (this.values == null) this.values = (ReferenceCollection<V>)ReferenceSets.singleton(this.value); 
/* 253 */       return this.values;
/*     */     }
/*     */ 
/*     */     
/*     */     public boolean isEmpty() {
/* 258 */       return false;
/*     */     }
/*     */ 
/*     */     
/*     */     public int hashCode() {
/* 263 */       return this.key ^ ((this.value == null) ? 0 : System.identityHashCode(this.value));
/*     */     }
/*     */ 
/*     */     
/*     */     public boolean equals(Object o) {
/* 268 */       if (o == this) return true; 
/* 269 */       if (!(o instanceof Map)) return false; 
/* 270 */       Map<?, ?> m = (Map<?, ?>)o;
/* 271 */       if (m.size() != 1) return false; 
/* 272 */       return ((Map.Entry)m.entrySet().iterator().next()).equals(entrySet().iterator().next());
/*     */     }
/*     */ 
/*     */     
/*     */     public String toString() {
/* 277 */       return "{" + this.key + "=>" + this.value + "}";
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
/*     */   public static <V> Char2ReferenceMap<V> singleton(char key, V value) {
/* 293 */     return new Singleton<>(key, value);
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
/*     */   public static <V> Char2ReferenceMap<V> singleton(Character key, V value) {
/* 308 */     return new Singleton<>(key.charValue(), value);
/*     */   }
/*     */   
/*     */   public static class SynchronizedMap<V>
/*     */     extends Char2ReferenceFunctions.SynchronizedFunction<V> implements Char2ReferenceMap<V>, Serializable {
/*     */     private static final long serialVersionUID = -7046029254386353129L;
/*     */     protected final Char2ReferenceMap<V> map;
/*     */     protected transient ObjectSet<Char2ReferenceMap.Entry<V>> entries;
/*     */     protected transient CharSet keys;
/*     */     protected transient ReferenceCollection<V> values;
/*     */     
/*     */     protected SynchronizedMap(Char2ReferenceMap<V> m, Object sync) {
/* 320 */       super(m, sync);
/* 321 */       this.map = m;
/*     */     }
/*     */     
/*     */     protected SynchronizedMap(Char2ReferenceMap<V> m) {
/* 325 */       super(m);
/* 326 */       this.map = m;
/*     */     }
/*     */ 
/*     */     
/*     */     public boolean containsValue(Object v) {
/* 331 */       synchronized (this.sync) {
/* 332 */         return this.map.containsValue(v);
/*     */       } 
/*     */     }
/*     */ 
/*     */     
/*     */     public void putAll(Map<? extends Character, ? extends V> m) {
/* 338 */       synchronized (this.sync) {
/* 339 */         this.map.putAll(m);
/*     */       } 
/*     */     }
/*     */ 
/*     */     
/*     */     public ObjectSet<Char2ReferenceMap.Entry<V>> char2ReferenceEntrySet() {
/* 345 */       synchronized (this.sync) {
/* 346 */         if (this.entries == null) this.entries = ObjectSets.synchronize(this.map.char2ReferenceEntrySet(), this.sync); 
/* 347 */         return this.entries;
/*     */       } 
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
/*     */     public ObjectSet<Map.Entry<Character, V>> entrySet() {
/* 360 */       return (ObjectSet)char2ReferenceEntrySet();
/*     */     }
/*     */ 
/*     */     
/*     */     public CharSet keySet() {
/* 365 */       synchronized (this.sync) {
/* 366 */         if (this.keys == null) this.keys = CharSets.synchronize(this.map.keySet(), this.sync); 
/* 367 */         return this.keys;
/*     */       } 
/*     */     }
/*     */ 
/*     */     
/*     */     public ReferenceCollection<V> values() {
/* 373 */       synchronized (this.sync) {
/* 374 */         if (this.values == null) this.values = ReferenceCollections.synchronize(this.map.values(), this.sync); 
/* 375 */         return this.values;
/*     */       } 
/*     */     }
/*     */ 
/*     */     
/*     */     public boolean isEmpty() {
/* 381 */       synchronized (this.sync) {
/* 382 */         return this.map.isEmpty();
/*     */       } 
/*     */     }
/*     */ 
/*     */     
/*     */     public int hashCode() {
/* 388 */       synchronized (this.sync) {
/* 389 */         return this.map.hashCode();
/*     */       } 
/*     */     }
/*     */ 
/*     */     
/*     */     public boolean equals(Object o) {
/* 395 */       if (o == this) return true; 
/* 396 */       synchronized (this.sync) {
/* 397 */         return this.map.equals(o);
/*     */       } 
/*     */     }
/*     */     
/*     */     private void writeObject(ObjectOutputStream s) throws IOException {
/* 402 */       synchronized (this.sync) {
/* 403 */         s.defaultWriteObject();
/*     */       } 
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     public V getOrDefault(char key, V defaultValue) {
/* 410 */       synchronized (this.sync) {
/* 411 */         return this.map.getOrDefault(key, defaultValue);
/*     */       } 
/*     */     }
/*     */ 
/*     */     
/*     */     public void forEach(BiConsumer<? super Character, ? super V> action) {
/* 417 */       synchronized (this.sync) {
/* 418 */         this.map.forEach(action);
/*     */       } 
/*     */     }
/*     */ 
/*     */     
/*     */     public void replaceAll(BiFunction<? super Character, ? super V, ? extends V> function) {
/* 424 */       synchronized (this.sync) {
/* 425 */         this.map.replaceAll(function);
/*     */       } 
/*     */     }
/*     */ 
/*     */     
/*     */     public V putIfAbsent(char key, V value) {
/* 431 */       synchronized (this.sync) {
/* 432 */         return this.map.putIfAbsent(key, value);
/*     */       } 
/*     */     }
/*     */ 
/*     */     
/*     */     public boolean remove(char key, Object value) {
/* 438 */       synchronized (this.sync) {
/* 439 */         return this.map.remove(key, value);
/*     */       } 
/*     */     }
/*     */ 
/*     */     
/*     */     public V replace(char key, V value) {
/* 445 */       synchronized (this.sync) {
/* 446 */         return this.map.replace(key, value);
/*     */       } 
/*     */     }
/*     */ 
/*     */     
/*     */     public boolean replace(char key, V oldValue, V newValue) {
/* 452 */       synchronized (this.sync) {
/* 453 */         return this.map.replace(key, oldValue, newValue);
/*     */       } 
/*     */     }
/*     */ 
/*     */     
/*     */     public V computeIfAbsent(char key, IntFunction<? extends V> mappingFunction) {
/* 459 */       synchronized (this.sync) {
/* 460 */         return this.map.computeIfAbsent(key, mappingFunction);
/*     */       } 
/*     */     }
/*     */ 
/*     */     
/*     */     public V computeIfAbsent(char key, Char2ReferenceFunction<? extends V> mappingFunction) {
/* 466 */       synchronized (this.sync) {
/* 467 */         return this.map.computeIfAbsent(key, mappingFunction);
/*     */       } 
/*     */     }
/*     */ 
/*     */     
/*     */     public V computeIfPresent(char key, BiFunction<? super Character, ? super V, ? extends V> remappingFunction) {
/* 473 */       synchronized (this.sync) {
/* 474 */         return this.map.computeIfPresent(key, remappingFunction);
/*     */       } 
/*     */     }
/*     */ 
/*     */     
/*     */     public V compute(char key, BiFunction<? super Character, ? super V, ? extends V> remappingFunction) {
/* 480 */       synchronized (this.sync) {
/* 481 */         return this.map.compute(key, remappingFunction);
/*     */       } 
/*     */     }
/*     */ 
/*     */     
/*     */     public V merge(char key, V value, BiFunction<? super V, ? super V, ? extends V> remappingFunction) {
/* 487 */       synchronized (this.sync) {
/* 488 */         return this.map.merge(key, value, remappingFunction);
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
/*     */     public V getOrDefault(Object key, V defaultValue) {
/* 500 */       synchronized (this.sync) {
/* 501 */         return this.map.getOrDefault(key, defaultValue);
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
/*     */     public boolean remove(Object key, Object value) {
/* 513 */       synchronized (this.sync) {
/* 514 */         return this.map.remove(key, value);
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
/*     */     public V replace(Character key, V value) {
/* 526 */       synchronized (this.sync) {
/* 527 */         return this.map.replace(key, value);
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
/*     */     public boolean replace(Character key, V oldValue, V newValue) {
/* 539 */       synchronized (this.sync) {
/* 540 */         return this.map.replace(key, oldValue, newValue);
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
/*     */     public V putIfAbsent(Character key, V value) {
/* 552 */       synchronized (this.sync) {
/* 553 */         return this.map.putIfAbsent(key, value);
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
/*     */     public V computeIfAbsent(Character key, Function<? super Character, ? extends V> mappingFunction) {
/* 565 */       synchronized (this.sync) {
/* 566 */         return this.map.computeIfAbsent(key, mappingFunction);
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
/*     */     public V computeIfPresent(Character key, BiFunction<? super Character, ? super V, ? extends V> remappingFunction) {
/* 578 */       synchronized (this.sync) {
/* 579 */         return this.map.computeIfPresent(key, remappingFunction);
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
/*     */     public V compute(Character key, BiFunction<? super Character, ? super V, ? extends V> remappingFunction) {
/* 591 */       synchronized (this.sync) {
/* 592 */         return this.map.compute(key, remappingFunction);
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
/*     */     public V merge(Character key, V value, BiFunction<? super V, ? super V, ? extends V> remappingFunction) {
/* 604 */       synchronized (this.sync) {
/* 605 */         return this.map.merge(key, value, remappingFunction);
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
/*     */   public static <V> Char2ReferenceMap<V> synchronize(Char2ReferenceMap<V> m) {
/* 618 */     return new SynchronizedMap<>(m);
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
/*     */   public static <V> Char2ReferenceMap<V> synchronize(Char2ReferenceMap<V> m, Object sync) {
/* 631 */     return new SynchronizedMap<>(m, sync);
/*     */   }
/*     */   
/*     */   public static class UnmodifiableMap<V>
/*     */     extends Char2ReferenceFunctions.UnmodifiableFunction<V> implements Char2ReferenceMap<V>, Serializable {
/*     */     private static final long serialVersionUID = -7046029254386353129L;
/*     */     protected final Char2ReferenceMap<? extends V> map;
/*     */     protected transient ObjectSet<Char2ReferenceMap.Entry<V>> entries;
/*     */     protected transient CharSet keys;
/*     */     protected transient ReferenceCollection<V> values;
/*     */     
/*     */     protected UnmodifiableMap(Char2ReferenceMap<? extends V> m) {
/* 643 */       super(m);
/* 644 */       this.map = m;
/*     */     }
/*     */ 
/*     */     
/*     */     public boolean containsValue(Object v) {
/* 649 */       return this.map.containsValue(v);
/*     */     }
/*     */ 
/*     */     
/*     */     public void putAll(Map<? extends Character, ? extends V> m) {
/* 654 */       throw new UnsupportedOperationException();
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     public ObjectSet<Char2ReferenceMap.Entry<V>> char2ReferenceEntrySet() {
/* 660 */       if (this.entries == null) this.entries = ObjectSets.unmodifiable(this.map.char2ReferenceEntrySet()); 
/* 661 */       return this.entries;
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
/*     */     public ObjectSet<Map.Entry<Character, V>> entrySet() {
/* 673 */       return (ObjectSet)char2ReferenceEntrySet();
/*     */     }
/*     */ 
/*     */     
/*     */     public CharSet keySet() {
/* 678 */       if (this.keys == null) this.keys = CharSets.unmodifiable(this.map.keySet()); 
/* 679 */       return this.keys;
/*     */     }
/*     */ 
/*     */     
/*     */     public ReferenceCollection<V> values() {
/* 684 */       if (this.values == null) this.values = ReferenceCollections.unmodifiable(this.map.values()); 
/* 685 */       return this.values;
/*     */     }
/*     */ 
/*     */     
/*     */     public boolean isEmpty() {
/* 690 */       return this.map.isEmpty();
/*     */     }
/*     */ 
/*     */     
/*     */     public int hashCode() {
/* 695 */       return this.map.hashCode();
/*     */     }
/*     */ 
/*     */     
/*     */     public boolean equals(Object o) {
/* 700 */       if (o == this) return true; 
/* 701 */       return this.map.equals(o);
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public V getOrDefault(char key, V defaultValue) {
/* 708 */       return this.map.getOrDefault(key, defaultValue);
/*     */     }
/*     */ 
/*     */     
/*     */     public void forEach(BiConsumer<? super Character, ? super V> action) {
/* 713 */       this.map.forEach(action);
/*     */     }
/*     */ 
/*     */     
/*     */     public void replaceAll(BiFunction<? super Character, ? super V, ? extends V> function) {
/* 718 */       throw new UnsupportedOperationException();
/*     */     }
/*     */ 
/*     */     
/*     */     public V putIfAbsent(char key, V value) {
/* 723 */       throw new UnsupportedOperationException();
/*     */     }
/*     */ 
/*     */     
/*     */     public boolean remove(char key, Object value) {
/* 728 */       throw new UnsupportedOperationException();
/*     */     }
/*     */ 
/*     */     
/*     */     public V replace(char key, V value) {
/* 733 */       throw new UnsupportedOperationException();
/*     */     }
/*     */ 
/*     */     
/*     */     public boolean replace(char key, V oldValue, V newValue) {
/* 738 */       throw new UnsupportedOperationException();
/*     */     }
/*     */ 
/*     */     
/*     */     public V computeIfAbsent(char key, IntFunction<? extends V> mappingFunction) {
/* 743 */       throw new UnsupportedOperationException();
/*     */     }
/*     */ 
/*     */     
/*     */     public V computeIfAbsent(char key, Char2ReferenceFunction<? extends V> mappingFunction) {
/* 748 */       throw new UnsupportedOperationException();
/*     */     }
/*     */ 
/*     */     
/*     */     public V computeIfPresent(char key, BiFunction<? super Character, ? super V, ? extends V> remappingFunction) {
/* 753 */       throw new UnsupportedOperationException();
/*     */     }
/*     */ 
/*     */     
/*     */     public V compute(char key, BiFunction<? super Character, ? super V, ? extends V> remappingFunction) {
/* 758 */       throw new UnsupportedOperationException();
/*     */     }
/*     */ 
/*     */     
/*     */     public V merge(char key, V value, BiFunction<? super V, ? super V, ? extends V> remappingFunction) {
/* 763 */       throw new UnsupportedOperationException();
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
/*     */     public V getOrDefault(Object key, V defaultValue) {
/* 775 */       return this.map.getOrDefault(key, defaultValue);
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     @Deprecated
/*     */     public boolean remove(Object key, Object value) {
/* 786 */       throw new UnsupportedOperationException();
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     @Deprecated
/*     */     public V replace(Character key, V value) {
/* 797 */       throw new UnsupportedOperationException();
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     @Deprecated
/*     */     public boolean replace(Character key, V oldValue, V newValue) {
/* 808 */       throw new UnsupportedOperationException();
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     @Deprecated
/*     */     public V putIfAbsent(Character key, V value) {
/* 819 */       throw new UnsupportedOperationException();
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     @Deprecated
/*     */     public V computeIfAbsent(Character key, Function<? super Character, ? extends V> mappingFunction) {
/* 830 */       throw new UnsupportedOperationException();
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     @Deprecated
/*     */     public V computeIfPresent(Character key, BiFunction<? super Character, ? super V, ? extends V> remappingFunction) {
/* 841 */       throw new UnsupportedOperationException();
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     @Deprecated
/*     */     public V compute(Character key, BiFunction<? super Character, ? super V, ? extends V> remappingFunction) {
/* 852 */       throw new UnsupportedOperationException();
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     @Deprecated
/*     */     public V merge(Character key, V value, BiFunction<? super V, ? super V, ? extends V> remappingFunction) {
/* 863 */       throw new UnsupportedOperationException();
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
/*     */   public static <V> Char2ReferenceMap<V> unmodifiable(Char2ReferenceMap<? extends V> m) {
/* 875 */     return new UnmodifiableMap<>(m);
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\i\\unimi\dsi\fastutil\chars\Char2ReferenceMaps.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */