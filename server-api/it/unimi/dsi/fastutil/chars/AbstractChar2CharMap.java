/*     */ package it.unimi.dsi.fastutil.chars;
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
/*     */ public abstract class AbstractChar2CharMap
/*     */   extends AbstractChar2CharFunction
/*     */   implements Char2CharMap, Serializable
/*     */ {
/*     */   private static final long serialVersionUID = -4940583368468432370L;
/*     */   
/*     */   public boolean containsKey(char k) {
/*  60 */     ObjectIterator<Char2CharMap.Entry> i = char2CharEntrySet().iterator();
/*  61 */     while (i.hasNext()) { if (((Char2CharMap.Entry)i.next()).getCharKey() == k) return true;  }
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
/*     */   public boolean containsValue(char v) {
/*  80 */     ObjectIterator<Char2CharMap.Entry> i = char2CharEntrySet().iterator();
/*  81 */     while (i.hasNext()) { if (((Char2CharMap.Entry)i.next()).getCharValue() == v) return true;  }
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
/*     */     implements Char2CharMap.Entry
/*     */   {
/*     */     protected char key;
/*     */ 
/*     */     
/*     */     protected char value;
/*     */ 
/*     */ 
/*     */     
/*     */     public BasicEntry() {}
/*     */ 
/*     */     
/*     */     public BasicEntry(Character key, Character value) {
/* 106 */       this.key = key.charValue();
/* 107 */       this.value = value.charValue();
/*     */     }
/*     */     
/*     */     public BasicEntry(char key, char value) {
/* 111 */       this.key = key;
/* 112 */       this.value = value;
/*     */     }
/*     */ 
/*     */     
/*     */     public char getCharKey() {
/* 117 */       return this.key;
/*     */     }
/*     */ 
/*     */     
/*     */     public char getCharValue() {
/* 122 */       return this.value;
/*     */     }
/*     */ 
/*     */     
/*     */     public char setValue(char value) {
/* 127 */       throw new UnsupportedOperationException();
/*     */     }
/*     */ 
/*     */     
/*     */     public boolean equals(Object o) {
/* 132 */       if (!(o instanceof Map.Entry)) return false; 
/* 133 */       if (o instanceof Char2CharMap.Entry) {
/* 134 */         Char2CharMap.Entry entry = (Char2CharMap.Entry)o;
/* 135 */         return (this.key == entry.getCharKey() && this.value == entry.getCharValue());
/*     */       } 
/* 137 */       Map.Entry<?, ?> e = (Map.Entry<?, ?>)o;
/* 138 */       Object key = e.getKey();
/* 139 */       if (key == null || !(key instanceof Character)) return false; 
/* 140 */       Object value = e.getValue();
/* 141 */       if (value == null || !(value instanceof Character)) return false; 
/* 142 */       return (this.key == ((Character)key).charValue() && this.value == ((Character)value).charValue());
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
/*     */     extends AbstractObjectSet<Char2CharMap.Entry>
/*     */   {
/*     */     protected final Char2CharMap map;
/*     */ 
/*     */     
/*     */     public BasicEntrySet(Char2CharMap map) {
/* 164 */       this.map = map;
/*     */     }
/*     */ 
/*     */     
/*     */     public boolean contains(Object o) {
/* 169 */       if (!(o instanceof Map.Entry)) return false; 
/* 170 */       if (o instanceof Char2CharMap.Entry) {
/* 171 */         Char2CharMap.Entry entry = (Char2CharMap.Entry)o;
/* 172 */         char c = entry.getCharKey();
/* 173 */         return (this.map.containsKey(c) && this.map.get(c) == entry.getCharValue());
/*     */       } 
/* 175 */       Map.Entry<?, ?> e = (Map.Entry<?, ?>)o;
/* 176 */       Object key = e.getKey();
/* 177 */       if (key == null || !(key instanceof Character)) return false; 
/* 178 */       char k = ((Character)key).charValue();
/* 179 */       Object value = e.getValue();
/* 180 */       if (value == null || !(value instanceof Character)) return false; 
/* 181 */       return (this.map.containsKey(k) && this.map.get(k) == ((Character)value).charValue());
/*     */     }
/*     */ 
/*     */     
/*     */     public boolean remove(Object o) {
/* 186 */       if (!(o instanceof Map.Entry)) return false; 
/* 187 */       if (o instanceof Char2CharMap.Entry) {
/* 188 */         Char2CharMap.Entry entry = (Char2CharMap.Entry)o;
/* 189 */         return this.map.remove(entry.getCharKey(), entry.getCharValue());
/*     */       } 
/* 191 */       Map.Entry<?, ?> e = (Map.Entry<?, ?>)o;
/* 192 */       Object key = e.getKey();
/* 193 */       if (key == null || !(key instanceof Character)) return false; 
/* 194 */       char k = ((Character)key).charValue();
/* 195 */       Object value = e.getValue();
/* 196 */       if (value == null || !(value instanceof Character)) return false; 
/* 197 */       char v = ((Character)value).charValue();
/* 198 */       return this.map.remove(k, v);
/*     */     }
/*     */ 
/*     */     
/*     */     public int size() {
/* 203 */       return this.map.size();
/*     */     }
/*     */ 
/*     */     
/*     */     public ObjectSpliterator<Char2CharMap.Entry> spliterator() {
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
/*     */   public CharSet keySet() {
/* 226 */     return new AbstractCharSet()
/*     */       {
/*     */         public boolean contains(char k) {
/* 229 */           return AbstractChar2CharMap.this.containsKey(k);
/*     */         }
/*     */ 
/*     */         
/*     */         public int size() {
/* 234 */           return AbstractChar2CharMap.this.size();
/*     */         }
/*     */ 
/*     */         
/*     */         public void clear() {
/* 239 */           AbstractChar2CharMap.this.clear();
/*     */         }
/*     */ 
/*     */         
/*     */         public CharIterator iterator() {
/* 244 */           return new CharIterator() {
/* 245 */               private final ObjectIterator<Char2CharMap.Entry> i = Char2CharMaps.fastIterator(AbstractChar2CharMap.this);
/*     */ 
/*     */               
/*     */               public char nextChar() {
/* 249 */                 return ((Char2CharMap.Entry)this.i.next()).getCharKey();
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
/*     */               public void forEachRemaining(CharConsumer action) {
/* 264 */                 this.i.forEachRemaining(entry -> action.accept(entry.getCharKey()));
/*     */               }
/*     */             };
/*     */         }
/*     */ 
/*     */         
/*     */         public CharSpliterator spliterator() {
/* 271 */           return CharSpliterators.asSpliterator(iterator(), Size64.sizeOf(AbstractChar2CharMap.this), 321);
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
/*     */   public CharCollection values() {
/* 290 */     return new AbstractCharCollection()
/*     */       {
/*     */         public boolean contains(char k) {
/* 293 */           return AbstractChar2CharMap.this.containsValue(k);
/*     */         }
/*     */ 
/*     */         
/*     */         public int size() {
/* 298 */           return AbstractChar2CharMap.this.size();
/*     */         }
/*     */ 
/*     */         
/*     */         public void clear() {
/* 303 */           AbstractChar2CharMap.this.clear();
/*     */         }
/*     */ 
/*     */         
/*     */         public CharIterator iterator() {
/* 308 */           return new CharIterator() {
/* 309 */               private final ObjectIterator<Char2CharMap.Entry> i = Char2CharMaps.fastIterator(AbstractChar2CharMap.this);
/*     */ 
/*     */               
/*     */               public char nextChar() {
/* 313 */                 return ((Char2CharMap.Entry)this.i.next()).getCharValue();
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
/*     */               public void forEachRemaining(CharConsumer action) {
/* 328 */                 this.i.forEachRemaining(entry -> action.accept(entry.getCharValue()));
/*     */               }
/*     */             };
/*     */         }
/*     */ 
/*     */         
/*     */         public CharSpliterator spliterator() {
/* 335 */           return CharSpliterators.asSpliterator(iterator(), Size64.sizeOf(AbstractChar2CharMap.this), 320);
/*     */         }
/*     */       };
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void putAll(Map<? extends Character, ? extends Character> m) {
/* 344 */     if (m instanceof Char2CharMap) {
/* 345 */       ObjectIterator<Char2CharMap.Entry> i = Char2CharMaps.fastIterator((Char2CharMap)m);
/* 346 */       while (i.hasNext()) {
/* 347 */         Char2CharMap.Entry e = (Char2CharMap.Entry)i.next();
/* 348 */         put(e.getCharKey(), e.getCharValue());
/*     */       } 
/*     */     } else {
/* 351 */       int n = m.size();
/* 352 */       Iterator<? extends Map.Entry<? extends Character, ? extends Character>> i = m.entrySet().iterator();
/*     */       
/* 354 */       while (n-- != 0) {
/* 355 */         Map.Entry<? extends Character, ? extends Character> e = i.next();
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
/* 371 */     ObjectIterator<Char2CharMap.Entry> i = Char2CharMaps.fastIterator(this);
/* 372 */     for (; n-- != 0; h += ((Char2CharMap.Entry)i.next()).hashCode());
/* 373 */     return h;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean equals(Object o) {
/* 378 */     if (o == this) return true; 
/* 379 */     if (!(o instanceof Map)) return false; 
/* 380 */     Map<?, ?> m = (Map<?, ?>)o;
/* 381 */     if (m.size() != size()) return false; 
/* 382 */     return char2CharEntrySet().containsAll(m.entrySet());
/*     */   }
/*     */ 
/*     */   
/*     */   public String toString() {
/* 387 */     StringBuilder s = new StringBuilder();
/* 388 */     ObjectIterator<Char2CharMap.Entry> i = Char2CharMaps.fastIterator(this);
/* 389 */     int n = size();
/*     */     
/* 391 */     boolean first = true;
/* 392 */     s.append("{");
/* 393 */     while (n-- != 0) {
/* 394 */       if (first) { first = false; }
/* 395 */       else { s.append(", "); }
/* 396 */        Char2CharMap.Entry e = (Char2CharMap.Entry)i.next();
/* 397 */       s.append(String.valueOf(e.getCharKey()));
/* 398 */       s.append("=>");
/* 399 */       s.append(String.valueOf(e.getCharValue()));
/*     */     } 
/* 401 */     s.append("}");
/* 402 */     return s.toString();
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\i\\unimi\dsi\fastutil\chars\AbstractChar2CharMap.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */