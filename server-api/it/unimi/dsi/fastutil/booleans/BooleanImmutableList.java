/*     */ package it.unimi.dsi.fastutil.booleans;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class BooleanImmutableList
/*     */   extends BooleanLists.ImmutableListBase
/*     */   implements BooleanList, RandomAccess, Cloneable, Serializable
/*     */ {
/*     */   private static final long serialVersionUID = 0L;
/*  39 */   static final BooleanImmutableList EMPTY = new BooleanImmutableList(BooleanArrays.EMPTY_ARRAY);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private final boolean[] a;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public BooleanImmutableList(boolean[] a) {
/*  52 */     this.a = a;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public BooleanImmutableList(Collection<? extends Boolean> c) {
/*  61 */     this(c.isEmpty() ? BooleanArrays.EMPTY_ARRAY : BooleanIterators.unwrap(BooleanIterators.asBooleanIterator(c.iterator())));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public BooleanImmutableList(BooleanCollection c) {
/*  70 */     this(c.isEmpty() ? BooleanArrays.EMPTY_ARRAY : BooleanIterators.unwrap(c.iterator()));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public BooleanImmutableList(BooleanList l) {
/*  80 */     this(l.isEmpty() ? BooleanArrays.EMPTY_ARRAY : new boolean[l.size()]);
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
/*     */   public BooleanImmutableList(boolean[] a, int offset, int length) {
/*  93 */     this((length == 0) ? BooleanArrays.EMPTY_ARRAY : new boolean[length]);
/*  94 */     System.arraycopy(a, offset, this.a, 0, length);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public BooleanImmutableList(BooleanIterator i) {
/* 104 */     this(i.hasNext() ? BooleanIterators.unwrap(i) : BooleanArrays.EMPTY_ARRAY);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static BooleanImmutableList of() {
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
/*     */   public static BooleanImmutableList of(boolean... init) {
/* 128 */     return (init.length == 0) ? of() : new BooleanImmutableList(init);
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean getBoolean(int index) {
/* 133 */     if (index >= this.a.length) throw new IndexOutOfBoundsException("Index (" + index + ") is greater than or equal to list size (" + this.a.length + ")"); 
/* 134 */     return this.a[index];
/*     */   }
/*     */ 
/*     */   
/*     */   public int indexOf(boolean k) {
/* 139 */     for (int i = 0, size = this.a.length; i < size; ) { if (k == this.a[i]) return i;  i++; }
/* 140 */      return -1;
/*     */   }
/*     */ 
/*     */   
/*     */   public int lastIndexOf(boolean k) {
/* 145 */     for (int i = this.a.length; i-- != 0;) { if (k == this.a[i]) return i;  }
/* 146 */      return -1;
/*     */   }
/*     */ 
/*     */   
/*     */   public int size() {
/* 151 */     return this.a.length;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isEmpty() {
/* 156 */     return (this.a.length == 0);
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
/*     */   public void getElements(int from, boolean[] a, int offset, int length) {
/* 169 */     BooleanArrays.ensureOffsetLength(a, offset, length);
/* 170 */     System.arraycopy(this.a, from, a, offset, length);
/*     */   }
/*     */ 
/*     */   
/*     */   public void forEach(BooleanConsumer action) {
/* 175 */     for (int i = 0; i < this.a.length; i++) {
/* 176 */       action.accept(this.a[i]);
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean[] toBooleanArray() {
/* 182 */     if (this.a.length == 0) return BooleanArrays.EMPTY_ARRAY; 
/* 183 */     return (boolean[])this.a.clone();
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean[] toArray(boolean[] a) {
/* 188 */     if (a == null || a.length < size()) a = new boolean[this.a.length]; 
/* 189 */     System.arraycopy(this.a, 0, a, 0, a.length);
/* 190 */     return a;
/*     */   }
/*     */ 
/*     */   
/*     */   public BooleanListIterator listIterator(final int index) {
/* 195 */     ensureIndex(index);
/* 196 */     return new BooleanListIterator() {
/* 197 */         int pos = index;
/*     */ 
/*     */         
/*     */         public boolean hasNext() {
/* 201 */           return (this.pos < BooleanImmutableList.this.a.length);
/*     */         }
/*     */ 
/*     */         
/*     */         public boolean hasPrevious() {
/* 206 */           return (this.pos > 0);
/*     */         }
/*     */ 
/*     */         
/*     */         public boolean nextBoolean() {
/* 211 */           if (!hasNext()) throw new NoSuchElementException(); 
/* 212 */           return BooleanImmutableList.this.a[this.pos++];
/*     */         }
/*     */ 
/*     */         
/*     */         public boolean previousBoolean() {
/* 217 */           if (!hasPrevious()) throw new NoSuchElementException(); 
/* 218 */           return BooleanImmutableList.this.a[--this.pos];
/*     */         }
/*     */ 
/*     */         
/*     */         public int nextIndex() {
/* 223 */           return this.pos;
/*     */         }
/*     */ 
/*     */         
/*     */         public int previousIndex() {
/* 228 */           return this.pos - 1;
/*     */         }
/*     */ 
/*     */         
/*     */         public void forEachRemaining(BooleanConsumer action) {
/* 233 */           while (this.pos < BooleanImmutableList.this.a.length) {
/* 234 */             action.accept(BooleanImmutableList.this.a[this.pos++]);
/*     */           }
/*     */         }
/*     */ 
/*     */         
/*     */         public void add(boolean k) {
/* 240 */           throw new UnsupportedOperationException();
/*     */         }
/*     */ 
/*     */         
/*     */         public void set(boolean k) {
/* 245 */           throw new UnsupportedOperationException();
/*     */         }
/*     */ 
/*     */         
/*     */         public void remove() {
/* 250 */           throw new UnsupportedOperationException();
/*     */         }
/*     */ 
/*     */         
/*     */         public int back(int n) {
/* 255 */           if (n < 0) throw new IllegalArgumentException("Argument must be nonnegative: " + n); 
/* 256 */           int remaining = BooleanImmutableList.this.a.length - this.pos;
/* 257 */           if (n < remaining) {
/* 258 */             this.pos -= n;
/*     */           } else {
/* 260 */             n = remaining;
/* 261 */             this.pos = 0;
/*     */           } 
/* 263 */           return n;
/*     */         }
/*     */ 
/*     */         
/*     */         public int skip(int n) {
/* 268 */           if (n < 0) throw new IllegalArgumentException("Argument must be nonnegative: " + n); 
/* 269 */           int remaining = BooleanImmutableList.this.a.length - this.pos;
/* 270 */           if (n < remaining) {
/* 271 */             this.pos += n;
/*     */           } else {
/* 273 */             n = remaining;
/* 274 */             this.pos = BooleanImmutableList.this.a.length;
/*     */           } 
/* 276 */           return n;
/*     */         }
/*     */       };
/*     */   }
/*     */   
/*     */   private final class Spliterator implements BooleanSpliterator { int pos;
/*     */     int max;
/*     */     
/*     */     public Spliterator() {
/* 285 */       this(0, BooleanImmutableList.this.a.length);
/*     */     }
/*     */     
/*     */     private Spliterator(int pos, int max) {
/* 289 */       assert pos <= max : "pos " + pos + " must be <= max " + max;
/* 290 */       this.pos = pos;
/* 291 */       this.max = max;
/*     */     }
/*     */ 
/*     */     
/*     */     public int characteristics() {
/* 296 */       return 17744;
/*     */     }
/*     */ 
/*     */     
/*     */     public long estimateSize() {
/* 301 */       return (this.max - this.pos);
/*     */     }
/*     */ 
/*     */     
/*     */     public boolean tryAdvance(BooleanConsumer action) {
/* 306 */       if (this.pos >= this.max) return false; 
/* 307 */       action.accept(BooleanImmutableList.this.a[this.pos++]);
/* 308 */       return true;
/*     */     }
/*     */ 
/*     */     
/*     */     public void forEachRemaining(BooleanConsumer action) {
/* 313 */       for (; this.pos < this.max; this.pos++) {
/* 314 */         action.accept(BooleanImmutableList.this.a[this.pos]);
/*     */       }
/*     */     }
/*     */ 
/*     */     
/*     */     public long skip(long n) {
/* 320 */       if (n < 0L) throw new IllegalArgumentException("Argument must be nonnegative: " + n); 
/* 321 */       if (this.pos >= this.max) return 0L; 
/* 322 */       int remaining = this.max - this.pos;
/* 323 */       if (n < remaining) {
/* 324 */         this.pos = SafeMath.safeLongToInt(this.pos + n);
/* 325 */         return n;
/*     */       } 
/* 327 */       n = remaining;
/* 328 */       this.pos = this.max;
/* 329 */       return n;
/*     */     }
/*     */ 
/*     */     
/*     */     public BooleanSpliterator trySplit() {
/* 334 */       int retLen = this.max - this.pos >> 1;
/* 335 */       if (retLen <= 1) return null; 
/* 336 */       int myNewPos = this.pos + retLen;
/* 337 */       int retMax = myNewPos;
/* 338 */       int oldPos = this.pos;
/* 339 */       this.pos = myNewPos;
/* 340 */       return new Spliterator(oldPos, retMax);
/*     */     } }
/*     */ 
/*     */ 
/*     */   
/*     */   public BooleanSpliterator spliterator() {
/* 346 */     return new Spliterator();
/*     */   }
/*     */ 
/*     */   
/*     */   private static final class ImmutableSubList
/*     */     extends BooleanLists.ImmutableListBase
/*     */     implements RandomAccess, Serializable
/*     */   {
/*     */     private static final long serialVersionUID = 7054639518438982401L;
/*     */     
/*     */     final BooleanImmutableList innerList;
/*     */     
/*     */     final int from;
/*     */     final int to;
/*     */     final transient boolean[] a;
/*     */     
/*     */     ImmutableSubList(BooleanImmutableList innerList, int from, int to) {
/* 363 */       this.innerList = innerList;
/* 364 */       this.from = from;
/* 365 */       this.to = to;
/* 366 */       this.a = innerList.a;
/*     */     }
/*     */ 
/*     */     
/*     */     public boolean getBoolean(int index) {
/* 371 */       ensureRestrictedIndex(index);
/* 372 */       return this.a[index + this.from];
/*     */     }
/*     */ 
/*     */     
/*     */     public int indexOf(boolean k) {
/* 377 */       for (int i = this.from; i < this.to; ) { if (k == this.a[i]) return i - this.from;  i++; }
/* 378 */        return -1;
/*     */     }
/*     */ 
/*     */     
/*     */     public int lastIndexOf(boolean k) {
/* 383 */       for (int i = this.to; i-- != this.from;) { if (k == this.a[i]) return i - this.from;  }
/* 384 */        return -1;
/*     */     }
/*     */ 
/*     */     
/*     */     public int size() {
/* 389 */       return this.to - this.from;
/*     */     }
/*     */ 
/*     */     
/*     */     public boolean isEmpty() {
/* 394 */       return (this.to <= this.from);
/*     */     }
/*     */ 
/*     */     
/*     */     public void getElements(int fromSublistIndex, boolean[] a, int offset, int length) {
/* 399 */       BooleanArrays.ensureOffsetLength(a, offset, length);
/* 400 */       ensureRestrictedIndex(fromSublistIndex);
/* 401 */       if (this.from + length > this.to) throw new IndexOutOfBoundsException("Final index " + (this.from + length) + " (startingIndex: " + this.from + " + length: " + length + ") is greater then list length " + size()); 
/* 402 */       System.arraycopy(this.a, fromSublistIndex + this.from, a, offset, length);
/*     */     }
/*     */ 
/*     */     
/*     */     public void forEach(BooleanConsumer action) {
/* 407 */       for (int i = this.from; i < this.to; i++) {
/* 408 */         action.accept(this.a[i]);
/*     */       }
/*     */     }
/*     */ 
/*     */     
/*     */     public boolean[] toBooleanArray() {
/* 414 */       return Arrays.copyOfRange(this.a, this.from, this.to);
/*     */     }
/*     */ 
/*     */     
/*     */     public boolean[] toArray(boolean[] a) {
/* 419 */       if (a == null || a.length < size()) a = new boolean[size()]; 
/* 420 */       System.arraycopy(this.a, this.from, a, 0, size());
/* 421 */       return a;
/*     */     }
/*     */ 
/*     */     
/*     */     public BooleanListIterator listIterator(final int index) {
/* 426 */       ensureIndex(index);
/* 427 */       return new BooleanListIterator() {
/* 428 */           int pos = index;
/*     */ 
/*     */           
/*     */           public boolean hasNext() {
/* 432 */             return (this.pos < BooleanImmutableList.ImmutableSubList.this.to);
/*     */           }
/*     */ 
/*     */           
/*     */           public boolean hasPrevious() {
/* 437 */             return (this.pos > BooleanImmutableList.ImmutableSubList.this.from);
/*     */           }
/*     */ 
/*     */           
/*     */           public boolean nextBoolean() {
/* 442 */             if (!hasNext()) throw new NoSuchElementException(); 
/* 443 */             return BooleanImmutableList.ImmutableSubList.this.a[this.pos++ + BooleanImmutableList.ImmutableSubList.this.from];
/*     */           }
/*     */ 
/*     */           
/*     */           public boolean previousBoolean() {
/* 448 */             if (!hasPrevious()) throw new NoSuchElementException(); 
/* 449 */             return BooleanImmutableList.ImmutableSubList.this.a[--this.pos + BooleanImmutableList.ImmutableSubList.this.from];
/*     */           }
/*     */ 
/*     */           
/*     */           public int nextIndex() {
/* 454 */             return this.pos;
/*     */           }
/*     */ 
/*     */           
/*     */           public int previousIndex() {
/* 459 */             return this.pos - 1;
/*     */           }
/*     */ 
/*     */           
/*     */           public void forEachRemaining(BooleanConsumer action) {
/* 464 */             while (this.pos < BooleanImmutableList.ImmutableSubList.this.to) {
/* 465 */               action.accept(BooleanImmutableList.ImmutableSubList.this.a[this.pos++ + BooleanImmutableList.ImmutableSubList.this.from]);
/*     */             }
/*     */           }
/*     */ 
/*     */           
/*     */           public void add(boolean k) {
/* 471 */             throw new UnsupportedOperationException();
/*     */           }
/*     */ 
/*     */           
/*     */           public void set(boolean k) {
/* 476 */             throw new UnsupportedOperationException();
/*     */           }
/*     */ 
/*     */           
/*     */           public void remove() {
/* 481 */             throw new UnsupportedOperationException();
/*     */           }
/*     */ 
/*     */           
/*     */           public int back(int n) {
/* 486 */             if (n < 0) throw new IllegalArgumentException("Argument must be nonnegative: " + n); 
/* 487 */             int remaining = BooleanImmutableList.ImmutableSubList.this.to - this.pos;
/* 488 */             if (n < remaining) {
/* 489 */               this.pos -= n;
/*     */             } else {
/* 491 */               n = remaining;
/* 492 */               this.pos = 0;
/*     */             } 
/* 494 */             return n;
/*     */           }
/*     */ 
/*     */           
/*     */           public int skip(int n) {
/* 499 */             if (n < 0) throw new IllegalArgumentException("Argument must be nonnegative: " + n); 
/* 500 */             int remaining = BooleanImmutableList.ImmutableSubList.this.to - this.pos;
/* 501 */             if (n < remaining) {
/* 502 */               this.pos += n;
/*     */             } else {
/* 504 */               n = remaining;
/* 505 */               this.pos = BooleanImmutableList.ImmutableSubList.this.to;
/*     */             } 
/* 507 */             return n;
/*     */           }
/*     */         };
/*     */     }
/*     */     
/*     */     private final class SubListSpliterator
/*     */       extends BooleanSpliterators.EarlyBindingSizeIndexBasedSpliterator {
/*     */       SubListSpliterator() {
/* 515 */         super(BooleanImmutableList.ImmutableSubList.this.from, BooleanImmutableList.ImmutableSubList.this.to);
/*     */       }
/*     */ 
/*     */       
/*     */       private SubListSpliterator(int pos, int maxPos) {
/* 520 */         super(pos, maxPos);
/*     */       }
/*     */ 
/*     */ 
/*     */       
/*     */       protected final boolean get(int i) {
/* 526 */         return BooleanImmutableList.ImmutableSubList.this.a[i];
/*     */       }
/*     */ 
/*     */       
/*     */       protected final SubListSpliterator makeForSplit(int pos, int maxPos) {
/* 531 */         return new SubListSpliterator(pos, maxPos);
/*     */       }
/*     */ 
/*     */       
/*     */       public boolean tryAdvance(BooleanConsumer action) {
/* 536 */         if (this.pos >= this.maxPos) return false; 
/* 537 */         action.accept(BooleanImmutableList.ImmutableSubList.this.a[this.pos++]);
/* 538 */         return true;
/*     */       }
/*     */ 
/*     */       
/*     */       public void forEachRemaining(BooleanConsumer action) {
/* 543 */         int max = this.maxPos;
/* 544 */         while (this.pos < max) {
/* 545 */           action.accept(BooleanImmutableList.ImmutableSubList.this.a[this.pos++]);
/*     */         }
/*     */       }
/*     */ 
/*     */       
/*     */       public int characteristics() {
/* 551 */         return 17744;
/*     */       }
/*     */     }
/*     */ 
/*     */     
/*     */     public BooleanSpliterator spliterator() {
/* 557 */       return new SubListSpliterator();
/*     */     }
/*     */     
/*     */     boolean contentsEquals(boolean[] otherA, int otherAFrom, int otherATo) {
/* 561 */       if (this.a == otherA && this.from == otherAFrom && this.to == otherATo) {
/* 562 */         return true;
/*     */       }
/* 564 */       if (otherATo - otherAFrom != size()) {
/* 565 */         return false;
/*     */       }
/* 567 */       int pos = this.from, otherPos = otherAFrom;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 573 */       while (pos < this.to) { if (this.a[pos++] != otherA[otherPos++]) return false;  }
/* 574 */        return true;
/*     */     }
/*     */ 
/*     */     
/*     */     public boolean equals(Object o) {
/* 579 */       if (o == this) return true; 
/* 580 */       if (o == null) return false; 
/* 581 */       if (!(o instanceof List)) return false; 
/* 582 */       if (o instanceof BooleanImmutableList) {
/*     */         
/* 584 */         BooleanImmutableList other = (BooleanImmutableList)o;
/* 585 */         return contentsEquals(other.a, 0, other.size());
/*     */       } 
/* 587 */       if (o instanceof ImmutableSubList) {
/*     */         
/* 589 */         ImmutableSubList other = (ImmutableSubList)o;
/* 590 */         return contentsEquals(other.a, other.from, other.to);
/*     */       } 
/* 592 */       return super.equals(o);
/*     */     }
/*     */     
/*     */     int contentsCompareTo(boolean[] otherA, int otherAFrom, int otherATo) {
/* 596 */       if (this.a == otherA && this.from == otherAFrom && this.to == otherATo) return 0;
/*     */       
/*     */       int i;
/*     */       int j;
/* 600 */       for (i = this.from, j = otherAFrom; i < this.to && i < otherATo; i++, j++) {
/* 601 */         boolean e1 = this.a[i];
/* 602 */         boolean e2 = otherA[j]; int r;
/* 603 */         if ((r = Boolean.compare(e1, e2)) != 0) return r; 
/*     */       } 
/* 605 */       return (i < otherATo) ? -1 : ((i < this.to) ? 1 : 0);
/*     */     }
/*     */ 
/*     */     
/*     */     public int compareTo(List<? extends Boolean> l) {
/* 610 */       if (l instanceof BooleanImmutableList) {
/*     */         
/* 612 */         BooleanImmutableList other = (BooleanImmutableList)l;
/* 613 */         return contentsCompareTo(other.a, 0, other.size());
/*     */       } 
/* 615 */       if (l instanceof ImmutableSubList) {
/*     */         
/* 617 */         ImmutableSubList other = (ImmutableSubList)l;
/* 618 */         return contentsCompareTo(other.a, other.from, other.to);
/*     */       } 
/* 620 */       return super.compareTo(l);
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     private Object readResolve() throws ObjectStreamException {
/*     */       try {
/* 628 */         return this.innerList.subList(this.from, this.to);
/* 629 */       } catch (IllegalArgumentException|IndexOutOfBoundsException ex) {
/* 630 */         throw (InvalidObjectException)(new InvalidObjectException(ex.getMessage())).initCause(ex);
/*     */       } 
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public BooleanList subList(int from, int to) {
/* 639 */       ensureIndex(from);
/* 640 */       ensureIndex(to);
/* 641 */       if (from == to) return BooleanImmutableList.EMPTY; 
/* 642 */       if (from > to) throw new IllegalArgumentException("Start index (" + from + ") is greater than end index (" + to + ")"); 
/* 643 */       return new ImmutableSubList(this.innerList, from + this.from, to + this.from);
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
/*     */   public BooleanList subList(int from, int to) {
/* 658 */     if (from == 0 && to == size()) return this; 
/* 659 */     ensureIndex(from);
/* 660 */     ensureIndex(to);
/* 661 */     if (from == to) return EMPTY; 
/* 662 */     if (from > to) throw new IllegalArgumentException("Start index (" + from + ") is greater than end index (" + to + ")"); 
/* 663 */     return new ImmutableSubList(this, from, to);
/*     */   }
/*     */ 
/*     */   
/*     */   public BooleanImmutableList clone() {
/* 668 */     return this;
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
/*     */   public boolean equals(BooleanImmutableList l) {
/* 681 */     if (l == this) return true; 
/* 682 */     if (this.a == l.a) return true; 
/* 683 */     int s = size();
/* 684 */     if (s != l.size()) return false; 
/* 685 */     boolean[] a1 = this.a;
/* 686 */     boolean[] a2 = l.a;
/* 687 */     return Arrays.equals(a1, a2);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean equals(Object o) {
/* 693 */     if (o == this) return true; 
/* 694 */     if (o == null) return false; 
/* 695 */     if (!(o instanceof List)) return false; 
/* 696 */     if (o instanceof BooleanImmutableList) {
/* 697 */       return equals((BooleanImmutableList)o);
/*     */     }
/* 699 */     if (o instanceof ImmutableSubList)
/*     */     {
/* 701 */       return ((ImmutableSubList)o).equals(this);
/*     */     }
/* 703 */     return super.equals(o);
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
/*     */   public int compareTo(BooleanImmutableList l) {
/* 718 */     if (this.a == l.a) return 0;
/*     */     
/* 720 */     int s1 = size(), s2 = l.size();
/* 721 */     boolean[] a1 = this.a, a2 = l.a;
/*     */     
/*     */     int i;
/* 724 */     for (i = 0; i < s1 && i < s2; i++) {
/* 725 */       boolean e1 = a1[i];
/* 726 */       boolean e2 = a2[i]; int r;
/* 727 */       if ((r = Boolean.compare(e1, e2)) != 0) return r; 
/*     */     } 
/* 729 */     return (i < s2) ? -1 : ((i < s1) ? 1 : 0);
/*     */   }
/*     */ 
/*     */   
/*     */   public int compareTo(List<? extends Boolean> l) {
/* 734 */     if (l instanceof BooleanImmutableList) {
/* 735 */       return compareTo((BooleanImmutableList)l);
/*     */     }
/* 737 */     if (l instanceof ImmutableSubList) {
/*     */ 
/*     */       
/* 740 */       ImmutableSubList other = (ImmutableSubList)l;
/*     */       
/* 742 */       return -other.compareTo(this);
/*     */     } 
/* 744 */     return super.compareTo(l);
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\i\\unimi\dsi\fastutil\booleans\BooleanImmutableList.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */