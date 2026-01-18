/*     */ package it.unimi.dsi.fastutil.objects;
/*     */ 
/*     */ import it.unimi.dsi.fastutil.Size64;
/*     */ import it.unimi.dsi.fastutil.bytes.AbstractByteCollection;
/*     */ import it.unimi.dsi.fastutil.bytes.ByteCollection;
/*     */ import it.unimi.dsi.fastutil.bytes.ByteConsumer;
/*     */ import it.unimi.dsi.fastutil.bytes.ByteIterator;
/*     */ import it.unimi.dsi.fastutil.bytes.ByteSpliterator;
/*     */ import it.unimi.dsi.fastutil.bytes.ByteSpliterators;
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
/*     */ public abstract class AbstractObject2ByteMap<K>
/*     */   extends AbstractObject2ByteFunction<K>
/*     */   implements Object2ByteMap<K>, Serializable
/*     */ {
/*     */   private static final long serialVersionUID = -4940583368468432370L;
/*     */   
/*     */   public boolean containsKey(Object k) {
/*  63 */     ObjectIterator<Object2ByteMap.Entry<K>> i = object2ByteEntrySet().iterator();
/*  64 */     while (i.hasNext()) { if (((Object2ByteMap.Entry)i.next()).getKey() == k) return true;  }
/*  65 */      return false;
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
/*     */   public boolean containsValue(byte v) {
/*  83 */     ObjectIterator<Object2ByteMap.Entry<K>> i = object2ByteEntrySet().iterator();
/*  84 */     while (i.hasNext()) { if (((Object2ByteMap.Entry)i.next()).getByteValue() == v) return true;  }
/*  85 */      return false;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isEmpty() {
/*  90 */     return (size() == 0);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public static class BasicEntry<K>
/*     */     implements Object2ByteMap.Entry<K>
/*     */   {
/*     */     protected K key;
/*     */ 
/*     */     
/*     */     protected byte value;
/*     */ 
/*     */ 
/*     */     
/*     */     public BasicEntry() {}
/*     */ 
/*     */     
/*     */     public BasicEntry(K key, Byte value) {
/* 109 */       this.key = key;
/* 110 */       this.value = value.byteValue();
/*     */     }
/*     */     
/*     */     public BasicEntry(K key, byte value) {
/* 114 */       this.key = key;
/* 115 */       this.value = value;
/*     */     }
/*     */ 
/*     */     
/*     */     public K getKey() {
/* 120 */       return this.key;
/*     */     }
/*     */ 
/*     */     
/*     */     public byte getByteValue() {
/* 125 */       return this.value;
/*     */     }
/*     */ 
/*     */     
/*     */     public byte setValue(byte value) {
/* 130 */       throw new UnsupportedOperationException();
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     public boolean equals(Object o) {
/* 136 */       if (!(o instanceof Map.Entry)) return false; 
/* 137 */       if (o instanceof Object2ByteMap.Entry) {
/* 138 */         Object2ByteMap.Entry<K> entry = (Object2ByteMap.Entry<K>)o;
/* 139 */         return (Objects.equals(this.key, entry.getKey()) && this.value == entry.getByteValue());
/*     */       } 
/* 141 */       Map.Entry<?, ?> e = (Map.Entry<?, ?>)o;
/* 142 */       Object key = e.getKey();
/* 143 */       Object value = e.getValue();
/* 144 */       if (value == null || !(value instanceof Byte)) return false; 
/* 145 */       return (Objects.equals(this.key, key) && this.value == ((Byte)value).byteValue());
/*     */     }
/*     */ 
/*     */     
/*     */     public int hashCode() {
/* 150 */       return ((this.key == null) ? 0 : this.key.hashCode()) ^ this.value;
/*     */     }
/*     */ 
/*     */     
/*     */     public String toString() {
/* 155 */       return (new StringBuilder()).append(this.key).append("->").append(this.value).toString();
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   public static abstract class BasicEntrySet<K>
/*     */     extends AbstractObjectSet<Object2ByteMap.Entry<K>>
/*     */   {
/*     */     protected final Object2ByteMap<K> map;
/*     */ 
/*     */     
/*     */     public BasicEntrySet(Object2ByteMap<K> map) {
/* 167 */       this.map = map;
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     public boolean contains(Object o) {
/* 173 */       if (!(o instanceof Map.Entry)) return false; 
/* 174 */       if (o instanceof Object2ByteMap.Entry) {
/* 175 */         Object2ByteMap.Entry<K> entry = (Object2ByteMap.Entry<K>)o;
/* 176 */         K k1 = entry.getKey();
/* 177 */         return (this.map.containsKey(k1) && this.map.getByte(k1) == entry.getByteValue());
/*     */       } 
/* 179 */       Map.Entry<?, ?> e = (Map.Entry<?, ?>)o;
/* 180 */       Object k = e.getKey();
/* 181 */       Object value = e.getValue();
/* 182 */       if (value == null || !(value instanceof Byte)) return false; 
/* 183 */       return (this.map.containsKey(k) && this.map.getByte(k) == ((Byte)value).byteValue());
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     public boolean remove(Object o) {
/* 189 */       if (!(o instanceof Map.Entry)) return false; 
/* 190 */       if (o instanceof Object2ByteMap.Entry) {
/* 191 */         Object2ByteMap.Entry<K> entry = (Object2ByteMap.Entry<K>)o;
/* 192 */         return this.map.remove(entry.getKey(), entry.getByteValue());
/*     */       } 
/* 194 */       Map.Entry<?, ?> e = (Map.Entry<?, ?>)o;
/* 195 */       Object k = e.getKey();
/* 196 */       Object value = e.getValue();
/* 197 */       if (value == null || !(value instanceof Byte)) return false; 
/* 198 */       byte v = ((Byte)value).byteValue();
/* 199 */       return this.map.remove(k, v);
/*     */     }
/*     */ 
/*     */     
/*     */     public int size() {
/* 204 */       return this.map.size();
/*     */     }
/*     */ 
/*     */     
/*     */     public ObjectSpliterator<Object2ByteMap.Entry<K>> spliterator() {
/* 209 */       return ObjectSpliterators.asSpliterator(iterator(), Size64.sizeOf(this.map), 65);
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
/* 227 */     return new AbstractObjectSet<K>()
/*     */       {
/*     */         public boolean contains(Object k) {
/* 230 */           return AbstractObject2ByteMap.this.containsKey(k);
/*     */         }
/*     */ 
/*     */         
/*     */         public int size() {
/* 235 */           return AbstractObject2ByteMap.this.size();
/*     */         }
/*     */ 
/*     */         
/*     */         public void clear() {
/* 240 */           AbstractObject2ByteMap.this.clear();
/*     */         }
/*     */ 
/*     */         
/*     */         public ObjectIterator<K> iterator() {
/* 245 */           return new ObjectIterator<K>() {
/* 246 */               private final ObjectIterator<Object2ByteMap.Entry<K>> i = Object2ByteMaps.fastIterator(AbstractObject2ByteMap.this);
/*     */ 
/*     */               
/*     */               public K next() {
/* 250 */                 return ((Object2ByteMap.Entry<K>)this.i.next()).getKey();
/*     */               }
/*     */ 
/*     */               
/*     */               public boolean hasNext() {
/* 255 */                 return this.i.hasNext();
/*     */               }
/*     */ 
/*     */               
/*     */               public void remove() {
/* 260 */                 this.i.remove();
/*     */               }
/*     */ 
/*     */               
/*     */               public void forEachRemaining(Consumer<? super K> action) {
/* 265 */                 this.i.forEachRemaining(entry -> action.accept(entry.getKey()));
/*     */               }
/*     */             };
/*     */         }
/*     */ 
/*     */         
/*     */         public ObjectSpliterator<K> spliterator() {
/* 272 */           return ObjectSpliterators.asSpliterator(iterator(), Size64.sizeOf(AbstractObject2ByteMap.this), 65);
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
/*     */   public ByteCollection values() {
/* 291 */     return (ByteCollection)new AbstractByteCollection()
/*     */       {
/*     */         public boolean contains(byte k) {
/* 294 */           return AbstractObject2ByteMap.this.containsValue(k);
/*     */         }
/*     */ 
/*     */         
/*     */         public int size() {
/* 299 */           return AbstractObject2ByteMap.this.size();
/*     */         }
/*     */ 
/*     */         
/*     */         public void clear() {
/* 304 */           AbstractObject2ByteMap.this.clear();
/*     */         }
/*     */ 
/*     */         
/*     */         public ByteIterator iterator() {
/* 309 */           return new ByteIterator() {
/* 310 */               private final ObjectIterator<Object2ByteMap.Entry<K>> i = Object2ByteMaps.fastIterator(AbstractObject2ByteMap.this);
/*     */ 
/*     */               
/*     */               public byte nextByte() {
/* 314 */                 return ((Object2ByteMap.Entry)this.i.next()).getByteValue();
/*     */               }
/*     */ 
/*     */               
/*     */               public boolean hasNext() {
/* 319 */                 return this.i.hasNext();
/*     */               }
/*     */ 
/*     */               
/*     */               public void remove() {
/* 324 */                 this.i.remove();
/*     */               }
/*     */ 
/*     */               
/*     */               public void forEachRemaining(ByteConsumer action) {
/* 329 */                 this.i.forEachRemaining(entry -> action.accept(entry.getByteValue()));
/*     */               }
/*     */             };
/*     */         }
/*     */ 
/*     */         
/*     */         public ByteSpliterator spliterator() {
/* 336 */           return ByteSpliterators.asSpliterator(iterator(), Size64.sizeOf(AbstractObject2ByteMap.this), 320);
/*     */         }
/*     */       };
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void putAll(Map<? extends K, ? extends Byte> m) {
/* 345 */     if (m instanceof Object2ByteMap) {
/* 346 */       ObjectIterator<Object2ByteMap.Entry<K>> i = Object2ByteMaps.fastIterator((Object2ByteMap)m);
/* 347 */       while (i.hasNext()) {
/* 348 */         Object2ByteMap.Entry<? extends K> e = i.next();
/* 349 */         put(e.getKey(), e.getByteValue());
/*     */       } 
/*     */     } else {
/* 352 */       int n = m.size();
/* 353 */       Iterator<? extends Map.Entry<? extends K, ? extends Byte>> i = m.entrySet().iterator();
/*     */       
/* 355 */       while (n-- != 0) {
/* 356 */         Map.Entry<? extends K, ? extends Byte> e = i.next();
/* 357 */         put(e.getKey(), e.getValue());
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
/* 371 */     int h = 0, n = size();
/* 372 */     ObjectIterator<Object2ByteMap.Entry<K>> i = Object2ByteMaps.fastIterator(this);
/* 373 */     for (; n-- != 0; h += ((Object2ByteMap.Entry)i.next()).hashCode());
/* 374 */     return h;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean equals(Object o) {
/* 379 */     if (o == this) return true; 
/* 380 */     if (!(o instanceof Map)) return false; 
/* 381 */     Map<?, ?> m = (Map<?, ?>)o;
/* 382 */     if (m.size() != size()) return false; 
/* 383 */     return object2ByteEntrySet().containsAll(m.entrySet());
/*     */   }
/*     */ 
/*     */   
/*     */   public String toString() {
/* 388 */     StringBuilder s = new StringBuilder();
/* 389 */     ObjectIterator<Object2ByteMap.Entry<K>> i = Object2ByteMaps.fastIterator(this);
/* 390 */     int n = size();
/*     */     
/* 392 */     boolean first = true;
/* 393 */     s.append("{");
/* 394 */     while (n-- != 0) {
/* 395 */       if (first) { first = false; }
/* 396 */       else { s.append(", "); }
/* 397 */        Object2ByteMap.Entry<K> e = i.next();
/* 398 */       if (this == e.getKey()) { s.append("(this map)"); }
/* 399 */       else { s.append(String.valueOf(e.getKey())); }
/* 400 */        s.append("=>");
/* 401 */       s.append(String.valueOf(e.getByteValue()));
/*     */     } 
/* 403 */     s.append("}");
/* 404 */     return s.toString();
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\i\\unimi\dsi\fastutil\objects\AbstractObject2ByteMap.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */