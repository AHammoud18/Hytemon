/*     */ package it.unimi.dsi.fastutil.objects;
/*     */ 
/*     */ import it.unimi.dsi.fastutil.HashCommon;
/*     */ import it.unimi.dsi.fastutil.floats.FloatCollection;
/*     */ import it.unimi.dsi.fastutil.floats.FloatCollections;
/*     */ import it.unimi.dsi.fastutil.floats.FloatSets;
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
/*     */ import java.util.function.ToDoubleFunction;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public final class Reference2FloatMaps
/*     */ {
/*     */   public static <K> ObjectIterator<Reference2FloatMap.Entry<K>> fastIterator(Reference2FloatMap<K> map) {
/*  44 */     ObjectSet<Reference2FloatMap.Entry<K>> entries = map.reference2FloatEntrySet();
/*  45 */     return (entries instanceof Reference2FloatMap.FastEntrySet) ? ((Reference2FloatMap.FastEntrySet)entries).fastIterator() : entries.iterator();
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
/*     */   public static <K> void fastForEach(Reference2FloatMap<K> map, Consumer<? super Reference2FloatMap.Entry<K>> consumer) {
/*  60 */     ObjectSet<Reference2FloatMap.Entry<K>> entries = map.reference2FloatEntrySet();
/*  61 */     if (entries instanceof Reference2FloatMap.FastEntrySet) { ((Reference2FloatMap.FastEntrySet)entries).fastForEach(consumer); }
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
/*     */   public static <K> ObjectIterable<Reference2FloatMap.Entry<K>> fastIterable(Reference2FloatMap<K> map) {
/*  77 */     final ObjectSet<Reference2FloatMap.Entry<K>> entries = map.reference2FloatEntrySet();
/*  78 */     return (entries instanceof Reference2FloatMap.FastEntrySet) ? (ObjectIterable)new ObjectIterable<Reference2FloatMap.Entry<Reference2FloatMap.Entry<K>>>()
/*     */       {
/*     */         public ObjectIterator<Reference2FloatMap.Entry<K>> iterator() {
/*  81 */           return ((Reference2FloatMap.FastEntrySet<K>)entries).fastIterator();
/*     */         }
/*     */ 
/*     */         
/*     */         public ObjectSpliterator<Reference2FloatMap.Entry<K>> spliterator() {
/*  86 */           return entries.spliterator();
/*     */         }
/*     */ 
/*     */         
/*     */         public void forEach(Consumer<? super Reference2FloatMap.Entry<K>> consumer) {
/*  91 */           ((Reference2FloatMap.FastEntrySet<K>)entries).fastForEach(consumer);
/*     */         }
/*  93 */       } : entries;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static class EmptyMap<K>
/*     */     extends Reference2FloatFunctions.EmptyFunction<K>
/*     */     implements Reference2FloatMap<K>, Serializable, Cloneable
/*     */   {
/*     */     private static final long serialVersionUID = -7046029254386353129L;
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public boolean containsValue(float v) {
/* 110 */       return false;
/*     */     }
/*     */ 
/*     */     
/*     */     @Deprecated
/*     */     public Float getOrDefault(Object key, Float defaultValue) {
/* 116 */       return defaultValue;
/*     */     }
/*     */ 
/*     */     
/*     */     public float getOrDefault(Object key, float defaultValue) {
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
/*     */     public void putAll(Map<? extends K, ? extends Float> m) {
/* 137 */       throw new UnsupportedOperationException();
/*     */     }
/*     */ 
/*     */     
/*     */     public ObjectSet<Reference2FloatMap.Entry<K>> reference2FloatEntrySet() {
/* 142 */       return ObjectSets.EMPTY_SET;
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     public ReferenceSet<K> keySet() {
/* 148 */       return ReferenceSets.EMPTY_SET;
/*     */     }
/*     */ 
/*     */     
/*     */     public FloatCollection values() {
/* 153 */       return (FloatCollection)FloatSets.EMPTY_SET;
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     public void forEach(BiConsumer<? super K, ? super Float> consumer) {}
/*     */ 
/*     */     
/*     */     public Object clone() {
/* 162 */       return Reference2FloatMaps.EMPTY_MAP;
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
/*     */   public static <K> Reference2FloatMap<K> emptyMap() {
/* 203 */     return EMPTY_MAP;
/*     */   }
/*     */ 
/*     */   
/*     */   public static class Singleton<K>
/*     */     extends Reference2FloatFunctions.Singleton<K>
/*     */     implements Reference2FloatMap<K>, Serializable, Cloneable
/*     */   {
/*     */     private static final long serialVersionUID = -7046029254386353129L;
/*     */     
/*     */     protected transient ObjectSet<Reference2FloatMap.Entry<K>> entries;
/*     */     
/*     */     protected transient ReferenceSet<K> keys;
/*     */     protected transient FloatCollection values;
/*     */     
/*     */     protected Singleton(K key, float value) {
/* 219 */       super(key, value);
/*     */     }
/*     */ 
/*     */     
/*     */     public boolean containsValue(float v) {
/* 224 */       return (Float.floatToIntBits(this.value) == Float.floatToIntBits(v));
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
/* 235 */       return (Float.floatToIntBits(((Float)ov).floatValue()) == Float.floatToIntBits(this.value));
/*     */     }
/*     */ 
/*     */     
/*     */     public void putAll(Map<? extends K, ? extends Float> m) {
/* 240 */       throw new UnsupportedOperationException();
/*     */     }
/*     */ 
/*     */     
/*     */     public ObjectSet<Reference2FloatMap.Entry<K>> reference2FloatEntrySet() {
/* 245 */       if (this.entries == null) this.entries = ObjectSets.singleton(new AbstractReference2FloatMap.BasicEntry<>(this.key, this.value)); 
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
/*     */     public ObjectSet<Map.Entry<K, Float>> entrySet() {
/* 258 */       return (ObjectSet)reference2FloatEntrySet();
/*     */     }
/*     */ 
/*     */     
/*     */     public ReferenceSet<K> keySet() {
/* 263 */       if (this.keys == null) this.keys = ReferenceSets.singleton(this.key); 
/* 264 */       return this.keys;
/*     */     }
/*     */ 
/*     */     
/*     */     public FloatCollection values() {
/* 269 */       if (this.values == null) this.values = (FloatCollection)FloatSets.singleton(this.value); 
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
/* 280 */       return System.identityHashCode(this.key) ^ HashCommon.float2int(this.value);
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
/*     */   public static <K> Reference2FloatMap<K> singleton(K key, float value) {
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
/*     */   public static <K> Reference2FloatMap<K> singleton(K key, Float value) {
/* 325 */     return new Singleton<>(key, value.floatValue());
/*     */   }
/*     */   
/*     */   public static class SynchronizedMap<K>
/*     */     extends Reference2FloatFunctions.SynchronizedFunction<K> implements Reference2FloatMap<K>, Serializable {
/*     */     private static final long serialVersionUID = -7046029254386353129L;
/*     */     protected final Reference2FloatMap<K> map;
/*     */     protected transient ObjectSet<Reference2FloatMap.Entry<K>> entries;
/*     */     protected transient ReferenceSet<K> keys;
/*     */     protected transient FloatCollection values;
/*     */     
/*     */     protected SynchronizedMap(Reference2FloatMap<K> m, Object sync) {
/* 337 */       super(m, sync);
/* 338 */       this.map = m;
/*     */     }
/*     */     
/*     */     protected SynchronizedMap(Reference2FloatMap<K> m) {
/* 342 */       super(m);
/* 343 */       this.map = m;
/*     */     }
/*     */ 
/*     */     
/*     */     public boolean containsValue(float v) {
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
/*     */     public void putAll(Map<? extends K, ? extends Float> m) {
/* 368 */       synchronized (this.sync) {
/* 369 */         this.map.putAll(m);
/*     */       } 
/*     */     }
/*     */ 
/*     */     
/*     */     public ObjectSet<Reference2FloatMap.Entry<K>> reference2FloatEntrySet() {
/* 375 */       synchronized (this.sync) {
/* 376 */         if (this.entries == null) this.entries = ObjectSets.synchronize(this.map.reference2FloatEntrySet(), this.sync); 
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
/*     */     public ObjectSet<Map.Entry<K, Float>> entrySet() {
/* 390 */       return (ObjectSet)reference2FloatEntrySet();
/*     */     }
/*     */ 
/*     */     
/*     */     public ReferenceSet<K> keySet() {
/* 395 */       synchronized (this.sync) {
/* 396 */         if (this.keys == null) this.keys = ReferenceSets.synchronize(this.map.keySet(), this.sync); 
/* 397 */         return this.keys;
/*     */       } 
/*     */     }
/*     */ 
/*     */     
/*     */     public FloatCollection values() {
/* 403 */       synchronized (this.sync) {
/* 404 */         if (this.values == null) this.values = FloatCollections.synchronize(this.map.values(), this.sync); 
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
/*     */     public float getOrDefault(Object key, float defaultValue) {
/* 440 */       synchronized (this.sync) {
/* 441 */         return this.map.getOrDefault(key, defaultValue);
/*     */       } 
/*     */     }
/*     */ 
/*     */     
/*     */     public void forEach(BiConsumer<? super K, ? super Float> action) {
/* 447 */       synchronized (this.sync) {
/* 448 */         this.map.forEach(action);
/*     */       } 
/*     */     }
/*     */ 
/*     */     
/*     */     public void replaceAll(BiFunction<? super K, ? super Float, ? extends Float> function) {
/* 454 */       synchronized (this.sync) {
/* 455 */         this.map.replaceAll(function);
/*     */       } 
/*     */     }
/*     */ 
/*     */     
/*     */     public float putIfAbsent(K key, float value) {
/* 461 */       synchronized (this.sync) {
/* 462 */         return this.map.putIfAbsent(key, value);
/*     */       } 
/*     */     }
/*     */ 
/*     */     
/*     */     public boolean remove(Object key, float value) {
/* 468 */       synchronized (this.sync) {
/* 469 */         return this.map.remove(key, value);
/*     */       } 
/*     */     }
/*     */ 
/*     */     
/*     */     public float replace(K key, float value) {
/* 475 */       synchronized (this.sync) {
/* 476 */         return this.map.replace(key, value);
/*     */       } 
/*     */     }
/*     */ 
/*     */     
/*     */     public boolean replace(K key, float oldValue, float newValue) {
/* 482 */       synchronized (this.sync) {
/* 483 */         return this.map.replace(key, oldValue, newValue);
/*     */       } 
/*     */     }
/*     */ 
/*     */     
/*     */     public float computeIfAbsent(K key, ToDoubleFunction<? super K> mappingFunction) {
/* 489 */       synchronized (this.sync) {
/* 490 */         return this.map.computeIfAbsent(key, mappingFunction);
/*     */       } 
/*     */     }
/*     */ 
/*     */     
/*     */     public float computeIfAbsent(K key, Reference2FloatFunction<? super K> mappingFunction) {
/* 496 */       synchronized (this.sync) {
/* 497 */         return this.map.computeIfAbsent(key, mappingFunction);
/*     */       } 
/*     */     }
/*     */ 
/*     */     
/*     */     public float computeFloatIfPresent(K key, BiFunction<? super K, ? super Float, ? extends Float> remappingFunction) {
/* 503 */       synchronized (this.sync) {
/* 504 */         return this.map.computeFloatIfPresent(key, remappingFunction);
/*     */       } 
/*     */     }
/*     */ 
/*     */     
/*     */     public float computeFloat(K key, BiFunction<? super K, ? super Float, ? extends Float> remappingFunction) {
/* 510 */       synchronized (this.sync) {
/* 511 */         return this.map.computeFloat(key, remappingFunction);
/*     */       } 
/*     */     }
/*     */ 
/*     */     
/*     */     public float merge(K key, float value, BiFunction<? super Float, ? super Float, ? extends Float> remappingFunction) {
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
/*     */     public Float getOrDefault(Object key, Float defaultValue) {
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
/*     */     public Float replace(K key, Float value) {
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
/*     */     public boolean replace(K key, Float oldValue, Float newValue) {
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
/*     */     public Float putIfAbsent(K key, Float value) {
/* 582 */       synchronized (this.sync) {
/* 583 */         return this.map.putIfAbsent(key, value);
/*     */       } 
/*     */     }
/*     */ 
/*     */     
/*     */     public Float computeIfAbsent(K key, Function<? super K, ? extends Float> mappingFunction) {
/* 589 */       synchronized (this.sync) {
/* 590 */         return this.map.computeIfAbsent(key, mappingFunction);
/*     */       } 
/*     */     }
/*     */ 
/*     */     
/*     */     public Float computeIfPresent(K key, BiFunction<? super K, ? super Float, ? extends Float> remappingFunction) {
/* 596 */       synchronized (this.sync) {
/* 597 */         return this.map.computeIfPresent(key, remappingFunction);
/*     */       } 
/*     */     }
/*     */ 
/*     */     
/*     */     public Float compute(K key, BiFunction<? super K, ? super Float, ? extends Float> remappingFunction) {
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
/*     */     public Float merge(K key, Float value, BiFunction<? super Float, ? super Float, ? extends Float> remappingFunction) {
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
/*     */   public static <K> Reference2FloatMap<K> synchronize(Reference2FloatMap<K> m) {
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
/*     */   public static <K> Reference2FloatMap<K> synchronize(Reference2FloatMap<K> m, Object sync) {
/* 643 */     return new SynchronizedMap<>(m, sync);
/*     */   }
/*     */   
/*     */   public static class UnmodifiableMap<K>
/*     */     extends Reference2FloatFunctions.UnmodifiableFunction<K> implements Reference2FloatMap<K>, Serializable {
/*     */     private static final long serialVersionUID = -7046029254386353129L;
/*     */     protected final Reference2FloatMap<? extends K> map;
/*     */     protected transient ObjectSet<Reference2FloatMap.Entry<K>> entries;
/*     */     protected transient ReferenceSet<K> keys;
/*     */     protected transient FloatCollection values;
/*     */     
/*     */     protected UnmodifiableMap(Reference2FloatMap<? extends K> m) {
/* 655 */       super(m);
/* 656 */       this.map = m;
/*     */     }
/*     */ 
/*     */     
/*     */     public boolean containsValue(float v) {
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
/*     */     public void putAll(Map<? extends K, ? extends Float> m) {
/* 677 */       throw new UnsupportedOperationException();
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     public ObjectSet<Reference2FloatMap.Entry<K>> reference2FloatEntrySet() {
/* 683 */       if (this.entries == null) this.entries = ObjectSets.unmodifiable((ObjectSet)this.map.reference2FloatEntrySet()); 
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
/*     */     public ObjectSet<Map.Entry<K, Float>> entrySet() {
/* 696 */       return (ObjectSet)reference2FloatEntrySet();
/*     */     }
/*     */ 
/*     */     
/*     */     public ReferenceSet<K> keySet() {
/* 701 */       if (this.keys == null) this.keys = ReferenceSets.unmodifiable(this.map.keySet()); 
/* 702 */       return this.keys;
/*     */     }
/*     */ 
/*     */     
/*     */     public FloatCollection values() {
/* 707 */       if (this.values == null) this.values = FloatCollections.unmodifiable(this.map.values()); 
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
/*     */     public float getOrDefault(Object key, float defaultValue) {
/* 731 */       return this.map.getOrDefault(key, defaultValue);
/*     */     }
/*     */ 
/*     */     
/*     */     public void forEach(BiConsumer<? super K, ? super Float> action) {
/* 736 */       this.map.forEach(action);
/*     */     }
/*     */ 
/*     */     
/*     */     public void replaceAll(BiFunction<? super K, ? super Float, ? extends Float> function) {
/* 741 */       throw new UnsupportedOperationException();
/*     */     }
/*     */ 
/*     */     
/*     */     public float putIfAbsent(K key, float value) {
/* 746 */       throw new UnsupportedOperationException();
/*     */     }
/*     */ 
/*     */     
/*     */     public boolean remove(Object key, float value) {
/* 751 */       throw new UnsupportedOperationException();
/*     */     }
/*     */ 
/*     */     
/*     */     public float replace(K key, float value) {
/* 756 */       throw new UnsupportedOperationException();
/*     */     }
/*     */ 
/*     */     
/*     */     public boolean replace(K key, float oldValue, float newValue) {
/* 761 */       throw new UnsupportedOperationException();
/*     */     }
/*     */ 
/*     */     
/*     */     public float computeIfAbsent(K key, ToDoubleFunction<? super K> mappingFunction) {
/* 766 */       throw new UnsupportedOperationException();
/*     */     }
/*     */ 
/*     */     
/*     */     public float computeIfAbsent(K key, Reference2FloatFunction<? super K> mappingFunction) {
/* 771 */       throw new UnsupportedOperationException();
/*     */     }
/*     */ 
/*     */     
/*     */     public float computeFloatIfPresent(K key, BiFunction<? super K, ? super Float, ? extends Float> remappingFunction) {
/* 776 */       throw new UnsupportedOperationException();
/*     */     }
/*     */ 
/*     */     
/*     */     public float computeFloat(K key, BiFunction<? super K, ? super Float, ? extends Float> remappingFunction) {
/* 781 */       throw new UnsupportedOperationException();
/*     */     }
/*     */ 
/*     */     
/*     */     public float merge(K key, float value, BiFunction<? super Float, ? super Float, ? extends Float> remappingFunction) {
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
/*     */     public Float getOrDefault(Object key, Float defaultValue) {
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
/*     */     public Float replace(K key, Float value) {
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
/*     */     public boolean replace(K key, Float oldValue, Float newValue) {
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
/*     */     public Float putIfAbsent(K key, Float value) {
/* 842 */       throw new UnsupportedOperationException();
/*     */     }
/*     */ 
/*     */     
/*     */     public Float computeIfAbsent(K key, Function<? super K, ? extends Float> mappingFunction) {
/* 847 */       throw new UnsupportedOperationException();
/*     */     }
/*     */ 
/*     */     
/*     */     public Float computeIfPresent(K key, BiFunction<? super K, ? super Float, ? extends Float> remappingFunction) {
/* 852 */       throw new UnsupportedOperationException();
/*     */     }
/*     */ 
/*     */     
/*     */     public Float compute(K key, BiFunction<? super K, ? super Float, ? extends Float> remappingFunction) {
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
/*     */     public Float merge(K key, Float value, BiFunction<? super Float, ? super Float, ? extends Float> remappingFunction) {
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
/*     */   public static <K> Reference2FloatMap<K> unmodifiable(Reference2FloatMap<? extends K> m) {
/* 880 */     return new UnmodifiableMap<>(m);
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\i\\unimi\dsi\fastutil\objects\Reference2FloatMaps.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */