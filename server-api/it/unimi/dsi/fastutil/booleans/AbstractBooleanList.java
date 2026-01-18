/*     */ package it.unimi.dsi.fastutil.booleans;
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
/*     */ public abstract class AbstractBooleanList
/*     */   extends AbstractBooleanCollection
/*     */   implements BooleanList, BooleanStack
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
/*     */   public void add(int index, boolean k) {
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
/*     */   public boolean add(boolean k) {
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
/*     */   public boolean removeBoolean(int i) {
/*  98 */     throw new UnsupportedOperationException();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean set(int index, boolean k) {
/* 108 */     throw new UnsupportedOperationException();
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean addAll(int index, Collection<? extends Boolean> c) {
/* 114 */     if (c instanceof BooleanCollection) {
/* 115 */       return addAll(index, (BooleanCollection)c);
/*     */     }
/* 117 */     ensureIndex(index);
/* 118 */     Iterator<? extends Boolean> i = c.iterator();
/* 119 */     boolean retVal = i.hasNext();
/* 120 */     for (; i.hasNext(); add(index++, ((Boolean)i.next()).booleanValue()));
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
/*     */   public boolean addAll(Collection<? extends Boolean> c) {
/* 132 */     return addAll(size(), c);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public BooleanListIterator iterator() {
/* 142 */     return listIterator();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public BooleanListIterator listIterator() {
/* 152 */     return listIterator(0);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public BooleanListIterator listIterator(int index) {
/* 162 */     ensureIndex(index);
/* 163 */     return new BooleanIterators.AbstractIndexBasedListIterator(0, index)
/*     */       {
/*     */         protected final boolean get(int i) {
/* 166 */           return AbstractBooleanList.this.getBoolean(i);
/*     */         }
/*     */ 
/*     */         
/*     */         protected final void add(int i, boolean k) {
/* 171 */           AbstractBooleanList.this.add(i, k);
/*     */         }
/*     */ 
/*     */         
/*     */         protected final void set(int i, boolean k) {
/* 176 */           AbstractBooleanList.this.set(i, k);
/*     */         }
/*     */ 
/*     */         
/*     */         protected final void remove(int i) {
/* 181 */           AbstractBooleanList.this.removeBoolean(i);
/*     */         }
/*     */ 
/*     */         
/*     */         protected final int getMaxPos() {
/* 186 */           return AbstractBooleanList.this.size();
/*     */         }
/*     */       };
/*     */   }
/*     */   
/*     */   static final class IndexBasedSpliterator extends BooleanSpliterators.LateBindingSizeIndexBasedSpliterator {
/*     */     final BooleanList l;
/*     */     
/*     */     IndexBasedSpliterator(BooleanList l, int pos) {
/* 195 */       super(pos);
/* 196 */       this.l = l;
/*     */     }
/*     */     
/*     */     IndexBasedSpliterator(BooleanList l, int pos, int maxPos) {
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
/*     */     protected final boolean get(int i) {
/* 211 */       return this.l.getBoolean(i);
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
/*     */   public boolean contains(boolean k) {
/* 228 */     return (indexOf(k) >= 0);
/*     */   }
/*     */ 
/*     */   
/*     */   public int indexOf(boolean k) {
/* 233 */     BooleanListIterator i = listIterator();
/*     */     
/* 235 */     while (i.hasNext()) {
/* 236 */       boolean e = i.nextBoolean();
/* 237 */       if (k == e) return i.previousIndex(); 
/*     */     } 
/* 239 */     return -1;
/*     */   }
/*     */ 
/*     */   
/*     */   public int lastIndexOf(boolean k) {
/* 244 */     BooleanListIterator i = listIterator(size());
/*     */     
/* 246 */     while (i.hasPrevious()) {
/* 247 */       boolean e = i.previousBoolean();
/* 248 */       if (k == e) return i.nextIndex(); 
/*     */     } 
/* 250 */     return -1;
/*     */   }
/*     */ 
/*     */   
/*     */   public void size(int size) {
/* 255 */     int i = size();
/* 256 */     if (size > i) { for (; i++ < size; add(false)); }
/* 257 */     else { for (; i-- != size; removeBoolean(i)); }
/*     */   
/*     */   }
/*     */   
/*     */   public BooleanList subList(int from, int to) {
/* 262 */     ensureIndex(from);
/* 263 */     ensureIndex(to);
/* 264 */     if (from > to) throw new IndexOutOfBoundsException("Start index (" + from + ") is greater than end index (" + to + ")"); 
/* 265 */     return (this instanceof RandomAccess) ? new BooleanRandomAccessSubList(this, from, to) : new BooleanSubList(this, from, to);
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
/*     */   public void forEach(BooleanConsumer action) {
/* 277 */     if (this instanceof RandomAccess) {
/* 278 */       for (int i = 0, max = size(); i < max; i++) {
/* 279 */         action.accept(getBoolean(i));
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
/* 298 */     BooleanListIterator i = listIterator(from);
/* 299 */     int n = to - from;
/* 300 */     if (n < 0) throw new IllegalArgumentException("Start index (" + from + ") is greater than end index (" + to + ")"); 
/* 301 */     while (n-- != 0) {
/* 302 */       i.nextBoolean();
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
/*     */   public void addElements(int index, boolean[] a, int offset, int length) {
/* 316 */     ensureIndex(index);
/* 317 */     BooleanArrays.ensureOffsetLength(a, offset, length);
/* 318 */     if (this instanceof RandomAccess) {
/* 319 */       for (; length-- != 0; add(index++, a[offset++]));
/*     */     } else {
/* 321 */       BooleanListIterator iter = listIterator(index);
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
/*     */   public void addElements(int index, boolean[] a) {
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
/*     */   public void getElements(int from, boolean[] a, int offset, int length) {
/* 345 */     ensureIndex(from);
/* 346 */     BooleanArrays.ensureOffsetLength(a, offset, length);
/* 347 */     if (from + length > size()) throw new IndexOutOfBoundsException("End index (" + (from + length) + ") is greater than list size (" + size() + ")"); 
/* 348 */     if (this instanceof RandomAccess) {
/* 349 */       int current = from;
/* 350 */       for (; length-- != 0; a[offset++] = getBoolean(current++));
/*     */     } else {
/* 352 */       BooleanListIterator i = listIterator(from);
/* 353 */       for (; length-- != 0; a[offset++] = i.nextBoolean());
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public void setElements(int index, boolean[] a, int offset, int length) {
/* 359 */     ensureIndex(index);
/* 360 */     BooleanArrays.ensureOffsetLength(a, offset, length);
/* 361 */     if (index + length > size()) throw new IndexOutOfBoundsException("End index (" + (index + length) + ") is greater than list size (" + size() + ")"); 
/* 362 */     if (this instanceof RandomAccess) {
/* 363 */       for (int i = 0; i < length; i++) {
/* 364 */         set(i + index, a[i + offset]);
/*     */       }
/*     */     } else {
/* 367 */       BooleanListIterator iter = listIterator(index);
/* 368 */       int i = 0;
/* 369 */       while (i < length) {
/* 370 */         iter.nextBoolean();
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
/* 393 */     BooleanIterator i = iterator();
/* 394 */     int h = 1, s = size();
/* 395 */     while (s-- != 0) {
/* 396 */       boolean k = i.nextBoolean();
/* 397 */       h = 31 * h + (k ? 1231 : 1237);
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
/* 409 */     if (l instanceof BooleanList) {
/* 410 */       BooleanListIterator booleanListIterator1 = listIterator(), booleanListIterator2 = ((BooleanList)l).listIterator();
/* 411 */       while (s-- != 0) { if (booleanListIterator1.nextBoolean() != booleanListIterator2.nextBoolean()) return false;  }
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
/*     */   public int compareTo(List<? extends Boolean> l) {
/* 432 */     if (l == this) return 0; 
/* 433 */     if (l instanceof BooleanList) {
/* 434 */       BooleanListIterator booleanListIterator1 = listIterator(), booleanListIterator2 = ((BooleanList)l).listIterator();
/*     */ 
/*     */       
/* 437 */       while (booleanListIterator1.hasNext() && booleanListIterator2.hasNext()) {
/* 438 */         boolean e1 = booleanListIterator1.nextBoolean();
/* 439 */         boolean e2 = booleanListIterator2.nextBoolean(); int r;
/* 440 */         if ((r = Boolean.compare(e1, e2)) != 0) return r; 
/*     */       } 
/* 442 */       return booleanListIterator2.hasNext() ? -1 : (booleanListIterator1.hasNext() ? 1 : 0);
/*     */     } 
/* 444 */     ListIterator<? extends Boolean> i1 = listIterator(), i2 = l.listIterator();
/*     */     
/* 446 */     while (i1.hasNext() && i2.hasNext()) {
/* 447 */       int r; if ((r = ((Comparable)i1.next()).compareTo(i2.next())) != 0) return r; 
/*     */     } 
/* 449 */     return i2.hasNext() ? -1 : (i1.hasNext() ? 1 : 0);
/*     */   }
/*     */ 
/*     */   
/*     */   public void push(boolean o) {
/* 454 */     add(o);
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean popBoolean() {
/* 459 */     if (isEmpty()) throw new NoSuchElementException(); 
/* 460 */     return removeBoolean(size() - 1);
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean topBoolean() {
/* 465 */     if (isEmpty()) throw new NoSuchElementException(); 
/* 466 */     return getBoolean(size() - 1);
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean peekBoolean(int i) {
/* 471 */     return getBoolean(size() - 1 - i);
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
/*     */   public boolean rem(boolean k) {
/* 483 */     int index = indexOf(k);
/* 484 */     if (index == -1) return false; 
/* 485 */     removeBoolean(index);
/* 486 */     return true;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean[] toBooleanArray() {
/* 491 */     int size = size();
/* 492 */     if (size == 0) return BooleanArrays.EMPTY_ARRAY; 
/* 493 */     boolean[] ret = new boolean[size];
/* 494 */     getElements(0, ret, 0, size);
/* 495 */     return ret;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean[] toArray(boolean[] a) {
/* 500 */     int size = size();
/* 501 */     if (a.length < size) {
/* 502 */       a = Arrays.copyOf(a, size);
/*     */     }
/* 504 */     getElements(0, a, 0, size);
/* 505 */     return a;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean addAll(int index, BooleanCollection c) {
/* 510 */     ensureIndex(index);
/* 511 */     BooleanIterator i = c.iterator();
/* 512 */     boolean retVal = i.hasNext();
/* 513 */     for (; i.hasNext(); add(index++, i.nextBoolean()));
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
/*     */   public boolean addAll(BooleanCollection c) {
/* 525 */     return addAll(size(), c);
/*     */   }
/*     */ 
/*     */   
/*     */   public String toString() {
/* 530 */     StringBuilder s = new StringBuilder();
/* 531 */     BooleanIterator i = iterator();
/* 532 */     int n = size();
/*     */     
/* 534 */     boolean first = true;
/* 535 */     s.append("[");
/* 536 */     while (n-- != 0) {
/* 537 */       if (first) { first = false; }
/* 538 */       else { s.append(", "); }
/* 539 */        boolean k = i.nextBoolean();
/* 540 */       s.append(String.valueOf(k));
/*     */     } 
/* 542 */     s.append("]");
/* 543 */     return s.toString();
/*     */   }
/*     */ 
/*     */   
/*     */   public static class BooleanSubList
/*     */     extends AbstractBooleanList
/*     */     implements Serializable
/*     */   {
/*     */     private static final long serialVersionUID = -7046029254386353129L;
/*     */     protected final BooleanList l;
/*     */     protected final int from;
/*     */     protected int to;
/*     */     
/*     */     public BooleanSubList(BooleanList l, int from, int to) {
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
/*     */     public boolean add(boolean k) {
/* 571 */       this.l.add(this.to, k);
/* 572 */       this.to++;
/* 573 */       assert assertRange();
/* 574 */       return true;
/*     */     }
/*     */ 
/*     */     
/*     */     public void add(int index, boolean k) {
/* 579 */       ensureIndex(index);
/* 580 */       this.l.add(this.from + index, k);
/* 581 */       this.to++;
/* 582 */       assert assertRange();
/*     */     }
/*     */ 
/*     */     
/*     */     public boolean addAll(int index, Collection<? extends Boolean> c) {
/* 587 */       ensureIndex(index);
/* 588 */       this.to += c.size();
/* 589 */       return this.l.addAll(this.from + index, c);
/*     */     }
/*     */ 
/*     */     
/*     */     public boolean getBoolean(int index) {
/* 594 */       ensureRestrictedIndex(index);
/* 595 */       return this.l.getBoolean(this.from + index);
/*     */     }
/*     */ 
/*     */     
/*     */     public boolean removeBoolean(int index) {
/* 600 */       ensureRestrictedIndex(index);
/* 601 */       this.to--;
/* 602 */       return this.l.removeBoolean(this.from + index);
/*     */     }
/*     */ 
/*     */     
/*     */     public boolean set(int index, boolean k) {
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
/*     */     public void getElements(int from, boolean[] a, int offset, int length) {
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
/*     */     public void addElements(int index, boolean[] a, int offset, int length) {
/* 634 */       ensureIndex(index);
/* 635 */       this.l.addElements(this.from + index, a, offset, length);
/* 636 */       this.to += length;
/* 637 */       assert assertRange();
/*     */     }
/*     */ 
/*     */     
/*     */     public void setElements(int index, boolean[] a, int offset, int length) {
/* 642 */       ensureIndex(index);
/* 643 */       this.l.setElements(this.from + index, a, offset, length);
/* 644 */       assert assertRange();
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     private final class RandomAccessIter
/*     */       extends BooleanIterators.AbstractIndexBasedListIterator
/*     */     {
/*     */       RandomAccessIter(int pos) {
/* 653 */         super(0, pos);
/*     */       }
/*     */ 
/*     */       
/*     */       protected final boolean get(int i) {
/* 658 */         return AbstractBooleanList.BooleanSubList.this.l.getBoolean(AbstractBooleanList.BooleanSubList.this.from + i);
/*     */       }
/*     */ 
/*     */ 
/*     */       
/*     */       protected final void add(int i, boolean k) {
/* 664 */         AbstractBooleanList.BooleanSubList.this.add(i, k);
/*     */       }
/*     */ 
/*     */       
/*     */       protected final void set(int i, boolean k) {
/* 669 */         AbstractBooleanList.BooleanSubList.this.set(i, k);
/*     */       }
/*     */ 
/*     */       
/*     */       protected final void remove(int i) {
/* 674 */         AbstractBooleanList.BooleanSubList.this.removeBoolean(i);
/*     */       }
/*     */ 
/*     */       
/*     */       protected final int getMaxPos() {
/* 679 */         return AbstractBooleanList.BooleanSubList.this.to - AbstractBooleanList.BooleanSubList.this.from;
/*     */       }
/*     */ 
/*     */       
/*     */       public void add(boolean k) {
/* 684 */         super.add(k);
/* 685 */         assert AbstractBooleanList.BooleanSubList.this.assertRange();
/*     */       }
/*     */ 
/*     */       
/*     */       public void remove() {
/* 690 */         super.remove();
/* 691 */         assert AbstractBooleanList.BooleanSubList.this.assertRange();
/*     */       }
/*     */     }
/*     */     
/*     */     private class ParentWrappingIter implements BooleanListIterator {
/*     */       private BooleanListIterator parent;
/*     */       
/*     */       ParentWrappingIter(BooleanListIterator parent) {
/* 699 */         this.parent = parent;
/*     */       }
/*     */ 
/*     */       
/*     */       public int nextIndex() {
/* 704 */         return this.parent.nextIndex() - AbstractBooleanList.BooleanSubList.this.from;
/*     */       }
/*     */ 
/*     */       
/*     */       public int previousIndex() {
/* 709 */         return this.parent.previousIndex() - AbstractBooleanList.BooleanSubList.this.from;
/*     */       }
/*     */ 
/*     */       
/*     */       public boolean hasNext() {
/* 714 */         return (this.parent.nextIndex() < AbstractBooleanList.BooleanSubList.this.to);
/*     */       }
/*     */ 
/*     */       
/*     */       public boolean hasPrevious() {
/* 719 */         return (this.parent.previousIndex() >= AbstractBooleanList.BooleanSubList.this.from);
/*     */       }
/*     */ 
/*     */       
/*     */       public boolean nextBoolean() {
/* 724 */         if (!hasNext()) throw new NoSuchElementException(); 
/* 725 */         return this.parent.nextBoolean();
/*     */       }
/*     */ 
/*     */       
/*     */       public boolean previousBoolean() {
/* 730 */         if (!hasPrevious()) throw new NoSuchElementException(); 
/* 731 */         return this.parent.previousBoolean();
/*     */       }
/*     */ 
/*     */       
/*     */       public void add(boolean k) {
/* 736 */         this.parent.add(k);
/*     */       }
/*     */ 
/*     */       
/*     */       public void set(boolean k) {
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
/* 757 */         if (parentNewPos < AbstractBooleanList.BooleanSubList.this.from - 1) parentNewPos = AbstractBooleanList.BooleanSubList.this.from - 1; 
/* 758 */         int toSkip = parentNewPos - currentPos;
/* 759 */         return this.parent.back(toSkip);
/*     */       }
/*     */ 
/*     */       
/*     */       public int skip(int n) {
/* 764 */         if (n < 0) throw new IllegalArgumentException("Argument must be nonnegative: " + n); 
/* 765 */         int currentPos = this.parent.nextIndex();
/* 766 */         int parentNewPos = currentPos + n;
/* 767 */         if (parentNewPos > AbstractBooleanList.BooleanSubList.this.to) parentNewPos = AbstractBooleanList.BooleanSubList.this.to; 
/* 768 */         int toSkip = parentNewPos - currentPos;
/* 769 */         return this.parent.skip(toSkip);
/*     */       }
/*     */     }
/*     */ 
/*     */     
/*     */     public BooleanListIterator listIterator(int index) {
/* 775 */       ensureIndex(index);
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 780 */       return (this.l instanceof RandomAccess) ? new RandomAccessIter(index) : new ParentWrappingIter(this.l.listIterator(index + this.from));
/*     */     }
/*     */ 
/*     */     
/*     */     public BooleanSpliterator spliterator() {
/* 785 */       return (this.l instanceof RandomAccess) ? new AbstractBooleanList.IndexBasedSpliterator(this.l, this.from, this.to) : super.spliterator();
/*     */     }
/*     */ 
/*     */     
/*     */     public BooleanList subList(int from, int to) {
/* 790 */       ensureIndex(from);
/* 791 */       ensureIndex(to);
/* 792 */       if (from > to) throw new IllegalArgumentException("Start index (" + from + ") is greater than end index (" + to + ")");
/*     */ 
/*     */       
/* 795 */       return new BooleanSubList(this, from, to);
/*     */     }
/*     */ 
/*     */     
/*     */     public boolean rem(boolean k) {
/* 800 */       int index = indexOf(k);
/* 801 */       if (index == -1) return false; 
/* 802 */       this.to--;
/* 803 */       this.l.removeBoolean(this.from + index);
/* 804 */       assert assertRange();
/* 805 */       return true;
/*     */     }
/*     */ 
/*     */     
/*     */     public boolean addAll(int index, BooleanCollection c) {
/* 810 */       ensureIndex(index);
/* 811 */       return super.addAll(index, c);
/*     */     }
/*     */ 
/*     */     
/*     */     public boolean addAll(int index, BooleanList l) {
/* 816 */       ensureIndex(index);
/* 817 */       return super.addAll(index, l);
/*     */     }
/*     */   }
/*     */   
/*     */   public static class BooleanRandomAccessSubList extends BooleanSubList implements RandomAccess {
/*     */     private static final long serialVersionUID = -107070782945191929L;
/*     */     
/*     */     public BooleanRandomAccessSubList(BooleanList l, int from, int to) {
/* 825 */       super(l, from, to);
/*     */     }
/*     */ 
/*     */     
/*     */     public BooleanList subList(int from, int to) {
/* 830 */       ensureIndex(from);
/* 831 */       ensureIndex(to);
/* 832 */       if (from > to) throw new IllegalArgumentException("Start index (" + from + ") is greater than end index (" + to + ")");
/*     */ 
/*     */       
/* 835 */       return new BooleanRandomAccessSubList(this, from, to);
/*     */     }
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\i\\unimi\dsi\fastutil\booleans\AbstractBooleanList.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */