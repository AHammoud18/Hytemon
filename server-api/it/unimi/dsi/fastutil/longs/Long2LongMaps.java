/*     */ package it.unimi.dsi.fastutil.longs;
/*     */ 
/*     */ import it.unimi.dsi.fastutil.HashCommon;
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
/*     */ import java.util.function.LongFunction;
/*     */ import java.util.function.LongUnaryOperator;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public final class Long2LongMaps
/*     */ {
/*     */   public static ObjectIterator<Long2LongMap.Entry> fastIterator(Long2LongMap map) {
/*  46 */     ObjectSet<Long2LongMap.Entry> entries = map.long2LongEntrySet();
/*  47 */     return (entries instanceof Long2LongMap.FastEntrySet) ? ((Long2LongMap.FastEntrySet)entries).fastIterator() : entries.iterator();
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
/*     */   public static void fastForEach(Long2LongMap map, Consumer<? super Long2LongMap.Entry> consumer) {
/*  62 */     ObjectSet<Long2LongMap.Entry> entries = map.long2LongEntrySet();
/*  63 */     if (entries instanceof Long2LongMap.FastEntrySet) { ((Long2LongMap.FastEntrySet)entries).fastForEach(consumer); }
/*  64 */     else { entries.forEach(consumer); }
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
/*     */   public static ObjectIterable<Long2LongMap.Entry> fastIterable(Long2LongMap map) {
/*  79 */     final ObjectSet<Long2LongMap.Entry> entries = map.long2LongEntrySet();
/*  80 */     return (entries instanceof Long2LongMap.FastEntrySet) ? new ObjectIterable<Long2LongMap.Entry>()
/*     */       {
/*     */         public ObjectIterator<Long2LongMap.Entry> iterator() {
/*  83 */           return ((Long2LongMap.FastEntrySet)entries).fastIterator();
/*     */         }
/*     */ 
/*     */         
/*     */         public ObjectSpliterator<Long2LongMap.Entry> spliterator() {
/*  88 */           return entries.spliterator();
/*     */         }
/*     */ 
/*     */         
/*     */         public void forEach(Consumer<? super Long2LongMap.Entry> consumer) {
/*  93 */           ((Long2LongMap.FastEntrySet)entries).fastForEach(consumer);
/*     */         }
/*  95 */       } : (ObjectIterable<Long2LongMap.Entry>)entries;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static class EmptyMap
/*     */     extends Long2LongFunctions.EmptyFunction
/*     */     implements Long2LongMap, Serializable, Cloneable
/*     */   {
/*     */     private static final long serialVersionUID = -7046029254386353129L;
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public boolean containsValue(long v) {
/* 112 */       return false;
/*     */     }
/*     */ 
/*     */     
/*     */     @Deprecated
/*     */     public Long getOrDefault(Object key, Long defaultValue) {
/* 118 */       return defaultValue;
/*     */     }
/*     */ 
/*     */     
/*     */     public long getOrDefault(long key, long defaultValue) {
/* 123 */       return defaultValue;
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
/* 134 */       return false;
/*     */     }
/*     */ 
/*     */     
/*     */     public void putAll(Map<? extends Long, ? extends Long> m) {
/* 139 */       throw new UnsupportedOperationException();
/*     */     }
/*     */ 
/*     */     
/*     */     public ObjectSet<Long2LongMap.Entry> long2LongEntrySet() {
/* 144 */       return (ObjectSet<Long2LongMap.Entry>)ObjectSets.EMPTY_SET;
/*     */     }
/*     */ 
/*     */     
/*     */     public LongSet keySet() {
/* 149 */       return LongSets.EMPTY_SET;
/*     */     }
/*     */ 
/*     */     
/*     */     public LongCollection values() {
/* 154 */       return LongSets.EMPTY_SET;
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     public void forEach(BiConsumer<? super Long, ? super Long> consumer) {}
/*     */ 
/*     */     
/*     */     public Object clone() {
/* 163 */       return Long2LongMaps.EMPTY_MAP;
/*     */     }
/*     */ 
/*     */     
/*     */     public boolean isEmpty() {
/* 168 */       return true;
/*     */     }
/*     */ 
/*     */     
/*     */     public int hashCode() {
/* 173 */       return 0;
/*     */     }
/*     */ 
/*     */     
/*     */     public boolean equals(Object o) {
/* 178 */       if (!(o instanceof Map)) return false; 
/* 179 */       return ((Map)o).isEmpty();
/*     */     }
/*     */ 
/*     */     
/*     */     public String toString() {
/* 184 */       return "{}";
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 192 */   public static final EmptyMap EMPTY_MAP = new EmptyMap();
/*     */ 
/*     */   
/*     */   public static class Singleton
/*     */     extends Long2LongFunctions.Singleton
/*     */     implements Long2LongMap, Serializable, Cloneable
/*     */   {
/*     */     private static final long serialVersionUID = -7046029254386353129L;
/*     */     
/*     */     protected transient ObjectSet<Long2LongMap.Entry> entries;
/*     */     
/*     */     protected transient LongSet keys;
/*     */     protected transient LongCollection values;
/*     */     
/*     */     protected Singleton(long key, long value) {
/* 207 */       super(key, value);
/*     */     }
/*     */ 
/*     */     
/*     */     public boolean containsValue(long v) {
/* 212 */       return (this.value == v);
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
/* 223 */       return (((Long)ov).longValue() == this.value);
/*     */     }
/*     */ 
/*     */     
/*     */     public void putAll(Map<? extends Long, ? extends Long> m) {
/* 228 */       throw new UnsupportedOperationException();
/*     */     }
/*     */ 
/*     */     
/*     */     public ObjectSet<Long2LongMap.Entry> long2LongEntrySet() {
/* 233 */       if (this.entries == null) this.entries = ObjectSets.singleton(new AbstractLong2LongMap.BasicEntry(this.key, this.value)); 
/* 234 */       return this.entries;
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
/*     */     public ObjectSet<Map.Entry<Long, Long>> entrySet() {
/* 246 */       return (ObjectSet)long2LongEntrySet();
/*     */     }
/*     */ 
/*     */     
/*     */     public LongSet keySet() {
/* 251 */       if (this.keys == null) this.keys = LongSets.singleton(this.key); 
/* 252 */       return this.keys;
/*     */     }
/*     */ 
/*     */     
/*     */     public LongCollection values() {
/* 257 */       if (this.values == null) this.values = LongSets.singleton(this.value); 
/* 258 */       return this.values;
/*     */     }
/*     */ 
/*     */     
/*     */     public boolean isEmpty() {
/* 263 */       return false;
/*     */     }
/*     */ 
/*     */     
/*     */     public int hashCode() {
/* 268 */       return HashCommon.long2int(this.key) ^ HashCommon.long2int(this.value);
/*     */     }
/*     */ 
/*     */     
/*     */     public boolean equals(Object o) {
/* 273 */       if (o == this) return true; 
/* 274 */       if (!(o instanceof Map)) return false; 
/* 275 */       Map<?, ?> m = (Map<?, ?>)o;
/* 276 */       if (m.size() != 1) return false; 
/* 277 */       return ((Map.Entry)m.entrySet().iterator().next()).equals(entrySet().iterator().next());
/*     */     }
/*     */ 
/*     */     
/*     */     public String toString() {
/* 282 */       return "{" + this.key + "=>" + this.value + "}";
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
/*     */   public static Long2LongMap singleton(long key, long value) {
/* 298 */     return new Singleton(key, value);
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
/*     */   public static Long2LongMap singleton(Long key, Long value) {
/* 313 */     return new Singleton(key.longValue(), value.longValue());
/*     */   }
/*     */   
/*     */   public static class SynchronizedMap
/*     */     extends Long2LongFunctions.SynchronizedFunction implements Long2LongMap, Serializable {
/*     */     private static final long serialVersionUID = -7046029254386353129L;
/*     */     protected final Long2LongMap map;
/*     */     protected transient ObjectSet<Long2LongMap.Entry> entries;
/*     */     protected transient LongSet keys;
/*     */     protected transient LongCollection values;
/*     */     
/*     */     protected SynchronizedMap(Long2LongMap m, Object sync) {
/* 325 */       super(m, sync);
/* 326 */       this.map = m;
/*     */     }
/*     */     
/*     */     protected SynchronizedMap(Long2LongMap m) {
/* 330 */       super(m);
/* 331 */       this.map = m;
/*     */     }
/*     */ 
/*     */     
/*     */     public boolean containsValue(long v) {
/* 336 */       synchronized (this.sync) {
/* 337 */         return this.map.containsValue(v);
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
/* 349 */       synchronized (this.sync) {
/* 350 */         return this.map.containsValue(ov);
/*     */       } 
/*     */     }
/*     */ 
/*     */     
/*     */     public void putAll(Map<? extends Long, ? extends Long> m) {
/* 356 */       synchronized (this.sync) {
/* 357 */         this.map.putAll(m);
/*     */       } 
/*     */     }
/*     */ 
/*     */     
/*     */     public ObjectSet<Long2LongMap.Entry> long2LongEntrySet() {
/* 363 */       synchronized (this.sync) {
/* 364 */         if (this.entries == null) this.entries = ObjectSets.synchronize(this.map.long2LongEntrySet(), this.sync); 
/* 365 */         return this.entries;
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
/*     */     public ObjectSet<Map.Entry<Long, Long>> entrySet() {
/* 378 */       return (ObjectSet)long2LongEntrySet();
/*     */     }
/*     */ 
/*     */     
/*     */     public LongSet keySet() {
/* 383 */       synchronized (this.sync) {
/* 384 */         if (this.keys == null) this.keys = LongSets.synchronize(this.map.keySet(), this.sync); 
/* 385 */         return this.keys;
/*     */       } 
/*     */     }
/*     */ 
/*     */     
/*     */     public LongCollection values() {
/* 391 */       synchronized (this.sync) {
/* 392 */         if (this.values == null) this.values = LongCollections.synchronize(this.map.values(), this.sync); 
/* 393 */         return this.values;
/*     */       } 
/*     */     }
/*     */ 
/*     */     
/*     */     public boolean isEmpty() {
/* 399 */       synchronized (this.sync) {
/* 400 */         return this.map.isEmpty();
/*     */       } 
/*     */     }
/*     */ 
/*     */     
/*     */     public int hashCode() {
/* 406 */       synchronized (this.sync) {
/* 407 */         return this.map.hashCode();
/*     */       } 
/*     */     }
/*     */ 
/*     */     
/*     */     public boolean equals(Object o) {
/* 413 */       if (o == this) return true; 
/* 414 */       synchronized (this.sync) {
/* 415 */         return this.map.equals(o);
/*     */       } 
/*     */     }
/*     */     
/*     */     private void writeObject(ObjectOutputStream s) throws IOException {
/* 420 */       synchronized (this.sync) {
/* 421 */         s.defaultWriteObject();
/*     */       } 
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     public long getOrDefault(long key, long defaultValue) {
/* 428 */       synchronized (this.sync) {
/* 429 */         return this.map.getOrDefault(key, defaultValue);
/*     */       } 
/*     */     }
/*     */ 
/*     */     
/*     */     public void forEach(BiConsumer<? super Long, ? super Long> action) {
/* 435 */       synchronized (this.sync) {
/* 436 */         this.map.forEach(action);
/*     */       } 
/*     */     }
/*     */ 
/*     */     
/*     */     public void replaceAll(BiFunction<? super Long, ? super Long, ? extends Long> function) {
/* 442 */       synchronized (this.sync) {
/* 443 */         this.map.replaceAll(function);
/*     */       } 
/*     */     }
/*     */ 
/*     */     
/*     */     public long putIfAbsent(long key, long value) {
/* 449 */       synchronized (this.sync) {
/* 450 */         return this.map.putIfAbsent(key, value);
/*     */       } 
/*     */     }
/*     */ 
/*     */     
/*     */     public boolean remove(long key, long value) {
/* 456 */       synchronized (this.sync) {
/* 457 */         return this.map.remove(key, value);
/*     */       } 
/*     */     }
/*     */ 
/*     */     
/*     */     public long replace(long key, long value) {
/* 463 */       synchronized (this.sync) {
/* 464 */         return this.map.replace(key, value);
/*     */       } 
/*     */     }
/*     */ 
/*     */     
/*     */     public boolean replace(long key, long oldValue, long newValue) {
/* 470 */       synchronized (this.sync) {
/* 471 */         return this.map.replace(key, oldValue, newValue);
/*     */       } 
/*     */     }
/*     */ 
/*     */     
/*     */     public long computeIfAbsent(long key, LongUnaryOperator mappingFunction) {
/* 477 */       synchronized (this.sync) {
/* 478 */         return this.map.computeIfAbsent(key, mappingFunction);
/*     */       } 
/*     */     }
/*     */ 
/*     */     
/*     */     public long computeIfAbsentNullable(long key, LongFunction<? extends Long> mappingFunction) {
/* 484 */       synchronized (this.sync) {
/* 485 */         return this.map.computeIfAbsentNullable(key, mappingFunction);
/*     */       } 
/*     */     }
/*     */ 
/*     */     
/*     */     public long computeIfAbsent(long key, Long2LongFunction mappingFunction) {
/* 491 */       synchronized (this.sync) {
/* 492 */         return this.map.computeIfAbsent(key, mappingFunction);
/*     */       } 
/*     */     }
/*     */ 
/*     */     
/*     */     public long computeIfPresent(long key, BiFunction<? super Long, ? super Long, ? extends Long> remappingFunction) {
/* 498 */       synchronized (this.sync) {
/* 499 */         return this.map.computeIfPresent(key, remappingFunction);
/*     */       } 
/*     */     }
/*     */ 
/*     */     
/*     */     public long compute(long key, BiFunction<? super Long, ? super Long, ? extends Long> remappingFunction) {
/* 505 */       synchronized (this.sync) {
/* 506 */         return this.map.compute(key, remappingFunction);
/*     */       } 
/*     */     }
/*     */ 
/*     */     
/*     */     public long merge(long key, long value, BiFunction<? super Long, ? super Long, ? extends Long> remappingFunction) {
/* 512 */       synchronized (this.sync) {
/* 513 */         return this.map.merge(key, value, remappingFunction);
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
/* 525 */       synchronized (this.sync) {
/* 526 */         return this.map.getOrDefault(key, defaultValue);
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
/* 538 */       synchronized (this.sync) {
/* 539 */         return this.map.remove(key, value);
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
/*     */     public Long replace(Long key, Long value) {
/* 551 */       synchronized (this.sync) {
/* 552 */         return this.map.replace(key, value);
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
/*     */     public boolean replace(Long key, Long oldValue, Long newValue) {
/* 564 */       synchronized (this.sync) {
/* 565 */         return this.map.replace(key, oldValue, newValue);
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
/*     */     public Long putIfAbsent(Long key, Long value) {
/* 577 */       synchronized (this.sync) {
/* 578 */         return this.map.putIfAbsent(key, value);
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
/*     */     public Long computeIfAbsent(Long key, Function<? super Long, ? extends Long> mappingFunction) {
/* 590 */       synchronized (this.sync) {
/* 591 */         return this.map.computeIfAbsent(key, mappingFunction);
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
/*     */     public Long computeIfPresent(Long key, BiFunction<? super Long, ? super Long, ? extends Long> remappingFunction) {
/* 603 */       synchronized (this.sync) {
/* 604 */         return this.map.computeIfPresent(key, remappingFunction);
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
/*     */     public Long compute(Long key, BiFunction<? super Long, ? super Long, ? extends Long> remappingFunction) {
/* 616 */       synchronized (this.sync) {
/* 617 */         return this.map.compute(key, remappingFunction);
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
/*     */     public Long merge(Long key, Long value, BiFunction<? super Long, ? super Long, ? extends Long> remappingFunction) {
/* 629 */       synchronized (this.sync) {
/* 630 */         return this.map.merge(key, value, remappingFunction);
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
/*     */   public static Long2LongMap synchronize(Long2LongMap m) {
/* 643 */     return new SynchronizedMap(m);
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
/*     */   public static Long2LongMap synchronize(Long2LongMap m, Object sync) {
/* 656 */     return new SynchronizedMap(m, sync);
/*     */   }
/*     */   
/*     */   public static class UnmodifiableMap
/*     */     extends Long2LongFunctions.UnmodifiableFunction implements Long2LongMap, Serializable {
/*     */     private static final long serialVersionUID = -7046029254386353129L;
/*     */     protected final Long2LongMap map;
/*     */     protected transient ObjectSet<Long2LongMap.Entry> entries;
/*     */     protected transient LongSet keys;
/*     */     protected transient LongCollection values;
/*     */     
/*     */     protected UnmodifiableMap(Long2LongMap m) {
/* 668 */       super(m);
/* 669 */       this.map = m;
/*     */     }
/*     */ 
/*     */     
/*     */     public boolean containsValue(long v) {
/* 674 */       return this.map.containsValue(v);
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
/* 685 */       return this.map.containsValue(ov);
/*     */     }
/*     */ 
/*     */     
/*     */     public void putAll(Map<? extends Long, ? extends Long> m) {
/* 690 */       throw new UnsupportedOperationException();
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     public ObjectSet<Long2LongMap.Entry> long2LongEntrySet() {
/* 696 */       if (this.entries == null) this.entries = ObjectSets.unmodifiable(this.map.long2LongEntrySet()); 
/* 697 */       return this.entries;
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
/*     */     public ObjectSet<Map.Entry<Long, Long>> entrySet() {
/* 709 */       return (ObjectSet)long2LongEntrySet();
/*     */     }
/*     */ 
/*     */     
/*     */     public LongSet keySet() {
/* 714 */       if (this.keys == null) this.keys = LongSets.unmodifiable(this.map.keySet()); 
/* 715 */       return this.keys;
/*     */     }
/*     */ 
/*     */     
/*     */     public LongCollection values() {
/* 720 */       if (this.values == null) this.values = LongCollections.unmodifiable(this.map.values()); 
/* 721 */       return this.values;
/*     */     }
/*     */ 
/*     */     
/*     */     public boolean isEmpty() {
/* 726 */       return this.map.isEmpty();
/*     */     }
/*     */ 
/*     */     
/*     */     public int hashCode() {
/* 731 */       return this.map.hashCode();
/*     */     }
/*     */ 
/*     */     
/*     */     public boolean equals(Object o) {
/* 736 */       if (o == this) return true; 
/* 737 */       return this.map.equals(o);
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     public long getOrDefault(long key, long defaultValue) {
/* 743 */       return this.map.getOrDefault(key, defaultValue);
/*     */     }
/*     */ 
/*     */     
/*     */     public void forEach(BiConsumer<? super Long, ? super Long> action) {
/* 748 */       this.map.forEach(action);
/*     */     }
/*     */ 
/*     */     
/*     */     public void replaceAll(BiFunction<? super Long, ? super Long, ? extends Long> function) {
/* 753 */       throw new UnsupportedOperationException();
/*     */     }
/*     */ 
/*     */     
/*     */     public long putIfAbsent(long key, long value) {
/* 758 */       throw new UnsupportedOperationException();
/*     */     }
/*     */ 
/*     */     
/*     */     public boolean remove(long key, long value) {
/* 763 */       throw new UnsupportedOperationException();
/*     */     }
/*     */ 
/*     */     
/*     */     public long replace(long key, long value) {
/* 768 */       throw new UnsupportedOperationException();
/*     */     }
/*     */ 
/*     */     
/*     */     public boolean replace(long key, long oldValue, long newValue) {
/* 773 */       throw new UnsupportedOperationException();
/*     */     }
/*     */ 
/*     */     
/*     */     public long computeIfAbsent(long key, LongUnaryOperator mappingFunction) {
/* 778 */       throw new UnsupportedOperationException();
/*     */     }
/*     */ 
/*     */     
/*     */     public long computeIfAbsentNullable(long key, LongFunction<? extends Long> mappingFunction) {
/* 783 */       throw new UnsupportedOperationException();
/*     */     }
/*     */ 
/*     */     
/*     */     public long computeIfAbsent(long key, Long2LongFunction mappingFunction) {
/* 788 */       throw new UnsupportedOperationException();
/*     */     }
/*     */ 
/*     */     
/*     */     public long computeIfPresent(long key, BiFunction<? super Long, ? super Long, ? extends Long> remappingFunction) {
/* 793 */       throw new UnsupportedOperationException();
/*     */     }
/*     */ 
/*     */     
/*     */     public long compute(long key, BiFunction<? super Long, ? super Long, ? extends Long> remappingFunction) {
/* 798 */       throw new UnsupportedOperationException();
/*     */     }
/*     */ 
/*     */     
/*     */     public long merge(long key, long value, BiFunction<? super Long, ? super Long, ? extends Long> remappingFunction) {
/* 803 */       throw new UnsupportedOperationException();
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
/* 814 */       return this.map.getOrDefault(key, defaultValue);
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
/* 825 */       throw new UnsupportedOperationException();
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     @Deprecated
/*     */     public Long replace(Long key, Long value) {
/* 836 */       throw new UnsupportedOperationException();
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     @Deprecated
/*     */     public boolean replace(Long key, Long oldValue, Long newValue) {
/* 847 */       throw new UnsupportedOperationException();
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     @Deprecated
/*     */     public Long putIfAbsent(Long key, Long value) {
/* 858 */       throw new UnsupportedOperationException();
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     @Deprecated
/*     */     public Long computeIfAbsent(Long key, Function<? super Long, ? extends Long> mappingFunction) {
/* 869 */       throw new UnsupportedOperationException();
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     @Deprecated
/*     */     public Long computeIfPresent(Long key, BiFunction<? super Long, ? super Long, ? extends Long> remappingFunction) {
/* 880 */       throw new UnsupportedOperationException();
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     @Deprecated
/*     */     public Long compute(Long key, BiFunction<? super Long, ? super Long, ? extends Long> remappingFunction) {
/* 891 */       throw new UnsupportedOperationException();
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     @Deprecated
/*     */     public Long merge(Long key, Long value, BiFunction<? super Long, ? super Long, ? extends Long> remappingFunction) {
/* 902 */       throw new UnsupportedOperationException();
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
/*     */   public static Long2LongMap unmodifiable(Long2LongMap m) {
/* 914 */     return new UnmodifiableMap(m);
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\i\\unimi\dsi\fastutil\longs\Long2LongMaps.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */