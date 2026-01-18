/*     */ package it.unimi.dsi.fastutil.floats;
/*     */ 
/*     */ import it.unimi.dsi.fastutil.objects.AbstractObjectCollection;
/*     */ import it.unimi.dsi.fastutil.objects.AbstractObjectSet;
/*     */ import it.unimi.dsi.fastutil.objects.ObjectArrays;
/*     */ import it.unimi.dsi.fastutil.objects.ObjectCollection;
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
/*     */ 
/*     */ 
/*     */ public class Float2ObjectArrayMap<V>
/*     */   extends AbstractFloat2ObjectMap<V>
/*     */   implements Serializable, Cloneable
/*     */ {
/*     */   private static final long serialVersionUID = 1L;
/*     */   protected transient float[] key;
/*     */   protected transient Object[] value;
/*     */   protected int size;
/*     */   protected transient Float2ObjectMap.FastEntrySet<V> entries;
/*     */   protected transient FloatSet keys;
/*     */   protected transient ObjectCollection<V> values;
/*     */   
/*     */   public Float2ObjectArrayMap(float[] key, Object[] value) {
/*  63 */     this.key = key;
/*  64 */     this.value = value;
/*  65 */     this.size = key.length;
/*  66 */     if (key.length != value.length) throw new IllegalArgumentException("Keys and values have different lengths (" + key.length + ", " + value.length + ")");
/*     */   
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public Float2ObjectArrayMap() {
/*  73 */     this.key = FloatArrays.EMPTY_ARRAY;
/*  74 */     this.value = ObjectArrays.EMPTY_ARRAY;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Float2ObjectArrayMap(int capacity) {
/*  83 */     this.key = new float[capacity];
/*  84 */     this.value = new Object[capacity];
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Float2ObjectArrayMap(Float2ObjectMap<V> m) {
/*  93 */     this(m.size());
/*  94 */     int i = 0;
/*  95 */     for (ObjectIterator<Float2ObjectMap.Entry<V>> objectIterator = m.float2ObjectEntrySet().iterator(); objectIterator.hasNext(); ) { Float2ObjectMap.Entry<V> e = objectIterator.next();
/*  96 */       this.key[i] = e.getFloatKey();
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
/*     */   public Float2ObjectArrayMap(Map<? extends Float, ? extends V> m) {
/* 109 */     this(m.size());
/* 110 */     int i = 0;
/* 111 */     for (Map.Entry<? extends Float, ? extends V> e : m.entrySet()) {
/* 112 */       this.key[i] = ((Float)e.getKey()).floatValue();
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
/*     */   public Float2ObjectArrayMap(float[] key, Object[] value, int size) {
/* 132 */     this.key = key;
/* 133 */     this.value = value;
/* 134 */     this.size = size;
/* 135 */     if (key.length != value.length) throw new IllegalArgumentException("Keys and values have different lengths (" + key.length + ", " + value.length + ")"); 
/* 136 */     if (size > key.length) throw new IllegalArgumentException("The provided size (" + size + ") is larger than or equal to the backing-arrays size (" + key.length + ")"); 
/*     */   }
/*     */   
/*     */   private final class EntrySet
/*     */     extends AbstractObjectSet<Float2ObjectMap.Entry<V>> implements Float2ObjectMap.FastEntrySet<V> {
/*     */     private EntrySet() {}
/*     */     
/*     */     public ObjectIterator<Float2ObjectMap.Entry<V>> iterator() {
/* 144 */       return new ObjectIterator<Float2ObjectMap.Entry<V>>() {
/* 145 */           int curr = -1; int next = 0;
/*     */ 
/*     */           
/*     */           public boolean hasNext() {
/* 149 */             return (this.next < Float2ObjectArrayMap.this.size);
/*     */           }
/*     */ 
/*     */ 
/*     */           
/*     */           public Float2ObjectMap.Entry<V> next() {
/* 155 */             if (!hasNext()) throw new NoSuchElementException(); 
/* 156 */             return new AbstractFloat2ObjectMap.BasicEntry<>(Float2ObjectArrayMap.this.key[this.curr = this.next], (V)Float2ObjectArrayMap.this.value[this.next++]);
/*     */           }
/*     */ 
/*     */           
/*     */           public void remove() {
/* 161 */             if (this.curr == -1) throw new IllegalStateException(); 
/* 162 */             this.curr = -1;
/* 163 */             int tail = Float2ObjectArrayMap.this.size-- - this.next--;
/* 164 */             System.arraycopy(Float2ObjectArrayMap.this.key, this.next + 1, Float2ObjectArrayMap.this.key, this.next, tail);
/* 165 */             System.arraycopy(Float2ObjectArrayMap.this.value, this.next + 1, Float2ObjectArrayMap.this.value, this.next, tail);
/* 166 */             Float2ObjectArrayMap.this.value[Float2ObjectArrayMap.this.size] = null;
/*     */           }
/*     */ 
/*     */ 
/*     */ 
/*     */           
/*     */           public void forEachRemaining(Consumer<? super Float2ObjectMap.Entry<V>> action) {
/* 173 */             int max = Float2ObjectArrayMap.this.size;
/* 174 */             while (this.next < max) {
/* 175 */               action.accept(new AbstractFloat2ObjectMap.BasicEntry<>(Float2ObjectArrayMap.this.key[this.curr = this.next], (V)Float2ObjectArrayMap.this.value[this.next++]));
/*     */             }
/*     */           }
/*     */         };
/*     */     }
/*     */ 
/*     */     
/*     */     public ObjectIterator<Float2ObjectMap.Entry<V>> fastIterator() {
/* 183 */       return new ObjectIterator<Float2ObjectMap.Entry<V>>() {
/* 184 */           int next = 0; int curr = -1;
/* 185 */           final AbstractFloat2ObjectMap.BasicEntry<V> entry = new AbstractFloat2ObjectMap.BasicEntry<>();
/*     */ 
/*     */           
/*     */           public boolean hasNext() {
/* 189 */             return (this.next < Float2ObjectArrayMap.this.size);
/*     */           }
/*     */ 
/*     */ 
/*     */           
/*     */           public Float2ObjectMap.Entry<V> next() {
/* 195 */             if (!hasNext()) throw new NoSuchElementException(); 
/* 196 */             this.entry.key = Float2ObjectArrayMap.this.key[this.curr = this.next];
/* 197 */             this.entry.value = (V)Float2ObjectArrayMap.this.value[this.next++];
/* 198 */             return this.entry;
/*     */           }
/*     */ 
/*     */           
/*     */           public void remove() {
/* 203 */             if (this.curr == -1) throw new IllegalStateException(); 
/* 204 */             this.curr = -1;
/* 205 */             int tail = Float2ObjectArrayMap.this.size-- - this.next--;
/* 206 */             System.arraycopy(Float2ObjectArrayMap.this.key, this.next + 1, Float2ObjectArrayMap.this.key, this.next, tail);
/* 207 */             System.arraycopy(Float2ObjectArrayMap.this.value, this.next + 1, Float2ObjectArrayMap.this.value, this.next, tail);
/* 208 */             Float2ObjectArrayMap.this.value[Float2ObjectArrayMap.this.size] = null;
/*     */           }
/*     */ 
/*     */ 
/*     */ 
/*     */           
/*     */           public void forEachRemaining(Consumer<? super Float2ObjectMap.Entry<V>> action) {
/* 215 */             int max = Float2ObjectArrayMap.this.size;
/* 216 */             while (this.next < max) {
/* 217 */               this.entry.key = Float2ObjectArrayMap.this.key[this.curr = this.next];
/* 218 */               this.entry.value = (V)Float2ObjectArrayMap.this.value[this.next++];
/* 219 */               action.accept(this.entry);
/*     */             } 
/*     */           }
/*     */         };
/*     */     }
/*     */     
/*     */     final class EntrySetSpliterator
/*     */       extends ObjectSpliterators.EarlyBindingSizeIndexBasedSpliterator<Float2ObjectMap.Entry<V>>
/*     */       implements ObjectSpliterator<Float2ObjectMap.Entry<V>> {
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
/*     */       protected final Float2ObjectMap.Entry<V> get(int location) {
/* 240 */         return new AbstractFloat2ObjectMap.BasicEntry<>(Float2ObjectArrayMap.this.key[location], (V)Float2ObjectArrayMap.this.value[location]);
/*     */       }
/*     */ 
/*     */       
/*     */       protected final EntrySetSpliterator makeForSplit(int pos, int maxPos) {
/* 245 */         return new EntrySetSpliterator(pos, maxPos);
/*     */       }
/*     */     }
/*     */ 
/*     */     
/*     */     public ObjectSpliterator<Float2ObjectMap.Entry<V>> spliterator() {
/* 251 */       return new EntrySetSpliterator(0, Float2ObjectArrayMap.this.size);
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public void forEach(Consumer<? super Float2ObjectMap.Entry<V>> action) {
/* 259 */       for (int i = 0, max = Float2ObjectArrayMap.this.size; i < max; i++) {
/* 260 */         action.accept(new AbstractFloat2ObjectMap.BasicEntry<>(Float2ObjectArrayMap.this.key[i], (V)Float2ObjectArrayMap.this.value[i]));
/*     */       }
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public void fastForEach(Consumer<? super Float2ObjectMap.Entry<V>> action) {
/* 268 */       AbstractFloat2ObjectMap.BasicEntry<V> entry = new AbstractFloat2ObjectMap.BasicEntry<>();
/*     */       
/* 270 */       for (int i = 0, max = Float2ObjectArrayMap.this.size; i < max; i++) {
/* 271 */         entry.key = Float2ObjectArrayMap.this.key[i];
/* 272 */         entry.value = (V)Float2ObjectArrayMap.this.value[i];
/* 273 */         action.accept(entry);
/*     */       } 
/*     */     }
/*     */ 
/*     */     
/*     */     public int size() {
/* 279 */       return Float2ObjectArrayMap.this.size;
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     public boolean contains(Object o) {
/* 285 */       if (!(o instanceof Map.Entry)) return false; 
/* 286 */       Map.Entry<?, ?> e = (Map.Entry<?, ?>)o;
/* 287 */       if (e.getKey() == null || !(e.getKey() instanceof Float)) return false; 
/* 288 */       float k = ((Float)e.getKey()).floatValue();
/* 289 */       return (Float2ObjectArrayMap.this.containsKey(k) && Objects.equals(Float2ObjectArrayMap.this.get(k), e.getValue()));
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     public boolean remove(Object o) {
/* 295 */       if (!(o instanceof Map.Entry)) return false; 
/* 296 */       Map.Entry<?, ?> e = (Map.Entry<?, ?>)o;
/* 297 */       if (e.getKey() == null || !(e.getKey() instanceof Float)) return false; 
/* 298 */       float k = ((Float)e.getKey()).floatValue();
/* 299 */       V v = (V)e.getValue();
/* 300 */       int oldPos = Float2ObjectArrayMap.this.findKey(k);
/* 301 */       if (oldPos == -1 || !Objects.equals(v, Float2ObjectArrayMap.this.value[oldPos])) return false; 
/* 302 */       int tail = Float2ObjectArrayMap.this.size - oldPos - 1;
/* 303 */       System.arraycopy(Float2ObjectArrayMap.this.key, oldPos + 1, Float2ObjectArrayMap.this.key, oldPos, tail);
/* 304 */       System.arraycopy(Float2ObjectArrayMap.this.value, oldPos + 1, Float2ObjectArrayMap.this.value, oldPos, tail);
/* 305 */       Float2ObjectArrayMap.this.size--;
/* 306 */       Float2ObjectArrayMap.this.value[Float2ObjectArrayMap.this.size] = null;
/* 307 */       return true;
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   public Float2ObjectMap.FastEntrySet<V> float2ObjectEntrySet() {
/* 313 */     if (this.entries == null) this.entries = new EntrySet(); 
/* 314 */     return this.entries;
/*     */   }
/*     */   
/*     */   private int findKey(float k) {
/* 318 */     float[] key = this.key;
/* 319 */     for (int i = this.size; i-- != 0;) { if (Float.floatToIntBits(key[i]) == Float.floatToIntBits(k)) return i;  }
/* 320 */      return -1;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public V get(float k) {
/* 326 */     float[] key = this.key;
/* 327 */     for (int i = this.size; i-- != 0;) { if (Float.floatToIntBits(key[i]) == Float.floatToIntBits(k)) return (V)this.value[i];  }
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
/*     */   public boolean containsKey(float k) {
/* 346 */     return (findKey(k) != -1);
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean containsValue(Object v) {
/* 351 */     for (int i = this.size; i-- != 0;) { if (Objects.equals(this.value[i], v)) return true;  }
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
/*     */   public V put(float k, V v) {
/* 363 */     int oldKey = findKey(k);
/* 364 */     if (oldKey != -1) {
/* 365 */       V oldValue = (V)this.value[oldKey];
/* 366 */       this.value[oldKey] = v;
/* 367 */       return oldValue;
/*     */     } 
/* 369 */     if (this.size == this.key.length) {
/* 370 */       float[] newKey = new float[(this.size == 0) ? 2 : (this.size * 2)];
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
/*     */   public V remove(float k) {
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
/*     */   private final class KeySet extends AbstractFloatSet { private KeySet() {}
/*     */     
/*     */     public boolean contains(float k) {
/* 402 */       return (Float2ObjectArrayMap.this.findKey(k) != -1);
/*     */     }
/*     */ 
/*     */     
/*     */     public boolean remove(float k) {
/* 407 */       int oldPos = Float2ObjectArrayMap.this.findKey(k);
/* 408 */       if (oldPos == -1) return false; 
/* 409 */       int tail = Float2ObjectArrayMap.this.size - oldPos - 1;
/* 410 */       System.arraycopy(Float2ObjectArrayMap.this.key, oldPos + 1, Float2ObjectArrayMap.this.key, oldPos, tail);
/* 411 */       System.arraycopy(Float2ObjectArrayMap.this.value, oldPos + 1, Float2ObjectArrayMap.this.value, oldPos, tail);
/* 412 */       Float2ObjectArrayMap.this.size--;
/* 413 */       Float2ObjectArrayMap.this.value[Float2ObjectArrayMap.this.size] = null;
/* 414 */       return true;
/*     */     }
/*     */ 
/*     */     
/*     */     public FloatIterator iterator() {
/* 419 */       return new FloatIterator() {
/* 420 */           int pos = 0;
/*     */ 
/*     */           
/*     */           public boolean hasNext() {
/* 424 */             return (this.pos < Float2ObjectArrayMap.this.size);
/*     */           }
/*     */ 
/*     */ 
/*     */           
/*     */           public float nextFloat() {
/* 430 */             if (!hasNext()) throw new NoSuchElementException(); 
/* 431 */             return Float2ObjectArrayMap.this.key[this.pos++];
/*     */           }
/*     */ 
/*     */           
/*     */           public void remove() {
/* 436 */             if (this.pos == 0) throw new IllegalStateException(); 
/* 437 */             int tail = Float2ObjectArrayMap.this.size - this.pos;
/* 438 */             System.arraycopy(Float2ObjectArrayMap.this.key, this.pos, Float2ObjectArrayMap.this.key, this.pos - 1, tail);
/* 439 */             System.arraycopy(Float2ObjectArrayMap.this.value, this.pos, Float2ObjectArrayMap.this.value, this.pos - 1, tail);
/* 440 */             Float2ObjectArrayMap.this.size--;
/* 441 */             this.pos--;
/* 442 */             Float2ObjectArrayMap.this.value[Float2ObjectArrayMap.this.size] = null;
/*     */           }
/*     */ 
/*     */ 
/*     */ 
/*     */           
/*     */           public void forEachRemaining(FloatConsumer action) {
/* 449 */             int max = Float2ObjectArrayMap.this.size;
/* 450 */             while (this.pos < max)
/* 451 */               action.accept(Float2ObjectArrayMap.this.key[this.pos++]); 
/*     */           }
/*     */         };
/*     */     }
/*     */     
/*     */     final class KeySetSpliterator
/*     */       extends FloatSpliterators.EarlyBindingSizeIndexBasedSpliterator
/*     */       implements FloatSpliterator {
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
/*     */       protected final float get(int location) {
/* 471 */         return Float2ObjectArrayMap.this.key[location];
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
/*     */       public void forEachRemaining(FloatConsumer action) {
/* 483 */         int max = Float2ObjectArrayMap.this.size;
/* 484 */         while (this.pos < max) {
/* 485 */           action.accept(Float2ObjectArrayMap.this.key[this.pos++]);
/*     */         }
/*     */       }
/*     */     }
/*     */ 
/*     */     
/*     */     public FloatSpliterator spliterator() {
/* 492 */       return new KeySetSpliterator(0, Float2ObjectArrayMap.this.size);
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public void forEach(FloatConsumer action) {
/* 499 */       for (int i = 0, max = Float2ObjectArrayMap.this.size; i < max; i++) {
/* 500 */         action.accept(Float2ObjectArrayMap.this.key[i]);
/*     */       }
/*     */     }
/*     */ 
/*     */     
/*     */     public int size() {
/* 506 */       return Float2ObjectArrayMap.this.size;
/*     */     }
/*     */ 
/*     */     
/*     */     public void clear() {
/* 511 */       Float2ObjectArrayMap.this.clear();
/*     */     } }
/*     */ 
/*     */ 
/*     */   
/*     */   public FloatSet keySet() {
/* 517 */     if (this.keys == null) this.keys = new KeySet(); 
/* 518 */     return this.keys;
/*     */   }
/*     */   
/*     */   private final class ValuesCollection extends AbstractObjectCollection<V> { private ValuesCollection() {}
/*     */     
/*     */     public boolean contains(Object v) {
/* 524 */       return Float2ObjectArrayMap.this.containsValue(v);
/*     */     }
/*     */ 
/*     */     
/*     */     public ObjectIterator<V> iterator() {
/* 529 */       return new ObjectIterator<V>() {
/* 530 */           int pos = 0;
/*     */ 
/*     */           
/*     */           public boolean hasNext() {
/* 534 */             return (this.pos < Float2ObjectArrayMap.this.size);
/*     */           }
/*     */ 
/*     */ 
/*     */           
/*     */           public V next() {
/* 540 */             if (!hasNext()) throw new NoSuchElementException(); 
/* 541 */             return (V)Float2ObjectArrayMap.this.value[this.pos++];
/*     */           }
/*     */ 
/*     */           
/*     */           public void remove() {
/* 546 */             if (this.pos == 0) throw new IllegalStateException(); 
/* 547 */             int tail = Float2ObjectArrayMap.this.size - this.pos;
/* 548 */             System.arraycopy(Float2ObjectArrayMap.this.key, this.pos, Float2ObjectArrayMap.this.key, this.pos - 1, tail);
/* 549 */             System.arraycopy(Float2ObjectArrayMap.this.value, this.pos, Float2ObjectArrayMap.this.value, this.pos - 1, tail);
/* 550 */             Float2ObjectArrayMap.this.size--;
/* 551 */             this.pos--;
/* 552 */             Float2ObjectArrayMap.this.value[Float2ObjectArrayMap.this.size] = null;
/*     */           }
/*     */ 
/*     */ 
/*     */ 
/*     */           
/*     */           public void forEachRemaining(Consumer<? super V> action) {
/* 559 */             int max = Float2ObjectArrayMap.this.size;
/* 560 */             while (this.pos < max)
/* 561 */               action.accept((V)Float2ObjectArrayMap.this.value[this.pos++]); 
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
/* 581 */         return (V)Float2ObjectArrayMap.this.value[location];
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
/* 593 */         int max = Float2ObjectArrayMap.this.size;
/* 594 */         while (this.pos < max) {
/* 595 */           action.accept((V)Float2ObjectArrayMap.this.value[this.pos++]);
/*     */         }
/*     */       }
/*     */     }
/*     */ 
/*     */     
/*     */     public ObjectSpliterator<V> spliterator() {
/* 602 */       return new ValuesSpliterator(0, Float2ObjectArrayMap.this.size);
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public void forEach(Consumer<? super V> action) {
/* 609 */       for (int i = 0, max = Float2ObjectArrayMap.this.size; i < max; i++) {
/* 610 */         action.accept((V)Float2ObjectArrayMap.this.value[i]);
/*     */       }
/*     */     }
/*     */ 
/*     */     
/*     */     public int size() {
/* 616 */       return Float2ObjectArrayMap.this.size;
/*     */     }
/*     */ 
/*     */     
/*     */     public void clear() {
/* 621 */       Float2ObjectArrayMap.this.clear();
/*     */     } }
/*     */ 
/*     */ 
/*     */   
/*     */   public ObjectCollection<V> values() {
/* 627 */     if (this.values == null) this.values = (ObjectCollection<V>)new ValuesCollection(); 
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
/*     */   public Float2ObjectArrayMap<V> clone() {
/*     */     Float2ObjectArrayMap<V> c;
/*     */     try {
/* 645 */       c = (Float2ObjectArrayMap<V>)super.clone();
/* 646 */     } catch (CloneNotSupportedException cantHappen) {
/* 647 */       throw new InternalError();
/*     */     } 
/* 649 */     c.key = (float[])this.key.clone();
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
/* 660 */       s.writeFloat(this.key[i]);
/* 661 */       s.writeObject(this.value[i]);
/*     */     } 
/*     */   }
/*     */   
/*     */   private void readObject(ObjectInputStream s) throws IOException, ClassNotFoundException {
/* 666 */     s.defaultReadObject();
/* 667 */     this.key = new float[this.size];
/* 668 */     this.value = new Object[this.size];
/* 669 */     for (int i = 0; i < this.size; i++) {
/* 670 */       this.key[i] = s.readFloat();
/* 671 */       this.value[i] = s.readObject();
/*     */     } 
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\i\\unimi\dsi\fastutil\floats\Float2ObjectArrayMap.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */