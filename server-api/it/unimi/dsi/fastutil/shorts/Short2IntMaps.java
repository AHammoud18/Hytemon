/*     */ package it.unimi.dsi.fastutil.shorts;
/*     */ 
/*     */ import it.unimi.dsi.fastutil.ints.IntCollection;
/*     */ import it.unimi.dsi.fastutil.ints.IntCollections;
/*     */ import it.unimi.dsi.fastutil.ints.IntSets;
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
/*     */ import java.util.function.Function;
/*     */ import java.util.function.IntFunction;
/*     */ import java.util.function.IntUnaryOperator;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public final class Short2IntMaps
/*     */ {
/*     */   public static ObjectIterator<Short2IntMap.Entry> fastIterator(Short2IntMap map) {
/*  49 */     ObjectSet<Short2IntMap.Entry> entries = map.short2IntEntrySet();
/*  50 */     return (entries instanceof Short2IntMap.FastEntrySet) ? ((Short2IntMap.FastEntrySet)entries).fastIterator() : entries.iterator();
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
/*     */   public static void fastForEach(Short2IntMap map, Consumer<? super Short2IntMap.Entry> consumer) {
/*  65 */     ObjectSet<Short2IntMap.Entry> entries = map.short2IntEntrySet();
/*  66 */     if (entries instanceof Short2IntMap.FastEntrySet) { ((Short2IntMap.FastEntrySet)entries).fastForEach(consumer); }
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
/*     */   public static ObjectIterable<Short2IntMap.Entry> fastIterable(Short2IntMap map) {
/*  82 */     final ObjectSet<Short2IntMap.Entry> entries = map.short2IntEntrySet();
/*  83 */     return (entries instanceof Short2IntMap.FastEntrySet) ? new ObjectIterable<Short2IntMap.Entry>()
/*     */       {
/*     */         public ObjectIterator<Short2IntMap.Entry> iterator() {
/*  86 */           return ((Short2IntMap.FastEntrySet)entries).fastIterator();
/*     */         }
/*     */ 
/*     */         
/*     */         public ObjectSpliterator<Short2IntMap.Entry> spliterator() {
/*  91 */           return entries.spliterator();
/*     */         }
/*     */ 
/*     */         
/*     */         public void forEach(Consumer<? super Short2IntMap.Entry> consumer) {
/*  96 */           ((Short2IntMap.FastEntrySet)entries).fastForEach(consumer);
/*     */         }
/*  98 */       } : (ObjectIterable<Short2IntMap.Entry>)entries;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static class EmptyMap
/*     */     extends Short2IntFunctions.EmptyFunction
/*     */     implements Short2IntMap, Serializable, Cloneable
/*     */   {
/*     */     private static final long serialVersionUID = -7046029254386353129L;
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public boolean containsValue(int v) {
/* 115 */       return false;
/*     */     }
/*     */ 
/*     */     
/*     */     @Deprecated
/*     */     public Integer getOrDefault(Object key, Integer defaultValue) {
/* 121 */       return defaultValue;
/*     */     }
/*     */ 
/*     */     
/*     */     public int getOrDefault(short key, int defaultValue) {
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
/*     */     public void putAll(Map<? extends Short, ? extends Integer> m) {
/* 142 */       throw new UnsupportedOperationException();
/*     */     }
/*     */ 
/*     */     
/*     */     public ObjectSet<Short2IntMap.Entry> short2IntEntrySet() {
/* 147 */       return (ObjectSet<Short2IntMap.Entry>)ObjectSets.EMPTY_SET;
/*     */     }
/*     */ 
/*     */     
/*     */     public ShortSet keySet() {
/* 152 */       return ShortSets.EMPTY_SET;
/*     */     }
/*     */ 
/*     */     
/*     */     public IntCollection values() {
/* 157 */       return (IntCollection)IntSets.EMPTY_SET;
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     public void forEach(BiConsumer<? super Short, ? super Integer> consumer) {}
/*     */ 
/*     */     
/*     */     public Object clone() {
/* 166 */       return Short2IntMaps.EMPTY_MAP;
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
/*     */     extends Short2IntFunctions.Singleton
/*     */     implements Short2IntMap, Serializable, Cloneable
/*     */   {
/*     */     private static final long serialVersionUID = -7046029254386353129L;
/*     */     
/*     */     protected transient ObjectSet<Short2IntMap.Entry> entries;
/*     */     
/*     */     protected transient ShortSet keys;
/*     */     protected transient IntCollection values;
/*     */     
/*     */     protected Singleton(short key, int value) {
/* 210 */       super(key, value);
/*     */     }
/*     */ 
/*     */     
/*     */     public boolean containsValue(int v) {
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
/* 226 */       return (((Integer)ov).intValue() == this.value);
/*     */     }
/*     */ 
/*     */     
/*     */     public void putAll(Map<? extends Short, ? extends Integer> m) {
/* 231 */       throw new UnsupportedOperationException();
/*     */     }
/*     */ 
/*     */     
/*     */     public ObjectSet<Short2IntMap.Entry> short2IntEntrySet() {
/* 236 */       if (this.entries == null) this.entries = ObjectSets.singleton(new AbstractShort2IntMap.BasicEntry(this.key, this.value)); 
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
/*     */     public ObjectSet<Map.Entry<Short, Integer>> entrySet() {
/* 249 */       return (ObjectSet)short2IntEntrySet();
/*     */     }
/*     */ 
/*     */     
/*     */     public ShortSet keySet() {
/* 254 */       if (this.keys == null) this.keys = ShortSets.singleton(this.key); 
/* 255 */       return this.keys;
/*     */     }
/*     */ 
/*     */     
/*     */     public IntCollection values() {
/* 260 */       if (this.values == null) this.values = (IntCollection)IntSets.singleton(this.value); 
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
/* 271 */       return this.key ^ this.value;
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
/*     */   public static Short2IntMap singleton(short key, int value) {
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
/*     */   public static Short2IntMap singleton(Short key, Integer value) {
/* 316 */     return new Singleton(key.shortValue(), value.intValue());
/*     */   }
/*     */   
/*     */   public static class SynchronizedMap
/*     */     extends Short2IntFunctions.SynchronizedFunction implements Short2IntMap, Serializable {
/*     */     private static final long serialVersionUID = -7046029254386353129L;
/*     */     protected final Short2IntMap map;
/*     */     protected transient ObjectSet<Short2IntMap.Entry> entries;
/*     */     protected transient ShortSet keys;
/*     */     protected transient IntCollection values;
/*     */     
/*     */     protected SynchronizedMap(Short2IntMap m, Object sync) {
/* 328 */       super(m, sync);
/* 329 */       this.map = m;
/*     */     }
/*     */     
/*     */     protected SynchronizedMap(Short2IntMap m) {
/* 333 */       super(m);
/* 334 */       this.map = m;
/*     */     }
/*     */ 
/*     */     
/*     */     public boolean containsValue(int v) {
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
/*     */     public void putAll(Map<? extends Short, ? extends Integer> m) {
/* 359 */       synchronized (this.sync) {
/* 360 */         this.map.putAll(m);
/*     */       } 
/*     */     }
/*     */ 
/*     */     
/*     */     public ObjectSet<Short2IntMap.Entry> short2IntEntrySet() {
/* 366 */       synchronized (this.sync) {
/* 367 */         if (this.entries == null) this.entries = ObjectSets.synchronize(this.map.short2IntEntrySet(), this.sync); 
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
/*     */     public ObjectSet<Map.Entry<Short, Integer>> entrySet() {
/* 381 */       return (ObjectSet)short2IntEntrySet();
/*     */     }
/*     */ 
/*     */     
/*     */     public ShortSet keySet() {
/* 386 */       synchronized (this.sync) {
/* 387 */         if (this.keys == null) this.keys = ShortSets.synchronize(this.map.keySet(), this.sync); 
/* 388 */         return this.keys;
/*     */       } 
/*     */     }
/*     */ 
/*     */     
/*     */     public IntCollection values() {
/* 394 */       synchronized (this.sync) {
/* 395 */         if (this.values == null) this.values = IntCollections.synchronize(this.map.values(), this.sync); 
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
/*     */     public int getOrDefault(short key, int defaultValue) {
/* 431 */       synchronized (this.sync) {
/* 432 */         return this.map.getOrDefault(key, defaultValue);
/*     */       } 
/*     */     }
/*     */ 
/*     */     
/*     */     public void forEach(BiConsumer<? super Short, ? super Integer> action) {
/* 438 */       synchronized (this.sync) {
/* 439 */         this.map.forEach(action);
/*     */       } 
/*     */     }
/*     */ 
/*     */     
/*     */     public void replaceAll(BiFunction<? super Short, ? super Integer, ? extends Integer> function) {
/* 445 */       synchronized (this.sync) {
/* 446 */         this.map.replaceAll(function);
/*     */       } 
/*     */     }
/*     */ 
/*     */     
/*     */     public int putIfAbsent(short key, int value) {
/* 452 */       synchronized (this.sync) {
/* 453 */         return this.map.putIfAbsent(key, value);
/*     */       } 
/*     */     }
/*     */ 
/*     */     
/*     */     public boolean remove(short key, int value) {
/* 459 */       synchronized (this.sync) {
/* 460 */         return this.map.remove(key, value);
/*     */       } 
/*     */     }
/*     */ 
/*     */     
/*     */     public int replace(short key, int value) {
/* 466 */       synchronized (this.sync) {
/* 467 */         return this.map.replace(key, value);
/*     */       } 
/*     */     }
/*     */ 
/*     */     
/*     */     public boolean replace(short key, int oldValue, int newValue) {
/* 473 */       synchronized (this.sync) {
/* 474 */         return this.map.replace(key, oldValue, newValue);
/*     */       } 
/*     */     }
/*     */ 
/*     */     
/*     */     public int computeIfAbsent(short key, IntUnaryOperator mappingFunction) {
/* 480 */       synchronized (this.sync) {
/* 481 */         return this.map.computeIfAbsent(key, mappingFunction);
/*     */       } 
/*     */     }
/*     */ 
/*     */     
/*     */     public int computeIfAbsentNullable(short key, IntFunction<? extends Integer> mappingFunction) {
/* 487 */       synchronized (this.sync) {
/* 488 */         return this.map.computeIfAbsentNullable(key, mappingFunction);
/*     */       } 
/*     */     }
/*     */ 
/*     */     
/*     */     public int computeIfAbsent(short key, Short2IntFunction mappingFunction) {
/* 494 */       synchronized (this.sync) {
/* 495 */         return this.map.computeIfAbsent(key, mappingFunction);
/*     */       } 
/*     */     }
/*     */ 
/*     */     
/*     */     public int computeIfPresent(short key, BiFunction<? super Short, ? super Integer, ? extends Integer> remappingFunction) {
/* 501 */       synchronized (this.sync) {
/* 502 */         return this.map.computeIfPresent(key, remappingFunction);
/*     */       } 
/*     */     }
/*     */ 
/*     */     
/*     */     public int compute(short key, BiFunction<? super Short, ? super Integer, ? extends Integer> remappingFunction) {
/* 508 */       synchronized (this.sync) {
/* 509 */         return this.map.compute(key, remappingFunction);
/*     */       } 
/*     */     }
/*     */ 
/*     */     
/*     */     public int merge(short key, int value, BiFunction<? super Integer, ? super Integer, ? extends Integer> remappingFunction) {
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
/*     */     public Integer getOrDefault(Object key, Integer defaultValue) {
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
/*     */     public Integer replace(Short key, Integer value) {
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
/*     */     public boolean replace(Short key, Integer oldValue, Integer newValue) {
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
/*     */     public Integer putIfAbsent(Short key, Integer value) {
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
/*     */     public Integer computeIfAbsent(Short key, Function<? super Short, ? extends Integer> mappingFunction) {
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
/*     */     public Integer computeIfPresent(Short key, BiFunction<? super Short, ? super Integer, ? extends Integer> remappingFunction) {
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
/*     */     public Integer compute(Short key, BiFunction<? super Short, ? super Integer, ? extends Integer> remappingFunction) {
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
/*     */     public Integer merge(Short key, Integer value, BiFunction<? super Integer, ? super Integer, ? extends Integer> remappingFunction) {
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
/*     */   public static Short2IntMap synchronize(Short2IntMap m) {
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
/*     */   public static Short2IntMap synchronize(Short2IntMap m, Object sync) {
/* 659 */     return new SynchronizedMap(m, sync);
/*     */   }
/*     */   
/*     */   public static class UnmodifiableMap
/*     */     extends Short2IntFunctions.UnmodifiableFunction implements Short2IntMap, Serializable {
/*     */     private static final long serialVersionUID = -7046029254386353129L;
/*     */     protected final Short2IntMap map;
/*     */     protected transient ObjectSet<Short2IntMap.Entry> entries;
/*     */     protected transient ShortSet keys;
/*     */     protected transient IntCollection values;
/*     */     
/*     */     protected UnmodifiableMap(Short2IntMap m) {
/* 671 */       super(m);
/* 672 */       this.map = m;
/*     */     }
/*     */ 
/*     */     
/*     */     public boolean containsValue(int v) {
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
/*     */     public void putAll(Map<? extends Short, ? extends Integer> m) {
/* 693 */       throw new UnsupportedOperationException();
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     public ObjectSet<Short2IntMap.Entry> short2IntEntrySet() {
/* 699 */       if (this.entries == null) this.entries = ObjectSets.unmodifiable(this.map.short2IntEntrySet()); 
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
/*     */     public ObjectSet<Map.Entry<Short, Integer>> entrySet() {
/* 712 */       return (ObjectSet)short2IntEntrySet();
/*     */     }
/*     */ 
/*     */     
/*     */     public ShortSet keySet() {
/* 717 */       if (this.keys == null) this.keys = ShortSets.unmodifiable(this.map.keySet()); 
/* 718 */       return this.keys;
/*     */     }
/*     */ 
/*     */     
/*     */     public IntCollection values() {
/* 723 */       if (this.values == null) this.values = IntCollections.unmodifiable(this.map.values()); 
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
/*     */     public int getOrDefault(short key, int defaultValue) {
/* 746 */       return this.map.getOrDefault(key, defaultValue);
/*     */     }
/*     */ 
/*     */     
/*     */     public void forEach(BiConsumer<? super Short, ? super Integer> action) {
/* 751 */       this.map.forEach(action);
/*     */     }
/*     */ 
/*     */     
/*     */     public void replaceAll(BiFunction<? super Short, ? super Integer, ? extends Integer> function) {
/* 756 */       throw new UnsupportedOperationException();
/*     */     }
/*     */ 
/*     */     
/*     */     public int putIfAbsent(short key, int value) {
/* 761 */       throw new UnsupportedOperationException();
/*     */     }
/*     */ 
/*     */     
/*     */     public boolean remove(short key, int value) {
/* 766 */       throw new UnsupportedOperationException();
/*     */     }
/*     */ 
/*     */     
/*     */     public int replace(short key, int value) {
/* 771 */       throw new UnsupportedOperationException();
/*     */     }
/*     */ 
/*     */     
/*     */     public boolean replace(short key, int oldValue, int newValue) {
/* 776 */       throw new UnsupportedOperationException();
/*     */     }
/*     */ 
/*     */     
/*     */     public int computeIfAbsent(short key, IntUnaryOperator mappingFunction) {
/* 781 */       throw new UnsupportedOperationException();
/*     */     }
/*     */ 
/*     */     
/*     */     public int computeIfAbsentNullable(short key, IntFunction<? extends Integer> mappingFunction) {
/* 786 */       throw new UnsupportedOperationException();
/*     */     }
/*     */ 
/*     */     
/*     */     public int computeIfAbsent(short key, Short2IntFunction mappingFunction) {
/* 791 */       throw new UnsupportedOperationException();
/*     */     }
/*     */ 
/*     */     
/*     */     public int computeIfPresent(short key, BiFunction<? super Short, ? super Integer, ? extends Integer> remappingFunction) {
/* 796 */       throw new UnsupportedOperationException();
/*     */     }
/*     */ 
/*     */     
/*     */     public int compute(short key, BiFunction<? super Short, ? super Integer, ? extends Integer> remappingFunction) {
/* 801 */       throw new UnsupportedOperationException();
/*     */     }
/*     */ 
/*     */     
/*     */     public int merge(short key, int value, BiFunction<? super Integer, ? super Integer, ? extends Integer> remappingFunction) {
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
/*     */     public Integer getOrDefault(Object key, Integer defaultValue) {
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
/*     */     public Integer replace(Short key, Integer value) {
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
/*     */     public boolean replace(Short key, Integer oldValue, Integer newValue) {
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
/*     */     public Integer putIfAbsent(Short key, Integer value) {
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
/*     */     public Integer computeIfAbsent(Short key, Function<? super Short, ? extends Integer> mappingFunction) {
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
/*     */     public Integer computeIfPresent(Short key, BiFunction<? super Short, ? super Integer, ? extends Integer> remappingFunction) {
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
/*     */     public Integer compute(Short key, BiFunction<? super Short, ? super Integer, ? extends Integer> remappingFunction) {
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
/*     */     public Integer merge(Short key, Integer value, BiFunction<? super Integer, ? super Integer, ? extends Integer> remappingFunction) {
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
/*     */   public static Short2IntMap unmodifiable(Short2IntMap m) {
/* 917 */     return new UnmodifiableMap(m);
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\i\\unimi\dsi\fastutil\shorts\Short2IntMaps.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */