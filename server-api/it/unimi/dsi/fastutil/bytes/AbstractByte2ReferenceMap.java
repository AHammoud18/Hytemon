/*     */ package it.unimi.dsi.fastutil.bytes;
/*     */ 
/*     */ import it.unimi.dsi.fastutil.Size64;
/*     */ import it.unimi.dsi.fastutil.objects.AbstractObjectSet;
/*     */ import it.unimi.dsi.fastutil.objects.AbstractReferenceCollection;
/*     */ import it.unimi.dsi.fastutil.objects.ObjectIterator;
/*     */ import it.unimi.dsi.fastutil.objects.ObjectSpliterator;
/*     */ import it.unimi.dsi.fastutil.objects.ObjectSpliterators;
/*     */ import it.unimi.dsi.fastutil.objects.ReferenceCollection;
/*     */ import java.io.Serializable;
/*     */ import java.util.Collection;
/*     */ import java.util.Iterator;
/*     */ import java.util.Map;
/*     */ import java.util.Set;
/*     */ import java.util.Spliterator;
/*     */ import java.util.function.Consumer;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public abstract class AbstractByte2ReferenceMap<V>
/*     */   extends AbstractByte2ReferenceFunction<V>
/*     */   implements Byte2ReferenceMap<V>, Serializable
/*     */ {
/*     */   private static final long serialVersionUID = -4940583368468432370L;
/*     */   
/*     */   public boolean containsKey(byte k) {
/*  63 */     ObjectIterator<Byte2ReferenceMap.Entry<V>> i = byte2ReferenceEntrySet().iterator();
/*  64 */     while (i.hasNext()) { if (((Byte2ReferenceMap.Entry)i.next()).getByteKey() == k) return true;  }
/*  65 */      return false;
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
/*     */   public boolean containsValue(Object v) {
/*  83 */     ObjectIterator<Byte2ReferenceMap.Entry<V>> i = byte2ReferenceEntrySet().iterator();
/*  84 */     while (i.hasNext()) { if (((Byte2ReferenceMap.Entry)i.next()).getValue() == v) return true;  }
/*  85 */      return false;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isEmpty() {
/*  90 */     return (size() == 0);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public static class BasicEntry<V>
/*     */     implements Byte2ReferenceMap.Entry<V>
/*     */   {
/*     */     protected byte key;
/*     */ 
/*     */     
/*     */     protected V value;
/*     */ 
/*     */ 
/*     */     
/*     */     public BasicEntry() {}
/*     */ 
/*     */     
/*     */     public BasicEntry(Byte key, V value) {
/* 109 */       this.key = key.byteValue();
/* 110 */       this.value = value;
/*     */     }
/*     */     
/*     */     public BasicEntry(byte key, V value) {
/* 114 */       this.key = key;
/* 115 */       this.value = value;
/*     */     }
/*     */ 
/*     */     
/*     */     public byte getByteKey() {
/* 120 */       return this.key;
/*     */     }
/*     */ 
/*     */     
/*     */     public V getValue() {
/* 125 */       return this.value;
/*     */     }
/*     */ 
/*     */     
/*     */     public V setValue(V value) {
/* 130 */       throw new UnsupportedOperationException();
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     public boolean equals(Object o) {
/* 136 */       if (!(o instanceof Map.Entry)) return false; 
/* 137 */       if (o instanceof Byte2ReferenceMap.Entry) {
/* 138 */         Byte2ReferenceMap.Entry<V> entry = (Byte2ReferenceMap.Entry<V>)o;
/* 139 */         return (this.key == entry.getByteKey() && this.value == entry.getValue());
/*     */       } 
/* 141 */       Map.Entry<?, ?> e = (Map.Entry<?, ?>)o;
/* 142 */       Object key = e.getKey();
/* 143 */       if (key == null || !(key instanceof Byte)) return false; 
/* 144 */       Object value = e.getValue();
/* 145 */       return (this.key == ((Byte)key).byteValue() && this.value == value);
/*     */     }
/*     */ 
/*     */     
/*     */     public int hashCode() {
/* 150 */       return this.key ^ ((this.value == null) ? 0 : System.identityHashCode(this.value));
/*     */     }
/*     */ 
/*     */     
/*     */     public String toString() {
/* 155 */       return this.key + "->" + this.value;
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   public static abstract class BasicEntrySet<V>
/*     */     extends AbstractObjectSet<Byte2ReferenceMap.Entry<V>>
/*     */   {
/*     */     protected final Byte2ReferenceMap<V> map;
/*     */ 
/*     */     
/*     */     public BasicEntrySet(Byte2ReferenceMap<V> map) {
/* 167 */       this.map = map;
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     public boolean contains(Object o) {
/* 173 */       if (!(o instanceof Map.Entry)) return false; 
/* 174 */       if (o instanceof Byte2ReferenceMap.Entry) {
/* 175 */         Byte2ReferenceMap.Entry<V> entry = (Byte2ReferenceMap.Entry<V>)o;
/* 176 */         byte b = entry.getByteKey();
/* 177 */         return (this.map.containsKey(b) && this.map.get(b) == entry.getValue());
/*     */       } 
/* 179 */       Map.Entry<?, ?> e = (Map.Entry<?, ?>)o;
/* 180 */       Object key = e.getKey();
/* 181 */       if (key == null || !(key instanceof Byte)) return false; 
/* 182 */       byte k = ((Byte)key).byteValue();
/* 183 */       Object value = e.getValue();
/* 184 */       return (this.map.containsKey(k) && this.map.get(k) == value);
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     public boolean remove(Object o) {
/* 190 */       if (!(o instanceof Map.Entry)) return false; 
/* 191 */       if (o instanceof Byte2ReferenceMap.Entry) {
/* 192 */         Byte2ReferenceMap.Entry<V> entry = (Byte2ReferenceMap.Entry<V>)o;
/* 193 */         return this.map.remove(entry.getByteKey(), entry.getValue());
/*     */       } 
/* 195 */       Map.Entry<?, ?> e = (Map.Entry<?, ?>)o;
/* 196 */       Object key = e.getKey();
/* 197 */       if (key == null || !(key instanceof Byte)) return false; 
/* 198 */       byte k = ((Byte)key).byteValue();
/* 199 */       Object v = e.getValue();
/* 200 */       return this.map.remove(k, v);
/*     */     }
/*     */ 
/*     */     
/*     */     public int size() {
/* 205 */       return this.map.size();
/*     */     }
/*     */ 
/*     */     
/*     */     public ObjectSpliterator<Byte2ReferenceMap.Entry<V>> spliterator() {
/* 210 */       return ObjectSpliterators.asSpliterator(iterator(), Size64.sizeOf(this.map), 65);
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
/* 228 */     return new AbstractByteSet()
/*     */       {
/*     */         public boolean contains(byte k) {
/* 231 */           return AbstractByte2ReferenceMap.this.containsKey(k);
/*     */         }
/*     */ 
/*     */         
/*     */         public int size() {
/* 236 */           return AbstractByte2ReferenceMap.this.size();
/*     */         }
/*     */ 
/*     */         
/*     */         public void clear() {
/* 241 */           AbstractByte2ReferenceMap.this.clear();
/*     */         }
/*     */ 
/*     */         
/*     */         public ByteIterator iterator() {
/* 246 */           return new ByteIterator() {
/* 247 */               private final ObjectIterator<Byte2ReferenceMap.Entry<V>> i = Byte2ReferenceMaps.fastIterator(AbstractByte2ReferenceMap.this);
/*     */ 
/*     */               
/*     */               public byte nextByte() {
/* 251 */                 return ((Byte2ReferenceMap.Entry)this.i.next()).getByteKey();
/*     */               }
/*     */ 
/*     */               
/*     */               public boolean hasNext() {
/* 256 */                 return this.i.hasNext();
/*     */               }
/*     */ 
/*     */               
/*     */               public void remove() {
/* 261 */                 this.i.remove();
/*     */               }
/*     */ 
/*     */               
/*     */               public void forEachRemaining(ByteConsumer action) {
/* 266 */                 this.i.forEachRemaining(entry -> action.accept(entry.getByteKey()));
/*     */               }
/*     */             };
/*     */         }
/*     */ 
/*     */         
/*     */         public ByteSpliterator spliterator() {
/* 273 */           return ByteSpliterators.asSpliterator(iterator(), Size64.sizeOf(AbstractByte2ReferenceMap.this), 321);
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
/*     */   public ReferenceCollection<V> values() {
/* 292 */     return (ReferenceCollection<V>)new AbstractReferenceCollection<V>()
/*     */       {
/*     */         public boolean contains(Object k) {
/* 295 */           return AbstractByte2ReferenceMap.this.containsValue(k);
/*     */         }
/*     */ 
/*     */         
/*     */         public int size() {
/* 300 */           return AbstractByte2ReferenceMap.this.size();
/*     */         }
/*     */ 
/*     */         
/*     */         public void clear() {
/* 305 */           AbstractByte2ReferenceMap.this.clear();
/*     */         }
/*     */ 
/*     */         
/*     */         public ObjectIterator<V> iterator() {
/* 310 */           return new ObjectIterator<V>() {
/* 311 */               private final ObjectIterator<Byte2ReferenceMap.Entry<V>> i = Byte2ReferenceMaps.fastIterator(AbstractByte2ReferenceMap.this);
/*     */ 
/*     */               
/*     */               public V next() {
/* 315 */                 return ((Byte2ReferenceMap.Entry<V>)this.i.next()).getValue();
/*     */               }
/*     */ 
/*     */               
/*     */               public boolean hasNext() {
/* 320 */                 return this.i.hasNext();
/*     */               }
/*     */ 
/*     */               
/*     */               public void remove() {
/* 325 */                 this.i.remove();
/*     */               }
/*     */ 
/*     */               
/*     */               public void forEachRemaining(Consumer<? super V> action) {
/* 330 */                 this.i.forEachRemaining(entry -> action.accept(entry.getValue()));
/*     */               }
/*     */             };
/*     */         }
/*     */ 
/*     */         
/*     */         public ObjectSpliterator<V> spliterator() {
/* 337 */           return ObjectSpliterators.asSpliterator(iterator(), Size64.sizeOf(AbstractByte2ReferenceMap.this), 64);
/*     */         }
/*     */       };
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void putAll(Map<? extends Byte, ? extends V> m) {
/* 346 */     if (m instanceof Byte2ReferenceMap) {
/* 347 */       ObjectIterator<Byte2ReferenceMap.Entry<V>> i = Byte2ReferenceMaps.fastIterator((Byte2ReferenceMap)m);
/* 348 */       while (i.hasNext()) {
/* 349 */         Byte2ReferenceMap.Entry<? extends V> e = (Byte2ReferenceMap.Entry<? extends V>)i.next();
/* 350 */         put(e.getByteKey(), e.getValue());
/*     */       } 
/*     */     } else {
/* 353 */       int n = m.size();
/* 354 */       Iterator<? extends Map.Entry<? extends Byte, ? extends V>> i = m.entrySet().iterator();
/*     */       
/* 356 */       while (n-- != 0) {
/* 357 */         Map.Entry<? extends Byte, ? extends V> e = i.next();
/* 358 */         put(e.getKey(), e.getValue());
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
/* 372 */     int h = 0, n = size();
/* 373 */     ObjectIterator<Byte2ReferenceMap.Entry<V>> i = Byte2ReferenceMaps.fastIterator(this);
/* 374 */     for (; n-- != 0; h += ((Byte2ReferenceMap.Entry)i.next()).hashCode());
/* 375 */     return h;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean equals(Object o) {
/* 380 */     if (o == this) return true; 
/* 381 */     if (!(o instanceof Map)) return false; 
/* 382 */     Map<?, ?> m = (Map<?, ?>)o;
/* 383 */     if (m.size() != size()) return false; 
/* 384 */     return byte2ReferenceEntrySet().containsAll(m.entrySet());
/*     */   }
/*     */ 
/*     */   
/*     */   public String toString() {
/* 389 */     StringBuilder s = new StringBuilder();
/* 390 */     ObjectIterator<Byte2ReferenceMap.Entry<V>> i = Byte2ReferenceMaps.fastIterator(this);
/* 391 */     int n = size();
/*     */     
/* 393 */     boolean first = true;
/* 394 */     s.append("{");
/* 395 */     while (n-- != 0) {
/* 396 */       if (first) { first = false; }
/* 397 */       else { s.append(", "); }
/* 398 */        Byte2ReferenceMap.Entry<V> e = (Byte2ReferenceMap.Entry<V>)i.next();
/* 399 */       s.append(String.valueOf(e.getByteKey()));
/* 400 */       s.append("=>");
/* 401 */       if (this == e.getValue()) { s.append("(this map)"); continue; }
/* 402 */        s.append(String.valueOf(e.getValue()));
/*     */     } 
/* 404 */     s.append("}");
/* 405 */     return s.toString();
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\i\\unimi\dsi\fastutil\bytes\AbstractByte2ReferenceMap.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */