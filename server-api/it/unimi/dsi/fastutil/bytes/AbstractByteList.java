/*     */ package it.unimi.dsi.fastutil.bytes;
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
/*     */ public abstract class AbstractByteList
/*     */   extends AbstractByteCollection
/*     */   implements ByteList, ByteStack
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
/*     */   public void add(int index, byte k) {
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
/*     */   public boolean add(byte k) {
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
/*     */   public byte removeByte(int i) {
/*  98 */     throw new UnsupportedOperationException();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public byte set(int index, byte k) {
/* 108 */     throw new UnsupportedOperationException();
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean addAll(int index, Collection<? extends Byte> c) {
/* 114 */     if (c instanceof ByteCollection) {
/* 115 */       return addAll(index, (ByteCollection)c);
/*     */     }
/* 117 */     ensureIndex(index);
/* 118 */     Iterator<? extends Byte> i = c.iterator();
/* 119 */     boolean retVal = i.hasNext();
/* 120 */     for (; i.hasNext(); add(index++, ((Byte)i.next()).byteValue()));
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
/*     */   public boolean addAll(Collection<? extends Byte> c) {
/* 132 */     return addAll(size(), c);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ByteListIterator iterator() {
/* 142 */     return listIterator();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ByteListIterator listIterator() {
/* 152 */     return listIterator(0);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ByteListIterator listIterator(int index) {
/* 162 */     ensureIndex(index);
/* 163 */     return new ByteIterators.AbstractIndexBasedListIterator(0, index)
/*     */       {
/*     */         protected final byte get(int i) {
/* 166 */           return AbstractByteList.this.getByte(i);
/*     */         }
/*     */ 
/*     */         
/*     */         protected final void add(int i, byte k) {
/* 171 */           AbstractByteList.this.add(i, k);
/*     */         }
/*     */ 
/*     */         
/*     */         protected final void set(int i, byte k) {
/* 176 */           AbstractByteList.this.set(i, k);
/*     */         }
/*     */ 
/*     */         
/*     */         protected final void remove(int i) {
/* 181 */           AbstractByteList.this.removeByte(i);
/*     */         }
/*     */ 
/*     */         
/*     */         protected final int getMaxPos() {
/* 186 */           return AbstractByteList.this.size();
/*     */         }
/*     */       };
/*     */   }
/*     */   
/*     */   static final class IndexBasedSpliterator extends ByteSpliterators.LateBindingSizeIndexBasedSpliterator {
/*     */     final ByteList l;
/*     */     
/*     */     IndexBasedSpliterator(ByteList l, int pos) {
/* 195 */       super(pos);
/* 196 */       this.l = l;
/*     */     }
/*     */     
/*     */     IndexBasedSpliterator(ByteList l, int pos, int maxPos) {
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
/*     */     protected final byte get(int i) {
/* 211 */       return this.l.getByte(i);
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
/*     */   public boolean contains(byte k) {
/* 228 */     return (indexOf(k) >= 0);
/*     */   }
/*     */ 
/*     */   
/*     */   public int indexOf(byte k) {
/* 233 */     ByteListIterator i = listIterator();
/*     */     
/* 235 */     while (i.hasNext()) {
/* 236 */       byte e = i.nextByte();
/* 237 */       if (k == e) return i.previousIndex(); 
/*     */     } 
/* 239 */     return -1;
/*     */   }
/*     */ 
/*     */   
/*     */   public int lastIndexOf(byte k) {
/* 244 */     ByteListIterator i = listIterator(size());
/*     */     
/* 246 */     while (i.hasPrevious()) {
/* 247 */       byte e = i.previousByte();
/* 248 */       if (k == e) return i.nextIndex(); 
/*     */     } 
/* 250 */     return -1;
/*     */   }
/*     */ 
/*     */   
/*     */   public void size(int size) {
/* 255 */     int i = size();
/* 256 */     if (size > i) { for (; i++ < size; add((byte)0)); }
/* 257 */     else { for (; i-- != size; removeByte(i)); }
/*     */   
/*     */   }
/*     */   
/*     */   public ByteList subList(int from, int to) {
/* 262 */     ensureIndex(from);
/* 263 */     ensureIndex(to);
/* 264 */     if (from > to) throw new IndexOutOfBoundsException("Start index (" + from + ") is greater than end index (" + to + ")"); 
/* 265 */     return (this instanceof RandomAccess) ? new ByteRandomAccessSubList(this, from, to) : new ByteSubList(this, from, to);
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
/*     */   public void forEach(ByteConsumer action) {
/* 277 */     if (this instanceof RandomAccess) {
/* 278 */       for (int i = 0, max = size(); i < max; i++) {
/* 279 */         action.accept(getByte(i));
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
/* 298 */     ByteListIterator i = listIterator(from);
/* 299 */     int n = to - from;
/* 300 */     if (n < 0) throw new IllegalArgumentException("Start index (" + from + ") is greater than end index (" + to + ")"); 
/* 301 */     while (n-- != 0) {
/* 302 */       i.nextByte();
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
/*     */   public void addElements(int index, byte[] a, int offset, int length) {
/* 316 */     ensureIndex(index);
/* 317 */     ByteArrays.ensureOffsetLength(a, offset, length);
/* 318 */     if (this instanceof RandomAccess) {
/* 319 */       for (; length-- != 0; add(index++, a[offset++]));
/*     */     } else {
/* 321 */       ByteListIterator iter = listIterator(index);
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
/*     */   public void addElements(int index, byte[] a) {
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
/*     */   public void getElements(int from, byte[] a, int offset, int length) {
/* 345 */     ensureIndex(from);
/* 346 */     ByteArrays.ensureOffsetLength(a, offset, length);
/* 347 */     if (from + length > size()) throw new IndexOutOfBoundsException("End index (" + (from + length) + ") is greater than list size (" + size() + ")"); 
/* 348 */     if (this instanceof RandomAccess) {
/* 349 */       int current = from;
/* 350 */       for (; length-- != 0; a[offset++] = getByte(current++));
/*     */     } else {
/* 352 */       ByteListIterator i = listIterator(from);
/* 353 */       for (; length-- != 0; a[offset++] = i.nextByte());
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public void setElements(int index, byte[] a, int offset, int length) {
/* 359 */     ensureIndex(index);
/* 360 */     ByteArrays.ensureOffsetLength(a, offset, length);
/* 361 */     if (index + length > size()) throw new IndexOutOfBoundsException("End index (" + (index + length) + ") is greater than list size (" + size() + ")"); 
/* 362 */     if (this instanceof RandomAccess) {
/* 363 */       for (int i = 0; i < length; i++) {
/* 364 */         set(i + index, a[i + offset]);
/*     */       }
/*     */     } else {
/* 367 */       ByteListIterator iter = listIterator(index);
/* 368 */       int i = 0;
/* 369 */       while (i < length) {
/* 370 */         iter.nextByte();
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
/* 393 */     ByteIterator i = iterator();
/* 394 */     int h = 1, s = size();
/* 395 */     while (s-- != 0) {
/* 396 */       byte k = i.nextByte();
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
/* 409 */     if (l instanceof ByteList) {
/* 410 */       ByteListIterator byteListIterator1 = listIterator(), byteListIterator2 = ((ByteList)l).listIterator();
/* 411 */       while (s-- != 0) { if (byteListIterator1.nextByte() != byteListIterator2.nextByte()) return false;  }
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
/*     */   public int compareTo(List<? extends Byte> l) {
/* 432 */     if (l == this) return 0; 
/* 433 */     if (l instanceof ByteList) {
/* 434 */       ByteListIterator byteListIterator1 = listIterator(), byteListIterator2 = ((ByteList)l).listIterator();
/*     */ 
/*     */       
/* 437 */       while (byteListIterator1.hasNext() && byteListIterator2.hasNext()) {
/* 438 */         byte e1 = byteListIterator1.nextByte();
/* 439 */         byte e2 = byteListIterator2.nextByte(); int r;
/* 440 */         if ((r = Byte.compare(e1, e2)) != 0) return r; 
/*     */       } 
/* 442 */       return byteListIterator2.hasNext() ? -1 : (byteListIterator1.hasNext() ? 1 : 0);
/*     */     } 
/* 444 */     ListIterator<? extends Byte> i1 = listIterator(), i2 = l.listIterator();
/*     */     
/* 446 */     while (i1.hasNext() && i2.hasNext()) {
/* 447 */       int r; if ((r = ((Comparable)i1.next()).compareTo(i2.next())) != 0) return r; 
/*     */     } 
/* 449 */     return i2.hasNext() ? -1 : (i1.hasNext() ? 1 : 0);
/*     */   }
/*     */ 
/*     */   
/*     */   public void push(byte o) {
/* 454 */     add(o);
/*     */   }
/*     */ 
/*     */   
/*     */   public byte popByte() {
/* 459 */     if (isEmpty()) throw new NoSuchElementException(); 
/* 460 */     return removeByte(size() - 1);
/*     */   }
/*     */ 
/*     */   
/*     */   public byte topByte() {
/* 465 */     if (isEmpty()) throw new NoSuchElementException(); 
/* 466 */     return getByte(size() - 1);
/*     */   }
/*     */ 
/*     */   
/*     */   public byte peekByte(int i) {
/* 471 */     return getByte(size() - 1 - i);
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
/*     */   public boolean rem(byte k) {
/* 483 */     int index = indexOf(k);
/* 484 */     if (index == -1) return false; 
/* 485 */     removeByte(index);
/* 486 */     return true;
/*     */   }
/*     */ 
/*     */   
/*     */   public byte[] toByteArray() {
/* 491 */     int size = size();
/* 492 */     if (size == 0) return ByteArrays.EMPTY_ARRAY; 
/* 493 */     byte[] ret = new byte[size];
/* 494 */     getElements(0, ret, 0, size);
/* 495 */     return ret;
/*     */   }
/*     */ 
/*     */   
/*     */   public byte[] toArray(byte[] a) {
/* 500 */     int size = size();
/* 501 */     if (a.length < size) {
/* 502 */       a = Arrays.copyOf(a, size);
/*     */     }
/* 504 */     getElements(0, a, 0, size);
/* 505 */     return a;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean addAll(int index, ByteCollection c) {
/* 510 */     ensureIndex(index);
/* 511 */     ByteIterator i = c.iterator();
/* 512 */     boolean retVal = i.hasNext();
/* 513 */     for (; i.hasNext(); add(index++, i.nextByte()));
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
/*     */   public boolean addAll(ByteCollection c) {
/* 525 */     return addAll(size(), c);
/*     */   }
/*     */ 
/*     */   
/*     */   public String toString() {
/* 530 */     StringBuilder s = new StringBuilder();
/* 531 */     ByteIterator i = iterator();
/* 532 */     int n = size();
/*     */     
/* 534 */     boolean first = true;
/* 535 */     s.append("[");
/* 536 */     while (n-- != 0) {
/* 537 */       if (first) { first = false; }
/* 538 */       else { s.append(", "); }
/* 539 */        byte k = i.nextByte();
/* 540 */       s.append(String.valueOf(k));
/*     */     } 
/* 542 */     s.append("]");
/* 543 */     return s.toString();
/*     */   }
/*     */ 
/*     */   
/*     */   public static class ByteSubList
/*     */     extends AbstractByteList
/*     */     implements Serializable
/*     */   {
/*     */     private static final long serialVersionUID = -7046029254386353129L;
/*     */     protected final ByteList l;
/*     */     protected final int from;
/*     */     protected int to;
/*     */     
/*     */     public ByteSubList(ByteList l, int from, int to) {
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
/*     */     public boolean add(byte k) {
/* 571 */       this.l.add(this.to, k);
/* 572 */       this.to++;
/* 573 */       assert assertRange();
/* 574 */       return true;
/*     */     }
/*     */ 
/*     */     
/*     */     public void add(int index, byte k) {
/* 579 */       ensureIndex(index);
/* 580 */       this.l.add(this.from + index, k);
/* 581 */       this.to++;
/* 582 */       assert assertRange();
/*     */     }
/*     */ 
/*     */     
/*     */     public boolean addAll(int index, Collection<? extends Byte> c) {
/* 587 */       ensureIndex(index);
/* 588 */       this.to += c.size();
/* 589 */       return this.l.addAll(this.from + index, c);
/*     */     }
/*     */ 
/*     */     
/*     */     public byte getByte(int index) {
/* 594 */       ensureRestrictedIndex(index);
/* 595 */       return this.l.getByte(this.from + index);
/*     */     }
/*     */ 
/*     */     
/*     */     public byte removeByte(int index) {
/* 600 */       ensureRestrictedIndex(index);
/* 601 */       this.to--;
/* 602 */       return this.l.removeByte(this.from + index);
/*     */     }
/*     */ 
/*     */     
/*     */     public byte set(int index, byte k) {
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
/*     */     public void getElements(int from, byte[] a, int offset, int length) {
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
/*     */     public void addElements(int index, byte[] a, int offset, int length) {
/* 634 */       ensureIndex(index);
/* 635 */       this.l.addElements(this.from + index, a, offset, length);
/* 636 */       this.to += length;
/* 637 */       assert assertRange();
/*     */     }
/*     */ 
/*     */     
/*     */     public void setElements(int index, byte[] a, int offset, int length) {
/* 642 */       ensureIndex(index);
/* 643 */       this.l.setElements(this.from + index, a, offset, length);
/* 644 */       assert assertRange();
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     private final class RandomAccessIter
/*     */       extends ByteIterators.AbstractIndexBasedListIterator
/*     */     {
/*     */       RandomAccessIter(int pos) {
/* 653 */         super(0, pos);
/*     */       }
/*     */ 
/*     */       
/*     */       protected final byte get(int i) {
/* 658 */         return AbstractByteList.ByteSubList.this.l.getByte(AbstractByteList.ByteSubList.this.from + i);
/*     */       }
/*     */ 
/*     */ 
/*     */       
/*     */       protected final void add(int i, byte k) {
/* 664 */         AbstractByteList.ByteSubList.this.add(i, k);
/*     */       }
/*     */ 
/*     */       
/*     */       protected final void set(int i, byte k) {
/* 669 */         AbstractByteList.ByteSubList.this.set(i, k);
/*     */       }
/*     */ 
/*     */       
/*     */       protected final void remove(int i) {
/* 674 */         AbstractByteList.ByteSubList.this.removeByte(i);
/*     */       }
/*     */ 
/*     */       
/*     */       protected final int getMaxPos() {
/* 679 */         return AbstractByteList.ByteSubList.this.to - AbstractByteList.ByteSubList.this.from;
/*     */       }
/*     */ 
/*     */       
/*     */       public void add(byte k) {
/* 684 */         super.add(k);
/* 685 */         assert AbstractByteList.ByteSubList.this.assertRange();
/*     */       }
/*     */ 
/*     */       
/*     */       public void remove() {
/* 690 */         super.remove();
/* 691 */         assert AbstractByteList.ByteSubList.this.assertRange();
/*     */       }
/*     */     }
/*     */     
/*     */     private class ParentWrappingIter implements ByteListIterator {
/*     */       private ByteListIterator parent;
/*     */       
/*     */       ParentWrappingIter(ByteListIterator parent) {
/* 699 */         this.parent = parent;
/*     */       }
/*     */ 
/*     */       
/*     */       public int nextIndex() {
/* 704 */         return this.parent.nextIndex() - AbstractByteList.ByteSubList.this.from;
/*     */       }
/*     */ 
/*     */       
/*     */       public int previousIndex() {
/* 709 */         return this.parent.previousIndex() - AbstractByteList.ByteSubList.this.from;
/*     */       }
/*     */ 
/*     */       
/*     */       public boolean hasNext() {
/* 714 */         return (this.parent.nextIndex() < AbstractByteList.ByteSubList.this.to);
/*     */       }
/*     */ 
/*     */       
/*     */       public boolean hasPrevious() {
/* 719 */         return (this.parent.previousIndex() >= AbstractByteList.ByteSubList.this.from);
/*     */       }
/*     */ 
/*     */       
/*     */       public byte nextByte() {
/* 724 */         if (!hasNext()) throw new NoSuchElementException(); 
/* 725 */         return this.parent.nextByte();
/*     */       }
/*     */ 
/*     */       
/*     */       public byte previousByte() {
/* 730 */         if (!hasPrevious()) throw new NoSuchElementException(); 
/* 731 */         return this.parent.previousByte();
/*     */       }
/*     */ 
/*     */       
/*     */       public void add(byte k) {
/* 736 */         this.parent.add(k);
/*     */       }
/*     */ 
/*     */       
/*     */       public void set(byte k) {
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
/* 757 */         if (parentNewPos < AbstractByteList.ByteSubList.this.from - 1) parentNewPos = AbstractByteList.ByteSubList.this.from - 1; 
/* 758 */         int toSkip = parentNewPos - currentPos;
/* 759 */         return this.parent.back(toSkip);
/*     */       }
/*     */ 
/*     */       
/*     */       public int skip(int n) {
/* 764 */         if (n < 0) throw new IllegalArgumentException("Argument must be nonnegative: " + n); 
/* 765 */         int currentPos = this.parent.nextIndex();
/* 766 */         int parentNewPos = currentPos + n;
/* 767 */         if (parentNewPos > AbstractByteList.ByteSubList.this.to) parentNewPos = AbstractByteList.ByteSubList.this.to; 
/* 768 */         int toSkip = parentNewPos - currentPos;
/* 769 */         return this.parent.skip(toSkip);
/*     */       }
/*     */     }
/*     */ 
/*     */     
/*     */     public ByteListIterator listIterator(int index) {
/* 775 */       ensureIndex(index);
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 780 */       return (this.l instanceof RandomAccess) ? new RandomAccessIter(index) : new ParentWrappingIter(this.l.listIterator(index + this.from));
/*     */     }
/*     */ 
/*     */     
/*     */     public ByteSpliterator spliterator() {
/* 785 */       return (this.l instanceof RandomAccess) ? new AbstractByteList.IndexBasedSpliterator(this.l, this.from, this.to) : super.spliterator();
/*     */     }
/*     */ 
/*     */     
/*     */     public ByteList subList(int from, int to) {
/* 790 */       ensureIndex(from);
/* 791 */       ensureIndex(to);
/* 792 */       if (from > to) throw new IllegalArgumentException("Start index (" + from + ") is greater than end index (" + to + ")");
/*     */ 
/*     */       
/* 795 */       return new ByteSubList(this, from, to);
/*     */     }
/*     */ 
/*     */     
/*     */     public boolean rem(byte k) {
/* 800 */       int index = indexOf(k);
/* 801 */       if (index == -1) return false; 
/* 802 */       this.to--;
/* 803 */       this.l.removeByte(this.from + index);
/* 804 */       assert assertRange();
/* 805 */       return true;
/*     */     }
/*     */ 
/*     */     
/*     */     public boolean addAll(int index, ByteCollection c) {
/* 810 */       ensureIndex(index);
/* 811 */       return super.addAll(index, c);
/*     */     }
/*     */ 
/*     */     
/*     */     public boolean addAll(int index, ByteList l) {
/* 816 */       ensureIndex(index);
/* 817 */       return super.addAll(index, l);
/*     */     }
/*     */   }
/*     */   
/*     */   public static class ByteRandomAccessSubList extends ByteSubList implements RandomAccess {
/*     */     private static final long serialVersionUID = -107070782945191929L;
/*     */     
/*     */     public ByteRandomAccessSubList(ByteList l, int from, int to) {
/* 825 */       super(l, from, to);
/*     */     }
/*     */ 
/*     */     
/*     */     public ByteList subList(int from, int to) {
/* 830 */       ensureIndex(from);
/* 831 */       ensureIndex(to);
/* 832 */       if (from > to) throw new IllegalArgumentException("Start index (" + from + ") is greater than end index (" + to + ")");
/*     */ 
/*     */       
/* 835 */       return new ByteRandomAccessSubList(this, from, to);
/*     */     }
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\i\\unimi\dsi\fastutil\bytes\AbstractByteList.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */