/*     */ package it.unimi.dsi.fastutil.objects;
/*     */ 
/*     */ import it.unimi.dsi.fastutil.Size64;
/*     */ import it.unimi.dsi.fastutil.booleans.AbstractBooleanCollection;
/*     */ import it.unimi.dsi.fastutil.booleans.BooleanCollection;
/*     */ import it.unimi.dsi.fastutil.booleans.BooleanConsumer;
/*     */ import it.unimi.dsi.fastutil.booleans.BooleanIterator;
/*     */ import it.unimi.dsi.fastutil.booleans.BooleanSpliterator;
/*     */ import it.unimi.dsi.fastutil.booleans.BooleanSpliterators;
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
/*     */ public abstract class AbstractReference2BooleanMap<K>
/*     */   extends AbstractReference2BooleanFunction<K>
/*     */   implements Reference2BooleanMap<K>, Serializable
/*     */ {
/*     */   private static final long serialVersionUID = -4940583368468432370L;
/*     */   
/*     */   public boolean containsKey(Object k) {
/*  63 */     ObjectIterator<Reference2BooleanMap.Entry<K>> i = reference2BooleanEntrySet().iterator();
/*  64 */     while (i.hasNext()) { if (((Reference2BooleanMap.Entry)i.next()).getKey() == k) return true;  }
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
/*     */   public boolean containsValue(boolean v) {
/*  83 */     ObjectIterator<Reference2BooleanMap.Entry<K>> i = reference2BooleanEntrySet().iterator();
/*  84 */     while (i.hasNext()) { if (((Reference2BooleanMap.Entry)i.next()).getBooleanValue() == v) return true;  }
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
/*     */   public static class BasicEntry<K>
/*     */     implements Reference2BooleanMap.Entry<K>
/*     */   {
/*     */     protected K key;
/*     */ 
/*     */     
/*     */     protected boolean value;
/*     */ 
/*     */ 
/*     */     
/*     */     public BasicEntry() {}
/*     */ 
/*     */     
/*     */     public BasicEntry(K key, Boolean value) {
/* 109 */       this.key = key;
/* 110 */       this.value = value.booleanValue();
/*     */     }
/*     */     
/*     */     public BasicEntry(K key, boolean value) {
/* 114 */       this.key = key;
/* 115 */       this.value = value;
/*     */     }
/*     */ 
/*     */     
/*     */     public K getKey() {
/* 120 */       return this.key;
/*     */     }
/*     */ 
/*     */     
/*     */     public boolean getBooleanValue() {
/* 125 */       return this.value;
/*     */     }
/*     */ 
/*     */     
/*     */     public boolean setValue(boolean value) {
/* 130 */       throw new UnsupportedOperationException();
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     public boolean equals(Object o) {
/* 136 */       if (!(o instanceof Map.Entry)) return false; 
/* 137 */       if (o instanceof Reference2BooleanMap.Entry) {
/* 138 */         Reference2BooleanMap.Entry<K> entry = (Reference2BooleanMap.Entry<K>)o;
/* 139 */         return (this.key == entry.getKey() && this.value == entry.getBooleanValue());
/*     */       } 
/* 141 */       Map.Entry<?, ?> e = (Map.Entry<?, ?>)o;
/* 142 */       Object key = e.getKey();
/* 143 */       Object value = e.getValue();
/* 144 */       if (value == null || !(value instanceof Boolean)) return false; 
/* 145 */       return (this.key == key && this.value == ((Boolean)value).booleanValue());
/*     */     }
/*     */ 
/*     */     
/*     */     public int hashCode() {
/* 150 */       return System.identityHashCode(this.key) ^ (this.value ? 1231 : 1237);
/*     */     }
/*     */ 
/*     */     
/*     */     public String toString() {
/* 155 */       return (new StringBuilder()).append(this.key).append("->").append(this.value).toString();
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   public static abstract class BasicEntrySet<K>
/*     */     extends AbstractObjectSet<Reference2BooleanMap.Entry<K>>
/*     */   {
/*     */     protected final Reference2BooleanMap<K> map;
/*     */ 
/*     */     
/*     */     public BasicEntrySet(Reference2BooleanMap<K> map) {
/* 167 */       this.map = map;
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     public boolean contains(Object o) {
/* 173 */       if (!(o instanceof Map.Entry)) return false; 
/* 174 */       if (o instanceof Reference2BooleanMap.Entry) {
/* 175 */         Reference2BooleanMap.Entry<K> entry = (Reference2BooleanMap.Entry<K>)o;
/* 176 */         K k1 = entry.getKey();
/* 177 */         return (this.map.containsKey(k1) && this.map.getBoolean(k1) == entry.getBooleanValue());
/*     */       } 
/* 179 */       Map.Entry<?, ?> e = (Map.Entry<?, ?>)o;
/* 180 */       Object k = e.getKey();
/* 181 */       Object value = e.getValue();
/* 182 */       if (value == null || !(value instanceof Boolean)) return false; 
/* 183 */       return (this.map.containsKey(k) && this.map.getBoolean(k) == ((Boolean)value).booleanValue());
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     public boolean remove(Object o) {
/* 189 */       if (!(o instanceof Map.Entry)) return false; 
/* 190 */       if (o instanceof Reference2BooleanMap.Entry) {
/* 191 */         Reference2BooleanMap.Entry<K> entry = (Reference2BooleanMap.Entry<K>)o;
/* 192 */         return this.map.remove(entry.getKey(), entry.getBooleanValue());
/*     */       } 
/* 194 */       Map.Entry<?, ?> e = (Map.Entry<?, ?>)o;
/* 195 */       Object k = e.getKey();
/* 196 */       Object value = e.getValue();
/* 197 */       if (value == null || !(value instanceof Boolean)) return false; 
/* 198 */       boolean v = ((Boolean)value).booleanValue();
/* 199 */       return this.map.remove(k, v);
/*     */     }
/*     */ 
/*     */     
/*     */     public int size() {
/* 204 */       return this.map.size();
/*     */     }
/*     */ 
/*     */     
/*     */     public ObjectSpliterator<Reference2BooleanMap.Entry<K>> spliterator() {
/* 209 */       return ObjectSpliterators.asSpliterator(iterator(), Size64.sizeOf(this.map), 65);
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
/*     */   public ReferenceSet<K> keySet() {
/* 227 */     return new AbstractReferenceSet<K>()
/*     */       {
/*     */         public boolean contains(Object k) {
/* 230 */           return AbstractReference2BooleanMap.this.containsKey(k);
/*     */         }
/*     */ 
/*     */         
/*     */         public int size() {
/* 235 */           return AbstractReference2BooleanMap.this.size();
/*     */         }
/*     */ 
/*     */         
/*     */         public void clear() {
/* 240 */           AbstractReference2BooleanMap.this.clear();
/*     */         }
/*     */ 
/*     */         
/*     */         public ObjectIterator<K> iterator() {
/* 245 */           return new ObjectIterator<K>() {
/* 246 */               private final ObjectIterator<Reference2BooleanMap.Entry<K>> i = Reference2BooleanMaps.fastIterator(AbstractReference2BooleanMap.this);
/*     */ 
/*     */               
/*     */               public K next() {
/* 250 */                 return ((Reference2BooleanMap.Entry<K>)this.i.next()).getKey();
/*     */               }
/*     */ 
/*     */               
/*     */               public boolean hasNext() {
/* 255 */                 return this.i.hasNext();
/*     */               }
/*     */ 
/*     */               
/*     */               public void remove() {
/* 260 */                 this.i.remove();
/*     */               }
/*     */ 
/*     */               
/*     */               public void forEachRemaining(Consumer<? super K> action) {
/* 265 */                 this.i.forEachRemaining(entry -> action.accept(entry.getKey()));
/*     */               }
/*     */             };
/*     */         }
/*     */ 
/*     */         
/*     */         public ObjectSpliterator<K> spliterator() {
/* 272 */           return ObjectSpliterators.asSpliterator(iterator(), Size64.sizeOf(AbstractReference2BooleanMap.this), 65);
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
/* 291 */     return (BooleanCollection)new AbstractBooleanCollection()
/*     */       {
/*     */         public boolean contains(boolean k) {
/* 294 */           return AbstractReference2BooleanMap.this.containsValue(k);
/*     */         }
/*     */ 
/*     */         
/*     */         public int size() {
/* 299 */           return AbstractReference2BooleanMap.this.size();
/*     */         }
/*     */ 
/*     */         
/*     */         public void clear() {
/* 304 */           AbstractReference2BooleanMap.this.clear();
/*     */         }
/*     */ 
/*     */         
/*     */         public BooleanIterator iterator() {
/* 309 */           return new BooleanIterator() {
/* 310 */               private final ObjectIterator<Reference2BooleanMap.Entry<K>> i = Reference2BooleanMaps.fastIterator(AbstractReference2BooleanMap.this);
/*     */ 
/*     */               
/*     */               public boolean nextBoolean() {
/* 314 */                 return ((Reference2BooleanMap.Entry)this.i.next()).getBooleanValue();
/*     */               }
/*     */ 
/*     */               
/*     */               public boolean hasNext() {
/* 319 */                 return this.i.hasNext();
/*     */               }
/*     */ 
/*     */               
/*     */               public void remove() {
/* 324 */                 this.i.remove();
/*     */               }
/*     */ 
/*     */               
/*     */               public void forEachRemaining(BooleanConsumer action) {
/* 329 */                 this.i.forEachRemaining(entry -> action.accept(entry.getBooleanValue()));
/*     */               }
/*     */             };
/*     */         }
/*     */ 
/*     */         
/*     */         public BooleanSpliterator spliterator() {
/* 336 */           return BooleanSpliterators.asSpliterator(iterator(), Size64.sizeOf(AbstractReference2BooleanMap.this), 320);
/*     */         }
/*     */       };
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void putAll(Map<? extends K, ? extends Boolean> m) {
/* 345 */     if (m instanceof Reference2BooleanMap) {
/* 346 */       ObjectIterator<Reference2BooleanMap.Entry<K>> i = Reference2BooleanMaps.fastIterator((Reference2BooleanMap)m);
/* 347 */       while (i.hasNext()) {
/* 348 */         Reference2BooleanMap.Entry<? extends K> e = i.next();
/* 349 */         put(e.getKey(), e.getBooleanValue());
/*     */       } 
/*     */     } else {
/* 352 */       int n = m.size();
/* 353 */       Iterator<? extends Map.Entry<? extends K, ? extends Boolean>> i = m.entrySet().iterator();
/*     */       
/* 355 */       while (n-- != 0) {
/* 356 */         Map.Entry<? extends K, ? extends Boolean> e = i.next();
/* 357 */         put(e.getKey(), e.getValue());
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
/* 371 */     int h = 0, n = size();
/* 372 */     ObjectIterator<Reference2BooleanMap.Entry<K>> i = Reference2BooleanMaps.fastIterator(this);
/* 373 */     for (; n-- != 0; h += ((Reference2BooleanMap.Entry)i.next()).hashCode());
/* 374 */     return h;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean equals(Object o) {
/* 379 */     if (o == this) return true; 
/* 380 */     if (!(o instanceof Map)) return false; 
/* 381 */     Map<?, ?> m = (Map<?, ?>)o;
/* 382 */     if (m.size() != size()) return false; 
/* 383 */     return reference2BooleanEntrySet().containsAll(m.entrySet());
/*     */   }
/*     */ 
/*     */   
/*     */   public String toString() {
/* 388 */     StringBuilder s = new StringBuilder();
/* 389 */     ObjectIterator<Reference2BooleanMap.Entry<K>> i = Reference2BooleanMaps.fastIterator(this);
/* 390 */     int n = size();
/*     */     
/* 392 */     boolean first = true;
/* 393 */     s.append("{");
/* 394 */     while (n-- != 0) {
/* 395 */       if (first) { first = false; }
/* 396 */       else { s.append(", "); }
/* 397 */        Reference2BooleanMap.Entry<K> e = i.next();
/* 398 */       if (this == e.getKey()) { s.append("(this map)"); }
/* 399 */       else { s.append(String.valueOf(e.getKey())); }
/* 400 */        s.append("=>");
/* 401 */       s.append(String.valueOf(e.getBooleanValue()));
/*     */     } 
/* 403 */     s.append("}");
/* 404 */     return s.toString();
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\i\\unimi\dsi\fastutil\objects\AbstractReference2BooleanMap.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */