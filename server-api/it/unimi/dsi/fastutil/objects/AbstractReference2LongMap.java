/*     */ package it.unimi.dsi.fastutil.objects;
/*     */ 
/*     */ import it.unimi.dsi.fastutil.HashCommon;
/*     */ import it.unimi.dsi.fastutil.Size64;
/*     */ import it.unimi.dsi.fastutil.longs.AbstractLongCollection;
/*     */ import it.unimi.dsi.fastutil.longs.LongBinaryOperator;
/*     */ import it.unimi.dsi.fastutil.longs.LongCollection;
/*     */ import it.unimi.dsi.fastutil.longs.LongIterator;
/*     */ import it.unimi.dsi.fastutil.longs.LongSpliterator;
/*     */ import it.unimi.dsi.fastutil.longs.LongSpliterators;
/*     */ import java.io.Serializable;
/*     */ import java.util.Collection;
/*     */ import java.util.Iterator;
/*     */ import java.util.Map;
/*     */ import java.util.Set;
/*     */ import java.util.Spliterator;
/*     */ import java.util.function.Consumer;
/*     */ import java.util.function.LongBinaryOperator;
/*     */ import java.util.function.LongConsumer;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public abstract class AbstractReference2LongMap<K>
/*     */   extends AbstractReference2LongFunction<K>
/*     */   implements Reference2LongMap<K>, Serializable
/*     */ {
/*     */   private static final long serialVersionUID = -4940583368468432370L;
/*     */   
/*     */   public boolean containsKey(Object k) {
/*  62 */     ObjectIterator<Reference2LongMap.Entry<K>> i = reference2LongEntrySet().iterator();
/*  63 */     while (i.hasNext()) { if (((Reference2LongMap.Entry)i.next()).getKey() == k) return true;  }
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
/*     */   public boolean containsValue(long v) {
/*  82 */     ObjectIterator<Reference2LongMap.Entry<K>> i = reference2LongEntrySet().iterator();
/*  83 */     while (i.hasNext()) { if (((Reference2LongMap.Entry)i.next()).getLongValue() == v) return true;  }
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
/*     */   public final long mergeLong(K key, long value, LongBinaryOperator remappingFunction) {
/* 100 */     return mergeLong(key, value, (LongBinaryOperator)remappingFunction);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public static class BasicEntry<K>
/*     */     implements Reference2LongMap.Entry<K>
/*     */   {
/*     */     protected K key;
/*     */ 
/*     */     
/*     */     protected long value;
/*     */ 
/*     */ 
/*     */     
/*     */     public BasicEntry() {}
/*     */ 
/*     */     
/*     */     public BasicEntry(K key, Long value) {
/* 119 */       this.key = key;
/* 120 */       this.value = value.longValue();
/*     */     }
/*     */     
/*     */     public BasicEntry(K key, long value) {
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
/*     */     public long getLongValue() {
/* 135 */       return this.value;
/*     */     }
/*     */ 
/*     */     
/*     */     public long setValue(long value) {
/* 140 */       throw new UnsupportedOperationException();
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     public boolean equals(Object o) {
/* 146 */       if (!(o instanceof Map.Entry)) return false; 
/* 147 */       if (o instanceof Reference2LongMap.Entry) {
/* 148 */         Reference2LongMap.Entry<K> entry = (Reference2LongMap.Entry<K>)o;
/* 149 */         return (this.key == entry.getKey() && this.value == entry.getLongValue());
/*     */       } 
/* 151 */       Map.Entry<?, ?> e = (Map.Entry<?, ?>)o;
/* 152 */       Object key = e.getKey();
/* 153 */       Object value = e.getValue();
/* 154 */       if (value == null || !(value instanceof Long)) return false; 
/* 155 */       return (this.key == key && this.value == ((Long)value).longValue());
/*     */     }
/*     */ 
/*     */     
/*     */     public int hashCode() {
/* 160 */       return System.identityHashCode(this.key) ^ HashCommon.long2int(this.value);
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
/*     */     extends AbstractObjectSet<Reference2LongMap.Entry<K>>
/*     */   {
/*     */     protected final Reference2LongMap<K> map;
/*     */ 
/*     */     
/*     */     public BasicEntrySet(Reference2LongMap<K> map) {
/* 177 */       this.map = map;
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     public boolean contains(Object o) {
/* 183 */       if (!(o instanceof Map.Entry)) return false; 
/* 184 */       if (o instanceof Reference2LongMap.Entry) {
/* 185 */         Reference2LongMap.Entry<K> entry = (Reference2LongMap.Entry<K>)o;
/* 186 */         K k1 = entry.getKey();
/* 187 */         return (this.map.containsKey(k1) && this.map.getLong(k1) == entry.getLongValue());
/*     */       } 
/* 189 */       Map.Entry<?, ?> e = (Map.Entry<?, ?>)o;
/* 190 */       Object k = e.getKey();
/* 191 */       Object value = e.getValue();
/* 192 */       if (value == null || !(value instanceof Long)) return false; 
/* 193 */       return (this.map.containsKey(k) && this.map.getLong(k) == ((Long)value).longValue());
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     public boolean remove(Object o) {
/* 199 */       if (!(o instanceof Map.Entry)) return false; 
/* 200 */       if (o instanceof Reference2LongMap.Entry) {
/* 201 */         Reference2LongMap.Entry<K> entry = (Reference2LongMap.Entry<K>)o;
/* 202 */         return this.map.remove(entry.getKey(), entry.getLongValue());
/*     */       } 
/* 204 */       Map.Entry<?, ?> e = (Map.Entry<?, ?>)o;
/* 205 */       Object k = e.getKey();
/* 206 */       Object value = e.getValue();
/* 207 */       if (value == null || !(value instanceof Long)) return false; 
/* 208 */       long v = ((Long)value).longValue();
/* 209 */       return this.map.remove(k, v);
/*     */     }
/*     */ 
/*     */     
/*     */     public int size() {
/* 214 */       return this.map.size();
/*     */     }
/*     */ 
/*     */     
/*     */     public ObjectSpliterator<Reference2LongMap.Entry<K>> spliterator() {
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
/*     */   public ReferenceSet<K> keySet() {
/* 237 */     return new AbstractReferenceSet<K>()
/*     */       {
/*     */         public boolean contains(Object k) {
/* 240 */           return AbstractReference2LongMap.this.containsKey(k);
/*     */         }
/*     */ 
/*     */         
/*     */         public int size() {
/* 245 */           return AbstractReference2LongMap.this.size();
/*     */         }
/*     */ 
/*     */         
/*     */         public void clear() {
/* 250 */           AbstractReference2LongMap.this.clear();
/*     */         }
/*     */ 
/*     */         
/*     */         public ObjectIterator<K> iterator() {
/* 255 */           return new ObjectIterator<K>() {
/* 256 */               private final ObjectIterator<Reference2LongMap.Entry<K>> i = Reference2LongMaps.fastIterator(AbstractReference2LongMap.this);
/*     */ 
/*     */               
/*     */               public K next() {
/* 260 */                 return ((Reference2LongMap.Entry<K>)this.i.next()).getKey();
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
/* 282 */           return ObjectSpliterators.asSpliterator(iterator(), Size64.sizeOf(AbstractReference2LongMap.this), 65);
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
/*     */   public LongCollection values() {
/* 301 */     return (LongCollection)new AbstractLongCollection()
/*     */       {
/*     */         public boolean contains(long k) {
/* 304 */           return AbstractReference2LongMap.this.containsValue(k);
/*     */         }
/*     */ 
/*     */         
/*     */         public int size() {
/* 309 */           return AbstractReference2LongMap.this.size();
/*     */         }
/*     */ 
/*     */         
/*     */         public void clear() {
/* 314 */           AbstractReference2LongMap.this.clear();
/*     */         }
/*     */ 
/*     */         
/*     */         public LongIterator iterator() {
/* 319 */           return new LongIterator() {
/* 320 */               private final ObjectIterator<Reference2LongMap.Entry<K>> i = Reference2LongMaps.fastIterator(AbstractReference2LongMap.this);
/*     */ 
/*     */               
/*     */               public long nextLong() {
/* 324 */                 return ((Reference2LongMap.Entry)this.i.next()).getLongValue();
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
/*     */               public void forEachRemaining(LongConsumer action) {
/* 339 */                 this.i.forEachRemaining(entry -> action.accept(entry.getLongValue()));
/*     */               }
/*     */             };
/*     */         }
/*     */ 
/*     */         
/*     */         public LongSpliterator spliterator() {
/* 346 */           return LongSpliterators.asSpliterator(iterator(), Size64.sizeOf(AbstractReference2LongMap.this), 320);
/*     */         }
/*     */       };
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void putAll(Map<? extends K, ? extends Long> m) {
/* 355 */     if (m instanceof Reference2LongMap) {
/* 356 */       ObjectIterator<Reference2LongMap.Entry<K>> i = Reference2LongMaps.fastIterator((Reference2LongMap)m);
/* 357 */       while (i.hasNext()) {
/* 358 */         Reference2LongMap.Entry<? extends K> e = i.next();
/* 359 */         put(e.getKey(), e.getLongValue());
/*     */       } 
/*     */     } else {
/* 362 */       int n = m.size();
/* 363 */       Iterator<? extends Map.Entry<? extends K, ? extends Long>> i = m.entrySet().iterator();
/*     */       
/* 365 */       while (n-- != 0) {
/* 366 */         Map.Entry<? extends K, ? extends Long> e = i.next();
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
/* 382 */     ObjectIterator<Reference2LongMap.Entry<K>> i = Reference2LongMaps.fastIterator(this);
/* 383 */     for (; n-- != 0; h += ((Reference2LongMap.Entry)i.next()).hashCode());
/* 384 */     return h;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean equals(Object o) {
/* 389 */     if (o == this) return true; 
/* 390 */     if (!(o instanceof Map)) return false; 
/* 391 */     Map<?, ?> m = (Map<?, ?>)o;
/* 392 */     if (m.size() != size()) return false; 
/* 393 */     return reference2LongEntrySet().containsAll(m.entrySet());
/*     */   }
/*     */ 
/*     */   
/*     */   public String toString() {
/* 398 */     StringBuilder s = new StringBuilder();
/* 399 */     ObjectIterator<Reference2LongMap.Entry<K>> i = Reference2LongMaps.fastIterator(this);
/* 400 */     int n = size();
/*     */     
/* 402 */     boolean first = true;
/* 403 */     s.append("{");
/* 404 */     while (n-- != 0) {
/* 405 */       if (first) { first = false; }
/* 406 */       else { s.append(", "); }
/* 407 */        Reference2LongMap.Entry<K> e = i.next();
/* 408 */       if (this == e.getKey()) { s.append("(this map)"); }
/* 409 */       else { s.append(String.valueOf(e.getKey())); }
/* 410 */        s.append("=>");
/* 411 */       s.append(String.valueOf(e.getLongValue()));
/*     */     } 
/* 413 */     s.append("}");
/* 414 */     return s.toString();
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\i\\unimi\dsi\fastutil\objects\AbstractReference2LongMap.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */