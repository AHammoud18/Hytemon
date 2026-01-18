/*     */ package it.unimi.dsi.fastutil.chars;
/*     */ 
/*     */ import it.unimi.dsi.fastutil.objects.AbstractObjectSet;
/*     */ import it.unimi.dsi.fastutil.objects.AbstractReferenceCollection;
/*     */ import it.unimi.dsi.fastutil.objects.ObjectArrays;
/*     */ import it.unimi.dsi.fastutil.objects.ObjectIterator;
/*     */ import it.unimi.dsi.fastutil.objects.ObjectSet;
/*     */ import it.unimi.dsi.fastutil.objects.ObjectSpliterator;
/*     */ import it.unimi.dsi.fastutil.objects.ObjectSpliterators;
/*     */ import it.unimi.dsi.fastutil.objects.ReferenceCollection;
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
/*     */ public class Char2ReferenceArrayMap<V>
/*     */   extends AbstractChar2ReferenceMap<V>
/*     */   implements Serializable, Cloneable
/*     */ {
/*     */   private static final long serialVersionUID = 1L;
/*     */   protected transient char[] key;
/*     */   protected transient Object[] value;
/*     */   protected int size;
/*     */   protected transient Char2ReferenceMap.FastEntrySet<V> entries;
/*     */   protected transient CharSet keys;
/*     */   protected transient ReferenceCollection<V> values;
/*     */   
/*     */   public Char2ReferenceArrayMap(char[] key, Object[] value) {
/*  63 */     this.key = key;
/*  64 */     this.value = value;
/*  65 */     this.size = key.length;
/*  66 */     if (key.length != value.length) throw new IllegalArgumentException("Keys and values have different lengths (" + key.length + ", " + value.length + ")");
/*     */   
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public Char2ReferenceArrayMap() {
/*  73 */     this.key = CharArrays.EMPTY_ARRAY;
/*  74 */     this.value = ObjectArrays.EMPTY_ARRAY;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Char2ReferenceArrayMap(int capacity) {
/*  83 */     this.key = new char[capacity];
/*  84 */     this.value = new Object[capacity];
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Char2ReferenceArrayMap(Char2ReferenceMap<V> m) {
/*  93 */     this(m.size());
/*  94 */     int i = 0;
/*  95 */     for (ObjectIterator<Char2ReferenceMap.Entry<V>> objectIterator = m.char2ReferenceEntrySet().iterator(); objectIterator.hasNext(); ) { Char2ReferenceMap.Entry<V> e = objectIterator.next();
/*  96 */       this.key[i] = e.getCharKey();
/*  97 */       this.value[i] = e.getValue();
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
/*     */   public Char2ReferenceArrayMap(Map<? extends Character, ? extends V> m) {
/* 109 */     this(m.size());
/* 110 */     int i = 0;
/* 111 */     for (Map.Entry<? extends Character, ? extends V> e : m.entrySet()) {
/* 112 */       this.key[i] = ((Character)e.getKey()).charValue();
/* 113 */       this.value[i] = e.getValue();
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
/*     */   public Char2ReferenceArrayMap(char[] key, Object[] value, int size) {
/* 132 */     this.key = key;
/* 133 */     this.value = value;
/* 134 */     this.size = size;
/* 135 */     if (key.length != value.length) throw new IllegalArgumentException("Keys and values have different lengths (" + key.length + ", " + value.length + ")"); 
/* 136 */     if (size > key.length) throw new IllegalArgumentException("The provided size (" + size + ") is larger than or equal to the backing-arrays size (" + key.length + ")"); 
/*     */   }
/*     */   
/*     */   private final class EntrySet
/*     */     extends AbstractObjectSet<Char2ReferenceMap.Entry<V>> implements Char2ReferenceMap.FastEntrySet<V> {
/*     */     private EntrySet() {}
/*     */     
/*     */     public ObjectIterator<Char2ReferenceMap.Entry<V>> iterator() {
/* 144 */       return new ObjectIterator<Char2ReferenceMap.Entry<V>>() {
/* 145 */           int curr = -1; int next = 0;
/*     */ 
/*     */           
/*     */           public boolean hasNext() {
/* 149 */             return (this.next < Char2ReferenceArrayMap.this.size);
/*     */           }
/*     */ 
/*     */ 
/*     */           
/*     */           public Char2ReferenceMap.Entry<V> next() {
/* 155 */             if (!hasNext()) throw new NoSuchElementException(); 
/* 156 */             return new AbstractChar2ReferenceMap.BasicEntry<>(Char2ReferenceArrayMap.this.key[this.curr = this.next], (V)Char2ReferenceArrayMap.this.value[this.next++]);
/*     */           }
/*     */ 
/*     */           
/*     */           public void remove() {
/* 161 */             if (this.curr == -1) throw new IllegalStateException(); 
/* 162 */             this.curr = -1;
/* 163 */             int tail = Char2ReferenceArrayMap.this.size-- - this.next--;
/* 164 */             System.arraycopy(Char2ReferenceArrayMap.this.key, this.next + 1, Char2ReferenceArrayMap.this.key, this.next, tail);
/* 165 */             System.arraycopy(Char2ReferenceArrayMap.this.value, this.next + 1, Char2ReferenceArrayMap.this.value, this.next, tail);
/* 166 */             Char2ReferenceArrayMap.this.value[Char2ReferenceArrayMap.this.size] = null;
/*     */           }
/*     */ 
/*     */ 
/*     */ 
/*     */           
/*     */           public void forEachRemaining(Consumer<? super Char2ReferenceMap.Entry<V>> action) {
/* 173 */             int max = Char2ReferenceArrayMap.this.size;
/* 174 */             while (this.next < max) {
/* 175 */               action.accept(new AbstractChar2ReferenceMap.BasicEntry<>(Char2ReferenceArrayMap.this.key[this.curr = this.next], (V)Char2ReferenceArrayMap.this.value[this.next++]));
/*     */             }
/*     */           }
/*     */         };
/*     */     }
/*     */ 
/*     */     
/*     */     public ObjectIterator<Char2ReferenceMap.Entry<V>> fastIterator() {
/* 183 */       return new ObjectIterator<Char2ReferenceMap.Entry<V>>() {
/* 184 */           int next = 0; int curr = -1;
/* 185 */           final AbstractChar2ReferenceMap.BasicEntry<V> entry = new AbstractChar2ReferenceMap.BasicEntry<>();
/*     */ 
/*     */           
/*     */           public boolean hasNext() {
/* 189 */             return (this.next < Char2ReferenceArrayMap.this.size);
/*     */           }
/*     */ 
/*     */ 
/*     */           
/*     */           public Char2ReferenceMap.Entry<V> next() {
/* 195 */             if (!hasNext()) throw new NoSuchElementException(); 
/* 196 */             this.entry.key = Char2ReferenceArrayMap.this.key[this.curr = this.next];
/* 197 */             this.entry.value = (V)Char2ReferenceArrayMap.this.value[this.next++];
/* 198 */             return this.entry;
/*     */           }
/*     */ 
/*     */           
/*     */           public void remove() {
/* 203 */             if (this.curr == -1) throw new IllegalStateException(); 
/* 204 */             this.curr = -1;
/* 205 */             int tail = Char2ReferenceArrayMap.this.size-- - this.next--;
/* 206 */             System.arraycopy(Char2ReferenceArrayMap.this.key, this.next + 1, Char2ReferenceArrayMap.this.key, this.next, tail);
/* 207 */             System.arraycopy(Char2ReferenceArrayMap.this.value, this.next + 1, Char2ReferenceArrayMap.this.value, this.next, tail);
/* 208 */             Char2ReferenceArrayMap.this.value[Char2ReferenceArrayMap.this.size] = null;
/*     */           }
/*     */ 
/*     */ 
/*     */ 
/*     */           
/*     */           public void forEachRemaining(Consumer<? super Char2ReferenceMap.Entry<V>> action) {
/* 215 */             int max = Char2ReferenceArrayMap.this.size;
/* 216 */             while (this.next < max) {
/* 217 */               this.entry.key = Char2ReferenceArrayMap.this.key[this.curr = this.next];
/* 218 */               this.entry.value = (V)Char2ReferenceArrayMap.this.value[this.next++];
/* 219 */               action.accept(this.entry);
/*     */             } 
/*     */           }
/*     */         };
/*     */     }
/*     */     
/*     */     final class EntrySetSpliterator
/*     */       extends ObjectSpliterators.EarlyBindingSizeIndexBasedSpliterator<Char2ReferenceMap.Entry<V>>
/*     */       implements ObjectSpliterator<Char2ReferenceMap.Entry<V>> {
/*     */       EntrySetSpliterator(int pos, int maxPos) {
/* 229 */         super(pos, maxPos);
/*     */       }
/*     */ 
/*     */       
/*     */       public int characteristics() {
/* 234 */         return 16465;
/*     */       }
/*     */ 
/*     */ 
/*     */       
/*     */       protected final Char2ReferenceMap.Entry<V> get(int location) {
/* 240 */         return new AbstractChar2ReferenceMap.BasicEntry<>(Char2ReferenceArrayMap.this.key[location], (V)Char2ReferenceArrayMap.this.value[location]);
/*     */       }
/*     */ 
/*     */       
/*     */       protected final EntrySetSpliterator makeForSplit(int pos, int maxPos) {
/* 245 */         return new EntrySetSpliterator(pos, maxPos);
/*     */       }
/*     */     }
/*     */ 
/*     */     
/*     */     public ObjectSpliterator<Char2ReferenceMap.Entry<V>> spliterator() {
/* 251 */       return new EntrySetSpliterator(0, Char2ReferenceArrayMap.this.size);
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public void forEach(Consumer<? super Char2ReferenceMap.Entry<V>> action) {
/* 259 */       for (int i = 0, max = Char2ReferenceArrayMap.this.size; i < max; i++) {
/* 260 */         action.accept(new AbstractChar2ReferenceMap.BasicEntry<>(Char2ReferenceArrayMap.this.key[i], (V)Char2ReferenceArrayMap.this.value[i]));
/*     */       }
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public void fastForEach(Consumer<? super Char2ReferenceMap.Entry<V>> action) {
/* 268 */       AbstractChar2ReferenceMap.BasicEntry<V> entry = new AbstractChar2ReferenceMap.BasicEntry<>();
/*     */       
/* 270 */       for (int i = 0, max = Char2ReferenceArrayMap.this.size; i < max; i++) {
/* 271 */         entry.key = Char2ReferenceArrayMap.this.key[i];
/* 272 */         entry.value = (V)Char2ReferenceArrayMap.this.value[i];
/* 273 */         action.accept(entry);
/*     */       } 
/*     */     }
/*     */ 
/*     */     
/*     */     public int size() {
/* 279 */       return Char2ReferenceArrayMap.this.size;
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     public boolean contains(Object o) {
/* 285 */       if (!(o instanceof Map.Entry)) return false; 
/* 286 */       Map.Entry<?, ?> e = (Map.Entry<?, ?>)o;
/* 287 */       if (e.getKey() == null || !(e.getKey() instanceof Character)) return false; 
/* 288 */       char k = ((Character)e.getKey()).charValue();
/* 289 */       return (Char2ReferenceArrayMap.this.containsKey(k) && Char2ReferenceArrayMap.this.get(k) == e.getValue());
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     public boolean remove(Object o) {
/* 295 */       if (!(o instanceof Map.Entry)) return false; 
/* 296 */       Map.Entry<?, ?> e = (Map.Entry<?, ?>)o;
/* 297 */       if (e.getKey() == null || !(e.getKey() instanceof Character)) return false; 
/* 298 */       char k = ((Character)e.getKey()).charValue();
/* 299 */       V v = (V)e.getValue();
/* 300 */       int oldPos = Char2ReferenceArrayMap.this.findKey(k);
/* 301 */       if (oldPos == -1 || v != Char2ReferenceArrayMap.this.value[oldPos]) return false; 
/* 302 */       int tail = Char2ReferenceArrayMap.this.size - oldPos - 1;
/* 303 */       System.arraycopy(Char2ReferenceArrayMap.this.key, oldPos + 1, Char2ReferenceArrayMap.this.key, oldPos, tail);
/* 304 */       System.arraycopy(Char2ReferenceArrayMap.this.value, oldPos + 1, Char2ReferenceArrayMap.this.value, oldPos, tail);
/* 305 */       Char2ReferenceArrayMap.this.size--;
/* 306 */       Char2ReferenceArrayMap.this.value[Char2ReferenceArrayMap.this.size] = null;
/* 307 */       return true;
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   public Char2ReferenceMap.FastEntrySet<V> char2ReferenceEntrySet() {
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
/*     */   public V get(char k) {
/* 326 */     char[] key = this.key;
/* 327 */     for (int i = this.size; i-- != 0;) { if (key[i] == k) return (V)this.value[i];  }
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
/* 338 */     for (int i = this.size; i-- != 0;) {
/* 339 */       this.value[i] = null;
/*     */     }
/* 341 */     this.size = 0;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean containsKey(char k) {
/* 346 */     return (findKey(k) != -1);
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean containsValue(Object v) {
/* 351 */     for (int i = this.size; i-- != 0;) { if (this.value[i] == v) return true;  }
/* 352 */      return false;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isEmpty() {
/* 357 */     return (this.size == 0);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public V put(char k, V v) {
/* 363 */     int oldKey = findKey(k);
/* 364 */     if (oldKey != -1) {
/* 365 */       V oldValue = (V)this.value[oldKey];
/* 366 */       this.value[oldKey] = v;
/* 367 */       return oldValue;
/*     */     } 
/* 369 */     if (this.size == this.key.length) {
/* 370 */       char[] newKey = new char[(this.size == 0) ? 2 : (this.size * 2)];
/* 371 */       Object[] newValue = new Object[(this.size == 0) ? 2 : (this.size * 2)];
/* 372 */       for (int i = this.size; i-- != 0; ) {
/* 373 */         newKey[i] = this.key[i];
/* 374 */         newValue[i] = this.value[i];
/*     */       } 
/* 376 */       this.key = newKey;
/* 377 */       this.value = newValue;
/*     */     } 
/* 379 */     this.key[this.size] = k;
/* 380 */     this.value[this.size] = v;
/* 381 */     this.size++;
/* 382 */     return this.defRetValue;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public V remove(char k) {
/* 388 */     int oldPos = findKey(k);
/* 389 */     if (oldPos == -1) return this.defRetValue; 
/* 390 */     V oldValue = (V)this.value[oldPos];
/* 391 */     int tail = this.size - oldPos - 1;
/* 392 */     System.arraycopy(this.key, oldPos + 1, this.key, oldPos, tail);
/* 393 */     System.arraycopy(this.value, oldPos + 1, this.value, oldPos, tail);
/* 394 */     this.size--;
/* 395 */     this.value[this.size] = null;
/* 396 */     return oldValue;
/*     */   }
/*     */   
/*     */   private final class KeySet extends AbstractCharSet { private KeySet() {}
/*     */     
/*     */     public boolean contains(char k) {
/* 402 */       return (Char2ReferenceArrayMap.this.findKey(k) != -1);
/*     */     }
/*     */ 
/*     */     
/*     */     public boolean remove(char k) {
/* 407 */       int oldPos = Char2ReferenceArrayMap.this.findKey(k);
/* 408 */       if (oldPos == -1) return false; 
/* 409 */       int tail = Char2ReferenceArrayMap.this.size - oldPos - 1;
/* 410 */       System.arraycopy(Char2ReferenceArrayMap.this.key, oldPos + 1, Char2ReferenceArrayMap.this.key, oldPos, tail);
/* 411 */       System.arraycopy(Char2ReferenceArrayMap.this.value, oldPos + 1, Char2ReferenceArrayMap.this.value, oldPos, tail);
/* 412 */       Char2ReferenceArrayMap.this.size--;
/* 413 */       Char2ReferenceArrayMap.this.value[Char2ReferenceArrayMap.this.size] = null;
/* 414 */       return true;
/*     */     }
/*     */ 
/*     */     
/*     */     public CharIterator iterator() {
/* 419 */       return new CharIterator() {
/* 420 */           int pos = 0;
/*     */ 
/*     */           
/*     */           public boolean hasNext() {
/* 424 */             return (this.pos < Char2ReferenceArrayMap.this.size);
/*     */           }
/*     */ 
/*     */ 
/*     */           
/*     */           public char nextChar() {
/* 430 */             if (!hasNext()) throw new NoSuchElementException(); 
/* 431 */             return Char2ReferenceArrayMap.this.key[this.pos++];
/*     */           }
/*     */ 
/*     */           
/*     */           public void remove() {
/* 436 */             if (this.pos == 0) throw new IllegalStateException(); 
/* 437 */             int tail = Char2ReferenceArrayMap.this.size - this.pos;
/* 438 */             System.arraycopy(Char2ReferenceArrayMap.this.key, this.pos, Char2ReferenceArrayMap.this.key, this.pos - 1, tail);
/* 439 */             System.arraycopy(Char2ReferenceArrayMap.this.value, this.pos, Char2ReferenceArrayMap.this.value, this.pos - 1, tail);
/* 440 */             Char2ReferenceArrayMap.this.size--;
/* 441 */             this.pos--;
/* 442 */             Char2ReferenceArrayMap.this.value[Char2ReferenceArrayMap.this.size] = null;
/*     */           }
/*     */ 
/*     */ 
/*     */ 
/*     */           
/*     */           public void forEachRemaining(CharConsumer action) {
/* 449 */             int max = Char2ReferenceArrayMap.this.size;
/* 450 */             while (this.pos < max)
/* 451 */               action.accept(Char2ReferenceArrayMap.this.key[this.pos++]); 
/*     */           }
/*     */         };
/*     */     }
/*     */     
/*     */     final class KeySetSpliterator
/*     */       extends CharSpliterators.EarlyBindingSizeIndexBasedSpliterator
/*     */       implements CharSpliterator {
/*     */       KeySetSpliterator(int pos, int maxPos) {
/* 460 */         super(pos, maxPos);
/*     */       }
/*     */ 
/*     */       
/*     */       public int characteristics() {
/* 465 */         return 16721;
/*     */       }
/*     */ 
/*     */ 
/*     */       
/*     */       protected final char get(int location) {
/* 471 */         return Char2ReferenceArrayMap.this.key[location];
/*     */       }
/*     */ 
/*     */       
/*     */       protected final KeySetSpliterator makeForSplit(int pos, int maxPos) {
/* 476 */         return new KeySetSpliterator(pos, maxPos);
/*     */       }
/*     */ 
/*     */ 
/*     */ 
/*     */       
/*     */       public void forEachRemaining(CharConsumer action) {
/* 483 */         int max = Char2ReferenceArrayMap.this.size;
/* 484 */         while (this.pos < max) {
/* 485 */           action.accept(Char2ReferenceArrayMap.this.key[this.pos++]);
/*     */         }
/*     */       }
/*     */     }
/*     */ 
/*     */     
/*     */     public CharSpliterator spliterator() {
/* 492 */       return new KeySetSpliterator(0, Char2ReferenceArrayMap.this.size);
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public void forEach(CharConsumer action) {
/* 499 */       for (int i = 0, max = Char2ReferenceArrayMap.this.size; i < max; i++) {
/* 500 */         action.accept(Char2ReferenceArrayMap.this.key[i]);
/*     */       }
/*     */     }
/*     */ 
/*     */     
/*     */     public int size() {
/* 506 */       return Char2ReferenceArrayMap.this.size;
/*     */     }
/*     */ 
/*     */     
/*     */     public void clear() {
/* 511 */       Char2ReferenceArrayMap.this.clear();
/*     */     } }
/*     */ 
/*     */ 
/*     */   
/*     */   public CharSet keySet() {
/* 517 */     if (this.keys == null) this.keys = new KeySet(); 
/* 518 */     return this.keys;
/*     */   }
/*     */   
/*     */   private final class ValuesCollection extends AbstractReferenceCollection<V> { private ValuesCollection() {}
/*     */     
/*     */     public boolean contains(Object v) {
/* 524 */       return Char2ReferenceArrayMap.this.containsValue(v);
/*     */     }
/*     */ 
/*     */     
/*     */     public ObjectIterator<V> iterator() {
/* 529 */       return new ObjectIterator<V>() {
/* 530 */           int pos = 0;
/*     */ 
/*     */           
/*     */           public boolean hasNext() {
/* 534 */             return (this.pos < Char2ReferenceArrayMap.this.size);
/*     */           }
/*     */ 
/*     */ 
/*     */           
/*     */           public V next() {
/* 540 */             if (!hasNext()) throw new NoSuchElementException(); 
/* 541 */             return (V)Char2ReferenceArrayMap.this.value[this.pos++];
/*     */           }
/*     */ 
/*     */           
/*     */           public void remove() {
/* 546 */             if (this.pos == 0) throw new IllegalStateException(); 
/* 547 */             int tail = Char2ReferenceArrayMap.this.size - this.pos;
/* 548 */             System.arraycopy(Char2ReferenceArrayMap.this.key, this.pos, Char2ReferenceArrayMap.this.key, this.pos - 1, tail);
/* 549 */             System.arraycopy(Char2ReferenceArrayMap.this.value, this.pos, Char2ReferenceArrayMap.this.value, this.pos - 1, tail);
/* 550 */             Char2ReferenceArrayMap.this.size--;
/* 551 */             this.pos--;
/* 552 */             Char2ReferenceArrayMap.this.value[Char2ReferenceArrayMap.this.size] = null;
/*     */           }
/*     */ 
/*     */ 
/*     */ 
/*     */           
/*     */           public void forEachRemaining(Consumer<? super V> action) {
/* 559 */             int max = Char2ReferenceArrayMap.this.size;
/* 560 */             while (this.pos < max)
/* 561 */               action.accept((V)Char2ReferenceArrayMap.this.value[this.pos++]); 
/*     */           }
/*     */         };
/*     */     }
/*     */     
/*     */     final class ValuesSpliterator
/*     */       extends ObjectSpliterators.EarlyBindingSizeIndexBasedSpliterator<V>
/*     */       implements ObjectSpliterator<V> {
/*     */       ValuesSpliterator(int pos, int maxPos) {
/* 570 */         super(pos, maxPos);
/*     */       }
/*     */ 
/*     */       
/*     */       public int characteristics() {
/* 575 */         return 16464;
/*     */       }
/*     */ 
/*     */ 
/*     */       
/*     */       protected final V get(int location) {
/* 581 */         return (V)Char2ReferenceArrayMap.this.value[location];
/*     */       }
/*     */ 
/*     */       
/*     */       protected final ValuesSpliterator makeForSplit(int pos, int maxPos) {
/* 586 */         return new ValuesSpliterator(pos, maxPos);
/*     */       }
/*     */ 
/*     */ 
/*     */ 
/*     */       
/*     */       public void forEachRemaining(Consumer<? super V> action) {
/* 593 */         int max = Char2ReferenceArrayMap.this.size;
/* 594 */         while (this.pos < max) {
/* 595 */           action.accept((V)Char2ReferenceArrayMap.this.value[this.pos++]);
/*     */         }
/*     */       }
/*     */     }
/*     */ 
/*     */     
/*     */     public ObjectSpliterator<V> spliterator() {
/* 602 */       return new ValuesSpliterator(0, Char2ReferenceArrayMap.this.size);
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public void forEach(Consumer<? super V> action) {
/* 609 */       for (int i = 0, max = Char2ReferenceArrayMap.this.size; i < max; i++) {
/* 610 */         action.accept((V)Char2ReferenceArrayMap.this.value[i]);
/*     */       }
/*     */     }
/*     */ 
/*     */     
/*     */     public int size() {
/* 616 */       return Char2ReferenceArrayMap.this.size;
/*     */     }
/*     */ 
/*     */     
/*     */     public void clear() {
/* 621 */       Char2ReferenceArrayMap.this.clear();
/*     */     } }
/*     */ 
/*     */ 
/*     */   
/*     */   public ReferenceCollection<V> values() {
/* 627 */     if (this.values == null) this.values = (ReferenceCollection<V>)new ValuesCollection(); 
/* 628 */     return this.values;
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
/*     */   public Char2ReferenceArrayMap<V> clone() {
/*     */     Char2ReferenceArrayMap<V> c;
/*     */     try {
/* 645 */       c = (Char2ReferenceArrayMap<V>)super.clone();
/* 646 */     } catch (CloneNotSupportedException cantHappen) {
/* 647 */       throw new InternalError();
/*     */     } 
/* 649 */     c.key = (char[])this.key.clone();
/* 650 */     c.value = (Object[])this.value.clone();
/* 651 */     c.entries = null;
/* 652 */     c.keys = null;
/* 653 */     c.values = null;
/* 654 */     return c;
/*     */   }
/*     */   
/*     */   private void writeObject(ObjectOutputStream s) throws IOException {
/* 658 */     s.defaultWriteObject();
/* 659 */     for (int i = 0, max = this.size; i < max; i++) {
/* 660 */       s.writeChar(this.key[i]);
/* 661 */       s.writeObject(this.value[i]);
/*     */     } 
/*     */   }
/*     */   
/*     */   private void readObject(ObjectInputStream s) throws IOException, ClassNotFoundException {
/* 666 */     s.defaultReadObject();
/* 667 */     this.key = new char[this.size];
/* 668 */     this.value = new Object[this.size];
/* 669 */     for (int i = 0; i < this.size; i++) {
/* 670 */       this.key[i] = s.readChar();
/* 671 */       this.value[i] = s.readObject();
/*     */     } 
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\i\\unimi\dsi\fastutil\chars\Char2ReferenceArrayMap.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */