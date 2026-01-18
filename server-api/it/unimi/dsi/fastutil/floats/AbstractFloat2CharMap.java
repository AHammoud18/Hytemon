/*     */ package it.unimi.dsi.fastutil.floats;
/*     */ 
/*     */ import it.unimi.dsi.fastutil.HashCommon;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public abstract class AbstractFloat2CharMap
/*     */   extends AbstractFloat2CharFunction
/*     */   implements Float2CharMap, Serializable
/*     */ {
/*     */   private static final long serialVersionUID = -4940583368468432370L;
/*     */   
/*     */   public boolean containsKey(float k) {
/*  66 */     ObjectIterator<Float2CharMap.Entry> i = float2CharEntrySet().iterator();
/*  67 */     while (i.hasNext()) { if (((Float2CharMap.Entry)i.next()).getFloatKey() == k) return true;  }
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
/*  86 */     ObjectIterator<Float2CharMap.Entry> i = float2CharEntrySet().iterator();
/*  87 */     while (i.hasNext()) { if (((Float2CharMap.Entry)i.next()).getCharValue() == v) return true;  }
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
/*     */     implements Float2CharMap.Entry
/*     */   {
/*     */     protected float key;
/*     */ 
/*     */     
/*     */     protected char value;
/*     */ 
/*     */ 
/*     */     
/*     */     public BasicEntry() {}
/*     */ 
/*     */     
/*     */     public BasicEntry(Float key, Character value) {
/* 112 */       this.key = key.floatValue();
/* 113 */       this.value = value.charValue();
/*     */     }
/*     */     
/*     */     public BasicEntry(float key, char value) {
/* 117 */       this.key = key;
/* 118 */       this.value = value;
/*     */     }
/*     */ 
/*     */     
/*     */     public float getFloatKey() {
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
/* 139 */       if (o instanceof Float2CharMap.Entry) {
/* 140 */         Float2CharMap.Entry entry = (Float2CharMap.Entry)o;
/* 141 */         return (Float.floatToIntBits(this.key) == Float.floatToIntBits(entry.getFloatKey()) && this.value == entry.getCharValue());
/*     */       } 
/* 143 */       Map.Entry<?, ?> e = (Map.Entry<?, ?>)o;
/* 144 */       Object key = e.getKey();
/* 145 */       if (key == null || !(key instanceof Float)) return false; 
/* 146 */       Object value = e.getValue();
/* 147 */       if (value == null || !(value instanceof Character)) return false; 
/* 148 */       return (Float.floatToIntBits(this.key) == Float.floatToIntBits(((Float)key).floatValue()) && this.value == ((Character)value).charValue());
/*     */     }
/*     */ 
/*     */     
/*     */     public int hashCode() {
/* 153 */       return HashCommon.float2int(this.key) ^ this.value;
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
/*     */     extends AbstractObjectSet<Float2CharMap.Entry>
/*     */   {
/*     */     protected final Float2CharMap map;
/*     */ 
/*     */     
/*     */     public BasicEntrySet(Float2CharMap map) {
/* 170 */       this.map = map;
/*     */     }
/*     */ 
/*     */     
/*     */     public boolean contains(Object o) {
/* 175 */       if (!(o instanceof Map.Entry)) return false; 
/* 176 */       if (o instanceof Float2CharMap.Entry) {
/* 177 */         Float2CharMap.Entry entry = (Float2CharMap.Entry)o;
/* 178 */         float f = entry.getFloatKey();
/* 179 */         return (this.map.containsKey(f) && this.map.get(f) == entry.getCharValue());
/*     */       } 
/* 181 */       Map.Entry<?, ?> e = (Map.Entry<?, ?>)o;
/* 182 */       Object key = e.getKey();
/* 183 */       if (key == null || !(key instanceof Float)) return false; 
/* 184 */       float k = ((Float)key).floatValue();
/* 185 */       Object value = e.getValue();
/* 186 */       if (value == null || !(value instanceof Character)) return false; 
/* 187 */       return (this.map.containsKey(k) && this.map.get(k) == ((Character)value).charValue());
/*     */     }
/*     */ 
/*     */     
/*     */     public boolean remove(Object o) {
/* 192 */       if (!(o instanceof Map.Entry)) return false; 
/* 193 */       if (o instanceof Float2CharMap.Entry) {
/* 194 */         Float2CharMap.Entry entry = (Float2CharMap.Entry)o;
/* 195 */         return this.map.remove(entry.getFloatKey(), entry.getCharValue());
/*     */       } 
/* 197 */       Map.Entry<?, ?> e = (Map.Entry<?, ?>)o;
/* 198 */       Object key = e.getKey();
/* 199 */       if (key == null || !(key instanceof Float)) return false; 
/* 200 */       float k = ((Float)key).floatValue();
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
/*     */     public ObjectSpliterator<Float2CharMap.Entry> spliterator() {
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
/*     */   public FloatSet keySet() {
/* 232 */     return new AbstractFloatSet()
/*     */       {
/*     */         public boolean contains(float k) {
/* 235 */           return AbstractFloat2CharMap.this.containsKey(k);
/*     */         }
/*     */ 
/*     */         
/*     */         public int size() {
/* 240 */           return AbstractFloat2CharMap.this.size();
/*     */         }
/*     */ 
/*     */         
/*     */         public void clear() {
/* 245 */           AbstractFloat2CharMap.this.clear();
/*     */         }
/*     */ 
/*     */         
/*     */         public FloatIterator iterator() {
/* 250 */           return new FloatIterator() {
/* 251 */               private final ObjectIterator<Float2CharMap.Entry> i = Float2CharMaps.fastIterator(AbstractFloat2CharMap.this);
/*     */ 
/*     */               
/*     */               public float nextFloat() {
/* 255 */                 return ((Float2CharMap.Entry)this.i.next()).getFloatKey();
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
/*     */               public void forEachRemaining(FloatConsumer action) {
/* 270 */                 this.i.forEachRemaining(entry -> action.accept(entry.getFloatKey()));
/*     */               }
/*     */             };
/*     */         }
/*     */ 
/*     */         
/*     */         public FloatSpliterator spliterator() {
/* 277 */           return FloatSpliterators.asSpliterator(iterator(), Size64.sizeOf(AbstractFloat2CharMap.this), 321);
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
/* 299 */           return AbstractFloat2CharMap.this.containsValue(k);
/*     */         }
/*     */ 
/*     */         
/*     */         public int size() {
/* 304 */           return AbstractFloat2CharMap.this.size();
/*     */         }
/*     */ 
/*     */         
/*     */         public void clear() {
/* 309 */           AbstractFloat2CharMap.this.clear();
/*     */         }
/*     */ 
/*     */         
/*     */         public CharIterator iterator() {
/* 314 */           return new CharIterator() {
/* 315 */               private final ObjectIterator<Float2CharMap.Entry> i = Float2CharMaps.fastIterator(AbstractFloat2CharMap.this);
/*     */ 
/*     */               
/*     */               public char nextChar() {
/* 319 */                 return ((Float2CharMap.Entry)this.i.next()).getCharValue();
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
/* 341 */           return CharSpliterators.asSpliterator(iterator(), Size64.sizeOf(AbstractFloat2CharMap.this), 320);
/*     */         }
/*     */       };
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void putAll(Map<? extends Float, ? extends Character> m) {
/* 350 */     if (m instanceof Float2CharMap) {
/* 351 */       ObjectIterator<Float2CharMap.Entry> i = Float2CharMaps.fastIterator((Float2CharMap)m);
/* 352 */       while (i.hasNext()) {
/* 353 */         Float2CharMap.Entry e = (Float2CharMap.Entry)i.next();
/* 354 */         put(e.getFloatKey(), e.getCharValue());
/*     */       } 
/*     */     } else {
/* 357 */       int n = m.size();
/* 358 */       Iterator<? extends Map.Entry<? extends Float, ? extends Character>> i = m.entrySet().iterator();
/*     */       
/* 360 */       while (n-- != 0) {
/* 361 */         Map.Entry<? extends Float, ? extends Character> e = i.next();
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
/* 377 */     ObjectIterator<Float2CharMap.Entry> i = Float2CharMaps.fastIterator(this);
/* 378 */     for (; n-- != 0; h += ((Float2CharMap.Entry)i.next()).hashCode());
/* 379 */     return h;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean equals(Object o) {
/* 384 */     if (o == this) return true; 
/* 385 */     if (!(o instanceof Map)) return false; 
/* 386 */     Map<?, ?> m = (Map<?, ?>)o;
/* 387 */     if (m.size() != size()) return false; 
/* 388 */     return float2CharEntrySet().containsAll(m.entrySet());
/*     */   }
/*     */ 
/*     */   
/*     */   public String toString() {
/* 393 */     StringBuilder s = new StringBuilder();
/* 394 */     ObjectIterator<Float2CharMap.Entry> i = Float2CharMaps.fastIterator(this);
/* 395 */     int n = size();
/*     */     
/* 397 */     boolean first = true;
/* 398 */     s.append("{");
/* 399 */     while (n-- != 0) {
/* 400 */       if (first) { first = false; }
/* 401 */       else { s.append(", "); }
/* 402 */        Float2CharMap.Entry e = (Float2CharMap.Entry)i.next();
/* 403 */       s.append(String.valueOf(e.getFloatKey()));
/* 404 */       s.append("=>");
/* 405 */       s.append(String.valueOf(e.getCharValue()));
/*     */     } 
/* 407 */     s.append("}");
/* 408 */     return s.toString();
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\i\\unimi\dsi\fastutil\floats\AbstractFloat2CharMap.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */