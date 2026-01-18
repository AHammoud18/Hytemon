/*     */ package it.unimi.dsi.fastutil.doubles;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ public class Double2DoubleArrayMap
/*     */   extends AbstractDouble2DoubleMap
/*     */   implements Serializable, Cloneable
/*     */ {
/*     */   private static final long serialVersionUID = 1L;
/*     */   protected transient double[] key;
/*     */   protected transient double[] value;
/*     */   protected int size;
/*     */   protected transient Double2DoubleMap.FastEntrySet entries;
/*     */   protected transient DoubleSet keys;
/*     */   protected transient DoubleCollection values;
/*     */   
/*     */   public Double2DoubleArrayMap(double[] key, double[] value) {
/*  60 */     this.key = key;
/*  61 */     this.value = value;
/*  62 */     this.size = key.length;
/*  63 */     if (key.length != value.length) throw new IllegalArgumentException("Keys and values have different lengths (" + key.length + ", " + value.length + ")");
/*     */   
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public Double2DoubleArrayMap() {
/*  70 */     this.key = DoubleArrays.EMPTY_ARRAY;
/*  71 */     this.value = DoubleArrays.EMPTY_ARRAY;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Double2DoubleArrayMap(int capacity) {
/*  80 */     this.key = new double[capacity];
/*  81 */     this.value = new double[capacity];
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Double2DoubleArrayMap(Double2DoubleMap m) {
/*  90 */     this(m.size());
/*  91 */     int i = 0;
/*  92 */     for (ObjectIterator<Double2DoubleMap.Entry> objectIterator = m.double2DoubleEntrySet().iterator(); objectIterator.hasNext(); ) { Double2DoubleMap.Entry e = objectIterator.next();
/*  93 */       this.key[i] = e.getDoubleKey();
/*  94 */       this.value[i] = e.getDoubleValue();
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
/*     */   public Double2DoubleArrayMap(Map<? extends Double, ? extends Double> m) {
/* 106 */     this(m.size());
/* 107 */     int i = 0;
/* 108 */     for (Map.Entry<? extends Double, ? extends Double> e : m.entrySet()) {
/* 109 */       this.key[i] = ((Double)e.getKey()).doubleValue();
/* 110 */       this.value[i] = ((Double)e.getValue()).doubleValue();
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
/*     */   public Double2DoubleArrayMap(double[] key, double[] value, int size) {
/* 129 */     this.key = key;
/* 130 */     this.value = value;
/* 131 */     this.size = size;
/* 132 */     if (key.length != value.length) throw new IllegalArgumentException("Keys and values have different lengths (" + key.length + ", " + value.length + ")"); 
/* 133 */     if (size > key.length) throw new IllegalArgumentException("The provided size (" + size + ") is larger than or equal to the backing-arrays size (" + key.length + ")"); 
/*     */   }
/*     */   
/*     */   private final class EntrySet
/*     */     extends AbstractObjectSet<Double2DoubleMap.Entry> implements Double2DoubleMap.FastEntrySet {
/*     */     private EntrySet() {}
/*     */     
/*     */     public ObjectIterator<Double2DoubleMap.Entry> iterator() {
/* 141 */       return new ObjectIterator<Double2DoubleMap.Entry>() {
/* 142 */           int curr = -1; int next = 0;
/*     */ 
/*     */           
/*     */           public boolean hasNext() {
/* 146 */             return (this.next < Double2DoubleArrayMap.this.size);
/*     */           }
/*     */ 
/*     */ 
/*     */           
/*     */           public Double2DoubleMap.Entry next() {
/* 152 */             if (!hasNext()) throw new NoSuchElementException(); 
/* 153 */             return new AbstractDouble2DoubleMap.BasicEntry(Double2DoubleArrayMap.this.key[this.curr = this.next], Double2DoubleArrayMap.this.value[this.next++]);
/*     */           }
/*     */ 
/*     */           
/*     */           public void remove() {
/* 158 */             if (this.curr == -1) throw new IllegalStateException(); 
/* 159 */             this.curr = -1;
/* 160 */             int tail = Double2DoubleArrayMap.this.size-- - this.next--;
/* 161 */             System.arraycopy(Double2DoubleArrayMap.this.key, this.next + 1, Double2DoubleArrayMap.this.key, this.next, tail);
/* 162 */             System.arraycopy(Double2DoubleArrayMap.this.value, this.next + 1, Double2DoubleArrayMap.this.value, this.next, tail);
/*     */           }
/*     */ 
/*     */ 
/*     */ 
/*     */           
/*     */           public void forEachRemaining(Consumer<? super Double2DoubleMap.Entry> action) {
/* 169 */             int max = Double2DoubleArrayMap.this.size;
/* 170 */             while (this.next < max) {
/* 171 */               action.accept(new AbstractDouble2DoubleMap.BasicEntry(Double2DoubleArrayMap.this.key[this.curr = this.next], Double2DoubleArrayMap.this.value[this.next++]));
/*     */             }
/*     */           }
/*     */         };
/*     */     }
/*     */ 
/*     */     
/*     */     public ObjectIterator<Double2DoubleMap.Entry> fastIterator() {
/* 179 */       return new ObjectIterator<Double2DoubleMap.Entry>() {
/* 180 */           int next = 0; int curr = -1;
/* 181 */           final AbstractDouble2DoubleMap.BasicEntry entry = new AbstractDouble2DoubleMap.BasicEntry();
/*     */ 
/*     */           
/*     */           public boolean hasNext() {
/* 185 */             return (this.next < Double2DoubleArrayMap.this.size);
/*     */           }
/*     */ 
/*     */ 
/*     */           
/*     */           public Double2DoubleMap.Entry next() {
/* 191 */             if (!hasNext()) throw new NoSuchElementException(); 
/* 192 */             this.entry.key = Double2DoubleArrayMap.this.key[this.curr = this.next];
/* 193 */             this.entry.value = Double2DoubleArrayMap.this.value[this.next++];
/* 194 */             return this.entry;
/*     */           }
/*     */ 
/*     */           
/*     */           public void remove() {
/* 199 */             if (this.curr == -1) throw new IllegalStateException(); 
/* 200 */             this.curr = -1;
/* 201 */             int tail = Double2DoubleArrayMap.this.size-- - this.next--;
/* 202 */             System.arraycopy(Double2DoubleArrayMap.this.key, this.next + 1, Double2DoubleArrayMap.this.key, this.next, tail);
/* 203 */             System.arraycopy(Double2DoubleArrayMap.this.value, this.next + 1, Double2DoubleArrayMap.this.value, this.next, tail);
/*     */           }
/*     */ 
/*     */ 
/*     */ 
/*     */           
/*     */           public void forEachRemaining(Consumer<? super Double2DoubleMap.Entry> action) {
/* 210 */             int max = Double2DoubleArrayMap.this.size;
/* 211 */             while (this.next < max) {
/* 212 */               this.entry.key = Double2DoubleArrayMap.this.key[this.curr = this.next];
/* 213 */               this.entry.value = Double2DoubleArrayMap.this.value[this.next++];
/* 214 */               action.accept(this.entry);
/*     */             } 
/*     */           }
/*     */         };
/*     */     }
/*     */     
/*     */     final class EntrySetSpliterator
/*     */       extends ObjectSpliterators.EarlyBindingSizeIndexBasedSpliterator<Double2DoubleMap.Entry>
/*     */       implements ObjectSpliterator<Double2DoubleMap.Entry> {
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
/*     */       protected final Double2DoubleMap.Entry get(int location) {
/* 235 */         return new AbstractDouble2DoubleMap.BasicEntry(Double2DoubleArrayMap.this.key[location], Double2DoubleArrayMap.this.value[location]);
/*     */       }
/*     */ 
/*     */       
/*     */       protected final EntrySetSpliterator makeForSplit(int pos, int maxPos) {
/* 240 */         return new EntrySetSpliterator(pos, maxPos);
/*     */       }
/*     */     }
/*     */ 
/*     */     
/*     */     public ObjectSpliterator<Double2DoubleMap.Entry> spliterator() {
/* 246 */       return new EntrySetSpliterator(0, Double2DoubleArrayMap.this.size);
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public void forEach(Consumer<? super Double2DoubleMap.Entry> action) {
/* 254 */       for (int i = 0, max = Double2DoubleArrayMap.this.size; i < max; i++) {
/* 255 */         action.accept(new AbstractDouble2DoubleMap.BasicEntry(Double2DoubleArrayMap.this.key[i], Double2DoubleArrayMap.this.value[i]));
/*     */       }
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public void fastForEach(Consumer<? super Double2DoubleMap.Entry> action) {
/* 263 */       AbstractDouble2DoubleMap.BasicEntry entry = new AbstractDouble2DoubleMap.BasicEntry();
/*     */       
/* 265 */       for (int i = 0, max = Double2DoubleArrayMap.this.size; i < max; i++) {
/* 266 */         entry.key = Double2DoubleArrayMap.this.key[i];
/* 267 */         entry.value = Double2DoubleArrayMap.this.value[i];
/* 268 */         action.accept(entry);
/*     */       } 
/*     */     }
/*     */ 
/*     */     
/*     */     public int size() {
/* 274 */       return Double2DoubleArrayMap.this.size;
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     public boolean contains(Object o) {
/* 280 */       if (!(o instanceof Map.Entry)) return false; 
/* 281 */       Map.Entry<?, ?> e = (Map.Entry<?, ?>)o;
/* 282 */       if (e.getKey() == null || !(e.getKey() instanceof Double)) return false; 
/* 283 */       if (e.getValue() == null || !(e.getValue() instanceof Double)) return false; 
/* 284 */       double k = ((Double)e.getKey()).doubleValue();
/* 285 */       return (Double2DoubleArrayMap.this.containsKey(k) && Double.doubleToLongBits(Double2DoubleArrayMap.this.get(k)) == Double.doubleToLongBits(((Double)e.getValue()).doubleValue()));
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     public boolean remove(Object o) {
/* 291 */       if (!(o instanceof Map.Entry)) return false; 
/* 292 */       Map.Entry<?, ?> e = (Map.Entry<?, ?>)o;
/* 293 */       if (e.getKey() == null || !(e.getKey() instanceof Double)) return false; 
/* 294 */       if (e.getValue() == null || !(e.getValue() instanceof Double)) return false; 
/* 295 */       double k = ((Double)e.getKey()).doubleValue();
/* 296 */       double v = ((Double)e.getValue()).doubleValue();
/* 297 */       int oldPos = Double2DoubleArrayMap.this.findKey(k);
/* 298 */       if (oldPos == -1 || Double.doubleToLongBits(v) != Double.doubleToLongBits(Double2DoubleArrayMap.this.value[oldPos])) return false; 
/* 299 */       int tail = Double2DoubleArrayMap.this.size - oldPos - 1;
/* 300 */       System.arraycopy(Double2DoubleArrayMap.this.key, oldPos + 1, Double2DoubleArrayMap.this.key, oldPos, tail);
/* 301 */       System.arraycopy(Double2DoubleArrayMap.this.value, oldPos + 1, Double2DoubleArrayMap.this.value, oldPos, tail);
/* 302 */       Double2DoubleArrayMap.this.size--;
/* 303 */       return true;
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   public Double2DoubleMap.FastEntrySet double2DoubleEntrySet() {
/* 309 */     if (this.entries == null) this.entries = new EntrySet(); 
/* 310 */     return this.entries;
/*     */   }
/*     */   
/*     */   private int findKey(double k) {
/* 314 */     double[] key = this.key;
/* 315 */     for (int i = this.size; i-- != 0;) { if (Double.doubleToLongBits(key[i]) == Double.doubleToLongBits(k)) return i;  }
/* 316 */      return -1;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public double get(double k) {
/* 322 */     double[] key = this.key;
/* 323 */     for (int i = this.size; i-- != 0;) { if (Double.doubleToLongBits(key[i]) == Double.doubleToLongBits(k)) return this.value[i];  }
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
/*     */   public boolean containsKey(double k) {
/* 339 */     return (findKey(k) != -1);
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean containsValue(double v) {
/* 344 */     for (int i = this.size; i-- != 0;) { if (Double.doubleToLongBits(this.value[i]) == Double.doubleToLongBits(v)) return true;  }
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
/*     */   public double put(double k, double v) {
/* 356 */     int oldKey = findKey(k);
/* 357 */     if (oldKey != -1) {
/* 358 */       double oldValue = this.value[oldKey];
/* 359 */       this.value[oldKey] = v;
/* 360 */       return oldValue;
/*     */     } 
/* 362 */     if (this.size == this.key.length) {
/* 363 */       double[] newKey = new double[(this.size == 0) ? 2 : (this.size * 2)];
/* 364 */       double[] newValue = new double[(this.size == 0) ? 2 : (this.size * 2)];
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
/*     */   public double remove(double k) {
/* 381 */     int oldPos = findKey(k);
/* 382 */     if (oldPos == -1) return this.defRetValue; 
/* 383 */     double oldValue = this.value[oldPos];
/* 384 */     int tail = this.size - oldPos - 1;
/* 385 */     System.arraycopy(this.key, oldPos + 1, this.key, oldPos, tail);
/* 386 */     System.arraycopy(this.value, oldPos + 1, this.value, oldPos, tail);
/* 387 */     this.size--;
/* 388 */     return oldValue;
/*     */   }
/*     */   
/*     */   private final class KeySet extends AbstractDoubleSet { private KeySet() {}
/*     */     
/*     */     public boolean contains(double k) {
/* 394 */       return (Double2DoubleArrayMap.this.findKey(k) != -1);
/*     */     }
/*     */ 
/*     */     
/*     */     public boolean remove(double k) {
/* 399 */       int oldPos = Double2DoubleArrayMap.this.findKey(k);
/* 400 */       if (oldPos == -1) return false; 
/* 401 */       int tail = Double2DoubleArrayMap.this.size - oldPos - 1;
/* 402 */       System.arraycopy(Double2DoubleArrayMap.this.key, oldPos + 1, Double2DoubleArrayMap.this.key, oldPos, tail);
/* 403 */       System.arraycopy(Double2DoubleArrayMap.this.value, oldPos + 1, Double2DoubleArrayMap.this.value, oldPos, tail);
/* 404 */       Double2DoubleArrayMap.this.size--;
/* 405 */       return true;
/*     */     }
/*     */ 
/*     */     
/*     */     public DoubleIterator iterator() {
/* 410 */       return new DoubleIterator() {
/* 411 */           int pos = 0;
/*     */ 
/*     */           
/*     */           public boolean hasNext() {
/* 415 */             return (this.pos < Double2DoubleArrayMap.this.size);
/*     */           }
/*     */ 
/*     */ 
/*     */           
/*     */           public double nextDouble() {
/* 421 */             if (!hasNext()) throw new NoSuchElementException(); 
/* 422 */             return Double2DoubleArrayMap.this.key[this.pos++];
/*     */           }
/*     */ 
/*     */           
/*     */           public void remove() {
/* 427 */             if (this.pos == 0) throw new IllegalStateException(); 
/* 428 */             int tail = Double2DoubleArrayMap.this.size - this.pos;
/* 429 */             System.arraycopy(Double2DoubleArrayMap.this.key, this.pos, Double2DoubleArrayMap.this.key, this.pos - 1, tail);
/* 430 */             System.arraycopy(Double2DoubleArrayMap.this.value, this.pos, Double2DoubleArrayMap.this.value, this.pos - 1, tail);
/* 431 */             Double2DoubleArrayMap.this.size--;
/* 432 */             this.pos--;
/*     */           }
/*     */ 
/*     */ 
/*     */ 
/*     */           
/*     */           public void forEachRemaining(DoubleConsumer action) {
/* 439 */             int max = Double2DoubleArrayMap.this.size;
/* 440 */             while (this.pos < max)
/* 441 */               action.accept(Double2DoubleArrayMap.this.key[this.pos++]); 
/*     */           }
/*     */         };
/*     */     }
/*     */     
/*     */     final class KeySetSpliterator
/*     */       extends DoubleSpliterators.EarlyBindingSizeIndexBasedSpliterator
/*     */       implements DoubleSpliterator {
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
/*     */       protected final double get(int location) {
/* 461 */         return Double2DoubleArrayMap.this.key[location];
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
/*     */       public void forEachRemaining(DoubleConsumer action) {
/* 473 */         int max = Double2DoubleArrayMap.this.size;
/* 474 */         while (this.pos < max) {
/* 475 */           action.accept(Double2DoubleArrayMap.this.key[this.pos++]);
/*     */         }
/*     */       }
/*     */     }
/*     */ 
/*     */     
/*     */     public DoubleSpliterator spliterator() {
/* 482 */       return new KeySetSpliterator(0, Double2DoubleArrayMap.this.size);
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public void forEach(DoubleConsumer action) {
/* 489 */       for (int i = 0, max = Double2DoubleArrayMap.this.size; i < max; i++) {
/* 490 */         action.accept(Double2DoubleArrayMap.this.key[i]);
/*     */       }
/*     */     }
/*     */ 
/*     */     
/*     */     public int size() {
/* 496 */       return Double2DoubleArrayMap.this.size;
/*     */     }
/*     */ 
/*     */     
/*     */     public void clear() {
/* 501 */       Double2DoubleArrayMap.this.clear();
/*     */     } }
/*     */ 
/*     */ 
/*     */   
/*     */   public DoubleSet keySet() {
/* 507 */     if (this.keys == null) this.keys = new KeySet(); 
/* 508 */     return this.keys;
/*     */   }
/*     */   
/*     */   private final class ValuesCollection extends AbstractDoubleCollection { private ValuesCollection() {}
/*     */     
/*     */     public boolean contains(double v) {
/* 514 */       return Double2DoubleArrayMap.this.containsValue(v);
/*     */     }
/*     */ 
/*     */     
/*     */     public DoubleIterator iterator() {
/* 519 */       return new DoubleIterator() {
/* 520 */           int pos = 0;
/*     */ 
/*     */           
/*     */           public boolean hasNext() {
/* 524 */             return (this.pos < Double2DoubleArrayMap.this.size);
/*     */           }
/*     */ 
/*     */ 
/*     */           
/*     */           public double nextDouble() {
/* 530 */             if (!hasNext()) throw new NoSuchElementException(); 
/* 531 */             return Double2DoubleArrayMap.this.value[this.pos++];
/*     */           }
/*     */ 
/*     */           
/*     */           public void remove() {
/* 536 */             if (this.pos == 0) throw new IllegalStateException(); 
/* 537 */             int tail = Double2DoubleArrayMap.this.size - this.pos;
/* 538 */             System.arraycopy(Double2DoubleArrayMap.this.key, this.pos, Double2DoubleArrayMap.this.key, this.pos - 1, tail);
/* 539 */             System.arraycopy(Double2DoubleArrayMap.this.value, this.pos, Double2DoubleArrayMap.this.value, this.pos - 1, tail);
/* 540 */             Double2DoubleArrayMap.this.size--;
/* 541 */             this.pos--;
/*     */           }
/*     */ 
/*     */ 
/*     */ 
/*     */           
/*     */           public void forEachRemaining(DoubleConsumer action) {
/* 548 */             int max = Double2DoubleArrayMap.this.size;
/* 549 */             while (this.pos < max)
/* 550 */               action.accept(Double2DoubleArrayMap.this.value[this.pos++]); 
/*     */           }
/*     */         };
/*     */     }
/*     */     
/*     */     final class ValuesSpliterator
/*     */       extends DoubleSpliterators.EarlyBindingSizeIndexBasedSpliterator
/*     */       implements DoubleSpliterator {
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
/*     */       protected final double get(int location) {
/* 570 */         return Double2DoubleArrayMap.this.value[location];
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
/*     */       public void forEachRemaining(DoubleConsumer action) {
/* 582 */         int max = Double2DoubleArrayMap.this.size;
/* 583 */         while (this.pos < max) {
/* 584 */           action.accept(Double2DoubleArrayMap.this.value[this.pos++]);
/*     */         }
/*     */       }
/*     */     }
/*     */ 
/*     */     
/*     */     public DoubleSpliterator spliterator() {
/* 591 */       return new ValuesSpliterator(0, Double2DoubleArrayMap.this.size);
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public void forEach(DoubleConsumer action) {
/* 598 */       for (int i = 0, max = Double2DoubleArrayMap.this.size; i < max; i++) {
/* 599 */         action.accept(Double2DoubleArrayMap.this.value[i]);
/*     */       }
/*     */     }
/*     */ 
/*     */     
/*     */     public int size() {
/* 605 */       return Double2DoubleArrayMap.this.size;
/*     */     }
/*     */ 
/*     */     
/*     */     public void clear() {
/* 610 */       Double2DoubleArrayMap.this.clear();
/*     */     } }
/*     */ 
/*     */ 
/*     */   
/*     */   public DoubleCollection values() {
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
/*     */   public Double2DoubleArrayMap clone() {
/*     */     Double2DoubleArrayMap c;
/*     */     try {
/* 634 */       c = (Double2DoubleArrayMap)super.clone();
/* 635 */     } catch (CloneNotSupportedException cantHappen) {
/* 636 */       throw new InternalError();
/*     */     } 
/* 638 */     c.key = (double[])this.key.clone();
/* 639 */     c.value = (double[])this.value.clone();
/* 640 */     c.entries = null;
/* 641 */     c.keys = null;
/* 642 */     c.values = null;
/* 643 */     return c;
/*     */   }
/*     */   
/*     */   private void writeObject(ObjectOutputStream s) throws IOException {
/* 647 */     s.defaultWriteObject();
/* 648 */     for (int i = 0, max = this.size; i < max; i++) {
/* 649 */       s.writeDouble(this.key[i]);
/* 650 */       s.writeDouble(this.value[i]);
/*     */     } 
/*     */   }
/*     */   
/*     */   private void readObject(ObjectInputStream s) throws IOException, ClassNotFoundException {
/* 655 */     s.defaultReadObject();
/* 656 */     this.key = new double[this.size];
/* 657 */     this.value = new double[this.size];
/* 658 */     for (int i = 0; i < this.size; i++) {
/* 659 */       this.key[i] = s.readDouble();
/* 660 */       this.value[i] = s.readDouble();
/*     */     } 
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\i\\unimi\dsi\fastutil\doubles\Double2DoubleArrayMap.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */