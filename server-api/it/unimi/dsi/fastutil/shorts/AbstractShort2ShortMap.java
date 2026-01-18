/*     */ package it.unimi.dsi.fastutil.shorts;
/*     */ 
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
/*     */ 
/*     */ public abstract class AbstractShort2ShortMap
/*     */   extends AbstractShort2ShortFunction
/*     */   implements Short2ShortMap, Serializable
/*     */ {
/*     */   private static final long serialVersionUID = -4940583368468432370L;
/*     */   
/*     */   public boolean containsKey(short k) {
/*  60 */     ObjectIterator<Short2ShortMap.Entry> i = short2ShortEntrySet().iterator();
/*  61 */     while (i.hasNext()) { if (((Short2ShortMap.Entry)i.next()).getShortKey() == k) return true;  }
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
/*     */   public boolean containsValue(short v) {
/*  80 */     ObjectIterator<Short2ShortMap.Entry> i = short2ShortEntrySet().iterator();
/*  81 */     while (i.hasNext()) { if (((Short2ShortMap.Entry)i.next()).getShortValue() == v) return true;  }
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
/*     */     implements Short2ShortMap.Entry
/*     */   {
/*     */     protected short key;
/*     */ 
/*     */     
/*     */     protected short value;
/*     */ 
/*     */ 
/*     */     
/*     */     public BasicEntry() {}
/*     */ 
/*     */     
/*     */     public BasicEntry(Short key, Short value) {
/* 106 */       this.key = key.shortValue();
/* 107 */       this.value = value.shortValue();
/*     */     }
/*     */     
/*     */     public BasicEntry(short key, short value) {
/* 111 */       this.key = key;
/* 112 */       this.value = value;
/*     */     }
/*     */ 
/*     */     
/*     */     public short getShortKey() {
/* 117 */       return this.key;
/*     */     }
/*     */ 
/*     */     
/*     */     public short getShortValue() {
/* 122 */       return this.value;
/*     */     }
/*     */ 
/*     */     
/*     */     public short setValue(short value) {
/* 127 */       throw new UnsupportedOperationException();
/*     */     }
/*     */ 
/*     */     
/*     */     public boolean equals(Object o) {
/* 132 */       if (!(o instanceof Map.Entry)) return false; 
/* 133 */       if (o instanceof Short2ShortMap.Entry) {
/* 134 */         Short2ShortMap.Entry entry = (Short2ShortMap.Entry)o;
/* 135 */         return (this.key == entry.getShortKey() && this.value == entry.getShortValue());
/*     */       } 
/* 137 */       Map.Entry<?, ?> e = (Map.Entry<?, ?>)o;
/* 138 */       Object key = e.getKey();
/* 139 */       if (key == null || !(key instanceof Short)) return false; 
/* 140 */       Object value = e.getValue();
/* 141 */       if (value == null || !(value instanceof Short)) return false; 
/* 142 */       return (this.key == ((Short)key).shortValue() && this.value == ((Short)value).shortValue());
/*     */     }
/*     */ 
/*     */     
/*     */     public int hashCode() {
/* 147 */       return this.key ^ this.value;
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
/*     */     extends AbstractObjectSet<Short2ShortMap.Entry>
/*     */   {
/*     */     protected final Short2ShortMap map;
/*     */ 
/*     */     
/*     */     public BasicEntrySet(Short2ShortMap map) {
/* 164 */       this.map = map;
/*     */     }
/*     */ 
/*     */     
/*     */     public boolean contains(Object o) {
/* 169 */       if (!(o instanceof Map.Entry)) return false; 
/* 170 */       if (o instanceof Short2ShortMap.Entry) {
/* 171 */         Short2ShortMap.Entry entry = (Short2ShortMap.Entry)o;
/* 172 */         short s = entry.getShortKey();
/* 173 */         return (this.map.containsKey(s) && this.map.get(s) == entry.getShortValue());
/*     */       } 
/* 175 */       Map.Entry<?, ?> e = (Map.Entry<?, ?>)o;
/* 176 */       Object key = e.getKey();
/* 177 */       if (key == null || !(key instanceof Short)) return false; 
/* 178 */       short k = ((Short)key).shortValue();
/* 179 */       Object value = e.getValue();
/* 180 */       if (value == null || !(value instanceof Short)) return false; 
/* 181 */       return (this.map.containsKey(k) && this.map.get(k) == ((Short)value).shortValue());
/*     */     }
/*     */ 
/*     */     
/*     */     public boolean remove(Object o) {
/* 186 */       if (!(o instanceof Map.Entry)) return false; 
/* 187 */       if (o instanceof Short2ShortMap.Entry) {
/* 188 */         Short2ShortMap.Entry entry = (Short2ShortMap.Entry)o;
/* 189 */         return this.map.remove(entry.getShortKey(), entry.getShortValue());
/*     */       } 
/* 191 */       Map.Entry<?, ?> e = (Map.Entry<?, ?>)o;
/* 192 */       Object key = e.getKey();
/* 193 */       if (key == null || !(key instanceof Short)) return false; 
/* 194 */       short k = ((Short)key).shortValue();
/* 195 */       Object value = e.getValue();
/* 196 */       if (value == null || !(value instanceof Short)) return false; 
/* 197 */       short v = ((Short)value).shortValue();
/* 198 */       return this.map.remove(k, v);
/*     */     }
/*     */ 
/*     */     
/*     */     public int size() {
/* 203 */       return this.map.size();
/*     */     }
/*     */ 
/*     */     
/*     */     public ObjectSpliterator<Short2ShortMap.Entry> spliterator() {
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
/*     */   public ShortSet keySet() {
/* 226 */     return new AbstractShortSet()
/*     */       {
/*     */         public boolean contains(short k) {
/* 229 */           return AbstractShort2ShortMap.this.containsKey(k);
/*     */         }
/*     */ 
/*     */         
/*     */         public int size() {
/* 234 */           return AbstractShort2ShortMap.this.size();
/*     */         }
/*     */ 
/*     */         
/*     */         public void clear() {
/* 239 */           AbstractShort2ShortMap.this.clear();
/*     */         }
/*     */ 
/*     */         
/*     */         public ShortIterator iterator() {
/* 244 */           return new ShortIterator() {
/* 245 */               private final ObjectIterator<Short2ShortMap.Entry> i = Short2ShortMaps.fastIterator(AbstractShort2ShortMap.this);
/*     */ 
/*     */               
/*     */               public short nextShort() {
/* 249 */                 return ((Short2ShortMap.Entry)this.i.next()).getShortKey();
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
/*     */               public void forEachRemaining(ShortConsumer action) {
/* 264 */                 this.i.forEachRemaining(entry -> action.accept(entry.getShortKey()));
/*     */               }
/*     */             };
/*     */         }
/*     */ 
/*     */         
/*     */         public ShortSpliterator spliterator() {
/* 271 */           return ShortSpliterators.asSpliterator(iterator(), Size64.sizeOf(AbstractShort2ShortMap.this), 321);
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
/*     */   public ShortCollection values() {
/* 290 */     return new AbstractShortCollection()
/*     */       {
/*     */         public boolean contains(short k) {
/* 293 */           return AbstractShort2ShortMap.this.containsValue(k);
/*     */         }
/*     */ 
/*     */         
/*     */         public int size() {
/* 298 */           return AbstractShort2ShortMap.this.size();
/*     */         }
/*     */ 
/*     */         
/*     */         public void clear() {
/* 303 */           AbstractShort2ShortMap.this.clear();
/*     */         }
/*     */ 
/*     */         
/*     */         public ShortIterator iterator() {
/* 308 */           return new ShortIterator() {
/* 309 */               private final ObjectIterator<Short2ShortMap.Entry> i = Short2ShortMaps.fastIterator(AbstractShort2ShortMap.this);
/*     */ 
/*     */               
/*     */               public short nextShort() {
/* 313 */                 return ((Short2ShortMap.Entry)this.i.next()).getShortValue();
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
/*     */               public void forEachRemaining(ShortConsumer action) {
/* 328 */                 this.i.forEachRemaining(entry -> action.accept(entry.getShortValue()));
/*     */               }
/*     */             };
/*     */         }
/*     */ 
/*     */         
/*     */         public ShortSpliterator spliterator() {
/* 335 */           return ShortSpliterators.asSpliterator(iterator(), Size64.sizeOf(AbstractShort2ShortMap.this), 320);
/*     */         }
/*     */       };
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void putAll(Map<? extends Short, ? extends Short> m) {
/* 344 */     if (m instanceof Short2ShortMap) {
/* 345 */       ObjectIterator<Short2ShortMap.Entry> i = Short2ShortMaps.fastIterator((Short2ShortMap)m);
/* 346 */       while (i.hasNext()) {
/* 347 */         Short2ShortMap.Entry e = (Short2ShortMap.Entry)i.next();
/* 348 */         put(e.getShortKey(), e.getShortValue());
/*     */       } 
/*     */     } else {
/* 351 */       int n = m.size();
/* 352 */       Iterator<? extends Map.Entry<? extends Short, ? extends Short>> i = m.entrySet().iterator();
/*     */       
/* 354 */       while (n-- != 0) {
/* 355 */         Map.Entry<? extends Short, ? extends Short> e = i.next();
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
/* 371 */     ObjectIterator<Short2ShortMap.Entry> i = Short2ShortMaps.fastIterator(this);
/* 372 */     for (; n-- != 0; h += ((Short2ShortMap.Entry)i.next()).hashCode());
/* 373 */     return h;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean equals(Object o) {
/* 378 */     if (o == this) return true; 
/* 379 */     if (!(o instanceof Map)) return false; 
/* 380 */     Map<?, ?> m = (Map<?, ?>)o;
/* 381 */     if (m.size() != size()) return false; 
/* 382 */     return short2ShortEntrySet().containsAll(m.entrySet());
/*     */   }
/*     */ 
/*     */   
/*     */   public String toString() {
/* 387 */     StringBuilder s = new StringBuilder();
/* 388 */     ObjectIterator<Short2ShortMap.Entry> i = Short2ShortMaps.fastIterator(this);
/* 389 */     int n = size();
/*     */     
/* 391 */     boolean first = true;
/* 392 */     s.append("{");
/* 393 */     while (n-- != 0) {
/* 394 */       if (first) { first = false; }
/* 395 */       else { s.append(", "); }
/* 396 */        Short2ShortMap.Entry e = (Short2ShortMap.Entry)i.next();
/* 397 */       s.append(String.valueOf(e.getShortKey()));
/* 398 */       s.append("=>");
/* 399 */       s.append(String.valueOf(e.getShortValue()));
/*     */     } 
/* 401 */     s.append("}");
/* 402 */     return s.toString();
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\i\\unimi\dsi\fastutil\shorts\AbstractShort2ShortMap.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */