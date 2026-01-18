/*     */ package it.unimi.dsi.fastutil.doubles;
/*     */ 
/*     */ import it.unimi.dsi.fastutil.HashCommon;
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
/*     */ import java.util.function.DoubleConsumer;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public abstract class AbstractDoubleList
/*     */   extends AbstractDoubleCollection
/*     */   implements DoubleList, DoubleStack
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
/*     */   public void add(int index, double k) {
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
/*     */   public boolean add(double k) {
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
/*     */   public double removeDouble(int i) {
/*  98 */     throw new UnsupportedOperationException();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public double set(int index, double k) {
/* 108 */     throw new UnsupportedOperationException();
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean addAll(int index, Collection<? extends Double> c) {
/* 114 */     if (c instanceof DoubleCollection) {
/* 115 */       return addAll(index, (DoubleCollection)c);
/*     */     }
/* 117 */     ensureIndex(index);
/* 118 */     Iterator<? extends Double> i = c.iterator();
/* 119 */     boolean retVal = i.hasNext();
/* 120 */     for (; i.hasNext(); add(index++, ((Double)i.next()).doubleValue()));
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
/*     */   public boolean addAll(Collection<? extends Double> c) {
/* 132 */     return addAll(size(), c);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public DoubleListIterator iterator() {
/* 142 */     return listIterator();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public DoubleListIterator listIterator() {
/* 152 */     return listIterator(0);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public DoubleListIterator listIterator(int index) {
/* 162 */     ensureIndex(index);
/* 163 */     return new DoubleIterators.AbstractIndexBasedListIterator(0, index)
/*     */       {
/*     */         protected final double get(int i) {
/* 166 */           return AbstractDoubleList.this.getDouble(i);
/*     */         }
/*     */ 
/*     */         
/*     */         protected final void add(int i, double k) {
/* 171 */           AbstractDoubleList.this.add(i, k);
/*     */         }
/*     */ 
/*     */         
/*     */         protected final void set(int i, double k) {
/* 176 */           AbstractDoubleList.this.set(i, k);
/*     */         }
/*     */ 
/*     */         
/*     */         protected final void remove(int i) {
/* 181 */           AbstractDoubleList.this.removeDouble(i);
/*     */         }
/*     */ 
/*     */         
/*     */         protected final int getMaxPos() {
/* 186 */           return AbstractDoubleList.this.size();
/*     */         }
/*     */       };
/*     */   }
/*     */   
/*     */   static final class IndexBasedSpliterator extends DoubleSpliterators.LateBindingSizeIndexBasedSpliterator {
/*     */     final DoubleList l;
/*     */     
/*     */     IndexBasedSpliterator(DoubleList l, int pos) {
/* 195 */       super(pos);
/* 196 */       this.l = l;
/*     */     }
/*     */     
/*     */     IndexBasedSpliterator(DoubleList l, int pos, int maxPos) {
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
/*     */     protected final double get(int i) {
/* 211 */       return this.l.getDouble(i);
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
/*     */   public boolean contains(double k) {
/* 228 */     return (indexOf(k) >= 0);
/*     */   }
/*     */ 
/*     */   
/*     */   public int indexOf(double k) {
/* 233 */     DoubleListIterator i = listIterator();
/*     */     
/* 235 */     while (i.hasNext()) {
/* 236 */       double e = i.nextDouble();
/* 237 */       if (Double.doubleToLongBits(k) == Double.doubleToLongBits(e)) return i.previousIndex(); 
/*     */     } 
/* 239 */     return -1;
/*     */   }
/*     */ 
/*     */   
/*     */   public int lastIndexOf(double k) {
/* 244 */     DoubleListIterator i = listIterator(size());
/*     */     
/* 246 */     while (i.hasPrevious()) {
/* 247 */       double e = i.previousDouble();
/* 248 */       if (Double.doubleToLongBits(k) == Double.doubleToLongBits(e)) return i.nextIndex(); 
/*     */     } 
/* 250 */     return -1;
/*     */   }
/*     */ 
/*     */   
/*     */   public void size(int size) {
/* 255 */     int i = size();
/* 256 */     if (size > i) { for (; i++ < size; add(0.0D)); }
/* 257 */     else { for (; i-- != size; removeDouble(i)); }
/*     */   
/*     */   }
/*     */   
/*     */   public DoubleList subList(int from, int to) {
/* 262 */     ensureIndex(from);
/* 263 */     ensureIndex(to);
/* 264 */     if (from > to) throw new IndexOutOfBoundsException("Start index (" + from + ") is greater than end index (" + to + ")"); 
/* 265 */     return (this instanceof RandomAccess) ? new DoubleRandomAccessSubList(this, from, to) : new DoubleSubList(this, from, to);
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
/*     */   public void forEach(DoubleConsumer action) {
/* 277 */     if (this instanceof RandomAccess) {
/* 278 */       for (int i = 0, max = size(); i < max; i++) {
/* 279 */         action.accept(getDouble(i));
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
/* 298 */     DoubleListIterator i = listIterator(from);
/* 299 */     int n = to - from;
/* 300 */     if (n < 0) throw new IllegalArgumentException("Start index (" + from + ") is greater than end index (" + to + ")"); 
/* 301 */     while (n-- != 0) {
/* 302 */       i.nextDouble();
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
/*     */   public void addElements(int index, double[] a, int offset, int length) {
/* 316 */     ensureIndex(index);
/* 317 */     DoubleArrays.ensureOffsetLength(a, offset, length);
/* 318 */     if (this instanceof RandomAccess) {
/* 319 */       for (; length-- != 0; add(index++, a[offset++]));
/*     */     } else {
/* 321 */       DoubleListIterator iter = listIterator(index);
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
/*     */   public void addElements(int index, double[] a) {
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
/*     */   public void getElements(int from, double[] a, int offset, int length) {
/* 345 */     ensureIndex(from);
/* 346 */     DoubleArrays.ensureOffsetLength(a, offset, length);
/* 347 */     if (from + length > size()) throw new IndexOutOfBoundsException("End index (" + (from + length) + ") is greater than list size (" + size() + ")"); 
/* 348 */     if (this instanceof RandomAccess) {
/* 349 */       int current = from;
/* 350 */       for (; length-- != 0; a[offset++] = getDouble(current++));
/*     */     } else {
/* 352 */       DoubleListIterator i = listIterator(from);
/* 353 */       for (; length-- != 0; a[offset++] = i.nextDouble());
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public void setElements(int index, double[] a, int offset, int length) {
/* 359 */     ensureIndex(index);
/* 360 */     DoubleArrays.ensureOffsetLength(a, offset, length);
/* 361 */     if (index + length > size()) throw new IndexOutOfBoundsException("End index (" + (index + length) + ") is greater than list size (" + size() + ")"); 
/* 362 */     if (this instanceof RandomAccess) {
/* 363 */       for (int i = 0; i < length; i++) {
/* 364 */         set(i + index, a[i + offset]);
/*     */       }
/*     */     } else {
/* 367 */       DoubleListIterator iter = listIterator(index);
/* 368 */       int i = 0;
/* 369 */       while (i < length) {
/* 370 */         iter.nextDouble();
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
/* 393 */     DoubleIterator i = iterator();
/* 394 */     int h = 1, s = size();
/* 395 */     while (s-- != 0) {
/* 396 */       double k = i.nextDouble();
/* 397 */       h = 31 * h + HashCommon.double2int(k);
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
/* 409 */     if (l instanceof DoubleList) {
/* 410 */       DoubleListIterator doubleListIterator1 = listIterator(), doubleListIterator2 = ((DoubleList)l).listIterator();
/* 411 */       while (s-- != 0) { if (doubleListIterator1.nextDouble() != doubleListIterator2.nextDouble()) return false;  }
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
/*     */   public int compareTo(List<? extends Double> l) {
/* 432 */     if (l == this) return 0; 
/* 433 */     if (l instanceof DoubleList) {
/* 434 */       DoubleListIterator doubleListIterator1 = listIterator(), doubleListIterator2 = ((DoubleList)l).listIterator();
/*     */ 
/*     */       
/* 437 */       while (doubleListIterator1.hasNext() && doubleListIterator2.hasNext()) {
/* 438 */         double e1 = doubleListIterator1.nextDouble();
/* 439 */         double e2 = doubleListIterator2.nextDouble(); int r;
/* 440 */         if ((r = Double.compare(e1, e2)) != 0) return r; 
/*     */       } 
/* 442 */       return doubleListIterator2.hasNext() ? -1 : (doubleListIterator1.hasNext() ? 1 : 0);
/*     */     } 
/* 444 */     ListIterator<? extends Double> i1 = listIterator(), i2 = l.listIterator();
/*     */     
/* 446 */     while (i1.hasNext() && i2.hasNext()) {
/* 447 */       int r; if ((r = ((Comparable)i1.next()).compareTo(i2.next())) != 0) return r; 
/*     */     } 
/* 449 */     return i2.hasNext() ? -1 : (i1.hasNext() ? 1 : 0);
/*     */   }
/*     */ 
/*     */   
/*     */   public void push(double o) {
/* 454 */     add(o);
/*     */   }
/*     */ 
/*     */   
/*     */   public double popDouble() {
/* 459 */     if (isEmpty()) throw new NoSuchElementException(); 
/* 460 */     return removeDouble(size() - 1);
/*     */   }
/*     */ 
/*     */   
/*     */   public double topDouble() {
/* 465 */     if (isEmpty()) throw new NoSuchElementException(); 
/* 466 */     return getDouble(size() - 1);
/*     */   }
/*     */ 
/*     */   
/*     */   public double peekDouble(int i) {
/* 471 */     return getDouble(size() - 1 - i);
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
/*     */   public boolean rem(double k) {
/* 483 */     int index = indexOf(k);
/* 484 */     if (index == -1) return false; 
/* 485 */     removeDouble(index);
/* 486 */     return true;
/*     */   }
/*     */ 
/*     */   
/*     */   public double[] toDoubleArray() {
/* 491 */     int size = size();
/* 492 */     if (size == 0) return DoubleArrays.EMPTY_ARRAY; 
/* 493 */     double[] ret = new double[size];
/* 494 */     getElements(0, ret, 0, size);
/* 495 */     return ret;
/*     */   }
/*     */ 
/*     */   
/*     */   public double[] toArray(double[] a) {
/* 500 */     int size = size();
/* 501 */     if (a.length < size) {
/* 502 */       a = Arrays.copyOf(a, size);
/*     */     }
/* 504 */     getElements(0, a, 0, size);
/* 505 */     return a;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean addAll(int index, DoubleCollection c) {
/* 510 */     ensureIndex(index);
/* 511 */     DoubleIterator i = c.iterator();
/* 512 */     boolean retVal = i.hasNext();
/* 513 */     for (; i.hasNext(); add(index++, i.nextDouble()));
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
/*     */   public boolean addAll(DoubleCollection c) {
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
/*     */   public final void replaceAll(DoubleUnaryOperator operator) {
/* 536 */     replaceAll(operator);
/*     */   }
/*     */ 
/*     */   
/*     */   public String toString() {
/* 541 */     StringBuilder s = new StringBuilder();
/* 542 */     DoubleIterator i = iterator();
/* 543 */     int n = size();
/*     */     
/* 545 */     boolean first = true;
/* 546 */     s.append("[");
/* 547 */     while (n-- != 0) {
/* 548 */       if (first) { first = false; }
/* 549 */       else { s.append(", "); }
/* 550 */        double k = i.nextDouble();
/* 551 */       s.append(String.valueOf(k));
/*     */     } 
/* 553 */     s.append("]");
/* 554 */     return s.toString();
/*     */   }
/*     */ 
/*     */   
/*     */   public static class DoubleSubList
/*     */     extends AbstractDoubleList
/*     */     implements Serializable
/*     */   {
/*     */     private static final long serialVersionUID = -7046029254386353129L;
/*     */     protected final DoubleList l;
/*     */     protected final int from;
/*     */     protected int to;
/*     */     
/*     */     public DoubleSubList(DoubleList l, int from, int to) {
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
/*     */     public boolean add(double k) {
/* 582 */       this.l.add(this.to, k);
/* 583 */       this.to++;
/* 584 */       assert assertRange();
/* 585 */       return true;
/*     */     }
/*     */ 
/*     */     
/*     */     public void add(int index, double k) {
/* 590 */       ensureIndex(index);
/* 591 */       this.l.add(this.from + index, k);
/* 592 */       this.to++;
/* 593 */       assert assertRange();
/*     */     }
/*     */ 
/*     */     
/*     */     public boolean addAll(int index, Collection<? extends Double> c) {
/* 598 */       ensureIndex(index);
/* 599 */       this.to += c.size();
/* 600 */       return this.l.addAll(this.from + index, c);
/*     */     }
/*     */ 
/*     */     
/*     */     public double getDouble(int index) {
/* 605 */       ensureRestrictedIndex(index);
/* 606 */       return this.l.getDouble(this.from + index);
/*     */     }
/*     */ 
/*     */     
/*     */     public double removeDouble(int index) {
/* 611 */       ensureRestrictedIndex(index);
/* 612 */       this.to--;
/* 613 */       return this.l.removeDouble(this.from + index);
/*     */     }
/*     */ 
/*     */     
/*     */     public double set(int index, double k) {
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
/*     */     public void getElements(int from, double[] a, int offset, int length) {
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
/*     */     public void addElements(int index, double[] a, int offset, int length) {
/* 645 */       ensureIndex(index);
/* 646 */       this.l.addElements(this.from + index, a, offset, length);
/* 647 */       this.to += length;
/* 648 */       assert assertRange();
/*     */     }
/*     */ 
/*     */     
/*     */     public void setElements(int index, double[] a, int offset, int length) {
/* 653 */       ensureIndex(index);
/* 654 */       this.l.setElements(this.from + index, a, offset, length);
/* 655 */       assert assertRange();
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     private final class RandomAccessIter
/*     */       extends DoubleIterators.AbstractIndexBasedListIterator
/*     */     {
/*     */       RandomAccessIter(int pos) {
/* 664 */         super(0, pos);
/*     */       }
/*     */ 
/*     */       
/*     */       protected final double get(int i) {
/* 669 */         return AbstractDoubleList.DoubleSubList.this.l.getDouble(AbstractDoubleList.DoubleSubList.this.from + i);
/*     */       }
/*     */ 
/*     */ 
/*     */       
/*     */       protected final void add(int i, double k) {
/* 675 */         AbstractDoubleList.DoubleSubList.this.add(i, k);
/*     */       }
/*     */ 
/*     */       
/*     */       protected final void set(int i, double k) {
/* 680 */         AbstractDoubleList.DoubleSubList.this.set(i, k);
/*     */       }
/*     */ 
/*     */       
/*     */       protected final void remove(int i) {
/* 685 */         AbstractDoubleList.DoubleSubList.this.removeDouble(i);
/*     */       }
/*     */ 
/*     */       
/*     */       protected final int getMaxPos() {
/* 690 */         return AbstractDoubleList.DoubleSubList.this.to - AbstractDoubleList.DoubleSubList.this.from;
/*     */       }
/*     */ 
/*     */       
/*     */       public void add(double k) {
/* 695 */         super.add(k);
/* 696 */         assert AbstractDoubleList.DoubleSubList.this.assertRange();
/*     */       }
/*     */ 
/*     */       
/*     */       public void remove() {
/* 701 */         super.remove();
/* 702 */         assert AbstractDoubleList.DoubleSubList.this.assertRange();
/*     */       }
/*     */     }
/*     */     
/*     */     private class ParentWrappingIter implements DoubleListIterator {
/*     */       private DoubleListIterator parent;
/*     */       
/*     */       ParentWrappingIter(DoubleListIterator parent) {
/* 710 */         this.parent = parent;
/*     */       }
/*     */ 
/*     */       
/*     */       public int nextIndex() {
/* 715 */         return this.parent.nextIndex() - AbstractDoubleList.DoubleSubList.this.from;
/*     */       }
/*     */ 
/*     */       
/*     */       public int previousIndex() {
/* 720 */         return this.parent.previousIndex() - AbstractDoubleList.DoubleSubList.this.from;
/*     */       }
/*     */ 
/*     */       
/*     */       public boolean hasNext() {
/* 725 */         return (this.parent.nextIndex() < AbstractDoubleList.DoubleSubList.this.to);
/*     */       }
/*     */ 
/*     */       
/*     */       public boolean hasPrevious() {
/* 730 */         return (this.parent.previousIndex() >= AbstractDoubleList.DoubleSubList.this.from);
/*     */       }
/*     */ 
/*     */       
/*     */       public double nextDouble() {
/* 735 */         if (!hasNext()) throw new NoSuchElementException(); 
/* 736 */         return this.parent.nextDouble();
/*     */       }
/*     */ 
/*     */       
/*     */       public double previousDouble() {
/* 741 */         if (!hasPrevious()) throw new NoSuchElementException(); 
/* 742 */         return this.parent.previousDouble();
/*     */       }
/*     */ 
/*     */       
/*     */       public void add(double k) {
/* 747 */         this.parent.add(k);
/*     */       }
/*     */ 
/*     */       
/*     */       public void set(double k) {
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
/* 768 */         if (parentNewPos < AbstractDoubleList.DoubleSubList.this.from - 1) parentNewPos = AbstractDoubleList.DoubleSubList.this.from - 1; 
/* 769 */         int toSkip = parentNewPos - currentPos;
/* 770 */         return this.parent.back(toSkip);
/*     */       }
/*     */ 
/*     */       
/*     */       public int skip(int n) {
/* 775 */         if (n < 0) throw new IllegalArgumentException("Argument must be nonnegative: " + n); 
/* 776 */         int currentPos = this.parent.nextIndex();
/* 777 */         int parentNewPos = currentPos + n;
/* 778 */         if (parentNewPos > AbstractDoubleList.DoubleSubList.this.to) parentNewPos = AbstractDoubleList.DoubleSubList.this.to; 
/* 779 */         int toSkip = parentNewPos - currentPos;
/* 780 */         return this.parent.skip(toSkip);
/*     */       }
/*     */     }
/*     */ 
/*     */     
/*     */     public DoubleListIterator listIterator(int index) {
/* 786 */       ensureIndex(index);
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 791 */       return (this.l instanceof RandomAccess) ? new RandomAccessIter(index) : new ParentWrappingIter(this.l.listIterator(index + this.from));
/*     */     }
/*     */ 
/*     */     
/*     */     public DoubleSpliterator spliterator() {
/* 796 */       return (this.l instanceof RandomAccess) ? new AbstractDoubleList.IndexBasedSpliterator(this.l, this.from, this.to) : super.spliterator();
/*     */     }
/*     */ 
/*     */     
/*     */     public DoubleList subList(int from, int to) {
/* 801 */       ensureIndex(from);
/* 802 */       ensureIndex(to);
/* 803 */       if (from > to) throw new IllegalArgumentException("Start index (" + from + ") is greater than end index (" + to + ")");
/*     */ 
/*     */       
/* 806 */       return new DoubleSubList(this, from, to);
/*     */     }
/*     */ 
/*     */     
/*     */     public boolean rem(double k) {
/* 811 */       int index = indexOf(k);
/* 812 */       if (index == -1) return false; 
/* 813 */       this.to--;
/* 814 */       this.l.removeDouble(this.from + index);
/* 815 */       assert assertRange();
/* 816 */       return true;
/*     */     }
/*     */ 
/*     */     
/*     */     public boolean addAll(int index, DoubleCollection c) {
/* 821 */       ensureIndex(index);
/* 822 */       return super.addAll(index, c);
/*     */     }
/*     */ 
/*     */     
/*     */     public boolean addAll(int index, DoubleList l) {
/* 827 */       ensureIndex(index);
/* 828 */       return super.addAll(index, l);
/*     */     }
/*     */   }
/*     */   
/*     */   public static class DoubleRandomAccessSubList extends DoubleSubList implements RandomAccess {
/*     */     private static final long serialVersionUID = -107070782945191929L;
/*     */     
/*     */     public DoubleRandomAccessSubList(DoubleList l, int from, int to) {
/* 836 */       super(l, from, to);
/*     */     }
/*     */ 
/*     */     
/*     */     public DoubleList subList(int from, int to) {
/* 841 */       ensureIndex(from);
/* 842 */       ensureIndex(to);
/* 843 */       if (from > to) throw new IllegalArgumentException("Start index (" + from + ") is greater than end index (" + to + ")");
/*     */ 
/*     */       
/* 846 */       return new DoubleRandomAccessSubList(this, from, to);
/*     */     }
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\i\\unimi\dsi\fastutil\doubles\AbstractDoubleList.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */