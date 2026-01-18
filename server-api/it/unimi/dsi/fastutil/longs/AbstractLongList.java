/*     */ package it.unimi.dsi.fastutil.longs;
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
/*     */ import java.util.function.LongConsumer;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public abstract class AbstractLongList
/*     */   extends AbstractLongCollection
/*     */   implements LongList, LongStack
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
/*     */   public void add(int index, long k) {
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
/*     */   public boolean add(long k) {
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
/*     */   public long removeLong(int i) {
/*  98 */     throw new UnsupportedOperationException();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public long set(int index, long k) {
/* 108 */     throw new UnsupportedOperationException();
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean addAll(int index, Collection<? extends Long> c) {
/* 114 */     if (c instanceof LongCollection) {
/* 115 */       return addAll(index, (LongCollection)c);
/*     */     }
/* 117 */     ensureIndex(index);
/* 118 */     Iterator<? extends Long> i = c.iterator();
/* 119 */     boolean retVal = i.hasNext();
/* 120 */     for (; i.hasNext(); add(index++, ((Long)i.next()).longValue()));
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
/*     */   public boolean addAll(Collection<? extends Long> c) {
/* 132 */     return addAll(size(), c);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public LongListIterator iterator() {
/* 142 */     return listIterator();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public LongListIterator listIterator() {
/* 152 */     return listIterator(0);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public LongListIterator listIterator(int index) {
/* 162 */     ensureIndex(index);
/* 163 */     return new LongIterators.AbstractIndexBasedListIterator(0, index)
/*     */       {
/*     */         protected final long get(int i) {
/* 166 */           return AbstractLongList.this.getLong(i);
/*     */         }
/*     */ 
/*     */         
/*     */         protected final void add(int i, long k) {
/* 171 */           AbstractLongList.this.add(i, k);
/*     */         }
/*     */ 
/*     */         
/*     */         protected final void set(int i, long k) {
/* 176 */           AbstractLongList.this.set(i, k);
/*     */         }
/*     */ 
/*     */         
/*     */         protected final void remove(int i) {
/* 181 */           AbstractLongList.this.removeLong(i);
/*     */         }
/*     */ 
/*     */         
/*     */         protected final int getMaxPos() {
/* 186 */           return AbstractLongList.this.size();
/*     */         }
/*     */       };
/*     */   }
/*     */   
/*     */   static final class IndexBasedSpliterator extends LongSpliterators.LateBindingSizeIndexBasedSpliterator {
/*     */     final LongList l;
/*     */     
/*     */     IndexBasedSpliterator(LongList l, int pos) {
/* 195 */       super(pos);
/* 196 */       this.l = l;
/*     */     }
/*     */     
/*     */     IndexBasedSpliterator(LongList l, int pos, int maxPos) {
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
/*     */     protected final long get(int i) {
/* 211 */       return this.l.getLong(i);
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
/*     */   public boolean contains(long k) {
/* 228 */     return (indexOf(k) >= 0);
/*     */   }
/*     */ 
/*     */   
/*     */   public int indexOf(long k) {
/* 233 */     LongListIterator i = listIterator();
/*     */     
/* 235 */     while (i.hasNext()) {
/* 236 */       long e = i.nextLong();
/* 237 */       if (k == e) return i.previousIndex(); 
/*     */     } 
/* 239 */     return -1;
/*     */   }
/*     */ 
/*     */   
/*     */   public int lastIndexOf(long k) {
/* 244 */     LongListIterator i = listIterator(size());
/*     */     
/* 246 */     while (i.hasPrevious()) {
/* 247 */       long e = i.previousLong();
/* 248 */       if (k == e) return i.nextIndex(); 
/*     */     } 
/* 250 */     return -1;
/*     */   }
/*     */ 
/*     */   
/*     */   public void size(int size) {
/* 255 */     int i = size();
/* 256 */     if (size > i) { for (; i++ < size; add(0L)); }
/* 257 */     else { for (; i-- != size; removeLong(i)); }
/*     */   
/*     */   }
/*     */   
/*     */   public LongList subList(int from, int to) {
/* 262 */     ensureIndex(from);
/* 263 */     ensureIndex(to);
/* 264 */     if (from > to) throw new IndexOutOfBoundsException("Start index (" + from + ") is greater than end index (" + to + ")"); 
/* 265 */     return (this instanceof RandomAccess) ? new LongRandomAccessSubList(this, from, to) : new LongSubList(this, from, to);
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
/*     */   public void forEach(LongConsumer action) {
/* 277 */     if (this instanceof RandomAccess) {
/* 278 */       for (int i = 0, max = size(); i < max; i++) {
/* 279 */         action.accept(getLong(i));
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
/* 298 */     LongListIterator i = listIterator(from);
/* 299 */     int n = to - from;
/* 300 */     if (n < 0) throw new IllegalArgumentException("Start index (" + from + ") is greater than end index (" + to + ")"); 
/* 301 */     while (n-- != 0) {
/* 302 */       i.nextLong();
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
/*     */   public void addElements(int index, long[] a, int offset, int length) {
/* 316 */     ensureIndex(index);
/* 317 */     LongArrays.ensureOffsetLength(a, offset, length);
/* 318 */     if (this instanceof RandomAccess) {
/* 319 */       for (; length-- != 0; add(index++, a[offset++]));
/*     */     } else {
/* 321 */       LongListIterator iter = listIterator(index);
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
/*     */   public void addElements(int index, long[] a) {
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
/*     */   public void getElements(int from, long[] a, int offset, int length) {
/* 345 */     ensureIndex(from);
/* 346 */     LongArrays.ensureOffsetLength(a, offset, length);
/* 347 */     if (from + length > size()) throw new IndexOutOfBoundsException("End index (" + (from + length) + ") is greater than list size (" + size() + ")"); 
/* 348 */     if (this instanceof RandomAccess) {
/* 349 */       int current = from;
/* 350 */       for (; length-- != 0; a[offset++] = getLong(current++));
/*     */     } else {
/* 352 */       LongListIterator i = listIterator(from);
/* 353 */       for (; length-- != 0; a[offset++] = i.nextLong());
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public void setElements(int index, long[] a, int offset, int length) {
/* 359 */     ensureIndex(index);
/* 360 */     LongArrays.ensureOffsetLength(a, offset, length);
/* 361 */     if (index + length > size()) throw new IndexOutOfBoundsException("End index (" + (index + length) + ") is greater than list size (" + size() + ")"); 
/* 362 */     if (this instanceof RandomAccess) {
/* 363 */       for (int i = 0; i < length; i++) {
/* 364 */         set(i + index, a[i + offset]);
/*     */       }
/*     */     } else {
/* 367 */       LongListIterator iter = listIterator(index);
/* 368 */       int i = 0;
/* 369 */       while (i < length) {
/* 370 */         iter.nextLong();
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
/* 393 */     LongIterator i = iterator();
/* 394 */     int h = 1, s = size();
/* 395 */     while (s-- != 0) {
/* 396 */       long k = i.nextLong();
/* 397 */       h = 31 * h + HashCommon.long2int(k);
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
/* 409 */     if (l instanceof LongList) {
/* 410 */       LongListIterator longListIterator1 = listIterator(), longListIterator2 = ((LongList)l).listIterator();
/* 411 */       while (s-- != 0) { if (longListIterator1.nextLong() != longListIterator2.nextLong()) return false;  }
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
/*     */   public int compareTo(List<? extends Long> l) {
/* 432 */     if (l == this) return 0; 
/* 433 */     if (l instanceof LongList) {
/* 434 */       LongListIterator longListIterator1 = listIterator(), longListIterator2 = ((LongList)l).listIterator();
/*     */ 
/*     */       
/* 437 */       while (longListIterator1.hasNext() && longListIterator2.hasNext()) {
/* 438 */         long e1 = longListIterator1.nextLong();
/* 439 */         long e2 = longListIterator2.nextLong(); int r;
/* 440 */         if ((r = Long.compare(e1, e2)) != 0) return r; 
/*     */       } 
/* 442 */       return longListIterator2.hasNext() ? -1 : (longListIterator1.hasNext() ? 1 : 0);
/*     */     } 
/* 444 */     ListIterator<? extends Long> i1 = listIterator(), i2 = l.listIterator();
/*     */     
/* 446 */     while (i1.hasNext() && i2.hasNext()) {
/* 447 */       int r; if ((r = ((Comparable)i1.next()).compareTo(i2.next())) != 0) return r; 
/*     */     } 
/* 449 */     return i2.hasNext() ? -1 : (i1.hasNext() ? 1 : 0);
/*     */   }
/*     */ 
/*     */   
/*     */   public void push(long o) {
/* 454 */     add(o);
/*     */   }
/*     */ 
/*     */   
/*     */   public long popLong() {
/* 459 */     if (isEmpty()) throw new NoSuchElementException(); 
/* 460 */     return removeLong(size() - 1);
/*     */   }
/*     */ 
/*     */   
/*     */   public long topLong() {
/* 465 */     if (isEmpty()) throw new NoSuchElementException(); 
/* 466 */     return getLong(size() - 1);
/*     */   }
/*     */ 
/*     */   
/*     */   public long peekLong(int i) {
/* 471 */     return getLong(size() - 1 - i);
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
/*     */   public boolean rem(long k) {
/* 483 */     int index = indexOf(k);
/* 484 */     if (index == -1) return false; 
/* 485 */     removeLong(index);
/* 486 */     return true;
/*     */   }
/*     */ 
/*     */   
/*     */   public long[] toLongArray() {
/* 491 */     int size = size();
/* 492 */     if (size == 0) return LongArrays.EMPTY_ARRAY; 
/* 493 */     long[] ret = new long[size];
/* 494 */     getElements(0, ret, 0, size);
/* 495 */     return ret;
/*     */   }
/*     */ 
/*     */   
/*     */   public long[] toArray(long[] a) {
/* 500 */     int size = size();
/* 501 */     if (a.length < size) {
/* 502 */       a = Arrays.copyOf(a, size);
/*     */     }
/* 504 */     getElements(0, a, 0, size);
/* 505 */     return a;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean addAll(int index, LongCollection c) {
/* 510 */     ensureIndex(index);
/* 511 */     LongIterator i = c.iterator();
/* 512 */     boolean retVal = i.hasNext();
/* 513 */     for (; i.hasNext(); add(index++, i.nextLong()));
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
/*     */   public boolean addAll(LongCollection c) {
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
/*     */   public final void replaceAll(LongUnaryOperator operator) {
/* 536 */     replaceAll(operator);
/*     */   }
/*     */ 
/*     */   
/*     */   public String toString() {
/* 541 */     StringBuilder s = new StringBuilder();
/* 542 */     LongIterator i = iterator();
/* 543 */     int n = size();
/*     */     
/* 545 */     boolean first = true;
/* 546 */     s.append("[");
/* 547 */     while (n-- != 0) {
/* 548 */       if (first) { first = false; }
/* 549 */       else { s.append(", "); }
/* 550 */        long k = i.nextLong();
/* 551 */       s.append(String.valueOf(k));
/*     */     } 
/* 553 */     s.append("]");
/* 554 */     return s.toString();
/*     */   }
/*     */ 
/*     */   
/*     */   public static class LongSubList
/*     */     extends AbstractLongList
/*     */     implements Serializable
/*     */   {
/*     */     private static final long serialVersionUID = -7046029254386353129L;
/*     */     protected final LongList l;
/*     */     protected final int from;
/*     */     protected int to;
/*     */     
/*     */     public LongSubList(LongList l, int from, int to) {
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
/*     */     public boolean add(long k) {
/* 582 */       this.l.add(this.to, k);
/* 583 */       this.to++;
/* 584 */       assert assertRange();
/* 585 */       return true;
/*     */     }
/*     */ 
/*     */     
/*     */     public void add(int index, long k) {
/* 590 */       ensureIndex(index);
/* 591 */       this.l.add(this.from + index, k);
/* 592 */       this.to++;
/* 593 */       assert assertRange();
/*     */     }
/*     */ 
/*     */     
/*     */     public boolean addAll(int index, Collection<? extends Long> c) {
/* 598 */       ensureIndex(index);
/* 599 */       this.to += c.size();
/* 600 */       return this.l.addAll(this.from + index, c);
/*     */     }
/*     */ 
/*     */     
/*     */     public long getLong(int index) {
/* 605 */       ensureRestrictedIndex(index);
/* 606 */       return this.l.getLong(this.from + index);
/*     */     }
/*     */ 
/*     */     
/*     */     public long removeLong(int index) {
/* 611 */       ensureRestrictedIndex(index);
/* 612 */       this.to--;
/* 613 */       return this.l.removeLong(this.from + index);
/*     */     }
/*     */ 
/*     */     
/*     */     public long set(int index, long k) {
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
/*     */     public void getElements(int from, long[] a, int offset, int length) {
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
/*     */     public void addElements(int index, long[] a, int offset, int length) {
/* 645 */       ensureIndex(index);
/* 646 */       this.l.addElements(this.from + index, a, offset, length);
/* 647 */       this.to += length;
/* 648 */       assert assertRange();
/*     */     }
/*     */ 
/*     */     
/*     */     public void setElements(int index, long[] a, int offset, int length) {
/* 653 */       ensureIndex(index);
/* 654 */       this.l.setElements(this.from + index, a, offset, length);
/* 655 */       assert assertRange();
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     private final class RandomAccessIter
/*     */       extends LongIterators.AbstractIndexBasedListIterator
/*     */     {
/*     */       RandomAccessIter(int pos) {
/* 664 */         super(0, pos);
/*     */       }
/*     */ 
/*     */       
/*     */       protected final long get(int i) {
/* 669 */         return AbstractLongList.LongSubList.this.l.getLong(AbstractLongList.LongSubList.this.from + i);
/*     */       }
/*     */ 
/*     */ 
/*     */       
/*     */       protected final void add(int i, long k) {
/* 675 */         AbstractLongList.LongSubList.this.add(i, k);
/*     */       }
/*     */ 
/*     */       
/*     */       protected final void set(int i, long k) {
/* 680 */         AbstractLongList.LongSubList.this.set(i, k);
/*     */       }
/*     */ 
/*     */       
/*     */       protected final void remove(int i) {
/* 685 */         AbstractLongList.LongSubList.this.removeLong(i);
/*     */       }
/*     */ 
/*     */       
/*     */       protected final int getMaxPos() {
/* 690 */         return AbstractLongList.LongSubList.this.to - AbstractLongList.LongSubList.this.from;
/*     */       }
/*     */ 
/*     */       
/*     */       public void add(long k) {
/* 695 */         super.add(k);
/* 696 */         assert AbstractLongList.LongSubList.this.assertRange();
/*     */       }
/*     */ 
/*     */       
/*     */       public void remove() {
/* 701 */         super.remove();
/* 702 */         assert AbstractLongList.LongSubList.this.assertRange();
/*     */       }
/*     */     }
/*     */     
/*     */     private class ParentWrappingIter implements LongListIterator {
/*     */       private LongListIterator parent;
/*     */       
/*     */       ParentWrappingIter(LongListIterator parent) {
/* 710 */         this.parent = parent;
/*     */       }
/*     */ 
/*     */       
/*     */       public int nextIndex() {
/* 715 */         return this.parent.nextIndex() - AbstractLongList.LongSubList.this.from;
/*     */       }
/*     */ 
/*     */       
/*     */       public int previousIndex() {
/* 720 */         return this.parent.previousIndex() - AbstractLongList.LongSubList.this.from;
/*     */       }
/*     */ 
/*     */       
/*     */       public boolean hasNext() {
/* 725 */         return (this.parent.nextIndex() < AbstractLongList.LongSubList.this.to);
/*     */       }
/*     */ 
/*     */       
/*     */       public boolean hasPrevious() {
/* 730 */         return (this.parent.previousIndex() >= AbstractLongList.LongSubList.this.from);
/*     */       }
/*     */ 
/*     */       
/*     */       public long nextLong() {
/* 735 */         if (!hasNext()) throw new NoSuchElementException(); 
/* 736 */         return this.parent.nextLong();
/*     */       }
/*     */ 
/*     */       
/*     */       public long previousLong() {
/* 741 */         if (!hasPrevious()) throw new NoSuchElementException(); 
/* 742 */         return this.parent.previousLong();
/*     */       }
/*     */ 
/*     */       
/*     */       public void add(long k) {
/* 747 */         this.parent.add(k);
/*     */       }
/*     */ 
/*     */       
/*     */       public void set(long k) {
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
/* 768 */         if (parentNewPos < AbstractLongList.LongSubList.this.from - 1) parentNewPos = AbstractLongList.LongSubList.this.from - 1; 
/* 769 */         int toSkip = parentNewPos - currentPos;
/* 770 */         return this.parent.back(toSkip);
/*     */       }
/*     */ 
/*     */       
/*     */       public int skip(int n) {
/* 775 */         if (n < 0) throw new IllegalArgumentException("Argument must be nonnegative: " + n); 
/* 776 */         int currentPos = this.parent.nextIndex();
/* 777 */         int parentNewPos = currentPos + n;
/* 778 */         if (parentNewPos > AbstractLongList.LongSubList.this.to) parentNewPos = AbstractLongList.LongSubList.this.to; 
/* 779 */         int toSkip = parentNewPos - currentPos;
/* 780 */         return this.parent.skip(toSkip);
/*     */       }
/*     */     }
/*     */ 
/*     */     
/*     */     public LongListIterator listIterator(int index) {
/* 786 */       ensureIndex(index);
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 791 */       return (this.l instanceof RandomAccess) ? new RandomAccessIter(index) : new ParentWrappingIter(this.l.listIterator(index + this.from));
/*     */     }
/*     */ 
/*     */     
/*     */     public LongSpliterator spliterator() {
/* 796 */       return (this.l instanceof RandomAccess) ? new AbstractLongList.IndexBasedSpliterator(this.l, this.from, this.to) : super.spliterator();
/*     */     }
/*     */ 
/*     */     
/*     */     public LongList subList(int from, int to) {
/* 801 */       ensureIndex(from);
/* 802 */       ensureIndex(to);
/* 803 */       if (from > to) throw new IllegalArgumentException("Start index (" + from + ") is greater than end index (" + to + ")");
/*     */ 
/*     */       
/* 806 */       return new LongSubList(this, from, to);
/*     */     }
/*     */ 
/*     */     
/*     */     public boolean rem(long k) {
/* 811 */       int index = indexOf(k);
/* 812 */       if (index == -1) return false; 
/* 813 */       this.to--;
/* 814 */       this.l.removeLong(this.from + index);
/* 815 */       assert assertRange();
/* 816 */       return true;
/*     */     }
/*     */ 
/*     */     
/*     */     public boolean addAll(int index, LongCollection c) {
/* 821 */       ensureIndex(index);
/* 822 */       return super.addAll(index, c);
/*     */     }
/*     */ 
/*     */     
/*     */     public boolean addAll(int index, LongList l) {
/* 827 */       ensureIndex(index);
/* 828 */       return super.addAll(index, l);
/*     */     }
/*     */   }
/*     */   
/*     */   public static class LongRandomAccessSubList extends LongSubList implements RandomAccess {
/*     */     private static final long serialVersionUID = -107070782945191929L;
/*     */     
/*     */     public LongRandomAccessSubList(LongList l, int from, int to) {
/* 836 */       super(l, from, to);
/*     */     }
/*     */ 
/*     */     
/*     */     public LongList subList(int from, int to) {
/* 841 */       ensureIndex(from);
/* 842 */       ensureIndex(to);
/* 843 */       if (from > to) throw new IllegalArgumentException("Start index (" + from + ") is greater than end index (" + to + ")");
/*     */ 
/*     */       
/* 846 */       return new LongRandomAccessSubList(this, from, to);
/*     */     }
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\i\\unimi\dsi\fastutil\longs\AbstractLongList.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */