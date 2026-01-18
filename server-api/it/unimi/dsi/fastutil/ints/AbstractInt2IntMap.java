/*     */ package it.unimi.dsi.fastutil.ints;
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
/*     */ 
/*     */ 
/*     */ public abstract class AbstractInt2IntMap
/*     */   extends AbstractInt2IntFunction
/*     */   implements Int2IntMap, Serializable
/*     */ {
/*     */   private static final long serialVersionUID = -4940583368468432370L;
/*     */   
/*     */   public boolean containsKey(int k) {
/*  60 */     ObjectIterator<Int2IntMap.Entry> i = int2IntEntrySet().iterator();
/*  61 */     while (i.hasNext()) { if (((Int2IntMap.Entry)i.next()).getIntKey() == k) return true;  }
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
/*     */   public boolean containsValue(int v) {
/*  80 */     ObjectIterator<Int2IntMap.Entry> i = int2IntEntrySet().iterator();
/*  81 */     while (i.hasNext()) { if (((Int2IntMap.Entry)i.next()).getIntValue() == v) return true;  }
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
/*     */   public final int mergeInt(int key, int value, IntBinaryOperator remappingFunction) {
/*  98 */     return mergeInt(key, value, remappingFunction);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public static class BasicEntry
/*     */     implements Int2IntMap.Entry
/*     */   {
/*     */     protected int key;
/*     */ 
/*     */     
/*     */     protected int value;
/*     */ 
/*     */ 
/*     */     
/*     */     public BasicEntry() {}
/*     */ 
/*     */     
/*     */     public BasicEntry(Integer key, Integer value) {
/* 117 */       this.key = key.intValue();
/* 118 */       this.value = value.intValue();
/*     */     }
/*     */     
/*     */     public BasicEntry(int key, int value) {
/* 122 */       this.key = key;
/* 123 */       this.value = value;
/*     */     }
/*     */ 
/*     */     
/*     */     public int getIntKey() {
/* 128 */       return this.key;
/*     */     }
/*     */ 
/*     */     
/*     */     public int getIntValue() {
/* 133 */       return this.value;
/*     */     }
/*     */ 
/*     */     
/*     */     public int setValue(int value) {
/* 138 */       throw new UnsupportedOperationException();
/*     */     }
/*     */ 
/*     */     
/*     */     public boolean equals(Object o) {
/* 143 */       if (!(o instanceof Map.Entry)) return false; 
/* 144 */       if (o instanceof Int2IntMap.Entry) {
/* 145 */         Int2IntMap.Entry entry = (Int2IntMap.Entry)o;
/* 146 */         return (this.key == entry.getIntKey() && this.value == entry.getIntValue());
/*     */       } 
/* 148 */       Map.Entry<?, ?> e = (Map.Entry<?, ?>)o;
/* 149 */       Object key = e.getKey();
/* 150 */       if (key == null || !(key instanceof Integer)) return false; 
/* 151 */       Object value = e.getValue();
/* 152 */       if (value == null || !(value instanceof Integer)) return false; 
/* 153 */       return (this.key == ((Integer)key).intValue() && this.value == ((Integer)value).intValue());
/*     */     }
/*     */ 
/*     */     
/*     */     public int hashCode() {
/* 158 */       return this.key ^ this.value;
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
/*     */     extends AbstractObjectSet<Int2IntMap.Entry>
/*     */   {
/*     */     protected final Int2IntMap map;
/*     */ 
/*     */     
/*     */     public BasicEntrySet(Int2IntMap map) {
/* 175 */       this.map = map;
/*     */     }
/*     */ 
/*     */     
/*     */     public boolean contains(Object o) {
/* 180 */       if (!(o instanceof Map.Entry)) return false; 
/* 181 */       if (o instanceof Int2IntMap.Entry) {
/* 182 */         Int2IntMap.Entry entry = (Int2IntMap.Entry)o;
/* 183 */         int i = entry.getIntKey();
/* 184 */         return (this.map.containsKey(i) && this.map.get(i) == entry.getIntValue());
/*     */       } 
/* 186 */       Map.Entry<?, ?> e = (Map.Entry<?, ?>)o;
/* 187 */       Object key = e.getKey();
/* 188 */       if (key == null || !(key instanceof Integer)) return false; 
/* 189 */       int k = ((Integer)key).intValue();
/* 190 */       Object value = e.getValue();
/* 191 */       if (value == null || !(value instanceof Integer)) return false; 
/* 192 */       return (this.map.containsKey(k) && this.map.get(k) == ((Integer)value).intValue());
/*     */     }
/*     */ 
/*     */     
/*     */     public boolean remove(Object o) {
/* 197 */       if (!(o instanceof Map.Entry)) return false; 
/* 198 */       if (o instanceof Int2IntMap.Entry) {
/* 199 */         Int2IntMap.Entry entry = (Int2IntMap.Entry)o;
/* 200 */         return this.map.remove(entry.getIntKey(), entry.getIntValue());
/*     */       } 
/* 202 */       Map.Entry<?, ?> e = (Map.Entry<?, ?>)o;
/* 203 */       Object key = e.getKey();
/* 204 */       if (key == null || !(key instanceof Integer)) return false; 
/* 205 */       int k = ((Integer)key).intValue();
/* 206 */       Object value = e.getValue();
/* 207 */       if (value == null || !(value instanceof Integer)) return false; 
/* 208 */       int v = ((Integer)value).intValue();
/* 209 */       return this.map.remove(k, v);
/*     */     }
/*     */ 
/*     */     
/*     */     public int size() {
/* 214 */       return this.map.size();
/*     */     }
/*     */ 
/*     */     
/*     */     public ObjectSpliterator<Int2IntMap.Entry> spliterator() {
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
/*     */   public IntSet keySet() {
/* 237 */     return new AbstractIntSet()
/*     */       {
/*     */         public boolean contains(int k) {
/* 240 */           return AbstractInt2IntMap.this.containsKey(k);
/*     */         }
/*     */ 
/*     */         
/*     */         public int size() {
/* 245 */           return AbstractInt2IntMap.this.size();
/*     */         }
/*     */ 
/*     */         
/*     */         public void clear() {
/* 250 */           AbstractInt2IntMap.this.clear();
/*     */         }
/*     */ 
/*     */         
/*     */         public IntIterator iterator() {
/* 255 */           return new IntIterator() {
/* 256 */               private final ObjectIterator<Int2IntMap.Entry> i = Int2IntMaps.fastIterator(AbstractInt2IntMap.this);
/*     */ 
/*     */               
/*     */               public int nextInt() {
/* 260 */                 return ((Int2IntMap.Entry)this.i.next()).getIntKey();
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
/*     */               public void forEachRemaining(IntConsumer action) {
/* 275 */                 this.i.forEachRemaining(entry -> action.accept(entry.getIntKey()));
/*     */               }
/*     */             };
/*     */         }
/*     */ 
/*     */         
/*     */         public IntSpliterator spliterator() {
/* 282 */           return IntSpliterators.asSpliterator(iterator(), Size64.sizeOf(AbstractInt2IntMap.this), 321);
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
/* 301 */     return new AbstractIntCollection()
/*     */       {
/*     */         public boolean contains(int k) {
/* 304 */           return AbstractInt2IntMap.this.containsValue(k);
/*     */         }
/*     */ 
/*     */         
/*     */         public int size() {
/* 309 */           return AbstractInt2IntMap.this.size();
/*     */         }
/*     */ 
/*     */         
/*     */         public void clear() {
/* 314 */           AbstractInt2IntMap.this.clear();
/*     */         }
/*     */ 
/*     */         
/*     */         public IntIterator iterator() {
/* 319 */           return new IntIterator() {
/* 320 */               private final ObjectIterator<Int2IntMap.Entry> i = Int2IntMaps.fastIterator(AbstractInt2IntMap.this);
/*     */ 
/*     */               
/*     */               public int nextInt() {
/* 324 */                 return ((Int2IntMap.Entry)this.i.next()).getIntValue();
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
/*     */               public void forEachRemaining(IntConsumer action) {
/* 339 */                 this.i.forEachRemaining(entry -> action.accept(entry.getIntValue()));
/*     */               }
/*     */             };
/*     */         }
/*     */ 
/*     */         
/*     */         public IntSpliterator spliterator() {
/* 346 */           return IntSpliterators.asSpliterator(iterator(), Size64.sizeOf(AbstractInt2IntMap.this), 320);
/*     */         }
/*     */       };
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void putAll(Map<? extends Integer, ? extends Integer> m) {
/* 355 */     if (m instanceof Int2IntMap) {
/* 356 */       ObjectIterator<Int2IntMap.Entry> i = Int2IntMaps.fastIterator((Int2IntMap)m);
/* 357 */       while (i.hasNext()) {
/* 358 */         Int2IntMap.Entry e = (Int2IntMap.Entry)i.next();
/* 359 */         put(e.getIntKey(), e.getIntValue());
/*     */       } 
/*     */     } else {
/* 362 */       int n = m.size();
/* 363 */       Iterator<? extends Map.Entry<? extends Integer, ? extends Integer>> i = m.entrySet().iterator();
/*     */       
/* 365 */       while (n-- != 0) {
/* 366 */         Map.Entry<? extends Integer, ? extends Integer> e = i.next();
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
/* 382 */     ObjectIterator<Int2IntMap.Entry> i = Int2IntMaps.fastIterator(this);
/* 383 */     for (; n-- != 0; h += ((Int2IntMap.Entry)i.next()).hashCode());
/* 384 */     return h;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean equals(Object o) {
/* 389 */     if (o == this) return true; 
/* 390 */     if (!(o instanceof Map)) return false; 
/* 391 */     Map<?, ?> m = (Map<?, ?>)o;
/* 392 */     if (m.size() != size()) return false; 
/* 393 */     return int2IntEntrySet().containsAll(m.entrySet());
/*     */   }
/*     */ 
/*     */   
/*     */   public String toString() {
/* 398 */     StringBuilder s = new StringBuilder();
/* 399 */     ObjectIterator<Int2IntMap.Entry> i = Int2IntMaps.fastIterator(this);
/* 400 */     int n = size();
/*     */     
/* 402 */     boolean first = true;
/* 403 */     s.append("{");
/* 404 */     while (n-- != 0) {
/* 405 */       if (first) { first = false; }
/* 406 */       else { s.append(", "); }
/* 407 */        Int2IntMap.Entry e = (Int2IntMap.Entry)i.next();
/* 408 */       s.append(String.valueOf(e.getIntKey()));
/* 409 */       s.append("=>");
/* 410 */       s.append(String.valueOf(e.getIntValue()));
/*     */     } 
/* 412 */     s.append("}");
/* 413 */     return s.toString();
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\i\\unimi\dsi\fastutil\ints\AbstractInt2IntMap.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */