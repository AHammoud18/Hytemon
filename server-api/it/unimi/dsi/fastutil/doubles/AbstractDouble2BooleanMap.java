/*     */ package it.unimi.dsi.fastutil.doubles;
/*     */ 
/*     */ import it.unimi.dsi.fastutil.HashCommon;
/*     */ import it.unimi.dsi.fastutil.Size64;
/*     */ import it.unimi.dsi.fastutil.booleans.AbstractBooleanCollection;
/*     */ import it.unimi.dsi.fastutil.booleans.BooleanCollection;
/*     */ import it.unimi.dsi.fastutil.booleans.BooleanConsumer;
/*     */ import it.unimi.dsi.fastutil.booleans.BooleanIterator;
/*     */ import it.unimi.dsi.fastutil.booleans.BooleanSpliterator;
/*     */ import it.unimi.dsi.fastutil.booleans.BooleanSpliterators;
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
/*     */ public abstract class AbstractDouble2BooleanMap
/*     */   extends AbstractDouble2BooleanFunction
/*     */   implements Double2BooleanMap, Serializable
/*     */ {
/*     */   private static final long serialVersionUID = -4940583368468432370L;
/*     */   
/*     */   public boolean containsKey(double k) {
/*  66 */     ObjectIterator<Double2BooleanMap.Entry> i = double2BooleanEntrySet().iterator();
/*  67 */     while (i.hasNext()) { if (((Double2BooleanMap.Entry)i.next()).getDoubleKey() == k) return true;  }
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
/*     */   public boolean containsValue(boolean v) {
/*  86 */     ObjectIterator<Double2BooleanMap.Entry> i = double2BooleanEntrySet().iterator();
/*  87 */     while (i.hasNext()) { if (((Double2BooleanMap.Entry)i.next()).getBooleanValue() == v) return true;  }
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
/*     */     implements Double2BooleanMap.Entry
/*     */   {
/*     */     protected double key;
/*     */ 
/*     */     
/*     */     protected boolean value;
/*     */ 
/*     */ 
/*     */     
/*     */     public BasicEntry() {}
/*     */ 
/*     */     
/*     */     public BasicEntry(Double key, Boolean value) {
/* 112 */       this.key = key.doubleValue();
/* 113 */       this.value = value.booleanValue();
/*     */     }
/*     */     
/*     */     public BasicEntry(double key, boolean value) {
/* 117 */       this.key = key;
/* 118 */       this.value = value;
/*     */     }
/*     */ 
/*     */     
/*     */     public double getDoubleKey() {
/* 123 */       return this.key;
/*     */     }
/*     */ 
/*     */     
/*     */     public boolean getBooleanValue() {
/* 128 */       return this.value;
/*     */     }
/*     */ 
/*     */     
/*     */     public boolean setValue(boolean value) {
/* 133 */       throw new UnsupportedOperationException();
/*     */     }
/*     */ 
/*     */     
/*     */     public boolean equals(Object o) {
/* 138 */       if (!(o instanceof Map.Entry)) return false; 
/* 139 */       if (o instanceof Double2BooleanMap.Entry) {
/* 140 */         Double2BooleanMap.Entry entry = (Double2BooleanMap.Entry)o;
/* 141 */         return (Double.doubleToLongBits(this.key) == Double.doubleToLongBits(entry.getDoubleKey()) && this.value == entry.getBooleanValue());
/*     */       } 
/* 143 */       Map.Entry<?, ?> e = (Map.Entry<?, ?>)o;
/* 144 */       Object key = e.getKey();
/* 145 */       if (key == null || !(key instanceof Double)) return false; 
/* 146 */       Object value = e.getValue();
/* 147 */       if (value == null || !(value instanceof Boolean)) return false; 
/* 148 */       return (Double.doubleToLongBits(this.key) == Double.doubleToLongBits(((Double)key).doubleValue()) && this.value == ((Boolean)value).booleanValue());
/*     */     }
/*     */ 
/*     */     
/*     */     public int hashCode() {
/* 153 */       return HashCommon.double2int(this.key) ^ (this.value ? 1231 : 1237);
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
/*     */     extends AbstractObjectSet<Double2BooleanMap.Entry>
/*     */   {
/*     */     protected final Double2BooleanMap map;
/*     */ 
/*     */     
/*     */     public BasicEntrySet(Double2BooleanMap map) {
/* 170 */       this.map = map;
/*     */     }
/*     */ 
/*     */     
/*     */     public boolean contains(Object o) {
/* 175 */       if (!(o instanceof Map.Entry)) return false; 
/* 176 */       if (o instanceof Double2BooleanMap.Entry) {
/* 177 */         Double2BooleanMap.Entry entry = (Double2BooleanMap.Entry)o;
/* 178 */         double d = entry.getDoubleKey();
/* 179 */         return (this.map.containsKey(d) && this.map.get(d) == entry.getBooleanValue());
/*     */       } 
/* 181 */       Map.Entry<?, ?> e = (Map.Entry<?, ?>)o;
/* 182 */       Object key = e.getKey();
/* 183 */       if (key == null || !(key instanceof Double)) return false; 
/* 184 */       double k = ((Double)key).doubleValue();
/* 185 */       Object value = e.getValue();
/* 186 */       if (value == null || !(value instanceof Boolean)) return false; 
/* 187 */       return (this.map.containsKey(k) && this.map.get(k) == ((Boolean)value).booleanValue());
/*     */     }
/*     */ 
/*     */     
/*     */     public boolean remove(Object o) {
/* 192 */       if (!(o instanceof Map.Entry)) return false; 
/* 193 */       if (o instanceof Double2BooleanMap.Entry) {
/* 194 */         Double2BooleanMap.Entry entry = (Double2BooleanMap.Entry)o;
/* 195 */         return this.map.remove(entry.getDoubleKey(), entry.getBooleanValue());
/*     */       } 
/* 197 */       Map.Entry<?, ?> e = (Map.Entry<?, ?>)o;
/* 198 */       Object key = e.getKey();
/* 199 */       if (key == null || !(key instanceof Double)) return false; 
/* 200 */       double k = ((Double)key).doubleValue();
/* 201 */       Object value = e.getValue();
/* 202 */       if (value == null || !(value instanceof Boolean)) return false; 
/* 203 */       boolean v = ((Boolean)value).booleanValue();
/* 204 */       return this.map.remove(k, v);
/*     */     }
/*     */ 
/*     */     
/*     */     public int size() {
/* 209 */       return this.map.size();
/*     */     }
/*     */ 
/*     */     
/*     */     public ObjectSpliterator<Double2BooleanMap.Entry> spliterator() {
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
/*     */   public DoubleSet keySet() {
/* 232 */     return new AbstractDoubleSet()
/*     */       {
/*     */         public boolean contains(double k) {
/* 235 */           return AbstractDouble2BooleanMap.this.containsKey(k);
/*     */         }
/*     */ 
/*     */         
/*     */         public int size() {
/* 240 */           return AbstractDouble2BooleanMap.this.size();
/*     */         }
/*     */ 
/*     */         
/*     */         public void clear() {
/* 245 */           AbstractDouble2BooleanMap.this.clear();
/*     */         }
/*     */ 
/*     */         
/*     */         public DoubleIterator iterator() {
/* 250 */           return new DoubleIterator() {
/* 251 */               private final ObjectIterator<Double2BooleanMap.Entry> i = Double2BooleanMaps.fastIterator(AbstractDouble2BooleanMap.this);
/*     */ 
/*     */               
/*     */               public double nextDouble() {
/* 255 */                 return ((Double2BooleanMap.Entry)this.i.next()).getDoubleKey();
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
/*     */               public void forEachRemaining(DoubleConsumer action) {
/* 270 */                 this.i.forEachRemaining(entry -> action.accept(entry.getDoubleKey()));
/*     */               }
/*     */             };
/*     */         }
/*     */ 
/*     */         
/*     */         public DoubleSpliterator spliterator() {
/* 277 */           return DoubleSpliterators.asSpliterator(iterator(), Size64.sizeOf(AbstractDouble2BooleanMap.this), 321);
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
/*     */   public BooleanCollection values() {
/* 296 */     return (BooleanCollection)new AbstractBooleanCollection()
/*     */       {
/*     */         public boolean contains(boolean k) {
/* 299 */           return AbstractDouble2BooleanMap.this.containsValue(k);
/*     */         }
/*     */ 
/*     */         
/*     */         public int size() {
/* 304 */           return AbstractDouble2BooleanMap.this.size();
/*     */         }
/*     */ 
/*     */         
/*     */         public void clear() {
/* 309 */           AbstractDouble2BooleanMap.this.clear();
/*     */         }
/*     */ 
/*     */         
/*     */         public BooleanIterator iterator() {
/* 314 */           return new BooleanIterator() {
/* 315 */               private final ObjectIterator<Double2BooleanMap.Entry> i = Double2BooleanMaps.fastIterator(AbstractDouble2BooleanMap.this);
/*     */ 
/*     */               
/*     */               public boolean nextBoolean() {
/* 319 */                 return ((Double2BooleanMap.Entry)this.i.next()).getBooleanValue();
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
/*     */               public void forEachRemaining(BooleanConsumer action) {
/* 334 */                 this.i.forEachRemaining(entry -> action.accept(entry.getBooleanValue()));
/*     */               }
/*     */             };
/*     */         }
/*     */ 
/*     */         
/*     */         public BooleanSpliterator spliterator() {
/* 341 */           return BooleanSpliterators.asSpliterator(iterator(), Size64.sizeOf(AbstractDouble2BooleanMap.this), 320);
/*     */         }
/*     */       };
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void putAll(Map<? extends Double, ? extends Boolean> m) {
/* 350 */     if (m instanceof Double2BooleanMap) {
/* 351 */       ObjectIterator<Double2BooleanMap.Entry> i = Double2BooleanMaps.fastIterator((Double2BooleanMap)m);
/* 352 */       while (i.hasNext()) {
/* 353 */         Double2BooleanMap.Entry e = (Double2BooleanMap.Entry)i.next();
/* 354 */         put(e.getDoubleKey(), e.getBooleanValue());
/*     */       } 
/*     */     } else {
/* 357 */       int n = m.size();
/* 358 */       Iterator<? extends Map.Entry<? extends Double, ? extends Boolean>> i = m.entrySet().iterator();
/*     */       
/* 360 */       while (n-- != 0) {
/* 361 */         Map.Entry<? extends Double, ? extends Boolean> e = i.next();
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
/* 377 */     ObjectIterator<Double2BooleanMap.Entry> i = Double2BooleanMaps.fastIterator(this);
/* 378 */     for (; n-- != 0; h += ((Double2BooleanMap.Entry)i.next()).hashCode());
/* 379 */     return h;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean equals(Object o) {
/* 384 */     if (o == this) return true; 
/* 385 */     if (!(o instanceof Map)) return false; 
/* 386 */     Map<?, ?> m = (Map<?, ?>)o;
/* 387 */     if (m.size() != size()) return false; 
/* 388 */     return double2BooleanEntrySet().containsAll(m.entrySet());
/*     */   }
/*     */ 
/*     */   
/*     */   public String toString() {
/* 393 */     StringBuilder s = new StringBuilder();
/* 394 */     ObjectIterator<Double2BooleanMap.Entry> i = Double2BooleanMaps.fastIterator(this);
/* 395 */     int n = size();
/*     */     
/* 397 */     boolean first = true;
/* 398 */     s.append("{");
/* 399 */     while (n-- != 0) {
/* 400 */       if (first) { first = false; }
/* 401 */       else { s.append(", "); }
/* 402 */        Double2BooleanMap.Entry e = (Double2BooleanMap.Entry)i.next();
/* 403 */       s.append(String.valueOf(e.getDoubleKey()));
/* 404 */       s.append("=>");
/* 405 */       s.append(String.valueOf(e.getBooleanValue()));
/*     */     } 
/* 407 */     s.append("}");
/* 408 */     return s.toString();
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\i\\unimi\dsi\fastutil\doubles\AbstractDouble2BooleanMap.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */