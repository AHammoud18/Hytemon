/*     */ package it.unimi.dsi.fastutil.doubles;
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
/*     */ 
/*     */ public abstract class AbstractDouble2DoubleMap
/*     */   extends AbstractDouble2DoubleFunction
/*     */   implements Double2DoubleMap, Serializable
/*     */ {
/*     */   private static final long serialVersionUID = -4940583368468432370L;
/*     */   
/*     */   public boolean containsKey(double k) {
/*  60 */     ObjectIterator<Double2DoubleMap.Entry> i = double2DoubleEntrySet().iterator();
/*  61 */     while (i.hasNext()) { if (((Double2DoubleMap.Entry)i.next()).getDoubleKey() == k) return true;  }
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
/*     */   public boolean containsValue(double v) {
/*  80 */     ObjectIterator<Double2DoubleMap.Entry> i = double2DoubleEntrySet().iterator();
/*  81 */     while (i.hasNext()) { if (((Double2DoubleMap.Entry)i.next()).getDoubleValue() == v) return true;  }
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public final double mergeDouble(double key, double value, DoubleBinaryOperator remappingFunction) {
/*  98 */     return mergeDouble(key, value, remappingFunction);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public static class BasicEntry
/*     */     implements Double2DoubleMap.Entry
/*     */   {
/*     */     protected double key;
/*     */ 
/*     */     
/*     */     protected double value;
/*     */ 
/*     */ 
/*     */     
/*     */     public BasicEntry() {}
/*     */ 
/*     */     
/*     */     public BasicEntry(Double key, Double value) {
/* 117 */       this.key = key.doubleValue();
/* 118 */       this.value = value.doubleValue();
/*     */     }
/*     */     
/*     */     public BasicEntry(double key, double value) {
/* 122 */       this.key = key;
/* 123 */       this.value = value;
/*     */     }
/*     */ 
/*     */     
/*     */     public double getDoubleKey() {
/* 128 */       return this.key;
/*     */     }
/*     */ 
/*     */     
/*     */     public double getDoubleValue() {
/* 133 */       return this.value;
/*     */     }
/*     */ 
/*     */     
/*     */     public double setValue(double value) {
/* 138 */       throw new UnsupportedOperationException();
/*     */     }
/*     */ 
/*     */     
/*     */     public boolean equals(Object o) {
/* 143 */       if (!(o instanceof Map.Entry)) return false; 
/* 144 */       if (o instanceof Double2DoubleMap.Entry) {
/* 145 */         Double2DoubleMap.Entry entry = (Double2DoubleMap.Entry)o;
/* 146 */         return (Double.doubleToLongBits(this.key) == Double.doubleToLongBits(entry.getDoubleKey()) && Double.doubleToLongBits(this.value) == Double.doubleToLongBits(entry.getDoubleValue()));
/*     */       } 
/* 148 */       Map.Entry<?, ?> e = (Map.Entry<?, ?>)o;
/* 149 */       Object key = e.getKey();
/* 150 */       if (key == null || !(key instanceof Double)) return false; 
/* 151 */       Object value = e.getValue();
/* 152 */       if (value == null || !(value instanceof Double)) return false; 
/* 153 */       return (Double.doubleToLongBits(this.key) == Double.doubleToLongBits(((Double)key).doubleValue()) && Double.doubleToLongBits(this.value) == Double.doubleToLongBits(((Double)value).doubleValue()));
/*     */     }
/*     */ 
/*     */     
/*     */     public int hashCode() {
/* 158 */       return HashCommon.double2int(this.key) ^ HashCommon.double2int(this.value);
/*     */     }
/*     */ 
/*     */     
/*     */     public String toString() {
/* 163 */       return this.key + "->" + this.value;
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   public static abstract class BasicEntrySet
/*     */     extends AbstractObjectSet<Double2DoubleMap.Entry>
/*     */   {
/*     */     protected final Double2DoubleMap map;
/*     */ 
/*     */     
/*     */     public BasicEntrySet(Double2DoubleMap map) {
/* 175 */       this.map = map;
/*     */     }
/*     */ 
/*     */     
/*     */     public boolean contains(Object o) {
/* 180 */       if (!(o instanceof Map.Entry)) return false; 
/* 181 */       if (o instanceof Double2DoubleMap.Entry) {
/* 182 */         Double2DoubleMap.Entry entry = (Double2DoubleMap.Entry)o;
/* 183 */         double d = entry.getDoubleKey();
/* 184 */         return (this.map.containsKey(d) && Double.doubleToLongBits(this.map.get(d)) == Double.doubleToLongBits(entry.getDoubleValue()));
/*     */       } 
/* 186 */       Map.Entry<?, ?> e = (Map.Entry<?, ?>)o;
/* 187 */       Object key = e.getKey();
/* 188 */       if (key == null || !(key instanceof Double)) return false; 
/* 189 */       double k = ((Double)key).doubleValue();
/* 190 */       Object value = e.getValue();
/* 191 */       if (value == null || !(value instanceof Double)) return false; 
/* 192 */       return (this.map.containsKey(k) && Double.doubleToLongBits(this.map.get(k)) == Double.doubleToLongBits(((Double)value).doubleValue()));
/*     */     }
/*     */ 
/*     */     
/*     */     public boolean remove(Object o) {
/* 197 */       if (!(o instanceof Map.Entry)) return false; 
/* 198 */       if (o instanceof Double2DoubleMap.Entry) {
/* 199 */         Double2DoubleMap.Entry entry = (Double2DoubleMap.Entry)o;
/* 200 */         return this.map.remove(entry.getDoubleKey(), entry.getDoubleValue());
/*     */       } 
/* 202 */       Map.Entry<?, ?> e = (Map.Entry<?, ?>)o;
/* 203 */       Object key = e.getKey();
/* 204 */       if (key == null || !(key instanceof Double)) return false; 
/* 205 */       double k = ((Double)key).doubleValue();
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
/*     */     public ObjectSpliterator<Double2DoubleMap.Entry> spliterator() {
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
/*     */   public DoubleSet keySet() {
/* 237 */     return new AbstractDoubleSet()
/*     */       {
/*     */         public boolean contains(double k) {
/* 240 */           return AbstractDouble2DoubleMap.this.containsKey(k);
/*     */         }
/*     */ 
/*     */         
/*     */         public int size() {
/* 245 */           return AbstractDouble2DoubleMap.this.size();
/*     */         }
/*     */ 
/*     */         
/*     */         public void clear() {
/* 250 */           AbstractDouble2DoubleMap.this.clear();
/*     */         }
/*     */ 
/*     */         
/*     */         public DoubleIterator iterator() {
/* 255 */           return new DoubleIterator() {
/* 256 */               private final ObjectIterator<Double2DoubleMap.Entry> i = Double2DoubleMaps.fastIterator(AbstractDouble2DoubleMap.this);
/*     */ 
/*     */               
/*     */               public double nextDouble() {
/* 260 */                 return ((Double2DoubleMap.Entry)this.i.next()).getDoubleKey();
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
/*     */               public void forEachRemaining(DoubleConsumer action) {
/* 275 */                 this.i.forEachRemaining(entry -> action.accept(entry.getDoubleKey()));
/*     */               }
/*     */             };
/*     */         }
/*     */ 
/*     */         
/*     */         public DoubleSpliterator spliterator() {
/* 282 */           return DoubleSpliterators.asSpliterator(iterator(), Size64.sizeOf(AbstractDouble2DoubleMap.this), 321);
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
/* 301 */     return new AbstractDoubleCollection()
/*     */       {
/*     */         public boolean contains(double k) {
/* 304 */           return AbstractDouble2DoubleMap.this.containsValue(k);
/*     */         }
/*     */ 
/*     */         
/*     */         public int size() {
/* 309 */           return AbstractDouble2DoubleMap.this.size();
/*     */         }
/*     */ 
/*     */         
/*     */         public void clear() {
/* 314 */           AbstractDouble2DoubleMap.this.clear();
/*     */         }
/*     */ 
/*     */         
/*     */         public DoubleIterator iterator() {
/* 319 */           return new DoubleIterator() {
/* 320 */               private final ObjectIterator<Double2DoubleMap.Entry> i = Double2DoubleMaps.fastIterator(AbstractDouble2DoubleMap.this);
/*     */ 
/*     */               
/*     */               public double nextDouble() {
/* 324 */                 return ((Double2DoubleMap.Entry)this.i.next()).getDoubleValue();
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
/* 346 */           return DoubleSpliterators.asSpliterator(iterator(), Size64.sizeOf(AbstractDouble2DoubleMap.this), 320);
/*     */         }
/*     */       };
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void putAll(Map<? extends Double, ? extends Double> m) {
/* 355 */     if (m instanceof Double2DoubleMap) {
/* 356 */       ObjectIterator<Double2DoubleMap.Entry> i = Double2DoubleMaps.fastIterator((Double2DoubleMap)m);
/* 357 */       while (i.hasNext()) {
/* 358 */         Double2DoubleMap.Entry e = (Double2DoubleMap.Entry)i.next();
/* 359 */         put(e.getDoubleKey(), e.getDoubleValue());
/*     */       } 
/*     */     } else {
/* 362 */       int n = m.size();
/* 363 */       Iterator<? extends Map.Entry<? extends Double, ? extends Double>> i = m.entrySet().iterator();
/*     */       
/* 365 */       while (n-- != 0) {
/* 366 */         Map.Entry<? extends Double, ? extends Double> e = i.next();
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
/* 382 */     ObjectIterator<Double2DoubleMap.Entry> i = Double2DoubleMaps.fastIterator(this);
/* 383 */     for (; n-- != 0; h += ((Double2DoubleMap.Entry)i.next()).hashCode());
/* 384 */     return h;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean equals(Object o) {
/* 389 */     if (o == this) return true; 
/* 390 */     if (!(o instanceof Map)) return false; 
/* 391 */     Map<?, ?> m = (Map<?, ?>)o;
/* 392 */     if (m.size() != size()) return false; 
/* 393 */     return double2DoubleEntrySet().containsAll(m.entrySet());
/*     */   }
/*     */ 
/*     */   
/*     */   public String toString() {
/* 398 */     StringBuilder s = new StringBuilder();
/* 399 */     ObjectIterator<Double2DoubleMap.Entry> i = Double2DoubleMaps.fastIterator(this);
/* 400 */     int n = size();
/*     */     
/* 402 */     boolean first = true;
/* 403 */     s.append("{");
/* 404 */     while (n-- != 0) {
/* 405 */       if (first) { first = false; }
/* 406 */       else { s.append(", "); }
/* 407 */        Double2DoubleMap.Entry e = (Double2DoubleMap.Entry)i.next();
/* 408 */       s.append(String.valueOf(e.getDoubleKey()));
/* 409 */       s.append("=>");
/* 410 */       s.append(String.valueOf(e.getDoubleValue()));
/*     */     } 
/* 412 */     s.append("}");
/* 413 */     return s.toString();
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\i\\unimi\dsi\fastutil\doubles\AbstractDouble2DoubleMap.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */