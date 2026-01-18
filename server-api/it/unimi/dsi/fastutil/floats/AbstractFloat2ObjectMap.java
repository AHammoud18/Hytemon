/*     */ package it.unimi.dsi.fastutil.floats;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public abstract class AbstractFloat2ObjectMap<V>
/*     */   extends AbstractFloat2ObjectFunction<V>
/*     */   implements Float2ObjectMap<V>, Serializable
/*     */ {
/*     */   private static final long serialVersionUID = -4940583368468432370L;
/*     */   
/*     */   public boolean containsKey(float k) {
/*  63 */     ObjectIterator<Float2ObjectMap.Entry<V>> i = float2ObjectEntrySet().iterator();
/*  64 */     while (i.hasNext()) { if (((Float2ObjectMap.Entry)i.next()).getFloatKey() == k) return true;  }
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
/*  83 */     ObjectIterator<Float2ObjectMap.Entry<V>> i = float2ObjectEntrySet().iterator();
/*  84 */     while (i.hasNext()) { if (((Float2ObjectMap.Entry)i.next()).getValue() == v) return true;  }
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
/*     */     implements Float2ObjectMap.Entry<V>
/*     */   {
/*     */     protected float key;
/*     */ 
/*     */     
/*     */     protected V value;
/*     */ 
/*     */ 
/*     */     
/*     */     public BasicEntry() {}
/*     */ 
/*     */     
/*     */     public BasicEntry(Float key, V value) {
/* 109 */       this.key = key.floatValue();
/* 110 */       this.value = value;
/*     */     }
/*     */     
/*     */     public BasicEntry(float key, V value) {
/* 114 */       this.key = key;
/* 115 */       this.value = value;
/*     */     }
/*     */ 
/*     */     
/*     */     public float getFloatKey() {
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
/* 137 */       if (o instanceof Float2ObjectMap.Entry) {
/* 138 */         Float2ObjectMap.Entry<V> entry = (Float2ObjectMap.Entry<V>)o;
/* 139 */         return (Float.floatToIntBits(this.key) == Float.floatToIntBits(entry.getFloatKey()) && Objects.equals(this.value, entry.getValue()));
/*     */       } 
/* 141 */       Map.Entry<?, ?> e = (Map.Entry<?, ?>)o;
/* 142 */       Object key = e.getKey();
/* 143 */       if (key == null || !(key instanceof Float)) return false; 
/* 144 */       Object value = e.getValue();
/* 145 */       return (Float.floatToIntBits(this.key) == Float.floatToIntBits(((Float)key).floatValue()) && Objects.equals(this.value, value));
/*     */     }
/*     */ 
/*     */     
/*     */     public int hashCode() {
/* 150 */       return HashCommon.float2int(this.key) ^ ((this.value == null) ? 0 : this.value.hashCode());
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
/*     */     extends AbstractObjectSet<Float2ObjectMap.Entry<V>>
/*     */   {
/*     */     protected final Float2ObjectMap<V> map;
/*     */ 
/*     */     
/*     */     public BasicEntrySet(Float2ObjectMap<V> map) {
/* 167 */       this.map = map;
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     public boolean contains(Object o) {
/* 173 */       if (!(o instanceof Map.Entry)) return false; 
/* 174 */       if (o instanceof Float2ObjectMap.Entry) {
/* 175 */         Float2ObjectMap.Entry<V> entry = (Float2ObjectMap.Entry<V>)o;
/* 176 */         float f = entry.getFloatKey();
/* 177 */         return (this.map.containsKey(f) && Objects.equals(this.map.get(f), entry.getValue()));
/*     */       } 
/* 179 */       Map.Entry<?, ?> e = (Map.Entry<?, ?>)o;
/* 180 */       Object key = e.getKey();
/* 181 */       if (key == null || !(key instanceof Float)) return false; 
/* 182 */       float k = ((Float)key).floatValue();
/* 183 */       Object value = e.getValue();
/* 184 */       return (this.map.containsKey(k) && Objects.equals(this.map.get(k), value));
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     public boolean remove(Object o) {
/* 190 */       if (!(o instanceof Map.Entry)) return false; 
/* 191 */       if (o instanceof Float2ObjectMap.Entry) {
/* 192 */         Float2ObjectMap.Entry<V> entry = (Float2ObjectMap.Entry<V>)o;
/* 193 */         return this.map.remove(entry.getFloatKey(), entry.getValue());
/*     */       } 
/* 195 */       Map.Entry<?, ?> e = (Map.Entry<?, ?>)o;
/* 196 */       Object key = e.getKey();
/* 197 */       if (key == null || !(key instanceof Float)) return false; 
/* 198 */       float k = ((Float)key).floatValue();
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
/*     */     public ObjectSpliterator<Float2ObjectMap.Entry<V>> spliterator() {
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
/*     */   public FloatSet keySet() {
/* 228 */     return new AbstractFloatSet()
/*     */       {
/*     */         public boolean contains(float k) {
/* 231 */           return AbstractFloat2ObjectMap.this.containsKey(k);
/*     */         }
/*     */ 
/*     */         
/*     */         public int size() {
/* 236 */           return AbstractFloat2ObjectMap.this.size();
/*     */         }
/*     */ 
/*     */         
/*     */         public void clear() {
/* 241 */           AbstractFloat2ObjectMap.this.clear();
/*     */         }
/*     */ 
/*     */         
/*     */         public FloatIterator iterator() {
/* 246 */           return new FloatIterator() {
/* 247 */               private final ObjectIterator<Float2ObjectMap.Entry<V>> i = Float2ObjectMaps.fastIterator(AbstractFloat2ObjectMap.this);
/*     */ 
/*     */               
/*     */               public float nextFloat() {
/* 251 */                 return ((Float2ObjectMap.Entry)this.i.next()).getFloatKey();
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
/*     */               public void forEachRemaining(FloatConsumer action) {
/* 266 */                 this.i.forEachRemaining(entry -> action.accept(entry.getFloatKey()));
/*     */               }
/*     */             };
/*     */         }
/*     */ 
/*     */         
/*     */         public FloatSpliterator spliterator() {
/* 273 */           return FloatSpliterators.asSpliterator(iterator(), Size64.sizeOf(AbstractFloat2ObjectMap.this), 321);
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
/* 295 */           return AbstractFloat2ObjectMap.this.containsValue(k);
/*     */         }
/*     */ 
/*     */         
/*     */         public int size() {
/* 300 */           return AbstractFloat2ObjectMap.this.size();
/*     */         }
/*     */ 
/*     */         
/*     */         public void clear() {
/* 305 */           AbstractFloat2ObjectMap.this.clear();
/*     */         }
/*     */ 
/*     */         
/*     */         public ObjectIterator<V> iterator() {
/* 310 */           return new ObjectIterator<V>() {
/* 311 */               private final ObjectIterator<Float2ObjectMap.Entry<V>> i = Float2ObjectMaps.fastIterator(AbstractFloat2ObjectMap.this);
/*     */ 
/*     */               
/*     */               public V next() {
/* 315 */                 return ((Float2ObjectMap.Entry<V>)this.i.next()).getValue();
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
/* 337 */           return ObjectSpliterators.asSpliterator(iterator(), Size64.sizeOf(AbstractFloat2ObjectMap.this), 64);
/*     */         }
/*     */       };
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void putAll(Map<? extends Float, ? extends V> m) {
/* 346 */     if (m instanceof Float2ObjectMap) {
/* 347 */       ObjectIterator<Float2ObjectMap.Entry<V>> i = Float2ObjectMaps.fastIterator((Float2ObjectMap)m);
/* 348 */       while (i.hasNext()) {
/* 349 */         Float2ObjectMap.Entry<? extends V> e = (Float2ObjectMap.Entry<? extends V>)i.next();
/* 350 */         put(e.getFloatKey(), e.getValue());
/*     */       } 
/*     */     } else {
/* 353 */       int n = m.size();
/* 354 */       Iterator<? extends Map.Entry<? extends Float, ? extends V>> i = m.entrySet().iterator();
/*     */       
/* 356 */       while (n-- != 0) {
/* 357 */         Map.Entry<? extends Float, ? extends V> e = i.next();
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
/* 373 */     ObjectIterator<Float2ObjectMap.Entry<V>> i = Float2ObjectMaps.fastIterator(this);
/* 374 */     for (; n-- != 0; h += ((Float2ObjectMap.Entry)i.next()).hashCode());
/* 375 */     return h;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean equals(Object o) {
/* 380 */     if (o == this) return true; 
/* 381 */     if (!(o instanceof Map)) return false; 
/* 382 */     Map<?, ?> m = (Map<?, ?>)o;
/* 383 */     if (m.size() != size()) return false; 
/* 384 */     return float2ObjectEntrySet().containsAll(m.entrySet());
/*     */   }
/*     */ 
/*     */   
/*     */   public String toString() {
/* 389 */     StringBuilder s = new StringBuilder();
/* 390 */     ObjectIterator<Float2ObjectMap.Entry<V>> i = Float2ObjectMaps.fastIterator(this);
/* 391 */     int n = size();
/*     */     
/* 393 */     boolean first = true;
/* 394 */     s.append("{");
/* 395 */     while (n-- != 0) {
/* 396 */       if (first) { first = false; }
/* 397 */       else { s.append(", "); }
/* 398 */        Float2ObjectMap.Entry<V> e = (Float2ObjectMap.Entry<V>)i.next();
/* 399 */       s.append(String.valueOf(e.getFloatKey()));
/* 400 */       s.append("=>");
/* 401 */       if (this == e.getValue()) { s.append("(this map)"); continue; }
/* 402 */        s.append(String.valueOf(e.getValue()));
/*     */     } 
/* 404 */     s.append("}");
/* 405 */     return s.toString();
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\i\\unimi\dsi\fastutil\floats\AbstractFloat2ObjectMap.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */