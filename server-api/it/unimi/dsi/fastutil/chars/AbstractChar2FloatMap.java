/*     */ package it.unimi.dsi.fastutil.chars;
/*     */ 
/*     */ import it.unimi.dsi.fastutil.HashCommon;
/*     */ import it.unimi.dsi.fastutil.Size64;
/*     */ import it.unimi.dsi.fastutil.floats.AbstractFloatCollection;
/*     */ import it.unimi.dsi.fastutil.floats.FloatCollection;
/*     */ import it.unimi.dsi.fastutil.floats.FloatConsumer;
/*     */ import it.unimi.dsi.fastutil.floats.FloatIterator;
/*     */ import it.unimi.dsi.fastutil.floats.FloatSpliterator;
/*     */ import it.unimi.dsi.fastutil.floats.FloatSpliterators;
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
/*     */ public abstract class AbstractChar2FloatMap
/*     */   extends AbstractChar2FloatFunction
/*     */   implements Char2FloatMap, Serializable
/*     */ {
/*     */   private static final long serialVersionUID = -4940583368468432370L;
/*     */   
/*     */   public boolean containsKey(char k) {
/*  66 */     ObjectIterator<Char2FloatMap.Entry> i = char2FloatEntrySet().iterator();
/*  67 */     while (i.hasNext()) { if (((Char2FloatMap.Entry)i.next()).getCharKey() == k) return true;  }
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
/*     */   public boolean containsValue(float v) {
/*  86 */     ObjectIterator<Char2FloatMap.Entry> i = char2FloatEntrySet().iterator();
/*  87 */     while (i.hasNext()) { if (((Char2FloatMap.Entry)i.next()).getFloatValue() == v) return true;  }
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
/*     */     implements Char2FloatMap.Entry
/*     */   {
/*     */     protected char key;
/*     */ 
/*     */     
/*     */     protected float value;
/*     */ 
/*     */ 
/*     */     
/*     */     public BasicEntry() {}
/*     */ 
/*     */     
/*     */     public BasicEntry(Character key, Float value) {
/* 112 */       this.key = key.charValue();
/* 113 */       this.value = value.floatValue();
/*     */     }
/*     */     
/*     */     public BasicEntry(char key, float value) {
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
/*     */     public float getFloatValue() {
/* 128 */       return this.value;
/*     */     }
/*     */ 
/*     */     
/*     */     public float setValue(float value) {
/* 133 */       throw new UnsupportedOperationException();
/*     */     }
/*     */ 
/*     */     
/*     */     public boolean equals(Object o) {
/* 138 */       if (!(o instanceof Map.Entry)) return false; 
/* 139 */       if (o instanceof Char2FloatMap.Entry) {
/* 140 */         Char2FloatMap.Entry entry = (Char2FloatMap.Entry)o;
/* 141 */         return (this.key == entry.getCharKey() && Float.floatToIntBits(this.value) == Float.floatToIntBits(entry.getFloatValue()));
/*     */       } 
/* 143 */       Map.Entry<?, ?> e = (Map.Entry<?, ?>)o;
/* 144 */       Object key = e.getKey();
/* 145 */       if (key == null || !(key instanceof Character)) return false; 
/* 146 */       Object value = e.getValue();
/* 147 */       if (value == null || !(value instanceof Float)) return false; 
/* 148 */       return (this.key == ((Character)key).charValue() && Float.floatToIntBits(this.value) == Float.floatToIntBits(((Float)value).floatValue()));
/*     */     }
/*     */ 
/*     */     
/*     */     public int hashCode() {
/* 153 */       return this.key ^ HashCommon.float2int(this.value);
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
/*     */     extends AbstractObjectSet<Char2FloatMap.Entry>
/*     */   {
/*     */     protected final Char2FloatMap map;
/*     */ 
/*     */     
/*     */     public BasicEntrySet(Char2FloatMap map) {
/* 170 */       this.map = map;
/*     */     }
/*     */ 
/*     */     
/*     */     public boolean contains(Object o) {
/* 175 */       if (!(o instanceof Map.Entry)) return false; 
/* 176 */       if (o instanceof Char2FloatMap.Entry) {
/* 177 */         Char2FloatMap.Entry entry = (Char2FloatMap.Entry)o;
/* 178 */         char c = entry.getCharKey();
/* 179 */         return (this.map.containsKey(c) && Float.floatToIntBits(this.map.get(c)) == Float.floatToIntBits(entry.getFloatValue()));
/*     */       } 
/* 181 */       Map.Entry<?, ?> e = (Map.Entry<?, ?>)o;
/* 182 */       Object key = e.getKey();
/* 183 */       if (key == null || !(key instanceof Character)) return false; 
/* 184 */       char k = ((Character)key).charValue();
/* 185 */       Object value = e.getValue();
/* 186 */       if (value == null || !(value instanceof Float)) return false; 
/* 187 */       return (this.map.containsKey(k) && Float.floatToIntBits(this.map.get(k)) == Float.floatToIntBits(((Float)value).floatValue()));
/*     */     }
/*     */ 
/*     */     
/*     */     public boolean remove(Object o) {
/* 192 */       if (!(o instanceof Map.Entry)) return false; 
/* 193 */       if (o instanceof Char2FloatMap.Entry) {
/* 194 */         Char2FloatMap.Entry entry = (Char2FloatMap.Entry)o;
/* 195 */         return this.map.remove(entry.getCharKey(), entry.getFloatValue());
/*     */       } 
/* 197 */       Map.Entry<?, ?> e = (Map.Entry<?, ?>)o;
/* 198 */       Object key = e.getKey();
/* 199 */       if (key == null || !(key instanceof Character)) return false; 
/* 200 */       char k = ((Character)key).charValue();
/* 201 */       Object value = e.getValue();
/* 202 */       if (value == null || !(value instanceof Float)) return false; 
/* 203 */       float v = ((Float)value).floatValue();
/* 204 */       return this.map.remove(k, v);
/*     */     }
/*     */ 
/*     */     
/*     */     public int size() {
/* 209 */       return this.map.size();
/*     */     }
/*     */ 
/*     */     
/*     */     public ObjectSpliterator<Char2FloatMap.Entry> spliterator() {
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
/* 235 */           return AbstractChar2FloatMap.this.containsKey(k);
/*     */         }
/*     */ 
/*     */         
/*     */         public int size() {
/* 240 */           return AbstractChar2FloatMap.this.size();
/*     */         }
/*     */ 
/*     */         
/*     */         public void clear() {
/* 245 */           AbstractChar2FloatMap.this.clear();
/*     */         }
/*     */ 
/*     */         
/*     */         public CharIterator iterator() {
/* 250 */           return new CharIterator() {
/* 251 */               private final ObjectIterator<Char2FloatMap.Entry> i = Char2FloatMaps.fastIterator(AbstractChar2FloatMap.this);
/*     */ 
/*     */               
/*     */               public char nextChar() {
/* 255 */                 return ((Char2FloatMap.Entry)this.i.next()).getCharKey();
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
/* 277 */           return CharSpliterators.asSpliterator(iterator(), Size64.sizeOf(AbstractChar2FloatMap.this), 321);
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
/*     */   public FloatCollection values() {
/* 296 */     return (FloatCollection)new AbstractFloatCollection()
/*     */       {
/*     */         public boolean contains(float k) {
/* 299 */           return AbstractChar2FloatMap.this.containsValue(k);
/*     */         }
/*     */ 
/*     */         
/*     */         public int size() {
/* 304 */           return AbstractChar2FloatMap.this.size();
/*     */         }
/*     */ 
/*     */         
/*     */         public void clear() {
/* 309 */           AbstractChar2FloatMap.this.clear();
/*     */         }
/*     */ 
/*     */         
/*     */         public FloatIterator iterator() {
/* 314 */           return new FloatIterator() {
/* 315 */               private final ObjectIterator<Char2FloatMap.Entry> i = Char2FloatMaps.fastIterator(AbstractChar2FloatMap.this);
/*     */ 
/*     */               
/*     */               public float nextFloat() {
/* 319 */                 return ((Char2FloatMap.Entry)this.i.next()).getFloatValue();
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
/*     */               public void forEachRemaining(FloatConsumer action) {
/* 334 */                 this.i.forEachRemaining(entry -> action.accept(entry.getFloatValue()));
/*     */               }
/*     */             };
/*     */         }
/*     */ 
/*     */         
/*     */         public FloatSpliterator spliterator() {
/* 341 */           return FloatSpliterators.asSpliterator(iterator(), Size64.sizeOf(AbstractChar2FloatMap.this), 320);
/*     */         }
/*     */       };
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void putAll(Map<? extends Character, ? extends Float> m) {
/* 350 */     if (m instanceof Char2FloatMap) {
/* 351 */       ObjectIterator<Char2FloatMap.Entry> i = Char2FloatMaps.fastIterator((Char2FloatMap)m);
/* 352 */       while (i.hasNext()) {
/* 353 */         Char2FloatMap.Entry e = (Char2FloatMap.Entry)i.next();
/* 354 */         put(e.getCharKey(), e.getFloatValue());
/*     */       } 
/*     */     } else {
/* 357 */       int n = m.size();
/* 358 */       Iterator<? extends Map.Entry<? extends Character, ? extends Float>> i = m.entrySet().iterator();
/*     */       
/* 360 */       while (n-- != 0) {
/* 361 */         Map.Entry<? extends Character, ? extends Float> e = i.next();
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
/* 377 */     ObjectIterator<Char2FloatMap.Entry> i = Char2FloatMaps.fastIterator(this);
/* 378 */     for (; n-- != 0; h += ((Char2FloatMap.Entry)i.next()).hashCode());
/* 379 */     return h;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean equals(Object o) {
/* 384 */     if (o == this) return true; 
/* 385 */     if (!(o instanceof Map)) return false; 
/* 386 */     Map<?, ?> m = (Map<?, ?>)o;
/* 387 */     if (m.size() != size()) return false; 
/* 388 */     return char2FloatEntrySet().containsAll(m.entrySet());
/*     */   }
/*     */ 
/*     */   
/*     */   public String toString() {
/* 393 */     StringBuilder s = new StringBuilder();
/* 394 */     ObjectIterator<Char2FloatMap.Entry> i = Char2FloatMaps.fastIterator(this);
/* 395 */     int n = size();
/*     */     
/* 397 */     boolean first = true;
/* 398 */     s.append("{");
/* 399 */     while (n-- != 0) {
/* 400 */       if (first) { first = false; }
/* 401 */       else { s.append(", "); }
/* 402 */        Char2FloatMap.Entry e = (Char2FloatMap.Entry)i.next();
/* 403 */       s.append(String.valueOf(e.getCharKey()));
/* 404 */       s.append("=>");
/* 405 */       s.append(String.valueOf(e.getFloatValue()));
/*     */     } 
/* 407 */     s.append("}");
/* 408 */     return s.toString();
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\i\\unimi\dsi\fastutil\chars\AbstractChar2FloatMap.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */