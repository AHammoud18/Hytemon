/*     */ package it.unimi.dsi.fastutil.ints;
/*     */ 
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
/*     */ public final class Int2IntMaps
/*     */ {
/*     */   public static ObjectIterator<Int2IntMap.Entry> fastIterator(Int2IntMap map) {
/*  46 */     ObjectSet<Int2IntMap.Entry> entries = map.int2IntEntrySet();
/*  47 */     return (entries instanceof Int2IntMap.FastEntrySet) ? ((Int2IntMap.FastEntrySet)entries).fastIterator() : entries.iterator();
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
/*     */   public static void fastForEach(Int2IntMap map, Consumer<? super Int2IntMap.Entry> consumer) {
/*  62 */     ObjectSet<Int2IntMap.Entry> entries = map.int2IntEntrySet();
/*  63 */     if (entries instanceof Int2IntMap.FastEntrySet) { ((Int2IntMap.FastEntrySet)entries).fastForEach(consumer); }
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
/*     */   public static ObjectIterable<Int2IntMap.Entry> fastIterable(Int2IntMap map) {
/*  79 */     final ObjectSet<Int2IntMap.Entry> entries = map.int2IntEntrySet();
/*  80 */     return (entries instanceof Int2IntMap.FastEntrySet) ? new ObjectIterable<Int2IntMap.Entry>()
/*     */       {
/*     */         public ObjectIterator<Int2IntMap.Entry> iterator() {
/*  83 */           return ((Int2IntMap.FastEntrySet)entries).fastIterator();
/*     */         }
/*     */ 
/*     */         
/*     */         public ObjectSpliterator<Int2IntMap.Entry> spliterator() {
/*  88 */           return entries.spliterator();
/*     */         }
/*     */ 
/*     */         
/*     */         public void forEach(Consumer<? super Int2IntMap.Entry> consumer) {
/*  93 */           ((Int2IntMap.FastEntrySet)entries).fastForEach(consumer);
/*     */         }
/*  95 */       } : (ObjectIterable<Int2IntMap.Entry>)entries;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static class EmptyMap
/*     */     extends Int2IntFunctions.EmptyFunction
/*     */     implements Int2IntMap, Serializable, Cloneable
/*     */   {
/*     */     private static final long serialVersionUID = -7046029254386353129L;
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public boolean containsValue(int v) {
/* 112 */       return false;
/*     */     }
/*     */ 
/*     */     
/*     */     @Deprecated
/*     */     public Integer getOrDefault(Object key, Integer defaultValue) {
/* 118 */       return defaultValue;
/*     */     }
/*     */ 
/*     */     
/*     */     public int getOrDefault(int key, int defaultValue) {
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
/*     */     public void putAll(Map<? extends Integer, ? extends Integer> m) {
/* 139 */       throw new UnsupportedOperationException();
/*     */     }
/*     */ 
/*     */     
/*     */     public ObjectSet<Int2IntMap.Entry> int2IntEntrySet() {
/* 144 */       return (ObjectSet<Int2IntMap.Entry>)ObjectSets.EMPTY_SET;
/*     */     }
/*     */ 
/*     */     
/*     */     public IntSet keySet() {
/* 149 */       return IntSets.EMPTY_SET;
/*     */     }
/*     */ 
/*     */     
/*     */     public IntCollection values() {
/* 154 */       return IntSets.EMPTY_SET;
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     public void forEach(BiConsumer<? super Integer, ? super Integer> consumer) {}
/*     */ 
/*     */     
/*     */     public Object clone() {
/* 163 */       return Int2IntMaps.EMPTY_MAP;
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
/*     */     extends Int2IntFunctions.Singleton
/*     */     implements Int2IntMap, Serializable, Cloneable
/*     */   {
/*     */     private static final long serialVersionUID = -7046029254386353129L;
/*     */     
/*     */     protected transient ObjectSet<Int2IntMap.Entry> entries;
/*     */     
/*     */     protected transient IntSet keys;
/*     */     protected transient IntCollection values;
/*     */     
/*     */     protected Singleton(int key, int value) {
/* 207 */       super(key, value);
/*     */     }
/*     */ 
/*     */     
/*     */     public boolean containsValue(int v) {
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
/* 223 */       return (((Integer)ov).intValue() == this.value);
/*     */     }
/*     */ 
/*     */     
/*     */     public void putAll(Map<? extends Integer, ? extends Integer> m) {
/* 228 */       throw new UnsupportedOperationException();
/*     */     }
/*     */ 
/*     */     
/*     */     public ObjectSet<Int2IntMap.Entry> int2IntEntrySet() {
/* 233 */       if (this.entries == null) this.entries = ObjectSets.singleton(new AbstractInt2IntMap.BasicEntry(this.key, this.value)); 
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
/*     */     public ObjectSet<Map.Entry<Integer, Integer>> entrySet() {
/* 246 */       return (ObjectSet)int2IntEntrySet();
/*     */     }
/*     */ 
/*     */     
/*     */     public IntSet keySet() {
/* 251 */       if (this.keys == null) this.keys = IntSets.singleton(this.key); 
/* 252 */       return this.keys;
/*     */     }
/*     */ 
/*     */     
/*     */     public IntCollection values() {
/* 257 */       if (this.values == null) this.values = IntSets.singleton(this.value); 
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
/* 268 */       return this.key ^ this.value;
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
/*     */   public static Int2IntMap singleton(int key, int value) {
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
/*     */   public static Int2IntMap singleton(Integer key, Integer value) {
/* 313 */     return new Singleton(key.intValue(), value.intValue());
/*     */   }
/*     */   
/*     */   public static class SynchronizedMap
/*     */     extends Int2IntFunctions.SynchronizedFunction implements Int2IntMap, Serializable {
/*     */     private static final long serialVersionUID = -7046029254386353129L;
/*     */     protected final Int2IntMap map;
/*     */     protected transient ObjectSet<Int2IntMap.Entry> entries;
/*     */     protected transient IntSet keys;
/*     */     protected transient IntCollection values;
/*     */     
/*     */     protected SynchronizedMap(Int2IntMap m, Object sync) {
/* 325 */       super(m, sync);
/* 326 */       this.map = m;
/*     */     }
/*     */     
/*     */     protected SynchronizedMap(Int2IntMap m) {
/* 330 */       super(m);
/* 331 */       this.map = m;
/*     */     }
/*     */ 
/*     */     
/*     */     public boolean containsValue(int v) {
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
/*     */     public void putAll(Map<? extends Integer, ? extends Integer> m) {
/* 356 */       synchronized (this.sync) {
/* 357 */         this.map.putAll(m);
/*     */       } 
/*     */     }
/*     */ 
/*     */     
/*     */     public ObjectSet<Int2IntMap.Entry> int2IntEntrySet() {
/* 363 */       synchronized (this.sync) {
/* 364 */         if (this.entries == null) this.entries = ObjectSets.synchronize(this.map.int2IntEntrySet(), this.sync); 
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
/*     */     public ObjectSet<Map.Entry<Integer, Integer>> entrySet() {
/* 378 */       return (ObjectSet)int2IntEntrySet();
/*     */     }
/*     */ 
/*     */     
/*     */     public IntSet keySet() {
/* 383 */       synchronized (this.sync) {
/* 384 */         if (this.keys == null) this.keys = IntSets.synchronize(this.map.keySet(), this.sync); 
/* 385 */         return this.keys;
/*     */       } 
/*     */     }
/*     */ 
/*     */     
/*     */     public IntCollection values() {
/* 391 */       synchronized (this.sync) {
/* 392 */         if (this.values == null) this.values = IntCollections.synchronize(this.map.values(), this.sync); 
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
/*     */     public int getOrDefault(int key, int defaultValue) {
/* 428 */       synchronized (this.sync) {
/* 429 */         return this.map.getOrDefault(key, defaultValue);
/*     */       } 
/*     */     }
/*     */ 
/*     */     
/*     */     public void forEach(BiConsumer<? super Integer, ? super Integer> action) {
/* 435 */       synchronized (this.sync) {
/* 436 */         this.map.forEach(action);
/*     */       } 
/*     */     }
/*     */ 
/*     */     
/*     */     public void replaceAll(BiFunction<? super Integer, ? super Integer, ? extends Integer> function) {
/* 442 */       synchronized (this.sync) {
/* 443 */         this.map.replaceAll(function);
/*     */       } 
/*     */     }
/*     */ 
/*     */     
/*     */     public int putIfAbsent(int key, int value) {
/* 449 */       synchronized (this.sync) {
/* 450 */         return this.map.putIfAbsent(key, value);
/*     */       } 
/*     */     }
/*     */ 
/*     */     
/*     */     public boolean remove(int key, int value) {
/* 456 */       synchronized (this.sync) {
/* 457 */         return this.map.remove(key, value);
/*     */       } 
/*     */     }
/*     */ 
/*     */     
/*     */     public int replace(int key, int value) {
/* 463 */       synchronized (this.sync) {
/* 464 */         return this.map.replace(key, value);
/*     */       } 
/*     */     }
/*     */ 
/*     */     
/*     */     public boolean replace(int key, int oldValue, int newValue) {
/* 470 */       synchronized (this.sync) {
/* 471 */         return this.map.replace(key, oldValue, newValue);
/*     */       } 
/*     */     }
/*     */ 
/*     */     
/*     */     public int computeIfAbsent(int key, IntUnaryOperator mappingFunction) {
/* 477 */       synchronized (this.sync) {
/* 478 */         return this.map.computeIfAbsent(key, mappingFunction);
/*     */       } 
/*     */     }
/*     */ 
/*     */     
/*     */     public int computeIfAbsentNullable(int key, IntFunction<? extends Integer> mappingFunction) {
/* 484 */       synchronized (this.sync) {
/* 485 */         return this.map.computeIfAbsentNullable(key, mappingFunction);
/*     */       } 
/*     */     }
/*     */ 
/*     */     
/*     */     public int computeIfAbsent(int key, Int2IntFunction mappingFunction) {
/* 491 */       synchronized (this.sync) {
/* 492 */         return this.map.computeIfAbsent(key, mappingFunction);
/*     */       } 
/*     */     }
/*     */ 
/*     */     
/*     */     public int computeIfPresent(int key, BiFunction<? super Integer, ? super Integer, ? extends Integer> remappingFunction) {
/* 498 */       synchronized (this.sync) {
/* 499 */         return this.map.computeIfPresent(key, remappingFunction);
/*     */       } 
/*     */     }
/*     */ 
/*     */     
/*     */     public int compute(int key, BiFunction<? super Integer, ? super Integer, ? extends Integer> remappingFunction) {
/* 505 */       synchronized (this.sync) {
/* 506 */         return this.map.compute(key, remappingFunction);
/*     */       } 
/*     */     }
/*     */ 
/*     */     
/*     */     public int merge(int key, int value, BiFunction<? super Integer, ? super Integer, ? extends Integer> remappingFunction) {
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
/*     */     public Integer getOrDefault(Object key, Integer defaultValue) {
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
/*     */     public Integer replace(Integer key, Integer value) {
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
/*     */     public boolean replace(Integer key, Integer oldValue, Integer newValue) {
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
/*     */     public Integer putIfAbsent(Integer key, Integer value) {
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
/*     */     public Integer computeIfAbsent(Integer key, Function<? super Integer, ? extends Integer> mappingFunction) {
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
/*     */     public Integer computeIfPresent(Integer key, BiFunction<? super Integer, ? super Integer, ? extends Integer> remappingFunction) {
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
/*     */     public Integer compute(Integer key, BiFunction<? super Integer, ? super Integer, ? extends Integer> remappingFunction) {
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
/*     */     public Integer merge(Integer key, Integer value, BiFunction<? super Integer, ? super Integer, ? extends Integer> remappingFunction) {
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
/*     */   public static Int2IntMap synchronize(Int2IntMap m) {
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
/*     */   public static Int2IntMap synchronize(Int2IntMap m, Object sync) {
/* 656 */     return new SynchronizedMap(m, sync);
/*     */   }
/*     */   
/*     */   public static class UnmodifiableMap
/*     */     extends Int2IntFunctions.UnmodifiableFunction implements Int2IntMap, Serializable {
/*     */     private static final long serialVersionUID = -7046029254386353129L;
/*     */     protected final Int2IntMap map;
/*     */     protected transient ObjectSet<Int2IntMap.Entry> entries;
/*     */     protected transient IntSet keys;
/*     */     protected transient IntCollection values;
/*     */     
/*     */     protected UnmodifiableMap(Int2IntMap m) {
/* 668 */       super(m);
/* 669 */       this.map = m;
/*     */     }
/*     */ 
/*     */     
/*     */     public boolean containsValue(int v) {
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
/*     */     public void putAll(Map<? extends Integer, ? extends Integer> m) {
/* 690 */       throw new UnsupportedOperationException();
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     public ObjectSet<Int2IntMap.Entry> int2IntEntrySet() {
/* 696 */       if (this.entries == null) this.entries = ObjectSets.unmodifiable(this.map.int2IntEntrySet()); 
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
/*     */     public ObjectSet<Map.Entry<Integer, Integer>> entrySet() {
/* 709 */       return (ObjectSet)int2IntEntrySet();
/*     */     }
/*     */ 
/*     */     
/*     */     public IntSet keySet() {
/* 714 */       if (this.keys == null) this.keys = IntSets.unmodifiable(this.map.keySet()); 
/* 715 */       return this.keys;
/*     */     }
/*     */ 
/*     */     
/*     */     public IntCollection values() {
/* 720 */       if (this.values == null) this.values = IntCollections.unmodifiable(this.map.values()); 
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
/*     */     public int getOrDefault(int key, int defaultValue) {
/* 743 */       return this.map.getOrDefault(key, defaultValue);
/*     */     }
/*     */ 
/*     */     
/*     */     public void forEach(BiConsumer<? super Integer, ? super Integer> action) {
/* 748 */       this.map.forEach(action);
/*     */     }
/*     */ 
/*     */     
/*     */     public void replaceAll(BiFunction<? super Integer, ? super Integer, ? extends Integer> function) {
/* 753 */       throw new UnsupportedOperationException();
/*     */     }
/*     */ 
/*     */     
/*     */     public int putIfAbsent(int key, int value) {
/* 758 */       throw new UnsupportedOperationException();
/*     */     }
/*     */ 
/*     */     
/*     */     public boolean remove(int key, int value) {
/* 763 */       throw new UnsupportedOperationException();
/*     */     }
/*     */ 
/*     */     
/*     */     public int replace(int key, int value) {
/* 768 */       throw new UnsupportedOperationException();
/*     */     }
/*     */ 
/*     */     
/*     */     public boolean replace(int key, int oldValue, int newValue) {
/* 773 */       throw new UnsupportedOperationException();
/*     */     }
/*     */ 
/*     */     
/*     */     public int computeIfAbsent(int key, IntUnaryOperator mappingFunction) {
/* 778 */       throw new UnsupportedOperationException();
/*     */     }
/*     */ 
/*     */     
/*     */     public int computeIfAbsentNullable(int key, IntFunction<? extends Integer> mappingFunction) {
/* 783 */       throw new UnsupportedOperationException();
/*     */     }
/*     */ 
/*     */     
/*     */     public int computeIfAbsent(int key, Int2IntFunction mappingFunction) {
/* 788 */       throw new UnsupportedOperationException();
/*     */     }
/*     */ 
/*     */     
/*     */     public int computeIfPresent(int key, BiFunction<? super Integer, ? super Integer, ? extends Integer> remappingFunction) {
/* 793 */       throw new UnsupportedOperationException();
/*     */     }
/*     */ 
/*     */     
/*     */     public int compute(int key, BiFunction<? super Integer, ? super Integer, ? extends Integer> remappingFunction) {
/* 798 */       throw new UnsupportedOperationException();
/*     */     }
/*     */ 
/*     */     
/*     */     public int merge(int key, int value, BiFunction<? super Integer, ? super Integer, ? extends Integer> remappingFunction) {
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
/*     */     public Integer getOrDefault(Object key, Integer defaultValue) {
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
/*     */     public Integer replace(Integer key, Integer value) {
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
/*     */     public boolean replace(Integer key, Integer oldValue, Integer newValue) {
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
/*     */     public Integer putIfAbsent(Integer key, Integer value) {
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
/*     */     public Integer computeIfAbsent(Integer key, Function<? super Integer, ? extends Integer> mappingFunction) {
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
/*     */     public Integer computeIfPresent(Integer key, BiFunction<? super Integer, ? super Integer, ? extends Integer> remappingFunction) {
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
/*     */     public Integer compute(Integer key, BiFunction<? super Integer, ? super Integer, ? extends Integer> remappingFunction) {
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
/*     */     public Integer merge(Integer key, Integer value, BiFunction<? super Integer, ? super Integer, ? extends Integer> remappingFunction) {
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
/*     */   public static Int2IntMap unmodifiable(Int2IntMap m) {
/* 914 */     return new UnmodifiableMap(m);
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\i\\unimi\dsi\fastutil\ints\Int2IntMaps.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */