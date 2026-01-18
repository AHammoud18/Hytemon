/*     */ package it.unimi.dsi.fastutil.floats;
/*     */ 
/*     */ import it.unimi.dsi.fastutil.HashCommon;
/*     */ import it.unimi.dsi.fastutil.Size64;
/*     */ import it.unimi.dsi.fastutil.ints.AbstractIntCollection;
/*     */ import it.unimi.dsi.fastutil.ints.IntBinaryOperator;
/*     */ import it.unimi.dsi.fastutil.ints.IntCollection;
/*     */ import it.unimi.dsi.fastutil.ints.IntIterator;
/*     */ import it.unimi.dsi.fastutil.ints.IntSpliterator;
/*     */ import it.unimi.dsi.fastutil.ints.IntSpliterators;
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
/*     */ import java.util.function.IntBinaryOperator;
/*     */ import java.util.function.IntConsumer;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public abstract class AbstractFloat2IntMap
/*     */   extends AbstractFloat2IntFunction
/*     */   implements Float2IntMap, Serializable
/*     */ {
/*     */   private static final long serialVersionUID = -4940583368468432370L;
/*     */   
/*     */   public boolean containsKey(float k) {
/*  65 */     ObjectIterator<Float2IntMap.Entry> i = float2IntEntrySet().iterator();
/*  66 */     while (i.hasNext()) { if (((Float2IntMap.Entry)i.next()).getFloatKey() == k) return true;  }
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
/*     */   public boolean containsValue(int v) {
/*  85 */     ObjectIterator<Float2IntMap.Entry> i = float2IntEntrySet().iterator();
/*  86 */     while (i.hasNext()) { if (((Float2IntMap.Entry)i.next()).getIntValue() == v) return true;  }
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
/*     */   public final int mergeInt(float key, int value, IntBinaryOperator remappingFunction) {
/* 103 */     return mergeInt(key, value, (IntBinaryOperator)remappingFunction);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public static class BasicEntry
/*     */     implements Float2IntMap.Entry
/*     */   {
/*     */     protected float key;
/*     */ 
/*     */     
/*     */     protected int value;
/*     */ 
/*     */ 
/*     */     
/*     */     public BasicEntry() {}
/*     */ 
/*     */     
/*     */     public BasicEntry(Float key, Integer value) {
/* 122 */       this.key = key.floatValue();
/* 123 */       this.value = value.intValue();
/*     */     }
/*     */     
/*     */     public BasicEntry(float key, int value) {
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
/*     */     public int getIntValue() {
/* 138 */       return this.value;
/*     */     }
/*     */ 
/*     */     
/*     */     public int setValue(int value) {
/* 143 */       throw new UnsupportedOperationException();
/*     */     }
/*     */ 
/*     */     
/*     */     public boolean equals(Object o) {
/* 148 */       if (!(o instanceof Map.Entry)) return false; 
/* 149 */       if (o instanceof Float2IntMap.Entry) {
/* 150 */         Float2IntMap.Entry entry = (Float2IntMap.Entry)o;
/* 151 */         return (Float.floatToIntBits(this.key) == Float.floatToIntBits(entry.getFloatKey()) && this.value == entry.getIntValue());
/*     */       } 
/* 153 */       Map.Entry<?, ?> e = (Map.Entry<?, ?>)o;
/* 154 */       Object key = e.getKey();
/* 155 */       if (key == null || !(key instanceof Float)) return false; 
/* 156 */       Object value = e.getValue();
/* 157 */       if (value == null || !(value instanceof Integer)) return false; 
/* 158 */       return (Float.floatToIntBits(this.key) == Float.floatToIntBits(((Float)key).floatValue()) && this.value == ((Integer)value).intValue());
/*     */     }
/*     */ 
/*     */     
/*     */     public int hashCode() {
/* 163 */       return HashCommon.float2int(this.key) ^ this.value;
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
/*     */     extends AbstractObjectSet<Float2IntMap.Entry>
/*     */   {
/*     */     protected final Float2IntMap map;
/*     */ 
/*     */     
/*     */     public BasicEntrySet(Float2IntMap map) {
/* 180 */       this.map = map;
/*     */     }
/*     */ 
/*     */     
/*     */     public boolean contains(Object o) {
/* 185 */       if (!(o instanceof Map.Entry)) return false; 
/* 186 */       if (o instanceof Float2IntMap.Entry) {
/* 187 */         Float2IntMap.Entry entry = (Float2IntMap.Entry)o;
/* 188 */         float f = entry.getFloatKey();
/* 189 */         return (this.map.containsKey(f) && this.map.get(f) == entry.getIntValue());
/*     */       } 
/* 191 */       Map.Entry<?, ?> e = (Map.Entry<?, ?>)o;
/* 192 */       Object key = e.getKey();
/* 193 */       if (key == null || !(key instanceof Float)) return false; 
/* 194 */       float k = ((Float)key).floatValue();
/* 195 */       Object value = e.getValue();
/* 196 */       if (value == null || !(value instanceof Integer)) return false; 
/* 197 */       return (this.map.containsKey(k) && this.map.get(k) == ((Integer)value).intValue());
/*     */     }
/*     */ 
/*     */     
/*     */     public boolean remove(Object o) {
/* 202 */       if (!(o instanceof Map.Entry)) return false; 
/* 203 */       if (o instanceof Float2IntMap.Entry) {
/* 204 */         Float2IntMap.Entry entry = (Float2IntMap.Entry)o;
/* 205 */         return this.map.remove(entry.getFloatKey(), entry.getIntValue());
/*     */       } 
/* 207 */       Map.Entry<?, ?> e = (Map.Entry<?, ?>)o;
/* 208 */       Object key = e.getKey();
/* 209 */       if (key == null || !(key instanceof Float)) return false; 
/* 210 */       float k = ((Float)key).floatValue();
/* 211 */       Object value = e.getValue();
/* 212 */       if (value == null || !(value instanceof Integer)) return false; 
/* 213 */       int v = ((Integer)value).intValue();
/* 214 */       return this.map.remove(k, v);
/*     */     }
/*     */ 
/*     */     
/*     */     public int size() {
/* 219 */       return this.map.size();
/*     */     }
/*     */ 
/*     */     
/*     */     public ObjectSpliterator<Float2IntMap.Entry> spliterator() {
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
/* 245 */           return AbstractFloat2IntMap.this.containsKey(k);
/*     */         }
/*     */ 
/*     */         
/*     */         public int size() {
/* 250 */           return AbstractFloat2IntMap.this.size();
/*     */         }
/*     */ 
/*     */         
/*     */         public void clear() {
/* 255 */           AbstractFloat2IntMap.this.clear();
/*     */         }
/*     */ 
/*     */         
/*     */         public FloatIterator iterator() {
/* 260 */           return new FloatIterator() {
/* 261 */               private final ObjectIterator<Float2IntMap.Entry> i = Float2IntMaps.fastIterator(AbstractFloat2IntMap.this);
/*     */ 
/*     */               
/*     */               public float nextFloat() {
/* 265 */                 return ((Float2IntMap.Entry)this.i.next()).getFloatKey();
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
/* 287 */           return FloatSpliterators.asSpliterator(iterator(), Size64.sizeOf(AbstractFloat2IntMap.this), 321);
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
/*     */   public IntCollection values() {
/* 306 */     return (IntCollection)new AbstractIntCollection()
/*     */       {
/*     */         public boolean contains(int k) {
/* 309 */           return AbstractFloat2IntMap.this.containsValue(k);
/*     */         }
/*     */ 
/*     */         
/*     */         public int size() {
/* 314 */           return AbstractFloat2IntMap.this.size();
/*     */         }
/*     */ 
/*     */         
/*     */         public void clear() {
/* 319 */           AbstractFloat2IntMap.this.clear();
/*     */         }
/*     */ 
/*     */         
/*     */         public IntIterator iterator() {
/* 324 */           return new IntIterator() {
/* 325 */               private final ObjectIterator<Float2IntMap.Entry> i = Float2IntMaps.fastIterator(AbstractFloat2IntMap.this);
/*     */ 
/*     */               
/*     */               public int nextInt() {
/* 329 */                 return ((Float2IntMap.Entry)this.i.next()).getIntValue();
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
/*     */               public void forEachRemaining(IntConsumer action) {
/* 344 */                 this.i.forEachRemaining(entry -> action.accept(entry.getIntValue()));
/*     */               }
/*     */             };
/*     */         }
/*     */ 
/*     */         
/*     */         public IntSpliterator spliterator() {
/* 351 */           return IntSpliterators.asSpliterator(iterator(), Size64.sizeOf(AbstractFloat2IntMap.this), 320);
/*     */         }
/*     */       };
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void putAll(Map<? extends Float, ? extends Integer> m) {
/* 360 */     if (m instanceof Float2IntMap) {
/* 361 */       ObjectIterator<Float2IntMap.Entry> i = Float2IntMaps.fastIterator((Float2IntMap)m);
/* 362 */       while (i.hasNext()) {
/* 363 */         Float2IntMap.Entry e = (Float2IntMap.Entry)i.next();
/* 364 */         put(e.getFloatKey(), e.getIntValue());
/*     */       } 
/*     */     } else {
/* 367 */       int n = m.size();
/* 368 */       Iterator<? extends Map.Entry<? extends Float, ? extends Integer>> i = m.entrySet().iterator();
/*     */       
/* 370 */       while (n-- != 0) {
/* 371 */         Map.Entry<? extends Float, ? extends Integer> e = i.next();
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
/* 387 */     ObjectIterator<Float2IntMap.Entry> i = Float2IntMaps.fastIterator(this);
/* 388 */     for (; n-- != 0; h += ((Float2IntMap.Entry)i.next()).hashCode());
/* 389 */     return h;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean equals(Object o) {
/* 394 */     if (o == this) return true; 
/* 395 */     if (!(o instanceof Map)) return false; 
/* 396 */     Map<?, ?> m = (Map<?, ?>)o;
/* 397 */     if (m.size() != size()) return false; 
/* 398 */     return float2IntEntrySet().containsAll(m.entrySet());
/*     */   }
/*     */ 
/*     */   
/*     */   public String toString() {
/* 403 */     StringBuilder s = new StringBuilder();
/* 404 */     ObjectIterator<Float2IntMap.Entry> i = Float2IntMaps.fastIterator(this);
/* 405 */     int n = size();
/*     */     
/* 407 */     boolean first = true;
/* 408 */     s.append("{");
/* 409 */     while (n-- != 0) {
/* 410 */       if (first) { first = false; }
/* 411 */       else { s.append(", "); }
/* 412 */        Float2IntMap.Entry e = (Float2IntMap.Entry)i.next();
/* 413 */       s.append(String.valueOf(e.getFloatKey()));
/* 414 */       s.append("=>");
/* 415 */       s.append(String.valueOf(e.getIntValue()));
/*     */     } 
/* 417 */     s.append("}");
/* 418 */     return s.toString();
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\i\\unimi\dsi\fastutil\floats\AbstractFloat2IntMap.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */