/*     */ package it.unimi.dsi.fastutil.chars;
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
/*     */ public final class Char2CharMaps
/*     */ {
/*     */   public static ObjectIterator<Char2CharMap.Entry> fastIterator(Char2CharMap map) {
/*  46 */     ObjectSet<Char2CharMap.Entry> entries = map.char2CharEntrySet();
/*  47 */     return (entries instanceof Char2CharMap.FastEntrySet) ? ((Char2CharMap.FastEntrySet)entries).fastIterator() : entries.iterator();
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
/*     */   public static void fastForEach(Char2CharMap map, Consumer<? super Char2CharMap.Entry> consumer) {
/*  62 */     ObjectSet<Char2CharMap.Entry> entries = map.char2CharEntrySet();
/*  63 */     if (entries instanceof Char2CharMap.FastEntrySet) { ((Char2CharMap.FastEntrySet)entries).fastForEach(consumer); }
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
/*     */   public static ObjectIterable<Char2CharMap.Entry> fastIterable(Char2CharMap map) {
/*  79 */     final ObjectSet<Char2CharMap.Entry> entries = map.char2CharEntrySet();
/*  80 */     return (entries instanceof Char2CharMap.FastEntrySet) ? new ObjectIterable<Char2CharMap.Entry>()
/*     */       {
/*     */         public ObjectIterator<Char2CharMap.Entry> iterator() {
/*  83 */           return ((Char2CharMap.FastEntrySet)entries).fastIterator();
/*     */         }
/*     */ 
/*     */         
/*     */         public ObjectSpliterator<Char2CharMap.Entry> spliterator() {
/*  88 */           return entries.spliterator();
/*     */         }
/*     */ 
/*     */         
/*     */         public void forEach(Consumer<? super Char2CharMap.Entry> consumer) {
/*  93 */           ((Char2CharMap.FastEntrySet)entries).fastForEach(consumer);
/*     */         }
/*  95 */       } : (ObjectIterable<Char2CharMap.Entry>)entries;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static class EmptyMap
/*     */     extends Char2CharFunctions.EmptyFunction
/*     */     implements Char2CharMap, Serializable, Cloneable
/*     */   {
/*     */     private static final long serialVersionUID = -7046029254386353129L;
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public boolean containsValue(char v) {
/* 112 */       return false;
/*     */     }
/*     */ 
/*     */     
/*     */     @Deprecated
/*     */     public Character getOrDefault(Object key, Character defaultValue) {
/* 118 */       return defaultValue;
/*     */     }
/*     */ 
/*     */     
/*     */     public char getOrDefault(char key, char defaultValue) {
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
/*     */     public void putAll(Map<? extends Character, ? extends Character> m) {
/* 139 */       throw new UnsupportedOperationException();
/*     */     }
/*     */ 
/*     */     
/*     */     public ObjectSet<Char2CharMap.Entry> char2CharEntrySet() {
/* 144 */       return (ObjectSet<Char2CharMap.Entry>)ObjectSets.EMPTY_SET;
/*     */     }
/*     */ 
/*     */     
/*     */     public CharSet keySet() {
/* 149 */       return CharSets.EMPTY_SET;
/*     */     }
/*     */ 
/*     */     
/*     */     public CharCollection values() {
/* 154 */       return CharSets.EMPTY_SET;
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     public void forEach(BiConsumer<? super Character, ? super Character> consumer) {}
/*     */ 
/*     */     
/*     */     public Object clone() {
/* 163 */       return Char2CharMaps.EMPTY_MAP;
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
/*     */     extends Char2CharFunctions.Singleton
/*     */     implements Char2CharMap, Serializable, Cloneable
/*     */   {
/*     */     private static final long serialVersionUID = -7046029254386353129L;
/*     */     
/*     */     protected transient ObjectSet<Char2CharMap.Entry> entries;
/*     */     
/*     */     protected transient CharSet keys;
/*     */     protected transient CharCollection values;
/*     */     
/*     */     protected Singleton(char key, char value) {
/* 207 */       super(key, value);
/*     */     }
/*     */ 
/*     */     
/*     */     public boolean containsValue(char v) {
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
/* 223 */       return (((Character)ov).charValue() == this.value);
/*     */     }
/*     */ 
/*     */     
/*     */     public void putAll(Map<? extends Character, ? extends Character> m) {
/* 228 */       throw new UnsupportedOperationException();
/*     */     }
/*     */ 
/*     */     
/*     */     public ObjectSet<Char2CharMap.Entry> char2CharEntrySet() {
/* 233 */       if (this.entries == null) this.entries = ObjectSets.singleton(new AbstractChar2CharMap.BasicEntry(this.key, this.value)); 
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
/*     */     public ObjectSet<Map.Entry<Character, Character>> entrySet() {
/* 246 */       return (ObjectSet)char2CharEntrySet();
/*     */     }
/*     */ 
/*     */     
/*     */     public CharSet keySet() {
/* 251 */       if (this.keys == null) this.keys = CharSets.singleton(this.key); 
/* 252 */       return this.keys;
/*     */     }
/*     */ 
/*     */     
/*     */     public CharCollection values() {
/* 257 */       if (this.values == null) this.values = CharSets.singleton(this.value); 
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
/*     */   public static Char2CharMap singleton(char key, char value) {
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
/*     */   public static Char2CharMap singleton(Character key, Character value) {
/* 313 */     return new Singleton(key.charValue(), value.charValue());
/*     */   }
/*     */   
/*     */   public static class SynchronizedMap
/*     */     extends Char2CharFunctions.SynchronizedFunction implements Char2CharMap, Serializable {
/*     */     private static final long serialVersionUID = -7046029254386353129L;
/*     */     protected final Char2CharMap map;
/*     */     protected transient ObjectSet<Char2CharMap.Entry> entries;
/*     */     protected transient CharSet keys;
/*     */     protected transient CharCollection values;
/*     */     
/*     */     protected SynchronizedMap(Char2CharMap m, Object sync) {
/* 325 */       super(m, sync);
/* 326 */       this.map = m;
/*     */     }
/*     */     
/*     */     protected SynchronizedMap(Char2CharMap m) {
/* 330 */       super(m);
/* 331 */       this.map = m;
/*     */     }
/*     */ 
/*     */     
/*     */     public boolean containsValue(char v) {
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
/*     */     public void putAll(Map<? extends Character, ? extends Character> m) {
/* 356 */       synchronized (this.sync) {
/* 357 */         this.map.putAll(m);
/*     */       } 
/*     */     }
/*     */ 
/*     */     
/*     */     public ObjectSet<Char2CharMap.Entry> char2CharEntrySet() {
/* 363 */       synchronized (this.sync) {
/* 364 */         if (this.entries == null) this.entries = ObjectSets.synchronize(this.map.char2CharEntrySet(), this.sync); 
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
/*     */     public ObjectSet<Map.Entry<Character, Character>> entrySet() {
/* 378 */       return (ObjectSet)char2CharEntrySet();
/*     */     }
/*     */ 
/*     */     
/*     */     public CharSet keySet() {
/* 383 */       synchronized (this.sync) {
/* 384 */         if (this.keys == null) this.keys = CharSets.synchronize(this.map.keySet(), this.sync); 
/* 385 */         return this.keys;
/*     */       } 
/*     */     }
/*     */ 
/*     */     
/*     */     public CharCollection values() {
/* 391 */       synchronized (this.sync) {
/* 392 */         if (this.values == null) this.values = CharCollections.synchronize(this.map.values(), this.sync); 
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
/*     */     public char getOrDefault(char key, char defaultValue) {
/* 428 */       synchronized (this.sync) {
/* 429 */         return this.map.getOrDefault(key, defaultValue);
/*     */       } 
/*     */     }
/*     */ 
/*     */     
/*     */     public void forEach(BiConsumer<? super Character, ? super Character> action) {
/* 435 */       synchronized (this.sync) {
/* 436 */         this.map.forEach(action);
/*     */       } 
/*     */     }
/*     */ 
/*     */     
/*     */     public void replaceAll(BiFunction<? super Character, ? super Character, ? extends Character> function) {
/* 442 */       synchronized (this.sync) {
/* 443 */         this.map.replaceAll(function);
/*     */       } 
/*     */     }
/*     */ 
/*     */     
/*     */     public char putIfAbsent(char key, char value) {
/* 449 */       synchronized (this.sync) {
/* 450 */         return this.map.putIfAbsent(key, value);
/*     */       } 
/*     */     }
/*     */ 
/*     */     
/*     */     public boolean remove(char key, char value) {
/* 456 */       synchronized (this.sync) {
/* 457 */         return this.map.remove(key, value);
/*     */       } 
/*     */     }
/*     */ 
/*     */     
/*     */     public char replace(char key, char value) {
/* 463 */       synchronized (this.sync) {
/* 464 */         return this.map.replace(key, value);
/*     */       } 
/*     */     }
/*     */ 
/*     */     
/*     */     public boolean replace(char key, char oldValue, char newValue) {
/* 470 */       synchronized (this.sync) {
/* 471 */         return this.map.replace(key, oldValue, newValue);
/*     */       } 
/*     */     }
/*     */ 
/*     */     
/*     */     public char computeIfAbsent(char key, IntUnaryOperator mappingFunction) {
/* 477 */       synchronized (this.sync) {
/* 478 */         return this.map.computeIfAbsent(key, mappingFunction);
/*     */       } 
/*     */     }
/*     */ 
/*     */     
/*     */     public char computeIfAbsentNullable(char key, IntFunction<? extends Character> mappingFunction) {
/* 484 */       synchronized (this.sync) {
/* 485 */         return this.map.computeIfAbsentNullable(key, mappingFunction);
/*     */       } 
/*     */     }
/*     */ 
/*     */     
/*     */     public char computeIfAbsent(char key, Char2CharFunction mappingFunction) {
/* 491 */       synchronized (this.sync) {
/* 492 */         return this.map.computeIfAbsent(key, mappingFunction);
/*     */       } 
/*     */     }
/*     */ 
/*     */     
/*     */     public char computeIfPresent(char key, BiFunction<? super Character, ? super Character, ? extends Character> remappingFunction) {
/* 498 */       synchronized (this.sync) {
/* 499 */         return this.map.computeIfPresent(key, remappingFunction);
/*     */       } 
/*     */     }
/*     */ 
/*     */     
/*     */     public char compute(char key, BiFunction<? super Character, ? super Character, ? extends Character> remappingFunction) {
/* 505 */       synchronized (this.sync) {
/* 506 */         return this.map.compute(key, remappingFunction);
/*     */       } 
/*     */     }
/*     */ 
/*     */     
/*     */     public char merge(char key, char value, BiFunction<? super Character, ? super Character, ? extends Character> remappingFunction) {
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
/*     */     public Character getOrDefault(Object key, Character defaultValue) {
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
/*     */     public Character replace(Character key, Character value) {
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
/*     */     public boolean replace(Character key, Character oldValue, Character newValue) {
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
/*     */     public Character putIfAbsent(Character key, Character value) {
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
/*     */     public Character computeIfAbsent(Character key, Function<? super Character, ? extends Character> mappingFunction) {
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
/*     */     public Character computeIfPresent(Character key, BiFunction<? super Character, ? super Character, ? extends Character> remappingFunction) {
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
/*     */     public Character compute(Character key, BiFunction<? super Character, ? super Character, ? extends Character> remappingFunction) {
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
/*     */     public Character merge(Character key, Character value, BiFunction<? super Character, ? super Character, ? extends Character> remappingFunction) {
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
/*     */   public static Char2CharMap synchronize(Char2CharMap m) {
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
/*     */   public static Char2CharMap synchronize(Char2CharMap m, Object sync) {
/* 656 */     return new SynchronizedMap(m, sync);
/*     */   }
/*     */   
/*     */   public static class UnmodifiableMap
/*     */     extends Char2CharFunctions.UnmodifiableFunction implements Char2CharMap, Serializable {
/*     */     private static final long serialVersionUID = -7046029254386353129L;
/*     */     protected final Char2CharMap map;
/*     */     protected transient ObjectSet<Char2CharMap.Entry> entries;
/*     */     protected transient CharSet keys;
/*     */     protected transient CharCollection values;
/*     */     
/*     */     protected UnmodifiableMap(Char2CharMap m) {
/* 668 */       super(m);
/* 669 */       this.map = m;
/*     */     }
/*     */ 
/*     */     
/*     */     public boolean containsValue(char v) {
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
/*     */     public void putAll(Map<? extends Character, ? extends Character> m) {
/* 690 */       throw new UnsupportedOperationException();
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     public ObjectSet<Char2CharMap.Entry> char2CharEntrySet() {
/* 696 */       if (this.entries == null) this.entries = ObjectSets.unmodifiable(this.map.char2CharEntrySet()); 
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
/*     */     public ObjectSet<Map.Entry<Character, Character>> entrySet() {
/* 709 */       return (ObjectSet)char2CharEntrySet();
/*     */     }
/*     */ 
/*     */     
/*     */     public CharSet keySet() {
/* 714 */       if (this.keys == null) this.keys = CharSets.unmodifiable(this.map.keySet()); 
/* 715 */       return this.keys;
/*     */     }
/*     */ 
/*     */     
/*     */     public CharCollection values() {
/* 720 */       if (this.values == null) this.values = CharCollections.unmodifiable(this.map.values()); 
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
/*     */     public char getOrDefault(char key, char defaultValue) {
/* 743 */       return this.map.getOrDefault(key, defaultValue);
/*     */     }
/*     */ 
/*     */     
/*     */     public void forEach(BiConsumer<? super Character, ? super Character> action) {
/* 748 */       this.map.forEach(action);
/*     */     }
/*     */ 
/*     */     
/*     */     public void replaceAll(BiFunction<? super Character, ? super Character, ? extends Character> function) {
/* 753 */       throw new UnsupportedOperationException();
/*     */     }
/*     */ 
/*     */     
/*     */     public char putIfAbsent(char key, char value) {
/* 758 */       throw new UnsupportedOperationException();
/*     */     }
/*     */ 
/*     */     
/*     */     public boolean remove(char key, char value) {
/* 763 */       throw new UnsupportedOperationException();
/*     */     }
/*     */ 
/*     */     
/*     */     public char replace(char key, char value) {
/* 768 */       throw new UnsupportedOperationException();
/*     */     }
/*     */ 
/*     */     
/*     */     public boolean replace(char key, char oldValue, char newValue) {
/* 773 */       throw new UnsupportedOperationException();
/*     */     }
/*     */ 
/*     */     
/*     */     public char computeIfAbsent(char key, IntUnaryOperator mappingFunction) {
/* 778 */       throw new UnsupportedOperationException();
/*     */     }
/*     */ 
/*     */     
/*     */     public char computeIfAbsentNullable(char key, IntFunction<? extends Character> mappingFunction) {
/* 783 */       throw new UnsupportedOperationException();
/*     */     }
/*     */ 
/*     */     
/*     */     public char computeIfAbsent(char key, Char2CharFunction mappingFunction) {
/* 788 */       throw new UnsupportedOperationException();
/*     */     }
/*     */ 
/*     */     
/*     */     public char computeIfPresent(char key, BiFunction<? super Character, ? super Character, ? extends Character> remappingFunction) {
/* 793 */       throw new UnsupportedOperationException();
/*     */     }
/*     */ 
/*     */     
/*     */     public char compute(char key, BiFunction<? super Character, ? super Character, ? extends Character> remappingFunction) {
/* 798 */       throw new UnsupportedOperationException();
/*     */     }
/*     */ 
/*     */     
/*     */     public char merge(char key, char value, BiFunction<? super Character, ? super Character, ? extends Character> remappingFunction) {
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
/*     */     public Character getOrDefault(Object key, Character defaultValue) {
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
/*     */     public Character replace(Character key, Character value) {
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
/*     */     public boolean replace(Character key, Character oldValue, Character newValue) {
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
/*     */     public Character putIfAbsent(Character key, Character value) {
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
/*     */     public Character computeIfAbsent(Character key, Function<? super Character, ? extends Character> mappingFunction) {
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
/*     */     public Character computeIfPresent(Character key, BiFunction<? super Character, ? super Character, ? extends Character> remappingFunction) {
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
/*     */     public Character compute(Character key, BiFunction<? super Character, ? super Character, ? extends Character> remappingFunction) {
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
/*     */     public Character merge(Character key, Character value, BiFunction<? super Character, ? super Character, ? extends Character> remappingFunction) {
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
/*     */   public static Char2CharMap unmodifiable(Char2CharMap m) {
/* 914 */     return new UnmodifiableMap(m);
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\i\\unimi\dsi\fastutil\chars\Char2CharMaps.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */