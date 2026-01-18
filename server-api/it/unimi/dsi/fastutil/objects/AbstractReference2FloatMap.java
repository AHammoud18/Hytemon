/*     */ package it.unimi.dsi.fastutil.objects;
/*     */ 
/*     */ import it.unimi.dsi.fastutil.HashCommon;
/*     */ import it.unimi.dsi.fastutil.Size64;
/*     */ import it.unimi.dsi.fastutil.floats.AbstractFloatCollection;
/*     */ import it.unimi.dsi.fastutil.floats.FloatCollection;
/*     */ import it.unimi.dsi.fastutil.floats.FloatConsumer;
/*     */ import it.unimi.dsi.fastutil.floats.FloatIterator;
/*     */ import it.unimi.dsi.fastutil.floats.FloatSpliterator;
/*     */ import it.unimi.dsi.fastutil.floats.FloatSpliterators;
/*     */ import java.io.Serializable;
/*     */ import java.util.Collection;
/*     */ import java.util.Iterator;
/*     */ import java.util.Map;
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
/*     */ public abstract class AbstractReference2FloatMap<K>
/*     */   extends AbstractReference2FloatFunction<K>
/*     */   implements Reference2FloatMap<K>, Serializable
/*     */ {
/*     */   private static final long serialVersionUID = -4940583368468432370L;
/*     */   
/*     */   public boolean containsKey(Object k) {
/*  63 */     ObjectIterator<Reference2FloatMap.Entry<K>> i = reference2FloatEntrySet().iterator();
/*  64 */     while (i.hasNext()) { if (((Reference2FloatMap.Entry)i.next()).getKey() == k) return true;  }
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
/*     */   public boolean containsValue(float v) {
/*  83 */     ObjectIterator<Reference2FloatMap.Entry<K>> i = reference2FloatEntrySet().iterator();
/*  84 */     while (i.hasNext()) { if (((Reference2FloatMap.Entry)i.next()).getFloatValue() == v) return true;  }
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
/*     */     implements Reference2FloatMap.Entry<K>
/*     */   {
/*     */     protected K key;
/*     */ 
/*     */     
/*     */     protected float value;
/*     */ 
/*     */ 
/*     */     
/*     */     public BasicEntry() {}
/*     */ 
/*     */     
/*     */     public BasicEntry(K key, Float value) {
/* 109 */       this.key = key;
/* 110 */       this.value = value.floatValue();
/*     */     }
/*     */     
/*     */     public BasicEntry(K key, float value) {
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
/*     */     public float getFloatValue() {
/* 125 */       return this.value;
/*     */     }
/*     */ 
/*     */     
/*     */     public float setValue(float value) {
/* 130 */       throw new UnsupportedOperationException();
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     public boolean equals(Object o) {
/* 136 */       if (!(o instanceof Map.Entry)) return false; 
/* 137 */       if (o instanceof Reference2FloatMap.Entry) {
/* 138 */         Reference2FloatMap.Entry<K> entry = (Reference2FloatMap.Entry<K>)o;
/* 139 */         return (this.key == entry.getKey() && Float.floatToIntBits(this.value) == Float.floatToIntBits(entry.getFloatValue()));
/*     */       } 
/* 141 */       Map.Entry<?, ?> e = (Map.Entry<?, ?>)o;
/* 142 */       Object key = e.getKey();
/* 143 */       Object value = e.getValue();
/* 144 */       if (value == null || !(value instanceof Float)) return false; 
/* 145 */       return (this.key == key && Float.floatToIntBits(this.value) == Float.floatToIntBits(((Float)value).floatValue()));
/*     */     }
/*     */ 
/*     */     
/*     */     public int hashCode() {
/* 150 */       return System.identityHashCode(this.key) ^ HashCommon.float2int(this.value);
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
/*     */     extends AbstractObjectSet<Reference2FloatMap.Entry<K>>
/*     */   {
/*     */     protected final Reference2FloatMap<K> map;
/*     */ 
/*     */     
/*     */     public BasicEntrySet(Reference2FloatMap<K> map) {
/* 167 */       this.map = map;
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     public boolean contains(Object o) {
/* 173 */       if (!(o instanceof Map.Entry)) return false; 
/* 174 */       if (o instanceof Reference2FloatMap.Entry) {
/* 175 */         Reference2FloatMap.Entry<K> entry = (Reference2FloatMap.Entry<K>)o;
/* 176 */         K k1 = entry.getKey();
/* 177 */         return (this.map.containsKey(k1) && Float.floatToIntBits(this.map.getFloat(k1)) == Float.floatToIntBits(entry.getFloatValue()));
/*     */       } 
/* 179 */       Map.Entry<?, ?> e = (Map.Entry<?, ?>)o;
/* 180 */       Object k = e.getKey();
/* 181 */       Object value = e.getValue();
/* 182 */       if (value == null || !(value instanceof Float)) return false; 
/* 183 */       return (this.map.containsKey(k) && Float.floatToIntBits(this.map.getFloat(k)) == Float.floatToIntBits(((Float)value).floatValue()));
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     public boolean remove(Object o) {
/* 189 */       if (!(o instanceof Map.Entry)) return false; 
/* 190 */       if (o instanceof Reference2FloatMap.Entry) {
/* 191 */         Reference2FloatMap.Entry<K> entry = (Reference2FloatMap.Entry<K>)o;
/* 192 */         return this.map.remove(entry.getKey(), entry.getFloatValue());
/*     */       } 
/* 194 */       Map.Entry<?, ?> e = (Map.Entry<?, ?>)o;
/* 195 */       Object k = e.getKey();
/* 196 */       Object value = e.getValue();
/* 197 */       if (value == null || !(value instanceof Float)) return false; 
/* 198 */       float v = ((Float)value).floatValue();
/* 199 */       return this.map.remove(k, v);
/*     */     }
/*     */ 
/*     */     
/*     */     public int size() {
/* 204 */       return this.map.size();
/*     */     }
/*     */ 
/*     */     
/*     */     public ObjectSpliterator<Reference2FloatMap.Entry<K>> spliterator() {
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
/*     */   public ReferenceSet<K> keySet() {
/* 227 */     return new AbstractReferenceSet<K>()
/*     */       {
/*     */         public boolean contains(Object k) {
/* 230 */           return AbstractReference2FloatMap.this.containsKey(k);
/*     */         }
/*     */ 
/*     */         
/*     */         public int size() {
/* 235 */           return AbstractReference2FloatMap.this.size();
/*     */         }
/*     */ 
/*     */         
/*     */         public void clear() {
/* 240 */           AbstractReference2FloatMap.this.clear();
/*     */         }
/*     */ 
/*     */         
/*     */         public ObjectIterator<K> iterator() {
/* 245 */           return new ObjectIterator<K>() {
/* 246 */               private final ObjectIterator<Reference2FloatMap.Entry<K>> i = Reference2FloatMaps.fastIterator(AbstractReference2FloatMap.this);
/*     */ 
/*     */               
/*     */               public K next() {
/* 250 */                 return ((Reference2FloatMap.Entry<K>)this.i.next()).getKey();
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
/* 272 */           return ObjectSpliterators.asSpliterator(iterator(), Size64.sizeOf(AbstractReference2FloatMap.this), 65);
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
/*     */   public FloatCollection values() {
/* 291 */     return (FloatCollection)new AbstractFloatCollection()
/*     */       {
/*     */         public boolean contains(float k) {
/* 294 */           return AbstractReference2FloatMap.this.containsValue(k);
/*     */         }
/*     */ 
/*     */         
/*     */         public int size() {
/* 299 */           return AbstractReference2FloatMap.this.size();
/*     */         }
/*     */ 
/*     */         
/*     */         public void clear() {
/* 304 */           AbstractReference2FloatMap.this.clear();
/*     */         }
/*     */ 
/*     */         
/*     */         public FloatIterator iterator() {
/* 309 */           return new FloatIterator() {
/* 310 */               private final ObjectIterator<Reference2FloatMap.Entry<K>> i = Reference2FloatMaps.fastIterator(AbstractReference2FloatMap.this);
/*     */ 
/*     */               
/*     */               public float nextFloat() {
/* 314 */                 return ((Reference2FloatMap.Entry)this.i.next()).getFloatValue();
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
/*     */               public void forEachRemaining(FloatConsumer action) {
/* 329 */                 this.i.forEachRemaining(entry -> action.accept(entry.getFloatValue()));
/*     */               }
/*     */             };
/*     */         }
/*     */ 
/*     */         
/*     */         public FloatSpliterator spliterator() {
/* 336 */           return FloatSpliterators.asSpliterator(iterator(), Size64.sizeOf(AbstractReference2FloatMap.this), 320);
/*     */         }
/*     */       };
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void putAll(Map<? extends K, ? extends Float> m) {
/* 345 */     if (m instanceof Reference2FloatMap) {
/* 346 */       ObjectIterator<Reference2FloatMap.Entry<K>> i = Reference2FloatMaps.fastIterator((Reference2FloatMap)m);
/* 347 */       while (i.hasNext()) {
/* 348 */         Reference2FloatMap.Entry<? extends K> e = i.next();
/* 349 */         put(e.getKey(), e.getFloatValue());
/*     */       } 
/*     */     } else {
/* 352 */       int n = m.size();
/* 353 */       Iterator<? extends Map.Entry<? extends K, ? extends Float>> i = m.entrySet().iterator();
/*     */       
/* 355 */       while (n-- != 0) {
/* 356 */         Map.Entry<? extends K, ? extends Float> e = i.next();
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
/* 372 */     ObjectIterator<Reference2FloatMap.Entry<K>> i = Reference2FloatMaps.fastIterator(this);
/* 373 */     for (; n-- != 0; h += ((Reference2FloatMap.Entry)i.next()).hashCode());
/* 374 */     return h;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean equals(Object o) {
/* 379 */     if (o == this) return true; 
/* 380 */     if (!(o instanceof Map)) return false; 
/* 381 */     Map<?, ?> m = (Map<?, ?>)o;
/* 382 */     if (m.size() != size()) return false; 
/* 383 */     return reference2FloatEntrySet().containsAll(m.entrySet());
/*     */   }
/*     */ 
/*     */   
/*     */   public String toString() {
/* 388 */     StringBuilder s = new StringBuilder();
/* 389 */     ObjectIterator<Reference2FloatMap.Entry<K>> i = Reference2FloatMaps.fastIterator(this);
/* 390 */     int n = size();
/*     */     
/* 392 */     boolean first = true;
/* 393 */     s.append("{");
/* 394 */     while (n-- != 0) {
/* 395 */       if (first) { first = false; }
/* 396 */       else { s.append(", "); }
/* 397 */        Reference2FloatMap.Entry<K> e = i.next();
/* 398 */       if (this == e.getKey()) { s.append("(this map)"); }
/* 399 */       else { s.append(String.valueOf(e.getKey())); }
/* 400 */        s.append("=>");
/* 401 */       s.append(String.valueOf(e.getFloatValue()));
/*     */     } 
/* 403 */     s.append("}");
/* 404 */     return s.toString();
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\i\\unimi\dsi\fastutil\objects\AbstractReference2FloatMap.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */