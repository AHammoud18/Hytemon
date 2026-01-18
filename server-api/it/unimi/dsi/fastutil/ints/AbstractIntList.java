/*     */ package it.unimi.dsi.fastutil.ints;
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
/*     */ import java.util.function.IntConsumer;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public abstract class AbstractIntList
/*     */   extends AbstractIntCollection
/*     */   implements IntList, IntStack
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
/*     */   public void add(int index, int k) {
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
/*     */   public boolean add(int k) {
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
/*     */   public int removeInt(int i) {
/*  98 */     throw new UnsupportedOperationException();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int set(int index, int k) {
/* 108 */     throw new UnsupportedOperationException();
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean addAll(int index, Collection<? extends Integer> c) {
/* 114 */     if (c instanceof IntCollection) {
/* 115 */       return addAll(index, (IntCollection)c);
/*     */     }
/* 117 */     ensureIndex(index);
/* 118 */     Iterator<? extends Integer> i = c.iterator();
/* 119 */     boolean retVal = i.hasNext();
/* 120 */     for (; i.hasNext(); add(index++, ((Integer)i.next()).intValue()));
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
/*     */   public boolean addAll(Collection<? extends Integer> c) {
/* 132 */     return addAll(size(), c);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public IntListIterator iterator() {
/* 142 */     return listIterator();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public IntListIterator listIterator() {
/* 152 */     return listIterator(0);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public IntListIterator listIterator(int index) {
/* 162 */     ensureIndex(index);
/* 163 */     return new IntIterators.AbstractIndexBasedListIterator(0, index)
/*     */       {
/*     */         protected final int get(int i) {
/* 166 */           return AbstractIntList.this.getInt(i);
/*     */         }
/*     */ 
/*     */         
/*     */         protected final void add(int i, int k) {
/* 171 */           AbstractIntList.this.add(i, k);
/*     */         }
/*     */ 
/*     */         
/*     */         protected final void set(int i, int k) {
/* 176 */           AbstractIntList.this.set(i, k);
/*     */         }
/*     */ 
/*     */         
/*     */         protected final void remove(int i) {
/* 181 */           AbstractIntList.this.removeInt(i);
/*     */         }
/*     */ 
/*     */         
/*     */         protected final int getMaxPos() {
/* 186 */           return AbstractIntList.this.size();
/*     */         }
/*     */       };
/*     */   }
/*     */   
/*     */   static final class IndexBasedSpliterator extends IntSpliterators.LateBindingSizeIndexBasedSpliterator {
/*     */     final IntList l;
/*     */     
/*     */     IndexBasedSpliterator(IntList l, int pos) {
/* 195 */       super(pos);
/* 196 */       this.l = l;
/*     */     }
/*     */     
/*     */     IndexBasedSpliterator(IntList l, int pos, int maxPos) {
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
/*     */     protected final int get(int i) {
/* 211 */       return this.l.getInt(i);
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
/*     */   public boolean contains(int k) {
/* 228 */     return (indexOf(k) >= 0);
/*     */   }
/*     */ 
/*     */   
/*     */   public int indexOf(int k) {
/* 233 */     IntListIterator i = listIterator();
/*     */     
/* 235 */     while (i.hasNext()) {
/* 236 */       int e = i.nextInt();
/* 237 */       if (k == e) return i.previousIndex(); 
/*     */     } 
/* 239 */     return -1;
/*     */   }
/*     */ 
/*     */   
/*     */   public int lastIndexOf(int k) {
/* 244 */     IntListIterator i = listIterator(size());
/*     */     
/* 246 */     while (i.hasPrevious()) {
/* 247 */       int e = i.previousInt();
/* 248 */       if (k == e) return i.nextIndex(); 
/*     */     } 
/* 250 */     return -1;
/*     */   }
/*     */ 
/*     */   
/*     */   public void size(int size) {
/* 255 */     int i = size();
/* 256 */     if (size > i) { for (; i++ < size; add(0)); }
/* 257 */     else { for (; i-- != size; removeInt(i)); }
/*     */   
/*     */   }
/*     */   
/*     */   public IntList subList(int from, int to) {
/* 262 */     ensureIndex(from);
/* 263 */     ensureIndex(to);
/* 264 */     if (from > to) throw new IndexOutOfBoundsException("Start index (" + from + ") is greater than end index (" + to + ")"); 
/* 265 */     return (this instanceof RandomAccess) ? new IntRandomAccessSubList(this, from, to) : new IntSubList(this, from, to);
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
/*     */   public void forEach(IntConsumer action) {
/* 277 */     if (this instanceof RandomAccess) {
/* 278 */       for (int i = 0, max = size(); i < max; i++) {
/* 279 */         action.accept(getInt(i));
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
/* 298 */     IntListIterator i = listIterator(from);
/* 299 */     int n = to - from;
/* 300 */     if (n < 0) throw new IllegalArgumentException("Start index (" + from + ") is greater than end index (" + to + ")"); 
/* 301 */     while (n-- != 0) {
/* 302 */       i.nextInt();
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
/*     */   public void addElements(int index, int[] a, int offset, int length) {
/* 316 */     ensureIndex(index);
/* 317 */     IntArrays.ensureOffsetLength(a, offset, length);
/* 318 */     if (this instanceof RandomAccess) {
/* 319 */       for (; length-- != 0; add(index++, a[offset++]));
/*     */     } else {
/* 321 */       IntListIterator iter = listIterator(index);
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
/*     */   public void addElements(int index, int[] a) {
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
/*     */   public void getElements(int from, int[] a, int offset, int length) {
/* 345 */     ensureIndex(from);
/* 346 */     IntArrays.ensureOffsetLength(a, offset, length);
/* 347 */     if (from + length > size()) throw new IndexOutOfBoundsException("End index (" + (from + length) + ") is greater than list size (" + size() + ")"); 
/* 348 */     if (this instanceof RandomAccess) {
/* 349 */       int current = from;
/* 350 */       for (; length-- != 0; a[offset++] = getInt(current++));
/*     */     } else {
/* 352 */       IntListIterator i = listIterator(from);
/* 353 */       for (; length-- != 0; a[offset++] = i.nextInt());
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public void setElements(int index, int[] a, int offset, int length) {
/* 359 */     ensureIndex(index);
/* 360 */     IntArrays.ensureOffsetLength(a, offset, length);
/* 361 */     if (index + length > size()) throw new IndexOutOfBoundsException("End index (" + (index + length) + ") is greater than list size (" + size() + ")"); 
/* 362 */     if (this instanceof RandomAccess) {
/* 363 */       for (int i = 0; i < length; i++) {
/* 364 */         set(i + index, a[i + offset]);
/*     */       }
/*     */     } else {
/* 367 */       IntListIterator iter = listIterator(index);
/* 368 */       int i = 0;
/* 369 */       while (i < length) {
/* 370 */         iter.nextInt();
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
/* 393 */     IntIterator i = iterator();
/* 394 */     int h = 1, s = size();
/* 395 */     while (s-- != 0) {
/* 396 */       int k = i.nextInt();
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
/* 409 */     if (l instanceof IntList) {
/* 410 */       IntListIterator intListIterator1 = listIterator(), intListIterator2 = ((IntList)l).listIterator();
/* 411 */       while (s-- != 0) { if (intListIterator1.nextInt() != intListIterator2.nextInt()) return false;  }
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
/*     */   public int compareTo(List<? extends Integer> l) {
/* 432 */     if (l == this) return 0; 
/* 433 */     if (l instanceof IntList) {
/* 434 */       IntListIterator intListIterator1 = listIterator(), intListIterator2 = ((IntList)l).listIterator();
/*     */ 
/*     */       
/* 437 */       while (intListIterator1.hasNext() && intListIterator2.hasNext()) {
/* 438 */         int e1 = intListIterator1.nextInt();
/* 439 */         int e2 = intListIterator2.nextInt(); int r;
/* 440 */         if ((r = Integer.compare(e1, e2)) != 0) return r; 
/*     */       } 
/* 442 */       return intListIterator2.hasNext() ? -1 : (intListIterator1.hasNext() ? 1 : 0);
/*     */     } 
/* 444 */     ListIterator<? extends Integer> i1 = listIterator(), i2 = l.listIterator();
/*     */     
/* 446 */     while (i1.hasNext() && i2.hasNext()) {
/* 447 */       int r; if ((r = ((Comparable)i1.next()).compareTo(i2.next())) != 0) return r; 
/*     */     } 
/* 449 */     return i2.hasNext() ? -1 : (i1.hasNext() ? 1 : 0);
/*     */   }
/*     */ 
/*     */   
/*     */   public void push(int o) {
/* 454 */     add(o);
/*     */   }
/*     */ 
/*     */   
/*     */   public int popInt() {
/* 459 */     if (isEmpty()) throw new NoSuchElementException(); 
/* 460 */     return removeInt(size() - 1);
/*     */   }
/*     */ 
/*     */   
/*     */   public int topInt() {
/* 465 */     if (isEmpty()) throw new NoSuchElementException(); 
/* 466 */     return getInt(size() - 1);
/*     */   }
/*     */ 
/*     */   
/*     */   public int peekInt(int i) {
/* 471 */     return getInt(size() - 1 - i);
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
/*     */   public boolean rem(int k) {
/* 483 */     int index = indexOf(k);
/* 484 */     if (index == -1) return false; 
/* 485 */     removeInt(index);
/* 486 */     return true;
/*     */   }
/*     */ 
/*     */   
/*     */   public int[] toIntArray() {
/* 491 */     int size = size();
/* 492 */     if (size == 0) return IntArrays.EMPTY_ARRAY; 
/* 493 */     int[] ret = new int[size];
/* 494 */     getElements(0, ret, 0, size);
/* 495 */     return ret;
/*     */   }
/*     */ 
/*     */   
/*     */   public int[] toArray(int[] a) {
/* 500 */     int size = size();
/* 501 */     if (a.length < size) {
/* 502 */       a = Arrays.copyOf(a, size);
/*     */     }
/* 504 */     getElements(0, a, 0, size);
/* 505 */     return a;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean addAll(int index, IntCollection c) {
/* 510 */     ensureIndex(index);
/* 511 */     IntIterator i = c.iterator();
/* 512 */     boolean retVal = i.hasNext();
/* 513 */     for (; i.hasNext(); add(index++, i.nextInt()));
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
/*     */   public boolean addAll(IntCollection c) {
/* 525 */     return addAll(size(), c);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public final void replaceAll(IntUnaryOperator operator) {
/* 536 */     replaceAll(operator);
/*     */   }
/*     */ 
/*     */   
/*     */   public String toString() {
/* 541 */     StringBuilder s = new StringBuilder();
/* 542 */     IntIterator i = iterator();
/* 543 */     int n = size();
/*     */     
/* 545 */     boolean first = true;
/* 546 */     s.append("[");
/* 547 */     while (n-- != 0) {
/* 548 */       if (first) { first = false; }
/* 549 */       else { s.append(", "); }
/* 550 */        int k = i.nextInt();
/* 551 */       s.append(String.valueOf(k));
/*     */     } 
/* 553 */     s.append("]");
/* 554 */     return s.toString();
/*     */   }
/*     */ 
/*     */   
/*     */   public static class IntSubList
/*     */     extends AbstractIntList
/*     */     implements Serializable
/*     */   {
/*     */     private static final long serialVersionUID = -7046029254386353129L;
/*     */     protected final IntList l;
/*     */     protected final int from;
/*     */     protected int to;
/*     */     
/*     */     public IntSubList(IntList l, int from, int to) {
/* 568 */       this.l = l;
/* 569 */       this.from = from;
/* 570 */       this.to = to;
/*     */     }
/*     */     
/*     */     private boolean assertRange() {
/* 574 */       assert this.from <= this.l.size();
/* 575 */       assert this.to <= this.l.size();
/* 576 */       assert this.to >= this.from;
/* 577 */       return true;
/*     */     }
/*     */ 
/*     */     
/*     */     public boolean add(int k) {
/* 582 */       this.l.add(this.to, k);
/* 583 */       this.to++;
/* 584 */       assert assertRange();
/* 585 */       return true;
/*     */     }
/*     */ 
/*     */     
/*     */     public void add(int index, int k) {
/* 590 */       ensureIndex(index);
/* 591 */       this.l.add(this.from + index, k);
/* 592 */       this.to++;
/* 593 */       assert assertRange();
/*     */     }
/*     */ 
/*     */     
/*     */     public boolean addAll(int index, Collection<? extends Integer> c) {
/* 598 */       ensureIndex(index);
/* 599 */       this.to += c.size();
/* 600 */       return this.l.addAll(this.from + index, c);
/*     */     }
/*     */ 
/*     */     
/*     */     public int getInt(int index) {
/* 605 */       ensureRestrictedIndex(index);
/* 606 */       return this.l.getInt(this.from + index);
/*     */     }
/*     */ 
/*     */     
/*     */     public int removeInt(int index) {
/* 611 */       ensureRestrictedIndex(index);
/* 612 */       this.to--;
/* 613 */       return this.l.removeInt(this.from + index);
/*     */     }
/*     */ 
/*     */     
/*     */     public int set(int index, int k) {
/* 618 */       ensureRestrictedIndex(index);
/* 619 */       return this.l.set(this.from + index, k);
/*     */     }
/*     */ 
/*     */     
/*     */     public int size() {
/* 624 */       return this.to - this.from;
/*     */     }
/*     */ 
/*     */     
/*     */     public void getElements(int from, int[] a, int offset, int length) {
/* 629 */       ensureIndex(from);
/* 630 */       if (from + length > size()) throw new IndexOutOfBoundsException("End index (" + from + length + ") is greater than list size (" + size() + ")"); 
/* 631 */       this.l.getElements(this.from + from, a, offset, length);
/*     */     }
/*     */ 
/*     */     
/*     */     public void removeElements(int from, int to) {
/* 636 */       ensureIndex(from);
/* 637 */       ensureIndex(to);
/* 638 */       this.l.removeElements(this.from + from, this.from + to);
/* 639 */       this.to -= to - from;
/* 640 */       assert assertRange();
/*     */     }
/*     */ 
/*     */     
/*     */     public void addElements(int index, int[] a, int offset, int length) {
/* 645 */       ensureIndex(index);
/* 646 */       this.l.addElements(this.from + index, a, offset, length);
/* 647 */       this.to += length;
/* 648 */       assert assertRange();
/*     */     }
/*     */ 
/*     */     
/*     */     public void setElements(int index, int[] a, int offset, int length) {
/* 653 */       ensureIndex(index);
/* 654 */       this.l.setElements(this.from + index, a, offset, length);
/* 655 */       assert assertRange();
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     private final class RandomAccessIter
/*     */       extends IntIterators.AbstractIndexBasedListIterator
/*     */     {
/*     */       RandomAccessIter(int pos) {
/* 664 */         super(0, pos);
/*     */       }
/*     */ 
/*     */       
/*     */       protected final int get(int i) {
/* 669 */         return AbstractIntList.IntSubList.this.l.getInt(AbstractIntList.IntSubList.this.from + i);
/*     */       }
/*     */ 
/*     */ 
/*     */       
/*     */       protected final void add(int i, int k) {
/* 675 */         AbstractIntList.IntSubList.this.add(i, k);
/*     */       }
/*     */ 
/*     */       
/*     */       protected final void set(int i, int k) {
/* 680 */         AbstractIntList.IntSubList.this.set(i, k);
/*     */       }
/*     */ 
/*     */       
/*     */       protected final void remove(int i) {
/* 685 */         AbstractIntList.IntSubList.this.removeInt(i);
/*     */       }
/*     */ 
/*     */       
/*     */       protected final int getMaxPos() {
/* 690 */         return AbstractIntList.IntSubList.this.to - AbstractIntList.IntSubList.this.from;
/*     */       }
/*     */ 
/*     */       
/*     */       public void add(int k) {
/* 695 */         super.add(k);
/* 696 */         assert AbstractIntList.IntSubList.this.assertRange();
/*     */       }
/*     */ 
/*     */       
/*     */       public void remove() {
/* 701 */         super.remove();
/* 702 */         assert AbstractIntList.IntSubList.this.assertRange();
/*     */       }
/*     */     }
/*     */     
/*     */     private class ParentWrappingIter implements IntListIterator {
/*     */       private IntListIterator parent;
/*     */       
/*     */       ParentWrappingIter(IntListIterator parent) {
/* 710 */         this.parent = parent;
/*     */       }
/*     */ 
/*     */       
/*     */       public int nextIndex() {
/* 715 */         return this.parent.nextIndex() - AbstractIntList.IntSubList.this.from;
/*     */       }
/*     */ 
/*     */       
/*     */       public int previousIndex() {
/* 720 */         return this.parent.previousIndex() - AbstractIntList.IntSubList.this.from;
/*     */       }
/*     */ 
/*     */       
/*     */       public boolean hasNext() {
/* 725 */         return (this.parent.nextIndex() < AbstractIntList.IntSubList.this.to);
/*     */       }
/*     */ 
/*     */       
/*     */       public boolean hasPrevious() {
/* 730 */         return (this.parent.previousIndex() >= AbstractIntList.IntSubList.this.from);
/*     */       }
/*     */ 
/*     */       
/*     */       public int nextInt() {
/* 735 */         if (!hasNext()) throw new NoSuchElementException(); 
/* 736 */         return this.parent.nextInt();
/*     */       }
/*     */ 
/*     */       
/*     */       public int previousInt() {
/* 741 */         if (!hasPrevious()) throw new NoSuchElementException(); 
/* 742 */         return this.parent.previousInt();
/*     */       }
/*     */ 
/*     */       
/*     */       public void add(int k) {
/* 747 */         this.parent.add(k);
/*     */       }
/*     */ 
/*     */       
/*     */       public void set(int k) {
/* 752 */         this.parent.set(k);
/*     */       }
/*     */ 
/*     */       
/*     */       public void remove() {
/* 757 */         this.parent.remove();
/*     */       }
/*     */ 
/*     */       
/*     */       public int back(int n) {
/* 762 */         if (n < 0) throw new IllegalArgumentException("Argument must be nonnegative: " + n); 
/* 763 */         int currentPos = this.parent.previousIndex();
/* 764 */         int parentNewPos = currentPos - n;
/*     */ 
/*     */ 
/*     */         
/* 768 */         if (parentNewPos < AbstractIntList.IntSubList.this.from - 1) parentNewPos = AbstractIntList.IntSubList.this.from - 1; 
/* 769 */         int toSkip = parentNewPos - currentPos;
/* 770 */         return this.parent.back(toSkip);
/*     */       }
/*     */ 
/*     */       
/*     */       public int skip(int n) {
/* 775 */         if (n < 0) throw new IllegalArgumentException("Argument must be nonnegative: " + n); 
/* 776 */         int currentPos = this.parent.nextIndex();
/* 777 */         int parentNewPos = currentPos + n;
/* 778 */         if (parentNewPos > AbstractIntList.IntSubList.this.to) parentNewPos = AbstractIntList.IntSubList.this.to; 
/* 779 */         int toSkip = parentNewPos - currentPos;
/* 780 */         return this.parent.skip(toSkip);
/*     */       }
/*     */     }
/*     */ 
/*     */     
/*     */     public IntListIterator listIterator(int index) {
/* 786 */       ensureIndex(index);
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 791 */       return (this.l instanceof RandomAccess) ? new RandomAccessIter(index) : new ParentWrappingIter(this.l.listIterator(index + this.from));
/*     */     }
/*     */ 
/*     */     
/*     */     public IntSpliterator spliterator() {
/* 796 */       return (this.l instanceof RandomAccess) ? new AbstractIntList.IndexBasedSpliterator(this.l, this.from, this.to) : super.spliterator();
/*     */     }
/*     */ 
/*     */     
/*     */     public IntList subList(int from, int to) {
/* 801 */       ensureIndex(from);
/* 802 */       ensureIndex(to);
/* 803 */       if (from > to) throw new IllegalArgumentException("Start index (" + from + ") is greater than end index (" + to + ")");
/*     */ 
/*     */       
/* 806 */       return new IntSubList(this, from, to);
/*     */     }
/*     */ 
/*     */     
/*     */     public boolean rem(int k) {
/* 811 */       int index = indexOf(k);
/* 812 */       if (index == -1) return false; 
/* 813 */       this.to--;
/* 814 */       this.l.removeInt(this.from + index);
/* 815 */       assert assertRange();
/* 816 */       return true;
/*     */     }
/*     */ 
/*     */     
/*     */     public boolean addAll(int index, IntCollection c) {
/* 821 */       ensureIndex(index);
/* 822 */       return super.addAll(index, c);
/*     */     }
/*     */ 
/*     */     
/*     */     public boolean addAll(int index, IntList l) {
/* 827 */       ensureIndex(index);
/* 828 */       return super.addAll(index, l);
/*     */     }
/*     */   }
/*     */   
/*     */   public static class IntRandomAccessSubList extends IntSubList implements RandomAccess {
/*     */     private static final long serialVersionUID = -107070782945191929L;
/*     */     
/*     */     public IntRandomAccessSubList(IntList l, int from, int to) {
/* 836 */       super(l, from, to);
/*     */     }
/*     */ 
/*     */     
/*     */     public IntList subList(int from, int to) {
/* 841 */       ensureIndex(from);
/* 842 */       ensureIndex(to);
/* 843 */       if (from > to) throw new IllegalArgumentException("Start index (" + from + ") is greater than end index (" + to + ")");
/*     */ 
/*     */       
/* 846 */       return new IntRandomAccessSubList(this, from, to);
/*     */     }
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\i\\unimi\dsi\fastutil\ints\AbstractIntList.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */