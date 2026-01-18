/*     */ package it.unimi.dsi.fastutil.bytes;
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
/*     */ public abstract class AbstractByte2ByteMap
/*     */   extends AbstractByte2ByteFunction
/*     */   implements Byte2ByteMap, Serializable
/*     */ {
/*     */   private static final long serialVersionUID = -4940583368468432370L;
/*     */   
/*     */   public boolean containsKey(byte k) {
/*  60 */     ObjectIterator<Byte2ByteMap.Entry> i = byte2ByteEntrySet().iterator();
/*  61 */     while (i.hasNext()) { if (((Byte2ByteMap.Entry)i.next()).getByteKey() == k) return true;  }
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
/*     */   public boolean containsValue(byte v) {
/*  80 */     ObjectIterator<Byte2ByteMap.Entry> i = byte2ByteEntrySet().iterator();
/*  81 */     while (i.hasNext()) { if (((Byte2ByteMap.Entry)i.next()).getByteValue() == v) return true;  }
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
/*     */     implements Byte2ByteMap.Entry
/*     */   {
/*     */     protected byte key;
/*     */ 
/*     */     
/*     */     protected byte value;
/*     */ 
/*     */ 
/*     */     
/*     */     public BasicEntry() {}
/*     */ 
/*     */     
/*     */     public BasicEntry(Byte key, Byte value) {
/* 106 */       this.key = key.byteValue();
/* 107 */       this.value = value.byteValue();
/*     */     }
/*     */     
/*     */     public BasicEntry(byte key, byte value) {
/* 111 */       this.key = key;
/* 112 */       this.value = value;
/*     */     }
/*     */ 
/*     */     
/*     */     public byte getByteKey() {
/* 117 */       return this.key;
/*     */     }
/*     */ 
/*     */     
/*     */     public byte getByteValue() {
/* 122 */       return this.value;
/*     */     }
/*     */ 
/*     */     
/*     */     public byte setValue(byte value) {
/* 127 */       throw new UnsupportedOperationException();
/*     */     }
/*     */ 
/*     */     
/*     */     public boolean equals(Object o) {
/* 132 */       if (!(o instanceof Map.Entry)) return false; 
/* 133 */       if (o instanceof Byte2ByteMap.Entry) {
/* 134 */         Byte2ByteMap.Entry entry = (Byte2ByteMap.Entry)o;
/* 135 */         return (this.key == entry.getByteKey() && this.value == entry.getByteValue());
/*     */       } 
/* 137 */       Map.Entry<?, ?> e = (Map.Entry<?, ?>)o;
/* 138 */       Object key = e.getKey();
/* 139 */       if (key == null || !(key instanceof Byte)) return false; 
/* 140 */       Object value = e.getValue();
/* 141 */       if (value == null || !(value instanceof Byte)) return false; 
/* 142 */       return (this.key == ((Byte)key).byteValue() && this.value == ((Byte)value).byteValue());
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
/*     */     extends AbstractObjectSet<Byte2ByteMap.Entry>
/*     */   {
/*     */     protected final Byte2ByteMap map;
/*     */ 
/*     */     
/*     */     public BasicEntrySet(Byte2ByteMap map) {
/* 164 */       this.map = map;
/*     */     }
/*     */ 
/*     */     
/*     */     public boolean contains(Object o) {
/* 169 */       if (!(o instanceof Map.Entry)) return false; 
/* 170 */       if (o instanceof Byte2ByteMap.Entry) {
/* 171 */         Byte2ByteMap.Entry entry = (Byte2ByteMap.Entry)o;
/* 172 */         byte b = entry.getByteKey();
/* 173 */         return (this.map.containsKey(b) && this.map.get(b) == entry.getByteValue());
/*     */       } 
/* 175 */       Map.Entry<?, ?> e = (Map.Entry<?, ?>)o;
/* 176 */       Object key = e.getKey();
/* 177 */       if (key == null || !(key instanceof Byte)) return false; 
/* 178 */       byte k = ((Byte)key).byteValue();
/* 179 */       Object value = e.getValue();
/* 180 */       if (value == null || !(value instanceof Byte)) return false; 
/* 181 */       return (this.map.containsKey(k) && this.map.get(k) == ((Byte)value).byteValue());
/*     */     }
/*     */ 
/*     */     
/*     */     public boolean remove(Object o) {
/* 186 */       if (!(o instanceof Map.Entry)) return false; 
/* 187 */       if (o instanceof Byte2ByteMap.Entry) {
/* 188 */         Byte2ByteMap.Entry entry = (Byte2ByteMap.Entry)o;
/* 189 */         return this.map.remove(entry.getByteKey(), entry.getByteValue());
/*     */       } 
/* 191 */       Map.Entry<?, ?> e = (Map.Entry<?, ?>)o;
/* 192 */       Object key = e.getKey();
/* 193 */       if (key == null || !(key instanceof Byte)) return false; 
/* 194 */       byte k = ((Byte)key).byteValue();
/* 195 */       Object value = e.getValue();
/* 196 */       if (value == null || !(value instanceof Byte)) return false; 
/* 197 */       byte v = ((Byte)value).byteValue();
/* 198 */       return this.map.remove(k, v);
/*     */     }
/*     */ 
/*     */     
/*     */     public int size() {
/* 203 */       return this.map.size();
/*     */     }
/*     */ 
/*     */     
/*     */     public ObjectSpliterator<Byte2ByteMap.Entry> spliterator() {
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
/*     */   public ByteSet keySet() {
/* 226 */     return new AbstractByteSet()
/*     */       {
/*     */         public boolean contains(byte k) {
/* 229 */           return AbstractByte2ByteMap.this.containsKey(k);
/*     */         }
/*     */ 
/*     */         
/*     */         public int size() {
/* 234 */           return AbstractByte2ByteMap.this.size();
/*     */         }
/*     */ 
/*     */         
/*     */         public void clear() {
/* 239 */           AbstractByte2ByteMap.this.clear();
/*     */         }
/*     */ 
/*     */         
/*     */         public ByteIterator iterator() {
/* 244 */           return new ByteIterator() {
/* 245 */               private final ObjectIterator<Byte2ByteMap.Entry> i = Byte2ByteMaps.fastIterator(AbstractByte2ByteMap.this);
/*     */ 
/*     */               
/*     */               public byte nextByte() {
/* 249 */                 return ((Byte2ByteMap.Entry)this.i.next()).getByteKey();
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
/*     */               public void forEachRemaining(ByteConsumer action) {
/* 264 */                 this.i.forEachRemaining(entry -> action.accept(entry.getByteKey()));
/*     */               }
/*     */             };
/*     */         }
/*     */ 
/*     */         
/*     */         public ByteSpliterator spliterator() {
/* 271 */           return ByteSpliterators.asSpliterator(iterator(), Size64.sizeOf(AbstractByte2ByteMap.this), 321);
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
/*     */   public ByteCollection values() {
/* 290 */     return new AbstractByteCollection()
/*     */       {
/*     */         public boolean contains(byte k) {
/* 293 */           return AbstractByte2ByteMap.this.containsValue(k);
/*     */         }
/*     */ 
/*     */         
/*     */         public int size() {
/* 298 */           return AbstractByte2ByteMap.this.size();
/*     */         }
/*     */ 
/*     */         
/*     */         public void clear() {
/* 303 */           AbstractByte2ByteMap.this.clear();
/*     */         }
/*     */ 
/*     */         
/*     */         public ByteIterator iterator() {
/* 308 */           return new ByteIterator() {
/* 309 */               private final ObjectIterator<Byte2ByteMap.Entry> i = Byte2ByteMaps.fastIterator(AbstractByte2ByteMap.this);
/*     */ 
/*     */               
/*     */               public byte nextByte() {
/* 313 */                 return ((Byte2ByteMap.Entry)this.i.next()).getByteValue();
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
/*     */               public void forEachRemaining(ByteConsumer action) {
/* 328 */                 this.i.forEachRemaining(entry -> action.accept(entry.getByteValue()));
/*     */               }
/*     */             };
/*     */         }
/*     */ 
/*     */         
/*     */         public ByteSpliterator spliterator() {
/* 335 */           return ByteSpliterators.asSpliterator(iterator(), Size64.sizeOf(AbstractByte2ByteMap.this), 320);
/*     */         }
/*     */       };
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void putAll(Map<? extends Byte, ? extends Byte> m) {
/* 344 */     if (m instanceof Byte2ByteMap) {
/* 345 */       ObjectIterator<Byte2ByteMap.Entry> i = Byte2ByteMaps.fastIterator((Byte2ByteMap)m);
/* 346 */       while (i.hasNext()) {
/* 347 */         Byte2ByteMap.Entry e = (Byte2ByteMap.Entry)i.next();
/* 348 */         put(e.getByteKey(), e.getByteValue());
/*     */       } 
/*     */     } else {
/* 351 */       int n = m.size();
/* 352 */       Iterator<? extends Map.Entry<? extends Byte, ? extends Byte>> i = m.entrySet().iterator();
/*     */       
/* 354 */       while (n-- != 0) {
/* 355 */         Map.Entry<? extends Byte, ? extends Byte> e = i.next();
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
/* 371 */     ObjectIterator<Byte2ByteMap.Entry> i = Byte2ByteMaps.fastIterator(this);
/* 372 */     for (; n-- != 0; h += ((Byte2ByteMap.Entry)i.next()).hashCode());
/* 373 */     return h;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean equals(Object o) {
/* 378 */     if (o == this) return true; 
/* 379 */     if (!(o instanceof Map)) return false; 
/* 380 */     Map<?, ?> m = (Map<?, ?>)o;
/* 381 */     if (m.size() != size()) return false; 
/* 382 */     return byte2ByteEntrySet().containsAll(m.entrySet());
/*     */   }
/*     */ 
/*     */   
/*     */   public String toString() {
/* 387 */     StringBuilder s = new StringBuilder();
/* 388 */     ObjectIterator<Byte2ByteMap.Entry> i = Byte2ByteMaps.fastIterator(this);
/* 389 */     int n = size();
/*     */     
/* 391 */     boolean first = true;
/* 392 */     s.append("{");
/* 393 */     while (n-- != 0) {
/* 394 */       if (first) { first = false; }
/* 395 */       else { s.append(", "); }
/* 396 */        Byte2ByteMap.Entry e = (Byte2ByteMap.Entry)i.next();
/* 397 */       s.append(String.valueOf(e.getByteKey()));
/* 398 */       s.append("=>");
/* 399 */       s.append(String.valueOf(e.getByteValue()));
/*     */     } 
/* 401 */     s.append("}");
/* 402 */     return s.toString();
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\i\\unimi\dsi\fastutil\bytes\AbstractByte2ByteMap.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */