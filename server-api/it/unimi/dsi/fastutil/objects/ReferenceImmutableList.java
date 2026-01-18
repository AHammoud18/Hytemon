/*     */ package it.unimi.dsi.fastutil.objects;
/*     */ 
/*     */ import it.unimi.dsi.fastutil.SafeMath;
/*     */ import java.io.InvalidObjectException;
/*     */ import java.io.ObjectStreamException;
/*     */ import java.io.Serializable;
/*     */ import java.lang.reflect.Array;
/*     */ import java.util.Arrays;
/*     */ import java.util.Collection;
/*     */ import java.util.List;
/*     */ import java.util.ListIterator;
/*     */ import java.util.NoSuchElementException;
/*     */ import java.util.RandomAccess;
/*     */ import java.util.function.Consumer;
/*     */ import java.util.function.Supplier;
/*     */ import java.util.stream.Collector;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class ReferenceImmutableList<K>
/*     */   extends ReferenceLists.ImmutableListBase<K>
/*     */   implements ReferenceList<K>, RandomAccess, Cloneable, Serializable
/*     */ {
/*     */   private static final long serialVersionUID = 0L;
/*  42 */   static final ReferenceImmutableList EMPTY = new ReferenceImmutableList((K[])ObjectArrays.EMPTY_ARRAY);
/*     */   private final K[] a;
/*     */   
/*     */   private static final <K> K[] emptyArray() {
/*  46 */     return (K[])ObjectArrays.EMPTY_ARRAY;
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
/*     */   public ReferenceImmutableList(K[] a) {
/*  61 */     this.a = a;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ReferenceImmutableList(Collection<? extends K> c) {
/*  70 */     this(c.isEmpty() ? emptyArray() : ObjectIterators.<K>unwrap(c.iterator()));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ReferenceImmutableList(ReferenceCollection<? extends K> c) {
/*  79 */     this(c.isEmpty() ? emptyArray() : ObjectIterators.<K>unwrap(c.iterator()));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ReferenceImmutableList(ReferenceList<? extends K> l) {
/*  89 */     this(l.isEmpty() ? emptyArray() : (K[])new Object[l.size()]);
/*  90 */     l.getElements(0, (Object[])this.a, 0, l.size());
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
/*     */   public ReferenceImmutableList(K[] a, int offset, int length) {
/* 102 */     this((length == 0) ? emptyArray() : (K[])new Object[length]);
/* 103 */     System.arraycopy(a, offset, this.a, 0, length);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ReferenceImmutableList(ObjectIterator<? extends K> i) {
/* 113 */     this(i.hasNext() ? ObjectIterators.<K>unwrap(i) : emptyArray());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static <K> ReferenceImmutableList<K> of() {
/* 123 */     return EMPTY;
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
/*     */   @SafeVarargs
/*     */   public static <K> ReferenceImmutableList<K> of(K... init) {
/* 137 */     return (init.length == 0) ? of() : new ReferenceImmutableList<>(init);
/*     */   }
/*     */   
/*     */   private static <K> ReferenceImmutableList<K> convertTrustedToImmutableList(ReferenceArrayList<K> arrayList) {
/* 141 */     if (arrayList.isEmpty()) {
/* 142 */       return of();
/*     */     }
/* 144 */     K[] backingArray = arrayList.elements();
/* 145 */     if (arrayList.size() != backingArray.length) {
/* 146 */       backingArray = Arrays.copyOf(backingArray, arrayList.size());
/*     */     }
/* 148 */     return new ReferenceImmutableList<>(backingArray);
/*     */   }
/*     */   
/* 151 */   private static final Collector<Object, ?, ReferenceImmutableList<Object>> TO_LIST_COLLECTOR = Collector.of(ReferenceArrayList::new, ReferenceArrayList::add, ReferenceArrayList::combine, ReferenceImmutableList::convertTrustedToImmutableList, new Collector.Characteristics[0]);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static <K> Collector<K, ?, ReferenceImmutableList<K>> toList() {
/* 158 */     return (Collector)TO_LIST_COLLECTOR;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static <K> Collector<K, ?, ReferenceImmutableList<K>> toListWithExpectedSize(int expectedSize) {
/* 166 */     if (expectedSize <= 10)
/*     */     {
/*     */       
/* 169 */       return toList();
/*     */     }
/* 171 */     return Collector.of(new ReferenceCollections.SizeDecreasingSupplier<>(expectedSize, size -> (size <= 10) ? new ReferenceArrayList() : new ReferenceArrayList(size)), ReferenceArrayList::add, ReferenceArrayList::combine, ReferenceImmutableList::convertTrustedToImmutableList, new Collector.Characteristics[0]);
/*     */   }
/*     */ 
/*     */   
/*     */   public K get(int index) {
/* 176 */     if (index >= this.a.length) throw new IndexOutOfBoundsException("Index (" + index + ") is greater than or equal to list size (" + this.a.length + ")"); 
/* 177 */     return this.a[index];
/*     */   }
/*     */ 
/*     */   
/*     */   public int indexOf(Object k) {
/* 182 */     for (int i = 0, size = this.a.length; i < size; ) { if (k == this.a[i]) return i;  i++; }
/* 183 */      return -1;
/*     */   }
/*     */ 
/*     */   
/*     */   public int lastIndexOf(Object k) {
/* 188 */     for (int i = this.a.length; i-- != 0;) { if (k == this.a[i]) return i;  }
/* 189 */      return -1;
/*     */   }
/*     */ 
/*     */   
/*     */   public int size() {
/* 194 */     return this.a.length;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isEmpty() {
/* 199 */     return (this.a.length == 0);
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
/*     */   public void getElements(int from, Object[] a, int offset, int length) {
/* 212 */     ObjectArrays.ensureOffsetLength(a, offset, length);
/* 213 */     System.arraycopy(this.a, from, a, offset, length);
/*     */   }
/*     */ 
/*     */   
/*     */   public void forEach(Consumer<? super K> action) {
/* 218 */     for (int i = 0; i < this.a.length; i++) {
/* 219 */       action.accept(this.a[i]);
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public Object[] toArray() {
/* 226 */     if (this.a.length == 0) return ObjectArrays.EMPTY_ARRAY; 
/* 227 */     if (this.a.getClass() == Object[].class) return (Object[])this.a.clone(); 
/* 228 */     return Arrays.copyOf(this.a, this.a.length, Object[].class);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public <T> T[] toArray(T[] a) {
/* 234 */     if (a == null) {
/* 235 */       a = (T[])new Object[size()];
/* 236 */     } else if (a.length < size()) {
/* 237 */       a = (T[])Array.newInstance(a.getClass().getComponentType(), size());
/*     */     } 
/* 239 */     System.arraycopy(this.a, 0, a, 0, size());
/* 240 */     if (a.length > size()) {
/* 241 */       a[size()] = null;
/*     */     }
/* 243 */     return a;
/*     */   }
/*     */ 
/*     */   
/*     */   public ObjectListIterator<K> listIterator(final int index) {
/* 248 */     ensureIndex(index);
/* 249 */     return new ObjectListIterator<K>() {
/* 250 */         int pos = index;
/*     */ 
/*     */         
/*     */         public boolean hasNext() {
/* 254 */           return (this.pos < ReferenceImmutableList.this.a.length);
/*     */         }
/*     */ 
/*     */         
/*     */         public boolean hasPrevious() {
/* 259 */           return (this.pos > 0);
/*     */         }
/*     */ 
/*     */         
/*     */         public K next() {
/* 264 */           if (!hasNext()) throw new NoSuchElementException(); 
/* 265 */           return (K)ReferenceImmutableList.this.a[this.pos++];
/*     */         }
/*     */ 
/*     */         
/*     */         public K previous() {
/* 270 */           if (!hasPrevious()) throw new NoSuchElementException(); 
/* 271 */           return (K)ReferenceImmutableList.this.a[--this.pos];
/*     */         }
/*     */ 
/*     */         
/*     */         public int nextIndex() {
/* 276 */           return this.pos;
/*     */         }
/*     */ 
/*     */         
/*     */         public int previousIndex() {
/* 281 */           return this.pos - 1;
/*     */         }
/*     */ 
/*     */         
/*     */         public void forEachRemaining(Consumer<? super K> action) {
/* 286 */           while (this.pos < ReferenceImmutableList.this.a.length) {
/* 287 */             action.accept((K)ReferenceImmutableList.this.a[this.pos++]);
/*     */           }
/*     */         }
/*     */ 
/*     */         
/*     */         public void add(K k) {
/* 293 */           throw new UnsupportedOperationException();
/*     */         }
/*     */ 
/*     */         
/*     */         public void set(K k) {
/* 298 */           throw new UnsupportedOperationException();
/*     */         }
/*     */ 
/*     */         
/*     */         public void remove() {
/* 303 */           throw new UnsupportedOperationException();
/*     */         }
/*     */ 
/*     */         
/*     */         public int back(int n) {
/* 308 */           if (n < 0) throw new IllegalArgumentException("Argument must be nonnegative: " + n); 
/* 309 */           int remaining = ReferenceImmutableList.this.a.length - this.pos;
/* 310 */           if (n < remaining) {
/* 311 */             this.pos -= n;
/*     */           } else {
/* 313 */             n = remaining;
/* 314 */             this.pos = 0;
/*     */           } 
/* 316 */           return n;
/*     */         }
/*     */ 
/*     */         
/*     */         public int skip(int n) {
/* 321 */           if (n < 0) throw new IllegalArgumentException("Argument must be nonnegative: " + n); 
/* 322 */           int remaining = ReferenceImmutableList.this.a.length - this.pos;
/* 323 */           if (n < remaining) {
/* 324 */             this.pos += n;
/*     */           } else {
/* 326 */             n = remaining;
/* 327 */             this.pos = ReferenceImmutableList.this.a.length;
/*     */           } 
/* 329 */           return n;
/*     */         }
/*     */       };
/*     */   }
/*     */   
/*     */   private final class Spliterator implements ObjectSpliterator<K> { int pos;
/*     */     int max;
/*     */     
/*     */     public Spliterator() {
/* 338 */       this(0, ReferenceImmutableList.this.a.length);
/*     */     }
/*     */     
/*     */     private Spliterator(int pos, int max) {
/* 342 */       assert pos <= max : "pos " + pos + " must be <= max " + max;
/* 343 */       this.pos = pos;
/* 344 */       this.max = max;
/*     */     }
/*     */ 
/*     */     
/*     */     public int characteristics() {
/* 349 */       return 17488;
/*     */     }
/*     */ 
/*     */     
/*     */     public long estimateSize() {
/* 354 */       return (this.max - this.pos);
/*     */     }
/*     */ 
/*     */     
/*     */     public boolean tryAdvance(Consumer<? super K> action) {
/* 359 */       if (this.pos >= this.max) return false; 
/* 360 */       action.accept((K)ReferenceImmutableList.this.a[this.pos++]);
/* 361 */       return true;
/*     */     }
/*     */ 
/*     */     
/*     */     public void forEachRemaining(Consumer<? super K> action) {
/* 366 */       for (; this.pos < this.max; this.pos++) {
/* 367 */         action.accept((K)ReferenceImmutableList.this.a[this.pos]);
/*     */       }
/*     */     }
/*     */ 
/*     */     
/*     */     public long skip(long n) {
/* 373 */       if (n < 0L) throw new IllegalArgumentException("Argument must be nonnegative: " + n); 
/* 374 */       if (this.pos >= this.max) return 0L; 
/* 375 */       int remaining = this.max - this.pos;
/* 376 */       if (n < remaining) {
/* 377 */         this.pos = SafeMath.safeLongToInt(this.pos + n);
/* 378 */         return n;
/*     */       } 
/* 380 */       n = remaining;
/* 381 */       this.pos = this.max;
/* 382 */       return n;
/*     */     }
/*     */ 
/*     */     
/*     */     public ObjectSpliterator<K> trySplit() {
/* 387 */       int retLen = this.max - this.pos >> 1;
/* 388 */       if (retLen <= 1) return null; 
/* 389 */       int myNewPos = this.pos + retLen;
/* 390 */       int retMax = myNewPos;
/* 391 */       int oldPos = this.pos;
/* 392 */       this.pos = myNewPos;
/* 393 */       return new Spliterator(oldPos, retMax);
/*     */     } }
/*     */ 
/*     */ 
/*     */   
/*     */   public ObjectSpliterator<K> spliterator() {
/* 399 */     return new Spliterator();
/*     */   }
/*     */ 
/*     */   
/*     */   private static final class ImmutableSubList<K>
/*     */     extends ReferenceLists.ImmutableListBase<K>
/*     */     implements RandomAccess, Serializable
/*     */   {
/*     */     private static final long serialVersionUID = 7054639518438982401L;
/*     */     
/*     */     final ReferenceImmutableList<K> innerList;
/*     */     
/*     */     final int from;
/*     */     final int to;
/*     */     final transient K[] a;
/*     */     
/*     */     ImmutableSubList(ReferenceImmutableList<K> innerList, int from, int to) {
/* 416 */       this.innerList = innerList;
/* 417 */       this.from = from;
/* 418 */       this.to = to;
/* 419 */       this.a = innerList.a;
/*     */     }
/*     */ 
/*     */     
/*     */     public K get(int index) {
/* 424 */       ensureRestrictedIndex(index);
/* 425 */       return this.a[index + this.from];
/*     */     }
/*     */ 
/*     */     
/*     */     public int indexOf(Object k) {
/* 430 */       for (int i = this.from; i < this.to; ) { if (k == this.a[i]) return i - this.from;  i++; }
/* 431 */        return -1;
/*     */     }
/*     */ 
/*     */     
/*     */     public int lastIndexOf(Object k) {
/* 436 */       for (int i = this.to; i-- != this.from;) { if (k == this.a[i]) return i - this.from;  }
/* 437 */        return -1;
/*     */     }
/*     */ 
/*     */     
/*     */     public int size() {
/* 442 */       return this.to - this.from;
/*     */     }
/*     */ 
/*     */     
/*     */     public boolean isEmpty() {
/* 447 */       return (this.to <= this.from);
/*     */     }
/*     */ 
/*     */     
/*     */     public void getElements(int fromSublistIndex, Object[] a, int offset, int length) {
/* 452 */       ObjectArrays.ensureOffsetLength(a, offset, length);
/* 453 */       ensureRestrictedIndex(fromSublistIndex);
/* 454 */       if (this.from + length > this.to) throw new IndexOutOfBoundsException("Final index " + (this.from + length) + " (startingIndex: " + this.from + " + length: " + length + ") is greater then list length " + size()); 
/* 455 */       System.arraycopy(this.a, fromSublistIndex + this.from, a, offset, length);
/*     */     }
/*     */ 
/*     */     
/*     */     public void forEach(Consumer<? super K> action) {
/* 460 */       for (int i = this.from; i < this.to; i++) {
/* 461 */         action.accept(this.a[i]);
/*     */       }
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     public Object[] toArray() {
/* 468 */       return Arrays.copyOfRange(this.a, this.from, this.to, Object[].class);
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     public <K> K[] toArray(K[] a) {
/* 474 */       int size = size();
/* 475 */       if (a == null) {
/* 476 */         a = (K[])new Object[size];
/* 477 */       } else if (a.length < size) {
/* 478 */         a = (K[])Array.newInstance(a.getClass().getComponentType(), size);
/*     */       } 
/* 480 */       System.arraycopy(this.a, this.from, a, 0, size);
/* 481 */       if (a.length > size) {
/* 482 */         a[size] = null;
/*     */       }
/* 484 */       return a;
/*     */     }
/*     */ 
/*     */     
/*     */     public ObjectListIterator<K> listIterator(final int index) {
/* 489 */       ensureIndex(index);
/* 490 */       return new ObjectListIterator<K>() {
/* 491 */           int pos = index;
/*     */ 
/*     */           
/*     */           public boolean hasNext() {
/* 495 */             return (this.pos < ReferenceImmutableList.ImmutableSubList.this.to);
/*     */           }
/*     */ 
/*     */           
/*     */           public boolean hasPrevious() {
/* 500 */             return (this.pos > ReferenceImmutableList.ImmutableSubList.this.from);
/*     */           }
/*     */ 
/*     */           
/*     */           public K next() {
/* 505 */             if (!hasNext()) throw new NoSuchElementException(); 
/* 506 */             return ReferenceImmutableList.ImmutableSubList.this.a[this.pos++ + ReferenceImmutableList.ImmutableSubList.this.from];
/*     */           }
/*     */ 
/*     */           
/*     */           public K previous() {
/* 511 */             if (!hasPrevious()) throw new NoSuchElementException(); 
/* 512 */             return ReferenceImmutableList.ImmutableSubList.this.a[--this.pos + ReferenceImmutableList.ImmutableSubList.this.from];
/*     */           }
/*     */ 
/*     */           
/*     */           public int nextIndex() {
/* 517 */             return this.pos;
/*     */           }
/*     */ 
/*     */           
/*     */           public int previousIndex() {
/* 522 */             return this.pos - 1;
/*     */           }
/*     */ 
/*     */           
/*     */           public void forEachRemaining(Consumer<? super K> action) {
/* 527 */             while (this.pos < ReferenceImmutableList.ImmutableSubList.this.to) {
/* 528 */               action.accept(ReferenceImmutableList.ImmutableSubList.this.a[this.pos++ + ReferenceImmutableList.ImmutableSubList.this.from]);
/*     */             }
/*     */           }
/*     */ 
/*     */           
/*     */           public void add(K k) {
/* 534 */             throw new UnsupportedOperationException();
/*     */           }
/*     */ 
/*     */           
/*     */           public void set(K k) {
/* 539 */             throw new UnsupportedOperationException();
/*     */           }
/*     */ 
/*     */           
/*     */           public void remove() {
/* 544 */             throw new UnsupportedOperationException();
/*     */           }
/*     */ 
/*     */           
/*     */           public int back(int n) {
/* 549 */             if (n < 0) throw new IllegalArgumentException("Argument must be nonnegative: " + n); 
/* 550 */             int remaining = ReferenceImmutableList.ImmutableSubList.this.to - this.pos;
/* 551 */             if (n < remaining) {
/* 552 */               this.pos -= n;
/*     */             } else {
/* 554 */               n = remaining;
/* 555 */               this.pos = 0;
/*     */             } 
/* 557 */             return n;
/*     */           }
/*     */ 
/*     */           
/*     */           public int skip(int n) {
/* 562 */             if (n < 0) throw new IllegalArgumentException("Argument must be nonnegative: " + n); 
/* 563 */             int remaining = ReferenceImmutableList.ImmutableSubList.this.to - this.pos;
/* 564 */             if (n < remaining) {
/* 565 */               this.pos += n;
/*     */             } else {
/* 567 */               n = remaining;
/* 568 */               this.pos = ReferenceImmutableList.ImmutableSubList.this.to;
/*     */             } 
/* 570 */             return n;
/*     */           }
/*     */         };
/*     */     }
/*     */     
/*     */     private final class SubListSpliterator
/*     */       extends ObjectSpliterators.EarlyBindingSizeIndexBasedSpliterator<K> {
/*     */       SubListSpliterator() {
/* 578 */         super(ReferenceImmutableList.ImmutableSubList.this.from, ReferenceImmutableList.ImmutableSubList.this.to);
/*     */       }
/*     */ 
/*     */       
/*     */       private SubListSpliterator(int pos, int maxPos) {
/* 583 */         super(pos, maxPos);
/*     */       }
/*     */ 
/*     */ 
/*     */       
/*     */       protected final K get(int i) {
/* 589 */         return ReferenceImmutableList.ImmutableSubList.this.a[i];
/*     */       }
/*     */ 
/*     */       
/*     */       protected final SubListSpliterator makeForSplit(int pos, int maxPos) {
/* 594 */         return new SubListSpliterator(pos, maxPos);
/*     */       }
/*     */ 
/*     */       
/*     */       public boolean tryAdvance(Consumer<? super K> action) {
/* 599 */         if (this.pos >= this.maxPos) return false; 
/* 600 */         action.accept(ReferenceImmutableList.ImmutableSubList.this.a[this.pos++]);
/* 601 */         return true;
/*     */       }
/*     */ 
/*     */       
/*     */       public void forEachRemaining(Consumer<? super K> action) {
/* 606 */         int max = this.maxPos;
/* 607 */         while (this.pos < max) {
/* 608 */           action.accept(ReferenceImmutableList.ImmutableSubList.this.a[this.pos++]);
/*     */         }
/*     */       }
/*     */ 
/*     */       
/*     */       public int characteristics() {
/* 614 */         return 17488;
/*     */       }
/*     */     }
/*     */ 
/*     */     
/*     */     public ObjectSpliterator<K> spliterator() {
/* 620 */       return new SubListSpliterator();
/*     */     }
/*     */     
/*     */     boolean contentsEquals(K[] otherA, int otherAFrom, int otherATo) {
/* 624 */       if (this.a == otherA && this.from == otherAFrom && this.to == otherATo) {
/* 625 */         return true;
/*     */       }
/* 627 */       if (otherATo - otherAFrom != size()) {
/* 628 */         return false;
/*     */       }
/* 630 */       int pos = this.from, otherPos = otherAFrom;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 636 */       while (pos < this.to) { if (this.a[pos++] != otherA[otherPos++]) return false;  }
/* 637 */        return true;
/*     */     }
/*     */ 
/*     */     
/*     */     public boolean equals(Object o) {
/* 642 */       if (o == this) return true; 
/* 643 */       if (o == null) return false; 
/* 644 */       if (!(o instanceof List)) return false; 
/* 645 */       if (o instanceof ReferenceImmutableList) {
/*     */         
/* 647 */         ReferenceImmutableList<K> other = (ReferenceImmutableList<K>)o;
/* 648 */         return contentsEquals(other.a, 0, other.size());
/*     */       } 
/* 650 */       if (o instanceof ImmutableSubList) {
/*     */         
/* 652 */         ImmutableSubList<K> other = (ImmutableSubList<K>)o;
/* 653 */         return contentsEquals(other.a, other.from, other.to);
/*     */       } 
/* 655 */       return super.equals(o);
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     private Object readResolve() throws ObjectStreamException {
/*     */       try {
/* 663 */         return this.innerList.subList(this.from, this.to);
/* 664 */       } catch (IllegalArgumentException|IndexOutOfBoundsException ex) {
/* 665 */         throw (InvalidObjectException)(new InvalidObjectException(ex.getMessage())).initCause(ex);
/*     */       } 
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public ReferenceList<K> subList(int from, int to) {
/* 675 */       ensureIndex(from);
/* 676 */       ensureIndex(to);
/* 677 */       if (from == to) return ReferenceImmutableList.EMPTY; 
/* 678 */       if (from > to) throw new IllegalArgumentException("Start index (" + from + ") is greater than end index (" + to + ")"); 
/* 679 */       return new ImmutableSubList(this.innerList, from + this.from, to + this.from);
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
/*     */ 
/*     */ 
/*     */   
/*     */   public ReferenceList<K> subList(int from, int to) {
/* 694 */     if (from == 0 && to == size()) return this; 
/* 695 */     ensureIndex(from);
/* 696 */     ensureIndex(to);
/* 697 */     if (from == to) return EMPTY; 
/* 698 */     if (from > to) throw new IllegalArgumentException("Start index (" + from + ") is greater than end index (" + to + ")"); 
/* 699 */     return new ImmutableSubList<>(this, from, to);
/*     */   }
/*     */ 
/*     */   
/*     */   public ReferenceImmutableList<K> clone() {
/* 704 */     return this;
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
/*     */   public boolean equals(ReferenceImmutableList<K> l) {
/* 717 */     if (l == this) return true; 
/* 718 */     if (this.a == l.a) return true; 
/* 719 */     int s = size();
/* 720 */     if (s != l.size()) return false; 
/* 721 */     K[] a1 = this.a;
/* 722 */     K[] a2 = l.a;
/* 723 */     while (s-- != 0) { if (a1[s] != a2[s]) return false;  }
/* 724 */      return true;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean equals(Object o) {
/* 730 */     if (o == this) return true; 
/* 731 */     if (o == null) return false; 
/* 732 */     if (!(o instanceof List)) return false; 
/* 733 */     if (o instanceof ReferenceImmutableList) {
/* 734 */       return equals((ReferenceImmutableList<K>)o);
/*     */     }
/* 736 */     if (o instanceof ImmutableSubList)
/*     */     {
/* 738 */       return ((ImmutableSubList)o).equals(this);
/*     */     }
/* 740 */     return super.equals(o);
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\i\\unimi\dsi\fastutil\objects\ReferenceImmutableList.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */