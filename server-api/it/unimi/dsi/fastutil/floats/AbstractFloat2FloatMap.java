/*     */ package it.unimi.dsi.fastutil.floats;
/*     */ 
/*     */ import it.unimi.dsi.fastutil.HashCommon;
/*     */ import it.unimi.dsi.fastutil.Size64;
/*     */ import it.unimi.dsi.fastutil.objects.AbstractObjectSet;
/*     */ import it.unimi.dsi.fastutil.objects.ObjectIterator;
/*     */ import it.unimi.dsi.fastutil.objects.ObjectSpliterator;
/*     */ import it.unimi.dsi.fastutil.objects.ObjectSpliterators;
/*     */ import java.io.Serializable;
/*     */ import java.util.Collection;
/*     */ import java.util.Iterator;
/*     */ import java.util.Map;
/*     */ import java.util.Set;
/*     */ import java.util.Spliterator;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public abstract class AbstractFloat2FloatMap
/*     */   extends AbstractFloat2FloatFunction
/*     */   implements Float2FloatMap, Serializable
/*     */ {
/*     */   private static final long serialVersionUID = -4940583368468432370L;
/*     */   
/*     */   public boolean containsKey(float k) {
/*  60 */     ObjectIterator<Float2FloatMap.Entry> i = float2FloatEntrySet().iterator();
/*  61 */     while (i.hasNext()) { if (((Float2FloatMap.Entry)i.next()).getFloatKey() == k) return true;  }
/*  62 */      return false;
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
/*  80 */     ObjectIterator<Float2FloatMap.Entry> i = float2FloatEntrySet().iterator();
/*  81 */     while (i.hasNext()) { if (((Float2FloatMap.Entry)i.next()).getFloatValue() == v) return true;  }
/*  82 */      return false;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isEmpty() {
/*  87 */     return (size() == 0);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public static class BasicEntry
/*     */     implements Float2FloatMap.Entry
/*     */   {
/*     */     protected float key;
/*     */ 
/*     */     
/*     */     protected float value;
/*     */ 
/*     */ 
/*     */     
/*     */     public BasicEntry() {}
/*     */ 
/*     */     
/*     */     public BasicEntry(Float key, Float value) {
/* 106 */       this.key = key.floatValue();
/* 107 */       this.value = value.floatValue();
/*     */     }
/*     */     
/*     */     public BasicEntry(float key, float value) {
/* 111 */       this.key = key;
/* 112 */       this.value = value;
/*     */     }
/*     */ 
/*     */     
/*     */     public float getFloatKey() {
/* 117 */       return this.key;
/*     */     }
/*     */ 
/*     */     
/*     */     public float getFloatValue() {
/* 122 */       return this.value;
/*     */     }
/*     */ 
/*     */     
/*     */     public float setValue(float value) {
/* 127 */       throw new UnsupportedOperationException();
/*     */     }
/*     */ 
/*     */     
/*     */     public boolean equals(Object o) {
/* 132 */       if (!(o instanceof Map.Entry)) return false; 
/* 133 */       if (o instanceof Float2FloatMap.Entry) {
/* 134 */         Float2FloatMap.Entry entry = (Float2FloatMap.Entry)o;
/* 135 */         return (Float.floatToIntBits(this.key) == Float.floatToIntBits(entry.getFloatKey()) && Float.floatToIntBits(this.value) == Float.floatToIntBits(entry.getFloatValue()));
/*     */       } 
/* 137 */       Map.Entry<?, ?> e = (Map.Entry<?, ?>)o;
/* 138 */       Object key = e.getKey();
/* 139 */       if (key == null || !(key instanceof Float)) return false; 
/* 140 */       Object value = e.getValue();
/* 141 */       if (value == null || !(value instanceof Float)) return false; 
/* 142 */       return (Float.floatToIntBits(this.key) == Float.floatToIntBits(((Float)key).floatValue()) && Float.floatToIntBits(this.value) == Float.floatToIntBits(((Float)value).floatValue()));
/*     */     }
/*     */ 
/*     */     
/*     */     public int hashCode() {
/* 147 */       return HashCommon.float2int(this.key) ^ HashCommon.float2int(this.value);
/*     */     }
/*     */ 
/*     */     
/*     */     public String toString() {
/* 152 */       return this.key + "->" + this.value;
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   public static abstract class BasicEntrySet
/*     */     extends AbstractObjectSet<Float2FloatMap.Entry>
/*     */   {
/*     */     protected final Float2FloatMap map;
/*     */ 
/*     */     
/*     */     public BasicEntrySet(Float2FloatMap map) {
/* 164 */       this.map = map;
/*     */     }
/*     */ 
/*     */     
/*     */     public boolean contains(Object o) {
/* 169 */       if (!(o instanceof Map.Entry)) return false; 
/* 170 */       if (o instanceof Float2FloatMap.Entry) {
/* 171 */         Float2FloatMap.Entry entry = (Float2FloatMap.Entry)o;
/* 172 */         float f = entry.getFloatKey();
/* 173 */         return (this.map.containsKey(f) && Float.floatToIntBits(this.map.get(f)) == Float.floatToIntBits(entry.getFloatValue()));
/*     */       } 
/* 175 */       Map.Entry<?, ?> e = (Map.Entry<?, ?>)o;
/* 176 */       Object key = e.getKey();
/* 177 */       if (key == null || !(key instanceof Float)) return false; 
/* 178 */       float k = ((Float)key).floatValue();
/* 179 */       Object value = e.getValue();
/* 180 */       if (value == null || !(value instanceof Float)) return false; 
/* 181 */       return (this.map.containsKey(k) && Float.floatToIntBits(this.map.get(k)) == Float.floatToIntBits(((Float)value).floatValue()));
/*     */     }
/*     */ 
/*     */     
/*     */     public boolean remove(Object o) {
/* 186 */       if (!(o instanceof Map.Entry)) return false; 
/* 187 */       if (o instanceof Float2FloatMap.Entry) {
/* 188 */         Float2FloatMap.Entry entry = (Float2FloatMap.Entry)o;
/* 189 */         return this.map.remove(entry.getFloatKey(), entry.getFloatValue());
/*     */       } 
/* 191 */       Map.Entry<?, ?> e = (Map.Entry<?, ?>)o;
/* 192 */       Object key = e.getKey();
/* 193 */       if (key == null || !(key instanceof Float)) return false; 
/* 194 */       float k = ((Float)key).floatValue();
/* 195 */       Object value = e.getValue();
/* 196 */       if (value == null || !(value instanceof Float)) return false; 
/* 197 */       float v = ((Float)value).floatValue();
/* 198 */       return this.map.remove(k, v);
/*     */     }
/*     */ 
/*     */     
/*     */     public int size() {
/* 203 */       return this.map.size();
/*     */     }
/*     */ 
/*     */     
/*     */     public ObjectSpliterator<Float2FloatMap.Entry> spliterator() {
/* 208 */       return ObjectSpliterators.asSpliterator(iterator(), Size64.sizeOf(this.map), 65);
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
/* 226 */     return new AbstractFloatSet()
/*     */       {
/*     */         public boolean contains(float k) {
/* 229 */           return AbstractFloat2FloatMap.this.containsKey(k);
/*     */         }
/*     */ 
/*     */         
/*     */         public int size() {
/* 234 */           return AbstractFloat2FloatMap.this.size();
/*     */         }
/*     */ 
/*     */         
/*     */         public void clear() {
/* 239 */           AbstractFloat2FloatMap.this.clear();
/*     */         }
/*     */ 
/*     */         
/*     */         public FloatIterator iterator() {
/* 244 */           return new FloatIterator() {
/* 245 */               private final ObjectIterator<Float2FloatMap.Entry> i = Float2FloatMaps.fastIterator(AbstractFloat2FloatMap.this);
/*     */ 
/*     */               
/*     */               public float nextFloat() {
/* 249 */                 return ((Float2FloatMap.Entry)this.i.next()).getFloatKey();
/*     */               }
/*     */ 
/*     */               
/*     */               public boolean hasNext() {
/* 254 */                 return this.i.hasNext();
/*     */               }
/*     */ 
/*     */               
/*     */               public void remove() {
/* 259 */                 this.i.remove();
/*     */               }
/*     */ 
/*     */               
/*     */               public void forEachRemaining(FloatConsumer action) {
/* 264 */                 this.i.forEachRemaining(entry -> action.accept(entry.getFloatKey()));
/*     */               }
/*     */             };
/*     */         }
/*     */ 
/*     */         
/*     */         public FloatSpliterator spliterator() {
/* 271 */           return FloatSpliterators.asSpliterator(iterator(), Size64.sizeOf(AbstractFloat2FloatMap.this), 321);
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
/* 290 */     return new AbstractFloatCollection()
/*     */       {
/*     */         public boolean contains(float k) {
/* 293 */           return AbstractFloat2FloatMap.this.containsValue(k);
/*     */         }
/*     */ 
/*     */         
/*     */         public int size() {
/* 298 */           return AbstractFloat2FloatMap.this.size();
/*     */         }
/*     */ 
/*     */         
/*     */         public void clear() {
/* 303 */           AbstractFloat2FloatMap.this.clear();
/*     */         }
/*     */ 
/*     */         
/*     */         public FloatIterator iterator() {
/* 308 */           return new FloatIterator() {
/* 309 */               private final ObjectIterator<Float2FloatMap.Entry> i = Float2FloatMaps.fastIterator(AbstractFloat2FloatMap.this);
/*     */ 
/*     */               
/*     */               public float nextFloat() {
/* 313 */                 return ((Float2FloatMap.Entry)this.i.next()).getFloatValue();
/*     */               }
/*     */ 
/*     */               
/*     */               public boolean hasNext() {
/* 318 */                 return this.i.hasNext();
/*     */               }
/*     */ 
/*     */               
/*     */               public void remove() {
/* 323 */                 this.i.remove();
/*     */               }
/*     */ 
/*     */               
/*     */               public void forEachRemaining(FloatConsumer action) {
/* 328 */                 this.i.forEachRemaining(entry -> action.accept(entry.getFloatValue()));
/*     */               }
/*     */             };
/*     */         }
/*     */ 
/*     */         
/*     */         public FloatSpliterator spliterator() {
/* 335 */           return FloatSpliterators.asSpliterator(iterator(), Size64.sizeOf(AbstractFloat2FloatMap.this), 320);
/*     */         }
/*     */       };
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void putAll(Map<? extends Float, ? extends Float> m) {
/* 344 */     if (m instanceof Float2FloatMap) {
/* 345 */       ObjectIterator<Float2FloatMap.Entry> i = Float2FloatMaps.fastIterator((Float2FloatMap)m);
/* 346 */       while (i.hasNext()) {
/* 347 */         Float2FloatMap.Entry e = (Float2FloatMap.Entry)i.next();
/* 348 */         put(e.getFloatKey(), e.getFloatValue());
/*     */       } 
/*     */     } else {
/* 351 */       int n = m.size();
/* 352 */       Iterator<? extends Map.Entry<? extends Float, ? extends Float>> i = m.entrySet().iterator();
/*     */       
/* 354 */       while (n-- != 0) {
/* 355 */         Map.Entry<? extends Float, ? extends Float> e = i.next();
/* 356 */         put(e.getKey(), e.getValue());
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
/* 370 */     int h = 0, n = size();
/* 371 */     ObjectIterator<Float2FloatMap.Entry> i = Float2FloatMaps.fastIterator(this);
/* 372 */     for (; n-- != 0; h += ((Float2FloatMap.Entry)i.next()).hashCode());
/* 373 */     return h;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean equals(Object o) {
/* 378 */     if (o == this) return true; 
/* 379 */     if (!(o instanceof Map)) return false; 
/* 380 */     Map<?, ?> m = (Map<?, ?>)o;
/* 381 */     if (m.size() != size()) return false; 
/* 382 */     return float2FloatEntrySet().containsAll(m.entrySet());
/*     */   }
/*     */ 
/*     */   
/*     */   public String toString() {
/* 387 */     StringBuilder s = new StringBuilder();
/* 388 */     ObjectIterator<Float2FloatMap.Entry> i = Float2FloatMaps.fastIterator(this);
/* 389 */     int n = size();
/*     */     
/* 391 */     boolean first = true;
/* 392 */     s.append("{");
/* 393 */     while (n-- != 0) {
/* 394 */       if (first) { first = false; }
/* 395 */       else { s.append(", "); }
/* 396 */        Float2FloatMap.Entry e = (Float2FloatMap.Entry)i.next();
/* 397 */       s.append(String.valueOf(e.getFloatKey()));
/* 398 */       s.append("=>");
/* 399 */       s.append(String.valueOf(e.getFloatValue()));
/*     */     } 
/* 401 */     s.append("}");
/* 402 */     return s.toString();
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\i\\unimi\dsi\fastutil\floats\AbstractFloat2FloatMap.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */