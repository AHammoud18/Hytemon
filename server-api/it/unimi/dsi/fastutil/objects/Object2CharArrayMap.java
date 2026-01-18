/*     */ package it.unimi.dsi.fastutil.objects;
/*     */ 
/*     */ import it.unimi.dsi.fastutil.chars.AbstractCharCollection;
/*     */ import it.unimi.dsi.fastutil.chars.CharArrays;
/*     */ import it.unimi.dsi.fastutil.chars.CharCollection;
/*     */ import it.unimi.dsi.fastutil.chars.CharConsumer;
/*     */ import it.unimi.dsi.fastutil.chars.CharIterator;
/*     */ import it.unimi.dsi.fastutil.chars.CharSpliterator;
/*     */ import it.unimi.dsi.fastutil.chars.CharSpliterators;
/*     */ import java.io.IOException;
/*     */ import java.io.ObjectInputStream;
/*     */ import java.io.ObjectOutputStream;
/*     */ import java.io.Serializable;
/*     */ import java.util.Collection;
/*     */ import java.util.Iterator;
/*     */ import java.util.Map;
/*     */ import java.util.NoSuchElementException;
/*     */ import java.util.Objects;
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
/*     */ public class Object2CharArrayMap<K>
/*     */   extends AbstractObject2CharMap<K>
/*     */   implements Serializable, Cloneable
/*     */ {
/*     */   private static final long serialVersionUID = 1L;
/*     */   protected transient Object[] key;
/*     */   protected transient char[] value;
/*     */   protected int size;
/*     */   protected transient Object2CharMap.FastEntrySet<K> entries;
/*     */   protected transient ObjectSet<K> keys;
/*     */   protected transient CharCollection values;
/*     */   
/*     */   public Object2CharArrayMap(Object[] key, char[] value) {
/*  60 */     this.key = key;
/*  61 */     this.value = value;
/*  62 */     this.size = key.length;
/*  63 */     if (key.length != value.length) throw new IllegalArgumentException("Keys and values have different lengths (" + key.length + ", " + value.length + ")");
/*     */   
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public Object2CharArrayMap() {
/*  70 */     this.key = ObjectArrays.EMPTY_ARRAY;
/*  71 */     this.value = CharArrays.EMPTY_ARRAY;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Object2CharArrayMap(int capacity) {
/*  80 */     this.key = new Object[capacity];
/*  81 */     this.value = new char[capacity];
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Object2CharArrayMap(Object2CharMap<K> m) {
/*  90 */     this(m.size());
/*  91 */     int i = 0;
/*  92 */     for (ObjectIterator<Object2CharMap.Entry<K>> objectIterator = m.object2CharEntrySet().iterator(); objectIterator.hasNext(); ) { Object2CharMap.Entry<K> e = objectIterator.next();
/*  93 */       this.key[i] = e.getKey();
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
/*     */   public Object2CharArrayMap(Map<? extends K, ? extends Character> m) {
/* 106 */     this(m.size());
/* 107 */     int i = 0;
/* 108 */     for (Map.Entry<? extends K, ? extends Character> e : m.entrySet()) {
/* 109 */       this.key[i] = e.getKey();
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
/*     */   public Object2CharArrayMap(Object[] key, char[] value, int size) {
/* 129 */     this.key = key;
/* 130 */     this.value = value;
/* 131 */     this.size = size;
/* 132 */     if (key.length != value.length) throw new IllegalArgumentException("Keys and values have different lengths (" + key.length + ", " + value.length + ")"); 
/* 133 */     if (size > key.length) throw new IllegalArgumentException("The provided size (" + size + ") is larger than or equal to the backing-arrays size (" + key.length + ")"); 
/*     */   }
/*     */   
/*     */   private final class EntrySet
/*     */     extends AbstractObjectSet<Object2CharMap.Entry<K>> implements Object2CharMap.FastEntrySet<K> {
/*     */     private EntrySet() {}
/*     */     
/*     */     public ObjectIterator<Object2CharMap.Entry<K>> iterator() {
/* 141 */       return (ObjectIterator)new ObjectIterator<Object2CharMap.Entry<Object2CharMap.Entry<K>>>() {
/* 142 */           int curr = -1; int next = 0;
/*     */ 
/*     */           
/*     */           public boolean hasNext() {
/* 146 */             return (this.next < Object2CharArrayMap.this.size);
/*     */           }
/*     */ 
/*     */ 
/*     */           
/*     */           public Object2CharMap.Entry<K> next() {
/* 152 */             if (!hasNext()) throw new NoSuchElementException(); 
/* 153 */             return new AbstractObject2CharMap.BasicEntry<>((K)Object2CharArrayMap.this.key[this.curr = this.next], Object2CharArrayMap.this.value[this.next++]);
/*     */           }
/*     */ 
/*     */           
/*     */           public void remove() {
/* 158 */             if (this.curr == -1) throw new IllegalStateException(); 
/* 159 */             this.curr = -1;
/* 160 */             int tail = Object2CharArrayMap.this.size-- - this.next--;
/* 161 */             System.arraycopy(Object2CharArrayMap.this.key, this.next + 1, Object2CharArrayMap.this.key, this.next, tail);
/* 162 */             System.arraycopy(Object2CharArrayMap.this.value, this.next + 1, Object2CharArrayMap.this.value, this.next, tail);
/* 163 */             Object2CharArrayMap.this.key[Object2CharArrayMap.this.size] = null;
/*     */           }
/*     */ 
/*     */ 
/*     */ 
/*     */           
/*     */           public void forEachRemaining(Consumer<? super Object2CharMap.Entry<K>> action) {
/* 170 */             int max = Object2CharArrayMap.this.size;
/* 171 */             while (this.next < max) {
/* 172 */               action.accept(new AbstractObject2CharMap.BasicEntry<>((K)Object2CharArrayMap.this.key[this.curr = this.next], Object2CharArrayMap.this.value[this.next++]));
/*     */             }
/*     */           }
/*     */         };
/*     */     }
/*     */ 
/*     */     
/*     */     public ObjectIterator<Object2CharMap.Entry<K>> fastIterator() {
/* 180 */       return (ObjectIterator)new ObjectIterator<Object2CharMap.Entry<Object2CharMap.Entry<K>>>() {
/* 181 */           int next = 0; int curr = -1;
/* 182 */           final AbstractObject2CharMap.BasicEntry<K> entry = new AbstractObject2CharMap.BasicEntry<>();
/*     */ 
/*     */           
/*     */           public boolean hasNext() {
/* 186 */             return (this.next < Object2CharArrayMap.this.size);
/*     */           }
/*     */ 
/*     */ 
/*     */           
/*     */           public Object2CharMap.Entry<K> next() {
/* 192 */             if (!hasNext()) throw new NoSuchElementException(); 
/* 193 */             this.entry.key = (K)Object2CharArrayMap.this.key[this.curr = this.next];
/* 194 */             this.entry.value = Object2CharArrayMap.this.value[this.next++];
/* 195 */             return this.entry;
/*     */           }
/*     */ 
/*     */           
/*     */           public void remove() {
/* 200 */             if (this.curr == -1) throw new IllegalStateException(); 
/* 201 */             this.curr = -1;
/* 202 */             int tail = Object2CharArrayMap.this.size-- - this.next--;
/* 203 */             System.arraycopy(Object2CharArrayMap.this.key, this.next + 1, Object2CharArrayMap.this.key, this.next, tail);
/* 204 */             System.arraycopy(Object2CharArrayMap.this.value, this.next + 1, Object2CharArrayMap.this.value, this.next, tail);
/* 205 */             Object2CharArrayMap.this.key[Object2CharArrayMap.this.size] = null;
/*     */           }
/*     */ 
/*     */ 
/*     */ 
/*     */           
/*     */           public void forEachRemaining(Consumer<? super Object2CharMap.Entry<K>> action) {
/* 212 */             int max = Object2CharArrayMap.this.size;
/* 213 */             while (this.next < max) {
/* 214 */               this.entry.key = (K)Object2CharArrayMap.this.key[this.curr = this.next];
/* 215 */               this.entry.value = Object2CharArrayMap.this.value[this.next++];
/* 216 */               action.accept(this.entry);
/*     */             } 
/*     */           }
/*     */         };
/*     */     }
/*     */     
/*     */     final class EntrySetSpliterator
/*     */       extends ObjectSpliterators.EarlyBindingSizeIndexBasedSpliterator<Object2CharMap.Entry<K>>
/*     */       implements ObjectSpliterator<Object2CharMap.Entry<K>> {
/*     */       EntrySetSpliterator(int pos, int maxPos) {
/* 226 */         super(pos, maxPos);
/*     */       }
/*     */ 
/*     */       
/*     */       public int characteristics() {
/* 231 */         return 16465;
/*     */       }
/*     */ 
/*     */ 
/*     */       
/*     */       protected final Object2CharMap.Entry<K> get(int location) {
/* 237 */         return new AbstractObject2CharMap.BasicEntry<>((K)Object2CharArrayMap.this.key[location], Object2CharArrayMap.this.value[location]);
/*     */       }
/*     */ 
/*     */       
/*     */       protected final EntrySetSpliterator makeForSplit(int pos, int maxPos) {
/* 242 */         return new EntrySetSpliterator(pos, maxPos);
/*     */       }
/*     */     }
/*     */ 
/*     */     
/*     */     public ObjectSpliterator<Object2CharMap.Entry<K>> spliterator() {
/* 248 */       return new EntrySetSpliterator(0, Object2CharArrayMap.this.size);
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public void forEach(Consumer<? super Object2CharMap.Entry<K>> action) {
/* 256 */       for (int i = 0, max = Object2CharArrayMap.this.size; i < max; i++) {
/* 257 */         action.accept(new AbstractObject2CharMap.BasicEntry<>((K)Object2CharArrayMap.this.key[i], Object2CharArrayMap.this.value[i]));
/*     */       }
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public void fastForEach(Consumer<? super Object2CharMap.Entry<K>> action) {
/* 265 */       AbstractObject2CharMap.BasicEntry<K> entry = new AbstractObject2CharMap.BasicEntry<>();
/*     */       
/* 267 */       for (int i = 0, max = Object2CharArrayMap.this.size; i < max; i++) {
/* 268 */         entry.key = (K)Object2CharArrayMap.this.key[i];
/* 269 */         entry.value = Object2CharArrayMap.this.value[i];
/* 270 */         action.accept(entry);
/*     */       } 
/*     */     }
/*     */ 
/*     */     
/*     */     public int size() {
/* 276 */       return Object2CharArrayMap.this.size;
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     public boolean contains(Object o) {
/* 282 */       if (!(o instanceof Map.Entry)) return false; 
/* 283 */       Map.Entry<?, ?> e = (Map.Entry<?, ?>)o;
/* 284 */       if (e.getValue() == null || !(e.getValue() instanceof Character)) return false; 
/* 285 */       K k = (K)e.getKey();
/* 286 */       return (Object2CharArrayMap.this.containsKey(k) && Object2CharArrayMap.this.getChar(k) == ((Character)e.getValue()).charValue());
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     public boolean remove(Object o) {
/* 292 */       if (!(o instanceof Map.Entry)) return false; 
/* 293 */       Map.Entry<?, ?> e = (Map.Entry<?, ?>)o;
/* 294 */       if (e.getValue() == null || !(e.getValue() instanceof Character)) return false; 
/* 295 */       K k = (K)e.getKey();
/* 296 */       char v = ((Character)e.getValue()).charValue();
/* 297 */       int oldPos = Object2CharArrayMap.this.findKey(k);
/* 298 */       if (oldPos == -1 || v != Object2CharArrayMap.this.value[oldPos]) return false; 
/* 299 */       int tail = Object2CharArrayMap.this.size - oldPos - 1;
/* 300 */       System.arraycopy(Object2CharArrayMap.this.key, oldPos + 1, Object2CharArrayMap.this.key, oldPos, tail);
/* 301 */       System.arraycopy(Object2CharArrayMap.this.value, oldPos + 1, Object2CharArrayMap.this.value, oldPos, tail);
/* 302 */       Object2CharArrayMap.this.size--;
/* 303 */       Object2CharArrayMap.this.key[Object2CharArrayMap.this.size] = null;
/* 304 */       return true;
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   public Object2CharMap.FastEntrySet<K> object2CharEntrySet() {
/* 310 */     if (this.entries == null) this.entries = new EntrySet(); 
/* 311 */     return this.entries;
/*     */   }
/*     */   
/*     */   private int findKey(Object k) {
/* 315 */     Object[] key = this.key;
/* 316 */     for (int i = this.size; i-- != 0;) { if (Objects.equals(key[i], k)) return i;  }
/* 317 */      return -1;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public char getChar(Object k) {
/* 323 */     Object[] key = this.key;
/* 324 */     for (int i = this.size; i-- != 0;) { if (Objects.equals(key[i], k)) return this.value[i];  }
/* 325 */      return this.defRetValue;
/*     */   }
/*     */ 
/*     */   
/*     */   public int size() {
/* 330 */     return this.size;
/*     */   }
/*     */ 
/*     */   
/*     */   public void clear() {
/* 335 */     for (int i = this.size; i-- != 0;) {
/* 336 */       this.key[i] = null;
/*     */     }
/* 338 */     this.size = 0;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean containsKey(Object k) {
/* 343 */     return (findKey(k) != -1);
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean containsValue(char v) {
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
/*     */   public char put(K k, char v) {
/* 360 */     int oldKey = findKey(k);
/* 361 */     if (oldKey != -1) {
/* 362 */       char oldValue = this.value[oldKey];
/* 363 */       this.value[oldKey] = v;
/* 364 */       return oldValue;
/*     */     } 
/* 366 */     if (this.size == this.key.length) {
/* 367 */       Object[] newKey = new Object[(this.size == 0) ? 2 : (this.size * 2)];
/* 368 */       char[] newValue = new char[(this.size == 0) ? 2 : (this.size * 2)];
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
/*     */   public char removeChar(Object k) {
/* 385 */     int oldPos = findKey(k);
/* 386 */     if (oldPos == -1) return this.defRetValue; 
/* 387 */     char oldValue = this.value[oldPos];
/* 388 */     int tail = this.size - oldPos - 1;
/* 389 */     System.arraycopy(this.key, oldPos + 1, this.key, oldPos, tail);
/* 390 */     System.arraycopy(this.value, oldPos + 1, this.value, oldPos, tail);
/* 391 */     this.size--;
/* 392 */     this.key[this.size] = null;
/* 393 */     return oldValue;
/*     */   }
/*     */   
/*     */   private final class KeySet extends AbstractObjectSet<K> { private KeySet() {}
/*     */     
/*     */     public boolean contains(Object k) {
/* 399 */       return (Object2CharArrayMap.this.findKey(k) != -1);
/*     */     }
/*     */ 
/*     */     
/*     */     public boolean remove(Object k) {
/* 404 */       int oldPos = Object2CharArrayMap.this.findKey(k);
/* 405 */       if (oldPos == -1) return false; 
/* 406 */       int tail = Object2CharArrayMap.this.size - oldPos - 1;
/* 407 */       System.arraycopy(Object2CharArrayMap.this.key, oldPos + 1, Object2CharArrayMap.this.key, oldPos, tail);
/* 408 */       System.arraycopy(Object2CharArrayMap.this.value, oldPos + 1, Object2CharArrayMap.this.value, oldPos, tail);
/* 409 */       Object2CharArrayMap.this.size--;
/* 410 */       Object2CharArrayMap.this.key[Object2CharArrayMap.this.size] = null;
/* 411 */       return true;
/*     */     }
/*     */ 
/*     */     
/*     */     public ObjectIterator<K> iterator() {
/* 416 */       return new ObjectIterator<K>() {
/* 417 */           int pos = 0;
/*     */ 
/*     */           
/*     */           public boolean hasNext() {
/* 421 */             return (this.pos < Object2CharArrayMap.this.size);
/*     */           }
/*     */ 
/*     */ 
/*     */           
/*     */           public K next() {
/* 427 */             if (!hasNext()) throw new NoSuchElementException(); 
/* 428 */             return (K)Object2CharArrayMap.this.key[this.pos++];
/*     */           }
/*     */ 
/*     */           
/*     */           public void remove() {
/* 433 */             if (this.pos == 0) throw new IllegalStateException(); 
/* 434 */             int tail = Object2CharArrayMap.this.size - this.pos;
/* 435 */             System.arraycopy(Object2CharArrayMap.this.key, this.pos, Object2CharArrayMap.this.key, this.pos - 1, tail);
/* 436 */             System.arraycopy(Object2CharArrayMap.this.value, this.pos, Object2CharArrayMap.this.value, this.pos - 1, tail);
/* 437 */             Object2CharArrayMap.this.size--;
/* 438 */             this.pos--;
/* 439 */             Object2CharArrayMap.this.key[Object2CharArrayMap.this.size] = null;
/*     */           }
/*     */ 
/*     */ 
/*     */ 
/*     */           
/*     */           public void forEachRemaining(Consumer<? super K> action) {
/* 446 */             int max = Object2CharArrayMap.this.size;
/* 447 */             while (this.pos < max)
/* 448 */               action.accept((K)Object2CharArrayMap.this.key[this.pos++]); 
/*     */           }
/*     */         };
/*     */     }
/*     */     
/*     */     final class KeySetSpliterator
/*     */       extends ObjectSpliterators.EarlyBindingSizeIndexBasedSpliterator<K>
/*     */       implements ObjectSpliterator<K> {
/*     */       KeySetSpliterator(int pos, int maxPos) {
/* 457 */         super(pos, maxPos);
/*     */       }
/*     */ 
/*     */       
/*     */       public int characteristics() {
/* 462 */         return 16465;
/*     */       }
/*     */ 
/*     */ 
/*     */       
/*     */       protected final K get(int location) {
/* 468 */         return (K)Object2CharArrayMap.this.key[location];
/*     */       }
/*     */ 
/*     */       
/*     */       protected final KeySetSpliterator makeForSplit(int pos, int maxPos) {
/* 473 */         return new KeySetSpliterator(pos, maxPos);
/*     */       }
/*     */ 
/*     */ 
/*     */ 
/*     */       
/*     */       public void forEachRemaining(Consumer<? super K> action) {
/* 480 */         int max = Object2CharArrayMap.this.size;
/* 481 */         while (this.pos < max) {
/* 482 */           action.accept((K)Object2CharArrayMap.this.key[this.pos++]);
/*     */         }
/*     */       }
/*     */     }
/*     */ 
/*     */     
/*     */     public ObjectSpliterator<K> spliterator() {
/* 489 */       return new KeySetSpliterator(0, Object2CharArrayMap.this.size);
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public void forEach(Consumer<? super K> action) {
/* 496 */       for (int i = 0, max = Object2CharArrayMap.this.size; i < max; i++) {
/* 497 */         action.accept((K)Object2CharArrayMap.this.key[i]);
/*     */       }
/*     */     }
/*     */ 
/*     */     
/*     */     public int size() {
/* 503 */       return Object2CharArrayMap.this.size;
/*     */     }
/*     */ 
/*     */     
/*     */     public void clear() {
/* 508 */       Object2CharArrayMap.this.clear();
/*     */     } }
/*     */ 
/*     */ 
/*     */   
/*     */   public ObjectSet<K> keySet() {
/* 514 */     if (this.keys == null) this.keys = new KeySet(); 
/* 515 */     return this.keys;
/*     */   }
/*     */   
/*     */   private final class ValuesCollection extends AbstractCharCollection { private ValuesCollection() {}
/*     */     
/*     */     public boolean contains(char v) {
/* 521 */       return Object2CharArrayMap.this.containsValue(v);
/*     */     }
/*     */ 
/*     */     
/*     */     public CharIterator iterator() {
/* 526 */       return new CharIterator() {
/* 527 */           int pos = 0;
/*     */ 
/*     */           
/*     */           public boolean hasNext() {
/* 531 */             return (this.pos < Object2CharArrayMap.this.size);
/*     */           }
/*     */ 
/*     */ 
/*     */           
/*     */           public char nextChar() {
/* 537 */             if (!hasNext()) throw new NoSuchElementException(); 
/* 538 */             return Object2CharArrayMap.this.value[this.pos++];
/*     */           }
/*     */ 
/*     */           
/*     */           public void remove() {
/* 543 */             if (this.pos == 0) throw new IllegalStateException(); 
/* 544 */             int tail = Object2CharArrayMap.this.size - this.pos;
/* 545 */             System.arraycopy(Object2CharArrayMap.this.key, this.pos, Object2CharArrayMap.this.key, this.pos - 1, tail);
/* 546 */             System.arraycopy(Object2CharArrayMap.this.value, this.pos, Object2CharArrayMap.this.value, this.pos - 1, tail);
/* 547 */             Object2CharArrayMap.this.size--;
/* 548 */             this.pos--;
/* 549 */             Object2CharArrayMap.this.key[Object2CharArrayMap.this.size] = null;
/*     */           }
/*     */ 
/*     */ 
/*     */ 
/*     */           
/*     */           public void forEachRemaining(CharConsumer action) {
/* 556 */             int max = Object2CharArrayMap.this.size;
/* 557 */             while (this.pos < max)
/* 558 */               action.accept(Object2CharArrayMap.this.value[this.pos++]); 
/*     */           }
/*     */         };
/*     */     }
/*     */     
/*     */     final class ValuesSpliterator
/*     */       extends CharSpliterators.EarlyBindingSizeIndexBasedSpliterator
/*     */       implements CharSpliterator {
/*     */       ValuesSpliterator(int pos, int maxPos) {
/* 567 */         super(pos, maxPos);
/*     */       }
/*     */ 
/*     */       
/*     */       public int characteristics() {
/* 572 */         return 16720;
/*     */       }
/*     */ 
/*     */ 
/*     */       
/*     */       protected final char get(int location) {
/* 578 */         return Object2CharArrayMap.this.value[location];
/*     */       }
/*     */ 
/*     */       
/*     */       protected final ValuesSpliterator makeForSplit(int pos, int maxPos) {
/* 583 */         return new ValuesSpliterator(pos, maxPos);
/*     */       }
/*     */ 
/*     */ 
/*     */ 
/*     */       
/*     */       public void forEachRemaining(CharConsumer action) {
/* 590 */         int max = Object2CharArrayMap.this.size;
/* 591 */         while (this.pos < max) {
/* 592 */           action.accept(Object2CharArrayMap.this.value[this.pos++]);
/*     */         }
/*     */       }
/*     */     }
/*     */ 
/*     */     
/*     */     public CharSpliterator spliterator() {
/* 599 */       return new ValuesSpliterator(0, Object2CharArrayMap.this.size);
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public void forEach(CharConsumer action) {
/* 606 */       for (int i = 0, max = Object2CharArrayMap.this.size; i < max; i++) {
/* 607 */         action.accept(Object2CharArrayMap.this.value[i]);
/*     */       }
/*     */     }
/*     */ 
/*     */     
/*     */     public int size() {
/* 613 */       return Object2CharArrayMap.this.size;
/*     */     }
/*     */ 
/*     */     
/*     */     public void clear() {
/* 618 */       Object2CharArrayMap.this.clear();
/*     */     } }
/*     */ 
/*     */ 
/*     */   
/*     */   public CharCollection values() {
/* 624 */     if (this.values == null) this.values = (CharCollection)new ValuesCollection(); 
/* 625 */     return this.values;
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
/*     */   public Object2CharArrayMap<K> clone() {
/*     */     Object2CharArrayMap<K> c;
/*     */     try {
/* 642 */       c = (Object2CharArrayMap<K>)super.clone();
/* 643 */     } catch (CloneNotSupportedException cantHappen) {
/* 644 */       throw new InternalError();
/*     */     } 
/* 646 */     c.key = (Object[])this.key.clone();
/* 647 */     c.value = (char[])this.value.clone();
/* 648 */     c.entries = null;
/* 649 */     c.keys = null;
/* 650 */     c.values = null;
/* 651 */     return c;
/*     */   }
/*     */   
/*     */   private void writeObject(ObjectOutputStream s) throws IOException {
/* 655 */     s.defaultWriteObject();
/* 656 */     for (int i = 0, max = this.size; i < max; i++) {
/* 657 */       s.writeObject(this.key[i]);
/* 658 */       s.writeChar(this.value[i]);
/*     */     } 
/*     */   }
/*     */   
/*     */   private void readObject(ObjectInputStream s) throws IOException, ClassNotFoundException {
/* 663 */     s.defaultReadObject();
/* 664 */     this.key = new Object[this.size];
/* 665 */     this.value = new char[this.size];
/* 666 */     for (int i = 0; i < this.size; i++) {
/* 667 */       this.key[i] = s.readObject();
/* 668 */       this.value[i] = s.readChar();
/*     */     } 
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\i\\unimi\dsi\fastutil\objects\Object2CharArrayMap.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */