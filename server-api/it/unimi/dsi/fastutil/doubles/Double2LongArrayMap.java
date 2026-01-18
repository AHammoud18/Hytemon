/*     */ package it.unimi.dsi.fastutil.doubles;
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
/*     */ import java.util.function.DoubleConsumer;
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
/*     */ public class Double2LongArrayMap
/*     */   extends AbstractDouble2LongMap
/*     */   implements Serializable, Cloneable
/*     */ {
/*     */   private static final long serialVersionUID = 1L;
/*     */   protected transient double[] key;
/*     */   protected transient long[] value;
/*     */   protected int size;
/*     */   protected transient Double2LongMap.FastEntrySet entries;
/*     */   protected transient DoubleSet keys;
/*     */   protected transient LongCollection values;
/*     */   
/*     */   public Double2LongArrayMap(double[] key, long[] value) {
/*  63 */     this.key = key;
/*  64 */     this.value = value;
/*  65 */     this.size = key.length;
/*  66 */     if (key.length != value.length) throw new IllegalArgumentException("Keys and values have different lengths (" + key.length + ", " + value.length + ")");
/*     */   
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public Double2LongArrayMap() {
/*  73 */     this.key = DoubleArrays.EMPTY_ARRAY;
/*  74 */     this.value = LongArrays.EMPTY_ARRAY;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Double2LongArrayMap(int capacity) {
/*  83 */     this.key = new double[capacity];
/*  84 */     this.value = new long[capacity];
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Double2LongArrayMap(Double2LongMap m) {
/*  93 */     this(m.size());
/*  94 */     int i = 0;
/*  95 */     for (ObjectIterator<Double2LongMap.Entry> objectIterator = m.double2LongEntrySet().iterator(); objectIterator.hasNext(); ) { Double2LongMap.Entry e = objectIterator.next();
/*  96 */       this.key[i] = e.getDoubleKey();
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
/*     */   public Double2LongArrayMap(Map<? extends Double, ? extends Long> m) {
/* 109 */     this(m.size());
/* 110 */     int i = 0;
/* 111 */     for (Map.Entry<? extends Double, ? extends Long> e : m.entrySet()) {
/* 112 */       this.key[i] = ((Double)e.getKey()).doubleValue();
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
/*     */   public Double2LongArrayMap(double[] key, long[] value, int size) {
/* 132 */     this.key = key;
/* 133 */     this.value = value;
/* 134 */     this.size = size;
/* 135 */     if (key.length != value.length) throw new IllegalArgumentException("Keys and values have different lengths (" + key.length + ", " + value.length + ")"); 
/* 136 */     if (size > key.length) throw new IllegalArgumentException("The provided size (" + size + ") is larger than or equal to the backing-arrays size (" + key.length + ")"); 
/*     */   }
/*     */   
/*     */   private final class EntrySet
/*     */     extends AbstractObjectSet<Double2LongMap.Entry> implements Double2LongMap.FastEntrySet {
/*     */     private EntrySet() {}
/*     */     
/*     */     public ObjectIterator<Double2LongMap.Entry> iterator() {
/* 144 */       return new ObjectIterator<Double2LongMap.Entry>() {
/* 145 */           int curr = -1; int next = 0;
/*     */ 
/*     */           
/*     */           public boolean hasNext() {
/* 149 */             return (this.next < Double2LongArrayMap.this.size);
/*     */           }
/*     */ 
/*     */ 
/*     */           
/*     */           public Double2LongMap.Entry next() {
/* 155 */             if (!hasNext()) throw new NoSuchElementException(); 
/* 156 */             return new AbstractDouble2LongMap.BasicEntry(Double2LongArrayMap.this.key[this.curr = this.next], Double2LongArrayMap.this.value[this.next++]);
/*     */           }
/*     */ 
/*     */           
/*     */           public void remove() {
/* 161 */             if (this.curr == -1) throw new IllegalStateException(); 
/* 162 */             this.curr = -1;
/* 163 */             int tail = Double2LongArrayMap.this.size-- - this.next--;
/* 164 */             System.arraycopy(Double2LongArrayMap.this.key, this.next + 1, Double2LongArrayMap.this.key, this.next, tail);
/* 165 */             System.arraycopy(Double2LongArrayMap.this.value, this.next + 1, Double2LongArrayMap.this.value, this.next, tail);
/*     */           }
/*     */ 
/*     */ 
/*     */ 
/*     */           
/*     */           public void forEachRemaining(Consumer<? super Double2LongMap.Entry> action) {
/* 172 */             int max = Double2LongArrayMap.this.size;
/* 173 */             while (this.next < max) {
/* 174 */               action.accept(new AbstractDouble2LongMap.BasicEntry(Double2LongArrayMap.this.key[this.curr = this.next], Double2LongArrayMap.this.value[this.next++]));
/*     */             }
/*     */           }
/*     */         };
/*     */     }
/*     */ 
/*     */     
/*     */     public ObjectIterator<Double2LongMap.Entry> fastIterator() {
/* 182 */       return new ObjectIterator<Double2LongMap.Entry>() {
/* 183 */           int next = 0; int curr = -1;
/* 184 */           final AbstractDouble2LongMap.BasicEntry entry = new AbstractDouble2LongMap.BasicEntry();
/*     */ 
/*     */           
/*     */           public boolean hasNext() {
/* 188 */             return (this.next < Double2LongArrayMap.this.size);
/*     */           }
/*     */ 
/*     */ 
/*     */           
/*     */           public Double2LongMap.Entry next() {
/* 194 */             if (!hasNext()) throw new NoSuchElementException(); 
/* 195 */             this.entry.key = Double2LongArrayMap.this.key[this.curr = this.next];
/* 196 */             this.entry.value = Double2LongArrayMap.this.value[this.next++];
/* 197 */             return this.entry;
/*     */           }
/*     */ 
/*     */           
/*     */           public void remove() {
/* 202 */             if (this.curr == -1) throw new IllegalStateException(); 
/* 203 */             this.curr = -1;
/* 204 */             int tail = Double2LongArrayMap.this.size-- - this.next--;
/* 205 */             System.arraycopy(Double2LongArrayMap.this.key, this.next + 1, Double2LongArrayMap.this.key, this.next, tail);
/* 206 */             System.arraycopy(Double2LongArrayMap.this.value, this.next + 1, Double2LongArrayMap.this.value, this.next, tail);
/*     */           }
/*     */ 
/*     */ 
/*     */ 
/*     */           
/*     */           public void forEachRemaining(Consumer<? super Double2LongMap.Entry> action) {
/* 213 */             int max = Double2LongArrayMap.this.size;
/* 214 */             while (this.next < max) {
/* 215 */               this.entry.key = Double2LongArrayMap.this.key[this.curr = this.next];
/* 216 */               this.entry.value = Double2LongArrayMap.this.value[this.next++];
/* 217 */               action.accept(this.entry);
/*     */             } 
/*     */           }
/*     */         };
/*     */     }
/*     */     
/*     */     final class EntrySetSpliterator
/*     */       extends ObjectSpliterators.EarlyBindingSizeIndexBasedSpliterator<Double2LongMap.Entry>
/*     */       implements ObjectSpliterator<Double2LongMap.Entry> {
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
/*     */       protected final Double2LongMap.Entry get(int location) {
/* 238 */         return new AbstractDouble2LongMap.BasicEntry(Double2LongArrayMap.this.key[location], Double2LongArrayMap.this.value[location]);
/*     */       }
/*     */ 
/*     */       
/*     */       protected final EntrySetSpliterator makeForSplit(int pos, int maxPos) {
/* 243 */         return new EntrySetSpliterator(pos, maxPos);
/*     */       }
/*     */     }
/*     */ 
/*     */     
/*     */     public ObjectSpliterator<Double2LongMap.Entry> spliterator() {
/* 249 */       return new EntrySetSpliterator(0, Double2LongArrayMap.this.size);
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public void forEach(Consumer<? super Double2LongMap.Entry> action) {
/* 257 */       for (int i = 0, max = Double2LongArrayMap.this.size; i < max; i++) {
/* 258 */         action.accept(new AbstractDouble2LongMap.BasicEntry(Double2LongArrayMap.this.key[i], Double2LongArrayMap.this.value[i]));
/*     */       }
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public void fastForEach(Consumer<? super Double2LongMap.Entry> action) {
/* 266 */       AbstractDouble2LongMap.BasicEntry entry = new AbstractDouble2LongMap.BasicEntry();
/*     */       
/* 268 */       for (int i = 0, max = Double2LongArrayMap.this.size; i < max; i++) {
/* 269 */         entry.key = Double2LongArrayMap.this.key[i];
/* 270 */         entry.value = Double2LongArrayMap.this.value[i];
/* 271 */         action.accept(entry);
/*     */       } 
/*     */     }
/*     */ 
/*     */     
/*     */     public int size() {
/* 277 */       return Double2LongArrayMap.this.size;
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     public boolean contains(Object o) {
/* 283 */       if (!(o instanceof Map.Entry)) return false; 
/* 284 */       Map.Entry<?, ?> e = (Map.Entry<?, ?>)o;
/* 285 */       if (e.getKey() == null || !(e.getKey() instanceof Double)) return false; 
/* 286 */       if (e.getValue() == null || !(e.getValue() instanceof Long)) return false; 
/* 287 */       double k = ((Double)e.getKey()).doubleValue();
/* 288 */       return (Double2LongArrayMap.this.containsKey(k) && Double2LongArrayMap.this.get(k) == ((Long)e.getValue()).longValue());
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     public boolean remove(Object o) {
/* 294 */       if (!(o instanceof Map.Entry)) return false; 
/* 295 */       Map.Entry<?, ?> e = (Map.Entry<?, ?>)o;
/* 296 */       if (e.getKey() == null || !(e.getKey() instanceof Double)) return false; 
/* 297 */       if (e.getValue() == null || !(e.getValue() instanceof Long)) return false; 
/* 298 */       double k = ((Double)e.getKey()).doubleValue();
/* 299 */       long v = ((Long)e.getValue()).longValue();
/* 300 */       int oldPos = Double2LongArrayMap.this.findKey(k);
/* 301 */       if (oldPos == -1 || v != Double2LongArrayMap.this.value[oldPos]) return false; 
/* 302 */       int tail = Double2LongArrayMap.this.size - oldPos - 1;
/* 303 */       System.arraycopy(Double2LongArrayMap.this.key, oldPos + 1, Double2LongArrayMap.this.key, oldPos, tail);
/* 304 */       System.arraycopy(Double2LongArrayMap.this.value, oldPos + 1, Double2LongArrayMap.this.value, oldPos, tail);
/* 305 */       Double2LongArrayMap.this.size--;
/* 306 */       return true;
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   public Double2LongMap.FastEntrySet double2LongEntrySet() {
/* 312 */     if (this.entries == null) this.entries = new EntrySet(); 
/* 313 */     return this.entries;
/*     */   }
/*     */   
/*     */   private int findKey(double k) {
/* 317 */     double[] key = this.key;
/* 318 */     for (int i = this.size; i-- != 0;) { if (Double.doubleToLongBits(key[i]) == Double.doubleToLongBits(k)) return i;  }
/* 319 */      return -1;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public long get(double k) {
/* 325 */     double[] key = this.key;
/* 326 */     for (int i = this.size; i-- != 0;) { if (Double.doubleToLongBits(key[i]) == Double.doubleToLongBits(k)) return this.value[i];  }
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
/*     */   public boolean containsKey(double k) {
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
/*     */   public long put(double k, long v) {
/* 359 */     int oldKey = findKey(k);
/* 360 */     if (oldKey != -1) {
/* 361 */       long oldValue = this.value[oldKey];
/* 362 */       this.value[oldKey] = v;
/* 363 */       return oldValue;
/*     */     } 
/* 365 */     if (this.size == this.key.length) {
/* 366 */       double[] newKey = new double[(this.size == 0) ? 2 : (this.size * 2)];
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
/*     */   public long remove(double k) {
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
/*     */   private final class KeySet extends AbstractDoubleSet { private KeySet() {}
/*     */     
/*     */     public boolean contains(double k) {
/* 397 */       return (Double2LongArrayMap.this.findKey(k) != -1);
/*     */     }
/*     */ 
/*     */     
/*     */     public boolean remove(double k) {
/* 402 */       int oldPos = Double2LongArrayMap.this.findKey(k);
/* 403 */       if (oldPos == -1) return false; 
/* 404 */       int tail = Double2LongArrayMap.this.size - oldPos - 1;
/* 405 */       System.arraycopy(Double2LongArrayMap.this.key, oldPos + 1, Double2LongArrayMap.this.key, oldPos, tail);
/* 406 */       System.arraycopy(Double2LongArrayMap.this.value, oldPos + 1, Double2LongArrayMap.this.value, oldPos, tail);
/* 407 */       Double2LongArrayMap.this.size--;
/* 408 */       return true;
/*     */     }
/*     */ 
/*     */     
/*     */     public DoubleIterator iterator() {
/* 413 */       return new DoubleIterator() {
/* 414 */           int pos = 0;
/*     */ 
/*     */           
/*     */           public boolean hasNext() {
/* 418 */             return (this.pos < Double2LongArrayMap.this.size);
/*     */           }
/*     */ 
/*     */ 
/*     */           
/*     */           public double nextDouble() {
/* 424 */             if (!hasNext()) throw new NoSuchElementException(); 
/* 425 */             return Double2LongArrayMap.this.key[this.pos++];
/*     */           }
/*     */ 
/*     */           
/*     */           public void remove() {
/* 430 */             if (this.pos == 0) throw new IllegalStateException(); 
/* 431 */             int tail = Double2LongArrayMap.this.size - this.pos;
/* 432 */             System.arraycopy(Double2LongArrayMap.this.key, this.pos, Double2LongArrayMap.this.key, this.pos - 1, tail);
/* 433 */             System.arraycopy(Double2LongArrayMap.this.value, this.pos, Double2LongArrayMap.this.value, this.pos - 1, tail);
/* 434 */             Double2LongArrayMap.this.size--;
/* 435 */             this.pos--;
/*     */           }
/*     */ 
/*     */ 
/*     */ 
/*     */           
/*     */           public void forEachRemaining(DoubleConsumer action) {
/* 442 */             int max = Double2LongArrayMap.this.size;
/* 443 */             while (this.pos < max)
/* 444 */               action.accept(Double2LongArrayMap.this.key[this.pos++]); 
/*     */           }
/*     */         };
/*     */     }
/*     */     
/*     */     final class KeySetSpliterator
/*     */       extends DoubleSpliterators.EarlyBindingSizeIndexBasedSpliterator
/*     */       implements DoubleSpliterator {
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
/*     */       protected final double get(int location) {
/* 464 */         return Double2LongArrayMap.this.key[location];
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
/*     */       public void forEachRemaining(DoubleConsumer action) {
/* 476 */         int max = Double2LongArrayMap.this.size;
/* 477 */         while (this.pos < max) {
/* 478 */           action.accept(Double2LongArrayMap.this.key[this.pos++]);
/*     */         }
/*     */       }
/*     */     }
/*     */ 
/*     */     
/*     */     public DoubleSpliterator spliterator() {
/* 485 */       return new KeySetSpliterator(0, Double2LongArrayMap.this.size);
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public void forEach(DoubleConsumer action) {
/* 492 */       for (int i = 0, max = Double2LongArrayMap.this.size; i < max; i++) {
/* 493 */         action.accept(Double2LongArrayMap.this.key[i]);
/*     */       }
/*     */     }
/*     */ 
/*     */     
/*     */     public int size() {
/* 499 */       return Double2LongArrayMap.this.size;
/*     */     }
/*     */ 
/*     */     
/*     */     public void clear() {
/* 504 */       Double2LongArrayMap.this.clear();
/*     */     } }
/*     */ 
/*     */ 
/*     */   
/*     */   public DoubleSet keySet() {
/* 510 */     if (this.keys == null) this.keys = new KeySet(); 
/* 511 */     return this.keys;
/*     */   }
/*     */   
/*     */   private final class ValuesCollection extends AbstractLongCollection { private ValuesCollection() {}
/*     */     
/*     */     public boolean contains(long v) {
/* 517 */       return Double2LongArrayMap.this.containsValue(v);
/*     */     }
/*     */ 
/*     */     
/*     */     public LongIterator iterator() {
/* 522 */       return new LongIterator() {
/* 523 */           int pos = 0;
/*     */ 
/*     */           
/*     */           public boolean hasNext() {
/* 527 */             return (this.pos < Double2LongArrayMap.this.size);
/*     */           }
/*     */ 
/*     */ 
/*     */           
/*     */           public long nextLong() {
/* 533 */             if (!hasNext()) throw new NoSuchElementException(); 
/* 534 */             return Double2LongArrayMap.this.value[this.pos++];
/*     */           }
/*     */ 
/*     */           
/*     */           public void remove() {
/* 539 */             if (this.pos == 0) throw new IllegalStateException(); 
/* 540 */             int tail = Double2LongArrayMap.this.size - this.pos;
/* 541 */             System.arraycopy(Double2LongArrayMap.this.key, this.pos, Double2LongArrayMap.this.key, this.pos - 1, tail);
/* 542 */             System.arraycopy(Double2LongArrayMap.this.value, this.pos, Double2LongArrayMap.this.value, this.pos - 1, tail);
/* 543 */             Double2LongArrayMap.this.size--;
/* 544 */             this.pos--;
/*     */           }
/*     */ 
/*     */ 
/*     */ 
/*     */           
/*     */           public void forEachRemaining(LongConsumer action) {
/* 551 */             int max = Double2LongArrayMap.this.size;
/* 552 */             while (this.pos < max)
/* 553 */               action.accept(Double2LongArrayMap.this.value[this.pos++]); 
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
/* 573 */         return Double2LongArrayMap.this.value[location];
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
/* 585 */         int max = Double2LongArrayMap.this.size;
/* 586 */         while (this.pos < max) {
/* 587 */           action.accept(Double2LongArrayMap.this.value[this.pos++]);
/*     */         }
/*     */       }
/*     */     }
/*     */ 
/*     */     
/*     */     public LongSpliterator spliterator() {
/* 594 */       return new ValuesSpliterator(0, Double2LongArrayMap.this.size);
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public void forEach(LongConsumer action) {
/* 601 */       for (int i = 0, max = Double2LongArrayMap.this.size; i < max; i++) {
/* 602 */         action.accept(Double2LongArrayMap.this.value[i]);
/*     */       }
/*     */     }
/*     */ 
/*     */     
/*     */     public int size() {
/* 608 */       return Double2LongArrayMap.this.size;
/*     */     }
/*     */ 
/*     */     
/*     */     public void clear() {
/* 613 */       Double2LongArrayMap.this.clear();
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
/*     */   public Double2LongArrayMap clone() {
/*     */     Double2LongArrayMap c;
/*     */     try {
/* 637 */       c = (Double2LongArrayMap)super.clone();
/* 638 */     } catch (CloneNotSupportedException cantHappen) {
/* 639 */       throw new InternalError();
/*     */     } 
/* 641 */     c.key = (double[])this.key.clone();
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
/* 652 */       s.writeDouble(this.key[i]);
/* 653 */       s.writeLong(this.value[i]);
/*     */     } 
/*     */   }
/*     */   
/*     */   private void readObject(ObjectInputStream s) throws IOException, ClassNotFoundException {
/* 658 */     s.defaultReadObject();
/* 659 */     this.key = new double[this.size];
/* 660 */     this.value = new long[this.size];
/* 661 */     for (int i = 0; i < this.size; i++) {
/* 662 */       this.key[i] = s.readDouble();
/* 663 */       this.value[i] = s.readLong();
/*     */     } 
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\i\\unimi\dsi\fastutil\doubles\Double2LongArrayMap.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */