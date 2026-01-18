/*     */ package it.unimi.dsi.fastutil.objects;
/*     */ 
/*     */ import it.unimi.dsi.fastutil.Stack;
/*     */ import java.io.Serializable;
/*     */ import java.util.Arrays;
/*     */ import java.util.Collection;
/*     */ import java.util.Iterator;
/*     */ import java.util.List;
/*     */ import java.util.ListIterator;
/*     */ import java.util.NoSuchElementException;
/*     */ import java.util.RandomAccess;
/*     */ import java.util.Spliterator;
/*     */ import java.util.function.Consumer;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public abstract class AbstractReferenceList<K>
/*     */   extends AbstractReferenceCollection<K>
/*     */   implements ReferenceList<K>, Stack<K>
/*     */ {
/*     */   protected void ensureIndex(int index) {
/*  54 */     if (index < 0) throw new IndexOutOfBoundsException("Index (" + index + ") is negative"); 
/*  55 */     if (index > size()) throw new IndexOutOfBoundsException("Index (" + index + ") is greater than list size (" + size() + ")");
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
/*  67 */     if (index < 0) throw new IndexOutOfBoundsException("Index (" + index + ") is negative"); 
/*  68 */     if (index >= size()) throw new IndexOutOfBoundsException("Index (" + index + ") is greater than or equal to list size (" + size() + ")");
/*     */   
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void add(int index, K k) {
/*  78 */     throw new UnsupportedOperationException();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean add(K k) {
/*  89 */     add(size(), k);
/*  90 */     return true;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public K remove(int i) {
/* 100 */     throw new UnsupportedOperationException();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public K set(int index, K k) {
/* 110 */     throw new UnsupportedOperationException();
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean addAll(int index, Collection<? extends K> c) {
/* 116 */     ensureIndex(index);
/* 117 */     Iterator<? extends K> i = c.iterator();
/* 118 */     boolean retVal = i.hasNext();
/* 119 */     for (; i.hasNext(); add(index++, i.next()));
/* 120 */     return retVal;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean addAll(Collection<? extends K> c) {
/* 131 */     return addAll(size(), c);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ObjectListIterator<K> iterator() {
/* 141 */     return listIterator();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ObjectListIterator<K> listIterator() {
/* 151 */     return listIterator(0);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ObjectListIterator<K> listIterator(int index) {
/* 161 */     ensureIndex(index);
/* 162 */     return new ObjectIterators.AbstractIndexBasedListIterator<K>(0, index)
/*     */       {
/*     */         protected final K get(int i) {
/* 165 */           return AbstractReferenceList.this.get(i);
/*     */         }
/*     */ 
/*     */         
/*     */         protected final void add(int i, K k) {
/* 170 */           AbstractReferenceList.this.add(i, k);
/*     */         }
/*     */ 
/*     */         
/*     */         protected final void set(int i, K k) {
/* 175 */           AbstractReferenceList.this.set(i, k);
/*     */         }
/*     */ 
/*     */         
/*     */         protected final void remove(int i) {
/* 180 */           AbstractReferenceList.this.remove(i);
/*     */         }
/*     */ 
/*     */         
/*     */         protected final int getMaxPos() {
/* 185 */           return AbstractReferenceList.this.size();
/*     */         }
/*     */       };
/*     */   }
/*     */   
/*     */   static final class IndexBasedSpliterator<K> extends ObjectSpliterators.LateBindingSizeIndexBasedSpliterator<K> {
/*     */     final ReferenceList<K> l;
/*     */     
/*     */     IndexBasedSpliterator(ReferenceList<K> l, int pos) {
/* 194 */       super(pos);
/* 195 */       this.l = l;
/*     */     }
/*     */     
/*     */     IndexBasedSpliterator(ReferenceList<K> l, int pos, int maxPos) {
/* 199 */       super(pos, maxPos);
/* 200 */       this.l = l;
/*     */     }
/*     */ 
/*     */     
/*     */     protected final int getMaxPosFromBackingStore() {
/* 205 */       return this.l.size();
/*     */     }
/*     */ 
/*     */     
/*     */     protected final K get(int i) {
/* 210 */       return this.l.get(i);
/*     */     }
/*     */ 
/*     */     
/*     */     protected final IndexBasedSpliterator<K> makeForSplit(int pos, int maxPos) {
/* 215 */       return new IndexBasedSpliterator(this.l, pos, maxPos);
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
/*     */   public boolean contains(Object k) {
/* 227 */     return (indexOf(k) >= 0);
/*     */   }
/*     */ 
/*     */   
/*     */   public int indexOf(Object k) {
/* 232 */     ObjectListIterator<K> i = listIterator();
/*     */     
/* 234 */     while (i.hasNext()) {
/* 235 */       K e = i.next();
/* 236 */       if (k == e) return i.previousIndex(); 
/*     */     } 
/* 238 */     return -1;
/*     */   }
/*     */ 
/*     */   
/*     */   public int lastIndexOf(Object k) {
/* 243 */     ObjectListIterator<K> i = listIterator(size());
/*     */     
/* 245 */     while (i.hasPrevious()) {
/* 246 */       K e = i.previous();
/* 247 */       if (k == e) return i.nextIndex(); 
/*     */     } 
/* 249 */     return -1;
/*     */   }
/*     */ 
/*     */   
/*     */   public void size(int size) {
/* 254 */     int i = size();
/* 255 */     if (size > i) { for (; i++ < size; add((K)null)); }
/* 256 */     else { for (; i-- != size; remove(i)); }
/*     */   
/*     */   }
/*     */   
/*     */   public ReferenceList<K> subList(int from, int to) {
/* 261 */     ensureIndex(from);
/* 262 */     ensureIndex(to);
/* 263 */     if (from > to) throw new IndexOutOfBoundsException("Start index (" + from + ") is greater than end index (" + to + ")"); 
/* 264 */     return (this instanceof RandomAccess) ? new ReferenceRandomAccessSubList<>(this, from, to) : new ReferenceSubList<>(this, from, to);
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
/*     */   public void forEach(Consumer<? super K> action) {
/* 276 */     if (this instanceof RandomAccess) {
/* 277 */       for (int i = 0, max = size(); i < max; i++) {
/* 278 */         action.accept(get(i));
/*     */       }
/*     */     } else {
/* 281 */       super.forEach(action);
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
/* 294 */     ensureIndex(to);
/*     */ 
/*     */     
/* 297 */     ObjectListIterator<K> i = listIterator(from);
/* 298 */     int n = to - from;
/* 299 */     if (n < 0) throw new IllegalArgumentException("Start index (" + from + ") is greater than end index (" + to + ")"); 
/* 300 */     while (n-- != 0) {
/* 301 */       i.next();
/* 302 */       i.remove();
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
/*     */   public void addElements(int index, K[] a, int offset, int length) {
/* 315 */     ensureIndex(index);
/* 316 */     ObjectArrays.ensureOffsetLength(a, offset, length);
/* 317 */     if (this instanceof RandomAccess) {
/* 318 */       for (; length-- != 0; add(index++, a[offset++]));
/*     */     } else {
/* 320 */       ObjectListIterator<K> iter = listIterator(index);
/* 321 */       for (; length-- != 0; iter.add(a[offset++]));
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void addElements(int index, K[] a) {
/* 332 */     addElements(index, a, 0, a.length);
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
/*     */   public void getElements(int from, Object[] a, int offset, int length) {
/* 344 */     ensureIndex(from);
/* 345 */     ObjectArrays.ensureOffsetLength(a, offset, length);
/* 346 */     if (from + length > size()) throw new IndexOutOfBoundsException("End index (" + (from + length) + ") is greater than list size (" + size() + ")"); 
/* 347 */     if (this instanceof RandomAccess) {
/* 348 */       int current = from;
/* 349 */       for (; length-- != 0; a[offset++] = get(current++));
/*     */     } else {
/* 351 */       ObjectListIterator<K> i = listIterator(from);
/* 352 */       for (; length-- != 0; a[offset++] = i.next());
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public void setElements(int index, K[] a, int offset, int length) {
/* 358 */     ensureIndex(index);
/* 359 */     ObjectArrays.ensureOffsetLength(a, offset, length);
/* 360 */     if (index + length > size()) throw new IndexOutOfBoundsException("End index (" + (index + length) + ") is greater than list size (" + size() + ")"); 
/* 361 */     if (this instanceof RandomAccess) {
/* 362 */       for (int i = 0; i < length; i++) {
/* 363 */         set(i + index, a[i + offset]);
/*     */       }
/*     */     } else {
/* 366 */       ObjectListIterator<K> iter = listIterator(index);
/* 367 */       int i = 0;
/* 368 */       while (i < length) {
/* 369 */         iter.next();
/* 370 */         iter.set(a[offset + i++]);
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
/* 382 */     removeElements(0, size());
/*     */   }
/*     */ 
/*     */   
/*     */   public Object[] toArray() {
/* 387 */     int size = size();
/*     */     
/* 389 */     if (size == 0) return ObjectArrays.EMPTY_ARRAY; 
/* 390 */     Object[] ret = new Object[size];
/* 391 */     getElements(0, ret, 0, size);
/* 392 */     return ret;
/*     */   }
/*     */ 
/*     */   
/*     */   public <T> T[] toArray(T[] a) {
/* 397 */     int size = size();
/* 398 */     if (a.length < size) a = Arrays.copyOf(a, size); 
/* 399 */     getElements(0, (Object[])a, 0, size);
/* 400 */     if (a.length > size) a[size] = null; 
/* 401 */     return a;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int hashCode() {
/* 411 */     ObjectIterator<K> i = iterator();
/* 412 */     int h = 1, s = size();
/* 413 */     while (s-- != 0) {
/* 414 */       K k = i.next();
/* 415 */       h = 31 * h + System.identityHashCode(k);
/*     */     } 
/* 417 */     return h;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean equals(Object o) {
/* 422 */     if (o == this) return true; 
/* 423 */     if (!(o instanceof List)) return false; 
/* 424 */     List<?> l = (List)o;
/* 425 */     int s = size();
/* 426 */     if (s != l.size()) return false; 
/* 427 */     ListIterator<?> i1 = listIterator(), i2 = l.listIterator();
/* 428 */     while (s-- != 0) { if (i1.next() != i2.next()) return false;  }
/* 429 */      return true;
/*     */   }
/*     */ 
/*     */   
/*     */   public void push(K o) {
/* 434 */     add(o);
/*     */   }
/*     */ 
/*     */   
/*     */   public K pop() {
/* 439 */     if (isEmpty()) throw new NoSuchElementException(); 
/* 440 */     return remove(size() - 1);
/*     */   }
/*     */ 
/*     */   
/*     */   public K top() {
/* 445 */     if (isEmpty()) throw new NoSuchElementException(); 
/* 446 */     return get(size() - 1);
/*     */   }
/*     */ 
/*     */   
/*     */   public K peek(int i) {
/* 451 */     return get(size() - 1 - i);
/*     */   }
/*     */ 
/*     */   
/*     */   public String toString() {
/* 456 */     StringBuilder s = new StringBuilder();
/* 457 */     ObjectIterator<K> i = iterator();
/* 458 */     int n = size();
/*     */     
/* 460 */     boolean first = true;
/* 461 */     s.append("[");
/* 462 */     while (n-- != 0) {
/* 463 */       if (first) { first = false; }
/* 464 */       else { s.append(", "); }
/* 465 */        K k = i.next();
/* 466 */       if (this == k) { s.append("(this list)"); continue; }
/* 467 */        s.append(String.valueOf(k));
/*     */     } 
/* 469 */     s.append("]");
/* 470 */     return s.toString();
/*     */   }
/*     */ 
/*     */   
/*     */   public static class ReferenceSubList<K>
/*     */     extends AbstractReferenceList<K>
/*     */     implements Serializable
/*     */   {
/*     */     private static final long serialVersionUID = -7046029254386353129L;
/*     */     protected final ReferenceList<K> l;
/*     */     protected final int from;
/*     */     protected int to;
/*     */     
/*     */     public ReferenceSubList(ReferenceList<K> l, int from, int to) {
/* 484 */       this.l = l;
/* 485 */       this.from = from;
/* 486 */       this.to = to;
/*     */     }
/*     */     
/*     */     private boolean assertRange() {
/* 490 */       assert this.from <= this.l.size();
/* 491 */       assert this.to <= this.l.size();
/* 492 */       assert this.to >= this.from;
/* 493 */       return true;
/*     */     }
/*     */ 
/*     */     
/*     */     public boolean add(K k) {
/* 498 */       this.l.add(this.to, k);
/* 499 */       this.to++;
/* 500 */       assert assertRange();
/* 501 */       return true;
/*     */     }
/*     */ 
/*     */     
/*     */     public void add(int index, K k) {
/* 506 */       ensureIndex(index);
/* 507 */       this.l.add(this.from + index, k);
/* 508 */       this.to++;
/* 509 */       assert assertRange();
/*     */     }
/*     */ 
/*     */     
/*     */     public boolean addAll(int index, Collection<? extends K> c) {
/* 514 */       ensureIndex(index);
/* 515 */       this.to += c.size();
/* 516 */       return this.l.addAll(this.from + index, c);
/*     */     }
/*     */ 
/*     */     
/*     */     public K get(int index) {
/* 521 */       ensureRestrictedIndex(index);
/* 522 */       return this.l.get(this.from + index);
/*     */     }
/*     */ 
/*     */     
/*     */     public K remove(int index) {
/* 527 */       ensureRestrictedIndex(index);
/* 528 */       this.to--;
/* 529 */       return this.l.remove(this.from + index);
/*     */     }
/*     */ 
/*     */     
/*     */     public K set(int index, K k) {
/* 534 */       ensureRestrictedIndex(index);
/* 535 */       return this.l.set(this.from + index, k);
/*     */     }
/*     */ 
/*     */     
/*     */     public int size() {
/* 540 */       return this.to - this.from;
/*     */     }
/*     */ 
/*     */     
/*     */     public void getElements(int from, Object[] a, int offset, int length) {
/* 545 */       ensureIndex(from);
/* 546 */       if (from + length > size()) throw new IndexOutOfBoundsException("End index (" + from + length + ") is greater than list size (" + size() + ")"); 
/* 547 */       this.l.getElements(this.from + from, a, offset, length);
/*     */     }
/*     */ 
/*     */     
/*     */     public void removeElements(int from, int to) {
/* 552 */       ensureIndex(from);
/* 553 */       ensureIndex(to);
/* 554 */       this.l.removeElements(this.from + from, this.from + to);
/* 555 */       this.to -= to - from;
/* 556 */       assert assertRange();
/*     */     }
/*     */ 
/*     */     
/*     */     public void addElements(int index, K[] a, int offset, int length) {
/* 561 */       ensureIndex(index);
/* 562 */       this.l.addElements(this.from + index, a, offset, length);
/* 563 */       this.to += length;
/* 564 */       assert assertRange();
/*     */     }
/*     */ 
/*     */     
/*     */     public void setElements(int index, K[] a, int offset, int length) {
/* 569 */       ensureIndex(index);
/* 570 */       this.l.setElements(this.from + index, a, offset, length);
/* 571 */       assert assertRange();
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     private final class RandomAccessIter
/*     */       extends ObjectIterators.AbstractIndexBasedListIterator<K>
/*     */     {
/*     */       RandomAccessIter(int pos) {
/* 580 */         super(0, pos);
/*     */       }
/*     */ 
/*     */       
/*     */       protected final K get(int i) {
/* 585 */         return AbstractReferenceList.ReferenceSubList.this.l.get(AbstractReferenceList.ReferenceSubList.this.from + i);
/*     */       }
/*     */ 
/*     */ 
/*     */       
/*     */       protected final void add(int i, K k) {
/* 591 */         AbstractReferenceList.ReferenceSubList.this.add(i, k);
/*     */       }
/*     */ 
/*     */       
/*     */       protected final void set(int i, K k) {
/* 596 */         AbstractReferenceList.ReferenceSubList.this.set(i, k);
/*     */       }
/*     */ 
/*     */       
/*     */       protected final void remove(int i) {
/* 601 */         AbstractReferenceList.ReferenceSubList.this.remove(i);
/*     */       }
/*     */ 
/*     */       
/*     */       protected final int getMaxPos() {
/* 606 */         return AbstractReferenceList.ReferenceSubList.this.to - AbstractReferenceList.ReferenceSubList.this.from;
/*     */       }
/*     */ 
/*     */       
/*     */       public void add(K k) {
/* 611 */         super.add(k);
/* 612 */         assert AbstractReferenceList.ReferenceSubList.this.assertRange();
/*     */       }
/*     */ 
/*     */       
/*     */       public void remove() {
/* 617 */         super.remove();
/* 618 */         assert AbstractReferenceList.ReferenceSubList.this.assertRange();
/*     */       }
/*     */     }
/*     */     
/*     */     private class ParentWrappingIter implements ObjectListIterator<K> {
/*     */       private ObjectListIterator<K> parent;
/*     */       
/*     */       ParentWrappingIter(ObjectListIterator<K> parent) {
/* 626 */         this.parent = parent;
/*     */       }
/*     */ 
/*     */       
/*     */       public int nextIndex() {
/* 631 */         return this.parent.nextIndex() - AbstractReferenceList.ReferenceSubList.this.from;
/*     */       }
/*     */ 
/*     */       
/*     */       public int previousIndex() {
/* 636 */         return this.parent.previousIndex() - AbstractReferenceList.ReferenceSubList.this.from;
/*     */       }
/*     */ 
/*     */       
/*     */       public boolean hasNext() {
/* 641 */         return (this.parent.nextIndex() < AbstractReferenceList.ReferenceSubList.this.to);
/*     */       }
/*     */ 
/*     */       
/*     */       public boolean hasPrevious() {
/* 646 */         return (this.parent.previousIndex() >= AbstractReferenceList.ReferenceSubList.this.from);
/*     */       }
/*     */ 
/*     */       
/*     */       public K next() {
/* 651 */         if (!hasNext()) throw new NoSuchElementException(); 
/* 652 */         return this.parent.next();
/*     */       }
/*     */ 
/*     */       
/*     */       public K previous() {
/* 657 */         if (!hasPrevious()) throw new NoSuchElementException(); 
/* 658 */         return this.parent.previous();
/*     */       }
/*     */ 
/*     */       
/*     */       public void add(K k) {
/* 663 */         this.parent.add(k);
/*     */       }
/*     */ 
/*     */       
/*     */       public void set(K k) {
/* 668 */         this.parent.set(k);
/*     */       }
/*     */ 
/*     */       
/*     */       public void remove() {
/* 673 */         this.parent.remove();
/*     */       }
/*     */ 
/*     */       
/*     */       public int back(int n) {
/* 678 */         if (n < 0) throw new IllegalArgumentException("Argument must be nonnegative: " + n); 
/* 679 */         int currentPos = this.parent.previousIndex();
/* 680 */         int parentNewPos = currentPos - n;
/*     */ 
/*     */ 
/*     */         
/* 684 */         if (parentNewPos < AbstractReferenceList.ReferenceSubList.this.from - 1) parentNewPos = AbstractReferenceList.ReferenceSubList.this.from - 1; 
/* 685 */         int toSkip = parentNewPos - currentPos;
/* 686 */         return this.parent.back(toSkip);
/*     */       }
/*     */ 
/*     */       
/*     */       public int skip(int n) {
/* 691 */         if (n < 0) throw new IllegalArgumentException("Argument must be nonnegative: " + n); 
/* 692 */         int currentPos = this.parent.nextIndex();
/* 693 */         int parentNewPos = currentPos + n;
/* 694 */         if (parentNewPos > AbstractReferenceList.ReferenceSubList.this.to) parentNewPos = AbstractReferenceList.ReferenceSubList.this.to; 
/* 695 */         int toSkip = parentNewPos - currentPos;
/* 696 */         return this.parent.skip(toSkip);
/*     */       }
/*     */     }
/*     */ 
/*     */     
/*     */     public ObjectListIterator<K> listIterator(int index) {
/* 702 */       ensureIndex(index);
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 707 */       return (this.l instanceof RandomAccess) ? new RandomAccessIter(index) : new ParentWrappingIter(this.l.listIterator(index + this.from));
/*     */     }
/*     */ 
/*     */     
/*     */     public ObjectSpliterator<K> spliterator() {
/* 712 */       return (this.l instanceof RandomAccess) ? new AbstractReferenceList.IndexBasedSpliterator<>(this.l, this.from, this.to) : super.spliterator();
/*     */     }
/*     */ 
/*     */     
/*     */     public ReferenceList<K> subList(int from, int to) {
/* 717 */       ensureIndex(from);
/* 718 */       ensureIndex(to);
/* 719 */       if (from > to) throw new IllegalArgumentException("Start index (" + from + ") is greater than end index (" + to + ")");
/*     */ 
/*     */       
/* 722 */       return new ReferenceSubList(this, from, to);
/*     */     }
/*     */   }
/*     */   
/*     */   public static class ReferenceRandomAccessSubList<K> extends ReferenceSubList<K> implements RandomAccess {
/*     */     private static final long serialVersionUID = -107070782945191929L;
/*     */     
/*     */     public ReferenceRandomAccessSubList(ReferenceList<K> l, int from, int to) {
/* 730 */       super(l, from, to);
/*     */     }
/*     */ 
/*     */     
/*     */     public ReferenceList<K> subList(int from, int to) {
/* 735 */       ensureIndex(from);
/* 736 */       ensureIndex(to);
/* 737 */       if (from > to) throw new IllegalArgumentException("Start index (" + from + ") is greater than end index (" + to + ")");
/*     */ 
/*     */       
/* 740 */       return new ReferenceRandomAccessSubList(this, from, to);
/*     */     }
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\i\\unimi\dsi\fastutil\objects\AbstractReferenceList.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */