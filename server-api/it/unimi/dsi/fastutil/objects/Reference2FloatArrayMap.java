/*     */ package it.unimi.dsi.fastutil.objects;
/*     */ 
/*     */ import it.unimi.dsi.fastutil.floats.AbstractFloatCollection;
/*     */ import it.unimi.dsi.fastutil.floats.FloatArrays;
/*     */ import it.unimi.dsi.fastutil.floats.FloatCollection;
/*     */ import it.unimi.dsi.fastutil.floats.FloatConsumer;
/*     */ import it.unimi.dsi.fastutil.floats.FloatIterator;
/*     */ import it.unimi.dsi.fastutil.floats.FloatSpliterator;
/*     */ import it.unimi.dsi.fastutil.floats.FloatSpliterators;
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
/*     */ public class Reference2FloatArrayMap<K>
/*     */   extends AbstractReference2FloatMap<K>
/*     */   implements Serializable, Cloneable
/*     */ {
/*     */   private static final long serialVersionUID = 1L;
/*     */   protected transient Object[] key;
/*     */   protected transient float[] value;
/*     */   protected int size;
/*     */   protected transient Reference2FloatMap.FastEntrySet<K> entries;
/*     */   protected transient ReferenceSet<K> keys;
/*     */   protected transient FloatCollection values;
/*     */   
/*     */   public Reference2FloatArrayMap(Object[] key, float[] value) {
/*  60 */     this.key = key;
/*  61 */     this.value = value;
/*  62 */     this.size = key.length;
/*  63 */     if (key.length != value.length) throw new IllegalArgumentException("Keys and values have different lengths (" + key.length + ", " + value.length + ")");
/*     */   
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public Reference2FloatArrayMap() {
/*  70 */     this.key = ObjectArrays.EMPTY_ARRAY;
/*  71 */     this.value = FloatArrays.EMPTY_ARRAY;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Reference2FloatArrayMap(int capacity) {
/*  80 */     this.key = new Object[capacity];
/*  81 */     this.value = new float[capacity];
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Reference2FloatArrayMap(Reference2FloatMap<K> m) {
/*  90 */     this(m.size());
/*  91 */     int i = 0;
/*  92 */     for (ObjectIterator<Reference2FloatMap.Entry<K>> objectIterator = m.reference2FloatEntrySet().iterator(); objectIterator.hasNext(); ) { Reference2FloatMap.Entry<K> e = objectIterator.next();
/*  93 */       this.key[i] = e.getKey();
/*  94 */       this.value[i] = e.getFloatValue();
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
/*     */   public Reference2FloatArrayMap(Map<? extends K, ? extends Float> m) {
/* 106 */     this(m.size());
/* 107 */     int i = 0;
/* 108 */     for (Map.Entry<? extends K, ? extends Float> e : m.entrySet()) {
/* 109 */       this.key[i] = e.getKey();
/* 110 */       this.value[i] = ((Float)e.getValue()).floatValue();
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
/*     */   public Reference2FloatArrayMap(Object[] key, float[] value, int size) {
/* 129 */     this.key = key;
/* 130 */     this.value = value;
/* 131 */     this.size = size;
/* 132 */     if (key.length != value.length) throw new IllegalArgumentException("Keys and values have different lengths (" + key.length + ", " + value.length + ")"); 
/* 133 */     if (size > key.length) throw new IllegalArgumentException("The provided size (" + size + ") is larger than or equal to the backing-arrays size (" + key.length + ")"); 
/*     */   }
/*     */   
/*     */   private final class EntrySet
/*     */     extends AbstractObjectSet<Reference2FloatMap.Entry<K>> implements Reference2FloatMap.FastEntrySet<K> {
/*     */     private EntrySet() {}
/*     */     
/*     */     public ObjectIterator<Reference2FloatMap.Entry<K>> iterator() {
/* 141 */       return (ObjectIterator)new ObjectIterator<Reference2FloatMap.Entry<Reference2FloatMap.Entry<K>>>() {
/* 142 */           int curr = -1; int next = 0;
/*     */ 
/*     */           
/*     */           public boolean hasNext() {
/* 146 */             return (this.next < Reference2FloatArrayMap.this.size);
/*     */           }
/*     */ 
/*     */ 
/*     */           
/*     */           public Reference2FloatMap.Entry<K> next() {
/* 152 */             if (!hasNext()) throw new NoSuchElementException(); 
/* 153 */             return new AbstractReference2FloatMap.BasicEntry<>((K)Reference2FloatArrayMap.this.key[this.curr = this.next], Reference2FloatArrayMap.this.value[this.next++]);
/*     */           }
/*     */ 
/*     */           
/*     */           public void remove() {
/* 158 */             if (this.curr == -1) throw new IllegalStateException(); 
/* 159 */             this.curr = -1;
/* 160 */             int tail = Reference2FloatArrayMap.this.size-- - this.next--;
/* 161 */             System.arraycopy(Reference2FloatArrayMap.this.key, this.next + 1, Reference2FloatArrayMap.this.key, this.next, tail);
/* 162 */             System.arraycopy(Reference2FloatArrayMap.this.value, this.next + 1, Reference2FloatArrayMap.this.value, this.next, tail);
/* 163 */             Reference2FloatArrayMap.this.key[Reference2FloatArrayMap.this.size] = null;
/*     */           }
/*     */ 
/*     */ 
/*     */ 
/*     */           
/*     */           public void forEachRemaining(Consumer<? super Reference2FloatMap.Entry<K>> action) {
/* 170 */             int max = Reference2FloatArrayMap.this.size;
/* 171 */             while (this.next < max) {
/* 172 */               action.accept(new AbstractReference2FloatMap.BasicEntry<>((K)Reference2FloatArrayMap.this.key[this.curr = this.next], Reference2FloatArrayMap.this.value[this.next++]));
/*     */             }
/*     */           }
/*     */         };
/*     */     }
/*     */ 
/*     */     
/*     */     public ObjectIterator<Reference2FloatMap.Entry<K>> fastIterator() {
/* 180 */       return (ObjectIterator)new ObjectIterator<Reference2FloatMap.Entry<Reference2FloatMap.Entry<K>>>() {
/* 181 */           int next = 0; int curr = -1;
/* 182 */           final AbstractReference2FloatMap.BasicEntry<K> entry = new AbstractReference2FloatMap.BasicEntry<>();
/*     */ 
/*     */           
/*     */           public boolean hasNext() {
/* 186 */             return (this.next < Reference2FloatArrayMap.this.size);
/*     */           }
/*     */ 
/*     */ 
/*     */           
/*     */           public Reference2FloatMap.Entry<K> next() {
/* 192 */             if (!hasNext()) throw new NoSuchElementException(); 
/* 193 */             this.entry.key = (K)Reference2FloatArrayMap.this.key[this.curr = this.next];
/* 194 */             this.entry.value = Reference2FloatArrayMap.this.value[this.next++];
/* 195 */             return this.entry;
/*     */           }
/*     */ 
/*     */           
/*     */           public void remove() {
/* 200 */             if (this.curr == -1) throw new IllegalStateException(); 
/* 201 */             this.curr = -1;
/* 202 */             int tail = Reference2FloatArrayMap.this.size-- - this.next--;
/* 203 */             System.arraycopy(Reference2FloatArrayMap.this.key, this.next + 1, Reference2FloatArrayMap.this.key, this.next, tail);
/* 204 */             System.arraycopy(Reference2FloatArrayMap.this.value, this.next + 1, Reference2FloatArrayMap.this.value, this.next, tail);
/* 205 */             Reference2FloatArrayMap.this.key[Reference2FloatArrayMap.this.size] = null;
/*     */           }
/*     */ 
/*     */ 
/*     */ 
/*     */           
/*     */           public void forEachRemaining(Consumer<? super Reference2FloatMap.Entry<K>> action) {
/* 212 */             int max = Reference2FloatArrayMap.this.size;
/* 213 */             while (this.next < max) {
/* 214 */               this.entry.key = (K)Reference2FloatArrayMap.this.key[this.curr = this.next];
/* 215 */               this.entry.value = Reference2FloatArrayMap.this.value[this.next++];
/* 216 */               action.accept(this.entry);
/*     */             } 
/*     */           }
/*     */         };
/*     */     }
/*     */     
/*     */     final class EntrySetSpliterator
/*     */       extends ObjectSpliterators.EarlyBindingSizeIndexBasedSpliterator<Reference2FloatMap.Entry<K>>
/*     */       implements ObjectSpliterator<Reference2FloatMap.Entry<K>> {
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
/*     */       protected final Reference2FloatMap.Entry<K> get(int location) {
/* 237 */         return new AbstractReference2FloatMap.BasicEntry<>((K)Reference2FloatArrayMap.this.key[location], Reference2FloatArrayMap.this.value[location]);
/*     */       }
/*     */ 
/*     */       
/*     */       protected final EntrySetSpliterator makeForSplit(int pos, int maxPos) {
/* 242 */         return new EntrySetSpliterator(pos, maxPos);
/*     */       }
/*     */     }
/*     */ 
/*     */     
/*     */     public ObjectSpliterator<Reference2FloatMap.Entry<K>> spliterator() {
/* 248 */       return new EntrySetSpliterator(0, Reference2FloatArrayMap.this.size);
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public void forEach(Consumer<? super Reference2FloatMap.Entry<K>> action) {
/* 256 */       for (int i = 0, max = Reference2FloatArrayMap.this.size; i < max; i++) {
/* 257 */         action.accept(new AbstractReference2FloatMap.BasicEntry<>((K)Reference2FloatArrayMap.this.key[i], Reference2FloatArrayMap.this.value[i]));
/*     */       }
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public void fastForEach(Consumer<? super Reference2FloatMap.Entry<K>> action) {
/* 265 */       AbstractReference2FloatMap.BasicEntry<K> entry = new AbstractReference2FloatMap.BasicEntry<>();
/*     */       
/* 267 */       for (int i = 0, max = Reference2FloatArrayMap.this.size; i < max; i++) {
/* 268 */         entry.key = (K)Reference2FloatArrayMap.this.key[i];
/* 269 */         entry.value = Reference2FloatArrayMap.this.value[i];
/* 270 */         action.accept(entry);
/*     */       } 
/*     */     }
/*     */ 
/*     */     
/*     */     public int size() {
/* 276 */       return Reference2FloatArrayMap.this.size;
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     public boolean contains(Object o) {
/* 282 */       if (!(o instanceof Map.Entry)) return false; 
/* 283 */       Map.Entry<?, ?> e = (Map.Entry<?, ?>)o;
/* 284 */       if (e.getValue() == null || !(e.getValue() instanceof Float)) return false; 
/* 285 */       K k = (K)e.getKey();
/* 286 */       return (Reference2FloatArrayMap.this.containsKey(k) && Float.floatToIntBits(Reference2FloatArrayMap.this.getFloat(k)) == Float.floatToIntBits(((Float)e.getValue()).floatValue()));
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     public boolean remove(Object o) {
/* 292 */       if (!(o instanceof Map.Entry)) return false; 
/* 293 */       Map.Entry<?, ?> e = (Map.Entry<?, ?>)o;
/* 294 */       if (e.getValue() == null || !(e.getValue() instanceof Float)) return false; 
/* 295 */       K k = (K)e.getKey();
/* 296 */       float v = ((Float)e.getValue()).floatValue();
/* 297 */       int oldPos = Reference2FloatArrayMap.this.findKey(k);
/* 298 */       if (oldPos == -1 || Float.floatToIntBits(v) != Float.floatToIntBits(Reference2FloatArrayMap.this.value[oldPos])) return false; 
/* 299 */       int tail = Reference2FloatArrayMap.this.size - oldPos - 1;
/* 300 */       System.arraycopy(Reference2FloatArrayMap.this.key, oldPos + 1, Reference2FloatArrayMap.this.key, oldPos, tail);
/* 301 */       System.arraycopy(Reference2FloatArrayMap.this.value, oldPos + 1, Reference2FloatArrayMap.this.value, oldPos, tail);
/* 302 */       Reference2FloatArrayMap.this.size--;
/* 303 */       Reference2FloatArrayMap.this.key[Reference2FloatArrayMap.this.size] = null;
/* 304 */       return true;
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   public Reference2FloatMap.FastEntrySet<K> reference2FloatEntrySet() {
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
/*     */   public float getFloat(Object k) {
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
/*     */   public boolean containsValue(float v) {
/* 348 */     for (int i = this.size; i-- != 0;) { if (Float.floatToIntBits(this.value[i]) == Float.floatToIntBits(v)) return true;  }
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
/*     */   public float put(K k, float v) {
/* 360 */     int oldKey = findKey(k);
/* 361 */     if (oldKey != -1) {
/* 362 */       float oldValue = this.value[oldKey];
/* 363 */       this.value[oldKey] = v;
/* 364 */       return oldValue;
/*     */     } 
/* 366 */     if (this.size == this.key.length) {
/* 367 */       Object[] newKey = new Object[(this.size == 0) ? 2 : (this.size * 2)];
/* 368 */       float[] newValue = new float[(this.size == 0) ? 2 : (this.size * 2)];
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
/*     */   public float removeFloat(Object k) {
/* 385 */     int oldPos = findKey(k);
/* 386 */     if (oldPos == -1) return this.defRetValue; 
/* 387 */     float oldValue = this.value[oldPos];
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
/* 399 */       return (Reference2FloatArrayMap.this.findKey(k) != -1);
/*     */     }
/*     */ 
/*     */     
/*     */     public boolean remove(Object k) {
/* 404 */       int oldPos = Reference2FloatArrayMap.this.findKey(k);
/* 405 */       if (oldPos == -1) return false; 
/* 406 */       int tail = Reference2FloatArrayMap.this.size - oldPos - 1;
/* 407 */       System.arraycopy(Reference2FloatArrayMap.this.key, oldPos + 1, Reference2FloatArrayMap.this.key, oldPos, tail);
/* 408 */       System.arraycopy(Reference2FloatArrayMap.this.value, oldPos + 1, Reference2FloatArrayMap.this.value, oldPos, tail);
/* 409 */       Reference2FloatArrayMap.this.size--;
/* 410 */       Reference2FloatArrayMap.this.key[Reference2FloatArrayMap.this.size] = null;
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
/* 421 */             return (this.pos < Reference2FloatArrayMap.this.size);
/*     */           }
/*     */ 
/*     */ 
/*     */           
/*     */           public K next() {
/* 427 */             if (!hasNext()) throw new NoSuchElementException(); 
/* 428 */             return (K)Reference2FloatArrayMap.this.key[this.pos++];
/*     */           }
/*     */ 
/*     */           
/*     */           public void remove() {
/* 433 */             if (this.pos == 0) throw new IllegalStateException(); 
/* 434 */             int tail = Reference2FloatArrayMap.this.size - this.pos;
/* 435 */             System.arraycopy(Reference2FloatArrayMap.this.key, this.pos, Reference2FloatArrayMap.this.key, this.pos - 1, tail);
/* 436 */             System.arraycopy(Reference2FloatArrayMap.this.value, this.pos, Reference2FloatArrayMap.this.value, this.pos - 1, tail);
/* 437 */             Reference2FloatArrayMap.this.size--;
/* 438 */             this.pos--;
/* 439 */             Reference2FloatArrayMap.this.key[Reference2FloatArrayMap.this.size] = null;
/*     */           }
/*     */ 
/*     */ 
/*     */ 
/*     */           
/*     */           public void forEachRemaining(Consumer<? super K> action) {
/* 446 */             int max = Reference2FloatArrayMap.this.size;
/* 447 */             while (this.pos < max)
/* 448 */               action.accept((K)Reference2FloatArrayMap.this.key[this.pos++]); 
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
/* 468 */         return (K)Reference2FloatArrayMap.this.key[location];
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
/* 480 */         int max = Reference2FloatArrayMap.this.size;
/* 481 */         while (this.pos < max) {
/* 482 */           action.accept((K)Reference2FloatArrayMap.this.key[this.pos++]);
/*     */         }
/*     */       }
/*     */     }
/*     */ 
/*     */     
/*     */     public ObjectSpliterator<K> spliterator() {
/* 489 */       return new KeySetSpliterator(0, Reference2FloatArrayMap.this.size);
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public void forEach(Consumer<? super K> action) {
/* 496 */       for (int i = 0, max = Reference2FloatArrayMap.this.size; i < max; i++) {
/* 497 */         action.accept((K)Reference2FloatArrayMap.this.key[i]);
/*     */       }
/*     */     }
/*     */ 
/*     */     
/*     */     public int size() {
/* 503 */       return Reference2FloatArrayMap.this.size;
/*     */     }
/*     */ 
/*     */     
/*     */     public void clear() {
/* 508 */       Reference2FloatArrayMap.this.clear();
/*     */     } }
/*     */ 
/*     */ 
/*     */   
/*     */   public ReferenceSet<K> keySet() {
/* 514 */     if (this.keys == null) this.keys = new KeySet(); 
/* 515 */     return this.keys;
/*     */   }
/*     */   
/*     */   private final class ValuesCollection extends AbstractFloatCollection { private ValuesCollection() {}
/*     */     
/*     */     public boolean contains(float v) {
/* 521 */       return Reference2FloatArrayMap.this.containsValue(v);
/*     */     }
/*     */ 
/*     */     
/*     */     public FloatIterator iterator() {
/* 526 */       return new FloatIterator() {
/* 527 */           int pos = 0;
/*     */ 
/*     */           
/*     */           public boolean hasNext() {
/* 531 */             return (this.pos < Reference2FloatArrayMap.this.size);
/*     */           }
/*     */ 
/*     */ 
/*     */           
/*     */           public float nextFloat() {
/* 537 */             if (!hasNext()) throw new NoSuchElementException(); 
/* 538 */             return Reference2FloatArrayMap.this.value[this.pos++];
/*     */           }
/*     */ 
/*     */           
/*     */           public void remove() {
/* 543 */             if (this.pos == 0) throw new IllegalStateException(); 
/* 544 */             int tail = Reference2FloatArrayMap.this.size - this.pos;
/* 545 */             System.arraycopy(Reference2FloatArrayMap.this.key, this.pos, Reference2FloatArrayMap.this.key, this.pos - 1, tail);
/* 546 */             System.arraycopy(Reference2FloatArrayMap.this.value, this.pos, Reference2FloatArrayMap.this.value, this.pos - 1, tail);
/* 547 */             Reference2FloatArrayMap.this.size--;
/* 548 */             this.pos--;
/* 549 */             Reference2FloatArrayMap.this.key[Reference2FloatArrayMap.this.size] = null;
/*     */           }
/*     */ 
/*     */ 
/*     */ 
/*     */           
/*     */           public void forEachRemaining(FloatConsumer action) {
/* 556 */             int max = Reference2FloatArrayMap.this.size;
/* 557 */             while (this.pos < max)
/* 558 */               action.accept(Reference2FloatArrayMap.this.value[this.pos++]); 
/*     */           }
/*     */         };
/*     */     }
/*     */     
/*     */     final class ValuesSpliterator
/*     */       extends FloatSpliterators.EarlyBindingSizeIndexBasedSpliterator
/*     */       implements FloatSpliterator {
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
/*     */       protected final float get(int location) {
/* 578 */         return Reference2FloatArrayMap.this.value[location];
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
/*     */       public void forEachRemaining(FloatConsumer action) {
/* 590 */         int max = Reference2FloatArrayMap.this.size;
/* 591 */         while (this.pos < max) {
/* 592 */           action.accept(Reference2FloatArrayMap.this.value[this.pos++]);
/*     */         }
/*     */       }
/*     */     }
/*     */ 
/*     */     
/*     */     public FloatSpliterator spliterator() {
/* 599 */       return new ValuesSpliterator(0, Reference2FloatArrayMap.this.size);
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public void forEach(FloatConsumer action) {
/* 606 */       for (int i = 0, max = Reference2FloatArrayMap.this.size; i < max; i++) {
/* 607 */         action.accept(Reference2FloatArrayMap.this.value[i]);
/*     */       }
/*     */     }
/*     */ 
/*     */     
/*     */     public int size() {
/* 613 */       return Reference2FloatArrayMap.this.size;
/*     */     }
/*     */ 
/*     */     
/*     */     public void clear() {
/* 618 */       Reference2FloatArrayMap.this.clear();
/*     */     } }
/*     */ 
/*     */ 
/*     */   
/*     */   public FloatCollection values() {
/* 624 */     if (this.values == null) this.values = (FloatCollection)new ValuesCollection(); 
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
/*     */   public Reference2FloatArrayMap<K> clone() {
/*     */     Reference2FloatArrayMap<K> c;
/*     */     try {
/* 642 */       c = (Reference2FloatArrayMap<K>)super.clone();
/* 643 */     } catch (CloneNotSupportedException cantHappen) {
/* 644 */       throw new InternalError();
/*     */     } 
/* 646 */     c.key = (Object[])this.key.clone();
/* 647 */     c.value = (float[])this.value.clone();
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
/* 658 */       s.writeFloat(this.value[i]);
/*     */     } 
/*     */   }
/*     */   
/*     */   private void readObject(ObjectInputStream s) throws IOException, ClassNotFoundException {
/* 663 */     s.defaultReadObject();
/* 664 */     this.key = new Object[this.size];
/* 665 */     this.value = new float[this.size];
/* 666 */     for (int i = 0; i < this.size; i++) {
/* 667 */       this.key[i] = s.readObject();
/* 668 */       this.value[i] = s.readFloat();
/*     */     } 
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\i\\unimi\dsi\fastutil\objects\Reference2FloatArrayMap.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */