/*     */ package io.netty.util.collection;
/*     */ 
/*     */ import io.netty.util.internal.MathUtil;
/*     */ import java.util.AbstractCollection;
/*     */ import java.util.AbstractSet;
/*     */ import java.util.Arrays;
/*     */ import java.util.Collection;
/*     */ import java.util.Iterator;
/*     */ import java.util.Map;
/*     */ import java.util.NoSuchElementException;
/*     */ import java.util.Set;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class LongObjectHashMap<V>
/*     */   implements LongObjectMap<V>
/*     */ {
/*     */   public static final int DEFAULT_CAPACITY = 8;
/*     */   public static final float DEFAULT_LOAD_FACTOR = 0.5F;
/*  49 */   private static final Object NULL_VALUE = new Object();
/*     */   
/*     */   private int maxSize;
/*     */   
/*     */   private final float loadFactor;
/*     */   
/*     */   private long[] keys;
/*     */   
/*     */   private V[] values;
/*     */   
/*     */   private int size;
/*     */   
/*     */   private int mask;
/*  62 */   private final Set<Long> keySet = new KeySet();
/*  63 */   private final Set<Map.Entry<Long, V>> entrySet = new EntrySet();
/*  64 */   private final Iterable<LongObjectMap.PrimitiveEntry<V>> entries = new Iterable<LongObjectMap.PrimitiveEntry<V>>()
/*     */     {
/*     */       public Iterator<LongObjectMap.PrimitiveEntry<V>> iterator() {
/*  67 */         return new LongObjectHashMap.PrimitiveIterator();
/*     */       }
/*     */     };
/*     */   
/*     */   public LongObjectHashMap() {
/*  72 */     this(8, 0.5F);
/*     */   }
/*     */   
/*     */   public LongObjectHashMap(int initialCapacity) {
/*  76 */     this(initialCapacity, 0.5F);
/*     */   }
/*     */   
/*     */   public LongObjectHashMap(int initialCapacity, float loadFactor) {
/*  80 */     if (loadFactor <= 0.0F || loadFactor > 1.0F)
/*     */     {
/*     */       
/*  83 */       throw new IllegalArgumentException("loadFactor must be > 0 and <= 1");
/*     */     }
/*     */     
/*  86 */     this.loadFactor = loadFactor;
/*     */ 
/*     */     
/*  89 */     int capacity = MathUtil.safeFindNextPositivePowerOfTwo(initialCapacity);
/*  90 */     this.mask = capacity - 1;
/*     */ 
/*     */     
/*  93 */     this.keys = new long[capacity];
/*     */     
/*  95 */     V[] temp = (V[])new Object[capacity];
/*  96 */     this.values = temp;
/*     */ 
/*     */     
/*  99 */     this.maxSize = calcMaxSize(capacity);
/*     */   }
/*     */   
/*     */   private static <T> T toExternal(T value) {
/* 103 */     assert value != null : "null is not a legitimate internal value. Concurrent Modification?";
/* 104 */     return (value == NULL_VALUE) ? null : value;
/*     */   }
/*     */ 
/*     */   
/*     */   private static <T> T toInternal(T value) {
/* 109 */     return (value == null) ? (T)NULL_VALUE : value;
/*     */   }
/*     */ 
/*     */   
/*     */   public V get(long key) {
/* 114 */     int index = indexOf(key);
/* 115 */     return (index == -1) ? null : toExternal(this.values[index]);
/*     */   }
/*     */ 
/*     */   
/*     */   public V put(long key, V value) {
/* 120 */     int startIndex = hashIndex(key);
/* 121 */     int index = startIndex;
/*     */     
/*     */     do {
/* 124 */       if (this.values[index] == null) {
/*     */         
/* 126 */         this.keys[index] = key;
/* 127 */         this.values[index] = toInternal(value);
/* 128 */         growSize();
/* 129 */         return null;
/*     */       } 
/* 131 */       if (this.keys[index] == key)
/*     */       {
/* 133 */         V previousValue = this.values[index];
/* 134 */         this.values[index] = toInternal(value);
/* 135 */         return toExternal(previousValue);
/*     */       }
/*     */     
/*     */     }
/* 139 */     while ((index = probeNext(index)) != startIndex);
/*     */     
/* 141 */     throw new IllegalStateException("Unable to insert");
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void putAll(Map<? extends Long, ? extends V> sourceMap) {
/* 148 */     if (sourceMap instanceof LongObjectHashMap) {
/*     */ 
/*     */       
/* 151 */       LongObjectHashMap<V> source = (LongObjectHashMap)sourceMap;
/* 152 */       for (int i = 0; i < source.values.length; i++) {
/* 153 */         V sourceValue = source.values[i];
/* 154 */         if (sourceValue != null) {
/* 155 */           put(source.keys[i], sourceValue);
/*     */         }
/*     */       } 
/*     */       
/*     */       return;
/*     */     } 
/*     */     
/* 162 */     for (Map.Entry<? extends Long, ? extends V> entry : sourceMap.entrySet()) {
/* 163 */       put(entry.getKey(), entry.getValue());
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   public V remove(long key) {
/* 169 */     int index = indexOf(key);
/* 170 */     if (index == -1) {
/* 171 */       return null;
/*     */     }
/*     */     
/* 174 */     V prev = this.values[index];
/* 175 */     removeAt(index);
/* 176 */     return toExternal(prev);
/*     */   }
/*     */ 
/*     */   
/*     */   public int size() {
/* 181 */     return this.size;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isEmpty() {
/* 186 */     return (this.size == 0);
/*     */   }
/*     */ 
/*     */   
/*     */   public void clear() {
/* 191 */     Arrays.fill(this.keys, 0L);
/* 192 */     Arrays.fill((Object[])this.values, (Object)null);
/* 193 */     this.size = 0;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean containsKey(long key) {
/* 198 */     return (indexOf(key) >= 0);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean containsValue(Object value) {
/* 204 */     V v1 = toInternal((V)value);
/* 205 */     for (V v2 : this.values) {
/*     */       
/* 207 */       if (v2 != null && v2.equals(v1)) {
/* 208 */         return true;
/*     */       }
/*     */     } 
/* 211 */     return false;
/*     */   }
/*     */ 
/*     */   
/*     */   public Iterable<LongObjectMap.PrimitiveEntry<V>> entries() {
/* 216 */     return this.entries;
/*     */   }
/*     */ 
/*     */   
/*     */   public Collection<V> values() {
/* 221 */     return new AbstractCollection<V>()
/*     */       {
/*     */         public Iterator<V> iterator() {
/* 224 */           return new Iterator<V>() {
/* 225 */               final LongObjectHashMap<V>.PrimitiveIterator iter = new LongObjectHashMap.PrimitiveIterator();
/*     */ 
/*     */               
/*     */               public boolean hasNext() {
/* 229 */                 return this.iter.hasNext();
/*     */               }
/*     */ 
/*     */               
/*     */               public V next() {
/* 234 */                 return this.iter.next().value();
/*     */               }
/*     */ 
/*     */               
/*     */               public void remove() {
/* 239 */                 this.iter.remove();
/*     */               }
/*     */             };
/*     */         }
/*     */ 
/*     */         
/*     */         public int size() {
/* 246 */           return LongObjectHashMap.this.size;
/*     */         }
/*     */       };
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int hashCode() {
/* 256 */     int hash = this.size;
/* 257 */     for (long key : this.keys)
/*     */     {
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 265 */       hash ^= hashCode(key);
/*     */     }
/* 267 */     return hash;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean equals(Object obj) {
/* 272 */     if (this == obj) {
/* 273 */       return true;
/*     */     }
/* 275 */     if (!(obj instanceof LongObjectMap)) {
/* 276 */       return false;
/*     */     }
/*     */     
/* 279 */     LongObjectMap other = (LongObjectMap)obj;
/* 280 */     if (this.size != other.size()) {
/* 281 */       return false;
/*     */     }
/* 283 */     for (int i = 0; i < this.values.length; i++) {
/* 284 */       V value = this.values[i];
/* 285 */       if (value != null) {
/* 286 */         long key = this.keys[i];
/* 287 */         Object otherValue = other.get(key);
/* 288 */         if (value == NULL_VALUE) {
/* 289 */           if (otherValue != null) {
/* 290 */             return false;
/*     */           }
/* 292 */         } else if (!value.equals(otherValue)) {
/* 293 */           return false;
/*     */         } 
/*     */       } 
/*     */     } 
/* 297 */     return true;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean containsKey(Object key) {
/* 302 */     return containsKey(objectToKey(key));
/*     */   }
/*     */ 
/*     */   
/*     */   public V get(Object key) {
/* 307 */     return get(objectToKey(key));
/*     */   }
/*     */ 
/*     */   
/*     */   public V put(Long key, V value) {
/* 312 */     return put(objectToKey(key), value);
/*     */   }
/*     */ 
/*     */   
/*     */   public V remove(Object key) {
/* 317 */     return remove(objectToKey(key));
/*     */   }
/*     */ 
/*     */   
/*     */   public Set<Long> keySet() {
/* 322 */     return this.keySet;
/*     */   }
/*     */ 
/*     */   
/*     */   public Set<Map.Entry<Long, V>> entrySet() {
/* 327 */     return this.entrySet;
/*     */   }
/*     */   
/*     */   private long objectToKey(Object key) {
/* 331 */     return ((Long)key).longValue();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private int indexOf(long key) {
/* 341 */     int startIndex = hashIndex(key);
/* 342 */     int index = startIndex;
/*     */     
/*     */     while (true) {
/* 345 */       if (this.values[index] == null)
/*     */       {
/* 347 */         return -1;
/*     */       }
/* 349 */       if (key == this.keys[index]) {
/* 350 */         return index;
/*     */       }
/*     */ 
/*     */       
/* 354 */       if ((index = probeNext(index)) == startIndex) {
/* 355 */         return -1;
/*     */       }
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private int hashIndex(long key) {
/* 365 */     return hashCode(key) & this.mask;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static int hashCode(long key) {
/* 372 */     return (int)(key ^ key >>> 32L);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private int probeNext(int index) {
/* 380 */     return index + 1 & this.mask;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void growSize() {
/* 387 */     this.size++;
/*     */     
/* 389 */     if (this.size > this.maxSize) {
/* 390 */       if (this.keys.length == Integer.MAX_VALUE) {
/* 391 */         throw new IllegalStateException("Max capacity reached at size=" + this.size);
/*     */       }
/*     */ 
/*     */       
/* 395 */       rehash(this.keys.length << 1);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private boolean removeAt(int index) {
/* 407 */     this.size--;
/*     */ 
/*     */     
/* 410 */     this.keys[index] = 0L;
/* 411 */     this.values[index] = null;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 418 */     int nextFree = index;
/* 419 */     int i = probeNext(index);
/* 420 */     for (V value = this.values[i]; value != null; value = this.values[i = probeNext(i)]) {
/* 421 */       long key = this.keys[i];
/* 422 */       int bucket = hashIndex(key);
/* 423 */       if ((i < bucket && (bucket <= nextFree || nextFree <= i)) || (bucket <= nextFree && nextFree <= i)) {
/*     */ 
/*     */         
/* 426 */         this.keys[nextFree] = key;
/* 427 */         this.values[nextFree] = value;
/*     */         
/* 429 */         this.keys[i] = 0L;
/* 430 */         this.values[i] = null;
/* 431 */         nextFree = i;
/*     */       } 
/*     */     } 
/* 434 */     return (nextFree != index);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private int calcMaxSize(int capacity) {
/* 442 */     int upperBound = capacity - 1;
/* 443 */     return Math.min(upperBound, (int)(capacity * this.loadFactor));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void rehash(int newCapacity) {
/* 452 */     long[] oldKeys = this.keys;
/* 453 */     V[] oldVals = this.values;
/*     */     
/* 455 */     this.keys = new long[newCapacity];
/*     */     
/* 457 */     V[] temp = (V[])new Object[newCapacity];
/* 458 */     this.values = temp;
/*     */     
/* 460 */     this.maxSize = calcMaxSize(newCapacity);
/* 461 */     this.mask = newCapacity - 1;
/*     */ 
/*     */     
/* 464 */     for (int i = 0; i < oldVals.length; i++) {
/* 465 */       V oldVal = oldVals[i];
/* 466 */       if (oldVal != null) {
/*     */ 
/*     */         
/* 469 */         long oldKey = oldKeys[i];
/* 470 */         int index = hashIndex(oldKey);
/*     */         
/*     */         while (true) {
/* 473 */           if (this.values[index] == null) {
/* 474 */             this.keys[index] = oldKey;
/* 475 */             this.values[index] = oldVal;
/*     */             
/*     */             break;
/*     */           } 
/*     */           
/* 480 */           index = probeNext(index);
/*     */         } 
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public String toString() {
/* 488 */     if (isEmpty()) {
/* 489 */       return "{}";
/*     */     }
/* 491 */     StringBuilder sb = new StringBuilder(4 * this.size);
/* 492 */     sb.append('{');
/* 493 */     boolean first = true;
/* 494 */     for (int i = 0; i < this.values.length; i++) {
/* 495 */       V value = this.values[i];
/* 496 */       if (value != null) {
/* 497 */         if (!first) {
/* 498 */           sb.append(", ");
/*     */         }
/* 500 */         sb.append(keyToString(this.keys[i])).append('=').append((value == this) ? "(this Map)" : 
/* 501 */             toExternal(value));
/* 502 */         first = false;
/*     */       } 
/*     */     } 
/* 505 */     return sb.append('}').toString();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected String keyToString(long key) {
/* 513 */     return Long.toString(key);
/*     */   }
/*     */   
/*     */   private final class EntrySet
/*     */     extends AbstractSet<Map.Entry<Long, V>>
/*     */   {
/*     */     private EntrySet() {}
/*     */     
/*     */     public Iterator<Map.Entry<Long, V>> iterator() {
/* 522 */       return new LongObjectHashMap.MapIterator();
/*     */     }
/*     */ 
/*     */     
/*     */     public int size() {
/* 527 */       return LongObjectHashMap.this.size();
/*     */     }
/*     */   }
/*     */   
/*     */   private final class KeySet
/*     */     extends AbstractSet<Long>
/*     */   {
/*     */     private KeySet() {}
/*     */     
/*     */     public int size() {
/* 537 */       return LongObjectHashMap.this.size();
/*     */     }
/*     */ 
/*     */     
/*     */     public boolean contains(Object o) {
/* 542 */       return LongObjectHashMap.this.containsKey(o);
/*     */     }
/*     */ 
/*     */     
/*     */     public boolean remove(Object o) {
/* 547 */       return (LongObjectHashMap.this.remove(o) != null);
/*     */     }
/*     */ 
/*     */     
/*     */     public boolean retainAll(Collection<?> retainedKeys) {
/* 552 */       boolean changed = false;
/* 553 */       for (Iterator<LongObjectMap.PrimitiveEntry<V>> iter = LongObjectHashMap.this.entries().iterator(); iter.hasNext(); ) {
/* 554 */         LongObjectMap.PrimitiveEntry<V> entry = iter.next();
/* 555 */         if (!retainedKeys.contains(Long.valueOf(entry.key()))) {
/* 556 */           changed = true;
/* 557 */           iter.remove();
/*     */         } 
/*     */       } 
/* 560 */       return changed;
/*     */     }
/*     */ 
/*     */     
/*     */     public void clear() {
/* 565 */       LongObjectHashMap.this.clear();
/*     */     }
/*     */ 
/*     */     
/*     */     public Iterator<Long> iterator() {
/* 570 */       return new Iterator<Long>() {
/* 571 */           private final Iterator<Map.Entry<Long, V>> iter = LongObjectHashMap.this.entrySet.iterator();
/*     */ 
/*     */           
/*     */           public boolean hasNext() {
/* 575 */             return this.iter.hasNext();
/*     */           }
/*     */ 
/*     */           
/*     */           public Long next() {
/* 580 */             return (Long)((Map.Entry)this.iter.next()).getKey();
/*     */           }
/*     */ 
/*     */           
/*     */           public void remove() {
/* 585 */             this.iter.remove();
/*     */           }
/*     */         };
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   private final class PrimitiveIterator
/*     */     implements Iterator<LongObjectMap.PrimitiveEntry<V>>, LongObjectMap.PrimitiveEntry<V>
/*     */   {
/* 595 */     private int prevIndex = -1;
/* 596 */     private int nextIndex = -1;
/* 597 */     private int entryIndex = -1;
/*     */     
/*     */     private void scanNext() {
/* 600 */       while (++this.nextIndex != LongObjectHashMap.this.values.length && LongObjectHashMap.this.values[this.nextIndex] == null);
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     public boolean hasNext() {
/* 606 */       if (this.nextIndex == -1) {
/* 607 */         scanNext();
/*     */       }
/* 609 */       return (this.nextIndex != LongObjectHashMap.this.values.length);
/*     */     }
/*     */ 
/*     */     
/*     */     public LongObjectMap.PrimitiveEntry<V> next() {
/* 614 */       if (!hasNext()) {
/* 615 */         throw new NoSuchElementException();
/*     */       }
/*     */       
/* 618 */       this.prevIndex = this.nextIndex;
/* 619 */       scanNext();
/*     */ 
/*     */       
/* 622 */       this.entryIndex = this.prevIndex;
/* 623 */       return this;
/*     */     }
/*     */ 
/*     */     
/*     */     public void remove() {
/* 628 */       if (this.prevIndex == -1) {
/* 629 */         throw new IllegalStateException("next must be called before each remove.");
/*     */       }
/* 631 */       if (LongObjectHashMap.this.removeAt(this.prevIndex))
/*     */       {
/*     */ 
/*     */         
/* 635 */         this.nextIndex = this.prevIndex;
/*     */       }
/* 637 */       this.prevIndex = -1;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public long key() {
/* 645 */       return LongObjectHashMap.this.keys[this.entryIndex];
/*     */     }
/*     */ 
/*     */     
/*     */     public V value() {
/* 650 */       return (V)LongObjectHashMap.toExternal((T)LongObjectHashMap.this.values[this.entryIndex]);
/*     */     }
/*     */ 
/*     */     
/*     */     public void setValue(V value) {
/* 655 */       LongObjectHashMap.this.values[this.entryIndex] = LongObjectHashMap.toInternal((T)value);
/*     */     }
/*     */     
/*     */     private PrimitiveIterator() {}
/*     */   }
/*     */   
/*     */   private final class MapIterator
/*     */     implements Iterator<Map.Entry<Long, V>> {
/* 663 */     private final LongObjectHashMap<V>.PrimitiveIterator iter = new LongObjectHashMap.PrimitiveIterator();
/*     */ 
/*     */     
/*     */     public boolean hasNext() {
/* 667 */       return this.iter.hasNext();
/*     */     }
/*     */ 
/*     */     
/*     */     public Map.Entry<Long, V> next() {
/* 672 */       if (!hasNext()) {
/* 673 */         throw new NoSuchElementException();
/*     */       }
/*     */       
/* 676 */       this.iter.next();
/*     */       
/* 678 */       return new LongObjectHashMap.MapEntry(this.iter.entryIndex);
/*     */     }
/*     */ 
/*     */     
/*     */     public void remove() {
/* 683 */       this.iter.remove();
/*     */     }
/*     */     
/*     */     private MapIterator() {}
/*     */   }
/*     */   
/*     */   final class MapEntry
/*     */     implements Map.Entry<Long, V> {
/*     */     private final int entryIndex;
/*     */     
/*     */     MapEntry(int entryIndex) {
/* 694 */       this.entryIndex = entryIndex;
/*     */     }
/*     */ 
/*     */     
/*     */     public Long getKey() {
/* 699 */       verifyExists();
/* 700 */       return Long.valueOf(LongObjectHashMap.this.keys[this.entryIndex]);
/*     */     }
/*     */ 
/*     */     
/*     */     public V getValue() {
/* 705 */       verifyExists();
/* 706 */       return (V)LongObjectHashMap.toExternal((T)LongObjectHashMap.this.values[this.entryIndex]);
/*     */     }
/*     */ 
/*     */     
/*     */     public V setValue(V value) {
/* 711 */       verifyExists();
/* 712 */       V prevValue = (V)LongObjectHashMap.toExternal((T)LongObjectHashMap.this.values[this.entryIndex]);
/* 713 */       LongObjectHashMap.this.values[this.entryIndex] = LongObjectHashMap.toInternal((T)value);
/* 714 */       return prevValue;
/*     */     }
/*     */     
/*     */     private void verifyExists() {
/* 718 */       if (LongObjectHashMap.this.values[this.entryIndex] == null)
/* 719 */         throw new IllegalStateException("The map entry has been removed"); 
/*     */     }
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\io\nett\\util\collection\LongObjectHashMap.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */