/*     */ package it.unimi.dsi.fastutil.objects;
/*     */ 
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public final class Object2ObjectMaps
/*     */ {
/*     */   public static <K, V> ObjectIterator<Object2ObjectMap.Entry<K, V>> fastIterator(Object2ObjectMap<K, V> map) {
/*  41 */     ObjectSet<Object2ObjectMap.Entry<K, V>> entries = map.object2ObjectEntrySet();
/*  42 */     return (entries instanceof Object2ObjectMap.FastEntrySet) ? ((Object2ObjectMap.FastEntrySet)entries).fastIterator() : entries.iterator();
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
/*     */   public static <K, V> void fastForEach(Object2ObjectMap<K, V> map, Consumer<? super Object2ObjectMap.Entry<K, V>> consumer) {
/*  57 */     ObjectSet<Object2ObjectMap.Entry<K, V>> entries = map.object2ObjectEntrySet();
/*  58 */     if (entries instanceof Object2ObjectMap.FastEntrySet) { ((Object2ObjectMap.FastEntrySet)entries).fastForEach(consumer); }
/*  59 */     else { entries.forEach(consumer); }
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
/*     */   public static <K, V> ObjectIterable<Object2ObjectMap.Entry<K, V>> fastIterable(Object2ObjectMap<K, V> map) {
/*  74 */     final ObjectSet<Object2ObjectMap.Entry<K, V>> entries = map.object2ObjectEntrySet();
/*  75 */     return (entries instanceof Object2ObjectMap.FastEntrySet) ? (ObjectIterable)new ObjectIterable<Object2ObjectMap.Entry<Object2ObjectMap.Entry<K, V>, V>>()
/*     */       {
/*     */         public ObjectIterator<Object2ObjectMap.Entry<K, V>> iterator() {
/*  78 */           return ((Object2ObjectMap.FastEntrySet<K, V>)entries).fastIterator();
/*     */         }
/*     */ 
/*     */         
/*     */         public ObjectSpliterator<Object2ObjectMap.Entry<K, V>> spliterator() {
/*  83 */           return entries.spliterator();
/*     */         }
/*     */ 
/*     */         
/*     */         public void forEach(Consumer<? super Object2ObjectMap.Entry<K, V>> consumer) {
/*  88 */           ((Object2ObjectMap.FastEntrySet<K, V>)entries).fastForEach(consumer);
/*     */         }
/*  90 */       } : entries;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static class EmptyMap<K, V>
/*     */     extends Object2ObjectFunctions.EmptyFunction<K, V>
/*     */     implements Object2ObjectMap<K, V>, Serializable, Cloneable
/*     */   {
/*     */     private static final long serialVersionUID = -7046029254386353129L;
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public boolean containsValue(Object v) {
/* 107 */       return false;
/*     */     }
/*     */ 
/*     */     
/*     */     public V getOrDefault(Object key, V defaultValue) {
/* 112 */       return defaultValue;
/*     */     }
/*     */ 
/*     */     
/*     */     public void putAll(Map<? extends K, ? extends V> m) {
/* 117 */       throw new UnsupportedOperationException();
/*     */     }
/*     */ 
/*     */     
/*     */     public ObjectSet<Object2ObjectMap.Entry<K, V>> object2ObjectEntrySet() {
/* 122 */       return ObjectSets.EMPTY_SET;
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     public ObjectSet<K> keySet() {
/* 128 */       return ObjectSets.EMPTY_SET;
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     public ObjectCollection<V> values() {
/* 134 */       return ObjectSets.EMPTY_SET;
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     public void forEach(BiConsumer<? super K, ? super V> consumer) {}
/*     */ 
/*     */     
/*     */     public Object clone() {
/* 143 */       return Object2ObjectMaps.EMPTY_MAP;
/*     */     }
/*     */ 
/*     */     
/*     */     public boolean isEmpty() {
/* 148 */       return true;
/*     */     }
/*     */ 
/*     */     
/*     */     public int hashCode() {
/* 153 */       return 0;
/*     */     }
/*     */ 
/*     */     
/*     */     public boolean equals(Object o) {
/* 158 */       if (!(o instanceof Map)) return false; 
/* 159 */       return ((Map)o).isEmpty();
/*     */     }
/*     */ 
/*     */     
/*     */     public String toString() {
/* 164 */       return "{}";
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 172 */   public static final EmptyMap EMPTY_MAP = new EmptyMap<>();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static <K, V> Object2ObjectMap<K, V> emptyMap() {
/* 184 */     return EMPTY_MAP;
/*     */   }
/*     */ 
/*     */   
/*     */   public static class Singleton<K, V>
/*     */     extends Object2ObjectFunctions.Singleton<K, V>
/*     */     implements Object2ObjectMap<K, V>, Serializable, Cloneable
/*     */   {
/*     */     private static final long serialVersionUID = -7046029254386353129L;
/*     */     
/*     */     protected transient ObjectSet<Object2ObjectMap.Entry<K, V>> entries;
/*     */     
/*     */     protected transient ObjectSet<K> keys;
/*     */     protected transient ObjectCollection<V> values;
/*     */     
/*     */     protected Singleton(K key, V value) {
/* 200 */       super(key, value);
/*     */     }
/*     */ 
/*     */     
/*     */     public boolean containsValue(Object v) {
/* 205 */       return Objects.equals(this.value, v);
/*     */     }
/*     */ 
/*     */     
/*     */     public void putAll(Map<? extends K, ? extends V> m) {
/* 210 */       throw new UnsupportedOperationException();
/*     */     }
/*     */ 
/*     */     
/*     */     public ObjectSet<Object2ObjectMap.Entry<K, V>> object2ObjectEntrySet() {
/* 215 */       if (this.entries == null) this.entries = ObjectSets.singleton(new AbstractObject2ObjectMap.BasicEntry<>(this.key, this.value)); 
/* 216 */       return this.entries;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public ObjectSet<Map.Entry<K, V>> entrySet() {
/* 223 */       return (ObjectSet)object2ObjectEntrySet();
/*     */     }
/*     */ 
/*     */     
/*     */     public ObjectSet<K> keySet() {
/* 228 */       if (this.keys == null) this.keys = ObjectSets.singleton(this.key); 
/* 229 */       return this.keys;
/*     */     }
/*     */ 
/*     */     
/*     */     public ObjectCollection<V> values() {
/* 234 */       if (this.values == null) this.values = ObjectSets.singleton(this.value); 
/* 235 */       return this.values;
/*     */     }
/*     */ 
/*     */     
/*     */     public boolean isEmpty() {
/* 240 */       return false;
/*     */     }
/*     */ 
/*     */     
/*     */     public int hashCode() {
/* 245 */       return ((this.key == null) ? 0 : this.key.hashCode()) ^ ((this.value == null) ? 0 : this.value.hashCode());
/*     */     }
/*     */ 
/*     */     
/*     */     public boolean equals(Object o) {
/* 250 */       if (o == this) return true; 
/* 251 */       if (!(o instanceof Map)) return false; 
/* 252 */       Map<?, ?> m = (Map<?, ?>)o;
/* 253 */       if (m.size() != 1) return false; 
/* 254 */       return ((Map.Entry)m.entrySet().iterator().next()).equals(entrySet().iterator().next());
/*     */     }
/*     */ 
/*     */     
/*     */     public String toString() {
/* 259 */       return "{" + this.key + "=>" + this.value + "}";
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
/*     */   public static <K, V> Object2ObjectMap<K, V> singleton(K key, V value) {
/* 275 */     return new Singleton<>(key, value);
/*     */   }
/*     */   
/*     */   public static class SynchronizedMap<K, V>
/*     */     extends Object2ObjectFunctions.SynchronizedFunction<K, V> implements Object2ObjectMap<K, V>, Serializable {
/*     */     private static final long serialVersionUID = -7046029254386353129L;
/*     */     protected final Object2ObjectMap<K, V> map;
/*     */     protected transient ObjectSet<Object2ObjectMap.Entry<K, V>> entries;
/*     */     protected transient ObjectSet<K> keys;
/*     */     protected transient ObjectCollection<V> values;
/*     */     
/*     */     protected SynchronizedMap(Object2ObjectMap<K, V> m, Object sync) {
/* 287 */       super(m, sync);
/* 288 */       this.map = m;
/*     */     }
/*     */     
/*     */     protected SynchronizedMap(Object2ObjectMap<K, V> m) {
/* 292 */       super(m);
/* 293 */       this.map = m;
/*     */     }
/*     */ 
/*     */     
/*     */     public boolean containsValue(Object v) {
/* 298 */       synchronized (this.sync) {
/* 299 */         return this.map.containsValue(v);
/*     */       } 
/*     */     }
/*     */ 
/*     */     
/*     */     public void putAll(Map<? extends K, ? extends V> m) {
/* 305 */       synchronized (this.sync) {
/* 306 */         this.map.putAll(m);
/*     */       } 
/*     */     }
/*     */ 
/*     */     
/*     */     public ObjectSet<Object2ObjectMap.Entry<K, V>> object2ObjectEntrySet() {
/* 312 */       synchronized (this.sync) {
/* 313 */         if (this.entries == null) this.entries = ObjectSets.synchronize(this.map.object2ObjectEntrySet(), this.sync); 
/* 314 */         return this.entries;
/*     */       } 
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public ObjectSet<Map.Entry<K, V>> entrySet() {
/* 322 */       return (ObjectSet)object2ObjectEntrySet();
/*     */     }
/*     */ 
/*     */     
/*     */     public ObjectSet<K> keySet() {
/* 327 */       synchronized (this.sync) {
/* 328 */         if (this.keys == null) this.keys = ObjectSets.synchronize(this.map.keySet(), this.sync); 
/* 329 */         return this.keys;
/*     */       } 
/*     */     }
/*     */ 
/*     */     
/*     */     public ObjectCollection<V> values() {
/* 335 */       synchronized (this.sync) {
/* 336 */         if (this.values == null) this.values = ObjectCollections.synchronize(this.map.values(), this.sync); 
/* 337 */         return this.values;
/*     */       } 
/*     */     }
/*     */ 
/*     */     
/*     */     public boolean isEmpty() {
/* 343 */       synchronized (this.sync) {
/* 344 */         return this.map.isEmpty();
/*     */       } 
/*     */     }
/*     */ 
/*     */     
/*     */     public int hashCode() {
/* 350 */       synchronized (this.sync) {
/* 351 */         return this.map.hashCode();
/*     */       } 
/*     */     }
/*     */ 
/*     */     
/*     */     public boolean equals(Object o) {
/* 357 */       if (o == this) return true; 
/* 358 */       synchronized (this.sync) {
/* 359 */         return this.map.equals(o);
/*     */       } 
/*     */     }
/*     */     
/*     */     private void writeObject(ObjectOutputStream s) throws IOException {
/* 364 */       synchronized (this.sync) {
/* 365 */         s.defaultWriteObject();
/*     */       } 
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     public V getOrDefault(Object key, V defaultValue) {
/* 372 */       synchronized (this.sync) {
/* 373 */         return this.map.getOrDefault(key, defaultValue);
/*     */       } 
/*     */     }
/*     */ 
/*     */     
/*     */     public void forEach(BiConsumer<? super K, ? super V> action) {
/* 379 */       synchronized (this.sync) {
/* 380 */         this.map.forEach(action);
/*     */       } 
/*     */     }
/*     */ 
/*     */     
/*     */     public void replaceAll(BiFunction<? super K, ? super V, ? extends V> function) {
/* 386 */       synchronized (this.sync) {
/* 387 */         this.map.replaceAll(function);
/*     */       } 
/*     */     }
/*     */ 
/*     */     
/*     */     public V putIfAbsent(K key, V value) {
/* 393 */       synchronized (this.sync) {
/* 394 */         return this.map.putIfAbsent(key, value);
/*     */       } 
/*     */     }
/*     */ 
/*     */     
/*     */     public boolean remove(Object key, Object value) {
/* 400 */       synchronized (this.sync) {
/* 401 */         return this.map.remove(key, value);
/*     */       } 
/*     */     }
/*     */ 
/*     */     
/*     */     public V replace(K key, V value) {
/* 407 */       synchronized (this.sync) {
/* 408 */         return this.map.replace(key, value);
/*     */       } 
/*     */     }
/*     */ 
/*     */     
/*     */     public boolean replace(K key, V oldValue, V newValue) {
/* 414 */       synchronized (this.sync) {
/* 415 */         return this.map.replace(key, oldValue, newValue);
/*     */       } 
/*     */     }
/*     */ 
/*     */     
/*     */     public V computeIfPresent(K key, BiFunction<? super K, ? super V, ? extends V> remappingFunction) {
/* 421 */       synchronized (this.sync) {
/* 422 */         return this.map.computeIfPresent(key, remappingFunction);
/*     */       } 
/*     */     }
/*     */ 
/*     */     
/*     */     public V compute(K key, BiFunction<? super K, ? super V, ? extends V> remappingFunction) {
/* 428 */       synchronized (this.sync) {
/* 429 */         return this.map.compute(key, remappingFunction);
/*     */       } 
/*     */     }
/*     */ 
/*     */     
/*     */     public V merge(K key, V value, BiFunction<? super V, ? super V, ? extends V> remappingFunction) {
/* 435 */       synchronized (this.sync) {
/* 436 */         return this.map.merge(key, value, remappingFunction);
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
/*     */   public static <K, V> Object2ObjectMap<K, V> synchronize(Object2ObjectMap<K, V> m) {
/* 449 */     return new SynchronizedMap<>(m);
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
/*     */   public static <K, V> Object2ObjectMap<K, V> synchronize(Object2ObjectMap<K, V> m, Object sync) {
/* 462 */     return new SynchronizedMap<>(m, sync);
/*     */   }
/*     */   
/*     */   public static class UnmodifiableMap<K, V>
/*     */     extends Object2ObjectFunctions.UnmodifiableFunction<K, V> implements Object2ObjectMap<K, V>, Serializable {
/*     */     private static final long serialVersionUID = -7046029254386353129L;
/*     */     protected final Object2ObjectMap<? extends K, ? extends V> map;
/*     */     protected transient ObjectSet<Object2ObjectMap.Entry<K, V>> entries;
/*     */     protected transient ObjectSet<K> keys;
/*     */     protected transient ObjectCollection<V> values;
/*     */     
/*     */     protected UnmodifiableMap(Object2ObjectMap<? extends K, ? extends V> m) {
/* 474 */       super(m);
/* 475 */       this.map = m;
/*     */     }
/*     */ 
/*     */     
/*     */     public boolean containsValue(Object v) {
/* 480 */       return this.map.containsValue(v);
/*     */     }
/*     */ 
/*     */     
/*     */     public void putAll(Map<? extends K, ? extends V> m) {
/* 485 */       throw new UnsupportedOperationException();
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     public ObjectSet<Object2ObjectMap.Entry<K, V>> object2ObjectEntrySet() {
/* 491 */       if (this.entries == null) this.entries = ObjectSets.unmodifiable((ObjectSet)this.map.object2ObjectEntrySet()); 
/* 492 */       return this.entries;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public ObjectSet<Map.Entry<K, V>> entrySet() {
/* 499 */       return (ObjectSet)object2ObjectEntrySet();
/*     */     }
/*     */ 
/*     */     
/*     */     public ObjectSet<K> keySet() {
/* 504 */       if (this.keys == null) this.keys = ObjectSets.unmodifiable(this.map.keySet()); 
/* 505 */       return this.keys;
/*     */     }
/*     */ 
/*     */     
/*     */     public ObjectCollection<V> values() {
/* 510 */       if (this.values == null) this.values = ObjectCollections.unmodifiable(this.map.values()); 
/* 511 */       return this.values;
/*     */     }
/*     */ 
/*     */     
/*     */     public boolean isEmpty() {
/* 516 */       return this.map.isEmpty();
/*     */     }
/*     */ 
/*     */     
/*     */     public int hashCode() {
/* 521 */       return this.map.hashCode();
/*     */     }
/*     */ 
/*     */     
/*     */     public boolean equals(Object o) {
/* 526 */       if (o == this) return true; 
/* 527 */       return this.map.equals(o);
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public V getOrDefault(Object key, V defaultValue) {
/* 534 */       return this.map.getOrDefault(key, defaultValue);
/*     */     }
/*     */ 
/*     */     
/*     */     public void forEach(BiConsumer<? super K, ? super V> action) {
/* 539 */       this.map.forEach(action);
/*     */     }
/*     */ 
/*     */     
/*     */     public void replaceAll(BiFunction<? super K, ? super V, ? extends V> function) {
/* 544 */       throw new UnsupportedOperationException();
/*     */     }
/*     */ 
/*     */     
/*     */     public V putIfAbsent(K key, V value) {
/* 549 */       throw new UnsupportedOperationException();
/*     */     }
/*     */ 
/*     */     
/*     */     public boolean remove(Object key, Object value) {
/* 554 */       throw new UnsupportedOperationException();
/*     */     }
/*     */ 
/*     */     
/*     */     public V replace(K key, V value) {
/* 559 */       throw new UnsupportedOperationException();
/*     */     }
/*     */ 
/*     */     
/*     */     public boolean replace(K key, V oldValue, V newValue) {
/* 564 */       throw new UnsupportedOperationException();
/*     */     }
/*     */ 
/*     */     
/*     */     public V computeIfPresent(K key, BiFunction<? super K, ? super V, ? extends V> remappingFunction) {
/* 569 */       throw new UnsupportedOperationException();
/*     */     }
/*     */ 
/*     */     
/*     */     public V compute(K key, BiFunction<? super K, ? super V, ? extends V> remappingFunction) {
/* 574 */       throw new UnsupportedOperationException();
/*     */     }
/*     */ 
/*     */     
/*     */     public V merge(K key, V value, BiFunction<? super V, ? super V, ? extends V> remappingFunction) {
/* 579 */       throw new UnsupportedOperationException();
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
/*     */   public static <K, V> Object2ObjectMap<K, V> unmodifiable(Object2ObjectMap<? extends K, ? extends V> m) {
/* 591 */     return new UnmodifiableMap<>(m);
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\i\\unimi\dsi\fastutil\objects\Object2ObjectMaps.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */