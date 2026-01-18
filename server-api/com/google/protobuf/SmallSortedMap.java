/*     */ package com.google.protobuf;
/*     */ 
/*     */ import java.util.AbstractMap;
/*     */ import java.util.AbstractSet;
/*     */ import java.util.Collections;
/*     */ import java.util.Iterator;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ import java.util.Set;
/*     */ import java.util.SortedMap;
/*     */ import java.util.TreeMap;
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
/*     */ class SmallSortedMap<K extends Comparable<K>, V>
/*     */   extends AbstractMap<K, V>
/*     */ {
/*     */   static final int DEFAULT_FIELD_MAP_ARRAY_SIZE = 16;
/*     */   private Object[] entries;
/*     */   private int entriesSize;
/*     */   private Map<K, V> overflowEntries;
/*     */   private boolean isImmutable;
/*     */   private volatile EntrySet lazyEntrySet;
/*     */   private Map<K, V> overflowEntriesDescending;
/*     */   
/*     */   static <FieldDescriptorT extends FieldSet.FieldDescriptorLite<FieldDescriptorT>> SmallSortedMap<FieldDescriptorT, Object> newFieldMap() {
/*  69 */     return new SmallSortedMap<FieldDescriptorT, Object>()
/*     */       {
/*     */         public void makeImmutable() {
/*  72 */           if (!isImmutable()) {
/*  73 */             for (int i = 0; i < getNumArrayEntries(); i++) {
/*  74 */               Map.Entry<FieldDescriptorT, Object> entry = getArrayEntryAt(i);
/*  75 */               if (((FieldSet.FieldDescriptorLite)entry.getKey()).isRepeated()) {
/*  76 */                 List<?> value = (List)entry.getValue();
/*  77 */                 entry.setValue(Collections.unmodifiableList(value));
/*     */               } 
/*     */             } 
/*  80 */             for (Map.Entry<FieldDescriptorT, Object> entry : getOverflowEntries()) {
/*  81 */               if (((FieldSet.FieldDescriptorLite)entry.getKey()).isRepeated()) {
/*  82 */                 List<?> value = (List)entry.getValue();
/*  83 */                 entry.setValue(Collections.unmodifiableList(value));
/*     */               } 
/*     */             } 
/*     */           } 
/*  87 */           super.makeImmutable();
/*     */         }
/*     */       };
/*     */   }
/*     */ 
/*     */   
/*     */   static <K extends Comparable<K>, V> SmallSortedMap<K, V> newInstanceForTest() {
/*  94 */     return new SmallSortedMap<>();
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
/*     */ 
/*     */ 
/*     */   
/*     */   private SmallSortedMap() {
/* 113 */     this.overflowEntries = Collections.emptyMap();
/* 114 */     this.overflowEntriesDescending = Collections.emptyMap();
/*     */   }
/*     */ 
/*     */   
/*     */   public void makeImmutable() {
/* 119 */     if (!this.isImmutable) {
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 124 */       this
/*     */ 
/*     */         
/* 127 */         .overflowEntries = this.overflowEntries.isEmpty() ? Collections.<K, V>emptyMap() : Collections.<K, V>unmodifiableMap(this.overflowEntries);
/* 128 */       this
/*     */ 
/*     */         
/* 131 */         .overflowEntriesDescending = this.overflowEntriesDescending.isEmpty() ? Collections.<K, V>emptyMap() : Collections.<K, V>unmodifiableMap(this.overflowEntriesDescending);
/* 132 */       this.isImmutable = true;
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isImmutable() {
/* 138 */     return this.isImmutable;
/*     */   }
/*     */ 
/*     */   
/*     */   public int getNumArrayEntries() {
/* 143 */     return this.entriesSize;
/*     */   }
/*     */ 
/*     */   
/*     */   public Map.Entry<K, V> getArrayEntryAt(int index) {
/* 148 */     if (index >= this.entriesSize) {
/* 149 */       throw new ArrayIndexOutOfBoundsException(index);
/*     */     }
/*     */     
/* 152 */     Entry e = (Entry)this.entries[index];
/* 153 */     return e;
/*     */   }
/*     */ 
/*     */   
/*     */   public int getNumOverflowEntries() {
/* 158 */     return this.overflowEntries.size();
/*     */   }
/*     */ 
/*     */   
/*     */   public Iterable<Map.Entry<K, V>> getOverflowEntries() {
/* 163 */     return this.overflowEntries.isEmpty() ? 
/* 164 */       Collections.<Map.Entry<K, V>>emptySet() : 
/* 165 */       this.overflowEntries.entrySet();
/*     */   }
/*     */ 
/*     */   
/*     */   public int size() {
/* 170 */     return this.entriesSize + this.overflowEntries.size();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean containsKey(Object o) {
/* 181 */     Comparable comparable = (Comparable)o;
/* 182 */     return (binarySearchInArray((K)comparable) >= 0 || this.overflowEntries.containsKey(comparable));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public V get(Object o) {
/* 193 */     Comparable comparable = (Comparable)o;
/* 194 */     int index = binarySearchInArray((K)comparable);
/* 195 */     if (index >= 0) {
/*     */       
/* 197 */       Entry e = (Entry)this.entries[index];
/* 198 */       return e.getValue();
/*     */     } 
/* 200 */     return this.overflowEntries.get(comparable);
/*     */   }
/*     */ 
/*     */   
/*     */   public V put(K key, V value) {
/* 205 */     checkMutable();
/* 206 */     int index = binarySearchInArray(key);
/* 207 */     if (index >= 0) {
/*     */ 
/*     */       
/* 210 */       Entry e = (Entry)this.entries[index];
/* 211 */       return e.setValue(value);
/*     */     } 
/* 213 */     ensureEntryArrayMutable();
/* 214 */     int insertionPoint = -(index + 1);
/* 215 */     if (insertionPoint >= 16)
/*     */     {
/* 217 */       return getOverflowEntriesMutable().put(key, value);
/*     */     }
/*     */     
/* 220 */     if (this.entriesSize == 16) {
/*     */ 
/*     */       
/* 223 */       Entry lastEntryInArray = (Entry)this.entries[15];
/* 224 */       this.entriesSize--;
/* 225 */       getOverflowEntriesMutable().put(lastEntryInArray.getKey(), lastEntryInArray.getValue());
/*     */     } 
/* 227 */     System.arraycopy(this.entries, insertionPoint, this.entries, insertionPoint + 1, this.entries.length - insertionPoint - 1);
/*     */     
/* 229 */     this.entries[insertionPoint] = new Entry(key, value);
/* 230 */     this.entriesSize++;
/* 231 */     return null;
/*     */   }
/*     */ 
/*     */   
/*     */   public void clear() {
/* 236 */     checkMutable();
/* 237 */     if (this.entriesSize != 0) {
/* 238 */       this.entries = null;
/* 239 */       this.entriesSize = 0;
/*     */     } 
/* 241 */     if (!this.overflowEntries.isEmpty()) {
/* 242 */       this.overflowEntries.clear();
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public V remove(Object o) {
/* 253 */     checkMutable();
/*     */     
/* 255 */     Comparable comparable = (Comparable)o;
/* 256 */     int index = binarySearchInArray((K)comparable);
/* 257 */     if (index >= 0) {
/* 258 */       return removeArrayEntryAt(index);
/*     */     }
/*     */ 
/*     */     
/* 262 */     if (this.overflowEntries.isEmpty()) {
/* 263 */       return null;
/*     */     }
/* 265 */     return this.overflowEntries.remove(comparable);
/*     */   }
/*     */ 
/*     */   
/*     */   private V removeArrayEntryAt(int index) {
/* 270 */     checkMutable();
/*     */     
/* 272 */     V removed = ((Entry)this.entries[index]).getValue();
/*     */     
/* 274 */     System.arraycopy(this.entries, index + 1, this.entries, index, this.entriesSize - index - 1);
/* 275 */     this.entriesSize--;
/* 276 */     if (!this.overflowEntries.isEmpty()) {
/*     */ 
/*     */       
/* 279 */       Iterator<Map.Entry<K, V>> iterator = getOverflowEntriesMutable().entrySet().iterator();
/* 280 */       this.entries[this.entriesSize] = new Entry(iterator.next());
/* 281 */       this.entriesSize++;
/* 282 */       iterator.remove();
/*     */     } 
/* 284 */     return removed;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private int binarySearchInArray(K key) {
/* 293 */     int left = 0;
/* 294 */     int right = this.entriesSize - 1;
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 299 */     if (right >= 0) {
/*     */       
/* 301 */       int cmp = key.compareTo(((Entry)this.entries[right]).getKey());
/* 302 */       if (cmp > 0)
/* 303 */         return -(right + 2); 
/* 304 */       if (cmp == 0) {
/* 305 */         return right;
/*     */       }
/*     */     } 
/*     */     
/* 309 */     while (left <= right) {
/* 310 */       int mid = (left + right) / 2;
/*     */       
/* 312 */       int cmp = key.compareTo(((Entry)this.entries[mid]).getKey());
/* 313 */       if (cmp < 0) {
/* 314 */         right = mid - 1; continue;
/* 315 */       }  if (cmp > 0) {
/* 316 */         left = mid + 1; continue;
/*     */       } 
/* 318 */       return mid;
/*     */     } 
/*     */     
/* 321 */     return -(left + 1);
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
/*     */   public Set<Map.Entry<K, V>> entrySet() {
/* 333 */     if (this.lazyEntrySet == null) {
/* 334 */       this.lazyEntrySet = new EntrySet();
/*     */     }
/* 336 */     return this.lazyEntrySet;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   Set<Map.Entry<K, V>> descendingEntrySet() {
/* 345 */     return new DescendingEntrySet();
/*     */   }
/*     */ 
/*     */   
/*     */   private void checkMutable() {
/* 350 */     if (this.isImmutable) {
/* 351 */       throw new UnsupportedOperationException();
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private SortedMap<K, V> getOverflowEntriesMutable() {
/* 360 */     checkMutable();
/* 361 */     if (this.overflowEntries.isEmpty() && !(this.overflowEntries instanceof TreeMap)) {
/* 362 */       this.overflowEntries = new TreeMap<>();
/* 363 */       this.overflowEntriesDescending = ((TreeMap<K, V>)this.overflowEntries).descendingMap();
/*     */     } 
/* 365 */     return (SortedMap<K, V>)this.overflowEntries;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void ensureEntryArrayMutable() {
/* 372 */     checkMutable();
/* 373 */     if (this.entries == null) {
/* 374 */       this.entries = new Object[16];
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   private class Entry
/*     */     implements Map.Entry<K, V>, Comparable<Entry>
/*     */   {
/*     */     private final K key;
/*     */     
/*     */     private V value;
/*     */ 
/*     */     
/*     */     Entry(Map.Entry<K, V> copy) {
/* 388 */       this(copy.getKey(), copy.getValue());
/*     */     }
/*     */     
/*     */     Entry(K key, V value) {
/* 392 */       this.key = key;
/* 393 */       this.value = value;
/*     */     }
/*     */ 
/*     */     
/*     */     public K getKey() {
/* 398 */       return this.key;
/*     */     }
/*     */ 
/*     */     
/*     */     public V getValue() {
/* 403 */       return this.value;
/*     */     }
/*     */ 
/*     */     
/*     */     public int compareTo(Entry other) {
/* 408 */       return getKey().compareTo(other.getKey());
/*     */     }
/*     */ 
/*     */     
/*     */     public V setValue(V newValue) {
/* 413 */       SmallSortedMap.this.checkMutable();
/* 414 */       V oldValue = this.value;
/* 415 */       this.value = newValue;
/* 416 */       return oldValue;
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     public boolean equals(Object o) {
/* 422 */       if (o == this) {
/* 423 */         return true;
/*     */       }
/* 425 */       if (!(o instanceof Map.Entry)) {
/* 426 */         return false;
/*     */       }
/* 428 */       Map.Entry<?, ?> other = (Map.Entry<?, ?>)o;
/* 429 */       return (equals(this.key, other.getKey()) && equals(this.value, other.getValue()));
/*     */     }
/*     */ 
/*     */     
/*     */     public int hashCode() {
/* 434 */       return ((this.key == null) ? 0 : this.key.hashCode()) ^ ((this.value == null) ? 0 : this.value.hashCode());
/*     */     }
/*     */ 
/*     */     
/*     */     public String toString() {
/* 439 */       return (new StringBuilder()).append(this.key).append("=").append(this.value).toString();
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     private boolean equals(Object o1, Object o2) {
/* 446 */       return (o1 == null) ? ((o2 == null)) : o1.equals(o2);
/*     */     }
/*     */   }
/*     */   
/*     */   private class EntrySet
/*     */     extends AbstractSet<Map.Entry<K, V>> {
/*     */     private EntrySet() {}
/*     */     
/*     */     public Iterator<Map.Entry<K, V>> iterator() {
/* 455 */       return new SmallSortedMap.EntryIterator();
/*     */     }
/*     */ 
/*     */     
/*     */     public int size() {
/* 460 */       return SmallSortedMap.this.size();
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public boolean contains(Object o) {
/* 471 */       Map.Entry<K, V> entry = (Map.Entry<K, V>)o;
/* 472 */       V existing = (V)SmallSortedMap.this.get(entry.getKey());
/* 473 */       V value = entry.getValue();
/* 474 */       return (existing == value || (existing != null && existing.equals(value)));
/*     */     }
/*     */ 
/*     */     
/*     */     public boolean add(Map.Entry<K, V> entry) {
/* 479 */       if (!contains(entry)) {
/* 480 */         SmallSortedMap.this.put((Comparable)entry.getKey(), entry.getValue());
/* 481 */         return true;
/*     */       } 
/* 483 */       return false;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public boolean remove(Object o) {
/* 494 */       Map.Entry<K, V> entry = (Map.Entry<K, V>)o;
/* 495 */       if (contains(entry)) {
/* 496 */         SmallSortedMap.this.remove(entry.getKey());
/* 497 */         return true;
/*     */       } 
/* 499 */       return false;
/*     */     }
/*     */ 
/*     */     
/*     */     public void clear() {
/* 504 */       SmallSortedMap.this.clear();
/*     */     } }
/*     */   
/*     */   private class DescendingEntrySet extends EntrySet {
/*     */     private DescendingEntrySet() {}
/*     */     
/*     */     public Iterator<Map.Entry<K, V>> iterator() {
/* 511 */       return new SmallSortedMap.DescendingEntryIterator();
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private class EntryIterator
/*     */     implements Iterator<Map.Entry<K, V>>
/*     */   {
/* 521 */     private int pos = -1;
/*     */     
/*     */     private boolean nextCalledBeforeRemove;
/*     */     private Iterator<Map.Entry<K, V>> lazyOverflowIterator;
/*     */     
/*     */     public boolean hasNext() {
/* 527 */       return (this.pos + 1 < SmallSortedMap.this.entriesSize || (
/* 528 */         !SmallSortedMap.this.overflowEntries.isEmpty() && getOverflowIterator().hasNext()));
/*     */     }
/*     */ 
/*     */     
/*     */     public Map.Entry<K, V> next() {
/* 533 */       this.nextCalledBeforeRemove = true;
/*     */ 
/*     */       
/* 536 */       if (++this.pos < SmallSortedMap.this.entriesSize) {
/*     */         
/* 538 */         SmallSortedMap<K, V>.Entry e = (SmallSortedMap.Entry)SmallSortedMap.this.entries[this.pos];
/* 539 */         return e;
/*     */       } 
/* 541 */       return getOverflowIterator().next();
/*     */     }
/*     */ 
/*     */     
/*     */     public void remove() {
/* 546 */       if (!this.nextCalledBeforeRemove) {
/* 547 */         throw new IllegalStateException("remove() was called before next()");
/*     */       }
/* 549 */       this.nextCalledBeforeRemove = false;
/* 550 */       SmallSortedMap.this.checkMutable();
/*     */       
/* 552 */       if (this.pos < SmallSortedMap.this.entriesSize) {
/* 553 */         SmallSortedMap.this.removeArrayEntryAt(this.pos--);
/*     */       } else {
/* 555 */         getOverflowIterator().remove();
/*     */       } 
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     private Iterator<Map.Entry<K, V>> getOverflowIterator() {
/* 565 */       if (this.lazyOverflowIterator == null) {
/* 566 */         this.lazyOverflowIterator = SmallSortedMap.this.overflowEntries.entrySet().iterator();
/*     */       }
/* 568 */       return this.lazyOverflowIterator;
/*     */     }
/*     */     
/*     */     private EntryIterator() {} }
/*     */   
/*     */   private class DescendingEntryIterator implements Iterator<Map.Entry<K, V>> {
/*     */     private int pos;
/*     */     private Iterator<Map.Entry<K, V>> lazyOverflowIterator;
/*     */     
/*     */     private DescendingEntryIterator() {
/* 578 */       this.pos = SmallSortedMap.this.entriesSize;
/*     */     }
/*     */ 
/*     */     
/*     */     public boolean hasNext() {
/* 583 */       return ((this.pos > 0 && this.pos <= SmallSortedMap.this.entriesSize) || getOverflowIterator().hasNext());
/*     */     }
/*     */ 
/*     */     
/*     */     public Map.Entry<K, V> next() {
/* 588 */       if (getOverflowIterator().hasNext()) {
/* 589 */         return getOverflowIterator().next();
/*     */       }
/*     */       
/* 592 */       SmallSortedMap<K, V>.Entry e = (SmallSortedMap.Entry)SmallSortedMap.this.entries[--this.pos];
/* 593 */       return e;
/*     */     }
/*     */ 
/*     */     
/*     */     public void remove() {
/* 598 */       throw new UnsupportedOperationException();
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     private Iterator<Map.Entry<K, V>> getOverflowIterator() {
/* 607 */       if (this.lazyOverflowIterator == null) {
/* 608 */         this.lazyOverflowIterator = SmallSortedMap.this.overflowEntriesDescending.entrySet().iterator();
/*     */       }
/* 610 */       return this.lazyOverflowIterator;
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean equals(Object o) {
/* 617 */     if (this == o) {
/* 618 */       return true;
/*     */     }
/*     */     
/* 621 */     if (!(o instanceof SmallSortedMap)) {
/* 622 */       return super.equals(o);
/*     */     }
/*     */     
/* 625 */     SmallSortedMap<?, ?> other = (SmallSortedMap<?, ?>)o;
/* 626 */     int size = size();
/* 627 */     if (size != other.size()) {
/* 628 */       return false;
/*     */     }
/*     */ 
/*     */     
/* 632 */     int numArrayEntries = getNumArrayEntries();
/* 633 */     if (numArrayEntries != other.getNumArrayEntries()) {
/* 634 */       return entrySet().equals(other.entrySet());
/*     */     }
/*     */     
/* 637 */     for (int i = 0; i < numArrayEntries; i++) {
/* 638 */       if (!getArrayEntryAt(i).equals(other.getArrayEntryAt(i))) {
/* 639 */         return false;
/*     */       }
/*     */     } 
/*     */     
/* 643 */     if (numArrayEntries != size) {
/* 644 */       return this.overflowEntries.equals(other.overflowEntries);
/*     */     }
/*     */     
/* 647 */     return true;
/*     */   }
/*     */ 
/*     */   
/*     */   public int hashCode() {
/* 652 */     int h = 0;
/* 653 */     int listSize = getNumArrayEntries();
/* 654 */     for (int i = 0; i < listSize; i++) {
/* 655 */       h += this.entries[i].hashCode();
/*     */     }
/*     */     
/* 658 */     if (getNumOverflowEntries() > 0) {
/* 659 */       h += this.overflowEntries.hashCode();
/*     */     }
/* 661 */     return h;
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\google\protobuf\SmallSortedMap.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */