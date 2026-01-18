/*     */ package it.unimi.dsi.fastutil.longs;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ public class Long2LongArrayMap
/*     */   extends AbstractLong2LongMap
/*     */   implements Serializable, Cloneable
/*     */ {
/*     */   private static final long serialVersionUID = 1L;
/*     */   protected transient long[] key;
/*     */   protected transient long[] value;
/*     */   protected int size;
/*     */   protected transient Long2LongMap.FastEntrySet entries;
/*     */   protected transient LongSet keys;
/*     */   protected transient LongCollection values;
/*     */   
/*     */   public Long2LongArrayMap(long[] key, long[] value) {
/*  60 */     this.key = key;
/*  61 */     this.value = value;
/*  62 */     this.size = key.length;
/*  63 */     if (key.length != value.length) throw new IllegalArgumentException("Keys and values have different lengths (" + key.length + ", " + value.length + ")");
/*     */   
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public Long2LongArrayMap() {
/*  70 */     this.key = LongArrays.EMPTY_ARRAY;
/*  71 */     this.value = LongArrays.EMPTY_ARRAY;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Long2LongArrayMap(int capacity) {
/*  80 */     this.key = new long[capacity];
/*  81 */     this.value = new long[capacity];
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Long2LongArrayMap(Long2LongMap m) {
/*  90 */     this(m.size());
/*  91 */     int i = 0;
/*  92 */     for (ObjectIterator<Long2LongMap.Entry> objectIterator = m.long2LongEntrySet().iterator(); objectIterator.hasNext(); ) { Long2LongMap.Entry e = objectIterator.next();
/*  93 */       this.key[i] = e.getLongKey();
/*  94 */       this.value[i] = e.getLongValue();
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
/*     */   public Long2LongArrayMap(Map<? extends Long, ? extends Long> m) {
/* 106 */     this(m.size());
/* 107 */     int i = 0;
/* 108 */     for (Map.Entry<? extends Long, ? extends Long> e : m.entrySet()) {
/* 109 */       this.key[i] = ((Long)e.getKey()).longValue();
/* 110 */       this.value[i] = ((Long)e.getValue()).longValue();
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
/*     */   public Long2LongArrayMap(long[] key, long[] value, int size) {
/* 129 */     this.key = key;
/* 130 */     this.value = value;
/* 131 */     this.size = size;
/* 132 */     if (key.length != value.length) throw new IllegalArgumentException("Keys and values have different lengths (" + key.length + ", " + value.length + ")"); 
/* 133 */     if (size > key.length) throw new IllegalArgumentException("The provided size (" + size + ") is larger than or equal to the backing-arrays size (" + key.length + ")"); 
/*     */   }
/*     */   
/*     */   private final class EntrySet
/*     */     extends AbstractObjectSet<Long2LongMap.Entry> implements Long2LongMap.FastEntrySet {
/*     */     private EntrySet() {}
/*     */     
/*     */     public ObjectIterator<Long2LongMap.Entry> iterator() {
/* 141 */       return new ObjectIterator<Long2LongMap.Entry>() {
/* 142 */           int curr = -1; int next = 0;
/*     */ 
/*     */           
/*     */           public boolean hasNext() {
/* 146 */             return (this.next < Long2LongArrayMap.this.size);
/*     */           }
/*     */ 
/*     */ 
/*     */           
/*     */           public Long2LongMap.Entry next() {
/* 152 */             if (!hasNext()) throw new NoSuchElementException(); 
/* 153 */             return new AbstractLong2LongMap.BasicEntry(Long2LongArrayMap.this.key[this.curr = this.next], Long2LongArrayMap.this.value[this.next++]);
/*     */           }
/*     */ 
/*     */           
/*     */           public void remove() {
/* 158 */             if (this.curr == -1) throw new IllegalStateException(); 
/* 159 */             this.curr = -1;
/* 160 */             int tail = Long2LongArrayMap.this.size-- - this.next--;
/* 161 */             System.arraycopy(Long2LongArrayMap.this.key, this.next + 1, Long2LongArrayMap.this.key, this.next, tail);
/* 162 */             System.arraycopy(Long2LongArrayMap.this.value, this.next + 1, Long2LongArrayMap.this.value, this.next, tail);
/*     */           }
/*     */ 
/*     */ 
/*     */ 
/*     */           
/*     */           public void forEachRemaining(Consumer<? super Long2LongMap.Entry> action) {
/* 169 */             int max = Long2LongArrayMap.this.size;
/* 170 */             while (this.next < max) {
/* 171 */               action.accept(new AbstractLong2LongMap.BasicEntry(Long2LongArrayMap.this.key[this.curr = this.next], Long2LongArrayMap.this.value[this.next++]));
/*     */             }
/*     */           }
/*     */         };
/*     */     }
/*     */ 
/*     */     
/*     */     public ObjectIterator<Long2LongMap.Entry> fastIterator() {
/* 179 */       return new ObjectIterator<Long2LongMap.Entry>() {
/* 180 */           int next = 0; int curr = -1;
/* 181 */           final AbstractLong2LongMap.BasicEntry entry = new AbstractLong2LongMap.BasicEntry();
/*     */ 
/*     */           
/*     */           public boolean hasNext() {
/* 185 */             return (this.next < Long2LongArrayMap.this.size);
/*     */           }
/*     */ 
/*     */ 
/*     */           
/*     */           public Long2LongMap.Entry next() {
/* 191 */             if (!hasNext()) throw new NoSuchElementException(); 
/* 192 */             this.entry.key = Long2LongArrayMap.this.key[this.curr = this.next];
/* 193 */             this.entry.value = Long2LongArrayMap.this.value[this.next++];
/* 194 */             return this.entry;
/*     */           }
/*     */ 
/*     */           
/*     */           public void remove() {
/* 199 */             if (this.curr == -1) throw new IllegalStateException(); 
/* 200 */             this.curr = -1;
/* 201 */             int tail = Long2LongArrayMap.this.size-- - this.next--;
/* 202 */             System.arraycopy(Long2LongArrayMap.this.key, this.next + 1, Long2LongArrayMap.this.key, this.next, tail);
/* 203 */             System.arraycopy(Long2LongArrayMap.this.value, this.next + 1, Long2LongArrayMap.this.value, this.next, tail);
/*     */           }
/*     */ 
/*     */ 
/*     */ 
/*     */           
/*     */           public void forEachRemaining(Consumer<? super Long2LongMap.Entry> action) {
/* 210 */             int max = Long2LongArrayMap.this.size;
/* 211 */             while (this.next < max) {
/* 212 */               this.entry.key = Long2LongArrayMap.this.key[this.curr = this.next];
/* 213 */               this.entry.value = Long2LongArrayMap.this.value[this.next++];
/* 214 */               action.accept(this.entry);
/*     */             } 
/*     */           }
/*     */         };
/*     */     }
/*     */     
/*     */     final class EntrySetSpliterator
/*     */       extends ObjectSpliterators.EarlyBindingSizeIndexBasedSpliterator<Long2LongMap.Entry>
/*     */       implements ObjectSpliterator<Long2LongMap.Entry> {
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
/*     */       protected final Long2LongMap.Entry get(int location) {
/* 235 */         return new AbstractLong2LongMap.BasicEntry(Long2LongArrayMap.this.key[location], Long2LongArrayMap.this.value[location]);
/*     */       }
/*     */ 
/*     */       
/*     */       protected final EntrySetSpliterator makeForSplit(int pos, int maxPos) {
/* 240 */         return new EntrySetSpliterator(pos, maxPos);
/*     */       }
/*     */     }
/*     */ 
/*     */     
/*     */     public ObjectSpliterator<Long2LongMap.Entry> spliterator() {
/* 246 */       return new EntrySetSpliterator(0, Long2LongArrayMap.this.size);
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public void forEach(Consumer<? super Long2LongMap.Entry> action) {
/* 254 */       for (int i = 0, max = Long2LongArrayMap.this.size; i < max; i++) {
/* 255 */         action.accept(new AbstractLong2LongMap.BasicEntry(Long2LongArrayMap.this.key[i], Long2LongArrayMap.this.value[i]));
/*     */       }
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public void fastForEach(Consumer<? super Long2LongMap.Entry> action) {
/* 263 */       AbstractLong2LongMap.BasicEntry entry = new AbstractLong2LongMap.BasicEntry();
/*     */       
/* 265 */       for (int i = 0, max = Long2LongArrayMap.this.size; i < max; i++) {
/* 266 */         entry.key = Long2LongArrayMap.this.key[i];
/* 267 */         entry.value = Long2LongArrayMap.this.value[i];
/* 268 */         action.accept(entry);
/*     */       } 
/*     */     }
/*     */ 
/*     */     
/*     */     public int size() {
/* 274 */       return Long2LongArrayMap.this.size;
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     public boolean contains(Object o) {
/* 280 */       if (!(o instanceof Map.Entry)) return false; 
/* 281 */       Map.Entry<?, ?> e = (Map.Entry<?, ?>)o;
/* 282 */       if (e.getKey() == null || !(e.getKey() instanceof Long)) return false; 
/* 283 */       if (e.getValue() == null || !(e.getValue() instanceof Long)) return false; 
/* 284 */       long k = ((Long)e.getKey()).longValue();
/* 285 */       return (Long2LongArrayMap.this.containsKey(k) && Long2LongArrayMap.this.get(k) == ((Long)e.getValue()).longValue());
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     public boolean remove(Object o) {
/* 291 */       if (!(o instanceof Map.Entry)) return false; 
/* 292 */       Map.Entry<?, ?> e = (Map.Entry<?, ?>)o;
/* 293 */       if (e.getKey() == null || !(e.getKey() instanceof Long)) return false; 
/* 294 */       if (e.getValue() == null || !(e.getValue() instanceof Long)) return false; 
/* 295 */       long k = ((Long)e.getKey()).longValue();
/* 296 */       long v = ((Long)e.getValue()).longValue();
/* 297 */       int oldPos = Long2LongArrayMap.this.findKey(k);
/* 298 */       if (oldPos == -1 || v != Long2LongArrayMap.this.value[oldPos]) return false; 
/* 299 */       int tail = Long2LongArrayMap.this.size - oldPos - 1;
/* 300 */       System.arraycopy(Long2LongArrayMap.this.key, oldPos + 1, Long2LongArrayMap.this.key, oldPos, tail);
/* 301 */       System.arraycopy(Long2LongArrayMap.this.value, oldPos + 1, Long2LongArrayMap.this.value, oldPos, tail);
/* 302 */       Long2LongArrayMap.this.size--;
/* 303 */       return true;
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   public Long2LongMap.FastEntrySet long2LongEntrySet() {
/* 309 */     if (this.entries == null) this.entries = new EntrySet(); 
/* 310 */     return this.entries;
/*     */   }
/*     */   
/*     */   private int findKey(long k) {
/* 314 */     long[] key = this.key;
/* 315 */     for (int i = this.size; i-- != 0;) { if (key[i] == k) return i;  }
/* 316 */      return -1;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public long get(long k) {
/* 322 */     long[] key = this.key;
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
/*     */   public boolean containsKey(long k) {
/* 339 */     return (findKey(k) != -1);
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean containsValue(long v) {
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
/*     */   public long put(long k, long v) {
/* 356 */     int oldKey = findKey(k);
/* 357 */     if (oldKey != -1) {
/* 358 */       long oldValue = this.value[oldKey];
/* 359 */       this.value[oldKey] = v;
/* 360 */       return oldValue;
/*     */     } 
/* 362 */     if (this.size == this.key.length) {
/* 363 */       long[] newKey = new long[(this.size == 0) ? 2 : (this.size * 2)];
/* 364 */       long[] newValue = new long[(this.size == 0) ? 2 : (this.size * 2)];
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
/*     */   public long remove(long k) {
/* 381 */     int oldPos = findKey(k);
/* 382 */     if (oldPos == -1) return this.defRetValue; 
/* 383 */     long oldValue = this.value[oldPos];
/* 384 */     int tail = this.size - oldPos - 1;
/* 385 */     System.arraycopy(this.key, oldPos + 1, this.key, oldPos, tail);
/* 386 */     System.arraycopy(this.value, oldPos + 1, this.value, oldPos, tail);
/* 387 */     this.size--;
/* 388 */     return oldValue;
/*     */   }
/*     */   
/*     */   private final class KeySet extends AbstractLongSet { private KeySet() {}
/*     */     
/*     */     public boolean contains(long k) {
/* 394 */       return (Long2LongArrayMap.this.findKey(k) != -1);
/*     */     }
/*     */ 
/*     */     
/*     */     public boolean remove(long k) {
/* 399 */       int oldPos = Long2LongArrayMap.this.findKey(k);
/* 400 */       if (oldPos == -1) return false; 
/* 401 */       int tail = Long2LongArrayMap.this.size - oldPos - 1;
/* 402 */       System.arraycopy(Long2LongArrayMap.this.key, oldPos + 1, Long2LongArrayMap.this.key, oldPos, tail);
/* 403 */       System.arraycopy(Long2LongArrayMap.this.value, oldPos + 1, Long2LongArrayMap.this.value, oldPos, tail);
/* 404 */       Long2LongArrayMap.this.size--;
/* 405 */       return true;
/*     */     }
/*     */ 
/*     */     
/*     */     public LongIterator iterator() {
/* 410 */       return new LongIterator() {
/* 411 */           int pos = 0;
/*     */ 
/*     */           
/*     */           public boolean hasNext() {
/* 415 */             return (this.pos < Long2LongArrayMap.this.size);
/*     */           }
/*     */ 
/*     */ 
/*     */           
/*     */           public long nextLong() {
/* 421 */             if (!hasNext()) throw new NoSuchElementException(); 
/* 422 */             return Long2LongArrayMap.this.key[this.pos++];
/*     */           }
/*     */ 
/*     */           
/*     */           public void remove() {
/* 427 */             if (this.pos == 0) throw new IllegalStateException(); 
/* 428 */             int tail = Long2LongArrayMap.this.size - this.pos;
/* 429 */             System.arraycopy(Long2LongArrayMap.this.key, this.pos, Long2LongArrayMap.this.key, this.pos - 1, tail);
/* 430 */             System.arraycopy(Long2LongArrayMap.this.value, this.pos, Long2LongArrayMap.this.value, this.pos - 1, tail);
/* 431 */             Long2LongArrayMap.this.size--;
/* 432 */             this.pos--;
/*     */           }
/*     */ 
/*     */ 
/*     */ 
/*     */           
/*     */           public void forEachRemaining(LongConsumer action) {
/* 439 */             int max = Long2LongArrayMap.this.size;
/* 440 */             while (this.pos < max)
/* 441 */               action.accept(Long2LongArrayMap.this.key[this.pos++]); 
/*     */           }
/*     */         };
/*     */     }
/*     */     
/*     */     final class KeySetSpliterator
/*     */       extends LongSpliterators.EarlyBindingSizeIndexBasedSpliterator
/*     */       implements LongSpliterator {
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
/*     */       protected final long get(int location) {
/* 461 */         return Long2LongArrayMap.this.key[location];
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
/*     */       public void forEachRemaining(LongConsumer action) {
/* 473 */         int max = Long2LongArrayMap.this.size;
/* 474 */         while (this.pos < max) {
/* 475 */           action.accept(Long2LongArrayMap.this.key[this.pos++]);
/*     */         }
/*     */       }
/*     */     }
/*     */ 
/*     */     
/*     */     public LongSpliterator spliterator() {
/* 482 */       return new KeySetSpliterator(0, Long2LongArrayMap.this.size);
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public void forEach(LongConsumer action) {
/* 489 */       for (int i = 0, max = Long2LongArrayMap.this.size; i < max; i++) {
/* 490 */         action.accept(Long2LongArrayMap.this.key[i]);
/*     */       }
/*     */     }
/*     */ 
/*     */     
/*     */     public int size() {
/* 496 */       return Long2LongArrayMap.this.size;
/*     */     }
/*     */ 
/*     */     
/*     */     public void clear() {
/* 501 */       Long2LongArrayMap.this.clear();
/*     */     } }
/*     */ 
/*     */ 
/*     */   
/*     */   public LongSet keySet() {
/* 507 */     if (this.keys == null) this.keys = new KeySet(); 
/* 508 */     return this.keys;
/*     */   }
/*     */   
/*     */   private final class ValuesCollection extends AbstractLongCollection { private ValuesCollection() {}
/*     */     
/*     */     public boolean contains(long v) {
/* 514 */       return Long2LongArrayMap.this.containsValue(v);
/*     */     }
/*     */ 
/*     */     
/*     */     public LongIterator iterator() {
/* 519 */       return new LongIterator() {
/* 520 */           int pos = 0;
/*     */ 
/*     */           
/*     */           public boolean hasNext() {
/* 524 */             return (this.pos < Long2LongArrayMap.this.size);
/*     */           }
/*     */ 
/*     */ 
/*     */           
/*     */           public long nextLong() {
/* 530 */             if (!hasNext()) throw new NoSuchElementException(); 
/* 531 */             return Long2LongArrayMap.this.value[this.pos++];
/*     */           }
/*     */ 
/*     */           
/*     */           public void remove() {
/* 536 */             if (this.pos == 0) throw new IllegalStateException(); 
/* 537 */             int tail = Long2LongArrayMap.this.size - this.pos;
/* 538 */             System.arraycopy(Long2LongArrayMap.this.key, this.pos, Long2LongArrayMap.this.key, this.pos - 1, tail);
/* 539 */             System.arraycopy(Long2LongArrayMap.this.value, this.pos, Long2LongArrayMap.this.value, this.pos - 1, tail);
/* 540 */             Long2LongArrayMap.this.size--;
/* 541 */             this.pos--;
/*     */           }
/*     */ 
/*     */ 
/*     */ 
/*     */           
/*     */           public void forEachRemaining(LongConsumer action) {
/* 548 */             int max = Long2LongArrayMap.this.size;
/* 549 */             while (this.pos < max)
/* 550 */               action.accept(Long2LongArrayMap.this.value[this.pos++]); 
/*     */           }
/*     */         };
/*     */     }
/*     */     
/*     */     final class ValuesSpliterator
/*     */       extends LongSpliterators.EarlyBindingSizeIndexBasedSpliterator
/*     */       implements LongSpliterator {
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
/*     */       protected final long get(int location) {
/* 570 */         return Long2LongArrayMap.this.value[location];
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
/*     */       public void forEachRemaining(LongConsumer action) {
/* 582 */         int max = Long2LongArrayMap.this.size;
/* 583 */         while (this.pos < max) {
/* 584 */           action.accept(Long2LongArrayMap.this.value[this.pos++]);
/*     */         }
/*     */       }
/*     */     }
/*     */ 
/*     */     
/*     */     public LongSpliterator spliterator() {
/* 591 */       return new ValuesSpliterator(0, Long2LongArrayMap.this.size);
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public void forEach(LongConsumer action) {
/* 598 */       for (int i = 0, max = Long2LongArrayMap.this.size; i < max; i++) {
/* 599 */         action.accept(Long2LongArrayMap.this.value[i]);
/*     */       }
/*     */     }
/*     */ 
/*     */     
/*     */     public int size() {
/* 605 */       return Long2LongArrayMap.this.size;
/*     */     }
/*     */ 
/*     */     
/*     */     public void clear() {
/* 610 */       Long2LongArrayMap.this.clear();
/*     */     } }
/*     */ 
/*     */ 
/*     */   
/*     */   public LongCollection values() {
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
/*     */   public Long2LongArrayMap clone() {
/*     */     Long2LongArrayMap c;
/*     */     try {
/* 634 */       c = (Long2LongArrayMap)super.clone();
/* 635 */     } catch (CloneNotSupportedException cantHappen) {
/* 636 */       throw new InternalError();
/*     */     } 
/* 638 */     c.key = (long[])this.key.clone();
/* 639 */     c.value = (long[])this.value.clone();
/* 640 */     c.entries = null;
/* 641 */     c.keys = null;
/* 642 */     c.values = null;
/* 643 */     return c;
/*     */   }
/*     */   
/*     */   private void writeObject(ObjectOutputStream s) throws IOException {
/* 647 */     s.defaultWriteObject();
/* 648 */     for (int i = 0, max = this.size; i < max; i++) {
/* 649 */       s.writeLong(this.key[i]);
/* 650 */       s.writeLong(this.value[i]);
/*     */     } 
/*     */   }
/*     */   
/*     */   private void readObject(ObjectInputStream s) throws IOException, ClassNotFoundException {
/* 655 */     s.defaultReadObject();
/* 656 */     this.key = new long[this.size];
/* 657 */     this.value = new long[this.size];
/* 658 */     for (int i = 0; i < this.size; i++) {
/* 659 */       this.key[i] = s.readLong();
/* 660 */       this.value[i] = s.readLong();
/*     */     } 
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\i\\unimi\dsi\fastutil\longs\Long2LongArrayMap.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */