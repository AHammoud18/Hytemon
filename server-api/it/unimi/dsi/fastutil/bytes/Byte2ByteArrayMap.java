/*     */ package it.unimi.dsi.fastutil.bytes;
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
/*     */ public class Byte2ByteArrayMap
/*     */   extends AbstractByte2ByteMap
/*     */   implements Serializable, Cloneable
/*     */ {
/*     */   private static final long serialVersionUID = 1L;
/*     */   protected transient byte[] key;
/*     */   protected transient byte[] value;
/*     */   protected int size;
/*     */   protected transient Byte2ByteMap.FastEntrySet entries;
/*     */   protected transient ByteSet keys;
/*     */   protected transient ByteCollection values;
/*     */   
/*     */   public Byte2ByteArrayMap(byte[] key, byte[] value) {
/*  60 */     this.key = key;
/*  61 */     this.value = value;
/*  62 */     this.size = key.length;
/*  63 */     if (key.length != value.length) throw new IllegalArgumentException("Keys and values have different lengths (" + key.length + ", " + value.length + ")");
/*     */   
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public Byte2ByteArrayMap() {
/*  70 */     this.key = ByteArrays.EMPTY_ARRAY;
/*  71 */     this.value = ByteArrays.EMPTY_ARRAY;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Byte2ByteArrayMap(int capacity) {
/*  80 */     this.key = new byte[capacity];
/*  81 */     this.value = new byte[capacity];
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Byte2ByteArrayMap(Byte2ByteMap m) {
/*  90 */     this(m.size());
/*  91 */     int i = 0;
/*  92 */     for (ObjectIterator<Byte2ByteMap.Entry> objectIterator = m.byte2ByteEntrySet().iterator(); objectIterator.hasNext(); ) { Byte2ByteMap.Entry e = objectIterator.next();
/*  93 */       this.key[i] = e.getByteKey();
/*  94 */       this.value[i] = e.getByteValue();
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
/*     */   public Byte2ByteArrayMap(Map<? extends Byte, ? extends Byte> m) {
/* 106 */     this(m.size());
/* 107 */     int i = 0;
/* 108 */     for (Map.Entry<? extends Byte, ? extends Byte> e : m.entrySet()) {
/* 109 */       this.key[i] = ((Byte)e.getKey()).byteValue();
/* 110 */       this.value[i] = ((Byte)e.getValue()).byteValue();
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
/*     */   public Byte2ByteArrayMap(byte[] key, byte[] value, int size) {
/* 129 */     this.key = key;
/* 130 */     this.value = value;
/* 131 */     this.size = size;
/* 132 */     if (key.length != value.length) throw new IllegalArgumentException("Keys and values have different lengths (" + key.length + ", " + value.length + ")"); 
/* 133 */     if (size > key.length) throw new IllegalArgumentException("The provided size (" + size + ") is larger than or equal to the backing-arrays size (" + key.length + ")"); 
/*     */   }
/*     */   
/*     */   private final class EntrySet
/*     */     extends AbstractObjectSet<Byte2ByteMap.Entry> implements Byte2ByteMap.FastEntrySet {
/*     */     private EntrySet() {}
/*     */     
/*     */     public ObjectIterator<Byte2ByteMap.Entry> iterator() {
/* 141 */       return new ObjectIterator<Byte2ByteMap.Entry>() {
/* 142 */           int curr = -1; int next = 0;
/*     */ 
/*     */           
/*     */           public boolean hasNext() {
/* 146 */             return (this.next < Byte2ByteArrayMap.this.size);
/*     */           }
/*     */ 
/*     */ 
/*     */           
/*     */           public Byte2ByteMap.Entry next() {
/* 152 */             if (!hasNext()) throw new NoSuchElementException(); 
/* 153 */             return new AbstractByte2ByteMap.BasicEntry(Byte2ByteArrayMap.this.key[this.curr = this.next], Byte2ByteArrayMap.this.value[this.next++]);
/*     */           }
/*     */ 
/*     */           
/*     */           public void remove() {
/* 158 */             if (this.curr == -1) throw new IllegalStateException(); 
/* 159 */             this.curr = -1;
/* 160 */             int tail = Byte2ByteArrayMap.this.size-- - this.next--;
/* 161 */             System.arraycopy(Byte2ByteArrayMap.this.key, this.next + 1, Byte2ByteArrayMap.this.key, this.next, tail);
/* 162 */             System.arraycopy(Byte2ByteArrayMap.this.value, this.next + 1, Byte2ByteArrayMap.this.value, this.next, tail);
/*     */           }
/*     */ 
/*     */ 
/*     */ 
/*     */           
/*     */           public void forEachRemaining(Consumer<? super Byte2ByteMap.Entry> action) {
/* 169 */             int max = Byte2ByteArrayMap.this.size;
/* 170 */             while (this.next < max) {
/* 171 */               action.accept(new AbstractByte2ByteMap.BasicEntry(Byte2ByteArrayMap.this.key[this.curr = this.next], Byte2ByteArrayMap.this.value[this.next++]));
/*     */             }
/*     */           }
/*     */         };
/*     */     }
/*     */ 
/*     */     
/*     */     public ObjectIterator<Byte2ByteMap.Entry> fastIterator() {
/* 179 */       return new ObjectIterator<Byte2ByteMap.Entry>() {
/* 180 */           int next = 0; int curr = -1;
/* 181 */           final AbstractByte2ByteMap.BasicEntry entry = new AbstractByte2ByteMap.BasicEntry();
/*     */ 
/*     */           
/*     */           public boolean hasNext() {
/* 185 */             return (this.next < Byte2ByteArrayMap.this.size);
/*     */           }
/*     */ 
/*     */ 
/*     */           
/*     */           public Byte2ByteMap.Entry next() {
/* 191 */             if (!hasNext()) throw new NoSuchElementException(); 
/* 192 */             this.entry.key = Byte2ByteArrayMap.this.key[this.curr = this.next];
/* 193 */             this.entry.value = Byte2ByteArrayMap.this.value[this.next++];
/* 194 */             return this.entry;
/*     */           }
/*     */ 
/*     */           
/*     */           public void remove() {
/* 199 */             if (this.curr == -1) throw new IllegalStateException(); 
/* 200 */             this.curr = -1;
/* 201 */             int tail = Byte2ByteArrayMap.this.size-- - this.next--;
/* 202 */             System.arraycopy(Byte2ByteArrayMap.this.key, this.next + 1, Byte2ByteArrayMap.this.key, this.next, tail);
/* 203 */             System.arraycopy(Byte2ByteArrayMap.this.value, this.next + 1, Byte2ByteArrayMap.this.value, this.next, tail);
/*     */           }
/*     */ 
/*     */ 
/*     */ 
/*     */           
/*     */           public void forEachRemaining(Consumer<? super Byte2ByteMap.Entry> action) {
/* 210 */             int max = Byte2ByteArrayMap.this.size;
/* 211 */             while (this.next < max) {
/* 212 */               this.entry.key = Byte2ByteArrayMap.this.key[this.curr = this.next];
/* 213 */               this.entry.value = Byte2ByteArrayMap.this.value[this.next++];
/* 214 */               action.accept(this.entry);
/*     */             } 
/*     */           }
/*     */         };
/*     */     }
/*     */     
/*     */     final class EntrySetSpliterator
/*     */       extends ObjectSpliterators.EarlyBindingSizeIndexBasedSpliterator<Byte2ByteMap.Entry>
/*     */       implements ObjectSpliterator<Byte2ByteMap.Entry> {
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
/*     */       protected final Byte2ByteMap.Entry get(int location) {
/* 235 */         return new AbstractByte2ByteMap.BasicEntry(Byte2ByteArrayMap.this.key[location], Byte2ByteArrayMap.this.value[location]);
/*     */       }
/*     */ 
/*     */       
/*     */       protected final EntrySetSpliterator makeForSplit(int pos, int maxPos) {
/* 240 */         return new EntrySetSpliterator(pos, maxPos);
/*     */       }
/*     */     }
/*     */ 
/*     */     
/*     */     public ObjectSpliterator<Byte2ByteMap.Entry> spliterator() {
/* 246 */       return new EntrySetSpliterator(0, Byte2ByteArrayMap.this.size);
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public void forEach(Consumer<? super Byte2ByteMap.Entry> action) {
/* 254 */       for (int i = 0, max = Byte2ByteArrayMap.this.size; i < max; i++) {
/* 255 */         action.accept(new AbstractByte2ByteMap.BasicEntry(Byte2ByteArrayMap.this.key[i], Byte2ByteArrayMap.this.value[i]));
/*     */       }
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public void fastForEach(Consumer<? super Byte2ByteMap.Entry> action) {
/* 263 */       AbstractByte2ByteMap.BasicEntry entry = new AbstractByte2ByteMap.BasicEntry();
/*     */       
/* 265 */       for (int i = 0, max = Byte2ByteArrayMap.this.size; i < max; i++) {
/* 266 */         entry.key = Byte2ByteArrayMap.this.key[i];
/* 267 */         entry.value = Byte2ByteArrayMap.this.value[i];
/* 268 */         action.accept(entry);
/*     */       } 
/*     */     }
/*     */ 
/*     */     
/*     */     public int size() {
/* 274 */       return Byte2ByteArrayMap.this.size;
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     public boolean contains(Object o) {
/* 280 */       if (!(o instanceof Map.Entry)) return false; 
/* 281 */       Map.Entry<?, ?> e = (Map.Entry<?, ?>)o;
/* 282 */       if (e.getKey() == null || !(e.getKey() instanceof Byte)) return false; 
/* 283 */       if (e.getValue() == null || !(e.getValue() instanceof Byte)) return false; 
/* 284 */       byte k = ((Byte)e.getKey()).byteValue();
/* 285 */       return (Byte2ByteArrayMap.this.containsKey(k) && Byte2ByteArrayMap.this.get(k) == ((Byte)e.getValue()).byteValue());
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     public boolean remove(Object o) {
/* 291 */       if (!(o instanceof Map.Entry)) return false; 
/* 292 */       Map.Entry<?, ?> e = (Map.Entry<?, ?>)o;
/* 293 */       if (e.getKey() == null || !(e.getKey() instanceof Byte)) return false; 
/* 294 */       if (e.getValue() == null || !(e.getValue() instanceof Byte)) return false; 
/* 295 */       byte k = ((Byte)e.getKey()).byteValue();
/* 296 */       byte v = ((Byte)e.getValue()).byteValue();
/* 297 */       int oldPos = Byte2ByteArrayMap.this.findKey(k);
/* 298 */       if (oldPos == -1 || v != Byte2ByteArrayMap.this.value[oldPos]) return false; 
/* 299 */       int tail = Byte2ByteArrayMap.this.size - oldPos - 1;
/* 300 */       System.arraycopy(Byte2ByteArrayMap.this.key, oldPos + 1, Byte2ByteArrayMap.this.key, oldPos, tail);
/* 301 */       System.arraycopy(Byte2ByteArrayMap.this.value, oldPos + 1, Byte2ByteArrayMap.this.value, oldPos, tail);
/* 302 */       Byte2ByteArrayMap.this.size--;
/* 303 */       return true;
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   public Byte2ByteMap.FastEntrySet byte2ByteEntrySet() {
/* 309 */     if (this.entries == null) this.entries = new EntrySet(); 
/* 310 */     return this.entries;
/*     */   }
/*     */   
/*     */   private int findKey(byte k) {
/* 314 */     byte[] key = this.key;
/* 315 */     for (int i = this.size; i-- != 0;) { if (key[i] == k) return i;  }
/* 316 */      return -1;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public byte get(byte k) {
/* 322 */     byte[] key = this.key;
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
/*     */   public boolean containsKey(byte k) {
/* 339 */     return (findKey(k) != -1);
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean containsValue(byte v) {
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
/*     */   public byte put(byte k, byte v) {
/* 356 */     int oldKey = findKey(k);
/* 357 */     if (oldKey != -1) {
/* 358 */       byte oldValue = this.value[oldKey];
/* 359 */       this.value[oldKey] = v;
/* 360 */       return oldValue;
/*     */     } 
/* 362 */     if (this.size == this.key.length) {
/* 363 */       byte[] newKey = new byte[(this.size == 0) ? 2 : (this.size * 2)];
/* 364 */       byte[] newValue = new byte[(this.size == 0) ? 2 : (this.size * 2)];
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
/*     */   public byte remove(byte k) {
/* 381 */     int oldPos = findKey(k);
/* 382 */     if (oldPos == -1) return this.defRetValue; 
/* 383 */     byte oldValue = this.value[oldPos];
/* 384 */     int tail = this.size - oldPos - 1;
/* 385 */     System.arraycopy(this.key, oldPos + 1, this.key, oldPos, tail);
/* 386 */     System.arraycopy(this.value, oldPos + 1, this.value, oldPos, tail);
/* 387 */     this.size--;
/* 388 */     return oldValue;
/*     */   }
/*     */   
/*     */   private final class KeySet extends AbstractByteSet { private KeySet() {}
/*     */     
/*     */     public boolean contains(byte k) {
/* 394 */       return (Byte2ByteArrayMap.this.findKey(k) != -1);
/*     */     }
/*     */ 
/*     */     
/*     */     public boolean remove(byte k) {
/* 399 */       int oldPos = Byte2ByteArrayMap.this.findKey(k);
/* 400 */       if (oldPos == -1) return false; 
/* 401 */       int tail = Byte2ByteArrayMap.this.size - oldPos - 1;
/* 402 */       System.arraycopy(Byte2ByteArrayMap.this.key, oldPos + 1, Byte2ByteArrayMap.this.key, oldPos, tail);
/* 403 */       System.arraycopy(Byte2ByteArrayMap.this.value, oldPos + 1, Byte2ByteArrayMap.this.value, oldPos, tail);
/* 404 */       Byte2ByteArrayMap.this.size--;
/* 405 */       return true;
/*     */     }
/*     */ 
/*     */     
/*     */     public ByteIterator iterator() {
/* 410 */       return new ByteIterator() {
/* 411 */           int pos = 0;
/*     */ 
/*     */           
/*     */           public boolean hasNext() {
/* 415 */             return (this.pos < Byte2ByteArrayMap.this.size);
/*     */           }
/*     */ 
/*     */ 
/*     */           
/*     */           public byte nextByte() {
/* 421 */             if (!hasNext()) throw new NoSuchElementException(); 
/* 422 */             return Byte2ByteArrayMap.this.key[this.pos++];
/*     */           }
/*     */ 
/*     */           
/*     */           public void remove() {
/* 427 */             if (this.pos == 0) throw new IllegalStateException(); 
/* 428 */             int tail = Byte2ByteArrayMap.this.size - this.pos;
/* 429 */             System.arraycopy(Byte2ByteArrayMap.this.key, this.pos, Byte2ByteArrayMap.this.key, this.pos - 1, tail);
/* 430 */             System.arraycopy(Byte2ByteArrayMap.this.value, this.pos, Byte2ByteArrayMap.this.value, this.pos - 1, tail);
/* 431 */             Byte2ByteArrayMap.this.size--;
/* 432 */             this.pos--;
/*     */           }
/*     */ 
/*     */ 
/*     */ 
/*     */           
/*     */           public void forEachRemaining(ByteConsumer action) {
/* 439 */             int max = Byte2ByteArrayMap.this.size;
/* 440 */             while (this.pos < max)
/* 441 */               action.accept(Byte2ByteArrayMap.this.key[this.pos++]); 
/*     */           }
/*     */         };
/*     */     }
/*     */     
/*     */     final class KeySetSpliterator
/*     */       extends ByteSpliterators.EarlyBindingSizeIndexBasedSpliterator
/*     */       implements ByteSpliterator {
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
/*     */       protected final byte get(int location) {
/* 461 */         return Byte2ByteArrayMap.this.key[location];
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
/*     */       public void forEachRemaining(ByteConsumer action) {
/* 473 */         int max = Byte2ByteArrayMap.this.size;
/* 474 */         while (this.pos < max) {
/* 475 */           action.accept(Byte2ByteArrayMap.this.key[this.pos++]);
/*     */         }
/*     */       }
/*     */     }
/*     */ 
/*     */     
/*     */     public ByteSpliterator spliterator() {
/* 482 */       return new KeySetSpliterator(0, Byte2ByteArrayMap.this.size);
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public void forEach(ByteConsumer action) {
/* 489 */       for (int i = 0, max = Byte2ByteArrayMap.this.size; i < max; i++) {
/* 490 */         action.accept(Byte2ByteArrayMap.this.key[i]);
/*     */       }
/*     */     }
/*     */ 
/*     */     
/*     */     public int size() {
/* 496 */       return Byte2ByteArrayMap.this.size;
/*     */     }
/*     */ 
/*     */     
/*     */     public void clear() {
/* 501 */       Byte2ByteArrayMap.this.clear();
/*     */     } }
/*     */ 
/*     */ 
/*     */   
/*     */   public ByteSet keySet() {
/* 507 */     if (this.keys == null) this.keys = new KeySet(); 
/* 508 */     return this.keys;
/*     */   }
/*     */   
/*     */   private final class ValuesCollection extends AbstractByteCollection { private ValuesCollection() {}
/*     */     
/*     */     public boolean contains(byte v) {
/* 514 */       return Byte2ByteArrayMap.this.containsValue(v);
/*     */     }
/*     */ 
/*     */     
/*     */     public ByteIterator iterator() {
/* 519 */       return new ByteIterator() {
/* 520 */           int pos = 0;
/*     */ 
/*     */           
/*     */           public boolean hasNext() {
/* 524 */             return (this.pos < Byte2ByteArrayMap.this.size);
/*     */           }
/*     */ 
/*     */ 
/*     */           
/*     */           public byte nextByte() {
/* 530 */             if (!hasNext()) throw new NoSuchElementException(); 
/* 531 */             return Byte2ByteArrayMap.this.value[this.pos++];
/*     */           }
/*     */ 
/*     */           
/*     */           public void remove() {
/* 536 */             if (this.pos == 0) throw new IllegalStateException(); 
/* 537 */             int tail = Byte2ByteArrayMap.this.size - this.pos;
/* 538 */             System.arraycopy(Byte2ByteArrayMap.this.key, this.pos, Byte2ByteArrayMap.this.key, this.pos - 1, tail);
/* 539 */             System.arraycopy(Byte2ByteArrayMap.this.value, this.pos, Byte2ByteArrayMap.this.value, this.pos - 1, tail);
/* 540 */             Byte2ByteArrayMap.this.size--;
/* 541 */             this.pos--;
/*     */           }
/*     */ 
/*     */ 
/*     */ 
/*     */           
/*     */           public void forEachRemaining(ByteConsumer action) {
/* 548 */             int max = Byte2ByteArrayMap.this.size;
/* 549 */             while (this.pos < max)
/* 550 */               action.accept(Byte2ByteArrayMap.this.value[this.pos++]); 
/*     */           }
/*     */         };
/*     */     }
/*     */     
/*     */     final class ValuesSpliterator
/*     */       extends ByteSpliterators.EarlyBindingSizeIndexBasedSpliterator
/*     */       implements ByteSpliterator {
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
/*     */       protected final byte get(int location) {
/* 570 */         return Byte2ByteArrayMap.this.value[location];
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
/*     */       public void forEachRemaining(ByteConsumer action) {
/* 582 */         int max = Byte2ByteArrayMap.this.size;
/* 583 */         while (this.pos < max) {
/* 584 */           action.accept(Byte2ByteArrayMap.this.value[this.pos++]);
/*     */         }
/*     */       }
/*     */     }
/*     */ 
/*     */     
/*     */     public ByteSpliterator spliterator() {
/* 591 */       return new ValuesSpliterator(0, Byte2ByteArrayMap.this.size);
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public void forEach(ByteConsumer action) {
/* 598 */       for (int i = 0, max = Byte2ByteArrayMap.this.size; i < max; i++) {
/* 599 */         action.accept(Byte2ByteArrayMap.this.value[i]);
/*     */       }
/*     */     }
/*     */ 
/*     */     
/*     */     public int size() {
/* 605 */       return Byte2ByteArrayMap.this.size;
/*     */     }
/*     */ 
/*     */     
/*     */     public void clear() {
/* 610 */       Byte2ByteArrayMap.this.clear();
/*     */     } }
/*     */ 
/*     */ 
/*     */   
/*     */   public ByteCollection values() {
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
/*     */   public Byte2ByteArrayMap clone() {
/*     */     Byte2ByteArrayMap c;
/*     */     try {
/* 634 */       c = (Byte2ByteArrayMap)super.clone();
/* 635 */     } catch (CloneNotSupportedException cantHappen) {
/* 636 */       throw new InternalError();
/*     */     } 
/* 638 */     c.key = (byte[])this.key.clone();
/* 639 */     c.value = (byte[])this.value.clone();
/* 640 */     c.entries = null;
/* 641 */     c.keys = null;
/* 642 */     c.values = null;
/* 643 */     return c;
/*     */   }
/*     */   
/*     */   private void writeObject(ObjectOutputStream s) throws IOException {
/* 647 */     s.defaultWriteObject();
/* 648 */     for (int i = 0, max = this.size; i < max; i++) {
/* 649 */       s.writeByte(this.key[i]);
/* 650 */       s.writeByte(this.value[i]);
/*     */     } 
/*     */   }
/*     */   
/*     */   private void readObject(ObjectInputStream s) throws IOException, ClassNotFoundException {
/* 655 */     s.defaultReadObject();
/* 656 */     this.key = new byte[this.size];
/* 657 */     this.value = new byte[this.size];
/* 658 */     for (int i = 0; i < this.size; i++) {
/* 659 */       this.key[i] = s.readByte();
/* 660 */       this.value[i] = s.readByte();
/*     */     } 
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\i\\unimi\dsi\fastutil\bytes\Byte2ByteArrayMap.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */