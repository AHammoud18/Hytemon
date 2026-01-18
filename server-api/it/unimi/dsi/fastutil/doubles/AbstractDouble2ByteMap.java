/*     */ package it.unimi.dsi.fastutil.doubles;
/*     */ 
/*     */ import it.unimi.dsi.fastutil.HashCommon;
/*     */ import it.unimi.dsi.fastutil.Size64;
/*     */ import it.unimi.dsi.fastutil.bytes.AbstractByteCollection;
/*     */ import it.unimi.dsi.fastutil.bytes.ByteCollection;
/*     */ import it.unimi.dsi.fastutil.bytes.ByteConsumer;
/*     */ import it.unimi.dsi.fastutil.bytes.ByteIterator;
/*     */ import it.unimi.dsi.fastutil.bytes.ByteSpliterator;
/*     */ import it.unimi.dsi.fastutil.bytes.ByteSpliterators;
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
/*     */ public abstract class AbstractDouble2ByteMap
/*     */   extends AbstractDouble2ByteFunction
/*     */   implements Double2ByteMap, Serializable
/*     */ {
/*     */   private static final long serialVersionUID = -4940583368468432370L;
/*     */   
/*     */   public boolean containsKey(double k) {
/*  66 */     ObjectIterator<Double2ByteMap.Entry> i = double2ByteEntrySet().iterator();
/*  67 */     while (i.hasNext()) { if (((Double2ByteMap.Entry)i.next()).getDoubleKey() == k) return true;  }
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
/*     */   public boolean containsValue(byte v) {
/*  86 */     ObjectIterator<Double2ByteMap.Entry> i = double2ByteEntrySet().iterator();
/*  87 */     while (i.hasNext()) { if (((Double2ByteMap.Entry)i.next()).getByteValue() == v) return true;  }
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
/*     */     implements Double2ByteMap.Entry
/*     */   {
/*     */     protected double key;
/*     */ 
/*     */     
/*     */     protected byte value;
/*     */ 
/*     */ 
/*     */     
/*     */     public BasicEntry() {}
/*     */ 
/*     */     
/*     */     public BasicEntry(Double key, Byte value) {
/* 112 */       this.key = key.doubleValue();
/* 113 */       this.value = value.byteValue();
/*     */     }
/*     */     
/*     */     public BasicEntry(double key, byte value) {
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
/*     */     public byte getByteValue() {
/* 128 */       return this.value;
/*     */     }
/*     */ 
/*     */     
/*     */     public byte setValue(byte value) {
/* 133 */       throw new UnsupportedOperationException();
/*     */     }
/*     */ 
/*     */     
/*     */     public boolean equals(Object o) {
/* 138 */       if (!(o instanceof Map.Entry)) return false; 
/* 139 */       if (o instanceof Double2ByteMap.Entry) {
/* 140 */         Double2ByteMap.Entry entry = (Double2ByteMap.Entry)o;
/* 141 */         return (Double.doubleToLongBits(this.key) == Double.doubleToLongBits(entry.getDoubleKey()) && this.value == entry.getByteValue());
/*     */       } 
/* 143 */       Map.Entry<?, ?> e = (Map.Entry<?, ?>)o;
/* 144 */       Object key = e.getKey();
/* 145 */       if (key == null || !(key instanceof Double)) return false; 
/* 146 */       Object value = e.getValue();
/* 147 */       if (value == null || !(value instanceof Byte)) return false; 
/* 148 */       return (Double.doubleToLongBits(this.key) == Double.doubleToLongBits(((Double)key).doubleValue()) && this.value == ((Byte)value).byteValue());
/*     */     }
/*     */ 
/*     */     
/*     */     public int hashCode() {
/* 153 */       return HashCommon.double2int(this.key) ^ this.value;
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
/*     */     extends AbstractObjectSet<Double2ByteMap.Entry>
/*     */   {
/*     */     protected final Double2ByteMap map;
/*     */ 
/*     */     
/*     */     public BasicEntrySet(Double2ByteMap map) {
/* 170 */       this.map = map;
/*     */     }
/*     */ 
/*     */     
/*     */     public boolean contains(Object o) {
/* 175 */       if (!(o instanceof Map.Entry)) return false; 
/* 176 */       if (o instanceof Double2ByteMap.Entry) {
/* 177 */         Double2ByteMap.Entry entry = (Double2ByteMap.Entry)o;
/* 178 */         double d = entry.getDoubleKey();
/* 179 */         return (this.map.containsKey(d) && this.map.get(d) == entry.getByteValue());
/*     */       } 
/* 181 */       Map.Entry<?, ?> e = (Map.Entry<?, ?>)o;
/* 182 */       Object key = e.getKey();
/* 183 */       if (key == null || !(key instanceof Double)) return false; 
/* 184 */       double k = ((Double)key).doubleValue();
/* 185 */       Object value = e.getValue();
/* 186 */       if (value == null || !(value instanceof Byte)) return false; 
/* 187 */       return (this.map.containsKey(k) && this.map.get(k) == ((Byte)value).byteValue());
/*     */     }
/*     */ 
/*     */     
/*     */     public boolean remove(Object o) {
/* 192 */       if (!(o instanceof Map.Entry)) return false; 
/* 193 */       if (o instanceof Double2ByteMap.Entry) {
/* 194 */         Double2ByteMap.Entry entry = (Double2ByteMap.Entry)o;
/* 195 */         return this.map.remove(entry.getDoubleKey(), entry.getByteValue());
/*     */       } 
/* 197 */       Map.Entry<?, ?> e = (Map.Entry<?, ?>)o;
/* 198 */       Object key = e.getKey();
/* 199 */       if (key == null || !(key instanceof Double)) return false; 
/* 200 */       double k = ((Double)key).doubleValue();
/* 201 */       Object value = e.getValue();
/* 202 */       if (value == null || !(value instanceof Byte)) return false; 
/* 203 */       byte v = ((Byte)value).byteValue();
/* 204 */       return this.map.remove(k, v);
/*     */     }
/*     */ 
/*     */     
/*     */     public int size() {
/* 209 */       return this.map.size();
/*     */     }
/*     */ 
/*     */     
/*     */     public ObjectSpliterator<Double2ByteMap.Entry> spliterator() {
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
/* 235 */           return AbstractDouble2ByteMap.this.containsKey(k);
/*     */         }
/*     */ 
/*     */         
/*     */         public int size() {
/* 240 */           return AbstractDouble2ByteMap.this.size();
/*     */         }
/*     */ 
/*     */         
/*     */         public void clear() {
/* 245 */           AbstractDouble2ByteMap.this.clear();
/*     */         }
/*     */ 
/*     */         
/*     */         public DoubleIterator iterator() {
/* 250 */           return new DoubleIterator() {
/* 251 */               private final ObjectIterator<Double2ByteMap.Entry> i = Double2ByteMaps.fastIterator(AbstractDouble2ByteMap.this);
/*     */ 
/*     */               
/*     */               public double nextDouble() {
/* 255 */                 return ((Double2ByteMap.Entry)this.i.next()).getDoubleKey();
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
/* 277 */           return DoubleSpliterators.asSpliterator(iterator(), Size64.sizeOf(AbstractDouble2ByteMap.this), 321);
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
/*     */   public ByteCollection values() {
/* 296 */     return (ByteCollection)new AbstractByteCollection()
/*     */       {
/*     */         public boolean contains(byte k) {
/* 299 */           return AbstractDouble2ByteMap.this.containsValue(k);
/*     */         }
/*     */ 
/*     */         
/*     */         public int size() {
/* 304 */           return AbstractDouble2ByteMap.this.size();
/*     */         }
/*     */ 
/*     */         
/*     */         public void clear() {
/* 309 */           AbstractDouble2ByteMap.this.clear();
/*     */         }
/*     */ 
/*     */         
/*     */         public ByteIterator iterator() {
/* 314 */           return new ByteIterator() {
/* 315 */               private final ObjectIterator<Double2ByteMap.Entry> i = Double2ByteMaps.fastIterator(AbstractDouble2ByteMap.this);
/*     */ 
/*     */               
/*     */               public byte nextByte() {
/* 319 */                 return ((Double2ByteMap.Entry)this.i.next()).getByteValue();
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
/*     */               public void forEachRemaining(ByteConsumer action) {
/* 334 */                 this.i.forEachRemaining(entry -> action.accept(entry.getByteValue()));
/*     */               }
/*     */             };
/*     */         }
/*     */ 
/*     */         
/*     */         public ByteSpliterator spliterator() {
/* 341 */           return ByteSpliterators.asSpliterator(iterator(), Size64.sizeOf(AbstractDouble2ByteMap.this), 320);
/*     */         }
/*     */       };
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void putAll(Map<? extends Double, ? extends Byte> m) {
/* 350 */     if (m instanceof Double2ByteMap) {
/* 351 */       ObjectIterator<Double2ByteMap.Entry> i = Double2ByteMaps.fastIterator((Double2ByteMap)m);
/* 352 */       while (i.hasNext()) {
/* 353 */         Double2ByteMap.Entry e = (Double2ByteMap.Entry)i.next();
/* 354 */         put(e.getDoubleKey(), e.getByteValue());
/*     */       } 
/*     */     } else {
/* 357 */       int n = m.size();
/* 358 */       Iterator<? extends Map.Entry<? extends Double, ? extends Byte>> i = m.entrySet().iterator();
/*     */       
/* 360 */       while (n-- != 0) {
/* 361 */         Map.Entry<? extends Double, ? extends Byte> e = i.next();
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
/* 377 */     ObjectIterator<Double2ByteMap.Entry> i = Double2ByteMaps.fastIterator(this);
/* 378 */     for (; n-- != 0; h += ((Double2ByteMap.Entry)i.next()).hashCode());
/* 379 */     return h;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean equals(Object o) {
/* 384 */     if (o == this) return true; 
/* 385 */     if (!(o instanceof Map)) return false; 
/* 386 */     Map<?, ?> m = (Map<?, ?>)o;
/* 387 */     if (m.size() != size()) return false; 
/* 388 */     return double2ByteEntrySet().containsAll(m.entrySet());
/*     */   }
/*     */ 
/*     */   
/*     */   public String toString() {
/* 393 */     StringBuilder s = new StringBuilder();
/* 394 */     ObjectIterator<Double2ByteMap.Entry> i = Double2ByteMaps.fastIterator(this);
/* 395 */     int n = size();
/*     */     
/* 397 */     boolean first = true;
/* 398 */     s.append("{");
/* 399 */     while (n-- != 0) {
/* 400 */       if (first) { first = false; }
/* 401 */       else { s.append(", "); }
/* 402 */        Double2ByteMap.Entry e = (Double2ByteMap.Entry)i.next();
/* 403 */       s.append(String.valueOf(e.getDoubleKey()));
/* 404 */       s.append("=>");
/* 405 */       s.append(String.valueOf(e.getByteValue()));
/*     */     } 
/* 407 */     s.append("}");
/* 408 */     return s.toString();
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\i\\unimi\dsi\fastutil\doubles\AbstractDouble2ByteMap.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */