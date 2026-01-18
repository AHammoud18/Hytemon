/*     */ package it.unimi.dsi.fastutil.longs;
/*     */ 
/*     */ import it.unimi.dsi.fastutil.HashCommon;
/*     */ import it.unimi.dsi.fastutil.Size64;
/*     */ import it.unimi.dsi.fastutil.objects.AbstractObjectSet;
/*     */ import it.unimi.dsi.fastutil.objects.ObjectIterator;
/*     */ import it.unimi.dsi.fastutil.objects.ObjectSpliterator;
/*     */ import it.unimi.dsi.fastutil.objects.ObjectSpliterators;
/*     */ import it.unimi.dsi.fastutil.shorts.AbstractShortCollection;
/*     */ import it.unimi.dsi.fastutil.shorts.ShortCollection;
/*     */ import it.unimi.dsi.fastutil.shorts.ShortConsumer;
/*     */ import it.unimi.dsi.fastutil.shorts.ShortIterator;
/*     */ import it.unimi.dsi.fastutil.shorts.ShortSpliterator;
/*     */ import it.unimi.dsi.fastutil.shorts.ShortSpliterators;
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
/*     */ public abstract class AbstractLong2ShortMap
/*     */   extends AbstractLong2ShortFunction
/*     */   implements Long2ShortMap, Serializable
/*     */ {
/*     */   private static final long serialVersionUID = -4940583368468432370L;
/*     */   
/*     */   public boolean containsKey(long k) {
/*  66 */     ObjectIterator<Long2ShortMap.Entry> i = long2ShortEntrySet().iterator();
/*  67 */     while (i.hasNext()) { if (((Long2ShortMap.Entry)i.next()).getLongKey() == k) return true;  }
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
/*     */   public boolean containsValue(short v) {
/*  86 */     ObjectIterator<Long2ShortMap.Entry> i = long2ShortEntrySet().iterator();
/*  87 */     while (i.hasNext()) { if (((Long2ShortMap.Entry)i.next()).getShortValue() == v) return true;  }
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
/*     */     implements Long2ShortMap.Entry
/*     */   {
/*     */     protected long key;
/*     */ 
/*     */     
/*     */     protected short value;
/*     */ 
/*     */ 
/*     */     
/*     */     public BasicEntry() {}
/*     */ 
/*     */     
/*     */     public BasicEntry(Long key, Short value) {
/* 112 */       this.key = key.longValue();
/* 113 */       this.value = value.shortValue();
/*     */     }
/*     */     
/*     */     public BasicEntry(long key, short value) {
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
/*     */     public short getShortValue() {
/* 128 */       return this.value;
/*     */     }
/*     */ 
/*     */     
/*     */     public short setValue(short value) {
/* 133 */       throw new UnsupportedOperationException();
/*     */     }
/*     */ 
/*     */     
/*     */     public boolean equals(Object o) {
/* 138 */       if (!(o instanceof Map.Entry)) return false; 
/* 139 */       if (o instanceof Long2ShortMap.Entry) {
/* 140 */         Long2ShortMap.Entry entry = (Long2ShortMap.Entry)o;
/* 141 */         return (this.key == entry.getLongKey() && this.value == entry.getShortValue());
/*     */       } 
/* 143 */       Map.Entry<?, ?> e = (Map.Entry<?, ?>)o;
/* 144 */       Object key = e.getKey();
/* 145 */       if (key == null || !(key instanceof Long)) return false; 
/* 146 */       Object value = e.getValue();
/* 147 */       if (value == null || !(value instanceof Short)) return false; 
/* 148 */       return (this.key == ((Long)key).longValue() && this.value == ((Short)value).shortValue());
/*     */     }
/*     */ 
/*     */     
/*     */     public int hashCode() {
/* 153 */       return HashCommon.long2int(this.key) ^ this.value;
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
/*     */     extends AbstractObjectSet<Long2ShortMap.Entry>
/*     */   {
/*     */     protected final Long2ShortMap map;
/*     */ 
/*     */     
/*     */     public BasicEntrySet(Long2ShortMap map) {
/* 170 */       this.map = map;
/*     */     }
/*     */ 
/*     */     
/*     */     public boolean contains(Object o) {
/* 175 */       if (!(o instanceof Map.Entry)) return false; 
/* 176 */       if (o instanceof Long2ShortMap.Entry) {
/* 177 */         Long2ShortMap.Entry entry = (Long2ShortMap.Entry)o;
/* 178 */         long l = entry.getLongKey();
/* 179 */         return (this.map.containsKey(l) && this.map.get(l) == entry.getShortValue());
/*     */       } 
/* 181 */       Map.Entry<?, ?> e = (Map.Entry<?, ?>)o;
/* 182 */       Object key = e.getKey();
/* 183 */       if (key == null || !(key instanceof Long)) return false; 
/* 184 */       long k = ((Long)key).longValue();
/* 185 */       Object value = e.getValue();
/* 186 */       if (value == null || !(value instanceof Short)) return false; 
/* 187 */       return (this.map.containsKey(k) && this.map.get(k) == ((Short)value).shortValue());
/*     */     }
/*     */ 
/*     */     
/*     */     public boolean remove(Object o) {
/* 192 */       if (!(o instanceof Map.Entry)) return false; 
/* 193 */       if (o instanceof Long2ShortMap.Entry) {
/* 194 */         Long2ShortMap.Entry entry = (Long2ShortMap.Entry)o;
/* 195 */         return this.map.remove(entry.getLongKey(), entry.getShortValue());
/*     */       } 
/* 197 */       Map.Entry<?, ?> e = (Map.Entry<?, ?>)o;
/* 198 */       Object key = e.getKey();
/* 199 */       if (key == null || !(key instanceof Long)) return false; 
/* 200 */       long k = ((Long)key).longValue();
/* 201 */       Object value = e.getValue();
/* 202 */       if (value == null || !(value instanceof Short)) return false; 
/* 203 */       short v = ((Short)value).shortValue();
/* 204 */       return this.map.remove(k, v);
/*     */     }
/*     */ 
/*     */     
/*     */     public int size() {
/* 209 */       return this.map.size();
/*     */     }
/*     */ 
/*     */     
/*     */     public ObjectSpliterator<Long2ShortMap.Entry> spliterator() {
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
/* 235 */           return AbstractLong2ShortMap.this.containsKey(k);
/*     */         }
/*     */ 
/*     */         
/*     */         public int size() {
/* 240 */           return AbstractLong2ShortMap.this.size();
/*     */         }
/*     */ 
/*     */         
/*     */         public void clear() {
/* 245 */           AbstractLong2ShortMap.this.clear();
/*     */         }
/*     */ 
/*     */         
/*     */         public LongIterator iterator() {
/* 250 */           return new LongIterator() {
/* 251 */               private final ObjectIterator<Long2ShortMap.Entry> i = Long2ShortMaps.fastIterator(AbstractLong2ShortMap.this);
/*     */ 
/*     */               
/*     */               public long nextLong() {
/* 255 */                 return ((Long2ShortMap.Entry)this.i.next()).getLongKey();
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
/* 277 */           return LongSpliterators.asSpliterator(iterator(), Size64.sizeOf(AbstractLong2ShortMap.this), 321);
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
/* 296 */     return (ShortCollection)new AbstractShortCollection()
/*     */       {
/*     */         public boolean contains(short k) {
/* 299 */           return AbstractLong2ShortMap.this.containsValue(k);
/*     */         }
/*     */ 
/*     */         
/*     */         public int size() {
/* 304 */           return AbstractLong2ShortMap.this.size();
/*     */         }
/*     */ 
/*     */         
/*     */         public void clear() {
/* 309 */           AbstractLong2ShortMap.this.clear();
/*     */         }
/*     */ 
/*     */         
/*     */         public ShortIterator iterator() {
/* 314 */           return new ShortIterator() {
/* 315 */               private final ObjectIterator<Long2ShortMap.Entry> i = Long2ShortMaps.fastIterator(AbstractLong2ShortMap.this);
/*     */ 
/*     */               
/*     */               public short nextShort() {
/* 319 */                 return ((Long2ShortMap.Entry)this.i.next()).getShortValue();
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
/*     */               public void forEachRemaining(ShortConsumer action) {
/* 334 */                 this.i.forEachRemaining(entry -> action.accept(entry.getShortValue()));
/*     */               }
/*     */             };
/*     */         }
/*     */ 
/*     */         
/*     */         public ShortSpliterator spliterator() {
/* 341 */           return ShortSpliterators.asSpliterator(iterator(), Size64.sizeOf(AbstractLong2ShortMap.this), 320);
/*     */         }
/*     */       };
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void putAll(Map<? extends Long, ? extends Short> m) {
/* 350 */     if (m instanceof Long2ShortMap) {
/* 351 */       ObjectIterator<Long2ShortMap.Entry> i = Long2ShortMaps.fastIterator((Long2ShortMap)m);
/* 352 */       while (i.hasNext()) {
/* 353 */         Long2ShortMap.Entry e = (Long2ShortMap.Entry)i.next();
/* 354 */         put(e.getLongKey(), e.getShortValue());
/*     */       } 
/*     */     } else {
/* 357 */       int n = m.size();
/* 358 */       Iterator<? extends Map.Entry<? extends Long, ? extends Short>> i = m.entrySet().iterator();
/*     */       
/* 360 */       while (n-- != 0) {
/* 361 */         Map.Entry<? extends Long, ? extends Short> e = i.next();
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
/* 377 */     ObjectIterator<Long2ShortMap.Entry> i = Long2ShortMaps.fastIterator(this);
/* 378 */     for (; n-- != 0; h += ((Long2ShortMap.Entry)i.next()).hashCode());
/* 379 */     return h;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean equals(Object o) {
/* 384 */     if (o == this) return true; 
/* 385 */     if (!(o instanceof Map)) return false; 
/* 386 */     Map<?, ?> m = (Map<?, ?>)o;
/* 387 */     if (m.size() != size()) return false; 
/* 388 */     return long2ShortEntrySet().containsAll(m.entrySet());
/*     */   }
/*     */ 
/*     */   
/*     */   public String toString() {
/* 393 */     StringBuilder s = new StringBuilder();
/* 394 */     ObjectIterator<Long2ShortMap.Entry> i = Long2ShortMaps.fastIterator(this);
/* 395 */     int n = size();
/*     */     
/* 397 */     boolean first = true;
/* 398 */     s.append("{");
/* 399 */     while (n-- != 0) {
/* 400 */       if (first) { first = false; }
/* 401 */       else { s.append(", "); }
/* 402 */        Long2ShortMap.Entry e = (Long2ShortMap.Entry)i.next();
/* 403 */       s.append(String.valueOf(e.getLongKey()));
/* 404 */       s.append("=>");
/* 405 */       s.append(String.valueOf(e.getShortValue()));
/*     */     } 
/* 407 */     s.append("}");
/* 408 */     return s.toString();
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\i\\unimi\dsi\fastutil\longs\AbstractLong2ShortMap.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */