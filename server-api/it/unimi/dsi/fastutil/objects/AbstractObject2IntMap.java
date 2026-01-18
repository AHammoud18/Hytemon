/*     */ package it.unimi.dsi.fastutil.objects;
/*     */ 
/*     */ import it.unimi.dsi.fastutil.Size64;
/*     */ import it.unimi.dsi.fastutil.ints.AbstractIntCollection;
/*     */ import it.unimi.dsi.fastutil.ints.IntBinaryOperator;
/*     */ import it.unimi.dsi.fastutil.ints.IntCollection;
/*     */ import it.unimi.dsi.fastutil.ints.IntIterator;
/*     */ import it.unimi.dsi.fastutil.ints.IntSpliterator;
/*     */ import it.unimi.dsi.fastutil.ints.IntSpliterators;
/*     */ import java.io.Serializable;
/*     */ import java.util.Collection;
/*     */ import java.util.Iterator;
/*     */ import java.util.Map;
/*     */ import java.util.Objects;
/*     */ import java.util.Set;
/*     */ import java.util.Spliterator;
/*     */ import java.util.function.Consumer;
/*     */ import java.util.function.IntBinaryOperator;
/*     */ import java.util.function.IntConsumer;
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
/*     */ public abstract class AbstractObject2IntMap<K>
/*     */   extends AbstractObject2IntFunction<K>
/*     */   implements Object2IntMap<K>, Serializable
/*     */ {
/*     */   private static final long serialVersionUID = -4940583368468432370L;
/*     */   
/*     */   public boolean containsKey(Object k) {
/*  62 */     ObjectIterator<Object2IntMap.Entry<K>> i = object2IntEntrySet().iterator();
/*  63 */     while (i.hasNext()) { if (((Object2IntMap.Entry)i.next()).getKey() == k) return true;  }
/*  64 */      return false;
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
/*     */   public boolean containsValue(int v) {
/*  82 */     ObjectIterator<Object2IntMap.Entry<K>> i = object2IntEntrySet().iterator();
/*  83 */     while (i.hasNext()) { if (((Object2IntMap.Entry)i.next()).getIntValue() == v) return true;  }
/*  84 */      return false;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isEmpty() {
/*  89 */     return (size() == 0);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public final int mergeInt(K key, int value, IntBinaryOperator remappingFunction) {
/* 100 */     return mergeInt(key, value, (IntBinaryOperator)remappingFunction);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public static class BasicEntry<K>
/*     */     implements Object2IntMap.Entry<K>
/*     */   {
/*     */     protected K key;
/*     */ 
/*     */     
/*     */     protected int value;
/*     */ 
/*     */ 
/*     */     
/*     */     public BasicEntry() {}
/*     */ 
/*     */     
/*     */     public BasicEntry(K key, Integer value) {
/* 119 */       this.key = key;
/* 120 */       this.value = value.intValue();
/*     */     }
/*     */     
/*     */     public BasicEntry(K key, int value) {
/* 124 */       this.key = key;
/* 125 */       this.value = value;
/*     */     }
/*     */ 
/*     */     
/*     */     public K getKey() {
/* 130 */       return this.key;
/*     */     }
/*     */ 
/*     */     
/*     */     public int getIntValue() {
/* 135 */       return this.value;
/*     */     }
/*     */ 
/*     */     
/*     */     public int setValue(int value) {
/* 140 */       throw new UnsupportedOperationException();
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     public boolean equals(Object o) {
/* 146 */       if (!(o instanceof Map.Entry)) return false; 
/* 147 */       if (o instanceof Object2IntMap.Entry) {
/* 148 */         Object2IntMap.Entry<K> entry = (Object2IntMap.Entry<K>)o;
/* 149 */         return (Objects.equals(this.key, entry.getKey()) && this.value == entry.getIntValue());
/*     */       } 
/* 151 */       Map.Entry<?, ?> e = (Map.Entry<?, ?>)o;
/* 152 */       Object key = e.getKey();
/* 153 */       Object value = e.getValue();
/* 154 */       if (value == null || !(value instanceof Integer)) return false; 
/* 155 */       return (Objects.equals(this.key, key) && this.value == ((Integer)value).intValue());
/*     */     }
/*     */ 
/*     */     
/*     */     public int hashCode() {
/* 160 */       return ((this.key == null) ? 0 : this.key.hashCode()) ^ this.value;
/*     */     }
/*     */ 
/*     */     
/*     */     public String toString() {
/* 165 */       return (new StringBuilder()).append(this.key).append("->").append(this.value).toString();
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   public static abstract class BasicEntrySet<K>
/*     */     extends AbstractObjectSet<Object2IntMap.Entry<K>>
/*     */   {
/*     */     protected final Object2IntMap<K> map;
/*     */ 
/*     */     
/*     */     public BasicEntrySet(Object2IntMap<K> map) {
/* 177 */       this.map = map;
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     public boolean contains(Object o) {
/* 183 */       if (!(o instanceof Map.Entry)) return false; 
/* 184 */       if (o instanceof Object2IntMap.Entry) {
/* 185 */         Object2IntMap.Entry<K> entry = (Object2IntMap.Entry<K>)o;
/* 186 */         K k1 = entry.getKey();
/* 187 */         return (this.map.containsKey(k1) && this.map.getInt(k1) == entry.getIntValue());
/*     */       } 
/* 189 */       Map.Entry<?, ?> e = (Map.Entry<?, ?>)o;
/* 190 */       Object k = e.getKey();
/* 191 */       Object value = e.getValue();
/* 192 */       if (value == null || !(value instanceof Integer)) return false; 
/* 193 */       return (this.map.containsKey(k) && this.map.getInt(k) == ((Integer)value).intValue());
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     public boolean remove(Object o) {
/* 199 */       if (!(o instanceof Map.Entry)) return false; 
/* 200 */       if (o instanceof Object2IntMap.Entry) {
/* 201 */         Object2IntMap.Entry<K> entry = (Object2IntMap.Entry<K>)o;
/* 202 */         return this.map.remove(entry.getKey(), entry.getIntValue());
/*     */       } 
/* 204 */       Map.Entry<?, ?> e = (Map.Entry<?, ?>)o;
/* 205 */       Object k = e.getKey();
/* 206 */       Object value = e.getValue();
/* 207 */       if (value == null || !(value instanceof Integer)) return false; 
/* 208 */       int v = ((Integer)value).intValue();
/* 209 */       return this.map.remove(k, v);
/*     */     }
/*     */ 
/*     */     
/*     */     public int size() {
/* 214 */       return this.map.size();
/*     */     }
/*     */ 
/*     */     
/*     */     public ObjectSpliterator<Object2IntMap.Entry<K>> spliterator() {
/* 219 */       return ObjectSpliterators.asSpliterator(iterator(), Size64.sizeOf(this.map), 65);
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
/* 237 */     return new AbstractObjectSet<K>()
/*     */       {
/*     */         public boolean contains(Object k) {
/* 240 */           return AbstractObject2IntMap.this.containsKey(k);
/*     */         }
/*     */ 
/*     */         
/*     */         public int size() {
/* 245 */           return AbstractObject2IntMap.this.size();
/*     */         }
/*     */ 
/*     */         
/*     */         public void clear() {
/* 250 */           AbstractObject2IntMap.this.clear();
/*     */         }
/*     */ 
/*     */         
/*     */         public ObjectIterator<K> iterator() {
/* 255 */           return new ObjectIterator<K>() {
/* 256 */               private final ObjectIterator<Object2IntMap.Entry<K>> i = Object2IntMaps.fastIterator(AbstractObject2IntMap.this);
/*     */ 
/*     */               
/*     */               public K next() {
/* 260 */                 return ((Object2IntMap.Entry<K>)this.i.next()).getKey();
/*     */               }
/*     */ 
/*     */               
/*     */               public boolean hasNext() {
/* 265 */                 return this.i.hasNext();
/*     */               }
/*     */ 
/*     */               
/*     */               public void remove() {
/* 270 */                 this.i.remove();
/*     */               }
/*     */ 
/*     */               
/*     */               public void forEachRemaining(Consumer<? super K> action) {
/* 275 */                 this.i.forEachRemaining(entry -> action.accept(entry.getKey()));
/*     */               }
/*     */             };
/*     */         }
/*     */ 
/*     */         
/*     */         public ObjectSpliterator<K> spliterator() {
/* 282 */           return ObjectSpliterators.asSpliterator(iterator(), Size64.sizeOf(AbstractObject2IntMap.this), 65);
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
/*     */   public IntCollection values() {
/* 301 */     return (IntCollection)new AbstractIntCollection()
/*     */       {
/*     */         public boolean contains(int k) {
/* 304 */           return AbstractObject2IntMap.this.containsValue(k);
/*     */         }
/*     */ 
/*     */         
/*     */         public int size() {
/* 309 */           return AbstractObject2IntMap.this.size();
/*     */         }
/*     */ 
/*     */         
/*     */         public void clear() {
/* 314 */           AbstractObject2IntMap.this.clear();
/*     */         }
/*     */ 
/*     */         
/*     */         public IntIterator iterator() {
/* 319 */           return new IntIterator() {
/* 320 */               private final ObjectIterator<Object2IntMap.Entry<K>> i = Object2IntMaps.fastIterator(AbstractObject2IntMap.this);
/*     */ 
/*     */               
/*     */               public int nextInt() {
/* 324 */                 return ((Object2IntMap.Entry)this.i.next()).getIntValue();
/*     */               }
/*     */ 
/*     */               
/*     */               public boolean hasNext() {
/* 329 */                 return this.i.hasNext();
/*     */               }
/*     */ 
/*     */               
/*     */               public void remove() {
/* 334 */                 this.i.remove();
/*     */               }
/*     */ 
/*     */               
/*     */               public void forEachRemaining(IntConsumer action) {
/* 339 */                 this.i.forEachRemaining(entry -> action.accept(entry.getIntValue()));
/*     */               }
/*     */             };
/*     */         }
/*     */ 
/*     */         
/*     */         public IntSpliterator spliterator() {
/* 346 */           return IntSpliterators.asSpliterator(iterator(), Size64.sizeOf(AbstractObject2IntMap.this), 320);
/*     */         }
/*     */       };
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void putAll(Map<? extends K, ? extends Integer> m) {
/* 355 */     if (m instanceof Object2IntMap) {
/* 356 */       ObjectIterator<Object2IntMap.Entry<K>> i = Object2IntMaps.fastIterator((Object2IntMap)m);
/* 357 */       while (i.hasNext()) {
/* 358 */         Object2IntMap.Entry<? extends K> e = i.next();
/* 359 */         put(e.getKey(), e.getIntValue());
/*     */       } 
/*     */     } else {
/* 362 */       int n = m.size();
/* 363 */       Iterator<? extends Map.Entry<? extends K, ? extends Integer>> i = m.entrySet().iterator();
/*     */       
/* 365 */       while (n-- != 0) {
/* 366 */         Map.Entry<? extends K, ? extends Integer> e = i.next();
/* 367 */         put(e.getKey(), e.getValue());
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
/* 381 */     int h = 0, n = size();
/* 382 */     ObjectIterator<Object2IntMap.Entry<K>> i = Object2IntMaps.fastIterator(this);
/* 383 */     for (; n-- != 0; h += ((Object2IntMap.Entry)i.next()).hashCode());
/* 384 */     return h;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean equals(Object o) {
/* 389 */     if (o == this) return true; 
/* 390 */     if (!(o instanceof Map)) return false; 
/* 391 */     Map<?, ?> m = (Map<?, ?>)o;
/* 392 */     if (m.size() != size()) return false; 
/* 393 */     return object2IntEntrySet().containsAll(m.entrySet());
/*     */   }
/*     */ 
/*     */   
/*     */   public String toString() {
/* 398 */     StringBuilder s = new StringBuilder();
/* 399 */     ObjectIterator<Object2IntMap.Entry<K>> i = Object2IntMaps.fastIterator(this);
/* 400 */     int n = size();
/*     */     
/* 402 */     boolean first = true;
/* 403 */     s.append("{");
/* 404 */     while (n-- != 0) {
/* 405 */       if (first) { first = false; }
/* 406 */       else { s.append(", "); }
/* 407 */        Object2IntMap.Entry<K> e = i.next();
/* 408 */       if (this == e.getKey()) { s.append("(this map)"); }
/* 409 */       else { s.append(String.valueOf(e.getKey())); }
/* 410 */        s.append("=>");
/* 411 */       s.append(String.valueOf(e.getIntValue()));
/*     */     } 
/* 413 */     s.append("}");
/* 414 */     return s.toString();
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\i\\unimi\dsi\fastutil\objects\AbstractObject2IntMap.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */