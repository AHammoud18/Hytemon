/*     */ package it.unimi.dsi.fastutil.objects;
/*     */ 
/*     */ import it.unimi.dsi.fastutil.bytes.ByteCollection;
/*     */ import it.unimi.dsi.fastutil.bytes.ByteCollections;
/*     */ import it.unimi.dsi.fastutil.bytes.ByteSets;
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
/*     */ public final class Reference2ByteMaps
/*     */ {
/*     */   public static <K> ObjectIterator<Reference2ByteMap.Entry<K>> fastIterator(Reference2ByteMap<K> map) {
/*  44 */     ObjectSet<Reference2ByteMap.Entry<K>> entries = map.reference2ByteEntrySet();
/*  45 */     return (entries instanceof Reference2ByteMap.FastEntrySet) ? ((Reference2ByteMap.FastEntrySet)entries).fastIterator() : entries.iterator();
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
/*     */   public static <K> void fastForEach(Reference2ByteMap<K> map, Consumer<? super Reference2ByteMap.Entry<K>> consumer) {
/*  60 */     ObjectSet<Reference2ByteMap.Entry<K>> entries = map.reference2ByteEntrySet();
/*  61 */     if (entries instanceof Reference2ByteMap.FastEntrySet) { ((Reference2ByteMap.FastEntrySet)entries).fastForEach(consumer); }
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
/*     */   public static <K> ObjectIterable<Reference2ByteMap.Entry<K>> fastIterable(Reference2ByteMap<K> map) {
/*  77 */     final ObjectSet<Reference2ByteMap.Entry<K>> entries = map.reference2ByteEntrySet();
/*  78 */     return (entries instanceof Reference2ByteMap.FastEntrySet) ? (ObjectIterable)new ObjectIterable<Reference2ByteMap.Entry<Reference2ByteMap.Entry<K>>>()
/*     */       {
/*     */         public ObjectIterator<Reference2ByteMap.Entry<K>> iterator() {
/*  81 */           return ((Reference2ByteMap.FastEntrySet<K>)entries).fastIterator();
/*     */         }
/*     */ 
/*     */         
/*     */         public ObjectSpliterator<Reference2ByteMap.Entry<K>> spliterator() {
/*  86 */           return entries.spliterator();
/*     */         }
/*     */ 
/*     */         
/*     */         public void forEach(Consumer<? super Reference2ByteMap.Entry<K>> consumer) {
/*  91 */           ((Reference2ByteMap.FastEntrySet<K>)entries).fastForEach(consumer);
/*     */         }
/*  93 */       } : entries;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static class EmptyMap<K>
/*     */     extends Reference2ByteFunctions.EmptyFunction<K>
/*     */     implements Reference2ByteMap<K>, Serializable, Cloneable
/*     */   {
/*     */     private static final long serialVersionUID = -7046029254386353129L;
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public boolean containsValue(byte v) {
/* 110 */       return false;
/*     */     }
/*     */ 
/*     */     
/*     */     @Deprecated
/*     */     public Byte getOrDefault(Object key, Byte defaultValue) {
/* 116 */       return defaultValue;
/*     */     }
/*     */ 
/*     */     
/*     */     public byte getOrDefault(Object key, byte defaultValue) {
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
/*     */     public void putAll(Map<? extends K, ? extends Byte> m) {
/* 137 */       throw new UnsupportedOperationException();
/*     */     }
/*     */ 
/*     */     
/*     */     public ObjectSet<Reference2ByteMap.Entry<K>> reference2ByteEntrySet() {
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
/*     */     public ByteCollection values() {
/* 153 */       return (ByteCollection)ByteSets.EMPTY_SET;
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     public void forEach(BiConsumer<? super K, ? super Byte> consumer) {}
/*     */ 
/*     */     
/*     */     public Object clone() {
/* 162 */       return Reference2ByteMaps.EMPTY_MAP;
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
/*     */   public static <K> Reference2ByteMap<K> emptyMap() {
/* 203 */     return EMPTY_MAP;
/*     */   }
/*     */ 
/*     */   
/*     */   public static class Singleton<K>
/*     */     extends Reference2ByteFunctions.Singleton<K>
/*     */     implements Reference2ByteMap<K>, Serializable, Cloneable
/*     */   {
/*     */     private static final long serialVersionUID = -7046029254386353129L;
/*     */     
/*     */     protected transient ObjectSet<Reference2ByteMap.Entry<K>> entries;
/*     */     
/*     */     protected transient ReferenceSet<K> keys;
/*     */     protected transient ByteCollection values;
/*     */     
/*     */     protected Singleton(K key, byte value) {
/* 219 */       super(key, value);
/*     */     }
/*     */ 
/*     */     
/*     */     public boolean containsValue(byte v) {
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
/* 235 */       return (((Byte)ov).byteValue() == this.value);
/*     */     }
/*     */ 
/*     */     
/*     */     public void putAll(Map<? extends K, ? extends Byte> m) {
/* 240 */       throw new UnsupportedOperationException();
/*     */     }
/*     */ 
/*     */     
/*     */     public ObjectSet<Reference2ByteMap.Entry<K>> reference2ByteEntrySet() {
/* 245 */       if (this.entries == null) this.entries = ObjectSets.singleton(new AbstractReference2ByteMap.BasicEntry<>(this.key, this.value)); 
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
/*     */     public ObjectSet<Map.Entry<K, Byte>> entrySet() {
/* 258 */       return (ObjectSet)reference2ByteEntrySet();
/*     */     }
/*     */ 
/*     */     
/*     */     public ReferenceSet<K> keySet() {
/* 263 */       if (this.keys == null) this.keys = ReferenceSets.singleton(this.key); 
/* 264 */       return this.keys;
/*     */     }
/*     */ 
/*     */     
/*     */     public ByteCollection values() {
/* 269 */       if (this.values == null) this.values = (ByteCollection)ByteSets.singleton(this.value); 
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
/* 280 */       return System.identityHashCode(this.key) ^ this.value;
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
/*     */   public static <K> Reference2ByteMap<K> singleton(K key, byte value) {
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
/*     */   public static <K> Reference2ByteMap<K> singleton(K key, Byte value) {
/* 325 */     return new Singleton<>(key, value.byteValue());
/*     */   }
/*     */   
/*     */   public static class SynchronizedMap<K>
/*     */     extends Reference2ByteFunctions.SynchronizedFunction<K> implements Reference2ByteMap<K>, Serializable {
/*     */     private static final long serialVersionUID = -7046029254386353129L;
/*     */     protected final Reference2ByteMap<K> map;
/*     */     protected transient ObjectSet<Reference2ByteMap.Entry<K>> entries;
/*     */     protected transient ReferenceSet<K> keys;
/*     */     protected transient ByteCollection values;
/*     */     
/*     */     protected SynchronizedMap(Reference2ByteMap<K> m, Object sync) {
/* 337 */       super(m, sync);
/* 338 */       this.map = m;
/*     */     }
/*     */     
/*     */     protected SynchronizedMap(Reference2ByteMap<K> m) {
/* 342 */       super(m);
/* 343 */       this.map = m;
/*     */     }
/*     */ 
/*     */     
/*     */     public boolean containsValue(byte v) {
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
/*     */     public void putAll(Map<? extends K, ? extends Byte> m) {
/* 368 */       synchronized (this.sync) {
/* 369 */         this.map.putAll(m);
/*     */       } 
/*     */     }
/*     */ 
/*     */     
/*     */     public ObjectSet<Reference2ByteMap.Entry<K>> reference2ByteEntrySet() {
/* 375 */       synchronized (this.sync) {
/* 376 */         if (this.entries == null) this.entries = ObjectSets.synchronize(this.map.reference2ByteEntrySet(), this.sync); 
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
/*     */     public ObjectSet<Map.Entry<K, Byte>> entrySet() {
/* 390 */       return (ObjectSet)reference2ByteEntrySet();
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
/*     */     public ByteCollection values() {
/* 403 */       synchronized (this.sync) {
/* 404 */         if (this.values == null) this.values = ByteCollections.synchronize(this.map.values(), this.sync); 
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
/*     */     public byte getOrDefault(Object key, byte defaultValue) {
/* 440 */       synchronized (this.sync) {
/* 441 */         return this.map.getOrDefault(key, defaultValue);
/*     */       } 
/*     */     }
/*     */ 
/*     */     
/*     */     public void forEach(BiConsumer<? super K, ? super Byte> action) {
/* 447 */       synchronized (this.sync) {
/* 448 */         this.map.forEach(action);
/*     */       } 
/*     */     }
/*     */ 
/*     */     
/*     */     public void replaceAll(BiFunction<? super K, ? super Byte, ? extends Byte> function) {
/* 454 */       synchronized (this.sync) {
/* 455 */         this.map.replaceAll(function);
/*     */       } 
/*     */     }
/*     */ 
/*     */     
/*     */     public byte putIfAbsent(K key, byte value) {
/* 461 */       synchronized (this.sync) {
/* 462 */         return this.map.putIfAbsent(key, value);
/*     */       } 
/*     */     }
/*     */ 
/*     */     
/*     */     public boolean remove(Object key, byte value) {
/* 468 */       synchronized (this.sync) {
/* 469 */         return this.map.remove(key, value);
/*     */       } 
/*     */     }
/*     */ 
/*     */     
/*     */     public byte replace(K key, byte value) {
/* 475 */       synchronized (this.sync) {
/* 476 */         return this.map.replace(key, value);
/*     */       } 
/*     */     }
/*     */ 
/*     */     
/*     */     public boolean replace(K key, byte oldValue, byte newValue) {
/* 482 */       synchronized (this.sync) {
/* 483 */         return this.map.replace(key, oldValue, newValue);
/*     */       } 
/*     */     }
/*     */ 
/*     */     
/*     */     public byte computeIfAbsent(K key, ToIntFunction<? super K> mappingFunction) {
/* 489 */       synchronized (this.sync) {
/* 490 */         return this.map.computeIfAbsent(key, mappingFunction);
/*     */       } 
/*     */     }
/*     */ 
/*     */     
/*     */     public byte computeIfAbsent(K key, Reference2ByteFunction<? super K> mappingFunction) {
/* 496 */       synchronized (this.sync) {
/* 497 */         return this.map.computeIfAbsent(key, mappingFunction);
/*     */       } 
/*     */     }
/*     */ 
/*     */     
/*     */     public byte computeByteIfPresent(K key, BiFunction<? super K, ? super Byte, ? extends Byte> remappingFunction) {
/* 503 */       synchronized (this.sync) {
/* 504 */         return this.map.computeByteIfPresent(key, remappingFunction);
/*     */       } 
/*     */     }
/*     */ 
/*     */     
/*     */     public byte computeByte(K key, BiFunction<? super K, ? super Byte, ? extends Byte> remappingFunction) {
/* 510 */       synchronized (this.sync) {
/* 511 */         return this.map.computeByte(key, remappingFunction);
/*     */       } 
/*     */     }
/*     */ 
/*     */     
/*     */     public byte merge(K key, byte value, BiFunction<? super Byte, ? super Byte, ? extends Byte> remappingFunction) {
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
/*     */     public Byte getOrDefault(Object key, Byte defaultValue) {
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
/*     */     public Byte replace(K key, Byte value) {
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
/*     */     public boolean replace(K key, Byte oldValue, Byte newValue) {
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
/*     */     public Byte putIfAbsent(K key, Byte value) {
/* 582 */       synchronized (this.sync) {
/* 583 */         return this.map.putIfAbsent(key, value);
/*     */       } 
/*     */     }
/*     */ 
/*     */     
/*     */     public Byte computeIfAbsent(K key, Function<? super K, ? extends Byte> mappingFunction) {
/* 589 */       synchronized (this.sync) {
/* 590 */         return this.map.computeIfAbsent(key, mappingFunction);
/*     */       } 
/*     */     }
/*     */ 
/*     */     
/*     */     public Byte computeIfPresent(K key, BiFunction<? super K, ? super Byte, ? extends Byte> remappingFunction) {
/* 596 */       synchronized (this.sync) {
/* 597 */         return this.map.computeIfPresent(key, remappingFunction);
/*     */       } 
/*     */     }
/*     */ 
/*     */     
/*     */     public Byte compute(K key, BiFunction<? super K, ? super Byte, ? extends Byte> remappingFunction) {
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
/*     */     public Byte merge(K key, Byte value, BiFunction<? super Byte, ? super Byte, ? extends Byte> remappingFunction) {
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
/*     */   public static <K> Reference2ByteMap<K> synchronize(Reference2ByteMap<K> m) {
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
/*     */   public static <K> Reference2ByteMap<K> synchronize(Reference2ByteMap<K> m, Object sync) {
/* 643 */     return new SynchronizedMap<>(m, sync);
/*     */   }
/*     */   
/*     */   public static class UnmodifiableMap<K>
/*     */     extends Reference2ByteFunctions.UnmodifiableFunction<K> implements Reference2ByteMap<K>, Serializable {
/*     */     private static final long serialVersionUID = -7046029254386353129L;
/*     */     protected final Reference2ByteMap<? extends K> map;
/*     */     protected transient ObjectSet<Reference2ByteMap.Entry<K>> entries;
/*     */     protected transient ReferenceSet<K> keys;
/*     */     protected transient ByteCollection values;
/*     */     
/*     */     protected UnmodifiableMap(Reference2ByteMap<? extends K> m) {
/* 655 */       super(m);
/* 656 */       this.map = m;
/*     */     }
/*     */ 
/*     */     
/*     */     public boolean containsValue(byte v) {
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
/*     */     public void putAll(Map<? extends K, ? extends Byte> m) {
/* 677 */       throw new UnsupportedOperationException();
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     public ObjectSet<Reference2ByteMap.Entry<K>> reference2ByteEntrySet() {
/* 683 */       if (this.entries == null) this.entries = ObjectSets.unmodifiable((ObjectSet)this.map.reference2ByteEntrySet()); 
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
/*     */     public ObjectSet<Map.Entry<K, Byte>> entrySet() {
/* 696 */       return (ObjectSet)reference2ByteEntrySet();
/*     */     }
/*     */ 
/*     */     
/*     */     public ReferenceSet<K> keySet() {
/* 701 */       if (this.keys == null) this.keys = ReferenceSets.unmodifiable(this.map.keySet()); 
/* 702 */       return this.keys;
/*     */     }
/*     */ 
/*     */     
/*     */     public ByteCollection values() {
/* 707 */       if (this.values == null) this.values = ByteCollections.unmodifiable(this.map.values()); 
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
/*     */     public byte getOrDefault(Object key, byte defaultValue) {
/* 731 */       return this.map.getOrDefault(key, defaultValue);
/*     */     }
/*     */ 
/*     */     
/*     */     public void forEach(BiConsumer<? super K, ? super Byte> action) {
/* 736 */       this.map.forEach(action);
/*     */     }
/*     */ 
/*     */     
/*     */     public void replaceAll(BiFunction<? super K, ? super Byte, ? extends Byte> function) {
/* 741 */       throw new UnsupportedOperationException();
/*     */     }
/*     */ 
/*     */     
/*     */     public byte putIfAbsent(K key, byte value) {
/* 746 */       throw new UnsupportedOperationException();
/*     */     }
/*     */ 
/*     */     
/*     */     public boolean remove(Object key, byte value) {
/* 751 */       throw new UnsupportedOperationException();
/*     */     }
/*     */ 
/*     */     
/*     */     public byte replace(K key, byte value) {
/* 756 */       throw new UnsupportedOperationException();
/*     */     }
/*     */ 
/*     */     
/*     */     public boolean replace(K key, byte oldValue, byte newValue) {
/* 761 */       throw new UnsupportedOperationException();
/*     */     }
/*     */ 
/*     */     
/*     */     public byte computeIfAbsent(K key, ToIntFunction<? super K> mappingFunction) {
/* 766 */       throw new UnsupportedOperationException();
/*     */     }
/*     */ 
/*     */     
/*     */     public byte computeIfAbsent(K key, Reference2ByteFunction<? super K> mappingFunction) {
/* 771 */       throw new UnsupportedOperationException();
/*     */     }
/*     */ 
/*     */     
/*     */     public byte computeByteIfPresent(K key, BiFunction<? super K, ? super Byte, ? extends Byte> remappingFunction) {
/* 776 */       throw new UnsupportedOperationException();
/*     */     }
/*     */ 
/*     */     
/*     */     public byte computeByte(K key, BiFunction<? super K, ? super Byte, ? extends Byte> remappingFunction) {
/* 781 */       throw new UnsupportedOperationException();
/*     */     }
/*     */ 
/*     */     
/*     */     public byte merge(K key, byte value, BiFunction<? super Byte, ? super Byte, ? extends Byte> remappingFunction) {
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
/*     */     public Byte getOrDefault(Object key, Byte defaultValue) {
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
/*     */     public Byte replace(K key, Byte value) {
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
/*     */     public boolean replace(K key, Byte oldValue, Byte newValue) {
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
/*     */     public Byte putIfAbsent(K key, Byte value) {
/* 842 */       throw new UnsupportedOperationException();
/*     */     }
/*     */ 
/*     */     
/*     */     public Byte computeIfAbsent(K key, Function<? super K, ? extends Byte> mappingFunction) {
/* 847 */       throw new UnsupportedOperationException();
/*     */     }
/*     */ 
/*     */     
/*     */     public Byte computeIfPresent(K key, BiFunction<? super K, ? super Byte, ? extends Byte> remappingFunction) {
/* 852 */       throw new UnsupportedOperationException();
/*     */     }
/*     */ 
/*     */     
/*     */     public Byte compute(K key, BiFunction<? super K, ? super Byte, ? extends Byte> remappingFunction) {
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
/*     */     public Byte merge(K key, Byte value, BiFunction<? super Byte, ? super Byte, ? extends Byte> remappingFunction) {
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
/*     */   public static <K> Reference2ByteMap<K> unmodifiable(Reference2ByteMap<? extends K> m) {
/* 880 */     return new UnmodifiableMap<>(m);
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\i\\unimi\dsi\fastutil\objects\Reference2ByteMaps.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */