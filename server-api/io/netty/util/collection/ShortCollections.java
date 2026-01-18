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
/*     */ public final class ShortCollections
/*     */ {
/*  29 */   private static final ShortObjectMap<Object> EMPTY_MAP = new EmptyMap();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static <V> ShortObjectMap<V> emptyMap() {
/*  39 */     return (ShortObjectMap)EMPTY_MAP;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static <V> ShortObjectMap<V> unmodifiableMap(ShortObjectMap<V> map) {
/*  46 */     return new UnmodifiableMap<>(map);
/*     */   }
/*     */   
/*     */   private static final class EmptyMap
/*     */     implements ShortObjectMap<Object>
/*     */   {
/*     */     private EmptyMap() {}
/*     */     
/*     */     public Object get(short key) {
/*  55 */       return null;
/*     */     }
/*     */ 
/*     */     
/*     */     public Object put(short key, Object value) {
/*  60 */       throw new UnsupportedOperationException("put");
/*     */     }
/*     */ 
/*     */     
/*     */     public Object remove(short key) {
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
/*     */     public Set<Short> keySet() {
/*  90 */       return Collections.emptySet();
/*     */     }
/*     */ 
/*     */     
/*     */     public boolean containsKey(short key) {
/*  95 */       return false;
/*     */     }
/*     */ 
/*     */     
/*     */     public boolean containsValue(Object value) {
/* 100 */       return false;
/*     */     }
/*     */ 
/*     */     
/*     */     public Iterable<ShortObjectMap.PrimitiveEntry<Object>> entries() {
/* 105 */       return Collections.emptySet();
/*     */     }
/*     */ 
/*     */     
/*     */     public Object get(Object key) {
/* 110 */       return null;
/*     */     }
/*     */ 
/*     */     
/*     */     public Object put(Short key, Object value) {
/* 115 */       throw new UnsupportedOperationException();
/*     */     }
/*     */ 
/*     */     
/*     */     public Object remove(Object key) {
/* 120 */       return null;
/*     */     }
/*     */ 
/*     */     
/*     */     public void putAll(Map<? extends Short, ?> m) {
/* 125 */       throw new UnsupportedOperationException();
/*     */     }
/*     */ 
/*     */     
/*     */     public Collection<Object> values() {
/* 130 */       return Collections.emptyList();
/*     */     }
/*     */ 
/*     */     
/*     */     public Set<Map.Entry<Short, Object>> entrySet() {
/* 135 */       return Collections.emptySet();
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   private static final class UnmodifiableMap<V>
/*     */     implements ShortObjectMap<V>
/*     */   {
/*     */     private final ShortObjectMap<V> map;
/*     */     
/*     */     private Set<Short> keySet;
/*     */     
/*     */     private Set<Map.Entry<Short, V>> entrySet;
/*     */     private Collection<V> values;
/*     */     private Iterable<ShortObjectMap.PrimitiveEntry<V>> entries;
/*     */     
/*     */     UnmodifiableMap(ShortObjectMap<V> map) {
/* 152 */       this.map = map;
/*     */     }
/*     */ 
/*     */     
/*     */     public V get(short key) {
/* 157 */       return this.map.get(key);
/*     */     }
/*     */ 
/*     */     
/*     */     public V put(short key, V value) {
/* 162 */       throw new UnsupportedOperationException("put");
/*     */     }
/*     */ 
/*     */     
/*     */     public V remove(short key) {
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
/*     */     public boolean containsKey(short key) {
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
/*     */     public V put(Short key, V value) {
/* 207 */       throw new UnsupportedOperationException("put");
/*     */     }
/*     */ 
/*     */     
/*     */     public V remove(Object key) {
/* 212 */       throw new UnsupportedOperationException("remove");
/*     */     }
/*     */ 
/*     */     
/*     */     public void putAll(Map<? extends Short, ? extends V> m) {
/* 217 */       throw new UnsupportedOperationException("putAll");
/*     */     }
/*     */ 
/*     */     
/*     */     public Iterable<ShortObjectMap.PrimitiveEntry<V>> entries() {
/* 222 */       if (this.entries == null) {
/* 223 */         this.entries = new Iterable<ShortObjectMap.PrimitiveEntry<V>>()
/*     */           {
/*     */             public Iterator<ShortObjectMap.PrimitiveEntry<V>> iterator() {
/* 226 */               return new ShortCollections.UnmodifiableMap.IteratorImpl(ShortCollections.UnmodifiableMap.this.map.entries().iterator());
/*     */             }
/*     */           };
/*     */       }
/*     */       
/* 231 */       return this.entries;
/*     */     }
/*     */ 
/*     */     
/*     */     public Set<Short> keySet() {
/* 236 */       if (this.keySet == null) {
/* 237 */         this.keySet = Collections.unmodifiableSet(this.map.keySet());
/*     */       }
/* 239 */       return this.keySet;
/*     */     }
/*     */ 
/*     */     
/*     */     public Set<Map.Entry<Short, V>> entrySet() {
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
/*     */       implements Iterator<ShortObjectMap.PrimitiveEntry<V>>
/*     */     {
/*     */       final Iterator<ShortObjectMap.PrimitiveEntry<V>> iter;
/*     */       
/*     */       IteratorImpl(Iterator<ShortObjectMap.PrimitiveEntry<V>> iter) {
/* 265 */         this.iter = iter;
/*     */       }
/*     */ 
/*     */       
/*     */       public boolean hasNext() {
/* 270 */         return this.iter.hasNext();
/*     */       }
/*     */ 
/*     */       
/*     */       public ShortObjectMap.PrimitiveEntry<V> next() {
/* 275 */         if (!hasNext()) {
/* 276 */           throw new NoSuchElementException();
/*     */         }
/* 278 */         return new ShortCollections.UnmodifiableMap.EntryImpl(this.iter.next());
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
/*     */       implements ShortObjectMap.PrimitiveEntry<V>
/*     */     {
/*     */       private final ShortObjectMap.PrimitiveEntry<V> entry;
/*     */       
/*     */       EntryImpl(ShortObjectMap.PrimitiveEntry<V> entry) {
/* 294 */         this.entry = entry;
/*     */       }
/*     */ 
/*     */       
/*     */       public short key() {
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


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\io\nett\\util\collection\ShortCollections.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */