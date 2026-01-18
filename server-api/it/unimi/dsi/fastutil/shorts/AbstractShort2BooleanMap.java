/*     */ package it.unimi.dsi.fastutil.shorts;
/*     */ 
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public abstract class AbstractShort2BooleanMap
/*     */   extends AbstractShort2BooleanFunction
/*     */   implements Short2BooleanMap, Serializable
/*     */ {
/*     */   private static final long serialVersionUID = -4940583368468432370L;
/*     */   
/*     */   public boolean containsKey(short k) {
/*  66 */     ObjectIterator<Short2BooleanMap.Entry> i = short2BooleanEntrySet().iterator();
/*  67 */     while (i.hasNext()) { if (((Short2BooleanMap.Entry)i.next()).getShortKey() == k) return true;  }
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
/*  86 */     ObjectIterator<Short2BooleanMap.Entry> i = short2BooleanEntrySet().iterator();
/*  87 */     while (i.hasNext()) { if (((Short2BooleanMap.Entry)i.next()).getBooleanValue() == v) return true;  }
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
/*     */     implements Short2BooleanMap.Entry
/*     */   {
/*     */     protected short key;
/*     */ 
/*     */     
/*     */     protected boolean value;
/*     */ 
/*     */ 
/*     */     
/*     */     public BasicEntry() {}
/*     */ 
/*     */     
/*     */     public BasicEntry(Short key, Boolean value) {
/* 112 */       this.key = key.shortValue();
/* 113 */       this.value = value.booleanValue();
/*     */     }
/*     */     
/*     */     public BasicEntry(short key, boolean value) {
/* 117 */       this.key = key;
/* 118 */       this.value = value;
/*     */     }
/*     */ 
/*     */     
/*     */     public short getShortKey() {
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
/* 139 */       if (o instanceof Short2BooleanMap.Entry) {
/* 140 */         Short2BooleanMap.Entry entry = (Short2BooleanMap.Entry)o;
/* 141 */         return (this.key == entry.getShortKey() && this.value == entry.getBooleanValue());
/*     */       } 
/* 143 */       Map.Entry<?, ?> e = (Map.Entry<?, ?>)o;
/* 144 */       Object key = e.getKey();
/* 145 */       if (key == null || !(key instanceof Short)) return false; 
/* 146 */       Object value = e.getValue();
/* 147 */       if (value == null || !(value instanceof Boolean)) return false; 
/* 148 */       return (this.key == ((Short)key).shortValue() && this.value == ((Boolean)value).booleanValue());
/*     */     }
/*     */ 
/*     */     
/*     */     public int hashCode() {
/* 153 */       return this.key ^ (this.value ? 1231 : 1237);
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
/*     */     extends AbstractObjectSet<Short2BooleanMap.Entry>
/*     */   {
/*     */     protected final Short2BooleanMap map;
/*     */ 
/*     */     
/*     */     public BasicEntrySet(Short2BooleanMap map) {
/* 170 */       this.map = map;
/*     */     }
/*     */ 
/*     */     
/*     */     public boolean contains(Object o) {
/* 175 */       if (!(o instanceof Map.Entry)) return false; 
/* 176 */       if (o instanceof Short2BooleanMap.Entry) {
/* 177 */         Short2BooleanMap.Entry entry = (Short2BooleanMap.Entry)o;
/* 178 */         short s = entry.getShortKey();
/* 179 */         return (this.map.containsKey(s) && this.map.get(s) == entry.getBooleanValue());
/*     */       } 
/* 181 */       Map.Entry<?, ?> e = (Map.Entry<?, ?>)o;
/* 182 */       Object key = e.getKey();
/* 183 */       if (key == null || !(key instanceof Short)) return false; 
/* 184 */       short k = ((Short)key).shortValue();
/* 185 */       Object value = e.getValue();
/* 186 */       if (value == null || !(value instanceof Boolean)) return false; 
/* 187 */       return (this.map.containsKey(k) && this.map.get(k) == ((Boolean)value).booleanValue());
/*     */     }
/*     */ 
/*     */     
/*     */     public boolean remove(Object o) {
/* 192 */       if (!(o instanceof Map.Entry)) return false; 
/* 193 */       if (o instanceof Short2BooleanMap.Entry) {
/* 194 */         Short2BooleanMap.Entry entry = (Short2BooleanMap.Entry)o;
/* 195 */         return this.map.remove(entry.getShortKey(), entry.getBooleanValue());
/*     */       } 
/* 197 */       Map.Entry<?, ?> e = (Map.Entry<?, ?>)o;
/* 198 */       Object key = e.getKey();
/* 199 */       if (key == null || !(key instanceof Short)) return false; 
/* 200 */       short k = ((Short)key).shortValue();
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
/*     */     public ObjectSpliterator<Short2BooleanMap.Entry> spliterator() {
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
/*     */   public ShortSet keySet() {
/* 232 */     return new AbstractShortSet()
/*     */       {
/*     */         public boolean contains(short k) {
/* 235 */           return AbstractShort2BooleanMap.this.containsKey(k);
/*     */         }
/*     */ 
/*     */         
/*     */         public int size() {
/* 240 */           return AbstractShort2BooleanMap.this.size();
/*     */         }
/*     */ 
/*     */         
/*     */         public void clear() {
/* 245 */           AbstractShort2BooleanMap.this.clear();
/*     */         }
/*     */ 
/*     */         
/*     */         public ShortIterator iterator() {
/* 250 */           return new ShortIterator() {
/* 251 */               private final ObjectIterator<Short2BooleanMap.Entry> i = Short2BooleanMaps.fastIterator(AbstractShort2BooleanMap.this);
/*     */ 
/*     */               
/*     */               public short nextShort() {
/* 255 */                 return ((Short2BooleanMap.Entry)this.i.next()).getShortKey();
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
/*     */               public void forEachRemaining(ShortConsumer action) {
/* 270 */                 this.i.forEachRemaining(entry -> action.accept(entry.getShortKey()));
/*     */               }
/*     */             };
/*     */         }
/*     */ 
/*     */         
/*     */         public ShortSpliterator spliterator() {
/* 277 */           return ShortSpliterators.asSpliterator(iterator(), Size64.sizeOf(AbstractShort2BooleanMap.this), 321);
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
/* 299 */           return AbstractShort2BooleanMap.this.containsValue(k);
/*     */         }
/*     */ 
/*     */         
/*     */         public int size() {
/* 304 */           return AbstractShort2BooleanMap.this.size();
/*     */         }
/*     */ 
/*     */         
/*     */         public void clear() {
/* 309 */           AbstractShort2BooleanMap.this.clear();
/*     */         }
/*     */ 
/*     */         
/*     */         public BooleanIterator iterator() {
/* 314 */           return new BooleanIterator() {
/* 315 */               private final ObjectIterator<Short2BooleanMap.Entry> i = Short2BooleanMaps.fastIterator(AbstractShort2BooleanMap.this);
/*     */ 
/*     */               
/*     */               public boolean nextBoolean() {
/* 319 */                 return ((Short2BooleanMap.Entry)this.i.next()).getBooleanValue();
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
/* 341 */           return BooleanSpliterators.asSpliterator(iterator(), Size64.sizeOf(AbstractShort2BooleanMap.this), 320);
/*     */         }
/*     */       };
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void putAll(Map<? extends Short, ? extends Boolean> m) {
/* 350 */     if (m instanceof Short2BooleanMap) {
/* 351 */       ObjectIterator<Short2BooleanMap.Entry> i = Short2BooleanMaps.fastIterator((Short2BooleanMap)m);
/* 352 */       while (i.hasNext()) {
/* 353 */         Short2BooleanMap.Entry e = (Short2BooleanMap.Entry)i.next();
/* 354 */         put(e.getShortKey(), e.getBooleanValue());
/*     */       } 
/*     */     } else {
/* 357 */       int n = m.size();
/* 358 */       Iterator<? extends Map.Entry<? extends Short, ? extends Boolean>> i = m.entrySet().iterator();
/*     */       
/* 360 */       while (n-- != 0) {
/* 361 */         Map.Entry<? extends Short, ? extends Boolean> e = i.next();
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
/* 377 */     ObjectIterator<Short2BooleanMap.Entry> i = Short2BooleanMaps.fastIterator(this);
/* 378 */     for (; n-- != 0; h += ((Short2BooleanMap.Entry)i.next()).hashCode());
/* 379 */     return h;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean equals(Object o) {
/* 384 */     if (o == this) return true; 
/* 385 */     if (!(o instanceof Map)) return false; 
/* 386 */     Map<?, ?> m = (Map<?, ?>)o;
/* 387 */     if (m.size() != size()) return false; 
/* 388 */     return short2BooleanEntrySet().containsAll(m.entrySet());
/*     */   }
/*     */ 
/*     */   
/*     */   public String toString() {
/* 393 */     StringBuilder s = new StringBuilder();
/* 394 */     ObjectIterator<Short2BooleanMap.Entry> i = Short2BooleanMaps.fastIterator(this);
/* 395 */     int n = size();
/*     */     
/* 397 */     boolean first = true;
/* 398 */     s.append("{");
/* 399 */     while (n-- != 0) {
/* 400 */       if (first) { first = false; }
/* 401 */       else { s.append(", "); }
/* 402 */        Short2BooleanMap.Entry e = (Short2BooleanMap.Entry)i.next();
/* 403 */       s.append(String.valueOf(e.getShortKey()));
/* 404 */       s.append("=>");
/* 405 */       s.append(String.valueOf(e.getBooleanValue()));
/*     */     } 
/* 407 */     s.append("}");
/* 408 */     return s.toString();
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\i\\unimi\dsi\fastutil\shorts\AbstractShort2BooleanMap.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */