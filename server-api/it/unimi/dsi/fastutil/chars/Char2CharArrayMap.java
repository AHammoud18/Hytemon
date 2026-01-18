/*     */ package it.unimi.dsi.fastutil.chars;
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
/*     */ public class Char2CharArrayMap
/*     */   extends AbstractChar2CharMap
/*     */   implements Serializable, Cloneable
/*     */ {
/*     */   private static final long serialVersionUID = 1L;
/*     */   protected transient char[] key;
/*     */   protected transient char[] value;
/*     */   protected int size;
/*     */   protected transient Char2CharMap.FastEntrySet entries;
/*     */   protected transient CharSet keys;
/*     */   protected transient CharCollection values;
/*     */   
/*     */   public Char2CharArrayMap(char[] key, char[] value) {
/*  60 */     this.key = key;
/*  61 */     this.value = value;
/*  62 */     this.size = key.length;
/*  63 */     if (key.length != value.length) throw new IllegalArgumentException("Keys and values have different lengths (" + key.length + ", " + value.length + ")");
/*     */   
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public Char2CharArrayMap() {
/*  70 */     this.key = CharArrays.EMPTY_ARRAY;
/*  71 */     this.value = CharArrays.EMPTY_ARRAY;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Char2CharArrayMap(int capacity) {
/*  80 */     this.key = new char[capacity];
/*  81 */     this.value = new char[capacity];
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Char2CharArrayMap(Char2CharMap m) {
/*  90 */     this(m.size());
/*  91 */     int i = 0;
/*  92 */     for (ObjectIterator<Char2CharMap.Entry> objectIterator = m.char2CharEntrySet().iterator(); objectIterator.hasNext(); ) { Char2CharMap.Entry e = objectIterator.next();
/*  93 */       this.key[i] = e.getCharKey();
/*  94 */       this.value[i] = e.getCharValue();
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
/*     */   public Char2CharArrayMap(Map<? extends Character, ? extends Character> m) {
/* 106 */     this(m.size());
/* 107 */     int i = 0;
/* 108 */     for (Map.Entry<? extends Character, ? extends Character> e : m.entrySet()) {
/* 109 */       this.key[i] = ((Character)e.getKey()).charValue();
/* 110 */       this.value[i] = ((Character)e.getValue()).charValue();
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
/*     */   public Char2CharArrayMap(char[] key, char[] value, int size) {
/* 129 */     this.key = key;
/* 130 */     this.value = value;
/* 131 */     this.size = size;
/* 132 */     if (key.length != value.length) throw new IllegalArgumentException("Keys and values have different lengths (" + key.length + ", " + value.length + ")"); 
/* 133 */     if (size > key.length) throw new IllegalArgumentException("The provided size (" + size + ") is larger than or equal to the backing-arrays size (" + key.length + ")"); 
/*     */   }
/*     */   
/*     */   private final class EntrySet
/*     */     extends AbstractObjectSet<Char2CharMap.Entry> implements Char2CharMap.FastEntrySet {
/*     */     private EntrySet() {}
/*     */     
/*     */     public ObjectIterator<Char2CharMap.Entry> iterator() {
/* 141 */       return new ObjectIterator<Char2CharMap.Entry>() {
/* 142 */           int curr = -1; int next = 0;
/*     */ 
/*     */           
/*     */           public boolean hasNext() {
/* 146 */             return (this.next < Char2CharArrayMap.this.size);
/*     */           }
/*     */ 
/*     */ 
/*     */           
/*     */           public Char2CharMap.Entry next() {
/* 152 */             if (!hasNext()) throw new NoSuchElementException(); 
/* 153 */             return new AbstractChar2CharMap.BasicEntry(Char2CharArrayMap.this.key[this.curr = this.next], Char2CharArrayMap.this.value[this.next++]);
/*     */           }
/*     */ 
/*     */           
/*     */           public void remove() {
/* 158 */             if (this.curr == -1) throw new IllegalStateException(); 
/* 159 */             this.curr = -1;
/* 160 */             int tail = Char2CharArrayMap.this.size-- - this.next--;
/* 161 */             System.arraycopy(Char2CharArrayMap.this.key, this.next + 1, Char2CharArrayMap.this.key, this.next, tail);
/* 162 */             System.arraycopy(Char2CharArrayMap.this.value, this.next + 1, Char2CharArrayMap.this.value, this.next, tail);
/*     */           }
/*     */ 
/*     */ 
/*     */ 
/*     */           
/*     */           public void forEachRemaining(Consumer<? super Char2CharMap.Entry> action) {
/* 169 */             int max = Char2CharArrayMap.this.size;
/* 170 */             while (this.next < max) {
/* 171 */               action.accept(new AbstractChar2CharMap.BasicEntry(Char2CharArrayMap.this.key[this.curr = this.next], Char2CharArrayMap.this.value[this.next++]));
/*     */             }
/*     */           }
/*     */         };
/*     */     }
/*     */ 
/*     */     
/*     */     public ObjectIterator<Char2CharMap.Entry> fastIterator() {
/* 179 */       return new ObjectIterator<Char2CharMap.Entry>() {
/* 180 */           int next = 0; int curr = -1;
/* 181 */           final AbstractChar2CharMap.BasicEntry entry = new AbstractChar2CharMap.BasicEntry();
/*     */ 
/*     */           
/*     */           public boolean hasNext() {
/* 185 */             return (this.next < Char2CharArrayMap.this.size);
/*     */           }
/*     */ 
/*     */ 
/*     */           
/*     */           public Char2CharMap.Entry next() {
/* 191 */             if (!hasNext()) throw new NoSuchElementException(); 
/* 192 */             this.entry.key = Char2CharArrayMap.this.key[this.curr = this.next];
/* 193 */             this.entry.value = Char2CharArrayMap.this.value[this.next++];
/* 194 */             return this.entry;
/*     */           }
/*     */ 
/*     */           
/*     */           public void remove() {
/* 199 */             if (this.curr == -1) throw new IllegalStateException(); 
/* 200 */             this.curr = -1;
/* 201 */             int tail = Char2CharArrayMap.this.size-- - this.next--;
/* 202 */             System.arraycopy(Char2CharArrayMap.this.key, this.next + 1, Char2CharArrayMap.this.key, this.next, tail);
/* 203 */             System.arraycopy(Char2CharArrayMap.this.value, this.next + 1, Char2CharArrayMap.this.value, this.next, tail);
/*     */           }
/*     */ 
/*     */ 
/*     */ 
/*     */           
/*     */           public void forEachRemaining(Consumer<? super Char2CharMap.Entry> action) {
/* 210 */             int max = Char2CharArrayMap.this.size;
/* 211 */             while (this.next < max) {
/* 212 */               this.entry.key = Char2CharArrayMap.this.key[this.curr = this.next];
/* 213 */               this.entry.value = Char2CharArrayMap.this.value[this.next++];
/* 214 */               action.accept(this.entry);
/*     */             } 
/*     */           }
/*     */         };
/*     */     }
/*     */     
/*     */     final class EntrySetSpliterator
/*     */       extends ObjectSpliterators.EarlyBindingSizeIndexBasedSpliterator<Char2CharMap.Entry>
/*     */       implements ObjectSpliterator<Char2CharMap.Entry> {
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
/*     */       protected final Char2CharMap.Entry get(int location) {
/* 235 */         return new AbstractChar2CharMap.BasicEntry(Char2CharArrayMap.this.key[location], Char2CharArrayMap.this.value[location]);
/*     */       }
/*     */ 
/*     */       
/*     */       protected final EntrySetSpliterator makeForSplit(int pos, int maxPos) {
/* 240 */         return new EntrySetSpliterator(pos, maxPos);
/*     */       }
/*     */     }
/*     */ 
/*     */     
/*     */     public ObjectSpliterator<Char2CharMap.Entry> spliterator() {
/* 246 */       return new EntrySetSpliterator(0, Char2CharArrayMap.this.size);
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public void forEach(Consumer<? super Char2CharMap.Entry> action) {
/* 254 */       for (int i = 0, max = Char2CharArrayMap.this.size; i < max; i++) {
/* 255 */         action.accept(new AbstractChar2CharMap.BasicEntry(Char2CharArrayMap.this.key[i], Char2CharArrayMap.this.value[i]));
/*     */       }
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public void fastForEach(Consumer<? super Char2CharMap.Entry> action) {
/* 263 */       AbstractChar2CharMap.BasicEntry entry = new AbstractChar2CharMap.BasicEntry();
/*     */       
/* 265 */       for (int i = 0, max = Char2CharArrayMap.this.size; i < max; i++) {
/* 266 */         entry.key = Char2CharArrayMap.this.key[i];
/* 267 */         entry.value = Char2CharArrayMap.this.value[i];
/* 268 */         action.accept(entry);
/*     */       } 
/*     */     }
/*     */ 
/*     */     
/*     */     public int size() {
/* 274 */       return Char2CharArrayMap.this.size;
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     public boolean contains(Object o) {
/* 280 */       if (!(o instanceof Map.Entry)) return false; 
/* 281 */       Map.Entry<?, ?> e = (Map.Entry<?, ?>)o;
/* 282 */       if (e.getKey() == null || !(e.getKey() instanceof Character)) return false; 
/* 283 */       if (e.getValue() == null || !(e.getValue() instanceof Character)) return false; 
/* 284 */       char k = ((Character)e.getKey()).charValue();
/* 285 */       return (Char2CharArrayMap.this.containsKey(k) && Char2CharArrayMap.this.get(k) == ((Character)e.getValue()).charValue());
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     public boolean remove(Object o) {
/* 291 */       if (!(o instanceof Map.Entry)) return false; 
/* 292 */       Map.Entry<?, ?> e = (Map.Entry<?, ?>)o;
/* 293 */       if (e.getKey() == null || !(e.getKey() instanceof Character)) return false; 
/* 294 */       if (e.getValue() == null || !(e.getValue() instanceof Character)) return false; 
/* 295 */       char k = ((Character)e.getKey()).charValue();
/* 296 */       char v = ((Character)e.getValue()).charValue();
/* 297 */       int oldPos = Char2CharArrayMap.this.findKey(k);
/* 298 */       if (oldPos == -1 || v != Char2CharArrayMap.this.value[oldPos]) return false; 
/* 299 */       int tail = Char2CharArrayMap.this.size - oldPos - 1;
/* 300 */       System.arraycopy(Char2CharArrayMap.this.key, oldPos + 1, Char2CharArrayMap.this.key, oldPos, tail);
/* 301 */       System.arraycopy(Char2CharArrayMap.this.value, oldPos + 1, Char2CharArrayMap.this.value, oldPos, tail);
/* 302 */       Char2CharArrayMap.this.size--;
/* 303 */       return true;
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   public Char2CharMap.FastEntrySet char2CharEntrySet() {
/* 309 */     if (this.entries == null) this.entries = new EntrySet(); 
/* 310 */     return this.entries;
/*     */   }
/*     */   
/*     */   private int findKey(char k) {
/* 314 */     char[] key = this.key;
/* 315 */     for (int i = this.size; i-- != 0;) { if (key[i] == k) return i;  }
/* 316 */      return -1;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public char get(char k) {
/* 322 */     char[] key = this.key;
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
/*     */   public boolean containsKey(char k) {
/* 339 */     return (findKey(k) != -1);
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean containsValue(char v) {
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
/*     */   public char put(char k, char v) {
/* 356 */     int oldKey = findKey(k);
/* 357 */     if (oldKey != -1) {
/* 358 */       char oldValue = this.value[oldKey];
/* 359 */       this.value[oldKey] = v;
/* 360 */       return oldValue;
/*     */     } 
/* 362 */     if (this.size == this.key.length) {
/* 363 */       char[] newKey = new char[(this.size == 0) ? 2 : (this.size * 2)];
/* 364 */       char[] newValue = new char[(this.size == 0) ? 2 : (this.size * 2)];
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
/*     */   public char remove(char k) {
/* 381 */     int oldPos = findKey(k);
/* 382 */     if (oldPos == -1) return this.defRetValue; 
/* 383 */     char oldValue = this.value[oldPos];
/* 384 */     int tail = this.size - oldPos - 1;
/* 385 */     System.arraycopy(this.key, oldPos + 1, this.key, oldPos, tail);
/* 386 */     System.arraycopy(this.value, oldPos + 1, this.value, oldPos, tail);
/* 387 */     this.size--;
/* 388 */     return oldValue;
/*     */   }
/*     */   
/*     */   private final class KeySet extends AbstractCharSet { private KeySet() {}
/*     */     
/*     */     public boolean contains(char k) {
/* 394 */       return (Char2CharArrayMap.this.findKey(k) != -1);
/*     */     }
/*     */ 
/*     */     
/*     */     public boolean remove(char k) {
/* 399 */       int oldPos = Char2CharArrayMap.this.findKey(k);
/* 400 */       if (oldPos == -1) return false; 
/* 401 */       int tail = Char2CharArrayMap.this.size - oldPos - 1;
/* 402 */       System.arraycopy(Char2CharArrayMap.this.key, oldPos + 1, Char2CharArrayMap.this.key, oldPos, tail);
/* 403 */       System.arraycopy(Char2CharArrayMap.this.value, oldPos + 1, Char2CharArrayMap.this.value, oldPos, tail);
/* 404 */       Char2CharArrayMap.this.size--;
/* 405 */       return true;
/*     */     }
/*     */ 
/*     */     
/*     */     public CharIterator iterator() {
/* 410 */       return new CharIterator() {
/* 411 */           int pos = 0;
/*     */ 
/*     */           
/*     */           public boolean hasNext() {
/* 415 */             return (this.pos < Char2CharArrayMap.this.size);
/*     */           }
/*     */ 
/*     */ 
/*     */           
/*     */           public char nextChar() {
/* 421 */             if (!hasNext()) throw new NoSuchElementException(); 
/* 422 */             return Char2CharArrayMap.this.key[this.pos++];
/*     */           }
/*     */ 
/*     */           
/*     */           public void remove() {
/* 427 */             if (this.pos == 0) throw new IllegalStateException(); 
/* 428 */             int tail = Char2CharArrayMap.this.size - this.pos;
/* 429 */             System.arraycopy(Char2CharArrayMap.this.key, this.pos, Char2CharArrayMap.this.key, this.pos - 1, tail);
/* 430 */             System.arraycopy(Char2CharArrayMap.this.value, this.pos, Char2CharArrayMap.this.value, this.pos - 1, tail);
/* 431 */             Char2CharArrayMap.this.size--;
/* 432 */             this.pos--;
/*     */           }
/*     */ 
/*     */ 
/*     */ 
/*     */           
/*     */           public void forEachRemaining(CharConsumer action) {
/* 439 */             int max = Char2CharArrayMap.this.size;
/* 440 */             while (this.pos < max)
/* 441 */               action.accept(Char2CharArrayMap.this.key[this.pos++]); 
/*     */           }
/*     */         };
/*     */     }
/*     */     
/*     */     final class KeySetSpliterator
/*     */       extends CharSpliterators.EarlyBindingSizeIndexBasedSpliterator
/*     */       implements CharSpliterator {
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
/*     */       protected final char get(int location) {
/* 461 */         return Char2CharArrayMap.this.key[location];
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
/*     */       public void forEachRemaining(CharConsumer action) {
/* 473 */         int max = Char2CharArrayMap.this.size;
/* 474 */         while (this.pos < max) {
/* 475 */           action.accept(Char2CharArrayMap.this.key[this.pos++]);
/*     */         }
/*     */       }
/*     */     }
/*     */ 
/*     */     
/*     */     public CharSpliterator spliterator() {
/* 482 */       return new KeySetSpliterator(0, Char2CharArrayMap.this.size);
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public void forEach(CharConsumer action) {
/* 489 */       for (int i = 0, max = Char2CharArrayMap.this.size; i < max; i++) {
/* 490 */         action.accept(Char2CharArrayMap.this.key[i]);
/*     */       }
/*     */     }
/*     */ 
/*     */     
/*     */     public int size() {
/* 496 */       return Char2CharArrayMap.this.size;
/*     */     }
/*     */ 
/*     */     
/*     */     public void clear() {
/* 501 */       Char2CharArrayMap.this.clear();
/*     */     } }
/*     */ 
/*     */ 
/*     */   
/*     */   public CharSet keySet() {
/* 507 */     if (this.keys == null) this.keys = new KeySet(); 
/* 508 */     return this.keys;
/*     */   }
/*     */   
/*     */   private final class ValuesCollection extends AbstractCharCollection { private ValuesCollection() {}
/*     */     
/*     */     public boolean contains(char v) {
/* 514 */       return Char2CharArrayMap.this.containsValue(v);
/*     */     }
/*     */ 
/*     */     
/*     */     public CharIterator iterator() {
/* 519 */       return new CharIterator() {
/* 520 */           int pos = 0;
/*     */ 
/*     */           
/*     */           public boolean hasNext() {
/* 524 */             return (this.pos < Char2CharArrayMap.this.size);
/*     */           }
/*     */ 
/*     */ 
/*     */           
/*     */           public char nextChar() {
/* 530 */             if (!hasNext()) throw new NoSuchElementException(); 
/* 531 */             return Char2CharArrayMap.this.value[this.pos++];
/*     */           }
/*     */ 
/*     */           
/*     */           public void remove() {
/* 536 */             if (this.pos == 0) throw new IllegalStateException(); 
/* 537 */             int tail = Char2CharArrayMap.this.size - this.pos;
/* 538 */             System.arraycopy(Char2CharArrayMap.this.key, this.pos, Char2CharArrayMap.this.key, this.pos - 1, tail);
/* 539 */             System.arraycopy(Char2CharArrayMap.this.value, this.pos, Char2CharArrayMap.this.value, this.pos - 1, tail);
/* 540 */             Char2CharArrayMap.this.size--;
/* 541 */             this.pos--;
/*     */           }
/*     */ 
/*     */ 
/*     */ 
/*     */           
/*     */           public void forEachRemaining(CharConsumer action) {
/* 548 */             int max = Char2CharArrayMap.this.size;
/* 549 */             while (this.pos < max)
/* 550 */               action.accept(Char2CharArrayMap.this.value[this.pos++]); 
/*     */           }
/*     */         };
/*     */     }
/*     */     
/*     */     final class ValuesSpliterator
/*     */       extends CharSpliterators.EarlyBindingSizeIndexBasedSpliterator
/*     */       implements CharSpliterator {
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
/*     */       protected final char get(int location) {
/* 570 */         return Char2CharArrayMap.this.value[location];
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
/*     */       public void forEachRemaining(CharConsumer action) {
/* 582 */         int max = Char2CharArrayMap.this.size;
/* 583 */         while (this.pos < max) {
/* 584 */           action.accept(Char2CharArrayMap.this.value[this.pos++]);
/*     */         }
/*     */       }
/*     */     }
/*     */ 
/*     */     
/*     */     public CharSpliterator spliterator() {
/* 591 */       return new ValuesSpliterator(0, Char2CharArrayMap.this.size);
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public void forEach(CharConsumer action) {
/* 598 */       for (int i = 0, max = Char2CharArrayMap.this.size; i < max; i++) {
/* 599 */         action.accept(Char2CharArrayMap.this.value[i]);
/*     */       }
/*     */     }
/*     */ 
/*     */     
/*     */     public int size() {
/* 605 */       return Char2CharArrayMap.this.size;
/*     */     }
/*     */ 
/*     */     
/*     */     public void clear() {
/* 610 */       Char2CharArrayMap.this.clear();
/*     */     } }
/*     */ 
/*     */ 
/*     */   
/*     */   public CharCollection values() {
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
/*     */   public Char2CharArrayMap clone() {
/*     */     Char2CharArrayMap c;
/*     */     try {
/* 634 */       c = (Char2CharArrayMap)super.clone();
/* 635 */     } catch (CloneNotSupportedException cantHappen) {
/* 636 */       throw new InternalError();
/*     */     } 
/* 638 */     c.key = (char[])this.key.clone();
/* 639 */     c.value = (char[])this.value.clone();
/* 640 */     c.entries = null;
/* 641 */     c.keys = null;
/* 642 */     c.values = null;
/* 643 */     return c;
/*     */   }
/*     */   
/*     */   private void writeObject(ObjectOutputStream s) throws IOException {
/* 647 */     s.defaultWriteObject();
/* 648 */     for (int i = 0, max = this.size; i < max; i++) {
/* 649 */       s.writeChar(this.key[i]);
/* 650 */       s.writeChar(this.value[i]);
/*     */     } 
/*     */   }
/*     */   
/*     */   private void readObject(ObjectInputStream s) throws IOException, ClassNotFoundException {
/* 655 */     s.defaultReadObject();
/* 656 */     this.key = new char[this.size];
/* 657 */     this.value = new char[this.size];
/* 658 */     for (int i = 0; i < this.size; i++) {
/* 659 */       this.key[i] = s.readChar();
/* 660 */       this.value[i] = s.readChar();
/*     */     } 
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\i\\unimi\dsi\fastutil\chars\Char2CharArrayMap.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */