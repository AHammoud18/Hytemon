/*     */ package it.unimi.dsi.fastutil.chars;
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
/*     */ public class Char2ByteArrayMap
/*     */   extends AbstractChar2ByteMap
/*     */   implements Serializable, Cloneable
/*     */ {
/*     */   private static final long serialVersionUID = 1L;
/*     */   protected transient char[] key;
/*     */   protected transient byte[] value;
/*     */   protected int size;
/*     */   protected transient Char2ByteMap.FastEntrySet entries;
/*     */   protected transient CharSet keys;
/*     */   protected transient ByteCollection values;
/*     */   
/*     */   public Char2ByteArrayMap(char[] key, byte[] value) {
/*  64 */     this.key = key;
/*  65 */     this.value = value;
/*  66 */     this.size = key.length;
/*  67 */     if (key.length != value.length) throw new IllegalArgumentException("Keys and values have different lengths (" + key.length + ", " + value.length + ")");
/*     */   
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public Char2ByteArrayMap() {
/*  74 */     this.key = CharArrays.EMPTY_ARRAY;
/*  75 */     this.value = ByteArrays.EMPTY_ARRAY;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Char2ByteArrayMap(int capacity) {
/*  84 */     this.key = new char[capacity];
/*  85 */     this.value = new byte[capacity];
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Char2ByteArrayMap(Char2ByteMap m) {
/*  94 */     this(m.size());
/*  95 */     int i = 0;
/*  96 */     for (ObjectIterator<Char2ByteMap.Entry> objectIterator = m.char2ByteEntrySet().iterator(); objectIterator.hasNext(); ) { Char2ByteMap.Entry e = objectIterator.next();
/*  97 */       this.key[i] = e.getCharKey();
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
/*     */   public Char2ByteArrayMap(Map<? extends Character, ? extends Byte> m) {
/* 110 */     this(m.size());
/* 111 */     int i = 0;
/* 112 */     for (Map.Entry<? extends Character, ? extends Byte> e : m.entrySet()) {
/* 113 */       this.key[i] = ((Character)e.getKey()).charValue();
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
/*     */   public Char2ByteArrayMap(char[] key, byte[] value, int size) {
/* 133 */     this.key = key;
/* 134 */     this.value = value;
/* 135 */     this.size = size;
/* 136 */     if (key.length != value.length) throw new IllegalArgumentException("Keys and values have different lengths (" + key.length + ", " + value.length + ")"); 
/* 137 */     if (size > key.length) throw new IllegalArgumentException("The provided size (" + size + ") is larger than or equal to the backing-arrays size (" + key.length + ")"); 
/*     */   }
/*     */   
/*     */   private final class EntrySet
/*     */     extends AbstractObjectSet<Char2ByteMap.Entry> implements Char2ByteMap.FastEntrySet {
/*     */     private EntrySet() {}
/*     */     
/*     */     public ObjectIterator<Char2ByteMap.Entry> iterator() {
/* 145 */       return new ObjectIterator<Char2ByteMap.Entry>() {
/* 146 */           int curr = -1; int next = 0;
/*     */ 
/*     */           
/*     */           public boolean hasNext() {
/* 150 */             return (this.next < Char2ByteArrayMap.this.size);
/*     */           }
/*     */ 
/*     */ 
/*     */           
/*     */           public Char2ByteMap.Entry next() {
/* 156 */             if (!hasNext()) throw new NoSuchElementException(); 
/* 157 */             return new AbstractChar2ByteMap.BasicEntry(Char2ByteArrayMap.this.key[this.curr = this.next], Char2ByteArrayMap.this.value[this.next++]);
/*     */           }
/*     */ 
/*     */           
/*     */           public void remove() {
/* 162 */             if (this.curr == -1) throw new IllegalStateException(); 
/* 163 */             this.curr = -1;
/* 164 */             int tail = Char2ByteArrayMap.this.size-- - this.next--;
/* 165 */             System.arraycopy(Char2ByteArrayMap.this.key, this.next + 1, Char2ByteArrayMap.this.key, this.next, tail);
/* 166 */             System.arraycopy(Char2ByteArrayMap.this.value, this.next + 1, Char2ByteArrayMap.this.value, this.next, tail);
/*     */           }
/*     */ 
/*     */ 
/*     */ 
/*     */           
/*     */           public void forEachRemaining(Consumer<? super Char2ByteMap.Entry> action) {
/* 173 */             int max = Char2ByteArrayMap.this.size;
/* 174 */             while (this.next < max) {
/* 175 */               action.accept(new AbstractChar2ByteMap.BasicEntry(Char2ByteArrayMap.this.key[this.curr = this.next], Char2ByteArrayMap.this.value[this.next++]));
/*     */             }
/*     */           }
/*     */         };
/*     */     }
/*     */ 
/*     */     
/*     */     public ObjectIterator<Char2ByteMap.Entry> fastIterator() {
/* 183 */       return new ObjectIterator<Char2ByteMap.Entry>() {
/* 184 */           int next = 0; int curr = -1;
/* 185 */           final AbstractChar2ByteMap.BasicEntry entry = new AbstractChar2ByteMap.BasicEntry();
/*     */ 
/*     */           
/*     */           public boolean hasNext() {
/* 189 */             return (this.next < Char2ByteArrayMap.this.size);
/*     */           }
/*     */ 
/*     */ 
/*     */           
/*     */           public Char2ByteMap.Entry next() {
/* 195 */             if (!hasNext()) throw new NoSuchElementException(); 
/* 196 */             this.entry.key = Char2ByteArrayMap.this.key[this.curr = this.next];
/* 197 */             this.entry.value = Char2ByteArrayMap.this.value[this.next++];
/* 198 */             return this.entry;
/*     */           }
/*     */ 
/*     */           
/*     */           public void remove() {
/* 203 */             if (this.curr == -1) throw new IllegalStateException(); 
/* 204 */             this.curr = -1;
/* 205 */             int tail = Char2ByteArrayMap.this.size-- - this.next--;
/* 206 */             System.arraycopy(Char2ByteArrayMap.this.key, this.next + 1, Char2ByteArrayMap.this.key, this.next, tail);
/* 207 */             System.arraycopy(Char2ByteArrayMap.this.value, this.next + 1, Char2ByteArrayMap.this.value, this.next, tail);
/*     */           }
/*     */ 
/*     */ 
/*     */ 
/*     */           
/*     */           public void forEachRemaining(Consumer<? super Char2ByteMap.Entry> action) {
/* 214 */             int max = Char2ByteArrayMap.this.size;
/* 215 */             while (this.next < max) {
/* 216 */               this.entry.key = Char2ByteArrayMap.this.key[this.curr = this.next];
/* 217 */               this.entry.value = Char2ByteArrayMap.this.value[this.next++];
/* 218 */               action.accept(this.entry);
/*     */             } 
/*     */           }
/*     */         };
/*     */     }
/*     */     
/*     */     final class EntrySetSpliterator
/*     */       extends ObjectSpliterators.EarlyBindingSizeIndexBasedSpliterator<Char2ByteMap.Entry>
/*     */       implements ObjectSpliterator<Char2ByteMap.Entry> {
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
/*     */       protected final Char2ByteMap.Entry get(int location) {
/* 239 */         return new AbstractChar2ByteMap.BasicEntry(Char2ByteArrayMap.this.key[location], Char2ByteArrayMap.this.value[location]);
/*     */       }
/*     */ 
/*     */       
/*     */       protected final EntrySetSpliterator makeForSplit(int pos, int maxPos) {
/* 244 */         return new EntrySetSpliterator(pos, maxPos);
/*     */       }
/*     */     }
/*     */ 
/*     */     
/*     */     public ObjectSpliterator<Char2ByteMap.Entry> spliterator() {
/* 250 */       return new EntrySetSpliterator(0, Char2ByteArrayMap.this.size);
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public void forEach(Consumer<? super Char2ByteMap.Entry> action) {
/* 258 */       for (int i = 0, max = Char2ByteArrayMap.this.size; i < max; i++) {
/* 259 */         action.accept(new AbstractChar2ByteMap.BasicEntry(Char2ByteArrayMap.this.key[i], Char2ByteArrayMap.this.value[i]));
/*     */       }
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public void fastForEach(Consumer<? super Char2ByteMap.Entry> action) {
/* 267 */       AbstractChar2ByteMap.BasicEntry entry = new AbstractChar2ByteMap.BasicEntry();
/*     */       
/* 269 */       for (int i = 0, max = Char2ByteArrayMap.this.size; i < max; i++) {
/* 270 */         entry.key = Char2ByteArrayMap.this.key[i];
/* 271 */         entry.value = Char2ByteArrayMap.this.value[i];
/* 272 */         action.accept(entry);
/*     */       } 
/*     */     }
/*     */ 
/*     */     
/*     */     public int size() {
/* 278 */       return Char2ByteArrayMap.this.size;
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     public boolean contains(Object o) {
/* 284 */       if (!(o instanceof Map.Entry)) return false; 
/* 285 */       Map.Entry<?, ?> e = (Map.Entry<?, ?>)o;
/* 286 */       if (e.getKey() == null || !(e.getKey() instanceof Character)) return false; 
/* 287 */       if (e.getValue() == null || !(e.getValue() instanceof Byte)) return false; 
/* 288 */       char k = ((Character)e.getKey()).charValue();
/* 289 */       return (Char2ByteArrayMap.this.containsKey(k) && Char2ByteArrayMap.this.get(k) == ((Byte)e.getValue()).byteValue());
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     public boolean remove(Object o) {
/* 295 */       if (!(o instanceof Map.Entry)) return false; 
/* 296 */       Map.Entry<?, ?> e = (Map.Entry<?, ?>)o;
/* 297 */       if (e.getKey() == null || !(e.getKey() instanceof Character)) return false; 
/* 298 */       if (e.getValue() == null || !(e.getValue() instanceof Byte)) return false; 
/* 299 */       char k = ((Character)e.getKey()).charValue();
/* 300 */       byte v = ((Byte)e.getValue()).byteValue();
/* 301 */       int oldPos = Char2ByteArrayMap.this.findKey(k);
/* 302 */       if (oldPos == -1 || v != Char2ByteArrayMap.this.value[oldPos]) return false; 
/* 303 */       int tail = Char2ByteArrayMap.this.size - oldPos - 1;
/* 304 */       System.arraycopy(Char2ByteArrayMap.this.key, oldPos + 1, Char2ByteArrayMap.this.key, oldPos, tail);
/* 305 */       System.arraycopy(Char2ByteArrayMap.this.value, oldPos + 1, Char2ByteArrayMap.this.value, oldPos, tail);
/* 306 */       Char2ByteArrayMap.this.size--;
/* 307 */       return true;
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   public Char2ByteMap.FastEntrySet char2ByteEntrySet() {
/* 313 */     if (this.entries == null) this.entries = new EntrySet(); 
/* 314 */     return this.entries;
/*     */   }
/*     */   
/*     */   private int findKey(char k) {
/* 318 */     char[] key = this.key;
/* 319 */     for (int i = this.size; i-- != 0;) { if (key[i] == k) return i;  }
/* 320 */      return -1;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public byte get(char k) {
/* 326 */     char[] key = this.key;
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
/*     */   public boolean containsKey(char k) {
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
/*     */   public byte put(char k, byte v) {
/* 360 */     int oldKey = findKey(k);
/* 361 */     if (oldKey != -1) {
/* 362 */       byte oldValue = this.value[oldKey];
/* 363 */       this.value[oldKey] = v;
/* 364 */       return oldValue;
/*     */     } 
/* 366 */     if (this.size == this.key.length) {
/* 367 */       char[] newKey = new char[(this.size == 0) ? 2 : (this.size * 2)];
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
/*     */   public byte remove(char k) {
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
/*     */   private final class KeySet extends AbstractCharSet { private KeySet() {}
/*     */     
/*     */     public boolean contains(char k) {
/* 398 */       return (Char2ByteArrayMap.this.findKey(k) != -1);
/*     */     }
/*     */ 
/*     */     
/*     */     public boolean remove(char k) {
/* 403 */       int oldPos = Char2ByteArrayMap.this.findKey(k);
/* 404 */       if (oldPos == -1) return false; 
/* 405 */       int tail = Char2ByteArrayMap.this.size - oldPos - 1;
/* 406 */       System.arraycopy(Char2ByteArrayMap.this.key, oldPos + 1, Char2ByteArrayMap.this.key, oldPos, tail);
/* 407 */       System.arraycopy(Char2ByteArrayMap.this.value, oldPos + 1, Char2ByteArrayMap.this.value, oldPos, tail);
/* 408 */       Char2ByteArrayMap.this.size--;
/* 409 */       return true;
/*     */     }
/*     */ 
/*     */     
/*     */     public CharIterator iterator() {
/* 414 */       return new CharIterator() {
/* 415 */           int pos = 0;
/*     */ 
/*     */           
/*     */           public boolean hasNext() {
/* 419 */             return (this.pos < Char2ByteArrayMap.this.size);
/*     */           }
/*     */ 
/*     */ 
/*     */           
/*     */           public char nextChar() {
/* 425 */             if (!hasNext()) throw new NoSuchElementException(); 
/* 426 */             return Char2ByteArrayMap.this.key[this.pos++];
/*     */           }
/*     */ 
/*     */           
/*     */           public void remove() {
/* 431 */             if (this.pos == 0) throw new IllegalStateException(); 
/* 432 */             int tail = Char2ByteArrayMap.this.size - this.pos;
/* 433 */             System.arraycopy(Char2ByteArrayMap.this.key, this.pos, Char2ByteArrayMap.this.key, this.pos - 1, tail);
/* 434 */             System.arraycopy(Char2ByteArrayMap.this.value, this.pos, Char2ByteArrayMap.this.value, this.pos - 1, tail);
/* 435 */             Char2ByteArrayMap.this.size--;
/* 436 */             this.pos--;
/*     */           }
/*     */ 
/*     */ 
/*     */ 
/*     */           
/*     */           public void forEachRemaining(CharConsumer action) {
/* 443 */             int max = Char2ByteArrayMap.this.size;
/* 444 */             while (this.pos < max)
/* 445 */               action.accept(Char2ByteArrayMap.this.key[this.pos++]); 
/*     */           }
/*     */         };
/*     */     }
/*     */     
/*     */     final class KeySetSpliterator
/*     */       extends CharSpliterators.EarlyBindingSizeIndexBasedSpliterator
/*     */       implements CharSpliterator {
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
/*     */       protected final char get(int location) {
/* 465 */         return Char2ByteArrayMap.this.key[location];
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
/*     */       public void forEachRemaining(CharConsumer action) {
/* 477 */         int max = Char2ByteArrayMap.this.size;
/* 478 */         while (this.pos < max) {
/* 479 */           action.accept(Char2ByteArrayMap.this.key[this.pos++]);
/*     */         }
/*     */       }
/*     */     }
/*     */ 
/*     */     
/*     */     public CharSpliterator spliterator() {
/* 486 */       return new KeySetSpliterator(0, Char2ByteArrayMap.this.size);
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public void forEach(CharConsumer action) {
/* 493 */       for (int i = 0, max = Char2ByteArrayMap.this.size; i < max; i++) {
/* 494 */         action.accept(Char2ByteArrayMap.this.key[i]);
/*     */       }
/*     */     }
/*     */ 
/*     */     
/*     */     public int size() {
/* 500 */       return Char2ByteArrayMap.this.size;
/*     */     }
/*     */ 
/*     */     
/*     */     public void clear() {
/* 505 */       Char2ByteArrayMap.this.clear();
/*     */     } }
/*     */ 
/*     */ 
/*     */   
/*     */   public CharSet keySet() {
/* 511 */     if (this.keys == null) this.keys = new KeySet(); 
/* 512 */     return this.keys;
/*     */   }
/*     */   
/*     */   private final class ValuesCollection extends AbstractByteCollection { private ValuesCollection() {}
/*     */     
/*     */     public boolean contains(byte v) {
/* 518 */       return Char2ByteArrayMap.this.containsValue(v);
/*     */     }
/*     */ 
/*     */     
/*     */     public ByteIterator iterator() {
/* 523 */       return new ByteIterator() {
/* 524 */           int pos = 0;
/*     */ 
/*     */           
/*     */           public boolean hasNext() {
/* 528 */             return (this.pos < Char2ByteArrayMap.this.size);
/*     */           }
/*     */ 
/*     */ 
/*     */           
/*     */           public byte nextByte() {
/* 534 */             if (!hasNext()) throw new NoSuchElementException(); 
/* 535 */             return Char2ByteArrayMap.this.value[this.pos++];
/*     */           }
/*     */ 
/*     */           
/*     */           public void remove() {
/* 540 */             if (this.pos == 0) throw new IllegalStateException(); 
/* 541 */             int tail = Char2ByteArrayMap.this.size - this.pos;
/* 542 */             System.arraycopy(Char2ByteArrayMap.this.key, this.pos, Char2ByteArrayMap.this.key, this.pos - 1, tail);
/* 543 */             System.arraycopy(Char2ByteArrayMap.this.value, this.pos, Char2ByteArrayMap.this.value, this.pos - 1, tail);
/* 544 */             Char2ByteArrayMap.this.size--;
/* 545 */             this.pos--;
/*     */           }
/*     */ 
/*     */ 
/*     */ 
/*     */           
/*     */           public void forEachRemaining(ByteConsumer action) {
/* 552 */             int max = Char2ByteArrayMap.this.size;
/* 553 */             while (this.pos < max)
/* 554 */               action.accept(Char2ByteArrayMap.this.value[this.pos++]); 
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
/* 574 */         return Char2ByteArrayMap.this.value[location];
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
/* 586 */         int max = Char2ByteArrayMap.this.size;
/* 587 */         while (this.pos < max) {
/* 588 */           action.accept(Char2ByteArrayMap.this.value[this.pos++]);
/*     */         }
/*     */       }
/*     */     }
/*     */ 
/*     */     
/*     */     public ByteSpliterator spliterator() {
/* 595 */       return new ValuesSpliterator(0, Char2ByteArrayMap.this.size);
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public void forEach(ByteConsumer action) {
/* 602 */       for (int i = 0, max = Char2ByteArrayMap.this.size; i < max; i++) {
/* 603 */         action.accept(Char2ByteArrayMap.this.value[i]);
/*     */       }
/*     */     }
/*     */ 
/*     */     
/*     */     public int size() {
/* 609 */       return Char2ByteArrayMap.this.size;
/*     */     }
/*     */ 
/*     */     
/*     */     public void clear() {
/* 614 */       Char2ByteArrayMap.this.clear();
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
/*     */   public Char2ByteArrayMap clone() {
/*     */     Char2ByteArrayMap c;
/*     */     try {
/* 638 */       c = (Char2ByteArrayMap)super.clone();
/* 639 */     } catch (CloneNotSupportedException cantHappen) {
/* 640 */       throw new InternalError();
/*     */     } 
/* 642 */     c.key = (char[])this.key.clone();
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
/* 653 */       s.writeChar(this.key[i]);
/* 654 */       s.writeByte(this.value[i]);
/*     */     } 
/*     */   }
/*     */   
/*     */   private void readObject(ObjectInputStream s) throws IOException, ClassNotFoundException {
/* 659 */     s.defaultReadObject();
/* 660 */     this.key = new char[this.size];
/* 661 */     this.value = new byte[this.size];
/* 662 */     for (int i = 0; i < this.size; i++) {
/* 663 */       this.key[i] = s.readChar();
/* 664 */       this.value[i] = s.readByte();
/*     */     } 
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\i\\unimi\dsi\fastutil\chars\Char2ByteArrayMap.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */