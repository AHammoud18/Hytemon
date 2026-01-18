/*     */ package it.unimi.dsi.fastutil.shorts;
/*     */ 
/*     */ import it.unimi.dsi.fastutil.longs.AbstractLongCollection;
/*     */ import it.unimi.dsi.fastutil.longs.LongArrays;
/*     */ import it.unimi.dsi.fastutil.longs.LongCollection;
/*     */ import it.unimi.dsi.fastutil.longs.LongIterator;
/*     */ import it.unimi.dsi.fastutil.longs.LongSpliterator;
/*     */ import it.unimi.dsi.fastutil.longs.LongSpliterators;
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
/*     */ import java.util.function.LongConsumer;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class Short2LongArrayMap
/*     */   extends AbstractShort2LongMap
/*     */   implements Serializable, Cloneable
/*     */ {
/*     */   private static final long serialVersionUID = 1L;
/*     */   protected transient short[] key;
/*     */   protected transient long[] value;
/*     */   protected int size;
/*     */   protected transient Short2LongMap.FastEntrySet entries;
/*     */   protected transient ShortSet keys;
/*     */   protected transient LongCollection values;
/*     */   
/*     */   public Short2LongArrayMap(short[] key, long[] value) {
/*  63 */     this.key = key;
/*  64 */     this.value = value;
/*  65 */     this.size = key.length;
/*  66 */     if (key.length != value.length) throw new IllegalArgumentException("Keys and values have different lengths (" + key.length + ", " + value.length + ")");
/*     */   
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public Short2LongArrayMap() {
/*  73 */     this.key = ShortArrays.EMPTY_ARRAY;
/*  74 */     this.value = LongArrays.EMPTY_ARRAY;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Short2LongArrayMap(int capacity) {
/*  83 */     this.key = new short[capacity];
/*  84 */     this.value = new long[capacity];
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Short2LongArrayMap(Short2LongMap m) {
/*  93 */     this(m.size());
/*  94 */     int i = 0;
/*  95 */     for (ObjectIterator<Short2LongMap.Entry> objectIterator = m.short2LongEntrySet().iterator(); objectIterator.hasNext(); ) { Short2LongMap.Entry e = objectIterator.next();
/*  96 */       this.key[i] = e.getShortKey();
/*  97 */       this.value[i] = e.getLongValue();
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
/*     */   public Short2LongArrayMap(Map<? extends Short, ? extends Long> m) {
/* 109 */     this(m.size());
/* 110 */     int i = 0;
/* 111 */     for (Map.Entry<? extends Short, ? extends Long> e : m.entrySet()) {
/* 112 */       this.key[i] = ((Short)e.getKey()).shortValue();
/* 113 */       this.value[i] = ((Long)e.getValue()).longValue();
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
/*     */   public Short2LongArrayMap(short[] key, long[] value, int size) {
/* 132 */     this.key = key;
/* 133 */     this.value = value;
/* 134 */     this.size = size;
/* 135 */     if (key.length != value.length) throw new IllegalArgumentException("Keys and values have different lengths (" + key.length + ", " + value.length + ")"); 
/* 136 */     if (size > key.length) throw new IllegalArgumentException("The provided size (" + size + ") is larger than or equal to the backing-arrays size (" + key.length + ")"); 
/*     */   }
/*     */   
/*     */   private final class EntrySet
/*     */     extends AbstractObjectSet<Short2LongMap.Entry> implements Short2LongMap.FastEntrySet {
/*     */     private EntrySet() {}
/*     */     
/*     */     public ObjectIterator<Short2LongMap.Entry> iterator() {
/* 144 */       return new ObjectIterator<Short2LongMap.Entry>() {
/* 145 */           int curr = -1; int next = 0;
/*     */ 
/*     */           
/*     */           public boolean hasNext() {
/* 149 */             return (this.next < Short2LongArrayMap.this.size);
/*     */           }
/*     */ 
/*     */ 
/*     */           
/*     */           public Short2LongMap.Entry next() {
/* 155 */             if (!hasNext()) throw new NoSuchElementException(); 
/* 156 */             return new AbstractShort2LongMap.BasicEntry(Short2LongArrayMap.this.key[this.curr = this.next], Short2LongArrayMap.this.value[this.next++]);
/*     */           }
/*     */ 
/*     */           
/*     */           public void remove() {
/* 161 */             if (this.curr == -1) throw new IllegalStateException(); 
/* 162 */             this.curr = -1;
/* 163 */             int tail = Short2LongArrayMap.this.size-- - this.next--;
/* 164 */             System.arraycopy(Short2LongArrayMap.this.key, this.next + 1, Short2LongArrayMap.this.key, this.next, tail);
/* 165 */             System.arraycopy(Short2LongArrayMap.this.value, this.next + 1, Short2LongArrayMap.this.value, this.next, tail);
/*     */           }
/*     */ 
/*     */ 
/*     */ 
/*     */           
/*     */           public void forEachRemaining(Consumer<? super Short2LongMap.Entry> action) {
/* 172 */             int max = Short2LongArrayMap.this.size;
/* 173 */             while (this.next < max) {
/* 174 */               action.accept(new AbstractShort2LongMap.BasicEntry(Short2LongArrayMap.this.key[this.curr = this.next], Short2LongArrayMap.this.value[this.next++]));
/*     */             }
/*     */           }
/*     */         };
/*     */     }
/*     */ 
/*     */     
/*     */     public ObjectIterator<Short2LongMap.Entry> fastIterator() {
/* 182 */       return new ObjectIterator<Short2LongMap.Entry>() {
/* 183 */           int next = 0; int curr = -1;
/* 184 */           final AbstractShort2LongMap.BasicEntry entry = new AbstractShort2LongMap.BasicEntry();
/*     */ 
/*     */           
/*     */           public boolean hasNext() {
/* 188 */             return (this.next < Short2LongArrayMap.this.size);
/*     */           }
/*     */ 
/*     */ 
/*     */           
/*     */           public Short2LongMap.Entry next() {
/* 194 */             if (!hasNext()) throw new NoSuchElementException(); 
/* 195 */             this.entry.key = Short2LongArrayMap.this.key[this.curr = this.next];
/* 196 */             this.entry.value = Short2LongArrayMap.this.value[this.next++];
/* 197 */             return this.entry;
/*     */           }
/*     */ 
/*     */           
/*     */           public void remove() {
/* 202 */             if (this.curr == -1) throw new IllegalStateException(); 
/* 203 */             this.curr = -1;
/* 204 */             int tail = Short2LongArrayMap.this.size-- - this.next--;
/* 205 */             System.arraycopy(Short2LongArrayMap.this.key, this.next + 1, Short2LongArrayMap.this.key, this.next, tail);
/* 206 */             System.arraycopy(Short2LongArrayMap.this.value, this.next + 1, Short2LongArrayMap.this.value, this.next, tail);
/*     */           }
/*     */ 
/*     */ 
/*     */ 
/*     */           
/*     */           public void forEachRemaining(Consumer<? super Short2LongMap.Entry> action) {
/* 213 */             int max = Short2LongArrayMap.this.size;
/* 214 */             while (this.next < max) {
/* 215 */               this.entry.key = Short2LongArrayMap.this.key[this.curr = this.next];
/* 216 */               this.entry.value = Short2LongArrayMap.this.value[this.next++];
/* 217 */               action.accept(this.entry);
/*     */             } 
/*     */           }
/*     */         };
/*     */     }
/*     */     
/*     */     final class EntrySetSpliterator
/*     */       extends ObjectSpliterators.EarlyBindingSizeIndexBasedSpliterator<Short2LongMap.Entry>
/*     */       implements ObjectSpliterator<Short2LongMap.Entry> {
/*     */       EntrySetSpliterator(int pos, int maxPos) {
/* 227 */         super(pos, maxPos);
/*     */       }
/*     */ 
/*     */       
/*     */       public int characteristics() {
/* 232 */         return 16465;
/*     */       }
/*     */ 
/*     */ 
/*     */       
/*     */       protected final Short2LongMap.Entry get(int location) {
/* 238 */         return new AbstractShort2LongMap.BasicEntry(Short2LongArrayMap.this.key[location], Short2LongArrayMap.this.value[location]);
/*     */       }
/*     */ 
/*     */       
/*     */       protected final EntrySetSpliterator makeForSplit(int pos, int maxPos) {
/* 243 */         return new EntrySetSpliterator(pos, maxPos);
/*     */       }
/*     */     }
/*     */ 
/*     */     
/*     */     public ObjectSpliterator<Short2LongMap.Entry> spliterator() {
/* 249 */       return new EntrySetSpliterator(0, Short2LongArrayMap.this.size);
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public void forEach(Consumer<? super Short2LongMap.Entry> action) {
/* 257 */       for (int i = 0, max = Short2LongArrayMap.this.size; i < max; i++) {
/* 258 */         action.accept(new AbstractShort2LongMap.BasicEntry(Short2LongArrayMap.this.key[i], Short2LongArrayMap.this.value[i]));
/*     */       }
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public void fastForEach(Consumer<? super Short2LongMap.Entry> action) {
/* 266 */       AbstractShort2LongMap.BasicEntry entry = new AbstractShort2LongMap.BasicEntry();
/*     */       
/* 268 */       for (int i = 0, max = Short2LongArrayMap.this.size; i < max; i++) {
/* 269 */         entry.key = Short2LongArrayMap.this.key[i];
/* 270 */         entry.value = Short2LongArrayMap.this.value[i];
/* 271 */         action.accept(entry);
/*     */       } 
/*     */     }
/*     */ 
/*     */     
/*     */     public int size() {
/* 277 */       return Short2LongArrayMap.this.size;
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     public boolean contains(Object o) {
/* 283 */       if (!(o instanceof Map.Entry)) return false; 
/* 284 */       Map.Entry<?, ?> e = (Map.Entry<?, ?>)o;
/* 285 */       if (e.getKey() == null || !(e.getKey() instanceof Short)) return false; 
/* 286 */       if (e.getValue() == null || !(e.getValue() instanceof Long)) return false; 
/* 287 */       short k = ((Short)e.getKey()).shortValue();
/* 288 */       return (Short2LongArrayMap.this.containsKey(k) && Short2LongArrayMap.this.get(k) == ((Long)e.getValue()).longValue());
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     public boolean remove(Object o) {
/* 294 */       if (!(o instanceof Map.Entry)) return false; 
/* 295 */       Map.Entry<?, ?> e = (Map.Entry<?, ?>)o;
/* 296 */       if (e.getKey() == null || !(e.getKey() instanceof Short)) return false; 
/* 297 */       if (e.getValue() == null || !(e.getValue() instanceof Long)) return false; 
/* 298 */       short k = ((Short)e.getKey()).shortValue();
/* 299 */       long v = ((Long)e.getValue()).longValue();
/* 300 */       int oldPos = Short2LongArrayMap.this.findKey(k);
/* 301 */       if (oldPos == -1 || v != Short2LongArrayMap.this.value[oldPos]) return false; 
/* 302 */       int tail = Short2LongArrayMap.this.size - oldPos - 1;
/* 303 */       System.arraycopy(Short2LongArrayMap.this.key, oldPos + 1, Short2LongArrayMap.this.key, oldPos, tail);
/* 304 */       System.arraycopy(Short2LongArrayMap.this.value, oldPos + 1, Short2LongArrayMap.this.value, oldPos, tail);
/* 305 */       Short2LongArrayMap.this.size--;
/* 306 */       return true;
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   public Short2LongMap.FastEntrySet short2LongEntrySet() {
/* 312 */     if (this.entries == null) this.entries = new EntrySet(); 
/* 313 */     return this.entries;
/*     */   }
/*     */   
/*     */   private int findKey(short k) {
/* 317 */     short[] key = this.key;
/* 318 */     for (int i = this.size; i-- != 0;) { if (key[i] == k) return i;  }
/* 319 */      return -1;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public long get(short k) {
/* 325 */     short[] key = this.key;
/* 326 */     for (int i = this.size; i-- != 0;) { if (key[i] == k) return this.value[i];  }
/* 327 */      return this.defRetValue;
/*     */   }
/*     */ 
/*     */   
/*     */   public int size() {
/* 332 */     return this.size;
/*     */   }
/*     */ 
/*     */   
/*     */   public void clear() {
/* 337 */     this.size = 0;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean containsKey(short k) {
/* 342 */     return (findKey(k) != -1);
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean containsValue(long v) {
/* 347 */     for (int i = this.size; i-- != 0;) { if (this.value[i] == v) return true;  }
/* 348 */      return false;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isEmpty() {
/* 353 */     return (this.size == 0);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public long put(short k, long v) {
/* 359 */     int oldKey = findKey(k);
/* 360 */     if (oldKey != -1) {
/* 361 */       long oldValue = this.value[oldKey];
/* 362 */       this.value[oldKey] = v;
/* 363 */       return oldValue;
/*     */     } 
/* 365 */     if (this.size == this.key.length) {
/* 366 */       short[] newKey = new short[(this.size == 0) ? 2 : (this.size * 2)];
/* 367 */       long[] newValue = new long[(this.size == 0) ? 2 : (this.size * 2)];
/* 368 */       for (int i = this.size; i-- != 0; ) {
/* 369 */         newKey[i] = this.key[i];
/* 370 */         newValue[i] = this.value[i];
/*     */       } 
/* 372 */       this.key = newKey;
/* 373 */       this.value = newValue;
/*     */     } 
/* 375 */     this.key[this.size] = k;
/* 376 */     this.value[this.size] = v;
/* 377 */     this.size++;
/* 378 */     return this.defRetValue;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public long remove(short k) {
/* 384 */     int oldPos = findKey(k);
/* 385 */     if (oldPos == -1) return this.defRetValue; 
/* 386 */     long oldValue = this.value[oldPos];
/* 387 */     int tail = this.size - oldPos - 1;
/* 388 */     System.arraycopy(this.key, oldPos + 1, this.key, oldPos, tail);
/* 389 */     System.arraycopy(this.value, oldPos + 1, this.value, oldPos, tail);
/* 390 */     this.size--;
/* 391 */     return oldValue;
/*     */   }
/*     */   
/*     */   private final class KeySet extends AbstractShortSet { private KeySet() {}
/*     */     
/*     */     public boolean contains(short k) {
/* 397 */       return (Short2LongArrayMap.this.findKey(k) != -1);
/*     */     }
/*     */ 
/*     */     
/*     */     public boolean remove(short k) {
/* 402 */       int oldPos = Short2LongArrayMap.this.findKey(k);
/* 403 */       if (oldPos == -1) return false; 
/* 404 */       int tail = Short2LongArrayMap.this.size - oldPos - 1;
/* 405 */       System.arraycopy(Short2LongArrayMap.this.key, oldPos + 1, Short2LongArrayMap.this.key, oldPos, tail);
/* 406 */       System.arraycopy(Short2LongArrayMap.this.value, oldPos + 1, Short2LongArrayMap.this.value, oldPos, tail);
/* 407 */       Short2LongArrayMap.this.size--;
/* 408 */       return true;
/*     */     }
/*     */ 
/*     */     
/*     */     public ShortIterator iterator() {
/* 413 */       return new ShortIterator() {
/* 414 */           int pos = 0;
/*     */ 
/*     */           
/*     */           public boolean hasNext() {
/* 418 */             return (this.pos < Short2LongArrayMap.this.size);
/*     */           }
/*     */ 
/*     */ 
/*     */           
/*     */           public short nextShort() {
/* 424 */             if (!hasNext()) throw new NoSuchElementException(); 
/* 425 */             return Short2LongArrayMap.this.key[this.pos++];
/*     */           }
/*     */ 
/*     */           
/*     */           public void remove() {
/* 430 */             if (this.pos == 0) throw new IllegalStateException(); 
/* 431 */             int tail = Short2LongArrayMap.this.size - this.pos;
/* 432 */             System.arraycopy(Short2LongArrayMap.this.key, this.pos, Short2LongArrayMap.this.key, this.pos - 1, tail);
/* 433 */             System.arraycopy(Short2LongArrayMap.this.value, this.pos, Short2LongArrayMap.this.value, this.pos - 1, tail);
/* 434 */             Short2LongArrayMap.this.size--;
/* 435 */             this.pos--;
/*     */           }
/*     */ 
/*     */ 
/*     */ 
/*     */           
/*     */           public void forEachRemaining(ShortConsumer action) {
/* 442 */             int max = Short2LongArrayMap.this.size;
/* 443 */             while (this.pos < max)
/* 444 */               action.accept(Short2LongArrayMap.this.key[this.pos++]); 
/*     */           }
/*     */         };
/*     */     }
/*     */     
/*     */     final class KeySetSpliterator
/*     */       extends ShortSpliterators.EarlyBindingSizeIndexBasedSpliterator
/*     */       implements ShortSpliterator {
/*     */       KeySetSpliterator(int pos, int maxPos) {
/* 453 */         super(pos, maxPos);
/*     */       }
/*     */ 
/*     */       
/*     */       public int characteristics() {
/* 458 */         return 16721;
/*     */       }
/*     */ 
/*     */ 
/*     */       
/*     */       protected final short get(int location) {
/* 464 */         return Short2LongArrayMap.this.key[location];
/*     */       }
/*     */ 
/*     */       
/*     */       protected final KeySetSpliterator makeForSplit(int pos, int maxPos) {
/* 469 */         return new KeySetSpliterator(pos, maxPos);
/*     */       }
/*     */ 
/*     */ 
/*     */ 
/*     */       
/*     */       public void forEachRemaining(ShortConsumer action) {
/* 476 */         int max = Short2LongArrayMap.this.size;
/* 477 */         while (this.pos < max) {
/* 478 */           action.accept(Short2LongArrayMap.this.key[this.pos++]);
/*     */         }
/*     */       }
/*     */     }
/*     */ 
/*     */     
/*     */     public ShortSpliterator spliterator() {
/* 485 */       return new KeySetSpliterator(0, Short2LongArrayMap.this.size);
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public void forEach(ShortConsumer action) {
/* 492 */       for (int i = 0, max = Short2LongArrayMap.this.size; i < max; i++) {
/* 493 */         action.accept(Short2LongArrayMap.this.key[i]);
/*     */       }
/*     */     }
/*     */ 
/*     */     
/*     */     public int size() {
/* 499 */       return Short2LongArrayMap.this.size;
/*     */     }
/*     */ 
/*     */     
/*     */     public void clear() {
/* 504 */       Short2LongArrayMap.this.clear();
/*     */     } }
/*     */ 
/*     */ 
/*     */   
/*     */   public ShortSet keySet() {
/* 510 */     if (this.keys == null) this.keys = new KeySet(); 
/* 511 */     return this.keys;
/*     */   }
/*     */   
/*     */   private final class ValuesCollection extends AbstractLongCollection { private ValuesCollection() {}
/*     */     
/*     */     public boolean contains(long v) {
/* 517 */       return Short2LongArrayMap.this.containsValue(v);
/*     */     }
/*     */ 
/*     */     
/*     */     public LongIterator iterator() {
/* 522 */       return new LongIterator() {
/* 523 */           int pos = 0;
/*     */ 
/*     */           
/*     */           public boolean hasNext() {
/* 527 */             return (this.pos < Short2LongArrayMap.this.size);
/*     */           }
/*     */ 
/*     */ 
/*     */           
/*     */           public long nextLong() {
/* 533 */             if (!hasNext()) throw new NoSuchElementException(); 
/* 534 */             return Short2LongArrayMap.this.value[this.pos++];
/*     */           }
/*     */ 
/*     */           
/*     */           public void remove() {
/* 539 */             if (this.pos == 0) throw new IllegalStateException(); 
/* 540 */             int tail = Short2LongArrayMap.this.size - this.pos;
/* 541 */             System.arraycopy(Short2LongArrayMap.this.key, this.pos, Short2LongArrayMap.this.key, this.pos - 1, tail);
/* 542 */             System.arraycopy(Short2LongArrayMap.this.value, this.pos, Short2LongArrayMap.this.value, this.pos - 1, tail);
/* 543 */             Short2LongArrayMap.this.size--;
/* 544 */             this.pos--;
/*     */           }
/*     */ 
/*     */ 
/*     */ 
/*     */           
/*     */           public void forEachRemaining(LongConsumer action) {
/* 551 */             int max = Short2LongArrayMap.this.size;
/* 552 */             while (this.pos < max)
/* 553 */               action.accept(Short2LongArrayMap.this.value[this.pos++]); 
/*     */           }
/*     */         };
/*     */     }
/*     */     
/*     */     final class ValuesSpliterator
/*     */       extends LongSpliterators.EarlyBindingSizeIndexBasedSpliterator
/*     */       implements LongSpliterator {
/*     */       ValuesSpliterator(int pos, int maxPos) {
/* 562 */         super(pos, maxPos);
/*     */       }
/*     */ 
/*     */       
/*     */       public int characteristics() {
/* 567 */         return 16720;
/*     */       }
/*     */ 
/*     */ 
/*     */       
/*     */       protected final long get(int location) {
/* 573 */         return Short2LongArrayMap.this.value[location];
/*     */       }
/*     */ 
/*     */       
/*     */       protected final ValuesSpliterator makeForSplit(int pos, int maxPos) {
/* 578 */         return new ValuesSpliterator(pos, maxPos);
/*     */       }
/*     */ 
/*     */ 
/*     */ 
/*     */       
/*     */       public void forEachRemaining(LongConsumer action) {
/* 585 */         int max = Short2LongArrayMap.this.size;
/* 586 */         while (this.pos < max) {
/* 587 */           action.accept(Short2LongArrayMap.this.value[this.pos++]);
/*     */         }
/*     */       }
/*     */     }
/*     */ 
/*     */     
/*     */     public LongSpliterator spliterator() {
/* 594 */       return new ValuesSpliterator(0, Short2LongArrayMap.this.size);
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public void forEach(LongConsumer action) {
/* 601 */       for (int i = 0, max = Short2LongArrayMap.this.size; i < max; i++) {
/* 602 */         action.accept(Short2LongArrayMap.this.value[i]);
/*     */       }
/*     */     }
/*     */ 
/*     */     
/*     */     public int size() {
/* 608 */       return Short2LongArrayMap.this.size;
/*     */     }
/*     */ 
/*     */     
/*     */     public void clear() {
/* 613 */       Short2LongArrayMap.this.clear();
/*     */     } }
/*     */ 
/*     */ 
/*     */   
/*     */   public LongCollection values() {
/* 619 */     if (this.values == null) this.values = (LongCollection)new ValuesCollection(); 
/* 620 */     return this.values;
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
/*     */   public Short2LongArrayMap clone() {
/*     */     Short2LongArrayMap c;
/*     */     try {
/* 637 */       c = (Short2LongArrayMap)super.clone();
/* 638 */     } catch (CloneNotSupportedException cantHappen) {
/* 639 */       throw new InternalError();
/*     */     } 
/* 641 */     c.key = (short[])this.key.clone();
/* 642 */     c.value = (long[])this.value.clone();
/* 643 */     c.entries = null;
/* 644 */     c.keys = null;
/* 645 */     c.values = null;
/* 646 */     return c;
/*     */   }
/*     */   
/*     */   private void writeObject(ObjectOutputStream s) throws IOException {
/* 650 */     s.defaultWriteObject();
/* 651 */     for (int i = 0, max = this.size; i < max; i++) {
/* 652 */       s.writeShort(this.key[i]);
/* 653 */       s.writeLong(this.value[i]);
/*     */     } 
/*     */   }
/*     */   
/*     */   private void readObject(ObjectInputStream s) throws IOException, ClassNotFoundException {
/* 658 */     s.defaultReadObject();
/* 659 */     this.key = new short[this.size];
/* 660 */     this.value = new long[this.size];
/* 661 */     for (int i = 0; i < this.size; i++) {
/* 662 */       this.key[i] = s.readShort();
/* 663 */       this.value[i] = s.readLong();
/*     */     } 
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\i\\unimi\dsi\fastutil\shorts\Short2LongArrayMap.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */