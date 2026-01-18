/*     */ package it.unimi.dsi.fastutil.objects;
/*     */ 
/*     */ import java.io.IOException;
/*     */ import java.io.ObjectInputStream;
/*     */ import java.io.ObjectOutputStream;
/*     */ import java.io.Serializable;
/*     */ import java.util.Collection;
/*     */ import java.util.Iterator;
/*     */ import java.util.Map;
/*     */ import java.util.NoSuchElementException;
/*     */ import java.util.Objects;
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
/*     */ public class Object2ObjectArrayMap<K, V>
/*     */   extends AbstractObject2ObjectMap<K, V>
/*     */   implements Serializable, Cloneable
/*     */ {
/*     */   private static final long serialVersionUID = 1L;
/*     */   protected transient Object[] key;
/*     */   protected transient Object[] value;
/*     */   protected int size;
/*     */   protected transient Object2ObjectMap.FastEntrySet<K, V> entries;
/*     */   protected transient ObjectSet<K> keys;
/*     */   protected transient ObjectCollection<V> values;
/*     */   
/*     */   public Object2ObjectArrayMap(Object[] key, Object[] value) {
/*  56 */     this.key = key;
/*  57 */     this.value = value;
/*  58 */     this.size = key.length;
/*  59 */     if (key.length != value.length) throw new IllegalArgumentException("Keys and values have different lengths (" + key.length + ", " + value.length + ")");
/*     */   
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public Object2ObjectArrayMap() {
/*  66 */     this.key = ObjectArrays.EMPTY_ARRAY;
/*  67 */     this.value = ObjectArrays.EMPTY_ARRAY;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Object2ObjectArrayMap(int capacity) {
/*  76 */     this.key = new Object[capacity];
/*  77 */     this.value = new Object[capacity];
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Object2ObjectArrayMap(Object2ObjectMap<K, V> m) {
/*  86 */     this(m.size());
/*  87 */     int i = 0;
/*  88 */     for (ObjectIterator<Object2ObjectMap.Entry<K, V>> objectIterator = m.object2ObjectEntrySet().iterator(); objectIterator.hasNext(); ) { Object2ObjectMap.Entry<K, V> e = objectIterator.next();
/*  89 */       this.key[i] = e.getKey();
/*  90 */       this.value[i] = e.getValue();
/*  91 */       i++; }
/*     */     
/*  93 */     this.size = i;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Object2ObjectArrayMap(Map<? extends K, ? extends V> m) {
/* 102 */     this(m.size());
/* 103 */     int i = 0;
/* 104 */     for (Map.Entry<? extends K, ? extends V> e : m.entrySet()) {
/* 105 */       this.key[i] = e.getKey();
/* 106 */       this.value[i] = e.getValue();
/* 107 */       i++;
/*     */     } 
/* 109 */     this.size = i;
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
/*     */   public Object2ObjectArrayMap(Object[] key, Object[] value, int size) {
/* 125 */     this.key = key;
/* 126 */     this.value = value;
/* 127 */     this.size = size;
/* 128 */     if (key.length != value.length) throw new IllegalArgumentException("Keys and values have different lengths (" + key.length + ", " + value.length + ")"); 
/* 129 */     if (size > key.length) throw new IllegalArgumentException("The provided size (" + size + ") is larger than or equal to the backing-arrays size (" + key.length + ")"); 
/*     */   }
/*     */   
/*     */   private final class EntrySet
/*     */     extends AbstractObjectSet<Object2ObjectMap.Entry<K, V>> implements Object2ObjectMap.FastEntrySet<K, V> {
/*     */     private EntrySet() {}
/*     */     
/*     */     public ObjectIterator<Object2ObjectMap.Entry<K, V>> iterator() {
/* 137 */       return (ObjectIterator)new ObjectIterator<Object2ObjectMap.Entry<Object2ObjectMap.Entry<K, V>, V>>() {
/* 138 */           int curr = -1; int next = 0;
/*     */ 
/*     */           
/*     */           public boolean hasNext() {
/* 142 */             return (this.next < Object2ObjectArrayMap.this.size);
/*     */           }
/*     */ 
/*     */ 
/*     */           
/*     */           public Object2ObjectMap.Entry<K, V> next() {
/* 148 */             if (!hasNext()) throw new NoSuchElementException(); 
/* 149 */             return new AbstractObject2ObjectMap.BasicEntry<>((K)Object2ObjectArrayMap.this.key[this.curr = this.next], (V)Object2ObjectArrayMap.this.value[this.next++]);
/*     */           }
/*     */ 
/*     */           
/*     */           public void remove() {
/* 154 */             if (this.curr == -1) throw new IllegalStateException(); 
/* 155 */             this.curr = -1;
/* 156 */             int tail = Object2ObjectArrayMap.this.size-- - this.next--;
/* 157 */             System.arraycopy(Object2ObjectArrayMap.this.key, this.next + 1, Object2ObjectArrayMap.this.key, this.next, tail);
/* 158 */             System.arraycopy(Object2ObjectArrayMap.this.value, this.next + 1, Object2ObjectArrayMap.this.value, this.next, tail);
/* 159 */             Object2ObjectArrayMap.this.key[Object2ObjectArrayMap.this.size] = null;
/* 160 */             Object2ObjectArrayMap.this.value[Object2ObjectArrayMap.this.size] = null;
/*     */           }
/*     */ 
/*     */ 
/*     */ 
/*     */           
/*     */           public void forEachRemaining(Consumer<? super Object2ObjectMap.Entry<K, V>> action) {
/* 167 */             int max = Object2ObjectArrayMap.this.size;
/* 168 */             while (this.next < max) {
/* 169 */               action.accept(new AbstractObject2ObjectMap.BasicEntry<>((K)Object2ObjectArrayMap.this.key[this.curr = this.next], (V)Object2ObjectArrayMap.this.value[this.next++]));
/*     */             }
/*     */           }
/*     */         };
/*     */     }
/*     */ 
/*     */     
/*     */     public ObjectIterator<Object2ObjectMap.Entry<K, V>> fastIterator() {
/* 177 */       return (ObjectIterator)new ObjectIterator<Object2ObjectMap.Entry<Object2ObjectMap.Entry<K, V>, V>>() {
/* 178 */           int next = 0; int curr = -1;
/* 179 */           final AbstractObject2ObjectMap.BasicEntry<K, V> entry = new AbstractObject2ObjectMap.BasicEntry<>();
/*     */ 
/*     */           
/*     */           public boolean hasNext() {
/* 183 */             return (this.next < Object2ObjectArrayMap.this.size);
/*     */           }
/*     */ 
/*     */ 
/*     */           
/*     */           public Object2ObjectMap.Entry<K, V> next() {
/* 189 */             if (!hasNext()) throw new NoSuchElementException(); 
/* 190 */             this.entry.key = (K)Object2ObjectArrayMap.this.key[this.curr = this.next];
/* 191 */             this.entry.value = (V)Object2ObjectArrayMap.this.value[this.next++];
/* 192 */             return this.entry;
/*     */           }
/*     */ 
/*     */           
/*     */           public void remove() {
/* 197 */             if (this.curr == -1) throw new IllegalStateException(); 
/* 198 */             this.curr = -1;
/* 199 */             int tail = Object2ObjectArrayMap.this.size-- - this.next--;
/* 200 */             System.arraycopy(Object2ObjectArrayMap.this.key, this.next + 1, Object2ObjectArrayMap.this.key, this.next, tail);
/* 201 */             System.arraycopy(Object2ObjectArrayMap.this.value, this.next + 1, Object2ObjectArrayMap.this.value, this.next, tail);
/* 202 */             Object2ObjectArrayMap.this.key[Object2ObjectArrayMap.this.size] = null;
/* 203 */             Object2ObjectArrayMap.this.value[Object2ObjectArrayMap.this.size] = null;
/*     */           }
/*     */ 
/*     */ 
/*     */ 
/*     */           
/*     */           public void forEachRemaining(Consumer<? super Object2ObjectMap.Entry<K, V>> action) {
/* 210 */             int max = Object2ObjectArrayMap.this.size;
/* 211 */             while (this.next < max) {
/* 212 */               this.entry.key = (K)Object2ObjectArrayMap.this.key[this.curr = this.next];
/* 213 */               this.entry.value = (V)Object2ObjectArrayMap.this.value[this.next++];
/* 214 */               action.accept(this.entry);
/*     */             } 
/*     */           }
/*     */         };
/*     */     }
/*     */     
/*     */     final class EntrySetSpliterator
/*     */       extends ObjectSpliterators.EarlyBindingSizeIndexBasedSpliterator<Object2ObjectMap.Entry<K, V>>
/*     */       implements ObjectSpliterator<Object2ObjectMap.Entry<K, V>> {
/*     */       EntrySetSpliterator(int pos, int maxPos) {
/* 224 */         super(pos, maxPos);
/*     */       }
/*     */ 
/*     */       
/*     */       public int characteristics() {
/* 229 */         return 16465;
/*     */       }
/*     */ 
/*     */ 
/*     */       
/*     */       protected final Object2ObjectMap.Entry<K, V> get(int location) {
/* 235 */         return new AbstractObject2ObjectMap.BasicEntry<>((K)Object2ObjectArrayMap.this.key[location], (V)Object2ObjectArrayMap.this.value[location]);
/*     */       }
/*     */ 
/*     */       
/*     */       protected final EntrySetSpliterator makeForSplit(int pos, int maxPos) {
/* 240 */         return new EntrySetSpliterator(pos, maxPos);
/*     */       }
/*     */     }
/*     */ 
/*     */     
/*     */     public ObjectSpliterator<Object2ObjectMap.Entry<K, V>> spliterator() {
/* 246 */       return new EntrySetSpliterator(0, Object2ObjectArrayMap.this.size);
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public void forEach(Consumer<? super Object2ObjectMap.Entry<K, V>> action) {
/* 254 */       for (int i = 0, max = Object2ObjectArrayMap.this.size; i < max; i++) {
/* 255 */         action.accept(new AbstractObject2ObjectMap.BasicEntry<>((K)Object2ObjectArrayMap.this.key[i], (V)Object2ObjectArrayMap.this.value[i]));
/*     */       }
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public void fastForEach(Consumer<? super Object2ObjectMap.Entry<K, V>> action) {
/* 263 */       AbstractObject2ObjectMap.BasicEntry<K, V> entry = new AbstractObject2ObjectMap.BasicEntry<>();
/*     */       
/* 265 */       for (int i = 0, max = Object2ObjectArrayMap.this.size; i < max; i++) {
/* 266 */         entry.key = (K)Object2ObjectArrayMap.this.key[i];
/* 267 */         entry.value = (V)Object2ObjectArrayMap.this.value[i];
/* 268 */         action.accept(entry);
/*     */       } 
/*     */     }
/*     */ 
/*     */     
/*     */     public int size() {
/* 274 */       return Object2ObjectArrayMap.this.size;
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     public boolean contains(Object o) {
/* 280 */       if (!(o instanceof Map.Entry)) return false; 
/* 281 */       Map.Entry<?, ?> e = (Map.Entry<?, ?>)o;
/* 282 */       K k = (K)e.getKey();
/* 283 */       return (Object2ObjectArrayMap.this.containsKey(k) && Objects.equals(Object2ObjectArrayMap.this.get(k), e.getValue()));
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     public boolean remove(Object o) {
/* 289 */       if (!(o instanceof Map.Entry)) return false; 
/* 290 */       Map.Entry<?, ?> e = (Map.Entry<?, ?>)o;
/* 291 */       K k = (K)e.getKey();
/* 292 */       V v = (V)e.getValue();
/* 293 */       int oldPos = Object2ObjectArrayMap.this.findKey(k);
/* 294 */       if (oldPos == -1 || !Objects.equals(v, Object2ObjectArrayMap.this.value[oldPos])) return false; 
/* 295 */       int tail = Object2ObjectArrayMap.this.size - oldPos - 1;
/* 296 */       System.arraycopy(Object2ObjectArrayMap.this.key, oldPos + 1, Object2ObjectArrayMap.this.key, oldPos, tail);
/* 297 */       System.arraycopy(Object2ObjectArrayMap.this.value, oldPos + 1, Object2ObjectArrayMap.this.value, oldPos, tail);
/* 298 */       Object2ObjectArrayMap.this.size--;
/* 299 */       Object2ObjectArrayMap.this.key[Object2ObjectArrayMap.this.size] = null;
/* 300 */       Object2ObjectArrayMap.this.value[Object2ObjectArrayMap.this.size] = null;
/* 301 */       return true;
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   public Object2ObjectMap.FastEntrySet<K, V> object2ObjectEntrySet() {
/* 307 */     if (this.entries == null) this.entries = new EntrySet(); 
/* 308 */     return this.entries;
/*     */   }
/*     */   
/*     */   private int findKey(Object k) {
/* 312 */     Object[] key = this.key;
/* 313 */     for (int i = this.size; i-- != 0;) { if (Objects.equals(key[i], k)) return i;  }
/* 314 */      return -1;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public V get(Object k) {
/* 320 */     Object[] key = this.key;
/* 321 */     for (int i = this.size; i-- != 0;) { if (Objects.equals(key[i], k)) return (V)this.value[i];  }
/* 322 */      return this.defRetValue;
/*     */   }
/*     */ 
/*     */   
/*     */   public int size() {
/* 327 */     return this.size;
/*     */   }
/*     */ 
/*     */   
/*     */   public void clear() {
/* 332 */     for (int i = this.size; i-- != 0; ) {
/* 333 */       this.key[i] = null;
/* 334 */       this.value[i] = null;
/*     */     } 
/* 336 */     this.size = 0;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean containsKey(Object k) {
/* 341 */     return (findKey(k) != -1);
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean containsValue(Object v) {
/* 346 */     for (int i = this.size; i-- != 0;) { if (Objects.equals(this.value[i], v)) return true;  }
/* 347 */      return false;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isEmpty() {
/* 352 */     return (this.size == 0);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public V put(K k, V v) {
/* 358 */     int oldKey = findKey(k);
/* 359 */     if (oldKey != -1) {
/* 360 */       V oldValue = (V)this.value[oldKey];
/* 361 */       this.value[oldKey] = v;
/* 362 */       return oldValue;
/*     */     } 
/* 364 */     if (this.size == this.key.length) {
/* 365 */       Object[] newKey = new Object[(this.size == 0) ? 2 : (this.size * 2)];
/* 366 */       Object[] newValue = new Object[(this.size == 0) ? 2 : (this.size * 2)];
/* 367 */       for (int i = this.size; i-- != 0; ) {
/* 368 */         newKey[i] = this.key[i];
/* 369 */         newValue[i] = this.value[i];
/*     */       } 
/* 371 */       this.key = newKey;
/* 372 */       this.value = newValue;
/*     */     } 
/* 374 */     this.key[this.size] = k;
/* 375 */     this.value[this.size] = v;
/* 376 */     this.size++;
/* 377 */     return this.defRetValue;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public V remove(Object k) {
/* 383 */     int oldPos = findKey(k);
/* 384 */     if (oldPos == -1) return this.defRetValue; 
/* 385 */     V oldValue = (V)this.value[oldPos];
/* 386 */     int tail = this.size - oldPos - 1;
/* 387 */     System.arraycopy(this.key, oldPos + 1, this.key, oldPos, tail);
/* 388 */     System.arraycopy(this.value, oldPos + 1, this.value, oldPos, tail);
/* 389 */     this.size--;
/* 390 */     this.key[this.size] = null;
/* 391 */     this.value[this.size] = null;
/* 392 */     return oldValue;
/*     */   }
/*     */   
/*     */   private final class KeySet extends AbstractObjectSet<K> { private KeySet() {}
/*     */     
/*     */     public boolean contains(Object k) {
/* 398 */       return (Object2ObjectArrayMap.this.findKey(k) != -1);
/*     */     }
/*     */ 
/*     */     
/*     */     public boolean remove(Object k) {
/* 403 */       int oldPos = Object2ObjectArrayMap.this.findKey(k);
/* 404 */       if (oldPos == -1) return false; 
/* 405 */       int tail = Object2ObjectArrayMap.this.size - oldPos - 1;
/* 406 */       System.arraycopy(Object2ObjectArrayMap.this.key, oldPos + 1, Object2ObjectArrayMap.this.key, oldPos, tail);
/* 407 */       System.arraycopy(Object2ObjectArrayMap.this.value, oldPos + 1, Object2ObjectArrayMap.this.value, oldPos, tail);
/* 408 */       Object2ObjectArrayMap.this.size--;
/* 409 */       Object2ObjectArrayMap.this.key[Object2ObjectArrayMap.this.size] = null;
/* 410 */       Object2ObjectArrayMap.this.value[Object2ObjectArrayMap.this.size] = null;
/* 411 */       return true;
/*     */     }
/*     */ 
/*     */     
/*     */     public ObjectIterator<K> iterator() {
/* 416 */       return new ObjectIterator<K>() {
/* 417 */           int pos = 0;
/*     */ 
/*     */           
/*     */           public boolean hasNext() {
/* 421 */             return (this.pos < Object2ObjectArrayMap.this.size);
/*     */           }
/*     */ 
/*     */ 
/*     */           
/*     */           public K next() {
/* 427 */             if (!hasNext()) throw new NoSuchElementException(); 
/* 428 */             return (K)Object2ObjectArrayMap.this.key[this.pos++];
/*     */           }
/*     */ 
/*     */           
/*     */           public void remove() {
/* 433 */             if (this.pos == 0) throw new IllegalStateException(); 
/* 434 */             int tail = Object2ObjectArrayMap.this.size - this.pos;
/* 435 */             System.arraycopy(Object2ObjectArrayMap.this.key, this.pos, Object2ObjectArrayMap.this.key, this.pos - 1, tail);
/* 436 */             System.arraycopy(Object2ObjectArrayMap.this.value, this.pos, Object2ObjectArrayMap.this.value, this.pos - 1, tail);
/* 437 */             Object2ObjectArrayMap.this.size--;
/* 438 */             this.pos--;
/* 439 */             Object2ObjectArrayMap.this.key[Object2ObjectArrayMap.this.size] = null;
/* 440 */             Object2ObjectArrayMap.this.value[Object2ObjectArrayMap.this.size] = null;
/*     */           }
/*     */ 
/*     */ 
/*     */ 
/*     */           
/*     */           public void forEachRemaining(Consumer<? super K> action) {
/* 447 */             int max = Object2ObjectArrayMap.this.size;
/* 448 */             while (this.pos < max)
/* 449 */               action.accept((K)Object2ObjectArrayMap.this.key[this.pos++]); 
/*     */           }
/*     */         };
/*     */     }
/*     */     
/*     */     final class KeySetSpliterator
/*     */       extends ObjectSpliterators.EarlyBindingSizeIndexBasedSpliterator<K>
/*     */       implements ObjectSpliterator<K> {
/*     */       KeySetSpliterator(int pos, int maxPos) {
/* 458 */         super(pos, maxPos);
/*     */       }
/*     */ 
/*     */       
/*     */       public int characteristics() {
/* 463 */         return 16465;
/*     */       }
/*     */ 
/*     */ 
/*     */       
/*     */       protected final K get(int location) {
/* 469 */         return (K)Object2ObjectArrayMap.this.key[location];
/*     */       }
/*     */ 
/*     */       
/*     */       protected final KeySetSpliterator makeForSplit(int pos, int maxPos) {
/* 474 */         return new KeySetSpliterator(pos, maxPos);
/*     */       }
/*     */ 
/*     */ 
/*     */ 
/*     */       
/*     */       public void forEachRemaining(Consumer<? super K> action) {
/* 481 */         int max = Object2ObjectArrayMap.this.size;
/* 482 */         while (this.pos < max) {
/* 483 */           action.accept((K)Object2ObjectArrayMap.this.key[this.pos++]);
/*     */         }
/*     */       }
/*     */     }
/*     */ 
/*     */     
/*     */     public ObjectSpliterator<K> spliterator() {
/* 490 */       return new KeySetSpliterator(0, Object2ObjectArrayMap.this.size);
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public void forEach(Consumer<? super K> action) {
/* 497 */       for (int i = 0, max = Object2ObjectArrayMap.this.size; i < max; i++) {
/* 498 */         action.accept((K)Object2ObjectArrayMap.this.key[i]);
/*     */       }
/*     */     }
/*     */ 
/*     */     
/*     */     public int size() {
/* 504 */       return Object2ObjectArrayMap.this.size;
/*     */     }
/*     */ 
/*     */     
/*     */     public void clear() {
/* 509 */       Object2ObjectArrayMap.this.clear();
/*     */     } }
/*     */ 
/*     */ 
/*     */   
/*     */   public ObjectSet<K> keySet() {
/* 515 */     if (this.keys == null) this.keys = new KeySet(); 
/* 516 */     return this.keys;
/*     */   }
/*     */   
/*     */   private final class ValuesCollection extends AbstractObjectCollection<V> { private ValuesCollection() {}
/*     */     
/*     */     public boolean contains(Object v) {
/* 522 */       return Object2ObjectArrayMap.this.containsValue(v);
/*     */     }
/*     */ 
/*     */     
/*     */     public ObjectIterator<V> iterator() {
/* 527 */       return new ObjectIterator<V>() {
/* 528 */           int pos = 0;
/*     */ 
/*     */           
/*     */           public boolean hasNext() {
/* 532 */             return (this.pos < Object2ObjectArrayMap.this.size);
/*     */           }
/*     */ 
/*     */ 
/*     */           
/*     */           public V next() {
/* 538 */             if (!hasNext()) throw new NoSuchElementException(); 
/* 539 */             return (V)Object2ObjectArrayMap.this.value[this.pos++];
/*     */           }
/*     */ 
/*     */           
/*     */           public void remove() {
/* 544 */             if (this.pos == 0) throw new IllegalStateException(); 
/* 545 */             int tail = Object2ObjectArrayMap.this.size - this.pos;
/* 546 */             System.arraycopy(Object2ObjectArrayMap.this.key, this.pos, Object2ObjectArrayMap.this.key, this.pos - 1, tail);
/* 547 */             System.arraycopy(Object2ObjectArrayMap.this.value, this.pos, Object2ObjectArrayMap.this.value, this.pos - 1, tail);
/* 548 */             Object2ObjectArrayMap.this.size--;
/* 549 */             this.pos--;
/* 550 */             Object2ObjectArrayMap.this.key[Object2ObjectArrayMap.this.size] = null;
/* 551 */             Object2ObjectArrayMap.this.value[Object2ObjectArrayMap.this.size] = null;
/*     */           }
/*     */ 
/*     */ 
/*     */ 
/*     */           
/*     */           public void forEachRemaining(Consumer<? super V> action) {
/* 558 */             int max = Object2ObjectArrayMap.this.size;
/* 559 */             while (this.pos < max)
/* 560 */               action.accept((V)Object2ObjectArrayMap.this.value[this.pos++]); 
/*     */           }
/*     */         };
/*     */     }
/*     */     
/*     */     final class ValuesSpliterator
/*     */       extends ObjectSpliterators.EarlyBindingSizeIndexBasedSpliterator<V>
/*     */       implements ObjectSpliterator<V> {
/*     */       ValuesSpliterator(int pos, int maxPos) {
/* 569 */         super(pos, maxPos);
/*     */       }
/*     */ 
/*     */       
/*     */       public int characteristics() {
/* 574 */         return 16464;
/*     */       }
/*     */ 
/*     */ 
/*     */       
/*     */       protected final V get(int location) {
/* 580 */         return (V)Object2ObjectArrayMap.this.value[location];
/*     */       }
/*     */ 
/*     */       
/*     */       protected final ValuesSpliterator makeForSplit(int pos, int maxPos) {
/* 585 */         return new ValuesSpliterator(pos, maxPos);
/*     */       }
/*     */ 
/*     */ 
/*     */ 
/*     */       
/*     */       public void forEachRemaining(Consumer<? super V> action) {
/* 592 */         int max = Object2ObjectArrayMap.this.size;
/* 593 */         while (this.pos < max) {
/* 594 */           action.accept((V)Object2ObjectArrayMap.this.value[this.pos++]);
/*     */         }
/*     */       }
/*     */     }
/*     */ 
/*     */     
/*     */     public ObjectSpliterator<V> spliterator() {
/* 601 */       return new ValuesSpliterator(0, Object2ObjectArrayMap.this.size);
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public void forEach(Consumer<? super V> action) {
/* 608 */       for (int i = 0, max = Object2ObjectArrayMap.this.size; i < max; i++) {
/* 609 */         action.accept((V)Object2ObjectArrayMap.this.value[i]);
/*     */       }
/*     */     }
/*     */ 
/*     */     
/*     */     public int size() {
/* 615 */       return Object2ObjectArrayMap.this.size;
/*     */     }
/*     */ 
/*     */     
/*     */     public void clear() {
/* 620 */       Object2ObjectArrayMap.this.clear();
/*     */     } }
/*     */ 
/*     */ 
/*     */   
/*     */   public ObjectCollection<V> values() {
/* 626 */     if (this.values == null) this.values = new ValuesCollection(); 
/* 627 */     return this.values;
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
/*     */   public Object2ObjectArrayMap<K, V> clone() {
/*     */     Object2ObjectArrayMap<K, V> c;
/*     */     try {
/* 644 */       c = (Object2ObjectArrayMap<K, V>)super.clone();
/* 645 */     } catch (CloneNotSupportedException cantHappen) {
/* 646 */       throw new InternalError();
/*     */     } 
/* 648 */     c.key = (Object[])this.key.clone();
/* 649 */     c.value = (Object[])this.value.clone();
/* 650 */     c.entries = null;
/* 651 */     c.keys = null;
/* 652 */     c.values = null;
/* 653 */     return c;
/*     */   }
/*     */   
/*     */   private void writeObject(ObjectOutputStream s) throws IOException {
/* 657 */     s.defaultWriteObject();
/* 658 */     for (int i = 0, max = this.size; i < max; i++) {
/* 659 */       s.writeObject(this.key[i]);
/* 660 */       s.writeObject(this.value[i]);
/*     */     } 
/*     */   }
/*     */   
/*     */   private void readObject(ObjectInputStream s) throws IOException, ClassNotFoundException {
/* 665 */     s.defaultReadObject();
/* 666 */     this.key = new Object[this.size];
/* 667 */     this.value = new Object[this.size];
/* 668 */     for (int i = 0; i < this.size; i++) {
/* 669 */       this.key[i] = s.readObject();
/* 670 */       this.value[i] = s.readObject();
/*     */     } 
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\i\\unimi\dsi\fastutil\objects\Object2ObjectArrayMap.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */