/*     */ package it.unimi.dsi.fastutil.shorts;
/*     */ 
/*     */ import it.unimi.dsi.fastutil.objects.AbstractObjectSet;
/*     */ import it.unimi.dsi.fastutil.objects.AbstractReferenceCollection;
/*     */ import it.unimi.dsi.fastutil.objects.ObjectArrays;
/*     */ import it.unimi.dsi.fastutil.objects.ObjectIterator;
/*     */ import it.unimi.dsi.fastutil.objects.ObjectSet;
/*     */ import it.unimi.dsi.fastutil.objects.ObjectSpliterator;
/*     */ import it.unimi.dsi.fastutil.objects.ObjectSpliterators;
/*     */ import it.unimi.dsi.fastutil.objects.ReferenceCollection;
/*     */ import java.io.IOException;
/*     */ import java.io.ObjectInputStream;
/*     */ import java.io.ObjectOutputStream;
/*     */ import java.io.Serializable;
/*     */ import java.util.Collection;
/*     */ import java.util.Iterator;
/*     */ import java.util.Map;
/*     */ import java.util.NoSuchElementException;
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
/*     */ public class Short2ReferenceArrayMap<V>
/*     */   extends AbstractShort2ReferenceMap<V>
/*     */   implements Serializable, Cloneable
/*     */ {
/*     */   private static final long serialVersionUID = 1L;
/*     */   protected transient short[] key;
/*     */   protected transient Object[] value;
/*     */   protected int size;
/*     */   protected transient Short2ReferenceMap.FastEntrySet<V> entries;
/*     */   protected transient ShortSet keys;
/*     */   protected transient ReferenceCollection<V> values;
/*     */   
/*     */   public Short2ReferenceArrayMap(short[] key, Object[] value) {
/*  63 */     this.key = key;
/*  64 */     this.value = value;
/*  65 */     this.size = key.length;
/*  66 */     if (key.length != value.length) throw new IllegalArgumentException("Keys and values have different lengths (" + key.length + ", " + value.length + ")");
/*     */   
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public Short2ReferenceArrayMap() {
/*  73 */     this.key = ShortArrays.EMPTY_ARRAY;
/*  74 */     this.value = ObjectArrays.EMPTY_ARRAY;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Short2ReferenceArrayMap(int capacity) {
/*  83 */     this.key = new short[capacity];
/*  84 */     this.value = new Object[capacity];
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Short2ReferenceArrayMap(Short2ReferenceMap<V> m) {
/*  93 */     this(m.size());
/*  94 */     int i = 0;
/*  95 */     for (ObjectIterator<Short2ReferenceMap.Entry<V>> objectIterator = m.short2ReferenceEntrySet().iterator(); objectIterator.hasNext(); ) { Short2ReferenceMap.Entry<V> e = objectIterator.next();
/*  96 */       this.key[i] = e.getShortKey();
/*  97 */       this.value[i] = e.getValue();
/*  98 */       i++; }
/*     */     
/* 100 */     this.size = i;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Short2ReferenceArrayMap(Map<? extends Short, ? extends V> m) {
/* 109 */     this(m.size());
/* 110 */     int i = 0;
/* 111 */     for (Map.Entry<? extends Short, ? extends V> e : m.entrySet()) {
/* 112 */       this.key[i] = ((Short)e.getKey()).shortValue();
/* 113 */       this.value[i] = e.getValue();
/* 114 */       i++;
/*     */     } 
/* 116 */     this.size = i;
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
/*     */   public Short2ReferenceArrayMap(short[] key, Object[] value, int size) {
/* 132 */     this.key = key;
/* 133 */     this.value = value;
/* 134 */     this.size = size;
/* 135 */     if (key.length != value.length) throw new IllegalArgumentException("Keys and values have different lengths (" + key.length + ", " + value.length + ")"); 
/* 136 */     if (size > key.length) throw new IllegalArgumentException("The provided size (" + size + ") is larger than or equal to the backing-arrays size (" + key.length + ")"); 
/*     */   }
/*     */   
/*     */   private final class EntrySet
/*     */     extends AbstractObjectSet<Short2ReferenceMap.Entry<V>> implements Short2ReferenceMap.FastEntrySet<V> {
/*     */     private EntrySet() {}
/*     */     
/*     */     public ObjectIterator<Short2ReferenceMap.Entry<V>> iterator() {
/* 144 */       return new ObjectIterator<Short2ReferenceMap.Entry<V>>() {
/* 145 */           int curr = -1; int next = 0;
/*     */ 
/*     */           
/*     */           public boolean hasNext() {
/* 149 */             return (this.next < Short2ReferenceArrayMap.this.size);
/*     */           }
/*     */ 
/*     */ 
/*     */           
/*     */           public Short2ReferenceMap.Entry<V> next() {
/* 155 */             if (!hasNext()) throw new NoSuchElementException(); 
/* 156 */             return new AbstractShort2ReferenceMap.BasicEntry<>(Short2ReferenceArrayMap.this.key[this.curr = this.next], (V)Short2ReferenceArrayMap.this.value[this.next++]);
/*     */           }
/*     */ 
/*     */           
/*     */           public void remove() {
/* 161 */             if (this.curr == -1) throw new IllegalStateException(); 
/* 162 */             this.curr = -1;
/* 163 */             int tail = Short2ReferenceArrayMap.this.size-- - this.next--;
/* 164 */             System.arraycopy(Short2ReferenceArrayMap.this.key, this.next + 1, Short2ReferenceArrayMap.this.key, this.next, tail);
/* 165 */             System.arraycopy(Short2ReferenceArrayMap.this.value, this.next + 1, Short2ReferenceArrayMap.this.value, this.next, tail);
/* 166 */             Short2ReferenceArrayMap.this.value[Short2ReferenceArrayMap.this.size] = null;
/*     */           }
/*     */ 
/*     */ 
/*     */ 
/*     */           
/*     */           public void forEachRemaining(Consumer<? super Short2ReferenceMap.Entry<V>> action) {
/* 173 */             int max = Short2ReferenceArrayMap.this.size;
/* 174 */             while (this.next < max) {
/* 175 */               action.accept(new AbstractShort2ReferenceMap.BasicEntry<>(Short2ReferenceArrayMap.this.key[this.curr = this.next], (V)Short2ReferenceArrayMap.this.value[this.next++]));
/*     */             }
/*     */           }
/*     */         };
/*     */     }
/*     */ 
/*     */     
/*     */     public ObjectIterator<Short2ReferenceMap.Entry<V>> fastIterator() {
/* 183 */       return new ObjectIterator<Short2ReferenceMap.Entry<V>>() {
/* 184 */           int next = 0; int curr = -1;
/* 185 */           final AbstractShort2ReferenceMap.BasicEntry<V> entry = new AbstractShort2ReferenceMap.BasicEntry<>();
/*     */ 
/*     */           
/*     */           public boolean hasNext() {
/* 189 */             return (this.next < Short2ReferenceArrayMap.this.size);
/*     */           }
/*     */ 
/*     */ 
/*     */           
/*     */           public Short2ReferenceMap.Entry<V> next() {
/* 195 */             if (!hasNext()) throw new NoSuchElementException(); 
/* 196 */             this.entry.key = Short2ReferenceArrayMap.this.key[this.curr = this.next];
/* 197 */             this.entry.value = (V)Short2ReferenceArrayMap.this.value[this.next++];
/* 198 */             return this.entry;
/*     */           }
/*     */ 
/*     */           
/*     */           public void remove() {
/* 203 */             if (this.curr == -1) throw new IllegalStateException(); 
/* 204 */             this.curr = -1;
/* 205 */             int tail = Short2ReferenceArrayMap.this.size-- - this.next--;
/* 206 */             System.arraycopy(Short2ReferenceArrayMap.this.key, this.next + 1, Short2ReferenceArrayMap.this.key, this.next, tail);
/* 207 */             System.arraycopy(Short2ReferenceArrayMap.this.value, this.next + 1, Short2ReferenceArrayMap.this.value, this.next, tail);
/* 208 */             Short2ReferenceArrayMap.this.value[Short2ReferenceArrayMap.this.size] = null;
/*     */           }
/*     */ 
/*     */ 
/*     */ 
/*     */           
/*     */           public void forEachRemaining(Consumer<? super Short2ReferenceMap.Entry<V>> action) {
/* 215 */             int max = Short2ReferenceArrayMap.this.size;
/* 216 */             while (this.next < max) {
/* 217 */               this.entry.key = Short2ReferenceArrayMap.this.key[this.curr = this.next];
/* 218 */               this.entry.value = (V)Short2ReferenceArrayMap.this.value[this.next++];
/* 219 */               action.accept(this.entry);
/*     */             } 
/*     */           }
/*     */         };
/*     */     }
/*     */     
/*     */     final class EntrySetSpliterator
/*     */       extends ObjectSpliterators.EarlyBindingSizeIndexBasedSpliterator<Short2ReferenceMap.Entry<V>>
/*     */       implements ObjectSpliterator<Short2ReferenceMap.Entry<V>> {
/*     */       EntrySetSpliterator(int pos, int maxPos) {
/* 229 */         super(pos, maxPos);
/*     */       }
/*     */ 
/*     */       
/*     */       public int characteristics() {
/* 234 */         return 16465;
/*     */       }
/*     */ 
/*     */ 
/*     */       
/*     */       protected final Short2ReferenceMap.Entry<V> get(int location) {
/* 240 */         return new AbstractShort2ReferenceMap.BasicEntry<>(Short2ReferenceArrayMap.this.key[location], (V)Short2ReferenceArrayMap.this.value[location]);
/*     */       }
/*     */ 
/*     */       
/*     */       protected final EntrySetSpliterator makeForSplit(int pos, int maxPos) {
/* 245 */         return new EntrySetSpliterator(pos, maxPos);
/*     */       }
/*     */     }
/*     */ 
/*     */     
/*     */     public ObjectSpliterator<Short2ReferenceMap.Entry<V>> spliterator() {
/* 251 */       return new EntrySetSpliterator(0, Short2ReferenceArrayMap.this.size);
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public void forEach(Consumer<? super Short2ReferenceMap.Entry<V>> action) {
/* 259 */       for (int i = 0, max = Short2ReferenceArrayMap.this.size; i < max; i++) {
/* 260 */         action.accept(new AbstractShort2ReferenceMap.BasicEntry<>(Short2ReferenceArrayMap.this.key[i], (V)Short2ReferenceArrayMap.this.value[i]));
/*     */       }
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public void fastForEach(Consumer<? super Short2ReferenceMap.Entry<V>> action) {
/* 268 */       AbstractShort2ReferenceMap.BasicEntry<V> entry = new AbstractShort2ReferenceMap.BasicEntry<>();
/*     */       
/* 270 */       for (int i = 0, max = Short2ReferenceArrayMap.this.size; i < max; i++) {
/* 271 */         entry.key = Short2ReferenceArrayMap.this.key[i];
/* 272 */         entry.value = (V)Short2ReferenceArrayMap.this.value[i];
/* 273 */         action.accept(entry);
/*     */       } 
/*     */     }
/*     */ 
/*     */     
/*     */     public int size() {
/* 279 */       return Short2ReferenceArrayMap.this.size;
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     public boolean contains(Object o) {
/* 285 */       if (!(o instanceof Map.Entry)) return false; 
/* 286 */       Map.Entry<?, ?> e = (Map.Entry<?, ?>)o;
/* 287 */       if (e.getKey() == null || !(e.getKey() instanceof Short)) return false; 
/* 288 */       short k = ((Short)e.getKey()).shortValue();
/* 289 */       return (Short2ReferenceArrayMap.this.containsKey(k) && Short2ReferenceArrayMap.this.get(k) == e.getValue());
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     public boolean remove(Object o) {
/* 295 */       if (!(o instanceof Map.Entry)) return false; 
/* 296 */       Map.Entry<?, ?> e = (Map.Entry<?, ?>)o;
/* 297 */       if (e.getKey() == null || !(e.getKey() instanceof Short)) return false; 
/* 298 */       short k = ((Short)e.getKey()).shortValue();
/* 299 */       V v = (V)e.getValue();
/* 300 */       int oldPos = Short2ReferenceArrayMap.this.findKey(k);
/* 301 */       if (oldPos == -1 || v != Short2ReferenceArrayMap.this.value[oldPos]) return false; 
/* 302 */       int tail = Short2ReferenceArrayMap.this.size - oldPos - 1;
/* 303 */       System.arraycopy(Short2ReferenceArrayMap.this.key, oldPos + 1, Short2ReferenceArrayMap.this.key, oldPos, tail);
/* 304 */       System.arraycopy(Short2ReferenceArrayMap.this.value, oldPos + 1, Short2ReferenceArrayMap.this.value, oldPos, tail);
/* 305 */       Short2ReferenceArrayMap.this.size--;
/* 306 */       Short2ReferenceArrayMap.this.value[Short2ReferenceArrayMap.this.size] = null;
/* 307 */       return true;
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   public Short2ReferenceMap.FastEntrySet<V> short2ReferenceEntrySet() {
/* 313 */     if (this.entries == null) this.entries = new EntrySet(); 
/* 314 */     return this.entries;
/*     */   }
/*     */   
/*     */   private int findKey(short k) {
/* 318 */     short[] key = this.key;
/* 319 */     for (int i = this.size; i-- != 0;) { if (key[i] == k) return i;  }
/* 320 */      return -1;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public V get(short k) {
/* 326 */     short[] key = this.key;
/* 327 */     for (int i = this.size; i-- != 0;) { if (key[i] == k) return (V)this.value[i];  }
/* 328 */      return this.defRetValue;
/*     */   }
/*     */ 
/*     */   
/*     */   public int size() {
/* 333 */     return this.size;
/*     */   }
/*     */ 
/*     */   
/*     */   public void clear() {
/* 338 */     for (int i = this.size; i-- != 0;) {
/* 339 */       this.value[i] = null;
/*     */     }
/* 341 */     this.size = 0;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean containsKey(short k) {
/* 346 */     return (findKey(k) != -1);
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean containsValue(Object v) {
/* 351 */     for (int i = this.size; i-- != 0;) { if (this.value[i] == v) return true;  }
/* 352 */      return false;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isEmpty() {
/* 357 */     return (this.size == 0);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public V put(short k, V v) {
/* 363 */     int oldKey = findKey(k);
/* 364 */     if (oldKey != -1) {
/* 365 */       V oldValue = (V)this.value[oldKey];
/* 366 */       this.value[oldKey] = v;
/* 367 */       return oldValue;
/*     */     } 
/* 369 */     if (this.size == this.key.length) {
/* 370 */       short[] newKey = new short[(this.size == 0) ? 2 : (this.size * 2)];
/* 371 */       Object[] newValue = new Object[(this.size == 0) ? 2 : (this.size * 2)];
/* 372 */       for (int i = this.size; i-- != 0; ) {
/* 373 */         newKey[i] = this.key[i];
/* 374 */         newValue[i] = this.value[i];
/*     */       } 
/* 376 */       this.key = newKey;
/* 377 */       this.value = newValue;
/*     */     } 
/* 379 */     this.key[this.size] = k;
/* 380 */     this.value[this.size] = v;
/* 381 */     this.size++;
/* 382 */     return this.defRetValue;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public V remove(short k) {
/* 388 */     int oldPos = findKey(k);
/* 389 */     if (oldPos == -1) return this.defRetValue; 
/* 390 */     V oldValue = (V)this.value[oldPos];
/* 391 */     int tail = this.size - oldPos - 1;
/* 392 */     System.arraycopy(this.key, oldPos + 1, this.key, oldPos, tail);
/* 393 */     System.arraycopy(this.value, oldPos + 1, this.value, oldPos, tail);
/* 394 */     this.size--;
/* 395 */     this.value[this.size] = null;
/* 396 */     return oldValue;
/*     */   }
/*     */   
/*     */   private final class KeySet extends AbstractShortSet { private KeySet() {}
/*     */     
/*     */     public boolean contains(short k) {
/* 402 */       return (Short2ReferenceArrayMap.this.findKey(k) != -1);
/*     */     }
/*     */ 
/*     */     
/*     */     public boolean remove(short k) {
/* 407 */       int oldPos = Short2ReferenceArrayMap.this.findKey(k);
/* 408 */       if (oldPos == -1) return false; 
/* 409 */       int tail = Short2ReferenceArrayMap.this.size - oldPos - 1;
/* 410 */       System.arraycopy(Short2ReferenceArrayMap.this.key, oldPos + 1, Short2ReferenceArrayMap.this.key, oldPos, tail);
/* 411 */       System.arraycopy(Short2ReferenceArrayMap.this.value, oldPos + 1, Short2ReferenceArrayMap.this.value, oldPos, tail);
/* 412 */       Short2ReferenceArrayMap.this.size--;
/* 413 */       Short2ReferenceArrayMap.this.value[Short2ReferenceArrayMap.this.size] = null;
/* 414 */       return true;
/*     */     }
/*     */ 
/*     */     
/*     */     public ShortIterator iterator() {
/* 419 */       return new ShortIterator() {
/* 420 */           int pos = 0;
/*     */ 
/*     */           
/*     */           public boolean hasNext() {
/* 424 */             return (this.pos < Short2ReferenceArrayMap.this.size);
/*     */           }
/*     */ 
/*     */ 
/*     */           
/*     */           public short nextShort() {
/* 430 */             if (!hasNext()) throw new NoSuchElementException(); 
/* 431 */             return Short2ReferenceArrayMap.this.key[this.pos++];
/*     */           }
/*     */ 
/*     */           
/*     */           public void remove() {
/* 436 */             if (this.pos == 0) throw new IllegalStateException(); 
/* 437 */             int tail = Short2ReferenceArrayMap.this.size - this.pos;
/* 438 */             System.arraycopy(Short2ReferenceArrayMap.this.key, this.pos, Short2ReferenceArrayMap.this.key, this.pos - 1, tail);
/* 439 */             System.arraycopy(Short2ReferenceArrayMap.this.value, this.pos, Short2ReferenceArrayMap.this.value, this.pos - 1, tail);
/* 440 */             Short2ReferenceArrayMap.this.size--;
/* 441 */             this.pos--;
/* 442 */             Short2ReferenceArrayMap.this.value[Short2ReferenceArrayMap.this.size] = null;
/*     */           }
/*     */ 
/*     */ 
/*     */ 
/*     */           
/*     */           public void forEachRemaining(ShortConsumer action) {
/* 449 */             int max = Short2ReferenceArrayMap.this.size;
/* 450 */             while (this.pos < max)
/* 451 */               action.accept(Short2ReferenceArrayMap.this.key[this.pos++]); 
/*     */           }
/*     */         };
/*     */     }
/*     */     
/*     */     final class KeySetSpliterator
/*     */       extends ShortSpliterators.EarlyBindingSizeIndexBasedSpliterator
/*     */       implements ShortSpliterator {
/*     */       KeySetSpliterator(int pos, int maxPos) {
/* 460 */         super(pos, maxPos);
/*     */       }
/*     */ 
/*     */       
/*     */       public int characteristics() {
/* 465 */         return 16721;
/*     */       }
/*     */ 
/*     */ 
/*     */       
/*     */       protected final short get(int location) {
/* 471 */         return Short2ReferenceArrayMap.this.key[location];
/*     */       }
/*     */ 
/*     */       
/*     */       protected final KeySetSpliterator makeForSplit(int pos, int maxPos) {
/* 476 */         return new KeySetSpliterator(pos, maxPos);
/*     */       }
/*     */ 
/*     */ 
/*     */ 
/*     */       
/*     */       public void forEachRemaining(ShortConsumer action) {
/* 483 */         int max = Short2ReferenceArrayMap.this.size;
/* 484 */         while (this.pos < max) {
/* 485 */           action.accept(Short2ReferenceArrayMap.this.key[this.pos++]);
/*     */         }
/*     */       }
/*     */     }
/*     */ 
/*     */     
/*     */     public ShortSpliterator spliterator() {
/* 492 */       return new KeySetSpliterator(0, Short2ReferenceArrayMap.this.size);
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public void forEach(ShortConsumer action) {
/* 499 */       for (int i = 0, max = Short2ReferenceArrayMap.this.size; i < max; i++) {
/* 500 */         action.accept(Short2ReferenceArrayMap.this.key[i]);
/*     */       }
/*     */     }
/*     */ 
/*     */     
/*     */     public int size() {
/* 506 */       return Short2ReferenceArrayMap.this.size;
/*     */     }
/*     */ 
/*     */     
/*     */     public void clear() {
/* 511 */       Short2ReferenceArrayMap.this.clear();
/*     */     } }
/*     */ 
/*     */ 
/*     */   
/*     */   public ShortSet keySet() {
/* 517 */     if (this.keys == null) this.keys = new KeySet(); 
/* 518 */     return this.keys;
/*     */   }
/*     */   
/*     */   private final class ValuesCollection extends AbstractReferenceCollection<V> { private ValuesCollection() {}
/*     */     
/*     */     public boolean contains(Object v) {
/* 524 */       return Short2ReferenceArrayMap.this.containsValue(v);
/*     */     }
/*     */ 
/*     */     
/*     */     public ObjectIterator<V> iterator() {
/* 529 */       return new ObjectIterator<V>() {
/* 530 */           int pos = 0;
/*     */ 
/*     */           
/*     */           public boolean hasNext() {
/* 534 */             return (this.pos < Short2ReferenceArrayMap.this.size);
/*     */           }
/*     */ 
/*     */ 
/*     */           
/*     */           public V next() {
/* 540 */             if (!hasNext()) throw new NoSuchElementException(); 
/* 541 */             return (V)Short2ReferenceArrayMap.this.value[this.pos++];
/*     */           }
/*     */ 
/*     */           
/*     */           public void remove() {
/* 546 */             if (this.pos == 0) throw new IllegalStateException(); 
/* 547 */             int tail = Short2ReferenceArrayMap.this.size - this.pos;
/* 548 */             System.arraycopy(Short2ReferenceArrayMap.this.key, this.pos, Short2ReferenceArrayMap.this.key, this.pos - 1, tail);
/* 549 */             System.arraycopy(Short2ReferenceArrayMap.this.value, this.pos, Short2ReferenceArrayMap.this.value, this.pos - 1, tail);
/* 550 */             Short2ReferenceArrayMap.this.size--;
/* 551 */             this.pos--;
/* 552 */             Short2ReferenceArrayMap.this.value[Short2ReferenceArrayMap.this.size] = null;
/*     */           }
/*     */ 
/*     */ 
/*     */ 
/*     */           
/*     */           public void forEachRemaining(Consumer<? super V> action) {
/* 559 */             int max = Short2ReferenceArrayMap.this.size;
/* 560 */             while (this.pos < max)
/* 561 */               action.accept((V)Short2ReferenceArrayMap.this.value[this.pos++]); 
/*     */           }
/*     */         };
/*     */     }
/*     */     
/*     */     final class ValuesSpliterator
/*     */       extends ObjectSpliterators.EarlyBindingSizeIndexBasedSpliterator<V>
/*     */       implements ObjectSpliterator<V> {
/*     */       ValuesSpliterator(int pos, int maxPos) {
/* 570 */         super(pos, maxPos);
/*     */       }
/*     */ 
/*     */       
/*     */       public int characteristics() {
/* 575 */         return 16464;
/*     */       }
/*     */ 
/*     */ 
/*     */       
/*     */       protected final V get(int location) {
/* 581 */         return (V)Short2ReferenceArrayMap.this.value[location];
/*     */       }
/*     */ 
/*     */       
/*     */       protected final ValuesSpliterator makeForSplit(int pos, int maxPos) {
/* 586 */         return new ValuesSpliterator(pos, maxPos);
/*     */       }
/*     */ 
/*     */ 
/*     */ 
/*     */       
/*     */       public void forEachRemaining(Consumer<? super V> action) {
/* 593 */         int max = Short2ReferenceArrayMap.this.size;
/* 594 */         while (this.pos < max) {
/* 595 */           action.accept((V)Short2ReferenceArrayMap.this.value[this.pos++]);
/*     */         }
/*     */       }
/*     */     }
/*     */ 
/*     */     
/*     */     public ObjectSpliterator<V> spliterator() {
/* 602 */       return new ValuesSpliterator(0, Short2ReferenceArrayMap.this.size);
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public void forEach(Consumer<? super V> action) {
/* 609 */       for (int i = 0, max = Short2ReferenceArrayMap.this.size; i < max; i++) {
/* 610 */         action.accept((V)Short2ReferenceArrayMap.this.value[i]);
/*     */       }
/*     */     }
/*     */ 
/*     */     
/*     */     public int size() {
/* 616 */       return Short2ReferenceArrayMap.this.size;
/*     */     }
/*     */ 
/*     */     
/*     */     public void clear() {
/* 621 */       Short2ReferenceArrayMap.this.clear();
/*     */     } }
/*     */ 
/*     */ 
/*     */   
/*     */   public ReferenceCollection<V> values() {
/* 627 */     if (this.values == null) this.values = (ReferenceCollection<V>)new ValuesCollection(); 
/* 628 */     return this.values;
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
/*     */   public Short2ReferenceArrayMap<V> clone() {
/*     */     Short2ReferenceArrayMap<V> c;
/*     */     try {
/* 645 */       c = (Short2ReferenceArrayMap<V>)super.clone();
/* 646 */     } catch (CloneNotSupportedException cantHappen) {
/* 647 */       throw new InternalError();
/*     */     } 
/* 649 */     c.key = (short[])this.key.clone();
/* 650 */     c.value = (Object[])this.value.clone();
/* 651 */     c.entries = null;
/* 652 */     c.keys = null;
/* 653 */     c.values = null;
/* 654 */     return c;
/*     */   }
/*     */   
/*     */   private void writeObject(ObjectOutputStream s) throws IOException {
/* 658 */     s.defaultWriteObject();
/* 659 */     for (int i = 0, max = this.size; i < max; i++) {
/* 660 */       s.writeShort(this.key[i]);
/* 661 */       s.writeObject(this.value[i]);
/*     */     } 
/*     */   }
/*     */   
/*     */   private void readObject(ObjectInputStream s) throws IOException, ClassNotFoundException {
/* 666 */     s.defaultReadObject();
/* 667 */     this.key = new short[this.size];
/* 668 */     this.value = new Object[this.size];
/* 669 */     for (int i = 0; i < this.size; i++) {
/* 670 */       this.key[i] = s.readShort();
/* 671 */       this.value[i] = s.readObject();
/*     */     } 
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\i\\unimi\dsi\fastutil\shorts\Short2ReferenceArrayMap.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */