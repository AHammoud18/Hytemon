/*     */ package it.unimi.dsi.fastutil.shorts;
/*     */ 
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
/*     */ 
/*     */ public abstract class AbstractShort2IntMap
/*     */   extends AbstractShort2IntFunction
/*     */   implements Short2IntMap, Serializable
/*     */ {
/*     */   private static final long serialVersionUID = -4940583368468432370L;
/*     */   
/*     */   public boolean containsKey(short k) {
/*  65 */     ObjectIterator<Short2IntMap.Entry> i = short2IntEntrySet().iterator();
/*  66 */     while (i.hasNext()) { if (((Short2IntMap.Entry)i.next()).getShortKey() == k) return true;  }
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
/*  85 */     ObjectIterator<Short2IntMap.Entry> i = short2IntEntrySet().iterator();
/*  86 */     while (i.hasNext()) { if (((Short2IntMap.Entry)i.next()).getIntValue() == v) return true;  }
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
/*     */   public final int mergeInt(short key, int value, IntBinaryOperator remappingFunction) {
/* 103 */     return mergeInt(key, value, (IntBinaryOperator)remappingFunction);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public static class BasicEntry
/*     */     implements Short2IntMap.Entry
/*     */   {
/*     */     protected short key;
/*     */ 
/*     */     
/*     */     protected int value;
/*     */ 
/*     */ 
/*     */     
/*     */     public BasicEntry() {}
/*     */ 
/*     */     
/*     */     public BasicEntry(Short key, Integer value) {
/* 122 */       this.key = key.shortValue();
/* 123 */       this.value = value.intValue();
/*     */     }
/*     */     
/*     */     public BasicEntry(short key, int value) {
/* 127 */       this.key = key;
/* 128 */       this.value = value;
/*     */     }
/*     */ 
/*     */     
/*     */     public short getShortKey() {
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
/* 149 */       if (o instanceof Short2IntMap.Entry) {
/* 150 */         Short2IntMap.Entry entry = (Short2IntMap.Entry)o;
/* 151 */         return (this.key == entry.getShortKey() && this.value == entry.getIntValue());
/*     */       } 
/* 153 */       Map.Entry<?, ?> e = (Map.Entry<?, ?>)o;
/* 154 */       Object key = e.getKey();
/* 155 */       if (key == null || !(key instanceof Short)) return false; 
/* 156 */       Object value = e.getValue();
/* 157 */       if (value == null || !(value instanceof Integer)) return false; 
/* 158 */       return (this.key == ((Short)key).shortValue() && this.value == ((Integer)value).intValue());
/*     */     }
/*     */ 
/*     */     
/*     */     public int hashCode() {
/* 163 */       return this.key ^ this.value;
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
/*     */     extends AbstractObjectSet<Short2IntMap.Entry>
/*     */   {
/*     */     protected final Short2IntMap map;
/*     */ 
/*     */     
/*     */     public BasicEntrySet(Short2IntMap map) {
/* 180 */       this.map = map;
/*     */     }
/*     */ 
/*     */     
/*     */     public boolean contains(Object o) {
/* 185 */       if (!(o instanceof Map.Entry)) return false; 
/* 186 */       if (o instanceof Short2IntMap.Entry) {
/* 187 */         Short2IntMap.Entry entry = (Short2IntMap.Entry)o;
/* 188 */         short s = entry.getShortKey();
/* 189 */         return (this.map.containsKey(s) && this.map.get(s) == entry.getIntValue());
/*     */       } 
/* 191 */       Map.Entry<?, ?> e = (Map.Entry<?, ?>)o;
/* 192 */       Object key = e.getKey();
/* 193 */       if (key == null || !(key instanceof Short)) return false; 
/* 194 */       short k = ((Short)key).shortValue();
/* 195 */       Object value = e.getValue();
/* 196 */       if (value == null || !(value instanceof Integer)) return false; 
/* 197 */       return (this.map.containsKey(k) && this.map.get(k) == ((Integer)value).intValue());
/*     */     }
/*     */ 
/*     */     
/*     */     public boolean remove(Object o) {
/* 202 */       if (!(o instanceof Map.Entry)) return false; 
/* 203 */       if (o instanceof Short2IntMap.Entry) {
/* 204 */         Short2IntMap.Entry entry = (Short2IntMap.Entry)o;
/* 205 */         return this.map.remove(entry.getShortKey(), entry.getIntValue());
/*     */       } 
/* 207 */       Map.Entry<?, ?> e = (Map.Entry<?, ?>)o;
/* 208 */       Object key = e.getKey();
/* 209 */       if (key == null || !(key instanceof Short)) return false; 
/* 210 */       short k = ((Short)key).shortValue();
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
/*     */     public ObjectSpliterator<Short2IntMap.Entry> spliterator() {
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
/*     */   public ShortSet keySet() {
/* 242 */     return new AbstractShortSet()
/*     */       {
/*     */         public boolean contains(short k) {
/* 245 */           return AbstractShort2IntMap.this.containsKey(k);
/*     */         }
/*     */ 
/*     */         
/*     */         public int size() {
/* 250 */           return AbstractShort2IntMap.this.size();
/*     */         }
/*     */ 
/*     */         
/*     */         public void clear() {
/* 255 */           AbstractShort2IntMap.this.clear();
/*     */         }
/*     */ 
/*     */         
/*     */         public ShortIterator iterator() {
/* 260 */           return new ShortIterator() {
/* 261 */               private final ObjectIterator<Short2IntMap.Entry> i = Short2IntMaps.fastIterator(AbstractShort2IntMap.this);
/*     */ 
/*     */               
/*     */               public short nextShort() {
/* 265 */                 return ((Short2IntMap.Entry)this.i.next()).getShortKey();
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
/*     */               public void forEachRemaining(ShortConsumer action) {
/* 280 */                 this.i.forEachRemaining(entry -> action.accept(entry.getShortKey()));
/*     */               }
/*     */             };
/*     */         }
/*     */ 
/*     */         
/*     */         public ShortSpliterator spliterator() {
/* 287 */           return ShortSpliterators.asSpliterator(iterator(), Size64.sizeOf(AbstractShort2IntMap.this), 321);
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
/* 309 */           return AbstractShort2IntMap.this.containsValue(k);
/*     */         }
/*     */ 
/*     */         
/*     */         public int size() {
/* 314 */           return AbstractShort2IntMap.this.size();
/*     */         }
/*     */ 
/*     */         
/*     */         public void clear() {
/* 319 */           AbstractShort2IntMap.this.clear();
/*     */         }
/*     */ 
/*     */         
/*     */         public IntIterator iterator() {
/* 324 */           return new IntIterator() {
/* 325 */               private final ObjectIterator<Short2IntMap.Entry> i = Short2IntMaps.fastIterator(AbstractShort2IntMap.this);
/*     */ 
/*     */               
/*     */               public int nextInt() {
/* 329 */                 return ((Short2IntMap.Entry)this.i.next()).getIntValue();
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
/* 351 */           return IntSpliterators.asSpliterator(iterator(), Size64.sizeOf(AbstractShort2IntMap.this), 320);
/*     */         }
/*     */       };
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void putAll(Map<? extends Short, ? extends Integer> m) {
/* 360 */     if (m instanceof Short2IntMap) {
/* 361 */       ObjectIterator<Short2IntMap.Entry> i = Short2IntMaps.fastIterator((Short2IntMap)m);
/* 362 */       while (i.hasNext()) {
/* 363 */         Short2IntMap.Entry e = (Short2IntMap.Entry)i.next();
/* 364 */         put(e.getShortKey(), e.getIntValue());
/*     */       } 
/*     */     } else {
/* 367 */       int n = m.size();
/* 368 */       Iterator<? extends Map.Entry<? extends Short, ? extends Integer>> i = m.entrySet().iterator();
/*     */       
/* 370 */       while (n-- != 0) {
/* 371 */         Map.Entry<? extends Short, ? extends Integer> e = i.next();
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
/* 387 */     ObjectIterator<Short2IntMap.Entry> i = Short2IntMaps.fastIterator(this);
/* 388 */     for (; n-- != 0; h += ((Short2IntMap.Entry)i.next()).hashCode());
/* 389 */     return h;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean equals(Object o) {
/* 394 */     if (o == this) return true; 
/* 395 */     if (!(o instanceof Map)) return false; 
/* 396 */     Map<?, ?> m = (Map<?, ?>)o;
/* 397 */     if (m.size() != size()) return false; 
/* 398 */     return short2IntEntrySet().containsAll(m.entrySet());
/*     */   }
/*     */ 
/*     */   
/*     */   public String toString() {
/* 403 */     StringBuilder s = new StringBuilder();
/* 404 */     ObjectIterator<Short2IntMap.Entry> i = Short2IntMaps.fastIterator(this);
/* 405 */     int n = size();
/*     */     
/* 407 */     boolean first = true;
/* 408 */     s.append("{");
/* 409 */     while (n-- != 0) {
/* 410 */       if (first) { first = false; }
/* 411 */       else { s.append(", "); }
/* 412 */        Short2IntMap.Entry e = (Short2IntMap.Entry)i.next();
/* 413 */       s.append(String.valueOf(e.getShortKey()));
/* 414 */       s.append("=>");
/* 415 */       s.append(String.valueOf(e.getIntValue()));
/*     */     } 
/* 417 */     s.append("}");
/* 418 */     return s.toString();
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\i\\unimi\dsi\fastutil\shorts\AbstractShort2IntMap.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */