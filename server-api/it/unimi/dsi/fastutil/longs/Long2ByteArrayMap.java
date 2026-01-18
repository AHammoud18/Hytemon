/*     */ package it.unimi.dsi.fastutil.longs;
/*     */ 
/*     */ import it.unimi.dsi.fastutil.bytes.AbstractByteCollection;
/*     */ import it.unimi.dsi.fastutil.bytes.ByteArrays;
/*     */ import it.unimi.dsi.fastutil.bytes.ByteCollection;
/*     */ import it.unimi.dsi.fastutil.bytes.ByteConsumer;
/*     */ import it.unimi.dsi.fastutil.bytes.ByteIterator;
/*     */ import it.unimi.dsi.fastutil.bytes.ByteSpliterator;
/*     */ import it.unimi.dsi.fastutil.bytes.ByteSpliterators;
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
/*     */ public class Long2ByteArrayMap
/*     */   extends AbstractLong2ByteMap
/*     */   implements Serializable, Cloneable
/*     */ {
/*     */   private static final long serialVersionUID = 1L;
/*     */   protected transient long[] key;
/*     */   protected transient byte[] value;
/*     */   protected int size;
/*     */   protected transient Long2ByteMap.FastEntrySet entries;
/*     */   protected transient LongSet keys;
/*     */   protected transient ByteCollection values;
/*     */   
/*     */   public Long2ByteArrayMap(long[] key, byte[] value) {
/*  64 */     this.key = key;
/*  65 */     this.value = value;
/*  66 */     this.size = key.length;
/*  67 */     if (key.length != value.length) throw new IllegalArgumentException("Keys and values have different lengths (" + key.length + ", " + value.length + ")");
/*     */   
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public Long2ByteArrayMap() {
/*  74 */     this.key = LongArrays.EMPTY_ARRAY;
/*  75 */     this.value = ByteArrays.EMPTY_ARRAY;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Long2ByteArrayMap(int capacity) {
/*  84 */     this.key = new long[capacity];
/*  85 */     this.value = new byte[capacity];
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Long2ByteArrayMap(Long2ByteMap m) {
/*  94 */     this(m.size());
/*  95 */     int i = 0;
/*  96 */     for (ObjectIterator<Long2ByteMap.Entry> objectIterator = m.long2ByteEntrySet().iterator(); objectIterator.hasNext(); ) { Long2ByteMap.Entry e = objectIterator.next();
/*  97 */       this.key[i] = e.getLongKey();
/*  98 */       this.value[i] = e.getByteValue();
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
/*     */   public Long2ByteArrayMap(Map<? extends Long, ? extends Byte> m) {
/* 110 */     this(m.size());
/* 111 */     int i = 0;
/* 112 */     for (Map.Entry<? extends Long, ? extends Byte> e : m.entrySet()) {
/* 113 */       this.key[i] = ((Long)e.getKey()).longValue();
/* 114 */       this.value[i] = ((Byte)e.getValue()).byteValue();
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
/*     */   public Long2ByteArrayMap(long[] key, byte[] value, int size) {
/* 133 */     this.key = key;
/* 134 */     this.value = value;
/* 135 */     this.size = size;
/* 136 */     if (key.length != value.length) throw new IllegalArgumentException("Keys and values have different lengths (" + key.length + ", " + value.length + ")"); 
/* 137 */     if (size > key.length) throw new IllegalArgumentException("The provided size (" + size + ") is larger than or equal to the backing-arrays size (" + key.length + ")"); 
/*     */   }
/*     */   
/*     */   private final class EntrySet
/*     */     extends AbstractObjectSet<Long2ByteMap.Entry> implements Long2ByteMap.FastEntrySet {
/*     */     private EntrySet() {}
/*     */     
/*     */     public ObjectIterator<Long2ByteMap.Entry> iterator() {
/* 145 */       return new ObjectIterator<Long2ByteMap.Entry>() {
/* 146 */           int curr = -1; int next = 0;
/*     */ 
/*     */           
/*     */           public boolean hasNext() {
/* 150 */             return (this.next < Long2ByteArrayMap.this.size);
/*     */           }
/*     */ 
/*     */ 
/*     */           
/*     */           public Long2ByteMap.Entry next() {
/* 156 */             if (!hasNext()) throw new NoSuchElementException(); 
/* 157 */             return new AbstractLong2ByteMap.BasicEntry(Long2ByteArrayMap.this.key[this.curr = this.next], Long2ByteArrayMap.this.value[this.next++]);
/*     */           }
/*     */ 
/*     */           
/*     */           public void remove() {
/* 162 */             if (this.curr == -1) throw new IllegalStateException(); 
/* 163 */             this.curr = -1;
/* 164 */             int tail = Long2ByteArrayMap.this.size-- - this.next--;
/* 165 */             System.arraycopy(Long2ByteArrayMap.this.key, this.next + 1, Long2ByteArrayMap.this.key, this.next, tail);
/* 166 */             System.arraycopy(Long2ByteArrayMap.this.value, this.next + 1, Long2ByteArrayMap.this.value, this.next, tail);
/*     */           }
/*     */ 
/*     */ 
/*     */ 
/*     */           
/*     */           public void forEachRemaining(Consumer<? super Long2ByteMap.Entry> action) {
/* 173 */             int max = Long2ByteArrayMap.this.size;
/* 174 */             while (this.next < max) {
/* 175 */               action.accept(new AbstractLong2ByteMap.BasicEntry(Long2ByteArrayMap.this.key[this.curr = this.next], Long2ByteArrayMap.this.value[this.next++]));
/*     */             }
/*     */           }
/*     */         };
/*     */     }
/*     */ 
/*     */     
/*     */     public ObjectIterator<Long2ByteMap.Entry> fastIterator() {
/* 183 */       return new ObjectIterator<Long2ByteMap.Entry>() {
/* 184 */           int next = 0; int curr = -1;
/* 185 */           final AbstractLong2ByteMap.BasicEntry entry = new AbstractLong2ByteMap.BasicEntry();
/*     */ 
/*     */           
/*     */           public boolean hasNext() {
/* 189 */             return (this.next < Long2ByteArrayMap.this.size);
/*     */           }
/*     */ 
/*     */ 
/*     */           
/*     */           public Long2ByteMap.Entry next() {
/* 195 */             if (!hasNext()) throw new NoSuchElementException(); 
/* 196 */             this.entry.key = Long2ByteArrayMap.this.key[this.curr = this.next];
/* 197 */             this.entry.value = Long2ByteArrayMap.this.value[this.next++];
/* 198 */             return this.entry;
/*     */           }
/*     */ 
/*     */           
/*     */           public void remove() {
/* 203 */             if (this.curr == -1) throw new IllegalStateException(); 
/* 204 */             this.curr = -1;
/* 205 */             int tail = Long2ByteArrayMap.this.size-- - this.next--;
/* 206 */             System.arraycopy(Long2ByteArrayMap.this.key, this.next + 1, Long2ByteArrayMap.this.key, this.next, tail);
/* 207 */             System.arraycopy(Long2ByteArrayMap.this.value, this.next + 1, Long2ByteArrayMap.this.value, this.next, tail);
/*     */           }
/*     */ 
/*     */ 
/*     */ 
/*     */           
/*     */           public void forEachRemaining(Consumer<? super Long2ByteMap.Entry> action) {
/* 214 */             int max = Long2ByteArrayMap.this.size;
/* 215 */             while (this.next < max) {
/* 216 */               this.entry.key = Long2ByteArrayMap.this.key[this.curr = this.next];
/* 217 */               this.entry.value = Long2ByteArrayMap.this.value[this.next++];
/* 218 */               action.accept(this.entry);
/*     */             } 
/*     */           }
/*     */         };
/*     */     }
/*     */     
/*     */     final class EntrySetSpliterator
/*     */       extends ObjectSpliterators.EarlyBindingSizeIndexBasedSpliterator<Long2ByteMap.Entry>
/*     */       implements ObjectSpliterator<Long2ByteMap.Entry> {
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
/*     */       protected final Long2ByteMap.Entry get(int location) {
/* 239 */         return new AbstractLong2ByteMap.BasicEntry(Long2ByteArrayMap.this.key[location], Long2ByteArrayMap.this.value[location]);
/*     */       }
/*     */ 
/*     */       
/*     */       protected final EntrySetSpliterator makeForSplit(int pos, int maxPos) {
/* 244 */         return new EntrySetSpliterator(pos, maxPos);
/*     */       }
/*     */     }
/*     */ 
/*     */     
/*     */     public ObjectSpliterator<Long2ByteMap.Entry> spliterator() {
/* 250 */       return new EntrySetSpliterator(0, Long2ByteArrayMap.this.size);
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public void forEach(Consumer<? super Long2ByteMap.Entry> action) {
/* 258 */       for (int i = 0, max = Long2ByteArrayMap.this.size; i < max; i++) {
/* 259 */         action.accept(new AbstractLong2ByteMap.BasicEntry(Long2ByteArrayMap.this.key[i], Long2ByteArrayMap.this.value[i]));
/*     */       }
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public void fastForEach(Consumer<? super Long2ByteMap.Entry> action) {
/* 267 */       AbstractLong2ByteMap.BasicEntry entry = new AbstractLong2ByteMap.BasicEntry();
/*     */       
/* 269 */       for (int i = 0, max = Long2ByteArrayMap.this.size; i < max; i++) {
/* 270 */         entry.key = Long2ByteArrayMap.this.key[i];
/* 271 */         entry.value = Long2ByteArrayMap.this.value[i];
/* 272 */         action.accept(entry);
/*     */       } 
/*     */     }
/*     */ 
/*     */     
/*     */     public int size() {
/* 278 */       return Long2ByteArrayMap.this.size;
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     public boolean contains(Object o) {
/* 284 */       if (!(o instanceof Map.Entry)) return false; 
/* 285 */       Map.Entry<?, ?> e = (Map.Entry<?, ?>)o;
/* 286 */       if (e.getKey() == null || !(e.getKey() instanceof Long)) return false; 
/* 287 */       if (e.getValue() == null || !(e.getValue() instanceof Byte)) return false; 
/* 288 */       long k = ((Long)e.getKey()).longValue();
/* 289 */       return (Long2ByteArrayMap.this.containsKey(k) && Long2ByteArrayMap.this.get(k) == ((Byte)e.getValue()).byteValue());
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     public boolean remove(Object o) {
/* 295 */       if (!(o instanceof Map.Entry)) return false; 
/* 296 */       Map.Entry<?, ?> e = (Map.Entry<?, ?>)o;
/* 297 */       if (e.getKey() == null || !(e.getKey() instanceof Long)) return false; 
/* 298 */       if (e.getValue() == null || !(e.getValue() instanceof Byte)) return false; 
/* 299 */       long k = ((Long)e.getKey()).longValue();
/* 300 */       byte v = ((Byte)e.getValue()).byteValue();
/* 301 */       int oldPos = Long2ByteArrayMap.this.findKey(k);
/* 302 */       if (oldPos == -1 || v != Long2ByteArrayMap.this.value[oldPos]) return false; 
/* 303 */       int tail = Long2ByteArrayMap.this.size - oldPos - 1;
/* 304 */       System.arraycopy(Long2ByteArrayMap.this.key, oldPos + 1, Long2ByteArrayMap.this.key, oldPos, tail);
/* 305 */       System.arraycopy(Long2ByteArrayMap.this.value, oldPos + 1, Long2ByteArrayMap.this.value, oldPos, tail);
/* 306 */       Long2ByteArrayMap.this.size--;
/* 307 */       return true;
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   public Long2ByteMap.FastEntrySet long2ByteEntrySet() {
/* 313 */     if (this.entries == null) this.entries = new EntrySet(); 
/* 314 */     return this.entries;
/*     */   }
/*     */   
/*     */   private int findKey(long k) {
/* 318 */     long[] key = this.key;
/* 319 */     for (int i = this.size; i-- != 0;) { if (key[i] == k) return i;  }
/* 320 */      return -1;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public byte get(long k) {
/* 326 */     long[] key = this.key;
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
/*     */   public boolean containsKey(long k) {
/* 343 */     return (findKey(k) != -1);
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean containsValue(byte v) {
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
/*     */   public byte put(long k, byte v) {
/* 360 */     int oldKey = findKey(k);
/* 361 */     if (oldKey != -1) {
/* 362 */       byte oldValue = this.value[oldKey];
/* 363 */       this.value[oldKey] = v;
/* 364 */       return oldValue;
/*     */     } 
/* 366 */     if (this.size == this.key.length) {
/* 367 */       long[] newKey = new long[(this.size == 0) ? 2 : (this.size * 2)];
/* 368 */       byte[] newValue = new byte[(this.size == 0) ? 2 : (this.size * 2)];
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
/*     */   public byte remove(long k) {
/* 385 */     int oldPos = findKey(k);
/* 386 */     if (oldPos == -1) return this.defRetValue; 
/* 387 */     byte oldValue = this.value[oldPos];
/* 388 */     int tail = this.size - oldPos - 1;
/* 389 */     System.arraycopy(this.key, oldPos + 1, this.key, oldPos, tail);
/* 390 */     System.arraycopy(this.value, oldPos + 1, this.value, oldPos, tail);
/* 391 */     this.size--;
/* 392 */     return oldValue;
/*     */   }
/*     */   
/*     */   private final class KeySet extends AbstractLongSet { private KeySet() {}
/*     */     
/*     */     public boolean contains(long k) {
/* 398 */       return (Long2ByteArrayMap.this.findKey(k) != -1);
/*     */     }
/*     */ 
/*     */     
/*     */     public boolean remove(long k) {
/* 403 */       int oldPos = Long2ByteArrayMap.this.findKey(k);
/* 404 */       if (oldPos == -1) return false; 
/* 405 */       int tail = Long2ByteArrayMap.this.size - oldPos - 1;
/* 406 */       System.arraycopy(Long2ByteArrayMap.this.key, oldPos + 1, Long2ByteArrayMap.this.key, oldPos, tail);
/* 407 */       System.arraycopy(Long2ByteArrayMap.this.value, oldPos + 1, Long2ByteArrayMap.this.value, oldPos, tail);
/* 408 */       Long2ByteArrayMap.this.size--;
/* 409 */       return true;
/*     */     }
/*     */ 
/*     */     
/*     */     public LongIterator iterator() {
/* 414 */       return new LongIterator() {
/* 415 */           int pos = 0;
/*     */ 
/*     */           
/*     */           public boolean hasNext() {
/* 419 */             return (this.pos < Long2ByteArrayMap.this.size);
/*     */           }
/*     */ 
/*     */ 
/*     */           
/*     */           public long nextLong() {
/* 425 */             if (!hasNext()) throw new NoSuchElementException(); 
/* 426 */             return Long2ByteArrayMap.this.key[this.pos++];
/*     */           }
/*     */ 
/*     */           
/*     */           public void remove() {
/* 431 */             if (this.pos == 0) throw new IllegalStateException(); 
/* 432 */             int tail = Long2ByteArrayMap.this.size - this.pos;
/* 433 */             System.arraycopy(Long2ByteArrayMap.this.key, this.pos, Long2ByteArrayMap.this.key, this.pos - 1, tail);
/* 434 */             System.arraycopy(Long2ByteArrayMap.this.value, this.pos, Long2ByteArrayMap.this.value, this.pos - 1, tail);
/* 435 */             Long2ByteArrayMap.this.size--;
/* 436 */             this.pos--;
/*     */           }
/*     */ 
/*     */ 
/*     */ 
/*     */           
/*     */           public void forEachRemaining(LongConsumer action) {
/* 443 */             int max = Long2ByteArrayMap.this.size;
/* 444 */             while (this.pos < max)
/* 445 */               action.accept(Long2ByteArrayMap.this.key[this.pos++]); 
/*     */           }
/*     */         };
/*     */     }
/*     */     
/*     */     final class KeySetSpliterator
/*     */       extends LongSpliterators.EarlyBindingSizeIndexBasedSpliterator
/*     */       implements LongSpliterator {
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
/*     */       protected final long get(int location) {
/* 465 */         return Long2ByteArrayMap.this.key[location];
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
/*     */       public void forEachRemaining(LongConsumer action) {
/* 477 */         int max = Long2ByteArrayMap.this.size;
/* 478 */         while (this.pos < max) {
/* 479 */           action.accept(Long2ByteArrayMap.this.key[this.pos++]);
/*     */         }
/*     */       }
/*     */     }
/*     */ 
/*     */     
/*     */     public LongSpliterator spliterator() {
/* 486 */       return new KeySetSpliterator(0, Long2ByteArrayMap.this.size);
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public void forEach(LongConsumer action) {
/* 493 */       for (int i = 0, max = Long2ByteArrayMap.this.size; i < max; i++) {
/* 494 */         action.accept(Long2ByteArrayMap.this.key[i]);
/*     */       }
/*     */     }
/*     */ 
/*     */     
/*     */     public int size() {
/* 500 */       return Long2ByteArrayMap.this.size;
/*     */     }
/*     */ 
/*     */     
/*     */     public void clear() {
/* 505 */       Long2ByteArrayMap.this.clear();
/*     */     } }
/*     */ 
/*     */ 
/*     */   
/*     */   public LongSet keySet() {
/* 511 */     if (this.keys == null) this.keys = new KeySet(); 
/* 512 */     return this.keys;
/*     */   }
/*     */   
/*     */   private final class ValuesCollection extends AbstractByteCollection { private ValuesCollection() {}
/*     */     
/*     */     public boolean contains(byte v) {
/* 518 */       return Long2ByteArrayMap.this.containsValue(v);
/*     */     }
/*     */ 
/*     */     
/*     */     public ByteIterator iterator() {
/* 523 */       return new ByteIterator() {
/* 524 */           int pos = 0;
/*     */ 
/*     */           
/*     */           public boolean hasNext() {
/* 528 */             return (this.pos < Long2ByteArrayMap.this.size);
/*     */           }
/*     */ 
/*     */ 
/*     */           
/*     */           public byte nextByte() {
/* 534 */             if (!hasNext()) throw new NoSuchElementException(); 
/* 535 */             return Long2ByteArrayMap.this.value[this.pos++];
/*     */           }
/*     */ 
/*     */           
/*     */           public void remove() {
/* 540 */             if (this.pos == 0) throw new IllegalStateException(); 
/* 541 */             int tail = Long2ByteArrayMap.this.size - this.pos;
/* 542 */             System.arraycopy(Long2ByteArrayMap.this.key, this.pos, Long2ByteArrayMap.this.key, this.pos - 1, tail);
/* 543 */             System.arraycopy(Long2ByteArrayMap.this.value, this.pos, Long2ByteArrayMap.this.value, this.pos - 1, tail);
/* 544 */             Long2ByteArrayMap.this.size--;
/* 545 */             this.pos--;
/*     */           }
/*     */ 
/*     */ 
/*     */ 
/*     */           
/*     */           public void forEachRemaining(ByteConsumer action) {
/* 552 */             int max = Long2ByteArrayMap.this.size;
/* 553 */             while (this.pos < max)
/* 554 */               action.accept(Long2ByteArrayMap.this.value[this.pos++]); 
/*     */           }
/*     */         };
/*     */     }
/*     */     
/*     */     final class ValuesSpliterator
/*     */       extends ByteSpliterators.EarlyBindingSizeIndexBasedSpliterator
/*     */       implements ByteSpliterator {
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
/*     */       protected final byte get(int location) {
/* 574 */         return Long2ByteArrayMap.this.value[location];
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
/*     */       public void forEachRemaining(ByteConsumer action) {
/* 586 */         int max = Long2ByteArrayMap.this.size;
/* 587 */         while (this.pos < max) {
/* 588 */           action.accept(Long2ByteArrayMap.this.value[this.pos++]);
/*     */         }
/*     */       }
/*     */     }
/*     */ 
/*     */     
/*     */     public ByteSpliterator spliterator() {
/* 595 */       return new ValuesSpliterator(0, Long2ByteArrayMap.this.size);
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public void forEach(ByteConsumer action) {
/* 602 */       for (int i = 0, max = Long2ByteArrayMap.this.size; i < max; i++) {
/* 603 */         action.accept(Long2ByteArrayMap.this.value[i]);
/*     */       }
/*     */     }
/*     */ 
/*     */     
/*     */     public int size() {
/* 609 */       return Long2ByteArrayMap.this.size;
/*     */     }
/*     */ 
/*     */     
/*     */     public void clear() {
/* 614 */       Long2ByteArrayMap.this.clear();
/*     */     } }
/*     */ 
/*     */ 
/*     */   
/*     */   public ByteCollection values() {
/* 620 */     if (this.values == null) this.values = (ByteCollection)new ValuesCollection(); 
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
/*     */   public Long2ByteArrayMap clone() {
/*     */     Long2ByteArrayMap c;
/*     */     try {
/* 638 */       c = (Long2ByteArrayMap)super.clone();
/* 639 */     } catch (CloneNotSupportedException cantHappen) {
/* 640 */       throw new InternalError();
/*     */     } 
/* 642 */     c.key = (long[])this.key.clone();
/* 643 */     c.value = (byte[])this.value.clone();
/* 644 */     c.entries = null;
/* 645 */     c.keys = null;
/* 646 */     c.values = null;
/* 647 */     return c;
/*     */   }
/*     */   
/*     */   private void writeObject(ObjectOutputStream s) throws IOException {
/* 651 */     s.defaultWriteObject();
/* 652 */     for (int i = 0, max = this.size; i < max; i++) {
/* 653 */       s.writeLong(this.key[i]);
/* 654 */       s.writeByte(this.value[i]);
/*     */     } 
/*     */   }
/*     */   
/*     */   private void readObject(ObjectInputStream s) throws IOException, ClassNotFoundException {
/* 659 */     s.defaultReadObject();
/* 660 */     this.key = new long[this.size];
/* 661 */     this.value = new byte[this.size];
/* 662 */     for (int i = 0; i < this.size; i++) {
/* 663 */       this.key[i] = s.readLong();
/* 664 */       this.value[i] = s.readByte();
/*     */     } 
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\i\\unimi\dsi\fastutil\longs\Long2ByteArrayMap.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */