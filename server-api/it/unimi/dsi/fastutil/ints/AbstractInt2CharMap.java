/*     */ package it.unimi.dsi.fastutil.ints;
/*     */ 
/*     */ import it.unimi.dsi.fastutil.Size64;
/*     */ import it.unimi.dsi.fastutil.chars.AbstractCharCollection;
/*     */ import it.unimi.dsi.fastutil.chars.CharCollection;
/*     */ import it.unimi.dsi.fastutil.chars.CharConsumer;
/*     */ import it.unimi.dsi.fastutil.chars.CharIterator;
/*     */ import it.unimi.dsi.fastutil.chars.CharSpliterator;
/*     */ import it.unimi.dsi.fastutil.chars.CharSpliterators;
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
/*     */ public abstract class AbstractInt2CharMap
/*     */   extends AbstractInt2CharFunction
/*     */   implements Int2CharMap, Serializable
/*     */ {
/*     */   private static final long serialVersionUID = -4940583368468432370L;
/*     */   
/*     */   public boolean containsKey(int k) {
/*  66 */     ObjectIterator<Int2CharMap.Entry> i = int2CharEntrySet().iterator();
/*  67 */     while (i.hasNext()) { if (((Int2CharMap.Entry)i.next()).getIntKey() == k) return true;  }
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
/*     */   public boolean containsValue(char v) {
/*  86 */     ObjectIterator<Int2CharMap.Entry> i = int2CharEntrySet().iterator();
/*  87 */     while (i.hasNext()) { if (((Int2CharMap.Entry)i.next()).getCharValue() == v) return true;  }
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
/*     */     implements Int2CharMap.Entry
/*     */   {
/*     */     protected int key;
/*     */ 
/*     */     
/*     */     protected char value;
/*     */ 
/*     */ 
/*     */     
/*     */     public BasicEntry() {}
/*     */ 
/*     */     
/*     */     public BasicEntry(Integer key, Character value) {
/* 112 */       this.key = key.intValue();
/* 113 */       this.value = value.charValue();
/*     */     }
/*     */     
/*     */     public BasicEntry(int key, char value) {
/* 117 */       this.key = key;
/* 118 */       this.value = value;
/*     */     }
/*     */ 
/*     */     
/*     */     public int getIntKey() {
/* 123 */       return this.key;
/*     */     }
/*     */ 
/*     */     
/*     */     public char getCharValue() {
/* 128 */       return this.value;
/*     */     }
/*     */ 
/*     */     
/*     */     public char setValue(char value) {
/* 133 */       throw new UnsupportedOperationException();
/*     */     }
/*     */ 
/*     */     
/*     */     public boolean equals(Object o) {
/* 138 */       if (!(o instanceof Map.Entry)) return false; 
/* 139 */       if (o instanceof Int2CharMap.Entry) {
/* 140 */         Int2CharMap.Entry entry = (Int2CharMap.Entry)o;
/* 141 */         return (this.key == entry.getIntKey() && this.value == entry.getCharValue());
/*     */       } 
/* 143 */       Map.Entry<?, ?> e = (Map.Entry<?, ?>)o;
/* 144 */       Object key = e.getKey();
/* 145 */       if (key == null || !(key instanceof Integer)) return false; 
/* 146 */       Object value = e.getValue();
/* 147 */       if (value == null || !(value instanceof Character)) return false; 
/* 148 */       return (this.key == ((Integer)key).intValue() && this.value == ((Character)value).charValue());
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
/*     */     extends AbstractObjectSet<Int2CharMap.Entry>
/*     */   {
/*     */     protected final Int2CharMap map;
/*     */ 
/*     */     
/*     */     public BasicEntrySet(Int2CharMap map) {
/* 170 */       this.map = map;
/*     */     }
/*     */ 
/*     */     
/*     */     public boolean contains(Object o) {
/* 175 */       if (!(o instanceof Map.Entry)) return false; 
/* 176 */       if (o instanceof Int2CharMap.Entry) {
/* 177 */         Int2CharMap.Entry entry = (Int2CharMap.Entry)o;
/* 178 */         int i = entry.getIntKey();
/* 179 */         return (this.map.containsKey(i) && this.map.get(i) == entry.getCharValue());
/*     */       } 
/* 181 */       Map.Entry<?, ?> e = (Map.Entry<?, ?>)o;
/* 182 */       Object key = e.getKey();
/* 183 */       if (key == null || !(key instanceof Integer)) return false; 
/* 184 */       int k = ((Integer)key).intValue();
/* 185 */       Object value = e.getValue();
/* 186 */       if (value == null || !(value instanceof Character)) return false; 
/* 187 */       return (this.map.containsKey(k) && this.map.get(k) == ((Character)value).charValue());
/*     */     }
/*     */ 
/*     */     
/*     */     public boolean remove(Object o) {
/* 192 */       if (!(o instanceof Map.Entry)) return false; 
/* 193 */       if (o instanceof Int2CharMap.Entry) {
/* 194 */         Int2CharMap.Entry entry = (Int2CharMap.Entry)o;
/* 195 */         return this.map.remove(entry.getIntKey(), entry.getCharValue());
/*     */       } 
/* 197 */       Map.Entry<?, ?> e = (Map.Entry<?, ?>)o;
/* 198 */       Object key = e.getKey();
/* 199 */       if (key == null || !(key instanceof Integer)) return false; 
/* 200 */       int k = ((Integer)key).intValue();
/* 201 */       Object value = e.getValue();
/* 202 */       if (value == null || !(value instanceof Character)) return false; 
/* 203 */       char v = ((Character)value).charValue();
/* 204 */       return this.map.remove(k, v);
/*     */     }
/*     */ 
/*     */     
/*     */     public int size() {
/* 209 */       return this.map.size();
/*     */     }
/*     */ 
/*     */     
/*     */     public ObjectSpliterator<Int2CharMap.Entry> spliterator() {
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
/*     */   public IntSet keySet() {
/* 232 */     return new AbstractIntSet()
/*     */       {
/*     */         public boolean contains(int k) {
/* 235 */           return AbstractInt2CharMap.this.containsKey(k);
/*     */         }
/*     */ 
/*     */         
/*     */         public int size() {
/* 240 */           return AbstractInt2CharMap.this.size();
/*     */         }
/*     */ 
/*     */         
/*     */         public void clear() {
/* 245 */           AbstractInt2CharMap.this.clear();
/*     */         }
/*     */ 
/*     */         
/*     */         public IntIterator iterator() {
/* 250 */           return new IntIterator() {
/* 251 */               private final ObjectIterator<Int2CharMap.Entry> i = Int2CharMaps.fastIterator(AbstractInt2CharMap.this);
/*     */ 
/*     */               
/*     */               public int nextInt() {
/* 255 */                 return ((Int2CharMap.Entry)this.i.next()).getIntKey();
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
/*     */               public void forEachRemaining(IntConsumer action) {
/* 270 */                 this.i.forEachRemaining(entry -> action.accept(entry.getIntKey()));
/*     */               }
/*     */             };
/*     */         }
/*     */ 
/*     */         
/*     */         public IntSpliterator spliterator() {
/* 277 */           return IntSpliterators.asSpliterator(iterator(), Size64.sizeOf(AbstractInt2CharMap.this), 321);
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
/* 296 */     return (CharCollection)new AbstractCharCollection()
/*     */       {
/*     */         public boolean contains(char k) {
/* 299 */           return AbstractInt2CharMap.this.containsValue(k);
/*     */         }
/*     */ 
/*     */         
/*     */         public int size() {
/* 304 */           return AbstractInt2CharMap.this.size();
/*     */         }
/*     */ 
/*     */         
/*     */         public void clear() {
/* 309 */           AbstractInt2CharMap.this.clear();
/*     */         }
/*     */ 
/*     */         
/*     */         public CharIterator iterator() {
/* 314 */           return new CharIterator() {
/* 315 */               private final ObjectIterator<Int2CharMap.Entry> i = Int2CharMaps.fastIterator(AbstractInt2CharMap.this);
/*     */ 
/*     */               
/*     */               public char nextChar() {
/* 319 */                 return ((Int2CharMap.Entry)this.i.next()).getCharValue();
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
/*     */               public void forEachRemaining(CharConsumer action) {
/* 334 */                 this.i.forEachRemaining(entry -> action.accept(entry.getCharValue()));
/*     */               }
/*     */             };
/*     */         }
/*     */ 
/*     */         
/*     */         public CharSpliterator spliterator() {
/* 341 */           return CharSpliterators.asSpliterator(iterator(), Size64.sizeOf(AbstractInt2CharMap.this), 320);
/*     */         }
/*     */       };
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void putAll(Map<? extends Integer, ? extends Character> m) {
/* 350 */     if (m instanceof Int2CharMap) {
/* 351 */       ObjectIterator<Int2CharMap.Entry> i = Int2CharMaps.fastIterator((Int2CharMap)m);
/* 352 */       while (i.hasNext()) {
/* 353 */         Int2CharMap.Entry e = (Int2CharMap.Entry)i.next();
/* 354 */         put(e.getIntKey(), e.getCharValue());
/*     */       } 
/*     */     } else {
/* 357 */       int n = m.size();
/* 358 */       Iterator<? extends Map.Entry<? extends Integer, ? extends Character>> i = m.entrySet().iterator();
/*     */       
/* 360 */       while (n-- != 0) {
/* 361 */         Map.Entry<? extends Integer, ? extends Character> e = i.next();
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
/* 377 */     ObjectIterator<Int2CharMap.Entry> i = Int2CharMaps.fastIterator(this);
/* 378 */     for (; n-- != 0; h += ((Int2CharMap.Entry)i.next()).hashCode());
/* 379 */     return h;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean equals(Object o) {
/* 384 */     if (o == this) return true; 
/* 385 */     if (!(o instanceof Map)) return false; 
/* 386 */     Map<?, ?> m = (Map<?, ?>)o;
/* 387 */     if (m.size() != size()) return false; 
/* 388 */     return int2CharEntrySet().containsAll(m.entrySet());
/*     */   }
/*     */ 
/*     */   
/*     */   public String toString() {
/* 393 */     StringBuilder s = new StringBuilder();
/* 394 */     ObjectIterator<Int2CharMap.Entry> i = Int2CharMaps.fastIterator(this);
/* 395 */     int n = size();
/*     */     
/* 397 */     boolean first = true;
/* 398 */     s.append("{");
/* 399 */     while (n-- != 0) {
/* 400 */       if (first) { first = false; }
/* 401 */       else { s.append(", "); }
/* 402 */        Int2CharMap.Entry e = (Int2CharMap.Entry)i.next();
/* 403 */       s.append(String.valueOf(e.getIntKey()));
/* 404 */       s.append("=>");
/* 405 */       s.append(String.valueOf(e.getCharValue()));
/*     */     } 
/* 407 */     s.append("}");
/* 408 */     return s.toString();
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\i\\unimi\dsi\fastutil\ints\AbstractInt2CharMap.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */