/*     */ package it.unimi.dsi.fastutil.longs;
/*     */ 
/*     */ import it.unimi.dsi.fastutil.HashCommon;
/*     */ import it.unimi.dsi.fastutil.Size64;
/*     */ import it.unimi.dsi.fastutil.floats.AbstractFloatCollection;
/*     */ import it.unimi.dsi.fastutil.floats.FloatCollection;
/*     */ import it.unimi.dsi.fastutil.floats.FloatConsumer;
/*     */ import it.unimi.dsi.fastutil.floats.FloatIterator;
/*     */ import it.unimi.dsi.fastutil.floats.FloatSpliterator;
/*     */ import it.unimi.dsi.fastutil.floats.FloatSpliterators;
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
/*     */ 
/*     */ 
/*     */ public abstract class AbstractLong2FloatMap
/*     */   extends AbstractLong2FloatFunction
/*     */   implements Long2FloatMap, Serializable
/*     */ {
/*     */   private static final long serialVersionUID = -4940583368468432370L;
/*     */   
/*     */   public boolean containsKey(long k) {
/*  66 */     ObjectIterator<Long2FloatMap.Entry> i = long2FloatEntrySet().iterator();
/*  67 */     while (i.hasNext()) { if (((Long2FloatMap.Entry)i.next()).getLongKey() == k) return true;  }
/*  68 */      return false;
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
/*  86 */     ObjectIterator<Long2FloatMap.Entry> i = long2FloatEntrySet().iterator();
/*  87 */     while (i.hasNext()) { if (((Long2FloatMap.Entry)i.next()).getFloatValue() == v) return true;  }
/*  88 */      return false;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isEmpty() {
/*  93 */     return (size() == 0);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public static class BasicEntry
/*     */     implements Long2FloatMap.Entry
/*     */   {
/*     */     protected long key;
/*     */ 
/*     */     
/*     */     protected float value;
/*     */ 
/*     */ 
/*     */     
/*     */     public BasicEntry() {}
/*     */ 
/*     */     
/*     */     public BasicEntry(Long key, Float value) {
/* 112 */       this.key = key.longValue();
/* 113 */       this.value = value.floatValue();
/*     */     }
/*     */     
/*     */     public BasicEntry(long key, float value) {
/* 117 */       this.key = key;
/* 118 */       this.value = value;
/*     */     }
/*     */ 
/*     */     
/*     */     public long getLongKey() {
/* 123 */       return this.key;
/*     */     }
/*     */ 
/*     */     
/*     */     public float getFloatValue() {
/* 128 */       return this.value;
/*     */     }
/*     */ 
/*     */     
/*     */     public float setValue(float value) {
/* 133 */       throw new UnsupportedOperationException();
/*     */     }
/*     */ 
/*     */     
/*     */     public boolean equals(Object o) {
/* 138 */       if (!(o instanceof Map.Entry)) return false; 
/* 139 */       if (o instanceof Long2FloatMap.Entry) {
/* 140 */         Long2FloatMap.Entry entry = (Long2FloatMap.Entry)o;
/* 141 */         return (this.key == entry.getLongKey() && Float.floatToIntBits(this.value) == Float.floatToIntBits(entry.getFloatValue()));
/*     */       } 
/* 143 */       Map.Entry<?, ?> e = (Map.Entry<?, ?>)o;
/* 144 */       Object key = e.getKey();
/* 145 */       if (key == null || !(key instanceof Long)) return false; 
/* 146 */       Object value = e.getValue();
/* 147 */       if (value == null || !(value instanceof Float)) return false; 
/* 148 */       return (this.key == ((Long)key).longValue() && Float.floatToIntBits(this.value) == Float.floatToIntBits(((Float)value).floatValue()));
/*     */     }
/*     */ 
/*     */     
/*     */     public int hashCode() {
/* 153 */       return HashCommon.long2int(this.key) ^ HashCommon.float2int(this.value);
/*     */     }
/*     */ 
/*     */     
/*     */     public String toString() {
/* 158 */       return this.key + "->" + this.value;
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   public static abstract class BasicEntrySet
/*     */     extends AbstractObjectSet<Long2FloatMap.Entry>
/*     */   {
/*     */     protected final Long2FloatMap map;
/*     */ 
/*     */     
/*     */     public BasicEntrySet(Long2FloatMap map) {
/* 170 */       this.map = map;
/*     */     }
/*     */ 
/*     */     
/*     */     public boolean contains(Object o) {
/* 175 */       if (!(o instanceof Map.Entry)) return false; 
/* 176 */       if (o instanceof Long2FloatMap.Entry) {
/* 177 */         Long2FloatMap.Entry entry = (Long2FloatMap.Entry)o;
/* 178 */         long l = entry.getLongKey();
/* 179 */         return (this.map.containsKey(l) && Float.floatToIntBits(this.map.get(l)) == Float.floatToIntBits(entry.getFloatValue()));
/*     */       } 
/* 181 */       Map.Entry<?, ?> e = (Map.Entry<?, ?>)o;
/* 182 */       Object key = e.getKey();
/* 183 */       if (key == null || !(key instanceof Long)) return false; 
/* 184 */       long k = ((Long)key).longValue();
/* 185 */       Object value = e.getValue();
/* 186 */       if (value == null || !(value instanceof Float)) return false; 
/* 187 */       return (this.map.containsKey(k) && Float.floatToIntBits(this.map.get(k)) == Float.floatToIntBits(((Float)value).floatValue()));
/*     */     }
/*     */ 
/*     */     
/*     */     public boolean remove(Object o) {
/* 192 */       if (!(o instanceof Map.Entry)) return false; 
/* 193 */       if (o instanceof Long2FloatMap.Entry) {
/* 194 */         Long2FloatMap.Entry entry = (Long2FloatMap.Entry)o;
/* 195 */         return this.map.remove(entry.getLongKey(), entry.getFloatValue());
/*     */       } 
/* 197 */       Map.Entry<?, ?> e = (Map.Entry<?, ?>)o;
/* 198 */       Object key = e.getKey();
/* 199 */       if (key == null || !(key instanceof Long)) return false; 
/* 200 */       long k = ((Long)key).longValue();
/* 201 */       Object value = e.getValue();
/* 202 */       if (value == null || !(value instanceof Float)) return false; 
/* 203 */       float v = ((Float)value).floatValue();
/* 204 */       return this.map.remove(k, v);
/*     */     }
/*     */ 
/*     */     
/*     */     public int size() {
/* 209 */       return this.map.size();
/*     */     }
/*     */ 
/*     */     
/*     */     public ObjectSpliterator<Long2FloatMap.Entry> spliterator() {
/* 214 */       return ObjectSpliterators.asSpliterator(iterator(), Size64.sizeOf(this.map), 65);
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
/*     */   public LongSet keySet() {
/* 232 */     return new AbstractLongSet()
/*     */       {
/*     */         public boolean contains(long k) {
/* 235 */           return AbstractLong2FloatMap.this.containsKey(k);
/*     */         }
/*     */ 
/*     */         
/*     */         public int size() {
/* 240 */           return AbstractLong2FloatMap.this.size();
/*     */         }
/*     */ 
/*     */         
/*     */         public void clear() {
/* 245 */           AbstractLong2FloatMap.this.clear();
/*     */         }
/*     */ 
/*     */         
/*     */         public LongIterator iterator() {
/* 250 */           return new LongIterator() {
/* 251 */               private final ObjectIterator<Long2FloatMap.Entry> i = Long2FloatMaps.fastIterator(AbstractLong2FloatMap.this);
/*     */ 
/*     */               
/*     */               public long nextLong() {
/* 255 */                 return ((Long2FloatMap.Entry)this.i.next()).getLongKey();
/*     */               }
/*     */ 
/*     */               
/*     */               public boolean hasNext() {
/* 260 */                 return this.i.hasNext();
/*     */               }
/*     */ 
/*     */               
/*     */               public void remove() {
/* 265 */                 this.i.remove();
/*     */               }
/*     */ 
/*     */               
/*     */               public void forEachRemaining(LongConsumer action) {
/* 270 */                 this.i.forEachRemaining(entry -> action.accept(entry.getLongKey()));
/*     */               }
/*     */             };
/*     */         }
/*     */ 
/*     */         
/*     */         public LongSpliterator spliterator() {
/* 277 */           return LongSpliterators.asSpliterator(iterator(), Size64.sizeOf(AbstractLong2FloatMap.this), 321);
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
/* 296 */     return (FloatCollection)new AbstractFloatCollection()
/*     */       {
/*     */         public boolean contains(float k) {
/* 299 */           return AbstractLong2FloatMap.this.containsValue(k);
/*     */         }
/*     */ 
/*     */         
/*     */         public int size() {
/* 304 */           return AbstractLong2FloatMap.this.size();
/*     */         }
/*     */ 
/*     */         
/*     */         public void clear() {
/* 309 */           AbstractLong2FloatMap.this.clear();
/*     */         }
/*     */ 
/*     */         
/*     */         public FloatIterator iterator() {
/* 314 */           return new FloatIterator() {
/* 315 */               private final ObjectIterator<Long2FloatMap.Entry> i = Long2FloatMaps.fastIterator(AbstractLong2FloatMap.this);
/*     */ 
/*     */               
/*     */               public float nextFloat() {
/* 319 */                 return ((Long2FloatMap.Entry)this.i.next()).getFloatValue();
/*     */               }
/*     */ 
/*     */               
/*     */               public boolean hasNext() {
/* 324 */                 return this.i.hasNext();
/*     */               }
/*     */ 
/*     */               
/*     */               public void remove() {
/* 329 */                 this.i.remove();
/*     */               }
/*     */ 
/*     */               
/*     */               public void forEachRemaining(FloatConsumer action) {
/* 334 */                 this.i.forEachRemaining(entry -> action.accept(entry.getFloatValue()));
/*     */               }
/*     */             };
/*     */         }
/*     */ 
/*     */         
/*     */         public FloatSpliterator spliterator() {
/* 341 */           return FloatSpliterators.asSpliterator(iterator(), Size64.sizeOf(AbstractLong2FloatMap.this), 320);
/*     */         }
/*     */       };
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void putAll(Map<? extends Long, ? extends Float> m) {
/* 350 */     if (m instanceof Long2FloatMap) {
/* 351 */       ObjectIterator<Long2FloatMap.Entry> i = Long2FloatMaps.fastIterator((Long2FloatMap)m);
/* 352 */       while (i.hasNext()) {
/* 353 */         Long2FloatMap.Entry e = (Long2FloatMap.Entry)i.next();
/* 354 */         put(e.getLongKey(), e.getFloatValue());
/*     */       } 
/*     */     } else {
/* 357 */       int n = m.size();
/* 358 */       Iterator<? extends Map.Entry<? extends Long, ? extends Float>> i = m.entrySet().iterator();
/*     */       
/* 360 */       while (n-- != 0) {
/* 361 */         Map.Entry<? extends Long, ? extends Float> e = i.next();
/* 362 */         put(e.getKey(), e.getValue());
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
/* 376 */     int h = 0, n = size();
/* 377 */     ObjectIterator<Long2FloatMap.Entry> i = Long2FloatMaps.fastIterator(this);
/* 378 */     for (; n-- != 0; h += ((Long2FloatMap.Entry)i.next()).hashCode());
/* 379 */     return h;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean equals(Object o) {
/* 384 */     if (o == this) return true; 
/* 385 */     if (!(o instanceof Map)) return false; 
/* 386 */     Map<?, ?> m = (Map<?, ?>)o;
/* 387 */     if (m.size() != size()) return false; 
/* 388 */     return long2FloatEntrySet().containsAll(m.entrySet());
/*     */   }
/*     */ 
/*     */   
/*     */   public String toString() {
/* 393 */     StringBuilder s = new StringBuilder();
/* 394 */     ObjectIterator<Long2FloatMap.Entry> i = Long2FloatMaps.fastIterator(this);
/* 395 */     int n = size();
/*     */     
/* 397 */     boolean first = true;
/* 398 */     s.append("{");
/* 399 */     while (n-- != 0) {
/* 400 */       if (first) { first = false; }
/* 401 */       else { s.append(", "); }
/* 402 */        Long2FloatMap.Entry e = (Long2FloatMap.Entry)i.next();
/* 403 */       s.append(String.valueOf(e.getLongKey()));
/* 404 */       s.append("=>");
/* 405 */       s.append(String.valueOf(e.getFloatValue()));
/*     */     } 
/* 407 */     s.append("}");
/* 408 */     return s.toString();
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\i\\unimi\dsi\fastutil\longs\AbstractLong2FloatMap.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */