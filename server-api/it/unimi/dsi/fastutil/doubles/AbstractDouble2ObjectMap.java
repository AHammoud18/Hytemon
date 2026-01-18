/*     */ package it.unimi.dsi.fastutil.doubles;
/*     */ 
/*     */ import it.unimi.dsi.fastutil.HashCommon;
/*     */ import it.unimi.dsi.fastutil.Size64;
/*     */ import it.unimi.dsi.fastutil.objects.AbstractObjectCollection;
/*     */ import it.unimi.dsi.fastutil.objects.AbstractObjectSet;
/*     */ import it.unimi.dsi.fastutil.objects.ObjectCollection;
/*     */ import it.unimi.dsi.fastutil.objects.ObjectIterator;
/*     */ import it.unimi.dsi.fastutil.objects.ObjectSpliterator;
/*     */ import it.unimi.dsi.fastutil.objects.ObjectSpliterators;
/*     */ import java.io.Serializable;
/*     */ import java.util.Collection;
/*     */ import java.util.Iterator;
/*     */ import java.util.Map;
/*     */ import java.util.Objects;
/*     */ import java.util.Set;
/*     */ import java.util.Spliterator;
/*     */ import java.util.function.Consumer;
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
/*     */ 
/*     */ public abstract class AbstractDouble2ObjectMap<V>
/*     */   extends AbstractDouble2ObjectFunction<V>
/*     */   implements Double2ObjectMap<V>, Serializable
/*     */ {
/*     */   private static final long serialVersionUID = -4940583368468432370L;
/*     */   
/*     */   public boolean containsKey(double k) {
/*  63 */     ObjectIterator<Double2ObjectMap.Entry<V>> i = double2ObjectEntrySet().iterator();
/*  64 */     while (i.hasNext()) { if (((Double2ObjectMap.Entry)i.next()).getDoubleKey() == k) return true;  }
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
/*     */   public boolean containsValue(Object v) {
/*  83 */     ObjectIterator<Double2ObjectMap.Entry<V>> i = double2ObjectEntrySet().iterator();
/*  84 */     while (i.hasNext()) { if (((Double2ObjectMap.Entry)i.next()).getValue() == v) return true;  }
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
/*     */   public static class BasicEntry<V>
/*     */     implements Double2ObjectMap.Entry<V>
/*     */   {
/*     */     protected double key;
/*     */ 
/*     */     
/*     */     protected V value;
/*     */ 
/*     */ 
/*     */     
/*     */     public BasicEntry() {}
/*     */ 
/*     */     
/*     */     public BasicEntry(Double key, V value) {
/* 109 */       this.key = key.doubleValue();
/* 110 */       this.value = value;
/*     */     }
/*     */     
/*     */     public BasicEntry(double key, V value) {
/* 114 */       this.key = key;
/* 115 */       this.value = value;
/*     */     }
/*     */ 
/*     */     
/*     */     public double getDoubleKey() {
/* 120 */       return this.key;
/*     */     }
/*     */ 
/*     */     
/*     */     public V getValue() {
/* 125 */       return this.value;
/*     */     }
/*     */ 
/*     */     
/*     */     public V setValue(V value) {
/* 130 */       throw new UnsupportedOperationException();
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     public boolean equals(Object o) {
/* 136 */       if (!(o instanceof Map.Entry)) return false; 
/* 137 */       if (o instanceof Double2ObjectMap.Entry) {
/* 138 */         Double2ObjectMap.Entry<V> entry = (Double2ObjectMap.Entry<V>)o;
/* 139 */         return (Double.doubleToLongBits(this.key) == Double.doubleToLongBits(entry.getDoubleKey()) && Objects.equals(this.value, entry.getValue()));
/*     */       } 
/* 141 */       Map.Entry<?, ?> e = (Map.Entry<?, ?>)o;
/* 142 */       Object key = e.getKey();
/* 143 */       if (key == null || !(key instanceof Double)) return false; 
/* 144 */       Object value = e.getValue();
/* 145 */       return (Double.doubleToLongBits(this.key) == Double.doubleToLongBits(((Double)key).doubleValue()) && Objects.equals(this.value, value));
/*     */     }
/*     */ 
/*     */     
/*     */     public int hashCode() {
/* 150 */       return HashCommon.double2int(this.key) ^ ((this.value == null) ? 0 : this.value.hashCode());
/*     */     }
/*     */ 
/*     */     
/*     */     public String toString() {
/* 155 */       return this.key + "->" + this.value;
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   public static abstract class BasicEntrySet<V>
/*     */     extends AbstractObjectSet<Double2ObjectMap.Entry<V>>
/*     */   {
/*     */     protected final Double2ObjectMap<V> map;
/*     */ 
/*     */     
/*     */     public BasicEntrySet(Double2ObjectMap<V> map) {
/* 167 */       this.map = map;
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     public boolean contains(Object o) {
/* 173 */       if (!(o instanceof Map.Entry)) return false; 
/* 174 */       if (o instanceof Double2ObjectMap.Entry) {
/* 175 */         Double2ObjectMap.Entry<V> entry = (Double2ObjectMap.Entry<V>)o;
/* 176 */         double d = entry.getDoubleKey();
/* 177 */         return (this.map.containsKey(d) && Objects.equals(this.map.get(d), entry.getValue()));
/*     */       } 
/* 179 */       Map.Entry<?, ?> e = (Map.Entry<?, ?>)o;
/* 180 */       Object key = e.getKey();
/* 181 */       if (key == null || !(key instanceof Double)) return false; 
/* 182 */       double k = ((Double)key).doubleValue();
/* 183 */       Object value = e.getValue();
/* 184 */       return (this.map.containsKey(k) && Objects.equals(this.map.get(k), value));
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     public boolean remove(Object o) {
/* 190 */       if (!(o instanceof Map.Entry)) return false; 
/* 191 */       if (o instanceof Double2ObjectMap.Entry) {
/* 192 */         Double2ObjectMap.Entry<V> entry = (Double2ObjectMap.Entry<V>)o;
/* 193 */         return this.map.remove(entry.getDoubleKey(), entry.getValue());
/*     */       } 
/* 195 */       Map.Entry<?, ?> e = (Map.Entry<?, ?>)o;
/* 196 */       Object key = e.getKey();
/* 197 */       if (key == null || !(key instanceof Double)) return false; 
/* 198 */       double k = ((Double)key).doubleValue();
/* 199 */       Object v = e.getValue();
/* 200 */       return this.map.remove(k, v);
/*     */     }
/*     */ 
/*     */     
/*     */     public int size() {
/* 205 */       return this.map.size();
/*     */     }
/*     */ 
/*     */     
/*     */     public ObjectSpliterator<Double2ObjectMap.Entry<V>> spliterator() {
/* 210 */       return ObjectSpliterators.asSpliterator(iterator(), Size64.sizeOf(this.map), 65);
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
/*     */   public DoubleSet keySet() {
/* 228 */     return new AbstractDoubleSet()
/*     */       {
/*     */         public boolean contains(double k) {
/* 231 */           return AbstractDouble2ObjectMap.this.containsKey(k);
/*     */         }
/*     */ 
/*     */         
/*     */         public int size() {
/* 236 */           return AbstractDouble2ObjectMap.this.size();
/*     */         }
/*     */ 
/*     */         
/*     */         public void clear() {
/* 241 */           AbstractDouble2ObjectMap.this.clear();
/*     */         }
/*     */ 
/*     */         
/*     */         public DoubleIterator iterator() {
/* 246 */           return new DoubleIterator() {
/* 247 */               private final ObjectIterator<Double2ObjectMap.Entry<V>> i = Double2ObjectMaps.fastIterator(AbstractDouble2ObjectMap.this);
/*     */ 
/*     */               
/*     */               public double nextDouble() {
/* 251 */                 return ((Double2ObjectMap.Entry)this.i.next()).getDoubleKey();
/*     */               }
/*     */ 
/*     */               
/*     */               public boolean hasNext() {
/* 256 */                 return this.i.hasNext();
/*     */               }
/*     */ 
/*     */               
/*     */               public void remove() {
/* 261 */                 this.i.remove();
/*     */               }
/*     */ 
/*     */               
/*     */               public void forEachRemaining(DoubleConsumer action) {
/* 266 */                 this.i.forEachRemaining(entry -> action.accept(entry.getDoubleKey()));
/*     */               }
/*     */             };
/*     */         }
/*     */ 
/*     */         
/*     */         public DoubleSpliterator spliterator() {
/* 273 */           return DoubleSpliterators.asSpliterator(iterator(), Size64.sizeOf(AbstractDouble2ObjectMap.this), 321);
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
/*     */   public ObjectCollection<V> values() {
/* 292 */     return (ObjectCollection<V>)new AbstractObjectCollection<V>()
/*     */       {
/*     */         public boolean contains(Object k) {
/* 295 */           return AbstractDouble2ObjectMap.this.containsValue(k);
/*     */         }
/*     */ 
/*     */         
/*     */         public int size() {
/* 300 */           return AbstractDouble2ObjectMap.this.size();
/*     */         }
/*     */ 
/*     */         
/*     */         public void clear() {
/* 305 */           AbstractDouble2ObjectMap.this.clear();
/*     */         }
/*     */ 
/*     */         
/*     */         public ObjectIterator<V> iterator() {
/* 310 */           return new ObjectIterator<V>() {
/* 311 */               private final ObjectIterator<Double2ObjectMap.Entry<V>> i = Double2ObjectMaps.fastIterator(AbstractDouble2ObjectMap.this);
/*     */ 
/*     */               
/*     */               public V next() {
/* 315 */                 return ((Double2ObjectMap.Entry<V>)this.i.next()).getValue();
/*     */               }
/*     */ 
/*     */               
/*     */               public boolean hasNext() {
/* 320 */                 return this.i.hasNext();
/*     */               }
/*     */ 
/*     */               
/*     */               public void remove() {
/* 325 */                 this.i.remove();
/*     */               }
/*     */ 
/*     */               
/*     */               public void forEachRemaining(Consumer<? super V> action) {
/* 330 */                 this.i.forEachRemaining(entry -> action.accept(entry.getValue()));
/*     */               }
/*     */             };
/*     */         }
/*     */ 
/*     */         
/*     */         public ObjectSpliterator<V> spliterator() {
/* 337 */           return ObjectSpliterators.asSpliterator(iterator(), Size64.sizeOf(AbstractDouble2ObjectMap.this), 64);
/*     */         }
/*     */       };
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void putAll(Map<? extends Double, ? extends V> m) {
/* 346 */     if (m instanceof Double2ObjectMap) {
/* 347 */       ObjectIterator<Double2ObjectMap.Entry<V>> i = Double2ObjectMaps.fastIterator((Double2ObjectMap)m);
/* 348 */       while (i.hasNext()) {
/* 349 */         Double2ObjectMap.Entry<? extends V> e = (Double2ObjectMap.Entry<? extends V>)i.next();
/* 350 */         put(e.getDoubleKey(), e.getValue());
/*     */       } 
/*     */     } else {
/* 353 */       int n = m.size();
/* 354 */       Iterator<? extends Map.Entry<? extends Double, ? extends V>> i = m.entrySet().iterator();
/*     */       
/* 356 */       while (n-- != 0) {
/* 357 */         Map.Entry<? extends Double, ? extends V> e = i.next();
/* 358 */         put(e.getKey(), e.getValue());
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
/* 372 */     int h = 0, n = size();
/* 373 */     ObjectIterator<Double2ObjectMap.Entry<V>> i = Double2ObjectMaps.fastIterator(this);
/* 374 */     for (; n-- != 0; h += ((Double2ObjectMap.Entry)i.next()).hashCode());
/* 375 */     return h;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean equals(Object o) {
/* 380 */     if (o == this) return true; 
/* 381 */     if (!(o instanceof Map)) return false; 
/* 382 */     Map<?, ?> m = (Map<?, ?>)o;
/* 383 */     if (m.size() != size()) return false; 
/* 384 */     return double2ObjectEntrySet().containsAll(m.entrySet());
/*     */   }
/*     */ 
/*     */   
/*     */   public String toString() {
/* 389 */     StringBuilder s = new StringBuilder();
/* 390 */     ObjectIterator<Double2ObjectMap.Entry<V>> i = Double2ObjectMaps.fastIterator(this);
/* 391 */     int n = size();
/*     */     
/* 393 */     boolean first = true;
/* 394 */     s.append("{");
/* 395 */     while (n-- != 0) {
/* 396 */       if (first) { first = false; }
/* 397 */       else { s.append(", "); }
/* 398 */        Double2ObjectMap.Entry<V> e = (Double2ObjectMap.Entry<V>)i.next();
/* 399 */       s.append(String.valueOf(e.getDoubleKey()));
/* 400 */       s.append("=>");
/* 401 */       if (this == e.getValue()) { s.append("(this map)"); continue; }
/* 402 */        s.append(String.valueOf(e.getValue()));
/*     */     } 
/* 404 */     s.append("}");
/* 405 */     return s.toString();
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\i\\unimi\dsi\fastutil\doubles\AbstractDouble2ObjectMap.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */