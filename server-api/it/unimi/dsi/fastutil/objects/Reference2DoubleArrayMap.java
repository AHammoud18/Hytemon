/*     */ package it.unimi.dsi.fastutil.objects;
/*     */ 
/*     */ import it.unimi.dsi.fastutil.doubles.AbstractDoubleCollection;
/*     */ import it.unimi.dsi.fastutil.doubles.DoubleArrays;
/*     */ import it.unimi.dsi.fastutil.doubles.DoubleCollection;
/*     */ import it.unimi.dsi.fastutil.doubles.DoubleIterator;
/*     */ import it.unimi.dsi.fastutil.doubles.DoubleSpliterator;
/*     */ import it.unimi.dsi.fastutil.doubles.DoubleSpliterators;
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
/*     */ public class Reference2DoubleArrayMap<K>
/*     */   extends AbstractReference2DoubleMap<K>
/*     */   implements Serializable, Cloneable
/*     */ {
/*     */   private static final long serialVersionUID = 1L;
/*     */   protected transient Object[] key;
/*     */   protected transient double[] value;
/*     */   protected int size;
/*     */   protected transient Reference2DoubleMap.FastEntrySet<K> entries;
/*     */   protected transient ReferenceSet<K> keys;
/*     */   protected transient DoubleCollection values;
/*     */   
/*     */   public Reference2DoubleArrayMap(Object[] key, double[] value) {
/*  59 */     this.key = key;
/*  60 */     this.value = value;
/*  61 */     this.size = key.length;
/*  62 */     if (key.length != value.length) throw new IllegalArgumentException("Keys and values have different lengths (" + key.length + ", " + value.length + ")");
/*     */   
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public Reference2DoubleArrayMap() {
/*  69 */     this.key = ObjectArrays.EMPTY_ARRAY;
/*  70 */     this.value = DoubleArrays.EMPTY_ARRAY;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Reference2DoubleArrayMap(int capacity) {
/*  79 */     this.key = new Object[capacity];
/*  80 */     this.value = new double[capacity];
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Reference2DoubleArrayMap(Reference2DoubleMap<K> m) {
/*  89 */     this(m.size());
/*  90 */     int i = 0;
/*  91 */     for (ObjectIterator<Reference2DoubleMap.Entry<K>> objectIterator = m.reference2DoubleEntrySet().iterator(); objectIterator.hasNext(); ) { Reference2DoubleMap.Entry<K> e = objectIterator.next();
/*  92 */       this.key[i] = e.getKey();
/*  93 */       this.value[i] = e.getDoubleValue();
/*  94 */       i++; }
/*     */     
/*  96 */     this.size = i;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Reference2DoubleArrayMap(Map<? extends K, ? extends Double> m) {
/* 105 */     this(m.size());
/* 106 */     int i = 0;
/* 107 */     for (Map.Entry<? extends K, ? extends Double> e : m.entrySet()) {
/* 108 */       this.key[i] = e.getKey();
/* 109 */       this.value[i] = ((Double)e.getValue()).doubleValue();
/* 110 */       i++;
/*     */     } 
/* 112 */     this.size = i;
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
/*     */   public Reference2DoubleArrayMap(Object[] key, double[] value, int size) {
/* 128 */     this.key = key;
/* 129 */     this.value = value;
/* 130 */     this.size = size;
/* 131 */     if (key.length != value.length) throw new IllegalArgumentException("Keys and values have different lengths (" + key.length + ", " + value.length + ")"); 
/* 132 */     if (size > key.length) throw new IllegalArgumentException("The provided size (" + size + ") is larger than or equal to the backing-arrays size (" + key.length + ")"); 
/*     */   }
/*     */   
/*     */   private final class EntrySet
/*     */     extends AbstractObjectSet<Reference2DoubleMap.Entry<K>> implements Reference2DoubleMap.FastEntrySet<K> {
/*     */     private EntrySet() {}
/*     */     
/*     */     public ObjectIterator<Reference2DoubleMap.Entry<K>> iterator() {
/* 140 */       return (ObjectIterator)new ObjectIterator<Reference2DoubleMap.Entry<Reference2DoubleMap.Entry<K>>>() {
/* 141 */           int curr = -1; int next = 0;
/*     */ 
/*     */           
/*     */           public boolean hasNext() {
/* 145 */             return (this.next < Reference2DoubleArrayMap.this.size);
/*     */           }
/*     */ 
/*     */ 
/*     */           
/*     */           public Reference2DoubleMap.Entry<K> next() {
/* 151 */             if (!hasNext()) throw new NoSuchElementException(); 
/* 152 */             return new AbstractReference2DoubleMap.BasicEntry<>((K)Reference2DoubleArrayMap.this.key[this.curr = this.next], Reference2DoubleArrayMap.this.value[this.next++]);
/*     */           }
/*     */ 
/*     */           
/*     */           public void remove() {
/* 157 */             if (this.curr == -1) throw new IllegalStateException(); 
/* 158 */             this.curr = -1;
/* 159 */             int tail = Reference2DoubleArrayMap.this.size-- - this.next--;
/* 160 */             System.arraycopy(Reference2DoubleArrayMap.this.key, this.next + 1, Reference2DoubleArrayMap.this.key, this.next, tail);
/* 161 */             System.arraycopy(Reference2DoubleArrayMap.this.value, this.next + 1, Reference2DoubleArrayMap.this.value, this.next, tail);
/* 162 */             Reference2DoubleArrayMap.this.key[Reference2DoubleArrayMap.this.size] = null;
/*     */           }
/*     */ 
/*     */ 
/*     */ 
/*     */           
/*     */           public void forEachRemaining(Consumer<? super Reference2DoubleMap.Entry<K>> action) {
/* 169 */             int max = Reference2DoubleArrayMap.this.size;
/* 170 */             while (this.next < max) {
/* 171 */               action.accept(new AbstractReference2DoubleMap.BasicEntry<>((K)Reference2DoubleArrayMap.this.key[this.curr = this.next], Reference2DoubleArrayMap.this.value[this.next++]));
/*     */             }
/*     */           }
/*     */         };
/*     */     }
/*     */ 
/*     */     
/*     */     public ObjectIterator<Reference2DoubleMap.Entry<K>> fastIterator() {
/* 179 */       return (ObjectIterator)new ObjectIterator<Reference2DoubleMap.Entry<Reference2DoubleMap.Entry<K>>>() {
/* 180 */           int next = 0; int curr = -1;
/* 181 */           final AbstractReference2DoubleMap.BasicEntry<K> entry = new AbstractReference2DoubleMap.BasicEntry<>();
/*     */ 
/*     */           
/*     */           public boolean hasNext() {
/* 185 */             return (this.next < Reference2DoubleArrayMap.this.size);
/*     */           }
/*     */ 
/*     */ 
/*     */           
/*     */           public Reference2DoubleMap.Entry<K> next() {
/* 191 */             if (!hasNext()) throw new NoSuchElementException(); 
/* 192 */             this.entry.key = (K)Reference2DoubleArrayMap.this.key[this.curr = this.next];
/* 193 */             this.entry.value = Reference2DoubleArrayMap.this.value[this.next++];
/* 194 */             return this.entry;
/*     */           }
/*     */ 
/*     */           
/*     */           public void remove() {
/* 199 */             if (this.curr == -1) throw new IllegalStateException(); 
/* 200 */             this.curr = -1;
/* 201 */             int tail = Reference2DoubleArrayMap.this.size-- - this.next--;
/* 202 */             System.arraycopy(Reference2DoubleArrayMap.this.key, this.next + 1, Reference2DoubleArrayMap.this.key, this.next, tail);
/* 203 */             System.arraycopy(Reference2DoubleArrayMap.this.value, this.next + 1, Reference2DoubleArrayMap.this.value, this.next, tail);
/* 204 */             Reference2DoubleArrayMap.this.key[Reference2DoubleArrayMap.this.size] = null;
/*     */           }
/*     */ 
/*     */ 
/*     */ 
/*     */           
/*     */           public void forEachRemaining(Consumer<? super Reference2DoubleMap.Entry<K>> action) {
/* 211 */             int max = Reference2DoubleArrayMap.this.size;
/* 212 */             while (this.next < max) {
/* 213 */               this.entry.key = (K)Reference2DoubleArrayMap.this.key[this.curr = this.next];
/* 214 */               this.entry.value = Reference2DoubleArrayMap.this.value[this.next++];
/* 215 */               action.accept(this.entry);
/*     */             } 
/*     */           }
/*     */         };
/*     */     }
/*     */     
/*     */     final class EntrySetSpliterator
/*     */       extends ObjectSpliterators.EarlyBindingSizeIndexBasedSpliterator<Reference2DoubleMap.Entry<K>>
/*     */       implements ObjectSpliterator<Reference2DoubleMap.Entry<K>> {
/*     */       EntrySetSpliterator(int pos, int maxPos) {
/* 225 */         super(pos, maxPos);
/*     */       }
/*     */ 
/*     */       
/*     */       public int characteristics() {
/* 230 */         return 16465;
/*     */       }
/*     */ 
/*     */ 
/*     */       
/*     */       protected final Reference2DoubleMap.Entry<K> get(int location) {
/* 236 */         return new AbstractReference2DoubleMap.BasicEntry<>((K)Reference2DoubleArrayMap.this.key[location], Reference2DoubleArrayMap.this.value[location]);
/*     */       }
/*     */ 
/*     */       
/*     */       protected final EntrySetSpliterator makeForSplit(int pos, int maxPos) {
/* 241 */         return new EntrySetSpliterator(pos, maxPos);
/*     */       }
/*     */     }
/*     */ 
/*     */     
/*     */     public ObjectSpliterator<Reference2DoubleMap.Entry<K>> spliterator() {
/* 247 */       return new EntrySetSpliterator(0, Reference2DoubleArrayMap.this.size);
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public void forEach(Consumer<? super Reference2DoubleMap.Entry<K>> action) {
/* 255 */       for (int i = 0, max = Reference2DoubleArrayMap.this.size; i < max; i++) {
/* 256 */         action.accept(new AbstractReference2DoubleMap.BasicEntry<>((K)Reference2DoubleArrayMap.this.key[i], Reference2DoubleArrayMap.this.value[i]));
/*     */       }
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public void fastForEach(Consumer<? super Reference2DoubleMap.Entry<K>> action) {
/* 264 */       AbstractReference2DoubleMap.BasicEntry<K> entry = new AbstractReference2DoubleMap.BasicEntry<>();
/*     */       
/* 266 */       for (int i = 0, max = Reference2DoubleArrayMap.this.size; i < max; i++) {
/* 267 */         entry.key = (K)Reference2DoubleArrayMap.this.key[i];
/* 268 */         entry.value = Reference2DoubleArrayMap.this.value[i];
/* 269 */         action.accept(entry);
/*     */       } 
/*     */     }
/*     */ 
/*     */     
/*     */     public int size() {
/* 275 */       return Reference2DoubleArrayMap.this.size;
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     public boolean contains(Object o) {
/* 281 */       if (!(o instanceof Map.Entry)) return false; 
/* 282 */       Map.Entry<?, ?> e = (Map.Entry<?, ?>)o;
/* 283 */       if (e.getValue() == null || !(e.getValue() instanceof Double)) return false; 
/* 284 */       K k = (K)e.getKey();
/* 285 */       return (Reference2DoubleArrayMap.this.containsKey(k) && Double.doubleToLongBits(Reference2DoubleArrayMap.this.getDouble(k)) == Double.doubleToLongBits(((Double)e.getValue()).doubleValue()));
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     public boolean remove(Object o) {
/* 291 */       if (!(o instanceof Map.Entry)) return false; 
/* 292 */       Map.Entry<?, ?> e = (Map.Entry<?, ?>)o;
/* 293 */       if (e.getValue() == null || !(e.getValue() instanceof Double)) return false; 
/* 294 */       K k = (K)e.getKey();
/* 295 */       double v = ((Double)e.getValue()).doubleValue();
/* 296 */       int oldPos = Reference2DoubleArrayMap.this.findKey(k);
/* 297 */       if (oldPos == -1 || Double.doubleToLongBits(v) != Double.doubleToLongBits(Reference2DoubleArrayMap.this.value[oldPos])) return false; 
/* 298 */       int tail = Reference2DoubleArrayMap.this.size - oldPos - 1;
/* 299 */       System.arraycopy(Reference2DoubleArrayMap.this.key, oldPos + 1, Reference2DoubleArrayMap.this.key, oldPos, tail);
/* 300 */       System.arraycopy(Reference2DoubleArrayMap.this.value, oldPos + 1, Reference2DoubleArrayMap.this.value, oldPos, tail);
/* 301 */       Reference2DoubleArrayMap.this.size--;
/* 302 */       Reference2DoubleArrayMap.this.key[Reference2DoubleArrayMap.this.size] = null;
/* 303 */       return true;
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   public Reference2DoubleMap.FastEntrySet<K> reference2DoubleEntrySet() {
/* 309 */     if (this.entries == null) this.entries = new EntrySet(); 
/* 310 */     return this.entries;
/*     */   }
/*     */   
/*     */   private int findKey(Object k) {
/* 314 */     Object[] key = this.key;
/* 315 */     for (int i = this.size; i-- != 0;) { if (key[i] == k) return i;  }
/* 316 */      return -1;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public double getDouble(Object k) {
/* 322 */     Object[] key = this.key;
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
/* 334 */     for (int i = this.size; i-- != 0;) {
/* 335 */       this.key[i] = null;
/*     */     }
/* 337 */     this.size = 0;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean containsKey(Object k) {
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
/*     */   public double put(K k, double v) {
/* 359 */     int oldKey = findKey(k);
/* 360 */     if (oldKey != -1) {
/* 361 */       double oldValue = this.value[oldKey];
/* 362 */       this.value[oldKey] = v;
/* 363 */       return oldValue;
/*     */     } 
/* 365 */     if (this.size == this.key.length) {
/* 366 */       Object[] newKey = new Object[(this.size == 0) ? 2 : (this.size * 2)];
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
/*     */   public double removeDouble(Object k) {
/* 384 */     int oldPos = findKey(k);
/* 385 */     if (oldPos == -1) return this.defRetValue; 
/* 386 */     double oldValue = this.value[oldPos];
/* 387 */     int tail = this.size - oldPos - 1;
/* 388 */     System.arraycopy(this.key, oldPos + 1, this.key, oldPos, tail);
/* 389 */     System.arraycopy(this.value, oldPos + 1, this.value, oldPos, tail);
/* 390 */     this.size--;
/* 391 */     this.key[this.size] = null;
/* 392 */     return oldValue;
/*     */   }
/*     */   
/*     */   private final class KeySet extends AbstractReferenceSet<K> { private KeySet() {}
/*     */     
/*     */     public boolean contains(Object k) {
/* 398 */       return (Reference2DoubleArrayMap.this.findKey(k) != -1);
/*     */     }
/*     */ 
/*     */     
/*     */     public boolean remove(Object k) {
/* 403 */       int oldPos = Reference2DoubleArrayMap.this.findKey(k);
/* 404 */       if (oldPos == -1) return false; 
/* 405 */       int tail = Reference2DoubleArrayMap.this.size - oldPos - 1;
/* 406 */       System.arraycopy(Reference2DoubleArrayMap.this.key, oldPos + 1, Reference2DoubleArrayMap.this.key, oldPos, tail);
/* 407 */       System.arraycopy(Reference2DoubleArrayMap.this.value, oldPos + 1, Reference2DoubleArrayMap.this.value, oldPos, tail);
/* 408 */       Reference2DoubleArrayMap.this.size--;
/* 409 */       Reference2DoubleArrayMap.this.key[Reference2DoubleArrayMap.this.size] = null;
/* 410 */       return true;
/*     */     }
/*     */ 
/*     */     
/*     */     public ObjectIterator<K> iterator() {
/* 415 */       return new ObjectIterator<K>() {
/* 416 */           int pos = 0;
/*     */ 
/*     */           
/*     */           public boolean hasNext() {
/* 420 */             return (this.pos < Reference2DoubleArrayMap.this.size);
/*     */           }
/*     */ 
/*     */ 
/*     */           
/*     */           public K next() {
/* 426 */             if (!hasNext()) throw new NoSuchElementException(); 
/* 427 */             return (K)Reference2DoubleArrayMap.this.key[this.pos++];
/*     */           }
/*     */ 
/*     */           
/*     */           public void remove() {
/* 432 */             if (this.pos == 0) throw new IllegalStateException(); 
/* 433 */             int tail = Reference2DoubleArrayMap.this.size - this.pos;
/* 434 */             System.arraycopy(Reference2DoubleArrayMap.this.key, this.pos, Reference2DoubleArrayMap.this.key, this.pos - 1, tail);
/* 435 */             System.arraycopy(Reference2DoubleArrayMap.this.value, this.pos, Reference2DoubleArrayMap.this.value, this.pos - 1, tail);
/* 436 */             Reference2DoubleArrayMap.this.size--;
/* 437 */             this.pos--;
/* 438 */             Reference2DoubleArrayMap.this.key[Reference2DoubleArrayMap.this.size] = null;
/*     */           }
/*     */ 
/*     */ 
/*     */ 
/*     */           
/*     */           public void forEachRemaining(Consumer<? super K> action) {
/* 445 */             int max = Reference2DoubleArrayMap.this.size;
/* 446 */             while (this.pos < max)
/* 447 */               action.accept((K)Reference2DoubleArrayMap.this.key[this.pos++]); 
/*     */           }
/*     */         };
/*     */     }
/*     */     
/*     */     final class KeySetSpliterator
/*     */       extends ObjectSpliterators.EarlyBindingSizeIndexBasedSpliterator<K>
/*     */       implements ObjectSpliterator<K> {
/*     */       KeySetSpliterator(int pos, int maxPos) {
/* 456 */         super(pos, maxPos);
/*     */       }
/*     */ 
/*     */       
/*     */       public int characteristics() {
/* 461 */         return 16465;
/*     */       }
/*     */ 
/*     */ 
/*     */       
/*     */       protected final K get(int location) {
/* 467 */         return (K)Reference2DoubleArrayMap.this.key[location];
/*     */       }
/*     */ 
/*     */       
/*     */       protected final KeySetSpliterator makeForSplit(int pos, int maxPos) {
/* 472 */         return new KeySetSpliterator(pos, maxPos);
/*     */       }
/*     */ 
/*     */ 
/*     */ 
/*     */       
/*     */       public void forEachRemaining(Consumer<? super K> action) {
/* 479 */         int max = Reference2DoubleArrayMap.this.size;
/* 480 */         while (this.pos < max) {
/* 481 */           action.accept((K)Reference2DoubleArrayMap.this.key[this.pos++]);
/*     */         }
/*     */       }
/*     */     }
/*     */ 
/*     */     
/*     */     public ObjectSpliterator<K> spliterator() {
/* 488 */       return new KeySetSpliterator(0, Reference2DoubleArrayMap.this.size);
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public void forEach(Consumer<? super K> action) {
/* 495 */       for (int i = 0, max = Reference2DoubleArrayMap.this.size; i < max; i++) {
/* 496 */         action.accept((K)Reference2DoubleArrayMap.this.key[i]);
/*     */       }
/*     */     }
/*     */ 
/*     */     
/*     */     public int size() {
/* 502 */       return Reference2DoubleArrayMap.this.size;
/*     */     }
/*     */ 
/*     */     
/*     */     public void clear() {
/* 507 */       Reference2DoubleArrayMap.this.clear();
/*     */     } }
/*     */ 
/*     */ 
/*     */   
/*     */   public ReferenceSet<K> keySet() {
/* 513 */     if (this.keys == null) this.keys = new KeySet(); 
/* 514 */     return this.keys;
/*     */   }
/*     */   
/*     */   private final class ValuesCollection extends AbstractDoubleCollection { private ValuesCollection() {}
/*     */     
/*     */     public boolean contains(double v) {
/* 520 */       return Reference2DoubleArrayMap.this.containsValue(v);
/*     */     }
/*     */ 
/*     */     
/*     */     public DoubleIterator iterator() {
/* 525 */       return new DoubleIterator() {
/* 526 */           int pos = 0;
/*     */ 
/*     */           
/*     */           public boolean hasNext() {
/* 530 */             return (this.pos < Reference2DoubleArrayMap.this.size);
/*     */           }
/*     */ 
/*     */ 
/*     */           
/*     */           public double nextDouble() {
/* 536 */             if (!hasNext()) throw new NoSuchElementException(); 
/* 537 */             return Reference2DoubleArrayMap.this.value[this.pos++];
/*     */           }
/*     */ 
/*     */           
/*     */           public void remove() {
/* 542 */             if (this.pos == 0) throw new IllegalStateException(); 
/* 543 */             int tail = Reference2DoubleArrayMap.this.size - this.pos;
/* 544 */             System.arraycopy(Reference2DoubleArrayMap.this.key, this.pos, Reference2DoubleArrayMap.this.key, this.pos - 1, tail);
/* 545 */             System.arraycopy(Reference2DoubleArrayMap.this.value, this.pos, Reference2DoubleArrayMap.this.value, this.pos - 1, tail);
/* 546 */             Reference2DoubleArrayMap.this.size--;
/* 547 */             this.pos--;
/* 548 */             Reference2DoubleArrayMap.this.key[Reference2DoubleArrayMap.this.size] = null;
/*     */           }
/*     */ 
/*     */ 
/*     */ 
/*     */           
/*     */           public void forEachRemaining(DoubleConsumer action) {
/* 555 */             int max = Reference2DoubleArrayMap.this.size;
/* 556 */             while (this.pos < max)
/* 557 */               action.accept(Reference2DoubleArrayMap.this.value[this.pos++]); 
/*     */           }
/*     */         };
/*     */     }
/*     */     
/*     */     final class ValuesSpliterator
/*     */       extends DoubleSpliterators.EarlyBindingSizeIndexBasedSpliterator
/*     */       implements DoubleSpliterator {
/*     */       ValuesSpliterator(int pos, int maxPos) {
/* 566 */         super(pos, maxPos);
/*     */       }
/*     */ 
/*     */       
/*     */       public int characteristics() {
/* 571 */         return 16720;
/*     */       }
/*     */ 
/*     */ 
/*     */       
/*     */       protected final double get(int location) {
/* 577 */         return Reference2DoubleArrayMap.this.value[location];
/*     */       }
/*     */ 
/*     */       
/*     */       protected final ValuesSpliterator makeForSplit(int pos, int maxPos) {
/* 582 */         return new ValuesSpliterator(pos, maxPos);
/*     */       }
/*     */ 
/*     */ 
/*     */ 
/*     */       
/*     */       public void forEachRemaining(DoubleConsumer action) {
/* 589 */         int max = Reference2DoubleArrayMap.this.size;
/* 590 */         while (this.pos < max) {
/* 591 */           action.accept(Reference2DoubleArrayMap.this.value[this.pos++]);
/*     */         }
/*     */       }
/*     */     }
/*     */ 
/*     */     
/*     */     public DoubleSpliterator spliterator() {
/* 598 */       return new ValuesSpliterator(0, Reference2DoubleArrayMap.this.size);
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public void forEach(DoubleConsumer action) {
/* 605 */       for (int i = 0, max = Reference2DoubleArrayMap.this.size; i < max; i++) {
/* 606 */         action.accept(Reference2DoubleArrayMap.this.value[i]);
/*     */       }
/*     */     }
/*     */ 
/*     */     
/*     */     public int size() {
/* 612 */       return Reference2DoubleArrayMap.this.size;
/*     */     }
/*     */ 
/*     */     
/*     */     public void clear() {
/* 617 */       Reference2DoubleArrayMap.this.clear();
/*     */     } }
/*     */ 
/*     */ 
/*     */   
/*     */   public DoubleCollection values() {
/* 623 */     if (this.values == null) this.values = (DoubleCollection)new ValuesCollection(); 
/* 624 */     return this.values;
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
/*     */   public Reference2DoubleArrayMap<K> clone() {
/*     */     Reference2DoubleArrayMap<K> c;
/*     */     try {
/* 641 */       c = (Reference2DoubleArrayMap<K>)super.clone();
/* 642 */     } catch (CloneNotSupportedException cantHappen) {
/* 643 */       throw new InternalError();
/*     */     } 
/* 645 */     c.key = (Object[])this.key.clone();
/* 646 */     c.value = (double[])this.value.clone();
/* 647 */     c.entries = null;
/* 648 */     c.keys = null;
/* 649 */     c.values = null;
/* 650 */     return c;
/*     */   }
/*     */   
/*     */   private void writeObject(ObjectOutputStream s) throws IOException {
/* 654 */     s.defaultWriteObject();
/* 655 */     for (int i = 0, max = this.size; i < max; i++) {
/* 656 */       s.writeObject(this.key[i]);
/* 657 */       s.writeDouble(this.value[i]);
/*     */     } 
/*     */   }
/*     */   
/*     */   private void readObject(ObjectInputStream s) throws IOException, ClassNotFoundException {
/* 662 */     s.defaultReadObject();
/* 663 */     this.key = new Object[this.size];
/* 664 */     this.value = new double[this.size];
/* 665 */     for (int i = 0; i < this.size; i++) {
/* 666 */       this.key[i] = s.readObject();
/* 667 */       this.value[i] = s.readDouble();
/*     */     } 
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\i\\unimi\dsi\fastutil\objects\Reference2DoubleArrayMap.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */