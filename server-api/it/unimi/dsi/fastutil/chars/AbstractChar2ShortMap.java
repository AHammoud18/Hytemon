/*     */ package it.unimi.dsi.fastutil.chars;
/*     */ 
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public abstract class AbstractChar2ShortMap
/*     */   extends AbstractChar2ShortFunction
/*     */   implements Char2ShortMap, Serializable
/*     */ {
/*     */   private static final long serialVersionUID = -4940583368468432370L;
/*     */   
/*     */   public boolean containsKey(char k) {
/*  66 */     ObjectIterator<Char2ShortMap.Entry> i = char2ShortEntrySet().iterator();
/*  67 */     while (i.hasNext()) { if (((Char2ShortMap.Entry)i.next()).getCharKey() == k) return true;  }
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
/*  86 */     ObjectIterator<Char2ShortMap.Entry> i = char2ShortEntrySet().iterator();
/*  87 */     while (i.hasNext()) { if (((Char2ShortMap.Entry)i.next()).getShortValue() == v) return true;  }
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
/*     */     implements Char2ShortMap.Entry
/*     */   {
/*     */     protected char key;
/*     */ 
/*     */     
/*     */     protected short value;
/*     */ 
/*     */ 
/*     */     
/*     */     public BasicEntry() {}
/*     */ 
/*     */     
/*     */     public BasicEntry(Character key, Short value) {
/* 112 */       this.key = key.charValue();
/* 113 */       this.value = value.shortValue();
/*     */     }
/*     */     
/*     */     public BasicEntry(char key, short value) {
/* 117 */       this.key = key;
/* 118 */       this.value = value;
/*     */     }
/*     */ 
/*     */     
/*     */     public char getCharKey() {
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
/* 139 */       if (o instanceof Char2ShortMap.Entry) {
/* 140 */         Char2ShortMap.Entry entry = (Char2ShortMap.Entry)o;
/* 141 */         return (this.key == entry.getCharKey() && this.value == entry.getShortValue());
/*     */       } 
/* 143 */       Map.Entry<?, ?> e = (Map.Entry<?, ?>)o;
/* 144 */       Object key = e.getKey();
/* 145 */       if (key == null || !(key instanceof Character)) return false; 
/* 146 */       Object value = e.getValue();
/* 147 */       if (value == null || !(value instanceof Short)) return false; 
/* 148 */       return (this.key == ((Character)key).charValue() && this.value == ((Short)value).shortValue());
/*     */     }
/*     */ 
/*     */     
/*     */     public int hashCode() {
/* 153 */       return this.key ^ this.value;
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
/*     */     extends AbstractObjectSet<Char2ShortMap.Entry>
/*     */   {
/*     */     protected final Char2ShortMap map;
/*     */ 
/*     */     
/*     */     public BasicEntrySet(Char2ShortMap map) {
/* 170 */       this.map = map;
/*     */     }
/*     */ 
/*     */     
/*     */     public boolean contains(Object o) {
/* 175 */       if (!(o instanceof Map.Entry)) return false; 
/* 176 */       if (o instanceof Char2ShortMap.Entry) {
/* 177 */         Char2ShortMap.Entry entry = (Char2ShortMap.Entry)o;
/* 178 */         char c = entry.getCharKey();
/* 179 */         return (this.map.containsKey(c) && this.map.get(c) == entry.getShortValue());
/*     */       } 
/* 181 */       Map.Entry<?, ?> e = (Map.Entry<?, ?>)o;
/* 182 */       Object key = e.getKey();
/* 183 */       if (key == null || !(key instanceof Character)) return false; 
/* 184 */       char k = ((Character)key).charValue();
/* 185 */       Object value = e.getValue();
/* 186 */       if (value == null || !(value instanceof Short)) return false; 
/* 187 */       return (this.map.containsKey(k) && this.map.get(k) == ((Short)value).shortValue());
/*     */     }
/*     */ 
/*     */     
/*     */     public boolean remove(Object o) {
/* 192 */       if (!(o instanceof Map.Entry)) return false; 
/* 193 */       if (o instanceof Char2ShortMap.Entry) {
/* 194 */         Char2ShortMap.Entry entry = (Char2ShortMap.Entry)o;
/* 195 */         return this.map.remove(entry.getCharKey(), entry.getShortValue());
/*     */       } 
/* 197 */       Map.Entry<?, ?> e = (Map.Entry<?, ?>)o;
/* 198 */       Object key = e.getKey();
/* 199 */       if (key == null || !(key instanceof Character)) return false; 
/* 200 */       char k = ((Character)key).charValue();
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
/*     */     public ObjectSpliterator<Char2ShortMap.Entry> spliterator() {
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
/*     */   public CharSet keySet() {
/* 232 */     return new AbstractCharSet()
/*     */       {
/*     */         public boolean contains(char k) {
/* 235 */           return AbstractChar2ShortMap.this.containsKey(k);
/*     */         }
/*     */ 
/*     */         
/*     */         public int size() {
/* 240 */           return AbstractChar2ShortMap.this.size();
/*     */         }
/*     */ 
/*     */         
/*     */         public void clear() {
/* 245 */           AbstractChar2ShortMap.this.clear();
/*     */         }
/*     */ 
/*     */         
/*     */         public CharIterator iterator() {
/* 250 */           return new CharIterator() {
/* 251 */               private final ObjectIterator<Char2ShortMap.Entry> i = Char2ShortMaps.fastIterator(AbstractChar2ShortMap.this);
/*     */ 
/*     */               
/*     */               public char nextChar() {
/* 255 */                 return ((Char2ShortMap.Entry)this.i.next()).getCharKey();
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
/*     */               public void forEachRemaining(CharConsumer action) {
/* 270 */                 this.i.forEachRemaining(entry -> action.accept(entry.getCharKey()));
/*     */               }
/*     */             };
/*     */         }
/*     */ 
/*     */         
/*     */         public CharSpliterator spliterator() {
/* 277 */           return CharSpliterators.asSpliterator(iterator(), Size64.sizeOf(AbstractChar2ShortMap.this), 321);
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
/* 299 */           return AbstractChar2ShortMap.this.containsValue(k);
/*     */         }
/*     */ 
/*     */         
/*     */         public int size() {
/* 304 */           return AbstractChar2ShortMap.this.size();
/*     */         }
/*     */ 
/*     */         
/*     */         public void clear() {
/* 309 */           AbstractChar2ShortMap.this.clear();
/*     */         }
/*     */ 
/*     */         
/*     */         public ShortIterator iterator() {
/* 314 */           return new ShortIterator() {
/* 315 */               private final ObjectIterator<Char2ShortMap.Entry> i = Char2ShortMaps.fastIterator(AbstractChar2ShortMap.this);
/*     */ 
/*     */               
/*     */               public short nextShort() {
/* 319 */                 return ((Char2ShortMap.Entry)this.i.next()).getShortValue();
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
/* 341 */           return ShortSpliterators.asSpliterator(iterator(), Size64.sizeOf(AbstractChar2ShortMap.this), 320);
/*     */         }
/*     */       };
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void putAll(Map<? extends Character, ? extends Short> m) {
/* 350 */     if (m instanceof Char2ShortMap) {
/* 351 */       ObjectIterator<Char2ShortMap.Entry> i = Char2ShortMaps.fastIterator((Char2ShortMap)m);
/* 352 */       while (i.hasNext()) {
/* 353 */         Char2ShortMap.Entry e = (Char2ShortMap.Entry)i.next();
/* 354 */         put(e.getCharKey(), e.getShortValue());
/*     */       } 
/*     */     } else {
/* 357 */       int n = m.size();
/* 358 */       Iterator<? extends Map.Entry<? extends Character, ? extends Short>> i = m.entrySet().iterator();
/*     */       
/* 360 */       while (n-- != 0) {
/* 361 */         Map.Entry<? extends Character, ? extends Short> e = i.next();
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
/* 377 */     ObjectIterator<Char2ShortMap.Entry> i = Char2ShortMaps.fastIterator(this);
/* 378 */     for (; n-- != 0; h += ((Char2ShortMap.Entry)i.next()).hashCode());
/* 379 */     return h;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean equals(Object o) {
/* 384 */     if (o == this) return true; 
/* 385 */     if (!(o instanceof Map)) return false; 
/* 386 */     Map<?, ?> m = (Map<?, ?>)o;
/* 387 */     if (m.size() != size()) return false; 
/* 388 */     return char2ShortEntrySet().containsAll(m.entrySet());
/*     */   }
/*     */ 
/*     */   
/*     */   public String toString() {
/* 393 */     StringBuilder s = new StringBuilder();
/* 394 */     ObjectIterator<Char2ShortMap.Entry> i = Char2ShortMaps.fastIterator(this);
/* 395 */     int n = size();
/*     */     
/* 397 */     boolean first = true;
/* 398 */     s.append("{");
/* 399 */     while (n-- != 0) {
/* 400 */       if (first) { first = false; }
/* 401 */       else { s.append(", "); }
/* 402 */        Char2ShortMap.Entry e = (Char2ShortMap.Entry)i.next();
/* 403 */       s.append(String.valueOf(e.getCharKey()));
/* 404 */       s.append("=>");
/* 405 */       s.append(String.valueOf(e.getShortValue()));
/*     */     } 
/* 407 */     s.append("}");
/* 408 */     return s.toString();
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\i\\unimi\dsi\fastutil\chars\AbstractChar2ShortMap.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */