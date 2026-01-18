/*     */ package it.unimi.dsi.fastutil.longs;
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
/*     */ public abstract class AbstractLong2LongMap
/*     */   extends AbstractLong2LongFunction
/*     */   implements Long2LongMap, Serializable
/*     */ {
/*     */   private static final long serialVersionUID = -4940583368468432370L;
/*     */   
/*     */   public boolean containsKey(long k) {
/*  60 */     ObjectIterator<Long2LongMap.Entry> i = long2LongEntrySet().iterator();
/*  61 */     while (i.hasNext()) { if (((Long2LongMap.Entry)i.next()).getLongKey() == k) return true;  }
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
/*     */   public boolean containsValue(long v) {
/*  80 */     ObjectIterator<Long2LongMap.Entry> i = long2LongEntrySet().iterator();
/*  81 */     while (i.hasNext()) { if (((Long2LongMap.Entry)i.next()).getLongValue() == v) return true;  }
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
/*     */   public final long mergeLong(long key, long value, LongBinaryOperator remappingFunction) {
/*  98 */     return mergeLong(key, value, remappingFunction);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public static class BasicEntry
/*     */     implements Long2LongMap.Entry
/*     */   {
/*     */     protected long key;
/*     */ 
/*     */     
/*     */     protected long value;
/*     */ 
/*     */ 
/*     */     
/*     */     public BasicEntry() {}
/*     */ 
/*     */     
/*     */     public BasicEntry(Long key, Long value) {
/* 117 */       this.key = key.longValue();
/* 118 */       this.value = value.longValue();
/*     */     }
/*     */     
/*     */     public BasicEntry(long key, long value) {
/* 122 */       this.key = key;
/* 123 */       this.value = value;
/*     */     }
/*     */ 
/*     */     
/*     */     public long getLongKey() {
/* 128 */       return this.key;
/*     */     }
/*     */ 
/*     */     
/*     */     public long getLongValue() {
/* 133 */       return this.value;
/*     */     }
/*     */ 
/*     */     
/*     */     public long setValue(long value) {
/* 138 */       throw new UnsupportedOperationException();
/*     */     }
/*     */ 
/*     */     
/*     */     public boolean equals(Object o) {
/* 143 */       if (!(o instanceof Map.Entry)) return false; 
/* 144 */       if (o instanceof Long2LongMap.Entry) {
/* 145 */         Long2LongMap.Entry entry = (Long2LongMap.Entry)o;
/* 146 */         return (this.key == entry.getLongKey() && this.value == entry.getLongValue());
/*     */       } 
/* 148 */       Map.Entry<?, ?> e = (Map.Entry<?, ?>)o;
/* 149 */       Object key = e.getKey();
/* 150 */       if (key == null || !(key instanceof Long)) return false; 
/* 151 */       Object value = e.getValue();
/* 152 */       if (value == null || !(value instanceof Long)) return false; 
/* 153 */       return (this.key == ((Long)key).longValue() && this.value == ((Long)value).longValue());
/*     */     }
/*     */ 
/*     */     
/*     */     public int hashCode() {
/* 158 */       return HashCommon.long2int(this.key) ^ HashCommon.long2int(this.value);
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
/*     */     extends AbstractObjectSet<Long2LongMap.Entry>
/*     */   {
/*     */     protected final Long2LongMap map;
/*     */ 
/*     */     
/*     */     public BasicEntrySet(Long2LongMap map) {
/* 175 */       this.map = map;
/*     */     }
/*     */ 
/*     */     
/*     */     public boolean contains(Object o) {
/* 180 */       if (!(o instanceof Map.Entry)) return false; 
/* 181 */       if (o instanceof Long2LongMap.Entry) {
/* 182 */         Long2LongMap.Entry entry = (Long2LongMap.Entry)o;
/* 183 */         long l = entry.getLongKey();
/* 184 */         return (this.map.containsKey(l) && this.map.get(l) == entry.getLongValue());
/*     */       } 
/* 186 */       Map.Entry<?, ?> e = (Map.Entry<?, ?>)o;
/* 187 */       Object key = e.getKey();
/* 188 */       if (key == null || !(key instanceof Long)) return false; 
/* 189 */       long k = ((Long)key).longValue();
/* 190 */       Object value = e.getValue();
/* 191 */       if (value == null || !(value instanceof Long)) return false; 
/* 192 */       return (this.map.containsKey(k) && this.map.get(k) == ((Long)value).longValue());
/*     */     }
/*     */ 
/*     */     
/*     */     public boolean remove(Object o) {
/* 197 */       if (!(o instanceof Map.Entry)) return false; 
/* 198 */       if (o instanceof Long2LongMap.Entry) {
/* 199 */         Long2LongMap.Entry entry = (Long2LongMap.Entry)o;
/* 200 */         return this.map.remove(entry.getLongKey(), entry.getLongValue());
/*     */       } 
/* 202 */       Map.Entry<?, ?> e = (Map.Entry<?, ?>)o;
/* 203 */       Object key = e.getKey();
/* 204 */       if (key == null || !(key instanceof Long)) return false; 
/* 205 */       long k = ((Long)key).longValue();
/* 206 */       Object value = e.getValue();
/* 207 */       if (value == null || !(value instanceof Long)) return false; 
/* 208 */       long v = ((Long)value).longValue();
/* 209 */       return this.map.remove(k, v);
/*     */     }
/*     */ 
/*     */     
/*     */     public int size() {
/* 214 */       return this.map.size();
/*     */     }
/*     */ 
/*     */     
/*     */     public ObjectSpliterator<Long2LongMap.Entry> spliterator() {
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
/*     */   public LongSet keySet() {
/* 237 */     return new AbstractLongSet()
/*     */       {
/*     */         public boolean contains(long k) {
/* 240 */           return AbstractLong2LongMap.this.containsKey(k);
/*     */         }
/*     */ 
/*     */         
/*     */         public int size() {
/* 245 */           return AbstractLong2LongMap.this.size();
/*     */         }
/*     */ 
/*     */         
/*     */         public void clear() {
/* 250 */           AbstractLong2LongMap.this.clear();
/*     */         }
/*     */ 
/*     */         
/*     */         public LongIterator iterator() {
/* 255 */           return new LongIterator() {
/* 256 */               private final ObjectIterator<Long2LongMap.Entry> i = Long2LongMaps.fastIterator(AbstractLong2LongMap.this);
/*     */ 
/*     */               
/*     */               public long nextLong() {
/* 260 */                 return ((Long2LongMap.Entry)this.i.next()).getLongKey();
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
/*     */               public void forEachRemaining(LongConsumer action) {
/* 275 */                 this.i.forEachRemaining(entry -> action.accept(entry.getLongKey()));
/*     */               }
/*     */             };
/*     */         }
/*     */ 
/*     */         
/*     */         public LongSpliterator spliterator() {
/* 282 */           return LongSpliterators.asSpliterator(iterator(), Size64.sizeOf(AbstractLong2LongMap.this), 321);
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
/* 301 */     return new AbstractLongCollection()
/*     */       {
/*     */         public boolean contains(long k) {
/* 304 */           return AbstractLong2LongMap.this.containsValue(k);
/*     */         }
/*     */ 
/*     */         
/*     */         public int size() {
/* 309 */           return AbstractLong2LongMap.this.size();
/*     */         }
/*     */ 
/*     */         
/*     */         public void clear() {
/* 314 */           AbstractLong2LongMap.this.clear();
/*     */         }
/*     */ 
/*     */         
/*     */         public LongIterator iterator() {
/* 319 */           return new LongIterator() {
/* 320 */               private final ObjectIterator<Long2LongMap.Entry> i = Long2LongMaps.fastIterator(AbstractLong2LongMap.this);
/*     */ 
/*     */               
/*     */               public long nextLong() {
/* 324 */                 return ((Long2LongMap.Entry)this.i.next()).getLongValue();
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
/*     */               public void forEachRemaining(LongConsumer action) {
/* 339 */                 this.i.forEachRemaining(entry -> action.accept(entry.getLongValue()));
/*     */               }
/*     */             };
/*     */         }
/*     */ 
/*     */         
/*     */         public LongSpliterator spliterator() {
/* 346 */           return LongSpliterators.asSpliterator(iterator(), Size64.sizeOf(AbstractLong2LongMap.this), 320);
/*     */         }
/*     */       };
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void putAll(Map<? extends Long, ? extends Long> m) {
/* 355 */     if (m instanceof Long2LongMap) {
/* 356 */       ObjectIterator<Long2LongMap.Entry> i = Long2LongMaps.fastIterator((Long2LongMap)m);
/* 357 */       while (i.hasNext()) {
/* 358 */         Long2LongMap.Entry e = (Long2LongMap.Entry)i.next();
/* 359 */         put(e.getLongKey(), e.getLongValue());
/*     */       } 
/*     */     } else {
/* 362 */       int n = m.size();
/* 363 */       Iterator<? extends Map.Entry<? extends Long, ? extends Long>> i = m.entrySet().iterator();
/*     */       
/* 365 */       while (n-- != 0) {
/* 366 */         Map.Entry<? extends Long, ? extends Long> e = i.next();
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
/* 382 */     ObjectIterator<Long2LongMap.Entry> i = Long2LongMaps.fastIterator(this);
/* 383 */     for (; n-- != 0; h += ((Long2LongMap.Entry)i.next()).hashCode());
/* 384 */     return h;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean equals(Object o) {
/* 389 */     if (o == this) return true; 
/* 390 */     if (!(o instanceof Map)) return false; 
/* 391 */     Map<?, ?> m = (Map<?, ?>)o;
/* 392 */     if (m.size() != size()) return false; 
/* 393 */     return long2LongEntrySet().containsAll(m.entrySet());
/*     */   }
/*     */ 
/*     */   
/*     */   public String toString() {
/* 398 */     StringBuilder s = new StringBuilder();
/* 399 */     ObjectIterator<Long2LongMap.Entry> i = Long2LongMaps.fastIterator(this);
/* 400 */     int n = size();
/*     */     
/* 402 */     boolean first = true;
/* 403 */     s.append("{");
/* 404 */     while (n-- != 0) {
/* 405 */       if (first) { first = false; }
/* 406 */       else { s.append(", "); }
/* 407 */        Long2LongMap.Entry e = (Long2LongMap.Entry)i.next();
/* 408 */       s.append(String.valueOf(e.getLongKey()));
/* 409 */       s.append("=>");
/* 410 */       s.append(String.valueOf(e.getLongValue()));
/*     */     } 
/* 412 */     s.append("}");
/* 413 */     return s.toString();
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\i\\unimi\dsi\fastutil\longs\AbstractLong2LongMap.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */