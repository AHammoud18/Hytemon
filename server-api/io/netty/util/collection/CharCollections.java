/*     */ package io.netty.util.collection;
/*     */ 
/*     */ import java.util.Collection;
/*     */ import java.util.Collections;
/*     */ import java.util.Iterator;
/*     */ import java.util.Map;
/*     */ import java.util.NoSuchElementException;
/*     */ import java.util.Set;
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
/*     */ public final class CharCollections
/*     */ {
/*  29 */   private static final CharObjectMap<Object> EMPTY_MAP = new EmptyMap();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static <V> CharObjectMap<V> emptyMap() {
/*  39 */     return (CharObjectMap)EMPTY_MAP;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static <V> CharObjectMap<V> unmodifiableMap(CharObjectMap<V> map) {
/*  46 */     return new UnmodifiableMap<>(map);
/*     */   }
/*     */   
/*     */   private static final class EmptyMap
/*     */     implements CharObjectMap<Object>
/*     */   {
/*     */     private EmptyMap() {}
/*     */     
/*     */     public Object get(char key) {
/*  55 */       return null;
/*     */     }
/*     */ 
/*     */     
/*     */     public Object put(char key, Object value) {
/*  60 */       throw new UnsupportedOperationException("put");
/*     */     }
/*     */ 
/*     */     
/*     */     public Object remove(char key) {
/*  65 */       return null;
/*     */     }
/*     */ 
/*     */     
/*     */     public int size() {
/*  70 */       return 0;
/*     */     }
/*     */ 
/*     */     
/*     */     public boolean isEmpty() {
/*  75 */       return true;
/*     */     }
/*     */ 
/*     */     
/*     */     public boolean containsKey(Object key) {
/*  80 */       return false;
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     public void clear() {}
/*     */ 
/*     */ 
/*     */     
/*     */     public Set<Character> keySet() {
/*  90 */       return Collections.emptySet();
/*     */     }
/*     */ 
/*     */     
/*     */     public boolean containsKey(char key) {
/*  95 */       return false;
/*     */     }
/*     */ 
/*     */     
/*     */     public boolean containsValue(Object value) {
/* 100 */       return false;
/*     */     }
/*     */ 
/*     */     
/*     */     public Iterable<CharObjectMap.PrimitiveEntry<Object>> entries() {
/* 105 */       return Collections.emptySet();
/*     */     }
/*     */ 
/*     */     
/*     */     public Object get(Object key) {
/* 110 */       return null;
/*     */     }
/*     */ 
/*     */     
/*     */     public Object put(Character key, Object value) {
/* 115 */       throw new UnsupportedOperationException();
/*     */     }
/*     */ 
/*     */     
/*     */     public Object remove(Object key) {
/* 120 */       return null;
/*     */     }
/*     */ 
/*     */     
/*     */     public void putAll(Map<? extends Character, ?> m) {
/* 125 */       throw new UnsupportedOperationException();
/*     */     }
/*     */ 
/*     */     
/*     */     public Collection<Object> values() {
/* 130 */       return Collections.emptyList();
/*     */     }
/*     */ 
/*     */     
/*     */     public Set<Map.Entry<Character, Object>> entrySet() {
/* 135 */       return Collections.emptySet();
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   private static final class UnmodifiableMap<V>
/*     */     implements CharObjectMap<V>
/*     */   {
/*     */     private final CharObjectMap<V> map;
/*     */     
/*     */     private Set<Character> keySet;
/*     */     
/*     */     private Set<Map.Entry<Character, V>> entrySet;
/*     */     private Collection<V> values;
/*     */     private Iterable<CharObjectMap.PrimitiveEntry<V>> entries;
/*     */     
/*     */     UnmodifiableMap(CharObjectMap<V> map) {
/* 152 */       this.map = map;
/*     */     }
/*     */ 
/*     */     
/*     */     public V get(char key) {
/* 157 */       return this.map.get(key);
/*     */     }
/*     */ 
/*     */     
/*     */     public V put(char key, V value) {
/* 162 */       throw new UnsupportedOperationException("put");
/*     */     }
/*     */ 
/*     */     
/*     */     public V remove(char key) {
/* 167 */       throw new UnsupportedOperationException("remove");
/*     */     }
/*     */ 
/*     */     
/*     */     public int size() {
/* 172 */       return this.map.size();
/*     */     }
/*     */ 
/*     */     
/*     */     public boolean isEmpty() {
/* 177 */       return this.map.isEmpty();
/*     */     }
/*     */ 
/*     */     
/*     */     public void clear() {
/* 182 */       throw new UnsupportedOperationException("clear");
/*     */     }
/*     */ 
/*     */     
/*     */     public boolean containsKey(char key) {
/* 187 */       return this.map.containsKey(key);
/*     */     }
/*     */ 
/*     */     
/*     */     public boolean containsValue(Object value) {
/* 192 */       return this.map.containsValue(value);
/*     */     }
/*     */ 
/*     */     
/*     */     public boolean containsKey(Object key) {
/* 197 */       return this.map.containsKey(key);
/*     */     }
/*     */ 
/*     */     
/*     */     public V get(Object key) {
/* 202 */       return this.map.get(key);
/*     */     }
/*     */ 
/*     */     
/*     */     public V put(Character key, V value) {
/* 207 */       throw new UnsupportedOperationException("put");
/*     */     }
/*     */ 
/*     */     
/*     */     public V remove(Object key) {
/* 212 */       throw new UnsupportedOperationException("remove");
/*     */     }
/*     */ 
/*     */     
/*     */     public void putAll(Map<? extends Character, ? extends V> m) {
/* 217 */       throw new UnsupportedOperationException("putAll");
/*     */     }
/*     */ 
/*     */     
/*     */     public Iterable<CharObjectMap.PrimitiveEntry<V>> entries() {
/* 222 */       if (this.entries == null) {
/* 223 */         this.entries = new Iterable<CharObjectMap.PrimitiveEntry<V>>()
/*     */           {
/*     */             public Iterator<CharObjectMap.PrimitiveEntry<V>> iterator() {
/* 226 */               return new CharCollections.UnmodifiableMap.IteratorImpl(CharCollections.UnmodifiableMap.this.map.entries().iterator());
/*     */             }
/*     */           };
/*     */       }
/*     */       
/* 231 */       return this.entries;
/*     */     }
/*     */ 
/*     */     
/*     */     public Set<Character> keySet() {
/* 236 */       if (this.keySet == null) {
/* 237 */         this.keySet = Collections.unmodifiableSet(this.map.keySet());
/*     */       }
/* 239 */       return this.keySet;
/*     */     }
/*     */ 
/*     */     
/*     */     public Set<Map.Entry<Character, V>> entrySet() {
/* 244 */       if (this.entrySet == null) {
/* 245 */         this.entrySet = Collections.unmodifiableSet(this.map.entrySet());
/*     */       }
/* 247 */       return this.entrySet;
/*     */     }
/*     */ 
/*     */     
/*     */     public Collection<V> values() {
/* 252 */       if (this.values == null) {
/* 253 */         this.values = Collections.unmodifiableCollection(this.map.values());
/*     */       }
/* 255 */       return this.values;
/*     */     }
/*     */ 
/*     */     
/*     */     private class IteratorImpl
/*     */       implements Iterator<CharObjectMap.PrimitiveEntry<V>>
/*     */     {
/*     */       final Iterator<CharObjectMap.PrimitiveEntry<V>> iter;
/*     */       
/*     */       IteratorImpl(Iterator<CharObjectMap.PrimitiveEntry<V>> iter) {
/* 265 */         this.iter = iter;
/*     */       }
/*     */ 
/*     */       
/*     */       public boolean hasNext() {
/* 270 */         return this.iter.hasNext();
/*     */       }
/*     */ 
/*     */       
/*     */       public CharObjectMap.PrimitiveEntry<V> next() {
/* 275 */         if (!hasNext()) {
/* 276 */           throw new NoSuchElementException();
/*     */         }
/* 278 */         return new CharCollections.UnmodifiableMap.EntryImpl(this.iter.next());
/*     */       }
/*     */ 
/*     */       
/*     */       public void remove() {
/* 283 */         throw new UnsupportedOperationException("remove");
/*     */       }
/*     */     }
/*     */ 
/*     */     
/*     */     private class EntryImpl
/*     */       implements CharObjectMap.PrimitiveEntry<V>
/*     */     {
/*     */       private final CharObjectMap.PrimitiveEntry<V> entry;
/*     */       
/*     */       EntryImpl(CharObjectMap.PrimitiveEntry<V> entry) {
/* 294 */         this.entry = entry;
/*     */       }
/*     */ 
/*     */       
/*     */       public char key() {
/* 299 */         return this.entry.key();
/*     */       }
/*     */ 
/*     */       
/*     */       public V value() {
/* 304 */         return this.entry.value();
/*     */       }
/*     */ 
/*     */       
/*     */       public void setValue(V value) {
/* 309 */         throw new UnsupportedOperationException("setValue");
/*     */       }
/*     */     }
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\io\nett\\util\collection\CharCollections.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */