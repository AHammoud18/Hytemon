/*     */ package it.unimi.dsi.fastutil.bytes;
/*     */ 
/*     */ import it.unimi.dsi.fastutil.ints.AbstractIntCollection;
/*     */ import it.unimi.dsi.fastutil.ints.IntArrays;
/*     */ import it.unimi.dsi.fastutil.ints.IntCollection;
/*     */ import it.unimi.dsi.fastutil.ints.IntIterator;
/*     */ import it.unimi.dsi.fastutil.ints.IntSpliterator;
/*     */ import it.unimi.dsi.fastutil.ints.IntSpliterators;
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
/*     */ public class Byte2IntArrayMap
/*     */   extends AbstractByte2IntMap
/*     */   implements Serializable, Cloneable
/*     */ {
/*     */   private static final long serialVersionUID = 1L;
/*     */   protected transient byte[] key;
/*     */   protected transient int[] value;
/*     */   protected int size;
/*     */   protected transient Byte2IntMap.FastEntrySet entries;
/*     */   protected transient ByteSet keys;
/*     */   protected transient IntCollection values;
/*     */   
/*     */   public Byte2IntArrayMap(byte[] key, int[] value) {
/*  63 */     this.key = key;
/*  64 */     this.value = value;
/*  65 */     this.size = key.length;
/*  66 */     if (key.length != value.length) throw new IllegalArgumentException("Keys and values have different lengths (" + key.length + ", " + value.length + ")");
/*     */   
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public Byte2IntArrayMap() {
/*  73 */     this.key = ByteArrays.EMPTY_ARRAY;
/*  74 */     this.value = IntArrays.EMPTY_ARRAY;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Byte2IntArrayMap(int capacity) {
/*  83 */     this.key = new byte[capacity];
/*  84 */     this.value = new int[capacity];
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Byte2IntArrayMap(Byte2IntMap m) {
/*  93 */     this(m.size());
/*  94 */     int i = 0;
/*  95 */     for (ObjectIterator<Byte2IntMap.Entry> objectIterator = m.byte2IntEntrySet().iterator(); objectIterator.hasNext(); ) { Byte2IntMap.Entry e = objectIterator.next();
/*  96 */       this.key[i] = e.getByteKey();
/*  97 */       this.value[i] = e.getIntValue();
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
/*     */   public Byte2IntArrayMap(Map<? extends Byte, ? extends Integer> m) {
/* 109 */     this(m.size());
/* 110 */     int i = 0;
/* 111 */     for (Map.Entry<? extends Byte, ? extends Integer> e : m.entrySet()) {
/* 112 */       this.key[i] = ((Byte)e.getKey()).byteValue();
/* 113 */       this.value[i] = ((Integer)e.getValue()).intValue();
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
/*     */   public Byte2IntArrayMap(byte[] key, int[] value, int size) {
/* 132 */     this.key = key;
/* 133 */     this.value = value;
/* 134 */     this.size = size;
/* 135 */     if (key.length != value.length) throw new IllegalArgumentException("Keys and values have different lengths (" + key.length + ", " + value.length + ")"); 
/* 136 */     if (size > key.length) throw new IllegalArgumentException("The provided size (" + size + ") is larger than or equal to the backing-arrays size (" + key.length + ")"); 
/*     */   }
/*     */   
/*     */   private final class EntrySet
/*     */     extends AbstractObjectSet<Byte2IntMap.Entry> implements Byte2IntMap.FastEntrySet {
/*     */     private EntrySet() {}
/*     */     
/*     */     public ObjectIterator<Byte2IntMap.Entry> iterator() {
/* 144 */       return new ObjectIterator<Byte2IntMap.Entry>() {
/* 145 */           int curr = -1; int next = 0;
/*     */ 
/*     */           
/*     */           public boolean hasNext() {
/* 149 */             return (this.next < Byte2IntArrayMap.this.size);
/*     */           }
/*     */ 
/*     */ 
/*     */           
/*     */           public Byte2IntMap.Entry next() {
/* 155 */             if (!hasNext()) throw new NoSuchElementException(); 
/* 156 */             return new AbstractByte2IntMap.BasicEntry(Byte2IntArrayMap.this.key[this.curr = this.next], Byte2IntArrayMap.this.value[this.next++]);
/*     */           }
/*     */ 
/*     */           
/*     */           public void remove() {
/* 161 */             if (this.curr == -1) throw new IllegalStateException(); 
/* 162 */             this.curr = -1;
/* 163 */             int tail = Byte2IntArrayMap.this.size-- - this.next--;
/* 164 */             System.arraycopy(Byte2IntArrayMap.this.key, this.next + 1, Byte2IntArrayMap.this.key, this.next, tail);
/* 165 */             System.arraycopy(Byte2IntArrayMap.this.value, this.next + 1, Byte2IntArrayMap.this.value, this.next, tail);
/*     */           }
/*     */ 
/*     */ 
/*     */ 
/*     */           
/*     */           public void forEachRemaining(Consumer<? super Byte2IntMap.Entry> action) {
/* 172 */             int max = Byte2IntArrayMap.this.size;
/* 173 */             while (this.next < max) {
/* 174 */               action.accept(new AbstractByte2IntMap.BasicEntry(Byte2IntArrayMap.this.key[this.curr = this.next], Byte2IntArrayMap.this.value[this.next++]));
/*     */             }
/*     */           }
/*     */         };
/*     */     }
/*     */ 
/*     */     
/*     */     public ObjectIterator<Byte2IntMap.Entry> fastIterator() {
/* 182 */       return new ObjectIterator<Byte2IntMap.Entry>() {
/* 183 */           int next = 0; int curr = -1;
/* 184 */           final AbstractByte2IntMap.BasicEntry entry = new AbstractByte2IntMap.BasicEntry();
/*     */ 
/*     */           
/*     */           public boolean hasNext() {
/* 188 */             return (this.next < Byte2IntArrayMap.this.size);
/*     */           }
/*     */ 
/*     */ 
/*     */           
/*     */           public Byte2IntMap.Entry next() {
/* 194 */             if (!hasNext()) throw new NoSuchElementException(); 
/* 195 */             this.entry.key = Byte2IntArrayMap.this.key[this.curr = this.next];
/* 196 */             this.entry.value = Byte2IntArrayMap.this.value[this.next++];
/* 197 */             return this.entry;
/*     */           }
/*     */ 
/*     */           
/*     */           public void remove() {
/* 202 */             if (this.curr == -1) throw new IllegalStateException(); 
/* 203 */             this.curr = -1;
/* 204 */             int tail = Byte2IntArrayMap.this.size-- - this.next--;
/* 205 */             System.arraycopy(Byte2IntArrayMap.this.key, this.next + 1, Byte2IntArrayMap.this.key, this.next, tail);
/* 206 */             System.arraycopy(Byte2IntArrayMap.this.value, this.next + 1, Byte2IntArrayMap.this.value, this.next, tail);
/*     */           }
/*     */ 
/*     */ 
/*     */ 
/*     */           
/*     */           public void forEachRemaining(Consumer<? super Byte2IntMap.Entry> action) {
/* 213 */             int max = Byte2IntArrayMap.this.size;
/* 214 */             while (this.next < max) {
/* 215 */               this.entry.key = Byte2IntArrayMap.this.key[this.curr = this.next];
/* 216 */               this.entry.value = Byte2IntArrayMap.this.value[this.next++];
/* 217 */               action.accept(this.entry);
/*     */             } 
/*     */           }
/*     */         };
/*     */     }
/*     */     
/*     */     final class EntrySetSpliterator
/*     */       extends ObjectSpliterators.EarlyBindingSizeIndexBasedSpliterator<Byte2IntMap.Entry>
/*     */       implements ObjectSpliterator<Byte2IntMap.Entry> {
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
/*     */       protected final Byte2IntMap.Entry get(int location) {
/* 238 */         return new AbstractByte2IntMap.BasicEntry(Byte2IntArrayMap.this.key[location], Byte2IntArrayMap.this.value[location]);
/*     */       }
/*     */ 
/*     */       
/*     */       protected final EntrySetSpliterator makeForSplit(int pos, int maxPos) {
/* 243 */         return new EntrySetSpliterator(pos, maxPos);
/*     */       }
/*     */     }
/*     */ 
/*     */     
/*     */     public ObjectSpliterator<Byte2IntMap.Entry> spliterator() {
/* 249 */       return new EntrySetSpliterator(0, Byte2IntArrayMap.this.size);
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public void forEach(Consumer<? super Byte2IntMap.Entry> action) {
/* 257 */       for (int i = 0, max = Byte2IntArrayMap.this.size; i < max; i++) {
/* 258 */         action.accept(new AbstractByte2IntMap.BasicEntry(Byte2IntArrayMap.this.key[i], Byte2IntArrayMap.this.value[i]));
/*     */       }
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public void fastForEach(Consumer<? super Byte2IntMap.Entry> action) {
/* 266 */       AbstractByte2IntMap.BasicEntry entry = new AbstractByte2IntMap.BasicEntry();
/*     */       
/* 268 */       for (int i = 0, max = Byte2IntArrayMap.this.size; i < max; i++) {
/* 269 */         entry.key = Byte2IntArrayMap.this.key[i];
/* 270 */         entry.value = Byte2IntArrayMap.this.value[i];
/* 271 */         action.accept(entry);
/*     */       } 
/*     */     }
/*     */ 
/*     */     
/*     */     public int size() {
/* 277 */       return Byte2IntArrayMap.this.size;
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     public boolean contains(Object o) {
/* 283 */       if (!(o instanceof Map.Entry)) return false; 
/* 284 */       Map.Entry<?, ?> e = (Map.Entry<?, ?>)o;
/* 285 */       if (e.getKey() == null || !(e.getKey() instanceof Byte)) return false; 
/* 286 */       if (e.getValue() == null || !(e.getValue() instanceof Integer)) return false; 
/* 287 */       byte k = ((Byte)e.getKey()).byteValue();
/* 288 */       return (Byte2IntArrayMap.this.containsKey(k) && Byte2IntArrayMap.this.get(k) == ((Integer)e.getValue()).intValue());
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     public boolean remove(Object o) {
/* 294 */       if (!(o instanceof Map.Entry)) return false; 
/* 295 */       Map.Entry<?, ?> e = (Map.Entry<?, ?>)o;
/* 296 */       if (e.getKey() == null || !(e.getKey() instanceof Byte)) return false; 
/* 297 */       if (e.getValue() == null || !(e.getValue() instanceof Integer)) return false; 
/* 298 */       byte k = ((Byte)e.getKey()).byteValue();
/* 299 */       int v = ((Integer)e.getValue()).intValue();
/* 300 */       int oldPos = Byte2IntArrayMap.this.findKey(k);
/* 301 */       if (oldPos == -1 || v != Byte2IntArrayMap.this.value[oldPos]) return false; 
/* 302 */       int tail = Byte2IntArrayMap.this.size - oldPos - 1;
/* 303 */       System.arraycopy(Byte2IntArrayMap.this.key, oldPos + 1, Byte2IntArrayMap.this.key, oldPos, tail);
/* 304 */       System.arraycopy(Byte2IntArrayMap.this.value, oldPos + 1, Byte2IntArrayMap.this.value, oldPos, tail);
/* 305 */       Byte2IntArrayMap.this.size--;
/* 306 */       return true;
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   public Byte2IntMap.FastEntrySet byte2IntEntrySet() {
/* 312 */     if (this.entries == null) this.entries = new EntrySet(); 
/* 313 */     return this.entries;
/*     */   }
/*     */   
/*     */   private int findKey(byte k) {
/* 317 */     byte[] key = this.key;
/* 318 */     for (int i = this.size; i-- != 0;) { if (key[i] == k) return i;  }
/* 319 */      return -1;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public int get(byte k) {
/* 325 */     byte[] key = this.key;
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
/*     */   public boolean containsKey(byte k) {
/* 342 */     return (findKey(k) != -1);
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean containsValue(int v) {
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
/*     */   public int put(byte k, int v) {
/* 359 */     int oldKey = findKey(k);
/* 360 */     if (oldKey != -1) {
/* 361 */       int oldValue = this.value[oldKey];
/* 362 */       this.value[oldKey] = v;
/* 363 */       return oldValue;
/*     */     } 
/* 365 */     if (this.size == this.key.length) {
/* 366 */       byte[] newKey = new byte[(this.size == 0) ? 2 : (this.size * 2)];
/* 367 */       int[] newValue = new int[(this.size == 0) ? 2 : (this.size * 2)];
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
/*     */   public int remove(byte k) {
/* 384 */     int oldPos = findKey(k);
/* 385 */     if (oldPos == -1) return this.defRetValue; 
/* 386 */     int oldValue = this.value[oldPos];
/* 387 */     int tail = this.size - oldPos - 1;
/* 388 */     System.arraycopy(this.key, oldPos + 1, this.key, oldPos, tail);
/* 389 */     System.arraycopy(this.value, oldPos + 1, this.value, oldPos, tail);
/* 390 */     this.size--;
/* 391 */     return oldValue;
/*     */   }
/*     */   
/*     */   private final class KeySet extends AbstractByteSet { private KeySet() {}
/*     */     
/*     */     public boolean contains(byte k) {
/* 397 */       return (Byte2IntArrayMap.this.findKey(k) != -1);
/*     */     }
/*     */ 
/*     */     
/*     */     public boolean remove(byte k) {
/* 402 */       int oldPos = Byte2IntArrayMap.this.findKey(k);
/* 403 */       if (oldPos == -1) return false; 
/* 404 */       int tail = Byte2IntArrayMap.this.size - oldPos - 1;
/* 405 */       System.arraycopy(Byte2IntArrayMap.this.key, oldPos + 1, Byte2IntArrayMap.this.key, oldPos, tail);
/* 406 */       System.arraycopy(Byte2IntArrayMap.this.value, oldPos + 1, Byte2IntArrayMap.this.value, oldPos, tail);
/* 407 */       Byte2IntArrayMap.this.size--;
/* 408 */       return true;
/*     */     }
/*     */ 
/*     */     
/*     */     public ByteIterator iterator() {
/* 413 */       return new ByteIterator() {
/* 414 */           int pos = 0;
/*     */ 
/*     */           
/*     */           public boolean hasNext() {
/* 418 */             return (this.pos < Byte2IntArrayMap.this.size);
/*     */           }
/*     */ 
/*     */ 
/*     */           
/*     */           public byte nextByte() {
/* 424 */             if (!hasNext()) throw new NoSuchElementException(); 
/* 425 */             return Byte2IntArrayMap.this.key[this.pos++];
/*     */           }
/*     */ 
/*     */           
/*     */           public void remove() {
/* 430 */             if (this.pos == 0) throw new IllegalStateException(); 
/* 431 */             int tail = Byte2IntArrayMap.this.size - this.pos;
/* 432 */             System.arraycopy(Byte2IntArrayMap.this.key, this.pos, Byte2IntArrayMap.this.key, this.pos - 1, tail);
/* 433 */             System.arraycopy(Byte2IntArrayMap.this.value, this.pos, Byte2IntArrayMap.this.value, this.pos - 1, tail);
/* 434 */             Byte2IntArrayMap.this.size--;
/* 435 */             this.pos--;
/*     */           }
/*     */ 
/*     */ 
/*     */ 
/*     */           
/*     */           public void forEachRemaining(ByteConsumer action) {
/* 442 */             int max = Byte2IntArrayMap.this.size;
/* 443 */             while (this.pos < max)
/* 444 */               action.accept(Byte2IntArrayMap.this.key[this.pos++]); 
/*     */           }
/*     */         };
/*     */     }
/*     */     
/*     */     final class KeySetSpliterator
/*     */       extends ByteSpliterators.EarlyBindingSizeIndexBasedSpliterator
/*     */       implements ByteSpliterator {
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
/*     */       protected final byte get(int location) {
/* 464 */         return Byte2IntArrayMap.this.key[location];
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
/*     */       public void forEachRemaining(ByteConsumer action) {
/* 476 */         int max = Byte2IntArrayMap.this.size;
/* 477 */         while (this.pos < max) {
/* 478 */           action.accept(Byte2IntArrayMap.this.key[this.pos++]);
/*     */         }
/*     */       }
/*     */     }
/*     */ 
/*     */     
/*     */     public ByteSpliterator spliterator() {
/* 485 */       return new KeySetSpliterator(0, Byte2IntArrayMap.this.size);
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public void forEach(ByteConsumer action) {
/* 492 */       for (int i = 0, max = Byte2IntArrayMap.this.size; i < max; i++) {
/* 493 */         action.accept(Byte2IntArrayMap.this.key[i]);
/*     */       }
/*     */     }
/*     */ 
/*     */     
/*     */     public int size() {
/* 499 */       return Byte2IntArrayMap.this.size;
/*     */     }
/*     */ 
/*     */     
/*     */     public void clear() {
/* 504 */       Byte2IntArrayMap.this.clear();
/*     */     } }
/*     */ 
/*     */ 
/*     */   
/*     */   public ByteSet keySet() {
/* 510 */     if (this.keys == null) this.keys = new KeySet(); 
/* 511 */     return this.keys;
/*     */   }
/*     */   
/*     */   private final class ValuesCollection extends AbstractIntCollection { private ValuesCollection() {}
/*     */     
/*     */     public boolean contains(int v) {
/* 517 */       return Byte2IntArrayMap.this.containsValue(v);
/*     */     }
/*     */ 
/*     */     
/*     */     public IntIterator iterator() {
/* 522 */       return new IntIterator() {
/* 523 */           int pos = 0;
/*     */ 
/*     */           
/*     */           public boolean hasNext() {
/* 527 */             return (this.pos < Byte2IntArrayMap.this.size);
/*     */           }
/*     */ 
/*     */ 
/*     */           
/*     */           public int nextInt() {
/* 533 */             if (!hasNext()) throw new NoSuchElementException(); 
/* 534 */             return Byte2IntArrayMap.this.value[this.pos++];
/*     */           }
/*     */ 
/*     */           
/*     */           public void remove() {
/* 539 */             if (this.pos == 0) throw new IllegalStateException(); 
/* 540 */             int tail = Byte2IntArrayMap.this.size - this.pos;
/* 541 */             System.arraycopy(Byte2IntArrayMap.this.key, this.pos, Byte2IntArrayMap.this.key, this.pos - 1, tail);
/* 542 */             System.arraycopy(Byte2IntArrayMap.this.value, this.pos, Byte2IntArrayMap.this.value, this.pos - 1, tail);
/* 543 */             Byte2IntArrayMap.this.size--;
/* 544 */             this.pos--;
/*     */           }
/*     */ 
/*     */ 
/*     */ 
/*     */           
/*     */           public void forEachRemaining(IntConsumer action) {
/* 551 */             int max = Byte2IntArrayMap.this.size;
/* 552 */             while (this.pos < max)
/* 553 */               action.accept(Byte2IntArrayMap.this.value[this.pos++]); 
/*     */           }
/*     */         };
/*     */     }
/*     */     
/*     */     final class ValuesSpliterator
/*     */       extends IntSpliterators.EarlyBindingSizeIndexBasedSpliterator
/*     */       implements IntSpliterator {
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
/*     */       protected final int get(int location) {
/* 573 */         return Byte2IntArrayMap.this.value[location];
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
/*     */       public void forEachRemaining(IntConsumer action) {
/* 585 */         int max = Byte2IntArrayMap.this.size;
/* 586 */         while (this.pos < max) {
/* 587 */           action.accept(Byte2IntArrayMap.this.value[this.pos++]);
/*     */         }
/*     */       }
/*     */     }
/*     */ 
/*     */     
/*     */     public IntSpliterator spliterator() {
/* 594 */       return new ValuesSpliterator(0, Byte2IntArrayMap.this.size);
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public void forEach(IntConsumer action) {
/* 601 */       for (int i = 0, max = Byte2IntArrayMap.this.size; i < max; i++) {
/* 602 */         action.accept(Byte2IntArrayMap.this.value[i]);
/*     */       }
/*     */     }
/*     */ 
/*     */     
/*     */     public int size() {
/* 608 */       return Byte2IntArrayMap.this.size;
/*     */     }
/*     */ 
/*     */     
/*     */     public void clear() {
/* 613 */       Byte2IntArrayMap.this.clear();
/*     */     } }
/*     */ 
/*     */ 
/*     */   
/*     */   public IntCollection values() {
/* 619 */     if (this.values == null) this.values = (IntCollection)new ValuesCollection(); 
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
/*     */   public Byte2IntArrayMap clone() {
/*     */     Byte2IntArrayMap c;
/*     */     try {
/* 637 */       c = (Byte2IntArrayMap)super.clone();
/* 638 */     } catch (CloneNotSupportedException cantHappen) {
/* 639 */       throw new InternalError();
/*     */     } 
/* 641 */     c.key = (byte[])this.key.clone();
/* 642 */     c.value = (int[])this.value.clone();
/* 643 */     c.entries = null;
/* 644 */     c.keys = null;
/* 645 */     c.values = null;
/* 646 */     return c;
/*     */   }
/*     */   
/*     */   private void writeObject(ObjectOutputStream s) throws IOException {
/* 650 */     s.defaultWriteObject();
/* 651 */     for (int i = 0, max = this.size; i < max; i++) {
/* 652 */       s.writeByte(this.key[i]);
/* 653 */       s.writeInt(this.value[i]);
/*     */     } 
/*     */   }
/*     */   
/*     */   private void readObject(ObjectInputStream s) throws IOException, ClassNotFoundException {
/* 658 */     s.defaultReadObject();
/* 659 */     this.key = new byte[this.size];
/* 660 */     this.value = new int[this.size];
/* 661 */     for (int i = 0; i < this.size; i++) {
/* 662 */       this.key[i] = s.readByte();
/* 663 */       this.value[i] = s.readInt();
/*     */     } 
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\i\\unimi\dsi\fastutil\bytes\Byte2IntArrayMap.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */