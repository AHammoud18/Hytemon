/*     */ package it.unimi.dsi.fastutil.bytes;
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
/*     */ public class Byte2ShortArrayMap
/*     */   extends AbstractByte2ShortMap
/*     */   implements Serializable, Cloneable
/*     */ {
/*     */   private static final long serialVersionUID = 1L;
/*     */   protected transient byte[] key;
/*     */   protected transient short[] value;
/*     */   protected int size;
/*     */   protected transient Byte2ShortMap.FastEntrySet entries;
/*     */   protected transient ByteSet keys;
/*     */   protected transient ShortCollection values;
/*     */   
/*     */   public Byte2ShortArrayMap(byte[] key, short[] value) {
/*  64 */     this.key = key;
/*  65 */     this.value = value;
/*  66 */     this.size = key.length;
/*  67 */     if (key.length != value.length) throw new IllegalArgumentException("Keys and values have different lengths (" + key.length + ", " + value.length + ")");
/*     */   
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public Byte2ShortArrayMap() {
/*  74 */     this.key = ByteArrays.EMPTY_ARRAY;
/*  75 */     this.value = ShortArrays.EMPTY_ARRAY;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Byte2ShortArrayMap(int capacity) {
/*  84 */     this.key = new byte[capacity];
/*  85 */     this.value = new short[capacity];
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Byte2ShortArrayMap(Byte2ShortMap m) {
/*  94 */     this(m.size());
/*  95 */     int i = 0;
/*  96 */     for (ObjectIterator<Byte2ShortMap.Entry> objectIterator = m.byte2ShortEntrySet().iterator(); objectIterator.hasNext(); ) { Byte2ShortMap.Entry e = objectIterator.next();
/*  97 */       this.key[i] = e.getByteKey();
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
/*     */   public Byte2ShortArrayMap(Map<? extends Byte, ? extends Short> m) {
/* 110 */     this(m.size());
/* 111 */     int i = 0;
/* 112 */     for (Map.Entry<? extends Byte, ? extends Short> e : m.entrySet()) {
/* 113 */       this.key[i] = ((Byte)e.getKey()).byteValue();
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
/*     */   public Byte2ShortArrayMap(byte[] key, short[] value, int size) {
/* 133 */     this.key = key;
/* 134 */     this.value = value;
/* 135 */     this.size = size;
/* 136 */     if (key.length != value.length) throw new IllegalArgumentException("Keys and values have different lengths (" + key.length + ", " + value.length + ")"); 
/* 137 */     if (size > key.length) throw new IllegalArgumentException("The provided size (" + size + ") is larger than or equal to the backing-arrays size (" + key.length + ")"); 
/*     */   }
/*     */   
/*     */   private final class EntrySet
/*     */     extends AbstractObjectSet<Byte2ShortMap.Entry> implements Byte2ShortMap.FastEntrySet {
/*     */     private EntrySet() {}
/*     */     
/*     */     public ObjectIterator<Byte2ShortMap.Entry> iterator() {
/* 145 */       return new ObjectIterator<Byte2ShortMap.Entry>() {
/* 146 */           int curr = -1; int next = 0;
/*     */ 
/*     */           
/*     */           public boolean hasNext() {
/* 150 */             return (this.next < Byte2ShortArrayMap.this.size);
/*     */           }
/*     */ 
/*     */ 
/*     */           
/*     */           public Byte2ShortMap.Entry next() {
/* 156 */             if (!hasNext()) throw new NoSuchElementException(); 
/* 157 */             return new AbstractByte2ShortMap.BasicEntry(Byte2ShortArrayMap.this.key[this.curr = this.next], Byte2ShortArrayMap.this.value[this.next++]);
/*     */           }
/*     */ 
/*     */           
/*     */           public void remove() {
/* 162 */             if (this.curr == -1) throw new IllegalStateException(); 
/* 163 */             this.curr = -1;
/* 164 */             int tail = Byte2ShortArrayMap.this.size-- - this.next--;
/* 165 */             System.arraycopy(Byte2ShortArrayMap.this.key, this.next + 1, Byte2ShortArrayMap.this.key, this.next, tail);
/* 166 */             System.arraycopy(Byte2ShortArrayMap.this.value, this.next + 1, Byte2ShortArrayMap.this.value, this.next, tail);
/*     */           }
/*     */ 
/*     */ 
/*     */ 
/*     */           
/*     */           public void forEachRemaining(Consumer<? super Byte2ShortMap.Entry> action) {
/* 173 */             int max = Byte2ShortArrayMap.this.size;
/* 174 */             while (this.next < max) {
/* 175 */               action.accept(new AbstractByte2ShortMap.BasicEntry(Byte2ShortArrayMap.this.key[this.curr = this.next], Byte2ShortArrayMap.this.value[this.next++]));
/*     */             }
/*     */           }
/*     */         };
/*     */     }
/*     */ 
/*     */     
/*     */     public ObjectIterator<Byte2ShortMap.Entry> fastIterator() {
/* 183 */       return new ObjectIterator<Byte2ShortMap.Entry>() {
/* 184 */           int next = 0; int curr = -1;
/* 185 */           final AbstractByte2ShortMap.BasicEntry entry = new AbstractByte2ShortMap.BasicEntry();
/*     */ 
/*     */           
/*     */           public boolean hasNext() {
/* 189 */             return (this.next < Byte2ShortArrayMap.this.size);
/*     */           }
/*     */ 
/*     */ 
/*     */           
/*     */           public Byte2ShortMap.Entry next() {
/* 195 */             if (!hasNext()) throw new NoSuchElementException(); 
/* 196 */             this.entry.key = Byte2ShortArrayMap.this.key[this.curr = this.next];
/* 197 */             this.entry.value = Byte2ShortArrayMap.this.value[this.next++];
/* 198 */             return this.entry;
/*     */           }
/*     */ 
/*     */           
/*     */           public void remove() {
/* 203 */             if (this.curr == -1) throw new IllegalStateException(); 
/* 204 */             this.curr = -1;
/* 205 */             int tail = Byte2ShortArrayMap.this.size-- - this.next--;
/* 206 */             System.arraycopy(Byte2ShortArrayMap.this.key, this.next + 1, Byte2ShortArrayMap.this.key, this.next, tail);
/* 207 */             System.arraycopy(Byte2ShortArrayMap.this.value, this.next + 1, Byte2ShortArrayMap.this.value, this.next, tail);
/*     */           }
/*     */ 
/*     */ 
/*     */ 
/*     */           
/*     */           public void forEachRemaining(Consumer<? super Byte2ShortMap.Entry> action) {
/* 214 */             int max = Byte2ShortArrayMap.this.size;
/* 215 */             while (this.next < max) {
/* 216 */               this.entry.key = Byte2ShortArrayMap.this.key[this.curr = this.next];
/* 217 */               this.entry.value = Byte2ShortArrayMap.this.value[this.next++];
/* 218 */               action.accept(this.entry);
/*     */             } 
/*     */           }
/*     */         };
/*     */     }
/*     */     
/*     */     final class EntrySetSpliterator
/*     */       extends ObjectSpliterators.EarlyBindingSizeIndexBasedSpliterator<Byte2ShortMap.Entry>
/*     */       implements ObjectSpliterator<Byte2ShortMap.Entry> {
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
/*     */       protected final Byte2ShortMap.Entry get(int location) {
/* 239 */         return new AbstractByte2ShortMap.BasicEntry(Byte2ShortArrayMap.this.key[location], Byte2ShortArrayMap.this.value[location]);
/*     */       }
/*     */ 
/*     */       
/*     */       protected final EntrySetSpliterator makeForSplit(int pos, int maxPos) {
/* 244 */         return new EntrySetSpliterator(pos, maxPos);
/*     */       }
/*     */     }
/*     */ 
/*     */     
/*     */     public ObjectSpliterator<Byte2ShortMap.Entry> spliterator() {
/* 250 */       return new EntrySetSpliterator(0, Byte2ShortArrayMap.this.size);
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public void forEach(Consumer<? super Byte2ShortMap.Entry> action) {
/* 258 */       for (int i = 0, max = Byte2ShortArrayMap.this.size; i < max; i++) {
/* 259 */         action.accept(new AbstractByte2ShortMap.BasicEntry(Byte2ShortArrayMap.this.key[i], Byte2ShortArrayMap.this.value[i]));
/*     */       }
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public void fastForEach(Consumer<? super Byte2ShortMap.Entry> action) {
/* 267 */       AbstractByte2ShortMap.BasicEntry entry = new AbstractByte2ShortMap.BasicEntry();
/*     */       
/* 269 */       for (int i = 0, max = Byte2ShortArrayMap.this.size; i < max; i++) {
/* 270 */         entry.key = Byte2ShortArrayMap.this.key[i];
/* 271 */         entry.value = Byte2ShortArrayMap.this.value[i];
/* 272 */         action.accept(entry);
/*     */       } 
/*     */     }
/*     */ 
/*     */     
/*     */     public int size() {
/* 278 */       return Byte2ShortArrayMap.this.size;
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     public boolean contains(Object o) {
/* 284 */       if (!(o instanceof Map.Entry)) return false; 
/* 285 */       Map.Entry<?, ?> e = (Map.Entry<?, ?>)o;
/* 286 */       if (e.getKey() == null || !(e.getKey() instanceof Byte)) return false; 
/* 287 */       if (e.getValue() == null || !(e.getValue() instanceof Short)) return false; 
/* 288 */       byte k = ((Byte)e.getKey()).byteValue();
/* 289 */       return (Byte2ShortArrayMap.this.containsKey(k) && Byte2ShortArrayMap.this.get(k) == ((Short)e.getValue()).shortValue());
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     public boolean remove(Object o) {
/* 295 */       if (!(o instanceof Map.Entry)) return false; 
/* 296 */       Map.Entry<?, ?> e = (Map.Entry<?, ?>)o;
/* 297 */       if (e.getKey() == null || !(e.getKey() instanceof Byte)) return false; 
/* 298 */       if (e.getValue() == null || !(e.getValue() instanceof Short)) return false; 
/* 299 */       byte k = ((Byte)e.getKey()).byteValue();
/* 300 */       short v = ((Short)e.getValue()).shortValue();
/* 301 */       int oldPos = Byte2ShortArrayMap.this.findKey(k);
/* 302 */       if (oldPos == -1 || v != Byte2ShortArrayMap.this.value[oldPos]) return false; 
/* 303 */       int tail = Byte2ShortArrayMap.this.size - oldPos - 1;
/* 304 */       System.arraycopy(Byte2ShortArrayMap.this.key, oldPos + 1, Byte2ShortArrayMap.this.key, oldPos, tail);
/* 305 */       System.arraycopy(Byte2ShortArrayMap.this.value, oldPos + 1, Byte2ShortArrayMap.this.value, oldPos, tail);
/* 306 */       Byte2ShortArrayMap.this.size--;
/* 307 */       return true;
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   public Byte2ShortMap.FastEntrySet byte2ShortEntrySet() {
/* 313 */     if (this.entries == null) this.entries = new EntrySet(); 
/* 314 */     return this.entries;
/*     */   }
/*     */   
/*     */   private int findKey(byte k) {
/* 318 */     byte[] key = this.key;
/* 319 */     for (int i = this.size; i-- != 0;) { if (key[i] == k) return i;  }
/* 320 */      return -1;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public short get(byte k) {
/* 326 */     byte[] key = this.key;
/* 327 */     for (int i = this.size; i-- != 0;) { if (key[i] == k) return this.value[i];  }
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
/*     */   public boolean containsKey(byte k) {
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
/*     */   public short put(byte k, short v) {
/* 360 */     int oldKey = findKey(k);
/* 361 */     if (oldKey != -1) {
/* 362 */       short oldValue = this.value[oldKey];
/* 363 */       this.value[oldKey] = v;
/* 364 */       return oldValue;
/*     */     } 
/* 366 */     if (this.size == this.key.length) {
/* 367 */       byte[] newKey = new byte[(this.size == 0) ? 2 : (this.size * 2)];
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
/*     */   public short remove(byte k) {
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
/*     */   private final class KeySet extends AbstractByteSet { private KeySet() {}
/*     */     
/*     */     public boolean contains(byte k) {
/* 398 */       return (Byte2ShortArrayMap.this.findKey(k) != -1);
/*     */     }
/*     */ 
/*     */     
/*     */     public boolean remove(byte k) {
/* 403 */       int oldPos = Byte2ShortArrayMap.this.findKey(k);
/* 404 */       if (oldPos == -1) return false; 
/* 405 */       int tail = Byte2ShortArrayMap.this.size - oldPos - 1;
/* 406 */       System.arraycopy(Byte2ShortArrayMap.this.key, oldPos + 1, Byte2ShortArrayMap.this.key, oldPos, tail);
/* 407 */       System.arraycopy(Byte2ShortArrayMap.this.value, oldPos + 1, Byte2ShortArrayMap.this.value, oldPos, tail);
/* 408 */       Byte2ShortArrayMap.this.size--;
/* 409 */       return true;
/*     */     }
/*     */ 
/*     */     
/*     */     public ByteIterator iterator() {
/* 414 */       return new ByteIterator() {
/* 415 */           int pos = 0;
/*     */ 
/*     */           
/*     */           public boolean hasNext() {
/* 419 */             return (this.pos < Byte2ShortArrayMap.this.size);
/*     */           }
/*     */ 
/*     */ 
/*     */           
/*     */           public byte nextByte() {
/* 425 */             if (!hasNext()) throw new NoSuchElementException(); 
/* 426 */             return Byte2ShortArrayMap.this.key[this.pos++];
/*     */           }
/*     */ 
/*     */           
/*     */           public void remove() {
/* 431 */             if (this.pos == 0) throw new IllegalStateException(); 
/* 432 */             int tail = Byte2ShortArrayMap.this.size - this.pos;
/* 433 */             System.arraycopy(Byte2ShortArrayMap.this.key, this.pos, Byte2ShortArrayMap.this.key, this.pos - 1, tail);
/* 434 */             System.arraycopy(Byte2ShortArrayMap.this.value, this.pos, Byte2ShortArrayMap.this.value, this.pos - 1, tail);
/* 435 */             Byte2ShortArrayMap.this.size--;
/* 436 */             this.pos--;
/*     */           }
/*     */ 
/*     */ 
/*     */ 
/*     */           
/*     */           public void forEachRemaining(ByteConsumer action) {
/* 443 */             int max = Byte2ShortArrayMap.this.size;
/* 444 */             while (this.pos < max)
/* 445 */               action.accept(Byte2ShortArrayMap.this.key[this.pos++]); 
/*     */           }
/*     */         };
/*     */     }
/*     */     
/*     */     final class KeySetSpliterator
/*     */       extends ByteSpliterators.EarlyBindingSizeIndexBasedSpliterator
/*     */       implements ByteSpliterator {
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
/*     */       protected final byte get(int location) {
/* 465 */         return Byte2ShortArrayMap.this.key[location];
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
/*     */       public void forEachRemaining(ByteConsumer action) {
/* 477 */         int max = Byte2ShortArrayMap.this.size;
/* 478 */         while (this.pos < max) {
/* 479 */           action.accept(Byte2ShortArrayMap.this.key[this.pos++]);
/*     */         }
/*     */       }
/*     */     }
/*     */ 
/*     */     
/*     */     public ByteSpliterator spliterator() {
/* 486 */       return new KeySetSpliterator(0, Byte2ShortArrayMap.this.size);
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public void forEach(ByteConsumer action) {
/* 493 */       for (int i = 0, max = Byte2ShortArrayMap.this.size; i < max; i++) {
/* 494 */         action.accept(Byte2ShortArrayMap.this.key[i]);
/*     */       }
/*     */     }
/*     */ 
/*     */     
/*     */     public int size() {
/* 500 */       return Byte2ShortArrayMap.this.size;
/*     */     }
/*     */ 
/*     */     
/*     */     public void clear() {
/* 505 */       Byte2ShortArrayMap.this.clear();
/*     */     } }
/*     */ 
/*     */ 
/*     */   
/*     */   public ByteSet keySet() {
/* 511 */     if (this.keys == null) this.keys = new KeySet(); 
/* 512 */     return this.keys;
/*     */   }
/*     */   
/*     */   private final class ValuesCollection extends AbstractShortCollection { private ValuesCollection() {}
/*     */     
/*     */     public boolean contains(short v) {
/* 518 */       return Byte2ShortArrayMap.this.containsValue(v);
/*     */     }
/*     */ 
/*     */     
/*     */     public ShortIterator iterator() {
/* 523 */       return new ShortIterator() {
/* 524 */           int pos = 0;
/*     */ 
/*     */           
/*     */           public boolean hasNext() {
/* 528 */             return (this.pos < Byte2ShortArrayMap.this.size);
/*     */           }
/*     */ 
/*     */ 
/*     */           
/*     */           public short nextShort() {
/* 534 */             if (!hasNext()) throw new NoSuchElementException(); 
/* 535 */             return Byte2ShortArrayMap.this.value[this.pos++];
/*     */           }
/*     */ 
/*     */           
/*     */           public void remove() {
/* 540 */             if (this.pos == 0) throw new IllegalStateException(); 
/* 541 */             int tail = Byte2ShortArrayMap.this.size - this.pos;
/* 542 */             System.arraycopy(Byte2ShortArrayMap.this.key, this.pos, Byte2ShortArrayMap.this.key, this.pos - 1, tail);
/* 543 */             System.arraycopy(Byte2ShortArrayMap.this.value, this.pos, Byte2ShortArrayMap.this.value, this.pos - 1, tail);
/* 544 */             Byte2ShortArrayMap.this.size--;
/* 545 */             this.pos--;
/*     */           }
/*     */ 
/*     */ 
/*     */ 
/*     */           
/*     */           public void forEachRemaining(ShortConsumer action) {
/* 552 */             int max = Byte2ShortArrayMap.this.size;
/* 553 */             while (this.pos < max)
/* 554 */               action.accept(Byte2ShortArrayMap.this.value[this.pos++]); 
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
/* 574 */         return Byte2ShortArrayMap.this.value[location];
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
/* 586 */         int max = Byte2ShortArrayMap.this.size;
/* 587 */         while (this.pos < max) {
/* 588 */           action.accept(Byte2ShortArrayMap.this.value[this.pos++]);
/*     */         }
/*     */       }
/*     */     }
/*     */ 
/*     */     
/*     */     public ShortSpliterator spliterator() {
/* 595 */       return new ValuesSpliterator(0, Byte2ShortArrayMap.this.size);
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public void forEach(ShortConsumer action) {
/* 602 */       for (int i = 0, max = Byte2ShortArrayMap.this.size; i < max; i++) {
/* 603 */         action.accept(Byte2ShortArrayMap.this.value[i]);
/*     */       }
/*     */     }
/*     */ 
/*     */     
/*     */     public int size() {
/* 609 */       return Byte2ShortArrayMap.this.size;
/*     */     }
/*     */ 
/*     */     
/*     */     public void clear() {
/* 614 */       Byte2ShortArrayMap.this.clear();
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
/*     */   public Byte2ShortArrayMap clone() {
/*     */     Byte2ShortArrayMap c;
/*     */     try {
/* 638 */       c = (Byte2ShortArrayMap)super.clone();
/* 639 */     } catch (CloneNotSupportedException cantHappen) {
/* 640 */       throw new InternalError();
/*     */     } 
/* 642 */     c.key = (byte[])this.key.clone();
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
/* 653 */       s.writeByte(this.key[i]);
/* 654 */       s.writeShort(this.value[i]);
/*     */     } 
/*     */   }
/*     */   
/*     */   private void readObject(ObjectInputStream s) throws IOException, ClassNotFoundException {
/* 659 */     s.defaultReadObject();
/* 660 */     this.key = new byte[this.size];
/* 661 */     this.value = new short[this.size];
/* 662 */     for (int i = 0; i < this.size; i++) {
/* 663 */       this.key[i] = s.readByte();
/* 664 */       this.value[i] = s.readShort();
/*     */     } 
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\i\\unimi\dsi\fastutil\bytes\Byte2ShortArrayMap.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */