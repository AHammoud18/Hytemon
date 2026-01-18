/*     */ package it.unimi.dsi.fastutil.longs;
/*     */ 
/*     */ import it.unimi.dsi.fastutil.HashCommon;
/*     */ import it.unimi.dsi.fastutil.Size64;
/*     */ import it.unimi.dsi.fastutil.doubles.AbstractDoubleCollection;
/*     */ import it.unimi.dsi.fastutil.doubles.DoubleBinaryOperator;
/*     */ import it.unimi.dsi.fastutil.doubles.DoubleCollection;
/*     */ import it.unimi.dsi.fastutil.doubles.DoubleIterator;
/*     */ import it.unimi.dsi.fastutil.doubles.DoubleSpliterator;
/*     */ import it.unimi.dsi.fastutil.doubles.DoubleSpliterators;
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
/*     */ import java.util.function.DoubleBinaryOperator;
/*     */ import java.util.function.DoubleConsumer;
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
/*     */ public abstract class AbstractLong2DoubleMap
/*     */   extends AbstractLong2DoubleFunction
/*     */   implements Long2DoubleMap, Serializable
/*     */ {
/*     */   private static final long serialVersionUID = -4940583368468432370L;
/*     */   
/*     */   public boolean containsKey(long k) {
/*  65 */     ObjectIterator<Long2DoubleMap.Entry> i = long2DoubleEntrySet().iterator();
/*  66 */     while (i.hasNext()) { if (((Long2DoubleMap.Entry)i.next()).getLongKey() == k) return true;  }
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
/*     */   public boolean containsValue(double v) {
/*  85 */     ObjectIterator<Long2DoubleMap.Entry> i = long2DoubleEntrySet().iterator();
/*  86 */     while (i.hasNext()) { if (((Long2DoubleMap.Entry)i.next()).getDoubleValue() == v) return true;  }
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
/*     */   public final double mergeDouble(long key, double value, DoubleBinaryOperator remappingFunction) {
/* 103 */     return mergeDouble(key, value, (DoubleBinaryOperator)remappingFunction);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public static class BasicEntry
/*     */     implements Long2DoubleMap.Entry
/*     */   {
/*     */     protected long key;
/*     */ 
/*     */     
/*     */     protected double value;
/*     */ 
/*     */ 
/*     */     
/*     */     public BasicEntry() {}
/*     */ 
/*     */     
/*     */     public BasicEntry(Long key, Double value) {
/* 122 */       this.key = key.longValue();
/* 123 */       this.value = value.doubleValue();
/*     */     }
/*     */     
/*     */     public BasicEntry(long key, double value) {
/* 127 */       this.key = key;
/* 128 */       this.value = value;
/*     */     }
/*     */ 
/*     */     
/*     */     public long getLongKey() {
/* 133 */       return this.key;
/*     */     }
/*     */ 
/*     */     
/*     */     public double getDoubleValue() {
/* 138 */       return this.value;
/*     */     }
/*     */ 
/*     */     
/*     */     public double setValue(double value) {
/* 143 */       throw new UnsupportedOperationException();
/*     */     }
/*     */ 
/*     */     
/*     */     public boolean equals(Object o) {
/* 148 */       if (!(o instanceof Map.Entry)) return false; 
/* 149 */       if (o instanceof Long2DoubleMap.Entry) {
/* 150 */         Long2DoubleMap.Entry entry = (Long2DoubleMap.Entry)o;
/* 151 */         return (this.key == entry.getLongKey() && Double.doubleToLongBits(this.value) == Double.doubleToLongBits(entry.getDoubleValue()));
/*     */       } 
/* 153 */       Map.Entry<?, ?> e = (Map.Entry<?, ?>)o;
/* 154 */       Object key = e.getKey();
/* 155 */       if (key == null || !(key instanceof Long)) return false; 
/* 156 */       Object value = e.getValue();
/* 157 */       if (value == null || !(value instanceof Double)) return false; 
/* 158 */       return (this.key == ((Long)key).longValue() && Double.doubleToLongBits(this.value) == Double.doubleToLongBits(((Double)value).doubleValue()));
/*     */     }
/*     */ 
/*     */     
/*     */     public int hashCode() {
/* 163 */       return HashCommon.long2int(this.key) ^ HashCommon.double2int(this.value);
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
/*     */     extends AbstractObjectSet<Long2DoubleMap.Entry>
/*     */   {
/*     */     protected final Long2DoubleMap map;
/*     */ 
/*     */     
/*     */     public BasicEntrySet(Long2DoubleMap map) {
/* 180 */       this.map = map;
/*     */     }
/*     */ 
/*     */     
/*     */     public boolean contains(Object o) {
/* 185 */       if (!(o instanceof Map.Entry)) return false; 
/* 186 */       if (o instanceof Long2DoubleMap.Entry) {
/* 187 */         Long2DoubleMap.Entry entry = (Long2DoubleMap.Entry)o;
/* 188 */         long l = entry.getLongKey();
/* 189 */         return (this.map.containsKey(l) && Double.doubleToLongBits(this.map.get(l)) == Double.doubleToLongBits(entry.getDoubleValue()));
/*     */       } 
/* 191 */       Map.Entry<?, ?> e = (Map.Entry<?, ?>)o;
/* 192 */       Object key = e.getKey();
/* 193 */       if (key == null || !(key instanceof Long)) return false; 
/* 194 */       long k = ((Long)key).longValue();
/* 195 */       Object value = e.getValue();
/* 196 */       if (value == null || !(value instanceof Double)) return false; 
/* 197 */       return (this.map.containsKey(k) && Double.doubleToLongBits(this.map.get(k)) == Double.doubleToLongBits(((Double)value).doubleValue()));
/*     */     }
/*     */ 
/*     */     
/*     */     public boolean remove(Object o) {
/* 202 */       if (!(o instanceof Map.Entry)) return false; 
/* 203 */       if (o instanceof Long2DoubleMap.Entry) {
/* 204 */         Long2DoubleMap.Entry entry = (Long2DoubleMap.Entry)o;
/* 205 */         return this.map.remove(entry.getLongKey(), entry.getDoubleValue());
/*     */       } 
/* 207 */       Map.Entry<?, ?> e = (Map.Entry<?, ?>)o;
/* 208 */       Object key = e.getKey();
/* 209 */       if (key == null || !(key instanceof Long)) return false; 
/* 210 */       long k = ((Long)key).longValue();
/* 211 */       Object value = e.getValue();
/* 212 */       if (value == null || !(value instanceof Double)) return false; 
/* 213 */       double v = ((Double)value).doubleValue();
/* 214 */       return this.map.remove(k, v);
/*     */     }
/*     */ 
/*     */     
/*     */     public int size() {
/* 219 */       return this.map.size();
/*     */     }
/*     */ 
/*     */     
/*     */     public ObjectSpliterator<Long2DoubleMap.Entry> spliterator() {
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
/*     */   public LongSet keySet() {
/* 242 */     return new AbstractLongSet()
/*     */       {
/*     */         public boolean contains(long k) {
/* 245 */           return AbstractLong2DoubleMap.this.containsKey(k);
/*     */         }
/*     */ 
/*     */         
/*     */         public int size() {
/* 250 */           return AbstractLong2DoubleMap.this.size();
/*     */         }
/*     */ 
/*     */         
/*     */         public void clear() {
/* 255 */           AbstractLong2DoubleMap.this.clear();
/*     */         }
/*     */ 
/*     */         
/*     */         public LongIterator iterator() {
/* 260 */           return new LongIterator() {
/* 261 */               private final ObjectIterator<Long2DoubleMap.Entry> i = Long2DoubleMaps.fastIterator(AbstractLong2DoubleMap.this);
/*     */ 
/*     */               
/*     */               public long nextLong() {
/* 265 */                 return ((Long2DoubleMap.Entry)this.i.next()).getLongKey();
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
/*     */               public void forEachRemaining(LongConsumer action) {
/* 280 */                 this.i.forEachRemaining(entry -> action.accept(entry.getLongKey()));
/*     */               }
/*     */             };
/*     */         }
/*     */ 
/*     */         
/*     */         public LongSpliterator spliterator() {
/* 287 */           return LongSpliterators.asSpliterator(iterator(), Size64.sizeOf(AbstractLong2DoubleMap.this), 321);
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
/* 306 */     return (DoubleCollection)new AbstractDoubleCollection()
/*     */       {
/*     */         public boolean contains(double k) {
/* 309 */           return AbstractLong2DoubleMap.this.containsValue(k);
/*     */         }
/*     */ 
/*     */         
/*     */         public int size() {
/* 314 */           return AbstractLong2DoubleMap.this.size();
/*     */         }
/*     */ 
/*     */         
/*     */         public void clear() {
/* 319 */           AbstractLong2DoubleMap.this.clear();
/*     */         }
/*     */ 
/*     */         
/*     */         public DoubleIterator iterator() {
/* 324 */           return new DoubleIterator() {
/* 325 */               private final ObjectIterator<Long2DoubleMap.Entry> i = Long2DoubleMaps.fastIterator(AbstractLong2DoubleMap.this);
/*     */ 
/*     */               
/*     */               public double nextDouble() {
/* 329 */                 return ((Long2DoubleMap.Entry)this.i.next()).getDoubleValue();
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
/*     */               public void forEachRemaining(DoubleConsumer action) {
/* 344 */                 this.i.forEachRemaining(entry -> action.accept(entry.getDoubleValue()));
/*     */               }
/*     */             };
/*     */         }
/*     */ 
/*     */         
/*     */         public DoubleSpliterator spliterator() {
/* 351 */           return DoubleSpliterators.asSpliterator(iterator(), Size64.sizeOf(AbstractLong2DoubleMap.this), 320);
/*     */         }
/*     */       };
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void putAll(Map<? extends Long, ? extends Double> m) {
/* 360 */     if (m instanceof Long2DoubleMap) {
/* 361 */       ObjectIterator<Long2DoubleMap.Entry> i = Long2DoubleMaps.fastIterator((Long2DoubleMap)m);
/* 362 */       while (i.hasNext()) {
/* 363 */         Long2DoubleMap.Entry e = (Long2DoubleMap.Entry)i.next();
/* 364 */         put(e.getLongKey(), e.getDoubleValue());
/*     */       } 
/*     */     } else {
/* 367 */       int n = m.size();
/* 368 */       Iterator<? extends Map.Entry<? extends Long, ? extends Double>> i = m.entrySet().iterator();
/*     */       
/* 370 */       while (n-- != 0) {
/* 371 */         Map.Entry<? extends Long, ? extends Double> e = i.next();
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
/* 387 */     ObjectIterator<Long2DoubleMap.Entry> i = Long2DoubleMaps.fastIterator(this);
/* 388 */     for (; n-- != 0; h += ((Long2DoubleMap.Entry)i.next()).hashCode());
/* 389 */     return h;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean equals(Object o) {
/* 394 */     if (o == this) return true; 
/* 395 */     if (!(o instanceof Map)) return false; 
/* 396 */     Map<?, ?> m = (Map<?, ?>)o;
/* 397 */     if (m.size() != size()) return false; 
/* 398 */     return long2DoubleEntrySet().containsAll(m.entrySet());
/*     */   }
/*     */ 
/*     */   
/*     */   public String toString() {
/* 403 */     StringBuilder s = new StringBuilder();
/* 404 */     ObjectIterator<Long2DoubleMap.Entry> i = Long2DoubleMaps.fastIterator(this);
/* 405 */     int n = size();
/*     */     
/* 407 */     boolean first = true;
/* 408 */     s.append("{");
/* 409 */     while (n-- != 0) {
/* 410 */       if (first) { first = false; }
/* 411 */       else { s.append(", "); }
/* 412 */        Long2DoubleMap.Entry e = (Long2DoubleMap.Entry)i.next();
/* 413 */       s.append(String.valueOf(e.getLongKey()));
/* 414 */       s.append("=>");
/* 415 */       s.append(String.valueOf(e.getDoubleValue()));
/*     */     } 
/* 417 */     s.append("}");
/* 418 */     return s.toString();
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\i\\unimi\dsi\fastutil\longs\AbstractLong2DoubleMap.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */