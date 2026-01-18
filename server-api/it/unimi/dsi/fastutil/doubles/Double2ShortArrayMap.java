/*     */ package it.unimi.dsi.fastutil.doubles;
/*     */ 
/*     */ import it.unimi.dsi.fastutil.objects.AbstractObjectSet;
/*     */ import it.unimi.dsi.fastutil.objects.ObjectIterator;
/*     */ import it.unimi.dsi.fastutil.objects.ObjectSet;
/*     */ import it.unimi.dsi.fastutil.objects.ObjectSpliterator;
/*     */ import it.unimi.dsi.fastutil.objects.ObjectSpliterators;
/*     */ import it.unimi.dsi.fastutil.shorts.AbstractShortCollection;
/*     */ import it.unimi.dsi.fastutil.shorts.ShortArrays;
/*     */ import it.unimi.dsi.fastutil.shorts.ShortCollection;
/*     */ import it.unimi.dsi.fastutil.shorts.ShortConsumer;
/*     */ import it.unimi.dsi.fastutil.shorts.ShortIterator;
/*     */ import it.unimi.dsi.fastutil.shorts.ShortSpliterator;
/*     */ import it.unimi.dsi.fastutil.shorts.ShortSpliterators;
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
/*     */ public class Double2ShortArrayMap
/*     */   extends AbstractDouble2ShortMap
/*     */   implements Serializable, Cloneable
/*     */ {
/*     */   private static final long serialVersionUID = 1L;
/*     */   protected transient double[] key;
/*     */   protected transient short[] value;
/*     */   protected int size;
/*     */   protected transient Double2ShortMap.FastEntrySet entries;
/*     */   protected transient DoubleSet keys;
/*     */   protected transient ShortCollection values;
/*     */   
/*     */   public Double2ShortArrayMap(double[] key, short[] value) {
/*  64 */     this.key = key;
/*  65 */     this.value = value;
/*  66 */     this.size = key.length;
/*  67 */     if (key.length != value.length) throw new IllegalArgumentException("Keys and values have different lengths (" + key.length + ", " + value.length + ")");
/*     */   
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public Double2ShortArrayMap() {
/*  74 */     this.key = DoubleArrays.EMPTY_ARRAY;
/*  75 */     this.value = ShortArrays.EMPTY_ARRAY;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Double2ShortArrayMap(int capacity) {
/*  84 */     this.key = new double[capacity];
/*  85 */     this.value = new short[capacity];
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Double2ShortArrayMap(Double2ShortMap m) {
/*  94 */     this(m.size());
/*  95 */     int i = 0;
/*  96 */     for (ObjectIterator<Double2ShortMap.Entry> objectIterator = m.double2ShortEntrySet().iterator(); objectIterator.hasNext(); ) { Double2ShortMap.Entry e = objectIterator.next();
/*  97 */       this.key[i] = e.getDoubleKey();
/*  98 */       this.value[i] = e.getShortValue();
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
/*     */   public Double2ShortArrayMap(Map<? extends Double, ? extends Short> m) {
/* 110 */     this(m.size());
/* 111 */     int i = 0;
/* 112 */     for (Map.Entry<? extends Double, ? extends Short> e : m.entrySet()) {
/* 113 */       this.key[i] = ((Double)e.getKey()).doubleValue();
/* 114 */       this.value[i] = ((Short)e.getValue()).shortValue();
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
/*     */   public Double2ShortArrayMap(double[] key, short[] value, int size) {
/* 133 */     this.key = key;
/* 134 */     this.value = value;
/* 135 */     this.size = size;
/* 136 */     if (key.length != value.length) throw new IllegalArgumentException("Keys and values have different lengths (" + key.length + ", " + value.length + ")"); 
/* 137 */     if (size > key.length) throw new IllegalArgumentException("The provided size (" + size + ") is larger than or equal to the backing-arrays size (" + key.length + ")"); 
/*     */   }
/*     */   
/*     */   private final class EntrySet
/*     */     extends AbstractObjectSet<Double2ShortMap.Entry> implements Double2ShortMap.FastEntrySet {
/*     */     private EntrySet() {}
/*     */     
/*     */     public ObjectIterator<Double2ShortMap.Entry> iterator() {
/* 145 */       return new ObjectIterator<Double2ShortMap.Entry>() {
/* 146 */           int curr = -1; int next = 0;
/*     */ 
/*     */           
/*     */           public boolean hasNext() {
/* 150 */             return (this.next < Double2ShortArrayMap.this.size);
/*     */           }
/*     */ 
/*     */ 
/*     */           
/*     */           public Double2ShortMap.Entry next() {
/* 156 */             if (!hasNext()) throw new NoSuchElementException(); 
/* 157 */             return new AbstractDouble2ShortMap.BasicEntry(Double2ShortArrayMap.this.key[this.curr = this.next], Double2ShortArrayMap.this.value[this.next++]);
/*     */           }
/*     */ 
/*     */           
/*     */           public void remove() {
/* 162 */             if (this.curr == -1) throw new IllegalStateException(); 
/* 163 */             this.curr = -1;
/* 164 */             int tail = Double2ShortArrayMap.this.size-- - this.next--;
/* 165 */             System.arraycopy(Double2ShortArrayMap.this.key, this.next + 1, Double2ShortArrayMap.this.key, this.next, tail);
/* 166 */             System.arraycopy(Double2ShortArrayMap.this.value, this.next + 1, Double2ShortArrayMap.this.value, this.next, tail);
/*     */           }
/*     */ 
/*     */ 
/*     */ 
/*     */           
/*     */           public void forEachRemaining(Consumer<? super Double2ShortMap.Entry> action) {
/* 173 */             int max = Double2ShortArrayMap.this.size;
/* 174 */             while (this.next < max) {
/* 175 */               action.accept(new AbstractDouble2ShortMap.BasicEntry(Double2ShortArrayMap.this.key[this.curr = this.next], Double2ShortArrayMap.this.value[this.next++]));
/*     */             }
/*     */           }
/*     */         };
/*     */     }
/*     */ 
/*     */     
/*     */     public ObjectIterator<Double2ShortMap.Entry> fastIterator() {
/* 183 */       return new ObjectIterator<Double2ShortMap.Entry>() {
/* 184 */           int next = 0; int curr = -1;
/* 185 */           final AbstractDouble2ShortMap.BasicEntry entry = new AbstractDouble2ShortMap.BasicEntry();
/*     */ 
/*     */           
/*     */           public boolean hasNext() {
/* 189 */             return (this.next < Double2ShortArrayMap.this.size);
/*     */           }
/*     */ 
/*     */ 
/*     */           
/*     */           public Double2ShortMap.Entry next() {
/* 195 */             if (!hasNext()) throw new NoSuchElementException(); 
/* 196 */             this.entry.key = Double2ShortArrayMap.this.key[this.curr = this.next];
/* 197 */             this.entry.value = Double2ShortArrayMap.this.value[this.next++];
/* 198 */             return this.entry;
/*     */           }
/*     */ 
/*     */           
/*     */           public void remove() {
/* 203 */             if (this.curr == -1) throw new IllegalStateException(); 
/* 204 */             this.curr = -1;
/* 205 */             int tail = Double2ShortArrayMap.this.size-- - this.next--;
/* 206 */             System.arraycopy(Double2ShortArrayMap.this.key, this.next + 1, Double2ShortArrayMap.this.key, this.next, tail);
/* 207 */             System.arraycopy(Double2ShortArrayMap.this.value, this.next + 1, Double2ShortArrayMap.this.value, this.next, tail);
/*     */           }
/*     */ 
/*     */ 
/*     */ 
/*     */           
/*     */           public void forEachRemaining(Consumer<? super Double2ShortMap.Entry> action) {
/* 214 */             int max = Double2ShortArrayMap.this.size;
/* 215 */             while (this.next < max) {
/* 216 */               this.entry.key = Double2ShortArrayMap.this.key[this.curr = this.next];
/* 217 */               this.entry.value = Double2ShortArrayMap.this.value[this.next++];
/* 218 */               action.accept(this.entry);
/*     */             } 
/*     */           }
/*     */         };
/*     */     }
/*     */     
/*     */     final class EntrySetSpliterator
/*     */       extends ObjectSpliterators.EarlyBindingSizeIndexBasedSpliterator<Double2ShortMap.Entry>
/*     */       implements ObjectSpliterator<Double2ShortMap.Entry> {
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
/*     */       protected final Double2ShortMap.Entry get(int location) {
/* 239 */         return new AbstractDouble2ShortMap.BasicEntry(Double2ShortArrayMap.this.key[location], Double2ShortArrayMap.this.value[location]);
/*     */       }
/*     */ 
/*     */       
/*     */       protected final EntrySetSpliterator makeForSplit(int pos, int maxPos) {
/* 244 */         return new EntrySetSpliterator(pos, maxPos);
/*     */       }
/*     */     }
/*     */ 
/*     */     
/*     */     public ObjectSpliterator<Double2ShortMap.Entry> spliterator() {
/* 250 */       return new EntrySetSpliterator(0, Double2ShortArrayMap.this.size);
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public void forEach(Consumer<? super Double2ShortMap.Entry> action) {
/* 258 */       for (int i = 0, max = Double2ShortArrayMap.this.size; i < max; i++) {
/* 259 */         action.accept(new AbstractDouble2ShortMap.BasicEntry(Double2ShortArrayMap.this.key[i], Double2ShortArrayMap.this.value[i]));
/*     */       }
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public void fastForEach(Consumer<? super Double2ShortMap.Entry> action) {
/* 267 */       AbstractDouble2ShortMap.BasicEntry entry = new AbstractDouble2ShortMap.BasicEntry();
/*     */       
/* 269 */       for (int i = 0, max = Double2ShortArrayMap.this.size; i < max; i++) {
/* 270 */         entry.key = Double2ShortArrayMap.this.key[i];
/* 271 */         entry.value = Double2ShortArrayMap.this.value[i];
/* 272 */         action.accept(entry);
/*     */       } 
/*     */     }
/*     */ 
/*     */     
/*     */     public int size() {
/* 278 */       return Double2ShortArrayMap.this.size;
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     public boolean contains(Object o) {
/* 284 */       if (!(o instanceof Map.Entry)) return false; 
/* 285 */       Map.Entry<?, ?> e = (Map.Entry<?, ?>)o;
/* 286 */       if (e.getKey() == null || !(e.getKey() instanceof Double)) return false; 
/* 287 */       if (e.getValue() == null || !(e.getValue() instanceof Short)) return false; 
/* 288 */       double k = ((Double)e.getKey()).doubleValue();
/* 289 */       return (Double2ShortArrayMap.this.containsKey(k) && Double2ShortArrayMap.this.get(k) == ((Short)e.getValue()).shortValue());
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     public boolean remove(Object o) {
/* 295 */       if (!(o instanceof Map.Entry)) return false; 
/* 296 */       Map.Entry<?, ?> e = (Map.Entry<?, ?>)o;
/* 297 */       if (e.getKey() == null || !(e.getKey() instanceof Double)) return false; 
/* 298 */       if (e.getValue() == null || !(e.getValue() instanceof Short)) return false; 
/* 299 */       double k = ((Double)e.getKey()).doubleValue();
/* 300 */       short v = ((Short)e.getValue()).shortValue();
/* 301 */       int oldPos = Double2ShortArrayMap.this.findKey(k);
/* 302 */       if (oldPos == -1 || v != Double2ShortArrayMap.this.value[oldPos]) return false; 
/* 303 */       int tail = Double2ShortArrayMap.this.size - oldPos - 1;
/* 304 */       System.arraycopy(Double2ShortArrayMap.this.key, oldPos + 1, Double2ShortArrayMap.this.key, oldPos, tail);
/* 305 */       System.arraycopy(Double2ShortArrayMap.this.value, oldPos + 1, Double2ShortArrayMap.this.value, oldPos, tail);
/* 306 */       Double2ShortArrayMap.this.size--;
/* 307 */       return true;
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   public Double2ShortMap.FastEntrySet double2ShortEntrySet() {
/* 313 */     if (this.entries == null) this.entries = new EntrySet(); 
/* 314 */     return this.entries;
/*     */   }
/*     */   
/*     */   private int findKey(double k) {
/* 318 */     double[] key = this.key;
/* 319 */     for (int i = this.size; i-- != 0;) { if (Double.doubleToLongBits(key[i]) == Double.doubleToLongBits(k)) return i;  }
/* 320 */      return -1;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public short get(double k) {
/* 326 */     double[] key = this.key;
/* 327 */     for (int i = this.size; i-- != 0;) { if (Double.doubleToLongBits(key[i]) == Double.doubleToLongBits(k)) return this.value[i];  }
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
/*     */   public boolean containsKey(double k) {
/* 343 */     return (findKey(k) != -1);
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean containsValue(short v) {
/* 348 */     for (int i = this.size; i-- != 0;) { if (this.value[i] == v) return true;  }
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
/*     */   public short put(double k, short v) {
/* 360 */     int oldKey = findKey(k);
/* 361 */     if (oldKey != -1) {
/* 362 */       short oldValue = this.value[oldKey];
/* 363 */       this.value[oldKey] = v;
/* 364 */       return oldValue;
/*     */     } 
/* 366 */     if (this.size == this.key.length) {
/* 367 */       double[] newKey = new double[(this.size == 0) ? 2 : (this.size * 2)];
/* 368 */       short[] newValue = new short[(this.size == 0) ? 2 : (this.size * 2)];
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
/*     */   public short remove(double k) {
/* 385 */     int oldPos = findKey(k);
/* 386 */     if (oldPos == -1) return this.defRetValue; 
/* 387 */     short oldValue = this.value[oldPos];
/* 388 */     int tail = this.size - oldPos - 1;
/* 389 */     System.arraycopy(this.key, oldPos + 1, this.key, oldPos, tail);
/* 390 */     System.arraycopy(this.value, oldPos + 1, this.value, oldPos, tail);
/* 391 */     this.size--;
/* 392 */     return oldValue;
/*     */   }
/*     */   
/*     */   private final class KeySet extends AbstractDoubleSet { private KeySet() {}
/*     */     
/*     */     public boolean contains(double k) {
/* 398 */       return (Double2ShortArrayMap.this.findKey(k) != -1);
/*     */     }
/*     */ 
/*     */     
/*     */     public boolean remove(double k) {
/* 403 */       int oldPos = Double2ShortArrayMap.this.findKey(k);
/* 404 */       if (oldPos == -1) return false; 
/* 405 */       int tail = Double2ShortArrayMap.this.size - oldPos - 1;
/* 406 */       System.arraycopy(Double2ShortArrayMap.this.key, oldPos + 1, Double2ShortArrayMap.this.key, oldPos, tail);
/* 407 */       System.arraycopy(Double2ShortArrayMap.this.value, oldPos + 1, Double2ShortArrayMap.this.value, oldPos, tail);
/* 408 */       Double2ShortArrayMap.this.size--;
/* 409 */       return true;
/*     */     }
/*     */ 
/*     */     
/*     */     public DoubleIterator iterator() {
/* 414 */       return new DoubleIterator() {
/* 415 */           int pos = 0;
/*     */ 
/*     */           
/*     */           public boolean hasNext() {
/* 419 */             return (this.pos < Double2ShortArrayMap.this.size);
/*     */           }
/*     */ 
/*     */ 
/*     */           
/*     */           public double nextDouble() {
/* 425 */             if (!hasNext()) throw new NoSuchElementException(); 
/* 426 */             return Double2ShortArrayMap.this.key[this.pos++];
/*     */           }
/*     */ 
/*     */           
/*     */           public void remove() {
/* 431 */             if (this.pos == 0) throw new IllegalStateException(); 
/* 432 */             int tail = Double2ShortArrayMap.this.size - this.pos;
/* 433 */             System.arraycopy(Double2ShortArrayMap.this.key, this.pos, Double2ShortArrayMap.this.key, this.pos - 1, tail);
/* 434 */             System.arraycopy(Double2ShortArrayMap.this.value, this.pos, Double2ShortArrayMap.this.value, this.pos - 1, tail);
/* 435 */             Double2ShortArrayMap.this.size--;
/* 436 */             this.pos--;
/*     */           }
/*     */ 
/*     */ 
/*     */ 
/*     */           
/*     */           public void forEachRemaining(DoubleConsumer action) {
/* 443 */             int max = Double2ShortArrayMap.this.size;
/* 444 */             while (this.pos < max)
/* 445 */               action.accept(Double2ShortArrayMap.this.key[this.pos++]); 
/*     */           }
/*     */         };
/*     */     }
/*     */     
/*     */     final class KeySetSpliterator
/*     */       extends DoubleSpliterators.EarlyBindingSizeIndexBasedSpliterator
/*     */       implements DoubleSpliterator {
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
/*     */       protected final double get(int location) {
/* 465 */         return Double2ShortArrayMap.this.key[location];
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
/*     */       public void forEachRemaining(DoubleConsumer action) {
/* 477 */         int max = Double2ShortArrayMap.this.size;
/* 478 */         while (this.pos < max) {
/* 479 */           action.accept(Double2ShortArrayMap.this.key[this.pos++]);
/*     */         }
/*     */       }
/*     */     }
/*     */ 
/*     */     
/*     */     public DoubleSpliterator spliterator() {
/* 486 */       return new KeySetSpliterator(0, Double2ShortArrayMap.this.size);
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public void forEach(DoubleConsumer action) {
/* 493 */       for (int i = 0, max = Double2ShortArrayMap.this.size; i < max; i++) {
/* 494 */         action.accept(Double2ShortArrayMap.this.key[i]);
/*     */       }
/*     */     }
/*     */ 
/*     */     
/*     */     public int size() {
/* 500 */       return Double2ShortArrayMap.this.size;
/*     */     }
/*     */ 
/*     */     
/*     */     public void clear() {
/* 505 */       Double2ShortArrayMap.this.clear();
/*     */     } }
/*     */ 
/*     */ 
/*     */   
/*     */   public DoubleSet keySet() {
/* 511 */     if (this.keys == null) this.keys = new KeySet(); 
/* 512 */     return this.keys;
/*     */   }
/*     */   
/*     */   private final class ValuesCollection extends AbstractShortCollection { private ValuesCollection() {}
/*     */     
/*     */     public boolean contains(short v) {
/* 518 */       return Double2ShortArrayMap.this.containsValue(v);
/*     */     }
/*     */ 
/*     */     
/*     */     public ShortIterator iterator() {
/* 523 */       return new ShortIterator() {
/* 524 */           int pos = 0;
/*     */ 
/*     */           
/*     */           public boolean hasNext() {
/* 528 */             return (this.pos < Double2ShortArrayMap.this.size);
/*     */           }
/*     */ 
/*     */ 
/*     */           
/*     */           public short nextShort() {
/* 534 */             if (!hasNext()) throw new NoSuchElementException(); 
/* 535 */             return Double2ShortArrayMap.this.value[this.pos++];
/*     */           }
/*     */ 
/*     */           
/*     */           public void remove() {
/* 540 */             if (this.pos == 0) throw new IllegalStateException(); 
/* 541 */             int tail = Double2ShortArrayMap.this.size - this.pos;
/* 542 */             System.arraycopy(Double2ShortArrayMap.this.key, this.pos, Double2ShortArrayMap.this.key, this.pos - 1, tail);
/* 543 */             System.arraycopy(Double2ShortArrayMap.this.value, this.pos, Double2ShortArrayMap.this.value, this.pos - 1, tail);
/* 544 */             Double2ShortArrayMap.this.size--;
/* 545 */             this.pos--;
/*     */           }
/*     */ 
/*     */ 
/*     */ 
/*     */           
/*     */           public void forEachRemaining(ShortConsumer action) {
/* 552 */             int max = Double2ShortArrayMap.this.size;
/* 553 */             while (this.pos < max)
/* 554 */               action.accept(Double2ShortArrayMap.this.value[this.pos++]); 
/*     */           }
/*     */         };
/*     */     }
/*     */     
/*     */     final class ValuesSpliterator
/*     */       extends ShortSpliterators.EarlyBindingSizeIndexBasedSpliterator
/*     */       implements ShortSpliterator {
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
/*     */       protected final short get(int location) {
/* 574 */         return Double2ShortArrayMap.this.value[location];
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
/*     */       public void forEachRemaining(ShortConsumer action) {
/* 586 */         int max = Double2ShortArrayMap.this.size;
/* 587 */         while (this.pos < max) {
/* 588 */           action.accept(Double2ShortArrayMap.this.value[this.pos++]);
/*     */         }
/*     */       }
/*     */     }
/*     */ 
/*     */     
/*     */     public ShortSpliterator spliterator() {
/* 595 */       return new ValuesSpliterator(0, Double2ShortArrayMap.this.size);
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public void forEach(ShortConsumer action) {
/* 602 */       for (int i = 0, max = Double2ShortArrayMap.this.size; i < max; i++) {
/* 603 */         action.accept(Double2ShortArrayMap.this.value[i]);
/*     */       }
/*     */     }
/*     */ 
/*     */     
/*     */     public int size() {
/* 609 */       return Double2ShortArrayMap.this.size;
/*     */     }
/*     */ 
/*     */     
/*     */     public void clear() {
/* 614 */       Double2ShortArrayMap.this.clear();
/*     */     } }
/*     */ 
/*     */ 
/*     */   
/*     */   public ShortCollection values() {
/* 620 */     if (this.values == null) this.values = (ShortCollection)new ValuesCollection(); 
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
/*     */   public Double2ShortArrayMap clone() {
/*     */     Double2ShortArrayMap c;
/*     */     try {
/* 638 */       c = (Double2ShortArrayMap)super.clone();
/* 639 */     } catch (CloneNotSupportedException cantHappen) {
/* 640 */       throw new InternalError();
/*     */     } 
/* 642 */     c.key = (double[])this.key.clone();
/* 643 */     c.value = (short[])this.value.clone();
/* 644 */     c.entries = null;
/* 645 */     c.keys = null;
/* 646 */     c.values = null;
/* 647 */     return c;
/*     */   }
/*     */   
/*     */   private void writeObject(ObjectOutputStream s) throws IOException {
/* 651 */     s.defaultWriteObject();
/* 652 */     for (int i = 0, max = this.size; i < max; i++) {
/* 653 */       s.writeDouble(this.key[i]);
/* 654 */       s.writeShort(this.value[i]);
/*     */     } 
/*     */   }
/*     */   
/*     */   private void readObject(ObjectInputStream s) throws IOException, ClassNotFoundException {
/* 659 */     s.defaultReadObject();
/* 660 */     this.key = new double[this.size];
/* 661 */     this.value = new short[this.size];
/* 662 */     for (int i = 0; i < this.size; i++) {
/* 663 */       this.key[i] = s.readDouble();
/* 664 */       this.value[i] = s.readShort();
/*     */     } 
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\i\\unimi\dsi\fastutil\doubles\Double2ShortArrayMap.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */