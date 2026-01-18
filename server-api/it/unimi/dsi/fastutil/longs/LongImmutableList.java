/*     */ package it.unimi.dsi.fastutil.longs;
/*     */ 
/*     */ import it.unimi.dsi.fastutil.SafeMath;
/*     */ import java.io.InvalidObjectException;
/*     */ import java.io.ObjectStreamException;
/*     */ import java.io.Serializable;
/*     */ import java.util.Arrays;
/*     */ import java.util.Collection;
/*     */ import java.util.List;
/*     */ import java.util.ListIterator;
/*     */ import java.util.NoSuchElementException;
/*     */ import java.util.RandomAccess;
/*     */ import java.util.function.LongConsumer;
/*     */ import java.util.stream.LongStream;
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
/*     */ public class LongImmutableList
/*     */   extends LongLists.ImmutableListBase
/*     */   implements LongList, RandomAccess, Cloneable, Serializable
/*     */ {
/*     */   private static final long serialVersionUID = 0L;
/*  39 */   static final LongImmutableList EMPTY = new LongImmutableList(LongArrays.EMPTY_ARRAY);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private final long[] a;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public LongImmutableList(long[] a) {
/*  52 */     this.a = a;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public LongImmutableList(Collection<? extends Long> c) {
/*  61 */     this(c.isEmpty() ? LongArrays.EMPTY_ARRAY : LongIterators.unwrap(LongIterators.asLongIterator(c.iterator())));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public LongImmutableList(LongCollection c) {
/*  70 */     this(c.isEmpty() ? LongArrays.EMPTY_ARRAY : LongIterators.unwrap(c.iterator()));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public LongImmutableList(LongList l) {
/*  80 */     this(l.isEmpty() ? LongArrays.EMPTY_ARRAY : new long[l.size()]);
/*  81 */     l.getElements(0, this.a, 0, l.size());
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
/*     */   public LongImmutableList(long[] a, int offset, int length) {
/*  93 */     this((length == 0) ? LongArrays.EMPTY_ARRAY : new long[length]);
/*  94 */     System.arraycopy(a, offset, this.a, 0, length);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public LongImmutableList(LongIterator i) {
/* 104 */     this(i.hasNext() ? LongIterators.unwrap(i) : LongArrays.EMPTY_ARRAY);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static LongImmutableList of() {
/* 114 */     return EMPTY;
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
/*     */   public static LongImmutableList of(long... init) {
/* 128 */     return (init.length == 0) ? of() : new LongImmutableList(init);
/*     */   }
/*     */   
/*     */   private static LongImmutableList convertTrustedToImmutableList(LongArrayList arrayList) {
/* 132 */     if (arrayList.isEmpty()) {
/* 133 */       return of();
/*     */     }
/* 135 */     long[] backingArray = arrayList.elements();
/* 136 */     if (arrayList.size() != backingArray.length) {
/* 137 */       backingArray = Arrays.copyOf(backingArray, arrayList.size());
/*     */     }
/* 139 */     return new LongImmutableList(backingArray);
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
/*     */   public static LongImmutableList toList(LongStream stream) {
/* 153 */     return convertTrustedToImmutableList(LongArrayList.toList(stream));
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
/*     */   public static LongImmutableList toListWithExpectedSize(LongStream stream, int expectedSize) {
/* 168 */     return convertTrustedToImmutableList(LongArrayList.toListWithExpectedSize(stream, expectedSize));
/*     */   }
/*     */ 
/*     */   
/*     */   public long getLong(int index) {
/* 173 */     if (index >= this.a.length) throw new IndexOutOfBoundsException("Index (" + index + ") is greater than or equal to list size (" + this.a.length + ")"); 
/* 174 */     return this.a[index];
/*     */   }
/*     */ 
/*     */   
/*     */   public int indexOf(long k) {
/* 179 */     for (int i = 0, size = this.a.length; i < size; ) { if (k == this.a[i]) return i;  i++; }
/* 180 */      return -1;
/*     */   }
/*     */ 
/*     */   
/*     */   public int lastIndexOf(long k) {
/* 185 */     for (int i = this.a.length; i-- != 0;) { if (k == this.a[i]) return i;  }
/* 186 */      return -1;
/*     */   }
/*     */ 
/*     */   
/*     */   public int size() {
/* 191 */     return this.a.length;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isEmpty() {
/* 196 */     return (this.a.length == 0);
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
/*     */   public void getElements(int from, long[] a, int offset, int length) {
/* 209 */     LongArrays.ensureOffsetLength(a, offset, length);
/* 210 */     System.arraycopy(this.a, from, a, offset, length);
/*     */   }
/*     */ 
/*     */   
/*     */   public void forEach(LongConsumer action) {
/* 215 */     for (int i = 0; i < this.a.length; i++) {
/* 216 */       action.accept(this.a[i]);
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   public long[] toLongArray() {
/* 222 */     if (this.a.length == 0) return LongArrays.EMPTY_ARRAY; 
/* 223 */     return (long[])this.a.clone();
/*     */   }
/*     */ 
/*     */   
/*     */   public long[] toArray(long[] a) {
/* 228 */     if (a == null || a.length < size()) a = new long[this.a.length]; 
/* 229 */     System.arraycopy(this.a, 0, a, 0, a.length);
/* 230 */     return a;
/*     */   }
/*     */ 
/*     */   
/*     */   public LongListIterator listIterator(final int index) {
/* 235 */     ensureIndex(index);
/* 236 */     return new LongListIterator() {
/* 237 */         int pos = index;
/*     */ 
/*     */         
/*     */         public boolean hasNext() {
/* 241 */           return (this.pos < LongImmutableList.this.a.length);
/*     */         }
/*     */ 
/*     */         
/*     */         public boolean hasPrevious() {
/* 246 */           return (this.pos > 0);
/*     */         }
/*     */ 
/*     */         
/*     */         public long nextLong() {
/* 251 */           if (!hasNext()) throw new NoSuchElementException(); 
/* 252 */           return LongImmutableList.this.a[this.pos++];
/*     */         }
/*     */ 
/*     */         
/*     */         public long previousLong() {
/* 257 */           if (!hasPrevious()) throw new NoSuchElementException(); 
/* 258 */           return LongImmutableList.this.a[--this.pos];
/*     */         }
/*     */ 
/*     */         
/*     */         public int nextIndex() {
/* 263 */           return this.pos;
/*     */         }
/*     */ 
/*     */         
/*     */         public int previousIndex() {
/* 268 */           return this.pos - 1;
/*     */         }
/*     */ 
/*     */         
/*     */         public void forEachRemaining(LongConsumer action) {
/* 273 */           while (this.pos < LongImmutableList.this.a.length) {
/* 274 */             action.accept(LongImmutableList.this.a[this.pos++]);
/*     */           }
/*     */         }
/*     */ 
/*     */         
/*     */         public void add(long k) {
/* 280 */           throw new UnsupportedOperationException();
/*     */         }
/*     */ 
/*     */         
/*     */         public void set(long k) {
/* 285 */           throw new UnsupportedOperationException();
/*     */         }
/*     */ 
/*     */         
/*     */         public void remove() {
/* 290 */           throw new UnsupportedOperationException();
/*     */         }
/*     */ 
/*     */         
/*     */         public int back(int n) {
/* 295 */           if (n < 0) throw new IllegalArgumentException("Argument must be nonnegative: " + n); 
/* 296 */           int remaining = LongImmutableList.this.a.length - this.pos;
/* 297 */           if (n < remaining) {
/* 298 */             this.pos -= n;
/*     */           } else {
/* 300 */             n = remaining;
/* 301 */             this.pos = 0;
/*     */           } 
/* 303 */           return n;
/*     */         }
/*     */ 
/*     */         
/*     */         public int skip(int n) {
/* 308 */           if (n < 0) throw new IllegalArgumentException("Argument must be nonnegative: " + n); 
/* 309 */           int remaining = LongImmutableList.this.a.length - this.pos;
/* 310 */           if (n < remaining) {
/* 311 */             this.pos += n;
/*     */           } else {
/* 313 */             n = remaining;
/* 314 */             this.pos = LongImmutableList.this.a.length;
/*     */           } 
/* 316 */           return n;
/*     */         }
/*     */       };
/*     */   }
/*     */   
/*     */   private final class Spliterator implements LongSpliterator { int pos;
/*     */     int max;
/*     */     
/*     */     public Spliterator() {
/* 325 */       this(0, LongImmutableList.this.a.length);
/*     */     }
/*     */     
/*     */     private Spliterator(int pos, int max) {
/* 329 */       assert pos <= max : "pos " + pos + " must be <= max " + max;
/* 330 */       this.pos = pos;
/* 331 */       this.max = max;
/*     */     }
/*     */ 
/*     */     
/*     */     public int characteristics() {
/* 336 */       return 17744;
/*     */     }
/*     */ 
/*     */     
/*     */     public long estimateSize() {
/* 341 */       return (this.max - this.pos);
/*     */     }
/*     */ 
/*     */     
/*     */     public boolean tryAdvance(LongConsumer action) {
/* 346 */       if (this.pos >= this.max) return false; 
/* 347 */       action.accept(LongImmutableList.this.a[this.pos++]);
/* 348 */       return true;
/*     */     }
/*     */ 
/*     */     
/*     */     public void forEachRemaining(LongConsumer action) {
/* 353 */       for (; this.pos < this.max; this.pos++) {
/* 354 */         action.accept(LongImmutableList.this.a[this.pos]);
/*     */       }
/*     */     }
/*     */ 
/*     */     
/*     */     public long skip(long n) {
/* 360 */       if (n < 0L) throw new IllegalArgumentException("Argument must be nonnegative: " + n); 
/* 361 */       if (this.pos >= this.max) return 0L; 
/* 362 */       int remaining = this.max - this.pos;
/* 363 */       if (n < remaining) {
/* 364 */         this.pos = SafeMath.safeLongToInt(this.pos + n);
/* 365 */         return n;
/*     */       } 
/* 367 */       n = remaining;
/* 368 */       this.pos = this.max;
/* 369 */       return n;
/*     */     }
/*     */ 
/*     */     
/*     */     public LongSpliterator trySplit() {
/* 374 */       int retLen = this.max - this.pos >> 1;
/* 375 */       if (retLen <= 1) return null; 
/* 376 */       int myNewPos = this.pos + retLen;
/* 377 */       int retMax = myNewPos;
/* 378 */       int oldPos = this.pos;
/* 379 */       this.pos = myNewPos;
/* 380 */       return new Spliterator(oldPos, retMax);
/*     */     } }
/*     */ 
/*     */ 
/*     */   
/*     */   public LongSpliterator spliterator() {
/* 386 */     return new Spliterator();
/*     */   }
/*     */ 
/*     */   
/*     */   private static final class ImmutableSubList
/*     */     extends LongLists.ImmutableListBase
/*     */     implements RandomAccess, Serializable
/*     */   {
/*     */     private static final long serialVersionUID = 7054639518438982401L;
/*     */     
/*     */     final LongImmutableList innerList;
/*     */     
/*     */     final int from;
/*     */     final int to;
/*     */     final transient long[] a;
/*     */     
/*     */     ImmutableSubList(LongImmutableList innerList, int from, int to) {
/* 403 */       this.innerList = innerList;
/* 404 */       this.from = from;
/* 405 */       this.to = to;
/* 406 */       this.a = innerList.a;
/*     */     }
/*     */ 
/*     */     
/*     */     public long getLong(int index) {
/* 411 */       ensureRestrictedIndex(index);
/* 412 */       return this.a[index + this.from];
/*     */     }
/*     */ 
/*     */     
/*     */     public int indexOf(long k) {
/* 417 */       for (int i = this.from; i < this.to; ) { if (k == this.a[i]) return i - this.from;  i++; }
/* 418 */        return -1;
/*     */     }
/*     */ 
/*     */     
/*     */     public int lastIndexOf(long k) {
/* 423 */       for (int i = this.to; i-- != this.from;) { if (k == this.a[i]) return i - this.from;  }
/* 424 */        return -1;
/*     */     }
/*     */ 
/*     */     
/*     */     public int size() {
/* 429 */       return this.to - this.from;
/*     */     }
/*     */ 
/*     */     
/*     */     public boolean isEmpty() {
/* 434 */       return (this.to <= this.from);
/*     */     }
/*     */ 
/*     */     
/*     */     public void getElements(int fromSublistIndex, long[] a, int offset, int length) {
/* 439 */       LongArrays.ensureOffsetLength(a, offset, length);
/* 440 */       ensureRestrictedIndex(fromSublistIndex);
/* 441 */       if (this.from + length > this.to) throw new IndexOutOfBoundsException("Final index " + (this.from + length) + " (startingIndex: " + this.from + " + length: " + length + ") is greater then list length " + size()); 
/* 442 */       System.arraycopy(this.a, fromSublistIndex + this.from, a, offset, length);
/*     */     }
/*     */ 
/*     */     
/*     */     public void forEach(LongConsumer action) {
/* 447 */       for (int i = this.from; i < this.to; i++) {
/* 448 */         action.accept(this.a[i]);
/*     */       }
/*     */     }
/*     */ 
/*     */     
/*     */     public long[] toLongArray() {
/* 454 */       return Arrays.copyOfRange(this.a, this.from, this.to);
/*     */     }
/*     */ 
/*     */     
/*     */     public long[] toArray(long[] a) {
/* 459 */       if (a == null || a.length < size()) a = new long[size()]; 
/* 460 */       System.arraycopy(this.a, this.from, a, 0, size());
/* 461 */       return a;
/*     */     }
/*     */ 
/*     */     
/*     */     public LongListIterator listIterator(final int index) {
/* 466 */       ensureIndex(index);
/* 467 */       return new LongListIterator() {
/* 468 */           int pos = index;
/*     */ 
/*     */           
/*     */           public boolean hasNext() {
/* 472 */             return (this.pos < LongImmutableList.ImmutableSubList.this.to);
/*     */           }
/*     */ 
/*     */           
/*     */           public boolean hasPrevious() {
/* 477 */             return (this.pos > LongImmutableList.ImmutableSubList.this.from);
/*     */           }
/*     */ 
/*     */           
/*     */           public long nextLong() {
/* 482 */             if (!hasNext()) throw new NoSuchElementException(); 
/* 483 */             return LongImmutableList.ImmutableSubList.this.a[this.pos++ + LongImmutableList.ImmutableSubList.this.from];
/*     */           }
/*     */ 
/*     */           
/*     */           public long previousLong() {
/* 488 */             if (!hasPrevious()) throw new NoSuchElementException(); 
/* 489 */             return LongImmutableList.ImmutableSubList.this.a[--this.pos + LongImmutableList.ImmutableSubList.this.from];
/*     */           }
/*     */ 
/*     */           
/*     */           public int nextIndex() {
/* 494 */             return this.pos;
/*     */           }
/*     */ 
/*     */           
/*     */           public int previousIndex() {
/* 499 */             return this.pos - 1;
/*     */           }
/*     */ 
/*     */           
/*     */           public void forEachRemaining(LongConsumer action) {
/* 504 */             while (this.pos < LongImmutableList.ImmutableSubList.this.to) {
/* 505 */               action.accept(LongImmutableList.ImmutableSubList.this.a[this.pos++ + LongImmutableList.ImmutableSubList.this.from]);
/*     */             }
/*     */           }
/*     */ 
/*     */           
/*     */           public void add(long k) {
/* 511 */             throw new UnsupportedOperationException();
/*     */           }
/*     */ 
/*     */           
/*     */           public void set(long k) {
/* 516 */             throw new UnsupportedOperationException();
/*     */           }
/*     */ 
/*     */           
/*     */           public void remove() {
/* 521 */             throw new UnsupportedOperationException();
/*     */           }
/*     */ 
/*     */           
/*     */           public int back(int n) {
/* 526 */             if (n < 0) throw new IllegalArgumentException("Argument must be nonnegative: " + n); 
/* 527 */             int remaining = LongImmutableList.ImmutableSubList.this.to - this.pos;
/* 528 */             if (n < remaining) {
/* 529 */               this.pos -= n;
/*     */             } else {
/* 531 */               n = remaining;
/* 532 */               this.pos = 0;
/*     */             } 
/* 534 */             return n;
/*     */           }
/*     */ 
/*     */           
/*     */           public int skip(int n) {
/* 539 */             if (n < 0) throw new IllegalArgumentException("Argument must be nonnegative: " + n); 
/* 540 */             int remaining = LongImmutableList.ImmutableSubList.this.to - this.pos;
/* 541 */             if (n < remaining) {
/* 542 */               this.pos += n;
/*     */             } else {
/* 544 */               n = remaining;
/* 545 */               this.pos = LongImmutableList.ImmutableSubList.this.to;
/*     */             } 
/* 547 */             return n;
/*     */           }
/*     */         };
/*     */     }
/*     */     
/*     */     private final class SubListSpliterator
/*     */       extends LongSpliterators.EarlyBindingSizeIndexBasedSpliterator {
/*     */       SubListSpliterator() {
/* 555 */         super(LongImmutableList.ImmutableSubList.this.from, LongImmutableList.ImmutableSubList.this.to);
/*     */       }
/*     */ 
/*     */       
/*     */       private SubListSpliterator(int pos, int maxPos) {
/* 560 */         super(pos, maxPos);
/*     */       }
/*     */ 
/*     */ 
/*     */       
/*     */       protected final long get(int i) {
/* 566 */         return LongImmutableList.ImmutableSubList.this.a[i];
/*     */       }
/*     */ 
/*     */       
/*     */       protected final SubListSpliterator makeForSplit(int pos, int maxPos) {
/* 571 */         return new SubListSpliterator(pos, maxPos);
/*     */       }
/*     */ 
/*     */       
/*     */       public boolean tryAdvance(LongConsumer action) {
/* 576 */         if (this.pos >= this.maxPos) return false; 
/* 577 */         action.accept(LongImmutableList.ImmutableSubList.this.a[this.pos++]);
/* 578 */         return true;
/*     */       }
/*     */ 
/*     */       
/*     */       public void forEachRemaining(LongConsumer action) {
/* 583 */         int max = this.maxPos;
/* 584 */         while (this.pos < max) {
/* 585 */           action.accept(LongImmutableList.ImmutableSubList.this.a[this.pos++]);
/*     */         }
/*     */       }
/*     */ 
/*     */       
/*     */       public int characteristics() {
/* 591 */         return 17744;
/*     */       }
/*     */     }
/*     */ 
/*     */     
/*     */     public LongSpliterator spliterator() {
/* 597 */       return new SubListSpliterator();
/*     */     }
/*     */     
/*     */     boolean contentsEquals(long[] otherA, int otherAFrom, int otherATo) {
/* 601 */       if (this.a == otherA && this.from == otherAFrom && this.to == otherATo) {
/* 602 */         return true;
/*     */       }
/* 604 */       if (otherATo - otherAFrom != size()) {
/* 605 */         return false;
/*     */       }
/* 607 */       int pos = this.from, otherPos = otherAFrom;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 613 */       while (pos < this.to) { if (this.a[pos++] != otherA[otherPos++]) return false;  }
/* 614 */        return true;
/*     */     }
/*     */ 
/*     */     
/*     */     public boolean equals(Object o) {
/* 619 */       if (o == this) return true; 
/* 620 */       if (o == null) return false; 
/* 621 */       if (!(o instanceof List)) return false; 
/* 622 */       if (o instanceof LongImmutableList) {
/*     */         
/* 624 */         LongImmutableList other = (LongImmutableList)o;
/* 625 */         return contentsEquals(other.a, 0, other.size());
/*     */       } 
/* 627 */       if (o instanceof ImmutableSubList) {
/*     */         
/* 629 */         ImmutableSubList other = (ImmutableSubList)o;
/* 630 */         return contentsEquals(other.a, other.from, other.to);
/*     */       } 
/* 632 */       return super.equals(o);
/*     */     }
/*     */     
/*     */     int contentsCompareTo(long[] otherA, int otherAFrom, int otherATo) {
/* 636 */       if (this.a == otherA && this.from == otherAFrom && this.to == otherATo) return 0;
/*     */       
/*     */       int i;
/*     */       int j;
/* 640 */       for (i = this.from, j = otherAFrom; i < this.to && i < otherATo; i++, j++) {
/* 641 */         long e1 = this.a[i];
/* 642 */         long e2 = otherA[j]; int r;
/* 643 */         if ((r = Long.compare(e1, e2)) != 0) return r; 
/*     */       } 
/* 645 */       return (i < otherATo) ? -1 : ((i < this.to) ? 1 : 0);
/*     */     }
/*     */ 
/*     */     
/*     */     public int compareTo(List<? extends Long> l) {
/* 650 */       if (l instanceof LongImmutableList) {
/*     */         
/* 652 */         LongImmutableList other = (LongImmutableList)l;
/* 653 */         return contentsCompareTo(other.a, 0, other.size());
/*     */       } 
/* 655 */       if (l instanceof ImmutableSubList) {
/*     */         
/* 657 */         ImmutableSubList other = (ImmutableSubList)l;
/* 658 */         return contentsCompareTo(other.a, other.from, other.to);
/*     */       } 
/* 660 */       return super.compareTo(l);
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     private Object readResolve() throws ObjectStreamException {
/*     */       try {
/* 668 */         return this.innerList.subList(this.from, this.to);
/* 669 */       } catch (IllegalArgumentException|IndexOutOfBoundsException ex) {
/* 670 */         throw (InvalidObjectException)(new InvalidObjectException(ex.getMessage())).initCause(ex);
/*     */       } 
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public LongList subList(int from, int to) {
/* 679 */       ensureIndex(from);
/* 680 */       ensureIndex(to);
/* 681 */       if (from == to) return LongImmutableList.EMPTY; 
/* 682 */       if (from > to) throw new IllegalArgumentException("Start index (" + from + ") is greater than end index (" + to + ")"); 
/* 683 */       return new ImmutableSubList(this.innerList, from + this.from, to + this.from);
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
/*     */   public LongList subList(int from, int to) {
/* 698 */     if (from == 0 && to == size()) return this; 
/* 699 */     ensureIndex(from);
/* 700 */     ensureIndex(to);
/* 701 */     if (from == to) return EMPTY; 
/* 702 */     if (from > to) throw new IllegalArgumentException("Start index (" + from + ") is greater than end index (" + to + ")"); 
/* 703 */     return new ImmutableSubList(this, from, to);
/*     */   }
/*     */ 
/*     */   
/*     */   public LongImmutableList clone() {
/* 708 */     return this;
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
/*     */   public boolean equals(LongImmutableList l) {
/* 721 */     if (l == this) return true; 
/* 722 */     if (this.a == l.a) return true; 
/* 723 */     int s = size();
/* 724 */     if (s != l.size()) return false; 
/* 725 */     long[] a1 = this.a;
/* 726 */     long[] a2 = l.a;
/* 727 */     return Arrays.equals(a1, a2);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean equals(Object o) {
/* 733 */     if (o == this) return true; 
/* 734 */     if (o == null) return false; 
/* 735 */     if (!(o instanceof List)) return false; 
/* 736 */     if (o instanceof LongImmutableList) {
/* 737 */       return equals((LongImmutableList)o);
/*     */     }
/* 739 */     if (o instanceof ImmutableSubList)
/*     */     {
/* 741 */       return ((ImmutableSubList)o).equals(this);
/*     */     }
/* 743 */     return super.equals(o);
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
/*     */   public int compareTo(LongImmutableList l) {
/* 758 */     if (this.a == l.a) return 0;
/*     */     
/* 760 */     int s1 = size(), s2 = l.size();
/* 761 */     long[] a1 = this.a, a2 = l.a;
/*     */     
/*     */     int i;
/* 764 */     for (i = 0; i < s1 && i < s2; i++) {
/* 765 */       long e1 = a1[i];
/* 766 */       long e2 = a2[i]; int r;
/* 767 */       if ((r = Long.compare(e1, e2)) != 0) return r; 
/*     */     } 
/* 769 */     return (i < s2) ? -1 : ((i < s1) ? 1 : 0);
/*     */   }
/*     */ 
/*     */   
/*     */   public int compareTo(List<? extends Long> l) {
/* 774 */     if (l instanceof LongImmutableList) {
/* 775 */       return compareTo((LongImmutableList)l);
/*     */     }
/* 777 */     if (l instanceof ImmutableSubList) {
/*     */ 
/*     */       
/* 780 */       ImmutableSubList other = (ImmutableSubList)l;
/*     */       
/* 782 */       return -other.compareTo(this);
/*     */     } 
/* 784 */     return super.compareTo(l);
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\i\\unimi\dsi\fastutil\longs\LongImmutableList.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */