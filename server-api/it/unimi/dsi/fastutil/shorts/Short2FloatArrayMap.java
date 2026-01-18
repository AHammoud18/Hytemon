/*     */ package it.unimi.dsi.fastutil.shorts;
/*     */ 
/*     */ import it.unimi.dsi.fastutil.floats.AbstractFloatCollection;
/*     */ import it.unimi.dsi.fastutil.floats.FloatArrays;
/*     */ import it.unimi.dsi.fastutil.floats.FloatCollection;
/*     */ import it.unimi.dsi.fastutil.floats.FloatConsumer;
/*     */ import it.unimi.dsi.fastutil.floats.FloatIterator;
/*     */ import it.unimi.dsi.fastutil.floats.FloatSpliterator;
/*     */ import it.unimi.dsi.fastutil.floats.FloatSpliterators;
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
/*     */ public class Short2FloatArrayMap
/*     */   extends AbstractShort2FloatMap
/*     */   implements Serializable, Cloneable
/*     */ {
/*     */   private static final long serialVersionUID = 1L;
/*     */   protected transient short[] key;
/*     */   protected transient float[] value;
/*     */   protected int size;
/*     */   protected transient Short2FloatMap.FastEntrySet entries;
/*     */   protected transient ShortSet keys;
/*     */   protected transient FloatCollection values;
/*     */   
/*     */   public Short2FloatArrayMap(short[] key, float[] value) {
/*  64 */     this.key = key;
/*  65 */     this.value = value;
/*  66 */     this.size = key.length;
/*  67 */     if (key.length != value.length) throw new IllegalArgumentException("Keys and values have different lengths (" + key.length + ", " + value.length + ")");
/*     */   
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public Short2FloatArrayMap() {
/*  74 */     this.key = ShortArrays.EMPTY_ARRAY;
/*  75 */     this.value = FloatArrays.EMPTY_ARRAY;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Short2FloatArrayMap(int capacity) {
/*  84 */     this.key = new short[capacity];
/*  85 */     this.value = new float[capacity];
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Short2FloatArrayMap(Short2FloatMap m) {
/*  94 */     this(m.size());
/*  95 */     int i = 0;
/*  96 */     for (ObjectIterator<Short2FloatMap.Entry> objectIterator = m.short2FloatEntrySet().iterator(); objectIterator.hasNext(); ) { Short2FloatMap.Entry e = objectIterator.next();
/*  97 */       this.key[i] = e.getShortKey();
/*  98 */       this.value[i] = e.getFloatValue();
/*  99 */       i++; }
/*     */     
/* 101 */     this.size = i;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Short2FloatArrayMap(Map<? extends Short, ? extends Float> m) {
/* 110 */     this(m.size());
/* 111 */     int i = 0;
/* 112 */     for (Map.Entry<? extends Short, ? extends Float> e : m.entrySet()) {
/* 113 */       this.key[i] = ((Short)e.getKey()).shortValue();
/* 114 */       this.value[i] = ((Float)e.getValue()).floatValue();
/* 115 */       i++;
/*     */     } 
/* 117 */     this.size = i;
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
/*     */   public Short2FloatArrayMap(short[] key, float[] value, int size) {
/* 133 */     this.key = key;
/* 134 */     this.value = value;
/* 135 */     this.size = size;
/* 136 */     if (key.length != value.length) throw new IllegalArgumentException("Keys and values have different lengths (" + key.length + ", " + value.length + ")"); 
/* 137 */     if (size > key.length) throw new IllegalArgumentException("The provided size (" + size + ") is larger than or equal to the backing-arrays size (" + key.length + ")"); 
/*     */   }
/*     */   
/*     */   private final class EntrySet
/*     */     extends AbstractObjectSet<Short2FloatMap.Entry> implements Short2FloatMap.FastEntrySet {
/*     */     private EntrySet() {}
/*     */     
/*     */     public ObjectIterator<Short2FloatMap.Entry> iterator() {
/* 145 */       return new ObjectIterator<Short2FloatMap.Entry>() {
/* 146 */           int curr = -1; int next = 0;
/*     */ 
/*     */           
/*     */           public boolean hasNext() {
/* 150 */             return (this.next < Short2FloatArrayMap.this.size);
/*     */           }
/*     */ 
/*     */ 
/*     */           
/*     */           public Short2FloatMap.Entry next() {
/* 156 */             if (!hasNext()) throw new NoSuchElementException(); 
/* 157 */             return new AbstractShort2FloatMap.BasicEntry(Short2FloatArrayMap.this.key[this.curr = this.next], Short2FloatArrayMap.this.value[this.next++]);
/*     */           }
/*     */ 
/*     */           
/*     */           public void remove() {
/* 162 */             if (this.curr == -1) throw new IllegalStateException(); 
/* 163 */             this.curr = -1;
/* 164 */             int tail = Short2FloatArrayMap.this.size-- - this.next--;
/* 165 */             System.arraycopy(Short2FloatArrayMap.this.key, this.next + 1, Short2FloatArrayMap.this.key, this.next, tail);
/* 166 */             System.arraycopy(Short2FloatArrayMap.this.value, this.next + 1, Short2FloatArrayMap.this.value, this.next, tail);
/*     */           }
/*     */ 
/*     */ 
/*     */ 
/*     */           
/*     */           public void forEachRemaining(Consumer<? super Short2FloatMap.Entry> action) {
/* 173 */             int max = Short2FloatArrayMap.this.size;
/* 174 */             while (this.next < max) {
/* 175 */               action.accept(new AbstractShort2FloatMap.BasicEntry(Short2FloatArrayMap.this.key[this.curr = this.next], Short2FloatArrayMap.this.value[this.next++]));
/*     */             }
/*     */           }
/*     */         };
/*     */     }
/*     */ 
/*     */     
/*     */     public ObjectIterator<Short2FloatMap.Entry> fastIterator() {
/* 183 */       return new ObjectIterator<Short2FloatMap.Entry>() {
/* 184 */           int next = 0; int curr = -1;
/* 185 */           final AbstractShort2FloatMap.BasicEntry entry = new AbstractShort2FloatMap.BasicEntry();
/*     */ 
/*     */           
/*     */           public boolean hasNext() {
/* 189 */             return (this.next < Short2FloatArrayMap.this.size);
/*     */           }
/*     */ 
/*     */ 
/*     */           
/*     */           public Short2FloatMap.Entry next() {
/* 195 */             if (!hasNext()) throw new NoSuchElementException(); 
/* 196 */             this.entry.key = Short2FloatArrayMap.this.key[this.curr = this.next];
/* 197 */             this.entry.value = Short2FloatArrayMap.this.value[this.next++];
/* 198 */             return this.entry;
/*     */           }
/*     */ 
/*     */           
/*     */           public void remove() {
/* 203 */             if (this.curr == -1) throw new IllegalStateException(); 
/* 204 */             this.curr = -1;
/* 205 */             int tail = Short2FloatArrayMap.this.size-- - this.next--;
/* 206 */             System.arraycopy(Short2FloatArrayMap.this.key, this.next + 1, Short2FloatArrayMap.this.key, this.next, tail);
/* 207 */             System.arraycopy(Short2FloatArrayMap.this.value, this.next + 1, Short2FloatArrayMap.this.value, this.next, tail);
/*     */           }
/*     */ 
/*     */ 
/*     */ 
/*     */           
/*     */           public void forEachRemaining(Consumer<? super Short2FloatMap.Entry> action) {
/* 214 */             int max = Short2FloatArrayMap.this.size;
/* 215 */             while (this.next < max) {
/* 216 */               this.entry.key = Short2FloatArrayMap.this.key[this.curr = this.next];
/* 217 */               this.entry.value = Short2FloatArrayMap.this.value[this.next++];
/* 218 */               action.accept(this.entry);
/*     */             } 
/*     */           }
/*     */         };
/*     */     }
/*     */     
/*     */     final class EntrySetSpliterator
/*     */       extends ObjectSpliterators.EarlyBindingSizeIndexBasedSpliterator<Short2FloatMap.Entry>
/*     */       implements ObjectSpliterator<Short2FloatMap.Entry> {
/*     */       EntrySetSpliterator(int pos, int maxPos) {
/* 228 */         super(pos, maxPos);
/*     */       }
/*     */ 
/*     */       
/*     */       public int characteristics() {
/* 233 */         return 16465;
/*     */       }
/*     */ 
/*     */ 
/*     */       
/*     */       protected final Short2FloatMap.Entry get(int location) {
/* 239 */         return new AbstractShort2FloatMap.BasicEntry(Short2FloatArrayMap.this.key[location], Short2FloatArrayMap.this.value[location]);
/*     */       }
/*     */ 
/*     */       
/*     */       protected final EntrySetSpliterator makeForSplit(int pos, int maxPos) {
/* 244 */         return new EntrySetSpliterator(pos, maxPos);
/*     */       }
/*     */     }
/*     */ 
/*     */     
/*     */     public ObjectSpliterator<Short2FloatMap.Entry> spliterator() {
/* 250 */       return new EntrySetSpliterator(0, Short2FloatArrayMap.this.size);
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public void forEach(Consumer<? super Short2FloatMap.Entry> action) {
/* 258 */       for (int i = 0, max = Short2FloatArrayMap.this.size; i < max; i++) {
/* 259 */         action.accept(new AbstractShort2FloatMap.BasicEntry(Short2FloatArrayMap.this.key[i], Short2FloatArrayMap.this.value[i]));
/*     */       }
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public void fastForEach(Consumer<? super Short2FloatMap.Entry> action) {
/* 267 */       AbstractShort2FloatMap.BasicEntry entry = new AbstractShort2FloatMap.BasicEntry();
/*     */       
/* 269 */       for (int i = 0, max = Short2FloatArrayMap.this.size; i < max; i++) {
/* 270 */         entry.key = Short2FloatArrayMap.this.key[i];
/* 271 */         entry.value = Short2FloatArrayMap.this.value[i];
/* 272 */         action.accept(entry);
/*     */       } 
/*     */     }
/*     */ 
/*     */     
/*     */     public int size() {
/* 278 */       return Short2FloatArrayMap.this.size;
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     public boolean contains(Object o) {
/* 284 */       if (!(o instanceof Map.Entry)) return false; 
/* 285 */       Map.Entry<?, ?> e = (Map.Entry<?, ?>)o;
/* 286 */       if (e.getKey() == null || !(e.getKey() instanceof Short)) return false; 
/* 287 */       if (e.getValue() == null || !(e.getValue() instanceof Float)) return false; 
/* 288 */       short k = ((Short)e.getKey()).shortValue();
/* 289 */       return (Short2FloatArrayMap.this.containsKey(k) && Float.floatToIntBits(Short2FloatArrayMap.this.get(k)) == Float.floatToIntBits(((Float)e.getValue()).floatValue()));
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     public boolean remove(Object o) {
/* 295 */       if (!(o instanceof Map.Entry)) return false; 
/* 296 */       Map.Entry<?, ?> e = (Map.Entry<?, ?>)o;
/* 297 */       if (e.getKey() == null || !(e.getKey() instanceof Short)) return false; 
/* 298 */       if (e.getValue() == null || !(e.getValue() instanceof Float)) return false; 
/* 299 */       short k = ((Short)e.getKey()).shortValue();
/* 300 */       float v = ((Float)e.getValue()).floatValue();
/* 301 */       int oldPos = Short2FloatArrayMap.this.findKey(k);
/* 302 */       if (oldPos == -1 || Float.floatToIntBits(v) != Float.floatToIntBits(Short2FloatArrayMap.this.value[oldPos])) return false; 
/* 303 */       int tail = Short2FloatArrayMap.this.size - oldPos - 1;
/* 304 */       System.arraycopy(Short2FloatArrayMap.this.key, oldPos + 1, Short2FloatArrayMap.this.key, oldPos, tail);
/* 305 */       System.arraycopy(Short2FloatArrayMap.this.value, oldPos + 1, Short2FloatArrayMap.this.value, oldPos, tail);
/* 306 */       Short2FloatArrayMap.this.size--;
/* 307 */       return true;
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   public Short2FloatMap.FastEntrySet short2FloatEntrySet() {
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
/*     */   public float get(short k) {
/* 326 */     short[] key = this.key;
/* 327 */     for (int i = this.size; i-- != 0;) { if (key[i] == k) return this.value[i];  }
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
/* 338 */     this.size = 0;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean containsKey(short k) {
/* 343 */     return (findKey(k) != -1);
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean containsValue(float v) {
/* 348 */     for (int i = this.size; i-- != 0;) { if (Float.floatToIntBits(this.value[i]) == Float.floatToIntBits(v)) return true;  }
/* 349 */      return false;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isEmpty() {
/* 354 */     return (this.size == 0);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public float put(short k, float v) {
/* 360 */     int oldKey = findKey(k);
/* 361 */     if (oldKey != -1) {
/* 362 */       float oldValue = this.value[oldKey];
/* 363 */       this.value[oldKey] = v;
/* 364 */       return oldValue;
/*     */     } 
/* 366 */     if (this.size == this.key.length) {
/* 367 */       short[] newKey = new short[(this.size == 0) ? 2 : (this.size * 2)];
/* 368 */       float[] newValue = new float[(this.size == 0) ? 2 : (this.size * 2)];
/* 369 */       for (int i = this.size; i-- != 0; ) {
/* 370 */         newKey[i] = this.key[i];
/* 371 */         newValue[i] = this.value[i];
/*     */       } 
/* 373 */       this.key = newKey;
/* 374 */       this.value = newValue;
/*     */     } 
/* 376 */     this.key[this.size] = k;
/* 377 */     this.value[this.size] = v;
/* 378 */     this.size++;
/* 379 */     return this.defRetValue;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public float remove(short k) {
/* 385 */     int oldPos = findKey(k);
/* 386 */     if (oldPos == -1) return this.defRetValue; 
/* 387 */     float oldValue = this.value[oldPos];
/* 388 */     int tail = this.size - oldPos - 1;
/* 389 */     System.arraycopy(this.key, oldPos + 1, this.key, oldPos, tail);
/* 390 */     System.arraycopy(this.value, oldPos + 1, this.value, oldPos, tail);
/* 391 */     this.size--;
/* 392 */     return oldValue;
/*     */   }
/*     */   
/*     */   private final class KeySet extends AbstractShortSet { private KeySet() {}
/*     */     
/*     */     public boolean contains(short k) {
/* 398 */       return (Short2FloatArrayMap.this.findKey(k) != -1);
/*     */     }
/*     */ 
/*     */     
/*     */     public boolean remove(short k) {
/* 403 */       int oldPos = Short2FloatArrayMap.this.findKey(k);
/* 404 */       if (oldPos == -1) return false; 
/* 405 */       int tail = Short2FloatArrayMap.this.size - oldPos - 1;
/* 406 */       System.arraycopy(Short2FloatArrayMap.this.key, oldPos + 1, Short2FloatArrayMap.this.key, oldPos, tail);
/* 407 */       System.arraycopy(Short2FloatArrayMap.this.value, oldPos + 1, Short2FloatArrayMap.this.value, oldPos, tail);
/* 408 */       Short2FloatArrayMap.this.size--;
/* 409 */       return true;
/*     */     }
/*     */ 
/*     */     
/*     */     public ShortIterator iterator() {
/* 414 */       return new ShortIterator() {
/* 415 */           int pos = 0;
/*     */ 
/*     */           
/*     */           public boolean hasNext() {
/* 419 */             return (this.pos < Short2FloatArrayMap.this.size);
/*     */           }
/*     */ 
/*     */ 
/*     */           
/*     */           public short nextShort() {
/* 425 */             if (!hasNext()) throw new NoSuchElementException(); 
/* 426 */             return Short2FloatArrayMap.this.key[this.pos++];
/*     */           }
/*     */ 
/*     */           
/*     */           public void remove() {
/* 431 */             if (this.pos == 0) throw new IllegalStateException(); 
/* 432 */             int tail = Short2FloatArrayMap.this.size - this.pos;
/* 433 */             System.arraycopy(Short2FloatArrayMap.this.key, this.pos, Short2FloatArrayMap.this.key, this.pos - 1, tail);
/* 434 */             System.arraycopy(Short2FloatArrayMap.this.value, this.pos, Short2FloatArrayMap.this.value, this.pos - 1, tail);
/* 435 */             Short2FloatArrayMap.this.size--;
/* 436 */             this.pos--;
/*     */           }
/*     */ 
/*     */ 
/*     */ 
/*     */           
/*     */           public void forEachRemaining(ShortConsumer action) {
/* 443 */             int max = Short2FloatArrayMap.this.size;
/* 444 */             while (this.pos < max)
/* 445 */               action.accept(Short2FloatArrayMap.this.key[this.pos++]); 
/*     */           }
/*     */         };
/*     */     }
/*     */     
/*     */     final class KeySetSpliterator
/*     */       extends ShortSpliterators.EarlyBindingSizeIndexBasedSpliterator
/*     */       implements ShortSpliterator {
/*     */       KeySetSpliterator(int pos, int maxPos) {
/* 454 */         super(pos, maxPos);
/*     */       }
/*     */ 
/*     */       
/*     */       public int characteristics() {
/* 459 */         return 16721;
/*     */       }
/*     */ 
/*     */ 
/*     */       
/*     */       protected final short get(int location) {
/* 465 */         return Short2FloatArrayMap.this.key[location];
/*     */       }
/*     */ 
/*     */       
/*     */       protected final KeySetSpliterator makeForSplit(int pos, int maxPos) {
/* 470 */         return new KeySetSpliterator(pos, maxPos);
/*     */       }
/*     */ 
/*     */ 
/*     */ 
/*     */       
/*     */       public void forEachRemaining(ShortConsumer action) {
/* 477 */         int max = Short2FloatArrayMap.this.size;
/* 478 */         while (this.pos < max) {
/* 479 */           action.accept(Short2FloatArrayMap.this.key[this.pos++]);
/*     */         }
/*     */       }
/*     */     }
/*     */ 
/*     */     
/*     */     public ShortSpliterator spliterator() {
/* 486 */       return new KeySetSpliterator(0, Short2FloatArrayMap.this.size);
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public void forEach(ShortConsumer action) {
/* 493 */       for (int i = 0, max = Short2FloatArrayMap.this.size; i < max; i++) {
/* 494 */         action.accept(Short2FloatArrayMap.this.key[i]);
/*     */       }
/*     */     }
/*     */ 
/*     */     
/*     */     public int size() {
/* 500 */       return Short2FloatArrayMap.this.size;
/*     */     }
/*     */ 
/*     */     
/*     */     public void clear() {
/* 505 */       Short2FloatArrayMap.this.clear();
/*     */     } }
/*     */ 
/*     */ 
/*     */   
/*     */   public ShortSet keySet() {
/* 511 */     if (this.keys == null) this.keys = new KeySet(); 
/* 512 */     return this.keys;
/*     */   }
/*     */   
/*     */   private final class ValuesCollection extends AbstractFloatCollection { private ValuesCollection() {}
/*     */     
/*     */     public boolean contains(float v) {
/* 518 */       return Short2FloatArrayMap.this.containsValue(v);
/*     */     }
/*     */ 
/*     */     
/*     */     public FloatIterator iterator() {
/* 523 */       return new FloatIterator() {
/* 524 */           int pos = 0;
/*     */ 
/*     */           
/*     */           public boolean hasNext() {
/* 528 */             return (this.pos < Short2FloatArrayMap.this.size);
/*     */           }
/*     */ 
/*     */ 
/*     */           
/*     */           public float nextFloat() {
/* 534 */             if (!hasNext()) throw new NoSuchElementException(); 
/* 535 */             return Short2FloatArrayMap.this.value[this.pos++];
/*     */           }
/*     */ 
/*     */           
/*     */           public void remove() {
/* 540 */             if (this.pos == 0) throw new IllegalStateException(); 
/* 541 */             int tail = Short2FloatArrayMap.this.size - this.pos;
/* 542 */             System.arraycopy(Short2FloatArrayMap.this.key, this.pos, Short2FloatArrayMap.this.key, this.pos - 1, tail);
/* 543 */             System.arraycopy(Short2FloatArrayMap.this.value, this.pos, Short2FloatArrayMap.this.value, this.pos - 1, tail);
/* 544 */             Short2FloatArrayMap.this.size--;
/* 545 */             this.pos--;
/*     */           }
/*     */ 
/*     */ 
/*     */ 
/*     */           
/*     */           public void forEachRemaining(FloatConsumer action) {
/* 552 */             int max = Short2FloatArrayMap.this.size;
/* 553 */             while (this.pos < max)
/* 554 */               action.accept(Short2FloatArrayMap.this.value[this.pos++]); 
/*     */           }
/*     */         };
/*     */     }
/*     */     
/*     */     final class ValuesSpliterator
/*     */       extends FloatSpliterators.EarlyBindingSizeIndexBasedSpliterator
/*     */       implements FloatSpliterator {
/*     */       ValuesSpliterator(int pos, int maxPos) {
/* 563 */         super(pos, maxPos);
/*     */       }
/*     */ 
/*     */       
/*     */       public int characteristics() {
/* 568 */         return 16720;
/*     */       }
/*     */ 
/*     */ 
/*     */       
/*     */       protected final float get(int location) {
/* 574 */         return Short2FloatArrayMap.this.value[location];
/*     */       }
/*     */ 
/*     */       
/*     */       protected final ValuesSpliterator makeForSplit(int pos, int maxPos) {
/* 579 */         return new ValuesSpliterator(pos, maxPos);
/*     */       }
/*     */ 
/*     */ 
/*     */ 
/*     */       
/*     */       public void forEachRemaining(FloatConsumer action) {
/* 586 */         int max = Short2FloatArrayMap.this.size;
/* 587 */         while (this.pos < max) {
/* 588 */           action.accept(Short2FloatArrayMap.this.value[this.pos++]);
/*     */         }
/*     */       }
/*     */     }
/*     */ 
/*     */     
/*     */     public FloatSpliterator spliterator() {
/* 595 */       return new ValuesSpliterator(0, Short2FloatArrayMap.this.size);
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public void forEach(FloatConsumer action) {
/* 602 */       for (int i = 0, max = Short2FloatArrayMap.this.size; i < max; i++) {
/* 603 */         action.accept(Short2FloatArrayMap.this.value[i]);
/*     */       }
/*     */     }
/*     */ 
/*     */     
/*     */     public int size() {
/* 609 */       return Short2FloatArrayMap.this.size;
/*     */     }
/*     */ 
/*     */     
/*     */     public void clear() {
/* 614 */       Short2FloatArrayMap.this.clear();
/*     */     } }
/*     */ 
/*     */ 
/*     */   
/*     */   public FloatCollection values() {
/* 620 */     if (this.values == null) this.values = (FloatCollection)new ValuesCollection(); 
/* 621 */     return this.values;
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
/*     */   public Short2FloatArrayMap clone() {
/*     */     Short2FloatArrayMap c;
/*     */     try {
/* 638 */       c = (Short2FloatArrayMap)super.clone();
/* 639 */     } catch (CloneNotSupportedException cantHappen) {
/* 640 */       throw new InternalError();
/*     */     } 
/* 642 */     c.key = (short[])this.key.clone();
/* 643 */     c.value = (float[])this.value.clone();
/* 644 */     c.entries = null;
/* 645 */     c.keys = null;
/* 646 */     c.values = null;
/* 647 */     return c;
/*     */   }
/*     */   
/*     */   private void writeObject(ObjectOutputStream s) throws IOException {
/* 651 */     s.defaultWriteObject();
/* 652 */     for (int i = 0, max = this.size; i < max; i++) {
/* 653 */       s.writeShort(this.key[i]);
/* 654 */       s.writeFloat(this.value[i]);
/*     */     } 
/*     */   }
/*     */   
/*     */   private void readObject(ObjectInputStream s) throws IOException, ClassNotFoundException {
/* 659 */     s.defaultReadObject();
/* 660 */     this.key = new short[this.size];
/* 661 */     this.value = new float[this.size];
/* 662 */     for (int i = 0; i < this.size; i++) {
/* 663 */       this.key[i] = s.readShort();
/* 664 */       this.value[i] = s.readFloat();
/*     */     } 
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\i\\unimi\dsi\fastutil\shorts\Short2FloatArrayMap.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */