/*     */ package it.unimi.dsi.fastutil.floats;
/*     */ 
/*     */ import it.unimi.dsi.fastutil.doubles.AbstractDoubleCollection;
/*     */ import it.unimi.dsi.fastutil.doubles.DoubleArrays;
/*     */ import it.unimi.dsi.fastutil.doubles.DoubleCollection;
/*     */ import it.unimi.dsi.fastutil.doubles.DoubleIterator;
/*     */ import it.unimi.dsi.fastutil.doubles.DoubleSpliterator;
/*     */ import it.unimi.dsi.fastutil.doubles.DoubleSpliterators;
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
/*     */ import java.util.function.DoubleConsumer;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class Float2DoubleArrayMap
/*     */   extends AbstractFloat2DoubleMap
/*     */   implements Serializable, Cloneable
/*     */ {
/*     */   private static final long serialVersionUID = 1L;
/*     */   protected transient float[] key;
/*     */   protected transient double[] value;
/*     */   protected int size;
/*     */   protected transient Float2DoubleMap.FastEntrySet entries;
/*     */   protected transient FloatSet keys;
/*     */   protected transient DoubleCollection values;
/*     */   
/*     */   public Float2DoubleArrayMap(float[] key, double[] value) {
/*  63 */     this.key = key;
/*  64 */     this.value = value;
/*  65 */     this.size = key.length;
/*  66 */     if (key.length != value.length) throw new IllegalArgumentException("Keys and values have different lengths (" + key.length + ", " + value.length + ")");
/*     */   
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public Float2DoubleArrayMap() {
/*  73 */     this.key = FloatArrays.EMPTY_ARRAY;
/*  74 */     this.value = DoubleArrays.EMPTY_ARRAY;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Float2DoubleArrayMap(int capacity) {
/*  83 */     this.key = new float[capacity];
/*  84 */     this.value = new double[capacity];
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Float2DoubleArrayMap(Float2DoubleMap m) {
/*  93 */     this(m.size());
/*  94 */     int i = 0;
/*  95 */     for (ObjectIterator<Float2DoubleMap.Entry> objectIterator = m.float2DoubleEntrySet().iterator(); objectIterator.hasNext(); ) { Float2DoubleMap.Entry e = objectIterator.next();
/*  96 */       this.key[i] = e.getFloatKey();
/*  97 */       this.value[i] = e.getDoubleValue();
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
/*     */   public Float2DoubleArrayMap(Map<? extends Float, ? extends Double> m) {
/* 109 */     this(m.size());
/* 110 */     int i = 0;
/* 111 */     for (Map.Entry<? extends Float, ? extends Double> e : m.entrySet()) {
/* 112 */       this.key[i] = ((Float)e.getKey()).floatValue();
/* 113 */       this.value[i] = ((Double)e.getValue()).doubleValue();
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
/*     */   public Float2DoubleArrayMap(float[] key, double[] value, int size) {
/* 132 */     this.key = key;
/* 133 */     this.value = value;
/* 134 */     this.size = size;
/* 135 */     if (key.length != value.length) throw new IllegalArgumentException("Keys and values have different lengths (" + key.length + ", " + value.length + ")"); 
/* 136 */     if (size > key.length) throw new IllegalArgumentException("The provided size (" + size + ") is larger than or equal to the backing-arrays size (" + key.length + ")"); 
/*     */   }
/*     */   
/*     */   private final class EntrySet
/*     */     extends AbstractObjectSet<Float2DoubleMap.Entry> implements Float2DoubleMap.FastEntrySet {
/*     */     private EntrySet() {}
/*     */     
/*     */     public ObjectIterator<Float2DoubleMap.Entry> iterator() {
/* 144 */       return new ObjectIterator<Float2DoubleMap.Entry>() {
/* 145 */           int curr = -1; int next = 0;
/*     */ 
/*     */           
/*     */           public boolean hasNext() {
/* 149 */             return (this.next < Float2DoubleArrayMap.this.size);
/*     */           }
/*     */ 
/*     */ 
/*     */           
/*     */           public Float2DoubleMap.Entry next() {
/* 155 */             if (!hasNext()) throw new NoSuchElementException(); 
/* 156 */             return new AbstractFloat2DoubleMap.BasicEntry(Float2DoubleArrayMap.this.key[this.curr = this.next], Float2DoubleArrayMap.this.value[this.next++]);
/*     */           }
/*     */ 
/*     */           
/*     */           public void remove() {
/* 161 */             if (this.curr == -1) throw new IllegalStateException(); 
/* 162 */             this.curr = -1;
/* 163 */             int tail = Float2DoubleArrayMap.this.size-- - this.next--;
/* 164 */             System.arraycopy(Float2DoubleArrayMap.this.key, this.next + 1, Float2DoubleArrayMap.this.key, this.next, tail);
/* 165 */             System.arraycopy(Float2DoubleArrayMap.this.value, this.next + 1, Float2DoubleArrayMap.this.value, this.next, tail);
/*     */           }
/*     */ 
/*     */ 
/*     */ 
/*     */           
/*     */           public void forEachRemaining(Consumer<? super Float2DoubleMap.Entry> action) {
/* 172 */             int max = Float2DoubleArrayMap.this.size;
/* 173 */             while (this.next < max) {
/* 174 */               action.accept(new AbstractFloat2DoubleMap.BasicEntry(Float2DoubleArrayMap.this.key[this.curr = this.next], Float2DoubleArrayMap.this.value[this.next++]));
/*     */             }
/*     */           }
/*     */         };
/*     */     }
/*     */ 
/*     */     
/*     */     public ObjectIterator<Float2DoubleMap.Entry> fastIterator() {
/* 182 */       return new ObjectIterator<Float2DoubleMap.Entry>() {
/* 183 */           int next = 0; int curr = -1;
/* 184 */           final AbstractFloat2DoubleMap.BasicEntry entry = new AbstractFloat2DoubleMap.BasicEntry();
/*     */ 
/*     */           
/*     */           public boolean hasNext() {
/* 188 */             return (this.next < Float2DoubleArrayMap.this.size);
/*     */           }
/*     */ 
/*     */ 
/*     */           
/*     */           public Float2DoubleMap.Entry next() {
/* 194 */             if (!hasNext()) throw new NoSuchElementException(); 
/* 195 */             this.entry.key = Float2DoubleArrayMap.this.key[this.curr = this.next];
/* 196 */             this.entry.value = Float2DoubleArrayMap.this.value[this.next++];
/* 197 */             return this.entry;
/*     */           }
/*     */ 
/*     */           
/*     */           public void remove() {
/* 202 */             if (this.curr == -1) throw new IllegalStateException(); 
/* 203 */             this.curr = -1;
/* 204 */             int tail = Float2DoubleArrayMap.this.size-- - this.next--;
/* 205 */             System.arraycopy(Float2DoubleArrayMap.this.key, this.next + 1, Float2DoubleArrayMap.this.key, this.next, tail);
/* 206 */             System.arraycopy(Float2DoubleArrayMap.this.value, this.next + 1, Float2DoubleArrayMap.this.value, this.next, tail);
/*     */           }
/*     */ 
/*     */ 
/*     */ 
/*     */           
/*     */           public void forEachRemaining(Consumer<? super Float2DoubleMap.Entry> action) {
/* 213 */             int max = Float2DoubleArrayMap.this.size;
/* 214 */             while (this.next < max) {
/* 215 */               this.entry.key = Float2DoubleArrayMap.this.key[this.curr = this.next];
/* 216 */               this.entry.value = Float2DoubleArrayMap.this.value[this.next++];
/* 217 */               action.accept(this.entry);
/*     */             } 
/*     */           }
/*     */         };
/*     */     }
/*     */     
/*     */     final class EntrySetSpliterator
/*     */       extends ObjectSpliterators.EarlyBindingSizeIndexBasedSpliterator<Float2DoubleMap.Entry>
/*     */       implements ObjectSpliterator<Float2DoubleMap.Entry> {
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
/*     */       protected final Float2DoubleMap.Entry get(int location) {
/* 238 */         return new AbstractFloat2DoubleMap.BasicEntry(Float2DoubleArrayMap.this.key[location], Float2DoubleArrayMap.this.value[location]);
/*     */       }
/*     */ 
/*     */       
/*     */       protected final EntrySetSpliterator makeForSplit(int pos, int maxPos) {
/* 243 */         return new EntrySetSpliterator(pos, maxPos);
/*     */       }
/*     */     }
/*     */ 
/*     */     
/*     */     public ObjectSpliterator<Float2DoubleMap.Entry> spliterator() {
/* 249 */       return new EntrySetSpliterator(0, Float2DoubleArrayMap.this.size);
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public void forEach(Consumer<? super Float2DoubleMap.Entry> action) {
/* 257 */       for (int i = 0, max = Float2DoubleArrayMap.this.size; i < max; i++) {
/* 258 */         action.accept(new AbstractFloat2DoubleMap.BasicEntry(Float2DoubleArrayMap.this.key[i], Float2DoubleArrayMap.this.value[i]));
/*     */       }
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public void fastForEach(Consumer<? super Float2DoubleMap.Entry> action) {
/* 266 */       AbstractFloat2DoubleMap.BasicEntry entry = new AbstractFloat2DoubleMap.BasicEntry();
/*     */       
/* 268 */       for (int i = 0, max = Float2DoubleArrayMap.this.size; i < max; i++) {
/* 269 */         entry.key = Float2DoubleArrayMap.this.key[i];
/* 270 */         entry.value = Float2DoubleArrayMap.this.value[i];
/* 271 */         action.accept(entry);
/*     */       } 
/*     */     }
/*     */ 
/*     */     
/*     */     public int size() {
/* 277 */       return Float2DoubleArrayMap.this.size;
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     public boolean contains(Object o) {
/* 283 */       if (!(o instanceof Map.Entry)) return false; 
/* 284 */       Map.Entry<?, ?> e = (Map.Entry<?, ?>)o;
/* 285 */       if (e.getKey() == null || !(e.getKey() instanceof Float)) return false; 
/* 286 */       if (e.getValue() == null || !(e.getValue() instanceof Double)) return false; 
/* 287 */       float k = ((Float)e.getKey()).floatValue();
/* 288 */       return (Float2DoubleArrayMap.this.containsKey(k) && Double.doubleToLongBits(Float2DoubleArrayMap.this.get(k)) == Double.doubleToLongBits(((Double)e.getValue()).doubleValue()));
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     public boolean remove(Object o) {
/* 294 */       if (!(o instanceof Map.Entry)) return false; 
/* 295 */       Map.Entry<?, ?> e = (Map.Entry<?, ?>)o;
/* 296 */       if (e.getKey() == null || !(e.getKey() instanceof Float)) return false; 
/* 297 */       if (e.getValue() == null || !(e.getValue() instanceof Double)) return false; 
/* 298 */       float k = ((Float)e.getKey()).floatValue();
/* 299 */       double v = ((Double)e.getValue()).doubleValue();
/* 300 */       int oldPos = Float2DoubleArrayMap.this.findKey(k);
/* 301 */       if (oldPos == -1 || Double.doubleToLongBits(v) != Double.doubleToLongBits(Float2DoubleArrayMap.this.value[oldPos])) return false; 
/* 302 */       int tail = Float2DoubleArrayMap.this.size - oldPos - 1;
/* 303 */       System.arraycopy(Float2DoubleArrayMap.this.key, oldPos + 1, Float2DoubleArrayMap.this.key, oldPos, tail);
/* 304 */       System.arraycopy(Float2DoubleArrayMap.this.value, oldPos + 1, Float2DoubleArrayMap.this.value, oldPos, tail);
/* 305 */       Float2DoubleArrayMap.this.size--;
/* 306 */       return true;
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   public Float2DoubleMap.FastEntrySet float2DoubleEntrySet() {
/* 312 */     if (this.entries == null) this.entries = new EntrySet(); 
/* 313 */     return this.entries;
/*     */   }
/*     */   
/*     */   private int findKey(float k) {
/* 317 */     float[] key = this.key;
/* 318 */     for (int i = this.size; i-- != 0;) { if (Float.floatToIntBits(key[i]) == Float.floatToIntBits(k)) return i;  }
/* 319 */      return -1;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public double get(float k) {
/* 325 */     float[] key = this.key;
/* 326 */     for (int i = this.size; i-- != 0;) { if (Float.floatToIntBits(key[i]) == Float.floatToIntBits(k)) return this.value[i];  }
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
/*     */   public boolean containsKey(float k) {
/* 342 */     return (findKey(k) != -1);
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean containsValue(double v) {
/* 347 */     for (int i = this.size; i-- != 0;) { if (Double.doubleToLongBits(this.value[i]) == Double.doubleToLongBits(v)) return true;  }
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
/*     */   public double put(float k, double v) {
/* 359 */     int oldKey = findKey(k);
/* 360 */     if (oldKey != -1) {
/* 361 */       double oldValue = this.value[oldKey];
/* 362 */       this.value[oldKey] = v;
/* 363 */       return oldValue;
/*     */     } 
/* 365 */     if (this.size == this.key.length) {
/* 366 */       float[] newKey = new float[(this.size == 0) ? 2 : (this.size * 2)];
/* 367 */       double[] newValue = new double[(this.size == 0) ? 2 : (this.size * 2)];
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
/*     */   public double remove(float k) {
/* 384 */     int oldPos = findKey(k);
/* 385 */     if (oldPos == -1) return this.defRetValue; 
/* 386 */     double oldValue = this.value[oldPos];
/* 387 */     int tail = this.size - oldPos - 1;
/* 388 */     System.arraycopy(this.key, oldPos + 1, this.key, oldPos, tail);
/* 389 */     System.arraycopy(this.value, oldPos + 1, this.value, oldPos, tail);
/* 390 */     this.size--;
/* 391 */     return oldValue;
/*     */   }
/*     */   
/*     */   private final class KeySet extends AbstractFloatSet { private KeySet() {}
/*     */     
/*     */     public boolean contains(float k) {
/* 397 */       return (Float2DoubleArrayMap.this.findKey(k) != -1);
/*     */     }
/*     */ 
/*     */     
/*     */     public boolean remove(float k) {
/* 402 */       int oldPos = Float2DoubleArrayMap.this.findKey(k);
/* 403 */       if (oldPos == -1) return false; 
/* 404 */       int tail = Float2DoubleArrayMap.this.size - oldPos - 1;
/* 405 */       System.arraycopy(Float2DoubleArrayMap.this.key, oldPos + 1, Float2DoubleArrayMap.this.key, oldPos, tail);
/* 406 */       System.arraycopy(Float2DoubleArrayMap.this.value, oldPos + 1, Float2DoubleArrayMap.this.value, oldPos, tail);
/* 407 */       Float2DoubleArrayMap.this.size--;
/* 408 */       return true;
/*     */     }
/*     */ 
/*     */     
/*     */     public FloatIterator iterator() {
/* 413 */       return new FloatIterator() {
/* 414 */           int pos = 0;
/*     */ 
/*     */           
/*     */           public boolean hasNext() {
/* 418 */             return (this.pos < Float2DoubleArrayMap.this.size);
/*     */           }
/*     */ 
/*     */ 
/*     */           
/*     */           public float nextFloat() {
/* 424 */             if (!hasNext()) throw new NoSuchElementException(); 
/* 425 */             return Float2DoubleArrayMap.this.key[this.pos++];
/*     */           }
/*     */ 
/*     */           
/*     */           public void remove() {
/* 430 */             if (this.pos == 0) throw new IllegalStateException(); 
/* 431 */             int tail = Float2DoubleArrayMap.this.size - this.pos;
/* 432 */             System.arraycopy(Float2DoubleArrayMap.this.key, this.pos, Float2DoubleArrayMap.this.key, this.pos - 1, tail);
/* 433 */             System.arraycopy(Float2DoubleArrayMap.this.value, this.pos, Float2DoubleArrayMap.this.value, this.pos - 1, tail);
/* 434 */             Float2DoubleArrayMap.this.size--;
/* 435 */             this.pos--;
/*     */           }
/*     */ 
/*     */ 
/*     */ 
/*     */           
/*     */           public void forEachRemaining(FloatConsumer action) {
/* 442 */             int max = Float2DoubleArrayMap.this.size;
/* 443 */             while (this.pos < max)
/* 444 */               action.accept(Float2DoubleArrayMap.this.key[this.pos++]); 
/*     */           }
/*     */         };
/*     */     }
/*     */     
/*     */     final class KeySetSpliterator
/*     */       extends FloatSpliterators.EarlyBindingSizeIndexBasedSpliterator
/*     */       implements FloatSpliterator {
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
/*     */       protected final float get(int location) {
/* 464 */         return Float2DoubleArrayMap.this.key[location];
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
/*     */       public void forEachRemaining(FloatConsumer action) {
/* 476 */         int max = Float2DoubleArrayMap.this.size;
/* 477 */         while (this.pos < max) {
/* 478 */           action.accept(Float2DoubleArrayMap.this.key[this.pos++]);
/*     */         }
/*     */       }
/*     */     }
/*     */ 
/*     */     
/*     */     public FloatSpliterator spliterator() {
/* 485 */       return new KeySetSpliterator(0, Float2DoubleArrayMap.this.size);
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public void forEach(FloatConsumer action) {
/* 492 */       for (int i = 0, max = Float2DoubleArrayMap.this.size; i < max; i++) {
/* 493 */         action.accept(Float2DoubleArrayMap.this.key[i]);
/*     */       }
/*     */     }
/*     */ 
/*     */     
/*     */     public int size() {
/* 499 */       return Float2DoubleArrayMap.this.size;
/*     */     }
/*     */ 
/*     */     
/*     */     public void clear() {
/* 504 */       Float2DoubleArrayMap.this.clear();
/*     */     } }
/*     */ 
/*     */ 
/*     */   
/*     */   public FloatSet keySet() {
/* 510 */     if (this.keys == null) this.keys = new KeySet(); 
/* 511 */     return this.keys;
/*     */   }
/*     */   
/*     */   private final class ValuesCollection extends AbstractDoubleCollection { private ValuesCollection() {}
/*     */     
/*     */     public boolean contains(double v) {
/* 517 */       return Float2DoubleArrayMap.this.containsValue(v);
/*     */     }
/*     */ 
/*     */     
/*     */     public DoubleIterator iterator() {
/* 522 */       return new DoubleIterator() {
/* 523 */           int pos = 0;
/*     */ 
/*     */           
/*     */           public boolean hasNext() {
/* 527 */             return (this.pos < Float2DoubleArrayMap.this.size);
/*     */           }
/*     */ 
/*     */ 
/*     */           
/*     */           public double nextDouble() {
/* 533 */             if (!hasNext()) throw new NoSuchElementException(); 
/* 534 */             return Float2DoubleArrayMap.this.value[this.pos++];
/*     */           }
/*     */ 
/*     */           
/*     */           public void remove() {
/* 539 */             if (this.pos == 0) throw new IllegalStateException(); 
/* 540 */             int tail = Float2DoubleArrayMap.this.size - this.pos;
/* 541 */             System.arraycopy(Float2DoubleArrayMap.this.key, this.pos, Float2DoubleArrayMap.this.key, this.pos - 1, tail);
/* 542 */             System.arraycopy(Float2DoubleArrayMap.this.value, this.pos, Float2DoubleArrayMap.this.value, this.pos - 1, tail);
/* 543 */             Float2DoubleArrayMap.this.size--;
/* 544 */             this.pos--;
/*     */           }
/*     */ 
/*     */ 
/*     */ 
/*     */           
/*     */           public void forEachRemaining(DoubleConsumer action) {
/* 551 */             int max = Float2DoubleArrayMap.this.size;
/* 552 */             while (this.pos < max)
/* 553 */               action.accept(Float2DoubleArrayMap.this.value[this.pos++]); 
/*     */           }
/*     */         };
/*     */     }
/*     */     
/*     */     final class ValuesSpliterator
/*     */       extends DoubleSpliterators.EarlyBindingSizeIndexBasedSpliterator
/*     */       implements DoubleSpliterator {
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
/*     */       protected final double get(int location) {
/* 573 */         return Float2DoubleArrayMap.this.value[location];
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
/*     */       public void forEachRemaining(DoubleConsumer action) {
/* 585 */         int max = Float2DoubleArrayMap.this.size;
/* 586 */         while (this.pos < max) {
/* 587 */           action.accept(Float2DoubleArrayMap.this.value[this.pos++]);
/*     */         }
/*     */       }
/*     */     }
/*     */ 
/*     */     
/*     */     public DoubleSpliterator spliterator() {
/* 594 */       return new ValuesSpliterator(0, Float2DoubleArrayMap.this.size);
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public void forEach(DoubleConsumer action) {
/* 601 */       for (int i = 0, max = Float2DoubleArrayMap.this.size; i < max; i++) {
/* 602 */         action.accept(Float2DoubleArrayMap.this.value[i]);
/*     */       }
/*     */     }
/*     */ 
/*     */     
/*     */     public int size() {
/* 608 */       return Float2DoubleArrayMap.this.size;
/*     */     }
/*     */ 
/*     */     
/*     */     public void clear() {
/* 613 */       Float2DoubleArrayMap.this.clear();
/*     */     } }
/*     */ 
/*     */ 
/*     */   
/*     */   public DoubleCollection values() {
/* 619 */     if (this.values == null) this.values = (DoubleCollection)new ValuesCollection(); 
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
/*     */   public Float2DoubleArrayMap clone() {
/*     */     Float2DoubleArrayMap c;
/*     */     try {
/* 637 */       c = (Float2DoubleArrayMap)super.clone();
/* 638 */     } catch (CloneNotSupportedException cantHappen) {
/* 639 */       throw new InternalError();
/*     */     } 
/* 641 */     c.key = (float[])this.key.clone();
/* 642 */     c.value = (double[])this.value.clone();
/* 643 */     c.entries = null;
/* 644 */     c.keys = null;
/* 645 */     c.values = null;
/* 646 */     return c;
/*     */   }
/*     */   
/*     */   private void writeObject(ObjectOutputStream s) throws IOException {
/* 650 */     s.defaultWriteObject();
/* 651 */     for (int i = 0, max = this.size; i < max; i++) {
/* 652 */       s.writeFloat(this.key[i]);
/* 653 */       s.writeDouble(this.value[i]);
/*     */     } 
/*     */   }
/*     */   
/*     */   private void readObject(ObjectInputStream s) throws IOException, ClassNotFoundException {
/* 658 */     s.defaultReadObject();
/* 659 */     this.key = new float[this.size];
/* 660 */     this.value = new double[this.size];
/* 661 */     for (int i = 0; i < this.size; i++) {
/* 662 */       this.key[i] = s.readFloat();
/* 663 */       this.value[i] = s.readDouble();
/*     */     } 
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\i\\unimi\dsi\fastutil\floats\Float2DoubleArrayMap.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */