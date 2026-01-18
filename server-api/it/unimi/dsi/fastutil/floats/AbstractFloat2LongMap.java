/*     */ package it.unimi.dsi.fastutil.floats;
/*     */ 
/*     */ import it.unimi.dsi.fastutil.HashCommon;
/*     */ import it.unimi.dsi.fastutil.Size64;
/*     */ import it.unimi.dsi.fastutil.longs.AbstractLongCollection;
/*     */ import it.unimi.dsi.fastutil.longs.LongBinaryOperator;
/*     */ import it.unimi.dsi.fastutil.longs.LongCollection;
/*     */ import it.unimi.dsi.fastutil.longs.LongIterator;
/*     */ import it.unimi.dsi.fastutil.longs.LongSpliterator;
/*     */ import it.unimi.dsi.fastutil.longs.LongSpliterators;
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
/*     */ public abstract class AbstractFloat2LongMap
/*     */   extends AbstractFloat2LongFunction
/*     */   implements Float2LongMap, Serializable
/*     */ {
/*     */   private static final long serialVersionUID = -4940583368468432370L;
/*     */   
/*     */   public boolean containsKey(float k) {
/*  65 */     ObjectIterator<Float2LongMap.Entry> i = float2LongEntrySet().iterator();
/*  66 */     while (i.hasNext()) { if (((Float2LongMap.Entry)i.next()).getFloatKey() == k) return true;  }
/*  67 */      return false;
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
/*  85 */     ObjectIterator<Float2LongMap.Entry> i = float2LongEntrySet().iterator();
/*  86 */     while (i.hasNext()) { if (((Float2LongMap.Entry)i.next()).getLongValue() == v) return true;  }
/*  87 */      return false;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isEmpty() {
/*  92 */     return (size() == 0);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public final long mergeLong(float key, long value, LongBinaryOperator remappingFunction) {
/* 103 */     return mergeLong(key, value, (LongBinaryOperator)remappingFunction);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public static class BasicEntry
/*     */     implements Float2LongMap.Entry
/*     */   {
/*     */     protected float key;
/*     */ 
/*     */     
/*     */     protected long value;
/*     */ 
/*     */ 
/*     */     
/*     */     public BasicEntry() {}
/*     */ 
/*     */     
/*     */     public BasicEntry(Float key, Long value) {
/* 122 */       this.key = key.floatValue();
/* 123 */       this.value = value.longValue();
/*     */     }
/*     */     
/*     */     public BasicEntry(float key, long value) {
/* 127 */       this.key = key;
/* 128 */       this.value = value;
/*     */     }
/*     */ 
/*     */     
/*     */     public float getFloatKey() {
/* 133 */       return this.key;
/*     */     }
/*     */ 
/*     */     
/*     */     public long getLongValue() {
/* 138 */       return this.value;
/*     */     }
/*     */ 
/*     */     
/*     */     public long setValue(long value) {
/* 143 */       throw new UnsupportedOperationException();
/*     */     }
/*     */ 
/*     */     
/*     */     public boolean equals(Object o) {
/* 148 */       if (!(o instanceof Map.Entry)) return false; 
/* 149 */       if (o instanceof Float2LongMap.Entry) {
/* 150 */         Float2LongMap.Entry entry = (Float2LongMap.Entry)o;
/* 151 */         return (Float.floatToIntBits(this.key) == Float.floatToIntBits(entry.getFloatKey()) && this.value == entry.getLongValue());
/*     */       } 
/* 153 */       Map.Entry<?, ?> e = (Map.Entry<?, ?>)o;
/* 154 */       Object key = e.getKey();
/* 155 */       if (key == null || !(key instanceof Float)) return false; 
/* 156 */       Object value = e.getValue();
/* 157 */       if (value == null || !(value instanceof Long)) return false; 
/* 158 */       return (Float.floatToIntBits(this.key) == Float.floatToIntBits(((Float)key).floatValue()) && this.value == ((Long)value).longValue());
/*     */     }
/*     */ 
/*     */     
/*     */     public int hashCode() {
/* 163 */       return HashCommon.float2int(this.key) ^ HashCommon.long2int(this.value);
/*     */     }
/*     */ 
/*     */     
/*     */     public String toString() {
/* 168 */       return this.key + "->" + this.value;
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   public static abstract class BasicEntrySet
/*     */     extends AbstractObjectSet<Float2LongMap.Entry>
/*     */   {
/*     */     protected final Float2LongMap map;
/*     */ 
/*     */     
/*     */     public BasicEntrySet(Float2LongMap map) {
/* 180 */       this.map = map;
/*     */     }
/*     */ 
/*     */     
/*     */     public boolean contains(Object o) {
/* 185 */       if (!(o instanceof Map.Entry)) return false; 
/* 186 */       if (o instanceof Float2LongMap.Entry) {
/* 187 */         Float2LongMap.Entry entry = (Float2LongMap.Entry)o;
/* 188 */         float f = entry.getFloatKey();
/* 189 */         return (this.map.containsKey(f) && this.map.get(f) == entry.getLongValue());
/*     */       } 
/* 191 */       Map.Entry<?, ?> e = (Map.Entry<?, ?>)o;
/* 192 */       Object key = e.getKey();
/* 193 */       if (key == null || !(key instanceof Float)) return false; 
/* 194 */       float k = ((Float)key).floatValue();
/* 195 */       Object value = e.getValue();
/* 196 */       if (value == null || !(value instanceof Long)) return false; 
/* 197 */       return (this.map.containsKey(k) && this.map.get(k) == ((Long)value).longValue());
/*     */     }
/*     */ 
/*     */     
/*     */     public boolean remove(Object o) {
/* 202 */       if (!(o instanceof Map.Entry)) return false; 
/* 203 */       if (o instanceof Float2LongMap.Entry) {
/* 204 */         Float2LongMap.Entry entry = (Float2LongMap.Entry)o;
/* 205 */         return this.map.remove(entry.getFloatKey(), entry.getLongValue());
/*     */       } 
/* 207 */       Map.Entry<?, ?> e = (Map.Entry<?, ?>)o;
/* 208 */       Object key = e.getKey();
/* 209 */       if (key == null || !(key instanceof Float)) return false; 
/* 210 */       float k = ((Float)key).floatValue();
/* 211 */       Object value = e.getValue();
/* 212 */       if (value == null || !(value instanceof Long)) return false; 
/* 213 */       long v = ((Long)value).longValue();
/* 214 */       return this.map.remove(k, v);
/*     */     }
/*     */ 
/*     */     
/*     */     public int size() {
/* 219 */       return this.map.size();
/*     */     }
/*     */ 
/*     */     
/*     */     public ObjectSpliterator<Float2LongMap.Entry> spliterator() {
/* 224 */       return ObjectSpliterators.asSpliterator(iterator(), Size64.sizeOf(this.map), 65);
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
/* 242 */     return new AbstractFloatSet()
/*     */       {
/*     */         public boolean contains(float k) {
/* 245 */           return AbstractFloat2LongMap.this.containsKey(k);
/*     */         }
/*     */ 
/*     */         
/*     */         public int size() {
/* 250 */           return AbstractFloat2LongMap.this.size();
/*     */         }
/*     */ 
/*     */         
/*     */         public void clear() {
/* 255 */           AbstractFloat2LongMap.this.clear();
/*     */         }
/*     */ 
/*     */         
/*     */         public FloatIterator iterator() {
/* 260 */           return new FloatIterator() {
/* 261 */               private final ObjectIterator<Float2LongMap.Entry> i = Float2LongMaps.fastIterator(AbstractFloat2LongMap.this);
/*     */ 
/*     */               
/*     */               public float nextFloat() {
/* 265 */                 return ((Float2LongMap.Entry)this.i.next()).getFloatKey();
/*     */               }
/*     */ 
/*     */               
/*     */               public boolean hasNext() {
/* 270 */                 return this.i.hasNext();
/*     */               }
/*     */ 
/*     */               
/*     */               public void remove() {
/* 275 */                 this.i.remove();
/*     */               }
/*     */ 
/*     */               
/*     */               public void forEachRemaining(FloatConsumer action) {
/* 280 */                 this.i.forEachRemaining(entry -> action.accept(entry.getFloatKey()));
/*     */               }
/*     */             };
/*     */         }
/*     */ 
/*     */         
/*     */         public FloatSpliterator spliterator() {
/* 287 */           return FloatSpliterators.asSpliterator(iterator(), Size64.sizeOf(AbstractFloat2LongMap.this), 321);
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
/* 306 */     return (LongCollection)new AbstractLongCollection()
/*     */       {
/*     */         public boolean contains(long k) {
/* 309 */           return AbstractFloat2LongMap.this.containsValue(k);
/*     */         }
/*     */ 
/*     */         
/*     */         public int size() {
/* 314 */           return AbstractFloat2LongMap.this.size();
/*     */         }
/*     */ 
/*     */         
/*     */         public void clear() {
/* 319 */           AbstractFloat2LongMap.this.clear();
/*     */         }
/*     */ 
/*     */         
/*     */         public LongIterator iterator() {
/* 324 */           return new LongIterator() {
/* 325 */               private final ObjectIterator<Float2LongMap.Entry> i = Float2LongMaps.fastIterator(AbstractFloat2LongMap.this);
/*     */ 
/*     */               
/*     */               public long nextLong() {
/* 329 */                 return ((Float2LongMap.Entry)this.i.next()).getLongValue();
/*     */               }
/*     */ 
/*     */               
/*     */               public boolean hasNext() {
/* 334 */                 return this.i.hasNext();
/*     */               }
/*     */ 
/*     */               
/*     */               public void remove() {
/* 339 */                 this.i.remove();
/*     */               }
/*     */ 
/*     */               
/*     */               public void forEachRemaining(LongConsumer action) {
/* 344 */                 this.i.forEachRemaining(entry -> action.accept(entry.getLongValue()));
/*     */               }
/*     */             };
/*     */         }
/*     */ 
/*     */         
/*     */         public LongSpliterator spliterator() {
/* 351 */           return LongSpliterators.asSpliterator(iterator(), Size64.sizeOf(AbstractFloat2LongMap.this), 320);
/*     */         }
/*     */       };
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void putAll(Map<? extends Float, ? extends Long> m) {
/* 360 */     if (m instanceof Float2LongMap) {
/* 361 */       ObjectIterator<Float2LongMap.Entry> i = Float2LongMaps.fastIterator((Float2LongMap)m);
/* 362 */       while (i.hasNext()) {
/* 363 */         Float2LongMap.Entry e = (Float2LongMap.Entry)i.next();
/* 364 */         put(e.getFloatKey(), e.getLongValue());
/*     */       } 
/*     */     } else {
/* 367 */       int n = m.size();
/* 368 */       Iterator<? extends Map.Entry<? extends Float, ? extends Long>> i = m.entrySet().iterator();
/*     */       
/* 370 */       while (n-- != 0) {
/* 371 */         Map.Entry<? extends Float, ? extends Long> e = i.next();
/* 372 */         put(e.getKey(), e.getValue());
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
/* 386 */     int h = 0, n = size();
/* 387 */     ObjectIterator<Float2LongMap.Entry> i = Float2LongMaps.fastIterator(this);
/* 388 */     for (; n-- != 0; h += ((Float2LongMap.Entry)i.next()).hashCode());
/* 389 */     return h;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean equals(Object o) {
/* 394 */     if (o == this) return true; 
/* 395 */     if (!(o instanceof Map)) return false; 
/* 396 */     Map<?, ?> m = (Map<?, ?>)o;
/* 397 */     if (m.size() != size()) return false; 
/* 398 */     return float2LongEntrySet().containsAll(m.entrySet());
/*     */   }
/*     */ 
/*     */   
/*     */   public String toString() {
/* 403 */     StringBuilder s = new StringBuilder();
/* 404 */     ObjectIterator<Float2LongMap.Entry> i = Float2LongMaps.fastIterator(this);
/* 405 */     int n = size();
/*     */     
/* 407 */     boolean first = true;
/* 408 */     s.append("{");
/* 409 */     while (n-- != 0) {
/* 410 */       if (first) { first = false; }
/* 411 */       else { s.append(", "); }
/* 412 */        Float2LongMap.Entry e = (Float2LongMap.Entry)i.next();
/* 413 */       s.append(String.valueOf(e.getFloatKey()));
/* 414 */       s.append("=>");
/* 415 */       s.append(String.valueOf(e.getLongValue()));
/*     */     } 
/* 417 */     s.append("}");
/* 418 */     return s.toString();
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\i\\unimi\dsi\fastutil\floats\AbstractFloat2LongMap.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */