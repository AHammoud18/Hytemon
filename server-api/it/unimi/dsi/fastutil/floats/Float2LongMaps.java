/*     */ package it.unimi.dsi.fastutil.floats;
/*     */ 
/*     */ import it.unimi.dsi.fastutil.HashCommon;
/*     */ import it.unimi.dsi.fastutil.longs.LongCollection;
/*     */ import it.unimi.dsi.fastutil.longs.LongCollections;
/*     */ import it.unimi.dsi.fastutil.longs.LongSets;
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
/*     */ import java.util.Set;
/*     */ import java.util.Spliterator;
/*     */ import java.util.function.BiConsumer;
/*     */ import java.util.function.BiFunction;
/*     */ import java.util.function.Consumer;
/*     */ import java.util.function.DoubleFunction;
/*     */ import java.util.function.DoubleToLongFunction;
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
/*     */ public final class Float2LongMaps
/*     */ {
/*     */   public static ObjectIterator<Float2LongMap.Entry> fastIterator(Float2LongMap map) {
/*  49 */     ObjectSet<Float2LongMap.Entry> entries = map.float2LongEntrySet();
/*  50 */     return (entries instanceof Float2LongMap.FastEntrySet) ? ((Float2LongMap.FastEntrySet)entries).fastIterator() : entries.iterator();
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
/*     */   public static void fastForEach(Float2LongMap map, Consumer<? super Float2LongMap.Entry> consumer) {
/*  65 */     ObjectSet<Float2LongMap.Entry> entries = map.float2LongEntrySet();
/*  66 */     if (entries instanceof Float2LongMap.FastEntrySet) { ((Float2LongMap.FastEntrySet)entries).fastForEach(consumer); }
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
/*     */   public static ObjectIterable<Float2LongMap.Entry> fastIterable(Float2LongMap map) {
/*  82 */     final ObjectSet<Float2LongMap.Entry> entries = map.float2LongEntrySet();
/*  83 */     return (entries instanceof Float2LongMap.FastEntrySet) ? new ObjectIterable<Float2LongMap.Entry>()
/*     */       {
/*     */         public ObjectIterator<Float2LongMap.Entry> iterator() {
/*  86 */           return ((Float2LongMap.FastEntrySet)entries).fastIterator();
/*     */         }
/*     */ 
/*     */         
/*     */         public ObjectSpliterator<Float2LongMap.Entry> spliterator() {
/*  91 */           return entries.spliterator();
/*     */         }
/*     */ 
/*     */         
/*     */         public void forEach(Consumer<? super Float2LongMap.Entry> consumer) {
/*  96 */           ((Float2LongMap.FastEntrySet)entries).fastForEach(consumer);
/*     */         }
/*  98 */       } : (ObjectIterable<Float2LongMap.Entry>)entries;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static class EmptyMap
/*     */     extends Float2LongFunctions.EmptyFunction
/*     */     implements Float2LongMap, Serializable, Cloneable
/*     */   {
/*     */     private static final long serialVersionUID = -7046029254386353129L;
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public boolean containsValue(long v) {
/* 115 */       return false;
/*     */     }
/*     */ 
/*     */     
/*     */     @Deprecated
/*     */     public Long getOrDefault(Object key, Long defaultValue) {
/* 121 */       return defaultValue;
/*     */     }
/*     */ 
/*     */     
/*     */     public long getOrDefault(float key, long defaultValue) {
/* 126 */       return defaultValue;
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
/* 137 */       return false;
/*     */     }
/*     */ 
/*     */     
/*     */     public void putAll(Map<? extends Float, ? extends Long> m) {
/* 142 */       throw new UnsupportedOperationException();
/*     */     }
/*     */ 
/*     */     
/*     */     public ObjectSet<Float2LongMap.Entry> float2LongEntrySet() {
/* 147 */       return (ObjectSet<Float2LongMap.Entry>)ObjectSets.EMPTY_SET;
/*     */     }
/*     */ 
/*     */     
/*     */     public FloatSet keySet() {
/* 152 */       return FloatSets.EMPTY_SET;
/*     */     }
/*     */ 
/*     */     
/*     */     public LongCollection values() {
/* 157 */       return (LongCollection)LongSets.EMPTY_SET;
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     public void forEach(BiConsumer<? super Float, ? super Long> consumer) {}
/*     */ 
/*     */     
/*     */     public Object clone() {
/* 166 */       return Float2LongMaps.EMPTY_MAP;
/*     */     }
/*     */ 
/*     */     
/*     */     public boolean isEmpty() {
/* 171 */       return true;
/*     */     }
/*     */ 
/*     */     
/*     */     public int hashCode() {
/* 176 */       return 0;
/*     */     }
/*     */ 
/*     */     
/*     */     public boolean equals(Object o) {
/* 181 */       if (!(o instanceof Map)) return false; 
/* 182 */       return ((Map)o).isEmpty();
/*     */     }
/*     */ 
/*     */     
/*     */     public String toString() {
/* 187 */       return "{}";
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 195 */   public static final EmptyMap EMPTY_MAP = new EmptyMap();
/*     */ 
/*     */   
/*     */   public static class Singleton
/*     */     extends Float2LongFunctions.Singleton
/*     */     implements Float2LongMap, Serializable, Cloneable
/*     */   {
/*     */     private static final long serialVersionUID = -7046029254386353129L;
/*     */     
/*     */     protected transient ObjectSet<Float2LongMap.Entry> entries;
/*     */     
/*     */     protected transient FloatSet keys;
/*     */     protected transient LongCollection values;
/*     */     
/*     */     protected Singleton(float key, long value) {
/* 210 */       super(key, value);
/*     */     }
/*     */ 
/*     */     
/*     */     public boolean containsValue(long v) {
/* 215 */       return (this.value == v);
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
/* 226 */       return (((Long)ov).longValue() == this.value);
/*     */     }
/*     */ 
/*     */     
/*     */     public void putAll(Map<? extends Float, ? extends Long> m) {
/* 231 */       throw new UnsupportedOperationException();
/*     */     }
/*     */ 
/*     */     
/*     */     public ObjectSet<Float2LongMap.Entry> float2LongEntrySet() {
/* 236 */       if (this.entries == null) this.entries = ObjectSets.singleton(new AbstractFloat2LongMap.BasicEntry(this.key, this.value)); 
/* 237 */       return this.entries;
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
/*     */     public ObjectSet<Map.Entry<Float, Long>> entrySet() {
/* 249 */       return (ObjectSet)float2LongEntrySet();
/*     */     }
/*     */ 
/*     */     
/*     */     public FloatSet keySet() {
/* 254 */       if (this.keys == null) this.keys = FloatSets.singleton(this.key); 
/* 255 */       return this.keys;
/*     */     }
/*     */ 
/*     */     
/*     */     public LongCollection values() {
/* 260 */       if (this.values == null) this.values = (LongCollection)LongSets.singleton(this.value); 
/* 261 */       return this.values;
/*     */     }
/*     */ 
/*     */     
/*     */     public boolean isEmpty() {
/* 266 */       return false;
/*     */     }
/*     */ 
/*     */     
/*     */     public int hashCode() {
/* 271 */       return HashCommon.float2int(this.key) ^ HashCommon.long2int(this.value);
/*     */     }
/*     */ 
/*     */     
/*     */     public boolean equals(Object o) {
/* 276 */       if (o == this) return true; 
/* 277 */       if (!(o instanceof Map)) return false; 
/* 278 */       Map<?, ?> m = (Map<?, ?>)o;
/* 279 */       if (m.size() != 1) return false; 
/* 280 */       return ((Map.Entry)m.entrySet().iterator().next()).equals(entrySet().iterator().next());
/*     */     }
/*     */ 
/*     */     
/*     */     public String toString() {
/* 285 */       return "{" + this.key + "=>" + this.value + "}";
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
/*     */   public static Float2LongMap singleton(float key, long value) {
/* 301 */     return new Singleton(key, value);
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
/*     */   public static Float2LongMap singleton(Float key, Long value) {
/* 316 */     return new Singleton(key.floatValue(), value.longValue());
/*     */   }
/*     */   
/*     */   public static class SynchronizedMap
/*     */     extends Float2LongFunctions.SynchronizedFunction implements Float2LongMap, Serializable {
/*     */     private static final long serialVersionUID = -7046029254386353129L;
/*     */     protected final Float2LongMap map;
/*     */     protected transient ObjectSet<Float2LongMap.Entry> entries;
/*     */     protected transient FloatSet keys;
/*     */     protected transient LongCollection values;
/*     */     
/*     */     protected SynchronizedMap(Float2LongMap m, Object sync) {
/* 328 */       super(m, sync);
/* 329 */       this.map = m;
/*     */     }
/*     */     
/*     */     protected SynchronizedMap(Float2LongMap m) {
/* 333 */       super(m);
/* 334 */       this.map = m;
/*     */     }
/*     */ 
/*     */     
/*     */     public boolean containsValue(long v) {
/* 339 */       synchronized (this.sync) {
/* 340 */         return this.map.containsValue(v);
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
/* 352 */       synchronized (this.sync) {
/* 353 */         return this.map.containsValue(ov);
/*     */       } 
/*     */     }
/*     */ 
/*     */     
/*     */     public void putAll(Map<? extends Float, ? extends Long> m) {
/* 359 */       synchronized (this.sync) {
/* 360 */         this.map.putAll(m);
/*     */       } 
/*     */     }
/*     */ 
/*     */     
/*     */     public ObjectSet<Float2LongMap.Entry> float2LongEntrySet() {
/* 366 */       synchronized (this.sync) {
/* 367 */         if (this.entries == null) this.entries = ObjectSets.synchronize(this.map.float2LongEntrySet(), this.sync); 
/* 368 */         return this.entries;
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
/*     */     public ObjectSet<Map.Entry<Float, Long>> entrySet() {
/* 381 */       return (ObjectSet)float2LongEntrySet();
/*     */     }
/*     */ 
/*     */     
/*     */     public FloatSet keySet() {
/* 386 */       synchronized (this.sync) {
/* 387 */         if (this.keys == null) this.keys = FloatSets.synchronize(this.map.keySet(), this.sync); 
/* 388 */         return this.keys;
/*     */       } 
/*     */     }
/*     */ 
/*     */     
/*     */     public LongCollection values() {
/* 394 */       synchronized (this.sync) {
/* 395 */         if (this.values == null) this.values = LongCollections.synchronize(this.map.values(), this.sync); 
/* 396 */         return this.values;
/*     */       } 
/*     */     }
/*     */ 
/*     */     
/*     */     public boolean isEmpty() {
/* 402 */       synchronized (this.sync) {
/* 403 */         return this.map.isEmpty();
/*     */       } 
/*     */     }
/*     */ 
/*     */     
/*     */     public int hashCode() {
/* 409 */       synchronized (this.sync) {
/* 410 */         return this.map.hashCode();
/*     */       } 
/*     */     }
/*     */ 
/*     */     
/*     */     public boolean equals(Object o) {
/* 416 */       if (o == this) return true; 
/* 417 */       synchronized (this.sync) {
/* 418 */         return this.map.equals(o);
/*     */       } 
/*     */     }
/*     */     
/*     */     private void writeObject(ObjectOutputStream s) throws IOException {
/* 423 */       synchronized (this.sync) {
/* 424 */         s.defaultWriteObject();
/*     */       } 
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     public long getOrDefault(float key, long defaultValue) {
/* 431 */       synchronized (this.sync) {
/* 432 */         return this.map.getOrDefault(key, defaultValue);
/*     */       } 
/*     */     }
/*     */ 
/*     */     
/*     */     public void forEach(BiConsumer<? super Float, ? super Long> action) {
/* 438 */       synchronized (this.sync) {
/* 439 */         this.map.forEach(action);
/*     */       } 
/*     */     }
/*     */ 
/*     */     
/*     */     public void replaceAll(BiFunction<? super Float, ? super Long, ? extends Long> function) {
/* 445 */       synchronized (this.sync) {
/* 446 */         this.map.replaceAll(function);
/*     */       } 
/*     */     }
/*     */ 
/*     */     
/*     */     public long putIfAbsent(float key, long value) {
/* 452 */       synchronized (this.sync) {
/* 453 */         return this.map.putIfAbsent(key, value);
/*     */       } 
/*     */     }
/*     */ 
/*     */     
/*     */     public boolean remove(float key, long value) {
/* 459 */       synchronized (this.sync) {
/* 460 */         return this.map.remove(key, value);
/*     */       } 
/*     */     }
/*     */ 
/*     */     
/*     */     public long replace(float key, long value) {
/* 466 */       synchronized (this.sync) {
/* 467 */         return this.map.replace(key, value);
/*     */       } 
/*     */     }
/*     */ 
/*     */     
/*     */     public boolean replace(float key, long oldValue, long newValue) {
/* 473 */       synchronized (this.sync) {
/* 474 */         return this.map.replace(key, oldValue, newValue);
/*     */       } 
/*     */     }
/*     */ 
/*     */     
/*     */     public long computeIfAbsent(float key, DoubleToLongFunction mappingFunction) {
/* 480 */       synchronized (this.sync) {
/* 481 */         return this.map.computeIfAbsent(key, mappingFunction);
/*     */       } 
/*     */     }
/*     */ 
/*     */     
/*     */     public long computeIfAbsentNullable(float key, DoubleFunction<? extends Long> mappingFunction) {
/* 487 */       synchronized (this.sync) {
/* 488 */         return this.map.computeIfAbsentNullable(key, mappingFunction);
/*     */       } 
/*     */     }
/*     */ 
/*     */     
/*     */     public long computeIfAbsent(float key, Float2LongFunction mappingFunction) {
/* 494 */       synchronized (this.sync) {
/* 495 */         return this.map.computeIfAbsent(key, mappingFunction);
/*     */       } 
/*     */     }
/*     */ 
/*     */     
/*     */     public long computeIfPresent(float key, BiFunction<? super Float, ? super Long, ? extends Long> remappingFunction) {
/* 501 */       synchronized (this.sync) {
/* 502 */         return this.map.computeIfPresent(key, remappingFunction);
/*     */       } 
/*     */     }
/*     */ 
/*     */     
/*     */     public long compute(float key, BiFunction<? super Float, ? super Long, ? extends Long> remappingFunction) {
/* 508 */       synchronized (this.sync) {
/* 509 */         return this.map.compute(key, remappingFunction);
/*     */       } 
/*     */     }
/*     */ 
/*     */     
/*     */     public long merge(float key, long value, BiFunction<? super Long, ? super Long, ? extends Long> remappingFunction) {
/* 515 */       synchronized (this.sync) {
/* 516 */         return this.map.merge(key, value, remappingFunction);
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
/*     */     public Long getOrDefault(Object key, Long defaultValue) {
/* 528 */       synchronized (this.sync) {
/* 529 */         return this.map.getOrDefault(key, defaultValue);
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
/* 541 */       synchronized (this.sync) {
/* 542 */         return this.map.remove(key, value);
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
/*     */     public Long replace(Float key, Long value) {
/* 554 */       synchronized (this.sync) {
/* 555 */         return this.map.replace(key, value);
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
/*     */     public boolean replace(Float key, Long oldValue, Long newValue) {
/* 567 */       synchronized (this.sync) {
/* 568 */         return this.map.replace(key, oldValue, newValue);
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
/*     */     public Long putIfAbsent(Float key, Long value) {
/* 580 */       synchronized (this.sync) {
/* 581 */         return this.map.putIfAbsent(key, value);
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
/*     */     public Long computeIfAbsent(Float key, Function<? super Float, ? extends Long> mappingFunction) {
/* 593 */       synchronized (this.sync) {
/* 594 */         return this.map.computeIfAbsent(key, mappingFunction);
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
/*     */     public Long computeIfPresent(Float key, BiFunction<? super Float, ? super Long, ? extends Long> remappingFunction) {
/* 606 */       synchronized (this.sync) {
/* 607 */         return this.map.computeIfPresent(key, remappingFunction);
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
/*     */     public Long compute(Float key, BiFunction<? super Float, ? super Long, ? extends Long> remappingFunction) {
/* 619 */       synchronized (this.sync) {
/* 620 */         return this.map.compute(key, remappingFunction);
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
/*     */     public Long merge(Float key, Long value, BiFunction<? super Long, ? super Long, ? extends Long> remappingFunction) {
/* 632 */       synchronized (this.sync) {
/* 633 */         return this.map.merge(key, value, remappingFunction);
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
/*     */   public static Float2LongMap synchronize(Float2LongMap m) {
/* 646 */     return new SynchronizedMap(m);
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
/*     */   public static Float2LongMap synchronize(Float2LongMap m, Object sync) {
/* 659 */     return new SynchronizedMap(m, sync);
/*     */   }
/*     */   
/*     */   public static class UnmodifiableMap
/*     */     extends Float2LongFunctions.UnmodifiableFunction implements Float2LongMap, Serializable {
/*     */     private static final long serialVersionUID = -7046029254386353129L;
/*     */     protected final Float2LongMap map;
/*     */     protected transient ObjectSet<Float2LongMap.Entry> entries;
/*     */     protected transient FloatSet keys;
/*     */     protected transient LongCollection values;
/*     */     
/*     */     protected UnmodifiableMap(Float2LongMap m) {
/* 671 */       super(m);
/* 672 */       this.map = m;
/*     */     }
/*     */ 
/*     */     
/*     */     public boolean containsValue(long v) {
/* 677 */       return this.map.containsValue(v);
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
/* 688 */       return this.map.containsValue(ov);
/*     */     }
/*     */ 
/*     */     
/*     */     public void putAll(Map<? extends Float, ? extends Long> m) {
/* 693 */       throw new UnsupportedOperationException();
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     public ObjectSet<Float2LongMap.Entry> float2LongEntrySet() {
/* 699 */       if (this.entries == null) this.entries = ObjectSets.unmodifiable(this.map.float2LongEntrySet()); 
/* 700 */       return this.entries;
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
/*     */     public ObjectSet<Map.Entry<Float, Long>> entrySet() {
/* 712 */       return (ObjectSet)float2LongEntrySet();
/*     */     }
/*     */ 
/*     */     
/*     */     public FloatSet keySet() {
/* 717 */       if (this.keys == null) this.keys = FloatSets.unmodifiable(this.map.keySet()); 
/* 718 */       return this.keys;
/*     */     }
/*     */ 
/*     */     
/*     */     public LongCollection values() {
/* 723 */       if (this.values == null) this.values = LongCollections.unmodifiable(this.map.values()); 
/* 724 */       return this.values;
/*     */     }
/*     */ 
/*     */     
/*     */     public boolean isEmpty() {
/* 729 */       return this.map.isEmpty();
/*     */     }
/*     */ 
/*     */     
/*     */     public int hashCode() {
/* 734 */       return this.map.hashCode();
/*     */     }
/*     */ 
/*     */     
/*     */     public boolean equals(Object o) {
/* 739 */       if (o == this) return true; 
/* 740 */       return this.map.equals(o);
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     public long getOrDefault(float key, long defaultValue) {
/* 746 */       return this.map.getOrDefault(key, defaultValue);
/*     */     }
/*     */ 
/*     */     
/*     */     public void forEach(BiConsumer<? super Float, ? super Long> action) {
/* 751 */       this.map.forEach(action);
/*     */     }
/*     */ 
/*     */     
/*     */     public void replaceAll(BiFunction<? super Float, ? super Long, ? extends Long> function) {
/* 756 */       throw new UnsupportedOperationException();
/*     */     }
/*     */ 
/*     */     
/*     */     public long putIfAbsent(float key, long value) {
/* 761 */       throw new UnsupportedOperationException();
/*     */     }
/*     */ 
/*     */     
/*     */     public boolean remove(float key, long value) {
/* 766 */       throw new UnsupportedOperationException();
/*     */     }
/*     */ 
/*     */     
/*     */     public long replace(float key, long value) {
/* 771 */       throw new UnsupportedOperationException();
/*     */     }
/*     */ 
/*     */     
/*     */     public boolean replace(float key, long oldValue, long newValue) {
/* 776 */       throw new UnsupportedOperationException();
/*     */     }
/*     */ 
/*     */     
/*     */     public long computeIfAbsent(float key, DoubleToLongFunction mappingFunction) {
/* 781 */       throw new UnsupportedOperationException();
/*     */     }
/*     */ 
/*     */     
/*     */     public long computeIfAbsentNullable(float key, DoubleFunction<? extends Long> mappingFunction) {
/* 786 */       throw new UnsupportedOperationException();
/*     */     }
/*     */ 
/*     */     
/*     */     public long computeIfAbsent(float key, Float2LongFunction mappingFunction) {
/* 791 */       throw new UnsupportedOperationException();
/*     */     }
/*     */ 
/*     */     
/*     */     public long computeIfPresent(float key, BiFunction<? super Float, ? super Long, ? extends Long> remappingFunction) {
/* 796 */       throw new UnsupportedOperationException();
/*     */     }
/*     */ 
/*     */     
/*     */     public long compute(float key, BiFunction<? super Float, ? super Long, ? extends Long> remappingFunction) {
/* 801 */       throw new UnsupportedOperationException();
/*     */     }
/*     */ 
/*     */     
/*     */     public long merge(float key, long value, BiFunction<? super Long, ? super Long, ? extends Long> remappingFunction) {
/* 806 */       throw new UnsupportedOperationException();
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     @Deprecated
/*     */     public Long getOrDefault(Object key, Long defaultValue) {
/* 817 */       return this.map.getOrDefault(key, defaultValue);
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
/* 828 */       throw new UnsupportedOperationException();
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     @Deprecated
/*     */     public Long replace(Float key, Long value) {
/* 839 */       throw new UnsupportedOperationException();
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     @Deprecated
/*     */     public boolean replace(Float key, Long oldValue, Long newValue) {
/* 850 */       throw new UnsupportedOperationException();
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     @Deprecated
/*     */     public Long putIfAbsent(Float key, Long value) {
/* 861 */       throw new UnsupportedOperationException();
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     @Deprecated
/*     */     public Long computeIfAbsent(Float key, Function<? super Float, ? extends Long> mappingFunction) {
/* 872 */       throw new UnsupportedOperationException();
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     @Deprecated
/*     */     public Long computeIfPresent(Float key, BiFunction<? super Float, ? super Long, ? extends Long> remappingFunction) {
/* 883 */       throw new UnsupportedOperationException();
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     @Deprecated
/*     */     public Long compute(Float key, BiFunction<? super Float, ? super Long, ? extends Long> remappingFunction) {
/* 894 */       throw new UnsupportedOperationException();
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     @Deprecated
/*     */     public Long merge(Float key, Long value, BiFunction<? super Long, ? super Long, ? extends Long> remappingFunction) {
/* 905 */       throw new UnsupportedOperationException();
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
/*     */   public static Float2LongMap unmodifiable(Float2LongMap m) {
/* 917 */     return new UnmodifiableMap(m);
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\i\\unimi\dsi\fastutil\floats\Float2LongMaps.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */