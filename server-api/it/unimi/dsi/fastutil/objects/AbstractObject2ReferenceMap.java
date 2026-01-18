/*     */ package it.unimi.dsi.fastutil.objects;
/*     */ 
/*     */ import it.unimi.dsi.fastutil.Size64;
/*     */ import java.io.Serializable;
/*     */ import java.util.Collection;
/*     */ import java.util.Iterator;
/*     */ import java.util.Map;
/*     */ import java.util.Objects;
/*     */ import java.util.Set;
/*     */ import java.util.Spliterator;
/*     */ import java.util.function.Consumer;
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
/*     */ public abstract class AbstractObject2ReferenceMap<K, V>
/*     */   extends AbstractObject2ReferenceFunction<K, V>
/*     */   implements Object2ReferenceMap<K, V>, Serializable
/*     */ {
/*     */   private static final long serialVersionUID = -4940583368468432370L;
/*     */   
/*     */   public boolean containsKey(Object k) {
/*  57 */     ObjectIterator<Object2ReferenceMap.Entry<K, V>> i = object2ReferenceEntrySet().iterator();
/*  58 */     while (i.hasNext()) { if (((Object2ReferenceMap.Entry)i.next()).getKey() == k) return true;  }
/*  59 */      return false;
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
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean containsValue(Object v) {
/*  77 */     ObjectIterator<Object2ReferenceMap.Entry<K, V>> i = object2ReferenceEntrySet().iterator();
/*  78 */     while (i.hasNext()) { if (((Object2ReferenceMap.Entry)i.next()).getValue() == v) return true;  }
/*  79 */      return false;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isEmpty() {
/*  84 */     return (size() == 0);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public static class BasicEntry<K, V>
/*     */     implements Object2ReferenceMap.Entry<K, V>
/*     */   {
/*     */     protected K key;
/*     */ 
/*     */     
/*     */     protected V value;
/*     */ 
/*     */ 
/*     */     
/*     */     public BasicEntry() {}
/*     */ 
/*     */     
/*     */     public BasicEntry(K key, V value) {
/* 103 */       this.key = key;
/* 104 */       this.value = value;
/*     */     }
/*     */ 
/*     */     
/*     */     public K getKey() {
/* 109 */       return this.key;
/*     */     }
/*     */ 
/*     */     
/*     */     public V getValue() {
/* 114 */       return this.value;
/*     */     }
/*     */ 
/*     */     
/*     */     public V setValue(V value) {
/* 119 */       throw new UnsupportedOperationException();
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     public boolean equals(Object o) {
/* 125 */       if (!(o instanceof Map.Entry)) return false; 
/* 126 */       if (o instanceof Object2ReferenceMap.Entry) {
/* 127 */         Object2ReferenceMap.Entry<K, V> entry = (Object2ReferenceMap.Entry<K, V>)o;
/* 128 */         return (Objects.equals(this.key, entry.getKey()) && this.value == entry.getValue());
/*     */       } 
/* 130 */       Map.Entry<?, ?> e = (Map.Entry<?, ?>)o;
/* 131 */       Object key = e.getKey();
/* 132 */       Object value = e.getValue();
/* 133 */       return (Objects.equals(this.key, key) && this.value == value);
/*     */     }
/*     */ 
/*     */     
/*     */     public int hashCode() {
/* 138 */       return ((this.key == null) ? 0 : this.key.hashCode()) ^ ((this.value == null) ? 0 : System.identityHashCode(this.value));
/*     */     }
/*     */ 
/*     */     
/*     */     public String toString() {
/* 143 */       return (new StringBuilder()).append(this.key).append("->").append(this.value).toString();
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   public static abstract class BasicEntrySet<K, V>
/*     */     extends AbstractObjectSet<Object2ReferenceMap.Entry<K, V>>
/*     */   {
/*     */     protected final Object2ReferenceMap<K, V> map;
/*     */ 
/*     */     
/*     */     public BasicEntrySet(Object2ReferenceMap<K, V> map) {
/* 155 */       this.map = map;
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     public boolean contains(Object o) {
/* 161 */       if (!(o instanceof Map.Entry)) return false; 
/* 162 */       if (o instanceof Object2ReferenceMap.Entry) {
/* 163 */         Object2ReferenceMap.Entry<K, V> entry = (Object2ReferenceMap.Entry<K, V>)o;
/* 164 */         K k1 = entry.getKey();
/* 165 */         return (this.map.containsKey(k1) && this.map.get(k1) == entry.getValue());
/*     */       } 
/* 167 */       Map.Entry<?, ?> e = (Map.Entry<?, ?>)o;
/* 168 */       Object k = e.getKey();
/* 169 */       Object value = e.getValue();
/* 170 */       return (this.map.containsKey(k) && this.map.get(k) == value);
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     public boolean remove(Object o) {
/* 176 */       if (!(o instanceof Map.Entry)) return false; 
/* 177 */       if (o instanceof Object2ReferenceMap.Entry) {
/* 178 */         Object2ReferenceMap.Entry<K, V> entry = (Object2ReferenceMap.Entry<K, V>)o;
/* 179 */         return this.map.remove(entry.getKey(), entry.getValue());
/*     */       } 
/* 181 */       Map.Entry<?, ?> e = (Map.Entry<?, ?>)o;
/* 182 */       Object k = e.getKey();
/* 183 */       Object v = e.getValue();
/* 184 */       return this.map.remove(k, v);
/*     */     }
/*     */ 
/*     */     
/*     */     public int size() {
/* 189 */       return this.map.size();
/*     */     }
/*     */ 
/*     */     
/*     */     public ObjectSpliterator<Object2ReferenceMap.Entry<K, V>> spliterator() {
/* 194 */       return ObjectSpliterators.asSpliterator(iterator(), Size64.sizeOf(this.map), 65);
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
/*     */ 
/*     */   
/*     */   public ObjectSet<K> keySet() {
/* 212 */     return new AbstractObjectSet<K>()
/*     */       {
/*     */         public boolean contains(Object k) {
/* 215 */           return AbstractObject2ReferenceMap.this.containsKey(k);
/*     */         }
/*     */ 
/*     */         
/*     */         public int size() {
/* 220 */           return AbstractObject2ReferenceMap.this.size();
/*     */         }
/*     */ 
/*     */         
/*     */         public void clear() {
/* 225 */           AbstractObject2ReferenceMap.this.clear();
/*     */         }
/*     */ 
/*     */         
/*     */         public ObjectIterator<K> iterator() {
/* 230 */           return new ObjectIterator<K>() {
/* 231 */               private final ObjectIterator<Object2ReferenceMap.Entry<K, V>> i = Object2ReferenceMaps.fastIterator(AbstractObject2ReferenceMap.this);
/*     */ 
/*     */               
/*     */               public K next() {
/* 235 */                 return (K)((Object2ReferenceMap.Entry)this.i.next()).getKey();
/*     */               }
/*     */ 
/*     */               
/*     */               public boolean hasNext() {
/* 240 */                 return this.i.hasNext();
/*     */               }
/*     */ 
/*     */               
/*     */               public void remove() {
/* 245 */                 this.i.remove();
/*     */               }
/*     */ 
/*     */               
/*     */               public void forEachRemaining(Consumer<? super K> action) {
/* 250 */                 this.i.forEachRemaining(entry -> action.accept(entry.getKey()));
/*     */               }
/*     */             };
/*     */         }
/*     */ 
/*     */         
/*     */         public ObjectSpliterator<K> spliterator() {
/* 257 */           return ObjectSpliterators.asSpliterator(iterator(), Size64.sizeOf(AbstractObject2ReferenceMap.this), 65);
/*     */         }
/*     */       };
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
/*     */ 
/*     */   
/*     */   public ReferenceCollection<V> values() {
/* 276 */     return new AbstractReferenceCollection<V>()
/*     */       {
/*     */         public boolean contains(Object k) {
/* 279 */           return AbstractObject2ReferenceMap.this.containsValue(k);
/*     */         }
/*     */ 
/*     */         
/*     */         public int size() {
/* 284 */           return AbstractObject2ReferenceMap.this.size();
/*     */         }
/*     */ 
/*     */         
/*     */         public void clear() {
/* 289 */           AbstractObject2ReferenceMap.this.clear();
/*     */         }
/*     */ 
/*     */         
/*     */         public ObjectIterator<V> iterator() {
/* 294 */           return new ObjectIterator<V>() {
/* 295 */               private final ObjectIterator<Object2ReferenceMap.Entry<K, V>> i = Object2ReferenceMaps.fastIterator(AbstractObject2ReferenceMap.this);
/*     */ 
/*     */               
/*     */               public V next() {
/* 299 */                 return (V)((Object2ReferenceMap.Entry)this.i.next()).getValue();
/*     */               }
/*     */ 
/*     */               
/*     */               public boolean hasNext() {
/* 304 */                 return this.i.hasNext();
/*     */               }
/*     */ 
/*     */               
/*     */               public void remove() {
/* 309 */                 this.i.remove();
/*     */               }
/*     */ 
/*     */               
/*     */               public void forEachRemaining(Consumer<? super V> action) {
/* 314 */                 this.i.forEachRemaining(entry -> action.accept(entry.getValue()));
/*     */               }
/*     */             };
/*     */         }
/*     */ 
/*     */         
/*     */         public ObjectSpliterator<V> spliterator() {
/* 321 */           return ObjectSpliterators.asSpliterator(iterator(), Size64.sizeOf(AbstractObject2ReferenceMap.this), 64);
/*     */         }
/*     */       };
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void putAll(Map<? extends K, ? extends V> m) {
/* 330 */     if (m instanceof Object2ReferenceMap) {
/* 331 */       ObjectIterator<Object2ReferenceMap.Entry<K, V>> i = Object2ReferenceMaps.fastIterator((Object2ReferenceMap)m);
/* 332 */       while (i.hasNext()) {
/* 333 */         Object2ReferenceMap.Entry<? extends K, ? extends V> e = i.next();
/* 334 */         put(e.getKey(), e.getValue());
/*     */       } 
/*     */     } else {
/* 337 */       int n = m.size();
/* 338 */       Iterator<? extends Map.Entry<? extends K, ? extends V>> i = m.entrySet().iterator();
/*     */       
/* 340 */       while (n-- != 0) {
/* 341 */         Map.Entry<? extends K, ? extends V> e = i.next();
/* 342 */         put(e.getKey(), e.getValue());
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
/*     */   
/*     */   public int hashCode() {
/* 356 */     int h = 0, n = size();
/* 357 */     ObjectIterator<Object2ReferenceMap.Entry<K, V>> i = Object2ReferenceMaps.fastIterator(this);
/* 358 */     for (; n-- != 0; h += ((Object2ReferenceMap.Entry)i.next()).hashCode());
/* 359 */     return h;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean equals(Object o) {
/* 364 */     if (o == this) return true; 
/* 365 */     if (!(o instanceof Map)) return false; 
/* 366 */     Map<?, ?> m = (Map<?, ?>)o;
/* 367 */     if (m.size() != size()) return false; 
/* 368 */     return object2ReferenceEntrySet().containsAll(m.entrySet());
/*     */   }
/*     */ 
/*     */   
/*     */   public String toString() {
/* 373 */     StringBuilder s = new StringBuilder();
/* 374 */     ObjectIterator<Object2ReferenceMap.Entry<K, V>> i = Object2ReferenceMaps.fastIterator(this);
/* 375 */     int n = size();
/*     */     
/* 377 */     boolean first = true;
/* 378 */     s.append("{");
/* 379 */     while (n-- != 0) {
/* 380 */       if (first) { first = false; }
/* 381 */       else { s.append(", "); }
/* 382 */        Object2ReferenceMap.Entry<K, V> e = i.next();
/* 383 */       if (this == e.getKey()) { s.append("(this map)"); }
/* 384 */       else { s.append(String.valueOf(e.getKey())); }
/* 385 */        s.append("=>");
/* 386 */       if (this == e.getValue()) { s.append("(this map)"); continue; }
/* 387 */        s.append(String.valueOf(e.getValue()));
/*     */     } 
/* 389 */     s.append("}");
/* 390 */     return s.toString();
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\i\\unimi\dsi\fastutil\objects\AbstractObject2ReferenceMap.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */