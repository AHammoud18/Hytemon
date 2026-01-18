/*     */ package it.unimi.dsi.fastutil.floats;
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
/*     */ public class FloatImmutableList
/*     */   extends FloatLists.ImmutableListBase
/*     */   implements FloatList, RandomAccess, Cloneable, Serializable
/*     */ {
/*     */   private static final long serialVersionUID = 0L;
/*  39 */   static final FloatImmutableList EMPTY = new FloatImmutableList(FloatArrays.EMPTY_ARRAY);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private final float[] a;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public FloatImmutableList(float[] a) {
/*  52 */     this.a = a;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public FloatImmutableList(Collection<? extends Float> c) {
/*  61 */     this(c.isEmpty() ? FloatArrays.EMPTY_ARRAY : FloatIterators.unwrap(FloatIterators.asFloatIterator(c.iterator())));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public FloatImmutableList(FloatCollection c) {
/*  70 */     this(c.isEmpty() ? FloatArrays.EMPTY_ARRAY : FloatIterators.unwrap(c.iterator()));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public FloatImmutableList(FloatList l) {
/*  80 */     this(l.isEmpty() ? FloatArrays.EMPTY_ARRAY : new float[l.size()]);
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
/*     */   public FloatImmutableList(float[] a, int offset, int length) {
/*  93 */     this((length == 0) ? FloatArrays.EMPTY_ARRAY : new float[length]);
/*  94 */     System.arraycopy(a, offset, this.a, 0, length);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public FloatImmutableList(FloatIterator i) {
/* 104 */     this(i.hasNext() ? FloatIterators.unwrap(i) : FloatArrays.EMPTY_ARRAY);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static FloatImmutableList of() {
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
/*     */   public static FloatImmutableList of(float... init) {
/* 128 */     return (init.length == 0) ? of() : new FloatImmutableList(init);
/*     */   }
/*     */ 
/*     */   
/*     */   public float getFloat(int index) {
/* 133 */     if (index >= this.a.length) throw new IndexOutOfBoundsException("Index (" + index + ") is greater than or equal to list size (" + this.a.length + ")"); 
/* 134 */     return this.a[index];
/*     */   }
/*     */ 
/*     */   
/*     */   public int indexOf(float k) {
/* 139 */     int i = 0;
/* 140 */     for (int size = this.a.length; i < size; ) { if (Float.floatToIntBits(k) == Float.floatToIntBits(this.a[i])) return i;  i++; }
/* 141 */      return -1;
/*     */   }
/*     */ 
/*     */   
/*     */   public int lastIndexOf(float k) {
/* 146 */     for (int i = this.a.length; i-- != 0;) { if (Float.floatToIntBits(k) == Float.floatToIntBits(this.a[i])) return i;  }
/* 147 */      return -1;
/*     */   }
/*     */ 
/*     */   
/*     */   public int size() {
/* 152 */     return this.a.length;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isEmpty() {
/* 157 */     return (this.a.length == 0);
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
/*     */   public void getElements(int from, float[] a, int offset, int length) {
/* 170 */     FloatArrays.ensureOffsetLength(a, offset, length);
/* 171 */     System.arraycopy(this.a, from, a, offset, length);
/*     */   }
/*     */ 
/*     */   
/*     */   public void forEach(FloatConsumer action) {
/* 176 */     for (int i = 0; i < this.a.length; i++) {
/* 177 */       action.accept(this.a[i]);
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   public float[] toFloatArray() {
/* 183 */     if (this.a.length == 0) return FloatArrays.EMPTY_ARRAY; 
/* 184 */     return (float[])this.a.clone();
/*     */   }
/*     */ 
/*     */   
/*     */   public float[] toArray(float[] a) {
/* 189 */     if (a == null || a.length < size()) a = new float[this.a.length]; 
/* 190 */     System.arraycopy(this.a, 0, a, 0, a.length);
/* 191 */     return a;
/*     */   }
/*     */ 
/*     */   
/*     */   public FloatListIterator listIterator(final int index) {
/* 196 */     ensureIndex(index);
/* 197 */     return new FloatListIterator() {
/* 198 */         int pos = index;
/*     */ 
/*     */         
/*     */         public boolean hasNext() {
/* 202 */           return (this.pos < FloatImmutableList.this.a.length);
/*     */         }
/*     */ 
/*     */         
/*     */         public boolean hasPrevious() {
/* 207 */           return (this.pos > 0);
/*     */         }
/*     */ 
/*     */         
/*     */         public float nextFloat() {
/* 212 */           if (!hasNext()) throw new NoSuchElementException(); 
/* 213 */           return FloatImmutableList.this.a[this.pos++];
/*     */         }
/*     */ 
/*     */         
/*     */         public float previousFloat() {
/* 218 */           if (!hasPrevious()) throw new NoSuchElementException(); 
/* 219 */           return FloatImmutableList.this.a[--this.pos];
/*     */         }
/*     */ 
/*     */         
/*     */         public int nextIndex() {
/* 224 */           return this.pos;
/*     */         }
/*     */ 
/*     */         
/*     */         public int previousIndex() {
/* 229 */           return this.pos - 1;
/*     */         }
/*     */ 
/*     */         
/*     */         public void forEachRemaining(FloatConsumer action) {
/* 234 */           while (this.pos < FloatImmutableList.this.a.length) {
/* 235 */             action.accept(FloatImmutableList.this.a[this.pos++]);
/*     */           }
/*     */         }
/*     */ 
/*     */         
/*     */         public void add(float k) {
/* 241 */           throw new UnsupportedOperationException();
/*     */         }
/*     */ 
/*     */         
/*     */         public void set(float k) {
/* 246 */           throw new UnsupportedOperationException();
/*     */         }
/*     */ 
/*     */         
/*     */         public void remove() {
/* 251 */           throw new UnsupportedOperationException();
/*     */         }
/*     */ 
/*     */         
/*     */         public int back(int n) {
/* 256 */           if (n < 0) throw new IllegalArgumentException("Argument must be nonnegative: " + n); 
/* 257 */           int remaining = FloatImmutableList.this.a.length - this.pos;
/* 258 */           if (n < remaining) {
/* 259 */             this.pos -= n;
/*     */           } else {
/* 261 */             n = remaining;
/* 262 */             this.pos = 0;
/*     */           } 
/* 264 */           return n;
/*     */         }
/*     */ 
/*     */         
/*     */         public int skip(int n) {
/* 269 */           if (n < 0) throw new IllegalArgumentException("Argument must be nonnegative: " + n); 
/* 270 */           int remaining = FloatImmutableList.this.a.length - this.pos;
/* 271 */           if (n < remaining) {
/* 272 */             this.pos += n;
/*     */           } else {
/* 274 */             n = remaining;
/* 275 */             this.pos = FloatImmutableList.this.a.length;
/*     */           } 
/* 277 */           return n;
/*     */         }
/*     */       };
/*     */   }
/*     */   
/*     */   private final class Spliterator implements FloatSpliterator { int pos;
/*     */     int max;
/*     */     
/*     */     public Spliterator() {
/* 286 */       this(0, FloatImmutableList.this.a.length);
/*     */     }
/*     */     
/*     */     private Spliterator(int pos, int max) {
/* 290 */       assert pos <= max : "pos " + pos + " must be <= max " + max;
/* 291 */       this.pos = pos;
/* 292 */       this.max = max;
/*     */     }
/*     */ 
/*     */     
/*     */     public int characteristics() {
/* 297 */       return 17744;
/*     */     }
/*     */ 
/*     */     
/*     */     public long estimateSize() {
/* 302 */       return (this.max - this.pos);
/*     */     }
/*     */ 
/*     */     
/*     */     public boolean tryAdvance(FloatConsumer action) {
/* 307 */       if (this.pos >= this.max) return false; 
/* 308 */       action.accept(FloatImmutableList.this.a[this.pos++]);
/* 309 */       return true;
/*     */     }
/*     */ 
/*     */     
/*     */     public void forEachRemaining(FloatConsumer action) {
/* 314 */       for (; this.pos < this.max; this.pos++) {
/* 315 */         action.accept(FloatImmutableList.this.a[this.pos]);
/*     */       }
/*     */     }
/*     */ 
/*     */     
/*     */     public long skip(long n) {
/* 321 */       if (n < 0L) throw new IllegalArgumentException("Argument must be nonnegative: " + n); 
/* 322 */       if (this.pos >= this.max) return 0L; 
/* 323 */       int remaining = this.max - this.pos;
/* 324 */       if (n < remaining) {
/* 325 */         this.pos = SafeMath.safeLongToInt(this.pos + n);
/* 326 */         return n;
/*     */       } 
/* 328 */       n = remaining;
/* 329 */       this.pos = this.max;
/* 330 */       return n;
/*     */     }
/*     */ 
/*     */     
/*     */     public FloatSpliterator trySplit() {
/* 335 */       int retLen = this.max - this.pos >> 1;
/* 336 */       if (retLen <= 1) return null; 
/* 337 */       int myNewPos = this.pos + retLen;
/* 338 */       int retMax = myNewPos;
/* 339 */       int oldPos = this.pos;
/* 340 */       this.pos = myNewPos;
/* 341 */       return new Spliterator(oldPos, retMax);
/*     */     } }
/*     */ 
/*     */ 
/*     */   
/*     */   public FloatSpliterator spliterator() {
/* 347 */     return new Spliterator();
/*     */   }
/*     */ 
/*     */   
/*     */   private static final class ImmutableSubList
/*     */     extends FloatLists.ImmutableListBase
/*     */     implements RandomAccess, Serializable
/*     */   {
/*     */     private static final long serialVersionUID = 7054639518438982401L;
/*     */     
/*     */     final FloatImmutableList innerList;
/*     */     
/*     */     final int from;
/*     */     final int to;
/*     */     final transient float[] a;
/*     */     
/*     */     ImmutableSubList(FloatImmutableList innerList, int from, int to) {
/* 364 */       this.innerList = innerList;
/* 365 */       this.from = from;
/* 366 */       this.to = to;
/* 367 */       this.a = innerList.a;
/*     */     }
/*     */ 
/*     */     
/*     */     public float getFloat(int index) {
/* 372 */       ensureRestrictedIndex(index);
/* 373 */       return this.a[index + this.from];
/*     */     }
/*     */ 
/*     */     
/*     */     public int indexOf(float k) {
/* 378 */       for (int i = this.from; i < this.to; ) { if (Float.floatToIntBits(k) == Float.floatToIntBits(this.a[i])) return i - this.from;  i++; }
/* 379 */        return -1;
/*     */     }
/*     */ 
/*     */     
/*     */     public int lastIndexOf(float k) {
/* 384 */       for (int i = this.to; i-- != this.from;) { if (Float.floatToIntBits(k) == Float.floatToIntBits(this.a[i])) return i - this.from;  }
/* 385 */        return -1;
/*     */     }
/*     */ 
/*     */     
/*     */     public int size() {
/* 390 */       return this.to - this.from;
/*     */     }
/*     */ 
/*     */     
/*     */     public boolean isEmpty() {
/* 395 */       return (this.to <= this.from);
/*     */     }
/*     */ 
/*     */     
/*     */     public void getElements(int fromSublistIndex, float[] a, int offset, int length) {
/* 400 */       FloatArrays.ensureOffsetLength(a, offset, length);
/* 401 */       ensureRestrictedIndex(fromSublistIndex);
/* 402 */       if (this.from + length > this.to) throw new IndexOutOfBoundsException("Final index " + (this.from + length) + " (startingIndex: " + this.from + " + length: " + length + ") is greater then list length " + size()); 
/* 403 */       System.arraycopy(this.a, fromSublistIndex + this.from, a, offset, length);
/*     */     }
/*     */ 
/*     */     
/*     */     public void forEach(FloatConsumer action) {
/* 408 */       for (int i = this.from; i < this.to; i++) {
/* 409 */         action.accept(this.a[i]);
/*     */       }
/*     */     }
/*     */ 
/*     */     
/*     */     public float[] toFloatArray() {
/* 415 */       return Arrays.copyOfRange(this.a, this.from, this.to);
/*     */     }
/*     */ 
/*     */     
/*     */     public float[] toArray(float[] a) {
/* 420 */       if (a == null || a.length < size()) a = new float[size()]; 
/* 421 */       System.arraycopy(this.a, this.from, a, 0, size());
/* 422 */       return a;
/*     */     }
/*     */ 
/*     */     
/*     */     public FloatListIterator listIterator(final int index) {
/* 427 */       ensureIndex(index);
/* 428 */       return new FloatListIterator() {
/* 429 */           int pos = index;
/*     */ 
/*     */           
/*     */           public boolean hasNext() {
/* 433 */             return (this.pos < FloatImmutableList.ImmutableSubList.this.to);
/*     */           }
/*     */ 
/*     */           
/*     */           public boolean hasPrevious() {
/* 438 */             return (this.pos > FloatImmutableList.ImmutableSubList.this.from);
/*     */           }
/*     */ 
/*     */           
/*     */           public float nextFloat() {
/* 443 */             if (!hasNext()) throw new NoSuchElementException(); 
/* 444 */             return FloatImmutableList.ImmutableSubList.this.a[this.pos++ + FloatImmutableList.ImmutableSubList.this.from];
/*     */           }
/*     */ 
/*     */           
/*     */           public float previousFloat() {
/* 449 */             if (!hasPrevious()) throw new NoSuchElementException(); 
/* 450 */             return FloatImmutableList.ImmutableSubList.this.a[--this.pos + FloatImmutableList.ImmutableSubList.this.from];
/*     */           }
/*     */ 
/*     */           
/*     */           public int nextIndex() {
/* 455 */             return this.pos;
/*     */           }
/*     */ 
/*     */           
/*     */           public int previousIndex() {
/* 460 */             return this.pos - 1;
/*     */           }
/*     */ 
/*     */           
/*     */           public void forEachRemaining(FloatConsumer action) {
/* 465 */             while (this.pos < FloatImmutableList.ImmutableSubList.this.to) {
/* 466 */               action.accept(FloatImmutableList.ImmutableSubList.this.a[this.pos++ + FloatImmutableList.ImmutableSubList.this.from]);
/*     */             }
/*     */           }
/*     */ 
/*     */           
/*     */           public void add(float k) {
/* 472 */             throw new UnsupportedOperationException();
/*     */           }
/*     */ 
/*     */           
/*     */           public void set(float k) {
/* 477 */             throw new UnsupportedOperationException();
/*     */           }
/*     */ 
/*     */           
/*     */           public void remove() {
/* 482 */             throw new UnsupportedOperationException();
/*     */           }
/*     */ 
/*     */           
/*     */           public int back(int n) {
/* 487 */             if (n < 0) throw new IllegalArgumentException("Argument must be nonnegative: " + n); 
/* 488 */             int remaining = FloatImmutableList.ImmutableSubList.this.to - this.pos;
/* 489 */             if (n < remaining) {
/* 490 */               this.pos -= n;
/*     */             } else {
/* 492 */               n = remaining;
/* 493 */               this.pos = 0;
/*     */             } 
/* 495 */             return n;
/*     */           }
/*     */ 
/*     */           
/*     */           public int skip(int n) {
/* 500 */             if (n < 0) throw new IllegalArgumentException("Argument must be nonnegative: " + n); 
/* 501 */             int remaining = FloatImmutableList.ImmutableSubList.this.to - this.pos;
/* 502 */             if (n < remaining) {
/* 503 */               this.pos += n;
/*     */             } else {
/* 505 */               n = remaining;
/* 506 */               this.pos = FloatImmutableList.ImmutableSubList.this.to;
/*     */             } 
/* 508 */             return n;
/*     */           }
/*     */         };
/*     */     }
/*     */     
/*     */     private final class SubListSpliterator
/*     */       extends FloatSpliterators.EarlyBindingSizeIndexBasedSpliterator {
/*     */       SubListSpliterator() {
/* 516 */         super(FloatImmutableList.ImmutableSubList.this.from, FloatImmutableList.ImmutableSubList.this.to);
/*     */       }
/*     */ 
/*     */       
/*     */       private SubListSpliterator(int pos, int maxPos) {
/* 521 */         super(pos, maxPos);
/*     */       }
/*     */ 
/*     */ 
/*     */       
/*     */       protected final float get(int i) {
/* 527 */         return FloatImmutableList.ImmutableSubList.this.a[i];
/*     */       }
/*     */ 
/*     */       
/*     */       protected final SubListSpliterator makeForSplit(int pos, int maxPos) {
/* 532 */         return new SubListSpliterator(pos, maxPos);
/*     */       }
/*     */ 
/*     */       
/*     */       public boolean tryAdvance(FloatConsumer action) {
/* 537 */         if (this.pos >= this.maxPos) return false; 
/* 538 */         action.accept(FloatImmutableList.ImmutableSubList.this.a[this.pos++]);
/* 539 */         return true;
/*     */       }
/*     */ 
/*     */       
/*     */       public void forEachRemaining(FloatConsumer action) {
/* 544 */         int max = this.maxPos;
/* 545 */         while (this.pos < max) {
/* 546 */           action.accept(FloatImmutableList.ImmutableSubList.this.a[this.pos++]);
/*     */         }
/*     */       }
/*     */ 
/*     */       
/*     */       public int characteristics() {
/* 552 */         return 17744;
/*     */       }
/*     */     }
/*     */ 
/*     */     
/*     */     public FloatSpliterator spliterator() {
/* 558 */       return new SubListSpliterator();
/*     */     }
/*     */     
/*     */     boolean contentsEquals(float[] otherA, int otherAFrom, int otherATo) {
/* 562 */       if (this.a == otherA && this.from == otherAFrom && this.to == otherATo) {
/* 563 */         return true;
/*     */       }
/* 565 */       if (otherATo - otherAFrom != size()) {
/* 566 */         return false;
/*     */       }
/* 568 */       int pos = this.from, otherPos = otherAFrom;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 574 */       while (pos < this.to) { if (this.a[pos++] != otherA[otherPos++]) return false;  }
/* 575 */        return true;
/*     */     }
/*     */ 
/*     */     
/*     */     public boolean equals(Object o) {
/* 580 */       if (o == this) return true; 
/* 581 */       if (o == null) return false; 
/* 582 */       if (!(o instanceof List)) return false; 
/* 583 */       if (o instanceof FloatImmutableList) {
/*     */         
/* 585 */         FloatImmutableList other = (FloatImmutableList)o;
/* 586 */         return contentsEquals(other.a, 0, other.size());
/*     */       } 
/* 588 */       if (o instanceof ImmutableSubList) {
/*     */         
/* 590 */         ImmutableSubList other = (ImmutableSubList)o;
/* 591 */         return contentsEquals(other.a, other.from, other.to);
/*     */       } 
/* 593 */       return super.equals(o);
/*     */     }
/*     */     
/*     */     int contentsCompareTo(float[] otherA, int otherAFrom, int otherATo) {
/* 597 */       if (this.a == otherA && this.from == otherAFrom && this.to == otherATo) return 0;
/*     */       
/*     */       int i;
/*     */       int j;
/* 601 */       for (i = this.from, j = otherAFrom; i < this.to && i < otherATo; i++, j++) {
/* 602 */         float e1 = this.a[i];
/* 603 */         float e2 = otherA[j]; int r;
/* 604 */         if ((r = Float.compare(e1, e2)) != 0) return r; 
/*     */       } 
/* 606 */       return (i < otherATo) ? -1 : ((i < this.to) ? 1 : 0);
/*     */     }
/*     */ 
/*     */     
/*     */     public int compareTo(List<? extends Float> l) {
/* 611 */       if (l instanceof FloatImmutableList) {
/*     */         
/* 613 */         FloatImmutableList other = (FloatImmutableList)l;
/* 614 */         return contentsCompareTo(other.a, 0, other.size());
/*     */       } 
/* 616 */       if (l instanceof ImmutableSubList) {
/*     */         
/* 618 */         ImmutableSubList other = (ImmutableSubList)l;
/* 619 */         return contentsCompareTo(other.a, other.from, other.to);
/*     */       } 
/* 621 */       return super.compareTo(l);
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     private Object readResolve() throws ObjectStreamException {
/*     */       try {
/* 629 */         return this.innerList.subList(this.from, this.to);
/* 630 */       } catch (IllegalArgumentException|IndexOutOfBoundsException ex) {
/* 631 */         throw (InvalidObjectException)(new InvalidObjectException(ex.getMessage())).initCause(ex);
/*     */       } 
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public FloatList subList(int from, int to) {
/* 640 */       ensureIndex(from);
/* 641 */       ensureIndex(to);
/* 642 */       if (from == to) return FloatImmutableList.EMPTY; 
/* 643 */       if (from > to) throw new IllegalArgumentException("Start index (" + from + ") is greater than end index (" + to + ")"); 
/* 644 */       return new ImmutableSubList(this.innerList, from + this.from, to + this.from);
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
/*     */   public FloatList subList(int from, int to) {
/* 659 */     if (from == 0 && to == size()) return this; 
/* 660 */     ensureIndex(from);
/* 661 */     ensureIndex(to);
/* 662 */     if (from == to) return EMPTY; 
/* 663 */     if (from > to) throw new IllegalArgumentException("Start index (" + from + ") is greater than end index (" + to + ")"); 
/* 664 */     return new ImmutableSubList(this, from, to);
/*     */   }
/*     */ 
/*     */   
/*     */   public FloatImmutableList clone() {
/* 669 */     return this;
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
/*     */   public boolean equals(FloatImmutableList l) {
/* 682 */     if (l == this) return true; 
/* 683 */     if (this.a == l.a) return true; 
/* 684 */     int s = size();
/* 685 */     if (s != l.size()) return false; 
/* 686 */     float[] a1 = this.a;
/* 687 */     float[] a2 = l.a;
/* 688 */     return Arrays.equals(a1, a2);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean equals(Object o) {
/* 694 */     if (o == this) return true; 
/* 695 */     if (o == null) return false; 
/* 696 */     if (!(o instanceof List)) return false; 
/* 697 */     if (o instanceof FloatImmutableList) {
/* 698 */       return equals((FloatImmutableList)o);
/*     */     }
/* 700 */     if (o instanceof ImmutableSubList)
/*     */     {
/* 702 */       return ((ImmutableSubList)o).equals(this);
/*     */     }
/* 704 */     return super.equals(o);
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
/*     */   public int compareTo(FloatImmutableList l) {
/* 719 */     if (this.a == l.a) return 0;
/*     */     
/* 721 */     int s1 = size(), s2 = l.size();
/* 722 */     float[] a1 = this.a, a2 = l.a;
/*     */     
/*     */     int i;
/* 725 */     for (i = 0; i < s1 && i < s2; i++) {
/* 726 */       float e1 = a1[i];
/* 727 */       float e2 = a2[i]; int r;
/* 728 */       if ((r = Float.compare(e1, e2)) != 0) return r; 
/*     */     } 
/* 730 */     return (i < s2) ? -1 : ((i < s1) ? 1 : 0);
/*     */   }
/*     */ 
/*     */   
/*     */   public int compareTo(List<? extends Float> l) {
/* 735 */     if (l instanceof FloatImmutableList) {
/* 736 */       return compareTo((FloatImmutableList)l);
/*     */     }
/* 738 */     if (l instanceof ImmutableSubList) {
/*     */ 
/*     */       
/* 741 */       ImmutableSubList other = (ImmutableSubList)l;
/*     */       
/* 743 */       return -other.compareTo(this);
/*     */     } 
/* 745 */     return super.compareTo(l);
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\i\\unimi\dsi\fastutil\floats\FloatImmutableList.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */