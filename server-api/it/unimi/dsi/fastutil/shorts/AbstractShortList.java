/*     */ package it.unimi.dsi.fastutil.shorts;
/*     */ 
/*     */ import java.io.Serializable;
/*     */ import java.util.Arrays;
/*     */ import java.util.Collection;
/*     */ import java.util.Iterator;
/*     */ import java.util.List;
/*     */ import java.util.ListIterator;
/*     */ import java.util.NoSuchElementException;
/*     */ import java.util.Objects;
/*     */ import java.util.RandomAccess;
/*     */ import java.util.Spliterator;
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
/*     */ public abstract class AbstractShortList
/*     */   extends AbstractShortCollection
/*     */   implements ShortList, ShortStack
/*     */ {
/*     */   protected void ensureIndex(int index) {
/*  52 */     if (index < 0) throw new IndexOutOfBoundsException("Index (" + index + ") is negative"); 
/*  53 */     if (index > size()) throw new IndexOutOfBoundsException("Index (" + index + ") is greater than list size (" + size() + ")");
/*     */   
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void ensureRestrictedIndex(int index) {
/*  65 */     if (index < 0) throw new IndexOutOfBoundsException("Index (" + index + ") is negative"); 
/*  66 */     if (index >= size()) throw new IndexOutOfBoundsException("Index (" + index + ") is greater than or equal to list size (" + size() + ")");
/*     */   
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void add(int index, short k) {
/*  76 */     throw new UnsupportedOperationException();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean add(short k) {
/*  87 */     add(size(), k);
/*  88 */     return true;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public short removeShort(int i) {
/*  98 */     throw new UnsupportedOperationException();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public short set(int index, short k) {
/* 108 */     throw new UnsupportedOperationException();
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean addAll(int index, Collection<? extends Short> c) {
/* 114 */     if (c instanceof ShortCollection) {
/* 115 */       return addAll(index, (ShortCollection)c);
/*     */     }
/* 117 */     ensureIndex(index);
/* 118 */     Iterator<? extends Short> i = c.iterator();
/* 119 */     boolean retVal = i.hasNext();
/* 120 */     for (; i.hasNext(); add(index++, ((Short)i.next()).shortValue()));
/* 121 */     return retVal;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean addAll(Collection<? extends Short> c) {
/* 132 */     return addAll(size(), c);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ShortListIterator iterator() {
/* 142 */     return listIterator();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ShortListIterator listIterator() {
/* 152 */     return listIterator(0);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ShortListIterator listIterator(int index) {
/* 162 */     ensureIndex(index);
/* 163 */     return new ShortIterators.AbstractIndexBasedListIterator(0, index)
/*     */       {
/*     */         protected final short get(int i) {
/* 166 */           return AbstractShortList.this.getShort(i);
/*     */         }
/*     */ 
/*     */         
/*     */         protected final void add(int i, short k) {
/* 171 */           AbstractShortList.this.add(i, k);
/*     */         }
/*     */ 
/*     */         
/*     */         protected final void set(int i, short k) {
/* 176 */           AbstractShortList.this.set(i, k);
/*     */         }
/*     */ 
/*     */         
/*     */         protected final void remove(int i) {
/* 181 */           AbstractShortList.this.removeShort(i);
/*     */         }
/*     */ 
/*     */         
/*     */         protected final int getMaxPos() {
/* 186 */           return AbstractShortList.this.size();
/*     */         }
/*     */       };
/*     */   }
/*     */   
/*     */   static final class IndexBasedSpliterator extends ShortSpliterators.LateBindingSizeIndexBasedSpliterator {
/*     */     final ShortList l;
/*     */     
/*     */     IndexBasedSpliterator(ShortList l, int pos) {
/* 195 */       super(pos);
/* 196 */       this.l = l;
/*     */     }
/*     */     
/*     */     IndexBasedSpliterator(ShortList l, int pos, int maxPos) {
/* 200 */       super(pos, maxPos);
/* 201 */       this.l = l;
/*     */     }
/*     */ 
/*     */     
/*     */     protected final int getMaxPosFromBackingStore() {
/* 206 */       return this.l.size();
/*     */     }
/*     */ 
/*     */     
/*     */     protected final short get(int i) {
/* 211 */       return this.l.getShort(i);
/*     */     }
/*     */ 
/*     */     
/*     */     protected final IndexBasedSpliterator makeForSplit(int pos, int maxPos) {
/* 216 */       return new IndexBasedSpliterator(this.l, pos, maxPos);
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
/*     */   public boolean contains(short k) {
/* 228 */     return (indexOf(k) >= 0);
/*     */   }
/*     */ 
/*     */   
/*     */   public int indexOf(short k) {
/* 233 */     ShortListIterator i = listIterator();
/*     */     
/* 235 */     while (i.hasNext()) {
/* 236 */       short e = i.nextShort();
/* 237 */       if (k == e) return i.previousIndex(); 
/*     */     } 
/* 239 */     return -1;
/*     */   }
/*     */ 
/*     */   
/*     */   public int lastIndexOf(short k) {
/* 244 */     ShortListIterator i = listIterator(size());
/*     */     
/* 246 */     while (i.hasPrevious()) {
/* 247 */       short e = i.previousShort();
/* 248 */       if (k == e) return i.nextIndex(); 
/*     */     } 
/* 250 */     return -1;
/*     */   }
/*     */ 
/*     */   
/*     */   public void size(int size) {
/* 255 */     int i = size();
/* 256 */     if (size > i) { for (; i++ < size; add((short)0)); }
/* 257 */     else { for (; i-- != size; removeShort(i)); }
/*     */   
/*     */   }
/*     */   
/*     */   public ShortList subList(int from, int to) {
/* 262 */     ensureIndex(from);
/* 263 */     ensureIndex(to);
/* 264 */     if (from > to) throw new IndexOutOfBoundsException("Start index (" + from + ") is greater than end index (" + to + ")"); 
/* 265 */     return (this instanceof RandomAccess) ? new ShortRandomAccessSubList(this, from, to) : new ShortSubList(this, from, to);
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
/*     */   public void forEach(ShortConsumer action) {
/* 277 */     if (this instanceof RandomAccess) {
/* 278 */       for (int i = 0, max = size(); i < max; i++) {
/* 279 */         action.accept(getShort(i));
/*     */       }
/*     */     } else {
/* 282 */       super.forEach(action);
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
/*     */   public void removeElements(int from, int to) {
/* 295 */     ensureIndex(to);
/*     */ 
/*     */     
/* 298 */     ShortListIterator i = listIterator(from);
/* 299 */     int n = to - from;
/* 300 */     if (n < 0) throw new IllegalArgumentException("Start index (" + from + ") is greater than end index (" + to + ")"); 
/* 301 */     while (n-- != 0) {
/* 302 */       i.nextShort();
/* 303 */       i.remove();
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
/*     */   public void addElements(int index, short[] a, int offset, int length) {
/* 316 */     ensureIndex(index);
/* 317 */     ShortArrays.ensureOffsetLength(a, offset, length);
/* 318 */     if (this instanceof RandomAccess) {
/* 319 */       for (; length-- != 0; add(index++, a[offset++]));
/*     */     } else {
/* 321 */       ShortListIterator iter = listIterator(index);
/* 322 */       for (; length-- != 0; iter.add(a[offset++]));
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void addElements(int index, short[] a) {
/* 333 */     addElements(index, a, 0, a.length);
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
/*     */   public void getElements(int from, short[] a, int offset, int length) {
/* 345 */     ensureIndex(from);
/* 346 */     ShortArrays.ensureOffsetLength(a, offset, length);
/* 347 */     if (from + length > size()) throw new IndexOutOfBoundsException("End index (" + (from + length) + ") is greater than list size (" + size() + ")"); 
/* 348 */     if (this instanceof RandomAccess) {
/* 349 */       int current = from;
/* 350 */       for (; length-- != 0; a[offset++] = getShort(current++));
/*     */     } else {
/* 352 */       ShortListIterator i = listIterator(from);
/* 353 */       for (; length-- != 0; a[offset++] = i.nextShort());
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public void setElements(int index, short[] a, int offset, int length) {
/* 359 */     ensureIndex(index);
/* 360 */     ShortArrays.ensureOffsetLength(a, offset, length);
/* 361 */     if (index + length > size()) throw new IndexOutOfBoundsException("End index (" + (index + length) + ") is greater than list size (" + size() + ")"); 
/* 362 */     if (this instanceof RandomAccess) {
/* 363 */       for (int i = 0; i < length; i++) {
/* 364 */         set(i + index, a[i + offset]);
/*     */       }
/*     */     } else {
/* 367 */       ShortListIterator iter = listIterator(index);
/* 368 */       int i = 0;
/* 369 */       while (i < length) {
/* 370 */         iter.nextShort();
/* 371 */         iter.set(a[offset + i++]);
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void clear() {
/* 383 */     removeElements(0, size());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int hashCode() {
/* 393 */     ShortIterator i = iterator();
/* 394 */     int h = 1, s = size();
/* 395 */     while (s-- != 0) {
/* 396 */       short k = i.nextShort();
/* 397 */       h = 31 * h + k;
/*     */     } 
/* 399 */     return h;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean equals(Object o) {
/* 404 */     if (o == this) return true; 
/* 405 */     if (!(o instanceof List)) return false; 
/* 406 */     List<?> l = (List)o;
/* 407 */     int s = size();
/* 408 */     if (s != l.size()) return false; 
/* 409 */     if (l instanceof ShortList) {
/* 410 */       ShortListIterator shortListIterator1 = listIterator(), shortListIterator2 = ((ShortList)l).listIterator();
/* 411 */       while (s-- != 0) { if (shortListIterator1.nextShort() != shortListIterator2.nextShort()) return false;  }
/* 412 */        return true;
/*     */     } 
/* 414 */     ListIterator<?> i1 = listIterator(), i2 = l.listIterator();
/* 415 */     while (s-- != 0) { if (!Objects.equals(i1.next(), i2.next())) return false;  }
/* 416 */      return true;
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
/*     */   public int compareTo(List<? extends Short> l) {
/* 432 */     if (l == this) return 0; 
/* 433 */     if (l instanceof ShortList) {
/* 434 */       ShortListIterator shortListIterator1 = listIterator(), shortListIterator2 = ((ShortList)l).listIterator();
/*     */ 
/*     */       
/* 437 */       while (shortListIterator1.hasNext() && shortListIterator2.hasNext()) {
/* 438 */         short e1 = shortListIterator1.nextShort();
/* 439 */         short e2 = shortListIterator2.nextShort(); int r;
/* 440 */         if ((r = Short.compare(e1, e2)) != 0) return r; 
/*     */       } 
/* 442 */       return shortListIterator2.hasNext() ? -1 : (shortListIterator1.hasNext() ? 1 : 0);
/*     */     } 
/* 444 */     ListIterator<? extends Short> i1 = listIterator(), i2 = l.listIterator();
/*     */     
/* 446 */     while (i1.hasNext() && i2.hasNext()) {
/* 447 */       int r; if ((r = ((Comparable)i1.next()).compareTo(i2.next())) != 0) return r; 
/*     */     } 
/* 449 */     return i2.hasNext() ? -1 : (i1.hasNext() ? 1 : 0);
/*     */   }
/*     */ 
/*     */   
/*     */   public void push(short o) {
/* 454 */     add(o);
/*     */   }
/*     */ 
/*     */   
/*     */   public short popShort() {
/* 459 */     if (isEmpty()) throw new NoSuchElementException(); 
/* 460 */     return removeShort(size() - 1);
/*     */   }
/*     */ 
/*     */   
/*     */   public short topShort() {
/* 465 */     if (isEmpty()) throw new NoSuchElementException(); 
/* 466 */     return getShort(size() - 1);
/*     */   }
/*     */ 
/*     */   
/*     */   public short peekShort(int i) {
/* 471 */     return getShort(size() - 1 - i);
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
/*     */   public boolean rem(short k) {
/* 483 */     int index = indexOf(k);
/* 484 */     if (index == -1) return false; 
/* 485 */     removeShort(index);
/* 486 */     return true;
/*     */   }
/*     */ 
/*     */   
/*     */   public short[] toShortArray() {
/* 491 */     int size = size();
/* 492 */     if (size == 0) return ShortArrays.EMPTY_ARRAY; 
/* 493 */     short[] ret = new short[size];
/* 494 */     getElements(0, ret, 0, size);
/* 495 */     return ret;
/*     */   }
/*     */ 
/*     */   
/*     */   public short[] toArray(short[] a) {
/* 500 */     int size = size();
/* 501 */     if (a.length < size) {
/* 502 */       a = Arrays.copyOf(a, size);
/*     */     }
/* 504 */     getElements(0, a, 0, size);
/* 505 */     return a;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean addAll(int index, ShortCollection c) {
/* 510 */     ensureIndex(index);
/* 511 */     ShortIterator i = c.iterator();
/* 512 */     boolean retVal = i.hasNext();
/* 513 */     for (; i.hasNext(); add(index++, i.nextShort()));
/* 514 */     return retVal;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean addAll(ShortCollection c) {
/* 525 */     return addAll(size(), c);
/*     */   }
/*     */ 
/*     */   
/*     */   public String toString() {
/* 530 */     StringBuilder s = new StringBuilder();
/* 531 */     ShortIterator i = iterator();
/* 532 */     int n = size();
/*     */     
/* 534 */     boolean first = true;
/* 535 */     s.append("[");
/* 536 */     while (n-- != 0) {
/* 537 */       if (first) { first = false; }
/* 538 */       else { s.append(", "); }
/* 539 */        short k = i.nextShort();
/* 540 */       s.append(String.valueOf(k));
/*     */     } 
/* 542 */     s.append("]");
/* 543 */     return s.toString();
/*     */   }
/*     */ 
/*     */   
/*     */   public static class ShortSubList
/*     */     extends AbstractShortList
/*     */     implements Serializable
/*     */   {
/*     */     private static final long serialVersionUID = -7046029254386353129L;
/*     */     protected final ShortList l;
/*     */     protected final int from;
/*     */     protected int to;
/*     */     
/*     */     public ShortSubList(ShortList l, int from, int to) {
/* 557 */       this.l = l;
/* 558 */       this.from = from;
/* 559 */       this.to = to;
/*     */     }
/*     */     
/*     */     private boolean assertRange() {
/* 563 */       assert this.from <= this.l.size();
/* 564 */       assert this.to <= this.l.size();
/* 565 */       assert this.to >= this.from;
/* 566 */       return true;
/*     */     }
/*     */ 
/*     */     
/*     */     public boolean add(short k) {
/* 571 */       this.l.add(this.to, k);
/* 572 */       this.to++;
/* 573 */       assert assertRange();
/* 574 */       return true;
/*     */     }
/*     */ 
/*     */     
/*     */     public void add(int index, short k) {
/* 579 */       ensureIndex(index);
/* 580 */       this.l.add(this.from + index, k);
/* 581 */       this.to++;
/* 582 */       assert assertRange();
/*     */     }
/*     */ 
/*     */     
/*     */     public boolean addAll(int index, Collection<? extends Short> c) {
/* 587 */       ensureIndex(index);
/* 588 */       this.to += c.size();
/* 589 */       return this.l.addAll(this.from + index, c);
/*     */     }
/*     */ 
/*     */     
/*     */     public short getShort(int index) {
/* 594 */       ensureRestrictedIndex(index);
/* 595 */       return this.l.getShort(this.from + index);
/*     */     }
/*     */ 
/*     */     
/*     */     public short removeShort(int index) {
/* 600 */       ensureRestrictedIndex(index);
/* 601 */       this.to--;
/* 602 */       return this.l.removeShort(this.from + index);
/*     */     }
/*     */ 
/*     */     
/*     */     public short set(int index, short k) {
/* 607 */       ensureRestrictedIndex(index);
/* 608 */       return this.l.set(this.from + index, k);
/*     */     }
/*     */ 
/*     */     
/*     */     public int size() {
/* 613 */       return this.to - this.from;
/*     */     }
/*     */ 
/*     */     
/*     */     public void getElements(int from, short[] a, int offset, int length) {
/* 618 */       ensureIndex(from);
/* 619 */       if (from + length > size()) throw new IndexOutOfBoundsException("End index (" + from + length + ") is greater than list size (" + size() + ")"); 
/* 620 */       this.l.getElements(this.from + from, a, offset, length);
/*     */     }
/*     */ 
/*     */     
/*     */     public void removeElements(int from, int to) {
/* 625 */       ensureIndex(from);
/* 626 */       ensureIndex(to);
/* 627 */       this.l.removeElements(this.from + from, this.from + to);
/* 628 */       this.to -= to - from;
/* 629 */       assert assertRange();
/*     */     }
/*     */ 
/*     */     
/*     */     public void addElements(int index, short[] a, int offset, int length) {
/* 634 */       ensureIndex(index);
/* 635 */       this.l.addElements(this.from + index, a, offset, length);
/* 636 */       this.to += length;
/* 637 */       assert assertRange();
/*     */     }
/*     */ 
/*     */     
/*     */     public void setElements(int index, short[] a, int offset, int length) {
/* 642 */       ensureIndex(index);
/* 643 */       this.l.setElements(this.from + index, a, offset, length);
/* 644 */       assert assertRange();
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     private final class RandomAccessIter
/*     */       extends ShortIterators.AbstractIndexBasedListIterator
/*     */     {
/*     */       RandomAccessIter(int pos) {
/* 653 */         super(0, pos);
/*     */       }
/*     */ 
/*     */       
/*     */       protected final short get(int i) {
/* 658 */         return AbstractShortList.ShortSubList.this.l.getShort(AbstractShortList.ShortSubList.this.from + i);
/*     */       }
/*     */ 
/*     */ 
/*     */       
/*     */       protected final void add(int i, short k) {
/* 664 */         AbstractShortList.ShortSubList.this.add(i, k);
/*     */       }
/*     */ 
/*     */       
/*     */       protected final void set(int i, short k) {
/* 669 */         AbstractShortList.ShortSubList.this.set(i, k);
/*     */       }
/*     */ 
/*     */       
/*     */       protected final void remove(int i) {
/* 674 */         AbstractShortList.ShortSubList.this.removeShort(i);
/*     */       }
/*     */ 
/*     */       
/*     */       protected final int getMaxPos() {
/* 679 */         return AbstractShortList.ShortSubList.this.to - AbstractShortList.ShortSubList.this.from;
/*     */       }
/*     */ 
/*     */       
/*     */       public void add(short k) {
/* 684 */         super.add(k);
/* 685 */         assert AbstractShortList.ShortSubList.this.assertRange();
/*     */       }
/*     */ 
/*     */       
/*     */       public void remove() {
/* 690 */         super.remove();
/* 691 */         assert AbstractShortList.ShortSubList.this.assertRange();
/*     */       }
/*     */     }
/*     */     
/*     */     private class ParentWrappingIter implements ShortListIterator {
/*     */       private ShortListIterator parent;
/*     */       
/*     */       ParentWrappingIter(ShortListIterator parent) {
/* 699 */         this.parent = parent;
/*     */       }
/*     */ 
/*     */       
/*     */       public int nextIndex() {
/* 704 */         return this.parent.nextIndex() - AbstractShortList.ShortSubList.this.from;
/*     */       }
/*     */ 
/*     */       
/*     */       public int previousIndex() {
/* 709 */         return this.parent.previousIndex() - AbstractShortList.ShortSubList.this.from;
/*     */       }
/*     */ 
/*     */       
/*     */       public boolean hasNext() {
/* 714 */         return (this.parent.nextIndex() < AbstractShortList.ShortSubList.this.to);
/*     */       }
/*     */ 
/*     */       
/*     */       public boolean hasPrevious() {
/* 719 */         return (this.parent.previousIndex() >= AbstractShortList.ShortSubList.this.from);
/*     */       }
/*     */ 
/*     */       
/*     */       public short nextShort() {
/* 724 */         if (!hasNext()) throw new NoSuchElementException(); 
/* 725 */         return this.parent.nextShort();
/*     */       }
/*     */ 
/*     */       
/*     */       public short previousShort() {
/* 730 */         if (!hasPrevious()) throw new NoSuchElementException(); 
/* 731 */         return this.parent.previousShort();
/*     */       }
/*     */ 
/*     */       
/*     */       public void add(short k) {
/* 736 */         this.parent.add(k);
/*     */       }
/*     */ 
/*     */       
/*     */       public void set(short k) {
/* 741 */         this.parent.set(k);
/*     */       }
/*     */ 
/*     */       
/*     */       public void remove() {
/* 746 */         this.parent.remove();
/*     */       }
/*     */ 
/*     */       
/*     */       public int back(int n) {
/* 751 */         if (n < 0) throw new IllegalArgumentException("Argument must be nonnegative: " + n); 
/* 752 */         int currentPos = this.parent.previousIndex();
/* 753 */         int parentNewPos = currentPos - n;
/*     */ 
/*     */ 
/*     */         
/* 757 */         if (parentNewPos < AbstractShortList.ShortSubList.this.from - 1) parentNewPos = AbstractShortList.ShortSubList.this.from - 1; 
/* 758 */         int toSkip = parentNewPos - currentPos;
/* 759 */         return this.parent.back(toSkip);
/*     */       }
/*     */ 
/*     */       
/*     */       public int skip(int n) {
/* 764 */         if (n < 0) throw new IllegalArgumentException("Argument must be nonnegative: " + n); 
/* 765 */         int currentPos = this.parent.nextIndex();
/* 766 */         int parentNewPos = currentPos + n;
/* 767 */         if (parentNewPos > AbstractShortList.ShortSubList.this.to) parentNewPos = AbstractShortList.ShortSubList.this.to; 
/* 768 */         int toSkip = parentNewPos - currentPos;
/* 769 */         return this.parent.skip(toSkip);
/*     */       }
/*     */     }
/*     */ 
/*     */     
/*     */     public ShortListIterator listIterator(int index) {
/* 775 */       ensureIndex(index);
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 780 */       return (this.l instanceof RandomAccess) ? new RandomAccessIter(index) : new ParentWrappingIter(this.l.listIterator(index + this.from));
/*     */     }
/*     */ 
/*     */     
/*     */     public ShortSpliterator spliterator() {
/* 785 */       return (this.l instanceof RandomAccess) ? new AbstractShortList.IndexBasedSpliterator(this.l, this.from, this.to) : super.spliterator();
/*     */     }
/*     */ 
/*     */     
/*     */     public ShortList subList(int from, int to) {
/* 790 */       ensureIndex(from);
/* 791 */       ensureIndex(to);
/* 792 */       if (from > to) throw new IllegalArgumentException("Start index (" + from + ") is greater than end index (" + to + ")");
/*     */ 
/*     */       
/* 795 */       return new ShortSubList(this, from, to);
/*     */     }
/*     */ 
/*     */     
/*     */     public boolean rem(short k) {
/* 800 */       int index = indexOf(k);
/* 801 */       if (index == -1) return false; 
/* 802 */       this.to--;
/* 803 */       this.l.removeShort(this.from + index);
/* 804 */       assert assertRange();
/* 805 */       return true;
/*     */     }
/*     */ 
/*     */     
/*     */     public boolean addAll(int index, ShortCollection c) {
/* 810 */       ensureIndex(index);
/* 811 */       return super.addAll(index, c);
/*     */     }
/*     */ 
/*     */     
/*     */     public boolean addAll(int index, ShortList l) {
/* 816 */       ensureIndex(index);
/* 817 */       return super.addAll(index, l);
/*     */     }
/*     */   }
/*     */   
/*     */   public static class ShortRandomAccessSubList extends ShortSubList implements RandomAccess {
/*     */     private static final long serialVersionUID = -107070782945191929L;
/*     */     
/*     */     public ShortRandomAccessSubList(ShortList l, int from, int to) {
/* 825 */       super(l, from, to);
/*     */     }
/*     */ 
/*     */     
/*     */     public ShortList subList(int from, int to) {
/* 830 */       ensureIndex(from);
/* 831 */       ensureIndex(to);
/* 832 */       if (from > to) throw new IllegalArgumentException("Start index (" + from + ") is greater than end index (" + to + ")");
/*     */ 
/*     */       
/* 835 */       return new ShortRandomAccessSubList(this, from, to);
/*     */     }
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\i\\unimi\dsi\fastutil\shorts\AbstractShortList.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */