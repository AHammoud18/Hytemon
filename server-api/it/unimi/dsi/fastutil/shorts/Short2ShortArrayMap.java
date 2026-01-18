/*     */ package it.unimi.dsi.fastutil.shorts;
/*     */ 
/*     */ import it.unimi.dsi.fastutil.objects.AbstractObjectSet;
/*     */ import it.unimi.dsi.fastutil.objects.ObjectIterator;
/*     */ import it.unimi.dsi.fastutil.objects.ObjectSet;
/*     */ import it.unimi.dsi.fastutil.objects.ObjectSpliterator;
/*     */ import it.unimi.dsi.fastutil.objects.ObjectSpliterators;
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
/*     */ public class Short2ShortArrayMap
/*     */   extends AbstractShort2ShortMap
/*     */   implements Serializable, Cloneable
/*     */ {
/*     */   private static final long serialVersionUID = 1L;
/*     */   protected transient short[] key;
/*     */   protected transient short[] value;
/*     */   protected int size;
/*     */   protected transient Short2ShortMap.FastEntrySet entries;
/*     */   protected transient ShortSet keys;
/*     */   protected transient ShortCollection values;
/*     */   
/*     */   public Short2ShortArrayMap(short[] key, short[] value) {
/*  60 */     this.key = key;
/*  61 */     this.value = value;
/*  62 */     this.size = key.length;
/*  63 */     if (key.length != value.length) throw new IllegalArgumentException("Keys and values have different lengths (" + key.length + ", " + value.length + ")");
/*     */   
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public Short2ShortArrayMap() {
/*  70 */     this.key = ShortArrays.EMPTY_ARRAY;
/*  71 */     this.value = ShortArrays.EMPTY_ARRAY;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Short2ShortArrayMap(int capacity) {
/*  80 */     this.key = new short[capacity];
/*  81 */     this.value = new short[capacity];
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Short2ShortArrayMap(Short2ShortMap m) {
/*  90 */     this(m.size());
/*  91 */     int i = 0;
/*  92 */     for (ObjectIterator<Short2ShortMap.Entry> objectIterator = m.short2ShortEntrySet().iterator(); objectIterator.hasNext(); ) { Short2ShortMap.Entry e = objectIterator.next();
/*  93 */       this.key[i] = e.getShortKey();
/*  94 */       this.value[i] = e.getShortValue();
/*  95 */       i++; }
/*     */     
/*  97 */     this.size = i;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Short2ShortArrayMap(Map<? extends Short, ? extends Short> m) {
/* 106 */     this(m.size());
/* 107 */     int i = 0;
/* 108 */     for (Map.Entry<? extends Short, ? extends Short> e : m.entrySet()) {
/* 109 */       this.key[i] = ((Short)e.getKey()).shortValue();
/* 110 */       this.value[i] = ((Short)e.getValue()).shortValue();
/* 111 */       i++;
/*     */     } 
/* 113 */     this.size = i;
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
/*     */   public Short2ShortArrayMap(short[] key, short[] value, int size) {
/* 129 */     this.key = key;
/* 130 */     this.value = value;
/* 131 */     this.size = size;
/* 132 */     if (key.length != value.length) throw new IllegalArgumentException("Keys and values have different lengths (" + key.length + ", " + value.length + ")"); 
/* 133 */     if (size > key.length) throw new IllegalArgumentException("The provided size (" + size + ") is larger than or equal to the backing-arrays size (" + key.length + ")"); 
/*     */   }
/*     */   
/*     */   private final class EntrySet
/*     */     extends AbstractObjectSet<Short2ShortMap.Entry> implements Short2ShortMap.FastEntrySet {
/*     */     private EntrySet() {}
/*     */     
/*     */     public ObjectIterator<Short2ShortMap.Entry> iterator() {
/* 141 */       return new ObjectIterator<Short2ShortMap.Entry>() {
/* 142 */           int curr = -1; int next = 0;
/*     */ 
/*     */           
/*     */           public boolean hasNext() {
/* 146 */             return (this.next < Short2ShortArrayMap.this.size);
/*     */           }
/*     */ 
/*     */ 
/*     */           
/*     */           public Short2ShortMap.Entry next() {
/* 152 */             if (!hasNext()) throw new NoSuchElementException(); 
/* 153 */             return new AbstractShort2ShortMap.BasicEntry(Short2ShortArrayMap.this.key[this.curr = this.next], Short2ShortArrayMap.this.value[this.next++]);
/*     */           }
/*     */ 
/*     */           
/*     */           public void remove() {
/* 158 */             if (this.curr == -1) throw new IllegalStateException(); 
/* 159 */             this.curr = -1;
/* 160 */             int tail = Short2ShortArrayMap.this.size-- - this.next--;
/* 161 */             System.arraycopy(Short2ShortArrayMap.this.key, this.next + 1, Short2ShortArrayMap.this.key, this.next, tail);
/* 162 */             System.arraycopy(Short2ShortArrayMap.this.value, this.next + 1, Short2ShortArrayMap.this.value, this.next, tail);
/*     */           }
/*     */ 
/*     */ 
/*     */ 
/*     */           
/*     */           public void forEachRemaining(Consumer<? super Short2ShortMap.Entry> action) {
/* 169 */             int max = Short2ShortArrayMap.this.size;
/* 170 */             while (this.next < max) {
/* 171 */               action.accept(new AbstractShort2ShortMap.BasicEntry(Short2ShortArrayMap.this.key[this.curr = this.next], Short2ShortArrayMap.this.value[this.next++]));
/*     */             }
/*     */           }
/*     */         };
/*     */     }
/*     */ 
/*     */     
/*     */     public ObjectIterator<Short2ShortMap.Entry> fastIterator() {
/* 179 */       return new ObjectIterator<Short2ShortMap.Entry>() {
/* 180 */           int next = 0; int curr = -1;
/* 181 */           final AbstractShort2ShortMap.BasicEntry entry = new AbstractShort2ShortMap.BasicEntry();
/*     */ 
/*     */           
/*     */           public boolean hasNext() {
/* 185 */             return (this.next < Short2ShortArrayMap.this.size);
/*     */           }
/*     */ 
/*     */ 
/*     */           
/*     */           public Short2ShortMap.Entry next() {
/* 191 */             if (!hasNext()) throw new NoSuchElementException(); 
/* 192 */             this.entry.key = Short2ShortArrayMap.this.key[this.curr = this.next];
/* 193 */             this.entry.value = Short2ShortArrayMap.this.value[this.next++];
/* 194 */             return this.entry;
/*     */           }
/*     */ 
/*     */           
/*     */           public void remove() {
/* 199 */             if (this.curr == -1) throw new IllegalStateException(); 
/* 200 */             this.curr = -1;
/* 201 */             int tail = Short2ShortArrayMap.this.size-- - this.next--;
/* 202 */             System.arraycopy(Short2ShortArrayMap.this.key, this.next + 1, Short2ShortArrayMap.this.key, this.next, tail);
/* 203 */             System.arraycopy(Short2ShortArrayMap.this.value, this.next + 1, Short2ShortArrayMap.this.value, this.next, tail);
/*     */           }
/*     */ 
/*     */ 
/*     */ 
/*     */           
/*     */           public void forEachRemaining(Consumer<? super Short2ShortMap.Entry> action) {
/* 210 */             int max = Short2ShortArrayMap.this.size;
/* 211 */             while (this.next < max) {
/* 212 */               this.entry.key = Short2ShortArrayMap.this.key[this.curr = this.next];
/* 213 */               this.entry.value = Short2ShortArrayMap.this.value[this.next++];
/* 214 */               action.accept(this.entry);
/*     */             } 
/*     */           }
/*     */         };
/*     */     }
/*     */     
/*     */     final class EntrySetSpliterator
/*     */       extends ObjectSpliterators.EarlyBindingSizeIndexBasedSpliterator<Short2ShortMap.Entry>
/*     */       implements ObjectSpliterator<Short2ShortMap.Entry> {
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
/*     */       protected final Short2ShortMap.Entry get(int location) {
/* 235 */         return new AbstractShort2ShortMap.BasicEntry(Short2ShortArrayMap.this.key[location], Short2ShortArrayMap.this.value[location]);
/*     */       }
/*     */ 
/*     */       
/*     */       protected final EntrySetSpliterator makeForSplit(int pos, int maxPos) {
/* 240 */         return new EntrySetSpliterator(pos, maxPos);
/*     */       }
/*     */     }
/*     */ 
/*     */     
/*     */     public ObjectSpliterator<Short2ShortMap.Entry> spliterator() {
/* 246 */       return new EntrySetSpliterator(0, Short2ShortArrayMap.this.size);
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public void forEach(Consumer<? super Short2ShortMap.Entry> action) {
/* 254 */       for (int i = 0, max = Short2ShortArrayMap.this.size; i < max; i++) {
/* 255 */         action.accept(new AbstractShort2ShortMap.BasicEntry(Short2ShortArrayMap.this.key[i], Short2ShortArrayMap.this.value[i]));
/*     */       }
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public void fastForEach(Consumer<? super Short2ShortMap.Entry> action) {
/* 263 */       AbstractShort2ShortMap.BasicEntry entry = new AbstractShort2ShortMap.BasicEntry();
/*     */       
/* 265 */       for (int i = 0, max = Short2ShortArrayMap.this.size; i < max; i++) {
/* 266 */         entry.key = Short2ShortArrayMap.this.key[i];
/* 267 */         entry.value = Short2ShortArrayMap.this.value[i];
/* 268 */         action.accept(entry);
/*     */       } 
/*     */     }
/*     */ 
/*     */     
/*     */     public int size() {
/* 274 */       return Short2ShortArrayMap.this.size;
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     public boolean contains(Object o) {
/* 280 */       if (!(o instanceof Map.Entry)) return false; 
/* 281 */       Map.Entry<?, ?> e = (Map.Entry<?, ?>)o;
/* 282 */       if (e.getKey() == null || !(e.getKey() instanceof Short)) return false; 
/* 283 */       if (e.getValue() == null || !(e.getValue() instanceof Short)) return false; 
/* 284 */       short k = ((Short)e.getKey()).shortValue();
/* 285 */       return (Short2ShortArrayMap.this.containsKey(k) && Short2ShortArrayMap.this.get(k) == ((Short)e.getValue()).shortValue());
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     public boolean remove(Object o) {
/* 291 */       if (!(o instanceof Map.Entry)) return false; 
/* 292 */       Map.Entry<?, ?> e = (Map.Entry<?, ?>)o;
/* 293 */       if (e.getKey() == null || !(e.getKey() instanceof Short)) return false; 
/* 294 */       if (e.getValue() == null || !(e.getValue() instanceof Short)) return false; 
/* 295 */       short k = ((Short)e.getKey()).shortValue();
/* 296 */       short v = ((Short)e.getValue()).shortValue();
/* 297 */       int oldPos = Short2ShortArrayMap.this.findKey(k);
/* 298 */       if (oldPos == -1 || v != Short2ShortArrayMap.this.value[oldPos]) return false; 
/* 299 */       int tail = Short2ShortArrayMap.this.size - oldPos - 1;
/* 300 */       System.arraycopy(Short2ShortArrayMap.this.key, oldPos + 1, Short2ShortArrayMap.this.key, oldPos, tail);
/* 301 */       System.arraycopy(Short2ShortArrayMap.this.value, oldPos + 1, Short2ShortArrayMap.this.value, oldPos, tail);
/* 302 */       Short2ShortArrayMap.this.size--;
/* 303 */       return true;
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   public Short2ShortMap.FastEntrySet short2ShortEntrySet() {
/* 309 */     if (this.entries == null) this.entries = new EntrySet(); 
/* 310 */     return this.entries;
/*     */   }
/*     */   
/*     */   private int findKey(short k) {
/* 314 */     short[] key = this.key;
/* 315 */     for (int i = this.size; i-- != 0;) { if (key[i] == k) return i;  }
/* 316 */      return -1;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public short get(short k) {
/* 322 */     short[] key = this.key;
/* 323 */     for (int i = this.size; i-- != 0;) { if (key[i] == k) return this.value[i];  }
/* 324 */      return this.defRetValue;
/*     */   }
/*     */ 
/*     */   
/*     */   public int size() {
/* 329 */     return this.size;
/*     */   }
/*     */ 
/*     */   
/*     */   public void clear() {
/* 334 */     this.size = 0;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean containsKey(short k) {
/* 339 */     return (findKey(k) != -1);
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean containsValue(short v) {
/* 344 */     for (int i = this.size; i-- != 0;) { if (this.value[i] == v) return true;  }
/* 345 */      return false;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isEmpty() {
/* 350 */     return (this.size == 0);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public short put(short k, short v) {
/* 356 */     int oldKey = findKey(k);
/* 357 */     if (oldKey != -1) {
/* 358 */       short oldValue = this.value[oldKey];
/* 359 */       this.value[oldKey] = v;
/* 360 */       return oldValue;
/*     */     } 
/* 362 */     if (this.size == this.key.length) {
/* 363 */       short[] newKey = new short[(this.size == 0) ? 2 : (this.size * 2)];
/* 364 */       short[] newValue = new short[(this.size == 0) ? 2 : (this.size * 2)];
/* 365 */       for (int i = this.size; i-- != 0; ) {
/* 366 */         newKey[i] = this.key[i];
/* 367 */         newValue[i] = this.value[i];
/*     */       } 
/* 369 */       this.key = newKey;
/* 370 */       this.value = newValue;
/*     */     } 
/* 372 */     this.key[this.size] = k;
/* 373 */     this.value[this.size] = v;
/* 374 */     this.size++;
/* 375 */     return this.defRetValue;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public short remove(short k) {
/* 381 */     int oldPos = findKey(k);
/* 382 */     if (oldPos == -1) return this.defRetValue; 
/* 383 */     short oldValue = this.value[oldPos];
/* 384 */     int tail = this.size - oldPos - 1;
/* 385 */     System.arraycopy(this.key, oldPos + 1, this.key, oldPos, tail);
/* 386 */     System.arraycopy(this.value, oldPos + 1, this.value, oldPos, tail);
/* 387 */     this.size--;
/* 388 */     return oldValue;
/*     */   }
/*     */   
/*     */   private final class KeySet extends AbstractShortSet { private KeySet() {}
/*     */     
/*     */     public boolean contains(short k) {
/* 394 */       return (Short2ShortArrayMap.this.findKey(k) != -1);
/*     */     }
/*     */ 
/*     */     
/*     */     public boolean remove(short k) {
/* 399 */       int oldPos = Short2ShortArrayMap.this.findKey(k);
/* 400 */       if (oldPos == -1) return false; 
/* 401 */       int tail = Short2ShortArrayMap.this.size - oldPos - 1;
/* 402 */       System.arraycopy(Short2ShortArrayMap.this.key, oldPos + 1, Short2ShortArrayMap.this.key, oldPos, tail);
/* 403 */       System.arraycopy(Short2ShortArrayMap.this.value, oldPos + 1, Short2ShortArrayMap.this.value, oldPos, tail);
/* 404 */       Short2ShortArrayMap.this.size--;
/* 405 */       return true;
/*     */     }
/*     */ 
/*     */     
/*     */     public ShortIterator iterator() {
/* 410 */       return new ShortIterator() {
/* 411 */           int pos = 0;
/*     */ 
/*     */           
/*     */           public boolean hasNext() {
/* 415 */             return (this.pos < Short2ShortArrayMap.this.size);
/*     */           }
/*     */ 
/*     */ 
/*     */           
/*     */           public short nextShort() {
/* 421 */             if (!hasNext()) throw new NoSuchElementException(); 
/* 422 */             return Short2ShortArrayMap.this.key[this.pos++];
/*     */           }
/*     */ 
/*     */           
/*     */           public void remove() {
/* 427 */             if (this.pos == 0) throw new IllegalStateException(); 
/* 428 */             int tail = Short2ShortArrayMap.this.size - this.pos;
/* 429 */             System.arraycopy(Short2ShortArrayMap.this.key, this.pos, Short2ShortArrayMap.this.key, this.pos - 1, tail);
/* 430 */             System.arraycopy(Short2ShortArrayMap.this.value, this.pos, Short2ShortArrayMap.this.value, this.pos - 1, tail);
/* 431 */             Short2ShortArrayMap.this.size--;
/* 432 */             this.pos--;
/*     */           }
/*     */ 
/*     */ 
/*     */ 
/*     */           
/*     */           public void forEachRemaining(ShortConsumer action) {
/* 439 */             int max = Short2ShortArrayMap.this.size;
/* 440 */             while (this.pos < max)
/* 441 */               action.accept(Short2ShortArrayMap.this.key[this.pos++]); 
/*     */           }
/*     */         };
/*     */     }
/*     */     
/*     */     final class KeySetSpliterator
/*     */       extends ShortSpliterators.EarlyBindingSizeIndexBasedSpliterator
/*     */       implements ShortSpliterator {
/*     */       KeySetSpliterator(int pos, int maxPos) {
/* 450 */         super(pos, maxPos);
/*     */       }
/*     */ 
/*     */       
/*     */       public int characteristics() {
/* 455 */         return 16721;
/*     */       }
/*     */ 
/*     */ 
/*     */       
/*     */       protected final short get(int location) {
/* 461 */         return Short2ShortArrayMap.this.key[location];
/*     */       }
/*     */ 
/*     */       
/*     */       protected final KeySetSpliterator makeForSplit(int pos, int maxPos) {
/* 466 */         return new KeySetSpliterator(pos, maxPos);
/*     */       }
/*     */ 
/*     */ 
/*     */ 
/*     */       
/*     */       public void forEachRemaining(ShortConsumer action) {
/* 473 */         int max = Short2ShortArrayMap.this.size;
/* 474 */         while (this.pos < max) {
/* 475 */           action.accept(Short2ShortArrayMap.this.key[this.pos++]);
/*     */         }
/*     */       }
/*     */     }
/*     */ 
/*     */     
/*     */     public ShortSpliterator spliterator() {
/* 482 */       return new KeySetSpliterator(0, Short2ShortArrayMap.this.size);
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public void forEach(ShortConsumer action) {
/* 489 */       for (int i = 0, max = Short2ShortArrayMap.this.size; i < max; i++) {
/* 490 */         action.accept(Short2ShortArrayMap.this.key[i]);
/*     */       }
/*     */     }
/*     */ 
/*     */     
/*     */     public int size() {
/* 496 */       return Short2ShortArrayMap.this.size;
/*     */     }
/*     */ 
/*     */     
/*     */     public void clear() {
/* 501 */       Short2ShortArrayMap.this.clear();
/*     */     } }
/*     */ 
/*     */ 
/*     */   
/*     */   public ShortSet keySet() {
/* 507 */     if (this.keys == null) this.keys = new KeySet(); 
/* 508 */     return this.keys;
/*     */   }
/*     */   
/*     */   private final class ValuesCollection extends AbstractShortCollection { private ValuesCollection() {}
/*     */     
/*     */     public boolean contains(short v) {
/* 514 */       return Short2ShortArrayMap.this.containsValue(v);
/*     */     }
/*     */ 
/*     */     
/*     */     public ShortIterator iterator() {
/* 519 */       return new ShortIterator() {
/* 520 */           int pos = 0;
/*     */ 
/*     */           
/*     */           public boolean hasNext() {
/* 524 */             return (this.pos < Short2ShortArrayMap.this.size);
/*     */           }
/*     */ 
/*     */ 
/*     */           
/*     */           public short nextShort() {
/* 530 */             if (!hasNext()) throw new NoSuchElementException(); 
/* 531 */             return Short2ShortArrayMap.this.value[this.pos++];
/*     */           }
/*     */ 
/*     */           
/*     */           public void remove() {
/* 536 */             if (this.pos == 0) throw new IllegalStateException(); 
/* 537 */             int tail = Short2ShortArrayMap.this.size - this.pos;
/* 538 */             System.arraycopy(Short2ShortArrayMap.this.key, this.pos, Short2ShortArrayMap.this.key, this.pos - 1, tail);
/* 539 */             System.arraycopy(Short2ShortArrayMap.this.value, this.pos, Short2ShortArrayMap.this.value, this.pos - 1, tail);
/* 540 */             Short2ShortArrayMap.this.size--;
/* 541 */             this.pos--;
/*     */           }
/*     */ 
/*     */ 
/*     */ 
/*     */           
/*     */           public void forEachRemaining(ShortConsumer action) {
/* 548 */             int max = Short2ShortArrayMap.this.size;
/* 549 */             while (this.pos < max)
/* 550 */               action.accept(Short2ShortArrayMap.this.value[this.pos++]); 
/*     */           }
/*     */         };
/*     */     }
/*     */     
/*     */     final class ValuesSpliterator
/*     */       extends ShortSpliterators.EarlyBindingSizeIndexBasedSpliterator
/*     */       implements ShortSpliterator {
/*     */       ValuesSpliterator(int pos, int maxPos) {
/* 559 */         super(pos, maxPos);
/*     */       }
/*     */ 
/*     */       
/*     */       public int characteristics() {
/* 564 */         return 16720;
/*     */       }
/*     */ 
/*     */ 
/*     */       
/*     */       protected final short get(int location) {
/* 570 */         return Short2ShortArrayMap.this.value[location];
/*     */       }
/*     */ 
/*     */       
/*     */       protected final ValuesSpliterator makeForSplit(int pos, int maxPos) {
/* 575 */         return new ValuesSpliterator(pos, maxPos);
/*     */       }
/*     */ 
/*     */ 
/*     */ 
/*     */       
/*     */       public void forEachRemaining(ShortConsumer action) {
/* 582 */         int max = Short2ShortArrayMap.this.size;
/* 583 */         while (this.pos < max) {
/* 584 */           action.accept(Short2ShortArrayMap.this.value[this.pos++]);
/*     */         }
/*     */       }
/*     */     }
/*     */ 
/*     */     
/*     */     public ShortSpliterator spliterator() {
/* 591 */       return new ValuesSpliterator(0, Short2ShortArrayMap.this.size);
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public void forEach(ShortConsumer action) {
/* 598 */       for (int i = 0, max = Short2ShortArrayMap.this.size; i < max; i++) {
/* 599 */         action.accept(Short2ShortArrayMap.this.value[i]);
/*     */       }
/*     */     }
/*     */ 
/*     */     
/*     */     public int size() {
/* 605 */       return Short2ShortArrayMap.this.size;
/*     */     }
/*     */ 
/*     */     
/*     */     public void clear() {
/* 610 */       Short2ShortArrayMap.this.clear();
/*     */     } }
/*     */ 
/*     */ 
/*     */   
/*     */   public ShortCollection values() {
/* 616 */     if (this.values == null) this.values = new ValuesCollection(); 
/* 617 */     return this.values;
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
/*     */   public Short2ShortArrayMap clone() {
/*     */     Short2ShortArrayMap c;
/*     */     try {
/* 634 */       c = (Short2ShortArrayMap)super.clone();
/* 635 */     } catch (CloneNotSupportedException cantHappen) {
/* 636 */       throw new InternalError();
/*     */     } 
/* 638 */     c.key = (short[])this.key.clone();
/* 639 */     c.value = (short[])this.value.clone();
/* 640 */     c.entries = null;
/* 641 */     c.keys = null;
/* 642 */     c.values = null;
/* 643 */     return c;
/*     */   }
/*     */   
/*     */   private void writeObject(ObjectOutputStream s) throws IOException {
/* 647 */     s.defaultWriteObject();
/* 648 */     for (int i = 0, max = this.size; i < max; i++) {
/* 649 */       s.writeShort(this.key[i]);
/* 650 */       s.writeShort(this.value[i]);
/*     */     } 
/*     */   }
/*     */   
/*     */   private void readObject(ObjectInputStream s) throws IOException, ClassNotFoundException {
/* 655 */     s.defaultReadObject();
/* 656 */     this.key = new short[this.size];
/* 657 */     this.value = new short[this.size];
/* 658 */     for (int i = 0; i < this.size; i++) {
/* 659 */       this.key[i] = s.readShort();
/* 660 */       this.value[i] = s.readShort();
/*     */     } 
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\i\\unimi\dsi\fastutil\shorts\Short2ShortArrayMap.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */