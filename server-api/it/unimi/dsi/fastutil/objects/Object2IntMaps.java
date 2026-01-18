/*     */ package it.unimi.dsi.fastutil.objects;
/*     */ 
/*     */ import it.unimi.dsi.fastutil.ints.IntCollection;
/*     */ import it.unimi.dsi.fastutil.ints.IntCollections;
/*     */ import it.unimi.dsi.fastutil.ints.IntSets;
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
/*     */ import java.util.function.ToIntFunction;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public final class Object2IntMaps
/*     */ {
/*     */   public static <K> ObjectIterator<Object2IntMap.Entry<K>> fastIterator(Object2IntMap<K> map) {
/*  44 */     ObjectSet<Object2IntMap.Entry<K>> entries = map.object2IntEntrySet();
/*  45 */     return (entries instanceof Object2IntMap.FastEntrySet) ? ((Object2IntMap.FastEntrySet)entries).fastIterator() : entries.iterator();
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
/*     */   public static <K> void fastForEach(Object2IntMap<K> map, Consumer<? super Object2IntMap.Entry<K>> consumer) {
/*  60 */     ObjectSet<Object2IntMap.Entry<K>> entries = map.object2IntEntrySet();
/*  61 */     if (entries instanceof Object2IntMap.FastEntrySet) { ((Object2IntMap.FastEntrySet)entries).fastForEach(consumer); }
/*  62 */     else { entries.forEach(consumer); }
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
/*     */   public static <K> ObjectIterable<Object2IntMap.Entry<K>> fastIterable(Object2IntMap<K> map) {
/*  77 */     final ObjectSet<Object2IntMap.Entry<K>> entries = map.object2IntEntrySet();
/*  78 */     return (entries instanceof Object2IntMap.FastEntrySet) ? (ObjectIterable)new ObjectIterable<Object2IntMap.Entry<Object2IntMap.Entry<K>>>()
/*     */       {
/*     */         public ObjectIterator<Object2IntMap.Entry<K>> iterator() {
/*  81 */           return ((Object2IntMap.FastEntrySet<K>)entries).fastIterator();
/*     */         }
/*     */ 
/*     */         
/*     */         public ObjectSpliterator<Object2IntMap.Entry<K>> spliterator() {
/*  86 */           return entries.spliterator();
/*     */         }
/*     */ 
/*     */         
/*     */         public void forEach(Consumer<? super Object2IntMap.Entry<K>> consumer) {
/*  91 */           ((Object2IntMap.FastEntrySet<K>)entries).fastForEach(consumer);
/*     */         }
/*  93 */       } : entries;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static class EmptyMap<K>
/*     */     extends Object2IntFunctions.EmptyFunction<K>
/*     */     implements Object2IntMap<K>, Serializable, Cloneable
/*     */   {
/*     */     private static final long serialVersionUID = -7046029254386353129L;
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public boolean containsValue(int v) {
/* 110 */       return false;
/*     */     }
/*     */ 
/*     */     
/*     */     @Deprecated
/*     */     public Integer getOrDefault(Object key, Integer defaultValue) {
/* 116 */       return defaultValue;
/*     */     }
/*     */ 
/*     */     
/*     */     public int getOrDefault(Object key, int defaultValue) {
/* 121 */       return defaultValue;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     @Deprecated
/*     */     public boolean containsValue(Object ov) {
/* 132 */       return false;
/*     */     }
/*     */ 
/*     */     
/*     */     public void putAll(Map<? extends K, ? extends Integer> m) {
/* 137 */       throw new UnsupportedOperationException();
/*     */     }
/*     */ 
/*     */     
/*     */     public ObjectSet<Object2IntMap.Entry<K>> object2IntEntrySet() {
/* 142 */       return ObjectSets.EMPTY_SET;
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     public ObjectSet<K> keySet() {
/* 148 */       return ObjectSets.EMPTY_SET;
/*     */     }
/*     */ 
/*     */     
/*     */     public IntCollection values() {
/* 153 */       return (IntCollection)IntSets.EMPTY_SET;
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     public void forEach(BiConsumer<? super K, ? super Integer> consumer) {}
/*     */ 
/*     */     
/*     */     public Object clone() {
/* 162 */       return Object2IntMaps.EMPTY_MAP;
/*     */     }
/*     */ 
/*     */     
/*     */     public boolean isEmpty() {
/* 167 */       return true;
/*     */     }
/*     */ 
/*     */     
/*     */     public int hashCode() {
/* 172 */       return 0;
/*     */     }
/*     */ 
/*     */     
/*     */     public boolean equals(Object o) {
/* 177 */       if (!(o instanceof Map)) return false; 
/* 178 */       return ((Map)o).isEmpty();
/*     */     }
/*     */ 
/*     */     
/*     */     public String toString() {
/* 183 */       return "{}";
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 191 */   public static final EmptyMap EMPTY_MAP = new EmptyMap();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static <K> Object2IntMap<K> emptyMap() {
/* 203 */     return EMPTY_MAP;
/*     */   }
/*     */ 
/*     */   
/*     */   public static class Singleton<K>
/*     */     extends Object2IntFunctions.Singleton<K>
/*     */     implements Object2IntMap<K>, Serializable, Cloneable
/*     */   {
/*     */     private static final long serialVersionUID = -7046029254386353129L;
/*     */     
/*     */     protected transient ObjectSet<Object2IntMap.Entry<K>> entries;
/*     */     
/*     */     protected transient ObjectSet<K> keys;
/*     */     protected transient IntCollection values;
/*     */     
/*     */     protected Singleton(K key, int value) {
/* 219 */       super(key, value);
/*     */     }
/*     */ 
/*     */     
/*     */     public boolean containsValue(int v) {
/* 224 */       return (this.value == v);
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     @Deprecated
/*     */     public boolean containsValue(Object ov) {
/* 235 */       return (((Integer)ov).intValue() == this.value);
/*     */     }
/*     */ 
/*     */     
/*     */     public void putAll(Map<? extends K, ? extends Integer> m) {
/* 240 */       throw new UnsupportedOperationException();
/*     */     }
/*     */ 
/*     */     
/*     */     public ObjectSet<Object2IntMap.Entry<K>> object2IntEntrySet() {
/* 245 */       if (this.entries == null) this.entries = ObjectSets.singleton(new AbstractObject2IntMap.BasicEntry<>(this.key, this.value)); 
/* 246 */       return this.entries;
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
/*     */     public ObjectSet<Map.Entry<K, Integer>> entrySet() {
/* 258 */       return (ObjectSet)object2IntEntrySet();
/*     */     }
/*     */ 
/*     */     
/*     */     public ObjectSet<K> keySet() {
/* 263 */       if (this.keys == null) this.keys = ObjectSets.singleton(this.key); 
/* 264 */       return this.keys;
/*     */     }
/*     */ 
/*     */     
/*     */     public IntCollection values() {
/* 269 */       if (this.values == null) this.values = (IntCollection)IntSets.singleton(this.value); 
/* 270 */       return this.values;
/*     */     }
/*     */ 
/*     */     
/*     */     public boolean isEmpty() {
/* 275 */       return false;
/*     */     }
/*     */ 
/*     */     
/*     */     public int hashCode() {
/* 280 */       return ((this.key == null) ? 0 : this.key.hashCode()) ^ this.value;
/*     */     }
/*     */ 
/*     */     
/*     */     public boolean equals(Object o) {
/* 285 */       if (o == this) return true; 
/* 286 */       if (!(o instanceof Map)) return false; 
/* 287 */       Map<?, ?> m = (Map<?, ?>)o;
/* 288 */       if (m.size() != 1) return false; 
/* 289 */       return ((Map.Entry)m.entrySet().iterator().next()).equals(entrySet().iterator().next());
/*     */     }
/*     */ 
/*     */     
/*     */     public String toString() {
/* 294 */       return "{" + this.key + "=>" + this.value + "}";
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
/*     */   public static <K> Object2IntMap<K> singleton(K key, int value) {
/* 310 */     return new Singleton<>(key, value);
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
/*     */   public static <K> Object2IntMap<K> singleton(K key, Integer value) {
/* 325 */     return new Singleton<>(key, value.intValue());
/*     */   }
/*     */   
/*     */   public static class SynchronizedMap<K>
/*     */     extends Object2IntFunctions.SynchronizedFunction<K> implements Object2IntMap<K>, Serializable {
/*     */     private static final long serialVersionUID = -7046029254386353129L;
/*     */     protected final Object2IntMap<K> map;
/*     */     protected transient ObjectSet<Object2IntMap.Entry<K>> entries;
/*     */     protected transient ObjectSet<K> keys;
/*     */     protected transient IntCollection values;
/*     */     
/*     */     protected SynchronizedMap(Object2IntMap<K> m, Object sync) {
/* 337 */       super(m, sync);
/* 338 */       this.map = m;
/*     */     }
/*     */     
/*     */     protected SynchronizedMap(Object2IntMap<K> m) {
/* 342 */       super(m);
/* 343 */       this.map = m;
/*     */     }
/*     */ 
/*     */     
/*     */     public boolean containsValue(int v) {
/* 348 */       synchronized (this.sync) {
/* 349 */         return this.map.containsValue(v);
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
/*     */     public boolean containsValue(Object ov) {
/* 361 */       synchronized (this.sync) {
/* 362 */         return this.map.containsValue(ov);
/*     */       } 
/*     */     }
/*     */ 
/*     */     
/*     */     public void putAll(Map<? extends K, ? extends Integer> m) {
/* 368 */       synchronized (this.sync) {
/* 369 */         this.map.putAll(m);
/*     */       } 
/*     */     }
/*     */ 
/*     */     
/*     */     public ObjectSet<Object2IntMap.Entry<K>> object2IntEntrySet() {
/* 375 */       synchronized (this.sync) {
/* 376 */         if (this.entries == null) this.entries = ObjectSets.synchronize(this.map.object2IntEntrySet(), this.sync); 
/* 377 */         return this.entries;
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
/*     */     public ObjectSet<Map.Entry<K, Integer>> entrySet() {
/* 390 */       return (ObjectSet)object2IntEntrySet();
/*     */     }
/*     */ 
/*     */     
/*     */     public ObjectSet<K> keySet() {
/* 395 */       synchronized (this.sync) {
/* 396 */         if (this.keys == null) this.keys = ObjectSets.synchronize(this.map.keySet(), this.sync); 
/* 397 */         return this.keys;
/*     */       } 
/*     */     }
/*     */ 
/*     */     
/*     */     public IntCollection values() {
/* 403 */       synchronized (this.sync) {
/* 404 */         if (this.values == null) this.values = IntCollections.synchronize(this.map.values(), this.sync); 
/* 405 */         return this.values;
/*     */       } 
/*     */     }
/*     */ 
/*     */     
/*     */     public boolean isEmpty() {
/* 411 */       synchronized (this.sync) {
/* 412 */         return this.map.isEmpty();
/*     */       } 
/*     */     }
/*     */ 
/*     */     
/*     */     public int hashCode() {
/* 418 */       synchronized (this.sync) {
/* 419 */         return this.map.hashCode();
/*     */       } 
/*     */     }
/*     */ 
/*     */     
/*     */     public boolean equals(Object o) {
/* 425 */       if (o == this) return true; 
/* 426 */       synchronized (this.sync) {
/* 427 */         return this.map.equals(o);
/*     */       } 
/*     */     }
/*     */     
/*     */     private void writeObject(ObjectOutputStream s) throws IOException {
/* 432 */       synchronized (this.sync) {
/* 433 */         s.defaultWriteObject();
/*     */       } 
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     public int getOrDefault(Object key, int defaultValue) {
/* 440 */       synchronized (this.sync) {
/* 441 */         return this.map.getOrDefault(key, defaultValue);
/*     */       } 
/*     */     }
/*     */ 
/*     */     
/*     */     public void forEach(BiConsumer<? super K, ? super Integer> action) {
/* 447 */       synchronized (this.sync) {
/* 448 */         this.map.forEach(action);
/*     */       } 
/*     */     }
/*     */ 
/*     */     
/*     */     public void replaceAll(BiFunction<? super K, ? super Integer, ? extends Integer> function) {
/* 454 */       synchronized (this.sync) {
/* 455 */         this.map.replaceAll(function);
/*     */       } 
/*     */     }
/*     */ 
/*     */     
/*     */     public int putIfAbsent(K key, int value) {
/* 461 */       synchronized (this.sync) {
/* 462 */         return this.map.putIfAbsent(key, value);
/*     */       } 
/*     */     }
/*     */ 
/*     */     
/*     */     public boolean remove(Object key, int value) {
/* 468 */       synchronized (this.sync) {
/* 469 */         return this.map.remove(key, value);
/*     */       } 
/*     */     }
/*     */ 
/*     */     
/*     */     public int replace(K key, int value) {
/* 475 */       synchronized (this.sync) {
/* 476 */         return this.map.replace(key, value);
/*     */       } 
/*     */     }
/*     */ 
/*     */     
/*     */     public boolean replace(K key, int oldValue, int newValue) {
/* 482 */       synchronized (this.sync) {
/* 483 */         return this.map.replace(key, oldValue, newValue);
/*     */       } 
/*     */     }
/*     */ 
/*     */     
/*     */     public int computeIfAbsent(K key, ToIntFunction<? super K> mappingFunction) {
/* 489 */       synchronized (this.sync) {
/* 490 */         return this.map.computeIfAbsent(key, mappingFunction);
/*     */       } 
/*     */     }
/*     */ 
/*     */     
/*     */     public int computeIfAbsent(K key, Object2IntFunction<? super K> mappingFunction) {
/* 496 */       synchronized (this.sync) {
/* 497 */         return this.map.computeIfAbsent(key, mappingFunction);
/*     */       } 
/*     */     }
/*     */ 
/*     */     
/*     */     public int computeIntIfPresent(K key, BiFunction<? super K, ? super Integer, ? extends Integer> remappingFunction) {
/* 503 */       synchronized (this.sync) {
/* 504 */         return this.map.computeIntIfPresent(key, remappingFunction);
/*     */       } 
/*     */     }
/*     */ 
/*     */     
/*     */     public int computeInt(K key, BiFunction<? super K, ? super Integer, ? extends Integer> remappingFunction) {
/* 510 */       synchronized (this.sync) {
/* 511 */         return this.map.computeInt(key, remappingFunction);
/*     */       } 
/*     */     }
/*     */ 
/*     */     
/*     */     public int merge(K key, int value, BiFunction<? super Integer, ? super Integer, ? extends Integer> remappingFunction) {
/* 517 */       synchronized (this.sync) {
/* 518 */         return this.map.merge(key, value, remappingFunction);
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
/*     */     public Integer getOrDefault(Object key, Integer defaultValue) {
/* 530 */       synchronized (this.sync) {
/* 531 */         return this.map.getOrDefault(key, defaultValue);
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
/* 543 */       synchronized (this.sync) {
/* 544 */         return this.map.remove(key, value);
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
/*     */     public Integer replace(K key, Integer value) {
/* 556 */       synchronized (this.sync) {
/* 557 */         return this.map.replace(key, value);
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
/*     */     public boolean replace(K key, Integer oldValue, Integer newValue) {
/* 569 */       synchronized (this.sync) {
/* 570 */         return this.map.replace(key, oldValue, newValue);
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
/*     */     public Integer putIfAbsent(K key, Integer value) {
/* 582 */       synchronized (this.sync) {
/* 583 */         return this.map.putIfAbsent(key, value);
/*     */       } 
/*     */     }
/*     */ 
/*     */     
/*     */     public Integer computeIfAbsent(K key, Function<? super K, ? extends Integer> mappingFunction) {
/* 589 */       synchronized (this.sync) {
/* 590 */         return this.map.computeIfAbsent(key, mappingFunction);
/*     */       } 
/*     */     }
/*     */ 
/*     */     
/*     */     public Integer computeIfPresent(K key, BiFunction<? super K, ? super Integer, ? extends Integer> remappingFunction) {
/* 596 */       synchronized (this.sync) {
/* 597 */         return this.map.computeIfPresent(key, remappingFunction);
/*     */       } 
/*     */     }
/*     */ 
/*     */     
/*     */     public Integer compute(K key, BiFunction<? super K, ? super Integer, ? extends Integer> remappingFunction) {
/* 603 */       synchronized (this.sync) {
/* 604 */         return this.map.compute(key, remappingFunction);
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
/*     */     public Integer merge(K key, Integer value, BiFunction<? super Integer, ? super Integer, ? extends Integer> remappingFunction) {
/* 616 */       synchronized (this.sync) {
/* 617 */         return this.map.merge(key, value, remappingFunction);
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
/*     */   public static <K> Object2IntMap<K> synchronize(Object2IntMap<K> m) {
/* 630 */     return new SynchronizedMap<>(m);
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
/*     */   public static <K> Object2IntMap<K> synchronize(Object2IntMap<K> m, Object sync) {
/* 643 */     return new SynchronizedMap<>(m, sync);
/*     */   }
/*     */   
/*     */   public static class UnmodifiableMap<K>
/*     */     extends Object2IntFunctions.UnmodifiableFunction<K> implements Object2IntMap<K>, Serializable {
/*     */     private static final long serialVersionUID = -7046029254386353129L;
/*     */     protected final Object2IntMap<? extends K> map;
/*     */     protected transient ObjectSet<Object2IntMap.Entry<K>> entries;
/*     */     protected transient ObjectSet<K> keys;
/*     */     protected transient IntCollection values;
/*     */     
/*     */     protected UnmodifiableMap(Object2IntMap<? extends K> m) {
/* 655 */       super(m);
/* 656 */       this.map = m;
/*     */     }
/*     */ 
/*     */     
/*     */     public boolean containsValue(int v) {
/* 661 */       return this.map.containsValue(v);
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     @Deprecated
/*     */     public boolean containsValue(Object ov) {
/* 672 */       return this.map.containsValue(ov);
/*     */     }
/*     */ 
/*     */     
/*     */     public void putAll(Map<? extends K, ? extends Integer> m) {
/* 677 */       throw new UnsupportedOperationException();
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     public ObjectSet<Object2IntMap.Entry<K>> object2IntEntrySet() {
/* 683 */       if (this.entries == null) this.entries = ObjectSets.unmodifiable((ObjectSet)this.map.object2IntEntrySet()); 
/* 684 */       return this.entries;
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
/*     */     public ObjectSet<Map.Entry<K, Integer>> entrySet() {
/* 696 */       return (ObjectSet)object2IntEntrySet();
/*     */     }
/*     */ 
/*     */     
/*     */     public ObjectSet<K> keySet() {
/* 701 */       if (this.keys == null) this.keys = ObjectSets.unmodifiable(this.map.keySet()); 
/* 702 */       return this.keys;
/*     */     }
/*     */ 
/*     */     
/*     */     public IntCollection values() {
/* 707 */       if (this.values == null) this.values = IntCollections.unmodifiable(this.map.values()); 
/* 708 */       return this.values;
/*     */     }
/*     */ 
/*     */     
/*     */     public boolean isEmpty() {
/* 713 */       return this.map.isEmpty();
/*     */     }
/*     */ 
/*     */     
/*     */     public int hashCode() {
/* 718 */       return this.map.hashCode();
/*     */     }
/*     */ 
/*     */     
/*     */     public boolean equals(Object o) {
/* 723 */       if (o == this) return true; 
/* 724 */       return this.map.equals(o);
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public int getOrDefault(Object key, int defaultValue) {
/* 731 */       return this.map.getOrDefault(key, defaultValue);
/*     */     }
/*     */ 
/*     */     
/*     */     public void forEach(BiConsumer<? super K, ? super Integer> action) {
/* 736 */       this.map.forEach(action);
/*     */     }
/*     */ 
/*     */     
/*     */     public void replaceAll(BiFunction<? super K, ? super Integer, ? extends Integer> function) {
/* 741 */       throw new UnsupportedOperationException();
/*     */     }
/*     */ 
/*     */     
/*     */     public int putIfAbsent(K key, int value) {
/* 746 */       throw new UnsupportedOperationException();
/*     */     }
/*     */ 
/*     */     
/*     */     public boolean remove(Object key, int value) {
/* 751 */       throw new UnsupportedOperationException();
/*     */     }
/*     */ 
/*     */     
/*     */     public int replace(K key, int value) {
/* 756 */       throw new UnsupportedOperationException();
/*     */     }
/*     */ 
/*     */     
/*     */     public boolean replace(K key, int oldValue, int newValue) {
/* 761 */       throw new UnsupportedOperationException();
/*     */     }
/*     */ 
/*     */     
/*     */     public int computeIfAbsent(K key, ToIntFunction<? super K> mappingFunction) {
/* 766 */       throw new UnsupportedOperationException();
/*     */     }
/*     */ 
/*     */     
/*     */     public int computeIfAbsent(K key, Object2IntFunction<? super K> mappingFunction) {
/* 771 */       throw new UnsupportedOperationException();
/*     */     }
/*     */ 
/*     */     
/*     */     public int computeIntIfPresent(K key, BiFunction<? super K, ? super Integer, ? extends Integer> remappingFunction) {
/* 776 */       throw new UnsupportedOperationException();
/*     */     }
/*     */ 
/*     */     
/*     */     public int computeInt(K key, BiFunction<? super K, ? super Integer, ? extends Integer> remappingFunction) {
/* 781 */       throw new UnsupportedOperationException();
/*     */     }
/*     */ 
/*     */     
/*     */     public int merge(K key, int value, BiFunction<? super Integer, ? super Integer, ? extends Integer> remappingFunction) {
/* 786 */       throw new UnsupportedOperationException();
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
/*     */     public Integer getOrDefault(Object key, Integer defaultValue) {
/* 798 */       return this.map.getOrDefault(key, defaultValue);
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
/* 809 */       throw new UnsupportedOperationException();
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     @Deprecated
/*     */     public Integer replace(K key, Integer value) {
/* 820 */       throw new UnsupportedOperationException();
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     @Deprecated
/*     */     public boolean replace(K key, Integer oldValue, Integer newValue) {
/* 831 */       throw new UnsupportedOperationException();
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     @Deprecated
/*     */     public Integer putIfAbsent(K key, Integer value) {
/* 842 */       throw new UnsupportedOperationException();
/*     */     }
/*     */ 
/*     */     
/*     */     public Integer computeIfAbsent(K key, Function<? super K, ? extends Integer> mappingFunction) {
/* 847 */       throw new UnsupportedOperationException();
/*     */     }
/*     */ 
/*     */     
/*     */     public Integer computeIfPresent(K key, BiFunction<? super K, ? super Integer, ? extends Integer> remappingFunction) {
/* 852 */       throw new UnsupportedOperationException();
/*     */     }
/*     */ 
/*     */     
/*     */     public Integer compute(K key, BiFunction<? super K, ? super Integer, ? extends Integer> remappingFunction) {
/* 857 */       throw new UnsupportedOperationException();
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     @Deprecated
/*     */     public Integer merge(K key, Integer value, BiFunction<? super Integer, ? super Integer, ? extends Integer> remappingFunction) {
/* 868 */       throw new UnsupportedOperationException();
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
/*     */   public static <K> Object2IntMap<K> unmodifiable(Object2IntMap<? extends K> m) {
/* 880 */     return new UnmodifiableMap<>(m);
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\i\\unimi\dsi\fastutil\objects\Object2IntMaps.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */