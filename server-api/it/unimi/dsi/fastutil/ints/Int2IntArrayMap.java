/*     */ package it.unimi.dsi.fastutil.ints;
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
/*     */ import java.util.function.IntConsumer;
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
/*     */ public class Int2IntArrayMap
/*     */   extends AbstractInt2IntMap
/*     */   implements Serializable, Cloneable
/*     */ {
/*     */   private static final long serialVersionUID = 1L;
/*     */   protected transient int[] key;
/*     */   protected transient int[] value;
/*     */   protected int size;
/*     */   protected transient Int2IntMap.FastEntrySet entries;
/*     */   protected transient IntSet keys;
/*     */   protected transient IntCollection values;
/*     */   
/*     */   public Int2IntArrayMap(int[] key, int[] value) {
/*  60 */     this.key = key;
/*  61 */     this.value = value;
/*  62 */     this.size = key.length;
/*  63 */     if (key.length != value.length) throw new IllegalArgumentException("Keys and values have different lengths (" + key.length + ", " + value.length + ")");
/*     */   
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public Int2IntArrayMap() {
/*  70 */     this.key = IntArrays.EMPTY_ARRAY;
/*  71 */     this.value = IntArrays.EMPTY_ARRAY;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Int2IntArrayMap(int capacity) {
/*  80 */     this.key = new int[capacity];
/*  81 */     this.value = new int[capacity];
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Int2IntArrayMap(Int2IntMap m) {
/*  90 */     this(m.size());
/*  91 */     int i = 0;
/*  92 */     for (ObjectIterator<Int2IntMap.Entry> objectIterator = m.int2IntEntrySet().iterator(); objectIterator.hasNext(); ) { Int2IntMap.Entry e = objectIterator.next();
/*  93 */       this.key[i] = e.getIntKey();
/*  94 */       this.value[i] = e.getIntValue();
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
/*     */   public Int2IntArrayMap(Map<? extends Integer, ? extends Integer> m) {
/* 106 */     this(m.size());
/* 107 */     int i = 0;
/* 108 */     for (Map.Entry<? extends Integer, ? extends Integer> e : m.entrySet()) {
/* 109 */       this.key[i] = ((Integer)e.getKey()).intValue();
/* 110 */       this.value[i] = ((Integer)e.getValue()).intValue();
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
/*     */   public Int2IntArrayMap(int[] key, int[] value, int size) {
/* 129 */     this.key = key;
/* 130 */     this.value = value;
/* 131 */     this.size = size;
/* 132 */     if (key.length != value.length) throw new IllegalArgumentException("Keys and values have different lengths (" + key.length + ", " + value.length + ")"); 
/* 133 */     if (size > key.length) throw new IllegalArgumentException("The provided size (" + size + ") is larger than or equal to the backing-arrays size (" + key.length + ")"); 
/*     */   }
/*     */   
/*     */   private final class EntrySet
/*     */     extends AbstractObjectSet<Int2IntMap.Entry> implements Int2IntMap.FastEntrySet {
/*     */     private EntrySet() {}
/*     */     
/*     */     public ObjectIterator<Int2IntMap.Entry> iterator() {
/* 141 */       return new ObjectIterator<Int2IntMap.Entry>() {
/* 142 */           int curr = -1; int next = 0;
/*     */ 
/*     */           
/*     */           public boolean hasNext() {
/* 146 */             return (this.next < Int2IntArrayMap.this.size);
/*     */           }
/*     */ 
/*     */ 
/*     */           
/*     */           public Int2IntMap.Entry next() {
/* 152 */             if (!hasNext()) throw new NoSuchElementException(); 
/* 153 */             return new AbstractInt2IntMap.BasicEntry(Int2IntArrayMap.this.key[this.curr = this.next], Int2IntArrayMap.this.value[this.next++]);
/*     */           }
/*     */ 
/*     */           
/*     */           public void remove() {
/* 158 */             if (this.curr == -1) throw new IllegalStateException(); 
/* 159 */             this.curr = -1;
/* 160 */             int tail = Int2IntArrayMap.this.size-- - this.next--;
/* 161 */             System.arraycopy(Int2IntArrayMap.this.key, this.next + 1, Int2IntArrayMap.this.key, this.next, tail);
/* 162 */             System.arraycopy(Int2IntArrayMap.this.value, this.next + 1, Int2IntArrayMap.this.value, this.next, tail);
/*     */           }
/*     */ 
/*     */ 
/*     */ 
/*     */           
/*     */           public void forEachRemaining(Consumer<? super Int2IntMap.Entry> action) {
/* 169 */             int max = Int2IntArrayMap.this.size;
/* 170 */             while (this.next < max) {
/* 171 */               action.accept(new AbstractInt2IntMap.BasicEntry(Int2IntArrayMap.this.key[this.curr = this.next], Int2IntArrayMap.this.value[this.next++]));
/*     */             }
/*     */           }
/*     */         };
/*     */     }
/*     */ 
/*     */     
/*     */     public ObjectIterator<Int2IntMap.Entry> fastIterator() {
/* 179 */       return new ObjectIterator<Int2IntMap.Entry>() {
/* 180 */           int next = 0; int curr = -1;
/* 181 */           final AbstractInt2IntMap.BasicEntry entry = new AbstractInt2IntMap.BasicEntry();
/*     */ 
/*     */           
/*     */           public boolean hasNext() {
/* 185 */             return (this.next < Int2IntArrayMap.this.size);
/*     */           }
/*     */ 
/*     */ 
/*     */           
/*     */           public Int2IntMap.Entry next() {
/* 191 */             if (!hasNext()) throw new NoSuchElementException(); 
/* 192 */             this.entry.key = Int2IntArrayMap.this.key[this.curr = this.next];
/* 193 */             this.entry.value = Int2IntArrayMap.this.value[this.next++];
/* 194 */             return this.entry;
/*     */           }
/*     */ 
/*     */           
/*     */           public void remove() {
/* 199 */             if (this.curr == -1) throw new IllegalStateException(); 
/* 200 */             this.curr = -1;
/* 201 */             int tail = Int2IntArrayMap.this.size-- - this.next--;
/* 202 */             System.arraycopy(Int2IntArrayMap.this.key, this.next + 1, Int2IntArrayMap.this.key, this.next, tail);
/* 203 */             System.arraycopy(Int2IntArrayMap.this.value, this.next + 1, Int2IntArrayMap.this.value, this.next, tail);
/*     */           }
/*     */ 
/*     */ 
/*     */ 
/*     */           
/*     */           public void forEachRemaining(Consumer<? super Int2IntMap.Entry> action) {
/* 210 */             int max = Int2IntArrayMap.this.size;
/* 211 */             while (this.next < max) {
/* 212 */               this.entry.key = Int2IntArrayMap.this.key[this.curr = this.next];
/* 213 */               this.entry.value = Int2IntArrayMap.this.value[this.next++];
/* 214 */               action.accept(this.entry);
/*     */             } 
/*     */           }
/*     */         };
/*     */     }
/*     */     
/*     */     final class EntrySetSpliterator
/*     */       extends ObjectSpliterators.EarlyBindingSizeIndexBasedSpliterator<Int2IntMap.Entry>
/*     */       implements ObjectSpliterator<Int2IntMap.Entry> {
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
/*     */       protected final Int2IntMap.Entry get(int location) {
/* 235 */         return new AbstractInt2IntMap.BasicEntry(Int2IntArrayMap.this.key[location], Int2IntArrayMap.this.value[location]);
/*     */       }
/*     */ 
/*     */       
/*     */       protected final EntrySetSpliterator makeForSplit(int pos, int maxPos) {
/* 240 */         return new EntrySetSpliterator(pos, maxPos);
/*     */       }
/*     */     }
/*     */ 
/*     */     
/*     */     public ObjectSpliterator<Int2IntMap.Entry> spliterator() {
/* 246 */       return new EntrySetSpliterator(0, Int2IntArrayMap.this.size);
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public void forEach(Consumer<? super Int2IntMap.Entry> action) {
/* 254 */       for (int i = 0, max = Int2IntArrayMap.this.size; i < max; i++) {
/* 255 */         action.accept(new AbstractInt2IntMap.BasicEntry(Int2IntArrayMap.this.key[i], Int2IntArrayMap.this.value[i]));
/*     */       }
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public void fastForEach(Consumer<? super Int2IntMap.Entry> action) {
/* 263 */       AbstractInt2IntMap.BasicEntry entry = new AbstractInt2IntMap.BasicEntry();
/*     */       
/* 265 */       for (int i = 0, max = Int2IntArrayMap.this.size; i < max; i++) {
/* 266 */         entry.key = Int2IntArrayMap.this.key[i];
/* 267 */         entry.value = Int2IntArrayMap.this.value[i];
/* 268 */         action.accept(entry);
/*     */       } 
/*     */     }
/*     */ 
/*     */     
/*     */     public int size() {
/* 274 */       return Int2IntArrayMap.this.size;
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     public boolean contains(Object o) {
/* 280 */       if (!(o instanceof Map.Entry)) return false; 
/* 281 */       Map.Entry<?, ?> e = (Map.Entry<?, ?>)o;
/* 282 */       if (e.getKey() == null || !(e.getKey() instanceof Integer)) return false; 
/* 283 */       if (e.getValue() == null || !(e.getValue() instanceof Integer)) return false; 
/* 284 */       int k = ((Integer)e.getKey()).intValue();
/* 285 */       return (Int2IntArrayMap.this.containsKey(k) && Int2IntArrayMap.this.get(k) == ((Integer)e.getValue()).intValue());
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     public boolean remove(Object o) {
/* 291 */       if (!(o instanceof Map.Entry)) return false; 
/* 292 */       Map.Entry<?, ?> e = (Map.Entry<?, ?>)o;
/* 293 */       if (e.getKey() == null || !(e.getKey() instanceof Integer)) return false; 
/* 294 */       if (e.getValue() == null || !(e.getValue() instanceof Integer)) return false; 
/* 295 */       int k = ((Integer)e.getKey()).intValue();
/* 296 */       int v = ((Integer)e.getValue()).intValue();
/* 297 */       int oldPos = Int2IntArrayMap.this.findKey(k);
/* 298 */       if (oldPos == -1 || v != Int2IntArrayMap.this.value[oldPos]) return false; 
/* 299 */       int tail = Int2IntArrayMap.this.size - oldPos - 1;
/* 300 */       System.arraycopy(Int2IntArrayMap.this.key, oldPos + 1, Int2IntArrayMap.this.key, oldPos, tail);
/* 301 */       System.arraycopy(Int2IntArrayMap.this.value, oldPos + 1, Int2IntArrayMap.this.value, oldPos, tail);
/* 302 */       Int2IntArrayMap.this.size--;
/* 303 */       return true;
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   public Int2IntMap.FastEntrySet int2IntEntrySet() {
/* 309 */     if (this.entries == null) this.entries = new EntrySet(); 
/* 310 */     return this.entries;
/*     */   }
/*     */   
/*     */   private int findKey(int k) {
/* 314 */     int[] key = this.key;
/* 315 */     for (int i = this.size; i-- != 0;) { if (key[i] == k) return i;  }
/* 316 */      return -1;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public int get(int k) {
/* 322 */     int[] key = this.key;
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
/*     */   public boolean containsKey(int k) {
/* 339 */     return (findKey(k) != -1);
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean containsValue(int v) {
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
/*     */   public int put(int k, int v) {
/* 356 */     int oldKey = findKey(k);
/* 357 */     if (oldKey != -1) {
/* 358 */       int oldValue = this.value[oldKey];
/* 359 */       this.value[oldKey] = v;
/* 360 */       return oldValue;
/*     */     } 
/* 362 */     if (this.size == this.key.length) {
/* 363 */       int[] newKey = new int[(this.size == 0) ? 2 : (this.size * 2)];
/* 364 */       int[] newValue = new int[(this.size == 0) ? 2 : (this.size * 2)];
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
/*     */   public int remove(int k) {
/* 381 */     int oldPos = findKey(k);
/* 382 */     if (oldPos == -1) return this.defRetValue; 
/* 383 */     int oldValue = this.value[oldPos];
/* 384 */     int tail = this.size - oldPos - 1;
/* 385 */     System.arraycopy(this.key, oldPos + 1, this.key, oldPos, tail);
/* 386 */     System.arraycopy(this.value, oldPos + 1, this.value, oldPos, tail);
/* 387 */     this.size--;
/* 388 */     return oldValue;
/*     */   }
/*     */   
/*     */   private final class KeySet extends AbstractIntSet { private KeySet() {}
/*     */     
/*     */     public boolean contains(int k) {
/* 394 */       return (Int2IntArrayMap.this.findKey(k) != -1);
/*     */     }
/*     */ 
/*     */     
/*     */     public boolean remove(int k) {
/* 399 */       int oldPos = Int2IntArrayMap.this.findKey(k);
/* 400 */       if (oldPos == -1) return false; 
/* 401 */       int tail = Int2IntArrayMap.this.size - oldPos - 1;
/* 402 */       System.arraycopy(Int2IntArrayMap.this.key, oldPos + 1, Int2IntArrayMap.this.key, oldPos, tail);
/* 403 */       System.arraycopy(Int2IntArrayMap.this.value, oldPos + 1, Int2IntArrayMap.this.value, oldPos, tail);
/* 404 */       Int2IntArrayMap.this.size--;
/* 405 */       return true;
/*     */     }
/*     */ 
/*     */     
/*     */     public IntIterator iterator() {
/* 410 */       return new IntIterator() {
/* 411 */           int pos = 0;
/*     */ 
/*     */           
/*     */           public boolean hasNext() {
/* 415 */             return (this.pos < Int2IntArrayMap.this.size);
/*     */           }
/*     */ 
/*     */ 
/*     */           
/*     */           public int nextInt() {
/* 421 */             if (!hasNext()) throw new NoSuchElementException(); 
/* 422 */             return Int2IntArrayMap.this.key[this.pos++];
/*     */           }
/*     */ 
/*     */           
/*     */           public void remove() {
/* 427 */             if (this.pos == 0) throw new IllegalStateException(); 
/* 428 */             int tail = Int2IntArrayMap.this.size - this.pos;
/* 429 */             System.arraycopy(Int2IntArrayMap.this.key, this.pos, Int2IntArrayMap.this.key, this.pos - 1, tail);
/* 430 */             System.arraycopy(Int2IntArrayMap.this.value, this.pos, Int2IntArrayMap.this.value, this.pos - 1, tail);
/* 431 */             Int2IntArrayMap.this.size--;
/* 432 */             this.pos--;
/*     */           }
/*     */ 
/*     */ 
/*     */ 
/*     */           
/*     */           public void forEachRemaining(IntConsumer action) {
/* 439 */             int max = Int2IntArrayMap.this.size;
/* 440 */             while (this.pos < max)
/* 441 */               action.accept(Int2IntArrayMap.this.key[this.pos++]); 
/*     */           }
/*     */         };
/*     */     }
/*     */     
/*     */     final class KeySetSpliterator
/*     */       extends IntSpliterators.EarlyBindingSizeIndexBasedSpliterator
/*     */       implements IntSpliterator {
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
/*     */       protected final int get(int location) {
/* 461 */         return Int2IntArrayMap.this.key[location];
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
/*     */       public void forEachRemaining(IntConsumer action) {
/* 473 */         int max = Int2IntArrayMap.this.size;
/* 474 */         while (this.pos < max) {
/* 475 */           action.accept(Int2IntArrayMap.this.key[this.pos++]);
/*     */         }
/*     */       }
/*     */     }
/*     */ 
/*     */     
/*     */     public IntSpliterator spliterator() {
/* 482 */       return new KeySetSpliterator(0, Int2IntArrayMap.this.size);
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public void forEach(IntConsumer action) {
/* 489 */       for (int i = 0, max = Int2IntArrayMap.this.size; i < max; i++) {
/* 490 */         action.accept(Int2IntArrayMap.this.key[i]);
/*     */       }
/*     */     }
/*     */ 
/*     */     
/*     */     public int size() {
/* 496 */       return Int2IntArrayMap.this.size;
/*     */     }
/*     */ 
/*     */     
/*     */     public void clear() {
/* 501 */       Int2IntArrayMap.this.clear();
/*     */     } }
/*     */ 
/*     */ 
/*     */   
/*     */   public IntSet keySet() {
/* 507 */     if (this.keys == null) this.keys = new KeySet(); 
/* 508 */     return this.keys;
/*     */   }
/*     */   
/*     */   private final class ValuesCollection extends AbstractIntCollection { private ValuesCollection() {}
/*     */     
/*     */     public boolean contains(int v) {
/* 514 */       return Int2IntArrayMap.this.containsValue(v);
/*     */     }
/*     */ 
/*     */     
/*     */     public IntIterator iterator() {
/* 519 */       return new IntIterator() {
/* 520 */           int pos = 0;
/*     */ 
/*     */           
/*     */           public boolean hasNext() {
/* 524 */             return (this.pos < Int2IntArrayMap.this.size);
/*     */           }
/*     */ 
/*     */ 
/*     */           
/*     */           public int nextInt() {
/* 530 */             if (!hasNext()) throw new NoSuchElementException(); 
/* 531 */             return Int2IntArrayMap.this.value[this.pos++];
/*     */           }
/*     */ 
/*     */           
/*     */           public void remove() {
/* 536 */             if (this.pos == 0) throw new IllegalStateException(); 
/* 537 */             int tail = Int2IntArrayMap.this.size - this.pos;
/* 538 */             System.arraycopy(Int2IntArrayMap.this.key, this.pos, Int2IntArrayMap.this.key, this.pos - 1, tail);
/* 539 */             System.arraycopy(Int2IntArrayMap.this.value, this.pos, Int2IntArrayMap.this.value, this.pos - 1, tail);
/* 540 */             Int2IntArrayMap.this.size--;
/* 541 */             this.pos--;
/*     */           }
/*     */ 
/*     */ 
/*     */ 
/*     */           
/*     */           public void forEachRemaining(IntConsumer action) {
/* 548 */             int max = Int2IntArrayMap.this.size;
/* 549 */             while (this.pos < max)
/* 550 */               action.accept(Int2IntArrayMap.this.value[this.pos++]); 
/*     */           }
/*     */         };
/*     */     }
/*     */     
/*     */     final class ValuesSpliterator
/*     */       extends IntSpliterators.EarlyBindingSizeIndexBasedSpliterator
/*     */       implements IntSpliterator {
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
/*     */       protected final int get(int location) {
/* 570 */         return Int2IntArrayMap.this.value[location];
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
/*     */       public void forEachRemaining(IntConsumer action) {
/* 582 */         int max = Int2IntArrayMap.this.size;
/* 583 */         while (this.pos < max) {
/* 584 */           action.accept(Int2IntArrayMap.this.value[this.pos++]);
/*     */         }
/*     */       }
/*     */     }
/*     */ 
/*     */     
/*     */     public IntSpliterator spliterator() {
/* 591 */       return new ValuesSpliterator(0, Int2IntArrayMap.this.size);
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public void forEach(IntConsumer action) {
/* 598 */       for (int i = 0, max = Int2IntArrayMap.this.size; i < max; i++) {
/* 599 */         action.accept(Int2IntArrayMap.this.value[i]);
/*     */       }
/*     */     }
/*     */ 
/*     */     
/*     */     public int size() {
/* 605 */       return Int2IntArrayMap.this.size;
/*     */     }
/*     */ 
/*     */     
/*     */     public void clear() {
/* 610 */       Int2IntArrayMap.this.clear();
/*     */     } }
/*     */ 
/*     */ 
/*     */   
/*     */   public IntCollection values() {
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
/*     */   public Int2IntArrayMap clone() {
/*     */     Int2IntArrayMap c;
/*     */     try {
/* 634 */       c = (Int2IntArrayMap)super.clone();
/* 635 */     } catch (CloneNotSupportedException cantHappen) {
/* 636 */       throw new InternalError();
/*     */     } 
/* 638 */     c.key = (int[])this.key.clone();
/* 639 */     c.value = (int[])this.value.clone();
/* 640 */     c.entries = null;
/* 641 */     c.keys = null;
/* 642 */     c.values = null;
/* 643 */     return c;
/*     */   }
/*     */   
/*     */   private void writeObject(ObjectOutputStream s) throws IOException {
/* 647 */     s.defaultWriteObject();
/* 648 */     for (int i = 0, max = this.size; i < max; i++) {
/* 649 */       s.writeInt(this.key[i]);
/* 650 */       s.writeInt(this.value[i]);
/*     */     } 
/*     */   }
/*     */   
/*     */   private void readObject(ObjectInputStream s) throws IOException, ClassNotFoundException {
/* 655 */     s.defaultReadObject();
/* 656 */     this.key = new int[this.size];
/* 657 */     this.value = new int[this.size];
/* 658 */     for (int i = 0; i < this.size; i++) {
/* 659 */       this.key[i] = s.readInt();
/* 660 */       this.value[i] = s.readInt();
/*     */     } 
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\i\\unimi\dsi\fastutil\ints\Int2IntArrayMap.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */