/*     */ package it.unimi.dsi.fastutil.doubles;
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
/*     */ import java.util.function.DoubleConsumer;
/*     */ import java.util.stream.DoubleStream;
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
/*     */ public class DoubleImmutableList
/*     */   extends DoubleLists.ImmutableListBase
/*     */   implements DoubleList, RandomAccess, Cloneable, Serializable
/*     */ {
/*     */   private static final long serialVersionUID = 0L;
/*  39 */   static final DoubleImmutableList EMPTY = new DoubleImmutableList(DoubleArrays.EMPTY_ARRAY);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private final double[] a;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public DoubleImmutableList(double[] a) {
/*  52 */     this.a = a;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public DoubleImmutableList(Collection<? extends Double> c) {
/*  61 */     this(c.isEmpty() ? DoubleArrays.EMPTY_ARRAY : DoubleIterators.unwrap(DoubleIterators.asDoubleIterator(c.iterator())));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public DoubleImmutableList(DoubleCollection c) {
/*  70 */     this(c.isEmpty() ? DoubleArrays.EMPTY_ARRAY : DoubleIterators.unwrap(c.iterator()));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public DoubleImmutableList(DoubleList l) {
/*  80 */     this(l.isEmpty() ? DoubleArrays.EMPTY_ARRAY : new double[l.size()]);
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
/*     */   public DoubleImmutableList(double[] a, int offset, int length) {
/*  93 */     this((length == 0) ? DoubleArrays.EMPTY_ARRAY : new double[length]);
/*  94 */     System.arraycopy(a, offset, this.a, 0, length);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public DoubleImmutableList(DoubleIterator i) {
/* 104 */     this(i.hasNext() ? DoubleIterators.unwrap(i) : DoubleArrays.EMPTY_ARRAY);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static DoubleImmutableList of() {
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
/*     */   public static DoubleImmutableList of(double... init) {
/* 128 */     return (init.length == 0) ? of() : new DoubleImmutableList(init);
/*     */   }
/*     */   
/*     */   private static DoubleImmutableList convertTrustedToImmutableList(DoubleArrayList arrayList) {
/* 132 */     if (arrayList.isEmpty()) {
/* 133 */       return of();
/*     */     }
/* 135 */     double[] backingArray = arrayList.elements();
/* 136 */     if (arrayList.size() != backingArray.length) {
/* 137 */       backingArray = Arrays.copyOf(backingArray, arrayList.size());
/*     */     }
/* 139 */     return new DoubleImmutableList(backingArray);
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
/*     */   public static DoubleImmutableList toList(DoubleStream stream) {
/* 153 */     return convertTrustedToImmutableList(DoubleArrayList.toList(stream));
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
/*     */   public static DoubleImmutableList toListWithExpectedSize(DoubleStream stream, int expectedSize) {
/* 168 */     return convertTrustedToImmutableList(DoubleArrayList.toListWithExpectedSize(stream, expectedSize));
/*     */   }
/*     */ 
/*     */   
/*     */   public double getDouble(int index) {
/* 173 */     if (index >= this.a.length) throw new IndexOutOfBoundsException("Index (" + index + ") is greater than or equal to list size (" + this.a.length + ")"); 
/* 174 */     return this.a[index];
/*     */   }
/*     */ 
/*     */   
/*     */   public int indexOf(double k) {
/* 179 */     int i = 0;
/* 180 */     for (int size = this.a.length; i < size; ) { if (Double.doubleToLongBits(k) == Double.doubleToLongBits(this.a[i])) return i;  i++; }
/* 181 */      return -1;
/*     */   }
/*     */ 
/*     */   
/*     */   public int lastIndexOf(double k) {
/* 186 */     for (int i = this.a.length; i-- != 0;) { if (Double.doubleToLongBits(k) == Double.doubleToLongBits(this.a[i])) return i;  }
/* 187 */      return -1;
/*     */   }
/*     */ 
/*     */   
/*     */   public int size() {
/* 192 */     return this.a.length;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isEmpty() {
/* 197 */     return (this.a.length == 0);
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
/*     */   public void getElements(int from, double[] a, int offset, int length) {
/* 210 */     DoubleArrays.ensureOffsetLength(a, offset, length);
/* 211 */     System.arraycopy(this.a, from, a, offset, length);
/*     */   }
/*     */ 
/*     */   
/*     */   public void forEach(DoubleConsumer action) {
/* 216 */     for (int i = 0; i < this.a.length; i++) {
/* 217 */       action.accept(this.a[i]);
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   public double[] toDoubleArray() {
/* 223 */     if (this.a.length == 0) return DoubleArrays.EMPTY_ARRAY; 
/* 224 */     return (double[])this.a.clone();
/*     */   }
/*     */ 
/*     */   
/*     */   public double[] toArray(double[] a) {
/* 229 */     if (a == null || a.length < size()) a = new double[this.a.length]; 
/* 230 */     System.arraycopy(this.a, 0, a, 0, a.length);
/* 231 */     return a;
/*     */   }
/*     */ 
/*     */   
/*     */   public DoubleListIterator listIterator(final int index) {
/* 236 */     ensureIndex(index);
/* 237 */     return new DoubleListIterator() {
/* 238 */         int pos = index;
/*     */ 
/*     */         
/*     */         public boolean hasNext() {
/* 242 */           return (this.pos < DoubleImmutableList.this.a.length);
/*     */         }
/*     */ 
/*     */         
/*     */         public boolean hasPrevious() {
/* 247 */           return (this.pos > 0);
/*     */         }
/*     */ 
/*     */         
/*     */         public double nextDouble() {
/* 252 */           if (!hasNext()) throw new NoSuchElementException(); 
/* 253 */           return DoubleImmutableList.this.a[this.pos++];
/*     */         }
/*     */ 
/*     */         
/*     */         public double previousDouble() {
/* 258 */           if (!hasPrevious()) throw new NoSuchElementException(); 
/* 259 */           return DoubleImmutableList.this.a[--this.pos];
/*     */         }
/*     */ 
/*     */         
/*     */         public int nextIndex() {
/* 264 */           return this.pos;
/*     */         }
/*     */ 
/*     */         
/*     */         public int previousIndex() {
/* 269 */           return this.pos - 1;
/*     */         }
/*     */ 
/*     */         
/*     */         public void forEachRemaining(DoubleConsumer action) {
/* 274 */           while (this.pos < DoubleImmutableList.this.a.length) {
/* 275 */             action.accept(DoubleImmutableList.this.a[this.pos++]);
/*     */           }
/*     */         }
/*     */ 
/*     */         
/*     */         public void add(double k) {
/* 281 */           throw new UnsupportedOperationException();
/*     */         }
/*     */ 
/*     */         
/*     */         public void set(double k) {
/* 286 */           throw new UnsupportedOperationException();
/*     */         }
/*     */ 
/*     */         
/*     */         public void remove() {
/* 291 */           throw new UnsupportedOperationException();
/*     */         }
/*     */ 
/*     */         
/*     */         public int back(int n) {
/* 296 */           if (n < 0) throw new IllegalArgumentException("Argument must be nonnegative: " + n); 
/* 297 */           int remaining = DoubleImmutableList.this.a.length - this.pos;
/* 298 */           if (n < remaining) {
/* 299 */             this.pos -= n;
/*     */           } else {
/* 301 */             n = remaining;
/* 302 */             this.pos = 0;
/*     */           } 
/* 304 */           return n;
/*     */         }
/*     */ 
/*     */         
/*     */         public int skip(int n) {
/* 309 */           if (n < 0) throw new IllegalArgumentException("Argument must be nonnegative: " + n); 
/* 310 */           int remaining = DoubleImmutableList.this.a.length - this.pos;
/* 311 */           if (n < remaining) {
/* 312 */             this.pos += n;
/*     */           } else {
/* 314 */             n = remaining;
/* 315 */             this.pos = DoubleImmutableList.this.a.length;
/*     */           } 
/* 317 */           return n;
/*     */         }
/*     */       };
/*     */   }
/*     */   
/*     */   private final class Spliterator implements DoubleSpliterator { int pos;
/*     */     int max;
/*     */     
/*     */     public Spliterator() {
/* 326 */       this(0, DoubleImmutableList.this.a.length);
/*     */     }
/*     */     
/*     */     private Spliterator(int pos, int max) {
/* 330 */       assert pos <= max : "pos " + pos + " must be <= max " + max;
/* 331 */       this.pos = pos;
/* 332 */       this.max = max;
/*     */     }
/*     */ 
/*     */     
/*     */     public int characteristics() {
/* 337 */       return 17744;
/*     */     }
/*     */ 
/*     */     
/*     */     public long estimateSize() {
/* 342 */       return (this.max - this.pos);
/*     */     }
/*     */ 
/*     */     
/*     */     public boolean tryAdvance(DoubleConsumer action) {
/* 347 */       if (this.pos >= this.max) return false; 
/* 348 */       action.accept(DoubleImmutableList.this.a[this.pos++]);
/* 349 */       return true;
/*     */     }
/*     */ 
/*     */     
/*     */     public void forEachRemaining(DoubleConsumer action) {
/* 354 */       for (; this.pos < this.max; this.pos++) {
/* 355 */         action.accept(DoubleImmutableList.this.a[this.pos]);
/*     */       }
/*     */     }
/*     */ 
/*     */     
/*     */     public long skip(long n) {
/* 361 */       if (n < 0L) throw new IllegalArgumentException("Argument must be nonnegative: " + n); 
/* 362 */       if (this.pos >= this.max) return 0L; 
/* 363 */       int remaining = this.max - this.pos;
/* 364 */       if (n < remaining) {
/* 365 */         this.pos = SafeMath.safeLongToInt(this.pos + n);
/* 366 */         return n;
/*     */       } 
/* 368 */       n = remaining;
/* 369 */       this.pos = this.max;
/* 370 */       return n;
/*     */     }
/*     */ 
/*     */     
/*     */     public DoubleSpliterator trySplit() {
/* 375 */       int retLen = this.max - this.pos >> 1;
/* 376 */       if (retLen <= 1) return null; 
/* 377 */       int myNewPos = this.pos + retLen;
/* 378 */       int retMax = myNewPos;
/* 379 */       int oldPos = this.pos;
/* 380 */       this.pos = myNewPos;
/* 381 */       return new Spliterator(oldPos, retMax);
/*     */     } }
/*     */ 
/*     */ 
/*     */   
/*     */   public DoubleSpliterator spliterator() {
/* 387 */     return new Spliterator();
/*     */   }
/*     */ 
/*     */   
/*     */   private static final class ImmutableSubList
/*     */     extends DoubleLists.ImmutableListBase
/*     */     implements RandomAccess, Serializable
/*     */   {
/*     */     private static final long serialVersionUID = 7054639518438982401L;
/*     */     
/*     */     final DoubleImmutableList innerList;
/*     */     
/*     */     final int from;
/*     */     final int to;
/*     */     final transient double[] a;
/*     */     
/*     */     ImmutableSubList(DoubleImmutableList innerList, int from, int to) {
/* 404 */       this.innerList = innerList;
/* 405 */       this.from = from;
/* 406 */       this.to = to;
/* 407 */       this.a = innerList.a;
/*     */     }
/*     */ 
/*     */     
/*     */     public double getDouble(int index) {
/* 412 */       ensureRestrictedIndex(index);
/* 413 */       return this.a[index + this.from];
/*     */     }
/*     */ 
/*     */     
/*     */     public int indexOf(double k) {
/* 418 */       for (int i = this.from; i < this.to; ) { if (Double.doubleToLongBits(k) == Double.doubleToLongBits(this.a[i])) return i - this.from;  i++; }
/* 419 */        return -1;
/*     */     }
/*     */ 
/*     */     
/*     */     public int lastIndexOf(double k) {
/* 424 */       for (int i = this.to; i-- != this.from;) { if (Double.doubleToLongBits(k) == Double.doubleToLongBits(this.a[i])) return i - this.from;  }
/* 425 */        return -1;
/*     */     }
/*     */ 
/*     */     
/*     */     public int size() {
/* 430 */       return this.to - this.from;
/*     */     }
/*     */ 
/*     */     
/*     */     public boolean isEmpty() {
/* 435 */       return (this.to <= this.from);
/*     */     }
/*     */ 
/*     */     
/*     */     public void getElements(int fromSublistIndex, double[] a, int offset, int length) {
/* 440 */       DoubleArrays.ensureOffsetLength(a, offset, length);
/* 441 */       ensureRestrictedIndex(fromSublistIndex);
/* 442 */       if (this.from + length > this.to) throw new IndexOutOfBoundsException("Final index " + (this.from + length) + " (startingIndex: " + this.from + " + length: " + length + ") is greater then list length " + size()); 
/* 443 */       System.arraycopy(this.a, fromSublistIndex + this.from, a, offset, length);
/*     */     }
/*     */ 
/*     */     
/*     */     public void forEach(DoubleConsumer action) {
/* 448 */       for (int i = this.from; i < this.to; i++) {
/* 449 */         action.accept(this.a[i]);
/*     */       }
/*     */     }
/*     */ 
/*     */     
/*     */     public double[] toDoubleArray() {
/* 455 */       return Arrays.copyOfRange(this.a, this.from, this.to);
/*     */     }
/*     */ 
/*     */     
/*     */     public double[] toArray(double[] a) {
/* 460 */       if (a == null || a.length < size()) a = new double[size()]; 
/* 461 */       System.arraycopy(this.a, this.from, a, 0, size());
/* 462 */       return a;
/*     */     }
/*     */ 
/*     */     
/*     */     public DoubleListIterator listIterator(final int index) {
/* 467 */       ensureIndex(index);
/* 468 */       return new DoubleListIterator() {
/* 469 */           int pos = index;
/*     */ 
/*     */           
/*     */           public boolean hasNext() {
/* 473 */             return (this.pos < DoubleImmutableList.ImmutableSubList.this.to);
/*     */           }
/*     */ 
/*     */           
/*     */           public boolean hasPrevious() {
/* 478 */             return (this.pos > DoubleImmutableList.ImmutableSubList.this.from);
/*     */           }
/*     */ 
/*     */           
/*     */           public double nextDouble() {
/* 483 */             if (!hasNext()) throw new NoSuchElementException(); 
/* 484 */             return DoubleImmutableList.ImmutableSubList.this.a[this.pos++ + DoubleImmutableList.ImmutableSubList.this.from];
/*     */           }
/*     */ 
/*     */           
/*     */           public double previousDouble() {
/* 489 */             if (!hasPrevious()) throw new NoSuchElementException(); 
/* 490 */             return DoubleImmutableList.ImmutableSubList.this.a[--this.pos + DoubleImmutableList.ImmutableSubList.this.from];
/*     */           }
/*     */ 
/*     */           
/*     */           public int nextIndex() {
/* 495 */             return this.pos;
/*     */           }
/*     */ 
/*     */           
/*     */           public int previousIndex() {
/* 500 */             return this.pos - 1;
/*     */           }
/*     */ 
/*     */           
/*     */           public void forEachRemaining(DoubleConsumer action) {
/* 505 */             while (this.pos < DoubleImmutableList.ImmutableSubList.this.to) {
/* 506 */               action.accept(DoubleImmutableList.ImmutableSubList.this.a[this.pos++ + DoubleImmutableList.ImmutableSubList.this.from]);
/*     */             }
/*     */           }
/*     */ 
/*     */           
/*     */           public void add(double k) {
/* 512 */             throw new UnsupportedOperationException();
/*     */           }
/*     */ 
/*     */           
/*     */           public void set(double k) {
/* 517 */             throw new UnsupportedOperationException();
/*     */           }
/*     */ 
/*     */           
/*     */           public void remove() {
/* 522 */             throw new UnsupportedOperationException();
/*     */           }
/*     */ 
/*     */           
/*     */           public int back(int n) {
/* 527 */             if (n < 0) throw new IllegalArgumentException("Argument must be nonnegative: " + n); 
/* 528 */             int remaining = DoubleImmutableList.ImmutableSubList.this.to - this.pos;
/* 529 */             if (n < remaining) {
/* 530 */               this.pos -= n;
/*     */             } else {
/* 532 */               n = remaining;
/* 533 */               this.pos = 0;
/*     */             } 
/* 535 */             return n;
/*     */           }
/*     */ 
/*     */           
/*     */           public int skip(int n) {
/* 540 */             if (n < 0) throw new IllegalArgumentException("Argument must be nonnegative: " + n); 
/* 541 */             int remaining = DoubleImmutableList.ImmutableSubList.this.to - this.pos;
/* 542 */             if (n < remaining) {
/* 543 */               this.pos += n;
/*     */             } else {
/* 545 */               n = remaining;
/* 546 */               this.pos = DoubleImmutableList.ImmutableSubList.this.to;
/*     */             } 
/* 548 */             return n;
/*     */           }
/*     */         };
/*     */     }
/*     */     
/*     */     private final class SubListSpliterator
/*     */       extends DoubleSpliterators.EarlyBindingSizeIndexBasedSpliterator {
/*     */       SubListSpliterator() {
/* 556 */         super(DoubleImmutableList.ImmutableSubList.this.from, DoubleImmutableList.ImmutableSubList.this.to);
/*     */       }
/*     */ 
/*     */       
/*     */       private SubListSpliterator(int pos, int maxPos) {
/* 561 */         super(pos, maxPos);
/*     */       }
/*     */ 
/*     */ 
/*     */       
/*     */       protected final double get(int i) {
/* 567 */         return DoubleImmutableList.ImmutableSubList.this.a[i];
/*     */       }
/*     */ 
/*     */       
/*     */       protected final SubListSpliterator makeForSplit(int pos, int maxPos) {
/* 572 */         return new SubListSpliterator(pos, maxPos);
/*     */       }
/*     */ 
/*     */       
/*     */       public boolean tryAdvance(DoubleConsumer action) {
/* 577 */         if (this.pos >= this.maxPos) return false; 
/* 578 */         action.accept(DoubleImmutableList.ImmutableSubList.this.a[this.pos++]);
/* 579 */         return true;
/*     */       }
/*     */ 
/*     */       
/*     */       public void forEachRemaining(DoubleConsumer action) {
/* 584 */         int max = this.maxPos;
/* 585 */         while (this.pos < max) {
/* 586 */           action.accept(DoubleImmutableList.ImmutableSubList.this.a[this.pos++]);
/*     */         }
/*     */       }
/*     */ 
/*     */       
/*     */       public int characteristics() {
/* 592 */         return 17744;
/*     */       }
/*     */     }
/*     */ 
/*     */     
/*     */     public DoubleSpliterator spliterator() {
/* 598 */       return new SubListSpliterator();
/*     */     }
/*     */     
/*     */     boolean contentsEquals(double[] otherA, int otherAFrom, int otherATo) {
/* 602 */       if (this.a == otherA && this.from == otherAFrom && this.to == otherATo) {
/* 603 */         return true;
/*     */       }
/* 605 */       if (otherATo - otherAFrom != size()) {
/* 606 */         return false;
/*     */       }
/* 608 */       int pos = this.from, otherPos = otherAFrom;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 614 */       while (pos < this.to) { if (this.a[pos++] != otherA[otherPos++]) return false;  }
/* 615 */        return true;
/*     */     }
/*     */ 
/*     */     
/*     */     public boolean equals(Object o) {
/* 620 */       if (o == this) return true; 
/* 621 */       if (o == null) return false; 
/* 622 */       if (!(o instanceof List)) return false; 
/* 623 */       if (o instanceof DoubleImmutableList) {
/*     */         
/* 625 */         DoubleImmutableList other = (DoubleImmutableList)o;
/* 626 */         return contentsEquals(other.a, 0, other.size());
/*     */       } 
/* 628 */       if (o instanceof ImmutableSubList) {
/*     */         
/* 630 */         ImmutableSubList other = (ImmutableSubList)o;
/* 631 */         return contentsEquals(other.a, other.from, other.to);
/*     */       } 
/* 633 */       return super.equals(o);
/*     */     }
/*     */     
/*     */     int contentsCompareTo(double[] otherA, int otherAFrom, int otherATo) {
/* 637 */       if (this.a == otherA && this.from == otherAFrom && this.to == otherATo) return 0;
/*     */       
/*     */       int i;
/*     */       int j;
/* 641 */       for (i = this.from, j = otherAFrom; i < this.to && i < otherATo; i++, j++) {
/* 642 */         double e1 = this.a[i];
/* 643 */         double e2 = otherA[j]; int r;
/* 644 */         if ((r = Double.compare(e1, e2)) != 0) return r; 
/*     */       } 
/* 646 */       return (i < otherATo) ? -1 : ((i < this.to) ? 1 : 0);
/*     */     }
/*     */ 
/*     */     
/*     */     public int compareTo(List<? extends Double> l) {
/* 651 */       if (l instanceof DoubleImmutableList) {
/*     */         
/* 653 */         DoubleImmutableList other = (DoubleImmutableList)l;
/* 654 */         return contentsCompareTo(other.a, 0, other.size());
/*     */       } 
/* 656 */       if (l instanceof ImmutableSubList) {
/*     */         
/* 658 */         ImmutableSubList other = (ImmutableSubList)l;
/* 659 */         return contentsCompareTo(other.a, other.from, other.to);
/*     */       } 
/* 661 */       return super.compareTo(l);
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     private Object readResolve() throws ObjectStreamException {
/*     */       try {
/* 669 */         return this.innerList.subList(this.from, this.to);
/* 670 */       } catch (IllegalArgumentException|IndexOutOfBoundsException ex) {
/* 671 */         throw (InvalidObjectException)(new InvalidObjectException(ex.getMessage())).initCause(ex);
/*     */       } 
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public DoubleList subList(int from, int to) {
/* 680 */       ensureIndex(from);
/* 681 */       ensureIndex(to);
/* 682 */       if (from == to) return DoubleImmutableList.EMPTY; 
/* 683 */       if (from > to) throw new IllegalArgumentException("Start index (" + from + ") is greater than end index (" + to + ")"); 
/* 684 */       return new ImmutableSubList(this.innerList, from + this.from, to + this.from);
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
/*     */   public DoubleList subList(int from, int to) {
/* 699 */     if (from == 0 && to == size()) return this; 
/* 700 */     ensureIndex(from);
/* 701 */     ensureIndex(to);
/* 702 */     if (from == to) return EMPTY; 
/* 703 */     if (from > to) throw new IllegalArgumentException("Start index (" + from + ") is greater than end index (" + to + ")"); 
/* 704 */     return new ImmutableSubList(this, from, to);
/*     */   }
/*     */ 
/*     */   
/*     */   public DoubleImmutableList clone() {
/* 709 */     return this;
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
/*     */   public boolean equals(DoubleImmutableList l) {
/* 722 */     if (l == this) return true; 
/* 723 */     if (this.a == l.a) return true; 
/* 724 */     int s = size();
/* 725 */     if (s != l.size()) return false; 
/* 726 */     double[] a1 = this.a;
/* 727 */     double[] a2 = l.a;
/* 728 */     return Arrays.equals(a1, a2);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean equals(Object o) {
/* 734 */     if (o == this) return true; 
/* 735 */     if (o == null) return false; 
/* 736 */     if (!(o instanceof List)) return false; 
/* 737 */     if (o instanceof DoubleImmutableList) {
/* 738 */       return equals((DoubleImmutableList)o);
/*     */     }
/* 740 */     if (o instanceof ImmutableSubList)
/*     */     {
/* 742 */       return ((ImmutableSubList)o).equals(this);
/*     */     }
/* 744 */     return super.equals(o);
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
/*     */   public int compareTo(DoubleImmutableList l) {
/* 759 */     if (this.a == l.a) return 0;
/*     */     
/* 761 */     int s1 = size(), s2 = l.size();
/* 762 */     double[] a1 = this.a, a2 = l.a;
/*     */     
/*     */     int i;
/* 765 */     for (i = 0; i < s1 && i < s2; i++) {
/* 766 */       double e1 = a1[i];
/* 767 */       double e2 = a2[i]; int r;
/* 768 */       if ((r = Double.compare(e1, e2)) != 0) return r; 
/*     */     } 
/* 770 */     return (i < s2) ? -1 : ((i < s1) ? 1 : 0);
/*     */   }
/*     */ 
/*     */   
/*     */   public int compareTo(List<? extends Double> l) {
/* 775 */     if (l instanceof DoubleImmutableList) {
/* 776 */       return compareTo((DoubleImmutableList)l);
/*     */     }
/* 778 */     if (l instanceof ImmutableSubList) {
/*     */ 
/*     */       
/* 781 */       ImmutableSubList other = (ImmutableSubList)l;
/*     */       
/* 783 */       return -other.compareTo(this);
/*     */     } 
/* 785 */     return super.compareTo(l);
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\i\\unimi\dsi\fastutil\doubles\DoubleImmutableList.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */