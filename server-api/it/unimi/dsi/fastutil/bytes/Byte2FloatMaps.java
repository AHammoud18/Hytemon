/*     */ package it.unimi.dsi.fastutil.bytes;
/*     */ 
/*     */ import it.unimi.dsi.fastutil.HashCommon;
/*     */ import it.unimi.dsi.fastutil.floats.FloatCollection;
/*     */ import it.unimi.dsi.fastutil.floats.FloatCollections;
/*     */ import it.unimi.dsi.fastutil.floats.FloatSets;
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
/*     */ import java.util.function.IntToDoubleFunction;
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
/*     */ public final class Byte2FloatMaps
/*     */ {
/*     */   public static ObjectIterator<Byte2FloatMap.Entry> fastIterator(Byte2FloatMap map) {
/*  49 */     ObjectSet<Byte2FloatMap.Entry> entries = map.byte2FloatEntrySet();
/*  50 */     return (entries instanceof Byte2FloatMap.FastEntrySet) ? ((Byte2FloatMap.FastEntrySet)entries).fastIterator() : entries.iterator();
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
/*     */   public static void fastForEach(Byte2FloatMap map, Consumer<? super Byte2FloatMap.Entry> consumer) {
/*  65 */     ObjectSet<Byte2FloatMap.Entry> entries = map.byte2FloatEntrySet();
/*  66 */     if (entries instanceof Byte2FloatMap.FastEntrySet) { ((Byte2FloatMap.FastEntrySet)entries).fastForEach(consumer); }
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
/*     */   public static ObjectIterable<Byte2FloatMap.Entry> fastIterable(Byte2FloatMap map) {
/*  82 */     final ObjectSet<Byte2FloatMap.Entry> entries = map.byte2FloatEntrySet();
/*  83 */     return (entries instanceof Byte2FloatMap.FastEntrySet) ? new ObjectIterable<Byte2FloatMap.Entry>()
/*     */       {
/*     */         public ObjectIterator<Byte2FloatMap.Entry> iterator() {
/*  86 */           return ((Byte2FloatMap.FastEntrySet)entries).fastIterator();
/*     */         }
/*     */ 
/*     */         
/*     */         public ObjectSpliterator<Byte2FloatMap.Entry> spliterator() {
/*  91 */           return entries.spliterator();
/*     */         }
/*     */ 
/*     */         
/*     */         public void forEach(Consumer<? super Byte2FloatMap.Entry> consumer) {
/*  96 */           ((Byte2FloatMap.FastEntrySet)entries).fastForEach(consumer);
/*     */         }
/*  98 */       } : (ObjectIterable<Byte2FloatMap.Entry>)entries;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static class EmptyMap
/*     */     extends Byte2FloatFunctions.EmptyFunction
/*     */     implements Byte2FloatMap, Serializable, Cloneable
/*     */   {
/*     */     private static final long serialVersionUID = -7046029254386353129L;
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public boolean containsValue(float v) {
/* 115 */       return false;
/*     */     }
/*     */ 
/*     */     
/*     */     @Deprecated
/*     */     public Float getOrDefault(Object key, Float defaultValue) {
/* 121 */       return defaultValue;
/*     */     }
/*     */ 
/*     */     
/*     */     public float getOrDefault(byte key, float defaultValue) {
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
/*     */     public void putAll(Map<? extends Byte, ? extends Float> m) {
/* 142 */       throw new UnsupportedOperationException();
/*     */     }
/*     */ 
/*     */     
/*     */     public ObjectSet<Byte2FloatMap.Entry> byte2FloatEntrySet() {
/* 147 */       return (ObjectSet<Byte2FloatMap.Entry>)ObjectSets.EMPTY_SET;
/*     */     }
/*     */ 
/*     */     
/*     */     public ByteSet keySet() {
/* 152 */       return ByteSets.EMPTY_SET;
/*     */     }
/*     */ 
/*     */     
/*     */     public FloatCollection values() {
/* 157 */       return (FloatCollection)FloatSets.EMPTY_SET;
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     public void forEach(BiConsumer<? super Byte, ? super Float> consumer) {}
/*     */ 
/*     */     
/*     */     public Object clone() {
/* 166 */       return Byte2FloatMaps.EMPTY_MAP;
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
/*     */     extends Byte2FloatFunctions.Singleton
/*     */     implements Byte2FloatMap, Serializable, Cloneable
/*     */   {
/*     */     private static final long serialVersionUID = -7046029254386353129L;
/*     */     
/*     */     protected transient ObjectSet<Byte2FloatMap.Entry> entries;
/*     */     
/*     */     protected transient ByteSet keys;
/*     */     protected transient FloatCollection values;
/*     */     
/*     */     protected Singleton(byte key, float value) {
/* 210 */       super(key, value);
/*     */     }
/*     */ 
/*     */     
/*     */     public boolean containsValue(float v) {
/* 215 */       return (Float.floatToIntBits(this.value) == Float.floatToIntBits(v));
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
/* 226 */       return (Float.floatToIntBits(((Float)ov).floatValue()) == Float.floatToIntBits(this.value));
/*     */     }
/*     */ 
/*     */     
/*     */     public void putAll(Map<? extends Byte, ? extends Float> m) {
/* 231 */       throw new UnsupportedOperationException();
/*     */     }
/*     */ 
/*     */     
/*     */     public ObjectSet<Byte2FloatMap.Entry> byte2FloatEntrySet() {
/* 236 */       if (this.entries == null) this.entries = ObjectSets.singleton(new AbstractByte2FloatMap.BasicEntry(this.key, this.value)); 
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
/*     */     public ObjectSet<Map.Entry<Byte, Float>> entrySet() {
/* 249 */       return (ObjectSet)byte2FloatEntrySet();
/*     */     }
/*     */ 
/*     */     
/*     */     public ByteSet keySet() {
/* 254 */       if (this.keys == null) this.keys = ByteSets.singleton(this.key); 
/* 255 */       return this.keys;
/*     */     }
/*     */ 
/*     */     
/*     */     public FloatCollection values() {
/* 260 */       if (this.values == null) this.values = (FloatCollection)FloatSets.singleton(this.value); 
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
/* 271 */       return this.key ^ HashCommon.float2int(this.value);
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
/*     */   public static Byte2FloatMap singleton(byte key, float value) {
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
/*     */   public static Byte2FloatMap singleton(Byte key, Float value) {
/* 316 */     return new Singleton(key.byteValue(), value.floatValue());
/*     */   }
/*     */   
/*     */   public static class SynchronizedMap
/*     */     extends Byte2FloatFunctions.SynchronizedFunction implements Byte2FloatMap, Serializable {
/*     */     private static final long serialVersionUID = -7046029254386353129L;
/*     */     protected final Byte2FloatMap map;
/*     */     protected transient ObjectSet<Byte2FloatMap.Entry> entries;
/*     */     protected transient ByteSet keys;
/*     */     protected transient FloatCollection values;
/*     */     
/*     */     protected SynchronizedMap(Byte2FloatMap m, Object sync) {
/* 328 */       super(m, sync);
/* 329 */       this.map = m;
/*     */     }
/*     */     
/*     */     protected SynchronizedMap(Byte2FloatMap m) {
/* 333 */       super(m);
/* 334 */       this.map = m;
/*     */     }
/*     */ 
/*     */     
/*     */     public boolean containsValue(float v) {
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
/*     */     public void putAll(Map<? extends Byte, ? extends Float> m) {
/* 359 */       synchronized (this.sync) {
/* 360 */         this.map.putAll(m);
/*     */       } 
/*     */     }
/*     */ 
/*     */     
/*     */     public ObjectSet<Byte2FloatMap.Entry> byte2FloatEntrySet() {
/* 366 */       synchronized (this.sync) {
/* 367 */         if (this.entries == null) this.entries = ObjectSets.synchronize(this.map.byte2FloatEntrySet(), this.sync); 
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
/*     */     public ObjectSet<Map.Entry<Byte, Float>> entrySet() {
/* 381 */       return (ObjectSet)byte2FloatEntrySet();
/*     */     }
/*     */ 
/*     */     
/*     */     public ByteSet keySet() {
/* 386 */       synchronized (this.sync) {
/* 387 */         if (this.keys == null) this.keys = ByteSets.synchronize(this.map.keySet(), this.sync); 
/* 388 */         return this.keys;
/*     */       } 
/*     */     }
/*     */ 
/*     */     
/*     */     public FloatCollection values() {
/* 394 */       synchronized (this.sync) {
/* 395 */         if (this.values == null) this.values = FloatCollections.synchronize(this.map.values(), this.sync); 
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
/*     */     public float getOrDefault(byte key, float defaultValue) {
/* 431 */       synchronized (this.sync) {
/* 432 */         return this.map.getOrDefault(key, defaultValue);
/*     */       } 
/*     */     }
/*     */ 
/*     */     
/*     */     public void forEach(BiConsumer<? super Byte, ? super Float> action) {
/* 438 */       synchronized (this.sync) {
/* 439 */         this.map.forEach(action);
/*     */       } 
/*     */     }
/*     */ 
/*     */     
/*     */     public void replaceAll(BiFunction<? super Byte, ? super Float, ? extends Float> function) {
/* 445 */       synchronized (this.sync) {
/* 446 */         this.map.replaceAll(function);
/*     */       } 
/*     */     }
/*     */ 
/*     */     
/*     */     public float putIfAbsent(byte key, float value) {
/* 452 */       synchronized (this.sync) {
/* 453 */         return this.map.putIfAbsent(key, value);
/*     */       } 
/*     */     }
/*     */ 
/*     */     
/*     */     public boolean remove(byte key, float value) {
/* 459 */       synchronized (this.sync) {
/* 460 */         return this.map.remove(key, value);
/*     */       } 
/*     */     }
/*     */ 
/*     */     
/*     */     public float replace(byte key, float value) {
/* 466 */       synchronized (this.sync) {
/* 467 */         return this.map.replace(key, value);
/*     */       } 
/*     */     }
/*     */ 
/*     */     
/*     */     public boolean replace(byte key, float oldValue, float newValue) {
/* 473 */       synchronized (this.sync) {
/* 474 */         return this.map.replace(key, oldValue, newValue);
/*     */       } 
/*     */     }
/*     */ 
/*     */     
/*     */     public float computeIfAbsent(byte key, IntToDoubleFunction mappingFunction) {
/* 480 */       synchronized (this.sync) {
/* 481 */         return this.map.computeIfAbsent(key, mappingFunction);
/*     */       } 
/*     */     }
/*     */ 
/*     */     
/*     */     public float computeIfAbsentNullable(byte key, IntFunction<? extends Float> mappingFunction) {
/* 487 */       synchronized (this.sync) {
/* 488 */         return this.map.computeIfAbsentNullable(key, mappingFunction);
/*     */       } 
/*     */     }
/*     */ 
/*     */     
/*     */     public float computeIfAbsent(byte key, Byte2FloatFunction mappingFunction) {
/* 494 */       synchronized (this.sync) {
/* 495 */         return this.map.computeIfAbsent(key, mappingFunction);
/*     */       } 
/*     */     }
/*     */ 
/*     */     
/*     */     public float computeIfPresent(byte key, BiFunction<? super Byte, ? super Float, ? extends Float> remappingFunction) {
/* 501 */       synchronized (this.sync) {
/* 502 */         return this.map.computeIfPresent(key, remappingFunction);
/*     */       } 
/*     */     }
/*     */ 
/*     */     
/*     */     public float compute(byte key, BiFunction<? super Byte, ? super Float, ? extends Float> remappingFunction) {
/* 508 */       synchronized (this.sync) {
/* 509 */         return this.map.compute(key, remappingFunction);
/*     */       } 
/*     */     }
/*     */ 
/*     */     
/*     */     public float merge(byte key, float value, BiFunction<? super Float, ? super Float, ? extends Float> remappingFunction) {
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
/*     */     public Float getOrDefault(Object key, Float defaultValue) {
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
/*     */     public Float replace(Byte key, Float value) {
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
/*     */     public boolean replace(Byte key, Float oldValue, Float newValue) {
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
/*     */     public Float putIfAbsent(Byte key, Float value) {
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
/*     */     public Float computeIfAbsent(Byte key, Function<? super Byte, ? extends Float> mappingFunction) {
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
/*     */     public Float computeIfPresent(Byte key, BiFunction<? super Byte, ? super Float, ? extends Float> remappingFunction) {
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
/*     */     public Float compute(Byte key, BiFunction<? super Byte, ? super Float, ? extends Float> remappingFunction) {
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
/*     */     public Float merge(Byte key, Float value, BiFunction<? super Float, ? super Float, ? extends Float> remappingFunction) {
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
/*     */   public static Byte2FloatMap synchronize(Byte2FloatMap m) {
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
/*     */   public static Byte2FloatMap synchronize(Byte2FloatMap m, Object sync) {
/* 659 */     return new SynchronizedMap(m, sync);
/*     */   }
/*     */   
/*     */   public static class UnmodifiableMap
/*     */     extends Byte2FloatFunctions.UnmodifiableFunction implements Byte2FloatMap, Serializable {
/*     */     private static final long serialVersionUID = -7046029254386353129L;
/*     */     protected final Byte2FloatMap map;
/*     */     protected transient ObjectSet<Byte2FloatMap.Entry> entries;
/*     */     protected transient ByteSet keys;
/*     */     protected transient FloatCollection values;
/*     */     
/*     */     protected UnmodifiableMap(Byte2FloatMap m) {
/* 671 */       super(m);
/* 672 */       this.map = m;
/*     */     }
/*     */ 
/*     */     
/*     */     public boolean containsValue(float v) {
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
/*     */     public void putAll(Map<? extends Byte, ? extends Float> m) {
/* 693 */       throw new UnsupportedOperationException();
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     public ObjectSet<Byte2FloatMap.Entry> byte2FloatEntrySet() {
/* 699 */       if (this.entries == null) this.entries = ObjectSets.unmodifiable(this.map.byte2FloatEntrySet()); 
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
/*     */     public ObjectSet<Map.Entry<Byte, Float>> entrySet() {
/* 712 */       return (ObjectSet)byte2FloatEntrySet();
/*     */     }
/*     */ 
/*     */     
/*     */     public ByteSet keySet() {
/* 717 */       if (this.keys == null) this.keys = ByteSets.unmodifiable(this.map.keySet()); 
/* 718 */       return this.keys;
/*     */     }
/*     */ 
/*     */     
/*     */     public FloatCollection values() {
/* 723 */       if (this.values == null) this.values = FloatCollections.unmodifiable(this.map.values()); 
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
/*     */     public float getOrDefault(byte key, float defaultValue) {
/* 746 */       return this.map.getOrDefault(key, defaultValue);
/*     */     }
/*     */ 
/*     */     
/*     */     public void forEach(BiConsumer<? super Byte, ? super Float> action) {
/* 751 */       this.map.forEach(action);
/*     */     }
/*     */ 
/*     */     
/*     */     public void replaceAll(BiFunction<? super Byte, ? super Float, ? extends Float> function) {
/* 756 */       throw new UnsupportedOperationException();
/*     */     }
/*     */ 
/*     */     
/*     */     public float putIfAbsent(byte key, float value) {
/* 761 */       throw new UnsupportedOperationException();
/*     */     }
/*     */ 
/*     */     
/*     */     public boolean remove(byte key, float value) {
/* 766 */       throw new UnsupportedOperationException();
/*     */     }
/*     */ 
/*     */     
/*     */     public float replace(byte key, float value) {
/* 771 */       throw new UnsupportedOperationException();
/*     */     }
/*     */ 
/*     */     
/*     */     public boolean replace(byte key, float oldValue, float newValue) {
/* 776 */       throw new UnsupportedOperationException();
/*     */     }
/*     */ 
/*     */     
/*     */     public float computeIfAbsent(byte key, IntToDoubleFunction mappingFunction) {
/* 781 */       throw new UnsupportedOperationException();
/*     */     }
/*     */ 
/*     */     
/*     */     public float computeIfAbsentNullable(byte key, IntFunction<? extends Float> mappingFunction) {
/* 786 */       throw new UnsupportedOperationException();
/*     */     }
/*     */ 
/*     */     
/*     */     public float computeIfAbsent(byte key, Byte2FloatFunction mappingFunction) {
/* 791 */       throw new UnsupportedOperationException();
/*     */     }
/*     */ 
/*     */     
/*     */     public float computeIfPresent(byte key, BiFunction<? super Byte, ? super Float, ? extends Float> remappingFunction) {
/* 796 */       throw new UnsupportedOperationException();
/*     */     }
/*     */ 
/*     */     
/*     */     public float compute(byte key, BiFunction<? super Byte, ? super Float, ? extends Float> remappingFunction) {
/* 801 */       throw new UnsupportedOperationException();
/*     */     }
/*     */ 
/*     */     
/*     */     public float merge(byte key, float value, BiFunction<? super Float, ? super Float, ? extends Float> remappingFunction) {
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
/*     */     public Float getOrDefault(Object key, Float defaultValue) {
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
/*     */     public Float replace(Byte key, Float value) {
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
/*     */     public boolean replace(Byte key, Float oldValue, Float newValue) {
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
/*     */     public Float putIfAbsent(Byte key, Float value) {
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
/*     */     public Float computeIfAbsent(Byte key, Function<? super Byte, ? extends Float> mappingFunction) {
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
/*     */     public Float computeIfPresent(Byte key, BiFunction<? super Byte, ? super Float, ? extends Float> remappingFunction) {
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
/*     */     public Float compute(Byte key, BiFunction<? super Byte, ? super Float, ? extends Float> remappingFunction) {
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
/*     */     public Float merge(Byte key, Float value, BiFunction<? super Float, ? super Float, ? extends Float> remappingFunction) {
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
/*     */   public static Byte2FloatMap unmodifiable(Byte2FloatMap m) {
/* 917 */     return new UnmodifiableMap(m);
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\i\\unimi\dsi\fastutil\bytes\Byte2FloatMaps.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */