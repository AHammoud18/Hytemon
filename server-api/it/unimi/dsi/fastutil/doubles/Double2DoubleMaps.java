/*     */ package it.unimi.dsi.fastutil.doubles;
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
/*     */ import java.util.function.DoubleFunction;
/*     */ import java.util.function.DoubleUnaryOperator;
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
/*     */ public final class Double2DoubleMaps
/*     */ {
/*     */   public static ObjectIterator<Double2DoubleMap.Entry> fastIterator(Double2DoubleMap map) {
/*  46 */     ObjectSet<Double2DoubleMap.Entry> entries = map.double2DoubleEntrySet();
/*  47 */     return (entries instanceof Double2DoubleMap.FastEntrySet) ? ((Double2DoubleMap.FastEntrySet)entries).fastIterator() : entries.iterator();
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
/*     */   public static void fastForEach(Double2DoubleMap map, Consumer<? super Double2DoubleMap.Entry> consumer) {
/*  62 */     ObjectSet<Double2DoubleMap.Entry> entries = map.double2DoubleEntrySet();
/*  63 */     if (entries instanceof Double2DoubleMap.FastEntrySet) { ((Double2DoubleMap.FastEntrySet)entries).fastForEach(consumer); }
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
/*     */   public static ObjectIterable<Double2DoubleMap.Entry> fastIterable(Double2DoubleMap map) {
/*  79 */     final ObjectSet<Double2DoubleMap.Entry> entries = map.double2DoubleEntrySet();
/*  80 */     return (entries instanceof Double2DoubleMap.FastEntrySet) ? new ObjectIterable<Double2DoubleMap.Entry>()
/*     */       {
/*     */         public ObjectIterator<Double2DoubleMap.Entry> iterator() {
/*  83 */           return ((Double2DoubleMap.FastEntrySet)entries).fastIterator();
/*     */         }
/*     */ 
/*     */         
/*     */         public ObjectSpliterator<Double2DoubleMap.Entry> spliterator() {
/*  88 */           return entries.spliterator();
/*     */         }
/*     */ 
/*     */         
/*     */         public void forEach(Consumer<? super Double2DoubleMap.Entry> consumer) {
/*  93 */           ((Double2DoubleMap.FastEntrySet)entries).fastForEach(consumer);
/*     */         }
/*  95 */       } : (ObjectIterable<Double2DoubleMap.Entry>)entries;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static class EmptyMap
/*     */     extends Double2DoubleFunctions.EmptyFunction
/*     */     implements Double2DoubleMap, Serializable, Cloneable
/*     */   {
/*     */     private static final long serialVersionUID = -7046029254386353129L;
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public boolean containsValue(double v) {
/* 112 */       return false;
/*     */     }
/*     */ 
/*     */     
/*     */     @Deprecated
/*     */     public Double getOrDefault(Object key, Double defaultValue) {
/* 118 */       return defaultValue;
/*     */     }
/*     */ 
/*     */     
/*     */     public double getOrDefault(double key, double defaultValue) {
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
/*     */     public void putAll(Map<? extends Double, ? extends Double> m) {
/* 139 */       throw new UnsupportedOperationException();
/*     */     }
/*     */ 
/*     */     
/*     */     public ObjectSet<Double2DoubleMap.Entry> double2DoubleEntrySet() {
/* 144 */       return (ObjectSet<Double2DoubleMap.Entry>)ObjectSets.EMPTY_SET;
/*     */     }
/*     */ 
/*     */     
/*     */     public DoubleSet keySet() {
/* 149 */       return DoubleSets.EMPTY_SET;
/*     */     }
/*     */ 
/*     */     
/*     */     public DoubleCollection values() {
/* 154 */       return DoubleSets.EMPTY_SET;
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     public void forEach(BiConsumer<? super Double, ? super Double> consumer) {}
/*     */ 
/*     */     
/*     */     public Object clone() {
/* 163 */       return Double2DoubleMaps.EMPTY_MAP;
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
/*     */     extends Double2DoubleFunctions.Singleton
/*     */     implements Double2DoubleMap, Serializable, Cloneable
/*     */   {
/*     */     private static final long serialVersionUID = -7046029254386353129L;
/*     */     
/*     */     protected transient ObjectSet<Double2DoubleMap.Entry> entries;
/*     */     
/*     */     protected transient DoubleSet keys;
/*     */     protected transient DoubleCollection values;
/*     */     
/*     */     protected Singleton(double key, double value) {
/* 207 */       super(key, value);
/*     */     }
/*     */ 
/*     */     
/*     */     public boolean containsValue(double v) {
/* 212 */       return (Double.doubleToLongBits(this.value) == Double.doubleToLongBits(v));
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
/* 223 */       return (Double.doubleToLongBits(((Double)ov).doubleValue()) == Double.doubleToLongBits(this.value));
/*     */     }
/*     */ 
/*     */     
/*     */     public void putAll(Map<? extends Double, ? extends Double> m) {
/* 228 */       throw new UnsupportedOperationException();
/*     */     }
/*     */ 
/*     */     
/*     */     public ObjectSet<Double2DoubleMap.Entry> double2DoubleEntrySet() {
/* 233 */       if (this.entries == null) this.entries = ObjectSets.singleton(new AbstractDouble2DoubleMap.BasicEntry(this.key, this.value)); 
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
/*     */     public ObjectSet<Map.Entry<Double, Double>> entrySet() {
/* 246 */       return (ObjectSet)double2DoubleEntrySet();
/*     */     }
/*     */ 
/*     */     
/*     */     public DoubleSet keySet() {
/* 251 */       if (this.keys == null) this.keys = DoubleSets.singleton(this.key); 
/* 252 */       return this.keys;
/*     */     }
/*     */ 
/*     */     
/*     */     public DoubleCollection values() {
/* 257 */       if (this.values == null) this.values = DoubleSets.singleton(this.value); 
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
/* 268 */       return HashCommon.double2int(this.key) ^ HashCommon.double2int(this.value);
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
/*     */   public static Double2DoubleMap singleton(double key, double value) {
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
/*     */   public static Double2DoubleMap singleton(Double key, Double value) {
/* 313 */     return new Singleton(key.doubleValue(), value.doubleValue());
/*     */   }
/*     */   
/*     */   public static class SynchronizedMap
/*     */     extends Double2DoubleFunctions.SynchronizedFunction implements Double2DoubleMap, Serializable {
/*     */     private static final long serialVersionUID = -7046029254386353129L;
/*     */     protected final Double2DoubleMap map;
/*     */     protected transient ObjectSet<Double2DoubleMap.Entry> entries;
/*     */     protected transient DoubleSet keys;
/*     */     protected transient DoubleCollection values;
/*     */     
/*     */     protected SynchronizedMap(Double2DoubleMap m, Object sync) {
/* 325 */       super(m, sync);
/* 326 */       this.map = m;
/*     */     }
/*     */     
/*     */     protected SynchronizedMap(Double2DoubleMap m) {
/* 330 */       super(m);
/* 331 */       this.map = m;
/*     */     }
/*     */ 
/*     */     
/*     */     public boolean containsValue(double v) {
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
/*     */     public void putAll(Map<? extends Double, ? extends Double> m) {
/* 356 */       synchronized (this.sync) {
/* 357 */         this.map.putAll(m);
/*     */       } 
/*     */     }
/*     */ 
/*     */     
/*     */     public ObjectSet<Double2DoubleMap.Entry> double2DoubleEntrySet() {
/* 363 */       synchronized (this.sync) {
/* 364 */         if (this.entries == null) this.entries = ObjectSets.synchronize(this.map.double2DoubleEntrySet(), this.sync); 
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
/*     */     public ObjectSet<Map.Entry<Double, Double>> entrySet() {
/* 378 */       return (ObjectSet)double2DoubleEntrySet();
/*     */     }
/*     */ 
/*     */     
/*     */     public DoubleSet keySet() {
/* 383 */       synchronized (this.sync) {
/* 384 */         if (this.keys == null) this.keys = DoubleSets.synchronize(this.map.keySet(), this.sync); 
/* 385 */         return this.keys;
/*     */       } 
/*     */     }
/*     */ 
/*     */     
/*     */     public DoubleCollection values() {
/* 391 */       synchronized (this.sync) {
/* 392 */         if (this.values == null) this.values = DoubleCollections.synchronize(this.map.values(), this.sync); 
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
/*     */     public double getOrDefault(double key, double defaultValue) {
/* 428 */       synchronized (this.sync) {
/* 429 */         return this.map.getOrDefault(key, defaultValue);
/*     */       } 
/*     */     }
/*     */ 
/*     */     
/*     */     public void forEach(BiConsumer<? super Double, ? super Double> action) {
/* 435 */       synchronized (this.sync) {
/* 436 */         this.map.forEach(action);
/*     */       } 
/*     */     }
/*     */ 
/*     */     
/*     */     public void replaceAll(BiFunction<? super Double, ? super Double, ? extends Double> function) {
/* 442 */       synchronized (this.sync) {
/* 443 */         this.map.replaceAll(function);
/*     */       } 
/*     */     }
/*     */ 
/*     */     
/*     */     public double putIfAbsent(double key, double value) {
/* 449 */       synchronized (this.sync) {
/* 450 */         return this.map.putIfAbsent(key, value);
/*     */       } 
/*     */     }
/*     */ 
/*     */     
/*     */     public boolean remove(double key, double value) {
/* 456 */       synchronized (this.sync) {
/* 457 */         return this.map.remove(key, value);
/*     */       } 
/*     */     }
/*     */ 
/*     */     
/*     */     public double replace(double key, double value) {
/* 463 */       synchronized (this.sync) {
/* 464 */         return this.map.replace(key, value);
/*     */       } 
/*     */     }
/*     */ 
/*     */     
/*     */     public boolean replace(double key, double oldValue, double newValue) {
/* 470 */       synchronized (this.sync) {
/* 471 */         return this.map.replace(key, oldValue, newValue);
/*     */       } 
/*     */     }
/*     */ 
/*     */     
/*     */     public double computeIfAbsent(double key, DoubleUnaryOperator mappingFunction) {
/* 477 */       synchronized (this.sync) {
/* 478 */         return this.map.computeIfAbsent(key, mappingFunction);
/*     */       } 
/*     */     }
/*     */ 
/*     */     
/*     */     public double computeIfAbsentNullable(double key, DoubleFunction<? extends Double> mappingFunction) {
/* 484 */       synchronized (this.sync) {
/* 485 */         return this.map.computeIfAbsentNullable(key, mappingFunction);
/*     */       } 
/*     */     }
/*     */ 
/*     */     
/*     */     public double computeIfAbsent(double key, Double2DoubleFunction mappingFunction) {
/* 491 */       synchronized (this.sync) {
/* 492 */         return this.map.computeIfAbsent(key, mappingFunction);
/*     */       } 
/*     */     }
/*     */ 
/*     */     
/*     */     public double computeIfPresent(double key, BiFunction<? super Double, ? super Double, ? extends Double> remappingFunction) {
/* 498 */       synchronized (this.sync) {
/* 499 */         return this.map.computeIfPresent(key, remappingFunction);
/*     */       } 
/*     */     }
/*     */ 
/*     */     
/*     */     public double compute(double key, BiFunction<? super Double, ? super Double, ? extends Double> remappingFunction) {
/* 505 */       synchronized (this.sync) {
/* 506 */         return this.map.compute(key, remappingFunction);
/*     */       } 
/*     */     }
/*     */ 
/*     */     
/*     */     public double merge(double key, double value, BiFunction<? super Double, ? super Double, ? extends Double> remappingFunction) {
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
/*     */     public Double getOrDefault(Object key, Double defaultValue) {
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
/*     */     public Double replace(Double key, Double value) {
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
/*     */     public boolean replace(Double key, Double oldValue, Double newValue) {
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
/*     */     public Double putIfAbsent(Double key, Double value) {
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
/*     */     public Double computeIfAbsent(Double key, Function<? super Double, ? extends Double> mappingFunction) {
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
/*     */     public Double computeIfPresent(Double key, BiFunction<? super Double, ? super Double, ? extends Double> remappingFunction) {
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
/*     */     public Double compute(Double key, BiFunction<? super Double, ? super Double, ? extends Double> remappingFunction) {
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
/*     */     public Double merge(Double key, Double value, BiFunction<? super Double, ? super Double, ? extends Double> remappingFunction) {
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
/*     */   public static Double2DoubleMap synchronize(Double2DoubleMap m) {
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
/*     */   public static Double2DoubleMap synchronize(Double2DoubleMap m, Object sync) {
/* 656 */     return new SynchronizedMap(m, sync);
/*     */   }
/*     */   
/*     */   public static class UnmodifiableMap
/*     */     extends Double2DoubleFunctions.UnmodifiableFunction implements Double2DoubleMap, Serializable {
/*     */     private static final long serialVersionUID = -7046029254386353129L;
/*     */     protected final Double2DoubleMap map;
/*     */     protected transient ObjectSet<Double2DoubleMap.Entry> entries;
/*     */     protected transient DoubleSet keys;
/*     */     protected transient DoubleCollection values;
/*     */     
/*     */     protected UnmodifiableMap(Double2DoubleMap m) {
/* 668 */       super(m);
/* 669 */       this.map = m;
/*     */     }
/*     */ 
/*     */     
/*     */     public boolean containsValue(double v) {
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
/*     */     public void putAll(Map<? extends Double, ? extends Double> m) {
/* 690 */       throw new UnsupportedOperationException();
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     public ObjectSet<Double2DoubleMap.Entry> double2DoubleEntrySet() {
/* 696 */       if (this.entries == null) this.entries = ObjectSets.unmodifiable(this.map.double2DoubleEntrySet()); 
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
/*     */     public ObjectSet<Map.Entry<Double, Double>> entrySet() {
/* 709 */       return (ObjectSet)double2DoubleEntrySet();
/*     */     }
/*     */ 
/*     */     
/*     */     public DoubleSet keySet() {
/* 714 */       if (this.keys == null) this.keys = DoubleSets.unmodifiable(this.map.keySet()); 
/* 715 */       return this.keys;
/*     */     }
/*     */ 
/*     */     
/*     */     public DoubleCollection values() {
/* 720 */       if (this.values == null) this.values = DoubleCollections.unmodifiable(this.map.values()); 
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
/*     */     public double getOrDefault(double key, double defaultValue) {
/* 743 */       return this.map.getOrDefault(key, defaultValue);
/*     */     }
/*     */ 
/*     */     
/*     */     public void forEach(BiConsumer<? super Double, ? super Double> action) {
/* 748 */       this.map.forEach(action);
/*     */     }
/*     */ 
/*     */     
/*     */     public void replaceAll(BiFunction<? super Double, ? super Double, ? extends Double> function) {
/* 753 */       throw new UnsupportedOperationException();
/*     */     }
/*     */ 
/*     */     
/*     */     public double putIfAbsent(double key, double value) {
/* 758 */       throw new UnsupportedOperationException();
/*     */     }
/*     */ 
/*     */     
/*     */     public boolean remove(double key, double value) {
/* 763 */       throw new UnsupportedOperationException();
/*     */     }
/*     */ 
/*     */     
/*     */     public double replace(double key, double value) {
/* 768 */       throw new UnsupportedOperationException();
/*     */     }
/*     */ 
/*     */     
/*     */     public boolean replace(double key, double oldValue, double newValue) {
/* 773 */       throw new UnsupportedOperationException();
/*     */     }
/*     */ 
/*     */     
/*     */     public double computeIfAbsent(double key, DoubleUnaryOperator mappingFunction) {
/* 778 */       throw new UnsupportedOperationException();
/*     */     }
/*     */ 
/*     */     
/*     */     public double computeIfAbsentNullable(double key, DoubleFunction<? extends Double> mappingFunction) {
/* 783 */       throw new UnsupportedOperationException();
/*     */     }
/*     */ 
/*     */     
/*     */     public double computeIfAbsent(double key, Double2DoubleFunction mappingFunction) {
/* 788 */       throw new UnsupportedOperationException();
/*     */     }
/*     */ 
/*     */     
/*     */     public double computeIfPresent(double key, BiFunction<? super Double, ? super Double, ? extends Double> remappingFunction) {
/* 793 */       throw new UnsupportedOperationException();
/*     */     }
/*     */ 
/*     */     
/*     */     public double compute(double key, BiFunction<? super Double, ? super Double, ? extends Double> remappingFunction) {
/* 798 */       throw new UnsupportedOperationException();
/*     */     }
/*     */ 
/*     */     
/*     */     public double merge(double key, double value, BiFunction<? super Double, ? super Double, ? extends Double> remappingFunction) {
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
/*     */     public Double getOrDefault(Object key, Double defaultValue) {
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
/*     */     public Double replace(Double key, Double value) {
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
/*     */     public boolean replace(Double key, Double oldValue, Double newValue) {
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
/*     */     public Double putIfAbsent(Double key, Double value) {
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
/*     */     public Double computeIfAbsent(Double key, Function<? super Double, ? extends Double> mappingFunction) {
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
/*     */     public Double computeIfPresent(Double key, BiFunction<? super Double, ? super Double, ? extends Double> remappingFunction) {
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
/*     */     public Double compute(Double key, BiFunction<? super Double, ? super Double, ? extends Double> remappingFunction) {
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
/*     */     public Double merge(Double key, Double value, BiFunction<? super Double, ? super Double, ? extends Double> remappingFunction) {
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
/*     */   public static Double2DoubleMap unmodifiable(Double2DoubleMap m) {
/* 914 */     return new UnmodifiableMap(m);
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\i\\unimi\dsi\fastutil\doubles\Double2DoubleMaps.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */