/*     */ package it.unimi.dsi.fastutil.ints;
/*     */ 
/*     */ import it.unimi.dsi.fastutil.objects.ObjectCollection;
/*     */ import it.unimi.dsi.fastutil.objects.ObjectCollections;
/*     */ import it.unimi.dsi.fastutil.objects.ObjectIterable;
/*     */ import it.unimi.dsi.fastutil.objects.ObjectIterator;
/*     */ import it.unimi.dsi.fastutil.objects.ObjectSet;
/*     */ import it.unimi.dsi.fastutil.objects.ObjectSets;
/*     */ import it.unimi.dsi.fastutil.objects.ObjectSpliterator;
/*     */ import java.io.IOException;
/*     */ import java.io.ObjectOutputStream;
/*     */ import java.io.Serializable;
/*     */ import java.util.Collection;
/*     */ import java.util.Iterator;
/*     */ import java.util.Map;
/*     */ import java.util.Objects;
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
/*     */ public final class Int2ObjectMaps
/*     */ {
/*     */   public static <V> ObjectIterator<Int2ObjectMap.Entry<V>> fastIterator(Int2ObjectMap<V> map) {
/*  48 */     ObjectSet<Int2ObjectMap.Entry<V>> entries = map.int2ObjectEntrySet();
/*  49 */     return (entries instanceof Int2ObjectMap.FastEntrySet) ? ((Int2ObjectMap.FastEntrySet)entries).fastIterator() : entries.iterator();
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
/*     */   public static <V> void fastForEach(Int2ObjectMap<V> map, Consumer<? super Int2ObjectMap.Entry<V>> consumer) {
/*  64 */     ObjectSet<Int2ObjectMap.Entry<V>> entries = map.int2ObjectEntrySet();
/*  65 */     if (entries instanceof Int2ObjectMap.FastEntrySet) { ((Int2ObjectMap.FastEntrySet)entries).fastForEach(consumer); }
/*  66 */     else { entries.forEach(consumer); }
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
/*     */   public static <V> ObjectIterable<Int2ObjectMap.Entry<V>> fastIterable(Int2ObjectMap<V> map) {
/*  81 */     final ObjectSet<Int2ObjectMap.Entry<V>> entries = map.int2ObjectEntrySet();
/*  82 */     return (entries instanceof Int2ObjectMap.FastEntrySet) ? new ObjectIterable<Int2ObjectMap.Entry<V>>()
/*     */       {
/*     */         public ObjectIterator<Int2ObjectMap.Entry<V>> iterator() {
/*  85 */           return ((Int2ObjectMap.FastEntrySet<V>)entries).fastIterator();
/*     */         }
/*     */ 
/*     */         
/*     */         public ObjectSpliterator<Int2ObjectMap.Entry<V>> spliterator() {
/*  90 */           return entries.spliterator();
/*     */         }
/*     */ 
/*     */         
/*     */         public void forEach(Consumer<? super Int2ObjectMap.Entry<V>> consumer) {
/*  95 */           ((Int2ObjectMap.FastEntrySet<V>)entries).fastForEach(consumer);
/*     */         }
/*  97 */       } : (ObjectIterable<Int2ObjectMap.Entry<V>>)entries;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static class EmptyMap<V>
/*     */     extends Int2ObjectFunctions.EmptyFunction<V>
/*     */     implements Int2ObjectMap<V>, Serializable, Cloneable
/*     */   {
/*     */     private static final long serialVersionUID = -7046029254386353129L;
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public boolean containsValue(Object v) {
/* 114 */       return false;
/*     */     }
/*     */ 
/*     */     
/*     */     @Deprecated
/*     */     public V getOrDefault(Object key, V defaultValue) {
/* 120 */       return defaultValue;
/*     */     }
/*     */ 
/*     */     
/*     */     public V getOrDefault(int key, V defaultValue) {
/* 125 */       return defaultValue;
/*     */     }
/*     */ 
/*     */     
/*     */     public void putAll(Map<? extends Integer, ? extends V> m) {
/* 130 */       throw new UnsupportedOperationException();
/*     */     }
/*     */ 
/*     */     
/*     */     public ObjectSet<Int2ObjectMap.Entry<V>> int2ObjectEntrySet() {
/* 135 */       return (ObjectSet<Int2ObjectMap.Entry<V>>)ObjectSets.EMPTY_SET;
/*     */     }
/*     */ 
/*     */     
/*     */     public IntSet keySet() {
/* 140 */       return IntSets.EMPTY_SET;
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     public ObjectCollection<V> values() {
/* 146 */       return (ObjectCollection<V>)ObjectSets.EMPTY_SET;
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     public void forEach(BiConsumer<? super Integer, ? super V> consumer) {}
/*     */ 
/*     */     
/*     */     public Object clone() {
/* 155 */       return Int2ObjectMaps.EMPTY_MAP;
/*     */     }
/*     */ 
/*     */     
/*     */     public boolean isEmpty() {
/* 160 */       return true;
/*     */     }
/*     */ 
/*     */     
/*     */     public int hashCode() {
/* 165 */       return 0;
/*     */     }
/*     */ 
/*     */     
/*     */     public boolean equals(Object o) {
/* 170 */       if (!(o instanceof Map)) return false; 
/* 171 */       return ((Map)o).isEmpty();
/*     */     }
/*     */ 
/*     */     
/*     */     public String toString() {
/* 176 */       return "{}";
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 184 */   public static final EmptyMap EMPTY_MAP = new EmptyMap();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static <V> Int2ObjectMap<V> emptyMap() {
/* 196 */     return EMPTY_MAP;
/*     */   }
/*     */ 
/*     */   
/*     */   public static class Singleton<V>
/*     */     extends Int2ObjectFunctions.Singleton<V>
/*     */     implements Int2ObjectMap<V>, Serializable, Cloneable
/*     */   {
/*     */     private static final long serialVersionUID = -7046029254386353129L;
/*     */     
/*     */     protected transient ObjectSet<Int2ObjectMap.Entry<V>> entries;
/*     */     
/*     */     protected transient IntSet keys;
/*     */     protected transient ObjectCollection<V> values;
/*     */     
/*     */     protected Singleton(int key, V value) {
/* 212 */       super(key, value);
/*     */     }
/*     */ 
/*     */     
/*     */     public boolean containsValue(Object v) {
/* 217 */       return Objects.equals(this.value, v);
/*     */     }
/*     */ 
/*     */     
/*     */     public void putAll(Map<? extends Integer, ? extends V> m) {
/* 222 */       throw new UnsupportedOperationException();
/*     */     }
/*     */ 
/*     */     
/*     */     public ObjectSet<Int2ObjectMap.Entry<V>> int2ObjectEntrySet() {
/* 227 */       if (this.entries == null) this.entries = ObjectSets.singleton(new AbstractInt2ObjectMap.BasicEntry<>(this.key, this.value)); 
/* 228 */       return this.entries;
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
/*     */     public ObjectSet<Map.Entry<Integer, V>> entrySet() {
/* 240 */       return (ObjectSet)int2ObjectEntrySet();
/*     */     }
/*     */ 
/*     */     
/*     */     public IntSet keySet() {
/* 245 */       if (this.keys == null) this.keys = IntSets.singleton(this.key); 
/* 246 */       return this.keys;
/*     */     }
/*     */ 
/*     */     
/*     */     public ObjectCollection<V> values() {
/* 251 */       if (this.values == null) this.values = (ObjectCollection<V>)ObjectSets.singleton(this.value); 
/* 252 */       return this.values;
/*     */     }
/*     */ 
/*     */     
/*     */     public boolean isEmpty() {
/* 257 */       return false;
/*     */     }
/*     */ 
/*     */     
/*     */     public int hashCode() {
/* 262 */       return this.key ^ ((this.value == null) ? 0 : this.value.hashCode());
/*     */     }
/*     */ 
/*     */     
/*     */     public boolean equals(Object o) {
/* 267 */       if (o == this) return true; 
/* 268 */       if (!(o instanceof Map)) return false; 
/* 269 */       Map<?, ?> m = (Map<?, ?>)o;
/* 270 */       if (m.size() != 1) return false; 
/* 271 */       return ((Map.Entry)m.entrySet().iterator().next()).equals(entrySet().iterator().next());
/*     */     }
/*     */ 
/*     */     
/*     */     public String toString() {
/* 276 */       return "{" + this.key + "=>" + this.value + "}";
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
/*     */   public static <V> Int2ObjectMap<V> singleton(int key, V value) {
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
/*     */   public static <V> Int2ObjectMap<V> singleton(Integer key, V value) {
/* 307 */     return new Singleton<>(key.intValue(), value);
/*     */   }
/*     */   
/*     */   public static class SynchronizedMap<V>
/*     */     extends Int2ObjectFunctions.SynchronizedFunction<V> implements Int2ObjectMap<V>, Serializable {
/*     */     private static final long serialVersionUID = -7046029254386353129L;
/*     */     protected final Int2ObjectMap<V> map;
/*     */     protected transient ObjectSet<Int2ObjectMap.Entry<V>> entries;
/*     */     protected transient IntSet keys;
/*     */     protected transient ObjectCollection<V> values;
/*     */     
/*     */     protected SynchronizedMap(Int2ObjectMap<V> m, Object sync) {
/* 319 */       super(m, sync);
/* 320 */       this.map = m;
/*     */     }
/*     */     
/*     */     protected SynchronizedMap(Int2ObjectMap<V> m) {
/* 324 */       super(m);
/* 325 */       this.map = m;
/*     */     }
/*     */ 
/*     */     
/*     */     public boolean containsValue(Object v) {
/* 330 */       synchronized (this.sync) {
/* 331 */         return this.map.containsValue(v);
/*     */       } 
/*     */     }
/*     */ 
/*     */     
/*     */     public void putAll(Map<? extends Integer, ? extends V> m) {
/* 337 */       synchronized (this.sync) {
/* 338 */         this.map.putAll(m);
/*     */       } 
/*     */     }
/*     */ 
/*     */     
/*     */     public ObjectSet<Int2ObjectMap.Entry<V>> int2ObjectEntrySet() {
/* 344 */       synchronized (this.sync) {
/* 345 */         if (this.entries == null) this.entries = ObjectSets.synchronize(this.map.int2ObjectEntrySet(), this.sync); 
/* 346 */         return this.entries;
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
/*     */     public ObjectSet<Map.Entry<Integer, V>> entrySet() {
/* 359 */       return (ObjectSet)int2ObjectEntrySet();
/*     */     }
/*     */ 
/*     */     
/*     */     public IntSet keySet() {
/* 364 */       synchronized (this.sync) {
/* 365 */         if (this.keys == null) this.keys = IntSets.synchronize(this.map.keySet(), this.sync); 
/* 366 */         return this.keys;
/*     */       } 
/*     */     }
/*     */ 
/*     */     
/*     */     public ObjectCollection<V> values() {
/* 372 */       synchronized (this.sync) {
/* 373 */         if (this.values == null) this.values = ObjectCollections.synchronize(this.map.values(), this.sync); 
/* 374 */         return this.values;
/*     */       } 
/*     */     }
/*     */ 
/*     */     
/*     */     public boolean isEmpty() {
/* 380 */       synchronized (this.sync) {
/* 381 */         return this.map.isEmpty();
/*     */       } 
/*     */     }
/*     */ 
/*     */     
/*     */     public int hashCode() {
/* 387 */       synchronized (this.sync) {
/* 388 */         return this.map.hashCode();
/*     */       } 
/*     */     }
/*     */ 
/*     */     
/*     */     public boolean equals(Object o) {
/* 394 */       if (o == this) return true; 
/* 395 */       synchronized (this.sync) {
/* 396 */         return this.map.equals(o);
/*     */       } 
/*     */     }
/*     */     
/*     */     private void writeObject(ObjectOutputStream s) throws IOException {
/* 401 */       synchronized (this.sync) {
/* 402 */         s.defaultWriteObject();
/*     */       } 
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     public V getOrDefault(int key, V defaultValue) {
/* 409 */       synchronized (this.sync) {
/* 410 */         return this.map.getOrDefault(key, defaultValue);
/*     */       } 
/*     */     }
/*     */ 
/*     */     
/*     */     public void forEach(BiConsumer<? super Integer, ? super V> action) {
/* 416 */       synchronized (this.sync) {
/* 417 */         this.map.forEach(action);
/*     */       } 
/*     */     }
/*     */ 
/*     */     
/*     */     public void replaceAll(BiFunction<? super Integer, ? super V, ? extends V> function) {
/* 423 */       synchronized (this.sync) {
/* 424 */         this.map.replaceAll(function);
/*     */       } 
/*     */     }
/*     */ 
/*     */     
/*     */     public V putIfAbsent(int key, V value) {
/* 430 */       synchronized (this.sync) {
/* 431 */         return this.map.putIfAbsent(key, value);
/*     */       } 
/*     */     }
/*     */ 
/*     */     
/*     */     public boolean remove(int key, Object value) {
/* 437 */       synchronized (this.sync) {
/* 438 */         return this.map.remove(key, value);
/*     */       } 
/*     */     }
/*     */ 
/*     */     
/*     */     public V replace(int key, V value) {
/* 444 */       synchronized (this.sync) {
/* 445 */         return this.map.replace(key, value);
/*     */       } 
/*     */     }
/*     */ 
/*     */     
/*     */     public boolean replace(int key, V oldValue, V newValue) {
/* 451 */       synchronized (this.sync) {
/* 452 */         return this.map.replace(key, oldValue, newValue);
/*     */       } 
/*     */     }
/*     */ 
/*     */     
/*     */     public V computeIfAbsent(int key, IntFunction<? extends V> mappingFunction) {
/* 458 */       synchronized (this.sync) {
/* 459 */         return this.map.computeIfAbsent(key, mappingFunction);
/*     */       } 
/*     */     }
/*     */ 
/*     */     
/*     */     public V computeIfAbsent(int key, Int2ObjectFunction<? extends V> mappingFunction) {
/* 465 */       synchronized (this.sync) {
/* 466 */         return this.map.computeIfAbsent(key, mappingFunction);
/*     */       } 
/*     */     }
/*     */ 
/*     */     
/*     */     public V computeIfPresent(int key, BiFunction<? super Integer, ? super V, ? extends V> remappingFunction) {
/* 472 */       synchronized (this.sync) {
/* 473 */         return this.map.computeIfPresent(key, remappingFunction);
/*     */       } 
/*     */     }
/*     */ 
/*     */     
/*     */     public V compute(int key, BiFunction<? super Integer, ? super V, ? extends V> remappingFunction) {
/* 479 */       synchronized (this.sync) {
/* 480 */         return this.map.compute(key, remappingFunction);
/*     */       } 
/*     */     }
/*     */ 
/*     */     
/*     */     public V merge(int key, V value, BiFunction<? super V, ? super V, ? extends V> remappingFunction) {
/* 486 */       synchronized (this.sync) {
/* 487 */         return this.map.merge(key, value, remappingFunction);
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
/* 499 */       synchronized (this.sync) {
/* 500 */         return this.map.getOrDefault(key, defaultValue);
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
/* 512 */       synchronized (this.sync) {
/* 513 */         return this.map.remove(key, value);
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
/*     */     public V replace(Integer key, V value) {
/* 525 */       synchronized (this.sync) {
/* 526 */         return this.map.replace(key, value);
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
/*     */     public boolean replace(Integer key, V oldValue, V newValue) {
/* 538 */       synchronized (this.sync) {
/* 539 */         return this.map.replace(key, oldValue, newValue);
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
/*     */     public V putIfAbsent(Integer key, V value) {
/* 551 */       synchronized (this.sync) {
/* 552 */         return this.map.putIfAbsent(key, value);
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
/*     */     public V computeIfAbsent(Integer key, Function<? super Integer, ? extends V> mappingFunction) {
/* 564 */       synchronized (this.sync) {
/* 565 */         return this.map.computeIfAbsent(key, mappingFunction);
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
/*     */     public V computeIfPresent(Integer key, BiFunction<? super Integer, ? super V, ? extends V> remappingFunction) {
/* 577 */       synchronized (this.sync) {
/* 578 */         return this.map.computeIfPresent(key, remappingFunction);
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
/*     */     public V compute(Integer key, BiFunction<? super Integer, ? super V, ? extends V> remappingFunction) {
/* 590 */       synchronized (this.sync) {
/* 591 */         return this.map.compute(key, remappingFunction);
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
/*     */     public V merge(Integer key, V value, BiFunction<? super V, ? super V, ? extends V> remappingFunction) {
/* 603 */       synchronized (this.sync) {
/* 604 */         return this.map.merge(key, value, remappingFunction);
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
/*     */   public static <V> Int2ObjectMap<V> synchronize(Int2ObjectMap<V> m) {
/* 617 */     return new SynchronizedMap<>(m);
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
/*     */   public static <V> Int2ObjectMap<V> synchronize(Int2ObjectMap<V> m, Object sync) {
/* 630 */     return new SynchronizedMap<>(m, sync);
/*     */   }
/*     */   
/*     */   public static class UnmodifiableMap<V>
/*     */     extends Int2ObjectFunctions.UnmodifiableFunction<V> implements Int2ObjectMap<V>, Serializable {
/*     */     private static final long serialVersionUID = -7046029254386353129L;
/*     */     protected final Int2ObjectMap<? extends V> map;
/*     */     protected transient ObjectSet<Int2ObjectMap.Entry<V>> entries;
/*     */     protected transient IntSet keys;
/*     */     protected transient ObjectCollection<V> values;
/*     */     
/*     */     protected UnmodifiableMap(Int2ObjectMap<? extends V> m) {
/* 642 */       super(m);
/* 643 */       this.map = m;
/*     */     }
/*     */ 
/*     */     
/*     */     public boolean containsValue(Object v) {
/* 648 */       return this.map.containsValue(v);
/*     */     }
/*     */ 
/*     */     
/*     */     public void putAll(Map<? extends Integer, ? extends V> m) {
/* 653 */       throw new UnsupportedOperationException();
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     public ObjectSet<Int2ObjectMap.Entry<V>> int2ObjectEntrySet() {
/* 659 */       if (this.entries == null) this.entries = ObjectSets.unmodifiable(this.map.int2ObjectEntrySet()); 
/* 660 */       return this.entries;
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
/*     */     public ObjectSet<Map.Entry<Integer, V>> entrySet() {
/* 672 */       return (ObjectSet)int2ObjectEntrySet();
/*     */     }
/*     */ 
/*     */     
/*     */     public IntSet keySet() {
/* 677 */       if (this.keys == null) this.keys = IntSets.unmodifiable(this.map.keySet()); 
/* 678 */       return this.keys;
/*     */     }
/*     */ 
/*     */     
/*     */     public ObjectCollection<V> values() {
/* 683 */       if (this.values == null) this.values = ObjectCollections.unmodifiable(this.map.values()); 
/* 684 */       return this.values;
/*     */     }
/*     */ 
/*     */     
/*     */     public boolean isEmpty() {
/* 689 */       return this.map.isEmpty();
/*     */     }
/*     */ 
/*     */     
/*     */     public int hashCode() {
/* 694 */       return this.map.hashCode();
/*     */     }
/*     */ 
/*     */     
/*     */     public boolean equals(Object o) {
/* 699 */       if (o == this) return true; 
/* 700 */       return this.map.equals(o);
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public V getOrDefault(int key, V defaultValue) {
/* 707 */       return this.map.getOrDefault(key, defaultValue);
/*     */     }
/*     */ 
/*     */     
/*     */     public void forEach(BiConsumer<? super Integer, ? super V> action) {
/* 712 */       this.map.forEach(action);
/*     */     }
/*     */ 
/*     */     
/*     */     public void replaceAll(BiFunction<? super Integer, ? super V, ? extends V> function) {
/* 717 */       throw new UnsupportedOperationException();
/*     */     }
/*     */ 
/*     */     
/*     */     public V putIfAbsent(int key, V value) {
/* 722 */       throw new UnsupportedOperationException();
/*     */     }
/*     */ 
/*     */     
/*     */     public boolean remove(int key, Object value) {
/* 727 */       throw new UnsupportedOperationException();
/*     */     }
/*     */ 
/*     */     
/*     */     public V replace(int key, V value) {
/* 732 */       throw new UnsupportedOperationException();
/*     */     }
/*     */ 
/*     */     
/*     */     public boolean replace(int key, V oldValue, V newValue) {
/* 737 */       throw new UnsupportedOperationException();
/*     */     }
/*     */ 
/*     */     
/*     */     public V computeIfAbsent(int key, IntFunction<? extends V> mappingFunction) {
/* 742 */       throw new UnsupportedOperationException();
/*     */     }
/*     */ 
/*     */     
/*     */     public V computeIfAbsent(int key, Int2ObjectFunction<? extends V> mappingFunction) {
/* 747 */       throw new UnsupportedOperationException();
/*     */     }
/*     */ 
/*     */     
/*     */     public V computeIfPresent(int key, BiFunction<? super Integer, ? super V, ? extends V> remappingFunction) {
/* 752 */       throw new UnsupportedOperationException();
/*     */     }
/*     */ 
/*     */     
/*     */     public V compute(int key, BiFunction<? super Integer, ? super V, ? extends V> remappingFunction) {
/* 757 */       throw new UnsupportedOperationException();
/*     */     }
/*     */ 
/*     */     
/*     */     public V merge(int key, V value, BiFunction<? super V, ? super V, ? extends V> remappingFunction) {
/* 762 */       throw new UnsupportedOperationException();
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
/* 774 */       return this.map.getOrDefault(key, defaultValue);
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
/* 785 */       throw new UnsupportedOperationException();
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     @Deprecated
/*     */     public V replace(Integer key, V value) {
/* 796 */       throw new UnsupportedOperationException();
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     @Deprecated
/*     */     public boolean replace(Integer key, V oldValue, V newValue) {
/* 807 */       throw new UnsupportedOperationException();
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     @Deprecated
/*     */     public V putIfAbsent(Integer key, V value) {
/* 818 */       throw new UnsupportedOperationException();
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     @Deprecated
/*     */     public V computeIfAbsent(Integer key, Function<? super Integer, ? extends V> mappingFunction) {
/* 829 */       throw new UnsupportedOperationException();
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     @Deprecated
/*     */     public V computeIfPresent(Integer key, BiFunction<? super Integer, ? super V, ? extends V> remappingFunction) {
/* 840 */       throw new UnsupportedOperationException();
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     @Deprecated
/*     */     public V compute(Integer key, BiFunction<? super Integer, ? super V, ? extends V> remappingFunction) {
/* 851 */       throw new UnsupportedOperationException();
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     @Deprecated
/*     */     public V merge(Integer key, V value, BiFunction<? super V, ? super V, ? extends V> remappingFunction) {
/* 862 */       throw new UnsupportedOperationException();
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
/*     */   public static <V> Int2ObjectMap<V> unmodifiable(Int2ObjectMap<? extends V> m) {
/* 874 */     return new UnmodifiableMap<>(m);
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\i\\unimi\dsi\fastutil\ints\Int2ObjectMaps.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */