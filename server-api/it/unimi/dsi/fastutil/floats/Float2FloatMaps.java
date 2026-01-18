/*     */ package it.unimi.dsi.fastutil.floats;
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
/*     */ public final class Float2FloatMaps
/*     */ {
/*     */   public static ObjectIterator<Float2FloatMap.Entry> fastIterator(Float2FloatMap map) {
/*  46 */     ObjectSet<Float2FloatMap.Entry> entries = map.float2FloatEntrySet();
/*  47 */     return (entries instanceof Float2FloatMap.FastEntrySet) ? ((Float2FloatMap.FastEntrySet)entries).fastIterator() : entries.iterator();
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
/*     */   public static void fastForEach(Float2FloatMap map, Consumer<? super Float2FloatMap.Entry> consumer) {
/*  62 */     ObjectSet<Float2FloatMap.Entry> entries = map.float2FloatEntrySet();
/*  63 */     if (entries instanceof Float2FloatMap.FastEntrySet) { ((Float2FloatMap.FastEntrySet)entries).fastForEach(consumer); }
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
/*     */   public static ObjectIterable<Float2FloatMap.Entry> fastIterable(Float2FloatMap map) {
/*  79 */     final ObjectSet<Float2FloatMap.Entry> entries = map.float2FloatEntrySet();
/*  80 */     return (entries instanceof Float2FloatMap.FastEntrySet) ? new ObjectIterable<Float2FloatMap.Entry>()
/*     */       {
/*     */         public ObjectIterator<Float2FloatMap.Entry> iterator() {
/*  83 */           return ((Float2FloatMap.FastEntrySet)entries).fastIterator();
/*     */         }
/*     */ 
/*     */         
/*     */         public ObjectSpliterator<Float2FloatMap.Entry> spliterator() {
/*  88 */           return entries.spliterator();
/*     */         }
/*     */ 
/*     */         
/*     */         public void forEach(Consumer<? super Float2FloatMap.Entry> consumer) {
/*  93 */           ((Float2FloatMap.FastEntrySet)entries).fastForEach(consumer);
/*     */         }
/*  95 */       } : (ObjectIterable<Float2FloatMap.Entry>)entries;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static class EmptyMap
/*     */     extends Float2FloatFunctions.EmptyFunction
/*     */     implements Float2FloatMap, Serializable, Cloneable
/*     */   {
/*     */     private static final long serialVersionUID = -7046029254386353129L;
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public boolean containsValue(float v) {
/* 112 */       return false;
/*     */     }
/*     */ 
/*     */     
/*     */     @Deprecated
/*     */     public Float getOrDefault(Object key, Float defaultValue) {
/* 118 */       return defaultValue;
/*     */     }
/*     */ 
/*     */     
/*     */     public float getOrDefault(float key, float defaultValue) {
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
/*     */     public void putAll(Map<? extends Float, ? extends Float> m) {
/* 139 */       throw new UnsupportedOperationException();
/*     */     }
/*     */ 
/*     */     
/*     */     public ObjectSet<Float2FloatMap.Entry> float2FloatEntrySet() {
/* 144 */       return (ObjectSet<Float2FloatMap.Entry>)ObjectSets.EMPTY_SET;
/*     */     }
/*     */ 
/*     */     
/*     */     public FloatSet keySet() {
/* 149 */       return FloatSets.EMPTY_SET;
/*     */     }
/*     */ 
/*     */     
/*     */     public FloatCollection values() {
/* 154 */       return FloatSets.EMPTY_SET;
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     public void forEach(BiConsumer<? super Float, ? super Float> consumer) {}
/*     */ 
/*     */     
/*     */     public Object clone() {
/* 163 */       return Float2FloatMaps.EMPTY_MAP;
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
/*     */     extends Float2FloatFunctions.Singleton
/*     */     implements Float2FloatMap, Serializable, Cloneable
/*     */   {
/*     */     private static final long serialVersionUID = -7046029254386353129L;
/*     */     
/*     */     protected transient ObjectSet<Float2FloatMap.Entry> entries;
/*     */     
/*     */     protected transient FloatSet keys;
/*     */     protected transient FloatCollection values;
/*     */     
/*     */     protected Singleton(float key, float value) {
/* 207 */       super(key, value);
/*     */     }
/*     */ 
/*     */     
/*     */     public boolean containsValue(float v) {
/* 212 */       return (Float.floatToIntBits(this.value) == Float.floatToIntBits(v));
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
/* 223 */       return (Float.floatToIntBits(((Float)ov).floatValue()) == Float.floatToIntBits(this.value));
/*     */     }
/*     */ 
/*     */     
/*     */     public void putAll(Map<? extends Float, ? extends Float> m) {
/* 228 */       throw new UnsupportedOperationException();
/*     */     }
/*     */ 
/*     */     
/*     */     public ObjectSet<Float2FloatMap.Entry> float2FloatEntrySet() {
/* 233 */       if (this.entries == null) this.entries = ObjectSets.singleton(new AbstractFloat2FloatMap.BasicEntry(this.key, this.value)); 
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
/*     */     public ObjectSet<Map.Entry<Float, Float>> entrySet() {
/* 246 */       return (ObjectSet)float2FloatEntrySet();
/*     */     }
/*     */ 
/*     */     
/*     */     public FloatSet keySet() {
/* 251 */       if (this.keys == null) this.keys = FloatSets.singleton(this.key); 
/* 252 */       return this.keys;
/*     */     }
/*     */ 
/*     */     
/*     */     public FloatCollection values() {
/* 257 */       if (this.values == null) this.values = FloatSets.singleton(this.value); 
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
/* 268 */       return HashCommon.float2int(this.key) ^ HashCommon.float2int(this.value);
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
/*     */   public static Float2FloatMap singleton(float key, float value) {
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
/*     */   public static Float2FloatMap singleton(Float key, Float value) {
/* 313 */     return new Singleton(key.floatValue(), value.floatValue());
/*     */   }
/*     */   
/*     */   public static class SynchronizedMap
/*     */     extends Float2FloatFunctions.SynchronizedFunction implements Float2FloatMap, Serializable {
/*     */     private static final long serialVersionUID = -7046029254386353129L;
/*     */     protected final Float2FloatMap map;
/*     */     protected transient ObjectSet<Float2FloatMap.Entry> entries;
/*     */     protected transient FloatSet keys;
/*     */     protected transient FloatCollection values;
/*     */     
/*     */     protected SynchronizedMap(Float2FloatMap m, Object sync) {
/* 325 */       super(m, sync);
/* 326 */       this.map = m;
/*     */     }
/*     */     
/*     */     protected SynchronizedMap(Float2FloatMap m) {
/* 330 */       super(m);
/* 331 */       this.map = m;
/*     */     }
/*     */ 
/*     */     
/*     */     public boolean containsValue(float v) {
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
/*     */     public void putAll(Map<? extends Float, ? extends Float> m) {
/* 356 */       synchronized (this.sync) {
/* 357 */         this.map.putAll(m);
/*     */       } 
/*     */     }
/*     */ 
/*     */     
/*     */     public ObjectSet<Float2FloatMap.Entry> float2FloatEntrySet() {
/* 363 */       synchronized (this.sync) {
/* 364 */         if (this.entries == null) this.entries = ObjectSets.synchronize(this.map.float2FloatEntrySet(), this.sync); 
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
/*     */     public ObjectSet<Map.Entry<Float, Float>> entrySet() {
/* 378 */       return (ObjectSet)float2FloatEntrySet();
/*     */     }
/*     */ 
/*     */     
/*     */     public FloatSet keySet() {
/* 383 */       synchronized (this.sync) {
/* 384 */         if (this.keys == null) this.keys = FloatSets.synchronize(this.map.keySet(), this.sync); 
/* 385 */         return this.keys;
/*     */       } 
/*     */     }
/*     */ 
/*     */     
/*     */     public FloatCollection values() {
/* 391 */       synchronized (this.sync) {
/* 392 */         if (this.values == null) this.values = FloatCollections.synchronize(this.map.values(), this.sync); 
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
/*     */     public float getOrDefault(float key, float defaultValue) {
/* 428 */       synchronized (this.sync) {
/* 429 */         return this.map.getOrDefault(key, defaultValue);
/*     */       } 
/*     */     }
/*     */ 
/*     */     
/*     */     public void forEach(BiConsumer<? super Float, ? super Float> action) {
/* 435 */       synchronized (this.sync) {
/* 436 */         this.map.forEach(action);
/*     */       } 
/*     */     }
/*     */ 
/*     */     
/*     */     public void replaceAll(BiFunction<? super Float, ? super Float, ? extends Float> function) {
/* 442 */       synchronized (this.sync) {
/* 443 */         this.map.replaceAll(function);
/*     */       } 
/*     */     }
/*     */ 
/*     */     
/*     */     public float putIfAbsent(float key, float value) {
/* 449 */       synchronized (this.sync) {
/* 450 */         return this.map.putIfAbsent(key, value);
/*     */       } 
/*     */     }
/*     */ 
/*     */     
/*     */     public boolean remove(float key, float value) {
/* 456 */       synchronized (this.sync) {
/* 457 */         return this.map.remove(key, value);
/*     */       } 
/*     */     }
/*     */ 
/*     */     
/*     */     public float replace(float key, float value) {
/* 463 */       synchronized (this.sync) {
/* 464 */         return this.map.replace(key, value);
/*     */       } 
/*     */     }
/*     */ 
/*     */     
/*     */     public boolean replace(float key, float oldValue, float newValue) {
/* 470 */       synchronized (this.sync) {
/* 471 */         return this.map.replace(key, oldValue, newValue);
/*     */       } 
/*     */     }
/*     */ 
/*     */     
/*     */     public float computeIfAbsent(float key, DoubleUnaryOperator mappingFunction) {
/* 477 */       synchronized (this.sync) {
/* 478 */         return this.map.computeIfAbsent(key, mappingFunction);
/*     */       } 
/*     */     }
/*     */ 
/*     */     
/*     */     public float computeIfAbsentNullable(float key, DoubleFunction<? extends Float> mappingFunction) {
/* 484 */       synchronized (this.sync) {
/* 485 */         return this.map.computeIfAbsentNullable(key, mappingFunction);
/*     */       } 
/*     */     }
/*     */ 
/*     */     
/*     */     public float computeIfAbsent(float key, Float2FloatFunction mappingFunction) {
/* 491 */       synchronized (this.sync) {
/* 492 */         return this.map.computeIfAbsent(key, mappingFunction);
/*     */       } 
/*     */     }
/*     */ 
/*     */     
/*     */     public float computeIfPresent(float key, BiFunction<? super Float, ? super Float, ? extends Float> remappingFunction) {
/* 498 */       synchronized (this.sync) {
/* 499 */         return this.map.computeIfPresent(key, remappingFunction);
/*     */       } 
/*     */     }
/*     */ 
/*     */     
/*     */     public float compute(float key, BiFunction<? super Float, ? super Float, ? extends Float> remappingFunction) {
/* 505 */       synchronized (this.sync) {
/* 506 */         return this.map.compute(key, remappingFunction);
/*     */       } 
/*     */     }
/*     */ 
/*     */     
/*     */     public float merge(float key, float value, BiFunction<? super Float, ? super Float, ? extends Float> remappingFunction) {
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
/*     */     public Float getOrDefault(Object key, Float defaultValue) {
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
/*     */     public Float replace(Float key, Float value) {
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
/*     */     public boolean replace(Float key, Float oldValue, Float newValue) {
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
/*     */     public Float putIfAbsent(Float key, Float value) {
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
/*     */     public Float computeIfAbsent(Float key, Function<? super Float, ? extends Float> mappingFunction) {
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
/*     */     public Float computeIfPresent(Float key, BiFunction<? super Float, ? super Float, ? extends Float> remappingFunction) {
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
/*     */     public Float compute(Float key, BiFunction<? super Float, ? super Float, ? extends Float> remappingFunction) {
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
/*     */     public Float merge(Float key, Float value, BiFunction<? super Float, ? super Float, ? extends Float> remappingFunction) {
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
/*     */   public static Float2FloatMap synchronize(Float2FloatMap m) {
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
/*     */   public static Float2FloatMap synchronize(Float2FloatMap m, Object sync) {
/* 656 */     return new SynchronizedMap(m, sync);
/*     */   }
/*     */   
/*     */   public static class UnmodifiableMap
/*     */     extends Float2FloatFunctions.UnmodifiableFunction implements Float2FloatMap, Serializable {
/*     */     private static final long serialVersionUID = -7046029254386353129L;
/*     */     protected final Float2FloatMap map;
/*     */     protected transient ObjectSet<Float2FloatMap.Entry> entries;
/*     */     protected transient FloatSet keys;
/*     */     protected transient FloatCollection values;
/*     */     
/*     */     protected UnmodifiableMap(Float2FloatMap m) {
/* 668 */       super(m);
/* 669 */       this.map = m;
/*     */     }
/*     */ 
/*     */     
/*     */     public boolean containsValue(float v) {
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
/*     */     public void putAll(Map<? extends Float, ? extends Float> m) {
/* 690 */       throw new UnsupportedOperationException();
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     public ObjectSet<Float2FloatMap.Entry> float2FloatEntrySet() {
/* 696 */       if (this.entries == null) this.entries = ObjectSets.unmodifiable(this.map.float2FloatEntrySet()); 
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
/*     */     public ObjectSet<Map.Entry<Float, Float>> entrySet() {
/* 709 */       return (ObjectSet)float2FloatEntrySet();
/*     */     }
/*     */ 
/*     */     
/*     */     public FloatSet keySet() {
/* 714 */       if (this.keys == null) this.keys = FloatSets.unmodifiable(this.map.keySet()); 
/* 715 */       return this.keys;
/*     */     }
/*     */ 
/*     */     
/*     */     public FloatCollection values() {
/* 720 */       if (this.values == null) this.values = FloatCollections.unmodifiable(this.map.values()); 
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
/*     */     public float getOrDefault(float key, float defaultValue) {
/* 743 */       return this.map.getOrDefault(key, defaultValue);
/*     */     }
/*     */ 
/*     */     
/*     */     public void forEach(BiConsumer<? super Float, ? super Float> action) {
/* 748 */       this.map.forEach(action);
/*     */     }
/*     */ 
/*     */     
/*     */     public void replaceAll(BiFunction<? super Float, ? super Float, ? extends Float> function) {
/* 753 */       throw new UnsupportedOperationException();
/*     */     }
/*     */ 
/*     */     
/*     */     public float putIfAbsent(float key, float value) {
/* 758 */       throw new UnsupportedOperationException();
/*     */     }
/*     */ 
/*     */     
/*     */     public boolean remove(float key, float value) {
/* 763 */       throw new UnsupportedOperationException();
/*     */     }
/*     */ 
/*     */     
/*     */     public float replace(float key, float value) {
/* 768 */       throw new UnsupportedOperationException();
/*     */     }
/*     */ 
/*     */     
/*     */     public boolean replace(float key, float oldValue, float newValue) {
/* 773 */       throw new UnsupportedOperationException();
/*     */     }
/*     */ 
/*     */     
/*     */     public float computeIfAbsent(float key, DoubleUnaryOperator mappingFunction) {
/* 778 */       throw new UnsupportedOperationException();
/*     */     }
/*     */ 
/*     */     
/*     */     public float computeIfAbsentNullable(float key, DoubleFunction<? extends Float> mappingFunction) {
/* 783 */       throw new UnsupportedOperationException();
/*     */     }
/*     */ 
/*     */     
/*     */     public float computeIfAbsent(float key, Float2FloatFunction mappingFunction) {
/* 788 */       throw new UnsupportedOperationException();
/*     */     }
/*     */ 
/*     */     
/*     */     public float computeIfPresent(float key, BiFunction<? super Float, ? super Float, ? extends Float> remappingFunction) {
/* 793 */       throw new UnsupportedOperationException();
/*     */     }
/*     */ 
/*     */     
/*     */     public float compute(float key, BiFunction<? super Float, ? super Float, ? extends Float> remappingFunction) {
/* 798 */       throw new UnsupportedOperationException();
/*     */     }
/*     */ 
/*     */     
/*     */     public float merge(float key, float value, BiFunction<? super Float, ? super Float, ? extends Float> remappingFunction) {
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
/*     */     public Float getOrDefault(Object key, Float defaultValue) {
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
/*     */     public Float replace(Float key, Float value) {
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
/*     */     public boolean replace(Float key, Float oldValue, Float newValue) {
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
/*     */     public Float putIfAbsent(Float key, Float value) {
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
/*     */     public Float computeIfAbsent(Float key, Function<? super Float, ? extends Float> mappingFunction) {
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
/*     */     public Float computeIfPresent(Float key, BiFunction<? super Float, ? super Float, ? extends Float> remappingFunction) {
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
/*     */     public Float compute(Float key, BiFunction<? super Float, ? super Float, ? extends Float> remappingFunction) {
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
/*     */     public Float merge(Float key, Float value, BiFunction<? super Float, ? super Float, ? extends Float> remappingFunction) {
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
/*     */   public static Float2FloatMap unmodifiable(Float2FloatMap m) {
/* 914 */     return new UnmodifiableMap(m);
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\i\\unimi\dsi\fastutil\floats\Float2FloatMaps.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */