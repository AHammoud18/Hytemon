/*     */ package it.unimi.dsi.fastutil.objects;
/*     */ 
/*     */ import it.unimi.dsi.fastutil.HashCommon;
/*     */ import it.unimi.dsi.fastutil.Size64;
/*     */ import it.unimi.dsi.fastutil.doubles.AbstractDoubleCollection;
/*     */ import it.unimi.dsi.fastutil.doubles.DoubleBinaryOperator;
/*     */ import it.unimi.dsi.fastutil.doubles.DoubleCollection;
/*     */ import it.unimi.dsi.fastutil.doubles.DoubleIterator;
/*     */ import it.unimi.dsi.fastutil.doubles.DoubleSpliterator;
/*     */ import it.unimi.dsi.fastutil.doubles.DoubleSpliterators;
/*     */ import java.io.Serializable;
/*     */ import java.util.Collection;
/*     */ import java.util.Iterator;
/*     */ import java.util.Map;
/*     */ import java.util.Set;
/*     */ import java.util.Spliterator;
/*     */ import java.util.function.Consumer;
/*     */ import java.util.function.DoubleBinaryOperator;
/*     */ import java.util.function.DoubleConsumer;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public abstract class AbstractReference2DoubleMap<K>
/*     */   extends AbstractReference2DoubleFunction<K>
/*     */   implements Reference2DoubleMap<K>, Serializable
/*     */ {
/*     */   private static final long serialVersionUID = -4940583368468432370L;
/*     */   
/*     */   public boolean containsKey(Object k) {
/*  62 */     ObjectIterator<Reference2DoubleMap.Entry<K>> i = reference2DoubleEntrySet().iterator();
/*  63 */     while (i.hasNext()) { if (((Reference2DoubleMap.Entry)i.next()).getKey() == k) return true;  }
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
/*     */   public boolean containsValue(double v) {
/*  82 */     ObjectIterator<Reference2DoubleMap.Entry<K>> i = reference2DoubleEntrySet().iterator();
/*  83 */     while (i.hasNext()) { if (((Reference2DoubleMap.Entry)i.next()).getDoubleValue() == v) return true;  }
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
/*     */   public final double mergeDouble(K key, double value, DoubleBinaryOperator remappingFunction) {
/* 100 */     return mergeDouble(key, value, (DoubleBinaryOperator)remappingFunction);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public static class BasicEntry<K>
/*     */     implements Reference2DoubleMap.Entry<K>
/*     */   {
/*     */     protected K key;
/*     */ 
/*     */     
/*     */     protected double value;
/*     */ 
/*     */ 
/*     */     
/*     */     public BasicEntry() {}
/*     */ 
/*     */     
/*     */     public BasicEntry(K key, Double value) {
/* 119 */       this.key = key;
/* 120 */       this.value = value.doubleValue();
/*     */     }
/*     */     
/*     */     public BasicEntry(K key, double value) {
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
/*     */     public double getDoubleValue() {
/* 135 */       return this.value;
/*     */     }
/*     */ 
/*     */     
/*     */     public double setValue(double value) {
/* 140 */       throw new UnsupportedOperationException();
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     public boolean equals(Object o) {
/* 146 */       if (!(o instanceof Map.Entry)) return false; 
/* 147 */       if (o instanceof Reference2DoubleMap.Entry) {
/* 148 */         Reference2DoubleMap.Entry<K> entry = (Reference2DoubleMap.Entry<K>)o;
/* 149 */         return (this.key == entry.getKey() && Double.doubleToLongBits(this.value) == Double.doubleToLongBits(entry.getDoubleValue()));
/*     */       } 
/* 151 */       Map.Entry<?, ?> e = (Map.Entry<?, ?>)o;
/* 152 */       Object key = e.getKey();
/* 153 */       Object value = e.getValue();
/* 154 */       if (value == null || !(value instanceof Double)) return false; 
/* 155 */       return (this.key == key && Double.doubleToLongBits(this.value) == Double.doubleToLongBits(((Double)value).doubleValue()));
/*     */     }
/*     */ 
/*     */     
/*     */     public int hashCode() {
/* 160 */       return System.identityHashCode(this.key) ^ HashCommon.double2int(this.value);
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
/*     */     extends AbstractObjectSet<Reference2DoubleMap.Entry<K>>
/*     */   {
/*     */     protected final Reference2DoubleMap<K> map;
/*     */ 
/*     */     
/*     */     public BasicEntrySet(Reference2DoubleMap<K> map) {
/* 177 */       this.map = map;
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     public boolean contains(Object o) {
/* 183 */       if (!(o instanceof Map.Entry)) return false; 
/* 184 */       if (o instanceof Reference2DoubleMap.Entry) {
/* 185 */         Reference2DoubleMap.Entry<K> entry = (Reference2DoubleMap.Entry<K>)o;
/* 186 */         K k1 = entry.getKey();
/* 187 */         return (this.map.containsKey(k1) && Double.doubleToLongBits(this.map.getDouble(k1)) == Double.doubleToLongBits(entry.getDoubleValue()));
/*     */       } 
/* 189 */       Map.Entry<?, ?> e = (Map.Entry<?, ?>)o;
/* 190 */       Object k = e.getKey();
/* 191 */       Object value = e.getValue();
/* 192 */       if (value == null || !(value instanceof Double)) return false; 
/* 193 */       return (this.map.containsKey(k) && Double.doubleToLongBits(this.map.getDouble(k)) == Double.doubleToLongBits(((Double)value).doubleValue()));
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     public boolean remove(Object o) {
/* 199 */       if (!(o instanceof Map.Entry)) return false; 
/* 200 */       if (o instanceof Reference2DoubleMap.Entry) {
/* 201 */         Reference2DoubleMap.Entry<K> entry = (Reference2DoubleMap.Entry<K>)o;
/* 202 */         return this.map.remove(entry.getKey(), entry.getDoubleValue());
/*     */       } 
/* 204 */       Map.Entry<?, ?> e = (Map.Entry<?, ?>)o;
/* 205 */       Object k = e.getKey();
/* 206 */       Object value = e.getValue();
/* 207 */       if (value == null || !(value instanceof Double)) return false; 
/* 208 */       double v = ((Double)value).doubleValue();
/* 209 */       return this.map.remove(k, v);
/*     */     }
/*     */ 
/*     */     
/*     */     public int size() {
/* 214 */       return this.map.size();
/*     */     }
/*     */ 
/*     */     
/*     */     public ObjectSpliterator<Reference2DoubleMap.Entry<K>> spliterator() {
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
/* 240 */           return AbstractReference2DoubleMap.this.containsKey(k);
/*     */         }
/*     */ 
/*     */         
/*     */         public int size() {
/* 245 */           return AbstractReference2DoubleMap.this.size();
/*     */         }
/*     */ 
/*     */         
/*     */         public void clear() {
/* 250 */           AbstractReference2DoubleMap.this.clear();
/*     */         }
/*     */ 
/*     */         
/*     */         public ObjectIterator<K> iterator() {
/* 255 */           return new ObjectIterator<K>() {
/* 256 */               private final ObjectIterator<Reference2DoubleMap.Entry<K>> i = Reference2DoubleMaps.fastIterator(AbstractReference2DoubleMap.this);
/*     */ 
/*     */               
/*     */               public K next() {
/* 260 */                 return ((Reference2DoubleMap.Entry<K>)this.i.next()).getKey();
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
/* 282 */           return ObjectSpliterators.asSpliterator(iterator(), Size64.sizeOf(AbstractReference2DoubleMap.this), 65);
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
/*     */   public DoubleCollection values() {
/* 301 */     return (DoubleCollection)new AbstractDoubleCollection()
/*     */       {
/*     */         public boolean contains(double k) {
/* 304 */           return AbstractReference2DoubleMap.this.containsValue(k);
/*     */         }
/*     */ 
/*     */         
/*     */         public int size() {
/* 309 */           return AbstractReference2DoubleMap.this.size();
/*     */         }
/*     */ 
/*     */         
/*     */         public void clear() {
/* 314 */           AbstractReference2DoubleMap.this.clear();
/*     */         }
/*     */ 
/*     */         
/*     */         public DoubleIterator iterator() {
/* 319 */           return new DoubleIterator() {
/* 320 */               private final ObjectIterator<Reference2DoubleMap.Entry<K>> i = Reference2DoubleMaps.fastIterator(AbstractReference2DoubleMap.this);
/*     */ 
/*     */               
/*     */               public double nextDouble() {
/* 324 */                 return ((Reference2DoubleMap.Entry)this.i.next()).getDoubleValue();
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
/*     */               public void forEachRemaining(DoubleConsumer action) {
/* 339 */                 this.i.forEachRemaining(entry -> action.accept(entry.getDoubleValue()));
/*     */               }
/*     */             };
/*     */         }
/*     */ 
/*     */         
/*     */         public DoubleSpliterator spliterator() {
/* 346 */           return DoubleSpliterators.asSpliterator(iterator(), Size64.sizeOf(AbstractReference2DoubleMap.this), 320);
/*     */         }
/*     */       };
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void putAll(Map<? extends K, ? extends Double> m) {
/* 355 */     if (m instanceof Reference2DoubleMap) {
/* 356 */       ObjectIterator<Reference2DoubleMap.Entry<K>> i = Reference2DoubleMaps.fastIterator((Reference2DoubleMap)m);
/* 357 */       while (i.hasNext()) {
/* 358 */         Reference2DoubleMap.Entry<? extends K> e = i.next();
/* 359 */         put(e.getKey(), e.getDoubleValue());
/*     */       } 
/*     */     } else {
/* 362 */       int n = m.size();
/* 363 */       Iterator<? extends Map.Entry<? extends K, ? extends Double>> i = m.entrySet().iterator();
/*     */       
/* 365 */       while (n-- != 0) {
/* 366 */         Map.Entry<? extends K, ? extends Double> e = i.next();
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
/* 382 */     ObjectIterator<Reference2DoubleMap.Entry<K>> i = Reference2DoubleMaps.fastIterator(this);
/* 383 */     for (; n-- != 0; h += ((Reference2DoubleMap.Entry)i.next()).hashCode());
/* 384 */     return h;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean equals(Object o) {
/* 389 */     if (o == this) return true; 
/* 390 */     if (!(o instanceof Map)) return false; 
/* 391 */     Map<?, ?> m = (Map<?, ?>)o;
/* 392 */     if (m.size() != size()) return false; 
/* 393 */     return reference2DoubleEntrySet().containsAll(m.entrySet());
/*     */   }
/*     */ 
/*     */   
/*     */   public String toString() {
/* 398 */     StringBuilder s = new StringBuilder();
/* 399 */     ObjectIterator<Reference2DoubleMap.Entry<K>> i = Reference2DoubleMaps.fastIterator(this);
/* 400 */     int n = size();
/*     */     
/* 402 */     boolean first = true;
/* 403 */     s.append("{");
/* 404 */     while (n-- != 0) {
/* 405 */       if (first) { first = false; }
/* 406 */       else { s.append(", "); }
/* 407 */        Reference2DoubleMap.Entry<K> e = i.next();
/* 408 */       if (this == e.getKey()) { s.append("(this map)"); }
/* 409 */       else { s.append(String.valueOf(e.getKey())); }
/* 410 */        s.append("=>");
/* 411 */       s.append(String.valueOf(e.getDoubleValue()));
/*     */     } 
/* 413 */     s.append("}");
/* 414 */     return s.toString();
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\i\\unimi\dsi\fastutil\objects\AbstractReference2DoubleMap.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */