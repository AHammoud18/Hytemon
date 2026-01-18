/*     */ package it.unimi.dsi.fastutil.doubles;
/*     */ 
/*     */ import it.unimi.dsi.fastutil.HashCommon;
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
/*     */ import java.util.function.DoubleFunction;
/*     */ import java.util.function.Function;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public final class Double2ObjectMaps
/*     */ {
/*     */   public static <V> ObjectIterator<Double2ObjectMap.Entry<V>> fastIterator(Double2ObjectMap<V> map) {
/*  48 */     ObjectSet<Double2ObjectMap.Entry<V>> entries = map.double2ObjectEntrySet();
/*  49 */     return (entries instanceof Double2ObjectMap.FastEntrySet) ? ((Double2ObjectMap.FastEntrySet)entries).fastIterator() : entries.iterator();
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
/*     */   public static <V> void fastForEach(Double2ObjectMap<V> map, Consumer<? super Double2ObjectMap.Entry<V>> consumer) {
/*  64 */     ObjectSet<Double2ObjectMap.Entry<V>> entries = map.double2ObjectEntrySet();
/*  65 */     if (entries instanceof Double2ObjectMap.FastEntrySet) { ((Double2ObjectMap.FastEntrySet)entries).fastForEach(consumer); }
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
/*     */   public static <V> ObjectIterable<Double2ObjectMap.Entry<V>> fastIterable(Double2ObjectMap<V> map) {
/*  81 */     final ObjectSet<Double2ObjectMap.Entry<V>> entries = map.double2ObjectEntrySet();
/*  82 */     return (entries instanceof Double2ObjectMap.FastEntrySet) ? new ObjectIterable<Double2ObjectMap.Entry<V>>()
/*     */       {
/*     */         public ObjectIterator<Double2ObjectMap.Entry<V>> iterator() {
/*  85 */           return ((Double2ObjectMap.FastEntrySet<V>)entries).fastIterator();
/*     */         }
/*     */ 
/*     */         
/*     */         public ObjectSpliterator<Double2ObjectMap.Entry<V>> spliterator() {
/*  90 */           return entries.spliterator();
/*     */         }
/*     */ 
/*     */         
/*     */         public void forEach(Consumer<? super Double2ObjectMap.Entry<V>> consumer) {
/*  95 */           ((Double2ObjectMap.FastEntrySet<V>)entries).fastForEach(consumer);
/*     */         }
/*  97 */       } : (ObjectIterable<Double2ObjectMap.Entry<V>>)entries;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static class EmptyMap<V>
/*     */     extends Double2ObjectFunctions.EmptyFunction<V>
/*     */     implements Double2ObjectMap<V>, Serializable, Cloneable
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
/*     */     public V getOrDefault(double key, V defaultValue) {
/* 125 */       return defaultValue;
/*     */     }
/*     */ 
/*     */     
/*     */     public void putAll(Map<? extends Double, ? extends V> m) {
/* 130 */       throw new UnsupportedOperationException();
/*     */     }
/*     */ 
/*     */     
/*     */     public ObjectSet<Double2ObjectMap.Entry<V>> double2ObjectEntrySet() {
/* 135 */       return (ObjectSet<Double2ObjectMap.Entry<V>>)ObjectSets.EMPTY_SET;
/*     */     }
/*     */ 
/*     */     
/*     */     public DoubleSet keySet() {
/* 140 */       return DoubleSets.EMPTY_SET;
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
/*     */     public void forEach(BiConsumer<? super Double, ? super V> consumer) {}
/*     */ 
/*     */     
/*     */     public Object clone() {
/* 155 */       return Double2ObjectMaps.EMPTY_MAP;
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
/*     */   public static <V> Double2ObjectMap<V> emptyMap() {
/* 196 */     return EMPTY_MAP;
/*     */   }
/*     */ 
/*     */   
/*     */   public static class Singleton<V>
/*     */     extends Double2ObjectFunctions.Singleton<V>
/*     */     implements Double2ObjectMap<V>, Serializable, Cloneable
/*     */   {
/*     */     private static final long serialVersionUID = -7046029254386353129L;
/*     */     
/*     */     protected transient ObjectSet<Double2ObjectMap.Entry<V>> entries;
/*     */     
/*     */     protected transient DoubleSet keys;
/*     */     protected transient ObjectCollection<V> values;
/*     */     
/*     */     protected Singleton(double key, V value) {
/* 212 */       super(key, value);
/*     */     }
/*     */ 
/*     */     
/*     */     public boolean containsValue(Object v) {
/* 217 */       return Objects.equals(this.value, v);
/*     */     }
/*     */ 
/*     */     
/*     */     public void putAll(Map<? extends Double, ? extends V> m) {
/* 222 */       throw new UnsupportedOperationException();
/*     */     }
/*     */ 
/*     */     
/*     */     public ObjectSet<Double2ObjectMap.Entry<V>> double2ObjectEntrySet() {
/* 227 */       if (this.entries == null) this.entries = ObjectSets.singleton(new AbstractDouble2ObjectMap.BasicEntry<>(this.key, this.value)); 
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
/*     */     public ObjectSet<Map.Entry<Double, V>> entrySet() {
/* 240 */       return (ObjectSet)double2ObjectEntrySet();
/*     */     }
/*     */ 
/*     */     
/*     */     public DoubleSet keySet() {
/* 245 */       if (this.keys == null) this.keys = DoubleSets.singleton(this.key); 
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
/* 262 */       return HashCommon.double2int(this.key) ^ ((this.value == null) ? 0 : this.value.hashCode());
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
/*     */   public static <V> Double2ObjectMap<V> singleton(double key, V value) {
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
/*     */   public static <V> Double2ObjectMap<V> singleton(Double key, V value) {
/* 307 */     return new Singleton<>(key.doubleValue(), value);
/*     */   }
/*     */   
/*     */   public static class SynchronizedMap<V>
/*     */     extends Double2ObjectFunctions.SynchronizedFunction<V> implements Double2ObjectMap<V>, Serializable {
/*     */     private static final long serialVersionUID = -7046029254386353129L;
/*     */     protected final Double2ObjectMap<V> map;
/*     */     protected transient ObjectSet<Double2ObjectMap.Entry<V>> entries;
/*     */     protected transient DoubleSet keys;
/*     */     protected transient ObjectCollection<V> values;
/*     */     
/*     */     protected SynchronizedMap(Double2ObjectMap<V> m, Object sync) {
/* 319 */       super(m, sync);
/* 320 */       this.map = m;
/*     */     }
/*     */     
/*     */     protected SynchronizedMap(Double2ObjectMap<V> m) {
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
/*     */     public void putAll(Map<? extends Double, ? extends V> m) {
/* 337 */       synchronized (this.sync) {
/* 338 */         this.map.putAll(m);
/*     */       } 
/*     */     }
/*     */ 
/*     */     
/*     */     public ObjectSet<Double2ObjectMap.Entry<V>> double2ObjectEntrySet() {
/* 344 */       synchronized (this.sync) {
/* 345 */         if (this.entries == null) this.entries = ObjectSets.synchronize(this.map.double2ObjectEntrySet(), this.sync); 
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
/*     */     public ObjectSet<Map.Entry<Double, V>> entrySet() {
/* 359 */       return (ObjectSet)double2ObjectEntrySet();
/*     */     }
/*     */ 
/*     */     
/*     */     public DoubleSet keySet() {
/* 364 */       synchronized (this.sync) {
/* 365 */         if (this.keys == null) this.keys = DoubleSets.synchronize(this.map.keySet(), this.sync); 
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
/*     */     public V getOrDefault(double key, V defaultValue) {
/* 409 */       synchronized (this.sync) {
/* 410 */         return this.map.getOrDefault(key, defaultValue);
/*     */       } 
/*     */     }
/*     */ 
/*     */     
/*     */     public void forEach(BiConsumer<? super Double, ? super V> action) {
/* 416 */       synchronized (this.sync) {
/* 417 */         this.map.forEach(action);
/*     */       } 
/*     */     }
/*     */ 
/*     */     
/*     */     public void replaceAll(BiFunction<? super Double, ? super V, ? extends V> function) {
/* 423 */       synchronized (this.sync) {
/* 424 */         this.map.replaceAll(function);
/*     */       } 
/*     */     }
/*     */ 
/*     */     
/*     */     public V putIfAbsent(double key, V value) {
/* 430 */       synchronized (this.sync) {
/* 431 */         return this.map.putIfAbsent(key, value);
/*     */       } 
/*     */     }
/*     */ 
/*     */     
/*     */     public boolean remove(double key, Object value) {
/* 437 */       synchronized (this.sync) {
/* 438 */         return this.map.remove(key, value);
/*     */       } 
/*     */     }
/*     */ 
/*     */     
/*     */     public V replace(double key, V value) {
/* 444 */       synchronized (this.sync) {
/* 445 */         return this.map.replace(key, value);
/*     */       } 
/*     */     }
/*     */ 
/*     */     
/*     */     public boolean replace(double key, V oldValue, V newValue) {
/* 451 */       synchronized (this.sync) {
/* 452 */         return this.map.replace(key, oldValue, newValue);
/*     */       } 
/*     */     }
/*     */ 
/*     */     
/*     */     public V computeIfAbsent(double key, DoubleFunction<? extends V> mappingFunction) {
/* 458 */       synchronized (this.sync) {
/* 459 */         return this.map.computeIfAbsent(key, mappingFunction);
/*     */       } 
/*     */     }
/*     */ 
/*     */     
/*     */     public V computeIfAbsent(double key, Double2ObjectFunction<? extends V> mappingFunction) {
/* 465 */       synchronized (this.sync) {
/* 466 */         return this.map.computeIfAbsent(key, mappingFunction);
/*     */       } 
/*     */     }
/*     */ 
/*     */     
/*     */     public V computeIfPresent(double key, BiFunction<? super Double, ? super V, ? extends V> remappingFunction) {
/* 472 */       synchronized (this.sync) {
/* 473 */         return this.map.computeIfPresent(key, remappingFunction);
/*     */       } 
/*     */     }
/*     */ 
/*     */     
/*     */     public V compute(double key, BiFunction<? super Double, ? super V, ? extends V> remappingFunction) {
/* 479 */       synchronized (this.sync) {
/* 480 */         return this.map.compute(key, remappingFunction);
/*     */       } 
/*     */     }
/*     */ 
/*     */     
/*     */     public V merge(double key, V value, BiFunction<? super V, ? super V, ? extends V> remappingFunction) {
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
/*     */     public V replace(Double key, V value) {
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
/*     */     public boolean replace(Double key, V oldValue, V newValue) {
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
/*     */     public V putIfAbsent(Double key, V value) {
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
/*     */     public V computeIfAbsent(Double key, Function<? super Double, ? extends V> mappingFunction) {
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
/*     */     public V computeIfPresent(Double key, BiFunction<? super Double, ? super V, ? extends V> remappingFunction) {
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
/*     */     public V compute(Double key, BiFunction<? super Double, ? super V, ? extends V> remappingFunction) {
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
/*     */     public V merge(Double key, V value, BiFunction<? super V, ? super V, ? extends V> remappingFunction) {
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
/*     */   public static <V> Double2ObjectMap<V> synchronize(Double2ObjectMap<V> m) {
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
/*     */   public static <V> Double2ObjectMap<V> synchronize(Double2ObjectMap<V> m, Object sync) {
/* 630 */     return new SynchronizedMap<>(m, sync);
/*     */   }
/*     */   
/*     */   public static class UnmodifiableMap<V>
/*     */     extends Double2ObjectFunctions.UnmodifiableFunction<V> implements Double2ObjectMap<V>, Serializable {
/*     */     private static final long serialVersionUID = -7046029254386353129L;
/*     */     protected final Double2ObjectMap<? extends V> map;
/*     */     protected transient ObjectSet<Double2ObjectMap.Entry<V>> entries;
/*     */     protected transient DoubleSet keys;
/*     */     protected transient ObjectCollection<V> values;
/*     */     
/*     */     protected UnmodifiableMap(Double2ObjectMap<? extends V> m) {
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
/*     */     public void putAll(Map<? extends Double, ? extends V> m) {
/* 653 */       throw new UnsupportedOperationException();
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     public ObjectSet<Double2ObjectMap.Entry<V>> double2ObjectEntrySet() {
/* 659 */       if (this.entries == null) this.entries = ObjectSets.unmodifiable(this.map.double2ObjectEntrySet()); 
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
/*     */     public ObjectSet<Map.Entry<Double, V>> entrySet() {
/* 672 */       return (ObjectSet)double2ObjectEntrySet();
/*     */     }
/*     */ 
/*     */     
/*     */     public DoubleSet keySet() {
/* 677 */       if (this.keys == null) this.keys = DoubleSets.unmodifiable(this.map.keySet()); 
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
/*     */     public V getOrDefault(double key, V defaultValue) {
/* 707 */       return this.map.getOrDefault(key, defaultValue);
/*     */     }
/*     */ 
/*     */     
/*     */     public void forEach(BiConsumer<? super Double, ? super V> action) {
/* 712 */       this.map.forEach(action);
/*     */     }
/*     */ 
/*     */     
/*     */     public void replaceAll(BiFunction<? super Double, ? super V, ? extends V> function) {
/* 717 */       throw new UnsupportedOperationException();
/*     */     }
/*     */ 
/*     */     
/*     */     public V putIfAbsent(double key, V value) {
/* 722 */       throw new UnsupportedOperationException();
/*     */     }
/*     */ 
/*     */     
/*     */     public boolean remove(double key, Object value) {
/* 727 */       throw new UnsupportedOperationException();
/*     */     }
/*     */ 
/*     */     
/*     */     public V replace(double key, V value) {
/* 732 */       throw new UnsupportedOperationException();
/*     */     }
/*     */ 
/*     */     
/*     */     public boolean replace(double key, V oldValue, V newValue) {
/* 737 */       throw new UnsupportedOperationException();
/*     */     }
/*     */ 
/*     */     
/*     */     public V computeIfAbsent(double key, DoubleFunction<? extends V> mappingFunction) {
/* 742 */       throw new UnsupportedOperationException();
/*     */     }
/*     */ 
/*     */     
/*     */     public V computeIfAbsent(double key, Double2ObjectFunction<? extends V> mappingFunction) {
/* 747 */       throw new UnsupportedOperationException();
/*     */     }
/*     */ 
/*     */     
/*     */     public V computeIfPresent(double key, BiFunction<? super Double, ? super V, ? extends V> remappingFunction) {
/* 752 */       throw new UnsupportedOperationException();
/*     */     }
/*     */ 
/*     */     
/*     */     public V compute(double key, BiFunction<? super Double, ? super V, ? extends V> remappingFunction) {
/* 757 */       throw new UnsupportedOperationException();
/*     */     }
/*     */ 
/*     */     
/*     */     public V merge(double key, V value, BiFunction<? super V, ? super V, ? extends V> remappingFunction) {
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
/*     */     public V replace(Double key, V value) {
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
/*     */     public boolean replace(Double key, V oldValue, V newValue) {
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
/*     */     public V putIfAbsent(Double key, V value) {
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
/*     */     public V computeIfAbsent(Double key, Function<? super Double, ? extends V> mappingFunction) {
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
/*     */     public V computeIfPresent(Double key, BiFunction<? super Double, ? super V, ? extends V> remappingFunction) {
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
/*     */     public V compute(Double key, BiFunction<? super Double, ? super V, ? extends V> remappingFunction) {
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
/*     */     public V merge(Double key, V value, BiFunction<? super V, ? super V, ? extends V> remappingFunction) {
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
/*     */   public static <V> Double2ObjectMap<V> unmodifiable(Double2ObjectMap<? extends V> m) {
/* 874 */     return new UnmodifiableMap<>(m);
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\i\\unimi\dsi\fastutil\doubles\Double2ObjectMaps.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */