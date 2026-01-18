/*     */ package it.unimi.dsi.fastutil.objects;
/*     */ 
/*     */ import it.unimi.dsi.fastutil.bytes.AbstractByteCollection;
/*     */ import it.unimi.dsi.fastutil.bytes.ByteArrays;
/*     */ import it.unimi.dsi.fastutil.bytes.ByteCollection;
/*     */ import it.unimi.dsi.fastutil.bytes.ByteConsumer;
/*     */ import it.unimi.dsi.fastutil.bytes.ByteIterator;
/*     */ import it.unimi.dsi.fastutil.bytes.ByteSpliterator;
/*     */ import it.unimi.dsi.fastutil.bytes.ByteSpliterators;
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
/*     */ public class Reference2ByteArrayMap<K>
/*     */   extends AbstractReference2ByteMap<K>
/*     */   implements Serializable, Cloneable
/*     */ {
/*     */   private static final long serialVersionUID = 1L;
/*     */   protected transient Object[] key;
/*     */   protected transient byte[] value;
/*     */   protected int size;
/*     */   protected transient Reference2ByteMap.FastEntrySet<K> entries;
/*     */   protected transient ReferenceSet<K> keys;
/*     */   protected transient ByteCollection values;
/*     */   
/*     */   public Reference2ByteArrayMap(Object[] key, byte[] value) {
/*  60 */     this.key = key;
/*  61 */     this.value = value;
/*  62 */     this.size = key.length;
/*  63 */     if (key.length != value.length) throw new IllegalArgumentException("Keys and values have different lengths (" + key.length + ", " + value.length + ")");
/*     */   
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public Reference2ByteArrayMap() {
/*  70 */     this.key = ObjectArrays.EMPTY_ARRAY;
/*  71 */     this.value = ByteArrays.EMPTY_ARRAY;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Reference2ByteArrayMap(int capacity) {
/*  80 */     this.key = new Object[capacity];
/*  81 */     this.value = new byte[capacity];
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Reference2ByteArrayMap(Reference2ByteMap<K> m) {
/*  90 */     this(m.size());
/*  91 */     int i = 0;
/*  92 */     for (ObjectIterator<Reference2ByteMap.Entry<K>> objectIterator = m.reference2ByteEntrySet().iterator(); objectIterator.hasNext(); ) { Reference2ByteMap.Entry<K> e = objectIterator.next();
/*  93 */       this.key[i] = e.getKey();
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
/*     */   public Reference2ByteArrayMap(Map<? extends K, ? extends Byte> m) {
/* 106 */     this(m.size());
/* 107 */     int i = 0;
/* 108 */     for (Map.Entry<? extends K, ? extends Byte> e : m.entrySet()) {
/* 109 */       this.key[i] = e.getKey();
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
/*     */   public Reference2ByteArrayMap(Object[] key, byte[] value, int size) {
/* 129 */     this.key = key;
/* 130 */     this.value = value;
/* 131 */     this.size = size;
/* 132 */     if (key.length != value.length) throw new IllegalArgumentException("Keys and values have different lengths (" + key.length + ", " + value.length + ")"); 
/* 133 */     if (size > key.length) throw new IllegalArgumentException("The provided size (" + size + ") is larger than or equal to the backing-arrays size (" + key.length + ")"); 
/*     */   }
/*     */   
/*     */   private final class EntrySet
/*     */     extends AbstractObjectSet<Reference2ByteMap.Entry<K>> implements Reference2ByteMap.FastEntrySet<K> {
/*     */     private EntrySet() {}
/*     */     
/*     */     public ObjectIterator<Reference2ByteMap.Entry<K>> iterator() {
/* 141 */       return (ObjectIterator)new ObjectIterator<Reference2ByteMap.Entry<Reference2ByteMap.Entry<K>>>() {
/* 142 */           int curr = -1; int next = 0;
/*     */ 
/*     */           
/*     */           public boolean hasNext() {
/* 146 */             return (this.next < Reference2ByteArrayMap.this.size);
/*     */           }
/*     */ 
/*     */ 
/*     */           
/*     */           public Reference2ByteMap.Entry<K> next() {
/* 152 */             if (!hasNext()) throw new NoSuchElementException(); 
/* 153 */             return new AbstractReference2ByteMap.BasicEntry<>((K)Reference2ByteArrayMap.this.key[this.curr = this.next], Reference2ByteArrayMap.this.value[this.next++]);
/*     */           }
/*     */ 
/*     */           
/*     */           public void remove() {
/* 158 */             if (this.curr == -1) throw new IllegalStateException(); 
/* 159 */             this.curr = -1;
/* 160 */             int tail = Reference2ByteArrayMap.this.size-- - this.next--;
/* 161 */             System.arraycopy(Reference2ByteArrayMap.this.key, this.next + 1, Reference2ByteArrayMap.this.key, this.next, tail);
/* 162 */             System.arraycopy(Reference2ByteArrayMap.this.value, this.next + 1, Reference2ByteArrayMap.this.value, this.next, tail);
/* 163 */             Reference2ByteArrayMap.this.key[Reference2ByteArrayMap.this.size] = null;
/*     */           }
/*     */ 
/*     */ 
/*     */ 
/*     */           
/*     */           public void forEachRemaining(Consumer<? super Reference2ByteMap.Entry<K>> action) {
/* 170 */             int max = Reference2ByteArrayMap.this.size;
/* 171 */             while (this.next < max) {
/* 172 */               action.accept(new AbstractReference2ByteMap.BasicEntry<>((K)Reference2ByteArrayMap.this.key[this.curr = this.next], Reference2ByteArrayMap.this.value[this.next++]));
/*     */             }
/*     */           }
/*     */         };
/*     */     }
/*     */ 
/*     */     
/*     */     public ObjectIterator<Reference2ByteMap.Entry<K>> fastIterator() {
/* 180 */       return (ObjectIterator)new ObjectIterator<Reference2ByteMap.Entry<Reference2ByteMap.Entry<K>>>() {
/* 181 */           int next = 0; int curr = -1;
/* 182 */           final AbstractReference2ByteMap.BasicEntry<K> entry = new AbstractReference2ByteMap.BasicEntry<>();
/*     */ 
/*     */           
/*     */           public boolean hasNext() {
/* 186 */             return (this.next < Reference2ByteArrayMap.this.size);
/*     */           }
/*     */ 
/*     */ 
/*     */           
/*     */           public Reference2ByteMap.Entry<K> next() {
/* 192 */             if (!hasNext()) throw new NoSuchElementException(); 
/* 193 */             this.entry.key = (K)Reference2ByteArrayMap.this.key[this.curr = this.next];
/* 194 */             this.entry.value = Reference2ByteArrayMap.this.value[this.next++];
/* 195 */             return this.entry;
/*     */           }
/*     */ 
/*     */           
/*     */           public void remove() {
/* 200 */             if (this.curr == -1) throw new IllegalStateException(); 
/* 201 */             this.curr = -1;
/* 202 */             int tail = Reference2ByteArrayMap.this.size-- - this.next--;
/* 203 */             System.arraycopy(Reference2ByteArrayMap.this.key, this.next + 1, Reference2ByteArrayMap.this.key, this.next, tail);
/* 204 */             System.arraycopy(Reference2ByteArrayMap.this.value, this.next + 1, Reference2ByteArrayMap.this.value, this.next, tail);
/* 205 */             Reference2ByteArrayMap.this.key[Reference2ByteArrayMap.this.size] = null;
/*     */           }
/*     */ 
/*     */ 
/*     */ 
/*     */           
/*     */           public void forEachRemaining(Consumer<? super Reference2ByteMap.Entry<K>> action) {
/* 212 */             int max = Reference2ByteArrayMap.this.size;
/* 213 */             while (this.next < max) {
/* 214 */               this.entry.key = (K)Reference2ByteArrayMap.this.key[this.curr = this.next];
/* 215 */               this.entry.value = Reference2ByteArrayMap.this.value[this.next++];
/* 216 */               action.accept(this.entry);
/*     */             } 
/*     */           }
/*     */         };
/*     */     }
/*     */     
/*     */     final class EntrySetSpliterator
/*     */       extends ObjectSpliterators.EarlyBindingSizeIndexBasedSpliterator<Reference2ByteMap.Entry<K>>
/*     */       implements ObjectSpliterator<Reference2ByteMap.Entry<K>> {
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
/*     */       protected final Reference2ByteMap.Entry<K> get(int location) {
/* 237 */         return new AbstractReference2ByteMap.BasicEntry<>((K)Reference2ByteArrayMap.this.key[location], Reference2ByteArrayMap.this.value[location]);
/*     */       }
/*     */ 
/*     */       
/*     */       protected final EntrySetSpliterator makeForSplit(int pos, int maxPos) {
/* 242 */         return new EntrySetSpliterator(pos, maxPos);
/*     */       }
/*     */     }
/*     */ 
/*     */     
/*     */     public ObjectSpliterator<Reference2ByteMap.Entry<K>> spliterator() {
/* 248 */       return new EntrySetSpliterator(0, Reference2ByteArrayMap.this.size);
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public void forEach(Consumer<? super Reference2ByteMap.Entry<K>> action) {
/* 256 */       for (int i = 0, max = Reference2ByteArrayMap.this.size; i < max; i++) {
/* 257 */         action.accept(new AbstractReference2ByteMap.BasicEntry<>((K)Reference2ByteArrayMap.this.key[i], Reference2ByteArrayMap.this.value[i]));
/*     */       }
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public void fastForEach(Consumer<? super Reference2ByteMap.Entry<K>> action) {
/* 265 */       AbstractReference2ByteMap.BasicEntry<K> entry = new AbstractReference2ByteMap.BasicEntry<>();
/*     */       
/* 267 */       for (int i = 0, max = Reference2ByteArrayMap.this.size; i < max; i++) {
/* 268 */         entry.key = (K)Reference2ByteArrayMap.this.key[i];
/* 269 */         entry.value = Reference2ByteArrayMap.this.value[i];
/* 270 */         action.accept(entry);
/*     */       } 
/*     */     }
/*     */ 
/*     */     
/*     */     public int size() {
/* 276 */       return Reference2ByteArrayMap.this.size;
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     public boolean contains(Object o) {
/* 282 */       if (!(o instanceof Map.Entry)) return false; 
/* 283 */       Map.Entry<?, ?> e = (Map.Entry<?, ?>)o;
/* 284 */       if (e.getValue() == null || !(e.getValue() instanceof Byte)) return false; 
/* 285 */       K k = (K)e.getKey();
/* 286 */       return (Reference2ByteArrayMap.this.containsKey(k) && Reference2ByteArrayMap.this.getByte(k) == ((Byte)e.getValue()).byteValue());
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     public boolean remove(Object o) {
/* 292 */       if (!(o instanceof Map.Entry)) return false; 
/* 293 */       Map.Entry<?, ?> e = (Map.Entry<?, ?>)o;
/* 294 */       if (e.getValue() == null || !(e.getValue() instanceof Byte)) return false; 
/* 295 */       K k = (K)e.getKey();
/* 296 */       byte v = ((Byte)e.getValue()).byteValue();
/* 297 */       int oldPos = Reference2ByteArrayMap.this.findKey(k);
/* 298 */       if (oldPos == -1 || v != Reference2ByteArrayMap.this.value[oldPos]) return false; 
/* 299 */       int tail = Reference2ByteArrayMap.this.size - oldPos - 1;
/* 300 */       System.arraycopy(Reference2ByteArrayMap.this.key, oldPos + 1, Reference2ByteArrayMap.this.key, oldPos, tail);
/* 301 */       System.arraycopy(Reference2ByteArrayMap.this.value, oldPos + 1, Reference2ByteArrayMap.this.value, oldPos, tail);
/* 302 */       Reference2ByteArrayMap.this.size--;
/* 303 */       Reference2ByteArrayMap.this.key[Reference2ByteArrayMap.this.size] = null;
/* 304 */       return true;
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   public Reference2ByteMap.FastEntrySet<K> reference2ByteEntrySet() {
/* 310 */     if (this.entries == null) this.entries = new EntrySet(); 
/* 311 */     return this.entries;
/*     */   }
/*     */   
/*     */   private int findKey(Object k) {
/* 315 */     Object[] key = this.key;
/* 316 */     for (int i = this.size; i-- != 0;) { if (key[i] == k) return i;  }
/* 317 */      return -1;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public byte getByte(Object k) {
/* 323 */     Object[] key = this.key;
/* 324 */     for (int i = this.size; i-- != 0;) { if (key[i] == k) return this.value[i];  }
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
/*     */   public byte put(K k, byte v) {
/* 360 */     int oldKey = findKey(k);
/* 361 */     if (oldKey != -1) {
/* 362 */       byte oldValue = this.value[oldKey];
/* 363 */       this.value[oldKey] = v;
/* 364 */       return oldValue;
/*     */     } 
/* 366 */     if (this.size == this.key.length) {
/* 367 */       Object[] newKey = new Object[(this.size == 0) ? 2 : (this.size * 2)];
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
/*     */   public byte removeByte(Object k) {
/* 385 */     int oldPos = findKey(k);
/* 386 */     if (oldPos == -1) return this.defRetValue; 
/* 387 */     byte oldValue = this.value[oldPos];
/* 388 */     int tail = this.size - oldPos - 1;
/* 389 */     System.arraycopy(this.key, oldPos + 1, this.key, oldPos, tail);
/* 390 */     System.arraycopy(this.value, oldPos + 1, this.value, oldPos, tail);
/* 391 */     this.size--;
/* 392 */     this.key[this.size] = null;
/* 393 */     return oldValue;
/*     */   }
/*     */   
/*     */   private final class KeySet extends AbstractReferenceSet<K> { private KeySet() {}
/*     */     
/*     */     public boolean contains(Object k) {
/* 399 */       return (Reference2ByteArrayMap.this.findKey(k) != -1);
/*     */     }
/*     */ 
/*     */     
/*     */     public boolean remove(Object k) {
/* 404 */       int oldPos = Reference2ByteArrayMap.this.findKey(k);
/* 405 */       if (oldPos == -1) return false; 
/* 406 */       int tail = Reference2ByteArrayMap.this.size - oldPos - 1;
/* 407 */       System.arraycopy(Reference2ByteArrayMap.this.key, oldPos + 1, Reference2ByteArrayMap.this.key, oldPos, tail);
/* 408 */       System.arraycopy(Reference2ByteArrayMap.this.value, oldPos + 1, Reference2ByteArrayMap.this.value, oldPos, tail);
/* 409 */       Reference2ByteArrayMap.this.size--;
/* 410 */       Reference2ByteArrayMap.this.key[Reference2ByteArrayMap.this.size] = null;
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
/* 421 */             return (this.pos < Reference2ByteArrayMap.this.size);
/*     */           }
/*     */ 
/*     */ 
/*     */           
/*     */           public K next() {
/* 427 */             if (!hasNext()) throw new NoSuchElementException(); 
/* 428 */             return (K)Reference2ByteArrayMap.this.key[this.pos++];
/*     */           }
/*     */ 
/*     */           
/*     */           public void remove() {
/* 433 */             if (this.pos == 0) throw new IllegalStateException(); 
/* 434 */             int tail = Reference2ByteArrayMap.this.size - this.pos;
/* 435 */             System.arraycopy(Reference2ByteArrayMap.this.key, this.pos, Reference2ByteArrayMap.this.key, this.pos - 1, tail);
/* 436 */             System.arraycopy(Reference2ByteArrayMap.this.value, this.pos, Reference2ByteArrayMap.this.value, this.pos - 1, tail);
/* 437 */             Reference2ByteArrayMap.this.size--;
/* 438 */             this.pos--;
/* 439 */             Reference2ByteArrayMap.this.key[Reference2ByteArrayMap.this.size] = null;
/*     */           }
/*     */ 
/*     */ 
/*     */ 
/*     */           
/*     */           public void forEachRemaining(Consumer<? super K> action) {
/* 446 */             int max = Reference2ByteArrayMap.this.size;
/* 447 */             while (this.pos < max)
/* 448 */               action.accept((K)Reference2ByteArrayMap.this.key[this.pos++]); 
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
/* 468 */         return (K)Reference2ByteArrayMap.this.key[location];
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
/* 480 */         int max = Reference2ByteArrayMap.this.size;
/* 481 */         while (this.pos < max) {
/* 482 */           action.accept((K)Reference2ByteArrayMap.this.key[this.pos++]);
/*     */         }
/*     */       }
/*     */     }
/*     */ 
/*     */     
/*     */     public ObjectSpliterator<K> spliterator() {
/* 489 */       return new KeySetSpliterator(0, Reference2ByteArrayMap.this.size);
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public void forEach(Consumer<? super K> action) {
/* 496 */       for (int i = 0, max = Reference2ByteArrayMap.this.size; i < max; i++) {
/* 497 */         action.accept((K)Reference2ByteArrayMap.this.key[i]);
/*     */       }
/*     */     }
/*     */ 
/*     */     
/*     */     public int size() {
/* 503 */       return Reference2ByteArrayMap.this.size;
/*     */     }
/*     */ 
/*     */     
/*     */     public void clear() {
/* 508 */       Reference2ByteArrayMap.this.clear();
/*     */     } }
/*     */ 
/*     */ 
/*     */   
/*     */   public ReferenceSet<K> keySet() {
/* 514 */     if (this.keys == null) this.keys = new KeySet(); 
/* 515 */     return this.keys;
/*     */   }
/*     */   
/*     */   private final class ValuesCollection extends AbstractByteCollection { private ValuesCollection() {}
/*     */     
/*     */     public boolean contains(byte v) {
/* 521 */       return Reference2ByteArrayMap.this.containsValue(v);
/*     */     }
/*     */ 
/*     */     
/*     */     public ByteIterator iterator() {
/* 526 */       return new ByteIterator() {
/* 527 */           int pos = 0;
/*     */ 
/*     */           
/*     */           public boolean hasNext() {
/* 531 */             return (this.pos < Reference2ByteArrayMap.this.size);
/*     */           }
/*     */ 
/*     */ 
/*     */           
/*     */           public byte nextByte() {
/* 537 */             if (!hasNext()) throw new NoSuchElementException(); 
/* 538 */             return Reference2ByteArrayMap.this.value[this.pos++];
/*     */           }
/*     */ 
/*     */           
/*     */           public void remove() {
/* 543 */             if (this.pos == 0) throw new IllegalStateException(); 
/* 544 */             int tail = Reference2ByteArrayMap.this.size - this.pos;
/* 545 */             System.arraycopy(Reference2ByteArrayMap.this.key, this.pos, Reference2ByteArrayMap.this.key, this.pos - 1, tail);
/* 546 */             System.arraycopy(Reference2ByteArrayMap.this.value, this.pos, Reference2ByteArrayMap.this.value, this.pos - 1, tail);
/* 547 */             Reference2ByteArrayMap.this.size--;
/* 548 */             this.pos--;
/* 549 */             Reference2ByteArrayMap.this.key[Reference2ByteArrayMap.this.size] = null;
/*     */           }
/*     */ 
/*     */ 
/*     */ 
/*     */           
/*     */           public void forEachRemaining(ByteConsumer action) {
/* 556 */             int max = Reference2ByteArrayMap.this.size;
/* 557 */             while (this.pos < max)
/* 558 */               action.accept(Reference2ByteArrayMap.this.value[this.pos++]); 
/*     */           }
/*     */         };
/*     */     }
/*     */     
/*     */     final class ValuesSpliterator
/*     */       extends ByteSpliterators.EarlyBindingSizeIndexBasedSpliterator
/*     */       implements ByteSpliterator {
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
/*     */       protected final byte get(int location) {
/* 578 */         return Reference2ByteArrayMap.this.value[location];
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
/*     */       public void forEachRemaining(ByteConsumer action) {
/* 590 */         int max = Reference2ByteArrayMap.this.size;
/* 591 */         while (this.pos < max) {
/* 592 */           action.accept(Reference2ByteArrayMap.this.value[this.pos++]);
/*     */         }
/*     */       }
/*     */     }
/*     */ 
/*     */     
/*     */     public ByteSpliterator spliterator() {
/* 599 */       return new ValuesSpliterator(0, Reference2ByteArrayMap.this.size);
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public void forEach(ByteConsumer action) {
/* 606 */       for (int i = 0, max = Reference2ByteArrayMap.this.size; i < max; i++) {
/* 607 */         action.accept(Reference2ByteArrayMap.this.value[i]);
/*     */       }
/*     */     }
/*     */ 
/*     */     
/*     */     public int size() {
/* 613 */       return Reference2ByteArrayMap.this.size;
/*     */     }
/*     */ 
/*     */     
/*     */     public void clear() {
/* 618 */       Reference2ByteArrayMap.this.clear();
/*     */     } }
/*     */ 
/*     */ 
/*     */   
/*     */   public ByteCollection values() {
/* 624 */     if (this.values == null) this.values = (ByteCollection)new ValuesCollection(); 
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
/*     */   public Reference2ByteArrayMap<K> clone() {
/*     */     Reference2ByteArrayMap<K> c;
/*     */     try {
/* 642 */       c = (Reference2ByteArrayMap<K>)super.clone();
/* 643 */     } catch (CloneNotSupportedException cantHappen) {
/* 644 */       throw new InternalError();
/*     */     } 
/* 646 */     c.key = (Object[])this.key.clone();
/* 647 */     c.value = (byte[])this.value.clone();
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
/* 658 */       s.writeByte(this.value[i]);
/*     */     } 
/*     */   }
/*     */   
/*     */   private void readObject(ObjectInputStream s) throws IOException, ClassNotFoundException {
/* 663 */     s.defaultReadObject();
/* 664 */     this.key = new Object[this.size];
/* 665 */     this.value = new byte[this.size];
/* 666 */     for (int i = 0; i < this.size; i++) {
/* 667 */       this.key[i] = s.readObject();
/* 668 */       this.value[i] = s.readByte();
/*     */     } 
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\i\\unimi\dsi\fastutil\objects\Reference2ByteArrayMap.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */