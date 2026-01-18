/*     */ package it.unimi.dsi.fastutil.floats;
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
/*     */ public class Float2FloatArrayMap
/*     */   extends AbstractFloat2FloatMap
/*     */   implements Serializable, Cloneable
/*     */ {
/*     */   private static final long serialVersionUID = 1L;
/*     */   protected transient float[] key;
/*     */   protected transient float[] value;
/*     */   protected int size;
/*     */   protected transient Float2FloatMap.FastEntrySet entries;
/*     */   protected transient FloatSet keys;
/*     */   protected transient FloatCollection values;
/*     */   
/*     */   public Float2FloatArrayMap(float[] key, float[] value) {
/*  60 */     this.key = key;
/*  61 */     this.value = value;
/*  62 */     this.size = key.length;
/*  63 */     if (key.length != value.length) throw new IllegalArgumentException("Keys and values have different lengths (" + key.length + ", " + value.length + ")");
/*     */   
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public Float2FloatArrayMap() {
/*  70 */     this.key = FloatArrays.EMPTY_ARRAY;
/*  71 */     this.value = FloatArrays.EMPTY_ARRAY;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Float2FloatArrayMap(int capacity) {
/*  80 */     this.key = new float[capacity];
/*  81 */     this.value = new float[capacity];
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Float2FloatArrayMap(Float2FloatMap m) {
/*  90 */     this(m.size());
/*  91 */     int i = 0;
/*  92 */     for (ObjectIterator<Float2FloatMap.Entry> objectIterator = m.float2FloatEntrySet().iterator(); objectIterator.hasNext(); ) { Float2FloatMap.Entry e = objectIterator.next();
/*  93 */       this.key[i] = e.getFloatKey();
/*  94 */       this.value[i] = e.getFloatValue();
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
/*     */   public Float2FloatArrayMap(Map<? extends Float, ? extends Float> m) {
/* 106 */     this(m.size());
/* 107 */     int i = 0;
/* 108 */     for (Map.Entry<? extends Float, ? extends Float> e : m.entrySet()) {
/* 109 */       this.key[i] = ((Float)e.getKey()).floatValue();
/* 110 */       this.value[i] = ((Float)e.getValue()).floatValue();
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
/*     */   public Float2FloatArrayMap(float[] key, float[] value, int size) {
/* 129 */     this.key = key;
/* 130 */     this.value = value;
/* 131 */     this.size = size;
/* 132 */     if (key.length != value.length) throw new IllegalArgumentException("Keys and values have different lengths (" + key.length + ", " + value.length + ")"); 
/* 133 */     if (size > key.length) throw new IllegalArgumentException("The provided size (" + size + ") is larger than or equal to the backing-arrays size (" + key.length + ")"); 
/*     */   }
/*     */   
/*     */   private final class EntrySet
/*     */     extends AbstractObjectSet<Float2FloatMap.Entry> implements Float2FloatMap.FastEntrySet {
/*     */     private EntrySet() {}
/*     */     
/*     */     public ObjectIterator<Float2FloatMap.Entry> iterator() {
/* 141 */       return new ObjectIterator<Float2FloatMap.Entry>() {
/* 142 */           int curr = -1; int next = 0;
/*     */ 
/*     */           
/*     */           public boolean hasNext() {
/* 146 */             return (this.next < Float2FloatArrayMap.this.size);
/*     */           }
/*     */ 
/*     */ 
/*     */           
/*     */           public Float2FloatMap.Entry next() {
/* 152 */             if (!hasNext()) throw new NoSuchElementException(); 
/* 153 */             return new AbstractFloat2FloatMap.BasicEntry(Float2FloatArrayMap.this.key[this.curr = this.next], Float2FloatArrayMap.this.value[this.next++]);
/*     */           }
/*     */ 
/*     */           
/*     */           public void remove() {
/* 158 */             if (this.curr == -1) throw new IllegalStateException(); 
/* 159 */             this.curr = -1;
/* 160 */             int tail = Float2FloatArrayMap.this.size-- - this.next--;
/* 161 */             System.arraycopy(Float2FloatArrayMap.this.key, this.next + 1, Float2FloatArrayMap.this.key, this.next, tail);
/* 162 */             System.arraycopy(Float2FloatArrayMap.this.value, this.next + 1, Float2FloatArrayMap.this.value, this.next, tail);
/*     */           }
/*     */ 
/*     */ 
/*     */ 
/*     */           
/*     */           public void forEachRemaining(Consumer<? super Float2FloatMap.Entry> action) {
/* 169 */             int max = Float2FloatArrayMap.this.size;
/* 170 */             while (this.next < max) {
/* 171 */               action.accept(new AbstractFloat2FloatMap.BasicEntry(Float2FloatArrayMap.this.key[this.curr = this.next], Float2FloatArrayMap.this.value[this.next++]));
/*     */             }
/*     */           }
/*     */         };
/*     */     }
/*     */ 
/*     */     
/*     */     public ObjectIterator<Float2FloatMap.Entry> fastIterator() {
/* 179 */       return new ObjectIterator<Float2FloatMap.Entry>() {
/* 180 */           int next = 0; int curr = -1;
/* 181 */           final AbstractFloat2FloatMap.BasicEntry entry = new AbstractFloat2FloatMap.BasicEntry();
/*     */ 
/*     */           
/*     */           public boolean hasNext() {
/* 185 */             return (this.next < Float2FloatArrayMap.this.size);
/*     */           }
/*     */ 
/*     */ 
/*     */           
/*     */           public Float2FloatMap.Entry next() {
/* 191 */             if (!hasNext()) throw new NoSuchElementException(); 
/* 192 */             this.entry.key = Float2FloatArrayMap.this.key[this.curr = this.next];
/* 193 */             this.entry.value = Float2FloatArrayMap.this.value[this.next++];
/* 194 */             return this.entry;
/*     */           }
/*     */ 
/*     */           
/*     */           public void remove() {
/* 199 */             if (this.curr == -1) throw new IllegalStateException(); 
/* 200 */             this.curr = -1;
/* 201 */             int tail = Float2FloatArrayMap.this.size-- - this.next--;
/* 202 */             System.arraycopy(Float2FloatArrayMap.this.key, this.next + 1, Float2FloatArrayMap.this.key, this.next, tail);
/* 203 */             System.arraycopy(Float2FloatArrayMap.this.value, this.next + 1, Float2FloatArrayMap.this.value, this.next, tail);
/*     */           }
/*     */ 
/*     */ 
/*     */ 
/*     */           
/*     */           public void forEachRemaining(Consumer<? super Float2FloatMap.Entry> action) {
/* 210 */             int max = Float2FloatArrayMap.this.size;
/* 211 */             while (this.next < max) {
/* 212 */               this.entry.key = Float2FloatArrayMap.this.key[this.curr = this.next];
/* 213 */               this.entry.value = Float2FloatArrayMap.this.value[this.next++];
/* 214 */               action.accept(this.entry);
/*     */             } 
/*     */           }
/*     */         };
/*     */     }
/*     */     
/*     */     final class EntrySetSpliterator
/*     */       extends ObjectSpliterators.EarlyBindingSizeIndexBasedSpliterator<Float2FloatMap.Entry>
/*     */       implements ObjectSpliterator<Float2FloatMap.Entry> {
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
/*     */       protected final Float2FloatMap.Entry get(int location) {
/* 235 */         return new AbstractFloat2FloatMap.BasicEntry(Float2FloatArrayMap.this.key[location], Float2FloatArrayMap.this.value[location]);
/*     */       }
/*     */ 
/*     */       
/*     */       protected final EntrySetSpliterator makeForSplit(int pos, int maxPos) {
/* 240 */         return new EntrySetSpliterator(pos, maxPos);
/*     */       }
/*     */     }
/*     */ 
/*     */     
/*     */     public ObjectSpliterator<Float2FloatMap.Entry> spliterator() {
/* 246 */       return new EntrySetSpliterator(0, Float2FloatArrayMap.this.size);
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public void forEach(Consumer<? super Float2FloatMap.Entry> action) {
/* 254 */       for (int i = 0, max = Float2FloatArrayMap.this.size; i < max; i++) {
/* 255 */         action.accept(new AbstractFloat2FloatMap.BasicEntry(Float2FloatArrayMap.this.key[i], Float2FloatArrayMap.this.value[i]));
/*     */       }
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public void fastForEach(Consumer<? super Float2FloatMap.Entry> action) {
/* 263 */       AbstractFloat2FloatMap.BasicEntry entry = new AbstractFloat2FloatMap.BasicEntry();
/*     */       
/* 265 */       for (int i = 0, max = Float2FloatArrayMap.this.size; i < max; i++) {
/* 266 */         entry.key = Float2FloatArrayMap.this.key[i];
/* 267 */         entry.value = Float2FloatArrayMap.this.value[i];
/* 268 */         action.accept(entry);
/*     */       } 
/*     */     }
/*     */ 
/*     */     
/*     */     public int size() {
/* 274 */       return Float2FloatArrayMap.this.size;
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     public boolean contains(Object o) {
/* 280 */       if (!(o instanceof Map.Entry)) return false; 
/* 281 */       Map.Entry<?, ?> e = (Map.Entry<?, ?>)o;
/* 282 */       if (e.getKey() == null || !(e.getKey() instanceof Float)) return false; 
/* 283 */       if (e.getValue() == null || !(e.getValue() instanceof Float)) return false; 
/* 284 */       float k = ((Float)e.getKey()).floatValue();
/* 285 */       return (Float2FloatArrayMap.this.containsKey(k) && Float.floatToIntBits(Float2FloatArrayMap.this.get(k)) == Float.floatToIntBits(((Float)e.getValue()).floatValue()));
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     public boolean remove(Object o) {
/* 291 */       if (!(o instanceof Map.Entry)) return false; 
/* 292 */       Map.Entry<?, ?> e = (Map.Entry<?, ?>)o;
/* 293 */       if (e.getKey() == null || !(e.getKey() instanceof Float)) return false; 
/* 294 */       if (e.getValue() == null || !(e.getValue() instanceof Float)) return false; 
/* 295 */       float k = ((Float)e.getKey()).floatValue();
/* 296 */       float v = ((Float)e.getValue()).floatValue();
/* 297 */       int oldPos = Float2FloatArrayMap.this.findKey(k);
/* 298 */       if (oldPos == -1 || Float.floatToIntBits(v) != Float.floatToIntBits(Float2FloatArrayMap.this.value[oldPos])) return false; 
/* 299 */       int tail = Float2FloatArrayMap.this.size - oldPos - 1;
/* 300 */       System.arraycopy(Float2FloatArrayMap.this.key, oldPos + 1, Float2FloatArrayMap.this.key, oldPos, tail);
/* 301 */       System.arraycopy(Float2FloatArrayMap.this.value, oldPos + 1, Float2FloatArrayMap.this.value, oldPos, tail);
/* 302 */       Float2FloatArrayMap.this.size--;
/* 303 */       return true;
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   public Float2FloatMap.FastEntrySet float2FloatEntrySet() {
/* 309 */     if (this.entries == null) this.entries = new EntrySet(); 
/* 310 */     return this.entries;
/*     */   }
/*     */   
/*     */   private int findKey(float k) {
/* 314 */     float[] key = this.key;
/* 315 */     for (int i = this.size; i-- != 0;) { if (Float.floatToIntBits(key[i]) == Float.floatToIntBits(k)) return i;  }
/* 316 */      return -1;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public float get(float k) {
/* 322 */     float[] key = this.key;
/* 323 */     for (int i = this.size; i-- != 0;) { if (Float.floatToIntBits(key[i]) == Float.floatToIntBits(k)) return this.value[i];  }
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
/*     */   public boolean containsKey(float k) {
/* 339 */     return (findKey(k) != -1);
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean containsValue(float v) {
/* 344 */     for (int i = this.size; i-- != 0;) { if (Float.floatToIntBits(this.value[i]) == Float.floatToIntBits(v)) return true;  }
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
/*     */   public float put(float k, float v) {
/* 356 */     int oldKey = findKey(k);
/* 357 */     if (oldKey != -1) {
/* 358 */       float oldValue = this.value[oldKey];
/* 359 */       this.value[oldKey] = v;
/* 360 */       return oldValue;
/*     */     } 
/* 362 */     if (this.size == this.key.length) {
/* 363 */       float[] newKey = new float[(this.size == 0) ? 2 : (this.size * 2)];
/* 364 */       float[] newValue = new float[(this.size == 0) ? 2 : (this.size * 2)];
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
/*     */   public float remove(float k) {
/* 381 */     int oldPos = findKey(k);
/* 382 */     if (oldPos == -1) return this.defRetValue; 
/* 383 */     float oldValue = this.value[oldPos];
/* 384 */     int tail = this.size - oldPos - 1;
/* 385 */     System.arraycopy(this.key, oldPos + 1, this.key, oldPos, tail);
/* 386 */     System.arraycopy(this.value, oldPos + 1, this.value, oldPos, tail);
/* 387 */     this.size--;
/* 388 */     return oldValue;
/*     */   }
/*     */   
/*     */   private final class KeySet extends AbstractFloatSet { private KeySet() {}
/*     */     
/*     */     public boolean contains(float k) {
/* 394 */       return (Float2FloatArrayMap.this.findKey(k) != -1);
/*     */     }
/*     */ 
/*     */     
/*     */     public boolean remove(float k) {
/* 399 */       int oldPos = Float2FloatArrayMap.this.findKey(k);
/* 400 */       if (oldPos == -1) return false; 
/* 401 */       int tail = Float2FloatArrayMap.this.size - oldPos - 1;
/* 402 */       System.arraycopy(Float2FloatArrayMap.this.key, oldPos + 1, Float2FloatArrayMap.this.key, oldPos, tail);
/* 403 */       System.arraycopy(Float2FloatArrayMap.this.value, oldPos + 1, Float2FloatArrayMap.this.value, oldPos, tail);
/* 404 */       Float2FloatArrayMap.this.size--;
/* 405 */       return true;
/*     */     }
/*     */ 
/*     */     
/*     */     public FloatIterator iterator() {
/* 410 */       return new FloatIterator() {
/* 411 */           int pos = 0;
/*     */ 
/*     */           
/*     */           public boolean hasNext() {
/* 415 */             return (this.pos < Float2FloatArrayMap.this.size);
/*     */           }
/*     */ 
/*     */ 
/*     */           
/*     */           public float nextFloat() {
/* 421 */             if (!hasNext()) throw new NoSuchElementException(); 
/* 422 */             return Float2FloatArrayMap.this.key[this.pos++];
/*     */           }
/*     */ 
/*     */           
/*     */           public void remove() {
/* 427 */             if (this.pos == 0) throw new IllegalStateException(); 
/* 428 */             int tail = Float2FloatArrayMap.this.size - this.pos;
/* 429 */             System.arraycopy(Float2FloatArrayMap.this.key, this.pos, Float2FloatArrayMap.this.key, this.pos - 1, tail);
/* 430 */             System.arraycopy(Float2FloatArrayMap.this.value, this.pos, Float2FloatArrayMap.this.value, this.pos - 1, tail);
/* 431 */             Float2FloatArrayMap.this.size--;
/* 432 */             this.pos--;
/*     */           }
/*     */ 
/*     */ 
/*     */ 
/*     */           
/*     */           public void forEachRemaining(FloatConsumer action) {
/* 439 */             int max = Float2FloatArrayMap.this.size;
/* 440 */             while (this.pos < max)
/* 441 */               action.accept(Float2FloatArrayMap.this.key[this.pos++]); 
/*     */           }
/*     */         };
/*     */     }
/*     */     
/*     */     final class KeySetSpliterator
/*     */       extends FloatSpliterators.EarlyBindingSizeIndexBasedSpliterator
/*     */       implements FloatSpliterator {
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
/*     */       protected final float get(int location) {
/* 461 */         return Float2FloatArrayMap.this.key[location];
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
/*     */       public void forEachRemaining(FloatConsumer action) {
/* 473 */         int max = Float2FloatArrayMap.this.size;
/* 474 */         while (this.pos < max) {
/* 475 */           action.accept(Float2FloatArrayMap.this.key[this.pos++]);
/*     */         }
/*     */       }
/*     */     }
/*     */ 
/*     */     
/*     */     public FloatSpliterator spliterator() {
/* 482 */       return new KeySetSpliterator(0, Float2FloatArrayMap.this.size);
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public void forEach(FloatConsumer action) {
/* 489 */       for (int i = 0, max = Float2FloatArrayMap.this.size; i < max; i++) {
/* 490 */         action.accept(Float2FloatArrayMap.this.key[i]);
/*     */       }
/*     */     }
/*     */ 
/*     */     
/*     */     public int size() {
/* 496 */       return Float2FloatArrayMap.this.size;
/*     */     }
/*     */ 
/*     */     
/*     */     public void clear() {
/* 501 */       Float2FloatArrayMap.this.clear();
/*     */     } }
/*     */ 
/*     */ 
/*     */   
/*     */   public FloatSet keySet() {
/* 507 */     if (this.keys == null) this.keys = new KeySet(); 
/* 508 */     return this.keys;
/*     */   }
/*     */   
/*     */   private final class ValuesCollection extends AbstractFloatCollection { private ValuesCollection() {}
/*     */     
/*     */     public boolean contains(float v) {
/* 514 */       return Float2FloatArrayMap.this.containsValue(v);
/*     */     }
/*     */ 
/*     */     
/*     */     public FloatIterator iterator() {
/* 519 */       return new FloatIterator() {
/* 520 */           int pos = 0;
/*     */ 
/*     */           
/*     */           public boolean hasNext() {
/* 524 */             return (this.pos < Float2FloatArrayMap.this.size);
/*     */           }
/*     */ 
/*     */ 
/*     */           
/*     */           public float nextFloat() {
/* 530 */             if (!hasNext()) throw new NoSuchElementException(); 
/* 531 */             return Float2FloatArrayMap.this.value[this.pos++];
/*     */           }
/*     */ 
/*     */           
/*     */           public void remove() {
/* 536 */             if (this.pos == 0) throw new IllegalStateException(); 
/* 537 */             int tail = Float2FloatArrayMap.this.size - this.pos;
/* 538 */             System.arraycopy(Float2FloatArrayMap.this.key, this.pos, Float2FloatArrayMap.this.key, this.pos - 1, tail);
/* 539 */             System.arraycopy(Float2FloatArrayMap.this.value, this.pos, Float2FloatArrayMap.this.value, this.pos - 1, tail);
/* 540 */             Float2FloatArrayMap.this.size--;
/* 541 */             this.pos--;
/*     */           }
/*     */ 
/*     */ 
/*     */ 
/*     */           
/*     */           public void forEachRemaining(FloatConsumer action) {
/* 548 */             int max = Float2FloatArrayMap.this.size;
/* 549 */             while (this.pos < max)
/* 550 */               action.accept(Float2FloatArrayMap.this.value[this.pos++]); 
/*     */           }
/*     */         };
/*     */     }
/*     */     
/*     */     final class ValuesSpliterator
/*     */       extends FloatSpliterators.EarlyBindingSizeIndexBasedSpliterator
/*     */       implements FloatSpliterator {
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
/*     */       protected final float get(int location) {
/* 570 */         return Float2FloatArrayMap.this.value[location];
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
/*     */       public void forEachRemaining(FloatConsumer action) {
/* 582 */         int max = Float2FloatArrayMap.this.size;
/* 583 */         while (this.pos < max) {
/* 584 */           action.accept(Float2FloatArrayMap.this.value[this.pos++]);
/*     */         }
/*     */       }
/*     */     }
/*     */ 
/*     */     
/*     */     public FloatSpliterator spliterator() {
/* 591 */       return new ValuesSpliterator(0, Float2FloatArrayMap.this.size);
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public void forEach(FloatConsumer action) {
/* 598 */       for (int i = 0, max = Float2FloatArrayMap.this.size; i < max; i++) {
/* 599 */         action.accept(Float2FloatArrayMap.this.value[i]);
/*     */       }
/*     */     }
/*     */ 
/*     */     
/*     */     public int size() {
/* 605 */       return Float2FloatArrayMap.this.size;
/*     */     }
/*     */ 
/*     */     
/*     */     public void clear() {
/* 610 */       Float2FloatArrayMap.this.clear();
/*     */     } }
/*     */ 
/*     */ 
/*     */   
/*     */   public FloatCollection values() {
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
/*     */   public Float2FloatArrayMap clone() {
/*     */     Float2FloatArrayMap c;
/*     */     try {
/* 634 */       c = (Float2FloatArrayMap)super.clone();
/* 635 */     } catch (CloneNotSupportedException cantHappen) {
/* 636 */       throw new InternalError();
/*     */     } 
/* 638 */     c.key = (float[])this.key.clone();
/* 639 */     c.value = (float[])this.value.clone();
/* 640 */     c.entries = null;
/* 641 */     c.keys = null;
/* 642 */     c.values = null;
/* 643 */     return c;
/*     */   }
/*     */   
/*     */   private void writeObject(ObjectOutputStream s) throws IOException {
/* 647 */     s.defaultWriteObject();
/* 648 */     for (int i = 0, max = this.size; i < max; i++) {
/* 649 */       s.writeFloat(this.key[i]);
/* 650 */       s.writeFloat(this.value[i]);
/*     */     } 
/*     */   }
/*     */   
/*     */   private void readObject(ObjectInputStream s) throws IOException, ClassNotFoundException {
/* 655 */     s.defaultReadObject();
/* 656 */     this.key = new float[this.size];
/* 657 */     this.value = new float[this.size];
/* 658 */     for (int i = 0; i < this.size; i++) {
/* 659 */       this.key[i] = s.readFloat();
/* 660 */       this.value[i] = s.readFloat();
/*     */     } 
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\i\\unimi\dsi\fastutil\floats\Float2FloatArrayMap.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */